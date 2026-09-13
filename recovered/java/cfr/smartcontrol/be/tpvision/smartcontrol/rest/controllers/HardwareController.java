/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.controllers;

import be.tpvision.smartcontrol.domain.Hardware;
import be.tpvision.smartcontrol.messages.controllers.hardware.AddHardwareMessages;
import be.tpvision.smartcontrol.messages.controllers.hardware.ConstructorMessages;
import be.tpvision.smartcontrol.messages.controllers.hardware.DeleteHardwareMessages;
import be.tpvision.smartcontrol.messages.controllers.hardware.GetHardwareMessages;
import be.tpvision.smartcontrol.messages.controllers.hardware.UpdateHardwareMessages;
import be.tpvision.smartcontrol.rest.mappers.hardware.HardwareMapper;
import be.tpvision.smartcontrol.rest.view_models.hardware.HardwareViewModel;
import be.tpvision.smartcontrol.service.HardwareService;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/hardware"})
public class HardwareController {
    private final HardwareService hardwareService;

    @Autowired
    public HardwareController(HardwareService hardwareService) {
        Assert.notNull((Object)hardwareService, ConstructorMessages.HARDWARE_SERVICE_CAN_NOT_BE_NULL);
        this.hardwareService = hardwareService;
    }

    private Hardware getHardware(String hardwareKey) {
        Assert.notNull((Object)hardwareKey, GetHardwareMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
        Hardware hardware = this.hardwareService.getHardware(hardwareKey);
        String hardwareNotFoundMessage = GetHardwareMessages.getHardwareNotFoundMessage(hardwareKey);
        Assert.state(hardware != null, hardwareNotFoundMessage);
        return hardware;
    }

    @GetMapping
    public List<HardwareViewModel> getHardware() {
        Set<Hardware> hardwareSet = this.hardwareService.getHardware();
        return HardwareMapper.toHardwareViewModelList(hardwareSet);
    }

    @PostMapping(value={"/{hardwareKey}"})
    public void addHardware(@PathVariable(value="hardwareKey") String hardwareKey) {
        Assert.notNull((Object)hardwareKey, AddHardwareMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
        Hardware hardware = new Hardware();
        hardware.setHardwareKey(hardwareKey);
        this.hardwareService.addHardware(hardware);
    }

    @PutMapping(value={"/{hardwareKey}/{contentId}"})
    public void updateHardware(@PathVariable(value="hardwareKey") String hardwareKey, @PathVariable(value="contentId") String contentId) {
        Assert.notNull((Object)hardwareKey, UpdateHardwareMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
        Hardware hardware = this.getHardware(hardwareKey);
        hardware.setContentId(contentId);
        this.hardwareService.updateHardware(hardware);
    }

    @DeleteMapping(value={"/{hardwareKey}"})
    public void deleteHardware(@PathVariable(value="hardwareKey") String hardwareKey) {
        Assert.notNull((Object)hardwareKey, DeleteHardwareMessages.HARDWARE_KEY_CAN_NOT_BE_NULL);
        this.hardwareService.deleteHardware(hardwareKey);
    }
}

