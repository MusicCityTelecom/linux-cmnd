/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.repository;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.Group;
import be.tpvision.smartcontrol.repository.OrderDirection;
import be.tpvision.smartcontrol.repository.Repository;
import be.tpvision.smartcontrol.repository.order_fields.OrderField;
import java.util.List;

public interface GroupRepository
extends Repository<Long, Group> {
    public Group getByIdWithDevicesOrderedBy(long var1, OrderField<? super Device> var3, OrderDirection var4);

    public List<Group> getAllOrderedByWithDevicesOrderedBy(OrderField<? super Group> var1, OrderDirection var2, OrderField<? super Device> var3, OrderDirection var4);
}

