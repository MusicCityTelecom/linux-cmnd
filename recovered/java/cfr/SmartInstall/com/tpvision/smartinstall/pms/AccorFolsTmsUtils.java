/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.pms;

import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.pms.TmsUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccorFolsTmsUtils
extends TmsUtils {
    private static final Logger LOG = LoggerFactory.getLogger(AccorFolsTmsUtils.class);
    public static final String CONFIG_NAME = "accor";
    private String pmsUrl = "https://flint.accor.com/%s/h%s/FlintService";
    private Object providerName;
    private Object hotelCode;

    @Override
    public void stop() {
    }

    @Override
    public void doStart() {
    }

    @Override
    protected void doLoadConfigs() {
        JSONObject pmsconfigs = PmsUtils.getPmsConfigs();
        if (pmsconfigs != null) {
            JSONObject config = pmsconfigs.optJSONObject(CONFIG_NAME);
            if (config == null) {
                LOG.error("get config failed");
                return;
            }
            this.providerName = config.optString("PROVIDER_NAME");
            this.hotelCode = config.optString("HOTEL_CODE");
        } else {
            LOG.error("get impala configs failed");
        }
    }

    @Override
    public void requestBill(String roomid) {
    }

    @Override
    public void refresh() {
    }

    @Override
    public void requestRefresh(String roomid) {
    }

    @Override
    public void requestExpressCheckout(String roomid) {
    }

    @Override
    public String isConnectedTms() {
        return null;
    }

    @Override
    public void updateMessageStatus(String msgId, PmsUtils.MessageStatus newStatus) {
    }

    @Override
    protected String getConfigName() {
        return CONFIG_NAME;
    }

    @Override
    public void responseWakeup(String roomId, String wakeupTime, String status) {
    }
}

