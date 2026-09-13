/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.mappers.twa;

import be.tpvision.smartcontrol.domain.twa.Brightness;
import be.tpvision.smartcontrol.messages.mappers.twa.brightness.ToBrightnessMessages;
import be.tpvision.smartcontrol.rest.view_models.twa.IntegerViewModel;
import org.springframework.util.Assert;

public class BrightnessMapper {
    private BrightnessMapper() {
    }

    public static Brightness toBrightness(IntegerViewModel integerViewModel) {
        Assert.notNull((Object)integerViewModel, ToBrightnessMessages.INTEGER_VIEW_MODEL_CAN_NOT_BE_NULL);
        Integer viewModelBrightness = integerViewModel.getValue();
        Assert.state(viewModelBrightness != null, ToBrightnessMessages.VIEW_MODEL_BRIGHTNESS_CAN_NOT_BE_NULL);
        double base = 655.35;
        int domainBrightness = (int)(655.35 * (double)viewModelBrightness.intValue());
        return new Brightness(domainBrightness, domainBrightness, domainBrightness);
    }
}

