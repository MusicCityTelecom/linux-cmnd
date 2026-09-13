/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.japit;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.mgr.DevicesManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.japit.TVDiscoveryManager;
import com.tpvision.smartinstall.util.BroadcastUtils;
import com.tpvision.smartinstall.util.JAPITUtils;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.Utils;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TVPowerManager {
    private static final Logger LOG = LoggerFactory.getLogger(TVPowerManager.class);

    private TVPowerManager() {
    }

    public static String changePowerService(String state) {
        String formatedState = TVPowerManager.formatPower(state);
        if (formatedState.equalsIgnoreCase("REBOOT")) {
            return "{    \"Svc\" : \"WebListeningServices\",   \"SvcVer\": \"1.0\",   \"Cookie\": " + JAPITUtils.getJapitRandomCookieValue() + ",   \"CmdType\": \"" + "Change" + "\",   \"Fun\" : \"PowerService\",    \"CommandDetails\" :    {     \"PowerAction\": \"Reboot\"   }}";
        }
        return "{ \t\"Svc\" : \"WebListeningServices\", \t\"SvcVer\": \"1.0\",\t\"Cookie\": " + JAPITUtils.getJapitRandomCookieValue() + ",  \"CmdType\": \"" + "Change" + "\",\t\"Fun\" : \"PowerService\", \t\"CommandDetails\" : \t{\t\t\"ToPowerState\" : \"" + formatedState + "\"\t}}";
    }

    public static String requestPowerCmd() {
        return "{\t\"Svc\" : \"WebListeningServices\",\t\"SvcVer\" : \"1.0\",\t\"Cookie\" : " + JAPITUtils.getJapitRandomCookieValue() + ",\t\"CmdType\" : \"Request\",\t\"Fun\" : \"PowerService\"}";
    }

    private static String formatPower(String power) {
        if (power.equalsIgnoreCase("REBOOT")) {
            return power;
        }
        return StringUtils.capitalize(power.toLowerCase());
    }

    private static List<Devices> findUpdatedPowerDevices(String tvOrGroupName, String type) {
        List<Devices> devicesList = new ArrayList<Devices>();
        if (StringUtils.equalsIgnoreCase("tv", type)) {
            Devices tv = JpaManager.getDevicesManager().loadByKey(tvOrGroupName);
            devicesList.add(tv);
        } else if (StringUtils.equalsIgnoreCase("group", type)) {
            devicesList = JpaManager.getDevicesManager().findDevicesByGroupName(tvOrGroupName);
        }
        return devicesList;
    }

    public static String modifyTVPowerState(String tvOrGroupName, String type, String power) {
        List<Devices> devicesList = TVPowerManager.findUpdatedPowerDevices(tvOrGroupName, type);
        String status = null;
        DevicesManager iptvmanager = JpaManager.getDevicesManager();
        int deviceCount = devicesList.size();
        for (Devices iptv2 : devicesList) {
            try {
                if ("offline".equalsIgnoreCase(power)) {
                    if (!PlatformUtils.isSupportWakeupOnLan(iptv2.getType())) {
                        return Utils.buildFailReturnJson("The TV does not support wakeup by lan");
                    }
                    BroadcastUtils.broadcastWakeOnLanMagicPackageForAllNetworkInterfaces(iptv2.getTvmacaddress());
                    TVDiscoveryManager.getDevices(iptv2.getTvipaddress());
                    status = Utils.buildSuccessReturnJson("mac", iptv2.getTvmacaddress());
                    continue;
                }
                String changePower = null;
                if (!JAPITUtils.isJapitListening(iptv2)) {
                    changePower = "offline";
                } else {
                    if ("Transiting".equalsIgnoreCase(iptv2.getSecureCmdSupport()) && deviceCount == 1) {
                        return Utils.buildFailReturnJson("unable to send request to TV, current TV is still transition status");
                    }
                    changePower = TVPowerManager.sendChangePowerStatusToTV(power, iptv2);
                    if (changePower == null && deviceCount == 1) {
                        return Utils.buildFailReturnJson("modify powerstatus fail!");
                    }
                }
                if (changePower != null) {
                    iptv2.setPowerstatus(changePower);
                    iptvmanager.save(iptv2);
                }
                status = Utils.buildSuccessReturnJson("power", changePower);
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return status;
    }

    private static String sendChangePowerStatusToTV(String power, Devices device) throws InterruptedException {
        String changePower = null;
        if ("On".equalsIgnoreCase(power)) {
            changePower = "Standby";
        } else if ("Standby".equalsIgnoreCase(power)) {
            changePower = "On";
        } else if ("REBOOT".equalsIgnoreCase(power)) {
            changePower = "REBOOT";
        }
        String powerJapit = TVPowerManager.changePowerService(changePower);
        String response = TVPowerManager.sendJapitCommandWithExceptionIgnore(device, powerJapit);
        if (StringUtils.isEmpty(response)) {
            return power;
        }
        String returnPowerStatus = TVPowerManager.getTVPowerStatusFromResponse(response);
        if (StringUtils.isNotBlank(returnPowerStatus)) {
            changePower = returnPowerStatus;
        } else {
            Thread.sleep(2000L);
            response = TVPowerManager.sendJapitCommandWithExceptionIgnore(device, powerJapit);
            returnPowerStatus = TVPowerManager.getTVPowerStatusFromResponse(response);
            if (StringUtils.isNotBlank(returnPowerStatus)) {
                changePower = returnPowerStatus;
            } else {
                LOG.error("modify tv {} power status to {} fail, error::{}", device.getId(), changePower, response);
            }
        }
        return changePower;
    }

    public static String sendJapitCommandWithExceptionIgnore(Devices device, String data) {
        try {
            return JAPITUtils.sendJapitCommand(device, data, 20000);
        }
        catch (Exception ex) {
            return null;
        }
    }

    private static String getTVPowerStatusFromResponse(String response) {
        JSONObject powerServiceParameters;
        String returnPowerStatus = null;
        JSONObject responseJson = new JSONObject(response);
        JSONObject commandDetails = responseJson.optJSONObject("CommandDetails");
        if (commandDetails != null && (powerServiceParameters = commandDetails.optJSONObject("PowerServiceParameters")) != null) {
            returnPowerStatus = powerServiceParameters.optString("CurrentPowerState");
        }
        return returnPowerStatus;
    }

    public static boolean wakeupTvOnLan(Devices tv) {
        boolean isWakeupSuccess = false;
        BroadcastUtils.broadcastWakeOnLanMagicPackageForAllNetworkInterfaces(tv.getTvmacaddress());
        int detectMaxTime = 5;
        long detectInterval = 1000L;
        for (int i = 0; i < detectMaxTime; ++i) {
            if (JAPITUtils.isJapitListening(tv)) {
                TVDiscoveryManager.refreshTVInfo(tv.getTvipaddress());
                isWakeupSuccess = true;
                break;
            }
            try {
                Thread.sleep(detectInterval);
                continue;
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
        return isWakeupSuccess;
    }
}

