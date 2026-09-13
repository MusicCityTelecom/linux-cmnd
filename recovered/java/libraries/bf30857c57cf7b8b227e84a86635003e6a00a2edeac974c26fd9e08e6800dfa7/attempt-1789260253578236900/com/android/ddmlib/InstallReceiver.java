/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.MultiLineReceiver;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InstallReceiver
extends MultiLineReceiver {
    private static final String SUCCESS_OUTPUT = "Success";
    private static final Pattern FAILURE_PATTERN = Pattern.compile("Failure\\s+\\[(.*)\\]");
    private String mErrorMessage = null;
    private boolean mSuccessfullyCompleted = false;

    @Override
    public void processNewLines(String[] lines) {
        for (String line : lines) {
            if (line.isEmpty()) continue;
            if (line.startsWith(SUCCESS_OUTPUT)) {
                this.mSuccessfullyCompleted = true;
                this.mErrorMessage = null;
                continue;
            }
            Matcher m3 = FAILURE_PATTERN.matcher(line);
            this.mErrorMessage = m3.matches() ? m3.group(1) : "Unknown failure (" + line + ")";
        }
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    public String getErrorMessage() {
        return this.mErrorMessage;
    }

    public boolean isSuccessfullyCompleted() {
        return this.mSuccessfullyCompleted;
    }
}

