/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Import
 *  org.springframework.data.cassandra.ReactiveSession
 *  org.springframework.data.cassandra.repository.ReactiveCassandraRepository
 *  org.springframework.data.cassandra.repository.support.ReactiveCassandraRepositoryFactoryBean
 */
package org.springframework.boot.autoconfigure.data.cassandra;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.boot.autoconfigure.data.RepositoryType;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveRepositoriesRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.data.cassandra.ReactiveSession;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.data.cassandra.repository.support.ReactiveCassandraRepositoryFactoryBean;

@AutoConfiguration(after={CassandraReactiveDataAutoConfiguration.class})
@ConditionalOnClass(value={ReactiveSession.class, ReactiveCassandraRepository.class})
@ConditionalOnRepositoryType(store="cassandra", type=RepositoryType.REACTIVE)
@ConditionalOnMissingBean(value={ReactiveCassandraRepositoryFactoryBean.class})
@Import(value={CassandraReactiveRepositoriesRegistrar.class})
public class CassandraReactiveRepositoriesAutoConfiguration {
}

