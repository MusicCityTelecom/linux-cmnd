/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.japit;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.mgr.DevicesManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.japit.EnableVSecureKeyCmd;
import com.tpvision.smartinstall.japit.GetVSecureKeyCmd;
import com.tpvision.smartinstall.japit.JapitCommand;
import com.tpvision.smartinstall.util.JAPITUtils;
import com.tpvision.smartinstall.util.PlatformUtils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetVSecureKeyManager {
    private static final Logger logger = LoggerFactory.getLogger(GetVSecureKeyManager.class);
    private static final Map<String, Long> VS_TV_MAP = new ConcurrentHashMap<String, Long>();
    private static final long MIN_INTERVAL_SINGLE_TV = 600000L;

    private GetVSecureKeyManager() {
    }

    private static void parseReceiveVSecureKey(JSONObject commandDetails) {
        JSONObject vsecureData = commandDetails.optJSONObject("VSecureTVData");
        if (vsecureData == null) {
            logger.error("VSecureTVData is empty, just return");
            return;
        }
        String vsecureKey = vsecureData.optString("VSecureTVCertificate");
        String vsecureTvid = vsecureData.optString("VSecureTVIdentifier");
        if (vsecureKey == null) {
            logger.error("VSecureTVCertificate is empty, just return");
            return;
        }
        String tVUniqueID = commandDetails.getJSONObject("WebListeningServiceParameters").getString("TVUniqueID");
        DevicesManager iptvmanager = JpaManager.getDevicesManager();
        Devices iptv2 = iptvmanager.loadByKey(tVUniqueID);
        if (iptv2 != null) {
            iptv2.setVsecureKey(vsecureKey);
            iptv2.setVsecuretvid(vsecureTvid);
            iptvmanager.save(iptv2);
            logger.info("save tv:{} vSecureKey data success", (Object)tVUniqueID);
        } else {
            logger.error("tv:{} not exists, save vsecureKey failure", (Object)tVUniqueID);
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
        GetVSecureKeyManager.getSecureKeyDataFromTv(tv);
    }

    private static void getSecureKeyDataFromTv(Devices tv) {
        if (PlatformUtils.isSupportEnablerService(tv.getType())) {
            EnableVSecureKeyCmd enableVSecureCmd = new EnableVSecureKeyCmd(JapitCommand.CommandType.Change, JapitCommand.CommandSvc.WebListeningServices);
            JAPITUtils.sendAsyncCommandWithDelay(tv, enableVSecureCmd.generateCommand(), 20000L, (result, resultJson) -> {
                JSONObject webListeningServicesEnablerParameters;
                if (result && resultJson.has("WebListeningServicesEnablerParameters") && "On".equalsIgnoreCase((webListeningServicesEnablerParameters = resultJson.getJSONObject("WebListeningServicesEnablerParameters")).optString("ContentSecurityService"))) {
                    GetVSecureKeyManager.sendGetVSecureKeyCmdToHttp(tv);
                }
            });
        } else {
            GetVSecureKeyManager.sendGetVSecureKeyCmdToHttp(tv);
        }
    }

    private static void sendGetVSecureKeyCmdToHttp(Devices tv) {
        GetVSecureKeyCmd getVSecureCmd = new GetVSecureKeyCmd(JapitCommand.CommandType.Request, JapitCommand.CommandSvc.WebListeningServices);
        JAPITUtils.sendAsyncCommandWithDelay(tv, getVSecureCmd.generateCommand(), 30000L, (result, resultJson) -> {
            if (result) {
                GetVSecureKeyManager.parseReceiveVSecureKey(resultJson);
            }
        });
    }
}

