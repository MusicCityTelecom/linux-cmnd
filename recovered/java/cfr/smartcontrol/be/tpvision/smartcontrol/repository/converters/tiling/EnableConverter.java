/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.repository.converters.tiling;

import be.tpvision.smartcontrol.domain.device_settings.miscellaneous.Tiling;
import be.tpvision.smartcontrol.repository.converters.EnumConverter;
import javax.persistence.Converter;

@Converter
public class EnableConverter
extends EnumConverter<Tiling.Enable> {
    public EnableConverter() {
        super(Tiling.Enable.class);
    }
}

