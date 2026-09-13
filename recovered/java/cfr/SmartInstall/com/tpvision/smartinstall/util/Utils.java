/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import com.google.gson.Gson;
import com.tpvision.smartinstall.dao.core.Profile;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.core.SettingPackage;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.ProfileManager;
import com.tpvision.smartinstall.dao.mgr.SettingManager;
import com.tpvision.smartinstall.servlet.IPProfile;
import com.tpvision.smartinstall.servlet.IPProfileConfig;
import com.tpvision.smartinstall.util.AdminPageTab;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.Setting2K14ESPageTab;
import com.tpvision.smartinstall.util.Setting2K14MSPageTab;
import com.tpvision.smartinstall.util.Setting2K16ESPageTab;
import com.tpvision.smartinstall.util.Setting2K16SSMSPageTab;
import com.tpvision.smartinstall.util.SiIdentifiers;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.TpvFileUtils;
import com.tpvision.smartinstall.util.TpvStringUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utils {
    private static final Logger LOG = LoggerFactory.getLogger(Utils.class);
    private static final Utils SINGLE_INSTANCE = new Utils();
    static AdminPageTab mAdminPageTab = new AdminPageTab();
    static Setting2K16SSMSPageTab m2K16SSMSSettingPageTab = new Setting2K16SSMSPageTab();
    static Setting2K16ESPageTab m2K16ESSettingPageTab = new Setting2K16ESPageTab();
    static Setting2K14MSPageTab m2K14MSSettingPageTab = new Setting2K14MSPageTab();
    static Setting2K14ESPageTab m2K14ESSettingPageTab = new Setting2K14ESPageTab();
    static ServletContext servletContext;

    public static ServletContext getServletContext() {
        return servletContext;
    }

    public static void setServletContext(ServletContext servletContext) {
        Utils.servletContext = servletContext;
    }

    public static Utils instance() {
        return SINGLE_INSTANCE;
    }

    public void save2K16SSMSSettingTabIndex(String id) {
        m2K16SSMSSettingPageTab.setTabId(id);
    }

    public static String get2K16SSMSSettingTabIndex() {
        return m2K16SSMSSettingPageTab.getTabId();
    }

    public void save2K16ESSettingTabIndex(String id) {
        m2K16ESSettingPageTab.setTabId(id);
    }

    public static String get2K16ESSettingTabIndex() {
        return m2K16ESSettingPageTab.getTabId();
    }

    public void save2K14MSSettingTabIndex(String id) {
        m2K14MSSettingPageTab.setTabId(id);
    }

    public static String get2K14MSSettingTabIndex() {
        return m2K14MSSettingPageTab.getTabId();
    }

    public void save2K14ESSettingTabIndex(String id) {
        m2K14ESSettingPageTab.setTabId(id);
    }

    public static String get2K14ESSettingTabIndex() {
        return m2K14ESSettingPageTab.getTabId();
    }

    public String getProductName(HttpSession session) {
        String platformName = "";
        if (session.getAttribute("platformName") != null) {
            platformName = session.getAttribute("platformName").toString().trim();
            if ("2K14MS".equalsIgnoreCase(platformName)) {
                platformName = "2K14/2K15-MS";
            } else if ("2K14ES".equalsIgnoreCase(platformName)) {
                platformName = "2K14/2K15-ES";
            } else if ("2K15MS".equalsIgnoreCase(platformName)) {
                platformName = "2016 MS";
            }
        }
        return platformName;
    }

    public String getCloneName(HttpServletRequest request, HttpSession session) {
        String sname = "";
        sname = session.getAttribute("cloneName") != null ? session.getAttribute("cloneName").toString().trim() : (String)request.getAttribute("configName");
        if (null == sname || sname.equalsIgnoreCase("")) {
            sname = request.getParameter("sname") != null ? request.getParameter("sname") : (String)request.getAttribute("sname");
        }
        return sname;
    }

    public String getCloneDisplayName(String sname) {
        SettingManager smgr;
        List<Setting> settings;
        String cloneDisplayName = "";
        if (null != sname && !sname.trim().equals("") && !(settings = (smgr = JpaManager.getSettingManager()).findSettingsByName(sname)).isEmpty()) {
            cloneDisplayName = settings.get(0).getClonerename();
        }
        return cloneDisplayName;
    }

    public static void updateUserProfileConfig(Map<String, String> params) {
        String userName = Utils.getAuthenticationName().trim();
        ProfileManager profileManager = JpaManager.getProfileManager();
        Profile profile = profileManager.loadByKey(userName);
        if (null != profile) {
            String profileConfig = profile.getConfig();
            IPProfileConfig ipProfileConfig = IPProfileConfig.fromJson(profileConfig);
            ipProfileConfig.updateConfigs(params);
            profile.setConfig(new Gson().toJson(ipProfileConfig));
            profileManager.save(profile);
        } else {
            LOG.error("user login error or authority failed");
        }
    }

    public static IPProfileConfig getUserConfig() {
        String currentUserName = Utils.getAuthenticationName();
        ProfileManager profileManager = JpaManager.getProfileManager();
        Profile profile = profileManager.loadByKey(currentUserName);
        return IPProfileConfig.fromJson(profile.getConfig());
    }

    public static String getCMNDBuildVersion(ServletContext sc) {
        String version = "7.0.1";
        String title = "CMND_Control";
        String manifestPath = "/META-INF/MANIFEST.MF";
        if ("".equals(CommonConstants.FULL_SI_SERVER_VER)) {
            try (InputStream manifestStream = sc.getResourceAsStream(manifestPath);){
                Manifest manifest = new Manifest(manifestStream);
                if (manifest.getMainAttributes() != null) {
                    title = manifest.getMainAttributes().getValue("Implementation-Title");
                    CommonConstants.FULL_SI_SERVER_VER = version = manifest.getMainAttributes().getValue("Implementation-Version");
                } else {
                    LOG.info("manifest is null");
                }
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        } else {
            version = CommonConstants.FULL_SI_SERVER_VER;
        }
        LOG.debug("title:{},version:{}", (Object)title, (Object)version);
        return version;
    }

    public static String getCMNDMajorVersion() {
        String version = "7.0.1";
        String versionPath = CommonConstants.TOMCAT_WD + "/webapps/version.txt";
        File versionFile = new File(versionPath);
        if (versionFile.exists()) {
            try {
                String sb = FileUtils.readFileToString(versionFile, StandardCharsets.UTF_8);
                if (sb.split("<CMND>").length > 0 && sb.split("<CMND>")[1].split("</CMND>").length > 0) {
                    version = sb.split("<CMND>")[1].split("</CMND>")[0];
                }
            }
            catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        LOG.debug("version:{}", (Object)version);
        return version;
    }

    public static String getVersion(String version) {
        Pattern p1 = Pattern.compile("\\s*|\t|\r|\n|null");
        Matcher m = p1.matcher(version);
        String str = version = m.replaceAll("");
        Pattern p2 = Pattern.compile(".", 16);
        String[] lineSplit = p2.split(str);
        if (lineSplit.length == 4) {
            if (lineSplit[2].length() > 3) {
                String r = lineSplit[2].substring(0, 3).trim();
                String l = String.valueOf(Integer.parseInt(lineSplit[2].substring(3, 4).trim(), 16));
                char[] arr = l.toCharArray();
                int sum = 0;
                for (char c : arr) {
                    sum += Integer.parseInt("" + c + "");
                }
                String f = String.valueOf(Integer.parseInt(r) + sum);
                switch (f.length()) {
                    case 0: {
                        lineSplit[2] = "000";
                        break;
                    }
                    case 1: {
                        lineSplit[2] = "00" + f;
                        break;
                    }
                    case 2: {
                        lineSplit[2] = "0" + f;
                        break;
                    }
                    default: {
                        lineSplit[2] = f;
                    }
                }
            }
            version = Integer.parseInt(lineSplit[1]) < 10 ? lineSplit[1].substring(2, 3) + "." + lineSplit[2] : "1." + lineSplit[1];
        }
        LOG.info(" version > {}", (Object)version);
        return version;
    }

    public static String getFolder(String version) {
        double versionDb = 0.0;
        String versionFolder = null;
        if ("".equalsIgnoreCase(version = Utils.getVersion(version))) {
            versionFolder = "9999";
        } else {
            try {
                versionDb = Double.parseDouble(version) * 1000.0;
                versionFolder = String.valueOf(Math.round(versionDb));
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
                versionFolder = "9999";
            }
        }
        LOG.info(" Folder > {}", (Object)versionFolder);
        return versionFolder;
    }

    public static JSONArray reSortJSONArray(JSONArray jsonArr) {
        if (null != jsonArr) {
            JSONArray sortArr = Utils.sortSimpleJSONArray(jsonArr, "Priority", "int", "asc");
            int sortArrLen = sortArr.length();
            for (int i = 0; i < sortArrLen; ++i) {
                JSONObject srcObj = (JSONObject)sortArr.get(i);
                if (null == srcObj) continue;
                srcObj.put("Priority", i + 1);
            }
        }
        return jsonArr;
    }

    public static JSONArray sortSimpleJSONArray(JSONArray array, String sortItem, String sortType, String sortDire) {
        JSONArray sortJSONArray = new JSONArray();
        if (null != sortDire) {
            ArrayList<JSONObject> list = new ArrayList<JSONObject>();
            JSONObject jsonObj = null;
            int beforeSortSize = array.length();
            for (int i = 0; i < beforeSortSize; ++i) {
                jsonObj = (JSONObject)array.get(i);
                list.add(jsonObj);
            }
            if ("asc".equalsIgnoreCase(sortDire) || "desc".equalsIgnoreCase(sortDire)) {
                Collections.sort(list, new SortComparatorForJSONUtils(sortItem, sortType, sortDire));
                int afterSortSize = list.size();
                for (int j = 0; j < afterSortSize; ++j) {
                    jsonObj = (JSONObject)list.get(j);
                    sortJSONArray.put(jsonObj);
                }
            } else {
                sortJSONArray = array;
            }
        } else {
            sortJSONArray = array;
        }
        return sortJSONArray;
    }

    public static boolean isAdmin() {
        boolean ret = false;
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return ret;
        }
        for (GrantedAuthority grantedAuthority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            if (!grantedAuthority.getAuthority().equalsIgnoreCase("ROLE_ADMIN")) continue;
            ret = true;
            break;
        }
        return ret;
    }

    public static String getAuthenticationName() {
        String authName = "admin";
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (null == auth) {
            return "admin";
        }
        if (auth.getName() != null) {
            authName = auth.getName().trim();
        }
        return authName;
    }

    public static void renderSuccessJsonData(HttpServletResponse response) {
        Utils.writeToResponse("{\"status\":\"success\"}", "text/json;charset=UTF-8", response);
    }

    public static void renderSuccessJsonData(JSONArray data, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject("{\"status\":\"success\"}");
        if (data != null) {
            jsonObject.put("data", data);
        }
        LOG.info("render msg==>{}", (Object)jsonObject);
        Utils.writeToResponse(jsonObject.toString(), "text/json;charset=UTF-8", response);
    }

    public static void renderSuccessJsonData(JSONObject data, HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject("{\"status\":\"success\"}");
        if (data != null) {
            jsonObject.put("data", data);
        }
        LOG.info("render msg==>{}", (Object)jsonObject);
        Utils.writeToResponse(jsonObject.toString(), "text/json;charset=UTF-8", response);
    }

    public static void renderErrorJsonMsg(String msg, HttpServletResponse response) {
        Utils.writeToResponse(Utils.failedStatus(msg), "text/json;charset=UTF-8", response);
    }

    public static void writeJsonToResponse(String data, HttpServletResponse response) {
        Utils.writeToResponse(data, "text/json;charset=UTF-8", response);
    }

    public static void writeToResponse(String data, String contentType, HttpServletResponse response) {
        if (null == data || 0 == data.length()) {
            response.setStatus(200);
            return;
        }
        response.setContentType(contentType);
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter writer = response.getWriter();){
            writer.write(data);
            writer.flush();
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static String failedStatus(String reason) {
        return "{\"status\":\"fail\", \"reason\" :\"" + reason + "\", \"msg\" :\"" + reason + "\"}";
    }

    public static Properties updateProductPropties(String key, String value) {
        Properties properties = new Properties();
        String configPath = servletContext.getInitParameter("productDisabled");
        try (InputStream is = servletContext.getResourceAsStream(configPath);){
            properties.load(is);
            properties.setProperty(key, value);
            try (FileOutputStream oFile = new FileOutputStream(servletContext.getRealPath("/") + configPath);){
                properties.store(oFile, "");
            }
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return properties;
    }

    public static Properties getProductPropties() {
        Properties properties = new Properties();
        String configPath = servletContext.getInitParameter("productDisabled");
        try (InputStream is = servletContext.getResourceAsStream(configPath);){
            properties.load(is);
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return properties;
    }

    public static void deployImgFileToSIServiceDir(File logo, String ext) throws IOException {
        File file;
        String imageName = TpvFileUtils.getImageMd5(logo) + "." + ext;
        File f = new File(CommonConstants.UI_LOGO_IMG_LOCATION + imageName);
        if (!f.exists()) {
            FileUtils.copyFile(logo, f);
        }
        if (!(file = new File(CommonConstants.servletContextPath + "/static/images/uiCustomizations/logo/" + imageName)).exists()) {
            TpvFileUtils.exportThumbnailFile(f, file);
        }
    }

    public static String buildSuccessReturnJson(String key, String value) {
        JSONObject errorResult = new JSONObject("{\"status\":\"success\"}");
        errorResult.put(key, value);
        return errorResult.toString();
    }

    public static String buildSuccessReturnJson(String key, List<String> value) {
        JSONObject errorResult = new JSONObject("{\"status\":\"success\"}");
        errorResult.put(key, value);
        return errorResult.toString();
    }

    public static long getMaxOnlineExpireMilSecs() {
        long fastModePollingMinutes = 10L;
        try {
            fastModePollingMinutes = Long.parseLong(IPProfile.loadIPProfile().getFastMode());
        }
        catch (Exception exception) {
            // empty catch block
        }
        return 3L * fastModePollingMinutes * 60L * 1000L;
    }

    public static boolean isWebServiceUrlError(long maxOnlineExpireMilSecs, String powerStatus, String tvIpAddress, String lastonline, String type) {
        Date lastOnlineDate;
        if (!"offline".equalsIgnoreCase(powerStatus) && StringUtils.isNotBlank(lastonline) && !"RF".equalsIgnoreCase(tvIpAddress) && PlatformUtils.isSupportCorrectWebServiceUrl(type) && (lastOnlineDate = TpvDateUtils.convertCreateUpdateStringToDate(lastonline)) != null) {
            return System.currentTimeMillis() - lastOnlineDate.getTime() > maxOnlineExpireMilSecs;
        }
        return false;
    }

    public static boolean isTvSettingNotMatchBetweenTvAndSIServer(String powerStatus, String lastSuccessSettingPackageId, String siIdentifiers, String tvIpAddress, String type) {
        SiIdentifiers identifiers;
        String tvSettingVersionNo;
        if (!"offline".equalsIgnoreCase(powerStatus) && StringUtils.isNumeric(lastSuccessSettingPackageId) && !"RF".equalsIgnoreCase(tvIpAddress) && PlatformUtils.isSupportCorrectDifferentTVSettings(type) && (tvSettingVersionNo = (identifiers = SiIdentifiers.fromJson(siIdentifiers)).getCloneItemVersionFromTvResponseItems("TVSettings")) != null) {
            int intSettingPackageId = TpvStringUtils.tryParseInt(lastSuccessSettingPackageId, -1);
            SettingPackage settingPackage = JpaManager.getSettingPackageManager().loadByKey(intSettingPackageId);
            if (settingPackage != null) {
                return !StringUtils.equalsIgnoreCase(settingPackage.getLastEdit(), tvSettingVersionNo);
            }
        }
        return false;
    }

    public static String buildFailReturnJson() {
        return "{\"status\":\"fail\"}";
    }

    public static String buildFailReturnJson(String reason) {
        JSONObject errorResult = new JSONObject("{\"status\":\"fail\"}");
        errorResult.put("reason", reason);
        return errorResult.toString();
    }

    public static void renderFileResponse(File file, HttpServletResponse response) {
        response.setContentType(new MimetypesFileTypeMap().getContentType(file));
        try (ServletOutputStream outStream = response.getOutputStream();
             FileInputStream fis = new FileInputStream(file);){
            byte[] data = new byte[1000];
            while (fis.read(data) > 0) {
                outStream.write(data);
            }
            outStream.write(data);
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static void responseZipFile(File zipFile, String displayName, HttpServletResponse response) {
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + displayName + ".zip\"");
        try (FileInputStream fis = new FileInputStream(zipFile);
             ServletOutputStream outStream = response.getOutputStream();){
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = fis.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            outStream.flush();
        }
        catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    public static String getOSName() {
        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.indexOf("linux") >= 0) {
            return "linux";
        }
        if (OS.indexOf("windows") >= 0) {
            return "windows";
        }
        return "";
    }

    public static boolean isLinuxOS() {
        return Utils.getOSName().equalsIgnoreCase("linux");
    }

    public static String getRootDir() {
        return Utils.isLinuxOS() ? "/opt/" : "C:/";
    }

    public static String getTomcatInstallPath() {
        if (Utils.getOSName().equalsIgnoreCase("windows")) {
            return "C:/Program Files/Apache Software Foundation/Tomcat 9.0";
        }
        return "/usr/local/tomcat";
    }

    public static File getUILogoPath(int uiId) {
        return new File(CommonConstants.CLONE_PROCESS_LOCATION + "uiCustomizations/" + uiId + "/PhilipsHome/");
    }

    private static class SortComparatorForJSONUtils
    implements Comparator<JSONObject> {
        private String sortItem;
        private String sortType;
        private String sortDire;

        public SortComparatorForJSONUtils(String sortItem, String sortType, String sortDire) {
            this.sortItem = sortItem;
            this.sortType = sortType;
            this.sortDire = sortDire;
        }

        @Override
        public int compare(JSONObject o1, JSONObject o2) {
            String value1 = o1.get(this.sortItem).toString();
            String value2 = o2.get(this.sortItem).toString();
            if ("int".equalsIgnoreCase(this.sortType)) {
                int int1 = Integer.parseInt(value1);
                int int2 = Integer.parseInt(value2);
                if ("asc".equalsIgnoreCase(this.sortDire)) {
                    return int1 - int2;
                }
                if ("desc".equalsIgnoreCase(this.sortDire)) {
                    return int2 - int1;
                }
                return 0;
            }
            if ("string".equalsIgnoreCase(this.sortType)) {
                if ("asc".equalsIgnoreCase(this.sortDire)) {
                    return value1.compareTo(value2);
                }
                if ("desc".equalsIgnoreCase(this.sortDire)) {
                    return value2.compareTo(value1);
                }
                return 0;
            }
            return 0;
        }
    }
}

