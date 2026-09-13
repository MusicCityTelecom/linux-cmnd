/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.core.convert.converter.Converter
 */
package org.apereo.cas.util.spring;

import java.time.ZonedDateTime;
import lombok.Generated;
import org.springframework.core.convert.converter.Converter;

public class Converters {
    @Generated
    public Converters() {
    }

    public static class ZonedDateTimeToStringConverter
    implements Converter<ZonedDateTime, String> {
        public String convert(ZonedDateTime zonedDateTime) {
            return zonedDateTime.toString();
        }
    }
}

