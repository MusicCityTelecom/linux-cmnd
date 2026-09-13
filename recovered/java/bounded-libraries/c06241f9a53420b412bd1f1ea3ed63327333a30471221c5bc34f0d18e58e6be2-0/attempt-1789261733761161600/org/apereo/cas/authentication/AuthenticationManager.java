/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.monitor.Monitorable
 */
package org.apereo.cas.authentication;

import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.monitor.Monitorable;

@FunctionalInterface
@Monitorable
public interface AuthenticationManager {
    public static final String AUTHENTICATION_METHOD_ATTRIBUTE = "authenticationMethod";
    public static final String AUTHENTICATION_DATE_ATTRIBUTE = "authenticationDate";

    public Authentication authenticate(AuthenticationTransaction var1) throws AuthenticationException;
}

