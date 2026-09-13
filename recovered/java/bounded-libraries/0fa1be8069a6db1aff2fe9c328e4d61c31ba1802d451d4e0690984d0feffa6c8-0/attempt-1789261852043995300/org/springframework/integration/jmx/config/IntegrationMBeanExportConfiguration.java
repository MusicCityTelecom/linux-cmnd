/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.config.BeanExpressionContext
 *  org.springframework.beans.factory.config.BeanExpressionResolver
 *  org.springframework.beans.factory.config.ConfigurableBeanFactory
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.context.EnvironmentAware
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.ImportAware
 *  org.springframework.context.annotation.MBeanExportConfiguration$SpecificPlatform
 *  org.springframework.context.annotation.Role
 *  org.springframework.context.expression.StandardBeanExpressionResolver
 *  org.springframework.core.annotation.AnnotationAttributes
 *  org.springframework.core.env.Environment
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.jmx.support.RegistrationPolicy
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 */
package org.springframework.integration.jmx.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import javax.management.MBeanServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.BeanExpressionResolver;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.annotation.MBeanExportConfiguration;
import org.springframework.context.annotation.Role;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.integration.jmx.config.EnableIntegrationMBeanExport;
import org.springframework.integration.monitor.IntegrationMBeanExporter;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Configuration(proxyBeanMethods=false)
public class IntegrationMBeanExportConfiguration
implements ImportAware,
EnvironmentAware,
BeanFactoryAware {
    public static final String MBEAN_EXPORTER_NAME = "integrationMbeanExporter";
    private AnnotationAttributes attributes;
    private BeanFactory beanFactory;
    private BeanExpressionResolver resolver = new StandardBeanExpressionResolver();
    private BeanExpressionContext expressionContext;
    private Environment environment;

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        if (beanFactory instanceof ConfigurableListableBeanFactory) {
            this.resolver = ((ConfigurableListableBeanFactory)beanFactory).getBeanExpressionResolver();
            this.expressionContext = new BeanExpressionContext((ConfigurableBeanFactory)((ConfigurableListableBeanFactory)beanFactory), null);
        }
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void setImportMetadata(AnnotationMetadata importMetadata) {
        Map map = importMetadata.getAnnotationAttributes(EnableIntegrationMBeanExport.class.getName());
        this.attributes = AnnotationAttributes.fromMap((Map)map);
        Assert.notNull((Object)this.attributes, () -> "@EnableIntegrationMBeanExport is not present on importing class " + importMetadata.getClassName());
    }

    @Bean(name={"integrationMbeanExporter"})
    @Role(value=2)
    public IntegrationMBeanExporter mbeanExporter() {
        IntegrationMBeanExporter exporter = new IntegrationMBeanExporter();
        exporter.setRegistrationPolicy((RegistrationPolicy)this.attributes.getEnum("registration"));
        this.setupDomain(exporter);
        this.setupServer(exporter);
        this.setupComponentNamePatterns(exporter);
        return exporter;
    }

    private void setupDomain(IntegrationMBeanExporter exporter) {
        String defaultDomain = this.attributes.getString("defaultDomain");
        if (this.environment != null) {
            defaultDomain = this.environment.resolvePlaceholders(defaultDomain);
        }
        if (StringUtils.hasText((String)defaultDomain)) {
            exporter.setDefaultDomain(defaultDomain);
        }
    }

    private void setupServer(IntegrationMBeanExporter exporter) {
        String server = this.attributes.getString("server");
        if (this.environment != null) {
            server = this.environment.resolvePlaceholders(server);
        }
        if (StringUtils.hasText((String)server)) {
            MBeanServer bean = server.startsWith("#{") && server.endsWith("}") ? (MBeanServer)this.resolver.evaluate(server, this.expressionContext) : (MBeanServer)this.beanFactory.getBean(server, MBeanServer.class);
            exporter.setServer(bean);
        } else {
            MBeanExportConfiguration.SpecificPlatform specificPlatform = MBeanExportConfiguration.SpecificPlatform.get();
            if (specificPlatform != null) {
                exporter.setServer(specificPlatform.getMBeanServer());
            }
        }
    }

    private void setupComponentNamePatterns(IntegrationMBeanExporter exporter) {
        String[] managedComponents;
        ArrayList<String> patterns = new ArrayList<String>();
        for (String managedComponent : managedComponents = this.attributes.getStringArray("managedComponents")) {
            String pattern = this.environment.resolvePlaceholders(managedComponent);
            patterns.addAll(Arrays.asList(StringUtils.commaDelimitedListToStringArray((String)pattern)));
        }
        exporter.setComponentNamePatterns(patterns.toArray(new String[0]));
    }
}

