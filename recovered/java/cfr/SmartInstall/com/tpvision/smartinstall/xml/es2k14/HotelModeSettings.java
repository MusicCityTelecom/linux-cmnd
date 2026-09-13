/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.xml.es2k14;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="", propOrder={"dashboard", "switchOnSrc", "SwitchOnChn", "switchOnVol", "maximumVol", "switchOnFeature", "switchOnPicFmt", "powerOn", "lowPowerStandby", "smartPower", "rebootEveryDay", "wakeOnLAN", "displayWelcomeMsg", "welcomeMsgLine1", "welcomeMsgLine2", "welcomeMsgTimeOut", "displayLogo", "smartInfo", "smartInfoIconLabel", "kbLock", "rcLock", "osdDisplay", "highSecurity", "autoScart", "usbBreakIn", "enableUSB", "sxpBaudRate", "enableTeletext", "enableMHEG", "enableEPG", "enableSubtitles", "subtitleOnStartup", "blueMute", "enableCISlot", "wiFiCrossConnect", "wiFiMiraCast", "directShare", "scrambledProgramOSD", "wiFiLostOSD", "jointSpace", "easylinkBreakIn", "easylinkControl", "enableSkype", "digitTimeout", "selectableAV", "watchTV", "externalClk", "clkBrighDimlight", "clkBrighIntenselight", "clkLightSensor", "timeDownload", "timeSetting", "clkNTPSvrURL", "clkDownloadProgram", "clkDownloadCountry", "clkTimeZone", "daylightSaving", "clkTimeOffset", "referenceDate", "referenceTime", "mainSpkrEnable", "indMainSpkrMute", "defMainSpkrVol", "autoChnUpdate", "autoSwUpdate", "skipScrambled", "multiRC", "myChoice", "askForPIN", "smartPay", "av", "smartTV", "appControlID", "profileName", "source", "fallback", "dashboardIconLabel", "serverUIURL", "webServicesURL", "tvDiscoveryService", "professionalSettingsService", "ipUpgradeService", "powerService", "vsecOverRFEnable", "eraseKeyOption", "vsecFrequency", "vsecMedium", "vsecSymbolRate", "rfclFrequency", "rfclMedium", "rfclSymbolRate", "upgradeMode", "autoUpgrade", "installationMode", "cloneMultiRC"})
@XmlRootElement(name="HotelModeSettings")
public class HotelModeSettings {
    @XmlElement(name="Dashboard", required=true)
    protected String dashboard;
    @XmlElement(name="SwitchOnSrc", required=true)
    protected String switchOnSrc;
    @XmlElement(name="SwitchOnChn", required=true)
    protected String SwitchOnChn;
    @XmlElement(name="SwitchOnVol", required=true)
    protected String switchOnVol;
    @XmlElement(name="MaximumVol", required=true)
    protected String maximumVol;
    @XmlElement(name="SwitchOnFeature", required=true)
    protected String switchOnFeature;
    @XmlElement(name="SwitchOnPicFmt", required=true)
    protected String switchOnPicFmt;
    @XmlElement(name="PowerOn", required=true)
    protected String powerOn;
    @XmlElement(name="LowPowerStandby", required=true)
    protected String lowPowerStandby;
    @XmlElement(name="SmartPower", required=true)
    protected String smartPower;
    @XmlElement(name="RebootEveryDay", required=true)
    protected String rebootEveryDay;
    @XmlElement(name="WakeOnLAN", required=true)
    protected String wakeOnLAN;
    @XmlElement(name="DisplayWelcomeMsg", required=true)
    protected String displayWelcomeMsg;
    @XmlElement(name="WelcomeMsgLine1", required=true)
    protected String welcomeMsgLine1;
    @XmlElement(name="WelcomeMsgLine2", required=true)
    protected String welcomeMsgLine2;
    @XmlElement(name="WelcomeMsgTimeOut", required=true)
    protected String welcomeMsgTimeOut;
    @XmlElement(name="DisplayLogo", required=true)
    protected String displayLogo;
    @XmlElement(name="SmartInfo", required=true)
    protected String smartInfo;
    @XmlElement(name="SmartInfoIconLabel", required=true)
    protected String smartInfoIconLabel;
    @XmlElement(name="KBLock", required=true)
    protected String kbLock;
    @XmlElement(name="RCLock", required=true)
    protected String rcLock;
    @XmlElement(name="OSDDisplay", required=true)
    protected String osdDisplay;
    @XmlElement(name="HighSecurity", required=true)
    protected String highSecurity;
    @XmlElement(name="AutoScart", required=true)
    protected String autoScart;
    @XmlElement(name="USBBreakIn", required=true)
    protected String usbBreakIn;
    @XmlElement(name="EnableUSB", required=true)
    protected String enableUSB;
    @XmlElement(name="SXPBaudRate", required=true)
    protected String sxpBaudRate;
    @XmlElement(name="EnableTeletext", required=true)
    protected String enableTeletext;
    @XmlElement(name="EnableMHEG", required=true)
    protected String enableMHEG;
    @XmlElement(name="EnableEPG", required=true)
    protected String enableEPG;
    @XmlElement(name="EnableSubtitles", required=true)
    protected String enableSubtitles;
    @XmlElement(name="SubtitleOnStartup", required=true)
    protected String subtitleOnStartup;
    @XmlElement(name="BlueMute", required=true)
    protected String blueMute;
    @XmlElement(name="EnableCISlot", required=true)
    protected String enableCISlot;
    @XmlElement(name="WiFiCrossConnect", required=true)
    protected String wiFiCrossConnect;
    @XmlElement(name="WiFiMiraCast", required=true)
    protected String wiFiMiraCast;
    @XmlElement(name="DirectShare", required=true)
    protected String directShare;
    @XmlElement(name="ScrambledProgramOSD", required=true)
    protected String scrambledProgramOSD;
    @XmlElement(name="WiFiLostOSD", required=true)
    protected String wiFiLostOSD;
    @XmlElement(name="JointSpace", required=true)
    protected String jointSpace;
    @XmlElement(name="EasylinkBreakIn", required=true)
    protected String easylinkBreakIn;
    @XmlElement(name="EasylinkControl", required=true)
    protected String easylinkControl;
    @XmlElement(name="EnableSkype", required=true)
    protected String enableSkype;
    @XmlElement(name="DigitTimeout", required=true)
    protected String digitTimeout;
    @XmlElement(name="SelectableAV", required=true)
    protected String selectableAV;
    @XmlElement(name="WatchTV", required=true)
    protected String watchTV;
    @XmlElement(name="ExternalClk", required=true)
    protected String externalClk;
    @XmlElement(name="ClkBrighDimlight", required=true)
    protected String clkBrighDimlight;
    @XmlElement(name="ClkBrighIntenselight", required=true)
    protected String clkBrighIntenselight;
    @XmlElement(name="ClkLightSensor", required=true)
    protected String clkLightSensor;
    @XmlElement(name="TimeDownload", required=true)
    protected String timeDownload;
    @XmlElement(name="TimeSetting", required=true)
    protected String timeSetting;
    @XmlElement(name="ClkNTPSvrURL", required=true)
    protected String clkNTPSvrURL;
    @XmlElement(name="ClkDownloadProgram", required=true)
    protected String clkDownloadProgram;
    @XmlElement(name="ClkDownloadCountry", required=true)
    protected String clkDownloadCountry;
    @XmlElement(name="ClkTimeZone", required=true)
    protected String clkTimeZone;
    @XmlElement(name="DaylightSaving", required=true)
    protected String daylightSaving;
    @XmlElement(name="ClkTimeOffset", required=true)
    protected String clkTimeOffset;
    @XmlElement(name="ReferenceDate", required=true)
    protected String referenceDate;
    @XmlElement(name="ReferenceTime", required=true)
    protected String referenceTime;
    @XmlElement(name="MainSpkrEnable", required=true)
    protected String mainSpkrEnable;
    @XmlElement(name="IndMainSpkrMute", required=true)
    protected String indMainSpkrMute;
    @XmlElement(name="DefMainSpkrVol", required=true)
    protected String defMainSpkrVol;
    @XmlElement(name="AutoChnUpdate", required=true)
    protected String autoChnUpdate;
    @XmlElement(name="AutoSwUpdate", required=true)
    protected String autoSwUpdate;
    @XmlElement(name="SkipScrambled", required=true)
    protected String skipScrambled;
    @XmlElement(name="MultiRC", required=true)
    protected String multiRC;
    @XmlElement(name="MyChoice", required=true)
    protected String myChoice;
    @XmlElement(name="AskForPIN", required=true)
    protected String askForPIN;
    @XmlElement(name="SmartPay", required=true)
    protected String smartPay;
    @XmlElement(name="AV", required=true)
    protected String av;
    @XmlElement(name="SmartTV", required=true)
    protected String smartTV;
    @XmlElement(name="AppControlID", required=true)
    protected String appControlID;
    @XmlElement(name="ProfileName", required=true)
    protected String profileName;
    @XmlElement(name="Source", required=true)
    protected String source;
    @XmlElement(name="Fallback", required=true)
    protected String fallback;
    @XmlElement(name="DashboardIconLabel", required=true)
    protected String dashboardIconLabel;
    @XmlElement(name="ServerUIURL", required=true)
    protected String serverUIURL;
    @XmlElement(name="WebServicesURL", required=true)
    protected String webServicesURL;
    @XmlElement(name="TVDiscoveryService", required=true)
    protected String tvDiscoveryService;
    @XmlElement(name="ProfessionalSettingsService", required=true)
    protected String professionalSettingsService;
    @XmlElement(name="IPUpgradeService", required=true)
    protected String ipUpgradeService;
    @XmlElement(name="PowerService", required=true)
    protected String powerService;
    @XmlElement(name="VsecOverRFEnable", required=true)
    protected String vsecOverRFEnable;
    @XmlElement(name="EraseKeyOption", required=true)
    protected String eraseKeyOption;
    @XmlElement(name="VsecFrequency", required=true)
    protected String vsecFrequency;
    @XmlElement(name="VsecMedium", required=true)
    protected String vsecMedium;
    @XmlElement(name="VsecSymbolRate", required=true)
    protected String vsecSymbolRate;
    @XmlElement(name="RFCLFrequency", required=true)
    protected String rfclFrequency;
    @XmlElement(name="RFCLMedium", required=true)
    protected String rfclMedium;
    @XmlElement(name="RFCLSymbolRate", required=true)
    protected String rfclSymbolRate;
    @XmlElement(name="UpgradeMode", required=true)
    protected String upgradeMode;
    @XmlElement(name="AutoUpgrade", required=true)
    protected String autoUpgrade;
    @XmlElement(name="InstallationMode", required=true)
    protected String installationMode;
    @XmlElement(name="CloneMultiRC", required=true)
    protected String cloneMultiRC;

