package com.tpvision.smartinstall.xml.es2k14;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "",
   propOrder = {
         "switchOnSrc",
         "switchOnChn",
         "switchOnVol",
         "maximumVol",
         "switchOnFeature",
         "switchOnPicFmt",
         "powerOn",
         "lowPowerStandby",
         "smartPower",
         "rebootEveryDay",
         "displayWelcomeMsg",
         "welcomeMsgLine1",
         "welcomeMsgLine2",
         "welcomeMsgTimeOut",
         "displayLogo",
         "smartInfo",
         "smartInfoIconLabel",
         "kbLock",
         "rcLock",
         "osdDisplay",
         "highSecurity",
         "autoScart",
         "usbBreakIn",
         "enableUSB",
         "sxpBaudRate",
         "enableTeletext",
         "enableMHEG",
         "enableEPG",
         "enableSubtitles",
         "subtitleOnStartup",
         "blueMute",
         "enableCISlot",
         "scrambledProgramOSD",
         "easylinkBreakIn",
         "easylinkControl",
         "digitTimeout",
         "selectableAV",
         "watchTV",
         "externalClk",
         "clkBrighDimlight",
         "clkBrighIntenselight",
         "clkLightSensor",
         "timeDownload",
         "timeSetting",
         "clkDownloadProgram",
         "clkDownloadCountry",
         "clkTimeZone",
         "daylightSaving",
         "clkTimeOffset",
         "referenceDate",
         "referenceTime",
         "mainSpkrEnable",
         "indMainSpkrMute",
         "defMainSpkrVol",
         "autoChnUpdate",
         "autoSwUpdate",
         "skipScrambled",
         "multiRC",
         "myChoice",
         "askForPIN",
         "smartPay",
         "av",
         "vsecOverRFEnable",
         "eraseKeyOption",
         "vsecFrequency",
         "vsecMedium",
         "vsecSymbolRate",
         "rfclFrequency",
         "rfclMedium",
         "rfclSymbolRate",
         "upgradeMode",
         "autoUpgrade",
         "installationMode",
         "cloneMultiRC"
   }
)
@XmlRootElement(name = "HotelModeSettings")
public class HotelModelSettingsForTpn142 {
   @XmlElement(name = "SwitchOnSrc", required = true)
   protected String switchOnSrc;
   @XmlElement(name = "SwitchOnChn", required = true)
   protected String switchOnChn;
   @XmlElement(name = "SwitchOnVol", required = true)
   protected String switchOnVol;
   @XmlElement(name = "MaximumVol", required = true)
   protected String maximumVol;
   @XmlElement(name = "SwitchOnFeature", required = true)
   protected String switchOnFeature;
   @XmlElement(name = "SwitchOnPicFmt", required = true)
   protected String switchOnPicFmt;
   @XmlElement(name = "PowerOn", required = true)
   protected String powerOn;
   @XmlElement(name = "LowPowerStandby", required = true)
   protected String lowPowerStandby;
   @XmlElement(name = "SmartPower", required = true)
   protected String smartPower;
   @XmlElement(name = "RebootEveryDay", required = true)
   protected String rebootEveryDay;
   @XmlElement(name = "DisplayWelcomeMsg", required = true)
   protected String displayWelcomeMsg;
   @XmlElement(name = "WelcomeMsgLine1", required = true)
   protected String welcomeMsgLine1;
   @XmlElement(name = "WelcomeMsgLine2", required = true)
   protected String welcomeMsgLine2;
   @XmlElement(name = "WelcomeMsgTimeOut", required = true)
   protected String welcomeMsgTimeOut;
   @XmlElement(name = "DisplayLogo", required = true)
   protected String displayLogo;
   @XmlElement(name = "SmartInfo", required = true)
   protected String smartInfo;
   @XmlElement(name = "SmartInfoIconLabel", required = true)
   protected String smartInfoIconLabel;
   @XmlElement(name = "KBLock", required = true)
   protected String kbLock;
   @XmlElement(name = "RCLock", required = true)
   protected String rcLock;
   @XmlElement(name = "OSDDisplay", required = true)
   protected String osdDisplay;
   @XmlElement(name = "HighSecurity", required = true)
   protected String highSecurity;
   @XmlElement(name = "AutoScart", required = true)
   protected String autoScart;
   @XmlElement(name = "USBBreakIn", required = true)
   protected String usbBreakIn;
   @XmlElement(name = "EnableUSB", required = true)
   protected String enableUSB;
   @XmlElement(name = "SXPBaudRate", required = true)
   protected String sxpBaudRate;
   @XmlElement(name = "EnableTeletext", required = true)
   protected String enableTeletext;
   @XmlElement(name = "EnableMHEG", required = true)
   protected String enableMHEG;
   @XmlElement(name = "EnableEPG", required = true)
   protected String enableEPG;
   @XmlElement(name = "EnableSubtitles", required = true)
   protected String enableSubtitles;
   @XmlElement(name = "SubtitleOnStartup", required = true)
   protected String subtitleOnStartup;
   @XmlElement(name = "BlueMute", required = true)
   protected String blueMute;
   @XmlElement(name = "EnableCISlot", required = true)
   protected String enableCISlot;
   @XmlElement(name = "ScrambledProgramOSD", required = true)
   protected String scrambledProgramOSD;
   @XmlElement(name = "EasylinkBreakIn", required = true)
   protected String easylinkBreakIn;
   @XmlElement(name = "EasylinkControl", required = true)
   protected String easylinkControl;
   @XmlElement(name = "DigitTimeout", required = true)
   protected String digitTimeout;
   @XmlElement(name = "SelectableAV", required = true)
   protected String selectableAV;
   @XmlElement(name = "WatchTV", required = true)
   protected String watchTV;
   @XmlElement(name = "ExternalClk", required = true)
   protected String externalClk;
   @XmlElement(name = "ClkBrighDimlight", required = true)
   protected String clkBrighDimlight;
   @XmlElement(name = "ClkBrighIntenselight", required = true)
   protected String clkBrighIntenselight;
   @XmlElement(name = "ClkLightSensor", required = true)
   protected String clkLightSensor;
   @XmlElement(name = "TimeDownload", required = true)
   protected String timeDownload;
   @XmlElement(name = "TimeSetting", required = true)
   protected String timeSetting;
   @XmlElement(name = "ClkDownloadProgram", required = true)
   protected String clkDownloadProgram;
   @XmlElement(name = "ClkDownloadCountry", required = true)
   protected String clkDownloadCountry;
   @XmlElement(name = "ClkTimeZone", required = true)
   protected String clkTimeZone;
   @XmlElement(name = "DaylightSaving", required = true)
   protected String daylightSaving;
   @XmlElement(name = "ClkTimeOffset", required = true)
   protected String clkTimeOffset;
   @XmlElement(name = "ReferenceDate", required = true)
   protected String referenceDate;
   @XmlElement(name = "ReferenceTime", required = true)
   protected String referenceTime;
   @XmlElement(name = "MainSpkrEnable", required = true)
   protected String mainSpkrEnable;
   @XmlElement(name = "IndMainSpkrMute", required = true)
   protected String indMainSpkrMute;
   @XmlElement(name = "DefMainSpkrVol", required = true)
   protected String defMainSpkrVol;
   @XmlElement(name = "AutoChnUpdate", required = true)
   protected String autoChnUpdate;
   @XmlElement(name = "AutoSwUpdate", required = true)
   protected String autoSwUpdate;
   @XmlElement(name = "SkipScrambled", required = true)
   protected String skipScrambled;
   @XmlElement(name = "MultiRC", required = true)
   protected String multiRC;
   @XmlElement(name = "MyChoice", required = true)
   protected String myChoice;
   @XmlElement(name = "AskForPIN", required = true)
   protected String askForPIN;
   @XmlElement(name = "SmartPay", required = true)
   protected String smartPay;
   @XmlElement(name = "AV", required = true)
   protected String av;
   @XmlElement(name = "VsecOverRFEnable", required = true)
   protected String vsecOverRFEnable;
   @XmlElement(name = "EraseKeyOption", required = true)
   protected String eraseKeyOption;
   @XmlElement(name = "VsecFrequency", required = true)
   protected String vsecFrequency;
   @XmlElement(name = "VsecMedium", required = true)
   protected String vsecMedium;
   @XmlElement(name = "VsecSymbolRate", required = true)
   protected String vsecSymbolRate;
   @XmlElement(name = "RFCLFrequency", required = true)
   protected String rfclFrequency;
   @XmlElement(name = "RFCLMedium", required = true)
   protected String rfclMedium;
   @XmlElement(name = "RFCLSymbolRate", required = true)
   protected String rfclSymbolRate;
   @XmlElement(name = "UpgradeMode", required = true)
   protected String upgradeMode;
   @XmlElement(name = "AutoUpgrade", required = true)
   protected String autoUpgrade;
   @XmlElement(name = "InstallationMode", required = true)
   protected String installationMode;
   @XmlElement(name = "CloneMultiRC", required = true)
   protected String cloneMultiRC;

