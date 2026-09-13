/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.repository;

import be.tpvision.smartcontrol.repository.data_transfer_objects.DeviceDTO;

public interface DeviceRepositoryJdbc {
    public DeviceDTO getDeviceDTO(long var1);

    public boolean getContentRotatedBySerialCode(String var1);

    public String getSerialCode(long var1);
}

