package com.tpvision.smartinstall.schedule;

import com.google.gson.Gson;
import com.tpvision.smartinstall.core.SettingChannelBean;
import com.tpvision.smartinstall.dao.core.Schedule;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.core.SettingPackage;
import com.tpvision.smartinstall.dao.core.UiCustomizations;
import com.tpvision.smartinstall.dao.core.Welcome;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.UploadCloneUtils;
import com.tpvision.smartinstall.util.WelcomeLogoUtils;
import java.io.File;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FixCloneInfoDataJob extends Job {
   private static final Logger LOG = LoggerFactory.getLogger(FixCloneInfoDataJob.class);

   @Override
   public String description() {
      return "used to fix the lost of settings/welcome/ui/schdule during upgrade from CMND6 to CMND7";
   }

   @Override
   public Job.ExecuteType getExecuteType() {
      return Job.ExecuteType.BYHAND;
   }

   @Override
   public boolean isExecuteOnce() {
      return false;
   }

   @Override
   public void execute() {
      LOG.info("start to handle clone info");
      List<Setting> settings = JpaManager.getSettingManager().loadAll();
      LOG.info("get setting list count => {}", settings.size());
      settings.forEach(this::hanldleSetting);
      LOG.info("end handle clone info data");
   }

   private void hanldleSetting(Setting setting) {
      LOG.info("start to handle setting[{}]", setting.getId());
      if (!"N".equalsIgnoreCase(setting.getIsdelete())) {
         LOG.warn("is deltelteStatus is not N ,current is {}", setting.getIsdelete());
      } else {
         File cloneFileDir = this.getCloneProcessDirectory(setting);
         if (cloneFileDir != null) {
            String platformId = setting.getPlatform();
            this.handleSettingPackage(setting, platformId);
            this.handleWelcomeLog(setting, platformId);
            this.handleUiCustomization(setting, platformId);
            this.handleSchedule(setting, platformId);
         }
      }
   }

   private void handleSchedule(Setting setting, String platformId) {
      if ("TPS191HE_CloneData".equals(platformId)) {
         LOG.info("platform is 2k19 es, skip schedule handle");
      } else {
         int scheduleId = setting.getScheduleId();
         if (scheduleId != 0) {
            LOG.info("schedule id is {}, skip handle", scheduleId);
         } else {
            List<Schedule> schedules = JpaManager.getScheduleManager().findByName(setting.getName());
            if (!schedules.isEmpty()) {
               LOG.info("schedule with name:{} already exists", setting.getName());
            } else {
               Schedule schedule = UploadCloneUtils.saveScheduleToDB(this.getSettingProcessPath(setting), setting.getName(), platformId);
               if (schedule != null) {
                  setting.setScheduleId(schedule.getId());
                  this.saveSettingData(setting);
               }
            }
         }
      }
   }

   private void handleUiCustomization(Setting setting, String platformId) {
      int uiCustomizationsId = setting.getUiCustomizationsId();
      if (uiCustomizationsId != 0) {
         LOG.info("UiCustomizations id is {}, skip handle", uiCustomizationsId);
      } else {
         List<UiCustomizations> uiCustomizationList = JpaManager.getUiCustomizationsManager().findByName(setting.getName());
         if (!uiCustomizationList.isEmpty()) {
            LOG.info("uiCustomization with name:{} already exists", setting.getName());
         } else {
            UiCustomizations uiCustomizations = UploadCloneUtils.loadConfToUiCustomizationsDb(
               this.getSettingProcessPath(setting), setting.getName(), platformId
            );
            if (uiCustomizations != null) {
               setting.setUiCustomizationsId(uiCustomizations.getId());
               this.saveSettingData(setting);
            }
         }
      }
   }

   private void handleWelcomeLog(Setting setting, String platformId) {
      int welcomeId = setting.getWelcomeId();
      if (welcomeId != 0) {
         LOG.info("welcomeId is {}, skip handle", welcomeId);
      } else {
         List<Welcome> welcomes = JpaManager.getWelcomeManager().findWelcomesByName(setting.getName());
         if (!welcomes.isEmpty()) {
            LOG.info("welcome with name:{} already exists", setting.getName());
         } else {
            String userName = setting.getCreatedBy();
            String configName = setting.getName();

            try {
               welcomeId = WelcomeLogoUtils.getInstance()
                  .saveWelcomeToDb(platformId, CommonConstants.CLONE_PROCESS_LOCATION + configName, configName, userName);
               if (welcomeId > 0) {
                  setting.setWelcomeId(welcomeId);
                  this.saveSettingData(setting);
               }
            } catch (Exception e) {
               LOG.warn(e.getMessage());
            }
         }
      }
   }

   private void handleSettingPackage(Setting setting, String platformId) {
      int settingPackageId = setting.getSettingPackageId();
      if (settingPackageId != 0) {
         LOG.info("setting package id is {}, skip handle", settingPackageId);
      } else {
         List<SettingPackage> settingPackages = JpaManager.getSettingPackageManager().findByName(setting.getName());
         if (!settingPackages.isEmpty()) {
            LOG.info("settingPackage with name:{} already exists", setting.getName());
         } else {
            SettingChannelBean settingChannelBean = this.getSettingChannelBeanByStringValue(setting.getValue());
            if (settingChannelBean == null || settingChannelBean.getSetttings() == null || settingChannelBean.getSetttings().getSetting().isEmpty()) {
               try {
                  settingChannelBean = UploadCloneUtils.getSettingChannelBean(this.getSettingProcessPath(setting), platformId);
               } catch (Exception ex) {
                  LOG.error(ex.getMessage(), ex);
                  LOG.warn("get tv setting failure, return;");
                  return;
               }
            }

            SettingPackage settingPackage = UploadCloneUtils.loadConfToSettingPackageDb(
               this.getSettingProcessPath(setting), setting.getName(), settingChannelBean, platformId
            );
            setting.setSettingPackageId(settingPackage.getId());
            this.saveSettingData(setting);
         }
      }
   }

   private SettingChannelBean getSettingChannelBeanByStringValue(String value) {
      try {
         return new Gson().fromJson(value, SettingChannelBean.class);
      } catch (Exception ex) {
         return null;
      }
   }

   private void saveSettingData(Setting setting) {
      JpaManager.getSettingManager().save(setting);
   }

   private String getSettingProcessPath(Setting setting) {
      return CommonConstants.CLONE_PROCESS_LOCATION + setting.getName() + File.separator + setting.getPlatform();
   }

   private File getCloneProcessDirectory(Setting setting) {
      String settingName = setting.getName();
      File file = new File(CommonConstants.CLONE_PROCESS_LOCATION + settingName);
      if (file.exists() && file.isDirectory()) {
         return file;
      }

      LOG.warn("{} clone file is missing", file.getAbsolutePath());
      return null;
   }
}
