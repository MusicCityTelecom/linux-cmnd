/*
 * Decompiled with CFR 0.152.
 */
package com.sun.xml.bind;

final class StackHelper {
    private StackHelper() {
    }

    static String getCallerClassName() {
        return StackWalker.getInstance().walk(frames -> frames.map(StackWalker.StackFrame::getClassName).skip(2L).findFirst().get());
    }
}

