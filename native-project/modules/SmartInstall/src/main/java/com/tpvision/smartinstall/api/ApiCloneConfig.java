package com.tpvision.smartinstall.api;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class ApiCloneConfig {
   public static final String SUPPORT_ASSIGN_TYPE_TAG = "supportAssignType";
   public static final List<String> SUPPORT_PACKAGE_TYPE = Collections.unmodifiableList(
      Arrays.asList("Firmware", "Clone", "Settings", "Channels", "Welcome", "Apps", "Content", "Banners", "UI", "Schedules")
   );
   public static final Map<String, List<String>> UPGRADE_PART_CLONE_FILE_MAP = new HashMap<>();

   private ApiCloneConfig() {
   }

   public static boolean isValidPackageTypeIgnoreCase(String packageType) {
      return packageType == null
         ? false
         : SUPPORT_PACKAGE_TYPE.stream().map(String::toLowerCase).collect(Collectors.toList()).contains(StringUtils.lowerCase(packageType));
   }

   public static String fixPackageTypeCase(String packageType) {
      return packageType.equalsIgnoreCase("UI") ? "UI" : StringUtils.capitalize(packageType.toLowerCase());
   }

   static {
      UPGRADE_PART_CLONE_FILE_MAP.put("Settings", Arrays.asList("TVSettings"));
      UPGRADE_PART_CLONE_FILE_MAP.put("Channels", Arrays.asList("TVChannelList", "MediaChannels"));
      UPGRADE_PART_CLONE_FILE_MAP.put("Apps", Arrays.asList("AndroidApps"));
      UPGRADE_PART_CLONE_FILE_MAP.put("Content", Arrays.asList("SmartInfoBrowser"));
      UPGRADE_PART_CLONE_FILE_MAP.put("Banners", Arrays.asList("Banner"));
      UPGRADE_PART_CLONE_FILE_MAP.put("Schedules", Arrays.asList("Schedules"));
   }
}
