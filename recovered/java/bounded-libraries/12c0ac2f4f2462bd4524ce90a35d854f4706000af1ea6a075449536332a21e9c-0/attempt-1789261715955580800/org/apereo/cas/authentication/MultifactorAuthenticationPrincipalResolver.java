/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.principal.Principal
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.authentication;

import org.apereo.cas.authentication.principal.Principal;
import org.springframework.core.Ordered;

@FunctionalInterface
public interface MultifactorAuthenticationPrincipalResolver
extends Ordered {
    public static MultifactorAuthenticationPrincipalResolver identical() {
        return principal -> principal;
    }

    default public int getOrder() {
        return Integer.MAX_VALUE;
    }

    default public boolean supports(Principal principal) {
        return principal != null;
    }

    public Principal resolve(Principal var1);
}

