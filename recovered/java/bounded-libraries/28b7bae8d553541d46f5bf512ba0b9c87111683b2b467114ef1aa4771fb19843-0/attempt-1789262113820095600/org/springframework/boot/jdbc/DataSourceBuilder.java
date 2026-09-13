/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mchange.v2.c3p0.AbstractComboPooledDataSource
 *  com.mchange.v2.c3p0.ComboPooledDataSource
 *  com.zaxxer.hikari.HikariConfig
 *  com.zaxxer.hikari.HikariDataSource
 *  oracle.jdbc.datasource.OracleCommonDataSource
 *  oracle.jdbc.datasource.OracleDataSource
 *  oracle.ucp.jdbc.PoolDataSource
 *  oracle.ucp.jdbc.PoolDataSourceImpl
 *  org.apache.commons.dbcp2.BasicDataSource
 *  org.apache.tomcat.jdbc.pool.DataSource
 *  org.apache.tomcat.jdbc.pool.DataSourceProxy
 *  org.h2.jdbcx.JdbcDataSource
 *  org.postgresql.ds.PGSimpleDataSource
 *  org.postgresql.ds.common.BaseDataSource
 *  org.springframework.beans.BeanUtils
 *  org.springframework.core.ResolvableType
 *  org.springframework.jdbc.datasource.AbstractDriverBasedDataSource
 *  org.springframework.jdbc.datasource.SimpleDriverDataSource
 *  org.springframework.jdbc.datasource.embedded.EmbeddedDatabase
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ReflectionUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.jdbc;

import com.mchange.v2.c3p0.AbstractComboPooledDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.beans.PropertyVetoException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Supplier;
import oracle.jdbc.datasource.OracleCommonDataSource;
import oracle.jdbc.datasource.OracleDataSource;
import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceImpl;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.DataSourceProxy;
import org.h2.jdbcx.JdbcDataSource;
import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.ds.common.BaseDataSource;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.boot.jdbc.UnsupportedDataSourcePropertyException;
import org.springframework.core.ResolvableType;
import org.springframework.jdbc.datasource.AbstractDriverBasedDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

public final class DataSourceBuilder<T extends javax.sql.DataSource> {
    private final ClassLoader classLoader;
    private final Map<DataSourceProperty, String> values = new HashMap<DataSourceProperty, String>();
    private Class<T> type;
    private final javax.sql.DataSource deriveFrom;

    private DataSourceBuilder(ClassLoader classLoader) {
        this.classLoader = classLoader;
        this.deriveFrom = null;
    }

    private DataSourceBuilder(T deriveFrom) {
        Assert.notNull(deriveFrom, (String)"DataSource must not be null");
        this.classLoader = deriveFrom.getClass().getClassLoader();
        this.type = deriveFrom.getClass();
        this.deriveFrom = deriveFrom;
    }

    public <D extends javax.sql.DataSource> DataSourceBuilder<D> type(Class<D> type) {
        this.type = type;
        return this;
    }

    public DataSourceBuilder<T> url(String url) {
        this.set(DataSourceProperty.URL, url);
        return this;
    }

    public DataSourceBuilder<T> driverClassName(String driverClassName) {
        this.set(DataSourceProperty.DRIVER_CLASS_NAME, driverClassName);
        return this;
    }

    public DataSourceBuilder<T> username(String username) {
        this.set(DataSourceProperty.USERNAME, username);
        return this;
    }

    public DataSourceBuilder<T> password(String password) {
        this.set(DataSourceProperty.PASSWORD, password);
        return this;
    }

    private void set(DataSourceProperty property, String value) {
        this.values.put(property, value);
    }

