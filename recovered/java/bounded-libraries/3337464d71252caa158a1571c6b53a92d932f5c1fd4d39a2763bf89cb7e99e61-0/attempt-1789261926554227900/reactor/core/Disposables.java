/*
 * Decompiled with CFR 0.152.
 */
package reactor.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.util.annotation.Nullable;

public final class Disposables {
    static final Disposable DISPOSED = Disposables.disposed();

    private Disposables() {
    }

    public static Disposable.Composite composite() {
        return new ListCompositeDisposable();
    }

    public static Disposable.Composite composite(Disposable ... disposables) {
        return new ListCompositeDisposable(disposables);
    }

    public static Disposable.Composite composite(Iterable<? extends Disposable> disposables) {
        return new ListCompositeDisposable(disposables);
    }

    public static Disposable disposed() {
        return new AlwaysDisposable();
    }

    public static Disposable never() {
        return new NeverDisposable();
    }

    public static Disposable single() {
        return new SimpleDisposable();
    }

    public static Disposable.Swap swap() {
        return new SwapDisposable();
    }

    static <T> boolean set(AtomicReferenceFieldUpdater<T, Disposable> updater, T holder, @Nullable Disposable newValue) {
        Disposable current;
        do {
            if ((current = updater.get(holder)) != DISPOSED) continue;
            if (newValue != null) {
                newValue.dispose();
            }
            return false;
        } while (!updater.compareAndSet(holder, current, newValue));
        if (current != null) {
            current.dispose();
        }
        return true;
    }

    static <T> boolean replace(AtomicReferenceFieldUpdater<T, Disposable> updater, T holder, @Nullable Disposable newValue) {
        Disposable current;
        do {
            if ((current = updater.get(holder)) != DISPOSED) continue;
            if (newValue != null) {
                newValue.dispose();
            }
            return false;
        } while (!updater.compareAndSet(holder, current, newValue));
        return true;
    }

    static <T> boolean dispose(AtomicReferenceFieldUpdater<T, Disposable> updater, T holder) {
        Disposable d;
        Disposable current = updater.get(holder);
        if (current != (d = DISPOSED) && (current = updater.getAndSet(holder, d)) != d) {
            if (current != null) {
                current.dispose();
            }
            return true;
        }
        return false;
    }

    static boolean isDisposed(Disposable d) {
        return d == DISPOSED;
    }

    static final class NeverDisposable
    implements Disposable {
        NeverDisposable() {
        }

        @Override
        public void dispose() {
        }

        @Override
        public boolean isDisposed() {
            return false;
        }
    }

    static final class AlwaysDisposable
    implements Disposable {
        AlwaysDisposable() {
        }

        @Override
        public void dispose() {
        }

        @Override
        public boolean isDisposed() {
            return true;
        }
    }

    static final class SimpleDisposable
    extends AtomicBoolean
    implements Disposable {
        SimpleDisposable() {
        }

        @Override
        public void dispose() {
            this.set(true);
        }

        @Override
        public boolean isDisposed() {
            return this.get();
        }
    }

    static final class SwapDisposable
    implements Disposable.Swap {
        volatile Disposable inner;
        static final AtomicReferenceFieldUpdater<SwapDisposable, Disposable> INNER = AtomicReferenceFieldUpdater.newUpdater(SwapDisposable.class, Disposable.class, "inner");

        SwapDisposable() {
        }

        @Override
        public boolean update(@Nullable Disposable next) {
            return Disposables.set(INNER, this, next);
        }

        @Override
        public boolean replace(@Nullable Disposable next) {
            return Disposables.replace(INNER, this, next);
        }

        @Override
        @Nullable
        public Disposable get() {
            return this.inner;
        }

        @Override
        public void dispose() {
            Disposables.dispose(INNER, this);
        }

        @Override
        public boolean isDisposed() {
            return Disposables.isDisposed(INNER.get(this));
        }
    }

