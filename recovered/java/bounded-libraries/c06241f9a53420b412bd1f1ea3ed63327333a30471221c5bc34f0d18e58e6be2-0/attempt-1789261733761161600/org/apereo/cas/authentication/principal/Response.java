/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication.principal;

import java.io.Serializable;
import java.util.Map;

public interface Response
extends Serializable {
    public Map<String, String> getAttributes();

    public ResponseType getResponseType();

    public String getUrl();

    public static enum ResponseType {
        POST,
        REDIRECT,
        HEADER;

    }
}

