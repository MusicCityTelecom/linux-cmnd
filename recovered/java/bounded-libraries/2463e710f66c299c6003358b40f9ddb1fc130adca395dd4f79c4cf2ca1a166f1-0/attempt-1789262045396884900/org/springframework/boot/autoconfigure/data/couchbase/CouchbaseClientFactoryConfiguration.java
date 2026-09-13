/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.couchbase.client.java.Cluster
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.data.couchbase.CouchbaseClientFactory
 *  org.springframework.data.couchbase.SimpleCouchbaseClientFactory
 */
package org.springframework.boot.autoconfigure.data.couchbase;

import com.couchbase.client.java.Cluster;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.CouchbaseClientFactory;
import org.springframework.data.couchbase.SimpleCouchbaseClientFactory;

@Configuration(proxyBeanMethods=false)
@ConditionalOnSingleCandidate(value=Cluster.class)
@ConditionalOnProperty(value={"spring.data.couchbase.bucket-name"})
class CouchbaseClientFactoryConfiguration {
    CouchbaseClientFactoryConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean
    CouchbaseClientFactory couchbaseClientFactory(Cluster cluster, CouchbaseDataProperties properties) {
        return new SimpleCouchbaseClientFactory(cluster, properties.getBucketName(), properties.getScopeName());
    }
}

