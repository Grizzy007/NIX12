package com.nix.lesson10.repository.db;

import com.nix.lesson10.annotations.Autowired;
import com.nix.lesson10.annotations.Singleton;
import com.nix.lesson10.config.JDBCConfig;
import com.nix.lesson10.model.Invoice;
import com.nix.lesson10.model.vehicle.*;

import java.sql.*;
import java.util.*;

@Singleton
public class DBInvoiceRepository {
    private static final String GET_ALL = "SELECT * FROM invoices i ";
    private static final String JOIN = "LEFT JOIN autos a on a.id = f.Autos_id LEFT JOIN type on type.id = a.Type_id " +
            "LEFT JOIN motos m on m.id = f.Motos_id LEFT JOIN brand b on b.id = a.Brand_id " +
            "LEFT JOIN trucks t on t.id = f.Trucks_id LEFT JOIN engine e on e.id = a.Engine_id " +
            "LEFT JOIN engine e2 on e2.id = m.Engine_id LEFT JOIN engine e3 on e3.id = t.Engine_id " +
            "LEFT JOIN brand b2 on b2.id = m.Brand_id LEFT JOIN brand b3 on b3.id = t.Brand_id ";
    private static final String GET_FROM_INTERMEDIATE_TABLE = "SELECT * FROM factory_in_invoice f " + JOIN +
            "WHERE Invoices_id = ?";
    private static final String INSERT_INVOICE = "INSERT INTO invoices(id,Created) VALUES(?,?)";
    private static final String INSERT_INTERMEDIATE_TABLE = "INSERT INTO factory_in_invoice" +
            "(id,Invoices_id,Autos_id,Motos_id,Trucks_id) " +
            " VALUES (?,?,(SELECT id FROM autos where autos.Model = ?),(SELECT id FROM motos where motos.Model = ?), " +
            "(SELECT id FROM trucks where trucks.Model = ?))";
    private static final String GET_INVOICE_BY_ID = GET_ALL +
            "LEFT JOIN factory_in_invoice f on i.id = f.Invoices_id " + JOIN +
            "WHERE Invoices_id = ?";
    private static final String UPDATE = "UPDATE invoices SET created = ? WHERE id = ?";
    private static final String DELETE_INVOICE = "DELETE FROM invoices WHERE id = ?";
    private static final String CLEAR = "DELETE FROM invoices";

    private final DBAutoRepository autoRepo;
    private final DBMotoRepository motoRepo;
    private final DBTruckRepository truckRepo;

    private static DBInvoiceRepository instance;
    private final Connection connection;

    @Autowired
    private DBInvoiceRepository() {
        connection = JDBCConfig.getInstance();
        autoRepo = DBAutoRepository.getInstance();
        motoRepo = DBMotoRepository.getInstance();
        truckRepo = DBTruckRepository.getInstance();
    }

    public static synchronized DBInvoiceRepository getInstance() {
        if (instance == null) {
            instance = new DBInvoiceRepository();
        }
        return instance;
    }

