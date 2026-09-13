package com.tpvision.smartinstall.xml.psg.configuration;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
   name = "",
   propOrder = {
         "buildRootFolder",
         "workingRootFolder",
         "transmissionMode",
         "guardInterval",
         "constellation",
         "codeRate",
         "modulationType",
         "deviceCapabilityFlags",
         "deviceStreamType",
         "txMode",
         "stuffMode",
         "bandwidth",
         "outputChannel",
         "modulationFrequency",
         "outputLevel",
         "originalNetworkId",
         "progNum",
         "pid",
         "oui",
         "txnId",
         "dwnldId",
         "networkId",
         "transportStreamId",
         "pcrPid",
         "roomNumber",
         "ssbIdentifier",
         "channelTableIdentifier"
   }
)
@XmlRootElement(name = "Config")
public class Config {
   @XmlElement(name = "BuildRootFolder", required = true)
   protected String buildRootFolder;
   @XmlElement(name = "WorkingRootFolder", required = true)
   protected String workingRootFolder;
   @XmlElement(name = "TransmissionMode", required = true)
   protected String transmissionMode;
   @XmlElement(name = "GuardInterval", required = true)
   protected String guardInterval;
   @XmlElement(name = "Constellation", required = true)
   protected String constellation;
   @XmlElement(name = "CodeRate", required = true)
   protected String codeRate;
   @XmlElement(name = "ModulationType", required = true)
   protected String modulationType;
   @XmlElement(name = "DeviceCapabilityFlags", required = true)
   protected String deviceCapabilityFlags;
   @XmlElement(name = "DeviceStreamType", required = true)
   protected String deviceStreamType;
   @XmlElement(name = "TxMode", required = true)
   protected String txMode;
   @XmlElement(name = "StuffMode", required = true)
   protected String stuffMode;
   @XmlElement(name = "Bandwidth", required = true)
   protected String bandwidth;
   @XmlElement(name = "OutputChannel", required = true)
   protected String outputChannel;
   @XmlElement(name = "ModulationFrequency", required = true)
   protected String modulationFrequency;
   @XmlElement(name = "OutputLevel", required = true)
   protected String outputLevel;
   @XmlElement(name = "OriginalNetworkId", required = true)
   protected String originalNetworkId;
   @XmlElement(name = "ProgNum", required = true)
   protected String progNum;
   @XmlElement(name = "Pid", required = true)
   protected String pid;
   @XmlElement(name = "Oui", required = true)
   protected String oui;
   @XmlElement(name = "TxnId", required = true)
   protected String txnId;
   @XmlElement(name = "DwnldId", required = true)
   protected String dwnldId;
   @XmlElement(name = "NetworkId", required = true)
   protected String networkId;
   @XmlElement(name = "TransportStreamId", required = true)
   protected String transportStreamId;
   @XmlElement(name = "PcrPid", required = true)
   protected String pcrPid;
   @XmlElement(name = "RoomNumber", required = true)
   protected String roomNumber;
   @XmlElement(name = "SsbIdentifier", required = true)
   protected String ssbIdentifier;
   @XmlElement(name = "ChannelTableIdentifier", required = true)
   protected String channelTableIdentifier;

   public String getBuildRootFolder() {
      return this.buildRootFolder;
   }

   public void setBuildRootFolder(String value) {
      this.buildRootFolder = value;
   }

   public String getWorkingRootFolder() {
      return this.workingRootFolder;
   }

   public void setWorkingRootFolder(String value) {
      this.workingRootFolder = value;
   }

   public String getTransmissionMode() {
      return this.transmissionMode;
   }

   public void setTransmissionMode(String value) {
      this.transmissionMode = value;
   }

   public String getGuardInterval() {
      return this.guardInterval;
   }

   public void setGuardInterval(String value) {
      this.guardInterval = value;
   }

   public String getConstellation() {
      return this.constellation;
   }

   public void setConstellation(String value) {
      this.constellation = value;
   }

   public String getCodeRate() {
      return this.codeRate;
   }

   public void setCodeRate(String value) {
      this.codeRate = value;
   }

   public String getModulationType() {
      return this.modulationType;
   }

   public void setModulationType(String value) {
      this.modulationType = value;
   }

   public String getDeviceCapabilityFlags() {
      return this.deviceCapabilityFlags;
   }

   public void setDeviceCapabilityFlags(String value) {
      this.deviceCapabilityFlags = value;
   }

   public String getDeviceStreamType() {
      return this.deviceStreamType;
   }

   public void setDeviceStreamType(String value) {
      this.deviceStreamType = value;
   }

   public String getTxMode() {
      return this.txMode;
   }

   public void setTxMode(String value) {
      this.txMode = value;
   }

   public String getStuffMode() {
      return this.stuffMode;
   }

   public void setStuffMode(String value) {
      this.stuffMode = value;
   }

   public String getBandwidth() {
      return this.bandwidth;
   }

   public void setBandwidth(String value) {
      this.bandwidth = value;
   }

   public String getOutputChannel() {
      return this.outputChannel;
   }

   public void setOutputChannel(String value) {
      this.outputChannel = value;
   }

   public String getModulationFrequency() {
      return this.modulationFrequency;
   }

   public void setModulationFrequency(String value) {
      this.modulationFrequency = value;
   }

   public String getOutputLevel() {
      return this.outputLevel;
   }

   public void setOutputLevel(String value) {
      this.outputLevel = value;
   }

   public String getOriginalNetworkId() {
      return this.originalNetworkId;
   }

   public void setOriginalNetworkId(String value) {
      this.originalNetworkId = value;
   }

   public String getProgNum() {
      return this.progNum;
   }

   public void setProgNum(String value) {
      this.progNum = value;
   }

   public String getPid() {
      return this.pid;
   }

   public void setPid(String value) {
      this.pid = value;
   }

   public String getOui() {
      return this.oui;
   }

   public void setOui(String value) {
      this.oui = value;
   }

   public String getTxnId() {
      return this.txnId;
   }

   public void setTxnId(String value) {
      this.txnId = value;
   }

   public String getDwnldId() {
      return this.dwnldId;
   }

   public void setDwnldId(String value) {
      this.dwnldId = value;
   }

   public String getNetworkId() {
      return this.networkId;
   }

   public void setNetworkId(String value) {
      this.networkId = value;
   }

   public String getTransportStreamId() {
      return this.transportStreamId;
   }

   public void setTransportStreamId(String value) {
      this.transportStreamId = value;
   }

   public String getPcrPid() {
      return this.pcrPid;
   }

   public void setPcrPid(String value) {
      this.pcrPid = value;
   }

   public String getRoomNumber() {
      return this.roomNumber;
   }

   public void setRoomNumber(String value) {
      this.roomNumber = value;
   }

   public String getSsbIdentifier() {
      return this.ssbIdentifier;
   }

   public void setSsbIdentifier(String value) {
      this.ssbIdentifier = value;
   }

   public String getChannelTableIdentifier() {
      return this.channelTableIdentifier;
   }

   public void setChannelTableIdentifier(String value) {
      this.channelTableIdentifier = value;
   }
}
