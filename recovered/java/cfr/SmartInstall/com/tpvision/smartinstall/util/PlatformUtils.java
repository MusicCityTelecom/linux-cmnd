/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.PlayoutInfo;
import com.tpvision.smartinstall.dao.core.UiCustomizations;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.SettingState;
import com.tpvision.smartinstall.util.TpvStringUtils;
import com.tpvision.smartinstall.util.UploadPlatformType;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public final class PlatformUtils {
    private static final String[] PLATFORM_NAME_LIST = new String[]{"2K14/2K15-MS", "2K14/2K15-ES", "2016 MS", "2016 ES", "2016 SS", "2019 MS", "2019 PS", "2019 ES", "2019 NAFTA", "TPM215HEA", "TPM215HKN", "TPM242HWW"};
    private static final String[] PLATFORM_ID_LIST = new String[]{"TPN141HE_CloneData", "TPN142HE_CloneData", "TPM1532HE_CloneData", "TPN161HE_CloneData", "TPM1531HE_CloneData", "TPM181HE_CloneData", "TPM187HE_CloneData", "TPS191HE_CloneData", "TPM191HN_CloneData", "TPM215HEA_CloneData", "TPM215HKN_CloneData", "TPM242HWW_CloneData"};
    private static final String[] PLATFORM_TYPE_LIST = new String[]{"MS2K14", "ES2K14", "MS2K16", "ES2K16", "SS2K16", "MS2K19", "PS2K19", "ES2K19", "NAFTA2K19", "T32", "NAFTAT32", "TPM242HWW"};

    private PlatformUtils() {
    }

    public static boolean isValidUploadedPlatform(String platformId) {
        return Arrays.asList("TPN141HE_CloneData", "TPN142HE_CloneData", "TPM1532HE_CloneData", "TPN161HE_CloneData", "TPM1531HE_CloneData", "TPM181HE_CloneData", "TPM187HE_CloneData", "TPS191HE_CloneData", "TPM191HN_CloneData", "TPM215HEA_CloneData", "TPM215HKN_CloneData", "TPM242HWW_CloneData").contains(platformId);
    }

    public static boolean isSupportShowWarnings(String platformName) {
        return PlatformUtils.isAsta2016Up(platformName);
    }

    public static boolean isSupportCorrectWebServiceUrl(String platformName) {
        return Arrays.asList("2016 MS", "2019 PS", "2019 MS", "2019 NAFTA", "TPM242HWW").contains(platformName);
    }

    public static boolean isSupportCorrectDifferentTVSettings(String platformName) {
        return Arrays.asList("2019 PS", "2019 MS", "TPM242HWW").contains(platformName);
    }

    public static String getPlatformType(String platformId) {
        if (Arrays.asList(PLATFORM_TYPE_LIST).contains(platformId)) {
            return platformId;
        }
        if (Arrays.asList(PLATFORM_ID_LIST).contains(platformId)) {
            return Arrays.asList(PLATFORM_TYPE_LIST).get(Arrays.asList(PLATFORM_ID_LIST).indexOf(platformId));
        }
        if (Arrays.asList(PLATFORM_NAME_LIST).contains(platformId)) {
            return Arrays.asList(PLATFORM_TYPE_LIST).get(Arrays.asList(PLATFORM_NAME_LIST).indexOf(platformId));
        }
        return "";
    }

    public static String getPlatformName(String platformId) {
        if (Arrays.asList(PLATFORM_NAME_LIST).contains(platformId)) {
            return platformId;
        }
        if (Arrays.asList(PLATFORM_ID_LIST).contains(platformId)) {
            return Arrays.asList(PLATFORM_NAME_LIST).get(Arrays.asList(PLATFORM_ID_LIST).indexOf(platformId));
        }
        if (Arrays.asList(PLATFORM_TYPE_LIST).contains(platformId)) {
            return Arrays.asList(PLATFORM_NAME_LIST).get(Arrays.asList(PLATFORM_TYPE_LIST).indexOf(platformId));
        }
        return "";
    }

    public static String getPlatformId(String platform) {
        if (Arrays.asList(PLATFORM_ID_LIST).contains(platform)) {
            return platform;
        }
        if (Arrays.asList(PLATFORM_NAME_LIST).contains(platform)) {
            return Arrays.asList(PLATFORM_ID_LIST).get(Arrays.asList(PLATFORM_NAME_LIST).indexOf(platform));
        }
        if (Arrays.asList(PLATFORM_TYPE_LIST).contains(platform)) {
            return Arrays.asList(PLATFORM_ID_LIST).get(Arrays.asList(PLATFORM_TYPE_LIST).indexOf(platform));
        }
        String platformId = platform + "_CloneData";
        if (Arrays.asList(PLATFORM_ID_LIST).contains(platformId)) {
            return platformId;
        }
        return "";
    }

    public static String[] getPlatformsSupportIpUpgrade() {
        ArrayList<String> platofrms = new ArrayList<String>();
        for (String platformId : PLATFORM_NAME_LIST) {
            if (!PlatformUtils.isContainsType(platformId)) continue;
            platofrms.add(platformId);
        }
        return platofrms.toArray(new String[0]);
    }

    public static boolean isContainsType(String type) {
        String[] supportTypes = new String[]{"2016 MS", "2016 SS", "2019 MS", "2019 PS", "2019 NAFTA", "TPM215HEA", "TPM215HKN", "TPM242HWW"};
        return Arrays.asList(supportTypes).contains(type);
    }

    public static boolean isContainsTypeForStandbyUpgrade(String type) {
        String[] supportTypes = new String[]{"2016 MS", "2016 SS", "2016 ES", "2019 MS", "2019 PS", "2019 NAFTA", "TPM215HEA", "TPM215HKN", "TPM242HWW"};
        return Arrays.asList(supportTypes).contains(type);
    }

    public static boolean isLoadDataForUicustomizations(String name) {
        String[] supportNames = new String[]{"TPM187HE_CloneData", "TPM181HE_CloneData", "TPM191HN_CloneData", "TPM242HWW_CloneData"};
        return Arrays.asList(supportNames).contains(name);
    }

    private static boolean is2K19MS(String tvModelName) {
        String[] models;
        for (String m : models = new String[]{"6014U", "5014", "7014U", "5114U/12", "5114/12", "5114W/12", "6114U/12", "2114/12", "HFL5214/12", "HFL5214U/12", "HFL6214U/12", "BFL2214/12", "HFL5214W/12", "HFL5214U/97", "BFL2214/97", "HFL5214U/96", "HFL5214U/11"}) {
            if (!tvModelName.contains(m)) continue;
            return true;
        }
        return false;
    }

    private static boolean is2K19NAFTA(String tvModelName) {
        String[] models;
        for (String m : models = new String[]{"6114U/27", "2114/27", "HFL5214U/27", "HFL6214U/27", "BFL2214/27"}) {
            if (!tvModelName.contains(m)) continue;
            return true;
        }
        return false;
    }

    private static boolean isT32Nafta(String tvModelName) {
        String[] models;
        for (String m : models = new String[]{"HFL4518U/27"}) {
            if (!tvModelName.contains(m)) continue;
            return true;
        }
        return false;
    }

    private static boolean isT32(String tvModelName) {
        String[] models;
        for (String m : models = new String[]{"HFL4518/12", "HFL4518U/12", "HFL4518/97", "HFL4518U/97"}) {
            if (!tvModelName.contains(m)) continue;
            return true;
        }
        return false;
    }

    public static String convertTvModelNumberToType(String value) {
        if (null != value) {
            if (value.contains("009D") || value.contains("010T") || value.contains("010L") || value.contains("010W") || value.contains("109K")) {
                return "2K14/2K15-MS";
            }
            if (value.contains("5011T")) {
                return "2016 MS";
            }
            if (value.contains("7011T") || value.contains("7111T")) {
                return "2016 SS";
            }
            if (PlatformUtils.is2K19MS(value)) {
                return "2019 MS";
            }
            if (value.contains("4014") || value.contains("5803")) {
                return "2019 PS";
            }
            if (value.contains("3014")) {
                return "2019 ES";
            }
            if (PlatformUtils.is2K19NAFTA(value)) {
                return "2019 NAFTA";
            }
            if (PlatformUtils.isT32Nafta(value)) {
                return "TPM215HKN";
            }
            if (PlatformUtils.isT32(value)) {
                return "TPM215HEA";
            }
            if (value.contains("25/") || value.contains("25U/")) {
                return "TPM242HWW";
            }
        }
        return "";
    }

    public static String getChannelVersion(String platform) {
        switch (platform) {
            case "TPN141HE_CloneData": 
            case "TPN142HE_CloneData": {
                return "v4";
            }
        }
        return "v5";
    }

    public static boolean isUsingNewGateway(String platformId) {
        String platform = PlatformUtils.getPlatformId(platformId);
        String[] ogPlatformIds = new String[]{"TPN142HE_CloneData", "TPN141HE_CloneData", "TPM1532HE_CloneData", "TPM1531HE_CloneData", "TPN161HE_CloneData"};
        return !Arrays.asList(ogPlatformIds).contains(platform);
    }

    public static boolean isDownloadable(String platformId) {
        String[] platformIds = new String[]{"2016 MS", "2016 SS", "2019 PS", "2019 MS", "2019 NAFTA", "TPM215HEA", "TPM215HKN", "TPM242HWW"};
        return Arrays.asList(platformIds).contains(platformId);
    }

    public static boolean isSupportMessage(String platformId) {
        platformId = PlatformUtils.getPlatformId(platformId);
        String[] platformIds = new String[]{"TPM1532HE_CloneData", "TPM1531HE_CloneData", "TPM187HE_CloneData", "TPM181HE_CloneData", "TPM191HN_CloneData", "TPM242HWW_CloneData"};
        return Arrays.asList(platformIds).contains(platformId);
    }

    public static boolean isSupportChannelEditor(String platformId) {
        platformId = PlatformUtils.getPlatformId(platformId);
        String[] notSupportPlatformIds = new String[0];
        return !Arrays.asList(notSupportPlatformIds).contains(platformId);
    }

    public static boolean isSupportChangePowerState(String platformId) {
        platformId = PlatformUtils.getPlatformId(platformId);
        String[] platformIds = new String[]{"TPN141HE_CloneData", "TPM1532HE_CloneData", "TPM1531HE_CloneData", "TPM187HE_CloneData", "TPM181HE_CloneData", "TPM191HN_CloneData", "TPM215HEA_CloneData", "TPM215HKN_CloneData", "TPM242HWW_CloneData"};
        return Arrays.asList(platformIds).contains(platformId);
    }

    public static boolean isSupportRFPlayout(String platformId) {
        platformId = PlatformUtils.getPlatformId(platformId);
        String[] notSupportPlatformIds = new String[]{"TPM215HEA_CloneData", "TPM215HKN_CloneData", "TPM242HWW_CloneData"};
        return !Arrays.asList(notSupportPlatformIds).contains(platformId);
    }

    public static String[] getXMLFileName(String rootfolder) {
        String platform;
        String[] fileName = new String[2];
        switch (platform = PlatformUtils.getPlatformId(rootfolder)) {
            case "TPN141HE_CloneData": 
            case "TPN142HE_CloneData": {
                fileName[1] = rootfolder + "_CHTB.xml";
                fileName[0] = rootfolder + "_SSB.xml";
                break;
            }
            default: {
                fileName[1] = "ChannelList.xml";
                fileName[0] = "TVSettings.xml";
            }
        }
        return fileName;
    }

    public static String getRootFolderName(String platformId) {
        return PlatformUtils.getPlatformId(platformId).replace("_CloneData", "");
    }

    public static String getCSMPath(String platform) {
        String platformId = PlatformUtils.getPlatformId(platform);
        String csmPath = "DataDump";
        switch (platformId) {
            case "TPN141HE_CloneData": 
            case "TPN142HE_CloneData": {
                csmPath = "CSMDump";
            }
        }
        return csmPath;
    }

    public static boolean hasPackageFeature(String platform) {
        boolean hasPackage = false;
        switch (platform) {
            case "TPM1532HE_CloneData": 
            case "TPM1531HE_CloneData": 
            case "TPM181HE_CloneData": 
            case "TPM187HE_CloneData": 
            case "TPM191HN_CloneData": 
            case "TPM215HEA_CloneData": 
            case "TPM215HKN_CloneData": 
            case "TPM242HWW_CloneData": {
                hasPackage = true;
                break;
            }
            default: {
                hasPackage = false;
            }
        }
        return hasPackage;
    }

    public static String getWelcomeLogoLocation(String platform) {
        String location = "";
        switch (platform) {
            case "TPM1532HE_CloneData": 
            case "TPM1531HE_CloneData": 
            case "TPN161HE_CloneData": 
            case "TPM181HE_CloneData": 
            case "TPM215HEA_CloneData": 
            case "TPM215HKN_CloneData": 
            case "TPM187HE_CloneData": 
            case "TPS191HE_CloneData": 
            case "TPM242HWW_CloneData": {
                location = "/MasterCloneData/WelcomeLogo/";
                break;
            }
            default: {
                location = "";
            }
        }
        return location;
    }

    public static String getSmartinfoDirctoryByPlatform(String platformId) {
        String ret = null;
        switch (platformId) {
            case "TPN141HE_CloneData": 
            case "TPN142HE_CloneData": {
                ret = "SmartInfoPages";
                break;
            }
            case "TPM1531HE_CloneData": 
            case "TPM1532HE_CloneData": 
            case "TPN161HE_CloneData": 
            case "TPM181HE_CloneData": 
            case "TPM187HE_CloneData": 
            case "TPM191HN_CloneData": 
            case "TPM215HEA_CloneData": 
            case "TPM215HKN_CloneData": 
            case "TPM242HWW_CloneData": {
                ret = "SmartInfoBrowser";
                break;
            }
        }
        return ret;
    }

    public static String getSmartinfoIdentifierTxtName(String platformId) {
        String txtName = "";
        if ("TPM1531HE_CloneData".equalsIgnoreCase(platformId) || "TPM1532HE_CloneData".equalsIgnoreCase(platformId) || "TPN161HE_CloneData".equalsIgnoreCase(platformId) || "TPM181HE_CloneData".equalsIgnoreCase(platformId) || "TPM187HE_CloneData".equalsIgnoreCase(platformId) || "TPM191HN_CloneData".equalsIgnoreCase(platformId) || "TPM215HEA_CloneData".equalsIgnoreCase(platformId) || "TPM215HKN_CloneData".equalsIgnoreCase(platformId) || "TPM242HWW_CloneData".equalsIgnoreCase(platformId)) {
            txtName = "/SmartInfoBrowser_Identifier.txt";
        } else if ("TPN141HE_CloneData".equalsIgnoreCase(platformId)) {
            txtName = "/TPN141HE_SmartInfoPages_Identifier.txt";
        } else if ("TPN142HE_CloneData".equalsIgnoreCase(platformId)) {
            txtName = "/TPN142HE_SmartInfoPages_Identifier.txt";
        }
        return txtName;
    }

    public static Map<String, Object> getChannelConfig(String platformName) {
        HashMap<String, Object> configMap = new HashMap<String, Object>();
        boolean is2k14Platform = Arrays.asList("2K14/2K15-MS", "2K14/2K15-ES").contains(platformName);
        if (Arrays.asList("2K14/2K15-MS", "2K14/2K15-ES", "2016 MS", "2016 SS", "2016 ES").contains(platformName)) {
            configMap.put("System", Arrays.asList("PAL-B/G", "PAL-D/K", "PAL-I", "SECAM L"));
        } else if (Arrays.asList("TPM242HWW").contains(platformName)) {
            configMap.put("System", Arrays.asList("WestEurope", "EastEurope", "UK", "France", "LATAM", "PALN", "PALM"));
        } else {
            configMap.put("System", Arrays.asList("WestEurope", "EastEurope", "UK", "France"));
        }
        if (Arrays.asList("TPM242HWW").contains(platformName)) {
            configMap.put("MediumExt", Arrays.asList("NTSC-Ant", "NTSC-C", "ATSCAntanna", "ATSCCable", "ATSCT", "DVBS", "DVBS2", "ISBDT"));
        } else if (!Arrays.asList("2K14/2K15-MS", "2K14/2K15-ES", "2016 MS", "2016 SS", "2016 ES").contains(platformName)) {
            configMap.put("MediumExt", Arrays.asList("NTSC-Ant", "NTSC-C", "ATSCAntanna", "ATSCCable", "ATSCT"));
        }
        configMap.put("isSupportLogo", !is2k14Platform);
        if (!is2k14Platform) {
            HashMap<String, String> modulationExt = new HashMap<String, String>();
            modulationExt.put("8-VSB", "8-VSB");
            modulationExt.put("16-VSB", "16-VSB");
            configMap.put("modulationExt", modulationExt);
        }
        HashSet<String> notSupportTypes = new HashSet<String>();
        if (is2k14Platform) {
            notSupportTypes.addAll(Arrays.asList("media", "cmnd_streams", "hls"));
        }
        if (!PlatformUtils.isSupportThemeTv(platformName)) {
            notSupportTypes.addAll(Arrays.asList("media", "hls"));
            if (!is2k14Platform) {
                notSupportTypes.add("iptv");
            }
        }
        if (!Arrays.asList("2019 MS", "2019 NAFTA", "TPM242HWW").contains(platformName)) {
            notSupportTypes.addAll(Arrays.asList("hls", "app"));
        }
        if (Arrays.asList("TPM215HEA", "TPM215HKN").contains(platformName)) {
            notSupportTypes.add("media");
        }
        configMap.put("NotSupportTypes", notSupportTypes);
        return configMap;
    }

    public static boolean isSupportThemeTv(String platform) {
        String platformId;
        switch (platformId = PlatformUtils.getPlatformId(platform)) {
            case "TPN141HE_CloneData": 
            case "TPN142HE_CloneData": 
            case "TPS191HE_CloneData": {
                return false;
            }
        }
        return true;
    }

    public static String[] getProhibitedEditBroadcastMediaArrayByPlatform(String platform) {
        String platformId;
        switch (platformId = PlatformUtils.getPlatformId(platform)) {
            case "TPM215HEA_CloneData": 
            case "TPM215HKN_CloneData": {
                return new String[]{"dvbs", "dvbs unsorted", "dvbs cam"};
            }
        }
        return new String[0];
    }

    public static boolean isCompatiblePlatform(String type, String platform) {
        if (platform == null) {
            return false;
        }
        String srcPlatform = PlatformUtils.getPlatformId(type);
        String destPlatform = PlatformUtils.getPlatformId(platform);
        return srcPlatform.equalsIgnoreCase(destPlatform);
    }

    public static File getUpgDestDir(String platform, String versionFolder) {
        File destDir = null;
        switch (platform) {
            case "ES2K14": {
                destDir = new File(CommonConstants.ES2K14_UPG_CREATOR_LOCATION_OUTPUT + versionFolder);
                break;
            }
            case "MS2K14": {
                destDir = new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_OUTPUT + versionFolder);
                break;
            }
            case "ES2K16": {
                destDir = new File(CommonConstants.ES2K16_UPG_CREATOR_LOCATION_OUTPUT + versionFolder);
                break;
            }
            case "MS2K16": {
                destDir = new File(CommonConstants.MS2K15_UPG_CREATOR_LOCATION_OUTPUT + versionFolder);
                break;
            }
            case "SS2K16": {
                destDir = new File(CommonConstants.SS2K16_UPG_CREATOR_LOCATION_OUTPUT + versionFolder);
                break;
            }
            default: {
                destDir = new File(CommonConstants.RF_PLAY_BACK_INPUT_LOCATION + platform);
            }
        }
        return destDir;
    }

    public static String getConfigJSPUrl(String platform) {
        String platformId;
        switch (platformId = PlatformUtils.getPlatformId(platform)) {
            case "TPN141HE_CloneData": {
                return "/jsp/setting/newconfig2K14.jsp";
            }
            case "TPN142HE_CloneData": {
                return "/jsp/setting/newconfig2K14ES.jsp";
            }
            case "TPM1532HE_CloneData": 
            case "TPM1531HE_CloneData": {
                return "/jsp/setting/newconfig2K16SSMS.jsp";
            }
            case "TPN161HE_CloneData": {
                return "/jsp/setting/newconfig2K16ES.jsp";
            }
            case "TPM187HE_CloneData": {
                return "/jsp/setting/newconfig2K19PS.jsp";
            }
            case "TPS191HE_CloneData": {
                return "/jsp/setting/newconfig2K19ES.jsp";
            }
        }
        return "/jsp/setting/TVSettings2K19MS.jsp";
    }

    public static String getPreProcessPath(String platform) {
        String platformId;
        switch (platformId = PlatformUtils.getPlatformId(platform)) {
            case "TPN141HE_CloneData": 
            case "TPN142HE_CloneData": {
                return "";
            }
        }
        return "/MasterCloneData/";
    }

    public static String getDownloadCloneProcessPath(String platform) {
        return "/MasterCloneData/";
    }

    public static List<String> getSupportCloneItemNames(String platform) {
        List<CommonConstants.CloneItemType> supportedList = PlatformUtils.getSupportCloneItems(platform);
        return supportedList.stream().map(Enum::name).collect(Collectors.toList());
    }

    public static List<CommonConstants.CloneItemType> getSupportCloneItems(String platform) {
        String platformId = PlatformUtils.getPlatformId(platform);
        ArrayList<CommonConstants.CloneItemType> itemTypes = new ArrayList<CommonConstants.CloneItemType>();
        itemTypes.addAll(Arrays.asList(CommonConstants.CloneItemType.values()));
        itemTypes.remove((Object)CommonConstants.CloneItemType.UnKnownItem);
        itemTypes.remove((Object)CommonConstants.CloneItemType.Clone);
        itemTypes.remove((Object)CommonConstants.CloneItemType.Firmware);
        itemTypes.remove((Object)CommonConstants.CloneItemType.WeatherForecast);
        itemTypes.remove((Object)CommonConstants.CloneItemType.PMS);
        itemTypes.remove((Object)CommonConstants.CloneItemType.UiCustomizations);
        itemTypes.remove((Object)CommonConstants.CloneItemType.DataDump);
        switch (platformId) {
            case "TPN141HE_CloneData": 
            case "TPN142HE_CloneData": {
                CommonConstants.CloneItemType[] supports = new CommonConstants.CloneItemType[]{CommonConstants.CloneItemType.CustomDashboardFallback, CommonConstants.CloneItemType.MiscSettings, CommonConstants.CloneItemType.SystemUIBackup, CommonConstants.CloneItemType.CombineMedia, CommonConstants.CloneItemType.VSecureKey};
                itemTypes.clear();
                itemTypes.add(CommonConstants.CloneItemType.TVSettings);
                itemTypes.add(CommonConstants.CloneItemType.SmartInfoPages);
                itemTypes.add(CommonConstants.CloneItemType.SmartInfoImages);
                itemTypes.add(CommonConstants.CloneItemType.ChannelList);
                itemTypes.add(CommonConstants.CloneItemType.WelcomeLogo);
                itemTypes.addAll(Arrays.asList(supports));
                break;
            }
            case "TPN161HE_CloneData": {
                itemTypes.remove((Object)CommonConstants.CloneItemType.Banner);
                itemTypes.remove((Object)CommonConstants.CloneItemType.AndroidApps);
                itemTypes.remove((Object)CommonConstants.CloneItemType.Schedules);
                break;
            }
            case "TPM1532HE_CloneData": 
            case "TPM1531HE_CloneData": {
                itemTypes.remove((Object)CommonConstants.CloneItemType.Banner);
                itemTypes.remove((Object)CommonConstants.CloneItemType.Schedules);
                break;
            }
            case "TPM181HE_CloneData": 
            case "TPM191HN_CloneData": 
            case "TPM242HWW_CloneData": {
                itemTypes.remove((Object)CommonConstants.CloneItemType.SmartInfoShow);
                itemTypes.add(CommonConstants.CloneItemType.WeatherForecast);
                itemTypes.add(CommonConstants.CloneItemType.PMS);
                break;
            }
            case "TPM187HE_CloneData": 
            case "TPS191HE_CloneData": {
                itemTypes.remove((Object)CommonConstants.CloneItemType.Banner);
                itemTypes.remove((Object)CommonConstants.CloneItemType.AndroidApps);
                itemTypes.remove((Object)CommonConstants.CloneItemType.SmartInfoShow);
                break;
            }
            case "TPM215HEA_CloneData": 
            case "TPM215HKN_CloneData": {
                itemTypes.remove((Object)CommonConstants.CloneItemType.Schedules);
                itemTypes.remove((Object)CommonConstants.CloneItemType.Banner);
                break;
            }
        }
        return itemTypes;
    }

    public static List<CommonConstants.CloneItemType> getPartialCloneList(String platform) {
        String platformId = PlatformUtils.getPlatformId(platform);
        ArrayList<CommonConstants.CloneItemType> itemTypes = new ArrayList<CommonConstants.CloneItemType>();
        CommonConstants.CloneItemType[] defaultList = new CommonConstants.CloneItemType[]{CommonConstants.CloneItemType.TVSettings, CommonConstants.CloneItemType.ChannelList, CommonConstants.CloneItemType.AndroidApps, CommonConstants.CloneItemType.SmartInfoBrowser, CommonConstants.CloneItemType.Banner, CommonConstants.CloneItemType.Schedules, CommonConstants.CloneItemType.WelcomeLogo, CommonConstants.CloneItemType.UiCustomizations};
        itemTypes.addAll(Arrays.asList(defaultList));
        switch (platformId) {
            case "TPN161HE_CloneData": {
                itemTypes.remove((Object)CommonConstants.CloneItemType.Banner);
                itemTypes.remove((Object)CommonConstants.CloneItemType.AndroidApps);
                itemTypes.remove((Object)CommonConstants.CloneItemType.UiCustomizations);
                itemTypes.remove((Object)CommonConstants.CloneItemType.Schedules);
                break;
            }
            case "TPS191HE_CloneData": {
                itemTypes.remove((Object)CommonConstants.CloneItemType.AndroidApps);
                itemTypes.remove((Object)CommonConstants.CloneItemType.Banner);
                itemTypes.remove((Object)CommonConstants.CloneItemType.UiCustomizations);
                itemTypes.remove((Object)CommonConstants.CloneItemType.Schedules);
                itemTypes.remove((Object)CommonConstants.CloneItemType.SmartInfoBrowser);
                break;
            }
            case "TPM187HE_CloneData": {
                itemTypes.remove((Object)CommonConstants.CloneItemType.AndroidApps);
                itemTypes.remove((Object)CommonConstants.CloneItemType.Banner);
                itemTypes.remove((Object)CommonConstants.CloneItemType.UiCustomizations);
                break;
            }
            case "TPM1532HE_CloneData": 
            case "TPM1531HE_CloneData": 
            case "TPM215HEA_CloneData": 
            case "TPM215HKN_CloneData": {
                itemTypes.remove((Object)CommonConstants.CloneItemType.Banner);
                itemTypes.remove((Object)CommonConstants.CloneItemType.UiCustomizations);
                itemTypes.remove((Object)CommonConstants.CloneItemType.Schedules);
                break;
            }
        }
        return itemTypes;
    }

    public static List<String> getIpUpgradeItems(String platform) {
        String platformId = PlatformUtils.getPlatformId(platform);
        String[] files = null;
        switch (platformId) {
            case "TPN141HE_CloneData": {
                files = new String[]{"MainFirmware", "TVSettings", "TVChannelList", "WelcomeLogo", "SmartInfoImages", "SmartInfoPages", "CustomDashboardFallback"};
                break;
            }
            case "TPM1532HE_CloneData": 
            case "TPM1531HE_CloneData": {
                files = new String[]{"MainFirmware", "TVSettings", "TVChannelList", "WelcomeLogo", "SmartInfoImages", "SmartInfoPages", "CustomDashboardFallback", "AndroidApps", "MediaChannels", "DataDump", "HTVCfg.xml", "RoomSpecificSettings", "Script"};
                break;
            }
            case "TPM181HE_CloneData": 
            case "TPM191HN_CloneData": 
            case "TPM242HWW_CloneData": {
                files = new String[]{"MainFirmware", "TVSettings", "TVChannelList", "SmartInfoPages", "CustomDashboardFallback", "AndroidApps", "MediaChannels", "DataDump", "HTVCfg.xml", "RoomSpecificSettings", "Script", "WeatherForecast", "Banner", "PMS", "AndroidAppsData", "ProfessionalApps", "ProfessionalAppsData", "Schedules", "MyChoice", "Vsecure"};
                break;
            }
            case "TPM187HE_CloneData": {
                files = new String[]{"MainFirmware", "TVSettings", "TVChannelList", "SmartInfoPages", "CustomDashboardFallback", "MediaChannels", "DataDump", "RoomSpecificSettings", "Script", "ProfessionalAppsData", "Schedules", "WelcomeLogo", "MyChoice", "Vsecure"};
                break;
            }
            case "TPM215HEA_CloneData": 
            case "TPM215HKN_CloneData": {
                files = new String[]{"MainFirmware", "TVSettings", "TVChannelList", "SmartInfoPages", "ProfessionalApps", "ProfessionalAppsData", "AndroidApps", "AndroidAppsData", "MyChoice", "DataDump", "Script"};
                break;
            }
        }
        return Arrays.asList(files);
    }

    public static String getIdentifierName(String platformId, String itemName) {
        if (itemName.equalsIgnoreCase("ChannelList")) {
            return "CHTB";
        }
        if (itemName.equalsIgnoreCase("TVSettings")) {
            return "SSB";
        }
        return itemName;
    }

    public static int getRoomDigits(String platform) {
        String platformId = PlatformUtils.getPlatformId(platform);
        int digits = 0;
        switch (platformId) {
            case "TPN142HE_CloneData": 
            case "TPN141HE_CloneData": {
                digits = 4;
                break;
            }
            default: {
                digits = 5;
            }
        }
        return digits;
    }

    public static String getPlatformWelcomeLogoResolution(String platform) {
        String platformId = PlatformUtils.getPlatformId(platform);
        if (!PlatformUtils.isAsta2016Up(platformId) || "TPS191HE_CloneData".equals(platformId)) {
            return "1280X720";
        }
        if (Arrays.asList("TPM1532HE_CloneData", "TPM1531HE_CloneData", "TPN161HE_CloneData", "TPM187HE_CloneData").contains(platformId)) {
            return "1920X1080";
        }
        return "3840X2160";
    }

    public static int getWelcomeType(String platform) {
        String platformId = PlatformUtils.getPlatformId(platform);
        int type = 1;
        switch (platformId) {
            case "TPM1532HE_CloneData": 
            case "TPM1531HE_CloneData": 
            case "TPN161HE_CloneData": 
            case "TPM187HE_CloneData": 
            case "TPS191HE_CloneData": {
                type = 0;
                break;
            }
            case "TPN142HE_CloneData": 
            case "TPN141HE_CloneData": {
                type = 2;
                break;
            }
        }
        return type;
    }

    public static boolean isMasf2019Up(String platform) {
        String platformId = PlatformUtils.getPlatformId(platform);
        int idxOf2016SS = Arrays.asList(PLATFORM_ID_LIST).indexOf("TPM1531HE_CloneData");
        int idx = Arrays.asList(PLATFORM_ID_LIST).indexOf(platformId);
        return idx > idxOf2016SS;
    }

    public static boolean isEasySuite(String platform) {
        return PlatformUtils.getPlatformName(platform).endsWith("ES");
    }

    public static boolean isSupportVSecureKeyByPlatformName(String platformName) {
        return "2019 MS".equalsIgnoreCase(platformName) || "TPM242HWW".equalsIgnoreCase(platformName) || "2019 PS".equalsIgnoreCase(platformName) || "2019 NAFTA".equalsIgnoreCase(platformName);
    }

    public static boolean isAsta2016Up(String platform) {
        String platformId = PlatformUtils.getPlatformId(platform);
        int idxOf2014ES = Arrays.asList(PLATFORM_ID_LIST).indexOf("TPN142HE_CloneData");
        int idx = Arrays.asList(PLATFORM_ID_LIST).indexOf(platformId);
        return idx > idxOf2014ES;
    }

    public static boolean isPmsMessageUnicode(String platform) {
        return PlatformUtils.isMasf2019Up(platform);
    }

    public static SettingState.BaseParamConverter getParamConverter(String platform) {
        String platformId;
        switch (platformId = PlatformUtils.getPlatformId(platform)) {
            case "TPM191HN_CloneData": {
                return SettingState.ParamConverterFor2K19NAFTA.instance();
            }
            case "TPM181HE_CloneData": 
            case "TPM242HWW_CloneData": {
                return SettingState.ParamConverterFor2K19MS.instance();
            }
            case "TPM187HE_CloneData": {
                return SettingState.ParamConverterFor2K19PS.instance();
            }
            case "TPS191HE_CloneData": {
                return SettingState.ParamConverterFor2K19ES.instance();
            }
            case "TPN161HE_CloneData": {
                return SettingState.ParamConverterFor2K16ES.instance();
            }
            case "TPM1532HE_CloneData": 
            case "TPM1531HE_CloneData": {
                return SettingState.ParamConverterFor2K15MS.instance();
            }
            case "TPN141HE_CloneData": {
                return SettingState.ParamConverterFor2K14MS.instance();
            }
            case "TPN142HE_CloneData": {
                return SettingState.ParamConverterFor2K14MSForTpn142.instance();
            }
        }
        return SettingState.ParamConverter.instance();
    }

    public static boolean isSupportRemoteControl(String platform) {
        String platformId = PlatformUtils.getPlatformId(platform);
        String[] platformIds = new String[]{"TPM187HE_CloneData", "TPM181HE_CloneData", "TPM191HN_CloneData", "TPM242HWW_CloneData"};
        List<String> platformList = Arrays.asList(platformIds);
        return platformList.contains(platformId);
    }

    public static String getRFTVDefaultCTN(String platform) {
        String platformId;
        switch (platformId = PlatformUtils.getPlatformId(platform)) {
            case "TPS191HE_CloneData": {
                return "xxHFL3014/12";
            }
            case "TPM187HE_CloneData": {
                return "xxHFL4014/12";
            }
            case "TPM181HE_CloneData": {
                return "xxHFL6014U/12";
            }
            case "TPM191HN_CloneData": {
                return "xxHFL6014U/27";
            }
            case "TPM242HWW_CloneData": {
                return "xxHFL4025/12";
            }
        }
        return "";
    }

    public static boolean isValidPlatformForNewRFTV(String platform) {
        String platformId = PlatformUtils.getPlatformId(platform);
        String[] platformIds = new String[]{"TPM187HE_CloneData", "TPM181HE_CloneData", "TPM191HN_CloneData", "TPS191HE_CloneData", "TPM242HWW_CloneData"};
        return Arrays.asList(platformIds).contains(platformId);
    }

    public static boolean isSupportHotelImage(UiCustomizations uiCustomizations) {
        return "2019 PS".equalsIgnoreCase(uiCustomizations.getPlatform());
    }

    public static boolean isSupportUpgradeStopJapit(String platformName) {
        return Arrays.asList("2019 MS", "2019 NAFTA", "TPM215HEA", "TPM215HKN", "TPM242HWW").contains(platformName);
    }

    public static String getIpCloneServiceVer(Devices tv) {
        String platformId = PlatformUtils.getPlatformId(tv.getType());
        if ("TPN141HE_CloneData".equalsIgnoreCase(platformId)) {
            return "1.0";
        }
        if ("TPM215HEA_CloneData".equalsIgnoreCase(platformId) || "TPM215HKN_CloneData".equalsIgnoreCase(platformId)) {
            return "5.0";
        }
        return "3.0";
    }

    public static UploadPlatformType fromPlatfromId(String platformId) {
        if ("TPN142HE_CloneData".equalsIgnoreCase(platformId)) {
            return UploadPlatformType.folderLocationfor2k14;
        }
        if ("TPN141HE_CloneData".equalsIgnoreCase(platformId)) {
            return UploadPlatformType.folderLocationfor2k14;
        }
        if ("TPM1532HE_CloneData".equalsIgnoreCase(platformId)) {
            return UploadPlatformType.folderLocationforAndroidClone;
        }
        if ("TPM1531HE_CloneData".equalsIgnoreCase(platformId)) {
            return UploadPlatformType.folderLocationforAndroidClone;
        }
        if ("TPN161HE_CloneData".equalsIgnoreCase(platformId)) {
            return UploadPlatformType.folderLocationfor2K16ES;
        }
        if ("TPM181HE_CloneData".equalsIgnoreCase(platformId)) {
            return UploadPlatformType.folderLocationforAndroidClone;
        }
        if ("TPM187HE_CloneData".equalsIgnoreCase(platformId)) {
            return UploadPlatformType.folderLocationfor2K16ES;
        }
        if ("TPS191HE_CloneData".equalsIgnoreCase(platformId)) {
            return UploadPlatformType.folderLocationfor2K16ES;
        }
        if ("TPM191HN_CloneData".equalsIgnoreCase(platformId)) {
            return UploadPlatformType.folderLocationforAndroidClone;
        }
        if ("TPM215HEA_CloneData".equalsIgnoreCase(platformId)) {
            return UploadPlatformType.folderLocationforAndroidClone;
        }
        if ("TPM215HKN_CloneData".equalsIgnoreCase(platformId)) {
            return UploadPlatformType.folderLocationforAndroidClone;
        }
        if ("TPM242HWW_CloneData".equalsIgnoreCase(platformId)) {
            return UploadPlatformType.folderLocationforAndroidClone;
        }
        return UploadPlatformType.unknowFolderLacation;
    }

    public static String convertToPlatformWithoutSpace(String platformData) {
        return PlatformUtils.getPlatformName(platformData).replace(" ", "");
    }

    public static String convertToFullPlatformData(String trimedPlatform) {
        for (String platformName : PLATFORM_NAME_LIST) {
            if (!StringUtils.equalsIgnoreCase(trimedPlatform, PlatformUtils.convertToPlatformWithoutSpace(platformName))) continue;
            return platformName;
        }
        for (String platformId : PLATFORM_ID_LIST) {
            if (!StringUtils.equalsIgnoreCase(trimedPlatform, PlatformUtils.convertToPlatformWithoutSpace(platformId))) continue;
            return platformId;
        }
        for (String platformType : PLATFORM_TYPE_LIST) {
            if (!StringUtils.equalsIgnoreCase(trimedPlatform, PlatformUtils.convertToPlatformWithoutSpace(platformType))) continue;
            return platformType;
        }
        return "";
    }

    public static boolean isSupportEnablerService(String platformData) {
        String platformId = PlatformUtils.getPlatformId(platformData);
        return !Arrays.asList("TPM187HE_CloneData").contains(platformId);
    }

    public static boolean isSupportWelcomeAppWeather(String platformData) {
        String platformId = PlatformUtils.getPlatformId(platformData);
        return !Arrays.asList("TPM215HEA_CloneData", "TPM215HKN_CloneData").contains(platformId);
    }

    public static boolean isSupportTVSettingUpdateRoomId(String platformData) {
        String platformId = PlatformUtils.getPlatformId(platformData);
        return Arrays.asList("TPM215HEA_CloneData", "TPM215HKN_CloneData").contains(platformId);
    }

    public static boolean isSupportPmsActions(String platformData) {
        String platformId = PlatformUtils.getPlatformId(platformData);
        return !Arrays.asList("TPM215HEA_CloneData", "TPM215HKN_CloneData").contains(platformId);
    }

    public static boolean isSupportUpgradeStatusCheckBeforeForceUpgrade(String platformData) {
        return Arrays.asList("TPM215HEA_CloneData", "TPM215HKN_CloneData").contains(PlatformUtils.getPlatformId(platformData));
    }

    public static boolean isSupportAlarm(Devices tv) {
        return PlatformUtils.isSupportAlarm(tv.getType(), tv.getTvFirmwareIdentifier());
    }

    public static boolean isSupportAlarm(String platformName, String tvFirmwareIdentifier) {
        if (StringUtils.equalsIgnoreCase(platformName, "TPM242HWW")) {
            return true;
        }
        List<String> supportPlatformNames = Arrays.asList("2019 NAFTA", "2019 MS");
        if (supportPlatformNames.contains(platformName) && StringUtils.isNoneBlank(tvFirmwareIdentifier)) {
            String miniVersion = "205.001.059.000";
            String digitVersion = tvFirmwareIdentifier.replaceAll("[\\w]*_R\\.", "");
            if (digitVersion.length() == miniVersion.length() && digitVersion.compareTo(miniVersion) >= 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSupportWakeupOnLan(String platformName) {
        String platformId = PlatformUtils.getPlatformId(platformName);
        return Arrays.asList("TPM1532HE_CloneData", "TPM1531HE_CloneData", "TPM181HE_CloneData", "TPM191HN_CloneData", "TPM215HEA_CloneData", "TPM215HKN_CloneData", "TPM242HWW_CloneData").contains(platformId);
    }

    public static boolean isSupportWakeupOnLanViaRemoteControl(String platformName) {
        return PlatformUtils.isSupportWakeupOnLan(platformName) && !Arrays.asList("TPM215HEA_CloneData", "TPM215HKN_CloneData").contains(PlatformUtils.getPlatformId(platformName));
    }

    public static void fixPlayoutInfoRooms(PlayoutInfo playoutInfo) {
        if (playoutInfo.getPlatform().equals("2019 PS")) {
            playoutInfo.setRooms(TpvStringUtils.fixPlayoutRoomNos(playoutInfo.getRooms()));
        }
    }

    public static String extractPlatformId(File bannerCloneFolder) {
        String fullPath = bannerCloneFolder.getAbsolutePath();
        Pattern pattern = Pattern.compile("([^\\\\/]+_CloneData)");
        Matcher matcher = pattern.matcher(fullPath);
        String lastMatch = null;
        while (matcher.find()) {
            lastMatch = matcher.group(1);
        }
        return lastMatch;
    }

    public static boolean isNeedCheckLowerVersion(String platform) {
        String platformId = PlatformUtils.getPlatformId(platform);
        return Arrays.asList("TPM215HEA_CloneData", "TPM215HKN_CloneData", "TPM242HWW_CloneData").contains(platformId);
    }

    public static boolean compareLowerVersion(String settingVersion, String version) {
        String[] v1Parts = PlatformUtils.extractVersionNumbers(settingVersion);
        String[] v2Parts = PlatformUtils.extractVersionNumbers(version);
        if (v1Parts == null || v2Parts == null) {
            return false;
        }
        for (int i = 0; i < 4; ++i) {
            int num2;
            int num1 = Integer.parseInt(v1Parts[i]);
            if (num1 < (num2 = Integer.parseInt(v2Parts[i]))) {
                return true;
            }
            if (num1 <= num2) continue;
            return false;
        }
        return false;
    }

    private static String[] extractVersionNumbers(String version) {
        if (version == null || version.isEmpty()) {
            return null;
        }
        Pattern pattern = Pattern.compile("\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})");
        Matcher matcher = pattern.matcher(version);
        if (matcher.find()) {
            String[] result = new String[4];
            for (int i = 0; i < 4; ++i) {
                result[i] = matcher.group(i + 1);
            }
            return result;
        }
        String[] parts = version.split("\\.");
        ArrayList<String> numbers = new ArrayList<String>();
        for (String part : parts) {
            if (part.matches("\\d{1,3}")) {
                numbers.add(part);
                if (numbers.size() != 4) continue;
                break;
            }
            numbers.clear();
        }
        if (numbers.size() == 4) {
            return numbers.toArray(new String[0]);
        }
        return null;
    }
}

