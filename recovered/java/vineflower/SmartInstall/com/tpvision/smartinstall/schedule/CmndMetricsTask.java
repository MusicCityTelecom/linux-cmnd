package com.tpvision.smartinstall.schedule;

import com.google.gson.Gson;
import com.tpvision.smartinstall.dao.core.CastAnalyticalData;
import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.PlayoutInfo;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.PlayoutInfoManager;
import com.tpvision.smartinstall.gateway.GatewayManager;
import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.servlet.LastRFConfig;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.ContentUtils;
import com.tpvision.smartinstall.util.IPUpgradeManager;
import com.tpvision.smartinstall.util.Log4j2Utils;
import com.tpvision.smartinstall.util.ProcessUtils;
import com.tpvision.smartinstall.util.TpvTimerTask;
import com.tpvision.smartinstall.util.Utils;
import java.io.File;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.Display;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.PhysicalMemory;

public class CmndMetricsTask extends TpvTimerTask {
   public static final String METRICS_LOG_PATH = CommonConstants.TOMCAT_WD + "/logs/metrics/";
   private static final Logger LOG = LoggerFactory.getLogger(CmndMetricsTask.class);
   private static final String UNKNOWN = "UnKnown";
   private static ThreadLocal<String> pmsTypeName = new ThreadLocal<String>() {
      public String initialValue() {
         return "";
      }
   };
   private static ThreadLocal<Boolean> isExpressCheckOut = new ThreadLocal<Boolean>() {
      public Boolean initialValue() {
         return false;
      }
   };
   private static final Map<String, String> mapPmsType = new HashMap<String, String>() {
      private static final long serialVersionUID = 1L;

      {
         this.put("impala PMS", "Impala PMS");
         this.put("tigerTMS 1.0.12.0", "TigerTMS 1.0.12.0");
         this.put("htngPMS", "HTNG PMS");
         this.put("oracleTMS 2.20.21", "FIAS 2.20.21");
         this.put("fiasServer", "FIAS 2.20.21 (Server)");
         this.put("Reception", "Reception");
         this.put("accor FOLS PMS", "Accor FOLS PMS");
         this.put("hop PMS", "HOP PMS");
         this.put("None", "CMND");
      }
   };

   @Override
   public void tryRun() {
      LOG.info("CmndMetricsTask start..");
      JSONObject sysJson = new JSONObject();
      this.loadServerHardWareInfo(sysJson);
      this.loadOSInfo(sysJson);
      this.loadCMNDInfo(sysJson);
      writeMetricsLog("sys", sysJson);

      for (Devices tv : JpaManager.getDevicesManager().loadAll()) {
         writeIptvInfoToMetricsLog(tv);
      }

      LOG.info("CmndMetricsTask end");
   }

   public static void writeIptvInfoToMetricsLog(Devices tv) {
      if (!tv.isRFDevice()) {
         JSONObject tvJson = new JSONObject();
         tvJson.put("device_id", DigestUtils.sha256Hex(tv.getTvserialnumber()));
         tvJson.put("tv_platform", tv.getType());
         tvJson.put("tv_model", tv.getTvmodelnumber());
         tvJson.put("current_power_status", tv.getPowerstatus());
         tvJson.put("current_firmware", tv.getTvFirmwareIdentifier());
         tvJson.put("current_software_upgrade_status", IPUpgradeManager.getUpgradeStatusByColor(tv.getFwColor()));
         tvJson.put("current_clone_upgrade_status", IPUpgradeManager.getUpgradeStatusByColor(tv.getCloneColor()));
         writeMetricsLog("ip", tvJson);
      }
   }

   public static void writeCastAnalyticMetrics(CastAnalyticalData castAnalyticalData) {
      JSONObject castAnalyticJson = new JSONObject();
      castAnalyticJson.put("deviceId", castAnalyticalData.getDeviceId());
      castAnalyticJson.put("start", castAnalyticalData.getStart());
      castAnalyticJson.put("end", castAnalyticalData.getEnd());
      castAnalyticJson.put("sessionType", castAnalyticalData.getSessionType());
      castAnalyticJson.put("applicationName", castAnalyticalData.getApplicationName());
      writeMetricsLog("cast", castAnalyticJson);
   }

   public static void setPmsTypeName(String name) {
      pmsTypeName.set(name);
   }

   public static void resetPmsTypeName() {
      pmsTypeName.set("");
   }

