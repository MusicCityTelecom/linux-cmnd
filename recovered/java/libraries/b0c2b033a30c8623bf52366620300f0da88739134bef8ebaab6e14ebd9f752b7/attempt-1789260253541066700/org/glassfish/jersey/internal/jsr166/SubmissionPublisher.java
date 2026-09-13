/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.jsr166;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Flow;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import org.glassfish.jersey.internal.jsr166.Flow;
import org.glassfish.jersey.internal.jsr166.SubmittableFlowPublisher;

public class SubmissionPublisher<T>
implements SubmittableFlowPublisher<T> {
    private final java.util.concurrent.SubmissionPublisher<T> publisher;

    public SubmissionPublisher(Executor executor, int n, BiConsumer<? super Flow.Subscriber<? super T>, ? super Throwable> biConsumer) {
        this.publisher = new java.util.concurrent.SubmissionPublisher<T>(executor, n, SubmissionPublisher.convertConsumer(biConsumer));
    }

    public SubmissionPublisher(Executor executor, int n) {
        this.publisher = new java.util.concurrent.SubmissionPublisher(executor, n);
    }

    public SubmissionPublisher() {
        this.publisher = new java.util.concurrent.SubmissionPublisher();
    }

    @Override
    public CompletableFuture<Void> consume(Consumer<? super T> consumer) {
        return this.publisher.consume(consumer);
    }

    @Override
    public void close() {
        this.publisher.close();
    }

    @Override
    public void closeExceptionally(Throwable throwable) {
        this.publisher.closeExceptionally(throwable);
    }

    @Override
    public long estimateMinimumDemand() {
        return this.publisher.estimateMinimumDemand();
    }

    @Override
    public int estimateMaximumLag() {
        return this.publisher.estimateMaximumLag();
    }

    @Override
    public Throwable getClosedException() {
        return this.publisher.getClosedException();
    }

    @Override
    public int getMaxBufferCapacity() {
        return this.publisher.getMaxBufferCapacity();
    }

    @Override
    public int offer(T t, long l, TimeUnit timeUnit, BiPredicate<Flow.Subscriber<? super T>, ? super T> biPredicate) {
        return this.publisher.offer((T)t, l, timeUnit, SubmissionPublisher.convertPredicate(biPredicate));
    }

    @Override
    public int offer(T t, BiPredicate<Flow.Subscriber<? super T>, ? super T> biPredicate) {
        return this.publisher.offer((T)t, SubmissionPublisher.convertPredicate(biPredicate));
    }

    @Override
    public int submit(T t) {
        return this.publisher.submit(t);
    }

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        this.publisher.subscribe(SubmissionPublisher.convertSubscriber(subscriber));
    }

    private static <T> BiConsumer<? super Flow.Subscriber<? super T>, ? super Throwable> convertConsumer(final BiConsumer<? super Flow.Subscriber<? super T>, ? super Throwable> biConsumer) {
        return new BiConsumer<Flow.Subscriber<? super T>, Throwable>(){

            @Override
            public void accept(Flow.Subscriber<? super T> subscriber, Throwable throwable) {
                biConsumer.accept(SubmissionPublisher.convertSubscriber(subscriber), throwable);
            }
        };
    }

    private static <T> BiPredicate<Flow.Subscriber<? super T>, ? super T> convertPredicate(final BiPredicate<Flow.Subscriber<? super T>, ? super T> biPredicate) {
        return new BiPredicate<Flow.Subscriber<? super T>, T>(){

            @Override
            public boolean test(Flow.Subscriber<? super T> subscriber, T t) {
                return biPredicate.test(SubmissionPublisher.convertSubscriber(subscriber), t);
            }
        };
    }

    private static <T> Flow.Subscriber<? super T> convertSubscriber(final Flow.Subscriber<? super T> subscriber) {
        return new Flow.Subscriber<T>(){

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                subscriber.onSubscribe(SubmissionPublisher.convertSubscription(subscription));
            }

            @Override
            public void onNext(T t) {
                subscriber.onNext(t);
            }

            @Override
            public void onError(Throwable throwable) {
                subscriber.onError(throwable);
            }

            @Override
            public void onComplete() {
                subscriber.onComplete();
            }
        };
    }

    private static <T> Flow.Subscriber<T> convertSubscriber(final Flow.Subscriber<T> subscriber) {
        return new Flow.Subscriber<T>(){

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                subscriber.onSubscribe(SubmissionPublisher.convertSubscription(subscription));
            }

            @Override
            public void onNext(T t) {
                subscriber.onNext(t);
            }

            @Override
            public void onError(Throwable throwable) {
                subscriber.onError(throwable);
            }

            @Override
            public void onComplete() {
                subscriber.onComplete();
            }
        };
    }

    private static Flow.Subscription convertSubscription(final Flow.Subscription subscription) {
        return new Flow.Subscription(){

            @Override
            public void request(long l) {
                subscription.request(l);
            }

            @Override
            public void cancel() {
                subscription.cancel();
            }
        };
    }

    private static Flow.Subscription convertSubscription(final Flow.Subscription subscription) {
        return new Flow.Subscription(){

            @Override
            public void request(long l) {
                subscription.request(l);
            }

            @Override
            public void cancel() {
                subscription.cancel();
            }
        };
    }
}

