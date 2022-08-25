package com.nix.lesson10.repository.db;

import com.nix.lesson10.annotations.Singleton;
import com.nix.lesson10.config.JDBCConfig;
import com.nix.lesson10.model.vehicle.Auto;
import com.nix.lesson10.model.vehicle.Brand;
import com.nix.lesson10.model.vehicle.Engine;
import com.nix.lesson10.model.vehicle.Type;
import com.nix.lesson10.repository.CrudRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class DBAutoRepository implements CrudRepository<Auto> {
    private static final String GET_ALL_AUTOS = "SELECT * FROM autos a " +
            "JOIN brand b on b.id = a.Brand_id " +
            "JOIN engine e on e.id = a.Engine_id " +
            "JOIN type t on t.id = a.Type_id ";
    private static final String INSERT_AUTO = "INSERT INTO autos" +
            "(id, model, price, created ,type_id, brand_id, engine_id) " +
            "values(?,?,?,?,(SELECT id FROM type WHERE name = ?),(SELECT id FROM brand WHERE name = ?)," +
            "(SELECT id FROM engine WHERE volume = ? AND Brand_id = (SELECT id FROM brand WHERE name = ?)))";
    private static final String GET_AUTO_BY_ID = GET_ALL_AUTOS +
            "WHERE a.id = ?";
    private static final String GET_AUTO_BY_MODEL = GET_ALL_AUTOS +
            "WHERE a.Model = ?";
    private static final String UPDATE = "UPDATE autos SET Model = ?, Price = ?, Created = ?, " +
            "Engine_id =(SELECT id FROM engine WHERE volume = ? AND Brand_id =(SELECT id FROM brand WHERE name = ?))" +
            "WHERE id = ?";
    private static final String DELETE = "DELETE FROM autos WHERE id = ?";
    private static final String CLEAR = "DELETE FROM autos";

    private static DBAutoRepository instance;

    private final Connection connection;

    public static DBAutoRepository getInstance() {
        if (instance == null) {
            instance = new DBAutoRepository();
        }
        return instance;
    }

    private DBAutoRepository() {
        connection = JDBCConfig.getInstance();
    }

    @Override
    public List<Auto> getAll() {
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(GET_ALL_AUTOS)) {
            List<Auto> autos = new ArrayList<>();
            while (rs.next()) {
                autos.add(mapToObj(rs));
            }
            connection.commit();
            return autos;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public Optional<Auto> getById(String id) {
        return getAuto(id, GET_AUTO_BY_ID);
    }

    public Optional<Auto> getByModel(String model) {
        return getAuto(model, GET_AUTO_BY_MODEL);
    }

    private Optional<Auto> getAuto(String model, String getAutoBy) {
        try (PreparedStatement statement = connection.prepareStatement(getAutoBy)) {
            statement.setString(1, model);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Auto auto = mapToObj(rs);
                connection.commit();
                return Optional.of(auto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Auto create(Auto vehicle) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_AUTO)) {
            statement.setString(1, vehicle.getId());
            statement.setString(2, vehicle.getModel());
            statement.setBigDecimal(3, vehicle.getPrice());
            statement.setTimestamp(4, Timestamp.valueOf(vehicle.getCreated()));
            statement.setString(5, vehicle.getBodyType().toString());
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
    public boolean createList(List<Auto> list) {
        list.forEach(this::create);
        return true;
    }

    @Override
    public boolean update(Auto vehicle) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, vehicle.getModel());
            statement.setBigDecimal(2, vehicle.getPrice());
            statement.setTimestamp(3, Timestamp.valueOf(vehicle.getCreated()));
            statement.setDouble(4, vehicle.getEngine().getVolume());
            statement.setString(5, vehicle.getEngine().getBrand().toString());
            statement.setString(6, vehicle.getId());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Auto delete(String id) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setString(1, id);
            Optional<Auto> a = getById(id);
            if (a.isPresent()) {
                statement.executeUpdate();
                connection.commit();
                return a.get();
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

    private Auto mapToObj(ResultSet rs) throws SQLException {
        Auto auto = new Auto();
        auto.setId(rs.getString("id"));
        auto.setBrand(Brand.valueOf(rs.getString("b.name")));
        auto.setModel(rs.getString("Model"));
        auto.setPrice(rs.getBigDecimal("Price"));
        auto.setBodyType(Type.valueOf(rs.getString("t.name")));
        auto.setCreated(rs.getTimestamp("Created").toLocalDateTime());
        auto.setEngine(
                new Engine(rs.getDouble("volume"),
                        Brand.valueOf(rs.getString("b.name")),
                        rs.getInt("valves")));
        return auto;
    }
}
