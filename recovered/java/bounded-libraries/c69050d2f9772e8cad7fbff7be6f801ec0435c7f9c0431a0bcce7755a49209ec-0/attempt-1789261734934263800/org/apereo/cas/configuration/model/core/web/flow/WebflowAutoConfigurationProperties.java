/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.web.flow;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-webflow", automated=true)
@JsonFilter(value="WebflowAutoConfigurationProperties")
public class WebflowAutoConfigurationProperties
implements Serializable {
    private static final long serialVersionUID = 2441628331918226505L;
    private int order;
    private boolean enabled = true;

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public WebflowAutoConfigurationProperties setOrder(int order) {
        this.order = order;
        return this;
    }

    @Generated
    public WebflowAutoConfigurationProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}

