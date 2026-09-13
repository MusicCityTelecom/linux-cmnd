package com.tpvision.smartinstall.schedule;

import com.tpvision.smartinstall.VersionChecker;
import com.tpvision.smartinstall.util.TpvTimerTask;
import com.tpvision.smartinstall.util.UserEmailMonitorHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoVersionChecktTask extends TpvTimerTask {
   private static final Logger LOG = LoggerFactory.getLogger(AutoVersionChecktTask.class);

   @Override
   public void tryRun() {
      LOG.info("start to do daily CMND/Reception/Firmware version check");
      VersionChecker.loadLatestVersionInfoFromRemoteJson();
      UserEmailMonitorHelper.sendCmndOrReceptionVersionOutOfDateNotice();
      UserEmailMonitorHelper.sendTvFirmwareOutOfDateNotice();
      LOG.info("end version check");
   }
}
