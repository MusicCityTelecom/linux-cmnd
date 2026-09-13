/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.Nullable
 *  reactor.core.publisher.Flux
 *  reactor.core.publisher.Mono
 */
package org.springframework.data.repository.query;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FluentQuery<T> {
    public FluentQuery<T> sortBy(Sort var1);

    public <R> FluentQuery<R> as(Class<R> var1);

    default public FluentQuery<T> project(String ... properties) {
        return this.project(Arrays.asList(properties));
    }

    public FluentQuery<T> project(Collection<String> var1);

    public static interface ReactiveFluentQuery<T>
    extends FluentQuery<T> {
        @Override
        public ReactiveFluentQuery<T> sortBy(Sort var1);

        @Override
        public <R> ReactiveFluentQuery<R> as(Class<R> var1);

        @Override
        default public ReactiveFluentQuery<T> project(String ... properties) {
            return this.project(Arrays.asList(properties));
        }

        @Override
        public ReactiveFluentQuery<T> project(Collection<String> var1);

        public Mono<T> one();

        public Mono<T> first();

        public Flux<T> all();

        public Mono<Page<T>> page(Pageable var1);

        public Mono<Long> count();

        public Mono<Boolean> exists();
    }

    public static interface FetchableFluentQuery<T>
    extends FluentQuery<T> {
        @Override
        public FetchableFluentQuery<T> sortBy(Sort var1);

        @Override
        public <R> FetchableFluentQuery<R> as(Class<R> var1);

        @Override
        default public FetchableFluentQuery<T> project(String ... properties) {
            return this.project(Arrays.asList(properties));
        }

        @Override
        public FetchableFluentQuery<T> project(Collection<String> var1);

        default public Optional<T> one() {
            return Optional.ofNullable(this.oneValue());
        }

        @Nullable
        public T oneValue();

        default public Optional<T> first() {
            return Optional.ofNullable(this.firstValue());
        }

        @Nullable
        public T firstValue();

        public List<T> all();

        public Page<T> page(Pageable var1);

        public Stream<T> stream();

        public long count();

        public boolean exists();
    }
}