    public String getDashboard() {
        return this.dashboard;
    }

    public void setDashboard(String value) {
        this.dashboard = value;
    }

    public String getSwitchOnSrc() {
        return this.switchOnSrc;
    }

    public void setSwitchOnSrc(String value) {
        this.switchOnSrc = value;
    }

    public String getSwitchOnChn() {
        return this.SwitchOnChn;
    }

    public void setSwitchOnChn(String value) {
        this.SwitchOnChn = value;
    }

    public String getSwitchOnVol() {
        return this.switchOnVol;
    }

    public void setSwitchOnVol(String value) {
        this.switchOnVol = value;
    }

    public String getMaximumVol() {
        return this.maximumVol;
    }

    public void setMaximumVol(String value) {
        this.maximumVol = value;
    }

    public String getSwitchOnFeature() {
        return this.switchOnFeature;
    }

    public void setSwitchOnFeature(String value) {
        this.switchOnFeature = value;
    }

    public String getSwitchOnPicFmt() {
        return this.switchOnPicFmt;
    }

    public void setSwitchOnPicFmt(String value) {
        this.switchOnPicFmt = value;
    }

    public String getPowerOn() {
        return this.powerOn;
    }

    public void setPowerOn(String value) {
        this.powerOn = value;
    }

    public String getLowPowerStandby() {
        return this.lowPowerStandby;
    }

