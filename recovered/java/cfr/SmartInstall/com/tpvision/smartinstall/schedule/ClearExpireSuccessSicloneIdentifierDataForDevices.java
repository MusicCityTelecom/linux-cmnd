/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.schedule;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.schedule.Job;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClearExpireSuccessSicloneIdentifierDataForDevices
extends Job {
    private static final Logger LOG = LoggerFactory.getLogger(ClearExpireSuccessSicloneIdentifierDataForDevices.class);

    @Override
    public String description() {
        return "use this job to clear old expired data in the device's field \"success_tvclone_Identifier\"";
    }

    @Override
    public Job.ExecuteType getExecuteType() {
        return Job.ExecuteType.AUTOMATICALLY;
    }

    @Override
    public boolean isExecuteOnce() {
        return true;
    }

    @Override
    public void execute() {
        LOG.info("start to clear old expired success_tvclone_Identifier data for devices");
        List<Devices> devicesList = JpaManager.getDevicesManager().loadAll();
        for (Devices devices : devicesList) {
            if (!StringUtils.isNotBlank(devices.getLastSuccessSettingPackageId())) continue;
            devices.setLastSuccessSettingPackageId(null);
            LOG.info("reset success_tvclone_Identifier to null for devices -> {}", (Object)devices.getTvuniqueid());
            JpaManager.getDevicesManager().save(devices);
        }
        LOG.info("end handle clear old expired success_tvclone_Identifier data");
    }
}

