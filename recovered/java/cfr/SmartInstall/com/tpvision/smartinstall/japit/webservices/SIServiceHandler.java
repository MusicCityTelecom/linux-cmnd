/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.japit.webservices;

import com.tpvision.smartinstall.japit.TVSIServiceManager;
import com.tpvision.smartinstall.japit.webservices.WebServiceCommandHandler;
import com.tpvision.smartinstall.servlet.IPProfile;

public class SIServiceHandler
extends WebServiceCommandHandler {
    @Override
    public String execute() {
        TVSIServiceManager.extractDevicesInfo(this.commandDetails, this.device);
        IPProfile lastConfig = IPProfile.loadIPProfile();
        return this.getProfessionalSettingsParameters(lastConfig.getURL(), lastConfig.getProfessionalService());
    }

    private String getProfessionalSettingsParameters(String customDashboardServerURL, String pollingFrequency) {
        String ret = "{  \"Svc\": \"WebServices\",  \"SvcVer\": \"3.0\",  \"Cookie\": 293,  \"CmdType\": \"Change\",  \"Fun\": \"SIService\",  \"CommandDetails\": {    \"WebServiceParameters\": {      \"PollingFrequency\": " + pollingFrequency + ",      \"TVUniqueID\": \"" + this.tvUniqueId + "\"    },    \"ProfessionalSettingsParameters\": {";
        ret = "".equals(customDashboardServerURL) ? ret + "} }}" : ret + "   \"CustomDashboardServerURL\": \"" + customDashboardServerURL + "\"} }}";
        return ret;
    }
}

