/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.springframework.security.authentication.jaas;

import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextLoginModule
implements LoginModule {
    private static final Log log = LogFactory.getLog(SecurityContextLoginModule.class);
    private Authentication authen;
    private Subject subject;
    private boolean ignoreMissingAuthentication = false;

    @Override
    public boolean abort() {
        if (this.authen == null) {
            return false;
        }
        this.authen = null;
        return true;
    }

    @Override
    public boolean commit() {
        if (this.authen == null) {
            return false;
        }
        this.subject.getPrincipals().add(this.authen);
        return true;
    }

    Authentication getAuthentication() {
        return this.authen;
    }

    Subject getSubject() {
        return this.subject;
    }

    public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options) {
        this.subject = subject;
        if (options != null) {
            this.ignoreMissingAuthentication = "true".equals(options.get("ignoreMissingAuthentication"));
        }
    }

    @Override
    public boolean login() throws LoginException {
        this.authen = SecurityContextHolder.getContext().getAuthentication();
        if (this.authen != null) {
            return true;
        }
        String msg = "Login cannot complete, authentication not found in security context";
        if (!this.ignoreMissingAuthentication) {
            throw new LoginException(msg);
        }
        log.warn((Object)msg);
        return false;
    }

    @Override
    public boolean logout() {
        if (this.authen == null) {
            return false;
        }
        this.subject.getPrincipals().remove(this.authen);
        this.authen = null;
        return true;
    }
}

