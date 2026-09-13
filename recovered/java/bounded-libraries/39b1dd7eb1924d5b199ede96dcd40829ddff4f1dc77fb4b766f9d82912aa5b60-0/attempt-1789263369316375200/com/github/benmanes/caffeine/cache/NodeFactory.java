/*
 * Decompiled with CFR 0.152.
 */
package com.github.benmanes.caffeine.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Interned;
import com.github.benmanes.caffeine.cache.Node;
import com.github.benmanes.caffeine.cache.References;
import com.github.benmanes.caffeine.cache.Weigher;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.ref.ReferenceQueue;

interface NodeFactory<K, V> {
    public static final MethodType FACTORY = MethodType.methodType(Void.TYPE);
    public static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
    public static final RetiredStrongKey RETIRED_STRONG_KEY = new RetiredStrongKey();
    public static final RetiredWeakKey RETIRED_WEAK_KEY = new RetiredWeakKey();
    public static final DeadStrongKey DEAD_STRONG_KEY = new DeadStrongKey();
    public static final DeadWeakKey DEAD_WEAK_KEY = new DeadWeakKey();
    public static final String ACCESS_TIME = "accessTime";
    public static final String WRITE_TIME = "writeTime";
    public static final String VALUE = "value";
    public static final String KEY = "key";

    default public boolean weakValues() {
        return false;
    }

    default public boolean softValues() {
        return false;
    }

    public Node<K, V> newNode(K var1, ReferenceQueue<K> var2, V var3, ReferenceQueue<V> var4, int var5, long var6);

    public Node<K, V> newNode(Object var1, V var2, ReferenceQueue<V> var3, int var4, long var5);

    default public Object newReferenceKey(K key, ReferenceQueue<K> referenceQueue) {
        return key;
    }

    default public Object newLookupKey(Object key) {
        return key;
    }

    public static <K, V> NodeFactory<K, V> newFactory(Caffeine<K, V> builder, boolean isAsync) {
        if (builder.interner) {
            return new Interned();
        }
        String className = NodeFactory.getClassName(builder, isAsync);
        return NodeFactory.loadFactory(className);
    }

    public static String getClassName(Caffeine<?, ?> builder, boolean isAsync) {
        StringBuilder className = new StringBuilder(Node.class.getPackageName()).append('.');
        if (builder.isStrongKeys()) {
            className.append('P');
        } else {
            className.append('F');
        }
        if (builder.isStrongValues()) {
            className.append('S');
        } else if (builder.isWeakValues()) {
            className.append('W');
        } else {
            className.append('D');
        }
        if (builder.expiresVariable()) {
            if (builder.refreshAfterWrite()) {
                className.append('A');
                if (builder.evicts()) {
                    className.append('W');
                }
            } else {
                className.append('W');
            }
        } else {
            if (builder.expiresAfterAccess()) {
                className.append('A');
            }
            if (builder.expiresAfterWrite()) {
                className.append('W');
            }
        }
        if (builder.refreshAfterWrite()) {
            className.append('R');
        }
        if (builder.evicts()) {
            className.append('M');
            if (isAsync || builder.isWeighted() && builder.weigher != Weigher.singletonWeigher()) {
                className.append('W');
            } else {
                className.append('S');
            }
        }
        return className.toString();
    }

    public static <K, V> NodeFactory<K, V> loadFactory(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            MethodHandle handle = LOOKUP.findConstructor(clazz, FACTORY);
            return handle.invoke();
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable t) {
            throw new IllegalStateException(className, t);
        }
    }

    public static final class DeadStrongKey {
    }

    public static final class RetiredStrongKey {
    }

    public static final class DeadWeakKey
    extends References.WeakKeyReference<Object> {
        DeadWeakKey() {
            super(null, null);
        }
    }

    public static final class RetiredWeakKey
    extends References.WeakKeyReference<Object> {
        RetiredWeakKey() {
            super(null, null);
        }
    }
}

