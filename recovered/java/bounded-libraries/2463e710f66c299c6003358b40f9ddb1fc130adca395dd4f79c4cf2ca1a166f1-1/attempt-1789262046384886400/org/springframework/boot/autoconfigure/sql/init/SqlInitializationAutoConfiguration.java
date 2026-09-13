/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.sql.init.dependency.DatabaseInitializationDependencyConfigurer
 *  org.springframework.context.annotation.Conditional
 *  org.springframework.context.annotation.ConfigurationCondition$ConfigurationPhase
 *  org.springframework.context.annotation.Import
 */
package org.springframework.boot.autoconfigure.sql.init;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.NoneNestedConditions;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.boot.autoconfigure.sql.init.DataSourceInitializationConfiguration;
import org.springframework.boot.autoconfigure.sql.init.R2dbcInitializationConfiguration;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.sql.init.dependency.DatabaseInitializationDependencyConfigurer;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.context.annotation.Import;

@AutoConfiguration(after={R2dbcAutoConfiguration.class, DataSourceAutoConfiguration.class})
@EnableConfigurationProperties(value={SqlInitializationProperties.class})
@Import(value={DatabaseInitializationDependencyConfigurer.class, R2dbcInitializationConfiguration.class, DataSourceInitializationConfiguration.class})
@ConditionalOnProperty(prefix="spring.sql.init", name={"enabled"}, matchIfMissing=true)
@Conditional(value={SqlInitializationModeCondition.class})
public class SqlInitializationAutoConfiguration {

    static class SqlInitializationModeCondition
    extends NoneNestedConditions {
        SqlInitializationModeCondition() {
            super(ConfigurationCondition.ConfigurationPhase.PARSE_CONFIGURATION);
        }

        @ConditionalOnProperty(prefix="spring.sql.init", name={"mode"}, havingValue="never")
        static class ModeIsNever {
            ModeIsNever() {
            }
        }
    }
}

