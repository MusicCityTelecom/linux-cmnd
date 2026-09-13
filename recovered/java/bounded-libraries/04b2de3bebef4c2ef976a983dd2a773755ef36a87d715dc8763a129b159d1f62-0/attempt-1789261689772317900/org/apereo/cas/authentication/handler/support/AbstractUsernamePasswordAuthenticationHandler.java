/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.beanutils.BeanUtils
 *  org.apache.commons.lang3.BooleanUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.AuthenticationHandlerExecutionResult
 *  org.apereo.cas.authentication.AuthenticationPasswordPolicyHandlingStrategy
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.authentication.PreventedException
 *  org.apereo.cas.authentication.handler.PrincipalNameTransformer
 *  org.apereo.cas.authentication.principal.PrincipalFactory
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.security.crypto.password.NoOpPasswordEncoder
 *  org.springframework.security.crypto.password.PasswordEncoder
 */
package org.apereo.cas.authentication.handler.support;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.FailedLoginException;
import lombok.Generated;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.AuthenticationHandlerExecutionResult;
import org.apereo.cas.authentication.AuthenticationPasswordPolicyHandlingStrategy;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.authentication.PreventedException;
import org.apereo.cas.authentication.credential.UsernamePasswordCredential;
import org.apereo.cas.authentication.handler.PrincipalNameTransformer;
import org.apereo.cas.authentication.handler.support.AbstractPreAndPostProcessingAuthenticationHandler;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.authentication.support.password.PasswordPolicyContext;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.util.function.FunctionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class AbstractUsernamePasswordAuthenticationHandler
extends AbstractPreAndPostProcessingAuthenticationHandler {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractUsernamePasswordAuthenticationHandler.class);
    protected AuthenticationPasswordPolicyHandlingStrategy passwordPolicyHandlingStrategy = (o, o2) -> new ArrayList(0);
    private PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();
    private PrincipalNameTransformer principalNameTransformer = String::trim;
    private PasswordPolicyContext passwordPolicyConfiguration;

    protected AbstractUsernamePasswordAuthenticationHandler(String name, ServicesManager servicesManager, PrincipalFactory principalFactory, Integer order) {
        super(name, servicesManager, principalFactory, order);
    }

    public boolean supports(Class<? extends Credential> clazz) {
        return UsernamePasswordCredential.class.isAssignableFrom(clazz);
    }

    public boolean supports(Credential credential) {
        if (!UsernamePasswordCredential.class.isInstance(credential)) {
            LOGGER.debug("Credential is not one of username/password and is not accepted by handler [{}]", (Object)this.getName());
            return false;
        }
        if (this.getCredentialSelectionPredicate() == null) {
            LOGGER.debug("No credential selection criteria is defined for handler [{}]. Credential is accepted for further processing", (Object)this.getName());
            return true;
        }
        LOGGER.debug("Examining credential [{}] eligibility for authentication handler [{}]", (Object)credential, (Object)this.getName());
        boolean result = this.getCredentialSelectionPredicate().test(credential);
        LOGGER.debug("Credential [{}] eligibility is [{}] for authentication handler [{}]", new Object[]{credential, this.getName(), BooleanUtils.toStringTrueFalse((boolean)result)});
        return result;
    }

    @Override
    protected AuthenticationHandlerExecutionResult doAuthentication(Credential credential, Service service) throws GeneralSecurityException {
        UsernamePasswordCredential originalUserPass = (UsernamePasswordCredential)credential;
        UsernamePasswordCredential userPass = new UsernamePasswordCredential();
        FunctionUtils.doUnchecked(u -> BeanUtils.copyProperties((Object)userPass, (Object)originalUserPass), (Object[])new Object[0]);
        this.transformUsername(userPass);
        this.transformPassword(userPass);
        LOGGER.debug("Attempting authentication internally for transformed credential [{}]", (Object)userPass);
        return this.authenticateUsernamePasswordInternal(userPass, originalUserPass.toPassword());
    }

    protected void transformPassword(UsernamePasswordCredential userPass) throws FailedLoginException, AccountNotFoundException {
        if (StringUtils.isBlank((CharSequence)userPass.toPassword())) {
            throw new FailedLoginException("Password is null.");
        }
        LOGGER.debug("Attempting to encode credential password via [{}] for [{}]", (Object)this.passwordEncoder.getClass().getName(), (Object)userPass.getUsername());
        String transformedPsw = this.passwordEncoder.encode((CharSequence)userPass.toPassword());
        if (StringUtils.isBlank((CharSequence)transformedPsw)) {
            throw new AccountNotFoundException("Encoded password is null.");
        }
        userPass.assignPassword(transformedPsw);
    }

    protected void transformUsername(UsernamePasswordCredential userPass) throws AccountNotFoundException {
        if (StringUtils.isBlank((CharSequence)userPass.getUsername())) {
            throw new AccountNotFoundException("Username is null.");
        }
        LOGGER.debug("Transforming credential username via [{}]", (Object)this.principalNameTransformer.getClass().getName());
        String transformedUsername = this.principalNameTransformer.transform(userPass.getUsername());
        if (StringUtils.isBlank((CharSequence)transformedUsername)) {
            throw new AccountNotFoundException("Transformed username is null.");
        }
        userPass.setUsername(transformedUsername);
    }

    protected abstract AuthenticationHandlerExecutionResult authenticateUsernamePasswordInternal(UsernamePasswordCredential var1, String var2) throws GeneralSecurityException, PreventedException;

    protected boolean matches(CharSequence charSequence, String password) {
        return this.passwordEncoder.matches(charSequence, password);
    }

    @Generated
    public void setPasswordPolicyHandlingStrategy(AuthenticationPasswordPolicyHandlingStrategy passwordPolicyHandlingStrategy) {
        this.passwordPolicyHandlingStrategy = passwordPolicyHandlingStrategy;
    }

    @Generated
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Generated
    public void setPrincipalNameTransformer(PrincipalNameTransformer principalNameTransformer) {
        this.principalNameTransformer = principalNameTransformer;
    }

    @Generated
    public void setPasswordPolicyConfiguration(PasswordPolicyContext passwordPolicyConfiguration) {
        this.passwordPolicyConfiguration = passwordPolicyConfiguration;
    }

    @Generated
    public AuthenticationPasswordPolicyHandlingStrategy getPasswordPolicyHandlingStrategy() {
        return this.passwordPolicyHandlingStrategy;
    }

    @Generated
    public PasswordEncoder getPasswordEncoder() {
        return this.passwordEncoder;
    }

    @Generated
    public PrincipalNameTransformer getPrincipalNameTransformer() {
        return this.principalNameTransformer;
    }

    @Generated
    public PasswordPolicyContext getPasswordPolicyConfiguration() {
        return this.passwordPolicyConfiguration;
    }
}

