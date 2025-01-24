package org.example.repository.impl;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import org.example.exception.DataProcessingException;
import org.example.repository.OrderRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private final SessionFactory sessionFactory;

    public OrderRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Order save(Order order) throws DataProcessingException {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(order);
            transaction.commit();
            return order;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't save order " + order, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<Order> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Order.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a order by id: " + id, e);
        }
    }

    @Override
    public List<Order> findAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<Order> criteriaQuery = session.getCriteriaBuilder()
                    .createQuery(Order.class);
            criteriaQuery.from(Order.class);
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all order", e);
        }
    }

    @Override
    public void updateStatus(Long orderId, String status) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Order order = session.get(Order.class, orderId);
            if (order != null) {
                order.setStatus(status);
                session.merge(order);
                transaction.commit();
            } else {
                throw new DataProcessingException("Order with id " + orderId + " doesn't exist");
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't update status for order with id " + orderId, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
