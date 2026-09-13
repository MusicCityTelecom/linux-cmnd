package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.SearchParam;
import com.tpvision.smartinstall.dao.SettingPackageRepository;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.core.SettingPackage;
import com.tpvision.smartinstall.util.TpvDateUtils;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingPackageManager {
   private static final Logger LOG = LoggerFactory.getLogger(SettingPackageManager.class);
   @Autowired
   private SettingPackageRepository settingPackageRepository;

   public JSONObject findSettingPackagePageBySearchParam(SearchParam sp) {
      try {
         return JpaManager.findSimpleLikeDataPageBySearchParam("settingPackage", sp);
      } catch (SQLException ex) {
         LOG.error(ex.getMessage(), ex);
         return null;
      }
   }

   public List<SettingPackage> findSettingPackagesByPlatforms(String... platforms) {
      return this.settingPackageRepository.findByPlatformIn(Arrays.asList(platforms));
   }

   public SettingPackage loadByKey(int id) {
      return this.settingPackageRepository.findById(id).orElse(null);
   }

   public List<SettingPackage> findByName(String name) {
      return this.settingPackageRepository.findByName(name);
   }

   public List<SettingPackage> loadAll() {
      return this.settingPackageRepository.findAll();
   }

   public void deleteByKey(int id) {
      this.settingPackageRepository.deleteById(id);
      this.clearSettingLinkWhenDeleteSettingPackage(id);
   }

   private void clearSettingLinkWhenDeleteSettingPackage(int id) {
      SettingManager settingManager = JpaManager.getSettingManager();

      for (Setting set : settingManager.findSettingListBySettingPackageId(id)) {
         set.setSettingPackageId(-1);
         set.setLastUpdatedDate(new Date());
         settingManager.save(set);
      }
   }

   public void save(SettingPackage obj) {
      if (!obj.isLastEditModified()) {
         obj.setLastEdit(TpvDateUtils.getCurrentIndentifierFormatTime());
      }

      this.settingPackageRepository.save(obj);
      this.syncLastUpdate(obj.getId());
   }

   private void syncLastUpdate(int id) {
      SettingManager settingManager = JpaManager.getSettingManager();

      for (Setting set : settingManager.findSettingListBySettingPackageId(id)) {
         set.setLastUpdatedDate(new Date());
         settingManager.save(set);
      }
   }

   public SettingPackage copy(int id) {
      SettingPackage settingPackage = this.loadByKey(id);
      SettingPackage newItem = new SettingPackage();
      newItem.setName("copy of " + settingPackage.getName());
      newItem.setPlatform(settingPackage.getPlatform());
      newItem.setValue(settingPackage.getValue());
      newItem.setLastEdit(TpvDateUtils.getCurrentIndentifierFormatTime());
      this.save(newItem);
      return newItem;
   }
}
