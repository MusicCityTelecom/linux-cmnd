/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.AuthenticationResultBuilder
 *  org.apereo.cas.authentication.AuthenticationResultBuilderFactory
 */
package org.apereo.cas.authentication;

import org.apereo.cas.authentication.AuthenticationResultBuilder;
import org.apereo.cas.authentication.AuthenticationResultBuilderFactory;
import org.apereo.cas.authentication.DefaultAuthenticationResultBuilder;

public class DefaultAuthenticationResultBuilderFactory
implements AuthenticationResultBuilderFactory {
    private static final long serialVersionUID = 3506297547445902679L;

    public AuthenticationResultBuilder newBuilder() {
        return new DefaultAuthenticationResultBuilder();
    }
}

