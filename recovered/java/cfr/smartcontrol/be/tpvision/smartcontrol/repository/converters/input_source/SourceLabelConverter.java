/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.repository.converters.input_source;

import be.tpvision.smartcontrol.domain.device_settings.input_sources.InputSource;
import be.tpvision.smartcontrol.repository.converters.EnumConverter;
import javax.persistence.Converter;

@Converter
public class SourceLabelConverter
extends EnumConverter<InputSource.SourceLabel> {
    public SourceLabelConverter() {
        super(InputSource.SourceLabel.class);
    }
}

