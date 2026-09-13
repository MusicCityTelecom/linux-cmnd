/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.handler.PrincipalNameTransformer
 */
package org.apereo.cas.util.transforms;

import lombok.Generated;
import org.apereo.cas.authentication.handler.PrincipalNameTransformer;

public class ConvertCasePrincipalNameTransformer
implements PrincipalNameTransformer {
    private boolean toUpperCase;

    public String transform(String formUserId) {
        String result = formUserId.trim();
        return this.toUpperCase ? result.toUpperCase() : result.toLowerCase();
    }

    @Generated
    public void setToUpperCase(boolean toUpperCase) {
        this.toUpperCase = toUpperCase;
    }

    @Generated
    public ConvertCasePrincipalNameTransformer() {
    }

    @Generated
    public ConvertCasePrincipalNameTransformer(boolean toUpperCase) {
        this.toUpperCase = toUpperCase;
    }
}

