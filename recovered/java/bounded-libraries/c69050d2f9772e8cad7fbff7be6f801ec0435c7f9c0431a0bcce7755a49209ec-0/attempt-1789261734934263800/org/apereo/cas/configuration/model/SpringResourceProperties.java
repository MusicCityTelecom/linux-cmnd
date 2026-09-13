/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.configuration.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.core.io.Resource;

@RequiresModule(name="cas-server-core-util", automated=true)
@JsonFilter(value="SpringResourceProperties")
public class SpringResourceProperties
implements Serializable {
    private static final long serialVersionUID = 4142130961445546358L;
    @RequiredProperty
    private transient Resource location;

    @Generated
    public Resource getLocation() {
        return this.location;
    }

    @Generated
    public SpringResourceProperties setLocation(Resource location) {
        this.location = location;
        return this;
    }

    @Generated
    public String toString() {
        return "SpringResourceProperties(location=" + this.location + ")";
    }
}

