package org.example.repository.impl;

import jakarta.persistence.criteria.CriteriaQuery;
import org.example.exception.DataProcessingException;
import org.example.model.Role;
import org.example.repository.RoleRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RoleRepositoryImpl implements RoleRepository {
    private final SessionFactory sessionFactory;

    public RoleRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Role> findAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<Role> criteriaQuery = session.getCriteriaBuilder()
                    .createQuery(Role.class);
            criteriaQuery.from(Role.class);
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all roles", e);
        }
    }

    @Override
    public Role save(Role role) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(role);
            transaction.commit();
            return role;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't save role " + role, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<Role> findByName(Role name) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Role.class, name));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a order by id: " + name, e);
        }
    }
}
