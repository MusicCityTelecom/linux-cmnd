/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.exceptions;

import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import javax.annotation.CheckReturnValue;

public class ValidationException
extends CommandExecutionException {
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends CommandExecutionException.Builder {
        @Override
        @CheckReturnValue
        public ValidationException build() {
            if (this.message != null) {
                if (this.cause != null) {
                    return new ValidationException(this.message, this.cause);
                }
                return new ValidationException(this.message);
            }
            if (this.cause != null) {
                return new ValidationException(this.cause);
            }
            return new ValidationException("");
        }
    }
}

