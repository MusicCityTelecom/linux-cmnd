package com.tpvision.smartinstall.servlet;

import com.tpvision.smartinstall.util.HttpUtils;
import com.tpvision.smartinstall.util.Utils;
import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class URLRedirectServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private static final Logger LOG = LoggerFactory.getLogger(URLRedirectServlet.class);

   @Override
   protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      StringBuilder sb = new StringBuilder();

      String line;
      try (BufferedReader reader = request.getReader()) {
         while ((line = reader.readLine()) != null) {
            sb.append(line).append('\n');
         }
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }

      String requestHeader = sb.toString();
      String clientip = request.getRemoteAddr();
      line = request.getLocalAddr();
      int serverPort = request.getServerPort();
      boolean bHttps = HttpUtils.isSupportHttpsByIp(clientip);
      String strURL = HttpUtils.getHttpString(bHttps) + line + ":" + serverPort + "/SmartInstall/webservices.jsp?clientip=" + clientip;
      String strRsp = "";

      try {
         strRsp = HttpUtils.send(bHttps, true, strURL, requestHeader, 10000);
         if (strRsp != null && strRsp.length() > 0) {
            Utils.writeToResponse(strRsp, "text/html", response);
         }
      } catch (Exception e) {
         LOG.error(e.getMessage());
      }
   }
}
