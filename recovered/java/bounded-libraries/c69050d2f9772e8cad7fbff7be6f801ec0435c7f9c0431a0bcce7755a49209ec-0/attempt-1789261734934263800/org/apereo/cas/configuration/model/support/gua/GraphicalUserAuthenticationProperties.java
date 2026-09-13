/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.gua;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.gua.LdapGraphicalUserAuthenticationProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-gua")
public class GraphicalUserAuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = 7527953699378415460L;
    @NestedConfigurationProperty
    private LdapGraphicalUserAuthenticationProperties ldap = new LdapGraphicalUserAuthenticationProperties();
    private Map<String, String> simple = new LinkedHashMap<String, String>();

    @Generated
    public LdapGraphicalUserAuthenticationProperties getLdap() {
        return this.ldap;
    }

    @Generated
    public Map<String, String> getSimple() {
        return this.simple;
    }

    @Generated
    public GraphicalUserAuthenticationProperties setLdap(LdapGraphicalUserAuthenticationProperties ldap) {
        this.ldap = ldap;
        return this;
    }

    @Generated
    public GraphicalUserAuthenticationProperties setSimple(Map<String, String> simple) {
        this.simple = simple;
        return this;
    }
}

