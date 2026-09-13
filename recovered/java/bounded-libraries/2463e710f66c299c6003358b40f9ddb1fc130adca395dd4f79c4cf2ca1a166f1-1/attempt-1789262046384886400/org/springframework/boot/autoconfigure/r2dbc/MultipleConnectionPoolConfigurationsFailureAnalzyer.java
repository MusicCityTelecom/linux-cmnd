/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.diagnostics.AbstractFailureAnalyzer
 *  org.springframework.boot.diagnostics.FailureAnalysis
 */
package org.springframework.boot.autoconfigure.r2dbc;

import org.springframework.boot.autoconfigure.r2dbc.MultipleConnectionPoolConfigurationsException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

class MultipleConnectionPoolConfigurationsFailureAnalzyer
extends AbstractFailureAnalyzer<MultipleConnectionPoolConfigurationsException> {
    MultipleConnectionPoolConfigurationsFailureAnalzyer() {
    }

    protected FailureAnalysis analyze(Throwable rootFailure, MultipleConnectionPoolConfigurationsException cause) {
        return new FailureAnalysis(cause.getMessage(), "Update your configuration so that R2DBC connection pooling is configured using either the spring.r2dbc.url property or the spring.r2dbc.pool.* properties", (Throwable)cause);
    }
}

