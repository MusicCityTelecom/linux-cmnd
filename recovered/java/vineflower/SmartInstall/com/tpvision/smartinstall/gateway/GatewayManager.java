package com.tpvision.smartinstall.gateway;

import com.google.gson.Gson;
import com.tpvision.smartinstall.dao.core.PlayoutInfo;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.PlayoutInfoManager;
import com.tpvision.smartinstall.servlet.BaseHttpServlet;
import com.tpvision.smartinstall.servlet.LastRFConfig;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.Configs;
import com.tpvision.smartinstall.util.NetworkUtils;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.ProcessUtils;
import com.tpvision.smartinstall.util.RFCommandState;
import com.tpvision.smartinstall.util.TpvFileUtils;
import com.tpvision.smartinstall.util.Utils;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.exec.environment.EnvironmentUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GatewayManager {
   public static final String MGATE_PROCESS = "MGate.exe";
   public static final String MGATE_PROCESS_LINUX = "MGate";
   public static final String GATEWAY_PROCESS = "Gateway.exe";
   public static final String SUCCEEDED = "succeeded";
   public static final String ERROR = "error";
   private static final String CLONES = "Clones";
   private static final String STREAMS = "Streams";
   private static final String TS_RATE = "TSRate";
   private static final String COMMAND = "command";
   private static final String DATA = "data";
   private static final String VERSION = "version";
   private static final String TEXT_CODE = "code";
   private static final String STATUS = "status";
   private static final int MAX_ERRORS = 3;
   private static final int MIN_RF_FILESIZE = -1073741824;
   private static final int CONNECTION_TIMEOUT = 10000;
   private static final int READ_TIMEOUT = 10000;
   private static final Logger LOG = LoggerFactory.getLogger(GatewayManager.class);
   private String mgatePath = null;
   private String cmndUrl = Configs.getServerUrl(null);
   private int mgatePort = 10088;
   private GatewayManager.GatewayState state;
   private String errorMessage = "";
   private int lastSince;
   private String lastCardList;
   private boolean isNeedUpdateStatus;
   private boolean itemUpdatePooling = false;
   private boolean isPlayoutWaiting;
   private volatile boolean isUsingOldGateway;
   private volatile boolean isOldGatewayRunning;
   private volatile boolean isMGateRunning;
   private boolean isUsingLocalMGate;
   private volatile boolean isMGateConnected;
   private Map<String, String> commandQueue = new HashMap<>();
   private int errorCounter;
   private String mgateHost;
   private boolean isCheckingPort;
   private int startupCounter = 0;
   private int newMGatePort;

   private GatewayManager.GatewayState statusToGatewayState(String status) {
      for (GatewayManager.GatewayState tmpstate : GatewayManager.GatewayState.values()) {
         if (tmpstate.value.equalsIgnoreCase(status)) {
            return tmpstate;
         }
      }

      return GatewayManager.GatewayState.UNKOWN;
   }

   public boolean isItemUpdatePooling() {
      return this.itemUpdatePooling;
   }

   public void setItemUpdatePooling(boolean itemUpdatePooling) {
      this.itemUpdatePooling = itemUpdatePooling;
   }

   public boolean isPlayoutWaiting() {
      return this.isPlayoutWaiting;
   }

   public void setPlayoutWaiting(boolean isPlayoutWating) {
      this.isPlayoutWaiting = isPlayoutWating;
   }

   public GatewayManager.GatewayState getState() {
      return this.state;
   }

   public static GatewayManager getInstance() {
      return GatewayManager.SingletonHolder.INSTANCE;
   }

   private GatewayManager() {
      this.teardownGateway();
      this.setState(GatewayManager.GatewayState.INITIATING);
      this.mgatePath = CommonConstants.MGATE_URL;

      try {
         URL mgateUrl = new URL(this.mgatePath);
         this.mgateHost = mgateUrl.getHost();
         this.mgatePort = mgateUrl.getPort();
      } catch (MalformedURLException e) {
         LOG.error(e.getMessage(), e);
      }

      this.newMGatePort = this.mgatePort;
      this.isUsingLocalMGate = this.mgatePath.contains("localhost") || this.mgatePath.contains("127.0.0.1");
      if (!this.isUsingLocalMGate) {
         this.cmndUrl = Configs.getServerUrl(this.mgateHost);
      }

      this.isUsingOldGateway = false;
      this.isMGateRunning = false;
      this.startupMGate();
   }

   public boolean isPlaying() {
      return this.state == GatewayManager.GatewayState.PLAYING
         || this.state == GatewayManager.GatewayState.GENERATING
         || this.isOldGatewayRunning() && this.isTSgenerated();
   }

   public boolean isPlayoutQueueEmpty() {
      List<PlayoutInfo> playoutInfos = JpaManager.getPlayoutInfoManager().loadAll();
      return playoutInfos.isEmpty();
   }

   public static Boolean isDekTecCardAvailableByGateway() {
      boolean installed = true;
      if (!ProcessUtils.isProcessRunning("Gateway.exe")) {
         String cmd = CommonConstants.RF_PLAY_BACK_EXE_NAME + " BUILD";
         Runtime runtime = Runtime.getRuntime();
         Process process = null;

         try {
            process = runtime.exec(cmd);
         } catch (IOException e1) {
            LOG.error(e1.getMessage(), e1);
            return false;
         }

         try (
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
         ) {
            String line;
            while ((line = br.readLine()) != null) {
               if (line.indexOf("DekTecManager: Error - device scan failed") > -1) {
                  installed = false;
                  break;
               }
            }

            ProcessUtils.killProcess("Gateway.exe");
         } catch (Exception e) {
            LOG.error(e.getMessage(), e);
         }
      }

      LOG.info("DekTec available :{}", installed);
      return installed;
   }

   public boolean isDektecCardInstalled() {
      String cardsStr = this.getOutputPorts();
      if (null != cardsStr) {
         LastRFConfig.CardInfo[] cardInfoList = new Gson().fromJson(cardsStr, LastRFConfig.CardInfo[].class);
         if (null != cardInfoList && cardInfoList.length > 0) {
            LastRFConfig lastRFConfig = LastRFConfig.loadLastConfig();
            lastRFConfig.cards.clear();
            lastRFConfig.cards.addAll(Arrays.asList(cardInfoList));
            lastRFConfig.save();
            return true;
         }
      }

      return false;
   }

   public boolean checkMGateRunning() {
      boolean mgateRunning = false;
      if (Utils.isLinuxOS() || !this.isUsingLocalMGate) {
         mgateRunning = true;
      } else if (!Utils.isLinuxOS()) {
         mgateRunning = ProcessUtils.isProcessRunning("MGate.exe");
      }

      return mgateRunning;
   }

   public boolean checkMGateConnected() {
      if (NetworkUtils.isListeningOnPort(this.mgateHost, this.newMGatePort, 500)) {
         this.sendCommand("status");
      } else {
         this.isMGateConnected = false;
      }

      return this.isMGateConnected;
   }

   public void startupMGate() {
      if (this.isUsingOldGateway()) {
         LOG.info("using old gateway,stop startup mgate");
      } else {
         LOG.info("Startup MGate");
         this.teardownGateway();
         if (!this.isUsingLocalMGate) {
            LOG.info("using remote MGate:{}", this.mgatePath);
         } else {
            if (!this.checkMGateRunning()) {
               while (NetworkUtils.isListeningOnPort(this.mgateHost, this.newMGatePort, 500)) {
                  this.newMGatePort = this.mgatePort + this.startupCounter++;
               }

               LOG.info("startup MGate using port:{}", this.newMGatePort);
               ProcessUtils.startProcess(CommonConstants.MGATEWAY_EXE_PATH + " " + this.newMGatePort, CommonConstants.MGATEWAY_WORK_PATH);
            }
         }
      }
   }

   public void teardownMGate() {
      LOG.info("teardown MGate");
      if (!Utils.isLinuxOS()) {
         ProcessUtils.killProcess("MGate.exe");
      }
   }

   public void teardownGateway() {
      LOG.info("teardown Gateway.exe");
      if (!Utils.isLinuxOS()) {
         ProcessUtils.killProcess("Gateway.exe");
      }
   }

   public String getVersion() {
      JSONObject jsonVersion = this.sendCommand("getVersion");
      return null != jsonVersion && jsonVersion.has("data") ? jsonVersion.getJSONObject("data").getString("version") : "Unknown";
   }

   public String getGatewayVersion() {
      String version = TpvFileUtils.getVersion(CommonConstants.RF_PLAY_BACK_EXE_NAME);
      if (null == version) {
         version = "Unknown";
      }

      return version;
   }

   public JSONObject getInterfaces() {
      JSONObject jsonIf = this.sendCommand("getInterfaces");
      return null != jsonIf && jsonIf.has("data") ? jsonIf.getJSONObject("data") : new JSONObject("{\"interfaces\":[]}");
   }

   private void checkIpSettings(LastRFConfig lastRFConfig) throws IOException {
      boolean found = false;
      JSONObject interfaces = this.getInterfaces();
      if (interfaces != null && interfaces.has("interfaces")) {
         JSONArray interfacesArray = interfaces.getJSONArray("interfaces");

         for (int i = 0; i < interfacesArray.length(); i++) {
            JSONObject jsObj = interfacesArray.getJSONObject(i);
            if (jsObj.has("localInterface") && jsObj.getString("localInterface").equalsIgnoreCase(lastRFConfig.outputConfig.ipSettings.localInterface)) {
               found = true;
               break;
            }
         }
      }

      if (!found) {
         throw new IOException("configured localInterface not found in MGate");
      }
   }

   private JSONObject getIPSettings(LastRFConfig lastRFConfig) {
      String jsipsetting = new Gson().toJson(lastRFConfig.outputConfig.ipSettings, LastRFConfig.IPSettings.class);
      JSONObject ipsettings = new JSONObject(jsipsetting);
      ipsettings.put("TSRate", lastRFConfig.outputConfig.tsRate);
      return ipsettings;
   }

   public JSONObject getRFSettings(LastRFConfig lastRFConfig) {
      JSONObject settingObject = new JSONObject();
      switch (lastRFConfig.outputConfig.rfMode) {
         case "DVBT":
            settingObject.put("ModulationBandwidth", lastRFConfig.getMapValue(LastRFConfig.ConfigType.BANDWIDTH, lastRFConfig.bandwidth));
            settingObject.put("Constellation", lastRFConfig.getMapValue(LastRFConfig.ConfigType.CONSTELLATION, lastRFConfig.modulation));
            settingObject.put("GuardInterval", lastRFConfig.getMapValue(LastRFConfig.ConfigType.GUARDINTERVAL, lastRFConfig.guardInterval));
            settingObject.put("TransmissionMode", lastRFConfig.getMapValue(LastRFConfig.ConfigType.TRANSMISSIONMODE, lastRFConfig.transmissionMode));
            settingObject.put("Coderate", lastRFConfig.getMapValue(LastRFConfig.ConfigType.CODERATE, lastRFConfig.coderate));
            break;
         case "ATSC":
            settingObject.put("VSBConstellation", lastRFConfig.outputConfig.vsb);
         case "QAM256":
            break;
         default:
            LOG.info("not supported RFMode:{}", lastRFConfig.outputConfig.rfMode);
      }

      settingObject.put("RFMode", lastRFConfig.outputConfig.rfMode);
      settingObject.put("OutputLevel", Float.parseFloat(lastRFConfig.outputLevel));
      long freq = (long)(Float.parseFloat(lastRFConfig.frequency) * 100.0F) * 10000L;
      settingObject.put("Frequency", freq);
      settingObject.put("DekTecCardPortIndex", lastRFConfig.cardIndex);
      return settingObject;
   }

   public String getDefaultOutputPath() {
      File output = new File(CommonConstants.RF_FILE_OUTPUT_PATH);
      if (!output.exists()) {
         output.mkdirs();
      }

      return CommonConstants.RF_FILE_OUTPUT_PATH;
   }

   private JSONObject getFileSettings(LastRFConfig lastRFConfig) throws IOException {
      TpvFileUtils.checkDiskSpaceFull(-1073741824L);
      JSONObject filesettings = new JSONObject();
      filesettings.put("TSRate", lastRFConfig.outputConfig.tsRate);
      filesettings.put("fileName", lastRFConfig.outputConfig.fileName);
      File output = new File(FilenameUtils.getFullPath(lastRFConfig.outputConfig.fileName));
      if (!output.exists()) {
         output.mkdirs();
      }

      return filesettings;
   }

   private int getResponseCode(JSONObject response) {
      return null != response && response.has("status") && response.get("status").equals("error") && response.has("code") ? response.getInt("code") : 0;
   }

   public String getOutputFile() {
      JSONObject resObj = this.sendCommand("getOutputFile");
      return null != resObj && resObj.has("data") ? resObj.getJSONObject("data").getString("file") : null;
   }

   public int setPlayoutSettings(LastRFConfig lastRFConfig) throws IOException {
      String[] validOutputs = new String[]{"RF", "File", "IP", "ASI"};
      String output = lastRFConfig.outputConfig.output;
      int res = 0;

      try {
         if (!Arrays.asList(validOutputs).contains(output)) {
            LOG.error("output is invalid,output={}", output);
            throw new IOException("output is invalid,output=" + output);
         }

         JSONObject specificSettings = null;
         switch (lastRFConfig.outputConfig.output) {
            case "IP":
               specificSettings = this.getIPSettings(lastRFConfig);
               break;
            case "File":
               specificSettings = this.getFileSettings(lastRFConfig);
               break;
            case "RF":
               if (!this.isDektecCardInstalled()) {
                  throw new IOException("Dektec card cannot be found");
               }

               specificSettings = this.getRFSettings(lastRFConfig);
               break;
            case "ASI":
               specificSettings = this.getASISettings(lastRFConfig);
               break;
            default:
               String msg = "not support output method:" + lastRFConfig.outputConfig.output;
               LOG.error(msg);
               throw new IOException(msg);
         }

         JSONObject playoutSettings = new JSONObject();
         playoutSettings.put("method", lastRFConfig.outputConfig.output);
         playoutSettings.put("SpecificSettings", specificSettings);
         JSONObject jsonObject = new JSONObject();
         jsonObject.put("command", "SetOutputSettings");
         jsonObject.put("PlayoutSettings", playoutSettings);
         JSONObject response = this.sendRequestToGateway(jsonObject.toString());
         return this.getResponseCode(response);
      } catch (IOException e) {
         this.setErrorState(e.getMessage());
         return res;
      }
   }

   public void setErrorState(String message) {
      this.setState(GatewayManager.GatewayState.ERROR);
      this.setErrorMessage(message);
   }

   private JSONObject getASISettings(LastRFConfig lastRFConfig) {
      JSONObject asisettings = new JSONObject();
      asisettings.put("TSRate", lastRFConfig.outputConfig.tsRate);
      asisettings.put("ASIPortIndex", lastRFConfig.outputConfig.ASIPortIndex);
      return asisettings;
   }

   private String pathToUriPath(String path) {
      if (this.isUsingLocalMGate) {
         return Paths.get(path).toUri().toString();
      }

      path = path.replace(CommonConstants.servletContextPath, "");
      return this.cmndUrl + path;
   }

   private String getCloneItemName(String fileName) {
      switch (fileName) {
         case "HTVCfg":
            return "HTVCfg.xml";
         case "Firmware":
            return "MainFirmware";
         default:
            return fileName;
      }
   }

   private JSONObject generateInfoItem(PlayoutInfo info) {
      String zipFullPath = PlayoutUtils.getRFZipFullPath(info);
      String location = this.pathToUriPath(zipFullPath);
      JSONObject item = new JSONObject();
      String itemName = this.getCloneItemName(info.getType());
      item.put("name", itemName);
      String version = info.getVersion();
      item.put("version", version);
      item.put("location", location);
      return item;
   }

   public List<JSONObject> generateRoomItem(String room, List<PlayoutInfo> infos) {
      Map<String, JSONArray> platformItemMap = new HashMap<>();

      for (PlayoutInfo info : infos) {
         String clusterName = PlatformUtils.getRootFolderName(info.getPlatform());
         JSONArray items = platformItemMap.get(clusterName);
         if (items == null) {
            items = new JSONArray();
            platformItemMap.put(clusterName, items);
         }

         items.put(this.generateInfoItem(info));
      }

      List<JSONObject> roomItemList = new ArrayList<>();

      for (Entry<String, JSONArray> entry : platformItemMap.entrySet()) {
         JSONArray items = entry.getValue();
         if (items != null) {
            JSONArray jaClusterName = new JSONArray();
            jaClusterName.put(entry.getKey());
            JSONObject clusterName = new JSONObject();
            clusterName.put("TVClusterName", jaClusterName);
            JSONObject roomItem = new JSONObject();
            roomItem.put("TVSerialNumber", "");
            roomItem.put("RoomID", this.processRoomId(room));
            roomItem.put("ApplyTo", clusterName);
            roomItem.put("CloneItems", items);
            roomItemList.add(roomItem);
         }
      }

      return roomItemList;
   }

   public String processRoomId(String roomId) {
      return roomId.contains("-") ? roomId.replace(" - ", " to ").replace("-", " to ") : roomId;
   }

   public boolean generatePlayout(JSONArray roomItems, JSONArray streamItems) {
      JSONObject cmdObj = new JSONObject();
      cmdObj.put("Rooms", roomItems);
      if (streamItems != null) {
         cmdObj.put("Streams", streamItems);
      } else {
         cmdObj.put("Streams", new JSONArray());
      }

      cmdObj.put("command", "generate");
      cmdObj.toString();
      this.commandQueue.remove("Clones");
      JSONObject response = this.sendRequestToGateway(cmdObj.toString());
      if (null != response) {
         LOG.info(response.toString());
         boolean succ = response.has("status") && response.getString("status").equalsIgnoreCase("succeeded");
         if (succ) {
            this.isNeedUpdateStatus = true;
            this.setItemUpdatePooling(true);
            return true;
         }
      }

      this.commandQueue.put("Clones", cmdObj.toString());
      return false;
   }

   public void generateStreams(PlayoutInfo[] infos) {
      JSONArray jsStreams = new JSONArray();

      for (PlayoutInfo info : infos) {
         jsStreams.put(this.getStreamJson(info.getName()));
      }

      JSONObject cmd = new JSONObject();
      cmd.put("command", "SetStreamList");
      cmd.put("Streams", jsStreams);
      this.commandQueue.remove("Streams");
      JSONObject response = this.sendRequestToGateway(cmd.toString());
      int code = this.getResponseCode(response);
      if (code == 5) {
         LOG.error("MGate is busy,resend setstreamlist later");
         this.commandQueue.put("Streams", cmd.toString());
      } else {
         this.isNeedUpdateStatus = true;
         this.setItemUpdatePooling(true);
      }
   }

   public JSONObject getLiveStreamJson(PlayoutInfo info) {
      String[] urlfields = info.getName().split(":");
      if (urlfields.length < 2) {
         LOG.error("live stream url error,{}", info.getName());
         return null;
      } else {
         JSONObject spts = new JSONObject();
         spts.put("localNIC", info.getSource());
         spts.put("mcastAddress", urlfields[0]);
         spts.put("mcastPort", Integer.parseInt(urlfields[1]));
         return spts;
      }
   }

   public JSONObject getStreamJson(String fileName) {
      JSONObject spts = new JSONObject();
      String fullName = CommonConstants.UPLOADED_AVSTREAM_LOCATION + fileName;
      String outName = PlayoutUtils.getRFProfilePath() + fileName;
      File outFile = new File(outName);
      if (!outFile.exists()) {
         try {
            FileUtils.copyFile(new File(fullName), outFile);
         } catch (IOException e) {
            LOG.error(e.getMessage(), e);
         }
      }

      spts.put("name", fileName);
      spts.put("version", String.valueOf(new File(fullName).lastModified()));
      spts.put("location", this.pathToUriPath(outName));
      return spts;
   }

   public int addStream(String fileName) {
      JSONObject spts = new JSONObject();
      spts.put("name", fileName);
      spts.put("version", String.valueOf(System.currentTimeMillis()));
      spts.put("location", this.pathToUriPath(CommonConstants.UPLOADED_AVSTREAM_LOCATION + fileName));
      JSONObject cmd = new JSONObject();
      cmd.put("command", "AddStream");
      cmd.put("Stream", spts);
      JSONObject response = this.sendRequestToGateway(cmd.toString());
      int code = this.getResponseCode(response);
      if (code > 0) {
         this.isNeedUpdateStatus = true;
         this.setItemUpdatePooling(true);
      }

      return code;
   }

   public int removeStream(int sid) {
      if (sid < 0) {
         LOG.error("invalid SID:{}", sid);
         return 0;
      } else {
         JSONObject jsObj = new JSONObject();
         jsObj.put("command", "RemoveStream");
         JSONObject jsStream = new JSONObject();
         jsStream.put("SID", sid);
         jsObj.put("Stream", jsStream);
         JSONObject response = this.sendRequestToGateway(jsObj.toString());
         return this.getResponseCode(response);
      }
   }

   public void stopPlayout() {
      LOG.info("stop playout");
      if (this.isUsingOldGateway() && this.isOldGatewayRunning()) {
         ProcessUtils.killProcess("Gateway.exe");
      } else {
         this.sendCommand("stop");
         this.setItemUpdatePooling(true);
      }
   }

   public void resumePlayout() {
      this.sendCommand("resume");
   }

   public void resetStatus() {
      this.setState(GatewayManager.GatewayState.IDLE);
      this.resetPlayoutInfoStatus();
   }

   public List<ResponsePlayInfo.AVService> getAVServices() {
      JSONObject resObj = this.sendCommand("getPlayInfo");
      ResponsePlayInfo playinfo = ResponsePlayInfo.fromJson(resObj.toString());
      return playinfo != null ? playinfo.data.services.AVServices : new ArrayList<>();
   }

   public void requestStatus() throws IOException {
      JSONObject response = this.sendCommand("status");
      if (null == response) {
         LOG.error("response null");
         throw new IOException("request status failed");
      }

      try {
         ResponseStatus responseStatus = ResponseStatus.fromJson(response.toString());
         if (null != responseStatus) {
            String status = responseStatus.getStatus();
            GatewayManager.GatewayState newstate = this.statusToGatewayState(status);
            int since = 0;
            if (response.has("since")) {
               since = response.getInt("since");
            }

            if (newstate != this.getState() || since <= this.lastSince) {
               this.isNeedUpdateStatus = true;
            }

            if (GatewayManager.GatewayState.ERROR == this.getState()) {
               LOG.info("playout status is error,not update status");
               return;
            }

            this.setState(newstate);
            this.setErrorMessage(responseStatus.getErrorMessage());
            if (this.isNeedUpdateStatus && this.state != GatewayManager.GatewayState.IDLE) {
               this.handlePlayoutItemStatus(responseStatus.getCloneItems());
               this.handleStreamItemStatus(responseStatus.getStreams());
               this.lastSince = since;
               this.setItemUpdatePooling(true);
               this.isNeedUpdateStatus = this.isNeedUpdateByCode(responseStatus);
            }

            if (this.commandQueue.size() > 0
               && (
                  newstate == GatewayManager.GatewayState.PLAYING
                     || newstate == GatewayManager.GatewayState.ERROR
                     || newstate == GatewayManager.GatewayState.IDLE
               )) {
               LOG.info("resend command");

               for (Entry<String, String> entry : this.commandQueue.entrySet()) {
                  JSONObject jsonObject = this.sendRequestToGateway(entry.getValue());
                  if (jsonObject != null && this.getResponseCode(jsonObject) == 0) {
                     this.commandQueue.remove(entry.getKey());
                     this.isNeedUpdateStatus = true;
                     this.setItemUpdatePooling(true);
                  }
               }
            }
         }
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         throw new IOException(e.getMessage());
      }
   }

   private boolean isNeedUpdateByCode(ResponseStatus resStatus) {
      if (null == resStatus) {
         return true;
      }

      for (ResponseStatus.CloneItem cloneItem : resStatus.CloneItems) {
         if (Integer.parseInt(cloneItem.code) < 4) {
            return true;
         }
      }

      for (ResponseStatus.StreamItem stream : resStatus.Streams) {
         if (Integer.parseInt(stream.code) < 4) {
            return true;
         }
      }

      return false;
   }

   public void setState(GatewayManager.GatewayState state) {
      if (this.state != state) {
         this.isNeedUpdateStatus = true;
      }

      this.state = state;
   }

   private void handlePlayoutItemStatus(List<ResponseStatus.CloneItem> list) {
      if (null != list && !list.isEmpty()) {
         for (ResponseStatus.CloneItem cloneitem : list) {
            this.updatePlayoutInfoStatus(cloneitem.name, Integer.parseInt(cloneitem.code));
         }
      }
   }

   private void handleStreamItemStatus(List<ResponseStatus.StreamItem> list) {
      for (ResponseStatus.StreamItem stream : list) {
         PlayoutInfoManager pim = JpaManager.getPlayoutInfoManager();
         String whereName = null;
         if (stream.name == null) {
            whereName = stream.MCastAddress + ":" + stream.MCastPort;
         } else {
            whereName = stream.name;
         }

         List<PlayoutInfo> playoutInfos = pim.findPlayoutInfoByName(whereName);
         if (!playoutInfos.isEmpty()) {
            PlayoutInfo playoutInfo = playoutInfos.get(0);
            playoutInfo.setStatus(Integer.parseInt(stream.code));
            playoutInfo.setConnectId(stream.SID);
            playoutInfo.setVersion(stream.version);
            pim.save(playoutInfo);
         }
      }
   }

   private void resetPlayoutInfoStatus() {
      JpaManager.getPlayoutInfoManager().resetStatus();
      this.setItemUpdatePooling(true);
   }

   private void updatePlayoutInfoStatus(String name, int status) {
      if (this.state != GatewayManager.GatewayState.IDLE || status != 4) {
         File file = new File(name);
         JpaManager.getPlayoutInfoManager().updateStatusByFilePath(file.getName(), status);
      }
   }

   public String getOutputPorts() {
      if (this.isUsingOldGateway()) {
         return this.lastCardList;
      }

      if (this.lastCardList == null) {
         LOG.info("wait for getting card info, max 30s");
         int waitSeconds = 300;

         while (!this.isMGateConnected && waitSeconds >= 0) {
            try {
               Thread.sleep(100L);
               waitSeconds--;
            } catch (InterruptedException e) {
               LOG.error(e.getMessage(), e);
            }
         }

         if (waitSeconds <= 0) {
            LOG.error("wait for mgate startup timeout");
         }
      }

      JSONObject response = this.sendCommand("getOutputPorts");
      if (null != response && response.has("data")) {
         JSONObject data = (JSONObject)response.get("data");
         this.lastCardList = data.getJSONArray("cards").toString();
         LOG.info("cards={}", this.lastCardList);
         return this.lastCardList;
      } else {
         return null;
      }
   }

   public JSONObject sendCommand(String command) {
      JSONObject jsonCmd = new JSONObject();
      jsonCmd.put("command", command);
      return this.sendRequestToGateway(jsonCmd.toString());
   }

   public String getMGateUrl() {
      return String.format(Locale.ENGLISH, "http://%s:%d/", this.mgateHost, this.newMGatePort);
   }

   private JSONObject sendRequestToGateway(String requestStr) {
      if (this.isUsingOldGateway()) {
         LOG.info("using old gateway, not send MGate command");
         return null;
      }

      boolean isStatus = requestStr.contains("status");
      if (!isStatus) {
         LOG.info(" SI >>>>>>>> GW:{}", requestStr);
         if (!this.isMGateConnected) {
            LOG.error("MGate is not connected");
            return null;
         }
      }

      String responseData = requestStr;

      try {
         URL url = new URL(this.getMGateUrl());
         HttpURLConnection connection = (HttpURLConnection)url.openConnection();
         connection.setConnectTimeout(10000);
         connection.setReadTimeout(10000);
         connection.setRequestMethod("POST");
         connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
         connection.setRequestProperty("Content-Length", "" + Integer.toString(responseData.getBytes().length));
         connection.setRequestProperty("Content-Language", "en-US");
         connection.setUseCaches(false);
         connection.setDoInput(true);
         connection.setDoOutput(true);

         try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            wr.writeBytes(responseData);
            wr.flush();
         }

         if (connection.getResponseCode() != 200) {
            throw new IOException("Connection Failed");
         }

         String resData = null;

         try (
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
         ) {
            StringBuilder res = new StringBuilder();

            String line;
            while ((line = rd.readLine()) != null) {
               res.append(line);
            }

            resData = res.toString();
         }

         if (!isStatus) {
            LOG.info(" GW >>>>>>>> SI: {}", resData);
         }

         JSONObject jsonObject = new JSONObject(resData);
         int errorCode = this.getResponseCode(jsonObject);
         if (errorCode > 0) {
            LOG.error("gateway return error,code={}", errorCode);
         }

         this.errorCounter = 0;
         this.isMGateConnected = true;
         return jsonObject;
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         this.errorCounter++;
         boolean isRunning = this.checkMGateRunning();
         if (!isRunning) {
            LOG.error("MGate is not running");
         }

         if (!isRunning || this.errorCounter >= 3 && this.isUsingLocalMGate && !this.isCheckingPort) {
            LOG.error("communication with MGate had errors, will restart MGate service");
            this.errorCounter = 0;
            this.setState(GatewayManager.GatewayState.ERROR);
            if (isRunning) {
               this.teardownMGate();
            }
         }

         this.isMGateConnected = false;
         return null;
      }
   }

   public boolean isUsingOldGateway() {
      return this.isUsingOldGateway;
   }

   public void setUsingOldGateway(boolean usingOldGateway) {
      LOG.info("setUsingOldGateway,{}", usingOldGateway);
      this.isUsingOldGateway = usingOldGateway;
      if (usingOldGateway && this.isMGateRunning) {
         this.teardownMGate();
      }
   }

   public boolean isOldGatewayRunning() {
      return this.isOldGatewayRunning;
   }

   public void setOldGatewayRunning(boolean isOldGatewayRunning) {
      this.isOldGatewayRunning = isOldGatewayRunning;
   }

   public boolean isMGateRunning() {
      return this.isMGateRunning;
   }

   public void setMGateRunning(boolean isMGateRunning) {
      if (this.isMGateRunning != isMGateRunning) {
         this.isMGateRunning = isMGateRunning;
         if (isMGateRunning) {
            this.getOutputPorts();
            LastRFConfig rfconfig = LastRFConfig.loadLastConfig();

            try {
               this.setPlayoutSettings(rfconfig);
            } catch (IOException e) {
               LOG.error(e.getMessage(), e);
            }

            if (!rfconfig.gatewayStopped) {
               LOG.info("try to start playout when mgate start");

               try {
                  PlayoutUtils.triggerStartPlayout();
               } catch (BaseHttpServlet.MessageException e) {
                  LOG.error(e.getMessage(), e);
               }
            }
         }
      }
   }

   public String getMessageByCode(int res) {
      int SUCCESS = 0;
      int NOT_STARTED = -1;
      int JSON_PARSING_FAILED = 2;
      int ALREADY_STARTED = 5;
      int SET_RF_PARAMS_FAILED = 6;
      int NO_CARD_INFO = 7;
      int NO_SUCH_STREAM = 8;
      switch (res) {
         case -1:
            return "Not started";
         case 0:
            return "success";
         case 1:
         case 3:
         case 4:
         default:
            return "error code:" + res;
         case 2:
            return "Json parsing failed";
         case 5:
            return "Already started";
         case 6:
            return "Set RF Params failed";
         case 7:
            return "Get card info failed";
         case 8:
            return "No stream to remove";
      }
   }

   public String getItemStatusByCode(int code) {
      String status = "";
      switch (code) {
         case 0:
            status = "New file";
            break;
         case 1:
            status = "File is being download";
            break;
         case 2:
            status = "File is retrieved";
            break;
         case 3:
            status = "PID file is created for this file";
            break;
         case 4:
            status = "File is playing out in TS";
            break;
         case 5:
            status = "Unable to collect file";
            break;
         case 6:
            status = "Unable to create a PID file";
            break;
         case 7:
            status = "File not valid";
            break;
         case 8:
            status = "File no longer in use";
            break;
         case 9:
            status = "Temp files (pid, dsi) are being removed";
            break;
         case 10:
            status = "Temp files cleaned";
            break;
         case 11:
            status = "File is being removed";
            break;
         case 12:
            status = "Maximum number of playouts reached, item is not played out!";
            break;
         default:
            return "Unkown error:" + code;
      }

      return status;
   }

   public void playToGateway(List<PlayoutInfo> infos) {
      this.teardownMGate();
      if (RFCommandState.instance().hasExecutionRunning()) {
         RFCommandState.instance().cleanup();
      }

      try {
         this.setState(GatewayManager.GatewayState.GENERATING);

         for (PlayoutInfo info : infos) {
            this.updatePlayoutStatus(info.getPlayoutId(), 1);
         }

         this.runGateway("BUILD");
         this.runGateway("PLAY");
         this.setState(GatewayManager.GatewayState.PLAYING);
         LOG.info("SettingCreator.java -> playToRF -> PLAY, Gateway.exe is running?{} ", ProcessUtils.isProcessRunning("Gateway.exe"));
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }
   }

   private void updatePlayoutStatus(int playoutId, int status) {
      PlayoutInfo playoutInfo = JpaManager.getPlayoutInfoManager().loadByKey(playoutId);
      if (null != playoutInfo) {
         playoutInfo.setStatus(status);
         JpaManager.getPlayoutInfoManager().save(playoutInfo);
         this.setItemUpdatePooling(true);
      }
   }

   public void runGateway(String option) throws IOException {
      String line = CommonConstants.RF_PLAY_BACK_EXE_NAME + " " + option;
      LOG.info("Going to perform PSG application action: {}", option);
      LOG.info("Running following command now: \n\t\t{}", line);
      CommandLine cmdLine = CommandLine.parse(line);
      DefaultExecutor executor = new DefaultExecutor();
      int[] values = new int[]{0, 1};
      executor.setExitValues(values);
      executor.setWorkingDirectory(new File(CommonConstants.RF_PLAY_BACK_EXE_WD));
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);
      executor.setStreamHandler(streamHandler);

      try {
         Map<String, String> mapEnv = EnvironmentUtils.getProcEnvironment();
         if (option.equalsIgnoreCase("PLAY")) {
            ExecuteWatchdog watchDog = new ExecuteWatchdog(-1L);
            executor.setWatchdog(watchDog);
            RFCommandState.instance().register(executor);
            DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
            executor.execute(cmdLine, resultHandler);
         } else {
            executor.execute(cmdLine, mapEnv);
         }

         LOG.info("output:{}", outputStream);
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         LOG.info(new String(outputStream.toByteArray()));
      }
   }

   public String getErrorMessage() {
      return this.errorMessage;
   }

   public void setErrorMessage(String errorMessage) {
      this.errorMessage = errorMessage;
   }

   public boolean isUsingLocalMGate() {
      return this.isUsingLocalMGate;
   }

   public void updateGatewayPlaystatus() {
      if (this.isUsingOldGateway()
         && this.isNeedUpdateStatus
         && this.isTSgenerated()
         && this.isOldGatewayRunning()
         && this.getState() == GatewayManager.GatewayState.PLAYING) {
         for (PlayoutInfo info : JpaManager.getPlayoutInfoManager().loadAll()) {
            this.updatePlayoutStatus(info.getPlayoutId(), 4);
         }

         this.isNeedUpdateStatus = false;
         LOG.info("update gateway playout status");
      }
   }

   private boolean isTSgenerated() {
      File file = new File(CommonConstants.RF_PLAY_BACK_OUTPUT_PATH);
      if (!file.exists()) {
         return false;
      }

      File[] subFiles = file.listFiles();
      if (null != subFiles && subFiles.length != 0) {
         for (File f : subFiles) {
            if (f.getName().endsWith(".ts")) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public enum GatewayState {
      INITIATING("initiating"),
      IDLE("idle"),
      STARTING("starting"),
      GENERATING("generating"),
      PLAYING("playing"),
      ERROR("error"),
      FAILED("failed"),
      UNKOWN("");

      private String value;

      GatewayState(String state) {
         this.value = state;
      }

      @Override
      public String toString() {
         return this.value;
      }
   }

   private static class SingletonHolder {
      private static final GatewayManager INSTANCE = new GatewayManager();
   }
}
