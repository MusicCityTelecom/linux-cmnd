/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.AutoSignalDetecting;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.Failovers;
import be.tpvision.smartcontrol.domain.device_settings.input_sources.InputSource;
import be.tpvision.smartcontrol.messages.services.input_sources.ConstructorMessages;
import be.tpvision.smartcontrol.messages.services.input_sources.SetAutoSignalDetectingMessages;
import be.tpvision.smartcontrol.messages.services.input_sources.SetFailoversMessages;
import be.tpvision.smartcontrol.messages.services.input_sources.SetInputSourceMessages;
import be.tpvision.smartcontrol.protocol.sicp.Command;
import be.tpvision.smartcontrol.service.CommandService;
import be.tpvision.smartcontrol.service.InputSourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@Profile(value={"production"})
public class InputSourcesServiceImpl
implements InputSourcesService {
    private final CommandService commandService;

    @Autowired
    InputSourcesServiceImpl(CommandService commandService) {
        Assert.notNull((Object)commandService, ConstructorMessages.COMMAND_SERVICE_CAN_NOT_BE_NULL);
        this.commandService = commandService;
    }

    @Override
    public InputSource getInputSource(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.INPUT_SOURCE, InputSource.class);
    }

    @Override
    public void setInputSource(Device device, InputSource inputSource) {
        Assert.notNull((Object)device, SetInputSourceMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)inputSource, SetInputSourceMessages.INPUT_SOURCE_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.INPUT_SOURCE, inputSource);
    }

    @Override
    public AutoSignalDetecting getAutoSignalDetecting(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.AUTO_SIGNAL_DETECTING, AutoSignalDetecting.class);
    }

    @Override
    public void setAutoSignalDetecting(Device device, AutoSignalDetecting autoSignalDetecting) {
        Assert.notNull((Object)device, SetAutoSignalDetectingMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)autoSignalDetecting, SetAutoSignalDetectingMessages.AUTO_SIGNAL_DETECTING_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.AUTO_SIGNAL_DETECTING, autoSignalDetecting);
    }

    @Override
    public Failovers getFailovers(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.FAILOVERS, Failovers.class);
    }

    @Override
    public void setFailovers(Device device, Failovers failovers) {
        Assert.notNull((Object)device, SetFailoversMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)failovers, SetFailoversMessages.FAILOVERS_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.FAILOVERS, failovers);
    }
}