   private static String getPmsTypeName() {
      return pmsTypeName.get();
   }

   public static void setExpressCheckOut(Boolean flag) {
      isExpressCheckOut.set(flag);
   }

   public static void resetExpressCheckOut() {
      isExpressCheckOut.set(false);
   }

   private static String getMetricsPmsType() {
      String pmsType = PmsUtils.getTmsType();

      for (String key : mapPmsType.keySet()) {
         if (pmsType.equalsIgnoreCase(key)) {
            pmsType = mapPmsType.get(key);
            break;
         }
      }

      return pmsType;
   }

   public static void writeIPTVMetricsLog(Devices tv, JSONObject jsonData) {
      if ("PMSService".equalsIgnoreCase(jsonData.optString("Fun"))) {
         JSONObject jsonBuffer = jsonData.optJSONObject("CommandDetails");
         if (jsonBuffer != null) {
            jsonBuffer = jsonBuffer.optJSONObject("PMSParameters");
            if (jsonBuffer != null) {
               String action = jsonBuffer.optString("Action");
               if ("".equalsIgnoreCase(action)) {
                  if (!jsonBuffer.isNull("GuestBill")) {
                     writePMSInfoToMetricsLog(tv, "billrequest");
                  }
               } else if ("NewGuestMessage".equalsIgnoreCase(action)) {
                  writePMSInfoToMetricsLog(tv, "message");
               } else if (Arrays.asList("CheckIn", "CheckOut").contains(action)) {
                  writePMSInfoToMetricsLog(tv, action.toLowerCase());
               } else if ("UpdateGuestPreferences".equalsIgnoreCase(action)) {
                  writePMSInfoToMetricsLog(tv, "languagechange");
               }
            }
         }
      }
   }

   public static void writeRFTVMetricsLog(PmsUtils.PmsAction pmsAction, String roomId) {
      for (Devices tv : PmsUtils.getTVsForRoom(roomId)) {
         if (tv.isRFDevice()) {
            writePMSInfoToMetricsLog(tv, pmsAction.toString().toLowerCase());
         }
      }
   }

   public static void writePMSInfoToMetricsLog(Devices tv, String action) {
      String pmsType = "".equalsIgnoreCase(getPmsTypeName()) ? getMetricsPmsType() : getPmsTypeName();
      JSONObject pmsJson = new JSONObject();
      pmsJson.put("device_id", DigestUtils.sha256Hex(tv.getTvserialnumber()));
      pmsJson.put("tv_platform", tv.getType());
      if ("checkout".equalsIgnoreCase(action) && isExpressCheckOut.get()) {
         pmsJson.put("action", "expresscheckout");
      } else {
         pmsJson.put("action", action);
      }

      pmsJson.put("room_id", tv.getTvroomid());
      pmsJson.put("pms_type", pmsType);
      writeMetricsLog("pms", pmsJson);
   }

   public static void loadRFData() {
      PlayoutInfoManager playoutInfoManager = JpaManager.getPlayoutInfoManager();
      List<PlayoutInfo> playoutInfos = playoutInfoManager.loadAll();
      String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
      String outputMethod = LastRFConfig.loadLastConfig().outputConfig.output;

      for (PlayoutInfo info : playoutInfos) {
         JSONObject playoutJson = new JSONObject();
         playoutJson.put("playout_id", info.getPlayoutId());
         playoutJson.put("tv_platform", info.getPlatform());
         playoutJson.put("type", info.getType());
         playoutJson.put("version", info.getVersion());
         playoutJson.put("playout_start", currentTime);
         playoutJson.put("output", outputMethod);
         writeMetricsLog("rf", playoutJson);
      }
   }

