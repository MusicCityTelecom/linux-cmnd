/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.japit.webservices;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.OnlineDevices;
import com.tpvision.smartinstall.japit.TVDiscoveryManager;
import com.tpvision.smartinstall.japit.webservices.WebServiceCommandHandler;
import com.tpvision.smartinstall.servlet.IPProfile;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.JAPITUtils;
import com.tpvision.smartinstall.util.NetworkUtils;
import com.tpvision.smartinstall.util.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TVDiscoveryServiceHandler
extends WebServiceCommandHandler {
    private static final Logger LOG = LoggerFactory.getLogger(TVDiscoveryServiceHandler.class);

    @Override
    public String execute() {
        JSONObject tvDiscoveryParameters = this.commandDetails.optJSONObject("TVDiscoveryParameters");
        if (null == tvDiscoveryParameters) {
            LOG.error("TVDiscoveryService TVDiscoveryParameters is null.");
            return "";
        }
        boolean isUsingHttps = this.requestSchema.equals("https");
        String tvIpAddress = tvDiscoveryParameters.optString("TVIPAddress");
        Properties prop = Utils.getProductPropties();
        String incomingTvAdr = prop.getProperty("incomingTvAdr");
        if ("on".equalsIgnoreCase(incomingTvAdr)) {
            tvIpAddress = this.clientIp;
        }
        String responseUniqueId = this.tvUniqueId;
        if (this.device != null && "Transiting".equalsIgnoreCase(this.device.getSecureCmdSupport())) {
            LOG.info("tv is still transiting,ignore initOnlineTvInfo");
        } else {
            int port = this.detectSecureStatusAndWlsPort(isUsingHttps, tvIpAddress);
            OnlineDevices onlineTvInfo = TVDiscoveryManager.initOnlineTvInfoDiscoveryParameter(tvIpAddress, port, this.tvUniqueId, tvDiscoveryParameters, isUsingHttps, true);
            if (onlineTvInfo != null) {
                if (port != 0) {
                    if (isUsingHttps) {
                        onlineTvInfo.setWlsSecurePort(port);
                    } else {
                        onlineTvInfo.setWlsPort(port);
                    }
                }
                Devices updatedDevice = TVDiscoveryManager.saveDetectedTvToDevices(onlineTvInfo, this.device, true);
                responseUniqueId = updatedDevice.getTvuniqueid();
            }
        }
        return this.getTvDiscoveryServiceResponse(responseUniqueId);
    }

    private int detectSecureStatusAndWlsPort(boolean isUsingHttps, String tvIp) {
        LOG.info("start to detect tv's japit port info,isUsingHttps-> {}", (Object)isUsingHttps);
        if (this.device != null && isUsingHttps && this.device.getWlsSecurePort() != 0) {
            LOG.info("tv is not new and already has the https port<{}>,skip detect ports job", (Object)this.device.getWlsSecurePort());
            return this.device.getWlsSecurePort();
        }
        if (this.device != null && !isUsingHttps && this.device.getWlsPort() != 0) {
            LOG.info("tv is not new and already has the http port<{}>,skip detect ports job", (Object)this.device.getWlsPort());
            return this.device.getWlsPort();
        }
        ArrayList<Integer> possiblePorts = new ArrayList<Integer>();
        if (isUsingHttps) {
            possiblePorts.addAll(CommonConstants.TV_WIXP_HTTPS_PORTS);
        } else {
            possiblePorts.addAll(CommonConstants.TV_WIXP_HTTP_PORTS);
        }
        int port = this.detectJapitPossiblePorts(tvIp, possiblePorts);
        LOG.info("detect tv's wls port=>{}, isSecure=>{}", (Object)port, (Object)isUsingHttps);
        return port;
    }

    private int detectJapitPossiblePorts(String tvIp, List<Integer> possiblePorts) {
        int validPort;
        LOG.info("detect target possiblePorts=>{}", (Object)possiblePorts);
        int port = 0;
        List<Integer> validPorts = NetworkUtils.filterSocketPorts(tvIp, possiblePorts);
        if (validPorts.size() == 1) {
            port = validPorts.get(0);
            LOG.info("only one port is OK ==>{}", (Object)port);
        } else if (validPorts.size() > 1 && (validPort = this.filterJapitPort(tvIp, validPorts)) != -1) {
            port = validPort;
            LOG.info("multiple ports are OK, use japit to test result=> {}", (Object)port);
        }
        if (port == 0) {
            LOG.warn("detect failure,return 0,wait for next round discovery japit");
        }
        return port;
    }

    private String getTvDiscoveryServiceResponse(String responseUniqueId) {
        IPProfile lastConfig = IPProfile.loadIPProfile();
        return this.getPollingFrequency(lastConfig.getTvDiscovery(), responseUniqueId);
    }

    private int filterJapitPort(String tvIp, List<Integer> validPorts) {
        for (int port : validPorts) {
            try {
                boolean bHttps = CommonConstants.TV_WIXP_HTTPS_PORTS.contains(port);
                String response = JAPITUtils.sendJapitCommandToTV(tvIp, port, bHttps, this.getSIServiceJapit(), 1000);
                if (!response.contains("CommandDetails")) continue;
                return port;
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return -1;
    }

    private String getSIServiceJapit() {
        return "{\t\"Svc\" : \"WebListeningServices\",\t\"SvcVer\": \"4.0\",\t\"Cookie\": " + JAPITUtils.getJapitRandomCookieValue() + ",\t\"CmdType\" : \"Request\",\t\"Fun\" : \"SIService\"}";
    }

    private String getPollingFrequency(String pollingFrequency, String responseUniqueId) {
        return "{  \"Svc\": \"WebServices\",  \"SvcVer\": \"1.0\",  \"Cookie\": 294,  \"CmdType\": \"Change\",  \"Fun\": \"TVDiscoveryService\",  \"CommandDetails\": {    \"WebServiceParameters\": {      \"PollingFrequency\": " + pollingFrequency + ",      \"TVUniqueID\": \"" + responseUniqueId + "\"    }  }}";
    }
}

