/*
 * Decompiled with CFR 0.152.
 */
package org.aopalliance.instrument;

import org.aopalliance.instrument.Instrumentation;

public class UndoNotSupportedException
extends Exception {
    public UndoNotSupportedException(Instrumentation instrumentation) {
        super("Undo not supported for instrumentation: " + instrumentation);
    }
}

