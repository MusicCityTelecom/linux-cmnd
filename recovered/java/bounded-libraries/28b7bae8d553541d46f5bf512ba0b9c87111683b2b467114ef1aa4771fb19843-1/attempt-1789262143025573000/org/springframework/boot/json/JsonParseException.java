/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.json;

public class JsonParseException
extends IllegalArgumentException {
    public JsonParseException() {
        this((Throwable)null);
    }

    public JsonParseException(Throwable cause) {
        super("Cannot parse JSON", cause);
    }
}