   public String getSwitchOnSrc() {
      return this.switchOnSrc;
   }

   public void setSwitchOnSrc(String switchOnSrc) {
      this.switchOnSrc = switchOnSrc;
   }

   public String getSwitchOnChn() {
      return this.switchOnChn;
   }

   public void setSwitchOnChn(String switchOnChn) {
      this.switchOnChn = switchOnChn;
   }

   public String getSwitchOnVol() {
      return this.switchOnVol;
   }

   public void setSwitchOnVol(String switchOnVol) {
      this.switchOnVol = switchOnVol;
   }

   public String getMaximumVol() {
      return this.maximumVol;
   }

   public void setMaximumVol(String maximumVol) {
      this.maximumVol = maximumVol;
   }

   public String getSwitchOnFeature() {
      return this.switchOnFeature;
   }

   public void setSwitchOnFeature(String switchOnFeature) {
      this.switchOnFeature = switchOnFeature;
   }

   public String getSwitchOnPicFmt() {
      return this.switchOnPicFmt;
   }

   public void setSwitchOnPicFmt(String switchOnPicFmt) {
      this.switchOnPicFmt = switchOnPicFmt;
   }

   public String getPowerOn() {
      return this.powerOn;
   }

   public void setPowerOn(String powerOn) {
      this.powerOn = powerOn;
   }

