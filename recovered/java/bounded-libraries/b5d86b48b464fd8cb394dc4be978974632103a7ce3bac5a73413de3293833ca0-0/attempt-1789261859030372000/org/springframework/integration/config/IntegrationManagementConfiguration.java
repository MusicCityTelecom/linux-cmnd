/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.context.EnvironmentAware
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.ImportAware
 *  org.springframework.context.annotation.Role
 *  org.springframework.core.annotation.AnnotationAttributes
 *  org.springframework.core.env.Environment
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.util.Assert
 */
package org.springframework.integration.config;

import java.util.Map;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.annotation.Role;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.integration.config.EnableIntegrationManagement;
import org.springframework.integration.config.IntegrationManagementConfigurer;
import org.springframework.integration.support.management.metrics.MetricsCaptor;
import org.springframework.util.Assert;

@Configuration(proxyBeanMethods=false)
public class IntegrationManagementConfiguration
implements ImportAware,
EnvironmentAware {
    private AnnotationAttributes attributes;
    private Environment environment;

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void setImportMetadata(AnnotationMetadata importMetadata) {
        Map map = importMetadata.getAnnotationAttributes(EnableIntegrationManagement.class.getName());
        this.attributes = AnnotationAttributes.fromMap((Map)map);
        Assert.notNull((Object)this.attributes, () -> "@EnableIntegrationManagement is not present on importing class " + importMetadata.getClassName());
    }

    @Bean(name={"integrationManagementConfigurer"})
    @Role(value=2)
    public IntegrationManagementConfigurer managementConfigurer(ObjectProvider<MetricsCaptor> metricsCaptorProvider) {
        IntegrationManagementConfigurer configurer = new IntegrationManagementConfigurer();
        configurer.setDefaultLoggingEnabled(Boolean.parseBoolean(this.environment.resolvePlaceholders((String)this.attributes.get((Object)"defaultLoggingEnabled"))));
        configurer.setMetricsCaptorProvider(metricsCaptorProvider);
        return configurer;
    }
}