    public T build() {
        DataSourceProperties<javax.sql.DataSource> properties = DataSourceProperties.forType(this.classLoader, this.type);
        DataSourceProperties<javax.sql.DataSource> deriveFromProperties = this.getDeriveFromProperties();
        Class<T> instanceType = this.type != null ? this.type : properties.getDataSourceInstanceType();
        javax.sql.DataSource dataSource = (javax.sql.DataSource)BeanUtils.instantiateClass(instanceType);
        HashSet<DataSourceProperty> applied = new HashSet<DataSourceProperty>();
        for (DataSourceProperty property : DataSourceProperty.values()) {
            String value = this.values.get((Object)property);
            if (value == null && deriveFromProperties != null && properties.canSet(property)) {
                value = deriveFromProperties.get(this.deriveFrom, property);
            }
            if (value == null) continue;
            properties.set(dataSource, property, value);
            applied.add(property);
        }
        if (!applied.contains((Object)DataSourceProperty.DRIVER_CLASS_NAME) && properties.canSet(DataSourceProperty.DRIVER_CLASS_NAME) && this.values.containsKey((Object)DataSourceProperty.URL)) {
            String url = this.values.get((Object)DataSourceProperty.URL);
            DatabaseDriver driver = DatabaseDriver.fromJdbcUrl(url);
            properties.set(dataSource, DataSourceProperty.DRIVER_CLASS_NAME, driver.getDriverClassName());
        }
        return (T)dataSource;
    }

    private DataSourceProperties<javax.sql.DataSource> getDeriveFromProperties() {
        if (this.deriveFrom == null) {
            return null;
        }
        return DataSourceProperties.forType(this.classLoader, this.deriveFrom.getClass());
    }

    public static DataSourceBuilder<?> create() {
        return DataSourceBuilder.create(null);
    }

    public static DataSourceBuilder<?> create(ClassLoader classLoader) {
        return new DataSourceBuilder(classLoader);
    }

    public static DataSourceBuilder<?> derivedFrom(javax.sql.DataSource dataSource) {
        if (dataSource instanceof EmbeddedDatabase) {
            try {
                dataSource = dataSource.unwrap(javax.sql.DataSource.class);
            }
            catch (SQLException ex) {
                throw new IllegalStateException("Unable to unwrap embedded database", ex);
            }
        }
        return new DataSourceBuilder<javax.sql.DataSource>(dataSource);
    }

    public static Class<? extends javax.sql.DataSource> findType(ClassLoader classLoader) {
        MappedDataSourceProperties mappings = MappedDataSourceProperties.forType(classLoader, null);
        return mappings != null ? mappings.getDataSourceInstanceType() : null;
    }

    private static class PostgresDataSourceProperties
    extends MappedDataSourceProperties<PGSimpleDataSource> {
        PostgresDataSourceProperties() {
            this.add(DataSourceProperty.URL, BaseDataSource::getUrl, BaseDataSource::setUrl);
            this.add(DataSourceProperty.USERNAME, BaseDataSource::getUser, BaseDataSource::setUser);
            this.add(DataSourceProperty.PASSWORD, BaseDataSource::getPassword, BaseDataSource::setPassword);
        }
    }

    private static class H2DataSourceProperties
    extends MappedDataSourceProperties<JdbcDataSource> {
        H2DataSourceProperties() {
            this.add(DataSourceProperty.URL, JdbcDataSource::getUrl, JdbcDataSource::setUrl);
            this.add(DataSourceProperty.USERNAME, JdbcDataSource::getUser, JdbcDataSource::setUser);
            this.add(DataSourceProperty.PASSWORD, JdbcDataSource::getPassword, JdbcDataSource::setPassword);
        }
    }

    private static class OracleDataSourceProperties
    extends MappedDataSourceProperties<OracleDataSource> {
        OracleDataSourceProperties() {
            this.add(DataSourceProperty.URL, OracleCommonDataSource::getURL, OracleCommonDataSource::setURL);
            this.add(DataSourceProperty.USERNAME, OracleCommonDataSource::getUser, OracleCommonDataSource::setUser);
            this.add(DataSourceProperty.PASSWORD, null, OracleCommonDataSource::setPassword);
        }
    }

