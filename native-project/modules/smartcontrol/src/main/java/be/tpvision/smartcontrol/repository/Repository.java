package be.tpvision.smartcontrol.repository;

import be.tpvision.smartcontrol.repository.order_fields.OrderField;
import java.util.List;
import javax.persistence.metamodel.SingularAttribute;

public interface Repository<P, E> {
   List<E> getAll();

   SingularAttribute<? super E, ?> getSingularAttribute(OrderField<? super E> orderField);

   List<E> getAllOrderedBy(OrderField<? super E> orderField, OrderDirection orderDirection);

   E getById(P id);

   void persist(E entity);

   E merge(E entity);

   void deleteById(final P id);

   void delete(final E entity);
}
