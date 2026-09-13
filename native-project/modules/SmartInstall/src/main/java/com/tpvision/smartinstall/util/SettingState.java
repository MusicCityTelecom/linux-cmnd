package com.tpvision.smartinstall.util;

import com.tpvision.smartinstall.core.SettingChannelBean;
import com.tpvision.smartinstall.xml.Setting;
import com.tpvision.smartinstall.xml.Settings;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SettingState {
   private static final Logger LOG = LoggerFactory.getLogger(SettingState.class);
   private Map<String, String> paramToValuesMap = new HashMap<>();
   private Map<String, String> settingNameToValuesMap = new HashMap<>();
   private Map<String, String> settingNameToCloneInMap = new HashMap<>();

   public SettingState(Settings settings) {
      if (settings != null) {
         for (Setting s : settings.getSetting()) {
            if (s.getItem() != null || s.getItem1() != null) {
               if (s.getRefFile() != null && s.getRefFile().startsWith("BdsLast")) {
                  this.settingNameToValuesMap.put(s.getItem1(), s.getLastValue1());
                  this.settingNameToCloneInMap.put(s.getItem1(), s.getCloneIn());
               } else {
                  if (s.getItem().equalsIgnoreCase("EasyLink") && s.getXaddr() == null) {
                     s.setXaddr("ES2K12");
                  }

                  if (s.getItem() != null && s.getItem().equalsIgnoreCase("HMHotelModeOnOffInstall")) {
                     s.setLastValue("1");
                  }

                  this.settingNameToValuesMap.put(s.getItem(), s.getLastValue());
                  this.settingNameToCloneInMap.put(s.getItem(), s.getCloneIn());
               }
            }
         }
      }
   }

   public SettingState(SettingChannelBean scb) {
      this(scb.getSetttings());
   }

   public static SettingState.BaseParamConverter getParamConverter(String platform) {
      String platformId = PlatformUtils.getPlatformId(platform);
      return PlatformUtils.getParamConverter(platformId);
   }

   public SettingState(HttpServletRequest request) {
      Enumeration<String> enumParam = request.getParameterNames();
      String platformName = null;
      Set<String> paramSet = new HashSet<>();

      while (enumParam.hasMoreElements()) {
         paramSet.add(enumParam.nextElement());
      }

      paramSet.addAll(Arrays.asList(SettingState.ParamConverter.checkBoxParams));

      for (String key : paramSet) {
         String value = request.getParameter(key);
         this.paramToValuesMap.put(key, value);
         HttpSession session = request.getSession();
         String platform = (String)session.getAttribute("platform");
         if (StringUtils.isNotBlank(platform)) {
            platformName = PlatformUtils.getPlatformId(platform);
         }

         SettingState.BaseParamConverter converter = getParamConverter(platformName);
         converter.handleValues(key, value, this.settingNameToValuesMap);
      }
   }

   public Map<String, String> getSettingToValuesMap() {
      return this.settingNameToValuesMap;
   }

   public Map<String, String> getSettingToCloneInMap() {
      return this.settingNameToCloneInMap;
   }

   public boolean settingEqual(Setting s, Setting setting) {
      boolean ret = false;
      if (s.getItem().equals(setting.getItem()) && s.getXaddr().equals(setting.getXaddr()) && s.getRefFile().equals(setting.getRefFile())) {
         ret = true;
      }

      return ret;
   }

   public abstract static class BaseParamConverter {
      protected Map<String, String> paramSettingMap = new HashMap<>();
      protected List<String> settingNames = null;
      protected List<String> paramNames = null;

      public String[] getSettingNames() {
         return this.settingNames.toArray(new String[0]);
      }

      public String[] getParamNames() {
         return this.paramNames.toArray(new String[0]);
      }

      public boolean hasSettingName(String item) {
         for (Entry<String, String> entry : this.paramSettingMap.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(item)) {
               return true;
            }
         }

         return false;
      }

      public void handleValues(String key, String value, Map<String, String> settingValuesMap) {
         if (null == value) {
            for (String s1 : SettingState.ParamConverter.checkBoxParams) {
               if (s1.equalsIgnoreCase(key)) {
                  value = "chfalse";
                  break;
               }
            }
         }

         if ("true".equalsIgnoreCase(value)) {
            value = "1";
         } else if ("false".equalsIgnoreCase(value)) {
            value = "0";
         }

         settingValuesMap.put(this.getSettingName(key), value);
      }

      public String getSettingName(String param) {
         if (param == null) {
            return null;
         } else {
            return this.paramSettingMap.containsKey(param) ? this.paramSettingMap.get(param) : null;
         }
      }

      protected BaseParamConverter(String platformId) {
         this.settingNames = new ArrayList<>();
         this.paramNames = new ArrayList<>();
         String jsonPath = this.getClass().getClassLoader().getResource("TVSettings/" + platformId + ".json").getFile();

         try {
            jsonPath = URLDecoder.decode(jsonPath, "utf-8");
            String contents = FileUtils.readFileToString(new File(jsonPath), StandardCharsets.UTF_8);
            JSONObject configsObj = new JSONObject(contents);

            for (Object key : configsObj.keySet()) {
               String value = configsObj.optString(key.toString());
               this.paramSettingMap.put(key.toString(), value);
               this.paramNames.add(key.toString());
               this.settingNames.add(value);
            }
         } catch (JSONException | IOException e) {
            SettingState.LOG.error(e.getMessage(), e);
         }
      }

      protected BaseParamConverter(String[] settingNames, String[] paramNames) {
         this.settingNames = new ArrayList<>();
         this.paramNames = new ArrayList<>();
         this.settingNames.addAll(Arrays.asList(settingNames));
         this.paramNames.addAll(Arrays.asList(paramNames));
         int counter = 0;

         for (String settingString : settingNames) {
            if (counter < paramNames.length) {
               this.paramSettingMap.put(settingString, paramNames[counter]);
               System.out.println(counter + ":" + settingString + "=" + paramNames[counter]);
            } else {
               System.out.println(counter + ":" + settingString + "=null");
            }

            counter++;
         }
      }
   }

   public static class ParamConverter extends SettingState.BaseParamConverter {
      private static final String[] checkBoxParams = new String[]{
         "src_watch_tv",
         "src_hdmi1",
         "src_hdmi2",
         "src_hdmi3",
         "src_hdmiside",
         "src_ext1",
         "src_ext2",
         "src_vga",
         "smart_power",
         "delayed_reboot",
         "auto_switch_off",
         "welcome_msg_timeout_on",
         "ss_indspeaker",
         "timeout_on",
         "timeout_on",
         "timeout_on",
         "cons_rem_lock",
         "cons_osd_disp",
         "cons_high_sec",
         "cons_wel_logo",
         "cons_brow_usb",
         "cons_auto_usb",
         "cons_easy_brin",
         "cons_easy_con",
         "cons_easy_app",
         "cons_enable_ci",
         "cons_enable_tele",
         "scrambled_osd",
         "source_setup",
         "cons_enable_mheg",
         "cons_enable_subti",
         "daylight_timeout_on",
         "freeze_on_noignal",
         "simply_share",
         "Display_Welcome_Msg"
      };
      private static String[] params = new String[]{
         "src_watch_tv",
         "src_hdmi1",
         "src_hdmi2",
         "src_hdmi3",
         "src_hdmiside",
         "src_ext1",
         "src_ext2",
         "src_vga",
         "fs_pbsmode",
         "smart_power",
         "fs_themetv",
         "cons_my_choice2k11",
         "daylight_timeout_on",
         "switch_on_vol",
         "cons_sxp_baud",
         "max_vol",
         "cons_sub_start",
         "cons_wel_logo",
         "ss_tvspeaker",
         "minbeds3",
         "switchon_source",
         "switchon_source1",
         "switchon_source2",
         "switchon_source3",
         "switchon_source4",
         "switchon_source_no",
         "welcome_msg_timeout_on",
         "welcome_line1",
         "welcome_line2",
         "message_time_out",
         "cons_loc_key",
         "cons_rem_lock",
         "cons_osd_disp",
         "cons_high_sec",
         "cons_brow_usb",
         "cons_auto_scart",
         "cons_easy_brin",
         "cons_easy_con",
         "cons_easy_app",
         "cons_auto_usb",
         " ",
         "cons_enable_ci",
         "cons_enable_tele",
         "cons_enable_mheg",
         "cons_enable_epg",
         "cons_enable_epg1",
         "cons_enable_subti",
         "power_on",
         "low_pow_stdby",
         "smart_power1",
         "auto_switch_off",
         "delayed_reboot",
         "cs_standby",
         "cs_on",
         "cd_dntime",
         "cs_dncountry",
         "cs_dnprogram",
         "cs_dnprogram",
         "cs_dnprogram",
         "cs_dnprogram",
         "cs_dnprogram",
         "minbeds4",
         "timeout_on",
         "fs_menulang",
         "fs_vsecmode",
         "fs_multirc",
         "cons_my_choice",
         "ss_indspeaker",
         "standby_delay_timeout",
         "joint_space",
         "freeze_on_noignal",
         "scrambled_osd",
         "simply_share",
         "simply_share1",
         "source_setup",
         "switch_On_feature",
         "ref_year",
         "ref_month",
         "ref_date",
         "ref_hour",
         "ref_minute",
         "ref_second",
         "net_tv",
         "hotelid",
         "profile",
         "smartui",
         "smartui_backup",
         "icon_label",
         "portal_url",
         "co_mid_attrmod2",
         "rfDownloadEnable",
         "rfAutoUpgradeEnable",
         "rfSoftwareType",
         "rfFrequency",
         "rfSymbolRate",
         "rfDecoder",
         "rfModultaion"
      };
      private static String[] settingName = new String[]{
         "HMSrcWatchTV",
         "HMSrcHDMI1",
         "HMSrcHDMI2",
         "HMSrcHDMI3",
         "HMSrcHDMISide",
         "HMSrcExtn1",
         "HMSrcExtn2",
         "HMSrcVga",
         "HMHotelModeOnOffInstall",
         "HMSmartPower",
         "HMThemeTv",
         "HMSmartPin",
         "TimersDaylightSaving",
         "HMOnVolume",
         "BaudRate",
         "HMOnMaxVolume",
         "InstSettingsSubtitle",
         "HmWelcomeLogo",
         "InstSettingsAmplifier",
         "HMDefMainSpkVol",
         "HMOnchannelSrc",
         "HMOnChannelOnePartNo",
         "HMOnChannelAnalogNo",
         "HMOnChannelDigit",
         "HMOnChannelTwoPartMajorNo",
         "HMOnChannelDigit",
         "HMDisplayMessage",
         "HMWelcomeMessageLine1Char19",
         "HMWelcomeMessageLine2Char0",
         "HMMessageTimeOut",
         "HMKeyboardLockStatus",
         "HMRCLockStatus",
         "HMOsdDisplay",
         "HMHighSecurityMode",
         "HMUsbBrowseAllowed",
         "HMPowerScart",
         "HmEasyLink",
         "HMCec",
         "HMCecAppMenu",
         "HMUSBBreakIn",
         null,
         "HMCICard",
         "HMTeletext",
         "HMMheg",
         "HMEpg",
         "OptEightDaysEpg",
         "HMSubtitles",
         "HMPowerOn",
         "RS232CtrlSpecialPowerMode",
         "HMSmartPower1",
         "LstStatAutPowerDownMode",
         "HMDelayedReboot",
         "HMLcdDisplaySby",
         "HMLcdDisplayOn",
         "AutoClockMode",
         "HMClockCountry",
         "HMClockChannelOnePartNo",
         "HMClockChannelAnalogNo",
         "HMClockChannelDigit",
         "HMClockChannelTwoPartMajorNo",
         "HMClockChannelType",
         "HMMenuTimeOffset",
         "HMLcdBuzzerVol",
         "HMPBSMenuLanguage",
         "HMVsecure",
         "HMMultiRC",
         "HMMyChoice",
         "HMIndepMainSpkMute",
         "HMStandByDelay",
         "HMJointSpace",
         "HMFreezeOnNoSignal",
         "HMScrambledProgramOsd",
         "HMSimplyShare",
         "HMSimplyShare1",
         "HMNativeSource",
         "HMSwitchonFeature",
         "RefYear",
         "RefMon",
         "RefDate",
         "RefHour",
         "RefMin",
         "RefSec",
         "HMNetTvSupport",
         "Hotelier",
         "AppConfig",
         "HMBrowserUI",
         "HMBrowserUIBackup",
         "ConfigPortalURL",
         "PortalURL",
         null,
         "HMRFDownloadEnable",
         "HMRFAutoUpgradeEnable",
         "HMRfdSoftwareType",
         "HMRfdFrequency",
         "HMRfdSymbolRate",
         "HMRfdDecoder",
         "HMRfdModultaion"
      };
      private static SettingState.ParamConverter SINGLE_INSTANCE = new SettingState.ParamConverter();

      private ParamConverter() {
         super(settingName, params);
      }

      public static SettingState.ParamConverter instance() {
         return SINGLE_INSTANCE;
      }

      @Override
      public void handleValues(String key, String value, Map<String, String> settingNameToValuesMap) {
         if (key.equalsIgnoreCase("scrambled_osd")) {
            if (value.equalsIgnoreCase("chfalse")) {
               settingNameToValuesMap.put("HMScrambledProgramOsd", "1");
            } else {
               settingNameToValuesMap.put("HMScrambledProgramOsd", "0");
            }
         } else if (key.equalsIgnoreCase("cons_enable_epg1")) {
            if (value.equalsIgnoreCase("0")) {
               settingNameToValuesMap.put("HMEpg", "0");
               settingNameToValuesMap.put("OptEightDaysEpg", "0");
            } else if (value.equalsIgnoreCase("1")) {
               settingNameToValuesMap.put("HMEpg", "1");
               settingNameToValuesMap.put("OptEightDaysEpg", "0");
            } else if (value.equalsIgnoreCase("2")) {
               settingNameToValuesMap.put("HMEpg", "1");
               settingNameToValuesMap.put("OptEightDaysEpg", "1");
            }
         } else if (key.equalsIgnoreCase("cons_enable_epg")) {
            if (value.equalsIgnoreCase("0")) {
               settingNameToValuesMap.put("HMEpg", "0");
               settingNameToValuesMap.put("HM8DayEpg", "0");
            } else if (value.equalsIgnoreCase("1")) {
               settingNameToValuesMap.put("HMEpg", "1");
               settingNameToValuesMap.put("HM8DayEpg", "0");
            } else if (value.equalsIgnoreCase("2")) {
               settingNameToValuesMap.put("HMEpg", "1");
               settingNameToValuesMap.put("HM8DayEpg", "1");
            }
         } else if (key.equalsIgnoreCase("welcome_line1")) {
            if (null != value) {
               try {
                  byte[] bytes = value.getBytes("US-ASCII");
                  int index = 0;

                  for (byte b : bytes) {
                     Integer ib = Integer.valueOf(b);
                     settingNameToValuesMap.put("HMWelcomeMessageLine1Char" + index, ib.toString());
                     index++;
                  }

                  while (index < 20) {
                     settingNameToValuesMap.put("HMWelcomeMessageLine1Char" + index, "0");
                     index++;
                  }
               } catch (UnsupportedEncodingException e) {
                  SettingState.LOG.error("" + e.getMessage(), e);
               }
            }
         } else if (key.equalsIgnoreCase("welcome_line2") && null != value) {
            try {
               byte[] bytes = value.getBytes("US-ASCII");
               int index = 0;

               for (byte b : bytes) {
                  Integer ib = Integer.valueOf(b);
                  settingNameToValuesMap.put("HMWelcomeMessageLine2Char" + index, ib.toString());
                  index++;
               }

               while (index < 20) {
                  settingNameToValuesMap.put("HMWelcomeMessageLine2Char" + index, "0");
                  index++;
               }
            } catch (UnsupportedEncodingException e) {
               SettingState.LOG.error("" + e.getMessage(), e);
            }
         }

         super.handleValues(key, value, settingNameToValuesMap);
      }
   }

   public static class ParamConverterFor2K14MS extends SettingState.BaseParamConverter {
      private static SettingState.ParamConverterFor2K14MS SINGLE_INSTANCE = new SettingState.ParamConverterFor2K14MS();

      private ParamConverterFor2K14MS() {
         super("2K14MS");
      }

      public static SettingState.ParamConverterFor2K14MS instance() {
         return SINGLE_INSTANCE;
      }

      @Override
      public void handleValues(String key, String value, Map<String, String> settingValuesMap) {
         if ("Oncredit".equalsIgnoreCase(value)) {
            value = "On(credit)";
         }

         super.handleValues(key, value, settingValuesMap);
      }
   }

   public static class ParamConverterFor2K14MSForTpn142 extends SettingState.BaseParamConverter {
      private static SettingState.ParamConverterFor2K14MSForTpn142 SINGLE_INSTANCE = new SettingState.ParamConverterFor2K14MSForTpn142();

      private ParamConverterFor2K14MSForTpn142() {
         super("2K14ES");
      }

      public static SettingState.ParamConverterFor2K14MSForTpn142 instance() {
         return SINGLE_INSTANCE;
      }

      @Override
      public void handleValues(String key, String value, Map<String, String> settingValuesMap) {
         if ("Oncredit".equalsIgnoreCase(value)) {
            value = "On(credit)";
         }

         super.handleValues(key, value, settingValuesMap);
      }
   }

   public static class ParamConverterFor2K15MS extends SettingState.BaseParamConverter {
      private static SettingState.ParamConverterFor2K15MS SINGLE_INSTANCE = new SettingState.ParamConverterFor2K15MS();

      private ParamConverterFor2K15MS() {
         super("2K16MS");
      }

      public static SettingState.ParamConverterFor2K15MS instance() {
         return SINGLE_INSTANCE;
      }

      @Override
      public void handleValues(String key, String value, Map<String, String> settingValuesMap) {
         if ("Stereo uncompressed".equalsIgnoreCase(value)) {
            value = "Stereo (uncompressed)";
         } else if ("Deive".equalsIgnoreCase(value)) {
            value = "Descriptive";
         } else if ("Chinese Cantonese".equalsIgnoreCase(value)) {
            value = "Chinese (Cantonese)";
         } else if ("Chinese Mandarin".equalsIgnoreCase(value)) {
            value = "Chinese (Mandarin)";
         } else if ("Scottish Gaelic Gàidhlig".equalsIgnoreCase(value)) {
            value = "Scottish Gaelic (Gàidhlig)";
         } else if ("Irish Gaelic Gaeilge".equalsIgnoreCase(value)) {
            value = "Irish Gaelic (Gaeilge)";
         }

         super.handleValues(key, value, settingValuesMap);
      }
   }

   public static class ParamConverterFor2K16ES extends SettingState.BaseParamConverter {
      private static SettingState.ParamConverterFor2K16ES SINGLE_INSTANCE = new SettingState.ParamConverterFor2K16ES();

      private ParamConverterFor2K16ES() {
         super("2K16ES");
      }

      public static SettingState.ParamConverterFor2K16ES instance() {
         return SINGLE_INSTANCE;
      }

      @Override
      public void handleValues(String key, String value, Map<String, String> settingValuesMap) {
         if ("Deive".equalsIgnoreCase(value)) {
            value = "Descriptive";
         }

         super.handleValues(key, value, settingValuesMap);
      }
   }

   public static class ParamConverterFor2K19ES extends SettingState.BaseParamConverter {
      private static SettingState.ParamConverterFor2K19ES SINGLE_INSTANCE = new SettingState.ParamConverterFor2K19ES();

      private ParamConverterFor2K19ES() {
         super("2K19ES");
      }

      public static SettingState.ParamConverterFor2K19ES instance() {
         return SINGLE_INSTANCE;
      }
   }

   public static class ParamConverterFor2K19MS extends SettingState.BaseParamConverter {
      private static SettingState.ParamConverterFor2K19MS SINGLE_INSTANCE = new SettingState.ParamConverterFor2K19MS();

      private ParamConverterFor2K19MS() {
         super("2K19MS");
      }

      public static SettingState.ParamConverterFor2K19MS instance() {
         return SINGLE_INSTANCE;
      }

      @Override
      public void handleValues(String key, String value, Map<String, String> settingValuesMap) {
         if (key.equalsIgnoreCase("Select_Dashboard") && value.equalsIgnoreCase("Custom Dashboard Browser based")) {
            value = "Custom Dashboard (Browser based)";
         }

         super.handleValues(key, value, settingValuesMap);
      }
   }

   public static class ParamConverterFor2K19NAFTA extends SettingState.BaseParamConverter {
      private static SettingState.ParamConverterFor2K19NAFTA instance = new SettingState.ParamConverterFor2K19NAFTA();

      private ParamConverterFor2K19NAFTA() {
         super("2K19NAFTA");
      }

      public static SettingState.ParamConverterFor2K19NAFTA instance() {
         return instance;
      }
   }

   public static class ParamConverterFor2K19PS extends SettingState.BaseParamConverter {
      private static SettingState.ParamConverterFor2K19PS SINGLE_INSTANCE = new SettingState.ParamConverterFor2K19PS();

      private ParamConverterFor2K19PS() {
         super("2K19PS");
      }

      public static SettingState.ParamConverterFor2K19PS instance() {
         return SINGLE_INSTANCE;
      }

      @Override
      public void handleValues(String key, String value, Map<String, String> settingValuesMap) {
         if (key.equalsIgnoreCase("Select_Dashboard") && value.equalsIgnoreCase("Custom Dashboard Browser based")) {
            value = "Custom Dashboard (Browser based)";
         }

         super.handleValues(key, value, settingValuesMap);
      }
   }
}