    private static class SimpleDataSourceProperties
    extends MappedDataSourceProperties<SimpleDriverDataSource> {
        SimpleDataSourceProperties() {
            this.add(DataSourceProperty.URL, AbstractDriverBasedDataSource::getUrl, AbstractDriverBasedDataSource::setUrl);
            this.add(DataSourceProperty.DRIVER_CLASS_NAME, Class.class, dataSource -> dataSource.getDriver().getClass(), SimpleDriverDataSource::setDriverClass);
            this.add(DataSourceProperty.USERNAME, AbstractDriverBasedDataSource::getUsername, AbstractDriverBasedDataSource::setUsername);
            this.add(DataSourceProperty.PASSWORD, AbstractDriverBasedDataSource::getPassword, AbstractDriverBasedDataSource::setPassword);
        }
    }

    private static class ComboPooledDataSourceProperties
    extends MappedDataSourceProperties<ComboPooledDataSource> {
        ComboPooledDataSourceProperties() {
            this.add(DataSourceProperty.URL, AbstractComboPooledDataSource::getJdbcUrl, AbstractComboPooledDataSource::setJdbcUrl);
            this.add(DataSourceProperty.DRIVER_CLASS_NAME, AbstractComboPooledDataSource::getDriverClass, this::setDriverClass);
            this.add(DataSourceProperty.USERNAME, AbstractComboPooledDataSource::getUser, AbstractComboPooledDataSource::setUser);
            this.add(DataSourceProperty.PASSWORD, AbstractComboPooledDataSource::getPassword, AbstractComboPooledDataSource::setPassword);
        }

        private void setDriverClass(ComboPooledDataSource dataSource, String driverClass) {
            try {
                dataSource.setDriverClass(driverClass);
            }
            catch (PropertyVetoException ex) {
                throw new IllegalArgumentException(ex);
            }
        }
    }

    private static class OraclePoolDataSourceProperties
    extends MappedDataSourceProperties<PoolDataSource> {
        @Override
        public Class<? extends PoolDataSource> getDataSourceInstanceType() {
            return PoolDataSourceImpl.class;
        }

        OraclePoolDataSourceProperties() {
            this.add(DataSourceProperty.URL, PoolDataSource::getURL, PoolDataSource::setURL);
            this.add(DataSourceProperty.DRIVER_CLASS_NAME, PoolDataSource::getConnectionFactoryClassName, PoolDataSource::setConnectionFactoryClassName);
            this.add(DataSourceProperty.USERNAME, PoolDataSource::getUser, PoolDataSource::setUser);
            this.add(DataSourceProperty.PASSWORD, null, PoolDataSource::setPassword);
        }
    }

    private static class MappedDbcp2DataSource
    extends MappedDataSourceProperties<BasicDataSource> {
        MappedDbcp2DataSource() {
            this.add(DataSourceProperty.URL, BasicDataSource::getUrl, BasicDataSource::setUrl);
            this.add(DataSourceProperty.DRIVER_CLASS_NAME, BasicDataSource::getDriverClassName, BasicDataSource::setDriverClassName);
            this.add(DataSourceProperty.USERNAME, BasicDataSource::getUsername, BasicDataSource::setUsername);
            this.add(DataSourceProperty.PASSWORD, BasicDataSource::getPassword, BasicDataSource::setPassword);
        }
    }

    private static class TomcatPoolDataSourceProperties
    extends MappedDataSourceProperties<DataSource> {
        TomcatPoolDataSourceProperties() {
            this.add(DataSourceProperty.URL, DataSourceProxy::getUrl, DataSourceProxy::setUrl);
            this.add(DataSourceProperty.DRIVER_CLASS_NAME, DataSourceProxy::getDriverClassName, DataSourceProxy::setDriverClassName);
            this.add(DataSourceProperty.USERNAME, DataSourceProxy::getUsername, DataSourceProxy::setUsername);
            this.add(DataSourceProperty.PASSWORD, DataSourceProxy::getPassword, DataSourceProxy::setPassword);
        }
    }

    private static class HikariDataSourceProperties
    extends MappedDataSourceProperties<HikariDataSource> {
        HikariDataSourceProperties() {
            this.add(DataSourceProperty.URL, HikariConfig::getJdbcUrl, HikariConfig::setJdbcUrl);
            this.add(DataSourceProperty.DRIVER_CLASS_NAME, HikariConfig::getDriverClassName, HikariConfig::setDriverClassName);
            this.add(DataSourceProperty.USERNAME, HikariConfig::getUsername, HikariConfig::setUsername);
            this.add(DataSourceProperty.PASSWORD, HikariConfig::getPassword, HikariConfig::setPassword);
        }
    }

