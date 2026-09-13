/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.throttle;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.bucket4j.BaseBucket4jProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-throttle-bucket4j")
@JsonFilter(value="Bucket4jThrottleProperties")
public class Bucket4jThrottleProperties
extends BaseBucket4jProperties {
    private static final long serialVersionUID = 5813165633105563813L;
}

