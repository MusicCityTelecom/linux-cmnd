/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.annotation.WebServlet
 */
package com.tpvision.smartinstall.servlet;

import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.SettingManager;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.ContentUtils;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.TpvFileUtils;
import com.tpvision.smartinstall.util.ZipCommonUtils;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(value={"/assign"})
public class AssignContentServlet
extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(AssignContentServlet.class);

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        String mode = request.getParameter("mode");
        if ("get".equals(mode)) {
            this.modeGet(request, response);
        } else if ("save".equals(mode)) {
            this.modeSave(request, response);
        }
    }

    private void modeGet(HttpServletRequest request, HttpServletResponse response) {
        String data = this.getThumbnailList();
        try {
            response.setContentType("text/json");
            IOUtils.write(data.getBytes(), (OutputStream)response.getOutputStream());
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void modeSave(HttpServletRequest request, HttpServletResponse response) {
        String title = request.getParameter("title");
        String contentId = request.getParameter("contentId");
        String cloneid = request.getParameter("cloneid");
        String status = "{\"status\":\"success\"}";
        SettingManager settingManager = JpaManager.getSettingManager();
        Setting setting = settingManager.loadByKey(Integer.valueOf(cloneid));
        try {
            if (null == setting) {
                throw new IOException("Setting not found");
            }
            String cloneName = setting.getName();
            if ("None".equalsIgnoreCase(contentId)) {
                setting.setContent("");
                this.removeWebsiteFromClone(cloneName);
                status = "{\"status\":\"setNone\"}";
            } else {
                this.copyWebsiteToClone(setting, contentId);
                setting.setContent(title);
            }
            settingManager.save(setting);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            status = "{\"status\":\"fail\"}";
        }
        response.setContentType("text/json");
        try {
            IOUtils.write(status.getBytes(), (OutputStream)response.getOutputStream());
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void removeWebsiteFromClone(String sname) throws IOException {
        String smartInfoBrowserLocationStr = this.getSmartInfoBrowserLocation(sname);
        File existfile = new File(smartInfoBrowserLocationStr);
        if (existfile.exists() || existfile.isDirectory()) {
            FileUtils.forceDelete(existfile);
        }
        LOG.info("AssginContentServlet: remove Webstie From Clone successfully!");
    }

    public String getThumbnailList() {
        String cmsData = ContentUtils.getContentList();
        return this.sortThumbnailList(cmsData);
    }

    private String sortThumbnailList(String jsonArrStr) {
        int i;
        JSONArray jsonArr = new JSONArray(jsonArrStr);
        JSONArray sortedJsonArray = new JSONArray();
        ArrayList<JSONObject> jsonValues = new ArrayList<JSONObject>();
        for (i = 0; i < jsonArr.length(); ++i) {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            jsonValues.add(jsonObj);
        }
        Collections.sort(jsonValues, new Comparator<JSONObject>(){
            private static final String TITLE = "title";
            private static final String THUMBNAIL = "thumbnail";

            @Override
            public int compare(JSONObject a, JSONObject b) {
                String valA = null;
                String valB = null;
                try {
                    valA = (String)a.get(TITLE);
                    valB = (String)b.get(TITLE);
                }
                catch (JSONException e) {
                    LOG.error(e.getMessage(), e);
                }
                if (null == valA) {
                    return -1;
                }
                if (null == valB) {
                    return 1;
                }
                if (valA.compareTo(valB) > 0) {
                    return 1;
                }
                if (valA.compareTo(valB) == 0) {
                    valA = (String)a.get(THUMBNAIL);
                    if (valA.compareTo(valB = (String)b.get(THUMBNAIL)) > 0) {
                        return 1;
                    }
                    return -1;
                }
                return -1;
            }
        });
        for (i = 0; i < jsonArr.length(); ++i) {
            sortedJsonArray.put(jsonValues.get(i));
        }
        return sortedJsonArray.toString();
    }

    private void copyWebsiteToClone(Setting setting, String id) throws IOException {
        String destZipFileStr = ContentUtils.downloadContentZip(id);
        File storeZipFile = new File(destZipFileStr);
        String cloneName = setting.getName();
        String platform = setting.getPlatform();
        String unZipSmartInfoBrowserLocationStr = this.getSmartInfoBrowserLocation(cloneName);
        File existfile = new File(unZipSmartInfoBrowserLocationStr);
        String txtName = PlatformUtils.getSmartinfoIdentifierTxtName(platform);
        File srcFile = null;
        if (!StringUtils.isEmpty(txtName)) {
            srcFile = new File(unZipSmartInfoBrowserLocationStr + txtName);
        }
        if (existfile.exists() || existfile.isDirectory()) {
            FileUtils.forceDelete(existfile);
        }
        ZipCommonUtils.unzip(storeZipFile, unZipSmartInfoBrowserLocationStr);
        try {
            this.getIdentifierContent(id, srcFile, setting);
        }
        catch (NullPointerException e) {
            LOG.error(e.getMessage(), e);
        }
        FileUtils.deleteQuietly(storeZipFile);
        LOG.info("AssginContentServlet:delete {} successfully!", (Object)storeZipFile.getName());
    }

    private void getIdentifierContent(String id, File srcFile, Setting set) {
        if (srcFile != null) {
            String identifierContent = ContentUtils.getSmartInfoChangedTime(id);
            try {
                FileUtils.writeStringToFile(srcFile, identifierContent, StandardCharsets.UTF_8);
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
        set.setLastUpdatedDate(new Date());
    }

    private String getSmartInfoBrowserLocation(String sname) {
        String smartInfoBrowserLocaton = CommonConstants.CLONE_PROCESS_LOCATION + sname;
        String smartInfoBrowserLocatonPart2 = this.getSmartInfoBrowserLocatonPart2(smartInfoBrowserLocaton);
        smartInfoBrowserLocaton = smartInfoBrowserLocaton + "/" + smartInfoBrowserLocatonPart2;
        return smartInfoBrowserLocaton;
    }

    private String getSmartInfoBrowserLocatonPart2(String smartInfoBrowserLocaton) {
        String platform = null;
        String ret = null;
        File unZipCloneFile = new File(smartInfoBrowserLocaton);
        if (unZipCloneFile.exists()) {
            File[] platformDirectory = unZipCloneFile.listFiles();
            if (null == platformDirectory || platformDirectory.length != 1) {
                LOG.error("AssginContentServlet:clone path: {} is corrupted", (Object)smartInfoBrowserLocaton);
                throw new RuntimeException("clone path is corrupted");
            }
            platform = platformDirectory[0].getName();
            String smartinfoDirName = PlatformUtils.getSmartinfoDirctoryByPlatform(platform);
            if (StringUtils.isEmpty(smartinfoDirName)) {
                LOG.error("AssginContentServlet:Unable to support CloneData : {}", (Object)platform);
                throw new RuntimeException("Unable to support CloneData");
            }
            boolean isHaveMasterCloneDataDir = TpvFileUtils.getDirectoryByName(platformDirectory[0], "MasterCloneData") != null;
            ret = isHaveMasterCloneDataDir ? "/MasterCloneData/" + smartinfoDirName : "/" + smartinfoDirName;
        }
        return platform + ret;
    }
}

