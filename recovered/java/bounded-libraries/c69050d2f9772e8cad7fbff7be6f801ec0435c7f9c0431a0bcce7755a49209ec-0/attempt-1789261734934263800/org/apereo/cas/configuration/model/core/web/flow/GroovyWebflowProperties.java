/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.web.flow;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-webflow", automated=true)
@JsonFilter(value="GroovyWebflowProperties")
public class GroovyWebflowProperties
extends SpringResourceProperties {
    private static final long serialVersionUID = 8079027843747126083L;
    private Map<String, String> actions = new LinkedHashMap<String, String>();

    @Generated
    public Map<String, String> getActions() {
        return this.actions;
    }

    @Generated
    public GroovyWebflowProperties setActions(Map<String, String> actions) {
        this.actions = actions;
        return this;
    }
}

