/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.schedule;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.mgr.DevicesManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.JAPITUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.TpvTimerTask;
import com.tpvision.smartinstall.util.UserEmailMonitorHelper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DetectOfflineTask
extends TpvTimerTask {
    private static final Logger LOG = LoggerFactory.getLogger(DetectOfflineTask.class);

    @Override
    public void tryRun() {
        LOG.info("DetectOfflineTask schedule run");
        DevicesManager iptvmanager = JpaManager.getDevicesManager();
        for (Devices device : iptvmanager.loadAll()) {
            if (device.isRFDevice() || !device.isOnline() || !this.isExceedThirtyMinites(device.getLastonline())) continue;
            int validPort = JAPITUtils.getValidJapitPort(device);
            if (validPort == -1) {
                this.handleOfflineDevice(device, iptvmanager);
                continue;
            }
            this.checkDeviceSecureCmdStatus(validPort, device, iptvmanager);
        }
    }

    private void checkDeviceSecureCmdStatus(int validPort, Devices device, DevicesManager iptvmanager) {
        boolean isSecureMode;
        if ("Transiting".equalsIgnoreCase(device.getSecureCmdSupport())) {
            LOG.warn("skip to check secure mode as device {} is in transiting status", (Object)device.getTvuniqueid());
            return;
        }
        boolean bl = isSecureMode = device.getWlsSecurePort() == validPort || CommonConstants.TV_WIXP_HTTPS_PORTS.contains(validPort);
        if (!device.getSecureCmdSupport().equalsIgnoreCase(String.valueOf(isSecureMode))) {
            LOG.info("fix secure mode to {} for device : {}", (Object)isSecureMode, (Object)device.getTvuniqueid());
            device.setSecureCmdSupport(String.valueOf(isSecureMode));
            iptvmanager.save(device);
        }
    }

    private void handleOfflineDevice(Devices device, DevicesManager iptvmanager) {
        LOG.info("offline tv:{}", (Object)device.getId());
        device.setPowerstatus("offline");
        iptvmanager.save(device);
        if (StringUtils.equalsIgnoreCase(device.getStandbyMode(), "Green")) {
            LOG.info("skip power Green mode TV email notice");
        } else {
            UserEmailMonitorHelper.sendTVIsOfflineNotice(device.getTvroomid());
        }
    }

    private boolean isExceedThirtyMinites(String startDate) {
        boolean is = false;
        try {
            SimpleDateFormat format = TpvDateUtils.getSimpleDateFormatWithEnglishLocale("yyyy/MM/dd-HH:mm:ss");
            Date d1 = format.parse(startDate);
            long diff = System.currentTimeMillis() - d1.getTime();
            return diff / 60000L > 30L;
        }
        catch (ParseException e) {
            LOG.error(e.getMessage(), e);
            return is;
        }
    }
}

