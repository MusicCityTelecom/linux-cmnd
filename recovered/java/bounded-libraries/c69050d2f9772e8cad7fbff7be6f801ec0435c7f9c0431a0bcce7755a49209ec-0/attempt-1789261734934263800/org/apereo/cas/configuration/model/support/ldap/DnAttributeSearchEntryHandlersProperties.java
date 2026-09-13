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
@JsonFilter(value="DnAttributeSearchEntryHandlersProperties")
public class DnAttributeSearchEntryHandlersProperties
implements Serializable {
    private static final long serialVersionUID = -1174594647679213858L;
    private String dnAttributeName = "entryDN";
    private boolean addIfExists;

    @Generated
    public String getDnAttributeName() {
        return this.dnAttributeName;
    }

    @Generated
    public boolean isAddIfExists() {
        return this.addIfExists;
    }

    @Generated
    public DnAttributeSearchEntryHandlersProperties setDnAttributeName(String dnAttributeName) {
        this.dnAttributeName = dnAttributeName;
        return this;
    }

    @Generated
    public DnAttributeSearchEntryHandlersProperties setAddIfExists(boolean addIfExists) {
        this.addIfExists = addIfExists;
        return this;
    }
}

