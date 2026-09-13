/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.sms;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-sms-twilio")
@JsonFilter(value="TwilioProperties")
public class TwilioProperties
implements Serializable {
    private static final long serialVersionUID = -7043132225482495229L;
    @RequiredProperty
    private String accountId;
    @RequiredProperty
    private String token;

    @Generated
    public String getAccountId() {
        return this.accountId;
    }

    @Generated
    public String getToken() {
        return this.token;
    }

    @Generated
    public TwilioProperties setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    @Generated
    public TwilioProperties setToken(String token) {
        this.token = token;
        return this;
    }
}

