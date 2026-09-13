package com.tpvision.smartinstall.servlet;

import com.tpvision.smartinstall.api.ApiLicenseChecker;
import com.tpvision.smartinstall.util.EmailUtils;
import com.tpvision.smartinstall.util.Utils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/CustomerSatisfactionSurvey")
public class CustomerSatisfactionSurvey extends HttpServlet {
   private static final Logger LOG = LoggerFactory.getLogger(CustomerSatisfactionSurvey.class);

   @Override
   protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String star = request.getParameter("star");
      String mailAddress = request.getParameter("MailAddress");
      String roomID = request.getParameter("RoomID");
      String title = request.getParameter("Title");
      if (StringUtils.isBlank(title)) {
         title = "How would you rate your stay?";
      }

      String subject = "Room " + roomID + " gave " + star + " stars";
      String content = "<!DOCTYPE html>\r\n<html>\r\n  <body>\r\n    Hello,\r\n    <p>The guest in room "
         + roomID
         + " gave "
         + star
         + " stars on the \""
         + title
         + "\" question.\r\n    <p>\r\n      Kind regards,\r\n      <br/>\r\n      CMND\r\n      <br/><b>Ref:"
         + ApiLicenseChecker.getInstance().getSerialNumber()
         + "</b>\r\n      <br/>\r\n    </p>\r\n  </body>\r\n</html>";
      if (StringUtils.contains(mailAddress, "@")) {
         EmailUtils.sendEmailByAmazonService(subject, content, mailAddress);
      } else {
         LOG.warn("no valid email<{}> need to be sent", mailAddress);
      }

      String status = "{\"star\":\"" + star + "\"}";
      Utils.writeToResponse(status, "text/html;charset=UTF-8", response);
   }
}
