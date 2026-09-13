/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.custom;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-web", automated=true)
@JsonFilter(value="CasCustomProperties")
public class CasCustomProperties
implements Serializable {
    private static final long serialVersionUID = 5354004353286722083L;
    private Map<String, String> properties = new HashMap<String, String>(0);

    @Generated
    public Map<String, String> getProperties() {
        return this.properties;
    }

    @Generated
    public CasCustomProperties setProperties(Map<String, String> properties) {
        this.properties = properties;
        return this;
    }
}

