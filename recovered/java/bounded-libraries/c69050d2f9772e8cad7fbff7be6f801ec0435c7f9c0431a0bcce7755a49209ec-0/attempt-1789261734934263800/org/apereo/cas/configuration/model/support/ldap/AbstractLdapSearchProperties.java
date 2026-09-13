/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.ldap;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.ldap.AbstractLdapProperties;
import org.apereo.cas.configuration.model.support.ldap.LdapSearchEntryHandlersProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-ldap")
@JsonFilter(value="AbstractLdapSearchProperties")
public abstract class AbstractLdapSearchProperties
extends AbstractLdapProperties {
    private static final long serialVersionUID = 3009946735155362639L;
    @RequiredProperty
    protected String searchFilter;
    private boolean subtreeSearch = true;
    private int pageSize;
    @RequiredProperty
    private String baseDn;
    private List<LdapSearchEntryHandlersProperties> searchEntryHandlers = new ArrayList<LdapSearchEntryHandlersProperties>(0);

    @Generated
    public String getSearchFilter() {
        return this.searchFilter;
    }

    @Generated
    public boolean isSubtreeSearch() {
        return this.subtreeSearch;
    }

    @Generated
    public int getPageSize() {
        return this.pageSize;
    }

    @Generated
    public String getBaseDn() {
        return this.baseDn;
    }

    @Generated
    public List<LdapSearchEntryHandlersProperties> getSearchEntryHandlers() {
        return this.searchEntryHandlers;
    }

    @Generated
    public AbstractLdapSearchProperties setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
        return this;
    }

    @Generated
    public AbstractLdapSearchProperties setSubtreeSearch(boolean subtreeSearch) {
        this.subtreeSearch = subtreeSearch;
        return this;
    }

    @Generated
    public AbstractLdapSearchProperties setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @Generated
    public AbstractLdapSearchProperties setBaseDn(String baseDn) {
        this.baseDn = baseDn;
        return this;
    }

    @Generated
    public AbstractLdapSearchProperties setSearchEntryHandlers(List<LdapSearchEntryHandlersProperties> searchEntryHandlers) {
        this.searchEntryHandlers = searchEntryHandlers;
        return this;
    }
}

