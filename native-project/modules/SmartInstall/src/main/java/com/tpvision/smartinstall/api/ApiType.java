package com.tpvision.smartinstall.api;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public enum ApiType {
   USER("user", "User login", Arrays.asList("/api/user"), false),
   RECEPTION("reception", "Reception", Arrays.asList("/api/reception"), false),
   NOTIFICATION("notification", "Notification", Arrays.asList("/api/notification"), false),
   PMS("pms", "PMS", Arrays.asList("/api/pms"), true),
   REMOTE_CONTROL("rc", "Remote Control", Arrays.asList("/api/rc"), true),
   MYCHOICE("mychoice", "MyChoice", Arrays.asList("/api/mychoice"), true),
   EXAPI("exapi", "ExAPI", Arrays.asList("/exapi"), true);

   private String tag;
   private String featureName;
   private List<String> supportApiPaths;
   private boolean isAuthRequired;

   ApiType(String tag, String featureName, List<String> supportApiPaths, boolean isAuthRequired) {
      this.tag = tag;
      this.featureName = featureName;
      this.supportApiPaths = supportApiPaths;
      this.isAuthRequired = isAuthRequired;
   }

   public static ApiType fromTag(String tag) {
      for (ApiType type : values()) {
         if (StringUtils.equalsIgnoreCase(tag, type.tag)) {
            return type;
         }
      }

      return null;
   }

   public static ApiType getApiTypeByPath(String requestPath) {
      for (ApiType apiType : values()) {
         for (String supportPath : apiType.getSupportApiPaths()) {
            if (requestPath.startsWith(supportPath)) {
               return apiType;
            }
         }
      }

      return null;
   }

   public String getFeatureName() {
      return this.featureName;
   }

   public boolean isAuthRequired() {
      return this.isAuthRequired;
   }

   public List<String> getSupportApiPaths() {
      return this.supportApiPaths;
   }

   public String getTag() {
      return this.tag;
   }
}
