/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.servlet;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.mgr.DevicesManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.japit.SecuredCmdControlManager;
import com.tpvision.smartinstall.japit.TVDiscoveryManager;
import com.tpvision.smartinstall.japit.webservices.WebServiceCommandHandler;
import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.util.TpvClassUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.TpvStringUtils;
import com.tpvision.smartinstall.util.Utils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class iptv
extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(iptv.class);
    private static final Map<String, Class<? extends WebServiceCommandHandler>> COMMAND_HANDLER_MAPPING = new HashMap<String, Class<? extends WebServiceCommandHandler>>();
    private static final Map<String, Object> TV_HANDLE_LOCKS;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String clientip = this.getClientIp(request);
        String requestSchema = request.getScheme();
        String requestData = this.getRequestData(request);
        String data = "";
        if (!this.checkSecurityHeader(request, requestData, requestSchema)) {
            data = this.error("ERROR", "security_token is invalid");
        } else {
            LOG.info("TV->SI tvip={} receive={} schema={}", clientip, TpvStringUtils.removeNL(requestData), requestSchema);
            data = this.handleWebServiceCommand(requestData, clientip, requestSchema);
            LOG.info("SI->TV tvip={} return={} schema={}", clientip, TpvStringUtils.removeNL(data), requestSchema);
        }
        Utils.writeToResponse(data, "text/html;charset=UTF-8", response);
    }

    private String getClientIp(HttpServletRequest request) {
        String requestIp = request.getParameter("clientip");
        if (StringUtils.isNotBlank(requestIp)) {
            LOG.info("clientip ={} from request parameter ", (Object)requestIp);
            return requestIp;
        }
        return request.getRemoteAddr();
    }

    private boolean checkSecurityHeader(HttpServletRequest request, String requestData, String requestSchema) {
        boolean compareSuccess;
        if (!requestSchema.equals("https")) {
            return true;
        }
        String securityToken = request.getHeader("Security-Token");
        if (StringUtils.isBlank(securityToken)) {
            return true;
        }
        String hashValue = SecuredCmdControlManager.generateRequestTokenFromPostData(requestData);
        boolean bl = compareSuccess = hashValue != null && hashValue.equalsIgnoreCase(securityToken);
        if (!compareSuccess) {
            LOG.error("compare token failure: header value is <{}>, the cal result is <{}>, post data:<{}>", securityToken, hashValue, requestData);
        }
        return compareSuccess;
    }

    private String getRequestData(HttpServletRequest request) {
        String jsonData = request.getParameter("jsonData");
        String requestHeader = "";
        if (null != jsonData) {
            requestHeader = jsonData;
        } else if ("application/x-www-form-urlencoded".equalsIgnoreCase(request.getContentType())) {
            Map m = request.getParameterMap();
            Set s = m.entrySet();
            for (Map.Entry entry : s) {
                requestHeader = (String)entry.getKey();
            }
        } else {
            try (ServletInputStream inputStream = request.getInputStream();){
                int len;
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                while ((len = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                outStream.close();
                requestHeader = new String(outStream.toByteArray(), StandardCharsets.UTF_8);
            }
            catch (IOException ix) {
                LOG.error(ix.getMessage(), ix);
            }
        }
        return requestHeader;
    }

    private String getTVUniqueID(JSONObject commandDetails, JSONObject webServiceParameters) {
        String tvUniqueId;
        if (webServiceParameters != null && StringUtils.isNotBlank(tvUniqueId = webServiceParameters.optString("TVUniqueID", ""))) {
            return tvUniqueId;
        }
        JSONObject tvDiscoveryParameters = commandDetails.optJSONObject("TVDiscoveryParameters");
        if (tvDiscoveryParameters != null) {
            String tvSerialNumber = tvDiscoveryParameters.optString("TVSerialNumber", "");
            String tvMacAddress = tvDiscoveryParameters.optString("TVMACAddress", "");
            return tvSerialNumber + tvMacAddress.replace(":", "");
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String handleWebServiceCommand(String requestData, String clientIp, String requestSchema) throws IOException {
        Object lock;
        JSONObject jsonObject = TpvStringUtils.getJSONObjectFromString(requestData);
        if (jsonObject == null) {
            LOG.error("JSONObject parsing in tv data failed");
            return "{\"status\": \"parsing request data failed.\"}";
        }
        JSONObject commandDetails = jsonObject.getJSONObject("CommandDetails");
        JSONObject webServiceParameters = commandDetails.optJSONObject("WebServiceParameters");
        String function = jsonObject.optString("Fun");
        String tvUniqueId = this.getTVUniqueID(commandDetails, webServiceParameters);
        if (StringUtils.isBlank(tvUniqueId)) {
            LOG.error("Invalid japit due to TVUniqueID is empty, just return empty");
            return "";
        }
        WebServiceCommandHandler handler = this.getHandlerByCommandFunc(function);
        if (handler == null) {
            LOG.warn("received <{}> command TODO implement!", (Object)function);
            return "";
        }
        handler.setClientIp(clientIp);
        handler.setCmdType(jsonObject.optString("CmdType"));
        handler.setCommandDetails(commandDetails);
        handler.setTvUniqueId(tvUniqueId);
        handler.setRequestSchema(requestSchema);
        boolean isNotDiscoveryJapit = !function.equalsIgnoreCase("TVDiscoveryService");
        Object object = lock = this.getDevicesHandleLock(tvUniqueId);
        synchronized (object) {
            Devices device = this.getDevicesObjectForHandler(clientIp, tvUniqueId);
            if (isNotDiscoveryJapit && device == null) {
                LOG.warn("submit get device info task for client ip : {}", (Object)clientIp);
                TVDiscoveryManager.getDevices(clientIp);
                return this.error(function, "TV no found, TVUniqueId=" + tvUniqueId);
            }
            handler.setDevice(device);
            String handleResult = handler.execute();
            if (device != null) {
                if (device.getPmsSyncStatus() == PmsUtils.PmsSyncStatus.NeedSync.ordinal()) {
                    LOG.info("submit sync pms status task for device : {}", (Object)device.getTvuniqueid());
                    PmsUtils.submitSyncPmsStatusTask(device);
                }
                if (isNotDiscoveryJapit && ("offline".equalsIgnoreCase(device.getPowerstatus()) || "REBOOT".equalsIgnoreCase(device.getPowerstatus()))) {
                    LOG.info("submit refresh power status task for device : {}", (Object)device.getTvuniqueid());
                    TVDiscoveryManager.getDevices(clientIp);
                }
            }
            return handleResult;
        }
    }

    private Devices getDevicesObjectForHandler(String clientIp, String tvUniqueId) {
        DevicesManager iptvmanager = JpaManager.getDevicesManager();
        Devices iptv2 = iptvmanager.loadByKey(tvUniqueId);
        if (iptv2 != null) {
            Properties prop = Utils.getProductPropties();
            String incomingTvAdr = prop.getProperty("incomingTvAdr");
            if ("on".equalsIgnoreCase(incomingTvAdr)) {
                iptv2.setTvipaddress(clientIp);
            }
            iptv2.setLastonline(TpvDateUtils.getDateTime());
            iptvmanager.save(iptv2);
        }
        return iptv2;
    }

    private static void findWebServiceCommandHandlerRoute() {
        try {
            List<Class<WebServiceCommandHandler>> list = TpvClassUtils.getAllAssignedClass(WebServiceCommandHandler.class);
            LOG.info("find {} subclass::{}", (Object)WebServiceCommandHandler.class.getName(), (Object)list);
            for (Class<WebServiceCommandHandler> handlerClz : list) {
                WebServiceCommandHandler handler = handlerClz.newInstance();
                String upperCaseFunction = handler.getHandleFunction().toUpperCase(Locale.ROOT);
                if (COMMAND_HANDLER_MAPPING.containsKey(upperCaseFunction)) {
                    throw new RuntimeException("error::function handler::" + upperCaseFunction + "refined.");
                }
                COMMAND_HANDLER_MAPPING.put(upperCaseFunction, handlerClz);
            }
            LOG.info("COMMAND_HANDLER_MAPPING==>{}", (Object)COMMAND_HANDLER_MAPPING);
        }
        catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    private WebServiceCommandHandler getHandlerByCommandFunc(String function) {
        String upperCaseFuction = function.toUpperCase(Locale.ROOT);
        if (!COMMAND_HANDLER_MAPPING.containsKey(upperCaseFuction)) {
            return null;
        }
        try {
            return COMMAND_HANDLER_MAPPING.get(upperCaseFuction).newInstance();
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    private Object getDevicesHandleLock(String tvUniqueId) {
        Object lock = TV_HANDLE_LOCKS.get(tvUniqueId);
        if (lock == null) {
            Object newLock = new Object();
            Object currentLock = TV_HANDLE_LOCKS.putIfAbsent(tvUniqueId, newLock);
            if (currentLock == null) {
                return newLock;
            }
            return currentLock;
        }
        return lock;
    }

    private String error(String func, String msg) {
        return "{\t\"Svc\" : \"WebServices\",\t\"SvcVer\" : \"3.0\",\t\"Cookie\" : 295,   \"CmdType\": \"error\",\t\"Fun\" : \"" + func + "\",   \"CommandDetails\" :    {     \"message\": \"" + msg + "\"   }}";
    }

    static {
        iptv.findWebServiceCommandHandlerRoute();
        TV_HANDLE_LOCKS = new ConcurrentHashMap<String, Object>();
    }
}

