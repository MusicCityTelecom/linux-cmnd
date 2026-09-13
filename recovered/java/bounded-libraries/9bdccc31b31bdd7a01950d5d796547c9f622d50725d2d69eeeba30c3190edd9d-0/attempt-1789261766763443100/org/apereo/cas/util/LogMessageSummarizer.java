/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 */
package org.apereo.cas.util;

import org.slf4j.Logger;

public interface LogMessageSummarizer {
    public boolean shouldSummarize(Logger var1);

    public String summarizeStackTrace(String var1, Throwable var2);
}

