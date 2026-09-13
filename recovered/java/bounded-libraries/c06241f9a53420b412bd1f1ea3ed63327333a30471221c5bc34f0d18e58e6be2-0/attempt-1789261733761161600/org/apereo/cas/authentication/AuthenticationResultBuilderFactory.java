/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication;

import java.io.Serializable;
import org.apereo.cas.authentication.AuthenticationResultBuilder;

@FunctionalInterface
public interface AuthenticationResultBuilderFactory
extends Serializable {
    public AuthenticationResultBuilder newBuilder();
}

