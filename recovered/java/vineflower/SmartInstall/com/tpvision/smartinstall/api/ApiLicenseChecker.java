package com.tpvision.smartinstall.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tpvision.smartinstall.dao.core.ExApi;
import com.tpvision.smartinstall.dao.core.SIConfig;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.util.Configs;
import com.tpvision.smartinstall.util.EncryptionDecryptionUtility;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.TpvRunableTask;
import com.tpvision.smartinstall.util.TpvStringUtils;
import com.tpvision.smartinstall.util.Utils;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import oshi.SystemInfo;
import oshi.hardware.Baseboard;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.CentralProcessor.ProcessorIdentifier;

public class ApiLicenseChecker {
   private static final Logger LOG = LoggerFactory.getLogger(ApiLicenseChecker.class);
   private static final int MAX_RETRY_COUNT_FOR_LOAD = 2;
   private static final int RETRY_MINUTE_INCREASE_INTERVAL = 5;
   private static final String LICENSE_CACHE_ENC_KEY = "%^*RT^F& IUHLSBDFKJ<SDFHPOIUSDFSDF";
   private static final String LICENSE_SERVER_URL = Configs.getProperty("license.server.url");
   private static final String LICENSE_DATA_QUERY_ADDRESS = LICENSE_SERVER_URL + "/api/v2/licenses/info";
   private static final String LICENSE_API_KEY_CHECK_ADDRESS = LICENSE_SERVER_URL + "/api/v2/licenses/check";
   private static final String LICENSE_STATUS_NOTIFY_ADDRESS = LICENSE_SERVER_URL + "/api/v2/licenses/notice";
   private static final String SUBMIT_DEVICE_COUNT_ADDRESS = LICENSE_SERVER_URL + "/api/v2/licenses/submit_device_count";
   private static final ApiLicenseChecker instance = new ApiLicenseChecker();
   private static final List<String> marketReleasedApiTypeList = Arrays.asList(
      Utils.getProductPropties().getProperty("license.api.type.released", "").split(",")
   );
   private int currentRetryCount = 0;
   private static final int DEVICE_COUNT_UNLIMITED = 0;
   private static final long DEIVICE_LIMIT_REACH_NOTIFY_MIN_INTEVAL_MS = 86400000L;
   private static final Map<ApiType, Date> DEVICE_LIMIT_REACH_NOTIFY_LAST_SEND_TIME = new HashMap<>();
   private String serialNumber;
   private String licenseActivationCode;
   private List<ApiLicenseChecker.ApiLicenseInfo> serverLicenseInfoList = Collections.emptyList();
   private boolean isExApiSwithOpen = false;

   public static ApiLicenseChecker getInstance() {
      return instance;
   }

   public void loadAll(boolean isManual) {
      this.initExApiSwitch();
      this.loadServerLicenseInfoList(isManual);
   }

   public void reloadExApiSwitch() {
      this.initExApiSwitch();
   }

   public void reload(boolean isManual) {
      this.loadServerLicenseInfoList(isManual);
   }

   public boolean verifyLicenseActivationCode(String activationCode) {
      String responseText = this.requestLicenseServer(this.serialNumber, activationCode, LICENSE_API_KEY_CHECK_ADDRESS, null);
      LOG.info("test license api key response = <{}>", responseText);

      try {
         return new JSONObject(responseText).getInt("code") == 0;
      } catch (Exception ex) {
         return false;
      }
   }

   public List<Map<String, String>> getFeatureLicenseDetail() {
      String statusTag = "status";
      List<Map<String, String>> resultList = new ArrayList<>();

      for (ApiType apiType : ApiType.values()) {
         if (apiType.isAuthRequired() && marketReleasedApiTypeList.contains(apiType.getTag())) {
            Map<String, String> typeInfo = new HashMap<>();
            typeInfo.put("feature", apiType.getFeatureName());
            String status = "invalid";
            String expireDate = "-";
            String deviceLimitStr = "Unlimited";

            for (ApiLicenseChecker.ApiLicenseInfo info : this.serverLicenseInfoList) {
               if (info.getApiType() == apiType) {
                  expireDate = new SimpleDateFormat("yyyy-MM-dd").format(info.getExpireDate());
                  status = info.isExpireLicense() ? "expired" : "valid";
                  if (info.getDeviceLimit() != 0) {
                     deviceLimitStr = String.valueOf(info.getDeviceLimit());
                  }
                  break;
               }
            }

            typeInfo.put(statusTag, status);
            typeInfo.put("expireDate", expireDate);
            typeInfo.put("deviceLimit", deviceLimitStr);
            resultList.add(typeInfo);
         }
      }

      Map<String, Integer> sortConfig = new HashMap<>();
      sortConfig.put("valid", 0);
      sortConfig.put("expired", 1);
      sortConfig.put("invalid", 2);
      Collections.sort(resultList, (info1, info2) -> {
         int status1 = sortConfig.get(info1.get(statusTag));
         int status2 = sortConfig.get(info2.get(statusTag));
         return status1 - status2;
      });
      return resultList;
   }

