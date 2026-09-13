/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.AuthenticationHandlerExecutionResult
 *  org.apereo.cas.authentication.AuthenticationPasswordPolicyHandlingStrategy
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.PrincipalFactory
 *  org.apereo.cas.services.ServicesManager
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.handler.support.jaas;

import java.io.File;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.security.URIParameter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginContext;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.AuthenticationPasswordPolicyHandlingStrategy;
import org.apereo.cas.authentication.credential.UsernamePasswordCredential;
import org.apereo.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.services.ServicesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JaasAuthenticationHandler
extends AbstractUsernamePasswordAuthenticationHandler {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(JaasAuthenticationHandler.class);
    private static final String SYS_PROP_KRB5_REALM = "java.security.krb5.realm";
    private static final String SYS_PROP_KERB5_KDC = "java.security.krb5.kdc";
    private String realm = "CAS";
    private String kerberosRealmSystemProperty;
    private String kerberosKdcSystemProperty;
    private String loginConfigType;
    private File loginConfigurationFile;

    public JaasAuthenticationHandler(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);
    }

    @Override
    protected AuthenticationHandlerExecutionResult authenticateUsernamePasswordInternal(UsernamePasswordCredential credential, String originalPassword) throws GeneralSecurityException {
        if (StringUtils.isNotBlank((CharSequence)this.kerberosKdcSystemProperty)) {
            LOGGER.debug("Configured kerberos system property [{}] to [{}]", (Object)SYS_PROP_KERB5_KDC, (Object)this.kerberosKdcSystemProperty);
            System.setProperty(SYS_PROP_KERB5_KDC, this.kerberosKdcSystemProperty);
        }
        if (StringUtils.isNotBlank((CharSequence)this.kerberosRealmSystemProperty)) {
            LOGGER.debug("Setting kerberos system property [{}] to [{}]", (Object)SYS_PROP_KRB5_REALM, (Object)this.kerberosRealmSystemProperty);
            System.setProperty(SYS_PROP_KRB5_REALM, this.kerberosRealmSystemProperty);
        }
        org.apereo.cas.authentication.principal.Principal principal = this.authenticateAndGetPrincipal(credential);
        AuthenticationPasswordPolicyHandlingStrategy strategy = this.getPasswordPolicyHandlingStrategy();
        if (principal != null && strategy != null) {
            LOGGER.debug("Attempting to examine and handle password policy via [{}]", (Object)strategy.getClass().getSimpleName());
            List messageList = strategy.handle((Object)principal, (Object)this.getPasswordPolicyConfiguration());
            return this.createHandlerResult(credential, principal, messageList);
        }
        throw new FailedLoginException("Unable to authenticate " + credential.getId());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected org.apereo.cas.authentication.principal.Principal authenticateAndGetPrincipal(UsernamePasswordCredential credential) throws GeneralSecurityException {
        LoginContext lc = this.getLoginContext(credential);
        try {
            lc.login();
            Set<Principal> principals = lc.getSubject().getPrincipals();
            LOGGER.debug("JAAS principals extracted from subject are [{}]", principals);
            if (principals != null && !principals.isEmpty()) {
                Principal secPrincipal = principals.iterator().next();
                LOGGER.debug("JAAS principal detected from subject login context is [{}]", (Object)secPrincipal.getName());
                org.apereo.cas.authentication.principal.Principal principal = this.principalFactory.createPrincipal(secPrincipal.getName());
                return principal;
            }
        }
        finally {
            if (lc != null) {
                lc.logout();
            }
        }
        return null;
    }

    protected LoginContext getLoginContext(UsernamePasswordCredential credential) throws GeneralSecurityException {
        UsernamePasswordCallbackHandler callbackHandler = new UsernamePasswordCallbackHandler(credential.getUsername(), credential.getPassword());
        if (this.loginConfigurationFile != null && StringUtils.isNotBlank((CharSequence)this.loginConfigType) && this.loginConfigurationFile.exists() && this.loginConfigurationFile.canRead()) {
            URIParameter parameters = new URIParameter(this.loginConfigurationFile.toURI());
            Configuration loginConfig = Configuration.getInstance(this.loginConfigType, parameters);
            return new LoginContext(this.realm, null, callbackHandler, loginConfig);
        }
        return new LoginContext(this.realm, callbackHandler);
    }

    @Generated
    public void setRealm(String realm) {
        this.realm = realm;
    }

    @Generated
    public void setKerberosRealmSystemProperty(String kerberosRealmSystemProperty) {
        this.kerberosRealmSystemProperty = kerberosRealmSystemProperty;
    }

    @Generated
    public void setKerberosKdcSystemProperty(String kerberosKdcSystemProperty) {
        this.kerberosKdcSystemProperty = kerberosKdcSystemProperty;
    }

    @Generated
    public void setLoginConfigType(String loginConfigType) {
        this.loginConfigType = loginConfigType;
    }

    @Generated
    public void setLoginConfigurationFile(File loginConfigurationFile) {
        this.loginConfigurationFile = loginConfigurationFile;
    }

    protected static class UsernamePasswordCallbackHandler
    implements CallbackHandler {
        private final String userName;
        private final char[] password;

        @Override
        public void handle(Callback[] callbacks) {
            Arrays.stream(callbacks).forEach(callback -> {
                if (callback.getClass().equals(NameCallback.class)) {
                    ((NameCallback)callback).setName(this.userName);
                } else if (callback.getClass().equals(PasswordCallback.class)) {
                    ((PasswordCallback)callback).setPassword(this.password);
                }
            });
        }

        @Generated
        public UsernamePasswordCallbackHandler(String userName, char[] password) {
            this.userName = userName;
            this.password = password;
        }
    }
}

