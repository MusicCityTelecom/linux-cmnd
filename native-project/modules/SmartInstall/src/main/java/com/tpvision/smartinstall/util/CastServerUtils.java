package com.tpvision.smartinstall.util;

import com.tpvision.smartinstall.dao.core.CastAnalyticalData;
import com.tpvision.smartinstall.dao.core.CastServerSetting;
import com.tpvision.smartinstall.dao.core.GuestInfo;
import com.tpvision.smartinstall.dao.mgr.CastAnalyticalDataManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.schedule.CmndMetricsTask;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

public class CastServerUtils {
   private static final Logger LOG = LoggerFactory.getLogger(CastServerUtils.class);
   private static final String ENDPOINT_URL_ADDR = "https://cloud.nevotek.com/Cloud.Nevotek.PPDSCasting";
   private static final String OPENAPI_URL_ADDR = "https://cloud.nevotek.com/Cloud.Unilink.OpenAPI";
   private static CastServerUtils.AnalyticalDataSyncState currentSyncState = CastServerUtils.AnalyticalDataSyncState.IDLE;

   private CastServerUtils() {
   }

   public static String registerActivationCodeToPpdsCastServerr(String activationCode, String serialNumber, String macAddress, String hotelId) {
      JSONObject regJson = new JSONObject();
      regJson.put("activation_code", activationCode);
      regJson.put("serial", serialNumber);
      regJson.put("mac", macAddress);
      regJson.put("HotelID", hotelId);
      return sendRequestToPpdsCastServer(HttpMethod.POST, "/v1/register ", null, regJson);
   }

   public static String unregisterCodeToPpdsCastServer(CastServerSetting castServerSetting) {
      return sendRequestToPpdsCastServer(HttpMethod.POST, "/v1/unregister/" + castServerSetting.getPropertyId(), castServerSetting, null);
   }

   public static String getSetupInfoFromPpdsCastServer(CastServerSetting castServerSetting) {
      return sendRequestToPpdsCastServer(HttpMethod.GET, "/v1/setup/" + castServerSetting.getPropertyId(), castServerSetting, null);
   }

   public static String submitSetupInfoToPpdsCastServer(CastServerSetting castServerSetting, JSONObject submitData) {
      return sendRequestToPpdsCastServer(HttpMethod.POST, "/v1/setup/" + castServerSetting.getPropertyId(), castServerSetting, submitData);
   }

   public static String checkStatusFromPpdsCastServer(CastServerSetting castServerSetting) {
      return sendRequestToPpdsCastServer(HttpMethod.GET, "/v1/checkstatus/" + castServerSetting.getPropertyId(), castServerSetting, null);
   }

   public static String getCastAnalyticsDataFromPpdsCastServer(CastServerSetting castServerSetting, JSONObject queryParameter) {
      return sendRequestToPpdsCastServer(HttpMethod.GET, "/v1/analytics/" + castServerSetting.getPropertyId(), castServerSetting, queryParameter);
   }

   public static String customizeLandingPageToPpdsCastServer(
      CastServerSetting castServerSetting, String encodeBase64Background, String ssidName, String guestDnsName, boolean isShowUrl
   ) {
      JSONObject dataJson = new JSONObject();
      dataJson.put("landingpageId", 1);
      dataJson.put("background", encodeBase64Background);
      dataJson.put("ssidname", ssidName);
      dataJson.put("guestdnsname", guestDnsName);
      dataJson.put("showurl", isShowUrl);
      return sendRequestToPpdsCastServer(HttpMethod.POST, "/v1/landingpage/" + castServerSetting.getPropertyId(), castServerSetting, dataJson);
   }

   public static void forwardDeviceRoomListToPpdsCastServer() {
      CastServerSetting castServerSetting = JpaManager.getCastServerSettingManager().findCastServerSetting();
      if (castServerSetting != null && !StringUtils.isEmpty(castServerSetting.getApiUser())) {
         JSONArray jsonArray = new JSONArray();
         JpaManager.getDevicesManager().loadAll().forEach(d -> {
            if (!d.isRFDevice()) {
               JSONObject tv = new JSONObject();
               tv.put("deviceId", d.getTvmacaddress());
               tv.put("roomNumber", d.getTvroomid());
               jsonArray.put(tv);
            }
         });
         JSONObject result = new JSONObject();
         result.put("devicelist", jsonArray);
         sendAsyncRequestToPpdsCastServer(HttpMethod.POST, "/v1/devicelist/" + castServerSetting.getPropertyId(), castServerSetting, result);
      } else {
         LOG.info("cast server not registered, exit push");
      }
   }

