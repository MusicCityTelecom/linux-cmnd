/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.schedule;

import com.tpvision.smartinstall.schedule.CmndMetricsTask;
import com.tpvision.smartinstall.schedule.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteMetricsInfoJob
extends Job {
    private static final Logger LOG = LoggerFactory.getLogger(WriteMetricsInfoJob.class);

    @Override
    public String description() {
        return "used to write cmnd metrics info";
    }

    @Override
    public boolean isExecuteOnce() {
        return false;
    }

    @Override
    public Job.ExecuteType getExecuteType() {
        return Job.ExecuteType.BYHAND;
    }

    @Override
    public void execute() {
        LOG.info("start to write metrics log");
        new CmndMetricsTask().run();
        LOG.info("end to write metrics log");
    }
}

