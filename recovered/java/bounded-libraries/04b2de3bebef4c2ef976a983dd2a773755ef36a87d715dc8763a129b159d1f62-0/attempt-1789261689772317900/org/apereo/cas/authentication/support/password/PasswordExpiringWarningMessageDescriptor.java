/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  org.apereo.cas.DefaultMessageDescriptor
 */
package org.apereo.cas.authentication.support.password;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import org.apereo.cas.DefaultMessageDescriptor;

public class PasswordExpiringWarningMessageDescriptor
extends DefaultMessageDescriptor {
    private static final long serialVersionUID = -5892600936676838470L;
    private static final String CODE = "password.expiration.warning";

    @JsonCreator
    public PasswordExpiringWarningMessageDescriptor(@JsonProperty(value="message") String message, @JsonProperty(value="days") long days) {
        super(CODE, message, new Serializable[]{Long.valueOf(days)});
    }

    public long getDaysToExpiration() {
        return (Long)this.getParams()[0];
    }
}

