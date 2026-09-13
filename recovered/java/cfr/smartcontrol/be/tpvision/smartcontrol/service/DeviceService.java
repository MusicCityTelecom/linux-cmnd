/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.DeviceListItem;
import be.tpvision.smartcontrol.repository.OrderDirection;
import be.tpvision.smartcontrol.repository.order_fields.OrderField;
import java.util.Set;

public interface DeviceService {
    public Set<DeviceListItem> getDevices();

    public Set<DeviceListItem> detectDevices();

    public Set<Device> getDevicesOrderedBy(OrderField<? super Device> var1, OrderDirection var2);

    public Device getDevice(long var1);

    public Device getDeviceBySerialCode(String var1);

    public void addDevice(Device var1);

    public void addDevice(Device var1, boolean var2);

    public void updateDevice(Device var1);

    public void updateDevice(Device var1, boolean var2);

    public void deleteDevice(Device var1);

    public void deleteDevice(long var1);

    public void logAllDevices();

    public void logOneDevice(Device var1);
}

