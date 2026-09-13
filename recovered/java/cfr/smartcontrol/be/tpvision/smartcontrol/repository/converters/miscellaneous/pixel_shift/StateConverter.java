/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.repository.converters.miscellaneous.pixel_shift;

import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.PixelShift;
import be.tpvision.smartcontrol.repository.converters.EnumConverter;
import javax.persistence.Converter;

@Converter
public class StateConverter
extends EnumConverter<PixelShift.State> {
    public StateConverter() {
        super(PixelShift.State.class);
    }
}

