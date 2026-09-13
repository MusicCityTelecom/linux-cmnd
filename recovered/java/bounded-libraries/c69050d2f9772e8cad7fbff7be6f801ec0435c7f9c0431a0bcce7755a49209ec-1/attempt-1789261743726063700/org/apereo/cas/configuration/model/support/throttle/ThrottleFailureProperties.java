/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.throttle;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-throttle")
@JsonFilter(value="ThrottleFailureProperties")
public class ThrottleFailureProperties
implements Serializable {
    private static final String DEFAULT_AUTHN_FAILED_ACTION = "AUTHENTICATION_FAILED";
    private static final long serialVersionUID = 7647772524660134142L;
    private String code = "AUTHENTICATION_FAILED";
    private int threshold = -1;
    private int rangeSeconds = -1;
    @DurationCapable
    private String throttleWindowSeconds = "0";

    @Generated
    public String getCode() {
        return this.code;
    }

    @Generated
    public int getThreshold() {
        return this.threshold;
    }

    @Generated
    public int getRangeSeconds() {
        return this.rangeSeconds;
    }

    @Generated
    public String getThrottleWindowSeconds() {
        return this.throttleWindowSeconds;
    }

    @Generated
    public ThrottleFailureProperties setCode(String code) {
        this.code = code;
        return this;
    }

    @Generated
    public ThrottleFailureProperties setThreshold(int threshold) {
        this.threshold = threshold;
        return this;
    }

    @Generated
    public ThrottleFailureProperties setRangeSeconds(int rangeSeconds) {
        this.rangeSeconds = rangeSeconds;
        return this;
    }

    @Generated
    public ThrottleFailureProperties setThrottleWindowSeconds(String throttleWindowSeconds) {
        this.throttleWindowSeconds = throttleWindowSeconds;
        return this;
    }
}

