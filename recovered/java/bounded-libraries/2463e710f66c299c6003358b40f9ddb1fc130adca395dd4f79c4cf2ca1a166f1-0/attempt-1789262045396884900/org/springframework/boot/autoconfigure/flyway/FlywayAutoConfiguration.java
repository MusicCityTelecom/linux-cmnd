/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.flywaydb.core.Flyway
 *  org.flywaydb.core.api.MigrationVersion
 *  org.flywaydb.core.api.callback.Callback
 *  org.flywaydb.core.api.configuration.FluentConfiguration
 *  org.flywaydb.core.api.migration.JavaMigration
 *  org.flywaydb.core.internal.plugin.PluginRegister
 *  org.flywaydb.database.sqlserver.SQLServerConfigurationExtension
 *  org.springframework.beans.factory.ObjectProvider
 *  org.springframework.boot.context.properties.ConfigurationPropertiesBinding
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.context.properties.PropertyMapper
 *  org.springframework.boot.jdbc.DataSourceBuilder
 *  org.springframework.boot.jdbc.DatabaseDriver
 *  org.springframework.boot.sql.init.dependency.DatabaseInitializationDependencyConfigurer
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.ConfigurationCondition$ConfigurationPhase
 *  org.springframework.context.annotation.Import
 *  org.springframework.core.convert.TypeDescriptor
 *  org.springframework.core.convert.converter.GenericConverter
 *  org.springframework.core.convert.converter.GenericConverter$ConvertiblePair
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.jdbc.datasource.SimpleDriverDataSource
 *  org.springframework.jdbc.support.JdbcUtils
 *  org.springframework.jdbc.support.MetaDataAccessException
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.autoconfigure.flyway;

