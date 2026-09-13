/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.messages.services.jdbc.device.ConstructorMessages;
import be.tpvision.smartcontrol.messages.services.jdbc.device.GetContentRotatedBySerialCodeMessages;
import be.tpvision.smartcontrol.messages.services.jdbc.device.GetDeviceMessages;
import be.tpvision.smartcontrol.messages.services.jdbc.device.GetSerialCodeMessages;
import be.tpvision.smartcontrol.repository.DeviceRepositoryJdbc;
import be.tpvision.smartcontrol.repository.data_transfer_objects.DeviceDTO;
import be.tpvision.smartcontrol.service.DeviceServiceJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class DeviceServiceJdbcImpl
implements DeviceServiceJdbc {
    private final DeviceRepositoryJdbc deviceRepositoryJdbc;

    @Autowired
    public DeviceServiceJdbcImpl(DeviceRepositoryJdbc deviceRepositoryJdbc) {
        Assert.notNull((Object)deviceRepositoryJdbc, ConstructorMessages.DEVICE_REPOSITORY_JDBC_CAN_NOT_BE_NULL);
        this.deviceRepositoryJdbc = deviceRepositoryJdbc;
    }

    @Override
    public DeviceDTO getDeviceDTO(long id) {
        Assert.isTrue(id >= 0L, GetDeviceMessages.ID_HAS_TO_BE_A_POSITIVE_NUMBER);
        return this.deviceRepositoryJdbc.getDeviceDTO(id);
    }

    @Override
    public boolean getContentRotatedBySerialCode(String serialCode) {
        Assert.notNull((Object)serialCode, GetContentRotatedBySerialCodeMessages.SERIAL_CODE_CAN_NOT_BE_NULL);
        Assert.isTrue(!serialCode.isEmpty(), GetContentRotatedBySerialCodeMessages.SERIAL_CODE_CAN_NOT_BE_EMPTY);
        return this.deviceRepositoryJdbc.getContentRotatedBySerialCode(serialCode);
    }

    @Override
    public String getSerialCode(long id) {
        Assert.isTrue(id >= 0L, GetSerialCodeMessages.ID_HAS_TO_BE_A_POSITIVE_NUMBER);
        return this.deviceRepositoryJdbc.getSerialCode(id);
    }
}

