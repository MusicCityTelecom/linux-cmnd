/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.trigger;

import com.tpvision.smartinstall.dao.core.TriggerInfo;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.trigger.TriggerUtils;
import com.tpvision.smartinstall.util.TpvTimerTask;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TriggerProcessTask
extends TpvTimerTask {
    private static final Logger LOG = LoggerFactory.getLogger(TriggerProcessTask.class);

    @Override
    public void tryRun() {
        List<TriggerInfo> infos = JpaManager.getTriggerInfoManager().loadAll();
        for (TriggerInfo info : infos) {
            if (!info.getTriggerActive().equalsIgnoreCase(TriggerUtils.TriggerActiveState.Yes.name()) || !TriggerUtils.isTimerTrigger(info) || !TriggerUtils.isTimeForTrigger(info)) continue;
            LOG.info("execute time trigger:{},{}", (Object)info.getName(), (Object)info.getTriggerCondition());
            TriggerUtils.executeTriggerThread(info, null, null);
        }
    }
}

