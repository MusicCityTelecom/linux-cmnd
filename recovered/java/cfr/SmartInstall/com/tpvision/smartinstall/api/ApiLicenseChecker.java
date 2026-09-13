/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tpvision.smartinstall.api.ApiType;
import com.tpvision.smartinstall.api.LicenseStatus;
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
    private static final List<String> marketReleasedApiTypeList = Arrays.asList(Utils.getProductPropties().getProperty("license.api.type.released", "").split(","));
    private int currentRetryCount = 0;
    private static final int DEVICE_COUNT_UNLIMITED = 0;
    private static final long DEIVICE_LIMIT_REACH_NOTIFY_MIN_INTEVAL_MS = 86400000L;
    private static final Map<ApiType, Date> DEVICE_LIMIT_REACH_NOTIFY_LAST_SEND_TIME = new HashMap<ApiType, Date>();
    private String serialNumber;
    private String licenseActivationCode;
    private List<ApiLicenseInfo> serverLicenseInfoList = Collections.emptyList();
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
        LOG.info("test license api key response = <{}>", (Object)responseText);
        try {
            return new JSONObject(responseText).getInt("code") == 0;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public List<Map<String, String>> getFeatureLicenseDetail() {
        String statusTag = "status";
        ArrayList<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        for (ApiType apiType : ApiType.values()) {
            if (!apiType.isAuthRequired() || !marketReleasedApiTypeList.contains(apiType.getTag())) continue;
            HashMap<String, String> typeInfo = new HashMap<String, String>();
            typeInfo.put("feature", apiType.getFeatureName());
            String status = "invalid";
            String expireDate = "-";
            String deviceLimitStr = "Unlimited";
            for (ApiLicenseInfo info : this.serverLicenseInfoList) {
                if (info.getApiType() != apiType) continue;
                expireDate = new SimpleDateFormat("yyyy-MM-dd").format(info.getExpireDate());
                String string = status = info.isExpireLicense() ? "expired" : "valid";
                if (info.getDeviceLimit() == 0) break;
                deviceLimitStr = String.valueOf(info.getDeviceLimit());
                break;
            }
            typeInfo.put(statusTag, status);
            typeInfo.put("expireDate", expireDate);
            typeInfo.put("deviceLimit", deviceLimitStr);
            resultList.add(typeInfo);
        }
        HashMap<String, Integer> sortConfig = new HashMap<String, Integer>();
        sortConfig.put("valid", 0);
        sortConfig.put("expired", 1);
        sortConfig.put("invalid", 2);
        Collections.sort(resultList, (info1, info2) -> {
            int status1 = (Integer)sortConfig.get(info1.get(statusTag));
            int status2 = (Integer)sortConfig.get(info2.get(statusTag));
            return status1 - status2;
        });
        return resultList;
    }

    public JSONArray getSupportedLicenseFeatures() {
        JSONArray result = new JSONArray();
        for (ApiLicenseInfo info : this.serverLicenseInfoList) {
            if (info.isExpireLicense()) continue;
            JSONObject obj = new JSONObject();
            obj.put("feature", info.getApiType().getFeatureName());
            obj.put("expireDate", new SimpleDateFormat("yyyy-MM-dd").format(info.getExpireDate()));
            obj.put("gracePeriod", info.getGracePeriod());
            result.put(obj);
        }
        return result;
    }

    public boolean isSupportApiType(ApiType checkType) {
        for (ApiLicenseInfo info : this.serverLicenseInfoList) {
            if (info.getApiType() != checkType || info.isExpireLicense()) continue;
            return true;
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
            for (ApiLicenseInfo info : this.serverLicenseInfoList) {
                if (info.getApiType() != belongApiType) continue;
                if (info.isExpireLicense()) {
                    return LicenseStatus.EXPIRE_LICENSE;
                }
                if (info.deviceLimit != 0 && info.deviceLimit < JpaManager.getDevicesManager().findAllCount()) {
                    this.sendLimitReachNotifyToLicenseServer(info);
                    return LicenseStatus.DEVICE_LIMIT_REACHED;
                }
                return LicenseStatus.VALIDE_LICENSE;
            }
            return LicenseStatus.NO_LICENSE;
        }
        for (ApiLicenseInfo info : this.serverLicenseInfoList) {
            if (info.isExpireLicense()) continue;
            return LicenseStatus.VALIDE_LICENSE;
        }
        return LicenseStatus.EXPIRE_LICENSE;
    }

    public void submitDeviceCountToLicenseServer() {
        if (StringUtils.isBlank(this.licenseActivationCode)) {
            LOG.info("no need to sync device count to license server as no code binded");
            return;
        }
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("device_count", String.valueOf(JpaManager.getDevicesManager().findAllCount()));
        String responseText = this.requestLicenseServer(this.serialNumber, this.licenseActivationCode, SUBMIT_DEVICE_COUNT_ADDRESS, params);
        LOG.info("send  response = <{}>", (Object)responseText);
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

    private synchronized void sendLimitReachNotifyToLicenseServer(ApiLicenseInfo apiLicenseInfo) {
        Date lastSendTime = DEVICE_LIMIT_REACH_NOTIFY_LAST_SEND_TIME.get((Object)apiLicenseInfo.getApiType());
        if (lastSendTime != null && System.currentTimeMillis() - lastSendTime.getTime() < 86400000L) {
            LOG.info("No need to send license device limit notify as the send interval not reached");
            return;
        }
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("type", "license_device_limit_reach");
        params.add("ext", apiLicenseInfo.getApiType().getTag());
        String responseText = this.requestLicenseServer(this.serialNumber, this.licenseActivationCode, LICENSE_STATUS_NOTIFY_ADDRESS, params);
        LOG.info("send license device limit reached notify response = <{}>", (Object)responseText);
        try {
            if (new JSONObject(responseText).getInt("code") == 0) {
                DEVICE_LIMIT_REACH_NOTIFY_LAST_SEND_TIME.put(apiLicenseInfo.getApiType(), new Date());
            }
        }
        catch (Exception ex) {
            LOG.error("send limit reach email error", ex);
        }
    }

    private synchronized void loadServerLicenseInfoList(boolean isManual) {
        this.serialNumber = this.getHardwareSerialNo();
        this.licenseActivationCode = this.getLicenseActivationCodeFromDatabase();
        LOG.info("init license base data => <{}><{}>", (Object)this.serialNumber, (Object)this.licenseActivationCode);
        if (StringUtils.isBlank(this.licenseActivationCode)) {
            LOG.warn("license activation code not set, exit.");
            this.serverLicenseInfoList.clear();
            this.saveLatestLicenseCacheToDatabase();
            return;
        }
        String mode = isManual ? "manual" : "automatic";
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("mode", mode);
        params.add("agent_id", this.getCMNDServerAgentId());
        String responseText = this.requestLicenseServer(this.serialNumber, this.licenseActivationCode, LICENSE_DATA_QUERY_ADDRESS, params);
        LOG.info("license server response = <{}>", (Object)responseText);
        if (StringUtils.isBlank(responseText) || !TpvStringUtils.isJSONString(responseText)) {
            LOG.warn("handle license request failure");
            if (this.currentRetryCount < 2) {
                ++this.currentRetryCount;
                this.createRetryThread(isManual);
            } else {
                LOG.warn("max retry count {} reached. stop the retry action.", (Object)2);
                this.currentRetryCount = 0;
            }
            ApiLicenseCache apiLicenseCache = this.getLatestLicenseCacheFromDatabase();
            if (apiLicenseCache != null) {
                this.serverLicenseInfoList = apiLicenseCache.getLicenseList();
                int failureDayCount = TpvDateUtils.differentDays(apiLicenseCache.getCacheDate(), new Date());
                for (ApiLicenseInfo info : this.serverLicenseInfoList) {
                    info.decreaseGracePeriod(failureDayCount);
                }
            } else {
                this.serverLicenseInfoList.clear();
            }
            this.saveLatestLicenseCacheToDatabase();
        } else {
            this.currentRetryCount = 0;
            this.extractServerLicenseInfo(responseText, this.licenseActivationCode);
            this.saveLatestLicenseCacheToDatabase();
        }
        if (isManual) {
            this.submitDeviceCountToLicenseServer();
        }
    }

    private void createRetryThread(final boolean isManual) {
        final int delayMinutes = this.currentRetryCount * 5;
        LOG.warn("create a delay thread to retry the {} time in {} minutes", (Object)this.currentRetryCount, (Object)delayMinutes);
        new Thread(new TpvRunableTask(){

            @Override
            public void execute() {
                try {
                    Thread.sleep((long)(delayMinutes * 60) * 1000L);
                    ApiLicenseChecker.getInstance().loadAll(isManual);
                }
                catch (Exception e) {
                    LOG.error(e.getMessage(), e);
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
            return;
        }
        String licenseEncodedData = responseJson.getString("data");
        if (StringUtils.isEmpty(licenseEncodedData)) {
            LOG.warn("server have no valid license data");
            return;
        }
        String decryptedData = EncryptionDecryptionUtility.decrypt(licenseEncodedData, activationCode);
        if (decryptedData == null) {
            LOG.warn("cant decrypt server license data =>{} ", (Object)licenseEncodedData);
            return;
        }
        ArrayList<ApiLicenseInfo> result = new ArrayList<ApiLicenseInfo>();
        String[] featureArray = decryptedData.split("\\$");
        if (featureArray.length == 0) {
            LOG.warn("decrypted license data empty:<{}>", (Object)decryptedData);
            return;
        }
        for (String featureLicenseInfo : featureArray) {
            ApiLicenseInfo supportApiTypeInfo = this.initSupportApiTypeInfo(featureLicenseInfo);
            if (supportApiTypeInfo == null) continue;
            result.add(supportApiTypeInfo);
        }
        this.serverLicenseInfoList = result;
    }

    private ApiLicenseInfo initSupportApiTypeInfo(String featureLicenseInfo) {
        String[] licenseConfig = featureLicenseInfo.split(",");
        if (licenseConfig.length != 4) {
            LOG.warn("error support feature license:{}", (Object)featureLicenseInfo);
            return null;
        }
        String apiTag = licenseConfig[0];
        ApiType apiType = ApiType.fromTag(apiTag);
        if (apiType == null) {
            LOG.warn("error support feature api type tag:{}", (Object)apiTag);
            return null;
        }
        if (!marketReleasedApiTypeList.contains(apiTag)) {
            LOG.warn("supported feature not released tag:{}", (Object)apiTag);
            return null;
        }
        Date expireDate = this.tryParseStringToDate(licenseConfig[1]);
        if (expireDate == null) {
            LOG.warn("error support feature date format:{}", (Object)licenseConfig[1]);
            return null;
        }
        int gracePeriod = TpvStringUtils.tryParseInt(licenseConfig[2], -1);
        if (gracePeriod < 0) {
            LOG.warn("error support feature grace period format:{}", (Object)licenseConfig[2]);
            return null;
        }
        if (DateUtils.addDays(expireDate, 1).before(new Date())) {
            LOG.warn("this license has been invalid :{}", (Object)featureLicenseInfo);
            return null;
        }
        int deviceLimit = TpvStringUtils.tryParseInt(licenseConfig[3], -1);
        if (deviceLimit < 0) {
            LOG.warn("error support device limit value format:{}", (Object)licenseConfig[3]);
            return null;
        }
        return new ApiLicenseInfo(apiType, expireDate, gracePeriod, deviceLimit);
    }

    private String getCMNDServerAgentId() {
        try {
            String metaString = FileUtils.readFileToString(new File("C:\\ProgramData\\filebeat\\meta.json"), StandardCharsets.UTF_8);
            JSONObject metaObject = new JSONObject(metaString);
            return metaObject.getString("uuid");
        }
        catch (Exception ex) {
            LOG.error("read meta info failure", ex);
            return "";
        }
    }

    private String requestLicenseServer(String deviceSerial, String activationCode, String url, MultiValueMap<String, String> params) {
        String[] codeInfos = activationCode.split("-");
        String tag = codeInfos[codeInfos.length - 1];
        if (params == null) {
            params = new LinkedMultiValueMap<String, String>();
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
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, null);
            ResponseEntity<String> response = template.exchange(url, HttpMethod.POST, requestEntity, String.class, new Object[0]);
            return (String)response.getBody();
        }
        catch (Exception ex) {
            LOG.error("request to cloud license server failure", ex);
            return null;
        }
    }

    private Date tryParseStringToDate(String expireDate) {
        String fullExpireDate = expireDate + " 23:59:59";
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return spf.parse(fullExpireDate);
        }
        catch (Exception exception) {
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
        ApiLicenseCache apiLicenseCache = new ApiLicenseCache();
        apiLicenseCache.setCacheDate(new Date());
        apiLicenseCache.setLicenseList(this.serverLicenseInfoList);
        String licenseCache = this.getLicenseCacheGson().toJson(apiLicenseCache);
        SIConfig sIConfig = JpaManager.getSIConfigManager().getSIConfig();
        String encCache = EncryptionDecryptionUtility.encrypt(licenseCache, LICENSE_CACHE_ENC_KEY);
        sIConfig.setLicenseData(encCache);
        JpaManager.getSIConfigManager().saveSIConfig(sIConfig);
    }

    private ApiLicenseCache getLatestLicenseCacheFromDatabase() {
        String encLicenseCache = JpaManager.getSIConfigManager().getSIConfig().getLicenseData();
        if (StringUtils.isEmpty(encLicenseCache)) {
            return null;
        }
        String decLicenseCache = EncryptionDecryptionUtility.decrypt(encLicenseCache, LICENSE_CACHE_ENC_KEY);
        if (decLicenseCache == null) {
            return null;
        }
        return this.getLicenseCacheGson().fromJson(decLicenseCache, ApiLicenseCache.class);
    }

    private void initExApiSwitch() {
        ExApi exapi = JpaManager.getExApiManager().loadExApi();
        this.isExApiSwithOpen = exapi != null && exapi.getApi().equalsIgnoreCase("on");
        LOG.info("current exapi switch is {}", (Object)this.isExApiSwithOpen);
    }

    private String getHardwareSerialNo() {
        HardwareAbstractionLayer hal = new SystemInfo().getHardware();
        CentralProcessor centralProcessor = hal.getProcessor();
        CentralProcessor.ProcessorIdentifier identifier = centralProcessor.getProcessorIdentifier();
        String cpuRawData = identifier.getProcessorID() + identifier.getFamily() + identifier.getIdentifier();
        LOG.info("cpuRawData=>{}", (Object)cpuRawData);
        Baseboard baseBoard = hal.getComputerSystem().getBaseboard();
        String motherBoardRawData = baseBoard.getManufacturer() + baseBoard.getModel() + baseBoard.getSerialNumber() + baseBoard.getVersion();
        LOG.info("motherBoardRawData=>{}", (Object)motherBoardRawData);
        String md5HashValue = DigestUtils.md5Hex((cpuRawData + motherBoardRawData).toLowerCase());
        return md5HashValue + this.getMd5CheckSum(md5HashValue);
    }

    private String getMd5CheckSum(String md5HashValue) {
        if (md5HashValue.length() != 32) {
            return "0000";
        }
        long sum = 0L;
        for (int i = 0; i < 8; ++i) {
            sum += Long.parseLong(md5HashValue.substring(i * 4, (i + 1) * 4), 16);
        }
        String result = Long.toHexString(sum);
        if (result.length() > 4) {
            result = result.substring(0, 4);
        } else if (result.length() < 4) {
            int zeroCount = 4 - result.length();
            StringBuilder zeroPad = new StringBuilder();
            for (int j = 0; j < zeroCount; ++j) {
                zeroPad.append("0");
            }
            result = zeroPad.toString() + result;
        }
        return result;
    }

    private String getAuthrizationByRequestData(MultiValueMap<String, String> params, String activationCode) {
        ArrayList keys = new ArrayList(params.keySet());
        Collections.sort(keys);
        StringBuilder rawRequestString = new StringBuilder();
        for (String param : keys) {
            rawRequestString.append(param).append("=").append(params.getFirst(param)).append("&");
        }
        return DigestUtils.md5Hex((rawRequestString.toString() + activationCode).getBytes()).toLowerCase();
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

    private static class ApiLicenseCache {
        private Date cacheDate;
        List<ApiLicenseInfo> licenseList;

        private ApiLicenseCache() {
        }

        public Date getCacheDate() {
            return this.cacheDate;
        }

        public void setCacheDate(Date cacheDate) {
            this.cacheDate = cacheDate;
        }

        public List<ApiLicenseInfo> getLicenseList() {
            return this.licenseList;
        }

        public void setLicenseList(List<ApiLicenseInfo> licenseList) {
            this.licenseList = licenseList;
        }
    }
}

