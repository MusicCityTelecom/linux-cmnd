/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.configurationprocessor.metadata;

import javax.tools.Diagnostic;

public class InvalidConfigurationMetadataException
extends RuntimeException {
    private final Diagnostic.Kind kind;

    public InvalidConfigurationMetadataException(String message, Diagnostic.Kind kind) {
        super(message);
        this.kind = kind;
    }

    public Diagnostic.Kind getKind() {
        return this.kind;
    }
}

