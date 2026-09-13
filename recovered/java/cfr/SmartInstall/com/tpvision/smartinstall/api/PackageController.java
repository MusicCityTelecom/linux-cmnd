/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.api;

import com.tpvision.smartinstall.api.ApiCloneConfig;
import com.tpvision.smartinstall.api.ApiErrorCode;
import com.tpvision.smartinstall.dao.core.AppPackage;
import com.tpvision.smartinstall.dao.core.Banners;
import com.tpvision.smartinstall.dao.core.ChannelPackage;
import com.tpvision.smartinstall.dao.core.Schedule;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.core.SettingPackage;
import com.tpvision.smartinstall.dao.core.UiCustomizations;
import com.tpvision.smartinstall.dao.core.UpgSetting;
import com.tpvision.smartinstall.dao.core.Welcome;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.ContentUtils;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/exapi/"})
public class PackageController {
    @GetMapping(value={"/packages"})
    public Object query(@RequestParam(value="package_type", defaultValue="") String packageType, @RequestParam(value="platform", defaultValue="") String platform) {
        if (StringUtils.isNotBlank(packageType) && !ApiCloneConfig.isValidPackageTypeIgnoreCase(packageType)) {
            return ApiErrorCode.PACKAGE_QUERY_TYPE_ERROR;
        }
        JSONArray result = new JSONArray();
        boolean isAllPackageType = StringUtils.isBlank(packageType);
        boolean isAllPlatforms = StringUtils.isBlank(platform);
        if (isAllPackageType || StringUtils.equalsIgnoreCase("Firmware", packageType)) {
            for (UpgSetting upgSetting : JpaManager.getUpgSettingManager().loadAll()) {
                String upgPlatform = PlatformUtils.convertToPlatformWithoutSpace(upgSetting.getPlatform());
                if (!isAllPlatforms && !StringUtils.equalsIgnoreCase(upgPlatform, platform)) continue;
                result.put(this.makeCloneItemObject(upgSetting.getId(), upgSetting.getUpgrename(), "Firmware", upgSetting.getIpversion(), upgPlatform));
            }
        }
        if (isAllPackageType || StringUtils.equalsIgnoreCase("Clone", packageType)) {
            for (Setting setting : JpaManager.getSettingManager().loadAll()) {
                String settingPlatform = PlatformUtils.convertToPlatformWithoutSpace(setting.getPlatform());
                if (!isAllPlatforms && !StringUtils.equalsIgnoreCase(settingPlatform, platform)) continue;
                JSONObject obj = this.makeCloneItemObject(setting.getId(), setting.getClonerename(), "Clone", TpvDateUtils.getIdentifierFormatTime(setting.getLastUpdatedDate()), settingPlatform);
                result.put(obj);
            }
        }
        if (isAllPackageType || StringUtils.equalsIgnoreCase("Settings", packageType)) {
            for (SettingPackage settingPackage : JpaManager.getSettingPackageManager().loadAll()) {
                String settingPackagePlatform = PlatformUtils.convertToPlatformWithoutSpace(settingPackage.getPlatform());
                if (!isAllPlatforms && !StringUtils.equalsIgnoreCase(settingPackagePlatform, platform)) continue;
                result.put(this.makeCloneItemObject(settingPackage.getId(), settingPackage.getName(), "Settings", settingPackage.getLastEdit(), settingPackagePlatform));
            }
        }
        if (isAllPackageType || StringUtils.equalsIgnoreCase("Channels", packageType)) {
            for (ChannelPackage channelPackage : JpaManager.getChannelPackageManager().loadAll()) {
                String channelPackagePlatform = PlatformUtils.convertToPlatformWithoutSpace(channelPackage.getPlatform());
                if (!isAllPlatforms && !StringUtils.equalsIgnoreCase(channelPackagePlatform, platform)) continue;
                result.put(this.makeCloneItemObject(channelPackage.getId(), channelPackage.getName(), "Channels", channelPackage.getLastEdit(), channelPackagePlatform));
            }
        }
        if (isAllPackageType || StringUtils.equalsIgnoreCase("Welcome", packageType)) {
            for (Welcome welcome : JpaManager.getWelcomeManager().loadAll()) {
                String welcomePlatform = PlatformUtils.convertToPlatformWithoutSpace(welcome.getPlatform());
                if (!isAllPlatforms && !StringUtils.equalsIgnoreCase(welcomePlatform, platform)) continue;
                result.put(this.makeCloneItemObject(welcome.getId(), welcome.getName(), "Welcome", welcome.getLastEdit(), welcomePlatform));
            }
        }
        if (isAllPackageType || StringUtils.equalsIgnoreCase("Apps", packageType)) {
            for (AppPackage app : JpaManager.getAppPackageManager().loadAll()) {
                String appPlatform = PlatformUtils.convertToPlatformWithoutSpace(app.getPlatform());
                if (!isAllPlatforms && !StringUtils.equalsIgnoreCase(appPlatform, platform)) continue;
                result.put(this.makeCloneItemObject(app.getId(), app.getName(), "Apps", app.getLastEdit(), appPlatform));
            }
        }
        if (isAllPackageType || StringUtils.equalsIgnoreCase("Content", packageType)) {
            boolean isSupportQuery = true;
            if (!isAllPlatforms) {
                isSupportQuery = PlatformUtils.getSupportCloneItems(PlatformUtils.convertToFullPlatformData(platform)).contains((Object)CommonConstants.CloneItemType.SmartInfoBrowser);
            }
            if (isSupportQuery) {
                String contentListStr = ContentUtils.getContentList();
                JSONArray jsonArr = new JSONArray(contentListStr);
                String contentPlatform = isAllPlatforms ? PlatformUtils.convertToPlatformWithoutSpace("2019 MS") : platform;
                for (int i = 0; i < jsonArr.length(); ++i) {
                    JSONObject obj = jsonArr.optJSONObject(i);
                    String changeTime = TpvDateUtils.getContentFormatedChangedTime(obj.optString("changed"));
                    result.put(this.makeCloneItemObject(Integer.parseInt(obj.optString("id")), obj.optString("title"), "Content", changeTime, contentPlatform));
                }
            }
        }
        if (isAllPackageType || StringUtils.equalsIgnoreCase("Banners", packageType)) {
            boolean isSupportQuery = true;
            if (!isAllPlatforms) {
                isSupportQuery = PlatformUtils.getSupportCloneItems(PlatformUtils.convertToFullPlatformData(platform)).contains((Object)CommonConstants.CloneItemType.Banner);
            }
            if (isSupportQuery) {
                String bannerPlatform = isAllPlatforms ? PlatformUtils.convertToPlatformWithoutSpace("2019 MS") : platform;
                for (Banners banners : JpaManager.getBannersManager().loadAll()) {
                    result.put(this.makeCloneItemObject(banners.getId(), banners.getName(), "Banners", banners.getLastEdit(), bannerPlatform));
                }
            }
        }
        if (isAllPackageType || StringUtils.equalsIgnoreCase("UI", packageType)) {
            for (UiCustomizations uiCustomizations : JpaManager.getUiCustomizationsManager().loadAll()) {
                String uiCustomizationsPlatform = PlatformUtils.convertToPlatformWithoutSpace(uiCustomizations.getPlatform());
                if (!isAllPlatforms && !StringUtils.equalsIgnoreCase(uiCustomizationsPlatform, platform)) continue;
                result.put(this.makeCloneItemObject(uiCustomizations.getId(), uiCustomizations.getName(), "UI", uiCustomizations.getLastEdit(), uiCustomizationsPlatform));
            }
        }
        if (isAllPackageType || StringUtils.equalsIgnoreCase("Schedules", packageType)) {
            for (Schedule schedule : JpaManager.getScheduleManager().loadAll()) {
                String schedulePlatform = PlatformUtils.convertToPlatformWithoutSpace(schedule.getPlatform());
                if (!isAllPlatforms && !StringUtils.equalsIgnoreCase(schedulePlatform, platform)) continue;
                result.put(this.makeCloneItemObject(schedule.getId(), schedule.getName(), "Schedules", schedule.getLastEdit(), schedulePlatform));
            }
        }
        return result;
    }

    private JSONObject makeCloneItemObject(int id, String name, String packageType, String version, String platformName) {
        JSONObject obj = new JSONObject();
        obj.put("platform", platformName);
        obj.put("package_type", packageType.toLowerCase());
        obj.put("name", name);
        obj.put("id", id);
        obj.put("version", version);
        return obj;
    }
}

