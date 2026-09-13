/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.data.couchbase.CouchbaseClientFactory
 *  org.springframework.data.couchbase.core.CouchbaseOperations
 *  org.springframework.data.couchbase.core.CouchbaseTemplate
 *  org.springframework.data.couchbase.core.convert.CouchbaseConverter
 *  org.springframework.data.couchbase.core.convert.MappingCouchbaseConverter
 *  org.springframework.data.couchbase.repository.config.RepositoryOperationsMapping
 */
package org.springframework.boot.autoconfigure.data.couchbase;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.core.CouchbaseOperations;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.core.convert.CouchbaseConverter;
import org.springframework.data.couchbase.core.convert.MappingCouchbaseConverter;
import org.springframework.data.couchbase.repository.config.RepositoryOperationsMapping;

@Configuration(proxyBeanMethods=false)
@ConditionalOnSingleCandidate(value=CouchbaseClientFactory.class)
class CouchbaseClientFactoryDependentConfiguration {
    CouchbaseClientFactoryDependentConfiguration() {
    }

    @Bean(name={"couchbaseTemplate"})
    @ConditionalOnMissingBean(name={"couchbaseTemplate"})
    CouchbaseTemplate couchbaseTemplate(CouchbaseClientFactory couchbaseClientFactory, MappingCouchbaseConverter mappingCouchbaseConverter) {
        return new CouchbaseTemplate(couchbaseClientFactory, (CouchbaseConverter)mappingCouchbaseConverter);
    }

    @Bean(name={"couchbaseRepositoryOperationsMapping"})
    @ConditionalOnMissingBean(name={"couchbaseRepositoryOperationsMapping"})
    RepositoryOperationsMapping couchbaseRepositoryOperationsMapping(CouchbaseTemplate couchbaseTemplate) {
        return new RepositoryOperationsMapping((CouchbaseOperations)couchbaseTemplate);
    }
}

