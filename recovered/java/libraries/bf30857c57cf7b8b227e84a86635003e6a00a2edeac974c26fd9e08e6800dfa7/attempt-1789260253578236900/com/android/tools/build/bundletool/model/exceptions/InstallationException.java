/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.exceptions;

import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import javax.annotation.CheckReturnValue;

public class InstallationException
extends CommandExecutionException {
    public InstallationException(String message) {
        super(message);
    }

    public InstallationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InstallationException(Throwable cause) {
        super(cause);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends CommandExecutionException.Builder {
        @Override
        @CheckReturnValue
        public InstallationException build() {
            if (this.message != null) {
                if (this.cause != null) {
                    return new InstallationException(this.message, this.cause);
                }
                return new InstallationException(this.message);
            }
            if (this.cause != null) {
                return new InstallationException(this.cause);
            }
            return new InstallationException("");
        }
    }
}

