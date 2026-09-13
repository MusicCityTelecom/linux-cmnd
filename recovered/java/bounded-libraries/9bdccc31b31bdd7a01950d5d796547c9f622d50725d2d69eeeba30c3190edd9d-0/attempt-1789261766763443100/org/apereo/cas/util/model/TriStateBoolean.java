/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonParser
 *  com.fasterxml.jackson.databind.DeserializationContext
 *  com.fasterxml.jackson.databind.JsonDeserializer
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 */
package org.apereo.cas.util.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.io.Serializable;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;

public enum TriStateBoolean implements Serializable
{
    TRUE(Boolean.TRUE),
    FALSE(Boolean.FALSE),
    UNDEFINED(null);

    private static final long serialVersionUID = -145819796564884951L;
    private final Boolean state;

    public static TriStateBoolean fromBoolean(boolean value) {
        return value ? TRUE : FALSE;
    }

    public boolean isTrue() {
        return Boolean.TRUE.equals(this.state);
    }

    public boolean isFalse() {
        return Boolean.FALSE.equals(this.state);
    }

    public boolean isUndefined() {
        return this.state == null;
    }

    public Boolean toBoolean() {
        return this.state;
    }

    @Generated
    private TriStateBoolean(Boolean state) {
        this.state = state;
    }

    public static class Deserializer
    extends JsonDeserializer<TriStateBoolean> {
        public TriStateBoolean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String value = jsonParser.getText();
            if (StringUtils.equalsIgnoreCase((CharSequence)value, (CharSequence)Boolean.TRUE.toString())) {
                return TRUE;
            }
            if (StringUtils.equalsIgnoreCase((CharSequence)value, (CharSequence)Boolean.FALSE.toString())) {
                return FALSE;
            }
            return TriStateBoolean.valueOf(value);
        }
    }
}

