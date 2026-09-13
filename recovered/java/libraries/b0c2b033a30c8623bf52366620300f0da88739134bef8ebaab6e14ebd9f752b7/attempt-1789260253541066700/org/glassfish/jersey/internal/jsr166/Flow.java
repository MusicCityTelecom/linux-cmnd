/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.jsr166;

public final class Flow {
    static final int DEFAULT_BUFFER_SIZE = 256;

    private Flow() {
    }

    public static int defaultBufferSize() {
        return 256;
    }

    public static interface Processor<T, R>
    extends Subscriber<T>,
    Publisher<R> {
    }

    public static interface Subscription {
        public void request(long var1);

        public void cancel();
    }

    public static interface Subscriber<T> {
        public void onSubscribe(Subscription var1);

        public void onNext(T var1);

        public void onError(Throwable var1);

        public void onComplete();
    }

    @FunctionalInterface
    public static interface Publisher<T> {
        public void subscribe(Subscriber<? super T> var1);
    }
}

