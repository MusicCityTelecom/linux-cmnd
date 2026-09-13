/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 *  org.springframework.util.LinkedCaseInsensitiveMap
 */
package org.apereo.cas.configuration.model.core.authentication;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.Map;
import javax.security.auth.login.LoginException;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.GroovyPasswordPolicyProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.util.LinkedCaseInsensitiveMap;

@RequiresModule(name="cas-server-core-authentication", automated=true)
@JsonFilter(value="PasswordPolicyProperties")
public class PasswordPolicyProperties
implements Serializable {
    private static final long serialVersionUID = -3878237508646993100L;
    private PasswordPolicyHandlingOptions strategy = PasswordPolicyHandlingOptions.DEFAULT;
    private Map<String, Class<? extends LoginException>> policyAttributes = new LinkedCaseInsensitiveMap();
    private boolean enabled = true;
    private boolean accountStateHandlingEnabled = true;
    private int loginFailures = 5;
    private String warningAttributeValue;
    private String warningAttributeName;
    private boolean displayWarningOnMatch = true;
    private boolean warnAll;
    private int warningDays = 30;
    @NestedConfigurationProperty
    private GroovyPasswordPolicyProperties groovy = new GroovyPasswordPolicyProperties();

    @Generated
    public PasswordPolicyHandlingOptions getStrategy() {
        return this.strategy;
    }

    @Generated
    public Map<String, Class<? extends LoginException>> getPolicyAttributes() {
        return this.policyAttributes;
    }

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public boolean isAccountStateHandlingEnabled() {
        return this.accountStateHandlingEnabled;
    }

    @Generated
    public int getLoginFailures() {
        return this.loginFailures;
    }

    @Generated
    public String getWarningAttributeValue() {
        return this.warningAttributeValue;
    }

    @Generated
    public String getWarningAttributeName() {
        return this.warningAttributeName;
    }

    @Generated
    public boolean isDisplayWarningOnMatch() {
        return this.displayWarningOnMatch;
    }

    @Generated
    public boolean isWarnAll() {
        return this.warnAll;
    }

    @Generated
    public int getWarningDays() {
        return this.warningDays;
    }

    @Generated
    public GroovyPasswordPolicyProperties getGroovy() {
        return this.groovy;
    }

    @Generated
    public PasswordPolicyProperties setStrategy(PasswordPolicyHandlingOptions strategy) {
        this.strategy = strategy;
        return this;
    }

    @Generated
    public PasswordPolicyProperties setPolicyAttributes(Map<String, Class<? extends LoginException>> policyAttributes) {
        this.policyAttributes = policyAttributes;
        return this;
    }

    @Generated
    public PasswordPolicyProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public PasswordPolicyProperties setAccountStateHandlingEnabled(boolean accountStateHandlingEnabled) {
        this.accountStateHandlingEnabled = accountStateHandlingEnabled;
        return this;
    }

    @Generated
    public PasswordPolicyProperties setLoginFailures(int loginFailures) {
        this.loginFailures = loginFailures;
        return this;
    }

    @Generated
    public PasswordPolicyProperties setWarningAttributeValue(String warningAttributeValue) {
        this.warningAttributeValue = warningAttributeValue;
        return this;
    }

    @Generated
    public PasswordPolicyProperties setWarningAttributeName(String warningAttributeName) {
        this.warningAttributeName = warningAttributeName;
        return this;
    }

    @Generated
    public PasswordPolicyProperties setDisplayWarningOnMatch(boolean displayWarningOnMatch) {
        this.displayWarningOnMatch = displayWarningOnMatch;
        return this;
    }

    @Generated
    public PasswordPolicyProperties setWarnAll(boolean warnAll) {
        this.warnAll = warnAll;
        return this;
    }

    @Generated
    public PasswordPolicyProperties setWarningDays(int warningDays) {
        this.warningDays = warningDays;
        return this;
    }

    @Generated
    public PasswordPolicyProperties setGroovy(GroovyPasswordPolicyProperties groovy) {
        this.groovy = groovy;
        return this;
    }

    public static enum PasswordPolicyHandlingOptions {
        DEFAULT,
        GROOVY,
        REJECT_RESULT_CODE;

    }
}

