/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import com.tpvision.smartinstall.dao.core.Banners;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.TpvFileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BannerTemplateUtils {
    private static final Logger LOG = LoggerFactory.getLogger(BannerTemplateUtils.class);
    private static final String CONFIG_FILE_NAME = "banner_settings.js";
    private static final String EMERGENCY_IMAGE_FOLDER_NAME = "emergency";
    private static final String COMMERCIAL_IMAGE_FOLDER_NAME = "commercial";

    private BannerTemplateUtils() {
    }

    public static void generateBannerCloneFilesByBanner(File directoryPath, Banners banners) throws IOException {
        if (StringUtils.equalsIgnoreCase("Custom", banners.getType())) {
            BannerTemplateUtils.copyCustomerBannerToAssebleFolder(directoryPath, banners);
        } else {
            BannerTemplateUtils.syncBannerTemplateToTargetPath(directoryPath);
            String jsonString = "[]";
            if (StringUtils.equalsIgnoreCase("Survey", banners.getType())) {
                jsonString = "[{\r\nid: 0,\r\ncontent: {type: \"survey\", properties:{mail_address:\"" + Optional.ofNullable(banners.getResponse()).orElse("") + "\",title:\"" + banners.getContent() + "\"}},\r\nselection: {type: \"always\", properties:{}},\r\ntrigger: \"" + banners.getTriggers() + "\",\r\nduration: {type: \"untill_key_press\", properties:{key_codes:[13]}}\r\n}]";
            } else if (StringUtils.equalsIgnoreCase("Emergency", banners.getType())) {
                String content = banners.getContent();
                if (Arrays.asList("Evacuation", "Firealert").contains(content)) {
                    jsonString = "[{\r\nid: 0,\r\ncontent:{type:\"emergency\", properties:{content:\"" + content + "\"}},\r\nselection: {type: \"always\", properties:{}},\r\ntrigger: \"external\",\r\nduration: {type: \"forever\", properties:{}}\r\n}]";
                } else {
                    jsonString = "[{\r\nid: 0,\r\ncontent:{type:\"image\", properties:{src:\"emergency/" + banners.getContent() + "\",top:0,left:0,width:1920}},\r\nselection: {type: \"always\", properties:{}},\r\ntrigger: \"external\",\r\nduration: {type: \"forever\", properties:{}}\r\n}]";
                    File emengencyImageFolder = new File(directoryPath.getAbsolutePath() + File.separatorChar + EMERGENCY_IMAGE_FOLDER_NAME);
                    if (emengencyImageFolder.exists()) {
                        FileUtils.deleteQuietly(emengencyImageFolder);
                    }
                    emengencyImageFolder.mkdir();
                    FileUtils.copyFileToDirectory(new File(CommonConstants.CLONE_BANNER_EMERGENCY_IMAGE_LOATION + banners.getContent()), emengencyImageFolder);
                }
            } else if (StringUtils.equalsIgnoreCase("Commercial", banners.getType())) {
                List<String> validContentList = BannerTemplateUtils.handleCommercialContentData(directoryPath, banners);
                jsonString = "[\r\n" + StringUtils.join(validContentList, ",\r\n") + "]";
            }
            String bannerSettingJsString = "var banner_settings = \r\n" + jsonString + ";";
            FileUtils.writeStringToFile(new File(directoryPath.getAbsolutePath() + File.separator + CONFIG_FILE_NAME), bannerSettingJsString, StandardCharsets.UTF_8);
        }
    }

    private static List<String> handleCommercialContentData(File directoryPath, Banners banners) throws IOException {
        File commercialImageFolder;
        ArrayList<String> validContentList = new ArrayList<String>();
        String contents = banners.getContent();
        JSONArray contentArray = new JSONArray();
        if (StringUtils.isNoneBlank(contents)) {
            contentArray = new JSONArray(contents);
        }
        if ((commercialImageFolder = new File(directoryPath.getAbsolutePath() + File.separatorChar + COMMERCIAL_IMAGE_FOLDER_NAME)).exists()) {
            FileUtils.deleteQuietly(commercialImageFolder);
        }
        commercialImageFolder.mkdir();
        int j = contentArray.length();
        for (int i = 0; i < j; ++i) {
            JSONObject contentObject = contentArray.getJSONObject(i);
            JSONObject properties = contentObject.getJSONObject("content").getJSONObject("properties");
            String imageName = properties.optString("src");
            Number contentId = Optional.ofNullable(contentObject.optNumber("version_tag")).orElse(contentObject.optNumber("id"));
            if (!StringUtils.isNoneBlank(imageName)) continue;
            FileUtils.copyFileToDirectory(new File(CommonConstants.CLONE_BANNER_COMMERCIAL_CONTENT_IMAGE_LOATION + imageName), commercialImageFolder);
            String oneContentString = "    {\r\n        id: " + contentId + ", \r\n        content:{type:\"image\", properties:{src:\"" + COMMERCIAL_IMAGE_FOLDER_NAME + "/" + imageName + "\",top:" + properties.getInt("top") + ",left:" + properties.getInt("left") + ",width:" + properties.getInt("width") + "}},\r\n        selection: " + contentObject.getJSONObject("selection").toString() + ",\r\n        trigger: \"" + contentObject.getString("trigger") + "\",\r\n        duration: " + contentObject.getJSONObject("duration").toString() + "\r\n    }";
            validContentList.add(oneContentString);
        }
        return validContentList;
    }

    public static Banners extractBannerUploadFileToBanner(File bannerCloneFolder, String configName) {
        String platformName;
        block9: {
            File bannerSettingJs = new File(bannerCloneFolder.getAbsolutePath() + File.separatorChar + CONFIG_FILE_NAME);
            platformName = PlatformUtils.getPlatformName(PlatformUtils.extractPlatformId(bannerCloneFolder));
            LOG.info("bannerFolder is {} , platformName is {}", (Object)bannerCloneFolder.getAbsolutePath(), (Object)platformName);
            if (bannerSettingJs.isFile() && bannerSettingJs.exists()) {
                try {
                    JSONArray bannerArray;
                    String fullString = FileUtils.readFileToString(bannerSettingJs, StandardCharsets.UTF_8);
                    String formatJsonString = fullString.replaceFirst("var banner_settings =", "").trim();
                    if (formatJsonString.endsWith(";")) {
                        formatJsonString = formatJsonString.substring(0, formatJsonString.length() - 1);
                    }
                    if ((bannerArray = new JSONArray(formatJsonString)).length() == 1) {
                        JSONObject bannerObject = bannerArray.getJSONObject(0);
                        JSONObject contentObject = bannerObject.getJSONObject("content");
                        String type = contentObject.getString("type");
                        if (StringUtils.equalsIgnoreCase(type, "survey")) {
                            String trigger = bannerObject.getString("trigger");
                            String mailAddress = contentObject.getJSONObject("properties").getString("mail_address");
                            String title = contentObject.getJSONObject("properties").optString("title", "How would you rate your stay?");
                            return BannerTemplateUtils.saveBannersToDB(configName, platformName, "Survey", title, trigger, mailAddress);
                        }
                        if (StringUtils.equalsIgnoreCase(type, EMERGENCY_IMAGE_FOLDER_NAME)) {
                            String content = contentObject.getJSONObject("properties").getString("content");
                            return BannerTemplateUtils.saveBannersToDB(configName, platformName, "Emergency", content, null, null);
                        }
                        if (StringUtils.equalsIgnoreCase(type, "image")) {
                            return BannerTemplateUtils.loadSingleImageBanner(bannerCloneFolder, configName, platformName, bannerArray, contentObject);
                        }
                        break block9;
                    }
                    return BannerTemplateUtils.saveCommericalBanners(bannerCloneFolder, configName, platformName, bannerArray);
                }
                catch (Exception e) {
                    LOG.error("save banner data failure", e);
                }
            } else {
                return null;
            }
        }
        return BannerTemplateUtils.saveCustomBannersToDB(bannerCloneFolder, configName, platformName);
    }

    private static void copyCustomerBannerToAssebleFolder(File directoryPath, Banners banners) throws IOException {
        File srcDirectoryStr = new File(CommonConstants.CLONE_PROCESS_LOCATION + "Banners" + File.separator + banners.getId());
        if (srcDirectoryStr.exists()) {
            try {
                FileUtils.copyDirectory(srcDirectoryStr, directoryPath);
                LOG.info("customer banners copy banners from {} to {}", (Object)srcDirectoryStr.getAbsolutePath(), (Object)directoryPath.getAbsolutePath());
            }
            catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        } else {
            boolean isFixed = false;
            if (Arrays.asList("Firealert", "Evacuation").contains(banners.getContent())) {
                banners.setType("Emergency");
                isFixed = true;
            } else if (Arrays.asList("Star").contains(banners.getContent())) {
                banners.setType("Survey");
                isFixed = true;
            }
            if (isFixed) {
                LOG.info("customer banners {} content {} fixed new type to {} ", banners.getId(), banners.getContent(), banners.getType());
                JpaManager.getBannersManager().save(banners);
                BannerTemplateUtils.generateBannerCloneFilesByBanner(directoryPath, banners);
            }
        }
    }

    private static Banners loadSingleImageBanner(File bannerCloneFolder, String configName, String platformName, JSONArray bannerArray, JSONObject contentObject) throws IOException {
        String imageSrc = contentObject.getJSONObject("properties").getString("src");
        if (imageSrc.startsWith(EMERGENCY_IMAGE_FOLDER_NAME)) {
            File imageFile = new File(bannerCloneFolder.getAbsolutePath() + File.separatorChar + imageSrc);
            String imageName = TpvFileUtils.getImageMd5(imageFile) + "." + FilenameUtils.getExtension(imageFile.getName().toLowerCase());
            File destEmergencyFile = new File(CommonConstants.CLONE_BANNER_EMERGENCY_IMAGE_LOATION + imageName);
            if (!destEmergencyFile.exists()) {
                FileUtils.copyFile(imageFile, destEmergencyFile);
            }
            return BannerTemplateUtils.saveBannersToDB(configName, platformName, "Emergency", destEmergencyFile.getName(), null, null);
        }
        return BannerTemplateUtils.saveCommericalBanners(bannerCloneFolder, configName, platformName, bannerArray);
    }

    private static void syncBannerTemplateToTargetPath(File targetDirectory) {
        try {
            FileUtils.copyDirectory(new File(CommonConstants.CLONE_BANNER_HTML_CODE_TEMPLATE), targetDirectory);
        }
        catch (IOException e) {
            LOG.info("customer banners copy banners from {} to {}", (Object)CommonConstants.CLONE_BANNER_HTML_CODE_TEMPLATE, (Object)targetDirectory.getAbsolutePath());
            e.printStackTrace();
        }
    }

    private static Banners saveCommericalBanners(File bannerCloneFolder, String configName, String platformName, JSONArray bannerSettingArray) throws IOException {
        int j = bannerSettingArray.length();
        for (int i = 0; i < j; ++i) {
            JSONObject propJsonObject = bannerSettingArray.getJSONObject(i).getJSONObject("content").getJSONObject("properties");
            String imageSrc = propJsonObject.getString("src");
            File imageFile = new File(bannerCloneFolder.getAbsolutePath() + File.separatorChar + imageSrc);
            if (!StringUtils.isNoneBlank(imageSrc) || !imageFile.exists()) continue;
            String imageName = TpvFileUtils.getImageMd5(imageFile) + "." + FilenameUtils.getExtension(imageFile.getName().toLowerCase());
            File destCommercialFile = new File(CommonConstants.CLONE_BANNER_COMMERCIAL_CONTENT_IMAGE_LOATION + imageName);
            if (!destCommercialFile.exists()) {
                FileUtils.copyFile(imageFile, destCommercialFile);
            }
            propJsonObject.put("src", destCommercialFile.getName());
        }
        return BannerTemplateUtils.saveBannersToDB(configName, platformName, "Commercial", bannerSettingArray.toString(), null, null);
    }

    private static Banners saveCustomBannersToDB(File sourceBannerFolder, String bannerCloneName, String platformName) {
        try {
            Banners banners = BannerTemplateUtils.saveBannersToDB(bannerCloneName, platformName, "Custom", "Custom", null, null);
            File targetDir = new File(CommonConstants.CLONE_PROCESS_LOCATION + "Banners/" + banners.getId());
            FileUtils.copyDirectory(sourceBannerFolder, targetDir);
            return banners;
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    private static Banners saveBannersToDB(String bannerCloneName, String platformName, String type, String content, String trigger, String response) {
        Banners banners = new Banners();
        banners.setName(bannerCloneName);
        banners.setPlatform(platformName);
        banners.setType(type);
        banners.setContent(content);
        banners.setTriggers(trigger);
        banners.setResponse(response);
        banners.setLastEdit(TpvDateUtils.getCurrentIndentifierFormatTime());
        JpaManager.getBannersManager().save(banners);
        return banners;
    }
}

