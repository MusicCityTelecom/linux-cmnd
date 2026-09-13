package com.tpvision.smartinstall.servlet;

import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.pms.TmsUtils;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class requestTmsMsgServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private static final Logger LOG = LoggerFactory.getLogger(requestTmsMsgServlet.class);
   public static boolean refreshFlag = false;

   @Override
   protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
      String mode = request.getParameter("mode");
      TmsUtils tms = PmsUtils.getTmsInstance();
      if (tms == null) {
         LOG.error("tms is null");
      } else {
         if ("requestBill".equals(mode)) {
            String roomId = request.getParameter("roomId");
            tms.requestBill(roomId);
         } else if ("refresh".equals(mode)) {
            tms.refresh();
         } else if ("requestRefresh".equals(mode)) {
            String roomId = request.getParameter("roomId");
            tms.requestRefresh(roomId);
         }
      }
   }
}
