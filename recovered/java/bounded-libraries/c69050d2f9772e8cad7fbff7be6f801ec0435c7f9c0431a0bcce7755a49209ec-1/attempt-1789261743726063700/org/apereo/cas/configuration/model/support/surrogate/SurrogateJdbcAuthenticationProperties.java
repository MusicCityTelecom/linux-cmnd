/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.surrogate;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-surrogate-authentication-jdbc")
@JsonFilter(value="SurrogateJdbcAuthenticationProperties")
public class SurrogateJdbcAuthenticationProperties
extends AbstractJpaProperties {
    private static final long serialVersionUID = 8970195444880123796L;
    @RequiredProperty
    private String surrogateSearchQuery = "SELECT COUNT(*) FROM surrogate WHERE username=?";
    @RequiredProperty
    private String surrogateAccountQuery = "SELECT surrogate_user AS surrogateAccount FROM surrogate WHERE username=?";

    @Generated
    public String getSurrogateSearchQuery() {
        return this.surrogateSearchQuery;
    }

    @Generated
    public String getSurrogateAccountQuery() {
        return this.surrogateAccountQuery;
    }

    @Generated
    public SurrogateJdbcAuthenticationProperties setSurrogateSearchQuery(String surrogateSearchQuery) {
        this.surrogateSearchQuery = surrogateSearchQuery;
        return this;
    }

    @Generated
    public SurrogateJdbcAuthenticationProperties setSurrogateAccountQuery(String surrogateAccountQuery) {
        this.surrogateAccountQuery = surrogateAccountQuery;
        return this;
    }
}

