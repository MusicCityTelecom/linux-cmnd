/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.control.customizers;

import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilePhase;

public abstract class CompilationCustomizer
implements CompilationUnit.IPrimaryClassNodeOperation {
    private final CompilePhase phase;

    public CompilationCustomizer(CompilePhase phase) {
        this.phase = phase;
    }

    public CompilePhase getPhase() {
        return this.phase;
    }
}

