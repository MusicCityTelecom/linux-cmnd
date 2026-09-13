/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.datastax.oss.driver.api.core.CqlSession
 *  org.springframework.context.annotation.Import
 *  org.springframework.data.cassandra.repository.CassandraRepository
 *  org.springframework.data.cassandra.repository.support.CassandraRepositoryFactoryBean
 */
package org.springframework.boot.autoconfigure.data.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.boot.autoconfigure.data.RepositoryType;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.support.CassandraRepositoryFactoryBean;

@AutoConfiguration
@ConditionalOnClass(value={CqlSession.class, CassandraRepository.class})
@ConditionalOnRepositoryType(store="cassandra", type=RepositoryType.IMPERATIVE)
@ConditionalOnMissingBean(value={CassandraRepositoryFactoryBean.class})
@Import(value={CassandraRepositoriesRegistrar.class})
public class CassandraRepositoriesAutoConfiguration {
}

