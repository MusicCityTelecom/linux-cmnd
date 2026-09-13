/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.repository.data_transfer_objects.DeviceDTO;

public interface DeviceServiceJdbc {
    public DeviceDTO getDeviceDTO(long var1);

    public boolean getContentRotatedBySerialCode(String var1);

    public String getSerialCode(long var1);
}

