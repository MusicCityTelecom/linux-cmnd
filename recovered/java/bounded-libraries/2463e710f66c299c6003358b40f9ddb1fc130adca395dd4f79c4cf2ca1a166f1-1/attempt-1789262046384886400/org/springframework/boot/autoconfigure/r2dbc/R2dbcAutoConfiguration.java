/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.r2dbc.spi.ConnectionFactory
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Import
 */
package org.springframework.boot.autoconfigure.r2dbc;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.ConnectionFactoryConfigurations;
import org.springframework.boot.autoconfigure.r2dbc.ConnectionFactoryDependentConfiguration;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@AutoConfiguration(before={DataSourceAutoConfiguration.class, SqlInitializationAutoConfiguration.class})
@ConditionalOnClass(value={ConnectionFactory.class})
@ConditionalOnResource(resources={"classpath:META-INF/services/io.r2dbc.spi.ConnectionFactoryProvider"})
@EnableConfigurationProperties(value={R2dbcProperties.class})
@Import(value={ConnectionFactoryConfigurations.PoolConfiguration.class, ConnectionFactoryConfigurations.GenericConfiguration.class, ConnectionFactoryDependentConfiguration.class})
public class R2dbcAutoConfiguration {
}

