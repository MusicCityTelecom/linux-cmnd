/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.servlet;

import com.google.gson.Gson;
import com.tpvision.smartinstall.dao.core.Banners;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.mgr.BannersManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.SettingManager;
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.TpvFileUtils;
import com.tpvision.smartinstall.util.Utils;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
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
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value={"/banners"})
public class BannersServlet {
    private static final Logger LOG = LoggerFactory.getLogger(BannersServlet.class);
    private static final String SUCCESS = "{\"status\":\"success\"}";
    private static final String FAIL = "{\"status\":\"fail\"}";
    private static final String SET_NONE = "{\"status\":\"setNone\"}";

    @GetMapping(value={"index"})
    public void index(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        BannersManager bannersManager = JpaManager.getBannersManager();
        Banners banners = bannersManager.loadByKey(Integer.parseInt(id));
        request.setAttribute("banners", banners);
        String bannersRef = "/jsp/banner/banner_" + banners.getType().toLowerCase() + ".jsp";
        try {
            request.getRequestDispatcher(bannersRef).forward(request, response);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @PostMapping(value={"add"})
    public void add(String type, String platform, HttpServletResponse response) {
        Banners banners = new Banners();
        banners.setName("Banner_" + TpvDateUtils.getCloneDateTimeName());
        banners.setType(type);
        banners.setPlatform(platform);
        if (StringUtils.equalsIgnoreCase(type, "Emergency")) {
            banners.setContent("Evacuation");
        } else if (!StringUtils.equalsIgnoreCase(type, "Commercial")) {
            if (StringUtils.equalsIgnoreCase(type, "Survey")) {
                banners.setContent("How would you rate your stay?");
                banners.setTriggers("at_each_start_up");
            } else {
                Utils.renderErrorJsonMsg("banner type error", response);
                return;
            }
        }
        JpaManager.getBannersManager().save(banners);
        Utils.renderSuccessJsonData(response);
    }

    @PostMapping(value={"delete"})
    public void delete(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String status = SUCCESS;
        BannersManager bannersManager = JpaManager.getBannersManager();
        Banners banners = new Banners();
        try {
            banners = bannersManager.loadByKey(Integer.parseInt(id));
            bannersManager.deleteByKey(Integer.parseInt(id));
            File destDirBanners = new File(CommonConstants.CLONE_PROCESS_LOCATION + "Banners/" + banners.getId());
            FileUtils.deleteQuietly(destDirBanners);
        }
        catch (Exception e) {
            status = FAIL;
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

    @PostMapping(value={"copy"})
    public void copy(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String status = SUCCESS;
        BannersManager bannersManager = JpaManager.getBannersManager();
        Banners copySrc = new Banners();
        Banners copyDest = new Banners();
        try {
            copySrc = bannersManager.loadByKey(Integer.parseInt(id));
            List<String> currentBannersNames = bannersManager.loadAll().stream().map(Banners::getName).map(String::trim).collect(Collectors.toList());
            String copyName = CloneItemUtils.getUniqueCloneName(currentBannersNames, "Copy of " + copySrc.getName());
            copyDest.setName(copyName);
            copyDest.setPlatform(copySrc.getPlatform());
            copyDest.setType(copySrc.getType());
            copyDest.setContent(copySrc.getContent());
            copyDest.setSchedule(copySrc.getSchedule());
            copyDest.setTriggers(copySrc.getTriggers());
            copyDest.setResponse(copySrc.getResponse());
            copyDest.setLastEdit(copySrc.getLastEdit());
            bannersManager.save(copyDest);
            File destDirBanners = new File(CommonConstants.CLONE_PROCESS_LOCATION + "Banners/" + copySrc.getId());
            File newestDirBanners = new File(CommonConstants.CLONE_PROCESS_LOCATION + "Banners/" + copyDest.getId());
            FileUtils.copyDirectory(destDirBanners, newestDirBanners);
        }
        catch (Exception e) {
            status = FAIL;
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

    @PostMapping(value={"rename"})
    public void rename(HttpServletRequest request, HttpServletResponse response) {
        String newName = request.getParameter("newName");
        String id = request.getParameter("id");
        String status = SUCCESS;
        BannersManager bannersManager = JpaManager.getBannersManager();
        try {
            int bannersId = Integer.parseInt(id);
            List<Banners> banners = bannersManager.findByName(newName);
            if (banners.isEmpty() || banners.size() == 1 && banners.get(0).getId() == bannersId) {
                Banners banner = bannersManager.loadByKey(bannersId);
                banner.setName(newName);
                bannersManager.save(banner);
            } else {
                status = "{\"status\":\"fail\",\"errorCode\":\"0\"}";
            }
        }
        catch (Exception e) {
            status = FAIL;
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

    @PostMapping(value={"saveEmail"})
    public void saveEmail(HttpServletRequest request, HttpServletResponse response) {
        String bannersResponse = request.getParameter("response");
        String id = request.getParameter("id");
        BannersManager bannersManager = JpaManager.getBannersManager();
        Banners banners = bannersManager.loadByKey(Integer.parseInt(id));
        banners.setResponse(bannersResponse);
        bannersManager.save(banners);
        Gson gson = new Gson();
        String status = "{\"status\":\"success\",\"response\":" + gson.toJson(bannersResponse) + "}";
        response.setContentType("text/json");
        try {
            IOUtils.write(status.getBytes(), (OutputStream)response.getOutputStream());
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @PostMapping(value={"saveContent"})
    public void saveContent(HttpServletRequest request, HttpServletResponse response) {
        String contentValue = request.getParameter("contentValue");
        String id = request.getParameter("id");
        BannersManager bannersManager = JpaManager.getBannersManager();
        Banners banners = bannersManager.loadByKey(Integer.parseInt(id));
        banners.setContent(contentValue);
        bannersManager.save(banners);
        Utils.renderSuccessJsonData(response);
    }

    @PostMapping(value={"saveTrigger"})
    public void saveTrigger(HttpServletRequest request, HttpServletResponse response) {
        String triggersValue = request.getParameter("triggersValue");
        String id = request.getParameter("id");
        BannersManager bannersManager = JpaManager.getBannersManager();
        Banners banners = bannersManager.loadByKey(Integer.parseInt(id));
        banners.setTriggers(triggersValue);
        bannersManager.save(banners);
        Utils.renderSuccessJsonData(response);
    }

    @GetMapping(value={"assignList"})
    public void getAssignBannersList(HttpServletResponse response) {
        try {
            IOUtils.write(new Gson().toJson(JpaManager.getBannersManager().loadAll()).getBytes(), (OutputStream)response.getOutputStream());
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @PostMapping(value={"assign"})
    public void assign(HttpServletRequest request, HttpServletResponse response) {
        String cloneIds = request.getParameter("clone_ids");
        String[] cloneIdsArr = cloneIds.split(",");
        String bannersId = request.getParameter("banners_id");
        String status = SUCCESS;
        SettingManager settingManager = JpaManager.getSettingManager();
        Setting setting = null;
        try {
            if ("None".equalsIgnoreCase(bannersId)) {
                bannersId = String.valueOf(-1);
                status = SET_NONE;
            }
            for (int i = 0; i < cloneIdsArr.length; ++i) {
                if ("null".equals(cloneIdsArr[i])) continue;
                setting = settingManager.loadByKey(Integer.parseInt(cloneIdsArr[i]));
                setting.setBannersId(Integer.parseInt(bannersId));
                setting.setLastUpdatedDate(new Date());
                settingManager.save(setting);
            }
        }
        catch (Exception e) {
            status = FAIL;
            LOG.error(e.getMessage(), e);
        }
        try {
            IOUtils.write(status.getBytes(), (OutputStream)response.getOutputStream());
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @GetMapping(value={"emergencyPics"})
    public void emergencyPics(HttpServletRequest request, HttpServletResponse response) {
        File emergencyFolder = new File(CommonConstants.CLONE_BANNER_EMERGENCY_IMAGE_LOATION);
        JSONArray picList = new JSONArray();
        File[] uiShowPics = emergencyFolder.listFiles();
        if (uiShowPics != null) {
            for (File pic : uiShowPics) {
                JSONObject picObject = new JSONObject();
                picObject.put("name", pic.getName());
                picObject.put("imgSize", TpvFileUtils.convertFileSizeToString(pic.length()));
                picObject.put("imgResolution", TpvFileUtils.getImageResolution(pic));
                picObject.put("fileExt", FilenameUtils.getExtension(pic.getName()).toUpperCase());
                picList.put(picObject);
            }
        }
        Utils.renderSuccessJsonData(picList, response);
    }

    @PostMapping(value={"uploadEmergencyPic"})
    public void uploadEmergencyPic(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            if (items.isEmpty()) {
                Utils.renderErrorJsonMsg("upload file is empty", response);
                return;
            }
            FileItem fileItem = items.get(0);
            String ext = FilenameUtils.getExtension(fileItem.getName().toLowerCase());
            String imageName = DigestUtils.md5Hex(fileItem.get()) + "." + ext;
            File destBackground = new File(CommonConstants.CLONE_BANNER_EMERGENCY_IMAGE_LOATION + imageName);
            if (destBackground.exists()) {
                Utils.renderErrorJsonMsg("upload file already exist", response);
                return;
            }
            fileItem.write(destBackground);
            Utils.renderSuccessJsonData(response);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            Utils.renderErrorJsonMsg(e.getMessage(), response);
        }
    }

    @PostMapping(value={"deleteEmergencyPic"})
    public void deleteEmergencyPic(HttpServletRequest request, HttpServletResponse response) {
        String picName = request.getParameter("picName");
        File deleteImage = new File(CommonConstants.CLONE_BANNER_EMERGENCY_IMAGE_LOATION + picName);
        if (!JpaManager.getBannersManager().findBannersByContentLike(picName).isEmpty()) {
            Utils.renderErrorJsonMsg("delete failure as the image still used by other banner clone", response);
            return;
        }
        if (deleteImage.isFile() && deleteImage.exists()) {
            FileUtils.deleteQuietly(deleteImage);
        }
        Utils.renderSuccessJsonData(response);
    }

    @GetMapping(value={"/showEmergencyPic"})
    public void showEmergencyPic(String name, HttpServletResponse response) {
        File showImage = new File(CommonConstants.CLONE_BANNER_EMERGENCY_IMAGE_LOATION + name);
        if (showImage.isFile() && showImage.exists()) {
            Utils.renderFileResponse(showImage, response);
        }
    }

    @PostMapping(value={"/loadContentList"})
    public void loadContentList(int bannerId, HttpServletResponse response) {
        JSONObject pageData = new JSONObject();
        Banners banners = JpaManager.getBannersManager().loadByKey(bannerId);
        if (banners != null && StringUtils.equalsIgnoreCase(banners.getType(), "Commercial")) {
            String jsonContent = Optional.ofNullable(banners.getContent()).orElse("[]");
            JSONArray jsonArray = new JSONArray(jsonContent);
            pageData.put("rows", jsonArray);
            pageData.put("current", 1);
            pageData.put("rowCount", Integer.MAX_VALUE);
            pageData.put("total", jsonArray.length());
        }
        Utils.writeJsonToResponse(pageData.toString(), response);
    }

    @GetMapping(value={"commericalPics"})
    public void commericalPics(HttpServletRequest request, HttpServletResponse response) {
        File commericialFolder = new File(CommonConstants.CLONE_BANNER_COMMERCIAL_CONTENT_IMAGE_LOATION);
        JSONArray picList = new JSONArray();
        File[] uiShowPics = commericialFolder.listFiles();
        if (uiShowPics != null) {
            for (File pic : uiShowPics) {
                JSONObject picObject = new JSONObject();
                picObject.put("name", pic.getName());
                picObject.put("imgSize", TpvFileUtils.convertFileSizeToString(pic.length()));
                picObject.put("imgResolution", TpvFileUtils.getImageResolution(pic));
                picObject.put("fileExt", FilenameUtils.getExtension(pic.getName()).toUpperCase());
                picList.put(picObject);
            }
        }
        Utils.renderSuccessJsonData(picList, response);
    }

    @PostMapping(value={"uploadCommericalPic"})
    public void uploadCommericalPic(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            if (items.isEmpty()) {
                Utils.renderErrorJsonMsg("upload file is empty", response);
                return;
            }
            FileItem fileItem = items.get(0);
            String ext = FilenameUtils.getExtension(fileItem.getName().toLowerCase());
            String imageName = DigestUtils.md5Hex(fileItem.get()) + "." + ext;
            File destBackground = new File(CommonConstants.CLONE_BANNER_COMMERCIAL_CONTENT_IMAGE_LOATION + imageName);
            if (destBackground.exists()) {
                Utils.renderErrorJsonMsg("upload file already exist", response);
                return;
            }
            fileItem.write(destBackground);
            Utils.renderSuccessJsonData(response);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            Utils.renderErrorJsonMsg(e.getMessage(), response);
        }
    }

    @PostMapping(value={"deleteCommericalPic"})
    public void deleteCommericalPic(HttpServletRequest request, HttpServletResponse response) {
        String picName = request.getParameter("picName");
        File deleteImage = new File(CommonConstants.CLONE_BANNER_COMMERCIAL_CONTENT_IMAGE_LOATION + picName);
        if (!JpaManager.getBannersManager().findBannersByContentLike(picName).isEmpty()) {
            Utils.renderErrorJsonMsg("delete failure as the image still used by other banner content", response);
            return;
        }
        if (deleteImage.isFile() && deleteImage.exists()) {
            FileUtils.deleteQuietly(deleteImage);
        }
        Utils.renderSuccessJsonData(response);
    }

    @GetMapping(value={"/showCommericalPic"})
    public void showCommericalPic(String name, HttpServletResponse response) {
        File showImage = new File(CommonConstants.CLONE_BANNER_COMMERCIAL_CONTENT_IMAGE_LOATION + name);
        if (showImage.isFile() && showImage.exists()) {
            Utils.renderFileResponse(showImage, response);
        }
    }

    @PostMapping(value={"/updateCommericalContent"})
    public void updateCommericalContent(int bannerId, long contentId, String content, boolean updateVersionTag, HttpServletResponse response) {
        Banners banners = JpaManager.getBannersManager().loadByKey(bannerId);
        if (banners != null && StringUtils.equalsIgnoreCase(banners.getType(), "Commercial")) {
            String jsonContent = Optional.ofNullable(banners.getContent()).orElse("[]");
            JSONArray jsonArray = new JSONArray(jsonContent);
            int index = this.getContentIndexById(jsonArray, contentId);
            if (index == -1) {
                Utils.renderErrorJsonMsg("update content not found", response);
                return;
            }
            JSONObject newContentJson = new JSONObject(content);
            if (updateVersionTag) {
                newContentJson.put("version_tag", System.currentTimeMillis());
            }
            jsonArray.put(index, newContentJson);
            banners.setContent(jsonArray.toString());
            JpaManager.getBannersManager().save(banners);
            Utils.renderSuccessJsonData(response);
        }
        Utils.renderErrorJsonMsg("commerical banner not found", response);
    }

    private int getContentIndexById(JSONArray jsonArray, long contentId) {
        int j = jsonArray.length();
        for (int i = 0; i < j; ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getLong("id") != contentId) continue;
            return i;
        }
        return -1;
    }

    @PostMapping(value={"/addCommericalContent"})
    public void addCommericalContent(int bannerId, HttpServletResponse response) {
        Banners banners = JpaManager.getBannersManager().loadByKey(bannerId);
        if (banners != null && StringUtils.equalsIgnoreCase(banners.getType(), "Commercial")) {
            String jsonContent = Optional.ofNullable(banners.getContent()).orElse("[]");
            JSONArray jsonArray = new JSONArray(jsonContent);
            JSONObject newContent = new JSONObject();
            newContent.put("id", System.currentTimeMillis());
            newContent.put("content", new JSONObject("{type:\"image\", properties:{src:\"\",top:50,left:50,width:1820}}"));
            newContent.put("selection", new JSONObject("{type: \"always\", properties:{}}"));
            newContent.put("trigger", "at_each_start_up");
            newContent.put("duration", new JSONObject("{type: \"untill_key_press\", properties:{key_codes:[\"ALL\"]}}"));
            JSONArray newJsonArray = new JSONArray();
            newJsonArray.put(newContent);
            newJsonArray.putAll(jsonArray);
            banners.setContent(newJsonArray.toString());
            JpaManager.getBannersManager().save(banners);
            Utils.renderSuccessJsonData(response);
        }
        Utils.renderErrorJsonMsg("add content commerical banner not found", response);
    }

    @PostMapping(value={"/copyCommericalContent"})
    public void copyCommericalContent(int bannerId, long contentId, HttpServletResponse response) {
        Banners banners = JpaManager.getBannersManager().loadByKey(bannerId);
        if (banners != null && StringUtils.equalsIgnoreCase(banners.getType(), "Commercial")) {
            String jsonContent = Optional.ofNullable(banners.getContent()).orElse("[]");
            JSONArray jsonArray = new JSONArray(jsonContent);
            int index = this.getContentIndexById(jsonArray, contentId);
            if (index == -1) {
                Utils.renderErrorJsonMsg("copy content not found", response);
                return;
            }
            JSONObject copyContent = jsonArray.optJSONObject(index);
            if (copyContent != null) {
                JSONObject newContent = new JSONObject(copyContent.toString());
                newContent.put("id", System.currentTimeMillis());
                newContent.remove("version_tag");
                for (int startIndex = jsonArray.length(); startIndex > index; --startIndex) {
                    jsonArray.put(startIndex, jsonArray.get(startIndex - 1));
                }
                jsonArray.put(index + 1, newContent);
            }
            banners.setContent(jsonArray.toString());
            JpaManager.getBannersManager().save(banners);
            Utils.renderSuccessJsonData(response);
        }
        Utils.renderErrorJsonMsg("copy content commerical banner not found", response);
    }

    @PostMapping(value={"/deleteCommericalContent"})
    public void deleteCommericalContent(int bannerId, long contentId, HttpServletResponse response) {
        Banners banners = JpaManager.getBannersManager().loadByKey(bannerId);
        if (banners != null && StringUtils.equalsIgnoreCase(banners.getType(), "Commercial")) {
            String jsonContent = Optional.ofNullable(banners.getContent()).orElse("[]");
            JSONArray jsonArray = new JSONArray(jsonContent);
            int index = this.getContentIndexById(jsonArray, contentId);
            if (index == -1) {
                Utils.renderErrorJsonMsg("delete content not found", response);
                return;
            }
            jsonArray.remove(index);
            banners.setContent(jsonArray.toString());
            JpaManager.getBannersManager().save(banners);
            Utils.renderSuccessJsonData(response);
        }
        Utils.renderErrorJsonMsg("delete content commerical banner not found", response);
    }

    @GetMapping(value={"/previewCommercialContent"})
    public void previewCommercialContent(int bannerId, long contentId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Banners banners = JpaManager.getBannersManager().loadByKey(bannerId);
        if (banners != null && StringUtils.equalsIgnoreCase(banners.getType(), "Commercial")) {
            String jsonContent = Optional.ofNullable(banners.getContent()).orElse("[]");
            JSONArray jsonArray = new JSONArray(jsonContent);
            int index = this.getContentIndexById(jsonArray, contentId);
            if (index == -1) {
                throw new RuntimeException("preview content not found");
            }
            JSONObject content = jsonArray.optJSONObject(index);
            if (content != null) {
                request.setAttribute("banners", banners);
                request.setAttribute("content", content);
                request.getRequestDispatcher("/jsp/banner/commerical_preview.jsp").forward(request, response);
                return;
            }
        }
        throw new RuntimeException("preview commerial content banner failure");
    }
}

