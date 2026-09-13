/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.exceptions;

import com.android.bundle.Errors;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.FormatMethod;
import com.google.errorprone.annotations.FormatString;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

public class CommandExecutionException
extends RuntimeException {
    public CommandExecutionException(String message) {
        super(message);
    }

    public CommandExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandExecutionException(Throwable cause) {
        super(cause);
    }

    public Errors.BundleToolError toProto() {
        Errors.BundleToolError.Builder builder = Errors.BundleToolError.newBuilder().setExceptionMessage(this.getMessage());
        this.customizeProto(builder);
        return builder.build();
    }

    protected void customizeProto(Errors.BundleToolError.Builder builder) {
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        @Nullable
        protected Throwable cause;
        @Nullable
        protected String message;

        public Builder withCause(Throwable cause) {
            this.cause = cause;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        @FormatMethod
        public Builder withMessage(@FormatString String message, Object ... args) {
            this.message = String.format(Preconditions.checkNotNull(message), args);
            return this;
        }

        @CheckReturnValue
        public CommandExecutionException build() {
            if (this.message != null) {
                if (this.cause != null) {
                    return new CommandExecutionException(this.message, this.cause);
                }
                return new CommandExecutionException(this.message);
            }
            if (this.cause != null) {
                return new CommandExecutionException(this.cause);
            }
            return new CommandExecutionException("");
        }
    }
}

