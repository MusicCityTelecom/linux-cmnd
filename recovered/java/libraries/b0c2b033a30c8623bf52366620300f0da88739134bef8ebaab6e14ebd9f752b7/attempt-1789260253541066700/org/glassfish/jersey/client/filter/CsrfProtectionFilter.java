/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

public class CsrfProtectionFilter
implements ClientRequestFilter {
    public static final String HEADER_NAME = "X-Requested-By";
    private static final Set<String> METHODS_TO_IGNORE;
    private final String requestedBy;

    public CsrfProtectionFilter() {
        this("");
    }

    public CsrfProtectionFilter(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    @Override
    public void filter(ClientRequestContext rc) throws IOException {
        if (!METHODS_TO_IGNORE.contains(rc.getMethod()) && !rc.getHeaders().containsKey(HEADER_NAME)) {
            rc.getHeaders().add(HEADER_NAME, this.requestedBy);
        }
    }

    static {
        HashSet<String> mti = new HashSet<String>();
        mti.add("GET");
        mti.add("OPTIONS");
        mti.add("HEAD");
        METHODS_TO_IGNORE = Collections.unmodifiableSet(mti);
    }
}

