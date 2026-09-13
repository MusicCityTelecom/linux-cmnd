/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.validation;

import java.io.Serializable;
import java.util.Optional;
import lombok.Generated;

public class AuthenticationContextValidationResult
implements Serializable {
    private static final long serialVersionUID = 5276264106164141194L;
    private final boolean success;
    private final Optional<String> contextId;

    @Generated
    private static Optional<String> $default$contextId() {
        return Optional.empty();
    }

    @Generated
    protected AuthenticationContextValidationResult(AuthenticationContextValidationResultBuilder<?, ?> b) {
        this.success = b.success;
        this.contextId = b.contextId$set ? b.contextId$value : AuthenticationContextValidationResult.$default$contextId();
    }

    @Generated
    public static AuthenticationContextValidationResultBuilder<?, ?> builder() {
        return new AuthenticationContextValidationResultBuilderImpl();
    }

    @Generated
    public boolean isSuccess() {
        return this.success;
    }

    @Generated
    public Optional<String> getContextId() {
        return this.contextId;
    }

    @Generated
    public String toString() {
        return "AuthenticationContextValidationResult(success=" + this.success + ", contextId=" + this.contextId + ")";
    }

    @Generated
    private static final class AuthenticationContextValidationResultBuilderImpl
    extends AuthenticationContextValidationResultBuilder<AuthenticationContextValidationResult, AuthenticationContextValidationResultBuilderImpl> {
        @Generated
        private AuthenticationContextValidationResultBuilderImpl() {
        }

        @Override
        @Generated
        protected AuthenticationContextValidationResultBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public AuthenticationContextValidationResult build() {
            return new AuthenticationContextValidationResult(this);
        }
    }

    @Generated
    public static abstract class AuthenticationContextValidationResultBuilder<C extends AuthenticationContextValidationResult, B extends AuthenticationContextValidationResultBuilder<C, B>> {
        @Generated
        private boolean success;
        @Generated
        private boolean contextId$set;
        @Generated
        private Optional<String> contextId$value;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B success(boolean success) {
            this.success = success;
            return this.self();
        }

        @Generated
        public B contextId(Optional<String> contextId) {
            this.contextId$value = contextId;
            this.contextId$set = true;
            return this.self();
        }

        @Generated
        public String toString() {
            return "AuthenticationContextValidationResult.AuthenticationContextValidationResultBuilder(success=" + this.success + ", contextId$value=" + this.contextId$value + ")";
        }
    }
}

