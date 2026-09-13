/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.spnego;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-spnego")
@JsonFilter(value="SpnegoAuthenticationProperties")
public class SpnegoAuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = 4513529663377430783L;
    private long cachePolicy = 600L;
    private String jcifsDomainController;
    private String jcifsDomain;
    private String jcifsPassword;
    private String jcifsServicePassword;
    @RequiredProperty
    private String jcifsServicePrincipal = "HTTP/cas.example.com@EXAMPLE.COM";
    @DurationCapable
    private String timeout = "PT5M";
    private String jcifsNetbiosWins;
    private String jcifsUsername;

    @Generated
    public long getCachePolicy() {
        return this.cachePolicy;
    }

    @Generated
    public String getJcifsDomainController() {
        return this.jcifsDomainController;
    }

    @Generated
    public String getJcifsDomain() {
        return this.jcifsDomain;
    }

    @Generated
    public String getJcifsPassword() {
        return this.jcifsPassword;
    }

    @Generated
    public String getJcifsServicePassword() {
        return this.jcifsServicePassword;
    }

    @Generated
    public String getJcifsServicePrincipal() {
        return this.jcifsServicePrincipal;
    }

    @Generated
    public String getTimeout() {
        return this.timeout;
    }

    @Generated
    public String getJcifsNetbiosWins() {
        return this.jcifsNetbiosWins;
    }

    @Generated
    public String getJcifsUsername() {
        return this.jcifsUsername;
    }

    @Generated
    public SpnegoAuthenticationProperties setCachePolicy(long cachePolicy) {
        this.cachePolicy = cachePolicy;
        return this;
    }

    @Generated
    public SpnegoAuthenticationProperties setJcifsDomainController(String jcifsDomainController) {
        this.jcifsDomainController = jcifsDomainController;
        return this;
    }

    @Generated
    public SpnegoAuthenticationProperties setJcifsDomain(String jcifsDomain) {
        this.jcifsDomain = jcifsDomain;
        return this;
    }

    @Generated
    public SpnegoAuthenticationProperties setJcifsPassword(String jcifsPassword) {
        this.jcifsPassword = jcifsPassword;
        return this;
    }

    @Generated
    public SpnegoAuthenticationProperties setJcifsServicePassword(String jcifsServicePassword) {
        this.jcifsServicePassword = jcifsServicePassword;
        return this;
    }

    @Generated
    public SpnegoAuthenticationProperties setJcifsServicePrincipal(String jcifsServicePrincipal) {
        this.jcifsServicePrincipal = jcifsServicePrincipal;
        return this;
    }

    @Generated
    public SpnegoAuthenticationProperties setTimeout(String timeout) {
        this.timeout = timeout;
        return this;
    }

    @Generated
    public SpnegoAuthenticationProperties setJcifsNetbiosWins(String jcifsNetbiosWins) {
        this.jcifsNetbiosWins = jcifsNetbiosWins;
        return this;
    }

    @Generated
    public SpnegoAuthenticationProperties setJcifsUsername(String jcifsUsername) {
        this.jcifsUsername = jcifsUsername;
        return this;
    }
}

