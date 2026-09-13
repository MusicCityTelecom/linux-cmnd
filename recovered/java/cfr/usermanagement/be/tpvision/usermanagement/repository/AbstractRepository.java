/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.usermanagement.repository;

import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import org.springframework.util.Assert;
import util.MessageUtilities;

public abstract class AbstractRepository<P, E> {
    @PersistenceContext
    private EntityManager entityManager;
    private final Class<E> entityClass;

    public AbstractRepository(Class<E> entityClass) {
        Assert.notNull(entityClass, MessageUtilities.ABSTRACT_REPOSITORY_ENTITY_CLASS_NOT_NULL_MESSAGE);
        this.entityClass = entityClass;
    }

    private void assertEntityManagerStateNotNull() {
        Assert.state(this.entityManager != null, MessageUtilities.ENTITY_MANAGER_NOT_NULL_MESSAGE);
    }

    private void assertEntityClassStateNotNull() {
        Assert.state(this.entityClass != null, MessageUtilities.ABSTRACT_REPOSITORY_ENTITY_CLASS_NOT_NULL_MESSAGE);
    }

    public List<E> getAll() {
        this.assertEntityManagerStateNotNull();
        this.assertEntityClassStateNotNull();
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        Objects.requireNonNull(criteriaBuilder, MessageUtilities.CRITERIA_BUILDER_NOT_NULL_MESSAGE);
        CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(this.entityClass);
        Objects.requireNonNull(criteriaQuery, MessageUtilities.CRITERIA_QUERY_NOT_NULL_MESSAGE);
        criteriaQuery.from(this.entityClass);
        TypedQuery<E> query = this.entityManager.createQuery(criteriaQuery);
        Objects.requireNonNull(query, MessageUtilities.QUERY_NOT_NULL_MESSAGE);
        return query.getResultList();
    }

    public E getById(P id) {
        this.assertEntityManagerStateNotNull();
        this.assertEntityClassStateNotNull();
        return this.entityManager.find(this.entityClass, id);
    }

    public void persist(E entity) {
        this.assertEntityManagerStateNotNull();
        Assert.notNull(entity, MessageUtilities.ABSTRACT_REPOSITORY_ENTITY_NOT_NULL_MESSAGE);
        this.entityManager.persist(entity);
    }

    public E merge(E entity) {
        this.assertEntityManagerStateNotNull();
        Assert.notNull(entity, MessageUtilities.ABSTRACT_REPOSITORY_ENTITY_NOT_NULL_MESSAGE);
        return this.entityManager.merge(entity);
    }

    public void deleteById(P id) {
        Assert.notNull(id, MessageUtilities.ABSTRACT_REPOSITORY_ID_NOT_NULL_MESSAGE);
        E entity = this.getById(id);
        if (entity != null) {
            this.delete(entity);
        }
    }

    public void delete(E entity) {
        this.assertEntityManagerStateNotNull();
        Assert.notNull(entity, MessageUtilities.ABSTRACT_REPOSITORY_ENTITY_NOT_NULL_MESSAGE);
        boolean isManagedEntity = this.entityManager.contains(entity);
        E entityToDelete = isManagedEntity ? entity : this.entityManager.merge(entity);
        this.entityManager.remove(entityToDelete);
    }

    protected EntityManager getEntityManager() {
        this.assertEntityManagerStateNotNull();
        return this.entityManager;
    }
}

