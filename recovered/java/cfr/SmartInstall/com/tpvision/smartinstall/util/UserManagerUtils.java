/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.util;

import com.tpvision.smartinstall.util.HttpUtils;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.util.DigestUtils;

public class UserManagerUtils {
    private static final Logger LOG = LoggerFactory.getLogger(UserManagerUtils.class);
    private static final String USERMANAGE_API_KEY = "&&&&&FDSf@#R@#!!!#@$@#$";
    private static final int CONN_TIMEOUT = 15000;
    private static final int SOKECT_TIMEOUT = 20000;

    private UserManagerUtils() {
    }

    public static String checkUserPasswordWithoutLogin(String username, String password) {
        try {
            HashMap<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("username", username);
            paramsMap.put("password", password);
            return UserManagerUtils.postRequestToUserManagerAuthBySign("/user/checkPassword", paramsMap);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return "fail";
        }
    }

    public static String modifyUserPasswordWithoutLogin(String username, String password) {
        try {
            HashMap<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("username", username);
            paramsMap.put("password", password);
            String response = UserManagerUtils.postRequestToUserManagerAuthBySign("/user/updatePassword", paramsMap);
            if (StringUtils.containsIgnoreCase(response, "OK")) {
                return "success";
            }
            return "fail";
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return "fail";
        }
    }

    public static String modifyUserPassword(String username, String password, HttpServletRequest request) {
        try {
            JSONObject resobj = UserManagerUtils.findUser(username, request);
            String userId = resobj.optString("id");
            String role = resobj.optString("role");
            return UserManagerUtils.modifyUserInfo(userId, username, password, role, request);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return "fail";
        }
    }

    public static String removeUser(String userId, HttpServletRequest request) throws IOException {
        return UserManagerUtils.sendRequestToUserManagerAuthByCAS("/" + userId + "?", "DELETE", null, true, request);
    }

    public static String modifyUserInfo(String userId, String uname, String password, String role, HttpServletRequest request) throws IOException {
        String json = "{\"id\":" + userId + ",\"username\":\"" + uname + "\",\"password\":\"" + password + "\",\"role\":\"" + role + "\"}";
        return UserManagerUtils.sendRequestToUserManagerAuthByCAS("/" + userId + "?", "PUT", json, true, request);
    }

    public static String addUser(String userId, String password, String role, HttpServletRequest request) throws IOException {
        String json = "{\"username\":\"" + userId + "\",\"password\":\"" + password + "\",\"role\":\"" + role + "\"}";
        return UserManagerUtils.sendRequestToUserManagerAuthByCAS("?", "POST", json, true, request);
    }

    public static String modifyUserRole(String userId, String newRole, HttpServletRequest request) throws IOException {
        return UserManagerUtils.sendRequestToUserManagerAuthByCAS("/" + userId + "/role/" + newRole + "?", "PUT", "", true, request);
    }

    private static JSONObject findUser(String uname, HttpServletRequest request) {
        JSONObject resobj;
        String res = null;
        try {
            res = UserManagerUtils.sendRequestToUserManagerAuthByCAS("/find/" + uname + "?", "GET", null, false, request);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        if (null != res && (resobj = new JSONObject(res)).optString("username").trim().equals(uname)) {
            return resobj;
        }
        return null;
    }

    public static JSONArray queryUserList(String filter, String sortId, HttpServletRequest request) {
        String res = null;
        try {
            res = UserManagerUtils.sendRequestToUserManagerAuthByCAS("?orderBy=" + filter + "&orderDirection=" + sortId + "&", "GET", null, false, request);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        if (null == res) {
            res = "[]";
        }
        return new JSONArray(res);
    }

    private static String sendRequestToUserManagerAuthByCAS(String uri, String type, String reqbody, Boolean isResponse, HttpServletRequest request) throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String ret = null;
        String url = HttpUtils.getUserManageLocalRequestAddress() + "/users" + uri.substring(0, uri.length() - 1);
        String ch = uri.substring(uri.length() - 1, uri.length());
        CasAuthenticationToken casAuthenticationToken = (CasAuthenticationToken)request.getUserPrincipal();
        Assertion assertion = casAuthenticationToken.getAssertion();
        AttributePrincipal attributePrincipal = assertion.getPrincipal();
        String proxyTicket = attributePrincipal.getProxyTicketFor(url);
        if (null == proxyTicket) {
            return null;
        }
        String encodedProxyTicket = URLEncoder.encode(proxyTicket, "UTF-8");
        String serviceUrl = url + ch + "ticket=" + encodedProxyTicket;
        try (CloseableHttpClient httpClient = HttpClients.createDefault();){
            StringEntity input;
            HttpRequestBase uriRequest = null;
            if ("GET".equalsIgnoreCase(type)) {
                uriRequest = new HttpGet(serviceUrl);
            } else if ("POST".equalsIgnoreCase(type)) {
                uriRequest = new HttpPost(serviceUrl);
                uriRequest.setHeader("Content-type", "application/json");
                input = new StringEntity(reqbody);
                ((HttpPost)uriRequest).setEntity(input);
            } else if ("DELETE".equalsIgnoreCase(type)) {
                uriRequest = new HttpDelete(serviceUrl);
            } else if ("PUT".equalsIgnoreCase(type)) {
                uriRequest = new HttpPut(serviceUrl);
                uriRequest.setHeader("Content-type", "application/json");
                input = new StringEntity(reqbody);
                ((HttpPut)uriRequest).setEntity(input);
            } else {
                String input2 = null;
                return input2;
            }
            uriRequest.setConfig(RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(15000).setConnectionRequestTimeout(20000).build());
            CloseableHttpResponse response = httpClient.execute(uriRequest);
            if (null != response && response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity1 = response.getEntity();
                ret = IOUtils.toString(entity1.getContent(), StandardCharsets.UTF_8);
            }
            if (isResponse.booleanValue()) {
                ret = response != null && response.getStatusLine().getStatusCode() == 200 ? "success" : "fail";
            }
        }
        stopWatch.stop();
        LOG.info("user management {} request cost time : {} ms", (Object)uri, (Object)stopWatch.getTime());
        return ret;
    }

    private static String postRequestToUserManagerAuthBySign(String uri, Map<String, String> postParameters) throws IOException {
        String ret = "fail";
        String url = HttpUtils.getUserManageLocalRequestAddress() + "/api" + uri;
        String sign = UserManagerUtils.getRequestSign(postParameters);
        postParameters.put("sign", sign);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();){
            HttpPost uriRequest = new HttpPost(url);
            uriRequest.setConfig(RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(15000).setConnectionRequestTimeout(20000).build());
            ArrayList<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>(0);
            for (Map.Entry<String, String> entry : postParameters.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity((List<? extends NameValuePair>)parameters);
            uriRequest.setEntity(formEntity);
            CloseableHttpResponse response = httpClient.execute(uriRequest);
            if (response != null && response.getStatusLine().getStatusCode() == 200) {
                ret = EntityUtils.toString(response.getEntity());
            }
        }
        return ret;
    }

    private static String getRequestSign(Map<String, String> parameterMap) {
        ArrayList<String> paramNameList = new ArrayList<String>(parameterMap.keySet());
        Collections.sort(paramNameList);
        paramNameList.remove("sign");
        StringBuilder signRawStr = new StringBuilder();
        for (String paramName : paramNameList) {
            String values = parameterMap.get(paramName);
            signRawStr.append(paramName).append("=").append(values).append("&");
        }
        signRawStr.append(USERMANAGE_API_KEY);
        return DigestUtils.md5DigestAsHex(signRawStr.toString().getBytes());
    }
}