   public JSONArray getSupportedLicenseFeatures() {
      JSONArray result = new JSONArray();

      for (ApiLicenseChecker.ApiLicenseInfo info : this.serverLicenseInfoList) {
         if (!info.isExpireLicense()) {
            JSONObject obj = new JSONObject();
            obj.put("feature", info.getApiType().getFeatureName());
            obj.put("expireDate", new SimpleDateFormat("yyyy-MM-dd").format(info.getExpireDate()));
            obj.put("gracePeriod", info.getGracePeriod());
            result.put(obj);
         }
      }

      return result;
   }

   public boolean isSupportApiType(ApiType checkType) {
      for (ApiLicenseChecker.ApiLicenseInfo info : this.serverLicenseInfoList) {
         if (info.getApiType() == checkType && !info.isExpireLicense()) {
            return true;
         }
      }

      return false;
   }

   public LicenseStatus check(String requestPath) {
      if (this.serverLicenseInfoList.isEmpty()) {
         return LicenseStatus.NO_LICENSE;
      }

      ApiType belongApiType = ApiType.getApiTypeByPath(requestPath);
      if (belongApiType == null) {
         return LicenseStatus.NO_LICENSE;
      }

      if (belongApiType.isAuthRequired()) {
         for (ApiLicenseChecker.ApiLicenseInfo info : this.serverLicenseInfoList) {
            if (info.getApiType() == belongApiType) {
               if (info.isExpireLicense()) {
                  return LicenseStatus.EXPIRE_LICENSE;
               }

               if (info.deviceLimit != 0 && info.deviceLimit < JpaManager.getDevicesManager().findAllCount()) {
                  this.sendLimitReachNotifyToLicenseServer(info);
                  return LicenseStatus.DEVICE_LIMIT_REACHED;
               }

               return LicenseStatus.VALIDE_LICENSE;
            }
         }

         return LicenseStatus.NO_LICENSE;
      } else {
         for (ApiLicenseChecker.ApiLicenseInfo info : this.serverLicenseInfoList) {
            if (!info.isExpireLicense()) {
               return LicenseStatus.VALIDE_LICENSE;
            }
         }

         return LicenseStatus.EXPIRE_LICENSE;
      }
   }

   public void submitDeviceCountToLicenseServer() {
      if (StringUtils.isBlank(this.licenseActivationCode)) {
         LOG.info("no need to sync device count to license server as no code binded");
      } else {
         MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
         params.add("device_count", String.valueOf(JpaManager.getDevicesManager().findAllCount()));
         String responseText = this.requestLicenseServer(this.serialNumber, this.licenseActivationCode, SUBMIT_DEVICE_COUNT_ADDRESS, params);
         LOG.info("send  response = <{}>", responseText);
      }
   }

   public String getSerialNumber() {
      return this.serialNumber;
   }

   public String getLicenseActivationCode() {
      return this.licenseActivationCode;
   }

   public boolean isExApiSwithOpen() {
      return this.isExApiSwithOpen;
   }

   private ApiLicenseChecker() {
   }

   private synchronized void sendLimitReachNotifyToLicenseServer(ApiLicenseChecker.ApiLicenseInfo apiLicenseInfo) {
      Date lastSendTime = DEVICE_LIMIT_REACH_NOTIFY_LAST_SEND_TIME.get(apiLicenseInfo.getApiType());
      if (lastSendTime != null && System.currentTimeMillis() - lastSendTime.getTime() < 86400000L) {
         LOG.info("No need to send license device limit notify as the send interval not reached");
      } else {
         MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
         params.add("type", "license_device_limit_reach");
         params.add("ext", apiLicenseInfo.getApiType().getTag());
         String responseText = this.requestLicenseServer(this.serialNumber, this.licenseActivationCode, LICENSE_STATUS_NOTIFY_ADDRESS, params);
         LOG.info("send license device limit reached notify response = <{}>", responseText);

         try {
            if (new JSONObject(responseText).getInt("code") == 0) {
               DEVICE_LIMIT_REACH_NOTIFY_LAST_SEND_TIME.put(apiLicenseInfo.getApiType(), new Date());
            }
         } catch (Exception ex) {
            LOG.error("send limit reach email error", ex);
         }
      }
   }

