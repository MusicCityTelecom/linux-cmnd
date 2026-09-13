/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.security.web.csrf;

import java.io.Serializable;

public interface CsrfToken
extends Serializable {
    public String getHeaderName();

    public String getParameterName();

    public String getToken();
}

