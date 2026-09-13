/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.annotation.WebServlet
 */
package com.tpvision.smartinstall.servlet;

import com.google.gson.Gson;
import com.tpvision.smartinstall.androidapp.AndroidAppHelper;
import com.tpvision.smartinstall.dao.core.ChannelPackage;
import com.tpvision.smartinstall.dao.core.Schedule;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.mgr.ChannelPackageManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.ScheduleManager;
import com.tpvision.smartinstall.dao.mgr.SettingManager;
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.Utils;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(value={"/schedule"})
public class ScheduleServlet
extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(ScheduleServlet.class);
    private static final String SCHEDULES = "Schedules";
    private static final String PRIORITY = "Priority";

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mode;
        switch (mode = request.getParameter("mode")) {
            case "SCHEDULE_INDEX": {
                this.schdeuleIndex(request, response);
                break;
            }
            case "SCHEDULE_EDIT": {
                break;
            }
            case "SCHEDULE_GET_CONTENT": {
                this.scheduleGetContent(request, response);
                break;
            }
            case "SCHEDULE_GET_ASSIGN_LIST": {
                this.scheduleGetAssignList(request, response);
                break;
            }
            case "SCHEDULE_GET_CHANNEL_LIST": {
                this.scheduleGetChannelList(request, response);
                break;
            }
            case "SCHEDULE_RENAME": {
                this.scheduleRename(request, response);
                break;
            }
            case "SCHEDULE_ASSIGN": {
                this.scheduleAssign(request, response);
                break;
            }
            case "SCHEDULE_COPY": {
                this.scheduleCopy(request, response);
                break;
            }
            case "SCHEDULE_DELETE": {
                this.scheduleDelete(request, response);
                break;
            }
            case "SCHEDULE_DELETE_EDIT": {
                this.scheduleDeleteEdit(request, response);
                break;
            }
            case "SCHEDULE_ADD": {
                this.scheduleAdd(request, response);
                break;
            }
            case "SCHEDULE_ADD_BUTTON": {
                this.scheduleAddButton(request, response);
                break;
            }
            case "SCHEDULE_CONTENT_CHANGE": {
                this.scheduleContentChange(request, response);
                break;
            }
            default: {
                return;
            }
        }
    }

    private void scheduleDeleteEdit(HttpServletRequest request, HttpServletResponse response) {
        String schedulesString;
        JSONArray schedulesArr;
        JSONObject jsonObject;
        String scheduleContent;
        String status = Utils.buildFailReturnJson();
        String scheduleId = request.getParameter("id");
        String priority = request.getParameter("priority");
        ScheduleManager scheMgr = JpaManager.getScheduleManager();
        Schedule schedule = null;
        if (StringUtils.isNotBlank(scheduleId) && null != (schedule = scheMgr.loadByKey(Integer.parseInt(scheduleId))) && !"".equalsIgnoreCase(scheduleContent = schedule.getContent()) && null != scheduleContent && (jsonObject = new JSONObject(scheduleContent)).has(SCHEDULES) && null != (schedulesArr = new JSONArray(schedulesString = jsonObject.get(SCHEDULES).toString()))) {
            StringBuilder content = new StringBuilder();
            int allSchedulesLen = schedulesArr.length();
            int hitScheduleIndex = -1;
            for (int i = 0; i < allSchedulesLen; ++i) {
                JSONObject scheduleObj = (JSONObject)schedulesArr.get(i);
                if (null == scheduleObj || !scheduleObj.has(PRIORITY) || scheduleObj.get(PRIORITY).toString().isEmpty() || null == priority || "".equals(priority) || !scheduleObj.get(PRIORITY).toString().equals(priority)) continue;
                hitScheduleIndex = i;
                break;
            }
            if (hitScheduleIndex > -1) {
                schedulesArr.remove(hitScheduleIndex);
                JSONArray sortScheduleArr = Utils.reSortJSONArray(schedulesArr);
                content.append("{\"Schedules\":");
                content.append(sortScheduleArr.toString());
                content.append("}");
                schedule.setContent(content.toString());
                scheMgr.save(schedule);
                status = "{\"status\":\"success\"}";
            }
        }
        Utils.writeToResponse(status, "text/html;charset=UTF-8", response);
    }

    private void scheduleContentChange(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject;
        String status = Utils.buildFailReturnJson();
        String scheduleId = request.getParameter("id");
        String scheduleContent = request.getParameter("scheduleContent");
        ScheduleManager scheMgr = JpaManager.getScheduleManager();
        Schedule schedule = scheMgr.loadByKey(Integer.parseInt(scheduleId));
        if (null != schedule && null != scheduleContent && !"".equals(scheduleContent) && (jsonObject = new JSONObject(scheduleContent)).has(SCHEDULES) && !"".equals(jsonObject.get(SCHEDULES))) {
            StringBuilder content = new StringBuilder();
            JSONArray schedulesArr = new JSONArray(jsonObject.get(SCHEDULES).toString());
            JSONArray sortSchedulesArr = Utils.reSortJSONArray(schedulesArr);
            content.append("{\"Schedules\":");
            content.append(sortSchedulesArr.toString());
            content.append("}");
            schedule.setContent(content.toString());
            scheMgr.save(schedule);
            status = "{\"status\":\"success\"}";
        }
        try {
            IOUtils.write(status.getBytes(), (OutputStream)response.getOutputStream());
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void scheduleAddButton(HttpServletRequest request, HttpServletResponse response) {
        String platform = request.getParameter("platform");
        ScheduleManager scheMgr = JpaManager.getScheduleManager();
        Schedule schedule = new Schedule();
        schedule.setName("Schedule_" + TpvDateUtils.getCloneDateTimeName());
        schedule.setPlatform(platform);
        schedule.setSchedule(null);
        schedule.setContent(null);
        scheMgr.save(schedule);
        String status = "{\"status\":\"success\"}";
        try {
            IOUtils.write(status.getBytes(), (OutputStream)response.getOutputStream());
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void scheduleAdd(HttpServletRequest request, HttpServletResponse response) {
        String status = Utils.buildFailReturnJson();
        String scheduleId = request.getParameter("id");
        String addSchedule = request.getParameter("addSchedule");
        ScheduleManager scheMgr = JpaManager.getScheduleManager();
        Schedule schedule = null;
        JSONArray addJsonArr = null;
        if (!"".equals(addSchedule) && null != addSchedule) {
            addJsonArr = new JSONArray(addSchedule);
        }
        if (null != (schedule = scheMgr.loadByKey(Integer.parseInt(scheduleId)))) {
            StringBuilder content = new StringBuilder();
            String scheduleContent = schedule.getContent();
            if (!"".equalsIgnoreCase(scheduleContent) && null != scheduleContent) {
                JSONObject jsonObject = new JSONObject(scheduleContent);
                if (jsonObject.has(SCHEDULES)) {
                    String schedulesString = jsonObject.get(SCHEDULES).toString();
                    JSONArray schedulesArr = new JSONArray(schedulesString);
                    if (null != addJsonArr) {
                        for (int i = 0; i < addJsonArr.length(); ++i) {
                            schedulesArr.put(addJsonArr.get(i));
                        }
                        content.append("{\"Schedules\":");
                        content.append(schedulesArr.toString());
                        content.append("}");
                        schedule.setContent(content.toString());
                        scheMgr.save(schedule);
                        status = "{\"status\":\"success\"}";
                    }
                } else if (null != addJsonArr) {
                    content.append("{\"Schedules\":");
                    content.append(addJsonArr.toString());
                    content.append("}");
                    schedule.setContent(content.toString());
                    scheMgr.save(schedule);
                    status = "{\"status\":\"success\"}";
                }
            } else if (null != addJsonArr) {
                content.append("{\"Schedules\":");
                content.append(addJsonArr.toString());
                content.append("}");
                schedule.setContent(content.toString());
                scheMgr.save(schedule);
                status = "{\"status\":\"success\"}";
            }
        }
        try {
            IOUtils.write(status.getBytes(), (OutputStream)response.getOutputStream());
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void scheduleAssign(HttpServletRequest request, HttpServletResponse response) {
        String status = "{\"status\":\"success\"}";
        String cloneIds = request.getParameter("clone_ids");
        String[] cloneIdsArr = cloneIds.split(",");
        String scheduleId = request.getParameter("schedule_id");
        SettingManager settingManager = JpaManager.getSettingManager();
        if ("None".equalsIgnoreCase(scheduleId)) {
            scheduleId = String.valueOf(-1);
            status = "{\"status\":\"setNone\"}";
        }
        for (int i = 0; i < cloneIdsArr.length; ++i) {
            Setting setting;
            if ("null".equals(cloneIdsArr[i]) || null == (setting = settingManager.loadByKey(Integer.parseInt(cloneIdsArr[i])))) continue;
            setting.setScheduleId(Integer.parseInt(scheduleId));
            setting.setLastUpdatedDate(new Date());
            settingManager.save(setting);
        }
        try {
            IOUtils.write(status.getBytes(), (OutputStream)response.getOutputStream());
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void scheduleGetAssignList(HttpServletRequest request, HttpServletResponse response) {
        String platform = request.getParameter("platform");
        List<Schedule> scheduleList = null;
        scheduleList = StringUtils.isEmpty(platform) ? JpaManager.getScheduleManager().loadAll() : JpaManager.getScheduleManager().findSchedulesByPlatforms(platform);
        try {
            IOUtils.write(new Gson().toJson(scheduleList).getBytes(), (OutputStream)response.getOutputStream());
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void scheduleGetChannelList(HttpServletRequest request, HttpServletResponse response) {
        int channelPackageId;
        SettingManager setMgr;
        Setting setting;
        String channelStr = "";
        String cloneId = request.getParameter("id");
        if (null != cloneId && null != (setting = (setMgr = JpaManager.getSettingManager()).loadByKey(Integer.valueOf(cloneId))) && (channelPackageId = setting.getChannelPackageId()) > 0) {
            ChannelPackageManager channelPackMgr = JpaManager.getChannelPackageManager();
            ChannelPackage channelPackage = null;
            channelPackage = channelPackMgr.loadByKey(channelPackageId);
            if (null != channelPackage) {
                channelStr = channelPackage.getValue();
            }
        }
        Utils.writeToResponse(channelStr, "text/html;charset=UTF-8", response);
    }

    private void scheduleDelete(HttpServletRequest request, HttpServletResponse response) {
        String status = Utils.buildFailReturnJson();
        String id = request.getParameter("id");
        ScheduleManager scheMgr = JpaManager.getScheduleManager();
        if (StringUtils.isNotBlank(id)) {
            scheMgr.deleteByKey(Integer.parseInt(id));
            status = "{\"status\":\"success\"}";
        }
        Utils.writeToResponse(status, "text/html;charset=UTF-8", response);
    }

    private void scheduleCopy(HttpServletRequest request, HttpServletResponse response) {
        String status = Utils.buildFailReturnJson();
        String id = request.getParameter("id");
        ScheduleManager scheMgr = JpaManager.getScheduleManager();
        Schedule schedule = null;
        Schedule newSchedule = new Schedule();
        if (StringUtils.isNotBlank(id) && null != (schedule = scheMgr.loadByKey(Integer.parseInt(id)))) {
            List<String> currentSchedulNames = scheMgr.loadAll().stream().map(Schedule::getName).map(String::trim).collect(Collectors.toList());
            String copyName = CloneItemUtils.getUniqueCloneName(currentSchedulNames, "Copy of " + schedule.getName());
            newSchedule.setName(copyName);
            newSchedule.setPlatform(schedule.getPlatform());
            newSchedule.setSchedule(schedule.getSchedule());
            newSchedule.setContent(schedule.getContent());
            newSchedule.setLastEdit(schedule.getLastEdit());
            scheMgr.save(newSchedule);
            status = "{\"status\":\"success\"}";
        }
        Utils.writeToResponse(status, "text/html;charset=UTF-8", response);
    }

    private void scheduleRename(HttpServletRequest request, HttpServletResponse response) {
        String status = Utils.buildFailReturnJson();
        String newName = request.getParameter("newName");
        String id = request.getParameter("id");
        int scheduleId = Integer.parseInt(id);
        ScheduleManager scheMgr = JpaManager.getScheduleManager();
        Schedule schedule = scheMgr.loadByKey(scheduleId);
        if (null != schedule) {
            List<Schedule> schedules = scheMgr.findByName(newName);
            if (schedules.isEmpty() || schedules.size() == 1 && schedules.get(0).getId() == scheduleId) {
                schedule.setName(newName);
                scheMgr.save(schedule);
                status = "{\"status\":\"success\"}";
            } else {
                status = Utils.buildFailReturnJson("schedule name already exist");
            }
        }
        Utils.writeToResponse(status, "text/html;charset=UTF-8", response);
    }

    private void scheduleGetContent(HttpServletRequest request, HttpServletResponse response) {
        ScheduleManager scheMgr;
        Schedule schedule;
        String data = "";
        String id = request.getParameter("id");
        if (StringUtils.isNotBlank(id) && null != (schedule = (scheMgr = JpaManager.getScheduleManager()).loadByKey(Integer.parseInt(id)))) {
            data = schedule.getContent();
        }
        Utils.writeToResponse(data, "text/html;charset=UTF-8", response);
    }

    private void schdeuleIndex(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        ScheduleManager scheMgr = JpaManager.getScheduleManager();
        Schedule schedule = scheMgr.loadByKey(Integer.parseInt(id));
        String scheduleRef = null;
        String name = schedule.getName();
        String platform = schedule.getPlatform();
        String content = schedule.getContent();
        String scheduleS = schedule.getSchedule();
        String lastEdit = schedule.getLastEdit();
        scheduleRef = "/schedule.jsp?name=" + name + "&platform=" + platform + "&id=" + id + "&content=" + content + "&schedule=" + scheduleS + "&lastEdit=" + lastEdit;
        request.getSession().setAttribute("scheduleId", id);
        List<String> defaultDropList = Arrays.asList("None", "YouTube", "Play Movies & TV", "Play Music", "Play Games", "Play Store");
        List<Object> cloneAppList = new ArrayList();
        String cloneId = request.getParameter("cloneId");
        if (StringUtils.isNumeric(cloneId) && Integer.parseInt(cloneId) > 0) {
            try {
                AndroidAppHelper helper = AndroidAppHelper.getHelper(Integer.parseInt(cloneId));
                cloneAppList = helper.getAppNames(null, false);
            }
            catch (Exception helper) {
                // empty catch block
            }
        }
        List<String> mergetDropList = Stream.concat(defaultDropList.stream(), cloneAppList.stream()).distinct().collect(Collectors.toList());
        request.setAttribute("supportApps", StringUtils.join((Object[])mergetDropList.toArray(new String[0]), ","));
        try {
            request.getRequestDispatcher(scheduleRef).forward(request, response);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}