    public void setLowPowerStandby(String value) {
        this.lowPowerStandby = value;
    }

    public String getSmartPower() {
        return this.smartPower;
    }

    public void setSmartPower(String value) {
        this.smartPower = value;
    }

    public String getRebootEveryDay() {
        return this.rebootEveryDay;
    }

    public void setRebootEveryDay(String value) {
        this.rebootEveryDay = value;
    }

    public String getWakeOnLAN() {
        return this.wakeOnLAN;
    }

    public void setWakeOnLAN(String value) {
        this.wakeOnLAN = value;
    }

    public String getDisplayWelcomeMsg() {
        return this.displayWelcomeMsg;
    }

    public void setDisplayWelcomeMsg(String value) {
        this.displayWelcomeMsg = value;
    }

    public String getWelcomeMsgLine1() {
        return this.welcomeMsgLine1;
    }

    public void setWelcomeMsgLine1(String value) {
        this.welcomeMsgLine1 = value;
    }

    public String getWelcomeMsgLine2() {
        return this.welcomeMsgLine2;
    }

    public void setWelcomeMsgLine2(String value) {
        this.welcomeMsgLine2 = value;
    }

    public String getWelcomeMsgTimeOut() {
        return this.welcomeMsgTimeOut;
    }

    public void setWelcomeMsgTimeOut(String value) {
        this.welcomeMsgTimeOut = value;
    }

