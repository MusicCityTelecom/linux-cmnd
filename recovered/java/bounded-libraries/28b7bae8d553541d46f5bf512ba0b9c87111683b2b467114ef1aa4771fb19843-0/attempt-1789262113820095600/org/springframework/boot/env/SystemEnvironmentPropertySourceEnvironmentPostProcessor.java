/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.Ordered
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.PropertySource
 *  org.springframework.core.env.SystemEnvironmentPropertySource
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.env;

import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginLookup;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.util.StringUtils;

public class SystemEnvironmentPropertySourceEnvironmentPostProcessor
implements EnvironmentPostProcessor,
Ordered {
    public static final int DEFAULT_ORDER = -2147483644;
    private int order = -2147483644;

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String sourceName = "systemEnvironment";
        PropertySource propertySource = environment.getPropertySources().get(sourceName);
        if (propertySource != null) {
            this.replacePropertySource(environment, sourceName, propertySource, application.getEnvironmentPrefix());
        }
    }

    private void replacePropertySource(ConfigurableEnvironment environment, String sourceName, PropertySource<?> propertySource, String environmentPrefix) {
        Map originalSource = (Map)propertySource.getSource();
        OriginAwareSystemEnvironmentPropertySource source = new OriginAwareSystemEnvironmentPropertySource(sourceName, originalSource, environmentPrefix);
        environment.getPropertySources().replace(sourceName, (PropertySource)source);
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    protected static class OriginAwareSystemEnvironmentPropertySource
    extends SystemEnvironmentPropertySource
    implements OriginLookup<String> {
        private final String prefix;

        OriginAwareSystemEnvironmentPropertySource(String name, Map<String, Object> source, String environmentPrefix) {
            super(name, source);
            this.prefix = this.determinePrefix(environmentPrefix);
        }

        private String determinePrefix(String environmentPrefix) {
            if (!StringUtils.hasText((String)environmentPrefix)) {
                return null;
            }
            if (environmentPrefix.endsWith(".") || environmentPrefix.endsWith("_") || environmentPrefix.endsWith("-")) {
                return environmentPrefix.substring(0, environmentPrefix.length() - 1);
            }
            return environmentPrefix;
        }

        public boolean containsProperty(String name) {
            return super.containsProperty(name);
        }

        public Object getProperty(String name) {
            return super.getProperty(name);
        }

        @Override
        public Origin getOrigin(String key) {
            String property = this.resolvePropertyName(key);
            if (super.containsProperty(property)) {
                return new SystemEnvironmentOrigin(property);
            }
            return null;
        }

        @Override
        public String getPrefix() {
            return this.prefix;
        }
    }
}

