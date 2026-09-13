/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.pms;

import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.pms.TmsUtils;
import com.tpvision.smartinstall.util.TpvTimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TmsConnectQueryTask
extends TpvTimerTask {
    private static final Logger LOG = LoggerFactory.getLogger(TmsConnectQueryTask.class);

    @Override
    public void tryRun() {
        LOG.info("checking TMS Connection,thread_Id:{},name:{}", (Object)Thread.currentThread().getId(), (Object)Thread.currentThread().getName());
        if (!PmsUtils.isPmsEnabled()) {
            LOG.info("Tms isn't enabled.");
            return;
        }
        TmsUtils tms = PmsUtils.getTmsInstance();
        if (tms != null) {
            LOG.debug("check connection");
            tms.checkConnection();
        }
    }
}

