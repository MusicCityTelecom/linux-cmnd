/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.querydsl.core.types.OrderSpecifier
 *  com.querydsl.core.types.Predicate
 *  org.reactivestreams.Publisher
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.data.querydsl;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveQuerydslPredicateExecutor<T> {
    public Mono<T> findOne(Predicate var1);

    public Flux<T> findAll(Predicate var1);

    public Flux<T> findAll(Predicate var1, Sort var2);

    public Flux<T> findAll(Predicate var1, OrderSpecifier<?> ... var2);

    public Flux<T> findAll(OrderSpecifier<?> ... var1);

    public Mono<Long> count(Predicate var1);

    public Mono<Boolean> exists(Predicate var1);

    public <S extends T, R, P extends Publisher<R>> P findBy(Predicate var1, Function<FluentQuery.ReactiveFluentQuery<S>, P> var2);
}

