/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationAccountStateHandler
 *  org.apereo.cas.configuration.model.core.authentication.PasswordPolicyProperties
 */
package org.apereo.cas.authentication.support.password;

import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationAccountStateHandler;
import org.apereo.cas.configuration.model.core.authentication.PasswordPolicyProperties;

public class PasswordPolicyContext {
    private AuthenticationAccountStateHandler accountStateHandler;
    private boolean alwaysDisplayPasswordExpirationWarning;
    private int passwordWarningNumberOfDays = 30;
    private int loginFailures = 5;

    public PasswordPolicyContext(int passwordWarningNumberOfDays) {
        this.passwordWarningNumberOfDays = passwordWarningNumberOfDays;
    }

    public PasswordPolicyContext(PasswordPolicyProperties props) {
        this(null, props.isWarnAll(), props.getWarningDays(), props.getLoginFailures());
    }

    @Generated
    public void setAccountStateHandler(AuthenticationAccountStateHandler accountStateHandler) {
        this.accountStateHandler = accountStateHandler;
    }

    @Generated
    public void setAlwaysDisplayPasswordExpirationWarning(boolean alwaysDisplayPasswordExpirationWarning) {
        this.alwaysDisplayPasswordExpirationWarning = alwaysDisplayPasswordExpirationWarning;
    }

    @Generated
    public void setPasswordWarningNumberOfDays(int passwordWarningNumberOfDays) {
        this.passwordWarningNumberOfDays = passwordWarningNumberOfDays;
    }

    @Generated
    public void setLoginFailures(int loginFailures) {
        this.loginFailures = loginFailures;
    }

    @Generated
    public PasswordPolicyContext() {
    }

    @Generated
    public AuthenticationAccountStateHandler getAccountStateHandler() {
        return this.accountStateHandler;
    }

    @Generated
    public boolean isAlwaysDisplayPasswordExpirationWarning() {
        return this.alwaysDisplayPasswordExpirationWarning;
    }

    @Generated
    public int getPasswordWarningNumberOfDays() {
        return this.passwordWarningNumberOfDays;
    }

    @Generated
    public int getLoginFailures() {
        return this.loginFailures;
    }

    @Generated
    public PasswordPolicyContext(AuthenticationAccountStateHandler accountStateHandler, boolean alwaysDisplayPasswordExpirationWarning, int passwordWarningNumberOfDays, int loginFailures) {
        this.accountStateHandler = accountStateHandler;
        this.alwaysDisplayPasswordExpirationWarning = alwaysDisplayPasswordExpirationWarning;
        this.passwordWarningNumberOfDays = passwordWarningNumberOfDays;
        this.loginFailures = loginFailures;
    }
}

