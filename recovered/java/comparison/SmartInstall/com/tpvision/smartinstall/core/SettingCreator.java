package com.tpvision.smartinstall.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tpvision.smartinstall.SmartInstallConfiguration;
import com.tpvision.smartinstall.androidapp.AndroidAppHelper;
import com.tpvision.smartinstall.bean.HotelInfo;
import com.tpvision.smartinstall.core.UpgCreator.UpgCreationCommandArgument;
import com.tpvision.smartinstall.dao.core.AppPackage;
import com.tpvision.smartinstall.dao.core.Banners;
import com.tpvision.smartinstall.dao.core.ChannelPackage;
import com.tpvision.smartinstall.dao.core.GuestInfo;
import com.tpvision.smartinstall.dao.core.PlayoutInfo;
import com.tpvision.smartinstall.dao.core.Schedule;
import com.tpvision.smartinstall.dao.core.SettingPackage;
import com.tpvision.smartinstall.dao.core.SmartinfoSetting;
import com.tpvision.smartinstall.dao.core.Smartui;
import com.tpvision.smartinstall.dao.core.UiCustomizations;
import com.tpvision.smartinstall.dao.core.UpgSetting;
import com.tpvision.smartinstall.dao.core.Weather;
import com.tpvision.smartinstall.dao.core.Welcome;
import com.tpvision.smartinstall.dao.mgr.AppPackageManager;
import com.tpvision.smartinstall.dao.mgr.ChannelPackageManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.SettingManager;
import com.tpvision.smartinstall.dao.mgr.SettingPackageManager;
import com.tpvision.smartinstall.dao.mgr.SmartinfoSettingManager;
import com.tpvision.smartinstall.dao.mgr.SmartuiManager;
import com.tpvision.smartinstall.dao.mgr.WelcomeManager;
import com.tpvision.smartinstall.gateway.PlayoutUtils;
import com.tpvision.smartinstall.japit.PmsJapitCommand;
import com.tpvision.smartinstall.pms.CheckInVO;
import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.pms.PmsUtils.PmsAction;
import com.tpvision.smartinstall.util.BannerTemplateUtils;
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.ConfigurationGenerator;
import com.tpvision.smartinstall.util.ContentUtils;
import com.tpvision.smartinstall.util.Location;
import com.tpvision.smartinstall.util.LocationManager;
import com.tpvision.smartinstall.util.PSGCatalogGenerator;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.TpvFileUtils;
import com.tpvision.smartinstall.util.TpvStringUtils;
import com.tpvision.smartinstall.util.Utils;
import com.tpvision.smartinstall.util.WelcomeLogoUtils;
import com.tpvision.smartinstall.util.ZipCommonUtils;
import com.tpvision.smartinstall.util.CloneItemUtils.CloneItemInfo;
import com.tpvision.smartinstall.util.CommonConstants.CloneItemType;
import com.tpvision.smartinstall.weather.WeatherServiceImpl;
import com.tpvision.smartinstall.xml.File;
import com.tpvision.smartinstall.xml.Platform;
import com.tpvision.smartinstall.xml.Setting;
import com.tpvision.smartinstall.xml.channel.v4.TvContents;
import com.tpvision.smartinstall.xml.es2k14.HotelModeSettings;
import com.tpvision.smartinstall.xml.es2k14.HotelModelSettingsForTpn142;
import com.tpvision.smartinstall.xml.setting.v2k16.Item;
import com.tpvision.smartinstall.xml.setting.v2k16.SchemaVersion;
import com.tpvision.smartinstall.xml.setting.v2k16.TVSettings;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SettingCreator implements AutoCloseable {
   private static final String WELCOME_LOGO = "WelcomeLogo";
   private static String versionFolder = null;
   private static final Logger LOG = LoggerFactory.getLogger(SettingCreator.class);
   private String outputPath;
   private String clonePath;
   private String platformId;
   private Platform platform;
   private String iPrRFMode = "IP";
   private boolean isDownloadMode = false;

   public SettingCreator(String platform) {
      this.platformId = PlatformUtils.getPlatformId(platform);
      this.platform = SmartInstallConfiguration.instance().getPlatform(this.platformId);

      try {
         Path tmpPath = Files.createTempDirectory(new java.io.File(CommonConstants.CLONE_ASSEMBLY_LOCATION).toPath(), this.platformId + "-");
         this.outputPath = tmpPath.toString() + java.io.File.separator;
         LOG.info("init SettingCreator platform:{}, assemble path:{}", this.platformId, this.outputPath);
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }
   }

   public SettingCreator(String platform, boolean isDownloadMode) {
      this(platform);
      this.isDownloadMode = isDownloadMode;
   }

   public SettingCreator(String platform, String iPrRFMode) {
      this(platform);
      this.iPrRFMode = iPrRFMode;
   }

   public String getOutputPath() {
      return this.outputPath;
   }

   public String getClonePath() {
      return this.clonePath;
   }

   private void setClonePath(String clonePath) {
      this.clonePath = clonePath;
   }

   public void createIpPackages(String cloneType, int cloneId) {
      switch (this.platformId) {
         case "TPN141HE_CloneData":
            this.creatMS2K14IP(new java.io.File(this.outputPath), cloneId);
            break;
         default:
            this.create2K16IPPackage(cloneId, cloneType);
      }
   }

   public static int copyCloneItem(String cloneType, int id) {
      LOG.info("copy cloneType:{},id:{}", cloneType, id);
      CloneItemType playType = CloneItemType.valueOf(cloneType);
      int newId = 0;
      switch (playType) {
         case TVSettings:
            SettingPackage settingPackage = JpaManager.getSettingPackageManager().copy(id);
            newId = settingPackage.getId();
            break;
         case AndroidApps:
            AppPackage app = JpaManager.getAppPackageManager().copy(id);
            newId = app.getId();
            break;
         case Banner:
            Banners banner = JpaManager.getBannersManager().copy(id);
            newId = banner.getId();
            break;
         case ChannelList:
         case MediaChannels:
            ChannelPackage channelPackage = JpaManager.getChannelPackageManager().copy(id);
            newId = channelPackage.getId();
            break;
         case Schedules:
            Schedule schedule = JpaManager.getScheduleManager().copy(id);
            newId = schedule.getId();
            break;
         case UiCustomizations:
            UiCustomizations ui = JpaManager.getUiCustomizationsManager().copy(id);
            newId = ui.getId();
            break;
         case WelcomeLogo:
            Welcome welcome = JpaManager.getWelcomeManager().copy(id);
            newId = welcome.getId();
            break;
         default:
            LOG.debug("unsupported copy items {}", cloneType);
      }

      return newId;
   }

   public void processClonePacket(String cloneType, int id, String rid) throws IOException {
      LOG.info("processClonePacket cloneType:{},id:{}", cloneType, id);
      CloneItemType playType = CloneItemType.valueOf(cloneType);
      switch (playType) {
         case TVSettings:
            SettingPackageManager settingpackageMgr = JpaManager.getSettingPackageManager();
            SettingPackage settingpackage = settingpackageMgr.loadByKey(id);
            if (null != settingpackage) {
               this.processTVSettings(settingpackage);
            }
            break;
         case AndroidApps:
            AppPackageManager apppackageMgr = JpaManager.getAppPackageManager();
            AppPackage apppackage = apppackageMgr.loadByKey(id);
            if (null != apppackage) {
               this.processAppPackage(apppackage);
            }
            break;
         case Banner:
            Banners banners = JpaManager.getBannersManager().loadByKey(id);
            if (null != banners) {
               this.processBanner(banners);
            }
            break;
         case ChannelList:
            ChannelPackageManager channelpackageMgr = JpaManager.getChannelPackageManager();
            ChannelPackage channelpackage = channelpackageMgr.loadByKey(id);
            if (null != channelpackage) {
               this.processChannelPackage(channelpackage);
            }
            break;
         case MediaChannels:
            ChannelPackage channelpackage1 = JpaManager.getChannelPackageManager().loadByKey(id);
            if (null != channelpackage1) {
               this.processMediaChannels(channelpackage1);
            }
            break;
         case Schedules:
            Schedule schedule = JpaManager.getScheduleManager().loadByKey(id);
            if (schedule != null) {
               this.processSchedules(schedule);
            }
            break;
         case UiCustomizations:
         case ProfessionalAppsData:
            UiCustomizations uiCustomizations = JpaManager.getUiCustomizationsManager().loadByKey(id);
            if (null != uiCustomizations) {
               this.processUiCustomizations(uiCustomizations);
            }
            break;
         case WelcomeLogo:
            this.processWelcome(id);
            break;
         case Clone:
            SettingManager settingMgr = JpaManager.getSettingManager();
            com.tpvision.smartinstall.dao.core.Setting setting = settingMgr.loadByKey(id);
            if (null != setting) {
               this.processSettings(setting);
            }
            break;
         case Firmware:
            UpgSetting upgSetting = JpaManager.getUpgSettingManager().loadByKey(id);
            if (null != upgSetting) {
               this.processFirmware(upgSetting);
            }
            break;
         case SmartInfoBrowser:
            this.processContent(id);
            break;
         case PMS:
            this.processPMS(id, rid);
            break;
         default:
            LOG.error("not support to playout:{}", playType.name());
      }
   }

   private void processPMS(int id, String rid) {
      java.io.File pmsfile = new java.io.File(this.outputPath + "/PMS/PMS.json");
      PmsAction pmsAction = PmsAction.values()[id];
      this.generatePMSFile(pmsAction, pmsfile, rid);
   }

   private void generatePMSFile(PmsAction pmsAction, java.io.File pmsfile, String rid) {
      String respData = "";
      switch (pmsAction) {
         case CheckOut:
            respData = PmsJapitCommand.getCheckOut();
            break;
         case CheckIn:
            GuestInfo gix = PmsUtils.getGuestInfoByRoomId(rid);
            if (null != gix) {
               CheckInVO checkinVO = PmsUtils.getCheckInVO(gix);
               respData = PmsJapitCommand.getCheckIn(checkinVO);
            }
            break;
         case LanguageChange:
            GuestInfo gi = PmsUtils.getGuestInfoByRoomId(rid);
            if (null != gi) {
               CheckInVO checkinVO = PmsUtils.getCheckInVO(gi);
               respData = PmsJapitCommand.getUpdateGuestPreference(checkinVO);
            }
      }

      respData = PmsUtils.convertToPMSOfflineService(respData, rid);
      pmsfile.getParentFile().mkdirs();

      try {
         FileUtils.writeStringToFile(pmsfile, respData, StandardCharsets.UTF_8);
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }

      this.processIdentifier("PMS", null);
   }

   public void processContent(int contentId) throws IOException {
      String copyPath = ContentUtils.copyWebsiteToAssembly(String.valueOf(contentId), this.platformId, this.outputPath);
      if (this.platformId.equalsIgnoreCase("TPN141HE_CloneData") || this.platformId.equalsIgnoreCase("TPN142HE_CloneData")) {
         String targetName = PlatformUtils.getRootFolderName(this.platformId) + "_" + PlatformUtils.getSmartinfoDirctoryByPlatform(this.platformId);
         ZipCommonUtils.gen7Zip(copyPath, CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + targetName + ".zip");
      }
   }

   public static void createRoomSpecificSettings(String roomID, String tvSerialNumber, String tvIP, String platform) {
      String roomSpecificSettings = "";
      if ("TPM187HE_CloneData".equalsIgnoreCase(PlatformUtils.getPlatformId(platform))) {
         roomSpecificSettings = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<RoomSpecificSettings>\r\n  <SchemaVersion MajorVerNo=\"1\" MinorVerNo=\"0\"/>\r\n  <TV>\r\n    <SerialNumber>"
            + tvSerialNumber
            + "</SerialNumber>\r\n    <item>\r\n      <Name>Identification Settings.RoomID</Name>\r\n      <Value>"
            + roomID
            + "</Value>\r\n    </item>\r\n  </TV>\r\n</RoomSpecificSettings>";
      } else {
         boolean isMasf = PlatformUtils.isMasf2019Up(platform);
         String namePrefix = isMasf ? "Professional Settings." : "";
         roomSpecificSettings = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<RoomSpecificSettings>\r\n  <SchemaVersion MajorVerNo=\"1\" MinorVerNo=\"0\"/>\r\n  <TV>\r\n    <SerialNumber>"
            + tvSerialNumber
            + "</SerialNumber>\r\n    <item>\r\n      <Name>"
            + namePrefix
            + "Advanced.Identification Settings.RoomID</Name>\r\n      <Value>"
            + roomID
            + "</Value>\r\n    </item>\r\n  </TV>\r\n</RoomSpecificSettings>";
      }

      String ipName = tvIP.replace(".", "");

      try {
         String dirPath = CommonConstants.SISERVER_CONF_DIR + ipName + "/MasterCloneData/RoomSpecificSettings/";
         java.io.File dirFile = new java.io.File(dirPath);
         FileUtils.forceMkdir(dirFile);
         String xmlPath = dirPath + "/RoomSpecificSettings.xml";
         java.io.File xmlFile = new java.io.File(xmlPath);
         FileUtils.writeStringToFile(xmlFile, roomSpecificSettings, StandardCharsets.UTF_8);
         String identifierPath = dirPath + "/RoomSpecificSettings_Identifier.txt";
         java.io.File identifierFile = new java.io.File(identifierPath);
         FileUtils.writeStringToFile(identifierFile, TpvDateUtils.getCurrentIndentifierFormatTime(), StandardCharsets.UTF_8);
         String zipPath = CommonConstants.servletContextPath + "/Profile/Clone/" + ipName + "/";
         java.io.File zipDirectory = new java.io.File(zipPath);
         FileUtils.deleteDirectory(zipDirectory);
         zipDirectory.mkdirs();
         String dirPathTmp = CommonConstants.SISERVER_CONF_DIR + ipName + "/MasterCloneData/";
         ZipCommonUtils.createZip(dirPathTmp, zipPath + "RoomSpecificSettings.zip");
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }
   }

   public static String createModifyRoomIdTVSetting(String tvIP, String newRoomId) {
      String tvSettingContent = "<?xml version='1.0' encoding='UTF-8' ?>\r\n<TVSettings>\r\n  <SchemaVersion MajorVerNo=\"1\" MinorVerNo=\"0\" />\r\n  <item>\r\n    <Name>Identification Settings.Room ID</Name>\r\n    <Value>"
         + newRoomId
         + "</Value>\r\n    <CloneIn>Yes</CloneIn>\r\n  </item>\r\n</TVSettings>";
      return generateSimpleTvSettingsClone(tvIP, tvSettingContent);
   }

   public static String createModifyServiceUrlTVSetting(String tvIP, String cmndIP) {
      String tvSettingContent = "<?xml version='1.0' encoding='UTF-8' ?>\r\n\r\n\r\n\r\n<TVSettings>\r\n  <SchemaVersion MajorVerNo=\"1\" MinorVerNo=\"0\" />\r\n  <item>\r\n    <Name>Wireless and Networks.Control TV Over IP.WebServices.Server URL</Name>\r\n    <Value>http://"
         + cmndIP
         + ":"
         + CommonConstants.CMND_HTTP_PORT
         + "/SmartInstall/webservices.jsp</Value>\r\n    <CloneIn>Yes</CloneIn>\r\n  </item>\r\n  <item>\r\n    <Name>Wireless and Networks.Control TV Over IP.Secure Communication.Server URL</Name>\r\n    <Value>https://"
         + cmndIP
         + ":"
         + CommonConstants.CMND_HTTPS_PORT
         + "/SmartInstall/webservices.jsp</Value>\r\n    <CloneIn>Yes</CloneIn>\r\n  </item>\r\n  <item>\r\n    <Name>Wireless and Networks.Control TV Over IP.WebServices.TVDiscoveryService</Name>\r\n    <Value>On</Value>\r\n    <CloneIn>Yes</CloneIn>\r\n  </item>\r\n  <item>\r\n    <Name>Wireless and Networks.Control TV Over IP.WebServices.ProfessionalSettingsService</Name>\r\n    <Value>On</Value>\r\n    <CloneIn>Yes</CloneIn>\r\n  </item>\r\n  <item>\r\n    <Name>Wireless and Networks.Control TV Over IP.WebServices.IPUpgradeService</Name>\r\n    <Value>On</Value>\r\n    <CloneIn>Yes</CloneIn>\r\n  </item>\r\n  <item>\r\n    <Name>Wireless and Networks.Control TV Over IP.WebServices.PMSService</Name>\r\n    <Value>On</Value>\r\n    <CloneIn>Yes</CloneIn>\r\n  </item>\r\n  <item>\r\n    <Name>Wireless and Networks.Control TV Over IP.WebListeningServices.PowerService</Name>\r\n    <Value>On</Value>\r\n    <CloneIn>Yes</CloneIn>\r\n  </item>\r\n  <item>\r\n    <Name>Wireless and Networks.Control TV Over IP.WebListeningServices.TVDiscoveryService</Name>\r\n    <Value>On</Value>\r\n    <CloneIn>Yes</CloneIn>\r\n  </item>\r\n  <item>\r\n    <Name>Wireless and Networks.Control TV Over IP.WebListeningServices.IPUpgradeService</Name>\r\n    <Value>On</Value>\r\n    <CloneIn>Yes</CloneIn>\r\n  </item>\r\n  <item>\r\n    <Name>Wireless and Networks.Control TV Over IP.WebListeningServices.PMSService</Name>\r\n    <Value>On</Value>\r\n    <CloneIn>Yes</CloneIn>\r\n  </item>\r\n\r\n</TVSettings>";
      return generateSimpleTvSettingsClone(tvIP, tvSettingContent);
   }

   public void processWelcomeLogo(Welcome welcome) throws IOException {
      String logoName = welcome.getName();
      java.io.File logoFile = new java.io.File(CommonConstants.WELCOME_LOGO_IMG_LOCATION + logoName);
      java.io.File directoryPath = new java.io.File(this.outputPath + java.io.File.separator + "WelcomeLogo" + java.io.File.separator);
      if (!directoryPath.exists()) {
         directoryPath.mkdirs();
      }

      String ext = FilenameUtils.getExtension(logoName);
      String targetName = "WelcomeLogo." + ext.toLowerCase();
      java.io.File targetFile = new java.io.File(directoryPath.getAbsolutePath() + java.io.File.separator + targetName);
      FileUtils.copyFile(logoFile, targetFile);
      this.processIdentifier("WelcomeLogo", welcome.getLastEdit());
   }

   public void processUiCustomizations(UiCustomizations ui) throws IOException {
      String professionalAppsDataPath = this.outputPath + java.io.File.separator + "ProfessionalAppsData" + java.io.File.separator;
      java.io.File directoryPath = new java.io.File(professionalAppsDataPath);
      java.io.File srcDirectoryStr = new java.io.File(
         CommonConstants.CLONE_PROCESS_LOCATION + "uiCustomizations" + java.io.File.separator + ui.getId() + java.io.File.separator + "PhilipsHome"
      );

      try {
         if (srcDirectoryStr.exists()) {
            FileUtils.copyDirectoryToDirectory(srcDirectoryStr, directoryPath);
            LOG.info("copy uiCustomizations dir {} to {}", srcDirectoryStr.getAbsolutePath(), professionalAppsDataPath);
         } else {
            LOG.error("not exist uiCustomizations");
         }
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }

      this.updateProfessionalAppsJSON(professionalAppsDataPath, "PhilipsHome", ui.getLastEdit());
      this.processIdentifier("ProfessionalAppsData", ui.getLastEdit());
   }

   private void removeProfessionalAppsJSON(String path, String... applicationNames) {
      java.io.File jsonFile = new java.io.File(path + java.io.File.separator + "ProfessionalAppsDataVersion.json");

      try {
         String jsonContent = jsonFile.exists() ? FileUtils.readFileToString(jsonFile, StandardCharsets.UTF_8) : "{}";
         JSONObject jo = new JSONObject(jsonContent);
         JSONArray jaDetails = jo.has("ApplicationCloneDataDetails") ? jo.getJSONArray("ApplicationCloneDataDetails") : new JSONArray();
         JSONArray newJaDetails = new JSONArray();

         for (int i = 0; i < jaDetails.length(); i++) {
            JSONObject tmpJo = jaDetails.getJSONObject(i);
            if (!tmpJo.has("ApplicationName") || !Arrays.asList(applicationNames).contains(tmpJo.getString("ApplicationName"))) {
               newJaDetails.put(tmpJo);
            }
         }

         jo.put("ApplicationCloneDataDetails", newJaDetails);
         FileUtils.writeStringToFile(jsonFile, jo.toString(4), StandardCharsets.UTF_8);
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }
   }

   private void updateProfessionalAppsJSON(String path, String applicationName, String identifier) {
      java.io.File jsonFile = new java.io.File(path + "ProfessionalAppsDataVersion.json");

      try {
         String jsonContent = jsonFile.exists() ? FileUtils.readFileToString(jsonFile, StandardCharsets.UTF_8) : "{}";
         JSONObject jo = new JSONObject(jsonContent);
         JSONArray jaDetails = jo.has("ApplicationCloneDataDetails") ? jo.getJSONArray("ApplicationCloneDataDetails") : new JSONArray();
         boolean isNew = false;
         JSONObject joDetail = null;

         for (int i = 0; i < jaDetails.length(); i++) {
            JSONObject tmpJo = jaDetails.getJSONObject(i);
            if (tmpJo.has("ApplicationName") && tmpJo.getString("ApplicationName").equalsIgnoreCase(applicationName)) {
               joDetail = tmpJo;
               break;
            }
         }

         if (null == joDetail) {
            joDetail = new JSONObject();
            isNew = true;
         }

         joDetail.put("ApplicationName", applicationName);
         joDetail.put("ApplicationCloneDataVersion", identifier);
         if (isNew) {
            jaDetails.put(joDetail);
         }

         jo.put("ApplicationCloneDataDetails", jaDetails);
         FileUtils.writeStringToFile(jsonFile, jo.toString(4), StandardCharsets.UTF_8);
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }
   }

   public void processBanner(Banners banners) throws IOException {
      java.io.File directoryPath = new java.io.File(this.outputPath + java.io.File.separator + "Banner" + java.io.File.separator);
      if (!directoryPath.exists()) {
         directoryPath.mkdirs();
      }

      BannerTemplateUtils.generateBannerCloneFilesByBanner(directoryPath, banners);
      this.processIdentifier("Banner", CloneItemUtils.getBannerIdentifier(banners));
   }

   public void processAppPackage(AppPackage appPackage) {
      java.io.File srcDirApps = new java.io.File(CloneItemUtils.getAppPackageDataPath(appPackage.getId()));
      java.io.File destDirApps = new java.io.File(this.outputPath + java.io.File.separator + CloneItemType.AndroidApps.name());

      try {
         FileUtils.copyDirectory(srcDirApps, destDirApps);
         java.io.File metaJson = new java.io.File(destDirApps.getAbsolutePath() + "/AndroidAppsMetaData.json");
         if (!metaJson.exists()) {
            AndroidAppHelper.saveAppsStringToMetaJsonFile(appPackage.getValue(), metaJson);
         }
      } catch (IOException e) {
         LOG.error(e.getMessage());
         return;
      }

      this.processIdentifier(CloneItemType.AndroidApps.name(), appPackage.getLastEdit());
   }

   public void processTVSettings(SettingPackage settingPackage) {
      List<Setting> listOfSettings = this.getSettingList(settingPackage.getValue());
      this.processSettings(listOfSettings);
      this.processFileContent(settingPackage.getTermAndConditions(), "TVSettings", "CustomTermsAndConditions.json");
      this.processFileContent(settingPackage.getHtvTlsPskKey(), "TVSettings", "HTV_TLS_PSK.KEY");
      this.processIdentifier("TVSettings", settingPackage.getLastEdit());
   }

   private void processFileContent(String content, String subDirectory, String fileName) {
      if (StringUtils.isNotBlank(content)) {
         String unescapedContent = StringEscapeUtils.unescapeJava(content);
         java.io.File directory = new java.io.File(this.outputPath + java.io.File.separator + subDirectory + java.io.File.separator);
         if (!directory.exists()) {
            directory.mkdirs();
         }

         java.io.File outputFile = new java.io.File(directory, fileName);

         try {
            FileUtils.write(outputFile, unescapedContent, StandardCharsets.UTF_8);
         } catch (IOException e) {
            LOG.error("Failed to write file: " + outputFile.getAbsolutePath(), e);
         }
      }
   }

   private void processHtvTlsPskKey(SettingPackage settingPackage) {
      String htvTlsPskKeyValue = settingPackage.getHtvTlsPskKey();
      htvTlsPskKeyValue = StringEscapeUtils.unescapeJava(htvTlsPskKeyValue);
      if (StringUtils.isNotBlank(htvTlsPskKeyValue)) {
         java.io.File directoryPath = new java.io.File(this.outputPath + java.io.File.separator + "TVSettings" + java.io.File.separator);
         if (!directoryPath.exists()) {
            directoryPath.mkdirs();
         }

         String termJsonPath = directoryPath + java.io.File.separator + "HTV_TLS_PSK.KEY";
         java.io.File termJsonFile = new java.io.File(termJsonPath);

         try {
            FileUtils.write(termJsonFile, htvTlsPskKeyValue, StandardCharsets.UTF_8);
         } catch (IOException e) {
            LOG.error(e.getMessage(), e);
         }
      }
   }

   private boolean isCloneDataEmpty(String path) {
      if (path == null) {
         return true;
      } else {
         java.io.File cloneItemDir = new java.io.File(path);
         if (cloneItemDir.exists()) {
            java.io.File[] subFiles = cloneItemDir.listFiles();
            int length = subFiles != null ? subFiles.length : 0;
            return length == 1 && subFiles[0].getName().contains("_Identifier.txt") || length < 1;
         } else {
            return true;
         }
      }
   }

   private void processMediaChannels(ChannelPackage channelPackage) {
      String channelBasePath = CloneItemUtils.getChannelPackageDataPath(channelPackage);
      String channelItem = "MediaChannels";
      java.io.File srcDir = new java.io.File(channelBasePath + channelItem);
      if (!srcDir.exists()) {
         LOG.warn("MediaChannels not exists,skip copy");
      } else {
         TpvFileUtils.copyDirectoryIngoreExistsFile(srcDir, new java.io.File(this.outputPath + channelItem));
         this.processIdentifier("MediaChannels", channelPackage.getLastEdit());
      }
   }

   private void processChannelist(ChannelPackage channelPackage) {
      this.processChannel(channelPackage.getValue());
      this.copyChannelListFiles(channelPackage);
      this.processIdentifier("ChannelList", channelPackage.getLastEdit());
   }

   public void processChannelPackage(ChannelPackage channelPackage) {
      this.processChannelist(channelPackage);
      this.processMediaChannels(channelPackage);
   }

   public void processSettingsUnSupportFile(String cloneType, String cloneName) {
      String fileName = CloneItemUtils.getCloneItemFileName(cloneType);

      try {
         String srcRootPath = CommonConstants.CLONE_PROCESS_LOCATION + cloneName + java.io.File.separator + this.platformId + "/MasterCloneData/" + fileName;
         String destRootPath = this.outputPath + fileName;
         java.io.File srcDir = new java.io.File(srcRootPath);
         if (srcDir.exists()) {
            java.io.File destDir = new java.io.File(destRootPath);
            FileUtils.copyDirectory(srcDir, destDir);
         }
      } catch (Exception ex) {
         LOG.error(ex.getMessage(), ex);
      }
   }

   private void setCloneReady(boolean isReady) {
      LOG.info("set clone ready ->{}", isReady);
      java.io.File lock = new java.io.File(this.outputPath, ".lock");
      if (isReady) {
         FileUtils.deleteQuietly(lock);
      } else {
         try {
            if (!lock.createNewFile()) {
               LOG.warn("create .lock file failed");
            }
         } catch (IOException e) {
            LOG.warn(e.getMessage());
         }
      }
   }

   private boolean isCloneReady() {
      java.io.File lock = new java.io.File(this.outputPath, ".lock");
      return !lock.exists();
   }

   private void waitForCloneReady(int timeout) {
      int countDown = timeout * 5;

      while (!this.isCloneReady() && countDown-- > 0) {
         try {
            Thread.sleep(200L);
         } catch (InterruptedException e) {
            e.printStackTrace();
         }
      }

      if (countDown <= 0) {
         LOG.warn("{} clone package cached not ready after 60s,clone package maybe not full", this.outputPath);
      } else {
         LOG.info("{} clone package cached is ready", this.outputPath);
      }
   }

   private boolean makeCloneOutput(com.tpvision.smartinstall.dao.core.Setting setting) {
      if (this.outputPath != null) {
         this.cleanAssemblyDir();
      }

      String outputName = String.format(
         Locale.ENGLISH, "%s/CLONE_%d_%d/", CommonConstants.CLONE_ASSEMBLY_LOCATION, setting.getId(), setting.getLastUpdatedDate().getTime()
      );
      java.io.File output = new java.io.File(outputName);
      if (output.exists() && PlatformUtils.isAsta2016Up(this.platformId)) {
         this.outputPath = output.getAbsolutePath() + "/";
         if (!this.isCloneReady()) {
            LOG.info("{} clone not ready, waiting for process", this.outputPath);
            this.waitForCloneReady(60);
         }

         return true;
      } else {
         cleanCachedCloneData(setting.getId());
         output.mkdirs();
         this.outputPath = output.getAbsolutePath() + "/";
         this.setCloneReady(false);
         return false;
      }
   }

   public static void cleanCachedCloneData(int settingId) {
      java.io.File assembleDir = new java.io.File(CommonConstants.CLONE_ASSEMBLY_LOCATION);
      if (assembleDir.exists()) {
         java.io.File[] files = assembleDir.listFiles();
         if (files != null) {
            for (java.io.File file : files) {
               if (file.getName().startsWith("CLONE_" + settingId + "_")) {
                  LOG.info("remove cached clone dir:{}", file.getName());
                  FileUtils.deleteQuietly(file);
               }
            }
         }
      }
   }

   public void processSettings(com.tpvision.smartinstall.dao.core.Setting setting) {
      LOG.info("process clone:{} ", setting.getName());
      long startTime = System.currentTimeMillis();
      if (this.makeCloneOutput(setting)) {
         LOG.info("clone package cached");
      } else {
         String settingProcessPath = this.getSettingCloneProcessPath(setting);

         try {
            this.preProcessChecksES(null);
            int settingPackageId = setting.getSettingPackageId();
            if (settingPackageId > 0) {
               SettingPackage settingPackage = JpaManager.getSettingPackageManager().loadByKey(settingPackageId);
               if (null != settingPackage) {
                  this.processTVSettings(settingPackage);
               } else {
                  LOG.error("settignPackage assigned not existed,id={}", settingPackageId);
               }
            }

            int channelPackageId = setting.getChannelPackageId();
            if (channelPackageId > 0) {
               ChannelPackage channelPackage = JpaManager.getChannelPackageManager().loadByKey(channelPackageId);
               if (null != channelPackage) {
                  this.processChannelPackage(channelPackage);
               } else {
                  LOG.error("channelPackage assigned not existed,id={}", channelPackageId);
               }
            }

            int appPackageId = setting.getAppPackageId();
            if (appPackageId > 0) {
               AppPackage appPackage = JpaManager.getAppPackageManager().loadByKey(appPackageId);
               if (null != appPackage) {
                  this.processAppPackage(appPackage);
               } else {
                  LOG.error("appPackage assigned not existed,id={}", appPackageId);
               }
            }

            this.prepareBaseProfessionalAppsData(
               settingProcessPath, TpvDateUtils.formatSiCloneIdentifiers(PlatformUtils.getPlatformName(setting.getPlatform()), setting.getLastUpdatedDate())
            );
            if (setting.getWelcomeId() > 0) {
               this.processWelcome(setting.getWelcomeId());
            }

            int uiid = setting.getUiCustomizationsId();
            if (uiid > 0) {
               UiCustomizations uiCustomizations = JpaManager.getUiCustomizationsManager().loadByKey(uiid);
               if (uiCustomizations != null) {
                  this.processUiCustomizations(uiCustomizations);
               } else {
                  LOG.error("uiCustomizations assigned not existed,id={}", uiid);
               }
            }

            int schId = setting.getScheduleId();
            if (schId > 0) {
               Schedule schedule = JpaManager.getScheduleManager().loadByKey(schId);
               if (schedule != null && !"TPS191HE_CloneData".equals(this.platformId)) {
                  this.processSchedules(schedule);
               } else {
                  LOG.error("schedule assigned not existed,id={}", schId);
               }
            }

            int bannerId = setting.getBannersId();
            if (bannerId > 0) {
               Banners banners = JpaManager.getBannersManager().loadByKey(bannerId);
               if (banners != null) {
                  this.processBanner(banners);
               } else {
                  LOG.error("banners assigned not existed,id={}", bannerId);
               }
            }

            this.processUnchangedFiles(settingProcessPath);
            this.processHotelInfo(settingProcessPath, setting, false);
            this.processThemeTv(settingProcessPath);
            this.processSmartPin(settingProcessPath);
            if (!this.platformId.equalsIgnoreCase("TPN141HE_CloneData") && !this.platformId.equalsIgnoreCase("TPN142HE_CloneData")) {
               this.process2K16Files(settingProcessPath);
            } else {
               this.process2K14MSFiles(settingProcessPath);
            }

            if (!PlatformUtils.isMasf2019Up(this.platformId)) {
               if (this.iPrRFMode.equalsIgnoreCase("RF")) {
                  this.processCrc(settingProcessPath, setting, new Date(System.currentTimeMillis()));
               } else {
                  this.processCrc(settingProcessPath, setting, setting.getLastUpdatedDate());
               }
            }

            this.updateCloneItemStatus(setting);
         } catch (IOException e1) {
            LOG.error(e1.getMessage(), e1);
         } finally {
            this.setCloneReady(true);
         }

         long endTime = System.currentTimeMillis();
         long totalTime = endTime - startTime;
         LOG.info("process clone data finished,used {} ms", totalTime);
      }
   }

   private void prepareBaseProfessionalAppsData(String settingProcessPath, String settingIdentifer) throws IOException {
      java.io.File oriFolder = new java.io.File(settingProcessPath + java.io.File.separator + "ProfessionalAppsData");
      if (oriFolder.exists() && oriFolder.isDirectory()) {
         java.io.File[] copyRequredFiles = oriFolder.listFiles(
            (dir, name) -> !StringUtils.equalsAnyIgnoreCase(
               name,
               "org.droidtv.welcome",
               "PhilipsHome",
               "ProfessionalAppsData_History.xml",
               "ProfessionalAppsData_Identifier.txt",
               "ProfessionalAppsDataVersion.json"
            )
         );
         if (copyRequredFiles != null && copyRequredFiles.length != 0) {
            java.io.File outputFolder = new java.io.File(this.outputPath + java.io.File.separator + "ProfessionalAppsData" + java.io.File.separator);

            for (java.io.File file : copyRequredFiles) {
               FileUtils.copyToDirectory(file, outputFolder);
            }

            this.processIdentifier("ProfessionalAppsData", settingIdentifer);
            java.io.File oriAppVersionFile = new java.io.File(oriFolder + java.io.File.separator + "ProfessionalAppsDataVersion.json");
            if (oriAppVersionFile.exists() && oriAppVersionFile.isFile()) {
               FileUtils.copyFileToDirectory(oriAppVersionFile, outputFolder);
               this.removeProfessionalAppsJSON(outputFolder.getAbsolutePath(), "org.droidtv.welcome", "PhilipsHome");
            }
         } else {
            LOG.info("no extract files required to be prepared for the ProfessionalAppsData folder");
         }
      } else {
         LOG.info("professionalAppsData folder not exist");
      }
   }

   private String getSettingCloneProcessPath(com.tpvision.smartinstall.dao.core.Setting setting) {
      String settingProcessPath = CommonConstants.CLONE_PROCESS_LOCATION
         + setting.getName()
         + java.io.File.separator
         + setting.getPlatform()
         + java.io.File.separator;
      if (TpvFileUtils.getDirectoryByName(new java.io.File(settingProcessPath), "MasterCloneData") != null) {
         settingProcessPath = settingProcessPath + "MasterCloneData" + java.io.File.separator;
      }

      return settingProcessPath;
   }

   public static boolean createMyChoiceRFClonePackage(
      String saveZipFilePath, String platformId, String room, JSONArray myChoiceParameterArray, List<PlayoutInfo> mergedPlayouts
   ) {
      try (SettingCreator settingCreator = new SettingCreator(platformId)) {
         String rootAssembleDir = settingCreator.outputPath;
         String assembleDir = rootAssembleDir + "MyChoice";
         String headerZeroRoomId = TpvStringUtils.getTvRoomId(room);
         String myChoiceData = "{  \"MyChoice\": [    {      \"Svc\": \"OfflineServices\",      \"SvcVer\": \"4.0\",      \"Cookie\": 293,      \"CmdType\": \"Change\",      \"Fun\": \"MYChoice\",      \"CommandDetails\": {        \"OfflineServiceParameters\": {          \"RoomID\": \""
            + headerZeroRoomId
            + "\"        },        \"MyChoiceParameters\": "
            + myChoiceParameterArray.toString()
            + "      }    }  ]} ";
         List<String> existedRoomIds = new ArrayList<>();
         existedRoomIds.add(headerZeroRoomId);
         JsonObject resultJson = JsonParser.parseString(myChoiceData).getAsJsonObject();

         for (PlayoutInfo oldPlayout : mergedPlayouts) {
            java.io.File oldPlayoutFile = new java.io.File(PlayoutUtils.getRFZipFullPath(oldPlayout));
            if (oldPlayoutFile.exists()) {
               try {
                  String tempDir = CommonConstants.USER_ZIP_TEMP_LOCATION + UUID.randomUUID().toString() + java.io.File.separator;
                  ZipCommonUtils.unZipFiles(oldPlayoutFile, tempDir);
                  java.io.File oldJsonFile = new java.io.File(tempDir + "MyChoice" + java.io.File.separator + "MyChoice.json");
                  JsonObject oldMychoiceJson = JsonParser.parseString(FileUtils.readFileToString(oldJsonFile, StandardCharsets.UTF_8)).getAsJsonObject();
                  JsonArray array = oldMychoiceJson.getAsJsonArray("MyChoice");

                  for (int i = 0; i < array.size(); i++) {
                     JsonObject obj = array.get(i).getAsJsonObject();
                     String roomId = obj.getAsJsonObject("CommandDetails").getAsJsonObject("OfflineServiceParameters").get("RoomID").getAsString();
                     if (!existedRoomIds.contains(roomId)) {
                        resultJson.getAsJsonArray("MyChoice").add(obj);
                        existedRoomIds.add(roomId);
                     }
                  }

                  FileUtils.deleteDirectory(new java.io.File(tempDir));
               } catch (Exception ex) {
                  LOG.error(ex.getMessage(), ex);
               }
            }
         }

         myChoiceData = new GsonBuilder().setPrettyPrinting().create().toJson(resultJson);
         String mychoiceDataFile = assembleDir + java.io.File.separator + "MyChoice.json";
         FileUtils.writeStringToFile(new java.io.File(mychoiceDataFile), myChoiceData, StandardCharsets.UTF_8);
         String identifierFilePath = assembleDir + java.io.File.separator + "MyChoice_Identifier.txt";
         FileUtils.writeStringToFile(new java.io.File(identifierFilePath), TpvDateUtils.getCurrentIndentifierFormatTime(), StandardCharsets.UTF_8);
         ZipCommonUtils.zipFiles(assembleDir, saveZipFilePath);
         return true;
      } catch (Exception ex) {
         LOG.error(ex.getMessage(), ex);
         return false;
      }
   }

   public static boolean createWeatherForecastClonePackage(String saveZipFilePath) {
      Location location = null;

      try {
         location = LocationManager.getLocationFromFile();
      } catch (Exception ex) {
         LOG.error(ex.getMessage(), ex);
      }

      if (location == null) {
         LOG.error("location data is null");
         return false;
      }

      String geonameid = location.getGeonameid();
      if (StringUtils.isBlank(geonameid)) {
         LOG.error("geonameid is blank, exit!");
         return false;
      }

      WeatherServiceImpl wsi = new WeatherServiceImpl();
      Weather weather = wsi.refreshCurrentWeather(geonameid, null);
      if (weather == null) {
         LOG.error("weather data retrieval fail, exit!");
         return false;
      }

      String forcastData = weather.getForecasts();

      try (SettingCreator settingCreator = new SettingCreator("TPM181HE_CloneData")) {
         String rootAssembleDir = settingCreator.outputPath;
         String assembleDir = rootAssembleDir + "WeatherForecast";
         String weatherDataFile = assembleDir + java.io.File.separator + "WeatherForecast.txt";
         FileUtils.writeStringToFile(new java.io.File(weatherDataFile), forcastData, StandardCharsets.UTF_8);
         String identifierFilePath = assembleDir + java.io.File.separator + "WeatherForecast_Identifier.txt";
         FileUtils.writeStringToFile(new java.io.File(identifierFilePath), TpvDateUtils.getCurrentIndentifierFormatTime(), StandardCharsets.UTF_8);
         ZipCommonUtils.zipFiles(assembleDir, saveZipFilePath);
         return true;
      } catch (Exception ex) {
         LOG.error(ex.getMessage(), ex);
         return false;
      }
   }

   public void processSchedules(Schedule schedule) {
      java.io.File directoryPath = new java.io.File(this.outputPath + java.io.File.separator + "Schedules" + java.io.File.separator);
      if (!directoryPath.exists()) {
         directoryPath.mkdirs();
      }

      String srcPath = CloneItemUtils.getSchedulesPackageDataPath(schedule.getId()) + "Schedules";
      java.io.File srcDirectoryStr = new java.io.File(srcPath);
      if (!srcDirectoryStr.exists()) {
         srcDirectoryStr.mkdir();
      }

      String scheduleJsonPath = srcPath + java.io.File.separator + "Schedules.json";
      java.io.File scheduleJsonFile = new java.io.File(scheduleJsonPath);

      try {
         String content = schedule.getContent();
         FileUtils.write(scheduleJsonFile, content, StandardCharsets.UTF_8);
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }

      try {
         if (srcDirectoryStr.exists()) {
            FileUtils.copyDirectory(srcDirectoryStr, directoryPath);
            LOG.info("copy schedules from {} to {}", srcDirectoryStr.getAbsolutePath(), directoryPath.getAbsolutePath());
         } else {
            LOG.info("not exist schedules");
         }
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }

      this.processIdentifier("Schedules", schedule.getLastEdit());
   }

   public void processWelcome(int welcomeId) throws IOException {
      String welcomePath = WelcomeLogoUtils.getWelcomePath(String.valueOf(welcomeId));
      java.io.File welcomePathFile = new java.io.File(welcomePath);
      if (welcomePathFile.exists()) {
         Welcome welcome = JpaManager.getWelcomeManager().loadByKey(welcomeId);
         int type = welcome.getType();
         String path = this.outputPath;
         String identifier = "WelcomeLogo";
         switch (type) {
            case 0:
               path = path + java.io.File.separator + "WelcomeLogo" + java.io.File.separator;
               break;
            case 1:
               String jsonFilePath = path + java.io.File.separator + "ProfessionalAppsData" + java.io.File.separator;
               this.updateProfessionalAppsJSON(jsonFilePath, "org.droidtv.welcome", welcome.getLastEdit());
               identifier = "ProfessionalAppsData";
               path = path + java.io.File.separator + "ProfessionalAppsData" + java.io.File.separator + "org.droidtv.welcome" + java.io.File.separator;
               break;
            case 2:
               path = path + java.io.File.separator;
         }

         java.io.File targetFile = new java.io.File(path);
         targetFile.getParentFile().mkdirs();
         FileUtils.copyDirectory(welcomePathFile, targetFile);
         this.processIdentifier(identifier, welcome.getLastEdit());
      }
   }

   private void updateCloneItemStatus(com.tpvision.smartinstall.dao.core.Setting setting) {
      Map<String, Boolean> cloneItemAvailable = new HashMap<>();

      for (String cloneItem : CommonConstants.cloneItems) {
         if (new java.io.File(this.outputPath + java.io.File.separator + cloneItem).exists()) {
            cloneItemAvailable.put(cloneItem, this.checkCloneItemAvailable(cloneItem));
         }
      }

      this.updateCloneItemStatus(cloneItemAvailable, setting);
   }

   private void updateCloneItemStatus(Map<String, Boolean> cloneItemAvailable, com.tpvision.smartinstall.dao.core.Setting setting) {
      SettingManager settingManager = JpaManager.getSettingManager();
      StringBuilder cloneItemBuilder = new StringBuilder();
      cloneItemBuilder.append("{\"cloneItemStatus\":[");
      StringBuilder cloneItem = new StringBuilder();
      if (cloneItemAvailable.size() > 0) {
         for (Entry<String, Boolean> mapEntry : cloneItemAvailable.entrySet()) {
            String cloneItemName = mapEntry.getKey();
            Boolean status = mapEntry.getValue();
            if ("ChannelList".equalsIgnoreCase(cloneItemName)) {
               cloneItemName = "TVChannelList";
            } else if ("SmartInfoShow".equalsIgnoreCase(cloneItemName)) {
               cloneItemName = "SmartInfoImages";
            } else if ("SmartInfoBrowser".equalsIgnoreCase(cloneItemName)) {
               cloneItemName = "SmartInfoPages";
            } else if ("LocalCustomDashboard".equalsIgnoreCase(cloneItemName)) {
               cloneItemName = "CustomDashboardFallback";
            }

            cloneItem.append("{");
            cloneItem.append("\"CloneItemName\":\"").append(cloneItemName).append("\",");
            cloneItem.append("\"Status\":\"").append(status ? "YES" : "NO").append("\"");
            cloneItem.append("},");
         }

         String cloneItemString = cloneItem.toString();
         if (cloneItemString.length() > 0) {
            cloneItemString = cloneItemString.substring(0, cloneItemString.length() - 1);
            cloneItemBuilder.append(cloneItemString);
         }
      }

      cloneItemBuilder.append("]}");
      if (null != setting) {
         setting.setCloneItemStatus(cloneItemBuilder.toString());
         settingManager.save(setting);
      }

      LOG.info("setting:{} updateCloneItemStatus:{} success", setting == null ? "" : setting.getId(), cloneItemBuilder);
   }

   private void processChannel(String itemValues) {
      Gson gson = new Gson();
      SettingChannelBean setting = gson.fromJson(itemValues, SettingChannelBean.class);
      if ("v4".equalsIgnoreCase(setting.getChannelVersion())) {
         TvContents channelMap = this.getListOfV4Channel(itemValues);
         this.procesV4Channel(channelMap);
      } else if ("v5".equalsIgnoreCase(setting.getChannelVersion())) {
         com.tpvision.smartinstall.xml.channel.v5.TvContents channelMap = this.getListOfV5Channel(itemValues);
         this.procesV5Channel(channelMap);
      }
   }

   public void processFirmware(UpgSetting upgSetting) throws IOException {
      LOG.info("process Firmware,platform={}", upgSetting.getPlatform());

      try {
         this.preProcessChecksES("");
      } catch (IOException e) {
         LOG.error(e.getMessage());
      }

      String contextDownloadPath = CommonConstants.servletContextPath + "/Profile/UPG/" + upgSetting.getId();
      java.io.File targetUpg = new java.io.File(contextDownloadPath + "/MainFirmware.upg");
      if (!targetUpg.exists()) {
         String upgSrc = CommonConstants.UPLOADED_UPG_LOCATION + upgSetting.getName();
         java.io.File upgFile = new java.io.File(upgSrc + java.io.File.separator + "AutoRun.upg");
         FileUtils.copyFile(upgFile, targetUpg);
      }
   }

   private void processThemeTv(String settingProcessPath) {
      String path = settingProcessPath + "ThemeTV" + java.io.File.separator;
      java.io.File themeDir = new java.io.File(path);
      if (themeDir.exists()) {
         java.io.File destDir = new java.io.File(this.outputPath + java.io.File.separator + "ThemeTV" + java.io.File.separator);
         destDir.mkdirs();

         try {
            FileUtils.copyDirectory(themeDir, destDir);
         } catch (IOException e) {
            LOG.error(e.getMessage(), e);
         }
      }
   }

   private void processHotelInfo(String settingProcessPath, com.tpvision.smartinstall.dao.core.Setting settings, boolean isdownload) {
      String rootFolderName = PlatformUtils.getRootFolderName(this.platformId);
      String path = "";
      if (this.platformId.indexOf(rootFolderName) > -1) {
         path = settingProcessPath + "/SmartInfoImages/file_01.jpeg";
         String imgPath = settingProcessPath + "/tempSmartInfoImages/";
         this.processESHotelInfoMS2K14(settings.getId(), imgPath);
      } else {
         path = settingProcessPath + "/Wallpaper/HotelInfo.jpg";
      }

      java.io.File hotelInfoFile = new java.io.File(path);
      java.io.File destDir = null;
      if (hotelInfoFile.exists()) {
         destDir = new java.io.File(this.outputPath + "/Wallpaper/");
         if (this.platformId.indexOf(rootFolderName) > -1) {
            destDir = new java.io.File(this.outputPath + "/SmartInfoImages/");
         }

         if (!destDir.exists()) {
            destDir.mkdirs();
         }

         try {
            if (!hotelInfoFile.isDirectory()) {
               FileUtils.copyFileToDirectory(hotelInfoFile, destDir);
            }
         } catch (IOException e) {
            LOG.error(e.getMessage(), e);
         }
      }
   }

   private void processSmartPin(String settingProcessPath) {
      String path = settingProcessPath + "/SmartPin";
      java.io.File smartPin = new java.io.File(path);
      if (smartPin.exists()) {
         java.io.File destDir = new java.io.File(this.outputPath + java.io.File.separator + "SmartPin/");
         if (!destDir.exists()) {
            destDir.mkdirs();
         }

         try {
            FileUtils.copyDirectory(smartPin, destDir);
         } catch (IOException e) {
            LOG.error(e.getMessage(), e);
         }
      }
   }

   private void processESHotelInfoMS2K14(int settingName, String imgPath) {
      List<String> imageList = this.getESImageList(settingName);
      if (!imageList.isEmpty()) {
         java.io.File dir = new java.io.File(imgPath);
         if (dir.exists()) {
            java.io.File[] ff = dir.listFiles();

            for (java.io.File f : ff) {
               try {
                  Files.delete(f.toPath());
               } catch (IOException e) {
                  LOG.error(e.getMessage(), e);
               }
            }

            int counter = 1;
            java.io.File destFile = null;

            for (String imageFileName : imageList) {
               java.io.File fileToCopy = new java.io.File(CommonConstants.HOTEL_INFO_ES_LOCATION + imageFileName);
               if (counter < 10) {
                  destFile = new java.io.File(dir + "/file_0" + counter + ".jpeg");
               } else if (counter <= 30) {
                  destFile = new java.io.File(dir + "/file_" + counter + ".jpeg");
               }

               counter++;

               try {
                  FileUtils.copyFile(fileToCopy, destFile);
               } catch (IOException e) {
                  LOG.error(e.getMessage(), e);
               }
            }
         }
      }
   }

   private List<String> getESImageList(int settingName) {
      List<String> result = new ArrayList<>();
      SmartinfoSettingManager sismgr = JpaManager.getSmartinfoSettingManager();

      try {
         List<SmartinfoSetting> settings = sismgr.findSmartinfoSettingBySettingId(settingName);
         if (!settings.isEmpty()) {
            int smartuiId = settings.get(0).getSmartuiId();
            SmartuiManager smartUIMgr = JpaManager.getSmartuiManager();
            Smartui smartui = smartUIMgr.loadByKey(smartuiId);
            if (null != smartui) {
               Gson gson = new Gson();
               if (smartui.getValue() != null) {
                  String json = IOUtils.toString(new ByteArrayInputStream(smartui.getValue()), "UTF-8");
                  HotelInfo hotelInfo = gson.fromJson(json, HotelInfo.class);
                  result = hotelInfo.getHotelinfo();
               }
            }
         }
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }

      return result;
   }

   public void processUpgCreation(Map<String, String> parameters) throws IOException {
      long startTime = System.currentTimeMillis();
      if ("TPN141HE_CloneData".equalsIgnoreCase(this.platformId) || "TPN142HE_CloneData".equalsIgnoreCase(this.platformId)) {
         this.processMS2K14(parameters);
      } else if ("TPN161HE_CloneData".equalsIgnoreCase(this.platformId)) {
         String hasPlayOut = parameters.get("output");
         this.process2K16ES(hasPlayOut);
      } else {
         String hasPlayOut = parameters.get("output");
         int clondId = TpvStringUtils.tryParseInt(parameters.get("id"), -1);
         String selectCloneType = parameters.get("select_clone_type");
         if (null == selectCloneType || "".equals(selectCloneType)) {
            selectCloneType = "Clone";
         }

         this.process2K16MS(hasPlayOut, clondId, selectCloneType);
      }

      long endTime = System.currentTimeMillis();
      long totalTime = endTime - startTime;
      LOG.info("processUpgCreation finished,used {} ms", totalTime);
   }

   public void processTSCreation(Map<String, String> parameters) {
      long startTime = System.currentTimeMillis();
      LOG.info("#### processTSCreation");
      this.cpCatalog(parameters);
      ConfigurationGenerator.generate4KTransmission(this.platformId);

      try {
         this.processUpgCreation(parameters);
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }

      long endTime = System.currentTimeMillis();
      long totalTime = endTime - startTime;
      LOG.info("processTSCreation finished,used {} ms", totalTime);
   }

   private void cpCatalog(Map<String, String> parameters) {
      String versionID = parameters.get("vid");
      if (null == versionID) {
         versionID = "";
      }

      parameters.put("ES2K12_PRESENT", "TRUE");
      parameters.put("PLATFROM_ID_PRESENT", this.platformId);

      try {
         versionFolder = Utils.getFolder(versionID);
      } catch (NumberFormatException e) {
         LOG.error(e.getMessage(), e);
      }

      PSGCatalogGenerator.generateCatalog(this.platformId, versionFolder, this.playOutOptions(parameters));
      ConfigurationGenerator.generate(parameters);
   }

   private HashMap<String, String> playOutOptions(Map<String, String> parameters) {
      HashMap<String, String> hash = new HashMap<>();
      hash.put("hasPlayOut", parameters.get("output"));
      hash.put("hasAll", parameters.get("rfall"));
      hash.put("hasThemeTV", parameters.get("rfth"));
      hash.put("hasWelcomeLogo", parameters.get("rfwl"));
      hash.put("hasSettings", parameters.get("rfse"));
      return hash;
   }

   private void processMS2K14(Map<String, String> parameters) {
      java.io.File inputFile = new java.io.File(CommonConstants.RF_PLAY_BACK_INPUT_LOCATION + java.io.File.separator + this.platformId + java.io.File.separator);
      this.copy2K14ScfgFile();
      if (this.iPrRFMode.equalsIgnoreCase("RF")) {
         this.creatMS2K14Upg(inputFile, parameters);
      } else {
         String id = parameters.get("id");
         this.creatMS2K14IP(new java.io.File(this.outputPath), Integer.parseInt(id));
      }

      FileUtils.deleteQuietly(inputFile);
      this.clean2K14UPGCreateTempFiles();
   }

   private void process2K16MS(String hasPlayOut, int cloneId, String selectCloneType) throws IOException {
      long startTime = System.currentTimeMillis();
      java.io.File inputFile = new java.io.File(this.outputPath);
      if ("RF".equalsIgnoreCase(this.iPrRFMode)) {
         this.creat2K16Upg(inputFile, hasPlayOut);
      } else {
         this.create2K16IPPackage(cloneId, selectCloneType);
      }

      java.io.File outputFile = new java.io.File(
         CommonConstants.RF_PLAY_BACK_OUTPUT_LOCATION + java.io.File.separator + this.platformId + java.io.File.separator
      );
      if (!outputFile.exists()) {
         outputFile.mkdirs();
      }

      long endTime = System.currentTimeMillis();
      long totalTime = endTime - startTime;
      LOG.info("process2K16MS finished,used {} ms", totalTime);
   }

   private void process2K16ES(String hasPlayOut) {
      long startTime = System.currentTimeMillis();
      java.io.File inputFile = new java.io.File(CommonConstants.RF_PLAY_BACK_INPUT_LOCATION + java.io.File.separator + this.platformId + java.io.File.separator);
      java.io.File outputFile = new java.io.File(
         CommonConstants.RF_PLAY_BACK_OUTPUT_LOCATION + java.io.File.separator + this.platformId + java.io.File.separator
      );
      if (this.iPrRFMode.equalsIgnoreCase("RF")) {
         this.creat2K16UpgES(inputFile, hasPlayOut);
      }

      if (!outputFile.exists()) {
         outputFile.mkdirs();
      }

      long endTime = System.currentTimeMillis();
      long totalTime = endTime - startTime;
      LOG.info("process2K16ES finished,used {} ms", totalTime);
   }

   private void create2K16IPPackage(int cloneId, String selectCloneType) {
      if (cloneId <= 0) {
         LOG.warn("cloneid is null or empty:{}", cloneId);
      } else {
         CloneItemInfo info = CloneItemUtils.getCloneItemInfo(selectCloneType, cloneId);
         if (info.getItemType() != CloneItemType.Firmware) {
            java.io.File clonePathFile = new java.io.File(info.getCachedPath());
            String destPath = clonePathFile.getAbsolutePath() + java.io.File.separator;
            if (!clonePathFile.exists()) {
               clonePathFile.mkdirs();
            }

            this.zipClonePacketAccordingType(cloneId, destPath, selectCloneType);
            this.setClonePath(info.getClonePath());
            LOG.info("files generated at: {}", destPath);
         }
      }
   }

   private void creatMS2K14IP(java.io.File inputFile, int cloneId) {
      String tmpClonePath = "Clone/" + cloneId + "-" + UUID.randomUUID() + "/";
      java.io.File destDir = new java.io.File(CommonConstants.servletContextPath + "/Profile/Clone/" + tmpClonePath);
      destDir.mkdirs();
      java.io.File esUpgInput = new java.io.File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT);

      try {
         FileUtils.copyDirectory(inputFile, esUpgInput);
      } catch (Exception ex) {
         LOG.error(ex.getMessage(), ex);
      }

      Map<String, CommandLine> list = new LinkedHashMap<>();
      String rootFolderName = PlatformUtils.getRootFolderName(this.platformId);
      boolean isSSBBinFileExist = new java.io.File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_SSB.BIN").exists();
      boolean isSSBBinIdentifierExist = new java.io.File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_SSB.xml").exists();
      if (isSSBBinFileExist || isSSBBinIdentifierExist) {
         CommandLine cmd1 = this.get2K14DwpackCmd();
         if (isSSBBinFileExist) {
            cmd1.addArgument("1");
            cmd1.addArgument(rootFolderName + "_SSB.BIN");
         }

         if (isSSBBinIdentifierExist) {
            cmd1.addArgument("3");
            cmd1.addArgument(rootFolderName + "_SSB.xml");
         }

         list.put("TVSettings", cmd1);
      }

      boolean isChannelFileExist = new java.io.File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_CHTB.BIN").exists();
      boolean isChannelIdentiferExist = new java.io.File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_CHTB.xml").exists();
      if (isChannelFileExist || isChannelIdentiferExist) {
         CommandLine cmd2 = this.get2K14DwpackCmd();
         if (isChannelFileExist) {
            cmd2.addArgument("2");
            cmd2.addArgument(rootFolderName + "_CHTB.BIN");
         }

         if (isChannelIdentiferExist) {
            cmd2.addArgument("4");
            cmd2.addArgument(rootFolderName + "_CHTB.xml");
         }

         list.put("TVChannelList", cmd2);
      }

      if (new java.io.File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_WelcomeLogo.png").exists()) {
         CommandLine cmd3 = this.get2K14DwpackCmd();
         cmd3.addArgument("5");
         cmd3.addArgument(rootFolderName + "_WelcomeLogo.png");
         list.put("WelcomeLogo", cmd3);
      }

      if (new java.io.File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_SmartInfoImages.zip").exists()) {
         CommandLine cmd4 = this.get2K14DwpackCmd();
         cmd4.addArgument("6");
         cmd4.addArgument(rootFolderName + "_SmartInfoImages.zip");
         list.put("SmartInfoImages", cmd4);
      }

      if (new java.io.File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_SmartInfoPages.zip").exists()) {
         CommandLine cmd5 = this.get2K14DwpackCmd();
         cmd5.addArgument("7");
         cmd5.addArgument(rootFolderName + "_SmartInfoPages.zip");
         list.put("SmartInfoPages", cmd5);
      }

      if (new java.io.File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_SystemUIBackup.zip").exists()) {
         CommandLine cmd6 = this.get2K14DwpackCmd();
         cmd6.addArgument("8");
         cmd6.addArgument(rootFolderName + "_SystemUIBackup.zip");
         list.put("CustomDashboardFallback", cmd6);
      }

      if (new java.io.File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_MiscSettings.zip").exists()) {
         CommandLine cmd7 = this.get2K14DwpackCmd();
         cmd7.addArgument("9");
         cmd7.addArgument(rootFolderName + "_MiscSettings.zip");
         list.put("MiscSettings", cmd7);
      }

      if (new java.io.File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_VSecureKey.zip").exists()) {
         CommandLine cmd8 = this.get2K14DwpackCmd();
         cmd8.addArgument("10");
         cmd8.addArgument(rootFolderName + "_VSecureKey.zip");
         list.put("VSecureKey", cmd8);
      }

      if (new java.io.File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_CombineMedia.zip").exists()) {
         CommandLine cmd9 = this.get2K14DwpackCmd();
         cmd9.addArgument("11");
         cmd9.addArgument(rootFolderName + "_CombineMedia.zip");
         list.put("CombineMedia", cmd9);
      }

      for (Entry<String, CommandLine> mapEntry : list.entrySet()) {
         LOG.debug("generate item:{} for 2k14", mapEntry.getKey());
         DefaultExecutor executor = new DefaultExecutor();
         int[] values = new int[]{0, 1};
         executor.setExitValues(values);
         executor.setWorkingDirectory(new java.io.File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT));
         ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
         PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
         executor.setStreamHandler(streamHandler);

         try {
            executor.execute(mapEntry.getValue());
         } catch (Exception e) {
            LOG.error(e.getMessage() + ":" + mapEntry.getKey(), e);
            continue;
         }

         java.io.File source = new java.io.File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + "Autorun.upg");
         java.io.File destination = new java.io.File(destDir + "/" + mapEntry.getKey() + ".upg");
         if (source.exists()) {
            try {
               FileUtils.copyFile(source, destination);
            } catch (IOException e) {
               LOG.error(e.getMessage(), e);
            }
         }

         String[] str = mapEntry.getValue().toStrings();
         String[] part = str[str.length - 1].split("\\.");
         String name = part[0] + "_Identifier.txt";
         source = new java.io.File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + name);
         destination = new java.io.File(destDir + java.io.File.separator + name);

         try {
            if (null != source && source.exists()) {
               FileUtils.copyFile(source, destination);
            }
         } catch (IOException e) {
            LOG.error(e.getMessage(), e);
         }
      }

      java.io.File source = new java.io.File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_SSB_Identifier.txt");
      java.io.File destFile = new java.io.File(destDir + "/identifier.txt");

      try {
         FileUtils.copyFile(source, destFile);
      } catch (IOException e) {
         LOG.warn(e.getMessage(), e);
      }

      this.setClonePath(tmpClonePath);
      LOG.info("files generated {} ", destDir);
   }

   private CommandLine get2K14DwpackCmd() {
      CommandLine cmd = CommandLine.parse("cmd /c HTV_DWPack_1401.exe");
      cmd.addArgument("0");
      cmd.addArgument("PHILIPS_2K14_EU_HTV");
      return cmd;
   }

   private void clean2K14UPGCreateTempFiles() {
      List<String> es = new ArrayList<>();
      es.add("HTV_DWPack_1401.exe");
      es.add("GenerateAutorun.bat");
      es.add("libeay32.dll");
      es.add("scfg.xml");
      es.add("Key");
      es.add("Tool_backup");
      es.add("7-Zip");
      es.add("Configuration");
      java.io.File inputPath = new java.io.File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT);

      for (java.io.File f : inputPath.listFiles()) {
         if (!es.contains(f.getName())) {
            FileUtils.deleteQuietly(f);
         }
      }
   }

   private void creatMS2K14Upg(java.io.File inputFile, Map<String, String> parameters) {
      java.io.File esUpgInput = new java.io.File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT);

      try {
         FileUtils.copyDirectory(inputFile, esUpgInput);
      } catch (Exception ex) {
         LOG.error(ex.getMessage(), ex);
      }

      CommandLine cmdLine = CommandLine.parse("cmd /c HTV_DWPack_1401.exe");
      UpgCreationCommandArgument uca = UpgCreator.getCommandArument(parameters);
      cmdLine = new UpgCreator().build2K14MSCmd(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT, uca, cmdLine, this.platformId);
      DefaultExecutor executor = new DefaultExecutor();
      int[] values = new int[]{0, 1};
      executor.setExitValues(values);
      executor.setWorkingDirectory(new java.io.File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT));
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
      executor.setStreamHandler(streamHandler);

      try {
         executor.execute(cmdLine);
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         LOG.error("Error creating UPG");
         LOG.info(new String(outputStream.toByteArray()));
         return;
      }

      java.io.File rfEsUpgFile = new java.io.File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + "Autorun.upg");
      java.io.File rfEsUpgDestinationDir = null;
      java.io.File rfEsUpgFileInDestinationDir = null;
      java.io.File nameChangedUpgFile = null;
      if (this.platformId.contains("TPN141HE")) {
         rfEsUpgDestinationDir = new java.io.File(CommonConstants.RF_PLAY_BACK_EXE_INPUT_LOC_MS_2K14_INPUT);
         rfEsUpgFileInDestinationDir = new java.io.File(CommonConstants.RF_PLAY_BACK_EXE_INPUT_LOC_MS_2K14_INPUT + "Autorun.upg");
         nameChangedUpgFile = new java.io.File(CommonConstants.RF_PLAY_BACK_EXE_INPUT_LOC_MS_2K14_INPUT + "Autorun_RF_Cloning.upg");
      } else if (this.platformId.contains("TPN142HE")) {
         rfEsUpgDestinationDir = new java.io.File(CommonConstants.RF_PLAY_BACK_EXE_INPUT_LOC_ES_2K14_INPUT);
         rfEsUpgFileInDestinationDir = new java.io.File(CommonConstants.RF_PLAY_BACK_EXE_INPUT_LOC_ES_2K14_INPUT + "Autorun.upg");
         nameChangedUpgFile = new java.io.File(CommonConstants.RF_PLAY_BACK_EXE_INPUT_LOC_ES_2K14_INPUT + "Autorun_RF_Cloning.upg");
      }

      try {
         FileUtils.moveFileToDirectory(rfEsUpgFile, rfEsUpgDestinationDir, true);
         if (null != nameChangedUpgFile && nameChangedUpgFile.exists()) {
            FileUtils.forceDelete(nameChangedUpgFile);
         }

         FileUtils.moveFile(rfEsUpgFileInDestinationDir, nameChangedUpgFile);
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }
   }

   private void creat2K16UpgES(java.io.File inputFile, String hasPlayOut) {
      TpvFileUtils.clearFiles(CommonConstants.ES2K16_UPG_CREATOR_LOCATION_INPUT);
      String upgInputFilePath = CommonConstants.ES2K16_UPG_CREATOR_LOCATION_INPUT + "MasterCloneData";
      String generate2K16UpgPath = CommonConstants.RF_PLAY_BACK_EXE_INPUT_LOC_ES_2K16_INPUT;
      java.io.File esUpgInput = new java.io.File(upgInputFilePath);

      try {
         if (esUpgInput.exists()) {
            FileUtils.forceDelete(esUpgInput);
         }
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }

      esUpgInput.mkdirs();

      try {
         FileUtils.copyDirectory(inputFile, esUpgInput);
      } catch (Exception ex) {
         LOG.error(ex.getMessage(), ex);
      }

      java.io.File destDir = new java.io.File(generate2K16UpgPath);

      try {
         if (destDir.exists()) {
            FileUtils.forceDelete(destDir);
         }
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }

      destDir.mkdirs();
      java.io.File[] fileList = esUpgInput.listFiles();
      ZipCommonUtils.zipFiles(Arrays.asList(fileList), upgInputFilePath + ".zip");
      java.io.File source = new java.io.File(upgInputFilePath + ".zip");
      java.io.File destination = new java.io.File(destDir + "/MasterCloneData.zip");

      try {
         destination.createNewFile();
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }

      try {
         FileUtils.copyFile(source, destination);
      } catch (IOException e1) {
         LOG.error(e1.getMessage(), e1);
      }

      LOG.info("Play_RF_2K16(3011 ES) files generated {}", destDir);
   }

   private void creat2K16Upg(java.io.File inputFile, String hasPlayOut) throws IOException {
      String upgInputFilePath = CommonConstants.MS2K15_UPG_CREATOR_LOCATION_INPUT;
      String generate2K16UpgPath = null;
      String outputPath = null;
      if (this.platformId.contains("TPM1531HE")) {
         generate2K16UpgPath = CommonConstants.RF_PLAY_BACK_EXE_INPUT_LOC_SS_2K16_INPUT;
         outputPath = CommonConstants.SS2K16_UPG_CREATOR_LOCATION_OUTPUT;
      } else if (this.platformId.contains("TPM1532HE")) {
         generate2K16UpgPath = CommonConstants.RF_PLAY_BACK_EXE_INPUT_LOC_MS_2K15_INPUT;
         outputPath = CommonConstants.MS2K15_UPG_CREATOR_LOCATION_OUTPUT;
      }

      TpvFileUtils.clearFiles(upgInputFilePath);
      TpvFileUtils.clearFiles(generate2K16UpgPath);
      if (hasPlayOut.contains("C")) {
         this.zipAllToPath(generate2K16UpgPath);
         ZipCommonUtils.gen7ZipForOAD(generate2K16UpgPath, "Autorun_RF_Cloning.upg", generate2K16UpgPath, "*.zip");
      }

      if (hasPlayOut.contains("F")) {
         String sourceUpgPath = outputPath + versionFolder + "/";
         java.io.File oadUpg = new java.io.File(sourceUpgPath + "/oad.upg");
         java.io.File oadUpgDestDir = new java.io.File(generate2K16UpgPath + "oad.upg");
         if (oadUpg.exists() && !oadUpg.isDirectory()) {
            FileUtils.copyFileToDirectory(oadUpg, new java.io.File(generate2K16UpgPath));
         } else {
            ZipCommonUtils.gen7ZipForOAD(generate2K16UpgPath, "oad.upg", sourceUpgPath, "*.upg");
            FileUtils.copyFileToDirectory(oadUpgDestDir, new java.io.File(sourceUpgPath));
         }

         ZipCommonUtils.gen7ZipForOAD(generate2K16UpgPath, "oad.upg", generate2K16UpgPath, "*.zip");
      }

      LOG.info("Play_RF(2K16) files generated {}", generate2K16UpgPath);
   }

   private void procesV4Channel(TvContents listOfChannel) {
      if (null != listOfChannel) {
         String fileName = this.outputPath + this.platform.getChannel().getFolderName() + this.platform.getChannel().getFileName();

         try {
            JAXBContext context = JAXBContext.newInstance("com.tpvision.smartinstall.xml.channel.v4");
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
            StringWriter sw = new StringWriter();
            marshaller.marshal(listOfChannel, sw);
            this.writeXML(fileName, sw);
         } catch (Exception e) {
            LOG.error(e.getMessage(), e);
         }
      }
   }

   private void procesV5Channel(com.tpvision.smartinstall.xml.channel.v5.TvContents listOfChannel) {
      if (null != listOfChannel) {
         String fileName = this.outputPath + this.platform.getChannel().getFolderName();
         String[] folders = this.platform.getChannel().getFileName().split("/");
         java.io.File rootFolder = new java.io.File(fileName + folders[0]);
         if (!rootFolder.exists()) {
            rootFolder.mkdirs();
         }

         fileName = fileName + this.platform.getChannel().getFileName();

         try {
            JAXBContext context = JAXBContext.newInstance("com.tpvision.smartinstall.xml.channel.v5");
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
            StringWriter sw = new StringWriter();
            marshaller.marshal(listOfChannel, sw);
            this.writeXML(fileName, sw);
         } catch (Exception e) {
            LOG.error(e.getMessage(), e);
         }
      }
   }

   private void writeXML(String fileName, StringWriter sw) {
      String str = sw.toString().replace("&amp;apos;", "&apos;");

      try {
         java.io.File f = new java.io.File(fileName);
         if (!f.exists()) {
            f.createNewFile();
         }

         try (FileOutputStream fos = new FileOutputStream(f)) {
            Writer out = new OutputStreamWriter(fos, "UTF8");
            out.write(str);
            out.flush();
         }
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }
   }

   private TvContents getListOfV4Channel(String confs) {
      TvContents ret = new TvContents();
      Gson gson = new Gson();

      try {
         SettingChannelBean settings = gson.fromJson(confs, SettingChannelBean.class);
         if (null != settings && settings.getV4Channel() != null) {
            ret = settings.getV4Channel();
         }
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }

      return ret;
   }

   private com.tpvision.smartinstall.xml.channel.v5.TvContents getListOfV5Channel(String confs) {
      com.tpvision.smartinstall.xml.channel.v5.TvContents ret = new com.tpvision.smartinstall.xml.channel.v5.TvContents();
      Gson gson = new Gson();

      try {
         SettingChannelBean settings = gson.fromJson(confs, SettingChannelBean.class);
         if (null != settings && settings.getV5Channel() != null) {
            ret = settings.getV5Channel();
         }
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }

      return ret;
   }

   private void preProcessChecksES(String cleaned) throws IOException {
      ArrayList<String> ES = new ArrayList<>();
      ES.add("buh13_pack_rel.exe");
      ES.add("7-Zip");
      ES.add("libeay32.dll");
      ES.add("msvcr100.dll");
      ES.add("cmd.bat");
      ES.add("HTV_DWPack_1401.exe");
      ES.add("GenerateAutorun.bat");
      ES.add("Key");
      ES.add("Tool_backup");
      ES.add("Configuration");
      java.io.File inputPath = new java.io.File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT);
      if (inputPath.exists() && (null == cleaned || null != cleaned && !cleaned.equalsIgnoreCase("true"))) {
         for (java.io.File f : inputPath.listFiles()) {
            boolean itemFound = false;

            for (String item : ES) {
               if (f.getName().indexOf(item) > -1) {
                  itemFound = true;
                  break;
               }
            }

            if (!itemFound) {
               try {
                  if (f.isDirectory()) {
                     FileUtils.deleteDirectory(f);
                  } else {
                     FileUtils.deleteQuietly(f);
                  }
               } catch (Exception e) {
                  LOG.error(e.getMessage(), e);
               }
            }
         }
      }

      String[] inputfolderList = new String[]{
         CommonConstants.RF_PLAY_BACK_EXE_INPUT_LOC_MS_2K14_INPUT,
         CommonConstants.RF_PLAY_BACK_EXE_INPUT_LOC_MS_2K15_INPUT,
         CommonConstants.RF_PLAY_BACK_EXE_INPUT_LOC_ES_2K14_INPUT,
         CommonConstants.RF_PLAY_BACK_EXE_INPUT_LOC_ES_2K16_INPUT
      };

      for (int i = 0; i < inputfolderList.length; i++) {
         java.io.File f = new java.io.File(inputfolderList[i]);
         if (f.exists()) {
            try {
               FileUtils.deleteDirectory(f);
            } catch (Exception e) {
               LOG.error(e.getMessage(), e);
            }
         }
      }

      this.copy2K14ScfgFile();
   }

   private void copy2K14ScfgFile() {
      java.io.File srcFile = null;
      if (this.platformId.contains("TPN141HE")) {
         srcFile = new java.io.File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + "Configuration/PHILIPS_2K14_EU_HTV/scfg.xml");
      } else if (this.platformId.contains("TPN142HE")) {
         srcFile = new java.io.File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + "Configuration/PHILIPS_2K14_EU_ES_HTV/scfg.xml");
      }

      try {
         if (null != srcFile && srcFile.exists()) {
            FileUtils.copyFile(srcFile, new java.io.File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + "/scfg.xml"));
         }
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }
   }

   private void cleanAssemblyDir() {
      if (this.outputPath != null) {
         java.io.File f = new java.io.File(this.outputPath);
         if (f.getName().startsWith("CLONE_")) {
            LOG.info("clone package is cached:{}", f.getName());
         } else {
            FileUtils.deleteQuietly(f);
         }
      }
   }

   private void makeDirs(String finalPath) {
      java.io.File f = new java.io.File(finalPath);
      f.mkdirs();
   }

   private void processUnchangedFiles(String settingProcessPath) {
      for (File f : this.platform.getUnchangedFiles().getFile()) {
         String fromFilePath = settingProcessPath + f.getPath();
         java.io.File inputFile = new java.io.File(fromFilePath);
         if (!inputFile.exists()) {
            fromFilePath = CommonConstants.RESOURCE_LOCATION + this.platformId + java.io.File.separator + f.getPath();
            inputFile = new java.io.File(fromFilePath);
         }

         if (inputFile.exists()) {
            String toFilePath = this.outputPath + java.io.File.separator + f.getPath();
            java.io.File destFile = new java.io.File(toFilePath);

            try {
               destFile.getParentFile().mkdirs();
               FileUtils.copyFile(inputFile, destFile);
            } catch (IOException e) {
               LOG.error(e.getMessage(), e);
            }
         }
      }
   }

   private String getIdentifierFilePath(String itemName) {
      List<File> crcFile = this.platform.getCrcFiles().getFile();
      String itemName2 = PlatformUtils.getIdentifierName(this.platformId, itemName);

      for (File f : crcFile) {
         String path = f.getPath();
         if (path.indexOf(itemName) >= 0 || path.indexOf(itemName2) >= 0) {
            return this.outputPath + path;
         }
      }

      String identifier = this.outputPath + itemName + java.io.File.separator + itemName + "_Identifier.txt";
      LOG.warn("identifier not config in config.xml,using default value:{}", identifier);
      return identifier;
   }

   private void processIdentifier(String itemName, String dateStr) {
      LOG.info("process identifier for:{} ", itemName);
      java.io.File fileIdentifier = new java.io.File(this.getIdentifierFilePath(itemName));
      String identifier = dateStr;
      if (dateStr == null) {
         identifier = TpvDateUtils.getCurrentIndentifierFormatTime();
      }

      try {
         FileUtils.writeStringToFile(fileIdentifier, identifier, StandardCharsets.UTF_8);
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }
   }

   private void processCrc(String preProcessPath, com.tpvision.smartinstall.dao.core.Setting setting, Date date) {
      List<File> crcFile = this.platform.getCrcFiles().getFile();
      Iterator var5 = crcFile.iterator();

      while (true) {
         File f;
         String cloneSubFolderName;
         while (true) {
            if (!var5.hasNext()) {
               return;
            }

            f = (File)var5.next();
            cloneSubFolderName = f.getPath().substring(0, f.getPath().indexOf(f.getName()));
            if (!"SmartInfoBrowser/".equalsIgnoreCase(cloneSubFolderName) && !"SmartInfoPages/".equalsIgnoreCase(cloneSubFolderName)) {
               if ("".equalsIgnoreCase(cloneSubFolderName)) {
                  break;
               }

               String srcFolder = preProcessPath + java.io.File.separator + cloneSubFolderName;
               if (new java.io.File(srcFolder).exists()) {
                  break;
               }
            }
         }

         String srcPath = preProcessPath + f.getPath();
         java.io.File processIdentifierFile = new java.io.File(srcPath);
         if (!processIdentifierFile.exists()) {
            try {
               processIdentifierFile.createNewFile();
            } catch (IOException e) {
               LOG.error(e.getMessage(), e);
            }
         }

         try (FileOutputStream fos = new FileOutputStream(processIdentifierFile)) {
            if (null == date) {
               date = new Date();
            }

            if (!f.getPath().contains("ChannelList") && !f.getPath().contains("CHTB")) {
               IOUtils.write(TpvDateUtils.getIdentifierFormatTime(date), fos, StandardCharsets.UTF_8);
            } else {
               String cpLastEdit = "0/0/0:0:0";
               ChannelPackageManager cpm = JpaManager.getChannelPackageManager();
               ChannelPackage cp = cpm.loadByKey(setting.getChannelPackageId());
               if (null != cp) {
                  cpLastEdit = cp.getLastEdit();
               }

               IOUtils.write(cpLastEdit, fos, StandardCharsets.UTF_8);
            }
         } catch (IOException e) {
            LOG.error(e.getMessage(), e);
         }

         String assemblyPath = "";
         if ("".equalsIgnoreCase(cloneSubFolderName)) {
            assemblyPath = this.outputPath + java.io.File.separator;
         } else {
            assemblyPath = this.outputPath + java.io.File.separator + cloneSubFolderName + java.io.File.separator;
         }

         java.io.File assemblySubFile = new java.io.File(assemblyPath);
         if (!assemblySubFile.exists()) {
            assemblySubFile.mkdirs();
         }

         String assemblyIdentifierPath = assemblyPath + f.getName();

         try {
            java.io.File assemblyIdentifierFile = new java.io.File(assemblyIdentifierPath);
            if (!assemblyIdentifierFile.exists()) {
               assemblyIdentifierFile.createNewFile();
            }

            FileUtils.copyFile(processIdentifierFile, assemblyIdentifierFile);
         } catch (IOException e) {
            LOG.error(e.getMessage(), e);
         }
      }
   }

   private void processSettings(List<Setting> listOfSettings) {
      LOG.info("SettingCreator.java -> processTVSettings -> platform {} ", this.platformId);
      if (this.platform == null) {
         LOG.info("SettingCreator.java -> processSettings -> None platform is available ! ");
      } else {
         if (this.platform.getCloneRootFolderName().startsWith("TPN141")) {
            this.processSettingMS2K14(listOfSettings);
         } else if (this.platform.getCloneRootFolderName().startsWith("TPN142")) {
            this.processSettingMS2K14ForTpn142(listOfSettings);
         } else {
            this.processSetting2K16(listOfSettings);
         }

         List<File> settingFiles = this.platform.getSettingFiles().getFile();
         List<Setting> configuredSettings = this.platform.getSettings().getSetting();
         Map<File, List<Setting>> fileToSettingMap = new HashMap<>();

         for (File f : settingFiles) {
            fileToSettingMap.put(f, new ArrayList<>());
         }

         for (Setting s : listOfSettings) {
            if (this.hasSettingConfigured(s, configuredSettings)) {
               File fileKey = null;

               for (File fileForKey : settingFiles) {
                  if ("BdsLastStatus.txt".equalsIgnoreCase(fileForKey.getName())) {
                     fileToSettingMap.get(fileForKey).add(s);
                     break;
                  }

                  if (null != s && s.getRefFile().equalsIgnoreCase(fileForKey.getName())) {
                     fileKey = fileForKey;
                     if (this.platormHasSetting(s)) {
                        fileToSettingMap.get(fileKey).add(s);
                     }
                     break;
                  }
               }
            }
         }

         String rootPath = this.platform.getId();

         for (File key : fileToSettingMap.keySet()) {
            int lastIndex = key.getPath().indexOf(key.getName());
            if (lastIndex < 0) {
               lastIndex = 0;
            }

            String pathToAdd = key.getPath().substring(0, lastIndex);
            String path = rootPath + java.io.File.separator + pathToAdd;
            path = this.outputPath + key.getName();
            java.io.File file = new java.io.File(path);
            StringBuilder sbuf = new StringBuilder();
            if (key.getName().equalsIgnoreCase("BdsLastStatus.txt")) {
               sbuf.append("<swver>" + this.platform.getSwver() + "</swver>").append(IOUtils.LINE_SEPARATOR_WINDOWS);
            } else {
               sbuf.append("<swver>" + this.platform.getSwver() + "</swver>").append(IOUtils.LINE_SEPARATOR_UNIX);
            }

            for (Setting s : fileToSettingMap.get(key)) {
               String lastValue = s.getLastValue();
               if (null != lastValue && lastValue.trim().equalsIgnoreCase("off")) {
                  lastValue = "0";
               } else if (null != lastValue && lastValue.trim().equalsIgnoreCase("on")) {
                  lastValue = "1";
               } else if (null != lastValue && lastValue.trim().equals("")) {
                  lastValue = "0";
               } else if (null == lastValue) {
                  lastValue = "0";
               }

               if (key.getName().equalsIgnoreCase("BdsLastStatus.txt")) {
                  sbuf.append("<ItemID>" + s.getItem1() + "</ItemID>").append(IOUtils.LINE_SEPARATOR_WINDOWS);
               } else {
                  sbuf.append("<item>" + s.getItem() + "</item>").append(IOUtils.LINE_SEPARATOR_UNIX);
               }

               if (!key.getName().equalsIgnoreCase("BdsLastStatus.txt")) {
                  String xaddr = this.getPlatformXaddr(s);
                  sbuf.append("<Xaddr>" + xaddr + "</Xaddr>").append(IOUtils.LINE_SEPARATOR_UNIX);
               }

               if (key.getName().equalsIgnoreCase("BdsLastStatus.txt")) {
                  sbuf.append("<Value>" + s.getLastValue1() + "</Value>").append(IOUtils.LINE_SEPARATOR_WINDOWS);
               } else {
                  sbuf.append("<lastvalue>" + lastValue + "</lastvalue>").append(IOUtils.LINE_SEPARATOR_UNIX);
               }
            }

            try (FileOutputStream fos = new FileOutputStream(file)) {
               IOUtils.write(sbuf.toString(), fos, StandardCharsets.UTF_8);
            } catch (Exception e) {
               LOG.error(e.getMessage(), e);
            }
         }
      }
   }

   private String getPlatformXaddr(Setting s) {
      String ret = null;

      for (Setting setting : this.platform.getSettings().getSetting()) {
         if (setting.getItem().equalsIgnoreCase(s.getItem()) && setting.getPosition().equals(s.getPosition())) {
            ret = setting.getXaddr();
            break;
         }
      }

      return ret;
   }

   private void processSettingMS2K14ForTpn142(List<Setting> listOfSettings) {
      HotelModelSettingsForTpn142 hms = new HotelModelSettingsForTpn142();

      for (Setting s : listOfSettings) {
         if ("easylink".equalsIgnoreCase(s.getItem())) {
            s.setXaddr("MS2K14");
         }

         if ("MS2K14".equalsIgnoreCase(s.getXaddr())) {
            String sName = s.getItem();
            String value = s.getLastValue() != null ? s.getLastValue() : "";
            value = " " + value.trim() + " ";
            if (!value.equalsIgnoreCase(" null ") || !sName.equalsIgnoreCase("CloneMultiRC")) {
               if ("SwitchOnSrc".equalsIgnoreCase(sName)) {
                  hms.setSwitchOnSrc(value);
               } else if ("SwitchOnChn".equalsIgnoreCase(sName)) {
                  if (hms.getSwitchOnSrc().trim().equalsIgnoreCase("TV")) {
                     hms.setSwitchOnChn(value);
                  }
               } else if ("SwitchOnVol".equalsIgnoreCase(sName)) {
                  hms.setSwitchOnVol(value);
               } else if ("MaximumVol".equalsIgnoreCase(sName)) {
                  hms.setMaximumVol(value);
               } else if ("SwitchOnFeature".equalsIgnoreCase(sName)) {
                  hms.setSwitchOnFeature(value);
               } else if ("SwitchOnPicFmt".equalsIgnoreCase(sName)) {
                  hms.setSwitchOnPicFmt(value);
               } else if ("PowerOn".equalsIgnoreCase(sName)) {
                  hms.setPowerOn(value);
               } else if ("LowPowerStandby".equalsIgnoreCase(sName)) {
                  hms.setLowPowerStandby(value);
               } else if ("SmartPower".equalsIgnoreCase(sName)) {
                  hms.setSmartPower(value);
               } else if ("RebootEveryDay".equalsIgnoreCase(sName)) {
                  hms.setRebootEveryDay(value);
               } else if ("DisplayWelcomeMsg".equalsIgnoreCase(sName)) {
                  hms.setDisplayWelcomeMsg(value);
               } else if ("WelcomeMsgLine1".equalsIgnoreCase(sName)) {
                  hms.setWelcomeMsgLine1(value);
               } else if ("WelcomeMsgLine2".equalsIgnoreCase(sName)) {
                  hms.setWelcomeMsgLine2(value);
               } else if ("WelcomeMsgTimeOut".equalsIgnoreCase(sName)) {
                  hms.setWelcomeMsgTimeOut(value);
               } else if ("DisplayLogo".equalsIgnoreCase(sName)) {
                  hms.setDisplayLogo(value);
               } else if ("SmartInfo".equalsIgnoreCase(sName)) {
                  hms.setSmartInfo(value);
               } else if ("SmartInfoIconLabel".equalsIgnoreCase(sName)) {
                  hms.setSmartInfoIconLabel(value);
               } else if ("KBLock".equalsIgnoreCase(sName)) {
                  hms.setKbLock(value);
               } else if ("RCLock".equalsIgnoreCase(sName)) {
                  hms.setRcLock(value);
               } else if ("OSDDisplay".equalsIgnoreCase(sName)) {
                  hms.setOsdDisplay(value);
               } else if ("HighSecurity".equalsIgnoreCase(sName)) {
                  hms.setHighSecurity(value);
               } else if ("AutoScart".equalsIgnoreCase(sName)) {
                  hms.setAutoScart(value);
               } else if ("USBBreakIn".equalsIgnoreCase(sName)) {
                  hms.setUsbBreakIn(value);
               } else if ("EnableUSB".equalsIgnoreCase(sName)) {
                  hms.setEnableUSB(value);
               } else if ("SXPBaudRate".equalsIgnoreCase(sName)) {
                  hms.setSxpBaudRate(value);
               } else if ("EnableTeletext".equalsIgnoreCase(sName)) {
                  hms.setEnableTeletext(value);
               } else if ("EnableMHEG".equalsIgnoreCase(sName)) {
                  hms.setEnableMHEG(value);
               } else if ("EnableEPG".equalsIgnoreCase(sName)) {
                  hms.setEnableEPG(value);
               } else if ("EnableSubtitles".equalsIgnoreCase(sName)) {
                  hms.setEnableSubtitles(value);
               } else if ("SubtitleOnStartup".equalsIgnoreCase(sName)) {
                  hms.setSubtitleOnStartup(value);
               } else if ("BlueMute".equalsIgnoreCase(sName)) {
                  hms.setBlueMute(value);
               } else if ("EnableCISlot".equalsIgnoreCase(sName)) {
                  hms.setEnableCISlot(value);
               } else if ("ScrambledProgramOSD".equalsIgnoreCase(sName)) {
                  hms.setScrambledProgramOSD(value);
               } else if ("EasylinkBreakIn".equalsIgnoreCase(sName)) {
                  hms.setEasylinkBreakIn(value);
               } else if ("EasylinkControl".equalsIgnoreCase(sName)) {
                  hms.setEasylinkControl(value);
               } else if ("DigitTimeout".equalsIgnoreCase(sName)) {
                  hms.setDigitTimeout(value);
               } else if ("SelectableAV".equalsIgnoreCase(sName)) {
                  hms.setSelectableAV(value);
               } else if ("WatchTV".equalsIgnoreCase(sName)) {
                  hms.setWatchTV(value);
               } else if ("ExternalClk".equalsIgnoreCase(sName)) {
                  hms.setExternalClk(value);
               } else if ("ClkBrighDimlight".equalsIgnoreCase(sName)) {
                  hms.setClkBrighDimlight(value);
               } else if ("ClkBrighIntenselight".equalsIgnoreCase(sName)) {
                  hms.setClkBrighIntenselight(value);
               } else if ("ClkLightSensor".equalsIgnoreCase(sName)) {
                  hms.setClkLightSensor(value);
               } else if ("TimeDownload".equalsIgnoreCase(sName)) {
                  hms.setTimeDownload(value);
               } else if ("TimeSetting".equalsIgnoreCase(sName)) {
                  hms.setTimeSetting(value);
               } else if ("ClkDownloadProgram".equalsIgnoreCase(sName)) {
                  hms.setClkDownloadProgram(value);
               } else if ("ClkDownloadCountry".equalsIgnoreCase(sName)) {
                  hms.setClkDownloadCountry(value);
               } else if ("ClkTimeZone".equalsIgnoreCase(sName)) {
                  hms.setClkTimeZone(value);
               } else if ("DaylightSaving".equalsIgnoreCase(sName)) {
                  hms.setDaylightSaving(value);
               } else if ("ClkTimeOffset".equalsIgnoreCase(sName)) {
                  hms.setClkTimeOffset(value);
               } else if ("ReferenceDate".equalsIgnoreCase(sName)) {
                  hms.setReferenceDate(value);
               } else if ("ReferenceTime".equalsIgnoreCase(sName)) {
                  hms.setReferenceTime(value);
               } else if ("MainSpkrEnable".equalsIgnoreCase(sName)) {
                  hms.setMainSpkrEnable(value);
               } else if ("IndMainSpkrMute".equalsIgnoreCase(sName)) {
                  hms.setIndMainSpkrMute(value);
               } else if ("DefMainSpkrVol".equalsIgnoreCase(sName)) {
                  hms.setDefMainSpkrVol(value);
               } else if ("AutoChnUpdate".equalsIgnoreCase(sName)) {
                  hms.setAutoChnUpdate(value);
               } else if ("AutoSwUpdate".equalsIgnoreCase(sName)) {
                  hms.setAutoSwUpdate(value);
               } else if ("SkipScrambled".equalsIgnoreCase(sName)) {
                  hms.setSkipScrambled(value);
               } else if ("MultiRC".equalsIgnoreCase(sName)) {
                  hms.setMultiRC(value);
               } else if ("MyChoice".equalsIgnoreCase(sName)) {
                  hms.setMyChoice(value);
               } else if ("AskForPIN".equalsIgnoreCase(sName)) {
                  hms.setAskForPIN(value);
               } else if ("SmartPay".equalsIgnoreCase(sName)) {
                  hms.setSmartPay(value);
               } else if ("AV".equalsIgnoreCase(sName)) {
                  hms.setAV(value);
               } else if ("VsecOverRFEnable".equalsIgnoreCase(sName)) {
                  hms.setVsecOverRFEnable(value);
               } else if ("EraseKeyOption".equalsIgnoreCase(sName)) {
                  hms.setEraseKeyOption(value);
               } else if ("VsecFrequency".equalsIgnoreCase(sName)) {
                  hms.setVsecFrequency(value);
               } else if ("VsecMedium".equalsIgnoreCase(sName)) {
                  hms.setVsecMedium(value);
               } else if ("VsecSymbolRate".equalsIgnoreCase(sName)) {
                  hms.setVsecSymbolRate(value);
               } else if ("RFCLFrequency".equalsIgnoreCase(sName)) {
                  hms.setRfclFrequency(value);
               } else if ("RFCLMedium".equalsIgnoreCase(sName)) {
                  hms.setRfclMedium(value);
               } else if ("RFCLSymbolRate".equalsIgnoreCase(sName)) {
                  hms.setRfclSymbolRate(value);
               } else if ("UpgradeMode".equalsIgnoreCase(sName)) {
                  hms.setUpgradeMode(value);
               } else if ("AutoUpgrade".equalsIgnoreCase(sName)) {
                  hms.setAutoUpgrade(value);
               } else if ("InstallationMode".equalsIgnoreCase(sName)) {
                  hms.setInstallationMode(value);
               } else if ("CloneMultiRC".equalsIgnoreCase(sName)) {
                  hms.setCloneMultiRC(value);
               }
            }
         }
      }

      try {
         JAXBContext context = JAXBContext.newInstance(HotelModelSettingsForTpn142.class);
         java.io.File settingFile = this.generateSettingXMLFile(PlatformUtils.getRootFolderName(this.platformId) + "_SSB.xml", null);
         Marshaller marshaller = context.createMarshaller();
         marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
         marshaller.marshal(hms, settingFile);
      } catch (JAXBException e) {
         LOG.error(e.getMessage(), e);
      }
   }

   private void processSettingMS2K14(List<Setting> listOfSettings) {
      HotelModeSettings hms = new HotelModeSettings();

      for (Setting s : listOfSettings) {
         if ("easylink".equalsIgnoreCase(s.getItem())) {
            s.setXaddr("MS2K14");
         }

         if ("MS2K14".equalsIgnoreCase(s.getXaddr())) {
            String sName = s.getItem();
            String value = s.getLastValue() != null ? s.getLastValue() : "";
            value = " " + value.trim() + " ";
            if (!value.equalsIgnoreCase(" null ") || !sName.equalsIgnoreCase("CloneMultiRC")) {
               if ("Dashboard".equalsIgnoreCase(sName)) {
                  hms.setDashboard(value);
               } else if ("SwitchOnSrc".equalsIgnoreCase(sName)) {
                  hms.setSwitchOnSrc(value);
               } else if ("SwitchOnChn".equalsIgnoreCase(sName)) {
                  if (hms.getSwitchOnSrc().trim().equalsIgnoreCase("TV")) {
                     hms.setSwitchOnChn(value);
                  }
               } else if ("SwitchOnVol".equalsIgnoreCase(sName)) {
                  hms.setSwitchOnVol(value);
               } else if ("MaximumVol".equalsIgnoreCase(sName)) {
                  hms.setMaximumVol(value);
               } else if ("SwitchOnFeature".equalsIgnoreCase(sName)) {
                  hms.setSwitchOnFeature(value);
               } else if ("SwitchOnPicFmt".equalsIgnoreCase(sName)) {
                  hms.setSwitchOnPicFmt(value);
               } else if ("PowerOn".equalsIgnoreCase(sName)) {
                  hms.setPowerOn(value);
               } else if ("LowPowerStandby".equalsIgnoreCase(sName)) {
                  hms.setLowPowerStandby(value);
               } else if ("SmartPower".equalsIgnoreCase(sName)) {
                  hms.setSmartPower(value);
               } else if ("RebootEveryDay".equalsIgnoreCase(sName)) {
                  hms.setRebootEveryDay(value);
               } else if ("WakeOnLAN".equalsIgnoreCase(sName)) {
                  hms.setWakeOnLAN(value);
               } else if ("DisplayWelcomeMsg".equalsIgnoreCase(sName)) {
                  hms.setDisplayWelcomeMsg(value);
               } else if ("WelcomeMsgLine1".equalsIgnoreCase(sName)) {
                  hms.setWelcomeMsgLine1(value);
               } else if ("WelcomeMsgLine2".equalsIgnoreCase(sName)) {
                  hms.setWelcomeMsgLine2(value);
               } else if ("WelcomeMsgTimeOut".equalsIgnoreCase(sName)) {
                  hms.setWelcomeMsgTimeOut(value);
               } else if ("DisplayLogo".equalsIgnoreCase(sName)) {
                  hms.setDisplayLogo(value);
               } else if ("SmartInfo".equalsIgnoreCase(sName)) {
                  hms.setSmartInfo(value);
               } else if ("SmartInfoIconLabel".equalsIgnoreCase(sName)) {
                  hms.setSmartInfoIconLabel(value);
               } else if ("KBLock".equalsIgnoreCase(sName)) {
                  hms.setKBLock(value);
               } else if ("RCLock".equalsIgnoreCase(sName)) {
                  hms.setRCLock(value);
               } else if ("OSDDisplay".equalsIgnoreCase(sName)) {
                  hms.setOSDDisplay(value);
               } else if ("HighSecurity".equalsIgnoreCase(sName)) {
                  hms.setHighSecurity(value);
               } else if ("AutoScart".equalsIgnoreCase(sName)) {
                  hms.setAutoScart(value);
               } else if ("USBBreakIn".equalsIgnoreCase(sName)) {
                  hms.setUSBBreakIn(value);
               } else if ("EnableUSB".equalsIgnoreCase(sName)) {
                  hms.setEnableUSB(value);
               } else if ("SXPBaudRate".equalsIgnoreCase(sName)) {
                  hms.setSXPBaudRate(value);
               } else if ("EnableTeletext".equalsIgnoreCase(sName)) {
                  hms.setEnableTeletext(value);
               } else if ("EnableMHEG".equalsIgnoreCase(sName)) {
                  hms.setEnableMHEG(value);
               } else if ("EnableEPG".equalsIgnoreCase(sName)) {
                  hms.setEnableEPG(value);
               } else if ("EnableSubtitles".equalsIgnoreCase(sName)) {
                  hms.setEnableSubtitles(value);
               } else if ("SubtitleOnStartup".equalsIgnoreCase(sName)) {
                  hms.setSubtitleOnStartup(value);
               } else if ("BlueMute".equalsIgnoreCase(sName)) {
                  hms.setBlueMute(value);
               } else if ("EnableCISlot".equalsIgnoreCase(sName)) {
                  hms.setEnableCISlot(value);
               } else if ("WiFiCrossConnect".equalsIgnoreCase(sName)) {
                  hms.setWiFiCrossConnect(value);
               } else if ("WiFiMiraCast".equalsIgnoreCase(sName)) {
                  hms.setWiFiMiraCast(value);
               } else if ("DirectShare".equalsIgnoreCase(sName)) {
                  hms.setDirectShare(value);
               } else if ("ScrambledProgramOSD".equalsIgnoreCase(sName)) {
                  hms.setScrambledProgramOSD(value);
               } else if ("WiFiLostOSD".equalsIgnoreCase(sName)) {
                  hms.setWiFiLostOSD(value);
               } else if ("JointSpace".equalsIgnoreCase(sName)) {
                  hms.setJointSpace(value);
               } else if ("EasylinkBreakIn".equalsIgnoreCase(sName)) {
                  hms.setEasylinkBreakIn(value);
               } else if ("EasylinkControl".equalsIgnoreCase(sName)) {
                  hms.setEasylinkControl(value);
               } else if ("EnableSkype".equalsIgnoreCase(sName)) {
                  hms.setEnableSkype(value);
               } else if ("DigitTimeout".equalsIgnoreCase(sName)) {
                  hms.setDigitTimeout(value);
               } else if ("SelectableAV".equalsIgnoreCase(sName)) {
                  hms.setSelectableAV(value);
               } else if ("WatchTV".equalsIgnoreCase(sName)) {
                  hms.setWatchTV(value);
               } else if ("ExternalClk".equalsIgnoreCase(sName)) {
                  hms.setExternalClk(value);
               } else if ("ClkBrighDimlight".equalsIgnoreCase(sName)) {
                  hms.setClkBrighDimlight(value);
               } else if ("ClkBrighIntenselight".equalsIgnoreCase(sName)) {
                  hms.setClkBrighIntenselight(value);
               } else if ("ClkLightSensor".equalsIgnoreCase(sName)) {
                  hms.setClkLightSensor(value);
               } else if ("TimeDownload".equalsIgnoreCase(sName)) {
                  hms.setTimeDownload(value);
               } else if ("TimeSetting".equalsIgnoreCase(sName)) {
                  hms.setTimeSetting(value);
               } else if ("ClkNTPSvrURL".equalsIgnoreCase(sName)) {
                  hms.setClkNTPSvrURL(value);
               } else if ("ClkDownloadProgram".equalsIgnoreCase(sName)) {
                  hms.setClkDownloadProgram(value);
               } else if ("ClkDownloadCountry".equalsIgnoreCase(sName)) {
                  hms.setClkDownloadCountry(value);
               } else if ("ClkTimeZone".equalsIgnoreCase(sName)) {
                  hms.setClkTimeZone(value);
               } else if ("DaylightSaving".equalsIgnoreCase(sName)) {
                  hms.setDaylightSaving(value);
               } else if ("ClkTimeOffset".equalsIgnoreCase(sName)) {
                  hms.setClkTimeOffset(value);
               } else if ("ReferenceDate".equalsIgnoreCase(sName)) {
                  hms.setReferenceDate(value);
               } else if ("ReferenceTime".equalsIgnoreCase(sName)) {
                  hms.setReferenceTime(value);
               } else if ("MainSpkrEnable".equalsIgnoreCase(sName)) {
                  hms.setMainSpkrEnable(value);
               } else if ("IndMainSpkrMute".equalsIgnoreCase(sName)) {
                  hms.setIndMainSpkrMute(value);
               } else if ("DefMainSpkrVol".equalsIgnoreCase(sName)) {
                  hms.setDefMainSpkrVol(value);
               } else if ("AutoChnUpdate".equalsIgnoreCase(sName)) {
                  hms.setAutoChnUpdate(value);
               } else if ("AutoSwUpdate".equalsIgnoreCase(sName)) {
                  hms.setAutoSwUpdate(value);
               } else if ("SkipScrambled".equalsIgnoreCase(sName)) {
                  hms.setSkipScrambled(value);
               } else if ("MultiRC".equalsIgnoreCase(sName)) {
                  hms.setMultiRC(value);
               } else if ("MyChoice".equalsIgnoreCase(sName)) {
                  hms.setMyChoice(value);
               } else if ("AskForPIN".equalsIgnoreCase(sName)) {
                  hms.setAskForPIN(value);
               } else if ("SmartPay".equalsIgnoreCase(sName)) {
                  hms.setSmartPay(value);
               } else if ("AV".equalsIgnoreCase(sName)) {
                  hms.setAV(value);
               } else if ("SmartTV".equalsIgnoreCase(sName)) {
                  hms.setSmartTV(value);
               } else if ("AppControlID".equalsIgnoreCase(sName)) {
                  hms.setAppControlID(value);
               } else if ("ProfileName".equalsIgnoreCase(sName)) {
                  hms.setProfileName(value);
               } else if ("Source".equalsIgnoreCase(sName)) {
                  hms.setSource(value);
               } else if ("Fallback".equalsIgnoreCase(sName)) {
                  hms.setFallback(value);
               } else if ("DashboardIconLabel".equalsIgnoreCase(sName)) {
                  hms.setDashboardIconLabel(value);
               } else if ("ServerUIURL".equalsIgnoreCase(sName)) {
                  hms.setServerUIURL(value);
               } else if ("WebServicesURL".equalsIgnoreCase(sName)) {
                  hms.setWebServicesURL(value);
               } else if ("TVDiscoveryService".equalsIgnoreCase(sName)) {
                  hms.setTVDiscoveryService(value);
               } else if ("ProfessionalSettingsService".equalsIgnoreCase(sName)) {
                  hms.setProfessionalSettingsService(value);
               } else if ("IPUpgradeService".equalsIgnoreCase(sName)) {
                  hms.setIPUpgradeService(value);
               } else if ("PowerService".equalsIgnoreCase(sName)) {
                  hms.setPowerService(value);
               } else if ("VsecOverRFEnable".equalsIgnoreCase(sName)) {
                  hms.setVsecOverRFEnable(value);
               } else if ("EraseKeyOption".equalsIgnoreCase(sName)) {
                  hms.setEraseKeyOption(value);
               } else if ("VsecFrequency".equalsIgnoreCase(sName)) {
                  hms.setVsecFrequency(value);
               } else if ("VsecMedium".equalsIgnoreCase(sName)) {
                  hms.setVsecMedium(value);
               } else if ("VsecSymbolRate".equalsIgnoreCase(sName)) {
                  hms.setVsecSymbolRate(value);
               } else if ("RFCLFrequency".equalsIgnoreCase(sName)) {
                  hms.setRFCLFrequency(value);
               } else if ("RFCLMedium".equalsIgnoreCase(sName)) {
                  hms.setRFCLMedium(value);
               } else if ("RFCLSymbolRate".equalsIgnoreCase(sName)) {
                  hms.setRFCLSymbolRate(value);
               } else if ("UpgradeMode".equalsIgnoreCase(sName)) {
                  hms.setUpgradeMode(value);
               } else if ("AutoUpgrade".equalsIgnoreCase(sName)) {
                  hms.setAutoUpgrade(value);
               } else if ("InstallationMode".equalsIgnoreCase(sName)) {
                  hms.setInstallationMode(value);
               } else if ("CloneMultiRC".equalsIgnoreCase(sName)) {
                  hms.setCloneMultiRC(value);
               }
            }
         }
      }

      try {
         JAXBContext context = JAXBContext.newInstance(HotelModeSettings.class);
         java.io.File settingFile = this.generateSettingXMLFile(PlatformUtils.getRootFolderName(this.platformId) + "_SSB.xml", null);
         Marshaller marshaller = context.createMarshaller();
         marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
         marshaller.marshal(hms, settingFile);
      } catch (JAXBException e) {
         LOG.error(e.getMessage(), e);
      }
   }

   private void processSetting2K16(List<Setting> listOfSettings) {
      List<Item> list = new ArrayList<>();
      String majorVerNo = "0";
      String minorVerNo = "0";
      Setting resolution = null;

      for (Setting s : listOfSettings) {
         if (null == s) {
            break;
         }

         Item item2 = new Item();
         if ("majorVerNo".equalsIgnoreCase(s.getItem())) {
            majorVerNo = s.getLastValue();
         }

         if ("minorVerNo".equalsIgnoreCase(s.getItem())) {
            minorVerNo = s.getLastValue();
         }

         if (("MS2K16".equalsIgnoreCase(s.getXaddr()) || "ES2K16".equalsIgnoreCase(s.getXaddr()))
            && !s.getItem().equals("majorVerNo")
            && !s.getItem().equals("minorVerNo")) {
            if ("Advanced.Diagnostic Logging.Frequency".equalsIgnoreCase(s.getItem())) {
               item2.setName(s.getItem());
               String value = StringUtils.isNotEmpty(s.getLastValue()) ? s.getLastValue() : "30";
               value = "" + value.trim() + "";
               item2.setValue(value);
               item2.setCloneIn(s.getCloneIn());
               list.add(item2);
            } else {
               item2.setName(s.getItem());
               switch (s.getItem()) {
                  case "TV Settings.Picture.Advanced.Sharpness.Super Resolution":
                  case "TV Settings.Picture.Advanced.Sharpness.Ultra Resolution":
                  case "TV Settings.Picture.Advanced.Sharpness.8K Ultra resolution":
                     resolution = s;
                  default:
                     String value = s.getLastValue() != null ? s.getLastValue() : "";
                     value = "" + value.trim() + "";
                     item2.setValue(value);
                     item2.setCloneIn(s.getCloneIn());
                     list.add(item2);
               }
            }
         }
      }

      if (null != resolution) {
         Item item3 = new Item();
         Item item4 = new Item();
         Item item5 = new Item();
         String value = resolution.getLastValue() != null ? resolution.getLastValue() : "";
         item3.setName("TV Settings.Picture.Advanced.Sharpness.Super Resolution");
         item3.setValue(value);
         item3.setCloneIn(resolution.getCloneIn());
         item4.setName("TV Settings.Picture.Advanced.Sharpness.Ultra Resolution");
         item4.setValue(value);
         item4.setCloneIn(resolution.getCloneIn());
         item5.setName("TV Settings.Picture.Advanced.Sharpness.8K Ultra resolution");
         item5.setValue(value);
         item5.setCloneIn(resolution.getCloneIn());
         switch (resolution.getItem()) {
            case "TV Settings.Picture.Advanced.Sharpness.Super Resolution":
               list.add(item4);
               list.add(item5);
               break;
            case "TV Settings.Picture.Advanced.Sharpness.Ultra Resolution":
               list.add(item3);
               list.add(item5);
               break;
            case "TV Settings.Picture.Advanced.Sharpness.8K Ultra resolution":
               list.add(item3);
               list.add(item4);
         }
      }

      try {
         TVSettings tvsettings = new TVSettings();
         SchemaVersion sv = new SchemaVersion();
         sv.setMajorVerNo(majorVerNo);
         sv.setMinorVerNo(minorVerNo);
         tvsettings.setSchemaVersion(sv);
         tvsettings.setItem(list);
         JAXBContext context = JAXBContext.newInstance(TVSettings.class);
         String settingLocation = this.outputPath + "/TVSettings/TVSettings.xml";
         LOG.info("Marshal TVSettings.xml location ::= {}", settingLocation);
         java.io.File settingFile = this.generateSettingXMLFile("TVSettings", "TVSettings.xml");
         Marshaller marshaller = context.createMarshaller();
         marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
         marshaller.marshal(tvsettings, settingFile);
      } catch (JAXBException e) {
         LOG.error(e.getMessage(), e);
      }
   }

   private boolean platormHasSetting(Setting s) {
      boolean ret = false;
      List<Setting> settings = this.platform.getSettings().getSetting();
      if (s.getItem1() != null && s.getRefFile() != null && s.getRefFile().equalsIgnoreCase("BdsLastStatus.txt")) {
         for (Setting stoTest : settings) {
            if (stoTest.getItem1() != null && stoTest.getItem1() != null && s.getItem1() != null && stoTest.getItem1().equals(s.getItem1())) {
               return true;
            }
         }
      }

      for (Setting set : settings) {
         if (null != s && s.getItem1() != null && s.getItem1().equalsIgnoreCase("BaudRate")) {
            LOG.info(s.getItem1());
         }

         if (null != s && set.getItem().equals(s.getItem())) {
            ret = true;
            break;
         }
      }

      return ret;
   }

   private boolean hasSettingConfigured(Setting s, List<Setting> configuredSettings) {
      boolean ret = false;
      if (s.getRefFile() != null && s.getRefFile().equalsIgnoreCase("BdsLastStatus.txt")) {
         return true;
      }

      if (s.getItem1() != null && s.getRefFile() != null && s.getRefFile().equalsIgnoreCase("BdsLastStatus.txt")) {
         for (Setting stoTest : configuredSettings) {
            if (stoTest.getItem1() != null && stoTest.getItem1() != null && s.getItem1() != null && stoTest.getItem1().equals(s.getItem1())) {
               return true;
            }
         }
      }

      if (s.getXaddr() != null && s.getXaddr().equals("ES2K12")) {
         return false;
      }

      for (Setting stoTest : configuredSettings) {
         if (stoTest.getItem() != null && s.getItem() != null && stoTest.getItem().equals(s.getItem())) {
            ret = true;
            break;
         }
      }

      return ret;
   }

   private List<Setting> getSettingList(String confs) {
      List<Setting> ret = null;

      try {
         SettingChannelBean settings = new Gson().fromJson(confs, SettingChannelBean.class);
         if (null != settings && settings.getSetttings().getSetting() != null && !settings.getSetttings().getSetting().isEmpty()) {
            ret = settings.getSetttings().getSetting();
         }
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }

      return ret;
   }

   private void process2K14MSFiles(String srcRootPath) {
      try {
         String[] cloneItems = new String[]{"SmartInfoImages", "SmartInfoPages", "SystemUIBackup"};

         for (String cloneItem : cloneItems) {
            java.io.File srcDir = new java.io.File(srcRootPath + cloneItem);
            if (srcDir.exists()) {
               java.io.File destDir = new java.io.File(this.outputPath + cloneItem);
               String zippath = destDir.toString();
               String targetName = PlatformUtils.getRootFolderName(this.platformId) + "_" + cloneItem;
               java.io.File file = new java.io.File(srcDir.toString() + java.io.File.separator + targetName + "_Identifier.txt");
               if (file.exists()) {
                  FileUtils.forceDelete(file);
               }

               String identifier = TpvDateUtils.getCurrentIndentifierFormatTime();

               try {
                  FileUtils.writeStringToFile(file, identifier, StandardCharsets.UTF_8);
               } catch (IOException e) {
                  LOG.error(e.getMessage(), e);
               }

               FileUtils.copyDirectory(srcDir, destDir);
               if (!this.isDownloadMode) {
                  ZipCommonUtils.gen7Zip(zippath, this.outputPath + targetName + ".zip");
               }
            }
         }

         java.io.File findCmsFiles = new java.io.File(srcRootPath);
         java.io.File csmDirOrFile = TpvFileUtils.getFileByNamePrefix(findCmsFiles, "CSM_");
         if (csmDirOrFile == null) {
            csmDirOrFile = TpvFileUtils.getDirectoryByName(findCmsFiles.getParentFile(), "CSMDump");
         }

         if (csmDirOrFile != null) {
            this.makeDirs(this.outputPath + "/CSMDump");
            if (csmDirOrFile.isFile()) {
               FileUtils.copyFileToDirectory(csmDirOrFile, new java.io.File(this.outputPath + "/CSMDump"));
            } else {
               FileUtils.copyDirectory(csmDirOrFile, new java.io.File(this.outputPath + "/CSMDump"));
            }
         }
      } catch (Exception ex) {
         LOG.error(ex.getMessage(), ex);
      }
   }

   private void copyChannelListFiles(ChannelPackage channelPackage) {
      String channelBasePath = CloneItemUtils.getChannelPackageDataPath(channelPackage);
      String[] channelItems = new String[]{"ChannelList"};

      for (String channelItem : channelItems) {
         java.io.File srcDir = new java.io.File(channelBasePath + channelItem);
         if (srcDir.exists()) {
            TpvFileUtils.copyDirectoryIngoreExistsFile(srcDir, new java.io.File(this.outputPath + channelItem));
         }
      }

      java.io.File defaultLogo = new java.io.File(this.outputPath + "/ChannelList/ChannelLogos/default/");
      FileUtils.deleteQuietly(defaultLogo);
      java.io.File defaultThemeIcons = new java.io.File(this.outputPath + "/ChannelList/ThemeIcons/default/");
      FileUtils.deleteQuietly(defaultThemeIcons);
   }

   private void process2K16Files(String srcRootPath) {
      try {
         this.removeCleanFile(srcRootPath);
         String[] cloneItemNames = new String[]{
            "LocalCustomDashboard",
            "WeatherForecast",
            "RoomSpecificSettings",
            "PMS",
            "SmartInfoBrowser",
            "Vsecure",
            "Script",
            "HTVCfg",
            "MyChoice",
            "ProfessionalApps",
            "AndroidAppsData"
         };

         for (String cloneItem : cloneItemNames) {
            java.io.File destDir2 = new java.io.File(this.outputPath + cloneItem);
            java.io.File srcDir = new java.io.File(srcRootPath + cloneItem);
            if (srcDir.exists()) {
               this.fixLostOfIdentifierFile(cloneItem, srcDir);
               FileUtils.copyDirectory(srcDir, destDir2);
            }
         }

         java.io.File findCmsFiles = TpvFileUtils.getDirectoryByName(new java.io.File(srcRootPath).getParentFile(), "DataDump");
         if (findCmsFiles != null) {
            java.io.File[] cmsFile = findCmsFiles.listFiles();
            if (null != cmsFile && cmsFile.length > 0) {
               for (java.io.File cms : cmsFile) {
                  if (cms.getName().startsWith("CSM")) {
                     this.makeDirs(this.outputPath + "/DataDump");
                     FileUtils.copyFileToDirectory(
                        new java.io.File(findCmsFiles + java.io.File.separator + cms.getName()), new java.io.File(this.outputPath + "/DataDump")
                     );
                     break;
                  }
               }
            }
         }
      } catch (Exception ex) {
         LOG.error(ex.getMessage(), ex);
      }
   }

   private void fixLostOfIdentifierFile(String cloneItem, java.io.File srcDir) {
      if (!Arrays.asList("WeatherForecast", "Script").contains(cloneItem)) {
         java.io.File cloneItemIdenfier = new java.io.File(srcDir + java.io.File.separator + cloneItem + "_Identifier.txt");
         if (!cloneItemIdenfier.exists() || cloneItemIdenfier.length() == 0L) {
            String currentTimeIdentifier = TpvDateUtils.getCurrentIndentifierFormatTime();

            try {
               FileUtils.writeStringToFile(cloneItemIdenfier, currentTimeIdentifier, StandardCharsets.UTF_8);
            } catch (IOException e) {
               LOG.error(e.getMessage(), e);
            }
         }
      }
   }

   private void removeCleanFile(String filePath) {
      java.io.File roomSpecificFile = new java.io.File(filePath + "RoomSpecificSettings//");
      if (roomSpecificFile.exists()) {
         java.io.File[] files = roomSpecificFile.listFiles();

         for (java.io.File f : files) {
            if (f.getName().endsWith(".clean")) {
               try {
                  Files.delete(f.toPath());
               } catch (IOException e) {
                  LOG.error(e.getMessage(), e);
               }
            }
         }
      }
   }

   private java.io.File generateSettingXMLFile(String firstFolder, String secondFolder) {
      String path = this.outputPath + java.io.File.separator + firstFolder;
      if (null != secondFolder) {
         java.io.File parentFile = new java.io.File(path);
         if (!parentFile.exists()) {
            parentFile.mkdirs();
         }

         path = path + java.io.File.separator + secondFolder;
      }

      java.io.File settingFile = new java.io.File(path);
      if (!settingFile.getParentFile().exists()) {
         settingFile.getParentFile().mkdirs();
      }

      if (!settingFile.exists()) {
         try {
            settingFile.createNewFile();
         } catch (IOException e) {
            LOG.error(e.getMessage(), e);
         }
      }

      return settingFile;
   }

   private void zipAllToPath(String path) {
      java.io.File cloneAssemblyDir = new java.io.File(this.outputPath);
      java.io.File[] cloneItems = cloneAssemblyDir.listFiles();

      for (java.io.File cloneItemDir : cloneItems) {
         String zipName = cloneItemDir.getName();
         String srcPath = cloneItemDir.getAbsolutePath();
         if (this.isCloneDataEmpty(cloneItemDir.getAbsolutePath())) {
            LOG.info("empty clone data,ignore");
         } else {
            String zipPath = String.format(Locale.ENGLISH, "%s/%s.zip", path, zipName);
            ZipCommonUtils.zipFiles(srcPath, zipPath);
         }
      }
   }

   private String getItemIdentifier(java.io.File subItem) {
      if (subItem.getName().equalsIgnoreCase("DataDump")) {
         return "";
      }

      java.io.File identifer = new java.io.File(subItem.getAbsolutePath() + java.io.File.separator + subItem.getName() + "_Identifier.txt");

      try {
         return FileUtils.readFileToString(identifer, StandardCharsets.UTF_8);
      } catch (IOException e) {
         return "";
      }
   }

   public String getSiAssignCloneItems() {
      JSONArray jaItems = new JSONArray();
      java.io.File assemblyLocation = new java.io.File(this.outputPath);
      java.io.File[] files = assemblyLocation.listFiles();
      if (files != null) {
         for (java.io.File assignFile : files) {
            String assignDirName = assignFile.getName();
            if (!PlatformUtils.isAsta2016Up(this.platformId) || this.checkCloneItemAvailable(assignDirName)) {
               JSONObject jsObj = new JSONObject();
               String itemName = CloneItemUtils.convertItemToJapitName(assignDirName);
               String versionNo = this.getItemIdentifier(assignFile);
               LOG.info("itemName:{},versionNo:{}", itemName, versionNo);
               versionNo = TpvStringUtils.limitStringLength(versionNo, 32);
               jsObj.put("CloneItemName", itemName);
               jsObj.put("CloneItemVersionNo", versionNo);
               jsObj.put("CloneItemStatus", "No");
               jaItems.put(jsObj);
            }
         }
      }

      JSONObject jsObj = new JSONObject();
      jsObj.put("SiAssignItem", jaItems);
      LOG.info("SiAssignCloneItems:{}", jsObj);
      return jsObj.toString();
   }

   @Override
   public void close() {
      this.cleanAssemblyDir();
   }

   public void removeUnexportItems(String[] exportItems) {
      List<String> itemList = Arrays.asList(exportItems);
      java.io.File[] subItems = new java.io.File(this.outputPath).listFiles();
      if (subItems == null) {
         LOG.error("output path not exits,{}", this.outputPath);
      } else {
         String rootfolderName = PlatformUtils.getRootFolderName(this.platformId);
         String[] specItems = new String[]{CloneItemType.TVSettings.name(), CloneItemType.ChannelList.name(), CloneItemType.WelcomeLogo.name()};
         String[] specItemNames = new String[]{rootfolderName + "_SSB", rootfolderName + "_CHTB", rootfolderName + "_WelcomeLogo"};

         for (java.io.File subItem : subItems) {
            if (subItem.isDirectory() && !itemList.contains(subItem.getName())) {
               FileUtils.deleteQuietly(subItem);
               LOG.info("remove unexport Item:{}", subItem.getName());
            } else if (subItem.isFile() && !PlatformUtils.isAsta2016Up(this.platformId)) {
               for (int i = 0; i < specItemNames.length; i++) {
                  String itemName = specItemNames[i];
                  String itemType = specItems[i];
                  if (subItem.getName().startsWith(itemName) && !itemList.contains(itemType)) {
                     FileUtils.deleteQuietly(subItem);
                     LOG.info("remove unexport Item:{}", subItem.getName());
                  }
               }

               if (FilenameUtils.getExtension(subItem.getName()).equalsIgnoreCase("zip")) {
                  String subItemName = FilenameUtils.getBaseName(subItem.getName()).replaceAll(rootfolderName + "_", "");
                  if (!itemList.contains(subItemName)) {
                     FileUtils.deleteQuietly(subItem);
                     LOG.info("remove unexport Item:{}", subItem.getName());
                  }
               }
            }
         }
      }
   }

   public void initEnableScheduleTvSettings(String identifier) {
      try {
         String tvsettingPath = this.outputPath + "TVSettings";
         java.io.File tvSettingFile = new java.io.File(tvsettingPath);
         if (tvSettingFile.exists()) {
            FileUtils.deleteQuietly(tvSettingFile);
         }

         tvSettingFile.mkdirs();
         String tvsettingXmlPath = tvsettingPath + java.io.File.separator + "TVSettings.xml";
         java.io.File tvsettingXmlFile = new java.io.File(tvsettingXmlPath);
         FileUtils.writeStringToFile(
            tvsettingXmlFile,
            "<?xml version='1.0' encoding='UTF-8' ?>\r\n\r\n\r\n\r\n<TVSettings>\r\n  <SchemaVersion MajorVerNo=\"1\" MinorVerNo=\"0\" />\r\n  <item>\r\n    <Name>Features.Scheduler.Enable</Name>\r\n    <Value>Yes</Value>\r\n    <CloneIn>Yes</CloneIn>\r\n  </item>\r\n\r\n</TVSettings>",
            StandardCharsets.UTF_8
         );
         String tvsettingIdentifierPath = tvsettingPath + java.io.File.separator + "TVSettings_Identifier.txt";
         java.io.File tvsettingIdentifierFile = new java.io.File(tvsettingIdentifierPath);
         FileUtils.writeStringToFile(tvsettingIdentifierFile, identifier, StandardCharsets.UTF_8);
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }
   }

   public void zipClonePacketAccordingType(int cloneId, String destPath, String selectCloneType) {
      List<String> copyList = new ArrayList<>();
      switch (selectCloneType) {
         case "Clone":
            copyList = Arrays.asList(CommonConstants.cloneItems);
            break;
         case "Settings":
            copyList.add("TVSettings");
            break;
         case "Channels":
            copyList.add("ChannelList");
            copyList.add("MediaChannels");
            break;
         case "Apps":
            copyList.add("AndroidApps");
            break;
         case "Content":
            copyList.add("SmartInfoBrowser");
            break;
         case "Banners":
            copyList.add("Banner");
            copyList.add("TVSettings");
            break;
         case "UI":
            copyList.add("ProfessionalAppsData");
            break;
         case "Schedules":
            copyList.add("TVSettings");
            copyList.add("Schedules");
            break;
         case "Welcome":
            WelcomeManager welMgr = JpaManager.getWelcomeManager();
            Welcome welcome = null;

            try {
               welcome = welMgr.loadByKey(cloneId);
               if (null != welcome) {
                  int type = welcome.getType();
                  copyList.add(this.getWelcomeCloneName(type));
                  if (type == 1) {
                     copyList.add("TVSettings");
                  }
               }
            } catch (Exception e) {
               LOG.error(e.getMessage(), e);
            }
            break;
         case "Weather":
            copyList.add("WeatherForecast");
      }

      for (String dirName : copyList) {
         String sourcePath = this.outputPath + java.io.File.separator + dirName;
         java.io.File sourcePathFile = new java.io.File(sourcePath);
         java.io.File destFile = new java.io.File(destPath + dirName + ".zip");
         if (sourcePathFile.exists() && this.checkCloneItemAvailable(dirName) && !destFile.exists()) {
            ZipCommonUtils.zipFiles(sourcePath, destPath + dirName + ".zip");
         }
      }
   }

   private static String generateSimpleTvSettingsClone(String tvIP, String tvSettingContent) {
      try {
         String ipRoot = tvIP.replace(".", "");
         String dirPath = CommonConstants.SISERVER_CONF_DIR + ipRoot + "/MasterCloneData/TVSettings/";
         FileUtils.forceMkdir(new java.io.File(dirPath));
         String xmlPath = dirPath + "TVSettings.xml";
         FileUtils.writeStringToFile(new java.io.File(xmlPath), tvSettingContent, StandardCharsets.UTF_8);
         String identifierPath = dirPath + "TVSettings_Identifier.txt";
         String identifier = TpvDateUtils.getCurrentIndentifierFormatTime();
         FileUtils.writeStringToFile(new java.io.File(identifierPath), identifier, StandardCharsets.UTF_8);
         String zipContentPath = CommonConstants.SISERVER_CONF_DIR + ipRoot + "/MasterCloneData/";
         String targetPath = CommonConstants.servletContextPath + "/Profile/Clone/" + ipRoot + "/";
         java.io.File targetDirectory = new java.io.File(targetPath);
         FileUtils.deleteDirectory(targetDirectory);
         targetDirectory.mkdirs();
         ZipCommonUtils.createZip(zipContentPath, targetPath + "TVSettings.zip");
         return identifier;
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         return null;
      }
   }

   private boolean checkCloneItemAvailable(String cloneItemName) {
      java.io.File cloneItemFile = new java.io.File(this.outputPath + java.io.File.separator + cloneItemName);
      int fileCount = 0;
      List<String> skipCheckFiles = Arrays.asList("HTVCfg.xml", "Script.xml");
      if (cloneItemFile.isDirectory()) {
         for (java.io.File assignData : cloneItemFile.listFiles()) {
            if (skipCheckFiles.contains(assignData.getName())) {
               return true;
            }

            fileCount++;
         }
      } else {
         LOG.error("cloneItemName {} is not a valid clone directory", cloneItemName);
      }

      return fileCount > 1;
   }

   private String getWelcomeCloneName(int type) {
      String cloneName = "WelcomeLogo";
      switch (type) {
         case 0:
            cloneName = "WelcomeLogo";
            break;
         case 1:
            cloneName = "ProfessionalAppsData";
         case 2:
      }

      return cloneName;
   }
}
