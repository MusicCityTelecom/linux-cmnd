package com.tpvision.smartinstall.dao.mgr;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tpvision.smartinstall.VersionChecker;
import com.tpvision.smartinstall.api.ApiLicenseChecker;
import com.tpvision.smartinstall.dao.DevicesRepository;
import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.Groups;
import com.tpvision.smartinstall.dao.core.UpgSetting;
import com.tpvision.smartinstall.schedule.CmndMetricsTask;
import com.tpvision.smartinstall.servlet.IPProfile;
import com.tpvision.smartinstall.servlet.IPTVPooling;
import com.tpvision.smartinstall.trigger.TriggerUtils;
import com.tpvision.smartinstall.util.CastServerUtils;
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.IPUpgradeManager;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

@Service
public class DevicesManager {
   private static final Logger logger = LoggerFactory.getLogger(DevicesManager.class);
   @Autowired
   private DevicesRepository devicesRepository;

   public Devices loadByKey(String id) {
      return this.devicesRepository.findById(id).orElse(null);
   }

   public boolean isExistForUnqiueId(String tvUnqiueId) {
      return this.devicesRepository.existsById(tvUnqiueId);
   }

   public void deleteByKey(String id) {
      Devices tv = this.devicesRepository.findById(id).orElse(null);
      if (tv != null) {
         IPUpgradeManager.cleanExpiredCloneFiles(tv, null);
         this.devicesRepository.deleteById(id);
         ApiLicenseChecker.getInstance().submitDeviceCountToLicenseServer();
         IPTVPooling.notifyDeviceDataChange();
         CastServerUtils.forwardDeviceRoomListToPpdsCastServer();
      }
   }

   public void deleteTV(Devices tv) {
      this.devicesRepository.delete(tv);
   }

   public Devices findDeviceByTVUniqueID(String tvUniqueId) {
      return this.devicesRepository.findByTvuniqueid(tvUniqueId);
   }

   public List<Devices> findDevicesByCloneId(int cloneId, String cloneType) {
      return this.devicesRepository.findByCloneidAndCloneType(cloneId, cloneType);
   }

   public List<Devices> findDevicesByType(String type) {
      return this.devicesRepository.findByType(type);
   }

   public List<Devices> findDevicesByTvipaddress(String tvipaddress) {
      return this.devicesRepository.findByTvipaddress(tvipaddress);
   }

   public List<Devices> findDevicesByTvipaddressAndCloneMode(String tvipaddress, String cloneMode) {
      return this.devicesRepository.findByTvipaddressAndCloneMode(tvipaddress, cloneMode);
   }

   public List<Devices> findDevicesByTvipaddressAndPowerStatus(String tvipaddress, String powerStatus) {
      return this.devicesRepository.findByTvipaddressAndPowerstatus(tvipaddress, powerStatus);
   }

   public List<Devices> findDevicesByTvname(String tvname) {
      return this.devicesRepository.findByTvname(tvname);
   }

   public List<Devices> findDevicesByClonePath(String clonePath) {
      return this.devicesRepository.findByClonePath(clonePath);
   }

   public List<Devices> findDevicesByRoomIdAndType(String tvroomid, String type) {
      return this.devicesRepository.findByTvroomidAndType(tvroomid, type);
   }

   public List<Devices> findDevicesByRoomId(String tvroomid) {
      return this.devicesRepository.findByTvroomid(tvroomid);
   }

   public List<Devices> findDevicesByFirmwareId(Integer firmwareId) {
      return this.devicesRepository.findByFirmwareid(firmwareId);
   }

   public List<Devices> loadAll() {
      return this.devicesRepository.findAll();
   }

   public List<Devices> findDevicesByGroupName(String groupName) {
      return this.devicesRepository.findDevicesByGroupName(groupName);
   }

   public List<Map<String, Object>> findDeviceTypeAndFirmwareInfoByRooms(Set<String> roomIds) {
      return this.devicesRepository.findDeviceTypeAndFirmwareInfoByRooms(roomIds);
   }

