/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.hazelcast.core.HazelcastInstance
 *  org.springframework.util.Assert
 *  org.springframework.util.ReflectionUtils
 */
package org.springframework.boot.actuate.hazelcast;

import com.hazelcast.core.HazelcastInstance;
import java.lang.reflect.Method;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

public class HazelcastHealthIndicator
extends AbstractHealthIndicator {
    private final HazelcastInstance hazelcast;

    public HazelcastHealthIndicator(HazelcastInstance hazelcast) {
        super("Hazelcast health check failed");
        Assert.notNull((Object)hazelcast, (String)"HazelcastInstance must not be null");
        this.hazelcast = hazelcast;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        this.hazelcast.executeTransaction(context -> {
            builder.up().withDetail("name", this.hazelcast.getName()).withDetail("uuid", this.extractUuid());
            return null;
        });
    }

    private String extractUuid() {
        try {
            return this.hazelcast.getLocalEndpoint().getUuid().toString();
        }
        catch (NoSuchMethodError ex) {
            Method endpointAccessor = ReflectionUtils.findMethod(HazelcastInstance.class, (String)"getLocalEndpoint");
            Object endpoint = ReflectionUtils.invokeMethod((Method)endpointAccessor, (Object)this.hazelcast);
            Method uuidAccessor = ReflectionUtils.findMethod(endpoint.getClass(), (String)"getUuid");
            return (String)ReflectionUtils.invokeMethod((Method)uuidAccessor, (Object)endpoint);
        }
    }
}

