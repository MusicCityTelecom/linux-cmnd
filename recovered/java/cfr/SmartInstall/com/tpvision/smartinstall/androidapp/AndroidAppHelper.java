/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.androidapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tpvision.smartinstall.androidapp.AndroidApplications;
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
    private static final String[] CATEGORY = new String[]{"Children", "Entertainment", "Financial", "Games", "Lifestyle", "Local Info", "Music", "News", "Other", "Sports", "Technology", "Travel", "Weather"};
    private static final String[] COUNTRY = new String[]{"AR", "AT", "AU", "BE", "BG", "BR", "CH", "CZ", "DE", "DK", "EE", "ES", "FI", "FR", "GB", "GR", "HR", "HU", "IE", "IT", "KZ", "LT", "LU", "LV", "NL", "NO", "NZ", "PL", "PT", "RO", "RS", "RU", "SE", "SI", "SK", "TR", "UA", "US", "ZZ"};
    private static final String KEYSTORE_PATH = CommonConstants.SISERVER_CONF_DIR + "/KeyStore/";
    private static final String KEY_ALIAS_NAME = "alias_aab_sign_key";
    public static final boolean AAB_EXTRACT_SINGLE_APK = true;
    public static final String SMARTINFO_CATEGORY = "com.philips.professionaldisplaysolutions.jedi.intent.category.SMART_INFO";
    public static final String CDB_CATEGORY = "com.philips.professionaldisplaysolutions.jedi.intent.category.PRO_TV_DASHBOARD";
    public static final String EPG_CATEGORY = "com.philips.professionaldisplaysolutions.jedi.intent.category.PRO_TV_EPG";
    public static final String CONFIG_CATEGORY = "com.philips.professionaldisplaysolutions.jedi.intent.category.PRO_TV_CONFIG";
    private static final String[] PHILIPS_APPS_CATEGORIES = new String[]{"com.philips.professionaldisplaysolutions.jedi.intent.category.SMART_INFO", "com.philips.professionaldisplaysolutions.jedi.intent.category.PRO_TV_DASHBOARD", "com.philips.professionaldisplaysolutions.jedi.intent.category.PRO_TV_EPG", "com.philips.professionaldisplaysolutions.jedi.intent.category.PRO_TV_CONFIG"};
    private static final Map<String, String> preInstalledPlayStoreApps = new HashMap<String, String>();
    private static final Logger LOG;
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
                    LOG.error("clone package not found:{}", (Object)this.sname);
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
                if (appDetails == null || appDetails.isEmpty()) {
                    this.androidApplications = new AndroidApplications();
                    this.apps = new ArrayList<AndroidApplications.AndroidApp>();
                } else {
                    this.androidApplications = new Gson().fromJson(appDetails, AndroidApplications.class);
                    this.apps = this.androidApplications.getClonePackages();
                    this.checkDuplicatedPositions(this.apps);
                }
            }
            this.isValid = true;
        }
        catch (IOException | SQLException e) {
            LOG.error(e.getMessage(), e);
            this.errorMessage = e.getMessage();
        }
    }

    private void checkDuplicatedPositions(List<AndroidApplications.AndroidApp> apps) throws IOException {
        ArrayList<String> positions = new ArrayList<String>();
        boolean duplicated = false;
        for (AndroidApplications.AndroidApp app : apps) {
            if (positions.contains(app.getPackagePosition())) {
                LOG.info("found duplicated position:{},{}", (Object)app.getPackageName(), (Object)app.getPackagePosition());
                app.setPackagePosition(String.valueOf(apps.size()));
                duplicated = true;
                continue;
            }
            positions.add(app.getPackagePosition());
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
            AndroidAppHelper.updateProfessionalAppsInfo(this.professionalAppsPath);
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
        ArrayList<String> appList = new ArrayList<String>();
        appList.addAll(this.getAndroidAppNames(category, isFilterPhilipseAppWhenCategoryNull));
        appList.addAll(this.getProfessionalAppNames(category, isFilterPhilipseAppWhenCategoryNull));
        return appList;
    }

    public List<String> getAppPackageNames(String category, boolean isFilterPhilipseAppWhenCategoryNull) {
        ArrayList<String> appList = new ArrayList<String>();
        appList.addAll(this.getAndroidAppPackageNames(category, isFilterPhilipseAppWhenCategoryNull));
        appList.addAll(this.getProfessionalAppPackageNames(category, isFilterPhilipseAppWhenCategoryNull));
        return appList;
    }

    private List<String> getAndroidAppNames(String category, boolean isFilterPhilipseAppWhenCategoryNull) {
        ArrayList<String> appList = new ArrayList<String>();
        if (this.androidApplications == null) {
            return appList;
        }
        boolean categorySenstive = category != null || isFilterPhilipseAppWhenCategoryNull;
        for (AndroidApplications.AndroidApp app : this.androidApplications.getClonePackages()) {
            if (categorySenstive && LOCAL.equalsIgnoreCase(app.getPackageType())) {
                try (ApkFile apk2 = new ApkFile(this.getAndroidAppPath() + app.getPackageURI());){
                    String manifestXml = apk2.getManifestXml();
                    List<String> categoryList = AndroidAppHelper.getCategoriesByManifestXml(manifestXml);
                    app.setPackageCategory(categoryList.toArray(new String[0]));
                }
                catch (Exception apk2) {
                    // empty catch block
                }
            }
            boolean isIncludeThisApp = false;
            if (category != null) {
                isIncludeThisApp = app.containsCategory(category);
            } else if (!isFilterPhilipseAppWhenCategoryNull) {
                isIncludeThisApp = true;
            } else {
                boolean bl = isIncludeThisApp = !AndroidAppHelper.isPhilipsApps(app);
            }
            if (!isIncludeThisApp) continue;
            String labelName = this.getAppNameFromAndroidApp(app);
            appList.add(labelName);
        }
        return appList;
    }

    private List<String> getAndroidAppPackageNames(String category, boolean isFilterPhilipseAppWhenCategoryNull) {
        ArrayList<String> appList = new ArrayList<String>();
        if (this.androidApplications == null) {
            return appList;
        }
        boolean categorySenstive = category != null || isFilterPhilipseAppWhenCategoryNull;
        for (AndroidApplications.AndroidApp app : this.androidApplications.getClonePackages()) {
            if (categorySenstive && LOCAL.equalsIgnoreCase(app.getPackageType())) {
                try (ApkFile apk2 = new ApkFile(this.getAndroidAppPath() + app.getPackageURI());){
                    String manifestXml = apk2.getManifestXml();
                    List<String> categoryList = AndroidAppHelper.getCategoriesByManifestXml(manifestXml);
                    app.setPackageCategory(categoryList.toArray(new String[0]));
                }
                catch (Exception apk2) {
                    // empty catch block
                }
            }
            boolean isIncludeThisApp = false;
            if (category != null) {
                isIncludeThisApp = app.containsCategory(category);
            } else if (!isFilterPhilipseAppWhenCategoryNull) {
                isIncludeThisApp = true;
            } else {
                boolean bl = isIncludeThisApp = !AndroidAppHelper.isPhilipsApps(app);
            }
            if (!isIncludeThisApp) continue;
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
        return appList;
    }

    private String getAppNameFromAndroidApp(AndroidApplications.AndroidApp app) {
        String labelName;
        if (StringUtils.isNotBlank(app.getApplicationName())) {
            return app.getApplicationName();
        }
        if (preInstalledPlayStoreApps.containsKey(app.getPackageName())) {
            return preInstalledPlayStoreApps.get(app.getPackageName());
        }
        String apkFilePath = this.getAndroidAppPath() + app.getPackageURI();
        if (new File(apkFilePath).exists() && StringUtils.isNoneBlank(labelName = this.getApkLabel(apkFilePath))) {
            return labelName;
        }
        if (StringUtils.isNotBlank(app.getPackageURI())) {
            return FilenameUtils.getBaseName(app.getPackageURI());
        }
        if (app.getPackageDetails() != null && app.getPackageDetails().getSplitPackages() != null && !app.getPackageDetails().getSplitPackages().isEmpty()) {
            AndroidApplications.PackageDetails.SplitPackage firstSplitPackage = app.getPackageDetails().getSplitPackages().get(0);
            String labelName2 = firstSplitPackage.getPackageURI();
            if ((labelName2 = labelName2.substring(labelName2.indexOf("./") + 2)).contains("/")) {
                labelName2 = labelName2.substring(0, labelName2.indexOf(47));
            }
            return labelName2.replace(".apk", "");
        }
        return "Name not Found";
    }

    private List<String> getProfessionalAppNames(String category, boolean isFilterPhilipseAppWhenCategoryNull) {
        ArrayList<String> appList = new ArrayList<String>();
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
                boolean bl = isIncludeThisApp = !AndroidAppHelper.isPhilipsApps(app);
            }
            if (!isIncludeThisApp) continue;
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
        return appList;
    }

    private List<String> getProfessionalAppPackageNames(String category, boolean isFilterPhilipseAppWhenCategoryNull) {
        ArrayList<String> appList = new ArrayList<String>();
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
                boolean bl = isIncludeThisApp = !AndroidAppHelper.isPhilipsApps(app);
            }
            if (!isIncludeThisApp) continue;
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
        return appList;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String getApkLabel(String apkFilePath) {
        File fileApk = new File(apkFilePath);
        if (!fileApk.exists()) {
            return null;
        }
        try (ApkFile apkFile = new ApkFile(apkFilePath);){
            ApkMeta apkMeta = apkFile.getApkMeta();
            String string = apkMeta.getLabel();
            return string;
        }
        catch (Exception e) {
            LOG.error("Get label for apk " + apkFilePath + " failure", e);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String getApkPackageName(String apkFilePath) {
        File fileApk = new File(apkFilePath);
        if (!fileApk.exists()) {
            return null;
        }
        try (ApkFile apkFile = new ApkFile(apkFilePath);){
            ApkMeta apkMeta = apkFile.getApkMeta();
            String string = apkMeta.getPackageName();
            return string;
        }
        catch (Exception e) {
            LOG.error("Get packagename for apk " + apkFilePath + " failure", e);
            return null;
        }
    }

    private static boolean isPhilipsApps(AndroidApplications.AndroidApp app) {
        for (String category : PHILIPS_APPS_CATEGORIES) {
            if (!app.containsCategory(category)) continue;
            return true;
        }
        return false;
    }

    public static void updateProfessionalAppsInfo(String path) {
        if (path == null) {
            LOG.info("ProfessionalApps not exists");
            return;
        }
        File professionalAppsPath = new File(path);
        File[] apkFiles = professionalAppsPath.listFiles();
        if (apkFiles == null) {
            LOG.info("ProfessionalApps is empty");
            return;
        }
        File infoFile = new File(path + "/ProfessionalAppsInfo.json");
        if (infoFile.exists()) {
            LOG.info("ProfessionalAppsInfo.json existed,remove");
            FileUtils.deleteQuietly(infoFile);
        }
        AndroidApplications applications = new AndroidApplications();
        ArrayList<AndroidApplications.AndroidApp> androidAppList = new ArrayList<AndroidApplications.AndroidApp>();
        for (File apk : apkFiles) {
            AndroidApplications.AndroidApp androidApp;
            if (!FilenameUtils.getExtension(apk.getName()).equalsIgnoreCase("apk") || (androidApp = AndroidAppHelper.parseAndroidApps(apk.getAbsolutePath())) == null) continue;
            androidAppList.add(androidApp);
        }
        applications.setClonePackages(androidAppList);
        applications.setAvailablePackages(String.valueOf(androidAppList.size()));
        try {
            FileUtils.writeStringToFile(infoFile, new Gson().toJson(applications), StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private static List<String> getCategoriesByManifestXml(String manifestXml) {
        SAXReader reader = new SAXReader();
        ArrayList<String> categoryList = new ArrayList<String>();
        try {
            Document document = reader.read(new StringReader(manifestXml));
            Element root = document.getRootElement();
            List<Node> categoryNodes = root.selectNodes("/manifest/application/*/intent-filter/category");
            for (Node categoryNode : categoryNodes) {
                String category = categoryNode.valueOf("@android:name");
                if (categoryList.contains(category) || category.isEmpty()) continue;
                categoryList.add(category);
            }
        }
        catch (DocumentException e) {
            LOG.error(e.getMessage(), e);
        }
        return categoryList;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static AndroidApplications.AndroidApp parseAndroidApps(String apkFileName) {
        try {
            File fileApk = new File(apkFileName);
            if (!fileApk.exists()) {
                throw new IOException("apk file not exits," + apkFileName);
            }
            try (ApkFile apkFile = new ApkFile(fileApk);){
                ApkMeta apkMeta = apkFile.getApkMeta();
                AndroidApplications.AndroidApp androidApp2 = new AndroidApplications.AndroidApp();
                androidApp2.setPackageURI(fileApk.getName());
                androidApp2.setPackageType("LOCAL");
                androidApp2.setPackageName(apkMeta.getPackageName());
                Set<Locale> locales = apkFile.getLocales();
                ArrayList<String> localeNames = new ArrayList<String>();
                for (Locale locale : locales) {
                    String localeName = locale.getCountry();
                    if (localeName.isEmpty() || localeNames.contains(localeName)) continue;
                    localeNames.add(localeName);
                }
                androidApp2.setPackageCountry(localeNames.toArray(new String[0]));
                String manifestXml = apkFile.getManifestXml();
                List<String> categoryList = AndroidAppHelper.getCategoriesByManifestXml(manifestXml);
                androidApp2.setPackageCategory(categoryList.toArray(new String[0]));
                AndroidApplications.AndroidApp androidApp = androidApp2;
                return androidApp;
            }
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    private void updateAndroidAppsMetaDataJson(String data) throws IOException {
        File appJsonFile = new File(this.getAndroidAppPath() + "AndroidAppsMetaData.json");
        AndroidAppHelper.saveAppsStringToMetaJsonFile(data, appJsonFile);
    }

    public static void saveAppsStringToMetaJsonFile(String jsonString, File jsonFile) throws IOException {
        JsonObject resultJson = JsonParser.parseString(jsonString).getAsJsonObject();
        JsonArray appArrays = resultJson.getAsJsonArray("clonePackages");
        for (int i = 0; i < appArrays.size(); ++i) {
            appArrays.get(i).getAsJsonObject().remove("applicationName");
        }
        String formattedJsonString = new GsonBuilder().setPrettyPrinting().create().toJson(resultJson);
        FileUtils.writeStringToFile(jsonFile, formattedJsonString, StandardCharsets.UTF_8);
    }

    private void updateIdentifier() {
        File appIdentifierFile = new File(this.getAndroidAppPath() + "AndroidApps_Identifier.txt");
        try {
            FileUtils.writeStringToFile(appIdentifierFile, TpvDateUtils.getCurrentIndentifierFormatTime(), StandardCharsets.UTF_8);
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private int generateAppNo() {
        int highestLocalAppNo = 0;
        for (AndroidApplications.AndroidApp app : this.apps) {
            int appNo;
            if (!LOCAL.equalsIgnoreCase(app.getPackageType()) || (appNo = Integer.parseInt(app.getPackagePosition())) <= highestLocalAppNo) continue;
            highestLocalAppNo = appNo;
        }
        int newLocalAppNo = highestLocalAppNo + 1;
        this.handleConflictAppNo(newLocalAppNo);
        return newLocalAppNo;
    }

    private void handleConflictAppNo(int checkAppNo) {
        for (AndroidApplications.AndroidApp app : this.apps) {
            int appNo = Integer.parseInt(app.getPackagePosition());
            if (appNo != checkAppNo) continue;
            int newAppNo = appNo + 1;
            this.handleConflictAppNo(newAppNo);
            app.setPackagePosition(String.valueOf(newAppNo));
            break;
        }
    }

    public JsonObject getAppsInfo(AndroidApplications.AndroidApp app) {
        if (null == app) {
            LOG.error(" app is null in getAppsinfo");
            return null;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("size", this.getAndroidAppFileSize(app));
        jsonObject.addProperty("name", StringEscapeUtils.escapeJava(this.getAppNameFromAndroidApp(app)));
        jsonObject.addProperty("appNo", Integer.parseInt(app.getPackagePosition()));
        jsonObject.addProperty("hide", app.getPackageShowHide());
        jsonObject.addProperty("category", String.join((CharSequence)",", app.getPackageCategory()));
        jsonObject.addProperty("country", String.join((CharSequence)",", app.getPackageCountry()));
        jsonObject.addProperty("type", app.getPackageType());
        return jsonObject;
    }

    private long getAndroidAppFileSize(AndroidApplications.AndroidApp app) {
        String fileName = app.getPackageURI();
        if (StringUtils.isBlank(fileName) && app.getPackageDetails() != null && app.getPackageDetails().getSplitPackages() != null && !app.getPackageDetails().getSplitPackages().isEmpty()) {
            AndroidApplications.PackageDetails.SplitPackage firstSplitPackage = app.getPackageDetails().getSplitPackages().get(0);
            fileName = firstSplitPackage.getPackageURI();
        }
        if (fileName == null) {
            LOG.info("app {} PackageURI is null", (Object)app.getPackagePosition());
            return 0L;
        }
        if ((fileName = fileName.substring(fileName.indexOf("./") + 2)).contains("/")) {
            fileName = fileName.substring(0, fileName.indexOf(47));
        }
        return this.getFileSize(fileName);
    }

    public long getFileSize(String fileName) {
        return TpvFileUtils.getDirSize(new File(this.getAndroidAppPath() + fileName));
    }

    public boolean checkAppInfoJSONValid() {
        for (AndroidApplications.AndroidApp app : this.apps) {
            if (this.getAppsInfo(app) != null) continue;
            return false;
        }
        return true;
    }

    public void addAppBundle(String packageName) throws IOException {
        this.checkValid();
        AndroidApplications.AndroidApp newApp = new AndroidApplications.AndroidApp();
        newApp.setPackageType(LOCAL);
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
                LOG.info("not apk file:{},ignore", (Object)apk.getName());
                continue;
            }
            try (ApkFile apkFile = new ApkFile(apk);){
                ApkMeta apkMeta = apkFile.getApkMeta();
                String packageUri = "./" + packageName + "/" + apk.getName();
                String split = apkMeta.getSplit();
                if (split == null || split.isEmpty()) {
                    AndroidApplications.PackageDetails.BasePackage basePackage = new AndroidApplications.PackageDetails.BasePackage();
                    basePackage.setPackageURI(packageUri);
                    packageDetails.getBasePackages().add(basePackage);
                    packageDetails.setPackageName(apkMeta.getPackageName());
                    continue;
                }
                AndroidApplications.PackageDetails.SplitPackage splitPackage = new AndroidApplications.PackageDetails.SplitPackage();
                splitPackage.setPackageURI(packageUri);
                splitPackage.setSplitName(split);
                packageDetails.getSplitPackages().add(splitPackage);
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
        newApp.setPackageType(LOCAL);
        newApp.setPackageCategory(CATEGORY);
        newApp.setPackageCountry(COUNTRY);
        newApp.setPackagePosition("" + this.generateAppNo());
        newApp.setPackageShowHide("1");
        try (ApkFile apk = new ApkFile(this.getAndroidAppPath() + apkname);){
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
        if (!LOCAL.equalsIgnoreCase(app.getPackageType())) {
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
            if (LOCAL.equalsIgnoreCase(app.getPackageType()) || appPostion != null && Integer.parseInt(appPostion) <= Integer.parseInt(app.getPackagePosition())) continue;
            appPostion = app.getPackagePosition();
        }
        return appPostion;
    }

    private String getHighestAppNoOfLocalApps() {
        String appPostion = null;
        for (AndroidApplications.AndroidApp app : this.apps) {
            if (!LOCAL.equalsIgnoreCase(app.getPackageType()) || appPostion != null && Integer.parseInt(appPostion) >= Integer.parseInt(app.getPackagePosition())) continue;
            appPostion = app.getPackagePosition();
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
        String highestLocalPostion;
        String minUnLocalPosition;
        this.checkValid();
        AndroidApplications.AndroidApp oldapp = this.findAppByNo(oldNo);
        if (oldapp == null) {
            throw new IOException("app not found appNo=" + appNo);
        }
        if (LOCAL.equalsIgnoreCase(oldapp.getPackageType()) ? (minUnLocalPosition = this.getLowerstAppNoOfUnLocalApps()) != null && Integer.parseInt(appNo) >= Integer.parseInt(minUnLocalPosition) : (highestLocalPostion = this.getHighestAppNoOfLocalApps()) != null && Integer.parseInt(appNo) <= Integer.parseInt(highestLocalPostion)) {
            throw new IOException("App cannot be moved to the new position! Local apps must be positioned before PlayStore or Portal apps!");
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
            if (!appNo.equalsIgnoreCase(app.getPackagePosition())) continue;
            resultApp = app;
            break;
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
            if (!ks.containsAlias(KEY_ALIAS_NAME)) {
                throw new KeyStoreException("aab alias not exists");
            }
        }
        catch (Exception e) {
            LOG.error(e.getMessage());
            this.keyPass = UUID.randomUUID().toString();
            this.ksPass = UUID.randomUUID().toString();
            jks.getParentFile().mkdirs();
            String keytool = this.getKeytoolPath();
            String command = String.format(Locale.ENGLISH, "%s -genkeypair -alias %s -keypass %s -keystore %s -storepass %s -dname \"CN=Philips CMND,O=Android,C=US\" -validity 10000", keytool, KEY_ALIAS_NAME, this.keyPass, jks.getAbsolutePath(), this.ksPass);
            if (ProcessUtils.execCommond(CommonConstants.KEYTOOL_WD, command)) {
                LOG.info("generate new keystore");
                FileUtils.writeStringToFile(kspass, this.ksPass, StandardCharsets.UTF_8);
                FileUtils.writeStringToFile(keypass, this.keyPass, StandardCharsets.UTF_8);
            }
            LOG.error("generate new keystore failed");
        }
    }

    private String getKeytoolPath() {
        String javaHome = System.getProperty("java.home");
        LOG.info("java.home:{}", (Object)javaHome);
        File keytoolExePath = new File(javaHome, "bin\\keytool.exe");
        return keytoolExePath.exists() ? keytoolExePath.getAbsolutePath() : CommonConstants.KEYTOOL_WD + "\\keytool.exe";
    }

    public String extractApksfromAAB(String aabFileName, boolean universal) throws IOException {
        this.checkValid();
        this.checkKeyStore();
        LOG.info("extract apks from app bundle {}", (Object)aabFileName);
        String apksName = aabFileName + ".apks";
        File apksfile = new File(apksName);
        if (apksfile.exists()) {
            FileUtils.deleteQuietly(apksfile);
        }
        String basePath = this.getAndroidAppPath() + FilenameUtils.getBaseName(aabFileName);
        String command = String.format(Locale.ENGLISH, "java -jar bundletool.jar build-apks --bundle=%s --output=%s --ks=%s --ks-pass=pass:%s --ks-key-alias=%s --key-pass=pass:%s", aabFileName, apksName, KEYSTORE_PATH + "keystore.jks", this.ksPass, KEY_ALIAS_NAME, this.keyPass);
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
        LOG = LoggerFactory.getLogger(AndroidAppHelper.class);
    }
}

