/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.cas.util.LogMessageSummarizer
 *  org.slf4j.Logger
 */
package org.apereo.cas.util;

import java.util.Arrays;
import org.apereo.cas.util.LogMessageSummarizer;
import org.slf4j.Logger;

public class DefaultLogMessageSummarizer
implements LogMessageSummarizer {
    public boolean shouldSummarize(Logger logger) {
        return !logger.isDebugEnabled();
    }

    public String summarizeStackTrace(String message, Throwable throwable) {
        StringBuilder builder = new StringBuilder(message).append('\n');
        Arrays.stream(throwable.getStackTrace()).limit(3L).forEach(trace -> {
            String error = String.format("\t%s:%s:%s%n", trace.getFileName(), trace.getMethodName(), trace.getLineNumber());
            builder.append(error);
        });
        return builder.toString();
    }
}

