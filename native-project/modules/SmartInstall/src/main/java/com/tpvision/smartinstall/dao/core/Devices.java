package com.tpvision.smartinstall.dao.core;

import com.tpvision.smartinstall.util.SiIdentifiers;
import com.tpvision.smartinstall.util.Utils;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "devices")
@DynamicUpdate(true)
public class Devices {
   public static final String CLONE_MODE_UPGRADE = "Upgrade";
   public static final String CLONE_MODE_UPLOAD = "Upload";
   public static final String CLONE_TYPE_CLONE = "clone";
   public static final String UPGRADE_STOP_COLOR = "black";
   public static final String UPGRADE_ASSIGNED_COLOR = "blue";
   public static final String UPGRADE_UPGRADING_COLOR = "#FFBF00";
   public static final String UPGRADE_FAILURE_COLOR = "red";
   public static final String UPGRADE_SUCCESS_COLOR = "#01DF01";
   public static final String PROCESS_STOP = "ST";
   public static final String PROCESS_ASSIGNEED = "U";
   public static final String PROCESS_INPROCESS = "INP";
   public static final String STATUS_SUCCESS = "Successful";
   @Id
   @Column(name = "id")
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
   @Column(name = "LastCloneRename")
   private String lastCloneRename = "Unknown";
   @Column(name = "remotecontrol_status")
   private String remotecontrolStatus;
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
   private String lastSuccessSettingPackageId;
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
   @Column(name = "channel_color")
   private String channelColor;
   @Column(name = "app_color")
   private String appColor;
   @Column(name = "upgrade_type")
   private String upgradeType;
   @Column(name = "si_Identifiers")
   private String siIdentifiers;
   @Column(name = "networkInterfaceIp")
   private String networkInterfaceIp;
   @Column(name = "clone_type")
   private String cloneType = "None";
   @Column(name = "clone_path")
   private String clonePath;
   @Column(name = "SecureCmdSupport")
   private String secureCmdSupport;
   @Column(name = "vsecureKey")
   private String vsecureKey;
   @Column(name = "wls_port")
   private int wlsPort;
   @Column(name = "wls_secure_port")
   private int wlsSecurePort;
   @Column(name = "secure_key_support")
   private String secureKeySupport;
   @Column(name = "pms_sync_status")
   private int pmsSyncStatus;
   @Column(name = "standby_mode")
   private String standbyMode;
   @Transient
   private boolean isRefreshPage = false;
   @Transient
   private boolean isWriteMetricsLog = false;
   @Transient
   private boolean isCreateNewDevice = false;
   @Transient
   private boolean isForwardToCastServer = false;

   public Devices() {
   }

   public Devices(boolean isCreateNewDevice) {
      this.isCreateNewDevice = isCreateNewDevice;
   }

   public int getPmsSyncStatus() {
      return this.pmsSyncStatus;
   }

