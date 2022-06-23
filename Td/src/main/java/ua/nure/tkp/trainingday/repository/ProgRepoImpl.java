package ua.nure.tkp.trainingday.repository.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Bean;
import ua.nure.tkp.trainingday.entity.Program;
import ua.nure.tkp.trainingday.repository.ProgRepo;
import ua.nure.tkp.trainingday.util.HibernateUtil;

public class ProgRepoImpl implements ProgRepo {
    @Override
    public Iterable<Program> findAll() {
        Session session = null;
        Transaction transaction = null;
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        Iterable<Program> programs = session.createNativeQuery("SELECT * FROM programs").list();
        return programs;
    }

    @Override
    public Program findById(Integer id) {
        return null;
    }

    @Override
    public Program delete(Integer id) {
        return null;
    }

    @Override
    public Program save(Program p) {
        return null;
    }
}
