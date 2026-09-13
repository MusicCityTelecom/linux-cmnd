package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.SearchParam;
import com.tpvision.smartinstall.dao.AppPackageRepository;
import com.tpvision.smartinstall.dao.core.AppPackage;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppPackageManager {
   private static final Logger LOG = LoggerFactory.getLogger(AppPackageManager.class);
   @Autowired
   private AppPackageRepository appPackageRepository;

   public JSONObject findAppPackagePageBySearchParam(SearchParam sp) {
      try {
         return JpaManager.findSimpleLikeDataPageBySearchParam("apppackage", sp);
      } catch (SQLException ex) {
         LOG.error(ex.getMessage(), ex);
         return null;
      }
   }

   public List<AppPackage> findAppPackageByPlatforms(String... platformList) {
      return this.appPackageRepository.findByPlatformIn(Arrays.asList(platformList));
   }

   public List<AppPackage> findByName(String name) {
      return this.appPackageRepository.findByName(name);
   }

   public AppPackage loadByKey(int id) {
      return this.appPackageRepository.findById(id).orElse(null);
   }

   public void deleteByKey(int id) {
      this.appPackageRepository.deleteById(id);
      this.clearSettingLinkWhenDeleteAppPackage(id);
   }

   private void clearSettingLinkWhenDeleteAppPackage(int id) {
      SettingManager settingManager = JpaManager.getSettingManager();

      for (Setting set : settingManager.findSettingListByAppPackageId(id)) {
         set.setAndroidApps("");
         set.setAppPackageId(-1);
         settingManager.save(set);
      }
   }

   public List<AppPackage> loadAll() {
      return this.appPackageRepository.findAll();
   }

   public void save(AppPackage appPackage) {
      if (!appPackage.isLastEditModified()) {
         appPackage.setLastEdit(TpvDateUtils.getCurrentIndentifierFormatTime());
      }

      this.appPackageRepository.save(appPackage);
      this.updateSettingData(appPackage);
   }

   private void updateSettingData(AppPackage appPackage) {
      SettingManager settingManager = JpaManager.getSettingManager();

      for (Setting set : settingManager.findSettingListByAppPackageId(appPackage.getId())) {
         set.setAndroidApps(appPackage.getValue());
         set.setLastUpdatedDate(new Date());
         settingManager.save(set);
      }
   }

   public AppPackage copy(int id) {
      AppPackage appPackage = this.loadByKey(id);
      AppPackage newItem = new AppPackage();
      newItem.setName("Copy of " + appPackage.getName());
      newItem.setPlatform(appPackage.getPlatform());
      newItem.setNumber(appPackage.getNumber());
      newItem.setLastEdit(TpvDateUtils.getCurrentIndentifierFormatTime());
      newItem.setValue(appPackage.getValue());
      newItem.setSize(appPackage.getSize());
      this.save(newItem);
      File srcDirApps = new File(CloneItemUtils.getAppPackageDataPath(appPackage.getId()));
      File destDirApps = new File(CloneItemUtils.getAppPackageDataPath(newItem.getId()));
      destDirApps.mkdirs();

      try {
         FileUtils.copyDirectory(srcDirApps, destDirApps);
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }

      return newItem;
   }
}
