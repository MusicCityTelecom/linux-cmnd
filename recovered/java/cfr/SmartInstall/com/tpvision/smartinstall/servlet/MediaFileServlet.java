/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.annotation.WebServlet
 */
package com.tpvision.smartinstall.servlet;

import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.Utils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(value={"/mediafile"})
public class MediaFileServlet
extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(MediaFileServlet.class.getClass());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String status = "{\"status\":\"fail\"}";
        String mode = request.getParameter("mode");
        LOG.info("mode={}", (Object)mode);
        if ("UPLOAD_MEDIA_FILE".equalsIgnoreCase(mode)) {
            status = this.uploadMediaFile(request);
        } else if ("DELETE_MEDIA_FILE".equalsIgnoreCase(mode)) {
            status = this.deleteMediaFile(request, response);
        }
        Utils.writeToResponse(status, "text/json;charset=UTF-8", response);
    }

    private String deleteMediaFile(HttpServletRequest request, HttpServletResponse response) {
        String status;
        String filename = request.getParameter("filename");
        String sname = request.getParameter("sname");
        String createdBy = request.getParameter("createdBy");
        String platformName = request.getParameter("platformName");
        String cloneDisplayName = request.getParameter("cloneDisplayName");
        int channelPackageId = Integer.parseInt(request.getParameter("channelPackageId"));
        LOG.info("filename=" + filename + ",sname=" + sname + ",createdBy=" + createdBy + ",platformName=" + platformName + ",cloneDisplayName=" + cloneDisplayName + ",channelPackageId=" + channelPackageId);
        String srcRootPath = CommonConstants.CLONE_PROCESS_LOCATION + "ChannelPackages" + File.separator + channelPackageId + File.separator + "MediaChannels";
        String deleteFilePath = srcRootPath + File.separator + filename;
        File deleteFile = new File(deleteFilePath);
        if (deleteFile.exists()) {
            FileUtils.deleteQuietly(deleteFile);
            status = "{\"status\":\"success\"}";
            LOG.info("delete file success:{}", (Object)filename);
        } else {
            status = "{\"status\":\"none\"}";
        }
        return status;
    }

    private String uploadMediaFile(HttpServletRequest request) {
        String status = "{\"status\":\"fail\"}";
        String oldFilename = request.getParameter("old_filename");
        String sname = request.getParameter("sname");
        String createdBy = request.getParameter("createdBy");
        String platformName = request.getParameter("platformName");
        String cloneDisplayName = request.getParameter("cloneDisplayName");
        int channelPackageId = Integer.parseInt(request.getParameter("channelPackageId"));
        LOG.info("old_filename=" + oldFilename + ",sname=" + sname + ",createdBy=" + createdBy + ",platformName=" + platformName + ",cloneDisplayName=" + cloneDisplayName + ",channelPackageId=" + channelPackageId);
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                if (null != multiparts) {
                    String filename = multiparts.get(0).getName();
                    LOG.info("filename={}", (Object)filename);
                    String srcRootPath = CommonConstants.CLONE_PROCESS_LOCATION + "ChannelPackages" + File.separator + channelPackageId + File.separator + "MediaChannels";
                    File processMediaChannel = new File(srcRootPath);
                    if (!processMediaChannel.exists()) {
                        processMediaChannel.mkdirs();
                    }
                    String mediaChannelIdenifier = processMediaChannel.toString() + File.separator + "MediaChannels_Identifier.txt";
                    FileUtils.writeStringToFile(new File(mediaChannelIdenifier), TpvDateUtils.getCurrentIndentifierFormatTime(), StandardCharsets.UTF_8);
                    if (!"".equals(oldFilename) && null != oldFilename) {
                        oldFilename = oldFilename.replace("file://", "");
                        String oldFilePath = processMediaChannel.toString() + File.separator + oldFilename;
                        File oldFile = new File(oldFilePath);
                        if (oldFile.exists()) {
                            FileUtils.deleteQuietly(oldFile);
                            LOG.info("delete file success:{}", (Object)oldFilename);
                        }
                    }
                    for (FileItem item : multiparts) {
                        if (item.isFormField()) continue;
                        File saveFile = new File(processMediaChannel.toString() + File.separator + filename);
                        if (saveFile.exists()) {
                            String currentTime;
                            int lengthTime;
                            int lastDotIndex = item.getName().lastIndexOf(".");
                            String name = item.getName().substring(0, lastDotIndex);
                            String format = item.getName().substring(lastDotIndex);
                            while ((lengthTime = (currentTime = String.valueOf(System.currentTimeMillis())).length()) > 3) {
                                filename = name + "_" + currentTime.substring(lengthTime - 3, lengthTime) + format;
                                LOG.info("new filename={}", (Object)filename);
                                saveFile = new File(processMediaChannel.toString() + File.separator + filename);
                                if (saveFile.exists()) continue;
                                break;
                            }
                        }
                        item.write(saveFile);
                    }
                    status = "{\"status\":\"success\",\"filename\":\"" + filename + "\"}";
                }
            }
            catch (Exception ex) {
                LOG.error(ex.getMessage(), ex);
            }
        }
        return status;
    }
}

