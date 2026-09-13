/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.util.Assert
 */
package org.springframework.security.web.csrf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.util.Assert;

public final class LazyCsrfTokenRepository
implements CsrfTokenRepository {
    private static final String HTTP_RESPONSE_ATTR = HttpServletResponse.class.getName();
    private final CsrfTokenRepository delegate;

    public LazyCsrfTokenRepository(CsrfTokenRepository delegate) {
        Assert.notNull((Object)delegate, (String)"delegate cannot be null");
        this.delegate = delegate;
    }

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        return this.wrap(request, this.delegate.generateToken(request));
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        if (token == null) {
            this.delegate.saveToken(token, request, response);
        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        return this.delegate.loadToken(request);
    }

    private CsrfToken wrap(HttpServletRequest request, CsrfToken token) {
        HttpServletResponse response = this.getResponse(request);
        return new SaveOnAccessCsrfToken(this.delegate, request, response, token);
    }

    private HttpServletResponse getResponse(HttpServletRequest request) {
        HttpServletResponse response = (HttpServletResponse)request.getAttribute(HTTP_RESPONSE_ATTR);
        Assert.notNull((Object)response, () -> "The HttpServletRequest attribute must contain an HttpServletResponse for the attribute " + HTTP_RESPONSE_ATTR);
        return response;
    }

    private static final class SaveOnAccessCsrfToken
    implements CsrfToken {
        private transient CsrfTokenRepository tokenRepository;
        private transient HttpServletRequest request;
        private transient HttpServletResponse response;
        private final CsrfToken delegate;

        SaveOnAccessCsrfToken(CsrfTokenRepository tokenRepository, HttpServletRequest request, HttpServletResponse response, CsrfToken delegate) {
            this.tokenRepository = tokenRepository;
            this.request = request;
            this.response = response;
            this.delegate = delegate;
        }

        @Override
        public String getHeaderName() {
            return this.delegate.getHeaderName();
        }

        @Override
        public String getParameterName() {
            return this.delegate.getParameterName();
        }

        @Override
        public String getToken() {
            this.saveTokenIfNecessary();
            return this.delegate.getToken();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || this.getClass() != obj.getClass()) {
                return false;
            }
            SaveOnAccessCsrfToken other = (SaveOnAccessCsrfToken)obj;
            return !(this.delegate == null ? other.delegate != null : !this.delegate.equals(other.delegate));
        }

        public int hashCode() {
            int prime = 31;
            int result = 1;
            result = 31 * result + (this.delegate == null ? 0 : this.delegate.hashCode());
            return result;
        }

        public String toString() {
            return "SaveOnAccessCsrfToken [delegate=" + this.delegate + "]";
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void saveTokenIfNecessary() {
            if (this.tokenRepository == null) {
                return;
            }
            SaveOnAccessCsrfToken saveOnAccessCsrfToken = this;
            synchronized (saveOnAccessCsrfToken) {
                if (this.tokenRepository != null) {
                    this.tokenRepository.saveToken(this.delegate, this.request, this.response);
                    this.tokenRepository = null;
                    this.request = null;
                    this.response = null;
                }
            }
        }
    }
}

