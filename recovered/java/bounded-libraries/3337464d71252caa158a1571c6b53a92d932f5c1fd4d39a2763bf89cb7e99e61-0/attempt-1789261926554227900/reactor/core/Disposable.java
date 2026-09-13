/*
 * Decompiled with CFR 0.152.
 */
package reactor.core;

import java.util.Collection;
import java.util.function.Supplier;
import reactor.util.annotation.Nullable;

@FunctionalInterface
public interface Disposable {
    public void dispose();

    default public boolean isDisposed() {
        return false;
    }

    public static interface Composite
    extends Disposable {
        public boolean add(Disposable var1);

        default public boolean addAll(Collection<? extends Disposable> ds) {
            boolean abort = this.isDisposed();
            for (Disposable disposable : ds) {
                if (abort) {
                    disposable.dispose();
                    continue;
                }
                abort = !this.add(disposable);
            }
            return !abort;
        }

        @Override
        public void dispose();

        @Override
        public boolean isDisposed();

        public boolean remove(Disposable var1);

        public int size();
    }

    public static interface Swap
    extends Disposable,
    Supplier<Disposable> {
        public boolean update(@Nullable Disposable var1);

        public boolean replace(@Nullable Disposable var1);
    }
}

