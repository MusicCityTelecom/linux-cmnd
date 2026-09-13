/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.lambda;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

final class SameExecutorCompletionStage<T>
implements CompletionStage<T> {
    private final CompletionStage<T> delegate;
    private final Executor defaultExecutor;

    static final <T> SameExecutorCompletionStage<T> of(CompletionStage<T> delegate, Executor defaultExecutor) {
        return new SameExecutorCompletionStage<T>(delegate, defaultExecutor);
    }

    SameExecutorCompletionStage(CompletionStage<T> delegate, Executor defaultExecutor) {
        this.delegate = delegate;
        this.defaultExecutor = defaultExecutor;
    }

    @Override
    public final <U> CompletionStage<U> thenApply(Function<? super T, ? extends U> fn) {
        return SameExecutorCompletionStage.of(this.delegate.thenApply(fn), this.defaultExecutor);
    }

    @Override
    public final <U> CompletionStage<U> thenApplyAsync(Function<? super T, ? extends U> fn) {
        if (this.defaultExecutor == null) {
            return SameExecutorCompletionStage.of(this.delegate.thenApplyAsync(fn), null);
        }
        return SameExecutorCompletionStage.of(this.delegate.thenApplyAsync(fn, this.defaultExecutor), this.defaultExecutor);
    }

    @Override
    public final <U> CompletionStage<U> thenApplyAsync(Function<? super T, ? extends U> fn, Executor executor) {
        return SameExecutorCompletionStage.of(this.delegate.thenApplyAsync(fn, executor), executor);
    }

    @Override
    public final CompletionStage<Void> thenAccept(Consumer<? super T> action) {
        return SameExecutorCompletionStage.of(this.delegate.thenAccept(action), this.defaultExecutor);
    }

    @Override
    public final CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action) {
        if (this.defaultExecutor == null) {
            return SameExecutorCompletionStage.of(this.delegate.thenAcceptAsync(action), null);
        }
        return SameExecutorCompletionStage.of(this.delegate.thenAcceptAsync(action, this.defaultExecutor), this.defaultExecutor);
    }

    @Override
    public final CompletionStage<Void> thenAcceptAsync(Consumer<? super T> action, Executor executor) {
        return SameExecutorCompletionStage.of(this.delegate.thenAcceptAsync(action, executor), executor);
    }

    @Override
    public final CompletionStage<Void> thenRun(Runnable action) {
        return SameExecutorCompletionStage.of(this.delegate.thenRun(action), this.defaultExecutor);
    }

    @Override
    public final CompletionStage<Void> thenRunAsync(Runnable action) {
        if (this.defaultExecutor == null) {
            return SameExecutorCompletionStage.of(this.delegate.thenRunAsync(action), null);
        }
        return SameExecutorCompletionStage.of(this.delegate.thenRunAsync(action, this.defaultExecutor), this.defaultExecutor);
    }

    @Override
    public final CompletionStage<Void> thenRunAsync(Runnable action, Executor executor) {
        return SameExecutorCompletionStage.of(this.delegate.thenRunAsync(action, executor), executor);
    }

    @Override
    public final <U, V> CompletionStage<V> thenCombine(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn) {
        return SameExecutorCompletionStage.of(this.delegate.thenCombine(other, fn), this.defaultExecutor);
    }

    @Override
    public final <U, V> CompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn) {
        if (this.defaultExecutor == null) {
            return SameExecutorCompletionStage.of(this.delegate.thenCombineAsync(other, fn), null);
        }
        return SameExecutorCompletionStage.of(this.delegate.thenCombineAsync(other, fn, this.defaultExecutor), this.defaultExecutor);
    }

    @Override
    public final <U, V> CompletionStage<V> thenCombineAsync(CompletionStage<? extends U> other, BiFunction<? super T, ? super U, ? extends V> fn, Executor executor) {
        return SameExecutorCompletionStage.of(this.delegate.thenCombineAsync(other, fn, executor), executor);
    }

    @Override
    public final <U> CompletionStage<Void> thenAcceptBoth(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action) {
        return SameExecutorCompletionStage.of(this.delegate.thenAcceptBoth(other, action), this.defaultExecutor);
    }

    @Override
    public final <U> CompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action) {
        if (this.defaultExecutor == null) {
            return SameExecutorCompletionStage.of(this.delegate.thenAcceptBothAsync(other, action), null);
        }
        return SameExecutorCompletionStage.of(this.delegate.thenAcceptBothAsync(other, action, this.defaultExecutor), this.defaultExecutor);
    }

    @Override
    public final <U> CompletionStage<Void> thenAcceptBothAsync(CompletionStage<? extends U> other, BiConsumer<? super T, ? super U> action, Executor executor) {
        return SameExecutorCompletionStage.of(this.delegate.thenAcceptBothAsync(other, action, executor), executor);
    }

    @Override
    public final CompletionStage<Void> runAfterBoth(CompletionStage<?> other, Runnable action) {
        return SameExecutorCompletionStage.of(this.delegate.runAfterBoth(other, action), this.defaultExecutor);
    }

    @Override
    public final CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action) {
        if (this.defaultExecutor == null) {
            return SameExecutorCompletionStage.of(this.delegate.runAfterBothAsync(other, action), null);
        }
        return SameExecutorCompletionStage.of(this.delegate.runAfterBothAsync(other, action, this.defaultExecutor), this.defaultExecutor);
    }

    @Override
    public final CompletionStage<Void> runAfterBothAsync(CompletionStage<?> other, Runnable action, Executor executor) {
        return SameExecutorCompletionStage.of(this.delegate.runAfterBothAsync(other, action, executor), executor);
    }

    @Override
    public final <U> CompletionStage<U> applyToEither(CompletionStage<? extends T> other, Function<? super T, U> fn) {
        return SameExecutorCompletionStage.of(this.delegate.applyToEither(other, fn), this.defaultExecutor);
    }

    @Override
    public final <U> CompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn) {
        if (this.defaultExecutor == null) {
            return SameExecutorCompletionStage.of(this.delegate.applyToEitherAsync(other, fn), null);
        }
        return SameExecutorCompletionStage.of(this.delegate.applyToEitherAsync(other, fn, this.defaultExecutor), this.defaultExecutor);
    }

    @Override
    public final <U> CompletionStage<U> applyToEitherAsync(CompletionStage<? extends T> other, Function<? super T, U> fn, Executor executor) {
        return SameExecutorCompletionStage.of(this.delegate.applyToEitherAsync(other, fn, executor), executor);
    }

    @Override
    public final CompletionStage<Void> acceptEither(CompletionStage<? extends T> other, Consumer<? super T> action) {
        return SameExecutorCompletionStage.of(this.delegate.acceptEither(other, action), this.defaultExecutor);
    }

    @Override
    public final CompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action) {
        if (this.defaultExecutor == null) {
            return SameExecutorCompletionStage.of(this.delegate.acceptEitherAsync(other, action), null);
        }
        return SameExecutorCompletionStage.of(this.delegate.acceptEitherAsync(other, action, this.defaultExecutor), this.defaultExecutor);
    }

    @Override
    public final CompletionStage<Void> acceptEitherAsync(CompletionStage<? extends T> other, Consumer<? super T> action, Executor executor) {
        return SameExecutorCompletionStage.of(this.delegate.acceptEitherAsync(other, action, executor), executor);
    }

    @Override
    public final CompletionStage<Void> runAfterEither(CompletionStage<?> other, Runnable action) {
        return SameExecutorCompletionStage.of(this.delegate.runAfterEither(other, action), this.defaultExecutor);
    }

    @Override
    public final CompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action) {
        if (this.defaultExecutor == null) {
            return SameExecutorCompletionStage.of(this.delegate.runAfterEitherAsync(other, action), null);
        }
        return SameExecutorCompletionStage.of(this.delegate.runAfterEitherAsync(other, action, this.defaultExecutor), this.defaultExecutor);
    }

    @Override
    public final CompletionStage<Void> runAfterEitherAsync(CompletionStage<?> other, Runnable action, Executor executor) {
        return SameExecutorCompletionStage.of(this.delegate.runAfterEitherAsync(other, action, executor), executor);
    }

    @Override
    public final <U> CompletionStage<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> fn) {
        return SameExecutorCompletionStage.of(this.delegate.thenCompose(fn), this.defaultExecutor);
    }

    @Override
    public final <U> CompletionStage<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn) {
        if (this.defaultExecutor == null) {
            return SameExecutorCompletionStage.of(this.delegate.thenComposeAsync(fn), null);
        }
        return SameExecutorCompletionStage.of(this.delegate.thenComposeAsync(fn, this.defaultExecutor), this.defaultExecutor);
    }

    @Override
    public final <U> CompletionStage<U> thenComposeAsync(Function<? super T, ? extends CompletionStage<U>> fn, Executor executor) {
        return SameExecutorCompletionStage.of(this.delegate.thenComposeAsync(fn, executor), executor);
    }

    @Override
    public final CompletionStage<T> exceptionally(Function<Throwable, ? extends T> fn) {
        return SameExecutorCompletionStage.of(this.delegate.exceptionally(fn), this.defaultExecutor);
    }

    @Override
    public final CompletionStage<T> whenComplete(BiConsumer<? super T, ? super Throwable> action) {
        return SameExecutorCompletionStage.of(this.delegate.whenComplete(action), this.defaultExecutor);
    }

    @Override
    public final CompletionStage<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action) {
        if (this.defaultExecutor == null) {
            return SameExecutorCompletionStage.of(this.delegate.whenCompleteAsync(action), null);
        }
        return SameExecutorCompletionStage.of(this.delegate.whenCompleteAsync(action, this.defaultExecutor), this.defaultExecutor);
    }

    @Override
    public final CompletionStage<T> whenCompleteAsync(BiConsumer<? super T, ? super Throwable> action, Executor executor) {
        return SameExecutorCompletionStage.of(this.delegate.whenCompleteAsync(action, executor), executor);
    }

    @Override
    public final <U> CompletionStage<U> handle(BiFunction<? super T, Throwable, ? extends U> fn) {
        return SameExecutorCompletionStage.of(this.delegate.handle(fn), this.defaultExecutor);
    }

    @Override
    public final <U> CompletionStage<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn) {
        if (this.defaultExecutor == null) {
            return SameExecutorCompletionStage.of(this.delegate.handleAsync(fn), null);
        }
        return SameExecutorCompletionStage.of(this.delegate.handleAsync(fn, this.defaultExecutor), this.defaultExecutor);
    }

    @Override
    public final <U> CompletionStage<U> handleAsync(BiFunction<? super T, Throwable, ? extends U> fn, Executor executor) {
        return SameExecutorCompletionStage.of(this.delegate.handleAsync(fn, executor), executor);
    }

    @Override
    public final CompletableFuture<T> toCompletableFuture() {
        return this.delegate.toCompletableFuture();
    }
}

