/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.PersistentIdGenerator
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.util.RandomUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services;

import lombok.Generated;
import org.apereo.cas.authentication.principal.PersistentIdGenerator;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.authentication.principal.ShibbolethCompatiblePersistentIdGenerator;
import org.apereo.cas.services.BaseRegisteredServiceUsernameAttributeProvider;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.util.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnonymousRegisteredServiceUsernameAttributeProvider
extends BaseRegisteredServiceUsernameAttributeProvider {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(AnonymousRegisteredServiceUsernameAttributeProvider.class);
    private static final long serialVersionUID = 7050462900237284803L;
    private PersistentIdGenerator persistentIdGenerator = new ShibbolethCompatiblePersistentIdGenerator(RandomUtils.randomAlphanumeric((int)16));

    @Override
    protected String resolveUsernameInternal(Principal principal, Service service, RegisteredService registeredService) {
        String id = this.persistentIdGenerator.generate(principal, service);
        LOGGER.debug("Resolved username [{}] for anonymous access", (Object)id);
        return id;
    }

    @Generated
    public AnonymousRegisteredServiceUsernameAttributeProvider() {
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AnonymousRegisteredServiceUsernameAttributeProvider)) {
            return false;
        }
        AnonymousRegisteredServiceUsernameAttributeProvider other = (AnonymousRegisteredServiceUsernameAttributeProvider)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        PersistentIdGenerator this$persistentIdGenerator = this.persistentIdGenerator;
        PersistentIdGenerator other$persistentIdGenerator = other.persistentIdGenerator;
        return !(this$persistentIdGenerator == null ? other$persistentIdGenerator != null : !this$persistentIdGenerator.equals(other$persistentIdGenerator));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof AnonymousRegisteredServiceUsernameAttributeProvider;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        PersistentIdGenerator $persistentIdGenerator = this.persistentIdGenerator;
        result = result * 59 + ($persistentIdGenerator == null ? 43 : $persistentIdGenerator.hashCode());
        return result;
    }

    @Generated
    public PersistentIdGenerator getPersistentIdGenerator() {
        return this.persistentIdGenerator;
    }

    @Generated
    public AnonymousRegisteredServiceUsernameAttributeProvider(PersistentIdGenerator persistentIdGenerator) {
        this.persistentIdGenerator = persistentIdGenerator;
    }

    @Generated
    public AnonymousRegisteredServiceUsernameAttributeProvider setPersistentIdGenerator(PersistentIdGenerator persistentIdGenerator) {
        this.persistentIdGenerator = persistentIdGenerator;
        return this;
    }
}

