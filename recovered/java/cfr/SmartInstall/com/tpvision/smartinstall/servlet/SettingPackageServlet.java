/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.annotation.WebServlet
 */
package com.tpvision.smartinstall.servlet;

import com.google.gson.Gson;
import com.tpvision.smartinstall.androidapp.AndroidAppHelper;
import com.tpvision.smartinstall.core.SettingChannelBean;
import com.tpvision.smartinstall.core.TermAndConditionsBean;
import com.tpvision.smartinstall.dao.core.ChannelPackage;
import com.tpvision.smartinstall.dao.core.SettingPackage;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.SettingManager;
import com.tpvision.smartinstall.dao.mgr.SettingPackageManager;
import com.tpvision.smartinstall.servlet.BaseHttpServlet;
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.JAPITUtils;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.SettingState;
import com.tpvision.smartinstall.xml.Setting;
import com.tpvision.smartinstall.xml.setting.v2k16.roomspecific.Item;
import com.tpvision.smartinstall.xml.setting.v2k16.roomspecific.RoomSpecificSettings;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(value={"/settingpackage"})
public class SettingPackageServlet
extends BaseHttpServlet {
    private static final String SETTING_PACKAGE_ID = "settingPackageId";
    private static final String TERM_VALUE = "value";
    private static final String TERM_VALUE_NAME = "valueName";
    private static final String TERM_ID = "termId";
    private static final String MULTI_REMOTE_CONTROL = "Multi_Remote_Control";
    private static final Logger LOG = LoggerFactory.getLogger(SettingPackageServlet.class);
    private static final String SUCCESS = "{\"status\":\"success\"}";
    private static final String SET_NONE = "{\"status\":\"setNone\"}";

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mode;
        switch (mode = request.getParameter("mode")) {
            case "SETTING_INDEX": {
                this.settingIndex(request, response);
                break;
            }
            case "RENAME_SETTINGPACKAGE": {
                this.renameSettingPackage(request, response);
                break;
            }
            case "ASSIGN_SETTINGPACKAGE": {
                this.assignSettingPackage(request, response);
                break;
            }
            case "COPY_SETTINGPACKAGE": {
                this.copySettingPackage(request, response);
                break;
            }
            case "DELETE_SETTINGPACKAGE": {
                this.deleteSettingPackage(request, response);
                break;
            }
            case "SETTING_EDIT": {
                this.settingEdit(request, response);
                break;
            }
            case "GET_SMARTINFO_APPS": 
            case "GET_CDB_APPS": 
            case "GET_EPG_APPS": {
                this.getSmartInfoApps(request, response);
                break;
            }
            case "GET_APPS_BY_CATEGORYS": {
                this.getAppsByCategorys(request, response);
                break;
            }
            case "GET_APPS_PACKAGE_BY_CATEGORYS": {
                this.getAppsPackageByCategorys(request, response);
                break;
            }
            case "GET_ASSIGN_SETTINGPACKAGE_LIST": {
                this.getAssignSettingPackageList(request, response);
                break;
            }
            case "UPDATE_SETTING": {
                this.updateSettings(request, response);
                break;
            }
            case "LOAD_JSON_DEFINITIONS": {
                this.loadJSONDefinitions(request, response);
                break;
            }
            case "LOAD_VALUES": {
                this.loadValues(request, response);
                break;
            }
            case "UPDATE_SETTING_CLONEIN": {
                this.updateCloneIn(request, response);
                break;
            }
            case "ADD_TERM_AND_CONDITIONS": {
                this.addTermAndConditions(request, response);
                break;
            }
            case "INIT_EDIT_TERM": {
                this.initEditTerm(request, response);
                break;
            }
            case "SAVE_TERM_AND_CONDITIONS": {
                this.saveTermAndConditions(request, response);
                break;
            }
            case "DELETE_TERM_AND_CONDITIONS": {
                this.deleteTermAndConditions(request, response);
                break;
            }
            default: {
                LOG.error("not support mode:{}", (Object)mode);
            }
        }
    }

    private void deleteTermAndConditions(HttpServletRequest request, HttpServletResponse response) {
        String settingPackageId = request.getParameter(SETTING_PACKAGE_ID);
        String termValue = request.getParameter(TERM_VALUE);
        String valueName = request.getParameter(TERM_VALUE_NAME);
        String termId = request.getParameter(TERM_ID);
        this.saveTermIntoDB(termValue, termId, settingPackageId, valueName);
        this.responseJSON(this.successStatus(), response);
    }

    private String saveTermIntoDB(String value, String termId, String settingPackageId, String valueName) {
        int seq;
        SettingPackage settingPackage = JpaManager.getSettingPackageManager().loadByKey(Integer.valueOf(settingPackageId));
        String termAndConditions = settingPackage.getTermAndConditions();
        value = StringEscapeUtils.escapeJava(value);
        TermAndConditionsBean termAndConditionsBean = new Gson().fromJson(termAndConditions, TermAndConditionsBean.class);
        TermAndConditionsBean.CommandDetails cmdDetails = termAndConditionsBean.getCommandDetails();
        TermAndConditionsBean.CommandDetails.ApplicationDetails appDetails = cmdDetails.getApplicationDetails();
        TermAndConditionsBean.CommandDetails.ApplicationDetails.ApplicationAttributes appAttr = appDetails.getApplicationAttributes();
        List<TermAndConditionsBean.CommandDetails.ApplicationDetails.ApplicationAttributes.TermsAndConditions> messages = appAttr.getMessages();
        if ("new".equalsIgnoreCase(termId)) {
            TermAndConditionsBean.CommandDetails.ApplicationDetails.ApplicationAttributes.TermsAndConditions newMsg = new TermAndConditionsBean.CommandDetails.ApplicationDetails.ApplicationAttributes.TermsAndConditions();
            newMsg.setLanguage("eng");
            newMsg.setMessageBody("Please enter a message.");
            newMsg.setMessageTitle("Please enter a title.");
            messages.add(newMsg);
        } else if ("language".equalsIgnoreCase(valueName)) {
            messages.get(Integer.parseInt(termId)).setLanguage(value);
        } else if ("body".equalsIgnoreCase(valueName)) {
            messages.get(Integer.parseInt(termId)).setMessageBody(value);
        } else if ("title".equalsIgnoreCase(valueName)) {
            messages.get(Integer.parseInt(termId)).setMessageTitle(value);
        } else if ("delete".equalsIgnoreCase(valueName) && (seq = Integer.parseInt(termId)) >= 0 && seq < messages.size()) {
            messages.remove(seq);
        }
        appAttr.setMessages(messages);
        appDetails.setApplicationAttributes(appAttr);
        cmdDetails.setApplicationDetails(appDetails);
        termAndConditionsBean.setCommandDetails(cmdDetails);
        String termAndConditionsData = new Gson().toJson(termAndConditionsBean);
        settingPackage.setTermAndConditions(termAndConditionsData);
        JpaManager.getSettingPackageManager().save(settingPackage);
        return String.valueOf(messages.size());
    }

    private void saveTermAndConditions(HttpServletRequest request, HttpServletResponse response) {
        String settingPackageId = request.getParameter(SETTING_PACKAGE_ID);
        String termValue = request.getParameter(TERM_VALUE);
        String valueName = request.getParameter(TERM_VALUE_NAME);
        String termId = request.getParameter(TERM_ID);
        this.saveTermIntoDB(termValue, termId, settingPackageId, valueName);
        this.responseJSON(this.successStatus(), response);
    }

    private void initEditTerm(HttpServletRequest request, HttpServletResponse response) {
        String settingPackageId = request.getParameter(SETTING_PACKAGE_ID);
        SettingPackage settingPackage = JpaManager.getSettingPackageManager().loadByKey(Integer.valueOf(settingPackageId));
        String termAndConditions = settingPackage.getTermAndConditions();
        if (termAndConditions == null) {
            termAndConditions = "{\"Svc\":\"offlineServices\",\"SvcVer\":\"4.0\",\"Cookie\":" + JAPITUtils.getJapitRandomCookieValue() + ",\"CmdType\":\"Change\",\"Fun\":\"ApplicationControl\",\"CommandDetails\":{\"ApplicationDetails\":{\"ApplicationName\":\"Googlecast\",\"ApplicationAttributes\":{\"TermsAndConditions\":[{\"Language\":\"eng\",\"TermsAndConditionsTitle\":\"Please enter a title.\",\"TermsAndConditionsBody\":\"Please enter a message.\"}]}}}}";
            settingPackage.setTermAndConditions(termAndConditions);
            JpaManager.getSettingPackageManager().save(settingPackage);
        }
        TermAndConditionsBean termAndConditionsBean = new Gson().fromJson(termAndConditions, TermAndConditionsBean.class);
        List<TermAndConditionsBean.CommandDetails.ApplicationDetails.ApplicationAttributes.TermsAndConditions> messages = termAndConditionsBean.getCommandDetails().getApplicationDetails().getApplicationAttributes().getMessages();
        StringBuilder sbLanguage = new StringBuilder();
        StringBuilder sbMessageBody = new StringBuilder();
        StringBuilder sbMessageTitle = new StringBuilder();
        boolean first = true;
        for (TermAndConditionsBean.CommandDetails.ApplicationDetails.ApplicationAttributes.TermsAndConditions mes : messages) {
            if (first) {
                first = false;
            } else {
                sbLanguage.append("|");
                sbMessageBody.append("|");
                sbMessageTitle.append("|");
            }
            sbLanguage.append(mes.getLanguage());
            sbMessageBody.append(StringEscapeUtils.unescapeJava(mes.getMessageBody()));
            sbMessageTitle.append(StringEscapeUtils.unescapeJava(mes.getMessageTitle()));
        }
        Gson gson = new Gson();
        String status = "{\"languageList\":" + gson.toJson(sbLanguage.toString()) + ", \"bodyList\":" + gson.toJson(sbMessageBody.toString()) + ", \"titleList\":" + gson.toJson(sbMessageTitle.toString()) + "}";
        this.responseJSON(status, response);
    }

    private void addTermAndConditions(HttpServletRequest request, HttpServletResponse response) {
        String status = this.successStatus();
        String settingPackageId = request.getParameter(SETTING_PACKAGE_ID);
        String messagesNum = this.saveTermIntoDB("", "new", settingPackageId, "");
        status = "{\"messagesNum\":" + messagesNum + "}";
        this.responseJSON(status, response);
    }

    private void updateCloneIn(HttpServletRequest request, HttpServletResponse response) {
        String key = request.getParameter("key");
        String value = request.getParameter(TERM_VALUE);
        String settingPackageId = request.getParameter(SETTING_PACKAGE_ID);
        String status = this.successStatus();
        try {
            if (key == null) {
                throw new BaseHttpServlet.MessageException("key is null");
            }
            SettingPackage settingPackage = JpaManager.getSettingPackageManager().loadByKey(Integer.valueOf(settingPackageId));
            LOG.info("update setting clonein:{} to {}", (Object)key, (Object)value);
            SettingChannelBean scb = new Gson().fromJson(settingPackage.getValue(), SettingChannelBean.class);
            Setting settingItem = this.getSettingItem(key, scb);
            if (settingItem == null) {
                LOG.info("Setting name not found in database:{}", (Object)key);
                settingItem = new Setting();
                scb.getSetttings().getSetting().add(settingItem);
                settingItem.setItem(key);
                settingItem.setCloneIn(value);
            } else {
                settingItem.setCloneIn(value);
            }
            String toDbString = new Gson().toJson(scb);
            settingPackage.setValue(toDbString);
            JpaManager.getSettingPackageManager().save(settingPackage);
        }
        catch (BaseHttpServlet.MessageException | NumberFormatException e) {
            LOG.error(e.getMessage(), e);
            status = this.failedStatus(e.getMessage());
        }
        this.responseJSON(status, response);
    }

    private void loadValues(HttpServletRequest request, HttpServletResponse response) {
        String settingPackageId = request.getParameter(SETTING_PACKAGE_ID);
        try {
            SettingPackage settingPackage = JpaManager.getSettingPackageManager().loadByKey(Integer.parseInt(settingPackageId));
            SettingChannelBean scb = new Gson().fromJson(settingPackage.getValue(), SettingChannelBean.class);
            List<Setting> settings = scb.getSetttings().getSetting();
            this.responseJSON(this.successStatus(settings), response);
        }
        catch (NumberFormatException e) {
            LOG.error(e.getMessage(), e);
            this.responseJSON(this.failedStatus(e.getMessage()), response);
        }
    }

    private void loadJSONDefinitions(HttpServletRequest request, HttpServletResponse response) {
        String settingPackageId = request.getParameter(SETTING_PACKAGE_ID);
        try {
            SettingPackage settingPackage = JpaManager.getSettingPackageManager().loadByKey(Integer.parseInt(settingPackageId));
            String defines = this.getTVSettingsDefinition(settingPackage.getPlatform());
            JSONObject jsonDef = new JSONObject(defines);
            this.responseJSON(this.successStatus(jsonDef), response);
        }
        catch (IOException | NumberFormatException e) {
            LOG.error(e.getMessage(), e);
            this.responseJSON(this.failedStatus(e.getMessage()), response);
        }
    }

    private Setting getSettingItem(String settingName, SettingChannelBean scb) {
        for (Setting s : scb.getSetttings().getSetting()) {
            if (!s.getItem().equals(settingName)) continue;
            return s;
        }
        return null;
    }

    private void updateSettings(HttpServletRequest request, HttpServletResponse response) {
        String key = request.getParameter("key");
        String value = request.getParameter(TERM_VALUE);
        String settingPackageId = request.getParameter(SETTING_PACKAGE_ID);
        String settingName = request.getParameter("settingName");
        String status = this.successStatus();
        try {
            if (key == null) {
                throw new BaseHttpServlet.MessageException("key is null");
            }
            boolean multiRCCheck = key.equalsIgnoreCase("multi_Remote_Control_checked");
            SettingPackage settingPackage = JpaManager.getSettingPackageManager().loadByKey(Integer.valueOf(settingPackageId));
            if (settingName == null) {
                SettingState.BaseParamConverter converter = SettingState.getParamConverter(settingPackage.getPlatform());
                settingName = converter.getSettingName(key);
                if (multiRCCheck) {
                    settingName = converter.getSettingName(MULTI_REMOTE_CONTROL);
                    String string = value = value.equalsIgnoreCase("true") ? "Yes" : "No";
                }
            }
            if (settingName == null) {
                throw new BaseHttpServlet.MessageException("Setting name not found " + key);
            }
            LOG.info("update setting:{} to {}", (Object)settingName, (Object)value);
            SettingChannelBean scb = new Gson().fromJson(settingPackage.getValue(), SettingChannelBean.class);
            Setting settingItem = this.getSettingItem(settingName, scb);
            if (settingItem == null) {
                LOG.info("Setting name not found in database:{}", (Object)settingName);
                settingItem = new Setting();
                settingItem.setXaddr("MS2K16");
                scb.getSetttings().getSetting().add(settingItem);
                settingItem.setItem(settingName);
                settingItem.setCloneIn("Yes");
            }
            if (multiRCCheck) {
                settingItem.setCloneIn(value);
            } else {
                settingItem.setLastValue(value);
            }
            String toDbString = new Gson().toJson(scb);
            settingPackage.setValue(toDbString);
            JpaManager.getSettingPackageManager().save(settingPackage);
        }
        catch (BaseHttpServlet.MessageException | NumberFormatException e) {
            LOG.error(e.getMessage(), e);
            status = this.failedStatus(e.getMessage());
        }
        this.responseJSON(status, response);
    }

    private void getAppsByCategorys(HttpServletRequest request, HttpServletResponse response) {
        try {
            String settingPackageId = request.getParameter(SETTING_PACKAGE_ID);
            String categorys = request.getParameter("categorys");
            String[] categoryArray = categorys.split(",");
            JSONArray jsArray = this.getAppsByCategory(settingPackageId, categoryArray);
            this.responseJSON(this.successStatus(jsArray), response);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            this.responseJSON(this.failedStatus(e.getMessage()), response);
        }
    }

    private void getAppsPackageByCategorys(HttpServletRequest request, HttpServletResponse response) {
        try {
            String settingPackageId = request.getParameter(SETTING_PACKAGE_ID);
            String categorys = request.getParameter("categorys");
            String[] categoryArray = categorys.split(",");
            JSONArray jsArray = this.getAppsPackageByCategory(settingPackageId, categoryArray);
            this.responseJSON(this.successStatus(jsArray), response);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            this.responseJSON(this.failedStatus(e.getMessage()), response);
        }
    }

    private JSONArray getAppsByCategory(String settingPackageId, String ... categorys) {
        AndroidAppHelper helper = AndroidAppHelper.getHelper(Integer.parseInt(settingPackageId));
        HashSet<String> resultSets = new HashSet<String>();
        for (String category : categorys) {
            resultSets.addAll(helper.getAppNames(category));
        }
        JSONArray jsArray = new JSONArray();
        for (String appName : resultSets) {
            jsArray.put(appName);
        }
        return jsArray;
    }

    private JSONArray getAppsPackageByCategory(String settingPackageId, String ... categorys) {
        AndroidAppHelper helper = AndroidAppHelper.getHelper(Integer.parseInt(settingPackageId));
        HashSet<String> resultSets = new HashSet<String>();
        for (String category : categorys) {
            resultSets.addAll(helper.getAppPackageNames(category));
        }
        JSONArray jsArray = new JSONArray();
        for (String appName : resultSets) {
            jsArray.put(appName);
        }
        return jsArray;
    }

    @Deprecated
    private void getSmartInfoApps(HttpServletRequest request, HttpServletResponse response) {
        String mode = request.getParameter("mode");
        try {
            String settingPackageId = request.getParameter(SETTING_PACKAGE_ID);
            String category = "";
            switch (mode) {
                case "GET_SMARTINFO_APPS": {
                    category = "com.philips.professionaldisplaysolutions.jedi.intent.category.SMART_INFO";
                    break;
                }
                case "GET_CDB_APPS": {
                    category = "com.philips.professionaldisplaysolutions.jedi.intent.category.PRO_TV_DASHBOARD";
                    break;
                }
                case "GET_EPG_APPS": {
                    category = "com.philips.professionaldisplaysolutions.jedi.intent.category.PRO_TV_EPG";
                    break;
                }
                default: {
                    LOG.info("unsupport mode:{}", (Object)mode);
                }
            }
            JSONArray jsArray = this.getAppsByCategory(settingPackageId, category);
            this.responseJSON(this.successStatus(jsArray), response);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            this.responseJSON(this.failedStatus(e.getMessage()), response);
        }
    }

    private void settingEdit(HttpServletRequest request, HttpServletResponse response) {
        String settingPackageId = request.getParameter(SETTING_PACKAGE_ID);
        try {
            String welcomelogoImageFileName;
            SettingPackage settingPackage = JpaManager.getSettingPackageManager().loadByKey(Integer.valueOf(settingPackageId));
            SettingChannelBean scb = new Gson().fromJson(settingPackage.getValue(), SettingChannelBean.class);
            String hotelImageFileName = request.getAttribute("HOTEL_IMAGE_FILE_NAME") != null ? (String)request.getAttribute("HOTEL_IMAGE_FILE_NAME") : null;
            String string = welcomelogoImageFileName = request.getAttribute("WELCOMELOGO_IMAGE_FILE_NAME") != null ? (String)request.getAttribute("WELCOMELOGO_IMAGE_FILE_NAME") : null;
            if (null != hotelImageFileName) {
                scb.setHotelImageName(hotelImageFileName);
            }
            if (null != welcomelogoImageFileName) {
                scb.setWelcomelogoImageName(welcomelogoImageFileName);
            }
            String multiRemoteControlChecked = request.getParameter("MultiRemoteControlChecked");
            this.setWelcomeMsg(scb, request);
            this.setSwitchOnSource(scb);
            this.setClockChannel(scb, request);
            if (scb.getRoomSpecificSettings() != null) {
                this.setRoomSepcificSettings(scb.getRoomSpecificSettings(), request);
            }
            SettingState ss = new SettingState(request);
            Map<String, String> map = ss.getSettingToValuesMap();
            boolean bStaticIP = false;
            for (Setting s : scb.getSetttings().getSetting()) {
                if (null == s.getItem()) {
                    LOG.error("getItem is null");
                    continue;
                }
                String value = map.get(s.getItem());
                if (null == value) {
                    LOG.error("value is null:" + s.getItem());
                    continue;
                }
                if ("Wireless and Networks.Settings.Network Configuration".equalsIgnoreCase(s.getItem())) {
                    bStaticIP = "Static IP".equalsIgnoreCase(value);
                }
                if ("Wireless and Networks.Settings.Static IP Configuration.IP Address".equalsIgnoreCase(s.getItem()) || "Wireless and Networks.Settings.Static IP Configuration.Netmask".equalsIgnoreCase(s.getItem()) || "Wireless and Networks.Settings.Static IP Configuration.Gateway".equalsIgnoreCase(s.getItem()) || "Wireless and Networks.Settings.Static IP Configuration.DNS 1".equalsIgnoreCase(s.getItem()) || "Wireless and Networks.Settings.Static IP Configuration.DNS 2".equalsIgnoreCase(s.getItem())) {
                    if (bStaticIP) {
                        s.setCloneIn("Yes");
                    } else {
                        s.setCloneIn("No");
                    }
                }
                if (s.getRefFile() != null && s.getRefFile().startsWith("BdsLastStatus")) {
                    if (null != value && s.getItem1().equalsIgnoreCase(s.getItem())) {
                        if (value.equalsIgnoreCase("chfalse")) {
                            s.setLastValue("0");
                        } else {
                            s.setLastValue1(value);
                        }
                    }
                } else if (s.getRefFile() == null && s.getXaddr() != null && (s.getXaddr().equalsIgnoreCase("ES2K12") || s.getXaddr().equalsIgnoreCase("ES2K13") || s.getXaddr().equalsIgnoreCase("MS2K14"))) {
                    if (map.get(s.getItem()) != null && !map.get(s.getItem()).equalsIgnoreCase("chfalse")) {
                        s.setLastValue(value);
                    }
                    if (s.getXaddr() != null && s.getXaddr().equalsIgnoreCase("ES2K13")) {
                        s.setRefFile("ES2K13");
                    } else if (s.getXaddr() != null && s.getXaddr().equalsIgnoreCase("MS2K14")) {
                        s.setRefFile("MS2K14");
                    } else if (s.getXaddr() != null && s.getXaddr().equalsIgnoreCase("MS2K16")) {
                        s.setRefFile("MS2K16");
                    } else if (s.getXaddr() != null && s.getXaddr().equalsIgnoreCase("ES2K16")) {
                        s.setRefFile("ES2K16");
                    } else {
                        s.setRefFile("ES2K12");
                    }
                } else if (!(s.getRefFile() == null || s.getRefFile().startsWith("LastStatus") || s.getRefFile().equalsIgnoreCase("ES2K12") || s.getXaddr() == null || s.getXaddr().equalsIgnoreCase("ES2K13") || s.getXaddr().equalsIgnoreCase("MS2K14"))) {
                    if (value.equalsIgnoreCase("chfalse")) {
                        s.setLastValue("0");
                    } else {
                        s.setLastValue(value);
                    }
                } else if (s.getXaddr() != null && (s.getXaddr().equalsIgnoreCase("ES2K12") || s.getXaddr().equalsIgnoreCase("ES2K13") || s.getXaddr().equalsIgnoreCase("MS2K14") || s.getXaddr().equalsIgnoreCase("MS2K16")) && s.getRefFile() != null && (s.getRefFile().equalsIgnoreCase("ES2K12") || s.getXaddr().equalsIgnoreCase("ES2K13") || s.getXaddr().equalsIgnoreCase("MS2K14") || s.getXaddr().equalsIgnoreCase("MS2K16") || s.getXaddr().equalsIgnoreCase("ES2K16") || s.getRefFile().equalsIgnoreCase(""))) {
                    if (null != value && value.equalsIgnoreCase("chfalse")) {
                        s.setLastValue(s.getLastValue());
                    } else {
                        s.setLastValue(value);
                    }
                }
                if ("Features.Multi Remote Control".equalsIgnoreCase(s.getItem())) {
                    if ("Yes".equalsIgnoreCase(multiRemoteControlChecked)) {
                        s.setCloneIn("Yes");
                    } else {
                        s.setCloneIn("No");
                    }
                }
                if (!"Input Control.Multi Remote Control".equalsIgnoreCase(s.getItem())) continue;
                if ("Yes".equalsIgnoreCase(multiRemoteControlChecked)) {
                    s.setCloneIn("Yes");
                    continue;
                }
                s.setCloneIn("No");
            }
            String toDbString = new Gson().toJson(scb);
            settingPackage.setValue(toDbString);
            JpaManager.getSettingPackageManager().save(settingPackage);
        }
        catch (NumberFormatException e) {
            LOG.error(e.getMessage(), e);
            try {
                response.sendRedirect("/SmartInstall/getFile?mode=index");
            }
            catch (IOException e1) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    private void deleteSettingPackage(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String status = SUCCESS;
        SettingPackageManager settingPackageManager = JpaManager.getSettingPackageManager();
        try {
            settingPackageManager.deleteByKey(Integer.parseInt(id));
        }
        catch (NumberFormatException e) {
            status = this.failedStatus(e.getMessage());
            LOG.error(e.getMessage(), e);
        }
        this.responseJSON(status, response);
    }

    private void copySettingPackage(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String status = SUCCESS;
        SettingPackageManager settingPackageManager = JpaManager.getSettingPackageManager();
        SettingPackage copyDest = new SettingPackage();
        try {
            SettingPackage copySrc = settingPackageManager.loadByKey(Integer.parseInt(id));
            List<String> currentSettingPackageNames = settingPackageManager.loadAll().stream().map(SettingPackage::getName).map(String::trim).collect(Collectors.toList());
            String copyName = CloneItemUtils.getUniqueCloneName(currentSettingPackageNames, "Copy of " + copySrc.getName());
            copyDest.setName(copyName);
            copyDest.setPlatform(copySrc.getPlatform());
            copyDest.setValue(copySrc.getValue());
            copyDest.setLastEdit(copySrc.getLastEdit());
            settingPackageManager.save(copyDest);
        }
        catch (NumberFormatException e) {
            status = this.failedStatus(e.getMessage());
            LOG.error(e.getMessage(), e);
        }
        this.responseJSON(status, response);
    }

    private String getTVSettingsDefinition(String platform) throws IOException {
        String platformId = PlatformUtils.getPlatformId(platform);
        String jsonPath = this.getClass().getClassLoader().getResource("TVSettings/" + platformId + ".json").getFile();
        jsonPath = URLDecoder.decode(jsonPath, "utf-8");
        return FileUtils.readFileToString(new File(jsonPath), StandardCharsets.UTF_8);
    }

    private void settingIndex(HttpServletRequest request, HttpServletResponse response) {
        try {
            ChannelPackage channelPackage;
            String appId = "0";
            String channelId = "0";
            String scheduleId = "0";
            String id = "0";
            String sid = request.getParameter("sid");
            if (null != sid) {
                com.tpvision.smartinstall.dao.core.Setting setting = JpaManager.getSettingManager().loadByKey(Integer.parseInt(sid));
                if (null != setting) {
                    appId = String.valueOf(setting.getAppPackageId());
                    scheduleId = String.valueOf(setting.getScheduleId());
                    if (PlatformUtils.isSupportChannelEditor(setting.getPlatform())) {
                        channelId = String.valueOf(setting.getChannelPackageId());
                    }
                    id = String.valueOf(setting.getSettingPackageId());
                    request.setAttribute("sname", setting.getName());
                    request.setAttribute("settingId", sid);
                    ArrayList<String> details = new ArrayList<String>();
                    details.add(setting.getName());
                    details.add(setting.getPlatform());
                }
            } else {
                id = request.getParameter("id");
                request.setAttribute("settingId", "0");
            }
            SettingPackageManager settingPackageManager = JpaManager.getSettingPackageManager();
            SettingPackage settingPackage = settingPackageManager.loadByKey(Integer.parseInt(id));
            if (null == settingPackage) {
                LOG.error("invalid setting package id:{}", (Object)id);
                throw new IOException("invalid setting package id");
            }
            SettingChannelBean scb = new Gson().fromJson(settingPackage.getValue(), SettingChannelBean.class);
            if (StringUtils.isNotBlank(channelId) && (channelPackage = JpaManager.getChannelPackageManager().loadByKey(Integer.parseInt(channelId))) != null) {
                scb.mergeWithChanelPackageBean(new Gson().fromJson(channelPackage.getValue(), SettingChannelBean.class));
            }
            this.setWelcomeMsg(scb, request);
            this.setClockChannel(scb, request);
            SettingState.BaseParamConverter converter = PlatformUtils.getParamConverter(settingPackage.getPlatform());
            Setting settingItem = this.getSettingItem(converter.getSettingName(MULTI_REMOTE_CONTROL), scb);
            if (settingItem != null) {
                request.setAttribute("MultiRemoteControlChecked", settingItem.getCloneIn());
            }
            request.setAttribute("scb", scb);
            request.setAttribute("Platform", PlatformUtils.getPlatformName(settingPackage.getPlatform()));
            request.setAttribute("DisplayName", settingPackage.getName());
            request.setAttribute(SETTING_PACKAGE_ID, id);
            request.getSession().setAttribute("appPackageId", null == appId ? "0" : appId);
            request.getSession().setAttribute("channelPackageId", null == channelId ? "0" : channelId);
            request.getSession().setAttribute("scheduleId", null == scheduleId ? "0" : scheduleId);
            request.setAttribute("themeTVSupport", PlatformUtils.isSupportThemeTv(settingPackage.getPlatform()));
            request.getSession().setAttribute("platform", PlatformUtils.getPlatformId(settingPackage.getPlatform()));
            request.setAttribute("platformName", PlatformUtils.getPlatformId(settingPackage.getPlatform()));
            if (this.isEasySuite(scb)) {
                request.setAttribute("ESMODE", "true");
            }
            String jspUrl = PlatformUtils.getConfigJSPUrl(settingPackage.getPlatform());
            request.getRequestDispatcher(jspUrl).forward(request, response);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private boolean isEasySuite(SettingChannelBean scb) {
        boolean isEasySuite = false;
        for (Setting s : scb.getSetttings().getSetting()) {
            if (s.getRefFile() != null && (s.getRefFile() == null || !"ES2K12".equalsIgnoreCase(s.getXaddr()) && !"ES2K13".equalsIgnoreCase(s.getXaddr()) && !"MS2K14".equalsIgnoreCase(s.getXaddr()) && !"MS2K16".equalsIgnoreCase(s.getXaddr()) && !"MS2K15".equalsIgnoreCase(s.getXaddr()) && !"ES2K16".equalsIgnoreCase(s.getXaddr()))) continue;
            isEasySuite = true;
            break;
        }
        return isEasySuite;
    }

    private void renameSettingPackage(HttpServletRequest request, HttpServletResponse response) {
        String newName = request.getParameter("newName");
        String id = request.getParameter("id");
        String status = SUCCESS;
        try {
            int settingPackageId = Integer.parseInt(id);
            SettingPackageManager settingPackageManager = JpaManager.getSettingPackageManager();
            List<SettingPackage> settingPackages = settingPackageManager.findByName(newName);
            if (!(settingPackages.isEmpty() || settingPackages.size() == 1 && settingPackages.get(0).getId() == settingPackageId)) {
                throw new RuntimeException("settings new name already exist");
            }
            SettingPackage settingPackage = settingPackageManager.loadByKey(settingPackageId);
            settingPackage.setName(newName);
            settingPackageManager.save(settingPackage);
        }
        catch (Exception e) {
            status = this.failedStatus(e.getMessage());
            LOG.error(e.getMessage(), e);
        }
        this.responseJSON(status, response);
    }

    private void getAssignSettingPackageList(HttpServletRequest request, HttpServletResponse response) {
        String platform = request.getParameter("platform");
        LOG.info("assign platform:{}", (Object)platform);
        List<SettingPackage> settingPackages = null;
        settingPackages = platform.isEmpty() ? JpaManager.getSettingPackageManager().loadAll() : JpaManager.getSettingPackageManager().findSettingPackagesByPlatforms(platform);
        try {
            IOUtils.write(new Gson().toJson(settingPackages).getBytes(), (OutputStream)response.getOutputStream());
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void assignSettingPackage(HttpServletRequest request, HttpServletResponse response) {
        String cloneIds = request.getParameter("clone_ids");
        String[] cloneIdsArr = cloneIds.split(",");
        String settingPackageId = request.getParameter("settingPackage_id");
        String status = SUCCESS;
        if ("None".equalsIgnoreCase(settingPackageId)) {
            settingPackageId = String.valueOf(-1);
            status = SET_NONE;
        } else {
            SettingPackageManager settingPackageManager = JpaManager.getSettingPackageManager();
            String[] settingPackage = settingPackageManager.loadByKey(Integer.parseInt(settingPackageId));
            if (settingPackage == null) {
                LOG.error("settingpackageid:{} not exists", (Object)settingPackageId);
                throw new RuntimeException("selected setting package clone not exists");
            }
        }
        SettingManager settingManager = JpaManager.getSettingManager();
        for (String cloneIdsArr1 : cloneIdsArr) {
            if ("null".equals(cloneIdsArr1)) continue;
            com.tpvision.smartinstall.dao.core.Setting setting = settingManager.loadByKey(Integer.parseInt(cloneIdsArr1));
            setting.setSettingPackageId(Integer.parseInt(settingPackageId));
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
        if (scb.getSetttings().getSetting().isEmpty()) {
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
                    if (null == value || null != value && value.equals("0")) continue;
                    try {
                        if (null == value || value.trim().equals("")) continue;
                        char c = (char)Integer.parseInt(value);
                        sbuild.append(Character.toString(c));
                        continue;
                    }
                    catch (Exception ex) {
                        LOG.error(ex.getMessage(), ex);
                    }
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

    private void setSwitchOnSource(SettingChannelBean scb) {
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
}

