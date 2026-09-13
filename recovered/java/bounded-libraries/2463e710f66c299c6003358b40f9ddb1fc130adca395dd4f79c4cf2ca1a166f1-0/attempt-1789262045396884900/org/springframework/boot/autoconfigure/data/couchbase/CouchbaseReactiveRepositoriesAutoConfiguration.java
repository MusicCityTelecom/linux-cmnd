/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.couchbase.client.java.Cluster
 *  org.springframework.context.annotation.Import
 *  org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository
 *  org.springframework.data.couchbase.repository.config.ReactiveRepositoryOperationsMapping
 *  org.springframework.data.couchbase.repository.support.ReactiveCouchbaseRepositoryFactoryBean
 *  reactor.core.publisher.Flux
 */
package org.springframework.boot.autoconfigure.data.couchbase;

import com.couchbase.client.java.Cluster;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.boot.autoconfigure.data.RepositoryType;
import org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveRepositoriesRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import org.springframework.data.couchbase.repository.config.ReactiveRepositoryOperationsMapping;
import org.springframework.data.couchbase.repository.support.ReactiveCouchbaseRepositoryFactoryBean;
import reactor.core.publisher.Flux;

@AutoConfiguration(after={CouchbaseReactiveDataAutoConfiguration.class})
@ConditionalOnClass(value={Cluster.class, ReactiveCouchbaseRepository.class, Flux.class})
@ConditionalOnRepositoryType(store="couchbase", type=RepositoryType.REACTIVE)
@ConditionalOnBean(value={ReactiveRepositoryOperationsMapping.class})
@ConditionalOnMissingBean(value={ReactiveCouchbaseRepositoryFactoryBean.class})
@Import(value={CouchbaseReactiveRepositoriesRegistrar.class})
public class CouchbaseReactiveRepositoriesAutoConfiguration {
}