    @FunctionalInterface
    private static interface Setter<T, V> {
        public void set(T var1, V var2) throws SQLException;
    }

    @FunctionalInterface
    private static interface Getter<T, V> {
        public V get(T var1) throws SQLException;
    }

    private static class ReflectionDataSourceProperties<T extends javax.sql.DataSource>
    implements DataSourceProperties<T> {
        private final Map<DataSourceProperty, Method> getters;
        private final Map<DataSourceProperty, Method> setters;
        private final Class<T> dataSourceType;

        ReflectionDataSourceProperties(Class<T> dataSourceType) {
            Assert.state((dataSourceType != null ? 1 : 0) != 0, (String)"No supported DataSource type found");
            HashMap<DataSourceProperty, Method> getters = new HashMap<DataSourceProperty, Method>();
            HashMap<DataSourceProperty, Method> setters = new HashMap<DataSourceProperty, Method>();
            for (DataSourceProperty property : DataSourceProperty.values()) {
                this.putIfNotNull(getters, property, property.findGetter(dataSourceType));
                this.putIfNotNull(setters, property, property.findSetter(dataSourceType));
            }
            this.dataSourceType = dataSourceType;
            this.getters = Collections.unmodifiableMap(getters);
            this.setters = Collections.unmodifiableMap(setters);
        }

        private void putIfNotNull(Map<DataSourceProperty, Method> map, DataSourceProperty property, Method method) {
            if (method != null) {
                map.put(property, method);
            }
        }

        @Override
        public Class<T> getDataSourceInstanceType() {
            return this.dataSourceType;
        }

        @Override
        public boolean canSet(DataSourceProperty property) {
            return this.setters.containsKey((Object)property);
        }

        @Override
        public void set(T dataSource, DataSourceProperty property, String value) {
            Method method = this.getMethod(property, this.setters);
            if (method != null) {
                ReflectionUtils.invokeMethod((Method)method, dataSource, (Object[])new Object[]{value});
            }
        }

        @Override
        public String get(T dataSource, DataSourceProperty property) {
            Method method = this.getMethod(property, this.getters);
            if (method != null) {
                return (String)ReflectionUtils.invokeMethod((Method)method, dataSource);
            }
            return null;
        }

        private Method getMethod(DataSourceProperty property, Map<DataSourceProperty, Method> methods) {
            Method method = methods.get((Object)property);
            if (method == null) {
                UnsupportedDataSourcePropertyException.throwIf(!property.isOptional(), () -> "Unable to find suitable method for " + (Object)((Object)property));
                return null;
            }
            ReflectionUtils.makeAccessible((Method)method);
            return method;
        }
    }

    private static class MappedDataSourceProperty<T extends javax.sql.DataSource, V> {
        private final DataSourceProperty property;
        private final Class<V> type;
        private final Getter<T, V> getter;
        private final Setter<T, V> setter;

        MappedDataSourceProperty(DataSourceProperty property, Class<V> type, Getter<T, V> getter, Setter<T, V> setter) {
            this.property = property;
            this.type = type;
            this.getter = getter;
            this.setter = setter;
        }

        void set(T dataSource, String value) {
            try {
                if (this.setter == null) {
                    UnsupportedDataSourcePropertyException.throwIf(!this.property.isOptional(), () -> "No setter mapped for '" + (Object)((Object)this.property) + "' property");
                    return;
                }
                this.setter.set(dataSource, this.convertFromString(value));
            }
            catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }

        String get(T dataSource) {
            try {
                if (this.getter == null) {
                    UnsupportedDataSourcePropertyException.throwIf(!this.property.isOptional(), () -> "No getter mapped for '" + (Object)((Object)this.property) + "' property");
                    return null;
                }
                return this.convertToString(this.getter.get(dataSource));
            }
            catch (SQLException ex) {
                throw new IllegalStateException(ex);
            }
        }

