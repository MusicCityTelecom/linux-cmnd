/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.mappers.audio;

import be.tpvision.smartcontrol.domain.device_settings.audio.VolumeLimits;
import be.tpvision.smartcontrol.messages.mappers.audio.volume_limits.ToVolumeLimitsMessages;
import be.tpvision.smartcontrol.messages.mappers.audio.volume_limits.ToVolumeLimitsViewModelMessages;
import be.tpvision.smartcontrol.rest.view_models.audio.VolumeLimitsViewModel;
import org.springframework.util.Assert;

public class VolumeLimitsMapper {
    private VolumeLimitsMapper() {
    }

    public static VolumeLimitsViewModel toVolumeLimitsViewModel(VolumeLimits volumeLimits) {
        Assert.notNull((Object)volumeLimits, ToVolumeLimitsViewModelMessages.VOLUME_LIMITS_CAN_NOT_BE_NULL);
        int minimum = volumeLimits.getMinimum();
        int maximum = volumeLimits.getMaximum();
        int switchOn = volumeLimits.getSwitchOn();
        return new VolumeLimitsViewModel(minimum, maximum, switchOn);
    }

    public static VolumeLimits toVolumeLimits(VolumeLimitsViewModel volumeLimitsViewModel) {
        Assert.notNull((Object)volumeLimitsViewModel, ToVolumeLimitsMessages.VOLUME_LIMITS_VIEW_MODEL_CAN_NOT_BE_NULL);
        int minimum = volumeLimitsViewModel.getMinimum();
        int maximum = volumeLimitsViewModel.getMaximum();
        int switchOn = volumeLimitsViewModel.getSwitchOn();
        return new VolumeLimits(minimum, maximum, switchOn);
    }
}

