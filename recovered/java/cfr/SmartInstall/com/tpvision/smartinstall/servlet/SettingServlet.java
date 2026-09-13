/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.annotation.WebServlet
 */
package com.tpvision.smartinstall.servlet;

import com.google.gson.Gson;
import com.tpvision.smartinstall.core.SettingChannelBean;
import com.tpvision.smartinstall.core.SettingCreator;
import com.tpvision.smartinstall.dao.core.ChannelPackage;
import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.SettingPackage;
import com.tpvision.smartinstall.dao.core.SmartcmsSetting;
import com.tpvision.smartinstall.dao.core.SmartinfoSetting;
import com.tpvision.smartinstall.dao.mgr.AppPackageManager;
import com.tpvision.smartinstall.dao.mgr.ChannelPackageManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.ScheduleManager;
import com.tpvision.smartinstall.dao.mgr.SettingManager;
import com.tpvision.smartinstall.dao.mgr.SettingPackageManager;
import com.tpvision.smartinstall.dao.mgr.SmartcmsSettingManager;
import com.tpvision.smartinstall.dao.mgr.SmartinfoSettingManager;
import com.tpvision.smartinstall.dao.mgr.UiCustomizationsManager;
import com.tpvision.smartinstall.dao.mgr.WelcomeManager;
import com.tpvision.smartinstall.servlet.BaseHttpServlet;
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.SettingState;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.Utils;
import com.tpvision.smartinstall.xml.Setting;
import com.tpvision.smartinstall.xml.setting.v2k16.roomspecific.Item;
import com.tpvision.smartinstall.xml.setting.v2k16.roomspecific.RoomSpecificSettings;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns={"/setting/*"})
public class SettingServlet
extends BaseHttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(SettingServlet.class);
    private static String snameUpload = "";
    private static Random randomGenerator = new Random();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String overwrite;
        String umode;
        String mode = request.getParameter("mode");
        String sname = request.getParameter("sname");
        String string = umode = request.getAttribute("umode") != null ? (String)request.getAttribute("umode") : null;
        if (null != umode) {
            mode = "SET";
            snameUpload = sname = (String)request.getAttribute("configName");
        }
        if (null != (overwrite = (String)request.getAttribute("overwrite")) && overwrite.equalsIgnoreCase("yes")) {
            request.setAttribute("overwrite", "true");
        }
        if ("SET".equalsIgnoreCase(mode) && "".equals(sname.trim()) || "SET".equalsIgnoreCase(mode) && "null".equals(sname.trim())) {
            response.sendRedirect(request.getContextPath() + "/getFile?mode=index&config_error=config_error");
        } else if ("CHANGESETTINGNAME".equalsIgnoreCase(mode)) {
            this.modeChangeSettingName(request, response);
        } else if ("SET".equalsIgnoreCase(mode)) {
            this.modeSet(request, response);
        } else if ("LIST_SETTING_PROFILE".equals(mode)) {
            this.modeListSettingProfile(request, response);
        } else if ("FIND_SETTING_PROFILE".equals(mode)) {
            this.modeFindSettingProfile(request, response);
        } else if ("updateHotelInfo".equals(mode)) {
            this.modeUpdateHotelInfo(request, response);
        } else if ("DELETE_HOTEL".equals(mode)) {
            this.modeDeleteHotel(request, response);
        } else if ("DELETE_CLONE".equals(mode)) {
            this.modeDeleteClone(request, response);
        } else if ("COPY_CLONE".equals(mode)) {
            this.modeCopyClone(request, response);
        } else if ("CHECK_ONLINE".equals(mode)) {
            HttpSession session = request.getSession();
            long nextInterval = -1L;
            if (session.getMaxInactiveInterval() > 0) {
                nextInterval = (session.getMaxInactiveInterval() + 5) * 1000;
            }
            JSONObject result = new JSONObject();
            result.put("nextCheckInterval", nextInterval).put("id", session.getId());
            Utils.renderSuccessJsonData(result, response);
        } else if ("Clone_Is_Old".equals(mode)) {
            this.modeCloneIsOld(request, response);
        } else if ("ASSIGN_CHANNEL_PACKAGE".equals(mode)) {
            this.modeAssignChannelPackage(request, response);
        }
    }

    protected void modeChangeSettingName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        com.tpvision.smartinstall.dao.core.Setting setting;
        String status = "{\"status\":\"fail\",\"errorCode\":\"-1\"}";
        boolean namefound = false;
        String newname = request.getParameter("name");
        String id = request.getParameter("id");
        SettingManager sm = JpaManager.getSettingManager();
        List<com.tpvision.smartinstall.dao.core.Setting> settings = sm.loadAll();
        ArrayList<String> names = new ArrayList<String>();
        for (int j = 0; settings.size() != j; ++j) {
            names.add(settings.get(j).getClonerename());
            if (!settings.get(j).getClonerename().equalsIgnoreCase(newname)) continue;
            status = "{\"status\":\"fail\",\"errorCode\":\"0\"}";
            namefound = true;
        }
        if (!namefound && null != (setting = sm.loadByKey(Integer.parseInt(id)))) {
            setting.setClonerename(newname);
            sm.save(setting);
            status = "{\"status\":\"success\",\"errorCode\":\"1\"}";
        }
        response.setContentType("text/json");
        IOUtils.write(status.getBytes(), (OutputStream)response.getOutputStream());
    }

    protected void modeAssignChannelPackage(HttpServletRequest request, HttpServletResponse response) {
        ChannelPackageManager cpm;
        ChannelPackage cp;
        String sid = request.getParameter("sid");
        String[] setGroup = sid.split(",");
        String[] fullcpid = request.getParameter("cpid").split("_");
        int cpid = Integer.parseInt(fullcpid[1]);
        if (cpid > 0 && (cp = (cpm = JpaManager.getChannelPackageManager()).loadByKey(cpid)) == null) {
            LOG.error("selected ChannelPackage:{} not exists", (Object)cpid);
            Utils.renderErrorJsonMsg("{\"status\":\"fail\"}", response);
            return;
        }
        SettingManager sm = JpaManager.getSettingManager();
        for (int i = 0; i < setGroup.length; ++i) {
            com.tpvision.smartinstall.dao.core.Setting set = sm.loadByKey(Integer.parseInt(setGroup[i]));
            if (null == set) continue;
            set.setChannelPackageId(cpid);
            set.setLastUpdatedDate(new Date());
            sm.save(set);
        }
        Utils.renderSuccessJsonData(response);
    }

    private SettingChannelBean getMergedSettingChannelBeanBySetting(com.tpvision.smartinstall.dao.core.Setting setting) {
        ChannelPackage channelPackage;
        SettingPackage settingPackage;
        SettingChannelBean scb = new Gson().fromJson(setting.getValue(), SettingChannelBean.class);
        if (setting.getSettingPackageId() > 0 && (settingPackage = JpaManager.getSettingPackageManager().loadByKey(setting.getSettingPackageId())) != null) {
            SettingChannelBean settingPackageBean = new Gson().fromJson(settingPackage.getValue(), SettingChannelBean.class);
            scb.mergeWithSettingPackageBean(settingPackageBean);
        }
        if (setting.getChannelPackageId() > 0 && (channelPackage = JpaManager.getChannelPackageManager().loadByKey(setting.getChannelPackageId())) != null) {
            SettingChannelBean channelPackageBean = new Gson().fromJson(channelPackage.getValue(), SettingChannelBean.class);
            scb.mergeWithChanelPackageBean(channelPackageBean);
        }
        return scb;
    }

    /*
     * Exception decompiling
     */
    protected void modeSet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    protected void modeUpdateHotelInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject msg;
        block22: {
            List<FileItem> items = null;
            msg = new JSONObject();
            msg.put("result", "fail");
            try {
                items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            }
            catch (FileUploadException e1) {
                LOG.error(e1.getMessage(), e1);
            }
            InputStream filecontent = null;
            String file_name = null;
            String id = "null";
            for (FileItem item : items) {
                filecontent = item.getInputStream();
                file_name = item.getName();
            }
            id = Integer.toString(randomGenerator.nextInt(10000));
            byte[] content = IOUtils.toByteArray(filecontent);
            if (null != content && content.length > 0 && null != file_name && (file_name.endsWith("jpg") || file_name.endsWith("JPG") || file_name.endsWith("jpeg") || file_name.endsWith("JPEG"))) {
                String imageName = DigestUtils.md5Hex(id);
                File f = new File(CommonConstants.HOTEL_INFO_IMG_LOCATION + imageName);
                File file = new File(CommonConstants.HOTEL_INFO_THUMB_IMG_LOCATION + imageName);
                FileUtils.writeByteArrayToFile(f, content);
                FileUtils.writeByteArrayToFile(file, content);
                try {
                    BufferedImage image = ImageIO.read(file);
                    if (null == image) break block22;
                    int height = image.getHeight();
                    int width = image.getWidth();
                    if (width == 1920 && height == 1080) {
                        BufferedImage thumbnail = Scalr.resize(image, 160, 90, new BufferedImageOp[0]);
                        ImageIO.write((RenderedImage)thumbnail, "jpg", file);
                        image.flush();
                        thumbnail.flush();
                        msg.put("result", "success");
                        break block22;
                    }
                    FileUtils.deleteQuietly(f);
                    FileUtils.deleteQuietly(file);
                    msg.put("msg", "Only jpeg images of 1920*1080 resolution are allowed");
                }
                catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                }
            } else {
                msg.put("msg", "Only jpeg images are allowed");
            }
        }
        response.setContentType("text/json");
        try (PrintWriter writer = response.getWriter();){
            writer.print(msg.toString());
            writer.flush();
        }
        catch (IOException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    protected void modeCopyClone(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String status = "{\"status\":\"fail\"}";
        String idStr = request.getParameter("id");
        if (null == idStr) {
            return;
        }
        int id = Integer.parseInt(idStr);
        com.tpvision.smartinstall.dao.core.Setting settingSrc = null;
        SettingManager setmgr = JpaManager.getSettingManager();
        settingSrc = setmgr.loadByKey(id);
        if (null != settingSrc) {
            com.tpvision.smartinstall.dao.core.Setting settingDest = new com.tpvision.smartinstall.dao.core.Setting();
            settingDest.setValue(settingSrc.getValue());
            settingDest.setPlatform(settingSrc.getPlatform());
            List<String> currentSettingNames = setmgr.loadAll().stream().map(com.tpvision.smartinstall.dao.core.Setting::getClonerename).map(String::trim).collect(Collectors.toList());
            String rename = CloneItemUtils.getUniqueCloneName(currentSettingNames, "Copy of " + settingSrc.getClonerename());
            settingDest.setClonerename(rename);
            settingDest.setName(rename);
            settingDest.setIsdelete(settingSrc.getIsdelete());
            settingDest.setType(settingSrc.getType());
            settingDest.setCreatedBy(settingSrc.getCreatedBy());
            settingDest.setCreatedDate(settingSrc.getCreatedDate());
            settingDest.setLastUpdatedBy(settingSrc.getLastUpdatedBy());
            settingDest.setLastUpdatedDate(settingSrc.getLastUpdatedDate());
            settingDest.setGeonameId(settingSrc.getGeonameId());
            settingDest.setLanguage(settingSrc.getLanguage());
            settingDest.setAndroidApps(settingSrc.getAndroidApps());
            settingDest.setContent(settingSrc.getContent());
            if (settingSrc.getChannelPackageId() > 0) {
                settingDest.setChannelPackageId(SettingCreator.copyCloneItem(CommonConstants.CloneItemType.ChannelList.name(), settingSrc.getChannelPackageId()));
            }
            if (settingSrc.getAppPackageId() > 0) {
                settingDest.setAppPackageId(SettingCreator.copyCloneItem(CommonConstants.CloneItemType.AndroidApps.name(), settingSrc.getAppPackageId()));
            }
            if (settingSrc.getSettingPackageId() > 0) {
                settingDest.setSettingPackageId(SettingCreator.copyCloneItem(CommonConstants.CloneItemType.TVSettings.name(), settingSrc.getSettingPackageId()));
            }
            if (settingSrc.getUiCustomizationsId() > 0) {
                settingDest.setUiCustomizationsId(SettingCreator.copyCloneItem(CommonConstants.CloneItemType.UiCustomizations.name(), settingSrc.getUiCustomizationsId()));
            }
            if (settingSrc.getBannersId() > 0) {
                settingDest.setBannersId(SettingCreator.copyCloneItem(CommonConstants.CloneItemType.Banner.name(), settingSrc.getBannersId()));
            }
            if (settingSrc.getScheduleId() > 0) {
                settingDest.setScheduleId(SettingCreator.copyCloneItem(CommonConstants.CloneItemType.Schedules.name(), settingSrc.getScheduleId()));
            }
            if (settingSrc.getWelcomeId() > 0) {
                settingDest.setWelcomeId(SettingCreator.copyCloneItem(CommonConstants.CloneItemType.WelcomeLogo.name(), settingSrc.getWelcomeId()));
            }
            settingDest.setCloneItemStatus(settingSrc.getCloneItemStatus());
            setmgr.save(settingDest);
            status = "{\"status\":\"success\"}";
            File srcPath = new File(CommonConstants.CLONE_PROCESS_LOCATION + settingSrc.getName());
            File dirPath = new File(CommonConstants.CLONE_PROCESS_LOCATION + rename);
            FileUtils.copyDirectory(srcPath, dirPath);
        }
        Utils.writeToResponse(status, "text/html;charset=UTF-8", response);
    }

    protected void modeDeleteClone(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String status = "{\"status\":\"fail\"}";
        int id = Integer.parseInt(request.getParameter("id"));
        SmartcmsSettingManager sismgr = JpaManager.getSmartcmsSettingManager();
        List<SmartcmsSetting> cmssettings = sismgr.findSmartcmsSettingBySettingId(id);
        for (int i = 0; i < cmssettings.size(); ++i) {
            sismgr.deleteByKey(cmssettings.get(i).getId());
        }
        List<Devices> relatedDevices = JpaManager.getDevicesManager().findDevicesByCloneId(id, "clone");
        relatedDevices.forEach(device -> {
            device.setCloneid(0);
            device.setCloneColor("black");
            JpaManager.getDevicesManager().save((Devices)device);
        });
        SmartinfoSettingManager suimgr = JpaManager.getSmartinfoSettingManager();
        List<SmartinfoSetting> smartinfoSettings = suimgr.findSmartinfoSettingBySettingId(id);
        for (int i = 0; i < smartinfoSettings.size(); ++i) {
            suimgr.deleteByKey(smartinfoSettings.get(i).getId());
        }
        SettingManager smgr = JpaManager.getSettingManager();
        try {
            com.tpvision.smartinstall.dao.core.Setting setting = smgr.loadByKey(id);
            if (null != setting) {
                this.deletePartialClone(setting);
                smgr.deleteByKey(id);
                File f = new File(CommonConstants.CLONE_PROCESS_LOCATION + setting.getName());
                if (f.exists()) {
                    FileUtils.forceDelete(f);
                }
                SettingCreator.cleanCachedCloneData(setting.getId());
            }
            status = "{\"status\":\"success\"}";
            HttpSession session = request.getSession();
            String clonedataNamme = (String)session.getAttribute("cloneName");
            String settingNamme = (String)session.getAttribute("sname");
            if (null != setting && (setting.getName().equalsIgnoreCase(clonedataNamme) || setting.getName().equalsIgnoreCase(settingNamme))) {
                session.setAttribute("cloneName", null);
                session.setAttribute("sname", null);
            }
        }
        catch (NumberFormatException e) {
            LOG.error(e.getMessage(), e);
        }
        response.setContentType("text/json");
        IOUtils.write(status.getBytes(), (OutputStream)response.getOutputStream());
    }

    protected void deletePartialClone(com.tpvision.smartinstall.dao.core.Setting setting) {
        SettingManager smgr = JpaManager.getSettingManager();
        int settingPackegeId = -1;
        int channelPackageId = -1;
        int appPackageId = -1;
        int welcomePackageId = -1;
        int uiCustomizationsPackageId = -1;
        int schedulePackageId = -1;
        try {
            if (null != setting) {
                List<com.tpvision.smartinstall.dao.core.Setting> settingSchedulePackage;
                List<com.tpvision.smartinstall.dao.core.Setting> settingUiCustomizationsPackage;
                List<com.tpvision.smartinstall.dao.core.Setting> settingWelcomePackage;
                List<com.tpvision.smartinstall.dao.core.Setting> settingChannelPackage;
                String deleteDirectoryStr;
                List<com.tpvision.smartinstall.dao.core.Setting> settingAppPackage;
                List<com.tpvision.smartinstall.dao.core.Setting> settingPackage;
                settingPackegeId = setting.getSettingPackageId();
                if (settingPackegeId > 0 && null != (settingPackage = smgr.findSettingListBySettingPackageId(settingPackegeId)) && settingPackage.size() == 1) {
                    SettingPackageManager settMag = JpaManager.getSettingPackageManager();
                    settMag.deleteByKey(settingPackegeId);
                }
                if ((appPackageId = setting.getAppPackageId()) > 0 && null != (settingAppPackage = smgr.findSettingListByAppPackageId(appPackageId)) && settingAppPackage.size() == 1) {
                    deleteDirectoryStr = CommonConstants.CLONE_PROCESS_LOCATION + "AppPackages" + File.separator + appPackageId;
                    File appPackage = new File(deleteDirectoryStr);
                    if (null != appPackage && appPackage.exists()) {
                        FileUtils.forceDelete(appPackage);
                    }
                    AppPackageManager appMag = JpaManager.getAppPackageManager();
                    appMag.deleteByKey(appPackageId);
                }
                if ((channelPackageId = setting.getChannelPackageId()) > 0 && null != (settingChannelPackage = smgr.findSettingListByChannelPackageId(channelPackageId)) && settingChannelPackage.size() == 1) {
                    deleteDirectoryStr = CommonConstants.CLONE_PROCESS_LOCATION + "ChannelPackages" + File.separator + channelPackageId;
                    File channelPackage = new File(deleteDirectoryStr);
                    if (null != channelPackage && channelPackage.exists()) {
                        FileUtils.forceDelete(channelPackage);
                    }
                    ChannelPackageManager channMag = JpaManager.getChannelPackageManager();
                    channMag.deleteByKey(channelPackageId);
                }
                if ((welcomePackageId = setting.getWelcomeId()) > 0 && null != (settingWelcomePackage = smgr.findSettingListByWelcomeId(welcomePackageId)) && settingWelcomePackage.size() == 1) {
                    deleteDirectoryStr = CommonConstants.CLONE_PROCESS_LOCATION + "Welcome" + File.separator + welcomePackageId;
                    File welcomePackageFile = new File(deleteDirectoryStr);
                    if (null != welcomePackageFile && welcomePackageFile.exists()) {
                        FileUtils.forceDelete(welcomePackageFile);
                    }
                    WelcomeManager welcomeMag = JpaManager.getWelcomeManager();
                    welcomeMag.deleteByKey(welcomePackageId);
                }
                if ((uiCustomizationsPackageId = setting.getUiCustomizationsId()) > 0 && null != (settingUiCustomizationsPackage = smgr.findSettingListByUiCustomizationsId(uiCustomizationsPackageId)) && settingUiCustomizationsPackage.size() == 1) {
                    deleteDirectoryStr = CommonConstants.CLONE_PROCESS_LOCATION + "uiCustomizations" + File.separator + uiCustomizationsPackageId;
                    File uiCustomizationsFile = new File(deleteDirectoryStr);
                    if (null != uiCustomizationsFile && uiCustomizationsFile.exists()) {
                        FileUtils.forceDelete(uiCustomizationsFile);
                    }
                    UiCustomizationsManager uiCustomizationsMag = JpaManager.getUiCustomizationsManager();
                    uiCustomizationsMag.deleteByKey(uiCustomizationsPackageId);
                }
                if ((schedulePackageId = setting.getScheduleId()) > 0 && null != (settingSchedulePackage = smgr.findSettingListByScheduleId(schedulePackageId)) && settingSchedulePackage.size() == 1) {
                    ScheduleManager scheduleMag = JpaManager.getScheduleManager();
                    scheduleMag.deleteByKey(schedulePackageId);
                }
            }
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    protected void modeDeleteHotel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        String status = "{\"status\":\"fail\"}";
        try {
            File workFolder = new File(CommonConstants.HOTEL_INFO_IMG_LOCATION);
            for (File f : workFolder.listFiles()) {
                if (!f.getName().equalsIgnoreCase(id)) continue;
                FileUtils.deleteQuietly(f);
            }
            File workFolderThumb = new File(CommonConstants.HOTEL_INFO_THUMB_IMG_LOCATION);
            if (workFolderThumb.length() > 0L) {
                for (File f : workFolderThumb.listFiles()) {
                    if (!f.getName().equalsIgnoreCase(id)) continue;
                    FileUtils.deleteQuietly(f);
                    status = "{\"status\":\"success\"}";
                }
            }
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        response.setContentType("text/json");
        IOUtils.write(status.getBytes(), (OutputStream)response.getOutputStream());
    }

    protected void modeListSettingProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SettingManager smgr = JpaManager.getSettingManager();
        List<com.tpvision.smartinstall.dao.core.Setting> settings = smgr.loadAll();
        StringBuilder sbuild = new StringBuilder();
        sbuild.append("{setting:[");
        for (com.tpvision.smartinstall.dao.core.Setting s : settings) {
            sbuild.append("\"").append(s.getName()).append("\"").append(",");
        }
        sbuild.append("\"").append("\"");
        sbuild.append("]}");
        response.setContentType("text/json");
        IOUtils.write(sbuild.toString().getBytes(), (OutputStream)response.getOutputStream());
    }

    protected void modeFindSettingProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SettingManager smgr = JpaManager.getSettingManager();
        String settingName = request.getParameter("sname");
        String status = "{\"status\":\"success\"}";
        List<com.tpvision.smartinstall.dao.core.Setting> settings = smgr.findSettingsByName(settingName);
        if (null == settings || settings.isEmpty()) {
            status = "{\"status\":\"fail\"}";
        }
        response.setContentType("text/json");
        IOUtils.write(status.getBytes(), (OutputStream)response.getOutputStream());
    }

    private void setWelcomeMsg(SettingChannelBean scb, HttpServletRequest request) {
        ArrayList<String> lines = new ArrayList<String>();
        boolean isEasySuite = false;
        if (null == scb) {
            return;
        }
        if (scb.getSetttings() == null) {
            return;
        }
        if (scb.getSetttings().getSetting() == null) {
            return;
        }
        if (scb.getSetttings().getSetting().size() < 1) {
            return;
        }
        for (Setting setting : scb.getSetttings().getSetting()) {
            if (setting.getRefFile() != null && (setting.getRefFile() == null || !setting.getRefFile().equalsIgnoreCase("ES2K12")) || setting.getXaddr() == null || !setting.getXaddr().equalsIgnoreCase("ES2K12")) continue;
            isEasySuite = true;
            break;
        }
        HashMap<String, String> settingItemToLastValueMap = new HashMap<String, String>();
        for (Setting s : scb.getSetttings().getSetting()) {
            settingItemToLastValueMap.put(s.getItem(), s.getLastValue());
        }
        if (!isEasySuite) {
            boolean bl = false;
            for (int i = 1; i < 3; ++i) {
                StringBuilder sbuild = new StringBuilder();
                for (int j = 0; j < 20; ++j) {
                    String key = "HMWelcomeMessageLine" + i + "Char" + j;
                    String value = (String)settingItemToLastValueMap.get(key);
                    if (null == value || null != value && value.equals("0") || null == value || value.trim().equals("")) continue;
                    char c = (char)Integer.parseInt(value);
                    sbuild.append(Character.toString(c));
                }
                lines.add(sbuild.toString());
            }
            request.setAttribute("WELCOME_MSG_LINE_1", lines.get(0));
            request.setAttribute("WELCOME_MSG_LINE_2", lines.get(1));
        } else {
            request.setAttribute("WELCOME_MSG_LINE_1", settingItemToLastValueMap.get("WelcomeMsgLine1"));
            request.setAttribute("WELCOME_MSG_LINE_2", settingItemToLastValueMap.get("WelcomeMsgLine2"));
        }
    }

    private void setClockChannel(SettingChannelBean scb, HttpServletRequest request) {
        String toClockChannel = request.getParameter("cs_dnprogram");
        if (null != toClockChannel && !toClockChannel.trim().equals("")) {
            for (Setting s : scb.getSetttings().getSetting()) {
                if (null != s && s.getItem() != null && s.getItem().equalsIgnoreCase("HMClockChannelOnePartNo")) {
                    s.setLastValue(toClockChannel);
                }
                if (null != s && s.getItem() != null && s.getItem().equalsIgnoreCase("HMClockChannelAnalogNo")) {
                    s.setLastValue(toClockChannel);
                }
                if (null != s && s.getItem() != null && s.getItem().equalsIgnoreCase("HMClockChannelDigit")) {
                    s.setLastValue(toClockChannel);
                }
                if (null == s || s.getItem() == null || !s.getItem().equalsIgnoreCase("HMClockChannelTwoPartMajorNo")) continue;
                s.setLastValue(toClockChannel);
            }
        }
    }

    private void setSwitchOnSource(SettingChannelBean scb, HttpServletRequest request) {
        SettingState ss = new SettingState(scb);
        Map<String, String> settingToValueMap = ss.getSettingToValuesMap();
        String toSetHMOnChannelSrc = settingToValueMap.get(SettingState.ParamConverter.instance().getSettingName("switchon_source"));
        String toSetChannelNumber = settingToValueMap.get(SettingState.ParamConverter.instance().getSettingName("switchon_source_no"));
        boolean shouldSetChannelNumber = true;
        if (null == toSetChannelNumber || null == toSetHMOnChannelSrc || null != toSetHMOnChannelSrc && !toSetHMOnChannelSrc.equalsIgnoreCase("1")) {
            shouldSetChannelNumber = false;
        }
        for (Setting s : scb.getSetttings().getSetting()) {
            if (null != s && s.getItem() != null && s.getItem().equalsIgnoreCase("HMOnchannelSrc")) {
                s.setLastValue(toSetHMOnChannelSrc);
            }
            if (!shouldSetChannelNumber) continue;
            if (null != s && s.getItem() != null && s.getItem().equalsIgnoreCase("HMOnChannelAnalogNo")) {
                s.setLastValue(toSetChannelNumber);
            }
            if (null != s && s.getItem() != null && s.getItem().equalsIgnoreCase("HMOnChannelDigit")) {
                s.setLastValue(toSetChannelNumber);
            }
            if (null != s && s.getItem() != null && s.getItem().equalsIgnoreCase("HMOnChannelTwoPartMajorNo")) {
                s.setLastValue(toSetChannelNumber);
            }
            if (null == s || s.getItem() == null || !s.getItem().equalsIgnoreCase("HMOnChannelOnePartNo")) continue;
            s.setLastValue(toSetChannelNumber);
        }
    }

    private void setDiagnosticAnalytic(SettingChannelBean scb, HttpServletRequest request) {
    }

    private void modeCloneIsOld(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean UPLOAD_CLONE_IS_OLD = true;
        int EDIT_CLONE_IS_WRONG = 2;
        int error_type = 0;
        String msg = "{\"error_type\":\"0\"}";
        String flag_error = request.getParameter("flag_error");
        String sname = request.getParameter("sname");
        if (flag_error.equalsIgnoreCase("1")) {
            error_type = this.checkCloneIsOld(snameUpload, 1);
        } else if (flag_error.equalsIgnoreCase("2")) {
            error_type = this.checkCloneIsOld(sname, 2);
        }
        msg = 1 == error_type ? "{\"error_type\":\"1\"}" : (2 == error_type ? "{\"error_type\":\"2\"}" : "{\"error_type\":\"0\"}");
        request.setAttribute("flag_error", "0");
        response.setContentType("text/json");
        IOUtils.write(msg.getBytes(), (OutputStream)response.getOutputStream());
    }

    private int checkCloneIsOld(String sname, int index) {
        if (index == 1) {
            SettingManager smgr = JpaManager.getSettingManager();
            List<com.tpvision.smartinstall.dao.core.Setting> settings = smgr.findSettingsByName(sname);
            if (!settings.isEmpty()) {
                smgr.deleteByKey(settings.get(0).getId());
                return 1;
            }
        } else {
            return 2;
        }
        return 0;
    }

    private String getRoomSepcificSettings(RoomSpecificSettings rss) {
        if (null == rss || rss.getTV() == null) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("serialNumber", rss.getTV().getSerialNumber());
        List<Item> items = rss.getTV().getItem();
        for (Item item : items) {
            String name = "";
            if ("Advanced.Identification Settings.RoomID".equalsIgnoreCase(item.getName())) {
                name = "RoomID";
            } else if ("Features.MultiRemoteControl".equalsIgnoreCase(item.getName())) {
                name = "MultiRemoteControl";
            }
            jsonObject.put(name, item.getValue());
        }
        return jsonObject.toString();
    }

    private void setRoomSepcificSettings(RoomSpecificSettings rss, HttpServletRequest request) {
        String roomId = request.getParameter("RoomID");
        String multiRemoteControl = request.getParameter("multiRemoteControl");
        if (null == rss || rss.getTV() == null) {
            return;
        }
        List<Item> items = rss.getTV().getItem();
        for (Item item : items) {
            String value = "";
            if ("Advanced.Identification Settings.RoomID".equalsIgnoreCase(item.getName())) {
                value = roomId;
            } else if ("Features.MultiRemoteControl".equalsIgnoreCase(item.getName()) && null != multiRemoteControl) {
                value = multiRemoteControl;
            }
            item.setValue(value);
        }
    }

    private void updateSettingIdentifier(String cloneName, Date updatedDate, String platform) {
        String srcPath = CommonConstants.CLONE_PROCESS_LOCATION + cloneName + "/" + platform + "/";
        File rootFile = new File(srcPath);
        if (!rootFile.exists()) {
            return;
        }
        if (platform.indexOf("TPN16") > -1 || platform.indexOf("TPM153") > -1 || platform.indexOf("TPM181") > -1) {
            srcPath = srcPath + "MasterCloneData/TVSettings/TVSettings_Identifier.txt";
        } else if (platform.indexOf("TPN14") > -1 || platform.indexOf("T911") > -1 || platform.indexOf("TPM1012") > -1) {
            platform = platform.substring(0, platform.indexOf(95));
            srcPath = srcPath + platform + "_SSB_Identifier.txt";
        } else if (platform.indexOf("Q55") != -1) {
            return;
        }
        File settingIdentifierFile = new File(srcPath);
        try {
            FileUtils.writeStringToFile(settingIdentifierFile, TpvDateUtils.getCurrentIndentifierFormatTime(), StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}