   private synchronized void loadServerLicenseInfoList(boolean isManual) {
      this.serialNumber = this.getHardwareSerialNo();
      this.licenseActivationCode = this.getLicenseActivationCodeFromDatabase();
      LOG.info("init license base data => <{}><{}>", this.serialNumber, this.licenseActivationCode);
      if (StringUtils.isBlank(this.licenseActivationCode)) {
         LOG.warn("license activation code not set, exit.");
         this.serverLicenseInfoList.clear();
         this.saveLatestLicenseCacheToDatabase();
      } else {
         String mode = isManual ? "manual" : "automatic";
         MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
         params.add("mode", mode);
         params.add("agent_id", this.getCMNDServerAgentId());
         String responseText = this.requestLicenseServer(this.serialNumber, this.licenseActivationCode, LICENSE_DATA_QUERY_ADDRESS, params);
         LOG.info("license server response = <{}>", responseText);
         if (!StringUtils.isBlank(responseText) && TpvStringUtils.isJSONString(responseText)) {
            this.currentRetryCount = 0;
            this.extractServerLicenseInfo(responseText, this.licenseActivationCode);
            this.saveLatestLicenseCacheToDatabase();
         } else {
            LOG.warn("handle license request failure");
            if (this.currentRetryCount < 2) {
               this.currentRetryCount++;
               this.createRetryThread(isManual);
            } else {
               LOG.warn("max retry count {} reached. stop the retry action.", 2);
               this.currentRetryCount = 0;
            }

            ApiLicenseChecker.ApiLicenseCache apiLicenseCache = this.getLatestLicenseCacheFromDatabase();
            if (apiLicenseCache != null) {
               this.serverLicenseInfoList = apiLicenseCache.getLicenseList();
               int failureDayCount = TpvDateUtils.differentDays(apiLicenseCache.getCacheDate(), new Date());

               for (ApiLicenseChecker.ApiLicenseInfo info : this.serverLicenseInfoList) {
                  info.decreaseGracePeriod(failureDayCount);
               }
            } else {
               this.serverLicenseInfoList.clear();
            }

            this.saveLatestLicenseCacheToDatabase();
         }

         if (isManual) {
            this.submitDeviceCountToLicenseServer();
         }
      }
   }

   private void createRetryThread(final boolean isManual) {
      final int delayMinutes = this.currentRetryCount * 5;
      LOG.warn("create a delay thread to retry the {} time in {} minutes", this.currentRetryCount, delayMinutes);
      new Thread(new TpvRunableTask() {
         @Override
         public void execute() {
            try {
               Thread.sleep(delayMinutes * 60 * 1000L);
               ApiLicenseChecker.getInstance().loadAll(isManual);
            } catch (Exception e) {
               ApiLicenseChecker.LOG.error(e.getMessage(), e);
            }
         }
      }).start();
   }

   private void extractServerLicenseInfo(String responseText, String activationCode) {
      this.serverLicenseInfoList.clear();
      JSONObject responseJson = new JSONObject(responseText);
      int resultCode = responseJson.optInt("code", -1);
      if (resultCode != 0) {
         LOG.warn("license response return error:[{}]", responseJson.opt("msg"));
      } else {
         String licenseEncodedData = responseJson.getString("data");
         if (StringUtils.isEmpty(licenseEncodedData)) {
            LOG.warn("server have no valid license data");
         } else {
            String decryptedData = EncryptionDecryptionUtility.decrypt(licenseEncodedData, activationCode);
            if (decryptedData == null) {
               LOG.warn("cant decrypt server license data =>{} ", licenseEncodedData);
            } else {
               List<ApiLicenseChecker.ApiLicenseInfo> result = new ArrayList<>();
               String[] featureArray = decryptedData.split("\\$");
               if (featureArray.length == 0) {
                  LOG.warn("decrypted license data empty:<{}>", decryptedData);
               } else {
                  for (String featureLicenseInfo : featureArray) {
                     ApiLicenseChecker.ApiLicenseInfo supportApiTypeInfo = this.initSupportApiTypeInfo(featureLicenseInfo);
                     if (supportApiTypeInfo != null) {
                        result.add(supportApiTypeInfo);
                     }
                  }

                  this.serverLicenseInfoList = result;
               }
            }
         }
      }
   }

