/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.saml.idp;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-saml-idp")
@JsonFilter(value="SamlIdPServicesProperties")
public class SamlIdPServicesProperties
implements Serializable {
    private static final long serialVersionUID = 7211477683583467619L;
    private final Map<String, String> defaults = new LinkedHashMap<String, String>();

    @Generated
    public Map<String, String> getDefaults() {
        return this.defaults;
    }
}

