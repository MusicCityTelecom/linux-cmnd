package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.SearchParam;
import com.tpvision.smartinstall.dao.WelcomeRepository;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.core.UiCustomizations;
import com.tpvision.smartinstall.dao.core.Welcome;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.Utils;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WelcomeManager {
   private static final Logger LOG = LoggerFactory.getLogger(WelcomeManager.class);
   @Autowired
   private WelcomeRepository welcomeRepository;

   public JSONObject findWelcomesPageBySearchParam(SearchParam sp) {
      try {
         return JpaManager.findSimpleLikeDataPageBySearchParam("welcome", sp);
      } catch (SQLException ex) {
         LOG.error(ex.getMessage(), ex);
         return null;
      }
   }

   public List<Welcome> findWelcomesByPlatforms(String... platforms) {
      List<String> platformList = Arrays.asList(platforms);
      List<String> platformIdList = platformList.stream().map(PlatformUtils::getPlatformId).collect(Collectors.toList());
      return this.welcomeRepository.findByPlatformIn(platformIdList);
   }

   public Welcome loadByKey(int id) {
      return this.welcomeRepository.findById(id).orElse(null);
   }

   public List<Welcome> findWelcomesByType(int type) {
      return this.welcomeRepository.findByType(type);
   }

   public List<Welcome> findWelcomesByName(String name) {
      return this.welcomeRepository.findByName(name);
   }

   public void deleteByKey(int id) {
      this.welcomeRepository.deleteById(id);
      this.clearSettingLinkWhenDeleteWelcome(id);
   }

   private void clearSettingLinkWhenDeleteWelcome(int id) {
      SettingManager settingManager = JpaManager.getSettingManager();

      for (Setting set : settingManager.findSettingListByWelcomeId(id)) {
         set.setWelcomeId(-1);
         set.setLastUpdatedDate(new Date());
         settingManager.save(set);
      }
   }

   public List<Welcome> loadAll() {
      return this.welcomeRepository.findAll();
   }

   public Welcome copy(int id) {
      Welcome srcItem = this.loadByKey(id);
      Welcome newItem = new Welcome();
      newItem.setName("Copy of " + srcItem.getName());
      newItem.setPlatform(srcItem.getPlatform());
      newItem.setType(srcItem.getType());
      newItem.setLastEdit(TpvDateUtils.getCurrentIndentifierFormatTime());
      newItem.setCreatedBy(Utils.getAuthenticationName());
      this.save(newItem);
      File srcDir = new File(this.getPackageDataPath(srcItem.getId()));
      File destDir = new File(this.getPackageDataPath(newItem.getId()));
      destDir.mkdirs();

      try {
         FileUtils.copyDirectory(srcDir, destDir);
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }

      return newItem;
   }

   public String getPackageDataPath(int packageId) {
      return String.format(Locale.ENGLISH, "%sWelcome/%d/", CommonConstants.CLONE_PROCESS_LOCATION, packageId);
   }

   public Welcome save(Welcome bean) {
      if (!bean.isLastEditModified()) {
         bean.setLastEdit(TpvDateUtils.getCurrentIndentifierFormatTime());
      }

      this.welcomeRepository.save(bean);
      this.syncLastUpdate(bean.getId(), bean.getLastEdit());
      return bean;
   }

   private void syncLastUpdate(int id, String lastedit) {
      SettingManager settingManager = JpaManager.getSettingManager();

      for (Setting set : settingManager.findSettingListByWelcomeId(id)) {
         set.setLastUpdatedDate(new Date());
         settingManager.save(set);
         if (set.getUiCustomizationsId() > 0) {
            UiCustomizations ui = JpaManager.getUiCustomizationsManager().loadByKey(set.getUiCustomizationsId());
            if (ui != null) {
               ui.setLastEdit(lastedit);
               JpaManager.getUiCustomizationsManager().save(ui);
            }
         }
      }
   }
}
