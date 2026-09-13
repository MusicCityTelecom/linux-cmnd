/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.roomcheck;

import com.tpvision.smartinstall.core.SettingCreator;
import com.tpvision.smartinstall.dao.core.PlayoutInfo;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.PlayoutInfoManager;
import com.tpvision.smartinstall.gateway.PlayoutUtils;
import com.tpvision.smartinstall.roomcheck.RoomCheckResult;
import com.tpvision.smartinstall.roomcheck.RoomSelection;
import com.tpvision.smartinstall.roomcheck.RoomSelectionComparator;
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.PlatformUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoomCheckUtil {
    private static final Logger LOG = LoggerFactory.getLogger(RoomCheckUtil.class);
    private List<Integer> cloneItemListConflict = new ArrayList<Integer>();
    private RoomCheckResult roomCheckResult = new RoomCheckResult();

    private RoomCheckUtil() {
    }

    public static RoomCheckResult checkOverWrite(String playType, String roomStrs, String uid, boolean bUpdate) {
        RoomCheckUtil roomCheckUtil = new RoomCheckUtil();
        roomCheckUtil.roomCheckResult.setCurrentAllPlayoutInfoList(JpaManager.getPlayoutInfoManager().findPlayoutInfosExculudeTypes("AV Stream", "Live Stream"));
        String conflictResult = roomCheckUtil.doCheckOverWrite(playType, roomStrs, uid, bUpdate);
        roomCheckUtil.roomCheckResult.setConflictResult(conflictResult);
        return roomCheckUtil.roomCheckResult;
    }

    public static boolean checkRoomOverWrite(String newCloneItemRoomSelectionString, String existingCloneItemRoomSelectionString, PlayoutInfo info) {
        return new RoomCheckUtil().doCheckRoomOverWrite(newCloneItemRoomSelectionString, existingCloneItemRoomSelectionString, info, false);
    }

    private void checkCloneItems(String playType, String roomStrs, boolean bUpdate) {
        PlayoutInfoManager pim = JpaManager.getPlayoutInfoManager();
        PlayoutInfo[] playoutInfo = pim.findPlayoutInfoByType(playType).toArray(new PlayoutInfo[0]);
        if (playoutInfo == null || playoutInfo.length == 0) {
            return;
        }
        for (int i = 0; i < playoutInfo.length; ++i) {
            PlayoutInfo info = playoutInfo[i];
            if (!this.doCheckRoomOverWrite(roomStrs, playoutInfo[i].getRooms(), info, bUpdate)) continue;
            this.cloneItemListConflict.add(info.getPlayoutId());
        }
    }

    private List<String> getCheckCloneTypes(CloneItemUtils.CloneItemInfo itemInfo, CommonConstants.CloneItemType playoutType, int id, String roomStrs) {
        ArrayList<String> checkRequiredTypeList = new ArrayList<String>();
        try (SettingCreator sc = new SettingCreator(itemInfo.getPlatform());){
            File[] subItems;
            sc.processClonePacket(playoutType.name(), id, roomStrs);
            String outputPath = sc.getOutputPath();
            List<String> listDir = PlatformUtils.getSupportCloneItemNames(itemInfo.getPlatform());
            for (File subItem : subItems = new File(outputPath).listFiles()) {
                String itemPath = subItem.getAbsolutePath();
                if (CloneItemUtils.isCloneDataEmpty(itemPath)) {
                    LOG.info("{} is empty", (Object)subItem.getName());
                    continue;
                }
                if (!listDir.contains(subItem.getName())) {
                    LOG.info("{} is not support by {}", (Object)subItem.getName(), (Object)itemInfo.getPlatform());
                    continue;
                }
                CommonConstants.CloneItemType itemType = CloneItemUtils.parseCloneItemType(subItem.getName());
                if (itemType == CommonConstants.CloneItemType.UnKnownItem) continue;
                checkRequiredTypeList.add(itemType.name());
            }
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return checkRequiredTypeList;
    }

    private String getConflictResult() {
        StringBuilder result = new StringBuilder();
        if (this.cloneItemListConflict.isEmpty()) {
            return "";
        }
        result.append("This new playout will overwrite the configuration of playout ");
        for (int i = 0; i < this.cloneItemListConflict.size(); ++i) {
            result.append(this.cloneItemListConflict.get(i));
            if (i == this.cloneItemListConflict.size() - 1) continue;
            result.append(",");
        }
        result.append(" for the selected rooms. Do you wish to proceed?");
        return result.toString();
    }

    private String doCheckOverWrite(String playType, String roomStrs, String uid, boolean bUpdate) {
        LOG.info("playout checkOverWrite, type:{},room:{}", (Object)playType, (Object)roomStrs);
        this.cloneItemListConflict.clear();
        List<Object> playTypes = new ArrayList<String>();
        CommonConstants.CloneItemType playoutType = CloneItemUtils.parseCloneItemType(playType);
        CloneItemUtils.CloneItemInfo itemInfo = CloneItemUtils.getCloneItemInfo(playoutType, Integer.parseInt(uid));
        if (PlatformUtils.isAsta2016Up(itemInfo.getPlatform())) {
            if (playoutType == CommonConstants.CloneItemType.Firmware) {
                playTypes.add(playoutType.name());
            } else {
                playTypes = this.getCheckCloneTypes(itemInfo, playoutType, Integer.parseInt(uid), roomStrs);
            }
        } else if (playoutType == CommonConstants.CloneItemType.Clone) {
            Setting setting = JpaManager.getSettingManager().loadByKey(Integer.parseInt(uid));
            List<CommonConstants.CloneItemType> list = PlatformUtils.getSupportCloneItems(setting.getPlatform());
            for (CommonConstants.CloneItemType cloneItem : list) {
                if (CloneItemUtils.getAssignedId(cloneItem, setting) <= 0 && null == CloneItemUtils.getCloneItemPath(setting, cloneItem.name())) continue;
                playTypes.add(cloneItem.name());
            }
        } else {
            playTypes.add(playoutType.name());
        }
        this.roomCheckResult.setWillAddCount(playTypes.size());
        for (String string : playTypes) {
            this.checkCloneItems(string, roomStrs, bUpdate);
        }
        return this.getConflictResult();
    }

    private void handleRoomPartial(String newRoom, PlayoutInfo info, boolean bUpdate) {
        if (info != null) {
            this.roomCheckResult.getPartialOverlapUpdateRequiredPlayoutInfoMap().put(info, newRoom);
            if (bUpdate) {
                LOG.info("handleRoomPartial id:{},type:{},room:{}", info.getPlayoutId(), info.getType(), info.getRooms());
                info.setRooms(newRoom);
                JpaManager.getPlayoutInfoManager().save(info);
            }
        }
    }

    private void handleRoomFull(PlayoutInfo info, boolean bUpdate) {
        if (info != null) {
            this.roomCheckResult.getFullOverlapRemoveRequiredPlayoutInfoList().add(info);
            if (bUpdate) {
                LOG.info("delete playout,id:{},type:{},room:{}", info.getPlayoutId(), info.getType(), info.getRooms());
                PlayoutUtils.deletePlayout(info.getPlayoutId(), false);
            }
        }
    }

    public boolean doCheckRoomOverWrite(String newCloneItemRoomSelectionString, String existingCloneItemRoomSelectionString, PlayoutInfo info, boolean bUpdate) {
        boolean bOverWrite = false;
        if (null == newCloneItemRoomSelectionString || newCloneItemRoomSelectionString.length() == 0) {
            LOG.info("newCloneItemRoomSelectionString is null");
            return false;
        }
        if (null == existingCloneItemRoomSelectionString) {
            LOG.info("existingCloneItemRoomSelectionString is null");
            return false;
        }
        if (existingCloneItemRoomSelectionString.length() == 0) {
            LOG.info("No overwrites detected");
            return false;
        }
        if (existingCloneItemRoomSelectionString.equalsIgnoreCase("All") && !newCloneItemRoomSelectionString.equalsIgnoreCase("All")) {
            LOG.info("No overwrites detected");
            return false;
        }
        RoomSelection roomSelectionNewCloneItem = new RoomSelection(newCloneItemRoomSelectionString);
        RoomSelection roomSelectionExistingCloneItem = new RoomSelection(existingCloneItemRoomSelectionString);
        RoomSelectionComparator.RoomSelectionOverlap overlapRate = RoomSelectionComparator.getRoomSelectionOverlapRate(roomSelectionNewCloneItem, roomSelectionExistingCloneItem);
        RoomSelection updatedRoomSelectionExistingCloneItem = RoomSelectionComparator.getRelevantExistingRoomSelection(roomSelectionNewCloneItem, roomSelectionExistingCloneItem);
        String msg = null;
        switch (overlapRate) {
            case NONE: {
                msg = "overlapRate is NONE";
                break;
            }
            case PARTIAL: {
                msg = "overlapRate is PARTIAL";
                this.handleRoomPartial(updatedRoomSelectionExistingCloneItem.toString(), info, bUpdate);
                bOverWrite = true;
                break;
            }
            case FULL: {
                msg = "overlapRate is FULL";
                this.handleRoomFull(info, bUpdate);
                bOverWrite = true;
                break;
            }
            default: {
                LOG.info("Unknown enum value:{} ", (Object)overlapRate);
            }
        }
        if (bOverWrite) {
            LOG.info("newCloneItemRoomSelectionString={}  existingCloneItemRoomSelectionString={},result:{}", newCloneItemRoomSelectionString, existingCloneItemRoomSelectionString, msg);
        }
        return bOverWrite;
    }
}

