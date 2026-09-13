/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.guava;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.jersey.internal.guava.AbstractFuture;
import org.glassfish.jersey.internal.guava.AsyncFunction;
import org.glassfish.jersey.internal.guava.ListenableFuture;
import org.glassfish.jersey.internal.guava.MoreExecutors;
import org.glassfish.jersey.internal.guava.Preconditions;
import org.glassfish.jersey.internal.guava.Uninterruptibles;

public final class Futures {
    private Futures() {
    }

    public static <V> ListenableFuture<V> immediateFuture(V value) {
        return new ImmediateSuccessfulFuture<V>(value);
    }

    public static <V> ListenableFuture<V> immediateFailedFuture(Throwable throwable) {
        Preconditions.checkNotNull(throwable);
        return new ImmediateFailedFuture(throwable);
    }

    public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> input, Function<? super I, ? extends O> function) {
        Preconditions.checkNotNull(function);
        ChainingListenableFuture output = new ChainingListenableFuture(Futures.asAsyncFunction(function), input);
        input.addListener(output, MoreExecutors.directExecutor());
        return output;
    }

    private static <I, O> AsyncFunction<I, O> asAsyncFunction(final Function<? super I, ? extends O> function) {
        return new AsyncFunction<I, O>(){

            @Override
            public ListenableFuture<O> apply(I input) {
                Object output = function.apply(input);
                return Futures.immediateFuture(output);
            }
        };
    }

    private static class ChainingListenableFuture<I, O>
    extends AbstractFuture<O>
    implements Runnable {
        private AsyncFunction<? super I, ? extends O> function;
        private ListenableFuture<? extends I> inputFuture;
        private volatile ListenableFuture<? extends O> outputFuture;

        private ChainingListenableFuture(AsyncFunction<? super I, ? extends O> function, ListenableFuture<? extends I> inputFuture) {
            this.function = Preconditions.checkNotNull(function);
            this.inputFuture = Preconditions.checkNotNull(inputFuture);
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            if (super.cancel(mayInterruptIfRunning)) {
                this.cancel(this.inputFuture, mayInterruptIfRunning);
                this.cancel(this.outputFuture, mayInterruptIfRunning);
                return true;
            }
            return false;
        }

        private void cancel(Future<?> future, boolean mayInterruptIfRunning) {
            if (future != null) {
                future.cancel(mayInterruptIfRunning);
            }
        }

        /*
         * Loose catch block
         */
        @Override
        public void run() {
            block14: {
                try {
                    I sourceResult;
                    try {
                        sourceResult = Uninterruptibles.getUninterruptibly(this.inputFuture);
                    }
                    catch (CancellationException e) {
                        this.cancel(false);
                        this.function = null;
                        this.inputFuture = null;
                        return;
                    }
                    catch (ExecutionException e) {
                        this.setException(e.getCause());
                        this.function = null;
                        this.inputFuture = null;
                        return;
                    }
                    this.outputFuture = Preconditions.checkNotNull(this.function.apply(sourceResult), "AsyncFunction may not return null.");
                    final ListenableFuture<O> outputFuture = this.outputFuture;
                    if (this.isCancelled()) {
                        outputFuture.cancel(this.wasInterrupted());
                        this.outputFuture = null;
                        return;
                    }
                    outputFuture.addListener(new Runnable(){

                        @Override
                        public void run() {
                            try {
                                this.set(Uninterruptibles.getUninterruptibly(outputFuture));
                            }
                            catch (CancellationException e) {
                                this.cancel(false);
                            }
                            catch (ExecutionException e) {
                                this.setException(e.getCause());
                            }
                            finally {
                                outputFuture = null;
                            }
                        }
                    }, MoreExecutors.directExecutor());
                    break block14;
                    {
                        catch (UndeclaredThrowableException e) {
                            this.setException(e.getCause());
                            break block14;
                        }
                        catch (Throwable t) {
                            this.setException(t);
                            break block14;
                        }
                        catch (Throwable throwable) {
                            throw throwable;
                        }
                    }
                }
                finally {
                    this.function = null;
                    this.inputFuture = null;
                }
            }
        }
    }

    private static class ImmediateFailedFuture<V>
    extends ImmediateFuture<V> {
        private final Throwable thrown;

        ImmediateFailedFuture(Throwable thrown) {
            this.thrown = thrown;
        }

        @Override
        public V get() throws ExecutionException {
            throw new ExecutionException(this.thrown);
        }
    }

    private static class ImmediateSuccessfulFuture<V>
    extends ImmediateFuture<V> {
        private final V value;

        ImmediateSuccessfulFuture(V value) {
            this.value = value;
        }

        @Override
        public V get() {
            return this.value;
        }
    }

    private static abstract class ImmediateFuture<V>
    implements ListenableFuture<V> {
        private static final Logger log = Logger.getLogger(ImmediateFuture.class.getName());

        private ImmediateFuture() {
        }

        @Override
        public void addListener(Runnable listener, Executor executor) {
            Preconditions.checkNotNull(listener, "Runnable was null.");
            Preconditions.checkNotNull(executor, "Executor was null.");
            try {
                executor.execute(listener);
            }
            catch (RuntimeException e) {
                log.log(Level.SEVERE, "RuntimeException while executing runnable " + listener + " with executor " + executor, e);
            }
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }

        @Override
        public abstract V get() throws ExecutionException;

        @Override
        public V get(long timeout, TimeUnit unit) throws ExecutionException {
            Preconditions.checkNotNull(unit);
            return this.get();
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isDone() {
            return true;
        }
    }
}

