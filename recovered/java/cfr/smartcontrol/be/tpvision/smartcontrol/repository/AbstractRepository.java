/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.repository;

import be.tpvision.smartcontrol.messages.repositories.abstract_repository.ConstructorMessages;
import be.tpvision.smartcontrol.messages.repositories.abstract_repository.GetAllOrderedByMessages;
import be.tpvision.smartcontrol.repository.OrderDirection;
import be.tpvision.smartcontrol.repository.Repository;
import be.tpvision.smartcontrol.repository.order_fields.OrderField;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import org.springframework.util.Assert;

public abstract class AbstractRepository<P, E>
implements Repository<P, E> {
    @PersistenceContext
    private EntityManager entityManager;
    private final Class<E> entityClass;

    public AbstractRepository(Class<E> entityClass) {
        Assert.notNull(entityClass, ConstructorMessages.ENTITY_CLASS_CAN_NOT_BE_NULL);
        this.entityClass = entityClass;
    }

    @Override
    public List<E> getAll() {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(this.entityClass);
        criteriaQuery.from(this.entityClass);
        TypedQuery<E> query = this.entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public List<E> getAllOrderedBy(OrderField<? super E> orderField, OrderDirection orderDirection) {
        Assert.notNull(orderField, GetAllOrderedByMessages.ORDER_FIELD_CAN_NOT_BE_NULL);
        Assert.notNull((Object)orderDirection, GetAllOrderedByMessages.ORDER_DIRECTION_CAN_NOT_BE_NULL);
        SingularAttribute<? super E, ?> singularAttribute = this.getSingularAttribute(orderField);
        Assert.state(singularAttribute != null, GetAllOrderedByMessages.SINGULAR_ATTRIBUTE_CAN_NOT_BE_NULL);
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        Assert.state(criteriaBuilder != null, GetAllOrderedByMessages.CRITERIA_BUILDER_CAN_NOT_BE_NULL);
        CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(this.entityClass);
        Assert.state(criteriaQuery != null, GetAllOrderedByMessages.CRITERIA_QUERY_CAN_NOT_BE_NULL);
        Root<E> root = criteriaQuery.from(this.entityClass);
        Assert.state(root != null, GetAllOrderedByMessages.ROOT_CAN_NOT_BE_NULL);
        Path<?> expression = root.get(singularAttribute);
        Assert.state(expression != null, GetAllOrderedByMessages.EXPRESSION_CAN_NOT_BE_NULL);
        boolean isOrderDirectionDesc = orderDirection.equals((Object)OrderDirection.DESC);
        Order order = isOrderDirectionDesc ? criteriaBuilder.desc(expression) : criteriaBuilder.asc(expression);
        Assert.state(order != null, GetAllOrderedByMessages.ORDER_CAN_NOT_BE_NULL);
        criteriaQuery.orderBy(order);
        TypedQuery<E> query = this.entityManager.createQuery(criteriaQuery);
        Assert.state(query != null, GetAllOrderedByMessages.QUERY_CAN_NOT_BE_NULL);
        return query.getResultList();
    }

    @Override
    public E getById(P id) {
        return this.entityManager.find(this.entityClass, id);
    }

    @Override
    public void persist(E entity) {
        this.entityManager.persist(entity);
    }

    @Override
    public E merge(E entity) {
        return this.entityManager.merge(entity);
    }

    @Override
    public void deleteById(P id) {
        E entity = this.getById(id);
        if (entity != null) {
            this.delete(entity);
        }
    }

    @Override
    public void delete(E entity) {
        boolean isManagedEntity = this.entityManager.contains(entity);
        E entityToDelete = isManagedEntity ? entity : this.entityManager.merge(entity);
        this.entityManager.remove(entityToDelete);
    }

    protected EntityManager getEntityManager() {
        return this.entityManager;
    }
}

