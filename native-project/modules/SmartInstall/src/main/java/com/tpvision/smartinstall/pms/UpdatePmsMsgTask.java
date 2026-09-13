package com.tpvision.smartinstall.pms;

import com.tpvision.smartinstall.dao.core.Message;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.MessageManager;
import com.tpvision.smartinstall.schedule.CmndMetricsTask;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.TpvTimerTask;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdatePmsMsgTask extends TpvTimerTask {
   private static final Logger LOG = LoggerFactory.getLogger(UpdatePmsMsgTask.class);

   @Override
   public void tryRun() {
      if (!PmsUtils.isPmsMessagesEnabled()) {
         LOG.info("pms messages disabled,exit");
      } else {
         MessageManager mm = JpaManager.getMessageManager();
         Date curTime = new Date();

         try {
            for (Message m : mm.loadAll()) {
               String sendTimeStr = m.getTimeSend();
               Date sendTime = TpvDateUtils.parseMessageDate(sendTimeStr);
               if (curTime.after(sendTime) && "N".equalsIgnoreCase(m.getIsSent())) {
                  CmndMetricsTask.setPmsTypeName("CMND");
                  PmsUtils.sendMsg2TV(m.getGuestIds());
                  CmndMetricsTask.resetPmsTypeName();
               }
            }
         } catch (NumberFormatException e) {
            LOG.error(e.getMessage(), e);
            CmndMetricsTask.resetPmsTypeName();
         }
      }
   }
}
