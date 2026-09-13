/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.web.tomcat;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-webapp-tomcat")
@JsonFilter(value="CasEmbeddedApacheTomcatBasicAuthenticationProperties")
public class CasEmbeddedApacheTomcatBasicAuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = 1164446071136700282L;
    @RequiredProperty
    private boolean enabled;
    private List<String> securityRoles = Stream.of("admin").collect(Collectors.toList());
    private List<String> authRoles = Stream.of("admin").collect(Collectors.toList());
    private List<String> patterns = Stream.of("/*").collect(Collectors.toList());

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public List<String> getSecurityRoles() {
        return this.securityRoles;
    }

    @Generated
    public List<String> getAuthRoles() {
        return this.authRoles;
    }

    @Generated
    public List<String> getPatterns() {
        return this.patterns;
    }

    @Generated
    public CasEmbeddedApacheTomcatBasicAuthenticationProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatBasicAuthenticationProperties setSecurityRoles(List<String> securityRoles) {
        this.securityRoles = securityRoles;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatBasicAuthenticationProperties setAuthRoles(List<String> authRoles) {
        this.authRoles = authRoles;
        return this;
    }

    @Generated
    public CasEmbeddedApacheTomcatBasicAuthenticationProperties setPatterns(List<String> patterns) {
        this.patterns = patterns;
        return this;
    }
}

