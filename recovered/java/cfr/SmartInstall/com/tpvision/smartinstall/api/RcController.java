/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.tomcat.util.buf.StringUtils
 */
package com.tpvision.smartinstall.api;

import com.tpvision.smartinstall.SearchParam;
import com.tpvision.smartinstall.api.ApiErrorCode;
import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.Groups;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.japit.remotecontrol.RemoteControlHelper;
import com.tpvision.smartinstall.servlet.RemoteControlServlet;
import com.tpvision.smartinstall.util.ContentUtils;
import com.tpvision.smartinstall.util.IPUpgradeManager;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.TpvStringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.tomcat.util.buf.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/rc"})
public class RcController {
    private static final Logger logger = LoggerFactory.getLogger(RcController.class);

    @GetMapping(value={"/getAllRooms"})
    public List<Integer> getAllRooms() {
        HashSet<Integer> roomSet = new HashSet<Integer>();
        for (Devices device : JpaManager.getDevicesManager().loadAll()) {
            String roomidString;
            int intRoomid;
            if (!this.isValidRCDevices(device) || (intRoomid = TpvStringUtils.tryParseInt(roomidString = device.getTvroomid(), -1)) == -1) continue;
            roomSet.add(intRoomid);
        }
        ArrayList<Integer> list = new ArrayList<Integer>(roomSet);
        Collections.sort(list);
        return list;
    }

    @PostMapping(value={"/getSettingValue"})
    public Object getSettingValue(String roomNo, String settingName) {
        Object obj = this.getDeviceByRoomNo(roomNo);
        if (obj instanceof ApiErrorCode) {
            return obj;
        }
        Devices device = (Devices)obj;
        String tvId = device.getTvuniqueid();
        try {
            String data = "";
            RemoteControlHelper helper = RemoteControlHelper.getInstance(tvId, null);
            RemoteControlServlet.RemoteControlType settingType = RemoteControlServlet.RemoteControlType.valueOf(settingName);
            switch (settingType) {
                case Application: {
                    data = helper.requestActiveApplication();
                    break;
                }
                case Channel: {
                    data = helper.requestCurrentChannel();
                    break;
                }
                case Mute: {
                    data = helper.requestAudioMute();
                    break;
                }
                case Power: {
                    data = helper.requestPower();
                    break;
                }
                case Source: {
                    data = helper.requestSource();
                    break;
                }
                case Volume: {
                    data = helper.requestAudioVolume();
                    break;
                }
                default: {
                    return ApiErrorCode.RC_SETTING_TYPE_KEY_ERROR;
                }
            }
            return new JSONObject().put("value", data);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ApiErrorCode.RC_FAIL_GET_SETTING_VALUE_FOR_ROOM;
        }
    }

    @GetMapping(value={"/getAllGroups"})
    public Set<String> getAllGroups() {
        HashSet<String> groupSet = new HashSet<String>();
        SearchParam sp = new SearchParam();
        sp.setRowCount(Integer.MAX_VALUE);
        JSONArray data = JpaManager.getGroupsManager().findGroupInfoViewPageBySearchParam(sp).getJSONArray("rows");
        for (int i = 0; i < data.length(); ++i) {
            JSONObject info = data.getJSONObject(i);
            if (!info.optBoolean("supportRemoteControl")) continue;
            groupSet.add(info.getString("name"));
        }
        return groupSet;
    }

    @PostMapping(value={"/getRoomInfo"})
    public Object getSettingsByRoomNo(String roomNo) {
        Object obj = this.getDeviceByRoomNo(roomNo);
        if (obj instanceof ApiErrorCode) {
            return obj;
        }
        Devices device = (Devices)obj;
        String tvId = device.getTvuniqueid();
        try {
            JSONObject resultInfo = new JSONObject();
            RemoteControlHelper helper = RemoteControlHelper.getInstance(tvId, null);
            resultInfo.put("ApplicationList", helper.getValueList(RemoteControlServlet.RemoteControlType.ApplicationList.name()));
            resultInfo.put("SourceList", helper.getValueList(RemoteControlServlet.RemoteControlType.SourceList.name()));
            return resultInfo;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ApiErrorCode.RC_FAIL_GET_ROOMINFO_FOR_ROOMNO;
        }
    }

    @PostMapping(value={"/getSettingsByGroupName"})
    public Object getSettingsByGroupName(String groupName) {
        List<Groups> groups = JpaManager.getGroupsManager().findGroupsByGroupName(groupName);
        if (groups.isEmpty()) {
            return ApiErrorCode.RC_ERROR_GROUP_NAME;
        }
        try {
            JSONObject resultInfo = new JSONObject();
            RemoteControlHelper helper = RemoteControlHelper.getInstance(null, groupName);
            resultInfo.put("ApplicationList", helper.getValueList(RemoteControlServlet.RemoteControlType.ApplicationList.name()));
            resultInfo.put("SourceList", helper.getValueList(RemoteControlServlet.RemoteControlType.SourceList.name()));
            return resultInfo;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ApiErrorCode.RC_FAIL_GET_ROOMINFO_FOR_GROUPN;
        }
    }

