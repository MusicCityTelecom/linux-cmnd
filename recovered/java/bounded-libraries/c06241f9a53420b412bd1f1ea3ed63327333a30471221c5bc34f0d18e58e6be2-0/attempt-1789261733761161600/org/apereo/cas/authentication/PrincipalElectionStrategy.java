/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.services.persondir.support.merger.IAttributeMerger
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.authentication;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.services.persondir.support.merger.IAttributeMerger;
import org.springframework.core.Ordered;

public interface PrincipalElectionStrategy
extends Serializable,
Ordered {
    public static final String BEAN_NAME = "principalElectionStrategy";

    public Principal nominate(Collection<Authentication> var1, Map<String, List<Object>> var2);

    public Principal nominate(List<Principal> var1, Map<String, List<Object>> var2);

    default public int getOrder() {
        return Integer.MAX_VALUE;
    }

    public IAttributeMerger getAttributeMerger();
}

