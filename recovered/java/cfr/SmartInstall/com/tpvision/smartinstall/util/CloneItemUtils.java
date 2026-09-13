/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

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
import com.tpvision.smartinstall.dao.mgr.ChannelPackageManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.SettingManager;
import com.tpvision.smartinstall.dao.mgr.SettingPackageManager;
import com.tpvision.smartinstall.dao.mgr.UpgSettingManager;
import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.ContentUtils;
import com.tpvision.smartinstall.util.IPUpgradeManager;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.TpvStringUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CloneItemUtils {
    private static final Logger LOG = LoggerFactory.getLogger(CloneItemUtils.class);
    public static final int MAX_INDENTIFIER_LENGTH = 32;
    private static Map<String, String> japitNameToShowNameMap = new HashMap<String, String>();
    private static Map<String, String> showNameToJapitNameMap;

    private CloneItemUtils() {
    }

    public static CloneItemInfo getCloneItemInfo(CommonConstants.CloneItemType itemType, int id) {
        CloneItemInfo info = new CloneItemInfo();
        info.setId(id);
        info.setClone(false);
        info.setItemType(itemType);
        switch (itemType) {
            case Clone: {
                SettingManager settingMgr = JpaManager.getSettingManager();
                Setting setting = settingMgr.loadByKey(id);
                if (null == setting) break;
                info.name = setting.getClonerename();
                info.version = TpvDateUtils.formatSiCloneIdentifiers(PlatformUtils.getPlatformName(setting.getPlatform()), setting.getLastUpdatedDate());
                info.platform = PlatformUtils.getPlatformName(setting.getPlatform());
                info.setClone(true);
                info.setLastUpdated(setting.getLastUpdatedDate());
                break;
            }
            case Firmware: {
                UpgSettingManager upgSettingMgr = JpaManager.getUpgSettingManager();
                UpgSetting upgSetting = upgSettingMgr.loadByKey(id);
                if (null == upgSetting) break;
                info.name = upgSetting.getUpgrename();
                info.version = upgSetting.getIpversion();
                info.platform = PlatformUtils.getPlatformName(upgSetting.getPlatform());
                info.setLastUpdated(upgSetting.getLastUpdatedDate());
                break;
            }
            case TVSettings: {
                SettingPackageManager settingpackageMgr = JpaManager.getSettingPackageManager();
                SettingPackage settingpackage = settingpackageMgr.loadByKey(id);
                if (null == settingpackage) break;
                info.name = settingpackage.getName();
                info.version = settingpackage.getLastEdit();
                info.platform = PlatformUtils.getPlatformName(settingpackage.getPlatform());
                info.setLastUpdated(TpvDateUtils.convertIndentiferTimsStringToDate(settingpackage.getLastEdit()));
                break;
            }
            case ChannelList: 
            case MediaChannels: {
                ChannelPackageManager channelpackageMgr = JpaManager.getChannelPackageManager();
                ChannelPackage channelpackage = channelpackageMgr.loadByKey(id);
                if (null == channelpackage) break;
                info.name = channelpackage.getName();
                info.version = channelpackage.getLastEdit();
                info.platform = PlatformUtils.getPlatformName(channelpackage.getPlatform());
                info.setLastUpdated(TpvDateUtils.convertIndentiferTimsStringToDate(channelpackage.getLastEdit()));
                break;
            }
            case AndroidApps: {
                AppPackageManager apppackageMgr = JpaManager.getAppPackageManager();
                AppPackage apppackage = apppackageMgr.loadByKey(id);
                if (null == apppackage) break;
                info.name = apppackage.getName();
                info.version = apppackage.getLastEdit();
                info.platform = PlatformUtils.getPlatformName(apppackage.getPlatform());
                info.setLastUpdated(TpvDateUtils.convertIndentiferTimsStringToDate(apppackage.getLastEdit()));
                break;
            }
            case Banner: {
                Banners banners = JpaManager.getBannersManager().loadByKey(id);
                if (null == banners) break;
                info.name = banners.getName();
                info.version = CloneItemUtils.getBannerIdentifier(banners);
                info.platform = PlatformUtils.getPlatformName("TPM181HE_CloneData");
                info.isDefaultPlatform = true;
                info.setLastUpdated(TpvDateUtils.convertIndentiferTimsStringToDate(banners.getLastEdit()));
                break;
            }
            case WelcomeLogo: {
                Welcome welcome = JpaManager.getWelcomeManager().loadByKey(id);
                if (null == welcome) break;
                info.name = welcome.getName();
                info.version = welcome.getLastEdit();
                info.platform = PlatformUtils.getPlatformName(welcome.getPlatform());
                info.setLastUpdated(TpvDateUtils.convertIndentiferTimsStringToDate(welcome.getLastEdit()));
                break;
            }
            case Schedules: {
                Schedule schedule = JpaManager.getScheduleManager().loadByKey(id);
                if (null == schedule) break;
                info.name = schedule.getName();
                info.version = schedule.getLastEdit();
                info.platform = PlatformUtils.getPlatformName("TPM181HE_CloneData");
                info.isDefaultPlatform = true;
                info.setLastUpdated(TpvDateUtils.convertIndentiferTimsStringToDate(schedule.getLastEdit()));
                break;
            }
            case ProfessionalAppsData: 
            case UiCustomizations: {
                UiCustomizations uiCustomizations = JpaManager.getUiCustomizationsManager().loadByKey(id);
                if (null == uiCustomizations) break;
                info.name = uiCustomizations.getName();
                info.version = uiCustomizations.getLastEdit();
                info.platform = PlatformUtils.getPlatformName(uiCustomizations.getPlatform());
                info.setLastUpdated(TpvDateUtils.convertIndentiferTimsStringToDate(uiCustomizations.getLastEdit()));
                break;
            }
            case PMS: {
                PmsUtils.PmsAction action = PmsUtils.PmsAction.values()[id];
                info.name = action.name();
                info.platform = "TPM181HE_CloneData";
                info.version = TpvDateUtils.formatLocalDate(new Date(), "dd-MMM-yyyy-'T'HHmmss");
                info.isDefaultPlatform = true;
                info.setLastUpdated(new Date());
                break;
            }
            case SmartInfoBrowser: 
            case SmartInfoPages: {
                ContentUtils.Content content = ContentUtils.getContent(String.valueOf(id));
                info.name = content.title;
                info.version = content.changed;
                info.platform = PlatformUtils.getPlatformName("TPM181HE_CloneData");
                info.isDefaultPlatform = true;
                info.setLastUpdated(TpvDateUtils.convertIndentiferTimsStringToDate(content.changed));
                break;
            }
        }
        return info;
    }

    public static String getItemTypeMapName(CommonConstants.CloneItemType itemType) {
        switch (itemType) {
            case TVSettings: {
                return "Settings";
            }
            case AndroidApps: {
                return "Apps";
            }
            case ChannelList: {
                return "Channels";
            }
            case SmartInfoBrowser: {
                return "Content";
            }
            case Banner: {
                return "Banners";
            }
            case Schedules: {
                return "Schedule";
            }
            case WelcomeLogo: {
                return "Welcome";
            }
            case UiCustomizations: {
                return "UiCustomizations";
            }
        }
        return itemType.name();
    }

    public static CommonConstants.CloneItemType getCloneItemTypeByName(String itemName) {
        switch (itemName) {
            case "Software": 
            case "Firmware": {
                return CommonConstants.CloneItemType.Firmware;
            }
            case "Settings": {
                return CommonConstants.CloneItemType.TVSettings;
            }
            case "Apps": {
                return CommonConstants.CloneItemType.AndroidApps;
            }
            case "Channels": {
                return CommonConstants.CloneItemType.ChannelList;
            }
            case "Content": {
                return CommonConstants.CloneItemType.SmartInfoBrowser;
            }
            case "Banners": {
                return CommonConstants.CloneItemType.Banner;
            }
            case "Schedule": 
            case "Schedules": {
                return CommonConstants.CloneItemType.Schedules;
            }
            case "Welcome": {
                return CommonConstants.CloneItemType.WelcomeLogo;
            }
            case "UiCustomizations": 
            case "UI": {
                return CommonConstants.CloneItemType.UiCustomizations;
            }
        }
        return CommonConstants.CloneItemType.Clone;
    }

    public static CommonConstants.CloneItemType parseCloneItemType(String cloneItemName) {
        try {
            return CommonConstants.CloneItemType.valueOf(cloneItemName);
        }
        catch (Exception e) {
            switch (cloneItemName) {
                case "TVChannelList": {
                    return CommonConstants.CloneItemType.ChannelList;
                }
                case "SmartInfoImages": {
                    return CommonConstants.CloneItemType.SmartInfoShow;
                }
                case "SmartInfoPages": {
                    return CommonConstants.CloneItemType.SmartInfoBrowser;
                }
            }
            return CommonConstants.CloneItemType.UnKnownItem;
        }
    }

    public static String getCloneItemFileName(String clonename) {
        try {
            CommonConstants.CloneItemType itemType = CommonConstants.CloneItemType.valueOf(clonename);
            return itemType.name();
        }
        catch (Exception e) {
            return "unkown";
        }
    }

    public static String getItemTypeIdName(CommonConstants.CloneItemType itemType) {
        switch (itemType) {
            case TVSettings: {
                return "settingPackageId";
            }
            case AndroidApps: {
                return "appPackageId";
            }
            case ChannelList: {
                return "channelPackageId";
            }
            case SmartInfoBrowser: {
                return "contentId";
            }
            case Banner: {
                return "bannerId";
            }
            case Schedules: {
                return "scheduleId";
            }
            case WelcomeLogo: {
                return "welcomeId";
            }
            case UiCustomizations: {
                return "uiCustomizationsId";
            }
        }
        return "";
    }

    public static String convertJapitNameToItem(String japitName) {
        return japitNameToShowNameMap.getOrDefault(japitName, japitName);
    }

    public static String convertItemToJapitName(String oldName) {
        return showNameToJapitNameMap.getOrDefault(oldName, oldName);
    }

    private static String getCloneRootPath(String cloneName) {
        String rootPath = CommonConstants.CLONE_PROCESS_LOCATION + cloneName + "/";
        return new File(rootPath).exists() ? rootPath : null;
    }

    private static String getMasterClonePath(String cloneName, String platform) {
        String rootPath = CloneItemUtils.getCloneRootPath(cloneName);
        if (rootPath != null) {
            String masterPath = rootPath + PlatformUtils.getPlatformId(platform) + "/MasterCloneData/";
            if (new File(masterPath).exists()) {
                return masterPath;
            }
            masterPath = rootPath + PlatformUtils.getPlatformId(platform) + "/";
            if (new File(masterPath).exists()) {
                return masterPath;
            }
        }
        return null;
    }

    public static String getCloneItemPath(Setting setting, String itemName) {
        String itemPath;
        String masterPath = CloneItemUtils.getMasterClonePath(setting.getName(), setting.getPlatform());
        if (masterPath != null && new File(itemPath = masterPath + itemName + "/").exists()) {
            return itemPath;
        }
        return null;
    }

    public static String getIdentifier(String settingPath) {
        File path = new File(settingPath);
        File identifyFile = new File(path.getAbsolutePath() + "/" + path.getName() + "_Identifier.txt");
        if (identifyFile.exists()) {
            try {
                String identifier = FileUtils.readFileToString(identifyFile, StandardCharsets.UTF_8);
                return identifier.trim();
            }
            catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return "";
    }

    public static int getAssignedId(CommonConstants.CloneItemType cloneType, Setting setting) {
        int id = -1;
        switch (cloneType) {
            case TVSettings: {
                id = setting.getSettingPackageId();
                break;
            }
            case AndroidApps: {
                id = setting.getAppPackageId();
                break;
            }
            case ChannelList: 
            case MediaChannels: {
                id = setting.getChannelPackageId();
                break;
            }
            case Banner: {
                id = setting.getBannersId();
                break;
            }
            case Schedules: {
                id = setting.getScheduleId();
                break;
            }
            case WelcomeLogo: {
                id = setting.getWelcomeId();
                break;
            }
            case ProfessionalAppsData: 
            case UiCustomizations: {
                id = setting.getUiCustomizationsId();
                break;
            }
            default: {
                LOG.error("not support assign clone item:{}", (Object)cloneType);
            }
        }
        return id;
    }

    public static boolean isCloneDataEmpty(String path) {
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

    public static String getCloneItemPlatform(CommonConstants.CloneItemType cloneType, int cloneId) {
        String platform = null;
        CloneItemInfo info = CloneItemUtils.getCloneItemInfo(cloneType, cloneId);
        platform = info.getPlatform();
        if (platform != null) {
            platform = PlatformUtils.getPlatformId(platform);
        }
        return platform;
    }

    public static String getBannerProcessFolderPath(Banners banners) {
        return String.format(Locale.ENGLISH, "%sBanners/%s/", CommonConstants.CLONE_PROCESS_LOCATION, banners.getId());
    }

    public static String getChannelPackageDataPath(ChannelPackage channelPackage) {
        return String.format(Locale.ENGLISH, "%sChannelPackages/%d/", CommonConstants.CLONE_PROCESS_LOCATION, channelPackage.getId());
    }

    public static String getAppPackageDataPath(int appPackageId) {
        return String.format(Locale.ENGLISH, "%sAppPackages/%d/AndroidApps/", CommonConstants.CLONE_PROCESS_LOCATION, appPackageId);
    }

    public static String getChannelPackageLogoPath(int packageId) {
        return String.format(Locale.ENGLISH, CommonConstants.CHANNEL_PACKAGE_LOGO_PATH_FORMAT, packageId);
    }

    public static String getChannelThemeTvIconPath(int packageId) {
        return String.format(Locale.ENGLISH, CommonConstants.CHANNEL_PACKAGE_THEME_TV_ICON_PATH_FORMAT, packageId);
    }

    public static String getSchedulesPackageDataPath(int packageId) {
        return String.format(Locale.ENGLISH, "%sSchedulesPackages/%d/", CommonConstants.CLONE_PROCESS_LOCATION, packageId);
    }

    public static String getUniqueCloneName(List<String> currentExistsName, String baseName) {
        String rename;
        block2: {
            String name;
            rename = baseName;
            if (null == currentExistsName || currentExistsName.isEmpty()) break block2;
            int j = 0;
            while (true) {
                name = baseName;
                if (j > 0) {
                    name = name + "(" + j + ")";
                }
                if (!currentExistsName.contains(name)) break;
                ++j;
            }
            rename = name;
        }
        return rename;
    }

    public static String getBannerIdentifier(Banners banner) {
        String identifier = banner.getLastEdit() + " " + banner.getName();
        return TpvStringUtils.limitStringLength(identifier, 32);
    }

    public static CloneItemInfo getCloneItemInfo(String selectCloneType, int cloneId) {
        CommonConstants.CloneItemType type = IPUpgradeManager.convertUpgradeTypeToCloneItemType(selectCloneType);
        return CloneItemUtils.getCloneItemInfo(type, cloneId);
    }

    static {
        japitNameToShowNameMap.put("TVChannelList", "ChannelList");
        japitNameToShowNameMap.put("SmartInfoImages", "SmartInfoShow");
        japitNameToShowNameMap.put("SmartInfoPages", "SmartInfoBrowser");
        japitNameToShowNameMap.put("CustomDashboardFallback", "LocalCustomDashboard");
        japitNameToShowNameMap.put("HTVCfg.xml", "HTVCfg");
        showNameToJapitNameMap = new HashMap<String, String>();
        for (Map.Entry<String, String> entry : japitNameToShowNameMap.entrySet()) {
            showNameToJapitNameMap.put(entry.getValue(), entry.getKey());
        }
    }

    public static class CloneItemInfo {
        CommonConstants.CloneItemType itemType;
        int id;
        String name;
        String version;
        String platform;
        boolean isClone = false;
        boolean isDefaultPlatform = false;
        Date lastUpdated;

        public Date getLastUpdated() {
            return this.lastUpdated;
        }

        public void setLastUpdated(Date lastUpdated) {
            this.lastUpdated = lastUpdated;
        }

        public boolean isDefaultPlatform() {
            return this.isDefaultPlatform;
        }

        public void setDefaultPlatform(boolean isDefaultPlatform) {
            this.isDefaultPlatform = isDefaultPlatform;
        }

        public CommonConstants.CloneItemType getItemType() {
            return this.itemType;
        }

        public void setItemType(CommonConstants.CloneItemType itemType) {
            this.itemType = itemType;
        }

        public int getId() {
            return this.id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isClone() {
            return this.isClone;
        }

        public void setClone(boolean isClone) {
            this.isClone = isClone;
        }

        public String getPlatform() {
            return this.platform;
        }

        public void setPlatform(String platform) {
            this.platform = platform;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVersion() {
            return this.version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public boolean isCompatible(String platform) {
            String platformId = PlatformUtils.getPlatformName(platform);
            return this.isDefaultPlatform() || this.getPlatform().equalsIgnoreCase(platformId);
        }

        public String getClonePath() {
            String longVersion = String.valueOf(this.getLastUpdated().getTime());
            return String.format(Locale.ENGLISH, "%s/%d-%s/", this.itemType.name(), this.id, longVersion);
        }

        public String getCachedPath() {
            if (this.itemType == CommonConstants.CloneItemType.Firmware) {
                return String.format(Locale.ENGLISH, "%s/Profile/UPG/%d/", CommonConstants.servletContextPath, this.id);
            }
            return String.format(Locale.ENGLISH, "%s/Profile/Clone/%s", CommonConstants.servletContextPath, this.getClonePath());
        }
    }
}

