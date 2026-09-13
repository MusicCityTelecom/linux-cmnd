/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.diagnostics;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.diagnostics.FailureAnalysisReporter;
import org.springframework.util.StringUtils;

public final class LoggingFailureAnalysisReporter
implements FailureAnalysisReporter {
    private static final Log logger = LogFactory.getLog(LoggingFailureAnalysisReporter.class);

    @Override
    public void report(FailureAnalysis failureAnalysis) {
        if (logger.isDebugEnabled()) {
            logger.debug((Object)"Application failed to start due to an exception", failureAnalysis.getCause());
        }
        if (logger.isErrorEnabled()) {
            logger.error((Object)this.buildMessage(failureAnalysis));
        }
    }

    private String buildMessage(FailureAnalysis failureAnalysis) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%n%n", new Object[0]));
        builder.append(String.format("***************************%n", new Object[0]));
        builder.append(String.format("APPLICATION FAILED TO START%n", new Object[0]));
        builder.append(String.format("***************************%n%n", new Object[0]));
        builder.append(String.format("Description:%n%n", new Object[0]));
        builder.append(String.format("%s%n", failureAnalysis.getDescription()));
        if (StringUtils.hasText((String)failureAnalysis.getAction())) {
            builder.append(String.format("%nAction:%n%n", new Object[0]));
            builder.append(String.format("%s%n", failureAnalysis.getAction()));
        }
        return builder.toString();
    }
}

