/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.annotation.WebServlet
 */
package com.tpvision.smartinstall.servlet;

import com.google.gson.Gson;
import com.tpvision.smartinstall.core.ChannelHelper;
import com.tpvision.smartinstall.core.SettingChannelBean;
import com.tpvision.smartinstall.dao.core.ChannelPackage;
import com.tpvision.smartinstall.dao.mgr.ChannelPackageManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.SettingManager;
import com.tpvision.smartinstall.gateway.GatewayManager;
import com.tpvision.smartinstall.gateway.ResponsePlayInfo;
import com.tpvision.smartinstall.servlet.BaseHttpServlet;
import com.tpvision.smartinstall.servlet.LastRFConfig;
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.xml.Setting;
import com.tpvision.smartinstall.xml.channel.v4.Application;
import com.tpvision.smartinstall.xml.channel.v4.ApplicationMap;
import com.tpvision.smartinstall.xml.channel.v4.Broadcast;
import com.tpvision.smartinstall.xml.channel.v4.Channel;
import com.tpvision.smartinstall.xml.channel.v4.ChannelMap;
import com.tpvision.smartinstall.xml.channel.v4.Setup;
import com.tpvision.smartinstall.xml.channel.v5.Media;
import com.tpvision.smartinstall.xml.channel.v5.Multicast;
import com.tpvision.smartinstall.xml.channel.v5.TvContents;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;

