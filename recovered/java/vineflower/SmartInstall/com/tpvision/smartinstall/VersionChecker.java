package com.tpvision.smartinstall;

import com.tpvision.smartinstall.util.TpvStringUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class VersionChecker {
   private static final Logger LOG = LoggerFactory.getLogger(VersionChecker.class);
   private static final String LATEST_VERSION_JSON_LOACTION = "https://cmnd7.s3.eu-central-1.amazonaws.com/latest_softwares_firmwares.json";
   private static VersionChecker.VersionDetail latestCmndVersion = null;
   private static VersionChecker.VersionDetail latestReceptionVersion = null;
   private static Map<String, VersionChecker.VersionDetail> latestFirmwareVersionMap = new HashMap<>();

   private VersionChecker() {
   }

   public static synchronized void loadLatestVersionInfoFromRemoteJson() {
      String jsonBody = null;

      try {
         SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
         factory.setConnectTimeout(10000);
         factory.setReadTimeout(10000);
         RestTemplate template = new RestTemplate(factory);
         ResponseEntity<String> response = template.exchange(
            "https://cmnd7.s3.eu-central-1.amazonaws.com/latest_softwares_firmwares.json", HttpMethod.GET, null, String.class
         );
         jsonBody = response.getBody();
         LOG.info("latest json data==>{}", jsonBody);
      } catch (Exception ex) {
         LOG.error("download latest json data server failure", ex);
      }

      if (!StringUtils.isBlank(jsonBody) && TpvStringUtils.isJSONString(jsonBody)) {
         JSONObject versionJson = new JSONObject(jsonBody);
         VersionChecker.VersionDetail cmndVersionDetail = extractCmndVersion(versionJson);
         if (cmndVersionDetail != null) {
            latestCmndVersion = cmndVersionDetail;
         }

         VersionChecker.VersionDetail receptionVersionDetail = extractReceptionVersion(versionJson);
         if (cmndVersionDetail != null) {
            latestReceptionVersion = receptionVersionDetail;
         }

         extractLatestFirmwareVersionMap(versionJson);
         LOG.info("extrace version data from json complete");
      } else {
         LOG.error("unable to load latest version info, exit");
      }
   }

   public static VersionChecker.VersionDetail getCmndNewVersion(String currentVersion) {
      return latestCmndVersion != null && isHaveNewerVersion(currentVersion, latestCmndVersion.getVersion()) ? latestCmndVersion : null;
   }

   public static VersionChecker.VersionDetail getReceptionNewVersion(String currentVersion) {
      return latestReceptionVersion != null && isHaveNewerVersion(currentVersion, latestReceptionVersion.getVersion()) ? latestReceptionVersion : null;
   }

   public static VersionChecker.VersionDetail getFirmwareNewVersion(String platformName, String modelName, String currentVersion) {
      String platformWithoutSpace = TpvStringUtils.removeSpaceFromTvPlatform(platformName);
      VersionChecker.VersionDetail versionDetail = latestFirmwareVersionMap.get((platformWithoutSpace + modelName).toLowerCase());
      if (versionDetail == null) {
         versionDetail = latestFirmwareVersionMap.get(platformWithoutSpace.toLowerCase());
      }

      return versionDetail != null && isHaveNewerVersion(currentVersion, versionDetail.getVersion()) ? versionDetail : null;
   }

   public static VersionChecker.VersionDetail getLatestReceptionVersionDirectly() {
      return latestReceptionVersion;
   }

   private static boolean isHaveNewerVersion(String currentVersion, String latestVersion) {
      if (StringUtils.isBlank(latestVersion)) {
         return false;
      }

      if (StringUtils.isBlank(currentVersion)) {
         return true;
      }

      if (StringUtils.equalsIgnoreCase(currentVersion, latestVersion)) {
         return false;
      }

      String[] currentVersionSplit = currentVersion.toLowerCase().split("\\.");
      String[] latestVersionSplit = latestVersion.toLowerCase().split("\\.");
      int checkSize = Math.min(currentVersionSplit.length, latestVersionSplit.length);

      for (int i = 0; i < checkSize; i++) {
         String currentValueStr = currentVersionSplit[i];
         String latestValueStr = latestVersionSplit[i];
         if (StringUtils.isNumeric(currentValueStr) && StringUtils.isNumeric(latestValueStr)) {
            int lastSubVersion = Integer.parseInt(latestValueStr);
            int currentSubVersion = Integer.parseInt(currentValueStr);
            if (lastSubVersion != currentSubVersion) {
               return lastSubVersion > currentSubVersion;
            }
         } else {
            int strCompareValue = StringUtils.compare(latestValueStr, currentValueStr);
            if (strCompareValue != 0) {
               return strCompareValue > 0;
            }
         }
      }

      return latestVersionSplit.length > currentVersionSplit.length;
   }

   private static void extractLatestFirmwareVersionMap(JSONObject jsonObject) {
      try {
         JSONArray latestFirmwareArray = jsonObject.getJSONArray("latest_firmwares");

         for (int i = 0; i < latestFirmwareArray.length(); i++) {
            JSONObject firmwareObject = latestFirmwareArray.getJSONObject(i);
            String platformName = firmwareObject.optString("platform");
            if (StringUtils.isBlank(platformName)) {
               LOG.warn("invalid firmware object: {}", firmwareObject);
            } else {
               JSONObject versionDetailObject = firmwareObject.getJSONObject("version_details");
               VersionChecker.VersionDetail versionDetail = new VersionChecker.VersionDetail();
               versionDetail.setVersion(versionDetailObject.optString("version"));
               versionDetail.setUrl(versionDetailObject.optString("url"));
               versionDetail.setLabel(versionDetailObject.optString("label"));
               List<String> filterModels = null;
               JSONArray models = firmwareObject.optJSONArray("models");
               if (models != null && models.length() > 0) {
                  filterModels = models.toList().stream().map(Object::toString).collect(Collectors.toList());
               }

               if (filterModels != null) {
                  filterModels.forEach(model -> {
                     VersionChecker.VersionDetail var10000 = latestFirmwareVersionMap.put((platformName + model).toLowerCase(), versionDetail);
                  });
               } else {
                  latestFirmwareVersionMap.put(platformName.toLowerCase(), versionDetail);
               }
            }
         }
      } catch (Exception ex) {
         LOG.error("extract firmware failure", ex);
      }
   }

   private static VersionChecker.VersionDetail extractReceptionVersion(JSONObject jsonObject) {
      try {
         JSONArray latestSoftwareArray = jsonObject.getJSONArray("latest_softwares");

         for (int i = 0; i < latestSoftwareArray.length(); i++) {
            JSONObject softwareObject = latestSoftwareArray.getJSONObject(i);
            if (StringUtils.equalsIgnoreCase("CMND Reception", softwareObject.optString("name"))) {
               JSONObject versionDetailObject = softwareObject.getJSONObject("version_details");
               VersionChecker.VersionDetail versionDetail = new VersionChecker.VersionDetail();
               versionDetail.setVersion(versionDetailObject.getString("version"));
               versionDetail.setUrl(versionDetailObject.optString("url"));
               return versionDetail;
            }
         }

         LOG.warn("can't find Reception version info in the json file");
      } catch (Exception ex) {
         LOG.error("extract version falure", ex);
      }

      return null;
   }

   private static VersionChecker.VersionDetail extractCmndVersion(JSONObject jsonObject) {
      try {
         JSONArray latestSoftwareArray = jsonObject.getJSONArray("latest_softwares");

         for (int i = 0; i < latestSoftwareArray.length(); i++) {
            JSONObject softwareObject = latestSoftwareArray.getJSONObject(i);
            if (StringUtils.equalsIgnoreCase("CMND", softwareObject.optString("name"))) {
               JSONObject versionDetailObject = softwareObject.getJSONObject("version_details");
               VersionChecker.VersionDetail versionDetail = new VersionChecker.VersionDetail();
               versionDetail.setVersion(versionDetailObject.getString("version"));
               versionDetail.setUrl(versionDetailObject.optString("url"));
               return versionDetail;
            }
         }

         LOG.warn("can't find CMND version info in the json file");
      } catch (Exception ex) {
         LOG.error("extract version falure", ex);
      }

      return null;
   }

   public static class VersionDetail {
      private String version;
      private String label;
      private String url;

      public String getVersion() {
         return this.version;
      }

      public void setVersion(String version) {
         this.version = version;
      }

      public String getLabel() {
         return this.label;
      }

      public void setLabel(String label) {
         this.label = label;
      }

      public String getUrl() {
         return this.url;
      }

      public void setUrl(String url) {
         this.url = url;
      }
   }
}
