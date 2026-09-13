/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.Service
 */
package org.apereo.cas.ticket;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.ticket.AbstractTicketException;

@FunctionalInterface
public interface TicketValidator {
    public ValidationResult validate(String var1, String var2) throws AbstractTicketException;

    public static class ValidationResult
    implements Serializable {
        private static final long serialVersionUID = 8115764183802826474L;
        private final Principal principal;
        private final Service service;
        private final Map<String, List<Object>> attributes;
        private final Map<String, Serializable> context;

        @Generated
        private static Map<String, List<Object>> $default$attributes() {
            return new LinkedHashMap<String, List<Object>>();
        }

        @Generated
        private static Map<String, Serializable> $default$context() {
            return new LinkedHashMap<String, Serializable>();
        }

        @Generated
        protected ValidationResult(ValidationResultBuilder<?, ?> b) {
            this.principal = b.principal;
            this.service = b.service;
            this.attributes = b.attributes$set ? b.attributes$value : ValidationResult.$default$attributes();
            this.context = b.context$set ? b.context$value : ValidationResult.$default$context();
        }

        @Generated
        public static ValidationResultBuilder<?, ?> builder() {
            return new ValidationResultBuilderImpl();
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
        public Map<String, List<Object>> getAttributes() {
            return this.attributes;
        }

        @Generated
        public Map<String, Serializable> getContext() {
            return this.context;
        }

        @Generated
        private static final class ValidationResultBuilderImpl
        extends ValidationResultBuilder<ValidationResult, ValidationResultBuilderImpl> {
            @Generated
            private ValidationResultBuilderImpl() {
            }

            @Override
            @Generated
            protected ValidationResultBuilderImpl self() {
                return this;
            }

            @Override
            @Generated
            public ValidationResult build() {
                return new ValidationResult(this);
            }
        }

        @Generated
        public static abstract class ValidationResultBuilder<C extends ValidationResult, B extends ValidationResultBuilder<C, B>> {
            @Generated
            private Principal principal;
            @Generated
            private Service service;
            @Generated
            private boolean attributes$set;
            @Generated
            private Map<String, List<Object>> attributes$value;
            @Generated
            private boolean context$set;
            @Generated
            private Map<String, Serializable> context$value;

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
            public B attributes(Map<String, List<Object>> attributes) {
                this.attributes$value = attributes;
                this.attributes$set = true;
                return this.self();
            }

            @Generated
            public B context(Map<String, Serializable> context) {
                this.context$value = context;
                this.context$set = true;
                return this.self();
            }

            @Generated
            public String toString() {
                return "TicketValidator.ValidationResult.ValidationResultBuilder(principal=" + this.principal + ", service=" + this.service + ", attributes$value=" + this.attributes$value + ", context$value=" + this.context$value + ")";
            }
        }
    }
}

