/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.actuate.context.properties;

import org.springframework.boot.actuate.context.properties.ConfigurationPropertiesReportEndpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.endpoint.web.annotation.EndpointWebExtension;

@EndpointWebExtension(endpoint=ConfigurationPropertiesReportEndpoint.class)
public class ConfigurationPropertiesReportEndpointWebExtension {
    private final ConfigurationPropertiesReportEndpoint delegate;

    public ConfigurationPropertiesReportEndpointWebExtension(ConfigurationPropertiesReportEndpoint delegate) {
        this.delegate = delegate;
    }

    @ReadOperation
    public WebEndpointResponse<ConfigurationPropertiesReportEndpoint.ApplicationConfigurationProperties> configurationPropertiesWithPrefix(@Selector String prefix) {
        ConfigurationPropertiesReportEndpoint.ApplicationConfigurationProperties configurationProperties = this.delegate.configurationPropertiesWithPrefix(prefix);
        boolean foundMatchingBeans = configurationProperties.getContexts().values().stream().anyMatch(context -> !context.getBeans().isEmpty());
        return foundMatchingBeans ? new WebEndpointResponse<ConfigurationPropertiesReportEndpoint.ApplicationConfigurationProperties>(configurationProperties, 200) : new WebEndpointResponse<int>(404);
    }
}

