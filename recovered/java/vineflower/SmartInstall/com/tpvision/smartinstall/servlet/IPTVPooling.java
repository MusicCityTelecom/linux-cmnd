package com.tpvision.smartinstall.servlet;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IPTVPooling {
   private static final Logger LOG = LoggerFactory.getLogger(IPTVPooling.class);

   public static void notifyDeviceDataChange() {
      PollingWebSocket.notifyDeviceUpdate(assembleJSONReturnData("tv_changed", null));
   }

   public static void notifyUpgradeStatusChange(JSONObject upgradeResult) {
      PollingWebSocket.notifyDeviceUpdate(assembleJSONReturnData("upgrade_notice", upgradeResult));
   }

   public static void notifyUploadStatusChange(JSONObject uploadResult) {
      PollingWebSocket.notifyDeviceUpdate(assembleJSONReturnData("upload_notice", uploadResult));
   }

   private static JSONObject assembleJSONReturnData(String noticeType, JSONObject info) {
      JSONObject responseResult = new JSONObject();
      responseResult.put("type", noticeType);
      responseResult.put("detail", info);
      LOG.info("notifiy data:{}", responseResult);
      return responseResult;
   }
}
