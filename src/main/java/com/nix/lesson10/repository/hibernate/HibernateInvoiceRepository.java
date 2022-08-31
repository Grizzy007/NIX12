package com.nix.lesson10.repository.hibernate;

import com.nix.lesson10.config.HibernateConfig;
import com.nix.lesson10.model.Invoice;
import com.nix.lesson10.model.vehicle.Vehicle;
import com.nix.lesson10.repository.InvoiceRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class HibernateInvoiceRepository implements InvoiceRepository {
    private static HibernateInvoiceRepository instance;
    private final EntityManager entityManager;

    private HibernateInvoiceRepository() {
        entityManager = HibernateConfig.getEntityManager();
    }

    public static synchronized HibernateInvoiceRepository getInstance() {
        if (instance == null) {
            instance = new HibernateInvoiceRepository();
        }
        return instance;
    }

    @Override
    public List<Invoice> getAll() {
        entityManager.getTransaction().begin();
        List<Invoice> invoices = entityManager.createQuery("from Invoice", Invoice.class)
                .getResultList();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return invoices;
    }

    @Override
    public Optional<Invoice> getById(String id) {
        entityManager.getTransaction().begin();
        Invoice invoice = entityManager.createQuery("from Invoice where id = :id", Invoice.class)
                .setParameter("id", id)
                .getSingleResult();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return Optional.of(invoice);
    }

    @Override
    public Invoice create(Invoice vehicle) {
        entityManager.getTransaction().begin();
        entityManager.persist(vehicle);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return null;
    }

    @Override
    public boolean createList(List<Invoice> list) {
        list.forEach(this::create);
        return false;
    }

    @Override
    public boolean update(Invoice invoice) {
        entityManager.getTransaction().begin();
        Set<Vehicle> set = invoice.getVehicles();
        entityManager.createQuery("update Invoice set created = :created, vehicles = :vehicles where id = :id")
                .setParameter("created", invoice.getCreated())
                .setParameter("vehicles", set)
                .setParameter("id", invoice.getId())
                .executeUpdate();
        entityManager.flush();
        entityManager.getTransaction().commit();
        return true;
    }

    @Override
    public Invoice delete(String id) {
        entityManager.getTransaction().begin();
        Invoice toDelete = entityManager.createQuery("from Invoice  where id = :id", Invoice.class)
                .setParameter("id", id).getSingleResult();
        entityManager.remove(toDelete);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return toDelete;
    }
}
