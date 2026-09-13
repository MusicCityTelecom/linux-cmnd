/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import org.apache.commons.exec.DefaultExecutor;

public class RFCommandState {
    private static RFCommandState instance = new RFCommandState();
    private DefaultExecutor exec;

    private RFCommandState() {
    }

    public static RFCommandState instance() {
        return instance;
    }

    public void register(DefaultExecutor executor) {
        if (null != this.exec) {
            this.cleanup();
        }
        this.exec = executor;
    }

    public void cleanup() {
        if (null != this.exec) {
            this.exec.getWatchdog().destroyProcess();
            this.exec = null;
        }
    }

    public boolean hasExecutionRunning() {
        boolean result = false;
        if (null != this.exec) {
            result = true;
        }
        return result;
    }
}

