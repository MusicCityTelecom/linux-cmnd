/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.hibernate.boot.model.naming.ImplicitNamingStrategy
 *  org.hibernate.boot.model.naming.PhysicalNamingStrategy
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.jdbc.SchemaManagementProvider
 *  org.springframework.boot.jdbc.metadata.CompositeDataSourcePoolMetadataProvider
 *  org.springframework.boot.jdbc.metadata.DataSourcePoolMetadata
 *  org.springframework.boot.jdbc.metadata.DataSourcePoolMetadataProvider
 *  org.springframework.boot.orm.jpa.hibernate.SpringJtaPlatform
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.jndi.JndiLocatorDelegate
 *  org.springframework.orm.hibernate5.SpringBeanContainer
 *  org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter
 *  org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
 *  org.springframework.transaction.jta.JtaTransactionManager
 *  org.springframework.util.ClassUtils
 */
package org.springframework.boot.autoconfigure.orm.jpa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateDefaultDdlAutoProvider;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.SchemaManagementProvider;
import org.springframework.boot.jdbc.metadata.CompositeDataSourcePoolMetadataProvider;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadata;
import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadataProvider;
import org.springframework.boot.orm.jpa.hibernate.SpringJtaPlatform;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiLocatorDelegate;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.util.ClassUtils;

