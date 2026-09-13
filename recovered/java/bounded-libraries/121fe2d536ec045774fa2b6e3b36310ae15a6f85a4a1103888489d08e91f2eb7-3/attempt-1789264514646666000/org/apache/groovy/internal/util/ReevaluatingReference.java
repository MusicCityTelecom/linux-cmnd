/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.internal.util;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import java.lang.ref.WeakReference;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import org.apache.groovy.internal.util.Function;
import org.apache.groovy.internal.util.Supplier;
import org.apache.groovy.internal.util.UncheckedThrow;
import org.apache.groovy.lang.annotation.Incubating;
import org.codehaus.groovy.GroovyBugError;

@Incubating
public class ReevaluatingReference<T> {
    private static final MethodHandle FALLBACK_HANDLE;
    private final Supplier<T> valueSupplier;
    private final Function<T, SwitchPoint> validationSupplier;
    private final WeakReference<Class<T>> clazzRef;
    private MethodHandle returnRef;

    private static <T> T doPrivileged(PrivilegedExceptionAction<T> action) throws PrivilegedActionException {
        return AccessController.doPrivileged(action);
    }

    public ReevaluatingReference(Class clazz, Supplier<T> valueSupplier, Function<T, SwitchPoint> validationSupplier) {
        this.valueSupplier = valueSupplier;
        this.validationSupplier = validationSupplier;
        this.clazzRef = new WeakReference<Class>(clazz);
        this.replacePayLoad();
    }

    private T replacePayLoad() {
        T payload = this.valueSupplier.get();
        MethodHandle ref = MethodHandles.constant((Class)this.clazzRef.get(), payload);
        SwitchPoint sp = this.validationSupplier.apply(payload);
        this.returnRef = sp.guardWithTest(ref, FALLBACK_HANDLE);
        return payload;
    }

    public T getPayload() {
        Object ref = null;
        try {
            ref = this.returnRef.invokeExact();
        }
        catch (Throwable throwable) {
            UncheckedThrow.rethrow(throwable);
        }
        return (T)ref;
    }

    static {
        try {
            FALLBACK_HANDLE = ReevaluatingReference.doPrivileged(() -> MethodHandles.lookup().findSpecial(ReevaluatingReference.class, "replacePayLoad", MethodType.methodType(Object.class), ReevaluatingReference.class));
        }
        catch (PrivilegedActionException e) {
            throw new GroovyBugError(e);
        }
    }
}

