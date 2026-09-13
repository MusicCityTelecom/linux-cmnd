/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.Service
 */
package org.apereo.cas.services;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.services.RegisteredService;

public class RegisteredServiceAttributeReleasePolicyContext {
    private final Principal principal;
    private final Service service;
    private final RegisteredService registeredService;
    private final Map<String, List<Object>> releasingAttributes;

    @Generated
    private static Map<String, List<Object>> $default$releasingAttributes() {
        return new LinkedHashMap<String, List<Object>>();
    }

    @Generated
    protected RegisteredServiceAttributeReleasePolicyContext(RegisteredServiceAttributeReleasePolicyContextBuilder<?, ?> b) {
        this.principal = b.principal;
        this.service = b.service;
        this.registeredService = b.registeredService;
        this.releasingAttributes = b.releasingAttributes$set ? b.releasingAttributes$value : RegisteredServiceAttributeReleasePolicyContext.$default$releasingAttributes();
    }

    @Generated
    public static RegisteredServiceAttributeReleasePolicyContextBuilder<?, ?> builder() {
        return new RegisteredServiceAttributeReleasePolicyContextBuilderImpl();
    }

    @Generated
    public Principal getPrincipal() {
        return this.principal;
    }

    @Generated
    public Service getService() {
        return this.service;
    }

    @Generated
    public RegisteredService getRegisteredService() {
        return this.registeredService;
    }

    @Generated
    public Map<String, List<Object>> getReleasingAttributes() {
        return this.releasingAttributes;
    }

    @Generated
    private static final class RegisteredServiceAttributeReleasePolicyContextBuilderImpl
    extends RegisteredServiceAttributeReleasePolicyContextBuilder<RegisteredServiceAttributeReleasePolicyContext, RegisteredServiceAttributeReleasePolicyContextBuilderImpl> {
        @Generated
        private RegisteredServiceAttributeReleasePolicyContextBuilderImpl() {
        }

        @Override
        @Generated
        protected RegisteredServiceAttributeReleasePolicyContextBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public RegisteredServiceAttributeReleasePolicyContext build() {
            return new RegisteredServiceAttributeReleasePolicyContext(this);
        }
    }

    @Generated
    public static abstract class RegisteredServiceAttributeReleasePolicyContextBuilder<C extends RegisteredServiceAttributeReleasePolicyContext, B extends RegisteredServiceAttributeReleasePolicyContextBuilder<C, B>> {
        @Generated
        private Principal principal;
        @Generated
        private Service service;
        @Generated
        private RegisteredService registeredService;
        @Generated
        private boolean releasingAttributes$set;
        @Generated
        private Map<String, List<Object>> releasingAttributes$value;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B principal(Principal principal) {
            this.principal = principal;
            return this.self();
        }

        @Generated
        public B service(Service service) {
            this.service = service;
            return this.self();
        }

        @Generated
        public B registeredService(RegisteredService registeredService) {
            this.registeredService = registeredService;
            return this.self();
        }

        @Generated
        public B releasingAttributes(Map<String, List<Object>> releasingAttributes) {
            this.releasingAttributes$value = releasingAttributes;
            this.releasingAttributes$set = true;
            return this.self();
        }

        @Generated
        public String toString() {
            return "RegisteredServiceAttributeReleasePolicyContext.RegisteredServiceAttributeReleasePolicyContextBuilder(principal=" + this.principal + ", service=" + this.service + ", registeredService=" + this.registeredService + ", releasingAttributes$value=" + this.releasingAttributes$value + ")";
        }
    }
}

