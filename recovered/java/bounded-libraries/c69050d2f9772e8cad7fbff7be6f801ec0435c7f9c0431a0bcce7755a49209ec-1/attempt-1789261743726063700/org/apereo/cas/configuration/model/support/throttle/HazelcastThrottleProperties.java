/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.throttle;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.hazelcast.BaseHazelcastProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-throttle-hazelcast")
@JsonFilter(value="HazelcastThrottleProperties")
public class HazelcastThrottleProperties
extends BaseHazelcastProperties {
    private static final long serialVersionUID = 5813165633105563813L;
}

