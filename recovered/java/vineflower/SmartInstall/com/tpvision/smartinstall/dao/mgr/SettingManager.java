package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.SearchParam;
import com.tpvision.smartinstall.dao.SettingRepository;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.util.PlatformUtils;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class SettingManager {
   private static final Logger LOG = LoggerFactory.getLogger(SettingManager.class);
   @Autowired
   private SettingRepository settingRepository;

   public JSONObject findClonesPageBySearchParam(SearchParam sp) {
      try {
         return JpaManager.findSimpleLikeDataPageBySearchParam("settinginfo_view", sp);
      } catch (SQLException ex) {
         LOG.error(ex.getMessage(), ex);
         return null;
      }
   }

   public List<Setting> findSettingByIds(List<Integer> settingIdList) {
      return this.settingRepository.findByIdIn(settingIdList);
   }

   public List<Setting> findSettingsByPlatforms(String... platforms) {
      List<String> platformList = Arrays.asList(platforms);
      List<String> platformIdList = platformList.stream().map(PlatformUtils::getPlatformId).collect(Collectors.toList());
      return this.settingRepository.findByPlatformIn(platformIdList);
   }

   public List<Setting> findSettingListByLastUpdatedByOrderByLastUpdatedDateDesc(String username) {
      return this.settingRepository.findByLastUpdatedByOrderByLastUpdatedDateDesc(username);
   }

   public List<Setting> loadAllOrderByCreatedDateDesc() {
      Sort sort = Sort.by(Direction.DESC, "createdDate");
      return this.settingRepository.findAll(sort);
   }

   public List<Setting> loadAll() {
      return this.settingRepository.findAll();
   }

   public Setting loadByKey(int id) {
      return this.settingRepository.findById(id).orElse(null);
   }

   public List<Setting> findSettingsByNameStartingWith(String name) {
      return this.settingRepository.findByNameStartingWith(name);
   }

   public void deleteByKey(int id) {
      this.settingRepository.deleteById(id);
   }

   public List<Setting> findSettingsByName(String name) {
      return this.settingRepository.findByName(name);
   }

   public List<Setting> findSettingsByCloneRename(String cloneRename) {
      return this.settingRepository.findByClonerename(cloneRename);
   }

   public List<Setting> findSettingListBySettingPackageId(int settingPackageId) {
      return this.settingRepository.findBySettingPackageId(settingPackageId);
   }

   public List<Setting> findSettingListByAppPackageId(int appPackageId) {
      return this.settingRepository.findByAppPackageId(appPackageId);
   }

   public List<Setting> findSettingListByBannersId(int bannersId) {
      return this.settingRepository.findByBannersId(bannersId);
   }

   public List<Setting> findSettingListByUiCustomizationsId(int uiCustomizationsId) {
      return this.settingRepository.findByUiCustomizationsId(uiCustomizationsId);
   }

   public List<Setting> findSettingListByWelcomeId(int welcomeId) {
      return this.settingRepository.findByWelcomeId(welcomeId);
   }

   public List<Setting> findSettingListByScheduleId(int scheduleId) {
      return this.settingRepository.findByScheduleId(scheduleId);
   }

   public List<Setting> findSettingListByChannelPackageId(int channelPackageId) {
      return this.settingRepository.findByChannelPackageId(channelPackageId);
   }

   public void save(Setting obj) {
      this.settingRepository.save(obj);
   }
}
