/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.core.monitor;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.memcached.BaseMemcachedProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-memcached-monitor")
@JsonFilter(value="MemcachedMonitorProperties")
public class MemcachedMonitorProperties
extends BaseMemcachedProperties {
    private static final long serialVersionUID = -9139788158851782673L;
}

