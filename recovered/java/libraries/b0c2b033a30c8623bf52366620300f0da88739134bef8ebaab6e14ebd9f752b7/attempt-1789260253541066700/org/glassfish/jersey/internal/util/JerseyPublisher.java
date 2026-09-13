/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.jsr166.Flow;
import org.glassfish.jersey.internal.jsr166.SubmissionPublisherFactory;
import org.glassfish.jersey.internal.jsr166.SubmittableFlowPublisher;

public class JerseyPublisher<T>
implements Flow.Publisher<T> {
    private static final int DEFAULT_BUFFER_CAPACITY = 256;
    private SubmittableFlowPublisher<T> submissionPublisher = SubmissionPublisherFactory.createSubmissionPublisher();
    private final PublisherStrategy strategy;

    public JerseyPublisher() {
        this(ForkJoinPool.commonPool(), 256, PublisherStrategy.BEST_EFFORT);
    }

    public JerseyPublisher(PublisherStrategy strategy) {
        this(ForkJoinPool.commonPool(), 256, strategy);
    }

    public JerseyPublisher(Executor executor) {
        this(executor, PublisherStrategy.BEST_EFFORT);
    }

    public JerseyPublisher(Executor executor, PublisherStrategy strategy) {
        this.strategy = strategy;
        this.submissionPublisher = SubmissionPublisherFactory.createSubmissionPublisher(executor, 256);
    }

    public JerseyPublisher(int maxBufferCapacity) {
        this(ForkJoinPool.commonPool(), maxBufferCapacity, PublisherStrategy.BEST_EFFORT);
    }

    public JerseyPublisher(Executor executor, int maxBufferCapacity, PublisherStrategy strategy) {
        this.strategy = strategy;
        this.submissionPublisher = SubmissionPublisherFactory.createSubmissionPublisher(executor, maxBufferCapacity);
    }

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        this.submissionPublisher.subscribe(new SubscriberWrapper<T>(subscriber));
    }

    private int submit(T data) {
        return this.submissionPublisher.submit(data);
    }

    public CompletableFuture<Void> consume(Consumer<? super T> consumer) {
        return this.submissionPublisher.consume(consumer);
    }

    private int offer(T item, BiPredicate<Flow.Subscriber<? super T>, ? super T> onDrop) {
        return this.offer(item, 0L, TimeUnit.MILLISECONDS, onDrop);
    }

    private int offer(T item, long timeout, TimeUnit unit, BiPredicate<Flow.Subscriber<? super T>, ? super T> onDrop) {
        BiPredicate<Flow.Subscriber, Object> callback = onDrop == null ? this::onDrop : (subscriber, data) -> {
            onDrop.test(this.getSubscriberWrapper((Flow.Subscriber)subscriber).getWrappedSubscriber(), (T)data);
            return false;
        };
        return this.submissionPublisher.offer(item, timeout, unit, callback);
    }

    private boolean onDrop(Flow.Subscriber<? super T> subscriber, T t) {
        subscriber.onError(new IllegalStateException(LocalizationMessages.SLOW_SUBSCRIBER(t)));
        this.getSubscriberWrapper(subscriber).getSubscription().cancel();
        return false;
    }

    private SubscriberWrapper getSubscriberWrapper(Flow.Subscriber subscriber) {
        if (subscriber instanceof SubscriberWrapper) {
            return (SubscriberWrapper)subscriber;
        }
        throw new IllegalArgumentException(LocalizationMessages.UNKNOWN_SUBSCRIBER());
    }

    public int publish(T item) {
        if (PublisherStrategy.BLOCKING == this.strategy) {
            return this.submit(item);
        }
        return this.submissionPublisher.offer(item, this::onDrop);
    }

    public void close() {
        this.submissionPublisher.close();
    }

    public void closeExceptionally(Throwable error) {
        this.submissionPublisher.closeExceptionally(error);
    }

    public int estimateMaximumLag() {
        return this.submissionPublisher.estimateMaximumLag();
    }

    public long estimateMinimumDemand() {
        return this.submissionPublisher.estimateMinimumDemand();
    }

    public Throwable getClosedException() {
        return this.submissionPublisher.getClosedException();
    }

    public int getMaxBufferCapacity() {
        return this.submissionPublisher.getMaxBufferCapacity();
    }

    public static enum PublisherStrategy {
        BLOCKING,
        BEST_EFFORT;

    }

    public static class SubscriberWrapper<T>
    implements Flow.Subscriber<T> {
        private Flow.Subscriber<? super T> subscriber;
        private Flow.Subscription subscription = null;

        public SubscriberWrapper(Flow.Subscriber<? super T> subscriber) {
            this.subscriber = subscriber;
        }

        @Override
        public void onSubscribe(final Flow.Subscription subscription) {
            this.subscription = subscription;
            this.subscriber.onSubscribe(new Flow.Subscription(){

                @Override
                public void request(long n) {
                    subscription.request(n);
                }

                @Override
                public void cancel() {
                    subscription.cancel();
                }
            });
        }

        @Override
        public void onNext(T item) {
            this.subscriber.onNext(item);
        }

        @Override
        public void onError(Throwable throwable) {
            this.subscriber.onError(throwable);
        }

        @Override
        public void onComplete() {
            this.subscriber.onComplete();
        }

        public Flow.Subscriber<? super T> getWrappedSubscriber() {
            return this.subscriber;
        }

        public Flow.Subscription getSubscription() {
            return this.subscription;
        }
    }
}

