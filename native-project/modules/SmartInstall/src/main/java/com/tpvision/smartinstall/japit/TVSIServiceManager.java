package com.tpvision.smartinstall.japit;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.util.JAPITUtils;
import com.tpvision.smartinstall.util.JSONObjectConverter;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TVSIServiceManager {
   private static final Logger LOG = LoggerFactory.getLogger(TVSIServiceManager.class);

   private TVSIServiceManager() {
   }

   private static String requestSIServiceCmd() {
      return "{\"Svc\" : \"WebListeningServices\",\"SvcVer\" : \"4.0\",\"Cookie\" : "
         + JAPITUtils.getJapitRandomCookieValue()
         + ",\"CmdType\" : \"Request\",\"Fun\" : \"SIService\"}";
   }

   public static String sendRequestSIServiceToTV(Devices device) {
      String japit = requestSIServiceCmd();

      try {
         String result = JAPITUtils.sendJapitCommand(device, japit);
         if (result.contains("FunCausedError")) {
            EnablerServiceCmd enablerServiceCmd = new EnablerServiceCmd(JapitCommand.CommandType.Change, JapitCommand.CommandSvc.WebListeningServices);
            enablerServiceCmd.setProfessionalSettingsService(true);
            JAPITUtils.sendJapitCommand(device, enablerServiceCmd.generateCommand());
            result = JAPITUtils.sendJapitCommand(device, japit);
         }

         JSONObject jsonObject = new JSONObject(result);
         extractDevicesInfo(jsonObject.getJSONObject("CommandDetails"), device);
         return result;
      } catch (Exception ex) {
         LOG.error("send si data failure", ex);
         return null;
      }
   }

   public static void extractDevicesInfo(JSONObject commandDetails, Devices device) {
      JSONObject professionalSettingsParameters = commandDetails.optJSONObject("ProfessionalSettingsParameters");
      if (professionalSettingsParameters == null) {
         LOG.warn("SIService ProfessionalSettingsParameters is null.");
      } else {
         device.setTvstatus(JSONObjectConverter.mergeObjects(device.getTvstatus(), professionalSettingsParameters));
         JSONObject controlTVOverIP = professionalSettingsParameters.optJSONObject("ControlTVOverIP");
         if (controlTVOverIP == null) {
            LOG.warn("SIService ProfessionalSettingsParameters>ControlTVOverIP is null.");
         } else {
            JSONObject webListeningServiceSettings = controlTVOverIP.optJSONObject("WebListeningServiceSettings");
            if (webListeningServiceSettings == null) {
               webListeningServiceSettings = controlTVOverIP.optJSONObject("WebListeningServiceParameters");
            }

            if (webListeningServiceSettings == null) {
               LOG.warn("SIService ProfessionalSettingsParameters>ControlTVOverIP>WebListeningServiceParameters is null.");
            } else {
               int port = webListeningServiceSettings.optInt("Port", 0);
               if (port > 0) {
                  device.setWlsPort(port);
               }

               int securePort = webListeningServiceSettings.optInt("SecurePort", 0);
               if (securePort > 0) {
                  device.setWlsSecurePort(securePort);
               }
            }
         }

         JSONObject powerSettings = professionalSettingsParameters.optJSONObject("PowerSettings");
         if (powerSettings != null) {
            String standbyMode = powerSettings.optString("StandbyMode");
            if (StringUtils.isNotBlank(standbyMode)) {
               device.setStandbyMode(standbyMode);
            }
         }

         JpaManager.getDevicesManager().save(device);
         LOG.info("SIService update tv:{} ", device.getTvuniqueid());
      }
   }
}
