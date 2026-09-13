/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.scim;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-scim")
public class ScimProperties
implements Serializable {
    private static final long serialVersionUID = 7943229230342691009L;
    private boolean enabled = true;
    private long version = 2L;
    @RequiredProperty
    private String target;
    @RequiredProperty
    private String oauthToken;
    @RequiredProperty
    private String username;
    @RequiredProperty
    private String password;

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public long getVersion() {
        return this.version;
    }

    @Generated
    public String getTarget() {
        return this.target;
    }

    @Generated
    public String getOauthToken() {
        return this.oauthToken;
    }

    @Generated
    public String getUsername() {
        return this.username;
    }

    @Generated
    public String getPassword() {
        return this.password;
    }

    @Generated
    public ScimProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public ScimProperties setVersion(long version) {
        this.version = version;
        return this;
    }

    @Generated
    public ScimProperties setTarget(String target) {
        this.target = target;
        return this;
    }

    @Generated
    public ScimProperties setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
        return this;
    }

    @Generated
    public ScimProperties setUsername(String username) {
        this.username = username;
        return this;
    }

    @Generated
    public ScimProperties setPassword(String password) {
        this.password = password;
        return this;
    }
}

