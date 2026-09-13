/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;
import be.tpvision.smartcontrol.domain.device_settings.system.ModelNumberFirmwareVersionBuildDateInfo;
import be.tpvision.smartcontrol.domain.device_settings.system.SicpAndPlatformInfo;
import be.tpvision.smartcontrol.messages.services.system.ConstructorMessages;
import be.tpvision.smartcontrol.protocol.sicp.Command;
import be.tpvision.smartcontrol.service.CommandService;
import be.tpvision.smartcontrol.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@Profile(value={"production"})
public class SystemServiceImpl
implements SystemService {
    private final CommandService commandService;

    @Autowired
    public SystemServiceImpl(CommandService commandService) {
        Assert.notNull((Object)commandService, ConstructorMessages.COMMAND_SERVICE_CAN_NOT_BE_NULL);
        this.commandService = commandService;
    }

    private StringWrapper send(Device device, SicpAndPlatformInfo sicpAndPlatformInfo) {
        if (device == null || sicpAndPlatformInfo == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.SICP_VERSION_AND_PLATFORM_INFO, sicpAndPlatformInfo, StringWrapper.class);
    }

    @Override
    public StringWrapper getSICPVersion(Device device) {
        if (device == null) {
            return null;
        }
        return this.send(device, SicpAndPlatformInfo.SICP_VERSION);
    }

    @Override
    public StringWrapper getPlatformLabel(Device device) {
        if (device == null) {
            return null;
        }
        return this.send(device, SicpAndPlatformInfo.PLATFORM_LABEL);
    }

    @Override
    public StringWrapper getPlatformVersion(Device device) {
        if (device == null) {
            return null;
        }
        return this.send(device, SicpAndPlatformInfo.PLATFORM_VERSION);
    }

    private StringWrapper send(Device device, ModelNumberFirmwareVersionBuildDateInfo modelNumberFirmwareVersionBuildDateInfo) {
        if (device == null || modelNumberFirmwareVersionBuildDateInfo == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.MODEL_NUMBER_FIRMWARE_VERSION_BUILD_DATE, modelNumberFirmwareVersionBuildDateInfo, StringWrapper.class);
    }

    @Override
    public StringWrapper getModelNumber(Device device) {
        if (device == null) {
            return null;
        }
        return this.send(device, ModelNumberFirmwareVersionBuildDateInfo.MODEL_NUMBER);
    }

    @Override
    public StringWrapper getFirmwareVersion(Device device) {
        if (device == null) {
            return null;
        }
        return this.send(device, ModelNumberFirmwareVersionBuildDateInfo.FIRMWARE_VERSION);
    }

    @Override
    public StringWrapper getBuildDate(Device device) {
        if (device == null) {
            return null;
        }
        return this.send(device, ModelNumberFirmwareVersionBuildDateInfo.BUILD_DATE);
    }

    @Override
    public StringWrapper getFirmwareVersionAndroid(Device device) {
        if (device == null) {
            return null;
        }
        return this.send(device, ModelNumberFirmwareVersionBuildDateInfo.FIRMWARE_VERSION_ANDROID);
    }
}