   public String getLowPowerStandby() {
      return this.lowPowerStandby;
   }

   public void setLowPowerStandby(String lowPowerStandby) {
      this.lowPowerStandby = lowPowerStandby;
   }

   public String getSmartPower() {
      return this.smartPower;
   }

   public void setSmartPower(String smartPower) {
      this.smartPower = smartPower;
   }

   public String getRebootEveryDay() {
      return this.rebootEveryDay;
   }

   public void setRebootEveryDay(String rebootEveryDay) {
      this.rebootEveryDay = rebootEveryDay;
   }

   public String getDisplayWelcomeMsg() {
      return this.displayWelcomeMsg;
   }

   public void setDisplayWelcomeMsg(String displayWelcomeMsg) {
      this.displayWelcomeMsg = displayWelcomeMsg;
   }

   public String getWelcomeMsgLine1() {
      return this.welcomeMsgLine1;
   }

   public void setWelcomeMsgLine1(String welcomeMsgLine1) {
      this.welcomeMsgLine1 = welcomeMsgLine1;
   }

   public String getWelcomeMsgLine2() {
      return this.welcomeMsgLine2;
   }

   public void setWelcomeMsgLine2(String welcomeMsgLine2) {
      this.welcomeMsgLine2 = welcomeMsgLine2;
   }

