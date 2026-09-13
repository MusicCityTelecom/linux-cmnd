/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  lombok.Generated
 *  org.apereo.cas.authentication.MessageDescriptor
 */
package org.apereo.cas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Arrays;
import lombok.Generated;
import org.apereo.cas.authentication.MessageDescriptor;

public class DefaultMessageDescriptor
implements MessageDescriptor {
    private static final long serialVersionUID = 1227390629186486032L;
    private final String code;
    private final String defaultMessage;
    private final Serializable[] params;

    @JsonCreator
    public DefaultMessageDescriptor(@JsonProperty(value="code") String code) {
        this(code, code, null);
    }

    @Generated
    public String getCode() {
        return this.code;
    }

    @Generated
    public String getDefaultMessage() {
        return this.defaultMessage;
    }

    @Generated
    public Serializable[] getParams() {
        return this.params;
    }

    @Generated
    public DefaultMessageDescriptor(String code, String defaultMessage, Serializable[] params) {
        this.code = code;
        this.defaultMessage = defaultMessage;
        this.params = params;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DefaultMessageDescriptor)) {
            return false;
        }
        DefaultMessageDescriptor other = (DefaultMessageDescriptor)o;
        if (!other.canEqual(this)) {
            return false;
        }
        String this$code = this.code;
        String other$code = other.code;
        if (this$code == null ? other$code != null : !this$code.equals(other$code)) {
            return false;
        }
        String this$defaultMessage = this.defaultMessage;
        String other$defaultMessage = other.defaultMessage;
        if (this$defaultMessage == null ? other$defaultMessage != null : !this$defaultMessage.equals(other$defaultMessage)) {
            return false;
        }
        return Arrays.deepEquals(this.params, other.params);
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DefaultMessageDescriptor;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $code = this.code;
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        String $defaultMessage = this.defaultMessage;
        result = result * 59 + ($defaultMessage == null ? 43 : $defaultMessage.hashCode());
        result = result * 59 + Arrays.deepHashCode(this.params);
        return result;
    }
}

