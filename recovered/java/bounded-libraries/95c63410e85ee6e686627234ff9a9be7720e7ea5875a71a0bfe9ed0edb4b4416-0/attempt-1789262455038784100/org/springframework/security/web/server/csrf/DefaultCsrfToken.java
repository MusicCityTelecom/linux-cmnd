/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.server.csrf;

import org.springframework.security.web.server.csrf.CsrfToken;
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

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CsrfToken)) {
            return false;
        }
        CsrfToken other = (CsrfToken)obj;
        if (!this.getToken().equals(other.getToken())) {
            return false;
        }
        if (!this.getParameterName().equals(other.getParameterName())) {
            return false;
        }
        return this.getHeaderName().equals(other.getHeaderName());
    }

    public int hashCode() {
        int result = this.getToken().hashCode();
        result = 31 * result + this.getParameterName().hashCode();
        result = 31 * result + this.getHeaderName().hashCode();
        return result;
    }
}

