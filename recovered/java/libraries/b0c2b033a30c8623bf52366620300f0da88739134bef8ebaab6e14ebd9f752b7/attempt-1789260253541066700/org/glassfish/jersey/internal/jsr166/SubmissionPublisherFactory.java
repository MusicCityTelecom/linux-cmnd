/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.jsr166;

import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import org.glassfish.jersey.internal.jsr166.Flow;
import org.glassfish.jersey.internal.jsr166.SubmissionPublisher;
import org.glassfish.jersey.internal.jsr166.SubmittableFlowPublisher;

public class SubmissionPublisherFactory {
    public static <T> SubmittableFlowPublisher<T> createSubmissionPublisher() {
        return new SubmissionPublisher();
    }

    public static <T> SubmittableFlowPublisher<T> createSubmissionPublisher(Executor executor, int n) {
        return new SubmissionPublisher(executor, n);
    }

    public static <T> SubmittableFlowPublisher<T> createSubmissionPublisher(Executor executor, int n, BiConsumer<? super Flow.Subscriber<? super T>, ? super Throwable> biConsumer) {
        return new SubmissionPublisher(executor, n, biConsumer);
    }
}

