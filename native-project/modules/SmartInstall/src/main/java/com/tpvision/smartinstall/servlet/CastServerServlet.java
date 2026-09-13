package com.tpvision.smartinstall.servlet;

import com.tpvision.smartinstall.dao.core.CastAnalyticalData;
import com.tpvision.smartinstall.dao.core.CastServerSetting;
import com.tpvision.smartinstall.dao.mgr.CastServerSettingManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.util.CastServerUtils;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.TpvStringUtils;
import com.tpvision.smartinstall.util.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.Map.Entry;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/castServer")
public class CastServerServlet {
   private static final Logger logger = LoggerFactory.getLogger(CastServerServlet.class);
   private static final int HEAT_MAP_BLOCK_SIZE = 15;
   @Autowired
   private CastServerSettingManager castServerSettingManager;

   @GetMapping("/index")
   public String indexPage(HttpServletRequest request, String info, Model model) {
      CastServerSetting castServerSetting = this.castServerSettingManager.findCastServerSetting();
      model.addAttribute("isRegister", castServerSetting != null && StringUtils.isNoneBlank(castServerSetting.getApiUser()));
      model.addAttribute("castServerSetting", castServerSetting);
      return "cast_server/index";
   }

   @PostMapping("/register")
   @ResponseBody
   public String register(String activationCode, String serialNumber, String macAddress, String hotelId) {
      CastServerSetting castServerSetting = this.castServerSettingManager.findCastServerSetting();
      if (castServerSetting != null && StringUtils.isNoneBlank(castServerSetting.getApiUser())) {
         return Utils.buildFailReturnJson("Cast server is already registered, please unregister it first.");
      }

      if (StringUtils.isAnyEmpty(activationCode, serialNumber, macAddress, hotelId)) {
         return Utils.buildFailReturnJson("Request data error.");
      }

      String registerResponse = CastServerUtils.registerActivationCodeToPpdsCastServerr(activationCode, serialNumber, macAddress, hotelId);
      if (!TpvStringUtils.isJSONString(registerResponse)) {
         return Utils.buildFailReturnJson("Response data error.");
      }

      JSONObject responseObject = new JSONObject(registerResponse);
      if (!"OK".equalsIgnoreCase(responseObject.optString("registration"))) {
         return Utils.buildFailReturnJson("Registration failed: " + responseObject.optString("description"));
      }

      if (castServerSetting == null) {
         castServerSetting = new CastServerSetting();
      }

      castServerSetting.setActivationCode(activationCode);
      castServerSetting.setHotelId(hotelId);
      castServerSetting.setSerialNumber(serialNumber);
      castServerSetting.setMacAddress(macAddress);
      castServerSetting.setApiUser(responseObject.getString("api_user"));
      castServerSetting.setApiPassword(responseObject.getString("api_password"));
      castServerSetting.setPropertyId(responseObject.getString("propertyId"));
      this.castServerSettingManager.save(castServerSetting);
      CastServerUtils.forwardDeviceRoomListToPpdsCastServer();
      return "{\"status\":\"success\"}";
   }

   @PostMapping("/unregister")
   @ResponseBody
   public String unregister() {
      CastServerSetting castServerSetting = this.castServerSettingManager.findCastServerSetting();
      if (castServerSetting != null && !StringUtils.isBlank(castServerSetting.getApiUser())) {
         String unregRes = CastServerUtils.unregisterCodeToPpdsCastServer(castServerSetting);
         if (!TpvStringUtils.isJSONString(unregRes)) {
            return Utils.buildFailReturnJson("Response error data:" + unregRes);
         }

         JSONObject unregJson = new JSONObject(unregRes);
         String desc = unregJson.optString("description");
         if (!"OK".equalsIgnoreCase(desc)) {
            String errorMsg = "unknown error";
            if (StringUtils.isNoneBlank(desc)) {
               errorMsg = desc;
            }

            return Utils.buildFailReturnJson("Unregister failed:" + errorMsg);
         } else {
            if (StringUtils.isNoneBlank(castServerSetting.getBackgroundPath())) {
               FileUtils.deleteQuietly(new File(CommonConstants.CAST_SERVER_LANDING_PAGE_BACKGROUND_IMAGE_LOATION + castServerSetting.getBackgroundPath()));
            }

            this.castServerSettingManager.delete(castServerSetting);
            return "{\"status\":\"success\"}";
         }
      } else {
         return Utils.buildFailReturnJson("Cast server is not registered");
      }
   }