   public static void startSyncAnalyticalData() {
      if (tryToUpdateSyncStatus()) {
         try {
            CastServerSetting castServerSetting = JpaManager.getCastServerSettingManager().findCastServerSetting();
            if (castServerSetting != null && !StringUtils.isEmpty(castServerSetting.getApiUser())) {
               CastAnalyticalDataManager castAnalyticalDataManager = JpaManager.getCastAnalyticalDataManager();
               JSONObject queryParameter = new JSONObject();
               Long maxIndexId = castAnalyticalDataManager.findMaxIndexId();
               if (maxIndexId != null) {
                  queryParameter.put("index", maxIndexId.intValue());
               }

               String responseData = getCastAnalyticsDataFromPpdsCastServer(castServerSetting, queryParameter);
               if (TpvStringUtils.isJSONString(responseData)) {
                  JSONObject resJson = new JSONObject(responseData);
                  JSONArray dataList = resJson.optJSONArray("list");
                  if (dataList != null) {
                     int i = 0;

                     for (int j = dataList.length(); i < j; i++) {
                        doAnalyticalDataSave(castAnalyticalDataManager, dataList, i);
                     }
                  }
               }

               return;
            }

            LOG.info("cast server not registered, exit sync");
         } catch (Exception e) {
            LOG.error("sync analytical data failed", e);
            return;
         } finally {
            revertUpdateSyncStatus();
         }
      }
   }

   public static void sendCheckInNoticeToOpenApiServer(GuestInfo guestInfo) {
      CastServerSetting castServerSetting = JpaManager.getCastServerSettingManager().findCastServerSetting();
      if (castServerSetting != null && !StringUtils.isEmpty(castServerSetting.getApiUser())) {
         JSONObject sendData = new JSONObject();
         sendData.put("RoomNumber", guestInfo.getRoomid());
         sendData.put("GuestNumber", guestInfo.getGuestId());
         sendData.put("GuestLanguage", guestInfo.getGuestLanguage());
         sendData.put("GuestLastname", "");
         sendData.put("GuestFirstname", "");
         sendData.put("GuestTitle", "");
         sendAsyncRequestToOpenApiServer(HttpMethod.POST, "/pms/checkin/" + getRequstPropertyId(castServerSetting), castServerSetting, sendData);
      } else {
         LOG.info("cast server not registered, exit push check in guest info to server");
      }
   }

   public static void sendCheckoutNoticeToOpenApiServer(GuestInfo guestInfo) {
      CastServerSetting castServerSetting = JpaManager.getCastServerSettingManager().findCastServerSetting();
      if (castServerSetting != null && !StringUtils.isEmpty(castServerSetting.getApiUser())) {
         JSONObject sendData = new JSONObject();
         sendData.put("RoomNumber", guestInfo.getRoomid());
         sendData.put("GuestNumber", guestInfo.getGuestId());
         sendAsyncRequestToOpenApiServer(HttpMethod.POST, "/pms/checkout/" + getRequstPropertyId(castServerSetting), castServerSetting, sendData);
      } else {
         LOG.info("cast server not registered, exit push checkout data to server");
      }
   }

   public static void sendChangeRoomNoticeToOpenApiServer(GuestInfo guestInfo, String oldRoom, String newRoom) {
      CastServerSetting castServerSetting = JpaManager.getCastServerSettingManager().findCastServerSetting();
      if (castServerSetting != null && !StringUtils.isEmpty(castServerSetting.getApiUser())) {
         JSONObject sendData = new JSONObject();
         sendData.put("RoomNumber", oldRoom);
         sendData.put("NewRoomNumber", newRoom);
         sendData.put("GuestNumber", guestInfo.getGuestId());
         sendAsyncRequestToOpenApiServer(HttpMethod.POST, "/pms/roomchange/" + getRequstPropertyId(castServerSetting), castServerSetting, sendData);
      } else {
         LOG.info("cast server not registered, exit push checkout data to server");
      }
   }

   private static synchronized boolean tryToUpdateSyncStatus() {
      if (currentSyncState != CastServerUtils.AnalyticalDataSyncState.IDLE) {
         LOG.info("start sync analytical data failure as the current status is in : <{}>", currentSyncState);
         return false;
      } else {
         currentSyncState = CastServerUtils.AnalyticalDataSyncState.SYNCING;
         return true;
      }
   }

