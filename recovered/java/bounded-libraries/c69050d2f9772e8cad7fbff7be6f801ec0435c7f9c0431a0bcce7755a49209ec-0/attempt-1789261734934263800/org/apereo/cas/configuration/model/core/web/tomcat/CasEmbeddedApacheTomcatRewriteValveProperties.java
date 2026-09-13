/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.core.io.ClassPathResource
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.configuration.model.core.web.tomcat;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@RequiresModule(name="cas-server-webapp-tomcat")
@JsonFilter(value="CasEmbeddedApacheTomcatRewriteValveProperties")
public class CasEmbeddedApacheTomcatRewriteValveProperties
implements Serializable {
    private static final long serialVersionUID = 9030094143985594411L;
    @RequiredProperty
    private transient Resource location = new ClassPathResource("container/tomcat/rewrite.config");

    @Generated
    public Resource getLocation() {
        return this.location;
    }

    @Generated
    public CasEmbeddedApacheTomcatRewriteValveProperties setLocation(Resource location) {
        this.location = location;
        return this;
    }
}

