/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.x509;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-x509-webflow")
@JsonFilter(value="SerialNoPrincipalResolverProperties")
public class SerialNoPrincipalResolverProperties
implements Serializable {
    private static final long serialVersionUID = -4935371089672080311L;
    private int principalSNRadix;
    private boolean principalHexSNZeroPadding;

    @Generated
    public int getPrincipalSNRadix() {
        return this.principalSNRadix;
    }

    @Generated
    public boolean isPrincipalHexSNZeroPadding() {
        return this.principalHexSNZeroPadding;
    }

    @Generated
    public SerialNoPrincipalResolverProperties setPrincipalSNRadix(int principalSNRadix) {
        this.principalSNRadix = principalSNRadix;
        return this;
    }

    @Generated
    public SerialNoPrincipalResolverProperties setPrincipalHexSNZeroPadding(boolean principalHexSNZeroPadding) {
        this.principalHexSNZeroPadding = principalHexSNZeroPadding;
        return this;
    }
}

