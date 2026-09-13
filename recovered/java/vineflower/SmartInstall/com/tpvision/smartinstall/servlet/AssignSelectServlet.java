package com.tpvision.smartinstall.servlet;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tpvision.smartinstall.dao.core.AppPackage;
import com.tpvision.smartinstall.dao.core.Banners;
import com.tpvision.smartinstall.dao.core.ChannelPackage;
import com.tpvision.smartinstall.dao.core.Schedule;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.core.SettingPackage;
import com.tpvision.smartinstall.dao.core.UiCustomizations;
import com.tpvision.smartinstall.dao.core.UpgSetting;
import com.tpvision.smartinstall.dao.core.Welcome;
import com.tpvision.smartinstall.dao.mgr.AppPackageManager;
import com.tpvision.smartinstall.dao.mgr.BannersManager;
import com.tpvision.smartinstall.dao.mgr.ChannelPackageManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.ScheduleManager;
import com.tpvision.smartinstall.dao.mgr.SettingManager;
import com.tpvision.smartinstall.dao.mgr.SettingPackageManager;
import com.tpvision.smartinstall.dao.mgr.UiCustomizationsManager;
import com.tpvision.smartinstall.dao.mgr.UpgSettingManager;
import com.tpvision.smartinstall.dao.mgr.WelcomeManager;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.IPUpgradeManager;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/assignSelect")
public class AssignSelectServlet extends BaseHttpServlet {
   private static final long serialVersionUID = 1L;
   private static final Logger LOG = LoggerFactory.getLogger(AssignSelectServlet.class);

   @Override
   protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String data = "{\"status\":\"success\",\"data\":\"nodata\"}";
      String selectType = request.getParameter("selectType");
      String platforms = request.getParameter("platforms");
      String versions = request.getParameter("versions");
      String tvGroups = request.getParameter("tvGroups");
      switch (selectType) {
         case "getUpgradeTypeList":
            data = this.getUpgradeTypeList();
            break;
         case "Firmware":
            data = this.getFirmwareList(platforms, versions, tvGroups);
            break;
         case "Clone":
            data = this.getCloneList(platforms);
            break;
         case "AllSelectNone":
            data = this.allSelectNone(request);
            break;
         case "Settings":
            data = this.getSettingsList(platforms);
            break;
         case "Channels":
            data = this.getChannelsList(platforms);
            break;
         case "Apps":
            data = this.getAppsList(platforms);
            break;
         case "Content":
            data = this.getContentList();
            break;
         case "Banners":
            data = this.getBannersList(platforms);
            break;
         case "UI":
            data = this.getUIList(platforms);
            break;
         case "Schedules":
            data = this.getSchedulesList(platforms);
            break;
         case "Welcome":
            data = this.getWelcomeList(platforms);
            break;
         default:
            LOG.error("not support type");
      }

