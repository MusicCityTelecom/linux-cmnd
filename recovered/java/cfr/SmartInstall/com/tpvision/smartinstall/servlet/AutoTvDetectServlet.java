/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.annotation.WebServlet
 */
package com.tpvision.smartinstall.servlet;

import com.google.gson.Gson;
import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.OnlineDevices;
import com.tpvision.smartinstall.dao.mgr.DevicesManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.OnlineDevicesManager;
import com.tpvision.smartinstall.japit.TVDiscoveryManager;
import com.tpvision.smartinstall.util.IPUpgradeManager;
import com.tpvision.smartinstall.util.NetworkInterfaceInfo;
import com.tpvision.smartinstall.util.NetworkUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.TpvStringUtils;
import com.tpvision.smartinstall.util.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(value={"/AutoTvDetectServlet"})
public class AutoTvDetectServlet
extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(AutoTvDetectServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mode = request.getParameter("mode");
        if ("searchTv".equalsIgnoreCase(mode)) {
            this.searchTv(request, response);
        } else if ("getNetWorkSegment".equalsIgnoreCase(mode)) {
            List<NetworkInterfaceInfo> networkInterfaces = NetworkUtils.getNetSegmentJsonArray();
            JSONArray data = new JSONArray(new Gson().toJson(networkInterfaces));
            Utils.renderSuccessJsonData(data, response);
        } else if ("serviceShutDown".equalsIgnoreCase(mode)) {
            Utils.writeToResponse("{\"status\":\"success\"}", "text/json;charset=UTF-8", response);
        } else if ("searchTvSuccess".equalsIgnoreCase(mode)) {
            this.handleFoundedTvs(response);
        } else if ("importTvData".equalsIgnoreCase(mode)) {
            this.importTvData(request, response);
        } else if ("correctServiceUrl".equalsIgnoreCase(mode)) {
            String tvUniqueId = request.getParameter("tvUniqueId");
            this.correctWebserviceUrl(tvUniqueId, response);
        }
    }

    private void searchTv(HttpServletRequest request, HttpServletResponse response) {
        String firstIp = request.getParameter("firstIp");
        String lastIp = request.getParameter("lastIp");
        boolean autoImport = Boolean.parseBoolean(request.getParameter("autoImport"));
        TVDiscoveryManager.DetectTarget detectTarget = TVDiscoveryManager.DetectTarget.valueOf(TpvStringUtils.tryParseInt(request.getParameter("detectTarget"), -1));
        LOG.info("firstIp:{},lastIp:{},detectTarget:{}", new Object[]{firstIp, lastIp, detectTarget});
        boolean searchAll = StringUtils.isNotBlank(lastIp) && !StringUtils.equals(firstIp, lastIp);
        ArrayList<OnlineDevices> detectResult = new ArrayList<OnlineDevices>();
        if (searchAll) {
            detectResult.addAll(TVDiscoveryManager.scanAllTvListByIpSegment(firstIp, lastIp, detectTarget));
        } else {
            OnlineDevices onlineTvInfo = TVDiscoveryManager.findTvInfoBySingleIp(firstIp, detectTarget);
            if (onlineTvInfo != null) {
                detectResult.add(onlineTvInfo);
            }
        }
        OnlineDevicesManager onlineDevicesManager = JpaManager.getOnlineDevicesManager();
        onlineDevicesManager.deleteAll();
        for (OnlineDevices onlineTvInfo : detectResult) {
            this.saveDetectedTvToOnlineDb(onlineTvInfo);
        }
        if (autoImport) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("count", detectResult.size());
            Utils.renderSuccessJsonData(jsonObject, response);
        } else {
            String data = new Gson().toJson(onlineDevicesManager.loadAll());
            Utils.renderSuccessJsonData(new JSONArray(data), response);
        }
    }

    private void saveDetectedTvToOnlineDb(OnlineDevices onlineTvInfo) {
        String tvUniqueId = onlineTvInfo.getTvuniqueid();
        DevicesManager tvManager = JpaManager.getDevicesManager();
        Devices tv = tvManager.loadByKey(tvUniqueId);
        if (null == tv) {
            onlineTvInfo.setId(tvUniqueId);
            onlineTvInfo.setCreateddate(TpvDateUtils.getDateTime());
            LOG.info("device table not exist this tv:{} save to onlinedevices", (Object)tvUniqueId);
            JpaManager.getOnlineDevicesManager().saveOnlineDevices(onlineTvInfo);
        } else {
            LOG.info("device table exists this tv:{} update device data", (Object)tvUniqueId);
            TVDiscoveryManager.saveDetectedTvToDevices(onlineTvInfo, tv, false);
        }
    }

    private void handleFoundedTvs(HttpServletResponse response) {
        List<OnlineDevices> onlineDevices = null;
        OnlineDevicesManager odm = JpaManager.getOnlineDevicesManager();
        onlineDevices = odm.loadAll();
        this.saveOnlineDataToDevicesAndSendTVSetting(onlineDevices);
        JSONObject result = new JSONObject();
        result.put("count", onlineDevices.size());
        Utils.renderSuccessJsonData(result, response);
    }

    private void importTvData(HttpServletRequest request, HttpServletResponse response) {
        String maps = request.getParameter("maps");
        JSONObject jsonObject = new JSONObject(maps);
        Set<String> set = jsonObject.keySet();
        OnlineDevicesManager odm = JpaManager.getOnlineDevicesManager();
        for (String networkIp : set) {
            String tvids = jsonObject.optString(networkIp);
            LOG.info("import tv data ==> [key]={}, [value]={}", (Object)networkIp, (Object)tvids);
            String[] tvArray = tvids.split(",");
            ArrayList<OnlineDevices> onlineDevices = new ArrayList<OnlineDevices>();
            for (String tvId : tvArray) {
                OnlineDevices onlineDevice = odm.findByTvId(tvId);
                if (onlineDevice == null) continue;
                onlineDevices.add(onlineDevice);
            }
            this.saveOnlineDataToDevicesAndSendTVSetting(onlineDevices);
        }
        Utils.writeToResponse("{\"status\":\"success\"}", "text/html;charset=UTF-8", response);
    }

    private void saveOnlineDataToDevicesAndSendTVSetting(List<OnlineDevices> onlineDevicesList) {
        DevicesManager devicesManager = JpaManager.getDevicesManager();
        for (OnlineDevices onlineDevices : onlineDevicesList) {
            Devices tv = devicesManager.loadByKey(onlineDevices.getTvuniqueid());
            Devices devices = TVDiscoveryManager.saveDetectedTvToDevices(onlineDevices, tv, false);
            IPUpgradeManager.sendModifyWebserviceUrlTVSettingsRequestToTV(devices, true);
        }
    }

    private void correctWebserviceUrl(String tvUniqueId, HttpServletResponse response) {
        boolean sendResult;
        Devices devices = JpaManager.getDevicesManager().loadByKey(tvUniqueId);
        if (devices != null && (sendResult = IPUpgradeManager.sendModifyWebserviceUrlTVSettingsRequestToTV(devices, false))) {
            Utils.writeToResponse("{\"status\":\"success\"}", "text/json;charset=UTF-8", response);
        }
        Utils.writeToResponse("{\"status\":\"fail\"}", "text/json;charset=UTF-8", response);
    }
}