   @GetMapping("/getSetup")
   @ResponseBody
   public String getSetup() {
      CastServerSetting castServerSetting = this.castServerSettingManager.findCastServerSetting();
      if (castServerSetting != null && !StringUtils.isBlank(castServerSetting.getApiUser())) {
         String setupRes = CastServerUtils.getSetupInfoFromPpdsCastServer(castServerSetting);
         return StringUtils.contains(setupRes, "HotelID") ? setupRes : Utils.buildFailReturnJson("query setup data failure");
      } else {
         return Utils.buildFailReturnJson("Cast server is not registered");
      }
   }

   @PostMapping("/submitSetup")
   @ResponseBody
   public String submitSetup(HttpServletRequest request) {
      CastServerSetting castServerSetting = this.castServerSettingManager.findCastServerSetting();
      if (castServerSetting != null && !StringUtils.isBlank(castServerSetting.getApiUser())) {
         JSONObject jsonObject = new JSONObject();

         for (String param : request.getParameterMap().keySet()) {
            String value = request.getParameter(param);
            if (StringUtils.isBlank(value)) {
               return Utils.buildFailReturnJson("setup failure due to " + param + " value is empty");
            }

            jsonObject.put(param, value);
         }

         jsonObject.put(
            "ExternalChromeCastDiscovery", StringUtils.equalsAnyIgnoreCase(Boolean.TRUE.toString(), request.getParameter("ExternalChromeCastDiscovery"))
         );
         String setupRes = CastServerUtils.submitSetupInfoToPpdsCastServer(castServerSetting, jsonObject);
         if (!TpvStringUtils.isJSONString(setupRes)) {
            return Utils.buildFailReturnJson("Response data error.");
         }

         JSONObject responseObject = new JSONObject(setupRes);
         if (!"OK".equalsIgnoreCase(responseObject.optString("description"))) {
            String errorMsg = "unknown error";
            JSONObject responseStatus = responseObject.optJSONObject("ResponseStatus");
            if (responseStatus != null) {
               errorMsg = responseStatus.optString("Message");
            }

            return Utils.buildFailReturnJson("Configuration application failed: " + errorMsg);
         } else {
            castServerSetting.setGuestWlanIp(request.getParameter("GuestWlanIp"));
            this.castServerSettingManager.save(castServerSetting);
            return "{\"status\":\"success\"}";
         }
      } else {
         return Utils.buildFailReturnJson("Cast server is not registered");
      }
   }

