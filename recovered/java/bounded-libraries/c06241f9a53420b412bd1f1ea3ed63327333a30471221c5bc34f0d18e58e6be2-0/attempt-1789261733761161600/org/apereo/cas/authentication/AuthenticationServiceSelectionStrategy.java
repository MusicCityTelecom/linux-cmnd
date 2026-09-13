/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.authentication;

import java.io.Serializable;
import org.apereo.cas.authentication.principal.Service;
import org.springframework.core.Ordered;

public interface AuthenticationServiceSelectionStrategy
extends Serializable,
Ordered {
    public Service resolveServiceFrom(Service var1);

    public boolean supports(Service var1);
}

