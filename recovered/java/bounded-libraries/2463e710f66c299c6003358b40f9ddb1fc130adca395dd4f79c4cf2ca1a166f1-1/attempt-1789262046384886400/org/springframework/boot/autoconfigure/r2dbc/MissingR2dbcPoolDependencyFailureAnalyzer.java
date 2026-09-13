/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.diagnostics.AbstractFailureAnalyzer
 *  org.springframework.boot.diagnostics.FailureAnalysis
 */
package org.springframework.boot.autoconfigure.r2dbc;

import org.springframework.boot.autoconfigure.r2dbc.MissingR2dbcPoolDependencyException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

class MissingR2dbcPoolDependencyFailureAnalyzer
extends AbstractFailureAnalyzer<MissingR2dbcPoolDependencyException> {
    MissingR2dbcPoolDependencyFailureAnalyzer() {
    }

    protected FailureAnalysis analyze(Throwable rootFailure, MissingR2dbcPoolDependencyException cause) {
        return new FailureAnalysis(cause.getMessage(), "Update your application's build to depend on io.r2dbc:r2dbc-pool or your application's configuration to disable R2DBC connection pooling.", (Throwable)cause);
    }
}

