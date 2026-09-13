/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.http.HttpEntity
 *  org.springframework.http.HttpMethod
 *  org.springframework.http.client.ClientHttpRequestFactory
 *  org.springframework.http.client.SimpleClientHttpRequestFactory
 *  org.springframework.util.DigestUtils
 *  org.springframework.util.LinkedMultiValueMap
 *  org.springframework.web.client.RestTemplate
 */
package org.apereo.cas.adaptors.jdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.DigestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

class UsersManager {
    private static final String RESET_REDIRECT_KEY = "#)(UFKDFLSDFSDFSDFSDFDFF****#JFDNF";

    private UsersManager() {
    }

    public static void handleWeakPassword(HttpServletRequest request, HttpServletResponse response) {
        try {
            String username = request.getParameter("username");
            String sourceIp = request.getRemoteAddr();
            long time = System.currentTimeMillis();
            String checkKey = DigestUtils.md5DigestAsHex((byte[])(username + sourceIp + time + RESET_REDIRECT_KEY).getBytes());
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("username", username);
            map.put("sourceIp", sourceIp);
            map.put("time", time);
            map.put("checkKey", checkKey);
            ObjectMapper mapper = new ObjectMapper();
            String params = mapper.writeValueAsString(map);
            String info = Base64.getEncoder().encodeToString(params.getBytes());
            response.sendRedirect("/SmartInstall/password/changeWeakPage?info=" + info);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void handleLoginFailure(HttpServletRequest request) {
        try {
            String username = request.getParameter("username");
            long time = System.currentTimeMillis();
            String checkKey = DigestUtils.md5DigestAsHex((byte[])(username + time + RESET_REDIRECT_KEY).getBytes());
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("username", username);
            map.put("time", time);
            map.put("checkKey", checkKey);
            ObjectMapper mapper = new ObjectMapper();
            String params = mapper.writeValueAsString(map);
            String info = Base64.getEncoder().encodeToString(params.getBytes());
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setConnectTimeout(10000);
            factory.setReadTimeout(10000);
            RestTemplate template = new RestTemplate((ClientHttpRequestFactory)factory);
            String passwordErrorNoticeUrl = "http://" + request.getServerName() + ":8080/SmartInstall/password/loginFailure";
            LinkedMultiValueMap multiValueMap = new LinkedMultiValueMap();
            multiValueMap.add((Object)"info", (Object)info);
            HttpEntity requestEntity = new HttpEntity((Object)multiValueMap, null);
            template.exchange(passwordErrorNoticeUrl, HttpMethod.POST, requestEntity, String.class, new Object[0]);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

