package org.example.repository.impl;

import jakarta.persistence.criteria.CriteriaQuery;
import org.example.exception.DataProcessingException;
import org.example.model.CartItem;
import org.example.repository.CartItemRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class CartItemRepositoryImpl implements CartItemRepository {
    private final SessionFactory sessionFactory;

    public CartItemRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public CartItem save(CartItem cartItem) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(cartItem);
            transaction.commit();
            return cartItem;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't save cartItem " + cartItem, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<CartItem> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(CartItem.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a CartItem by id: " + id, e);
        }
    }

    @Override
    public List<CartItem> findAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<CartItem> criteriaQuery = session.getCriteriaBuilder()
                    .createQuery(CartItem.class);
            criteriaQuery.from(CartItem.class);
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all CartItems", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            CartItem cartItem = session.get(CartItem.class, id);
            if (cartItem != null) {
                session.remove(cartItem);
            } else {
                throw new DataProcessingException("CartItem doesn't exist" + id);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't delete cartItem with id " + id, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