    public String getDisplayLogo() {
        return this.displayLogo;
    }

    public void setDisplayLogo(String value) {
        this.displayLogo = value;
    }

    public String getSmartInfo() {
        return this.smartInfo;
    }

    public void setSmartInfo(String value) {
        this.smartInfo = value;
    }

    public String getSmartInfoIconLabel() {
        return this.smartInfoIconLabel;
    }

    public void setSmartInfoIconLabel(String value) {
        this.smartInfoIconLabel = value;
    }

    public String getKBLock() {
        return this.kbLock;
    }

    public void setKBLock(String value) {
        this.kbLock = value;
    }

    public String getRCLock() {
        return this.rcLock;
    }

    public void setRCLock(String value) {
        this.rcLock = value;
    }

    public String getOSDDisplay() {
        return this.osdDisplay;
    }

    public void setOSDDisplay(String value) {
        this.osdDisplay = value;
    }

    public String getHighSecurity() {
        return this.highSecurity;
    }

    public void setHighSecurity(String value) {
        this.highSecurity = value;
    }

    public String getAutoScart() {
        return this.autoScart;
    }

    public void setAutoScart(String value) {
        this.autoScart = value;
    }

    public String getUSBBreakIn() {
        return this.usbBreakIn;
    }

    public void setUSBBreakIn(String value) {
        this.usbBreakIn = value;
    }

    public String getEnableUSB() {
        return this.enableUSB;
    }

    public void setEnableUSB(String value) {
        this.enableUSB = value;
    }

    public String getSXPBaudRate() {
        return this.sxpBaudRate;
    }

    public void setSXPBaudRate(String value) {
        this.sxpBaudRate = value;
    }

    public String getEnableTeletext() {
        return this.enableTeletext;
    }

    public void setEnableTeletext(String value) {
        this.enableTeletext = value;
    }

    public String getEnableMHEG() {
        return this.enableMHEG;
    }

    public void setEnableMHEG(String value) {
        this.enableMHEG = value;
    }

    public String getEnableEPG() {
        return this.enableEPG;
    }

    public void setEnableEPG(String value) {
        this.enableEPG = value;
    }

    public String getEnableSubtitles() {
        return this.enableSubtitles;
    }

    public void setEnableSubtitles(String value) {
        this.enableSubtitles = value;
    }

    public String getSubtitleOnStartup() {
        return this.subtitleOnStartup;
    }

    public void setSubtitleOnStartup(String value) {
        this.subtitleOnStartup = value;
    }

    public String getBlueMute() {
        return this.blueMute;
    }

    public void setBlueMute(String value) {
        this.blueMute = value;
    }

    public String getEnableCISlot() {
        return this.enableCISlot;
    }

    public void setEnableCISlot(String value) {
        this.enableCISlot = value;
    }

    public String getWiFiCrossConnect() {
        return this.wiFiCrossConnect;
    }

    public void setWiFiCrossConnect(String value) {
        this.wiFiCrossConnect = value;
    }

    public String getWiFiMiraCast() {
        return this.wiFiMiraCast;
    }

    public void setWiFiMiraCast(String value) {
        this.wiFiMiraCast = value;
    }

    public String getDirectShare() {
        return this.directShare;
    }

    public void setDirectShare(String value) {
        this.directShare = value;
    }

    public String getScrambledProgramOSD() {
        return this.scrambledProgramOSD;
    }

    public void setScrambledProgramOSD(String value) {
        this.scrambledProgramOSD = value;
    }

    public String getWiFiLostOSD() {
        return this.wiFiLostOSD;
    }

    public void setWiFiLostOSD(String value) {
        this.wiFiLostOSD = value;
    }

    public String getJointSpace() {
        return this.jointSpace;
    }

