/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.bucket4j;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.bucket4j.Bucket4jBandwidthLimitProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-bucket4j-core")
@JsonFilter(value="BaseBucket4jProperties")
public abstract class BaseBucket4jProperties
implements Serializable {
    private static final long serialVersionUID = 1813165633105563813L;
    private boolean enabled = true;
    private boolean blocking = true;
    private List<Bucket4jBandwidthLimitProperties> bandwidth = new ArrayList<Bucket4jBandwidthLimitProperties>();

    @Generated
    public boolean isEnabled() {
        return this.enabled;
    }

    @Generated
    public boolean isBlocking() {
        return this.blocking;
    }

    @Generated
    public List<Bucket4jBandwidthLimitProperties> getBandwidth() {
        return this.bandwidth;
    }

    @Generated
    public BaseBucket4jProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Generated
    public BaseBucket4jProperties setBlocking(boolean blocking) {
        this.blocking = blocking;
        return this;
    }

    @Generated
    public BaseBucket4jProperties setBandwidth(List<Bucket4jBandwidthLimitProperties> bandwidth) {
        this.bandwidth = bandwidth;
        return this;
    }
}

