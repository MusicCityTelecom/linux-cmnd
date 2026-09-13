/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.authentication;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-person-directory", automated=true)
@Deprecated(since="6.2.0")
@JsonFilter(value="ScriptedPrincipalAttributesProperties")
public class ScriptedPrincipalAttributesProperties
extends SpringResourceProperties {
    private static final long serialVersionUID = 4221139939506528713L;
    @Deprecated(since="6.2.0")
    private String engineName;
    @Deprecated(since="6.2.0")
    private boolean caseInsensitive;
    @Deprecated(since="6.2.0")
    private int order;
    @Deprecated(since="6.2.0")
    private String id;

    @Deprecated
    @Generated
    public String getEngineName() {
        return this.engineName;
    }

    @Deprecated
    @Generated
    public boolean isCaseInsensitive() {
        return this.caseInsensitive;
    }

    @Deprecated
    @Generated
    public int getOrder() {
        return this.order;
    }

    @Deprecated
    @Generated
    public String getId() {
        return this.id;
    }

    @Deprecated
    @Generated
    public ScriptedPrincipalAttributesProperties setEngineName(String engineName) {
        this.engineName = engineName;
        return this;
    }

    @Deprecated
    @Generated
    public ScriptedPrincipalAttributesProperties setCaseInsensitive(boolean caseInsensitive) {
        this.caseInsensitive = caseInsensitive;
        return this;
    }

    @Deprecated
    @Generated
    public ScriptedPrincipalAttributesProperties setOrder(int order) {
        this.order = order;
        return this;
    }

    @Deprecated
    @Generated
    public ScriptedPrincipalAttributesProperties setId(String id) {
        this.id = id;
        return this;
    }
}

