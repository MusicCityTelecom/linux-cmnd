/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.pac4j.oidc;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.pac4j.oidc.BasePac4jOidcClientProperties;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-pac4j-webflow")
@JsonFilter(value="Pac4jAppleOidcClientProperties")
public class Pac4jAppleOidcClientProperties
extends BasePac4jOidcClientProperties {
    private static final long serialVersionUID = 2258382317533639638L;
    @DurationCapable
    private String timeout = "PT30S";
    private String privateKeyId;
    private String teamId;
    private String privateKey;

    @Generated
    public String getTimeout() {
        return this.timeout;
    }

    @Generated
    public String getPrivateKeyId() {
        return this.privateKeyId;
    }

    @Generated
    public String getTeamId() {
        return this.teamId;
    }

    @Generated
    public String getPrivateKey() {
        return this.privateKey;
    }

    @Generated
    public Pac4jAppleOidcClientProperties setTimeout(String timeout) {
        this.timeout = timeout;
        return this;
    }

    @Generated
    public Pac4jAppleOidcClientProperties setPrivateKeyId(String privateKeyId) {
        this.privateKeyId = privateKeyId;
        return this;
    }

    @Generated
    public Pac4jAppleOidcClientProperties setTeamId(String teamId) {
        this.teamId = teamId;
        return this;
    }

    @Generated
    public Pac4jAppleOidcClientProperties setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
        return this;
    }
}

