/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.annotation.WebServlet
 */
package com.tpvision.smartinstall.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.PlayoutInfo;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.core.UpgSetting;
import com.tpvision.smartinstall.dao.mgr.DevicesManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.PlayoutInfoManager;
import com.tpvision.smartinstall.dao.mgr.SettingManager;
import com.tpvision.smartinstall.dao.mgr.UpgSettingManager;
import com.tpvision.smartinstall.servlet.BaseHttpServlet;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.JaxbReadXml;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.TpvFileUtils;
import com.tpvision.smartinstall.util.TpvStringUtils;
import com.tpvision.smartinstall.util.Utils;
import com.tpvision.smartinstall.util.ZipCommonUtils;
import com.tpvision.smartinstall.xml.Upgradeinfo;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(value={"/UploadUpgServlet"})
public class UploadUpgServlet
extends BaseHttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(UploadUpgServlet.class.getClass());
    private static final int BUFF_LENGTH = 128;
    private ArrayList<File> allFiles = new ArrayList();
    public long useableSpace = 0L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.error("UploadUPGServlet:{} sending doGet requests, however fileupload only possible over post", (Object)request.getRemoteAddr());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String status = "fail";
        String mode = request.getParameter("mode");
        LOG.info(" UI >>>>>>>> SI UploadUPGServlet: request = {}", (Object)mode);
        if (null == mode) {
            Utils.writeToResponse(status, "text/json;charset=UTF-8", response);
            LOG.info("UploadUpgServlet mode is null");
            return;
        }
        switch (mode.toUpperCase(Locale.ROOT)) {
            case "COPY_UPG": {
                status = this.modeCopyUpgFile(request);
                break;
            }
            case "OAD_UPG_STATUS": {
                status = this.getOadStatus(request);
                break;
            }
            case "RENAME_UPG": {
                status = this.renameUPG(request);
                break;
            }
            case "DELETE_UPG": {
                status = this.deleteUPG(request);
                break;
            }
            case "FIRMWAREINFO": {
                status = this.getFirmwareInfo(request);
                break;
            }
            case "CSMINFO": {
                status = this.getCSMInfo(request);
                break;
            }
            case "CLEAREXECUTEDUPG": {
                status = this.clearExecutedUpg(request);
                break;
            }
            case "UPLOAD_UPG": {
                status = this.modeStoreUserFile(request);
                break;
            }
            default: {
                LOG.info("UploadUPGServlet received unknown command = {}", (Object)mode);
            }
        }
        LOG.info(" SI >>>>>>>> UI UploadUPGServlet response for request = " + mode + " = " + status);
        Utils.writeToResponse(status, "text/json;charset=UTF-8", response);
        LOG.info("================== Finished Anwsering UI UploadUPGServlet " + mode + " request ===============");
    }

    private String getOadStatus(HttpServletRequest request) throws IOException {
        String status;
        String platform = null;
        String version = null;
        String versionFolder = null;
        String oad_path = null;
        long source_size = 0L;
        long target_size = 0L;
        String id = request.getParameter("uid");
        UpgSettingManager smgr = JpaManager.getUpgSettingManager();
        UpgSetting setting = smgr.loadByKey(Integer.parseInt(id));
        if (null != setting) {
            platform = setting.getPlatform();
            version = setting.getVersion();
        }
        if ("MS2K14".equalsIgnoreCase(platform) || "ES2K14".equalsIgnoreCase(platform) || "MS2K16".equalsIgnoreCase(platform) || "ES2K16".equalsIgnoreCase(platform) || "SS2K16".equalsIgnoreCase(platform)) {
            versionFolder = Utils.getFolder(version);
        }
        if ("MS2K16".equalsIgnoreCase(platform) || "SS2K16".equalsIgnoreCase(platform)) {
            oad_path = "MS2K16".equalsIgnoreCase(platform) ? CommonConstants.MS2K15_UPG_CREATOR_LOCATION_OUTPUT + versionFolder + "/" : CommonConstants.SS2K16_UPG_CREATOR_LOCATION_OUTPUT + versionFolder + "/";
            File aoutorun = new File(oad_path + "autorun.upg");
            File oad = new File(oad_path + "oad.upg");
            source_size = !aoutorun.exists() ? 0L : FileUtils.sizeOf(aoutorun);
            status = source_size < (target_size = !oad.exists() ? 0L : FileUtils.sizeOf(oad)) ? "{\"status\":\"success\"}" : "{\"status\":\"fails\"}";
        } else {
            status = "{\"status\":\"success\"}";
        }
        return status;
    }

    private String modeCopyUpgFile(HttpServletRequest request) throws IOException {
        String status = "{\"status\":\"fail\"}";
        File destDir = null;
        String platform = null;
        String version = null;
        String upgName = null;
        String versionFolder = null;
        String id = request.getParameter("uid");
        PlayoutInfoManager pim = JpaManager.getPlayoutInfoManager();
        PlayoutInfo playoutInfo = pim.loadByKey(Integer.parseInt(id));
        if (null != playoutInfo) {
            upgName = playoutInfo.getName();
            platform = playoutInfo.getPlatform();
            version = playoutInfo.getVersion();
        }
        if ("MS2K14".equalsIgnoreCase(platform) || "ES2K14".equalsIgnoreCase(platform) || "MS2K16".equalsIgnoreCase(platform) || "ES2K16".equalsIgnoreCase(platform) || "SS2K16".equalsIgnoreCase(platform)) {
            versionFolder = Utils.getFolder(version);
        } else {
            try {
                Double versionDbl = Double.parseDouble(version) * 1000.0;
                versionFolder = String.valueOf(versionDbl);
                versionFolder = versionFolder.substring(0, versionFolder.indexOf(46));
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
                return this.failedStatus(e.getMessage());
            }
        }
        File scrFile = new File(CommonConstants.UPLOADED_UPG_LOCATION + upgName + "/Autorun.upg");
        if (!scrFile.exists()) {
            pim.deleteByKey(Integer.parseInt(id));
            return Utils.buildFailReturnJson("File does not seem to exist, please upload new file.");
        }
        try {
            TpvFileUtils.checkDiskSpaceFull(scrFile);
        }
        catch (IOException e) {
            LOG.error("UploadUPGServlet: modeCopyUPGFile: Disk Full error: " + e.getMessage(), e);
            return this.failedStatus(e.getMessage());
        }
        this.preProcessChecks();
        if ("ES2K12".equalsIgnoreCase(platform) || "MS2K16".equalsIgnoreCase(platform) || "SS2K16".equalsIgnoreCase(platform) || "ES2K13".equalsIgnoreCase(platform) || "MS2K14".equalsIgnoreCase(platform) || "ES2K14".equalsIgnoreCase(platform) || "ES2K16".equalsIgnoreCase(platform)) {
            this.preProcessChecksES(platform);
            if ("MS2K14".equalsIgnoreCase(platform)) {
                destDir = new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_OUTPUT + versionFolder);
            } else if ("ES2K14".equalsIgnoreCase(platform)) {
                destDir = new File(CommonConstants.ES2K14_UPG_CREATOR_LOCATION_OUTPUT + versionFolder);
            } else if ("MS2K16".equalsIgnoreCase(platform)) {
                destDir = new File(CommonConstants.MS2K15_UPG_CREATOR_LOCATION_OUTPUT + versionFolder);
            } else if ("SS2K16".equalsIgnoreCase(platform)) {
                destDir = new File(CommonConstants.SS2K16_UPG_CREATOR_LOCATION_OUTPUT + versionFolder);
            } else if ("ES2K16".equalsIgnoreCase(platform)) {
                destDir = new File(CommonConstants.ES2K16_UPG_CREATOR_LOCATION_OUTPUT + versionFolder);
            }
        } else {
            destDir = new File(CommonConstants.RF_PLAY_BACK_INPUT_LOCATION + platform);
        }
        if (null != destDir) {
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            try {
                FileUtils.copyFileToDirectory(scrFile, destDir);
                if ("MS2K16".equalsIgnoreCase(platform)) {
                    String outputPath = CommonConstants.MS2K15_UPG_CREATOR_LOCATION_OUTPUT + versionFolder + "/";
                    ZipCommonUtils.zipFiles(outputPath + "oad.upg", outputPath + "autorun.upg");
                } else if ("SS2K16".equalsIgnoreCase(platform)) {
                    String outputPath = CommonConstants.SS2K16_UPG_CREATOR_LOCATION_OUTPUT + versionFolder + "/";
                    ZipCommonUtils.zipFiles(outputPath + "oad.upg", outputPath + "autorun.upg");
                }
                status = "{\"status\":\"success\"}";
            }
            catch (IOException e) {
                LOG.error(e.getMessage(), e);
                return this.failedStatus(e.getMessage());
            }
        }
        return status;
    }

    protected void preProcessChecks() throws IOException {
        File inputPath = new File(CommonConstants.RF_PLAY_BACK_INPUT_LOCATION);
        if (inputPath.exists()) {
            try {
                FileUtils.deleteDirectory(inputPath);
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    protected void preProcessChecksES(String platformId) throws IOException {
        ArrayList<String> ES = new ArrayList<String>();
        File inputPath = null;
        if ("MS2K14".equalsIgnoreCase(platformId) || "ES2K14".equalsIgnoreCase(platformId) || "MS2K16".equalsIgnoreCase(platformId) || "ES2K16".equalsIgnoreCase(platformId)) {
            inputPath = new File(CommonConstants.MS2K14_UPG_CREATOR_LOCATION_INPUT);
            ES.add("HTV_DWPack_1401.exe");
            ES.add("GenerateAutorun.bat");
            ES.add("libeay32.dll");
            ES.add("scfg.xml");
            ES.add("Key");
            ES.add("Tool_backup");
            ES.add("7-Zip");
            ES.add("Configuration");
        }
        if (null == inputPath || !inputPath.exists()) {
            return;
        }
        for (File f : inputPath.listFiles()) {
            boolean itemFound = false;
            for (String item : ES) {
                if (f.getName().indexOf(item) <= -1) continue;
                itemFound = true;
                break;
            }
            if (itemFound) continue;
            try {
                if (f.isDirectory()) {
                    FileUtils.deleteDirectory(f);
                    continue;
                }
                FileUtils.deleteQuietly(f);
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    protected int size(InputStream stream) {
        if (null == stream) {
            return 0;
        }
        int length = 0;
        try {
            int size;
            byte[] buffer = new byte[2048];
            while ((size = stream.read(buffer)) != -1) {
                length += size;
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return length;
    }

    private String modeStoreUserFile(HttpServletRequest request) throws ServletException, IOException {
        List<String> list = null;
        String tempFilename = null;
        String res = "{\"status\":\"fail\",\"errorCode\":\"1\"}";
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                String userName = Utils.getAuthenticationName();
                tempFilename = UUID.randomUUID().toString();
                File tempFirmWareFolder = new File(CommonConstants.UPLOADED_UPG_LOCATION + tempFilename + "/");
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                if (null != multiparts && (multiparts.get(0).getName().indexOf(".pkg") > -1 || multiparts.get(0).getName().indexOf(".upg") > -1)) {
                    if (!tempFirmWareFolder.exists()) {
                        tempFirmWareFolder.mkdirs();
                    }
                    for (FileItem item : multiparts) {
                        if (item.isFormField()) continue;
                        String path = tempFirmWareFolder.toString();
                        if (item.getName().indexOf(".upg") <= -1 && item.getName().indexOf(".pkg") <= -1) continue;
                        TpvFileUtils.checkDiskSpaceFull(2L * item.getSize());
                        File targetLocationFile = new File(tempFirmWareFolder.toString() + "/Autorun.upg");
                        if (targetLocationFile.exists()) {
                            FileUtils.deleteQuietly(targetLocationFile);
                        }
                        item.write(targetLocationFile);
                        FileInputStream filecontent = new FileInputStream(targetLocationFile);
                        Throwable throwable = null;
                        try {
                            list = this.upgRead(filecontent, path, request.getContentLength());
                        }
                        catch (Throwable throwable2) {
                            throwable = throwable2;
                            throw throwable2;
                        }
                        finally {
                            if (filecontent == null) continue;
                            if (throwable != null) {
                                try {
                                    ((InputStream)filecontent).close();
                                }
                                catch (Throwable throwable3) {
                                    throwable.addSuppressed(throwable3);
                                }
                                continue;
                            }
                            ((InputStream)filecontent).close();
                        }
                    }
                }
                if (null != list) {
                    String platform = (String)list.get(0);
                    String version = (String)list.get(1);
                    if (StringUtils.isEmpty(version) || "0".equalsIgnoreCase(version)) {
                        version = "1.0";
                    }
                    if (PlatformUtils.isValidUploadedPlatform(PlatformUtils.getPlatformId(platform))) {
                        UpgSetting upgSetting = this.loadConfToDb(userName, tempFilename, platform, version, (String)list.get(3));
                        File realFolder = new File(CommonConstants.UPLOADED_UPG_LOCATION + upgSetting.getName());
                        if (realFolder.exists()) {
                            FileUtils.deleteQuietly(realFolder);
                        }
                        tempFirmWareFolder.renameTo(realFolder);
                        res = "{\"status\":\"success\",\"rename\":\"" + (String)list.get(2) + "\", \"name\":\"" + upgSetting.getName() + "\", \"version\":\"" + version + "\" }";
                    } else {
                        res = "{\"status\":\"fail\",\"errorCode\":\"3\"}";
                    }
                } else {
                    res = "{\"status\":\"fail\",\"errorCode\":\"2\"}";
                }
            }
            catch (Exception ex) {
                LOG.error(ex.getMessage(), ex);
                res = this.failedStatus(ex.getMessage());
            }
        }
        return res;
    }

    private UpgSetting loadConfToDb(String userName, String configName, String platform, String version, String ipversion) {
        UpgSettingManager smgr = JpaManager.getUpgSettingManager();
        UpgSetting set = new UpgSetting();
        set.setName(configName);
        set.setUpgrename(configName);
        set.setPlatform(platform);
        set.setVersion(version);
        set.setIpversion(ipversion);
        set.setCreatedBy(userName);
        set.setCreatedDate(new Date());
        set.setLastUpdatedBy(userName);
        set.setLastUpdatedDate(new Date());
        set.setUpgPlayedOn(new Date());
        set.setUpgPlayedBy(userName);
        set.setIsdelete("N");
        smgr.saveUpgSetting(set);
        set.setName("firmware_" + set.getId());
        set.setUpgrename(set.getName());
        smgr.saveUpgSetting(set);
        return set;
    }

    private List<String> upgRead(InputStream content, String path, int fileLength) throws IOException {
        StopWatch upgReadWatch = new StopWatch();
        upgReadWatch.start();
        byte[] buffer = new byte[128];
        List<Object> list = new ArrayList();
        if (fileLength > 314572800 || fileLength < 0xA00000) {
            list = this.tryToLoadFromUpgXml(path);
            if (list == null) {
                try (InputStreamReader r = new InputStreamReader(content);
                     BufferedReader bfReader1 = new BufferedReader(r);){
                    list = this.upgParse(bfReader1, path, null);
                }
                catch (Exception e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            content.read(buffer, 0, 128);
            baos.write(buffer, 0, 128);
            baos.flush();
            try (ByteArrayInputStream stream1 = new ByteArrayInputStream(baos.toByteArray());
                 ByteArrayInputStream stream2 = new ByteArrayInputStream(baos.toByteArray());
                 InputStreamReader r = new InputStreamReader(stream2);
                 BufferedReader bfReader2 = new BufferedReader(r);){
                list = this.upgParse(bfReader2, path, stream1);
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
        upgReadWatch.stop();
        LOG.info("upg read version info from file under folder:<{}>, use:{}", (Object)path, (Object)upgReadWatch.getTime());
        return list;
    }

    private List<String> tryToLoadFromUpgXml(String path) {
        String version = "1";
        String platformid = null;
        String isReame = "N";
        String ipversion = "";
        String tempFilePath = CommonConstants.UPLOADED_UPG_LOCATION_TEMPXML;
        File uploadUpgFile = new File(path + "/Autorun.upg");
        String extractFileName = "META-INF/updateinfo/upg.xml";
        String xmlPath = tempFilePath + extractFileName;
        try {
            ZipCommonUtils.zipFileRead(uploadUpgFile.getAbsolutePath(), extractFileName, tempFilePath);
        }
        catch (Exception ex) {
            LOG.warn("Can't extract xml file from the upg under the folder:{}, try to get the tail data", (Object)path);
            ArrayList<String> result = new ArrayList<String>();
            try (ReversedLinesFileReader reader = new ReversedLinesFileReader(uploadUpgFile, StandardCharsets.UTF_8);){
                String line = "";
                while ((line = reader.readLine()) != null) {
                    result.add(0, line);
                    if (!line.toLowerCase().contains("<upgradeinfo>")) continue;
                    result.add(0, "<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                    FileUtils.writeStringToFile(new File(xmlPath), String.join((CharSequence)"", result.toArray(new String[0])), StandardCharsets.UTF_8);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        File xmlPathFile = new File(xmlPath);
        if (xmlPathFile.exists()) {
            try {
                String productId;
                Upgradeinfo upgradeinfo = JaxbReadXml.readString(Upgradeinfo.class, xmlPath);
                File destDir = new File(xmlPath);
                if (destDir.exists()) {
                    Files.delete(destDir.toPath());
                }
                if ((productId = upgradeinfo.getProductId()).endsWith("_R")) {
                    productId = StringUtils.substring(productId, 0, productId.length() - 2);
                }
                platformid = PlatformUtils.getPlatformType(productId + "_CloneData");
                version = upgradeinfo.getSoftWareVersion();
                String[] versionArray = version.split("\\.");
                String mainVersion = versionArray[versionArray.length - 2];
                String subVersion = versionArray[versionArray.length - 1];
                if (subVersion.length() == 1) {
                    version = mainVersion + ".00" + subVersion;
                } else if (subVersion.length() == 2) {
                    version = mainVersion + ".0" + subVersion;
                } else if (subVersion.length() == 3) {
                    version = mainVersion + "." + subVersion;
                }
                ipversion = upgradeinfo.softWareVersion;
                LOG.info("String>>>>{}", (Object)ipversion);
                isReame = "Y";
                ArrayList<String> list = new ArrayList<String>();
                list.add(platformid);
                list.add(version);
                list.add(isReame);
                list.add(ipversion);
                return list;
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return null;
    }

    private List<String> upgParse(BufferedReader bfReader, String path, InputStream stream) {
        String[] lineSplit = null;
        String version = "1";
        String[] versionSplit = null;
        String platformid = null;
        String isReame = "N";
        String ipversion = "";
        long majorNumber = 0L;
        long minorNumber = 0L;
        byte[] buffer = new byte[128];
        try {
            String line;
            while ((line = bfReader.readLine()) != null) {
                if (line.contains("Q555A") || line.contains("Q555H") || line.contains("Q554B")) {
                    Pattern p = Pattern.compile("-", 16);
                    lineSplit = p.split(line);
                    if (lineSplit[1].contains("_")) {
                        versionSplit = lineSplit[1].split("_");
                        version = versionSplit[0];
                    } else {
                        version = lineSplit[1];
                    }
                    platformid = lineSplit[0];
                    if (line.contains("Q554B") && version.startsWith("0.4")) {
                        platformid = platformid + "-2K13PSMS";
                    }
                    isReame = "Y";
                    LOG.info("String>>>>{}", (Object)version);
                } else if (line.contains("PHILIPS_2K12") || line.contains("T35TC1_Auto")) {
                    platformid = "ES2K12";
                    version = "";
                    isReame = "Y";
                } else if (line.contains("D0TKN2_Auto_")) {
                    platformid = "ES2K13";
                    version = "2.013";
                    isReame = "Y";
                } else if (line.contains("TN4EHV_Auto_") || line.contains("TN4EEV_Auto_")) {
                    boolean isMsPlatform = line.contains("TN4EHV_Auto_");
                    String str = this.convert(line);
                    Pattern p = Pattern.compile("\\u0000", 16);
                    lineSplit = p.split(str);
                    try {
                        StringBuilder ipversionBuilder = new StringBuilder();
                        if (isMsPlatform) {
                            ipversionBuilder.append("TPN141HE_004.");
                        } else {
                            ipversionBuilder.append("TPN142HE_005.");
                        }
                        String[] versionSplits = lineSplit[1].split("\\.");
                        ipversion = ipversionBuilder.append(versionSplits[1]).append(".").append(versionSplits[2]).append(".128").toString();
                    }
                    catch (Exception ex) {
                        LOG.error(ex.getMessage(), ex);
                        ipversion = "";
                    }
                    LOG.info("2k14 -> version ::>>>> {}", (Object)ipversion);
                    platformid = isMsPlatform ? "MS" + lineSplit[2].substring(0, 4).toUpperCase() : "ES" + lineSplit[2].substring(0, 4).toUpperCase();
                    try {
                        version = Utils.getVersion(lineSplit[1]);
                    }
                    catch (Exception ex) {
                        LOG.error(ex.getMessage(), ex);
                        version = "1.000";
                    }
                    isReame = "Y";
                } else if (null != stream && line.contains("TPN161HE")) {
                    stream.read(buffer, 0, 128);
                    majorNumber = (buffer[4] + 256 & 0xFF) + (buffer[5] << 8) + (buffer[6] << 16) + (buffer[7] << 32);
                    minorNumber = (buffer[8] + 256 & 0xFF) + (buffer[9] << 8) + (buffer[10] << 16) + (buffer[11] << 32);
                    version = String.format(Locale.ENGLISH, "%03d.%03d", majorNumber, minorNumber);
                    ipversion = "TPN161HE_000." + version + ".000";
                    if (line.contains("TPN161HE")) {
                        platformid = "ES2K16";
                    }
                    isReame = "Y";
                } else if (line.contains("TPM187HE_Auto")) {
                    String str = this.convert(line);
                    Pattern p = Pattern.compile("\\u0000", 16);
                    lineSplit = p.split(str);
                    try {
                        ipversion = lineSplit[1];
                    }
                    catch (Exception ex) {
                        LOG.error(ex.getMessage(), ex);
                        ipversion = "";
                    }
                    LOG.info("String>>>>{}", (Object)ipversion);
                    String productId = "TPM187HE";
                    platformid = PlatformUtils.getPlatformType(productId + "_CloneData");
                    try {
                        String[] vers = ipversion.split("\\.");
                        if (null != vers && vers.length >= 4) {
                            version = vers[2] + "." + vers[3];
                        }
                    }
                    catch (Exception ex) {
                        LOG.error(ex.getMessage(), ex);
                        version = "1.000";
                    }
                    isReame = "Y";
                } else if (null != stream && line.contains("TPS191HE")) {
                    stream.read(buffer, 0, 128);
                    platformid = PlatformUtils.getPlatformType("TPS191HE_CloneData");
                    String header = new String(buffer);
                    String[] lines = header.split("\n");
                    String maj = "";
                    String min = "";
                    for (String aline : lines) {
                        if (aline.contains("PHI_MAJ2")) {
                            maj = aline.replaceAll("# PHI_MAJ2 ", "");
                            continue;
                        }
                        if (!aline.contains("PHI_MIN")) continue;
                        min = aline.replaceAll("# PHI_MIN ", "");
                    }
                    version = maj + "." + min;
                    ipversion = "TPS191HE_000." + version + ".000";
                } else {
                    String[] vers;
                    if (line.contains("TPM215HKN")) {
                        platformid = "NAFTAT32";
                        ipversion = line.substring(line.indexOf("TPM215HKN"));
                        try {
                            vers = ipversion.split("\\.");
                            if (null != vers && vers.length >= 4) {
                                version = vers[2] + "." + vers[3];
                            }
                        }
                        catch (Exception ex) {
                            LOG.error(ex.getMessage(), ex);
                            version = "1.000";
                        }
                        isReame = "Y";
                        break;
                    }
                    if (line.contains("TPM215HEA")) {
                        platformid = "T32";
                        ipversion = line.substring(line.indexOf("TPM215HEA"));
                        try {
                            vers = ipversion.split("\\.");
                            if (null != vers && vers.length >= 4) {
                                version = vers[2] + "." + vers[3];
                            }
                        }
                        catch (Exception ex) {
                            LOG.error(ex.getMessage(), ex);
                            version = "1.000";
                        }
                        isReame = "Y";
                        break;
                    }
                }
                if (StringUtils.isAnyBlank(platformid, version, ipversion) || !StringUtils.equalsAnyIgnoreCase(isReame, "Y")) continue;
                break;
            }
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        ArrayList<String> list = new ArrayList<String>();
        list.add(platformid);
        list.add(version);
        list.add(isReame);
        list.add(ipversion);
        return list;
    }

    public long getSizeOfCFreeDrive() {
        File f = new File(CommonConstants.C_DIR);
        long gbSize = 0x100000L;
        this.useableSpace = f.getUsableSpace() / 0x100000L;
        return this.useableSpace;
    }

    public String getCloneItemsIdentifiers(String filePath) {
        File rootfile = new File(filePath);
        this.getSubFile(rootfile);
        String totalInfo = "";
        if (null != this.allFiles) {
            for (File file : this.allFiles) {
                try {
                    FileReader fr = new FileReader(file);
                    Throwable throwable = null;
                    try {
                        BufferedReader br = new BufferedReader(fr);
                        Throwable throwable2 = null;
                        try {
                            StringBuilder sb = new StringBuilder();
                            String line = br.readLine();
                            if (line != null) {
                                sb.append(line + " ");
                            }
                            totalInfo = totalInfo + sb.toString();
                        }
                        catch (Throwable throwable3) {
                            throwable2 = throwable3;
                            throw throwable3;
                        }
                        finally {
                            if (br == null) continue;
                            if (throwable2 != null) {
                                try {
                                    br.close();
                                }
                                catch (Throwable throwable4) {
                                    throwable2.addSuppressed(throwable4);
                                }
                                continue;
                            }
                            br.close();
                        }
                    }
                    catch (Throwable throwable5) {
                        throwable = throwable5;
                        throw throwable5;
                    }
                    finally {
                        if (fr == null) continue;
                        if (throwable != null) {
                            try {
                                fr.close();
                            }
                            catch (Throwable throwable6) {
                                throwable.addSuppressed(throwable6);
                            }
                            continue;
                        }
                        fr.close();
                    }
                }
                catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
        return totalInfo;
    }

    public void getSubFile(File rootFile) {
        if (null != rootFile) {
            if (rootFile.isDirectory()) {
                File[] files = rootFile.listFiles();
                if (null != files && files.length > 0) {
                    for (File f : files) {
                        if (f.isDirectory()) {
                            this.getSubFile(f);
                            continue;
                        }
                        if (!f.getName().endsWith("_Identifier.txt")) continue;
                        this.allFiles.add(f);
                    }
                }
            } else if (rootFile.getName().endsWith("_Identifier.txt")) {
                this.allFiles.add(rootFile);
            }
        }
    }

    private String clearExecutedUpg(HttpServletRequest request) {
        String platform = request.getParameter("platform");
        String version = request.getParameter("version");
        String clearPath = null;
        if ("ES2K16".equalsIgnoreCase(platform)) {
            clearPath = CommonConstants.ES2K16_UPG_CREATOR_LOCATION_OUTPUT;
        } else if ("MS2K16".equalsIgnoreCase(platform)) {
            clearPath = CommonConstants.MS2K15_UPG_CREATOR_LOCATION_OUTPUT;
        } else if ("MS2K14".equalsIgnoreCase(platform)) {
            clearPath = CommonConstants.MS2K14_UPG_CREATOR_LOCATION_OUTPUT;
        } else if ("ES2K14".equalsIgnoreCase(platform)) {
            clearPath = CommonConstants.ES2K14_UPG_CREATOR_LOCATION_OUTPUT;
        }
        String mainVersion = version.substring(0, version.indexOf(46));
        String subVersion = version.substring(version.indexOf(46) + 1);
        File file = new File(clearPath + "/" + mainVersion + subVersion);
        if (file.exists()) {
            this.deleteDir(file);
        } else {
            LOG.error("UploadUPGServlet: clearExecutedUpg: could not find firmwares");
        }
        return "{\"status\":\"success\"}";
    }

    private boolean deleteDir(File file) {
        if (file.isDirectory()) {
            String[] children = file.list();
            for (int i = 0; i < children.length; ++i) {
                boolean success = this.deleteDir(new File(file, children[i]));
                if (success) continue;
                return false;
            }
        }
        return file.delete();
    }

    private String renameUPG(HttpServletRequest request) {
        UpgSettingManager smgr = JpaManager.getUpgSettingManager();
        String status = "{\"status\":\"fail\",\"errorCode\":\"1\"}";
        String name = request.getParameter("name");
        String rename = request.getParameter("rename");
        String version = request.getParameter("version");
        String opMark = request.getParameter("op_mark");
        List<UpgSetting> settings = null;
        List<UpgSetting> upgsett = null;
        if ("1".equals(opMark)) {
            settings = Collections.singletonList(smgr.loadByKey(Integer.parseInt(name)));
            upgsett = smgr.findByUpgRename(name);
        } else {
            settings = smgr.findByName(name);
            upgsett = smgr.findByUpgRename(rename);
        }
        if (!upgsett.isEmpty()) {
            status = "{\"status\":\"fail\"}";
            return status;
        }
        if (!settings.isEmpty()) {
            UpgSetting set = settings.get(0);
            if ("0".equals(opMark) && null != rename) {
                set.setUpgrename(rename);
            }
            if (null != version) {
                set.setIpversion(version);
            }
            smgr.saveUpgSetting(set);
            status = "{\"status\":\"success\"}";
            if (null == rename) {
                rename = name;
            }
        }
        return status;
    }

    private String deleteUPG(HttpServletRequest request) {
        File scrFile;
        String status = "{\"status\":\"fail\",\"errorCode\":\"-1\"}";
        int id = TpvStringUtils.tryParseInt(request.getParameter("id"), -1);
        if (id <= 0) {
            LOG.error("UploadUPGServlet: ID is blank in deleteUPG request");
            return status;
        }
        DevicesManager devicesManager = JpaManager.getDevicesManager();
        devicesManager.findDevicesByFirmwareId(id).forEach(device -> {
            device.setFirmwareid(0);
            device.setSiFirmwareIdentifier(null);
            device.setProgress("ST");
            device.setFwColor("black");
            devicesManager.save((Devices)device);
        });
        UpgSettingManager smgr = JpaManager.getUpgSettingManager();
        status = "{\"status\":\"fail\",\"errorCode\":\"0\"}";
        UpgSetting setting = smgr.loadByKey(id);
        if (null == setting) {
            LOG.error("UploadUPGServlet: deleteUPG: no settings found for id = " + id);
        }
        if (!(scrFile = new File(CommonConstants.UPLOADED_UPG_LOCATION + setting.getName() + "/Autorun.upg")).exists()) {
            scrFile = new File(CommonConstants.UPLOADED_UPG_LOCATION + setting.getPlatform() + "/" + setting.getVersion() + "/Autorun.upg");
        }
        FileUtils.deleteQuietly(scrFile);
        try {
            if (FileUtils.isEmptyDirectory(scrFile.getParentFile())) {
                FileUtils.deleteQuietly(scrFile.getParentFile());
            }
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        smgr.deleteByKey(setting.getId());
        status = "{\"status\":\"success\"}";
        String contextPath = CommonConstants.servletContextPath;
        File upgDir = new File(contextPath + "/Profile/UPG/" + id + "/");
        try {
            if (upgDir.exists()) {
                FileUtils.deleteDirectory(upgDir);
            }
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return this.failedStatus(e.getMessage());
        }
        return status;
    }

    private String getFirmwareInfo(HttpServletRequest request) {
        int upgId = Integer.parseInt(request.getParameter("upgId"));
        UpgSettingManager upgSettingManager = JpaManager.getUpgSettingManager();
        UpgSetting upgSetting = upgSettingManager.loadByKey(upgId);
        String platformType = PlatformUtils.getPlatformName(upgSetting.getPlatform());
        String status = "{\"status\":\"success\", \"Firmware_name\":\"" + upgSetting.getUpgrename() + "\", \"Platform_type\":\"" + platformType + "\", \"Software_version\":\"" + upgSetting.getVersion() + "\", \"Time_upload\":\"" + upgSetting.getCreatedDate() + "\", \"User_uploaded\":\"" + upgSetting.getCreatedBy() + "\", \"Time_last_RF_Playout\":\"" + upgSetting.getUpgPlayedOn() + "\"}";
        return status;
    }

    private String getCSMInfo(HttpServletRequest request) {
        String status = "{\"status\":\"fail\"}";
        int settingId = Integer.parseInt(request.getParameter("settingId"));
        String platform = request.getParameter("platform");
        String name = request.getParameter("name");
        try {
            File[] file;
            String userName = Utils.getAuthenticationName();
            String path = CommonConstants.CLONE_PROCESS_LOCATION + name + "/" + platform;
            File dataDumpDir = TpvFileUtils.getDirectoryByName(new File(path), PlatformUtils.getCSMPath(platform));
            File csmFile = null;
            if (dataDumpDir == null) {
                dataDumpDir = new File(path);
            }
            if (null == (file = dataDumpDir.listFiles()) || file.length == 0) {
                LOG.error("UploadUPGServlet: getCSMInfo: did not find any files in path = {}", (Object)path);
            } else {
                for (File f : file) {
                    if ((!f.isFile() || !f.getName().startsWith("CSM_")) && !f.getName().startsWith("CSM")) continue;
                    csmFile = f;
                    break;
                }
            }
            if (csmFile != null) {
                String csmInfo = FileUtils.readFileToString(csmFile, StandardCharsets.UTF_8);
                JsonObject csmJson = new Gson().fromJson("{\"status\":\"success\"}", JsonObject.class);
                csmJson.addProperty("csminfo", csmInfo);
                return csmJson.toString();
            }
            SettingManager sm = JpaManager.getSettingManager();
            Setting list = sm.loadByKey(settingId);
            if (platform.startsWith("TPM153") || platform.startsWith("TPM181HE")) {
                path = CommonConstants.CLONE_PROCESS_LOCATION + userName + "/" + name + "/" + platform + "/MasterCloneData";
            }
            String cloneItemsIdentifiers = this.getCloneItemsIdentifiers(path);
            LOG.info("UploadUPGServlet: getCSMInfo: cloneItemsIdentifiers ={}", (Object)cloneItemsIdentifiers);
            status = "{\"status\":\"success\", \"clonename\":\"" + list.getName() + "\", \"range\":\"" + PlatformUtils.getPlatformName(list.getPlatform()) + "\", \"Clone_parts_IDs\":\"" + cloneItemsIdentifiers + "\", \"Time_uploaded\":\"" + list.getCreatedDate() + "\", \"createdBy\":\"" + list.getCreatedBy() + "\", \"updatedDate\":\"" + list.getLastUpdatedDate() + "\"}";
            return status;
        }
        catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
            status = this.failedStatus(ex.getMessage());
            return status;
        }
    }

    private String convert(String str) {
        StringBuilder ostr = new StringBuilder();
        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            if (ch >= ' ' && ch <= '~') {
                ostr.append(ch);
                continue;
            }
            ostr.append("\\u");
            String hex = Integer.toHexString(str.charAt(i) & 0xFFFF);
            for (int j = 0; j < 4 - hex.length(); ++j) {
                ostr.append("0");
            }
            ostr.append(hex.toLowerCase());
        }
        return new String(ostr);
    }
}

