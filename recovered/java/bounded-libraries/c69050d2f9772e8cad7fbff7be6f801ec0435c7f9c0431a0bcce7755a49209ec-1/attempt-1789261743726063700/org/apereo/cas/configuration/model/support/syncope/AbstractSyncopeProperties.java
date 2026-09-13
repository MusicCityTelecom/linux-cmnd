/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.syncope;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-syncope-authentication")
@JsonFilter(value="AbstractSyncopeProperties")
public abstract class AbstractSyncopeProperties
implements Serializable {
    private static final long serialVersionUID = 98513672245088L;
    @RequiredProperty
    private String domain = "Master";
    @RequiredProperty
    private String url;
    private Map<String, String> attributeMappings = new LinkedHashMap<String, String>();

    @Generated
    public String getDomain() {
        return this.domain;
    }

    @Generated
    public String getUrl() {
        return this.url;
    }

    @Generated
    public Map<String, String> getAttributeMappings() {
        return this.attributeMappings;
    }

    @Generated
    public AbstractSyncopeProperties setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    @Generated
    public AbstractSyncopeProperties setUrl(String url) {
        this.url = url;
        return this;
    }

    @Generated
    public AbstractSyncopeProperties setAttributeMappings(Map<String, String> attributeMappings) {
        this.attributeMappings = attributeMappings;
        return this;
    }
}

