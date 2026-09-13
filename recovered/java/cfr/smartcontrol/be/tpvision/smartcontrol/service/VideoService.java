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

public interface VideoService {
    public VideoParameters getVideoParameters(Device var1);

    public void setVideoParameters(Device var1, VideoParameters var2);

    public ColorTemperature getColorTemperature(Device var1);

    public void setColorTemperature(Device var1, ColorTemperature var2);

    public ColorParameters getColorParameters(Device var1);

    public void setColorParameters(Device var1, ColorParameters var2);

    public ColorTemperature100K getColorTemperature100K(Device var1);

    public void setColorTemperature100K(Device var1, ColorTemperature100K var2);

    public PictureFormat getPictureFormat(Device var1);

    public void setPictureFormat(Device var1, PictureFormat var2);

    public VGAVideoParameters getVGAVideoParameters(Device var1);

    public void setVGAVideoParameters(Device var1, VGAVideoParameters var2);

    public PictureInPicture getPictureInPicture(Device var1);

    public void setPictureInPicture(Device var1, PictureInPicture var2);

    public PictureInPictureSource getPictureInPictureSource(Device var1);

    public void setPictureInPictureSource(Device var1, PictureInPictureSource var2);

    public PictureStyle getPictureStyle(Device var1);

    public void setPictureStyle(Device var1, PictureStyle var2);
}

