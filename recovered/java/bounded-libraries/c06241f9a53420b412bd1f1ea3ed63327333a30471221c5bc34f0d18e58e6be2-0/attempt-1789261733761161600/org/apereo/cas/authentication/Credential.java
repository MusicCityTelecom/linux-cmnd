/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication;

import java.io.Serializable;

@FunctionalInterface
public interface Credential
extends Serializable {
    public static final String CREDENTIAL_TYPE_ATTRIBUTE = "credentialType";
    public static final String UNKNOWN_ID = "unknown";

    public String getId();
}

