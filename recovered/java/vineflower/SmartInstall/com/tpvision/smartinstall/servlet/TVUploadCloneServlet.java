package com.tpvision.smartinstall.servlet;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.mgr.DevicesManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.UploadCloneUtils;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/CloneToServer")
public class TVUploadCloneServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private static final Logger LOG = LoggerFactory.getLogger(TVUploadCloneServlet.class);
   private static final String TEMPFILEPATH = CommonConstants.SISERVER_UPLOAD_DIR + "temp/";

   @Override
   protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      try {
         this.execute(request, response);
      } catch (Exception e) {
         response.setStatus(400);
         LOG.error(e.getMessage(), e);
      }
   }

   private void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      String tvIpaddress = request.getParameter("clientip");
      if (StringUtils.isEmpty(tvIpaddress)) {
         tvIpaddress = request.getRemoteAddr();
      }

      Devices tvInfo = this.getTvInfoByIp(tvIpaddress);
      if (tvInfo == null) {
         LOG.error("get uploaded tv by clientip {} failure", tvIpaddress);
      } else {
         try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            LOG.info("real.chen: start to receive zip file from ip={}", tvIpaddress);
            response.setContentType("text/plain");
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(4096);
            File isCreat = new File(TEMPFILEPATH);
            if (!isCreat.exists()) {
               isCreat.mkdirs();
            }

            factory.setRepository(new File(TEMPFILEPATH));
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(1572864000L);
            List<FileItem> items = upload.parseRequest(request);
            Iterator<FileItem> iter = items.iterator();
            FileItem fileItem = null;

            try (PrintWriter outNet = response.getWriter()) {
               while (iter.hasNext()) {
                  FileItem item = iter.next();
                  if (item.isFormField()) {
                     String name = item.getFieldName();
                     String value = item.getString("UTF-8");
                     outNet.println(name + ":" + value + "\r\n");
                     outNet.flush();
                  } else {
                     fileItem = item;
                  }
               }

               if (null != fileItem) {
                  this.processUploadedFile(tvInfo, fileItem, outNet);
               }
            } catch (IOException ex) {
               LOG.error(ex.getMessage(), ex);
            }

            stopWatch.stop();
            LOG.info("upload costtime:{}ms", stopWatch.getTime());
         } catch (Exception e) {
            String errorStr = "[execute]tvType:" + tvInfo.getType() + ",tvUniqueID:" + tvInfo.getTvuniqueid() + "upload error:" + e.getMessage();
            LOG.error(errorStr, e);
            throw e;
         }
      }
   }

   private void processUploadedFile(Devices tvInfo, FileItem item, PrintWriter outNet) throws Exception {
      String filename = item.getName();
      int index = filename.lastIndexOf(47);
      filename = filename.substring(index + 1, filename.length());
      long fileSize = item.getSize();
      if ("".equals(filename) && fileSize == 0L) {
         LOG.error("upload file is empty");
      } else {
         String tvUniqueID = tvInfo.getTvuniqueid();
         String path = CommonConstants.SISERVER_UPLOAD_DIR + tvUniqueID;
         File isCreat = new File(path);
         if (!isCreat.exists()) {
            isCreat.mkdirs();
         }

         File uploadedFile = new File(path + File.separatorChar + filename);
         item.write(uploadedFile);
         uploadedFile.setExecutable(true);
         uploadedFile.setReadable(true);
         uploadedFile.setWritable(true);
         outNet.println(filename + " is saved. size: " + fileSize + "\r\n");
         LOG.info("saved file:{},fileSize:{}b", uploadedFile.getAbsolutePath(), fileSize);
         outNet.flush();
         int addOneUploadItem = 0;
         if (UploadCloneUtils.uploadItemsRecvCount_cmnd.containsKey(tvUniqueID)) {
            addOneUploadItem = UploadCloneUtils.uploadItemsRecvCount_cmnd.get(tvUniqueID);
         }

         UploadCloneUtils.uploadItemsRecvCount_cmnd.put(tvUniqueID, addOneUploadItem + 1);
         LOG.info(
            "tvUniqueID:{},requireUploadSize:{},currentUploadCount:{}",
            tvUniqueID,
            UploadCloneUtils.uploadItemsCount_tv.get(tvUniqueID),
            UploadCloneUtils.uploadItemsRecvCount_cmnd.get(tvUniqueID)
         );
      }
   }

   private Devices getTvInfoByIp(String clientip) {
      DevicesManager iptvmanager = JpaManager.getDevicesManager();

      for (Devices tv : iptvmanager.findDevicesByTvipaddressAndCloneMode(clientip, "Upload")) {
         if (UploadCloneUtils.uploadItemsCount_tv.containsKey(tv.getTvuniqueid())) {
            return tv;
         }
      }

      return null;
   }
}