   public void setPmsSyncStatus(int pmsSyncStatus) {
      this.pmsSyncStatus = pmsSyncStatus;
   }

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
      this.updateRefreshPageValue(StringUtils.equals(tvname, this.tvname));
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
      this.updateRefreshPageValue(StringUtils.equals(tvroomid, this.tvroomid));
      this.updateForwardToCastServer(StringUtils.equals(tvroomid, this.tvroomid));
      this.tvroomid = tvroomid;
   }

   public String getTvmacaddress() {
      return this.tvmacaddress;
   }

   public void setTvmacaddress(String tvmacaddress) {
      this.updateForwardToCastServer(StringUtils.equals(tvmacaddress, this.tvmacaddress));
      this.tvmacaddress = tvmacaddress;
   }

   public String getTvipaddress() {
      return this.tvipaddress;
   }

   public void setTvipaddress(String tvipaddress) {
      this.updateRefreshPageValue(StringUtils.equals(tvipaddress, this.tvipaddress));
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
      boolean isSame = StringUtils.equals(powerstatus, this.powerstatus);
      this.updateRefreshPageValue(isSame);
      this.updateWriteMetricsLog(isSame);
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

   public String getRemotecontrolStatus() {
      return this.remotecontrolStatus;
   }

   public void setRemotecontrolStatus(String remotecontrolStatus) {
      this.updateRefreshPageValue(StringUtils.equals(remotecontrolStatus, this.remotecontrolStatus));
      this.remotecontrolStatus = remotecontrolStatus;
   }

   public String getStatus() {
      return this.status;
   }

   public void setStatus(String status) {
      this.updateRefreshPageValue(StringUtils.equals(status, this.status));
      this.status = status;
   }

   public String getProgress() {
      return this.progress;
   }

   public void setProgress(String progress) {
      this.updateRefreshPageValue(StringUtils.equals(progress, this.progress));
      this.progress = progress;
   }

   public String getTvstatus() {
      return this.tvstatus;
   }

   public void setTvstatus(String tvstatus) {
      this.updateRefreshPageValue(StringUtils.equals(tvstatus, this.tvstatus));
      this.tvstatus = tvstatus;
   }

   public String getSiCloneIdentifiers() {
      return this.siCloneIdentifiers;
   }

   public void setSiCloneIdentifiers(String siCloneIdentifiers) {
      this.updateRefreshPageValue(StringUtils.equals(siCloneIdentifiers, this.siCloneIdentifiers));
      this.siCloneIdentifiers = siCloneIdentifiers;
   }

   public String getSiIdentifiers() {
      return this.siIdentifiers;
   }

   public void setSiIdentifiers(String siIdentifiers) {
      if (!this.isRefreshPage) {
         SiIdentifiers oldIdentifiers = SiIdentifiers.fromJson(this.siIdentifiers);
         String oldTvSettingVersionNo = oldIdentifiers.getCloneItemVersionFromTvResponseItems("TVSettings");
         SiIdentifiers newIdentifiers = SiIdentifiers.fromJson(siIdentifiers);
         String newTvSettingVersionNo = newIdentifiers.getCloneItemVersionFromTvResponseItems("TVSettings");
         this.updateRefreshPageValue(StringUtils.equalsIgnoreCase(oldTvSettingVersionNo, newTvSettingVersionNo));
      }

      this.siIdentifiers = siIdentifiers;
   }

   public String getSiFirmwareIdentifier() {
      return this.siFirmwareIdentifier;
   }

   public void setSiFirmwareIdentifier(String siFirmwareIdentifier) {
      this.updateRefreshPageValue(StringUtils.equals(siFirmwareIdentifier, this.siFirmwareIdentifier));
      this.siFirmwareIdentifier = siFirmwareIdentifier;
   }

   public String getTvCloneIdentifiers() {
      return this.tvCloneIdentifiers;
   }

   public void setTvCloneIdentifiers(String tvCloneIdentifiers) {
      this.updateRefreshPageValue(StringUtils.equals(tvCloneIdentifiers, this.tvCloneIdentifiers));
      this.tvCloneIdentifiers = tvCloneIdentifiers;
   }

   public String getTvFirmwareIdentifier() {
      return this.tvFirmwareIdentifier;
   }

   public void setTvFirmwareIdentifier(String tvFirmwareIdentifier) {
      boolean isSame = StringUtils.equals(tvFirmwareIdentifier, this.tvFirmwareIdentifier);
      this.updateRefreshPageValue(isSame);
      this.updateWriteMetricsLog(isSame);
      this.tvFirmwareIdentifier = tvFirmwareIdentifier;
   }

   public String getLastSuccessSettingPackageId() {
      return this.lastSuccessSettingPackageId;
   }

   public void setLastSuccessSettingPackageId(String lastSuccessSettingPackageId) {
      this.updateRefreshPageValue(StringUtils.equals(lastSuccessSettingPackageId, this.lastSuccessSettingPackageId));
      this.lastSuccessSettingPackageId = lastSuccessSettingPackageId;
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
      if (!this.isRefreshPage && !this.isCreateNewDevice) {
         if (StringUtils.isEmpty(this.lastonline)) {
            this.isRefreshPage = true;
         } else {
            long maxOnlineExpireMilSecs = Utils.getMaxOnlineExpireMilSecs();
            boolean isWebServiceUrlError = Utils.isWebServiceUrlError(maxOnlineExpireMilSecs, this.powerstatus, this.tvipaddress, this.lastonline, this.type);
            if (isWebServiceUrlError) {
               this.isRefreshPage = true;
            }
         }
      }

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
      boolean isSame = StringUtils.equals(cloneColor, this.cloneColor);
      this.updateRefreshPageValue(isSame);
      this.updateWriteMetricsLog(isSame);
      this.cloneColor = cloneColor;
   }

   public String getFwColor() {
      return this.fwColor;
   }

   public void setFwColor(String fwColor) {
      boolean isSame = StringUtils.equals(fwColor, this.fwColor);
      this.updateRefreshPageValue(isSame);
      this.updateWriteMetricsLog(isSame);
      this.fwColor = fwColor;
   }

   public String getNetworkInterfaceIp() {
      return this.networkInterfaceIp;
   }

   public void setNetworkInterfaceIp(String networkInterfaceIp) {
      this.networkInterfaceIp = networkInterfaceIp;
   }

   public String getCloneType() {
      return this.cloneType;
   }

   public void setCloneType(String cloneType) {
      this.cloneType = cloneType;
   }

   public String getSecureCmdSupport() {
      return this.secureCmdSupport;
   }

   public void setSecureCmdSupport(String secureCmdSupport) {
      this.secureCmdSupport = secureCmdSupport;
   }

   public String getVsecureKey() {
      return this.vsecureKey;
   }

   public void setVsecureKey(String vsecureKey) {
      this.vsecureKey = vsecureKey;
   }

   public String getClonePath() {
      return this.clonePath;
   }

   public void setClonePath(String clonePath) {
      this.clonePath = clonePath;
   }

   public String getLastCloneRename() {
      return this.lastCloneRename;
   }

   public void setLastCloneRename(String lastCloneRename) {
      this.updateRefreshPageValue(StringUtils.equals(lastCloneRename, this.lastCloneRename));
      this.lastCloneRename = lastCloneRename;
   }

   public String getChannelColor() {
      return this.channelColor;
   }

   public void setChannelColor(String channelColor) {
      this.channelColor = channelColor;
   }

   public String getAppColor() {
      return this.appColor;
   }

   public void setAppColor(String appColor) {
      this.appColor = appColor;
   }

   public String getUpgradeType() {
      return this.upgradeType;
   }

   public void setUpgradeType(String upgradeType) {
      this.upgradeType = upgradeType;
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

   public boolean isRFDevice() {
      return this.getTvipaddress().equalsIgnoreCase("RF");
   }

   public boolean isSecureHttpJapit() {
      return "true".equalsIgnoreCase(this.secureCmdSupport);
   }

   public String getSecureKeySupport() {
      return this.secureKeySupport;
   }

   public void setSecureKeySupport(String secureKeySupport) {
      this.secureKeySupport = secureKeySupport;
   }

   public String getStandbyMode() {
      return this.standbyMode;
   }

   public void setStandbyMode(String standbyMode) {
      this.standbyMode = standbyMode;
   }

   public boolean isOnline() {
      return this.getPowerstatus().equalsIgnoreCase("On") || this.getPowerstatus().equalsIgnoreCase("Standby");
   }

   private void updateRefreshPageValue(boolean isSame) {
      if (!this.isRefreshPage && !isSame) {
         this.isRefreshPage = true;
      }
   }

   private void updateForwardToCastServer(boolean isSame) {
      if (!this.isForwardToCastServer && !isSame) {
         this.isForwardToCastServer = true;
      }
   }

   public boolean isNeedRefresh() {
      if (this.isRefreshPage) {
         this.isRefreshPage = false;
         return true;
      } else {
         return false;
      }
   }

   private void updateWriteMetricsLog(boolean isSame) {
      if (!this.isWriteMetricsLog && !isSame) {
         this.isWriteMetricsLog = true;
      }
   }

   public boolean isNeedWriteMetricsLog() {
      if (this.isWriteMetricsLog) {
         this.isWriteMetricsLog = false;
         return true;
      } else {
         return false;
      }
   }

   public boolean isCreateNewDevice() {
      return this.isCreateNewDevice;
   }

   public boolean isForwardToCastServer() {
      if (this.isForwardToCastServer) {
         this.isForwardToCastServer = false;
         return true;
      } else {
         return false;
      }
   }
}
