package com.tpvision.smartinstall.servlet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IPProfileConfig {
   private static final Logger LOG = LoggerFactory.getLogger(IPProfileConfig.class);
   Map<String, String> configMap = new HashMap<>();

   public boolean isValidConfigName(String configName) {
      try {
         IPProfileConfig.IPProfileConfigName.valueOf(configName);
         return true;
      } catch (Exception e) {
         return false;
      }
   }

   public void updateConfig(IPProfileConfig.IPProfileConfigName configName, String value) {
      this.configMap.put(configName.name(), value);
   }

   public void updateConfigs(Map<String, String> params) {
      for (Entry<String, String> entry : params.entrySet()) {
         if (this.isValidConfigName(entry.getKey())) {
            this.configMap.put(entry.getKey(), entry.getValue());
         }
      }
   }

   public String getConfig(IPProfileConfig.IPProfileConfigName configName) {
      return this.configMap.get(configName.name());
   }

   public String getConfigsJson() {
      return new Gson().toJson(this.configMap);
   }

   public static IPProfileConfig fromJson(String json) {
      IPProfileConfig ipProfileConfig = getDefaultIPProfileConfig();
      if (json != null && !json.isEmpty()) {
         try {
            JSONObject jsObj = new JSONObject(json);
            if (jsObj.has("configMap")) {
               String configs = jsObj.getJSONObject("configMap").toString();
               Map<String, String> retMap = new Gson().fromJson(configs, (new TypeToken<Map<String, String>>() {}).getType());
               ipProfileConfig.updateConfigs(retMap);
            }
         } catch (JSONException e) {
            LOG.error(e.getMessage(), e);
         }

         return ipProfileConfig;
      } else {
         return ipProfileConfig;
      }
   }

   private static IPProfileConfig getDefaultIPProfileConfig() {
      IPProfileConfig ipProfileConfig = new IPProfileConfig();

      for (IPProfileConfig.IPProfileConfigName configName : IPProfileConfig.IPProfileConfigName.values()) {
         if (configName.name().endsWith("_dropdownText")) {
            ipProfileConfig.updateConfig(configName, "10");
         } else if (configName.name().endsWith("_page")) {
            ipProfileConfig.updateConfig(configName, "1");
         } else {
            ipProfileConfig.updateConfig(configName, "");
         }
      }

      return ipProfileConfig;
   }

   public enum IPProfileConfigName {
      TabIndex,
      PMS_tabsIndex,
      PMS_tabsRooms_sort,
      PMS_tabsRooms_search,
      PMS_tabsRooms_dropdownText,
      PMS_tabsRooms_page,
      TVs_tabsIndex,
      PMS_tabsMsg_msgfilter1,
      PMS_tabsMsg_sort,
      PMS_tabsMsg_search,
      PMS_tabsMsg_dropdownText,
      PMS_tabsMsg_page,
      TVs_tabsDevices_tvStatusOverview,
      TVs_tabsDevices_gridDevices_sort,
      TVs_tabsDevices_gridDevices_search,
      TVs_tabsDevices_gridDevices_dropdownText,
      TVs_tabsDevices_gridDevices_checkBox,
      TVs_tabsDevices_gridDevices_page,
      TVs_CurrentExpanded,
      TVs_tabsGroups_gridDevices_g_sort,
      TVs_tabsGroups_gridDevices_g_search,
      TVs_tabsGroups_gridDevices_g_dropdownText,
      TVs_tabsGroups_gridDevices_g_checkBox,
      TVs_tabsGroups_gridDevices_g_page,
      TVGroups_CurrentExpanded,
      Files_tabsPlay_sort,
      Files_tabsPlay_search,
      Files_tabsPlay_dropdownText,
      Files_tabsPlay_checkBox,
      Files_tabsPlay_page,
      Files_tabsIndex,
      Files_tabsFirmware_sort,
      Files_tabsFirmware_search,
      Files_tabsFirmware_dropdownText,
      Files_tabsFirmware_checkBox,
      Files_tabsFirmware_page,
      Files_tabsClone_sort,
      Files_tabsClone_search,
      Files_tabsClone_dropdownText,
      Files_tabsClone_checkBox,
      Files_tabsClone_page,
      TVSettings_activeTab,
      TVSettings_selectedMenu,
      Files_tabsSettingPackage_sort,
      Files_tabsSettingPackage_search,
      Files_tabsSettingPackage_dropdownText,
      Files_tabsSettingPackage_checkBox,
      Files_tabsSettingPackage_page,
      Files_tabsChannelPackage_sort,
      Files_tabsChannelPackage_search,
      Files_tabsChannelPackage_dropdownText,
      Files_tabsChannelPackage_checkBox,
      Files_tabsChannelPackage_page,
      Files_tabsChannels_sort_mode,
      Files_tabsApp_sort,
      Files_tabsApp_search,
      Files_tabsApp_dropdownText,
      Files_tabsApp_checkBox,
      Files_tabsApp_page,
      Files_tabsWelcome_sort,
      Files_tabsWelcome_search,
      Files_tabsWelcome_dropdownText,
      Files_tabsWelcome_checkBox,
      Files_tabsWelcome_page,
      Files_tabsTriggers_sort,
      Files_tabsTriggers_search,
      Files_tabsTriggers_dropdownText,
      Files_tabsTriggers_checkBox,
      Files_tabsTriggers_page,
      Files_tabsUI_sort,
      Files_tabsUI_search,
      Files_tabsUI_dropdownText,
      Files_tabsUI_checkBox,
      Files_tabsUI_page,
      Files_tabsUI_editor,
      Files_tabsBanners_sort,
      Files_tabsBanners_search,
      Files_tabsBanners_dropdownText,
      Files_tabsBanners_checkBox,
      Files_tabsBanners_page,
      Files_tabsSchedule_sort,
      Files_tabsSchedule_search,
      Files_tabsSchedule_dropdownText,
      Files_tabsSchedule_checkBox,
      Files_tabsSchedule_page,
      Admin_tabsIndex,
      Admin_tabsList_sort,
      Admin_tabsList_search,
      Admin_tabsList_dropdownText,
      Admin_tabsList_page,
      Admin_tabsLog_sort,
      Admin_tabsLog_search,
      Admin_tabsLog_dropdownText,
      Admin_tabsLog_page;
   }
}
