package com.tpvision.smartinstall.pms;

import com.tpvision.smartinstall.bean.PmsAlarmStatus;
import com.tpvision.smartinstall.dao.core.Billitem;
import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.GuestInfo;
import com.tpvision.smartinstall.dao.core.Message;
import com.tpvision.smartinstall.dao.core.PmsStatus;
import com.tpvision.smartinstall.dao.core.Reservation;
import com.tpvision.smartinstall.dao.core.Roominfo;
import com.tpvision.smartinstall.dao.core.SIConfig;
import com.tpvision.smartinstall.dao.core.WakeupInfo;
import com.tpvision.smartinstall.dao.mgr.BillitemManager;
import com.tpvision.smartinstall.dao.mgr.DevicesManager;
import com.tpvision.smartinstall.dao.mgr.GuestInfoManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.MessageManager;
import com.tpvision.smartinstall.dao.mgr.PmsStatusManager;
import com.tpvision.smartinstall.dao.mgr.ReservationManager;
import com.tpvision.smartinstall.dao.mgr.WakeupInfoManager;
import com.tpvision.smartinstall.gateway.PlayoutUtils;
import com.tpvision.smartinstall.japit.ApplicationControlCmd;
import com.tpvision.smartinstall.japit.EnablerServiceCmd;
import com.tpvision.smartinstall.japit.JapitCommand;
import com.tpvision.smartinstall.japit.PmsJapitCommand;
import com.tpvision.smartinstall.japit.TVPowerManager;
import com.tpvision.smartinstall.schedule.CmndMetricsTask;
import com.tpvision.smartinstall.servlet.PollingWebSocket;
import com.tpvision.smartinstall.trigger.TriggerUtils;
import com.tpvision.smartinstall.util.CastServerUtils;
import com.tpvision.smartinstall.util.JAPITUtils;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.PmsMessageUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.TpvNamedThreadFactory;
import com.tpvision.smartinstall.util.TpvRunableTask;
import com.tpvision.smartinstall.util.TpvStringUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PmsUtils {
   private static final Logger LOG = LoggerFactory.getLogger(PmsUtils.class);
   private static TmsUtils sTmsUtils;
   private static ScheduledExecutorService service4message;
   private static ScheduledExecutorService service4connection;
   private static ScheduledExecutorService service4wakeup;
   private static ThreadPoolExecutor executor = new ThreadPoolExecutor(
      5, 50, 50000L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1000), new TpvNamedThreadFactory("pms-sync-pool")
   );
   private static Map<String, Timer> timerMap = new HashMap<>();
   private static final int REQUEST_TIMEOUT = 10000;
   private static Random rand = new Random();

   private static String getTimerName(PmsUtils.RequestType requestType, String roomId) {
      Integer room = Integer.parseInt(roomId);
      return requestType.name() + "_" + room;
   }

   public static void setRequestTimer(final PmsUtils.RequestType requestType, final String roomId) {
      String timerName = getTimerName(requestType, roomId);
      TimerTask task = new TimerTask() {
         @Override
         public void run() {
            String requestName = requestType.name();
            PmsUtils.LOG.warn("{} for room:{} not respond from FIAS in {} seconds", requestName, roomId, 10);
            PmsUtils.removeRequestTimer(requestType, roomId);
            this.cancel();
         }
      };
      Timer timer = new Timer(timerName);
      timer.schedule(task, 10000L, 10000L);
      timerMap.put(timerName, timer);
   }

   public static void removeRequestTimer(PmsUtils.RequestType requestType, String roomId) {
      String timerName = getTimerName(requestType, roomId);
      if (timerMap.containsKey(timerName)) {
         Timer timer = timerMap.get(timerName);
         timer.cancel();
         timerMap.remove(timerName);
         LOG.info("removeRequestTimer {}", timerName);
      }
   }

   private PmsUtils() {
   }

   public static String changeXML2String(Object obj) throws JAXBException {
      JAXBContext jax = JAXBContext.newInstance(obj.getClass());
      Marshaller msh = jax.createMarshaller();
      msh.setProperty("jaxb.formatted.output", true);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      msh.marshal(obj, baos);
      return baos.toString();
   }

   public static String getPmsTypeName() {
      PmsStatus ps = getPmsStatus();
      if (!"on".equalsIgnoreCase(ps.getPms())) {
         return "Off";
      }

      String pmsType = ps.getPmstype();
      pmsType = pmsType.split(" ")[0];
      pmsType = pmsType.replace("TMS", "").replace("PMS", "");
      if (pmsType.contains("oracle")) {
         pmsType = "FIAS";
      }

      return pmsType.toUpperCase();
   }

   public static void createTmsInstace(String pmsType) {
      if (sTmsUtils != null) {
         sTmsUtils.stop();
      }

      LOG.info("createTmsInstace,Current PMS Type:{}", pmsType);
      if (pmsType == null || pmsType.equalsIgnoreCase("none")) {
         sTmsUtils = null;
      } else if (pmsType.contains("tigerTMS")) {
         sTmsUtils = new TigerTmsUtils();
      } else if (pmsType.contains("oracleTMS")) {
         sTmsUtils = new OracleTmsUtils();
      } else if (pmsType.contains("impala")) {
         sTmsUtils = new ImpalaTmsUtils();
      } else if (pmsType.contains("accor")) {
         sTmsUtils = new AccorFolsTmsUtils();
      } else if (pmsType.contains("htng")) {
         sTmsUtils = new HTNGTmsUtils();
      } else if (pmsType.contains("fiasServer")) {
         sTmsUtils = new FiasServerTmsUtils();
      } else if (pmsType.contains("hop")) {
         sTmsUtils = new HopTmsUtils();
      } else {
         sTmsUtils = null;
      }

      if (sTmsUtils != null) {
         sTmsUtils.start();
      } else {
         updatePmsConnectionStatus("Ok");
      }
   }

   public static boolean isAutoCreateTV() {
      TmsUtils tms = getTmsInstance();
      return tms == null || tms.isAutoCreateTV();
   }

   private static String getAutoRFPlatform() {
      TmsUtils tms = getTmsInstance();
      return tms == null ? null : tms.getConfigValue("RFTV_PLATFORM", "2019 MS");
   }

   public static TmsUtils getTmsInstance() {
      String pmsType = getTmsType();
      if (!pmsType.equalsIgnoreCase("none") && sTmsUtils == null) {
         createTmsInstace(pmsType);
      }

      return sTmsUtils;
   }

   public static void submitSyncPmsStatusTask(Devices devices) {
      executor.submit((new TpvRunableTask() {
         private Devices devices;

         public Runnable init(Devices devices) {
            this.devices = devices;
            return this;
         }

         @Override
         public void execute() {
            PmsUtils.syncPmsStatus(this.devices);
         }
      }).init(devices));
   }

   private static void stopTmsInstance() {
      LOG.info("Stopping TMS instance");
      if (null != sTmsUtils) {
         sTmsUtils.stop();
         sTmsUtils = null;
      }
   }

   public static void setPMSEnabled(boolean enabled) {
      LOG.info("set pms {}", enabled ? "enabled" : "disabled");
      if (enabled) {
         if (isPmsMessagesEnabled()) {
            startPmsMessageSchedule();
         } else {
            LOG.info("PMS Message is disabled");
            stopPmsMessageSchedule();
         }

         startTmsConnectionSchedule();
         startWakeupAlarmSchedule();
      } else {
         stopPmsMessageSchedule();
         stopTmsConnectionSchedule();
         stopWakeupAlarmSchedule();
      }
   }

   private static void stopTmsConnectionSchedule() {
      LOG.info("Stop PMS connection check task");
      if (service4connection != null) {
         service4connection.shutdownNow();
         service4connection = null;
      }

      stopTmsInstance();
   }

   private static void startTmsConnectionSchedule() {
      if (service4connection != null) {
         LOG.error("pms connection check task is running");
      } else {
         if (getTmsInstance() != null) {
            service4connection = Executors.newScheduledThreadPool(1, new TpvNamedThreadFactory("tms"));
            service4connection.scheduleWithFixedDelay(new TmsConnectQueryTask(), 1000L, 60000L, TimeUnit.MILLISECONDS);
            LOG.info("Check TMS connection status every 1 minute.");
         }
      }
   }

   private static void startPmsMessageSchedule() {
      LOG.info("Start PMS messages check task every 1 minute.");
      if (service4message != null) {
         LOG.error("pms message check task is running");
      } else {
         service4message = Executors.newScheduledThreadPool(1, new TpvNamedThreadFactory("pms-message"));
         service4message.scheduleWithFixedDelay(new UpdatePmsMsgTask(), 1L, 60L, TimeUnit.SECONDS);
      }
   }

   private static void stopPmsMessageSchedule() {
      LOG.info("Stop PMS messages check task");
      if (service4message != null) {
         service4message.shutdownNow();
         service4message = null;
      }
   }

   public static void startWakeupAlarmSchedule() {
      if (service4wakeup != null) {
         LOG.error("pms WakeupAlarm task is running");
      } else {
         LOG.info("Start PMS WakeupAlarm task every 1 minute.");
         service4wakeup = Executors.newScheduledThreadPool(1, new TpvNamedThreadFactory("pms-wakeup"));
         service4wakeup.scheduleWithFixedDelay(new WakeupAlarmTask(), 1L, 60L, TimeUnit.SECONDS);
      }
   }

   public static void stopWakeupAlarmSchedule() {
      if (service4wakeup != null) {
         LOG.info("Stop PMS WakeupAlarm task.");
         service4wakeup.shutdown();
         service4wakeup = null;
      }
   }

   public static boolean sendMsg2TV(String roomid) {
      if (!isPmsMessagesEnabled()) {
         return false;
      }

      MessageManager mm = JpaManager.getMessageManager();
      List<Message> msgs = mm.findAccessTimeSendMessageByGuestIds(roomid);
      if (msgs.isEmpty()) {
         LOG.error("There is no suitable msgs for the guestId={}!", roomid);
         return false;
      }

      try {
         sendData2Room(roomid, t -> changeGuestMessage(t, msgs));

         for (Message message : msgs) {
            if (message.getIsSent().equalsIgnoreCase("N")) {
               message.setIsSent("Y");
               message.setStatus("UnRead");
               mm.save(message);
            }
         }

         setMessageUpdated();
         return true;
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
         return false;
      }
   }

   public static void changeLanguage(String roomid, String lang) throws SQLException, IOException {
      GuestInfoManager gim = JpaManager.getGuestInfoManager();
      List<GuestInfo> guestInfos = gim.findGuestInfosByRoomid(roomid);
      if (guestInfos != null && !guestInfos.isEmpty()) {
         for (GuestInfo guestInfo : guestInfos) {
            guestInfo.setGuestLanguage(lang);
            gim.save(guestInfo);
            if ("Y".equalsIgnoreCase(guestInfo.getCheckin())) {
               CastServerUtils.sendCheckInNoticeToOpenApiServer(guestInfo);
            }
         }

         if (hasRFTVForRoom(roomid)) {
            PlayoutUtils.addPMSAction(PmsUtils.PmsAction.LanguageChange, roomid);
         }

         GuestInfo guestInfo = guestInfos.get(0);
         sendUpdateGuestPreference(guestInfo);
         TriggerUtils.executePMSTrigger(PmsUtils.PmsAction.LanguageChange, roomid, lang);
      } else {
         throw new SQLException("not guest found for roomid=" + roomid);
      }
   }

   public static GuestInfo handleGuestResults(Element rootElt) {
      GuestInfo guestInfo = assembleGuestInfo(rootElt);
      if (guestInfo != null) {
         String roomId = guestInfo.getRoomid();

         try {
            updateGuestInfo(roomId, PmsUtils.GuestInfoFieldType.GuestDepartureDate, guestInfo.getDepartureDate());
            updateGuestInfo(roomId, PmsUtils.GuestInfoFieldType.GuestLanguage, guestInfo.getGuestLanguage());
            updateGuestInfo(roomId, PmsUtils.GuestInfoFieldType.GuestName, guestInfo.getGuestName());
         } catch (IOException e) {
            LOG.info("update guestinfo fail:{}", e.getMessage());
         }
      }

      return guestInfo;
   }

   public static void updatePMSFeaturesToTV() {
      new Thread(() -> {
         try {
            GuestInfoManager gim = JpaManager.getGuestInfoManager();

            for (GuestInfo gi : gim.loadAll()) {
               if (!gi.getCheckin().equalsIgnoreCase("Y")) {
                  return;
               }

               sendUpdateGuestPreference(gi);
               sendUpdatePmsFeatures(gi);
            }
         } catch (Exception e) {
            LOG.error(e.getMessage(), e);
         }
      }).start();
   }

   private static String getSwitchValueByConfig(String currentVal, String config) {
      String newValue = currentVal;
      switch (config) {
         case "Off":
            newValue = "False";
            break;
         case "DefaultAlwaysOn":
         case "Demo":
            if (currentVal == null) {
               newValue = "True";
            }
            break;
         case "DefaultAlwaysOff":
            if (currentVal == null) {
               newValue = "False";
            }
         case "FollowPMS":
            break;
         default:
            LOG.info("invalid config:{}", config);
      }

      return newValue;
   }

   public static CheckInVO getCheckInVO(GuestInfo guest) {
      CheckInVO checkinVO = new CheckInVO();
      String guestName = guest.getGuestName();
      String langugae = guest.getGuestLanguage();
      String title = guest.getTitle();
      int index = guestName.lastIndexOf(32);
      String firstName = "";
      String lastName = "";
      String expressCheckout = "False";
      String viewBill = "False";
      String doNotDisturb = "True";
      boolean guestDetails = false;
      boolean guestPreferLanguage = false;
      boolean messages = false;
      if (index == -1) {
         firstName = guestName;
      } else {
         firstName = guestName.substring(0, index);
         lastName = guestName.substring(index + 1, guestName.length());
      }

      String displayName = null;
      if ("".equalsIgnoreCase(guestName)) {
         displayName = guest.getGroupName();
      } else {
         displayName = title + " " + guestName;
      }

      if ("brazil-portuguese".equalsIgnoreCase(langugae)) {
         langugae = "por";
      }

      PmsStatus ps = null;
      PmsStatusManager psm = JpaManager.getPmsStatusManager();
      ps = psm.loadByKey(1);
      expressCheckout = getSwitchValueByConfig(guest.getExpressCheckout(), ps.getExpresscheckout());
      viewBill = getSwitchValueByConfig(guest.getViewBill(), ps.getBillontv());
      doNotDisturb = getSwitchValueByConfig(guest.getDoNotDisturb(), ps.getDoNotDisturb());
      if ("On".equalsIgnoreCase(ps.getGuestname())) {
         guestDetails = true;
      }

      if ("On".equalsIgnoreCase(ps.getGuestlanguage())) {
         guestPreferLanguage = true;
      }

      if ("On".equalsIgnoreCase(ps.getMessages())) {
         messages = guest.getViewMessage() == null || guest.getViewMessage().equalsIgnoreCase("True");
      }

      if (null == displayName) {
         checkinVO.setDisplayName("");
      } else {
         checkinVO.setDisplayName(TpvStringUtils.string2Unicode(displayName));
      }

      checkinVO.setFirstName(TpvStringUtils.string2Unicode(firstName));
      checkinVO.setSurName(TpvStringUtils.string2Unicode(lastName));
      checkinVO.setGroupName(TpvStringUtils.string2Unicode(guest.getGroupName()));
      checkinVO.setGuestDetailsEnabled(guestDetails);
      checkinVO.setGuestPreferLanguageEnabled(guestPreferLanguage);
      checkinVO.setLanguage(langugae);
      checkinVO.setMessageEnabled(messages);
      checkinVO.setDonotDisturb(doNotDisturb);
      checkinVO.setViewBill(viewBill);
      checkinVO.setExpressCheckout(expressCheckout);
      checkinVO.setRoomStatus("Occupied");
      checkinVO.setSharingStatus("Single");
      checkinVO.setArrivalDate(guest.getArrivalDate());
      String checkinTime = guest.getCheckinTime();
      if (StringUtils.isEmpty(checkinTime)) {
         checkinVO.setArrivalTime("00:00");
      } else {
         checkinVO.setArrivalTime(checkinTime.split(" ")[1]);
      }

      checkinVO.setDepartureDate(guest.getDepartureDate());
      checkinVO.setDepartureTime("");
      checkinVO.setDemo(ps.isDemoBill());
      return checkinVO;
   }

   public static CheckInVO processCheckInVOForASTA(CheckInVO checkInVO) {
      if (checkInVO == null) {
         return null;
      }

      checkInVO.setDisplayName(TpvStringUtils.unicode2ASTA(checkInVO.getDisplayName()));
      checkInVO.setFirstName(TpvStringUtils.unicode2ASTA(checkInVO.getFirstName()));
      checkInVO.setSurName(TpvStringUtils.unicode2ASTA(checkInVO.getSurName()));
      checkInVO.setGroupName(TpvStringUtils.unicode2ASTA(checkInVO.getGroupName()));
      return checkInVO;
   }

   private static List<Devices> getIPTVForCheckRoom(String roomId) {
      return getTVsForRoom(roomId).stream().filter(e -> !e.isRFDevice()).collect(Collectors.toList());
   }

   public static List<Devices> getTVsForRoom(String roomId) {
      DevicesManager dm = JpaManager.getDevicesManager();
      return dm.findDevicesByRoomId(roomId);
   }

   public static List<Devices> getCompatibleTVsForRoom(String roomId, String tvid) {
      List<Devices> tvIPAddrs = new ArrayList<>();
      DevicesManager dm = JpaManager.getDevicesManager();
      List<Devices> devices = getTVsForRoom(roomId);
      Devices targetDevice = dm.loadByKey(tvid);

      for (Devices device : devices) {
         if (null != targetDevice && device.getType().equalsIgnoreCase(targetDevice.getType())) {
            tvIPAddrs.add(device);
         }
      }

      return tvIPAddrs;
   }

   public static PmsStatus getPmsStatus() {
      PmsStatusManager psm = JpaManager.getPmsStatusManager();
      return psm.loadByKey(1);
   }

   public static boolean isPmsEnabled() {
      boolean val = false;
      PmsStatus ps = getPmsStatus();
      if (null != ps && "on".equalsIgnoreCase(ps.getPms())) {
         val = true;
      }

      return val;
   }

   public static boolean isPmsMessagesEnabled() {
      boolean val = false;
      PmsStatus ps = getPmsStatus();
      if (null != ps && "on".equalsIgnoreCase(ps.getMessages())) {
         val = true;
      }

      return val;
   }

   public static String getTmsType() {
      String val = "";
      PmsStatus ps = getPmsStatus();
      if (null != ps && ps.getPms().equalsIgnoreCase("on")) {
         val = ps.getPmstype();
      }

      return val;
   }

   public static JSONObject getPmsConfigs() {
      try {
         PmsStatus ps = getPmsStatus();
         return new JSONObject(ps.getPmsconfigs());
      } catch (Exception e) {
         LOG.error("get pms configs failed");
         return null;
      }
   }

   public static void updatePmsVersion(String version) {
      PmsStatusManager psm = JpaManager.getPmsStatusManager();
      PmsStatus ps = psm.loadByKey(1);
      if (null != ps) {
         ps.setPmsconnectionversion(version);
         psm.save(ps);
      }
   }

   public static void updatePmsConnectionStatus(String status) {
      LOG.info("updatePmsConnectionStatus:{}", status);
      PmsStatusManager psm = JpaManager.getPmsStatusManager();
      PmsStatus ps = psm.loadByKey(1);
      if (null != ps) {
         ps.setPmsconnectionstatus(status);
         psm.save(ps);
      }
   }

   public static void responseWakeupStatus(String roomId, boolean status) {
      WakeupInfoManager wakeupInfoManager = JpaManager.getWakeupInfoManager();
      WakeupInfo wakeupInfo = wakeupInfoManager.getNearestOtWakeupInfo(roomId);
      TmsUtils tms = getTmsInstance();
      if (wakeupInfo != null) {
         long ot = -wakeupInfoManager.getTimeDifference(wakeupInfo.getWakeupTime());
         if (status) {
            if (wakeupInfo.getStatus() != 2) {
               wakeupInfoManager.updateStatus(wakeupInfo.getId(), 2);
               if (tms != null) {
                  tms.responseWakeup(wakeupInfo.getRoomId(), wakeupInfo.getWakeupTime(), "OK");
               }
            }
         } else if (wakeupInfo.getStatus() == 0) {
            wakeupInfoManager.updateStatus(wakeupInfo.getId(), 4);
            if (tms != null) {
               tms.responseWakeup(wakeupInfo.getRoomId(), wakeupInfo.getWakeupTime(), "BY");
            }
         } else if (ot > 900000L && ot < 1080000L) {
            wakeupInfoManager.updateStatus(wakeupInfo.getId(), 3);
            if (tms != null) {
               tms.responseWakeup(wakeupInfo.getRoomId(), wakeupInfo.getWakeupTime(), "NR");
            }
         }
      }
   }

   private static void deleteGuestInfo(GuestInfo gi) {
      if (null != gi) {
         LOG.info("delete guestinfo:{},{}", gi.getGuestId(), gi.getGuestName());
         GuestInfoManager gim = JpaManager.getGuestInfoManager();
         gim.deleteByKey(gi.getGuestId());
      }
   }

   private static GuestInfo assembleGuestInfo(Element rootElt) {
      String reservationId = rootElt.attributeValue("resno");
      String roomId = rootElt.elementTextTrim("room");
      String guestId = rootElt.elementTextTrim("guestid");
      String title = rootElt.elementTextTrim("title");
      String surname = rootElt.elementTextTrim("last");
      String forename = rootElt.elementTextTrim("first");
      String lang = rootElt.elementTextTrim("lang");
      String language = null;
      String group = rootElt.elementTextTrim("group");
      String arrivalDate = rootElt.elementTextTrim("arrival");
      String departureDate = rootElt.elementTextTrim("departure");
      String tvSetting = rootElt.elementTextTrim("tv");
      String viewbill = rootElt.elementTextTrim("viewbill");
      String expressco = rootElt.elementTextTrim("expressco");
      GuestInfoManager gm = JpaManager.getGuestInfoManager();
      guestId = generateNewGuestId(guestId, roomId);
      GuestInfo gi = gm.loadByKey(guestId);
      if (!isGuestCheckedIn(gi, roomId)) {
         String log = TpvStringUtils.format("guest {} no checked in room {}", guestId, roomId);
         LOG.error(log);
         return null;
      }

      TmsUtils tms = getTmsInstance();
      if (tms != null) {
         language = tms.getMappedLanguage(lang);
      }

      PmsStatus ps = null;
      PmsStatusManager psm = JpaManager.getPmsStatusManager();
      ps = psm.loadByKey(1);
      expressco = getSwitchValueByConfig(expressco, ps.getExpresscheckout());
      viewbill = getSwitchValueByConfig(viewbill, ps.getBillontv());
      gi.setOrderid(reservationId);
      gi.setCheckin("Y");
      gi.setGuestId(guestId);
      gi.setTitle(title);
      gi.setGuestName(forename + " " + surname);
      gi.setGuestLanguage(language);
      gi.setGroupName(group);
      gi.setRoomid(roomId);
      gi.setTvsettingtype(tvSetting);
      gi.setArrivalDate(arrivalDate);
      gi.setDepartureDate(departureDate);
      gi.setViewBill(viewbill);
      gi.setExpressCheckout(expressco);
      return gi;
   }

   public static GuestInfo saveCheckinResults2DB(Element rootElt) throws IOException {
      return saveCheckinResults2DB(rootElt, true);
   }

   public static GuestInfo saveCheckinResults2DB(Element rootElt, boolean checkedin) throws IOException {
      LOG.info("checkin info:{}", rootElt.asXML());
      String reservationId = rootElt.attributeValue("resno");
      String roomId = rootElt.elementTextTrim("room");
      String guestId = rootElt.elementTextTrim("guestid");
      String title = rootElt.elementTextTrim("title");
      String surname = rootElt.elementTextTrim("last");
      String forename = rootElt.elementTextTrim("first");
      String lang = rootElt.elementTextTrim("lang");
      String language = null;
      String group = rootElt.elementTextTrim("group");
      String arrivalDate = rootElt.elementTextTrim("arrival");
      String departureDate = rootElt.elementTextTrim("departure");
      String tvSetting = rootElt.elementTextTrim("tv");
      String viewbill = rootElt.elementTextTrim("viewbill");
      String expressco = rootElt.elementTextTrim("expressco");
      GuestInfoManager gm = JpaManager.getGuestInfoManager();
      guestId = generateNewGuestId(guestId, roomId);
      GuestInfo gi = gm.loadByKey(guestId);
      if (isGuestCheckedIn(gi, roomId) && checkedin) {
         String log = TpvStringUtils.format("guest {} already checked in room {}", guestId, roomId);
         LOG.info(log);
         throw new IOException(log);
      }

      if (isGuestCheckin(roomId) && checkedin) {
         String log = TpvStringUtils.format("the room {} is occupied", roomId);
         LOG.info(log);
         throw new IOException(log);
      }

      ReservationManager resm = JpaManager.getReservationManager();
      Reservation res = resm.loadByKey(reservationId);
      if (null == res) {
         res = new Reservation();
         res.setReservationId(reservationId);
      }

      res.setGuests(forename + " " + surname);
      res.setRooms(roomId);
      res.setStartTime(arrivalDate);
      res.setEndTime(departureDate);
      resm.save(res);
      TmsUtils tms = getTmsInstance();
      if (tms != null) {
         language = tms.getMappedLanguage(lang);
      }

      if (null == gi) {
         gi = new GuestInfo();
      } else if (gi.getOrderid() != null && !gi.getOrderid().equalsIgnoreCase(reservationId)) {
         LOG.info("reservation id changed, remove old reservation:{}", gi.getOrderid());
         JpaManager.getReservationManager().deleteByKey(gi.getOrderid());
      }

      PmsStatus ps = null;
      PmsStatusManager psm = JpaManager.getPmsStatusManager();
      ps = psm.loadByKey(1);
      expressco = getSwitchValueByConfig(expressco, ps.getExpresscheckout());
      viewbill = getSwitchValueByConfig(viewbill, ps.getBillontv());
      gi.setOrderid(reservationId);
      gi.setCheckin("Y");
      gi.setGuestId(guestId);
      gi.setTitle(title);
      gi.setGuestName(forename + " " + surname);
      gi.setGuestLanguage(language);
      gi.setGroupName(group);
      gi.setRoomid(roomId);
      gi.setTvsettingtype(tvSetting);
      gi.setArrivalDate(arrivalDate);
      gi.setDepartureDate(departureDate);
      gi.setViewBill(viewbill);
      gi.setExpressCheckout(expressco);
      gm.save(gi);
      return gi;
   }

   public static GuestInfo saveRefreshResults2DB(Element rootElt) throws IOException {
      String reservationId = rootElt.attributeValue("resno");
      if (reservationId == null) {
         throw new IOException("invalid reservation id: " + reservationId);
      }

      String roomId = rootElt.elementTextTrim("room");
      String guestId = null;
      String title = null;
      String surname = null;
      String forename = null;
      String lang = null;
      String language = null;
      String group = null;
      String arrivalDate = rootElt.elementTextTrim("arrival");
      String departureDate = rootElt.elementTextTrim("departure");
      String tvSetting = rootElt.elementTextTrim("tv");
      String viewbill = rootElt.elementTextTrim("viewbill");
      String expressco = rootElt.elementTextTrim("expressco");
      String occupied = rootElt.elementTextTrim("occupied");
      if ("N".equalsIgnoreCase(occupied)) {
         return null;
      }

      Element guestsElem = rootElt.element("guests");
      Element guestElem = null != guestsElem ? guestsElem.element("guest") : rootElt;
      title = guestElem.elementTextTrim("title");
      surname = guestElem.elementTextTrim("last");
      forename = guestElem.elementTextTrim("first");
      lang = guestElem.elementTextTrim("lang");
      group = guestElem.elementTextTrim("group");
      guestId = guestElem.elementTextTrim("guestid");
      if (guestId == null) {
         throw new IOException("invalid guest id: " + guestId);
      }

      guestId = generateNewGuestId(guestId, roomId);
      TmsUtils tms = getTmsInstance();
      if (tms != null) {
         language = tms.getMappedLanguage(lang);
      }

      GuestInfoManager gm = JpaManager.getGuestInfoManager();
      GuestInfo gi = gm.loadByKey(guestId);
      if (null == gi) {
         gi = new GuestInfo();
      }

      PmsStatus ps = null;
      PmsStatusManager psm = JpaManager.getPmsStatusManager();
      ps = psm.loadByKey(1);
      expressco = getSwitchValueByConfig(expressco, ps.getExpresscheckout());
      viewbill = getSwitchValueByConfig(viewbill, ps.getBillontv());
      gi.setCheckin("Y");
      gi.setGuestId(guestId);
      gi.setTitle(title);
      gi.setGuestName(forename + " " + surname);
      gi.setGuestLanguage(language);
      gi.setGroupName(group);
      gi.setRoomid(roomId);
      gi.setTvsettingtype(tvSetting);
      gi.setArrivalDate(arrivalDate);
      gi.setDepartureDate(departureDate);
      gi.setViewBill(viewbill);
      gi.setExpressCheckout(expressco);
      gm.save(gi);
      setMessageUpdated();
      ReservationManager resm = JpaManager.getReservationManager();
      Reservation res = resm.loadByKey(reservationId);
      if (null == res) {
         res = new Reservation();
         res.setReservationId(reservationId);
      }

      res.setGuests(forename + " " + surname);
      res.setRooms(roomId);
      res.setStartTime(arrivalDate);
      res.setEndTime(departureDate);
      resm.save(res);
      return gi;
   }

   public static Message saveMessageTextResults2DB(Element rootElt) {
      LOG.info("save message text:{}", rootElt.asXML());
      String roomId = rootElt.elementTextTrim("room");
      String datetime = rootElt.elementTextTrim("datetime");
      String msgtext = rootElt.elementTextTrim("msgtext");
      String status = rootElt.elementTextTrim("status");
      String msgid = rootElt.elementTextTrim("msgid");
      Message msg = null;
      MessageManager msgmgr = JpaManager.getMessageManager();

      try {
         SimpleDateFormat formatter = TpvDateUtils.getMessageTimeFormat();
         Date st = null;
         if (msgid != null) {
            msg = msgmgr.loadByKey(msgid);
         }

         if (msg == null) {
            msg = msgmgr.createMessage();
            msg.setId(msgid);
            st = formatter.parse(datetime);
            msg.setTimeSend(formatter.format(st));
            msg.setContent(msgtext);
            msg.setGuestIds(roomId);
            msg.setTitle("");
            if (status == null) {
               status = "new";
            }
         } else {
            msg.setStatus(status);
         }

         msgmgr.save(msg);
      } catch (ParseException e) {
         LOG.error(e.getMessage(), e);
      }

      if (status == null || status.equalsIgnoreCase("new")) {
         sendMsg2TV(roomId);
      }

      setMessageUpdated();
      return msg;
   }

   private static void updatePmsSyncStatus(Devices tv, PmsUtils.PmsSyncStatus syncStatus) {
      tv.setPmsSyncStatus(syncStatus.ordinal());
      JpaManager.getDevicesManager().updatePmsSyncNewStatus(tv);
   }

   public static void sendData2TV(Devices tv, String data) throws Exception {
      try {
         if (!PlatformUtils.isAsta2016Up(tv.getType()) && "PMSService".equalsIgnoreCase(new JSONObject(data).optString("Fun"))) {
            LOG.warn("{} no support PMSService", tv.getType());
            updatePmsSyncStatus(tv, PmsUtils.PmsSyncStatus.Synced);
         } else {
            updatePmsSyncStatus(tv, PmsUtils.PmsSyncStatus.Syncing);
            String resp = JAPITUtils.sendJapitCommand(tv, data, 10000);
            if (!StringUtils.isEmpty(resp) && resp.contains("{")) {
               JSONObject jsonResp = new JSONObject(resp);
               if ("Error".equalsIgnoreCase(jsonResp.optString("Fun"))) {
                  throw new IOException("TV can not process PMS Command");
               }
            }

            CmndMetricsTask.writeIPTVMetricsLog(tv, new JSONObject(data));
            updatePmsSyncStatus(tv, PmsUtils.PmsSyncStatus.Synced);
         }
      } catch (Exception e) {
         LOG.error("send Pms message to TV {} failed, pms Sync=false", tv.getTvuniqueid());
         updatePmsSyncStatus(tv, PmsUtils.PmsSyncStatus.NeedSync);
         throw e;
      }
   }

   private static void sendData2Room(String roomId, String data) throws IOException {
      sendData2Room(roomId, t -> data);
   }

   private static void sendData2Room(String roomId, Function<Devices, String> function) throws IOException {
      sendDatas2Room(roomId, t -> {
         String data = function.apply(t);
         List<String> datas = new ArrayList<>();
         datas.add(data);
         return datas;
      });
   }

   private static void sendDatas2Room(String roomId, Function<Devices, List<String>> function) throws IOException {
      List<Devices> tvs = getIPTVForCheckRoom(roomId);
      int exceptionCount = 0;
      String message = "";
      if (tvs.isEmpty()) {
         int var11 = true;
         message = "roomId " + roomId + " no iptv device found!";
         LOG.warn(message);
      } else {
         for (Devices tv : tvs) {
            List<String> datas = function.apply(tv);
            if (!ensureTvJapitWorking(tv, datas)) {
               LOG.info("tv {} offline, set pms need sync", tv.getId());
               updatePmsSyncStatus(tv, PmsUtils.PmsSyncStatus.NeedSync);
            } else {
               try {
                  for (String data : datas) {
                     sendData2TV(tv, data);
                  }
               } catch (Exception e) {
                  LOG.info("{} error:{}", tv.getTvuniqueid(), e.getMessage());
                  message = e.getMessage();
                  exceptionCount++;
               }
            }
         }

         if (exceptionCount > 0) {
            if (exceptionCount > 1) {
               message = exceptionCount == tvs.size() ? "Send command to TV failed" : "Send command to some tvs failed";
            }

            throw new IOException(message);
         }
      }
   }

   private static boolean ensureTvJapitWorking(Devices tv, List<String> datas) {
      if (tv.isOnline()) {
         return true;
      }

      if (PlatformUtils.isSupportWakeupOnLan(tv.getType())) {
         for (String data : datas) {
            if (StringUtils.containsIgnoreCase(data, "PowerService")
               && StringUtils.containsIgnoreCase(data, "ToPowerState")
               && StringUtils.containsIgnoreCase(data, "On")) {
               return TVPowerManager.wakeupTvOnLan(tv);
            }
         }
      }

      return false;
   }

   private static void syncPmsStatus(Devices tv) {
      LOG.info("sync pms status to tv=<{}>, ip=<{}>", tv.getTvuniqueid(), tv.getTvipaddress());
      String pmsData = null;
      if (isGuestCheckin(tv.getTvroomid())) {
         GuestInfo gi = getGuestInfoByRoomId(tv.getTvroomid());
         CheckInVO checkinVO = getCheckInVO(gi);
         if (!PlatformUtils.isMasf2019Up(tv.getType())) {
            processCheckInVOForASTA(checkinVO);
         }

         pmsData = PmsJapitCommand.getCheckIn(checkinVO);
      } else {
         pmsData = PmsJapitCommand.getCheckOut();
      }

      try {
         sendData2TV(tv, pmsData);
      } catch (Exception e) {
         LOG.error("syncPmsStatus to TV {} failed,{}", tv.getTvuniqueid(), e.getMessage());
      }
   }

   private static boolean hasRFTVForRoom(String roomId) {
      boolean hasRF = false;
      List<Devices> tvs = getTVsForRoom(roomId);

      for (Devices tv : tvs) {
         if (tv.isRFDevice()) {
            hasRF = true;
            break;
         }
      }

      if (tvs.isEmpty() && isAutoCreateTV()) {
         LOG.info("tvlist is empty, auto create RF TV for roomid:{}", roomId);
         createRFTV(roomId, getAutoRFPlatform());
         hasRF = true;
      }

      return hasRF;
   }

   public static void checkin2TV(GuestInfo gi, boolean needWakeup) throws IOException {
      if (gi == null) {
         LOG.error("guestinfo is null");
      } else {
         LOG.info("checkin2TV room:{},guestid:{}", gi.getRoomid(), gi.getGuestId());
         String roomid = gi.getRoomid();
         if (hasRFTVForRoom(roomid)) {
            PlayoutUtils.addPMSAction(PmsUtils.PmsAction.CheckIn, String.valueOf(roomid));
         }

         CheckInVO checkinVO = getCheckInVO(gi);
         String respData = PmsJapitCommand.getCheckIn(checkinVO);
         sendDatas2Room(roomid, t -> {
            String checkinData = respData;
            List<String> datas = new ArrayList<>();
            if (!PlatformUtils.isMasf2019Up(t.getType())) {
               processCheckInVOForASTA(checkinVO);
               checkinData = PmsJapitCommand.getCheckIn(checkinVO);
            }

            datas.add(checkinData);
            if (needWakeup && "On".equalsIgnoreCase(getPmsStatus().getAutoWakeUpTv())) {
               String standbyJapit = TVPowerManager.changePowerService("On");
               datas.add(standbyJapit);
            }

            return datas;
         });
      }
   }

   public static boolean setAutoWakeUpTv(boolean autoWakeup) {
      LOG.info("change AutoWakeUpTv to {}", autoWakeup);
      PmsStatus ps = getPmsStatus();
      String wakeup = ps.getAutoWakeUpTv();
      String newWakeup = autoWakeup ? "On" : "Off";
      if (!newWakeup.equalsIgnoreCase(wakeup)) {
         ps.setAutoWakeUpTv(newWakeup);
         JpaManager.getPmsStatusManager().save(ps);
      }

      return wakeup.equalsIgnoreCase("On");
   }

   public static void createRFTV(String roomid, String platformName) {
      LOG.info("create RF TV:{}", roomid);
      DevicesManager tvmanager = JpaManager.getDevicesManager();
      Devices tv = new Devices(true);
      String rid = roomid;
      tv.setTvroomid(rid);
      String uuid = UUID.randomUUID().toString().replace("-", "");
      String tvSerialNumber = "CMNDRF" + uuid.substring(0, 12);
      String tvMACAddress = TpvStringUtils.getTVMAVAddress(uuid.substring(12, 24));
      String tvUniqueId = tvSerialNumber + tvMACAddress.replace(":", "");
      String ctn = PlatformUtils.getRFTVDefaultCTN(platformName);
      tv.setTvmodelnumber(ctn);
      tv.setId(tvUniqueId);
      tv.setTvname(TpvDateUtils.getTvNameFormateString());
      tv.setType(platformName);
      tv.setTvipaddress("RF");
      tv.setPowerstatus("On");
      String datetime = TpvDateUtils.getDateTime();
      tv.setCreateddate(datetime);
      tv.setLastonline(datetime);
      tv.setTvuniqueid(tvUniqueId);
      tv.setTvserialnumber(tvSerialNumber);
      tv.setTvmacaddress(tvMACAddress);
      tvmanager.save(tv);
   }

   private static List<Billitem> getBillItems(String roomid) {
      BillitemManager bim = JpaManager.getBillitemManager();
      return roomid == null ? bim.findBillitemsRoomIdIsNull() : bim.findBillitemsByRoomId(roomid);
   }

   private static JSONArray processBillItemsForASTA(JSONArray billitems) {
      for (int i = 0; i < billitems.length(); i++) {
         JSONObject jsObj = billitems.getJSONObject(i);
         jsObj.put("BillItemDisplayName", TpvStringUtils.unicode2String(jsObj.optString("BillItemDisplayName")));
      }

      return billitems;
   }

   private static boolean isGuestCheckedIn(GuestInfo gi, String roomid) {
      return StringUtils.isEmpty(roomid) ? false : gi != null && gi.getCheckin().equalsIgnoreCase("Y") && gi.getRoomid().equalsIgnoreCase(roomid);
   }

   public static boolean isGuestCheckin(String roomid) {
      List<GuestInfo> guestInfos = getGuestInfosByRoomId(roomid);
      boolean checkin = false;

      for (GuestInfo guestInfo : guestInfos) {
         if (guestInfo.getCheckin().equalsIgnoreCase("Y")) {
            checkin = true;
            break;
         }
      }

      return checkin;
   }

   public static boolean isGuestViewBill(String roomid) {
      List<GuestInfo> guestInfos = getGuestInfosByRoomId(roomid);
      boolean viewBill = false;

      for (GuestInfo guestInfo : guestInfos) {
         if ("True".equalsIgnoreCase(guestInfo.getViewBill())) {
            viewBill = true;
            break;
         }
      }

      return viewBill;
   }

   public static void sendBill2TV(GuestInfo guest) {
      if (null == guest) {
         LOG.error("guest is null");
      } else {
         LOG.info("sendBill2TV");
         String roomid = guest.getRoomid();

         try {
            sendDatas2Room(roomid, t -> changeGuestBill(guest, t));
         } catch (IOException e) {
            LOG.error(e.getMessage(), e);
         }
      }
   }

   public static void responseBill(Devices tv) throws IOException {
      String bill = getPmsStatus().getBillontv();
      if ("Off".equalsIgnoreCase(bill)) {
         throw new IOException("CMND Billontv option is off, Cannot get bill!!");
      }

      TmsUtils tms = getTmsInstance();
      if (null != tms && tms.isInstantBill() && !getPmsStatus().isDemoBill()) {
         LOG.info("TmsUtils not null, send bill request to tms");
         tms.requestBill(tv.getTvroomid());
      } else {
         GuestInfo guest = getGuestInfoByRoomId(tv.getTvroomid());
         sendBill2TV(guest);
      }
   }

   private static JSONArray getBillItemArray(String roomId) {
      JSONArray array = new JSONArray();
      SimpleDateFormat dateFormat1 = TpvDateUtils.getSimpleDateFormatWithEnglishLocale("dd/MM/yyyy");
      Date date = null;
      List<Billitem> bis = getBillItems(roomId);
      if (null != bis) {
         int id = 1;

         for (Billitem b : bis) {
            if (!"No".equalsIgnoreCase(b.getDisplayFlag())) {
               try {
                  date = dateFormat1.parse(b.getBillItemDate());
               } catch (ParseException e) {
                  LOG.error("parse date failed:{}", b.getBillItemDate());
                  continue;
               }

               JSONObject jsObj = new JSONObject();
               jsObj.put("ID", id++);
               jsObj.put("BillItemDisplayName", TpvStringUtils.string2Unicode(b.getBillItemDisplayName()));
               jsObj.put("BillItemAmount", b.getBillItemAmount());
               jsObj.put("BillItemDate", dateFormat1.format(date));
               jsObj.put("BillItemTime", b.getBillItemTime());
               array.put(jsObj);
            }
         }
      }

      return array;
   }

   public static JSONObject getBillObject(Devices tv) {
      JSONObject billObject = null;
      String roomId = tv.getTvroomid();
      if (!isGuestCheckin(roomId)) {
         LOG.warn("Guest not checkin, cannot get bill");
      } else if (!isGuestViewBill(roomId)) {
         LOG.warn("Guest ViewBill is off, cannot get bill");
      } else {
         String bill = getPmsStatus().getBillontv();
         if ("Off".equalsIgnoreCase(bill)) {
            LOG.warn("CMND Billontv option is off, Cannot get bill!!");
         } else {
            boolean isDemo = getPmsStatus().isDemoBill();
            TmsUtils tms = getTmsInstance();
            if (null != tms && tms.isInstantBill() && !isDemo) {
               LOG.info("TmsUtils not null, send bill request to tms");
               tms.requestBill(tv.getTvroomid());
            } else {
               GuestInfo gi = getGuestInfoByRoomId(roomId);
               JSONArray array = getBillItemArray(isDemo ? null : String.valueOf(roomId));
               if (!PlatformUtils.isMasf2019Up(tv.getType())) {
                  processBillItemsForASTA(array);
               }

               billObject = PmsJapitCommand.getGuestBill(tv, gi, array);
            }
         }
      }

      return billObject;
   }

   private static List<String> changeGuestBill(GuestInfo guest, Devices tv) {
      boolean isDemo = getPmsStatus().isDemoBill();
      JSONArray array = getBillItemArray(isDemo ? null : String.valueOf(guest.getRoomid()));
      if (!PlatformUtils.isMasf2019Up(tv.getType())) {
         processBillItemsForASTA(array);
      }

      return PmsJapitCommand.getGuestBills(tv, guest, array);
   }

   public static String getCurrency() {
      String currency = getPmsStatus().getCurrency();
      String currencyProp = getPmsStatus().getCurrencyPreference();
      int i = currency.indexOf(" ");
      if ("CurrencySymbol".equalsIgnoreCase(currencyProp)) {
         if (-1 != i) {
            currency = currency.substring(0, i);
         } else {
            currency = "";
         }
      } else {
         if (-1 != i) {
            currency = currency.substring(i + 1, currency.length());
         }

         currency = TpvStringUtils.string2Unicode(currency);
      }

      return currency;
   }

   public static String generateNewGuestId(String guestId, String roomId) {
      PmsStatusManager psm = JpaManager.getPmsStatusManager();
      PmsStatus ps = psm.loadByKey(1);
      boolean isAllowMultipleRooms = "Yes".equalsIgnoreCase(ps.getAllowMultipleRoom());
      return roomId != null && isAllowMultipleRooms ? guestId + "_" + roomId : guestId;
   }

   public static String extractGuestId(String newGuestId) {
      return newGuestId.contains("_") ? newGuestId.split("_")[0] : newGuestId;
   }

   public static void updateAllowMultipleStatus(String status) {
      PmsStatusManager psm = JpaManager.getPmsStatusManager();
      PmsStatus ps = psm.loadByKey(1);
      if (!status.equalsIgnoreCase(ps.getAllowMultipleRoom())) {
         ps.setAllowMultipleRoom(status);
         psm.save(ps);
      }
   }

   public static void updateLimitNetworkInterface(String networkInterface) {
      PmsStatusManager psm = JpaManager.getPmsStatusManager();
      PmsStatus ps = psm.loadByKey(1);
      if (!networkInterface.equalsIgnoreCase(ps.getLimitNetworkInterface())) {
         ps.setLimitNetworkInterface(networkInterface);
         ps.setPmsconnectionstatus("");
         psm.save(ps);
         createTmsInstace(ps.getPmstype());
      }
   }

   public static String getLimitNetworkIpAddress() {
      PmsStatusManager psm = JpaManager.getPmsStatusManager();
      PmsStatus ps = psm.loadByKey(1);
      String ipaddress = ps.getLimitNetworkInterface();
      if (ipaddress.contains("/")) {
         ipaddress = ipaddress.split("/")[0];
      }

      return ipaddress;
   }

   public static GuestInfo saveCheckoutResults2DB(Element rootElt) {
      String roomId = rootElt.elementTextTrim("room");
      String guestId = rootElt.elementTextTrim("guestid");
      GuestInfoManager gm = JpaManager.getGuestInfoManager();
      GuestInfo gi = null;
      if (!StringUtils.isEmpty(guestId)) {
         guestId = generateNewGuestId(guestId, roomId);
         gi = gm.loadByKey(guestId);
      }

      if (null != gi && "Y".equalsIgnoreCase(gi.getCheckin())) {
         gi.setCheckin("N");
         gm.save(gi);
         setUpdatedGuestInfo(gi.getGuestId());
      }

      if (gi == null && roomId != null) {
         LOG.info("guest not found for guestid:{}", guestId);
         gi = getGuestInfoByRoomId(roomId);
      }

      return gi;
   }

   private static void checkout2TV(String roomid) throws IOException {
      LOG.info("checkout2TV for room:{}", roomid);
      if (hasRFTVForRoom(roomid)) {
         PlayoutUtils.addPMSAction(PmsUtils.PmsAction.CheckOut, String.valueOf(roomid));
      }

      String respData = PmsJapitCommand.getCheckOut();
      String autoSwitchOff = getPmsStatus().getAutoSwitchOffTv();
      String respStandbyData = TVPowerManager.changePowerService("Standby");
      sendDatas2Room(roomid, t -> {
         List<String> datas = new ArrayList<>();
         datas.add(respData);
         if ("On".equalsIgnoreCase(autoSwitchOff)) {
            datas.add(respStandbyData);
         }

         return datas;
      });
   }

   public static void changeGuestRoom(String roomold, String room) {
      LOG.info("changeGuestRoom for room:{} to {}", roomold, room);
      GuestInfoManager gm = JpaManager.getGuestInfoManager();
      ReservationManager rm = JpaManager.getReservationManager();

      try {
         GuestInfo gi = null;
         List<GuestInfo> guestInfos = gm.findGuestInfosByRoomid(roomold);
         if (guestInfos.isEmpty()) {
            LOG.error("guest not exists for old room:{}", roomold);
            return;
         }

         gi = guestInfos.get(0);
         checkout2TV(gi.getRoomid());
         gm.deleteByKey(gi.getGuestId());
         gi.setRoomid(room);
         gi.setGuestId(generateNewGuestId(extractGuestId(gi.getGuestId()), room));
         gm.save(gi);
         if (gi.getOrderid() != null) {
            Reservation r = rm.loadByKey(gi.getOrderid());
            if (r != null) {
               r.setRooms(room);
               rm.save(r);
            }
         }

         List<Message> messages = JpaManager.getMessageManager().findMessageByGuestIds(roomold);
         messages.stream().forEach(t -> {
            t.setGuestIds(room);
            JpaManager.getMessageManager().save(t);
         });
         checkin2TV(gi, false);
         setUpdatedGuestInfo(gi.getGuestId());
         setMessageUpdated();
         CastServerUtils.sendChangeRoomNoticeToOpenApiServer(gi, roomold, room);
      } catch (NumberFormatException | IOException e) {
         LOG.error(e.getMessage(), e);
      }
   }

   public static GuestInfo saveBillBalance2DB(Element rootElt) {
      String roomId = rootElt.elementTextTrim("room");
      String balance = rootElt.elementTextTrim("balance");
      String totalDateTime = rootElt.elementTextTrim("totalDateTime");
      if (totalDateTime == null) {
         totalDateTime = TpvDateUtils.formatLocalDate(new Date(), "dd/MM/yyyy HH:mm:ss");
      }

      return storageBalance2GuestInfo(roomId, balance, totalDateTime);
   }

   public static void saveBillItem2DB(Element rootElt) {
      String roomId = rootElt.elementTextTrim("room");
      String billId = rootElt.elementTextTrim("billid");
      String description = rootElt.elementTextTrim("description");
      String charge = rootElt.elementTextTrim("charge");
      String datetime = rootElt.elementTextTrim("datetime");
      String displayFlag = rootElt.elementTextTrim("displayflag");
      String date = "";
      String time = "";
      String[] chunks = datetime.split(" ");
      if (chunks.length > 1) {
         date = datetime.split(" ")[0];
         time = datetime.split(" ")[1];
      }

      BillitemManager bim = JpaManager.getBillitemManager();
      Billitem bi = null;
      if (billId != null && !billId.isEmpty()) {
         bi = bim.loadByKey(billId);
      } else {
         billId = UUID.randomUUID().toString();
      }

      if (bi == null) {
         bi = new Billitem();
         bi.setID(billId);
      }

      bi.setBillItemDisplayName(description);
      bi.setBillItemAmount(charge);
      bi.setBillItemDate(date);
      bi.setBillItemTime(time);
      bi.setRoomId(roomId);
      bi.setDisplayFlag("".equalsIgnoreCase(displayFlag) ? "Yes" : displayFlag);
      bim.save(bi);
   }

   public static String getBalance(String roomId) {
      List<GuestInfo> gis = JpaManager.getGuestInfoManager().findGuestInfosByRoomid(roomId);
      GuestInfo gi = null;
      if (null != gis && !gis.isEmpty()) {
         gi = gis.get(0);
      }

      return gi != null && gi.getBalance() != null ? gi.getBalance() : "0";
   }

   public static void deletePreBillItems(String roomId) {
      BillitemManager bm = JpaManager.getBillitemManager();

      for (Billitem bi : bm.findBillitemsByRoomId(roomId)) {
         bm.deleteByKey(bi.getID());
      }
   }

   public static GuestInfo storageBalance2GuestInfo(String roomId, String balance, String totalDateTime) {
      GuestInfoManager gim = JpaManager.getGuestInfoManager();
      List<GuestInfo> gis = gim.findGuestInfosByRoomid(roomId);
      if (gis.isEmpty()) {
         return null;
      }

      for (GuestInfo gi : gis) {
         gi.setBalance(balance);
         gi.setTotalBillDateTime(totalDateTime);
         gim.save(gi);
      }

      return gis.get(0);
   }

   public static GuestInfo getGuestInfoByRoomId(String roomId) {
      List<GuestInfo> gis = getGuestInfosByRoomId(roomId);
      return !gis.isEmpty() ? gis.get(0) : null;
   }

   private static List<GuestInfo> getGuestInfosByRoomId(String roomId) {
      GuestInfoManager gm = JpaManager.getGuestInfoManager();
      return gm.findGuestInfosByRoomid(roomId);
   }

   public static void processExpressCheckoutFromPms(Element rootElt) {
      LOG.info("processExpressCheckoutFromPms");
      String roomId = rootElt.elementTextTrim("room");
      String status = rootElt.elementTextTrim("status");

      try {
         if (status.equalsIgnoreCase("true")) {
            CmndMetricsTask.setExpressCheckOut(true);
            processCheckout(roomId);
            CmndMetricsTask.resetExpressCheckOut();
         } else {
            String respCheckoutData = PmsJapitCommand.getExpressCheckOutError("Others");
            sendData2Room(roomId, respCheckoutData);
         }
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
         CmndMetricsTask.resetExpressCheckOut();
      }
   }

   public static void processAfterCheckout(String roomId) {
      LOG.info("processAfterCheckout {}", roomId);
      cleanPmsData(roomId);
   }

   public static void cleanPmsData(String roomId) {
      if (!StringUtils.isEmpty(roomId)) {
         LOG.info("cleanPmsData {}", roomId);

         for (GuestInfo gi : getGuestInfosByRoomId(roomId)) {
            deleteGuestInfo(gi);
            setUpdatedGuestInfo(gi.getGuestId());
         }

         cleanMessages(roomId);
         deletePreBillItems(roomId);
      }
   }

   public static GuestInfo createGuest(String guestName, String groupName, String roomId, String roomType, String guestLang) {
      LOG.info("create new guest");
      int id1 = rand.nextInt(99999);
      GuestInfo guestInfo = new GuestInfo();
      guestInfo.setGuestId(generateNewGuestId(String.valueOf(id1), roomId));
      guestInfo.setTitle("");
      guestInfo.setGuestName(guestName == null ? "" : guestName);
      guestInfo.setGroupName(groupName == null ? "" : groupName);
      guestInfo.setRoomid(roomId);
      guestInfo.setCheckin("N");
      guestInfo.setGuestLanguage(guestLang == null ? "eng" : guestLang);
      guestInfo.setRoomType(roomType);
      CheckInVO checkinVo = getCheckInVO(guestInfo);
      guestInfo.setExpressCheckout(checkinVo.getExpressCheckout());
      guestInfo.setViewBill(checkinVo.getViewBill());
      guestInfo.setViewMessage(checkinVo.isMessagesEnabled() ? "True" : "False");
      guestInfo.setDoNotDisturb(checkinVo.getDonotDisturb());
      JpaManager.getGuestInfoManager().save(guestInfo);
      return guestInfo;
   }

   public static GuestInfo updateGuestInfo(String roomId, PmsUtils.GuestInfoFieldType fieldType, String fieldValue) throws IOException {
      LOG.info("update GuestInfo {} to {} for room:{}", fieldType, fieldValue, roomId);
      checkPmsEnable();
      GuestInfoManager guestInfoMgr = JpaManager.getGuestInfoManager();
      List<GuestInfo> guestInfoList = guestInfoMgr.findGuestInfosByRoomid(roomId);
      if (guestInfoList.isEmpty()) {
         throw new IOException("roomId( " + roomId + " ) no exists");
      }

      GuestInfo guestInfo = guestInfoList.get(0);
      switch (fieldType) {
         case GuestName:
            if (!fieldValue.equalsIgnoreCase(guestInfo.getGuestName())) {
               guestInfo.setGuestName(fieldValue);
               guestInfoMgr.save(guestInfo);
               if (guestInfo.getOrderid() != null) {
                  ReservationManager reservationMgr = JpaManager.getReservationManager();
                  Reservation reservation = reservationMgr.loadByKey(guestInfo.getOrderid());
                  if (reservation != null && fieldValue.equalsIgnoreCase(reservation.getGuests())) {
                     reservation.setGuests(fieldValue);
                     reservationMgr.save(reservation);
                  }
               }

               if (guestInfo.getCheckin().equalsIgnoreCase("Y")) {
                  CheckInVO checkinVO = getCheckInVO(guestInfo);
                  sendData2Room(guestInfo.getRoomid(), tv -> {
                     if (!PlatformUtils.isMasf2019Up(tv.getType())) {
                        processCheckInVOForASTA(checkinVO);
                     }

                     return PmsJapitCommand.getUpdateGuestDetails(checkinVO);
                  });
               }
            }
            break;
         case GuestLanguage:
            if (!fieldValue.equalsIgnoreCase(guestInfo.getGuestLanguage())) {
               guestInfo.setGuestLanguage(fieldValue);
               guestInfoMgr.save(guestInfo);
               if (hasRFTVForRoom(roomId)) {
                  PlayoutUtils.addPMSAction(PmsUtils.PmsAction.LanguageChange, roomId);
               }

               sendUpdateGuestPreference(guestInfo);
               TriggerUtils.executePMSTrigger(PmsUtils.PmsAction.LanguageChange, roomId, fieldValue);
            }
            break;
         case GuestDepartureDate:
            if (!fieldValue.equalsIgnoreCase(guestInfo.getDepartureDate())) {
               guestInfo.setDepartureDate(fieldValue);
               guestInfoMgr.save(guestInfo);
               CheckInVO checkinVO = getCheckInVO(guestInfo);
               String respData = PmsJapitCommand.getUpdateRoomStatus(checkinVO);
               sendData2Room(roomId, respData);
            }
      }

      return guestInfo;
   }

   public static GuestInfo renameGuest(String guestId, String guestName, String roomId) throws IOException {
      LOG.info("renameGuest {} to {} for room:{}", guestId, guestName, roomId);
      checkPmsEnable();
      GuestInfoManager guestInfoMgr = JpaManager.getGuestInfoManager();
      if ("None".equalsIgnoreCase(guestId)) {
         LOG.info("guest not exist, create new guest");
         return createGuest(guestName, null, roomId, null, null);
      }

      List<GuestInfo> guestInfoList = guestInfoMgr.findByBeginGuestid(extractGuestId(guestId));
      if (guestInfoList.isEmpty()) {
         throw new IOException("guest not exist,id=" + guestId);
      }

      for (GuestInfo guestInfo : guestInfoList) {
         String orgName = guestInfo.getGuestName();
         ReservationManager reservationMgr = JpaManager.getReservationManager();
         if (guestInfo.getOrderid() != null) {
            Reservation reservation = reservationMgr.loadByKey(guestInfo.getOrderid());
            if (reservation != null) {
               String replaceName = reservation.getGuests().replace(orgName, guestName);
               reservation.setGuests(replaceName);
               reservationMgr.save(reservation);
            }
         }

         guestInfo.setGuestName(guestName);
         guestInfoMgr.save(guestInfo);
         if (guestInfo.getCheckin().equalsIgnoreCase("Y")) {
            CheckInVO checkinVO = getCheckInVO(guestInfo);
            sendData2Room(guestInfo.getRoomid(), tv -> {
               if (!PlatformUtils.isMasf2019Up(tv.getType())) {
                  processCheckInVOForASTA(checkinVO);
               }

               return PmsJapitCommand.getUpdateGuestDetails(checkinVO);
            });
         }
      }

      return guestInfoList.get(0);
   }

   public static GuestInfo renameGroup(String guestId, String groupName, String roomId) throws IOException {
      checkPmsEnable();
      LOG.info("renameGroup");
      GuestInfoManager guestInfoMgr = JpaManager.getGuestInfoManager();
      GuestInfo guestInfo = guestInfoMgr.loadByKey(guestId);
      if (guestInfo == null) {
         guestInfo = createGuest(null, groupName, roomId, null, null);
         LOG.info("guest not exits, create new by groupname");
      } else {
         guestInfo.setGroupName(groupName);
         guestInfoMgr.save(guestInfo);
      }

      if (guestInfo.getCheckin().equalsIgnoreCase("Y")) {
         CheckInVO checkinVO = getCheckInVO(guestInfo);
         sendData2Room(guestInfo.getRoomid(), tv -> {
            if (!PlatformUtils.isMasf2019Up(tv.getType())) {
               processCheckInVOForASTA(checkinVO);
            }

            return PmsJapitCommand.getUpdateGuestDetails(checkinVO);
         });
      }

      String roomid = String.valueOf(guestInfo.getRoomid());
      TriggerUtils.executePMSTrigger(PmsUtils.PmsAction.GroupChange, roomid, groupName);
      return guestInfo;
   }

   public static void processCheckout(String roomid) throws IOException {
      checkPmsEnable();
      if (!StringUtils.isEmpty(roomid)) {
         LOG.info("processCheckout {}", roomid);
         updatePmsConnectionInfo(roomid);
         getGuestInfosByRoomId(roomid).forEach(CastServerUtils::sendCheckoutNoticeToOpenApiServer);
         TriggerUtils.executePMSTrigger(PmsUtils.PmsAction.CheckOut, roomid, null);
         processAfterCheckout(String.valueOf(roomid));

         try {
            checkout2TV(roomid);
         } catch (IOException e) {
            LOG.warn(e.getMessage());
            throw e;
         }
      }
   }

   public static void processCheckin(String guestId) throws IOException {
      processCheckin(guestId, null);
   }

   public static GuestInfo getGuest(String guestId) throws IOException {
      GuestInfo gi = JpaManager.getGuestInfoManager().loadByKey(guestId);
      if (gi == null) {
         throw new IOException("guest not exist,id=" + guestId);
      } else {
         return gi;
      }
   }

   private static boolean isSupportRoomType() {
      SIConfig siconfig = JpaManager.getSIConfigManager().getSIConfig();
      return StringUtils.equalsIgnoreCase(siconfig.getSupportRoomtype(), Boolean.TRUE.toString());
   }

   public static void processCheckin(String guestId, String checkoutDateTime) throws IOException {
      checkPmsEnable();
      LOG.info("processCheckin {}", guestId);
      GuestInfo gi = getGuest(guestId);
      gi.setCheckin("Y");
      if (StringUtils.isEmpty(gi.getArrivalDate())) {
         gi.setArrivalDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
      }

      if (StringUtils.isEmpty(gi.getCheckinTime())) {
         gi.setCheckinTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
      }

      if (StringUtils.isNoneBlank(checkoutDateTime)) {
         Date tempData = TpvDateUtils.parseDateString(checkoutDateTime, "yyyy-MM-dd HH:mm");
         if (tempData != null) {
            gi.setDepartureDate(new SimpleDateFormat("dd/MM/yyyy").format(tempData));
            gi.setCheckoutTime(checkoutDateTime);
         }
      }

      JpaManager.getGuestInfoManager().save(gi);
      setUpdatedGuestInfo(guestId);
      updatePmsConnectionInfo(gi.getRoomid());
      CastServerUtils.sendCheckInNoticeToOpenApiServer(gi);
      IOException a = null;

      try {
         checkin2TV(gi, true);
      } catch (IOException e) {
         a = e;
      }

      String roomId = gi.getRoomid();
      TriggerUtils.executePMSTrigger(PmsUtils.PmsAction.CheckIn, roomId, null);
      if (gi.getGroupName() != null) {
         TriggerUtils.executePMSTrigger(PmsUtils.PmsAction.GroupChange, roomId, gi.getGroupName());
      }

      if (gi.getGuestLanguage() != null) {
         TriggerUtils.executePMSTrigger(PmsUtils.PmsAction.LanguageChange, roomId, gi.getGuestLanguage());
      }

      if (isSupportRoomType() && gi.getRoomType() != null) {
         TriggerUtils.executePMSTrigger(PmsUtils.PmsAction.RoomType, roomId, gi.getRoomType());
      }

      if (a != null) {
         throw a;
      }
   }

   private static void updatePmsConnectionInfo(String roomId) {
      PmsStatus ps = getPmsStatus();
      SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
      ps.setPmsconnectioninfo(String.format("%s - %s", df.format(new Date()), roomId));
      JpaManager.getPmsStatusManager().save(ps);
   }

   public static GuestInfo switchCheckInStatus(String guestId, boolean toCheckin) throws IOException {
      checkPmsEnable();
      LOG.info("switchCheckInStatus guestId={} to {}", guestId, toCheckin ? "checkin" : "checkout");
      GuestInfo gi = getGuest(guestId);
      boolean checkin = gi.getCheckin().equalsIgnoreCase("Y");
      if (toCheckin == checkin) {
         LOG.info("guest {} already {}", guestId, toCheckin ? "checkin" : "checkout");
         return gi;
      } else if (checkin) {
         processCheckout(gi.getRoomid());
         return null;
      } else {
         processCheckin(guestId);
         return getGuest(guestId);
      }
   }

   public static void deleteMessage(String id) {
      LOG.info("deleteMessage {}", id);
      JpaManager.getMessageManager().deleteByKey(id);
   }

   private static void cleanMessages(String roomId) {
      LOG.info("cleanMessages {}", roomId);
      JpaManager.getMessageManager().deleteByRoomId(roomId);
   }

   public static void setMessageUpdated() {
      PollingWebSocket.notifyMessageUpdate();
   }

   public static void setUpdatedGuestInfo(String guestId) {
      LOG.info("set guestinfo updated:{}", guestId);
      PollingWebSocket.notifyGuestInfoUpdate(guestId);
   }

   private static void checkPmsEnable() throws IOException {
      if (!isPmsEnabled()) {
         throw new IOException("PMS is disabled.");
      }
   }

   public static GuestInfo switchDoNotDisturb(String id) throws IOException {
      checkPmsEnable();
      LOG.info("switchDoNotDisturb {}", id);
      GuestInfo gi = getGuest(id);
      PmsStatus ps = getPmsStatus();
      String pmsStatusDoNotDisturb = ps.getDoNotDisturb();
      if ("Off".equalsIgnoreCase(pmsStatusDoNotDisturb)) {
         throw new IOException("Cannot turn DoNotDisturb on TV ON: DootDisturb On TV is off in PMS Admin tab");
      }

      boolean doNotDisturb = "true".equalsIgnoreCase(gi.getDoNotDisturb());
      String donotDisturbFlag = doNotDisturb ? "false" : "true";
      gi.setDoNotDisturb(donotDisturbFlag);
      JpaManager.getGuestInfoManager().save(gi);
      sendUpdateGuestPreference(gi);
      return gi;
   }

   public static GuestInfo switchViewBill(String id) throws SQLException, IOException {
      checkPmsEnable();
      LOG.info("switchViewBill {}", id);
      PmsStatus ps = getPmsStatus();
      String pmsStatusBillonTv = ps.getBillontv();
      if ("Off".equalsIgnoreCase(pmsStatusBillonTv)) {
         throw new SQLException("Cannot turn bill on TV ON: Bill On TV is off in PMS Admin tab");
      }

      GuestInfo gi = getGuest(id);
      boolean currentEnabled = "True".equalsIgnoreCase(gi.getViewBill());
      String viewBillFlag = currentEnabled ? "False" : "True";
      gi.setViewBill(viewBillFlag);
      JpaManager.getGuestInfoManager().save(gi);
      sendUpdatePmsFeatures(gi);
      return gi;
   }

   public static GuestInfo switchExpressCheckout(String id) throws SQLException, IOException {
      checkPmsEnable();
      LOG.info("switchExpressCheckout {}", id);
      PmsStatus ps = getPmsStatus();
      String pmsStatusExpressCheckout = ps.getExpresscheckout();
      if ("Off".equalsIgnoreCase(pmsStatusExpressCheckout)) {
         throw new SQLException("Cannot turn express checkout ON, express checkout is turned off in PMS Admin tab");
      }

      GuestInfo gi = getGuest(id);
      boolean currentEnabled = "True".equalsIgnoreCase(gi.getExpressCheckout());
      String expressCheckoutFlag = currentEnabled ? "False" : "True";
      gi.setExpressCheckout(expressCheckoutFlag);
      JpaManager.getGuestInfoManager().save(gi);
      sendUpdatePmsFeatures(gi);
      return gi;
   }

   public static void updateMessageStatus(String msgId, String newStatus) {
      TmsUtils tms = getTmsInstance();
      if (tms != null) {
         try {
            PmsUtils.MessageStatus status = PmsUtils.MessageStatus.valueOf(newStatus);
            Message msg = JpaManager.getMessageManager().loadByMsgId(msgId);
            msgId = msg.getId();
            tms.updateMessageStatus(msgId, status);
         } catch (Exception e) {
            LOG.info(e.getMessage());
         }
      }
   }

   private static void sendUpdateGuestPreference(GuestInfo gi) throws IOException {
      if (gi != null && gi.getCheckin().equalsIgnoreCase("Y")) {
         LOG.info("sendUpdateGuestPreference {}", gi.getGuestId());
         CheckInVO checkinVO = getCheckInVO(gi);
         String respData = PmsJapitCommand.getUpdateGuestPreference(checkinVO);
         sendData2Room(gi.getRoomid(), respData);
      } else {
         LOG.warn("Guest not exist or not checked in");
      }
   }

   public static String convertToPMSOfflineService(String data, String roomId) {
      JSONObject obj = new JSONObject(data);
      obj.put("Svc", "OfflineServices");
      JSONObject offlineServiceParameters = new JSONObject();
      offlineServiceParameters.put("RoomID", roomId);
      JSONObject commandDetails = obj.optJSONObject("CommandDetails");
      commandDetails.put("OfflineServiceParameters", offlineServiceParameters);
      JSONArray pms = new JSONArray();
      pms.put(obj);
      JSONObject all = new JSONObject();
      all.put("PMS", pms);
      return all.toString();
   }

   private static void sendUpdatePmsFeatures(GuestInfo gi) throws IOException {
      if (gi != null && gi.getCheckin().equalsIgnoreCase("Y")) {
         LOG.info("sendUpdatePmsFeatures {}", gi.getGuestId());
         String respData = PmsJapitCommand.getUpdatePMSFeatures(getCheckInVO(gi));
         sendData2Room(gi.getRoomid(), respData);
      } else {
         throw new IOException("Guest not exist or not checked in");
      }
   }

   private static String changeGuestMessage(Devices tv, List<Message> messages) {
      JSONArray array = PmsMessageUtils.getGuestMessagesArray(tv, messages);
      return PmsJapitCommand.getNewGuestMessage(array);
   }

   public static void cleanDatabase() {
      LOG.info("clean pms data");
      List<Roominfo> roomList = JpaManager.getRoominfoManager().loadAll();
      roomList.stream().map(Roominfo::getRoomid).distinct().forEach(PmsUtils::cleanPmsData);
      JpaManager.getReservationManager().deleteAll();
      JpaManager.getRoominfoManager().deleteAll();
      JpaManager.getGuestInfoManager().deleteAll();
   }

   public static PmsAlarmStatus getPmsAlarmStatusForDevice(Devices tv) {
      if (!PlatformUtils.isSupportAlarm(tv)) {
         LOG.warn("tv <{}> not support alarm status query", tv.getTvuniqueid());
         return null;
      }

      ApplicationControlCmd applicationControlCmd = new ApplicationControlCmd();
      applicationControlCmd.setCmdSvc(JapitCommand.CommandSvc.WebListeningServices);
      applicationControlCmd.setSvcVer("1.0");
      applicationControlCmd.setCmdType(JapitCommand.CommandType.Request);
      applicationControlCmd.setCmdDetail(
         "RequestApplicationAttributesValueDetails",
         new JSONArray("[{ \"ApplicationName\": \"Alarm\",    \"RequestListForApplicationAttributesValue\":[      \"AlarmTime\",      \"AlarmEnabled\"   ]}]")
      );

      try {
         String result = JAPITUtils.sendJapitCommand(tv, applicationControlCmd.generateCommand());
         if (isErrorSvc(result)) {
            LOG.warn("ErrorSvc,enable applicationControlService then try again");
            enableApplicationControlService(tv, true, true);
            result = JAPITUtils.sendJapitCommand(tv, applicationControlCmd.generateCommand());
         }

         return extractPmsAlarmStatus(result);
      } catch (Exception e) {
         LOG.error("query alarm status failure", e);
         return null;
      }
   }

   public static boolean disablePmsAlarm(Devices tv) {
      if (!PlatformUtils.isSupportAlarm(tv)) {
         LOG.warn("tv <{}> not support disable alarm", tv.getTvuniqueid());
         return false;
      }

      if (!isPmsAlarmEnable(tv)) {
         return true;
      }

      ApplicationControlCmd applicationControlCmd = new ApplicationControlCmd();
      applicationControlCmd.setCmdSvc(JapitCommand.CommandSvc.WebListeningServices);
      applicationControlCmd.setSvcVer("1.0");
      applicationControlCmd.setCmdType(JapitCommand.CommandType.Change);
      applicationControlCmd.setCmdDetail(
         "ApplicationDetails",
         new JSONObject("{\"ApplicationName\":\"Alarm\",\"ApplicationType\":\"Native\",\"ApplicationAttributes\":{  \"AlarmEnabled\":\"No\"}}")
      );

      try {
         JAPITUtils.sendJapitCommand(tv, applicationControlCmd.generateCommand());
         PmsAlarmStatus pmsAlarmStatus = getPmsAlarmStatusForDevice(tv);
         boolean isDisabledSuccess = pmsAlarmStatus != null && !pmsAlarmStatus.isAlarmEnabled();
         if (isDisabledSuccess) {
            CmndMetricsTask.writePMSInfoToMetricsLog(tv, "alarm_disabled");
         }

         return isDisabledSuccess;
      } catch (Exception e) {
         LOG.error("disable alarm status failure", e);
         return false;
      }
   }

   public static boolean updatePmsAlarmTime(Devices tv, String alarmTime) {
      if (!PlatformUtils.isSupportAlarm(tv)) {
         LOG.warn("tv <{}> not support update alarm time", tv.getTvuniqueid());
         return false;
      }

      boolean isAlarmEnabled = isPmsAlarmEnable(tv);
      ApplicationControlCmd applicationControlCmd = new ApplicationControlCmd();
      applicationControlCmd.setCmdSvc(JapitCommand.CommandSvc.WebListeningServices);
      applicationControlCmd.setSvcVer("4.0");
      applicationControlCmd.setCmdType(JapitCommand.CommandType.Change);
      applicationControlCmd.setCmdDetail(
         "ApplicationDetails",
         new JSONObject(
            "{\"ApplicationName\":\"Alarm\",\"ApplicationType\":\"Native\",\"ApplicationAttributes\":{  \"AlarmTime\":\""
               + alarmTime
               + "\",  \"AlarmRingingVolume\":"
               + getPmsStatus().getAlarmRingingVolume()
               + "}}"
         )
      );
      applicationControlCmd.setCmdDetail("ApplicationState", "Activate");

      try {
         String result = JAPITUtils.sendJapitCommand(tv, applicationControlCmd.generateCommand());
         boolean updateResult = StringUtils.equalsIgnoreCase(extractPmsAlarmStatus(result).getAlarmTime(), alarmTime);
         if (updateResult) {
            CmndMetricsTask.writePMSInfoToMetricsLog(tv, isAlarmEnabled ? "alarm_time_update" : "alarm_set");
         }

         return updateResult;
      } catch (Exception e) {
         LOG.error("update alarm time failure", e);
         return false;
      }
   }

   public static boolean enableApplicationControlService(Devices tv, boolean enableWs, boolean enableWls) throws Exception {
      EnablerServiceCmd enablerServiceCmd = new EnablerServiceCmd(JapitCommand.CommandType.Change, JapitCommand.CommandSvc.WebListeningServices);
      enablerServiceCmd.setApplicationControlService(true);
      if (enableWs) {
         enablerServiceCmd.setWebListeningServices(false);
         JAPITUtils.sendJapitCommand(tv, enablerServiceCmd.generateCommand());
      }

      if (enableWls) {
         enablerServiceCmd.setWebListeningServices(true);
         JAPITUtils.sendJapitCommand(tv, enablerServiceCmd.generateCommand());
      }

      return true;
   }

   private static PmsAlarmStatus extractPmsAlarmStatus(String jsonString) {
      JSONObject jsonResult = new JSONObject(jsonString);
      JSONObject alarmInfo = jsonResult.getJSONObject("CommandDetails")
         .getJSONArray("ApplicationAttributesValue")
         .getJSONObject(0)
         .getJSONObject("ApplicationAttributes");
      PmsAlarmStatus status = new PmsAlarmStatus();
      status.setAlarmEnabled(StringUtils.equalsIgnoreCase("Yes", alarmInfo.optString("AlarmEnabled")));
      status.setAlarmTime(alarmInfo.getString("AlarmTime"));
      return status;
   }

   private static boolean isErrorSvc(String tvresponse) {
      JSONObject jsonResult = new JSONObject(tvresponse);
      return "ErrorSvc".equalsIgnoreCase(jsonResult.getString("Svc"));
   }

   private static boolean isPmsAlarmEnable(Devices tv) {
      PmsAlarmStatus pmsAlarmStatus = getPmsAlarmStatusForDevice(tv);
      return pmsAlarmStatus != null && pmsAlarmStatus.isAlarmEnabled();
   }

   public enum GuestInfoFieldType {
      GuestName,
      GuestLanguage,
      GuestDepartureDate;
   }

   public enum MessageStatus {
      New,
      UnRead,
      Read,
      Delete;
   }

   public enum PmsAction {
      CheckIn,
      CheckOut,
      LanguageChange,
      GroupChange,
      RoomType;
   }

   public enum PmsSyncStatus {
      NeedSync,
      Synced,
      Syncing;
   }

   public enum RequestType {
      RequestBill,
      RequestRefresh,
      RequestExpressCheckout,
      RequestMessage;
   }
}
