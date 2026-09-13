package com.tpvision.smartinstall.japit.remotecontrol;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.TriggerInfo;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.japit.ApplicationControlCmd;
import com.tpvision.smartinstall.japit.AudioServiceCmd;
import com.tpvision.smartinstall.japit.ChannelSelectionServiceCmd;
import com.tpvision.smartinstall.japit.EnablerServiceCmd;
import com.tpvision.smartinstall.japit.SourceServiceCmd;
import com.tpvision.smartinstall.japit.TVPowerManager;
import com.tpvision.smartinstall.servlet.RemoteControlServlet;
import com.tpvision.smartinstall.servlet.TriggerServlet;
import com.tpvision.smartinstall.trigger.TriggerUtils;
import com.tpvision.smartinstall.util.BroadcastUtils;
import com.tpvision.smartinstall.util.JAPITUtils;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.TpvCallableTask;
import com.tpvision.smartinstall.util.TpvNamedThreadFactory;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoteControlHelper {
   private static final Logger LOG = LoggerFactory.getLogger(RemoteControlHelper.class);
   private String tvids;
   private String gname;
   private String platform;
   private List<String> tvidList = new ArrayList<>();
   private String errorMessage;
   private boolean isValid = false;
   private int exceptionCount = 0;

   private RemoteControlHelper(String tvids, String groupName) {
      this.tvids = tvids;
      this.gname = groupName;
      this.init();
   }

   private void init() {
      try {
         if (!StringUtils.isNoneEmpty(this.tvids)) {
            this.platform = getGroupPlatform(this.gname);
            this.tvidList = this.getTvidListByGroupName(this.gname);
            if (this.tvidList.isEmpty()) {
               throw new SQLException("tv not found for group: " + this.gname);
            }
         } else {
            for (String oneId : this.tvids.split(",")) {
               Devices tv = JpaManager.getDevicesManager().loadByKey(oneId);
               if (tv != null) {
                  this.tvidList.add(oneId);
                  this.platform = PlatformUtils.getPlatformName(tv.getType());
               }
            }

            if (this.tvidList.isEmpty()) {
               throw new SQLException("tv not found for: " + this.tvids);
            }
         }

         this.isValid = true;
      } catch (Exception e) {
         this.isValid = false;
         this.errorMessage = e.getMessage();
      }
   }

   public List<String> getTvidListByGroupName(String groupName) {
      return JpaManager.getDevicesManager()
         .findDevicesByGroupName(groupName)
         .stream()
         .filter(e -> !e.isRFDevice())
         .map(e -> e.getId())
         .collect(Collectors.toList());
   }

   public static String getGroupPlatform(String groupName) {
      if (groupName == null) {
         return "";
      }

      String[] chunks = groupName.split("_");
      return chunks.length > 0 ? chunks[chunks.length - 1] : "";
   }

   public void checkValid() throws IOException {
      if (!this.isValid) {
         throw new IOException(this.errorMessage);
      }
   }

   public void checkValidRequest() throws IOException {
      this.checkValid();
      if (this.tvidList.size() > 1) {
         throw new IOException("group is not supported to request setting");
      }
   }

   public static RemoteControlHelper getInstance(String tvid, String groupName) throws IOException {
      RemoteControlHelper helper = new RemoteControlHelper(tvid, groupName);
      helper.checkValid();
      return helper;
   }

   private static JSONObject loadSettingsJson() {
      try {
         String jsonPath = RemoteControlHelper.class.getResource("/remote_control_dialog_settings.json").getFile();
         jsonPath = URLDecoder.decode(jsonPath, "utf-8");
         String contents = FileUtils.readFileToString(new File(jsonPath), StandardCharsets.UTF_8);
         if (contents != null && contents.length() != 0) {
            return new JSONObject(contents);
         } else {
            throw new IOException("remote control dialog settings json is empty");
         }
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         return null;
      }
   }

   public void changeSetting(String settingName, String value) throws Exception {
      RemoteControlServlet.RemoteControlType settingType = RemoteControlServlet.RemoteControlType.valueOf(settingName);
      switch (settingType) {
         case Application:
            this.changeApplication(value);
            break;
         case Channel:
            this.changeCurrentChannel(Integer.parseInt(value));
            break;
         case Mute:
            this.changeAudioMute(value);
            break;
         case Power:
            this.changePower(value);
            break;
         case Source:
            this.changeSource(value);
            break;
         case Volume:
            this.changeAudioVolume(value);
            break;
         case Enabler:
            this.changeEnabler(value);
            break;
         case ManualTrigger:
            this.executeTrigger(value);
            break;
         default:
            throw new IOException("unsupported setting to change:" + settingName);
      }
   }

   private static List<String> getSettingList(String platform, String settingName) {
      List<String> srcList = new ArrayList<>();
      JSONObject settingsJson = loadSettingsJson();
      if (settingsJson != null && settingsJson.has("group_values")) {
         JSONArray groupvalues = settingsJson.optJSONArray("group_values");

         for (int i = 0; i < groupvalues.length(); i++) {
            JSONObject obj = groupvalues.optJSONObject(i);
            if (obj.optString("platform_type").equalsIgnoreCase(platform)) {
               JSONArray arr = obj.optJSONArray(settingName);

               for (int j = 0; j < arr.length(); j++) {
                  srcList.add(arr.optString(j));
               }
               break;
            }
         }
      }

      return srcList;
   }

   public String requestPower() throws Exception {
      this.checkValidRequest();
      String data = TVPowerManager.requestPowerCmd();
      Devices tv = JpaManager.getDevicesManager().loadByKey(this.tvidList.get(0));
      String resp = JAPITUtils.sendJapitCommand(tv, data);
      JSONObject powerObj = new JSONObject(resp);
      return powerObj.has("CommandDetails")
         ? powerObj.getJSONObject("CommandDetails").getJSONObject("PowerServiceParameters").getString("CurrentPowerState")
         : null;
   }

   public void changePower(String toPower) throws Exception {
      if (toPower.equalsIgnoreCase("reboot")) {
         toPower = "REBOOT";
      }

      String realPower = toPower;
      this.executeCommnandUsingThreadPool(
         tvid -> {
            try {
               Devices tv = JpaManager.getDevicesManager().loadByKey(tvid);
               if ("Wakeup".equalsIgnoreCase(realPower)) {
                  BroadcastUtils.broadcastWakeOnLanMagicPackageForAllNetworkInterfaces(tv.getTvmacaddress());
               } else {
                  String data = TVPowerManager.changePowerService(realPower);
                  if (!tv.isOnline()
                     && StringUtils.equalsIgnoreCase(realPower, "On")
                     && PlatformUtils.isSupportWakeupOnLan(tv.getType())
                     && !TVPowerManager.wakeupTvOnLan(tv)) {
                     this.exceptionCount++;
                     return false;
                  }

                  TVPowerManager.sendJapitCommandWithExceptionIgnore(tv, data);
                  tv.setPowerstatus(realPower);
                  JpaManager.getDevicesManager().save(tv);
               }

               return true;
            } catch (Exception e) {
               LOG.error(e.getMessage(), e);
               this.exceptionCount++;
               return false;
            }
         }
      );
      if (this.exceptionCount > 0 && !toPower.equalsIgnoreCase("reboot")) {
         throw new IOException("change Power for some TVs failed");
      }
   }

   public List<String> requestSourceList() throws Exception {
      return getSettingList(this.platform, "sources");
   }

   public List<String> requestApplicationListByTvId(String tvId) throws Exception {
      try {
         return ApplicationControlCmd.requestApplicationList(tvId, "NonNative");
      } catch (IOException | JSONException ex) {
         this.enableRemoteControlFunctionsByTvId(tvId, true);
         return ApplicationControlCmd.requestApplicationList(tvId, "NonNative");
      }
   }

   public Map<Integer, String> requestManualTriggerMap() {
      Map<Integer, String> mannulTriggerMap = new HashMap<>();

      for (TriggerInfo trigger : JpaManager.getTriggerInfoManager()
         .findTriggerInfosByTriggerActiveAndTriggerTypeAndTarget(TriggerInfo.TRIGGER_ACTIVE_YES, TriggerServlet.TriggerType.Manual.name(), this.platform)) {
         mannulTriggerMap.put(trigger.getId(), trigger.getName());
      }

      return mannulTriggerMap;
   }

   public void changeApplication(String appName) throws Exception {
      this.executeCommnandUsingThreadPool(tvid -> {
         try {
            ApplicationControlCmd.changeCurrentApplication(tvid, appName);
            return true;
         } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            this.exceptionCount++;
            return false;
         }
      });
      if (this.exceptionCount > 0) {
         throw new IOException("change Application for some TVs failed");
      }
   }

   public String requestActiveApplication() throws Exception {
      this.checkValidRequest();
      return ApplicationControlCmd.requestActiveApplication(this.tvidList.get(0));
   }

   public String requestAudioMute() throws Exception {
      this.checkValidRequest();
      String tvid = this.tvidList.get(0);
      Devices device = JpaManager.getDevicesManager().findDeviceByTVUniqueID(tvid);
      return AudioServiceCmd.requestAudioMute(tvid, device.getType());
   }

   public String requestAudioVolume() throws Exception {
      this.checkValidRequest();
      String tvid = this.tvidList.get(0);
      Devices device = JpaManager.getDevicesManager().findDeviceByTVUniqueID(tvid);
      return AudioServiceCmd.requestAudioVolume(tvid, device.getType());
   }

   public void changeAudioVolume(String volume) throws Exception {
      this.executeCommnandUsingThreadPool(tvid -> {
         try {
            Devices device = JpaManager.getDevicesManager().findDeviceByTVUniqueID(tvid);
            AudioServiceCmd.changeAudioVolume(tvid, device.getType(), volume);
            return true;
         } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            this.exceptionCount++;
            return false;
         }
      });
      if (this.exceptionCount > 0) {
         throw new IOException("change Audio Volume for some TVs failed");
      }
   }

   public void changeAudioMute(String mute) throws Exception {
      this.executeCommnandUsingThreadPool(tvid -> {
         try {
            Devices device = JpaManager.getDevicesManager().findDeviceByTVUniqueID(tvid);
            AudioServiceCmd.changeAudioMute(tvid, device.getType(), mute);
            return true;
         } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            this.exceptionCount++;
            return false;
         }
      });
      if (this.exceptionCount > 0) {
         throw new IOException("change Audio Mute for some TVs failed");
      }
   }

   public String requestSource() throws Exception {
      this.checkValidRequest();
      return SourceServiceCmd.requestSource(this.tvidList.get(0));
   }

   public void changeSource(String source) throws Exception {
      this.executeCommnandUsingThreadPool(tvid -> {
         try {
            SourceServiceCmd.changeSource(tvid, source);
            return true;
         } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            this.exceptionCount++;
            return false;
         }
      });
      if (this.exceptionCount > 0) {
         throw new IOException("change Source for some TVs failed");
      }
   }

   public String requestCurrentChannel() throws Exception {
      this.checkValidRequest();
      return ChannelSelectionServiceCmd.requestCurrentChannel(this.tvidList.get(0));
   }

   public void changeCurrentChannel(int channelNumber) throws Exception {
      this.executeCommnandUsingThreadPool(tvid -> {
         try {
            ChannelSelectionServiceCmd.changeCurrentChannel(tvid, channelNumber);
            return true;
         } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            this.exceptionCount++;
            return false;
         }
      });
      if (this.exceptionCount > 0) {
         throw new IOException("change CurrentChannel for some TVs failed");
      }
   }

   public void changeEnabler(String value) throws Exception {
      boolean newServiceStatus = value.equalsIgnoreCase("true");
      this.executeCommnandUsingThreadPool(tvid -> {
         try {
            this.enableRemoteControlFunctionsByTvId(tvid, newServiceStatus);
            return true;
         } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            this.exceptionCount++;
            return false;
         }
      });
      if (this.exceptionCount > 0) {
         throw new IOException("change enabler for some TVs failed");
      }
   }

   public void executeTrigger(String value) throws Exception {
      int triggerId = Integer.parseInt(value);
      TriggerInfo triggerInfo = JpaManager.getTriggerInfoManager().loadByKey(triggerId);
      if (triggerInfo != null && StringUtils.equals(triggerInfo.getTriggerType(), TriggerServlet.TriggerType.Manual.name())) {
         try {
            TriggerUtils.executeTriggerThread(triggerInfo, String.join(",", this.tvidList), null);
         } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new IOException("execute manual trigger for some TVs failed");
         }
      } else {
         throw new IOException("manual trigger data error");
      }
   }

   private void enableRemoteControlFunctionsByTvId(String tvid, boolean newServiceStatus) throws Exception {
      Devices enablerTV = JpaManager.getDevicesManager().loadByKey(tvid);
      if (enablerTV == null) {
         throw new IOException("device not found:" + tvid);
      }

      if (PlatformUtils.isSupportEnablerService(enablerTV.getType())) {
         EnablerServiceCmd.changeEnabler(tvid, newServiceStatus);
      } else {
         LOG.info("tvid <{}> with Type<{}> not support enabler japit", tvid, enablerTV.getType());
      }
   }

   public static List<String> generateChannelList() {
      List<String> channelList = new ArrayList<>();

      for (int i = 1; i <= 500; i++) {
         channelList.add(String.valueOf(i));
      }

      return channelList;
   }

   private static List<String> generateVolumeList() {
      List<String> volumeList = new ArrayList<>();

      for (int i = 0; i <= 60; i++) {
         volumeList.add(String.valueOf(i));
      }

      return volumeList;
   }

   public List<String> getValueList(String settingName) throws Exception {
      RemoteControlServlet.RemoteControlType settingType = RemoteControlServlet.RemoteControlType.valueOf(settingName);
      return (settingType == RemoteControlServlet.RemoteControlType.Application || settingType == RemoteControlServlet.RemoteControlType.ApplicationList)
            && this.tvidList.size() == 1
         ? this.requestApplicationListByTvId(this.tvidList.get(0))
         : getValueList(this.platform, settingName);
   }

   public String getPlatform() {
      return this.platform;
   }

   public static List<String> getValueList(String platform, String settingName) throws Exception {
      RemoteControlServlet.RemoteControlType settingType = RemoteControlServlet.RemoteControlType.valueOf(settingName);
      switch (settingType) {
         case Application:
         case ApplicationList:
            return getSettingList(platform, "applications");
         case Channel:
         case ChannelList:
            return generateChannelList();
         case Mute:
            return Arrays.asList("On", "Off");
         case Power:
            return Arrays.asList("On", "Standby", "Reboot");
         case Source:
         case SourceList:
            return getSettingList(platform, "sources");
         case Volume:
            return generateVolumeList();
         case Enabler:
         case ManualTrigger:
         default:
            LOG.info("not supportted remote control valuelist:{}, return empty", settingType);
            return new ArrayList<>();
      }
   }

   private void executeCommnandUsingThreadPool(Predicate<String> commandFunction) {
      int tvIdCount = this.tvidList.size();
      LOG.info("start to do action on {}", this.tvidList);
      if (tvIdCount != 0) {
         StopWatch stopWatch = new StopWatch();
         stopWatch.start();
         ThreadPoolExecutor executorService = new ThreadPoolExecutor(
            tvIdCount, tvIdCount, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new TpvNamedThreadFactory("remote-control-cmd-pool")
         );
         List<Future<Boolean>> resultList = new ArrayList<>();
         this.tvidList.stream().forEach(tvId -> {
            Future<Boolean> result = executorService.submit(new TpvCallableTask<Boolean>() {
               public Boolean execute() {
                  return commandFunction.test(tvId);
               }
            });
            resultList.add(result);
         });

         for (Future<Boolean> futureResult : resultList) {
            try {
               futureResult.get();
            } catch (Exception e) {
               e.printStackTrace();
            }
         }

         if (!executorService.isShutdown()) {
            executorService.shutdownNow();
         }

         stopWatch.stop();
         LOG.info("action cost time:{} seconds", stopWatch.getTime(TimeUnit.SECONDS));
      }
   }
}
