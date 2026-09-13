/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.gateway;

import com.tpvision.smartinstall.core.SettingCreator;
import com.tpvision.smartinstall.dao.core.PlayoutInfo;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.core.UpgSetting;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.PlayoutInfoManager;
import com.tpvision.smartinstall.gateway.GatewayManager;
import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.roomcheck.RoomCheckUtil;
import com.tpvision.smartinstall.roomcheck.RoomSelection;
import com.tpvision.smartinstall.schedule.CmndMetricsTask;
import com.tpvision.smartinstall.servlet.BaseHttpServlet;
import com.tpvision.smartinstall.servlet.LastRFConfig;
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.IPUpgradeManager;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.TpvFileUtils;
import com.tpvision.smartinstall.util.TpvNamedThreadFactory;
import com.tpvision.smartinstall.util.TpvStringUtils;
import com.tpvision.smartinstall.util.Utils;
import com.tpvision.smartinstall.util.ZipCommonUtils;
import com.tpvision.smartinstall.xml.psg.configuration.Config;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayoutUtils {
    private static final Logger LOG = LoggerFactory.getLogger(PlayoutUtils.class);
    public static final int MAX_AV_STREAM = 8;
    public static final int MAX_PLAYOUTNAME_LENGTH = 45;
    public static final int PLAY_NOTSTARTED = -1;
    public static final int PLAY_SUCCESS = 0;
    public static final int PLAY_ERROR = 1;
    public static final String PLAYOUT_AV_STREAM = "AV Stream";
    public static final String PLAYOUT_LIVE_STREAM = "Live Stream";
    public static final String PLAYOUT_SOURCE_PMS = "PMS";
    public static final String PLAYOUT_SOURCE_TRIGGER = "Trigger";
    public static final long MAX_CLONE_ITEMS = 42L;
    private static GatewayManager mGatewayManager = GatewayManager.getInstance();
    private static PlayoutUtils singleton = new PlayoutUtils();
    private boolean playoutStopped = false;
    private ScheduledExecutorService service;

    private PlayoutUtils() {
        LastRFConfig lastRFConfig = LastRFConfig.loadLastConfig();
        this.playoutStopped = lastRFConfig.gatewayStopped;
        if (!this.playoutStopped) {
            this.initScheduleService();
        }
    }

    private void initScheduleService() {
        this.service = Executors.newScheduledThreadPool(1, new TpvNamedThreadFactory("playout"));
        this.service.scheduleWithFixedDelay(this::schedulePlayout, 2L, 5L, TimeUnit.SECONDS);
    }

    public static PlayoutUtils getInstance() {
        return singleton;
    }

    public boolean isPlayoutStopped() {
        return this.playoutStopped;
    }

    public void setPlayoutStopped(boolean playoutStopped) {
        LOG.info("set playout stopped:{}", (Object)playoutStopped);
        if (this.playoutStopped != playoutStopped) {
            this.playoutStopped = playoutStopped;
            LastRFConfig lastRFConfig = LastRFConfig.loadLastConfig();
            lastRFConfig.gatewayStopped = playoutStopped;
            lastRFConfig.save();
        }
    }

    public static void triggerStartPlayout() throws BaseHttpServlet.MessageException {
        LOG.info("trigger Start Playout");
        PlayoutUtils.checkRFOutput();
        new Thread(() -> {
            try {
                PlayoutUtils.getInstance().startPlayout();
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }).start();
    }

    public static void processCloneItemsPacket(String cloneType, int settingId, SettingCreator sc) throws IOException {
        Setting setting = JpaManager.getSettingManager().loadByKey(settingId);
        if (setting == null) {
            LOG.error("setting not existed,{}", (Object)settingId);
            return;
        }
        CommonConstants.CloneItemType playType = CommonConstants.CloneItemType.valueOf(cloneType);
        int assignedId = CloneItemUtils.getAssignedId(playType, setting);
        if (assignedId > 0) {
            sc.processClonePacket(cloneType, assignedId, null);
        } else {
            sc.processSettingsUnSupportFile(cloneType, setting.getName());
        }
    }

    private static List<Integer> consolidatePlayoutInfo(JSONArray pmsArray, PlayoutInfo target) {
        int i;
        ArrayList<Integer> finallRoomsInfo = new ArrayList<Integer>();
        List<Integer> targetRoomsInfo = RoomSelection.parseRoomsNum(target.getRooms());
        ArrayList<Integer> indicesToRemove = new ArrayList<Integer>();
        for (i = 0; i < pmsArray.length(); ++i) {
            JSONObject pmsItem = pmsArray.getJSONObject(i);
            JSONObject commandDetails = pmsItem.getJSONObject("CommandDetails");
            JSONObject offlineServiceParameters = commandDetails.getJSONObject("OfflineServiceParameters");
            int roomId = Integer.parseInt(offlineServiceParameters.getString("RoomID"));
            if (targetRoomsInfo.contains(roomId)) {
                finallRoomsInfo.add(roomId);
                continue;
            }
            indicesToRemove.add(i);
        }
        for (i = indicesToRemove.size() - 1; i >= 0; --i) {
            LOG.info("remove room {} from pms.json", indicesToRemove.get(i));
            pmsArray.remove((Integer)indicesToRemove.get(i));
        }
        return finallRoomsInfo;
    }

    private static void mergePlayoutInfo(PlayoutInfo target, PlayoutInfo source) {
        try {
            String targetFilePath = PlayoutUtils.getRFZipFullPath(target);
            String sourceFilePath = PlayoutUtils.getRFZipFullPath(source);
            String targetContent = null;
            String sourceContent = null;
            targetContent = ZipCommonUtils.readJsonFromZip(targetFilePath, "PMS/PMS.json");
            JSONObject targetJsonObject = new JSONObject(targetContent);
            JSONArray targetPmsArray = targetJsonObject.getJSONArray(PLAYOUT_SOURCE_PMS);
            sourceContent = ZipCommonUtils.readJsonFromZip(sourceFilePath, "PMS/PMS.json");
            JSONObject sourceJsonObject = new JSONObject(sourceContent);
            JSONArray sourcePmsArray = sourceJsonObject.getJSONArray(PLAYOUT_SOURCE_PMS);
            List<Integer> targetRoomsInfo = PlayoutUtils.consolidatePlayoutInfo(targetPmsArray, target);
            int sourceRoomId = Integer.parseInt(sourcePmsArray.getJSONObject(0).getJSONObject("CommandDetails").getJSONObject("OfflineServiceParameters").getString("RoomID"));
            int index = targetRoomsInfo.indexOf(sourceRoomId);
            if (index != -1) {
                targetPmsArray.put(index, sourcePmsArray.getJSONObject(0));
            } else {
                targetPmsArray.put(sourcePmsArray.getJSONObject(0));
                targetRoomsInfo.add(sourceRoomId);
            }
            Collections.sort(targetRoomsInfo);
            String targetRooms = RoomSelection.formatRoomsNum(targetRoomsInfo);
            targetJsonObject.put(PLAYOUT_SOURCE_PMS, targetPmsArray);
            Date t = new Date();
            String pmsFileName = String.format("PMS_0_%d.zip", t.getTime());
            String pmsPath = PlayoutUtils.getRFProfilePath() + "PMS/";
            String mergeFilePath = PlayoutUtils.getRFProfilePath() + pmsFileName;
            String version = TpvDateUtils.getIdentifierFormatTime(t);
            File pmsDir = new File(CommonConstants.PROFILE_RF_DIR, PLAYOUT_SOURCE_PMS);
            if (!pmsDir.exists()) {
                pmsDir.mkdir();
            }
            File pmsFile = new File(pmsPath + "PMS.json");
            File pmsIdentifierFile = new File(pmsPath + "PMS_Identifier.txt");
            FileUtils.writeStringToFile(pmsFile, targetJsonObject.toString(), StandardCharsets.UTF_8);
            FileUtils.writeStringToFile(pmsIdentifierFile, version, StandardCharsets.UTF_8);
            if (!new File(mergeFilePath).exists()) {
                ZipCommonUtils.zipFiles(pmsPath, mergeFilePath);
            }
            TpvFileUtils.deleteDirecotry(CommonConstants.PROFILE_RF_DIR + PLAYOUT_SOURCE_PMS);
            ZipCommonUtils.deleteZipFile(CommonConstants.PROFILE_RF_DIR, target.getFilePath(), source.getFilePath());
            String actionName = "";
            for (int i = 0; i < targetPmsArray.length(); ++i) {
                JSONObject pmsItem = targetPmsArray.getJSONObject(i);
                JSONObject commandDetails = pmsItem.getJSONObject("CommandDetails");
                if (actionName.isEmpty()) {
                    actionName = commandDetails.getJSONObject("PMSParameters").getString("Action");
                    continue;
                }
                if (actionName.equalsIgnoreCase(commandDetails.getJSONObject("PMSParameters").getString("Action"))) continue;
                actionName = "CheckIn/Out";
                break;
            }
            target.setRooms(targetRooms);
            target.setName(actionName);
            target.setVersion(version);
            target.setFilePath(pmsFileName);
            PlayoutInfoManager playoutInfoMgr = JpaManager.getPlayoutInfoManager();
            playoutInfoMgr.deleteByKey(source.getPlayoutId());
            playoutInfoMgr.save(target);
        }
        catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    private static int countUniquePlayouts(List<PlayoutInfo> playoutInfoList) {
        HashSet<String> uniqueEntries = new HashSet<String>();
        for (PlayoutInfo info : playoutInfoList) {
            String uniqueKey = info.getName() + "|" + info.getPlatform() + "|" + info.getVersion() + "|" + info.getFilePath();
            uniqueEntries.add(uniqueKey);
        }
        return uniqueEntries.size();
    }

    private static void processPlayoutInfo() {
        long uniqueCount;
        List<PlayoutInfo> infos = JpaManager.getPlayoutInfoManager().findPlayoutInfosExculudeTypes(PLAYOUT_AV_STREAM, PLAYOUT_LIVE_STREAM);
        List pmsPlayoutInfos = infos.stream().filter(info -> PLAYOUT_SOURCE_PMS.equals(info.getType()) && info.getPlatform().contains("2019")).collect(Collectors.toList());
        List pmsMergedPlayoutInfos = pmsPlayoutInfos.stream().filter(info -> info.getRooms() != null && info.getRooms().contains(",")).collect(Collectors.toList());
        if (!pmsMergedPlayoutInfos.isEmpty()) {
            block0: for (PlayoutInfo info2 : pmsPlayoutInfos) {
                String rooms = info2.getRooms();
                if (rooms == null || rooms.contains(",")) continue;
                for (PlayoutInfo mergedInfo : pmsMergedPlayoutInfos) {
                    if (!mergedInfo.getRooms().contains(info2.getRooms())) continue;
                    PlayoutUtils.mergePlayoutInfo(mergedInfo, info2);
                    continue block0;
                }
            }
        }
        if ((uniqueCount = (long)PlayoutUtils.countUniquePlayouts(infos)) > 42L) {
            LOG.info("unique count is {} , start merge flow", (Object)uniqueCount);
            Map<String, List<PlayoutInfo>> groupedByPlatform = JpaManager.getPlayoutInfoManager().findPlayoutInfosExculudeTypes(PLAYOUT_AV_STREAM, PLAYOUT_LIVE_STREAM).stream().filter(info -> PLAYOUT_SOURCE_PMS.equals(info.getType()) && info.getPlatform().contains("2019")).collect(Collectors.groupingBy(PlayoutInfo::getPlatform));
            groupedByPlatform.forEach((platform, playoutInfos) -> {
                LOG.info("Platform:{}", platform);
                PlayoutInfo mergedInfo = (PlayoutInfo)playoutInfos.get(0);
                for (int i = 1; i < playoutInfos.size(); ++i) {
                    PlayoutUtils.mergePlayoutInfo(mergedInfo, (PlayoutInfo)playoutInfos.get(i));
                }
            });
        }
    }

    public JSONArray getCloneItems() {
        PlayoutUtils.processPlayoutInfo();
        List<PlayoutInfo> infos = JpaManager.getPlayoutInfoManager().findPlayoutInfosExculudeTypes(PLAYOUT_AV_STREAM, PLAYOUT_LIVE_STREAM);
        HashMap mapRoomInfos = new HashMap();
        long cloneCounter = 0L;
        HashSet<String> uniqueEntries = new HashSet<String>();
        for (PlayoutInfo info : infos) {
            String uniqueKey = info.getName() + "|" + info.getPlatform() + "|" + info.getVersion() + "|" + info.getFilePath();
            cloneCounter = uniqueEntries.size();
            if (cloneCounter >= 42L) {
                if (uniqueEntries.contains(uniqueKey)) continue;
                PlayoutInfoManager playoutInfoMgr = JpaManager.getPlayoutInfoManager();
                info.setStatus(12);
                playoutInfoMgr.save(info);
                continue;
            }
            uniqueEntries.add(uniqueKey);
            if (!mapRoomInfos.containsKey(info.getRooms())) {
                mapRoomInfos.put(info.getRooms(), new ArrayList());
            }
            ((List)mapRoomInfos.get(info.getRooms())).add(info);
        }
        if (cloneCounter > 42L) {
            LOG.warn("Reached maximum number of playouts(42),exceed " + (cloneCounter - 42L) + " item is not played out!");
        }
        JSONArray roomItems = new JSONArray();
        for (String room : mapRoomInfos.keySet()) {
            List<JSONObject> jsRoomItems = mGatewayManager.generateRoomItem(room, (List)mapRoomInfos.get(room));
            for (JSONObject jsRoomItem : jsRoomItems) {
                if (jsRoomItem == null) continue;
                roomItems.put(jsRoomItem);
            }
        }
        return roomItems;
    }

    public JSONArray getStreamItems() {
        JSONArray streamItems = new JSONArray();
        List<PlayoutInfo> streamInfos = JpaManager.getPlayoutInfoManager().findPlayoutInfosIncludeTypes(PLAYOUT_AV_STREAM, PLAYOUT_LIVE_STREAM);
        for (PlayoutInfo info : streamInfos) {
            if (info.getType().equalsIgnoreCase(PLAYOUT_AV_STREAM)) {
                streamItems.put(mGatewayManager.getStreamJson(info.getName()));
                continue;
            }
            JSONObject jsLive = mGatewayManager.getLiveStreamJson(info);
            if (jsLive == null) continue;
            streamItems.put(jsLive);
        }
        return streamItems;
    }

    public static void checkRFOutput() throws BaseHttpServlet.MessageException {
        LastRFConfig lastRFConfig = LastRFConfig.loadLastConfig();
        String output = lastRFConfig.outputConfig.output;
        if (output.equalsIgnoreCase("RF") && !mGatewayManager.isDektecCardInstalled()) {
            throw new BaseHttpServlet.MessageException("dektecfail");
        }
    }

    public void startPlayout() throws IOException {
        mGatewayManager.setPlayoutWaiting(false);
        mGatewayManager.resetStatus();
        PlayoutUtils.cleanGeneratedFiles();
        mGatewayManager.setState(GatewayManager.GatewayState.STARTING);
        this.setPlayoutStopped(false);
        if (PlayoutUtils.isOldGatewayPlayoutExist()) {
            LOG.info("using old gateway playout");
            mGatewayManager.setUsingOldGateway(true);
            List<PlayoutInfo> infos = JpaManager.getPlayoutInfoManager().loadAll();
            PlayoutUtils.processPlayoutWithOldGatway(infos);
        } else {
            mGatewayManager.setUsingOldGateway(false);
            JSONArray cloneItems = this.getCloneItems();
            JSONArray streamItems = this.getStreamItems();
            boolean success = mGatewayManager.generatePlayout(cloneItems, streamItems);
            if (!success) {
                LOG.error("generate playout failed");
            }
        }
        CmndMetricsTask.loadRFData();
    }

    public static boolean streamPlayoutExisted(String fileName) {
        List<PlayoutInfo> infos = JpaManager.getPlayoutInfoManager().findPlayoutInfoByTypeAndName(PLAYOUT_AV_STREAM, fileName);
        return !infos.isEmpty();
    }

    public static void addLiveStream(String localNic, String srcAddress, String port) throws BaseHttpServlet.MessageException {
        if (PlayoutUtils.isOldGatewayPlayoutExist()) {
            throw new BaseHttpServlet.MessageException("playout using old gateway existed, please remove that first");
        }
        PlayoutInfo playoutInfo = new PlayoutInfo();
        playoutInfo.setName(srcAddress + ":" + port);
        playoutInfo.setPlatform("All");
        playoutInfo.setRooms("All");
        playoutInfo.setSchedule("");
        playoutInfo.setType(PLAYOUT_LIVE_STREAM);
        playoutInfo.setVersion("");
        playoutInfo.setConnectId(-1);
        playoutInfo.setStatus(0);
        playoutInfo.setSource(localNic);
        JpaManager.getPlayoutInfoManager().save(playoutInfo);
        PlayoutUtils.getInstance().onPlayoutChanged();
    }

    public static void addStreamToPlayout(String fileName) throws BaseHttpServlet.MessageException {
        PlayoutInfoManager playoutInfoMgr = JpaManager.getPlayoutInfoManager();
        List<PlayoutInfo> infoList = playoutInfoMgr.findPlayoutInfoByType(PLAYOUT_AV_STREAM);
        if (infoList.size() >= 8) {
            throw new BaseHttpServlet.MessageException(String.format(Locale.ENGLISH, "Only %d AV Stream support at the same time, please remove some streams first", 8));
        }
        PlayoutInfo playoutInfo = new PlayoutInfo();
        playoutInfo.setName(fileName);
        playoutInfo.setPlatform("All");
        playoutInfo.setRooms("All");
        playoutInfo.setSchedule("");
        playoutInfo.setType(PLAYOUT_AV_STREAM);
        playoutInfo.setVersion("");
        playoutInfo.setConnectId(-1);
        playoutInfo.setStatus(0);
        playoutInfoMgr.save(playoutInfo);
        LOG.info("add playout,id:{},type:{},room:{}", playoutInfo.getPlayoutId(), playoutInfo.getType(), playoutInfo.getRooms());
        PlayoutUtils.getInstance().onPlayoutChanged();
    }

    public static void stopPlayout() {
        LOG.info("stop playout");
        GatewayManager.getInstance().stopPlayout();
        PlayoutUtils.getInstance().setPlayoutStopped(true);
    }

    private static int getAssignedId(CommonConstants.CloneItemType cloneType, int cloneId) {
        Setting setting = JpaManager.getSettingManager().loadByKey(cloneId);
        return CloneItemUtils.getAssignedId(cloneType, setting);
    }

    public static boolean checkPlayoutCompatible(String platform, String roomStr) {
        boolean newGateway = PlatformUtils.isUsingNewGateway(platform);
        if (newGateway) {
            return !PlayoutUtils.isOldGatewayPlayoutExist();
        }
        List<PlayoutInfo> playoutInfos = JpaManager.getPlayoutInfoManager().loadAll();
        for (PlayoutInfo info : playoutInfos) {
            if (info.getRooms().equalsIgnoreCase(roomStr) && PlatformUtils.isCompatiblePlatform(info.getPlatform(), platform)) continue;
            return false;
        }
        return true;
    }

    public static boolean isOldGatewayPlayoutExist() {
        List<PlayoutInfo> playoutInfos = JpaManager.getPlayoutInfoManager().loadAll();
        for (PlayoutInfo playoutInfo : playoutInfos) {
            if (PlatformUtils.isUsingNewGateway(playoutInfo.getPlatform())) continue;
            return true;
        }
        return false;
    }

    private static void addCloneItemsForOldPlatform(CommonConstants.CloneItemType playoutType, int id, String roomStrs, String source) throws BaseHttpServlet.MessageException {
        if (playoutType == CommonConstants.CloneItemType.Clone) {
            Setting setting = JpaManager.getSettingManager().loadByKey(id);
            if (setting == null) {
                throw new BaseHttpServlet.MessageException("setting[" + id + "] is missing.");
            }
            List<CommonConstants.CloneItemType> cloneItems = PlatformUtils.getSupportCloneItems(setting.getPlatform());
            for (CommonConstants.CloneItemType cloneItem : cloneItems) {
                if (CloneItemUtils.getAssignedId(cloneItem, setting) <= 0 && null == CloneItemUtils.getCloneItemPath(setting, cloneItem.name())) continue;
                String versionId = "";
                Config conf = null;
                try {
                    JAXBContext context = JAXBContext.newInstance(Config.class);
                    Unmarshaller unmarshaller = context.createUnmarshaller();
                    File file = new File(CommonConstants.CONFIG_FILE);
                    conf = (Config)unmarshaller.unmarshal(file);
                    versionId = conf.getSsbIdentifier();
                }
                catch (Exception e) {
                    LOG.error(e.getMessage());
                }
                PlayoutInfoManager playoutInfoMgr = JpaManager.getPlayoutInfoManager();
                PlayoutInfo playoutInfo = new PlayoutInfo();
                playoutInfo.setName(setting.getName());
                playoutInfo.setPlatform(PlatformUtils.getPlatformName(setting.getPlatform()));
                playoutInfo.setRooms(roomStrs);
                playoutInfo.setSchedule("");
                playoutInfo.setIsclone("True");
                playoutInfo.setType(cloneItem.name());
                playoutInfo.setVersion(versionId);
                playoutInfo.setConnectId(id);
                playoutInfo.setStatus(0);
                playoutInfo.setSource(source);
                playoutInfoMgr.save(playoutInfo);
                LOG.info("add playout,id:{},type:{},room:{}", playoutInfo.getPlayoutId(), playoutInfo.getType(), playoutInfo.getRooms());
                mGatewayManager.setItemUpdatePooling(true);
            }
        } else {
            CloneItemUtils.CloneItemInfo itemInfo = CloneItemUtils.getCloneItemInfo(playoutType, id);
            PlayoutInfoManager playoutInfoMgr = JpaManager.getPlayoutInfoManager();
            PlayoutInfo playoutInfo = new PlayoutInfo();
            playoutInfo.setName(itemInfo.getName());
            playoutInfo.setPlatform(itemInfo.getPlatform());
            playoutInfo.setRooms(roomStrs);
            playoutInfo.setSchedule("");
            playoutInfo.setIsclone("False");
            playoutInfo.setType(playoutType.name());
            playoutInfo.setVersion(itemInfo.getVersion());
            playoutInfo.setConnectId(id);
            playoutInfo.setStatus(0);
            playoutInfo.setSource(source);
            playoutInfoMgr.save(playoutInfo);
            LOG.info("add playout,id:{},type:{},room:{}", playoutInfo.getPlayoutId(), playoutInfo.getType(), playoutInfo.getRooms());
            mGatewayManager.setItemUpdatePooling(true);
        }
        PlayoutUtils.getInstance().onPlayoutChanged();
    }

    private static void addCloneItems(CommonConstants.CloneItemType playoutType, int id, String roomStrs, String source) {
        new Thread(() -> {
            CloneItemUtils.CloneItemInfo itemInfo = CloneItemUtils.getCloneItemInfo(playoutType, id);
            try (SettingCreator sc = new SettingCreator(itemInfo.getPlatform());){
                File[] subItems;
                sc.processClonePacket(playoutType.name(), id, roomStrs);
                String outputPath = sc.getOutputPath();
                List<String> listDir = PlatformUtils.getSupportCloneItemNames(itemInfo.getPlatform());
                for (File subItem : subItems = new File(outputPath).listFiles()) {
                    PlayoutInfo playoutInfo;
                    String zipFullPath;
                    int assgignId;
                    String itemPath = subItem.getAbsolutePath();
                    if (CloneItemUtils.isCloneDataEmpty(itemPath)) {
                        LOG.info("{} is empty", (Object)subItem.getName());
                        continue;
                    }
                    if (!listDir.contains(subItem.getName())) {
                        LOG.info("{} is not support by {}", (Object)subItem.getName(), (Object)itemInfo.getPlatform());
                        continue;
                    }
                    CloneItemUtils.CloneItemInfo info = itemInfo;
                    CommonConstants.CloneItemType itemType = CloneItemUtils.parseCloneItemType(subItem.getName());
                    boolean assigned = false;
                    if (playoutType == CommonConstants.CloneItemType.Clone && (assgignId = PlayoutUtils.getAssignedId(itemType, id)) > 0) {
                        info = CloneItemUtils.getCloneItemInfo(itemType, assgignId);
                        assigned = true;
                    }
                    if (!assigned) {
                        String version = CloneItemUtils.getIdentifier(itemPath);
                        if (!version.isEmpty()) {
                            info.setVersion(version);
                        }
                        info.setItemType(itemType);
                    }
                    if (new File(zipFullPath = PlayoutUtils.getRFZipFullPath(playoutInfo = PlayoutUtils.savePlayout(info = PlayoutUtils.processDefaultPlatform(info, roomStrs), roomStrs, source))).exists()) continue;
                    ZipCommonUtils.zipFiles(subItem.getAbsolutePath(), zipFullPath);
                }
            }
            catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
            PlayoutUtils.getInstance().onPlayoutChanged();
        }).start();
    }

    private static CloneItemUtils.CloneItemInfo processDefaultPlatform(CloneItemUtils.CloneItemInfo info, String roomIdStr) {
        List<String> platforms = JpaManager.getDevicesManager().getRfDevicePlatformsByRoomId(roomIdStr);
        if (info.isDefaultPlatform() && !platforms.isEmpty() && !platforms.contains(info.getPlatform())) {
            info.setPlatform(platforms.get(0));
        }
        return info;
    }

    public static void addToPlayoutList(String uid, String playType, String roomStrs, String source) throws BaseHttpServlet.MessageException {
        int id = Integer.parseInt(uid);
        String checkResult = RoomCheckUtil.checkOverWrite(playType, roomStrs, uid, true).getConflictResult();
        LOG.info("Playout room check Result:{}", (Object)checkResult);
        CommonConstants.CloneItemType playoutType = CloneItemUtils.parseCloneItemType(playType);
        if (playoutType == CommonConstants.CloneItemType.UnKnownItem) {
            throw new BaseHttpServlet.MessageException("not support cloneType," + playType);
        }
        CloneItemUtils.CloneItemInfo itemInfo = CloneItemUtils.getCloneItemInfo(playoutType, id);
        if (!PlayoutUtils.checkPlayoutCompatible(itemInfo.getPlatform(), roomStrs)) {
            throw new BaseHttpServlet.MessageException(String.format(Locale.ENGLISH, "playtype[%s] is not compatible with current playing item", playType));
        }
        if (PlatformUtils.isAsta2016Up(itemInfo.getPlatform())) {
            if (playoutType == CommonConstants.CloneItemType.Firmware) {
                PlayoutUtils.addFirmware(itemInfo, roomStrs, source);
            } else {
                PlayoutUtils.addCloneItems(playoutType, id, roomStrs, source);
            }
        } else {
            PlayoutUtils.addCloneItemsForOldPlatform(playoutType, id, roomStrs, source);
        }
    }

    private static String getUpgPath(int upgId) {
        UpgSetting upgSetting = JpaManager.getUpgSettingManager().loadByKey(upgId);
        if (upgSetting != null) {
            return CommonConstants.UPLOADED_UPG_LOCATION + upgSetting.getName() + "/Autorun.upg";
        }
        return null;
    }

    private static void addFirmware(CloneItemUtils.CloneItemInfo info, String roomStrs, String source) {
        new Thread(() -> {
            PlayoutInfo playoutInfo = PlayoutUtils.savePlayout(info, roomStrs, source);
            String filePath = PlayoutUtils.getRFZipFullPath(playoutInfo);
            File targetFile = new File(filePath);
            if (!targetFile.exists()) {
                try {
                    String upgPath = PlayoutUtils.getUpgPath(playoutInfo.getConnectId());
                    FileUtils.copyFile(new File(upgPath), targetFile);
                }
                catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
            PlayoutUtils.getInstance().onPlayoutChanged();
        }).start();
    }

    public static String processRoomIdSequence(String roomstr) {
        return new RoomSelection(roomstr).toString();
    }

    public static PlayoutInfo savePlayout(CloneItemUtils.CloneItemInfo itemInfo, String roomStrs, String source) {
        String playoutName = TpvStringUtils.limitStringLength(itemInfo.getName(), 45);
        String platform = PlatformUtils.getPlatformName(itemInfo.getPlatform());
        PlayoutInfoManager playoutInfoMgr = JpaManager.getPlayoutInfoManager();
        PlayoutInfo playoutInfo = new PlayoutInfo();
        playoutInfo.setName(playoutName);
        playoutInfo.setPlatform(platform);
        playoutInfo.setRooms(PlayoutUtils.processRoomIdSequence(roomStrs));
        playoutInfo.setSchedule("");
        playoutInfo.setIsclone(itemInfo.isClone() ? "True" : "False");
        playoutInfo.setType(itemInfo.getItemType().name());
        playoutInfo.setVersion(itemInfo.getVersion());
        playoutInfo.setConnectId(itemInfo.getId());
        playoutInfo.setStatus(0);
        playoutInfo.setSource(source);
        playoutInfo.setFilePath(PlayoutUtils.getRFZipFilePath(itemInfo));
        playoutInfoMgr.save(playoutInfo);
        mGatewayManager.setItemUpdatePooling(true);
        LOG.info("add playout,id:{},type:{},room:{}", playoutInfo.getPlayoutId(), playoutInfo.getType(), playoutInfo.getRooms());
        return playoutInfo;
    }

    public static void addPMSAction(PmsUtils.PmsAction action, String roomId) {
        roomId = String.valueOf(Integer.parseInt(roomId));
        LOG.info("add PMS Action:{},room:{}", (Object)action, (Object)roomId);
        List<PlayoutInfo> playoutInfos = JpaManager.getPlayoutInfoManager().findPlayoutInfoByTypeAndRoomsAndSource(CommonConstants.CloneItemType.PMS.name(), roomId, PLAYOUT_SOURCE_PMS);
        LOG.info("remove playout for PMS:{},room:{},size:{}", new Object[]{action, roomId, playoutInfos.size()});
        playoutInfos.stream().forEach(t -> {
            LOG.info("delete playout, id:{},type:{},room:{}", t.getPlayoutId(), t.getType(), t.getRooms());
            PlayoutUtils.deletePlayout(t.getPlayoutId(), false);
        });
        try {
            PlayoutUtils.addToPlayoutList(String.valueOf(action.ordinal()), CommonConstants.CloneItemType.PMS.name(), roomId, PLAYOUT_SOURCE_PMS);
            CmndMetricsTask.writeRFTVMetricsLog(action, roomId);
        }
        catch (BaseHttpServlet.MessageException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public void schedulePlayout() {
        if (!this.isPlayoutStopped() && mGatewayManager.isPlayoutWaiting()) {
            try {
                if (mGatewayManager.getState() != GatewayManager.GatewayState.STARTING) {
                    this.startPlayout();
                }
            }
            catch (Exception e) {
                mGatewayManager.stopPlayout();
                LOG.error(e.getMessage(), e);
            }
        }
    }

    public void onPlayoutChanged() {
        LOG.info("playout changed");
        mGatewayManager.setItemUpdatePooling(true);
        mGatewayManager.setPlayoutWaiting(true);
        if (this.service == null && !this.playoutStopped) {
            this.initScheduleService();
        }
    }

    private static void doDeletePlayout(int playoutId) {
        LOG.info("delete playout,id:{}", (Object)playoutId);
        PlayoutInfo info = JpaManager.getPlayoutInfoManager().loadByKey(playoutId);
        if (info != null) {
            if (info.getType().equalsIgnoreCase(PLAYOUT_AV_STREAM)) {
                File avStream2;
                File avStream = new File(CommonConstants.UPLOADED_AVSTREAM_LOCATION + info.getName());
                if (avStream.exists()) {
                    FileUtils.deleteQuietly(avStream);
                }
                if ((avStream2 = new File(PlayoutUtils.getRFProfilePath() + info.getName())).exists()) {
                    FileUtils.deleteQuietly(avStream2);
                }
            }
            if (info.getFilePath() != null) {
                PlayoutUtils.deleteGeneratedFiles(info.getFilePath());
            }
            JpaManager.getPlayoutInfoManager().deleteByKey(playoutId);
        }
    }

    public static void deletePlayouts(String ids) {
        String[] idlist;
        for (String idStr : idlist = ids.split(",")) {
            PlayoutUtils.doDeletePlayout(Integer.parseInt(idStr));
        }
        PlayoutUtils.getInstance().onPlayoutChanged();
    }

    public static void deletePlayout(int playoutId, boolean isRePlayout) {
        PlayoutUtils.doDeletePlayout(playoutId);
        if (isRePlayout) {
            PlayoutUtils.getInstance().onPlayoutChanged();
        }
    }

    public static void removeTriggerPlayouts(String roomId) {
        List<PlayoutInfo> infos = JpaManager.getPlayoutInfoManager().findPlayoutInfoByRoomsAndSource(roomId, PLAYOUT_SOURCE_TRIGGER);
        LOG.info("remove trigger playouts,room:{},size:{}", (Object)roomId, (Object)infos.size());
        infos.forEach(t -> PlayoutUtils.deletePlayout(t.getPlayoutId(), false));
    }

    public static String getRFZipFilePath(CloneItemUtils.CloneItemInfo info) {
        String ext = info.getItemType() == CommonConstants.CloneItemType.Firmware ? ".upg" : ".zip";
        return String.format(Locale.ENGLISH, "%s_%d_%d%s", new Object[]{info.getItemType(), info.getId(), info.getLastUpdated().getTime(), ext});
    }

    public static String getRFZipFullPath(PlayoutInfo info) {
        return PlayoutUtils.getRFProfilePath() + info.getFilePath();
    }

    public static String getRFProfilePath() {
        return CommonConstants.PROFILE_RF_DIR;
    }

    private static void deleteGeneratedFiles(String filePath) {
        LOG.info("delete generated files:{}", (Object)filePath);
        if (JpaManager.getPlayoutInfoManager().countByFilePath(filePath) > 1) {
            LOG.info("{} also used by other playouts, keep it", (Object)filePath);
            return;
        }
        FileUtils.deleteQuietly(new File(PlayoutUtils.getRFProfilePath(), filePath));
    }

    private static void cleanGeneratedFiles() {
        File file = new File(CommonConstants.RF_PLAY_BACK_OUTPUT_PATH);
        FileUtils.deleteQuietly(file);
    }

    public static void clearPlayoutList() {
        List<PlayoutInfo> infos = JpaManager.getPlayoutInfoManager().loadAll();
        for (PlayoutInfo info : infos) {
            PlayoutUtils.doDeletePlayout(info.getPlayoutId());
        }
    }

    private static void processPlayoutWithOldGatway(List<PlayoutInfo> infos) throws IOException {
        if (infos == null || infos.isEmpty()) {
            LOG.error("old gateway playout list is empty");
            return;
        }
        ArrayList<Integer> handledCloneIds = new ArrayList<Integer>();
        HashSet<String> exportItems = new HashSet<String>();
        HashMap<String, String> parameters = new HashMap<String, String>();
        String platformId = PlatformUtils.getPlatformId(infos.get(0).getPlatform());
        try (SettingCreator sc = new SettingCreator(platformId, "RF");){
            boolean hasFirmware = false;
            boolean hasClone = false;
            for (PlayoutInfo info : infos) {
                String rooms = info.getRooms();
                parameters.put("roomIdString", rooms);
                if (rooms.equalsIgnoreCase("all")) {
                    parameters.put("upgradeAllRoom", "true");
                }
                if (info.getType().equals(CommonConstants.CloneItemType.Firmware.name())) {
                    UpgSetting upgSetting = JpaManager.getUpgSettingManager().loadByKey(info.getConnectId());
                    PlayoutUtils.handleCopyUpgForOldGateway(upgSetting);
                    hasFirmware = true;
                    parameters.put("vid", upgSetting.getVersion());
                    continue;
                }
                exportItems.add(info.getType());
                try {
                    if (info.getIsclone() != null && info.getIsclone().equalsIgnoreCase("True")) {
                        if (!handledCloneIds.contains(info.getConnectId())) {
                            sc.processClonePacket(CommonConstants.CloneItemType.Clone.name(), info.getConnectId(), info.getRooms());
                            handledCloneIds.add(info.getConnectId());
                        }
                    } else {
                        sc.processClonePacket(info.getType(), info.getConnectId(), info.getRooms());
                    }
                    hasClone = true;
                }
                catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
            sc.removeUnexportItems(exportItems.toArray(new String[0]));
            parameters.put("output", (hasClone ? "C" : "") + (hasFirmware ? "F" : ""));
            String outputPath = sc.getOutputPath();
            IPUpgradeManager.copyFromAssemblyToPlayout(outputPath, platformId);
            PlayoutUtils.lastConfigToParameters(parameters);
            sc.processTSCreation(parameters);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return;
        }
        mGatewayManager.playToGateway(infos);
        CmndMetricsTask.loadRFData();
    }

    private static void handleCopyUpgForOldGateway(UpgSetting upgSetting) {
        String platform = upgSetting.getPlatform();
        String version = upgSetting.getVersion();
        String upgName = upgSetting.getName();
        String versionFolder = null;
        if ("MS2K14".equalsIgnoreCase(platform) || "ES2K14".equalsIgnoreCase(platform) || "MS2K16".equalsIgnoreCase(platform) || "ES2K16".equalsIgnoreCase(platform) || "SS2K16".equalsIgnoreCase(platform)) {
            versionFolder = Utils.getFolder(version);
        } else {
            try {
                Double versionDbl = Double.parseDouble(version) * 1000.0;
                versionFolder = String.valueOf(versionDbl);
                versionFolder = versionFolder.substring(0, versionFolder.indexOf(46));
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
        File srcFile = new File(CommonConstants.UPLOADED_UPG_LOCATION + upgName + "/Autorun.upg");
        if (!srcFile.exists()) {
            LOG.error("UPG File does not seem to exist, please upload new file.");
            return;
        }
        File destDir = PlatformUtils.getUpgDestDir(platform, versionFolder);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        try {
            String destFileName = destDir.getAbsolutePath() + "/autorun.upg";
            FileUtils.copyFile(srcFile, new File(destFileName));
            String targetPath = destDir.getAbsolutePath();
            ZipCommonUtils.zipFiles(destFileName, targetPath + "/oad.upg");
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private static void lastConfigToParameters(Map<String, String> parameters) {
        LastRFConfig lastRFConfig = LastRFConfig.loadLastConfig();
        parameters.put("freq", lastRFConfig.getFrequency());
        parameters.put("mod", lastRFConfig.getModulation());
        parameters.put("outlevel", lastRFConfig.getOutputLevel());
        parameters.put("band", lastRFConfig.getBandwidth());
    }
}

