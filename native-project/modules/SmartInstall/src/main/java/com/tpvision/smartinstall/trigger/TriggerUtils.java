package com.tpvision.smartinstall.trigger;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.TriggerHistoryInfo;
import com.tpvision.smartinstall.dao.core.TriggerInfo;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.gateway.PlayoutUtils;
import com.tpvision.smartinstall.japit.remotecontrol.RemoteControlHelper;
import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.servlet.BaseHttpServlet;
import com.tpvision.smartinstall.servlet.TriggerServlet;
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.IPUpgradeManager;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TriggerUtils {
   private static final String SUCCESS = "success";
   private static final String FAIL = "fail";
   private static final String FORMAT_DATE_TIME = "dd|MMMMM|HH:mm";
   private static final String FORMAT_HH_MM = "HH:mm";
   private static final Logger LOG = LoggerFactory.getLogger(TriggerUtils.class);
   public static final int TRIGGER_TIME_GAP = 50000;

   private TriggerUtils() {
   }

   public static boolean isPmsTrigger(String typeName) {
      try {
         TriggerServlet.TriggerType type = TriggerServlet.TriggerType.valueOf(typeName);
         return type.name().startsWith("PMS_");
      } catch (Exception e) {
         LOG.error("invalid TriggerType:{}", typeName);
         return false;
      }
   }

   public static boolean isTimerTrigger(TriggerInfo info) {
      return info.getTriggerType() != null && info.getTriggerType().contains("Time");
   }

   public static boolean isTimeForTrigger(TriggerInfo info) {
      TriggerServlet.TriggerType type = TriggerServlet.TriggerType.valueOf(info.getTriggerType());
      String when = info.getTriggerCondition();
      Date triggerDate = null;
      Date now = new Date();

      try {
         switch (type) {
            case Date_and_Time:
               triggerDate = parseDateTime(when);
               now = convertByDateFormat(getDateTimeFormat(), now);
               break;
            case Day_and_Time:
               triggerDate = parseDayTime(when);
               now = convertByDateFormat(getTimeFormat(), now);
               break;
            case Time:
               triggerDate = parseTime(when);
               now = convertByDateFormat(getTimeFormat(), now);
               break;
            case PMS_CheckIn_or_CheckOut:
            case PMS_Group_ID:
            case PMS_Language:
            case Trigger:
            case New_Device:
            case Room_Type:
               return false;
            default:
               LOG.error("not supported trigger type:{}", type);
               return false;
         }
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }

      if (triggerDate != null) {
         long diff = Math.abs(now.getTime() - triggerDate.getTime());
         return diff <= 50000L;
      } else {
         return false;
      }
   }

   public static boolean isTriggerToday(String days) {
      if (days != null && !days.isEmpty()) {
         Calendar nCalendar = Calendar.getInstance();
         int day = nCalendar.get(7);
         return days.contains(String.valueOf(day));
      } else {
         return false;
      }
   }

   private static Date convertByDateFormat(SimpleDateFormat df, Date date) throws ParseException {
      String toStr = df.format(date);
      return df.parse(toStr);
   }

   public static Date parseDayTime(String daytime) throws IOException, ParseException {
      String[] dayfields = daytime.split("\\|");
      if (dayfields != null && dayfields.length == 2) {
         String days = dayfields[0];
         String time = dayfields[1];
         return isTriggerToday(days) ? parseTime(time) : null;
      } else {
         throw new IOException("invalid day time value:" + daytime);
      }
   }

   public static SimpleDateFormat getTimeFormat() {
      return TpvDateUtils.getSimpleDateFormatWithEnglishLocale("HH:mm");
   }

   public static SimpleDateFormat getDateTimeFormat() {
      return TpvDateUtils.getSimpleDateFormatWithEnglishLocale("dd|MMMMM|HH:mm");
   }

   public static Date parseTime(String time) throws ParseException {
      return getTimeFormat().parse(time);
   }

   public static Date parseDateTime(String datetime) throws ParseException {
      return getDateTimeFormat().parse(datetime);
   }

   public static void executeNewDeviceTrigger(Devices tv) {
      LOG.info("active new device trigger:{}", tv.getTvuniqueid());

      for (TriggerInfo info : JpaManager.getTriggerInfoManager()
         .findTriggerInfosByTriggerActiveAndTriggerTypeAndTarget(
            TriggerUtils.TriggerActiveState.Yes.name(), TriggerServlet.TriggerType.New_Device.name(), tv.getType()
         )) {
         executeTriggerThread(info, tv.getTvuniqueid(), null, true);
      }
   }

   public static void executePMSTrigger(PmsUtils.PmsAction action, String roomId, String value) {
      LOG.info("execute Trigger for roomid:{},action:{}", roomId, action);
      String triggerActive = TriggerUtils.TriggerActiveState.Yes.name();
      String triggerType = "";
      String triggerCondition = "";
      switch (action) {
         case CheckIn:
            triggerType = TriggerServlet.TriggerType.PMS_CheckIn_or_CheckOut.name();
            triggerCondition = "In";
            break;
         case CheckOut:
            triggerType = TriggerServlet.TriggerType.PMS_CheckIn_or_CheckOut.name();
            triggerCondition = "Out";
            break;
         case GroupChange:
            triggerType = TriggerServlet.TriggerType.PMS_Group_ID.name();
            triggerCondition = value;
            break;
         case LanguageChange:
            triggerType = TriggerServlet.TriggerType.PMS_Language.name();
            triggerCondition = value;
            break;
         case RoomType:
            triggerType = TriggerServlet.TriggerType.Room_Type.name();
            triggerCondition = value;
            break;
         default:
            LOG.error("unsupported action type:{}", action);
            return;
      }

      if (action == PmsUtils.PmsAction.CheckOut || action == PmsUtils.PmsAction.CheckIn) {
         PlayoutUtils.removeTriggerPlayouts(roomId);
      }

      try {
         List<TriggerInfo> infos = JpaManager.getTriggerInfoManager()
            .findTriggerInfosByTriggerActiveAndTriggerTypeAndTriggerCondition(triggerActive, triggerType, triggerCondition);
         if (infos == null || infos.isEmpty()) {
            throw new BaseHttpServlet.MessageException("no activated trigger exists");
         }

         for (TriggerInfo info : infos) {
            String tvGroups = "All".equalsIgnoreCase(info.getTarget()) ? null : info.getTarget();
            String tvs = JpaManager.getDevicesManager().findDeviceIdsByRoomId(roomId, tvGroups);
            if (tvs != null) {
               executeTriggerThread(info, tvs, roomId);
            }
         }
      } catch (BaseHttpServlet.MessageException e) {
         LOG.info(e.getMessage());
      }
   }

   private static boolean processRemoteControlTrigger(TriggerInfo info, String tvidList, String settingName, String id, String idName) {
      try {
         RemoteControlHelper helper = RemoteControlHelper.getInstance(tvidList, info.getTarget());
         String value = idName;
         if (StringUtils.isBlank(value)) {
            List<String> valueList = helper.getValueList(settingName);
            value = valueList.get(Integer.parseInt(id));
         }

         LOG.info("Trigger RemoteControl:{},{},{}", info.getName(), settingName, value);
         tryToEnablerService(helper);
         helper.changeSetting(settingName, value);
         info.setLastTrigger(new Date());
         JpaManager.getTriggerInfoManager().save(info);
         return true;
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         return false;
      }
   }

   private static void tryToEnablerService(RemoteControlHelper helper) throws InterruptedException {
      try {
         helper.changeEnabler("true");
      } catch (Exception ex) {
         LOG.warn("Some errors while enable service, while still continue");
      }

      Thread.sleep(3000L);
   }

   public static String getTVsByTarget(String target) {
      if (target == null) {
         LOG.error("target is empty");
         return null;
      }

      List<Devices> devicesList = null;
      if (target.equalsIgnoreCase("All")) {
         devicesList = JpaManager.getDevicesManager().loadAll();
      } else {
         devicesList = JpaManager.getDevicesManager().findDevicesByGroupName(target);
      }

      List<String> tvIds = devicesList.stream().map(Devices::getId).distinct().collect(Collectors.toList());
      return String.join(",", tvIds.toArray(new String[0]));
   }

   private static int addTriggerHistory(TriggerInfo info, String tvlist, String roomId, String type, String idName, String action) {
      TriggerHistoryInfo triggerHistory = new TriggerHistoryInfo();
      String source = null;
      String triggerInfo = null;
      String doAction = null;
      String triggerType = info.getTriggerType();
      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyy");
      triggerHistory.setTriggerTime(sdf.format(new Date()));
      if (triggerType.contains("PMS") || triggerType.contains("Room_Type")) {
         source = roomId;
      } else if (triggerType.contains("Time")) {
         source = info.getTriggerCondition();
         source = source.substring(source.length() - 5);
      } else if (triggerType.contains("New_Device") || triggerType.contains("Trigger")) {
         source = tvlist;
      }

      if (triggerType.contains("PMS")) {
         if (info.getTriggerCondition().contentEquals("In")) {
            triggerInfo = "PMS Check-In";
         } else if (info.getTriggerCondition().contentEquals("Out")) {
            triggerInfo = "PMS Check-Out";
         }
      } else {
         triggerInfo = triggerType;
      }

      triggerHistory.setSource(source);
      triggerHistory.setName(info.getName());
      if ("Wait".equalsIgnoreCase(type)) {
         doAction = type + " " + idName + " min " + action + " sec";
      } else if ("RemoteControl".equalsIgnoreCase(type)) {
         doAction = type + " : " + action + " " + idName;
      } else if ("None".equalsIgnoreCase(type)) {
         doAction = type;
      } else {
         doAction = type + " : " + idName;
      }

      triggerHistory.setTriggerInfo(triggerInfo);
      triggerHistory.setDoAction(doAction);
      triggerHistory.setTarget(info.getTarget());
      triggerHistory.setResult("pending");
      JpaManager.getTriggerHistoryManager().save(triggerHistory);
      return triggerHistory.getId();
   }

   private static void updateTriggerHistory(int id, String result) {
      JpaManager.getTriggerHistoryManager().updateResult(id, result);
   }

   public static void executeTriggerThread(TriggerInfo info, String tvlist, String roomId) {
      executeTriggerThread(info, tvlist, roomId, false);
   }

   public static void executeTriggerThread(TriggerInfo info, String tvlist, String roomId, boolean assignOnly) {
      if (!info.getTriggerActive().equalsIgnoreCase(TriggerUtils.TriggerActiveState.Yes.name())) {
         LOG.info("trigger {} inactive,exit", info.getName());
      } else {
         String dolist = info.getDolist();
         if (dolist == null) {
            LOG.info("trigger dolist is null,exit");
         } else {
            LOG.info("executeTrigger {} for tvs:{}", info.getName(), tvlist);
            new Thread(() -> {
               int triggerId = info.getId();
               Thread.currentThread().setName("trigger-id-thread-" + Integer.toString(triggerId));
               JSONArray arryDolist = new JSONArray(dolist);

               for (int i = 0; i < arryDolist.length(); i++) {
                  JSONObject doJob = arryDolist.getJSONObject(i);
                  String type = doJob.optString("type");
                  String id = doJob.optString("id");
                  String action = doJob.optString("do");
                  String idName = doJob.optString("idName");
                  int historyId = addTriggerHistory(info, tvlist, roomId, type, idName, action);
                  boolean executeResult = false;
                  switch (type) {
                     case "Wait":
                        executeResult = executeWait(id, action);
                        break;
                     case "RemoteControl":
                        executeResult = processRemoteControlTrigger(info, tvlist, action, id, idName);
                        break;
                     case "":
                     case "None":
                        continue;
                     default:
                        executeResult = executeUpgrade(info, type, id, tvlist, assignOnly);
                  }

                  if (executeResult) {
                     updateTriggerHistory(historyId, "success");
                  } else {
                     updateTriggerHistory(historyId, "fail");
                  }
               }
            }).start();
         }
      }
   }

   private static boolean executeWait(String id, String action) {
      int mill = 0;
      int min = StringUtils.isEmpty(id) ? 0 : Integer.parseInt(id);
      int sec = StringUtils.isEmpty(action) ? 0 : Integer.parseInt(action);
      mill = (min * 60 + sec) * 1000;

      try {
         Thread.sleep(mill);
      } catch (InterruptedException e) {
         LOG.warn(e.getMessage(), e);
         Thread.currentThread().interrupt();
         return false;
      }

      LOG.info("Trigger Wait:{}", mill);
      return true;
   }

   private static boolean executeUpgrade(TriggerInfo info, String type, String id, String tvlist, boolean assignOnly) {
      LOG.info("Trigger {}:{}", type, id);
      String tvs = tvlist;
      if (tvlist == null) {
         tvs = getTVsByTarget(info.getTarget());
      }

      CommonConstants.CloneItemType cloneType = IPUpgradeManager.convertUpgradeTypeToCloneItemType(type);
      if (cloneType == CommonConstants.CloneItemType.Clone || cloneType == CommonConstants.CloneItemType.Firmware) {
         LOG.info("prepare clone info:{}_{}", type, id);
         CloneItemUtils.CloneItemInfo cloneInfo = CloneItemUtils.getCloneItemInfo(type, Integer.parseInt(id));
         String platformId = cloneInfo.isDefaultPlatform() ? PlatformUtils.getPlatformId(info.getTarget()) : cloneInfo.getPlatform();

         try {
            IPUpgradeManager.generateClonePackage(cloneType, Integer.parseInt(id), platformId);
         } catch (IOException e) {
            LOG.warn("Failed to generate clone package {}", cloneInfo);
         }
      }

      boolean executeResult = false;
      String status = IPUpgradeManager.processCloneUpgradeType(type, id, tvs, "", null);
      if (!assignOnly) {
         if (IPUpgradeManager.startUpgrades(tvs, "U")) {
            executeResult = true;
         }
      } else if (status.contains("success")) {
         executeResult = true;
      }

      IPUpgradeManager.assignRFPlayouts(tvs, cloneType, Integer.parseInt(id), "Trigger");
      info.setLastTrigger(new Date());
      JpaManager.getTriggerInfoManager().save(info);
      return executeResult;
   }

   public static boolean isCloneAssigned(Integer cloneId) {
      return !JpaManager.getTriggerInfoManager().findByCloneTypeAndCloneId("Clone", cloneId).isEmpty();
   }

   public enum TriggerActiveState {
      Yes,
      No;
   }
}
