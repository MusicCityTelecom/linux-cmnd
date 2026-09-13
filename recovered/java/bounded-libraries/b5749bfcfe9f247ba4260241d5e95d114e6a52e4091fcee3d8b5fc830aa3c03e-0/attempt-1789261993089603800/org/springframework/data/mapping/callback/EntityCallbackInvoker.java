/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.data.mapping.callback;

import java.util.function.BiFunction;
import org.springframework.data.mapping.callback.EntityCallback;

interface EntityCallbackInvoker {
    public <T> Object invokeCallback(EntityCallback<T> var1, T var2, BiFunction<EntityCallback<T>, T, Object> var3);

    public static boolean matchesClassCastMessage(String exceptionMessage, Class<?> eventClass) {
        if (exceptionMessage.startsWith(eventClass.getName())) {
            return true;
        }
        if (exceptionMessage.startsWith(eventClass.toString())) {
            return true;
        }
        int moduleSeparatorIndex = exceptionMessage.indexOf(47);
        if (moduleSeparatorIndex != -1 && exceptionMessage.startsWith(eventClass.getName(), moduleSeparatorIndex + 1)) {
            return true;
        }
        return exceptionMessage.equals("argument type mismatch");
    }
}

