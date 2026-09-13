/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.diagnostics;

import org.springframework.boot.diagnostics.FailureAnalysis;

@FunctionalInterface
public interface FailureAnalyzer {
    public FailureAnalysis analyze(Throwable var1);
}

