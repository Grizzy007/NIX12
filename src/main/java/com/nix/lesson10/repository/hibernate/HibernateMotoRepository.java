package com.nix.lesson10.repository.hibernate;

import com.nix.lesson10.config.HibernateConfig;
import com.nix.lesson10.model.vehicle.Motorcycle;
import com.nix.lesson10.repository.CrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class HibernateMotoRepository implements CrudRepository<Motorcycle> {
    private final EntityManager entityManager;

    private static HibernateMotoRepository instance;

    public static HibernateMotoRepository getInstance() {
        if (instance == null) {
            instance = new HibernateMotoRepository();
        }
        return instance;
    }

    public HibernateMotoRepository() {
        entityManager = HibernateConfig.getEntityManager();
    }

    @Override
    public List<Motorcycle> getAll() {
        entityManager.getTransaction().begin();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Motorcycle> query = builder.createQuery(Motorcycle.class);
        Root<Motorcycle> root = query.from(Motorcycle.class);
        query.select(root);
        List<Motorcycle> motos = entityManager.createQuery(query).getResultList();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return motos;
    }

    @Override
    public Optional<Motorcycle> getById(String id) {
        entityManager.getTransaction().begin();
        Motorcycle moto = entityManager.createQuery("from Motorcycle where id = :id", Motorcycle.class)
                .setParameter("id", id)
                .getSingleResult();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return Optional.of(moto);
    }

    @Override
    public Motorcycle create(Motorcycle vehicle) {
        entityManager.getTransaction().begin();
        entityManager.persist(vehicle);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return vehicle;
    }

    @Override
    public boolean createList(List<Motorcycle> list) {
        list.forEach(this::create);
        return true;
    }

    @Override
    public boolean update(Motorcycle vehicle) {
        entityManager.getTransaction().begin();
        entityManager.createQuery("update Motorcycle set landing = :landing, " +
                        "model = :model, price = :price, created = :created, engine = :engine where id = :id")
                .setParameter("landing", vehicle.getLanding())
                .setParameter("model", vehicle.getModel())
                .setParameter("price", vehicle.getPrice())
                .setParameter("created", vehicle.getCreated())
                .setParameter("engine", vehicle.getEngine())
                .setParameter("id", vehicle.getId())
                .executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return true;
    }

    @Override
    public Motorcycle delete(String id) {
        entityManager.getTransaction().begin();
        Motorcycle moto = entityManager.createQuery("from Motorcycle  where id = :id", Motorcycle.class)
                .setParameter("id", id).getSingleResult();
        entityManager.remove(moto);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return moto;
    }
}
