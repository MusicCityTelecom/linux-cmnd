/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.ldap;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-ldap")
public class PrimaryGroupIdSearchEntryHandlersProperties
implements Serializable {
    private static final long serialVersionUID = 539574118704476712L;
    private String groupFilter = "(&(objectClass=group)(objectSid={0}))";
    private String baseDn;

    @Generated
    public String getGroupFilter() {
        return this.groupFilter;
    }

    @Generated
    public String getBaseDn() {
        return this.baseDn;
    }

    @Generated
    public PrimaryGroupIdSearchEntryHandlersProperties setGroupFilter(String groupFilter) {
        this.groupFilter = groupFilter;
        return this;
    }

    @Generated
    public PrimaryGroupIdSearchEntryHandlersProperties setBaseDn(String baseDn) {
        this.baseDn = baseDn;
        return this;
    }
}

