/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.servlet;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.Utils;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingServlet
extends HttpServlet {
    private static final long serialVersionUID = 2630891581658442043L;
    private static final Logger LOG = LoggerFactory.getLogger(LoggingServlet.class);

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        if (!ServletFileUpload.isMultipartContent(request)) {
            Utils.renderErrorJsonMsg("Not a valid upload request", response);
            return;
        }
        String requestIP = request.getRemoteAddr();
        Devices device = this.getDeviceByRequestIp(requestIP);
        if (device == null) {
            Utils.renderErrorJsonMsg("Can't find a device with ip:" + requestIP, response);
            return;
        }
        FileItem uploadFile = this.getFirstFileItemFromRequest(request);
        if (uploadFile == null) {
            Utils.renderErrorJsonMsg("Can't find a valid upload file from request", response);
            return;
        }
        try {
            uploadFile.write(this.createTargetSaveFileObject(device));
            Utils.renderSuccessJsonData(response);
        }
        catch (Exception ex) {
            LOG.error("save file failure", ex);
            Utils.renderErrorJsonMsg("save log file failure", response);
        }
    }

    private File createTargetSaveFileObject(Devices device) throws IOException {
        String tvUniqueId = device.getTvuniqueid();
        String fullPath = CommonConstants.REMOTE_DIAGNOSTIC_LOGGING_SAVE_PATH + tvUniqueId;
        String fileName = "RDM_Logging_" + tvUniqueId + "_" + new SimpleDateFormat("ddMMyyy_hhmm").format(new Date()) + ".zip";
        File file = new File(fullPath + File.separator + fileName);
        FileUtils.createParentDirectories(file);
        return file;
    }

    private Devices getDeviceByRequestIp(String requestIP) {
        List<Devices> devices = JpaManager.getDevicesManager().findDevicesByTvipaddress(requestIP);
        if (devices.isEmpty()) {
            return null;
        }
        Collections.sort(devices, new Comparator<Devices>(){

            @Override
            public int compare(Devices o1, Devices o2) {
                return this.getWeight(o2) - this.getWeight(o1);
            }

            private int getWeight(Devices device) {
                int weight = 0;
                if (device.isOnline()) {
                    ++weight;
                }
                if (!device.isRFDevice()) {
                    ++weight;
                }
                return weight;
            }
        });
        return devices.get(0);
    }

    private FileItem getFirstFileItemFromRequest(HttpServletRequest request) {
        try {
            List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            for (FileItem item : multiparts) {
                if (item.isFormField()) continue;
                return item;
            }
        }
        catch (Exception ex) {
            LOG.error("can't extract multipart from request", ex);
        }
        return null;
    }
}

