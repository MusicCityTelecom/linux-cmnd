/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.runtime.memoize;

import groovy.lang.Closure;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Arrays;
import java.util.Collections;
import org.codehaus.groovy.runtime.memoize.LRUProtectionStorage;
import org.codehaus.groovy.runtime.memoize.MemoizeCache;
import org.codehaus.groovy.runtime.memoize.NullProtectionStorage;
import org.codehaus.groovy.runtime.memoize.ProtectionStorage;

public abstract class Memoize {
    private static final MemoizeNullValue MEMOIZE_NULL = new MemoizeNullValue();

    public static <V> Closure<V> buildMemoizeFunction(MemoizeCache<Object, Object> cache, Closure<V> closure) {
        return new MemoizeFunction<V>(cache, closure);
    }

    public static <V> Closure<V> buildSoftReferenceMemoizeFunction(int protectedCacheSize, MemoizeCache<Object, SoftReference<Object>> cache, Closure<V> closure) {
        ProtectionStorage lruProtectionStorage = protectedCacheSize > 0 ? new LRUProtectionStorage(protectedCacheSize) : new NullProtectionStorage();
        ReferenceQueue queue = new ReferenceQueue();
        return new SoftReferenceMemoizeFunction<V>(cache, closure, lruProtectionStorage, queue);
    }

    private static Object generateKey(Object[] args) {
        if (args == null) {
            return Collections.emptyList();
        }
        Object[] copyOfArgs = Arrays.copyOf(args, args.length);
        return Arrays.asList(copyOfArgs);
    }

    private static class MemoizeNullValue {
        private MemoizeNullValue() {
        }

        public boolean equals(Object obj) {
            return obj instanceof MemoizeNullValue;
        }

        public int hashCode() {
            return "MemoizeNullValue".hashCode();
        }
    }

    private static class MemoizeFunction<V>
    extends Closure<V> {
        private static final long serialVersionUID = -2780003153676993093L;
        final MemoizeCache<Object, Object> cache;
        final Closure<V> closure;

        MemoizeFunction(MemoizeCache<Object, ?> cache, Closure<V> closure) {
            super(closure.getOwner());
            this.cache = MemoizeFunction.coerce(cache);
            this.closure = closure;
            this.parameterTypes = closure.getParameterTypes();
            this.maximumNumberOfParameters = closure.getMaximumNumberOfParameters();
        }

        private static MemoizeCache coerce(MemoizeCache<Object, ?> cache) {
            return cache;
        }

        @Override
        public V call(Object ... args) {
            Object key = Memoize.generateKey(args);
            Object result = this.cache.getAndPut(key, k -> {
                V r = this.closure.call(args);
                return r != null ? r : MEMOIZE_NULL;
            });
            return (V)(result == MEMOIZE_NULL ? null : result);
        }

        public V doCall(Object ... args) {
            return this.call(args);
        }
    }

    private static class SoftReferenceMemoizeFunction<V>
    extends MemoizeFunction<V> {
        private static final long serialVersionUID = -1338206227167457991L;
        final ProtectionStorage lruProtectionStorage;
        final ReferenceQueue queue;

        SoftReferenceMemoizeFunction(MemoizeCache<Object, SoftReference<Object>> cache, Closure<V> closure, ProtectionStorage lruProtectionStorage, ReferenceQueue queue) {
            super(cache, closure);
            this.lruProtectionStorage = lruProtectionStorage;
            this.queue = queue;
        }

        @Override
        public V call(Object ... args) {
            if (this.queue.poll() != null) {
                SoftReferenceMemoizeFunction.cleanUpNullReferences(this.cache, this.queue);
            }
            Object key = Memoize.generateKey(args);
            SoftReference reference = (SoftReference)this.cache.getAndPut(key, k -> {
                Object r = this.closure.call(args);
                return null != r ? new SoftReference(r, this.queue) : new SoftReference<MemoizeNullValue>(MEMOIZE_NULL);
            });
            Object result = reference.get();
            this.lruProtectionStorage.touch(key, result);
            return result == MEMOIZE_NULL ? null : (V)result;
        }

        private static void cleanUpNullReferences(MemoizeCache<Object, Object> cache, ReferenceQueue queue) {
            while (queue.poll() != null) {
            }
            cache.cleanUpNullReferences();
        }
    }
}

