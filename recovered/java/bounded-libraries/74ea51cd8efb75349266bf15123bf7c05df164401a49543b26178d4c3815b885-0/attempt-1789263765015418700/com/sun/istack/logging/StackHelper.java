/*
 * Decompiled with CFR 0.152.
 */
package com.sun.istack.logging;

import com.sun.istack.logging.Logger;

class StackHelper {
    StackHelper() {
    }

    static String getCallerMethodName() {
        return StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).walk(frames -> frames.dropWhile(f -> !Logger.class.equals(f.getDeclaringClass())).dropWhile(f -> Logger.class.equals(f.getDeclaringClass())).findFirst().map(StackWalker.StackFrame::getMethodName).orElse("UNKNOWN METHOD"));
    }
}

