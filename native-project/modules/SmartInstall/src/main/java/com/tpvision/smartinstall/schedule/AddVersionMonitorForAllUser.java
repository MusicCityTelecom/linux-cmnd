package com.tpvision.smartinstall.schedule;

import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.util.UserEmailMonitorHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddVersionMonitorForAllUser extends Job {
   private static final Logger LOG = LoggerFactory.getLogger(AddVersionMonitorForAllUser.class);

   @Override
   public String description() {
      return "used to add version check monitor support for all users";
   }

   @Override
   public Job.ExecuteType getExecuteType() {
      return Job.ExecuteType.AUTOMATICALLY;
   }

   @Override
   public boolean isExecuteOnce() {
      return true;
   }

   @Override
   public void execute() {
      LOG.info("start to add monitor support tag to all users");
      JpaManager.getProfileManager().loadAll().stream().forEach(profile -> {
         if (profile.getMonitorConfig() != null) {
            List<String> monitorList = new ArrayList<>();
            monitorList.addAll(Arrays.asList(profile.getMonitorConfig().split(",")));
            monitorList.add(UserEmailMonitorHelper.MonitorEvents.CMND_AND_RECEPTION_VERSION_OUT_OF_DATE.name());
            monitorList.add(UserEmailMonitorHelper.MonitorEvents.TV_FIRMWARE_IS_OUT_OF_DATE.name());
            String newList = String.join(",", monitorList.toArray(new String[0]));
            profile.setMonitorConfig(newList);
            JpaManager.getProfileManager().save(profile);
         }
      });
      LOG.info("end handle handle monitor support tag to all users");
   }
}
