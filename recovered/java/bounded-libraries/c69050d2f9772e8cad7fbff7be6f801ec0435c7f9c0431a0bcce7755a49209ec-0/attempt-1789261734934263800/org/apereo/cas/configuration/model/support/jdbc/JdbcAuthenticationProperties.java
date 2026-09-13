/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.jdbc;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.jdbc.authn.BindJdbcAuthenticationProperties;
import org.apereo.cas.configuration.model.support.jdbc.authn.QueryEncodeJdbcAuthenticationProperties;
import org.apereo.cas.configuration.model.support.jdbc.authn.QueryJdbcAuthenticationProperties;
import org.apereo.cas.configuration.model.support.jdbc.authn.SearchJdbcAuthenticationProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-jdbc-authentication")
@JsonFilter(value="JdbcAuthenticationProperties")
public class JdbcAuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = 7199786191466526110L;
    private List<SearchJdbcAuthenticationProperties> search = new ArrayList<SearchJdbcAuthenticationProperties>(0);
    private List<QueryEncodeJdbcAuthenticationProperties> encode = new ArrayList<QueryEncodeJdbcAuthenticationProperties>(0);
    private List<QueryJdbcAuthenticationProperties> query = new ArrayList<QueryJdbcAuthenticationProperties>(0);
    private List<BindJdbcAuthenticationProperties> bind = new ArrayList<BindJdbcAuthenticationProperties>(0);

    @Generated
    public List<SearchJdbcAuthenticationProperties> getSearch() {
        return this.search;
    }

    @Generated
    public List<QueryEncodeJdbcAuthenticationProperties> getEncode() {
        return this.encode;
    }

    @Generated
    public List<QueryJdbcAuthenticationProperties> getQuery() {
        return this.query;
    }

    @Generated
    public List<BindJdbcAuthenticationProperties> getBind() {
        return this.bind;
    }

    @Generated
    public JdbcAuthenticationProperties setSearch(List<SearchJdbcAuthenticationProperties> search) {
        this.search = search;
        return this;
    }

    @Generated
    public JdbcAuthenticationProperties setEncode(List<QueryEncodeJdbcAuthenticationProperties> encode) {
        this.encode = encode;
        return this;
    }

    @Generated
    public JdbcAuthenticationProperties setQuery(List<QueryJdbcAuthenticationProperties> query) {
        this.query = query;
        return this;
    }

    @Generated
    public JdbcAuthenticationProperties setBind(List<BindJdbcAuthenticationProperties> bind) {
        this.bind = bind;
        return this;
    }
}

