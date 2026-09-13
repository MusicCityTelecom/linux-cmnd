/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.aup;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.ldap.AbstractLdapSearchProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-aup-ldap")
@JsonFilter(value="LdapAcceptableUsagePolicyProperties")
public class LdapAcceptableUsagePolicyProperties
extends AbstractLdapSearchProperties {
    private static final long serialVersionUID = -7991011278378393382L;
    private String aupAcceptedAttributeValue = Boolean.TRUE.toString().toUpperCase();

    @Generated
    public String getAupAcceptedAttributeValue() {
        return this.aupAcceptedAttributeValue;
    }

    @Generated
    public LdapAcceptableUsagePolicyProperties setAupAcceptedAttributeValue(String aupAcceptedAttributeValue) {
        this.aupAcceptedAttributeValue = aupAcceptedAttributeValue;
        return this;
    }
}

