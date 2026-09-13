/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.core.authentication;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.model.core.authentication.GroovyAuthenticationExceptionsProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-core-authentication", automated=true)
@JsonFilter(value="AuthenticationExceptionsProperties")
public class AuthenticationExceptionsProperties
implements Serializable {
    private static final long serialVersionUID = -2385347572099983874L;
    private List<Class<? extends Throwable>> exceptions = new ArrayList<Class<? extends Throwable>>(0);
    @NestedConfigurationProperty
    private GroovyAuthenticationExceptionsProperties groovy = new GroovyAuthenticationExceptionsProperties();

    @Generated
    public List<Class<? extends Throwable>> getExceptions() {
        return this.exceptions;
    }

    @Generated
    public GroovyAuthenticationExceptionsProperties getGroovy() {
        return this.groovy;
    }

    @Generated
    public AuthenticationExceptionsProperties setExceptions(List<Class<? extends Throwable>> exceptions) {
        this.exceptions = exceptions;
        return this;
    }

    @Generated
    public AuthenticationExceptionsProperties setGroovy(GroovyAuthenticationExceptionsProperties groovy) {
        this.groovy = groovy;
        return this;
    }
}