@Configuration(proxyBeanMethods=false)
@EnableConfigurationProperties(value={HibernateProperties.class})
@ConditionalOnSingleCandidate(value=DataSource.class)
class HibernateJpaConfiguration
extends JpaBaseConfiguration {
    private static final Log logger = LogFactory.getLog(HibernateJpaConfiguration.class);
    private static final String JTA_PLATFORM = "hibernate.transaction.jta.platform";
    private static final String PROVIDER_DISABLES_AUTOCOMMIT = "hibernate.connection.provider_disables_autocommit";
    private static final String[] NO_JTA_PLATFORM_CLASSES = new String[]{"org.hibernate.engine.transaction.jta.platform.internal.NoJtaPlatform", "org.hibernate.service.jta.platform.internal.NoJtaPlatform"};
    private final HibernateProperties hibernateProperties;
    private final HibernateDefaultDdlAutoProvider defaultDdlAutoProvider;
    private DataSourcePoolMetadataProvider poolMetadataProvider;
    private final List<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers;

    HibernateJpaConfiguration(DataSource dataSource, JpaProperties jpaProperties, ConfigurableListableBeanFactory beanFactory, ObjectProvider<JtaTransactionManager> jtaTransactionManager, HibernateProperties hibernateProperties, ObjectProvider<Collection<DataSourcePoolMetadataProvider>> metadataProviders, ObjectProvider<SchemaManagementProvider> providers, ObjectProvider<PhysicalNamingStrategy> physicalNamingStrategy, ObjectProvider<ImplicitNamingStrategy> implicitNamingStrategy, ObjectProvider<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers) {
        super(dataSource, jpaProperties, jtaTransactionManager);
        this.hibernateProperties = hibernateProperties;
        this.defaultDdlAutoProvider = new HibernateDefaultDdlAutoProvider((Iterable<SchemaManagementProvider>)providers);
        this.poolMetadataProvider = new CompositeDataSourcePoolMetadataProvider((Collection)metadataProviders.getIfAvailable());
        this.hibernatePropertiesCustomizers = this.determineHibernatePropertiesCustomizers((PhysicalNamingStrategy)physicalNamingStrategy.getIfAvailable(), (ImplicitNamingStrategy)implicitNamingStrategy.getIfAvailable(), beanFactory, hibernatePropertiesCustomizers.orderedStream().collect(Collectors.toList()));
    }

    private List<HibernatePropertiesCustomizer> determineHibernatePropertiesCustomizers(PhysicalNamingStrategy physicalNamingStrategy, ImplicitNamingStrategy implicitNamingStrategy, ConfigurableListableBeanFactory beanFactory, List<HibernatePropertiesCustomizer> hibernatePropertiesCustomizers) {
        ArrayList<HibernatePropertiesCustomizer> customizers = new ArrayList<HibernatePropertiesCustomizer>();
        if (ClassUtils.isPresent((String)"org.hibernate.resource.beans.container.spi.BeanContainer", (ClassLoader)this.getClass().getClassLoader())) {
            customizers.add(properties -> properties.put("hibernate.resource.beans.container", new SpringBeanContainer(beanFactory)));
        }
        if (physicalNamingStrategy != null || implicitNamingStrategy != null) {
            customizers.add(new NamingStrategiesHibernatePropertiesCustomizer(physicalNamingStrategy, implicitNamingStrategy));
        }
        customizers.addAll(hibernatePropertiesCustomizers);
        return customizers;
    }

    @Override
    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Override
    protected Map<String, Object> getVendorProperties() {
        Supplier<String> defaultDdlMode = () -> this.defaultDdlAutoProvider.getDefaultDdlAuto(this.getDataSource());
        return new LinkedHashMap<String, Object>(this.hibernateProperties.determineHibernateProperties(this.getProperties().getProperties(), new HibernateSettings().ddlAuto(defaultDdlMode).hibernatePropertiesCustomizers(this.hibernatePropertiesCustomizers)));
    }

    @Override
    protected void customizeVendorProperties(Map<String, Object> vendorProperties) {
        super.customizeVendorProperties(vendorProperties);
        if (!vendorProperties.containsKey(JTA_PLATFORM)) {
            this.configureJtaPlatform(vendorProperties);
        }
        if (!vendorProperties.containsKey(PROVIDER_DISABLES_AUTOCOMMIT)) {
            this.configureProviderDisablesAutocommit(vendorProperties);
        }
    }

    private void configureJtaPlatform(Map<String, Object> vendorProperties) throws LinkageError {
        JtaTransactionManager jtaTransactionManager = this.getJtaTransactionManager();
        if (jtaTransactionManager == null) {
            vendorProperties.put(JTA_PLATFORM, this.getNoJtaPlatformManager());
        } else if (!this.runningOnWebSphere()) {
            this.configureSpringJtaPlatform(vendorProperties, jtaTransactionManager);
        }
    }

    private void configureProviderDisablesAutocommit(Map<String, Object> vendorProperties) {
        if (this.isDataSourceAutoCommitDisabled() && !this.isJta()) {
            vendorProperties.put(PROVIDER_DISABLES_AUTOCOMMIT, "true");
        }
    }

    private boolean isDataSourceAutoCommitDisabled() {
        DataSourcePoolMetadata poolMetadata = this.poolMetadataProvider.getDataSourcePoolMetadata(this.getDataSource());
        return poolMetadata != null && Boolean.FALSE.equals(poolMetadata.getDefaultAutoCommit());
    }

    private boolean runningOnWebSphere() {
        return ClassUtils.isPresent((String)"com.ibm.websphere.jtaextensions.ExtendedJTATransaction", (ClassLoader)this.getClass().getClassLoader());
    }

    private void configureSpringJtaPlatform(Map<String, Object> vendorProperties, JtaTransactionManager jtaTransactionManager) {
        block3: {
            try {
                vendorProperties.put(JTA_PLATFORM, new SpringJtaPlatform(jtaTransactionManager));
            }
            catch (LinkageError ex) {
                if (!this.isUsingJndi()) {
                    throw new IllegalStateException("Unable to set Hibernate JTA platform, are you using the correct version of Hibernate?", ex);
                }
                if (!logger.isDebugEnabled()) break block3;
                logger.debug((Object)("Unable to set Hibernate JTA platform : " + ex.getMessage()));
            }
        }
    }

    private boolean isUsingJndi() {
        try {
            return JndiLocatorDelegate.isDefaultJndiEnvironmentAvailable();
        }
        catch (Error ex) {
            return false;
        }
    }

    private Object getNoJtaPlatformManager() {
        for (String candidate : NO_JTA_PLATFORM_CLASSES) {
            try {
                return Class.forName(candidate).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            }
            catch (Exception exception) {
            }
        }
        throw new IllegalStateException("No available JtaPlatform candidates amongst " + Arrays.toString(NO_JTA_PLATFORM_CLASSES));
    }

    private static class NamingStrategiesHibernatePropertiesCustomizer
    implements HibernatePropertiesCustomizer {
        private final PhysicalNamingStrategy physicalNamingStrategy;
        private final ImplicitNamingStrategy implicitNamingStrategy;

        NamingStrategiesHibernatePropertiesCustomizer(PhysicalNamingStrategy physicalNamingStrategy, ImplicitNamingStrategy implicitNamingStrategy) {
            this.physicalNamingStrategy = physicalNamingStrategy;
            this.implicitNamingStrategy = implicitNamingStrategy;
        }

        @Override
        public void customize(Map<String, Object> hibernateProperties) {
            if (this.physicalNamingStrategy != null) {
                hibernateProperties.put("hibernate.physical_naming_strategy", this.physicalNamingStrategy);
            }
            if (this.implicitNamingStrategy != null) {
                hibernateProperties.put("hibernate.implicit_naming_strategy", this.implicitNamingStrategy);
            }
        }
    }
}