   public void save(Devices obj) {
      boolean isCreateNewDevice = obj.isCreateNewDevice();
      this.devicesRepository.save(obj);
      if (isCreateNewDevice || obj.isNeedRefresh()) {
         IPTVPooling.notifyDeviceDataChange();
      }

      if (isCreateNewDevice || obj.isNeedWriteMetricsLog()) {
         CmndMetricsTask.writeIptvInfoToMetricsLog(obj);
      }

      if (isCreateNewDevice) {
         TriggerUtils.executeNewDeviceTrigger(obj);
         ApiLicenseChecker.getInstance().submitDeviceCountToLicenseServer();
         CastServerUtils.forwardDeviceRoomListToPpdsCastServer();
      }

      if (!isCreateNewDevice && obj.isForwardToCastServer()) {
         CastServerUtils.forwardDeviceRoomListToPpdsCastServer();
      }
   }

   @Transactional
   public void updatePmsSyncNewStatus(Devices tv) {
      this.devicesRepository.updateDevicePmsSyncStatus(tv.getPmsSyncStatus(), tv.getId());
   }

   public String getDevicesChartOverviewData(String mode) {
      String field = "";
      switch (mode.toUpperCase(Locale.ROOT)) {
         case "GETTVMODELSOVERVIEW":
            field = "TVModelNumber";
            break;
         case "GETCURRENTPOWERSTATUSOVERVIEW":
            field = "PowerStatus";
            break;
         case "GETCURRENTSWOVERVIEW":
            field = "tv_firmware_Identifier";
            break;
         case "GETCURRENTCLONEDATAOVERVIEW":
            field = "tv_clone_Identifiers";
            break;
         case "GETCURRENTUPGRADESTATUSOVERVIEW":
            field = "Progress";
            break;
         default:
            logger.info("unsupport case for Overview filter");
      }

      String rowData = "";
      String sql = "select " + field + ", count(1) as counts from devices group by " + field;
      boolean cloneDataFlag = false;
      if ("tv_clone_Identifiers".equals(field)) {
         cloneDataFlag = true;
         sql = "select tv_clone_Identifiers, count(tv_clone_Identifiers) as knownclonedatecount from devices where Status = 'Successful' group by tv_clone_Identifiers";
         sql = sql
            + " union all select 'Unknown', count(*) as knownclonedatecount from devices where Status = 'Failure' or Status is null or tv_clone_Identifiers is null ";
      } else if ("Progress".equals(field)) {
         field = "colors";
         sql = "select count(colors) counts, colors from  (select fw_color colors from  smartinstall.devices Union all select clone_color from  smartinstall.devices) t group by colors order by  case when colors='black' then 1 when colors='blue' then 2 when colors='#FFBF00' then 3\twhen colors='#01DF01' then 4 when colors='red' then 5 end;";
      } else if ("PowerStatus".equals(field)) {
         sql = "select "
            + field
            + ", count(1) as counts, (select count(*) from devices where TVIPAddress = 'RF') as RFcounts from devices where TVIPAddress <> 'RF' group by "
            + field;
      }

      List<String> array = new ArrayList<>();

      try (
         Connection conn = JpaManager.getConnection();
         Statement stat = conn.createStatement();
         ResultSet rs = stat.executeQuery(sql);
      ) {
         ResultSetMetaData metaData = rs.getMetaData();
         int columnCount = metaData.getColumnCount();
         if (cloneDataFlag) {
            String temp_rowData = "";
            String lastclonerename = "";
            int knownCloneDataCount = 0;

            while (rs.next()) {
               lastclonerename = rs.getString(metaData.getColumnLabel(1));
               knownCloneDataCount = rs.getInt(metaData.getColumnLabel(2));
               if (knownCloneDataCount != 0) {
                  logger.debug("knownCloneDataCount={},lastclonerename={}", knownCloneDataCount, lastclonerename);
                  if (StringUtils.isNotBlank(lastclonerename)) {
                     temp_rowData = temp_rowData
                        + "{\"c\":[{\"v\":\""
                        + lastclonerename
                        + "("
                        + knownCloneDataCount
                        + ")\",\"f\":null},{\"v\":"
                        + knownCloneDataCount
                        + ",\"f\":null}]}\r\n,";
                  }
               }
            }

            if (!"".equals(temp_rowData)) {
               array.add(temp_rowData.substring(0, temp_rowData.length() - 1));
            }
         } else {
            while (rs.next()) {
               String result = null;
               String count = null;

               for (int i = 1; i <= columnCount; i++) {
                  String columnName = metaData.getColumnLabel(i);
                  if (field.equalsIgnoreCase(columnName)) {
                     result = this.fixShowValue(mode, rs.getString(columnName));
                  } else if ("counts".equalsIgnoreCase(columnName)) {
                     count = rs.getString(columnName);
                  }
               }

               rowData = "{\"c\":[{\"v\":\"" + result + "(" + count + ")\",\"f\":null},{\"v\":" + count + ",\"f\":null}]}\r\n";
               array.add(rowData);
            }
         }
      } catch (SQLException e) {
         logger.error(e.getMessage(), e);
      }

      return "{\r\n  \"cols\": [\r\n        {\"id\":\"\",\"label\":\"Topping\",\"pattern\":\"\",\"type\":\"string\"},\r\n        {\"id\":\"\",\"label\":\"Slices\",\"pattern\":\"\",\"type\":\"number\"}\r\n      ],\r\n  \"rows\": "
         + array.toString()
         + " }";
   }

