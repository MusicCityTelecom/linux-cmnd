package com.tpvision.smartinstall.androidapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tpvision.smartinstall.dao.core.AppPackage;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.ProcessUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.TpvFileUtils;
import com.tpvision.smartinstall.util.ZipCommonUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AndroidAppHelper {
   private static final String LOCAL = "local";
   private static final String[] CATEGORY = new String[]{
      "Children", "Entertainment", "Financial", "Games", "Lifestyle", "Local Info", "Music", "News", "Other", "Sports", "Technology", "Travel", "Weather"
   };
   private static final String[] COUNTRY = new String[]{
      "AR",
      "AT",
      "AU",
      "BE",
      "BG",
      "BR",
      "CH",
      "CZ",
      "DE",
      "DK",
      "EE",
      "ES",
      "FI",
      "FR",
      "GB",
      "GR",
      "HR",
      "HU",
      "IE",
      "IT",
      "KZ",
      "LT",
      "LU",
      "LV",
      "NL",
      "NO",
      "NZ",
      "PL",
      "PT",
      "RO",
      "RS",
      "RU",
      "SE",
      "SI",
      "SK",
      "TR",
      "UA",
      "US",
      "ZZ"
   };
   private static final String KEYSTORE_PATH = CommonConstants.SISERVER_CONF_DIR + "/KeyStore/";
   private static final String KEY_ALIAS_NAME = "alias_aab_sign_key";
   public static final boolean AAB_EXTRACT_SINGLE_APK = true;
   public static final String SMARTINFO_CATEGORY = "com.philips.professionaldisplaysolutions.jedi.intent.category.SMART_INFO";
   public static final String CDB_CATEGORY = "com.philips.professionaldisplaysolutions.jedi.intent.category.PRO_TV_DASHBOARD";
   public static final String EPG_CATEGORY = "com.philips.professionaldisplaysolutions.jedi.intent.category.PRO_TV_EPG";
   public static final String CONFIG_CATEGORY = "com.philips.professionaldisplaysolutions.jedi.intent.category.PRO_TV_CONFIG";
   private static final String[] PHILIPS_APPS_CATEGORIES = new String[]{
      "com.philips.professionaldisplaysolutions.jedi.intent.category.SMART_INFO",
      "com.philips.professionaldisplaysolutions.jedi.intent.category.PRO_TV_DASHBOARD",
      "com.philips.professionaldisplaysolutions.jedi.intent.category.PRO_TV_EPG",
      "com.philips.professionaldisplaysolutions.jedi.intent.category.PRO_TV_CONFIG"
   };
   private static final Map<String, String> preInstalledPlayStoreApps = new HashMap<>();
   private static final Logger LOG = LoggerFactory.getLogger(AndroidAppHelper.class);
   private int appPackageId;
   private boolean isValid = false;
   private List<AndroidApplications.AndroidApp> apps;
   private AppPackage appPackage;
   private String errorMessage;
   private AndroidApplications androidApplications;
   private String ksPass;
   private String keyPass;
   private String sname;
   private Setting setting;
   private String professionalAppsPath;
   private AndroidApplications professionalApplications;

   public static AndroidAppHelper getHelper(int cloneId) {
      Setting setting = JpaManager.getSettingManager().loadByKey(cloneId);
      AndroidAppHelper helper = new AndroidAppHelper();
      helper.setting = setting;
      helper.loadData();
      return helper;
   }

   public AndroidAppHelper(String sname) {
      this.sname = sname;
      this.loadData();
   }

   public AndroidAppHelper(int appPackageId) {
      this.appPackageId = appPackageId;
      this.loadData();
   }

   public AndroidAppHelper() {
   }

   public List<AndroidApplications.AndroidApp> getApps() {
      return this.apps;
   }

   private void loadData() {
      try {
         if (this.sname != null) {
            List<Setting> settings = JpaManager.getSettingManager().findSettingsByName(this.sname);
            if (settings.isEmpty()) {
               LOG.error("clone package not found:{}", this.sname);
               throw new IOException("settings not found:" + this.sname);
            }

            this.setting = settings.get(0);
         }

         if (this.setting != null) {
            this.appPackageId = this.setting.getAppPackageId();
            this.professionalAppsPath = CloneItemUtils.getCloneItemPath(this.setting, CommonConstants.CloneItemType.ProfessionalApps.name());
            if (this.professionalAppsPath != null) {
               this.loadProfessionallApps();
            }
         }

         if (this.appPackageId > 0) {
            this.appPackage = JpaManager.getAppPackageManager().loadByKey(this.appPackageId);
            if (this.appPackage == null) {
               throw new SQLException("Invalid App package id:" + this.appPackageId);
            }

            String appDetails = this.appPackage.getValue();
            if (appDetails != null && !appDetails.isEmpty()) {
               this.androidApplications = new Gson().fromJson(appDetails, AndroidApplications.class);
               this.apps = this.androidApplications.getClonePackages();
               this.checkDuplicatedPositions(this.apps);
            } else {
               this.androidApplications = new AndroidApplications();
               this.apps = new ArrayList<>();
            }
         }

         this.isValid = true;
      } catch (SQLException | IOException e) {
         LOG.error(e.getMessage(), e);
         this.errorMessage = e.getMessage();
      }
   }

   private void checkDuplicatedPositions(List<AndroidApplications.AndroidApp> apps) throws IOException {
      List<String> positions = new ArrayList<>();
      boolean duplicated = false;

      for (AndroidApplications.AndroidApp app : apps) {
         if (positions.contains(app.getPackagePosition())) {
            LOG.info("found duplicated position:{},{}", app.getPackageName(), app.getPackagePosition());
            app.setPackagePosition(String.valueOf(apps.size()));
            duplicated = true;
         } else {
            positions.add(app.getPackagePosition());
         }
      }

      if (duplicated) {
         LOG.info("apps poisition duplicated, reset");
         this.save();
      }
   }

   private void loadProfessionallApps() throws IOException {
      String dataJson = this.professionalAppsPath + "/ProfessionalAppsInfo.json";
      File fileJson = new File(dataJson);
      if (!fileJson.exists()) {
         updateProfessionalAppsInfo(this.professionalAppsPath);
      }

      String jsonContent = FileUtils.readFileToString(fileJson, StandardCharsets.UTF_8);
      this.professionalApplications = new Gson().fromJson(jsonContent, AndroidApplications.class);
   }

   private void checkValid() throws IOException {
      if (!this.isValid) {
         throw new IOException(this.errorMessage);
      }
   }

   private void save() throws IOException {
      Collections.sort(this.apps, (arg0, arg1) -> {
         int p0 = Integer.parseInt(arg0.getPackagePosition());
         int p1 = Integer.parseInt(arg1.getPackagePosition());
         return p0 - p1;
      });
      this.androidApplications.setClonePackages(this.apps);
      this.androidApplications.setAvailablePackages("" + this.apps.size());
      String jsonAppDetails = new Gson().toJson(this.androidApplications);
      this.appPackage.setValue(jsonAppDetails);
      this.appPackage.setLastEdit(TpvDateUtils.getCurrentIndentifierFormatTime());
      this.appPackage.setSize(this.getAppSize());
      this.appPackage.setNumber(this.apps.size());
      JpaManager.getAppPackageManager().save(this.appPackage);
      this.updateAndroidAppsMetaDataJson(jsonAppDetails);
      this.updateIdentifier();
   }

   private String getAppSize() {
      String directoryStr = this.getAndroidAppPath();
      if (!new File(directoryStr).exists()) {
         return "0 KB";
      }

      File file = new File(directoryStr);
      long size = FileUtils.sizeOfDirectory(file);
      return FileUtils.byteCountToDisplaySize(size);
   }

   public String getAndroidAppPath() {
      return CloneItemUtils.getAppPackageDataPath(this.appPackageId);
   }

   public List<String> getAppNames(String category) {
      return this.getAppNames(category, true);
   }

   public List<String> getAppPackageNames(String category) {
      return this.getAppPackageNames(category, true);
   }

   public List<String> getAppNames(String category, boolean isFilterPhilipseAppWhenCategoryNull) {
      List<String> appList = new ArrayList<>();
      appList.addAll(this.getAndroidAppNames(category, isFilterPhilipseAppWhenCategoryNull));
      appList.addAll(this.getProfessionalAppNames(category, isFilterPhilipseAppWhenCategoryNull));
      return appList;
   }

   public List<String> getAppPackageNames(String category, boolean isFilterPhilipseAppWhenCategoryNull) {
      List<String> appList = new ArrayList<>();
      appList.addAll(this.getAndroidAppPackageNames(category, isFilterPhilipseAppWhenCategoryNull));
      appList.addAll(this.getProfessionalAppPackageNames(category, isFilterPhilipseAppWhenCategoryNull));
      return appList;
   }

   private List<String> getAndroidAppNames(String category, boolean isFilterPhilipseAppWhenCategoryNull) {
      List<String> appList = new ArrayList<>();
      if (this.androidApplications == null) {
         return appList;
      }

      boolean categorySenstive = category != null || isFilterPhilipseAppWhenCategoryNull;

      for (AndroidApplications.AndroidApp app : this.androidApplications.getClonePackages()) {
         if (categorySenstive && "local".equalsIgnoreCase(app.getPackageType())) {
            try (ApkFile apk = new ApkFile(this.getAndroidAppPath() + app.getPackageURI())) {
               String manifestXml = apk.getManifestXml();
               List<String> categoryList = getCategoriesByManifestXml(manifestXml);
               app.setPackageCategory(categoryList.toArray(new String[0]));
            } catch (Exception var21) {
            }
         }

         boolean isIncludeThisApp = false;
         if (category != null) {
            isIncludeThisApp = app.containsCategory(category);
         } else if (!isFilterPhilipseAppWhenCategoryNull) {
            isIncludeThisApp = true;
         } else {
            isIncludeThisApp = !isPhilipsApps(app);
         }

         if (isIncludeThisApp) {
            String labelName = this.getAppNameFromAndroidApp(app);
            appList.add(labelName);
         }
      }

      return appList;
   }

   private List<String> getAndroidAppPackageNames(String category, boolean isFilterPhilipseAppWhenCategoryNull) {
      List<String> appList = new ArrayList<>();
      if (this.androidApplications == null) {
         return appList;
      }

      boolean categorySenstive = category != null || isFilterPhilipseAppWhenCategoryNull;

      for (AndroidApplications.AndroidApp app : this.androidApplications.getClonePackages()) {
         if (categorySenstive && "local".equalsIgnoreCase(app.getPackageType())) {
            try (ApkFile apk = new ApkFile(this.getAndroidAppPath() + app.getPackageURI())) {
               String manifestXml = apk.getManifestXml();
               List<String> categoryList = getCategoriesByManifestXml(manifestXml);
               app.setPackageCategory(categoryList.toArray(new String[0]));
            } catch (Exception var21) {
            }
         }

         boolean isIncludeThisApp = false;
         if (category != null) {
            isIncludeThisApp = app.containsCategory(category);
         } else if (!isFilterPhilipseAppWhenCategoryNull) {
            isIncludeThisApp = true;
         } else {
            isIncludeThisApp = !isPhilipsApps(app);
         }

         if (isIncludeThisApp) {
            String packageName = null;
            String filePath = this.getAndroidAppPath() + app.getPackageURI();
            if (new File(filePath).exists()) {
               packageName = this.getApkPackageName(filePath);
            }

            if (StringUtils.isBlank(packageName)) {
               packageName = app.getPackageName();
            }

            appList.add(packageName);
         }
      }

      return appList;
   }

   private String getAppNameFromAndroidApp(AndroidApplications.AndroidApp app) {
      if (StringUtils.isNotBlank(app.getApplicationName())) {
         return app.getApplicationName();
      }

      if (preInstalledPlayStoreApps.containsKey(app.getPackageName())) {
         return preInstalledPlayStoreApps.get(app.getPackageName());
      }

      String apkFilePath = this.getAndroidAppPath() + app.getPackageURI();
      if (new File(apkFilePath).exists()) {
         String labelName = this.getApkLabel(apkFilePath);
         if (StringUtils.isNoneBlank(labelName)) {
            return labelName;
         }
      }

      if (StringUtils.isNotBlank(app.getPackageURI())) {
         return FilenameUtils.getBaseName(app.getPackageURI());
      }

      if (app.getPackageDetails() != null && app.getPackageDetails().getSplitPackages() != null && !app.getPackageDetails().getSplitPackages().isEmpty()) {
         AndroidApplications.PackageDetails.SplitPackage firstSplitPackage = app.getPackageDetails().getSplitPackages().get(0);
         String labelName = firstSplitPackage.getPackageURI();
         labelName = labelName.substring(labelName.indexOf("./") + 2);
         if (labelName.contains("/")) {
            labelName = labelName.substring(0, labelName.indexOf(47));
         }

         return labelName.replace(".apk", "");
      } else {
         return "Name not Found";
      }
   }

   private List<String> getProfessionalAppNames(String category, boolean isFilterPhilipseAppWhenCategoryNull) {
      List<String> appList = new ArrayList<>();
      if (this.professionalApplications == null) {
         return appList;
      }

      for (AndroidApplications.AndroidApp app : this.professionalApplications.getClonePackages()) {
         boolean isIncludeThisApp = false;
         if (category != null) {
            isIncludeThisApp = app.containsCategory(category);
         } else if (!isFilterPhilipseAppWhenCategoryNull) {
            isIncludeThisApp = true;
         } else {
            isIncludeThisApp = !isPhilipsApps(app);
         }

         if (isIncludeThisApp) {
            String labelName = null;
            String filePath = this.professionalAppsPath + app.getPackageURI();
            if (new File(filePath).exists()) {
               labelName = this.getApkLabel(filePath);
            }

            if (StringUtils.isBlank(labelName)) {
               labelName = FilenameUtils.getBaseName(app.getPackageURI());
            }

            appList.add(labelName);
         }
      }

      return appList;
   }

   private List<String> getProfessionalAppPackageNames(String category, boolean isFilterPhilipseAppWhenCategoryNull) {
      List<String> appList = new ArrayList<>();
      if (this.professionalApplications == null) {
         return appList;
      }

      for (AndroidApplications.AndroidApp app : this.professionalApplications.getClonePackages()) {
         boolean isIncludeThisApp = false;
         if (category != null) {
            isIncludeThisApp = app.containsCategory(category);
         } else if (!isFilterPhilipseAppWhenCategoryNull) {
            isIncludeThisApp = true;
         } else {
            isIncludeThisApp = !isPhilipsApps(app);
         }

         if (isIncludeThisApp) {
            String packageName = null;
            String filePath = this.professionalAppsPath + app.getPackageURI();
            if (new File(filePath).exists()) {
               packageName = this.getApkPackageName(filePath);
            }

            if (StringUtils.isBlank(packageName)) {
               packageName = app.getPackageName();
            }

            appList.add(packageName);
         }
      }

      return appList;
   }

   private String getApkLabel(String apkFilePath) {
      File fileApk = new File(apkFilePath);
      if (!fileApk.exists()) {
         return null;
      }

      try (ApkFile apkFile = new ApkFile(apkFilePath)) {
         ApkMeta apkMeta = apkFile.getApkMeta();
         return apkMeta.getLabel();
      } catch (Exception e) {
         LOG.error("Get label for apk " + apkFilePath + " failure", e);
         return null;
      }
   }

   private String getApkPackageName(String apkFilePath) {
      File fileApk = new File(apkFilePath);
      if (!fileApk.exists()) {
         return null;
      }

      try (ApkFile apkFile = new ApkFile(apkFilePath)) {
         ApkMeta apkMeta = apkFile.getApkMeta();
         return apkMeta.getPackageName();
      } catch (Exception e) {
         LOG.error("Get packagename for apk " + apkFilePath + " failure", e);
         return null;
      }
   }

   private static boolean isPhilipsApps(AndroidApplications.AndroidApp app) {
      for (String category : PHILIPS_APPS_CATEGORIES) {
         if (app.containsCategory(category)) {
            return true;
         }
      }

      return false;
   }

   public static void updateProfessionalAppsInfo(String path) {
      if (path == null) {
         LOG.info("ProfessionalApps not exists");
      } else {
         File professionalAppsPath = new File(path);
         File[] apkFiles = professionalAppsPath.listFiles();
         if (apkFiles == null) {
            LOG.info("ProfessionalApps is empty");
         } else {
            File infoFile = new File(path + "/ProfessionalAppsInfo.json");
            if (infoFile.exists()) {
               LOG.info("ProfessionalAppsInfo.json existed,remove");
               FileUtils.deleteQuietly(infoFile);
            }

            AndroidApplications applications = new AndroidApplications();
            List<AndroidApplications.AndroidApp> androidAppList = new ArrayList<>();

            for (File apk : apkFiles) {
               if (FilenameUtils.getExtension(apk.getName()).equalsIgnoreCase("apk")) {
                  AndroidApplications.AndroidApp androidApp = parseAndroidApps(apk.getAbsolutePath());
                  if (androidApp != null) {
                     androidAppList.add(androidApp);
                  }
               }
            }

            applications.setClonePackages(androidAppList);
            applications.setAvailablePackages(String.valueOf(androidAppList.size()));

            try {
               FileUtils.writeStringToFile(infoFile, new Gson().toJson(applications), StandardCharsets.UTF_8);
            } catch (IOException e) {
               LOG.error(e.getMessage(), e);
            }
         }
      }
   }

   private static List<String> getCategoriesByManifestXml(String manifestXml) {
      SAXReader reader = new SAXReader();
      List<String> categoryList = new ArrayList<>();

      try {
         Document document = reader.read(new StringReader(manifestXml));
         Element root = document.getRootElement();

         for (Node categoryNode : root.selectNodes("/manifest/application/*/intent-filter/category")) {
            String category = categoryNode.valueOf("@android:name");
            if (!categoryList.contains(category) && !category.isEmpty()) {
               categoryList.add(category);
            }
         }
      } catch (DocumentException e) {
         LOG.error(e.getMessage(), e);
      }

      return categoryList;
   }

   private static AndroidApplications.AndroidApp parseAndroidApps(String apkFileName) {
      try {
         File fileApk = new File(apkFileName);
         if (!fileApk.exists()) {
            throw new IOException("apk file not exits," + apkFileName);
         }

         try (ApkFile apkFile = new ApkFile(fileApk)) {
            ApkMeta apkMeta = apkFile.getApkMeta();
            AndroidApplications.AndroidApp androidApp = new AndroidApplications.AndroidApp();
            androidApp.setPackageURI(fileApk.getName());
            androidApp.setPackageType("LOCAL");
            androidApp.setPackageName(apkMeta.getPackageName());
            Set<Locale> locales = apkFile.getLocales();
            List<String> localeNames = new ArrayList<>();

            for (Locale locale : locales) {
               String localeName = locale.getCountry();
               if (!localeName.isEmpty() && !localeNames.contains(localeName)) {
                  localeNames.add(localeName);
               }
            }

            androidApp.setPackageCountry(localeNames.toArray(new String[0]));
            String manifestXml = apkFile.getManifestXml();
            List<String> categoryList = getCategoriesByManifestXml(manifestXml);
            androidApp.setPackageCategory(categoryList.toArray(new String[0]));
            return androidApp;
         }
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
         return null;
      }
   }

   private void updateAndroidAppsMetaDataJson(String data) throws IOException {
      File appJsonFile = new File(this.getAndroidAppPath() + "AndroidAppsMetaData.json");
      saveAppsStringToMetaJsonFile(data, appJsonFile);
   }

   public static void saveAppsStringToMetaJsonFile(String jsonString, File jsonFile) throws IOException {
      JsonObject resultJson = JsonParser.parseString(jsonString).getAsJsonObject();
      JsonArray appArrays = resultJson.getAsJsonArray("clonePackages");

      for (int i = 0; i < appArrays.size(); i++) {
         appArrays.get(i).getAsJsonObject().remove("applicationName");
      }

      String formattedJsonString = new GsonBuilder().setPrettyPrinting().create().toJson(resultJson);
      FileUtils.writeStringToFile(jsonFile, formattedJsonString, StandardCharsets.UTF_8);
   }

   private void updateIdentifier() {
      File appIdentifierFile = new File(this.getAndroidAppPath() + "AndroidApps_Identifier.txt");

      try {
         FileUtils.writeStringToFile(appIdentifierFile, TpvDateUtils.getCurrentIndentifierFormatTime(), StandardCharsets.UTF_8);
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }
   }

   private int generateAppNo() {
      int highestLocalAppNo = 0;

      for (AndroidApplications.AndroidApp app : this.apps) {
         if ("local".equalsIgnoreCase(app.getPackageType())) {
            int appNo = Integer.parseInt(app.getPackagePosition());
            if (appNo > highestLocalAppNo) {
               highestLocalAppNo = appNo;
            }
         }
      }

      int newLocalAppNo = highestLocalAppNo + 1;
      this.handleConflictAppNo(newLocalAppNo);
      return newLocalAppNo;
   }

   private void handleConflictAppNo(int checkAppNo) {
      for (AndroidApplications.AndroidApp app : this.apps) {
         int appNo = Integer.parseInt(app.getPackagePosition());
         if (appNo == checkAppNo) {
            int newAppNo = appNo + 1;
            this.handleConflictAppNo(newAppNo);
            app.setPackagePosition(String.valueOf(newAppNo));
            break;
         }
      }
   }

   public JsonObject getAppsInfo(AndroidApplications.AndroidApp app) {
      if (null == app) {
         LOG.error(" app is null in getAppsinfo");
         return null;
      } else {
         JsonObject jsonObject = new JsonObject();
         jsonObject.addProperty("size", this.getAndroidAppFileSize(app));
         jsonObject.addProperty("name", StringEscapeUtils.escapeJava(this.getAppNameFromAndroidApp(app)));
         jsonObject.addProperty("appNo", Integer.parseInt(app.getPackagePosition()));
         jsonObject.addProperty("hide", app.getPackageShowHide());
         jsonObject.addProperty("category", String.join(",", app.getPackageCategory()));
         jsonObject.addProperty("country", String.join(",", app.getPackageCountry()));
         jsonObject.addProperty("type", app.getPackageType());
         return jsonObject;
      }
   }

   private long getAndroidAppFileSize(AndroidApplications.AndroidApp app) {
      String fileName = app.getPackageURI();
      if (StringUtils.isBlank(fileName)
         && app.getPackageDetails() != null
         && app.getPackageDetails().getSplitPackages() != null
         && !app.getPackageDetails().getSplitPackages().isEmpty()) {
         AndroidApplications.PackageDetails.SplitPackage firstSplitPackage = app.getPackageDetails().getSplitPackages().get(0);
         fileName = firstSplitPackage.getPackageURI();
      }

      if (fileName == null) {
         LOG.info("app {} PackageURI is null", app.getPackagePosition());
         return 0L;
      }

      fileName = fileName.substring(fileName.indexOf("./") + 2);
      if (fileName.contains("/")) {
         fileName = fileName.substring(0, fileName.indexOf(47));
      }

      return this.getFileSize(fileName);
   }

   public long getFileSize(String fileName) {
      return TpvFileUtils.getDirSize(new File(this.getAndroidAppPath() + fileName));
   }

   public boolean checkAppInfoJSONValid() {
      for (AndroidApplications.AndroidApp app : this.apps) {
         if (this.getAppsInfo(app) == null) {
            return false;
         }
      }

      return true;
   }

   public void addAppBundle(String packageName) throws IOException {
      this.checkValid();
      AndroidApplications.AndroidApp newApp = new AndroidApplications.AndroidApp();
      newApp.setPackageType("local");
      newApp.setPackageCategory(CATEGORY);
      newApp.setPackageCountry(COUNTRY);
      newApp.setPackagePosition("" + this.generateAppNo());
      newApp.setPackageShowHide("1");
      File[] apks = new File(this.getAndroidAppPath() + packageName).listFiles();
      if (apks == null) {
         this.errorMessage = "Empty bundle directory";
         LOG.error(this.errorMessage);
         throw new IOException(this.errorMessage);
      }

      AndroidApplications.PackageDetails packageDetails = new AndroidApplications.PackageDetails();

      for (File apk : apks) {
         if (!apk.getName().endsWith(".apk")) {
            LOG.info("not apk file:{},ignore", apk.getName());
         } else {
            try (ApkFile apkFile = new ApkFile(apk)) {
               ApkMeta apkMeta = apkFile.getApkMeta();
               String packageUri = "./" + packageName + "/" + apk.getName();
               String split = apkMeta.getSplit();
               if (split != null && !split.isEmpty()) {
                  AndroidApplications.PackageDetails.SplitPackage splitPackage = new AndroidApplications.PackageDetails.SplitPackage();
                  splitPackage.setPackageURI(packageUri);
                  splitPackage.setSplitName(split);
                  packageDetails.getSplitPackages().add(splitPackage);
               } else {
                  AndroidApplications.PackageDetails.BasePackage basePackage = new AndroidApplications.PackageDetails.BasePackage();
                  basePackage.setPackageURI(packageUri);
                  packageDetails.getBasePackages().add(basePackage);
                  packageDetails.setPackageName(apkMeta.getPackageName());
               }
            }
         }
      }

      newApp.setPackageDetails(packageDetails);
      newApp.setSilentAction("install");
      this.apps.add(newApp);
      this.save();
   }

   public void addApp(String apkname) throws IOException {
      this.checkValid();
      AndroidApplications.AndroidApp newApp = new AndroidApplications.AndroidApp();
      newApp.setPackageURI("./" + apkname);
      newApp.setPackageType("local");
      newApp.setPackageCategory(CATEGORY);
      newApp.setPackageCountry(COUNTRY);
      newApp.setPackagePosition("" + this.generateAppNo());
      newApp.setPackageShowHide("1");

      try (ApkFile apk = new ApkFile(this.getAndroidAppPath() + apkname)) {
         ApkMeta apkMeta = apk.getApkMeta();
         newApp.setPackageName(apkMeta.getPackageName());
      }

      newApp.setSilentAction("install");
      this.apps.add(newApp);
      this.save();
   }

   public void deleteApp(int appNo) throws IOException {
      this.checkValid();
      AndroidApplications.AndroidApp app = this.findAppByNo(String.valueOf(appNo));
      if (app == null) {
         throw new IOException("app not found appNo=" + appNo);
      }

      if (!"local".equalsIgnoreCase(app.getPackageType())) {
         throw new IOException("only local app can be removed");
      }

      String deleteAppName = app.getPackageURI();
      deleteAppName = deleteAppName.substring(deleteAppName.indexOf("./") + 2);
      if (app.getPackageDetails() != null) {
         deleteAppName = FilenameUtils.getPath(deleteAppName);
      }

      this.deleteAppFileByName(deleteAppName);
      this.apps.remove(app);
      this.save();
   }

   private void deleteAppFileByName(String appName) {
      File toDelete = new File(this.getAndroidAppPath() + appName);
      FileUtils.deleteQuietly(toDelete);
   }

   public void changeApp(String mode, String newValue, String appNo) throws IOException {
      this.checkValid();
      AndroidApplications.AndroidApp app = this.findAppByNo(appNo);
      if (app == null) {
         throw new IOException("app not found appNo=" + appNo);
      }

      if ("changeCategory".equalsIgnoreCase(mode)) {
         String[] strs = newValue.split(",");
         app.setPackageCategory(strs);
      } else if ("changeCountry".equalsIgnoreCase(mode)) {
         String[] strs = newValue.split(",");
         app.setPackageCountry(strs);
      } else if ("changeHide".equalsIgnoreCase(mode)) {
         app.setPackageShowHide(newValue);
      }

      this.save();
   }

   private String getLowerstAppNoOfUnLocalApps() {
      String appPostion = null;

      for (AndroidApplications.AndroidApp app : this.apps) {
         if (!"local".equalsIgnoreCase(app.getPackageType())
            && (appPostion == null || Integer.parseInt(appPostion) > Integer.parseInt(app.getPackagePosition()))) {
            appPostion = app.getPackagePosition();
         }
      }

      return appPostion;
   }

   private String getHighestAppNoOfLocalApps() {
      String appPostion = null;

      for (AndroidApplications.AndroidApp app : this.apps) {
         if ("local".equalsIgnoreCase(app.getPackageType())
            && (appPostion == null || Integer.parseInt(appPostion) < Integer.parseInt(app.getPackagePosition()))) {
            appPostion = app.getPackagePosition();
         }
      }

      return appPostion;
   }

   public void setApplicationName(String appNo, String name) throws IOException {
      this.checkValid();
      AndroidApplications.AndroidApp app = this.findAppByNo(appNo);
      if (app == null) {
         throw new IOException("app not found appNo=" + appNo);
      }

      app.setApplicationName(name);
      this.save();
   }

   public void changeAppNo(String appNo, String oldNo) throws IOException {
      this.checkValid();
      AndroidApplications.AndroidApp oldapp = this.findAppByNo(oldNo);
      if (oldapp == null) {
         throw new IOException("app not found appNo=" + appNo);
      }

      if ("local".equalsIgnoreCase(oldapp.getPackageType())) {
         String minUnLocalPosition = this.getLowerstAppNoOfUnLocalApps();
         if (minUnLocalPosition != null && Integer.parseInt(appNo) >= Integer.parseInt(minUnLocalPosition)) {
            throw new IOException("App cannot be moved to the new position! Local apps must be positioned before PlayStore or Portal apps!");
         }
      } else {
         String highestLocalPostion = this.getHighestAppNoOfLocalApps();
         if (highestLocalPostion != null && Integer.parseInt(appNo) <= Integer.parseInt(highestLocalPostion)) {
            throw new IOException("App cannot be moved to the new position! Local apps must be positioned before PlayStore or Portal apps!");
         }
      }

      AndroidApplications.AndroidApp newapp = this.findAppByNo(appNo);
      oldapp.setPackagePosition(appNo);
      if (newapp != null) {
         newapp.setPackagePosition(oldNo);
      }

      this.save();
   }

   public AndroidApplications.AndroidApp findAppByNo(String appNo) {
      if (null == appNo) {
         LOG.error(" null inputs in findAppByNo");
         return null;
      }

      AndroidApplications.AndroidApp resultApp = null;

      for (AndroidApplications.AndroidApp app : this.apps) {
         if (appNo.equalsIgnoreCase(app.getPackagePosition())) {
            resultApp = app;
            break;
         }
      }

      return resultApp;
   }

   private void checkKeyStore() throws IOException {
      File jks = new File(KEYSTORE_PATH + "keystore.jks");
      File kspass = new File(KEYSTORE_PATH + "keystore.pwd");
      File keypass = new File(KEYSTORE_PATH + "key.pwd");
      this.ksPass = null;
      this.keyPass = null;

      try {
         if (!jks.exists()) {
            throw new KeyStoreException("keystore.jks file not exists");
         }

         if (!kspass.exists()) {
            throw new KeyStoreException("ks pass not exists");
         }

         if (!keypass.exists()) {
            throw new KeyStoreException("key pass not exists");
         }

         this.ksPass = FileUtils.readFileToString(kspass, StandardCharsets.UTF_8);
         this.keyPass = FileUtils.readFileToString(keypass, StandardCharsets.UTF_8);
         char[] pwdArray = this.ksPass.toCharArray();
         KeyStore ks = KeyStore.getInstance("JKS");
         ks.load(new FileInputStream(jks), pwdArray);
         if (!ks.containsAlias("alias_aab_sign_key")) {
            throw new KeyStoreException("aab alias not exists");
         }
      } catch (Exception e) {
         LOG.error(e.getMessage());
         this.keyPass = UUID.randomUUID().toString();
         this.ksPass = UUID.randomUUID().toString();
         jks.getParentFile().mkdirs();
         String keytool = this.getKeytoolPath();
         String command = String.format(
            Locale.ENGLISH,
            "%s -genkeypair -alias %s -keypass %s -keystore %s -storepass %s -dname \"CN=Philips CMND,O=Android,C=US\" -validity 10000",
            keytool,
            "alias_aab_sign_key",
            this.keyPass,
            jks.getAbsolutePath(),
            this.ksPass
         );
         if (ProcessUtils.execCommond(CommonConstants.KEYTOOL_WD, command)) {
            LOG.info("generate new keystore");
            FileUtils.writeStringToFile(kspass, this.ksPass, StandardCharsets.UTF_8);
            FileUtils.writeStringToFile(keypass, this.keyPass, StandardCharsets.UTF_8);
         } else {
            LOG.error("generate new keystore failed");
         }
      }
   }

   private String getKeytoolPath() {
      String javaHome = System.getProperty("java.home");
      LOG.info("java.home:{}", javaHome);
      File keytoolExePath = new File(javaHome, "bin\\keytool.exe");
      return keytoolExePath.exists() ? keytoolExePath.getAbsolutePath() : CommonConstants.KEYTOOL_WD + "\\keytool.exe";
   }

   public String extractApksfromAAB(String aabFileName, boolean universal) throws IOException {
      this.checkValid();
      this.checkKeyStore();
      LOG.info("extract apks from app bundle {}", aabFileName);
      String apksName = aabFileName + ".apks";
      File apksfile = new File(apksName);
      if (apksfile.exists()) {
         FileUtils.deleteQuietly(apksfile);
      }

      String basePath = this.getAndroidAppPath() + FilenameUtils.getBaseName(aabFileName);
      String command = String.format(
         Locale.ENGLISH,
         "java -jar bundletool.jar build-apks --bundle=%s --output=%s --ks=%s --ks-pass=pass:%s --ks-key-alias=%s --key-pass=pass:%s",
         aabFileName,
         apksName,
         KEYSTORE_PATH + "keystore.jks",
         this.ksPass,
         "alias_aab_sign_key",
         this.keyPass
      );
      if (universal) {
         command = command + " --mode=universal";
      }

      if (!ProcessUtils.execCommond(CommonConstants.KEYTOOL_WD, command)) {
         throw new IOException("extract apks from app bundle " + aabFileName + " failed");
      }

      String tempFile = CommonConstants.ZIP_TEMP_DIR + "/" + FilenameUtils.getBaseName(aabFileName);
      ZipCommonUtils.unZipFiles(new File(apksName), tempFile);
      if (universal) {
         String apkName = basePath + ".apk";
         FileUtils.copyFile(new File(tempFile + "/universal.apk"), new File(apkName));
      } else {
         File targetPath = new File(basePath);
         targetPath.mkdirs();
         FileUtils.copyDirectory(new File(tempFile + "/splits/"), targetPath);
      }

      FileUtils.deleteQuietly(new File(tempFile));
      return universal ? new File(basePath + ".apk").getName() : basePath;
   }

   static {
      preInstalledPlayStoreApps.put("com.google.android.videos", "Play Movies & TV");
      preInstalledPlayStoreApps.put("com.google.android.play.games", "Play Games");
      preInstalledPlayStoreApps.put("com.google.android.youtube.tv", "YouTube");
      preInstalledPlayStoreApps.put("com.google.android.youtube.tvmusic", "YouTube Music");
      preInstalledPlayStoreApps.put("com.google.android.music", "Play Music");
      preInstalledPlayStoreApps.put("com.android.vending", "Play Store");
      preInstalledPlayStoreApps.put("com.netflix.ninja", "Netflix");
   }
}
