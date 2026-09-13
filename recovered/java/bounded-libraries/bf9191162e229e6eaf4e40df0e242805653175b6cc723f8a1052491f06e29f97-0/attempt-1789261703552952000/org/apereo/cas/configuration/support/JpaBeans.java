/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.zaxxer.hikari.HikariDataSource
 *  lombok.Generated
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties
 *  org.apereo.cas.configuration.model.support.jpa.JpaConfigurationContext
 *  org.apereo.cas.configuration.support.Beans
 *  org.apereo.cas.util.LoggingUtils
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.apereo.cas.util.spring.SpringExpressionLanguageValueResolver
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.jdbc.datasource.SimpleDriverDataSource
 *  org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException
 *  org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup
 *  org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
 */
package org.apereo.cas.configuration.support;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.util.Map;
import java.util.Properties;
import javax.sql.DataSource;
import lombok.Generated;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties;
import org.apereo.cas.configuration.model.support.jpa.JpaConfigurationContext;
import org.apereo.cas.configuration.support.Beans;
import org.apereo.cas.configuration.support.CloseableDataSource;
import org.apereo.cas.configuration.support.DefaultCloseableDataSource;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.spring.SpringExpressionLanguageValueResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

public final class JpaBeans {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(JpaBeans.class);

    public static DataSource newDataSource(String driverClass, String username, String password, String url) {
        return (DataSource)FunctionUtils.doUnchecked(() -> {
            SimpleDriverDataSource ds = new SimpleDriverDataSource();
            ds.setDriverClass(Class.forName(driverClass));
            ds.setUsername(username);
            ds.setPassword(password);
            ds.setUrl(url);
            return ds;
        });
    }

    public static CloseableDataSource newDataSource(AbstractJpaProperties jpaProperties) {
        String dataSourceName = jpaProperties.getDataSourceName();
        if (StringUtils.isNotBlank((CharSequence)dataSourceName)) {
            try {
                JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
                dsLookup.setResourceRef(false);
                DataSource containerDataSource = dsLookup.getDataSource(dataSourceName);
                return new DefaultCloseableDataSource(containerDataSource);
            }
            catch (DataSourceLookupFailureException e) {
                LOGGER.warn("Lookup of datasource [{}] failed due to [{}]. Back to JPA properties.", (Object)dataSourceName, (Object)e.getMessage());
            }
        }
        HikariDataSource bean = new HikariDataSource();
        if (StringUtils.isNotBlank((CharSequence)jpaProperties.getDriverClass())) {
            bean.setDriverClassName(jpaProperties.getDriverClass());
        }
        String url = SpringExpressionLanguageValueResolver.getInstance().resolve(jpaProperties.getUrl());
        bean.setJdbcUrl(url);
        bean.setUsername(jpaProperties.getUser());
        bean.setPassword(jpaProperties.getPassword());
        FunctionUtils.doUnchecked(u -> bean.setLoginTimeout((int)Beans.newDuration((String)jpaProperties.getPool().getMaxWait()).getSeconds()), (Object[])new Object[0]);
        bean.setMaximumPoolSize(jpaProperties.getPool().getMaxSize());
        bean.setMinimumIdle(jpaProperties.getPool().getMinSize());
        bean.setIdleTimeout(Beans.newDuration((String)jpaProperties.getIdleTimeout()).toMillis());
        bean.setLeakDetectionThreshold(jpaProperties.getLeakThreshold());
        bean.setInitializationFailTimeout(jpaProperties.getFailFastTimeout());
        bean.setIsolateInternalQueries(jpaProperties.isIsolateInternalQueries());
        bean.setConnectionTestQuery(jpaProperties.getHealthQuery());
        bean.setAllowPoolSuspension(jpaProperties.getPool().isSuspension());
        bean.setAutoCommit(jpaProperties.isAutocommit());
        bean.setValidationTimeout(jpaProperties.getPool().getTimeoutMillis());
        bean.setReadOnly(jpaProperties.isReadOnly());
        bean.setPoolName(jpaProperties.getPool().getName());
        bean.setKeepaliveTime(Beans.newDuration((String)jpaProperties.getPool().getKeepAliveTime()).toMillis());
        bean.setMaxLifetime(Beans.newDuration((String)jpaProperties.getPool().getMaximumLifetime()).toMillis());
        bean.setSchema(jpaProperties.getDefaultSchema());
        Properties dataSourceProperties = new Properties();
        dataSourceProperties.putAll((Map<?, ?>)jpaProperties.getProperties());
        bean.setDataSourceProperties(dataSourceProperties);
        return new DefaultCloseableDataSource((DataSource)bean);
    }

    public static LocalContainerEntityManagerFactoryBean newEntityManagerFactoryBean(JpaConfigurationContext config) {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setJpaVendorAdapter(config.getJpaVendorAdapter());
        if (config.getPersistenceProvider() != null) {
            bean.setPersistenceProvider(config.getPersistenceProvider());
        }
        if (StringUtils.isNotBlank((CharSequence)config.getPersistenceUnitName())) {
            bean.setPersistenceUnitName(config.getPersistenceUnitName());
        }
        if (!config.getPackagesToScan().isEmpty()) {
            bean.setPackagesToScan(config.getPackagesToScan().toArray(ArrayUtils.EMPTY_STRING_ARRAY));
        }
        if (config.getDataSource() != null) {
            bean.setDataSource(config.getDataSource());
        }
        bean.getJpaPropertyMap().putAll(config.getJpaProperties());
        return bean;
    }

    public static boolean isValidDataSourceConnection(CloseableDataSource ds, int timeout) {
        boolean bl;
        block8: {
            Connection con = ds.getConnection();
            try {
                bl = con.isValid(timeout);
                if (con == null) break block8;
            }
            catch (Throwable throwable) {
                try {
                    if (con != null) {
                        try {
                            con.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (Exception e) {
                    LoggingUtils.error((Logger)LOGGER, (Throwable)e);
                    return false;
                }
            }
            con.close();
        }
        return bl;
    }

    @Generated
    private JpaBeans() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