   private String fixShowValue(String mode, String result) {
      if (mode.equalsIgnoreCase("getCurrentPowerStatusOverview")) {
         if (result.equalsIgnoreCase("offline")) {
            return "Offline";
         }

         if (result.equalsIgnoreCase("ON")) {
            return "On";
         }
      } else if (mode.equalsIgnoreCase("getCurrentUpgradeStatusOverview")) {
         return IPUpgradeManager.getUpgradeStatusByColor(result);
      }

      return result;
   }

   public JSONArray findPlayoutTvGroupsData() {
      String sql = "select group_concat(distinct tvroomid) as roomIds,type from devices group by type";
      JSONArray jsonArray = null;

      try {
         jsonArray = JpaManager.getResultsetAsArray(sql);
      } catch (SQLException e) {
         logger.error("select playout tv failed", e);
      }

      return jsonArray;
   }

   public String findDeviceIdsByRoomId(String roomId, String tvgroups) {
      String sql = "select group_concat(id) as tvs from deviceinfo_view where cast(tvroomid as unsigned)=cast(? as unsigned)";
      if (tvgroups != null) {
         sql = sql + " and TVGroups=?";
      }

      String result = null;

      try (
         Connection conn = JpaManager.getConnection();
         PreparedStatement stat = conn.prepareStatement(sql);
      ) {
         stat.setString(1, roomId);
         if (tvgroups != null) {
            stat.setNString(2, tvgroups);
         }

         try (ResultSet rs = stat.executeQuery()) {
            if (rs.next()) {
               result = rs.getString(1);
            }

            return result;
         }
      } catch (SQLException e) {
         logger.error(e.getMessage(), e);
         return null;
      }
   }

   private boolean getPowerStatus(ResultSet rs) throws SQLException {
      String id = rs.getString("id");
      Devices device = this.loadByKey(id);
      return null != device && "offline".equalsIgnoreCase(device.getPowerstatus());
   }

   public JSONArray findDeviceInfoViewDataByTvId(String tvId) throws SQLException {
      String sql = "select * from deviceinfo_view where Id=?";
      return JpaManager.getResultsetAsArray(sql, tvId);
   }

