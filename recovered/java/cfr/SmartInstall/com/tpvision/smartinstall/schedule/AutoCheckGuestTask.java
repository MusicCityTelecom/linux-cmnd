/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.schedule;

import com.tpvision.smartinstall.dao.core.FutureCheckIn;
import com.tpvision.smartinstall.dao.core.GuestInfo;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.schedule.CmndMetricsTask;
import com.tpvision.smartinstall.util.TpvTimerTask;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoCheckGuestTask
extends TpvTimerTask {
    private static final Logger LOG = LoggerFactory.getLogger(AutoCheckGuestTask.class);

    @Override
    public void tryRun() {
        this.doAutoCheckIn();
        this.doAutoCheckout();
    }

    private void doAutoCheckIn() {
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        List<FutureCheckIn> checkedInGuestInfos = JpaManager.getFutureCheckInManager().findRequiredCheckInGuestInfo(currentTime);
        LOG.info("required checkin guest count=>{}", (Object)checkedInGuestInfos.size());
        for (FutureCheckIn futureCheckIn : checkedInGuestInfos) {
            try {
                String roomNo = futureCheckIn.getRoomid();
                String guestName = futureCheckIn.getGuestName();
                List<GuestInfo> guestInfoList = JpaManager.getGuestInfoManager().findGuestInfosByRoomid(roomNo);
                GuestInfo uncheckedGuestInfo = null;
                boolean isAlreadyChecked = false;
                for (GuestInfo info : guestInfoList) {
                    if ("Y".equalsIgnoreCase(info.getCheckin())) {
                        isAlreadyChecked = true;
                        break;
                    }
                    if (!StringUtils.equalsIgnoreCase(info.getGuestName(), guestName)) continue;
                    uncheckedGuestInfo = info;
                }
                if (isAlreadyChecked) {
                    LOG.warn("roomid <{}> already checked, skip handle this record", (Object)roomNo);
                    continue;
                }
                if (uncheckedGuestInfo == null) {
                    uncheckedGuestInfo = PmsUtils.createGuest(guestName, null, roomNo, futureCheckIn.getRoomType(), futureCheckIn.getGuestLanguage());
                }
                CmndMetricsTask.setPmsTypeName("Reception");
                PmsUtils.processCheckin(uncheckedGuestInfo.getGuestId(), futureCheckIn.getCheckoutTime());
                CmndMetricsTask.resetPmsTypeName();
                JpaManager.getFutureCheckInManager().deleteFutureCheckIn(futureCheckIn);
            }
            catch (Exception ex) {
                LOG.error(ex.getMessage(), ex);
                CmndMetricsTask.resetPmsTypeName();
            }
        }
    }

    private void doAutoCheckout() {
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        List<GuestInfo> expireGuestInfos = JpaManager.getGuestInfoManager().findGuestInfoByCheckoutTimeAndCheckInStatus(currentTime, "Y");
        LOG.info("required checkout guest count=>{}", (Object)expireGuestInfos.size());
        for (GuestInfo guestInfo : expireGuestInfos) {
            try {
                CmndMetricsTask.setPmsTypeName("Reception");
                PmsUtils.processCheckout(guestInfo.getRoomid());
                CmndMetricsTask.resetPmsTypeName();
            }
            catch (Exception ex) {
                LOG.error(ex.getMessage(), ex);
                CmndMetricsTask.resetPmsTypeName();
            }
        }
    }
}

