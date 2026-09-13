/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.surrogate;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.ldap.AbstractLdapSearchProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-surrogate-webflow")
@JsonFilter(value="SurrogateSimpleAuthenticationProperties")
public class SurrogateSimpleAuthenticationProperties
extends AbstractLdapSearchProperties {
    private static final long serialVersionUID = 16938920863432222L;
    private Map<String, String> surrogates = new LinkedHashMap<String, String>(2);

    @Generated
    public Map<String, String> getSurrogates() {
        return this.surrogates;
    }

    @Generated
    public SurrogateSimpleAuthenticationProperties setSurrogates(Map<String, String> surrogates) {
        this.surrogates = surrogates;
        return this;
    }
}