        private V convertFromString(String value) {
            if (String.class.equals(this.type)) {
                return (V)value;
            }
            if (Class.class.equals(this.type)) {
                return (V)ClassUtils.resolveClassName((String)value, null);
            }
            throw new IllegalStateException("Unsupported value type " + this.type);
        }

        private String convertToString(V value) {
            if (String.class.equals(this.type)) {
                return (String)value;
            }
            if (Class.class.equals(this.type)) {
                return ((Class)value).getName();
            }
            throw new IllegalStateException("Unsupported value type " + this.type);
        }
    }

    private static class MappedDataSourceProperties<T extends javax.sql.DataSource>
    implements DataSourceProperties<T> {
        private final Map<DataSourceProperty, MappedDataSourceProperty<T, ?>> mappedProperties = new HashMap();
        private final Class<T> dataSourceType = ResolvableType.forClass(MappedDataSourceProperties.class, this.getClass()).resolveGeneric(new int[0]);

        MappedDataSourceProperties() {
        }

        @Override
        public Class<? extends T> getDataSourceInstanceType() {
            return this.dataSourceType;
        }

        protected void add(DataSourceProperty property, Getter<T, String> getter, Setter<T, String> setter) {
            this.add(property, String.class, getter, setter);
        }

        protected <V> void add(DataSourceProperty property, Class<V> type, Getter<T, V> getter, Setter<T, V> setter) {
            this.mappedProperties.put(property, new MappedDataSourceProperty<T, V>(property, type, getter, setter));
        }

        @Override
        public boolean canSet(DataSourceProperty property) {
            return this.mappedProperties.containsKey((Object)property);
        }

        @Override
        public void set(T dataSource, DataSourceProperty property, String value) {
            MappedDataSourceProperty<T, ?> mappedProperty = this.getMapping(property);
            if (mappedProperty != null) {
                mappedProperty.set(dataSource, value);
            }
        }

        @Override
        public String get(T dataSource, DataSourceProperty property) {
            MappedDataSourceProperty<T, ?> mappedProperty = this.getMapping(property);
            if (mappedProperty != null) {
                return mappedProperty.get(dataSource);
            }
            return null;
        }

        private MappedDataSourceProperty<T, ?> getMapping(DataSourceProperty property) {
            MappedDataSourceProperty<T, ?> mappedProperty = this.mappedProperties.get((Object)property);
            UnsupportedDataSourcePropertyException.throwIf(!property.isOptional() && mappedProperty == null, () -> "No mapping found for " + (Object)((Object)property));
            return mappedProperty;
        }

        static <T extends javax.sql.DataSource> MappedDataSourceProperties<T> forType(ClassLoader classLoader, Class<T> type) {
            MappedDataSourceProperties<T> pooled = MappedDataSourceProperties.lookupPooled(classLoader, type);
            if (type == null || pooled != null) {
                return pooled;
            }
            return MappedDataSourceProperties.lookupBasic(classLoader, type);
        }

        private static <T extends javax.sql.DataSource> MappedDataSourceProperties<T> lookupPooled(ClassLoader classLoader, Class<T> type) {
            MappedDataSourceProperties<T> result = null;
            result = MappedDataSourceProperties.lookup(classLoader, type, result, "com.zaxxer.hikari.HikariDataSource", HikariDataSourceProperties::new, new String[0]);
            result = MappedDataSourceProperties.lookup(classLoader, type, result, "org.apache.tomcat.jdbc.pool.DataSource", TomcatPoolDataSourceProperties::new, new String[0]);
            result = MappedDataSourceProperties.lookup(classLoader, type, result, "org.apache.commons.dbcp2.BasicDataSource", MappedDbcp2DataSource::new, new String[0]);
            result = MappedDataSourceProperties.lookup(classLoader, type, result, "oracle.ucp.jdbc.PoolDataSourceImpl", OraclePoolDataSourceProperties::new, "oracle.jdbc.OracleConnection");
            result = MappedDataSourceProperties.lookup(classLoader, type, result, "com.mchange.v2.c3p0.ComboPooledDataSource", ComboPooledDataSourceProperties::new, new String[0]);
            return result;
        }

        private static <T extends javax.sql.DataSource> MappedDataSourceProperties<T> lookupBasic(ClassLoader classLoader, Class<T> dataSourceType) {
            MappedDataSourceProperties<T> result = null;
            result = MappedDataSourceProperties.lookup(classLoader, dataSourceType, result, "org.springframework.jdbc.datasource.SimpleDriverDataSource", SimpleDataSourceProperties::new, new String[0]);
            result = MappedDataSourceProperties.lookup(classLoader, dataSourceType, result, "oracle.jdbc.datasource.OracleDataSource", OracleDataSourceProperties::new, new String[0]);
            result = MappedDataSourceProperties.lookup(classLoader, dataSourceType, result, "org.h2.jdbcx.JdbcDataSource", H2DataSourceProperties::new, new String[0]);
            result = MappedDataSourceProperties.lookup(classLoader, dataSourceType, result, "org.postgresql.ds.PGSimpleDataSource", PostgresDataSourceProperties::new, new String[0]);
            return result;
        }

        private static <T extends javax.sql.DataSource> MappedDataSourceProperties<T> lookup(ClassLoader classLoader, Class<T> dataSourceType, MappedDataSourceProperties<T> existing, String dataSourceClassName, Supplier<MappedDataSourceProperties<?>> propertyMappingsSupplier, String ... requiredClassNames) {
            if (existing != null || !MappedDataSourceProperties.allPresent(classLoader, dataSourceClassName, requiredClassNames)) {
                return existing;
            }
            MappedDataSourceProperties<?> propertyMappings = propertyMappingsSupplier.get();
            return dataSourceType == null || propertyMappings.getDataSourceInstanceType().isAssignableFrom(dataSourceType) ? propertyMappings : null;
        }

        private static boolean allPresent(ClassLoader classLoader, String dataSourceClassName, String[] requiredClassNames) {
            boolean result = ClassUtils.isPresent((String)dataSourceClassName, (ClassLoader)classLoader);
            for (String requiredClassName : requiredClassNames) {
                result = result && ClassUtils.isPresent((String)requiredClassName, (ClassLoader)classLoader);
            }
            return result;
        }
    }

