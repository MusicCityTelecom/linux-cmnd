/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Hardware;
import be.tpvision.smartcontrol.messages.services.jdbc.hardware.AddHardwareMessages;
import be.tpvision.smartcontrol.messages.services.jdbc.hardware.ConstructorMessages;
import be.tpvision.smartcontrol.messages.services.jdbc.hardware.GetHardwareByContentIdMessages;
import be.tpvision.smartcontrol.messages.services.jdbc.hardware.GetHardwareMessages;
import be.tpvision.smartcontrol.messages.services.jdbc.hardware.UpdateHardwareMessages;
import be.tpvision.smartcontrol.repository.HardwareRepositoryJdbc;
import be.tpvision.smartcontrol.service.HardwareServiceJdbc;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class HardwareServiceJdbcImpl
implements HardwareServiceJdbc {
    private final HardwareRepositoryJdbc hardwareRepositoryJdbc;

    @Autowired
    public HardwareServiceJdbcImpl(HardwareRepositoryJdbc hardwareRepositoryJdbc) {
        Assert.notNull((Object)hardwareRepositoryJdbc, ConstructorMessages.HARDWARE_REPOSITORY_JDBC_CAN_NOT_BE_NULL);
        this.hardwareRepositoryJdbc = hardwareRepositoryJdbc;
    }

    @Override
    public Set<Hardware> getHardwareByContentId(String contentId) {
        Assert.notNull((Object)contentId, GetHardwareByContentIdMessages.CONTENT_ID_CAN_NOT_BE_NULL);
        Assert.isTrue(!contentId.isEmpty(), GetHardwareByContentIdMessages.CONTENT_ID_CAN_NOT_BE_EMPTY);
        return this.hardwareRepositoryJdbc.getHardwareByContentId(contentId);
    }

    @Override
    public Hardware getHardware(String hardwareKey) {
        Assert.notNull((Object)hardwareKey, GetHardwareMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
        Assert.isTrue(!hardwareKey.isEmpty(), GetHardwareMessages.HARDWARE_KEY_CAN_NOT_BE_EMPTY);
        return this.hardwareRepositoryJdbc.getHardware(hardwareKey);
    }

    @Override
    public void addHardware(Hardware hardware) {
        Assert.notNull((Object)hardware, AddHardwareMessages.HARDWARE_CAN_NOT_BE_NULL);
        this.hardwareRepositoryJdbc.addHardware(hardware);
    }

    @Override
    public void updateHardware(Hardware hardware) {
        Assert.notNull((Object)hardware, UpdateHardwareMessages.HARDWARE_CAN_NOT_BE_NULL);
        this.hardwareRepositoryJdbc.updateHardware(hardware);
    }
}