   private void loadServerHardWareInfo(JSONObject sysJson) {
      HardwareAbstractionLayer hal = new SystemInfo().getHardware();
      CentralProcessor centralProcessor = hal.getProcessor();
      String freqStr = new DecimalFormat("#.00").format(new BigDecimal(centralProcessor.getMaxFreq()).divide(new BigDecimal(1000000000))) + " GHz";
      String cpuInfo = centralProcessor.getProcessorIdentifier().getName().trim() + " " + freqStr;
      GlobalMemory memory = hal.getMemory();
      long totalMemByte = 0L;

      for (PhysicalMemory mem : memory.getPhysicalMemory()) {
         totalMemByte += mem.getCapacity();
      }

      String ramInfo = totalMemByte / 1073741824L + ".0 GB";
      String resolution = "UnKnown";

      label29:
      for (Display display : hal.getDisplays()) {
         String[] infoArr = display.toString().split("\n");
         String[] var15 = infoArr;
         int var16 = var15.length;
         int var17 = 0;

         while (true) {
            if (var17 < var16) {
               String line = var15[var17];
               int idx = line.indexOf("Active Pixels");
               if (idx == -1) {
                  var17++;
                  continue;
               }

               resolution = line.substring(idx).replace("Active Pixels", "").trim();
            }

            if (!resolution.equalsIgnoreCase("UnKnown")) {
               break label29;
            }
            break;
         }
      }

      LOG.info("[HW] CPU={}; RAM={}; Resolution={};", cpuInfo, ramInfo, resolution);
      sysJson.put("cpu", cpuInfo);
      sysJson.put("ram", ramInfo);
      sysJson.put("resolution", resolution);
   }

   private void loadOSInfo(JSONObject sysJson) {
      String osEdition = "UnKnown";
      String osVersion = "UnKnown";
      String osBuild = "UnKnown";
      String systemModel = "UnKnown";
      ProcessUtils.ProcessOutput processOutput = new ProcessUtils.ProcessOutput();
      if (ProcessUtils.execCommondWithReturn("C:/", "systeminfo", processOutput)) {
         String value = processOutput.getValue();
         String[] dataArr = value.split("\r\n");

         for (String str : dataArr) {
            if (str.startsWith("OS Name:")) {
               osEdition = str.replace("OS Name:", "").trim();
            } else if (str.startsWith("OS Version:")) {
               String versionStr = str.replace("OS Version:", "").trim();
               osVersion = versionStr.substring(0, versionStr.indexOf(32));
               osBuild = versionStr.substring(versionStr.indexOf("Build")).replace("Build", "").trim();
            } else if (str.startsWith("System Model:")) {
               systemModel = str.replace("System Model:", "").trim();
            }

            if (!"UnKnown".equalsIgnoreCase(osEdition) && !"UnKnown".equalsIgnoreCase(osVersion) && !"UnKnown".equalsIgnoreCase(systemModel)) {
               break;
            }
         }
      }

      LOG.info("[OS] Edition={}; Version={}; Build={};", osEdition, osVersion, osBuild);
      sysJson.put("os_edition", osEdition);
      sysJson.put("os_version", osVersion);
      sysJson.put("os_build", osBuild);
      sysJson.put("system_model", systemModel);
   }

   private void loadCMNDInfo(JSONObject sysJson) {
      String cmndVersion = Utils.getCMNDMajorVersion();
      String dekTecInfo = "NotDetected";
      GatewayManager gatewayManager = GatewayManager.getInstance();
      String cardsStr = gatewayManager.getOutputPorts();
      if (null != cardsStr) {
         LastRFConfig.CardInfo[] cardInfoList = new Gson().fromJson(cardsStr, LastRFConfig.CardInfo[].class);
         if (null != cardInfoList && cardInfoList.length > 0) {
            dekTecInfo = String.valueOf(cardInfoList[0].type);
         }
      }

      String contentsCount = "UnKnown";
      String contentJSON = ContentUtils.getContentList();
      if (StringUtils.isNotBlank(contentJSON)) {
         JSONArray array = new JSONArray(contentJSON);
         contentsCount = String.valueOf(array.length());
      }

      LOG.info("[INFO] CMND={}; DekTec={}; Contents={};", cmndVersion, dekTecInfo, contentsCount);
      sysJson.put("cmnd_version", cmndVersion);
      sysJson.put("dektec_card", dekTecInfo);
      sysJson.put("pms_type", PmsUtils.getPmsTypeName());
   }

   private static void writeMetricsLog(String type, JSONObject metricDetails) {
      String logFileName = "metrics." + type + "." + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".log";
      File logFile = new File(METRICS_LOG_PATH + logFileName);

      try {
         metricDetails.put("server_id", Log4j2Utils.FILE_BEAT_SERVER_ID);
         FileUtils.writeStringToFile(
            logFile, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS").format(new Date()) + " " + metricDetails.toString() + "\r\n", StandardCharsets.UTF_8, true
         );
      } catch (Exception e) {
         LOG.warn(e.getMessage(), e);
      }
   }
}
