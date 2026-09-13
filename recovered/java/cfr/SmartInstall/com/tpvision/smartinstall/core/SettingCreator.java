/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tpvision.smartinstall.SmartInstallConfiguration;
import com.tpvision.smartinstall.androidapp.AndroidAppHelper;
import com.tpvision.smartinstall.bean.HotelInfo;
import com.tpvision.smartinstall.core.SettingChannelBean;
import com.tpvision.smartinstall.core.UpgCreator;
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
import com.tpvision.smartinstall.weather.WeatherServiceImpl;
import com.tpvision.smartinstall.xml.Platform;
import com.tpvision.smartinstall.xml.Setting;
import com.tpvision.smartinstall.xml.channel.v5.TvContents;
import com.tpvision.smartinstall.xml.es2k14.HotelModeSettings;
import com.tpvision.smartinstall.xml.es2k14.HotelModelSettingsForTpn142;
import com.tpvision.smartinstall.xml.setting.v2k16.Item;
import com.tpvision.smartinstall.xml.setting.v2k16.SchemaVersion;
import com.tpvision.smartinstall.xml.setting.v2k16.TVSettings;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
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

public class SettingCreator
implements AutoCloseable {
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
            Path tmpPath = Files.createTempDirectory(new File(CommonConstants.CLONE_ASSEMBLY_LOCATION).toPath(), this.platformId + "-", new FileAttribute[0]);
            this.outputPath = tmpPath.toString() + File.separator;
            LOG.info("init SettingCreator platform:{}, assemble path:{}", (Object)this.platformId, (Object)this.outputPath);
        }
        catch (IOException e) {
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
            case "TPN141HE_CloneData": {
                this.creatMS2K14IP(new File(this.outputPath), cloneId);
                break;
            }
            default: {
                this.create2K16IPPackage(cloneId, cloneType);
            }
        }
    }

    public static int copyCloneItem(String cloneType, int id) {
        LOG.info("copy cloneType:{},id:{}", (Object)cloneType, (Object)id);
        CommonConstants.CloneItemType playType = CommonConstants.CloneItemType.valueOf(cloneType);
        int newId = 0;
        switch (playType) {
            case TVSettings: {
                SettingPackage settingPackage = JpaManager.getSettingPackageManager().copy(id);
                newId = settingPackage.getId();
                break;
            }
            case AndroidApps: {
                AppPackage app = JpaManager.getAppPackageManager().copy(id);
                newId = app.getId();
                break;
            }
            case Banner: {
                Banners banner = JpaManager.getBannersManager().copy(id);
                newId = banner.getId();
                break;
            }
            case ChannelList: 
            case MediaChannels: {
                ChannelPackage channelPackage = JpaManager.getChannelPackageManager().copy(id);
                newId = channelPackage.getId();
                break;
            }
            case Schedules: {
                Schedule schedule = JpaManager.getScheduleManager().copy(id);
                newId = schedule.getId();
                break;
            }
            case UiCustomizations: {
                UiCustomizations ui = JpaManager.getUiCustomizationsManager().copy(id);
                newId = ui.getId();
                break;
            }
            case WelcomeLogo: {
                Welcome welcome = JpaManager.getWelcomeManager().copy(id);
                newId = welcome.getId();
                break;
            }
            default: {
                LOG.debug("unsupported copy items {}", (Object)cloneType);
            }
        }
        return newId;
    }

    public void processClonePacket(String cloneType, int id, String rid) throws IOException {
        LOG.info("processClonePacket cloneType:{},id:{}", (Object)cloneType, (Object)id);
        CommonConstants.CloneItemType playType = CommonConstants.CloneItemType.valueOf(cloneType);
        switch (playType) {
            case Clone: {
                SettingManager settingMgr = JpaManager.getSettingManager();
                com.tpvision.smartinstall.dao.core.Setting setting = settingMgr.loadByKey(id);
                if (null == setting) break;
                this.processSettings(setting);
                break;
            }
            case Firmware: {
                UpgSetting upgSetting = JpaManager.getUpgSettingManager().loadByKey(id);
                if (null == upgSetting) break;
                this.processFirmware(upgSetting);
                break;
            }
            case TVSettings: {
                SettingPackageManager settingpackageMgr = JpaManager.getSettingPackageManager();
                SettingPackage settingpackage = settingpackageMgr.loadByKey(id);
                if (null == settingpackage) break;
                this.processTVSettings(settingpackage);
                break;
            }
            case ChannelList: {
                ChannelPackageManager channelpackageMgr = JpaManager.getChannelPackageManager();
                ChannelPackage channelpackage = channelpackageMgr.loadByKey(id);
                if (null == channelpackage) break;
                this.processChannelPackage(channelpackage);
                break;
            }
            case MediaChannels: {
                ChannelPackage channelpackage1 = JpaManager.getChannelPackageManager().loadByKey(id);
                if (null == channelpackage1) break;
                this.processMediaChannels(channelpackage1);
                break;
            }
            case AndroidApps: {
                AppPackageManager apppackageMgr = JpaManager.getAppPackageManager();
                AppPackage apppackage = apppackageMgr.loadByKey(id);
                if (null == apppackage) break;
                this.processAppPackage(apppackage);
                break;
            }
            case Banner: {
                Banners banners = JpaManager.getBannersManager().loadByKey(id);
                if (null == banners) break;
                this.processBanner(banners);
                break;
            }
            case SmartInfoBrowser: {
                this.processContent(id);
                break;
            }
            case WelcomeLogo: {
                this.processWelcome(id);
                break;
            }
            case PMS: {
                this.processPMS(id, rid);
                break;
            }
            case Schedules: {
                Schedule schedule = JpaManager.getScheduleManager().loadByKey(id);
                if (schedule == null) break;
                this.processSchedules(schedule);
                break;
            }
            case UiCustomizations: 
            case ProfessionalAppsData: {
                UiCustomizations uiCustomizations = JpaManager.getUiCustomizationsManager().loadByKey(id);
                if (null == uiCustomizations) break;
                this.processUiCustomizations(uiCustomizations);
                break;
            }
            default: {
                LOG.error("not support to playout:{}", (Object)playType.name());
            }
        }
    }

    private void processPMS(int id, String rid) {
        File pmsfile = new File(this.outputPath + "/PMS/PMS.json");
        PmsUtils.PmsAction pmsAction = PmsUtils.PmsAction.values()[id];
        this.generatePMSFile(pmsAction, pmsfile, rid);
    }

    private void generatePMSFile(PmsUtils.PmsAction pmsAction, File pmsfile, String rid) {
        String respData = "";
        switch (pmsAction) {
            case CheckOut: {
                respData = PmsJapitCommand.getCheckOut();
                break;
            }
            case CheckIn: {
                GuestInfo gi = PmsUtils.getGuestInfoByRoomId(rid);
                if (null == gi) break;
                CheckInVO checkinVO = PmsUtils.getCheckInVO(gi);
                respData = PmsJapitCommand.getCheckIn(checkinVO);
                break;
            }
            case LanguageChange: {
                GuestInfo gi = PmsUtils.getGuestInfoByRoomId(rid);
                if (null == gi) break;
                CheckInVO checkinVO = PmsUtils.getCheckInVO(gi);
                respData = PmsJapitCommand.getUpdateGuestPreference(checkinVO);
                break;
            }
        }
        respData = PmsUtils.convertToPMSOfflineService(respData, rid);
        pmsfile.getParentFile().mkdirs();
        try {
            FileUtils.writeStringToFile(pmsfile, respData, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
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
            roomSpecificSettings = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<RoomSpecificSettings>\r\n  <SchemaVersion MajorVerNo=\"1\" MinorVerNo=\"0\"/>\r\n  <TV>\r\n    <SerialNumber>" + tvSerialNumber + "</SerialNumber>\r\n    <item>\r\n      <Name>Identification Settings.RoomID</Name>\r\n      <Value>" + roomID + "</Value>\r\n    </item>\r\n  </TV>\r\n</RoomSpecificSettings>";
        } else {
            boolean isMasf = PlatformUtils.isMasf2019Up(platform);
            String namePrefix = isMasf ? "Professional Settings." : "";
            roomSpecificSettings = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<RoomSpecificSettings>\r\n  <SchemaVersion MajorVerNo=\"1\" MinorVerNo=\"0\"/>\r\n  <TV>\r\n    <SerialNumber>" + tvSerialNumber + "</SerialNumber>\r\n    <item>\r\n      <Name>" + namePrefix + "Advanced.Identification Settings.RoomID</Name>\r\n      <Value>" + roomID + "</Value>\r\n    </item>\r\n  </TV>\r\n</RoomSpecificSettings>";
        }
        String ipName = tvIP.replace(".", "");
        try {
            String dirPath = CommonConstants.SISERVER_CONF_DIR + ipName + "/MasterCloneData/RoomSpecificSettings/";
            File dirFile = new File(dirPath);
            FileUtils.forceMkdir(dirFile);
            String xmlPath = dirPath + "/RoomSpecificSettings.xml";
            File xmlFile = new File(xmlPath);
            FileUtils.writeStringToFile(xmlFile, roomSpecificSettings, StandardCharsets.UTF_8);
            String identifierPath = dirPath + "/RoomSpecificSettings_Identifier.txt";
            File identifierFile = new File(identifierPath);
            FileUtils.writeStringToFile(identifierFile, TpvDateUtils.getCurrentIndentifierFormatTime(), StandardCharsets.UTF_8);
            String zipPath = CommonConstants.servletContextPath + "/Profile/Clone/" + ipName + "/";
            File zipDirectory = new File(zipPath);
            FileUtils.deleteDirectory(zipDirectory);
            zipDirectory.mkdirs();
            String dirPathTmp = CommonConstants.SISERVER_CONF_DIR + ipName + "/MasterCloneData/";
            ZipCommonUtils.createZip(dirPathTmp, zipPath + "RoomSpecificSettings.zip");
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static String createModifyRoomIdTVSetting(String tvIP, String newRoomId) {
        String tvSettingContent = "<?xml version='1.0' encoding='UTF-8' ?>\r\n<TVSettings>\r\n  <SchemaVersion MajorVerNo=\"1\" MinorVerNo=\"0\" />\r\n  <item>\r\n    <Name>Identification Settings.Room ID</Name>\r\n    <Value>" + newRoomId + "</Value>\r\n    <CloneIn>Yes</CloneIn>\r\n  </item>\r\n</TVSettings>";
        return SettingCreator.generateSimpleTvSettingsClone(tvIP, tvSettingContent);
    }

    public static String createModifyServiceUrlTVSetting(String tvIP, String cmndIP) {
        String tvSettingContent = "<?xml version='1.0' encoding='UTF-8' ?>\r\n\r\n\r\n\r\n<TVSettings>\r\n  <SchemaVersion MajorVerNo=\"1\" MinorVerNo=\"0\" />\r\n  <item>\r\n    <Name>Wireless and Networks.Control TV Over IP.WebServices.Server URL</Name>\r\n    <Value>http://" + cmndIP + ":" + CommonConstants.CMND_HTTP_PORT + "/SmartInstall/webservices.jsp</Value>\r\n    <CloneIn>Yes</CloneIn>\r\n  </item>\r\n  <item>\r\n    <Name>Wireless and Networks.Control TV Over IP.Secure Communication.Server URL</Name>\r\n    <Value>https://" + cmndIP + ":" + CommonConstants.CMND_HTTPS_PORT + "/SmartInstall/webservices.jsp</Value>\r\n    <CloneIn>Yes</CloneIn>\r\n  </item>\r\n  <item>\r\n    <Name>Wireless and Networks.Control TV Over IP.WebServices.TVDiscoveryService</Name>\r\n    <Value>On</Value>\r\n    <CloneIn>Yes</CloneIn>\r\n  </item>\r\n  <item>\r\n    <Name>Wireless and Networks.Control TV Over IP.WebServices.ProfessionalSettingsService</Name>\r\n    <Value>On</Value>\r\n    <CloneIn>Yes</CloneIn>\r\n  </item>\r\n  <item>\r\n    <Name>Wireless and Networks.Control TV Over IP.WebServices.IPUpgradeService</Name>\r\n    <Value>On</Value>\r\n    <CloneIn>Yes</CloneIn>\r\n  </item>\r\n  <item>\r\n    <Name>Wireless and Networks.Control TV Over IP.WebServices.PMSService</Name>\r\n    <Value>On</Value>\r\n    <CloneIn>Yes</CloneIn>\r\n  </item>\r\n  <item>\r\n    <Name>Wireless and Networks.Control TV Over IP.WebListeningServices.PowerService</Name>\r\n    <Value>On</Value>\r\n    <CloneIn>Yes</CloneIn>\r\n  </item>\r\n  <item>\r\n    <Name>Wireless and Networks.Control TV Over IP.WebListeningServices.TVDiscoveryService</Name>\r\n    <Value>On</Value>\r\n    <CloneIn>Yes</CloneIn>\r\n  </item>\r\n  <item>\r\n    <Name>Wireless and Networks.Control TV Over IP.WebListeningServices.IPUpgradeService</Name>\r\n    <Value>On</Value>\r\n    <CloneIn>Yes</CloneIn>\r\n  </item>\r\n  <item>\r\n    <Name>Wireless and Networks.Control TV Over IP.WebListeningServices.PMSService</Name>\r\n    <Value>On</Value>\r\n    <CloneIn>Yes</CloneIn>\r\n  </item>\r\n\r\n</TVSettings>";
        return SettingCreator.generateSimpleTvSettingsClone(tvIP, tvSettingContent);
    }

    public void processWelcomeLogo(Welcome welcome) throws IOException {
        String logoName = welcome.getName();
        File logoFile = new File(CommonConstants.WELCOME_LOGO_IMG_LOCATION + logoName);
        File directoryPath = new File(this.outputPath + File.separator + WELCOME_LOGO + File.separator);
        if (!directoryPath.exists()) {
            directoryPath.mkdirs();
        }
        String ext = FilenameUtils.getExtension(logoName);
        String targetName = "WelcomeLogo." + ext.toLowerCase();
        File targetFile = new File(directoryPath.getAbsolutePath() + File.separator + targetName);
        FileUtils.copyFile(logoFile, targetFile);
        this.processIdentifier(WELCOME_LOGO, welcome.getLastEdit());
    }

    public void processUiCustomizations(UiCustomizations ui) throws IOException {
        String professionalAppsDataPath = this.outputPath + File.separator + "ProfessionalAppsData" + File.separator;
        File directoryPath = new File(professionalAppsDataPath);
        File srcDirectoryStr = new File(CommonConstants.CLONE_PROCESS_LOCATION + "uiCustomizations" + File.separator + ui.getId() + File.separator + "PhilipsHome");
        try {
            if (srcDirectoryStr.exists()) {
                FileUtils.copyDirectoryToDirectory(srcDirectoryStr, directoryPath);
                LOG.info("copy uiCustomizations dir {} to {}", (Object)srcDirectoryStr.getAbsolutePath(), (Object)professionalAppsDataPath);
            } else {
                LOG.error("not exist uiCustomizations");
            }
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        this.updateProfessionalAppsJSON(professionalAppsDataPath, "PhilipsHome", ui.getLastEdit());
        this.processIdentifier("ProfessionalAppsData", ui.getLastEdit());
    }

    private void removeProfessionalAppsJSON(String path, String ... applicationNames) {
        File jsonFile = new File(path + File.separator + "ProfessionalAppsDataVersion.json");
        try {
            String jsonContent = jsonFile.exists() ? FileUtils.readFileToString(jsonFile, StandardCharsets.UTF_8) : "{}";
            JSONObject jo = new JSONObject(jsonContent);
            JSONArray jaDetails = jo.has("ApplicationCloneDataDetails") ? jo.getJSONArray("ApplicationCloneDataDetails") : new JSONArray();
            JSONArray newJaDetails = new JSONArray();
            for (int i = 0; i < jaDetails.length(); ++i) {
                JSONObject tmpJo = jaDetails.getJSONObject(i);
                if (tmpJo.has("ApplicationName") && Arrays.asList(applicationNames).contains(tmpJo.getString("ApplicationName"))) continue;
                newJaDetails.put(tmpJo);
            }
            jo.put("ApplicationCloneDataDetails", newJaDetails);
            FileUtils.writeStringToFile(jsonFile, jo.toString(4), StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void updateProfessionalAppsJSON(String path, String applicationName, String identifier) {
        File jsonFile = new File(path + "ProfessionalAppsDataVersion.json");
        try {
            String jsonContent = jsonFile.exists() ? FileUtils.readFileToString(jsonFile, StandardCharsets.UTF_8) : "{}";
            JSONObject jo = new JSONObject(jsonContent);
            JSONArray jaDetails = jo.has("ApplicationCloneDataDetails") ? jo.getJSONArray("ApplicationCloneDataDetails") : new JSONArray();
            boolean isNew = false;
            JSONObject joDetail = null;
            for (int i = 0; i < jaDetails.length(); ++i) {
                JSONObject tmpJo = jaDetails.getJSONObject(i);
                if (!tmpJo.has("ApplicationName") || !tmpJo.getString("ApplicationName").equalsIgnoreCase(applicationName)) continue;
                joDetail = tmpJo;
                break;
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
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public void processBanner(Banners banners) throws IOException {
        File directoryPath = new File(this.outputPath + File.separator + "Banner" + File.separator);
        if (!directoryPath.exists()) {
            directoryPath.mkdirs();
        }
        BannerTemplateUtils.generateBannerCloneFilesByBanner(directoryPath, banners);
        this.processIdentifier("Banner", CloneItemUtils.getBannerIdentifier(banners));
    }

    public void processAppPackage(AppPackage appPackage) {
        File srcDirApps = new File(CloneItemUtils.getAppPackageDataPath(appPackage.getId()));
        File destDirApps = new File(this.outputPath + File.separator + CommonConstants.CloneItemType.AndroidApps.name());
        try {
            FileUtils.copyDirectory(srcDirApps, destDirApps);
            File metaJson = new File(destDirApps.getAbsolutePath() + "/AndroidAppsMetaData.json");
            if (!metaJson.exists()) {
                AndroidAppHelper.saveAppsStringToMetaJsonFile(appPackage.getValue(), metaJson);
            }
        }
        catch (IOException e) {
            LOG.error(e.getMessage());
            return;
        }
        this.processIdentifier(CommonConstants.CloneItemType.AndroidApps.name(), appPackage.getLastEdit());
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
            File directory = new File(this.outputPath + File.separator + subDirectory + File.separator);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File outputFile = new File(directory, fileName);
            try {
                FileUtils.write(outputFile, (CharSequence)unescapedContent, StandardCharsets.UTF_8);
            }
            catch (IOException e) {
                LOG.error("Failed to write file: " + outputFile.getAbsolutePath(), e);
            }
        }
    }

    private void processHtvTlsPskKey(SettingPackage settingPackage) {
        String htvTlsPskKeyValue = settingPackage.getHtvTlsPskKey();
        if (StringUtils.isNotBlank(htvTlsPskKeyValue = StringEscapeUtils.unescapeJava(htvTlsPskKeyValue))) {
            File directoryPath = new File(this.outputPath + File.separator + "TVSettings" + File.separator);
            if (!directoryPath.exists()) {
                directoryPath.mkdirs();
            }
            String termJsonPath = directoryPath + File.separator + "HTV_TLS_PSK.KEY";
            File termJsonFile = new File(termJsonPath);
            try {
                FileUtils.write(termJsonFile, (CharSequence)htvTlsPskKeyValue, StandardCharsets.UTF_8);
            }
            catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    private boolean isCloneDataEmpty(String path) {
        if (path == null) {
            return true;
        }
        File cloneItemDir = new File(path);
        if (cloneItemDir.exists()) {
            File[] subFiles = cloneItemDir.listFiles();
            int length = subFiles != null ? subFiles.length : 0;
            return length == 1 && subFiles[0].getName().contains("_Identifier.txt") || length < 1;
        }
        return true;
    }

    private void processMediaChannels(ChannelPackage channelPackage) {
        String channelBasePath = CloneItemUtils.getChannelPackageDataPath(channelPackage);
        String channelItem = "MediaChannels";
        File srcDir = new File(channelBasePath + channelItem);
        if (!srcDir.exists()) {
            LOG.warn("MediaChannels not exists,skip copy");
        } else {
            TpvFileUtils.copyDirectoryIngoreExistsFile(srcDir, new File(this.outputPath + channelItem));
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
            String srcRootPath = CommonConstants.CLONE_PROCESS_LOCATION + cloneName + File.separator + this.platformId + "/MasterCloneData/" + fileName;
            String destRootPath = this.outputPath + fileName;
            File srcDir = new File(srcRootPath);
            if (srcDir.exists()) {
                File destDir = new File(destRootPath);
                FileUtils.copyDirectory(srcDir, destDir);
            }
        }
        catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    private void setCloneReady(boolean isReady) {
        LOG.info("set clone ready ->{}", (Object)isReady);
        File lock = new File(this.outputPath, ".lock");
        if (isReady) {
            FileUtils.deleteQuietly(lock);
        } else {
            try {
                if (!lock.createNewFile()) {
                    LOG.warn("create .lock file failed");
                }
            }
            catch (IOException e) {
                LOG.warn(e.getMessage());
            }
        }
    }

    private boolean isCloneReady() {
        File lock = new File(this.outputPath, ".lock");
        return !lock.exists();
    }

    private void waitForCloneReady(int timeout) {
        int countDown = timeout * 5;
        while (!this.isCloneReady() && countDown-- > 0) {
            try {
                Thread.sleep(200L);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (countDown <= 0) {
            LOG.warn("{} clone package cached not ready after 60s,clone package maybe not full", (Object)this.outputPath);
        } else {
            LOG.info("{} clone package cached is ready", (Object)this.outputPath);
        }
    }

    private boolean makeCloneOutput(com.tpvision.smartinstall.dao.core.Setting setting) {
        String outputName;
        File output;
        if (this.outputPath != null) {
            this.cleanAssemblyDir();
        }
        if ((output = new File(outputName = String.format(Locale.ENGLISH, "%s/CLONE_%d_%d/", CommonConstants.CLONE_ASSEMBLY_LOCATION, setting.getId(), setting.getLastUpdatedDate().getTime()))).exists() && PlatformUtils.isAsta2016Up(this.platformId)) {
            this.outputPath = output.getAbsolutePath() + "/";
            if (!this.isCloneReady()) {
                LOG.info("{} clone not ready, waiting for process", (Object)this.outputPath);
                this.waitForCloneReady(60);
            }
            return true;
        }
        SettingCreator.cleanCachedCloneData(setting.getId());
        output.mkdirs();
        this.outputPath = output.getAbsolutePath() + "/";
        this.setCloneReady(false);
        return false;
    }

    public static void cleanCachedCloneData(int settingId) {
        File[] files;
        File assembleDir = new File(CommonConstants.CLONE_ASSEMBLY_LOCATION);
        if (assembleDir.exists() && (files = assembleDir.listFiles()) != null) {
            for (File file : files) {
                if (!file.getName().startsWith("CLONE_" + settingId + "_")) continue;
                LOG.info("remove cached clone dir:{}", (Object)file.getName());
                FileUtils.deleteQuietly(file);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void processSettings(com.tpvision.smartinstall.dao.core.Setting setting) {
        LOG.info("process clone:{} ", (Object)setting.getName());
        long startTime = System.currentTimeMillis();
        if (this.makeCloneOutput(setting)) {
            LOG.info("clone package cached");
            return;
        }
        String settingProcessPath = this.getSettingCloneProcessPath(setting);
        try {
            int bannerId;
            int schId;
            int uiid;
            int appPackageId;
            int channelPackageId;
            this.preProcessChecksES(null);
            int settingPackageId = setting.getSettingPackageId();
            if (settingPackageId > 0) {
                SettingPackage settingPackage = JpaManager.getSettingPackageManager().loadByKey(settingPackageId);
                if (null != settingPackage) {
                    this.processTVSettings(settingPackage);
                } else {
                    LOG.error("settignPackage assigned not existed,id={}", (Object)settingPackageId);
                }
            }
            if ((channelPackageId = setting.getChannelPackageId()) > 0) {
                ChannelPackage channelPackage = JpaManager.getChannelPackageManager().loadByKey(channelPackageId);
                if (null != channelPackage) {
                    this.processChannelPackage(channelPackage);
                } else {
                    LOG.error("channelPackage assigned not existed,id={}", (Object)channelPackageId);
                }
            }
            if ((appPackageId = setting.getAppPackageId()) > 0) {
                AppPackage appPackage = JpaManager.getAppPackageManager().loadByKey(appPackageId);
                if (null != appPackage) {
                    this.processAppPackage(appPackage);
                } else {
                    LOG.error("appPackage assigned not existed,id={}", (Object)appPackageId);
                }
            }
            this.prepareBaseProfessionalAppsData(settingProcessPath, TpvDateUtils.formatSiCloneIdentifiers(PlatformUtils.getPlatformName(setting.getPlatform()), setting.getLastUpdatedDate()));
            if (setting.getWelcomeId() > 0) {
                this.processWelcome(setting.getWelcomeId());
            }
            if ((uiid = setting.getUiCustomizationsId()) > 0) {
                UiCustomizations uiCustomizations = JpaManager.getUiCustomizationsManager().loadByKey(uiid);
                if (uiCustomizations != null) {
                    this.processUiCustomizations(uiCustomizations);
                } else {
                    LOG.error("uiCustomizations assigned not existed,id={}", (Object)uiid);
                }
            }
            if ((schId = setting.getScheduleId()) > 0) {
                Schedule schedule = JpaManager.getScheduleManager().loadByKey(schId);
                if (schedule != null && !"TPS191HE_CloneData".equals(this.platformId)) {
                    this.processSchedules(schedule);
                } else {
                    LOG.error("schedule assigned not existed,id={}", (Object)schId);
                }
            }
            if ((bannerId = setting.getBannersId()) > 0) {
                Banners banners = JpaManager.getBannersManager().loadByKey(bannerId);
                if (banners != null) {
                    this.processBanner(banners);
                } else {
                    LOG.error("banners assigned not existed,id={}", (Object)bannerId);
                }
            }
            this.processUnchangedFiles(settingProcessPath);
            this.processHotelInfo(settingProcessPath, setting, false);
            this.processThemeTv(settingProcessPath);
            this.processSmartPin(settingProcessPath);
            if (this.platformId.equalsIgnoreCase("TPN141HE_CloneData") || this.platformId.equalsIgnoreCase("TPN142HE_CloneData")) {
                this.process2K14MSFiles(settingProcessPath);
            } else {
                this.process2K16Files(settingProcessPath);
            }
            if (!PlatformUtils.isMasf2019Up(this.platformId)) {
                if (this.iPrRFMode.equalsIgnoreCase("RF")) {
                    this.processCrc(settingProcessPath, setting, new Date(System.currentTimeMillis()));
                } else {
                    this.processCrc(settingProcessPath, setting, setting.getLastUpdatedDate());
                }
            }
            this.updateCloneItemStatus(setting);
        }
        catch (IOException e1) {
            LOG.error(e1.getMessage(), e1);
        }
        finally {
            this.setCloneReady(true);
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        LOG.info("process clone data finished,used {} ms", (Object)totalTime);
    }

    private void prepareBaseProfessionalAppsData(String settingProcessPath, String settingIdentifer) throws IOException {
        File oriFolder = new File(settingProcessPath + File.separator + "ProfessionalAppsData");
        if (!oriFolder.exists() || !oriFolder.isDirectory()) {
            LOG.info("professionalAppsData folder not exist");
            return;
        }
        File[] copyRequredFiles = oriFolder.listFiles((dir, name) -> !StringUtils.equalsAnyIgnoreCase(name, "org.droidtv.welcome", "PhilipsHome", "ProfessionalAppsData_History.xml", "ProfessionalAppsData_Identifier.txt", "ProfessionalAppsDataVersion.json"));
        if (copyRequredFiles == null || copyRequredFiles.length == 0) {
            LOG.info("no extract files required to be prepared for the ProfessionalAppsData folder");
            return;
        }
        File outputFolder = new File(this.outputPath + File.separator + "ProfessionalAppsData" + File.separator);
        for (File file : copyRequredFiles) {
            FileUtils.copyToDirectory(file, outputFolder);
        }
        this.processIdentifier("ProfessionalAppsData", settingIdentifer);
        File oriAppVersionFile = new File(oriFolder + File.separator + "ProfessionalAppsDataVersion.json");
        if (oriAppVersionFile.exists() && oriAppVersionFile.isFile()) {
            FileUtils.copyFileToDirectory(oriAppVersionFile, outputFolder);
            this.removeProfessionalAppsJSON(outputFolder.getAbsolutePath(), "org.droidtv.welcome", "PhilipsHome");
        }
    }

    private String getSettingCloneProcessPath(com.tpvision.smartinstall.dao.core.Setting setting) {
        String settingProcessPath = CommonConstants.CLONE_PROCESS_LOCATION + setting.getName() + File.separator + setting.getPlatform() + File.separator;
        if (TpvFileUtils.getDirectoryByName(new File(settingProcessPath), "MasterCloneData") != null) {
            settingProcessPath = settingProcessPath + "MasterCloneData" + File.separator;
        }
        return settingProcessPath;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean createMyChoiceRFClonePackage(String saveZipFilePath, String platformId, String room, JSONArray myChoiceParameterArray, List<PlayoutInfo> mergedPlayouts) {
        try (SettingCreator settingCreator = new SettingCreator(platformId);){
            String rootAssembleDir = settingCreator.outputPath;
            String assembleDir = rootAssembleDir + "MyChoice";
            String headerZeroRoomId = TpvStringUtils.getTvRoomId(room);
            String myChoiceData = "{  \"MyChoice\": [    {      \"Svc\": \"OfflineServices\",      \"SvcVer\": \"4.0\",      \"Cookie\": 293,      \"CmdType\": \"Change\",      \"Fun\": \"MYChoice\",      \"CommandDetails\": {        \"OfflineServiceParameters\": {          \"RoomID\": \"" + headerZeroRoomId + "\"        },        \"MyChoiceParameters\": " + myChoiceParameterArray.toString() + "      }    }  ]} ";
            ArrayList<String> existedRoomIds = new ArrayList<String>();
            existedRoomIds.add(headerZeroRoomId);
            JsonObject resultJson = JsonParser.parseString(myChoiceData).getAsJsonObject();
            for (PlayoutInfo oldPlayout : mergedPlayouts) {
                File oldPlayoutFile = new File(PlayoutUtils.getRFZipFullPath(oldPlayout));
                if (!oldPlayoutFile.exists()) continue;
                try {
                    String tempDir = CommonConstants.USER_ZIP_TEMP_LOCATION + UUID.randomUUID().toString() + File.separator;
                    ZipCommonUtils.unZipFiles(oldPlayoutFile, tempDir);
                    File oldJsonFile = new File(tempDir + "MyChoice" + File.separator + "MyChoice.json");
                    JsonObject oldMychoiceJson = JsonParser.parseString(FileUtils.readFileToString(oldJsonFile, StandardCharsets.UTF_8)).getAsJsonObject();
                    JsonArray array = oldMychoiceJson.getAsJsonArray("MyChoice");
                    for (int i = 0; i < array.size(); ++i) {
                        JsonObject obj = array.get(i).getAsJsonObject();
                        String roomId = obj.getAsJsonObject("CommandDetails").getAsJsonObject("OfflineServiceParameters").get("RoomID").getAsString();
                        if (existedRoomIds.contains(roomId)) continue;
                        resultJson.getAsJsonArray("MyChoice").add(obj);
                        existedRoomIds.add(roomId);
                    }
                    FileUtils.deleteDirectory(new File(tempDir));
                }
                catch (Exception ex) {
                    LOG.error(ex.getMessage(), ex);
                }
            }
            myChoiceData = new GsonBuilder().setPrettyPrinting().create().toJson(resultJson);
            String mychoiceDataFile = assembleDir + File.separator + "MyChoice.json";
            FileUtils.writeStringToFile(new File(mychoiceDataFile), myChoiceData, StandardCharsets.UTF_8);
            String identifierFilePath = assembleDir + File.separator + "MyChoice_Identifier.txt";
            FileUtils.writeStringToFile(new File(identifierFilePath), TpvDateUtils.getCurrentIndentifierFormatTime(), StandardCharsets.UTF_8);
            ZipCommonUtils.zipFiles(assembleDir, saveZipFilePath);
            boolean bl = true;
            return bl;
        }
        catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static boolean createWeatherForecastClonePackage(String saveZipFilePath) {
        Location location = null;
        try {
            location = LocationManager.getLocationFromFile();
        }
        catch (Exception ex) {
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
        try (SettingCreator settingCreator = new SettingCreator("TPM181HE_CloneData");){
            String rootAssembleDir = settingCreator.outputPath;
            String assembleDir = rootAssembleDir + "WeatherForecast";
            String weatherDataFile = assembleDir + File.separator + "WeatherForecast.txt";
            FileUtils.writeStringToFile(new File(weatherDataFile), forcastData, StandardCharsets.UTF_8);
            String identifierFilePath = assembleDir + File.separator + "WeatherForecast_Identifier.txt";
            FileUtils.writeStringToFile(new File(identifierFilePath), TpvDateUtils.getCurrentIndentifierFormatTime(), StandardCharsets.UTF_8);
            ZipCommonUtils.zipFiles(assembleDir, saveZipFilePath);
            boolean bl = true;
            return bl;
        }
        catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            return false;
        }
    }

    public void processSchedules(Schedule schedule) {
        String srcPath;
        File srcDirectoryStr;
        File directoryPath = new File(this.outputPath + File.separator + "Schedules" + File.separator);
        if (!directoryPath.exists()) {
            directoryPath.mkdirs();
        }
        if (!(srcDirectoryStr = new File(srcPath = CloneItemUtils.getSchedulesPackageDataPath(schedule.getId()) + "Schedules")).exists()) {
            srcDirectoryStr.mkdir();
        }
        String scheduleJsonPath = srcPath + File.separator + "Schedules.json";
        File scheduleJsonFile = new File(scheduleJsonPath);
        try {
            String content = schedule.getContent();
            FileUtils.write(scheduleJsonFile, (CharSequence)content, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        try {
            if (srcDirectoryStr.exists()) {
                FileUtils.copyDirectory(srcDirectoryStr, directoryPath);
                LOG.info("copy schedules from {} to {}", (Object)srcDirectoryStr.getAbsolutePath(), (Object)directoryPath.getAbsolutePath());
            } else {
                LOG.info("not exist schedules");
            }
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        this.processIdentifier("Schedules", schedule.getLastEdit());
    }

    public void processWelcome(int welcomeId) throws IOException {
        String welcomePath = WelcomeLogoUtils.getWelcomePath(String.valueOf(welcomeId));
        File welcomePathFile = new File(welcomePath);
        if (welcomePathFile.exists()) {
            Welcome welcome = JpaManager.getWelcomeManager().loadByKey(welcomeId);
            int type = welcome.getType();
            String path = this.outputPath;
            String identifier = WELCOME_LOGO;
            switch (type) {
                case 0: {
                    path = path + File.separator + WELCOME_LOGO + File.separator;
                    break;
                }
                case 1: {
                    String jsonFilePath = path + File.separator + "ProfessionalAppsData" + File.separator;
                    this.updateProfessionalAppsJSON(jsonFilePath, "org.droidtv.welcome", welcome.getLastEdit());
                    identifier = "ProfessionalAppsData";
                    path = path + File.separator + "ProfessionalAppsData" + File.separator + "org.droidtv.welcome" + File.separator;
                    break;
                }
                case 2: {
                    path = path + File.separator;
                    break;
                }
            }
            File targetFile = new File(path);
            targetFile.getParentFile().mkdirs();
            FileUtils.copyDirectory(welcomePathFile, targetFile);
            this.processIdentifier(identifier, welcome.getLastEdit());
        }
    }

    private void updateCloneItemStatus(com.tpvision.smartinstall.dao.core.Setting setting) {
        HashMap<String, Boolean> cloneItemAvailable = new HashMap<String, Boolean>();
        for (String cloneItem : CommonConstants.cloneItems) {
            if (!new File(this.outputPath + File.separator + cloneItem).exists()) continue;
            cloneItemAvailable.put(cloneItem, this.checkCloneItemAvailable(cloneItem));
        }
        this.updateCloneItemStatus(cloneItemAvailable, setting);
    }

    private void updateCloneItemStatus(Map<String, Boolean> cloneItemAvailable, com.tpvision.smartinstall.dao.core.Setting setting) {
        SettingManager settingManager = JpaManager.getSettingManager();
        StringBuilder cloneItemBuilder = new StringBuilder();
        cloneItemBuilder.append("{\"cloneItemStatus\":[");
        StringBuilder cloneItem = new StringBuilder();
        if (cloneItemAvailable.size() > 0) {
            for (Map.Entry<String, Boolean> mapEntry : cloneItemAvailable.entrySet()) {
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
                cloneItem.append("\"Status\":\"").append(status != false ? "YES" : "NO").append("\"");
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
        LOG.info("setting:{} updateCloneItemStatus:{} success", setting == null ? "" : Integer.valueOf(setting.getId()), (Object)cloneItemBuilder);
    }

    private void processChannel(String itemValues) {
        Gson gson = new Gson();
        SettingChannelBean setting = gson.fromJson(itemValues, SettingChannelBean.class);
        if ("v4".equalsIgnoreCase(setting.getChannelVersion())) {
            com.tpvision.smartinstall.xml.channel.v4.TvContents channelMap = this.getListOfV4Channel(itemValues);
            this.procesV4Channel(channelMap);
        } else if ("v5".equalsIgnoreCase(setting.getChannelVersion())) {
            TvContents channelMap = this.getListOfV5Channel(itemValues);
            this.procesV5Channel(channelMap);
        }
    }

    public void processFirmware(UpgSetting upgSetting) throws IOException {
        LOG.info("process Firmware,platform={}", (Object)upgSetting.getPlatform());
        try {
            this.preProcessChecksES("");
        }
        catch (IOException e) {
            LOG.error(e.getMessage());
        }
        String contextDownloadPath = CommonConstants.servletContextPath + "/Profile/UPG/" + upgSetting.getId();
        File targetUpg = new File(contextDownloadPath + "/MainFirmware.upg");
        if (targetUpg.exists()) {
            return;
        }
        String upgSrc = CommonConstants.UPLOADED_UPG_LOCATION + upgSetting.getName();
        File upgFile = new File(upgSrc + File.separator + "AutoRun.upg");
        FileUtils.copyFile(upgFile, targetUpg);
    }

    private void processThemeTv(String settingProcessPath) {
        String path = settingProcessPath + "ThemeTV" + File.separator;
        File themeDir = new File(path);
        if (themeDir.exists()) {
            File destDir = new File(this.outputPath + File.separator + "ThemeTV" + File.separator);
            destDir.mkdirs();
            try {
                FileUtils.copyDirectory(themeDir, destDir);
            }
            catch (IOException e) {
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
        File hotelInfoFile = new File(path);
        File destDir = null;
        if (hotelInfoFile.exists()) {
            destDir = new File(this.outputPath + "/Wallpaper/");
            if (this.platformId.indexOf(rootFolderName) > -1) {
                destDir = new File(this.outputPath + "/SmartInfoImages/");
            }
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            try {
                if (!hotelInfoFile.isDirectory()) {
                    FileUtils.copyFileToDirectory(hotelInfoFile, destDir);
                }
            }
            catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    private void processSmartPin(String settingProcessPath) {
        String path = settingProcessPath + "/SmartPin";
        File smartPin = new File(path);
        if (smartPin.exists()) {
            File destDir = new File(this.outputPath + File.separator + "SmartPin/");
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            try {
                FileUtils.copyDirectory(smartPin, destDir);
            }
            catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    private void processESHotelInfoMS2K14(int settingName, String imgPath) {
        File dir;
        List<String> imageList = this.getESImageList(settingName);
        if (!imageList.isEmpty() && (dir = new File(imgPath)).exists()) {
            File[] ff;
            for (File f : ff = dir.listFiles()) {
                try {
                    Files.delete(f.toPath());
                }
                catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
            int counter = 1;
            File destFile = null;
            for (String imageFileName : imageList) {
                File fileToCopy = new File(CommonConstants.HOTEL_INFO_ES_LOCATION + imageFileName);
                if (counter < 10) {
                    destFile = new File(dir + "/file_0" + counter + ".jpeg");
                } else if (counter <= 30) {
                    destFile = new File(dir + "/file_" + counter + ".jpeg");
                }
                ++counter;
                try {
                    FileUtils.copyFile(fileToCopy, destFile);
                }
                catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
    }

    private List<String> getESImageList(int settingName) {
        List<String> result = new ArrayList<String>();
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
                        String json = IOUtils.toString((InputStream)new ByteArrayInputStream(smartui.getValue()), "UTF-8");
                        HotelInfo hotelInfo = gson.fromJson(json, HotelInfo.class);
                        result = hotelInfo.getHotelinfo();
                    }
                }
            }
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    public void processUpgCreation(Map<String, String> parameters) throws IOException {
        String hasPlayOut;
        long startTime = System.currentTimeMillis();
        if ("TPN141HE_CloneData".equalsIgnoreCase(this.platformId) || "TPN142HE_CloneData".equalsIgnoreCase(this.platformId)) {
            this.processMS2K14(parameters);
        } else if ("TPN161HE_CloneData".equalsIgnoreCase(this.platformId)) {
            hasPlayOut = parameters.get("output");
            this.process2K16ES(hasPlayOut);
        } else {
            hasPlayOut = parameters.get("output");
            int clondId = TpvStringUtils.tryParseInt(parameters.get("id"), -1);
            String selectCloneType = parameters.get("select_clone_type");
            if (null == selectCloneType || "".equals(selectCloneType)) {
                selectCloneType = "Clone";
            }
            this.process2K16MS(hasPlayOut, clondId, selectCloneType);
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        LOG.info("processUpgCreation finished,used {} ms", (Object)totalTime);
    }

    public void processTSCreation(Map<String, String> parameters) {
        long startTime = System.currentTimeMillis();
        LOG.info("#### processTSCreation");
        this.cpCatalog(parameters);
        ConfigurationGenerator.generate4KTransmission(this.platformId);
        try {
            this.processUpgCreation(parameters);
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        LOG.info("processTSCreation finished,used {} ms", (Object)totalTime);
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
        }
        catch (NumberFormatException e) {
            LOG.error(e.getMessage(), e);
        }
        PSGCatalogGenerator.generateCatalog(this.platformId, versionFolder, this.playOutOptions(parameters));
        ConfigurationGenerator.generate(parameters);
    }

    private HashMap<String, String> playOutOptions(Map<String, String> parameters) {
        HashMap<String, String> hash = new HashMap<String, String>();
        hash.put("hasPlayOut", parameters.get("output"));
        hash.put("hasAll", parameters.get("rfall"));
        hash.put("hasThemeTV", parameters.get("rfth"));
        hash.put("hasWelcomeLogo", parameters.get("rfwl"));
        hash.put("hasSettings", parameters.get("rfse"));
        return hash;
    }

    private void processMS2K14(Map<String, String> parameters) {
        File inputFile = new File(CommonConstants.RF_PLAY_BACK_INPUT_LOCATION + File.separator + this.platformId + File.separator);
        this.copy2K14ScfgFile();
        if (this.iPrRFMode.equalsIgnoreCase("RF")) {
            this.creatMS2K14Upg(inputFile, parameters);
        } else {
            String id = parameters.get("id");
            this.creatMS2K14IP(new File(this.outputPath), Integer.parseInt(id));
        }
        FileUtils.deleteQuietly(inputFile);
        this.clean2K14UPGCreateTempFiles();
    }

    private void process2K16MS(String hasPlayOut, int cloneId, String selectCloneType) throws IOException {
        long startTime = System.currentTimeMillis();
        File inputFile = new File(this.outputPath);
        if ("RF".equalsIgnoreCase(this.iPrRFMode)) {
            this.creat2K16Upg(inputFile, hasPlayOut);
        } else {
            this.create2K16IPPackage(cloneId, selectCloneType);
        }
        File outputFile = new File(CommonConstants.RF_PLAY_BACK_OUTPUT_LOCATION + File.separator + this.platformId + File.separator);
        if (!outputFile.exists()) {
            outputFile.mkdirs();
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        LOG.info("process2K16MS finished,used {} ms", (Object)totalTime);
    }

    private void process2K16ES(String hasPlayOut) {
        long startTime = System.currentTimeMillis();
        File inputFile = new File(CommonConstants.RF_PLAY_BACK_INPUT_LOCATION + File.separator + this.platformId + File.separator);
        File outputFile = new File(CommonConstants.RF_PLAY_BACK_OUTPUT_LOCATION + File.separator + this.platformId + File.separator);
        if (this.iPrRFMode.equalsIgnoreCase("RF")) {
            this.creat2K16UpgES(inputFile, hasPlayOut);
        }
        if (!outputFile.exists()) {
            outputFile.mkdirs();
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        LOG.info("process2K16ES finished,used {} ms", (Object)totalTime);
    }

    private void create2K16IPPackage(int cloneId, String selectCloneType) {
        if (cloneId <= 0) {
            LOG.warn("cloneid is null or empty:{}", (Object)cloneId);
            return;
        }
        CloneItemUtils.CloneItemInfo info = CloneItemUtils.getCloneItemInfo(selectCloneType, cloneId);
        if (info.getItemType() == CommonConstants.CloneItemType.Firmware) {
            return;
        }
        File clonePathFile = new File(info.getCachedPath());
        String destPath = clonePathFile.getAbsolutePath() + File.separator;
        if (!clonePathFile.exists()) {
            clonePathFile.mkdirs();
        }
        this.zipClonePacketAccordingType(cloneId, destPath, selectCloneType);
        this.setClonePath(info.getClonePath());
        LOG.info("files generated at: {}", (Object)destPath);
    }

    private void creatMS2K14IP(File inputFile, int cloneId) {
        String tmpClonePath = "Clone/" + cloneId + "-" + UUID.randomUUID() + "/";
        File destDir = new File(CommonConstants.servletContextPath + "/Profile/Clone/" + tmpClonePath);
        destDir.mkdirs();
        File esUpgInput = new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT);
        try {
            FileUtils.copyDirectory(inputFile, esUpgInput);
        }
        catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
        LinkedHashMap<String, Object> list = new LinkedHashMap<String, Object>();
        String rootFolderName = PlatformUtils.getRootFolderName(this.platformId);
        boolean isSSBBinFileExist = new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_SSB.BIN").exists();
        boolean isSSBBinIdentifierExist = new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_SSB.xml").exists();
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
        boolean isChannelFileExist = new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_CHTB.BIN").exists();
        boolean isChannelIdentiferExist = new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_CHTB.xml").exists();
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
        if (new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_WelcomeLogo.png").exists()) {
            CommandLine cmd3 = this.get2K14DwpackCmd();
            cmd3.addArgument("5");
            cmd3.addArgument(rootFolderName + "_WelcomeLogo.png");
            list.put(WELCOME_LOGO, cmd3);
        }
        if (new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_SmartInfoImages.zip").exists()) {
            CommandLine cmd4 = this.get2K14DwpackCmd();
            cmd4.addArgument("6");
            cmd4.addArgument(rootFolderName + "_SmartInfoImages.zip");
            list.put("SmartInfoImages", cmd4);
        }
        if (new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_SmartInfoPages.zip").exists()) {
            CommandLine cmd5 = this.get2K14DwpackCmd();
            cmd5.addArgument("7");
            cmd5.addArgument(rootFolderName + "_SmartInfoPages.zip");
            list.put("SmartInfoPages", cmd5);
        }
        if (new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_SystemUIBackup.zip").exists()) {
            CommandLine cmd6 = this.get2K14DwpackCmd();
            cmd6.addArgument("8");
            cmd6.addArgument(rootFolderName + "_SystemUIBackup.zip");
            list.put("CustomDashboardFallback", cmd6);
        }
        if (new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_MiscSettings.zip").exists()) {
            CommandLine cmd7 = this.get2K14DwpackCmd();
            cmd7.addArgument("9");
            cmd7.addArgument(rootFolderName + "_MiscSettings.zip");
            list.put("MiscSettings", cmd7);
        }
        if (new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_VSecureKey.zip").exists()) {
            CommandLine cmd8 = this.get2K14DwpackCmd();
            cmd8.addArgument("10");
            cmd8.addArgument(rootFolderName + "_VSecureKey.zip");
            list.put("VSecureKey", cmd8);
        }
        if (new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_CombineMedia.zip").exists()) {
            CommandLine cmd9 = this.get2K14DwpackCmd();
            cmd9.addArgument("11");
            cmd9.addArgument(rootFolderName + "_CombineMedia.zip");
            list.put("CombineMedia", cmd9);
        }
        for (Map.Entry entry : list.entrySet()) {
            LOG.debug("generate item:{} for 2k14", entry.getKey());
            DefaultExecutor executor = new DefaultExecutor();
            int[] values = new int[]{0, 1};
            executor.setExitValues(values);
            executor.setWorkingDirectory(new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
            executor.setStreamHandler(streamHandler);
            try {
                executor.execute((CommandLine)entry.getValue());
            }
            catch (Exception e) {
                LOG.error(e.getMessage() + ":" + (String)entry.getKey(), e);
                continue;
            }
            File source = new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + "Autorun.upg");
            File destination = new File(destDir + "/" + (String)entry.getKey() + ".upg");
            if (source.exists()) {
                try {
                    FileUtils.copyFile(source, destination);
                }
                catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
            String[] str = ((CommandLine)entry.getValue()).toStrings();
            String[] part = str[str.length - 1].split("\\.");
            String name = part[0] + "_Identifier.txt";
            source = new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + name);
            destination = new File(destDir + File.separator + name);
            try {
                if (null == source || !source.exists()) continue;
                FileUtils.copyFile(source, destination);
            }
            catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        File source = new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + rootFolderName + "_SSB_Identifier.txt");
        File file = new File(destDir + "/identifier.txt");
        try {
            FileUtils.copyFile(source, file);
        }
        catch (IOException e) {
            LOG.warn(e.getMessage(), e);
        }
        this.setClonePath(tmpClonePath);
        LOG.info("files generated {} ", (Object)destDir);
    }

    private CommandLine get2K14DwpackCmd() {
        CommandLine cmd = CommandLine.parse("cmd /c HTV_DWPack_1401.exe");
        cmd.addArgument("0");
        cmd.addArgument("PHILIPS_2K14_EU_HTV");
        return cmd;
    }

    private void clean2K14UPGCreateTempFiles() {
        ArrayList<String> es = new ArrayList<String>();
        es.add("HTV_DWPack_1401.exe");
        es.add("GenerateAutorun.bat");
        es.add("libeay32.dll");
        es.add("scfg.xml");
        es.add("Key");
        es.add("Tool_backup");
        es.add("7-Zip");
        es.add("Configuration");
        File inputPath = new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT);
        for (File f : inputPath.listFiles()) {
            if (es.contains(f.getName())) continue;
            FileUtils.deleteQuietly(f);
        }
    }

    private void creatMS2K14Upg(File inputFile, Map<String, String> parameters) {
        File esUpgInput = new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT);
        try {
            FileUtils.copyDirectory(inputFile, esUpgInput);
        }
        catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
        CommandLine cmdLine = CommandLine.parse("cmd /c HTV_DWPack_1401.exe");
        UpgCreator.UpgCreationCommandArgument uca = UpgCreator.getCommandArument(parameters);
        cmdLine = new UpgCreator().build2K14MSCmd(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT, uca, cmdLine, this.platformId);
        DefaultExecutor executor = new DefaultExecutor();
        int[] values = new int[]{0, 1};
        executor.setExitValues(values);
        executor.setWorkingDirectory(new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
        executor.setStreamHandler(streamHandler);
        try {
            executor.execute(cmdLine);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            LOG.error("Error creating UPG");
            LOG.info(new String(outputStream.toByteArray()));
            return;
        }
        File rfEsUpgFile = new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + "Autorun.upg");
        File rfEsUpgDestinationDir = null;
        File rfEsUpgFileInDestinationDir = null;
        File nameChangedUpgFile = null;
        if (this.platformId.contains("TPN141HE")) {
            rfEsUpgDestinationDir = new File(CommonConstants.RF_PLAY_BACK_EXE_INPUT_LOC_MS_2K14_INPUT);
            rfEsUpgFileInDestinationDir = new File(CommonConstants.RF_PLAY_BACK_EXE_INPUT_LOC_MS_2K14_INPUT + "Autorun.upg");
            nameChangedUpgFile = new File(CommonConstants.RF_PLAY_BACK_EXE_INPUT_LOC_MS_2K14_INPUT + "Autorun_RF_Cloning.upg");
        } else if (this.platformId.contains("TPN142HE")) {
            rfEsUpgDestinationDir = new File(CommonConstants.RF_PLAY_BACK_EXE_INPUT_LOC_ES_2K14_INPUT);
            rfEsUpgFileInDestinationDir = new File(CommonConstants.RF_PLAY_BACK_EXE_INPUT_LOC_ES_2K14_INPUT + "Autorun.upg");
            nameChangedUpgFile = new File(CommonConstants.RF_PLAY_BACK_EXE_INPUT_LOC_ES_2K14_INPUT + "Autorun_RF_Cloning.upg");
        }
        try {
            FileUtils.moveFileToDirectory(rfEsUpgFile, rfEsUpgDestinationDir, true);
            if (null != nameChangedUpgFile && nameChangedUpgFile.exists()) {
                FileUtils.forceDelete(nameChangedUpgFile);
            }
            FileUtils.moveFile(rfEsUpgFileInDestinationDir, nameChangedUpgFile);
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void creat2K16UpgES(File inputFile, String hasPlayOut) {
        TpvFileUtils.clearFiles(CommonConstants.ES2K16_UPG_CREATOR_LOCATION_INPUT);
        String upgInputFilePath = CommonConstants.ES2K16_UPG_CREATOR_LOCATION_INPUT + "MasterCloneData";
        String generate2K16UpgPath = CommonConstants.RF_PLAY_BACK_EXE_INPUT_LOC_ES_2K16_INPUT;
        File esUpgInput = new File(upgInputFilePath);
        try {
            if (esUpgInput.exists()) {
                FileUtils.forceDelete(esUpgInput);
            }
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        esUpgInput.mkdirs();
        try {
            FileUtils.copyDirectory(inputFile, esUpgInput);
        }
        catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
        File destDir = new File(generate2K16UpgPath);
        try {
            if (destDir.exists()) {
                FileUtils.forceDelete(destDir);
            }
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        destDir.mkdirs();
        File[] fileList = esUpgInput.listFiles();
        ZipCommonUtils.zipFiles(Arrays.asList(fileList), upgInputFilePath + ".zip");
        File source = new File(upgInputFilePath + ".zip");
        File destination = new File(destDir + "/MasterCloneData.zip");
        try {
            destination.createNewFile();
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        try {
            FileUtils.copyFile(source, destination);
        }
        catch (IOException e1) {
            LOG.error(e1.getMessage(), e1);
        }
        LOG.info("Play_RF_2K16(3011 ES) files generated {}", (Object)destDir);
    }

    private void creat2K16Upg(File inputFile, String hasPlayOut) throws IOException {
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
            File oadUpg = new File(sourceUpgPath + "/oad.upg");
            File oadUpgDestDir = new File(generate2K16UpgPath + "oad.upg");
            if (oadUpg.exists() && !oadUpg.isDirectory()) {
                FileUtils.copyFileToDirectory(oadUpg, new File(generate2K16UpgPath));
            } else {
                ZipCommonUtils.gen7ZipForOAD(generate2K16UpgPath, "oad.upg", sourceUpgPath, "*.upg");
                FileUtils.copyFileToDirectory(oadUpgDestDir, new File(sourceUpgPath));
            }
            ZipCommonUtils.gen7ZipForOAD(generate2K16UpgPath, "oad.upg", generate2K16UpgPath, "*.zip");
        }
        LOG.info("Play_RF(2K16) files generated {}", (Object)generate2K16UpgPath);
    }

    private void procesV4Channel(com.tpvision.smartinstall.xml.channel.v4.TvContents listOfChannel) {
        if (null == listOfChannel) {
            return;
        }
        String fileName = this.outputPath + this.platform.getChannel().getFolderName() + this.platform.getChannel().getFileName();
        try {
            JAXBContext context = JAXBContext.newInstance("com.tpvision.smartinstall.xml.channel.v4");
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
            StringWriter sw = new StringWriter();
            marshaller.marshal((Object)listOfChannel, sw);
            this.writeXML(fileName, sw);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void procesV5Channel(TvContents listOfChannel) {
        if (null == listOfChannel) {
            return;
        }
        String fileName = this.outputPath + this.platform.getChannel().getFolderName();
        String[] folders = this.platform.getChannel().getFileName().split("/");
        File rootFolder = new File(fileName + folders[0]);
        if (!rootFolder.exists()) {
            rootFolder.mkdirs();
        }
        fileName = fileName + this.platform.getChannel().getFileName();
        try {
            JAXBContext context = JAXBContext.newInstance("com.tpvision.smartinstall.xml.channel.v5");
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
            StringWriter sw = new StringWriter();
            marshaller.marshal((Object)listOfChannel, sw);
            this.writeXML(fileName, sw);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void writeXML(String fileName, StringWriter sw) {
        String str = sw.toString().replace("&amp;apos;", "&apos;");
        try {
            File f = new File(fileName);
            if (!f.exists()) {
                f.createNewFile();
            }
            try (FileOutputStream fos = new FileOutputStream(f);){
                OutputStreamWriter out = new OutputStreamWriter((OutputStream)fos, "UTF8");
                out.write(str);
                ((Writer)out).flush();
            }
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private com.tpvision.smartinstall.xml.channel.v4.TvContents getListOfV4Channel(String confs) {
        com.tpvision.smartinstall.xml.channel.v4.TvContents ret = new com.tpvision.smartinstall.xml.channel.v4.TvContents();
        Gson gson = new Gson();
        try {
            SettingChannelBean settings = gson.fromJson(confs, SettingChannelBean.class);
            if (null != settings && settings.getV4Channel() != null) {
                ret = settings.getV4Channel();
            }
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return ret;
    }

    private TvContents getListOfV5Channel(String confs) {
        TvContents ret = new TvContents();
        Gson gson = new Gson();
        try {
            SettingChannelBean settings = gson.fromJson(confs, SettingChannelBean.class);
            if (null != settings && settings.getV5Channel() != null) {
                ret = settings.getV5Channel();
            }
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return ret;
    }

    private void preProcessChecksES(String cleaned) throws IOException {
        ArrayList<String> ES = new ArrayList<String>();
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
        File inputPath = new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT);
        if (inputPath.exists() && (null == cleaned || null != cleaned && !cleaned.equalsIgnoreCase("true"))) {
            for (File f : inputPath.listFiles()) {
                boolean itemFound = false;
                for (String item : ES) {
                    if (f.getName().indexOf(item) <= -1) continue;
                    itemFound = true;
                    break;
                }
                if (itemFound) continue;
                try {
                    if (f.isDirectory()) {
                        FileUtils.deleteDirectory(f);
                        continue;
                    }
                    FileUtils.deleteQuietly(f);
                }
                catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
        String[] inputfolderList = new String[]{CommonConstants.RF_PLAY_BACK_EXE_INPUT_LOC_MS_2K14_INPUT, CommonConstants.RF_PLAY_BACK_EXE_INPUT_LOC_MS_2K15_INPUT, CommonConstants.RF_PLAY_BACK_EXE_INPUT_LOC_ES_2K14_INPUT, CommonConstants.RF_PLAY_BACK_EXE_INPUT_LOC_ES_2K16_INPUT};
        for (int i = 0; i < inputfolderList.length; ++i) {
            File f = new File(inputfolderList[i]);
            if (!f.exists()) continue;
            try {
                FileUtils.deleteDirectory(f);
                continue;
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
        this.copy2K14ScfgFile();
    }

    private void copy2K14ScfgFile() {
        File srcFile = null;
        if (this.platformId.contains("TPN141HE")) {
            srcFile = new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + "Configuration/PHILIPS_2K14_EU_HTV/scfg.xml");
        } else if (this.platformId.contains("TPN142HE")) {
            srcFile = new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + "Configuration/PHILIPS_2K14_EU_ES_HTV/scfg.xml");
        }
        try {
            if (null != srcFile && srcFile.exists()) {
                FileUtils.copyFile(srcFile, new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT + "/scfg.xml"));
            }
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void cleanAssemblyDir() {
        if (this.outputPath == null) {
            return;
        }
        File f = new File(this.outputPath);
        if (f.getName().startsWith("CLONE_")) {
            LOG.info("clone package is cached:{}", (Object)f.getName());
            return;
        }
        FileUtils.deleteQuietly(f);
    }

    private void makeDirs(String finalPath) {
        File f = new File(finalPath);
        f.mkdirs();
    }

    private void processUnchangedFiles(String settingProcessPath) {
        for (com.tpvision.smartinstall.xml.File f : this.platform.getUnchangedFiles().getFile()) {
            String fromFilePath = settingProcessPath + f.getPath();
            File inputFile = new File(fromFilePath);
            if (!inputFile.exists()) {
                fromFilePath = CommonConstants.RESOURCE_LOCATION + this.platformId + File.separator + f.getPath();
                inputFile = new File(fromFilePath);
            }
            if (!inputFile.exists()) continue;
            String toFilePath = this.outputPath + File.separator + f.getPath();
            File destFile = new File(toFilePath);
            try {
                destFile.getParentFile().mkdirs();
                FileUtils.copyFile(inputFile, destFile);
            }
            catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    private String getIdentifierFilePath(String itemName) {
        List<com.tpvision.smartinstall.xml.File> crcFile = this.platform.getCrcFiles().getFile();
        String itemName2 = PlatformUtils.getIdentifierName(this.platformId, itemName);
        for (com.tpvision.smartinstall.xml.File f : crcFile) {
            String path = f.getPath();
            if (path.indexOf(itemName) < 0 && path.indexOf(itemName2) < 0) continue;
            return this.outputPath + path;
        }
        String identifier = this.outputPath + itemName + File.separator + itemName + "_Identifier.txt";
        LOG.warn("identifier not config in config.xml,using default value:{}", (Object)identifier);
        return identifier;
    }

    private void processIdentifier(String itemName, String dateStr) {
        LOG.info("process identifier for:{} ", (Object)itemName);
        File fileIdentifier = new File(this.getIdentifierFilePath(itemName));
        String identifier = dateStr;
        if (dateStr == null) {
            identifier = TpvDateUtils.getCurrentIndentifierFormatTime();
        }
        try {
            FileUtils.writeStringToFile(fileIdentifier, identifier, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void processCrc(String preProcessPath, com.tpvision.smartinstall.dao.core.Setting setting, Date date) {
        List<com.tpvision.smartinstall.xml.File> crcFile = this.platform.getCrcFiles().getFile();
        for (com.tpvision.smartinstall.xml.File f : crcFile) {
            String srcFolder;
            String cloneSubFolderName = f.getPath().substring(0, f.getPath().indexOf(f.getName()));
            if ("SmartInfoBrowser/".equalsIgnoreCase(cloneSubFolderName) || "SmartInfoPages/".equalsIgnoreCase(cloneSubFolderName) || !"".equalsIgnoreCase(cloneSubFolderName) && !new File(srcFolder = preProcessPath + File.separator + cloneSubFolderName).exists()) continue;
            String srcPath = preProcessPath + f.getPath();
            File processIdentifierFile = new File(srcPath);
            if (!processIdentifierFile.exists()) {
                try {
                    processIdentifierFile.createNewFile();
                }
                catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
            try (FileOutputStream fos = new FileOutputStream(processIdentifierFile);){
                if (null == date) {
                    date = new Date();
                }
                if (f.getPath().contains("ChannelList") || f.getPath().contains("CHTB")) {
                    String cpLastEdit = "0/0/0:0:0";
                    ChannelPackageManager cpm = JpaManager.getChannelPackageManager();
                    ChannelPackage cp = cpm.loadByKey(setting.getChannelPackageId());
                    if (null != cp) {
                        cpLastEdit = cp.getLastEdit();
                    }
                    IOUtils.write(cpLastEdit, (OutputStream)fos, StandardCharsets.UTF_8);
                } else {
                    IOUtils.write(TpvDateUtils.getIdentifierFormatTime(date), (OutputStream)fos, StandardCharsets.UTF_8);
                }
            }
            catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
            String assemblyPath = "";
            assemblyPath = "".equalsIgnoreCase(cloneSubFolderName) ? this.outputPath + File.separator : this.outputPath + File.separator + cloneSubFolderName + File.separator;
            File assemblySubFile = new File(assemblyPath);
            if (!assemblySubFile.exists()) {
                assemblySubFile.mkdirs();
            }
            String assemblyIdentifierPath = assemblyPath + f.getName();
            try {
                File assemblyIdentifierFile = new File(assemblyIdentifierPath);
                if (!assemblyIdentifierFile.exists()) {
                    assemblyIdentifierFile.createNewFile();
                }
                FileUtils.copyFile(processIdentifierFile, assemblyIdentifierFile);
            }
            catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    private void processSettings(List<Setting> listOfSettings) {
        LOG.info("SettingCreator.java -> processTVSettings -> platform {} ", (Object)this.platformId);
        if (this.platform == null) {
            LOG.info("SettingCreator.java -> processSettings -> None platform is available ! ");
            return;
        }
        if (this.platform.getCloneRootFolderName().startsWith("TPN141")) {
            this.processSettingMS2K14(listOfSettings);
        } else if (this.platform.getCloneRootFolderName().startsWith("TPN142")) {
            this.processSettingMS2K14ForTpn142(listOfSettings);
        } else {
            this.processSetting2K16(listOfSettings);
        }
        List<com.tpvision.smartinstall.xml.File> settingFiles = this.platform.getSettingFiles().getFile();
        List<Setting> configuredSettings = this.platform.getSettings().getSetting();
        HashMap fileToSettingMap = new HashMap();
        for (com.tpvision.smartinstall.xml.File f : settingFiles) {
            fileToSettingMap.put(f, new ArrayList());
        }
        block12: for (Setting s : listOfSettings) {
            if (!this.hasSettingConfigured(s, configuredSettings)) continue;
            com.tpvision.smartinstall.xml.File fileKey = null;
            for (com.tpvision.smartinstall.xml.File fileForKey : settingFiles) {
                if ("BdsLastStatus.txt".equalsIgnoreCase(fileForKey.getName())) {
                    ((List)fileToSettingMap.get(fileForKey)).add(s);
                    continue block12;
                }
                if (null == s || !s.getRefFile().equalsIgnoreCase(fileForKey.getName())) continue;
                fileKey = fileForKey;
                if (!this.platormHasSetting(s)) continue block12;
                ((List)fileToSettingMap.get(fileKey)).add(s);
                continue block12;
            }
        }
        String rootPath = this.platform.getId();
        for (com.tpvision.smartinstall.xml.File key : fileToSettingMap.keySet()) {
            int lastIndex = key.getPath().indexOf(key.getName());
            if (lastIndex < 0) {
                lastIndex = 0;
            }
            String pathToAdd = key.getPath().substring(0, lastIndex);
            String path = rootPath + File.separator + pathToAdd;
            path = this.outputPath + key.getName();
            File file = new File(path);
            StringBuilder sbuf = new StringBuilder();
            if (key.getName().equalsIgnoreCase("BdsLastStatus.txt")) {
                sbuf.append("<swver>" + this.platform.getSwver() + "</swver>").append(IOUtils.LINE_SEPARATOR_WINDOWS);
            } else {
                sbuf.append("<swver>" + this.platform.getSwver() + "</swver>").append(IOUtils.LINE_SEPARATOR_UNIX);
            }
            for (Setting s : (List)fileToSettingMap.get(key)) {
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
                    continue;
                }
                sbuf.append("<lastvalue>" + lastValue + "</lastvalue>").append(IOUtils.LINE_SEPARATOR_UNIX);
            }
            try {
                FileOutputStream fos = new FileOutputStream(file);
                Throwable throwable = null;
                try {
                    IOUtils.write(sbuf.toString(), (OutputStream)fos, StandardCharsets.UTF_8);
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (fos == null) continue;
                    if (throwable != null) {
                        try {
                            fos.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                        continue;
                    }
                    fos.close();
                }
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    private String getPlatformXaddr(Setting s) {
        String ret = null;
        for (Setting setting : this.platform.getSettings().getSetting()) {
            if (!setting.getItem().equalsIgnoreCase(s.getItem()) || !setting.getPosition().equals(s.getPosition())) continue;
            ret = setting.getXaddr();
            break;
        }
        return ret;
    }

    private void processSettingMS2K14ForTpn142(List<Setting> listOfSettings) {
        HotelModelSettingsForTpn142 hms = new HotelModelSettingsForTpn142();
        for (Setting s : listOfSettings) {
            if ("easylink".equalsIgnoreCase(s.getItem())) {
                s.setXaddr("MS2K14");
            }
            if (!"MS2K14".equalsIgnoreCase(s.getXaddr())) continue;
            String sName = s.getItem();
            String value = s.getLastValue() != null ? s.getLastValue() : "";
            value = " " + value.trim() + " ";
            if (value.equalsIgnoreCase(" null ") && sName.equalsIgnoreCase("CloneMultiRC")) continue;
            if ("SwitchOnSrc".equalsIgnoreCase(sName)) {
                hms.setSwitchOnSrc(value);
                continue;
            }
            if ("SwitchOnChn".equalsIgnoreCase(sName)) {
                if (!hms.getSwitchOnSrc().trim().equalsIgnoreCase("TV")) continue;
                hms.setSwitchOnChn(value);
                continue;
            }
            if ("SwitchOnVol".equalsIgnoreCase(sName)) {
                hms.setSwitchOnVol(value);
                continue;
            }
            if ("MaximumVol".equalsIgnoreCase(sName)) {
                hms.setMaximumVol(value);
                continue;
            }
            if ("SwitchOnFeature".equalsIgnoreCase(sName)) {
                hms.setSwitchOnFeature(value);
                continue;
            }
            if ("SwitchOnPicFmt".equalsIgnoreCase(sName)) {
                hms.setSwitchOnPicFmt(value);
                continue;
            }
            if ("PowerOn".equalsIgnoreCase(sName)) {
                hms.setPowerOn(value);
                continue;
            }
            if ("LowPowerStandby".equalsIgnoreCase(sName)) {
                hms.setLowPowerStandby(value);
                continue;
            }
            if ("SmartPower".equalsIgnoreCase(sName)) {
                hms.setSmartPower(value);
                continue;
            }
            if ("RebootEveryDay".equalsIgnoreCase(sName)) {
                hms.setRebootEveryDay(value);
                continue;
            }
            if ("DisplayWelcomeMsg".equalsIgnoreCase(sName)) {
                hms.setDisplayWelcomeMsg(value);
                continue;
            }
            if ("WelcomeMsgLine1".equalsIgnoreCase(sName)) {
                hms.setWelcomeMsgLine1(value);
                continue;
            }
            if ("WelcomeMsgLine2".equalsIgnoreCase(sName)) {
                hms.setWelcomeMsgLine2(value);
                continue;
            }
            if ("WelcomeMsgTimeOut".equalsIgnoreCase(sName)) {
                hms.setWelcomeMsgTimeOut(value);
                continue;
            }
            if ("DisplayLogo".equalsIgnoreCase(sName)) {
                hms.setDisplayLogo(value);
                continue;
            }
            if ("SmartInfo".equalsIgnoreCase(sName)) {
                hms.setSmartInfo(value);
                continue;
            }
            if ("SmartInfoIconLabel".equalsIgnoreCase(sName)) {
                hms.setSmartInfoIconLabel(value);
                continue;
            }
            if ("KBLock".equalsIgnoreCase(sName)) {
                hms.setKbLock(value);
                continue;
            }
            if ("RCLock".equalsIgnoreCase(sName)) {
                hms.setRcLock(value);
                continue;
            }
            if ("OSDDisplay".equalsIgnoreCase(sName)) {
                hms.setOsdDisplay(value);
                continue;
            }
            if ("HighSecurity".equalsIgnoreCase(sName)) {
                hms.setHighSecurity(value);
                continue;
            }
            if ("AutoScart".equalsIgnoreCase(sName)) {
                hms.setAutoScart(value);
                continue;
            }
            if ("USBBreakIn".equalsIgnoreCase(sName)) {
                hms.setUsbBreakIn(value);
                continue;
            }
            if ("EnableUSB".equalsIgnoreCase(sName)) {
                hms.setEnableUSB(value);
                continue;
            }
            if ("SXPBaudRate".equalsIgnoreCase(sName)) {
                hms.setSxpBaudRate(value);
                continue;
            }
            if ("EnableTeletext".equalsIgnoreCase(sName)) {
                hms.setEnableTeletext(value);
                continue;
            }
            if ("EnableMHEG".equalsIgnoreCase(sName)) {
                hms.setEnableMHEG(value);
                continue;
            }
            if ("EnableEPG".equalsIgnoreCase(sName)) {
                hms.setEnableEPG(value);
                continue;
            }
            if ("EnableSubtitles".equalsIgnoreCase(sName)) {
                hms.setEnableSubtitles(value);
                continue;
            }
            if ("SubtitleOnStartup".equalsIgnoreCase(sName)) {
                hms.setSubtitleOnStartup(value);
                continue;
            }
            if ("BlueMute".equalsIgnoreCase(sName)) {
                hms.setBlueMute(value);
                continue;
            }
            if ("EnableCISlot".equalsIgnoreCase(sName)) {
                hms.setEnableCISlot(value);
                continue;
            }
            if ("ScrambledProgramOSD".equalsIgnoreCase(sName)) {
                hms.setScrambledProgramOSD(value);
                continue;
            }
            if ("EasylinkBreakIn".equalsIgnoreCase(sName)) {
                hms.setEasylinkBreakIn(value);
                continue;
            }
            if ("EasylinkControl".equalsIgnoreCase(sName)) {
                hms.setEasylinkControl(value);
                continue;
            }
            if ("DigitTimeout".equalsIgnoreCase(sName)) {
                hms.setDigitTimeout(value);
                continue;
            }
            if ("SelectableAV".equalsIgnoreCase(sName)) {
                hms.setSelectableAV(value);
                continue;
            }
            if ("WatchTV".equalsIgnoreCase(sName)) {
                hms.setWatchTV(value);
                continue;
            }
            if ("ExternalClk".equalsIgnoreCase(sName)) {
                hms.setExternalClk(value);
                continue;
            }
            if ("ClkBrighDimlight".equalsIgnoreCase(sName)) {
                hms.setClkBrighDimlight(value);
                continue;
            }
            if ("ClkBrighIntenselight".equalsIgnoreCase(sName)) {
                hms.setClkBrighIntenselight(value);
                continue;
            }
            if ("ClkLightSensor".equalsIgnoreCase(sName)) {
                hms.setClkLightSensor(value);
                continue;
            }
            if ("TimeDownload".equalsIgnoreCase(sName)) {
                hms.setTimeDownload(value);
                continue;
            }
            if ("TimeSetting".equalsIgnoreCase(sName)) {
                hms.setTimeSetting(value);
                continue;
            }
            if ("ClkDownloadProgram".equalsIgnoreCase(sName)) {
                hms.setClkDownloadProgram(value);
                continue;
            }
            if ("ClkDownloadCountry".equalsIgnoreCase(sName)) {
                hms.setClkDownloadCountry(value);
                continue;
            }
            if ("ClkTimeZone".equalsIgnoreCase(sName)) {
                hms.setClkTimeZone(value);
                continue;
            }
            if ("DaylightSaving".equalsIgnoreCase(sName)) {
                hms.setDaylightSaving(value);
                continue;
            }
            if ("ClkTimeOffset".equalsIgnoreCase(sName)) {
                hms.setClkTimeOffset(value);
                continue;
            }
            if ("ReferenceDate".equalsIgnoreCase(sName)) {
                hms.setReferenceDate(value);
                continue;
            }
            if ("ReferenceTime".equalsIgnoreCase(sName)) {
                hms.setReferenceTime(value);
                continue;
            }
            if ("MainSpkrEnable".equalsIgnoreCase(sName)) {
                hms.setMainSpkrEnable(value);
                continue;
            }
            if ("IndMainSpkrMute".equalsIgnoreCase(sName)) {
                hms.setIndMainSpkrMute(value);
                continue;
            }
            if ("DefMainSpkrVol".equalsIgnoreCase(sName)) {
                hms.setDefMainSpkrVol(value);
                continue;
            }
            if ("AutoChnUpdate".equalsIgnoreCase(sName)) {
                hms.setAutoChnUpdate(value);
                continue;
            }
            if ("AutoSwUpdate".equalsIgnoreCase(sName)) {
                hms.setAutoSwUpdate(value);
                continue;
            }
            if ("SkipScrambled".equalsIgnoreCase(sName)) {
                hms.setSkipScrambled(value);
                continue;
            }
            if ("MultiRC".equalsIgnoreCase(sName)) {
                hms.setMultiRC(value);
                continue;
            }
            if ("MyChoice".equalsIgnoreCase(sName)) {
                hms.setMyChoice(value);
                continue;
            }
            if ("AskForPIN".equalsIgnoreCase(sName)) {
                hms.setAskForPIN(value);
                continue;
            }
            if ("SmartPay".equalsIgnoreCase(sName)) {
                hms.setSmartPay(value);
                continue;
            }
            if ("AV".equalsIgnoreCase(sName)) {
                hms.setAV(value);
                continue;
            }
            if ("VsecOverRFEnable".equalsIgnoreCase(sName)) {
                hms.setVsecOverRFEnable(value);
                continue;
            }
            if ("EraseKeyOption".equalsIgnoreCase(sName)) {
                hms.setEraseKeyOption(value);
                continue;
            }
            if ("VsecFrequency".equalsIgnoreCase(sName)) {
                hms.setVsecFrequency(value);
                continue;
            }
            if ("VsecMedium".equalsIgnoreCase(sName)) {
                hms.setVsecMedium(value);
                continue;
            }
            if ("VsecSymbolRate".equalsIgnoreCase(sName)) {
                hms.setVsecSymbolRate(value);
                continue;
            }
            if ("RFCLFrequency".equalsIgnoreCase(sName)) {
                hms.setRfclFrequency(value);
                continue;
            }
            if ("RFCLMedium".equalsIgnoreCase(sName)) {
                hms.setRfclMedium(value);
                continue;
            }
            if ("RFCLSymbolRate".equalsIgnoreCase(sName)) {
                hms.setRfclSymbolRate(value);
                continue;
            }
            if ("UpgradeMode".equalsIgnoreCase(sName)) {
                hms.setUpgradeMode(value);
                continue;
            }
            if ("AutoUpgrade".equalsIgnoreCase(sName)) {
                hms.setAutoUpgrade(value);
                continue;
            }
            if ("InstallationMode".equalsIgnoreCase(sName)) {
                hms.setInstallationMode(value);
                continue;
            }
            if (!"CloneMultiRC".equalsIgnoreCase(sName)) continue;
            hms.setCloneMultiRC(value);
        }
        try {
            JAXBContext context = JAXBContext.newInstance(HotelModelSettingsForTpn142.class);
            File settingFile = this.generateSettingXMLFile(PlatformUtils.getRootFolderName(this.platformId) + "_SSB.xml", null);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
            marshaller.marshal((Object)hms, settingFile);
        }
        catch (JAXBException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void processSettingMS2K14(List<Setting> listOfSettings) {
        HotelModeSettings hms = new HotelModeSettings();
        for (Setting s : listOfSettings) {
            if ("easylink".equalsIgnoreCase(s.getItem())) {
                s.setXaddr("MS2K14");
            }
            if (!"MS2K14".equalsIgnoreCase(s.getXaddr())) continue;
            String sName = s.getItem();
            String value = s.getLastValue() != null ? s.getLastValue() : "";
            value = " " + value.trim() + " ";
            if (value.equalsIgnoreCase(" null ") && sName.equalsIgnoreCase("CloneMultiRC")) continue;
            if ("Dashboard".equalsIgnoreCase(sName)) {
                hms.setDashboard(value);
                continue;
            }
            if ("SwitchOnSrc".equalsIgnoreCase(sName)) {
                hms.setSwitchOnSrc(value);
                continue;
            }
            if ("SwitchOnChn".equalsIgnoreCase(sName)) {
                if (!hms.getSwitchOnSrc().trim().equalsIgnoreCase("TV")) continue;
                hms.setSwitchOnChn(value);
                continue;
            }
            if ("SwitchOnVol".equalsIgnoreCase(sName)) {
                hms.setSwitchOnVol(value);
                continue;
            }
            if ("MaximumVol".equalsIgnoreCase(sName)) {
                hms.setMaximumVol(value);
                continue;
            }
            if ("SwitchOnFeature".equalsIgnoreCase(sName)) {
                hms.setSwitchOnFeature(value);
                continue;
            }
            if ("SwitchOnPicFmt".equalsIgnoreCase(sName)) {
                hms.setSwitchOnPicFmt(value);
                continue;
            }
            if ("PowerOn".equalsIgnoreCase(sName)) {
                hms.setPowerOn(value);
                continue;
            }
            if ("LowPowerStandby".equalsIgnoreCase(sName)) {
                hms.setLowPowerStandby(value);
                continue;
            }
            if ("SmartPower".equalsIgnoreCase(sName)) {
                hms.setSmartPower(value);
                continue;
            }
            if ("RebootEveryDay".equalsIgnoreCase(sName)) {
                hms.setRebootEveryDay(value);
                continue;
            }
            if ("WakeOnLAN".equalsIgnoreCase(sName)) {
                hms.setWakeOnLAN(value);
                continue;
            }
            if ("DisplayWelcomeMsg".equalsIgnoreCase(sName)) {
                hms.setDisplayWelcomeMsg(value);
                continue;
            }
            if ("WelcomeMsgLine1".equalsIgnoreCase(sName)) {
                hms.setWelcomeMsgLine1(value);
                continue;
            }
            if ("WelcomeMsgLine2".equalsIgnoreCase(sName)) {
                hms.setWelcomeMsgLine2(value);
                continue;
            }
            if ("WelcomeMsgTimeOut".equalsIgnoreCase(sName)) {
                hms.setWelcomeMsgTimeOut(value);
                continue;
            }
            if ("DisplayLogo".equalsIgnoreCase(sName)) {
                hms.setDisplayLogo(value);
                continue;
            }
            if ("SmartInfo".equalsIgnoreCase(sName)) {
                hms.setSmartInfo(value);
                continue;
            }
            if ("SmartInfoIconLabel".equalsIgnoreCase(sName)) {
                hms.setSmartInfoIconLabel(value);
                continue;
            }
            if ("KBLock".equalsIgnoreCase(sName)) {
                hms.setKBLock(value);
                continue;
            }
            if ("RCLock".equalsIgnoreCase(sName)) {
                hms.setRCLock(value);
                continue;
            }
            if ("OSDDisplay".equalsIgnoreCase(sName)) {
                hms.setOSDDisplay(value);
                continue;
            }
            if ("HighSecurity".equalsIgnoreCase(sName)) {
                hms.setHighSecurity(value);
                continue;
            }
            if ("AutoScart".equalsIgnoreCase(sName)) {
                hms.setAutoScart(value);
                continue;
            }
            if ("USBBreakIn".equalsIgnoreCase(sName)) {
                hms.setUSBBreakIn(value);
                continue;
            }
            if ("EnableUSB".equalsIgnoreCase(sName)) {
                hms.setEnableUSB(value);
                continue;
            }
            if ("SXPBaudRate".equalsIgnoreCase(sName)) {
                hms.setSXPBaudRate(value);
                continue;
            }
            if ("EnableTeletext".equalsIgnoreCase(sName)) {
                hms.setEnableTeletext(value);
                continue;
            }
            if ("EnableMHEG".equalsIgnoreCase(sName)) {
                hms.setEnableMHEG(value);
                continue;
            }
            if ("EnableEPG".equalsIgnoreCase(sName)) {
                hms.setEnableEPG(value);
                continue;
            }
            if ("EnableSubtitles".equalsIgnoreCase(sName)) {
                hms.setEnableSubtitles(value);
                continue;
            }
            if ("SubtitleOnStartup".equalsIgnoreCase(sName)) {
                hms.setSubtitleOnStartup(value);
                continue;
            }
            if ("BlueMute".equalsIgnoreCase(sName)) {
                hms.setBlueMute(value);
                continue;
            }
            if ("EnableCISlot".equalsIgnoreCase(sName)) {
                hms.setEnableCISlot(value);
                continue;
            }
            if ("WiFiCrossConnect".equalsIgnoreCase(sName)) {
                hms.setWiFiCrossConnect(value);
                continue;
            }
            if ("WiFiMiraCast".equalsIgnoreCase(sName)) {
                hms.setWiFiMiraCast(value);
                continue;
            }
            if ("DirectShare".equalsIgnoreCase(sName)) {
                hms.setDirectShare(value);
                continue;
            }
            if ("ScrambledProgramOSD".equalsIgnoreCase(sName)) {
                hms.setScrambledProgramOSD(value);
                continue;
            }
            if ("WiFiLostOSD".equalsIgnoreCase(sName)) {
                hms.setWiFiLostOSD(value);
                continue;
            }
            if ("JointSpace".equalsIgnoreCase(sName)) {
                hms.setJointSpace(value);
                continue;
            }
            if ("EasylinkBreakIn".equalsIgnoreCase(sName)) {
                hms.setEasylinkBreakIn(value);
                continue;
            }
            if ("EasylinkControl".equalsIgnoreCase(sName)) {
                hms.setEasylinkControl(value);
                continue;
            }
            if ("EnableSkype".equalsIgnoreCase(sName)) {
                hms.setEnableSkype(value);
                continue;
            }
            if ("DigitTimeout".equalsIgnoreCase(sName)) {
                hms.setDigitTimeout(value);
                continue;
            }
            if ("SelectableAV".equalsIgnoreCase(sName)) {
                hms.setSelectableAV(value);
                continue;
            }
            if ("WatchTV".equalsIgnoreCase(sName)) {
                hms.setWatchTV(value);
                continue;
            }
            if ("ExternalClk".equalsIgnoreCase(sName)) {
                hms.setExternalClk(value);
                continue;
            }
            if ("ClkBrighDimlight".equalsIgnoreCase(sName)) {
                hms.setClkBrighDimlight(value);
                continue;
            }
            if ("ClkBrighIntenselight".equalsIgnoreCase(sName)) {
                hms.setClkBrighIntenselight(value);
                continue;
            }
            if ("ClkLightSensor".equalsIgnoreCase(sName)) {
                hms.setClkLightSensor(value);
                continue;
            }
            if ("TimeDownload".equalsIgnoreCase(sName)) {
                hms.setTimeDownload(value);
                continue;
            }
            if ("TimeSetting".equalsIgnoreCase(sName)) {
                hms.setTimeSetting(value);
                continue;
            }
            if ("ClkNTPSvrURL".equalsIgnoreCase(sName)) {
                hms.setClkNTPSvrURL(value);
                continue;
            }
            if ("ClkDownloadProgram".equalsIgnoreCase(sName)) {
                hms.setClkDownloadProgram(value);
                continue;
            }
            if ("ClkDownloadCountry".equalsIgnoreCase(sName)) {
                hms.setClkDownloadCountry(value);
                continue;
            }
            if ("ClkTimeZone".equalsIgnoreCase(sName)) {
                hms.setClkTimeZone(value);
                continue;
            }
            if ("DaylightSaving".equalsIgnoreCase(sName)) {
                hms.setDaylightSaving(value);
                continue;
            }
            if ("ClkTimeOffset".equalsIgnoreCase(sName)) {
                hms.setClkTimeOffset(value);
                continue;
            }
            if ("ReferenceDate".equalsIgnoreCase(sName)) {
                hms.setReferenceDate(value);
                continue;
            }
            if ("ReferenceTime".equalsIgnoreCase(sName)) {
                hms.setReferenceTime(value);
                continue;
            }
            if ("MainSpkrEnable".equalsIgnoreCase(sName)) {
                hms.setMainSpkrEnable(value);
                continue;
            }
            if ("IndMainSpkrMute".equalsIgnoreCase(sName)) {
                hms.setIndMainSpkrMute(value);
                continue;
            }
            if ("DefMainSpkrVol".equalsIgnoreCase(sName)) {
                hms.setDefMainSpkrVol(value);
                continue;
            }
            if ("AutoChnUpdate".equalsIgnoreCase(sName)) {
                hms.setAutoChnUpdate(value);
                continue;
            }
            if ("AutoSwUpdate".equalsIgnoreCase(sName)) {
                hms.setAutoSwUpdate(value);
                continue;
            }
            if ("SkipScrambled".equalsIgnoreCase(sName)) {
                hms.setSkipScrambled(value);
                continue;
            }
            if ("MultiRC".equalsIgnoreCase(sName)) {
                hms.setMultiRC(value);
                continue;
            }
            if ("MyChoice".equalsIgnoreCase(sName)) {
                hms.setMyChoice(value);
                continue;
            }
            if ("AskForPIN".equalsIgnoreCase(sName)) {
                hms.setAskForPIN(value);
                continue;
            }
            if ("SmartPay".equalsIgnoreCase(sName)) {
                hms.setSmartPay(value);
                continue;
            }
            if ("AV".equalsIgnoreCase(sName)) {
                hms.setAV(value);
                continue;
            }
            if ("SmartTV".equalsIgnoreCase(sName)) {
                hms.setSmartTV(value);
                continue;
            }
            if ("AppControlID".equalsIgnoreCase(sName)) {
                hms.setAppControlID(value);
                continue;
            }
            if ("ProfileName".equalsIgnoreCase(sName)) {
                hms.setProfileName(value);
                continue;
            }
            if ("Source".equalsIgnoreCase(sName)) {
                hms.setSource(value);
                continue;
            }
            if ("Fallback".equalsIgnoreCase(sName)) {
                hms.setFallback(value);
                continue;
            }
            if ("DashboardIconLabel".equalsIgnoreCase(sName)) {
                hms.setDashboardIconLabel(value);
                continue;
            }
            if ("ServerUIURL".equalsIgnoreCase(sName)) {
                hms.setServerUIURL(value);
                continue;
            }
            if ("WebServicesURL".equalsIgnoreCase(sName)) {
                hms.setWebServicesURL(value);
                continue;
            }
            if ("TVDiscoveryService".equalsIgnoreCase(sName)) {
                hms.setTVDiscoveryService(value);
                continue;
            }
            if ("ProfessionalSettingsService".equalsIgnoreCase(sName)) {
                hms.setProfessionalSettingsService(value);
                continue;
            }
            if ("IPUpgradeService".equalsIgnoreCase(sName)) {
                hms.setIPUpgradeService(value);
                continue;
            }
            if ("PowerService".equalsIgnoreCase(sName)) {
                hms.setPowerService(value);
                continue;
            }
            if ("VsecOverRFEnable".equalsIgnoreCase(sName)) {
                hms.setVsecOverRFEnable(value);
                continue;
            }
            if ("EraseKeyOption".equalsIgnoreCase(sName)) {
                hms.setEraseKeyOption(value);
                continue;
            }
            if ("VsecFrequency".equalsIgnoreCase(sName)) {
                hms.setVsecFrequency(value);
                continue;
            }
            if ("VsecMedium".equalsIgnoreCase(sName)) {
                hms.setVsecMedium(value);
                continue;
            }
            if ("VsecSymbolRate".equalsIgnoreCase(sName)) {
                hms.setVsecSymbolRate(value);
                continue;
            }
            if ("RFCLFrequency".equalsIgnoreCase(sName)) {
                hms.setRFCLFrequency(value);
                continue;
            }
            if ("RFCLMedium".equalsIgnoreCase(sName)) {
                hms.setRFCLMedium(value);
                continue;
            }
            if ("RFCLSymbolRate".equalsIgnoreCase(sName)) {
                hms.setRFCLSymbolRate(value);
                continue;
            }
            if ("UpgradeMode".equalsIgnoreCase(sName)) {
                hms.setUpgradeMode(value);
                continue;
            }
            if ("AutoUpgrade".equalsIgnoreCase(sName)) {
                hms.setAutoUpgrade(value);
                continue;
            }
            if ("InstallationMode".equalsIgnoreCase(sName)) {
                hms.setInstallationMode(value);
                continue;
            }
            if (!"CloneMultiRC".equalsIgnoreCase(sName)) continue;
            hms.setCloneMultiRC(value);
        }
        try {
            JAXBContext context = JAXBContext.newInstance(HotelModeSettings.class);
            File settingFile = this.generateSettingXMLFile(PlatformUtils.getRootFolderName(this.platformId) + "_SSB.xml", null);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
            marshaller.marshal((Object)hms, settingFile);
        }
        catch (JAXBException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void processSetting2K16(List<Setting> listOfSettings) {
        String value;
        ArrayList<Item> list = new ArrayList<Item>();
        String majorVerNo = "0";
        String minorVerNo = "0";
        Setting resolution = null;
        for (Setting s : listOfSettings) {
            if (null == s) break;
            Item item2 = new Item();
            if ("majorVerNo".equalsIgnoreCase(s.getItem())) {
                majorVerNo = s.getLastValue();
            }
            if ("minorVerNo".equalsIgnoreCase(s.getItem())) {
                minorVerNo = s.getLastValue();
            }
            if (!"MS2K16".equalsIgnoreCase(s.getXaddr()) && !"ES2K16".equalsIgnoreCase(s.getXaddr()) || s.getItem().equals("majorVerNo") || s.getItem().equals("minorVerNo")) continue;
            if ("Advanced.Diagnostic Logging.Frequency".equalsIgnoreCase(s.getItem())) {
                item2.setName(s.getItem());
                value = StringUtils.isNotEmpty(s.getLastValue()) ? s.getLastValue() : "30";
                value = "" + value.trim() + "";
                item2.setValue(value);
                item2.setCloneIn(s.getCloneIn());
                list.add(item2);
                continue;
            }
            item2.setName(s.getItem());
            switch (s.getItem()) {
                case "TV Settings.Picture.Advanced.Sharpness.Super Resolution": 
                case "TV Settings.Picture.Advanced.Sharpness.Ultra Resolution": 
                case "TV Settings.Picture.Advanced.Sharpness.8K Ultra resolution": {
                    resolution = s;
                    break;
                }
            }
            value = s.getLastValue() != null ? s.getLastValue() : "";
            value = "" + value.trim() + "";
            item2.setValue(value);
            item2.setCloneIn(s.getCloneIn());
            list.add(item2);
        }
        if (null != resolution) {
            Item item3 = new Item();
            Item item4 = new Item();
            Item item5 = new Item();
            value = resolution.getLastValue() != null ? resolution.getLastValue() : "";
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
                case "TV Settings.Picture.Advanced.Sharpness.Super Resolution": {
                    list.add(item4);
                    list.add(item5);
                    break;
                }
                case "TV Settings.Picture.Advanced.Sharpness.Ultra Resolution": {
                    list.add(item3);
                    list.add(item5);
                    break;
                }
                case "TV Settings.Picture.Advanced.Sharpness.8K Ultra resolution": {
                    list.add(item3);
                    list.add(item4);
                    break;
                }
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
            LOG.info("Marshal TVSettings.xml location ::= {}", (Object)settingLocation);
            File settingFile = this.generateSettingXMLFile("TVSettings", "TVSettings.xml");
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
            marshaller.marshal((Object)tvsettings, settingFile);
        }
        catch (JAXBException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private boolean platormHasSetting(Setting s) {
        boolean ret = false;
        List<Setting> settings = this.platform.getSettings().getSetting();
        if (s.getItem1() != null && s.getRefFile() != null && s.getRefFile().equalsIgnoreCase("BdsLastStatus.txt")) {
            for (Setting stoTest : settings) {
                if (stoTest.getItem1() == null || stoTest.getItem1() == null || s.getItem1() == null || !stoTest.getItem1().equals(s.getItem1())) continue;
                return true;
            }
        }
        for (Setting set : settings) {
            if (null != s && s.getItem1() != null && s.getItem1().equalsIgnoreCase("BaudRate")) {
                LOG.info(s.getItem1());
            }
            if (null == s || !set.getItem().equals(s.getItem())) continue;
            ret = true;
            break;
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
                if (stoTest.getItem1() == null || stoTest.getItem1() == null || s.getItem1() == null || !stoTest.getItem1().equals(s.getItem1())) continue;
                return true;
            }
        }
        if (s.getXaddr() != null && s.getXaddr().equals("ES2K12")) {
            return false;
        }
        for (Setting stoTest : configuredSettings) {
            if (stoTest.getItem() == null || s.getItem() == null || !stoTest.getItem().equals(s.getItem())) continue;
            ret = true;
            break;
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
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return ret;
    }

    private void process2K14MSFiles(String srcRootPath) {
        try {
            String[] cloneItems;
            for (String cloneItem : cloneItems = new String[]{"SmartInfoImages", "SmartInfoPages", "SystemUIBackup"}) {
                File srcDir = new File(srcRootPath + cloneItem);
                if (!srcDir.exists()) continue;
                File destDir = new File(this.outputPath + cloneItem);
                String zippath = destDir.toString();
                String targetName = PlatformUtils.getRootFolderName(this.platformId) + "_" + cloneItem;
                File file = new File(srcDir.toString() + File.separator + targetName + "_Identifier.txt");
                if (file.exists()) {
                    FileUtils.forceDelete(file);
                }
                String identifier = TpvDateUtils.getCurrentIndentifierFormatTime();
                try {
                    FileUtils.writeStringToFile(file, identifier, StandardCharsets.UTF_8);
                }
                catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                }
                FileUtils.copyDirectory(srcDir, destDir);
                if (this.isDownloadMode) continue;
                ZipCommonUtils.gen7Zip(zippath, this.outputPath + targetName + ".zip");
            }
            File findCmsFiles = new File(srcRootPath);
            File csmDirOrFile = TpvFileUtils.getFileByNamePrefix(findCmsFiles, "CSM_");
            if (csmDirOrFile == null) {
                csmDirOrFile = TpvFileUtils.getDirectoryByName(findCmsFiles.getParentFile(), "CSMDump");
            }
            if (csmDirOrFile != null) {
                this.makeDirs(this.outputPath + "/CSMDump");
                if (csmDirOrFile.isFile()) {
                    FileUtils.copyFileToDirectory(csmDirOrFile, new File(this.outputPath + "/CSMDump"));
                } else {
                    FileUtils.copyDirectory(csmDirOrFile, new File(this.outputPath + "/CSMDump"));
                }
            }
        }
        catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    private void copyChannelListFiles(ChannelPackage channelPackage) {
        String[] channelItems;
        String channelBasePath = CloneItemUtils.getChannelPackageDataPath(channelPackage);
        for (String channelItem : channelItems = new String[]{"ChannelList"}) {
            File srcDir = new File(channelBasePath + channelItem);
            if (!srcDir.exists()) continue;
            TpvFileUtils.copyDirectoryIngoreExistsFile(srcDir, new File(this.outputPath + channelItem));
        }
        File defaultLogo = new File(this.outputPath + "/ChannelList/ChannelLogos/default/");
        FileUtils.deleteQuietly(defaultLogo);
        File defaultThemeIcons = new File(this.outputPath + "/ChannelList/ThemeIcons/default/");
        FileUtils.deleteQuietly(defaultThemeIcons);
    }

    private void process2K16Files(String srcRootPath) {
        block4: {
            try {
                File[] cmsFile;
                String[] cloneItemNames;
                this.removeCleanFile(srcRootPath);
                for (String cloneItem : cloneItemNames = new String[]{"LocalCustomDashboard", "WeatherForecast", "RoomSpecificSettings", "PMS", "SmartInfoBrowser", "Vsecure", "Script", "HTVCfg", "MyChoice", "ProfessionalApps", "AndroidAppsData"}) {
                    File destDir2 = new File(this.outputPath + cloneItem);
                    File srcDir = new File(srcRootPath + cloneItem);
                    if (!srcDir.exists()) continue;
                    this.fixLostOfIdentifierFile(cloneItem, srcDir);
                    FileUtils.copyDirectory(srcDir, destDir2);
                }
                File findCmsFiles = TpvFileUtils.getDirectoryByName(new File(srcRootPath).getParentFile(), "DataDump");
                if (findCmsFiles == null || null == (cmsFile = findCmsFiles.listFiles()) || cmsFile.length <= 0) break block4;
                for (File cms : cmsFile) {
                    if (!cms.getName().startsWith("CSM")) continue;
                    this.makeDirs(this.outputPath + "/DataDump");
                    FileUtils.copyFileToDirectory(new File(findCmsFiles + File.separator + cms.getName()), new File(this.outputPath + "/DataDump"));
                    break;
                }
            }
            catch (Exception ex) {
                LOG.error(ex.getMessage(), ex);
            }
        }
    }

    private void fixLostOfIdentifierFile(String cloneItem, File srcDir) {
        File cloneItemIdenfier;
        if (!(Arrays.asList("WeatherForecast", "Script").contains(cloneItem) || (cloneItemIdenfier = new File(srcDir + File.separator + cloneItem + "_Identifier.txt")).exists() && cloneItemIdenfier.length() != 0L)) {
            String currentTimeIdentifier = TpvDateUtils.getCurrentIndentifierFormatTime();
            try {
                FileUtils.writeStringToFile(cloneItemIdenfier, currentTimeIdentifier, StandardCharsets.UTF_8);
            }
            catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    private void removeCleanFile(String filePath) {
        File roomSpecificFile = new File(filePath + "RoomSpecificSettings//");
        if (roomSpecificFile.exists()) {
            File[] files;
            for (File f : files = roomSpecificFile.listFiles()) {
                if (!f.getName().endsWith(".clean")) continue;
                try {
                    Files.delete(f.toPath());
                }
                catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
    }

    private File generateSettingXMLFile(String firstFolder, String secondFolder) {
        File settingFile;
        String path = this.outputPath + File.separator + firstFolder;
        if (null != secondFolder) {
            File parentFile = new File(path);
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            path = path + File.separator + secondFolder;
        }
        if (!(settingFile = new File(path)).getParentFile().exists()) {
            settingFile.getParentFile().mkdirs();
        }
        if (!settingFile.exists()) {
            try {
                settingFile.createNewFile();
            }
            catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return settingFile;
    }

    private void zipAllToPath(String path) {
        File[] cloneItems;
        File cloneAssemblyDir = new File(this.outputPath);
        for (File cloneItemDir : cloneItems = cloneAssemblyDir.listFiles()) {
            String zipName = cloneItemDir.getName();
            String srcPath = cloneItemDir.getAbsolutePath();
            if (this.isCloneDataEmpty(cloneItemDir.getAbsolutePath())) {
                LOG.info("empty clone data,ignore");
                continue;
            }
            String zipPath = String.format(Locale.ENGLISH, "%s/%s.zip", path, zipName);
            ZipCommonUtils.zipFiles(srcPath, zipPath);
        }
    }

    private String getItemIdentifier(File subItem) {
        if (subItem.getName().equalsIgnoreCase("DataDump")) {
            return "";
        }
        File identifer = new File(subItem.getAbsolutePath() + File.separator + subItem.getName() + "_Identifier.txt");
        try {
            return FileUtils.readFileToString(identifer, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            return "";
        }
    }

    public String getSiAssignCloneItems() {
        JSONArray jaItems = new JSONArray();
        File assemblyLocation = new File(this.outputPath);
        File[] files = assemblyLocation.listFiles();
        if (files != null) {
            for (File assignFile : files) {
                String assignDirName = assignFile.getName();
                if (PlatformUtils.isAsta2016Up(this.platformId) && !this.checkCloneItemAvailable(assignDirName)) continue;
                JSONObject jsObj = new JSONObject();
                String itemName = CloneItemUtils.convertItemToJapitName(assignDirName);
                String versionNo = this.getItemIdentifier(assignFile);
                LOG.info("itemName:{},versionNo:{}", (Object)itemName, (Object)versionNo);
                versionNo = TpvStringUtils.limitStringLength(versionNo, 32);
                jsObj.put("CloneItemName", itemName);
                jsObj.put("CloneItemVersionNo", versionNo);
                jsObj.put("CloneItemStatus", "No");
                jaItems.put(jsObj);
            }
        }
        JSONObject jsObj = new JSONObject();
        jsObj.put("SiAssignItem", jaItems);
        LOG.info("SiAssignCloneItems:{}", (Object)jsObj);
        return jsObj.toString();
    }

    @Override
    public void close() {
        this.cleanAssemblyDir();
    }

    public void removeUnexportItems(String[] exportItems) {
        List<String> itemList = Arrays.asList(exportItems);
        File[] subItems = new File(this.outputPath).listFiles();
        if (subItems == null) {
            LOG.error("output path not exits,{}", (Object)this.outputPath);
            return;
        }
        String rootfolderName = PlatformUtils.getRootFolderName(this.platformId);
        String[] specItems = new String[]{CommonConstants.CloneItemType.TVSettings.name(), CommonConstants.CloneItemType.ChannelList.name(), CommonConstants.CloneItemType.WelcomeLogo.name()};
        String[] specItemNames = new String[]{rootfolderName + "_SSB", rootfolderName + "_CHTB", rootfolderName + "_WelcomeLogo"};
        for (File subItem : subItems) {
            String subItemName;
            if (subItem.isDirectory() && !itemList.contains(subItem.getName())) {
                FileUtils.deleteQuietly(subItem);
                LOG.info("remove unexport Item:{}", (Object)subItem.getName());
                continue;
            }
            if (!subItem.isFile() || PlatformUtils.isAsta2016Up(this.platformId)) continue;
            for (int i = 0; i < specItemNames.length; ++i) {
                String itemName = specItemNames[i];
                String itemType = specItems[i];
                if (!subItem.getName().startsWith(itemName) || itemList.contains(itemType)) continue;
                FileUtils.deleteQuietly(subItem);
                LOG.info("remove unexport Item:{}", (Object)subItem.getName());
            }
            if (!FilenameUtils.getExtension(subItem.getName()).equalsIgnoreCase("zip") || itemList.contains(subItemName = FilenameUtils.getBaseName(subItem.getName()).replaceAll(rootfolderName + "_", ""))) continue;
            FileUtils.deleteQuietly(subItem);
            LOG.info("remove unexport Item:{}", (Object)subItem.getName());
        }
    }

    public void initEnableScheduleTvSettings(String identifier) {
        try {
            String tvsettingPath = this.outputPath + "TVSettings";
            File tvSettingFile = new File(tvsettingPath);
            if (tvSettingFile.exists()) {
                FileUtils.deleteQuietly(tvSettingFile);
            }
            tvSettingFile.mkdirs();
            String tvsettingXmlPath = tvsettingPath + File.separator + "TVSettings.xml";
            File tvsettingXmlFile = new File(tvsettingXmlPath);
            FileUtils.writeStringToFile(tvsettingXmlFile, "<?xml version='1.0' encoding='UTF-8' ?>\r\n\r\n\r\n\r\n<TVSettings>\r\n  <SchemaVersion MajorVerNo=\"1\" MinorVerNo=\"0\" />\r\n  <item>\r\n    <Name>Features.Scheduler.Enable</Name>\r\n    <Value>Yes</Value>\r\n    <CloneIn>Yes</CloneIn>\r\n  </item>\r\n\r\n</TVSettings>", StandardCharsets.UTF_8);
            String tvsettingIdentifierPath = tvsettingPath + File.separator + "TVSettings_Identifier.txt";
            File tvsettingIdentifierFile = new File(tvsettingIdentifierPath);
            FileUtils.writeStringToFile(tvsettingIdentifierFile, identifier, StandardCharsets.UTF_8);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /*
     * WARNING - void declaration
     */
    public void zipClonePacketAccordingType(int cloneId, String destPath, String selectCloneType) {
        void var6_17;
        List<Object> copyList = new ArrayList<String>();
        String string = selectCloneType;
        int n = -1;
        switch (string.hashCode()) {
            case 65203517: {
                if (!string.equals("Clone")) break;
                boolean bl = false;
                break;
            }
            case 1499275331: {
                if (!string.equals("Settings")) break;
                boolean bl = true;
                break;
            }
            case 1497270256: {
                if (!string.equals("Channels")) break;
                int n2 = 2;
                break;
            }
            case 2047634: {
                if (!string.equals("Apps")) break;
                int n3 = 3;
                break;
            }
            case -1678783399: {
                if (!string.equals("Content")) break;
                int n4 = 4;
                break;
            }
            case 1327693479: {
                if (!string.equals("Banners")) break;
                int n5 = 5;
                break;
            }
            case 2708: {
                if (!string.equals("UI")) break;
                int n6 = 6;
                break;
            }
            case 1843257500: {
                if (!string.equals("Schedules")) break;
                int n7 = 7;
                break;
            }
            case -1397214398: {
                if (!string.equals("Welcome")) break;
                int n8 = 8;
                break;
            }
            case -1406873644: {
                if (!string.equals("Weather")) break;
                int n9 = 9;
            }
        }
        switch (var6_17) {
            case 0: {
                copyList = Arrays.asList(CommonConstants.cloneItems);
                break;
            }
            case 1: {
                copyList.add("TVSettings");
                break;
            }
            case 2: {
                copyList.add("ChannelList");
                copyList.add("MediaChannels");
                break;
            }
            case 3: {
                copyList.add("AndroidApps");
                break;
            }
            case 4: {
                copyList.add("SmartInfoBrowser");
                break;
            }
            case 5: {
                copyList.add("Banner");
                copyList.add("TVSettings");
                break;
            }
            case 6: {
                copyList.add("ProfessionalAppsData");
                break;
            }
            case 7: {
                copyList.add("TVSettings");
                copyList.add("Schedules");
                break;
            }
            case 8: {
                WelcomeManager welMgr = JpaManager.getWelcomeManager();
                Welcome welcome = null;
                try {
                    welcome = welMgr.loadByKey(cloneId);
                    if (null == welcome) break;
                    int type = welcome.getType();
                    copyList.add(this.getWelcomeCloneName(type));
                    if (type != 1) break;
                    copyList.add("TVSettings");
                }
                catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
                break;
            }
            case 9: {
                copyList.add("WeatherForecast");
                break;
            }
        }
        for (String string2 : copyList) {
            String sourcePath = this.outputPath + File.separator + string2;
            File sourcePathFile = new File(sourcePath);
            File destFile = new File(destPath + string2 + ".zip");
            if (!sourcePathFile.exists() || !this.checkCloneItemAvailable(string2) || destFile.exists()) continue;
            ZipCommonUtils.zipFiles(sourcePath, destPath + string2 + ".zip");
        }
    }

    private static String generateSimpleTvSettingsClone(String tvIP, String tvSettingContent) {
        try {
            String ipRoot = tvIP.replace(".", "");
            String dirPath = CommonConstants.SISERVER_CONF_DIR + ipRoot + "/MasterCloneData/TVSettings/";
            FileUtils.forceMkdir(new File(dirPath));
            String xmlPath = dirPath + "TVSettings.xml";
            FileUtils.writeStringToFile(new File(xmlPath), tvSettingContent, StandardCharsets.UTF_8);
            String identifierPath = dirPath + "TVSettings_Identifier.txt";
            String identifier = TpvDateUtils.getCurrentIndentifierFormatTime();
            FileUtils.writeStringToFile(new File(identifierPath), identifier, StandardCharsets.UTF_8);
            String zipContentPath = CommonConstants.SISERVER_CONF_DIR + ipRoot + "/MasterCloneData/";
            String targetPath = CommonConstants.servletContextPath + "/Profile/Clone/" + ipRoot + "/";
            File targetDirectory = new File(targetPath);
            FileUtils.deleteDirectory(targetDirectory);
            targetDirectory.mkdirs();
            ZipCommonUtils.createZip(zipContentPath, targetPath + "TVSettings.zip");
            return identifier;
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    private boolean checkCloneItemAvailable(String cloneItemName) {
        File cloneItemFile = new File(this.outputPath + File.separator + cloneItemName);
        int fileCount = 0;
        List<String> skipCheckFiles = Arrays.asList("HTVCfg.xml", "Script.xml");
        if (cloneItemFile.isDirectory()) {
            for (File assignData : cloneItemFile.listFiles()) {
                if (skipCheckFiles.contains(assignData.getName())) {
                    return true;
                }
                ++fileCount;
            }
        } else {
            LOG.error("cloneItemName {} is not a valid clone directory", (Object)cloneItemName);
        }
        return fileCount > 1;
    }

    private String getWelcomeCloneName(int type) {
        String cloneName = WELCOME_LOGO;
        switch (type) {
            case 0: {
                cloneName = WELCOME_LOGO;
                break;
            }
            case 1: {
                cloneName = "ProfessionalAppsData";
                break;
            }
            case 2: {
                break;
            }
        }
        return cloneName;
    }
}

