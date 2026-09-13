/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.security.core.GrantedAuthority
 *  org.springframework.security.core.authority.GrantedAuthoritiesContainer
 */
package org.springframework.security.web.authentication.preauth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails
extends WebAuthenticationDetails
implements GrantedAuthoritiesContainer {
    private static final long serialVersionUID = 570L;
    private final List<GrantedAuthority> authorities;

    public PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails(HttpServletRequest request, Collection<? extends GrantedAuthority> authorities) {
        super(request);
        ArrayList<? extends GrantedAuthority> temp = new ArrayList<GrantedAuthority>(authorities.size());
        temp.addAll(authorities);
        this.authorities = Collections.unmodifiableList(temp);
    }

    public List<GrantedAuthority> getGrantedAuthorities() {
        return this.authorities;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("; ");
        sb.append(this.authorities);
        return sb.toString();
    }
}

