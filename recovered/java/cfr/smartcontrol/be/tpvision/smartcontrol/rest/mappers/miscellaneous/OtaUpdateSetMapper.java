/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.mappers.miscellaneous;

import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.OtaImageType;
import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.OtaUpdateSet;
import be.tpvision.smartcontrol.rest.view_models.miscellaneous.OtaUpdateSetViewModel;
import be.tpvision.smartcontrol.util.ValueUtilities;

public class OtaUpdateSetMapper {
    private OtaUpdateSetMapper() {
    }

    public static OtaUpdateSet toOtaUpdateSet(OtaUpdateSetViewModel otaUpdateSetViewModel) {
        String imageTypeString = otaUpdateSetViewModel.getImageType();
        OtaImageType otaImageType = ValueUtilities.getEnumValue(OtaImageType.class, imageTypeString);
        String statusString = otaUpdateSetViewModel.getStatus();
        OtaUpdateSet.Status status = ValueUtilities.getEnumValue(OtaUpdateSet.Status.class, statusString);
        return new OtaUpdateSet(otaImageType, status);
    }
}

