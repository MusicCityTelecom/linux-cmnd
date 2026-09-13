/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.authentication.handler.PrincipalNameTransformer
 */
package org.apereo.cas.util.transforms;

import org.apereo.cas.authentication.handler.PrincipalNameTransformer;

public class NoOpPrincipalNameTransformer
implements PrincipalNameTransformer {
    public String transform(String formUserId) {
        return formUserId.trim();
    }
}

