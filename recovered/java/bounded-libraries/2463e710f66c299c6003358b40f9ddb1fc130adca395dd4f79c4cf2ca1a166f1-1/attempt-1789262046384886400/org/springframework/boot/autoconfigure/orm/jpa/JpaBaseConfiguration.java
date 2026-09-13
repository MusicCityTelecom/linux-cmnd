/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.persistence.EntityManagerFactory
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.beans.factory.config.ConfigurableListableBeanFactory
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Primary
 *  org.springframework.orm.jpa.JpaTransactionManager
 *  org.springframework.orm.jpa.JpaVendorAdapter
 *  org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
 *  org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager
 *  org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter
 *  org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor
 *  org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter
 *  org.springframework.transaction.PlatformTransactionManager
 *  org.springframework.transaction.TransactionManager
 *  org.springframework.transaction.jta.JtaTransactionManager
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.StringUtils
 *  org.springframework.web.context.request.WebRequestInterceptor
 *  org.springframework.web.servlet.config.annotation.InterceptorRegistry
 *  org.springframework.web.servlet.config.annotation.WebMvcConfigurer
 */
package org.springframework.boot.autoconfigure.orm.jpa;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.domain.EntityScanPackages;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilderCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration(proxyBeanMethods=false)
@EnableConfigurationProperties(value={JpaProperties.class})
public abstract class JpaBaseConfiguration
implements BeanFactoryAware {
    private final DataSource dataSource;
    private final JpaProperties properties;
    private final JtaTransactionManager jtaTransactionManager;
    private ConfigurableListableBeanFactory beanFactory;

    protected JpaBaseConfiguration(DataSource dataSource, JpaProperties properties, ObjectProvider<JtaTransactionManager> jtaTransactionManager) {
        this.dataSource = dataSource;
        this.properties = properties;
        this.jtaTransactionManager = (JtaTransactionManager)jtaTransactionManager.getIfAvailable();
    }

    @Bean
    @ConditionalOnMissingBean(value={TransactionManager.class})
    public PlatformTransactionManager transactionManager(ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManagerCustomizers.ifAvailable(customizers -> customizers.customize((PlatformTransactionManager)transactionManager));
        return transactionManager;
    }

    @Bean
    @ConditionalOnMissingBean
    public JpaVendorAdapter jpaVendorAdapter() {
        AbstractJpaVendorAdapter adapter = this.createJpaVendorAdapter();
        adapter.setShowSql(this.properties.isShowSql());
        if (this.properties.getDatabase() != null) {
            adapter.setDatabase(this.properties.getDatabase());
        }
        if (this.properties.getDatabasePlatform() != null) {
            adapter.setDatabasePlatform(this.properties.getDatabasePlatform());
        }
        adapter.setGenerateDdl(this.properties.isGenerateDdl());
        return adapter;
    }

    @Bean
    @ConditionalOnMissingBean
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder(JpaVendorAdapter jpaVendorAdapter, ObjectProvider<PersistenceUnitManager> persistenceUnitManager, ObjectProvider<EntityManagerFactoryBuilderCustomizer> customizers) {
        EntityManagerFactoryBuilder builder = new EntityManagerFactoryBuilder(jpaVendorAdapter, this.properties.getProperties(), (PersistenceUnitManager)persistenceUnitManager.getIfAvailable());
        customizers.orderedStream().forEach(customizer -> customizer.customize(builder));
        return builder;
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(value={LocalContainerEntityManagerFactoryBean.class, EntityManagerFactory.class})
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder factoryBuilder) {
        Map<String, Object> vendorProperties = this.getVendorProperties();
        this.customizeVendorProperties(vendorProperties);
        return factoryBuilder.dataSource(this.dataSource).packages(this.getPackagesToScan()).properties(vendorProperties).mappingResources(this.getMappingResources()).jta(this.isJta()).build();
    }

    protected abstract AbstractJpaVendorAdapter createJpaVendorAdapter();

    protected abstract Map<String, Object> getVendorProperties();

    protected void customizeVendorProperties(Map<String, Object> vendorProperties) {
    }

    protected String[] getPackagesToScan() {
        List<String> packages = EntityScanPackages.get((BeanFactory)this.beanFactory).getPackageNames();
        if (packages.isEmpty() && AutoConfigurationPackages.has((BeanFactory)this.beanFactory)) {
            packages = AutoConfigurationPackages.get((BeanFactory)this.beanFactory);
        }
        return StringUtils.toStringArray(packages);
    }

    private String[] getMappingResources() {
        List<String> mappingResources = this.properties.getMappingResources();
        return !ObjectUtils.isEmpty(mappingResources) ? StringUtils.toStringArray(mappingResources) : null;
    }

    protected JtaTransactionManager getJtaTransactionManager() {
        return this.jtaTransactionManager;
    }

    protected final boolean isJta() {
        return this.jtaTransactionManager != null;
    }

    protected final JpaProperties getProperties() {
        return this.properties;
    }

    protected final DataSource getDataSource() {
        return this.dataSource;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory)beanFactory;
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnWebApplication(type=ConditionalOnWebApplication.Type.SERVLET)
    @ConditionalOnClass(value={WebMvcConfigurer.class})
    @ConditionalOnMissingBean(value={OpenEntityManagerInViewInterceptor.class, OpenEntityManagerInViewFilter.class})
    @ConditionalOnMissingFilterBean(value={OpenEntityManagerInViewFilter.class})
    @ConditionalOnProperty(prefix="spring.jpa", name={"open-in-view"}, havingValue="true", matchIfMissing=true)
    protected static class JpaWebConfiguration {
        private static final Log logger = LogFactory.getLog(JpaWebConfiguration.class);
        private final JpaProperties jpaProperties;

        protected JpaWebConfiguration(JpaProperties jpaProperties) {
            this.jpaProperties = jpaProperties;
        }

        @Bean
        public OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor() {
            if (this.jpaProperties.getOpenInView() == null) {
                logger.warn((Object)"spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning");
            }
            return new OpenEntityManagerInViewInterceptor();
        }

        @Bean
        public WebMvcConfigurer openEntityManagerInViewInterceptorConfigurer(final OpenEntityManagerInViewInterceptor interceptor) {
            return new WebMvcConfigurer(){

                public void addInterceptors(InterceptorRegistry registry) {
                    registry.addWebRequestInterceptor((WebRequestInterceptor)interceptor);
                }
            };
        }
    }
}

