/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.authentication.principal;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apereo.cas.authentication.principal.Principal;

@FunctionalInterface
public interface PrincipalFactory
extends Serializable {
    default public Principal createPrincipal(String id) {
        return this.createPrincipal(id, new HashMap<String, List<Object>>(0));
    }

    public Principal createPrincipal(String var1, Map<String, List<Object>> var2);
}