   private ApiLicenseChecker.ApiLicenseInfo initSupportApiTypeInfo(String featureLicenseInfo) {
      String[] licenseConfig = featureLicenseInfo.split(",");
      if (licenseConfig.length != 4) {
         LOG.warn("error support feature license:{}", featureLicenseInfo);
         return null;
      } else {
         String apiTag = licenseConfig[0];
         ApiType apiType = ApiType.fromTag(apiTag);
         if (apiType == null) {
            LOG.warn("error support feature api type tag:{}", apiTag);
            return null;
         } else if (!marketReleasedApiTypeList.contains(apiTag)) {
            LOG.warn("supported feature not released tag:{}", apiTag);
            return null;
         } else {
            Date expireDate = this.tryParseStringToDate(licenseConfig[1]);
            if (expireDate == null) {
               LOG.warn("error support feature date format:{}", licenseConfig[1]);
               return null;
            } else {
               int gracePeriod = TpvStringUtils.tryParseInt(licenseConfig[2], -1);
               if (gracePeriod < 0) {
                  LOG.warn("error support feature grace period format:{}", licenseConfig[2]);
                  return null;
               } else if (DateUtils.addDays(expireDate, 1).before(new Date())) {
                  LOG.warn("this license has been invalid :{}", featureLicenseInfo);
                  return null;
               } else {
                  int deviceLimit = TpvStringUtils.tryParseInt(licenseConfig[3], -1);
                  if (deviceLimit < 0) {
                     LOG.warn("error support device limit value format:{}", licenseConfig[3]);
                     return null;
                  } else {
                     return new ApiLicenseChecker.ApiLicenseInfo(apiType, expireDate, gracePeriod, deviceLimit);
                  }
               }
            }
         }
      }
   }

   private String getCMNDServerAgentId() {
      try {
         String metaString = FileUtils.readFileToString(new File("C:\\ProgramData\\filebeat\\meta.json"), StandardCharsets.UTF_8);
         JSONObject metaObject = new JSONObject(metaString);
         return metaObject.getString("uuid");
      } catch (Exception ex) {
         LOG.error("read meta info failure", ex);
         return "";
      }
   }

