package com.tpvision.smartinstall.servlet;

import com.tpvision.smartinstall.ImageStates;
import com.tpvision.smartinstall.util.CommonConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private static final Logger LOG = LoggerFactory.getLogger(ImageServlet.class);

   @Override
   protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String path = request.getPathInfo();
      if (null != path) {
         String[] split = path.split("/");
         if (null == split || split.length == 0) {
            return;
         }

         String imageName = split[split.length - 1];
         List<File> fileList = new ArrayList<>();
         if (path.indexOf("welcome") > -1) {
            fileList = ImageStates.instance().getImageThumbMapList().get("welcome");
         } else if (path.indexOf("smartinfoes") > -1) {
            File file = new File(CommonConstants.HOTEL_INFO_ES_THUMB_LOCATION);
            if (file.exists()) {
               for (File contents : file.listFiles()) {
                  fileList.add(contents);
               }
            }
         } else if (path.indexOf("templates") > -1) {
            fileList = ImageStates.instance().getImageThumbMapList().get("templates");
         } else if (path.indexOf("hotel") > -1) {
            fileList = ImageStates.instance().getImageThumbMapList().get("hotel");
         } else if (path.indexOf("theme") > -1) {
            fileList = ImageStates.instance().getImageMapList().get("theme");
         }

         File fileToServe = null;

         for (File f : fileList) {
            if (f.getName().equalsIgnoreCase(imageName)) {
               fileToServe = f;
               break;
            }
         }

         if (null != fileToServe) {
            response.setContentType(this.getServletContext().getMimeType(fileToServe.getName()));

            try (FileInputStream fis = new FileInputStream(fileToServe)) {
               byte[] bytes = IOUtils.toByteArray(fis);
               IOUtils.write(bytes, response.getOutputStream());
            } catch (IOException e) {
               LOG.error(e.getMessage(), e);
            }
         }
      }
   }
}
