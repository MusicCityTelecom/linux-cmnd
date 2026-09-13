package com.tpvision.smartinstall.servlet;

import com.tpvision.smartinstall.util.CommonConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/files/")
public class FileRequestController {
   private static final Logger LOG = LoggerFactory.getLogger(FileRequestController.class.getClass());

   @GetMapping("/image/pms/{fileName}")
   public void showMessageIcon(@PathVariable("fileName") String name, HttpServletResponse response) {
      String iconPath = CommonConstants.RECEPTION_LOGO_IMG_LOCATION;
      String path = iconPath + name;
      if (StringUtils.isEmpty(name) || !new File(path).exists()) {
         path = CommonConstants.servletContextPath + "/static/images/upload_normal.png";
      }

      response.setContentType(new MimetypesFileTypeMap().getContentType(path));

      try (
         ServletOutputStream outStream = response.getOutputStream();
         FileInputStream fis = new FileInputStream(path);
      ) {
         byte[] data = new byte[1000];

         while (fis.read(data) > 0) {
            outStream.write(data);
         }

         outStream.write(data);
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }
   }
}
