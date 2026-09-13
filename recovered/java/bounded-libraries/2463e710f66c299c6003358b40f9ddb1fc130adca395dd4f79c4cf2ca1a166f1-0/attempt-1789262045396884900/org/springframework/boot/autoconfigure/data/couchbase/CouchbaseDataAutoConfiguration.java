/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.couchbase.client.java.Bucket
 *  javax.validation.Validator
 *  org.springframework.boot.context.properties.EnableConfigurationProperties
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.context.annotation.Import
 *  org.springframework.data.couchbase.core.mapping.event.ValidatingCouchbaseEventListener
 *  org.springframework.data.couchbase.repository.CouchbaseRepository
 */
package org.springframework.boot.autoconfigure.data.couchbase;

import com.couchbase.client.java.Bucket;
import javax.validation.Validator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration;
import org.springframework.boot.autoconfigure.data.couchbase.CouchbaseClientFactoryConfiguration;
import org.springframework.boot.autoconfigure.data.couchbase.CouchbaseClientFactoryDependentConfiguration;
import org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataConfiguration;
import org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataProperties;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.couchbase.core.mapping.event.ValidatingCouchbaseEventListener;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

@AutoConfiguration(after={CouchbaseAutoConfiguration.class, ValidationAutoConfiguration.class})
@ConditionalOnClass(value={Bucket.class, CouchbaseRepository.class})
@EnableConfigurationProperties(value={CouchbaseDataProperties.class})
@Import(value={CouchbaseDataConfiguration.class, CouchbaseClientFactoryConfiguration.class, CouchbaseClientFactoryDependentConfiguration.class})
public class CouchbaseDataAutoConfiguration {

    @Configuration(proxyBeanMethods=false)
    @ConditionalOnClass(value={Validator.class})
    public static class ValidationConfiguration {
        @Bean
        @ConditionalOnSingleCandidate(value=Validator.class)
        public ValidatingCouchbaseEventListener validationEventListener(Validator validator) {
            return new ValidatingCouchbaseEventListener(validator);
        }
    }
}

