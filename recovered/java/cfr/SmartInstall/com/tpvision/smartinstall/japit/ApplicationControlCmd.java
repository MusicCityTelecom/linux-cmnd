/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.japit;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.japit.JapitCommand;
import com.tpvision.smartinstall.util.JAPITUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class ApplicationControlCmd
extends JapitCommand {
    public ApplicationControlCmd() {
        this.setCmdFun(JapitCommand.CommandFun.ApplicationControl);
        this.setCookie(JAPITUtils.getJapitRandomCookieValue());
    }

    public static String requestActiveApplication(String tvid) throws Exception {
        ApplicationControlCmd cmd = new ApplicationControlCmd();
        cmd.setCmdSvc(JapitCommand.CommandSvc.WebListeningServices);
        cmd.setCmdType(JapitCommand.CommandType.Request);
        cmd.send(tvid);
        JSONArray activeApplications = cmd.getResponseCmdDetail().optJSONArray("ActiveApplications");
        if (activeApplications != null && activeApplications.length() > 0) {
            return activeApplications.getJSONObject(0).optString("ApplicationName");
        }
        return null;
    }

    public static List<String> requestApplicationList(String tvid, String filters) throws Exception {
        Devices tv;
        ApplicationControlCmd cmd = new ApplicationControlCmd();
        cmd.setCmdSvc(JapitCommand.CommandSvc.WebListeningServices);
        cmd.setCmdType(JapitCommand.CommandType.Request);
        JSONObject requestDetails = new JSONObject();
        requestDetails.put("Filter", filters.split(","));
        cmd.setCmdDetail("RequestListOfAvailableApplications", requestDetails);
        cmd.send(tvid);
        ArrayList<String> appList = new ArrayList<String>();
        JSONArray activeApplications = cmd.getResponseCmdDetail().optJSONArray("CurrentAvailableApplicationList");
        if (activeApplications != null && activeApplications.length() > 0) {
            for (int i = 0; i < activeApplications.length(); ++i) {
                JSONObject object = activeApplications.optJSONObject(i);
                appList.add(object.optString("ApplicationName"));
            }
        }
        if (StringUtils.equalsIgnoreCase((tv = JpaManager.getDevicesManager().loadByKey(tvid)).getType(), "2019 MS") || StringUtils.equalsIgnoreCase(tv.getType(), "2019 NAFTA")) {
            ApplicationControlCmd.addNotExists(appList, Arrays.asList("DefaultDashboard", "Googlecast", "TVChannels", "Weather"));
        } else if (StringUtils.equalsIgnoreCase(tv.getType(), "2019 PS")) {
            ApplicationControlCmd.addNotExists(appList, Arrays.asList("DefaultDashboard", "TVChannels"));
        }
        return appList;
    }

    public static void addNotExists(List<String> containList, List<String> mergedList) {
        containList.addAll(mergedList.stream().filter(e -> !containList.contains(e)).collect(Collectors.toList()));
    }

    public static void changeCurrentApplication(String tvid, String applicationName) throws Exception {
        ApplicationControlCmd cmd = new ApplicationControlCmd();
        cmd.setCmdSvc(JapitCommand.CommandSvc.WebListeningServices);
        cmd.setCmdType(JapitCommand.CommandType.Change);
        JSONObject applicationDetails = new JSONObject();
        applicationDetails.put("ApplicationName", applicationName);
        if (applicationName.equals("TVChannels")) {
            applicationDetails.put("ApplicationSubState", "TVChannelAV");
        }
        cmd.setCmdDetail("ApplicationDetails", applicationDetails);
        cmd.setCmdDetail("ApplicationState", "Activate");
        cmd.send(tvid);
    }
}

