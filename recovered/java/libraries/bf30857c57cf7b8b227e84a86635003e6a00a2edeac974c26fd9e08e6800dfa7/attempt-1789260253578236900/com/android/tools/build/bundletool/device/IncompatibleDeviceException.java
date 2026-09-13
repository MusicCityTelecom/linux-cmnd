/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.device;

import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import javax.annotation.CheckReturnValue;

public final class IncompatibleDeviceException
extends CommandExecutionException {
    public IncompatibleDeviceException(String message) {
        super(message);
    }

    public IncompatibleDeviceException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncompatibleDeviceException(Throwable cause) {
        super(cause);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends CommandExecutionException.Builder {
        @Override
        @CheckReturnValue
        public IncompatibleDeviceException build() {
            if (this.message != null) {
                if (this.cause != null) {
                    return new IncompatibleDeviceException(this.message, this.cause);
                }
                return new IncompatibleDeviceException(this.message);
            }
            if (this.cause != null) {
                return new IncompatibleDeviceException(this.cause);
            }
            return new IncompatibleDeviceException("");
        }
    }
}

