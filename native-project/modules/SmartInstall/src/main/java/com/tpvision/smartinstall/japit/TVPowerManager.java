package com.tpvision.smartinstall.japit;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.mgr.DevicesManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
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
      String formatedState = formatPower(state);
      return formatedState.equalsIgnoreCase("REBOOT")
         ? "{    \"Svc\" : \"WebListeningServices\",   \"SvcVer\": \"1.0\",   \"Cookie\": "
            + JAPITUtils.getJapitRandomCookieValue()
            + ",   \"CmdType\": \""
            + "Change"
            + "\",   \"Fun\" : \"PowerService\",    \"CommandDetails\" :    {     \"PowerAction\": \"Reboot\"   }}"
         : "{ \t\"Svc\" : \"WebListeningServices\", \t\"SvcVer\": \"1.0\",\t\"Cookie\": "
            + JAPITUtils.getJapitRandomCookieValue()
            + ",  \"CmdType\": \""
            + "Change"
            + "\",\t\"Fun\" : \"PowerService\", \t\"CommandDetails\" : \t{\t\t\"ToPowerState\" : \""
            + formatedState
            + "\"\t}}";
   }

   public static String requestPowerCmd() {
      return "{\t\"Svc\" : \"WebListeningServices\",\t\"SvcVer\" : \"1.0\",\t\"Cookie\" : "
         + JAPITUtils.getJapitRandomCookieValue()
         + ",\t\"CmdType\" : \"Request\",\t\"Fun\" : \"PowerService\"}";
   }

   private static String formatPower(String power) {
      return power.equalsIgnoreCase("REBOOT") ? power : StringUtils.capitalize(power.toLowerCase());
   }

   private static List<Devices> findUpdatedPowerDevices(String tvOrGroupName, String type) {
      List<Devices> devicesList = new ArrayList<>();
      if (StringUtils.equalsIgnoreCase("tv", type)) {
         Devices tv = JpaManager.getDevicesManager().loadByKey(tvOrGroupName);
         devicesList.add(tv);
      } else if (StringUtils.equalsIgnoreCase("group", type)) {
         devicesList = JpaManager.getDevicesManager().findDevicesByGroupName(tvOrGroupName);
      }

      return devicesList;
   }

   public static String modifyTVPowerState(String tvOrGroupName, String type, String power) {
      List<Devices> devicesList = findUpdatedPowerDevices(tvOrGroupName, type);
      String status = null;
      DevicesManager iptvmanager = JpaManager.getDevicesManager();
      int deviceCount = devicesList.size();

      for (Devices iptv : devicesList) {
         try {
            if ("offline".equalsIgnoreCase(power)) {
               if (!PlatformUtils.isSupportWakeupOnLan(iptv.getType())) {
                  return Utils.buildFailReturnJson("The TV does not support wakeup by lan");
               }

               BroadcastUtils.broadcastWakeOnLanMagicPackageForAllNetworkInterfaces(iptv.getTvmacaddress());
               TVDiscoveryManager.getDevices(iptv.getTvipaddress());
               status = Utils.buildSuccessReturnJson("mac", iptv.getTvmacaddress());
            } else {
               String changePower = null;
               if (!JAPITUtils.isJapitListening(iptv)) {
                  changePower = "offline";
               } else {
                  if ("Transiting".equalsIgnoreCase(iptv.getSecureCmdSupport()) && deviceCount == 1) {
                     return Utils.buildFailReturnJson("unable to send request to TV, current TV is still transition status");
                  }

                  changePower = sendChangePowerStatusToTV(power, iptv);
                  if (changePower == null && deviceCount == 1) {
                     return Utils.buildFailReturnJson("modify powerstatus fail!");
                  }
               }

               if (changePower != null) {
                  iptv.setPowerstatus(changePower);
                  iptvmanager.save(iptv);
               }

               status = Utils.buildSuccessReturnJson("power", changePower);
            }
         } catch (Exception e) {
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

      String powerJapit = changePowerService(changePower);
      String response = sendJapitCommandWithExceptionIgnore(device, powerJapit);
      if (StringUtils.isEmpty(response)) {
         return power;
      }

      String returnPowerStatus = getTVPowerStatusFromResponse(response);
      if (StringUtils.isNotBlank(returnPowerStatus)) {
         changePower = returnPowerStatus;
      } else {
         Thread.sleep(2000L);
         response = sendJapitCommandWithExceptionIgnore(device, powerJapit);
         returnPowerStatus = getTVPowerStatusFromResponse(response);
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
      } catch (Exception ex) {
         return null;
      }
   }

   private static String getTVPowerStatusFromResponse(String response) {
      String returnPowerStatus = null;
      JSONObject responseJson = new JSONObject(response);
      JSONObject commandDetails = responseJson.optJSONObject("CommandDetails");
      if (commandDetails != null) {
         JSONObject powerServiceParameters = commandDetails.optJSONObject("PowerServiceParameters");
         if (powerServiceParameters != null) {
            returnPowerStatus = powerServiceParameters.optString("CurrentPowerState");
         }
      }

      return returnPowerStatus;
   }

   public static boolean wakeupTvOnLan(Devices tv) {
      boolean isWakeupSuccess = false;
      BroadcastUtils.broadcastWakeOnLanMagicPackageForAllNetworkInterfaces(tv.getTvmacaddress());
      int detectMaxTime = 5;
      long detectInterval = 1000L;

      for (int i = 0; i < detectMaxTime; i++) {
         if (JAPITUtils.isJapitListening(tv)) {
            TVDiscoveryManager.refreshTVInfo(tv.getTvipaddress());
            isWakeupSuccess = true;
            break;
         }

         try {
            Thread.sleep(detectInterval);
         } catch (InterruptedException var7) {
         }
      }

      return isWakeupSuccess;
   }
}
