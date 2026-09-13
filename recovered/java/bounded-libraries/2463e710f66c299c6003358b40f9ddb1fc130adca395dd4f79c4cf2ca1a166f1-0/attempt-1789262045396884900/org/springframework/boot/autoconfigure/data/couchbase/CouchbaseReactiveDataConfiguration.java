/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.data.couchbase.CouchbaseClientFactory
 *  org.springframework.data.couchbase.core.ReactiveCouchbaseOperations
 *  org.springframework.data.couchbase.core.ReactiveCouchbaseTemplate
 *  org.springframework.data.couchbase.core.convert.CouchbaseConverter
 *  org.springframework.data.couchbase.core.convert.MappingCouchbaseConverter
 *  org.springframework.data.couchbase.repository.config.ReactiveRepositoryOperationsMapping
 */
package org.springframework.boot.autoconfigure.data.couchbase;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.core.ReactiveCouchbaseOperations;
import org.springframework.data.couchbase.core.ReactiveCouchbaseTemplate;
import org.springframework.data.couchbase.core.convert.CouchbaseConverter;
import org.springframework.data.couchbase.core.convert.MappingCouchbaseConverter;
import org.springframework.data.couchbase.repository.config.ReactiveRepositoryOperationsMapping;

@Configuration(proxyBeanMethods=false)
@ConditionalOnSingleCandidate(value=CouchbaseClientFactory.class)
class CouchbaseReactiveDataConfiguration {
    CouchbaseReactiveDataConfiguration() {
    }

    @Bean(name={"reactiveCouchbaseTemplate"})
    @ConditionalOnMissingBean(name={"reactiveCouchbaseTemplate"})
    ReactiveCouchbaseTemplate reactiveCouchbaseTemplate(CouchbaseClientFactory couchbaseClientFactory, MappingCouchbaseConverter mappingCouchbaseConverter) {
        return new ReactiveCouchbaseTemplate(couchbaseClientFactory, (CouchbaseConverter)mappingCouchbaseConverter);
    }

    @Bean(name={"reactiveCouchbaseRepositoryOperationsMapping"})
    @ConditionalOnMissingBean(name={"reactiveCouchbaseRepositoryOperationsMapping"})
    ReactiveRepositoryOperationsMapping reactiveCouchbaseRepositoryOperationsMapping(ReactiveCouchbaseTemplate reactiveCouchbaseTemplate) {
        return new ReactiveRepositoryOperationsMapping((ReactiveCouchbaseOperations)reactiveCouchbaseTemplate);
    }
}

