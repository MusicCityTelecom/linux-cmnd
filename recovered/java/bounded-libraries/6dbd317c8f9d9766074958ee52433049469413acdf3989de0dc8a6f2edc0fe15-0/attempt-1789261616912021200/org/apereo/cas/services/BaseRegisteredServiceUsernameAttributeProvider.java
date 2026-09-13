/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.PostLoad
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceCipherExecutor
 *  org.apereo.cas.services.RegisteredServiceUsernameAttributeProvider
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.apereo.cas.util.spring.ApplicationContextProvider
 *  org.apereo.services.persondir.util.CaseCanonicalizationMode
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationContext
 */
package org.apereo.cas.services;

import java.util.Locale;
import java.util.Optional;
import javax.persistence.PostLoad;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceCipherExecutor;
import org.apereo.cas.services.RegisteredServiceUsernameAttributeProvider;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.spring.ApplicationContextProvider;
import org.apereo.services.persondir.util.CaseCanonicalizationMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public abstract class BaseRegisteredServiceUsernameAttributeProvider
implements RegisteredServiceUsernameAttributeProvider {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseRegisteredServiceUsernameAttributeProvider.class);
    private static final long serialVersionUID = -8381275200333399951L;
    private String canonicalizationMode = CaseCanonicalizationMode.NONE.name();
    private boolean encryptUsername;
    private String scope;

    public final String resolveUsername(Principal principal, Service service, RegisteredService registeredService) {
        String resolved = this.resolveUsernameInternal(principal, service, registeredService);
        if (this.canonicalizationMode == null) {
            this.canonicalizationMode = CaseCanonicalizationMode.NONE.name();
        }
        String username = (String)FunctionUtils.doIfNotNull((Object)this.scope, () -> String.format("%s@%s", resolved, this.scope), () -> resolved).get();
        String uid = CaseCanonicalizationMode.valueOf((String)this.canonicalizationMode).canonicalize(username.trim(), Locale.getDefault());
        LOGGER.debug("Resolved username for [{}] is [{}]", (Object)service, (Object)uid);
        if (!this.encryptUsername) {
            return uid;
        }
        String encryptedId = this.encryptResolvedUsername(principal, service, registeredService, uid);
        if (StringUtils.isBlank((CharSequence)encryptedId)) {
            throw new IllegalArgumentException("Could not encrypt username " + uid + " for service " + service);
        }
        return encryptedId;
    }

    protected String encryptResolvedUsername(Principal principal, Service service, RegisteredService registeredService, String username) {
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        RegisteredServiceCipherExecutor cipher = (RegisteredServiceCipherExecutor)applicationContext.getBean("registeredServiceCipherExecutor", RegisteredServiceCipherExecutor.class);
        return cipher.encode(username, Optional.of(registeredService));
    }

    @PostLoad
    public void initialize() {
        this.setCanonicalizationMode(CaseCanonicalizationMode.NONE.name());
    }

    protected abstract String resolveUsernameInternal(Principal var1, Service var2, RegisteredService var3);

    @Generated
    public String getCanonicalizationMode() {
        return this.canonicalizationMode;
    }

    @Generated
    public boolean isEncryptUsername() {
        return this.encryptUsername;
    }

    @Generated
    public String getScope() {
        return this.scope;
    }

    @Generated
    public void setCanonicalizationMode(String canonicalizationMode) {
        this.canonicalizationMode = canonicalizationMode;
    }

    @Generated
    public void setEncryptUsername(boolean encryptUsername) {
        this.encryptUsername = encryptUsername;
    }

    @Generated
    public void setScope(String scope) {
        this.scope = scope;
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BaseRegisteredServiceUsernameAttributeProvider)) {
            return false;
        }
        BaseRegisteredServiceUsernameAttributeProvider other = (BaseRegisteredServiceUsernameAttributeProvider)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.encryptUsername != other.encryptUsername) {
            return false;
        }
        String this$canonicalizationMode = this.canonicalizationMode;
        String other$canonicalizationMode = other.canonicalizationMode;
        if (this$canonicalizationMode == null ? other$canonicalizationMode != null : !this$canonicalizationMode.equals(other$canonicalizationMode)) {
            return false;
        }
        String this$scope = this.scope;
        String other$scope = other.scope;
        return !(this$scope == null ? other$scope != null : !this$scope.equals(other$scope));
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof BaseRegisteredServiceUsernameAttributeProvider;
    }

    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + (this.encryptUsername ? 79 : 97);
        String $canonicalizationMode = this.canonicalizationMode;
        result = result * 59 + ($canonicalizationMode == null ? 43 : $canonicalizationMode.hashCode());
        String $scope = this.scope;
        result = result * 59 + ($scope == null ? 43 : $scope.hashCode());
        return result;
    }

    @Generated
    protected BaseRegisteredServiceUsernameAttributeProvider(String canonicalizationMode, boolean encryptUsername, String scope) {
        this.canonicalizationMode = canonicalizationMode;
        this.encryptUsername = encryptUsername;
        this.scope = scope;
    }

    @Generated
    protected BaseRegisteredServiceUsernameAttributeProvider() {
    }
}

