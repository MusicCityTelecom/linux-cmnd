package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.SearchParam;
import com.tpvision.smartinstall.dao.ScheduleRepository;
import com.tpvision.smartinstall.dao.core.Schedule;
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
public class ScheduleManager {
   private static final Logger LOG = LoggerFactory.getLogger(ScheduleManager.class);
   @Autowired
   private ScheduleRepository scheduleRepository;

   public JSONObject findSchedulePageBySearchParam(SearchParam sp) {
      try {
         return JpaManager.findSimpleLikeDataPageBySearchParam("schedule", sp);
      } catch (SQLException ex) {
         LOG.error(ex.getMessage(), ex);
         return null;
      }
   }

   public List<Schedule> findSchedulesByPlatforms(String... platforms) {
      return this.scheduleRepository.findByPlatformIn(Arrays.asList(platforms));
   }

   public List<Schedule> findByName(String name) {
      return this.scheduleRepository.findByName(name);
   }

   public Schedule loadByKey(int id) {
      return this.scheduleRepository.findById(id).orElse(null);
   }

   public void deleteByKey(int id) {
      this.scheduleRepository.deleteById(id);
      this.clearSettingLinkWhenDeleteSchedule(id);
   }

   private void clearSettingLinkWhenDeleteSchedule(int id) {
      SettingManager settingManager = JpaManager.getSettingManager();

      for (Setting set : settingManager.findSettingListByScheduleId(id)) {
         set.setScheduleId(-1);
         set.setLastUpdatedDate(new Date());
         settingManager.save(set);
      }
   }

   public List<Schedule> loadAll() {
      return this.scheduleRepository.findAll();
   }

   public void save(Schedule obj) {
      if (!obj.isLastEditModified()) {
         obj.setLastEdit(TpvDateUtils.getCurrentIndentifierFormatTime());
      }

      this.scheduleRepository.save(obj);
      this.syncLastUpdate(obj.getId());
   }

   private void syncLastUpdate(int id) {
      SettingManager settingManager = JpaManager.getSettingManager();

      for (Setting set : settingManager.findSettingListByScheduleId(id)) {
         set.setLastUpdatedDate(new Date());
         settingManager.save(set);
      }
   }

   public Schedule copy(int id) {
      Schedule srcItem = this.loadByKey(id);
      Schedule newItem = new Schedule();
      newItem.setName("Copy of " + srcItem.getName());
      newItem.setPlatform(srcItem.getPlatform());
      newItem.setContent(srcItem.getContent());
      newItem.setSchedule(srcItem.getSchedule());
      newItem.setLastEdit(TpvDateUtils.getCurrentIndentifierFormatTime());
      this.save(newItem);
      File srcDir = new File(CloneItemUtils.getSchedulesPackageDataPath(srcItem.getId()));
      File destDir = new File(CloneItemUtils.getSchedulesPackageDataPath(newItem.getId()));
      destDir.mkdirs();

      try {
         FileUtils.copyDirectory(srcDir, destDir);
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }

      return newItem;
   }
}
