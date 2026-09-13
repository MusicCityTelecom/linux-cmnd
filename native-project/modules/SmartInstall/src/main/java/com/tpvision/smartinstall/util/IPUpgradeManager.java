package com.tpvision.smartinstall.util;

import com.tpvision.smartinstall.core.SettingCreator;
import com.tpvision.smartinstall.dao.core.AppPackage;
import com.tpvision.smartinstall.dao.core.Banners;
import com.tpvision.smartinstall.dao.core.ChannelPackage;
import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.Groups;
import com.tpvision.smartinstall.dao.core.GuestInfo;
import com.tpvision.smartinstall.dao.core.Schedule;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.core.SettingPackage;
import com.tpvision.smartinstall.dao.core.UiCustomizations;
import com.tpvision.smartinstall.dao.core.UpgSetting;
import com.tpvision.smartinstall.dao.core.Welcome;
import com.tpvision.smartinstall.dao.mgr.AppPackageManager;
import com.tpvision.smartinstall.dao.mgr.BannersManager;
import com.tpvision.smartinstall.dao.mgr.ChannelPackageManager;
import com.tpvision.smartinstall.dao.mgr.DevicesManager;
import com.tpvision.smartinstall.dao.mgr.GroupsManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.ScheduleManager;
import com.tpvision.smartinstall.dao.mgr.SettingManager;
import com.tpvision.smartinstall.dao.mgr.SettingPackageManager;
import com.tpvision.smartinstall.dao.mgr.UiCustomizationsManager;
import com.tpvision.smartinstall.dao.mgr.UpgSettingManager;
import com.tpvision.smartinstall.dao.mgr.WelcomeManager;
import com.tpvision.smartinstall.gateway.PlayoutUtils;
import com.tpvision.smartinstall.japit.IPCloneServiceManager;
import com.tpvision.smartinstall.servlet.BaseHttpServlet;
import com.tpvision.smartinstall.servlet.IPProfile;
import com.tpvision.smartinstall.trigger.TriggerUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IPUpgradeManager {
   private static final Logger LOG = LoggerFactory.getLogger(IPUpgradeManager.class);
   public static final String GET_UPGRADE_TYPE_LIST = "getUpgradeTypeList";
   public static final String UPGRADE_TYPE_ALL_SELECT_NONE = "AllSelectNone";
   public static final String UPGRADE_TYPE_NONE = "None";
   public static final String UPGRADE_TYPE_FIRMWARE = "Firmware";
   public static final String UPGRADE_TYPE_CLONE = "Clone";
   public static final String UPGRADE_TYPE_SETTINGS = "Settings";
   public static final String UPGRADE_TYPE_CHANNELS = "Channels";
   public static final String UPGRADE_TYPE_APPS = "Apps";
   public static final String UPGRADE_TYPE_CONTENT = "Content";
   public static final String UPGRADE_TYPE_BANNERS = "Banners";
   public static final String UPGRADE_TYPE_UI = "UI";
   public static final String UPGRADE_TYPE_SCHEDULES = "Schedules";
   public static final String UPGRADE_TYPE_WELCOME = "Welcome";
   public static final String UPGRADE_TYPE_WEATHER = "Weather";
   public static final String UPGRADE_TYPE_WAIT = "Wait";
   public static final String[] ORIGIN_UPGRADE_TYPE = new String[]{"None", "Firmware", "Clone"};
   public static final String[] SINGLE_UPGRADE_TYPE = new String[]{"Settings", "Channels", "Apps", "Content", "Banners", "UI", "Schedules", "Welcome", "Wait"};
   public static final String SELECT_CLONE_TYPE = "select_clone_type";
   public static final String DEFAULT_PLATFORM = "TPM181HE_CloneData";
   private static boolean FLAG_PARTINCOMPATIBLE = false;
   public static final String defaultPartialClone = "{\"selection\":[{\"id\":\"ipAll\",\"val\":\"true\"},{\"id\":\"ipTVSettings\",\"val\":\"false\"},{\"id\":\"ipTVChannelList\",\"val\":\"false\"},{\"id\":\"ipWelcomeLogo\",\"val\":\"false\"},{\"id\":\"ipSmartInfoImages\",\"val\":\"false\"},{\"id\":\"ipSmartInfoPages\",\"val\":\"false\"},{\"id\":\"ipCustomDashboard\",\"val\":\"false\"},{\"id\":\"ipAndroidApps\",\"val\":\"false\"},{\"id\":\"ipMediaChannels\",\"val\":\"false\"},{\"id\":\"ipRoomSpecificSettings\",\"val\":\"false\"},{\"id\":\"ipDataDump\",\"val\":\"false\"},{\"id\":\"ipHTVCfg\",\"val\":\"false\"},{\"id\":\"ipScript\",\"val\":\"false\"},{\"id\":\"ipWeatherForecast\",\"val\":\"false\"},{\"id\":\"ipBanner\",\"val\":\"false\"},{\"id\":\"ipPMS\",\"val\":\"false\"},{\"id\":\"ipAndroidAppsData\",\"val\":\"false\"},{\"id\":\"ipProfessionalApps\",\"val\":\"false\"},{\"id\":\"ipProfessionalAppsData\",\"val\":\"false\"},{\"id\":\"ipSchedules\",\"val\":\"false\"},{\"id\":\"ipMyChoice\",\"val\":\"false\"},{\"id\":\"ipVsecure\",\"val\":\"false\"}]}";

   private IPUpgradeManager() {
   }

   public static boolean sendModifyWebserviceUrlTVSettingsRequestToTV(Devices device, boolean isASyncSendJapit) {
      String networkInterfaceIp = device.getNetworkInterfaceIp();
      String ip = device.getTvipaddress();
      String identifier = SettingCreator.createModifyServiceUrlTVSetting(ip, networkInterfaceIp);
      if (identifier == null) {
         LOG.error("failure to init tvsetting clone files");
         return false;
      }

      String sendIPCloneJson = IPCloneServiceManager.generateSimpleTvSettingJson(device, networkInterfaceIp, identifier);
      if (isASyncSendJapit) {
         JAPITUtils.sendAsyncCommand(device, sendIPCloneJson, (bResult, resultJson) -> {
            if (bResult) {
               IPCloneServiceManager.refreshTVCloneItemVersionToDb(resultJson, device);
            }
         });
      } else {
         try {
            JAPITUtils.sendJapitCommand(device, sendIPCloneJson);
         } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            return false;
         }
      }

      return true;
   }

   public static String sendTVSettingsRequestToTV(Devices device, SettingPackage settingPackage) {
      LOG.info("start to force upgrade setting package <{}>", settingPackage.getId());
      String status = processCloneUpgradeType("Settings", String.valueOf(settingPackage.getId()), device.getTvuniqueid(), "", null);
      if (!status.contains("fail")) {
         startUpgrades(device.getTvuniqueid(), "U");
         return "success";
      } else {
         return "failed to create tv settings clone file";
      }
   }

   private static String updateDeviceFirmwareInfo(int firmwareId, String[] tvArray, String platform, UpgSetting upgsettings) {
      String ret = "{\"status\":\"success\"}";
      String platformId = PlatformUtils.getPlatformId(platform);
      tvArray = removeIncompatibleTVs(tvArray, platformId, "firmware");
      if (getFlagPartIncompatible()) {
         ret = Utils.buildSuccessReturnJson("hasPartUncompatible", "yes");
      }

      DevicesManager devicesMgr = JpaManager.getDevicesManager();

      for (String tvid : tvArray) {
         Devices device = devicesMgr.loadByKey(tvid);
         if (device == null) {
            LOG.warn("update device firmware info failure as tv <{}> not exist", tvid);
         } else if (isTVUpgrading(device)) {
            LOG.warn("update device firmware info failure as tv <{}> in upgrading status", tvid);
         } else {
            SiIdentifiers siIdentifiers = SiIdentifiers.fromJson(device.getSiIdentifiers());
            siIdentifiers.updateAssignFirmwareVersion(upgsettings.getIpversion());
            device.setSiIdentifiers(siIdentifiers.toJson());
            device.setFirmwareid(firmwareId);
            device.setSiFirmwareIdentifier(upgsettings.getIpversion());
            device.setFwColor("blue");
            if (device.getCloneid() == 0) {
               device.setProgress("U");
            }

            devicesMgr.save(device);
         }
      }

      return ret;
   }

   private static String handleCopyUpg(int firmwareId, String tvIds, String groupIds) {
      UpgSettingManager smgr = JpaManager.getUpgSettingManager();
      UpgSetting upgsettings = smgr.loadByKey(firmwareId);
      if (upgsettings == null) {
         LOG.error("firmware id:{} not exists, assign fail", firmwareId);
         return Utils.buildFailReturnJson("Firmware does not exist anymore, please reupload the firmware!");
      }

      String targetTvIds = getDeviceList(tvIds, groupIds);
      if (StringUtils.isBlank(targetTvIds)) {
         LOG.warn("target upgrade devices is empty, return directly");
         return "{\"status\":\"success\"}";
      }

      String platform = upgsettings.getPlatform();

      try (SettingCreator settingCreator = new SettingCreator(platform)) {
         settingCreator.processFirmware(upgsettings);
      } catch (IOException ex) {
         LOG.error("process firmware fail, please check firmware file status!", ex);
         return Utils.buildFailReturnJson("Firmware copy failure, please reupload the firmware!");
      }

      String[] tvArray = targetTvIds.split(",");
      return updateDeviceFirmwareInfo(firmwareId, tvArray, platform, upgsettings);
   }

   private static String sendForceUpgradeRequestToTV(Devices tv) {
      String power = tv.getPowerstatus();
      if (tv.isOnline() && !JAPITUtils.isJapitListening(tv)) {
         power = "offline";
      }

      if (!"offline".equalsIgnoreCase(power)) {
         LOG.info("start to force upgrade tv <{}>, power=<{}>", tv.getTvipaddress(), power);
         boolean isReadyToFroceUpgrade = IPCloneServiceManager.refreshTVCloneStatusAndCheckIsForceUpgradeReady(tv);
         if (!isReadyToFroceUpgrade) {
            LOG.warn("can't froce upgrade tv <{}>, as tv not in ready for upgrade status", tv.getTvipaddress());
            return Utils.buildFailReturnJson("can't froce upgrade the tv due to it's still in another upgrade process");
         } else {
            IPCloneServiceManager.sendIPCloneDataToTV(tv);
            return Utils.buildSuccessReturnJson("power", power);
         }
      } else {
         LOG.warn("can't froce upgrade tv <{}>, power=<{}>", tv.getTvipaddress(), power);
         return Utils.buildFailReturnJson("can't froce upgrade tv, please check TV's power status");
      }
   }

   public static SettingCreator generateClonePackage(CommonConstants.CloneItemType cloneType, int cloneId, String platformId) throws IOException {
      try (SettingCreator sc = new SettingCreator(platformId)) {
         sc.processClonePacket(cloneType.name(), cloneId, null);
         sc.createIpPackages(cloneType.name(), cloneId);
         return sc;
      }
   }

   private static void updateGuestInfoCloneId(String roomid, int cloneId, CommonConstants.CloneItemType upgradeType) {
      if (roomid == null) {
         LOG.info("roomid is null,return");
      } else {
         for (GuestInfo guestInfo : JpaManager.getGuestInfoManager().findGuestInfosByRoomid(roomid)) {
            switch (upgradeType) {
               case Clone:
               case Firmware:
               default:
                  break;
               case TVSettings:
                  guestInfo.setSettingPackageId(cloneId);
                  break;
               case AndroidApps:
                  guestInfo.setAppPackageId(cloneId);
                  break;
               case ChannelList:
                  guestInfo.setChannelPackageId(cloneId);
                  break;
               case SmartInfoBrowser:
                  guestInfo.setContentId(cloneId);
                  break;
               case Banner:
                  guestInfo.setBannerId(cloneId);
                  break;
               case Schedules:
                  guestInfo.setScheduleId(cloneId);
                  break;
               case WelcomeLogo:
                  guestInfo.setWelcomeId(String.valueOf(cloneId));
            }

            JpaManager.getGuestInfoManager().save(guestInfo);
         }
      }
   }

   public static void assignRFPlayouts(String tvs, CommonConstants.CloneItemType upgradeType, int cloneId, String source) {
      CloneItemUtils.CloneItemInfo info = CloneItemUtils.getCloneItemInfo(upgradeType, cloneId);
      List<String> roomIds = new ArrayList<>();

      for (Devices tv : getDevicesList(tvs)) {
         if (tv.isRFDevice() && info.isCompatible(tv.getType())) {
            String roomid = tv.getTvroomid();
            if (!roomIds.contains(roomid)) {
               roomIds.add(roomid);
            }
         }
      }

      if (roomIds.isEmpty()) {
         LOG.info("no RF devices");
      } else {
         String roomStrs = String.join(",", roomIds);

         try {
            PlayoutUtils.addToPlayoutList(String.valueOf(cloneId), upgradeType.name(), roomStrs, source);
         } catch (BaseHttpServlet.MessageException e) {
            LOG.error(e.getMessage(), e);
         }
      }
   }

   private static String getCloneRenameFromSetting(int cloneId) {
      Setting setting = JpaManager.getSettingManager().loadByKey(cloneId);
      return setting != null ? setting.getClonerename() : "Unknown clone";
   }

   private static void assignClone(Devices tv, CommonConstants.CloneItemType upgradeType, int cloneId, String clonePath, String siAssignItems) {
      cleanExpiredCloneFiles(tv, clonePath);
      if (cloneId == 0) {
         resetUpgradeState(tv, "");
         updateGuestInfoCloneId(tv.getTvroomid(), cloneId, upgradeType);
      } else {
         tv.setCloneType(upgradeType.name());
         tv.setProgress("U");
         String firmwareIdentifier = null;
         if (upgradeType == CommonConstants.CloneItemType.Clone) {
            tv.setLastCloneRename(getCloneRenameFromSetting(cloneId));
            tv.setCloneid(cloneId);
            tv.setClonePath(clonePath);
            CloneItemUtils.CloneItemInfo info = null;

            try {
               info = CloneItemUtils.getCloneItemInfo(upgradeType, cloneId);
            } catch (Exception e) {
               LOG.error(e.getMessage(), e);
            }

            tv.setSiCloneIdentifiers(info == null ? "" : info.getVersion());
            tv.setCloneColor("blue");
         } else if (upgradeType == CommonConstants.CloneItemType.Firmware) {
            tv.setFirmwareid(cloneId);
            firmwareIdentifier = IPCloneServiceManager.getFirmwareIdentifier(cloneId);
            tv.setSiFirmwareIdentifier(firmwareIdentifier);
            tv.setFwColor("blue");
         }

         String siIdentifiers = getSiAssignIdentifiers(tv.getSiIdentifiers(), siAssignItems, firmwareIdentifier);
         tv.setSiIdentifiers(siIdentifiers);
         JpaManager.getDevicesManager().save(tv);
         updateGuestInfoCloneId(tv.getTvroomid(), cloneId, upgradeType);
      }
   }

   private static String getSiAssignIdentifiers(String siIdentifiers, String siAssignItems, String firmwareIdentifier) {
      SiIdentifiers siIdentifiersObj = SiIdentifiers.fromJson(siIdentifiers);
      if (StringUtils.isNotBlank(siAssignItems)) {
         siIdentifiersObj.updateAssignCloneData(siAssignItems);
      }

      if (StringUtils.isNotBlank(firmwareIdentifier)) {
         siIdentifiersObj.updateAssignFirmwareVersion(firmwareIdentifier);
      }

      return siIdentifiersObj.toJson();
   }

   public static List<Devices> getDevicesList(String tvs) {
      List<Devices> tvList = new ArrayList<>();
      String[] tvids = tvs.split(",");

      for (String tvid : tvids) {
         Devices dev = JpaManager.getDevicesManager().loadByKey(tvid);
         if (dev != null) {
            tvList.add(dev);
         }
      }

      return tvList;
   }

   private static void resetUpgradeState(Devices tv, String selectType) {
      if ("Firmware".equalsIgnoreCase(selectType)) {
         tv.setFirmwareid(0);
         tv.setSiFirmwareIdentifier(null);
         tv.setFwColor("black");
         SiIdentifiers siIdentifiers = SiIdentifiers.fromJson(tv.getSiIdentifiers());
         siIdentifiers.removeSiItemByItemName("MainFirmware");
         tv.setSiIdentifiers(siIdentifiers.toJson());
      } else if (!StringUtils.isBlank(selectType) && !"None".equalsIgnoreCase(selectType)) {
         tv.setCloneid(0);
         tv.setSiCloneIdentifiers(null);
         tv.setCloneColor("black");
         SiIdentifiers siIdentifiers = SiIdentifiers.fromJson(tv.getSiIdentifiers());
         siIdentifiers.removeAllSiItemNotItemName("MainFirmware");
         tv.setSiIdentifiers(siIdentifiers.toJson());
      } else {
         tv.setFirmwareid(0);
         tv.setSiFirmwareIdentifier(null);
         tv.setFwColor("black");
         tv.setCloneid(0);
         tv.setSiCloneIdentifiers(null);
         tv.setCloneColor("black");
         SiIdentifiers siIdentifiers = SiIdentifiers.fromJson(tv.getSiIdentifiers());
         siIdentifiers.SiAssignItem.clear();
         tv.setSiIdentifiers(siIdentifiers.toJson());
      }

      if (tv.getFirmwareid() <= 0 && tv.getCloneid() <= 0) {
         boolean isSendCancelJapit = isRequireSendCancelJapit(tv);
         tv.setProgress("ST");
         if (isSendCancelJapit) {
            IPCloneServiceManager.cancelIpCloneServiceForTV(tv);
         }
      }

      if (tv.getCloneid() <= 0) {
         cleanExpiredCloneFiles(tv, null);
      }

      JpaManager.getDevicesManager().save(tv);
      GroupsManager groupsManager = JpaManager.getGroupsManager();

      for (Groups grouptv : groupsManager.findGroupsByTvid(tv.getTvuniqueid())) {
         grouptv.setProgress(tv.getProgress());
         grouptv.setPowerstatus(tv.getPowerstatus());
         groupsManager.save(grouptv);
      }
   }

   private static boolean isRequireSendCancelJapit(Devices tv) {
      return isTVUpgrading(tv) && PlatformUtils.isSupportUpgradeStopJapit(tv.getType());
   }

   public static IPUpgradeManager.UpgradeResult assignClones(List<Devices> tvList, CommonConstants.CloneItemType cloneType, int cloneId) throws IPUpgradeManager.UpgradeException {
      IPUpgradeManager.UpgradeResult upgradeResult = new IPUpgradeManager.UpgradeResult();
      upgradeResult.status = "Success";
      if (cloneId == 0) {
         for (Devices tv : tvList) {
            resetUpgradeState(tv, "");
         }

         return upgradeResult;
      } else {
         String platform = CloneItemUtils.getCloneItemPlatform(cloneType, cloneId);
         if (platform == null) {
            platform = PlatformUtils.getPlatformId(tvList.get(0).getType());
         }

         String clonePath = null;
         String siAssignItems = null;

         try {
            SettingCreator sc = generateClonePackage(cloneType, cloneId, platform);
            clonePath = sc.getClonePath();
            if (cloneType != CommonConstants.CloneItemType.Firmware) {
               siAssignItems = sc.getSiAssignCloneItems();
            }
         } catch (IOException e) {
            throw new IPUpgradeManager.UpgradeException("generate clone package failed:" + e.getMessage());
         }

         for (Devices tv : tvList) {
            if (!PlatformUtils.isCompatiblePlatform(tv.getType(), platform)) {
               LOG.error("tv {} not compatible with clone item {}[{}]", tv.getId(), cloneType.name(), cloneId);
               upgradeResult.hasUncompatible = true;
               upgradeResult.uncompatibleTvList.add(tv.getId());
            } else {
               assignClone(tv, cloneType, cloneId, clonePath, siAssignItems);
            }
         }

         return upgradeResult;
      }
   }

   public static boolean startUpgrades(String tvs, String progress) {
      List<Devices> tvList = getDevicesList(tvs);
      boolean result = true;
      if (progress.equalsIgnoreCase("U")) {
         for (Devices tv : tvList) {
            if (tv.isRFDevice()) {
               LOG.warn("startUpgrade is RF TV: {}", tv.getId());
            } else if (!"U".equalsIgnoreCase(tv.getProgress())) {
               LOG.warn("update failed, TV: {} does not have a clone package assigned", tv.getId());
            } else if (isTVUpgrading(tv)) {
               LOG.warn("Cannot start new update, upgrade process is already started for TV: {}", tv.getId());
            } else {
               String status = sendForceUpgradeRequestToTV(tv);
               if (status.contains("fail")) {
                  result = false;
               }
            }
         }
      } else if (progress.equalsIgnoreCase("ST")) {
         for (Devices tv : tvList) {
            resetUpgradeState(tv, "");
         }
      }

      return result;
   }

   public static String modeUpdatingProgress(String process, List<String> idList, String selectType) {
      String status = "{\"status\":\"success\"}";
      boolean faileStatus = false;
      String reason = "";
      DevicesManager tvmanager = JpaManager.getDevicesManager();

      for (int i = 0; i < idList.size(); i++) {
         String tvId = idList.get(i);
         Devices tv = tvmanager.loadByKey(tvId);
         if (null == tv) {
            LOG.error("modeUpdatingProgress tv = null for tvIdsArray ={} ", tvId);
         } else if ("ST".equalsIgnoreCase(process)) {
            if ("Upload".equalsIgnoreCase(tv.getCloneMode())) {
               tv.setUploadProgress(process);
               tv.setCloneMode("Upgrade");
               tvmanager.save(tv);
            } else if (isRequireSendCancelJapit(tv) && ("offline".equalsIgnoreCase(tv.getPowerstatus()) || !JAPITUtils.isJapitListening(tv))) {
               LOG.error("cant send cancel japit to tv<{}> as TV is not connected", tv.getTvipaddress());
               faileStatus = true;
               if (idList.size() == 1) {
                  reason = "Stop failure,TV is not connected!";
               } else if (idList.size() > 1) {
                  reason = "Partial TV is not connected!";
               }
            } else {
               resetUpgradeState(tv, selectType);
            }
         } else if ("U".equalsIgnoreCase(process)) {
            LOG.info(
               "ACTION: SI User do FORCE UPGRADE action for TV! forceUpgradePara: platfrom: {}, TV Name:{}, PowerStatus: {}, CurrentProgress:{}",
               tv.getType(),
               tv.getTvname(),
               tv.getPowerstatus(),
               tv.getProgress()
            );
            if ("offline".equalsIgnoreCase(tv.getPowerstatus())) {
               faileStatus = true;
               if (idList.size() == 1) {
                  reason = "TV is not connected!";
               } else if (idList.size() > 1) {
                  reason = "Partial TV is not connected!";
               }
            }

            if ("U".equalsIgnoreCase(tv.getProgress())) {
               status = sendForceUpgradeRequestToTV(tv);
            } else {
               faileStatus = true;
               reason = "Clone data not ready, please select clone";
            }
         }
      }

      if (faileStatus) {
         status = Utils.buildFailReturnJson(reason);
      }

      return status;
   }

   public static void cleanExpiredCloneFiles(Devices tv, String newClonePath) {
      String oldClonePath = tv.getClonePath();
      if (oldClonePath == null || oldClonePath.isEmpty()) {
         LOG.info("tv :{} clonePath is empty,return", tv.getTvuniqueid());
      } else if (StringUtils.equalsIgnoreCase(oldClonePath, newClonePath)) {
         LOG.info("tv:{} current clone path <{}> still in use, no need to clean", tv.getTvuniqueid(), newClonePath);
      } else {
         tv.setClonePath(null);
         JpaManager.getDevicesManager().save(tv);
         if (oldClonePath.startsWith("Clone")) {
            Pattern r = Pattern.compile("Clone\\/(\\d+)-*");
            Matcher m = r.matcher(oldClonePath);
            if (m.find()) {
               String cloneId = m.group(1);
               if (TriggerUtils.isCloneAssigned(Integer.parseInt(cloneId))) {
                  LOG.info("clone {} as assigned to trigger now, will not clean cache", cloneId);
                  return;
               }
            }
         }

         List<Devices> tvs = JpaManager.getDevicesManager().findDevicesByClonePath(oldClonePath);
         if (tvs.isEmpty()) {
            String fullPath = CommonConstants.servletContextPath + "/Profile/Clone/" + oldClonePath;
            FileUtils.deleteQuietly(new File(fullPath));
            LOG.info("delete profile file:{}", fullPath);
         }
      }
   }

   public static String processCloneUpgradeType(String selectCloneType, String cloneIdStr, String tvIds, String groupIds, Map<String, String> parameters) {
      String status = "{\"status\":\"fail\"}";
      LOG.info("select_cloneType:{},cloneIdStr:{}", selectCloneType, cloneIdStr);
      if (null == parameters) {
         parameters = new HashMap<>();
      }

      parameters.put("id", cloneIdStr);
      parameters.put("tvids", tvIds);
      parameters.put("groupIds", groupIds);
      parameters.put("selectType", selectCloneType);
      parameters.put(
         "partialClone",
         "{\"selection\":[{\"id\":\"ipAll\",\"val\":\"true\"},{\"id\":\"ipTVSettings\",\"val\":\"false\"},{\"id\":\"ipTVChannelList\",\"val\":\"false\"},{\"id\":\"ipWelcomeLogo\",\"val\":\"false\"},{\"id\":\"ipSmartInfoImages\",\"val\":\"false\"},{\"id\":\"ipSmartInfoPages\",\"val\":\"false\"},{\"id\":\"ipCustomDashboard\",\"val\":\"false\"},{\"id\":\"ipAndroidApps\",\"val\":\"false\"},{\"id\":\"ipMediaChannels\",\"val\":\"false\"},{\"id\":\"ipRoomSpecificSettings\",\"val\":\"false\"},{\"id\":\"ipDataDump\",\"val\":\"false\"},{\"id\":\"ipHTVCfg\",\"val\":\"false\"},{\"id\":\"ipScript\",\"val\":\"false\"},{\"id\":\"ipWeatherForecast\",\"val\":\"false\"},{\"id\":\"ipBanner\",\"val\":\"false\"},{\"id\":\"ipPMS\",\"val\":\"false\"},{\"id\":\"ipAndroidAppsData\",\"val\":\"false\"},{\"id\":\"ipProfessionalApps\",\"val\":\"false\"},{\"id\":\"ipProfessionalAppsData\",\"val\":\"false\"},{\"id\":\"ipSchedules\",\"val\":\"false\"},{\"id\":\"ipMyChoice\",\"val\":\"false\"},{\"id\":\"ipVsecure\",\"val\":\"false\"}]}"
      );
      if (parameters.get("output") == null) {
         parameters.put("output", "C");
      }

      parameters.put("select_clone_type", selectCloneType);
      int cloneId = TpvStringUtils.tryParseInt(cloneIdStr, -1);
      if ("None".equalsIgnoreCase(selectCloneType) || cloneId <= 0) {
         return handleAssignNone(tvIds, groupIds, selectCloneType);
      }

      if ("Firmware".equals(selectCloneType)) {
         return handleCopyUpg(cloneId, tvIds, groupIds);
      }

      if ("Clone".equals(selectCloneType)) {
         return processCloneUpgradeByPara(cloneIdStr, tvIds, groupIds, selectCloneType, parameters);
      }

      String platformId = getPlatformByID(selectCloneType, cloneId, tvIds, groupIds);
      if (platformId == null) {
         return Utils.buildFailReturnJson("device platform check failure, please check the platform of the seleceted devices!");
      }

      try (SettingCreator settingCreator = new SettingCreator(platformId)) {
         CommonConstants.CloneItemType convertType = convertUpgradeTypeToCloneItemType(selectCloneType);
         processClonePacket(convertType, cloneId, settingCreator);
         settingCreator.processUpgCreation(parameters);
         String siAssignCloneItems = settingCreator.getSiAssignCloneItems();
         String clonePath = settingCreator.getClonePath();
         status = updateProgressViaIP(cloneId, clonePath, tvIds, groupIds, platformId, selectCloneType, siAssignCloneItems);
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }

      return status;
   }

   private static boolean notCheckCompatibleBySelectCloneType(String selectCloneType) {
      return "Content".equals(selectCloneType);
   }

   public static String convertCloneItemTypeToUpgradeType(CommonConstants.CloneItemType type) {
      String upgradeType = "Clone";
      switch (type) {
         case Firmware:
            upgradeType = "Firmware";
            break;
         case TVSettings:
            upgradeType = "Settings";
            break;
         case AndroidApps:
            upgradeType = "Apps";
            break;
         case ChannelList:
            upgradeType = "Channels";
            break;
         case SmartInfoBrowser:
            upgradeType = "Content";
            break;
         case Banner:
            upgradeType = "Banners";
            break;
         case Schedules:
            upgradeType = "Schedules";
            break;
         case WelcomeLogo:
            upgradeType = "Welcome";
            break;
         case UiCustomizations:
            upgradeType = "UI";
      }

      return upgradeType;
   }

   public static CommonConstants.CloneItemType convertUpgradeTypeToCloneItemType(String type) {
      CommonConstants.CloneItemType cloneItemType = CommonConstants.CloneItemType.UnKnownItem;
      switch (type) {
         case "Clone":
            cloneItemType = CommonConstants.CloneItemType.Clone;
            break;
         case "Firmware":
            cloneItemType = CommonConstants.CloneItemType.Firmware;
            break;
         case "Settings":
            cloneItemType = CommonConstants.CloneItemType.TVSettings;
            break;
         case "Channels":
            cloneItemType = CommonConstants.CloneItemType.ChannelList;
            break;
         case "Apps":
            cloneItemType = CommonConstants.CloneItemType.AndroidApps;
            break;
         case "Content":
            cloneItemType = CommonConstants.CloneItemType.SmartInfoBrowser;
            break;
         case "Banners":
            cloneItemType = CommonConstants.CloneItemType.Banner;
            break;
         case "UI":
            cloneItemType = CommonConstants.CloneItemType.UiCustomizations;
            break;
         case "Schedules":
            cloneItemType = CommonConstants.CloneItemType.Schedules;
            break;
         case "Welcome":
            cloneItemType = CommonConstants.CloneItemType.WelcomeLogo;
            break;
         case "Weather":
            cloneItemType = CommonConstants.CloneItemType.WeatherForecast;
      }

      return cloneItemType;
   }

   private static boolean getFlagPartIncompatible() {
      return FLAG_PARTINCOMPATIBLE;
   }

   private static String[] removeIncompatibleTVs(String[] tvIds, String platform, String selectCloneType) {
      DevicesManager dm = JpaManager.getDevicesManager();
      ArrayList<String> correctPlatformTVs = new ArrayList<>();

      for (String tvId : tvIds) {
         correctPlatformTVs.add(tvId);
      }

      FLAG_PARTINCOMPATIBLE = false;
      Iterator<String> it = correctPlatformTVs.iterator();

      while (it.hasNext()) {
         String id = it.next();
         Devices device = dm.loadByKey(id);
         if (null != device) {
            String type = device.getType();
            if (!PlatformUtils.isCompatiblePlatform(type, platform) && (!"2019 NAFTA".equalsIgnoreCase(type) || !"banners".equalsIgnoreCase(selectCloneType))) {
               it.remove();
               FLAG_PARTINCOMPATIBLE = true;
            }
         } else {
            it.remove();
            IPProfile.updateIPProfile(id);
         }
      }

      return correctPlatformTVs.isEmpty() ? null : correctPlatformTVs.toArray(new String[0]);
   }

   public static boolean isTVUpgrading(Devices tv) {
      return "INP".equalsIgnoreCase(tv.getProgress());
   }

   public static String getDeviceList(String tvIds, String groupIds) {
      List<String> tvidList = new ArrayList<>();
      if (tvIds != null && !tvIds.isEmpty()) {
         String[] tvs = tvIds.split(",");
         tvidList.addAll(Arrays.asList(tvs));
      }

      if (groupIds != null && !groupIds.isEmpty()) {
         String[] groups = groupIds.split(",");

         for (String groupName : groups) {
            for (Groups group : JpaManager.getGroupsManager().findGroupsByGroupName(groupName)) {
               tvidList.add(group.getTvid());
            }
         }
      }

      String[] tvidArray = new HashSet<>(tvidList).toArray(new String[0]);
      return String.join(",", tvidArray);
   }

   private static String processCloneUpgradeByPara(String cloneIdStr, String tvIds, String groupIds, String selectCloneType, Map<String, String> parameters) {
      SettingManager sm = JpaManager.getSettingManager();
      Setting settingDb = sm.loadByKey(Integer.parseInt(cloneIdStr));
      if (null != settingDb) {
         try (SettingCreator settingCreator = new SettingCreator(settingDb.getPlatform())) {
            settingCreator.processSettings(settingDb);
            String platform = settingDb.getPlatform();
            if (!PlatformUtils.isAsta2016Up(platform)) {
               copyFromAssemblyToPlayout(settingCreator.getOutputPath(), PlatformUtils.getPlatformId(platform));
            }

            settingCreator.processUpgCreation(parameters);
            String siItemIdentifiers = settingCreator.getSiAssignCloneItems();
            String clonePath = settingCreator.getClonePath();
            return updateProgressViaIP(settingDb.getId(), clonePath, tvIds, groupIds, platform, selectCloneType, siItemIdentifiers);
         } catch (IOException e) {
            LOG.error(e.getMessage(), e);
         }
      }

      return "{\"status\":\"success\"}";
   }

   public static String handleAssignNone(String tvIds, String groupIds, String selectCloneType) {
      String[] tvIdArray = tvIds.split(",");
      String[] groupIdArray = groupIds.split(",");
      GroupsManager gm = JpaManager.getGroupsManager();
      DevicesManager dm = JpaManager.getDevicesManager();
      List<Devices> updateDevices = new ArrayList<>();
      List<Groups> updateGroups = new ArrayList<>();
      if (StringUtils.isNotBlank(tvIds)) {
         for (String tvId : tvIdArray) {
            Devices device = dm.loadByKey(tvId);
            if (device != null) {
               updateDevices.add(device);
            }

            updateGroups.addAll(gm.findGroupsByTvid(tvId));
         }
      }

      if (StringUtils.isNotBlank(groupIds)) {
         for (String groupName : groupIdArray) {
            List<Groups> groups = gm.findGroupsByGroupName(groupName);
            updateGroups.addAll(groups);

            for (Groups group : groups) {
               Devices device = dm.loadByKey(group.getTvid());
               if (device != null) {
                  updateDevices.add(device);
               }
            }
         }
      }

      for (Devices device : updateDevices) {
         resetUpgradeState(device, selectCloneType);
      }

      return "{\"status\":\"success\"}";
   }

   private static boolean processClonePacket(CommonConstants.CloneItemType cloneItemType, int id, SettingCreator settingCreator) {
      try {
         switch (cloneItemType) {
            case Clone:
               SettingManager settingMgr = JpaManager.getSettingManager();
               Setting setting = settingMgr.loadByKey(id);
               if (null != setting) {
                  settingCreator.processSettings(setting);
               }
               break;
            case Firmware:
               UpgSettingManager upgSettingMgr = JpaManager.getUpgSettingManager();
               UpgSetting upgSetting = upgSettingMgr.loadByKey(id);
               if (null != upgSetting) {
                  settingCreator.processFirmware(upgSetting);
               }
               break;
            case TVSettings:
               SettingPackageManager settingpackageMgr = JpaManager.getSettingPackageManager();
               SettingPackage settingpackage = settingpackageMgr.loadByKey(id);
               if (null != settingpackage) {
                  settingCreator.processTVSettings(settingpackage);
               }
               break;
            case AndroidApps:
               AppPackageManager apppackageMgr = JpaManager.getAppPackageManager();
               AppPackage apppackage = apppackageMgr.loadByKey(id);
               if (null != apppackage) {
                  settingCreator.processAppPackage(apppackage);
               }
               break;
            case ChannelList:
               ChannelPackageManager channelpackageMgr = JpaManager.getChannelPackageManager();
               ChannelPackage channelpackage = channelpackageMgr.loadByKey(id);
               if (null != channelpackage) {
                  settingCreator.processChannelPackage(channelpackage);
               }
               break;
            case SmartInfoBrowser:
               settingCreator.processContent(id);
               break;
            case Banner:
               Banners banners = JpaManager.getBannersManager().loadByKey(id);
               if (null != banners) {
                  settingCreator.processBanner(banners);
               }
               break;
            case Schedules:
               Schedule schedules = JpaManager.getScheduleManager().loadByKey(id);
               if (null != schedules) {
                  settingCreator.processSchedules(schedules);
                  settingCreator.initEnableScheduleTvSettings(schedules.getLastEdit());
               }
               break;
            case WelcomeLogo:
               Welcome welcome = JpaManager.getWelcomeManager().loadByKey(id);
               if (null != welcome) {
                  settingCreator.processWelcome(welcome.getId());
               }
               break;
            case UiCustomizations:
               UiCustomizations uicustom = JpaManager.getUiCustomizationsManager().loadByKey(id);
               if (null != uicustom) {
                  settingCreator.processUiCustomizations(uicustom);
               }
               break;
            default:
               LOG.error("not support to process clonetype:{}", cloneItemType);
         }

         return true;
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         return false;
      }
   }

   public static String getPlatformByID(String cloneType, int id, String tvids, String groupIds) {
      String platform = null;

      try {
         if ("Clone".equalsIgnoreCase(cloneType)) {
            SettingManager settingMgr = JpaManager.getSettingManager();
            Setting setting = settingMgr.loadByKey(id);
            if (null != setting) {
               platform = setting.getPlatform();
            }
         } else if ("setting".equalsIgnoreCase(cloneType) || "Settings".equalsIgnoreCase(cloneType)) {
            SettingPackageManager settingpackageMgr = JpaManager.getSettingPackageManager();
            SettingPackage settingpackage = settingpackageMgr.loadByKey(id);
            if (null != settingpackage) {
               platform = settingpackage.getPlatform();
            }
         } else if ("channel".equalsIgnoreCase(cloneType) || "Channels".equalsIgnoreCase(cloneType)) {
            ChannelPackageManager channelpackageMgr = JpaManager.getChannelPackageManager();
            ChannelPackage channelpackage = channelpackageMgr.loadByKey(id);
            if (null != channelpackage) {
               platform = channelpackage.getPlatform();
            }
         } else if ("app".equalsIgnoreCase(cloneType) || "Apps".equalsIgnoreCase(cloneType)) {
            AppPackageManager apppackageMgr = JpaManager.getAppPackageManager();
            AppPackage apppackage = apppackageMgr.loadByKey(id);
            if (null != apppackage) {
               platform = apppackage.getPlatform();
            }
         } else if ("Firmware".equalsIgnoreCase(cloneType)) {
            UpgSettingManager upgSettingMgr = JpaManager.getUpgSettingManager();
            UpgSetting upgSetting = upgSettingMgr.loadByKey(id);
            if (null != upgSetting) {
               platform = upgSetting.getPlatform();
            }
         } else if ("Content".equalsIgnoreCase(cloneType)) {
            String targetTvIds = getDeviceList(tvids, groupIds);
            List<String> platformNameList = getPlatformByDevice(targetTvIds);
            if (platformNameList.size() != 1) {
               LOG.warn("selected devices {} or groups {} has more than one platform :{}", tvids, groupIds, platformNameList);
               return null;
            }

            platform = platformNameList.get(0);
         } else if ("Banners".equalsIgnoreCase(cloneType)) {
            BannersManager bannersMgr = JpaManager.getBannersManager();
            Banners banner = bannersMgr.loadByKey(id);
            if (null != banner) {
               platform = banner.getPlatform();
            }
         } else if ("UI".equalsIgnoreCase(cloneType)) {
            UiCustomizationsManager uiCustomizationsManager = JpaManager.getUiCustomizationsManager();
            UiCustomizations uiCustomizations = uiCustomizationsManager.loadByKey(id);
            if (null != uiCustomizations) {
               platform = uiCustomizations.getPlatform();
            }
         } else if ("Welcome".equalsIgnoreCase(cloneType)) {
            WelcomeManager welcomeManager = JpaManager.getWelcomeManager();
            Welcome welcome = welcomeManager.loadByKey(id);
            if (null != welcome) {
               platform = welcome.getPlatform();
            }
         } else if ("Schedules".equalsIgnoreCase(cloneType)) {
            ScheduleManager scheduleManager = JpaManager.getScheduleManager();
            Schedule schedule = scheduleManager.loadByKey(id);
            if (null != schedule) {
               platform = schedule.getPlatform();
            }
         }
      } catch (Exception e) {
         LOG.error("getPlatformByID fail");
      }

      if (platform == null) {
         LOG.error("get platform by clonetype:{} and cloneid: {} failed", cloneType, id);
         return null;
      } else {
         return PlatformUtils.getPlatformId(platform);
      }
   }

   public static int getCloneIdByCloneTypeAndName(String cloneType, String cloneName) {
      try {
         if ("Clone".equalsIgnoreCase(cloneType)) {
            SettingManager settingMgr = JpaManager.getSettingManager();
            List<Setting> settings = settingMgr.findSettingsByCloneRename(cloneName);
            if (settings.size() == 1) {
               return settings.get(0).getId();
            }
         } else if ("setting".equalsIgnoreCase(cloneType) || "Settings".equalsIgnoreCase(cloneType)) {
            SettingPackageManager settingpackageMgr = JpaManager.getSettingPackageManager();
            List<SettingPackage> settingpackages = settingpackageMgr.findByName(cloneName);
            if (settingpackages.size() == 1) {
               return settingpackages.get(0).getId();
            }
         } else if ("channel".equalsIgnoreCase(cloneType) || "Channels".equalsIgnoreCase(cloneType)) {
            ChannelPackageManager channelpackageMgr = JpaManager.getChannelPackageManager();
            List<ChannelPackage> channelpackages = channelpackageMgr.findByName(cloneName);
            if (channelpackages.size() == 1) {
               return channelpackages.get(0).getId();
            }
         } else if ("app".equalsIgnoreCase(cloneType) || "Apps".equalsIgnoreCase(cloneType)) {
            AppPackageManager apppackageMgr = JpaManager.getAppPackageManager();
            List<AppPackage> apppackages = apppackageMgr.findByName(cloneName);
            if (apppackages.size() == 1) {
               return apppackages.get(0).getId();
            }
         } else if ("Firmware".equalsIgnoreCase(cloneType)) {
            UpgSettingManager upgSettingMgr = JpaManager.getUpgSettingManager();
            List<UpgSetting> upgSettings = upgSettingMgr.findByUpgRename(cloneName);
            if (upgSettings.size() == 1) {
               return upgSettings.get(0).getId();
            }
         } else if ("Content".equalsIgnoreCase(cloneType)) {
            List<Integer> idList = ContentUtils.findContentIdByTitle(cloneName);
            if (idList.size() == 1) {
               return idList.get(0);
            }
         } else if ("Banners".equalsIgnoreCase(cloneType)) {
            BannersManager bannersManager = JpaManager.getBannersManager();
            List<Banners> banners = bannersManager.findByName(cloneName);
            if (banners.size() == 1) {
               return banners.get(0).getId();
            }
         } else if ("UI".equalsIgnoreCase(cloneType)) {
            UiCustomizationsManager uiCustomizationsManager = JpaManager.getUiCustomizationsManager();
            List<UiCustomizations> uiCustomizations = uiCustomizationsManager.findByName(cloneName);
            if (uiCustomizations.size() == 1) {
               return uiCustomizations.get(0).getId();
            }
         } else if ("Welcome".equalsIgnoreCase(cloneType)) {
            WelcomeManager welcomeManager = JpaManager.getWelcomeManager();
            List<Welcome> welcomes = welcomeManager.findWelcomesByName(cloneName);
            if (welcomes.size() == 1) {
               return welcomes.get(0).getId();
            }
         } else if ("Schedules".equalsIgnoreCase(cloneType)) {
            ScheduleManager scheduleManager = JpaManager.getScheduleManager();
            List<Schedule> schedules = scheduleManager.findByName(cloneName);
            if (schedules.size() == 1) {
               return schedules.get(0).getId();
            }
         }
      } catch (Exception e) {
         LOG.error("get clone Id fail");
      }

      return -1;
   }

   private static List<String> getPlatformByDevice(String tvIds) {
      List<String> platformNameList = new ArrayList<>();

      for (String tvId : tvIds.split(",")) {
         Devices devices = JpaManager.getDevicesManager().loadByKey(tvId);
         if (devices != null && !platformNameList.contains(devices.getType())) {
            platformNameList.add(devices.getType());
         }
      }

      return platformNameList;
   }

   private static String updateProgressViaIP(
      int cloneId, String clonePath, String tvIds, String groupNames, String platform, String selectCloneType, String siAssignCloneItems
   ) {
      if (!StringUtils.isBlank(siAssignCloneItems) && siAssignCloneItems.contains("CloneItemName")) {
         String targetTvIds = getDeviceList(tvIds, groupNames);
         LOG.info("updated tvIds={}", targetTvIds);
         if (StringUtils.isNotEmpty(targetTvIds)) {
            String[] tvIdArray = targetTvIds.split(",");
            if (!notCheckCompatibleBySelectCloneType(selectCloneType) && null != platform && !"".equalsIgnoreCase(platform)) {
               tvIdArray = removeIncompatibleTVs(tvIdArray, platform, selectCloneType);
            }

            updateDeviceCloneInfo(tvIdArray, cloneId, clonePath, selectCloneType, siAssignCloneItems);
         }

         return getFlagPartIncompatible() ? "{\"status\":\"success\",\"hasPartUncompatible\":\"yes\",\"sameCloneVersion\":\"no\"}" : "{\"status\":\"success\"}";
      } else {
         return Utils.buildFailReturnJson("assign clone package is empty or corrupted,Please check!");
      }
   }

   private static CloneItemUtils.CloneItemInfo getParmeters(int cloneId, String selectCloneType) {
      CommonConstants.CloneItemType cloneItemType = convertUpgradeTypeToCloneItemType(selectCloneType);
      return CloneItemUtils.getCloneItemInfo(cloneItemType, cloneId);
   }

   private static void updateDeviceCloneInfo(String[] tvIdArray, int cloneId, String clonePath, String selectCloneType, String siAssignCloneItems) {
      if (cloneId > 0 && null != tvIdArray && tvIdArray.length != 0) {
         DevicesManager deviceMgr = JpaManager.getDevicesManager();
         CloneItemUtils.CloneItemInfo cloneItemInfo = getParmeters(cloneId, selectCloneType);

         for (String tvId : tvIdArray) {
            Devices device = deviceMgr.loadByKey(tvId);
            if (isTVUpgrading(device)) {
               LOG.warn("update device clone info failure as tv <{}> in upgrading status", tvId);
            } else {
               cleanExpiredCloneFiles(device, clonePath);
               device.setCloneid(cloneId);
               device.setUpgradeType("Clone");
               device.setCloneType(selectCloneType);
               device.setProgress("U");
               device.setCloneColor("blue");
               device.setClonePath(clonePath);
               device.setLastCloneRename(cloneItemInfo.getName());
               device.setSiCloneIdentifiers(cloneItemInfo.getVersion());
               SiIdentifiers siIdentifiers = SiIdentifiers.fromJson(device.getSiIdentifiers());
               siIdentifiers.updateAssignCloneData(siAssignCloneItems);
               device.setSiIdentifiers(siIdentifiers.toJson());
               deviceMgr.save(device);
            }
         }
      } else {
         LOG.info("cloneId is blank or tvId is blank");
      }
   }

   public static void copyFromAssemblyToPlayout(String assemblyPath, String platformId) throws IOException {
      long startTime = System.currentTimeMillis();
      File f = new File(assemblyPath);
      File f1 = new File(CommonConstants.RF_PLAY_BACK_INPUT_LOCATION + platformId);
      FileUtils.deleteDirectory(f1);
      f1.mkdirs();
      FileUtils.copyDirectory(f, f1);
      long endTime = System.currentTimeMillis();
      long totalTime = endTime - startTime;
      LOG.info("copyFromAssemblyToPlayout finished,used {} ms", totalTime);
   }

   public static String getUpgradeStatusByColor(String color) {
      if (color.equalsIgnoreCase("black")) {
         return "Nothing assigned";
      } else if (color.equalsIgnoreCase("blue")) {
         return "Not started";
      } else if (color.equalsIgnoreCase("#FFBF00")) {
         return "In progress";
      } else if (color.equalsIgnoreCase("#01DF01")) {
         return "Successful";
      } else {
         return color.equalsIgnoreCase("red") ? "Failed" : "Unknown";
      }
   }

   public static class UpgradeException extends IOException {
      private static final long serialVersionUID = -4431416215763105278L;
      private String message;

      public UpgradeException(String message) {
         this.message = message;
      }

      @Override
      public String getMessage() {
         return this.message;
      }
   }

   public static class UpgradeResult {
      public String status = "Success";
      public boolean hasUncompatible = false;
      public List<String> uncompatibleTvList = new ArrayList<>();
      public boolean hasPartUncompatible = false;
      public String message;

      public String toStatusJson() {
         JSONObject resObj = new JSONObject();
         resObj.put("status", this.status);
         resObj.put("hasUncompatible", this.hasUncompatible ? "yes" : "no");
         resObj.put("hasPartUncompatible", this.hasPartUncompatible ? "yes" : "no");
         resObj.put("message", this.message);
         return resObj.toString();
      }
   }
}
