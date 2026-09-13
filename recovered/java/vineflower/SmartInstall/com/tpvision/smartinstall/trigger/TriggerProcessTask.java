package com.tpvision.smartinstall.trigger;

import com.tpvision.smartinstall.dao.core.TriggerInfo;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.util.TpvTimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TriggerProcessTask extends TpvTimerTask {
   private static final Logger LOG = LoggerFactory.getLogger(TriggerProcessTask.class);

   @Override
   public void tryRun() {
      for (TriggerInfo info : JpaManager.getTriggerInfoManager().loadAll()) {
         if (info.getTriggerActive().equalsIgnoreCase(TriggerUtils.TriggerActiveState.Yes.name())
            && TriggerUtils.isTimerTrigger(info)
            && TriggerUtils.isTimeForTrigger(info)) {
            LOG.info("execute time trigger:{},{}", info.getName(), info.getTriggerCondition());
            TriggerUtils.executeTriggerThread(info, null, null);
         }
      }
   }
}
