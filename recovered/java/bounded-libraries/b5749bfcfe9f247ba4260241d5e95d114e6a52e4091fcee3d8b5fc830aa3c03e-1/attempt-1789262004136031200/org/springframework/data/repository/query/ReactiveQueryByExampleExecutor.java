/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.data.repository.query;

import java.util.function.Function;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveQueryByExampleExecutor<T> {
    public <S extends T> Mono<S> findOne(Example<S> var1);

    public <S extends T> Flux<S> findAll(Example<S> var1);

    public <S extends T> Flux<S> findAll(Example<S> var1, Sort var2);

    public <S extends T> Mono<Long> count(Example<S> var1);

    public <S extends T> Mono<Boolean> exists(Example<S> var1);

    public <S extends T, R, P extends Publisher<R>> P findBy(Example<S> var1, Function<FluentQuery.ReactiveFluentQuery<S>, P> var2);
}

