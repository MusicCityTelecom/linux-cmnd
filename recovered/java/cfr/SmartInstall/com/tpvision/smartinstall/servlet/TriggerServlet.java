/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.annotation.WebServlet
 */
package com.tpvision.smartinstall.servlet;

import com.google.gson.Gson;
import com.tpvision.smartinstall.SearchParam;
import com.tpvision.smartinstall.dao.core.TriggerInfo;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.japit.remotecontrol.RemoteControlHelper;
import com.tpvision.smartinstall.servlet.BaseHttpServlet;
import com.tpvision.smartinstall.trigger.TriggerUtils;
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.IPUpgradeManager;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.Utils;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(value={"/trigger"})
public class TriggerServlet
extends BaseHttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(TriggerServlet.class);
    private static final String ID_NAME = "idName";

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String status = null;
        String mode = request.getParameter("mode");
        if (mode == null) {
            String message = "mode parameter not existed";
            LOG.error(message);
            status = this.failedStatus(message);
            this.responseJSON(status, response);
            return;
        }
        switch (mode) {
            case "INDEX": {
                request.getRequestDispatcher("/jsp/tv/trigger_history.jsp").forward(request, response);
                break;
            }
            case "ADD_TRIGGER": {
                status = this.addDefaultTriggerInfo();
                break;
            }
            case "ADD_SUBTRIGGER": {
                status = this.addSubTriggerInfo(request);
                break;
            }
            case "DELETE_TRIGGER": {
                status = this.deleteTriggerInfo(request);
                break;
            }
            case "RENAME_TRIGGER": {
                status = this.renameTriggerInfo(request);
                break;
            }
            case "UPDATE_TRIGGER": {
                status = this.updateTriggerInfo(request);
                break;
            }
            case "GET_TRIGGER_TYPES": {
                status = this.getTriggerTypes();
                break;
            }
            case "GET_TRIGGER_TARGETS": {
                status = this.getTriggerTargets(request);
                break;
            }
            case "GET_GROUP_ID_LIST": {
                status = this.getGroupIdList();
                break;
            }
            case "GET_TRIGGER_HISTORY": {
                status = this.getTriggerHistory(request);
                break;
            }
            case "DELETE_TRIGGER_HISTORY": {
                JpaManager.getTriggerHistoryManager().deleteAll();
                status = "{\"status\":\"success\"}";
                break;
            }
            case "GET_CONTROL_TYPES": {
                status = this.getControlTypes();
                break;
            }
            case "GET_VALUE_LIST": {
                status = this.getValueList(request);
                break;
            }
        }
        this.responseJSON(status, response);
    }

    private String getGroupIdList() {
        try {
            JSONArray groupNames1 = JpaManager.getGuestInfoManager().findAllGroupName();
            return this.successStatus(groupNames1);
        }
        catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            return this.failedStatus(e.getMessage());
        }
    }

    private String getControlTypes() {
        List<String> controlTypes = Arrays.asList("Power", "Source", "Mute", "Volume", "Channel", "Application");
        return this.successStatus(controlTypes);
    }

    private String getValueList(HttpServletRequest request) {
        String tvid = request.getParameter("tvid");
        String targetName = request.getParameter("targetName");
        String settingName = request.getParameter("settingName");
        String triggerType = request.getParameter("triggerType");
        try {
            if (this.isPlatformNameTarget(triggerType)) {
                return this.successStatus(RemoteControlHelper.getValueList(targetName, settingName));
            }
            RemoteControlHelper helper = RemoteControlHelper.getInstance(tvid, targetName);
            return this.successStatus(helper.getValueList(settingName));
        }
        catch (Exception e) {
            LOG.info(e.getMessage(), e);
            return this.failedStatus(e.getMessage());
        }
    }

    private String updateTriggerInfo(HttpServletRequest request) {
        String idstr = request.getParameter("id");
        String key = request.getParameter("key");
        String value = request.getParameter("value");
        try {
            if (key == null) {
                throw new IOException("Key not supported," + key);
            }
            TriggerInfo triggerInfo = JpaManager.getTriggerInfoManager().loadByKey(Integer.parseInt(idstr));
            if (triggerInfo == null) {
                throw new IOException("TriggerInfo not found,id=" + idstr);
            }
            switch (key) {
                case "trigger_type": {
                    if (!value.equalsIgnoreCase(triggerInfo.getTriggerType())) {
                        triggerInfo.setTarget(null);
                        triggerInfo.setTriggerCondition(null);
                        triggerInfo.setCloneType(null);
                        triggerInfo.setCloneId(null);
                        triggerInfo.setDolist(null);
                    }
                    triggerInfo.setTriggerType(value);
                    break;
                }
                case "when": {
                    triggerInfo.setTriggerCondition(value);
                    break;
                }
                case "to": {
                    triggerInfo.setTarget(value);
                    triggerInfo.setCloneType(null);
                    triggerInfo.setCloneId(null);
                    triggerInfo.setDolist(null);
                    break;
                }
                case "clone_type": {
                    triggerInfo.setCloneType(value);
                    triggerInfo.setCloneId(null);
                    break;
                }
                case "clone_id": {
                    triggerInfo.setCloneId(Integer.parseInt(value));
                    break;
                }
                case "active": {
                    this.updateTriggerActive(triggerInfo, value);
                    break;
                }
                case "dolist": {
                    String origDolist = triggerInfo.getDolist();
                    JSONObject updateContent = new JSONObject(value);
                    int idx = Integer.parseInt(updateContent.optString("doIdx"));
                    String type = updateContent.optString("type");
                    String id = updateContent.optString("id");
                    String action = updateContent.optString("do");
                    String idName = updateContent.optString(ID_NAME);
                    JSONArray arryDolist = origDolist == null ? new JSONArray() : new JSONArray(origDolist);
                    JSONObject object = new JSONObject();
                    object.put("type", type);
                    object.put("id", id);
                    object.put(ID_NAME, idName);
                    if (!"".equalsIgnoreCase(action)) {
                        object.put("do", action);
                    }
                    arryDolist.put(idx, object);
                    triggerInfo.setDolist(arryDolist.toString());
                    break;
                }
                default: {
                    throw new IOException("not support trigger parameters,key=" + key);
                }
            }
            triggerInfo.setLastEdit(new Date());
            JpaManager.getTriggerInfoManager().save(triggerInfo);
            JSONArray data = new JSONArray();
            data.put(new Gson().toJson(triggerInfo));
            return this.successStatus(data);
        }
        catch (IOException | NumberFormatException e) {
            LOG.error(e.getMessage());
            return this.failedStatus(e.getMessage());
        }
    }

    private void updateTriggerActive(TriggerInfo info, String value) throws IOException {
        if (TriggerUtils.TriggerActiveState.Yes.name().equalsIgnoreCase(value)) {
            String doList;
            ArrayList<String> fields = new ArrayList<String>();
            if (info.getTriggerType() == null) {
                fields.add("trigger type");
            }
            if (info.getTriggerCondition() == null && info.getTriggerType() != null && !Arrays.asList(TriggerType.New_Device.name(), TriggerType.Manual.name()).contains(info.getTriggerType())) {
                fields.add("trigger when");
            }
            if ((doList = info.getDolist()) == null) {
                fields.add("dolist");
            } else {
                JSONArray arrayList = new JSONArray(doList);
                for (int i = 0; i < arrayList.length(); ++i) {
                    if ("None".equalsIgnoreCase(arrayList.getJSONObject(i).optString("type")) || !"".equalsIgnoreCase(arrayList.getJSONObject(i).optString("id"))) continue;
                    fields.add("id_" + Integer.toString(i));
                }
            }
            if (info.getTriggerType() != null) {
                try {
                    TriggerType.valueOf(info.getTriggerType());
                    if (info.getTarget() == null) {
                        fields.add("trigger target");
                    }
                }
                catch (Exception e) {
                    LOG.error("not a valid trigger type");
                }
            }
            info.setNote(String.join((CharSequence)",", fields));
            if (!fields.isEmpty()) {
                JpaManager.getTriggerInfoManager().save(info);
                throw new IOException("Trigger could not be activated as it is incomplete. Please fill in the required fields.");
            }
            info.setTriggerActive(TriggerUtils.TriggerActiveState.Yes.name());
            return;
        }
        info.setTriggerActive(TriggerUtils.TriggerActiveState.No.name());
    }

    private JSONObject getPlatformJson(String groupName) {
        JSONObject obj = new JSONObject();
        obj.put("name", groupName);
        String platform = RemoteControlHelper.getGroupPlatform(groupName);
        obj.put("supportRemoteControl", PlatformUtils.isSupportRemoteControl(platform));
        return obj;
    }

    private String getTriggerTargets(HttpServletRequest request) {
        JSONArray groupArr = new JSONArray();
        String triggerType = this.optParameter(request, "trigger_type", "");
        if (this.isPlatformNameTarget(triggerType)) {
            String[] platforms;
            for (String platform : platforms = PlatformUtils.getPlatformsSupportIpUpgrade()) {
                groupArr.put(this.getPlatformJson(platform));
            }
        } else {
            List<String> items = JpaManager.getGroupsManager().findAllGroupNames();
            groupArr.put(this.getPlatformJson("All"));
            for (int i = 0; i < items.size(); ++i) {
                groupArr.put(this.getPlatformJson(items.get(i)));
            }
        }
        return this.successStatus(groupArr);
    }

    private boolean isPlatformNameTarget(String triggerType) {
        return Arrays.asList(TriggerType.New_Device.name(), TriggerType.Manual.name()).contains(triggerType);
    }

    private String getTriggerTypes() {
        JSONArray types = new JSONArray();
        for (TriggerType triggerType : TriggerType.values()) {
            JSONObject triggerObj = new JSONObject();
            triggerObj.put("name", triggerType.toString());
            triggerObj.put("value", triggerType.name());
            types.put(triggerObj);
        }
        return this.successStatus(types);
    }

    private String renameTriggerInfo(HttpServletRequest request) {
        String idstr = request.getParameter("id");
        String newName = request.getParameter("name");
        TriggerInfo trigger = JpaManager.getTriggerInfoManager().loadByKey(Integer.parseInt(idstr));
        trigger.setName(newName);
        JpaManager.getTriggerInfoManager().save(trigger);
        return "{\"status\":\"success\"}";
    }

    private String deleteTriggerInfo(HttpServletRequest request) {
        String[] idstr = request.getParameter("id").split("_");
        TriggerInfo trigger = JpaManager.getTriggerInfoManager().loadByKey(Integer.parseInt(idstr[0]));
        String actions = trigger.getDolist();
        JSONObject result = new JSONObject();
        if (actions != null) {
            CommonConstants.CloneItemType type;
            JSONArray actionArray = new JSONArray(actions);
            int arryId = Integer.parseInt(idstr[1]);
            JSONObject actionObject = actionArray.getJSONObject(arryId);
            String cloneType = actionObject.optString("type");
            String cloneId = actionObject.optString("id");
            if (!("".equalsIgnoreCase(cloneType) || "".equalsIgnoreCase(cloneId) || (type = IPUpgradeManager.convertUpgradeTypeToCloneItemType(cloneType)) != CommonConstants.CloneItemType.Clone && type != CommonConstants.CloneItemType.Firmware)) {
                CloneItemUtils.CloneItemInfo cloneItemInfo = CloneItemUtils.getCloneItemInfo(type, Integer.parseInt(cloneId));
                if (!JpaManager.getDevicesManager().hasAssignedDevices(cloneItemInfo)) {
                    LOG.info("clean cached clone info {}", (Object)cloneItemInfo);
                    FileUtils.deleteQuietly(new File(cloneItemInfo.getCachedPath()));
                }
            }
            actionArray.remove(arryId);
            if (actionArray.length() > 0) {
                trigger.setDolist(actionArray.toString());
                JpaManager.getTriggerInfoManager().save(trigger);
                result.put("data", actionArray);
                result.put("status", "success");
                return result.toString();
            }
        }
        JpaManager.getTriggerInfoManager().deleteByKey(Integer.parseInt(idstr[0]));
        return "{\"status\":\"success\"}";
    }

    private String addSubTriggerInfo(HttpServletRequest request) {
        String[] idstr = request.getParameter("id").split("_");
        TriggerInfo trigger = JpaManager.getTriggerInfoManager().loadByKey(Integer.parseInt(idstr[0]));
        String actions = trigger.getDolist();
        JSONObject result = new JSONObject();
        if (actions != null) {
            int length;
            JSONArray actionArray = new JSONArray(actions);
            JSONObject actionObject = new JSONObject();
            actionObject.put("type", "Wait");
            actionObject.put("id", "0");
            actionObject.put(ID_NAME, "0");
            actionObject.put("do", "10");
            int doIdx = Integer.parseInt(idstr[1]);
            for (int i = length = actionArray.length(); i > doIdx + 1; --i) {
                actionArray.put(i, actionArray.getJSONObject(i - 1));
            }
            actionArray.put(doIdx + 1, actionObject);
            trigger.setDolist(actionArray.toString());
            result.put("data", actionArray);
        }
        JpaManager.getTriggerInfoManager().save(trigger);
        result.put("status", "success");
        return result.toString();
    }

    private String addDefaultTriggerInfo() {
        TriggerInfo trigger = new TriggerInfo();
        trigger.setName("Trigger " + TpvDateUtils.formatLocalDate(new Date(), "yyyyMMdd'T'HHmm"));
        trigger.setCreated(new Date());
        trigger.setCreatedBy(Utils.getAuthenticationName());
        trigger.setTriggerActive(TriggerUtils.TriggerActiveState.No.name());
        JpaManager.getTriggerInfoManager().save(trigger);
        return "{\"status\":\"success\"}";
    }

    private String getTriggerHistory(HttpServletRequest request) {
        SearchParam sp = this.buildWhereClause(request, "id,name,target,trigger_time,result");
        JSONObject triggerHistoryList = JpaManager.getTriggerHistoryManager().findTriggerHistoryPageBySearchParam(sp);
        return triggerHistoryList.toString();
    }

    public static enum TriggerType {
        Trigger("Trigger"),
        Time("Time"),
        Day_and_Time("Day and Time"),
        Date_and_Time("Date and Time"),
        PMS_CheckIn_or_CheckOut("PMS Check-In or Check-Out"),
        PMS_Language("PMS Language"),
        PMS_Group_ID("PMS Group ID"),
        New_Device("New Device"),
        Room_Type("Room Type"),
        Manual("Manual");

        private String displayName;

        private TriggerType(String triggerName) {
            this.displayName = triggerName;
        }

        public String getDisplayName() {
            return this.displayName;
        }

        public String toString() {
            return this.displayName;
        }
    }
}