@WebServlet(urlPatterns={"/channel/*"})
public class ChannelServlet
extends BaseHttpServlet {
    private static final String CHANNEL_PACKAGE_ID = "channelPackageId";
    private static final Logger LOG = LoggerFactory.getLogger(ChannelServlet.class);
    private static final String SUCCESS_ADD_CHN = "1";
    private static final String FAILURE_ADD_CHN = "-1";
    private static final String SUCCESS_DEL_CHN = "1";
    private static final String FAILURE_DEL_CHN = "-1";
    private static final String DUPLICATE = "0";
    private static final String STATUS_SUCCESS = "{\"status\":\"success\"}";
    private static final String STATUS_FAILED = "{\"status\":\"fail\"}";

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mode = this.optParameter(request, "mode", "");
        switch (mode.toUpperCase(Locale.ROOT)) {
            case "INDEX": {
                this.modeIndex(request, response);
                break;
            }
            case "ADD_CHANNEL": {
                this.modeAddChannel(request, response);
                break;
            }
            case "DELETE_CHANNEL": {
                this.modeDeleteChannel(request, response);
                break;
            }
            case "CHANNEL_SWAP": {
                this.modeChannelSwap(request, response);
                break;
            }
            case "CHANNEL_NUM_CHECK": {
                this.modeChannelNumberCheck(request, response);
                break;
            }
            case "THEMETV_NAME_CHANGE": {
                this.modeThemeTVNameChange(request, response);
                break;
            }
            case "CHANNEL_FILTER": {
                this.modeChannelFilter(request, response);
                break;
            }
            case "CHANNEL_FILTER_OPTION": {
                this.modeChannelFilterOption(request, response);
                break;
            }
            case "CHANGE_CHANNEL_PACKAGE_NAME": {
                this.modeChangeChannelPackageName(request, response);
                break;
            }
            case "GET_CHANNEL_PACKAGE_INFO": {
                this.modeGetChannelPackageInfo(request, response);
                break;
            }
            case "DELETE_CHANNEL_PACKAGE": {
                this.modeDeleteChannelPackage(request, response);
                break;
            }
            case "COPY_CHANNEL_PACKAGE": {
                this.modeCopyChannelPackage(request, response);
                break;
            }
            case "CHANNEL_LOGO": {
                this.showChannelLogo(request, response);
                break;
            }
            case "THEMETV_ICON": {
                this.showThemeTvIcon(request, response);
                break;
            }
            case "IMPORT_LOGO": {
                this.importChannelLogo(request, response);
                break;
            }
            case "CHANGE_LOGO": {
                this.changeChannelLogo(request, response);
                break;
            }
            case "DELETE_LOGO": {
                this.deleteChannelLogo(request, response);
                break;
            }
            case "GET_CHANNEL_LOGO_MAP": {
                this.getChannelLogoMap(request, response);
                break;
            }
            case "GET_CHANNEL_LIST": {
                this.getChannelList(request, response);
                break;
            }
            case "GET_APPLICATION_LIST": {
                this.getApplicationList(request, response);
                break;
            }
            case "GET_CHANNEL_APPLICATION_LIST": {
                this.getChannelApplicationList(request, response);
                break;
            }
            case "GET_THEMETV_LIST": {
                this.getThemeTvList(request, response);
                break;
            }
            case "GET_THEMETV_TV_JSON": {
                this.getThemeTvJson(request, response);
                break;
            }
            case "DELETE_CHANNELS": {
                this.deleteChannels(request, response);
                break;
            }
            case "UPDATE_CHANNEL": {
                this.updateChannel(request, response);
                break;
            }
            case "UPDATE_APPLICATION": {
                this.updateApplication(request, response);
                break;
            }
            case "SORT_CHANNELS": {
                this.sortChannels(request, response);
                break;
            }
            case "RESET_CHANNELS_NO": {
                this.resetChannelsNo(request, response);
                break;
            }
            case "SWAP_CHANNEL_NO": {
                this.swapChannelsNo(request, response);
                break;
            }
            case "NEW_CHANNEL": {
                this.newChannel(request, response);
                break;
            }
            case "CHANGE_ICON": {
                this.changeThemeIcon(request, response);
                break;
            }
            case "DELETE_ICON": {
                this.deleteThemeTvIcon(request, response);
                break;
            }
            case "GET_CHANNEL_NAMES": {
                this.getChannelNames(request, response);
                break;
            }
            case "GET_SUB_CHANNEL_NAMES_BY_FILTER": {
                this.getChannelNamesByFilter(request, response);
                break;
            }
            default: {
                this.unknownCommand(response);
            }
        }
    }

    private void getChannelNames(HttpServletRequest request, HttpServletResponse response) {
        String channelPackageId = request.getParameter(CHANNEL_PACKAGE_ID);
        String status = null;
        try {
            ChannelHelper helper = new ChannelHelper(Integer.parseInt(channelPackageId));
            List<String> channelList = helper.getChannelNames();
            status = this.successStatus(channelList);
        }
        catch (BaseHttpServlet.MessageException | NumberFormatException e) {
            LOG.error(e.getMessage(), e);
            status = this.failedStatus(e.getMessage());
        }
        this.responseJSON(status, response);
    }

    private void getChannelNamesByFilter(HttpServletRequest request, HttpServletResponse response) {
        String filter = request.getParameter("filter");
        if (StringUtils.isBlank(filter)) {
            this.getChannelNames(request, response);
            return;
        }
        String[] params = filter.split(",");
        String channelPackageId = request.getParameter(CHANNEL_PACKAGE_ID);
        String status = null;
        try {
            ChannelHelper helper = new ChannelHelper(Integer.parseInt(channelPackageId));
            List<String> channelList = helper.getChannelNamesByTypeAndMediums(params[0], Arrays.copyOfRange(params, 1, params.length));
            status = this.successStatus(channelList);
        }
        catch (BaseHttpServlet.MessageException | NumberFormatException e) {
            LOG.error(e.getMessage(), e);
            status = this.failedStatus(e.getMessage());
        }
        this.responseJSON(status, response);
    }

    private void newChannel(HttpServletRequest request, HttpServletResponse response) {
        String status = this.successStatus();
        String channelPackageId = request.getParameter(CHANNEL_PACKAGE_ID);
        try {
            ChannelHelper helper = new ChannelHelper(Integer.parseInt(channelPackageId));
            String data = request.getParameter("data");
            helper.newChannel(data);
            status = this.successStatus();
        }
        catch (BaseHttpServlet.MessageException e) {
            LOG.error(e.getMessage(), e);
            status = this.failedStatus(e.getMessage());
        }
        this.responseJSON(status, response);
    }

    private void sortChannels(HttpServletRequest request, HttpServletResponse response) {
        String status = this.successStatus();
        String channelPackageId = request.getParameter(CHANNEL_PACKAGE_ID);
        try {
            ChannelHelper helper = new ChannelHelper(Integer.parseInt(channelPackageId));
            helper.sortChannels();
            status = this.successStatus();
        }
        catch (BaseHttpServlet.MessageException e) {
            LOG.error(e.getMessage(), e);
            status = this.failedStatus(e.getMessage());
        }
        this.responseJSON(status, response);
    }

    private void resetChannelsNo(HttpServletRequest request, HttpServletResponse response) {
        String status = this.successStatus();
        String channelPackageId = request.getParameter(CHANNEL_PACKAGE_ID);
        try {
            ChannelHelper helper = new ChannelHelper(Integer.parseInt(channelPackageId));
            helper.resetChannelsNo();
            status = this.successStatus();
        }
        catch (BaseHttpServlet.MessageException e) {
            LOG.error(e.getMessage(), e);
            status = this.failedStatus(e.getMessage());
        }
        this.responseJSON(status, response);
    }

    private void swapChannelsNo(HttpServletRequest request, HttpServletResponse response) {
        String status = this.successStatus();
        String channelPackageId = request.getParameter(CHANNEL_PACKAGE_ID);
        String swapIds = request.getParameter("swapIds");
        try {
            ChannelHelper helper = new ChannelHelper(Integer.parseInt(channelPackageId));
            helper.swapChannelsNo(swapIds);
            status = this.successStatus();
        }
        catch (BaseHttpServlet.MessageException e) {
            LOG.error(e.getMessage(), e);
            status = this.failedStatus(e.getMessage());
        }
        this.responseJSON(status, response);
    }

    private void updateChannel(HttpServletRequest request, HttpServletResponse response) {
        String status = this.successStatus();
        String channelPackageId = request.getParameter(CHANNEL_PACKAGE_ID);
        String channelId = request.getParameter("channelId");
        try {
            ChannelHelper helper = new ChannelHelper(Integer.parseInt(channelPackageId));
            String jsChannel = request.getParameter("data");
            jsChannel = HtmlUtils.htmlUnescape(jsChannel);
            helper.updateChannel(Integer.parseInt(channelId), jsChannel);
            status = this.successStatus();
        }
        catch (BaseHttpServlet.MessageException | SQLException e) {
            LOG.error(e.getMessage(), e);
            status = this.failedStatus(e.getMessage());
        }
        this.responseJSON(status, response);
    }

    private void updateApplication(HttpServletRequest request, HttpServletResponse response) {
        String status = this.successStatus();
        String channelPackageId = request.getParameter(CHANNEL_PACKAGE_ID);
        String applicationId = request.getParameter("applicationId");
        try {
            ChannelHelper helper = new ChannelHelper(Integer.parseInt(channelPackageId));
            String jsApplication = request.getParameter("data");
            jsApplication = HtmlUtils.htmlUnescape(jsApplication);
            helper.updateApplication(Integer.parseInt(applicationId), jsApplication);
            status = this.successStatus();
        }
        catch (BaseHttpServlet.MessageException | SQLException e) {
            LOG.error(e.getMessage(), e);
            status = this.failedStatus(e.getMessage());
        }
        this.responseJSON(status, response);
    }

    private void deleteChannels(HttpServletRequest request, HttpServletResponse response) {
        String status = "";
        String channelPackageId = request.getParameter(CHANNEL_PACKAGE_ID);
        String ids = request.getParameter("ids");
        try {
            ChannelHelper helper = new ChannelHelper(Integer.parseInt(channelPackageId));
            helper.deleteChannels(ids);
            status = this.successStatus();
        }
        catch (BaseHttpServlet.MessageException | SQLException e) {
            LOG.error(e.getMessage(), e);
            status = this.failedStatus(e.getMessage());
        }
        this.responseJSON(status, response);
    }

    private void getThemeTvJson(HttpServletRequest request, HttpServletResponse response) {
        String channelPackageId = request.getParameter(CHANNEL_PACKAGE_ID);
        String status = null;
        try {
            ChannelHelper helper = new ChannelHelper(Integer.parseInt(channelPackageId));
            JSONObject themetvJson = helper.getThemeTvJson();
            status = this.successStatus(themetvJson);
        }
        catch (BaseHttpServlet.MessageException | NumberFormatException e) {
            LOG.error(e.getMessage(), e);
            status = this.failedStatus(e.getMessage());
        }
        this.responseJSON(status, response);
    }

    private void getThemeTvList(HttpServletRequest request, HttpServletResponse response) {
        String channelPackageId = request.getParameter(CHANNEL_PACKAGE_ID);
        String status = null;
        try {
            ChannelHelper helper = new ChannelHelper(Integer.parseInt(channelPackageId));
            List<String> themetvList = helper.getThemeTvNameList();
            status = this.successStatus(themetvList);
        }
        catch (BaseHttpServlet.MessageException | NumberFormatException e) {
            LOG.error(e.getMessage(), e);
            status = this.failedStatus(e.getMessage());
        }
        this.responseJSON(status, response);
    }

    private void getApplicationList(HttpServletRequest request, HttpServletResponse response) {
        String channelPackageId = request.getParameter(CHANNEL_PACKAGE_ID);
        String status = null;
        try {
            ChannelHelper helper = new ChannelHelper(Integer.parseInt(channelPackageId));
            List<?> appList = helper.getApplicationList();
            status = this.successStatus(appList);
        }
        catch (BaseHttpServlet.MessageException | NumberFormatException e) {
            LOG.error(e.getMessage(), e);
            status = this.failedStatus(e.getMessage());
        }
        this.responseJSON(status, response);
    }

    private void getChannelList(HttpServletRequest request, HttpServletResponse response) {
        String channelPackageId = request.getParameter(CHANNEL_PACKAGE_ID);
        String status = null;
        try {
            ChannelHelper helper = new ChannelHelper(Integer.parseInt(channelPackageId));
            List<?> channelList = helper.getChannelList();
            status = this.successStatus(channelList);
        }
        catch (BaseHttpServlet.MessageException | NumberFormatException e) {
            LOG.error(e.getMessage(), e);
            status = this.failedStatus(e.getMessage());
        }
        this.responseJSON(status, response);
    }

    private void getChannelApplicationList(HttpServletRequest request, HttpServletResponse response) {
        String channelPackageId = request.getParameter(CHANNEL_PACKAGE_ID);
        String status = null;
        try {
            ChannelHelper helper = new ChannelHelper(Integer.parseInt(channelPackageId));
            List<?> appList = helper.getApplicationList();
            List<?> channelList = helper.getChannelList();
            status = this.successStatus(appList, channelList);
        }
        catch (BaseHttpServlet.MessageException | NumberFormatException e) {
            LOG.error(e.getMessage(), e);
            status = this.failedStatus(e.getMessage());
        }
        this.responseJSON(status, response);
    }

    private void getChannelLogoMap(HttpServletRequest request, HttpServletResponse response) {
        String status = null;
        String channelPackageIdStr = request.getParameter(CHANNEL_PACKAGE_ID);
        try {
            int channelPackageId = Integer.parseInt(channelPackageIdStr);
            ChannelHelper helper = new ChannelHelper(channelPackageId);
            JSONArray ja = helper.getChannelLogoMap();
            status = this.successStatus(ja);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            status = this.failedStatus(e.getMessage());
        }
        this.responseJSON(status, response);
    }

    private void deleteChannelLogo(HttpServletRequest request, HttpServletResponse response) {
        String status = STATUS_FAILED;
        String channelPackageIdStr = request.getParameter(CHANNEL_PACKAGE_ID);
        String channelNumber = request.getParameter("presetNumber");
        try {
            int channelPackageId = Integer.parseInt(channelPackageIdStr);
            ChannelHelper helper = new ChannelHelper(channelPackageId);
            helper.updateChannelLogo(channelNumber, "");
            status = this.successStatus();
        }
        catch (BaseHttpServlet.MessageException e1) {
            LOG.error(e1.getMessage(), e1);
            status = this.failedStatus(e1.getMessage());
        }
        this.responseJSON(status, response);
    }

    private void deleteThemeTvIcon(HttpServletRequest request, HttpServletResponse response) {
        String status = STATUS_FAILED;
        String channelPackageIdStr = request.getParameter(CHANNEL_PACKAGE_ID);
        String ttvIndex = request.getParameter("ttvIndex");
        try {
            int channelPackageId = Integer.parseInt(channelPackageIdStr);
            ChannelHelper helper = new ChannelHelper(channelPackageId);
            HashMap<String, String> iconList = new HashMap<String, String>();
            iconList.put(ttvIndex, "");
            helper.updateThemeTvIconMapping(iconList);
            status = this.successStatus();
        }
        catch (BaseHttpServlet.MessageException e1) {
            LOG.error(e1.getMessage(), e1);
            status = this.failedStatus(e1.getMessage());
        }
        this.responseJSON(status, response);
    }

    private void changeChannelLogo(HttpServletRequest request, HttpServletResponse response) {
        String status = STATUS_FAILED;
        String channelPackageIdStr = request.getParameter(CHANNEL_PACKAGE_ID);
        String channelNumber = request.getParameter("presetNumber");
        int channelPackageId = Integer.parseInt(channelPackageIdStr);
        HashMap<String, String> channelMap = new HashMap<String, String>();
        String logoPath = CloneItemUtils.getChannelPackageLogoPath(channelPackageId);
        try {
            String tempFileName = channelNumber + "_" + System.currentTimeMillis() + ".tmp";
            String finalFileName = channelNumber + ".png";
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            fileUpload.setSizeMax(204800L);
            List<FileItem> items = fileUpload.parseRequest(request);
            for (FileItem item : items) {
                if (item.isFormField()) continue;
                File tempFile = new File(logoPath + File.separator + "custom" + File.separator + tempFileName);
                File finalFile = new File(logoPath + File.separator + "custom" + File.separator + finalFileName);
                if (!tempFile.getParentFile().exists()) {
                    tempFile.getParentFile().mkdirs();
                }
                item.write(tempFile);
                if (finalFile.exists()) {
                    Files.delete(finalFile.toPath());
                }
                Files.move(tempFile.toPath(), finalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                channelMap.put(channelNumber, finalFileName);
                break;
            }
            ChannelHelper helper = new ChannelHelper(channelPackageId);
            helper.updateChannelLogo(channelNumber, finalFileName);
            status = this.successStatus();
        }
        catch (Exception e) {
            LOG.error("Channel logo update failed: " + channelNumber, e);
            this.cleanTempFiles(logoPath, channelNumber);
            status = this.failedStatus("Upload failed: " + e.getMessage());
        }
        this.responseJSON(status, response);
    }

    private void cleanTempFiles(String logoPath, String channelNumber) {
        try {
            File[] tempFiles;
            File customDir = new File(logoPath + File.separator + "custom");
            for (File temp : tempFiles = customDir.listFiles((dir, name) -> name.startsWith(channelNumber + "_") && name.endsWith(".tmp"))) {
                Files.deleteIfExists(temp.toPath());
            }
        }
        catch (IOException e) {
            LOG.warn("Temp file cleanup failed", e);
        }
    }

    private void changeThemeIcon(HttpServletRequest request, HttpServletResponse response) {
        String status = STATUS_FAILED;
        String channelPackageIdStr = request.getParameter(CHANNEL_PACKAGE_ID);
        String ttvIndex = request.getParameter("ttvIndex");
        int channelPackageId = Integer.parseInt(channelPackageIdStr);
        HashMap<String, String> iconList = new HashMap<String, String>();
        String iconPath = CloneItemUtils.getChannelThemeTvIconPath(channelPackageId);
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            List<FileItem> items = fileUpload.parseRequest(request);
            String filename = "";
            for (FileItem item : items) {
                if (item.isFormField()) continue;
                filename = FilenameUtils.getName(item.getName());
                String newFileName = "custom/" + ttvIndex + "." + FilenameUtils.getExtension(filename);
                String logoFileName = iconPath + File.separator + newFileName;
                File logoFile = new File(logoFileName);
                if (!logoFile.getParentFile().exists()) {
                    logoFile.getParentFile().mkdirs();
                }
                item.write(logoFile);
                iconList.put(ttvIndex, newFileName);
                break;
            }
            ChannelHelper helper = new ChannelHelper(channelPackageId);
            helper.updateThemeTvIconMapping(iconList);
            status = this.successStatus();
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            status = this.failedStatus(e.getMessage());
        }
        this.responseJSON(status, response);
    }

    private void importChannelLogo(HttpServletRequest request, HttpServletResponse response) {
        String status = STATUS_FAILED;
        String channelPackageIdStr = request.getParameter(CHANNEL_PACKAGE_ID);
        HashMap<String, String> channelLogMap = new HashMap<String, String>();
        int channelPackageId = Integer.parseInt(channelPackageIdStr);
        String logoPath = CloneItemUtils.getChannelPackageLogoPath(channelPackageId);
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            List<FileItem> items = fileUpload.parseRequest(request);
            String filename = "";
            for (FileItem item : items) {
                if (item.isFormField()) continue;
                filename = FilenameUtils.getName(item.getName());
                String logoFileName = logoPath + File.separator + "custom" + File.separator + filename;
                File logoFile = new File(logoFileName);
                if (!logoFile.getParentFile().exists()) {
                    logoFile.getParentFile().mkdirs();
                }
                item.write(logoFile);
                String channelNumber = FilenameUtils.getBaseName(filename);
                channelLogMap.put(channelNumber, filename);
            }
            ChannelHelper helper = new ChannelHelper(channelPackageId);
            helper.updateChannelLogoMapping(channelLogMap);
            status = STATUS_SUCCESS;
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            status = this.failedStatus(e.getMessage());
        }
        this.responseJSON(status, response);
    }

    private void showThemeTvIcon(HttpServletRequest request, HttpServletResponse response) {
        String packageId;
        String path;
        File iconFile;
        String iconPath = request.getParameter("iconPath");
        if (null != iconPath) {
            iconPath = iconPath.replace("file://", "");
        }
        if (!(iconFile = new File(path = CloneItemUtils.getChannelThemeTvIconPath(Integer.valueOf(packageId = request.getParameter("packageId"))) + iconPath)).exists()) {
            path = CommonConstants.servletContextPath + "/static/images/upload_normal.png";
        }
        try (ServletOutputStream outStream = response.getOutputStream();
             FileInputStream fis = new FileInputStream(path);){
            byte[] data = new byte[1000];
            while (fis.read(data) > 0) {
                outStream.write(data);
            }
            response.setContentType("image/*");
            outStream.write(data);
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void showChannelLogo(HttpServletRequest request, HttpServletResponse response) {
        String packageId;
        String path;
        File logoFile;
        String logoPath = request.getParameter("logoPath");
        if (null != logoPath && (logoPath = logoPath.replaceAll("file://", "")).startsWith("default/")) {
            logoPath = "";
        }
        if (!(logoFile = new File(path = CloneItemUtils.getChannelPackageLogoPath(Integer.valueOf(packageId = request.getParameter("packageId"))) + logoPath)).exists() || null == logoPath || logoPath.isEmpty()) {
            path = CommonConstants.servletContextPath + "/static/images/upload_normal.png";
        }
        try (ServletOutputStream outStream = response.getOutputStream();
             FileInputStream fis = new FileInputStream(path);){
            byte[] data = new byte[1000];
            while (fis.read(data) > 0) {
                outStream.write(data);
            }
            response.setContentType("image/*");
            outStream.write(data);
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void modeIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sname = request.getParameter("sname");
        String channelPackageId = request.getParameter(CHANNEL_PACKAGE_ID);
        if (null != sname) {
            SettingManager smgr = JpaManager.getSettingManager();
            List<com.tpvision.smartinstall.dao.core.Setting> set = smgr.findSettingsByName(sname);
            try {
                channelPackageId = String.valueOf(set.get(0).getChannelPackageId());
            }
            catch (NullPointerException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        request.getSession().setAttribute("cloneName", sname);
        request.getSession().setAttribute(CHANNEL_PACKAGE_ID, channelPackageId);
        String channelsRef = this.getChannelJSP(channelPackageId);
        request.getRequestDispatcher(channelsRef).forward(request, response);
    }

    private void modeChannelFilterOption(HttpServletRequest request, HttpServletResponse response) {
        String sname = request.getParameter("sname");
        ArrayList<String> channleFilterOption = new ArrayList<String>();
        SettingChannelBean scb = null;
        try {
            int channelPackageId = this.getAssignedChannelPackageId(sname);
            ChannelPackage cp = JpaManager.getChannelPackageManager().loadByKey(channelPackageId);
            if (channelPackageId <= 0 || cp == null) {
                throw new BaseHttpServlet.MessageException("setting no assigned channel package");
            }
            scb = new Gson().fromJson(cp.getValue(), SettingChannelBean.class);
            List<String> themeTVNamesList = scb.getThemeTvNameList();
            channleFilterOption.add("All Channels");
            channleFilterOption.add("TV Channels");
            channleFilterOption.add("Radio Channels");
            channleFilterOption.add("MyChoice Free");
            channleFilterOption.add("MyChoice Package 1");
            channleFilterOption.add("MyChoice Package 2");
            for (int i = 0; i < 10; ++i) {
                if ("".equals(themeTVNamesList.get(i))) continue;
                channleFilterOption.add(themeTVNamesList.get(i));
            }
        }
        catch (BaseHttpServlet.MessageException e) {
            LOG.error(e.getMessage(), e);
        }
        this.responseText(String.join((CharSequence)",", channleFilterOption), response);
    }

    private int getAssignedChannelPackageId(String sname) {
        try {
            List<com.tpvision.smartinstall.dao.core.Setting> settings = JpaManager.getSettingManager().findSettingsByName(sname);
            if (settings.isEmpty()) {
                LOG.error("setting not exists");
                throw new IOException("not assign setting");
            }
            com.tpvision.smartinstall.dao.core.Setting setting = settings.get(0);
            return CloneItemUtils.getAssignedId(CommonConstants.CloneItemType.ChannelList, setting);
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
            return -1;
        }
    }

    private void modeChannelFilter(HttpServletRequest request, HttpServletResponse response) {
        String sname = "";
        String channelFilterValue = "";
        sname = request.getParameter("sname");
        channelFilterValue = request.getParameter("chfilterValue");
        ArrayList<String> filterChannelsList = new ArrayList<String>();
        String status = null;
        try {
            JSONObject setup;
            int channelPackageId = this.getAssignedChannelPackageId(sname);
            ChannelPackage cp = JpaManager.getChannelPackageManager().loadByKey(channelPackageId);
            if (channelPackageId <= 0 || cp == null) {
                throw new IOException("not assign channel Package," + sname);
            }
            SettingChannelBean scb = new Gson().fromJson(cp.getValue(), SettingChannelBean.class);
            List<String> themeTVNamesList = scb.getThemeTvNameList();
            int themeTVNamesCount = themeTVNamesList.size();
            HashMap<String, String> themeTVNamesMap = new HashMap<String, String>();
            for (int i = 0; i < themeTVNamesCount; ++i) {
                themeTVNamesMap.put("ThemeTV " + (i + 1), themeTVNamesList.get(i));
            }
            List channelList = scb.getChannelList();
            String jaStr = new Gson().toJson(channelList);
            ArrayList<JSONObject> filterObjList = new ArrayList<JSONObject>();
            JSONArray ja = new JSONArray(jaStr);
            block19: for (int i = 0; i < ja.length(); ++i) {
                JSONObject obj = ja.optJSONObject(i);
                setup = obj.optJSONObject("setup");
                JSONObject broadcast = obj.optJSONObject("broadcast");
                switch (channelFilterValue) {
                    case "All Channels": {
                        filterObjList.add(obj);
                        continue block19;
                    }
                    case "Radio Channels": {
                        if (broadcast == null || !broadcast.optString("servicetype").equalsIgnoreCase("Radio")) continue block19;
                        filterObjList.add(obj);
                        continue block19;
                    }
                    case "TV Channels": {
                        if (broadcast == null || !broadcast.optString("servicetype").equalsIgnoreCase("TV")) continue block19;
                        filterObjList.add(obj);
                        continue block19;
                    }
                    case "MyChoice Free": {
                        if (setup == null || !setup.optString("freePKG").equalsIgnoreCase("1")) continue block19;
                        filterObjList.add(obj);
                        continue block19;
                    }
                    case "MyChoice Package 1": {
                        if (setup == null || !setup.optString("payPKG1").equalsIgnoreCase("1")) continue block19;
                        filterObjList.add(obj);
                        continue block19;
                    }
                    case "MyChoice Package 2": {
                        if (setup == null || !setup.optString("payPKG2").equalsIgnoreCase("1")) continue block19;
                        filterObjList.add(obj);
                        continue block19;
                    }
                    default: {
                        for (int j = 1; j <= themeTVNamesCount; ++j) {
                            if (!((String)themeTVNamesMap.get("ThemeTV " + j)).equalsIgnoreCase(channelFilterValue) || DUPLICATE.equals(setup.optString("ttv" + j))) continue;
                            filterObjList.add(obj);
                        }
                    }
                }
            }
            if (filterObjList.isEmpty()) {
                filterChannelsList.add("None");
            }
            for (JSONObject jo : filterObjList) {
                setup = jo.optJSONObject("setup");
                filterChannelsList.add(setup.optString("presetnumber", DUPLICATE) + " - " + setup.optString("name"));
            }
            status = this.successStatus(filterChannelsList);
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
            status = this.failedStatus(e.getMessage());
        }
        this.responseJSON(status, response);
    }

    protected void modeAddChannel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String channelPackageId = request.getParameter(CHANNEL_PACKAGE_ID);
        String esmode = null;
        String directAddr = null;
        String msg = null;
        try {
            ChannelPackageManager cpmgr = JpaManager.getChannelPackageManager();
            ChannelPackage cp = cpmgr.loadByKey(Integer.parseInt(channelPackageId));
            SettingChannelBean scb = new Gson().fromJson(cp.getValue(), SettingChannelBean.class);
            directAddr = this.getChannelJSP(channelPackageId);
            msg = "v4".equalsIgnoreCase(scb.getChannelVersion()) ? this.addv4Channel(request, response, esmode, cpmgr, cp, scb) : this.addv5Channel(request, response, esmode, cpmgr, cp, scb);
        }
        catch (Exception e) {
            msg = "-1";
            LOG.error("" + e.getMessage(), e);
        }
        if (null != msg) {
            directAddr = directAddr + "&msg=" + msg;
        }
        response.sendRedirect(request.getContextPath() + directAddr);
    }

    protected void modeDeleteChannel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String channelPackageId = request.getParameter(CHANNEL_PACKAGE_ID);
        String esmode = null;
        String directAddr = null;
        String msg = null;
        ChannelPackageManager cpmgr = JpaManager.getChannelPackageManager();
        ChannelPackage cp = cpmgr.loadByKey(Integer.parseInt(channelPackageId));
        if (null != cp) {
            SettingChannelBean scb = new Gson().fromJson(cp.getValue(), SettingChannelBean.class);
            directAddr = this.getChannelJSP(channelPackageId);
            msg = "v4".equalsIgnoreCase(scb.getChannelVersion()) ? this.deletev4Channel(request, response, esmode, cpmgr, cp, scb) : this.deletev5Channel(request, response, esmode, cpmgr, cp, scb);
        }
        if (null != msg) {
            directAddr = directAddr + "&msg=" + msg;
        }
        response.sendRedirect(request.getContextPath() + directAddr);
    }

    protected void modeChannelSwap(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String channelPackageId = request.getParameter(CHANNEL_PACKAGE_ID);
        String channelJson = request.getParameter("channeljson");
        String channelJsonApp = request.getParameter("channeljsonapp");
        String status = STATUS_FAILED;
        if (null != channelPackageId && null != channelJson) {
            ChannelPackageManager cpmgr = JpaManager.getChannelPackageManager();
            ChannelPackage cp = cpmgr.loadByKey(Integer.parseInt(channelPackageId));
            if (null != cp) {
                SettingChannelBean scb = new Gson().fromJson(cp.getValue(), SettingChannelBean.class);
                if ("v4".equalsIgnoreCase(scb.getChannelVersion())) {
                    ArrayList chList;
                    com.tpvision.smartinstall.xml.channel.v4.TvContents cmap = scb.getV4Channel();
                    scb.getV4Channel().getChannelMap().getChannel().clear();
                    String platform = request.getParameter("platform");
                    if (!"2016 ES".equalsIgnoreCase(platform)) {
                        scb.getV4Channel().getApplicationMap().getApplication().clear();
                    }
                    ChannelMap channel = new Gson().fromJson(channelJson, ChannelMap.class);
                    ApplicationMap application = new Gson().fromJson(channelJsonApp, ApplicationMap.class);
                    cmap.setSchemaVersion(scb.getV4Channel().getSchemaVersion());
                    cmap.setApplicationMap(application);
                    cmap.setExtensionMap(scb.getV4Channel().getExtensionMap());
                    cmap.setChannelMap(channel);
                    List<Object> list = null != scb ? (scb.getV4Channel() != null ? scb.getV4Channel().getChannelMap().getChannel() : new ArrayList()) : (chList = new ArrayList());
                    ArrayList appList = null != scb ? (scb.getV4Channel() != null ? scb.getV4Channel().getApplicationMap().getApplication() : new ArrayList()) : new ArrayList();
                    try {
                        JSONObject obj = new JSONObject(channelJson);
                        JSONArray array = obj.getJSONArray("channel");
                        int i = 0;
                        for (i = 0; i < array.length(); ++i) {
                            JSONObject ob = array.getJSONObject(i);
                            JSONObject setup = new JSONObject(ob.get("setup").toString());
                            ((Channel)chList.get(i)).getSetup().setFreePKG(setup.get("FreePKG").toString());
                            ((Channel)chList.get(i)).getSetup().setPayPKG1(setup.get("PayPKG1").toString());
                            ((Channel)chList.get(i)).getSetup().setPayPKG2(setup.get("PayPKG2").toString());
                        }
                        JSONObject objApp = new JSONObject(channelJsonApp);
                        JSONArray arrayApp = objApp.getJSONArray("application");
                        for (int j = 0; j < arrayApp.length(); ++j) {
                            JSONObject obapp = arrayApp.getJSONObject(j);
                            JSONObject setupapp = new JSONObject(obapp.get("setup").toString());
                            ((Application)appList.get(j)).getSetup().setFreePKG(setupapp.get("FreePKG").toString());
                            ((Application)appList.get(j)).getSetup().setPayPKG1(setupapp.get("PayPKG1").toString());
                            ((Application)appList.get(j)).getSetup().setPayPKG2(setupapp.get("PayPKG2").toString());
                        }
                    }
                    catch (Exception e) {
                        LOG.error(e.getMessage(), e);
                    }
                    if (null != scb) {
                        scb.setV4Channel(cmap);
                        String strToSave = new Gson().toJson(scb);
                        cp.setValue(strToSave);
                        cpmgr.save(cp);
                    }
                } else if ("v5".equalsIgnoreCase(scb.getChannelVersion())) {
                    TvContents cmap = scb.getV5Channel();
                    scb.getV5Channel().getChannelMap().getChannel().clear();
                    com.tpvision.smartinstall.xml.channel.v5.ChannelMap channel = new Gson().fromJson(channelJson, com.tpvision.smartinstall.xml.channel.v5.ChannelMap.class);
                    com.tpvision.smartinstall.xml.channel.v5.ApplicationMap application = new Gson().fromJson(channelJsonApp, com.tpvision.smartinstall.xml.channel.v5.ApplicationMap.class);
                    cmap.setSchemaVersion(scb.getV5Channel().getSchemaVersion());
                    cmap.setApplicationMap(application);
                    cmap.setChannelMap(channel);
                    if (null != scb) {
                        scb.setV5Channel(cmap);
                        String strToSave = new Gson().toJson(scb);
                        cp.setValue(strToSave);
                        cpmgr.save(cp);
                    }
                }
                status = STATUS_SUCCESS;
            }
            try {
                response.setContentType("text/json");
                IOUtils.write(status.getBytes(), (OutputStream)response.getOutputStream());
            }
            catch (IOException e1) {
                LOG.error(e1.getMessage(), e1);
            }
        }
    }

    protected void modeChannelNumberCheck(HttpServletRequest request, HttpServletResponse response) {
        String channelPackageId = request.getParameter(CHANNEL_PACKAGE_ID);
        String number = request.getParameter("channelNumber");
        String exist = DUPLICATE;
        try {
            ChannelHelper helper = new ChannelHelper(Integer.parseInt(channelPackageId));
            exist = helper.isPresetNumberExist(number) ? "1" : DUPLICATE;
        }
        catch (BaseHttpServlet.MessageException e) {
            LOG.error(e.getMessage(), e);
        }
        this.responseText(exist, response);
    }

    private String addv4Channel(HttpServletRequest request, HttpServletResponse response, String esmode, ChannelPackageManager cpmgr, ChannelPackage cp, SettingChannelBean scb) {
        String ipchnumber;
        String chtype = request.getParameter("chtype") != null ? request.getParameter("chtype") : "null";
        String number = request.getParameter("chnumber") != null ? request.getParameter("chnumber") : "";
        String frequency = request.getParameter("chfrq") != null ? request.getParameter("chfrq") : DUPLICATE;
        String servicetype = request.getParameter("servicetype") != null ? request.getParameter("servicetype") : "TV";
        String bandwidth = request.getParameter("chbandwidthid") != null ? request.getParameter("chbandwidthid") : "Auto";
        String medium = request.getParameter("medium") != null ? request.getParameter("medium") : "dvbt";
        String modulation = request.getParameter("chmodulation") != null ? request.getParameter("chmodulation") : "16";
        String onid = request.getParameter("chonid") != null ? request.getParameter("chonid") : DUPLICATE;
        String serviceId = request.getParameter("chsid") != null ? request.getParameter("chsid") : DUPLICATE;
        String symrate = request.getParameter("chsymrate") != null ? request.getParameter("chsymrate") : DUPLICATE;
        String system = request.getParameter("chsystem") != null ? request.getParameter("chsystem") : "";
        String tsid = request.getParameter("chtsid") != null ? request.getParameter("chtsid") : DUPLICATE;
        String name = request.getParameter("chname") != null ? request.getParameter("chname") : "";
        String blank = request.getParameter("blank") != null ? request.getParameter("blank") : "";
        String skip = request.getParameter("skip") != null ? request.getParameter("skip") : "";
        String freePKG = request.getParameter("FreePKG") != null ? request.getParameter("FreePKG") : DUPLICATE;
        String payPKG1 = request.getParameter("PayPKG1") != null ? request.getParameter("PayPKG1") : DUPLICATE;
        String payPKG2 = request.getParameter("PayPKG2") != null ? request.getParameter("PayPKG2") : DUPLICATE;
        String string = ipchnumber = request.getParameter("ipchnumber") != null ? request.getParameter("ipchnumber") : "";
        ArrayList chList = null != scb ? (scb.getV4Channel() != null ? (scb.getV4Channel().getChannelMap() != null ? scb.getV4Channel().getChannelMap().getChannel() : new ArrayList()) : new ArrayList()) : new ArrayList();
        Channel channel = new Channel();
        Broadcast broadcast = new Broadcast();
        com.tpvision.smartinstall.xml.channel.v4.Multicast multicast = new com.tpvision.smartinstall.xml.channel.v4.Multicast();
        Setup setup = new Setup();
        String ret_msg = null;
        ArrayList<String> channel_number = new ArrayList<String>();
        for (Channel c : chList) {
            channel_number.add(c.getSetup().getPresetnumber());
        }
        if (channel_number.contains(number) || channel_number.contains(ipchnumber)) {
            ret_msg = DUPLICATE;
            return ret_msg;
        }
        channel_number = new ArrayList();
        for (Channel c : chList) {
            channel_number.add(c.getSetup().getPresetnumber());
        }
        if (channel_number.contains(number)) {
            ret_msg = DUPLICATE;
        } else {
            broadcast.setFrequency(frequency);
            broadcast.setServicetype(servicetype);
            broadcast.setBandwidth(bandwidth);
            broadcast.setMedium(medium);
            broadcast.setModulation(modulation);
            broadcast.setONID(onid);
            for (Setting s : scb.getSetttings().getSetting()) {
                if (s.getRefFile() != null && !"MS2K14".equalsIgnoreCase(s.getRefFile()) && !"ES2K16".equalsIgnoreCase(s.getRefFile()) || !"MS2K14".equalsIgnoreCase(s.getXaddr()) && !"ES2K16".equalsIgnoreCase(s.getXaddr())) continue;
                esmode = "true";
            }
            if (null != chtype && (chtype.equalsIgnoreCase("rf") || chtype.equalsIgnoreCase("null"))) {
                this.rfChannels(request, esmode, frequency, servicetype, bandwidth, medium, modulation, onid, serviceId, symrate, system, tsid, channel, broadcast);
                number = request.getParameter("chnumber") != null ? request.getParameter("chnumber") : "";
            } else {
                String multicasturl = request.getParameter("multicasturl") != null ? request.getParameter("multicasturl") : "";
                multicast.setUrl(multicasturl);
                channel.setMulticast(multicast);
                number = request.getParameter("ipchnumber") != null ? request.getParameter("ipchnumber") : "";
                name = request.getParameter("ipchname") != null ? request.getParameter("ipchname") : "";
            }
            setup.setName(name);
            setup.setPresetnumber(number);
            setup.setSkip(skip);
            setup.setBlank(blank);
            setup.setFreePKG(freePKG);
            setup.setPayPKG1(payPKG1);
            setup.setPayPKG2(payPKG2);
            channel.setSetup(setup);
            int count = -1;
            try {
                if (chList.size() == 0) {
                    scb.getV4Channel().getChannelMap().getChannel().add(++count, channel);
                    String toDbString = new Gson().toJson(scb);
                    ChannelPackage toSave = cp;
                    toSave.setValue(toDbString);
                    toSave.setNumberOfChs(toSave.getNumberOfChs() + 1);
                    cpmgr.save(toSave);
                    ret_msg = "1";
                    return ret_msg;
                }
                for (Channel c : chList) {
                    ++count;
                    if (Integer.parseInt(c.getSetup().getPresetnumber()) > Integer.parseInt(number)) {
                        scb.getV4Channel().getChannelMap().getChannel().add(count, channel);
                        String toDbString = new Gson().toJson(scb);
                        ChannelPackage toSave = cp;
                        toSave.setValue(toDbString);
                        toSave.setNumberOfChs(toSave.getNumberOfChs() + 1);
                        cpmgr.save(toSave);
                        ret_msg = "1";
                    } else {
                        if (count != scb.getV4Channel().getChannelMap().getChannel().size() - 1) continue;
                        scb.getV4Channel().getChannelMap().getChannel().add(channel);
                        String toDbString = new Gson().toJson(scb);
                        ChannelPackage toSave = cp;
                        toSave.setValue(toDbString);
                        toSave.setNumberOfChs(toSave.getNumberOfChs() + 1);
                        cpmgr.save(toSave);
                        ret_msg = "1";
                    }
                    break;
                }
            }
            catch (Exception e) {
                ret_msg = "-1";
                LOG.error("" + e.getMessage(), e);
            }
        }
        return ret_msg;
    }

    public List<com.tpvision.smartinstall.xml.channel.v5.Channel> CMNDStreamChannelFromRequest(HttpServletRequest request) {
        ArrayList<com.tpvision.smartinstall.xml.channel.v5.Channel> channelList = new ArrayList<com.tpvision.smartinstall.xml.channel.v5.Channel>();
        List<ResponsePlayInfo.AVService> avServices = GatewayManager.getInstance().getAVServices();
        for (ResponsePlayInfo.AVService avService : avServices) {
            String checkValue = request.getParameter("cmnd_streams_" + avService.getSID());
            if (null == checkValue || !checkValue.equalsIgnoreCase("on")) continue;
            com.tpvision.smartinstall.xml.channel.v5.Channel channel = new com.tpvision.smartinstall.xml.channel.v5.Channel();
            com.tpvision.smartinstall.xml.channel.v5.Setup setup = new com.tpvision.smartinstall.xml.channel.v5.Setup();
            if (avService.getFreq() > 0L) {
                com.tpvision.smartinstall.xml.channel.v5.Broadcast broadcast = new com.tpvision.smartinstall.xml.channel.v5.Broadcast();
                String frequency = String.valueOf(avService.getFreq()).substring(0, 6);
                broadcast.setFrequency(frequency);
                broadcast.setServicetype("TV");
                broadcast.setBandwidth("Auto");
                broadcast.setMedium("dvbt");
                LastRFConfig lastRFConfig = LastRFConfig.loadLastConfig();
                String modulation = lastRFConfig.getModulation();
                modulation = modulation != null ? modulation.replace("-QAM", "") : "auto";
                broadcast.setModulation(modulation);
                broadcast.setONID(String.valueOf(avService.getONID()));
                broadcast.setServiceID(String.valueOf(avService.getSID()));
                broadcast.setSymbolrate(DUPLICATE);
                broadcast.setSystem("");
                broadcast.setTSID(String.valueOf(avService.getTSID()));
                channel.setBroadcast(broadcast);
            } else if (avService.getUrl() != null) {
                String casttype = "multicast";
                try {
                    InetAddress inetAddress = InetAddress.getByName(avService.getUrl());
                    casttype = inetAddress.isMulticastAddress() ? "multicast" : "unicast";
                }
                catch (UnknownHostException e) {
                    LOG.error("not a valid url for ip");
                }
                String encoding = "VBR";
                String multicasturl = String.format(Locale.ENGLISH, "%s://%s:%d/%d/%d/%d/%s", casttype, avService.getUrl(), avService.getPort(), avService.getSID(), avService.getONID(), avService.getTSID(), encoding);
                Multicast multicast = new Multicast();
                multicast.setUrl(multicasturl);
                channel.setMulticast(multicast);
            }
            setup.setTTV1(DUPLICATE);
            setup.setTTV2(DUPLICATE);
            setup.setTTV3(DUPLICATE);
            setup.setTTV4(DUPLICATE);
            setup.setTTV5(DUPLICATE);
            setup.setTTV6(DUPLICATE);
            setup.setTTV7(DUPLICATE);
            setup.setTTV8(DUPLICATE);
            setup.setTTV9(DUPLICATE);
            setup.setTTV10(DUPLICATE);
            setup.setName(avService.getName());
            setup.setPresetnumber(String.valueOf(avService.getPMTPid()));
            setup.setSkip(DUPLICATE);
            setup.setBlank(DUPLICATE);
            setup.setFreePKG("1");
            setup.setPayPKG1(DUPLICATE);
            setup.setPayPKG2(DUPLICATE);
            channel.setSetup(setup);
            channelList.add(channel);
        }
        return channelList;
    }

    public com.tpvision.smartinstall.xml.channel.v5.Channel channelFromRequest(HttpServletRequest request) {
        com.tpvision.smartinstall.xml.channel.v5.Channel channel = new com.tpvision.smartinstall.xml.channel.v5.Channel();
        com.tpvision.smartinstall.xml.channel.v5.Setup setup = new com.tpvision.smartinstall.xml.channel.v5.Setup();
        String chtype = request.getParameter("chtype") != null ? request.getParameter("chtype") : "null";
        String number = request.getParameter("chnumber") != null ? request.getParameter("chnumber") : "";
        String frequency = request.getParameter("chfrq") != null ? request.getParameter("chfrq") : DUPLICATE;
        String servicetype = request.getParameter("servicetype") != null ? request.getParameter("servicetype") : "TV";
        String bandwidth = request.getParameter("chbandwidthid") != null ? request.getParameter("chbandwidthid") : "Auto";
        String medium = request.getParameter("medium") != null ? request.getParameter("medium") : "dvbt";
        String modulation = request.getParameter("chmodulation") != null ? request.getParameter("chmodulation") : "16";
        String onid = request.getParameter("chonid") != null ? request.getParameter("chonid") : DUPLICATE;
        String serviceId = request.getParameter("chsid") != null ? request.getParameter("chsid") : DUPLICATE;
        String symrate = request.getParameter("chsymrate") != null ? request.getParameter("chsymrate") : DUPLICATE;
        String system = request.getParameter("chsystem") != null ? request.getParameter("chsystem") : "";
        String tsid = request.getParameter("chtsid") != null ? request.getParameter("chtsid") : DUPLICATE;
        String name = request.getParameter("chname") != null ? request.getParameter("chname") : "";
        String blank = request.getParameter("blank") != null ? request.getParameter("blank") : DUPLICATE;
        String skip = request.getParameter("skip") != null ? request.getParameter("skip") : DUPLICATE;
        String physicalChannel = this.optParameter(request, "PhysicalChannel", "2");
        String programNumber = this.optParameter(request, "ProgramNumber", DUPLICATE);
        String orbitalposition = this.optParameter(request, "orbitalposition", "0192E");
        String polarization = this.optParameter(request, "polarization", "Horizontal");
        String esmode = request.getParameter("mode");
        String freePKG = "1";
        String payPKG1 = DUPLICATE;
        String payPKG2 = DUPLICATE;
        if (chtype.equalsIgnoreCase("rf") || chtype.equalsIgnoreCase("null")) {
            com.tpvision.smartinstall.xml.channel.v5.Broadcast broadcast = new com.tpvision.smartinstall.xml.channel.v5.Broadcast();
            broadcast.setFrequency(frequency);
            broadcast.setServicetype(servicetype);
            broadcast.setBandwidth(bandwidth);
            broadcast.setMedium(medium);
            broadcast.setModulation(modulation);
            broadcast.setONID(onid);
            broadcast.setPhysicalChannel(physicalChannel);
            broadcast.setProgramNumber(programNumber);
            broadcast.setOrbitalposition(orbitalposition);
            broadcast.setPolarization(polarization);
            if (null == esmode || !esmode.equalsIgnoreCase("true")) {
                String nid = request.getParameter("chnid") != null ? request.getParameter("chnid") : DUPLICATE;
                broadcast.setONID(nid);
            }
            broadcast.setServiceID(serviceId);
            broadcast.setSymbolrate(symrate);
            broadcast.setSystem(system);
            broadcast.setTSID(tsid);
            channel.setBroadcast(broadcast);
        } else if ("iptv".equalsIgnoreCase(chtype)) {
            Multicast multicast = new Multicast();
            String multicasturl = request.getParameter("multicasturl") != null ? request.getParameter("multicasturl") : "";
            multicast.setUrl(multicasturl);
            channel.setMulticast(multicast);
            number = request.getParameter("ipchnumber") != null ? request.getParameter("ipchnumber") : "";
            name = request.getParameter("ipchname") != null ? request.getParameter("ipchname") : "";
        } else if (!chtype.equalsIgnoreCase("cmnd_streams")) {
            Media media = new Media();
            String mediaurl = request.getParameter("mediaurl") != null ? request.getParameter("mediaurl") : "";
            media.setUrl(mediaurl);
            channel.setMedia(media);
            number = request.getParameter("mediachnumber") != null ? request.getParameter("mediachnumber") : "";
            name = request.getParameter("mediachname") != null ? request.getParameter("mediachname") : "";
        }
        setup.setTTV1(DUPLICATE);
        setup.setTTV2(DUPLICATE);
        setup.setTTV3(DUPLICATE);
        setup.setTTV4(DUPLICATE);
        setup.setTTV5(DUPLICATE);
        setup.setTTV6(DUPLICATE);
        setup.setTTV7(DUPLICATE);
        setup.setTTV8(DUPLICATE);
        setup.setTTV9(DUPLICATE);
        setup.setTTV10(DUPLICATE);
        setup.setName(name);
        setup.setPresetnumber(number);
        setup.setSkip(skip);
        setup.setBlank(blank);
        setup.setFreePKG(freePKG);
        setup.setPayPKG1(payPKG1);
        setup.setPayPKG2(payPKG2);
        channel.setSetup(setup);
        return channel;
    }

    private String addv5Channel(HttpServletRequest request, HttpServletResponse response, String esmode, ChannelPackageManager cpmgr, ChannelPackage cp, SettingChannelBean scb) {
        String chtype = request.getParameter("chtype") != null ? request.getParameter("chtype") : "null";
        List<com.tpvision.smartinstall.xml.channel.v5.Channel> newChannelList = new ArrayList<com.tpvision.smartinstall.xml.channel.v5.Channel>();
        if (chtype.equalsIgnoreCase("cmnd_streams")) {
            newChannelList = this.CMNDStreamChannelFromRequest(request);
        } else {
            newChannelList.add(this.channelFromRequest(request));
        }
        ArrayList<com.tpvision.smartinstall.xml.channel.v5.Channel> chList = new ArrayList<com.tpvision.smartinstall.xml.channel.v5.Channel>();
        try {
            chList.addAll(scb.getV5Channel().getChannelMap().getChannel());
            chList.addAll(newChannelList);
            chList.sort(new Comparator<com.tpvision.smartinstall.xml.channel.v5.Channel>(){

                @Override
                public int compare(com.tpvision.smartinstall.xml.channel.v5.Channel o1, com.tpvision.smartinstall.xml.channel.v5.Channel o2) {
                    Double d1 = NumberUtils.toDouble(o1.getSetup().getPresetnumber(), 0.0);
                    Double d2 = NumberUtils.toDouble(o2.getSetup().getPresetnumber(), 0.0);
                    return d1.compareTo(d2);
                }
            });
            scb.getV5Channel().getChannelMap().getChannel().clear();
            scb.getV5Channel().getChannelMap().getChannel().addAll(chList);
        }
        catch (NullPointerException e) {
            LOG.error(e.getMessage());
        }
        String ret_msg = "1";
        String toDbString = new Gson().toJson(scb);
        cp.setValue(toDbString);
        cp.setNumberOfChs(scb.getV5Channel().getChannelMap().getChannel().size());
        cpmgr.save(cp);
        return ret_msg;
    }

    private String deletev4Channel(HttpServletRequest request, HttpServletResponse response, String esmode, ChannelPackageManager cpmgr, ChannelPackage cp, SettingChannelBean scb) {
        String delChIds;
        String retMsg = null;
        String string = delChIds = request.getParameter("delChIds") != null ? request.getParameter("delChIds") : "none";
        if (delChIds.compareTo("none") == 0) {
            retMsg = "-1";
            return retMsg;
        }
        String[] delChIdsArray = delChIds.split(",");
        int length_ch = delChIdsArray.length;
        if ("".equals(delChIdsArray[0])) {
            length_ch = 0;
        }
        if (length_ch > 0) {
            for (int i = length_ch - 1; i >= 0; --i) {
                scb.getV4Channel().getChannelMap().getChannel().remove(Integer.parseInt(delChIdsArray[i]));
            }
        }
        try {
            String toDbString = new Gson().toJson(scb);
            ChannelPackage toSave = cp;
            toSave.setValue(toDbString);
            toSave.setNumberOfChs(toSave.getNumberOfChs() - 1);
            cpmgr.save(toSave);
            retMsg = "1";
        }
        catch (Exception e) {
            retMsg = "-1";
            LOG.error("" + e.getMessage(), e);
        }
        return retMsg;
    }

    private String deletev5Channel(HttpServletRequest request, HttpServletResponse response, String esmode, ChannelPackageManager cpmgr, ChannelPackage cp, SettingChannelBean scb) {
        String delChIds;
        String ret_msg = null;
        String string = delChIds = request.getParameter("delChIds") != null ? request.getParameter("delChIds") : "none";
        if (delChIds.compareTo("none") == 0) {
            ret_msg = "-1";
            return ret_msg;
        }
        String[] delChIdsArray = delChIds.split(",");
        int length_ch = delChIdsArray.length;
        if ("".equals(delChIdsArray[0])) {
            length_ch = 0;
        }
        if (length_ch > 0) {
            for (int i = length_ch - 1; i >= 0; --i) {
                scb.getV5Channel().getChannelMap().getChannel().remove(Integer.parseInt(delChIdsArray[i]));
            }
        }
        String toDbString = new Gson().toJson(scb);
        ChannelPackage toSave = cp;
        toSave.setValue(toDbString);
        toSave.setNumberOfChs(toSave.getNumberOfChs() - 1);
        cpmgr.save(toSave);
        return "1";
    }

    private void rfChannels(HttpServletRequest request, String esmode, String frequency, String servicetype, String bandwidth, String medium, String modulation, String onid, String serviceId, String symrate, String system, String tsid, Channel channel, Broadcast broadcast) {
        broadcast.setFrequency(frequency);
        broadcast.setServicetype(servicetype);
        broadcast.setBandwidth(bandwidth);
        broadcast.setMedium(medium);
        broadcast.setModulation(modulation);
        broadcast.setONID(onid);
        if (null == esmode || !esmode.equalsIgnoreCase("true")) {
            String nid = request.getParameter("chnid") != null ? request.getParameter("chnid") : DUPLICATE;
            broadcast.setONID(nid);
        }
        broadcast.setServiceID(serviceId);
        broadcast.setSymbolrate(symrate);
        broadcast.setSystem(system);
        broadcast.setTSID(tsid);
        channel.setBroadcast(broadcast);
    }

    private void modeThemeTVNameChange(HttpServletRequest request, HttpServletResponse response) {
        String channelPackageId = request.getParameter(CHANNEL_PACKAGE_ID);
        String[] ttvnamevalArr = request.getParameterValues("ttvnamevalArr[]");
        String status = STATUS_FAILED;
        ChannelHelper helper = new ChannelHelper(Integer.parseInt(channelPackageId));
        try {
            helper.updateThemeTvNameList(Arrays.asList(ttvnamevalArr));
            status = this.successStatus();
        }
        catch (BaseHttpServlet.MessageException e1) {
            LOG.error(e1.getMessage(), e1);
            status = this.failedStatus(e1.getMessage());
        }
        this.responseJSON(status, response);
    }

    protected void modeCopyChannelPackage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String status = STATUS_FAILED;
        String id = request.getParameter("id");
        if (null != id) {
            JpaManager.getChannelPackageManager().copy(Integer.parseInt(id));
        }
        response.setContentType("text/json");
        try {
            IOUtils.write(status.getBytes(), (OutputStream)response.getOutputStream());
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    protected void modeDeleteChannelPackage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String status = STATUS_FAILED;
        String id = request.getParameter("id");
        if (null != id) {
            ChannelPackageManager cpmgr = JpaManager.getChannelPackageManager();
            cpmgr.deleteByKey(Integer.valueOf(id));
            status = STATUS_SUCCESS;
        }
        response.setContentType("text/json");
        try {
            IOUtils.write(status.getBytes(), (OutputStream)response.getOutputStream());
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void modeChangeChannelPackageName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String status;
        String newName = request.getParameter("newName");
        String id = request.getParameter("id");
        int channelId = Integer.parseInt(id);
        ChannelPackageManager cpmgr = JpaManager.getChannelPackageManager();
        ChannelPackage cp = cpmgr.loadByKey(channelId);
        if (null != cp) {
            List<ChannelPackage> channelPackages = cpmgr.findByName(newName);
            if (channelPackages.isEmpty() || channelPackages.size() == 1 && channelPackages.get(0).getId() == channelId) {
                cp.setName(newName);
                cpmgr.save(cp);
                status = STATUS_SUCCESS;
            } else {
                status = this.failedStatus("new channel name already exist");
            }
        } else {
            status = this.failedStatus("channel record not exist");
        }
        this.responseJSON(status, response);
    }

    private void modeGetChannelPackageInfo(HttpServletRequest request, HttpServletResponse response) {
        String platform = request.getParameter("platform");
        LOG.info("assign platform:{}", (Object)platform);
        ArrayList<ChannelPackage> channelPackages = new ArrayList<ChannelPackage>();
        ChannelPackage noneChannelPackage = new ChannelPackage();
        noneChannelPackage.setId(-1);
        noneChannelPackage.setName("None");
        noneChannelPackage.setPlatform("None");
        noneChannelPackage.setNumberOfChs(0);
        channelPackages.add(noneChannelPackage);
        if (StringUtils.isNotBlank(platform)) {
            channelPackages.addAll(JpaManager.getChannelPackageManager().findChannelPackagesByPlatforms(platform));
        } else {
            channelPackages.addAll(JpaManager.getChannelPackageManager().loadAll());
        }
        try {
            IOUtils.write(new Gson().toJson(channelPackages).getBytes(), (OutputStream)response.getOutputStream());
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void unknownCommand(HttpServletResponse response) {
        String status = STATUS_FAILED;
        try {
            response.setContentType("text/json");
            IOUtils.write(status.getBytes(), (OutputStream)response.getOutputStream());
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private String getChannelJSP(String channelPackageId) {
        return "/jsp/channel/channels2K19.jsp?channelPackageId=" + channelPackageId;
    }
}

