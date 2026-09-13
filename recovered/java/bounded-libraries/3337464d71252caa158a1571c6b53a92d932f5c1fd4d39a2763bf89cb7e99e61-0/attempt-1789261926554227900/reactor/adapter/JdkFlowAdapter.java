/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 *  org.reactivestreams.Subscriber
 *  org.reactivestreams.Subscription
 */
package reactor.adapter;

import java.util.concurrent.Flow;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;

public abstract class JdkFlowAdapter {
    public static <T> Flow.Publisher<T> publisherToFlowPublisher(Publisher<T> publisher) {
        return new PublisherAsFlowPublisher(publisher);
    }

    public static <T> Flux<T> flowPublisherToFlux(Flow.Publisher<T> publisher) {
        return new FlowPublisherAsFlux(publisher);
    }

    JdkFlowAdapter() {
    }

    private static class SubscriberToRS<T>
    implements Flow.Subscriber<T>,
    Subscription {
        private final Subscriber<? super T> s;
        Flow.Subscription subscription;

        public SubscriberToRS(Subscriber<? super T> s) {
            this.s = s;
        }

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            this.s.onSubscribe((Subscription)this);
        }

        @Override
        public void onNext(T o) {
            this.s.onNext(o);
        }

        @Override
        public void onError(Throwable throwable) {
            this.s.onError(throwable);
        }

        @Override
        public void onComplete() {
            this.s.onComplete();
        }

        public void request(long n) {
            this.subscription.request(n);
        }

        public void cancel() {
            this.subscription.cancel();
        }
    }

    private static class FlowSubscriber<T>
    implements CoreSubscriber<T>,
    Flow.Subscription {
        private final Flow.Subscriber<? super T> subscriber;
        Subscription subscription;

        public FlowSubscriber(Flow.Subscriber<? super T> subscriber) {
            this.subscriber = subscriber;
        }

        @Override
        public void onSubscribe(Subscription s) {
            this.subscription = s;
            this.subscriber.onSubscribe(this);
        }

        public void onNext(T o) {
            this.subscriber.onNext(o);
        }

        public void onError(Throwable t) {
            this.subscriber.onError(t);
        }

        public void onComplete() {
            this.subscriber.onComplete();
        }

        @Override
        public void request(long n) {
            this.subscription.request(n);
        }

        @Override
        public void cancel() {
            this.subscription.cancel();
        }
    }

    private static class PublisherAsFlowPublisher<T>
    implements Flow.Publisher<T> {
        private final Publisher<T> pub;

        private PublisherAsFlowPublisher(Publisher<T> pub) {
            this.pub = pub;
        }

        @Override
        public void subscribe(Flow.Subscriber<? super T> subscriber) {
            this.pub.subscribe(new FlowSubscriber<T>(subscriber));
        }
    }

    private static class FlowPublisherAsFlux<T>
    extends Flux<T>
    implements Scannable {
        private final Flow.Publisher<T> pub;

        private FlowPublisherAsFlux(Flow.Publisher<T> pub) {
            this.pub = pub;
        }

        @Override
        public void subscribe(CoreSubscriber<? super T> actual) {
            this.pub.subscribe(new SubscriberToRS<T>(actual));
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            return null;
        }
    }
}