import java.sql.DatabaseMetaData;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationVersion;
import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.flywaydb.core.api.migration.JavaMigration;
import org.flywaydb.core.internal.plugin.PluginRegister;
import org.flywaydb.database.sqlserver.SQLServerConfigurationExtension;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayConfigurationCustomizer;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.autoconfigure.flyway.FlywaySchemaManagementProvider;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.boot.sql.init.dependency.DatabaseInitializationDependencyConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@AutoConfiguration(after={DataSourceAutoConfiguration.class, JdbcTemplateAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@ConditionalOnClass(value={Flyway.class})
@Conditional(value={FlywayDataSourceCondition.class})
@ConditionalOnProperty(prefix="spring.flyway", name={"enabled"}, matchIfMissing=true)
@Import(value={DatabaseInitializationDependencyConfigurer.class})
public class FlywayAutoConfiguration {
    @Bean
    @ConfigurationPropertiesBinding
    public StringOrNumberToMigrationVersionConverter stringOrNumberMigrationVersionConverter() {
        return new StringOrNumberToMigrationVersionConverter();
    }

    @Bean
    public FlywaySchemaManagementProvider flywayDefaultDdlModeProvider(ObjectProvider<Flyway> flyways) {
        return new FlywaySchemaManagementProvider((Iterable<Flyway>)flyways);
    }

    static final class FlywayDataSourceCondition
    extends AnyNestedCondition {
        FlywayDataSourceCondition() {
            super(ConfigurationCondition.ConfigurationPhase.REGISTER_BEAN);
        }

        @ConditionalOnProperty(prefix="spring.flyway", name={"url"})
        private static final class FlywayUrlCondition {
            private FlywayUrlCondition() {
            }
        }

        @ConditionalOnBean(value={DataSource.class})
        private static final class DataSourceBeanCondition {
            private DataSourceBeanCondition() {
            }
        }
    }

    static class StringOrNumberToMigrationVersionConverter
    implements GenericConverter {
        private static final Set<GenericConverter.ConvertiblePair> CONVERTIBLE_TYPES;

        StringOrNumberToMigrationVersionConverter() {
        }

        public Set<GenericConverter.ConvertiblePair> getConvertibleTypes() {
            return CONVERTIBLE_TYPES;
        }

        public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
            String value = ObjectUtils.nullSafeToString((Object)source);
            return MigrationVersion.fromVersion((String)value);
        }

        static {
            HashSet<GenericConverter.ConvertiblePair> types = new HashSet<GenericConverter.ConvertiblePair>(2);
            types.add(new GenericConverter.ConvertiblePair(String.class, MigrationVersion.class));
            types.add(new GenericConverter.ConvertiblePair(Number.class, MigrationVersion.class));
            CONVERTIBLE_TYPES = Collections.unmodifiableSet(types);
        }
    }

    private static class LocationResolver {
        private static final String VENDOR_PLACEHOLDER = "{vendor}";
        private final DataSource dataSource;

        LocationResolver(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        List<String> resolveLocations(List<String> locations) {
            if (this.usesVendorLocation(locations)) {
                DatabaseDriver databaseDriver = this.getDatabaseDriver();
                return this.replaceVendorLocations(locations, databaseDriver);
            }
            return locations;
        }

        private List<String> replaceVendorLocations(List<String> locations, DatabaseDriver databaseDriver) {
            if (databaseDriver == DatabaseDriver.UNKNOWN) {
                return locations;
            }
            String vendor = databaseDriver.getId();
            return locations.stream().map(location -> location.replace(VENDOR_PLACEHOLDER, vendor)).collect(Collectors.toList());
        }

        private DatabaseDriver getDatabaseDriver() {
            try {
                String url = (String)JdbcUtils.extractDatabaseMetaData((DataSource)this.dataSource, DatabaseMetaData::getURL);
                return DatabaseDriver.fromJdbcUrl((String)url);
            }
            catch (MetaDataAccessException ex) {
                throw new IllegalStateException(ex);
            }
        }

        private boolean usesVendorLocation(Collection<String> locations) {
            for (String location : locations) {
                if (!location.contains(VENDOR_PLACEHOLDER)) continue;
                return true;
            }
            return false;
        }
    }

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={JdbcUtils.class})
    @ConditionalOnMissingBean(value={Flyway.class})
    @EnableConfigurationProperties(value={FlywayProperties.class})
    public static class FlywayConfiguration {
        @Bean
        public Flyway flyway(FlywayProperties properties, ResourceLoader resourceLoader, ObjectProvider<DataSource> dataSource, @FlywayDataSource ObjectProvider<DataSource> flywayDataSource, ObjectProvider<FlywayConfigurationCustomizer> fluentConfigurationCustomizers, ObjectProvider<JavaMigration> javaMigrations, ObjectProvider<Callback> callbacks) {
            FluentConfiguration configuration = new FluentConfiguration(resourceLoader.getClassLoader());
            this.configureDataSource(configuration, properties, (DataSource)flywayDataSource.getIfAvailable(), (DataSource)dataSource.getIfUnique());
            this.configureProperties(configuration, properties);
            List<Callback> orderedCallbacks = callbacks.orderedStream().collect(Collectors.toList());
            this.configureCallbacks(configuration, orderedCallbacks);
            fluentConfigurationCustomizers.orderedStream().forEach(customizer -> customizer.customize(configuration));
            this.configureFlywayCallbacks(configuration, orderedCallbacks);
            List<JavaMigration> migrations = javaMigrations.stream().collect(Collectors.toList());
            this.configureJavaMigrations(configuration, migrations);
            return configuration.load();
        }

        private void configureDataSource(FluentConfiguration configuration, FlywayProperties properties, DataSource flywayDataSource, DataSource dataSource) {
            DataSource migrationDataSource = this.getMigrationDataSource(properties, flywayDataSource, dataSource);
            configuration.dataSource(migrationDataSource);
        }

        private DataSource getMigrationDataSource(FlywayProperties properties, DataSource flywayDataSource, DataSource dataSource) {
            if (flywayDataSource != null) {
                return flywayDataSource;
            }
            if (properties.getUrl() != null) {
                DataSourceBuilder builder = DataSourceBuilder.create().type(SimpleDriverDataSource.class);
                builder.url(properties.getUrl());
                this.applyCommonBuilderProperties(properties, builder);
                return builder.build();
            }
            if (properties.getUser() != null && dataSource != null) {
                DataSourceBuilder builder = DataSourceBuilder.derivedFrom((DataSource)dataSource).type(SimpleDriverDataSource.class);
                this.applyCommonBuilderProperties(properties, builder);
                return builder.build();
            }
            Assert.state((dataSource != null ? 1 : 0) != 0, (String)"Flyway migration DataSource missing");
            return dataSource;
        }

        private void applyCommonBuilderProperties(FlywayProperties properties, DataSourceBuilder<?> builder) {
            builder.username(properties.getUser());
            builder.password(properties.getPassword());
            if (StringUtils.hasText((String)properties.getDriverClassName())) {
                builder.driverClassName(properties.getDriverClassName());
            }
        }

        private void configureProperties(FluentConfiguration configuration, FlywayProperties properties) {
            PropertyMapper map = PropertyMapper.get().alwaysApplyingWhenNonNull();
            String[] locations = new LocationResolver(configuration.getDataSource()).resolveLocations(properties.getLocations()).toArray(new String[0]);
            this.configureFailOnMissingLocations(configuration, properties.isFailOnMissingLocations());
            map.from((Object)locations).to(arg_0 -> ((FluentConfiguration)configuration).locations(arg_0));
            map.from((Object)properties.getEncoding()).to(arg_0 -> ((FluentConfiguration)configuration).encoding(arg_0));
            map.from((Object)properties.getConnectRetries()).to(arg_0 -> ((FluentConfiguration)configuration).connectRetries(arg_0));
            map.from((Object)properties.getConnectRetriesInterval()).to(interval -> configuration.connectRetriesInterval((int)interval.getSeconds()));
            map.from((Object)properties.getLockRetryCount()).to(lockRetryCount -> configuration.lockRetryCount(lockRetryCount.intValue()));
            map.from((Object)properties.getDefaultSchema()).to(schema -> configuration.defaultSchema(schema));
            map.from(properties.getSchemas()).as(StringUtils::toStringArray).to(arg_0 -> ((FluentConfiguration)configuration).schemas(arg_0));
            this.configureCreateSchemas(configuration, properties.isCreateSchemas());
            map.from((Object)properties.getTable()).to(arg_0 -> ((FluentConfiguration)configuration).table(arg_0));
            map.from((Object)properties.getTablespace()).to(tablespace -> configuration.tablespace(tablespace));
            map.from((Object)properties.getBaselineDescription()).to(arg_0 -> ((FluentConfiguration)configuration).baselineDescription(arg_0));
            map.from((Object)properties.getBaselineVersion()).to(arg_0 -> ((FluentConfiguration)configuration).baselineVersion(arg_0));
            map.from((Object)properties.getInstalledBy()).to(arg_0 -> ((FluentConfiguration)configuration).installedBy(arg_0));
            map.from(properties.getPlaceholders()).to(arg_0 -> ((FluentConfiguration)configuration).placeholders(arg_0));
            map.from((Object)properties.getPlaceholderPrefix()).to(arg_0 -> ((FluentConfiguration)configuration).placeholderPrefix(arg_0));
            map.from((Object)properties.getPlaceholderSuffix()).to(arg_0 -> ((FluentConfiguration)configuration).placeholderSuffix(arg_0));
            map.from((Object)properties.getPlaceholderSeparator()).to(placeHolderSeparator -> configuration.placeholderSeparator(placeHolderSeparator));
            map.from((Object)properties.isPlaceholderReplacement()).to(arg_0 -> ((FluentConfiguration)configuration).placeholderReplacement(arg_0));
            map.from((Object)properties.getSqlMigrationPrefix()).to(arg_0 -> ((FluentConfiguration)configuration).sqlMigrationPrefix(arg_0));
            map.from(properties.getSqlMigrationSuffixes()).as(StringUtils::toStringArray).to(arg_0 -> ((FluentConfiguration)configuration).sqlMigrationSuffixes(arg_0));
            map.from((Object)properties.getSqlMigrationSeparator()).to(arg_0 -> ((FluentConfiguration)configuration).sqlMigrationSeparator(arg_0));
            map.from((Object)properties.getRepeatableSqlMigrationPrefix()).to(arg_0 -> ((FluentConfiguration)configuration).repeatableSqlMigrationPrefix(arg_0));
            map.from((Object)properties.getTarget()).to(arg_0 -> ((FluentConfiguration)configuration).target(arg_0));
            map.from((Object)properties.isBaselineOnMigrate()).to(arg_0 -> ((FluentConfiguration)configuration).baselineOnMigrate(arg_0));
            map.from((Object)properties.isCleanDisabled()).to(arg_0 -> ((FluentConfiguration)configuration).cleanDisabled(arg_0));
            map.from((Object)properties.isCleanOnValidationError()).to(arg_0 -> ((FluentConfiguration)configuration).cleanOnValidationError(arg_0));
            map.from((Object)properties.isGroup()).to(arg_0 -> ((FluentConfiguration)configuration).group(arg_0));
            this.configureIgnoredMigrations(configuration, properties, map);
            map.from((Object)properties.isMixed()).to(arg_0 -> ((FluentConfiguration)configuration).mixed(arg_0));
            map.from((Object)properties.isOutOfOrder()).to(arg_0 -> ((FluentConfiguration)configuration).outOfOrder(arg_0));
            map.from((Object)properties.isSkipDefaultCallbacks()).to(arg_0 -> ((FluentConfiguration)configuration).skipDefaultCallbacks(arg_0));
            map.from((Object)properties.isSkipDefaultResolvers()).to(arg_0 -> ((FluentConfiguration)configuration).skipDefaultResolvers(arg_0));
            this.configureValidateMigrationNaming(configuration, properties.isValidateMigrationNaming());
            map.from((Object)properties.isValidateOnMigrate()).to(arg_0 -> ((FluentConfiguration)configuration).validateOnMigrate(arg_0));
            map.from(properties.getInitSqls()).whenNot(CollectionUtils::isEmpty).as(initSqls -> StringUtils.collectionToDelimitedString((Collection)initSqls, (String)"\n")).to(arg_0 -> ((FluentConfiguration)configuration).initSql(arg_0));
            map.from((Object)properties.getScriptPlaceholderPrefix()).to(prefix -> configuration.scriptPlaceholderPrefix(prefix));
            map.from((Object)properties.getScriptPlaceholderSuffix()).to(suffix -> configuration.scriptPlaceholderSuffix(suffix));
            map.from((Object)properties.getBatch()).to(arg_0 -> ((FluentConfiguration)configuration).batch(arg_0));
            map.from((Object)properties.getDryRunOutput()).to(arg_0 -> ((FluentConfiguration)configuration).dryRunOutput(arg_0));
            map.from((Object)properties.getErrorOverrides()).to(arg_0 -> ((FluentConfiguration)configuration).errorOverrides(arg_0));
            map.from((Object)properties.getLicenseKey()).to(arg_0 -> ((FluentConfiguration)configuration).licenseKey(arg_0));
            map.from((Object)properties.getOracleSqlplus()).to(arg_0 -> ((FluentConfiguration)configuration).oracleSqlplus(arg_0));
            map.from((Object)properties.getOracleSqlplusWarn()).to(oracleSqlplusWarn -> configuration.oracleSqlplusWarn(oracleSqlplusWarn.booleanValue()));
            map.from((Object)properties.getStream()).to(arg_0 -> ((FluentConfiguration)configuration).stream(arg_0));
            map.from((Object)properties.getUndoSqlMigrationPrefix()).to(arg_0 -> ((FluentConfiguration)configuration).undoSqlMigrationPrefix(arg_0));
            map.from((Object)properties.getCherryPick()).to(cherryPick -> configuration.cherryPick(cherryPick));
            map.from(properties.getJdbcProperties()).whenNot(Map::isEmpty).to(jdbcProperties -> configuration.jdbcProperties(jdbcProperties));
            map.from((Object)properties.getKerberosConfigFile()).to(configFile -> configuration.kerberosConfigFile(configFile));
            map.from((Object)properties.getOracleKerberosCacheFile()).to(cacheFile -> configuration.oracleKerberosCacheFile(cacheFile));
            map.from((Object)properties.getOutputQueryResults()).to(outputQueryResults -> configuration.outputQueryResults(outputQueryResults.booleanValue()));
            map.from((Object)properties.getSqlServerKerberosLoginFile()).whenNonNull().to(this::configureSqlServerKerberosLoginFile);
            map.from((Object)properties.getSkipExecutingMigrations()).to(skipExecutingMigrations -> configuration.skipExecutingMigrations(skipExecutingMigrations.booleanValue()));
            map.from(properties.getIgnoreMigrationPatterns()).whenNot(List::isEmpty).to(ignoreMigrationPatterns -> configuration.ignoreMigrationPatterns(ignoreMigrationPatterns.toArray(new String[0])));
            map.from((Object)properties.getDetectEncoding()).to(detectEncoding -> configuration.detectEncoding(detectEncoding.booleanValue()));
            map.from((Object)properties.getBaselineMigrationPrefix()).to(baselineMigrationPrefix -> configuration.baselineMigrationPrefix(baselineMigrationPrefix));
        }

        private void configureIgnoredMigrations(FluentConfiguration configuration, FlywayProperties properties, PropertyMapper map) {
            try {
                map.from((Object)properties.isIgnoreMissingMigrations()).to(arg_0 -> ((FluentConfiguration)configuration).ignoreMissingMigrations(arg_0));
                map.from((Object)properties.isIgnoreIgnoredMigrations()).to(arg_0 -> ((FluentConfiguration)configuration).ignoreIgnoredMigrations(arg_0));
                map.from((Object)properties.isIgnorePendingMigrations()).to(arg_0 -> ((FluentConfiguration)configuration).ignorePendingMigrations(arg_0));
                map.from((Object)properties.isIgnoreFutureMigrations()).to(arg_0 -> ((FluentConfiguration)configuration).ignoreFutureMigrations(arg_0));
            }
            catch (BootstrapMethodError | NoSuchMethodError linkageError) {
                // empty catch block
            }
        }

        private void configureFailOnMissingLocations(FluentConfiguration configuration, boolean failOnMissingLocations) {
            try {
                configuration.failOnMissingLocations(failOnMissingLocations);
            }
            catch (NoSuchMethodError noSuchMethodError) {
                // empty catch block
            }
        }

        private void configureCreateSchemas(FluentConfiguration configuration, boolean createSchemas) {
            try {
                configuration.createSchemas(createSchemas);
            }
            catch (NoSuchMethodError noSuchMethodError) {
                // empty catch block
            }
        }

        private void configureSqlServerKerberosLoginFile(String sqlServerKerberosLoginFile) {
            SQLServerConfigurationExtension sqlServerConfigurationExtension = (SQLServerConfigurationExtension)PluginRegister.getPlugin(SQLServerConfigurationExtension.class);
            sqlServerConfigurationExtension.setKerberosLoginFile(sqlServerKerberosLoginFile);
        }

        private void configureValidateMigrationNaming(FluentConfiguration configuration, boolean validateMigrationNaming) {
            try {
                configuration.validateMigrationNaming(validateMigrationNaming);
            }
            catch (NoSuchMethodError noSuchMethodError) {
                // empty catch block
            }
        }

        private void configureCallbacks(FluentConfiguration configuration, List<Callback> callbacks) {
            if (!callbacks.isEmpty()) {
                configuration.callbacks(callbacks.toArray(new Callback[0]));
            }
        }

        private void configureFlywayCallbacks(FluentConfiguration flyway, List<Callback> callbacks) {
            if (!callbacks.isEmpty()) {
                flyway.callbacks(callbacks.toArray(new Callback[0]));
            }
        }

        private void configureJavaMigrations(FluentConfiguration flyway, List<JavaMigration> migrations) {
            if (!migrations.isEmpty()) {
                try {
                    flyway.javaMigrations(migrations.toArray(new JavaMigration[0]));
                }
                catch (NoSuchMethodError noSuchMethodError) {
                    // empty catch block
                }
            }
        }

        @Bean
        @ConditionalOnMissingBean
        public FlywayMigrationInitializer flywayInitializer(Flyway flyway, ObjectProvider<FlywayMigrationStrategy> migrationStrategy) {
            return new FlywayMigrationInitializer(flyway, (FlywayMigrationStrategy)migrationStrategy.getIfAvailable());
        }
    }
}

