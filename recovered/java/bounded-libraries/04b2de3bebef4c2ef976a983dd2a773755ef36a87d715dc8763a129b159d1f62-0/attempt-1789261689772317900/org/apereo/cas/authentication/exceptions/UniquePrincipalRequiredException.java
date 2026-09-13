/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.AuthenticationException
 */
package org.apereo.cas.authentication.exceptions;

import org.apereo.cas.authentication.AuthenticationException;

public class UniquePrincipalRequiredException
extends AuthenticationException {
    private static final String CODE = "UNIQUE_PRINCIPAL_REQUIRED";
    private static final long serialVersionUID = 3532358716666809448L;

    public String getCode() {
        return CODE;
    }
}

