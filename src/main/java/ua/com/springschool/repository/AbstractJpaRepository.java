package ua.com.springschool.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public class AbstractJpaRepository<T> {

    private Class<T> entity;

    @PersistenceContext
    private EntityManager entityManager;

    public final void setEntity(final Class<T> entity) {
        this.entity = entity;
    }

    public Optional<T> findById(final long id) {
        return Optional.ofNullable(entityManager.find(entity, id));
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return entityManager.createQuery("from " + entity.getName()).getResultList();
    }

    public boolean existsById(final long id) {
        if (entityManager.contains(findById(id))) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public T save(T entity) {
        if (entityManager.contains(entity)) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }
        return entity;
    }


    public void delete(final T entity) {
        entityManager.remove(entity);
    }

    public void deleteById(final long id) {
        final T entity = (T) findById(id);
        delete(entity);
    }

}
