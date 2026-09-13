/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.authentication.principal.PrincipalFactory
 */
package org.apereo.cas.authentication.principal;

import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.authentication.principal.PrincipalFactory;
import org.apereo.cas.authentication.principal.SimplePrincipal;

public class DefaultPrincipalFactory
implements PrincipalFactory {
    private static final long serialVersionUID = -3999695695604948495L;

    public Principal createPrincipal(String id, Map<String, List<Object>> attributes) {
        return new SimplePrincipal(id, attributes);
    }

    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DefaultPrincipalFactory)) {
            return false;
        }
        DefaultPrincipalFactory other = (DefaultPrincipalFactory)o;
        return other.canEqual(this);
    }

    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof DefaultPrincipalFactory;
    }

    @Generated
    public int hashCode() {
        boolean result = true;
        return 1;
    }
}

