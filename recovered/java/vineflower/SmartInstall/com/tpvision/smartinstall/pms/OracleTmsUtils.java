package com.tpvision.smartinstall.pms;

import com.tpvision.smartinstall.dao.core.GuestInfo;
import com.tpvision.smartinstall.dao.core.Message;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.WakeupInfoManager;
import com.tpvision.smartinstall.util.SocketClientCallback;
import com.tpvision.smartinstall.util.SocketClientHelper;
import com.tpvision.smartinstall.util.TpvDateUtils;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OracleTmsUtils extends TmsUtils {
   private static final String ERROR_TCP_RETURN_ERROR = "Error: TCP return error.";
   private static final String TIME_HHMMSS = "HHmmss";
   private static final String DATE_YYMMDD = "yyMMdd";
   private static final String DATE_YYYYMMDD = "yyyyMMdd";
   private static final String BILLDATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
   private static final byte[] START_FLAG = new byte[]{2};
   private static final byte[] END_FLAG = new byte[]{3};
   private static final Logger LOG = LoggerFactory.getLogger(OracleTmsUtils.class);
   public static final String CONFIG_NAME = "oracleTMS";
   private static final long CHECK_ALIVE_INTERVAL = 300000L;
   private boolean resyncDatabase = false;
   private boolean isDatabaseSync;
   private boolean isFirstBillItem = true;
   protected String address;
   protected int port;
   protected String encoding;
   private SocketClientHelper.SocketClient socketClient;
   private boolean isOnline;
   private long lastCheckAlive;

   private void createSocketConnection() {
      if (this.isSocketAlive()) {
         LOG.info("Client is alive.");
      } else {
         LOG.info("Create Socket Connection");
         if (this.socketClient != null) {
            SocketClientHelper.removeClient(this.address, this.port);
            this.socketClient = null;
         }

         try {
            this.socketClient = SocketClientHelper.createClient(this.address, this.port, new SocketClientCallback() {
               @Override
               public void onError(Throwable e) {
                  SocketClientHelper.removeClient(OracleTmsUtils.this.address, OracleTmsUtils.this.port);
                  OracleTmsUtils.this.setConnected(false);
               }

               @Override
               public void onConnected() {
                  OracleTmsUtils.LOG.info("Client ({}:{}) connected", OracleTmsUtils.this.address, OracleTmsUtils.this.port);
                  OracleTmsUtils.this.setConnected(true);
                  OracleTmsUtils.this.setLastStatus("Ok");
               }

               @Override
               public void onReceived(byte[] data) {
                  String res = new String(data, Charset.forName(OracleTmsUtils.this.encoding));
                  OracleTmsUtils.LOG.info("Client({}:{}) Received:{}", OracleTmsUtils.this.address, OracleTmsUtils.this.port, res);

                  try {
                     OracleTmsUtils.this.processReceivedData(res);
                  } catch (IOException e) {
                     OracleTmsUtils.LOG.error(e.getMessage(), e);
                  }
               }

               @Override
               public void onSent(byte[] data) {
                  OracleTmsUtils.LOG.info("Client({}:{}) Sent:{}", OracleTmsUtils.this.address, OracleTmsUtils.this.port, new String(data).trim());
               }

               @Override
               public void onDisconnected() {
                  OracleTmsUtils.LOG.info("Client({}:{}) disconnected", OracleTmsUtils.this.address, OracleTmsUtils.this.port);
                  SocketClientHelper.removeClient(OracleTmsUtils.this.address, OracleTmsUtils.this.port);
                  OracleTmsUtils.this.setConnected(false);
               }
            });
         } catch (IOException e) {
            LOG.error(e.getMessage());
            this.setConnected(false);
         }
      }
   }

   private boolean isSocketAlive() {
      return this.socketClient != null && this.socketClient.isAlive();
   }

   @Override
   public void requestBill(String roomid) {
      GuestInfo gi = PmsUtils.getGuestInfoByRoomId(roomid);
      Integer room = Integer.parseInt(roomid);
      this.sendCommandToTMS(String.format(Locale.ENGLISH, "XR|RN%d|G#%s|", room, gi.getOrderid()));
      PmsUtils.setRequestTimer(PmsUtils.RequestType.RequestBill, roomid);
   }

   @Override
   public void refresh() {
      LOG.info("request to sync data");
      this.sendCommandToTMS(String.format(Locale.ENGLISH, "DR|DA%s|TI%s|", this.getDate(), this.getTime()));
   }

   @Override
   public void requestExpressCheckout(String roomid) {
      LOG.info("requestExpressCheckout:{}", roomid);
      String date = TpvDateUtils.formatLocalDate(new Date(), "yyMMdd");
      String time = TpvDateUtils.formatLocalDate(new Date(), "HHmmss");
      GuestInfo gi = PmsUtils.getGuestInfoByRoomId(roomid);
      String reservationid = gi.getOrderid();
      String balance = PmsUtils.getBalance(roomid);
      if (balance.contains(".")) {
         balance = balance.replace(".", "");
      }

      this.sendCommandToTMS(String.format(Locale.ENGLISH, "XC|RN%s|G#%s|BA%s|DA%s|TI%s|", roomid, reservationid, balance, date, time));
      PmsUtils.setRequestTimer(PmsUtils.RequestType.RequestExpressCheckout, roomid);
   }

   @Override
   public boolean isSupportExpressCheckout() {
      return true;
   }

   @Override
   public boolean isInstantBill() {
      return true;
   }

   @Override
   public void requestRefresh(String roomid) {
   }

   @Override
   public String isConnectedTms() {
      try {
         LOG.debug("Tms ip/domain: {}", this.address);
         if (!this.isSocketAlive()) {
            this.createSocketConnection();
            Thread.sleep(5000L);
         }

         if (System.currentTimeMillis() - this.lastCheckAlive > 300000L) {
            this.lastCheckAlive = System.currentTimeMillis();
            return this.sendLinkStartCommand();
         } else {
            return this.getLastStatus();
         }
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         return "Error: Unknown.";
      }
   }

   @Override
   public void onConnected() {
      LOG.info("Oracle FIAS Connected");
   }

   @Override
   public void onDisconnected() {
      super.onDisconnected();
      LOG.info("Oracle FIAS Disconnected");
      this.isOnline = false;
   }

   private String getDate() {
      return TpvDateUtils.formatLocalDate(new Date(), "yyMMdd");
   }

   private String getTime() {
      return TpvDateUtils.formatLocalDate(new Date(), "HHmmss");
   }

   private String sendLinkActiveCommand() {
      return this.sendCommandToTMS(String.format(Locale.ENGLISH, "LA|DA%s|TI%s|", this.getDate(), this.getTime())) ? "Ok" : "Error: TCP return error.";
   }

   private String sendLinkStartCommand() {
      return this.sendCommandToTMS(String.format(Locale.ENGLISH, "LS|DA%s|TI%s|", this.getDate(), this.getTime())) ? "Ok" : "Error: TCP return error.";
   }

   private String sendLinkEndCommand() {
      return this.sendCommandToTMS(String.format(Locale.ENGLISH, "LE|DA%s|TI%s|", this.getDate(), this.getTime())) ? "Ok" : "Error: TCP return error.";
   }

   public boolean sendCommandToTMS(String data) {
      if (null == this.address || "".equalsIgnoreCase(this.address) || this.port == -1) {
         LOG.info("Host ip/port is wrong.");
         return false;
      } else if (null == data || data.isEmpty()) {
         LOG.info("Data is empty.");
         return false;
      } else if (!this.isSocketAlive()) {
         LOG.info("Client isn't alive.");
         return false;
      } else {
         String newData = new String(START_FLAG) + data + new String(END_FLAG);
         LOG.info("SI >>>>> FIAS:{}", newData);
         return this.socketClient.sendData(newData.getBytes());
      }
   }

   public static Map<String, String> parseNotification(String notification) {
      notification = notification.trim();
      String[] fields = null != notification ? notification.split("\\|") : null;
      if (null != fields && fields.length > 0) {
         Map<String, String> map = new HashMap<>();
         map.put("ID", fields[0]);

         try {
            for (int i = 1; i < fields.length; i++) {
               if (fields[i].length() >= 2) {
                  map.put(fields[i].substring(0, 2), fields[i].substring(2));
               }
            }

            return map;
         } catch (Exception e) {
            LOG.error(e.getMessage(), e);
         }
      }

      return new HashMap<>();
   }

   public void processReceivedData(String data) throws IOException {
      LOG.info("FIAS >>>>> SI:{}", data);
      Map<String, String> record = parseNotification(data);
      if (record.isEmpty()) {
         LOG.error("parsed data:{} is null,exit", data);
      } else {
         switch ((String)record.get("ID")) {
            case "LS":
               this.processLinkStart(record);
               break;
            case "LA":
               LOG.info("Oracle TMS LA");
               if (this.isOnline && !this.isDatabaseSync) {
                  this.refresh();
               }
               break;
            case "LE":
               this.isOnline = false;
               LOG.info("Oracle TMS LE,link offline");
               this.sendLinkStartCommand();
               break;
            case "GI":
               this.processCheckedin(record);
               break;
            case "GO":
               this.processCheckedout(record);
               break;
            case "GC":
               this.processGuestChanged(record);
               break;
            case "XI":
               this.processBillitems(record);
               break;
            case "XB":
               this.processBalance(record);
               break;
            case "XC":
               this.processExpressCheckout(record);
               break;
            case "DS":
               LOG.info("Oracle TMS database resync start");
               this.resyncDatabase = false;
               break;
            case "DE":
               LOG.info("Oracle TMS database resync end");
               this.resyncDatabase = false;
               this.isDatabaseSync = true;
               break;
            case "XL":
            case "XT":
               this.processMessageText(record);
               break;
            case "XD":
               this.processDeleteMessage(record);
               break;
            case "WR":
               this.processWakeup(record, false);
               break;
            case "WC":
               this.processWakeup(record, true);
               break;
            default:
               LOG.info("Unkown Oracle TMS Command:{}", record.get("ID"));
         }
      }
   }

   private void processWakeup(Map<String, String> record, boolean clearFlag) {
      String roomNo = record.getOrDefault("RN", "");
      String wakeupTime = formatDateTime(record.get("DA"), record.get("TI"), "yyyy-MM-dd HH:mm:ss");
      WakeupInfoManager wakeupInfoManager = JpaManager.getWakeupInfoManager();
      if (clearFlag) {
         LOG.info("Oracle TMS clear wakeup:{},{}", roomNo, wakeupTime);
         if (StringUtils.isEmpty(wakeupTime)) {
            wakeupInfoManager.deleteAllWakeups(roomNo);
         } else {
            wakeupInfoManager.deleteWakeup(roomNo, wakeupTime);
         }
      } else {
         LOG.info("Oracle TMS set wakeup:{},{}", roomNo, wakeupTime);
         wakeupInfoManager.insertWakeup(roomNo, "", wakeupTime);
      }
   }

   private void processMessageText(Map<String, String> record) {
      LOG.info("Oracle TMS send message text");
      if (record.get("MI") != null) {
         String msgtime = formatDateTime(record.get("DA"), record.get("TI"), "yyyy-MM-dd HH:mm:ss");
         Element rootElt = DocumentHelper.createDocument().addElement("messagetextresults");
         rootElt.addAttribute("resno", record.get("G#"));
         rootElt.addElement("room").setText(record.getOrDefault("RN", ""));
         rootElt.addElement("guestid").setText(record.getOrDefault("G#", ""));
         rootElt.addElement("msgid").setText(record.getOrDefault("MI", ""));
         rootElt.addElement("msgtext").setText(record.getOrDefault("MT", ""));
         rootElt.addElement("datetime").setText(null != msgtime ? msgtime : "2018-05-10 12:00:00");
         PmsUtils.saveMessageTextResults2DB(rootElt);
      }
   }

   private void processDeleteMessage(Map<String, String> record) {
      String msgId = record.get("MI");
      LOG.info("Oracle TMS delete message: {}", record.get("MT"));
      if (msgId != null) {
         PmsUtils.deleteMessage(msgId);
      }
   }

   private void processExpressCheckout(Map<String, String> record) {
      LOG.info("Oracle TMS express checkout answer");
      Element rootElt = DocumentHelper.createDocument().addElement("expresscheckoutresults");
      rootElt.addAttribute("resno", record.get("G#"));
      String roomId = record.getOrDefault("RN", "");
      rootElt.addElement("room").setText(roomId);
      boolean success = record.get("AS") != null && record.get("AS").equalsIgnoreCase("OK");
      rootElt.addElement("status").setText(String.valueOf(success));
      String billDateTime = formatDateTime(record.getOrDefault("DA", ""), record.getOrDefault("TI", ""), "dd/MM/yyyy HH:mm:ss");
      rootElt.addElement("datetime").setText(null != billDateTime ? billDateTime : "10/05/2018 12:00:00");
      PmsUtils.processExpressCheckoutFromPms(rootElt);
      PmsUtils.removeRequestTimer(PmsUtils.RequestType.RequestExpressCheckout, roomId);
   }

   private String processBillAmount(String ba) {
      if (ba.contains(".")) {
         return ba;
      }

      Double amount = Double.parseDouble(ba) * 0.01;
      return String.format("%.2f", amount);
   }

   private void processBalance(Map<String, String> record) {
      LOG.info("Oracle TMS send bill balance");
      Element rootElt = DocumentHelper.createDocument().addElement("roombillresults");
      rootElt.addAttribute("resno", record.get("G#"));
      rootElt.addElement("room").setText(record.getOrDefault("RN", ""));
      String ba = record.getOrDefault("BA", "");
      String billDateTime = formatDateTime(
         record.getOrDefault("DA", TpvDateUtils.formatLocalDate(new Date(), "yyMMdd")),
         record.getOrDefault("TI", TpvDateUtils.formatLocalDate(new Date(), "HHmmss")),
         "dd/MM/yyyy HH:mm:ss"
      );
      ba = this.processBillAmount(ba);
      rootElt.addElement("balance").setText(ba);
      rootElt.addElement("totalDateTime").setText(billDateTime);
      GuestInfo guest = PmsUtils.saveBillBalance2DB(rootElt);
      if (!this.resyncDatabase) {
         PmsUtils.sendBill2TV(guest);
      }

      this.isFirstBillItem = true;
   }

   private void processBillitems(Map<String, String> record) {
      LOG.info("Oracle TMS send bill item");
      Element rootElt = DocumentHelper.createDocument().addElement("roombillresults");
      rootElt.addAttribute("resno", record.get("G#"));
      String roomId = record.getOrDefault("RN", "");
      rootElt.addElement("room").setText(roomId);
      rootElt.addElement("guestid").setText(record.getOrDefault("G#", ""));
      rootElt.addElement("code").setText(record.getOrDefault("DC", ""));
      rootElt.addElement("description").setText(record.getOrDefault("BD", ""));
      rootElt.addElement("displayflag").setText("Y".equalsIgnoreCase(record.getOrDefault("FD", "Y")) ? "Yes" : "No");
      String bi = record.getOrDefault("BI", "");
      bi = this.processBillAmount(bi);
      rootElt.addElement("charge").setText(bi);
      String billDateTime = formatDateTime(record.getOrDefault("DA", ""), record.getOrDefault("TI", ""), "dd/MM/yyyy HH:mm:ss");
      rootElt.addElement("datetime").setText(null != billDateTime ? billDateTime : "10/05/2018 12:00:00");
      if (this.isFirstBillItem) {
         PmsUtils.deletePreBillItems(roomId);
         this.isFirstBillItem = false;
      }

      PmsUtils.saveBillItem2DB(rootElt);
      PmsUtils.removeRequestTimer(PmsUtils.RequestType.RequestBill, roomId);
   }

   private void processGuestChanged(Map<String, String> record) throws IOException {
      LOG.info("Oracle TMS edit guest");
      String oldRoomId = record.get("RO");
      String roomId = StringUtils.isEmpty(oldRoomId) ? record.get("RN") : oldRoomId;
      GuestInfo gi = PmsUtils.getGuestInfoByRoomId(roomId);
      if (gi == null) {
         throw new IOException("guest not exists:" + roomId);
      }

      Element rootElt = DocumentHelper.createDocument().addElement("editguestresults");
      rootElt.addAttribute("resno", record.getOrDefault("G#", gi.getOrderid()));
      rootElt.addElement("room").setText(roomId);
      rootElt.addElement("guestid").setText(PmsUtils.extractGuestId(gi.getGuestId()));
      rootElt.addElement("title").setText(record.getOrDefault("GT", gi.getTitle()));
      String[] names = gi.getGuestName().split(" ");
      rootElt.addElement("last").setText(record.getOrDefault("GN", names.length > 0 ? names[1] : ""));
      rootElt.addElement("first").setText(record.getOrDefault("GF", names[0]));
      rootElt.addElement("lang").setText(record.getOrDefault("GL", gi.getGuestLanguage()));
      rootElt.addElement("group").setText(record.getOrDefault("GG", gi.getGroupName()));
      String arrival = formatDate(record.getOrDefault("GA", gi.getArrivalDate()), "dd/MM/yyyy");
      String departure = formatDate(record.getOrDefault("GD", gi.getDepartureDate()), "dd/MM/yyyy");
      rootElt.addElement("arrival").setText(arrival);
      rootElt.addElement("departure").setText(departure);
      rootElt.addElement("tv").setText("Standard");
      rootElt.addElement("viewbill").setText("True");
      rootElt.addElement("expressco").setText("False");
      if (!this.resyncDatabase) {
         if (!StringUtils.isEmpty(oldRoomId)) {
            PmsUtils.changeGuestRoom(oldRoomId, record.get("RN"));
         } else {
            GuestInfo guest = PmsUtils.handleGuestResults(rootElt);
            if (guest != null) {
               PmsUtils.setUpdatedGuestInfo(guest.getGuestId());
            }
         }
      }
   }

   private void processCheckedout(Map<String, String> record) throws IOException {
      LOG.info("Oracle TMS checkout");
      String roomid = record.getOrDefault("RN", null);
      if (roomid == null) {
         LOG.error("invalid roomid");
         throw new IOException("invalid roomId");
      }

      PmsUtils.processCheckout(roomid);
   }

   private void processCheckedin(Map<String, String> record) throws IOException {
      LOG.info("Oracle TMS checkin");
      Element rootElt = DocumentHelper.createDocument().addElement("checkinresults");
      rootElt.addAttribute("resno", record.get("G#"));
      rootElt.addElement("room").setText(record.getOrDefault("RN", ""));
      rootElt.addElement("guestid").setText(record.getOrDefault("G#", ""));
      rootElt.addElement("title").setText(record.getOrDefault("GT", ""));
      rootElt.addElement("last").setText(record.getOrDefault("GN", ""));
      rootElt.addElement("first").setText(record.getOrDefault("GF", ""));
      rootElt.addElement("lang").setText(record.getOrDefault("GL", ""));
      rootElt.addElement("group").setText(record.getOrDefault("GG", ""));
      String arrival = formatDate(record.getOrDefault("GA", ""), "dd/MM/yyyy");
      String departure = formatDate(record.getOrDefault("GD", ""), "dd/MM/yyyy");
      rootElt.addElement("arrival").setText(arrival);
      rootElt.addElement("departure").setText(departure);
      rootElt.addElement("tv").setText("Standard");
      String vrValue = record.getOrDefault("VR", "");
      switch (vrValue) {
         case "VA":
            rootElt.addElement("viewbill").setText("True");
            rootElt.addElement("expressco").setText("True");
            break;
         case "VN":
            rootElt.addElement("viewbill").setText("False");
            rootElt.addElement("expressco").setText("False");
            break;
         default:
            rootElt.addElement("viewbill").setText("True");
            rootElt.addElement("expressco").setText("False");
      }

      GuestInfo guest = PmsUtils.saveCheckinResults2DB(rootElt);
      if (!this.resyncDatabase) {
         PmsUtils.processCheckin(guest.getGuestId());
      }
   }

   private void processLinkStart(Map<String, String> record) {
      LOG.info("Oracle TMS LINK START");
      String[] INIT_CMDS = new String[]{
         "LR|RIGI|FLRNG#GSSFGNGLGGGTGFGAGD|",
         "LR|RIGC|FLRNG#GSROGNGLGGGTGFGAGDDATI|",
         "LR|RIGO|FLRNG#GSSFDATI|",
         "LR|RIXL|FLG#MIMTRNDATI|",
         "LR|RIXT|FLG#MIMTRNDATI|",
         "LR|RIXR|FLG#RN|",
         "LR|RIXI|FLG#RNF#FDBDBIDCDATI|",
         "LR|RIXB|FLG#RNBADATI|",
         "LR|RIXC|FLG#RNASDATI|",
         "LR|RIWR|FLRNDATI|",
         "LR|RIWA|FLRNDATIAS|",
         "LR|RIWC|FLRNDATI|",
         "LR|RIXD|FLG#MIMTRNDATI|",
         "LR|RIXM|FLG#RNMIRTDATI|"
      };
      this.sendCommandToTMS(String.format(Locale.ENGLISH, "LD|DA%s|TI%s|V#2.0.0|IFPB|", this.getDate(), this.getTime()));

      for (String cmd : INIT_CMDS) {
         this.sendCommandToTMS(cmd);

         try {
            Thread.sleep(200L);
         } catch (Exception e) {
            LOG.error(e.getMessage(), e);
         }
      }

      this.sendCommandToTMS(String.format(Locale.ENGLISH, "LA|DA%s|TI%s|", this.getDate(), this.getTime()));
      String pmsVersion = record.get("V#");
      if (null != pmsVersion && !pmsVersion.isEmpty()) {
         PmsUtils.updatePmsVersion(pmsVersion);
      }

      this.isOnline = true;
      LOG.info("FIAS Server is online");
   }

   public static String formatDate(String date, String format) {
      String sDate = TpvDateUtils.formatStringDate(date, "dd/MM/yyyy");
      return sDate == null ? "" : sDate;
   }

   public static String formatDateTime(String date, String time, String format) {
      String dDateTime = null;
      if (!"".equalsIgnoreCase(date) && !"".equalsIgnoreCase(time)) {
         try {
            String pattern = (date.length() == 6 ? "yyMMdd" : "yyyyMMdd") + "HHmmss";
            SimpleDateFormat sFormat = TpvDateUtils.getSimpleDateFormatWithEnglishLocale(pattern);
            Date sDateTime = sFormat.parse(date + time);
            SimpleDateFormat dFormat = TpvDateUtils.getSimpleDateFormatWithEnglishLocale(format);
            dDateTime = dFormat.format(sDateTime);
         } catch (ParseException e) {
            LOG.error(e.getMessage(), e);
         }

         return dDateTime;
      } else {
         return dDateTime;
      }
   }

   private static String parseWakeupDate(String wakeupTime) {
      String date = wakeupTime.split(" ")[0];
      StringBuilder res = new StringBuilder();

      for (String s : date.split("-")) {
         if (s.length() == 4) {
            res.append(s.substring(2));
         } else {
            res.append(s);
         }
      }

      return res.toString();
   }

   private static String parseWakeupTime(String wakeupTime) {
      String date = wakeupTime.split(" ")[1];
      StringBuilder res = new StringBuilder();

      for (String s : date.split(":")) {
         res.append(s);
      }

      return res.toString();
   }

   @Override
   public void stop() {
      LOG.info("stop oracle TMS");
      SocketClientHelper.removeClient(this.address, this.port);
   }

   @Override
   public void doStart() {
      LOG.info("start oracle TMS");
      this.isOnline = false;
      if (!this.isSocketAlive()) {
         this.createSocketConnection();
      }
   }

   @Override
   protected void doLoadConfigs() {
      LOG.info("FIAS configs:{}", this.config);
      this.address = this.config.optString("PMS_SERVER", "127.0.0.1");
      this.port = this.config.optInt("PMS_PORT", 20099);
      this.encoding = this.config.optString("Encoding", "UTF-8");
   }

   @Override
   public String getMappedLanguage(String srcLanguage) {
      String language = null;
      Map<String, String> map = new HashMap<>();
      map.put("EA", "eng");
      map.put("FR", "fre");
      map.put("GE", "ger");
      map.put("IT", "ita");
      map.put("JA", "jpn");
      map.put("SP", "spa");
      map.put("NL", "dut");
      map.put("AR", "ara");
      map.put("BG", "bul");
      map.put("HR", "hrv");
      map.put("CZ", "cze");
      map.put("DA", "dan");
      map.put("FI", "fin");
      map.put("GR", "gre");
      map.put("HE", "heb");
      map.put("HU", "hun");
      map.put("ID", "ind");
      map.put("GA", "gle");
      map.put("KK", "kaz");
      map.put("LV", "lav");
      map.put("LT", "lit");
      map.put("MK", "mac");
      map.put("NO", "nor");
      map.put("PL", "pol");
      map.put("PT", "por");
      map.put("RO", "ron");
      map.put("RU", "rus");
      map.put("SR", "srp");
      map.put("ZH", "chi");
      map.put("SK", "slk");
      map.put("SL", "slv");
      map.put("SV", "swe");
      map.put("TH", "tha");
      map.put("TR", "tur");
      map.put("UK", "ukr");
      map.put("VI", "vie");
      map.put("SQ", "alb");
      map.put("CR", "hrv");
      map.put("CS", "cze");
      map.put("DK", "dan");
      map.put("ET", "est");
      map.put("FN", "fin");
      map.put("EL", "gre");
      map.put("RI", "ind");
      map.put("NR", "nor");
      map.put("PO", "por");
      map.put("RL", "rus");
      map.put("RS", "rus");
      map.put("SC", "slk");
      map.put("SW", "swe");
      map.put("KO", "kor");
      map.put("KA", "geo");
      if (null != map.get(srcLanguage)) {
         language = map.get(srcLanguage);
      } else if (map.containsValue(srcLanguage)) {
         language = srcLanguage;
      } else {
         language = "eng";
      }

      return language;
   }

   @Override
   public void updateMessageStatus(String msgId, PmsUtils.MessageStatus newStatus) {
      Message msg = JpaManager.getMessageManager().loadByKey(msgId);
      if (msg == null) {
         LOG.info("msg {} not found", msgId);
      } else {
         String roomId = msg.getGuestIds();
         GuestInfo gi = PmsUtils.getGuestInfoByRoomId(roomId);
         String resId = gi.getOrderid();
         String outMsg = null;
         switch (newStatus) {
            case Read:
               outMsg = String.format(Locale.ENGLISH, "XM|RN%s|G#%s|MI%s|RT1|DA%s|TI%s|", roomId, resId, msgId, this.getDate(), this.getTime());
               break;
            case Delete:
               outMsg = String.format(Locale.ENGLISH, "XD|RN%s|G#%s|MI%s|DA%s|TI%s|", roomId, resId, msgId, this.getDate(), this.getTime());
               break;
            default:
               LOG.info("Message status {} not need update to PMS", newStatus);
         }

         if (!StringUtils.isEmpty(outMsg)) {
            this.sendCommandToTMS(outMsg);
         }
      }
   }

   @Override
   protected String getConfigName() {
      return "oracleTMS";
   }

   @Override
   public void responseWakeup(String roomId, String wakeupTime, String status) {
      String wakeupAs = String.format(Locale.ENGLISH, "WA|RN%s|DA%s|TI%s|AS%s|", roomId, parseWakeupDate(wakeupTime), parseWakeupTime(wakeupTime), status);
      this.sendCommandToTMS(wakeupAs);
   }
}
