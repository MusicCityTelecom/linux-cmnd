/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.context.config;

import org.springframework.boot.context.config.ConfigDataException;
import org.springframework.boot.origin.OriginProvider;

public abstract class ConfigDataNotFoundException
extends ConfigDataException
implements OriginProvider {
    ConfigDataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract String getReferenceDescription();
}

