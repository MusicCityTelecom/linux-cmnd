/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TpvTimerTask
extends TimerTask {
    private static final Logger LOG = LoggerFactory.getLogger(TpvTimerTask.class);

    @Override
    public void run() {
        try {
            this.tryRun();
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public abstract void tryRun();
}

