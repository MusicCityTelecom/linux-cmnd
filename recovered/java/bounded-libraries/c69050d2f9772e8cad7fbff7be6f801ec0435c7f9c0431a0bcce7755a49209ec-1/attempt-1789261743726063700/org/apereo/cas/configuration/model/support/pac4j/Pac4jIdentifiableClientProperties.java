/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.pac4j;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.pac4j.Pac4jBaseClientProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-pac4j-webflow")
@JsonFilter(value="Pac4jIdentifiableClientProperties")
public class Pac4jIdentifiableClientProperties
extends Pac4jBaseClientProperties {
    private static final long serialVersionUID = 3007013267786902465L;
    @RequiredProperty
    private String id;
    @RequiredProperty
    private String secret;

    @Generated
    public String getId() {
        return this.id;
    }

    @Generated
    public String getSecret() {
        return this.secret;
    }

    @Generated
    public Pac4jIdentifiableClientProperties setId(String id) {
        this.id = id;
        return this;
    }

    @Generated
    public Pac4jIdentifiableClientProperties setSecret(String secret) {
        this.secret = secret;
        return this;
    }
}