   @GetMapping("/checkstatus")
   @ResponseBody
   public String checkstatus() {
      CastServerSetting castServerSetting = this.castServerSettingManager.findCastServerSetting();
      if (castServerSetting != null && !StringUtils.isBlank(castServerSetting.getApiUser())) {
         new Thread(CastServerUtils::startSyncAnalyticalData).start();
         String checkRes = CastServerUtils.checkStatusFromPpdsCastServer(castServerSetting);
         if (StringUtils.contains(checkRes, "registration") && TpvStringUtils.isJSONString(checkRes)) {
            JSONObject resJson = new JSONObject(checkRes);
            String licenseExpirationDate = resJson.optString("licenseExpirationDate");
            boolean isLicenseExpired = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).compareTo(licenseExpirationDate) > 0;
            resJson.put("isLicenseExpired", isLicenseExpired);
            if (StringUtils.isEmpty(resJson.optString("GoogleCastGatewayUrl"))) {
               resJson.put("GoogleCastGatewayUrl", "Pending");
            }

            return resJson.toString();
         } else {
            if (!TpvStringUtils.isJSONString(checkRes)) {
               return Utils.buildFailReturnJson("Response data error.");
            }

            JSONObject responseObject = new JSONObject(checkRes);
            return Utils.buildFailReturnJson("checkstatus failure: " + responseObject.optString("description"));
         }
      } else {
         return Utils.buildFailReturnJson("Cast server is not registered");
      }
   }

   @PostMapping("/saveLandingPage")
   @ResponseBody
   public String saveLandingPage(String backgroundImgName, String background, String ssidName, String guestDnsName, String showUrl) {
      CastServerSetting castServerSetting = this.castServerSettingManager.findCastServerSetting();
      if (castServerSetting != null && !StringUtils.isBlank(castServerSetting.getApiUser())) {
         String newBackgroundPath = "";
         String backgrondUrlRemoveRequired = "";
         if (StringUtils.isBlank(background)) {
            if (StringUtils.isNoneBlank(castServerSetting.getBackgroundPath())) {
               backgrondUrlRemoveRequired = castServerSetting.getBackgroundPath();
            }
         } else if (background.contains("base64") && background.startsWith("data")) {
            if (StringUtils.isNoneBlank(castServerSetting.getBackgroundPath())) {
               backgrondUrlRemoveRequired = castServerSetting.getBackgroundPath();
            }

            newBackgroundPath = UUID.randomUUID().toString() + "." + FileNameUtils.getExtension(backgroundImgName);
            File newFile = new File(CommonConstants.CAST_SERVER_LANDING_PAGE_BACKGROUND_IMAGE_LOATION + newBackgroundPath);

            try {
               FileUtils.touch(newFile);
               String base64Part = background.substring(background.indexOf("base64,") + 7);
               FileUtils.writeByteArrayToFile(newFile, Base64.getDecoder().decode(base64Part));
            } catch (IOException e) {
               logger.error("base64 decode to file failure", e);
               return Utils.buildFailReturnJson("Save new background file failure");
            }
         } else {
            newBackgroundPath = castServerSetting.getBackgroundPath();
         }

         boolean isShowUrl = StringUtils.equalsIgnoreCase(showUrl, "on");
         String encodeBase64Background = "";
         if (StringUtils.isNoneBlank(newBackgroundPath)) {
            try {
               encodeBase64Background = Base64.getEncoder()
                  .encodeToString(
                     FileUtils.readFileToByteArray(new File(CommonConstants.CAST_SERVER_LANDING_PAGE_BACKGROUND_IMAGE_LOATION + newBackgroundPath))
                  );
            } catch (IOException e) {
               logger.error("base64 encode failure", e);
               return Utils.buildFailReturnJson("Can't encode file to base64 format");
            }
         }

         String postRes = CastServerUtils.customizeLandingPageToPpdsCastServer(castServerSetting, encodeBase64Background, ssidName, guestDnsName, isShowUrl);
         if (!TpvStringUtils.isJSONString(postRes)) {
            return Utils.buildFailReturnJson("Response error data:" + postRes);
         }

         JSONObject unregJson = new JSONObject(postRes);
         String desc = unregJson.optString("description");
         if (!"OK".equalsIgnoreCase(desc)) {
            String errorMsg = "unknown error";
            if (StringUtils.isNoneBlank(desc)) {
               errorMsg = desc;
            }

            return Utils.buildFailReturnJson("save landing page failed:" + errorMsg);
         } else {
            if (StringUtils.isNoneBlank(backgrondUrlRemoveRequired)) {
               FileUtils.deleteQuietly(new File(CommonConstants.CAST_SERVER_LANDING_PAGE_BACKGROUND_IMAGE_LOATION + backgrondUrlRemoveRequired));
            }

            castServerSetting.setBackgroundPath(newBackgroundPath);
            castServerSetting.setSsidName(ssidName);
            castServerSetting.setGuestDnsName(guestDnsName);
            castServerSetting.setShowUrl(String.valueOf(isShowUrl));
            this.castServerSettingManager.save(castServerSetting);
            return "{\"status\":\"success\"}";
         }
      } else {
         return Utils.buildFailReturnJson("Cast server is not registered");
      }
   }

   @GetMapping("/showBackground")
   public void showBackground(String name, HttpServletResponse response) {
      String backgroundPath = CommonConstants.CAST_SERVER_LANDING_PAGE_BACKGROUND_IMAGE_LOATION + name;
      File imgFile = new File(backgroundPath);
      if (!imgFile.exists()) {
         backgroundPath = CommonConstants.servletContextPath + "/static/images/upload_normal.png";
      }

      response.setContentType(new MimetypesFileTypeMap().getContentType(backgroundPath));

      try (
         ServletOutputStream outStream = response.getOutputStream();
         FileInputStream fis = new FileInputStream(backgroundPath);
      ) {
         byte[] buffer = new byte[4096];

         int bytesRead;
         while ((bytesRead = fis.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
         }
      } catch (IOException e) {
         logger.error(e.getMessage(), e);
      }
   }

   @GetMapping("/getLandingPage")
   @ResponseBody
   public String getLandingPage() {
      CastServerSetting castServerSetting = this.castServerSettingManager.findCastServerSetting();
      if (castServerSetting != null && !StringUtils.isBlank(castServerSetting.getApiUser())) {
         JSONObject landingJson = new JSONObject();
         landingJson.put("ssidName", castServerSetting.getSsidName());
         landingJson.put("guestDnsName", castServerSetting.getGuestDnsName());
         landingJson.put("showUrl", castServerSetting.getShowUrl());
         if (StringUtils.isNoneBlank(castServerSetting.getBackgroundPath())) {
            landingJson.put("background", "/SmartInstall/castServer/showBackground?name=" + castServerSetting.getBackgroundPath());
         } else {
            landingJson.put("background", "");
         }

         landingJson.put("guestWlanIp", castServerSetting.getGuestWlanIp());
         return landingJson.toString();
      } else {
         return Utils.buildFailReturnJson("Cast server is not registered");
      }
   }

   @PostMapping("/queryAnalyticalData")
   @ResponseBody
   public String queryAnalyticalData(
      String queryTimeType,
      @RequestParam(value = "relativeValue", defaultValue = "0") int relativeValue,
      String relativeUnit,
      String absoluteStartTime,
      String absoluteEndTime,
      String applicationName,
      String showType
   ) {
      SimpleDateFormat showSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
      SimpleDateFormat databaseDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
      databaseDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
      Date queryStartTime = null;
      Date queryEndTime = null;
      if (StringUtils.equalsAnyIgnoreCase(queryTimeType, "relative")) {
         Calendar targetCalendar = new GregorianCalendar();
         if (StringUtils.equalsAnyIgnoreCase(relativeUnit, "hour")) {
            targetCalendar.add(11, -relativeValue);
         } else if (StringUtils.equalsAnyIgnoreCase(relativeUnit, "day")) {
            targetCalendar.add(6, -relativeValue);
         } else if (StringUtils.equalsAnyIgnoreCase(relativeUnit, "month")) {
            targetCalendar.add(2, -relativeValue);
         }

         queryStartTime = targetCalendar.getTime();
         queryEndTime = new Date();
      } else {
         try {
            queryStartTime = showSimpleDateFormat.parse(absoluteStartTime);
            queryEndTime = showSimpleDateFormat.parse(absoluteEndTime);
         } catch (Exception e) {
            logger.error("data format err", e);
            return Utils.buildFailReturnJson("time format error");
         }
      }

      List<CastAnalyticalData> overlappedDataList = JpaManager.getCastAnalyticalDataManager()
         .findOverLappedCastAnalyticalDataByTimeFrame(queryStartTime, queryEndTime, applicationName);
      JSONObject result = new JSONObject();
      result.put("startTime", showSimpleDateFormat.format(queryStartTime));
      result.put("endTime", showSimpleDateFormat.format(queryEndTime));
      if (StringUtils.equalsAnyIgnoreCase(showType, "table")) {
         JSONArray dataList = this.calShowDataForTable(databaseDateFormat, queryStartTime, queryEndTime, overlappedDataList);
         result.put("data", dataList);
      } else if (StringUtils.equalsAnyIgnoreCase(showType, "heatmap")) {
         long milsecondsPerBlock = (queryEndTime.getTime() - queryStartTime.getTime()) / 15L;
         Date[] timeSpanArray = new Date[15];

         for (int i = 0; i < 15; i++) {
            timeSpanArray[i] = new Date(queryStartTime.getTime() + milsecondsPerBlock * i);
         }

         Map<String, Long[]> appUsedSessionCountMap = this.calDataMapForHeatMap(databaseDateFormat, queryEndTime, overlappedDataList, timeSpanArray);
         this.injectHeatmapDataToResult(result, timeSpanArray, appUsedSessionCountMap);
      }

      return result.toString();
   }

   private void injectHeatmapDataToResult(JSONObject result, Date[] timeSpanArray, Map<String, Long[]> appUsedSessionCountMap) {
      JSONArray timeSpanJsonArray = new JSONArray();

      for (Date d : timeSpanArray) {
         timeSpanJsonArray.put(new SimpleDateFormat("HH:mm '\n' yyyy-MM-dd").format(d));
      }

      result.put("times", timeSpanJsonArray);
      JSONArray appNameArray = new JSONArray();
      JSONArray dataArray = new JSONArray();
      long maxSessionCount = 0L;

      for (Entry<String, Long[]> appEntry : appUsedSessionCountMap.entrySet()) {
         int yIndex = appNameArray.length();
         appNameArray.put(appEntry.getKey());
         Long[] timeDataArray = appEntry.getValue();

         for (int j = 0; j < timeDataArray.length; j++) {
            JSONArray oneBlock = new JSONArray();
            oneBlock.put(j);
            oneBlock.put(yIndex);
            long sessionCount = timeDataArray[j];
            if (sessionCount > maxSessionCount) {
               maxSessionCount = sessionCount;
            }

            oneBlock.put(sessionCount);
            dataArray.put(oneBlock);
         }
      }

      result.put("apps", appNameArray);
      result.put("data", dataArray);
      result.put("maxValue", maxSessionCount);
   }

   private Map<String, Long[]> calDataMapForHeatMap(
      SimpleDateFormat databaseDateFormat, Date queryEndTime, List<CastAnalyticalData> overlappedDataList, Date[] timeSpanArray
   ) {
      Map<String, Long[]> appUsedSessionCountMap = new HashMap<>();

      for (CastAnalyticalData castAnalyticalData : overlappedDataList) {
         try {
            String appName = castAnalyticalData.getApplicationName();
            if (!appUsedSessionCountMap.containsKey(appName)) {
               Long[] initArray = new Long[15];
               Arrays.fill(initArray, Long.valueOf(0L));
               appUsedSessionCountMap.put(appName, initArray);
            }

            Date dataStartTime = databaseDateFormat.parse(castAnalyticalData.getStart());
            Date dataEndTime = databaseDateFormat.parse(castAnalyticalData.getEnd());
            Long[] countArray = appUsedSessionCountMap.get(appName);

            for (int i = 0; i < 15; i++) {
               Date startTime = timeSpanArray[i];
               Date endTime = i == 14 ? queryEndTime : timeSpanArray[i + 1];
               if (!endTime.before(dataStartTime) && !endTime.equals(dataStartTime) && !startTime.after(dataEndTime)) {
                  Long[] var15 = countArray;
                  int var16 = i;
                  var15[var16] = var15[var16] + 1L;
               }
            }
         } catch (Exception ex) {
            logger.error("cal heatmap data failuer", ex);
         }
      }

      return appUsedSessionCountMap;
   }

   private JSONArray calShowDataForTable(
      SimpleDateFormat databaseDateFormat, Date queryStartTime, Date queryEndTime, List<CastAnalyticalData> overlappedDataList
   ) {
      JSONArray dataList = new JSONArray();

      for (CastAnalyticalData castAnalyticalData : overlappedDataList) {
         try {
            JSONObject appObject = null;
            int i = 0;
            int j = dataList.length();

            while (true) {
               if (i < j) {
                  JSONObject tempData = dataList.getJSONObject(i);
                  if (!StringUtils.equals(tempData.getString("appName"), castAnalyticalData.getApplicationName())) {
                     i++;
                     continue;
                  }

                  appObject = tempData;
               }

               String sessionCountKey = "sessionCount";
               String totalTimeKey = "totalTime";
               if (appObject == null) {
                  appObject = new JSONObject();
                  appObject.put("appName", castAnalyticalData.getApplicationName());
                  appObject.put(sessionCountKey, 0);
                  appObject.put(totalTimeKey, 0);
                  dataList.put(appObject);
               }

               appObject.put(sessionCountKey, appObject.getInt(sessionCountKey) + 1);
               Date dataStartTime = databaseDateFormat.parse(castAnalyticalData.getStart());
               Date dataEndTime = databaseDateFormat.parse(castAnalyticalData.getEnd());
               long overlapInSecs = this.calOverlapTimeInSeconds(queryStartTime, queryEndTime, dataStartTime, dataEndTime);
               appObject.put(totalTimeKey, appObject.getLong(totalTimeKey) + overlapInSecs);
               break;
            }
         } catch (Exception e) {
            logger.error("cal by cast anylytical data failuer", e);
         }
      }

      return dataList;
   }

   private long calOverlapTimeInSeconds(Date queryStartTime, Date queryEndTime, Date dataStartTime, Date dataEndTime) {
      long maxStartTimeInMis = Long.max(queryStartTime.getTime(), dataStartTime.getTime());
      long minEndTimeMis = Long.min(queryEndTime.getTime() - 1000L, dataEndTime.getTime());
      return (minEndTimeMis + 1000L - maxStartTimeInMis) / 1000L;
   }
}
