/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.annotation.WebServlet
 */
package com.tpvision.smartinstall.servlet;

import com.google.gson.Gson;
import com.tpvision.smartinstall.VersionChecker;
import com.tpvision.smartinstall.api.ApiConstants;
import com.tpvision.smartinstall.api.ApiLicenseChecker;
import com.tpvision.smartinstall.api.ApiType;
import com.tpvision.smartinstall.dao.core.MyChoiceTemplate;
import com.tpvision.smartinstall.dao.core.ReceptionClient;
import com.tpvision.smartinstall.dao.core.SIConfig;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.schedule.ScheduleListener;
import com.tpvision.smartinstall.servlet.BaseHttpServlet;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.Utils;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(value={"/siconfig"})
public class SIConfigServlet
extends BaseHttpServlet {
    private static final long serialVersionUID = 4507021129242872816L;
    private static final Logger LOG = LoggerFactory.getLogger(SIConfigServlet.class);

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mode = request.getParameter("mode");
        String weatherService = request.getParameter("weatherService");
        String refreshRate = request.getParameter("refreshRate");
        String result = "{\"status\":\"fail\"}";
        SIConfig sic = JpaManager.getSIConfigManager().getSIConfig();
        if (null != sic) {
            if ("setFreshRate".equalsIgnoreCase(mode)) {
                sic.setRefreshRate(refreshRate);
                JpaManager.getSIConfigManager().saveSIConfig(sic);
                ScheduleListener.resetWeatherService();
                result = "{\"status\":\"success\"}";
            } else if ("setWeatherService".equalsIgnoreCase(mode)) {
                sic.setWeatherService(weatherService);
                JpaManager.getSIConfigManager().saveSIConfig(sic);
                ScheduleListener.resetWeatherService();
                result = "{\"status\":\"success\"}";
            } else if ("get".equalsIgnoreCase(mode)) {
                result = "{\"status\":\"success\", \"weatherService\":\"" + sic.getWeatherService() + "\", \"refreshRate\":\"" + sic.getRefreshRate() + "\"}";
            } else if ("set2K16SSMSSettingTabIndex".equalsIgnoreCase(mode)) {
                Utils.instance().save2K16SSMSSettingTabIndex(request.getParameter("tabId"));
            } else if ("set2K16ESSettingTabIndex".equalsIgnoreCase(mode)) {
                Utils.instance().save2K16ESSettingTabIndex(request.getParameter("tabId"));
            } else if ("set2K14MSSettingTabIndex".equalsIgnoreCase(mode)) {
                Utils.instance().save2K14MSSettingTabIndex(request.getParameter("tabId"));
            } else if ("set2K14ESSettingTabIndex".equalsIgnoreCase(mode)) {
                Utils.instance().save2K14ESSettingTabIndex(request.getParameter("tabId"));
            } else {
                if ("showReception".equalsIgnoreCase(mode)) {
                    this.showReceptionPage(request, response, sic);
                    return;
                }
                if ("showLicense".equalsIgnoreCase(mode)) {
                    this.showLicensePage(request, response, sic);
                    return;
                }
                if ("saveReception".equalsIgnoreCase(mode)) {
                    this.saveReceptionDataToSiconfig(request, sic);
                    result = "{\"status\":\"success\"}";
                } else if ("saveLicense".equalsIgnoreCase(mode)) {
                    String licenseActivationCode = request.getParameter("licenseActivationCode");
                    result = this.activeApiLicense(licenseActivationCode, sic);
                } else if ("showTemplate".equalsIgnoreCase(mode)) {
                    this.showTemplate(request, response);
                } else if ("saveTemplate".equalsIgnoreCase(mode)) {
                    this.saveTemplate(request, response);
                } else if ("saveLogo".equalsIgnoreCase(mode)) {
                    this.saveLogo(request, response);
                } else if ("isHaveMyChoiceLicense".equalsIgnoreCase(mode) && ApiLicenseChecker.getInstance().isSupportApiType(ApiType.MYCHOICE)) {
                    result = "{\"status\":\"success\"}";
                } else if ("getRoomType".equalsIgnoreCase(mode)) {
                    result = this.getRoomType(sic);
                } else {
                    if ("CHECK_USERNAME_ROLE_CONFIG".equalsIgnoreCase(mode)) {
                        String data = this.checkUserNameRoleConfig(request);
                        Utils.writeToResponse(data, "text/html;charset=UTF-8", response);
                        return;
                    }
                    if ("UPDATE_USERNAME_ROLE_CONFIG".equalsIgnoreCase(mode)) {
                        String data = this.updateUserNameRoleConfig(request);
                        Utils.writeToResponse(data, "text/html;charset=UTF-8", response);
                        return;
                    }
                    if ("GET_CMND_VERSION".equalsIgnoreCase(mode)) {
                        String cmndVersion = Utils.getCMNDBuildVersion(this.getServletContext());
                        if ("".equalsIgnoreCase(cmndVersion)) {
                            cmndVersion = "7.0.1";
                        }
                        String data = "{\"cmndVersion\":\"" + cmndVersion + "\"}";
                        Utils.writeToResponse(data, "text/html;charset=UTF-8", response);
                        return;
                    }
                    if ("CHECK_LATEST_VERSIONS".equalsIgnoreCase(mode)) {
                        if (request.getSession().getAttribute("version_check") == null) {
                            JSONArray versionInfo = this.checkLatestVersionShowInfo();
                            request.getSession().setAttribute("version_check", true);
                            Utils.renderSuccessJsonData(versionInfo, response);
                        } else {
                            Utils.renderErrorJsonMsg("already checked for this login", response);
                        }
                        return;
                    }
                    if ("SKIP_RECEPTION_VERSION_CHECK".equalsIgnoreCase(mode)) {
                        ReceptionClient receptionClient = JpaManager.getReceptionClientManager().findById(Integer.parseInt(request.getParameter("id")));
                        if (receptionClient != null && receptionClient.getStatus() == 1) {
                            receptionClient.setStatus(0);
                            JpaManager.getReceptionClientManager().save(receptionClient);
                        }
                        Utils.renderSuccessJsonData(response);
                        return;
                    }
                }
            }
        }
        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter();){
            out.print(result);
            out.flush();
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private JSONArray checkLatestVersionShowInfo() {
        JSONArray dialogNoticeArray = new JSONArray();
        VersionChecker.VersionDetail latestCmndVersion = VersionChecker.getCmndNewVersion(Utils.getCMNDMajorVersion());
        if (latestCmndVersion != null) {
            JSONObject cmndVersionNotice = new JSONObject();
            cmndVersionNotice.put("type", "Cmnd");
            cmndVersionNotice.put("tips", "CMND is out-of-date,latest version is <b>" + latestCmndVersion.getVersion() + "</b>");
            cmndVersionNotice.put("url", latestCmndVersion.getUrl());
            dialogNoticeArray.put(cmndVersionNotice);
        }
        List<ReceptionClient> checkedReceptionClientList = JpaManager.getReceptionClientManager().findReceptionClientListByStatus(1);
        checkedReceptionClientList.stream().forEach(receptionVersion -> {
            VersionChecker.VersionDetail newVersion = VersionChecker.getReceptionNewVersion(receptionVersion.getCurrentVersion());
            if (newVersion != null) {
                JSONObject receptionVerson = new JSONObject();
                receptionVerson.put("type", "Reception");
                receptionVerson.put("tips", "CMND Reception on PC <b>" + receptionVersion.getClientId() + "</b> is out-of-date,latest version is <b>" + newVersion.getVersion() + "</b>");
                receptionVerson.put("url", newVersion.getUrl());
                receptionVerson.put("id", receptionVersion.getId());
                dialogNoticeArray.put(receptionVerson);
            }
        });
        return dialogNoticeArray;
    }

    private String checkUserNameRoleConfig(HttpServletRequest request) {
        return Utils.getUserConfig().getConfigsJson();
    }

    private String updateUserNameRoleConfig(HttpServletRequest request) {
        String data = "{\"status\":\"success\"}";
        HashMap<String, String> params = new HashMap<String, String>();
        String key = request.getParameter("key");
        String value = request.getParameter("value");
        LOG.debug("[updateConfig]key={},value={}", (Object)key, (Object)value);
        params.put(key, value);
        Utils.updateUserProfileConfig(params);
        return data;
    }

    private String getRoomType(SIConfig si) {
        JSONArray array = new JSONArray();
        String roomType = si.getRoomType();
        if (roomType != null) {
            for (String item : roomType.split(",")) {
                array.put(item);
            }
        }
        return this.successStatus(array);
    }

    private String activeApiLicense(String licenseActivationCode, SIConfig sic) {
        if (StringUtils.isEmpty(licenseActivationCode)) {
            return this.returnErrorJson("license activation code is empty");
        }
        boolean verifyResult = ApiLicenseChecker.getInstance().verifyLicenseActivationCode(licenseActivationCode);
        if (!verifyResult) {
            return this.returnErrorJson("license request failure");
        }
        String oldCode = sic.getApiLicense();
        sic.setApiLicense(licenseActivationCode);
        if (!StringUtils.equalsIgnoreCase(oldCode, licenseActivationCode)) {
            sic.setLicenseData(null);
        }
        JpaManager.getSIConfigManager().saveSIConfig(sic);
        ApiLicenseChecker.getInstance().reload(true);
        return new Gson().toJson(this.formatApiLicenseData(ApiLicenseChecker.getInstance()));
    }

    private String returnErrorJson(String errorMsg) {
        JSONObject jsonObject = new JSONObject("{\"status\":\"fail\"}");
        jsonObject.put("msg", errorMsg);
        return jsonObject.toString();
    }

    private void saveReceptionDataToSiconfig(HttpServletRequest request, SIConfig sic) {
        sic.setSiName(request.getParameter("siName"));
        sic.setSiAddress(request.getParameter("siAddress"));
        sic.setSiSupport(request.getParameter("siSupport"));
        sic.setMychoiceApikey(request.getParameter("mychoiceApikey"));
        sic.setFutureCheckIn(request.getParameter("futureCheckIn"));
        sic.setCheckoutTime(request.getParameter("checkoutHour") + ":" + request.getParameter("checkoutMinute"));
        sic.setSupportLanguage(request.getParameter("supportLanguage"));
        sic.setDefaultLanguage(request.getParameter("defaultLanguage"));
        sic.setSupportRoomtype(request.getParameter("supportRoomtype"));
        sic.setRoomType(request.getParameter("roomType"));
        JpaManager.getSIConfigManager().saveSIConfig(sic);
    }

    private void showReceptionPage(HttpServletRequest request, HttpServletResponse response, SIConfig sic) throws ServletException, IOException {
        request.setAttribute("siconfig", sic);
        String checkoutTime = sic.getCheckoutTime();
        if (StringUtils.isBlank(checkoutTime)) {
            checkoutTime = "00:00";
        }
        String hour = checkoutTime.split(":")[0];
        String minute = checkoutTime.split(":")[1];
        request.setAttribute("checkoutHour", hour);
        request.setAttribute("checkoutMinute", minute);
        request.setAttribute("apiVeresion", 6);
        request.setAttribute("compatibleVersions", ApiConstants.API_BACKWARD_SUPPORT_VERSIONS.length == 0 ? "-" : Arrays.toString(ApiConstants.API_BACKWARD_SUPPORT_VERSIONS));
        request.getRequestDispatcher("jsp/admin/reception.jsp").forward(request, response);
    }

    private void showLicensePage(HttpServletRequest request, HttpServletResponse response, SIConfig sic) throws ServletException, IOException {
        request.setAttribute("siconfig", sic);
        ApiLicenseChecker apiLicenseChecker = ApiLicenseChecker.getInstance();
        request.setAttribute("licenseInfo", this.formatApiLicenseData(apiLicenseChecker));
        request.getRequestDispatcher("jsp/admin/license.jsp").forward(request, response);
    }

    private void showTemplate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MyChoiceTemplate myChoiceTemplate = JpaManager.getMyChoiceTemplateManager().getMyChoiceTemplate();
        request.setAttribute("myChoiceTemplate", myChoiceTemplate);
        request.getRequestDispatcher("jsp/admin/mychoiceTemplate.jsp").forward(request, response);
    }

    private void saveTemplate(HttpServletRequest request, HttpServletResponse response) {
        try {
            LOG.info("abovePinCode is {}", (Object)request.getParameter("abovePinCode"));
            MyChoiceTemplate myChoiceTemplate = JpaManager.getMyChoiceTemplateManager().getMyChoiceTemplate();
            myChoiceTemplate.setAbovePinCode(request.getParameter("abovePinCode"));
            myChoiceTemplate.setBelowPinCode(request.getParameter("belowPinCode"));
            myChoiceTemplate.setNumberOfDays(request.getParameter("numberOfDays"));
            myChoiceTemplate.setNumberOfHours(request.getParameter("numberOfHours"));
            myChoiceTemplate.setRoomNumber(request.getParameter("roomNumber"));
            myChoiceTemplate.setValidityNotice(request.getParameter("validityNotice"));
            myChoiceTemplate.setInstructions(request.getParameter("instructions"));
            JpaManager.getMyChoiceTemplateManager().saveMyChoiceTemplate(myChoiceTemplate);
            Utils.renderSuccessJsonData(response);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            Utils.renderErrorJsonMsg(e.getMessage(), response);
        }
    }

    private void saveLogo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            if (items.isEmpty()) {
                throw new RuntimeException("upload file is empty");
            }
            FileItem fileItem = items.get(0);
            String ext = FilenameUtils.getExtension(fileItem.getName());
            String imageName = "ReceptionLogo." + ext;
            File destLogo = new File(CommonConstants.RECEPTION_LOGO_IMG_LOCATION + imageName);
            if (destLogo.exists()) {
                FileUtils.deleteQuietly(destLogo);
            }
            fileItem.write(destLogo);
            MyChoiceTemplate myChoiceTemplate = JpaManager.getMyChoiceTemplateManager().getMyChoiceTemplate();
            myChoiceTemplate.setLogo(imageName);
            JpaManager.getMyChoiceTemplateManager().saveMyChoiceTemplate(myChoiceTemplate);
            Utils.renderSuccessJsonData(response);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            Utils.renderErrorJsonMsg(e.getMessage(), response);
        }
    }

    private Map<String, Object> formatApiLicenseData(ApiLicenseChecker apiLicenseChecker) {
        HashMap<String, Object> formatData = new HashMap<String, Object>();
        formatData.put("serialNumber", apiLicenseChecker.getSerialNumber());
        formatData.put("licenseActivationCode", Optional.ofNullable(apiLicenseChecker.getLicenseActivationCode()).orElse(""));
        boolean isShowLicenseData = StringUtils.isNotBlank(apiLicenseChecker.getLicenseActivationCode());
        formatData.put("isHaveLicense", isShowLicenseData);
        List<Object> licenseDetail = Collections.emptyList();
        if (isShowLicenseData) {
            licenseDetail = apiLicenseChecker.getFeatureLicenseDetail();
        }
        formatData.put("licenseDetail", licenseDetail);
        formatData.put("deviceCount", JpaManager.getDevicesManager().findAllCount());
        return formatData;
    }
}

