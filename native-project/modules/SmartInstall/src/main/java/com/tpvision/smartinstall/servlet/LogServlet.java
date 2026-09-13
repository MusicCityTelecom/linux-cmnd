package com.tpvision.smartinstall.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/log")
public class LogServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private static final Logger LOG = LoggerFactory.getLogger(LogServlet.class);

   @Override
   protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String mode = request.getParameter("mode");

      try {
         if ("ADD_LOG".equalsIgnoreCase(mode)) {
            this.addLog(request, response);
         } else {
            LOG.warn("Unknown mode in logservlet.doPost = {}", mode);
         }
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }
   }

   private void addLog(HttpServletRequest request, HttpServletResponse response) {
      String status = "{\"status\":\"success\"}";
      StringBuilder jb = new StringBuilder();
      String line = null;

      try {
         BufferedReader reader = request.getReader();

         while ((line = reader.readLine()) != null) {
            jb.append(line);
         }
      } catch (Exception e) {
         LOG.error(e.getMessage());
      }

      String logMessage = jb.toString();
      if (null != logMessage && !logMessage.isEmpty()) {
         JSONObject msgObj = null;

         try {
            msgObj = new JSONObject(logMessage);
         } catch (Exception e) {
            LOG.error("log message parse failed:{}", logMessage);
            return;
         }

         String logType = msgObj.optString("type");
         String message = "MGate >>> SI: " + msgObj.optString("message");
         switch (logType.toLowerCase()) {
            case "trace":
               LOG.trace(message);
               break;
            case "debug":
               LOG.debug(message);
               break;
            case "info":
               LOG.info(message);
               break;
            case "warn":
               LOG.warn(message);
               break;
            case "error":
               LOG.error(message);
               break;
            default:
               LOG.info(message);
         }

         response.setContentType("text/json");

         try {
            IOUtils.write(status.getBytes(), response.getOutputStream());
         } catch (IOException e) {
            LOG.error(e.getMessage(), e);
         }
      }
   }
}
