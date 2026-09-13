/*
 * Decompiled with CFR 0.152.
 */
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
import com.tpvision.smartinstall.pms.AccorFolsTmsUtils;
import com.tpvision.smartinstall.pms.CheckInVO;
import com.tpvision.smartinstall.pms.FiasServerTmsUtils;
import com.tpvision.smartinstall.pms.HTNGTmsUtils;
import com.tpvision.smartinstall.pms.HopTmsUtils;
import com.tpvision.smartinstall.pms.ImpalaTmsUtils;
import com.tpvision.smartinstall.pms.OracleTmsUtils;
import com.tpvision.smartinstall.pms.TigerTmsUtils;
import com.tpvision.smartinstall.pms.TmsConnectQueryTask;
import com.tpvision.smartinstall.pms.TmsUtils;
import com.tpvision.smartinstall.pms.UpdatePmsMsgTask;
import com.tpvision.smartinstall.pms.WakeupAlarmTask;
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
    private static ThreadPoolExecutor executor;
    private static Map<String, Timer> timerMap;
    private static final int REQUEST_TIMEOUT = 10000;
    private static Random rand;

    private static String getTimerName(RequestType requestType, String roomId) {
        Integer room = Integer.parseInt(roomId);
        return requestType.name() + "_" + room;
    }

    public static void setRequestTimer(final RequestType requestType, final String roomId) {
        String timerName = PmsUtils.getTimerName(requestType, roomId);
        TimerTask task = new TimerTask(){

            @Override
            public void run() {
                String requestName = requestType.name();
                LOG.warn("{} for room:{} not respond from FIAS in {} seconds", requestName, roomId, 10);
                PmsUtils.removeRequestTimer(requestType, roomId);
                this.cancel();
            }
        };
        Timer timer = new Timer(timerName);
        timer.schedule(task, 10000L, 10000L);
        timerMap.put(timerName, timer);
    }

    public static void removeRequestTimer(RequestType requestType, String roomId) {
        String timerName = PmsUtils.getTimerName(requestType, roomId);
        if (timerMap.containsKey(timerName)) {
            Timer timer = timerMap.get(timerName);
            timer.cancel();
            timerMap.remove(timerName);
            LOG.info("removeRequestTimer {}", (Object)timerName);
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
        PmsStatus ps = PmsUtils.getPmsStatus();
        if (!"on".equalsIgnoreCase(ps.getPms())) {
            return "Off";
        }
        String pmsType = ps.getPmstype();
        pmsType = pmsType.split(" ")[0];
        if ((pmsType = pmsType.replace("TMS", "").replace("PMS", "")).contains("oracle")) {
            pmsType = "FIAS";
        }
        return pmsType.toUpperCase();
    }

    public static void createTmsInstace(String pmsType) {
        if (sTmsUtils != null) {
            sTmsUtils.stop();
        }
        LOG.info("createTmsInstace,Current PMS Type:{}", (Object)pmsType);
        sTmsUtils = pmsType == null || pmsType.equalsIgnoreCase("none") ? null : (pmsType.contains("tigerTMS") ? new TigerTmsUtils() : (pmsType.contains("oracleTMS") ? new OracleTmsUtils() : (pmsType.contains("impala") ? new ImpalaTmsUtils() : (pmsType.contains("accor") ? new AccorFolsTmsUtils() : (pmsType.contains("htng") ? new HTNGTmsUtils() : (pmsType.contains("fiasServer") ? new FiasServerTmsUtils() : (pmsType.contains("hop") ? new HopTmsUtils() : null)))))));
        if (sTmsUtils != null) {
            sTmsUtils.start();
        } else {
            PmsUtils.updatePmsConnectionStatus("Ok");
        }
    }

    public static boolean isAutoCreateTV() {
        TmsUtils tms = PmsUtils.getTmsInstance();
        return tms == null || tms.isAutoCreateTV();
    }

    private static String getAutoRFPlatform() {
        TmsUtils tms = PmsUtils.getTmsInstance();
        return tms == null ? null : tms.getConfigValue("RFTV_PLATFORM", "2019 MS");
    }

    public static TmsUtils getTmsInstance() {
        String pmsType = PmsUtils.getTmsType();
        if (!pmsType.equalsIgnoreCase("none") && sTmsUtils == null) {
            PmsUtils.createTmsInstace(pmsType);
        }
        return sTmsUtils;
    }

    public static void submitSyncPmsStatusTask(Devices devices) {
        executor.submit(new TpvRunableTask(){
            private Devices devices;

            public Runnable init(Devices devices) {
                this.devices = devices;
                return this;
            }

            @Override
            public void execute() {
                PmsUtils.syncPmsStatus(this.devices);
            }
        }.init(devices));
    }

    private static void stopTmsInstance() {
        LOG.info("Stopping TMS instance");
        if (null != sTmsUtils) {
            sTmsUtils.stop();
            sTmsUtils = null;
        }
    }

    public static void setPMSEnabled(boolean enabled) {
        LOG.info("set pms {}", (Object)(enabled ? "enabled" : "disabled"));
        if (enabled) {
            if (PmsUtils.isPmsMessagesEnabled()) {
                PmsUtils.startPmsMessageSchedule();
            } else {
                LOG.info("PMS Message is disabled");
                PmsUtils.stopPmsMessageSchedule();
            }
            PmsUtils.startTmsConnectionSchedule();
            PmsUtils.startWakeupAlarmSchedule();
        } else {
            PmsUtils.stopPmsMessageSchedule();
            PmsUtils.stopTmsConnectionSchedule();
            PmsUtils.stopWakeupAlarmSchedule();
        }
    }

    private static void stopTmsConnectionSchedule() {
        LOG.info("Stop PMS connection check task");
        if (service4connection != null) {
            service4connection.shutdownNow();
            service4connection = null;
        }
        PmsUtils.stopTmsInstance();
    }

    private static void startTmsConnectionSchedule() {
        if (service4connection != null) {
            LOG.error("pms connection check task is running");
            return;
        }
        if (PmsUtils.getTmsInstance() != null) {
            service4connection = Executors.newScheduledThreadPool(1, new TpvNamedThreadFactory("tms"));
            service4connection.scheduleWithFixedDelay(new TmsConnectQueryTask(), 1000L, 60000L, TimeUnit.MILLISECONDS);
            LOG.info("Check TMS connection status every 1 minute.");
        }
    }

    private static void startPmsMessageSchedule() {
        LOG.info("Start PMS messages check task every 1 minute.");
        if (service4message != null) {
            LOG.error("pms message check task is running");
            return;
        }
        service4message = Executors.newScheduledThreadPool(1, new TpvNamedThreadFactory("pms-message"));
        service4message.scheduleWithFixedDelay(new UpdatePmsMsgTask(), 1L, 60L, TimeUnit.SECONDS);
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
            return;
        }
        LOG.info("Start PMS WakeupAlarm task every 1 minute.");
        service4wakeup = Executors.newScheduledThreadPool(1, new TpvNamedThreadFactory("pms-wakeup"));
        service4wakeup.scheduleWithFixedDelay(new WakeupAlarmTask(), 1L, 60L, TimeUnit.SECONDS);
    }

    public static void stopWakeupAlarmSchedule() {
        if (service4wakeup != null) {
            LOG.info("Stop PMS WakeupAlarm task.");
            service4wakeup.shutdown();
            service4wakeup = null;
        }
    }

    public static boolean sendMsg2TV(String roomid) {
        if (!PmsUtils.isPmsMessagesEnabled()) {
            return false;
        }
        MessageManager mm = JpaManager.getMessageManager();
        List<Message> msgs = mm.findAccessTimeSendMessageByGuestIds(roomid);
        if (msgs.isEmpty()) {
            LOG.error("There is no suitable msgs for the guestId={}!", (Object)roomid);
            return false;
        }
        try {
            PmsUtils.sendData2Room(roomid, (Devices t) -> PmsUtils.changeGuestMessage(t, msgs));
            for (Message message : msgs) {
                if (!message.getIsSent().equalsIgnoreCase("N")) continue;
                message.setIsSent("Y");
                message.setStatus("UnRead");
                mm.save(message);
            }
            PmsUtils.setMessageUpdated();
            return true;
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    public static void changeLanguage(String roomid, String lang) throws SQLException, IOException {
        GuestInfoManager gim = JpaManager.getGuestInfoManager();
        List<GuestInfo> guestInfos = gim.findGuestInfosByRoomid(roomid);
        if (guestInfos == null || guestInfos.isEmpty()) {
            throw new SQLException("not guest found for roomid=" + roomid);
        }
        for (GuestInfo guestInfo : guestInfos) {
            guestInfo.setGuestLanguage(lang);
            gim.save(guestInfo);
            if (!"Y".equalsIgnoreCase(guestInfo.getCheckin())) continue;
            CastServerUtils.sendCheckInNoticeToOpenApiServer(guestInfo);
        }
        if (PmsUtils.hasRFTVForRoom(roomid)) {
            PlayoutUtils.addPMSAction(PmsAction.LanguageChange, roomid);
        }
        GuestInfo guestInfo = guestInfos.get(0);
        PmsUtils.sendUpdateGuestPreference(guestInfo);
        TriggerUtils.executePMSTrigger(PmsAction.LanguageChange, roomid, lang);
    }

    public static GuestInfo handleGuestResults(Element rootElt) {
        GuestInfo guestInfo = PmsUtils.assembleGuestInfo(rootElt);
        if (guestInfo != null) {
            String roomId = guestInfo.getRoomid();
            try {
                PmsUtils.updateGuestInfo(roomId, GuestInfoFieldType.GuestDepartureDate, guestInfo.getDepartureDate());
                PmsUtils.updateGuestInfo(roomId, GuestInfoFieldType.GuestLanguage, guestInfo.getGuestLanguage());
                PmsUtils.updateGuestInfo(roomId, GuestInfoFieldType.GuestName, guestInfo.getGuestName());
            }
            catch (IOException e) {
                LOG.info("update guestinfo fail:{}", (Object)e.getMessage());
            }
        }
        return guestInfo;
    }

    public static void updatePMSFeaturesToTV() {
        new Thread(() -> {
            try {
                GuestInfoManager gim = JpaManager.getGuestInfoManager();
                List<GuestInfo> guestInfos = gim.loadAll();
                for (GuestInfo gi : guestInfos) {
                    if (!gi.getCheckin().equalsIgnoreCase("Y")) {
                        return;
                    }
                    PmsUtils.sendUpdateGuestPreference(gi);
                    PmsUtils.sendUpdatePmsFeatures(gi);
                }
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }).start();
    }

    private static String getSwitchValueByConfig(String currentVal, String config) {
        String newValue = currentVal;
        switch (config) {
            case "Off": {
                newValue = "False";
                break;
            }
            case "DefaultAlwaysOn": 
            case "Demo": {
                if (currentVal != null) break;
                newValue = "True";
                break;
            }
            case "DefaultAlwaysOff": {
                if (currentVal != null) break;
                newValue = "False";
                break;
            }
            case "FollowPMS": {
                break;
            }
            default: {
                LOG.info("invalid config:{}", (Object)config);
            }
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
        displayName = "".equalsIgnoreCase(guestName) ? guest.getGroupName() : title + " " + guestName;
        if ("brazil-portuguese".equalsIgnoreCase(langugae)) {
            langugae = "por";
        }
        PmsStatus ps = null;
        PmsStatusManager psm = JpaManager.getPmsStatusManager();
        ps = psm.loadByKey(1);
        expressCheckout = PmsUtils.getSwitchValueByConfig(guest.getExpressCheckout(), ps.getExpresscheckout());
        viewBill = PmsUtils.getSwitchValueByConfig(guest.getViewBill(), ps.getBillontv());
        doNotDisturb = PmsUtils.getSwitchValueByConfig(guest.getDoNotDisturb(), ps.getDoNotDisturb());
        if ("On".equalsIgnoreCase(ps.getGuestname())) {
            guestDetails = true;
        }
        if ("On".equalsIgnoreCase(ps.getGuestlanguage())) {
            guestPreferLanguage = true;
        }
        if ("On".equalsIgnoreCase(ps.getMessages())) {
            boolean bl = messages = guest.getViewMessage() == null || guest.getViewMessage().equalsIgnoreCase("True");
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
        return PmsUtils.getTVsForRoom(roomId).stream().filter(e -> !e.isRFDevice()).collect(Collectors.toList());
    }

    public static List<Devices> getTVsForRoom(String roomId) {
        DevicesManager dm = JpaManager.getDevicesManager();
        return dm.findDevicesByRoomId(roomId);
    }

    public static List<Devices> getCompatibleTVsForRoom(String roomId, String tvid) {
        ArrayList<Devices> tvIPAddrs = new ArrayList<Devices>();
        DevicesManager dm = JpaManager.getDevicesManager();
        List<Devices> devices = PmsUtils.getTVsForRoom(roomId);
        Devices targetDevice = dm.loadByKey(tvid);
        for (Devices device : devices) {
            if (null == targetDevice || !device.getType().equalsIgnoreCase(targetDevice.getType())) continue;
            tvIPAddrs.add(device);
        }
        return tvIPAddrs;
    }

    public static PmsStatus getPmsStatus() {
        PmsStatusManager psm = JpaManager.getPmsStatusManager();
        return psm.loadByKey(1);
    }

    public static boolean isPmsEnabled() {
        boolean val = false;
        PmsStatus ps = PmsUtils.getPmsStatus();
        if (null != ps && "on".equalsIgnoreCase(ps.getPms())) {
            val = true;
        }
        return val;
    }

    public static boolean isPmsMessagesEnabled() {
        boolean val = false;
        PmsStatus ps = PmsUtils.getPmsStatus();
        if (null != ps && "on".equalsIgnoreCase(ps.getMessages())) {
            val = true;
        }
        return val;
    }

    public static String getTmsType() {
        String val = "";
        PmsStatus ps = PmsUtils.getPmsStatus();
        if (null != ps && ps.getPms().equalsIgnoreCase("on")) {
            val = ps.getPmstype();
        }
        return val;
    }

    public static JSONObject getPmsConfigs() {
        try {
            PmsStatus ps = PmsUtils.getPmsStatus();
            return new JSONObject(ps.getPmsconfigs());
        }
        catch (Exception e) {
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
        LOG.info("updatePmsConnectionStatus:{}", (Object)status);
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
        TmsUtils tms = PmsUtils.getTmsInstance();
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
        if (null == gi) {
            return;
        }
        LOG.info("delete guestinfo:{},{}", (Object)gi.getGuestId(), (Object)gi.getGuestName());
        GuestInfoManager gim = JpaManager.getGuestInfoManager();
        gim.deleteByKey(gi.getGuestId());
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
        GuestInfo gi = gm.loadByKey(guestId = PmsUtils.generateNewGuestId(guestId, roomId));
        if (!PmsUtils.isGuestCheckedIn(gi, roomId)) {
            String log = TpvStringUtils.format("guest {} no checked in room {}", guestId, roomId);
            LOG.error(log);
            return null;
        }
        TmsUtils tms = PmsUtils.getTmsInstance();
        if (tms != null) {
            language = tms.getMappedLanguage(lang);
        }
        PmsStatus ps = null;
        PmsStatusManager psm = JpaManager.getPmsStatusManager();
        ps = psm.loadByKey(1);
        expressco = PmsUtils.getSwitchValueByConfig(expressco, ps.getExpresscheckout());
        viewbill = PmsUtils.getSwitchValueByConfig(viewbill, ps.getBillontv());
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
        return PmsUtils.saveCheckinResults2DB(rootElt, true);
    }

    public static GuestInfo saveCheckinResults2DB(Element rootElt, boolean checkedin) throws IOException {
        LOG.info("checkin info:{}", (Object)rootElt.asXML());
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
        guestId = PmsUtils.generateNewGuestId(guestId, roomId);
        GuestInfo gi = gm.loadByKey(guestId);
        if (PmsUtils.isGuestCheckedIn(gi, roomId) && checkedin) {
            String log = TpvStringUtils.format("guest {} already checked in room {}", guestId, roomId);
            LOG.info(log);
            throw new IOException(log);
        }
        if (PmsUtils.isGuestCheckin(roomId) && checkedin) {
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
        TmsUtils tms = PmsUtils.getTmsInstance();
        if (tms != null) {
            language = tms.getMappedLanguage(lang);
        }
        if (null == gi) {
            gi = new GuestInfo();
        } else if (gi.getOrderid() != null && !gi.getOrderid().equalsIgnoreCase(reservationId)) {
            LOG.info("reservation id changed, remove old reservation:{}", (Object)gi.getOrderid());
            JpaManager.getReservationManager().deleteByKey(gi.getOrderid());
        }
        PmsStatus ps = null;
        PmsStatusManager psm = JpaManager.getPmsStatusManager();
        ps = psm.loadByKey(1);
        expressco = PmsUtils.getSwitchValueByConfig(expressco, ps.getExpresscheckout());
        viewbill = PmsUtils.getSwitchValueByConfig(viewbill, ps.getBillontv());
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
        GuestInfoManager gm;
        GuestInfo gi;
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
        guestId = PmsUtils.generateNewGuestId(guestId, roomId);
        TmsUtils tms = PmsUtils.getTmsInstance();
        if (tms != null) {
            language = tms.getMappedLanguage(lang);
        }
        if (null == (gi = (gm = JpaManager.getGuestInfoManager()).loadByKey(guestId))) {
            gi = new GuestInfo();
        }
        PmsStatus ps = null;
        PmsStatusManager psm = JpaManager.getPmsStatusManager();
        ps = psm.loadByKey(1);
        expressco = PmsUtils.getSwitchValueByConfig(expressco, ps.getExpresscheckout());
        viewbill = PmsUtils.getSwitchValueByConfig(viewbill, ps.getBillontv());
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
        PmsUtils.setMessageUpdated();
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
        LOG.info("save message text:{}", (Object)rootElt.asXML());
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
        }
        catch (ParseException e) {
            LOG.error(e.getMessage(), e);
        }
        if (status == null || status.equalsIgnoreCase("new")) {
            PmsUtils.sendMsg2TV(roomId);
        }
        PmsUtils.setMessageUpdated();
        return msg;
    }

    private static void updatePmsSyncStatus(Devices tv, PmsSyncStatus syncStatus) {
        tv.setPmsSyncStatus(syncStatus.ordinal());
        JpaManager.getDevicesManager().updatePmsSyncNewStatus(tv);
    }

    public static void sendData2TV(Devices tv, String data) throws Exception {
        try {
            JSONObject jsonResp;
            if (!PlatformUtils.isAsta2016Up(tv.getType()) && "PMSService".equalsIgnoreCase(new JSONObject(data).optString("Fun"))) {
                LOG.warn("{} no support PMSService", (Object)tv.getType());
                PmsUtils.updatePmsSyncStatus(tv, PmsSyncStatus.Synced);
                return;
            }
            PmsUtils.updatePmsSyncStatus(tv, PmsSyncStatus.Syncing);
            String resp = JAPITUtils.sendJapitCommand(tv, data, 10000);
            if (!StringUtils.isEmpty(resp) && resp.contains("{") && "Error".equalsIgnoreCase((jsonResp = new JSONObject(resp)).optString("Fun"))) {
                throw new IOException("TV can not process PMS Command");
            }
            CmndMetricsTask.writeIPTVMetricsLog(tv, new JSONObject(data));
            PmsUtils.updatePmsSyncStatus(tv, PmsSyncStatus.Synced);
        }
        catch (Exception e) {
            LOG.error("send Pms message to TV {} failed, pms Sync=false", (Object)tv.getTvuniqueid());
            PmsUtils.updatePmsSyncStatus(tv, PmsSyncStatus.NeedSync);
            throw e;
        }
    }

    private static void sendData2Room(String roomId, String data) throws IOException {
        PmsUtils.sendData2Room(roomId, (Devices t) -> data);
    }

    private static void sendData2Room(String roomId, Function<Devices, String> function) throws IOException {
        PmsUtils.sendDatas2Room(roomId, t -> {
            String data = (String)function.apply((Devices)t);
            ArrayList<String> datas = new ArrayList<String>();
            datas.add(data);
            return datas;
        });
    }

    private static void sendDatas2Room(String roomId, Function<Devices, List<String>> function) throws IOException {
        List<Devices> tvs = PmsUtils.getIPTVForCheckRoom(roomId);
        int exceptionCount = 0;
        String message = "";
        if (tvs.isEmpty()) {
            exceptionCount = 1;
            message = "roomId " + roomId + " no iptv device found!";
            LOG.warn(message);
            return;
        }
        for (Devices tv : tvs) {
            List<String> datas;
            if (!PmsUtils.ensureTvJapitWorking(tv, datas = function.apply(tv))) {
                LOG.info("tv {} offline, set pms need sync", (Object)tv.getId());
                PmsUtils.updatePmsSyncStatus(tv, PmsSyncStatus.NeedSync);
                continue;
            }
            try {
                for (String data : datas) {
                    PmsUtils.sendData2TV(tv, data);
                }
            }
            catch (Exception e) {
                LOG.info("{} error:{}", (Object)tv.getTvuniqueid(), (Object)e.getMessage());
                message = e.getMessage();
                ++exceptionCount;
            }
        }
        if (exceptionCount > 0) {
            if (exceptionCount > 1) {
                message = exceptionCount == tvs.size() ? "Send command to TV failed" : "Send command to some tvs failed";
            }
            throw new IOException(message);
        }
    }

    private static boolean ensureTvJapitWorking(Devices tv, List<String> datas) {
        if (tv.isOnline()) {
            return true;
        }
        if (PlatformUtils.isSupportWakeupOnLan(tv.getType())) {
            for (String data : datas) {
                if (!StringUtils.containsIgnoreCase(data, "PowerService") || !StringUtils.containsIgnoreCase(data, "ToPowerState") || !StringUtils.containsIgnoreCase(data, "On")) continue;
                return TVPowerManager.wakeupTvOnLan(tv);
            }
        }
        return false;
    }

    private static void syncPmsStatus(Devices tv) {
        LOG.info("sync pms status to tv=<{}>, ip=<{}>", (Object)tv.getTvuniqueid(), (Object)tv.getTvipaddress());
        String pmsData = null;
        if (PmsUtils.isGuestCheckin(tv.getTvroomid())) {
            GuestInfo gi = PmsUtils.getGuestInfoByRoomId(tv.getTvroomid());
            CheckInVO checkinVO = PmsUtils.getCheckInVO(gi);
            if (!PlatformUtils.isMasf2019Up(tv.getType())) {
                PmsUtils.processCheckInVOForASTA(checkinVO);
            }
            pmsData = PmsJapitCommand.getCheckIn(checkinVO);
        } else {
            pmsData = PmsJapitCommand.getCheckOut();
        }
        try {
            PmsUtils.sendData2TV(tv, pmsData);
        }
        catch (Exception e) {
            LOG.error("syncPmsStatus to TV {} failed,{}", (Object)tv.getTvuniqueid(), (Object)e.getMessage());
        }
    }

    private static boolean hasRFTVForRoom(String roomId) {
        boolean hasRF = false;
        List<Devices> tvs = PmsUtils.getTVsForRoom(roomId);
        for (Devices tv : tvs) {
            if (!tv.isRFDevice()) continue;
            hasRF = true;
            break;
        }
        if (tvs.isEmpty() && PmsUtils.isAutoCreateTV()) {
            LOG.info("tvlist is empty, auto create RF TV for roomid:{}", (Object)roomId);
            PmsUtils.createRFTV(roomId, PmsUtils.getAutoRFPlatform());
            hasRF = true;
        }
        return hasRF;
    }

    public static void checkin2TV(GuestInfo gi, boolean needWakeup) throws IOException {
        if (gi == null) {
            LOG.error("guestinfo is null");
            return;
        }
        LOG.info("checkin2TV room:{},guestid:{}", (Object)gi.getRoomid(), (Object)gi.getGuestId());
        String roomid = gi.getRoomid();
        if (PmsUtils.hasRFTVForRoom(roomid)) {
            PlayoutUtils.addPMSAction(PmsAction.CheckIn, String.valueOf(roomid));
        }
        CheckInVO checkinVO = PmsUtils.getCheckInVO(gi);
        String respData = PmsJapitCommand.getCheckIn(checkinVO);
        PmsUtils.sendDatas2Room(roomid, t -> {
            String checkinData = respData;
            ArrayList<String> datas = new ArrayList<String>();
            if (!PlatformUtils.isMasf2019Up(t.getType())) {
                PmsUtils.processCheckInVOForASTA(checkinVO);
                checkinData = PmsJapitCommand.getCheckIn(checkinVO);
            }
            datas.add(checkinData);
            if (needWakeup && "On".equalsIgnoreCase(PmsUtils.getPmsStatus().getAutoWakeUpTv())) {
                String standbyJapit = TVPowerManager.changePowerService("On");
                datas.add(standbyJapit);
            }
            return datas;
        });
    }

    public static boolean setAutoWakeUpTv(boolean autoWakeup) {
        String newWakeup;
        LOG.info("change AutoWakeUpTv to {}", (Object)autoWakeup);
        PmsStatus ps = PmsUtils.getPmsStatus();
        String wakeup = ps.getAutoWakeUpTv();
        String string = newWakeup = autoWakeup ? "On" : "Off";
        if (!newWakeup.equalsIgnoreCase(wakeup)) {
            ps.setAutoWakeUpTv(newWakeup);
            JpaManager.getPmsStatusManager().save(ps);
        }
        return wakeup.equalsIgnoreCase("On");
    }

    public static void createRFTV(String roomid, String platformName) {
        LOG.info("create RF TV:{}", (Object)roomid);
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
        if (roomid == null) {
            return bim.findBillitemsRoomIdIsNull();
        }
        return bim.findBillitemsByRoomId(roomid);
    }

    private static JSONArray processBillItemsForASTA(JSONArray billitems) {
        for (int i = 0; i < billitems.length(); ++i) {
            JSONObject jsObj = billitems.getJSONObject(i);
            jsObj.put("BillItemDisplayName", TpvStringUtils.unicode2String(jsObj.optString("BillItemDisplayName")));
        }
        return billitems;
    }

    private static boolean isGuestCheckedIn(GuestInfo gi, String roomid) {
        if (StringUtils.isEmpty(roomid)) {
            return false;
        }
        return gi != null && gi.getCheckin().equalsIgnoreCase("Y") && gi.getRoomid().equalsIgnoreCase(roomid);
    }

    public static boolean isGuestCheckin(String roomid) {
        List<GuestInfo> guestInfos = PmsUtils.getGuestInfosByRoomId(roomid);
        boolean checkin = false;
        for (GuestInfo guestInfo : guestInfos) {
            if (!guestInfo.getCheckin().equalsIgnoreCase("Y")) continue;
            checkin = true;
            break;
        }
        return checkin;
    }

    public static boolean isGuestViewBill(String roomid) {
        List<GuestInfo> guestInfos = PmsUtils.getGuestInfosByRoomId(roomid);
        boolean viewBill = false;
        for (GuestInfo guestInfo : guestInfos) {
            if (!"True".equalsIgnoreCase(guestInfo.getViewBill())) continue;
            viewBill = true;
            break;
        }
        return viewBill;
    }

    public static void sendBill2TV(GuestInfo guest) {
        if (null == guest) {
            LOG.error("guest is null");
            return;
        }
        LOG.info("sendBill2TV");
        String roomid = guest.getRoomid();
        try {
            PmsUtils.sendDatas2Room(roomid, t -> PmsUtils.changeGuestBill(guest, t));
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public static void responseBill(Devices tv) throws IOException {
        String bill = PmsUtils.getPmsStatus().getBillontv();
        if ("Off".equalsIgnoreCase(bill)) {
            throw new IOException("CMND Billontv option is off, Cannot get bill!!");
        }
        TmsUtils tms = PmsUtils.getTmsInstance();
        if (null != tms && tms.isInstantBill() && !PmsUtils.getPmsStatus().isDemoBill()) {
            LOG.info("TmsUtils not null, send bill request to tms");
            tms.requestBill(tv.getTvroomid());
        } else {
            GuestInfo guest = PmsUtils.getGuestInfoByRoomId(tv.getTvroomid());
            PmsUtils.sendBill2TV(guest);
        }
    }

    private static JSONArray getBillItemArray(String roomId) {
        JSONArray array = new JSONArray();
        SimpleDateFormat dateFormat1 = TpvDateUtils.getSimpleDateFormatWithEnglishLocale("dd/MM/yyyy");
        Date date = null;
        List<Billitem> bis = PmsUtils.getBillItems(roomId);
        if (null != bis) {
            int id = 1;
            for (Billitem b : bis) {
                if ("No".equalsIgnoreCase(b.getDisplayFlag())) continue;
                try {
                    date = dateFormat1.parse(b.getBillItemDate());
                }
                catch (ParseException e) {
                    LOG.error("parse date failed:{}", (Object)b.getBillItemDate());
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
        return array;
    }

    public static JSONObject getBillObject(Devices tv) {
        JSONObject billObject = null;
        String roomId = tv.getTvroomid();
        if (!PmsUtils.isGuestCheckin(roomId)) {
            LOG.warn("Guest not checkin, cannot get bill");
        } else if (!PmsUtils.isGuestViewBill(roomId)) {
            LOG.warn("Guest ViewBill is off, cannot get bill");
        } else {
            String bill = PmsUtils.getPmsStatus().getBillontv();
            if ("Off".equalsIgnoreCase(bill)) {
                LOG.warn("CMND Billontv option is off, Cannot get bill!!");
            } else {
                boolean isDemo = PmsUtils.getPmsStatus().isDemoBill();
                TmsUtils tms = PmsUtils.getTmsInstance();
                if (null != tms && tms.isInstantBill() && !isDemo) {
                    LOG.info("TmsUtils not null, send bill request to tms");
                    tms.requestBill(tv.getTvroomid());
                } else {
                    GuestInfo gi = PmsUtils.getGuestInfoByRoomId(roomId);
                    JSONArray array = PmsUtils.getBillItemArray(isDemo ? null : String.valueOf(roomId));
                    if (!PlatformUtils.isMasf2019Up(tv.getType())) {
                        PmsUtils.processBillItemsForASTA(array);
                    }
                    billObject = PmsJapitCommand.getGuestBill(tv, gi, array);
                }
            }
        }
        return billObject;
    }

    private static List<String> changeGuestBill(GuestInfo guest, Devices tv) {
        boolean isDemo = PmsUtils.getPmsStatus().isDemoBill();
        JSONArray array = PmsUtils.getBillItemArray(isDemo ? null : String.valueOf(guest.getRoomid()));
        if (!PlatformUtils.isMasf2019Up(tv.getType())) {
            PmsUtils.processBillItemsForASTA(array);
        }
        return PmsJapitCommand.getGuestBills(tv, guest, array);
    }

    public static String getCurrency() {
        String currency = PmsUtils.getPmsStatus().getCurrency();
        String currencyProp = PmsUtils.getPmsStatus().getCurrencyPreference();
        int i = currency.indexOf(" ");
        if ("CurrencySymbol".equalsIgnoreCase(currencyProp)) {
            currency = -1 != i ? currency.substring(0, i) : "";
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
        if (roomId == null || !isAllowMultipleRooms) {
            return guestId;
        }
        return guestId + "_" + roomId;
    }

    public static String extractGuestId(String newGuestId) {
        if (newGuestId.contains("_")) {
            return newGuestId.split("_")[0];
        }
        return newGuestId;
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
            PmsUtils.createTmsInstace(ps.getPmstype());
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
            guestId = PmsUtils.generateNewGuestId(guestId, roomId);
            gi = gm.loadByKey(guestId);
        }
        if (null != gi && "Y".equalsIgnoreCase(gi.getCheckin())) {
            gi.setCheckin("N");
            gm.save(gi);
            PmsUtils.setUpdatedGuestInfo(gi.getGuestId());
        }
        if (gi == null && roomId != null) {
            LOG.info("guest not found for guestid:{}", (Object)guestId);
            gi = PmsUtils.getGuestInfoByRoomId(roomId);
        }
        return gi;
    }

    private static void checkout2TV(String roomid) throws IOException {
        LOG.info("checkout2TV for room:{}", (Object)roomid);
        if (PmsUtils.hasRFTVForRoom(roomid)) {
            PlayoutUtils.addPMSAction(PmsAction.CheckOut, String.valueOf(roomid));
        }
        String respData = PmsJapitCommand.getCheckOut();
        String autoSwitchOff = PmsUtils.getPmsStatus().getAutoSwitchOffTv();
        String respStandbyData = TVPowerManager.changePowerService("Standby");
        PmsUtils.sendDatas2Room(roomid, t -> {
            ArrayList<String> datas = new ArrayList<String>();
            datas.add(respData);
            if ("On".equalsIgnoreCase(autoSwitchOff)) {
                datas.add(respStandbyData);
            }
            return datas;
        });
    }

    public static void changeGuestRoom(String roomold, String room) {
        LOG.info("changeGuestRoom for room:{} to {}", (Object)roomold, (Object)room);
        GuestInfoManager gm = JpaManager.getGuestInfoManager();
        ReservationManager rm = JpaManager.getReservationManager();
        try {
            Reservation r;
            GuestInfo gi = null;
            List<GuestInfo> guestInfos = gm.findGuestInfosByRoomid(roomold);
            if (guestInfos.isEmpty()) {
                LOG.error("guest not exists for old room:{}", (Object)roomold);
                return;
            }
            gi = guestInfos.get(0);
            PmsUtils.checkout2TV(gi.getRoomid());
            gm.deleteByKey(gi.getGuestId());
            gi.setRoomid(room);
            gi.setGuestId(PmsUtils.generateNewGuestId(PmsUtils.extractGuestId(gi.getGuestId()), room));
            gm.save(gi);
            if (gi.getOrderid() != null && (r = rm.loadByKey(gi.getOrderid())) != null) {
                r.setRooms(room);
                rm.save(r);
            }
            List<Message> messages = JpaManager.getMessageManager().findMessageByGuestIds(roomold);
            messages.stream().forEach(t -> {
                t.setGuestIds(room);
                JpaManager.getMessageManager().save((Message)t);
            });
            PmsUtils.checkin2TV(gi, false);
            PmsUtils.setUpdatedGuestInfo(gi.getGuestId());
            PmsUtils.setMessageUpdated();
            CastServerUtils.sendChangeRoomNoticeToOpenApiServer(gi, roomold, room);
        }
        catch (IOException | NumberFormatException e) {
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
        return PmsUtils.storageBalance2GuestInfo(roomId, balance, totalDateTime);
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
        if (billId == null || billId.isEmpty()) {
            billId = UUID.randomUUID().toString();
        } else {
            bi = bim.loadByKey(billId);
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
        return gi == null || gi.getBalance() == null ? "0" : gi.getBalance();
    }

    public static void deletePreBillItems(String roomId) {
        BillitemManager bm = JpaManager.getBillitemManager();
        List<Billitem> bis = bm.findBillitemsByRoomId(roomId);
        for (Billitem bi : bis) {
            bm.deleteByKey(bi.getID());
        }
    }

    public static GuestInfo storageBalance2GuestInfo(String roomId, String balance, String totalDateTime) {
        GuestInfoManager gim = JpaManager.getGuestInfoManager();
        List<GuestInfo> gis = gim.findGuestInfosByRoomid(roomId);
        if (!gis.isEmpty()) {
            for (GuestInfo gi : gis) {
                gi.setBalance(balance);
                gi.setTotalBillDateTime(totalDateTime);
                gim.save(gi);
            }
            return gis.get(0);
        }
        return null;
    }

    public static GuestInfo getGuestInfoByRoomId(String roomId) {
        List<GuestInfo> gis = PmsUtils.getGuestInfosByRoomId(roomId);
        if (!gis.isEmpty()) {
            return gis.get(0);
        }
        return null;
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
                PmsUtils.processCheckout(roomId);
                CmndMetricsTask.resetExpressCheckOut();
            } else {
                String respCheckoutData = PmsJapitCommand.getExpressCheckOutError("Others");
                PmsUtils.sendData2Room(roomId, respCheckoutData);
            }
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
            CmndMetricsTask.resetExpressCheckOut();
        }
    }

    public static void processAfterCheckout(String roomId) {
        LOG.info("processAfterCheckout {}", (Object)roomId);
        PmsUtils.cleanPmsData(roomId);
    }

    public static void cleanPmsData(String roomId) {
        if (StringUtils.isEmpty(roomId)) {
            return;
        }
        LOG.info("cleanPmsData {}", (Object)roomId);
        List<GuestInfo> gis = PmsUtils.getGuestInfosByRoomId(roomId);
        for (GuestInfo gi : gis) {
            PmsUtils.deleteGuestInfo(gi);
            PmsUtils.setUpdatedGuestInfo(gi.getGuestId());
        }
        PmsUtils.cleanMessages(roomId);
        PmsUtils.deletePreBillItems(roomId);
    }

    public static GuestInfo createGuest(String guestName, String groupName, String roomId, String roomType, String guestLang) {
        LOG.info("create new guest");
        int id1 = rand.nextInt(99999);
        GuestInfo guestInfo = new GuestInfo();
        guestInfo.setGuestId(PmsUtils.generateNewGuestId(String.valueOf(id1), roomId));
        guestInfo.setTitle("");
        guestInfo.setGuestName(guestName == null ? "" : guestName);
        guestInfo.setGroupName(groupName == null ? "" : groupName);
        guestInfo.setRoomid(roomId);
        guestInfo.setCheckin("N");
        guestInfo.setGuestLanguage(guestLang == null ? "eng" : guestLang);
        guestInfo.setRoomType(roomType);
        CheckInVO checkinVo = PmsUtils.getCheckInVO(guestInfo);
        guestInfo.setExpressCheckout(checkinVo.getExpressCheckout());
        guestInfo.setViewBill(checkinVo.getViewBill());
        guestInfo.setViewMessage(checkinVo.isMessagesEnabled() ? "True" : "False");
        guestInfo.setDoNotDisturb(checkinVo.getDonotDisturb());
        JpaManager.getGuestInfoManager().save(guestInfo);
        return guestInfo;
    }

    public static GuestInfo updateGuestInfo(String roomId, GuestInfoFieldType fieldType, String fieldValue) throws IOException {
        LOG.info("update GuestInfo {} to {} for room:{}", new Object[]{fieldType, fieldValue, roomId});
        PmsUtils.checkPmsEnable();
        GuestInfoManager guestInfoMgr = JpaManager.getGuestInfoManager();
        List<GuestInfo> guestInfoList = guestInfoMgr.findGuestInfosByRoomid(roomId);
        if (guestInfoList.isEmpty()) {
            throw new IOException("roomId( " + roomId + " ) no exists");
        }
        GuestInfo guestInfo = guestInfoList.get(0);
        switch (fieldType) {
            case GuestName: {
                ReservationManager reservationMgr;
                Reservation reservation;
                if (fieldValue.equalsIgnoreCase(guestInfo.getGuestName())) break;
                guestInfo.setGuestName(fieldValue);
                guestInfoMgr.save(guestInfo);
                if (guestInfo.getOrderid() != null && (reservation = (reservationMgr = JpaManager.getReservationManager()).loadByKey(guestInfo.getOrderid())) != null && fieldValue.equalsIgnoreCase(reservation.getGuests())) {
                    reservation.setGuests(fieldValue);
                    reservationMgr.save(reservation);
                }
                if (!guestInfo.getCheckin().equalsIgnoreCase("Y")) break;
                CheckInVO checkinVO = PmsUtils.getCheckInVO(guestInfo);
                PmsUtils.sendData2Room(guestInfo.getRoomid(), (Devices tv) -> {
                    if (!PlatformUtils.isMasf2019Up(tv.getType())) {
                        PmsUtils.processCheckInVOForASTA(checkinVO);
                    }
                    return PmsJapitCommand.getUpdateGuestDetails(checkinVO);
                });
                break;
            }
            case GuestLanguage: {
                if (fieldValue.equalsIgnoreCase(guestInfo.getGuestLanguage())) break;
                guestInfo.setGuestLanguage(fieldValue);
                guestInfoMgr.save(guestInfo);
                if (PmsUtils.hasRFTVForRoom(roomId)) {
                    PlayoutUtils.addPMSAction(PmsAction.LanguageChange, roomId);
                }
                PmsUtils.sendUpdateGuestPreference(guestInfo);
                TriggerUtils.executePMSTrigger(PmsAction.LanguageChange, roomId, fieldValue);
                break;
            }
            case GuestDepartureDate: {
                if (fieldValue.equalsIgnoreCase(guestInfo.getDepartureDate())) break;
                guestInfo.setDepartureDate(fieldValue);
                guestInfoMgr.save(guestInfo);
                CheckInVO checkinVO = PmsUtils.getCheckInVO(guestInfo);
                String respData = PmsJapitCommand.getUpdateRoomStatus(checkinVO);
                PmsUtils.sendData2Room(roomId, respData);
            }
        }
        return guestInfo;
    }

    public static GuestInfo renameGuest(String guestId, String guestName, String roomId) throws IOException {
        LOG.info("renameGuest {} to {} for room:{}", guestId, guestName, roomId);
        PmsUtils.checkPmsEnable();
        GuestInfoManager guestInfoMgr = JpaManager.getGuestInfoManager();
        if ("None".equalsIgnoreCase(guestId)) {
            LOG.info("guest not exist, create new guest");
            GuestInfo guestInfo = PmsUtils.createGuest(guestName, null, roomId, null, null);
            return guestInfo;
        }
        List<GuestInfo> guestInfoList = guestInfoMgr.findByBeginGuestid(PmsUtils.extractGuestId(guestId));
        if (guestInfoList.isEmpty()) {
            throw new IOException("guest not exist,id=" + guestId);
        }
        for (GuestInfo guestInfo : guestInfoList) {
            Reservation reservation;
            String orgName = guestInfo.getGuestName();
            ReservationManager reservationMgr = JpaManager.getReservationManager();
            if (guestInfo.getOrderid() != null && (reservation = reservationMgr.loadByKey(guestInfo.getOrderid())) != null) {
                String replaceName = reservation.getGuests().replace(orgName, guestName);
                reservation.setGuests(replaceName);
                reservationMgr.save(reservation);
            }
            guestInfo.setGuestName(guestName);
            guestInfoMgr.save(guestInfo);
            if (!guestInfo.getCheckin().equalsIgnoreCase("Y")) continue;
            CheckInVO checkinVO = PmsUtils.getCheckInVO(guestInfo);
            PmsUtils.sendData2Room(guestInfo.getRoomid(), (Devices tv) -> {
                if (!PlatformUtils.isMasf2019Up(tv.getType())) {
                    PmsUtils.processCheckInVOForASTA(checkinVO);
                }
                return PmsJapitCommand.getUpdateGuestDetails(checkinVO);
            });
        }
        return guestInfoList.get(0);
    }

    public static GuestInfo renameGroup(String guestId, String groupName, String roomId) throws IOException {
        PmsUtils.checkPmsEnable();
        LOG.info("renameGroup");
        GuestInfoManager guestInfoMgr = JpaManager.getGuestInfoManager();
        GuestInfo guestInfo = guestInfoMgr.loadByKey(guestId);
        if (guestInfo == null) {
            guestInfo = PmsUtils.createGuest(null, groupName, roomId, null, null);
            LOG.info("guest not exits, create new by groupname");
        } else {
            guestInfo.setGroupName(groupName);
            guestInfoMgr.save(guestInfo);
        }
        if (guestInfo.getCheckin().equalsIgnoreCase("Y")) {
            CheckInVO checkinVO = PmsUtils.getCheckInVO(guestInfo);
            PmsUtils.sendData2Room(guestInfo.getRoomid(), (Devices tv) -> {
                if (!PlatformUtils.isMasf2019Up(tv.getType())) {
                    PmsUtils.processCheckInVOForASTA(checkinVO);
                }
                return PmsJapitCommand.getUpdateGuestDetails(checkinVO);
            });
        }
        String roomid = String.valueOf(guestInfo.getRoomid());
        TriggerUtils.executePMSTrigger(PmsAction.GroupChange, roomid, groupName);
        return guestInfo;
    }

    public static void processCheckout(String roomid) throws IOException {
        PmsUtils.checkPmsEnable();
        if (StringUtils.isEmpty(roomid)) {
            return;
        }
        LOG.info("processCheckout {}", (Object)roomid);
        PmsUtils.updatePmsConnectionInfo(roomid);
        PmsUtils.getGuestInfosByRoomId(roomid).forEach(CastServerUtils::sendCheckoutNoticeToOpenApiServer);
        TriggerUtils.executePMSTrigger(PmsAction.CheckOut, roomid, null);
        PmsUtils.processAfterCheckout(String.valueOf(roomid));
        try {
            PmsUtils.checkout2TV(roomid);
        }
        catch (IOException e) {
            LOG.warn(e.getMessage());
            throw e;
        }
    }

    public static void processCheckin(String guestId) throws IOException {
        PmsUtils.processCheckin(guestId, null);
    }

    public static GuestInfo getGuest(String guestId) throws IOException {
        GuestInfo gi = JpaManager.getGuestInfoManager().loadByKey(guestId);
        if (gi == null) {
            throw new IOException("guest not exist,id=" + guestId);
        }
        return gi;
    }

    private static boolean isSupportRoomType() {
        SIConfig siconfig = JpaManager.getSIConfigManager().getSIConfig();
        return StringUtils.equalsIgnoreCase(siconfig.getSupportRoomtype(), Boolean.TRUE.toString());
    }

    public static void processCheckin(String guestId, String checkoutDateTime) throws IOException {
        Date tempData;
        PmsUtils.checkPmsEnable();
        LOG.info("processCheckin {}", (Object)guestId);
        GuestInfo gi = PmsUtils.getGuest(guestId);
        gi.setCheckin("Y");
        if (StringUtils.isEmpty(gi.getArrivalDate())) {
            gi.setArrivalDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        }
        if (StringUtils.isEmpty(gi.getCheckinTime())) {
            gi.setCheckinTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        }
        if (StringUtils.isNoneBlank(checkoutDateTime) && (tempData = TpvDateUtils.parseDateString(checkoutDateTime, "yyyy-MM-dd HH:mm")) != null) {
            gi.setDepartureDate(new SimpleDateFormat("dd/MM/yyyy").format(tempData));
            gi.setCheckoutTime(checkoutDateTime);
        }
        JpaManager.getGuestInfoManager().save(gi);
        PmsUtils.setUpdatedGuestInfo(guestId);
        PmsUtils.updatePmsConnectionInfo(gi.getRoomid());
        CastServerUtils.sendCheckInNoticeToOpenApiServer(gi);
        IOException a = null;
        try {
            PmsUtils.checkin2TV(gi, true);
        }
        catch (IOException e) {
            a = e;
        }
        String roomId = gi.getRoomid();
        TriggerUtils.executePMSTrigger(PmsAction.CheckIn, roomId, null);
        if (gi.getGroupName() != null) {
            TriggerUtils.executePMSTrigger(PmsAction.GroupChange, roomId, gi.getGroupName());
        }
        if (gi.getGuestLanguage() != null) {
            TriggerUtils.executePMSTrigger(PmsAction.LanguageChange, roomId, gi.getGuestLanguage());
        }
        if (PmsUtils.isSupportRoomType() && gi.getRoomType() != null) {
            TriggerUtils.executePMSTrigger(PmsAction.RoomType, roomId, gi.getRoomType());
        }
        if (a != null) {
            throw a;
        }
    }

    private static void updatePmsConnectionInfo(String roomId) {
        PmsStatus ps = PmsUtils.getPmsStatus();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        ps.setPmsconnectioninfo(String.format("%s - %s", df.format(new Date()), roomId));
        JpaManager.getPmsStatusManager().save(ps);
    }

    public static GuestInfo switchCheckInStatus(String guestId, boolean toCheckin) throws IOException {
        PmsUtils.checkPmsEnable();
        LOG.info("switchCheckInStatus guestId={} to {}", (Object)guestId, (Object)(toCheckin ? "checkin" : "checkout"));
        GuestInfo gi = PmsUtils.getGuest(guestId);
        boolean checkin = gi.getCheckin().equalsIgnoreCase("Y");
        if (toCheckin == checkin) {
            LOG.info("guest {} already {}", (Object)guestId, (Object)(toCheckin ? "checkin" : "checkout"));
            return gi;
        }
        if (checkin) {
            PmsUtils.processCheckout(gi.getRoomid());
            return null;
        }
        PmsUtils.processCheckin(guestId);
        return PmsUtils.getGuest(guestId);
    }

    public static void deleteMessage(String id) {
        LOG.info("deleteMessage {}", (Object)id);
        JpaManager.getMessageManager().deleteByKey(id);
    }

    private static void cleanMessages(String roomId) {
        LOG.info("cleanMessages {}", (Object)roomId);
        JpaManager.getMessageManager().deleteByRoomId(roomId);
    }

    public static void setMessageUpdated() {
        PollingWebSocket.notifyMessageUpdate();
    }

    public static void setUpdatedGuestInfo(String guestId) {
        LOG.info("set guestinfo updated:{}", (Object)guestId);
        PollingWebSocket.notifyGuestInfoUpdate(guestId);
    }

    private static void checkPmsEnable() throws IOException {
        if (!PmsUtils.isPmsEnabled()) {
            throw new IOException("PMS is disabled.");
        }
    }

    public static GuestInfo switchDoNotDisturb(String id) throws IOException {
        PmsUtils.checkPmsEnable();
        LOG.info("switchDoNotDisturb {}", (Object)id);
        GuestInfo gi = PmsUtils.getGuest(id);
        PmsStatus ps = PmsUtils.getPmsStatus();
        String pmsStatusDoNotDisturb = ps.getDoNotDisturb();
        if ("Off".equalsIgnoreCase(pmsStatusDoNotDisturb)) {
            throw new IOException("Cannot turn DoNotDisturb on TV ON: DootDisturb On TV is off in PMS Admin tab");
        }
        boolean doNotDisturb = "true".equalsIgnoreCase(gi.getDoNotDisturb());
        String donotDisturbFlag = doNotDisturb ? "false" : "true";
        gi.setDoNotDisturb(donotDisturbFlag);
        JpaManager.getGuestInfoManager().save(gi);
        PmsUtils.sendUpdateGuestPreference(gi);
        return gi;
    }

    public static GuestInfo switchViewBill(String id) throws SQLException, IOException {
        PmsUtils.checkPmsEnable();
        LOG.info("switchViewBill {}", (Object)id);
        PmsStatus ps = PmsUtils.getPmsStatus();
        String pmsStatusBillonTv = ps.getBillontv();
        if ("Off".equalsIgnoreCase(pmsStatusBillonTv)) {
            throw new SQLException("Cannot turn bill on TV ON: Bill On TV is off in PMS Admin tab");
        }
        GuestInfo gi = PmsUtils.getGuest(id);
        boolean currentEnabled = "True".equalsIgnoreCase(gi.getViewBill());
        String viewBillFlag = currentEnabled ? "False" : "True";
        gi.setViewBill(viewBillFlag);
        JpaManager.getGuestInfoManager().save(gi);
        PmsUtils.sendUpdatePmsFeatures(gi);
        return gi;
    }

    public static GuestInfo switchExpressCheckout(String id) throws SQLException, IOException {
        PmsUtils.checkPmsEnable();
        LOG.info("switchExpressCheckout {}", (Object)id);
        PmsStatus ps = PmsUtils.getPmsStatus();
        String pmsStatusExpressCheckout = ps.getExpresscheckout();
        if ("Off".equalsIgnoreCase(pmsStatusExpressCheckout)) {
            throw new SQLException("Cannot turn express checkout ON, express checkout is turned off in PMS Admin tab");
        }
        GuestInfo gi = PmsUtils.getGuest(id);
        boolean currentEnabled = "True".equalsIgnoreCase(gi.getExpressCheckout());
        String expressCheckoutFlag = currentEnabled ? "False" : "True";
        gi.setExpressCheckout(expressCheckoutFlag);
        JpaManager.getGuestInfoManager().save(gi);
        PmsUtils.sendUpdatePmsFeatures(gi);
        return gi;
    }

    public static void updateMessageStatus(String msgId, String newStatus) {
        TmsUtils tms = PmsUtils.getTmsInstance();
        if (tms != null) {
            try {
                MessageStatus status = MessageStatus.valueOf(newStatus);
                Message msg = JpaManager.getMessageManager().loadByMsgId(msgId);
                msgId = msg.getId();
                tms.updateMessageStatus(msgId, status);
            }
            catch (Exception e) {
                LOG.info(e.getMessage());
            }
        }
    }

    private static void sendUpdateGuestPreference(GuestInfo gi) throws IOException {
        if (gi == null || !gi.getCheckin().equalsIgnoreCase("Y")) {
            LOG.warn("Guest not exist or not checked in");
            return;
        }
        LOG.info("sendUpdateGuestPreference {}", (Object)gi.getGuestId());
        CheckInVO checkinVO = PmsUtils.getCheckInVO(gi);
        String respData = PmsJapitCommand.getUpdateGuestPreference(checkinVO);
        PmsUtils.sendData2Room(gi.getRoomid(), respData);
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
        if (gi == null || !gi.getCheckin().equalsIgnoreCase("Y")) {
            throw new IOException("Guest not exist or not checked in");
        }
        LOG.info("sendUpdatePmsFeatures {}", (Object)gi.getGuestId());
        String respData = PmsJapitCommand.getUpdatePMSFeatures(PmsUtils.getCheckInVO(gi));
        PmsUtils.sendData2Room(gi.getRoomid(), respData);
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
            LOG.warn("tv <{}> not support alarm status query", (Object)tv.getTvuniqueid());
            return null;
        }
        ApplicationControlCmd applicationControlCmd = new ApplicationControlCmd();
        applicationControlCmd.setCmdSvc(JapitCommand.CommandSvc.WebListeningServices);
        applicationControlCmd.setSvcVer("1.0");
        applicationControlCmd.setCmdType(JapitCommand.CommandType.Request);
        applicationControlCmd.setCmdDetail("RequestApplicationAttributesValueDetails", new JSONArray("[{ \"ApplicationName\": \"Alarm\",    \"RequestListForApplicationAttributesValue\":[      \"AlarmTime\",      \"AlarmEnabled\"   ]}]"));
        try {
            String result = JAPITUtils.sendJapitCommand(tv, applicationControlCmd.generateCommand());
            if (PmsUtils.isErrorSvc(result)) {
                LOG.warn("ErrorSvc,enable applicationControlService then try again");
                PmsUtils.enableApplicationControlService(tv, true, true);
                result = JAPITUtils.sendJapitCommand(tv, applicationControlCmd.generateCommand());
            }
            return PmsUtils.extractPmsAlarmStatus(result);
        }
        catch (Exception e) {
            LOG.error("query alarm status failure", e);
            return null;
        }
    }

    public static boolean disablePmsAlarm(Devices tv) {
        if (!PlatformUtils.isSupportAlarm(tv)) {
            LOG.warn("tv <{}> not support disable alarm", (Object)tv.getTvuniqueid());
            return false;
        }
        if (!PmsUtils.isPmsAlarmEnable(tv)) {
            return true;
        }
        ApplicationControlCmd applicationControlCmd = new ApplicationControlCmd();
        applicationControlCmd.setCmdSvc(JapitCommand.CommandSvc.WebListeningServices);
        applicationControlCmd.setSvcVer("1.0");
        applicationControlCmd.setCmdType(JapitCommand.CommandType.Change);
        applicationControlCmd.setCmdDetail("ApplicationDetails", new JSONObject("{\"ApplicationName\":\"Alarm\",\"ApplicationType\":\"Native\",\"ApplicationAttributes\":{  \"AlarmEnabled\":\"No\"}}"));
        try {
            boolean isDisabledSuccess;
            JAPITUtils.sendJapitCommand(tv, applicationControlCmd.generateCommand());
            PmsAlarmStatus pmsAlarmStatus = PmsUtils.getPmsAlarmStatusForDevice(tv);
            boolean bl = isDisabledSuccess = pmsAlarmStatus != null && !pmsAlarmStatus.isAlarmEnabled();
            if (isDisabledSuccess) {
                CmndMetricsTask.writePMSInfoToMetricsLog(tv, "alarm_disabled");
            }
            return isDisabledSuccess;
        }
        catch (Exception e) {
            LOG.error("disable alarm status failure", e);
            return false;
        }
    }

    public static boolean updatePmsAlarmTime(Devices tv, String alarmTime) {
        if (!PlatformUtils.isSupportAlarm(tv)) {
            LOG.warn("tv <{}> not support update alarm time", (Object)tv.getTvuniqueid());
            return false;
        }
        boolean isAlarmEnabled = PmsUtils.isPmsAlarmEnable(tv);
        ApplicationControlCmd applicationControlCmd = new ApplicationControlCmd();
        applicationControlCmd.setCmdSvc(JapitCommand.CommandSvc.WebListeningServices);
        applicationControlCmd.setSvcVer("4.0");
        applicationControlCmd.setCmdType(JapitCommand.CommandType.Change);
        applicationControlCmd.setCmdDetail("ApplicationDetails", new JSONObject("{\"ApplicationName\":\"Alarm\",\"ApplicationType\":\"Native\",\"ApplicationAttributes\":{  \"AlarmTime\":\"" + alarmTime + "\",  \"AlarmRingingVolume\":" + PmsUtils.getPmsStatus().getAlarmRingingVolume() + "}}"));
        applicationControlCmd.setCmdDetail("ApplicationState", "Activate");
        try {
            String result = JAPITUtils.sendJapitCommand(tv, applicationControlCmd.generateCommand());
            boolean updateResult = StringUtils.equalsIgnoreCase(PmsUtils.extractPmsAlarmStatus(result).getAlarmTime(), alarmTime);
            if (updateResult) {
                CmndMetricsTask.writePMSInfoToMetricsLog(tv, isAlarmEnabled ? "alarm_time_update" : "alarm_set");
            }
            return updateResult;
        }
        catch (Exception e) {
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
        JSONObject alarmInfo = jsonResult.getJSONObject("CommandDetails").getJSONArray("ApplicationAttributesValue").getJSONObject(0).getJSONObject("ApplicationAttributes");
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
        PmsAlarmStatus pmsAlarmStatus = PmsUtils.getPmsAlarmStatusForDevice(tv);
        return pmsAlarmStatus != null && pmsAlarmStatus.isAlarmEnabled();
    }

    static {
        executor = new ThreadPoolExecutor(5, 50, 50000L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1000), new TpvNamedThreadFactory("pms-sync-pool"));
        timerMap = new HashMap<String, Timer>();
        rand = new Random();
    }

    public static enum RequestType {
        RequestBill,
        RequestRefresh,
        RequestExpressCheckout,
        RequestMessage;

    }

    public static enum GuestInfoFieldType {
        GuestName,
        GuestLanguage,
        GuestDepartureDate;

    }

    public static enum PmsSyncStatus {
        NeedSync,
        Synced,
        Syncing;

    }

    public static enum MessageStatus {
        New,
        UnRead,
        Read,
        Delete;

    }

    public static enum PmsAction {
        CheckIn,
        CheckOut,
        LanguageChange,
        GroupChange,
        RoomType;

    }
}

