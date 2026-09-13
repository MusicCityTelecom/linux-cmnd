/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.WebApplicationType
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.boot.autoconfigure.session;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.session.HazelcastSessionConfiguration;
import org.springframework.boot.autoconfigure.session.JdbcSessionConfiguration;
import org.springframework.boot.autoconfigure.session.MongoReactiveSessionConfiguration;
import org.springframework.boot.autoconfigure.session.MongoSessionConfiguration;
import org.springframework.boot.autoconfigure.session.NoOpReactiveSessionConfiguration;
import org.springframework.boot.autoconfigure.session.NoOpSessionConfiguration;
import org.springframework.boot.autoconfigure.session.RedisReactiveSessionConfiguration;
import org.springframework.boot.autoconfigure.session.RedisSessionConfiguration;
import org.springframework.boot.autoconfigure.session.StoreType;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

final class SessionStoreMappings {
    private static final Map<StoreType, Configurations> MAPPINGS;

    private SessionStoreMappings() {
    }

    static String getConfigurationClass(WebApplicationType webApplicationType, StoreType sessionStoreType) {
        Configurations configurations = MAPPINGS.get((Object)sessionStoreType);
        Assert.state((configurations != null ? 1 : 0) != 0, () -> "Unknown session store type " + (Object)((Object)sessionStoreType));
        return configurations.getConfiguration(webApplicationType);
    }

    static StoreType getType(WebApplicationType webApplicationType, String configurationClass) {
        return MAPPINGS.entrySet().stream().filter(entry -> ObjectUtils.nullSafeEquals((Object)configurationClass, (Object)((Configurations)entry.getValue()).getConfiguration(webApplicationType))).map(Map.Entry::getKey).findFirst().orElseThrow(() -> new IllegalStateException("Unknown configuration class " + configurationClass));
    }

    static {
        EnumMap<StoreType, Configurations> mappings = new EnumMap<StoreType, Configurations>(StoreType.class);
        mappings.put(StoreType.REDIS, new Configurations(RedisSessionConfiguration.class, RedisReactiveSessionConfiguration.class));
        mappings.put(StoreType.MONGODB, new Configurations(MongoSessionConfiguration.class, MongoReactiveSessionConfiguration.class));
        mappings.put(StoreType.JDBC, new Configurations(JdbcSessionConfiguration.class, null));
        mappings.put(StoreType.HAZELCAST, new Configurations(HazelcastSessionConfiguration.class, null));
        mappings.put(StoreType.NONE, new Configurations(NoOpSessionConfiguration.class, NoOpReactiveSessionConfiguration.class));
        MAPPINGS = Collections.unmodifiableMap(mappings);
    }

    private static class Configurations {
        private final Class<?> servletConfiguration;
        private final Class<?> reactiveConfiguration;

        Configurations(Class<?> servletConfiguration, Class<?> reactiveConfiguration) {
            this.servletConfiguration = servletConfiguration;
            this.reactiveConfiguration = reactiveConfiguration;
        }

        String getConfiguration(WebApplicationType webApplicationType) {
            switch (webApplicationType) {
                case SERVLET: {
                    return this.getName(this.servletConfiguration);
                }
                case REACTIVE: {
                    return this.getName(this.reactiveConfiguration);
                }
            }
            return null;
        }

        String getName(Class<?> configuration) {
            return configuration != null ? configuration.getName() : null;
        }
    }
}

