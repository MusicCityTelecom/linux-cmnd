/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.mappers.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.LedStrips;
import be.tpvision.smartcontrol.messages.mappers.miscellaneous.led_strips.ToLedStripsMessages;
import be.tpvision.smartcontrol.messages.mappers.miscellaneous.led_strips.ToLedStripsViewModelMessages;
import be.tpvision.smartcontrol.rest.view_models.miscellaneous.LedStripsViewModel;
import be.tpvision.smartcontrol.util.ValueUtilities;
import org.springframework.util.Assert;

public class LedStripsMapper {
    private LedStripsMapper() {
    }

    public static LedStripsViewModel toLedStripsViewModel(LedStrips ledStrips) {
        Assert.notNull((Object)ledStrips, ToLedStripsViewModelMessages.LED_STRIPS_CAN_NOT_BE_NULL_MESSAGE);
        LedStrips.Status domainStatus = ledStrips.getStatus();
        String viewModelStatus = String.valueOf((Object)domainStatus);
        int redValue = ledStrips.getRedValue();
        int greenValue = ledStrips.getGreenValue();
        int blueValue = ledStrips.getBlueValue();
        return new LedStripsViewModel(viewModelStatus, redValue, greenValue, blueValue);
    }

    public static LedStrips toLedStrips(LedStripsViewModel ledStripsViewModel) {
        Assert.notNull((Object)ledStripsViewModel, ToLedStripsMessages.LED_STRIPS_VIEW_MODEL_CAN_NOT_BE_NULL);
        String viewModelStatus = ledStripsViewModel.getStatus();
        LedStrips.Status domainStatus = ValueUtilities.getEnumValue(LedStrips.Status.class, viewModelStatus);
        int redValue = ledStripsViewModel.getRedValue();
        int blueValue = ledStripsViewModel.getBlueValue();
        int greenValue = ledStripsViewModel.getGreenValue();
        return new LedStrips(domainStatus, redValue, greenValue, blueValue);
    }
}

