/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.repository;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.io.ip.IpDestination;
import be.tpvision.smartcontrol.repository.Repository;

public interface DeviceRepository
extends Repository<Long, Device> {
    public Device getByIpDestination(IpDestination var1);

    public Device getBySerialCode(String var1);
}

