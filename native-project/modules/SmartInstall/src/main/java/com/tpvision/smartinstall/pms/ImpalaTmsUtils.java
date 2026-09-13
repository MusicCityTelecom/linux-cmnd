package com.tpvision.smartinstall.pms;

import com.tpvision.smartinstall.dao.core.Billitem;
import com.tpvision.smartinstall.dao.core.GuestInfo;
import com.tpvision.smartinstall.dao.core.Reservation;
import com.tpvision.smartinstall.dao.core.Roominfo;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.schedule.CmndMetricsTask;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.TpvNamedThreadFactory;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImpalaTmsUtils extends TmsUtils {
   private static final String CHECKED_IN = "CHECKED_IN";
   private static final String CHECKED_OUT = "CHECKED_OUT";
   private static final int DEFAULT_TIMEOUT = 20000;
   public static final String BASE_URL = "https://api.getimpala.com/v2/hotel";
   public static final String CONFIG_NAME = "impala";
   private static final Logger LOG = LoggerFactory.getLogger(ImpalaTmsUtils.class);
   private String apikey;
   private String holtelId;
   private String webhookSecret;
   private static final String DATE_FORMATE = "dd/MM/yyyy";
   private static final String TIME_FORMATE = "HH:mm:ss";
   private static final String START_DATE_FORMAT = "yyyy-MM-dd";
   private ScheduledExecutorService service;
   private String status = "Ok";
   private boolean isFirstSync;
   private boolean isStopped;
   private List<String> syncedBookings = new ArrayList<>();

   public String getApikey() {
      return this.apikey;
   }

   public void setApikey(String apikey) {
      this.apikey = apikey;
   }

   public String getHoltelId() {
      return this.holtelId;
   }

   public void setHoltelId(String holtelId) {
      this.holtelId = holtelId;
   }

   public String getWebhookSecret() {
      return this.webhookSecret;
   }

   public void setWebhookSecret(String webhookSecret) {
      this.webhookSecret = webhookSecret;
   }

   public JSONObject getResource(String resource, String id) throws IOException {
      LOG.debug("get resource {},id={}", resource, id);
      String js = this.getResources(resource, id, null);
      LOG.trace("get resource finshed");

      try {
         JSONObject obj = new JSONObject(js);
         return obj.optJSONObject("data");
      } catch (JSONException e) {
         LOG.warn("get resource failed:{}", js);
         return null;
      }
   }

   public JSONArray getResources(String url) throws IOException {
      LOG.info("getResources,url={}", url);
      String res = this.getResources(url, null, null);
      JSONObject resObj = new JSONObject(res);
      return resObj.optJSONArray("data");
   }

   public String updateResource(String url, Map<String, String> params) throws IOException {
      String fullurl = String.format(Locale.ENGLISH, "%s/%s/%s", "https://api.getimpala.com/v2/hotel", this.holtelId, url);
      List<NameValuePair> urlParameters = new ArrayList<>();
      if (params != null) {
         for (Entry<String, String> entry : params.entrySet()) {
            urlParameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
         }
      }

      String responseStr = "";

      try (CloseableHttpClient client = HttpClients.createDefault()) {
         HttpPost post = new HttpPost(fullurl);
         post.setHeader("Authorization", "Bearer " + this.apikey);
         post.setEntity(new UrlEncodedFormEntity(urlParameters));
         RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(20000).setConnectTimeout(20000).setSocketTimeout(20000).build();
         post.setConfig(requestConfig);
         HttpResponse response = client.execute(post);
         responseStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
         this.status = "Ok";
         return responseStr;
      } catch (IOException e) {
         this.status = "post failed";
         throw e;
      }
   }

   public boolean checkResponse(String response) {
      JSONObject res = new JSONObject(response);
      return !res.has("type") || !res.has("message");
   }

   public String getResources(String resource, String id, Map<String, String> params) throws IOException {
      String destid = "";
      if (id != null) {
         if (id.contains(" ")) {
            destid = id.replace(" ", "%20");
         } else {
            destid = id;
         }
      }

      String url = String.format(Locale.ENGLISH, "%s/%s/%s/%s", "https://api.getimpala.com/v2/hotel", this.holtelId, resource, destid);
      LOG.info("getResources={},id={},params={}", resource, id, params);
      if (this.isStopped) {
         LOG.info("Impala stopped");
         return null;
      }

      URI uri = null;

      try {
         URIBuilder builder = new URIBuilder(url);
         if (params != null) {
            for (Entry<String, String> entry : params.entrySet()) {
               builder.addParameter(entry.getKey(), entry.getValue());
            }
         }

         uri = builder.build();
      } catch (URISyntaxException e1) {
         LOG.error(e1.getMessage(), e1);
         throw new IOException("url is invalid:" + url);
      }

      String responseStr = "";
      String newStatus = "";

      try (CloseableHttpClient client = HttpClients.createDefault()) {
         HttpGet request = new HttpGet(uri);
         request.setHeader("Content-Type", "application/x-www-form-urlencoded");
         request.setHeader("Authorization", "Bearer " + this.apikey);
         RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(20000).setConnectTimeout(20000).setSocketTimeout(20000).build();
         request.setConfig(requestConfig);
         HttpResponse response = client.execute(request);
         responseStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
         if (!this.checkResponse(responseStr)) {
            LOG.error("response error:{}", response);
         }

         newStatus = "Ok";
      } catch (JSONException e) {
         newStatus = "connection failed:" + e.getMessage();
      }

      this.updateStatus(newStatus);
      return responseStr;
   }

   private void updateStatus(String newstatus) {
      if (!newstatus.equalsIgnoreCase(this.status)) {
         this.status = newstatus;
         PmsUtils.updatePmsConnectionStatus(this.status);
      }
   }

   @Override
   public void requestBill(String roomid) {
      LOG.info("request Bill for room:{}", roomid);

      try {
         for (GuestInfo gi : JpaManager.getGuestInfoManager().findGuestInfosByRoomid(roomid)) {
            if (gi.getCheckin().equalsIgnoreCase("Y")) {
               this.retrieveBillItems(gi);
            }
         }
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }
   }

   private static String getDateDiffByYear(int year) {
      Calendar cal = Calendar.getInstance();
      cal.add(1, year);
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      return df.format(cal.getTime());
   }

   private static String getDateDiffByDay(int day) {
      Calendar cal = Calendar.getInstance();
      cal.add(5, day);
      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      return df.format(cal.getTime());
   }

   @Override
   public void refresh() {
      Map<String, String> params = new HashMap<>();
      LOG.info("refresh data");
      boolean isAutowakeup = false;
      if (this.isFirstSync) {
         params.put("startDate", getDateDiffByDay(-6));
         params.put("endDate", getDateDiffByDay(0));
         LOG.info("impala data sync start ===,params:{}", params);
         isAutowakeup = PmsUtils.setAutoWakeUpTv(false);
      }

      try {
         this.syncedBookings.clear();
         boolean hasNext = true;

         while (hasNext) {
            String bookingsStr = this.getResources("bookings", null, params);
            if (bookingsStr != null && bookingsStr.length() > 0) {
               JSONObject jsbookings = new JSONObject(bookingsStr);
               this.handleBookings(jsbookings);
               JSONObject meta = jsbookings.optJSONObject("_meta");
               hasNext = meta != null && meta.optBoolean("hasNext");
               if (hasNext) {
                  params.put("next", meta.optString("next"));
               }
            }

            if (this.isStopped) {
               break;
            }
         }

         if (this.isFirstSync) {
            this.processCheckoutBookings();
            this.isFirstSync = false;
            if (isAutowakeup) {
               PmsUtils.setAutoWakeUpTv(true);
            }

            LOG.info("impala data sync finished ===");
         }
      } catch (IOException | SQLException e) {
         LOG.error(e.getMessage(), e);
      }
   }

   private void refreshLongTermBookings() {
      List<String> longTermBookings = JpaManager.getReservationManager().findLongtermBookings();
      LOG.info("refresh longterm bookings,size:{}", longTermBookings.size());
      ForkJoinPool fjpool = new ForkJoinPool(4);

      try {
         fjpool.submit(() -> longTermBookings.parallelStream().filter(t -> !this.syncedBookings.contains(t)).forEach(t -> {
            try {
               JSONObject booking = this.getResource("bookings", t);
               if (booking != null) {
                  this.handleBooking(booking);
               }
            } catch (IOException e) {
               LOG.error("refresh longterm booking {}", t, e);
            }
         })).get();
      } catch (InterruptedException | ExecutionException e) {
         LOG.error(e.getMessage(), e);
      } finally {
         fjpool.shutdown();
      }
   }

   private void refreshGuestInfo() throws IOException {
      LOG.info("refresh guests");
      Map<String, String> params = new HashMap<>();
      params.put("startDate", getDateDiffByYear(0));
      params.put("endDate", getDateDiffByYear(0));
      boolean hasNext = true;

      while (hasNext) {
         String bookingsStr = this.getResources("guests", null, params);
         if (bookingsStr != null && bookingsStr.length() > 0) {
            JSONObject jsguests = new JSONObject(bookingsStr);
            this.handleGuests(jsguests);
            JSONObject meta = jsguests.optJSONObject("_meta");
            hasNext = meta != null && meta.optBoolean("hasNext");
            if (hasNext) {
               params.put("next", meta.optString("next"));
            }
         }

         if (this.isStopped) {
            break;
         }
      }
   }

   private void processCheckoutBookings() throws SQLException {
      LOG.info("process checkout after sync");
      Map<String, String> roomStatusMap = JpaManager.getReservationManager().findRoomStatusMap();

      for (Entry<String, String> entry : roomStatusMap.entrySet()) {
         if (!"null".equals(entry.getKey()) && entry.getValue() != null) {
            List<String> statusList = Arrays.asList(entry.getValue().split(","));
            if (!statusList.contains("CHECKED_IN")) {
               try {
                  PmsUtils.processCheckout(entry.getKey());
               } catch (IOException e) {
                  LOG.error(e.getMessage());
               }
            }
         }
      }
   }

   @Override
   public void requestRefresh(String roomid) {
      try {
         for (GuestInfo gi : JpaManager.getGuestInfoManager().findGuestInfosByRoomid(roomid)) {
            String bookingId = gi.getOrderid();
            if (bookingId != null) {
               LOG.info("refresh booking:{}", bookingId);
               JSONObject booking = this.getResource("bookings", bookingId);
               this.handleBooking(booking);
            }
         }
      } catch (IOException e) {
         LOG.error("refresh error:{}", e.getMessage());
      }
   }

   @Override
   public boolean isAutoCreateTV() {
      return false;
   }

   @Override
   public void requestExpressCheckout(String roomid) {
      LOG.info("requestExpressCheckout:{}", roomid);

      try {
         for (GuestInfo gi : JpaManager.getGuestInfoManager().findGuestInfosByRoomid(roomid)) {
            if (gi.getOrderid() != null) {
               CmndMetricsTask.setExpressCheckOut(true);
               this.updateResource(String.format(Locale.ENGLISH, "bookings/%s/check-out", gi.getOrderid()), null);
               CmndMetricsTask.resetExpressCheckOut();
            }
         }
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
         CmndMetricsTask.resetExpressCheckOut();
      }
   }

   @Override
   public boolean isSupportExpressCheckout() {
      return true;
   }

   @Override
   public String isConnectedTms() {
      return this.status;
   }

   @Override
   public void stop() {
      LOG.info("stop Impala PMS");
      this.isStopped = true;
      if (null != this.service) {
         this.service.shutdown();
      }
   }

   @Override
   public void doStart() {
      LOG.info("start Impala PMS");
      PmsUtils.cleanDatabase();
      this.isStopped = false;
      this.service = Executors.newScheduledThreadPool(1, new TpvNamedThreadFactory("impala"));
      this.service.scheduleWithFixedDelay(() -> {
         try {
            this.refresh();
            this.refreshLongTermBookings();
         } catch (Throwable e) {
            LOG.error(e.getMessage(), e);
         }
      }, 2L, 60L, TimeUnit.SECONDS);
      PmsUtils.updatePmsVersion("2018-11-27");
      this.isFirstSync = true;
   }

   private void handleGuests(JSONObject jsGuests) {
      JSONArray data = jsGuests.optJSONArray("data");
      if (data != null) {
         LOG.debug("handle guests,size={}", data.length());

         for (int i = 0; i < data.length(); i++) {
            JSONObject guest = data.optJSONObject(i);

            try {
               this.updateGuestInfo(guest);
            } catch (IOException e) {
               LOG.error(e.getMessage(), e);
            }

            if (this.isStopped) {
               break;
            }
         }
      } else {
         LOG.info("no guests found:{}", jsGuests);
         if (jsGuests.has("message")) {
            LOG.error("Impala error:{}", jsGuests.optString("message"));
         }
      }
   }

   private void handleBookings(JSONObject jsbookings) {
      JSONArray data = jsbookings.optJSONArray("data");
      if (data != null) {
         LOG.info("handle bookings:size={}", data.length());
         List<JSONObject> bookings = new ArrayList<>();

         for (int i = 0; i < data.length(); i++) {
            JSONObject booking = data.optJSONObject(i);
            bookings.add(booking);
         }

         bookings.parallelStream().forEach(bookingx -> {
            try {
               this.syncedBookings.add(bookingx.optString("id"));
               this.handleBooking(bookingx);
            } catch (IOException e) {
               LOG.error(e.getMessage(), e);
            }
         });
      } else {
         LOG.info("no bookings found:{}", jsbookings);
         if (jsbookings.has("message")) {
            LOG.error("Impala error:{}", jsbookings.optString("message"));
         }
      }
   }

   public void retrieveBillItems(GuestInfo gi) throws IOException {
      String bookingId = gi.getOrderid();
      String roomid = gi.getRoomid();
      LOG.info("retrieve billitems:bookingid={},roomid={}", bookingId, roomid);
      JSONArray billArray = this.getResources("bookings/" + bookingId + "/bills");

      for (int i = 0; i < billArray.length(); i++) {
         JSONObject bill = billArray.optJSONObject(i);
         JSONArray charges = bill.optJSONArray("charges");

         for (int j = 0; j < charges.length(); j++) {
            JSONObject charge = charges.getJSONObject(j);
            Billitem bi = JpaManager.getBillitemManager().loadByKey(charge.optString("id"));
            if (bi != null) {
               return;
            }

            bi = new Billitem();
            bi.setID(charge.optString("id"));
            bi.setBillItemDisplayName(charge.optString("description"));
            String chargedAt = charge.optString("chargedAt");
            bi.setBillItemDate(this.convertDate(chargedAt));
            bi.setBillItemTime(this.convertTime(chargedAt));
            bi.setBillItemAmount(charge.optString("grossAmount"));
            bi.setRoomId(roomid);
            JpaManager.getBillitemManager().save(bi);
         }
      }

      PmsUtils.sendBill2TV(gi);
   }

   public String getRoomid(String areaId) throws IOException {
      if (StringUtils.isEmpty(areaId)) {
         return null;
      }

      Roominfo roominfo = this.getArea(areaId);
      return roominfo != null ? roominfo.getRoomid() : null;
   }

   public int extractRoomId(String areaId) {
      String[] chunks = areaId.split("-");
      if (chunks.length > 0) {
         try {
            return Integer.parseInt(chunks[0]);
         } catch (NumberFormatException e) {
            LOG.info("NumberFormatException:{}", areaId);
         }
      }

      return 0;
   }

   public Roominfo getArea(String areaId) throws IOException {
      Roominfo info = JpaManager.getRoominfoManager().loadByKey(areaId);
      if (info != null) {
         return info;
      }

      LOG.info("get area:{}", areaId);
      JSONObject jsArea = this.getResource("areas", areaId);
      if (jsArea == null) {
         LOG.warn("get area failed:{}", areaId);
         int roomId = this.extractRoomId(areaId);
         LOG.info("try to load room by room:{}", roomId);
         if (roomId > 0) {
            info = JpaManager.getRoominfoManager().loadByRoomid(String.valueOf(roomId));
            if (info != null) {
               LOG.info("save changed areaId:{},room:{}", areaId, roomId);
               info.setId(areaId);
               JpaManager.getRoominfoManager().save(info);
               return info;
            }
         }

         return null;
      } else {
         info = new Roominfo();
         info.setId(jsArea.optString("id"));
         int roomid = 0;

         try {
            roomid = Integer.parseInt(jsArea.optString("name"));
         } catch (Exception e) {
            roomid = new Random(System.nanoTime()).nextInt(200) + 7000;
            LOG.warn("invalid roomid, using random:{}", roomid);
         }

         info.setRoomid(String.valueOf(roomid));
         info.setStatus(jsArea.optString("status"));
         info.setDescription(jsArea.optString("description"));

         try {
            JpaManager.getRoominfoManager().save(info);
         } catch (Exception e) {
            LOG.error(e.getMessage());
            if (e.getMessage().contains("Duplicate entry")) {
               LOG.info("duplicated room {} found,ignore", areaId);
            }
         }

         return info;
      }
   }

   public GuestInfo updateGuestInfo(JSONObject jsGuest) throws IOException {
      if (jsGuest == null) {
         throw new IOException("guest is null");
      }

      String guestId = jsGuest.optString("id");
      String guestName = jsGuest.optString("firstName") + " " + jsGuest.optString("lastName");
      List<GuestInfo> guestInfos = JpaManager.getGuestInfoManager().findByBeginGuestid(guestId);
      if (guestInfos.isEmpty()) {
         return this.createNewGuest(jsGuest, null, null);
      }

      guestInfos.stream().forEach(t -> {
         if (!t.getGuestName().equalsIgnoreCase(guestName)) {
            LOG.info("guest {} name changed:{}", guestId, guestName);
            t.setGuestName(guestName);
            JpaManager.getGuestInfoManager().save(t);
         }
      });
      return guestInfos.get(0);
   }

   public GuestInfo getGuestInfo(String guestId, String orderId, String roomId) throws IOException {
      GuestInfo guest = JpaManager.getGuestInfoManager().loadByOrderId(orderId);
      if (guest != null) {
         return guest;
      } else if (guestId == null) {
         LOG.error("guestId is null,get guestInfo fail!");
         return null;
      } else {
         LOG.info("retrieve guest:{}", guestId);
         JSONObject jsGuest = this.getResource("guests", guestId);
         if (jsGuest == null) {
            throw new IOException("get guest failed:" + guestId);
         } else {
            return this.createNewGuest(jsGuest, orderId, roomId);
         }
      }
   }

   private GuestInfo createNewGuest(JSONObject jsGuest, String orderId, String roomId) {
      LOG.info("create new guest:{}", jsGuest);
      GuestInfo guest = new GuestInfo();
      String guestId = PmsUtils.generateNewGuestId(jsGuest.optString("id"), roomId);
      guest.setGuestId(guestId);
      guest.setTitle(jsGuest.optString("title"));
      guest.setGuestLanguage(jsGuest.optString("languageCode"));
      guest.setGuestName(jsGuest.optString("firstName") + " " + jsGuest.optString("lastName"));
      guest.setOrderid(orderId);
      CheckInVO checkinVo = PmsUtils.getCheckInVO(guest);
      guest.setExpressCheckout(checkinVo.getExpressCheckout());
      guest.setViewBill(checkinVo.getViewBill());
      guest.setViewMessage(checkinVo.isMessagesEnabled() ? "True" : "False");
      guest.setDoNotDisturb(checkinVo.getDonotDisturb());

      try {
         JpaManager.getGuestInfoManager().save(guest);
      } catch (Exception e) {
         LOG.error(e.getMessage());
         if (e.getMessage().contains("Duplicate entry")) {
            guestId = String.format("%s-%s", UUID.randomUUID().toString().substring(0, 18), guestId);
            guest.setGuestId(guestId);
            JpaManager.getGuestInfoManager().save(guest);
            LOG.info("duplicated guestId found, save as new one:{}", guestId);
         }
      }

      LOG.info("store guest info:{}", guest.getGuestId());
      return guest;
   }

   private String convertDate(String date) {
      Date now = this.parseDate(date);
      return TpvDateUtils.formatLocalDate(now, "dd/MM/yyyy");
   }

   private Date parseDate(String date) {
      return new Date(Integer.parseInt(date) * 1000L);
   }

   private String convertTime(String time) {
      Date now = this.parseDate(time);
      return TpvDateUtils.formatLocalDate(now, "HH:mm:ss");
   }

   public void cancelBooking(JSONObject booking) {
      String bookingId = booking.optString("id");
      LOG.info("booking cancelled:{}", bookingId);
      Reservation res = JpaManager.getReservationManager().loadByKey(bookingId);
      if (res != null) {
         String roomId = res.getRooms();
         PmsUtils.cleanPmsData(roomId);
      }
   }

   private void updateBooking(JSONObject booking, Reservation res) throws IOException {
      String bookingId = booking.optString("id");
      String areaId = booking.optString("areaId");
      String bookingStatus = booking.optString("status");
      boolean checkin = bookingStatus.equalsIgnoreCase("CHECKED_IN");
      res.setStartTime(this.convertDate(booking.optString("start")));
      res.setEndTime(this.convertDate(booking.optString("end")));
      String roomid = null;
      if (!StringUtils.isEmpty(areaId)) {
         roomid = this.getRoomid(areaId);
         if (roomid != null && res.getRooms() == null) {
            res.setRooms(roomid);
         }
      }

      if (checkin) {
         JSONArray guestids = booking.optJSONArray("guestIds");
         if (guestids != null && guestids.length() > 0) {
            String guestId = guestids.getString(0);
            GuestInfo guest = this.getGuestInfo(guestId, bookingId, roomid);
            if (guest.getRoomid() == null && roomid != null) {
               guest.setRoomid(roomid);
               JpaManager.getGuestInfoManager().save(guest);
            }

            res.setGuests(guest.getGuestName());
         }
      }

      JpaManager.getReservationManager().save(res);
   }

   private Reservation createNewBooking(JSONObject booking) throws IOException {
      String bookingId = booking.optString("id");
      String bookingStatus = booking.optString("status");
      Reservation res = new Reservation();
      res.setReservationId(bookingId);
      if (!this.isFirstSync) {
         LOG.info("handle new booking:id={},status={}", bookingId, bookingStatus);
      }

      this.updateBooking(booking, res);
      JpaManager.getReservationManager().save(res);
      return res;
   }

   private void bookingStatusChanged(JSONObject booking, Reservation res) throws IOException {
      String bookingId = res.getReservationId();
      String roomid = res.getRooms();
      String bookingStatus = res.getStatus();
      boolean checkin = bookingStatus.equalsIgnoreCase("CHECKED_IN");
      if (checkin) {
         LOG.info("handle booking checkin:{},room:{}", bookingId, roomid);
         if (PmsUtils.isGuestCheckin(roomid)) {
            LOG.warn("booking:{} room:{} guest already checked in, ignore process checked in", bookingId, roomid);
            return;
         }

         GuestInfo guest = this.getGuestInfo(null, bookingId, roomid);
         if (guest == null) {
            throw new IOException("guest for booking:" + bookingId + " not found");
         }

         guest.setArrivalDate(res.getStartTime());
         guest.setDepartureDate(res.getEndTime());
         JpaManager.getGuestInfoManager().save(guest);
         String grossAmount = booking.optString("grossAmount");
         if (grossAmount != null && !grossAmount.isEmpty() && !grossAmount.equals(guest.getBalance())) {
            guest.setBalance(grossAmount);
            JpaManager.getGuestInfoManager().save(guest);
            if (guest.isSupportViewBill()) {
               try {
                  LOG.info("support bill");
                  this.retrieveBillItems(guest);
               } catch (SocketTimeoutException ex) {
                  LOG.error(
                     "booking id <"
                        + guest.getOrderid()
                        + "> retrieve billitems failure as the long socket read time issue from the Impala server, skip bill handle",
                     ex
                  );
               }
            }
         }

         if ("N".equalsIgnoreCase(guest.getCheckin())) {
            PmsUtils.processCheckin(guest.getGuestId());
         }
      } else if (bookingStatus.equalsIgnoreCase("CHECKED_OUT") && !this.isFirstSync) {
         LOG.info("handle booking checkout {},room:{}", bookingId, roomid);
         String checkedinBooking = this.getCheckinBookingId(roomid);
         if (checkedinBooking != null && !bookingId.equalsIgnoreCase(checkedinBooking)) {
            LOG.info("room:{} contains another booking {} checked in", roomid, checkedinBooking);
         } else {
            PmsUtils.processCheckout(roomid);
         }
      }
   }

   public Reservation getReservation(JSONObject booking) throws IOException {
      Reservation res = JpaManager.getReservationManager().loadByKey(booking.optString("id"));
      if (res == null) {
         res = this.createNewBooking(booking);
      } else {
         this.updateBooking(booking, res);
      }

      return res;
   }

   private void bookingRoomChanged(String areaId, Reservation res) throws IOException {
      if (!StringUtils.isEmpty(areaId)) {
         String oldRoomId = res.getRooms();
         String roomid = this.getRoomid(areaId);
         if (oldRoomId != null && !oldRoomId.equalsIgnoreCase(roomid)) {
            LOG.info("room changed:{} to {}", oldRoomId, roomid);
            res.setRooms(roomid);
            JpaManager.getReservationManager().save(res);
            PmsUtils.changeGuestRoom(oldRoomId, roomid);
         }
      }
   }

   public void handleBooking(JSONObject booking) throws IOException {
      String bookingId = booking.optString("id");
      String bookingStatus = booking.optString("status");
      String areaId = booking.optString("areaId");
      if (this.isFirstSync) {
         LOG.info("handle booking:id={},status={}", bookingId, bookingStatus);
      }

      Reservation res = this.getReservation(booking);
      this.bookingRoomChanged(areaId, res);
      boolean statusChanged = !bookingStatus.equalsIgnoreCase(res.getStatus());
      if (statusChanged) {
         res.setStatus(bookingStatus);
         JpaManager.getReservationManager().save(res);
         this.bookingStatusChanged(booking, res);
      }

      LOG.debug("handle booking finished");
   }

   private String getCheckinBookingId(String roomId) {
      List<Reservation> resList = JpaManager.getReservationManager().findReservationByRooms(roomId);
      List<Reservation> checkinList = resList.stream().filter(r -> r.getStatus().equalsIgnoreCase("CHECKED_IN")).collect(Collectors.toList());
      return !checkinList.isEmpty() ? checkinList.get(0).getReservationId() : null;
   }

   @Override
   protected void doLoadConfigs() {
      LOG.info("impala configs:{}", this.config);
      this.setApikey(this.config.optString("API_KEY", this.apikey));
      this.setHoltelId(this.config.optString("HOTEL_ID", this.holtelId));
   }

   public void processWebhooks(JSONObject obj) throws IOException {
      String webhookType = obj.optString("type");
      ImpalaTmsUtils.WEBHOOK_TYPE hooktype = ImpalaTmsUtils.WEBHOOK_TYPE.valueOf(webhookType);
      JSONArray events = obj.optJSONArray("events");
      if (events != null && events.length() > 0) {
         switch (hooktype) {
            case BOOKING_CREATED:
            case BOOKING_CHANGED:
               for (int i = 0; i < events.length(); i++) {
                  JSONObject booking = events.optJSONObject(i).optJSONObject("newBooking");
                  this.handleBooking(booking);
               }
               break;
            case BOOKING_CANCELLED:
               for (int i = 0; i < events.length(); i++) {
                  JSONObject booking = events.optJSONObject(i).optJSONObject("cancelledBooking");
                  this.cancelBooking(booking);
               }
               break;
            case BOOKING_AREA_CHANGED:
               for (int i = 0; i < events.length(); i++) {
                  JSONObject event = events.optJSONObject(i);
                  JSONObject booking = event.optJSONObject("booking");
                  String newAreaId = event.optString("newAreaId");
                  booking.put("areaId", newAreaId);
                  this.handleBooking(booking);
               }
               break;
            case BOOKING_DATE_CHANGED:
               for (int i = 0; i < events.length(); i++) {
                  JSONObject event = events.optJSONObject(i);
                  JSONObject booking = event.optJSONObject("booking");
                  String newStart = event.optString("newStart");
                  String newEnd = event.optString("newEnd");
                  booking.put("start", newStart);
                  booking.put("end", newEnd);
                  this.handleBooking(booking);
               }
               break;
            default:
               LOG.info("webhook not implement now:{}", webhookType);
         }
      } else {
         LOG.error("events object is error:{}", obj);
      }
   }

   public boolean checkWebhookAuthenticity(String content, String signature) {
      try {
         Mac sha256HMAC = Mac.getInstance("HmacSHA256");
         SecretKeySpec secretkey = new SecretKeySpec(this.webhookSecret.getBytes(), "HmacSHA256");
         sha256HMAC.init(secretkey);
         String hash = Base64.encodeBase64String(sha256HMAC.doFinal(content.getBytes()));
         return hash != null && signature != null && hash.equalsIgnoreCase(signature);
      } catch (InvalidKeyException | NoSuchAlgorithmException | IllegalStateException e) {
         LOG.error(e.getMessage(), e);
         return false;
      }
   }

   @Override
   public void updateMessageStatus(String msgId, PmsUtils.MessageStatus newStatus) {
      LOG.error("not support update message for Impala PMS");
   }

   @Override
   protected String getConfigName() {
      return "impala";
   }

   @Override
   public void responseWakeup(String roomId, String wakeupTime, String status) {
   }

   public enum WEBHOOK_TYPE {
      BOOKING_SET_CREATED,
      BOOKING_SET_CHANGED,
      BOOKING_CREATED,
      BOOKING_CHANGED,
      BOOKING_CANCELLED,
      BOOKING_AREA_CHANGED,
      BOOKING_DATE_CHANGED;
   }
}
