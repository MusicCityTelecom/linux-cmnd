/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.couchbase.client.java.Cluster
 *  org.springframework.context.annotation.Import
 *  org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository
 *  reactor.core.publisher.Flux
 */
package org.springframework.boot.autoconfigure.data.couchbase;

import com.couchbase.client.java.Cluster;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.couchbase.CouchbaseReactiveDataConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import reactor.core.publisher.Flux;

@AutoConfiguration(after={CouchbaseDataAutoConfiguration.class})
@ConditionalOnClass(value={Cluster.class, ReactiveCouchbaseRepository.class, Flux.class})
@Import(value={CouchbaseReactiveDataConfiguration.class})
public class CouchbaseReactiveDataAutoConfiguration {
}

