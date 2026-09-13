/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.authentication;

import java.util.Optional;
import lombok.Generated;
import org.apereo.cas.authentication.MultifactorAuthenticationProvider;

public class MultifactorAuthenticationContextValidationResult {
    private final boolean success;
    private final Optional<MultifactorAuthenticationProvider> provider;

    @Generated
    private static Optional<MultifactorAuthenticationProvider> $default$provider() {
        return Optional.empty();
    }

    @Generated
    protected MultifactorAuthenticationContextValidationResult(MultifactorAuthenticationContextValidationResultBuilder<?, ?> b) {
        this.success = b.success;
        this.provider = b.provider$set ? b.provider$value : MultifactorAuthenticationContextValidationResult.$default$provider();
    }

    @Generated
    public static MultifactorAuthenticationContextValidationResultBuilder<?, ?> builder() {
        return new MultifactorAuthenticationContextValidationResultBuilderImpl();
    }

    @Generated
    public boolean isSuccess() {
        return this.success;
    }

    @Generated
    public Optional<MultifactorAuthenticationProvider> getProvider() {
        return this.provider;
    }

    @Generated
    private static final class MultifactorAuthenticationContextValidationResultBuilderImpl
    extends MultifactorAuthenticationContextValidationResultBuilder<MultifactorAuthenticationContextValidationResult, MultifactorAuthenticationContextValidationResultBuilderImpl> {
        @Generated
        private MultifactorAuthenticationContextValidationResultBuilderImpl() {
        }

        @Override
        @Generated
        protected MultifactorAuthenticationContextValidationResultBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public MultifactorAuthenticationContextValidationResult build() {
            return new MultifactorAuthenticationContextValidationResult(this);
        }
    }

    @Generated
    public static abstract class MultifactorAuthenticationContextValidationResultBuilder<C extends MultifactorAuthenticationContextValidationResult, B extends MultifactorAuthenticationContextValidationResultBuilder<C, B>> {
        @Generated
        private boolean success;
        @Generated
        private boolean provider$set;
        @Generated
        private Optional<MultifactorAuthenticationProvider> provider$value;

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
        public B provider(Optional<MultifactorAuthenticationProvider> provider) {
            this.provider$value = provider;
            this.provider$set = true;
            return this.self();
        }

        @Generated
        public String toString() {
            return "MultifactorAuthenticationContextValidationResult.MultifactorAuthenticationContextValidationResultBuilder(success=" + this.success + ", provider$value=" + this.provider$value + ")";
        }
    }
}