    public List<Invoice> getAll() {
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(GET_ALL)) {
            List<Invoice> invoices = new ArrayList<>();
            while (rs.next()) {
                Invoice invoice = formInvoice(rs);
                invoices.add(invoice);
            }
            connection.commit();
            return invoices;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public Optional<Invoice> getInvoiceByID(String id) {
        try (PreparedStatement ps = connection.prepareStatement(GET_INVOICE_BY_ID)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(formInvoice(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    public Invoice create(Invoice invoice) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_INVOICE)) {
            String id = invoice.getId();
            statement.setString(1, id);
            statement.setTimestamp(2, Timestamp.valueOf(invoice.getCreated()));
            statement.executeUpdate();
            List<Vehicle> vehicles = invoice.getVehicles();
            List<Auto> autos = vehicles.stream()
                    .filter(a -> a.getClass() == Auto.class)
                    .map(Auto.class::cast).toList();
            List<Motorcycle> motos = vehicles.stream()
                    .filter(a -> a.getClass() == Motorcycle.class)
                    .map(Motorcycle.class::cast).toList();
            List<Truck> trucks = vehicles.stream()
                    .filter(a -> a.getClass() == Truck.class)
                    .map(Truck.class::cast).toList();
            Iterator<Auto> iAuto = autos.iterator();
            Iterator<Motorcycle> iMoto = motos.iterator();
            Iterator<Truck> iTruck = trucks.iterator();
            insertIntermediateTableAutos(iAuto, id);
            insertIntermediateTableMotos(iMoto, id);
            insertIntermediateTableTrucks(iTruck, id);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoice;
    }

    public boolean createList(List<Invoice> list) {
        try {
            list.forEach(this::create);
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Invoice invoice) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setTimestamp(1, Timestamp.valueOf(invoice.getCreated()));
            statement.setString(2, invoice.getId());
            return statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Invoice delete(String id) {
        try (PreparedStatement ps = connection.prepareStatement(DELETE_INVOICE)) {
            ps.setString(1, id);
            Optional<Invoice> invoice = getInvoiceByID(id);
            if (invoice.isPresent()) {
                ps.executeUpdate();
                connection.commit();
                return invoice.get();
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void clear() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CLEAR);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Invoice formInvoice(ResultSet rs) throws SQLException {
        Invoice invoice = new Invoice();
        String id = rs.getString("i.id");
        invoice.setId(id);
        invoice.setCreated(rs.getTimestamp("i.created").toLocalDateTime());
        List<Vehicle> vehicles = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(GET_FROM_INTERMEDIATE_TABLE)) {
            ps.setString(1, id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("f.Autos_id") != null) {
                    Auto auto = parseAuto(resultSet);
                    vehicles.add(auto);
                }
                if (resultSet.getString("f.Motos_id") != null) {
                    Motorcycle moto = parseMoto(resultSet);
                    vehicles.add(moto);
                }
                if (resultSet.getString("f.Trucks_id") != null) {
                    Truck truck = parseTruck(resultSet);
                    vehicles.add(truck);
                }
            }
        }
        invoice.setVehicles(vehicles);
        return invoice;
    }

    private Auto parseAuto(ResultSet resultSet) throws SQLException {
        Auto auto = new Auto();
        auto.setId(resultSet.getString("a.id"));
        auto.setBrand(Brand.valueOf(resultSet.getString("b.name")));
        auto.setModel(resultSet.getString("a.Model"));
        auto.setPrice(resultSet.getBigDecimal("a.Price"));
        auto.setBodyType(Type.valueOf(resultSet.getString("type.name")));
        auto.setCreated(resultSet.getTimestamp("a.Created").toLocalDateTime());
        auto.setEngine(
                new Engine(resultSet.getDouble("e.volume"),
                        Brand.valueOf(resultSet.getString("b.name")),
                        resultSet.getInt("e.valves")));
        return auto;
    }

    private Motorcycle parseMoto(ResultSet resultSet) throws SQLException {
        Motorcycle moto = new Motorcycle();
        moto.setId(resultSet.getString("m.id"));
        moto.setBrand(Brand.valueOf(resultSet.getString("b2.name")));
        moto.setModel(resultSet.getString("m.Model"));
        moto.setPrice(resultSet.getBigDecimal("m.Price"));
        moto.setLanding(resultSet.getInt("m.Landing"));
        moto.setCreated(resultSet.getTimestamp("m.Created").toLocalDateTime());
        moto.setEngine(
                new Engine(resultSet.getDouble("e2.volume"),
                        Brand.valueOf(resultSet.getString("b2.name")),
                        resultSet.getInt("e2.valves")));
        return moto;
    }

    private Truck parseTruck(ResultSet resultSet) throws SQLException {
        Truck truck = new Truck();
        truck.setId(resultSet.getString("t.id"));
        truck.setBrand(Brand.valueOf(resultSet.getString("b3.name")));
        truck.setModel(resultSet.getString("t.Model"));
        truck.setPrice(resultSet.getBigDecimal("t.Price"));
        truck.setCapacity(resultSet.getInt("t.capacity"));
        truck.setCreated(resultSet.getTimestamp("t.Created").toLocalDateTime());
        truck.setEngine(
                new Engine(resultSet.getDouble("e3.volume"),
                        Brand.valueOf(resultSet.getString("b3.name")),
                        resultSet.getInt("e3.valves")));
        return truck;
    }

    private void insertIntermediateTableTrucks(Iterator<Truck> iterator, String id) {
        while (iterator.hasNext()) {
            try (PreparedStatement ps = connection.prepareStatement(INSERT_INTERMEDIATE_TABLE)) {
                ps.setString(1, UUID.randomUUID().toString());
                ps.setString(2, id);
                Truck truck = iterator.next();
                if (truckRepo.getByModel(truck.getModel()).isEmpty()) {
                    truckRepo.create(truck);
                }
                ps.setString(3, null);
                ps.setString(4, null);
                ps.setString(5, truck.getModel());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertIntermediateTableMotos(Iterator<Motorcycle> iterator, String id) {
        while (iterator.hasNext()) {
            try (PreparedStatement ps = connection.prepareStatement(INSERT_INTERMEDIATE_TABLE)) {
                ps.setString(1, UUID.randomUUID().toString());
                ps.setString(2, id);
                Motorcycle moto = iterator.next();
                if (motoRepo.getByModel(moto.getModel()).isEmpty()) {
                    motoRepo.create(moto);
                }
                ps.setString(3, null);
                ps.setString(4, moto.getModel());
                ps.setString(5, null);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void insertIntermediateTableAutos(Iterator<Auto> iterator, String id) {
        while (iterator.hasNext()) {
            try (PreparedStatement ps = connection.prepareStatement(INSERT_INTERMEDIATE_TABLE)) {
                ps.setString(1, UUID.randomUUID().toString());
                ps.setString(2, id);
                Auto auto = iterator.next();
                if (autoRepo.getByModel(auto.getModel()).isEmpty()) {
                    autoRepo.create(auto);
                }
                ps.setString(3, auto.getModel());
                ps.setString(4, null);
                ps.setString(5, null);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