    public void setJointSpace(String value) {
        this.jointSpace = value;
    }

    public String getEasylinkBreakIn() {
        return this.easylinkBreakIn;
    }

    public void setEasylinkBreakIn(String value) {
        this.easylinkBreakIn = value;
    }

    public String getEasylinkControl() {
        return this.easylinkControl;
    }

    public void setEasylinkControl(String value) {
        this.easylinkControl = value;
    }

    public String getEnableSkype() {
        return this.enableSkype;
    }

    public void setEnableSkype(String value) {
        this.enableSkype = value;
    }

    public String getDigitTimeout() {
        return this.digitTimeout;
    }

    public void setDigitTimeout(String value) {
        this.digitTimeout = value;
    }

    public String getSelectableAV() {
        return this.selectableAV;
    }

    public void setSelectableAV(String value) {
        this.selectableAV = value;
    }

    public String getWatchTV() {
        return this.watchTV;
    }

    public void setWatchTV(String value) {
        this.watchTV = value;
    }

    public String getExternalClk() {
        return this.externalClk;
    }

    public void setExternalClk(String value) {
        this.externalClk = value;
    }

    public String getClkBrighDimlight() {
        return this.clkBrighDimlight;
    }

    public void setClkBrighDimlight(String value) {
        this.clkBrighDimlight = value;
    }

    public String getClkBrighIntenselight() {
        return this.clkBrighIntenselight;
    }

    public void setClkBrighIntenselight(String value) {
        this.clkBrighIntenselight = value;
    }

    public String getClkLightSensor() {
        return this.clkLightSensor;
    }

    public void setClkLightSensor(String value) {
        this.clkLightSensor = value;
    }

    public String getTimeDownload() {
        return this.timeDownload;
    }

    public void setTimeDownload(String value) {
        this.timeDownload = value;
    }

    public String getTimeSetting() {
        return this.timeSetting;
    }

    public void setTimeSetting(String value) {
        this.timeSetting = value;
    }

    public String getClkNTPSvrURL() {
        return this.clkNTPSvrURL;
    }

    public void setClkNTPSvrURL(String value) {
        this.clkNTPSvrURL = value;
    }

    public String getClkDownloadProgram() {
        return this.clkDownloadProgram;
    }

    public void setClkDownloadProgram(String value) {
        this.clkDownloadProgram = value;
    }

    public String getClkDownloadCountry() {
        return this.clkDownloadCountry;
    }

    public void setClkDownloadCountry(String value) {
        this.clkDownloadCountry = value;
    }

    public String getClkTimeZone() {
        return this.clkTimeZone;
    }

    public void setClkTimeZone(String value) {
        this.clkTimeZone = value;
    }

    public String getDaylightSaving() {
        return this.daylightSaving;
    }

    public void setDaylightSaving(String value) {
        this.daylightSaving = value;
    }

    public String getClkTimeOffset() {
        return this.clkTimeOffset;
    }

    public void setClkTimeOffset(String value) {
        this.clkTimeOffset = value;
    }

    public String getReferenceDate() {
        return this.referenceDate;
    }

    public void setReferenceDate(String value) {
        this.referenceDate = value;
    }

    public String getReferenceTime() {
        return this.referenceTime;
    }

    public void setReferenceTime(String value) {
        this.referenceTime = value;
    }

    public String getMainSpkrEnable() {
        return this.mainSpkrEnable;
    }

    public void setMainSpkrEnable(String value) {
        this.mainSpkrEnable = value;
    }

    public String getIndMainSpkrMute() {
        return this.indMainSpkrMute;
    }

    public void setIndMainSpkrMute(String value) {
        this.indMainSpkrMute = value;
    }

    public String getDefMainSpkrVol() {
        return this.defMainSpkrVol;
    }

    public void setDefMainSpkrVol(String value) {
        this.defMainSpkrVol = value;
    }

    public String getAutoChnUpdate() {
        return this.autoChnUpdate;
    }

    public void setAutoChnUpdate(String value) {
        this.autoChnUpdate = value;
    }

    public String getAutoSwUpdate() {
        return this.autoSwUpdate;
    }

    public void setAutoSwUpdate(String value) {
        this.autoSwUpdate = value;
    }

    public String getSkipScrambled() {
        return this.skipScrambled;
    }

    public void setSkipScrambled(String value) {
        this.skipScrambled = value;
    }