   public String getWelcomeMsgTimeOut() {
      return this.welcomeMsgTimeOut;
   }

   public void setWelcomeMsgTimeOut(String welcomeMsgTimeOut) {
      this.welcomeMsgTimeOut = welcomeMsgTimeOut;
   }

   public String getDisplayLogo() {
      return this.displayLogo;
   }

   public void setDisplayLogo(String displayLogo) {
      this.displayLogo = displayLogo;
   }

   public String getSmartInfo() {
      return this.smartInfo;
   }

   public void setSmartInfo(String smartInfo) {
      this.smartInfo = smartInfo;
   }

   public String getSmartInfoIconLabel() {
      return this.smartInfoIconLabel;
   }

   public void setSmartInfoIconLabel(String smartInfoIconLabel) {
      this.smartInfoIconLabel = smartInfoIconLabel;
   }

   public String getKbLock() {
      return this.kbLock;
   }

   public void setKbLock(String kbLock) {
      this.kbLock = kbLock;
   }

   public String getRcLock() {
      return this.rcLock;
   }

   public void setRcLock(String rcLock) {
      this.rcLock = rcLock;
   }

   public String getOsdDisplay() {
      return this.osdDisplay;
   }

   public void setOsdDisplay(String osdDisplay) {
      this.osdDisplay = osdDisplay;
   }

   public String getHighSecurity() {
      return this.highSecurity;
   }

   public void setHighSecurity(String highSecurity) {
      this.highSecurity = highSecurity;
   }

   public String getAutoScart() {
      return this.autoScart;
   }

   public void setAutoScart(String autoScart) {
      this.autoScart = autoScart;
   }

   public String getUsbBreakIn() {
      return this.usbBreakIn;
   }

   public void setUsbBreakIn(String usbBreakIn) {
      this.usbBreakIn = usbBreakIn;
   }

   public String getEnableUSB() {
      return this.enableUSB;
   }

   public void setEnableUSB(String enableUSB) {
      this.enableUSB = enableUSB;
   }

   public String getSxpBaudRate() {
      return this.sxpBaudRate;
   }

   public void setSxpBaudRate(String sxpBaudRate) {
      this.sxpBaudRate = sxpBaudRate;
   }

   public String getEnableTeletext() {
      return this.enableTeletext;
   }

   public void setEnableTeletext(String enableTeletext) {
      this.enableTeletext = enableTeletext;
   }

   public String getEnableMHEG() {
      return this.enableMHEG;
   }

   public void setEnableMHEG(String enableMHEG) {
      this.enableMHEG = enableMHEG;
   }

   public String getEnableEPG() {
      return this.enableEPG;
   }

   public void setEnableEPG(String enableEPG) {
      this.enableEPG = enableEPG;
   }

   public String getEnableSubtitles() {
      return this.enableSubtitles;
   }

   public void setEnableSubtitles(String enableSubtitles) {
      this.enableSubtitles = enableSubtitles;
   }

   public String getSubtitleOnStartup() {
      return this.subtitleOnStartup;
   }

   public void setSubtitleOnStartup(String subtitleOnStartup) {
      this.subtitleOnStartup = subtitleOnStartup;
   }

   public String getBlueMute() {
      return this.blueMute;
   }

   public void setBlueMute(String blueMute) {
      this.blueMute = blueMute;
   }

   public String getEnableCISlot() {
      return this.enableCISlot;
   }

   public void setEnableCISlot(String enableCISlot) {
      this.enableCISlot = enableCISlot;
   }

   public String getScrambledProgramOSD() {
      return this.scrambledProgramOSD;
   }

   public void setScrambledProgramOSD(String scrambledProgramOSD) {
      this.scrambledProgramOSD = scrambledProgramOSD;
   }

