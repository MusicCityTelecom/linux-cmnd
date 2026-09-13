package com.tpvision.smartinstall.japit.webservices;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.mgr.DevicesManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.SettingManager;
import com.tpvision.smartinstall.japit.IPCloneService;
import com.tpvision.smartinstall.japit.IPCloneServiceManager;
import com.tpvision.smartinstall.servlet.IPTVPooling;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.DownloadLimiter;
import com.tpvision.smartinstall.util.IPUpgradeManager;
import com.tpvision.smartinstall.util.JAPITUtils;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.SiIdentifiers;
import com.tpvision.smartinstall.util.TpvStringUtils;
import com.tpvision.smartinstall.util.UploadCloneUtils;
import com.tpvision.smartinstall.xml.script.Activity;
import com.tpvision.smartinstall.xml.script.Script;
import com.tpvision.smartinstall.xml.setting.v2k16.roomspecific.RoomSpecificSettings;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IPCloneServiceHandler extends WebServiceCommandHandler {
   private static final Logger LOG = LoggerFactory.getLogger(IPCloneServiceHandler.class);
   private static final Integer MAX_RESEND_COUNTER = 3;
   private static final Map<String, Integer> reSendCounter = new HashMap<>();
   private static final DownloadLimiter downloadLimiter = DownloadLimiter.getInstance();

   @Override
   public String execute() {
      String retData = " ";
      DevicesManager iptvmanager = JpaManager.getDevicesManager();
      if ("Upgrade".equals(this.device.getCloneMode())) {
         LOG.info("IPCloneService for upgrade,tvPowerStatus={}, tvProgress={}", this.device.getPowerstatus(), this.device.getProgress());
      } else if ("Upload".equals(this.device.getCloneMode())) {
         LOG.info("IPCloneService for upload, tvPowerStatus={}, tvProgress={}", this.device.getPowerstatus(), this.device.getUploadProgress());
      }

      JSONObject ipCloneParameters = this.commandDetails.optJSONObject("IPCloneParameters");
      if (ipCloneParameters == null) {
         LOG.error("real.chen: upgradeParameters is empty.");
         return "";
      }

      IPCloneServiceManager.refreshTVCloneItemVersionToDb(this.commandDetails, this.device);
      JSONObject cloneToServerParameters = this.commandDetails.optJSONObject("CloneToServerParameters");
      if (null != cloneToServerParameters) {
         String currentUpgradeStatus = ipCloneParameters.getString("CurrentUpgradeStatus");
         String cloneToServerStatus = cloneToServerParameters.getString("CloneToServerStatus");
         LOG.info("tvip={}, upgradeStatus={}, uploadStatus={}", this.clientIp, currentUpgradeStatus, cloneToServerStatus);
         if ("ReadyForUpgrade".equalsIgnoreCase(currentUpgradeStatus)) {
            retData = this.handleReadyForUpgrade(this.device, cloneToServerStatus, ipCloneParameters, cloneToServerParameters);
         } else if ("UpgradeInProgress".equalsIgnoreCase(currentUpgradeStatus)) {
            this.handleUpgradeInProgress(this.device, iptvmanager);
         } else if ("NotInUpgradeMode".equalsIgnoreCase(currentUpgradeStatus)) {
            if ("Upload".equals(this.device.getCloneMode()) && !"InProgress".equalsIgnoreCase(cloneToServerStatus)) {
               retData = UploadCloneUtils.processUploadMsg(this.device, cloneToServerParameters);
            } else if ("Upgrade".equals(this.device.getCloneMode())) {
               retData = this.handleNotInUpgradeModeStatus(this.device, ipCloneParameters);
            }
         }
      } else {
         String currentUpgradeStatus = ipCloneParameters.get("CurrentUpgradeStatus").toString();
         if ("ReadyForUpgrade".equalsIgnoreCase(currentUpgradeStatus)) {
            retData = this.generateReadyForUpgradeJapit(this.device);
            LOG.info("iptv#1497 if(\"ReadyForUpgrade\".equalsIgnoreCase(currentUpgradeStatus))");
         } else if ("NotInUpgradeMode".equalsIgnoreCase(currentUpgradeStatus)) {
            retData = this.handleNotInUpgradeModeStatus(this.device, ipCloneParameters);
            LOG.info("iptv#1499 et_data = NotInUpgradeMode");
         }
      }

      return retData;
   }

   private String handleNotInUpgradeModeStatus(Devices tv, JSONObject ipCloneParameters) {
      String data = "";
      JSONObject cloneSessionStatus = ipCloneParameters.optJSONObject("CloneSessionStatus");
      if (null != cloneSessionStatus) {
         JSONArray cloneItemStatus = cloneSessionStatus.optJSONArray("CloneItemStatus");
         String tvProgress = tv.getProgress();
         String sessionStatus = cloneSessionStatus.optString("SessionStatus");
         LOG.info("response SessionStatus={}", sessionStatus);
         if ("Upgrade".equalsIgnoreCase(tv.getCloneMode()) && "INP".equalsIgnoreCase(tvProgress)) {
            LOG.info("TV progress is INP and clone mode is upgrade");
            if ("Failed".equalsIgnoreCase(sessionStatus) || "Failure".equalsIgnoreCase(sessionStatus) || "".equalsIgnoreCase(sessionStatus)) {
               LOG.info("start to handle failure result");
               boolean isReSendCloneUrl = this.checkReSendCloneUrl(tv.getCloneType(), tv.getCloneid());
               if (isReSendCloneUrl) {
                  LOG.info("NotInUpgradeMode => Script cause tv response Failure or Failed, now not need any handle");
               } else if (cloneSessionStatus.toString().contains("OutOfMemory")) {
                  LOG.info("find out memory tag, skip the retry");
                  this.upgradeFailure(
                     tv,
                     sessionStatus,
                     "Upgrade failed due to insufficient storage space, will not retry! TV ID: " + tv.getId() + ", IP: " + tv.getTvipaddress()
                  );
               } else {
                  LOG.info("NotInUpgradeMode => TV response failure sessionStatus, check IP upgrade version and send again");
                  List<String> retryTvUpgradeItem = this.checkIpUpgradeVersion(tv.getType(), tv.getTvserialnumber(), tv.getSiIdentifiers(), cloneItemStatus);
                  if (!retryTvUpgradeItem.isEmpty()) {
                     return this.reSendClone(tv, sessionStatus);
                  }

                  LOG.info("after check IP upgrade version, not need resend");
                  this.upgradeFailure(
                     tv, sessionStatus, "Upgrade failed due to tv response <" + sessionStatus + ">! TV ID: " + tv.getId() + ", IP: " + tv.getTvipaddress()
                  );
               }
            } else if ("PartiallySuccessful".equalsIgnoreCase(sessionStatus)
               || "Successful".equalsIgnoreCase(sessionStatus)
               || "Sucessful".equalsIgnoreCase(sessionStatus)
               || "Not Available".equalsIgnoreCase(sessionStatus)) {
               LOG.info("start to handle success result");
               if (tv.getFirmwareid() != 0) {
                  if (!StringUtils.equalsIgnoreCase(tv.getTvFirmwareIdentifier(), tv.getSiFirmwareIdentifier())) {
                     LOG.warn("tv response SessionStatus success, but fw upgrade failed");
                     return this.reSendClone(tv, sessionStatus);
                  }

                  LOG.info("fw upgrade success");
                  if (tv.getType().contains("2016") || tv.getType().contains("2014/2015 MS")) {
                     tv.setPowerstatus("REBOOT");
                  }

                  IPCloneServiceManager.updateFirmWareUpgradeColor(tv, "#01DF01");
                  if (tv.getCloneid() == 0) {
                     LOG.info("only assign fw, finish this upgrade");
                     this.upgradeSuccess(tv, sessionStatus);
                  } else if (tv.getCloneid() != 0) {
                     List<String> retryTvUpgradeItem = this.checkIpUpgradeVersion(tv.getType(), tv.getTvserialnumber(), tv.getSiIdentifiers(), cloneItemStatus);
                     if (retryTvUpgradeItem.isEmpty()) {
                        LOG.info("assign fw + cl tv response shows clone {} is upgrade successfully, finish this upgrade", tv.getCloneid());
                        this.upgradeSuccess(tv, sessionStatus);
                     } else {
                        LOG.info("assign fw + cl, clone {} not finished, waiting further response...", tv.getCloneid());
                        boolean isReSendCloneUrl = this.checkReSendCloneUrl(tv.getCloneType(), tv.getCloneid());
                        if (isReSendCloneUrl) {
                           LOG.info("clone id:{} enter iic mode ,resend clone japit", tv.getCloneid());
                           data = this.generateReadyForUpgradeJapit(tv);
                           return this.getReSendUpgradeData(tv, data);
                        }
                     }
                  }
               } else if (tv.getCloneid() != 0) {
                  LOG.info("clone return upgrade success");
                  List<String> retryTvUpgradeItem = this.checkIpUpgradeVersion(tv.getType(), tv.getTvserialnumber(), tv.getSiIdentifiers(), cloneItemStatus);
                  if (!retryTvUpgradeItem.isEmpty()) {
                     return this.reSendClone(tv, sessionStatus);
                  }

                  this.upgradeSuccess(tv, sessionStatus);
               }
            } else if ("Cancelled".equalsIgnoreCase(sessionStatus)) {
               LOG.info("start to handle cancelled result, revert to assigned status");
               this.revertToAssignStatus(tv);
            } else {
               LOG.info("sessionStatus={},skip handle it", sessionStatus);
            }
         } else {
            LOG.info("tv is not in upgrading in process status, skip handle");
         }
      } else {
         LOG.warn("cloneSessionStatus is null, just skip");
      }

      LOG.info("handleNotInUpgradeMode end, handle data and return");
      return this.dataHandle();
   }

   private String dataHandle() {
      return IPCloneServiceManager.changeIPCloneService(this.tvUniqueId);
   }

   private String generateReadyForUpgradeJapit(Devices tv) {
      String data = "";
      if ("Upgrade".equalsIgnoreCase(tv.getCloneMode()) && "U".equals(tv.getProgress()) || "INP".equalsIgnoreCase(tv.getProgress())) {
         LOG.info(
            "generate ReadyForUpgrade japit, current tv progress={}  fwId={},cloneId={},type={}",
            tv.getProgress(),
            tv.getFirmwareid(),
            tv.getCloneid(),
            tv.getType()
         );
         DevicesManager iptvmanager = JpaManager.getDevicesManager();
         if (tv.getFirmwareid() != 0 && tv.getCloneid() == 0 && tv.getType().contains("2016") && !"INP".equalsIgnoreCase(tv.getProgress())) {
            tv.setProgress("INP");
            iptvmanager.save(tv);
         }

         if (tv.getType().contains("2K14")) {
            if ("INP".equalsIgnoreCase(tv.getProgress())) {
               return data;
            }

            tv.setProgress("INP");
            iptvmanager.save(tv);
         }

         IPCloneService iPCloneService = IPCloneServiceManager.generateIPCloneService(tv, JAPITUtils.WebServiceType.WebServices);
         if (!iPCloneService.isDownloadUrlEmpty()) {
            IPCloneServiceManager.setColorForFWClone(tv, "#FFBF00");
            data = iPCloneService.toJson();
            SiIdentifiers siIdentifiers = SiIdentifiers.fromJson(tv.getSiIdentifiers());
            siIdentifiers.updateTvUpgradeItemsByIpCloneService(iPCloneService);
            tv.setSiIdentifiers(siIdentifiers.toJson());
            iptvmanager.save(tv);
         } else {
            this.upgradeSuccess(tv, "Successful");
         }
      }

      return data;
   }

   private void handleUpgradeInProgress(Devices tv, DevicesManager iptvmanager) {
      if ("ST".equalsIgnoreCase(tv.getProgress())) {
         LOG.warn("TV is in ST status now, ignore change status");
      } else {
         if (!IPUpgradeManager.isTVUpgrading(tv)) {
            IPCloneServiceManager.setColorForFWClone(tv, "#FFBF00");
            tv.setProgress("INP");
            iptvmanager.save(tv);
            LOG.info("tv response UpgradeInProgress, change status to upgrading");
         }
      }
   }

   private String handleReadyForUpgrade(Devices tv, String cloneToServerStatus, JSONObject ipCloneParameters, JSONObject cloneToServerParameters) {
      boolean isReSendCloneUrl = this.checkReSendCloneUrl(tv.getCloneType(), tv.getCloneid());
      String retData = " ";
      String previousProgress = tv.getProgress();
      LOG.info(
         "ReadyForUpgrade cloneMode={} Progress={} PowerState={} cloneToServerStatus={} isReSendCloneUrl={}",
         tv.getCloneMode(),
         previousProgress,
         tv.getPowerstatus(),
         cloneToServerStatus,
         isReSendCloneUrl
      );
      if ("Upgrade".equals(tv.getCloneMode())) {
         LOG.info("TV is in upgrade mode");
         if ("ST".equals(tv.getProgress())) {
            LOG.info("cmnd tv is in stop status, return default japit");
            retData = this.dataHandle();
         } else if (!"U".equals(tv.getProgress()) && (!isReSendCloneUrl || !"INP".equals(tv.getProgress()))) {
            if (tv.getFirmwareid() == 0 && "INP".equalsIgnoreCase(previousProgress)) {
               retData = this.autoStandbyUpgradeHandler(tv, ipCloneParameters);
            } else if (tv.getFirmwareid() != 0 && "INP".equals(tv.getProgress()) && PlatformUtils.isContainsTypeForStandbyUpgrade(tv.getType())) {
               retData = this.androidFWUpgradeReadyForUpgrade(ipCloneParameters, tv);
            }
         } else if (downloadLimiter.isDownloadAllowed(tv)) {
            downloadLimiter.startUpgrade(tv);
            retData = this.generateReadyForUpgradeJapit(tv);
            if (isReSendCloneUrl && "INP".equals(tv.getProgress())) {
               LOG.info("ReadyForUpgrade=>isReSendCloneUrl={}", isReSendCloneUrl);
               retData = this.getReSendUpgradeData(tv, retData);
            }
         }
      } else if ("Upload".equals(tv.getCloneMode()) && "Ready".equalsIgnoreCase(cloneToServerStatus)) {
         LOG.info("TV is in upload clone file mode");
         retData = UploadCloneUtils.processUploadMsg(tv, cloneToServerParameters);
      }

      return retData;
   }

   private boolean checkReSendCloneUrl(String cloneType, Integer cloneId) {
      boolean isReSendCloneUrl = false;
      if (cloneId != null && cloneId > 0) {
         isReSendCloneUrl = checkScriptReinstallTvCommand(cloneType, cloneId);
      }

      return isReSendCloneUrl;
   }

   private static boolean checkScriptReinstallTvCommand(String selectCloneType, int cloneId) {
      boolean hasReinstallTvCommand = false;
      if (!"Clone".equals(selectCloneType)) {
         return hasReinstallTvCommand;
      }

      if (cloneId <= 0) {
         return hasReinstallTvCommand;
      }

      SettingManager setMgr = JpaManager.getSettingManager();
      Setting set = setMgr.loadByKey(cloneId);
      if (null == set) {
         return hasReinstallTvCommand;
      }

      String srcRootPath = CommonConstants.CLONE_PROCESS_LOCATION
         + set.getName()
         + File.separator
         + PlatformUtils.getPlatformId(set.getPlatform())
         + File.separator
         + "MasterCloneData"
         + File.separator
         + "Script";
      File scriptDire = new File(srcRootPath);
      if (scriptDire.exists() && scriptDire.isDirectory()) {
         String scriptPath = srcRootPath + File.separator + "Script.xml";
         File scriptFile = new File(scriptPath);
         if (!scriptFile.exists()) {
            return hasReinstallTvCommand;
         }

         try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Script.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Script script = (Script)unmarshaller.unmarshal(scriptFile);
            if (null == script) {
               return hasReinstallTvCommand;
            }

            List<Activity> activities = script.getActivity();
            int activitySize = activities.size();
            if (activitySize == 0) {
               return hasReinstallTvCommand;
            }

            for (Activity activity : activities) {
               if ("ReinstallTV".equalsIgnoreCase(activity.getAction())) {
                  hasReinstallTvCommand = true;
                  break;
               }
            }
         } catch (JAXBException e) {
            LOG.error(e.getMessage(), e);
         }
      }

      LOG.info("hasReinstallTvCommand={}", hasReinstallTvCommand);
      return hasReinstallTvCommand;
   }

   private Map<String, String> getTvUpgradeResponseVersion(JSONArray tvItemStatus) {
      Map<String, String> tvItemUpgradeVersion = new HashMap<>();
      if (null != tvItemStatus) {
         int tvItemCount = tvItemStatus.length();

         for (int i = 0; i < tvItemCount; i++) {
            JSONObject cloneItem = tvItemStatus.getJSONObject(i);
            String cloneStatus = cloneItem.getString("CloneStatus");
            if (!"NotAvailable".equals(cloneStatus)) {
               JSONObject cloneItemDetails = cloneItem.getJSONObject("CloneItemDetails");
               String cloneItemName = cloneItemDetails.getString("CloneItemName");
               String cloneItemVersionNo;
               if ("Cancelled".equals(cloneStatus)) {
                  cloneItemVersionNo = "";
               } else {
                  cloneItemVersionNo = cloneItemDetails.optString("CloneItemVersionNo");
               }

               tvItemUpgradeVersion.put(cloneItemName, cloneItemVersionNo);
            }
         }
      }

      return tvItemUpgradeVersion;
   }

   private List<String> checkIpUpgradeVersion(String type, String tvserialnumber, String siIdentifiers, JSONArray tvItemStatus) {
      LOG.info("start check ip upgrade version");
      List<String> retryTvUpgradeItem = new ArrayList<>();
      SiIdentifiers siIdentifiersObj = SiIdentifiers.fromJson(siIdentifiers);
      Map<String, String> trCloneUpgradeVersionMap = siIdentifiersObj.getTvItemUpgradeVersion();
      Map<String, String> tvUpgradeResponseVersion = this.getTvUpgradeResponseVersion(tvItemStatus);
      List<String> ignoreCheckList = Arrays.asList("DataDump", "HTVCfg.xml", "Script");

      for (String itemName : trCloneUpgradeVersionMap.keySet()) {
         if (ignoreCheckList.contains(itemName)) {
            LOG.warn("{} ignore version check", itemName);
         } else if (!tvUpgradeResponseVersion.containsKey(itemName)) {
            LOG.warn("tv response clone items does not contain valid clone: {}, ignore retry", itemName);
         } else if ("RoomSpecificSettings".equalsIgnoreCase(itemName)) {
            boolean isSame = this.checkRoomSpecialSettingVersion(type, tvserialnumber);
            if (!isSame) {
               LOG.warn("RoomSpecificSettings check same failure. retry again.");
               retryTvUpgradeItem.add(itemName);
            }
         } else {
            String tvItemVersionNo = tvUpgradeResponseVersion.get(itemName);
            String cloneUpgradeVersionNo = trCloneUpgradeVersionMap.get(itemName);
            if (StringUtils.equalsIgnoreCase(tvItemVersionNo, cloneUpgradeVersionNo)) {
               LOG.info("{} upgrade success ,new version <{}> ", itemName, tvItemVersionNo);
            } else {
               LOG.warn("{} upgrade failure assign version:{}, response version:{}", itemName, cloneUpgradeVersionNo, tvItemVersionNo);
               retryTvUpgradeItem.add(itemName);
            }
         }
      }

      LOG.info("[iptv]retryTvUpgradeItem={}", retryTvUpgradeItem);
      return retryTvUpgradeItem;
   }

   private String reSendClone(Devices tv, String sessionStatus) {
      LOG.info("start resend clone");
      if (!reSendCounter.containsKey(tv.getId())) {
         reSendCounter.put(tv.getId(), MAX_RESEND_COUNTER);
      }

      if (reSendCounter.get(tv.getId()) == 0) {
         this.upgradeFailure(
            tv,
            sessionStatus,
            "Display <"
               + tv.getTvserialnumber()
               + "> upgrade failed! Reason-(retry "
               + MAX_RESEND_COUNTER
               + " times upgrade,"
               + tv.getTvroomid()
               + " tv response failed)!"
         );
      } else {
         reSendCounter.put(tv.getId(), reSendCounter.get(tv.getId()) - 1);
         LOG.info("NotInUpgradeMode DeviceId={} reSendCounter={} ", tv.getId(), reSendCounter.get(tv.getId()));
         IPTVPooling.notifyUpgradeStatusChange(new JSONObject().put("status", "retry"));
         LOG.info("resend clone file by WLS");
         IPCloneServiceManager.sendIPCloneDataToTV(tv);
      }

      return "";
   }

   private void upgradeFailure(Devices tv, String sessionStatus, String failureReason) {
      LOG.info("NotInUpgradeMode => tv response Failure or Failed or cancelled");
      tv.setProgress("ST");
      IPCloneServiceManager.setColorForFWClone(tv, "red");
      tv.setStatus(sessionStatus);
      reSendCounter.remove(tv.getId());
      JpaManager.getDevicesManager().save(tv);
      JSONObject failureDetail = new JSONObject("{\"status\":\"fail\"}");
      failureDetail.put("reason", failureReason);
      IPTVPooling.notifyUpgradeStatusChange(failureDetail);
      IPUpgradeManager.cleanExpiredCloneFiles(tv, null);
      downloadLimiter.upgradeComplete(tv);
   }

   private void revertToAssignStatus(Devices tv) {
      tv.setProgress("U");
      IPCloneServiceManager.setColorForFWClone(tv, "blue");
      reSendCounter.remove(tv.getId());
      JpaManager.getDevicesManager().save(tv);
      downloadLimiter.upgradeComplete(tv);
   }

   private void upgradeSuccess(Devices tv, String sessionStatus) {
      tv.setProgress("ST");
      tv.setStatus(sessionStatus);
      IPCloneServiceManager.setColorForFWClone(tv, "#01DF01");
      reSendCounter.remove(tv.getId());
      JpaManager.getDevicesManager().save(tv);
      IPTVPooling.notifyUpgradeStatusChange(new JSONObject("{\"status\":\"success\"}").put("TVID", tv.getId()));
      JpaManager.getDevicesManager().save(tv);
      IPUpgradeManager.cleanExpiredCloneFiles(tv, null);
      downloadLimiter.upgradeComplete(tv);
   }

   private String autoStandbyUpgradeHandler(Devices tv, JSONObject ipCloneParameters) {
      LOG.info("standby clone upgrade handler-> tv={}  ip:={} ", this.tvUniqueId, this.clientIp);
      JSONObject cloneSessionStatus = ipCloneParameters.optJSONObject("CloneSessionStatus");
      if (null != cloneSessionStatus) {
         JSONArray cloneItemStatus = cloneSessionStatus.getJSONArray("CloneItemStatus");
         String sessionStatus = cloneSessionStatus.getString("SessionStatus");
         if ("Failed".equalsIgnoreCase(sessionStatus) || "Failure".equalsIgnoreCase(sessionStatus)) {
            return this.generateReadyForUpgradeJapit(tv);
         }

         if (!"InProgress".equalsIgnoreCase(sessionStatus)) {
            List<String> retryTvUpgradeItem = this.checkIpUpgradeVersion(tv.getType(), tv.getTvserialnumber(), tv.getSiIdentifiers(), cloneItemStatus);
            if (!retryTvUpgradeItem.isEmpty()) {
               return this.generateReadyForUpgradeJapit(tv);
            }

            this.upgradeSuccess(tv, sessionStatus);
         }
      }

      return this.dataHandle();
   }

   private String androidFWUpgradeReadyForUpgrade(JSONObject ipCloneParameters, Devices device) {
      JSONObject cloneSessionStatus = ipCloneParameters.optJSONObject("CloneSessionStatus");
      if (cloneSessionStatus != null) {
         String sessionStatus = cloneSessionStatus.optString("SessionStatus");
         LOG.info("standBy firmware upgrade-> tv={} ip={} sessionStatus={}", this.tvUniqueId, this.clientIp, sessionStatus);
         if ("Failed".equalsIgnoreCase(sessionStatus) || "Failure".equalsIgnoreCase(sessionStatus) || "InProgress".equalsIgnoreCase(sessionStatus)) {
            return this.generateReadyForUpgradeJapit(device);
         }

         int positionFwId = device.getTvFirmwareIdentifier().lastIndexOf(device.getSiFirmwareIdentifier());
         if (positionFwId <= -1) {
            LOG.info("fw version check failed,siVersion:<{}>,tv's version:<{}>", device.getSiFirmwareIdentifier(), device.getTvFirmwareIdentifier());
            return this.generateReadyForUpgradeJapit(device);
         }

         LOG.info("fw version check success,same version:<{}>", device.getTvFirmwareIdentifier());
         if (device.getFirmwareid() != 0) {
            IPCloneServiceManager.updateFirmWareUpgradeColor(device, "#01DF01");
            device.setPowerstatus("REBOOT");
            JpaManager.getDevicesManager().save(device);
         }

         if (device.getCloneid() != 0) {
            JSONArray cloneItemStatus = cloneSessionStatus.optJSONArray("CloneItemStatus");
            List<String> retryTvUpgradeItem = this.checkIpUpgradeVersion(
               device.getType(), device.getTvserialnumber(), device.getSiIdentifiers(), cloneItemStatus
            );
            if (!retryTvUpgradeItem.isEmpty()) {
               return this.generateReadyForUpgradeJapit(device);
            }

            this.upgradeSuccess(device, sessionStatus);
         } else {
            LOG.info("fw standby upgrade success");
            this.upgradeSuccess(device, sessionStatus);
         }
      } else {
         LOG.info("CloneSessionStatus is null, skip handle");
      }

      return this.dataHandle();
   }

   private boolean checkRoomSpecialSettingVersion(String type, String tvserialnumber) {
      boolean isSame = true;
      String platform = PlatformUtils.getPlatformId(type);
      String roomSpecificSetting = CommonConstants.CLONE_ASSEMBLY_LOCATION
         + platform
         + File.separator
         + "RoomSpecificSettings"
         + File.separator
         + "RoomSpecificSettings.xml";

      try {
         JAXBContext context = JAXBContext.newInstance("com.tpvision.smartinstall.xml.setting.v2k16.roomspecific");
         Unmarshaller unmarshaller = context.createUnmarshaller();
         File roomSettingXml = new File(roomSpecificSetting);
         if (roomSettingXml.exists()) {
            try (
               FileInputStream stream = new FileInputStream(roomSettingXml);
               Reader freader = new InputStreamReader(stream, "UTF-8");
            ) {
               RoomSpecificSettings roomSettings = (RoomSpecificSettings)unmarshaller.unmarshal(freader);
               if (null != roomSettings) {
                  String roomSpecialSerialNumber = roomSettings.getTV().getSerialNumber();
                  if (null != tvserialnumber
                     && TpvStringUtils.limitStringLength(tvserialnumber, 32).equalsIgnoreCase(TpvStringUtils.limitStringLength(roomSpecialSerialNumber, 32))) {
                     isSame = false;
                  } else {
                     LOG.error("tvserialnumber is not equal, not retry");
                  }
               }
            } catch (Exception e) {
               LOG.error(e.getMessage(), e);
            }
         }
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }

      return isSame;
   }

   private String getReSendUpgradeData(Devices tv, String data) {
      String retryData = "";
      if (!TpvStringUtils.isJSONString(data)) {
         LOG.error("getReSendUpgradeData fail retry data is empty");
         return retryData;
      }

      JSONObject jsonObject = new JSONObject(data);
      if (jsonObject.has("Fun")) {
         IPCloneService iPCloneService = IPCloneServiceManager.generateIPCloneService(tv, JAPITUtils.WebServiceType.WebServices, "MainFirmware");
         if (!iPCloneService.isDownloadUrlEmpty()) {
            IPCloneServiceManager.updateCloneUpgradeColor(tv, "#FFBF00");
            retryData = iPCloneService.toJson();
            SiIdentifiers siIdentifiers = SiIdentifiers.fromJson(tv.getSiIdentifiers());
            siIdentifiers.updateTvUpgradeItemsByIpCloneService(iPCloneService);
            tv.setSiIdentifiers(siIdentifiers.toJson());
         } else {
            this.upgradeSuccess(tv, "Successful");
         }
      }

      return retryData;
   }
}
