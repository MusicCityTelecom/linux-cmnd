/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationMetaDataPopulator
 */
package org.apereo.cas.authentication.metadata;

import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationMetaDataPopulator;

public abstract class BaseAuthenticationMetaDataPopulator
implements AuthenticationMetaDataPopulator {
    private int order = Integer.MIN_VALUE;

    protected BaseAuthenticationMetaDataPopulator() {
        this(Integer.MIN_VALUE);
    }

    @Generated
    public String toString() {
        return "BaseAuthenticationMetaDataPopulator(order=" + this.order + ")";
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    protected BaseAuthenticationMetaDataPopulator(int order) {
        this.order = order;
    }

    @Generated
    public void setOrder(int order) {
        this.order = order;
    }
}

