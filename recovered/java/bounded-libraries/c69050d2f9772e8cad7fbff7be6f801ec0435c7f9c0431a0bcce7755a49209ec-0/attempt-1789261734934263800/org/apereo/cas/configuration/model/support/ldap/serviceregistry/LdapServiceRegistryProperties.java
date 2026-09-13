/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.ldap.serviceregistry;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.ldap.AbstractLdapSearchProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-ldap-service-registry")
@JsonFilter(value="LdapServiceRegistryProperties")
public class LdapServiceRegistryProperties
extends AbstractLdapSearchProperties {
    private static final long serialVersionUID = 2372867394066286022L;
    private String objectClass = "casRegisteredService";
    private String idAttribute = "uid";
    private String serviceDefinitionAttribute = "description";
    private String loadFilter = "(objectClass=%s)";

    public LdapServiceRegistryProperties() {
        this.setSearchFilter("(%s={0})");
    }

    @Override
    public String getSearchFilter() {
        return String.format(this.searchFilter, this.getIdAttribute());
    }

    public String getLoadFilter() {
        return String.format(this.loadFilter, this.getObjectClass());
    }

    @Generated
    public String getObjectClass() {
        return this.objectClass;
    }

    @Generated
    public String getIdAttribute() {
        return this.idAttribute;
    }

    @Generated
    public String getServiceDefinitionAttribute() {
        return this.serviceDefinitionAttribute;
    }

    @Generated
    public LdapServiceRegistryProperties setObjectClass(String objectClass) {
        this.objectClass = objectClass;
        return this;
    }

    @Generated
    public LdapServiceRegistryProperties setIdAttribute(String idAttribute) {
        this.idAttribute = idAttribute;
        return this;
    }

    @Generated
    public LdapServiceRegistryProperties setServiceDefinitionAttribute(String serviceDefinitionAttribute) {
        this.serviceDefinitionAttribute = serviceDefinitionAttribute;
        return this;
    }

    @Generated
    public LdapServiceRegistryProperties setLoadFilter(String loadFilter) {
        this.loadFilter = loadFilter;
        return this;
    }
}

