/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.ldap;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-ldap")
@JsonFilter(value="FollowReferralSearchEntryHandlersProperties")
public class FollowReferralSearchEntryHandlersProperties
implements Serializable {
    private static final long serialVersionUID = 7138108925310792763L;
    private int limit = 10;

    @Generated
    public int getLimit() {
        return this.limit;
    }

    @Generated
    public FollowReferralSearchEntryHandlersProperties setLimit(int limit) {
        this.limit = limit;
        return this;
    }
}

