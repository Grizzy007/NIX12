package com.nix.lesson10.repository.hibernate;

import com.nix.lesson10.config.HibernateConfig;
import com.nix.lesson10.model.vehicle.Auto;
import com.nix.lesson10.repository.CrudRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class HibernateAutoRepository implements CrudRepository<Auto> {
    private static HibernateAutoRepository instance;
    private final EntityManager entityManager;

    public static HibernateAutoRepository getInstance() {
        if (instance == null) {
            instance = new HibernateAutoRepository();
        }
        return instance;
    }

    private HibernateAutoRepository() {
        entityManager = HibernateConfig.getEntityManager();
    }

    @Override
    public List<Auto> getAll() {
        entityManager.getTransaction().begin();
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Auto> criteriaQuery = builder.createQuery(Auto.class);
        Root<Auto> getterRoot = criteriaQuery.from(Auto.class);
        criteriaQuery.select(getterRoot);
        List<Auto> autos = entityManager.createQuery(criteriaQuery)
                .getResultList();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return autos;
    }

    @Override
    public Optional<Auto> getById(String id) {
        entityManager.getTransaction().begin();
        Auto auto = entityManager.createQuery("from Auto where id = :id", Auto.class)
                .setParameter("id", id)
                .getSingleResult();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return Optional.of(auto);
    }

    @Override
    public Auto create(Auto vehicle) {
        entityManager.getTransaction().begin();
        entityManager.persist(vehicle);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return vehicle;
    }

    @Override
    public boolean createList(List<Auto> list) {
        list.forEach(this::create);
        return true;
    }

    @Override
    public boolean update(Auto vehicle) {
        entityManager.getTransaction().begin();
        entityManager.createQuery("update Auto set model = :model, price = :price, created = :created, " +
                "engine = :engine, bodyType = :type where id = :id")
                .setParameter("model", vehicle.getModel())
                .setParameter("price", vehicle.getPrice())
                .setParameter("created", vehicle.getCreated())
                .setParameter("engine", vehicle.getEngine())
                .setParameter("type", vehicle.getBodyType())
                .setParameter("id", vehicle.getId())
                .executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return true;

    }

    @Override
    public Auto delete(String id) {
        entityManager.getTransaction().begin();
        Auto toDelete = entityManager.createQuery("from Auto  where id = :id", Auto.class)
                .setParameter("id",id).getSingleResult();
        entityManager.remove(toDelete);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return toDelete;
    }
}