   public String getEasylinkBreakIn() {
      return this.easylinkBreakIn;
   }

   public void setEasylinkBreakIn(String easylinkBreakIn) {
      this.easylinkBreakIn = easylinkBreakIn;
   }

   public String getEasylinkControl() {
      return this.easylinkControl;
   }

   public void setEasylinkControl(String easylinkControl) {
      this.easylinkControl = easylinkControl;
   }

   public String getDigitTimeout() {
      return this.digitTimeout;
   }

   public void setDigitTimeout(String digitTimeout) {
      this.digitTimeout = digitTimeout;
   }

   public String getSelectableAV() {
      return this.selectableAV;
   }

   public void setSelectableAV(String selectableAV) {
      this.selectableAV = selectableAV;
   }

   public String getWatchTV() {
      return this.watchTV;
   }

   public void setWatchTV(String watchTV) {
      this.watchTV = watchTV;
   }

   public String getExternalClk() {
      return this.externalClk;
   }

   public void setExternalClk(String externalClk) {
      this.externalClk = externalClk;
   }

   public String getClkBrighDimlight() {
      return this.clkBrighDimlight;
   }

   public void setClkBrighDimlight(String clkBrighDimlight) {
      this.clkBrighDimlight = clkBrighDimlight;
   }

   public String getClkBrighIntenselight() {
      return this.clkBrighIntenselight;
   }

   public void setClkBrighIntenselight(String clkBrighIntenselight) {
      this.clkBrighIntenselight = clkBrighIntenselight;
   }

   public String getClkLightSensor() {
      return this.clkLightSensor;
   }

   public void setClkLightSensor(String clkLightSensor) {
      this.clkLightSensor = clkLightSensor;
   }

   public String getTimeDownload() {
      return this.timeDownload;
   }

   public void setTimeDownload(String timeDownload) {
      this.timeDownload = timeDownload;
   }

   public String getTimeSetting() {
      return this.timeSetting;
   }

   public void setTimeSetting(String timeSetting) {
      this.timeSetting = timeSetting;
   }

   public String getClkDownloadProgram() {
      return this.clkDownloadProgram;
   }

   public void setClkDownloadProgram(String clkDownloadProgram) {
      this.clkDownloadProgram = clkDownloadProgram;
   }

   public String getClkDownloadCountry() {
      return this.clkDownloadCountry;
   }

   public void setClkDownloadCountry(String clkDownloadCountry) {
      this.clkDownloadCountry = clkDownloadCountry;
   }

   public String getClkTimeZone() {
      return this.clkTimeZone;
   }

   public void setClkTimeZone(String clkTimeZone) {
      this.clkTimeZone = clkTimeZone;
   }

   public String getDaylightSaving() {
      return this.daylightSaving;
   }

   public void setDaylightSaving(String daylightSaving) {
      this.daylightSaving = daylightSaving;
   }

   public String getClkTimeOffset() {
      return this.clkTimeOffset;
   }

   public void setClkTimeOffset(String clkTimeOffset) {
      this.clkTimeOffset = clkTimeOffset;
   }

   public String getReferenceDate() {
      return this.referenceDate;
   }

   public void setReferenceDate(String referenceDate) {
      this.referenceDate = referenceDate;
   }

   public String getReferenceTime() {
      return this.referenceTime;
   }

   public void setReferenceTime(String referenceTime) {
      this.referenceTime = referenceTime;
   }

   public String getMainSpkrEnable() {
      return this.mainSpkrEnable;
   }

   public void setMainSpkrEnable(String mainSpkrEnable) {
      this.mainSpkrEnable = mainSpkrEnable;
   }

   public String getIndMainSpkrMute() {
      return this.indMainSpkrMute;
   }

   public void setIndMainSpkrMute(String indMainSpkrMute) {
      this.indMainSpkrMute = indMainSpkrMute;
   }

