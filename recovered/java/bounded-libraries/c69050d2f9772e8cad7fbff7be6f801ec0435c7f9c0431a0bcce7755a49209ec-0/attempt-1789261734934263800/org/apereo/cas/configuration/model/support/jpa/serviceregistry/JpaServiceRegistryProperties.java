/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.jpa.serviceregistry;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-jpa-service-registry")
@JsonFilter(value="JpaServiceRegistryProperties")
public class JpaServiceRegistryProperties
extends AbstractJpaProperties {
    private static final long serialVersionUID = 352435146313504995L;
    @RequiredProperty
    private boolean enabled = true;

    public JpaServiceRegistryProperties() {
        super.setUrl("jdbc:hsqldb:mem:cas-service-registry");
    }

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public JpaServiceRegistryProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}

