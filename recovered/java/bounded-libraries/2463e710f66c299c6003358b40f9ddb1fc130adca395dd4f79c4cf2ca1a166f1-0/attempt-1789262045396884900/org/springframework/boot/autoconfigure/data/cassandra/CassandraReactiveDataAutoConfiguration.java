/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.datastax.oss.driver.api.core.CqlSession
 *  org.springframework.context.annotation.Bean
 *  org.springframework.data.cassandra.ReactiveSession
 *  org.springframework.data.cassandra.ReactiveSessionFactory
 *  org.springframework.data.cassandra.core.ReactiveCassandraOperations
 *  org.springframework.data.cassandra.core.ReactiveCassandraTemplate
 *  org.springframework.data.cassandra.core.convert.CassandraConverter
 *  org.springframework.data.cassandra.core.cql.session.DefaultBridgedReactiveSession
 *  org.springframework.data.cassandra.core.cql.session.DefaultReactiveSessionFactory
 *  reactor.core.publisher.Flux
 */
package org.springframework.boot.autoconfigure.data.cassandra;

import com.datastax.oss.driver.api.core.CqlSession;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.ReactiveSession;
import org.springframework.data.cassandra.ReactiveSessionFactory;
import org.springframework.data.cassandra.core.ReactiveCassandraOperations;
import org.springframework.data.cassandra.core.ReactiveCassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.cql.session.DefaultBridgedReactiveSession;
import org.springframework.data.cassandra.core.cql.session.DefaultReactiveSessionFactory;
import reactor.core.publisher.Flux;

@AutoConfiguration(after={CassandraDataAutoConfiguration.class})
@ConditionalOnClass(value={CqlSession.class, ReactiveCassandraTemplate.class, Flux.class})
@ConditionalOnBean(value={CqlSession.class})
public class CassandraReactiveDataAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ReactiveSession reactiveCassandraSession(CqlSession session) {
        return new DefaultBridgedReactiveSession(session);
    }

    @Bean
    @ConditionalOnMissingBean
    public ReactiveSessionFactory reactiveCassandraSessionFactory(ReactiveSession reactiveCassandraSession) {
        return new DefaultReactiveSessionFactory(reactiveCassandraSession);
    }

    @Bean
    @ConditionalOnMissingBean(value={ReactiveCassandraOperations.class})
    public ReactiveCassandraTemplate reactiveCassandraTemplate(ReactiveSession reactiveCassandraSession, CassandraConverter converter) {
        return new ReactiveCassandraTemplate(reactiveCassandraSession, converter);
    }
}

