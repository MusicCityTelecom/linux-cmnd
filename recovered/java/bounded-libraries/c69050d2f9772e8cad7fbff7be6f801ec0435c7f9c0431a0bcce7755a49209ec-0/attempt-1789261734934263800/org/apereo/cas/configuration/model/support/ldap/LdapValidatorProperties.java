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
@JsonFilter(value="LdapValidatorProperties")
public class LdapValidatorProperties
implements Serializable {
    private static final long serialVersionUID = 1150417354213235193L;
    private String type = "search";
    private String baseDn = "";
    private String searchFilter = "(objectClass=*)";
    private String scope = "OBJECT";
    private String attributeName = "objectClass";
    private String attributeValue = "top";
    private String dn = "";

    @Generated
    public String getType() {
        return this.type;
    }

    @Generated
    public String getBaseDn() {
        return this.baseDn;
    }

    @Generated
    public String getSearchFilter() {
        return this.searchFilter;
    }

    @Generated
    public String getScope() {
        return this.scope;
    }

    @Generated
    public String getAttributeName() {
        return this.attributeName;
    }

    @Generated
    public String getAttributeValue() {
        return this.attributeValue;
    }

    @Generated
    public String getDn() {
        return this.dn;
    }

    @Generated
    public LdapValidatorProperties setType(String type) {
        this.type = type;
        return this;
    }

    @Generated
    public LdapValidatorProperties setBaseDn(String baseDn) {
        this.baseDn = baseDn;
        return this;
    }

    @Generated
    public LdapValidatorProperties setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
        return this;
    }

    @Generated
    public LdapValidatorProperties setScope(String scope) {
        this.scope = scope;
        return this;
    }

    @Generated
    public LdapValidatorProperties setAttributeName(String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    @Generated
    public LdapValidatorProperties setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
        return this;
    }

    @Generated
    public LdapValidatorProperties setDn(String dn) {
        this.dn = dn;
        return this;
    }
}

