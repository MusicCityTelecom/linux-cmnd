/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.repository;

import be.tpvision.smartcontrol.repository.OrderDirection;
import be.tpvision.smartcontrol.repository.order_fields.OrderField;
import java.util.List;
import javax.persistence.metamodel.SingularAttribute;

public interface Repository<P, E> {
    public List<E> getAll();

    public SingularAttribute<? super E, ?> getSingularAttribute(OrderField<? super E> var1);

    public List<E> getAllOrderedBy(OrderField<? super E> var1, OrderDirection var2);

    public E getById(P var1);

    public void persist(E var1);

    public E merge(E var1);

    public void deleteById(P var1);

    public void delete(E var1);
}

