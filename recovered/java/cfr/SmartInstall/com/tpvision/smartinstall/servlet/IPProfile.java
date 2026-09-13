/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.servlet;

import com.google.gson.Gson;
import com.tpvision.smartinstall.servlet.IPProfileConfig;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.Utils;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IPProfile {
    private static final Logger LOG = LoggerFactory.getLogger(IPProfile.class);
    private String greenMode;
    private String fastMode;
    private String professionalService;
    private String tvDiscovery;
    private String manuallyLimitUpdate = "true";
    private String webserviceURLIncorrectWarning = "true";
    private String tvSettingChangeWarning = "true";
    private String firmwareOutOfDateWarning = "true";
    private String maxUpdate;
    private String updateTimeout;
    private String URL;

    public String getManuallyLimitUpdate() {
        return this.manuallyLimitUpdate;
    }

    public void setManuallyLimitUpdate(String manuallyLimitUpdate) {
        this.manuallyLimitUpdate = manuallyLimitUpdate;
    }

    public String getGreenMode() {
        return this.greenMode;
    }

    public void setGreenMode(String greenMode) {
        this.greenMode = greenMode;
    }

    public String getFastMode() {
        return this.fastMode;
    }

    public void setFastMode(String fastMode) {
        this.fastMode = fastMode;
    }

    public String getProfessionalService() {
        return this.professionalService;
    }

    public void setProfessionalService(String professionalService) {
        this.professionalService = professionalService;
    }

    public String getTvDiscovery() {
        return this.tvDiscovery;
    }

    public void setTvDiscovery(String tvDiscovery) {
        this.tvDiscovery = tvDiscovery;
    }

    public String getMaxTVUpdate() {
        return this.maxUpdate;
    }

    public void setMaxTVUpdate(String maxUpdate) {
        this.maxUpdate = maxUpdate;
    }

    public String getUpdateTimeout() {
        return this.updateTimeout;
    }

    public void setUpdateTimeout(String updateTimeout) {
        this.updateTimeout = updateTimeout;
    }

    public String getWebserviceURLIncorrectWarning() {
        return this.webserviceURLIncorrectWarning;
    }

    public void setWebserviceURLIncorrectWarning(String webserviceURLIncorrectWarning) {
        this.webserviceURLIncorrectWarning = webserviceURLIncorrectWarning;
    }

    public String getTvSettingChangeWarning() {
        return this.tvSettingChangeWarning;
    }

    public void setTvSettingChangeWarning(String tvSettingChangeWarning) {
        this.tvSettingChangeWarning = tvSettingChangeWarning;
    }

    public String getFirmwareOutOfDateWarning() {
        return this.firmwareOutOfDateWarning;
    }

    public void setFirmwareOutOfDateWarning(String firmwareOutOfDateWarning) {
        this.firmwareOutOfDateWarning = firmwareOutOfDateWarning;
    }

    public String getURL() {
        return this.URL;
    }

    public void setURL(String URL2) {
        this.URL = URL2;
    }

    public void saveToFile() {
        try {
            File f = new File(CommonConstants.LAST_IP_CONFIG_LOCATION);
            FileUtils.writeStringToFile(f, new Gson().toJson(this), StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static IPProfile loadIPProfile() {
        File f = new File(CommonConstants.LAST_IP_CONFIG_LOCATION);
        IPProfile lastConfig = null;
        if (f.exists()) {
            try (FileReader fileReader = new FileReader(f);){
                Gson gson = new Gson();
                lastConfig = gson.fromJson((Reader)fileReader, IPProfile.class);
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
        if (lastConfig == null) {
            LOG.warn("last config is null start to init it");
            lastConfig = IPProfile.getDefaultIPProfile();
            lastConfig.saveToFile();
        }
        return lastConfig;
    }

    private static IPProfile getDefaultIPProfile() {
        IPProfile lastConfig = new IPProfile();
        lastConfig.setTvDiscovery("10");
        lastConfig.setGreenMode("60");
        lastConfig.setFastMode("10");
        lastConfig.setProfessionalService("10");
        lastConfig.setURL("");
        lastConfig.setManuallyLimitUpdate("true");
        lastConfig.setWebserviceURLIncorrectWarning("true");
        lastConfig.setTvSettingChangeWarning("true");
        lastConfig.setFirmwareOutOfDateWarning("true");
        lastConfig.setMaxTVUpdate("10");
        lastConfig.setUpdateTimeout("120");
        return lastConfig;
    }

    public static void updateIPProfile(String ... tvIds) {
        IPProfileConfig ipProfileConfig = Utils.getUserConfig();
        String checkBox = ipProfileConfig.getConfig(IPProfileConfig.IPProfileConfigName.TVs_tabsDevices_gridDevices_checkBox);
        String[] checkBoxArr = checkBox.split(",");
        int len = checkBoxArr.length;
        StringBuilder check = new StringBuilder();
        List<String> tvList = Arrays.asList(tvIds);
        for (int i = 0; i < len; ++i) {
            if (tvList.contains(checkBoxArr[i])) continue;
            check.append(checkBoxArr[i] + ",");
        }
        String checkString = check.toString();
        if (checkString.length() > 0) {
            checkString = checkString.substring(0, checkString.length() - 1);
        }
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(IPProfileConfig.IPProfileConfigName.TVs_tabsDevices_gridDevices_checkBox.name(), checkString);
        Utils.updateUserProfileConfig(params);
    }
}

