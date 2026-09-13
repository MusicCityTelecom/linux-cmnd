package com.tpvision.smartinstall.japit;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.mgr.DevicesManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.util.JAPITUtils;
import com.tpvision.smartinstall.util.PlatformUtils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetVSecureKeyManager {
   private static final Logger logger = LoggerFactory.getLogger(GetVSecureKeyManager.class);
   private static final Map<String, Long> VS_TV_MAP = new ConcurrentHashMap<>();
   private static final long MIN_INTERVAL_SINGLE_TV = 600000L;

   private GetVSecureKeyManager() {
   }

   private static void parseReceiveVSecureKey(JSONObject commandDetails) {
      JSONObject vsecureData = commandDetails.optJSONObject("VSecureTVData");
      if (vsecureData == null) {
         logger.error("VSecureTVData is empty, just return");
      } else {
         String vsecureKey = vsecureData.optString("VSecureTVCertificate");
         String vsecureTvid = vsecureData.optString("VSecureTVIdentifier");
         if (vsecureKey == null) {
            logger.error("VSecureTVCertificate is empty, just return");
         } else {
            String tVUniqueID = commandDetails.getJSONObject("WebListeningServiceParameters").getString("TVUniqueID");
            DevicesManager iptvmanager = JpaManager.getDevicesManager();
            Devices iptv = iptvmanager.loadByKey(tVUniqueID);
            if (iptv != null) {
               iptv.setVsecureKey(vsecureKey);
               iptv.setVsecuretvid(vsecureTvid);
               iptvmanager.save(iptv);
               logger.info("save tv:{} vSecureKey data success", tVUniqueID);
            } else {
               logger.error("tv:{} not exists, save vsecureKey failure", tVUniqueID);
            }
         }
      }
   }

   public static void getVSecureKeyFromTv(Devices tv) {
      if (VS_TV_MAP.containsKey(tv.getId())) {
         Long lastTime = VS_TV_MAP.get(tv.getId());
         if (System.currentTimeMillis() - lastTime < 600000L) {
            logger.info("getVSecureKey exit last request send recently!!");
            return;
         }
      }

      VS_TV_MAP.put(tv.getId(), System.currentTimeMillis());
      logger.info("send getVSecureKey request");
      getSecureKeyDataFromTv(tv);
   }

   private static void getSecureKeyDataFromTv(Devices tv) {
      if (PlatformUtils.isSupportEnablerService(tv.getType())) {
         EnableVSecureKeyCmd enableVSecureCmd = new EnableVSecureKeyCmd(JapitCommand.CommandType.Change, JapitCommand.CommandSvc.WebListeningServices);
         JAPITUtils.sendAsyncCommandWithDelay(tv, enableVSecureCmd.generateCommand(), 20000L, (result, resultJson) -> {
            if (result && resultJson.has("WebListeningServicesEnablerParameters")) {
               JSONObject webListeningServicesEnablerParameters = resultJson.getJSONObject("WebListeningServicesEnablerParameters");
               if ("On".equalsIgnoreCase(webListeningServicesEnablerParameters.optString("ContentSecurityService"))) {
                  sendGetVSecureKeyCmdToHttp(tv);
               }
            }
         });
      } else {
         sendGetVSecureKeyCmdToHttp(tv);
      }
   }

   private static void sendGetVSecureKeyCmdToHttp(Devices tv) {
      GetVSecureKeyCmd getVSecureCmd = new GetVSecureKeyCmd(JapitCommand.CommandType.Request, JapitCommand.CommandSvc.WebListeningServices);
      JAPITUtils.sendAsyncCommandWithDelay(tv, getVSecureCmd.generateCommand(), 30000L, (result, resultJson) -> {
         if (result) {
            parseReceiveVSecureKey(resultJson);
         }
      });
   }
}
