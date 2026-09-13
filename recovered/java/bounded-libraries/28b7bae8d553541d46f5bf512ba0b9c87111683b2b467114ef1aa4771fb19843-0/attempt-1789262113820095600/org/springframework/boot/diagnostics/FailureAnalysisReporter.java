/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.diagnostics;

import org.springframework.boot.diagnostics.FailureAnalysis;

@FunctionalInterface
public interface FailureAnalysisReporter {
    public void report(FailureAnalysis var1);
}

