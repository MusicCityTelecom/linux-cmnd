/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.schedule;

import com.tpvision.smartinstall.api.ApiLicenseChecker;
import com.tpvision.smartinstall.util.TpvTimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SyncLicenseManagerTask
extends TpvTimerTask {
    private static final Logger LOG = LoggerFactory.getLogger(SyncLicenseManagerTask.class);

    @Override
    public void tryRun() {
        LOG.info("SyncLicenseManagerTask start..");
        ApiLicenseChecker.getInstance().reload(false);
        LOG.info("SyncLicenseManagerTask end");
    }
}

