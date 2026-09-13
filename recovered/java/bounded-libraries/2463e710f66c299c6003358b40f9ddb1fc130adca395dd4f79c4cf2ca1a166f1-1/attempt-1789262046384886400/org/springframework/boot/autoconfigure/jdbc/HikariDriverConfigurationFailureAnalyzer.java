/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.boot.diagnostics.AbstractFailureAnalyzer
 *  org.springframework.boot.diagnostics.FailureAnalysis
 *  org.springframework.jdbc.CannotGetJdbcConnectionException
 */
package org.springframework.boot.autoconfigure.jdbc;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

class HikariDriverConfigurationFailureAnalyzer
extends AbstractFailureAnalyzer<CannotGetJdbcConnectionException> {
    private static final String EXPECTED_MESSAGE = "cannot use driverClassName and dataSourceClassName together.";

    HikariDriverConfigurationFailureAnalyzer() {
    }

    protected FailureAnalysis analyze(Throwable rootFailure, CannotGetJdbcConnectionException cause) {
        Throwable subCause = cause.getCause();
        if (subCause == null || !EXPECTED_MESSAGE.equals(subCause.getMessage())) {
            return null;
        }
        return new FailureAnalysis("Configuration of the Hikari connection pool failed: 'dataSourceClassName' is not supported.", "Spring Boot auto-configures only a driver and can't specify a custom DataSource. Consider configuring the Hikari DataSource in your own configuration.", (Throwable)cause);
    }
}

