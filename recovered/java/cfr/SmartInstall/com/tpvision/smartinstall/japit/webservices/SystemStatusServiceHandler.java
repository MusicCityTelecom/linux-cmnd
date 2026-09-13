/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.japit.webservices;

import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.japit.webservices.WebServiceCommandHandler;
import com.tpvision.smartinstall.util.UserEmailMonitorHelper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemStatusServiceHandler
extends WebServiceCommandHandler {
    private static final Logger LOG = LoggerFactory.getLogger(SystemStatusServiceHandler.class);

    @Override
    public String execute() {
        String beforeRemoteStatus = this.device.getRemotecontrolStatus();
        LOG.info("rc before stutus=>{}", (Object)beforeRemoteStatus);
        String currentRemoteStatus = null;
        JSONObject systemStatusParameters = this.commandDetails.optJSONObject("SystemStatusParameters");
        if (systemStatusParameters != null && systemStatusParameters.has("RemoteControlStatus")) {
            currentRemoteStatus = systemStatusParameters.getJSONObject("RemoteControlStatus").toString();
        }
        if (currentRemoteStatus != null) {
            if (!currentRemoteStatus.equalsIgnoreCase(beforeRemoteStatus)) {
                this.device.setRemotecontrolStatus(currentRemoteStatus);
                JpaManager.getDevicesManager().save(this.device);
            }
            if (!currentRemoteStatus.contains("BatteryOK")) {
                UserEmailMonitorHelper.sendRemoteControlLowBatteryNotice(this.device);
            }
        }
        return "";
    }
}

