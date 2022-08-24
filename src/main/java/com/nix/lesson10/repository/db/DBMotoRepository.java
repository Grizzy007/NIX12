package com.nix.lesson10.repository.db;

import com.nix.lesson10.config.JDBCConfig;
import com.nix.lesson10.model.vehicle.Brand;
import com.nix.lesson10.model.vehicle.Engine;
import com.nix.lesson10.model.vehicle.Motorcycle;
import com.nix.lesson10.repository.CrudRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DBMotoRepository implements CrudRepository<Motorcycle> {
    private static final String GET_ALL_MOTOS = "SELECT * FROM motos t " +
            "JOIN brand b on b.id = t.Brand_id " +
            "JOIN engine e on e.id = t.Engine_id ";
    private static final String INSERT_MOTOS = "INSERT INTO motos" +
            "(id, model, price, created, landing, brand_id, engine_id) " +
            "values(?,?,?,?,?,(SELECT id FROM brand WHERE name = ?)," +
            "(SELECT id FROM engine WHERE volume = ? AND Brand_id = (SELECT id FROM brand WHERE name = ?)))";
    private static final String GET_TRUCK_BY_ID = "SELECT * FROM motos m " +
            "JOIN brand b on b.id = m.Brand_id " +
            "WHERE m.id = ?";
    private static final String UPDATE = "UPDATE motos SET Model = ?, Price = ?, Created = ?, Landing = ?, " +
            "Engine_id =(SELECT id FROM engine WHERE volume = ? AND Brand_id =(SELECT id FROM brand WHERE name = ?))" +
            "WHERE id = ?";
    private static final String DELETE = "DELETE FROM motos WHERE id = ?";
    private static final String CLEAR = "DELETE FROM motos";

    private static DBMotoRepository instance;

    private final Connection connection;

    public static DBMotoRepository getInstance() {
        if (instance == null) {
            instance = new DBMotoRepository();
        }
        return instance;
    }

    private DBMotoRepository() {
        connection = JDBCConfig.getInstance();
    }

    @Override
    public List<Motorcycle> getAll() {
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(GET_ALL_MOTOS)) {
            List<Motorcycle> motorcycles = new ArrayList<>();
            while (rs.next()) {
                motorcycles.add(mapToObj(rs));
            }
            connection.commit();
            return motorcycles;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public Optional<Motorcycle> getById(String id) {
        try (PreparedStatement statement = connection.prepareStatement(GET_TRUCK_BY_ID)) {
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Motorcycle motorcycle = mapToObj(rs);
                connection.commit();
                return Optional.of(motorcycle);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Motorcycle create(Motorcycle vehicle) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_MOTOS)) {
            statement.setString(1, vehicle.getId());
            statement.setString(2, vehicle.getModel());
            statement.setBigDecimal(3, vehicle.getPrice());
            statement.setTimestamp(4, Timestamp.valueOf(vehicle.getCreated()));
            statement.setInt(5,vehicle.getLanding());
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
    public boolean createList(List<Motorcycle> list) {
        list.forEach(this::create);
        return true;
    }

    @Override
    public boolean update(Motorcycle vehicle) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, vehicle.getModel());
            statement.setBigDecimal(2, vehicle.getPrice());
            statement.setTimestamp(3, Timestamp.valueOf(vehicle.getCreated()));
            statement.setInt(4, vehicle.getLanding());
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
    public Motorcycle delete(String id) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setString(1, id);
            Optional<Motorcycle> m = getById(id);
            if (m.isPresent()) {
                statement.executeUpdate();
                connection.commit();
                return m.get();
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

    private Motorcycle mapToObj(ResultSet rs) throws SQLException {
        Motorcycle motos = new Motorcycle();
        motos.setId(rs.getString("id"));
        motos.setBrand(Brand.valueOf(rs.getString("b.name")));
        motos.setModel(rs.getString("Model"));
        motos.setPrice(rs.getBigDecimal("Price"));
        motos.setLanding(rs.getInt("Landing"));
        motos.setCreated(rs.getTimestamp("Created").toLocalDateTime());
        motos.setEngine(
                new Engine(rs.getDouble("volume"),
                        Brand.valueOf(rs.getString("b.name")),
                        rs.getInt("valves")));
        return motos;
    }
}
