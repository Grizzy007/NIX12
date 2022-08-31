package com.nix.lesson10.repository.hibernate;

import com.nix.lesson10.config.HibernateConfig;
import com.nix.lesson10.model.vehicle.Truck;
import com.nix.lesson10.repository.CrudRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class HibernateTruckRepository implements CrudRepository<Truck> {
    private static HibernateTruckRepository instance;

    public static HibernateTruckRepository getInstance() {
        if (instance == null) {
            instance = new HibernateTruckRepository();
        }
        return instance;
    }

    private HibernateTruckRepository() {
    }

    @Override
    public List<Truck> getAll() {
        SessionFactory sessionFactory = HibernateConfig.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Truck> trucks = session.createQuery("from Truck ", Truck.class)
                .list();
        session.getTransaction().commit();
        session.close();
        return trucks;
    }

    @Override
    public Optional<Truck> getById(String id) {
        SessionFactory sessionFactory = HibernateConfig.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Truck truck = session.createQuery("from Truck where id = :id", Truck.class)
                .setParameter("id", id)
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return Optional.of(truck);
    }

    @Override
    public Truck create(Truck vehicle) {
        SessionFactory sessionFactory = HibernateConfig.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(vehicle);
        session.getTransaction().commit();
        session.close();
        return vehicle;
    }

    @Override
    public boolean createList(List<Truck> list) {
        list.forEach(this::create);
        return true;
    }

    @Override
    public boolean update(Truck vehicle) {
        SessionFactory sessionFactory = HibernateConfig.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("update Truck set model = :model, price = :price, created = :created, " +
                            "engine = :engine, capacity = :capacity where id = :id")
                    .setParameter("model", vehicle.getModel())
                    .setParameter("price", vehicle.getPrice())
                    .setParameter("created", vehicle.getCreated())
                    .setParameter("engine", vehicle.getEngine())
                    .setParameter("capacity", vehicle.getCapacity())
                    .setParameter("id", vehicle.getId())
                    .executeUpdate();
            session.getTransaction().commit();
            return true;
        }
    }

    @Override
    public Truck delete(String id) {
        SessionFactory sessionFactory = HibernateConfig.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        getById(id).ifPresent(session::delete);
        session.getTransaction().commit();
        session.close();
        return getById(id).orElse(new Truck());
    }
}
