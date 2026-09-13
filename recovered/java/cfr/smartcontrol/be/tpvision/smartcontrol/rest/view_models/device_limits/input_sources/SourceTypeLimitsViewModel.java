/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.view_models.device_limits.input_sources;

import be.tpvision.smartcontrol.domain.device_settings.input_sources.InputSource;
import be.tpvision.smartcontrol.rest.view_models.device_limits.EnumLimitsViewModel;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SourceTypeLimitsViewModel
extends EnumLimitsViewModel<InputSource.SourceType> {
    public SourceTypeLimitsViewModel() {
        super(InputSource.SourceType.class, Stream.of(InputSource.SourceType.DISPLAY_PORT_1, InputSource.SourceType.DVI_D, InputSource.SourceType.VGA, InputSource.SourceType.HDMI_1, InputSource.SourceType.HDMI_2, InputSource.SourceType.HDMI_3, InputSource.SourceType.HDMI_4, InputSource.SourceType.MEDIA_PLAYER, InputSource.SourceType.BROWSER, InputSource.SourceType.SMART_CMS, InputSource.SourceType.PDF_PLAYER, InputSource.SourceType.CUSTOM, InputSource.SourceType.VIDEO, InputSource.SourceType.S_VIDEO, InputSource.SourceType.COMPONENT, InputSource.SourceType.CVI_2, InputSource.SourceType.DISPLAY_PORT_2, InputSource.SourceType.USB_1, InputSource.SourceType.USB_2, InputSource.SourceType.CARD_DVI_D, InputSource.SourceType.CARD_OPS, InputSource.SourceType.DIGITAL_MEDIA_SERVER, InputSource.SourceType.INTERNAL_STORAGE, InputSource.SourceType.VGA_2, InputSource.SourceType.VGA_3, InputSource.SourceType.IWB, InputSource.SourceType.RESERVED_1, InputSource.SourceType.RESERVED_2, InputSource.SourceType.CMND_PLAY_WEB, InputSource.SourceType.HOME_LAUNCHER, InputSource.SourceType.USB_TYPEC, InputSource.SourceType.KIOSK, InputSource.SourceType.SMART_INFO, InputSource.SourceType.TUNER, InputSource.SourceType.GOOGLE_CAST).map(String::valueOf).collect(Collectors.toList()));
    }
}

