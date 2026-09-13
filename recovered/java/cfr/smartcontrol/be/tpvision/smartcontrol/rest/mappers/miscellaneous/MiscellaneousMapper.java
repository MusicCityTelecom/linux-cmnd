/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.mappers.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Miscellaneous;
import be.tpvision.smartcontrol.messages.mappers.miscellaneous.miscellaneous.ToMiscellaneousViewModelMessages;
import be.tpvision.smartcontrol.rest.view_models.miscellaneous.MiscellaneousViewModel;
import org.springframework.util.Assert;

public class MiscellaneousMapper {
    private MiscellaneousMapper() {
    }

    public static MiscellaneousViewModel toMiscellaneousViewModel(Miscellaneous miscellaneous) {
        Assert.notNull((Object)miscellaneous, ToMiscellaneousViewModelMessages.MISCELLANEOUS_CAN_NOT_BE_NULL);
        int operatingHours = miscellaneous.getOperatingHours();
        return new MiscellaneousViewModel(operatingHours);
    }
}

