/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.annotation.WebServlet
 */
package com.tpvision.smartinstall.servlet;

import com.google.gson.Gson;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.core.UiCustomizations;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.SettingManager;
import com.tpvision.smartinstall.dao.mgr.UiCustomizationsManager;
import com.tpvision.smartinstall.servlet.BaseHttpServlet;
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.TpvFileUtils;
import com.tpvision.smartinstall.util.UIJsonHelper;
import com.tpvision.smartinstall.util.Utils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(value={"/ui"})
public class UiCustomizationsServlet
extends BaseHttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(UiCustomizationsServlet.class);
    private String uiAppListPath;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mode = request.getParameter("mode");
        if ("UICUSTOMIZATIONS_INDEX".equals(mode)) {
            this.goToCustomizationsModifyPage(request, response);
        } else if ("RENAME_UICUSTOMIZATIONS".equals(mode)) {
            this.renameUiCustomizations(request, response);
        } else if ("GET_UICUSTOMIZATIONS_LIST".equals(mode)) {
            this.getUiCustomazitionsList(request, response);
        } else if ("ASSIGN_UICUSTOMIZATIONS".equals(mode)) {
            this.assignUiCustomizations(request, response);
        } else if ("COPY_UICUSTOMIZATIONS".equals(mode)) {
            this.copyUiCustomizations(request, response);
        } else if ("DELETE_UICUSTOMIZATIONS".equals(mode)) {
            this.deleteUiCustomizations(request);
        } else if ("SAVE_DATA".equals(mode)) {
            this.saveData(request, response);
        } else if ("UPLOAD_BACKGROUND".equals(mode)) {
            this.uploadBackground(request, response);
        } else if ("UPLOAD_BACKGROUND_AUDIO".equals(mode)) {
            this.uploadBackgroundAudio(request, response);
        } else if ("UPLOAD_ICON".equals(mode)) {
            this.uploadIcon(request, response);
        } else if ("SHOWBACKGROUNDLIST".equals(mode)) {
            this.showBackgroundList(request, response);
        } else if ("SHOWBACKGROUNDAUDIOLIST".equals(mode)) {
            this.showBackgroundAudioList(request, response);
        } else if ("SHOWICONLIST".equals(mode)) {
            this.showIconList(request, response);
        } else if ("ADD_UICUSTOMIZATIONS".equals(mode)) {
            this.addUiCustomizations(request, response);
        } else if ("SAVE_BACKGROUND".equals(mode)) {
            this.saveBackground(request, response);
        } else if ("SAVE_BACKGROUND_AUDIO".equals(mode)) {
            this.saveBackgroundAudio(request, response);
        } else if ("SAVE_ICON".equals(mode)) {
            this.saveIcon(request, response);
        } else if ("UPLOAD_LOGO".equals(mode)) {
            this.uploadPSHotelLogo(request, response);
        } else if ("SAVE_LOGO".equals(mode)) {
            this.updatePSHotelLogo(request, response);
        } else if ("DELETE_LOGO".equals(mode)) {
            this.deletePSHotelLogo(request, response);
        } else if ("LOGO_GALLERY".equals(mode)) {
            this.getPSLogoGallery(response);
        } else if ("UPLOAD_SHARING".equals(mode)) {
            this.uploadSharing(request, response);
        } else if ("SHOWSHARINGLIST".equals(mode)) {
            this.showSharingList(request, response);
        } else if ("SAVE_SHARING".equals(mode)) {
            this.saveSharing(request, response);
        } else if ("DELETE_BACKGROUND".equals(mode)) {
            this.deleteBackground(request, response);
        } else if ("DELETE_BACKGROUND_AUDIO".equals(mode)) {
            this.deleteBackgroundAudio(request, response);
        } else if ("DELETE_SHARING".equals(mode)) {
            this.deleteSharing(request, response);
        } else if ("DELETE_UILOGO".equals(mode)) {
            this.deleteIcon(request, response);
        } else if ("SHOWRECOMMENDEDAPPLIST".equals(mode)) {
            this.showRecommendedAppList(request, response);
        } else if ("SAVE_RECOMMENDED_DATA".equals(mode)) {
            this.saveRecommendedData(request, response);
        } else if ("DOWNLOAD_AUDIO".equals(mode)) {
            this.downloadAudio(request, response);
        } else {
            LOG.warn("not supported mode:{}", (Object)mode);
            this.responseJSON(this.failedStatus("not supported mode"), response);
        }
    }

    private void deleteBackground(HttpServletRequest request, HttpServletResponse response) {
        String status = this.successStatus();
        String imageId = request.getParameter("picName");
        int uiId = Integer.parseInt(request.getParameter("id"));
        FileUtils.deleteQuietly(new File(this.getUiCloneBackgroundDirPath(uiId) + imageId));
        FileUtils.deleteQuietly(new File(this.getContextBackgroundDirPath() + imageId));
        this.responseJSON(status, response);
    }

    private void deleteBackgroundAudio(HttpServletRequest request, HttpServletResponse response) {
        String status = this.successStatus();
        String audioId = request.getParameter("audioName");
        int uiId = Integer.parseInt(request.getParameter("id"));
        FileUtils.deleteQuietly(new File(this.getUiCloneBackgroundAudioDirPath(uiId) + audioId));
        FileUtils.deleteQuietly(new File(this.getContextBackgroundAudioDirPath() + audioId));
        this.responseJSON(status, response);
    }

    private void deleteIcon(HttpServletRequest request, HttpServletResponse response) {
        String status = this.successStatus();
        String imageId = request.getParameter("picName");
        int uiId = Integer.parseInt(request.getParameter("id"));
        FileUtils.deleteQuietly(new File(this.getUiCloneIconDirPath(uiId) + imageId));
        FileUtils.deleteQuietly(new File(this.getContextIconDirPath() + imageId));
        this.responseJSON(status, response);
    }

    private void deleteSharing(HttpServletRequest request, HttpServletResponse response) {
        String status = this.successStatus();
        String imageId = request.getParameter("picName");
        int uiId = Integer.parseInt(request.getParameter("id"));
        FileUtils.deleteQuietly(new File(this.getUiCloneSharingDirPath(uiId) + imageId));
        FileUtils.deleteQuietly(new File(this.getContextSharingDirPath() + imageId));
        this.responseJSON(status, response);
    }

    private void deletePSHotelLogo(HttpServletRequest request, HttpServletResponse response) {
        String status = this.successStatus();
        String imageId = request.getParameter("imageId");
        FileUtils.deleteQuietly(new File(CommonConstants.UI_LOGO_IMG_LOCATION + imageId));
        FileUtils.deleteQuietly(new File(CommonConstants.servletContextPath + "/static/images/uiCustomizations/logo/" + imageId));
        this.responseJSON(status, response);
    }

    private void updatePSHotelLogo(HttpServletRequest request, HttpServletResponse response) {
        String json = "";
        try {
            int uiId = Integer.parseInt(request.getParameter("uiId"));
            String imageId = request.getParameter("imageId");
            File newLogo = new File(CommonConstants.UI_LOGO_IMG_LOCATION + imageId);
            if (!newLogo.exists()) {
                throw new IOException("hotel logo file not exists," + newLogo.getName());
            }
            UiCustomizations uiCustomizations = JpaManager.getUiCustomizationsManager().loadByKey(uiId);
            if (uiCustomizations == null || !PlatformUtils.isSupportHotelImage(uiCustomizations)) {
                LOG.error("not support logo,id={}", (Object)uiId);
                return;
            }
            File logoDir = Utils.getUILogoPath(uiId);
            if (!logoDir.exists()) {
                logoDir.mkdirs();
            }
            FileUtils.cleanDirectory(logoDir);
            String targetFileName = "logo." + FilenameUtils.getExtension(newLogo.getName());
            FileUtils.copyFile(newLogo, new File(logoDir.getAbsolutePath() + "/" + targetFileName));
            this.refreshUiCustomizationsLastEditTime(uiId);
            json = this.successStatus();
        }
        catch (IOException | NumberFormatException e) {
            LOG.error(e.getMessage(), e);
            json = this.failedStatus(e.getMessage());
        }
        this.responseJSON(json, response);
    }

    private void getPSLogoGallery(HttpServletResponse response) {
        String status = null;
        try {
            JSONArray array = new JSONArray();
            File file = new File(CommonConstants.UI_LOGO_IMG_LOCATION);
            File[] logos = file.listFiles();
            if (logos != null) {
                for (File logo : logos) {
                    if (logo.isDirectory() || !CommonConstants.SUPPORTED_LOGO_FORMAT.contains(FilenameUtils.getExtension(logo.getName()))) continue;
                    JSONObject obj = new JSONObject();
                    obj.put("fileName", logo.getName());
                    obj.put("imgSize", logo.length());
                    obj.put("imgResolution", TpvFileUtils.getImageResolution(logo));
                    array.put(obj);
                }
            }
            status = this.successStatus(array);
        }
        catch (NumberFormatException e) {
            LOG.error(e.getMessage(), e);
            status = this.failedStatus(e.getMessage());
        }
        this.responseJSON(status, response);
    }

    private void uploadPSHotelLogo(HttpServletRequest request, HttpServletResponse response) {
        String status = this.successStatus();
        try {
            List<File> uploads = this.extractUploadedFiles(request, false);
            for (File logo : uploads) {
                String ext = FilenameUtils.getExtension(logo.getName()).toLowerCase();
                if (CommonConstants.SUPPORTED_LOGO_FORMAT.contains(ext)) {
                    Utils.deployImgFileToSIServiceDir(logo, ext);
                    continue;
                }
                throw new IOException("must upload image format file");
            }
        }
        catch (Exception ex) {
            status = this.failedStatus(ex.getMessage());
        }
        this.responseJSON(status, response);
    }

    private void saveIcon(HttpServletRequest request, HttpServletResponse response) {
        String iconName = request.getParameter("iconName");
        int uiId = Integer.parseInt(request.getParameter("id"));
        String iconLogoDirPath = this.getUiCloneIconDirPath(uiId);
        File destDir = new File(iconLogoDirPath);
        if (destDir.exists()) {
            try {
                FileUtils.cleanDirectory(destDir);
            }
            catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        if (StringUtils.isNotBlank(iconName)) {
            File destIconFile = new File(iconLogoDirPath + "/" + iconName);
            try {
                File selectedIconFile = new File(CommonConstants.servletContextPath + "/static/images/uiCustomizations/icon/" + iconName);
                FileUtils.copyFile(selectedIconFile, destIconFile);
                this.refreshUiCustomizationsLastEditTime(uiId);
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    private void saveSharing(HttpServletRequest request, HttpServletResponse response) {
        String sharingName = request.getParameter("sharingName");
        int uiId = Integer.parseInt(request.getParameter("id"));
        String sharingLogoDirPath = this.getUiCloneSharingDirPath(uiId);
        File destDir = new File(sharingLogoDirPath);
        if (destDir.exists()) {
            try {
                FileUtils.cleanDirectory(destDir);
            }
            catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        if (StringUtils.isNotBlank(sharingName)) {
            File destSharingFile = new File(sharingLogoDirPath + "/" + sharingName);
            try {
                File selectedSharingFile = new File(CommonConstants.servletContextPath + "/static/images/uiCustomizations/sharing_background/" + sharingName);
                FileUtils.copyFile(selectedSharingFile, destSharingFile);
                this.refreshUiCustomizationsLastEditTime(uiId);
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    private String getUiCloneSharingDirPath(int uiId) {
        return CommonConstants.CLONE_PROCESS_LOCATION + "uiCustomizations/" + uiId + "/PhilipsHome/files/images/sharing_background/";
    }

    private String getUiCloneIconDirPath(int uiId) {
        return CommonConstants.CLONE_PROCESS_LOCATION + "uiCustomizations/" + uiId + "/PhilipsHome/files/images/hotel_logo/";
    }

    private String getUiCloneBackgroundDirPath(int uiId) {
        return CommonConstants.CLONE_PROCESS_LOCATION + "uiCustomizations/" + uiId + "/PhilipsHome/files/images/main_background/";
    }

    private String getUiCloneBackgroundAudioDirPath(int uiId) {
        return CommonConstants.CLONE_PROCESS_LOCATION + "uiCustomizations/" + uiId + "/PhilipsHome/files/audio/";
    }

    private String getContextIconDirPath() {
        return CommonConstants.servletContextPath + "/static/images/uiCustomizations/icon/";
    }

    private String getContextBackgroundDirPath() {
        return CommonConstants.servletContextPath + "/static/images/uiCustomizations/background/";
    }

    private String getContextSharingDirPath() {
        return CommonConstants.servletContextPath + "/static/images/uiCustomizations/sharing_background/";
    }

    private String getContextBackgroundAudioDirPath() {
        return CommonConstants.UI_AUDIO_LOCATION;
    }

    private String getUiAppListPath(int uiId) {
        return CommonConstants.CLONE_PROCESS_LOCATION + "uiCustomizations/" + uiId + "/PhilipsHome/AppList.xml";
    }

    private void saveBackground(HttpServletRequest request, HttpServletResponse response) {
        String backgroundName = request.getParameter("backgroundName");
        int uiId = Integer.parseInt(request.getParameter("id"));
        String bgImgDir = this.getUiCloneBackgroundDirPath(uiId);
        File destDir = new File(bgImgDir);
        if (destDir.exists()) {
            try {
                FileUtils.cleanDirectory(destDir);
            }
            catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        if (StringUtils.isNotBlank(backgroundName)) {
            File destBgImg = new File(bgImgDir + "/" + backgroundName);
            try {
                File sourceBgImg = new File(this.getContextBackgroundDirPath() + backgroundName);
                FileUtils.copyFile(sourceBgImg, destBgImg);
                this.refreshUiCustomizationsLastEditTime(uiId);
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    private void saveBackgroundAudio(HttpServletRequest request, HttpServletResponse response) {
        String backgroundAudioName = request.getParameter("backgroundAudioName");
        int uiId = Integer.parseInt(request.getParameter("id"));
        String audioDir = this.getUiCloneBackgroundAudioDirPath(uiId);
        File destDir = new File(audioDir);
        if (destDir.exists()) {
            try {
                FileUtils.cleanDirectory(destDir);
            }
            catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        if (StringUtils.isNotBlank(backgroundAudioName)) {
            File destAudio = new File(audioDir + "/" + backgroundAudioName);
            try {
                File sourceAudio = new File(this.getContextBackgroundAudioDirPath() + backgroundAudioName);
                FileUtils.copyFile(sourceAudio, destAudio);
                this.refreshUiCustomizationsLastEditTime(uiId);
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    private void refreshUiCustomizationsLastEditTime(int uiId) {
        UiCustomizationsManager uiCustomizationsManager = JpaManager.getUiCustomizationsManager();
        UiCustomizations uiCustomizations = uiCustomizationsManager.loadByKey(uiId);
        uiCustomizations.setLastEdit(TpvDateUtils.getCurrentIndentifierFormatTime());
        uiCustomizationsManager.save(uiCustomizations);
    }

    private void addUiCustomizations(HttpServletRequest request, HttpServletResponse response) {
        String platform = request.getParameter("platform");
        String status = "{\"status\":\"success\"}";
        UiCustomizationsManager uiCustomizationsManager = JpaManager.getUiCustomizationsManager();
        UiCustomizations uiCustomizations = new UiCustomizations();
        uiCustomizations.setName("UI_" + TpvDateUtils.getCloneDateTimeName());
        uiCustomizations.setValue("{}");
        uiCustomizations.setPlatform(platform);
        uiCustomizationsManager.save(uiCustomizations);
        response.setContentType("text/json");
        try {
            IOUtils.write(status.getBytes(), (OutputStream)response.getOutputStream());
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void showBackgroundList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int uiId = Integer.parseInt(request.getParameter("id"));
        String uiCloneBackgroundPath = this.getUiCloneBackgroundDirPath(uiId);
        String contextBackgroundPath = this.getContextBackgroundDirPath();
        JSONArray backgroundList = this.getPicJsonList(contextBackgroundPath, uiCloneBackgroundPath);
        Utils.writeToResponse(backgroundList.toString(), "text/json;charset=UTF-8", response);
    }

    private void showBackgroundAudioList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int uiId = Integer.parseInt(request.getParameter("id"));
        String uiCloneBackgroundAudioPath = this.getUiCloneBackgroundAudioDirPath(uiId);
        String contextBackgroundAudioPath = this.getContextBackgroundAudioDirPath();
        JSONArray backgroundAudioList = this.getAudioJsonList(contextBackgroundAudioPath, uiCloneBackgroundAudioPath);
        Utils.writeToResponse(backgroundAudioList.toString(), "text/json;charset=UTF-8", response);
    }

    private void showIconList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int uiId = Integer.parseInt(request.getParameter("id"));
        String uiCloneIconPath = this.getUiCloneIconDirPath(uiId);
        String contextIConPath = this.getContextIconDirPath();
        JSONArray iconList = this.getPicJsonList(contextIConPath, uiCloneIconPath);
        Utils.writeToResponse(iconList.toString(), "text/json;charset=UTF-8", response);
    }

    private void showSharingList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int uiId = Integer.parseInt(request.getParameter("id"));
        String uiCloneIconPath = this.getUiCloneSharingDirPath(uiId);
        String contextIConPath = this.getContextSharingDirPath();
        JSONArray iconList = this.getPicJsonList(contextIConPath, uiCloneIconPath);
        Utils.writeToResponse(iconList.toString(), "text/json;charset=UTF-8", response);
    }

    private void showRecommendedAppList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int uiId = Integer.parseInt(request.getParameter("id"));
        this.uiAppListPath = this.getUiAppListPath(uiId);
        JSONArray appList = this.getAppRecommendedList();
        Utils.writeToResponse(appList.toString(), "text/json;charset=UTF-8", response);
    }

    private void saveRecommendedData(HttpServletRequest request, HttpServletResponse response) {
        String appName = request.getParameter("name");
        String type = request.getParameter("type");
        String value = request.getParameter("val");
        int uiId = Integer.parseInt(request.getParameter("id"));
        LOG.info("update type:{}, appName:{},val:{}", type, appName, value);
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(new File(this.uiAppListPath));
            List<Element> list = document.getRootElement().elements();
            for (Element e : list) {
                if (!e.attributeValue("name").equalsIgnoreCase(appName)) continue;
                if ("1".equalsIgnoreCase(type)) {
                    e.addAttribute("RecommendedApp", value);
                    break;
                }
                if (!"2".equalsIgnoreCase(type)) break;
                e.addAttribute("AppRecommendation", value);
                break;
            }
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            XMLWriter writer = new XMLWriter(new FileOutputStream(this.uiAppListPath), format);
            writer.write(document);
            writer.close();
            this.refreshUiCustomizationsLastEditTime(uiId);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private JSONArray getAppRecommendedList() {
        JSONArray appListArray = new JSONArray();
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(new File(this.uiAppListPath));
            List<Element> list = document.getRootElement().elements();
            for (Element e : list) {
                JSONObject appObj = new JSONObject();
                appObj.put("name", e.attributeValue("name"));
                if ("true".equalsIgnoreCase(e.attributeValue("RecommendedApp"))) {
                    appObj.put("recommendedApp", "checked");
                } else {
                    appObj.put("recommendedApp", "");
                }
                if ("true".equalsIgnoreCase(e.attributeValue("AppRecommendation"))) {
                    appObj.put("appRecommendation", "checked");
                } else {
                    appObj.put("appRecommendation", "");
                }
                appListArray.put(appObj);
            }
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return appListArray;
    }

    private JSONArray getPicJsonList(String contextPicPath, String uiCloneFilePath) throws IOException {
        File uiCloneDir = new File(uiCloneFilePath);
        File[] currentUiPics = uiCloneDir.listFiles();
        File contextPicDir = new File(contextPicPath);
        ArrayList<String> selectedPicNames = new ArrayList<String>();
        if (currentUiPics != null) {
            for (File pic : currentUiPics) {
                File matchContextFile = new File(contextPicPath + pic.getName());
                if (matchContextFile.exists()) {
                    if (!DigestUtils.md5Hex(FileUtils.readFileToByteArray(pic)).equals(DigestUtils.md5Hex(FileUtils.readFileToByteArray(matchContextFile)))) {
                        String newClonePicName = UUID.randomUUID() + "." + FilenameUtils.getExtension(pic.getName());
                        File newPicFile = new File(pic.getParent() + File.separator + newClonePicName);
                        if (pic.renameTo(newPicFile)) {
                            FileUtils.copyFileToDirectory(newPicFile, contextPicDir);
                        }
                        selectedPicNames.add(newClonePicName);
                        continue;
                    }
                    selectedPicNames.add(pic.getName());
                    continue;
                }
                FileUtils.copyFileToDirectory(pic, contextPicDir);
                selectedPicNames.add(pic.getName());
            }
        }
        JSONArray picList = new JSONArray();
        File[] uiShowPics = contextPicDir.listFiles();
        if (uiShowPics != null) {
            for (File pic : uiShowPics) {
                JSONObject picObject = new JSONObject();
                picObject.put("name", pic.getName());
                picObject.put("selected", selectedPicNames.contains(pic.getName()));
                picObject.put("imgSize", pic.length());
                picObject.put("imgResolution", TpvFileUtils.getImageResolution(pic));
                picObject.put("fileExt", FilenameUtils.getExtension(pic.getName()).toUpperCase());
                picList.put(picObject);
            }
        }
        return picList;
    }

    private JSONArray getAudioJsonList(String contextAudioPath, String uiCloneFilePath) throws IOException {
        File uiCloneDir = new File(uiCloneFilePath);
        File[] currentAudioFiles = uiCloneDir.listFiles();
        File contextAudioDir = new File(contextAudioPath);
        ArrayList<String> selectedAudioNames = new ArrayList<String>();
        if (currentAudioFiles != null) {
            for (File audio : currentAudioFiles) {
                File matchContextFile = new File(contextAudioPath + audio.getName());
                if (matchContextFile.exists()) {
                    if (!DigestUtils.md5Hex(FileUtils.readFileToByteArray(audio)).equals(DigestUtils.md5Hex(FileUtils.readFileToByteArray(matchContextFile)))) {
                        String newCloneAudioName = UUID.randomUUID() + "." + FilenameUtils.getExtension(audio.getName());
                        File newAudioFile = new File(audio.getParent() + File.separator + newCloneAudioName);
                        if (audio.renameTo(newAudioFile)) {
                            FileUtils.copyFileToDirectory(newAudioFile, contextAudioDir);
                        }
                        selectedAudioNames.add(newCloneAudioName);
                        continue;
                    }
                    selectedAudioNames.add(audio.getName());
                    continue;
                }
                FileUtils.copyFileToDirectory(audio, contextAudioDir);
                selectedAudioNames.add(audio.getName());
            }
        }
        JSONArray audioList = new JSONArray();
        File[] uiShowAudios = contextAudioDir.listFiles();
        if (uiShowAudios != null) {
            for (File audio : uiShowAudios) {
                JSONObject audioObject = new JSONObject();
                audioObject.put("name", audio.getName());
                audioObject.put("selected", selectedAudioNames.contains(audio.getName()));
                audioObject.put("size", audio.length());
                audioObject.put("fileExt", FilenameUtils.getExtension(audio.getName()).toUpperCase());
                audioList.put(audioObject);
            }
        }
        return audioList;
    }

    private void uploadIcon(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            if (items.isEmpty()) {
                throw new RuntimeException("upload file is empty");
            }
            FileItem fileItem = items.get(0);
            String ext = FilenameUtils.getExtension(fileItem.getName());
            String imageName = UUID.randomUUID() + "." + ext;
            File destIcon = new File(this.getContextIconDirPath() + imageName);
            fileItem.write(destIcon);
            Utils.renderSuccessJsonData(response);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            Utils.renderErrorJsonMsg(e.getMessage(), response);
        }
    }

    private void uploadBackground(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            if (items.isEmpty()) {
                throw new RuntimeException("upload file is empty");
            }
            FileItem fileItem = items.get(0);
            String ext = FilenameUtils.getExtension(fileItem.getName());
            String imageName = UUID.randomUUID() + "." + ext;
            File destBackground = new File(this.getContextBackgroundDirPath() + imageName);
            fileItem.write(destBackground);
            Utils.renderSuccessJsonData(response);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            Utils.renderErrorJsonMsg(e.getMessage(), response);
        }
    }

    private void uploadBackgroundAudio(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            if (items.isEmpty()) {
                throw new RuntimeException("upload file is empty");
            }
            FileItem fileItem = items.get(0);
            String audioName = FilenameUtils.getName(fileItem.getName());
            File destBackgroundAudio = new File(this.getContextBackgroundAudioDirPath() + audioName);
            fileItem.write(destBackgroundAudio);
            Utils.renderSuccessJsonData(response);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            Utils.renderErrorJsonMsg(e.getMessage(), response);
        }
    }

    private void uploadSharing(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            if (items.isEmpty()) {
                throw new RuntimeException("upload file is empty");
            }
            FileItem fileItem = items.get(0);
            String ext = FilenameUtils.getExtension(fileItem.getName());
            String imageName = UUID.randomUUID() + "." + ext;
            File destSharing = new File(this.getContextSharingDirPath() + imageName);
            fileItem.write(destSharing);
            Utils.renderSuccessJsonData(response);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            Utils.renderErrorJsonMsg(e.getMessage(), response);
        }
    }

    private void saveData(HttpServletRequest request, HttpServletResponse response) {
        int uiId = Integer.parseInt(request.getParameter("id"));
        UiCustomizationsManager uiCustomizationsManager = JpaManager.getUiCustomizationsManager();
        UiCustomizations uiCustomizations = uiCustomizationsManager.loadByKey(uiId);
        UIJsonHelper helper = new UIJsonHelper(new JSONObject(uiCustomizations.getValue()), uiCustomizations.getPlatform());
        helper.updateSettingValue("HighlightedTextColor", request.getParameter("HighlightedTextColor"));
        helper.updateSettingValue("NonHighlightedTextColor", request.getParameter("NonHighlightedTextColor"));
        helper.updateSettingValue("BackgroundColor", request.getParameter("BackgroundColor"));
        helper.updateSettingValue("MainBackgroundColorFilter", request.getParameter("MainBackgroundColorFilter"));
        helper.updateSettingValue("HighlightedTextColor.Alpha", request.getParameter("HighlightedTextAlpha"));
        helper.updateSettingValue("NonHighlightedTextColor.Alpha", request.getParameter("NonHighlightedtextAlpha"));
        helper.updateSettingValue("BackgroundColor.Alpha", request.getParameter("BackgroundAlpha"));
        helper.updateSettingValue("MainBackgroundColorFilter.Alpha", request.getParameter("MainBackgroundAlpha"));
        helper.updateSettingValue("ShowAccountIcon", request.getParameter("Show_icon"));
        helper.updateSettingValue("ShowAssistantIcon", request.getParameter("Show_assistant_icon"));
        helper.updateSettingValue("BackgroundPanel.Enable", request.getParameter("Enable_background"));
        helper.updateSettingValue("BackgroundAudio", request.getParameter("Show_Background_Audio"));
        helper.updateSettingValue("showPremisesName", request.getParameter("Show_Premises_Name"));
        helper.updateSettingValue("showWelcome", request.getParameter("Show_Welcome"));
        JSONObject obj = new JSONObject(helper.settingValueMap);
        try {
            String xmlFilePath = CommonConstants.CLONE_PROCESS_LOCATION + "uiCustomizations/" + uiId + "/PhilipsHome/Dashboardsettings.xml";
            SAXReader reader = new SAXReader();
            Document document = reader.read(new File(xmlFilePath));
            List<Element> list = document.getRootElement().elements();
            for (int i = 0; i < helper.settingValueMap.size(); ++i) {
                for (Element e : list) {
                    String eName = e.elementText("Name");
                    if (!helper.settingValueMap.containsKey(eName)) continue;
                    String vl = helper.settingValueMap.get(eName);
                    e.element("Value").setText(vl);
                }
            }
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            XMLWriter writer = new XMLWriter(new FileOutputStream(xmlFilePath), format);
            writer.write(document);
            writer.close();
            String uiValue = obj.toString();
            uiCustomizations.setValue(uiValue);
            uiCustomizationsManager.save(uiCustomizations);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void deleteUiCustomizations(HttpServletRequest request) {
        String id = request.getParameter("id");
        try {
            UiCustomizationsManager uiCustomizationsManager = JpaManager.getUiCustomizationsManager();
            UiCustomizations uiCustomizations = uiCustomizationsManager.loadByKey(Integer.parseInt(id));
            uiCustomizationsManager.deleteByKey(Integer.parseInt(id));
            File srcUi = new File(CommonConstants.CLONE_PROCESS_LOCATION + "uiCustomizations" + File.separator + uiCustomizations.getId());
            if (srcUi.exists()) {
                FileUtils.deleteDirectory(srcUi);
            }
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void copyUiCustomizations(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        String status = "{\"status\":\"success\"}";
        UiCustomizationsManager uiCustomizationsManager = JpaManager.getUiCustomizationsManager();
        try {
            UiCustomizations copyUiCustomizations = new UiCustomizations();
            UiCustomizations uiCustomizations = uiCustomizationsManager.loadByKey(Integer.parseInt(id));
            List<String> currentSettingPackageNames = uiCustomizationsManager.loadAll().stream().map(UiCustomizations::getName).map(String::trim).collect(Collectors.toList());
            String copyName = CloneItemUtils.getUniqueCloneName(currentSettingPackageNames, "Copy of " + uiCustomizations.getName());
            copyUiCustomizations.setName(copyName);
            copyUiCustomizations.setValue(uiCustomizations.getValue());
            copyUiCustomizations.setPlatform(uiCustomizations.getPlatform());
            copyUiCustomizations.setLastEdit(uiCustomizations.getLastEdit());
            uiCustomizationsManager.save(copyUiCustomizations);
            File srcUi = new File(CommonConstants.CLONE_PROCESS_LOCATION + "uiCustomizations" + File.separator + uiCustomizations.getId());
            File copyUi = new File(CommonConstants.CLONE_PROCESS_LOCATION + "uiCustomizations" + File.separator + copyUiCustomizations.getId());
            FileUtils.copyDirectory(srcUi, copyUi);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        response.setContentType("text/json");
        try {
            IOUtils.write(status.getBytes(), (OutputStream)response.getOutputStream());
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void assignUiCustomizations(HttpServletRequest request, HttpServletResponse response) {
        String cloneIds = request.getParameter("clone_ids");
        String[] cloneIdsArr = cloneIds.split(",");
        String uiCustomizationsId = request.getParameter("uiCustomizations_id");
        String status = "{\"status\":\"success\"}";
        SettingManager settingManager = JpaManager.getSettingManager();
        Setting setting = null;
        if ("None".equalsIgnoreCase(uiCustomizationsId)) {
            uiCustomizationsId = String.valueOf(-1);
            status = "{\"status\":\"setNone\"}";
        }
        for (int i = 0; i < cloneIdsArr.length; ++i) {
            if ("null".equals(cloneIdsArr[i])) continue;
            setting = settingManager.loadByKey(Integer.parseInt(cloneIdsArr[i]));
            setting.setUiCustomizationsId(Integer.parseInt(uiCustomizationsId));
            setting.setLastUpdatedDate(new Date());
            settingManager.save(setting);
        }
        try {
            IOUtils.write(status.getBytes(), (OutputStream)response.getOutputStream());
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void getUiCustomazitionsList(HttpServletRequest request, HttpServletResponse response) {
        String platform = request.getParameter("platform");
        List<UiCustomizations> uiCustomizations = null;
        uiCustomizations = StringUtils.isNotBlank(platform) ? JpaManager.getUiCustomizationsManager().findUiCustomizationsByPlatforms(platform) : JpaManager.getUiCustomizationsManager().loadAll();
        try {
            IOUtils.write(new Gson().toJson(uiCustomizations).getBytes(), (OutputStream)response.getOutputStream());
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void renameUiCustomizations(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String newName = request.getParameter("newName");
        String id = request.getParameter("id");
        int uiId = Integer.parseInt(id);
        UiCustomizationsManager uiCustomizationsManager = JpaManager.getUiCustomizationsManager();
        List<UiCustomizations> uiCustomizations = uiCustomizationsManager.findByName(newName);
        if (!(uiCustomizations.isEmpty() || uiCustomizations.size() == 1 && uiCustomizations.get(0).getId() == uiId)) {
            Utils.renderErrorJsonMsg("UI new name already exist", response);
            return;
        }
        UiCustomizations uiCustomization = uiCustomizationsManager.loadByKey(uiId);
        uiCustomization.setName(newName);
        uiCustomizationsManager.save(uiCustomization);
        Utils.renderSuccessJsonData(response);
    }

    private void goToCustomizationsModifyPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        UiCustomizationsManager uiCustomizationsManager = JpaManager.getUiCustomizationsManager();
        UiCustomizations uiCustomizations = uiCustomizationsManager.loadByKey(Integer.parseInt(id));
        String uiRef = null;
        if (PlatformUtils.isSupportHotelImage(uiCustomizations)) {
            request.setAttribute("uiCustomizations", uiCustomizations);
            request.setAttribute("imageMd5", this.getPSHotelLogoImageMd5FileName(uiCustomizations.getId()));
            uiRef = "/ui_logo.jsp";
        } else {
            String uiValue = uiCustomizations.getValue();
            String uiName = uiCustomizations.getName();
            String platformName = uiCustomizations.getPlatform();
            if ("{}".equals(uiValue)) {
                uiValue = this.initUiCustomizationData(uiCustomizations);
            }
            uiRef = "/UiCustomizations.jsp?id=" + id + "&value=" + uiValue + "&name=" + uiName + "&platform=" + platformName;
        }
        try {
            request.getRequestDispatcher(uiRef).forward(request, response);
        }
        catch (ServletException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private String getPSHotelLogoImageMd5FileName(int uiId) throws IOException {
        File hotelLogoPath = Utils.getUILogoPath(uiId);
        if (hotelLogoPath.exists()) {
            File[] fileList;
            for (File logo : fileList = hotelLogoPath.listFiles()) {
                String ext = FilenameUtils.getExtension(logo.getName()).toLowerCase();
                if (!CommonConstants.SUPPORTED_LOGO_FORMAT.contains(ext)) continue;
                Utils.deployImgFileToSIServiceDir(logo, ext);
                return TpvFileUtils.getImageMd5(logo) + "." + ext;
            }
        }
        return "";
    }

    private String initUiCustomizationData(UiCustomizations uiCustomizations) throws IOException {
        String uiValue = "{\"UserInterfaceSetting.SidePanel.HighlightedTextColor.Alpha\":\"100\",\"UserInterfaceSetting.SidePanel.HighlightedTextColor.Red\":\"195\",\"UserInterfaceSetting.SidePanel.HighlightedTextColor.Green\":\"74\",\"UserInterfaceSetting.SidePanel.HighlightedTextColor.Blue\":\"255\",\"UserInterfaceSetting.SidePanel.NonHighlightedTextColor.Alpha\":\"100\",\"UserInterfaceSetting.SidePanel.NonHighlightedTextColor.Red\":\"83\",\"UserInterfaceSetting.SidePanel.NonHighlightedTextColor.Green\":\"61\",\"UserInterfaceSetting.SidePanel.NonHighlightedTextColor.Blue\":\"255\",\"UserInterfaceSetting.SidePanel.BackgroundColor.Alpha\":\"100\",\"UserInterfaceSetting.SidePanel.BackgroundColor.Red\":\"143\",\"UserInterfaceSetting.SidePanel.BackgroundColor.Green\":\"255\",\"UserInterfaceSetting.SidePanel.BackgroundColor.Blue\":\"225\",\"UserInterfaceSetting.MainBackgroundPanel.MainBackgroundColorFilter.Alpha\":\"100\",\"UserInterfaceSetting.MainBackgroundPanel.MainBackgroundColorFilter.Red\":\"138\",\"UserInterfaceSetting.MainBackgroundPanel.MainBackgroundColorFilter.Green\":\"178\",\"UserInterfaceSetting.MainBackgroundPanel.MainBackgroundColorFilter.Blue\":\"255\"}";
        File uiFile = new File(CommonConstants.CLONE_PROCESS_LOCATION + "uiCustomizations/" + uiCustomizations.getId() + "/PhilipsHome/Dashboardsettings.xml");
        String uiSetting = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DefaultDashboardSettings>\r\n<item>\r\n<Name>UserInterfaceSetting.SidePanel.HighlightedTextColor.Alpha</Name>\r\n<Value>100</Value>\r\n<CloneIn>Yes</CloneIn>\r\n</item>\r\n<item>\r\n<Name>UserInterfaceSetting.SidePanel.HighlightedTextColor.Red</Name>\r\n<Value>255</Value>\r\n<CloneIn>Yes</CloneIn>\r\n</item>\r\n<item>\r\n<Name>UserInterfaceSetting.SidePanel.HighlightedTextColor.Green</Name>\r\n<Value>255</Value>\r\n<CloneIn>Yes</CloneIn>\r\n</item>\r\n<item>\r\n<Name>UserInterfaceSetting.SidePanel.HighlightedTextColor.Blue</Name>\r\n<Value>255</Value>\r\n<CloneIn>Yes</CloneIn>\r\n</item>\r\n<item>\r\n<Name>UserInterfaceSetting.SidePanel.NonHighlightedTextColor.Alpha</Name>\r\n<Value>60</Value>\r\n<CloneIn>Yes</CloneIn>\r\n</item>\r\n<item>\r\n<Name>UserInterfaceSetting.SidePanel.NonHighlightedTextColor.Red</Name>\r\n<Value>255</Value>\r\n<CloneIn>Yes</CloneIn>\r\n</item>\r\n<item>\r\n<Name>UserInterfaceSetting.SidePanel.NonHighlightedTextColor.Green</Name>\r\n<Value>255</Value>\r\n<CloneIn>Yes</CloneIn>\r\n</item>\r\n<item>\r\n<Name>UserInterfaceSetting.SidePanel.NonHighlightedTextColor.Blue</Name>\r\n<Value>255</Value>\r\n<CloneIn>Yes</CloneIn>\r\n</item>\r\n<item>\r\n<Name>UserInterfaceSetting.SidePanel.BackgroundColor.Alpha</Name>\r\n<Value>100</Value>\r\n<CloneIn>Yes</CloneIn>\r\n</item>\r\n<item>\r\n<Name>UserInterfaceSetting.SidePanel.BackgroundColor.Red</Name>\r\n<Value>0</Value>\r\n<CloneIn>Yes</CloneIn>\r\n</item>\r\n<item>\r\n<Name>UserInterfaceSetting.SidePanel.BackgroundColor.Green</Name>\r\n<Value>52</Value>\r\n<CloneIn>Yes</CloneIn>\r\n</item>\r\n<item>\r\n<Name>UserInterfaceSetting.SidePanel.BackgroundColor.Blue</Name>\r\n<Value>120</Value>\r\n<CloneIn>Yes</CloneIn>\r\n</item>\r\n<item>\r\n<Name>UserInterfaceSetting.MainBackgroundPanel.MainBackgroundColorFilter.Alpha</Name>\r\n<Value>0</Value>\r\n<CloneIn>Yes</CloneIn>\r\n</item>\r\n<item>\r\n<Name>UserInterfaceSetting.MainBackgroundPanel.MainBackgroundColorFilter.Red</Name>\r\n<Value>44</Value>\r\n<CloneIn>Yes</CloneIn>\r\n</item>\r\n<item>\r\n<Name>UserInterfaceSetting.MainBackgroundPanel.MainBackgroundColorFilter.Green</Name>\r\n<Value>49</Value>\r\n<CloneIn>Yes</CloneIn>\r\n</item>\r\n<item>\r\n<Name>UserInterfaceSetting.MainBackgroundPanel.MainBackgroundColorFilter.Blue</Name>\r\n<Value>55</Value>\r\n<CloneIn>Yes</CloneIn>\r\n</item>\r\n<item>\r\n<Name>UserInterfaceSetting.ShowAccountIcon</Name>\r\n<Value>true</Value>\r\n<CloneIn>Yes</CloneIn>\r\n</item>\r\n<item>\r\n<Name>UserInterfaceSetting.ShowAssistantIcon</Name>\r\n<Value>true</Value>\r\n<CloneIn>Yes</CloneIn>\r\n</item>\r\n<item>\r\n<Name>UserInterfaceSetting.BackgroundPanel.Enable</Name>\r\n<Value>true</Value>\r\n<CloneIn>Yes</CloneIn>\r\n</item>\r\n</DefaultDashboardSettings>\r\n";
        uiFile.getParentFile().mkdirs();
        FileUtils.write(uiFile, (CharSequence)uiSetting, StandardCharsets.UTF_8);
        return uiValue;
    }

    private void downloadAudio(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        if (name == null || name.isEmpty()) {
            response.sendError(400, "Missing audio name");
            return;
        }
        if (name.contains("..") || name.contains("/") || name.contains("\\")) {
            response.sendError(400, "Invalid audio name");
            return;
        }
        String AUDIO_BASE_PATH = CommonConstants.UI_AUDIO_LOCATION;
        File audioFile = new File(AUDIO_BASE_PATH, name);
        if (!audioFile.exists() || !audioFile.isFile()) {
            response.sendError(404, "Audio file not found");
            return;
        }
        response.setContentType("audio/mpeg");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0L);
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(audioFile));
             BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());){
            int len;
            byte[] buffer = new byte[8192];
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        }
    }
}

