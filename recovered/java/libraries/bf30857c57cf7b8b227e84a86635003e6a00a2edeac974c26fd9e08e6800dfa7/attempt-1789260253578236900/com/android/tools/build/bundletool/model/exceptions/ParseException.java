/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.exceptions;

import com.android.tools.build.bundletool.model.exceptions.CommandExecutionException;
import javax.annotation.CheckReturnValue;

public class ParseException
extends CommandExecutionException {
    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseException(Throwable cause) {
        super(cause);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder
    extends CommandExecutionException.Builder {
        @Override
        @CheckReturnValue
        public ParseException build() {
            if (this.message != null) {
                if (this.cause != null) {
                    return new ParseException(this.message, this.cause);
                }
                return new ParseException(this.message);
            }
            if (this.cause != null) {
                return new ParseException(this.cause);
            }
            return new ParseException("");
        }
    }
}