   public JSONArray getDeviceJsonData(String sql, List<Object> paramsList, String filter) {
      long maxOnlineExpireMilSecs = Utils.getMaxOnlineExpireMilSecs();
      JSONArray array = new JSONArray();

      try (
         Connection conn = JpaManager.getConnection();
         PreparedStatement stat = conn.prepareStatement(sql);
      ) {
         for (int i = 1; i <= paramsList.size(); i++) {
            stat.setObject(i, paramsList.get(i - 1));
         }

         try (ResultSet rs = stat.executeQuery()) {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
               JSONObject jsonObj = new JSONObject();
               if (!"hide".equalsIgnoreCase(filter) || !this.getPowerStatus(rs)) {
                  String type = rs.getString("Type");

                  for (int i = 1; i <= columnCount; i++) {
                     String columnName = metaData.getColumnLabel(i);
                     String value = rs.getString(columnName);
                     if (!columnName.equalsIgnoreCase("remotecontrol_status") && !columnName.equalsIgnoreCase("TVStatus")) {
                        if ("tv_clone_Identifiers".equalsIgnoreCase(columnName)) {
                           if (StringUtils.isNotBlank(value)) {
                              value = this.maxTvCloneIdent(value, type);
                           } else {
                              value = "None";
                           }
                        } else if ("tv_firmware_Identifier".equalsIgnoreCase(columnName)) {
                           if (StringUtils.isBlank(value)) {
                              value = "None";
                           }
                        } else if (value == null) {
                           value = "None";
                        }

                        jsonObj.put(columnName, HtmlUtils.htmlEscape(value));
                     } else {
                        if (StringUtils.isBlank(value)) {
                           value = "None";
                        }

                        jsonObj.put(columnName, value);
                     }

                     if (columnName.equalsIgnoreCase("Id")) {
                        List<Groups> groups = JpaManager.getGroupsManager().findGroupsByTvid(value);
                        StringBuilder groupNamesBuilder = new StringBuilder();

                        for (int j = 0; j < groups.size(); j++) {
                           if (j > 0) {
                              groupNamesBuilder.append(",");
                           }

                           groupNamesBuilder.append(groups.get(j).getGroupname());
                        }

                        jsonObj.put("Groups", groupNamesBuilder.length() == 0 ? "None" : groupNamesBuilder.toString());
                     }
                  }

                  String fwInfo = "None";
                  if (rs.getInt("FirmwareId") > 0 && rs.getString("si_firmware_Identifier") != null) {
                     UpgSetting upgSet = null;
                     UpgSettingManager upgMgr = JpaManager.getUpgSettingManager();
                     upgSet = upgMgr.loadByKey(Integer.parseInt(rs.getString("FirmwareId")));
                     if (null != upgSet) {
                        fwInfo = upgSet.getIpversion();
                     } else {
                        fwInfo = rs.getString("si_firmware_Identifier");
                     }
                  } else if (rs.getString("tv_firmware_Identifier") != null) {
                     fwInfo = rs.getString("tv_firmware_Identifier");
                  }

                  jsonObj.put("showFwInfo", fwInfo);
                  String showCloneInfo = "Unknown";
                  String lastCloneRename = rs.getString("LastCloneRename");
                  String status = rs.getString("Status");
                  String cloneColor = rs.getString("clone_color");
                  if (("Unknown".equalsIgnoreCase(lastCloneRename) || "black".equalsIgnoreCase(cloneColor)) && !"Successful".equalsIgnoreCase(status)) {
                     showCloneInfo = "Unknown";
                  } else if ("blue".equalsIgnoreCase(cloneColor) || "#FFBF00".equalsIgnoreCase(cloneColor)) {
                     showCloneInfo = lastCloneRename + ' ' + rs.getString("si_clone_Identifiers");
                  } else if (StringUtils.isNotBlank(rs.getString("tv_clone_Identifiers"))) {
                     showCloneInfo = rs.getString("tv_clone_Identifiers");
                  }

                  jsonObj.put("showCloneInfo", showCloneInfo);
                  jsonObj.put("downloadable", PlatformUtils.isDownloadable(type));
                  jsonObj.put("supportRemoteControl", PlatformUtils.isSupportRemoteControl(type));
                  jsonObj.put("isHaveWarnings", this.checkWarningsStatus(rs, maxOnlineExpireMilSecs));
                  jsonObj.put("isSupportMessage", PlatformUtils.isSupportMessage(type));
                  jsonObj.put("isSupportPmsActions", PlatformUtils.isSupportPmsActions(type));
                  jsonObj.put("isSupportChangePowerState", PlatformUtils.isSupportChangePowerState(type));
                  jsonObj.put("isSupportWakupOnLan", PlatformUtils.isSupportWakeupOnLan(type));
                  jsonObj.remove("Lastonline");
                  jsonObj.remove("si_Identifiers");
                  array.put(jsonObj);
               }
            }

            return array;
         }
      } catch (SQLException e) {
         logger.error(e.getMessage(), e);
         return null;
      }
   }

   public JsonArray getDeviceJsonDataByTvGroup(String groupIds) {
      String[] groupIdArray = groupIds.split(",");
      StringBuilder placeholders = new StringBuilder();

      for (int i = 0; i < groupIdArray.length; i++) {
         if (i > 0) {
            placeholders.append(",");
         }

         placeholders.append("?");
      }

      StringBuilder sql = new StringBuilder();
      sql.append("SELECT tv_firmware_Identifier, type FROM deviceinfo_view v WHERE TVGroups IN (").append(placeholders).append(")");
      JsonArray result = new JsonArray();

      try (
         Connection conn = JpaManager.getConnection();
         PreparedStatement stat = conn.prepareStatement(sql.toString());
      ) {
         for (int i = 0; i < groupIdArray.length; i++) {
            stat.setString(i + 1, groupIdArray[i]);
         }

         try (ResultSet rs = stat.executeQuery()) {
            while (rs.next()) {
               JsonObject jsonObject = new JsonObject();
               jsonObject.addProperty("version", rs.getString("tv_firmware_Identifier"));
               jsonObject.addProperty("platform", rs.getString("type"));
               result.add(jsonObject);
            }
         }

         return result;
      } catch (SQLException e) {
         logger.error(e.getMessage(), e);
         return null;
      }
   }

   private boolean checkWarningsStatus(ResultSet rs, long maxOnlineExpireMilSecs) throws SQLException {
      String type = rs.getString("Type");
      if (!PlatformUtils.isSupportShowWarnings(type)) {
         return false;
      }

      IPProfile profile = IPProfile.loadIPProfile();
      String powerStatus = rs.getString("PowerStatus");
      String tvIpAddress = rs.getString("TVIPAddress");
      if (!StringUtils.equalsIgnoreCase(profile.getWebserviceURLIncorrectWarning(), "false")) {
         boolean isWebServiceUrlError = Utils.isWebServiceUrlError(maxOnlineExpireMilSecs, powerStatus, tvIpAddress, rs.getString("Lastonline"), type);
         if (isWebServiceUrlError) {
            return true;
         }
      }

      if (!StringUtils.equalsIgnoreCase(profile.getTvSettingChangeWarning(), "false")) {
         String lastSuccessSettingPackageId = rs.getString("success_siclone_Identifier");
         String siIdentifiers = rs.getString("si_Identifiers");
         if (Utils.isTvSettingNotMatchBetweenTvAndSIServer(powerStatus, lastSuccessSettingPackageId, siIdentifiers, tvIpAddress, type)) {
            return true;
         }
      }

      if (!StringUtils.equalsIgnoreCase(profile.getFirmwareOutOfDateWarning(), "false")) {
         String firmwareVersion = rs.getString("tv_firmware_Identifier");
         if (StringUtils.isNoneBlank(firmwareVersion)) {
            String modelName = rs.getString("TVModelNumber");
            if (VersionChecker.getFirmwareNewVersion(type, modelName, firmwareVersion) != null) {
               return true;
            }
         }
      }

      return false;
   }

   private String maxTvCloneIdent(String tvCloneIdent, String type) {
      if (StringUtils.isEmpty(tvCloneIdent)) {
         return "None";
      }

      String ret = null;
      String[] tvCloneIdentifiers = tvCloneIdent.split(",");
      String[] tvCloneIdentifiers_Android = tvCloneIdent.split(",");
      String platform = PlatformUtils.getPlatformId(type);
      if (PlatformUtils.hasPackageFeature(platform)) {
         SimpleDateFormat format = TpvDateUtils.getSimpleDateFormatWithEnglishLocale("dd/MM/yyyy:HH:mm");
         SimpleDateFormat formatTmp = TpvDateUtils.getSimpleDateFormatWithEnglishLocale("yyyy/MM/dd:HH:mm");
         SimpleDateFormat formatSmartInfo = TpvDateUtils.getSimpleDateFormatWithEnglishLocale("dd-MM-yyyy-'T'HHmmss");
         String dateStr = "";

         for (int i = 0; i < tvCloneIdentifiers.length; i++) {
            dateStr = tvCloneIdentifiers[i].trim();

            try {
               if ("00/00/0000:--:--".equals(dateStr)) {
                  dateStr = "01/01/1000:00:00";
               } else if (tvCloneIdentifiers[i].contains("T")) {
                  dateStr = formatTmp.format(formatSmartInfo.parse(dateStr.substring(0, 18)));
               } else {
                  dateStr = formatTmp.format(format.parse(dateStr));
               }
            } catch (Exception e) {
               dateStr = tvCloneIdentifiers[i].trim();
            }

            tvCloneIdentifiers_Android[i] = dateStr;
         }

         int maxPos = 0;
         if (null != tvCloneIdentifiers_Android && tvCloneIdentifiers_Android.length > 0) {
            ret = tvCloneIdentifiers_Android[0].trim();

            for (int i = 1; i < tvCloneIdentifiers_Android.length; i++) {
               if (ret.compareToIgnoreCase(tvCloneIdentifiers_Android[i].trim()) < 0) {
                  ret = tvCloneIdentifiers_Android[i].trim();
                  maxPos = i;
               }
            }
         }

         return "01/01/1000:00:00".equals(ret) ? "00/00/0000:--:--" : tvCloneIdentifiers[maxPos];
      } else {
         if (null != tvCloneIdentifiers && tvCloneIdentifiers.length > 0) {
            ret = tvCloneIdentifiers[0].trim();

            for (int i = 1; i < tvCloneIdentifiers.length; i++) {
               if (ret.compareToIgnoreCase(tvCloneIdentifiers[i].trim()) < 0) {
                  ret = tvCloneIdentifiers[i].trim();
               }
            }
         }

         return ret;
      }
   }

   public List<Devices> getDevicesListByTvUniqueIds(List<String> idList) {
      return this.devicesRepository.findByTvuniqueidIn(idList);
   }

   public List<String> getRfDevicePlatformsByRoomId(String roomIdstr) {
      List<Devices> tvs = new ArrayList<>();

      for (String roomId : roomIdstr.split(",")) {
         tvs.addAll(this.devicesRepository.findByTvroomid(roomId));
      }

      return tvs.stream().filter(Devices::isRFDevice).map(Devices::getType).distinct().collect(Collectors.toList());
   }

   public boolean hasAssignedDevices(CloneItemUtils.CloneItemInfo info) {
      return info.getItemType() == CommonConstants.CloneItemType.Firmware
         ? !this.devicesRepository.findByFirmwareid(info.getId()).isEmpty()
         : !this.findDevicesByClonePath(info.getClonePath()).isEmpty();
   }

   public int findAllCount() {
      return (int)this.devicesRepository.count();
   }
}
