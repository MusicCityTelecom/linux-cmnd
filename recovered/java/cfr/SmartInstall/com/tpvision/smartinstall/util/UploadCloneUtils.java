/*
 * Decompiled with CFR 0.152.
 */
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
import com.tpvision.smartinstall.util.BannerTemplateUtils;
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.JaxbReadXml;
import com.tpvision.smartinstall.util.MalformedCloneDataException;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.SettingState;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.TpvFileUtils;
import com.tpvision.smartinstall.util.TpvStringUtils;
import com.tpvision.smartinstall.util.UploadException;
import com.tpvision.smartinstall.util.UploadPlatformType;
import com.tpvision.smartinstall.util.Utils;
import com.tpvision.smartinstall.util.WelcomeLogoUtils;
import com.tpvision.smartinstall.util.ZipCommonUtils;
import com.tpvision.smartinstall.weather.WeatherServiceImpl;
import com.tpvision.smartinstall.xml.Channel;
import com.tpvision.smartinstall.xml.Config;
import com.tpvision.smartinstall.xml.Crc;
import com.tpvision.smartinstall.xml.CrcFiles;
import com.tpvision.smartinstall.xml.Platform;
import com.tpvision.smartinstall.xml.Setting;
import com.tpvision.smartinstall.xml.SettingFiles;
import com.tpvision.smartinstall.xml.Settings;
import com.tpvision.smartinstall.xml.UnchangedFiles;
import com.tpvision.smartinstall.xml.channel.v5.Broadcast;
import com.tpvision.smartinstall.xml.channel.v5.TvContents;
import com.tpvision.smartinstall.xml.es2k14.HotelModeSettings;
import com.tpvision.smartinstall.xml.es2k14.HotelModelSettingsForTpn142;
import com.tpvision.smartinstall.xml.remotediagnose.DIAGNOSTICANALYTIC;
import com.tpvision.smartinstall.xml.setting.v2k16.Item;
import com.tpvision.smartinstall.xml.setting.v2k16.TVSettings;
import com.tpvision.smartinstall.xml.setting.v2k16.roomspecific.RoomSpecificSettings;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    public static final Map<String, Integer> uploadItemsRecvCount_cmnd = new HashMap<String, Integer>();
    public static final Map<String, Integer> uploadItemsCount_tv = new HashMap<String, Integer>();
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
        File masterCloneDataDir = UploadCloneUtils.getMasterCloneDataDir(file);
        if (platformId.toUpperCase().contains("Q55")) {
            try {
                platform = UploadCloneUtils.getPlatform_Q55(masterCloneDataDir);
            }
            catch (MalformedCloneDataException e) {
                LOG.error(e.getMessage(), e);
            }
        } else if (Arrays.asList("TPN142HE_CloneData", "TPN141HE_CloneData").contains(platformId)) {
            platform = UploadCloneUtils.getPlatformES2K12(masterCloneDataDir, mark, platformId);
        } else {
            File tVSettingsDir = TpvFileUtils.getDirectoryByName(masterCloneDataDir, "TVSettings");
            if (tVSettingsDir != null) {
                platform = UploadCloneUtils.getPlatformES2K12(tVSettingsDir, mark, platformId);
            }
        }
        if (platform != null) {
            for (Setting set : platform.getSettings().getSetting()) {
                if (!"TV Settings.Picture.Advanced.Sharpness.Super Resolution".equalsIgnoreCase(set.getItem()) && !"TV Settings.Picture.Advanced.Sharpness.Ultra Resolution".equalsIgnoreCase(set.getItem()) && !"TV Settings.Picture.Advanced.Sharpness.8K Ultra resolution".equalsIgnoreCase(set.getItem())) continue;
                set.setItem("TV Settings.Picture.Advanced.Sharpness.Super Resolution");
                break;
            }
        }
        return platform;
    }

    public static SettingPackage loadConfToSettingPackageDb(String processPlatformDir, String configName, SettingChannelBean settingChannelBean, String platformId) {
        SettingPackageManager settingPackageManager = JpaManager.getSettingPackageManager();
        SettingPackage settingPackage = new SettingPackage();
        String termPath = processPlatformDir + "/MasterCloneData/TVSettings";
        String termJsonContent = UploadCloneUtils.readFileContent(termPath + File.separator + "CustomTermsAndConditions.json");
        String htvTlsPskKeyContent = UploadCloneUtils.readFileContent(termPath + File.separator + "HTV_TLS_PSK.KEY");
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
        List<com.tpvision.smartinstall.dao.core.Setting> settings = smgr.findSettingsByNameStartingWith(zipName);
        if (null != settings) {
            configName = zipName + "_" + settings.size();
            ArrayList<String> names = new ArrayList<String>();
            for (int j = 0; settings.size() != j; ++j) {
                names.add(settings.get(j).getName());
            }
            int value = settings.size();
            while (UploadCloneUtils.settingNameExists(configName, names)) {
                configName = zipName + "_" + value;
                ++value;
            }
        }
        return configName;
    }

    private static boolean settingNameExists(String configName, List<String> names) {
        for (String string : names) {
            if (!string.equalsIgnoreCase(configName)) continue;
            return true;
        }
        return false;
    }

    private static String updateHotelInfo(String processPlatformDir, String userName, String configName, String platformId) throws UploadException {
        String ret;
        block18: {
            ret = "";
            File hotelInfoDir = null;
            String configLocation = processPlatformDir + PlatformUtils.getPreProcessPath(platformId);
            String hotelInfoName = "HotelInfo";
            if ("TPN141HE_CloneData".equalsIgnoreCase(platformId) || "TPN142HE_CloneData".equalsIgnoreCase(platformId)) {
                configLocation = configLocation + "/SmartInfoImages";
                hotelInfoName = "file_";
            } else if ("TPM1532HE_CloneData".equalsIgnoreCase(platformId) || "TPM1531HE_CloneData".equalsIgnoreCase(platformId) || "TPN161HE_CloneData".equalsIgnoreCase(platformId)) {
                configLocation = configLocation + "/WelcomeLogo";
                hotelInfoName = "file_";
            }
            File parentFileForHotelInfo = new File(configLocation);
            int imgcount = 0;
            if (parentFileForHotelInfo.exists()) {
                File[] hotelInfoFileLists;
                for (File f : hotelInfoFileLists = parentFileForHotelInfo.listFiles()) {
                    if (f.getName().indexOf("jpeg") > -1 || f.getName().indexOf("jpg") > -1 || f.getName().indexOf("png") > -1) {
                        try {
                            if (parentFileForHotelInfo.getPath().indexOf(platformId) > -1) {
                                if (f.getName().indexOf(hotelInfoName) <= -1) continue;
                                ++imgcount;
                            }
                        }
                        catch (Exception e) {
                            LOG.error(e.getMessage(), e);
                        }
                        String rawPath = parentFileForHotelInfo.getAbsolutePath() + parentFileForHotelInfo.getName() + f.getName();
                        ret = DigestUtils.md5Hex(rawPath) + ".jpg";
                    }
                    try {
                        if (imgcount > 30) {
                            throw new UploadException(UploadException.ExceptionType.HotelInfoImageExcedsTotalCount);
                        }
                        if (f.getName().indexOf(hotelInfoName) <= -1) continue;
                        hotelInfoDir = new File(CommonConstants.HOTEL_INFO_ES_LOCATION + "/" + configName);
                        FileUtils.copyFileToDirectory(f, hotelInfoDir);
                    }
                    catch (IOException e) {
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
                if (null == hotelInfoDir || !hotelInfoDir.exists()) break block18;
                for (File source : hotelInfoDir.listFiles()) {
                    String ret1 = DigestUtils.md5Hex(hotelInfoDir.getAbsolutePath() + hotelInfoDir.getName() + source.getName()) + ".jpeg";
                    File destThumbFile = new File(thumbfile + "/" + ret1);
                    try {
                        BufferedImage image = ImageIO.read(source);
                        if (null != image) {
                            BufferedImage thumbnail = Scalr.resize(image, 122, 69, new BufferedImageOp[0]);
                            ImageIO.write((RenderedImage)thumbnail, "jpeg", destThumbFile);
                            image.flush();
                            thumbnail.flush();
                            new File(hotelInfoDir + "/" + source.getName()).renameTo(new File(hotelInfoDir + "/" + ret1));
                        }
                    }
                    catch (IOException e) {
                        LOG.error(e.getMessage(), e);
                        ret1 = null;
                    }
                    hinf.getHotelinfo().add(ret1);
                }
                FileUtils.copyDirectory(hotelInfoDir, hotelfile);
                FileUtils.deleteDirectory(hotelInfoDir);
                jsonStr = gson.toJson(hinf);
                String sname = configName;
                UploadCloneUtils.loadSmartUIToDb(userName, configName, sname, jsonStr);
                UploadCloneUtils.loadsmartinfoToDb(userName, configName, sname);
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
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
        List<com.tpvision.smartinstall.dao.core.Setting> settings = smgr.findSettingsByName(sname);
        com.tpvision.smartinstall.dao.core.Setting setseting = settings.get(0);
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
            com.tpvision.smartinstall.xml.channel.v5.Channel channel;
            Broadcast broadcast;
            if (!(channelT instanceof com.tpvision.smartinstall.xml.channel.v5.Channel) || null == (broadcast = (channel = (com.tpvision.smartinstall.xml.channel.v5.Channel)channelT).getBroadcast()) || !broadcast.getMedium().equalsIgnoreCase("Analog")) continue;
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

    private static com.tpvision.smartinstall.dao.core.Setting saveSettingToDb(String processPlatformDir, String userName, String configName, String type, SettingChannelBean settingChannelBean, String platformId) {
        String websiteName = "";
        try {
            websiteName = UploadCloneUtils.loadWebsiteNameFromSmartInfoIdentifier(processPlatformDir, configName, platformId);
        }
        catch (Exception exception) {
            // empty catch block
        }
        SettingManager smgr = JpaManager.getSettingManager();
        com.tpvision.smartinstall.dao.core.Setting set = new com.tpvision.smartinstall.dao.core.Setting();
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

    public static com.tpvision.smartinstall.dao.core.Setting loadAllConfToDb(String unZipCloneLocationStr, String userName, String configName, String type, String platformId) throws Exception {
        Schedule schedule;
        UiCustomizations uiCustomizations;
        Banners banners;
        AppPackage appPackage;
        ChannelPackage channelPackage;
        UploadCloneUtils.deleteIdFilesInZipfile(unZipCloneLocationStr);
        UploadCloneUtils.copyUnzipCloneFolderToProcessDir(configName, unZipCloneLocationStr, platformId);
        String processPlatformDir = CommonConstants.CLONE_PROCESS_LOCATION + configName + File.separator + platformId + File.separator;
        SettingChannelBean settingChannelBean = UploadCloneUtils.getSettingChannelBean(processPlatformDir, platformId);
        com.tpvision.smartinstall.dao.core.Setting setting = UploadCloneUtils.saveSettingToDb(processPlatformDir, userName, configName, type, settingChannelBean, platformId);
        if (settingChannelBean.getSetttings() != null) {
            SettingPackage settingPackage = UploadCloneUtils.loadConfToSettingPackageDb(processPlatformDir, configName, settingChannelBean, platformId);
            setting.setSettingPackageId(settingPackage.getId());
        }
        if ((channelPackage = UploadCloneUtils.saveChannelPackageToDB(processPlatformDir, userName, configName, settingChannelBean, platformId)) != null) {
            setting.setChannelPackageId(channelPackage.getId());
        }
        if ((appPackage = UploadCloneUtils.loadConfToAppPackageDb(processPlatformDir, configName, platformId)) != null) {
            setting.setAppPackageId(appPackage.getId());
        }
        if ((banners = UploadCloneUtils.loadConfToBanners(processPlatformDir, configName)) != null) {
            setting.setBannersId(banners.getId());
        }
        if ((uiCustomizations = UploadCloneUtils.loadConfToUiCustomizationsDb(processPlatformDir, configName, platformId)) != null) {
            setting.setUiCustomizationsId(uiCustomizations.getId());
        }
        if ((schedule = UploadCloneUtils.saveScheduleToDB(processPlatformDir, configName, platformId)) != null) {
            setting.setScheduleId(schedule.getId());
        }
        UploadCloneUtils.parseProfessionalApps(processPlatformDir);
        try {
            int welcomeId = WelcomeLogoUtils.getInstance().saveWelcomeToDb(platformId, CommonConstants.CLONE_PROCESS_LOCATION + configName, configName, userName);
            if (welcomeId > 0) {
                setting.setWelcomeId(welcomeId);
            }
        }
        catch (UploadException e) {
            LOG.warn(e.getMessage());
        }
        UploadCloneUtils.updateHotelInfo(processPlatformDir, userName, configName, platformId);
        JpaManager.getSettingManager().save(setting);
        return setting;
    }

    public static SettingChannelBean getSettingChannelBean(String platformDirPath, String platformId) throws IOException, JAXBException, UploadException {
        String channelVersion;
        Platform platform = UploadCloneUtils.loadPlatformTVSetting(platformDirPath, platformId);
        if (platform != null && !platformId.equals(platform.getSwver())) {
            throw new UploadException(UploadException.ExceptionType.CloneVersionNotCompatible);
        }
        Settings settings = platform != null ? platform.getSettings() : null;
        com.tpvision.smartinstall.xml.channel.v4.TvContents v4Channel = null;
        TvContents v5Channel = null;
        com.tpvision.smartinstall.xml.channel.v6.TvContents v6Channel = null;
        DIAGNOSTICANALYTIC diagnosticAnalytic = new DIAGNOSTICANALYTIC();
        RoomSpecificSettings roomSettings = new RoomSpecificSettings();
        switch (channelVersion = PlatformUtils.getChannelVersion(platformId)) {
            case "v4": {
                v4Channel = SettingChannelLoader.loadChannelV4(platformDirPath, platformId);
                break;
            }
            case "v5": {
                v5Channel = SettingChannelLoader.loadChannelV5(platformDirPath);
                roomSettings = UploadCloneUtils.getRoomSettings(platformDirPath);
                break;
            }
            case "v6": {
                v6Channel = SettingChannelLoader.loadChannelV6(platformDirPath);
                break;
            }
            default: {
                LOG.info("sw version mismatching!");
            }
        }
        if (null != v4Channel && v4Channel.getChannelMap() != null) {
            UploadCloneUtils.handleAnalogChannelParams(v4Channel.getChannelMap().getChannel());
        }
        if (null != v5Channel && v5Channel.getChannelMap() != null) {
            UploadCloneUtils.handleAnalogChannelParams(v5Channel.getChannelMap().getChannel());
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
                for (int i = 0; i < schedulesLen; ++i) {
                    String endTime;
                    JSONObject jsonObj = (JSONObject)schedulesArr.get(i);
                    String startTime = jsonObj.optString("StartTime");
                    if (startTime.compareTo(endTime = jsonObj.optString("EndTime")) >= 0) continue;
                    afterCheckScheduleArr.put(jsonObj);
                }
                JSONArray sortScheduleArr = Utils.reSortJSONArray(afterCheckScheduleArr);
                content.append("{\"Schedules\":");
                content.append(sortScheduleArr.toString());
                content.append("}");
            }
        }
        catch (Exception e) {
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
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return schedule;
    }

    private static String getTargetPath(String baseDir, String targetFolder) {
        List<String> filelist = null;
        String tmpFolder = baseDir;
        filelist = TpvFileUtils.findSubFolders(new File(tmpFolder));
        for (int i = 0; i < filelist.size(); ++i) {
            tmpFolder = filelist.get(i);
            if (tmpFolder.endsWith(targetFolder)) {
                return tmpFolder;
            }
            if ((tmpFolder = UploadCloneUtils.getTargetPath(tmpFolder, targetFolder)) == null) continue;
            return tmpFolder;
        }
        return null;
    }

    public static Banners loadConfToBanners(String processPlatformDir, String configName) {
        String bannerFolderPath = UploadCloneUtils.getTargetPath(processPlatformDir, "Banner");
        if (bannerFolderPath == null) {
            return null;
        }
        return BannerTemplateUtils.extractBannerUploadFileToBanner(new File(bannerFolderPath), configName);
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
                List<Element> list = root.elements();
                int j = list.size();
                for (int i = 0; i < j; ++i) {
                    Element e = list.get(i);
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
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    public static ChannelPackage saveChannelPackageToDB(String processPlatformDir, String userName, String configName, SettingChannelBean settingChannelBean, String platformId) {
        int length = 0;
        if ("V4".equalsIgnoreCase(settingChannelBean.getChannelVersion()) && settingChannelBean.getV4Channel() != null && settingChannelBean.getV4Channel().getChannelMap() != null && settingChannelBean.getV4Channel().getChannelMap().getChannel() != null) {
            length = settingChannelBean.getV4Channel().getChannelMap().getChannel().size();
        } else if ("V5".equalsIgnoreCase(settingChannelBean.getChannelVersion()) && settingChannelBean.getV5Channel() != null && settingChannelBean.getV5Channel().getChannelMap() != null && settingChannelBean.getV5Channel().getChannelMap().getChannel() != null) {
            length = settingChannelBean.getV5Channel().getChannelMap().getChannel().size();
        } else if ("V6".equalsIgnoreCase(settingChannelBean.getChannelVersion()) && settingChannelBean.getV6Channel() != null && settingChannelBean.getV6Channel().getChannelMap() != null && settingChannelBean.getV6Channel().getChannelMap().getChannel() != null) {
            length = settingChannelBean.getV6Channel().getChannelMap().getChannel().size();
        }
        String channelListDirString = processPlatformDir + "/MasterCloneData/ChannelList";
        File channelListDir = new File(channelListDirString);
        String srcMediaChannel = processPlatformDir + "/MasterCloneData/MediaChannels";
        File srcMediaChannelDir = new File(srcMediaChannel);
        if (length == 0 && !channelListDir.exists() && !srcMediaChannelDir.exists()) {
            LOG.warn("{} not exist ChannelList related folder", (Object)channelListDirString);
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
                LOG.info("ChannelPackages Copy to {}", (Object)channelPackagesDirectoryStr);
            }
            catch (IOException e) {
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
                LOG.info("MediaChannel Copy to {}", (Object)destMediaChannel);
            }
            catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return cp;
    }

    private static void parseProfessionalApps(String platformDir) {
        String path = UploadCloneUtils.getMasterCloneDataDir(new File(platformDir)).getAbsolutePath() + "/" + CommonConstants.CloneItemType.ProfessionalApps.name();
        if (!new File(path).exists()) {
            LOG.warn("professionalApps not exists");
            return;
        }
        File professionalAppsPath = new File(path);
        File[] apkFiles = professionalAppsPath.listFiles();
        if (apkFiles == null) {
            return;
        }
        File infoFile = new File(path + "/ProfessionalAppsInfo.json");
        if (infoFile.exists()) {
            LOG.info("ProfessionalAppsInfo.json existed");
            return;
        }
        AndroidApplications applications = new AndroidApplications();
        ArrayList<AndroidApplications.AndroidApp> androidAppList = new ArrayList<AndroidApplications.AndroidApp>();
        for (File apk : apkFiles) {
            AndroidApplications.AndroidApp androidApp;
            if (!FilenameUtils.getExtension(apk.getName()).equalsIgnoreCase("apk") || (androidApp = UploadCloneUtils.parseAndroidApps(apk.getAbsolutePath())) == null) continue;
            androidAppList.add(androidApp);
        }
        applications.setClonePackages(androidAppList);
        applications.setAvailablePackages(String.valueOf(androidAppList.size()));
        try {
            FileUtils.writeStringToFile(infoFile, new Gson().toJson(applications), StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private static List<String> getCategoriesByManifestXml(String manifestXml) {
        SAXReader reader = new SAXReader();
        ArrayList<String> categoryList = new ArrayList<String>();
        try {
            Document document = reader.read(new StringReader(manifestXml));
            Element root = document.getRootElement();
            List<Node> categoryNodes = root.selectNodes("/manifest/application/*/intent-filter/category");
            for (Node categoryNode : categoryNodes) {
                String fullCategory = categoryNode.valueOf("@android:name");
                if (!StringUtils.isNoneBlank(fullCategory) || categoryList.contains(fullCategory)) continue;
                categoryList.add(fullCategory);
            }
        }
        catch (DocumentException e) {
            LOG.error(e.getMessage(), e);
        }
        return categoryList;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static AndroidApplications.AndroidApp parseAndroidApps(String apkFileName) {
        try {
            File fileApk = new File(apkFileName);
            if (!fileApk.exists()) {
                throw new IOException("apk file not exits," + apkFileName);
            }
            try (ApkFile apkFile = new ApkFile(fileApk);){
                ApkMeta apkMeta = apkFile.getApkMeta();
                AndroidApplications.AndroidApp androidApp2 = new AndroidApplications.AndroidApp();
                androidApp2.setPackageURI(fileApk.getName());
                androidApp2.setPackageType("LOCAL");
                androidApp2.setPackageName(apkMeta.getPackageName());
                Set<Locale> locales = apkFile.getLocales();
                ArrayList<String> localeNames = new ArrayList<String>();
                for (Locale locale : locales) {
                    String localeName = locale.getCountry();
                    if (localeName.isEmpty() || localeNames.contains(localeName)) continue;
                    localeNames.add(localeName);
                }
                androidApp2.setPackageCountry(localeNames.toArray(new String[0]));
                String manifestXml = apkFile.getManifestXml();
                List<String> categoryList = UploadCloneUtils.getCategoriesByManifestXml(manifestXml);
                androidApp2.setPackageCategory(categoryList.toArray(new String[0]));
                AndroidApplications.AndroidApp androidApp = androidApp2;
                return androidApp;
            }
        }
        catch (IOException e) {
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
        }
        catch (IOException e1) {
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
            LOG.info("AppPackage Copy to {}", (Object)appPackagesDirectoryStr);
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return appPackage;
    }

    private static void deleteIdFilesInZipfile(String path) {
        File[] files = new File(path).listFiles();
        if (null == files) {
            return;
        }
        for (File f : files) {
            try {
                if (f.getPath().indexOf(".id") <= -1) continue;
                FileUtils.forceDelete(f);
            }
            catch (IOException e) {
                LOG.debug(e.getMessage(), e);
            }
        }
    }

    private static File getMasterCloneDataDir(File platformDir) {
        File masterCloneDataDir = TpvFileUtils.getDirectoryByName(platformDir, "MasterCloneData");
        if (masterCloneDataDir != null) {
            return masterCloneDataDir;
        }
        return platformDir;
    }

    private static RoomSpecificSettings getRoomSettings(String platformDir) {
        RoomSpecificSettings roomSettings;
        block29: {
            roomSettings = null;
            String path = platformDir + "/MasterCloneData/RoomSpecificSettings/RoomSpecificSettings.xml";
            try {
                JAXBContext context = JAXBContext.newInstance("com.tpvision.smartinstall.xml.setting.v2k16.roomspecific");
                Unmarshaller unmarshaller = context.createUnmarshaller();
                File roomSettintXml = new File(path);
                if (roomSettintXml.exists()) {
                    try (FileInputStream stream = new FileInputStream(roomSettintXml);
                         InputStreamReader freader = new InputStreamReader((InputStream)stream, "UTF-8");){
                        roomSettings = (RoomSpecificSettings)unmarshaller.unmarshal(freader);
                        roomSettings = UploadCloneUtils.verifyRoomSettings(roomSettings);
                        break block29;
                    }
                    catch (Exception e) {
                        LOG.error(e.getMessage(), e);
                    }
                    break block29;
                }
                roomSettings = new RoomSpecificSettings();
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return roomSettings;
    }

    private static RoomSpecificSettings verifyRoomSettings(RoomSpecificSettings roomSettings) {
        if (null != roomSettings && roomSettings.getTV() != null) {
            String serialNumber = roomSettings.getTV().getSerialNumber();
            roomSettings.getTV().setSerialNumber(UploadCloneUtils.replaceEnterChar(serialNumber));
            List<com.tpvision.smartinstall.xml.setting.v2k16.roomspecific.Item> items = roomSettings.getTV().getItem();
            for (com.tpvision.smartinstall.xml.setting.v2k16.roomspecific.Item item : items) {
                item.setValue(UploadCloneUtils.replaceEnterChar(item.getValue()));
            }
            return roomSettings;
        }
        return new RoomSpecificSettings();
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
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
        if ("TPN142HE_CloneData".equalsIgnoreCase(platformId) || "TPN141HE_CloneData".equalsIgnoreCase(platformId)) {
            File processPlatformIdDir = new File(cloneProcessPath + platformId);
            FileUtils.forceMkdir(processPlatformIdDir);
            File platformUnzipPath = new File(unZipCloneLocationStr + "/" + platformId);
            File unzipCSMDir = TpvFileUtils.getDirectoryByName(platformUnzipPath, "CSMDump");
            if (unzipCSMDir != null) {
                FileUtils.copyDirectory(unzipCSMDir, processPlatformIdDir);
            }
            File unzipMasterCloneData = UploadCloneUtils.getMasterCloneDataDir(platformUnzipPath);
            FileUtils.copyDirectory(unzipMasterCloneData, processPlatformIdDir);
        } else {
            FileUtils.copyDirectory(new File(unZipCloneLocationStr), new File(cloneProcessPath));
        }
    }

    public static File getPlatformHandleFolderDir(String unZipCloneLocationStr, String platformId) {
        File platformUnzipPath = new File(unZipCloneLocationStr + "/" + platformId);
        if ("TPN142HE_CloneData".equalsIgnoreCase(platformId) || "TPN141HE_CloneData".equalsIgnoreCase(platformId)) {
            return UploadCloneUtils.getMasterCloneDataDir(platformUnzipPath);
        }
        return platformUnzipPath;
    }

    private static String loadWebsiteNameFromSmartInfoIdentifier(String processPlatformDir, String configName, String platformId) throws IOException {
        UploadPlatformType uploadPlatformType = PlatformUtils.fromPlatfromId(platformId);
        String unzipPlatformDirPath = processPlatformDir;
        String websiteName = "";
        if (uploadPlatformType == UploadPlatformType.folderLocationforAndroidClone || uploadPlatformType == UploadPlatformType.folderLocationfor2K16ES) {
            File smartInfoBrowserDir = new File(unzipPlatformDirPath + "/MasterCloneData/SmartInfoBrowser/");
            if (smartInfoBrowserDir.exists()) {
                String smartInfoBrowserIdPath = unzipPlatformDirPath + "/MasterCloneData/SmartInfoBrowser/SmartInfoBrowser_Identifier.txt";
                File smartInfoBrowserIdFile = new File(smartInfoBrowserIdPath);
                if (smartInfoBrowserIdFile.exists() && (websiteName = FileUtils.readFileToString(smartInfoBrowserIdFile, StandardCharsets.UTF_8)).length() > 32) {
                    websiteName = TpvStringUtils.limitStringLength(websiteName, 32);
                    FileUtils.writeStringToFile(smartInfoBrowserIdFile, websiteName, StandardCharsets.UTF_8);
                }
                int length = websiteName.trim().length();
                long size = FileUtils.sizeOfDirectory(smartInfoBrowserDir);
                websiteName = length >= 18 ? websiteName.substring(17) : (size >= 1024L ? configName : "");
                LOG.info("2K16 websiteName={}", (Object)websiteName);
            }
        } else {
            File smartInfoPageDir = new File(unzipPlatformDirPath + "/MasterCloneData/SmartInfoPages/");
            if (smartInfoPageDir.exists()) {
                String smartInfoPagesIdPath = unzipPlatformDirPath;
                smartInfoPagesIdPath = "TPN141HE_CloneData".equals(platformId) ? smartInfoPagesIdPath + "/MasterCloneData/SmartInfoPages/TPN141HE_SmartInfoPages_Identifier.txt" : smartInfoPagesIdPath + "/MasterCloneData/SmartInfoPages/TPN142HE_SmartInfoPages_Identifier.txt";
                File smartInfoPagesFile = new File(smartInfoPagesIdPath);
                if (smartInfoPagesFile.exists() && (websiteName = FileUtils.readFileToString(smartInfoPagesFile, StandardCharsets.UTF_8)).length() > 32) {
                    websiteName = TpvStringUtils.limitStringLength(websiteName, 32);
                    FileUtils.writeStringToFile(smartInfoPagesFile, websiteName, StandardCharsets.UTF_8);
                }
                int length = websiteName.trim().length();
                long size = FileUtils.sizeOfDirectory(smartInfoPageDir);
                websiteName = length >= 18 ? websiteName.substring(17) : (size >= 1024L ? configName : "");
                LOG.info("2K14 websiteName={}", (Object)websiteName);
            }
        }
        return websiteName;
    }

    private static Platform getPlatformES2K12(File file, int mark, String platformId) {
        ArrayList<File> settingFileList = new ArrayList<File>();
        if (null != file && file.exists()) {
            File[] files;
            for (File f : files = file.listFiles()) {
                if (!f.isFile() || !f.getName().endsWith("xml") || (mark != 1 || !f.getName().startsWith("TVSettings")) && (mark != 0 || f.getName().indexOf("SSB") <= -1)) continue;
                settingFileList.add(f);
            }
        }
        if (settingFileList.isEmpty()) {
            return null;
        }
        Platform platform = new Platform();
        String platformType = ((File)settingFileList.get(0)).getName();
        if (platformType.indexOf(platformId.split("_")[0]) > -1 || platformType.indexOf("TVSettings") > -1) {
            platform.setCloneRootFolderName(platformId);
            platform.setSwver(platformId);
        }
        platform.setId(file.getName());
        List<File> binFiles = TpvFileUtils.findFiles(file, null);
        if (null != binFiles && binFiles.size() > 0) {
            UnchangedFiles uf = new UnchangedFiles();
            for (File f : binFiles) {
                Object name = f.getAbsolutePath();
                int index = ((String)name).indexOf(platform.getCloneRootFolderName()) + platform.getCloneRootFolderName().length() + 1;
                String path = ((String)name).substring(index);
                name = f.getName();
                com.tpvision.smartinstall.xml.File fileObj = new com.tpvision.smartinstall.xml.File();
                fileObj.setName((String)name);
                fileObj.setPath(path);
                uf.getFile().add(fileObj);
            }
            platform.setUnchangedFiles(uf);
        }
        List<File> crcFiles = UploadCloneUtils.findCRCFiles(file);
        CrcFiles crcFilesList = UploadCloneUtils.getCrcFileDetails(crcFiles, platform);
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
                    File settingFile = (File)settingFileList.get(0);
                    if (platformType.indexOf("TPN141HE") != -1) {
                        context14 = JAXBContext.newInstance(HotelModeSettings.class);
                        unmarshaller14 = context14.createUnmarshaller();
                        hmSettings2K14 = (HotelModeSettings)unmarshaller14.unmarshal(settingFile);
                    } else if (platformType.indexOf("TPN142HE") != -1) {
                        context14_tpn142 = JAXBContext.newInstance(HotelModelSettingsForTpn142.class);
                        unmarshaller14_tpn142 = context14_tpn142.createUnmarshaller();
                        hmSettings2K14_tpn142 = (HotelModelSettingsForTpn142)unmarshaller14_tpn142.unmarshal(settingFile);
                    } else {
                        LOG.info("platformType:{}", (Object)platformType);
                    }
                }
                List<Setting> settingList = null;
                Settings settings = new Settings();
                if (platformType.indexOf("TPN141HE") > -1) {
                    settingList = UploadCloneUtils.getCanonicalSettingFromMS2K14(hmSettings2K14);
                } else if (platformType.indexOf("TPN142HE") > -1) {
                    settingList = UploadCloneUtils.getCanonicalSettingFromMS2K14ForTpn142(hmSettings2K14_tpn142);
                } else if (platformType.indexOf("TVSettings") > -1) {
                    settingList = UploadCloneUtils.getCanonicalSettingFrom2K16(file, platformId);
                }
                settings.getSetting().addAll(settingList);
                platform.setSettings(settings);
            }
            catch (JAXBException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return platform;
    }

    private static List<File> findCRCFiles(File file) {
        File[] childrenFiles;
        ArrayList<File> files = new ArrayList<File>();
        block22: for (File f : childrenFiles = file.listFiles()) {
            if (f.isDirectory()) {
                files.addAll(UploadCloneUtils.findSettingsFiles(f));
                continue;
            }
            try (FileReader fread = new FileReader(f);
                 BufferedReader br = new BufferedReader(fread);){
                String line;
                boolean hasCrc = false;
                boolean hasDateFormat = false;
                while ((line = br.readLine()) != null) {
                    if (line.indexOf("CRC") > -1) {
                        hasCrc = true;
                    }
                    if (!hasDateFormat) {
                        SimpleDateFormat sdp = TpvDateUtils.getSimpleDateFormatWithEnglishLocale("yyyy/MM/dd-HH:mm");
                        try {
                            sdp.parse(line);
                            hasDateFormat = true;
                        }
                        catch (ParseException parseException) {
                            // empty catch block
                        }
                    }
                    if ((!hasCrc || !hasDateFormat) && !hasDateFormat) continue;
                    LOG.debug("Identified " + f.getName() + " as CRC file");
                    files.add(f);
                    continue block22;
                }
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return files;
    }

    private static List<File> findSettingsFiles(File file) {
        File[] childrenFiles;
        ArrayList<File> files = new ArrayList<File>();
        block21: for (File f : childrenFiles = file.listFiles()) {
            if (f.isDirectory()) {
                files.addAll(UploadCloneUtils.findSettingsFiles(f));
                continue;
            }
            if (!f.getName().endsWith(".txt")) continue;
            try (FileReader fread = new FileReader(f);
                 BufferedReader br = new BufferedReader(fread);){
                String line;
                LOG.debug("Analizing file: " + f.getAbsolutePath());
                boolean hasItem = false;
                boolean hasXaddr = false;
                boolean hasLastValue = false;
                while ((line = br.readLine()) != null) {
                    if (line.indexOf("HotelModeSettings") > -1) {
                        files.add(f);
                        continue block21;
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
                    if ((!hasXaddr || !hasLastValue || !hasItem) && (!hasLastValue || !hasItem)) continue;
                    LOG.debug("Identified " + f.getName() + " as settings file");
                    files.add(f);
                    continue block21;
                }
            }
            catch (FileNotFoundException e) {
                LOG.error(e.getMessage(), e);
            }
            catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return files;
    }

    private static List<Setting> getCanonicalSettingFromMS2K14ForTpn142(HotelModelSettingsForTpn142 hmSettings) {
        ArrayList<Setting> result = new ArrayList<Setting>();
        if (null != hmSettings) {
            Setting s1 = new Setting();
            s1.setItem("SwitchOnSrc");
            s1.setLastValue(" " + hmSettings.getSwitchOnSrc() + " ");
            s1.setXaddr(MS2K14);
            result.add(s1);
            Setting s2 = new Setting();
            s2.setItem("SwitchOnChn");
            s2.setLastValue(" " + hmSettings.getSwitchOnChn() + " ");
            s2.setXaddr(MS2K14);
            result.add(s2);
            Setting s3 = new Setting();
            s3.setItem("SwitchOnVol");
            s3.setLastValue(" " + hmSettings.getSwitchOnVol() + " ");
            s3.setXaddr(MS2K14);
            result.add(s3);
            Setting s4 = new Setting();
            s4.setItem("MaximumVol");
            s4.setLastValue(" " + hmSettings.getMaximumVol() + " ");
            s4.setXaddr(MS2K14);
            result.add(s4);
            Setting s5 = new Setting();
            s5.setItem("SwitchOnFeature");
            s5.setLastValue(" " + hmSettings.getSwitchOnFeature() + " ");
            s5.setXaddr(MS2K14);
            result.add(s5);
            Setting s6 = new Setting();
            s6.setItem("SwitchOnPicFmt");
            s6.setLastValue(" " + hmSettings.getSwitchOnPicFmt() + " ");
            s6.setXaddr(MS2K14);
            result.add(s6);
            Setting s7 = new Setting();
            s7.setItem("PowerOn");
            s7.setLastValue(" " + hmSettings.getPowerOn() + " ");
            s7.setXaddr(MS2K14);
            result.add(s7);
            Setting s8 = new Setting();
            s8.setItem("LowPowerStandby");
            s8.setLastValue(" " + hmSettings.getLowPowerStandby() + " ");
            s8.setXaddr(MS2K14);
            result.add(s8);
            Setting s9 = new Setting();
            s9.setItem("SmartPower");
            s9.setLastValue(" " + hmSettings.getSmartPower() + " ");
            s9.setXaddr(MS2K14);
            result.add(s9);
            Setting s10 = new Setting();
            s10.setItem("RebootEveryDay");
            s10.setLastValue(" " + hmSettings.getRebootEveryDay() + " ");
            s10.setXaddr(MS2K14);
            result.add(s10);
            Setting s12 = new Setting();
            s12.setItem("DisplayWelcomeMsg");
            s12.setLastValue(" " + hmSettings.getDisplayWelcomeMsg() + " ");
            s12.setXaddr(MS2K14);
            result.add(s12);
            Setting s13 = new Setting();
            s13.setItem("WelcomeMsgLine1");
            s13.setLastValue(" " + hmSettings.getWelcomeMsgLine1() + " ");
            s13.setXaddr(MS2K14);
            result.add(s13);
            Setting s14 = new Setting();
            s14.setItem("WelcomeMsgLine2");
            s14.setLastValue(" " + hmSettings.getWelcomeMsgLine2() + " ");
            s14.setXaddr(MS2K14);
            result.add(s14);
            Setting s15 = new Setting();
            s15.setItem("WelcomeMsgTimeOut");
            s15.setLastValue(" " + hmSettings.getWelcomeMsgTimeOut() + " ");
            s15.setXaddr(MS2K14);
            result.add(s15);
            Setting s16 = new Setting();
            s16.setItem("DisplayLogo");
            s16.setLastValue(" " + hmSettings.getDisplayLogo() + " ");
            s16.setXaddr(MS2K14);
            result.add(s16);
            Setting s17 = new Setting();
            s17.setItem("SmartInfo");
            s17.setLastValue(" " + hmSettings.getSmartInfo() + " ");
            s17.setXaddr(MS2K14);
            result.add(s17);
            Setting s18 = new Setting();
            s18.setItem("SmartInfoIconLabel");
            s18.setLastValue(" " + hmSettings.getSmartInfoIconLabel() + " ");
            s18.setXaddr(MS2K14);
            result.add(s18);
            Setting s19 = new Setting();
            s19.setItem("KBLock");
            s19.setLastValue(" " + hmSettings.getKbLock() + " ");
            s19.setXaddr(MS2K14);
            result.add(s19);
            Setting s20 = new Setting();
            s20.setItem("RCLock");
            s20.setLastValue(" " + hmSettings.getRcLock() + " ");
            s20.setXaddr(MS2K14);
            result.add(s20);
            Setting s21 = new Setting();
            s21.setItem("OSDDisplay");
            s21.setLastValue(" " + hmSettings.getOsdDisplay() + " ");
            s21.setXaddr(MS2K14);
            result.add(s21);
            Setting s22 = new Setting();
            s22.setItem("HighSecurity");
            s22.setLastValue(" " + hmSettings.getHighSecurity() + " ");
            s22.setXaddr(MS2K14);
            result.add(s22);
            Setting s23 = new Setting();
            s23.setItem("AutoScart");
            s23.setLastValue(" " + hmSettings.getAutoScart() + " ");
            s23.setXaddr(MS2K14);
            result.add(s23);
            Setting s24 = new Setting();
            s24.setItem("USBBreakIn");
            s24.setLastValue(" " + hmSettings.getUsbBreakIn() + " ");
            s24.setXaddr(MS2K14);
            result.add(s24);
            Setting s25 = new Setting();
            s25.setItem("EnableUSB");
            s25.setLastValue(" " + hmSettings.getEnableUSB() + " ");
            s25.setXaddr(MS2K14);
            result.add(s25);
            Setting s26 = new Setting();
            s26.setItem("SXPBaudRate");
            s26.setLastValue(" " + hmSettings.getSxpBaudRate() + " ");
            s26.setXaddr(MS2K14);
            result.add(s26);
            Setting s27 = new Setting();
            s27.setItem("EnableTeletext");
            s27.setLastValue(" " + hmSettings.getEnableTeletext() + " ");
            s27.setXaddr(MS2K14);
            result.add(s27);
            Setting s28 = new Setting();
            s28.setItem("EnableMHEG");
            s28.setLastValue(" " + hmSettings.getEnableMHEG() + " ");
            s28.setXaddr(MS2K14);
            result.add(s28);
            Setting s29 = new Setting();
            s29.setItem("EnableEPG");
            s29.setLastValue(" " + hmSettings.getEnableEPG() + " ");
            s29.setXaddr(MS2K14);
            result.add(s29);
            Setting s30 = new Setting();
            s30.setItem("EnableSubtitles");
            s30.setLastValue(" " + hmSettings.getEnableSubtitles() + " ");
            s30.setXaddr(MS2K14);
            result.add(s30);
            Setting s31 = new Setting();
            s31.setItem("SubtitleOnStartup");
            s31.setLastValue(" " + hmSettings.getSubtitleOnStartup() + " ");
            s31.setXaddr(MS2K14);
            result.add(s31);
            Setting s32 = new Setting();
            s32.setItem("BlueMute");
            s32.setLastValue(" " + hmSettings.getBlueMute() + " ");
            s32.setXaddr(MS2K14);
            result.add(s32);
            Setting s33 = new Setting();
            s33.setItem("EnableCISlot");
            s33.setLastValue(" " + hmSettings.getEnableCISlot() + " ");
            s33.setXaddr(MS2K14);
            result.add(s33);
            Setting s37 = new Setting();
            s37.setItem("ScrambledProgramOSD");
            s37.setLastValue(" " + hmSettings.getScrambledProgramOSD() + " ");
            s37.setXaddr(MS2K14);
            result.add(s37);
            Setting s40 = new Setting();
            s40.setItem("EasylinkBreakIn");
            s40.setLastValue(" " + hmSettings.getEasylinkBreakIn() + " ");
            s40.setXaddr(MS2K14);
            result.add(s40);
            Setting s41 = new Setting();
            s41.setItem("EasylinkControl");
            s41.setLastValue(" " + hmSettings.getEasylinkControl() + " ");
            s41.setXaddr(MS2K14);
            result.add(s41);
            Setting s43 = new Setting();
            s43.setItem("DigitTimeout");
            s43.setLastValue(" " + hmSettings.getDigitTimeout() + " ");
            s43.setXaddr(MS2K14);
            result.add(s43);
            Setting s44 = new Setting();
            s44.setItem("SelectableAV");
            s44.setLastValue(" " + hmSettings.getSelectableAV() + " ");
            s44.setXaddr(MS2K14);
            result.add(s44);
            Setting s45 = new Setting();
            s45.setItem("WatchTV");
            s45.setLastValue(" " + hmSettings.getWatchTV() + " ");
            s45.setXaddr(MS2K14);
            result.add(s45);
            Setting s46 = new Setting();
            s46.setItem("ExternalClk");
            s46.setLastValue(" " + hmSettings.getExternalClk() + " ");
            s46.setXaddr(MS2K14);
            result.add(s46);
            Setting s47 = new Setting();
            s47.setItem("ClkBrighDimlight");
            s47.setLastValue(" " + hmSettings.getClkBrighDimlight() + " ");
            s47.setXaddr(MS2K14);
            result.add(s47);
            Setting s48 = new Setting();
            s48.setItem("ClkBrighIntenselight");
            s48.setLastValue(" " + hmSettings.getClkBrighIntenselight() + " ");
            s48.setXaddr(MS2K14);
            result.add(s48);
            Setting s49 = new Setting();
            s49.setItem("ClkLightSensor");
            s49.setLastValue(" " + hmSettings.getClkLightSensor() + " ");
            s49.setXaddr(MS2K14);
            result.add(s49);
            Setting s50 = new Setting();
            s50.setItem("TimeDownload");
            s50.setLastValue(" " + hmSettings.getTimeDownload() + " ");
            s50.setXaddr(MS2K14);
            result.add(s50);
            Setting s51 = new Setting();
            s51.setItem("TimeSetting");
            s51.setLastValue(" " + hmSettings.getTimeSetting() + " ");
            s51.setXaddr(MS2K14);
            result.add(s51);
            Setting s53 = new Setting();
            s53.setItem("ClkDownloadProgram");
            s53.setLastValue(" " + hmSettings.getClkDownloadProgram() + " ");
            s53.setXaddr(MS2K14);
            result.add(s53);
            Setting s54 = new Setting();
            s54.setItem("ClkDownloadCountry");
            s54.setLastValue(" " + hmSettings.getClkDownloadCountry() + " ");
            s54.setXaddr(MS2K14);
            result.add(s54);
            Setting s55 = new Setting();
            s55.setItem("ClkTimeZone");
            s55.setLastValue(" " + hmSettings.getClkTimeZone() + " ");
            s55.setXaddr(MS2K14);
            result.add(s55);
            Setting s56 = new Setting();
            s56.setItem("DaylightSaving");
            s56.setLastValue(" " + hmSettings.getDaylightSaving() + " ");
            s56.setXaddr(MS2K14);
            result.add(s56);
            Setting s57 = new Setting();
            s57.setItem("ClkTimeOffset");
            s57.setLastValue(" " + hmSettings.getClkTimeOffset() + " ");
            s57.setXaddr(MS2K14);
            result.add(s57);
            Setting s58 = new Setting();
            s58.setItem("ReferenceDate");
            s58.setLastValue(" " + hmSettings.getReferenceDate() + " ");
            s58.setXaddr(MS2K14);
            result.add(s58);
            Setting s59 = new Setting();
            s59.setItem("ReferenceTime");
            s59.setLastValue(" " + hmSettings.getReferenceTime() + " ");
            s59.setXaddr(MS2K14);
            result.add(s59);
            Setting s60 = new Setting();
            s60.setItem("MainSpkrEnable");
            s60.setLastValue(" " + hmSettings.getMainSpkrEnable() + " ");
            s60.setXaddr(MS2K14);
            result.add(s60);
            Setting s61 = new Setting();
            s61.setItem("IndMainSpkrMute");
            s61.setLastValue(" " + hmSettings.getIndMainSpkrMute() + " ");
            s61.setXaddr(MS2K14);
            result.add(s61);
            Setting s62 = new Setting();
            s62.setItem("DefMainSpkrVol");
            s62.setLastValue(" " + hmSettings.getDefMainSpkrVol() + " ");
            s62.setXaddr(MS2K14);
            result.add(s62);
            Setting s63 = new Setting();
            s63.setItem("AutoChnUpdate");
            s63.setLastValue(" " + hmSettings.getAutoChnUpdate() + " ");
            s63.setXaddr(MS2K14);
            result.add(s63);
            Setting s64 = new Setting();
            s64.setItem("AutoSwUpdate");
            s64.setLastValue(" " + hmSettings.getAutoSwUpdate() + " ");
            s64.setXaddr(MS2K14);
            result.add(s64);
            Setting s65 = new Setting();
            s65.setItem("SkipScrambled");
            s65.setLastValue(" " + hmSettings.getSkipScrambled() + " ");
            s65.setXaddr(MS2K14);
            result.add(s65);
            Setting s66 = new Setting();
            s66.setItem("MultiRC");
            s66.setLastValue(" " + hmSettings.getMultiRC() + " ");
            s66.setXaddr(MS2K14);
            result.add(s66);
            Setting s67 = new Setting();
            s67.setItem("MyChoice");
            s67.setLastValue(" " + hmSettings.getMyChoice() + " ");
            s67.setXaddr(MS2K14);
            result.add(s67);
            Setting s68 = new Setting();
            s68.setItem("AskForPIN");
            s68.setLastValue(" " + hmSettings.getAskForPIN() + " ");
            s68.setXaddr(MS2K14);
            result.add(s68);
            Setting s69 = new Setting();
            s69.setItem("SmartPay");
            s69.setLastValue(" " + hmSettings.getSmartPay() + " ");
            s69.setXaddr(MS2K14);
            result.add(s69);
            Setting s70 = new Setting();
            s70.setItem("AV");
            s70.setLastValue(" " + hmSettings.getAV() + " ");
            s70.setXaddr(MS2K14);
            result.add(s70);
            Setting s84 = new Setting();
            s84.setItem("VsecOverRFEnable");
            s84.setLastValue(" " + hmSettings.getVsecOverRFEnable() + " ");
            s84.setXaddr(MS2K14);
            result.add(s84);
            Setting s85 = new Setting();
            s85.setItem("EraseKeyOption");
            s85.setLastValue(" " + hmSettings.getEraseKeyOption() + " ");
            s85.setXaddr(MS2K14);
            result.add(s85);
            Setting s86 = new Setting();
            s86.setItem("VsecFrequency");
            s86.setLastValue(" " + hmSettings.getVsecFrequency() + " ");
            s86.setXaddr(MS2K14);
            result.add(s86);
            Setting s87 = new Setting();
            s87.setItem("VsecMedium");
            s87.setLastValue(" " + hmSettings.getVsecMedium() + " ");
            s87.setXaddr(MS2K14);
            result.add(s87);
            Setting s88 = new Setting();
            s88.setItem("VsecSymbolRate");
            s88.setLastValue(" " + hmSettings.getVsecSymbolRate() + " ");
            s88.setXaddr(MS2K14);
            result.add(s88);
            Setting s90 = new Setting();
            s90.setItem("RFCLFrequency");
            s90.setLastValue(" " + hmSettings.getRfclFrequency() + " ");
            s90.setXaddr(MS2K14);
            result.add(s90);
            Setting s91 = new Setting();
            s91.setItem("RFCLMedium");
            s91.setLastValue(" " + hmSettings.getRfclMedium() + " ");
            s91.setXaddr(MS2K14);
            result.add(s91);
            Setting s92 = new Setting();
            s92.setItem("RFCLSymbolRate");
            s92.setLastValue(" " + hmSettings.getRfclSymbolRate() + " ");
            s92.setXaddr(MS2K14);
            result.add(s92);
            Setting s93 = new Setting();
            s93.setItem("UpgradeMode");
            s93.setLastValue(" " + hmSettings.getUpgradeMode() + " ");
            s93.setXaddr(MS2K14);
            result.add(s93);
            Setting s94 = new Setting();
            s94.setItem("AutoUpgrade");
            s94.setLastValue(" " + hmSettings.getAutoUpgrade() + " ");
            s94.setXaddr(MS2K14);
            result.add(s94);
            Setting s95 = new Setting();
            s95.setItem("InstallationMode");
            s95.setLastValue(" " + hmSettings.getInstallationMode() + " ");
            s95.setXaddr(MS2K14);
            result.add(s95);
            Setting s96 = new Setting();
            s96.setItem("CloneMultiRC");
            s96.setLastValue(" " + hmSettings.getCloneMultiRC() + " ");
            s96.setXaddr(MS2K14);
            result.add(s96);
        }
        HashMap<String, String> cloneDefaultValues = new HashMap<String, String>();
        cloneDefaultValues.put("CloneMultiRC", "Yes");
        for (Setting v : result) {
            for (Map.Entry entry : cloneDefaultValues.entrySet()) {
                if (!v.getItem().equalsIgnoreCase((String)entry.getKey()) || !v.getLastValue().trim().equalsIgnoreCase("null")) continue;
                v.setLastValue(" " + (String)entry.getValue() + " ");
            }
        }
        return result;
    }

    private static List<Setting> getCanonicalSettingFromMS2K14(HotelModeSettings hmSettings) {
        ArrayList<Setting> result = new ArrayList<Setting>();
        if (null != hmSettings) {
            Setting s0 = new Setting();
            s0.setItem("Dashboard");
            s0.setLastValue(" " + hmSettings.getDashboard() + " ");
            s0.setXaddr(MS2K14);
            result.add(s0);
            Setting s1 = new Setting();
            s1.setItem("SwitchOnSrc");
            s1.setLastValue(" " + hmSettings.getSwitchOnSrc() + " ");
            s1.setXaddr(MS2K14);
            result.add(s1);
            Setting s2 = new Setting();
            s2.setItem("SwitchOnChn");
            s2.setLastValue(" " + hmSettings.getSwitchOnChn() + " ");
            s2.setXaddr(MS2K14);
            result.add(s2);
            Setting s3 = new Setting();
            s3.setItem("SwitchOnVol");
            s3.setLastValue(" " + hmSettings.getSwitchOnVol() + " ");
            s3.setXaddr(MS2K14);
            result.add(s3);
            Setting s4 = new Setting();
            s4.setItem("MaximumVol");
            s4.setLastValue(" " + hmSettings.getMaximumVol() + " ");
            s4.setXaddr(MS2K14);
            result.add(s4);
            Setting s5 = new Setting();
            s5.setItem("SwitchOnFeature");
            s5.setLastValue(" " + hmSettings.getSwitchOnFeature() + " ");
            s5.setXaddr(MS2K14);
            result.add(s5);
            Setting s6 = new Setting();
            s6.setItem("SwitchOnPicFmt");
            s6.setLastValue(" " + hmSettings.getSwitchOnPicFmt() + " ");
            s6.setXaddr(MS2K14);
            result.add(s6);
            Setting s7 = new Setting();
            s7.setItem("PowerOn");
            s7.setLastValue(" " + hmSettings.getPowerOn() + " ");
            s7.setXaddr(MS2K14);
            result.add(s7);
            Setting s8 = new Setting();
            s8.setItem("LowPowerStandby");
            s8.setLastValue(" " + hmSettings.getLowPowerStandby() + " ");
            s8.setXaddr(MS2K14);
            result.add(s8);
            Setting s9 = new Setting();
            s9.setItem("SmartPower");
            s9.setLastValue(" " + hmSettings.getSmartPower() + " ");
            s9.setXaddr(MS2K14);
            result.add(s9);
            Setting s10 = new Setting();
            s10.setItem("RebootEveryDay");
            s10.setLastValue(" " + hmSettings.getRebootEveryDay() + " ");
            s10.setXaddr(MS2K14);
            result.add(s10);
            Setting s11 = new Setting();
            s11.setItem("WakeOnLAN");
            s11.setLastValue(" " + hmSettings.getWakeOnLAN() + " ");
            s11.setXaddr(MS2K14);
            result.add(s11);
            Setting s12 = new Setting();
            s12.setItem("DisplayWelcomeMsg");
            s12.setLastValue(" " + hmSettings.getDisplayWelcomeMsg() + " ");
            s12.setXaddr(MS2K14);
            result.add(s12);
            Setting s13 = new Setting();
            s13.setItem("WelcomeMsgLine1");
            s13.setLastValue(" " + hmSettings.getWelcomeMsgLine1() + " ");
            s13.setXaddr(MS2K14);
            result.add(s13);
            Setting s14 = new Setting();
            s14.setItem("WelcomeMsgLine2");
            s14.setLastValue(" " + hmSettings.getWelcomeMsgLine2() + " ");
            s14.setXaddr(MS2K14);
            result.add(s14);
            Setting s15 = new Setting();
            s15.setItem("WelcomeMsgTimeOut");
            s15.setLastValue(" " + hmSettings.getWelcomeMsgTimeOut() + " ");
            s15.setXaddr(MS2K14);
            result.add(s15);
            Setting s16 = new Setting();
            s16.setItem("DisplayLogo");
            s16.setLastValue(" " + hmSettings.getDisplayLogo() + " ");
            s16.setXaddr(MS2K14);
            result.add(s16);
            Setting s17 = new Setting();
            s17.setItem("SmartInfo");
            s17.setLastValue(" " + hmSettings.getSmartInfo() + " ");
            s17.setXaddr(MS2K14);
            result.add(s17);
            Setting s18 = new Setting();
            s18.setItem("SmartInfoIconLabel");
            s18.setLastValue(" " + hmSettings.getSmartInfoIconLabel() + " ");
            s18.setXaddr(MS2K14);
            result.add(s18);
            Setting s19 = new Setting();
            s19.setItem("KBLock");
            s19.setLastValue(" " + hmSettings.getKBLock() + " ");
            s19.setXaddr(MS2K14);
            result.add(s19);
            Setting s20 = new Setting();
            s20.setItem("RCLock");
            s20.setLastValue(" " + hmSettings.getRCLock() + " ");
            s20.setXaddr(MS2K14);
            result.add(s20);
            Setting s21 = new Setting();
            s21.setItem("OSDDisplay");
            s21.setLastValue(" " + hmSettings.getOSDDisplay() + " ");
            s21.setXaddr(MS2K14);
            result.add(s21);
            Setting s22 = new Setting();
            s22.setItem("HighSecurity");
            s22.setLastValue(" " + hmSettings.getHighSecurity() + " ");
            s22.setXaddr(MS2K14);
            result.add(s22);
            Setting s23 = new Setting();
            s23.setItem("AutoScart");
            s23.setLastValue(" " + hmSettings.getAutoScart() + " ");
            s23.setXaddr(MS2K14);
            result.add(s23);
            Setting s24 = new Setting();
            s24.setItem("USBBreakIn");
            s24.setLastValue(" " + hmSettings.getUSBBreakIn() + " ");
            s24.setXaddr(MS2K14);
            result.add(s24);
            Setting s25 = new Setting();
            s25.setItem("EnableUSB");
            s25.setLastValue(" " + hmSettings.getEnableUSB() + " ");
            s25.setXaddr(MS2K14);
            result.add(s25);
            Setting s26 = new Setting();
            s26.setItem("SXPBaudRate");
            s26.setLastValue(" " + hmSettings.getSXPBaudRate() + " ");
            s26.setXaddr(MS2K14);
            result.add(s26);
            Setting s27 = new Setting();
            s27.setItem("EnableTeletext");
            s27.setLastValue(" " + hmSettings.getEnableTeletext() + " ");
            s27.setXaddr(MS2K14);
            result.add(s27);
            Setting s28 = new Setting();
            s28.setItem("EnableMHEG");
            s28.setLastValue(" " + hmSettings.getEnableMHEG() + " ");
            s28.setXaddr(MS2K14);
            result.add(s28);
            Setting s29 = new Setting();
            s29.setItem("EnableEPG");
            s29.setLastValue(" " + hmSettings.getEnableEPG() + " ");
            s29.setXaddr(MS2K14);
            result.add(s29);
            Setting s30 = new Setting();
            s30.setItem("EnableSubtitles");
            s30.setLastValue(" " + hmSettings.getEnableSubtitles() + " ");
            s30.setXaddr(MS2K14);
            result.add(s30);
            Setting s31 = new Setting();
            s31.setItem("SubtitleOnStartup");
            s31.setLastValue(" " + hmSettings.getSubtitleOnStartup() + " ");
            s31.setXaddr(MS2K14);
            result.add(s31);
            Setting s32 = new Setting();
            s32.setItem("BlueMute");
            s32.setLastValue(" " + hmSettings.getBlueMute() + " ");
            s32.setXaddr(MS2K14);
            result.add(s32);
            Setting s33 = new Setting();
            s33.setItem("EnableCISlot");
            s33.setLastValue(" " + hmSettings.getEnableCISlot() + " ");
            s33.setXaddr(MS2K14);
            result.add(s33);
            Setting s34 = new Setting();
            s34.setItem("WiFiCrossConnect");
            s34.setLastValue(" " + hmSettings.getWiFiCrossConnect() + " ");
            s34.setXaddr(MS2K14);
            result.add(s34);
            Setting s35 = new Setting();
            s35.setItem("WiFiMiraCast");
            s35.setLastValue(" " + hmSettings.getWiFiMiraCast() + " ");
            s35.setXaddr(MS2K14);
            result.add(s35);
            Setting s36 = new Setting();
            s36.setItem("DirectShare");
            s36.setLastValue(" " + hmSettings.getDirectShare() + " ");
            s36.setXaddr(MS2K14);
            result.add(s36);
            Setting s37 = new Setting();
            s37.setItem("ScrambledProgramOSD");
            s37.setLastValue(" " + hmSettings.getScrambledProgramOSD() + " ");
            s37.setXaddr(MS2K14);
            result.add(s37);
            Setting s38 = new Setting();
            s38.setItem("WiFiLostOSD");
            s38.setLastValue(" " + hmSettings.getWiFiLostOSD() + " ");
            s38.setXaddr(MS2K14);
            result.add(s38);
            Setting s39 = new Setting();
            s39.setItem("JointSpace");
            s39.setLastValue(" " + hmSettings.getJointSpace() + " ");
            s39.setXaddr(MS2K14);
            result.add(s39);
            Setting s40 = new Setting();
            s40.setItem("EasylinkBreakIn");
            s40.setLastValue(" " + hmSettings.getEasylinkBreakIn() + " ");
            s40.setXaddr(MS2K14);
            result.add(s40);
            Setting s41 = new Setting();
            s41.setItem("EasylinkControl");
            s41.setLastValue(" " + hmSettings.getEasylinkControl() + " ");
            s41.setXaddr(MS2K14);
            result.add(s41);
            Setting s42 = new Setting();
            s42.setItem("EnableSkype");
            s42.setLastValue(" " + hmSettings.getEnableSkype() + " ");
            s42.setXaddr(MS2K14);
            result.add(s42);
            Setting s43 = new Setting();
            s43.setItem("DigitTimeout");
            s43.setLastValue(" " + hmSettings.getDigitTimeout() + " ");
            s43.setXaddr(MS2K14);
            result.add(s43);
            Setting s44 = new Setting();
            s44.setItem("SelectableAV");
            s44.setLastValue(" " + hmSettings.getSelectableAV() + " ");
            s44.setXaddr(MS2K14);
            result.add(s44);
            Setting s45 = new Setting();
            s45.setItem("WatchTV");
            s45.setLastValue(" " + hmSettings.getWatchTV() + " ");
            s45.setXaddr(MS2K14);
            result.add(s45);
            Setting s46 = new Setting();
            s46.setItem("ExternalClk");
            s46.setLastValue(" " + hmSettings.getExternalClk() + " ");
            s46.setXaddr(MS2K14);
            result.add(s46);
            Setting s47 = new Setting();
            s47.setItem("ClkBrighDimlight");
            s47.setLastValue(" " + hmSettings.getClkBrighDimlight() + " ");
            s47.setXaddr(MS2K14);
            result.add(s47);
            Setting s48 = new Setting();
            s48.setItem("ClkBrighIntenselight");
            s48.setLastValue(" " + hmSettings.getClkBrighIntenselight() + " ");
            s48.setXaddr(MS2K14);
            result.add(s48);
            Setting s49 = new Setting();
            s49.setItem("ClkLightSensor");
            s49.setLastValue(" " + hmSettings.getClkLightSensor() + " ");
            s49.setXaddr(MS2K14);
            result.add(s49);
            Setting s50 = new Setting();
            s50.setItem("TimeDownload");
            s50.setLastValue(" " + hmSettings.getTimeDownload() + " ");
            s50.setXaddr(MS2K14);
            result.add(s50);
            Setting s51 = new Setting();
            s51.setItem("TimeSetting");
            s51.setLastValue(" " + hmSettings.getTimeSetting() + " ");
            s51.setXaddr(MS2K14);
            result.add(s51);
            Setting s52 = new Setting();
            s52.setItem("ClkNTPSvrURL");
            s52.setLastValue(" " + hmSettings.getClkNTPSvrURL() + " ");
            s52.setXaddr(MS2K14);
            result.add(s52);
            Setting s53 = new Setting();
            s53.setItem("ClkDownloadProgram");
            s53.setLastValue(" " + hmSettings.getClkDownloadProgram() + " ");
            s53.setXaddr(MS2K14);
            result.add(s53);
            Setting s54 = new Setting();
            s54.setItem("ClkDownloadCountry");
            s54.setLastValue(" " + hmSettings.getClkDownloadCountry() + " ");
            s54.setXaddr(MS2K14);
            result.add(s54);
            Setting s55 = new Setting();
            s55.setItem("ClkTimeZone");
            s55.setLastValue(" " + hmSettings.getClkTimeZone() + " ");
            s55.setXaddr(MS2K14);
            result.add(s55);
            Setting s56 = new Setting();
            s56.setItem("DaylightSaving");
            s56.setLastValue(" " + hmSettings.getDaylightSaving() + " ");
            s56.setXaddr(MS2K14);
            result.add(s56);
            Setting s57 = new Setting();
            s57.setItem("ClkTimeOffset");
            s57.setLastValue(" " + hmSettings.getClkTimeOffset() + " ");
            s57.setXaddr(MS2K14);
            result.add(s57);
            Setting s58 = new Setting();
            s58.setItem("ReferenceDate");
            s58.setLastValue(" " + hmSettings.getReferenceDate() + " ");
            s58.setXaddr(MS2K14);
            result.add(s58);
            Setting s59 = new Setting();
            s59.setItem("ReferenceTime");
            s59.setLastValue(" " + hmSettings.getReferenceTime() + " ");
            s59.setXaddr(MS2K14);
            result.add(s59);
            Setting s60 = new Setting();
            s60.setItem("MainSpkrEnable");
            s60.setLastValue(" " + hmSettings.getMainSpkrEnable() + " ");
            s60.setXaddr(MS2K14);
            result.add(s60);
            Setting s61 = new Setting();
            s61.setItem("IndMainSpkrMute");
            s61.setLastValue(" " + hmSettings.getIndMainSpkrMute() + " ");
            s61.setXaddr(MS2K14);
            result.add(s61);
            Setting s62 = new Setting();
            s62.setItem("DefMainSpkrVol");
            s62.setLastValue(" " + hmSettings.getDefMainSpkrVol() + " ");
            s62.setXaddr(MS2K14);
            result.add(s62);
            Setting s63 = new Setting();
            s63.setItem("AutoChnUpdate");
            s63.setLastValue(" " + hmSettings.getAutoChnUpdate() + " ");
            s63.setXaddr(MS2K14);
            result.add(s63);
            Setting s64 = new Setting();
            s64.setItem("AutoSwUpdate");
            s64.setLastValue(" " + hmSettings.getAutoSwUpdate() + " ");
            s64.setXaddr(MS2K14);
            result.add(s64);
            Setting s65 = new Setting();
            s65.setItem("SkipScrambled");
            s65.setLastValue(" " + hmSettings.getSkipScrambled() + " ");
            s65.setXaddr(MS2K14);
            result.add(s65);
            Setting s66 = new Setting();
            s66.setItem("MultiRC");
            s66.setLastValue(" " + hmSettings.getMultiRC() + " ");
            s66.setXaddr(MS2K14);
            result.add(s66);
            Setting s67 = new Setting();
            s67.setItem("MyChoice");
            s67.setLastValue(" " + hmSettings.getMyChoice() + " ");
            s67.setXaddr(MS2K14);
            result.add(s67);
            Setting s68 = new Setting();
            s68.setItem("AskForPIN");
            s68.setLastValue(" " + hmSettings.getAskForPIN() + " ");
            s68.setXaddr(MS2K14);
            result.add(s68);
            Setting s69 = new Setting();
            s69.setItem("SmartPay");
            s69.setLastValue(" " + hmSettings.getSmartPay() + " ");
            s69.setXaddr(MS2K14);
            result.add(s69);
            Setting s70 = new Setting();
            s70.setItem("AV");
            s70.setLastValue(" " + hmSettings.getAV() + " ");
            s70.setXaddr(MS2K14);
            result.add(s70);
            Setting s71 = new Setting();
            s71.setItem("SmartTV");
            s71.setLastValue(" " + hmSettings.getSmartTV() + " ");
            s71.setXaddr(MS2K14);
            result.add(s71);
            Setting s72 = new Setting();
            s72.setItem("AppControlID");
            s72.setLastValue(" " + hmSettings.getAppControlID() + " ");
            s72.setXaddr(MS2K14);
            result.add(s72);
            Setting s73 = new Setting();
            s73.setItem("ProfileName");
            s73.setLastValue(" " + hmSettings.getProfileName() + " ");
            s73.setXaddr(MS2K14);
            result.add(s73);
            Setting s74 = new Setting();
            s74.setItem("Source");
            s74.setLastValue(" " + hmSettings.getSource() + " ");
            s74.setXaddr(MS2K14);
            result.add(s74);
            Setting s75 = new Setting();
            s75.setItem("Fallback");
            s75.setLastValue(" " + hmSettings.getFallback() + " ");
            s75.setXaddr(MS2K14);
            result.add(s75);
            Setting s76 = new Setting();
            s76.setItem("DashboardIconLabel");
            s76.setLastValue(" " + hmSettings.getDashboardIconLabel() + " ");
            s76.setXaddr(MS2K14);
            result.add(s76);
            Setting s77 = new Setting();
            s77.setItem("ServerUIURL");
            s77.setLastValue(" " + hmSettings.getServerUIURL() + " ");
            s77.setXaddr(MS2K14);
            result.add(s77);
            Setting s78 = new Setting();
            s78.setItem("WebServicesURL");
            s78.setLastValue(" " + hmSettings.getWebServicesURL() + " ");
            s78.setXaddr(MS2K14);
            result.add(s78);
            Setting s79 = new Setting();
            s79.setItem("TVDiscoveryService");
            s79.setLastValue(" " + hmSettings.getTVDiscoveryService() + " ");
            s79.setXaddr(MS2K14);
            result.add(s79);
            Setting s80 = new Setting();
            s80.setItem("ProfessionalSettingsService");
            s80.setLastValue(" " + hmSettings.getProfessionalSettingsService() + " ");
            s80.setXaddr(MS2K14);
            result.add(s80);
            Setting s81 = new Setting();
            s81.setItem("IPUpgradeService");
            s81.setLastValue(" " + hmSettings.getIPUpgradeService() + " ");
            s81.setXaddr(MS2K14);
            result.add(s81);
            Setting s82 = new Setting();
            s82.setItem("PowerService");
            s82.setLastValue(" " + hmSettings.getPowerService() + " ");
            s82.setXaddr(MS2K14);
            result.add(s82);
            Setting s84 = new Setting();
            s84.setItem("VsecOverRFEnable");
            s84.setLastValue(" " + hmSettings.getVsecOverRFEnable() + " ");
            s84.setXaddr(MS2K14);
            result.add(s84);
            Setting s85 = new Setting();
            s85.setItem("EraseKeyOption");
            s85.setLastValue(" " + hmSettings.getEraseKeyOption() + " ");
            s85.setXaddr(MS2K14);
            result.add(s85);
            Setting s86 = new Setting();
            s86.setItem("VsecFrequency");
            s86.setLastValue(" " + hmSettings.getVsecFrequency() + " ");
            s86.setXaddr(MS2K14);
            result.add(s86);
            Setting s87 = new Setting();
            s87.setItem("VsecMedium");
            s87.setLastValue(" " + hmSettings.getVsecMedium() + " ");
            s87.setXaddr(MS2K14);
            result.add(s87);
            Setting s88 = new Setting();
            s88.setItem("VsecSymbolRate");
            s88.setLastValue(" " + hmSettings.getVsecSymbolRate() + " ");
            s88.setXaddr(MS2K14);
            result.add(s88);
            Setting s90 = new Setting();
            s90.setItem("RFCLFrequency");
            s90.setLastValue(" " + hmSettings.getRFCLFrequency() + " ");
            s90.setXaddr(MS2K14);
            result.add(s90);
            Setting s91 = new Setting();
            s91.setItem("RFCLMedium");
            s91.setLastValue(" " + hmSettings.getRFCLMedium() + " ");
            s91.setXaddr(MS2K14);
            result.add(s91);
            Setting s92 = new Setting();
            s92.setItem("RFCLSymbolRate");
            s92.setLastValue(" " + hmSettings.getRFCLSymbolRate() + " ");
            s92.setXaddr(MS2K14);
            result.add(s92);
            Setting s93 = new Setting();
            s93.setItem("UpgradeMode");
            s93.setLastValue(" " + hmSettings.getUpgradeMode() + " ");
            s93.setXaddr(MS2K14);
            result.add(s93);
            Setting s94 = new Setting();
            s94.setItem("AutoUpgrade");
            s94.setLastValue(" " + hmSettings.getAutoUpgrade() + " ");
            s94.setXaddr(MS2K14);
            result.add(s94);
            Setting s95 = new Setting();
            s95.setItem("InstallationMode");
            s95.setLastValue(" " + hmSettings.getInstallationMode() + " ");
            s95.setXaddr(MS2K14);
            result.add(s95);
            Setting s96 = new Setting();
            s96.setItem("CloneMultiRC");
            s96.setLastValue(" " + hmSettings.getCloneMultiRC() + " ");
            s96.setXaddr(MS2K14);
            result.add(s96);
        }
        HashMap<String, String> cloneDefaultValues = new HashMap<String, String>();
        cloneDefaultValues.put("CloneMultiRC", "Yes");
        for (Setting v : result) {
            for (Map.Entry entry : cloneDefaultValues.entrySet()) {
                if (!v.getItem().equalsIgnoreCase((String)entry.getKey()) || !v.getLastValue().trim().equalsIgnoreCase("null")) continue;
                v.setLastValue(" " + (String)entry.getValue() + " ");
            }
        }
        return result;
    }

    private static List<Setting> getCanonicalSettingFrom2K16(File file, String platformId) {
        TVSettings tvSettings = null;
        try {
            String path = file.toPath() + "/TVSettings.xml";
            tvSettings = JaxbReadXml.readString(TVSettings.class, path);
        }
        catch (JAXBException e) {
            LOG.error(e.getMessage(), e);
        }
        String refdata = MS2K16;
        if ("TPN161HE_CloneData".equalsIgnoreCase(platformId)) {
            refdata = ES2K16;
        }
        ArrayList<Setting> result = new ArrayList<Setting>();
        if (null != tvSettings) {
            String majorVerNo = tvSettings.getSchemaVersion().getMajorVerNo();
            String minorVerNo = tvSettings.getSchemaVersion().getMinorVerNo();
            Setting s91 = new Setting();
            s91.setItem("majorVerNo");
            s91.setLastValue(majorVerNo);
            s91.setRefFile(refdata);
            s91.setXaddr(refdata);
            Setting s92 = new Setting();
            s92.setItem("minorVerNo");
            s92.setLastValue(minorVerNo);
            s92.setRefFile(refdata);
            s92.setXaddr(refdata);
            List<Item> item = tvSettings.getItem();
            result.add(s91);
            result.add(s92);
            for (Item o : item) {
                Setting s96 = new Setting();
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
        List<File> settingFiles = UploadCloneUtils.findSettingsFiles(file);
        List<File> crcFiles = UploadCloneUtils.findCRCFiles(file);
        List<File> childVariants = UploadCloneUtils.getListOfChildVariants(file);
        List<File> binFiles = TpvFileUtils.findFiles(file, null);
        List<com.tpvision.smartinstall.xml.File> mustHaveFiles = UploadCloneUtils.getMustFiles(file.getName());
        for (com.tpvision.smartinstall.xml.File file2 : mustHaveFiles) {
            if (UploadCloneUtils.searchFile(file, file2.getName())) continue;
            throw new MalformedCloneDataException("Missing Setting file");
        }
        List<Channel> mustHaveChannelFiles = UploadCloneUtils.getMustHaveChannelFiles(file.getName());
        for (Channel fs : mustHaveChannelFiles) {
            if (UploadCloneUtils.searchFile(file, fs.getFileName())) continue;
            throw new MalformedCloneDataException("Missing Channel file");
        }
        String string = UploadCloneUtils.getPlatform(settingFiles);
        if (string == null) {
            return null;
        }
        platform.setSwver(string);
        platform.setCloneRootFolderName(string);
        platform.setId(file.getName());
        if (null == childVariants || childVariants.size() > 0) {
            // empty if block
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
        CrcFiles crcFilesList = UploadCloneUtils.getCrcFileDetails(crcFiles, platform);
        platform.setCrcFiles(crcFilesList);
        Settings settings = UploadCloneUtils.getSettingsInfo(settingFiles, platform);
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
        ArrayList<Channel> ret = new ArrayList<Channel>();
        for (Platform p : conf.getPlatform()) {
            if (!p.getChannel().isIsMust() || !platformName.equalsIgnoreCase(p.getCloneRootFolderName())) continue;
            ret.add(p.getChannel());
        }
        return ret;
    }

    private static List<com.tpvision.smartinstall.xml.File> getMustFiles(String platformName) {
        Config conf = SmartInstallConfiguration.instance().getConfig();
        ArrayList<com.tpvision.smartinstall.xml.File> ret = new ArrayList<com.tpvision.smartinstall.xml.File>();
        if (null != conf) {
            for (Platform p : conf.getPlatform()) {
                for (com.tpvision.smartinstall.xml.File f : p.getSettingFiles().getFile()) {
                    if (f.isIsMust() == null || !f.isIsMust().booleanValue() || !platformName.equalsIgnoreCase(p.getCloneRootFolderName())) continue;
                    ret.add(f);
                }
            }
        }
        return ret;
    }

    private static Settings getSettingsInfo(List<File> settingFiles, Platform platform) {
        Settings ret = new Settings();
        for (File f : settingFiles) {
            List<Setting> settingList = UploadCloneUtils.analyzeSettings(f);
            ret.getSetting().addAll(settingList);
        }
        return ret;
    }

    private static List<Setting> analyzeSettings(File f) {
        ArrayList<Setting> ret = new ArrayList<Setting>();
        try (FileReader fread = new FileReader(f);
             BufferedReader bread = new BufferedReader(fread);){
            String line;
            Integer counter = -1;
            boolean finishedSettingInfo = false;
            String itemStr = null;
            String itemStr1 = null;
            String lastValueStr = null;
            String lastValueStr1 = null;
            String xaddrStr = null;
            int itemLength = new String("<item>").length();
            int itemLength1 = new String("<ItemID>").length();
            while ((line = bread.readLine()) != null) {
                Integer n = counter;
                Integer n2 = counter = Integer.valueOf(counter + 1);
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
                if (!finishedSettingInfo) continue;
                Setting set = new Setting();
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
        catch (FileNotFoundException e) {
            LOG.error(e.getMessage(), e);
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return ret;
    }

    private static CrcFiles getCrcFileDetails(List<File> crcFiles, Platform platform) {
        CrcFiles ret = new CrcFiles();
        for (File f : crcFiles) {
            ret.getFile().add(UploadCloneUtils.analyseFileForCrc(f, platform));
        }
        return ret;
    }

    private static String getPlatform(List<File> settingFiles) {
        File f = null;
        String itemStr = null;
        for (File file : settingFiles) {
            String absoluteName = file.getName();
            if (!absoluteName.equalsIgnoreCase("BDSSettings.txt")) continue;
            f = file;
            break;
        }
        if (f == null) {
            return null;
        }
        try (FileReader fread = new FileReader(f);
             BufferedReader bread = new BufferedReader(fread);){
            String line;
            while ((line = bread.readLine()) != null) {
                if (line.indexOf("<swver>") <= -1) continue;
                itemStr = line.substring(line.indexOf("<swver>") + "<swver>".length(), line.indexOf("</swver>"));
                break;
            }
        }
        catch (IOException e) {
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
        try (FileReader fread = new FileReader(f);
             BufferedReader bread = new BufferedReader(fread);){
            String line;
            boolean firstLine = true;
            Integer counter = -1;
            while ((line = bread.readLine()) != null) {
                Integer n = counter;
                Integer n2 = counter = Integer.valueOf(counter + 1);
                if (line.trim().equals("")) continue;
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] split = line.split(":");
                String prefix = split[0];
                Crc crc = new Crc();
                crc.setLineNo(new BigInteger(counter.toString()));
                crc.setPrefix(prefix);
                ret.getCrc().add(crc);
            }
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return ret;
    }

    private static List<File> getListOfChildVariants(File file) {
        ArrayList<File> files = new ArrayList<File>();
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
        LOG.info("[processUploadMsg]tyType:{},tvUniqueID:{}", (Object)tvType, (Object)tvUniqueId);
        JSONObject cloneToServerSessionStatus = cloneToServerParameters.getJSONObject("CloneToServerSessionStatus");
        String sessionStatus = cloneToServerSessionStatus.optString("SessionStatus");
        String sessionEndTime = cloneToServerSessionStatus.optString("SessionEndTime");
        int uploadItemsRecvCountCmndCount = 0;
        int uploadItemsCountTvCount = 0;
        if (uploadItemsRecvCount_cmnd.containsKey(tvUniqueId)) {
            uploadItemsRecvCountCmndCount = uploadItemsRecvCount_cmnd.get(tvUniqueId);
            LOG.info("uploadItemsRecvCount_cmnd_count:{}", (Object)uploadItemsRecvCountCmndCount);
            uploadItemsRecvCount_cmnd.remove(tvUniqueId);
        } else {
            LOG.error("uploadItemsRecvCount_cmnd_count not containsKey: {}", (Object)tvUniqueId);
        }
        if (uploadItemsCount_tv.containsKey(tvUniqueId)) {
            uploadItemsCountTvCount = uploadItemsCount_tv.get(tvUniqueId);
            uploadItemsCount_tv.remove(tvUniqueId);
            LOG.info("uploadItemsCount_tv_count:{}", (Object)uploadItemsCountTvCount);
        } else {
            LOG.error("uploadItemsCount_tv_count not containsKey: {}", (Object)tvUniqueId);
        }
        if (uploadItemsCountTvCount > 0 && uploadItemsRecvCountCmndCount > 0) {
            UploadCloneUtils.handleFinishDownloadCloneFiles(tv);
        }
        DevicesManager dmgr = JpaManager.getDevicesManager();
        tv.setCloneMode("Upgrade");
        tv.setUploadProgress("ST");
        tv.setUploadSessionStatus(sessionStatus);
        tv.setUploadSessionEnd(sessionEndTime);
        dmgr.save(tv);
        IPTVPooling.notifyUploadStatusChange(UploadCloneUtils.getUploadResult(tvUniqueId, cloneToServerParameters));
        return "";
    }

    private static JSONObject getUploadResult(String tvUID, JSONObject cloneToServerParameters) {
        List<String> availableSettingNames = UploadCloneUtils.getAvailableSettingNames(cloneToServerParameters);
        return UploadCloneUtils.checkUploadResult(availableSettingNames, tvUID);
    }

    private static List<String> getAvailableSettingNames(JSONObject cloneToServerParameters) {
        ArrayList<String> settingNames = new ArrayList<String>();
        JSONArray cloneItemsAvailableToServer = cloneToServerParameters.getJSONArray("CloneItemsAvailableToServer");
        for (int i = 0; i < cloneItemsAvailableToServer.length(); ++i) {
            String cloneItemName;
            JSONObject item = (JSONObject)cloneItemsAvailableToServer.get(i);
            if (!item.has("CloneItemName") || "MainFirmware".equalsIgnoreCase(cloneItemName = item.optString("CloneItemName")) || "".equals(cloneItemName)) continue;
            settingNames.add(CloneItemUtils.convertJapitNameToItem(cloneItemName));
        }
        LOG.info("settingNames.size():{}", (Object)settingNames.size());
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
                if (!fileName.endsWith(".zip")) continue;
                fileName = fileName.substring(0, fileName.indexOf(".zip"));
                availableSettingNames.remove(fileName);
            }
        }
        if (null != availableSettingNames && !availableSettingNames.isEmpty()) {
            return new JSONObject("{\"status\":\"fail\"}").put("reason", availableSettingNames.size() + " clone failed to download!");
        }
        return new JSONObject("{\"status\":\"success\"}");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void handleFinishDownloadCloneFiles(Devices device) {
        String configName = null;
        String tvUniqueID = device.getTvuniqueid();
        try {
            File[] files;
            String platformId = PlatformUtils.getPlatformId(device.getType());
            configName = UploadCloneUtils.generateSaveCloneName(platformId);
            LOG.info("Download from TV -- configName == {}", (Object)configName);
            String baseUploadPath = CommonConstants.SISERVER_UPLOAD_DIR + device.getTvuniqueid();
            String unZipCloneLocationStr = baseUploadPath + "/" + configName;
            File unZipCloneLocation = new File(unZipCloneLocationStr);
            FileUtils.deleteQuietly(unZipCloneLocation);
            File root = new File(baseUploadPath);
            String masterCloneDataPath = unZipCloneLocationStr + "/" + platformId + "/MasterCloneData";
            File temp = new File(masterCloneDataPath);
            FileUtils.forceMkdir(temp);
            for (File zipFile : files = root.listFiles()) {
                if (!zipFile.isFile()) continue;
                if (zipFile.getName().contains("DataDump")) {
                    String dataDumpPath = baseUploadPath + "/" + configName + "/" + platformId;
                    ZipCommonUtils.unzip(zipFile, dataDumpPath);
                    continue;
                }
                ZipCommonUtils.unzip(zipFile, masterCloneDataPath);
            }
            UploadCloneUtils.loadAllConfToDb(unZipCloneLocationStr, "admin", configName, "USER_DEFINED", platformId);
        }
        catch (Exception e) {
            LOG.info("-> TV Upload : Zip file process error!!!!!!");
            SettingManager smgr = JpaManager.getSettingManager();
            List<com.tpvision.smartinstall.dao.core.Setting> settings = smgr.findSettingsByName(configName);
            if (!settings.isEmpty()) {
                smgr.deleteByKey(settings.get(0).getId());
            }
            LOG.error("Unable to upload the last file, some of the required files are missing.", e);
        }
        finally {
            DevicesManager dmgr = JpaManager.getDevicesManager();
            Devices tv = dmgr.loadByKey(tvUniqueID);
            tv.setUploadProgress("ST");
            dmgr.save(tv);
            LOG.info("After save uploading clonedata to DB, set tv: {} UploadProgress to ST", (Object)tvUniqueID);
        }
    }

    private static String readFileContent(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            }
            catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }
        return "";
    }
}

