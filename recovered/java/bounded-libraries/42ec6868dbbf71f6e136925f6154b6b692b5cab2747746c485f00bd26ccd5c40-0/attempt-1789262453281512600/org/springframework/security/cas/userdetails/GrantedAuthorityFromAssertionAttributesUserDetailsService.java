/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jasig.cas.client.validation.Assertion
 *  org.springframework.security.core.authority.SimpleGrantedAuthority
 *  org.springframework.security.core.userdetails.User
 *  org.springframework.security.core.userdetails.UserDetails
 *  org.springframework.util.Assert
 */
package org.springframework.security.cas.userdetails;

import java.util.ArrayList;
import java.util.List;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.security.cas.userdetails.AbstractCasAssertionUserDetailsService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

public final class GrantedAuthorityFromAssertionAttributesUserDetailsService
extends AbstractCasAssertionUserDetailsService {
    private static final String NON_EXISTENT_PASSWORD_VALUE = "NO_PASSWORD";
    private final String[] attributes;
    private boolean convertToUpperCase = true;

    public GrantedAuthorityFromAssertionAttributesUserDetailsService(String[] attributes) {
        Assert.notNull((Object)attributes, (String)"attributes cannot be null.");
        Assert.isTrue((attributes.length > 0 ? 1 : 0) != 0, (String)"At least one attribute is required to retrieve roles from.");
        this.attributes = attributes;
    }

    @Override
    protected UserDetails loadUserDetails(Assertion assertion) {
        ArrayList<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<SimpleGrantedAuthority>();
        for (String attribute : this.attributes) {
            Object value = assertion.getPrincipal().getAttributes().get(attribute);
            if (value == null) continue;
            if (value instanceof List) {
                for (Object o : (List)value) {
                    grantedAuthorities.add(this.createSimpleGrantedAuthority(o));
                }
                continue;
            }
            grantedAuthorities.add(this.createSimpleGrantedAuthority(value));
        }
        return new User(assertion.getPrincipal().getName(), NON_EXISTENT_PASSWORD_VALUE, true, true, true, true, grantedAuthorities);
    }

    private SimpleGrantedAuthority createSimpleGrantedAuthority(Object o) {
        return new SimpleGrantedAuthority(this.convertToUpperCase ? o.toString().toUpperCase() : o.toString());
    }

    public void setConvertToUpperCase(boolean convertToUpperCase) {
        this.convertToUpperCase = convertToUpperCase;
    }
}

