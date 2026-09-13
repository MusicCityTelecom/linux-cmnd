package be.tpvision.usermanagement.repository;

import be.tpvision.usermanagement.domain.OrderDirection;
import be.tpvision.usermanagement.domain.Role;
import be.tpvision.usermanagement.domain.User;
import be.tpvision.usermanagement.domain.User_;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import util.MessageUtilities;

@Repository
public class UserRepository extends AbstractRepository<Long, User> {
   public UserRepository() {
      super(User.class);
   }

   public List<User> getAllOrderedBy(final SingularAttribute<? super User, ?> singularAttribute, final OrderDirection orderDirection) {
      Assert.notNull(singularAttribute, MessageUtilities.SINGULAR_ATTRIBUTE_NOT_NULL_MESSAGE);
      Assert.notNull(orderDirection, MessageUtilities.ORDER_DIRECTION_NOT_NULL_MESSAGE);
      EntityManager entityManager = super.getEntityManager();
      Objects.requireNonNull(entityManager, MessageUtilities.ENTITY_MANAGER_NOT_NULL_MESSAGE);
      CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
      Objects.requireNonNull(criteriaBuilder, MessageUtilities.CRITERIA_BUILDER_NOT_NULL_MESSAGE);
      CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
      Objects.requireNonNull(criteriaQuery, MessageUtilities.CRITERIA_QUERY_NOT_NULL_MESSAGE);
      Root<User> root = criteriaQuery.from(User.class);
      Objects.requireNonNull(root, MessageUtilities.ROOT_NOT_NULL_MESSAGE);
      Expression<?> expression = root.get(singularAttribute);
      Objects.requireNonNull(expression, MessageUtilities.EXPRESSION_NOT_NULL_MESSAGE);
      Order order = orderDirection.equals(OrderDirection.DESC) ? criteriaBuilder.desc(expression) : criteriaBuilder.asc(expression);
      Objects.requireNonNull(order, MessageUtilities.ORDER_NOT_NULL_MESSAGE);
      criteriaQuery.orderBy(order);
      Query query = entityManager.createQuery(criteriaQuery);
      Objects.requireNonNull(query, MessageUtilities.QUERY_NOT_NULL_MESSAGE);
      return query.getResultList();
   }

   public List<User> getAllByRole(final Role role) {
      Assert.notNull(role, MessageUtilities.USER_ROLE_NOT_NULL_MESSAGE);
      EntityManager entityManager = super.getEntityManager();
      Objects.requireNonNull(entityManager, MessageUtilities.ENTITY_MANAGER_NOT_NULL_MESSAGE);
      CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
      Objects.requireNonNull(criteriaBuilder, MessageUtilities.CRITERIA_BUILDER_NOT_NULL_MESSAGE);
      CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
      Objects.requireNonNull(criteriaQuery, MessageUtilities.CRITERIA_QUERY_NOT_NULL_MESSAGE);
      Root<User> root = criteriaQuery.from(User.class);
      Objects.requireNonNull(root, MessageUtilities.ROOT_NOT_NULL_MESSAGE);
      Expression<?> expression = root.get(User_.role);
      Objects.requireNonNull(expression, MessageUtilities.EXPRESSION_NOT_NULL_MESSAGE);
      Predicate predicate = criteriaBuilder.equal(expression, role);
      Objects.requireNonNull(predicate, MessageUtilities.PREDICATE_NOT_NULL_MESSAGE);
      criteriaQuery.where(predicate);
      Query query = entityManager.createQuery(criteriaQuery);
      Objects.requireNonNull(query, MessageUtilities.QUERY_NOT_NULL_MESSAGE);
      return query.getResultList();
   }

   public List<User> getAllByRoleOrderedBy(final Role role, final SingularAttribute<? super User, ?> singularAttribute, final OrderDirection orderDirection) {
      Assert.notNull(role, MessageUtilities.USER_ROLE_NOT_NULL_MESSAGE);
      Assert.notNull(singularAttribute, MessageUtilities.SINGULAR_ATTRIBUTE_NOT_NULL_MESSAGE);
      Assert.notNull(orderDirection, MessageUtilities.ORDER_DIRECTION_NOT_NULL_MESSAGE);
      EntityManager entityManager = super.getEntityManager();
      Objects.requireNonNull(entityManager, MessageUtilities.ENTITY_MANAGER_NOT_NULL_MESSAGE);
      CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
      Objects.requireNonNull(criteriaBuilder, MessageUtilities.CRITERIA_BUILDER_NOT_NULL_MESSAGE);
      CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
      Objects.requireNonNull(criteriaQuery, MessageUtilities.CRITERIA_QUERY_NOT_NULL_MESSAGE);
      Root<User> root = criteriaQuery.from(User.class);
      Objects.requireNonNull(root, MessageUtilities.ROOT_NOT_NULL_MESSAGE);
      Expression<?> expression = root.get(User_.role);
      Objects.requireNonNull(expression, MessageUtilities.EXPRESSION_NOT_NULL_MESSAGE);
      Predicate predicate = criteriaBuilder.equal(expression, role);
      Objects.requireNonNull(predicate, MessageUtilities.PREDICATE_NOT_NULL_MESSAGE);
      criteriaQuery.where(predicate);
      expression = root.get(singularAttribute);
      Objects.requireNonNull(expression, MessageUtilities.EXPRESSION_NOT_NULL_MESSAGE);
      Order order = orderDirection.equals(OrderDirection.DESC) ? criteriaBuilder.desc(expression) : criteriaBuilder.asc(expression);
      Objects.requireNonNull(order, MessageUtilities.ORDER_NOT_NULL_MESSAGE);
      criteriaQuery.orderBy(order);
      Query query = entityManager.createQuery(criteriaQuery);
      Objects.requireNonNull(query, MessageUtilities.QUERY_NOT_NULL_MESSAGE);
      return query.getResultList();
   }

   public Optional<User> getByUsername(final String username) {
      Assert.notNull(username, MessageUtilities.USER_USERNAME_NOT_NULL_MESSAGE);
      EntityManager entityManager = super.getEntityManager();
      Objects.requireNonNull(entityManager, MessageUtilities.ENTITY_MANAGER_NOT_NULL_MESSAGE);
      CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
      Objects.requireNonNull(criteriaBuilder, MessageUtilities.CRITERIA_BUILDER_NOT_NULL_MESSAGE);
      CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
      Objects.requireNonNull(criteriaQuery, MessageUtilities.CRITERIA_QUERY_NOT_NULL_MESSAGE);
      Root<User> root = criteriaQuery.from(User.class);
      Objects.requireNonNull(root, MessageUtilities.ROOT_NOT_NULL_MESSAGE);
      Expression<?> expression = root.get(User_.username);
      Objects.requireNonNull(expression, MessageUtilities.EXPRESSION_NOT_NULL_MESSAGE);
      Predicate predicate = criteriaBuilder.equal(expression, username);
      Objects.requireNonNull(predicate, MessageUtilities.PREDICATE_NOT_NULL_MESSAGE);
      criteriaQuery.where(predicate);
      Query query = entityManager.createQuery(criteriaQuery);
      Objects.requireNonNull(query, MessageUtilities.QUERY_NOT_NULL_MESSAGE);

      try {
         User user = (User)query.getSingleResult();
         return Optional.of(user);
      } catch (NoResultException e) {
         return Optional.empty();
      }
   }
}
