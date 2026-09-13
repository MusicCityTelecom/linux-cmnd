/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.SpringApplication
 *  org.springframework.boot.env.EnvironmentPostProcessor
 *  org.springframework.boot.env.OriginTrackedMapPropertySource
 *  org.springframework.boot.env.PropertiesPropertySourceLoader
 *  org.springframework.boot.origin.Origin
 *  org.springframework.boot.origin.OriginLookup
 *  org.springframework.core.Ordered
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.PropertySource
 *  org.springframework.core.io.ClassPathResource
 *  org.springframework.core.io.Resource
 */
package org.springframework.boot.autoconfigure.integration;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginLookup;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

class IntegrationPropertiesEnvironmentPostProcessor
implements EnvironmentPostProcessor,
Ordered {
    IntegrationPropertiesEnvironmentPostProcessor() {
    }

    public int getOrder() {
        return Integer.MAX_VALUE;
    }

    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        ClassPathResource resource = new ClassPathResource("META-INF/spring.integration.properties");
        if (resource.exists()) {
            this.registerIntegrationPropertiesPropertySource(environment, (Resource)resource);
        }
    }

    protected void registerIntegrationPropertiesPropertySource(ConfigurableEnvironment environment, Resource resource) {
        PropertiesPropertySourceLoader loader = new PropertiesPropertySourceLoader();
        try {
            OriginTrackedMapPropertySource propertyFileSource = (OriginTrackedMapPropertySource)loader.load("META-INF/spring.integration.properties", resource).get(0);
            environment.getPropertySources().addLast((PropertySource)new IntegrationPropertiesPropertySource(propertyFileSource));
        }
        catch (IOException ex) {
            throw new IllegalStateException("Failed to load integration properties from " + resource, ex);
        }
    }

    private static final class IntegrationPropertiesPropertySource
    extends PropertySource<Map<String, Object>>
    implements OriginLookup<String> {
        private static final String PREFIX = "spring.integration.";
        private static final Map<String, String> KEYS_MAPPING;
        private final OriginTrackedMapPropertySource delegate;

        IntegrationPropertiesPropertySource(OriginTrackedMapPropertySource delegate) {
            super("META-INF/spring.integration.properties", delegate.getSource());
            this.delegate = delegate;
        }

        public Object getProperty(String name) {
            return this.delegate.getProperty(KEYS_MAPPING.get(name));
        }

        public Origin getOrigin(String key) {
            return this.delegate.getOrigin(KEYS_MAPPING.get(key));
        }

        static {
            HashMap<String, String> mappings = new HashMap<String, String>();
            mappings.put("spring.integration.channel.auto-create", "spring.integration.channels.autoCreate");
            mappings.put("spring.integration.channel.max-unicast-subscribers", "spring.integration.channels.maxUnicastSubscribers");
            mappings.put("spring.integration.channel.max-broadcast-subscribers", "spring.integration.channels.maxBroadcastSubscribers");
            mappings.put("spring.integration.error.require-subscribers", "spring.integration.channels.error.requireSubscribers");
            mappings.put("spring.integration.error.ignore-failures", "spring.integration.channels.error.ignoreFailures");
            mappings.put("spring.integration.endpoint.throw-exception-on-late-reply", "spring.integration.messagingTemplate.throwExceptionOnLateReply");
            mappings.put("spring.integration.endpoint.read-only-headers", "spring.integration.readOnly.headers");
            mappings.put("spring.integration.endpoint.no-auto-startup", "spring.integration.endpoints.noAutoStartup");
            KEYS_MAPPING = Collections.unmodifiableMap(mappings);
        }
    }
}