    public String getMultiRC() {
        return this.multiRC;
    }

    public void setMultiRC(String value) {
        this.multiRC = value;
    }

    public String getMyChoice() {
        return this.myChoice;
    }

    public void setMyChoice(String value) {
        this.myChoice = value;
    }

    public String getAskForPIN() {
        return this.askForPIN;
    }

    public void setAskForPIN(String value) {
        this.askForPIN = value;
    }

    public String getSmartPay() {
        return this.smartPay;
    }

    public void setSmartPay(String value) {
        this.smartPay = value;
    }

    public String getAV() {
        return this.av;
    }

    public void setAV(String value) {
        this.av = value;
    }

    public String getSmartTV() {
        return this.smartTV;
    }

    public void setSmartTV(String value) {
        this.smartTV = value;
    }

    public String getAppControlID() {
        return this.appControlID;
    }

    public void setAppControlID(String value) {
        this.appControlID = value;
    }

    public String getProfileName() {
        return this.profileName;
    }

    public void setProfileName(String value) {
        this.profileName = value;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String value) {
        this.source = value;
    }

    public String getFallback() {
        return this.fallback;
    }

    public void setFallback(String value) {
        this.fallback = value;
    }

    public String getDashboardIconLabel() {
        return this.dashboardIconLabel;
    }

    public void setDashboardIconLabel(String value) {
        this.dashboardIconLabel = value;
    }

    public String getServerUIURL() {
        return this.serverUIURL;
    }

    public void setServerUIURL(String value) {
        this.serverUIURL = value;
    }

    public String getWebServicesURL() {
        return this.webServicesURL;
    }

    public void setWebServicesURL(String value) {
        this.webServicesURL = value;
    }

    public String getTVDiscoveryService() {
        return this.tvDiscoveryService;
    }

    public void setTVDiscoveryService(String value) {
        this.tvDiscoveryService = value;
    }

    public String getProfessionalSettingsService() {
        return this.professionalSettingsService;
    }

    public void setProfessionalSettingsService(String value) {
        this.professionalSettingsService = value;
    }

    public String getIPUpgradeService() {
        return this.ipUpgradeService;
    }

    public void setIPUpgradeService(String value) {
        this.ipUpgradeService = value;
    }

    public String getPowerService() {
        return this.powerService;
    }

    public void setPowerService(String value) {
        this.powerService = value;
    }

    public String getVsecOverRFEnable() {
        return this.vsecOverRFEnable;
    }

    public void setVsecOverRFEnable(String value) {
        this.vsecOverRFEnable = value;
    }

    public String getEraseKeyOption() {
        return this.eraseKeyOption;
    }

    public void setEraseKeyOption(String value) {
        this.eraseKeyOption = value;
    }

    public String getVsecFrequency() {
        return this.vsecFrequency;
    }

    public void setVsecFrequency(String value) {
        this.vsecFrequency = value;
    }

    public String getVsecMedium() {
        return this.vsecMedium;
    }

    public void setVsecMedium(String value) {
        this.vsecMedium = value;
    }

    public String getVsecSymbolRate() {
        return this.vsecSymbolRate;
    }

    public void setVsecSymbolRate(String value) {
        this.vsecSymbolRate = value;
    }

    public String getRFCLFrequency() {
        return this.rfclFrequency;
    }

    public void setRFCLFrequency(String value) {
        this.rfclFrequency = value;
    }

    public String getRFCLMedium() {
        return this.rfclMedium;
    }

    public void setRFCLMedium(String value) {
        this.rfclMedium = value;
    }

    public String getRFCLSymbolRate() {
        return this.rfclSymbolRate;
    }

    public void setRFCLSymbolRate(String value) {
        this.rfclSymbolRate = value;
    }

    public String getUpgradeMode() {
        return this.upgradeMode;
    }

    public void setUpgradeMode(String value) {
        this.upgradeMode = value;
    }

    public String getAutoUpgrade() {
        return this.autoUpgrade;
    }

    public void setAutoUpgrade(String value) {
        this.autoUpgrade = value;
    }

    public String getInstallationMode() {
        return this.installationMode;
    }

    public void setInstallationMode(String value) {
        this.installationMode = value;
    }

    public String getCloneMultiRC() {
        return this.cloneMultiRC;
    }

    public void setCloneMultiRC(String value) {
        this.cloneMultiRC = value;
    }
}

