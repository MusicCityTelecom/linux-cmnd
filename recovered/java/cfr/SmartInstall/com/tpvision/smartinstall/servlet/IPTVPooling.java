/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.servlet;

import com.tpvision.smartinstall.servlet.PollingWebSocket;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IPTVPooling {
    private static final Logger LOG = LoggerFactory.getLogger(IPTVPooling.class);

    public static void notifyDeviceDataChange() {
        PollingWebSocket.notifyDeviceUpdate(IPTVPooling.assembleJSONReturnData("tv_changed", null));
    }

    public static void notifyUpgradeStatusChange(JSONObject upgradeResult) {
        PollingWebSocket.notifyDeviceUpdate(IPTVPooling.assembleJSONReturnData("upgrade_notice", upgradeResult));
    }

    public static void notifyUploadStatusChange(JSONObject uploadResult) {
        PollingWebSocket.notifyDeviceUpdate(IPTVPooling.assembleJSONReturnData("upload_notice", uploadResult));
    }

    private static JSONObject assembleJSONReturnData(String noticeType, JSONObject info) {
        JSONObject responseResult = new JSONObject();
        responseResult.put("type", noticeType);
        responseResult.put("detail", info);
        LOG.info("notifiy data:{}", (Object)responseResult);
        return responseResult;
    }
}

