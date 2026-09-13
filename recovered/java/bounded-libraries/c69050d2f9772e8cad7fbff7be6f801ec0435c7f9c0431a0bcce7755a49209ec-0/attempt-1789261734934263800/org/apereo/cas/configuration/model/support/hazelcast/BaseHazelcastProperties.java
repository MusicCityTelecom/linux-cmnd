/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.hazelcast;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.features.CasFeatureModule;
import org.apereo.cas.configuration.model.support.hazelcast.HazelcastClusterProperties;
import org.apereo.cas.configuration.model.support.hazelcast.HazelcastCoreProperties;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-hazelcast-core")
@JsonFilter(value="BaseHazelcastProperties")
public class BaseHazelcastProperties
implements Serializable,
CasFeatureModule {
    public static final String SHUT_DOWN_HOOK_ENABLED_PROP = "hazelcast.shutdownhook.enabled";
    public static final String SOCKET_BIND_ANY_PROP = "hazelcast.socket.bind.any";
    public static final String LOGGING_TYPE_PROP = "hazelcast.logging.type";
    public static final String HAZELCAST_DISCOVERY_ENABLED_PROP = "hazelcast.discovery.enabled";
    public static final String HAZELCAST_LOCAL_ADDRESS_PROP = "hazelcast.local.localAddress";
    public static final String HAZELCAST_PUBLIC_ADDRESS_PROP = "hazelcast.local.publicAddress";
    public static final String MAX_HEARTBEAT_SECONDS_PROP = "hazelcast.max.no.heartbeat.seconds";
    public static final String IPV4_STACK_PROP = "hazelcast.prefer.ipv4.stack";
    private static final long serialVersionUID = 4204884717547468480L;
    @NestedConfigurationProperty
    private HazelcastClusterProperties cluster = new HazelcastClusterProperties();
    @NestedConfigurationProperty
    private HazelcastCoreProperties core = new HazelcastCoreProperties();

    @Generated
    public HazelcastClusterProperties getCluster() {
        return this.cluster;
    }

    @Generated
    public HazelcastCoreProperties getCore() {
        return this.core;
    }

    @Generated
    public BaseHazelcastProperties setCluster(HazelcastClusterProperties cluster) {
        this.cluster = cluster;
        return this;
    }

    @Generated
    public BaseHazelcastProperties setCore(HazelcastCoreProperties core) {
        this.core = core;
        return this;
    }
}

