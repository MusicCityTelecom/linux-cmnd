/*
 * Decompiled with CFR 0.152.
 */
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
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.ContentUtils;
import com.tpvision.smartinstall.util.JAPITUtils;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.SiIdentifiers;
import com.tpvision.smartinstall.util.TpvStringUtils;
import com.tpvision.smartinstall.util.Utils;
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
            }
            catch (Exception ex) {
                LOG.error(ex.getMessage(), ex);
                return false;
            }
        }
        return true;
    }

    public static String sendTVSettingsRequestToTV(Devices device, SettingPackage settingPackage) {
        LOG.info("start to force upgrade setting package <{}>", (Object)settingPackage.getId());
        String status = IPUpgradeManager.processCloneUpgradeType(UPGRADE_TYPE_SETTINGS, String.valueOf(settingPackage.getId()), device.getTvuniqueid(), "", null);
        if (status.contains("fail")) {
            return "failed to create tv settings clone file";
        }
        IPUpgradeManager.startUpgrades(device.getTvuniqueid(), "U");
        return "success";
    }

    private static String updateDeviceFirmwareInfo(int firmwareId, String[] tvArray, String platform, UpgSetting upgsettings) {
        String ret = "{\"status\":\"success\"}";
        String platformId = PlatformUtils.getPlatformId(platform);
        tvArray = IPUpgradeManager.removeIncompatibleTVs(tvArray, platformId, "firmware");
        if (IPUpgradeManager.getFlagPartIncompatible()) {
            ret = Utils.buildSuccessReturnJson("hasPartUncompatible", "yes");
        }
        DevicesManager devicesMgr = JpaManager.getDevicesManager();
        for (String tvid : tvArray) {
            Devices device = devicesMgr.loadByKey(tvid);
            if (device == null) {
                LOG.warn("update device firmware info failure as tv <{}> not exist", (Object)tvid);
                continue;
            }
            if (IPUpgradeManager.isTVUpgrading(device)) {
                LOG.warn("update device firmware info failure as tv <{}> in upgrading status", (Object)tvid);
                continue;
            }
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
        return ret;
    }

    private static String handleCopyUpg(int firmwareId, String tvIds, String groupIds) {
        UpgSettingManager smgr = JpaManager.getUpgSettingManager();
        UpgSetting upgsettings = smgr.loadByKey(firmwareId);
        if (upgsettings == null) {
            LOG.error("firmware id:{} not exists, assign fail", (Object)firmwareId);
            return Utils.buildFailReturnJson("Firmware does not exist anymore, please reupload the firmware!");
        }
        String targetTvIds = IPUpgradeManager.getDeviceList(tvIds, groupIds);
        if (StringUtils.isBlank(targetTvIds)) {
            LOG.warn("target upgrade devices is empty, return directly");
            return "{\"status\":\"success\"}";
        }
        String platform = upgsettings.getPlatform();
        try (SettingCreator settingCreator = new SettingCreator(platform);){
            settingCreator.processFirmware(upgsettings);
        }
        catch (IOException ex) {
            LOG.error("process firmware fail, please check firmware file status!", ex);
            return Utils.buildFailReturnJson("Firmware copy failure, please reupload the firmware!");
        }
        String[] tvArray = targetTvIds.split(",");
        return IPUpgradeManager.updateDeviceFirmwareInfo(firmwareId, tvArray, platform, upgsettings);
    }

    private static String sendForceUpgradeRequestToTV(Devices tv) {
        String power = tv.getPowerstatus();
        if (tv.isOnline() && !JAPITUtils.isJapitListening(tv)) {
            power = "offline";
        }
        if (!"offline".equalsIgnoreCase(power)) {
            LOG.info("start to force upgrade tv <{}>, power=<{}>", (Object)tv.getTvipaddress(), (Object)power);
            boolean isReadyToFroceUpgrade = IPCloneServiceManager.refreshTVCloneStatusAndCheckIsForceUpgradeReady(tv);
            if (!isReadyToFroceUpgrade) {
                LOG.warn("can't froce upgrade tv <{}>, as tv not in ready for upgrade status", (Object)tv.getTvipaddress());
                return Utils.buildFailReturnJson("can't froce upgrade the tv due to it's still in another upgrade process");
            }
            IPCloneServiceManager.sendIPCloneDataToTV(tv);
            return Utils.buildSuccessReturnJson("power", power);
        }
        LOG.warn("can't froce upgrade tv <{}>, power=<{}>", (Object)tv.getTvipaddress(), (Object)power);
        return Utils.buildFailReturnJson("can't froce upgrade tv, please check TV's power status");
    }

    public static SettingCreator generateClonePackage(CommonConstants.CloneItemType cloneType, int cloneId, String platformId) throws IOException {
        try (SettingCreator sc = new SettingCreator(platformId);){
            sc.processClonePacket(cloneType.name(), cloneId, null);
            sc.createIpPackages(cloneType.name(), cloneId);
            SettingCreator settingCreator = sc;
            return settingCreator;
        }
    }

    private static void updateGuestInfoCloneId(String roomid, int cloneId, CommonConstants.CloneItemType upgradeType) {
        if (roomid == null) {
            LOG.info("roomid is null,return");
            return;
        }
        List<GuestInfo> guestInfos = JpaManager.getGuestInfoManager().findGuestInfosByRoomid(roomid);
        for (GuestInfo guestInfo : guestInfos) {
            switch (upgradeType) {
                case Clone: 
                case Firmware: {
                    break;
                }
                case TVSettings: {
                    guestInfo.setSettingPackageId(cloneId);
                    break;
                }
                case AndroidApps: {
                    guestInfo.setAppPackageId(cloneId);
                    break;
                }
                case ChannelList: {
                    guestInfo.setChannelPackageId(cloneId);
                    break;
                }
                case SmartInfoBrowser: {
                    guestInfo.setContentId(cloneId);
                    break;
                }
                case Banner: {
                    guestInfo.setBannerId(cloneId);
                    break;
                }
                case Schedules: {
                    guestInfo.setScheduleId(cloneId);
                    break;
                }
                case WelcomeLogo: {
                    guestInfo.setWelcomeId(String.valueOf(cloneId));
                    break;
                }
            }
            JpaManager.getGuestInfoManager().save(guestInfo);
        }
    }

    public static void assignRFPlayouts(String tvs, CommonConstants.CloneItemType upgradeType, int cloneId, String source) {
        CloneItemUtils.CloneItemInfo info = CloneItemUtils.getCloneItemInfo(upgradeType, cloneId);
        ArrayList<String> roomIds = new ArrayList<String>();
        List<Devices> tvlist = IPUpgradeManager.getDevicesList(tvs);
        for (Devices tv : tvlist) {
            String roomid;
            if (!tv.isRFDevice() || !info.isCompatible(tv.getType()) || roomIds.contains(roomid = tv.getTvroomid())) continue;
            roomIds.add(roomid);
        }
        if (roomIds.isEmpty()) {
            LOG.info("no RF devices");
            return;
        }
        String roomStrs = String.join((CharSequence)",", roomIds);
        try {
            PlayoutUtils.addToPlayoutList(String.valueOf(cloneId), upgradeType.name(), roomStrs, source);
        }
        catch (BaseHttpServlet.MessageException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private static String getCloneRenameFromSetting(int cloneId) {
        Setting setting = JpaManager.getSettingManager().loadByKey(cloneId);
        if (setting != null) {
            return setting.getClonerename();
        }
        return "Unknown clone";
    }

    private static void assignClone(Devices tv, CommonConstants.CloneItemType upgradeType, int cloneId, String clonePath, String siAssignItems) {
        IPUpgradeManager.cleanExpiredCloneFiles(tv, clonePath);
        if (cloneId == 0) {
            IPUpgradeManager.resetUpgradeState(tv, "");
            IPUpgradeManager.updateGuestInfoCloneId(tv.getTvroomid(), cloneId, upgradeType);
            return;
        }
        tv.setCloneType(upgradeType.name());
        tv.setProgress("U");
        String firmwareIdentifier = null;
        if (upgradeType == CommonConstants.CloneItemType.Clone) {
            tv.setLastCloneRename(IPUpgradeManager.getCloneRenameFromSetting(cloneId));
            tv.setCloneid(cloneId);
            tv.setClonePath(clonePath);
            CloneItemUtils.CloneItemInfo info = null;
            try {
                info = CloneItemUtils.getCloneItemInfo(upgradeType, cloneId);
            }
            catch (Exception e) {
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
        String siIdentifiers = IPUpgradeManager.getSiAssignIdentifiers(tv.getSiIdentifiers(), siAssignItems, firmwareIdentifier);
        tv.setSiIdentifiers(siIdentifiers);
        JpaManager.getDevicesManager().save(tv);
        IPUpgradeManager.updateGuestInfoCloneId(tv.getTvroomid(), cloneId, upgradeType);
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
        String[] tvids;
        ArrayList<Devices> tvList = new ArrayList<Devices>();
        for (String tvid : tvids = tvs.split(",")) {
            Devices dev = JpaManager.getDevicesManager().loadByKey(tvid);
            if (dev == null) continue;
            tvList.add(dev);
        }
        return tvList;
    }

    private static void resetUpgradeState(Devices tv, String selectType) {
        SiIdentifiers siIdentifiers;
        if (UPGRADE_TYPE_FIRMWARE.equalsIgnoreCase(selectType)) {
            tv.setFirmwareid(0);
            tv.setSiFirmwareIdentifier(null);
            tv.setFwColor("black");
            siIdentifiers = SiIdentifiers.fromJson(tv.getSiIdentifiers());
            siIdentifiers.removeSiItemByItemName("MainFirmware");
            tv.setSiIdentifiers(siIdentifiers.toJson());
        } else if (StringUtils.isBlank(selectType) || UPGRADE_TYPE_NONE.equalsIgnoreCase(selectType)) {
            tv.setFirmwareid(0);
            tv.setSiFirmwareIdentifier(null);
            tv.setFwColor("black");
            tv.setCloneid(0);
            tv.setSiCloneIdentifiers(null);
            tv.setCloneColor("black");
            siIdentifiers = SiIdentifiers.fromJson(tv.getSiIdentifiers());
            siIdentifiers.SiAssignItem.clear();
            tv.setSiIdentifiers(siIdentifiers.toJson());
        } else {
            tv.setCloneid(0);
            tv.setSiCloneIdentifiers(null);
            tv.setCloneColor("black");
            siIdentifiers = SiIdentifiers.fromJson(tv.getSiIdentifiers());
            siIdentifiers.removeAllSiItemNotItemName("MainFirmware");
            tv.setSiIdentifiers(siIdentifiers.toJson());
        }
        if (tv.getFirmwareid() <= 0 && tv.getCloneid() <= 0) {
            boolean isSendCancelJapit = IPUpgradeManager.isRequireSendCancelJapit(tv);
            tv.setProgress("ST");
            if (isSendCancelJapit) {
                IPCloneServiceManager.cancelIpCloneServiceForTV(tv);
            }
        }
        if (tv.getCloneid() <= 0) {
            IPUpgradeManager.cleanExpiredCloneFiles(tv, null);
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
        return IPUpgradeManager.isTVUpgrading(tv) && PlatformUtils.isSupportUpgradeStopJapit(tv.getType());
    }

    public static UpgradeResult assignClones(List<Devices> tvList, CommonConstants.CloneItemType cloneType, int cloneId) throws UpgradeException {
        UpgradeResult upgradeResult = new UpgradeResult();
        upgradeResult.status = "Success";
        if (cloneId == 0) {
            for (Devices tv : tvList) {
                IPUpgradeManager.resetUpgradeState(tv, "");
            }
            return upgradeResult;
        }
        String platform = CloneItemUtils.getCloneItemPlatform(cloneType, cloneId);
        if (platform == null) {
            platform = PlatformUtils.getPlatformId(tvList.get(0).getType());
        }
        String clonePath = null;
        String siAssignItems = null;
        try {
            SettingCreator sc = IPUpgradeManager.generateClonePackage(cloneType, cloneId, platform);
            clonePath = sc.getClonePath();
            if (cloneType != CommonConstants.CloneItemType.Firmware) {
                siAssignItems = sc.getSiAssignCloneItems();
            }
        }
        catch (IOException e) {
            throw new UpgradeException("generate clone package failed:" + e.getMessage());
        }
        for (Devices tv : tvList) {
            if (!PlatformUtils.isCompatiblePlatform(tv.getType(), platform)) {
                LOG.error("tv {} not compatible with clone item {}[{}]", tv.getId(), cloneType.name(), cloneId);
                upgradeResult.hasUncompatible = true;
                upgradeResult.uncompatibleTvList.add(tv.getId());
                continue;
            }
            IPUpgradeManager.assignClone(tv, cloneType, cloneId, clonePath, siAssignItems);
        }
        return upgradeResult;
    }

    public static boolean startUpgrades(String tvs, String progress) {
        boolean result;
        block6: {
            List<Devices> tvList;
            block5: {
                tvList = IPUpgradeManager.getDevicesList(tvs);
                result = true;
                if (!progress.equalsIgnoreCase("U")) break block5;
                for (Devices tv : tvList) {
                    if (tv.isRFDevice()) {
                        LOG.warn("startUpgrade is RF TV: {}", (Object)tv.getId());
                        continue;
                    }
                    if (!"U".equalsIgnoreCase(tv.getProgress())) {
                        LOG.warn("update failed, TV: {} does not have a clone package assigned", (Object)tv.getId());
                        continue;
                    }
                    if (IPUpgradeManager.isTVUpgrading(tv)) {
                        LOG.warn("Cannot start new update, upgrade process is already started for TV: {}", (Object)tv.getId());
                        continue;
                    }
                    String status = IPUpgradeManager.sendForceUpgradeRequestToTV(tv);
                    if (!status.contains("fail")) continue;
                    result = false;
                }
                break block6;
            }
            if (!progress.equalsIgnoreCase("ST")) break block6;
            for (Devices tv : tvList) {
                IPUpgradeManager.resetUpgradeState(tv, "");
            }
        }
        return result;
    }

    public static String modeUpdatingProgress(String process, List<String> idList, String selectType) {
        String status = "{\"status\":\"success\"}";
        boolean faileStatus = false;
        String reason = "";
        DevicesManager tvmanager = JpaManager.getDevicesManager();
        for (int i = 0; i < idList.size(); ++i) {
            String tvId = idList.get(i);
            Devices tv = tvmanager.loadByKey(tvId);
            if (null == tv) {
                LOG.error("modeUpdatingProgress tv = null for tvIdsArray ={} ", (Object)tvId);
                continue;
            }
            if ("ST".equalsIgnoreCase(process)) {
                if ("Upload".equalsIgnoreCase(tv.getCloneMode())) {
                    tv.setUploadProgress(process);
                    tv.setCloneMode("Upgrade");
                    tvmanager.save(tv);
                    continue;
                }
                if (IPUpgradeManager.isRequireSendCancelJapit(tv) && ("offline".equalsIgnoreCase(tv.getPowerstatus()) || !JAPITUtils.isJapitListening(tv))) {
                    LOG.error("cant send cancel japit to tv<{}> as TV is not connected", (Object)tv.getTvipaddress());
                    faileStatus = true;
                    if (idList.size() == 1) {
                        reason = "Stop failure,TV is not connected!";
                        continue;
                    }
                    if (idList.size() <= 1) continue;
                    reason = "Partial TV is not connected!";
                    continue;
                }
                IPUpgradeManager.resetUpgradeState(tv, selectType);
                continue;
            }
            if (!"U".equalsIgnoreCase(process)) continue;
            LOG.info("ACTION: SI User do FORCE UPGRADE action for TV! forceUpgradePara: platfrom: {}, TV Name:{}, PowerStatus: {}, CurrentProgress:{}", tv.getType(), tv.getTvname(), tv.getPowerstatus(), tv.getProgress());
            if ("offline".equalsIgnoreCase(tv.getPowerstatus())) {
                faileStatus = true;
                if (idList.size() == 1) {
                    reason = "TV is not connected!";
                } else if (idList.size() > 1) {
                    reason = "Partial TV is not connected!";
                }
            }
            if ("U".equalsIgnoreCase(tv.getProgress())) {
                status = IPUpgradeManager.sendForceUpgradeRequestToTV(tv);
                continue;
            }
            faileStatus = true;
            reason = "Clone data not ready, please select clone";
        }
        if (faileStatus) {
            status = Utils.buildFailReturnJson(reason);
        }
        return status;
    }

    public static void cleanExpiredCloneFiles(Devices tv, String newClonePath) {
        String cloneId;
        Pattern r;
        Matcher m;
        String oldClonePath = tv.getClonePath();
        if (oldClonePath == null || oldClonePath.isEmpty()) {
            LOG.info("tv :{} clonePath is empty,return", (Object)tv.getTvuniqueid());
            return;
        }
        if (StringUtils.equalsIgnoreCase(oldClonePath, newClonePath)) {
            LOG.info("tv:{} current clone path <{}> still in use, no need to clean", (Object)tv.getTvuniqueid(), (Object)newClonePath);
            return;
        }
        tv.setClonePath(null);
        JpaManager.getDevicesManager().save(tv);
        if (oldClonePath.startsWith(UPGRADE_TYPE_CLONE) && (m = (r = Pattern.compile("Clone\\/(\\d+)-*")).matcher(oldClonePath)).find() && TriggerUtils.isCloneAssigned(Integer.parseInt(cloneId = m.group(1)))) {
            LOG.info("clone {} as assigned to trigger now, will not clean cache", (Object)cloneId);
            return;
        }
        List<Devices> tvs = JpaManager.getDevicesManager().findDevicesByClonePath(oldClonePath);
        if (tvs.isEmpty()) {
            String fullPath = CommonConstants.servletContextPath + "/Profile/Clone/" + oldClonePath;
            FileUtils.deleteQuietly(new File(fullPath));
            LOG.info("delete profile file:{}", (Object)fullPath);
        }
    }

    public static String processCloneUpgradeType(String selectCloneType, String cloneIdStr, String tvIds, String groupIds, Map<String, String> parameters) {
        String status = "{\"status\":\"fail\"}";
        LOG.info("select_cloneType:{},cloneIdStr:{}", (Object)selectCloneType, (Object)cloneIdStr);
        if (null == parameters) {
            parameters = new HashMap<String, String>();
        }
        parameters.put("id", cloneIdStr);
        parameters.put("tvids", tvIds);
        parameters.put("groupIds", groupIds);
        parameters.put("selectType", selectCloneType);
        parameters.put("partialClone", defaultPartialClone);
        if (parameters.get("output") == null) {
            parameters.put("output", "C");
        }
        parameters.put(SELECT_CLONE_TYPE, selectCloneType);
        int cloneId = TpvStringUtils.tryParseInt(cloneIdStr, -1);
        if (UPGRADE_TYPE_NONE.equalsIgnoreCase(selectCloneType) || cloneId <= 0) {
            return IPUpgradeManager.handleAssignNone(tvIds, groupIds, selectCloneType);
        }
        if (UPGRADE_TYPE_FIRMWARE.equals(selectCloneType)) {
            return IPUpgradeManager.handleCopyUpg(cloneId, tvIds, groupIds);
        }
        if (UPGRADE_TYPE_CLONE.equals(selectCloneType)) {
            return IPUpgradeManager.processCloneUpgradeByPara(cloneIdStr, tvIds, groupIds, selectCloneType, parameters);
        }
        String platformId = IPUpgradeManager.getPlatformByID(selectCloneType, cloneId, tvIds, groupIds);
        if (platformId == null) {
            return Utils.buildFailReturnJson("device platform check failure, please check the platform of the seleceted devices!");
        }
        try (SettingCreator settingCreator = new SettingCreator(platformId);){
            CommonConstants.CloneItemType convertType = IPUpgradeManager.convertUpgradeTypeToCloneItemType(selectCloneType);
            IPUpgradeManager.processClonePacket(convertType, cloneId, settingCreator);
            settingCreator.processUpgCreation(parameters);
            String siAssignCloneItems = settingCreator.getSiAssignCloneItems();
            String clonePath = settingCreator.getClonePath();
            status = IPUpgradeManager.updateProgressViaIP(cloneId, clonePath, tvIds, groupIds, platformId, selectCloneType, siAssignCloneItems);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return status;
    }

    private static boolean notCheckCompatibleBySelectCloneType(String selectCloneType) {
        return UPGRADE_TYPE_CONTENT.equals(selectCloneType);
    }

    public static String convertCloneItemTypeToUpgradeType(CommonConstants.CloneItemType type) {
        String upgradeType = UPGRADE_TYPE_CLONE;
        switch (type) {
            case Firmware: {
                upgradeType = UPGRADE_TYPE_FIRMWARE;
                break;
            }
            case TVSettings: {
                upgradeType = UPGRADE_TYPE_SETTINGS;
                break;
            }
            case ChannelList: {
                upgradeType = UPGRADE_TYPE_CHANNELS;
                break;
            }
            case AndroidApps: {
                upgradeType = UPGRADE_TYPE_APPS;
                break;
            }
            case SmartInfoBrowser: {
                upgradeType = UPGRADE_TYPE_CONTENT;
                break;
            }
            case Banner: {
                upgradeType = UPGRADE_TYPE_BANNERS;
                break;
            }
            case UiCustomizations: {
                upgradeType = UPGRADE_TYPE_UI;
                break;
            }
            case Schedules: {
                upgradeType = UPGRADE_TYPE_SCHEDULES;
                break;
            }
            case WelcomeLogo: {
                upgradeType = UPGRADE_TYPE_WELCOME;
                break;
            }
        }
        return upgradeType;
    }

    public static CommonConstants.CloneItemType convertUpgradeTypeToCloneItemType(String type) {
        CommonConstants.CloneItemType cloneItemType = CommonConstants.CloneItemType.UnKnownItem;
        switch (type) {
            case "Clone": {
                cloneItemType = CommonConstants.CloneItemType.Clone;
                break;
            }
            case "Firmware": {
                cloneItemType = CommonConstants.CloneItemType.Firmware;
                break;
            }
            case "Settings": {
                cloneItemType = CommonConstants.CloneItemType.TVSettings;
                break;
            }
            case "Channels": {
                cloneItemType = CommonConstants.CloneItemType.ChannelList;
                break;
            }
            case "Apps": {
                cloneItemType = CommonConstants.CloneItemType.AndroidApps;
                break;
            }
            case "Content": {
                cloneItemType = CommonConstants.CloneItemType.SmartInfoBrowser;
                break;
            }
            case "Banners": {
                cloneItemType = CommonConstants.CloneItemType.Banner;
                break;
            }
            case "UI": {
                cloneItemType = CommonConstants.CloneItemType.UiCustomizations;
                break;
            }
            case "Schedules": {
                cloneItemType = CommonConstants.CloneItemType.Schedules;
                break;
            }
            case "Welcome": {
                cloneItemType = CommonConstants.CloneItemType.WelcomeLogo;
                break;
            }
            case "Weather": {
                cloneItemType = CommonConstants.CloneItemType.WeatherForecast;
                break;
            }
        }
        return cloneItemType;
    }

    private static boolean getFlagPartIncompatible() {
        return FLAG_PARTINCOMPATIBLE;
    }

    private static String[] removeIncompatibleTVs(String[] tvIds, String platform, String selectCloneType) {
        DevicesManager dm = JpaManager.getDevicesManager();
        ArrayList<String> correctPlatformTVs = new ArrayList<String>();
        for (String tvId : tvIds) {
            correctPlatformTVs.add(tvId);
        }
        FLAG_PARTINCOMPATIBLE = false;
        Iterator it = correctPlatformTVs.iterator();
        while (it.hasNext()) {
            String id = (String)it.next();
            Devices device = dm.loadByKey(id);
            if (null != device) {
                String type = device.getType();
                if (PlatformUtils.isCompatiblePlatform(type, platform) || "2019 NAFTA".equalsIgnoreCase(type) && "banners".equalsIgnoreCase(selectCloneType)) continue;
                it.remove();
                FLAG_PARTINCOMPATIBLE = true;
                continue;
            }
            it.remove();
            IPProfile.updateIPProfile(id);
        }
        if (correctPlatformTVs.isEmpty()) {
            return null;
        }
        return correctPlatformTVs.toArray(new String[0]);
    }

    public static boolean isTVUpgrading(Devices tv) {
        return "INP".equalsIgnoreCase(tv.getProgress());
    }

    public static String getDeviceList(String tvIds, String groupIds) {
        ArrayList<String> tvidList = new ArrayList<String>();
        if (tvIds != null && !tvIds.isEmpty()) {
            String[] tvs = tvIds.split(",");
            tvidList.addAll(Arrays.asList(tvs));
        }
        if (groupIds != null && !groupIds.isEmpty()) {
            String[] groups;
            for (String groupName : groups = groupIds.split(",")) {
                for (Groups group : JpaManager.getGroupsManager().findGroupsByGroupName(groupName)) {
                    tvidList.add(group.getTvid());
                }
            }
        }
        CharSequence[] tvidArray = new HashSet(tvidList).toArray(new String[0]);
        return String.join((CharSequence)",", tvidArray);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String processCloneUpgradeByPara(String cloneIdStr, String tvIds, String groupIds, String selectCloneType, Map<String, String> parameters) {
        SettingManager sm = JpaManager.getSettingManager();
        Setting settingDb = sm.loadByKey(Integer.parseInt(cloneIdStr));
        if (null == settingDb) return "{\"status\":\"success\"}";
        try (SettingCreator settingCreator = new SettingCreator(settingDb.getPlatform());){
            settingCreator.processSettings(settingDb);
            String platform = settingDb.getPlatform();
            if (!PlatformUtils.isAsta2016Up(platform)) {
                IPUpgradeManager.copyFromAssemblyToPlayout(settingCreator.getOutputPath(), PlatformUtils.getPlatformId(platform));
            }
            settingCreator.processUpgCreation(parameters);
            String siItemIdentifiers = settingCreator.getSiAssignCloneItems();
            String clonePath = settingCreator.getClonePath();
            String string = IPUpgradeManager.updateProgressViaIP(settingDb.getId(), clonePath, tvIds, groupIds, platform, selectCloneType, siItemIdentifiers);
            return string;
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return "{\"status\":\"success\"}";
    }

    public static String handleAssignNone(String tvIds, String groupIds, String selectCloneType) {
        String[] tvIdArray = tvIds.split(",");
        String[] groupIdArray = groupIds.split(",");
        GroupsManager gm = JpaManager.getGroupsManager();
        DevicesManager dm = JpaManager.getDevicesManager();
        ArrayList<Devices> updateDevices = new ArrayList<Devices>();
        ArrayList<Groups> updateGroups = new ArrayList<Groups>();
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
                    if (device == null) continue;
                    updateDevices.add(device);
                }
            }
        }
        for (Devices device : updateDevices) {
            IPUpgradeManager.resetUpgradeState(device, selectCloneType);
        }
        return "{\"status\":\"success\"}";
    }

    private static boolean processClonePacket(CommonConstants.CloneItemType cloneItemType, int id, SettingCreator settingCreator) {
        try {
            switch (cloneItemType) {
                case Clone: {
                    SettingManager settingMgr = JpaManager.getSettingManager();
                    Setting setting = settingMgr.loadByKey(id);
                    if (null != setting) {
                        settingCreator.processSettings(setting);
                    }
                    break;
                }
                case Firmware: {
                    UpgSettingManager upgSettingMgr = JpaManager.getUpgSettingManager();
                    UpgSetting upgSetting = upgSettingMgr.loadByKey(id);
                    if (null != upgSetting) {
                        settingCreator.processFirmware(upgSetting);
                    }
                    break;
                }
                case TVSettings: {
                    SettingPackageManager settingpackageMgr = JpaManager.getSettingPackageManager();
                    SettingPackage settingpackage = settingpackageMgr.loadByKey(id);
                    if (null != settingpackage) {
                        settingCreator.processTVSettings(settingpackage);
                    }
                    break;
                }
                case ChannelList: {
                    ChannelPackageManager channelpackageMgr = JpaManager.getChannelPackageManager();
                    ChannelPackage channelpackage = channelpackageMgr.loadByKey(id);
                    if (null != channelpackage) {
                        settingCreator.processChannelPackage(channelpackage);
                    }
                    break;
                }
                case AndroidApps: {
                    AppPackageManager apppackageMgr = JpaManager.getAppPackageManager();
                    AppPackage apppackage = apppackageMgr.loadByKey(id);
                    if (null != apppackage) {
                        settingCreator.processAppPackage(apppackage);
                    }
                    break;
                }
                case SmartInfoBrowser: {
                    settingCreator.processContent(id);
                    break;
                }
                case Banner: {
                    Banners banners = JpaManager.getBannersManager().loadByKey(id);
                    if (null != banners) {
                        settingCreator.processBanner(banners);
                    }
                    break;
                }
                case UiCustomizations: {
                    UiCustomizations uicustom = JpaManager.getUiCustomizationsManager().loadByKey(id);
                    if (null != uicustom) {
                        settingCreator.processUiCustomizations(uicustom);
                    }
                    break;
                }
                case WelcomeLogo: {
                    Welcome welcome = JpaManager.getWelcomeManager().loadByKey(id);
                    if (null != welcome) {
                        settingCreator.processWelcome(welcome.getId());
                    }
                    break;
                }
                case Schedules: {
                    Schedule schedules = JpaManager.getScheduleManager().loadByKey(id);
                    if (null != schedules) {
                        settingCreator.processSchedules(schedules);
                        settingCreator.initEnableScheduleTvSettings(schedules.getLastEdit());
                    }
                    break;
                }
                default: {
                    LOG.error("not support to process clonetype:{}", (Object)cloneItemType);
                    break;
                }
            }
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    public static String getPlatformByID(String cloneType, int id, String tvids, String groupIds) {
        String platform = null;
        try {
            ScheduleManager scheduleManager;
            Schedule schedule;
            if (UPGRADE_TYPE_CLONE.equalsIgnoreCase(cloneType)) {
                SettingManager settingMgr = JpaManager.getSettingManager();
                Setting setting = settingMgr.loadByKey(id);
                if (null != setting) {
                    platform = setting.getPlatform();
                }
            } else if ("setting".equalsIgnoreCase(cloneType) || UPGRADE_TYPE_SETTINGS.equalsIgnoreCase(cloneType)) {
                SettingPackageManager settingpackageMgr = JpaManager.getSettingPackageManager();
                SettingPackage settingpackage = settingpackageMgr.loadByKey(id);
                if (null != settingpackage) {
                    platform = settingpackage.getPlatform();
                }
            } else if ("channel".equalsIgnoreCase(cloneType) || UPGRADE_TYPE_CHANNELS.equalsIgnoreCase(cloneType)) {
                ChannelPackageManager channelpackageMgr = JpaManager.getChannelPackageManager();
                ChannelPackage channelpackage = channelpackageMgr.loadByKey(id);
                if (null != channelpackage) {
                    platform = channelpackage.getPlatform();
                }
            } else if ("app".equalsIgnoreCase(cloneType) || UPGRADE_TYPE_APPS.equalsIgnoreCase(cloneType)) {
                AppPackageManager apppackageMgr = JpaManager.getAppPackageManager();
                AppPackage apppackage = apppackageMgr.loadByKey(id);
                if (null != apppackage) {
                    platform = apppackage.getPlatform();
                }
            } else if (UPGRADE_TYPE_FIRMWARE.equalsIgnoreCase(cloneType)) {
                UpgSettingManager upgSettingMgr = JpaManager.getUpgSettingManager();
                UpgSetting upgSetting = upgSettingMgr.loadByKey(id);
                if (null != upgSetting) {
                    platform = upgSetting.getPlatform();
                }
            } else if (UPGRADE_TYPE_CONTENT.equalsIgnoreCase(cloneType)) {
                String targetTvIds = IPUpgradeManager.getDeviceList(tvids, groupIds);
                List<String> platformNameList = IPUpgradeManager.getPlatformByDevice(targetTvIds);
                if (platformNameList.size() != 1) {
                    LOG.warn("selected devices {} or groups {} has more than one platform :{}", tvids, groupIds, platformNameList);
                    return null;
                }
                platform = platformNameList.get(0);
            } else if (UPGRADE_TYPE_BANNERS.equalsIgnoreCase(cloneType)) {
                BannersManager bannersMgr = JpaManager.getBannersManager();
                Banners banner = bannersMgr.loadByKey(id);
                if (null != banner) {
                    platform = banner.getPlatform();
                }
            } else if (UPGRADE_TYPE_UI.equalsIgnoreCase(cloneType)) {
                UiCustomizationsManager uiCustomizationsManager = JpaManager.getUiCustomizationsManager();
                UiCustomizations uiCustomizations = uiCustomizationsManager.loadByKey(id);
                if (null != uiCustomizations) {
                    platform = uiCustomizations.getPlatform();
                }
            } else if (UPGRADE_TYPE_WELCOME.equalsIgnoreCase(cloneType)) {
                WelcomeManager welcomeManager = JpaManager.getWelcomeManager();
                Welcome welcome = welcomeManager.loadByKey(id);
                if (null != welcome) {
                    platform = welcome.getPlatform();
                }
            } else if (UPGRADE_TYPE_SCHEDULES.equalsIgnoreCase(cloneType) && null != (schedule = (scheduleManager = JpaManager.getScheduleManager()).loadByKey(id))) {
                platform = schedule.getPlatform();
            }
        }
        catch (Exception e) {
            LOG.error("getPlatformByID fail");
        }
        if (platform == null) {
            LOG.error("get platform by clonetype:{} and cloneid: {} failed", (Object)cloneType, (Object)id);
            return null;
        }
        return PlatformUtils.getPlatformId(platform);
    }

    public static int getCloneIdByCloneTypeAndName(String cloneType, String cloneName) {
        try {
            ScheduleManager scheduleManager;
            List<Schedule> schedules;
            if (UPGRADE_TYPE_CLONE.equalsIgnoreCase(cloneType)) {
                SettingManager settingMgr = JpaManager.getSettingManager();
                List<Setting> settings = settingMgr.findSettingsByCloneRename(cloneName);
                if (settings.size() == 1) {
                    return settings.get(0).getId();
                }
            } else if ("setting".equalsIgnoreCase(cloneType) || UPGRADE_TYPE_SETTINGS.equalsIgnoreCase(cloneType)) {
                SettingPackageManager settingpackageMgr = JpaManager.getSettingPackageManager();
                List<SettingPackage> settingpackages = settingpackageMgr.findByName(cloneName);
                if (settingpackages.size() == 1) {
                    return settingpackages.get(0).getId();
                }
            } else if ("channel".equalsIgnoreCase(cloneType) || UPGRADE_TYPE_CHANNELS.equalsIgnoreCase(cloneType)) {
                ChannelPackageManager channelpackageMgr = JpaManager.getChannelPackageManager();
                List<ChannelPackage> channelpackages = channelpackageMgr.findByName(cloneName);
                if (channelpackages.size() == 1) {
                    return channelpackages.get(0).getId();
                }
            } else if ("app".equalsIgnoreCase(cloneType) || UPGRADE_TYPE_APPS.equalsIgnoreCase(cloneType)) {
                AppPackageManager apppackageMgr = JpaManager.getAppPackageManager();
                List<AppPackage> apppackages = apppackageMgr.findByName(cloneName);
                if (apppackages.size() == 1) {
                    return apppackages.get(0).getId();
                }
            } else if (UPGRADE_TYPE_FIRMWARE.equalsIgnoreCase(cloneType)) {
                UpgSettingManager upgSettingMgr = JpaManager.getUpgSettingManager();
                List<UpgSetting> upgSettings = upgSettingMgr.findByUpgRename(cloneName);
                if (upgSettings.size() == 1) {
                    return upgSettings.get(0).getId();
                }
            } else if (UPGRADE_TYPE_CONTENT.equalsIgnoreCase(cloneType)) {
                List<Integer> idList = ContentUtils.findContentIdByTitle(cloneName);
                if (idList.size() == 1) {
                    return idList.get(0);
                }
            } else if (UPGRADE_TYPE_BANNERS.equalsIgnoreCase(cloneType)) {
                BannersManager bannersManager = JpaManager.getBannersManager();
                List<Banners> banners = bannersManager.findByName(cloneName);
                if (banners.size() == 1) {
                    return banners.get(0).getId();
                }
            } else if (UPGRADE_TYPE_UI.equalsIgnoreCase(cloneType)) {
                UiCustomizationsManager uiCustomizationsManager = JpaManager.getUiCustomizationsManager();
                List<UiCustomizations> uiCustomizations = uiCustomizationsManager.findByName(cloneName);
                if (uiCustomizations.size() == 1) {
                    return uiCustomizations.get(0).getId();
                }
            } else if (UPGRADE_TYPE_WELCOME.equalsIgnoreCase(cloneType)) {
                WelcomeManager welcomeManager = JpaManager.getWelcomeManager();
                List<Welcome> welcomes = welcomeManager.findWelcomesByName(cloneName);
                if (welcomes.size() == 1) {
                    return welcomes.get(0).getId();
                }
            } else if (UPGRADE_TYPE_SCHEDULES.equalsIgnoreCase(cloneType) && (schedules = (scheduleManager = JpaManager.getScheduleManager()).findByName(cloneName)).size() == 1) {
                return schedules.get(0).getId();
            }
        }
        catch (Exception e) {
            LOG.error("get clone Id fail");
        }
        return -1;
    }

    private static List<String> getPlatformByDevice(String tvIds) {
        ArrayList<String> platformNameList = new ArrayList<String>();
        for (String tvId : tvIds.split(",")) {
            Devices devices = JpaManager.getDevicesManager().loadByKey(tvId);
            if (devices == null || platformNameList.contains(devices.getType())) continue;
            platformNameList.add(devices.getType());
        }
        return platformNameList;
    }

    private static String updateProgressViaIP(int cloneId, String clonePath, String tvIds, String groupNames, String platform, String selectCloneType, String siAssignCloneItems) {
        if (StringUtils.isBlank(siAssignCloneItems) || !siAssignCloneItems.contains("CloneItemName")) {
            return Utils.buildFailReturnJson("assign clone package is empty or corrupted,Please check!");
        }
        String targetTvIds = IPUpgradeManager.getDeviceList(tvIds, groupNames);
        LOG.info("updated tvIds={}", (Object)targetTvIds);
        if (StringUtils.isNotEmpty(targetTvIds)) {
            String[] tvIdArray = targetTvIds.split(",");
            if (!IPUpgradeManager.notCheckCompatibleBySelectCloneType(selectCloneType) && null != platform && !"".equalsIgnoreCase(platform)) {
                tvIdArray = IPUpgradeManager.removeIncompatibleTVs(tvIdArray, platform, selectCloneType);
            }
            IPUpgradeManager.updateDeviceCloneInfo(tvIdArray, cloneId, clonePath, selectCloneType, siAssignCloneItems);
        }
        if (IPUpgradeManager.getFlagPartIncompatible()) {
            return "{\"status\":\"success\",\"hasPartUncompatible\":\"yes\",\"sameCloneVersion\":\"no\"}";
        }
        return "{\"status\":\"success\"}";
    }

    private static CloneItemUtils.CloneItemInfo getParmeters(int cloneId, String selectCloneType) {
        CommonConstants.CloneItemType cloneItemType = IPUpgradeManager.convertUpgradeTypeToCloneItemType(selectCloneType);
        return CloneItemUtils.getCloneItemInfo(cloneItemType, cloneId);
    }

    private static void updateDeviceCloneInfo(String[] tvIdArray, int cloneId, String clonePath, String selectCloneType, String siAssignCloneItems) {
        if (cloneId <= 0 || null == tvIdArray || tvIdArray.length == 0) {
            LOG.info("cloneId is blank or tvId is blank");
            return;
        }
        DevicesManager deviceMgr = JpaManager.getDevicesManager();
        CloneItemUtils.CloneItemInfo cloneItemInfo = IPUpgradeManager.getParmeters(cloneId, selectCloneType);
        for (String tvId : tvIdArray) {
            Devices device = deviceMgr.loadByKey(tvId);
            if (IPUpgradeManager.isTVUpgrading(device)) {
                LOG.warn("update device clone info failure as tv <{}> in upgrading status", (Object)tvId);
                continue;
            }
            IPUpgradeManager.cleanExpiredCloneFiles(device, clonePath);
            device.setCloneid(cloneId);
            device.setUpgradeType(UPGRADE_TYPE_CLONE);
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

    public static void copyFromAssemblyToPlayout(String assemblyPath, String platformId) throws IOException {
        long startTime = System.currentTimeMillis();
        File f = new File(assemblyPath);
        File f1 = new File(CommonConstants.RF_PLAY_BACK_INPUT_LOCATION + platformId);
        FileUtils.deleteDirectory(f1);
        f1.mkdirs();
        FileUtils.copyDirectory(f, f1);
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        LOG.info("copyFromAssemblyToPlayout finished,used {} ms", (Object)totalTime);
    }

    public static String getUpgradeStatusByColor(String color) {
        if (color.equalsIgnoreCase("black")) {
            return "Nothing assigned";
        }
        if (color.equalsIgnoreCase("blue")) {
            return "Not started";
        }
        if (color.equalsIgnoreCase("#FFBF00")) {
            return "In progress";
        }
        if (color.equalsIgnoreCase("#01DF01")) {
            return "Successful";
        }
        if (color.equalsIgnoreCase("red")) {
            return "Failed";
        }
        return "Unknown";
    }

    public static class UpgradeResult {
        public String status = "Success";
        public boolean hasUncompatible = false;
        public List<String> uncompatibleTvList = new ArrayList<String>();
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

    public static class UpgradeException
    extends IOException {
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
}

