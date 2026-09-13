/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.tpvision.smartinstall.gateway.GatewayManager;
import com.tpvision.smartinstall.util.CommonConstants;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LastRFConfig {
    private static LastRFConfig mLastRFConfig;
    protected static final Map<String, Integer> BandwidthMap;
    protected static final Map<String, Integer> ConstellationMap;
    protected static final Map<String, Integer> GuardIntervalMap;
    protected static final Map<String, Integer> TransmissionModeMap;
    protected static final Map<String, Integer> CoderateMap;
    private static final Logger LOG;
    public String platformId;
    public String minRoom;
    public String maxRoom;
    public String frequency;
    public String modulation;
    public String bandwidth;
    public String outputLevel;
    public String lastPlayedId;
    public String rf_option_all;
    public String rf_option_MainFirmware;
    public String rf_option_TVSettings;
    public String rf_option_CustomDashboardFallback;
    public String rf_option_WelcomeLogo;
    public String rf_option_TVChannelList;
    public String rf_option_SmartInfoImages;
    public String rf_option_SmartInfoPages;
    public String rf_option_AndroidApps;
    public String rf_option_MediaChannels;
    public String rf_option_DataDump;
    public String rf_option_HTVCfg_xml;
    public String rf_option_RoomSpecificSettings;
    public String rf_option_AndroidScrip;
    public String rf_option_SSBFirmware;
    public String rf_option_StbySW;
    public String rf_option_SSIdent;
    public String rf_option_SSBNvm;
    public String rf_option_SSBChTab;
    public String rf_option_XMLCloning;
    public String rf_option_ThemeTv;
    public String rf_option_HotelInfo;
    public String rf_option_SmartUI;
    public String upg;
    public String onid;
    public String RoomString;
    public String RoomString2;
    public String upgradeAllRoom;
    public List<CardInfo> cards = new ArrayList<CardInfo>();
    public Integer cardIndex;
    public String guardInterval;
    public String transmissionMode;
    public String coderate;
    public boolean defaultToMGate;
    public OutputConfig outputConfig = new OutputConfig();
    public boolean gatewayStopped;

    public LastRFConfig() {
        this.outputConfig.output = "RF";
        this.outputConfig.ipSettings = new IPSettings();
    }

    public String getPlatformId() {
        return this.platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public String getMinRoom() {
        return this.minRoom;
    }

    public void setMinRoom(String minRoom) {
        this.minRoom = minRoom;
    }

    public String getMaxRoom() {
        return this.maxRoom;
    }

    public void setMaxRoom(String maxRoom) {
        this.maxRoom = maxRoom;
    }

    public String getFrequency() {
        return this.frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getModulation() {
        return this.modulation;
    }

    public String getBandwidth() {
        return this.bandwidth;
    }

    public void setBandwidth(String bandwidth) {
        this.bandwidth = bandwidth;
    }

    public void setModulation(String modulation) {
        this.modulation = modulation;
    }

    public String getOutputLevel() {
        return this.outputLevel;
    }

    public void setOutputLevel(String outputLevel) {
        this.outputLevel = outputLevel;
    }

    public String getLastPlayedId() {
        return this.lastPlayedId;
    }

    public void setLastPlayedId(String lastPlayedId) {
        this.lastPlayedId = lastPlayedId;
    }

    public String getUpgradeAllRoom() {
        return this.upgradeAllRoom;
    }

    public void setUpgradeAllRoom(String all) {
        this.upgradeAllRoom = all;
    }

    public String getUPG() {
        return this.upg;
    }

    public void setUPG(String iUpg) {
        this.upg = iUpg;
    }

    public String getONID() {
        return this.onid;
    }

    public void setONID(String iOnid) {
        this.onid = iOnid;
    }

    public String getRoomString() {
        return this.RoomString;
    }

    public void setRoomString(String RoomString) {
        this.RoomString = RoomString;
    }

    public String getRoomString2() {
        return this.RoomString2;
    }

    public void setRoomString2(String RoomString2) {
        this.RoomString2 = RoomString2;
    }

    public String getRf_option_all() {
        return this.rf_option_all;
    }

    public void setRf_option_all(String rf_option_all) {
        this.rf_option_all = rf_option_all;
    }

    public String getRf_option_MainFirmware() {
        return this.rf_option_MainFirmware;
    }

    public void setRf_option_MainFirmware(String rf_option_MainFirmware) {
        this.rf_option_MainFirmware = rf_option_MainFirmware;
    }

    public String getRf_option_TVSettings() {
        return this.rf_option_TVSettings;
    }

    public void setRf_option_TVSettings(String rf_option_TVSettings) {
        this.rf_option_TVSettings = rf_option_TVSettings;
    }

    public String getRf_option_CustomDashboardFallback() {
        return this.rf_option_CustomDashboardFallback;
    }

    public void setRf_option_CustomDashboardFallback(String rf_option_CustomDashboardFallback) {
        this.rf_option_CustomDashboardFallback = rf_option_CustomDashboardFallback;
    }

    public String getRf_option_WelcomeLogo() {
        return this.rf_option_WelcomeLogo;
    }

    public void setRf_option_WelcomeLogo(String rf_option_WelcomeLogo) {
        this.rf_option_WelcomeLogo = rf_option_WelcomeLogo;
    }

    public String getRf_option_TVChannelList() {
        return this.rf_option_TVChannelList;
    }

    public void setRf_option_TVChannelList(String rf_option_TVChannelList) {
        this.rf_option_TVChannelList = rf_option_TVChannelList;
    }

    public String getRf_option_SmartInfoImages() {
        return this.rf_option_SmartInfoImages;
    }

    public void setRf_option_SmartInfoImages(String rf_option_SmartInfoImages) {
        this.rf_option_SmartInfoImages = rf_option_SmartInfoImages;
    }

    public String getRf_option_SmartInfoPages() {
        return this.rf_option_SmartInfoPages;
    }

    public void setRf_option_SmartInfoPages(String rf_option_SmartInfoPages) {
        this.rf_option_SmartInfoPages = rf_option_SmartInfoPages;
    }

    public String getRf_option_AndroidApps() {
        return this.rf_option_AndroidApps;
    }

    public void setRf_option_AndroidApps(String rf_option_AndroidApps) {
        this.rf_option_AndroidApps = rf_option_AndroidApps;
    }

    public String getRf_option_MediaChannels() {
        return this.rf_option_MediaChannels;
    }

    public void setRf_option_MediaChannels(String rf_option_MediaChannels) {
        this.rf_option_MediaChannels = rf_option_MediaChannels;
    }

    public String getRf_option_DataDump() {
        return this.rf_option_DataDump;
    }

    public void setRf_option_DataDump(String rf_option_DataDump) {
        this.rf_option_DataDump = rf_option_DataDump;
    }

    public String getRf_option_HTVCfg_xml() {
        return this.rf_option_HTVCfg_xml;
    }

    public void setRf_option_HTVCfg_xml(String rf_option_HTVCfg_xml) {
        this.rf_option_HTVCfg_xml = rf_option_HTVCfg_xml;
    }

    public String getRf_option_RoomSpecificSettings() {
        return this.rf_option_RoomSpecificSettings;
    }

    public void setRf_option_RoomSpecificSettings(String rf_option_RoomSpecificSettings) {
        this.rf_option_RoomSpecificSettings = rf_option_RoomSpecificSettings;
    }

    public String getRf_option_AndroidScrip() {
        return this.rf_option_AndroidScrip;
    }

    public void setRf_option_AndroidScrip(String rf_option_AndroidScrip) {
        this.rf_option_AndroidScrip = rf_option_AndroidScrip;
    }

    public String getRf_option_SSBFirmware() {
        return this.rf_option_SSBFirmware;
    }

    public void setRf_option_SSBFirmware(String rf_option_SSBFirmware) {
        this.rf_option_SSBFirmware = rf_option_SSBFirmware;
    }

    public String getRf_option_StbySW() {
        return this.rf_option_StbySW;
    }

    public void setRf_option_StbySW(String rf_option_StbySW) {
        this.rf_option_StbySW = rf_option_StbySW;
    }

    public String getRf_option_SSIdent() {
        return this.rf_option_SSIdent;
    }

    public void setRf_option_SSIdent(String rf_option_SSIdent) {
        this.rf_option_SSIdent = rf_option_SSIdent;
    }

    public String getRf_option_SSBNvm() {
        return this.rf_option_SSBNvm;
    }

    public void setRf_option_SSBNvm(String rf_option_SSBNvm) {
        this.rf_option_SSBNvm = rf_option_SSBNvm;
    }

    public String getRf_option_SSBChTab() {
        return this.rf_option_SSBChTab;
    }

    public void setRf_option_SSBChTab(String rf_option_SSBChTab) {
        this.rf_option_SSBChTab = rf_option_SSBChTab;
    }

    public String getRf_option_XMLCloning() {
        return this.rf_option_XMLCloning;
    }

    public void setRf_option_XMLCloning(String rf_option_XMLCloning) {
        this.rf_option_XMLCloning = rf_option_XMLCloning;
    }

    public String getRf_option_ThemeTv() {
        return this.rf_option_ThemeTv;
    }

    public void setRf_option_ThemeTv(String rf_option_ThemeTv) {
        this.rf_option_ThemeTv = rf_option_ThemeTv;
    }

    public String getRf_option_HotelInfo() {
        return this.rf_option_HotelInfo;
    }

    public void setRf_option_HotelInfo(String rf_option_HotelInfo) {
        this.rf_option_HotelInfo = rf_option_HotelInfo;
    }

    public String getRf_option_SmartUI() {
        return this.rf_option_SmartUI;
    }

    public void setRf_option_SmartUI(String rf_option_SmartUI) {
        this.rf_option_SmartUI = rf_option_SmartUI;
    }

    public String getRFOptions_2K16ES() {
        ArrayList<String> options_2K16ES = new ArrayList<String>();
        if ("true".equalsIgnoreCase(this.rf_option_MainFirmware)) {
            options_2K16ES.add("MainFirmware");
        }
        if ("true".equalsIgnoreCase(this.rf_option_TVSettings)) {
            options_2K16ES.add("TVSettings");
        }
        if ("true".equalsIgnoreCase(this.rf_option_CustomDashboardFallback)) {
            options_2K16ES.add("LocalCustomDashboard");
        }
        if ("true".equalsIgnoreCase(this.rf_option_WelcomeLogo)) {
            options_2K16ES.add("WelcomeLogo");
        }
        if ("true".equalsIgnoreCase(this.rf_option_TVChannelList)) {
            options_2K16ES.add("ChannelList");
        }
        if ("true".equalsIgnoreCase(this.rf_option_SmartInfoImages)) {
            options_2K16ES.add("SmartInfoShow");
        }
        if ("true".equalsIgnoreCase(this.rf_option_SmartInfoPages)) {
            options_2K16ES.add("SmartInfoBrowser");
        }
        if ("true".equalsIgnoreCase(this.rf_option_MediaChannels)) {
            options_2K16ES.add("MediaChannels");
        }
        if ("true".equalsIgnoreCase(this.rf_option_DataDump)) {
            options_2K16ES.add("DataDump");
        }
        if ("true".equalsIgnoreCase(this.rf_option_HTVCfg_xml)) {
            options_2K16ES.add("HtvCfg.xml");
        }
        return String.join((CharSequence)",", options_2K16ES);
    }

    public String getRFOptions_2K16SSMS() {
        ArrayList<String> options_2K16SSMS = new ArrayList<String>();
        if ("true".equalsIgnoreCase(this.rf_option_MainFirmware)) {
            options_2K16SSMS.add("MainFirmware");
        }
        if ("true".equalsIgnoreCase(this.rf_option_TVSettings)) {
            options_2K16SSMS.add("TVSettings");
        }
        if ("true".equalsIgnoreCase(this.rf_option_CustomDashboardFallback)) {
            options_2K16SSMS.add("LocalCustomDashboard");
        }
        if ("true".equalsIgnoreCase(this.rf_option_WelcomeLogo)) {
            options_2K16SSMS.add("WelcomeLogo");
        }
        if ("true".equalsIgnoreCase(this.rf_option_TVChannelList)) {
            options_2K16SSMS.add("ChannelList");
        }
        if ("true".equalsIgnoreCase(this.rf_option_SmartInfoImages)) {
            options_2K16SSMS.add("SmartInfoShow");
        }
        if ("true".equalsIgnoreCase(this.rf_option_SmartInfoPages)) {
            options_2K16SSMS.add("SmartInfoBrowser");
        }
        if ("true".equalsIgnoreCase(this.rf_option_AndroidApps)) {
            options_2K16SSMS.add("AndroidApps");
        }
        if ("true".equalsIgnoreCase(this.rf_option_MediaChannels)) {
            options_2K16SSMS.add("MediaChannels");
        }
        if ("true".equalsIgnoreCase(this.rf_option_DataDump)) {
            options_2K16SSMS.add("DataDump");
        }
        if ("true".equalsIgnoreCase(this.rf_option_HTVCfg_xml)) {
            options_2K16SSMS.add("HtvCfg.xml");
        }
        if ("true".equalsIgnoreCase(this.rf_option_RoomSpecificSettings)) {
            options_2K16SSMS.add("RoomSpecificSettings");
        }
        if ("true".equalsIgnoreCase(this.rf_option_AndroidScrip)) {
            options_2K16SSMS.add("AndroidScript");
        }
        return String.join((CharSequence)",", options_2K16SSMS);
    }

    private static CardInfo getDefaultCardInfo() {
        CardInfo cardInfo = new CardInfo();
        cardInfo.RF = "TRUE";
        cardInfo.frequency.max = 2186;
        cardInfo.frequency.min = 32;
        cardInfo.modulation.max = 72;
        cardInfo.modulation.min = 0;
        cardInfo.outputLevel.max = 0;
        cardInfo.outputLevel.min = -60;
        cardInfo.type = 2111;
        cardInfo.id = 1;
        return cardInfo;
    }

    private Map<String, Integer> getMapByType(ConfigType configType) {
        Map<String, Integer> configMap = null;
        switch (configType) {
            case BANDWIDTH: {
                configMap = BandwidthMap;
                break;
            }
            case CONSTELLATION: {
                configMap = ConstellationMap;
                break;
            }
            case GUARDINTERVAL: {
                configMap = GuardIntervalMap;
                break;
            }
            case CODERATE: {
                configMap = CoderateMap;
                break;
            }
            case TRANSMISSIONMODE: {
                configMap = TransmissionModeMap;
            }
        }
        return configMap;
    }

    public Integer getMapValue(ConfigType configType, String key) {
        Map<String, Integer> configMap = this.getMapByType(configType);
        if (null != configMap && configMap.containsKey(key)) {
            return configMap.get(key);
        }
        return 0;
    }

    public String getMapString(ConfigType configType, int index) {
        Map<String, Integer> configMap = this.getMapByType(configType);
        if (null != configMap) {
            int i = 0;
            for (Map.Entry<String, Integer> entry : configMap.entrySet()) {
                if (i == index) {
                    return entry.getKey();
                }
                ++i;
            }
        }
        return "";
    }

    public static LastRFConfig loadConfig() {
        LOG.info("load last Config");
        File f = new File(CommonConstants.LAST_CONFIG_FILE_LOCATION);
        Gson gson = new Gson();
        LastRFConfig lastConfig = null;
        if (f.exists()) {
            try {
                CardInfo[] cardInfoList;
                lastConfig = gson.fromJson((Reader)new FileReader(f), LastRFConfig.class);
                String cardsStr = GatewayManager.getInstance().getOutputPorts();
                if (null != cardsStr && null != (cardInfoList = gson.fromJson(cardsStr, CardInfo[].class)) && cardInfoList.length > 0) {
                    lastConfig.cards.clear();
                    for (CardInfo cardInfo : cardInfoList) {
                        lastConfig.cards.add(cardInfo);
                    }
                    lastConfig.cardIndex = cardInfoList[0].id;
                }
                if (lastConfig.cards.isEmpty()) {
                    CardInfo cardInfo = LastRFConfig.getDefaultCardInfo();
                    lastConfig.cards.clear();
                    lastConfig.cards.add(cardInfo);
                    lastConfig.cardIndex = cardInfo.id;
                }
            }
            catch (JsonSyntaxException e) {
                LOG.error(e.getMessage(), e);
                lastConfig = null;
            }
            catch (JsonIOException e) {
                LOG.error(e.getMessage(), e);
            }
            catch (FileNotFoundException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        if (null == lastConfig) {
            lastConfig = LastRFConfig.loadDefaultData();
            lastConfig.save();
        }
        return lastConfig;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public void save() {
        File f = new File(CommonConstants.LAST_CONFIG_FILE_LOCATION);
        String jsonStr = new Gson().toJson(this);
        try {
            FileUtils.writeStringToFile(f, jsonStr, StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static LastRFConfig loadDefaultData() {
        LastRFConfig lastConfig = new LastRFConfig();
        lastConfig.setFrequency("706");
        lastConfig.setLastPlayedId("");
        lastConfig.setMaxRoom("ffff");
        lastConfig.setMinRoom("ffff");
        lastConfig.setModulation("64-QAM");
        lastConfig.setBandwidth("8");
        lastConfig.setOutputLevel("-18");
        lastConfig.setPlatformId("");
        lastConfig.setRf_option_all("true");
        lastConfig.setRf_option_MainFirmware("false");
        lastConfig.setRf_option_AndroidApps("false");
        lastConfig.setRf_option_CustomDashboardFallback("false");
        lastConfig.setRf_option_DataDump("false");
        lastConfig.setRf_option_HotelInfo("false");
        lastConfig.setRf_option_HTVCfg_xml("false");
        lastConfig.setRf_option_MediaChannels("false");
        lastConfig.setRf_option_RoomSpecificSettings("false");
        lastConfig.setRf_option_AndroidScrip("false");
        lastConfig.setRf_option_SmartInfoImages("false");
        lastConfig.setRf_option_SmartInfoPages("false");
        lastConfig.setRf_option_SmartUI("false");
        lastConfig.setRf_option_SSBChTab("false");
        lastConfig.setRf_option_SSBFirmware("false");
        lastConfig.setRf_option_SSBNvm("false");
        lastConfig.setRf_option_SSIdent("false");
        lastConfig.setRf_option_StbySW("false");
        lastConfig.setRf_option_ThemeTv("false");
        lastConfig.setRf_option_TVChannelList("false");
        lastConfig.setRf_option_TVSettings("false");
        lastConfig.setRf_option_WelcomeLogo("false");
        lastConfig.setRf_option_XMLCloning("false");
        lastConfig.setUPG("-");
        lastConfig.setONID("Others-233A");
        lastConfig.setUpgradeAllRoom("true");
        CardInfo cardInfo = LastRFConfig.getDefaultCardInfo();
        lastConfig.cards.add(cardInfo);
        lastConfig.cardIndex = cardInfo.id;
        return lastConfig;
    }

    public static LastRFConfig loadLastConfig() {
        if (null == mLastRFConfig) {
            mLastRFConfig = LastRFConfig.loadConfig();
        }
        return mLastRFConfig;
    }

    static {
        BandwidthMap = new LinkedHashMap<String, Integer>(){
            private static final long serialVersionUID = 7995031771069156976L;
            {
                this.put("5", 1);
                this.put("6", 2);
                this.put("7", 3);
                this.put("8", 4);
            }
        };
        ConstellationMap = new LinkedHashMap<String, Integer>(){
            private static final long serialVersionUID = 2603898740498597080L;
            {
                this.put("QPSK", 16);
                this.put("16-QAM", 32);
                this.put("64-QAM", 48);
            }
        };
        GuardIntervalMap = new LinkedHashMap<String, Integer>(){
            private static final long serialVersionUID = -8118642672653865738L;
            {
                this.put("1/32", 256);
                this.put("1/16", 512);
                this.put("1/8", 768);
                this.put("1/4", 1024);
            }
        };
        TransmissionModeMap = new LinkedHashMap<String, Integer>(){
            private static final long serialVersionUID = -8118642672653865038L;
            {
                this.put("2k", 65536);
                this.put("4k", 131072);
                this.put("8k", 196608);
            }
        };
        CoderateMap = new LinkedHashMap<String, Integer>(){
            private static final long serialVersionUID = -8118642672622865038L;
            {
                this.put("1/2", 0);
                this.put("2/3", 1);
                this.put("3/4", 2);
                this.put("4/5", 3);
                this.put("5/6", 4);
                this.put("6/7", 5);
                this.put("7/8", 6);
                this.put("1/4", 7);
                this.put("1/3", 8);
                this.put("2/5", 9);
                this.put("3/5", 10);
                this.put("8/9", 11);
                this.put("9/10", 12);
            }
        };
        LOG = LoggerFactory.getLogger(LastRFConfig.class);
    }

    public static class RangeConfig {
        public Integer max;
        public Integer min;
    }

    public static class OutputConfig {
        public String output;
        public IPSettings ipSettings;
        public String fileName;
        public Integer tsRate;
        public String rfMode;
        public Integer vsb;
        public Integer ASIPortIndex;
    }

    public static class IPSettings {
        public String localInterface;
        public String destinationAddress;
        public int port;
        public int ttl;
    }

    public static class CardInfo {
        public String RF;
        public RangeConfig frequency = new RangeConfig();
        public RangeConfig modulation = new RangeConfig();
        public RangeConfig outputLevel = new RangeConfig();
        public Integer type;
        public Integer id;
    }

    public static enum ConfigType {
        BANDWIDTH,
        CONSTELLATION,
        GUARDINTERVAL,
        TRANSMISSIONMODE,
        CODERATE;

    }
}