    private static interface DataSourceProperties<T extends javax.sql.DataSource> {
        public Class<? extends T> getDataSourceInstanceType();

        public boolean canSet(DataSourceProperty var1);

        public void set(T var1, DataSourceProperty var2, String var3);

        public String get(T var1, DataSourceProperty var2);

        public static <T extends javax.sql.DataSource> DataSourceProperties<T> forType(ClassLoader classLoader, Class<T> type) {
            MappedDataSourceProperties<T> mapped = MappedDataSourceProperties.forType(classLoader, type);
            return mapped != null ? mapped : new ReflectionDataSourceProperties<T>(type);
        }
    }

    private static enum DataSourceProperty {
        URL(false, "url", "URL"),
        DRIVER_CLASS_NAME(true, "driverClassName"),
        USERNAME(false, "username", "user"),
        PASSWORD(false, "password");

        private final boolean optional;
        private final String[] names;

        private DataSourceProperty(boolean optional, String ... names) {
            this.optional = optional;
            this.names = names;
        }

        boolean isOptional() {
            return this.optional;
        }

        public String toString() {
            return this.names[0];
        }

        Method findSetter(Class<?> type) {
            return this.findMethod("set", type, String.class);
        }

        Method findGetter(Class<?> type) {
            return this.findMethod("get", type, new Class[0]);
        }

        private Method findMethod(String prefix, Class<?> type, Class<?> ... paramTypes) {
            for (String name : this.names) {
                String candidate = prefix + StringUtils.capitalize((String)name);
                Method method = ReflectionUtils.findMethod(type, (String)candidate, (Class[])paramTypes);
                if (method == null) continue;
                return method;
            }
            return null;
        }
    }
}

