/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.uma;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.uma.UmaResourceSetJpaProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-oauth-uma")
@JsonFilter(value="UmaResourceSetProperties")
public class UmaResourceSetProperties
implements Serializable {
    private static final long serialVersionUID = 215435145313504895L;
    @NestedConfigurationProperty
    private UmaResourceSetJpaProperties jpa = new UmaResourceSetJpaProperties();

    @Generated
    public UmaResourceSetJpaProperties getJpa() {
        return this.jpa;
    }

    @Generated
    public UmaResourceSetProperties setJpa(UmaResourceSetJpaProperties jpa) {
        this.jpa = jpa;
        return this;
    }
}

