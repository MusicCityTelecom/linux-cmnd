/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.annotation.WebServlet
 */
package com.tpvision.smartinstall.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tpvision.smartinstall.api.ApiConstants;
import com.tpvision.smartinstall.api.ApiLicenseChecker;
import com.tpvision.smartinstall.api.ApiType;
import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.ExApi;
import com.tpvision.smartinstall.dao.mgr.DevicesManager;
import com.tpvision.smartinstall.dao.mgr.ExApiManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.japit.SecuredCmdControlManager;
import com.tpvision.smartinstall.schedule.Job;
import com.tpvision.smartinstall.servlet.RunRFServlet;
import com.tpvision.smartinstall.util.CertUtils;
import com.tpvision.smartinstall.util.NetworkUtils;
import com.tpvision.smartinstall.util.TpvBatchJobUtils;
import com.tpvision.smartinstall.util.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(value={"/ExAPIServlet"})
public class ExAPIServlet
extends RunRFServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(ExAPIServlet.class);

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        String status = "{\"status\":\"failure\"}";
        if ("adminApiTag".equalsIgnoreCase(type)) {
            this.adminApiTag(response);
        } else if ("AddAPIKey".equalsIgnoreCase(type)) {
            this.handleAddAPIKey(request, response);
        } else if ("DeleteAPIKey".equalsIgnoreCase(type)) {
            this.handleDeleteAPIKey(request);
        } else if ("getAPIKey".equalsIgnoreCase(type)) {
            this.handleGetAPIKey(request, response);
        } else if ("adminApiMgrChange".equals(type)) {
            this.adminApiMgrChange(request, response);
        } else if ("GetServerIP".equalsIgnoreCase(type)) {
            this.handleGetServerIP(response);
        } else {
            if ("downloadCaCertificate".equalsIgnoreCase(type)) {
                this.handleGetCaCertificate(response);
                return;
            }
            if ("testRequest".equals(type)) {
                this.handleTestRequest(request, response);
            } else if ("getJobs".equals(type)) {
                this.handleGetJobs(response);
            } else if ("executeJobs".equals(type)) {
                this.handleExecuteJobs(request, response);
            } else if ("getExApiStatus".equals(type)) {
                this.handleGetExApiStatus(response);
            } else {
                status = "{\"Status\":\"failure\",\"ResultDetails\":\"incorrect command type\"}";
            }
        }
        Utils.writeToResponse(status, "text/html;charset=UTF-8", response);
    }

    private void handleGetExApiStatus(HttpServletResponse response) {
        JSONObject result = new JSONObject();
        result.put("isSupport", ApiLicenseChecker.getInstance().isSupportApiType(ApiType.EXAPI));
        ExApi exapi = JpaManager.getExApiManager().loadExApi();
        result.put("isOpen", exapi == null ? "off" : exapi.getApi());
        result.put("currentVersion", 3);
        result.put("compatibleVersions", ApiConstants.EXAPI_BACKWARD_SUPPORT_VERSIONS.length == 0 ? "-" : Arrays.toString(ApiConstants.EXAPI_BACKWARD_SUPPORT_VERSIONS));
        Utils.writeToResponse(result.toString(), "text/html;charset=UTF-8", response);
    }

    private void adminApiTag(HttpServletResponse response) {
        ExApi exapi = JpaManager.getExApiManager().loadExApi();
        Utils.writeToResponse(new Gson().toJson(exapi), "text/html;charset=UTF-8", response);
    }

    private void handleAddAPIKey(HttpServletRequest request, HttpServletResponse response) {
        String status = "{\"status\":\"success\"}";
        String apikey = request.getParameter("API_Key");
        String apikeytitle = request.getParameter("API_KeyTitle");
        ExApi ps = new ExApi();
        ps.setApikey(DigestUtils.sha512Hex(apikey));
        ps.setApikeyname(apikeytitle);
        JpaManager.getExApiManager().save(ps);
        Utils.writeToResponse(status, "text/html;charset=UTF-8", response);
    }

    private void handleDeleteAPIKey(HttpServletRequest request) {
        String apikeyID = request.getParameter("ID");
        JpaManager.getExApiManager().deleteByKey(Integer.parseInt(apikeyID));
    }

    private void handleGetAPIKey(HttpServletRequest request, HttpServletResponse response) {
        int current = Integer.valueOf(request.getParameter("current"));
        int rowCount = Integer.valueOf(request.getParameter("rowCount"));
        String searchPhrase = request.getParameter("searchPhrase");
        String sortId = request.getParameter("sort[id]");
        String apikeyname = request.getParameter("sort[apikeyname]");
        String apikey = request.getParameter("sort[apikey]");
        JsonObject data = JpaManager.getExApiManager().findExapiPage(current, rowCount, searchPhrase, sortId, apikeyname, apikey);
        Utils.writeToResponse(data.toString(), "text/html;charset=UTF-8", response);
    }

    private void adminApiMgrChange(HttpServletRequest request, HttpServletResponse response) {
        String status = "{\"status\":\"success\"}";
        String api = StringUtils.equalsIgnoreCase("true", request.getParameter("isOpen")) ? "on" : "off";
        ExApiManager apiManager = JpaManager.getExApiManager();
        ExApi ps = apiManager.loadExApi();
        if (null == ps) {
            ps = new ExApi();
        }
        ps.setApi(api);
        apiManager.save(ps);
        ApiLicenseChecker.getInstance().reloadExApiSwitch();
        Utils.writeToResponse(status, "text/html;charset=UTF-8", response);
    }

    private void handleTestRequest(HttpServletRequest request, HttpServletResponse response) {
        String status = "{\"status\":\"success\"}";
        String ip = request.getParameter("TVIP");
        String command = request.getParameter("Command");
        String value = request.getParameter("Value");
        switch (command) {
            case "TransitCommunicationTo": {
                if ("Secured".equalsIgnoreCase(value)) {
                    Devices tv = this.getDevicesByIp(ip);
                    SecuredCmdControlManager.requestSecuredCmd(tv);
                    break;
                }
                if ("NonSecured".equalsIgnoreCase(value)) {
                    Devices tv = this.getDevicesByIp(ip);
                    SecuredCmdControlManager.sendSecuredCmdToHttp(tv);
                    break;
                }
                if ("SecuredDirect".equalsIgnoreCase(value)) {
                    SecuredCmdControlManager.sendSecuredCmdToHttps(ip);
                    break;
                }
                status = "{\"status\":\"fail\"}";
                break;
            }
            case "ReGenerateCA": {
                CertUtils.reGenerateCert();
                break;
            }
        }
        Utils.writeToResponse(status, "text/html;charset=UTF-8", response);
    }

    private Devices getDevicesByIp(String ip) {
        DevicesManager devicesManager = JpaManager.getDevicesManager();
        List<Devices> devices = devicesManager.findDevicesByTvipaddress(ip);
        if (devices.size() == 1) {
            return devices.get(0);
        }
        for (Devices tv : devices) {
            if ("offline".equals(tv.getPowerstatus())) continue;
            return tv;
        }
        return null;
    }

    private void handleGetServerIP(HttpServletResponse response) {
        List<String> list = NetworkUtils.getServerIpList();
        String serverIp = "";
        for (int i = 0; i < list.size(); ++i) {
            serverIp = i == list.size() - 1 ? serverIp + list.get(i) : serverIp + list.get(i) + ";";
        }
        String status = String.format("{\"status\":\"success\",\"data\":\"%s\"}", serverIp);
        Utils.writeToResponse(status, "text/html;charset=UTF-8", response);
    }

    private void handleGetCaCertificate(HttpServletResponse response) {
        String filePath = CertUtils.getCaPublicKeyPath();
        File downloadFile = new File(filePath);
        try (FileInputStream inStream = new FileInputStream(downloadFile);
             ServletOutputStream outStream = response.getOutputStream();){
            ServletContext context = this.getServletContext();
            String mimeType = context.getMimeType(filePath);
            if (null == mimeType) {
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);
            response.setContentLength((int)downloadFile.length());
            response.setHeader("Content-Disposition", "attachment; filename=\"ca.pem\"");
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void handleGetJobs(HttpServletResponse response) {
        Utils.writeToResponse(new JSONObject(TpvBatchJobUtils.getExecuteAbleJobs(Job.ExecuteType.ALL, Job.ExecuteType.BYHAND)).toString(), "text/json;charset=UTF-8", response);
    }

    private void handleExecuteJobs(HttpServletRequest request, HttpServletResponse response) {
        String jobName = request.getParameter("jobName");
        Utils.writeToResponse(TpvBatchJobUtils.handleExecuteJobs(jobName), "text/json;charset=UTF-8", response);
    }
}

