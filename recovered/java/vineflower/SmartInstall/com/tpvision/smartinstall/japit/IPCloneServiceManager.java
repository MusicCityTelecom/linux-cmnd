package com.tpvision.smartinstall.japit;

import com.tpvision.smartinstall.core.SettingCreator;
import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.core.UpgSetting;
import com.tpvision.smartinstall.dao.mgr.DevicesManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.servlet.IPProfile;
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.DownloadLimiter;
import com.tpvision.smartinstall.util.HttpUtils;
import com.tpvision.smartinstall.util.JAPITUtils;
import com.tpvision.smartinstall.util.NetworkUtils;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.SiIdentifiers;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.TpvStringUtils;
import com.tpvision.smartinstall.util.UploadCloneUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IPCloneServiceManager {
   private static final Logger LOG = LoggerFactory.getLogger(IPCloneServiceManager.class);
   public static final String UPRADE_STUATUS_READY_FOR_UPGRADE = "ReadyForUpgrade";
   public static final String UPRADE_STUATUS_UPGRADE_IN_PROGRESS = "UpgradeInProgress";
   public static final String UPRADE_STUATUS_NOT_IN_UPGRADE_MODE = "NotInUpgradeMode";

   private IPCloneServiceManager() {
   }

   public static void sendRoomIdToTV(Devices tv) {
      String ip = tv.getTvipaddress();
      String roomid = tv.getTvroomid();
      LOG.info("send roomid <{}> to <{}>", roomid, ip);
      String japitJson = null;
      if (PlatformUtils.isSupportTVSettingUpdateRoomId(tv.getType())) {
         String identifier = SettingCreator.createModifyRoomIdTVSetting(ip, roomid);
         japitJson = generateSimpleTvSettingJson(tv, NetworkUtils.getServerIpFromSameRoute(ip), identifier);
      } else {
         SettingCreator.createRoomSpecificSettings(roomid, tv.getTvserialnumber(), ip, tv.getType());
         japitJson = generateRoomSpecificSettingsJson(tv, ip);
      }

      JAPITUtils.sendAsyncCommand(tv, japitJson);
   }

   public static void updateFirmWareUpgradeColor(Devices device, String colorChange) {
      if (device.getFirmwareid() > 0) {
         LOG.info("ChangeColor to {} in updateFirmWareUpgradeColor", colorChange);
         boolean isFinish = "#01DF01".equalsIgnoreCase(colorChange) || "red".equalsIgnoreCase(colorChange) || "black".equalsIgnoreCase(colorChange);
         device.setFwColor(colorChange);
         if (isFinish) {
            device.setFirmwareid(0);
         }

         JpaManager.getDevicesManager().save(device);
      }
   }

   public static void updateCloneUpgradeColor(Devices device, String colorChange) {
      if (device.getCloneid() > 0) {
         LOG.info("ChangeColor to {} in updateCloneUpgradeColor", colorChange);
         boolean isFinish = "#01DF01".equalsIgnoreCase(colorChange) || "red".equalsIgnoreCase(colorChange) || "black".equalsIgnoreCase(colorChange);
         device.setCloneColor(colorChange);
         if (isFinish) {
            if ("#01DF01".equalsIgnoreCase(colorChange)) {
               device.setTvCloneIdentifiers(device.getLastCloneRename() + ' ' + device.getSiCloneIdentifiers());
               String successSettingPackageId = null;
               if ("Settings".equals(device.getCloneType())) {
                  successSettingPackageId = String.valueOf(device.getCloneid());
               } else if ("Clone".equals(device.getCloneType())) {
                  Setting setting = JpaManager.getSettingManager().loadByKey(device.getCloneid());
                  if (setting != null && setting.getSettingPackageId() > 0) {
                     successSettingPackageId = String.valueOf(setting.getSettingPackageId());
                  }
               }

               if (StringUtils.isNotBlank(successSettingPackageId)) {
                  device.setLastSuccessSettingPackageId(successSettingPackageId);
               }
            }

            device.setCloneid(0);
            device.setCloneType("None");
         }

         JpaManager.getDevicesManager().save(device);
      }
   }

   public static void setColorForFWClone(Devices device, String colorChange) {
      updateFirmWareUpgradeColor(device, colorChange);
      updateCloneUpgradeColor(device, colorChange);
   }

   public static void sendIPCloneDataToTV(Devices device) {
      IPCloneService ipCloneService = generateIPCloneService(device, JAPITUtils.WebServiceType.WebListeningServices);
      if (ipCloneService.isDownloadUrlEmpty()) {
         device.setProgress("ST");
         device.setStatus("Successful");
         setColorForFWClone(device, "#01DF01");
         LOG.warn("download url is empty, skip this upgrade ,update the upgrade status to success directly");
      } else {
         setColorForFWClone(device, "#FFBF00");
         SiIdentifiers siIdentifiers = SiIdentifiers.fromJson(device.getSiIdentifiers());
         siIdentifiers.updateTvUpgradeItemsByIpCloneService(ipCloneService);
         device.setSiIdentifiers(siIdentifiers.toJson());
         String ipCloneJson = ipCloneService.toJson();
         JAPITUtils.sendAsyncCommand(
            device,
            ipCloneJson,
            (bResult, resultJson) -> {
               boolean isUpdateDeviceProcess = false;
               if (bResult) {
                  JSONObject iPCloneParameters = resultJson.optJSONObject("IPCloneParameters");
                  isUpdateDeviceProcess = iPCloneParameters != null
                     && "UpgradeInProgress".equalsIgnoreCase(iPCloneParameters.optString("CurrentUpgradeStatus"));
               } else {
                  isUpdateDeviceProcess = "1.0".equalsIgnoreCase(ipCloneService.SvcVer)
                     && "japit return error,not a valid json string".equalsIgnoreCase(resultJson.getString("errMsg"));
               }

               LOG.info("japit send result is <{}>, and update device process is <{}>", bResult, isUpdateDeviceProcess);
               if (isUpdateDeviceProcess) {
                  DownloadLimiter.getInstance().startUpgrade(device);
                  device.setProgress("INP");
                  JpaManager.getDevicesManager().save(device);
               }
            }
         );
      }

      JpaManager.getDevicesManager().save(device);
   }

   public static void sendDLCommandToTV(Devices tv, String progress) throws Exception {
      String data = generateBaseRequestIPCloneService(tv);
      JSONObject jsonResult = new JSONObject(JAPITUtils.sendJapitCommand(tv, data));
      JSONObject commandDetails = jsonResult.getJSONObject("CommandDetails");
      JSONObject cloneToServerParameters = commandDetails.optJSONObject("CloneToServerParameters");
      if (null == cloneToServerParameters) {
         throw new Exception("clone to server parameter is empty");
      }

      String cloneToServerStatus = cloneToServerParameters.getString("CloneToServerStatus");
      if ("Ready".equalsIgnoreCase(cloneToServerStatus) && cloneToServerParameters.has("CloneToServerSessionStatus")) {
         sendDownloadCloneJson(cloneToServerParameters, tv, progress);
      } else {
         throw new Exception("Tv is not ready");
      }
   }

   private static String generateBaseRequestIPCloneService(Devices tv) {
      return "{  \"Svc\" : \"WebListeningServices\",  \"SvcVer\": \""
         + PlatformUtils.getIpCloneServiceVer(tv)
         + "\",  \"Cookie\": "
         + JAPITUtils.getJapitRandomCookieValue()
         + ",  \"CmdType\" : \"Request\",  \"Fun\" : \"IPCloneService\"}";
   }

   private static String generateCancelCloneJapit(Devices tv) {
      return "{\t\"Svc\": \"WebListeningServices\",\t\"SvcVer\": \""
         + PlatformUtils.getIpCloneServiceVer(tv)
         + "\",\t\"Cookie\": "
         + JAPITUtils.getJapitRandomCookieValue()
         + ",\t\"CmdType\": \"Change\",\t\"Fun\": \"IPCloneService\",\t\"CommandDetails\": {\t\t\"CloneAction\": \"Cancel\",\t\t\"WebListeningServiceParameters\": {\t\t\t\"TVUniqueID\": \""
         + tv.getTvuniqueid()
         + "\"\t\t}\t}}";
   }

   public static String changeIPCloneService(String tvUniqueId) {
      IPProfile lastConfig = IPProfile.loadIPProfile();
      String pollingFrequency = lastConfig.getFastMode();
      String pollingFrequencyGreen = lastConfig.getGreenMode();
      return "{  \"Svc\": \"WebServices\",  \"SvcVer\": \"1.0\",  \"Cookie\": 293,  \"CmdType\": \"Change\",  \"Fun\": \"IPCloneService\",  \"CommandDetails\": {    \"WebServiceParameters\": {      \"PollingFrequency\": "
         + pollingFrequency
         + ",      \"PollingFrequencyGreen\": "
         + pollingFrequencyGreen
         + ",      \"TVUniqueID\": \""
         + tvUniqueId
         + "\"    },    \"IPCloneParameters\": {},    \"CloneToServerParameters\": {}  }}";
   }

   private static void sendDownloadCloneJson(JSONObject cloneToServerParameters, Devices tv, String progress) {
      String tvUniqueId = tv.getTvuniqueid();
      DevicesManager tvmanager = JpaManager.getDevicesManager();
      String tvType = tv.getType();
      JSONArray cloneItemsAvailableToServer = cloneToServerParameters.getJSONArray("CloneItemsAvailableToServer");
      JSONObject session = (JSONObject)cloneToServerParameters.get("CloneToServerSessionStatus");
      String sessionStartTime = String.valueOf(session.get("SessionStartTime"));
      LOG.info("[sendDLCommandToTV]type={},tvUniqueID:{}", tvType, tvUniqueId);
      String downloadJson = generateDownloadClonePackageJson(tv, "WebListeningServices", cloneItemsAvailableToServer);
      UploadCloneUtils.uploadItemsRecvCount_cmnd.put(tvUniqueId, 0);
      UploadCloneUtils.uploadItemsCount_tv.put(tvUniqueId, TpvStringUtils.countStr(downloadJson, "\"URL\""));
      String downloadResultJSON = null;

      try {
         downloadResultJSON = JAPITUtils.sendJapitCommand(tv, downloadJson);
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }

      if (downloadResultJSON == null) {
         LOG.error("fail to start up download process! download JSON return fail");
         tv.setUploadProgress("ST");
         tvmanager.save(tv);
      }

      JSONObject jsonObject = new JSONObject(downloadResultJSON);
      if (jsonObject.get("Fun").toString().equalsIgnoreCase("Error")) {
         LOG.error("fail to start up download process! download JSON return error");
         tv.setUploadProgress("ST");
         tvmanager.save(tv);
      } else {
         tv.setCloneMode("Upload");
         tv.setUploadProgress(progress);
         tv.setUploadSessionStart(sessionStartTime);
         tvmanager.save(tv);
      }
   }

   public static void refreshTVCloneItemVersionToDb(JSONObject commandDetails, Devices tv) {
      JSONObject ipCloneParameters = commandDetails.optJSONObject("IPCloneParameters");
      if (null != ipCloneParameters) {
         JSONArray cloneItemStatus = null;
         JSONObject cloneSessionStatus = ipCloneParameters.optJSONObject("CloneSessionStatus");
         if (cloneSessionStatus != null) {
            cloneItemStatus = cloneSessionStatus.getJSONArray("CloneItemStatus");
         } else {
            JSONObject cloneToServerParameters = commandDetails.optJSONObject("CloneToServerParameters");
            if (null != cloneToServerParameters) {
               JSONObject cloneToServerSessionStatus = (JSONObject)cloneToServerParameters.get("CloneToServerSessionStatus");
               if (null != cloneToServerSessionStatus) {
                  cloneItemStatus = cloneToServerSessionStatus.getJSONArray("CloneItemStatus");
               }
            }
         }

         JSONArray cloneItemsAvailableToServer = null;
         JSONObject cloneToServerParameters = commandDetails.optJSONObject("CloneToServerParameters");
         if (cloneToServerParameters != null) {
            cloneItemsAvailableToServer = cloneToServerParameters.optJSONArray("CloneItemsAvailableToServer");
         }

         JSONArray cloneItemsRequiredForUpgrade = ipCloneParameters.optJSONArray("CloneItemsRequiredForUpgrade");
         SiIdentifiers siIdentifiers = SiIdentifiers.fromJson(tv.getSiIdentifiers());
         siIdentifiers.updateTvResponseItem(cloneItemStatus, cloneItemsAvailableToServer, cloneItemsRequiredForUpgrade);
         DevicesManager tvmanager = JpaManager.getDevicesManager();
         tv.setSiIdentifiers(siIdentifiers.toJson());
         String tvFirmwareIdentifier = siIdentifiers.getCloneItemVersionFromTvResponseItems("MainFirmware");
         if (tvFirmwareIdentifier != null) {
            tv.setTvFirmwareIdentifier(tvFirmwareIdentifier);
         }

         tvmanager.save(tv);
      }
   }

   public static IPCloneService generateIPCloneService(Devices tv, JAPITUtils.WebServiceType webServiceType, String... ignoreUpgradeItems) {
      IPCloneService ipCloneService = new IPCloneService(webServiceType);
      ipCloneService.SvcVer = PlatformUtils.getIpCloneServiceVer(tv);
      if (webServiceType == JAPITUtils.WebServiceType.WebListeningServices) {
         ipCloneService.CommandDetails.WebListeningServiceParameters.TVUniqueID = tv.getId();
      } else {
         ipCloneService.CommandDetails.WebServiceParameters.TVUniqueID = tv.getId();
      }

      String platformId = PlatformUtils.getPlatformId(tv.getType());
      boolean is2K14MS = "TPN141HE_CloneData".equalsIgnoreCase(platformId);
      boolean isNeedCheckLowerVersion = PlatformUtils.isNeedCheckLowerVersion(platformId);
      List<String> ignoreList = Arrays.asList(ignoreUpgradeItems);
      List<String> supportCloneItems = PlatformUtils.getIpUpgradeItems(tv.getType()).stream().filter(e -> !ignoreList.contains(e)).collect(Collectors.toList());
      String serverIp = NetworkUtils.getServerIpFromSameRoute(tv.getTvipaddress());
      String baseServerPath = "";
      if (HttpUtils.isSupportHttpsByUniqueId(tv.getTvuniqueid())) {
         baseServerPath = String.format(Locale.ENGLISH, "https://%s:%d/SmartInstall", serverIp, CommonConstants.CMND_HTTPS_PORT);
      } else {
         baseServerPath = String.format(Locale.ENGLISH, "http://%s:%d/SmartInstall", serverIp, CommonConstants.CMND_HTTP_PORT);
      }

      if (tv.getCloneid() > 0) {
         String clPath = "/Profile/Clone/" + tv.getClonePath() + (StringUtils.endsWith(tv.getClonePath(), "/") ? "" : "/");
         File[] cloneItems = new File(CommonConstants.servletContextPath + clPath).listFiles();
         if (cloneItems == null) {
            LOG.error("clone files not existed ,return empty ipcloneservice japit");
            return ipCloneService;
         }

         List<String> validCloneItemExt = Arrays.asList("upg", "zip");
         SiIdentifiers identifiers = SiIdentifiers.fromJson(tv.getSiIdentifiers());
         boolean isCheckSiAssignItem = PlatformUtils.isMasf2019Up(platformId);
         Map<String, String> assignCloneItems = identifiers.getSiItemUpgradeVersion();

         for (File cloneItem : cloneItems) {
            String cloneFileName = cloneItem.getName();
            String cloneFileExt = FilenameUtils.getExtension(cloneFileName);
            if (!validCloneItemExt.contains(cloneFileExt)) {
               LOG.warn("cloneItemName:{} has a invalid ext {} ,skip upgrade", cloneFileName, cloneFileExt);
            } else {
               String itemBaseName = FilenameUtils.getBaseName(cloneFileName);
               String cloneItemName = CloneItemUtils.convertItemToJapitName(itemBaseName);
               if (!supportCloneItems.contains(cloneItemName)) {
                  LOG.warn("cloneItemName:{} not in supported list ,skip upgrade", cloneFileName);
               } else if (isCheckSiAssignItem && !assignCloneItems.containsKey(cloneItemName)) {
                  LOG.warn("cloneItemName:{} not in assign clone list, skip upgrade", cloneFileName);
               } else {
                  String siCloneIdentifier = is2K14MS ? TpvDateUtils.getCurrentIndentifierFormatTime() : getCloneItemIdentifier(identifiers, cloneItemName);
                  String tvCloneIdentifer = identifiers.getTVCurrentCloneItemIdentifier(cloneItemName);
                  if (!"Script".equalsIgnoreCase(itemBaseName) && siCloneIdentifier.equalsIgnoreCase(tvCloneIdentifer)) {
                     LOG.warn("cloneItemName:{}, tv identifier is same as CMND identifier <{}>,skip upgrade", cloneItemName, siCloneIdentifier);
                  } else {
                     IPCloneService.CloneItemDownloadDetail cloneItemDownloadDetail = new IPCloneService.CloneItemDownloadDetail();
                     cloneItemDownloadDetail.CloneItemDetails.CloneItemName = cloneItemName;
                     cloneItemDownloadDetail.CloneItemDetails.CloneItemVersionNo = siCloneIdentifier;
                     cloneItemDownloadDetail.URL = baseServerPath + clPath + cloneFileName;
                     ipCloneService.CommandDetails.IPCloneParameters.CloneItemDownloadDetails.add(cloneItemDownloadDetail);
                  }
               }
            }
         }
      }

      if (tv.getFirmwareid() > 0 && supportCloneItems.contains("MainFirmware")) {
         int firmwareId = tv.getFirmwareid();
         String swPath = "/Profile/UPG/" + firmwareId + "/MainFirmware.upg";
         File firmwareFile = new File(CommonConstants.servletContextPath + swPath);
         if (firmwareFile.exists()) {
            if (isNeedCheckLowerVersion && PlatformUtils.compareLowerVersion(tv.getSiFirmwareIdentifier(), tv.getTvFirmwareIdentifier())) {
               LOG.warn(
                  "platformId:{}, tv identifier<{}> is higer than CMND identifier <{}>,skip upgrade",
                  platformId,
                  tv.getTvFirmwareIdentifier(),
                  tv.getSiFirmwareIdentifier()
               );
            } else {
               IPCloneService.CloneItemDownloadDetail cloneItemDownloadDetail = new IPCloneService.CloneItemDownloadDetail();
               cloneItemDownloadDetail.CloneItemDetails.CloneItemName = "MainFirmware";
               cloneItemDownloadDetail.CloneItemDetails.CloneItemVersionNo = getFirmwareIdentifier(firmwareId);
               cloneItemDownloadDetail.URL = baseServerPath + swPath;
               ipCloneService.CommandDetails.IPCloneParameters.CloneItemDownloadDetails.add(cloneItemDownloadDetail);
            }
         }
      }

      return ipCloneService;
   }

   public static String getFirmwareIdentifier(int id) {
      String identifier = TpvDateUtils.getDateTime();
      UpgSetting settings = JpaManager.getUpgSettingManager().loadByKey(id);
      if (null != settings) {
         identifier = settings.getIpversion();
      }

      return identifier;
   }

   private static String getCloneIdentifier(String path) {
      String identifier = TpvDateUtils.getCurrentIndentifierFormatTime();
      File tempFile = new File(path + "identifier.txt");
      if (tempFile.exists()) {
         try {
            identifier = FileUtils.readFileToString(tempFile, StandardCharsets.UTF_8).trim();
         } catch (IOException e) {
            LOG.error(e.getMessage(), e);
         }
      }

      return identifier;
   }

   private static String getCloneItemIdentifier(SiIdentifiers identifiers, String cloneItem) {
      for (SiIdentifiers.CloneItem cloneItem1 : identifiers.SiAssignItem) {
         if (cloneItem1.CloneItemName.equalsIgnoreCase(cloneItem)) {
            return cloneItem1.CloneItemVersionNo;
         }
      }

      return TpvDateUtils.getCurrentIndentifierFormatTime();
   }

   private static String generateDownloadClonePackageJson(Devices tv, String svc, JSONArray cloneItemsAvailableToServer) {
      String localip = NetworkUtils.getServerIpFromSameRoute(tv.getTvipaddress());
      boolean bHttps = false;
      String strHttp = HttpUtils.getHttpString(bHttps);
      String uri = strHttp + localip + ":" + HttpUtils.getCMNDPort(bHttps) + "/SmartInstall/CloneToServer";
      JSONObject changeResp = new JSONObject();
      changeResp.put("Svc", svc);
      changeResp.put("SvcVer", PlatformUtils.getIpCloneServiceVer(tv));
      changeResp.put("Cookie", JAPITUtils.getJapitRandomCookieValue());
      changeResp.put("CmdType", "Change");
      changeResp.put("Fun", "IPCloneService");
      JSONObject commandDetails = new JSONObject();
      if ("WebServices".equals(svc)) {
         JSONObject webServiceParameters = new JSONObject();
         webServiceParameters.put("PollingFrequency", 50);
         webServiceParameters.put("PollingFrequencyGreen", 60);
         webServiceParameters.put("TVUniqueID", tv.getTvuniqueid());
         commandDetails.put("WebServiceParameters", webServiceParameters);
      } else {
         JSONObject webServiceParameters = new JSONObject();
         webServiceParameters.put("TVUniqueID", tv.getTvuniqueid());
         commandDetails.put("WebListeningServiceParameters", webServiceParameters);
      }

      JSONObject cloneToServerParameters = new JSONObject();
      cloneToServerParameters.put("CloneToServerDetails", getCloneToServerDetails(uri, cloneItemsAvailableToServer));
      commandDetails.put("CloneToServerParameters", cloneToServerParameters);
      changeResp.put("CommandDetails", commandDetails);
      return changeResp.toString();
   }

   private static JSONArray getCloneToServerDetails(String uri, JSONArray cloneItemsAvailable) {
      Set<String> cloneItems = new HashSet<>();

      for (int i = 0; i < cloneItemsAvailable.length(); i++) {
         JSONObject item = (JSONObject)cloneItemsAvailable.get(i);
         if (item.has("CloneItemName")) {
            String cloneItemName = item.optString("CloneItemName");
            if (StringUtils.isNotBlank(cloneItemName)) {
               cloneItems.add(cloneItemName);
            }
         }
      }

      JSONArray cloneToServerDetails = new JSONArray();
      String[] allCloneItems = new String[]{
         "TVSettings",
         "TVChannelList",
         "WelcomeLogo",
         "SmartInfoImages",
         "SmartInfoPages",
         "AndroidApps",
         "RoomSpecificSettings",
         "DataDump",
         "CustomDashboardFallback",
         "Script",
         "MediaChannels",
         "WeatherForecast",
         "HTVCfg.xml",
         "Banner",
         "PMS",
         "AndroidAppsData",
         "ProfessionalApps",
         "ProfessionalAppsData",
         "Schedules",
         "MyChoice",
         "Vsecure"
      };
      int allCloneItemsLen = allCloneItems.length;

      for (int i = 0; i < allCloneItemsLen; i++) {
         if (cloneItems.contains(allCloneItems[i])) {
            JSONObject cloneItem = new JSONObject();
            cloneItem.put("CloneItemName", allCloneItems[i]);
            cloneItem.put("URL", uri);
            cloneToServerDetails.put(cloneItem);
         }
      }

      return cloneToServerDetails;
   }

   private static String generateRoomSpecificSettingsJson(Devices tv, String clientip) {
      String clPath = "/Profile/Clone/0/";
      String clidentifier = getCloneIdentifier(CommonConstants.servletContextPath + clPath);
      String serverip = NetworkUtils.getServerIpFromSameRoute(clientip);
      boolean isSupportHttps = HttpUtils.isSupportHttpsByDevice(tv);
      int serverPort = HttpUtils.getCMNDPort(isSupportHttps);
      String strHttp = HttpUtils.getHttpString(isSupportHttps);
      return "{ \"Svc\" : \"WebListeningServices\", \"SvcVer\": \""
         + PlatformUtils.getIpCloneServiceVer(tv)
         + "\",\"Cookie\": "
         + JAPITUtils.getJapitRandomCookieValue()
         + ", \"CmdType\": \"Change\",\"Fun\":\"IPCloneService\", \"CommandDetails\" : { \"WebListeningServiceParameters\" : { \"TVUniqueID\" : \""
         + tv.getTvuniqueid()
         + "\"},\"IPCloneParameters\" :{\"CloneItemDownloadDetails\": [{\"CloneItemDetails\" : { \"CloneItemName\" : \"RoomSpecificSettings\",\"CloneItemVersionNo\" : \""
         + clidentifier
         + "\"},\"URL\" : \""
         + strHttp
         + serverip
         + ":"
         + serverPort
         + "/SmartInstall/Profile/Clone/"
         + clientip.replace(".", "")
         + "/RoomSpecificSettings.zip\"}]}}}";
   }

   public static String generateSimpleTvSettingJson(Devices tv, String cmndIP, String identifier) {
      boolean isSupportHttps = HttpUtils.isSupportHttpsByDevice(tv);
      String strHttp = HttpUtils.getHttpString(isSupportHttps);
      int serverPort = HttpUtils.getCMNDPort(isSupportHttps);
      return "{ \"Svc\" : \"WebListeningServices\", \"SvcVer\": \""
         + PlatformUtils.getIpCloneServiceVer(tv)
         + "\",\"Cookie\": "
         + JAPITUtils.getJapitRandomCookieValue()
         + ", \"CmdType\": \"Change\",\"Fun\":\"IPCloneService\", \"CommandDetails\" : { \"WebListeningServiceParameters\" : { \"TVUniqueID\" : \""
         + tv.getTvuniqueid()
         + "\"},\"IPCloneParameters\" :{\"CloneItemDownloadDetails\": [{\"CloneItemDetails\" : { \"CloneItemName\" : \"TVSettings\",\"CloneItemVersionNo\" : \""
         + identifier
         + "\"},\"URL\" : \""
         + strHttp
         + cmndIP
         + ":"
         + serverPort
         + "/SmartInstall/Profile/Clone/"
         + tv.getTvipaddress().replace(".", "")
         + "/TVSettings.zip\"}]}}}";
   }

   public static void cancelIpCloneServiceForTV(Devices tv) {
      try {
         String data = generateCancelCloneJapit(tv);
         JAPITUtils.sendJapitCommand(tv, data, 10000);
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }
   }

   public static boolean refreshTVCloneStatusAndCheckIsForceUpgradeReady(Devices tv) {
      boolean result = true;

      try {
         String data = generateBaseRequestIPCloneService(tv);
         JSONObject jsonObject = new JSONObject(JAPITUtils.sendJapitCommand(tv, data, 10000));
         if (jsonObject.has("Fun") && jsonObject.get("Fun").toString().equalsIgnoreCase("IPCloneService")) {
            JSONObject commandDetails = jsonObject.getJSONObject("CommandDetails");
            refreshTVCloneItemVersionToDb(commandDetails, tv);
            if (PlatformUtils.isSupportUpgradeStatusCheckBeforeForceUpgrade(tv.getType())) {
               String currentUpgradeStatus = commandDetails.optJSONObject("IPCloneParameters").getString("CurrentUpgradeStatus");
               result = "ReadyForUpgrade".equalsIgnoreCase(currentUpgradeStatus);
            }
         }
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }

      return result;
   }
}
