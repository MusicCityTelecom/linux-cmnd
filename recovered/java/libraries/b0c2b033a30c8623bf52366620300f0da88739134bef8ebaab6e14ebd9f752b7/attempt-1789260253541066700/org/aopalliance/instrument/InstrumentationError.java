/*
 * Decompiled with CFR 0.152.
 */
package org.aopalliance.instrument;

import org.aopalliance.instrument.Instrumentation;

public class InstrumentationError
extends Error {
    public InstrumentationError(Instrumentation instrumentation, Throwable cause) {
        super("Error while instrumenting " + instrumentation, cause);
    }
}

