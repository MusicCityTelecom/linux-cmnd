/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.repository.converters.content;

import be.tpvision.smartcontrol.domain.Content;
import be.tpvision.smartcontrol.repository.converters.EnumConverter;
import javax.persistence.Converter;
import org.springframework.stereotype.Component;

@Converter
@Component
public class OrientationConverter
extends EnumConverter<Content.Orientation> {
    public OrientationConverter() {
        super(Content.Orientation.class);
    }
}

