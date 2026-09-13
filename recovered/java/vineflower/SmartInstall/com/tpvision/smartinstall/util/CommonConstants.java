package com.tpvision.smartinstall.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CommonConstants {
   public static final List<Integer> TV_WIXP_HTTP_PORTS = Collections.unmodifiableList(Arrays.asList(9079, 9080));
   public static final List<Integer> TV_WIXP_HTTPS_PORTS = Collections.unmodifiableList(Arrays.asList(9443));
   public static final List<Integer> TV_WIXP_ALL_PORTS = Collections.unmodifiableList(
      Stream.of(TV_WIXP_HTTPS_PORTS, TV_WIXP_HTTP_PORTS).flatMap(Collection::stream).collect(Collectors.toList())
   );
   public static final String HTTP = "http://";
   public static final String HTTPS = "https://";
   public static final String HTTPS_SCHEMA = "https";
   public static final String PORT_DIVIDER = ":";
   public static final String CMND_SMARTINSTALL = "/SmartInstall";
   public static final String CMND_WEBSERVICES = "/webservices.jsp";
   public static final String CMND_CERT_CA = "/Cert/ca.p12";
   public static final int CMND_HTTPS_PORT = Configs.getProperty("server.httpsport", 8443);
   public static final int CMND_HTTP_PORT = Configs.getProperty("server.port", 8080);
   public static final String CMS_URL = Configs.getProperty("cms.url", "http://localhost:8082/SmartCMS");
   public static final String MGATE_URL = Configs.getProperty("mgate.url", "http://localhost:10088/");
   public static final String POWER_REBOOT = "REBOOT";
   public static final String POWER_ON = "On";
   public static final String POWER_OFFLINE = "offline";
   public static final String POWER_STANDBY = "Standby";
   public static final String TV_WIXP_FOLDER = "/WIXP";
   public static final String SI_SERVER_VER = "7.0.1";
   public static final int MAX_STRING_LENGTH = 32;
   public static final String GATEWAY_PROCESS_NAME = "Gateway.exe";
   public static final String ROOT_PATH = Utils.getRootDir();
   public static final String PHILIPS_PATH = Configs.getProperty("philips.path", ROOT_PATH + "/Philips/");
   public static final String APACHE_WD = Utils.getRootDir() + "Apache24/";
   public static final String OPENSSL_WD = APACHE_WD + "bin/";
   public static final String KEYTOOL_WD = PHILIPS_PATH + "/SIServer/utils/keytool/";
   public static final String CERT_WD = PHILIPS_PATH + "/Cert/";
   public static String FULL_SI_SERVER_VER = "";
   public static String TOMCAT_WD = "";
   public static String servletContextPath = "";
   public static final String CONFIG_BUILD_ROOT = PHILIPS_PATH + "/HotelTV";
   public static final String CONFIG_WORK_ROOT = PHILIPS_PATH + "/HotelTV/PSG";
   public static final String CONFIG_FILE = PHILIPS_PATH + "/SIServer/PSG/Configuration.xml";
   public static final String SISERVER_UPG_DIR = PHILIPS_PATH + "/SIServer/UPG";
   public static final String LAST_SELECTED_CLONE_FILE = PHILIPS_PATH + "/SIServer/conf/last-selected-clone.txt";
   public static final String ZIP_TEMP_DIR = PHILIPS_PATH + "/SIServer/zip/temp";
   public static final String FILE_BEAT_INSTALL_DIR = PHILIPS_PATH + "filebeat/";
   public static final String MGATEWAY_EXE_PATH = PHILIPS_PATH + "/MGate/startMGate.bat";
   public static final String MGATEWAY_WORK_PATH = PHILIPS_PATH + "/MGate/";
   public static final String PROFILE_RF_DIR = PHILIPS_PATH + "/SIServer/Profile/RF/";
   public static final String SISERVER_CONF_DIR = PHILIPS_PATH + "/SIServer/conf/";
   public static final String SISERVER_UPLOAD_DIR = PHILIPS_PATH + "/SIServer/upload/";
   public static final String SISERVER_PSG_CATALOG_FILE = PHILIPS_PATH + "/SIServer/PSG/Catalog.xml";
   public static final String C_DIR = PHILIPS_PATH;
   public static final String CONFIG_FILE_LOCATION = PHILIPS_PATH + "/SIServer/conf/config.xml";
   public static final String LAST_CONFIG_FILE_LOCATION = PHILIPS_PATH + "/SIServer/conf/last-config.json";
   public static final String LAST_IP_CONFIG_LOCATION = PHILIPS_PATH + "/SIServer/conf/last-ip-config.json";
   public static final String RESOURCE_LOCATION = PHILIPS_PATH + "/SIServer/resource/";
   public static final String CLONE_ASSEMBLY_LOCATION = PHILIPS_PATH + "/SIServer/assembly/";
   public static final String CLONE_PROCESS_LOCATION = PHILIPS_PATH + "/SIServer/process/admin/";
   public static final String UPLOADED_UPG_LOCATION = PHILIPS_PATH + "/SIServer/UPG/";
   public static final String UPLOADED_UPG_LOCATION_TEMPXML = PHILIPS_PATH + "/SIServer/UPG/TEMPXML/";
   public static final String UPLOADED_AVSTREAM_LOCATION = PHILIPS_PATH + "/SIServer/AVStream/";
   public static final String RF_PLAY_BACK_INPUT_LOCATION = PHILIPS_PATH + "/SIServer/playback/input/";
   public static final String RF_PLAY_BACK_OUTPUT_LOCATION = PHILIPS_PATH + "/SIServer/playback/output/";
   public static final String RF_PLAY_BACK_EXE_NAME = PHILIPS_PATH + "/SIServer/utils/PSG/Gateway.exe";
   public static final String RF_PLAY_BACK_EXE_WD = PHILIPS_PATH + "/SIServer/utils/PSG/";
   public static final String RF_PLAY_BACK_OUTPUT_PATH = PHILIPS_PATH + "/HotelTV/PSG/OutputFiles";
   public static final String RF_FILE_OUTPUT_PATH = PHILIPS_PATH + "/MGate/output/";
   public static final List<String> SUPPORTED_LOGO_FORMAT = Collections.unmodifiableList(Arrays.asList("jpg", "png", "jpeg"));
   public static final String UI_LOGO_IMG_LOCATION = PHILIPS_PATH + "/SIServer/img/uilogo/";
   public static final String UI_AUDIO_LOCATION = PHILIPS_PATH + "/SIServer/audio/background/";
   public static final String STATIC_IMAGES_UILOGOTHUMB = "/static/images/uiCustomizations/logo/";
   public static final String RECEPTION_LOGO_IMG_LOCATION = PHILIPS_PATH + "/SIServer/img/template/";
   public static final String USER_ZIP_TEMP_LOCATION = PHILIPS_PATH + "/SIServer/zip/";
   public static final String DOWNLOAD_LOCATION = PHILIPS_PATH + "/SIServer/download/";
   public static final String HOTEL_INFO_IMG_LOCATION = PHILIPS_PATH + "/SIServer/img/hotel/";
   public static final String HOTEL_INFO_THUMB_IMG_LOCATION = PHILIPS_PATH + "/SIServer/img/hotel/thumb/";
   public static final String WELCOME_LOGO_IMG_LOCATION = PHILIPS_PATH + "/SIServer/img/welcome/";
   public static final String WELCOME_LOGO_THUMB_IMG_LOCATION = PHILIPS_PATH + "/SIServer/img/welcome/thumb/";
   public static final String STATIC_IMAGES_WELCOMETHUMB = "/static/images/welcomethumb/";
   public static final String THEME_IMG_LOCATION = PHILIPS_PATH + "/SIServer/img/theme/";
   public static final String HOTEL_INFO_ES_LOCATION = PHILIPS_PATH + "/SIServer/hotelinfoES/";
   public static final String HOTEL_INFO_ES_THUMB_LOCATION = PHILIPS_PATH + "/SIServer/hotelinfoES/thumb";
   public static final String CLONE_BANNER_EMERGENCY_IMAGE_LOATION = PHILIPS_PATH + "/SIServer/img/banner/emergency/";
   public static final String CLONE_BANNER_COMMERCIAL_CONTENT_IMAGE_LOATION = PHILIPS_PATH + "/SIServer/img/banner/commercial/";
   public static final String CLONE_BANNER_HTML_CODE_TEMPLATE = PHILIPS_PATH + "/SIServer/resource/banners/";
   public static final String CAST_SERVER_LANDING_PAGE_BACKGROUND_IMAGE_LOATION = PHILIPS_PATH + "/SIServer/img/cast/background/";
   public static final String REMOTE_DIAGNOSTIC_LOGGING_SAVE_PATH = PHILIPS_PATH + "/SIServer/Upload/Logs/";
   public static final String THEME = "theme";
   public static final String HOTEL = "hotel";
   public static final String WELCOME = "welcome";
   public static final String SMARTINFOES = "smartinfoes";
   public static final String TEMPLATES = "templates";
   public static final String LOCATION_MANAGER_STORE = PHILIPS_PATH + "/SIServer/conf/location.txt";
   public static final String MS2K14_UPG_CREATOR_UTIL_COMMAND = "cmd /c HTV_DWPack_1401.exe";
   public static final String MS2K14_UPG_CREATOR_LOCATION_INPUT = PHILIPS_PATH + "/SIServer/utils/UPG_Create_2K14/";
   public static final String MS2K14_XML_CREATOR_CATALOG_INPUT = "0144\\0000\\1404\\";
   public static final String MS2K14_UPG_CREATOR_LOCATION_OUTPUT = PHILIPS_PATH + "/HotelTV/2K14_MediaSuite/0144/0000/1404/";
   public static final String RF_PLAY_BACK_EXE_INPUT_LOC_MS_2K14_INPUT = PHILIPS_PATH + "/HotelTV/2K14_MediaSuite/0144/0000/1404/FEEF/";
   public static final String MS2K15_XML_CREATOR_CATALOG_INPUT = "0218\\0122\\00F0\\";
   public static final String MS2K15_UPG_CREATOR_LOCATION_OUTPUT = PHILIPS_PATH + "/HotelTV/2K15_MediaSuite/0218/0122/00F0/";
   public static final String RF_PLAY_BACK_EXE_INPUT_LOC_MS_2K15_INPUT = PHILIPS_PATH + "/HotelTV/2K15_MediaSuite/0218/0122/00F0/FEEF/";
   public static final String MS2K15_UPG_CREATOR_LOCATION_INPUT = PHILIPS_PATH + "/SIServer/utils/UPG_Create_2K15/";
   public static final String SS2K16_XML_CREATOR_CATALOG_INPUT = "021b\\0122\\00F0\\";
   public static final String SS2K16_UPG_CREATOR_LOCATION_OUTPUT = PHILIPS_PATH + "/HotelTV/2K15_MediaSuite/021b/0122/00F0/";
   public static final String RF_PLAY_BACK_EXE_INPUT_LOC_SS_2K16_INPUT = PHILIPS_PATH + "/HotelTV/2K15_MediaSuite/021b/0122/00F0/FEEF/";
   public static final String ES2K16_XML_CREATOR_CATALOG_INPUT = "1554\\0000\\1564\\";
   public static final String ES2K16_UPG_CREATOR_LOCATION_OUTPUT = PHILIPS_PATH + "/HotelTV/2K16_EasySuite/1554/0000/1564/";
   public static final String RF_PLAY_BACK_EXE_INPUT_LOC_ES_2K16_INPUT = PHILIPS_PATH + "/HotelTV/2K16_EasySuite/1554/0000/1564/FEEF/";
   public static final String ES2K16_UPG_CREATOR_LOCATION_INPUT = PHILIPS_PATH + "/SIServer/utils/UPG_Create_2K16/";
   public static final String ES2K14_XML_CREATOR_CATALOG_INPUT = "0148\\0000\\1408\\";
   public static final String ES2K14_UPG_CREATOR_LOCATION_OUTPUT = PHILIPS_PATH + "/HotelTV/2K14_EasySuite/0148/0000/1408/";
   public static final String RF_PLAY_BACK_EXE_INPUT_LOC_ES_2K14_INPUT = PHILIPS_PATH + "/HotelTV/2K14_EasySuite/0148/0000/1408/FEEF/";
   public static final String ZIP_7 = PHILIPS_PATH + "/SIServer/utils/7-Zip/";
   public static final String NAME_2K14_MS = "2K14/2K15-MS";
   public static final String NAME_2K14_ES = "2K14/2K15-ES";
   public static final String NAME_2K16_MS = "2016 MS";
   public static final String NAME_2K16_SS = "2016 SS";
   public static final String NAME_2K16_ES = "2016 ES";
   public static final String NAME_2K19_MS = "2019 MS";
   public static final String NAME_2K19_PS = "2019 PS";
   public static final String NAME_2K19_ES = "2019 ES";
   public static final String NAME_2K19_NAFTA = "2019 NAFTA";
   public static final String NAME_T32 = "TPM215HEA";
   public static final String NAME_T32_NAFTA = "TPM215HKN";
   public static final String NAME_TPM242HWW = "TPM242HWW";
   public static final String PRO_2K14_ES = "ES2K14";
   public static final String PRO_2K14_MS = "MS2K14";
   public static final String PRO_2K16_MS = "MS2K16";
   public static final String PRO_2K16_SS = "SS2K16";
   public static final String PRO_2K16_ES = "ES2K16";
   public static final String PRO_2K19_MS = "MS2K19";
   public static final String PRO_2K19_PS = "PS2K19";
   public static final String PRO_2K19_ES = "ES2K19";
   public static final String PRO_2K19_NAFTA = "NAFTA2K19";
   public static final String PRO_T32 = "T32";
   public static final String PRO_T32_NAFTA = "NAFTAT32";
   public static final String PRO_TPM242HWW = "TPM242HWW";
   public static final String ID_2K14_MS = "TPN141HE_CloneData";
   public static final String ID_2K14_ES = "TPN142HE_CloneData";
   public static final String ID_2K16_MS = "TPM1532HE_CloneData";
   public static final String ID_2K16_SS = "TPM1531HE_CloneData";
   public static final String ID_2K16_ES = "TPN161HE_CloneData";
   public static final String ID_2K19_MS = "TPM181HE_CloneData";
   public static final String ID_2K19_PS = "TPM187HE_CloneData";
   public static final String ID_2K19_ES = "TPS191HE_CloneData";
   public static final String ID_2K19_NAFTA = "TPM191HN_CloneData";
   public static final String ID_T32 = "TPM215HEA_CloneData";
   public static final String ID_T32_NAFTA = "TPM215HKN_CloneData";
   public static final String ID_TPM242HWW = "TPM242HWW_CloneData";
   public static final String CHANNEL_PACKAGE_LOGO_PATH_FORMAT = CLONE_PROCESS_LOCATION + "ChannelPackages/%d/ChannelList/ChannelLogos/";
   public static final String CHANNEL_PACKAGE_THEME_TV_ICON_PATH_FORMAT = CLONE_PROCESS_LOCATION + "ChannelPackages/%d/ChannelList/ThemeIcons/";
   public static final String MESSAGE_ICON_PATH_FORMAT = CLONE_PROCESS_LOCATION + "MessageIcons/";
   public static final String LOG_LOCATION = PHILIPS_PATH + "/SIServer/log/";
   public static final String MESSAGE_ICON_CACHE_PATH = "/static/images/messageicons/";
   public static final String PMS_DEFAULT_ICON_PATH = "files/image/pms/";
   public static final String[] cloneItems = new String[]{
      "TVSettings",
      "ChannelList",
      "WelcomeLogo",
      "SmartInfoShow",
      "SmartInfoBrowser",
      "AndroidApps",
      "RoomSpecificSettings",
      "LocalCustomDashboard",
      "Script",
      "MediaChannels",
      "WeatherForecast",
      "HTVCfg",
      "Banner",
      "PMS",
      "AndroidAppsData",
      "ProfessionalApps",
      "ProfessionalAppsData",
      "Schedules",
      "MyChoice",
      "Vsecure"
   };
   public static final String FAILED_STATUS = "{\"status\":\"fail\"}";
   public static final String SUCCESS_STATUS = "{\"status\":\"success\"}";
   public static final String TEXT_FAIL = "fail";
   public static final String TEXT_SUCCESS = "success";
   public static final String TEXT_HTML = "text/html;charset=UTF-8";
   public static final String TEXT_JSON = "text/json;charset=UTF-8";
   public static final String SUPER_RESOLUTION = "TV Settings.Picture.Advanced.Sharpness.Super Resolution";
   public static final String ULTRA_RESOLUTION = "TV Settings.Picture.Advanced.Sharpness.Ultra Resolution";
   public static final String ULTRA_8K_RESOLUTION = "TV Settings.Picture.Advanced.Sharpness.8K Ultra resolution";
   public static final String ADVANCED_LOGGING_FREQUENCY = "Advanced.Diagnostic Logging.Frequency";

   private CommonConstants() {
   }

   public enum CloneItemType {
      AndroidApps,
      ChannelList,
      LocalCustomDashboard,
      MediaChannels,
      RoomSpecificSettings,
      SmartInfoBrowser,
      SmartInfoShow,
      SmartInfoImages,
      SmartInfoPages,
      CustomDashboardFallback,
      MiscSettings,
      SystemUIBackup,
      CombineMedia,
      VSecureKey,
      TVSettings,
      Vsecure,
      WeatherForecast,
      WelcomeLogo,
      Banner,
      HTVCfg,
      MyChoice,
      Schedules,
      ProfessionalAppsData,
      AndroidAppsData,
      ProfessionalApps,
      Script,
      VirginModeSettings,
      ProfMenuPinCode,
      CustomDashboardLauncher,
      LocalMedia,
      DataDump,
      PMS,
      Clone,
      Firmware,
      UiCustomizations,
      UnKnownItem;
   }
}
