/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.pms;

import com.tpvision.smartinstall.dao.core.Billitem;
import com.tpvision.smartinstall.dao.core.GuestInfo;
import com.tpvision.smartinstall.dao.core.Reservation;
import com.tpvision.smartinstall.dao.core.Roominfo;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.pms.CheckInVO;
import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.pms.TmsUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.TpvNamedThreadFactory;
import com.tpvision.smartinstall.util.TpvTimerTask;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.net.util.Base64;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HopTmsUtils
extends TmsUtils {
    public static final String CONFIG_NAME = "hop";
    private static final String BASE_URL = "https://pms.hopsoftware.com";
    private static final Logger LOG = LoggerFactory.getLogger(HopTmsUtils.class);
    private static final String FROM_TIMEFORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String DATE_FORMATE = "dd/MM/yyyy";
    private static final String TIME_FORMATE = "HH:mm:ss";
    private static final int DEFAULT_TIMEOUT = 20000;
    private String lastReservationStatusesMD5;
    private String token;
    private long expiresTime;
    private String propertyId;
    private String clientId;
    private String clientSecret;
    private String userName;
    private String password;
    private ScheduledExecutorService service;
    private String status = "Ok";

    @Override
    protected void doLoadConfigs() {
        JSONObject pmsconfigs = PmsUtils.getPmsConfigs();
        if (pmsconfigs != null) {
            JSONObject config = pmsconfigs.optJSONObject(CONFIG_NAME);
            if (config == null) {
                LOG.error("get HOP PMS config failed");
                return;
            }
            LOG.info("hop configs:{}", (Object)config);
            this.setPropertyId(config.optString("PROPERTY_ID", "1"));
            this.setClientId(config.optString("CLIENT_ID", ""));
            this.setClientSecret(config.optString("CLIENT_SECRET", ""));
            this.setUserName(config.optString("USER_NAME", ""));
            this.setPassword(config.optString("PASSWORD"));
            String autoCreateTV = config.optString("AUTO_RFTV", "Off");
            this.setAutoCreateTV(autoCreateTV.equalsIgnoreCase("On"));
        } else {
            LOG.error("get HOP PMS configs failed");
        }
    }

    private JSONObject getResource(String path) throws IOException {
        this.checkOAuthToken();
        String fullurl = BASE_URL + path;
        String responseStr = "{}";
        LOG.info("getResource:{}", (Object)fullurl);
        try (CloseableHttpClient client = HttpClients.createDefault();){
            HttpGet post = new HttpGet(fullurl);
            post.setHeader("Authorization", "Bearer " + this.getToken());
            RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(20000).setConnectTimeout(20000).setSocketTimeout(20000).build();
            post.setConfig(requestConfig);
            CloseableHttpResponse response = client.execute(post);
            responseStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            this.setStatus("Ok");
        }
        catch (IOException e) {
            this.setStatus("connection failed," + e.getMessage());
            throw e;
        }
        return new JSONObject(responseStr);
    }

    private JSONObject getResource(String path, Map<String, String> params) throws IOException {
        this.checkOAuthToken();
        String fullurl = BASE_URL + path;
        ArrayList<BasicNameValuePair> urlParameters = new ArrayList<BasicNameValuePair>();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                urlParameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        String responseStr = "{}";
        try (CloseableHttpClient client = HttpClients.createDefault();){
            HttpPost post = new HttpPost(fullurl);
            post.setHeader("Authorization", "Bearer " + this.getToken());
            post.setEntity(new UrlEncodedFormEntity((List<? extends NameValuePair>)urlParameters));
            RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(20000).setConnectTimeout(20000).setSocketTimeout(20000).build();
            post.setConfig(requestConfig);
            CloseableHttpResponse response = client.execute(post);
            responseStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            this.setStatus("Ok");
        }
        catch (IOException e) {
            this.setStatus("connection failed," + e.getMessage());
            throw e;
        }
        return new JSONObject(responseStr);
    }

    private void checkOAuthToken() throws IOException {
        block16: {
            if (this.getToken() != null && System.currentTimeMillis() < this.getExpiresTime()) {
                return;
            }
            LOG.info("request new oauth token");
            String fullurl = String.format(Locale.ENGLISH, "%s/oauth/token", BASE_URL);
            ArrayList<BasicNameValuePair> urlParameters = new ArrayList<BasicNameValuePair>();
            urlParameters.add(new BasicNameValuePair("grant_type", "password"));
            urlParameters.add(new BasicNameValuePair("username", this.getUserName()));
            urlParameters.add(new BasicNameValuePair("password", this.getPassword()));
            String responseStr = "";
            try (CloseableHttpClient client = HttpClients.createDefault();){
                HttpPost post = new HttpPost(fullurl);
                String auth = this.getClientId() + ":" + this.getClientSecret();
                byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
                String authHeader = "Basic " + new String(encodedAuth);
                post.setHeader("Authorization", authHeader);
                post.setHeader("Content-Type", "application/x-www-form-urlencoded");
                post.setEntity(new UrlEncodedFormEntity((List<? extends NameValuePair>)urlParameters));
                RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(20000).setConnectTimeout(20000).setSocketTimeout(20000).build();
                post.setConfig(requestConfig);
                CloseableHttpResponse response = client.execute(post);
                responseStr = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
                LOG.info("oauth:{}", (Object)responseStr);
                JSONObject res = new JSONObject(responseStr);
                if (res.has("access_token") && res.has("expires_in")) {
                    this.setToken(res.optString("access_token"));
                    this.setExpiresTime(res.optLong("expires_in"));
                    break block16;
                }
                throw new IOException("get oauth token failed");
            }
            catch (Exception e) {
                this.setStatus("get oauth token failed");
                throw new IOException(e.getMessage());
            }
        }
    }

    private void retrieveBillItems(JSONArray billArray, String roomId) {
        LOG.info("retrieve billitems for room:{}", (Object)roomId);
        PmsUtils.deletePreBillItems(roomId);
        for (int i = 0; i < billArray.length(); ++i) {
            JSONObject charge = billArray.optJSONObject(i);
            Billitem bi = new Billitem();
            bi.setID(UUID.randomUUID().toString());
            bi.setBillItemDisplayName(charge.optString("Description"));
            String chargedAt = charge.optString("DateCreated");
            bi.setBillItemDate(this.convertDate(chargedAt));
            bi.setBillItemTime(this.convertTime(chargedAt));
            bi.setBillItemAmount(charge.optString("TotalAmount"));
            bi.setRoomId(roomId);
            JpaManager.getBillitemManager().save(bi);
        }
    }

    private JSONObject getRoom(JSONArray rooms, String roomid, String reservationStatus) {
        for (int i = 0; i < rooms.length(); ++i) {
            JSONObject room = rooms.getJSONObject(i);
            if (!roomid.equalsIgnoreCase(room.optString("RoomNumber")) || !reservationStatus.equalsIgnoreCase(room.optString("Status"))) continue;
            return room;
        }
        return null;
    }

    private JSONObject getGuest(JSONArray guests, String lastName) {
        for (int i = 0; i < guests.length(); ++i) {
            JSONObject guest = guests.getJSONObject(i);
            if (!lastName.equalsIgnoreCase(guest.optString("LastName"))) continue;
            return guest;
        }
        return guests.getJSONObject(0);
    }

    private JSONObject retrieveReservationDetail(String reservationId, String roomid) throws IOException {
        LOG.info("retrieveReservationDetail:{}", (Object)reservationId);
        JSONObject jsRes = this.getResource("/api/reservationdetail/" + reservationId);
        if (jsRes.optLong("Result") != 200L) {
            throw new IOException(jsRes.optString("Message"));
        }
        JSONObject resInfo = jsRes.optJSONObject("ReservationDetail");
        if (resInfo == null) {
            throw new IOException("not found ReservationDetail");
        }
        LOG.info("reservationDetail-{} response:{}", (Object)reservationId, (Object)resInfo);
        return resInfo;
    }

    private void handleResevationStatus(JSONObject resStatus) throws IOException {
        Reservation res;
        String grossAmount;
        String reservationId = resStatus.optString("ReservationId");
        String roomid = resStatus.optString("RoomNumber");
        LOG.debug("handleResevationStatus:{}", (Object)resStatus);
        String reservationStatus = resStatus.optString("Status");
        boolean roomCheckInStatus = PmsUtils.isGuestCheckin(roomid);
        boolean reservationCheckOutStatus = "CheckedOut".equalsIgnoreCase(reservationStatus);
        if (reservationCheckOutStatus && !roomCheckInStatus) {
            return;
        }
        JSONObject reservationDetail = this.retrieveReservationDetail(reservationId, roomid);
        JSONArray roomsDetail = reservationDetail.optJSONArray("Rooms");
        if (roomsDetail == null || roomsDetail.length() <= 0) {
            throw new IOException("not found rooms");
        }
        JSONObject roomDetail = this.getRoom(roomsDetail, roomid, reservationStatus);
        if (roomDetail == null) {
            throw new IOException(String.format(Locale.ENGLISH, "room %s not found in reservation:%s", roomid, reservationId));
        }
        JSONArray guestsDetail = roomDetail.optJSONArray("Guests");
        if (guestsDetail == null || guestsDetail.length() <= 0) {
            throw new IOException("not found guest");
        }
        JSONObject guestDetailObject = this.getGuest(guestsDetail, resStatus.optString("Name"));
        String guestid = PmsUtils.generateNewGuestId(guestDetailObject.optString("IndividualId"), roomid);
        GuestInfo guest = JpaManager.getGuestInfoManager().loadByKey(guestid);
        if (reservationCheckOutStatus) {
            if (guest != null && "Y".equalsIgnoreCase(guest.getCheckin())) {
                PmsUtils.processCheckout(roomid);
            }
            return;
        }
        String arriveDate = this.convertDate(roomDetail.optString("DateOfArrival"));
        String checkInTime = this.convertDateTime(roomDetail.optString("DateOfArrival"));
        String departureDate = this.convertDate(roomDetail.optString("DateOfDeparture"));
        if (guest != null) {
            if ("Y".equalsIgnoreCase(guest.getCheckin())) {
                return;
            }
        } else {
            if (roomCheckInStatus) {
                PmsUtils.processCheckout(roomid);
            }
            guest = new GuestInfo();
            guest.setGuestId(guestid);
            guest.setTitle(guestDetailObject.optString("Title"));
            guest.setGuestName(guestDetailObject.optString("FirstName") + " " + guestDetailObject.optString("LastName"));
            guest.setRoomid(roomid);
            guest.setOrderid(reservationId);
            guest.setArrivalDate(arriveDate);
            guest.setCheckinTime(checkInTime);
            guest.setDepartureDate(departureDate);
            guest.setCheckin("N");
            CheckInVO checkinVo = PmsUtils.getCheckInVO(guest);
            guest.setExpressCheckout(checkinVo.getExpressCheckout());
            guest.setViewBill(checkinVo.getViewBill());
            guest.setViewMessage(checkinVo.isMessagesEnabled() ? "True" : "False");
            guest.setDoNotDisturb(checkinVo.getDonotDisturb());
            JpaManager.getGuestInfoManager().save(guest);
        }
        if ((grossAmount = roomDetail.optString("Balance")) != null && !grossAmount.isEmpty() && !grossAmount.equals(guest.getBalance())) {
            guest.setBalance(grossAmount);
            JpaManager.getGuestInfoManager().save(guest);
            this.retrieveBillItems(roomDetail.optJSONArray("Transactions"), roomid);
        }
        if ((res = JpaManager.getReservationManager().loadByKey(reservationId)) == null) {
            res = new Reservation();
            res.setReservationId(reservationId);
            res.setRooms(roomid);
        }
        String[] roomArray = res.getRooms().split(",");
        ArrayList<String> rooms = new ArrayList<String>(roomArray.length);
        Collections.addAll(rooms, roomArray);
        if (!rooms.contains(roomid)) {
            rooms.add(roomid);
            res.setRooms(String.join((CharSequence)",", rooms));
        }
        res.setGuests(guest.getGuestName());
        res.setStartTime(arriveDate);
        res.setEndTime(departureDate);
        Roominfo room = JpaManager.getRoominfoManager().loadByRoomid(roomid);
        if (room == null) {
            room = new Roominfo();
            room.setId(UUID.randomUUID().toString());
            room.setRoomid(roomid);
        }
        res.setStatus(reservationStatus);
        JpaManager.getReservationManager().save(res);
        room.setStatus(reservationStatus);
        JpaManager.getRoominfoManager().save(room);
        PmsUtils.processCheckin(guestid);
        LOG.debug("handle reservation finished");
    }

    private String convertDate(String date) {
        SimpleDateFormat df = new SimpleDateFormat(FROM_TIMEFORMAT);
        Date now = new Date();
        try {
            now = df.parse(date);
        }
        catch (ParseException e) {
            LOG.error("date parse failed:{}", (Object)date);
        }
        return TpvDateUtils.formatLocalDate(now, DATE_FORMATE);
    }

    private String convertTime(String date) {
        SimpleDateFormat df = new SimpleDateFormat(FROM_TIMEFORMAT);
        Date now = new Date();
        try {
            now = df.parse(date);
        }
        catch (ParseException e) {
            LOG.error("date parse failed:{}", (Object)date);
        }
        return TpvDateUtils.formatLocalDate(now, TIME_FORMATE);
    }

    private String convertDateTime(String dateTime) {
        SimpleDateFormat df = new SimpleDateFormat(FROM_TIMEFORMAT);
        Date now = new Date();
        try {
            now = df.parse(dateTime);
        }
        catch (ParseException e) {
            LOG.error("date parse failed:{}", (Object)dateTime);
        }
        return TpvDateUtils.formatLocalDate(now, "yyyy-MM-dd HH:mm");
    }

    @Override
    public void requestBill(String roomid) {
    }

    @Override
    public void refresh() {
        try {
            JSONObject rs = this.getResource("/api/reservationstatus/" + this.getPropertyId());
            if (rs.optLong("Result") != 100L) {
                throw new IOException(rs.optString("Message"));
            }
            JSONArray jaStatus = rs.optJSONArray("ReservationStatuses");
            HashMap<String, JSONObject> roomReservationInfo = new HashMap<String, JSONObject>();
            for (int i = 0; i < jaStatus.length(); ++i) {
                JSONObject js = jaStatus.optJSONObject(i);
                String roomStatus = js.optString("Status");
                if (!"CheckedOut".equalsIgnoreCase(roomStatus) && !"CheckedIn".equalsIgnoreCase(roomStatus)) continue;
                roomReservationInfo.put(js.optString("RoomNumber"), js);
            }
            String reservationStatusesMD5 = DigestUtils.md5Hex(((Object)roomReservationInfo).toString());
            if (reservationStatusesMD5.equalsIgnoreCase(this.lastReservationStatusesMD5)) {
                return;
            }
            this.lastReservationStatusesMD5 = reservationStatusesMD5;
            LOG.info("reservationstatus md5:{},response:{}", (Object)this.lastReservationStatusesMD5, (Object)jaStatus);
            for (JSONObject jsStatus : roomReservationInfo.values()) {
                this.handleResevationStatus(jsStatus);
            }
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    public void requestRefresh(String roomid) {
    }

    @Override
    public void requestExpressCheckout(String roomid) {
    }

    @Override
    public String isConnectedTms() {
        return this.status;
    }

    @Override
    public void stop() {
        LOG.info("stop HOP PMS");
        if (null != this.service) {
            this.service.shutdown();
        }
    }

    @Override
    public void doStart() {
        LOG.info("start HOP PMS");
        this.service = Executors.newScheduledThreadPool(1, new TpvNamedThreadFactory(CONFIG_NAME));
        this.service.scheduleWithFixedDelay(new HopPollingTask(), 5L, 5L, TimeUnit.SECONDS);
        LOG.info("refresh data every 5s");
        PmsUtils.updatePmsVersion("HOP PMS v1.8");
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPropertyId() {
        return this.propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return this.clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getExpiresTime() {
        return this.expiresTime;
    }

    public void setExpiresTime(long expiresTime) {
        this.expiresTime = System.currentTimeMillis() + expiresTime * 1000L;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void updateMessageStatus(String msgId, PmsUtils.MessageStatus newStatus) {
        LOG.error("not support update message for HOP PMS");
    }

    @Override
    protected String getConfigName() {
        return CONFIG_NAME;
    }

    @Override
    public void responseWakeup(String roomId, String wakeupTime, String status) {
    }

    public class HopPollingTask
    extends TpvTimerTask {
        @Override
        public void tryRun() {
            HopTmsUtils.this.refresh();
        }
    }
}