    @GetMapping(value={"/getContentList"})
    public JSONArray getContentList() {
        JSONArray result = new JSONArray();
        String contentListStr = ContentUtils.getContentList();
        JSONArray jsonArr = new JSONArray(contentListStr);
        for (int i = 0; i < jsonArr.length(); ++i) {
            JSONObject obj = jsonArr.optJSONObject(i);
            JSONObject simpleObj = new JSONObject();
            simpleObj.put("id", obj.optString("id"));
            simpleObj.put("title", obj.optString("title"));
            result.put(simpleObj);
        }
        return result;
    }

    @PostMapping(value={"/updateContent"})
    public ApiErrorCode updateContent(String roomNo, int contentId) {
        Object obj = this.getDeviceByRoomNo(roomNo);
        if (obj instanceof ApiErrorCode) {
            return (ApiErrorCode)((Object)obj);
        }
        Devices device = (Devices)obj;
        if (!device.getProgress().equalsIgnoreCase("ST")) {
            return ApiErrorCode.RC_FAIL_TO_UPGRADE_CONTENT_AS_TV_NOT_IN_STOP_STATUS;
        }
        String status = IPUpgradeManager.processCloneUpgradeType("Content", String.valueOf(contentId), device.getTvuniqueid(), "", null);
        if (status.contains("fail")) {
            return ApiErrorCode.RC_FAIL_TO_CREATE_CONTENT_CLONE;
        }
        IPUpgradeManager.startUpgrades(device.getTvuniqueid(), "U");
        return ApiErrorCode.SUCCESS_OK;
    }

    @PostMapping(value={"/updateContentByGroupName"})
    public ApiErrorCode updateContentByGroupName(String groupName, int contentId) {
        List<Groups> groups = JpaManager.getGroupsManager().findGroupsByGroupName(groupName);
        if (groups.isEmpty()) {
            return ApiErrorCode.RC_ERROR_GROUP_NAME;
        }
        List updateAbleDeviceIds = groups.stream().map(g -> JpaManager.getDevicesManager().loadByKey(g.getTvid())).filter(e -> this.isValidRCDevices((Devices)e) && !e.getProgress().equalsIgnoreCase("ST")).map(Devices::getId).distinct().collect(Collectors.toList());
        if (updateAbleDeviceIds.isEmpty()) {
            return ApiErrorCode.RC_UPDATE_CONTENT_DEVICES_EMPTY;
        }
        String tvIds = StringUtils.join(updateAbleDeviceIds, (char)',');
        String status = IPUpgradeManager.processCloneUpgradeType("Content", String.valueOf(contentId), tvIds, "", null);
        if (status.contains("fail")) {
            return ApiErrorCode.RC_FAIL_TO_CREATE_CONTENT_CLONE;
        }
        IPUpgradeManager.startUpgrades(tvIds, "U");
        return ApiErrorCode.SUCCESS_OK;
    }

    @PostMapping(value={"/updateSettingByGroupName"})
    public ApiErrorCode updateSettingByGroupName(String groupName, String settingName, String value) {
        List<Groups> groups = JpaManager.getGroupsManager().findGroupsByGroupName(groupName);
        if (groups.isEmpty()) {
            return ApiErrorCode.RC_ERROR_GROUP_NAME;
        }
        try {
            RemoteControlHelper helper = RemoteControlHelper.getInstance(null, groupName);
            helper.changeSetting(settingName, value);
            return ApiErrorCode.SUCCESS_OK;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ApiErrorCode.RC_FAIL_UPDATE_SETTING_FOR_GROUP;
        }
    }

    @PostMapping(value={"/updateSetting"})
    public ApiErrorCode updateSetting(String roomNo, String settingName, String value) {
        Object obj = this.getDeviceByRoomNo(roomNo);
        if (obj instanceof ApiErrorCode) {
            return (ApiErrorCode)((Object)obj);
        }
        Devices device = (Devices)obj;
        String tvId = device.getTvuniqueid();
        try {
            RemoteControlHelper helper = RemoteControlHelper.getInstance(tvId, null);
            helper.changeSetting(settingName, value);
            return ApiErrorCode.SUCCESS_OK;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ApiErrorCode.RC_FAIL_UPDATE_SETTING_FOR_ROOMNO;
        }
    }

    private boolean isValidRCDevices(Devices device) {
        return !device.isRFDevice() && device.isOnline() && PlatformUtils.isSupportRemoteControl(device.getType());
    }

    private Object getDeviceByRoomNo(String roomNo) {
        int intRoomId = TpvStringUtils.tryParseInt(roomNo, -1);
        if (intRoomId == -1) {
            return ApiErrorCode.RC_FIND_ROOMNO_FORMAT_ERROR;
        }
        List devices = JpaManager.getDevicesManager().findDevicesByRoomId(roomNo).stream().filter(this::isValidRCDevices).collect(Collectors.toList());
        if (devices.isEmpty()) {
            return ApiErrorCode.RC_CANT_FIND_DEVICE_FOR_ROOMNO;
        }
        return devices.get(0);
    }
}

