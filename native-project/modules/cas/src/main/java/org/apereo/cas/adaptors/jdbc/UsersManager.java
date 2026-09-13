package org.apereo.cas.adaptors.jdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.DigestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
         String checkKey = DigestUtils.md5DigestAsHex((username + sourceIp + time + "#)(UFKDFLSDFSDFSDFSDFDFF****#JFDNF").getBytes());
         Map<String, Object> map = new HashMap<>();
         map.put("username", username);
         map.put("sourceIp", sourceIp);
         map.put("time", time);
         map.put("checkKey", checkKey);
         ObjectMapper mapper = new ObjectMapper();
         String params = mapper.writeValueAsString(map);
         String info = Base64.getEncoder().encodeToString(params.getBytes());
         response.sendRedirect("/SmartInstall/password/changeWeakPage?info=" + info);
      } catch (IOException ex) {
         ex.printStackTrace();
      }
   }

   public static void handleLoginFailure(HttpServletRequest request) {
      try {
         String username = request.getParameter("username");
         long time = System.currentTimeMillis();
         String checkKey = DigestUtils.md5DigestAsHex((username + time + "#)(UFKDFLSDFSDFSDFSDFDFF****#JFDNF").getBytes());
         Map<String, Object> map = new HashMap<>();
         map.put("username", username);
         map.put("time", time);
         map.put("checkKey", checkKey);
         ObjectMapper mapper = new ObjectMapper();
         String params = mapper.writeValueAsString(map);
         String info = Base64.getEncoder().encodeToString(params.getBytes());
         SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
         factory.setConnectTimeout(10000);
         factory.setReadTimeout(10000);
         RestTemplate template = new RestTemplate(factory);
         String passwordErrorNoticeUrl = "http://" + request.getServerName() + ":8080/SmartInstall/password/loginFailure";
         MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
         multiValueMap.add("info", info);
         HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(multiValueMap, null);
         template.exchange(passwordErrorNoticeUrl, HttpMethod.POST, requestEntity, String.class);
      } catch (IOException ex) {
         ex.printStackTrace();
      }
   }
}