      Utils.writeToResponse(data, "text/html;charset=UTF-8", response);
   }

   private String getUpgradeTypeList() {
      String data = "";
      StringBuilder upgradeListData = new StringBuilder();
      int originTypeLen = IPUpgradeManager.ORIGIN_UPGRADE_TYPE.length;
      int singleTypeLen = IPUpgradeManager.SINGLE_UPGRADE_TYPE.length;

      for (int i = 0; i < originTypeLen; i++) {
         upgradeListData.append(IPUpgradeManager.ORIGIN_UPGRADE_TYPE[i]);
         upgradeListData.append(",");
      }

      for (int j = 0; j < singleTypeLen; j++) {
         upgradeListData.append(IPUpgradeManager.SINGLE_UPGRADE_TYPE[j]);
         upgradeListData.append(",");
      }

      if (upgradeListData.length() > 0) {
         data = "{\"status\":\"success\",\"data\":\"" + upgradeListData.toString().substring(0, upgradeListData.toString().length() - 1) + "\"}";
      }

      return data;
   }

   private <T> JSONArray getArray(List<T> itemList, BiConsumer<T, JSONObject> func) {
      JSONArray array = new JSONArray();

      for (T item : itemList) {
         JSONObject obj = new JSONObject();
         func.accept(item, obj);
         array.put(obj);
      }

      return array;
   }

   private String getFirmwareList(String platforms, String versions, String tvGroups) {
      UpgSettingManager upgMgr = JpaManager.getUpgSettingManager();
      List<UpgSetting> upgSettings = StringUtils.isEmpty(platforms) ? upgMgr.loadAll() : upgMgr.findUpgSettingsByPlatforms(platforms.split(","));
      List<UpgSetting> filterUpgSettings = upgSettings;
      if (StringUtils.isNotBlank(versions)) {
         filterUpgSettings = this.filterLowerVersionByVersions(upgSettings, versions);
      } else if (StringUtils.isNotBlank(tvGroups)) {
         filterUpgSettings = this.filterLowerVersionByTvGroups(upgSettings, tvGroups);
      }

      JSONArray array = this.getArray(filterUpgSettings, (t, u) -> {
         u.put("Id", t.getId());
         u.put("Name", t.getUpgrename());
         u.put("Platform", PlatformUtils.getPlatformName(t.getPlatform()));
         u.put("Version", t.getIpversion());
      });
      return this.successStatus(array);
   }

   private List<UpgSetting> filterLowerVersionByTvGroups(List<UpgSetting> upgSettings, String tvGroups) {
      List<UpgSetting> list = new ArrayList<>();
      if (StringUtils.isBlank(tvGroups)) {
         return upgSettings;
      }

      JsonArray tvGroupsArray = JsonParser.parseString(tvGroups).getAsJsonArray();
      if (tvGroupsArray.isEmpty()) {
         return upgSettings;
      }

      List<String> tvGroupNames = new ArrayList<>();

      for (int i = 0; i < tvGroupsArray.size(); i++) {
         JsonElement element = tvGroupsArray.get(i);
         JsonObject jsonObject = element.getAsJsonObject();
         String platform = jsonObject.get("platform").getAsString();
         String tvGroupName = jsonObject.get("tvGroupName").getAsString();
         if (PlatformUtils.isNeedCheckLowerVersion(platform)) {
            tvGroupNames.add(tvGroupName);
         }
      }

      JsonArray jsonArray = JpaManager.getDevicesManager().getDeviceJsonDataByTvGroup(String.join(",", tvGroupNames));
      String maxVersion = this.getNeedCheckMaxVersion(jsonArray);
      if (StringUtils.isEmpty(maxVersion)) {
         return upgSettings;
      }

      for (UpgSetting upgSetting : upgSettings) {
         if (!PlatformUtils.isNeedCheckLowerVersion(upgSetting.getPlatform())) {
            list.add(upgSetting);
         } else {
            String settingVersion = upgSetting.getIpversion();
            if (!PlatformUtils.compareLowerVersion(settingVersion, maxVersion)) {
               list.add(upgSetting);
            }
         }
      }

      return list;
   }

   private List<UpgSetting> filterLowerVersionByVersions(List<UpgSetting> upgSettings, String versions) {
      List<UpgSetting> list = new ArrayList<>();
      if (!CollectionUtils.isEmpty(upgSettings) && !StringUtils.isEmpty(versions)) {
         JsonArray jsonArray = JsonParser.parseString(versions).getAsJsonArray();
         if (jsonArray.isEmpty()) {
            return upgSettings;
         }

         String maxVersion = this.getNeedCheckMaxVersion(jsonArray);
         if (StringUtils.isEmpty(maxVersion)) {
            return upgSettings;
         }

         for (UpgSetting upgSetting : upgSettings) {
            if (!PlatformUtils.isNeedCheckLowerVersion(upgSetting.getPlatform())) {
               list.add(upgSetting);
            } else {
               String settingVersion = upgSetting.getIpversion();
               if (!PlatformUtils.compareLowerVersion(settingVersion, maxVersion)) {
                  list.add(upgSetting);
               }
            }
         }

         return list;
      } else {
         return upgSettings;
      }
   }

   private String getNeedCheckMaxVersion(JsonArray jsonArray) {
      String maxVersion = "";

      for (int i = 0; i < jsonArray.size(); i++) {
         JsonElement element = jsonArray.get(i);
         JsonObject jsonObject = element.getAsJsonObject();
         String platform = jsonObject.get("platform").getAsString();
         String version = jsonObject.get("version").getAsString();
         if (PlatformUtils.isNeedCheckLowerVersion(platform)) {
            if (StringUtils.isBlank(maxVersion)) {
               maxVersion = version;
            } else {
               boolean result = PlatformUtils.compareLowerVersion(maxVersion, version);
               if (result) {
                  maxVersion = version;
               }
            }
         }
      }

      return maxVersion;
   }

   private String getCloneList(String platforms) {
      SettingManager upgMgr = JpaManager.getSettingManager();
      List<Setting> upgSettings = StringUtils.isEmpty(platforms) ? upgMgr.loadAll() : upgMgr.findSettingsByPlatforms(platforms.split(","));
      JSONArray array = this.getArray(upgSettings, (t, u) -> {
         u.put("Id", t.getId());
         u.put("Name", t.getClonerename());
         u.put("Platform", PlatformUtils.getPlatformName(t.getPlatform()));
      });
      return this.successStatus(array);
   }

   private String allSelectNone(HttpServletRequest request) {
      String data = "{\"status\":\"success\",\"data\":\"nodata\"}";
      String cloneids = request.getParameter("cloneids");
      if (null != cloneids && !"".equals(cloneids)) {
         String[] cloneidsArr = cloneids.split(",");
         int cloneidsLen = cloneidsArr.length;
         SettingManager setMgr = JpaManager.getSettingManager();
         Setting setting = null;

         for (int i = 0; i < cloneidsLen; i++) {
            String cloneId = cloneidsArr[i];
            setting = setMgr.loadByKey(Integer.valueOf(cloneId));
            if (null != setting) {
               setting.setSettingPackageId(-1);
               setting.setChannelPackageId(-1);
               setting.setAppPackageId(-1);
               setting.setBannersId(-1);
               setting.setUiCustomizationsId(-1);
               setting.setScheduleId(-1);
               setting.setWelcomeId(-1);
               setting.setContent("");
               setMgr.save(setting);
            }
         }
      }

      return data;
   }

   private String getSettingsList(String platforms) {
      SettingPackageManager upgMgr = JpaManager.getSettingPackageManager();
      List<SettingPackage> upgSettings = StringUtils.isEmpty(platforms) ? upgMgr.loadAll() : upgMgr.findSettingPackagesByPlatforms(platforms.split(","));
      JSONArray array = this.getArray(upgSettings, (t, u) -> {
         u.put("Id", t.getId());
         u.put("Name", t.getName());
         u.put("Platform", t.getPlatform());
      });
      return this.successStatus(array);
   }

   private String getChannelsList(String platforms) {
      ChannelPackageManager upgMgr = JpaManager.getChannelPackageManager();
      List<ChannelPackage> upgSettings = StringUtils.isEmpty(platforms) ? upgMgr.loadAll() : upgMgr.findChannelPackagesByPlatforms(platforms.split(","));
      JSONArray array = this.getArray(upgSettings, (t, u) -> {
         u.put("Id", t.getId());
         u.put("Name", t.getName());
         u.put("Platform", t.getPlatform());
         u.put("NumberOfChs", t.getNumberOfChs());
      });
      return this.successStatus(array);
   }

   private String getAppsList(String platforms) {
      AppPackageManager upgMgr = JpaManager.getAppPackageManager();
      List<AppPackage> upgSettings = StringUtils.isEmpty(platforms) ? upgMgr.loadAll() : upgMgr.findAppPackageByPlatforms(platforms.split(","));
      JSONArray array = this.getArray(upgSettings, (t, u) -> {
         u.put("Id", t.getId());
         u.put("Name", t.getName());
         u.put("Platform", t.getPlatform());
         u.put("Number", t.getNumber());
         u.put("Size", t.getSize());
      });
      return this.successStatus(array);
   }

   private String getContentList() {
      String data = "";
      AssignContentServlet assignContent = new AssignContentServlet();
      String contentData = assignContent.getThumbnailList();
      return "{\"status\":\"success\",\"data\":" + contentData + "}";
   }

   private String getBannersList(String platforms) {
      JSONArray array = new JSONArray();
      BannersManager upgMgr = JpaManager.getBannersManager();
      if (StringUtils.isBlank(platforms)) {
         LOG.info("platforms is empty,load all upg setting!");
         List<Banners> upgSettings = upgMgr.loadAll();
         array = this.getArray(upgSettings, (t, u) -> {
            u.put("Id", t.getId());
            u.put("Name", t.getName());
            u.put("Type", t.getType());
         });
      } else {
         String[] platformArray = platforms.split(",");

         for (String platformName : platformArray) {
            LOG.info("platform Name : {}", platformName);
            if (StringUtils.isNotBlank(platformName) && PlatformUtils.getSupportCloneItems(platformName).contains(CommonConstants.CloneItemType.Banner)) {
               List<Banners> upgSettings = upgMgr.findByPlatform(platformName);
               array = this.getArray(upgSettings, (t, u) -> {
                  u.put("Id", t.getId());
                  u.put("Name", t.getName());
                  u.put("Type", t.getType());
               });
            }
         }
      }

      return this.successStatus(array);
   }

   private String getUIList(String platforms) {
      UiCustomizationsManager mgr = JpaManager.getUiCustomizationsManager();
      List<UiCustomizations> itemList = StringUtils.isEmpty(platforms) ? mgr.loadAll() : mgr.findUiCustomizationsByPlatforms(platforms.split(","));
      JSONArray array = this.getArray(itemList, (t, u) -> {
         u.put("Id", t.getId());
         u.put("Name", t.getName());
         u.put("Platform", t.getPlatform());
      });
      return this.successStatus(array);
   }

   private String getSchedulesList(String platforms) {
      ScheduleManager mgr = JpaManager.getScheduleManager();
      List<Schedule> itemList = StringUtils.isEmpty(platforms) ? mgr.loadAll() : mgr.findSchedulesByPlatforms(platforms.split(","));
      JSONArray array = this.getArray(itemList, (t, u) -> {
         u.put("Id", t.getId());
         u.put("Name", t.getName());
         u.put("Platform", t.getPlatform());
      });
      return this.successStatus(array);
   }

   private String getWelcomeList(String platforms) {
      WelcomeManager mgr = JpaManager.getWelcomeManager();
      List<Welcome> itemList = StringUtils.isEmpty(platforms) ? mgr.loadAll() : mgr.findWelcomesByPlatforms(platforms.split(","));
      JSONArray array = this.getArray(itemList, (t, u) -> {
         u.put("Id", t.getId());
         u.put("Name", t.getName());
         u.put("Platform", PlatformUtils.getPlatformName(t.getPlatform()));
      });
      return this.successStatus(array);
   }
}
