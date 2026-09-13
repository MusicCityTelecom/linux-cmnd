/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jasig.cas.client.validation.Assertion
 *  org.springframework.security.authentication.AbstractAuthenticationToken
 *  org.springframework.security.core.GrantedAuthority
 *  org.springframework.security.core.userdetails.UserDetails
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.security.cas.authentication;

import java.io.Serializable;
import java.util.Collection;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

public class CasAuthenticationToken
extends AbstractAuthenticationToken
implements Serializable {
    private static final long serialVersionUID = 570L;
    private final Object credentials;
    private final Object principal;
    private final UserDetails userDetails;
    private final int keyHash;
    private final Assertion assertion;

    public CasAuthenticationToken(String key, Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, UserDetails userDetails, Assertion assertion) {
        this(CasAuthenticationToken.extractKeyHash(key), principal, credentials, authorities, userDetails, assertion);
    }

    private CasAuthenticationToken(Integer keyHash, Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, UserDetails userDetails, Assertion assertion) {
        super(authorities);
        if (principal == null || "".equals(principal) || credentials == null || "".equals(credentials) || authorities == null || userDetails == null || assertion == null) {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }
        this.keyHash = keyHash;
        this.principal = principal;
        this.credentials = credentials;
        this.userDetails = userDetails;
        this.assertion = assertion;
        this.setAuthenticated(true);
    }

    private static Integer extractKeyHash(String key) {
        Assert.hasLength((String)key, (String)"key cannot be null or empty");
        return key.hashCode();
    }

    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (obj instanceof CasAuthenticationToken) {
            CasAuthenticationToken test = (CasAuthenticationToken)obj;
            if (!this.assertion.equals(test.getAssertion())) {
                return false;
            }
            return this.getKeyHash() == test.getKeyHash();
        }
        return false;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + this.credentials.hashCode();
        result = 31 * result + this.principal.hashCode();
        result = 31 * result + this.userDetails.hashCode();
        result = 31 * result + this.keyHash;
        result = 31 * result + ObjectUtils.nullSafeHashCode((Object)this.assertion);
        return result;
    }

    public Object getCredentials() {
        return this.credentials;
    }

    public int getKeyHash() {
        return this.keyHash;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public Assertion getAssertion() {
        return this.assertion;
    }

    public UserDetails getUserDetails() {
        return this.userDetails;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(" Assertion: ").append(this.assertion);
        sb.append(" Credentials (Service/Proxy Ticket): ").append(this.credentials);
        return sb.toString();
    }
}