   private String requestLicenseServer(String deviceSerial, String activationCode, String url, MultiValueMap<String, String> params) {
      String[] codeInfos = activationCode.split("-");
      String tag = codeInfos[codeInfos.length - 1];
      if (params == null) {
         params = new LinkedMultiValueMap<>();
      }

      params.add("tag", tag);
      params.add("serial", deviceSerial);
      String authorization = this.getAuthrizationByRequestData(params, activationCode);
      params.add("authorization", authorization);
      SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
      factory.setConnectTimeout(10000);
      factory.setReadTimeout(10000);
      RestTemplate template = new RestTemplate(factory);

      try {
         HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, null);
         ResponseEntity<String> response = template.exchange(url, HttpMethod.POST, requestEntity, String.class);
         return response.getBody();
      } catch (Exception ex) {
         LOG.error("request to cloud license server failure", ex);
         return null;
      }
   }

   private Date tryParseStringToDate(String expireDate) {
      String fullExpireDate = expireDate + " 23:59:59";
      SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

      try {
         return spf.parse(fullExpireDate);
      } catch (Exception var5) {
         return null;
      }
   }

   private String getLicenseActivationCodeFromDatabase() {
      return JpaManager.getSIConfigManager().getSIConfig().getApiLicense();
   }

   private Gson getLicenseCacheGson() {
      return new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
   }

   private void saveLatestLicenseCacheToDatabase() {
      ApiLicenseChecker.ApiLicenseCache apiLicenseCache = new ApiLicenseChecker.ApiLicenseCache();
      apiLicenseCache.setCacheDate(new Date());
      apiLicenseCache.setLicenseList(this.serverLicenseInfoList);
      String licenseCache = this.getLicenseCacheGson().toJson(apiLicenseCache);
      SIConfig sIConfig = JpaManager.getSIConfigManager().getSIConfig();
      String encCache = EncryptionDecryptionUtility.encrypt(licenseCache, "%^*RT^F& IUHLSBDFKJ<SDFHPOIUSDFSDF");
      sIConfig.setLicenseData(encCache);
      JpaManager.getSIConfigManager().saveSIConfig(sIConfig);
   }

   private ApiLicenseChecker.ApiLicenseCache getLatestLicenseCacheFromDatabase() {
      String encLicenseCache = JpaManager.getSIConfigManager().getSIConfig().getLicenseData();
      if (StringUtils.isEmpty(encLicenseCache)) {
         return null;
      }

      String decLicenseCache = EncryptionDecryptionUtility.decrypt(encLicenseCache, "%^*RT^F& IUHLSBDFKJ<SDFHPOIUSDFSDF");
      return decLicenseCache == null ? null : this.getLicenseCacheGson().fromJson(decLicenseCache, ApiLicenseChecker.ApiLicenseCache.class);
   }

   private void initExApiSwitch() {
      ExApi exapi = JpaManager.getExApiManager().loadExApi();
      this.isExApiSwithOpen = exapi != null && exapi.getApi().equalsIgnoreCase("on");
      LOG.info("current exapi switch is {}", this.isExApiSwithOpen);
   }

   private String getHardwareSerialNo() {
      HardwareAbstractionLayer hal = new SystemInfo().getHardware();
      CentralProcessor centralProcessor = hal.getProcessor();
      ProcessorIdentifier identifier = centralProcessor.getProcessorIdentifier();
      String cpuRawData = identifier.getProcessorID() + identifier.getFamily() + identifier.getIdentifier();
      LOG.info("cpuRawData=>{}", cpuRawData);
      Baseboard baseBoard = hal.getComputerSystem().getBaseboard();
      String motherBoardRawData = baseBoard.getManufacturer() + baseBoard.getModel() + baseBoard.getSerialNumber() + baseBoard.getVersion();
      LOG.info("motherBoardRawData=>{}", motherBoardRawData);
      String md5HashValue = DigestUtils.md5Hex((cpuRawData + motherBoardRawData).toLowerCase());
      return md5HashValue + this.getMd5CheckSum(md5HashValue);
   }

   private String getMd5CheckSum(String md5HashValue) {
      if (md5HashValue.length() != 32) {
         return "0000";
      }

      long sum = 0L;

      for (int i = 0; i < 8; i++) {
         sum += Long.parseLong(md5HashValue.substring(i * 4, (i + 1) * 4), 16);
      }

      String result = Long.toHexString(sum);
      if (result.length() > 4) {
         result = result.substring(0, 4);
      } else if (result.length() < 4) {
         int zeroCount = 4 - result.length();
         StringBuilder zeroPad = new StringBuilder();

         for (int j = 0; j < zeroCount; j++) {
            zeroPad.append("0");
         }

         result = zeroPad.toString() + result;
      }

      return result;
   }

   private String getAuthrizationByRequestData(MultiValueMap<String, String> params, String activationCode) {
      List<String> keys = new ArrayList<>(params.keySet());
      Collections.sort(keys);
      StringBuilder rawRequestString = new StringBuilder();

      for (String param : keys) {
         rawRequestString.append(param).append("=").append(params.getFirst(param)).append("&");
      }

      return DigestUtils.md5Hex((rawRequestString.toString() + activationCode).getBytes()).toLowerCase();
   }

   private static class ApiLicenseCache {
      private Date cacheDate;
      List<ApiLicenseChecker.ApiLicenseInfo> licenseList;

      private ApiLicenseCache() {
      }

      public Date getCacheDate() {
         return this.cacheDate;
      }

      public void setCacheDate(Date cacheDate) {
         this.cacheDate = cacheDate;
      }

      public List<ApiLicenseChecker.ApiLicenseInfo> getLicenseList() {
         return this.licenseList;
      }

      public void setLicenseList(List<ApiLicenseChecker.ApiLicenseInfo> licenseList) {
         this.licenseList = licenseList;
      }
   }

   private static class ApiLicenseInfo {
      private ApiType apiType;
      private Date expireDate;
      private int gracePeriod;
      private int deviceLimit = 0;

      public ApiLicenseInfo(ApiType apiType, Date expireDate, int gracePeriod, int deviceLimit) {
         this.apiType = apiType;
         this.expireDate = expireDate;
         this.gracePeriod = gracePeriod;
         this.deviceLimit = deviceLimit;
      }

      public boolean isExpireLicense() {
         return this.expireDate.before(new Date()) || this.gracePeriod < 0;
      }

      public void decreaseGracePeriod(int decreaseCount) {
         this.gracePeriod -= decreaseCount;
      }

      public ApiType getApiType() {
         return this.apiType;
      }

      public Date getExpireDate() {
         return this.expireDate;
      }

      public int getGracePeriod() {
         return this.gracePeriod;
      }

      public int getDeviceLimit() {
         return this.deviceLimit;
      }
   }
}
