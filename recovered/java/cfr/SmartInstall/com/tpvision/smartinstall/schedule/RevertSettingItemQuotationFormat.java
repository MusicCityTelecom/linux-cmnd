/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.schedule;

import com.tpvision.smartinstall.dao.core.SettingPackage;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.schedule.Job;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RevertSettingItemQuotationFormat
extends Job {
    private static final Logger LOG = LoggerFactory.getLogger(RevertSettingItemQuotationFormat.class);

    @Override
    public String description() {
        return "used to add back the double quotation for below tv items";
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
        LOG.info("start to add back double quotation for 2k19 ms/ps/nafta T32/T32 Nafta");
        JpaManager.getSettingPackageManager().findSettingPackagesByPlatforms("2019 MS", "2019 PS", "2019 NAFTA", "TPM215HEA", "TPM215HKN").stream().forEach(settingPackage -> {
            String newValue;
            String oldValue = settingPackage.getValue();
            if (!StringUtils.equalsIgnoreCase(oldValue, newValue = oldValue.replace("Input Control.Remote Control.SmartInfo RC Key Mapping", "Input Control.Remote Control.\\\"SmartInfo\\\" RC Key Mapping").replace("Input Control.Remote Control.TV Menu RC Key Mapping", "Input Control.Remote Control.\\\"TV Menu\\\" RC Key Mapping").replace("Input Control.Remote Control.WatchTV RC Key Mapping", "Input Control.Remote Control.\\\"WatchTV\\\" RC Key Mapping").replace("Input Control.Remote Control.Cast RC Key Mapping", "Input Control.Remote Control.\\\"Cast\\\" RC Key Mapping"))) {
                settingPackage.setValue(newValue);
                JpaManager.getSettingPackageManager().save((SettingPackage)settingPackage);
            }
        });
        LOG.info("end handle add back double quotation for 2k19 ms/ps/nafta T32/T32 Nafta");
    }
}

