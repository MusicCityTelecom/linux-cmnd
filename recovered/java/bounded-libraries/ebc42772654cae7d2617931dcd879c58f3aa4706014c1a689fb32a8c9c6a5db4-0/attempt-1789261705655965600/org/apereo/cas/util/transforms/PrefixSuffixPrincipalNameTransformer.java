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

public class PrefixSuffixPrincipalNameTransformer
implements PrincipalNameTransformer {
    private String prefix;
    private String suffix;

    public String transform(String formUserId) {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.prefix != null) {
            stringBuilder.append(this.prefix);
        }
        stringBuilder.append(formUserId);
        if (this.suffix != null) {
            stringBuilder.append(this.suffix);
        }
        return stringBuilder.toString();
    }

    @Generated
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Generated
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Generated
    public PrefixSuffixPrincipalNameTransformer() {
    }

    @Generated
    public PrefixSuffixPrincipalNameTransformer(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }
}

