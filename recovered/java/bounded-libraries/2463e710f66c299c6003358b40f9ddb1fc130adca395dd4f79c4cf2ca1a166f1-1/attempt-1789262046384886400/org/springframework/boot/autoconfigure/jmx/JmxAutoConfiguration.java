/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.MBeanExportConfiguration$SpecificPlatform
 *  org.springframework.context.annotation.Primary
 *  org.springframework.jmx.export.MBeanExporter
 *  org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource
 *  org.springframework.jmx.export.annotation.AnnotationMBeanExporter
 *  org.springframework.jmx.export.metadata.JmxAttributeSource
 *  org.springframework.jmx.export.naming.ObjectNamingStrategy
 *  org.springframework.jmx.support.MBeanServerFactoryBean
 *  org.springframework.jmx.support.RegistrationPolicy
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.jmx;

import javax.management.MBeanServer;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.jmx.JmxProperties;
import org.springframework.boot.autoconfigure.jmx.ParentAwareNamingStrategy;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.MBeanExportConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource;
import org.springframework.jmx.export.annotation.AnnotationMBeanExporter;
import org.springframework.jmx.export.metadata.JmxAttributeSource;
import org.springframework.jmx.export.naming.ObjectNamingStrategy;
import org.springframework.jmx.support.MBeanServerFactoryBean;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.util.StringUtils;

@AutoConfiguration
@EnableConfigurationProperties(value={JmxProperties.class})
@ConditionalOnClass(value={MBeanExporter.class})
@ConditionalOnProperty(prefix="spring.jmx", name={"enabled"}, havingValue="true")
public class JmxAutoConfiguration {
    private final JmxProperties properties;

    public JmxAutoConfiguration(JmxProperties properties) {
        this.properties = properties;
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(value={MBeanExporter.class}, search=SearchStrategy.CURRENT)
    public AnnotationMBeanExporter mbeanExporter(ObjectNamingStrategy namingStrategy, BeanFactory beanFactory) {
        AnnotationMBeanExporter exporter = new AnnotationMBeanExporter();
        exporter.setRegistrationPolicy(RegistrationPolicy.FAIL_ON_EXISTING);
        exporter.setNamingStrategy(namingStrategy);
        String serverBean = this.properties.getServer();
        if (StringUtils.hasLength((String)serverBean)) {
            exporter.setServer((MBeanServer)beanFactory.getBean(serverBean, MBeanServer.class));
        }
        exporter.setEnsureUniqueRuntimeObjectNames(this.properties.isUniqueNames());
        return exporter;
    }

    @Bean
    @ConditionalOnMissingBean(value={ObjectNamingStrategy.class}, search=SearchStrategy.CURRENT)
    public ParentAwareNamingStrategy objectNamingStrategy() {
        ParentAwareNamingStrategy namingStrategy = new ParentAwareNamingStrategy((JmxAttributeSource)new AnnotationJmxAttributeSource());
        String defaultDomain = this.properties.getDefaultDomain();
        if (StringUtils.hasLength((String)defaultDomain)) {
            namingStrategy.setDefaultDomain(defaultDomain);
        }
        namingStrategy.setEnsureUniqueRuntimeObjectNames(this.properties.isUniqueNames());
        return namingStrategy;
    }

    @Bean
    @ConditionalOnMissingBean
    public MBeanServer mbeanServer() {
        MBeanExportConfiguration.SpecificPlatform platform = MBeanExportConfiguration.SpecificPlatform.get();
        if (platform != null) {
            return platform.getMBeanServer();
        }
        MBeanServerFactoryBean factory = new MBeanServerFactoryBean();
        factory.setLocateExistingServerIfPossible(true);
        factory.afterPropertiesSet();
        return factory.getObject();
    }
}

