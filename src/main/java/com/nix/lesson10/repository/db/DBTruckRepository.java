package com.nix.lesson10.repository.db;

import com.nix.lesson10.config.JDBCConfig;
import com.nix.lesson10.model.vehicle.*;
import com.nix.lesson10.repository.CrudRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DBTruckRepository implements CrudRepository<Truck> {
    private static final String GET_ALL_TRUCKS = "SELECT * FROM trucks t " +
            "JOIN brand b on b.id = t.Brand_id " +
            "JOIN engine e on e.id = t.Engine_id ";
    private static final String INSERT_TRUCK = "INSERT INTO trucks" +
            "(id, model, price, created, capacity, brand_id, engine_id) " +
            "values(?,?,?,?,?,(SELECT id FROM brand WHERE name = ?)," +
            "(SELECT id FROM engine WHERE volume = ? AND Brand_id = (SELECT id FROM brand WHERE name = ?)))";
    private static final String GET_TRUCK_BY_ID = "SELECT * FROM trucks t " +
            "JOIN brand b on b.id = t.Brand_id " +
            "WHERE t.id = ?";
    private static final String UPDATE = "UPDATE trucks SET Model = ?, Price = ?, Created = ?, Capacity = ?, " +
            "Engine_id =(SELECT id FROM engine WHERE volume = ? AND Brand_id =(SELECT id FROM brand WHERE name = ?))" +
            "WHERE id = ?";
    private static final String DELETE = "DELETE FROM trucks WHERE id = ?";
    private static final String CLEAR = "DELETE FROM trucks";

    private static DBTruckRepository instance;

    private final Connection connection;

    public static DBTruckRepository getInstance() {
        if (instance == null) {
            instance = new DBTruckRepository();
        }
        return instance;
    }

    private DBTruckRepository() {
        connection = JDBCConfig.getInstance();
    }

    @Override
    public List<Truck> getAll() {
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(GET_ALL_TRUCKS)) {
            List<Truck> trucks = new ArrayList<>();
            while (rs.next()) {
                trucks.add(mapToObj(rs));
            }
            connection.commit();
            return trucks;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public Optional<Truck> getById(String id) {
        try (PreparedStatement statement = connection.prepareStatement(GET_TRUCK_BY_ID)) {
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Truck truck = mapToObj(rs);
                connection.commit();
                return Optional.of(truck);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Truck create(Truck vehicle) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_TRUCK)) {
            statement.setString(1, vehicle.getId());
            statement.setString(2, vehicle.getModel());
            statement.setBigDecimal(3, vehicle.getPrice());
            statement.setTimestamp(4, Timestamp.valueOf(vehicle.getCreated()));
            statement.setInt(5, vehicle.getCapacity());
            statement.setString(6, vehicle.getBrand().toString());
            statement.setDouble(7, vehicle.getEngine().getVolume());
            statement.setString(8, vehicle.getEngine().getBrand().toString());
            statement.executeUpdate();
            connection.commit();
            return vehicle;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean createList(List<Truck> list) {
        list.forEach(this::create);
        return true;
    }

    @Override
    public boolean update(Truck vehicle) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, vehicle.getModel());
            statement.setBigDecimal(2, vehicle.getPrice());
            statement.setTimestamp(3, Timestamp.valueOf(vehicle.getCreated()));
            statement.setInt(4, vehicle.getCapacity());
            statement.setDouble(5, vehicle.getEngine().getVolume());
            statement.setString(6, vehicle.getEngine().getBrand().toString());
            statement.setString(7, vehicle.getId());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Truck delete(String id) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setString(1, id);
            Optional<Truck> t = getById(id);
            if (t.isPresent()) {
                statement.executeUpdate();
                connection.commit();
                return t.get();
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

    private Truck mapToObj(ResultSet rs) throws SQLException {
        Truck truck = new Truck();
        truck.setId(rs.getString("id"));
        truck.setBrand(Brand.valueOf(rs.getString("b.name")));
        truck.setModel(rs.getString("Model"));
        truck.setPrice(rs.getBigDecimal("Price"));
        truck.setCapacity(rs.getInt("capacity"));
        truck.setCreated(rs.getTimestamp("Created").toLocalDateTime());
        truck.setEngine(
                new Engine(rs.getDouble("volume"),
                        Brand.valueOf(rs.getString("b.name")),
                        rs.getInt("valves")));
        return truck;
    }
}
