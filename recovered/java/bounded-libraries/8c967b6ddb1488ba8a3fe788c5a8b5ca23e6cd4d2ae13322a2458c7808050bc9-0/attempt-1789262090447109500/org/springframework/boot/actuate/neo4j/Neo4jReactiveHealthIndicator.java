/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.neo4j.driver.Driver
 *  org.neo4j.driver.Record
 *  org.neo4j.driver.exceptions.SessionExpiredException
 *  org.neo4j.driver.reactive.RxResult
 *  org.neo4j.driver.reactive.RxSession
 *  org.neo4j.driver.summary.ResultSummary
 *  org.reactivestreams.Publisher
 *  reactor.core.publisher.Mono
 *  reactor.util.retry.Retry
 */
package org.springframework.boot.actuate.neo4j;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.exceptions.SessionExpiredException;
import org.neo4j.driver.reactive.RxResult;
import org.neo4j.driver.reactive.RxSession;
import org.neo4j.driver.summary.ResultSummary;
import org.reactivestreams.Publisher;
import org.springframework.boot.actuate.health.AbstractReactiveHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.neo4j.Neo4jHealthDetails;
import org.springframework.boot.actuate.neo4j.Neo4jHealthDetailsHandler;
import org.springframework.boot.actuate.neo4j.Neo4jHealthIndicator;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

public final class Neo4jReactiveHealthIndicator
extends AbstractReactiveHealthIndicator {
    private static final Log logger = LogFactory.getLog(Neo4jReactiveHealthIndicator.class);
    private final Driver driver;
    private final Neo4jHealthDetailsHandler healthDetailsHandler;

    public Neo4jReactiveHealthIndicator(Driver driver) {
        this.driver = driver;
        this.healthDetailsHandler = new Neo4jHealthDetailsHandler();
    }

    @Override
    protected Mono<Health> doHealthCheck(Health.Builder builder) {
        return this.runHealthCheckQuery().doOnError(SessionExpiredException.class, e -> logger.warn((Object)"Neo4j session has expired, retrying one single time to retrieve server health.")).retryWhen((Retry)Retry.max((long)1L).filter(SessionExpiredException.class::isInstance)).map(healthDetails -> {
            this.healthDetailsHandler.addHealthDetails(builder, (Neo4jHealthDetails)healthDetails);
            return builder.build();
        });
    }

    Mono<Neo4jHealthDetails> runHealthCheckQuery() {
        return Mono.using(() -> this.driver.rxSession(Neo4jHealthIndicator.DEFAULT_SESSION_CONFIG), session -> {
            RxResult result = session.run("CALL dbms.components() YIELD versions, name, edition WHERE name = 'Neo4j Kernel' RETURN edition, versions[0] as version");
            return Mono.from((Publisher)result.records()).zipWhen(record -> Mono.from((Publisher)result.consume())).map(tuple -> new Neo4jHealthDetails((Record)tuple.getT1(), (ResultSummary)tuple.getT2()));
        }, RxSession::close);
    }
}