    static final class ListCompositeDisposable
    implements Disposable.Composite,
    Scannable {
        @Nullable
        List<Disposable> resources;
        volatile boolean disposed;

        ListCompositeDisposable() {
        }

        ListCompositeDisposable(Disposable ... resources) {
            Objects.requireNonNull(resources, "resources is null");
            this.resources = new LinkedList<Disposable>();
            for (Disposable d : resources) {
                Objects.requireNonNull(d, "Disposable item is null");
                this.resources.add(d);
            }
        }

        ListCompositeDisposable(Iterable<? extends Disposable> resources) {
            Objects.requireNonNull(resources, "resources is null");
            this.resources = new LinkedList<Disposable>();
            for (Disposable disposable : resources) {
                Objects.requireNonNull(disposable, "Disposable item is null");
                this.resources.add(disposable);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void dispose() {
            List<Disposable> set;
            if (this.disposed) {
                return;
            }
            ListCompositeDisposable listCompositeDisposable = this;
            synchronized (listCompositeDisposable) {
                if (this.disposed) {
                    return;
                }
                this.disposed = true;
                set = this.resources;
                this.resources = null;
            }
            this.dispose(set);
        }

        @Override
        public boolean isDisposed() {
            return this.disposed;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean add(Disposable d) {
            Objects.requireNonNull(d, "d is null");
            if (!this.disposed) {
                ListCompositeDisposable listCompositeDisposable = this;
                synchronized (listCompositeDisposable) {
                    if (!this.disposed) {
                        List<Disposable> set = this.resources;
                        if (set == null) {
                            this.resources = set = new LinkedList<Disposable>();
                        }
                        set.add(d);
                        return true;
                    }
                }
            }
            d.dispose();
            return false;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         * WARNING - void declaration
         */
        @Override
        public boolean addAll(Collection<? extends Disposable> ds) {
            Objects.requireNonNull(ds, "ds is null");
            if (!this.disposed) {
                ListCompositeDisposable listCompositeDisposable = this;
                synchronized (listCompositeDisposable) {
                    if (!this.disposed) {
                        List<Disposable> list = this.resources;
                        if (list == null) {
                            LinkedList<Disposable> linkedList = new LinkedList<Disposable>();
                            this.resources = linkedList;
                        }
                        for (Disposable disposable : ds) {
                            void var3_5;
                            Objects.requireNonNull(disposable, "d is null");
                            var3_5.add(disposable);
                        }
                        return true;
                    }
                }
            }
            for (Disposable disposable : ds) {
                disposable.dispose();
            }
            return false;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean remove(Disposable d) {
            Objects.requireNonNull(d, "Disposable item is null");
            if (this.disposed) {
                return false;
            }
            ListCompositeDisposable listCompositeDisposable = this;
            synchronized (listCompositeDisposable) {
                if (this.disposed) {
                    return false;
                }
                List<Disposable> set = this.resources;
                if (set == null || !set.remove(d)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public int size() {
            List<Disposable> r = this.resources;
            return r == null ? 0 : r.size();
        }

        Stream<Disposable> asStream() {
            List<Disposable> r = this.resources;
            return r == null ? Stream.empty() : r.stream();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void clear() {
            List<Disposable> set;
            if (this.disposed) {
                return;
            }
            ListCompositeDisposable listCompositeDisposable = this;
            synchronized (listCompositeDisposable) {
                if (this.disposed) {
                    return;
                }
                set = this.resources;
                this.resources = null;
            }
            this.dispose(set);
        }

        void dispose(@Nullable List<Disposable> set) {
            if (set == null) {
                return;
            }
            ArrayList<Throwable> errors = null;
            for (Disposable o : set) {
                try {
                    o.dispose();
                }
                catch (Throwable ex) {
                    Exceptions.throwIfFatal(ex);
                    if (errors == null) {
                        errors = new ArrayList<Throwable>();
                    }
                    errors.add(ex);
                }
            }
            if (errors != null) {
                if (errors.size() == 1) {
                    throw Exceptions.propagate((Throwable)errors.get(0));
                }
                throw Exceptions.multiple((Iterable<Throwable>)errors);
            }
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return this.asStream().filter(Scannable.class::isInstance).map(Scannable::from);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return this.isDisposed();
            }
            return null;
        }
    }
}

