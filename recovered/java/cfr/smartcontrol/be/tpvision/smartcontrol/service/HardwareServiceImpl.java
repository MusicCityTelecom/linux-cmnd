/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Hardware;
import be.tpvision.smartcontrol.messages.services.hardware.AddHardwareMessages;
import be.tpvision.smartcontrol.messages.services.hardware.ConstructorMessages;
import be.tpvision.smartcontrol.messages.services.hardware.DeleteHardwareMessages;
import be.tpvision.smartcontrol.messages.services.hardware.GetHardwareMessages;
import be.tpvision.smartcontrol.messages.services.hardware.UpdateHardwareMessages;
import be.tpvision.smartcontrol.repository.HardwareRepository;
import be.tpvision.smartcontrol.service.HardwareService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class HardwareServiceImpl
implements HardwareService {
    private final HardwareRepository hardwareRepository;

    @Autowired
    public HardwareServiceImpl(HardwareRepository hardwareRepository) {
        Assert.notNull((Object)hardwareRepository, ConstructorMessages.HARDWARE_REPOSITORY_CAN_NOT_BE_NULL);
        this.hardwareRepository = hardwareRepository;
    }

    @Override
    public Set<Hardware> getHardware() {
        List hardwareList = this.hardwareRepository.getAll();
        return new HashSet<Hardware>(hardwareList);
    }

    @Override
    public Hardware getHardware(String hardwareKey) {
        Assert.notNull((Object)hardwareKey, GetHardwareMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
        return (Hardware)this.hardwareRepository.getById(hardwareKey);
    }

    @Override
    @Transactional
    public void addHardware(Hardware hardware) {
        Assert.notNull((Object)hardware, AddHardwareMessages.HARDWARE_CAN_NOT_BE_NULL);
        this.hardwareRepository.persist(hardware);
    }

    @Override
    @Transactional
    public void updateHardware(Hardware hardware) {
        Assert.notNull((Object)hardware, UpdateHardwareMessages.HARDWARE_CAN_NOT_BE_NULL);
        this.hardwareRepository.merge(hardware);
    }

    @Override
    @Transactional
    public void deleteHardware(Hardware hardware) {
        Assert.notNull((Object)hardware, DeleteHardwareMessages.HARDWARE_CAN_NOT_BE_NULL);
        this.hardwareRepository.delete(hardware);
    }

    @Override
    @Transactional
    public void deleteHardware(String hardwareKey) {
        Assert.notNull((Object)hardwareKey, DeleteHardwareMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
        this.hardwareRepository.deleteById(hardwareKey);
    }
}

