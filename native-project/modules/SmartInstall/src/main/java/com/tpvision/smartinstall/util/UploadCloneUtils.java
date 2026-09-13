package com.tpvision.smartinstall.util;

import com.google.gson.Gson;
import com.tpvision.smartinstall.SmartInstallConfiguration;
import com.tpvision.smartinstall.androidapp.AndroidApplications;
import com.tpvision.smartinstall.bean.HotelInfo;
import com.tpvision.smartinstall.core.SettingChannelBean;
import com.tpvision.smartinstall.core.SettingChannelLoader;
import com.tpvision.smartinstall.dao.core.AppPackage;
import com.tpvision.smartinstall.dao.core.Banners;
import com.tpvision.smartinstall.dao.core.ChannelPackage;
import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.Schedule;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.core.SettingPackage;
import com.tpvision.smartinstall.dao.core.SmartinfoSetting;
import com.tpvision.smartinstall.dao.core.Smartui;
import com.tpvision.smartinstall.dao.core.UiCustomizations;
import com.tpvision.smartinstall.dao.mgr.AppPackageManager;
import com.tpvision.smartinstall.dao.mgr.ChannelPackageManager;
import com.tpvision.smartinstall.dao.mgr.DevicesManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.SettingManager;
import com.tpvision.smartinstall.dao.mgr.SettingPackageManager;
import com.tpvision.smartinstall.dao.mgr.SmartinfoSettingManager;
import com.tpvision.smartinstall.dao.mgr.SmartuiManager;
import com.tpvision.smartinstall.dao.mgr.UiCustomizationsManager;
import com.tpvision.smartinstall.servlet.IPTVPooling;
import com.tpvision.smartinstall.weather.WeatherServiceImpl;
import com.tpvision.smartinstall.xml.Channel;
import com.tpvision.smartinstall.xml.Config;
import com.tpvision.smartinstall.xml.Crc;
import com.tpvision.smartinstall.xml.CrcFiles;
import com.tpvision.smartinstall.xml.Platform;
import com.tpvision.smartinstall.xml.SettingFiles;
import com.tpvision.smartinstall.xml.Settings;
import com.tpvision.smartinstall.xml.UnchangedFiles;
import com.tpvision.smartinstall.xml.channel.v4.TvContents;
import com.tpvision.smartinstall.xml.channel.v5.Broadcast;
import com.tpvision.smartinstall.xml.es2k14.HotelModeSettings;
import com.tpvision.smartinstall.xml.es2k14.HotelModelSettingsForTpn142;
import com.tpvision.smartinstall.xml.remotediagnose.DIAGNOSTICANALYTIC;
import com.tpvision.smartinstall.xml.setting.v2k16.Item;
import com.tpvision.smartinstall.xml.setting.v2k16.TVSettings;
import com.tpvision.smartinstall.xml.setting.v2k16.roomspecific.RoomSpecificSettings;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.imgscalr.Scalr;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UploadCloneUtils {
   private static final Logger LOG = LoggerFactory.getLogger(UploadCloneUtils.class);
   public static final Map<String, Integer> uploadItemsRecvCount_cmnd = new HashMap<>();
   public static final Map<String, Integer> uploadItemsCount_tv = new HashMap<>();
   private static final String MS2K14 = "MS2K14";
   private static final String MS2K16 = "MS2K16";
   private static final String ES2K16 = "ES2K16";
   public static final int totalCount = 30;

   private UploadCloneUtils() {
   }

   private static Platform loadPlatformTVSetting(String clonePath, String platformId) throws UploadException {
      UploadPlatformType uploadPlatformType = PlatformUtils.fromPlatfromId(platformId);
      int mark = 0;
      if (uploadPlatformType == UploadPlatformType.folderLocationforAndroidClone || uploadPlatformType == UploadPlatformType.folderLocationfor2K16ES) {
         mark = 1;
      }

      Platform platform = null;
      File file = new File(clonePath);
      File[] secondRootFileLists = file.listFiles();
      if (secondRootFileLists == null) {
         throw new UploadException(UploadException.ExceptionType.InvalidCloneFolderName);
      }

      File masterCloneDataDir = getMasterCloneDataDir(file);
      if (platformId.toUpperCase().contains("Q55")) {
         try {
            platform = getPlatform_Q55(masterCloneDataDir);
         } catch (MalformedCloneDataException e) {
            LOG.error(e.getMessage(), e);
         }
      } else if (Arrays.asList("TPN142HE_CloneData", "TPN141HE_CloneData").contains(platformId)) {
         platform = getPlatformES2K12(masterCloneDataDir, mark, platformId);
      } else {
         File tVSettingsDir = TpvFileUtils.getDirectoryByName(masterCloneDataDir, "TVSettings");
         if (tVSettingsDir != null) {
            platform = getPlatformES2K12(tVSettingsDir, mark, platformId);
         }
      }

      if (platform != null) {
         for (com.tpvision.smartinstall.xml.Setting set : platform.getSettings().getSetting()) {
            if ("TV Settings.Picture.Advanced.Sharpness.Super Resolution".equalsIgnoreCase(set.getItem())
               || "TV Settings.Picture.Advanced.Sharpness.Ultra Resolution".equalsIgnoreCase(set.getItem())
               || "TV Settings.Picture.Advanced.Sharpness.8K Ultra resolution".equalsIgnoreCase(set.getItem())) {
               set.setItem("TV Settings.Picture.Advanced.Sharpness.Super Resolution");
               break;
            }
         }
      }

      return platform;
   }

   public static SettingPackage loadConfToSettingPackageDb(
      String processPlatformDir, String configName, SettingChannelBean settingChannelBean, String platformId
   ) {
      SettingPackageManager settingPackageManager = JpaManager.getSettingPackageManager();
      SettingPackage settingPackage = new SettingPackage();
      String termPath = processPlatformDir + "/MasterCloneData/TVSettings";
      String termJsonContent = readFileContent(termPath + File.separator + "CustomTermsAndConditions.json");
      String htvTlsPskKeyContent = readFileContent(termPath + File.separator + "HTV_TLS_PSK.KEY");
      settingPackage.setName(configName);
      settingPackage.setPlatform(PlatformUtils.getPlatformName(platformId));
      settingPackage.setValue(settingChannelBean.exportSettingPackageSaveJson());
      settingPackage.setTermAndConditions(termJsonContent);
      settingPackage.setHtvTlsPskKey(htvTlsPskKeyContent);
      settingPackageManager.save(settingPackage);
      return settingPackage;
   }

   public static String generateSaveCloneName(String zipName) {
      String configName = null;
      SettingManager smgr = JpaManager.getSettingManager();
      List<Setting> settings = smgr.findSettingsByNameStartingWith(zipName);
      if (null != settings) {
         configName = zipName + "_" + settings.size();
         List<String> names = new ArrayList<>();

         for (int j = 0; settings.size() != j; j++) {
            names.add(settings.get(j).getName());
         }

         for (int value = settings.size(); settingNameExists(configName, names); value++) {
            configName = zipName + "_" + value;
         }
      }

      return configName;
   }

   private static boolean settingNameExists(String configName, List<String> names) {
      for (String string : names) {
         if (string.equalsIgnoreCase(configName)) {
            return true;
         }
      }

      return false;
   }

   private static String updateHotelInfo(String processPlatformDir, String userName, String configName, String platformId) throws UploadException {
      String ret = "";
      File hotelInfoDir = null;
      String configLocation = processPlatformDir + PlatformUtils.getPreProcessPath(platformId);
      String hotelInfoName = "HotelInfo";
      if ("TPN141HE_CloneData".equalsIgnoreCase(platformId) || "TPN142HE_CloneData".equalsIgnoreCase(platformId)) {
         configLocation = configLocation + "/SmartInfoImages";
         hotelInfoName = "file_";
      } else if ("TPM1532HE_CloneData".equalsIgnoreCase(platformId)
         || "TPM1531HE_CloneData".equalsIgnoreCase(platformId)
         || "TPN161HE_CloneData".equalsIgnoreCase(platformId)) {
         configLocation = configLocation + "/WelcomeLogo";
         hotelInfoName = "file_";
      }

      File parentFileForHotelInfo = new File(configLocation);
      int imgcount = 0;
      if (parentFileForHotelInfo.exists()) {
         File[] hotelInfoFileLists = parentFileForHotelInfo.listFiles();

         for (File f : hotelInfoFileLists) {
            if (f.getName().indexOf("jpeg") > -1 || f.getName().indexOf("jpg") > -1 || f.getName().indexOf("png") > -1) {
               try {
                  if (parentFileForHotelInfo.getPath().indexOf(platformId) > -1) {
                     if (f.getName().indexOf(hotelInfoName) <= -1) {
                        continue;
                     }

                     imgcount++;
                  }
               } catch (Exception e) {
                  LOG.error(e.getMessage(), e);
               }

               String rawPath = parentFileForHotelInfo.getAbsolutePath() + parentFileForHotelInfo.getName() + f.getName();
               ret = DigestUtils.md5Hex(rawPath) + ".jpg";
            }

            try {
               if (imgcount > 30) {
                  throw new UploadException(UploadException.ExceptionType.HotelInfoImageExcedsTotalCount);
               }

               if (f.getName().indexOf(hotelInfoName) > -1) {
                  hotelInfoDir = new File(CommonConstants.HOTEL_INFO_ES_LOCATION + "/" + configName);
                  FileUtils.copyFileToDirectory(f, hotelInfoDir);
               }
            } catch (IOException e) {
               LOG.error(e.getMessage(), e);
            }
         }
      }

      ret = "";
      String jsonStr = "";

      try {
         File hotelfile = new File(CommonConstants.HOTEL_INFO_ES_LOCATION);
         File thumbfile = new File(CommonConstants.HOTEL_INFO_ES_THUMB_LOCATION);
         Gson gson = new Gson();
         HotelInfo hinf = new HotelInfo();
         if (null != hotelInfoDir && hotelInfoDir.exists()) {
            for (File source : hotelInfoDir.listFiles()) {
               String ret1 = DigestUtils.md5Hex(hotelInfoDir.getAbsolutePath() + hotelInfoDir.getName() + source.getName()) + ".jpeg";
               File destThumbFile = new File(thumbfile + "/" + ret1);

               try {
                  BufferedImage image = ImageIO.read(source);
                  if (null != image) {
                     BufferedImage thumbnail = Scalr.resize(image, 122, 69);
                     ImageIO.write(thumbnail, "jpeg", destThumbFile);
                     image.flush();
                     thumbnail.flush();
                     new File(hotelInfoDir + "/" + source.getName()).renameTo(new File(hotelInfoDir + "/" + ret1));
                  }
               } catch (IOException e) {
                  LOG.error(e.getMessage(), e);
                  ret1 = null;
               }

               hinf.getHotelinfo().add(ret1);
            }

            FileUtils.copyDirectory(hotelInfoDir, hotelfile);
            FileUtils.deleteDirectory(hotelInfoDir);
            jsonStr = gson.toJson(hinf);
            String sname = configName;
            loadSmartUIToDb(userName, configName, sname, jsonStr);
            loadsmartinfoToDb(userName, configName, sname);
         }
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }

      return ret;
   }

   private static void loadSmartUIToDb(String userName, String configName, String sname, String jsonStr) {
      SmartuiManager suimgr = JpaManager.getSmartuiManager();
      List<Smartui> smartui = suimgr.findSmartuiByTypeAndIsDeleteAndName("SINFOES", "N", configName);
      if (!smartui.isEmpty()) {
         Smartui set = smartui.get(0);
         set.setLastUpdateBy(userName);
         set.setLastUpdateDate(new Date());
         suimgr.save(set);
      } else {
         Smartui set = new Smartui();
         set.setName(configName);
         byte[] json = null;
         json = jsonStr.getBytes();
         set.setValue(json);
         set.setCreatedBy(userName);
         set.setCreatedDate(new Date());
         set.setLastUpdateBy(userName);
         set.setLastUpdateDate(new Date());
         set.setType("SINFOES");
         suimgr.save(set);
      }
   }

   private static void loadsmartinfoToDb(String userName, String configName, String sname) {
      SmartinfoSettingManager sinfomgr = JpaManager.getSmartinfoSettingManager();
      SmartuiManager suimgr = JpaManager.getSmartuiManager();
      SettingManager smgr = JpaManager.getSettingManager();
      List<Setting> settings = smgr.findSettingsByName(sname);
      Setting setseting = settings.get(0);
      List<Smartui> smartui = suimgr.findSmartuiByTypeAndIsDeleteAndName("SINFOES", "N", configName);
      Smartui setsmartui = smartui.get(0);
      List<SmartinfoSetting> setinfo = sinfomgr.findSmartinfoSettingBySettingId(settings.get(0).getId());
      if (!setinfo.isEmpty()) {
         SmartinfoSetting set = setinfo.get(0);
         set.setSmartuiId(setsmartui.getId());
         set.setLastUpdatedBy(userName);
         set.setLastUpdatedDate(new Date());
         sinfomgr.save(set);
      } else {
         SmartinfoSetting setinfoset = new SmartinfoSetting();
         setinfoset.setSmartuiId(setsmartui.getId());
         setinfoset.setSettingId(setseting.getId());
         setinfoset.setCreatedBy(userName);
         setinfoset.setCreatedDate(new Date());
         setinfoset.setLastUpdatedBy(userName);
         setinfoset.setLastUpdatedDate(new Date());
         sinfomgr.save(setinfoset);
      }
   }

   private static <T> void handleAnalogChannelParams(List<T> channelList) {
      for (T channelT : channelList) {
         if (channelT instanceof com.tpvision.smartinstall.xml.channel.v5.Channel) {
            com.tpvision.smartinstall.xml.channel.v5.Channel channel = (com.tpvision.smartinstall.xml.channel.v5.Channel)channelT;
            Broadcast broadcast = channel.getBroadcast();
            if (null != broadcast && broadcast.getMedium().equalsIgnoreCase("Analog")) {
               if (broadcast.getONID() == null || broadcast.getONID().isEmpty()) {
                  broadcast.setONID("0");
               }

               if (broadcast.getTSID() == null || broadcast.getTSID().isEmpty()) {
                  broadcast.setTSID("0");
               }

               if (broadcast.getSymbolrate() == null || broadcast.getSymbolrate().isEmpty()) {
                  broadcast.setSymbolrate("0");
               }

               LOG.debug("broadcast=" + broadcast);
            }
         }
      }
   }

   private static Setting saveSettingToDb(
      String processPlatformDir, String userName, String configName, String type, SettingChannelBean settingChannelBean, String platformId
   ) {
      String websiteName = "";

      try {
         websiteName = loadWebsiteNameFromSmartInfoIdentifier(processPlatformDir, configName, platformId);
      } catch (Exception var13) {
      }

      SettingManager smgr = JpaManager.getSettingManager();
      Setting set = new Setting();
      set.setCreatedBy(userName);
      set.setCreatedDate(new Date());
      set.setLastUpdatedBy(userName);
      set.setLastUpdatedDate(new Date());
      set.setValue(settingChannelBean.exportSettingSaveJson());
      set.setAndroidApps("");
      set.setContent(websiteName);
      SettingState ss = new SettingState(settingChannelBean);
      Map<String, String> map = ss.getSettingToValuesMap();
      String geonameID = map.get("Advanced.Identification Settings.Premises Geonames ID");
      if (geonameID == null) {
         geonameID = map.get("Identification Settings.Premises Geonames ID");
      }

      set.setGeonameId(geonameID);
      set.setPlatform(platformId);
      set.setName(configName);
      set.setClonerename(configName);
      set.setIsdelete("N");
      set.setType(type);
      smgr.save(set);
      WeatherServiceImpl wsi = new WeatherServiceImpl();
      wsi.refreshCurrentWeather(geonameID, null);
      return set;
   }

   public static Setting loadAllConfToDb(String unZipCloneLocationStr, String userName, String configName, String type, String platformId) throws Exception {
      deleteIdFilesInZipfile(unZipCloneLocationStr);
      copyUnzipCloneFolderToProcessDir(configName, unZipCloneLocationStr, platformId);
      String processPlatformDir = CommonConstants.CLONE_PROCESS_LOCATION + configName + File.separator + platformId + File.separator;
      SettingChannelBean settingChannelBean = getSettingChannelBean(processPlatformDir, platformId);
      Setting setting = saveSettingToDb(processPlatformDir, userName, configName, type, settingChannelBean, platformId);
      if (settingChannelBean.getSetttings() != null) {
         SettingPackage settingPackage = loadConfToSettingPackageDb(processPlatformDir, configName, settingChannelBean, platformId);
         setting.setSettingPackageId(settingPackage.getId());
      }

      ChannelPackage channelPackage = saveChannelPackageToDB(processPlatformDir, userName, configName, settingChannelBean, platformId);
      if (channelPackage != null) {
         setting.setChannelPackageId(channelPackage.getId());
      }

      AppPackage appPackage = loadConfToAppPackageDb(processPlatformDir, configName, platformId);
      if (appPackage != null) {
         setting.setAppPackageId(appPackage.getId());
      }

      Banners banners = loadConfToBanners(processPlatformDir, configName);
      if (banners != null) {
         setting.setBannersId(banners.getId());
      }

      UiCustomizations uiCustomizations = loadConfToUiCustomizationsDb(processPlatformDir, configName, platformId);
      if (uiCustomizations != null) {
         setting.setUiCustomizationsId(uiCustomizations.getId());
      }

      Schedule schedule = saveScheduleToDB(processPlatformDir, configName, platformId);
      if (schedule != null) {
         setting.setScheduleId(schedule.getId());
      }

      parseProfessionalApps(processPlatformDir);

      try {
         int welcomeId = WelcomeLogoUtils.getInstance().saveWelcomeToDb(platformId, CommonConstants.CLONE_PROCESS_LOCATION + configName, configName, userName);
         if (welcomeId > 0) {
            setting.setWelcomeId(welcomeId);
         }
      } catch (UploadException e) {
         LOG.warn(e.getMessage());
      }

      updateHotelInfo(processPlatformDir, userName, configName, platformId);
      JpaManager.getSettingManager().save(setting);
      return setting;
   }

   public static SettingChannelBean getSettingChannelBean(String platformDirPath, String platformId) throws IOException, JAXBException, UploadException {
      Platform platform = loadPlatformTVSetting(platformDirPath, platformId);
      if (platform != null && !platformId.equals(platform.getSwver())) {
         throw new UploadException(UploadException.ExceptionType.CloneVersionNotCompatible);
      }

      Settings settings = platform != null ? platform.getSettings() : null;
      TvContents v4Channel = null;
      com.tpvision.smartinstall.xml.channel.v5.TvContents v5Channel = null;
      com.tpvision.smartinstall.xml.channel.v6.TvContents v6Channel = null;
      DIAGNOSTICANALYTIC diagnosticAnalytic = new DIAGNOSTICANALYTIC();
      RoomSpecificSettings roomSettings = new RoomSpecificSettings();
      String channelVersion = PlatformUtils.getChannelVersion(platformId);
      switch (channelVersion) {
         case "v4":
            v4Channel = SettingChannelLoader.loadChannelV4(platformDirPath, platformId);
            break;
         case "v5":
            v5Channel = SettingChannelLoader.loadChannelV5(platformDirPath);
            roomSettings = getRoomSettings(platformDirPath);
            break;
         case "v6":
            v6Channel = SettingChannelLoader.loadChannelV6(platformDirPath);
            break;
         default:
            LOG.info("sw version mismatching!");
      }

      if (null != v4Channel && v4Channel.getChannelMap() != null) {
         handleAnalogChannelParams(v4Channel.getChannelMap().getChannel());
      }

      if (null != v5Channel && v5Channel.getChannelMap() != null) {
         handleAnalogChannelParams(v5Channel.getChannelMap().getChannel());
      }

      SettingChannelBean scb = new SettingChannelBean(settings, v4Channel, v5Channel, channelVersion, diagnosticAnalytic, roomSettings);
      if (channelVersion.equalsIgnoreCase("v6")) {
         scb.setV6Channel(v6Channel);
      }

      return scb;
   }

   public static Schedule saveScheduleToDB(String processPlatformDir, String configName, String platformId) {
      if ("TPS191HE_CloneData".equals(platformId)) {
         LOG.warn("2k19ES not support schedule !");
         return null;
      }

      String schedulePath = processPlatformDir + "/MasterCloneData/Schedules";
      File scheduleFile = new File(schedulePath);
      if (!scheduleFile.exists()) {
         LOG.warn("Schedules folder is missing !");
         return null;
      }

      String scheduleJsonPath = schedulePath + File.separator + "Schedules.json";
      File scheduleJsonFile = new File(scheduleJsonPath);
      if (!scheduleJsonFile.exists()) {
         LOG.warn("Schedules json file is missing !");
         return null;
      }

      StringBuilder content = new StringBuilder();

      try {
         String sb = FileUtils.readFileToString(scheduleJsonFile, StandardCharsets.UTF_8);
         JSONObject jsonObject = new JSONObject(sb);
         if (jsonObject.has("Schedules")) {
            JSONArray schedulesArr = jsonObject.getJSONArray("Schedules");
            int schedulesLen = schedulesArr.length();
            JSONArray afterCheckScheduleArr = new JSONArray();

            for (int i = 0; i < schedulesLen; i++) {
               JSONObject jsonObj = (JSONObject)schedulesArr.get(i);
               String startTime = jsonObj.optString("StartTime");
               String endTime = jsonObj.optString("EndTime");
               if (startTime.compareTo(endTime) < 0) {
                  afterCheckScheduleArr.put(jsonObj);
               }
            }

            JSONArray sortScheduleArr = Utils.reSortJSONArray(afterCheckScheduleArr);
            content.append("{\"Schedules\":");
            content.append(sortScheduleArr.toString());
            content.append("}");
         }
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }

      Schedule schedule = new Schedule();
      schedule.setName(configName);
      schedule.setPlatform(PlatformUtils.getPlatformName(platformId));
      schedule.setSchedule("");
      schedule.setContent(content.toString());
      JpaManager.getScheduleManager().save(schedule);
      String scheduleDirectoryStr = CloneItemUtils.getSchedulesPackageDataPath(schedule.getId());
      File scheduleDirectoryDir = new File(scheduleDirectoryStr);
      if (!scheduleDirectoryDir.exists()) {
         scheduleDirectoryDir.mkdirs();
      }

      try {
         FileUtils.copyDirectoryToDirectory(scheduleFile, scheduleDirectoryDir);
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }

      return schedule;
   }

   private static String getTargetPath(String baseDir, String targetFolder) {
      List<String> filelist = null;
      String tmpFolder = baseDir;
      filelist = TpvFileUtils.findSubFolders(new File(tmpFolder));

      for (int i = 0; i < filelist.size(); i++) {
         tmpFolder = filelist.get(i);
         if (tmpFolder.endsWith(targetFolder)) {
            return tmpFolder;
         }

         tmpFolder = getTargetPath(tmpFolder, targetFolder);
         if (tmpFolder != null) {
            return tmpFolder;
         }
      }

      return null;
   }

   public static Banners loadConfToBanners(String processPlatformDir, String configName) {
      String bannerFolderPath = getTargetPath(processPlatformDir, "Banner");
      return bannerFolderPath == null ? null : BannerTemplateUtils.extractBannerUploadFileToBanner(new File(bannerFolderPath), configName);
   }

   public static UiCustomizations loadConfToUiCustomizationsDb(String processPlatformDir, String configName, String platformId) {
      if (!PlatformUtils.isLoadDataForUicustomizations(platformId)) {
         return null;
      }

      File sourceDir = new File(processPlatformDir + "/MasterCloneData/ProfessionalAppsData");
      if (!sourceDir.exists()) {
         LOG.warn("ProfessionalAppsData not exists");
         return null;
      }

      try {
         JSONObject obj = new JSONObject();
         File dashboardsettingsFile = new File(sourceDir + "/PhilipsHome/Dashboardsettings.xml");
         if (dashboardsettingsFile.exists()) {
            SAXReader reader = new SAXReader();
            Document document = reader.read(dashboardsettingsFile);
            Element root = document.getRootElement();
            List<?> list = root.elements();
            int i = 0;

            for (int j = list.size(); i < j; i++) {
               Element e = (Element)list.get(i);
               obj.put(e.elementText("Name"), e.elementText("Value"));
            }
         } else {
            LOG.warn("Dashboardsettings.xml not exsits, save empty string to UiCustomizations");
         }

         UiCustomizationsManager uimgr = JpaManager.getUiCustomizationsManager();
         UiCustomizations ui = new UiCustomizations();
         ui.setName(configName);
         ui.setPlatform(PlatformUtils.getPlatformName(platformId));
         ui.setValue(obj.toString());
         uimgr.save(ui);
         File targetDir = new File(CommonConstants.CLONE_PROCESS_LOCATION + "uiCustomizations/" + ui.getId());
         FileUtils.copyDirectory(sourceDir, targetDir);
         return ui;
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         return null;
      }
   }

   public static ChannelPackage saveChannelPackageToDB(
      String processPlatformDir, String userName, String configName, SettingChannelBean settingChannelBean, String platformId
   ) {
      int length = 0;
      if ("V4".equalsIgnoreCase(settingChannelBean.getChannelVersion())
         && settingChannelBean.getV4Channel() != null
         && settingChannelBean.getV4Channel().getChannelMap() != null
         && settingChannelBean.getV4Channel().getChannelMap().getChannel() != null) {
         length = settingChannelBean.getV4Channel().getChannelMap().getChannel().size();
      } else if ("V5".equalsIgnoreCase(settingChannelBean.getChannelVersion())
         && settingChannelBean.getV5Channel() != null
         && settingChannelBean.getV5Channel().getChannelMap() != null
         && settingChannelBean.getV5Channel().getChannelMap().getChannel() != null) {
         length = settingChannelBean.getV5Channel().getChannelMap().getChannel().size();
      } else if ("V6".equalsIgnoreCase(settingChannelBean.getChannelVersion())
         && settingChannelBean.getV6Channel() != null
         && settingChannelBean.getV6Channel().getChannelMap() != null
         && settingChannelBean.getV6Channel().getChannelMap().getChannel() != null) {
         length = settingChannelBean.getV6Channel().getChannelMap().getChannel().size();
      }

      String channelListDirString = processPlatformDir + "/MasterCloneData/ChannelList";
      File channelListDir = new File(channelListDirString);
      String srcMediaChannel = processPlatformDir + "/MasterCloneData/MediaChannels";
      File srcMediaChannelDir = new File(srcMediaChannel);
      if (length == 0 && !channelListDir.exists() && !srcMediaChannelDir.exists()) {
         LOG.warn("{} not exist ChannelList related folder", channelListDirString);
         return null;
      }

      ChannelPackageManager cpmgr = JpaManager.getChannelPackageManager();
      ChannelPackage cp = new ChannelPackage();
      cp.setName(configName);
      cp.setPlatform(PlatformUtils.getPlatformName(platformId));
      cp.setValue(settingChannelBean.exportChannelPackageJson());
      cp.setNumberOfChs(length);
      cp.setCreatedBy(userName);
      cp.setConfigName(configName);
      cpmgr.save(cp);
      if (channelListDir.exists()) {
         try {
            String channelPackagesDirectoryStr = CommonConstants.CLONE_PROCESS_LOCATION + "ChannelPackages/" + cp.getId();
            File channelPackagesDirectoryDir = new File(channelPackagesDirectoryStr);
            if (!channelPackagesDirectoryDir.exists()) {
               channelPackagesDirectoryDir.mkdirs();
            }

            FileUtils.copyDirectoryToDirectory(channelListDir, channelPackagesDirectoryDir);
            File defaultLogo = new File(channelPackagesDirectoryDir + "/ChannelList/ChannelLogos/default/");
            FileUtils.deleteQuietly(defaultLogo);
            File defaultThemeIcon = new File(channelPackagesDirectoryDir + "/ChannelList/ThemeIcons/default/");
            FileUtils.deleteQuietly(defaultThemeIcon);
            LOG.info("ChannelPackages Copy to {}", channelPackagesDirectoryStr);
         } catch (IOException e) {
            LOG.error(e.getMessage(), e);
         }
      }

      if (srcMediaChannelDir.exists()) {
         String destMediaChannel = CommonConstants.CLONE_PROCESS_LOCATION + "ChannelPackages/" + cp.getId();
         File destMediaChannelDir = new File(destMediaChannel);
         if (!destMediaChannelDir.exists()) {
            destMediaChannelDir.mkdirs();
         }

         try {
            FileUtils.copyDirectoryToDirectory(srcMediaChannelDir, destMediaChannelDir);
            LOG.info("MediaChannel Copy to {}", destMediaChannel);
         } catch (IOException e) {
            LOG.error(e.getMessage(), e);
         }
      }

      return cp;
   }

   private static void parseProfessionalApps(String platformDir) {
      String path = getMasterCloneDataDir(new File(platformDir)).getAbsolutePath() + "/" + CommonConstants.CloneItemType.ProfessionalApps.name();
      if (!new File(path).exists()) {
         LOG.warn("professionalApps not exists");
      } else {
         File professionalAppsPath = new File(path);
         File[] apkFiles = professionalAppsPath.listFiles();
         if (apkFiles != null) {
            File infoFile = new File(path + "/ProfessionalAppsInfo.json");
            if (infoFile.exists()) {
               LOG.info("ProfessionalAppsInfo.json existed");
            } else {
               AndroidApplications applications = new AndroidApplications();
               List<AndroidApplications.AndroidApp> androidAppList = new ArrayList<>();

               for (File apk : apkFiles) {
                  if (FilenameUtils.getExtension(apk.getName()).equalsIgnoreCase("apk")) {
                     AndroidApplications.AndroidApp androidApp = parseAndroidApps(apk.getAbsolutePath());
                     if (androidApp != null) {
                        androidAppList.add(androidApp);
                     }
                  }
               }

               applications.setClonePackages(androidAppList);
               applications.setAvailablePackages(String.valueOf(androidAppList.size()));

               try {
                  FileUtils.writeStringToFile(infoFile, new Gson().toJson(applications), StandardCharsets.UTF_8);
               } catch (IOException e) {
                  LOG.error(e.getMessage(), e);
               }
            }
         }
      }
   }

   private static List<String> getCategoriesByManifestXml(String manifestXml) {
      SAXReader reader = new SAXReader();
      List<String> categoryList = new ArrayList<>();

      try {
         Document document = reader.read(new StringReader(manifestXml));
         Element root = document.getRootElement();

         for (Node categoryNode : root.selectNodes("/manifest/application/*/intent-filter/category")) {
            String fullCategory = categoryNode.valueOf("@android:name");
            if (StringUtils.isNoneBlank(fullCategory) && !categoryList.contains(fullCategory)) {
               categoryList.add(fullCategory);
            }
         }
      } catch (DocumentException e) {
         LOG.error(e.getMessage(), e);
      }

      return categoryList;
   }

   private static AndroidApplications.AndroidApp parseAndroidApps(String apkFileName) {
      try {
         File fileApk = new File(apkFileName);
         if (!fileApk.exists()) {
            throw new IOException("apk file not exits," + apkFileName);
         }

         try (ApkFile apkFile = new ApkFile(fileApk)) {
            ApkMeta apkMeta = apkFile.getApkMeta();
            AndroidApplications.AndroidApp androidApp = new AndroidApplications.AndroidApp();
            androidApp.setPackageURI(fileApk.getName());
            androidApp.setPackageType("LOCAL");
            androidApp.setPackageName(apkMeta.getPackageName());
            Set<Locale> locales = apkFile.getLocales();
            List<String> localeNames = new ArrayList<>();

            for (Locale locale : locales) {
               String localeName = locale.getCountry();
               if (!localeName.isEmpty() && !localeNames.contains(localeName)) {
                  localeNames.add(localeName);
               }
            }

            androidApp.setPackageCountry(localeNames.toArray(new String[0]));
            String manifestXml = apkFile.getManifestXml();
            List<String> categoryList = getCategoriesByManifestXml(manifestXml);
            androidApp.setPackageCategory(categoryList.toArray(new String[0]));
            return androidApp;
         }
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
         return null;
      }
   }

   public static AppPackage loadConfToAppPackageDb(String processPlatformDir, String configName, String platformId) {
      if (!PlatformUtils.hasPackageFeature(platformId)) {
         LOG.warn("platform not support app");
         return null;
      }

      String directoryStr = processPlatformDir + "/MasterCloneData/AndroidApps";
      File directoryDir = new File(directoryStr);
      if (!directoryDir.exists()) {
         LOG.warn("upload android app path is null");
         return null;
      }

      String androidAppsPath = directoryStr + "/AndroidAppsMetaData.json";
      File androidAppsFile = new File(androidAppsPath);
      if (!androidAppsFile.exists()) {
         LOG.warn("upload android app path is null");
         return null;
      }

      AppPackageManager appPackageManager = JpaManager.getAppPackageManager();
      AppPackage appPackage = new AppPackage();
      appPackage.setName(configName);
      appPackage.setPlatform(PlatformUtils.getPlatformName(platformId));
      String androidApps = "";

      try {
         androidApps = FileUtils.readFileToString(androidAppsFile, StandardCharsets.UTF_8);
      } catch (IOException e1) {
         LOG.error("android json file read error", e1);
      }

      String number = null;
      String sizeStr = null;
      if (StringUtils.isEmpty(androidApps)) {
         androidApps = "";
         number = "0";
         sizeStr = "0 KB";
      } else {
         AndroidApplications androidApplications = new Gson().fromJson(androidApps, AndroidApplications.class);
         number = androidApplications.getAvailablePackages();
         long size = FileUtils.sizeOfDirectory(directoryDir);
         sizeStr = FileUtils.byteCountToDisplaySize(size);
         if (size <= 1024L) {
            androidApps = "";
            number = "0";
            sizeStr = "0 KB";
         }
      }

      appPackage.setValue(androidApps);
      appPackage.setNumber(Integer.parseInt(number));
      appPackage.setSize(sizeStr);
      appPackageManager.save(appPackage);
      String appPackagesDirectoryStr = CommonConstants.CLONE_PROCESS_LOCATION + "AppPackages/" + appPackage.getId();
      File appPackagesDirectoryDir = new File(appPackagesDirectoryStr);
      if (!appPackagesDirectoryDir.exists()) {
         appPackagesDirectoryDir.mkdirs();
      }

      try {
         FileUtils.copyDirectoryToDirectory(directoryDir, appPackagesDirectoryDir);
         LOG.info("AppPackage Copy to {}", appPackagesDirectoryStr);
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }

      return appPackage;
   }

   private static void deleteIdFilesInZipfile(String path) {
      File[] files = new File(path).listFiles();
      if (null != files) {
         for (File f : files) {
            try {
               if (f.getPath().indexOf(".id") > -1) {
                  FileUtils.forceDelete(f);
               }
            } catch (IOException e) {
               LOG.debug(e.getMessage(), e);
            }
         }
      }
   }

   private static File getMasterCloneDataDir(File platformDir) {
      File masterCloneDataDir = TpvFileUtils.getDirectoryByName(platformDir, "MasterCloneData");
      return masterCloneDataDir != null ? masterCloneDataDir : platformDir;
   }

   private static RoomSpecificSettings getRoomSettings(String platformDir) {
      RoomSpecificSettings roomSettings = null;
      String path = platformDir + "/MasterCloneData/RoomSpecificSettings/RoomSpecificSettings.xml";

      try {
         JAXBContext context = JAXBContext.newInstance("com.tpvision.smartinstall.xml.setting.v2k16.roomspecific");
         Unmarshaller unmarshaller = context.createUnmarshaller();
         File roomSettintXml = new File(path);
         if (roomSettintXml.exists()) {
            try (
               FileInputStream stream = new FileInputStream(roomSettintXml);
               Reader freader = new InputStreamReader(stream, "UTF-8");
            ) {
               roomSettings = (RoomSpecificSettings)unmarshaller.unmarshal(freader);
               roomSettings = verifyRoomSettings(roomSettings);
            } catch (Exception e) {
               LOG.error(e.getMessage(), e);
            }
         } else {
            roomSettings = new RoomSpecificSettings();
         }
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }

      return roomSettings;
   }

   private static RoomSpecificSettings verifyRoomSettings(RoomSpecificSettings roomSettings) {
      if (null != roomSettings && roomSettings.getTV() != null) {
         String serialNumber = roomSettings.getTV().getSerialNumber();
         roomSettings.getTV().setSerialNumber(replaceEnterChar(serialNumber));

         for (com.tpvision.smartinstall.xml.setting.v2k16.roomspecific.Item item : roomSettings.getTV().getItem()) {
            item.setValue(replaceEnterChar(item.getValue()));
         }

         return roomSettings;
      } else {
         return new RoomSpecificSettings();
      }
   }

   private static String replaceEnterChar(String str) {
      if (str.contains("\n")) {
         str = str.replace("\n", "");
      }

      return str;
   }

   private static void copyUnzipCloneFolderToProcessDir(String configName, String unZipCloneLocationStr, String platformId) throws IOException {
      String cloneProcessPath = CommonConstants.CLONE_PROCESS_LOCATION + configName + "/";
      File file1 = new File(cloneProcessPath);
      if (file1.exists()) {
         try {
            FileUtils.deleteDirectory(file1);
         } catch (Exception e) {
            LOG.error(e.getMessage(), e);
         }
      }

      if (!"TPN142HE_CloneData".equalsIgnoreCase(platformId) && !"TPN141HE_CloneData".equalsIgnoreCase(platformId)) {
         FileUtils.copyDirectory(new File(unZipCloneLocationStr), new File(cloneProcessPath));
      } else {
         File processPlatformIdDir = new File(cloneProcessPath + platformId);
         FileUtils.forceMkdir(processPlatformIdDir);
         File platformUnzipPath = new File(unZipCloneLocationStr + "/" + platformId);
         File unzipCSMDir = TpvFileUtils.getDirectoryByName(platformUnzipPath, "CSMDump");
         if (unzipCSMDir != null) {
            FileUtils.copyDirectory(unzipCSMDir, processPlatformIdDir);
         }

         File unzipMasterCloneData = getMasterCloneDataDir(platformUnzipPath);
         FileUtils.copyDirectory(unzipMasterCloneData, processPlatformIdDir);
      }
   }

   public static File getPlatformHandleFolderDir(String unZipCloneLocationStr, String platformId) {
      File platformUnzipPath = new File(unZipCloneLocationStr + "/" + platformId);
      return !"TPN142HE_CloneData".equalsIgnoreCase(platformId) && !"TPN141HE_CloneData".equalsIgnoreCase(platformId)
         ? platformUnzipPath
         : getMasterCloneDataDir(platformUnzipPath);
   }

   private static String loadWebsiteNameFromSmartInfoIdentifier(String processPlatformDir, String configName, String platformId) throws IOException {
      UploadPlatformType uploadPlatformType = PlatformUtils.fromPlatfromId(platformId);
      String unzipPlatformDirPath = processPlatformDir;
      String websiteName = "";
      if (uploadPlatformType != UploadPlatformType.folderLocationforAndroidClone && uploadPlatformType != UploadPlatformType.folderLocationfor2K16ES) {
         File smartInfoPageDir = new File(unzipPlatformDirPath + "/MasterCloneData/SmartInfoPages/");
         if (smartInfoPageDir.exists()) {
            String smartInfoPagesIdPath = unzipPlatformDirPath;
            if ("TPN141HE_CloneData".equals(platformId)) {
               smartInfoPagesIdPath = smartInfoPagesIdPath + "/MasterCloneData/SmartInfoPages/TPN141HE_SmartInfoPages_Identifier.txt";
            } else {
               smartInfoPagesIdPath = smartInfoPagesIdPath + "/MasterCloneData/SmartInfoPages/TPN142HE_SmartInfoPages_Identifier.txt";
            }

            File smartInfoPagesFile = new File(smartInfoPagesIdPath);
            if (smartInfoPagesFile.exists()) {
               websiteName = FileUtils.readFileToString(smartInfoPagesFile, StandardCharsets.UTF_8);
               if (websiteName.length() > 32) {
                  websiteName = TpvStringUtils.limitStringLength(websiteName, 32);
                  FileUtils.writeStringToFile(smartInfoPagesFile, websiteName, StandardCharsets.UTF_8);
               }
            }

            int length = websiteName.trim().length();
            long size = FileUtils.sizeOfDirectory(smartInfoPageDir);
            if (length >= 18) {
               websiteName = websiteName.substring(17);
            } else if (size >= 1024L) {
               websiteName = configName;
            } else {
               websiteName = "";
            }

            LOG.info("2K14 websiteName={}", websiteName);
         }
      } else {
         File smartInfoBrowserDir = new File(unzipPlatformDirPath + "/MasterCloneData/SmartInfoBrowser/");
         if (smartInfoBrowserDir.exists()) {
            String smartInfoBrowserIdPath = unzipPlatformDirPath + "/MasterCloneData/SmartInfoBrowser/SmartInfoBrowser_Identifier.txt";
            File smartInfoBrowserIdFile = new File(smartInfoBrowserIdPath);
            if (smartInfoBrowserIdFile.exists()) {
               websiteName = FileUtils.readFileToString(smartInfoBrowserIdFile, StandardCharsets.UTF_8);
               if (websiteName.length() > 32) {
                  websiteName = TpvStringUtils.limitStringLength(websiteName, 32);
                  FileUtils.writeStringToFile(smartInfoBrowserIdFile, websiteName, StandardCharsets.UTF_8);
               }
            }

            int length = websiteName.trim().length();
            long size = FileUtils.sizeOfDirectory(smartInfoBrowserDir);
            if (length >= 18) {
               websiteName = websiteName.substring(17);
            } else if (size >= 1024L) {
               websiteName = configName;
            } else {
               websiteName = "";
            }

            LOG.info("2K16 websiteName={}", websiteName);
         }
      }

      return websiteName;
   }

   private static Platform getPlatformES2K12(File file, int mark, String platformId) {
      List<File> settingFileList = new ArrayList<>();
      if (null != file && file.exists()) {
         File[] files = file.listFiles();

         for (File f : files) {
            if (f.isFile()
               && f.getName().endsWith("xml")
               && (mark == 1 && f.getName().startsWith("TVSettings") || mark == 0 && f.getName().indexOf("SSB") > -1)) {
               settingFileList.add(f);
            }
         }
      }

      if (settingFileList.isEmpty()) {
         return null;
      }

      Platform platform = new Platform();
      String platformType = settingFileList.get(0).getName();
      if (platformType.indexOf(platformId.split("_")[0]) > -1 || platformType.indexOf("TVSettings") > -1) {
         platform.setCloneRootFolderName(platformId);
         platform.setSwver(platformId);
      }

      platform.setId(file.getName());
      List<File> binFiles = TpvFileUtils.findFiles(file, null);
      if (null != binFiles && binFiles.size() > 0) {
         UnchangedFiles uf = new UnchangedFiles();

         for (File f : binFiles) {
            String name = f.getAbsolutePath();
            int index = name.indexOf(platform.getCloneRootFolderName()) + platform.getCloneRootFolderName().length() + 1;
            String path = name.substring(index);
            name = f.getName();
            com.tpvision.smartinstall.xml.File fileObj = new com.tpvision.smartinstall.xml.File();
            fileObj.setName(name);
            fileObj.setPath(path);
            uf.getFile().add(fileObj);
         }

         platform.setUnchangedFiles(uf);
      }

      List<File> crcFiles = findCRCFiles(file);
      CrcFiles crcFilesList = getCrcFileDetails(crcFiles, platform);
      platform.setCrcFiles(crcFilesList);
      if (!settingFileList.isEmpty()) {
         SettingFiles settingFilesForPlatform = new SettingFiles();

         for (File f : settingFileList) {
            String absolutePath = f.getAbsolutePath();
            String name = f.getName();
            int index = absolutePath.indexOf(platform.getCloneRootFolderName()) + platform.getCloneRootFolderName().length() + 1;
            String path = absolutePath.substring(index);
            com.tpvision.smartinstall.xml.File fileObj = new com.tpvision.smartinstall.xml.File();
            fileObj.setName(name);
            fileObj.setPath(path);
            if (f.getName().endsWith("xml")) {
               fileObj.setType("XML");
            } else {
               fileObj.setType("PXML");
            }

            settingFilesForPlatform.getFile().add(fileObj);
         }

         platform.setSettingFiles(settingFilesForPlatform);

         try {
            JAXBContext context14 = null;
            Unmarshaller unmarshaller14 = null;
            JAXBContext context14_tpn142 = null;
            Unmarshaller unmarshaller14_tpn142 = null;
            HotelModeSettings hmSettings2K14 = null;
            HotelModelSettingsForTpn142 hmSettings2K14_tpn142 = null;
            if (platformType.indexOf("TVSettings") == -1) {
               File settingFile = settingFileList.get(0);
               if (platformType.indexOf("TPN141HE") != -1) {
                  context14 = JAXBContext.newInstance(HotelModeSettings.class);
                  unmarshaller14 = context14.createUnmarshaller();
                  hmSettings2K14 = (HotelModeSettings)unmarshaller14.unmarshal(settingFile);
               } else if (platformType.indexOf("TPN142HE") != -1) {
                  context14_tpn142 = JAXBContext.newInstance(HotelModelSettingsForTpn142.class);
                  unmarshaller14_tpn142 = context14_tpn142.createUnmarshaller();
                  hmSettings2K14_tpn142 = (HotelModelSettingsForTpn142)unmarshaller14_tpn142.unmarshal(settingFile);
               } else {
                  LOG.info("platformType:{}", platformType);
               }
            }

            List<com.tpvision.smartinstall.xml.Setting> settingList = null;
            Settings settings = new Settings();
            if (platformType.indexOf("TPN141HE") > -1) {
               settingList = getCanonicalSettingFromMS2K14(hmSettings2K14);
            } else if (platformType.indexOf("TPN142HE") > -1) {
               settingList = getCanonicalSettingFromMS2K14ForTpn142(hmSettings2K14_tpn142);
            } else if (platformType.indexOf("TVSettings") > -1) {
               settingList = getCanonicalSettingFrom2K16(file, platformId);
            }

            settings.getSetting().addAll(settingList);
            platform.setSettings(settings);
         } catch (JAXBException e) {
            LOG.error(e.getMessage(), e);
         }
      }

      return platform;
   }

   private static List<File> findCRCFiles(File file) {
      List<File> files = new ArrayList<>();
      File[] childrenFiles = file.listFiles();

      for (File f : childrenFiles) {
         if (f.isDirectory()) {
            files.addAll(findSettingsFiles(f));
         } else {
            try (
               FileReader fread = new FileReader(f);
               BufferedReader br = new BufferedReader(fread);
            ) {
               boolean hasCrc = false;
               boolean hasDateFormat = false;

               String line;
               while ((line = br.readLine()) != null) {
                  if (line.indexOf("CRC") > -1) {
                     hasCrc = true;
                  }

                  if (!hasDateFormat) {
                     SimpleDateFormat sdp = TpvDateUtils.getSimpleDateFormatWithEnglishLocale("yyyy/MM/dd-HH:mm");

                     try {
                        sdp.parse(line);
                        hasDateFormat = true;
                     } catch (ParseException var41) {
                     }
                  }

                  if (hasCrc && hasDateFormat || hasDateFormat) {
                     LOG.debug("Identified " + f.getName() + " as CRC file");
                     files.add(f);
                     break;
                  }
               }
            } catch (Exception e) {
               LOG.error(e.getMessage(), e);
            }
         }
      }

      return files;
   }

   private static List<File> findSettingsFiles(File file) {
      List<File> files = new ArrayList<>();
      File[] childrenFiles = file.listFiles();

      for (File f : childrenFiles) {
         if (f.isDirectory()) {
            files.addAll(findSettingsFiles(f));
         } else if (f.getName().endsWith(".txt")) {
            try (
               FileReader fread = new FileReader(f);
               BufferedReader br = new BufferedReader(fread);
            ) {
               LOG.debug("Analizing file: " + f.getAbsolutePath());
               boolean hasItem = false;
               boolean hasXaddr = false;
               boolean hasLastValue = false;

               String line;
               while ((line = br.readLine()) != null) {
                  if (line.indexOf("HotelModeSettings") > -1) {
                     files.add(f);
                     break;
                  }

                  if (line.indexOf("item") > -1 || line.indexOf("ItemID") > -1) {
                     hasItem = true;
                  }

                  if (line.indexOf("lastvalue") > -1 || line.indexOf("Value") > -1) {
                     hasLastValue = true;
                  }

                  if (line.indexOf("Xaddr") > -1) {
                     hasXaddr = true;
                  }

                  if (hasXaddr && hasLastValue && hasItem || hasLastValue && hasItem) {
                     LOG.debug("Identified " + f.getName() + " as settings file");
                     files.add(f);
                     break;
                  }
               }
            } catch (FileNotFoundException e) {
               LOG.error(e.getMessage(), e);
            } catch (IOException e) {
               LOG.error(e.getMessage(), e);
            }
         }
      }

      return files;
   }

   private static List<com.tpvision.smartinstall.xml.Setting> getCanonicalSettingFromMS2K14ForTpn142(HotelModelSettingsForTpn142 hmSettings) {
      List<com.tpvision.smartinstall.xml.Setting> result = new ArrayList<>();
      if (null != hmSettings) {
         com.tpvision.smartinstall.xml.Setting s1 = new com.tpvision.smartinstall.xml.Setting();
         s1.setItem("SwitchOnSrc");
         s1.setLastValue(" " + hmSettings.getSwitchOnSrc() + " ");
         s1.setXaddr("MS2K14");
         result.add(s1);
         com.tpvision.smartinstall.xml.Setting s2 = new com.tpvision.smartinstall.xml.Setting();
         s2.setItem("SwitchOnChn");
         s2.setLastValue(" " + hmSettings.getSwitchOnChn() + " ");
         s2.setXaddr("MS2K14");
         result.add(s2);
         com.tpvision.smartinstall.xml.Setting s3 = new com.tpvision.smartinstall.xml.Setting();
         s3.setItem("SwitchOnVol");
         s3.setLastValue(" " + hmSettings.getSwitchOnVol() + " ");
         s3.setXaddr("MS2K14");
         result.add(s3);
         com.tpvision.smartinstall.xml.Setting s4 = new com.tpvision.smartinstall.xml.Setting();
         s4.setItem("MaximumVol");
         s4.setLastValue(" " + hmSettings.getMaximumVol() + " ");
         s4.setXaddr("MS2K14");
         result.add(s4);
         com.tpvision.smartinstall.xml.Setting s5 = new com.tpvision.smartinstall.xml.Setting();
         s5.setItem("SwitchOnFeature");
         s5.setLastValue(" " + hmSettings.getSwitchOnFeature() + " ");
         s5.setXaddr("MS2K14");
         result.add(s5);
         com.tpvision.smartinstall.xml.Setting s6 = new com.tpvision.smartinstall.xml.Setting();
         s6.setItem("SwitchOnPicFmt");
         s6.setLastValue(" " + hmSettings.getSwitchOnPicFmt() + " ");
         s6.setXaddr("MS2K14");
         result.add(s6);
         com.tpvision.smartinstall.xml.Setting s7 = new com.tpvision.smartinstall.xml.Setting();
         s7.setItem("PowerOn");
         s7.setLastValue(" " + hmSettings.getPowerOn() + " ");
         s7.setXaddr("MS2K14");
         result.add(s7);
         com.tpvision.smartinstall.xml.Setting s8 = new com.tpvision.smartinstall.xml.Setting();
         s8.setItem("LowPowerStandby");
         s8.setLastValue(" " + hmSettings.getLowPowerStandby() + " ");
         s8.setXaddr("MS2K14");
         result.add(s8);
         com.tpvision.smartinstall.xml.Setting s9 = new com.tpvision.smartinstall.xml.Setting();
         s9.setItem("SmartPower");
         s9.setLastValue(" " + hmSettings.getSmartPower() + " ");
         s9.setXaddr("MS2K14");
         result.add(s9);
         com.tpvision.smartinstall.xml.Setting s10 = new com.tpvision.smartinstall.xml.Setting();
         s10.setItem("RebootEveryDay");
         s10.setLastValue(" " + hmSettings.getRebootEveryDay() + " ");
         s10.setXaddr("MS2K14");
         result.add(s10);
         com.tpvision.smartinstall.xml.Setting s12 = new com.tpvision.smartinstall.xml.Setting();
         s12.setItem("DisplayWelcomeMsg");
         s12.setLastValue(" " + hmSettings.getDisplayWelcomeMsg() + " ");
         s12.setXaddr("MS2K14");
         result.add(s12);
         com.tpvision.smartinstall.xml.Setting s13 = new com.tpvision.smartinstall.xml.Setting();
         s13.setItem("WelcomeMsgLine1");
         s13.setLastValue(" " + hmSettings.getWelcomeMsgLine1() + " ");
         s13.setXaddr("MS2K14");
         result.add(s13);
         com.tpvision.smartinstall.xml.Setting s14 = new com.tpvision.smartinstall.xml.Setting();
         s14.setItem("WelcomeMsgLine2");
         s14.setLastValue(" " + hmSettings.getWelcomeMsgLine2() + " ");
         s14.setXaddr("MS2K14");
         result.add(s14);
         com.tpvision.smartinstall.xml.Setting s15 = new com.tpvision.smartinstall.xml.Setting();
         s15.setItem("WelcomeMsgTimeOut");
         s15.setLastValue(" " + hmSettings.getWelcomeMsgTimeOut() + " ");
         s15.setXaddr("MS2K14");
         result.add(s15);
         com.tpvision.smartinstall.xml.Setting s16 = new com.tpvision.smartinstall.xml.Setting();
         s16.setItem("DisplayLogo");
         s16.setLastValue(" " + hmSettings.getDisplayLogo() + " ");
         s16.setXaddr("MS2K14");
         result.add(s16);
         com.tpvision.smartinstall.xml.Setting s17 = new com.tpvision.smartinstall.xml.Setting();
         s17.setItem("SmartInfo");
         s17.setLastValue(" " + hmSettings.getSmartInfo() + " ");
         s17.setXaddr("MS2K14");
         result.add(s17);
         com.tpvision.smartinstall.xml.Setting s18 = new com.tpvision.smartinstall.xml.Setting();
         s18.setItem("SmartInfoIconLabel");
         s18.setLastValue(" " + hmSettings.getSmartInfoIconLabel() + " ");
         s18.setXaddr("MS2K14");
         result.add(s18);
         com.tpvision.smartinstall.xml.Setting s19 = new com.tpvision.smartinstall.xml.Setting();
         s19.setItem("KBLock");
         s19.setLastValue(" " + hmSettings.getKbLock() + " ");
         s19.setXaddr("MS2K14");
         result.add(s19);
         com.tpvision.smartinstall.xml.Setting s20 = new com.tpvision.smartinstall.xml.Setting();
         s20.setItem("RCLock");
         s20.setLastValue(" " + hmSettings.getRcLock() + " ");
         s20.setXaddr("MS2K14");
         result.add(s20);
         com.tpvision.smartinstall.xml.Setting s21 = new com.tpvision.smartinstall.xml.Setting();
         s21.setItem("OSDDisplay");
         s21.setLastValue(" " + hmSettings.getOsdDisplay() + " ");
         s21.setXaddr("MS2K14");
         result.add(s21);
         com.tpvision.smartinstall.xml.Setting s22 = new com.tpvision.smartinstall.xml.Setting();
         s22.setItem("HighSecurity");
         s22.setLastValue(" " + hmSettings.getHighSecurity() + " ");
         s22.setXaddr("MS2K14");
         result.add(s22);
         com.tpvision.smartinstall.xml.Setting s23 = new com.tpvision.smartinstall.xml.Setting();
         s23.setItem("AutoScart");
         s23.setLastValue(" " + hmSettings.getAutoScart() + " ");
         s23.setXaddr("MS2K14");
         result.add(s23);
         com.tpvision.smartinstall.xml.Setting s24 = new com.tpvision.smartinstall.xml.Setting();
         s24.setItem("USBBreakIn");
         s24.setLastValue(" " + hmSettings.getUsbBreakIn() + " ");
         s24.setXaddr("MS2K14");
         result.add(s24);
         com.tpvision.smartinstall.xml.Setting s25 = new com.tpvision.smartinstall.xml.Setting();
         s25.setItem("EnableUSB");
         s25.setLastValue(" " + hmSettings.getEnableUSB() + " ");
         s25.setXaddr("MS2K14");
         result.add(s25);
         com.tpvision.smartinstall.xml.Setting s26 = new com.tpvision.smartinstall.xml.Setting();
         s26.setItem("SXPBaudRate");
         s26.setLastValue(" " + hmSettings.getSxpBaudRate() + " ");
         s26.setXaddr("MS2K14");
         result.add(s26);
         com.tpvision.smartinstall.xml.Setting s27 = new com.tpvision.smartinstall.xml.Setting();
         s27.setItem("EnableTeletext");
         s27.setLastValue(" " + hmSettings.getEnableTeletext() + " ");
         s27.setXaddr("MS2K14");
         result.add(s27);
         com.tpvision.smartinstall.xml.Setting s28 = new com.tpvision.smartinstall.xml.Setting();
         s28.setItem("EnableMHEG");
         s28.setLastValue(" " + hmSettings.getEnableMHEG() + " ");
         s28.setXaddr("MS2K14");
         result.add(s28);
         com.tpvision.smartinstall.xml.Setting s29 = new com.tpvision.smartinstall.xml.Setting();
         s29.setItem("EnableEPG");
         s29.setLastValue(" " + hmSettings.getEnableEPG() + " ");
         s29.setXaddr("MS2K14");
         result.add(s29);
         com.tpvision.smartinstall.xml.Setting s30 = new com.tpvision.smartinstall.xml.Setting();
         s30.setItem("EnableSubtitles");
         s30.setLastValue(" " + hmSettings.getEnableSubtitles() + " ");
         s30.setXaddr("MS2K14");
         result.add(s30);
         com.tpvision.smartinstall.xml.Setting s31 = new com.tpvision.smartinstall.xml.Setting();
         s31.setItem("SubtitleOnStartup");
         s31.setLastValue(" " + hmSettings.getSubtitleOnStartup() + " ");
         s31.setXaddr("MS2K14");
         result.add(s31);
         com.tpvision.smartinstall.xml.Setting s32 = new com.tpvision.smartinstall.xml.Setting();
         s32.setItem("BlueMute");
         s32.setLastValue(" " + hmSettings.getBlueMute() + " ");
         s32.setXaddr("MS2K14");
         result.add(s32);
         com.tpvision.smartinstall.xml.Setting s33 = new com.tpvision.smartinstall.xml.Setting();
         s33.setItem("EnableCISlot");
         s33.setLastValue(" " + hmSettings.getEnableCISlot() + " ");
         s33.setXaddr("MS2K14");
         result.add(s33);
         com.tpvision.smartinstall.xml.Setting s37 = new com.tpvision.smartinstall.xml.Setting();
         s37.setItem("ScrambledProgramOSD");
         s37.setLastValue(" " + hmSettings.getScrambledProgramOSD() + " ");
         s37.setXaddr("MS2K14");
         result.add(s37);
         com.tpvision.smartinstall.xml.Setting s40 = new com.tpvision.smartinstall.xml.Setting();
         s40.setItem("EasylinkBreakIn");
         s40.setLastValue(" " + hmSettings.getEasylinkBreakIn() + " ");
         s40.setXaddr("MS2K14");
         result.add(s40);
         com.tpvision.smartinstall.xml.Setting s41 = new com.tpvision.smartinstall.xml.Setting();
         s41.setItem("EasylinkControl");
         s41.setLastValue(" " + hmSettings.getEasylinkControl() + " ");
         s41.setXaddr("MS2K14");
         result.add(s41);
         com.tpvision.smartinstall.xml.Setting s43 = new com.tpvision.smartinstall.xml.Setting();
         s43.setItem("DigitTimeout");
         s43.setLastValue(" " + hmSettings.getDigitTimeout() + " ");
         s43.setXaddr("MS2K14");
         result.add(s43);
         com.tpvision.smartinstall.xml.Setting s44 = new com.tpvision.smartinstall.xml.Setting();
         s44.setItem("SelectableAV");
         s44.setLastValue(" " + hmSettings.getSelectableAV() + " ");
         s44.setXaddr("MS2K14");
         result.add(s44);
         com.tpvision.smartinstall.xml.Setting s45 = new com.tpvision.smartinstall.xml.Setting();
         s45.setItem("WatchTV");
         s45.setLastValue(" " + hmSettings.getWatchTV() + " ");
         s45.setXaddr("MS2K14");
         result.add(s45);
         com.tpvision.smartinstall.xml.Setting s46 = new com.tpvision.smartinstall.xml.Setting();
         s46.setItem("ExternalClk");
         s46.setLastValue(" " + hmSettings.getExternalClk() + " ");
         s46.setXaddr("MS2K14");
         result.add(s46);
         com.tpvision.smartinstall.xml.Setting s47 = new com.tpvision.smartinstall.xml.Setting();
         s47.setItem("ClkBrighDimlight");
         s47.setLastValue(" " + hmSettings.getClkBrighDimlight() + " ");
         s47.setXaddr("MS2K14");
         result.add(s47);
         com.tpvision.smartinstall.xml.Setting s48 = new com.tpvision.smartinstall.xml.Setting();
         s48.setItem("ClkBrighIntenselight");
         s48.setLastValue(" " + hmSettings.getClkBrighIntenselight() + " ");
         s48.setXaddr("MS2K14");
         result.add(s48);
         com.tpvision.smartinstall.xml.Setting s49 = new com.tpvision.smartinstall.xml.Setting();
         s49.setItem("ClkLightSensor");
         s49.setLastValue(" " + hmSettings.getClkLightSensor() + " ");
         s49.setXaddr("MS2K14");
         result.add(s49);
         com.tpvision.smartinstall.xml.Setting s50 = new com.tpvision.smartinstall.xml.Setting();
         s50.setItem("TimeDownload");
         s50.setLastValue(" " + hmSettings.getTimeDownload() + " ");
         s50.setXaddr("MS2K14");
         result.add(s50);
         com.tpvision.smartinstall.xml.Setting s51 = new com.tpvision.smartinstall.xml.Setting();
         s51.setItem("TimeSetting");
         s51.setLastValue(" " + hmSettings.getTimeSetting() + " ");
         s51.setXaddr("MS2K14");
         result.add(s51);
         com.tpvision.smartinstall.xml.Setting s53 = new com.tpvision.smartinstall.xml.Setting();
         s53.setItem("ClkDownloadProgram");
         s53.setLastValue(" " + hmSettings.getClkDownloadProgram() + " ");
         s53.setXaddr("MS2K14");
         result.add(s53);
         com.tpvision.smartinstall.xml.Setting s54 = new com.tpvision.smartinstall.xml.Setting();
         s54.setItem("ClkDownloadCountry");
         s54.setLastValue(" " + hmSettings.getClkDownloadCountry() + " ");
         s54.setXaddr("MS2K14");
         result.add(s54);
         com.tpvision.smartinstall.xml.Setting s55 = new com.tpvision.smartinstall.xml.Setting();
         s55.setItem("ClkTimeZone");
         s55.setLastValue(" " + hmSettings.getClkTimeZone() + " ");
         s55.setXaddr("MS2K14");
         result.add(s55);
         com.tpvision.smartinstall.xml.Setting s56 = new com.tpvision.smartinstall.xml.Setting();
         s56.setItem("DaylightSaving");
         s56.setLastValue(" " + hmSettings.getDaylightSaving() + " ");
         s56.setXaddr("MS2K14");
         result.add(s56);
         com.tpvision.smartinstall.xml.Setting s57 = new com.tpvision.smartinstall.xml.Setting();
         s57.setItem("ClkTimeOffset");
         s57.setLastValue(" " + hmSettings.getClkTimeOffset() + " ");
         s57.setXaddr("MS2K14");
         result.add(s57);
         com.tpvision.smartinstall.xml.Setting s58 = new com.tpvision.smartinstall.xml.Setting();
         s58.setItem("ReferenceDate");
         s58.setLastValue(" " + hmSettings.getReferenceDate() + " ");
         s58.setXaddr("MS2K14");
         result.add(s58);
         com.tpvision.smartinstall.xml.Setting s59 = new com.tpvision.smartinstall.xml.Setting();
         s59.setItem("ReferenceTime");
         s59.setLastValue(" " + hmSettings.getReferenceTime() + " ");
         s59.setXaddr("MS2K14");
         result.add(s59);
         com.tpvision.smartinstall.xml.Setting s60 = new com.tpvision.smartinstall.xml.Setting();
         s60.setItem("MainSpkrEnable");
         s60.setLastValue(" " + hmSettings.getMainSpkrEnable() + " ");
         s60.setXaddr("MS2K14");
         result.add(s60);
         com.tpvision.smartinstall.xml.Setting s61 = new com.tpvision.smartinstall.xml.Setting();
         s61.setItem("IndMainSpkrMute");
         s61.setLastValue(" " + hmSettings.getIndMainSpkrMute() + " ");
         s61.setXaddr("MS2K14");
         result.add(s61);
         com.tpvision.smartinstall.xml.Setting s62 = new com.tpvision.smartinstall.xml.Setting();
         s62.setItem("DefMainSpkrVol");
         s62.setLastValue(" " + hmSettings.getDefMainSpkrVol() + " ");
         s62.setXaddr("MS2K14");
         result.add(s62);
         com.tpvision.smartinstall.xml.Setting s63 = new com.tpvision.smartinstall.xml.Setting();
         s63.setItem("AutoChnUpdate");
         s63.setLastValue(" " + hmSettings.getAutoChnUpdate() + " ");
         s63.setXaddr("MS2K14");
         result.add(s63);
         com.tpvision.smartinstall.xml.Setting s64 = new com.tpvision.smartinstall.xml.Setting();
         s64.setItem("AutoSwUpdate");
         s64.setLastValue(" " + hmSettings.getAutoSwUpdate() + " ");
         s64.setXaddr("MS2K14");
         result.add(s64);
         com.tpvision.smartinstall.xml.Setting s65 = new com.tpvision.smartinstall.xml.Setting();
         s65.setItem("SkipScrambled");
         s65.setLastValue(" " + hmSettings.getSkipScrambled() + " ");
         s65.setXaddr("MS2K14");
         result.add(s65);
         com.tpvision.smartinstall.xml.Setting s66 = new com.tpvision.smartinstall.xml.Setting();
         s66.setItem("MultiRC");
         s66.setLastValue(" " + hmSettings.getMultiRC() + " ");
         s66.setXaddr("MS2K14");
         result.add(s66);
         com.tpvision.smartinstall.xml.Setting s67 = new com.tpvision.smartinstall.xml.Setting();
         s67.setItem("MyChoice");
         s67.setLastValue(" " + hmSettings.getMyChoice() + " ");
         s67.setXaddr("MS2K14");
         result.add(s67);
         com.tpvision.smartinstall.xml.Setting s68 = new com.tpvision.smartinstall.xml.Setting();
         s68.setItem("AskForPIN");
         s68.setLastValue(" " + hmSettings.getAskForPIN() + " ");
         s68.setXaddr("MS2K14");
         result.add(s68);
         com.tpvision.smartinstall.xml.Setting s69 = new com.tpvision.smartinstall.xml.Setting();
         s69.setItem("SmartPay");
         s69.setLastValue(" " + hmSettings.getSmartPay() + " ");
         s69.setXaddr("MS2K14");
         result.add(s69);
         com.tpvision.smartinstall.xml.Setting s70 = new com.tpvision.smartinstall.xml.Setting();
         s70.setItem("AV");
         s70.setLastValue(" " + hmSettings.getAV() + " ");
         s70.setXaddr("MS2K14");
         result.add(s70);
         com.tpvision.smartinstall.xml.Setting s84 = new com.tpvision.smartinstall.xml.Setting();
         s84.setItem("VsecOverRFEnable");
         s84.setLastValue(" " + hmSettings.getVsecOverRFEnable() + " ");
         s84.setXaddr("MS2K14");
         result.add(s84);
         com.tpvision.smartinstall.xml.Setting s85 = new com.tpvision.smartinstall.xml.Setting();
         s85.setItem("EraseKeyOption");
         s85.setLastValue(" " + hmSettings.getEraseKeyOption() + " ");
         s85.setXaddr("MS2K14");
         result.add(s85);
         com.tpvision.smartinstall.xml.Setting s86 = new com.tpvision.smartinstall.xml.Setting();
         s86.setItem("VsecFrequency");
         s86.setLastValue(" " + hmSettings.getVsecFrequency() + " ");
         s86.setXaddr("MS2K14");
         result.add(s86);
         com.tpvision.smartinstall.xml.Setting s87 = new com.tpvision.smartinstall.xml.Setting();
         s87.setItem("VsecMedium");
         s87.setLastValue(" " + hmSettings.getVsecMedium() + " ");
         s87.setXaddr("MS2K14");
         result.add(s87);
         com.tpvision.smartinstall.xml.Setting s88 = new com.tpvision.smartinstall.xml.Setting();
         s88.setItem("VsecSymbolRate");
         s88.setLastValue(" " + hmSettings.getVsecSymbolRate() + " ");
         s88.setXaddr("MS2K14");
         result.add(s88);
         com.tpvision.smartinstall.xml.Setting s90 = new com.tpvision.smartinstall.xml.Setting();
         s90.setItem("RFCLFrequency");
         s90.setLastValue(" " + hmSettings.getRfclFrequency() + " ");
         s90.setXaddr("MS2K14");
         result.add(s90);
         com.tpvision.smartinstall.xml.Setting s91 = new com.tpvision.smartinstall.xml.Setting();
         s91.setItem("RFCLMedium");
         s91.setLastValue(" " + hmSettings.getRfclMedium() + " ");
         s91.setXaddr("MS2K14");
         result.add(s91);
         com.tpvision.smartinstall.xml.Setting s92 = new com.tpvision.smartinstall.xml.Setting();
         s92.setItem("RFCLSymbolRate");
         s92.setLastValue(" " + hmSettings.getRfclSymbolRate() + " ");
         s92.setXaddr("MS2K14");
         result.add(s92);
         com.tpvision.smartinstall.xml.Setting s93 = new com.tpvision.smartinstall.xml.Setting();
         s93.setItem("UpgradeMode");
         s93.setLastValue(" " + hmSettings.getUpgradeMode() + " ");
         s93.setXaddr("MS2K14");
         result.add(s93);
         com.tpvision.smartinstall.xml.Setting s94 = new com.tpvision.smartinstall.xml.Setting();
         s94.setItem("AutoUpgrade");
         s94.setLastValue(" " + hmSettings.getAutoUpgrade() + " ");
         s94.setXaddr("MS2K14");
         result.add(s94);
         com.tpvision.smartinstall.xml.Setting s95 = new com.tpvision.smartinstall.xml.Setting();
         s95.setItem("InstallationMode");
         s95.setLastValue(" " + hmSettings.getInstallationMode() + " ");
         s95.setXaddr("MS2K14");
         result.add(s95);
         com.tpvision.smartinstall.xml.Setting s96 = new com.tpvision.smartinstall.xml.Setting();
         s96.setItem("CloneMultiRC");
         s96.setLastValue(" " + hmSettings.getCloneMultiRC() + " ");
         s96.setXaddr("MS2K14");
         result.add(s96);
      }

      Map<String, String> cloneDefaultValues = new HashMap<>();
      cloneDefaultValues.put("CloneMultiRC", "Yes");

      for (com.tpvision.smartinstall.xml.Setting v : result) {
         for (Entry<String, String> entry : cloneDefaultValues.entrySet()) {
            if (v.getItem().equalsIgnoreCase(entry.getKey()) && v.getLastValue().trim().equalsIgnoreCase("null")) {
               v.setLastValue(" " + entry.getValue() + " ");
            }
         }
      }

      return result;
   }

   private static List<com.tpvision.smartinstall.xml.Setting> getCanonicalSettingFromMS2K14(HotelModeSettings hmSettings) {
      List<com.tpvision.smartinstall.xml.Setting> result = new ArrayList<>();
      if (null != hmSettings) {
         com.tpvision.smartinstall.xml.Setting s0 = new com.tpvision.smartinstall.xml.Setting();
         s0.setItem("Dashboard");
         s0.setLastValue(" " + hmSettings.getDashboard() + " ");
         s0.setXaddr("MS2K14");
         result.add(s0);
         com.tpvision.smartinstall.xml.Setting s1 = new com.tpvision.smartinstall.xml.Setting();
         s1.setItem("SwitchOnSrc");
         s1.setLastValue(" " + hmSettings.getSwitchOnSrc() + " ");
         s1.setXaddr("MS2K14");
         result.add(s1);
         com.tpvision.smartinstall.xml.Setting s2 = new com.tpvision.smartinstall.xml.Setting();
         s2.setItem("SwitchOnChn");
         s2.setLastValue(" " + hmSettings.getSwitchOnChn() + " ");
         s2.setXaddr("MS2K14");
         result.add(s2);
         com.tpvision.smartinstall.xml.Setting s3 = new com.tpvision.smartinstall.xml.Setting();
         s3.setItem("SwitchOnVol");
         s3.setLastValue(" " + hmSettings.getSwitchOnVol() + " ");
         s3.setXaddr("MS2K14");
         result.add(s3);
         com.tpvision.smartinstall.xml.Setting s4 = new com.tpvision.smartinstall.xml.Setting();
         s4.setItem("MaximumVol");
         s4.setLastValue(" " + hmSettings.getMaximumVol() + " ");
         s4.setXaddr("MS2K14");
         result.add(s4);
         com.tpvision.smartinstall.xml.Setting s5 = new com.tpvision.smartinstall.xml.Setting();
         s5.setItem("SwitchOnFeature");
         s5.setLastValue(" " + hmSettings.getSwitchOnFeature() + " ");
         s5.setXaddr("MS2K14");
         result.add(s5);
         com.tpvision.smartinstall.xml.Setting s6 = new com.tpvision.smartinstall.xml.Setting();
         s6.setItem("SwitchOnPicFmt");
         s6.setLastValue(" " + hmSettings.getSwitchOnPicFmt() + " ");
         s6.setXaddr("MS2K14");
         result.add(s6);
         com.tpvision.smartinstall.xml.Setting s7 = new com.tpvision.smartinstall.xml.Setting();
         s7.setItem("PowerOn");
         s7.setLastValue(" " + hmSettings.getPowerOn() + " ");
         s7.setXaddr("MS2K14");
         result.add(s7);
         com.tpvision.smartinstall.xml.Setting s8 = new com.tpvision.smartinstall.xml.Setting();
         s8.setItem("LowPowerStandby");
         s8.setLastValue(" " + hmSettings.getLowPowerStandby() + " ");
         s8.setXaddr("MS2K14");
         result.add(s8);
         com.tpvision.smartinstall.xml.Setting s9 = new com.tpvision.smartinstall.xml.Setting();
         s9.setItem("SmartPower");
         s9.setLastValue(" " + hmSettings.getSmartPower() + " ");
         s9.setXaddr("MS2K14");
         result.add(s9);
         com.tpvision.smartinstall.xml.Setting s10 = new com.tpvision.smartinstall.xml.Setting();
         s10.setItem("RebootEveryDay");
         s10.setLastValue(" " + hmSettings.getRebootEveryDay() + " ");
         s10.setXaddr("MS2K14");
         result.add(s10);
         com.tpvision.smartinstall.xml.Setting s11 = new com.tpvision.smartinstall.xml.Setting();
         s11.setItem("WakeOnLAN");
         s11.setLastValue(" " + hmSettings.getWakeOnLAN() + " ");
         s11.setXaddr("MS2K14");
         result.add(s11);
         com.tpvision.smartinstall.xml.Setting s12 = new com.tpvision.smartinstall.xml.Setting();
         s12.setItem("DisplayWelcomeMsg");
         s12.setLastValue(" " + hmSettings.getDisplayWelcomeMsg() + " ");
         s12.setXaddr("MS2K14");
         result.add(s12);
         com.tpvision.smartinstall.xml.Setting s13 = new com.tpvision.smartinstall.xml.Setting();
         s13.setItem("WelcomeMsgLine1");
         s13.setLastValue(" " + hmSettings.getWelcomeMsgLine1() + " ");
         s13.setXaddr("MS2K14");
         result.add(s13);
         com.tpvision.smartinstall.xml.Setting s14 = new com.tpvision.smartinstall.xml.Setting();
         s14.setItem("WelcomeMsgLine2");
         s14.setLastValue(" " + hmSettings.getWelcomeMsgLine2() + " ");
         s14.setXaddr("MS2K14");
         result.add(s14);
         com.tpvision.smartinstall.xml.Setting s15 = new com.tpvision.smartinstall.xml.Setting();
         s15.setItem("WelcomeMsgTimeOut");
         s15.setLastValue(" " + hmSettings.getWelcomeMsgTimeOut() + " ");
         s15.setXaddr("MS2K14");
         result.add(s15);
         com.tpvision.smartinstall.xml.Setting s16 = new com.tpvision.smartinstall.xml.Setting();
         s16.setItem("DisplayLogo");
         s16.setLastValue(" " + hmSettings.getDisplayLogo() + " ");
         s16.setXaddr("MS2K14");
         result.add(s16);
         com.tpvision.smartinstall.xml.Setting s17 = new com.tpvision.smartinstall.xml.Setting();
         s17.setItem("SmartInfo");
         s17.setLastValue(" " + hmSettings.getSmartInfo() + " ");
         s17.setXaddr("MS2K14");
         result.add(s17);
         com.tpvision.smartinstall.xml.Setting s18 = new com.tpvision.smartinstall.xml.Setting();
         s18.setItem("SmartInfoIconLabel");
         s18.setLastValue(" " + hmSettings.getSmartInfoIconLabel() + " ");
         s18.setXaddr("MS2K14");
         result.add(s18);
         com.tpvision.smartinstall.xml.Setting s19 = new com.tpvision.smartinstall.xml.Setting();
         s19.setItem("KBLock");
         s19.setLastValue(" " + hmSettings.getKBLock() + " ");
         s19.setXaddr("MS2K14");
         result.add(s19);
         com.tpvision.smartinstall.xml.Setting s20 = new com.tpvision.smartinstall.xml.Setting();
         s20.setItem("RCLock");
         s20.setLastValue(" " + hmSettings.getRCLock() + " ");
         s20.setXaddr("MS2K14");
         result.add(s20);
         com.tpvision.smartinstall.xml.Setting s21 = new com.tpvision.smartinstall.xml.Setting();
         s21.setItem("OSDDisplay");
         s21.setLastValue(" " + hmSettings.getOSDDisplay() + " ");
         s21.setXaddr("MS2K14");
         result.add(s21);
         com.tpvision.smartinstall.xml.Setting s22 = new com.tpvision.smartinstall.xml.Setting();
         s22.setItem("HighSecurity");
         s22.setLastValue(" " + hmSettings.getHighSecurity() + " ");
         s22.setXaddr("MS2K14");
         result.add(s22);
         com.tpvision.smartinstall.xml.Setting s23 = new com.tpvision.smartinstall.xml.Setting();
         s23.setItem("AutoScart");
         s23.setLastValue(" " + hmSettings.getAutoScart() + " ");
         s23.setXaddr("MS2K14");
         result.add(s23);
         com.tpvision.smartinstall.xml.Setting s24 = new com.tpvision.smartinstall.xml.Setting();
         s24.setItem("USBBreakIn");
         s24.setLastValue(" " + hmSettings.getUSBBreakIn() + " ");
         s24.setXaddr("MS2K14");
         result.add(s24);
         com.tpvision.smartinstall.xml.Setting s25 = new com.tpvision.smartinstall.xml.Setting();
         s25.setItem("EnableUSB");
         s25.setLastValue(" " + hmSettings.getEnableUSB() + " ");
         s25.setXaddr("MS2K14");
         result.add(s25);
         com.tpvision.smartinstall.xml.Setting s26 = new com.tpvision.smartinstall.xml.Setting();
         s26.setItem("SXPBaudRate");
         s26.setLastValue(" " + hmSettings.getSXPBaudRate() + " ");
         s26.setXaddr("MS2K14");
         result.add(s26);
         com.tpvision.smartinstall.xml.Setting s27 = new com.tpvision.smartinstall.xml.Setting();
         s27.setItem("EnableTeletext");
         s27.setLastValue(" " + hmSettings.getEnableTeletext() + " ");
         s27.setXaddr("MS2K14");
         result.add(s27);
         com.tpvision.smartinstall.xml.Setting s28 = new com.tpvision.smartinstall.xml.Setting();
         s28.setItem("EnableMHEG");
         s28.setLastValue(" " + hmSettings.getEnableMHEG() + " ");
         s28.setXaddr("MS2K14");
         result.add(s28);
         com.tpvision.smartinstall.xml.Setting s29 = new com.tpvision.smartinstall.xml.Setting();
         s29.setItem("EnableEPG");
         s29.setLastValue(" " + hmSettings.getEnableEPG() + " ");
         s29.setXaddr("MS2K14");
         result.add(s29);
         com.tpvision.smartinstall.xml.Setting s30 = new com.tpvision.smartinstall.xml.Setting();
         s30.setItem("EnableSubtitles");
         s30.setLastValue(" " + hmSettings.getEnableSubtitles() + " ");
         s30.setXaddr("MS2K14");
         result.add(s30);
         com.tpvision.smartinstall.xml.Setting s31 = new com.tpvision.smartinstall.xml.Setting();
         s31.setItem("SubtitleOnStartup");
         s31.setLastValue(" " + hmSettings.getSubtitleOnStartup() + " ");
         s31.setXaddr("MS2K14");
         result.add(s31);
         com.tpvision.smartinstall.xml.Setting s32 = new com.tpvision.smartinstall.xml.Setting();
         s32.setItem("BlueMute");
         s32.setLastValue(" " + hmSettings.getBlueMute() + " ");
         s32.setXaddr("MS2K14");
         result.add(s32);
         com.tpvision.smartinstall.xml.Setting s33 = new com.tpvision.smartinstall.xml.Setting();
         s33.setItem("EnableCISlot");
         s33.setLastValue(" " + hmSettings.getEnableCISlot() + " ");
         s33.setXaddr("MS2K14");
         result.add(s33);
         com.tpvision.smartinstall.xml.Setting s34 = new com.tpvision.smartinstall.xml.Setting();
         s34.setItem("WiFiCrossConnect");
         s34.setLastValue(" " + hmSettings.getWiFiCrossConnect() + " ");
         s34.setXaddr("MS2K14");
         result.add(s34);
         com.tpvision.smartinstall.xml.Setting s35 = new com.tpvision.smartinstall.xml.Setting();
         s35.setItem("WiFiMiraCast");
         s35.setLastValue(" " + hmSettings.getWiFiMiraCast() + " ");
         s35.setXaddr("MS2K14");
         result.add(s35);
         com.tpvision.smartinstall.xml.Setting s36 = new com.tpvision.smartinstall.xml.Setting();
         s36.setItem("DirectShare");
         s36.setLastValue(" " + hmSettings.getDirectShare() + " ");
         s36.setXaddr("MS2K14");
         result.add(s36);
         com.tpvision.smartinstall.xml.Setting s37 = new com.tpvision.smartinstall.xml.Setting();
         s37.setItem("ScrambledProgramOSD");
         s37.setLastValue(" " + hmSettings.getScrambledProgramOSD() + " ");
         s37.setXaddr("MS2K14");
         result.add(s37);
         com.tpvision.smartinstall.xml.Setting s38 = new com.tpvision.smartinstall.xml.Setting();
         s38.setItem("WiFiLostOSD");
         s38.setLastValue(" " + hmSettings.getWiFiLostOSD() + " ");
         s38.setXaddr("MS2K14");
         result.add(s38);
         com.tpvision.smartinstall.xml.Setting s39 = new com.tpvision.smartinstall.xml.Setting();
         s39.setItem("JointSpace");
         s39.setLastValue(" " + hmSettings.getJointSpace() + " ");
         s39.setXaddr("MS2K14");
         result.add(s39);
         com.tpvision.smartinstall.xml.Setting s40 = new com.tpvision.smartinstall.xml.Setting();
         s40.setItem("EasylinkBreakIn");
         s40.setLastValue(" " + hmSettings.getEasylinkBreakIn() + " ");
         s40.setXaddr("MS2K14");
         result.add(s40);
         com.tpvision.smartinstall.xml.Setting s41 = new com.tpvision.smartinstall.xml.Setting();
         s41.setItem("EasylinkControl");
         s41.setLastValue(" " + hmSettings.getEasylinkControl() + " ");
         s41.setXaddr("MS2K14");
         result.add(s41);
         com.tpvision.smartinstall.xml.Setting s42 = new com.tpvision.smartinstall.xml.Setting();
         s42.setItem("EnableSkype");
         s42.setLastValue(" " + hmSettings.getEnableSkype() + " ");
         s42.setXaddr("MS2K14");
         result.add(s42);
         com.tpvision.smartinstall.xml.Setting s43 = new com.tpvision.smartinstall.xml.Setting();
         s43.setItem("DigitTimeout");
         s43.setLastValue(" " + hmSettings.getDigitTimeout() + " ");
         s43.setXaddr("MS2K14");
         result.add(s43);
         com.tpvision.smartinstall.xml.Setting s44 = new com.tpvision.smartinstall.xml.Setting();
         s44.setItem("SelectableAV");
         s44.setLastValue(" " + hmSettings.getSelectableAV() + " ");
         s44.setXaddr("MS2K14");
         result.add(s44);
         com.tpvision.smartinstall.xml.Setting s45 = new com.tpvision.smartinstall.xml.Setting();
         s45.setItem("WatchTV");
         s45.setLastValue(" " + hmSettings.getWatchTV() + " ");
         s45.setXaddr("MS2K14");
         result.add(s45);
         com.tpvision.smartinstall.xml.Setting s46 = new com.tpvision.smartinstall.xml.Setting();
         s46.setItem("ExternalClk");
         s46.setLastValue(" " + hmSettings.getExternalClk() + " ");
         s46.setXaddr("MS2K14");
         result.add(s46);
         com.tpvision.smartinstall.xml.Setting s47 = new com.tpvision.smartinstall.xml.Setting();
         s47.setItem("ClkBrighDimlight");
         s47.setLastValue(" " + hmSettings.getClkBrighDimlight() + " ");
         s47.setXaddr("MS2K14");
         result.add(s47);
         com.tpvision.smartinstall.xml.Setting s48 = new com.tpvision.smartinstall.xml.Setting();
         s48.setItem("ClkBrighIntenselight");
         s48.setLastValue(" " + hmSettings.getClkBrighIntenselight() + " ");
         s48.setXaddr("MS2K14");
         result.add(s48);
         com.tpvision.smartinstall.xml.Setting s49 = new com.tpvision.smartinstall.xml.Setting();
         s49.setItem("ClkLightSensor");
         s49.setLastValue(" " + hmSettings.getClkLightSensor() + " ");
         s49.setXaddr("MS2K14");
         result.add(s49);
         com.tpvision.smartinstall.xml.Setting s50 = new com.tpvision.smartinstall.xml.Setting();
         s50.setItem("TimeDownload");
         s50.setLastValue(" " + hmSettings.getTimeDownload() + " ");
         s50.setXaddr("MS2K14");
         result.add(s50);
         com.tpvision.smartinstall.xml.Setting s51 = new com.tpvision.smartinstall.xml.Setting();
         s51.setItem("TimeSetting");
         s51.setLastValue(" " + hmSettings.getTimeSetting() + " ");
         s51.setXaddr("MS2K14");
         result.add(s51);
         com.tpvision.smartinstall.xml.Setting s52 = new com.tpvision.smartinstall.xml.Setting();
         s52.setItem("ClkNTPSvrURL");
         s52.setLastValue(" " + hmSettings.getClkNTPSvrURL() + " ");
         s52.setXaddr("MS2K14");
         result.add(s52);
         com.tpvision.smartinstall.xml.Setting s53 = new com.tpvision.smartinstall.xml.Setting();
         s53.setItem("ClkDownloadProgram");
         s53.setLastValue(" " + hmSettings.getClkDownloadProgram() + " ");
         s53.setXaddr("MS2K14");
         result.add(s53);
         com.tpvision.smartinstall.xml.Setting s54 = new com.tpvision.smartinstall.xml.Setting();
         s54.setItem("ClkDownloadCountry");
         s54.setLastValue(" " + hmSettings.getClkDownloadCountry() + " ");
         s54.setXaddr("MS2K14");
         result.add(s54);
         com.tpvision.smartinstall.xml.Setting s55 = new com.tpvision.smartinstall.xml.Setting();
         s55.setItem("ClkTimeZone");
         s55.setLastValue(" " + hmSettings.getClkTimeZone() + " ");
         s55.setXaddr("MS2K14");
         result.add(s55);
         com.tpvision.smartinstall.xml.Setting s56 = new com.tpvision.smartinstall.xml.Setting();
         s56.setItem("DaylightSaving");
         s56.setLastValue(" " + hmSettings.getDaylightSaving() + " ");
         s56.setXaddr("MS2K14");
         result.add(s56);
         com.tpvision.smartinstall.xml.Setting s57 = new com.tpvision.smartinstall.xml.Setting();
         s57.setItem("ClkTimeOffset");
         s57.setLastValue(" " + hmSettings.getClkTimeOffset() + " ");
         s57.setXaddr("MS2K14");
         result.add(s57);
         com.tpvision.smartinstall.xml.Setting s58 = new com.tpvision.smartinstall.xml.Setting();
         s58.setItem("ReferenceDate");
         s58.setLastValue(" " + hmSettings.getReferenceDate() + " ");
         s58.setXaddr("MS2K14");
         result.add(s58);
         com.tpvision.smartinstall.xml.Setting s59 = new com.tpvision.smartinstall.xml.Setting();
         s59.setItem("ReferenceTime");
         s59.setLastValue(" " + hmSettings.getReferenceTime() + " ");
         s59.setXaddr("MS2K14");
         result.add(s59);
         com.tpvision.smartinstall.xml.Setting s60 = new com.tpvision.smartinstall.xml.Setting();
         s60.setItem("MainSpkrEnable");
         s60.setLastValue(" " + hmSettings.getMainSpkrEnable() + " ");
         s60.setXaddr("MS2K14");
         result.add(s60);
         com.tpvision.smartinstall.xml.Setting s61 = new com.tpvision.smartinstall.xml.Setting();
         s61.setItem("IndMainSpkrMute");
         s61.setLastValue(" " + hmSettings.getIndMainSpkrMute() + " ");
         s61.setXaddr("MS2K14");
         result.add(s61);
         com.tpvision.smartinstall.xml.Setting s62 = new com.tpvision.smartinstall.xml.Setting();
         s62.setItem("DefMainSpkrVol");
         s62.setLastValue(" " + hmSettings.getDefMainSpkrVol() + " ");
         s62.setXaddr("MS2K14");
         result.add(s62);
         com.tpvision.smartinstall.xml.Setting s63 = new com.tpvision.smartinstall.xml.Setting();
         s63.setItem("AutoChnUpdate");
         s63.setLastValue(" " + hmSettings.getAutoChnUpdate() + " ");
         s63.setXaddr("MS2K14");
         result.add(s63);
         com.tpvision.smartinstall.xml.Setting s64 = new com.tpvision.smartinstall.xml.Setting();
         s64.setItem("AutoSwUpdate");
         s64.setLastValue(" " + hmSettings.getAutoSwUpdate() + " ");
         s64.setXaddr("MS2K14");
         result.add(s64);
         com.tpvision.smartinstall.xml.Setting s65 = new com.tpvision.smartinstall.xml.Setting();
         s65.setItem("SkipScrambled");
         s65.setLastValue(" " + hmSettings.getSkipScrambled() + " ");
         s65.setXaddr("MS2K14");
         result.add(s65);
         com.tpvision.smartinstall.xml.Setting s66 = new com.tpvision.smartinstall.xml.Setting();
         s66.setItem("MultiRC");
         s66.setLastValue(" " + hmSettings.getMultiRC() + " ");
         s66.setXaddr("MS2K14");
         result.add(s66);
         com.tpvision.smartinstall.xml.Setting s67 = new com.tpvision.smartinstall.xml.Setting();
         s67.setItem("MyChoice");
         s67.setLastValue(" " + hmSettings.getMyChoice() + " ");
         s67.setXaddr("MS2K14");
         result.add(s67);
         com.tpvision.smartinstall.xml.Setting s68 = new com.tpvision.smartinstall.xml.Setting();
         s68.setItem("AskForPIN");
         s68.setLastValue(" " + hmSettings.getAskForPIN() + " ");
         s68.setXaddr("MS2K14");
         result.add(s68);
         com.tpvision.smartinstall.xml.Setting s69 = new com.tpvision.smartinstall.xml.Setting();
         s69.setItem("SmartPay");
         s69.setLastValue(" " + hmSettings.getSmartPay() + " ");
         s69.setXaddr("MS2K14");
         result.add(s69);
         com.tpvision.smartinstall.xml.Setting s70 = new com.tpvision.smartinstall.xml.Setting();
         s70.setItem("AV");
         s70.setLastValue(" " + hmSettings.getAV() + " ");
         s70.setXaddr("MS2K14");
         result.add(s70);
         com.tpvision.smartinstall.xml.Setting s71 = new com.tpvision.smartinstall.xml.Setting();
         s71.setItem("SmartTV");
         s71.setLastValue(" " + hmSettings.getSmartTV() + " ");
         s71.setXaddr("MS2K14");
         result.add(s71);
         com.tpvision.smartinstall.xml.Setting s72 = new com.tpvision.smartinstall.xml.Setting();
         s72.setItem("AppControlID");
         s72.setLastValue(" " + hmSettings.getAppControlID() + " ");
         s72.setXaddr("MS2K14");
         result.add(s72);
         com.tpvision.smartinstall.xml.Setting s73 = new com.tpvision.smartinstall.xml.Setting();
         s73.setItem("ProfileName");
         s73.setLastValue(" " + hmSettings.getProfileName() + " ");
         s73.setXaddr("MS2K14");
         result.add(s73);
         com.tpvision.smartinstall.xml.Setting s74 = new com.tpvision.smartinstall.xml.Setting();
         s74.setItem("Source");
         s74.setLastValue(" " + hmSettings.getSource() + " ");
         s74.setXaddr("MS2K14");
         result.add(s74);
         com.tpvision.smartinstall.xml.Setting s75 = new com.tpvision.smartinstall.xml.Setting();
         s75.setItem("Fallback");
         s75.setLastValue(" " + hmSettings.getFallback() + " ");
         s75.setXaddr("MS2K14");
         result.add(s75);
         com.tpvision.smartinstall.xml.Setting s76 = new com.tpvision.smartinstall.xml.Setting();
         s76.setItem("DashboardIconLabel");
         s76.setLastValue(" " + hmSettings.getDashboardIconLabel() + " ");
         s76.setXaddr("MS2K14");
         result.add(s76);
         com.tpvision.smartinstall.xml.Setting s77 = new com.tpvision.smartinstall.xml.Setting();
         s77.setItem("ServerUIURL");
         s77.setLastValue(" " + hmSettings.getServerUIURL() + " ");
         s77.setXaddr("MS2K14");
         result.add(s77);
         com.tpvision.smartinstall.xml.Setting s78 = new com.tpvision.smartinstall.xml.Setting();
         s78.setItem("WebServicesURL");
         s78.setLastValue(" " + hmSettings.getWebServicesURL() + " ");
         s78.setXaddr("MS2K14");
         result.add(s78);
         com.tpvision.smartinstall.xml.Setting s79 = new com.tpvision.smartinstall.xml.Setting();
         s79.setItem("TVDiscoveryService");
         s79.setLastValue(" " + hmSettings.getTVDiscoveryService() + " ");
         s79.setXaddr("MS2K14");
         result.add(s79);
         com.tpvision.smartinstall.xml.Setting s80 = new com.tpvision.smartinstall.xml.Setting();
         s80.setItem("ProfessionalSettingsService");
         s80.setLastValue(" " + hmSettings.getProfessionalSettingsService() + " ");
         s80.setXaddr("MS2K14");
         result.add(s80);
         com.tpvision.smartinstall.xml.Setting s81 = new com.tpvision.smartinstall.xml.Setting();
         s81.setItem("IPUpgradeService");
         s81.setLastValue(" " + hmSettings.getIPUpgradeService() + " ");
         s81.setXaddr("MS2K14");
         result.add(s81);
         com.tpvision.smartinstall.xml.Setting s82 = new com.tpvision.smartinstall.xml.Setting();
         s82.setItem("PowerService");
         s82.setLastValue(" " + hmSettings.getPowerService() + " ");
         s82.setXaddr("MS2K14");
         result.add(s82);
         com.tpvision.smartinstall.xml.Setting s84 = new com.tpvision.smartinstall.xml.Setting();
         s84.setItem("VsecOverRFEnable");
         s84.setLastValue(" " + hmSettings.getVsecOverRFEnable() + " ");
         s84.setXaddr("MS2K14");
         result.add(s84);
         com.tpvision.smartinstall.xml.Setting s85 = new com.tpvision.smartinstall.xml.Setting();
         s85.setItem("EraseKeyOption");
         s85.setLastValue(" " + hmSettings.getEraseKeyOption() + " ");
         s85.setXaddr("MS2K14");
         result.add(s85);
         com.tpvision.smartinstall.xml.Setting s86 = new com.tpvision.smartinstall.xml.Setting();
         s86.setItem("VsecFrequency");
         s86.setLastValue(" " + hmSettings.getVsecFrequency() + " ");
         s86.setXaddr("MS2K14");
         result.add(s86);
         com.tpvision.smartinstall.xml.Setting s87 = new com.tpvision.smartinstall.xml.Setting();
         s87.setItem("VsecMedium");
         s87.setLastValue(" " + hmSettings.getVsecMedium() + " ");
         s87.setXaddr("MS2K14");
         result.add(s87);
         com.tpvision.smartinstall.xml.Setting s88 = new com.tpvision.smartinstall.xml.Setting();
         s88.setItem("VsecSymbolRate");
         s88.setLastValue(" " + hmSettings.getVsecSymbolRate() + " ");
         s88.setXaddr("MS2K14");
         result.add(s88);
         com.tpvision.smartinstall.xml.Setting s90 = new com.tpvision.smartinstall.xml.Setting();
         s90.setItem("RFCLFrequency");
         s90.setLastValue(" " + hmSettings.getRFCLFrequency() + " ");
         s90.setXaddr("MS2K14");
         result.add(s90);
         com.tpvision.smartinstall.xml.Setting s91 = new com.tpvision.smartinstall.xml.Setting();
         s91.setItem("RFCLMedium");
         s91.setLastValue(" " + hmSettings.getRFCLMedium() + " ");
         s91.setXaddr("MS2K14");
         result.add(s91);
         com.tpvision.smartinstall.xml.Setting s92 = new com.tpvision.smartinstall.xml.Setting();
         s92.setItem("RFCLSymbolRate");
         s92.setLastValue(" " + hmSettings.getRFCLSymbolRate() + " ");
         s92.setXaddr("MS2K14");
         result.add(s92);
         com.tpvision.smartinstall.xml.Setting s93 = new com.tpvision.smartinstall.xml.Setting();
         s93.setItem("UpgradeMode");
         s93.setLastValue(" " + hmSettings.getUpgradeMode() + " ");
         s93.setXaddr("MS2K14");
         result.add(s93);
         com.tpvision.smartinstall.xml.Setting s94 = new com.tpvision.smartinstall.xml.Setting();
         s94.setItem("AutoUpgrade");
         s94.setLastValue(" " + hmSettings.getAutoUpgrade() + " ");
         s94.setXaddr("MS2K14");
         result.add(s94);
         com.tpvision.smartinstall.xml.Setting s95 = new com.tpvision.smartinstall.xml.Setting();
         s95.setItem("InstallationMode");
         s95.setLastValue(" " + hmSettings.getInstallationMode() + " ");
         s95.setXaddr("MS2K14");
         result.add(s95);
         com.tpvision.smartinstall.xml.Setting s96 = new com.tpvision.smartinstall.xml.Setting();
         s96.setItem("CloneMultiRC");
         s96.setLastValue(" " + hmSettings.getCloneMultiRC() + " ");
         s96.setXaddr("MS2K14");
         result.add(s96);
      }

      Map<String, String> cloneDefaultValues = new HashMap<>();
      cloneDefaultValues.put("CloneMultiRC", "Yes");

      for (com.tpvision.smartinstall.xml.Setting v : result) {
         for (Entry<String, String> entry : cloneDefaultValues.entrySet()) {
            if (v.getItem().equalsIgnoreCase(entry.getKey()) && v.getLastValue().trim().equalsIgnoreCase("null")) {
               v.setLastValue(" " + entry.getValue() + " ");
            }
         }
      }

      return result;
   }

   private static List<com.tpvision.smartinstall.xml.Setting> getCanonicalSettingFrom2K16(File file, String platformId) {
      TVSettings tvSettings = null;

      try {
         String path = file.toPath() + "/TVSettings.xml";
         tvSettings = JaxbReadXml.readString(TVSettings.class, path);
      } catch (JAXBException e) {
         LOG.error(e.getMessage(), e);
      }

      String refdata = "MS2K16";
      if ("TPN161HE_CloneData".equalsIgnoreCase(platformId)) {
         refdata = "ES2K16";
      }

      List<com.tpvision.smartinstall.xml.Setting> result = new ArrayList<>();
      if (null != tvSettings) {
         String majorVerNo = tvSettings.getSchemaVersion().getMajorVerNo();
         String minorVerNo = tvSettings.getSchemaVersion().getMinorVerNo();
         com.tpvision.smartinstall.xml.Setting s91 = new com.tpvision.smartinstall.xml.Setting();
         s91.setItem("majorVerNo");
         s91.setLastValue(majorVerNo);
         s91.setRefFile(refdata);
         s91.setXaddr(refdata);
         com.tpvision.smartinstall.xml.Setting s92 = new com.tpvision.smartinstall.xml.Setting();
         s92.setItem("minorVerNo");
         s92.setLastValue(minorVerNo);
         s92.setRefFile(refdata);
         s92.setXaddr(refdata);
         List<Item> item = tvSettings.getItem();
         result.add(s91);
         result.add(s92);

         for (Item o : item) {
            com.tpvision.smartinstall.xml.Setting s96 = new com.tpvision.smartinstall.xml.Setting();
            s96.setItem(o.getName());
            s96.setLastValue("" + o.getValue() + "");
            s96.setCloneIn(o.getCloneIn());
            s96.setRefFile(refdata);
            s96.setXaddr(refdata);
            result.add(s96);
         }
      }

      return result;
   }

   private static Platform getPlatform_Q55(File file) throws MalformedCloneDataException {
      Platform platform = new Platform();
      List<File> settingFiles = findSettingsFiles(file);
      List<File> crcFiles = findCRCFiles(file);
      List<File> childVariants = getListOfChildVariants(file);
      List<File> binFiles = TpvFileUtils.findFiles(file, null);

      for (com.tpvision.smartinstall.xml.File fs : getMustFiles(file.getName())) {
         if (!searchFile(file, fs.getName())) {
            throw new MalformedCloneDataException("Missing Setting file");
         }
      }

      for (Channel fs : getMustHaveChannelFiles(file.getName())) {
         if (!searchFile(file, fs.getFileName())) {
            throw new MalformedCloneDataException("Missing Channel file");
         }
      }

      String swPlatform = getPlatform(settingFiles);
      if (swPlatform == null) {
         return null;
      }

      platform.setSwver(swPlatform);
      platform.setCloneRootFolderName(swPlatform);
      platform.setId(file.getName());
      if (null != childVariants && childVariants.size() > 0) {
      }

      if (null != binFiles && binFiles.size() > 0) {
         UnchangedFiles uf = new UnchangedFiles();

         for (File f : binFiles) {
            String name = f.getAbsolutePath();
            int index = name.indexOf(platform.getCloneRootFolderName()) + platform.getCloneRootFolderName().length() + 1;
            String path = name.substring(index);
            name = f.getName();
            com.tpvision.smartinstall.xml.File fileObj = new com.tpvision.smartinstall.xml.File();
            fileObj.setName(name);
            fileObj.setPath(path);
            uf.getFile().add(fileObj);
         }

         platform.setUnchangedFiles(uf);
      }

      if (null != settingFiles && settingFiles.size() > 0) {
         SettingFiles settingFilesForPlatform = new SettingFiles();

         for (File f : settingFiles) {
            String absolutePath = f.getAbsolutePath();
            String name = f.getName();
            int index = absolutePath.indexOf(platform.getCloneRootFolderName()) + platform.getCloneRootFolderName().length() + 1;
            String path = absolutePath.substring(index);
            com.tpvision.smartinstall.xml.File fileObj = new com.tpvision.smartinstall.xml.File();
            fileObj.setName(name);
            fileObj.setPath(path);
            if (f.getName().endsWith("xml")) {
               fileObj.setType("XML");
            } else {
               fileObj.setType("PXML");
            }

            settingFilesForPlatform.getFile().add(fileObj);
         }

         platform.setSettingFiles(settingFilesForPlatform);
      }

      CrcFiles crcFilesList = getCrcFileDetails(crcFiles, platform);
      platform.setCrcFiles(crcFilesList);
      Settings settings = getSettingsInfo(settingFiles, platform);
      platform.setSettings(settings);
      return platform;
   }

   private static boolean searchFile(File file, String name) {
      boolean ret = false;
      File[] files = file.listFiles(new TpvFileUtils.FilenameFilterFinder(name));
      if (null != files && files.length > 0) {
         ret = true;
      }

      return ret;
   }

   private static List<Channel> getMustHaveChannelFiles(String platformName) {
      Config conf = SmartInstallConfiguration.instance().getConfig();
      List<Channel> ret = new ArrayList<>();

      for (Platform p : conf.getPlatform()) {
         if (p.getChannel().isIsMust() && platformName.equalsIgnoreCase(p.getCloneRootFolderName())) {
            ret.add(p.getChannel());
         }
      }

      return ret;
   }

   private static List<com.tpvision.smartinstall.xml.File> getMustFiles(String platformName) {
      Config conf = SmartInstallConfiguration.instance().getConfig();
      List<com.tpvision.smartinstall.xml.File> ret = new ArrayList<>();
      if (null != conf) {
         for (Platform p : conf.getPlatform()) {
            for (com.tpvision.smartinstall.xml.File f : p.getSettingFiles().getFile()) {
               if (f.isIsMust() != null && f.isIsMust() && platformName.equalsIgnoreCase(p.getCloneRootFolderName())) {
                  ret.add(f);
               }
            }
         }
      }

      return ret;
   }

   private static Settings getSettingsInfo(List<File> settingFiles, Platform platform) {
      Settings ret = new Settings();

      for (File f : settingFiles) {
         List<com.tpvision.smartinstall.xml.Setting> settingList = analyzeSettings(f);
         ret.getSetting().addAll(settingList);
      }

      return ret;
   }

   private static List<com.tpvision.smartinstall.xml.Setting> analyzeSettings(File f) {
      List<com.tpvision.smartinstall.xml.Setting> ret = new ArrayList<>();

      try (
         FileReader fread = new FileReader(f);
         BufferedReader bread = new BufferedReader(fread);
      ) {
         Integer counter = -1;
         boolean finishedSettingInfo = false;
         String itemStr = null;
         String itemStr1 = null;
         String lastValueStr = null;
         String lastValueStr1 = null;
         String xaddrStr = null;
         int itemLength = new String("<item>").length();
         int itemLength1 = new String("<ItemID>").length();

         String line;
         while ((line = bread.readLine()) != null) {
            counter = counter + 1;
            if (line.indexOf("<item>") > -1) {
               itemStr = line.substring(line.indexOf("<item>") + itemLength, line.indexOf("</item>"));
            }

            if (line.indexOf("<ItemID>") > -1) {
               itemStr1 = line.substring(line.indexOf("<ItemID>") + itemLength1, line.indexOf("</ItemID>"));
            }

            if (line.indexOf("Xaddr") > -1) {
               xaddrStr = line.substring(line.indexOf("<Xaddr>") + "<Xaddr>".length(), line.indexOf("</Xaddr>"));
            }

            if (line.indexOf("lastvalue") > -1) {
               lastValueStr = line.substring(line.indexOf("<lastvalue>") + "<lastvalue>".length(), line.indexOf("</lastvalue>"));
            }

            if (line.indexOf("Value") > -1) {
               lastValueStr1 = line.substring(line.indexOf("<Value>") + "<Value>".length(), line.indexOf("</Value>"));
            }

            if (null != itemStr && null != xaddrStr && null != lastValueStr) {
               finishedSettingInfo = true;
            }

            if (null != itemStr1 && null != lastValueStr1) {
               finishedSettingInfo = true;
            }

            if (finishedSettingInfo) {
               com.tpvision.smartinstall.xml.Setting set = new com.tpvision.smartinstall.xml.Setting();
               set.setItem(itemStr);
               set.setItem1(itemStr1);
               set.setXaddr(xaddrStr);
               set.setLastValue(lastValueStr);
               set.setLastValue1(lastValueStr1);
               set.setPosition(new BigInteger(counter.toString()));
               set.setRefFile(f.getName());
               ret.add(set);
               itemStr = null;
               lastValueStr = null;
               itemStr1 = null;
               lastValueStr1 = null;
               xaddrStr = null;
               finishedSettingInfo = false;
            }
         }
      } catch (FileNotFoundException e) {
         LOG.error(e.getMessage(), e);
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }

      return ret;
   }

   private static CrcFiles getCrcFileDetails(List<File> crcFiles, Platform platform) {
      CrcFiles ret = new CrcFiles();

      for (File f : crcFiles) {
         ret.getFile().add(analyseFileForCrc(f, platform));
      }

      return ret;
   }

   private static String getPlatform(List<File> settingFiles) {
      File f = null;
      String itemStr = null;

      for (File file : settingFiles) {
         String absoluteName = file.getName();
         if (absoluteName.equalsIgnoreCase("BDSSettings.txt")) {
            f = file;
            break;
         }
      }

      if (f == null) {
         return null;
      }

      String line;
      try (
         FileReader fread = new FileReader(f);
         BufferedReader bread = new BufferedReader(fread);
      ) {
         while ((line = bread.readLine()) != null) {
            if (line.indexOf("<swver>") > -1) {
               itemStr = line.substring(line.indexOf("<swver>") + "<swver>".length(), line.indexOf("</swver>"));
               break;
            }
         }
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }

      return itemStr;
   }

   private static com.tpvision.smartinstall.xml.File analyseFileForCrc(File f, Platform platform) {
      com.tpvision.smartinstall.xml.File ret = new com.tpvision.smartinstall.xml.File();
      ret.setName(f.getName());
      String absolutePath = f.getAbsolutePath();
      int index = absolutePath.indexOf(platform.getCloneRootFolderName()) + platform.getCloneRootFolderName().length() + 1;
      String path = absolutePath.substring(index);
      ret.setPath(path);

      try (
         FileReader fread = new FileReader(f);
         BufferedReader bread = new BufferedReader(fread);
      ) {
         boolean firstLine = true;
         Integer counter = -1;

         String line;
         while ((line = bread.readLine()) != null) {
            counter = counter + 1;
            if (!line.trim().equals("")) {
               if (firstLine) {
                  firstLine = false;
               } else {
                  String[] split = line.split(":");
                  String prefix = split[0];
                  Crc crc = new Crc();
                  crc.setLineNo(new BigInteger(counter.toString()));
                  crc.setPrefix(prefix);
                  ret.getCrc().add(crc);
               }
            }
         }
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }

      return ret;
   }

   private static List<File> getListOfChildVariants(File file) {
      List<File> files = new ArrayList<>();

      for (File f : file.listFiles()) {
         if (!f.isDirectory()) {
            files = null;
            break;
         }

         files.add(f);
      }

      return files;
   }

   public static String processUploadMsg(Devices tv, JSONObject cloneToServerParameters) {
      String tvUniqueId = tv.getTvuniqueid();
      String tvType = tv.getType();
      LOG.info("[processUploadMsg]tyType:{},tvUniqueID:{}", tvType, tvUniqueId);
      JSONObject cloneToServerSessionStatus = cloneToServerParameters.getJSONObject("CloneToServerSessionStatus");
      String sessionStatus = cloneToServerSessionStatus.optString("SessionStatus");
      String sessionEndTime = cloneToServerSessionStatus.optString("SessionEndTime");
      int uploadItemsRecvCountCmndCount = 0;
      int uploadItemsCountTvCount = 0;
      if (uploadItemsRecvCount_cmnd.containsKey(tvUniqueId)) {
         uploadItemsRecvCountCmndCount = uploadItemsRecvCount_cmnd.get(tvUniqueId);
         LOG.info("uploadItemsRecvCount_cmnd_count:{}", uploadItemsRecvCountCmndCount);
         uploadItemsRecvCount_cmnd.remove(tvUniqueId);
      } else {
         LOG.error("uploadItemsRecvCount_cmnd_count not containsKey: {}", tvUniqueId);
      }

      if (uploadItemsCount_tv.containsKey(tvUniqueId)) {
         uploadItemsCountTvCount = uploadItemsCount_tv.get(tvUniqueId);
         uploadItemsCount_tv.remove(tvUniqueId);
         LOG.info("uploadItemsCount_tv_count:{}", uploadItemsCountTvCount);
      } else {
         LOG.error("uploadItemsCount_tv_count not containsKey: {}", tvUniqueId);
      }

      if (uploadItemsCountTvCount > 0 && uploadItemsRecvCountCmndCount > 0) {
         handleFinishDownloadCloneFiles(tv);
      }

      DevicesManager dmgr = JpaManager.getDevicesManager();
      tv.setCloneMode("Upgrade");
      tv.setUploadProgress("ST");
      tv.setUploadSessionStatus(sessionStatus);
      tv.setUploadSessionEnd(sessionEndTime);
      dmgr.save(tv);
      IPTVPooling.notifyUploadStatusChange(getUploadResult(tvUniqueId, cloneToServerParameters));
      return "";
   }

   private static JSONObject getUploadResult(String tvUID, JSONObject cloneToServerParameters) {
      List<String> availableSettingNames = getAvailableSettingNames(cloneToServerParameters);
      return checkUploadResult(availableSettingNames, tvUID);
   }

   private static List<String> getAvailableSettingNames(JSONObject cloneToServerParameters) {
      List<String> settingNames = new ArrayList<>();
      JSONArray cloneItemsAvailableToServer = cloneToServerParameters.getJSONArray("CloneItemsAvailableToServer");

      for (int i = 0; i < cloneItemsAvailableToServer.length(); i++) {
         JSONObject item = (JSONObject)cloneItemsAvailableToServer.get(i);
         if (item.has("CloneItemName")) {
            String cloneItemName = item.optString("CloneItemName");
            if (!"MainFirmware".equalsIgnoreCase(cloneItemName) && !"".equals(cloneItemName)) {
               settingNames.add(CloneItemUtils.convertJapitNameToItem(cloneItemName));
            }
         }
      }

      LOG.info("settingNames.size():{}", settingNames.size());
      return settingNames;
   }

   private static JSONObject checkUploadResult(List<String> availableSettingNames, String tvUID) {
      String uploadedFilePath = CommonConstants.SISERVER_UPLOAD_DIR + tvUID;
      File uploadedRootFile = new File(uploadedFilePath);
      if (!uploadedRootFile.exists()) {
         return new JSONObject("{\"status\":\"fail\"}").put("reason", "Nothing downloaded from TV side!");
      }

      File[] zipFiles = uploadedRootFile.listFiles();
      if (null != zipFiles) {
         for (File file : zipFiles) {
            String fileName = file.getName();
            if (fileName.endsWith(".zip")) {
               fileName = fileName.substring(0, fileName.indexOf(".zip"));
               availableSettingNames.remove(fileName);
            }
         }
      }

      return null != availableSettingNames && !availableSettingNames.isEmpty()
         ? new JSONObject("{\"status\":\"fail\"}").put("reason", availableSettingNames.size() + " clone failed to download!")
         : new JSONObject("{\"status\":\"success\"}");
   }

   private static void handleFinishDownloadCloneFiles(Devices device) {
      String configName = null;
      String tvUniqueID = device.getTvuniqueid();

      try {
         String platformId = PlatformUtils.getPlatformId(device.getType());
         configName = generateSaveCloneName(platformId);
         LOG.info("Download from TV -- configName == {}", configName);
         String baseUploadPath = CommonConstants.SISERVER_UPLOAD_DIR + device.getTvuniqueid();
         String unZipCloneLocationStr = baseUploadPath + "/" + configName;
         File unZipCloneLocation = new File(unZipCloneLocationStr);
         FileUtils.deleteQuietly(unZipCloneLocation);
         File root = new File(baseUploadPath);
         String masterCloneDataPath = unZipCloneLocationStr + "/" + platformId + "/MasterCloneData";
         File temp = new File(masterCloneDataPath);
         FileUtils.forceMkdir(temp);
         File[] files = root.listFiles();

         for (File zipFile : files) {
            if (zipFile.isFile()) {
               if (zipFile.getName().contains("DataDump")) {
                  String dataDumpPath = baseUploadPath + "/" + configName + "/" + platformId;
                  ZipCommonUtils.unzip(zipFile, dataDumpPath);
               } else {
                  ZipCommonUtils.unzip(zipFile, masterCloneDataPath);
               }
            }
         }

         loadAllConfToDb(unZipCloneLocationStr, "admin", configName, "USER_DEFINED", platformId);
      } catch (Exception e) {
         LOG.info("-> TV Upload : Zip file process error!!!!!!");
         SettingManager smgr = JpaManager.getSettingManager();
         List<Setting> settings = smgr.findSettingsByName(configName);
         if (!settings.isEmpty()) {
            smgr.deleteByKey(settings.get(0).getId());
         }

         LOG.error("Unable to upload the last file, some of the required files are missing.", e);
      } finally {
         DevicesManager dmgr = JpaManager.getDevicesManager();
         Devices tv = dmgr.loadByKey(tvUniqueID);
         tv.setUploadProgress("ST");
         dmgr.save(tv);
         LOG.info("After save uploading clonedata to DB, set tv: {} UploadProgress to ST", tvUniqueID);
      }
   }

   private static String readFileContent(String filePath) {
      File file = new File(filePath);
      if (file.exists()) {
         try {
            return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
         } catch (IOException e) {
            e.printStackTrace();
            return "";
         }
      } else {
         return "";
      }
   }
}
