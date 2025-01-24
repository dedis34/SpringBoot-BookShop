package org.example.repository.impl;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import org.example.exception.DataProcessingException;
import org.example.model.CartItem;
import org.example.model.ShoppingCart;
import org.example.repository.ShoppingCartRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ShoppingCartRepositoryImpl implements ShoppingCartRepository {
    private final SessionFactory sessionFactory;

    public ShoppingCartRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public ShoppingCart save(ShoppingCart shoppingCart) throws DataProcessingException {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(shoppingCart);
            transaction.commit();
            return shoppingCart;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't save shoppingCart " + shoppingCart, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<ShoppingCart> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(ShoppingCart.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a shoppingCart by id: " + id, e);
        }
    }

    @Override
    public List<ShoppingCart> findAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<ShoppingCart> criteriaQuery = session.getCriteriaBuilder()
                    .createQuery(ShoppingCart.class);
            criteriaQuery.from(ShoppingCart.class);
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all order", e);
        }
    }

    @Override
    public void clearCart(Long id) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            ShoppingCart shoppingCart = session.get(ShoppingCart.class, id);
            if (shoppingCart != null) {
                session.remove(shoppingCart);
            } else {
                throw new DataProcessingException("ShoppingCart doesn't exist" + id);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't delete shoppingCart with id " + id, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
