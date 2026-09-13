/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.pms;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.WakeupInfo;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.WakeupInfoManager;
import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.util.TpvTimerTask;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WakeupAlarmTask
extends TpvTimerTask {
    private static final Logger LOG = LoggerFactory.getLogger(WakeupAlarmTask.class);

    @Override
    public void tryRun() {
        WakeupInfoManager wakeupInfoManager = JpaManager.getWakeupInfoManager();
        List<String> roomidList = wakeupInfoManager.getRoomidList();
        WakeupInfo wakeupInfo = null;
        for (int i = 0; i < roomidList.size(); ++i) {
            wakeupInfo = wakeupInfoManager.getNearestWakeupInfo(roomidList.get(i));
            if (wakeupInfo != null && this.setAlarmToTV(wakeupInfo.getRoomId(), wakeupInfo.getWakeupTime())) {
                wakeupInfoManager.updateStatus(wakeupInfo.getId(), 1);
            }
            PmsUtils.responseWakeupStatus(roomidList.get(i), false);
        }
    }

    private boolean setAlarmToTV(String roomId, String wakeupTime) {
        List<Devices> devices = PmsUtils.getTVsForRoom(roomId);
        if (devices.isEmpty()) {
            LOG.error("RoomId:{} No Found TV", (Object)roomId);
            return false;
        }
        boolean flag = true;
        for (Devices tv : devices) {
            if (PmsUtils.updatePmsAlarmTime(tv, wakeupTime.split(" ")[1])) {
                LOG.info("Set Alarm To TV Pass:{},{}", (Object)tv.getId(), (Object)wakeupTime);
                continue;
            }
            LOG.error("Set Alarm To TV Fail:{},{}", (Object)tv.getId(), (Object)wakeupTime);
            flag = false;
        }
        return flag;
    }
}

