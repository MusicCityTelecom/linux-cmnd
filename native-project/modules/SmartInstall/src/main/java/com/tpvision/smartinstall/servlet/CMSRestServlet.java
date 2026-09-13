package com.tpvision.smartinstall.servlet;

import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.core.SmartcmsSetting;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.SettingManager;
import com.tpvision.smartinstall.dao.mgr.SmartcmsSettingManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = "/rest/api/website/v1/*")
public class CMSRestServlet extends HttpServlet {
   private static final Logger LOG = LoggerFactory.getLogger(CMSRestServlet.class);
   private static final long serialVersionUID = 1L;
   public static final String FILE_SEPARATOR = System.getProperty("file.separator");

   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setHeader("Access-Control-Allow-Origin", "*");
      String mode = request.getParameter("mode");
      if (mode.equalsIgnoreCase("tag")) {
         int nid = Integer.parseInt(request.getParameter("nid"));
         String userName = request.getParameter("user");
         String cmsName = request.getParameter("name");
         int cloneId = 0;
         String cloneName = null;
         SettingManager smgr = JpaManager.getSettingManager();
         List<Setting> settings = smgr.findSettingListByLastUpdatedByOrderByLastUpdatedDateDesc(userName);
         if (!settings.isEmpty()) {
            cloneName = settings.get(0).getClonerename();
            cloneId = settings.get(0).getId();
         }

         SmartcmsSettingManager sismgr = JpaManager.getSmartcmsSettingManager();
         List<SmartcmsSetting> cmssettings = sismgr.findSmartcmsSettingBySettingId(cloneId);
         SmartcmsSetting cms = null;
         if (null != cmssettings && cmssettings.size() > 0) {
            cms = cmssettings.get(0);
         } else {
            cms = new SmartcmsSetting();
            cms.setSettingId(cloneId);
            cms.setCreatedBy(userName);
            cms.setCreatedDate(new Date());
         }

         cms.setSmartcmsId(nid);
         cms.setName(cmsName);
         cms.setLastUpdatedBy(userName);
         cms.setLastUpdatedDate(new Date());
         sismgr.save(cms);
         LOG.info(" done ");
         response.setContentType("application/json");
         response.setCharacterEncoding("utf-8");
         JSONObject json = new JSONObject();
         if (null != cloneName && !cloneName.equalsIgnoreCase(null)) {
            json.put("success", "success");
            json.put("message", " successfully tagged to clone '" + cloneName + "'");
            json.put("nid", nid);
         } else {
            json.put("success", "fail");
            json.put("message", "The clone file is not selected");
         }

         try (PrintWriter out = response.getWriter()) {
            LOG.info(json.toString());
            out.print(json.toString());
            out.flush();
         } catch (IOException ex) {
            LOG.error(ex.getMessage(), ex);
         }
      }
   }

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      this.doGet(request, response);
   }

   @Override
   public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
      PrintWriter out = response.getWriter();
      out.write("PUT method (inserting data) was invoked!");
      out.flush();
   }

   @Override
   public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
      PrintWriter out = response.getWriter();
      out.write("DELETE method (removing data) was invoked!");
   }
}
