package com.tpvision.smartinstall.dao.core;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "onlinedevices")
public class OnlineDevices {
   @Id
   @Column(name = "Id")
   private String id;
   @Column(name = "TVName")
   private String tvname;
   @Column(name = "TVModelNumber")
   private String tvmodelnumber;
   @Column(name = "TVSerialNumber")
   private String tvserialnumber;
   @Column(name = "TVRoomID")
   private String tvroomid;
   @Column(name = "TVMACAddress")
   private String tvmacaddress;
   @Column(name = "TVIPAddress")
   private String tvipaddress;
   @Column(name = "VSecureTVID")
   private String vsecuretvid;
   @Column(name = "Type")
   private String type;
   @Column(name = "PowerStatus")
   private String powerstatus;
   @Column(name = "TVUniqueID")
   private String tvuniqueid;
   @Column(name = "FirmwareId")
   private int firmwareid;
   @Column(name = "CloneId")
   private int cloneid;
   @Column(name = "lastCloneRename")
   private String lastCloneRename = "{\"old\":\"Unknown\",\"new\":\"Unknown\"}";
   @Column(name = "Status")
   private String status;
   @Column(name = "Progress")
   private String progress;
   @Column(name = "TVStatus")
   private String tvstatus;
   @Column(name = "si_clone_Identifiers")
   private String siCloneIdentifiers;
   @Column(name = "si_firmware_Identifier")
   private String siFirmwareIdentifier;
   @Column(name = "tv_clone_Identifiers")
   private String tvCloneIdentifiers;
   @Column(name = "tv_firmware_Identifier")
   private String tvFirmwareIdentifier;
   @Column(name = "success_siclone_Identifier")
   private String successSicloneIdentifier;
   @Column(name = "success_tvclone_Identifier")
   private String successTvcloneIdentifier;
   @Column(name = "clone_color")
   private String cloneColor = "black";
   @Column(name = "fw_color")
   private String fwColor = "black";
   @Column(name = "CreatedDate")
   private String createddate;
   @Column(name = "ModifiedDate")
   private String modifieddate;
   @Column(name = "Lastonline")
   private String lastonline;
   @Column(name = "clone_mode")
   private String cloneMode = "Upgrade";
   @Column(name = "upload_progress")
   private String uploadProgress = "ST";
   @Column(name = "upload_session_id")
   private String uploadSessionId;
   @Column(name = "upload_session_start")
   private String uploadSessionStart;
   @Column(name = "upload_session_end")
   private String uploadSessionEnd;
   @Column(name = "upload_session_status")
   private String uploadSessionStatus;
   @Column(name = "networkInterfaceDisplayName")
   private String networkInterfaceDisplayName;
   @Column(name = "networkInterfaceIp")
   private String networkInterfaceIp;
   @Column(name = "SecureCmdSupport")
   private String secureCmdSupport;
   @Column(name = "wls_port")
   private int wlsPort;
   @Column(name = "wls_secure_port")
   private int wlsSecurePort;
   @Column(name = "last_unique_id")
   private String lastUniqueId;

