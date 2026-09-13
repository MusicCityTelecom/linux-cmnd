/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.env.ConfigurableEnvironment
 *  org.springframework.core.env.EnumerablePropertySource
 *  org.springframework.core.env.Environment
 *  org.springframework.core.env.PropertySource
 */
package org.springframework.boot.cloud;

import java.util.Arrays;
import java.util.List;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

public enum CloudPlatform {
    NONE{

        @Override
        public boolean isDetected(Environment environment) {
            return false;
        }
    }
    ,
    CLOUD_FOUNDRY{

        @Override
        public boolean isDetected(Environment environment) {
            return environment.containsProperty("VCAP_APPLICATION") || environment.containsProperty("VCAP_SERVICES");
        }
    }
    ,
    HEROKU{

        @Override
        public boolean isDetected(Environment environment) {
            return environment.containsProperty("DYNO");
        }
    }
    ,
    SAP{

        @Override
        public boolean isDetected(Environment environment) {
            return environment.containsProperty("HC_LANDSCAPE");
        }
    }
    ,
    KUBERNETES{
        private static final String KUBERNETES_SERVICE_HOST = "KUBERNETES_SERVICE_HOST";
        private static final String KUBERNETES_SERVICE_PORT = "KUBERNETES_SERVICE_PORT";
        private static final String SERVICE_HOST_SUFFIX = "_SERVICE_HOST";
        private static final String SERVICE_PORT_SUFFIX = "_SERVICE_PORT";

        @Override
        public boolean isDetected(Environment environment) {
            if (environment instanceof ConfigurableEnvironment) {
                return this.isAutoDetected((ConfigurableEnvironment)environment);
            }
            return false;
        }

        private boolean isAutoDetected(ConfigurableEnvironment environment) {
            PropertySource environmentPropertySource = environment.getPropertySources().get("systemEnvironment");
            if (environmentPropertySource != null) {
                if (environmentPropertySource.containsProperty(KUBERNETES_SERVICE_HOST) && environmentPropertySource.containsProperty(KUBERNETES_SERVICE_PORT)) {
                    return true;
                }
                if (environmentPropertySource instanceof EnumerablePropertySource) {
                    return this.isAutoDetected((EnumerablePropertySource)environmentPropertySource);
                }
            }
            return false;
        }

        private boolean isAutoDetected(EnumerablePropertySource<?> environmentPropertySource) {
            for (String propertyName : environmentPropertySource.getPropertyNames()) {
                String serviceName;
                if (!propertyName.endsWith(SERVICE_HOST_SUFFIX) || environmentPropertySource.getProperty((serviceName = propertyName.substring(0, propertyName.length() - SERVICE_HOST_SUFFIX.length())) + SERVICE_PORT_SUFFIX) == null) continue;
                return true;
            }
            return false;
        }
    }
    ,
    AZURE_APP_SERVICE{
        private final List<String> azureEnvVariables = Arrays.asList("WEBSITE_SITE_NAME", "WEBSITE_INSTANCE_ID", "WEBSITE_RESOURCE_GROUP", "WEBSITE_SKU");

        @Override
        public boolean isDetected(Environment environment) {
            return this.azureEnvVariables.stream().allMatch(arg_0 -> ((Environment)environment).containsProperty(arg_0));
        }
    };

    private static final String PROPERTY_NAME = "spring.main.cloud-platform";

    public boolean isActive(Environment environment) {
        String platformProperty = environment.getProperty(PROPERTY_NAME);
        return this.isEnforced(platformProperty) || platformProperty == null && this.isDetected(environment);
    }

    public boolean isEnforced(Environment environment) {
        return this.isEnforced(environment.getProperty(PROPERTY_NAME));
    }

    public boolean isEnforced(Binder binder) {
        return this.isEnforced((String)binder.bind(PROPERTY_NAME, String.class).orElse(null));
    }

    private boolean isEnforced(String platform) {
        return this.name().equalsIgnoreCase(platform);
    }

    public abstract boolean isDetected(Environment var1);

    public boolean isUsingForwardHeaders() {
        return true;
    }

    public static CloudPlatform getActive(Environment environment) {
        if (environment != null) {
            for (CloudPlatform cloudPlatform : CloudPlatform.values()) {
                if (!cloudPlatform.isActive(environment)) continue;
                return cloudPlatform;
            }
        }
        return null;
    }
}