   private static synchronized void revertUpdateSyncStatus() {
      currentSyncState = CastServerUtils.AnalyticalDataSyncState.IDLE;
   }

   private static void doAnalyticalDataSave(CastAnalyticalDataManager castAnalyticalDataManager, JSONArray dataList, int i) {
      try {
         JSONObject data = dataList.getJSONObject(i);
         CastAnalyticalData castAnalyticalData = new CastAnalyticalData();
         castAnalyticalData.setIndex(data.getInt("index"));
         castAnalyticalData.setDeviceId(data.getString("deviceId"));
         castAnalyticalData.setStart(data.getString("start"));
         castAnalyticalData.setEnd(data.getString("end"));
         castAnalyticalData.setSessionType(data.getString("sessionType"));
         castAnalyticalData.setApplicationName(data.getString("applicationName"));
         castAnalyticalData.setSyncTime(new Date());
         castAnalyticalDataManager.save(castAnalyticalData);
         CmndMetricsTask.writeCastAnalyticMetrics(castAnalyticalData);
      } catch (Exception ex) {
         LOG.error("save analytical data failed", ex);
      }
   }

   private static String getRequstPropertyId(CastServerSetting castServerSetting) {
      return castServerSetting.getHotelId();
   }

   private static void sendAsyncRequestToOpenApiServer(HttpMethod method, String url, CastServerSetting castServerSetting, JSONObject jsonObject) {
      new Thread(() -> sendRequestToWebServer(method, "https://cloud.nevotek.com/Cloud.Unilink.OpenAPI" + url, castServerSetting, jsonObject)).start();
   }

   private static void sendAsyncRequestToPpdsCastServer(HttpMethod method, String url, CastServerSetting castServerSetting, JSONObject jsonObject) {
      new Thread(() -> sendRequestToWebServer(method, "https://cloud.nevotek.com/Cloud.Nevotek.PPDSCasting" + url, castServerSetting, jsonObject)).start();
   }

   private static String sendRequestToPpdsCastServer(HttpMethod method, String url, CastServerSetting castServerSetting, JSONObject jsonObject) {
      return sendRequestToWebServer(method, "https://cloud.nevotek.com/Cloud.Nevotek.PPDSCasting" + url, castServerSetting, jsonObject);
   }

   private static String sendRequestToWebServer(HttpMethod method, String url, CastServerSetting castServerSetting, JSONObject jsonObject) {
      Map<String, String> headerMap = null;
      if (castServerSetting != null) {
         headerMap = getRequestAuthHeader(castServerSetting);
      }

      LOG.info("request online server:method:{}, url:{}, headerMap:{}, content:{}", method, url, headerMap, jsonObject);
      RestTemplate restTemplate = new RestTemplate();
      HttpHeaders requestHeaders = new HttpHeaders();
      requestHeaders.add("Content-Type", "application/json; charset=UTF-8");
      if (headerMap != null) {
         for (Entry<String, String> entry : headerMap.entrySet()) {
            requestHeaders.add(entry.getKey(), entry.getValue());
         }
      }

      HttpEntity<String> requestEntity = null;
      if (jsonObject != null) {
         requestEntity = new HttpEntity<>(jsonObject.toString(), requestHeaders);
      } else {
         requestEntity = new HttpEntity<>("", requestHeaders);
      }

      try {
         ResponseEntity<String> responseEntity = restTemplate.exchange(url, method, requestEntity, String.class);
         String responseBody = responseEntity.getBody();
         LOG.info("request sucess,code=<{}> response=<{}>", responseEntity.getStatusCode(), responseBody);
         return responseBody;
      } catch (HttpClientErrorException | HttpServerErrorException ex) {
         String bodyString = ex.getResponseBodyAsString();
         if (!StringUtils.isNoneBlank(bodyString)) {
            bodyString = ex.getStatusText();
         }

         LOG.error("request fail, error=<" + bodyString + ">", ex);
         return bodyString;
      } catch (Exception ex) {
         LOG.error("request fail", ex);
         return ex.getMessage();
      }
   }

   private static Map<String, String> getRequestAuthHeader(CastServerSetting castServerSetting) {
      Map<String, String> authMap = new HashMap<>();
      authMap.put(
         "Authorization", "Basic " + Base64.getEncoder().encodeToString((castServerSetting.getApiUser() + ":" + castServerSetting.getApiPassword()).getBytes())
      );
      return authMap;
   }

   public enum AnalyticalDataSyncState {
      IDLE,
      SYNCING,
      INTERRUPPED;
   }
}
