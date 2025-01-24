package org.example.repository.impl;

import jakarta.persistence.criteria.CriteriaQuery;
import org.example.exception.DataProcessingException;
import org.example.model.Category;
import org.example.repository.CategoryRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    private final SessionFactory sessionFactory;

    public CategoryRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Category save(Category category) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(category);
            transaction.commit();
            return category;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't save category " + category, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Optional<Category> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Category.class, id));
        } catch (Exception e) {
            throw new DataProcessingException("Can't get a category by id: " + id, e);
        }
    }

    @Override
    public List<Category> findAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<Category> criteriaQuery = session.getCriteriaBuilder()
                    .createQuery(Category.class);
            criteriaQuery.from(Category.class);
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get all categories", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Category category = session.get(Category.class, id);
            if (category != null) {
                session.remove(category);
            } else {
                throw new DataProcessingException("category doesn't exist" + id);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't delete category with id " + id, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
