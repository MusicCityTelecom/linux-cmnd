/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  lombok.Generated
 */
package org.apereo.cas.authentication;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import lombok.Generated;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class AuthenticationPolicyExecutionResult
implements Serializable {
    private static final long serialVersionUID = -6607624825058147653L;
    private final boolean success;

    public static AuthenticationPolicyExecutionResult failure() {
        return ((AuthenticationPolicyExecutionResultBuilder)AuthenticationPolicyExecutionResult.builder().success(false)).build();
    }

    public static AuthenticationPolicyExecutionResult success(boolean condition) {
        return ((AuthenticationPolicyExecutionResultBuilder)AuthenticationPolicyExecutionResult.builder().success(condition)).build();
    }

    public static AuthenticationPolicyExecutionResult success() {
        return AuthenticationPolicyExecutionResult.success(true);
    }

    @Generated
    protected AuthenticationPolicyExecutionResult(AuthenticationPolicyExecutionResultBuilder<?, ?> b) {
        this.success = b.success;
    }

    @Generated
    public static AuthenticationPolicyExecutionResultBuilder<?, ?> builder() {
        return new AuthenticationPolicyExecutionResultBuilderImpl();
    }

    @Generated
    public boolean isSuccess() {
        return this.success;
    }

    @Generated
    public String toString() {
        return "AuthenticationPolicyExecutionResult(success=" + this.success + ")";
    }

    @Generated
    private static final class AuthenticationPolicyExecutionResultBuilderImpl
    extends AuthenticationPolicyExecutionResultBuilder<AuthenticationPolicyExecutionResult, AuthenticationPolicyExecutionResultBuilderImpl> {
        @Generated
        private AuthenticationPolicyExecutionResultBuilderImpl() {
        }

        @Override
        @Generated
        protected AuthenticationPolicyExecutionResultBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public AuthenticationPolicyExecutionResult build() {
            return new AuthenticationPolicyExecutionResult(this);
        }
    }

    @Generated
    public static abstract class AuthenticationPolicyExecutionResultBuilder<C extends AuthenticationPolicyExecutionResult, B extends AuthenticationPolicyExecutionResultBuilder<C, B>> {
        @Generated
        private boolean success;

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
        public String toString() {
            return "AuthenticationPolicyExecutionResult.AuthenticationPolicyExecutionResultBuilder(success=" + this.success + ")";
        }
    }
}

