/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.couchbase.client.java.Bucket
 *  org.springframework.context.annotation.Import
 *  org.springframework.data.couchbase.repository.CouchbaseRepository
 *  org.springframework.data.couchbase.repository.config.RepositoryOperationsMapping
 *  org.springframework.data.couchbase.repository.support.CouchbaseRepositoryFactoryBean
 */
package org.springframework.boot.autoconfigure.data.couchbase;

import com.couchbase.client.java.Bucket;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.boot.autoconfigure.data.RepositoryType;
import org.springframework.boot.autoconfigure.data.couchbase.CouchbaseRepositoriesRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.data.couchbase.repository.config.RepositoryOperationsMapping;
import org.springframework.data.couchbase.repository.support.CouchbaseRepositoryFactoryBean;

@AutoConfiguration
@ConditionalOnClass(value={Bucket.class, CouchbaseRepository.class})
@ConditionalOnBean(value={RepositoryOperationsMapping.class})
@ConditionalOnRepositoryType(store="couchbase", type=RepositoryType.IMPERATIVE)
@ConditionalOnMissingBean(value={CouchbaseRepositoryFactoryBean.class})
@Import(value={CouchbaseRepositoriesRegistrar.class})
public class CouchbaseRepositoriesAutoConfiguration {
}

