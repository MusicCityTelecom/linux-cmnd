/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.csrf;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.util.Assert;

public final class DefaultCsrfToken
implements CsrfToken {
    private final String token;
    private final String parameterName;
    private final String headerName;

    public DefaultCsrfToken(String headerName, String parameterName, String token) {
        Assert.hasLength((String)headerName, (String)"headerName cannot be null or empty");
        Assert.hasLength((String)parameterName, (String)"parameterName cannot be null or empty");
        Assert.hasLength((String)token, (String)"token cannot be null or empty");
        this.headerName = headerName;
        this.parameterName = parameterName;
        this.token = token;
    }

    @Override
    public String getHeaderName() {
        return this.headerName;
    }

    @Override
    public String getParameterName() {
        return this.parameterName;
    }

    @Override
    public String getToken() {
        return this.token;
    }
}

