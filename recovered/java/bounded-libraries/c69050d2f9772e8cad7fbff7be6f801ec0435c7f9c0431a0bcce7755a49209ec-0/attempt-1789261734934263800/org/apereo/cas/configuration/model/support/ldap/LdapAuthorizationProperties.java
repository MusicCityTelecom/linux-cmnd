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
public class LdapAuthorizationProperties
implements Serializable {
    public static final String DEFAULT_ROLE_PREFIX = "ROLE_";
    private static final long serialVersionUID = -2680169790567609780L;
    private String roleAttribute = "uugid";
    private String rolePrefix = "ROLE_";
    private boolean allowMultipleResults;
    private String groupAttribute;
    private String groupPrefix = "";
    private String groupFilter;
    private String groupBaseDn;
    private String baseDn;
    private String searchFilter;

    @Generated
    public String getRoleAttribute() {
        return this.roleAttribute;
    }

    @Generated
    public String getRolePrefix() {
        return this.rolePrefix;
    }

    @Generated
    public boolean isAllowMultipleResults() {
        return this.allowMultipleResults;
    }

    @Generated
    public String getGroupAttribute() {
        return this.groupAttribute;
    }

    @Generated
    public String getGroupPrefix() {
        return this.groupPrefix;
    }

    @Generated
    public String getGroupFilter() {
        return this.groupFilter;
    }

    @Generated
    public String getGroupBaseDn() {
        return this.groupBaseDn;
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
    public LdapAuthorizationProperties setRoleAttribute(String roleAttribute) {
        this.roleAttribute = roleAttribute;
        return this;
    }

    @Generated
    public LdapAuthorizationProperties setRolePrefix(String rolePrefix) {
        this.rolePrefix = rolePrefix;
        return this;
    }

    @Generated
    public LdapAuthorizationProperties setAllowMultipleResults(boolean allowMultipleResults) {
        this.allowMultipleResults = allowMultipleResults;
        return this;
    }

    @Generated
    public LdapAuthorizationProperties setGroupAttribute(String groupAttribute) {
        this.groupAttribute = groupAttribute;
        return this;
    }

    @Generated
    public LdapAuthorizationProperties setGroupPrefix(String groupPrefix) {
        this.groupPrefix = groupPrefix;
        return this;
    }

    @Generated
    public LdapAuthorizationProperties setGroupFilter(String groupFilter) {
        this.groupFilter = groupFilter;
        return this;
    }

    @Generated
    public LdapAuthorizationProperties setGroupBaseDn(String groupBaseDn) {
        this.groupBaseDn = groupBaseDn;
        return this;
    }

    @Generated
    public LdapAuthorizationProperties setBaseDn(String baseDn) {
        this.baseDn = baseDn;
        return this;
    }

    @Generated
    public LdapAuthorizationProperties setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
        return this;
    }
}

