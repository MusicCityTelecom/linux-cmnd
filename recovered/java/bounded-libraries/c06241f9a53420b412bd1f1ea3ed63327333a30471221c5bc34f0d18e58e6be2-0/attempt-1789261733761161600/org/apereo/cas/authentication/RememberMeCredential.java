/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication;

import org.apereo.cas.authentication.Credential;

public interface RememberMeCredential
extends Credential {
    public static final String AUTHENTICATION_ATTRIBUTE_REMEMBER_ME = "org.apereo.cas.authentication.principal.REMEMBER_ME";
    public static final String REQUEST_PARAMETER_REMEMBER_ME = "rememberMe";

    public boolean isRememberMe();

    public void setRememberMe(boolean var1);
}

