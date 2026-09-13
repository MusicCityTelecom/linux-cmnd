package com.tpvision.smartinstall.schedule;

import com.tpvision.smartinstall.util.CastServerUtils;
import com.tpvision.smartinstall.util.TpvTimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SynCastAnalyticalDataTask extends TpvTimerTask {
   private static final Logger LOG = LoggerFactory.getLogger(SynCastAnalyticalDataTask.class);

   @Override
   public void tryRun() {
      LOG.info("SynCastAnalyticalDataTask start..");
      CastServerUtils.startSyncAnalyticalData();
      LOG.info("SynCastAnalyticalDataTask end");
   }
}
