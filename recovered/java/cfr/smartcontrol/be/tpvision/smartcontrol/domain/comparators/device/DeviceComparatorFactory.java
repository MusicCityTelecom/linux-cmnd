/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.domain.comparators.device;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.repository.OrderDirection;
import be.tpvision.smartcontrol.repository.order_fields.OrderField;
import java.util.Comparator;

public interface DeviceComparatorFactory {
    public Comparator<? super Device> getComparator(OrderField<? super Device> var1, OrderDirection var2);
}

