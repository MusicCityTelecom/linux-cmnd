/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.servlet;

import com.google.gson.Gson;
import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.PlayoutInfo;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.PlayoutInfoManager;
import com.tpvision.smartinstall.gateway.GatewayManager;
import com.tpvision.smartinstall.gateway.PlayoutUtils;
import com.tpvision.smartinstall.gateway.ResponsePlayInfo;
import com.tpvision.smartinstall.roomcheck.RoomCheckUtil;
import com.tpvision.smartinstall.servlet.BaseHttpServlet;
import com.tpvision.smartinstall.servlet.LastRFConfig;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.IPUpgradeManager;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.TpvFileUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet6Address;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunRFServlet
extends BaseHttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(RunRFServlet.class);
    private static final GatewayManager mGatewayManager = GatewayManager.getInstance();

    @Override
    public void init() throws ServletException {
        super.init();
        LastRFConfig lastRFConfig = LastRFConfig.loadLastConfig();
        if (!lastRFConfig.gatewayStopped) {
            try {
                PlayoutUtils.triggerStartPlayout();
            }
            catch (BaseHttpServlet.MessageException e) {
                LOG.error(e.getMessage());
            }
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mode = request.getParameter("mode");
        String status = this.successStatus();
        switch (mode) {
            case "getAVServices": {
                status = this.getAVServices();
                break;
            }
            case "getRFOutputPath": {
                status = this.getRFOutputPath();
                break;
            }
            case "getMGateIntefaces": {
                status = this.getMGateInterfaces();
                break;
            }
            case "getMGateVersion": {
                status = this.getMGateVersion();
                break;
            }
            case "upload": {
                status = this.uploadAVStream(request);
                break;
            }
            case "playoutAVStream": {
                status = this.playoutAVStream();
                break;
            }
            case "CheckPlayoutList": {
                status = this.checkPlayoutList(request);
                break;
            }
            case "AddToPlayoutList": {
                String uid = request.getParameter("uid");
                if (StringUtils.isEmpty(uid) && StringUtils.isEmpty(request.getParameter("groupName"))) break;
                String playType = request.getParameter("playType");
                String roomStrs = request.getParameter("roomStrs");
                RoomCheckUtil.checkOverWrite(playType, roomStrs, uid, true);
                status = this.handleAddToPlayoutList(request);
                break;
            }
            case "deletePlayouList": {
                status = this.deletePlayouts(request);
                break;
            }
            case "START": {
                status = this.processIPUpgrade(request);
                break;
            }
            case "STOP": {
                PlayoutUtils.stopPlayout();
                break;
            }
            case "getRFPlayoutStatus": {
                status = this.getRFPlayoutStatus(request);
                break;
            }
            case "UPDATE_LAST_CONFIG": {
                status = this.updateLastConfig(request);
                break;
            }
            case "SwitchMGateState": {
                status = this.switchMGateState(request);
                break;
            }
            case "GetLocalInterfaces": {
                status = this.getLocalInterfaces();
                break;
            }
            case "AddLiveStream": {
                status = this.addLiveStream(request);
                break;
            }
        }
        this.responseJSON(status, response);
    }

    private String getRFOutputPath() {
        JSONObject obj = new JSONObject();
        obj.put("path", mGatewayManager.getDefaultOutputPath());
        return this.successStatus(obj);
    }

    private String addLiveStream(HttpServletRequest request) {
        String localNic = this.optParameter(request, "interface", "127.0.0.1");
        String srcAddress = this.optParameter(request, "address", "");
        String port = this.optParameter(request, "port", "5555");
        try {
            PlayoutUtils.addLiveStream(localNic, srcAddress, port);
            return this.successStatus();
        }
        catch (BaseHttpServlet.MessageException e) {
            LOG.error(e.getMessage(), e);
            return this.failedStatus(e.getMessage());
        }
    }

    private String getLocalInterfaces() {
        String status = null;
        JSONArray jaInterfaces = new JSONArray();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface netInter = interfaces.nextElement();
                if (netInter.isLoopback() || !netInter.supportsMulticast() || netInter.isVirtual()) continue;
                for (InterfaceAddress ifAddr : netInter.getInterfaceAddresses()) {
                    String ipStr = ifAddr.getAddress().getHostAddress();
                    if (ifAddr.getAddress() instanceof Inet6Address) {
                        LOG.info("{},ipv6 address will ignore", (Object)ipStr);
                        continue;
                    }
                    JSONObject jsInterface = new JSONObject();
                    jsInterface.put("name", netInter.getDisplayName());
                    jsInterface.put("address", ipStr);
                    jaInterfaces.put(jsInterface);
                }
            }
            status = this.successStatus(jaInterfaces);
        }
        catch (SocketException e) {
            LOG.error(e.getMessage(), e);
            status = this.failedStatus(e.getMessage());
        }
        return status;
    }

    public String getMGateInterfaces() {
        return this.successStatus(mGatewayManager.getInterfaces());
    }

    private String checkPlayoutList(HttpServletRequest request) {
        String status = null;
        String roomStrs = request.getParameter("roomStrs");
        String uid = request.getParameter("uid");
        String platform = request.getParameter("platform");
        String playType = request.getParameter("playType");
        if (!PlayoutUtils.checkPlayoutCompatible(platform, roomStrs)) {
            return this.failedStatus("INCOMPATIBLE:Content is already being send to the TV, please stop that content first");
        }
        if (!StringUtils.isEmpty(request.getParameter("uid"))) {
            String result = RoomCheckUtil.checkOverWrite(playType, roomStrs, uid, false).getConflictResult();
            status = result.length() == 0 ? this.successStatus() : this.failedStatus(result);
        }
        return status;
    }

    private String deletePlayouts(HttpServletRequest request) {
        String status = this.successStatus();
        String ids = request.getParameter("ids");
        PlayoutUtils.deletePlayouts(ids);
        return status;
    }

    private String getMGateVersion() {
        String version = mGatewayManager.getVersion();
        JSONObject jsObject = new JSONObject();
        jsObject.put("mgate_version", version);
        jsObject.put("gateway_version", mGatewayManager.getGatewayVersion());
        jsObject.put("mgate_url", mGatewayManager.getMGateUrl());
        return this.successStatus(jsObject);
    }

    private String getAVServices() {
        List<ResponsePlayInfo.AVService> avServices = mGatewayManager.getAVServices();
        JSONObject jsObj = new JSONObject("{\"status\":\"success\"}");
        JSONArray ja = new JSONArray(new Gson().toJson(avServices));
        jsObj.put("data", ja);
        return jsObj.toString();
    }

    private String switchMGateState(HttpServletRequest request) {
        if (mGatewayManager.isPlaying()) {
            LOG.info("switch mgate to stop");
            PlayoutUtils.stopPlayout();
        } else {
            LOG.info("switch mgate to start");
            try {
                PlayoutUtils.triggerStartPlayout();
            }
            catch (BaseHttpServlet.MessageException e) {
                LOG.error(e.getMessage());
                return this.failedStatus(e.getMessage());
            }
        }
        return "{\"status\":\"success\"}";
    }

    private String uploadAVStream(HttpServletRequest request) {
        InputStream filecontent = null;
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            List<FileItem> items = fileUpload.parseRequest(request);
            String filename = "";
            long fileSize = 0L;
            for (FileItem item : items) {
                if (item.isFormField()) continue;
                filename = FilenameUtils.getName(item.getName());
                filecontent = item.getInputStream();
                fileSize = item.getSize();
                break;
            }
            if (null == filename || null == filecontent) {
                LOG.error("filename or filecontent empty");
                throw new BaseHttpServlet.MessageException("uploading file empty");
            }
            TpvFileUtils.checkDiskSpaceFull(fileSize);
            String newFileName = TpvFileUtils.filterFileName(filename);
            if (PlayoutUtils.streamPlayoutExisted(newFileName)) {
                throw new BaseHttpServlet.MessageException("AV Stream file " + filename + " is playing, please add a new file");
            }
            if (PlayoutUtils.isOldGatewayPlayoutExist()) {
                throw new BaseHttpServlet.MessageException("playout using old gateway existed, please remove that first");
            }
            this.saveAVStreamFile(newFileName, filecontent);
            PlayoutUtils.addStreamToPlayout(newFileName);
            return "{\"status\":\"success\"}";
        }
        catch (Exception e) {
            LOG.error(e.getMessage());
            return this.failedStatus(e.getMessage());
        }
    }

    private String playoutAVStream() {
        PlayoutInfoManager playoutInfoMgr = JpaManager.getPlayoutInfoManager();
        List<PlayoutInfo> infoList = playoutInfoMgr.findPlayoutInfoByType("AV Stream");
        for (PlayoutInfo info : infoList) {
            info.setConnectId(1);
            int res = mGatewayManager.addStream(info.getName());
            info.setStatus(res);
            if (res == 0) continue;
            String msg = mGatewayManager.getMessageByCode(res);
            return this.failedStatus(msg);
        }
        return this.successStatus();
    }

    private String handleAddToPlayoutList(HttpServletRequest request) {
        String playType = request.getParameter("playType");
        String roomStrs = request.getParameter("roomStrs");
        String uid = request.getParameter("uid");
        String platform = request.getParameter("platform");
        String groupName = request.getParameter("groupName");
        String result = null;
        List<Devices> rfTvs = this.getRFTVListByGroupName(groupName);
        if (!rfTvs.isEmpty()) {
            for (Devices rfTv : rfTvs) {
                String rid = rfTv.getTvroomid();
                result = this.addToPlayoutByPara(playType, rid, uid, platform);
            }
        } else {
            result = this.addToPlayoutByPara(playType, roomStrs, uid, platform);
        }
        return result;
    }

    private String addToPlayoutByPara(String playType, String roomStrs, String uid, String platform) {
        String status = "{\"status\":\"success\"}";
        String platformId = PlatformUtils.getPlatformId(platform);
        if (!PlayoutUtils.checkPlayoutCompatible(platformId, roomStrs)) {
            status = this.failedStatus("Content is already being send to the TV, please stop that content first");
        } else {
            LastRFConfig lastRFConfig = LastRFConfig.loadLastConfig();
            String output = lastRFConfig.outputConfig.output;
            if (output.equalsIgnoreCase("RF") && !mGatewayManager.isDektecCardInstalled()) {
                status = "{\"status\":\"dektecfail\"}";
            } else {
                try {
                    PlayoutUtils.addToPlayoutList(uid, playType, roomStrs, null);
                }
                catch (BaseHttpServlet.MessageException e) {
                    LOG.error(e.getMessage(), e);
                    status = this.failedStatus(e.getMessage());
                }
            }
        }
        return status;
    }

    private List<Devices> getRFTVListByGroupName(String groupName) {
        List<Devices> devicesList = Collections.emptyList();
        if (StringUtils.isNotEmpty(groupName)) {
            devicesList = JpaManager.getDevicesManager().findDevicesByGroupName(groupName).stream().filter(Devices::isRFDevice).collect(Collectors.toList());
        }
        return devicesList;
    }

    private String processIPUpgrade(HttpServletRequest request) {
        HashMap<String, String> parameters;
        String cloneIdStr = request.getParameter("id");
        String tvIds = request.getParameter("tvids");
        String groupIds = request.getParameter("groupIds");
        String selectCloneType = request.getParameter("selectType");
        String cloneType = request.getParameter("cloneType");
        if (null == cloneType) {
            cloneType = "clone";
        }
        if ((parameters = this.requestParametersToMap(request)).get("output") == null) {
            parameters.put("output", "C");
        }
        if (parameters.get("select_clone_type") == null) {
            parameters.put("select_clone_type", selectCloneType);
        }
        String status = IPUpgradeManager.processCloneUpgradeType(selectCloneType, cloneIdStr, tvIds, groupIds, parameters);
        CommonConstants.CloneItemType cloneType1 = IPUpgradeManager.convertUpgradeTypeToCloneItemType(selectCloneType);
        IPUpgradeManager.assignRFPlayouts(tvIds, cloneType1, Integer.parseInt(cloneIdStr), "PMS");
        return status;
    }

    private HashMap<String, String> requestParametersToMap(HttpServletRequest request) {
        HashMap<String, String> parameters = new HashMap<String, String>();
        Map reqParamsMap = request.getParameterMap();
        for (String param : reqParamsMap.keySet()) {
            parameters.put(param, request.getParameter(param));
        }
        return parameters;
    }

    private String updateLastConfig(HttpServletRequest request) {
        LastRFConfig.OutputConfig outputConfig;
        String outputStr;
        String status = this.successStatus();
        LastRFConfig lastConfig = LastRFConfig.loadLastConfig();
        if (request.getParameter("mod") != null && !request.getParameter("mod").trim().equals("")) {
            lastConfig.setModulation(request.getParameter("mod"));
        }
        if (request.getParameter("band") != null && !request.getParameter("band").trim().equals("")) {
            lastConfig.setBandwidth(request.getParameter("band"));
        }
        if (request.getParameter("outlevel") != null && !request.getParameter("outlevel").trim().equals("")) {
            lastConfig.setOutputLevel(request.getParameter("outlevel"));
        }
        if (request.getParameter("freq") != null && !request.getParameter("freq").trim().equals("")) {
            lastConfig.setFrequency(request.getParameter("freq"));
        }
        if (request.getParameter("onid") != null) {
            lastConfig.setONID(request.getParameter("onid"));
        } else {
            lastConfig.setONID("Others-233A");
        }
        if (request.getParameter("cardIndex") != null && !request.getParameter("cardIndex").trim().equals("")) {
            lastConfig.cardIndex = Integer.valueOf(request.getParameter("cardIndex"));
        }
        if (request.getParameter("transmode") != null && !request.getParameter("transmode").trim().equals("")) {
            lastConfig.transmissionMode = request.getParameter("transmode");
        }
        if (request.getParameter("coderate") != null && !request.getParameter("coderate").trim().equals("")) {
            lastConfig.coderate = request.getParameter("coderate");
        }
        if (request.getParameter("guardInterval") != null && !request.getParameter("guardInterval").trim().equals("")) {
            lastConfig.guardInterval = request.getParameter("guardInterval");
        }
        if (null != (outputStr = request.getParameter("outputSetting")) && !outputStr.isEmpty() && null != (outputConfig = new Gson().fromJson(outputStr, LastRFConfig.OutputConfig.class))) {
            lastConfig.outputConfig = outputConfig;
        }
        lastConfig.save();
        try {
            mGatewayManager.setPlayoutSettings(lastConfig);
            PlayoutUtils.getInstance().onPlayoutChanged();
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
            status = this.failedStatus(e.getMessage());
        }
        return status;
    }

    private String getRFPlayoutStatus(HttpServletRequest request) {
        JSONObject statusObj = new JSONObject();
        statusObj.put("PlayStatus", mGatewayManager.getState().name());
        statusObj.put("pooling", mGatewayManager.isItemUpdatePooling());
        statusObj.put("AddingPlayout", mGatewayManager.isPlayoutWaiting());
        mGatewayManager.setItemUpdatePooling(false);
        statusObj.put("errorMessage", mGatewayManager.getErrorMessage());
        mGatewayManager.setErrorMessage(null);
        return statusObj.toString();
    }

    private void saveAVStreamFile(String filename, InputStream filecontent) throws IOException {
        File avstreamPath = new File(CommonConstants.UPLOADED_AVSTREAM_LOCATION);
        if (!avstreamPath.exists()) {
            avstreamPath.mkdirs();
        }
        File storeFile = new File(CommonConstants.UPLOADED_AVSTREAM_LOCATION + filename);
        try (FileOutputStream fos = new FileOutputStream(storeFile);){
            IOUtils.copy(filecontent, (OutputStream)fos);
        }
    }
}

