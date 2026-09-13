/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.security.cas.authentication;

import org.springframework.security.cas.authentication.CasAuthenticationToken;

public interface StatelessTicketCache {
    public CasAuthenticationToken getByTicketId(String var1);

    public void putTicketInCache(CasAuthenticationToken var1);

    public void removeTicketFromCache(CasAuthenticationToken var1);

    public void removeTicketFromCache(String var1);
}

