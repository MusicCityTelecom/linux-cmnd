/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.pm;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-pm-webflow")
@JsonFilter(value="PasswordManagementCoreProperties")
public class PasswordManagementCoreProperties
implements Serializable {
    private static final long serialVersionUID = -261644582798411176L;
    @RequiredProperty
    private boolean enabled;
    private boolean autoLogin;
    @RequiredProperty
    private String passwordPolicyPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,10}";

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public boolean isAutoLogin() {
        return this.autoLogin;
    }

    @Generated
    public String getPasswordPolicyPattern() {
        return this.passwordPolicyPattern;
    }

    @Generated
    public PasswordManagementCoreProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public PasswordManagementCoreProperties setAutoLogin(boolean autoLogin) {
        this.autoLogin = autoLogin;
        return this;
    }

    @Generated
    public PasswordManagementCoreProperties setPasswordPolicyPattern(String passwordPolicyPattern) {
        this.passwordPolicyPattern = passwordPolicyPattern;
        return this;
    }

    @Generated
    public PasswordManagementCoreProperties() {
    }
}

