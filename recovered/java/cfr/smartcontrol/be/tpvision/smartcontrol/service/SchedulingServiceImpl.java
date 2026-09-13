/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.device_settings.IntWrapper;
import be.tpvision.smartcontrol.domain.device_settings.scheduling.scheduling_parameters.Page;
import be.tpvision.smartcontrol.messages.services.scheduling.scheduling_parameters.ConstructorMessages;
import be.tpvision.smartcontrol.messages.services.scheduling.scheduling_parameters.SetSchedulingParametersMessages;
import be.tpvision.smartcontrol.protocol.sicp.Command;
import be.tpvision.smartcontrol.service.CommandService;
import be.tpvision.smartcontrol.service.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class SchedulingServiceImpl
implements SchedulingService {
    private final CommandService commandService;

    @Autowired
    SchedulingServiceImpl(CommandService commandService) {
        Assert.notNull((Object)commandService, ConstructorMessages.COMMAND_SERVICE_CAN_NOT_BE_NULL);
        this.commandService = commandService;
    }

    @Override
    public Page getSchedulingParametersPage(Device device, IntWrapper pageNumber) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.SCHEDULING_PARAMETERS, pageNumber, Page.class);
    }

    @Override
    public void setSchedulingParametersPage(Device device, Page page) {
        Assert.notNull((Object)device, SetSchedulingParametersMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)page, SetSchedulingParametersMessages.PAGE_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.SCHEDULING_PARAMETERS, page);
    }
}

