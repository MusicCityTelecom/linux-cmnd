/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.sms;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-sms-nexmo")
public class NexmoProperties
implements Serializable {
    private static final long serialVersionUID = 7546596773588579321L;
    @RequiredProperty
    private String apiToken;
    private String apiSecret;
    private String signatureSecret;

    @Generated
    public String getApiToken() {
        return this.apiToken;
    }

    @Generated
    public String getApiSecret() {
        return this.apiSecret;
    }

    @Generated
    public String getSignatureSecret() {
        return this.signatureSecret;
    }

    @Generated
    public NexmoProperties setApiToken(String apiToken) {
        this.apiToken = apiToken;
        return this;
    }

    @Generated
    public NexmoProperties setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
        return this;
    }

    @Generated
    public NexmoProperties setSignatureSecret(String signatureSecret) {
        this.signatureSecret = signatureSecret;
        return this;
    }
}

