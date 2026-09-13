/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.jsr166;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import sun.misc.Unsafe;

class UnsafeAccessor {
    UnsafeAccessor() {
    }

    static Unsafe getUnsafe() {
        try {
            return Unsafe.getUnsafe();
        }
        catch (SecurityException securityException) {
            try {
                return AccessController.doPrivileged(() -> {
                    Class<Unsafe> k = Unsafe.class;
                    for (Field f : k.getDeclaredFields()) {
                        f.setAccessible(true);
                        Object x = f.get(null);
                        if (!k.isInstance(x)) continue;
                        return (Unsafe)k.cast(x);
                    }
                    throw new NoSuchFieldError("the Unsafe");
                });
            }
            catch (PrivilegedActionException e) {
                throw new RuntimeException("Could not initialize intrinsics", e.getCause());
            }
        }
    }
}

