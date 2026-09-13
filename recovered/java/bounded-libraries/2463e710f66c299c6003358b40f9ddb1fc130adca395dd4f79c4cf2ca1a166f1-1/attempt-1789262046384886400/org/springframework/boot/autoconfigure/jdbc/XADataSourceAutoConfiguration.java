/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.transaction.TransactionManager
 *  org.springframework.beans.BeanUtils
 *  org.springframework.beans.factory.BeanClassLoaderAware
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.context.properties.bind.Bindable
 *  org.springframework.boot.context.properties.bind.Binder
 *  org.springframework.boot.context.properties.source.ConfigurationPropertyName
 *  org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases
 *  org.springframework.boot.context.properties.source.ConfigurationPropertySource
 *  org.springframework.boot.context.properties.source.MapConfigurationPropertySource
 *  org.springframework.boot.jdbc.DatabaseDriver
 *  org.springframework.boot.jdbc.XADataSourceWrapper
 *  org.springframework.context.annotation.Bean
 *  org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.jdbc;

import java.util.HashMap;
import javax.sql.DataSource;
import javax.sql.XADataSource;
import javax.transaction.TransactionManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.boot.jdbc.XADataSourceWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

@AutoConfiguration(before={DataSourceAutoConfiguration.class})
@EnableConfigurationProperties(value={DataSourceProperties.class})
@ConditionalOnClass(value={DataSource.class, TransactionManager.class, EmbeddedDatabaseType.class})
@ConditionalOnBean(value={XADataSourceWrapper.class})
@ConditionalOnMissingBean(value={DataSource.class})
public class XADataSourceAutoConfiguration
implements BeanClassLoaderAware {
    private ClassLoader classLoader;

    @Bean
    public DataSource dataSource(XADataSourceWrapper wrapper, DataSourceProperties properties, ObjectProvider<XADataSource> xaDataSource) throws Exception {
        return wrapper.wrapDataSource((XADataSource)xaDataSource.getIfAvailable(() -> this.createXaDataSource(properties)));
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    private XADataSource createXaDataSource(DataSourceProperties properties) {
        String className = properties.getXa().getDataSourceClassName();
        if (!StringUtils.hasLength((String)className)) {
            className = DatabaseDriver.fromJdbcUrl((String)properties.determineUrl()).getXaDataSourceClassName();
        }
        Assert.state((boolean)StringUtils.hasLength((String)className), (String)"No XA DataSource class name specified");
        XADataSource dataSource = this.createXaDataSourceInstance(className);
        this.bindXaProperties(dataSource, properties);
        return dataSource;
    }

    private XADataSource createXaDataSourceInstance(String className) {
        try {
            Class dataSourceClass = ClassUtils.forName((String)className, (ClassLoader)this.classLoader);
            Object instance = BeanUtils.instantiateClass((Class)dataSourceClass);
            Assert.isInstanceOf(XADataSource.class, (Object)instance);
            return (XADataSource)instance;
        }
        catch (Exception ex) {
            throw new IllegalStateException("Unable to create XADataSource instance from '" + className + "'");
        }
    }

    private void bindXaProperties(XADataSource target, DataSourceProperties dataSourceProperties) {
        Binder binder = new Binder(new ConfigurationPropertySource[]{this.getBinderSource(dataSourceProperties)});
        binder.bind(ConfigurationPropertyName.EMPTY, Bindable.ofInstance((Object)target));
    }

    private ConfigurationPropertySource getBinderSource(DataSourceProperties dataSourceProperties) {
        HashMap<Object, Object> properties = new HashMap<Object, Object>();
        properties.putAll(dataSourceProperties.getXa().getProperties());
        properties.computeIfAbsent("user", key -> dataSourceProperties.determineUsername());
        properties.computeIfAbsent("password", key -> dataSourceProperties.determinePassword());
        try {
            properties.computeIfAbsent("url", key -> dataSourceProperties.determineUrl());
        }
        catch (DataSourceProperties.DataSourceBeanCreationException dataSourceBeanCreationException) {
            // empty catch block
        }
        MapConfigurationPropertySource source = new MapConfigurationPropertySource(properties);
        ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();
        aliases.addAliases("user", new String[]{"username"});
        return source.withAliases(aliases);
    }
}

