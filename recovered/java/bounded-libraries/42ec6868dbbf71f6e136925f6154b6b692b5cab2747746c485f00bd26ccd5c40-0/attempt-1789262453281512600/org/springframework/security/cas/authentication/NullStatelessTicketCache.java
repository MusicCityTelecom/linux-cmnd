/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.security.cas.authentication;

import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.cas.authentication.StatelessTicketCache;

public final class NullStatelessTicketCache
implements StatelessTicketCache {
    @Override
    public CasAuthenticationToken getByTicketId(String serviceTicket) {
        return null;
    }

    @Override
    public void putTicketInCache(CasAuthenticationToken token) {
    }

    @Override
    public void removeTicketFromCache(CasAuthenticationToken token) {
    }

    @Override
    public void removeTicketFromCache(String serviceTicket) {
    }
}

