/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  liquibase.exception.ChangeLogParseException
 */
package org.springframework.boot.liquibase;

import liquibase.exception.ChangeLogParseException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

class LiquibaseChangelogMissingFailureAnalyzer
extends AbstractFailureAnalyzer<ChangeLogParseException> {
    private static final String MESSAGE_SUFFIX = " does not exist";

    LiquibaseChangelogMissingFailureAnalyzer() {
    }

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, ChangeLogParseException cause) {
        if (cause.getMessage().endsWith(MESSAGE_SUFFIX)) {
            String changelogPath = this.extractChangelogPath(cause);
            return new FailureAnalysis(this.getDescription(changelogPath), "Make sure a Liquibase changelog is present at the configured path.", (Throwable)cause);
        }
        return null;
    }

    private String extractChangelogPath(ChangeLogParseException cause) {
        return cause.getMessage().substring(0, cause.getMessage().length() - MESSAGE_SUFFIX.length());
    }

    private String getDescription(String changelogPath) {
        return "Liquibase failed to start because no changelog could be found at '" + changelogPath + "'.";
    }
}

