/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.jackson.internal.jackson.jaxrs.cfg;

import java.util.concurrent.atomic.AtomicBoolean;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.cfg.ObjectWriterModifier;

public class ObjectWriterInjector {
    protected static final ThreadLocal<ObjectWriterModifier> _threadLocal = new ThreadLocal();
    protected static final AtomicBoolean _hasBeenSet = new AtomicBoolean(false);

    private ObjectWriterInjector() {
    }

    public static void set(ObjectWriterModifier mod) {
        _hasBeenSet.set(true);
        _threadLocal.set(mod);
    }

    public static ObjectWriterModifier get() {
        return _hasBeenSet.get() ? _threadLocal.get() : null;
    }

    public static ObjectWriterModifier getAndClear() {
        ObjectWriterModifier mod = ObjectWriterInjector.get();
        if (mod != null) {
            _threadLocal.remove();
        }
        return mod;
    }
}

