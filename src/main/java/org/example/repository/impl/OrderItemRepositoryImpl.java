package org.example.repository.impl;

import jakarta.persistence.criteria.CriteriaQuery;
import org.example.exception.DataProcessingException;
import org.example.model.OrderItem;
import org.example.repository.OrderItemRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderItemRepositoryImpl implements OrderItemRepository {
    private final SessionFactory sessionFactory;

    public OrderItemRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(orderItem);
            transaction.commit();
            return orderItem;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't save orderItem " + orderItem, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<OrderItem> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(OrderItem.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a orderItem by id: " + id, e);
        }
    }

    @Override
    public List<OrderItem> findAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<OrderItem> criteriaQuery = session.getCriteriaBuilder()
                    .createQuery(OrderItem.class);
            criteriaQuery.from(OrderItem.class);
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all orderItems", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            OrderItem orderItem = session.get(OrderItem.class, id);
            if (orderItem != null) {
                session.remove(orderItem);
            } else {
                throw new DataProcessingException("OrderItem doesn't exist" + id);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't delete orderItem with id " + id, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