   public String getId() {
      return this.id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getTvname() {
      return this.tvname;
   }

   public void setTvname(String tvname) {
      this.tvname = tvname;
   }

   public String getTvmodelnumber() {
      return this.tvmodelnumber;
   }

   public void setTvmodelnumber(String tvmodelnumber) {
      this.tvmodelnumber = tvmodelnumber;
   }

   public String getTvserialnumber() {
      return this.tvserialnumber;
   }

   public void setTvserialnumber(String tvserialnumber) {
      this.tvserialnumber = tvserialnumber;
   }

   public String getTvroomid() {
      return this.tvroomid;
   }

   public void setTvroomid(String tvroomid) {
      this.tvroomid = tvroomid;
   }

   public String getTvmacaddress() {
      return this.tvmacaddress;
   }

   public void setTvmacaddress(String tvmacaddress) {
      this.tvmacaddress = tvmacaddress;
   }

   public String getTvipaddress() {
      return this.tvipaddress;
   }

   public void setTvipaddress(String tvipaddress) {
      this.tvipaddress = tvipaddress;
   }

   public String getVsecuretvid() {
      return this.vsecuretvid;
   }

   public void setVsecuretvid(String vsecuretvid) {
      this.vsecuretvid = vsecuretvid;
   }

   public String getType() {
      return this.type;
   }

   public void setType(String type) {
      this.type = type;
   }

   public String getPowerstatus() {
      return this.powerstatus;
   }

   public void setPowerstatus(String powerstatus) {
      this.powerstatus = powerstatus;
   }

   public String getTvuniqueid() {
      return this.tvuniqueid;
   }

   public void setTvuniqueid(String tvuniqueid) {
      this.tvuniqueid = tvuniqueid;
   }

   public int getFirmwareid() {
      return this.firmwareid;
   }

   public void setFirmwareid(int firmwareid) {
      this.firmwareid = firmwareid;
   }

   public int getCloneid() {
      return this.cloneid;
   }

   public void setCloneid(int cloneid) {
      this.cloneid = cloneid;
   }

   public String getStatus() {
      return this.status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public String getProgress() {
      return this.progress;
   }

   public void setProgress(String progress) {
      this.progress = progress;
   }

   public String getTvstatus() {
      return this.tvstatus;
   }

   public void setTvstatus(String tvstatus) {
      this.tvstatus = tvstatus;
   }

   public String getSiCloneIdentifiers() {
      return this.siCloneIdentifiers;
   }

   public void setSiCloneIdentifiers(String siCloneIdentifiers) {
      this.siCloneIdentifiers = siCloneIdentifiers;
   }

   public String getSiFirmwareIdentifier() {
      return this.siFirmwareIdentifier;
   }

   public void setSiFirmwareIdentifier(String siFirmwareIdentifier) {
      this.siFirmwareIdentifier = siFirmwareIdentifier;
   }

   public String getTvCloneIdentifiers() {
      return this.tvCloneIdentifiers;
   }

   public void setTvCloneIdentifiers(String tvCloneIdentifiers) {
      this.tvCloneIdentifiers = tvCloneIdentifiers;
   }

   public String getTvFirmwareIdentifier() {
      return this.tvFirmwareIdentifier;
   }

   public void setTvFirmwareIdentifier(String tvFirmwareIdentifier) {
      this.tvFirmwareIdentifier = tvFirmwareIdentifier;
   }

   public String getSuccessSicloneIdentifier() {
      return this.successSicloneIdentifier;
   }

   public void setSuccessSicloneIdentifier(String successSicloneIdentifier) {
      this.successSicloneIdentifier = successSicloneIdentifier;
   }

   public String getSuccessTvcloneIdentifier() {
      return this.successTvcloneIdentifier;
   }

   public void setSuccessTvcloneIdentifier(String successTvcloneIdentifier) {
      this.successTvcloneIdentifier = successTvcloneIdentifier;
   }

   public String getCreateddate() {
      return this.createddate;
   }

   public void setCreateddate(String createddate) {
      this.createddate = createddate;
   }

   public String getModifieddate() {
      return this.modifieddate;
   }

   public void setModifieddate(String modifieddate) {
      this.modifieddate = modifieddate;
   }

   public String getLastonline() {
      return this.lastonline;
   }

   public void setLastonline(String lastonline) {
      this.lastonline = lastonline;
   }

   public String getCloneMode() {
      return this.cloneMode;
   }

   public void setCloneMode(String cloneMode) {
      this.cloneMode = cloneMode;
   }

   public String getUploadProgress() {
      return this.uploadProgress;
   }

   public void setUploadProgress(String uploadProgress) {
      this.uploadProgress = uploadProgress;
   }

   public String getUploadSessionId() {
      return this.uploadSessionId;
   }

   public void setUploadSessionId(String uploadSessionId) {
      this.uploadSessionId = uploadSessionId;
   }

   public String getUploadSessionStatus() {
      return this.uploadSessionStatus;
   }

   public void setUploadSessionStatus(String uploadSessionStatus) {
      this.uploadSessionStatus = uploadSessionStatus;
   }

   public String getUploadSessionStart() {
      return this.uploadSessionStart;
   }

   public void setUploadSessionStart(String uploadSessionStart) {
      this.uploadSessionStart = uploadSessionStart;
   }

   public String getUploadSessionEnd() {
      return this.uploadSessionEnd;
   }

   public void setUploadSessionEnd(String uploadSessionEnd) {
      this.uploadSessionEnd = uploadSessionEnd;
   }

   public String getCloneColor() {
      return this.cloneColor;
   }

   public void setCloneColor(String cloneColor) {
      this.cloneColor = cloneColor;
   }

   public String getFwColor() {
      return this.fwColor;
   }

   public void setFwColor(String fwColor) {
      this.fwColor = fwColor;
   }

   public String getLastCloneRename() {
      return this.lastCloneRename;
   }

   public void setLastCloneRename(String lastCloneRename) {
      this.lastCloneRename = lastCloneRename;
   }

   public String getNetworkInterfaceDisplayName() {
      return this.networkInterfaceDisplayName;
   }

   public void setNetworkInterfaceDisplayName(String networkInterfaceDisplayName) {
      this.networkInterfaceDisplayName = networkInterfaceDisplayName;
   }

   public String getNetworkInterfaceIp() {
      return this.networkInterfaceIp;
   }

   public void setNetworkInterfaceIp(String networkInterfaceIp) {
      this.networkInterfaceIp = networkInterfaceIp;
   }

   public String getSecureCmdSupport() {
      return this.secureCmdSupport;
   }

   public void setSecureCmdSupport(String secureCmdSupport) {
      this.secureCmdSupport = secureCmdSupport;
   }

   public int getWlsPort() {
      return this.wlsPort;
   }

   public void setWlsPort(int wlsPort) {
      this.wlsPort = wlsPort;
   }

   public int getWlsSecurePort() {
      return this.wlsSecurePort;
   }

   public void setWlsSecurePort(int wlsSecurePort) {
      this.wlsSecurePort = wlsSecurePort;
   }

   public String getLastUniqueId() {
      return this.lastUniqueId;
   }

   public void setLastUniqueId(String lastUniqueId) {
      this.lastUniqueId = lastUniqueId;
   }
}
