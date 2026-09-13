/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.boot.sql.init.dependency.DatabaseInitializationDependencyConfigurer
 *  org.springframework.context.annotation.Import
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package org.springframework.boot.autoconfigure.jdbc;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcProperties;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateConfiguration;
import org.springframework.boot.autoconfigure.jdbc.NamedParameterJdbcTemplateConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.sql.init.dependency.DatabaseInitializationDependencyConfigurer;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

@AutoConfiguration(after={DataSourceAutoConfiguration.class})
@ConditionalOnClass(value={DataSource.class, JdbcTemplate.class})
@ConditionalOnSingleCandidate(value=DataSource.class)
@EnableConfigurationProperties(value={JdbcProperties.class})
@Import(value={DatabaseInitializationDependencyConfigurer.class, JdbcTemplateConfiguration.class, NamedParameterJdbcTemplateConfiguration.class})
public class JdbcTemplateAutoConfiguration {
}

