/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.service;

import be.tpvision.smartcontrol.domain.Device;
import be.tpvision.smartcontrol.domain.device_settings.video.ColorParameters;
import be.tpvision.smartcontrol.domain.device_settings.video.ColorTemperature;
import be.tpvision.smartcontrol.domain.device_settings.video.ColorTemperature100K;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureFormat;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureInPicture;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureInPictureSource;
import be.tpvision.smartcontrol.domain.device_settings.video.PictureStyle;
import be.tpvision.smartcontrol.domain.device_settings.video.VGAVideoParameters;
import be.tpvision.smartcontrol.domain.device_settings.video.VideoParameters;
import be.tpvision.smartcontrol.messages.services.video.ConstructorMessages;
import be.tpvision.smartcontrol.messages.services.video.SetColorParametersMessages;
import be.tpvision.smartcontrol.messages.services.video.SetColorTemperature100kMessages;
import be.tpvision.smartcontrol.messages.services.video.SetColorTemperatureMessages;
import be.tpvision.smartcontrol.messages.services.video.SetPictureFormatMessages;
import be.tpvision.smartcontrol.messages.services.video.SetPictureInPictureMessages;
import be.tpvision.smartcontrol.messages.services.video.SetPictureInPictureSourceMessages;
import be.tpvision.smartcontrol.messages.services.video.SetPictureStyleMessages;
import be.tpvision.smartcontrol.messages.services.video.SetVgaVideoParametersMessages;
import be.tpvision.smartcontrol.messages.services.video.SetVideoParametersMessages;
import be.tpvision.smartcontrol.protocol.sicp.Command;
import be.tpvision.smartcontrol.service.CommandService;
import be.tpvision.smartcontrol.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@Profile(value={"production"})
public class VideoServiceImpl
implements VideoService {
    private final CommandService commandService;

    @Autowired
    VideoServiceImpl(CommandService commandService) {
        Assert.notNull((Object)commandService, ConstructorMessages.COMMAND_SERVICE_CAN_NOT_BE_NULL);
        this.commandService = commandService;
    }

    @Override
    public VideoParameters getVideoParameters(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.VIDEO_PARAMETERS, VideoParameters.class);
    }

    @Override
    public void setVideoParameters(Device device, VideoParameters videoParameters) {
        Assert.notNull((Object)device, SetVideoParametersMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)videoParameters, SetVideoParametersMessages.VIDEO_PARAMETERS_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.VIDEO_PARAMETERS, videoParameters);
    }

    @Override
    public ColorTemperature getColorTemperature(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.COLOR_TEMPERATURE, ColorTemperature.class);
    }

    @Override
    public void setColorTemperature(Device device, ColorTemperature colorTemperature) {
        Assert.notNull((Object)device, SetColorTemperatureMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)colorTemperature, SetColorTemperatureMessages.COLOR_TEMPERATURE_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.COLOR_TEMPERATURE, colorTemperature);
    }

    @Override
    public ColorParameters getColorParameters(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.COLOR_PARAMETERS, ColorParameters.class);
    }

    @Override
    public void setColorParameters(Device device, ColorParameters colorParameters) {
        Assert.notNull((Object)device, SetColorParametersMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)colorParameters, SetColorParametersMessages.COLOR_PARAMETERS_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.COLOR_PARAMETERS, colorParameters);
    }

    @Override
    public ColorTemperature100K getColorTemperature100K(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.COLOR_TEMPERATURE_100K, ColorTemperature100K.class);
    }

    @Override
    public void setColorTemperature100K(Device device, ColorTemperature100K colorTemperature100K) {
        Assert.notNull((Object)device, SetColorTemperature100kMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)colorTemperature100K, SetColorTemperature100kMessages.COLOR_TEMPERATURE_100K_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.COLOR_TEMPERATURE_100K, colorTemperature100K);
    }

    @Override
    public PictureFormat getPictureFormat(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.PICTURE_FORMAT, PictureFormat.class);
    }

    @Override
    public void setPictureFormat(Device device, PictureFormat pictureFormat) {
        Assert.notNull((Object)device, SetPictureFormatMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)pictureFormat, SetPictureFormatMessages.PICTURE_FORMAT_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.PICTURE_FORMAT, pictureFormat);
    }

    @Override
    public VGAVideoParameters getVGAVideoParameters(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.VGA_VIDEO_PARAMETERS, VGAVideoParameters.class);
    }

    @Override
    public void setVGAVideoParameters(Device device, VGAVideoParameters vgaVideoParameters) {
        Assert.notNull((Object)device, SetVgaVideoParametersMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)vgaVideoParameters, SetVgaVideoParametersMessages.VGA_VIDEO_PARAMETERS_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.VGA_VIDEO_PARAMETERS, vgaVideoParameters);
    }

    @Override
    public PictureInPicture getPictureInPicture(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.PICTURE_IN_PICTURE, PictureInPicture.class);
    }

    @Override
    public void setPictureInPicture(Device device, PictureInPicture pictureInPicture) {
        Assert.notNull((Object)device, SetPictureInPictureMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)pictureInPicture, SetPictureInPictureMessages.PICTURE_IN_PICTURE_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.PICTURE_IN_PICTURE, pictureInPicture);
    }

    @Override
    public PictureInPictureSource getPictureInPictureSource(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.PICTURE_IN_PICTURE_SOURCE, PictureInPictureSource.class);
    }

    @Override
    public void setPictureInPictureSource(Device device, PictureInPictureSource pictureInPictureSource) {
        Assert.notNull((Object)device, SetPictureInPictureSourceMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)pictureInPictureSource, SetPictureInPictureSourceMessages.PICTURE_IN_PICTURE_SOURCE_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.PICTURE_IN_PICTURE_SOURCE, pictureInPictureSource);
    }

    @Override
    public PictureStyle getPictureStyle(Device device) {
        if (device == null) {
            return null;
        }
        return this.commandService.send(device, Command.Type.GET, Command.Setting.PICTURE_STYLE, PictureStyle.class);
    }

    @Override
    public void setPictureStyle(Device device, PictureStyle pictureStyle) {
        Assert.notNull((Object)device, SetPictureStyleMessages.DEVICE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)pictureStyle, SetPictureStyleMessages.PICTURE_STYLE_CAN_NOT_BE_NULL);
        this.commandService.send(device, Command.Type.SET, Command.Setting.PICTURE_STYLE, pictureStyle);
    }
}

