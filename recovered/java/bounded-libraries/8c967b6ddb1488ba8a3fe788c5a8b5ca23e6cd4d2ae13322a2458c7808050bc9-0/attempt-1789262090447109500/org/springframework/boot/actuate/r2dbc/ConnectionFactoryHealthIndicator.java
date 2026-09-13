/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.r2dbc.spi.ColumnMetadata
 *  io.r2dbc.spi.Connection
 *  io.r2dbc.spi.ConnectionFactory
 *  io.r2dbc.spi.Row
 *  io.r2dbc.spi.RowMetadata
 *  io.r2dbc.spi.ValidationDepth
 *  org.reactivestreams.Publisher
 *  org.springframework.util.Assert
 *  org.springframework.util.StringUtils
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.boot.actuate.r2dbc;

import io.r2dbc.spi.ColumnMetadata;
import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import io.r2dbc.spi.ValidationDepth;
import org.reactivestreams.Publisher;
import org.springframework.boot.actuate.health.AbstractReactiveHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ConnectionFactoryHealthIndicator
extends AbstractReactiveHealthIndicator {
    private final ConnectionFactory connectionFactory;
    private final String validationQuery;

    public ConnectionFactoryHealthIndicator(ConnectionFactory connectionFactory) {
        this(connectionFactory, null);
    }

    public ConnectionFactoryHealthIndicator(ConnectionFactory connectionFactory, String validationQuery) {
        Assert.notNull((Object)connectionFactory, (String)"ConnectionFactory must not be null");
        this.connectionFactory = connectionFactory;
        this.validationQuery = validationQuery;
    }

    @Override
    protected final Mono<Health> doHealthCheck(Health.Builder builder) {
        return this.validate(builder).defaultIfEmpty((Object)builder.build()).onErrorResume(Exception.class, ex -> Mono.just((Object)builder.down((Throwable)ex).build()));
    }

    private Mono<Health> validate(Health.Builder builder) {
        builder.withDetail("database", this.connectionFactory.getMetadata().getName());
        return StringUtils.hasText((String)this.validationQuery) ? this.validateWithQuery(builder) : this.validateWithConnectionValidation(builder);
    }

    private Mono<Health> validateWithQuery(Health.Builder builder) {
        builder.withDetail("validationQuery", this.validationQuery);
        Mono connectionValidation = Mono.usingWhen((Publisher)this.connectionFactory.create(), conn -> Flux.from((Publisher)conn.createStatement(this.validationQuery).execute()).flatMap(it -> it.map(this::extractResult)).next(), Connection::close, (o, throwable) -> o.close(), Connection::close);
        return connectionValidation.map(result -> builder.up().withDetail("result", result).build());
    }

    private Mono<Health> validateWithConnectionValidation(Health.Builder builder) {
        builder.withDetail("validationQuery", "validate(REMOTE)");
        Mono connectionValidation = Mono.usingWhen((Publisher)this.connectionFactory.create(), connection -> Mono.from((Publisher)connection.validate(ValidationDepth.REMOTE)), Connection::close, (connection, ex) -> connection.close(), Connection::close);
        return connectionValidation.map(valid -> builder.status(valid != false ? Status.UP : Status.DOWN).build());
    }

    private Object extractResult(Row row, RowMetadata metadata) {
        return row.get(((ColumnMetadata)metadata.getColumnMetadatas().iterator().next()).getName());
    }
}

