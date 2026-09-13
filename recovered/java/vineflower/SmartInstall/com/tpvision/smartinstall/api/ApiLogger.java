package com.tpvision.smartinstall.api;

import com.amazonaws.HttpMethod;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiLogger {
   private static final Logger logger = LoggerFactory.getLogger(ApiLogger.class);
   private static final ThreadLocal<Boolean> IS_REQUIRE_WRITE_LOG = new ThreadLocal<>();
   private static final int MAX_LOG_LENGTH_CHAR_COUNT = 200;

   private ApiLogger() {
   }

   public static void logApiStart(HttpServletRequest request) {
      String method = request.getMethod();
      boolean isRequireLog = !StringUtils.equalsIgnoreCase(method, "OPTIONS");
      IS_REQUIRE_WRITE_LOG.set(isRequireLog);
      String ip = request.getRemoteAddr();
      String url = request.getRequestURI();
      if (isRequireLog) {
         String parameters = getMethodParams(request, method);
         logger.info("api request from ip : {}, method: {}, url : {}, parameter:{}", ip, method, url, parameters);
      }
   }

   private static String getMethodParams(HttpServletRequest request, String method) {
      String path = request.getRequestURI().toLowerCase();
      if (path.contains("/user")) {
         return "";
      } else if (method.equalsIgnoreCase(HttpMethod.GET.toString())) {
         return request.getQueryString();
      } else {
         return request.getContentType() != null && request.getContentType().toLowerCase().contains("x-www-form-urlencoded")
            ? assemblePostParameterData(request)
            : "raw data";
      }
   }

   private static String assemblePostParameterData(HttpServletRequest request) {
      Map<String, String[]> properties = request.getParameterMap();
      List<String> paramStringList = new ArrayList<>();
      String name = "";
      String value = "";

      for (Entry<String, String[]> entry : properties.entrySet()) {
         name = entry.getKey();
         String[] values = entry.getValue();
         if (null == values) {
            value = "";
         } else if (values.length <= 1) {
            value = values[0];
         } else {
            for (int i = 0; i < values.length; i++) {
               value = values[i] + ",";
            }

            value = value.substring(0, value.length() - 1);
         }

         paramStringList.add(name + "=" + value);
      }

      return StringUtils.join(paramStringList, '&');
   }

   public static void logFilterFailure(int code, String failureInfo) {
      if (BooleanUtils.isTrue(IS_REQUIRE_WRITE_LOG.get())) {
         logger.info("request filter failure:{} ,response code={} ", failureInfo, code);
      }

      IS_REQUIRE_WRITE_LOG.remove();
   }

   public static void logMethodExecutationReturn(Object result) {
      if (BooleanUtils.isTrue(IS_REQUIRE_WRITE_LOG.get())) {
         String str = result.toString();
         if (str.length() > 200) {
            str = str.substring(0, 200) + "...";
         }

         logger.info("method return: {} ", str);
      }

      IS_REQUIRE_WRITE_LOG.remove();
   }
}