   public String getDefMainSpkrVol() {
      return this.defMainSpkrVol;
   }

   public void setDefMainSpkrVol(String defMainSpkrVol) {
      this.defMainSpkrVol = defMainSpkrVol;
   }

   public String getAutoChnUpdate() {
      return this.autoChnUpdate;
   }

   public void setAutoChnUpdate(String autoChnUpdate) {
      this.autoChnUpdate = autoChnUpdate;
   }

   public String getAutoSwUpdate() {
      return this.autoSwUpdate;
   }

   public void setAutoSwUpdate(String autoSwUpdate) {
      this.autoSwUpdate = autoSwUpdate;
   }

   public String getSkipScrambled() {
      return this.skipScrambled;
   }

   public void setSkipScrambled(String skipScrambled) {
      this.skipScrambled = skipScrambled;
   }

   public String getMultiRC() {
      return this.multiRC;
   }

   public void setMultiRC(String multiRC) {
      this.multiRC = multiRC;
   }

   public String getMyChoice() {
      return this.myChoice;
   }

   public void setMyChoice(String myChoice) {
      this.myChoice = myChoice;
   }

   public String getAskForPIN() {
      return this.askForPIN;
   }

   public void setAskForPIN(String askForPIN) {
      this.askForPIN = askForPIN;
   }

   public String getSmartPay() {
      return this.smartPay;
   }

   public void setSmartPay(String smartPay) {
      this.smartPay = smartPay;
   }

   public String getAV() {
      return this.av;
   }

   public void setAV(String av) {
      this.av = av;
   }

   public String getVsecOverRFEnable() {
      return this.vsecOverRFEnable;
   }

   public void setVsecOverRFEnable(String vsecOverRFEnable) {
      this.vsecOverRFEnable = vsecOverRFEnable;
   }

   public String getEraseKeyOption() {
      return this.eraseKeyOption;
   }

   public void setEraseKeyOption(String eraseKeyOption) {
      this.eraseKeyOption = eraseKeyOption;
   }

   public String getVsecFrequency() {
      return this.vsecFrequency;
   }

   public void setVsecFrequency(String vsecFrequency) {
      this.vsecFrequency = vsecFrequency;
   }

   public String getVsecMedium() {
      return this.vsecMedium;
   }

   public void setVsecMedium(String vsecMedium) {
      this.vsecMedium = vsecMedium;
   }

   public String getVsecSymbolRate() {
      return this.vsecSymbolRate;
   }

   public void setVsecSymbolRate(String vsecSymbolRate) {
      this.vsecSymbolRate = vsecSymbolRate;
   }

   public String getRfclFrequency() {
      return this.rfclFrequency;
   }

   public void setRfclFrequency(String rfclFrequency) {
      this.rfclFrequency = rfclFrequency;
   }

   public String getRfclMedium() {
      return this.rfclMedium;
   }

   public void setRfclMedium(String rfclMedium) {
      this.rfclMedium = rfclMedium;
   }

   public String getRfclSymbolRate() {
      return this.rfclSymbolRate;
   }

   public void setRfclSymbolRate(String rfclSymbolRate) {
      this.rfclSymbolRate = rfclSymbolRate;
   }

   public String getUpgradeMode() {
      return this.upgradeMode;
   }

   public void setUpgradeMode(String upgradeMode) {
      this.upgradeMode = upgradeMode;
   }

   public String getAutoUpgrade() {
      return this.autoUpgrade;
   }

   public void setAutoUpgrade(String autoUpgrade) {
      this.autoUpgrade = autoUpgrade;
   }

   public String getInstallationMode() {
      return this.installationMode;
   }

   public void setInstallationMode(String installationMode) {
      this.installationMode = installationMode;
   }

   public String getCloneMultiRC() {
      return this.cloneMultiRC;
   }

   public void setCloneMultiRC(String cloneMultiRC) {
      this.cloneMultiRC = cloneMultiRC;
   }
}
