/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.r2dbc.spi.ConnectionFactory
 *  org.springframework.context.annotation.Import
 *  org.springframework.data.r2dbc.repository.R2dbcRepository
 *  org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactoryBean
 *  org.springframework.r2dbc.core.DatabaseClient
 */
package org.springframework.boot.autoconfigure.data.r2dbc;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.r2dbc.R2dbcDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.r2dbc.R2dbcRepositoriesAutoConfigureRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactoryBean;
import org.springframework.r2dbc.core.DatabaseClient;

@AutoConfiguration(after={R2dbcDataAutoConfiguration.class})
@ConditionalOnClass(value={ConnectionFactory.class, R2dbcRepository.class})
@ConditionalOnBean(value={DatabaseClient.class})
@ConditionalOnProperty(prefix="spring.data.r2dbc.repositories", name={"enabled"}, havingValue="true", matchIfMissing=true)
@ConditionalOnMissingBean(value={R2dbcRepositoryFactoryBean.class})
@Import(value={R2dbcRepositoriesAutoConfigureRegistrar.class})
public class R2dbcRepositoriesAutoConfiguration {
}

