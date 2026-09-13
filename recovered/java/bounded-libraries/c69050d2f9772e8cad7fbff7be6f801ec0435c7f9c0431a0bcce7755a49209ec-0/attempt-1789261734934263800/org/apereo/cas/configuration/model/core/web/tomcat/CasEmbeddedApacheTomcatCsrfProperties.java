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
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-webapp-tomcat")
@JsonFilter(value="CasEmbeddedApacheTomcatCsrfProperties")
public class CasEmbeddedApacheTomcatCsrfProperties
implements Serializable {
    private static final long serialVersionUID = -32143821503580896L;
    @RequiredProperty
    private boolean enabled;

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public CasEmbeddedApacheTomcatCsrfProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}

