/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.util;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.repository.OrderDirection;
import be.tpvision.smartcontrol.repository.order_fields.OrderField;
import java.util.Collection;
import java.util.List;

public interface DeviceUtilities {
    public List<Device> orderDevices(Collection<Device> var1, OrderField<? super Device> var2, OrderDirection var3);
}

