/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 *  org.springframework.boot.context.properties.NestedConfigurationProperty
 */
package org.apereo.cas.configuration.model.support.jpa;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.configuration.model.support.ConnectionPoolingProperties;
import org.apereo.cas.configuration.support.DurationCapable;
import org.apereo.cas.configuration.support.ExpressionLanguageCapable;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@RequiresModule(name="cas-server-support-jdbc-drivers")
@JsonFilter(value="AbstractJpaProperties")
public abstract class AbstractJpaProperties
implements Serializable {
    private static final long serialVersionUID = 761486823496930920L;
    private String dialect = "org.hibernate.dialect.HSQLDialect";
    private String ddlAuto = "update";
    @RequiredProperty
    private String driverClass = "org.hsqldb.jdbcDriver";
    @RequiredProperty
    @ExpressionLanguageCapable
    private String url = "jdbc:hsqldb:mem:cas-hsql-database";
    @RequiredProperty
    private String user = "sa";
    @RequiredProperty
    private String password = "";
    private String defaultCatalog;
    private String defaultSchema;
    private String healthQuery = "";
    @DurationCapable
    private String idleTimeout = "PT10M";
    private String dataSourceName;
    private Map<String, String> properties = new HashMap<String, String>(0);
    @NestedConfigurationProperty
    private ConnectionPoolingProperties pool = new ConnectionPoolingProperties();
    private long leakThreshold = 3000L;
    private boolean generateStatistics;
    private int batchSize = 100;
    private int fetchSize = 100;
    private long failFastTimeout = 1L;
    private boolean isolateInternalQueries;
    private boolean autocommit;
    private boolean readOnly;
    private String physicalNamingStrategyClassName = "org.apereo.cas.hibernate.CasHibernatePhysicalNamingStrategy";
    private String isolationLevelName = "ISOLATION_READ_COMMITTED";
    private String propagationBehaviorName = "PROPAGATION_REQUIRED";

    @Generated
    public String getDialect() {
        return this.dialect;
    }

    @Generated
    public String getDdlAuto() {
        return this.ddlAuto;
    }

    @Generated
    public String getDriverClass() {
        return this.driverClass;
    }

    @Generated
    public String getUrl() {
        return this.url;
    }

    @Generated
    public String getUser() {
        return this.user;
    }

    @Generated
    public String getPassword() {
        return this.password;
    }

    @Generated
    public String getDefaultCatalog() {
        return this.defaultCatalog;
    }

    @Generated
    public String getDefaultSchema() {
        return this.defaultSchema;
    }

    @Generated
    public String getHealthQuery() {
        return this.healthQuery;
    }

    @Generated
    public String getIdleTimeout() {
        return this.idleTimeout;
    }

    @Generated
    public String getDataSourceName() {
        return this.dataSourceName;
    }

    @Generated
    public Map<String, String> getProperties() {
        return this.properties;
    }

    @Generated
    public ConnectionPoolingProperties getPool() {
        return this.pool;
    }

    @Generated
    public long getLeakThreshold() {
        return this.leakThreshold;
    }

    @Generated
    public boolean isGenerateStatistics() {
        return this.generateStatistics;
    }

    @Generated
    public int getBatchSize() {
        return this.batchSize;
    }

    @Generated
    public int getFetchSize() {
        return this.fetchSize;
    }

    @Generated
    public long getFailFastTimeout() {
        return this.failFastTimeout;
    }

    @Generated
    public boolean isIsolateInternalQueries() {
        return this.isolateInternalQueries;
    }

    @Generated
    public boolean isAutocommit() {
        return this.autocommit;
    }

    @Generated
    public boolean isReadOnly() {
        return this.readOnly;
    }

    @Generated
    public String getPhysicalNamingStrategyClassName() {
        return this.physicalNamingStrategyClassName;
    }

    @Generated
    public String getIsolationLevelName() {
        return this.isolationLevelName;
    }

    @Generated
    public String getPropagationBehaviorName() {
        return this.propagationBehaviorName;
    }

    @Generated
    public AbstractJpaProperties setDialect(String dialect) {
        this.dialect = dialect;
        return this;
    }

    @Generated
    public AbstractJpaProperties setDdlAuto(String ddlAuto) {
        this.ddlAuto = ddlAuto;
        return this;
    }

    @Generated
    public AbstractJpaProperties setDriverClass(String driverClass) {
        this.driverClass = driverClass;
        return this;
    }

    @Generated
    public AbstractJpaProperties setUrl(String url) {
        this.url = url;
        return this;
    }

    @Generated
    public AbstractJpaProperties setUser(String user) {
        this.user = user;
        return this;
    }

    @Generated
    public AbstractJpaProperties setPassword(String password) {
        this.password = password;
        return this;
    }

    @Generated
    public AbstractJpaProperties setDefaultCatalog(String defaultCatalog) {
        this.defaultCatalog = defaultCatalog;
        return this;
    }

    @Generated
    public AbstractJpaProperties setDefaultSchema(String defaultSchema) {
        this.defaultSchema = defaultSchema;
        return this;
    }

    @Generated
    public AbstractJpaProperties setHealthQuery(String healthQuery) {
        this.healthQuery = healthQuery;
        return this;
    }

    @Generated
    public AbstractJpaProperties setIdleTimeout(String idleTimeout) {
        this.idleTimeout = idleTimeout;
        return this;
    }

    @Generated
    public AbstractJpaProperties setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
        return this;
    }

    @Generated
    public AbstractJpaProperties setProperties(Map<String, String> properties) {
        this.properties = properties;
        return this;
    }

    @Generated
    public AbstractJpaProperties setPool(ConnectionPoolingProperties pool) {
        this.pool = pool;
        return this;
    }

    @Generated
    public AbstractJpaProperties setLeakThreshold(long leakThreshold) {
        this.leakThreshold = leakThreshold;
        return this;
    }

    @Generated
    public AbstractJpaProperties setGenerateStatistics(boolean generateStatistics) {
        this.generateStatistics = generateStatistics;
        return this;
    }

    @Generated
    public AbstractJpaProperties setBatchSize(int batchSize) {
        this.batchSize = batchSize;
        return this;
    }

    @Generated
    public AbstractJpaProperties setFetchSize(int fetchSize) {
        this.fetchSize = fetchSize;
        return this;
    }

    @Generated
    public AbstractJpaProperties setFailFastTimeout(long failFastTimeout) {
        this.failFastTimeout = failFastTimeout;
        return this;
    }

    @Generated
    public AbstractJpaProperties setIsolateInternalQueries(boolean isolateInternalQueries) {
        this.isolateInternalQueries = isolateInternalQueries;
        return this;
    }

    @Generated
    public AbstractJpaProperties setAutocommit(boolean autocommit) {
        this.autocommit = autocommit;
        return this;
    }

    @Generated
    public AbstractJpaProperties setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
        return this;
    }

    @Generated
    public AbstractJpaProperties setPhysicalNamingStrategyClassName(String physicalNamingStrategyClassName) {
        this.physicalNamingStrategyClassName = physicalNamingStrategyClassName;
        return this;
    }

    @Generated
    public AbstractJpaProperties setIsolationLevelName(String isolationLevelName) {
        this.isolationLevelName = isolationLevelName;
        return this;
    }

    @Generated
    public AbstractJpaProperties setPropagationBehaviorName(String propagationBehaviorName) {
        this.propagationBehaviorName = propagationBehaviorName;
        return this;
    }
}

