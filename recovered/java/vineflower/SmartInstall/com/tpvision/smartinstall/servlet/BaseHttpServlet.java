package com.tpvision.smartinstall.servlet;

import com.google.gson.Gson;
import com.tpvision.smartinstall.SearchParam;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.TpvFileUtils;
import com.tpvision.smartinstall.util.TpvStringUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseHttpServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;
   private static final Logger LOG = LoggerFactory.getLogger(BaseHttpServlet.class);
   public static final String TEXT_FAIL = "fail";
   public static final String TEXT_SUCCESS = "success";
   public static final String FAILED_STATUS = "{\"status\":\"fail\"}";
   public static final String SUCCESS_STATUS = "{\"status\":\"success\"}";
   public static final String TEXT_HTML = "text/html;charset=UTF-8";
   public static final String TEXT_JSON = "text/json;charset=UTF-8";
   public static final String ZERO_NONE = "0,None";

   protected String failedStatus(String reason) {
      JSONObject resObj = new JSONObject("{\"status\":\"fail\"}");
      resObj.put("reason", reason);
      resObj.put("msg", reason);
      return resObj.toString();
   }

   protected String successStatus() {
      return this.successStatus(null);
   }

   protected List<File> extractUploadedFiles(HttpServletRequest request, boolean keepOrigName) throws Exception {
      FileItemFactory factory = new DiskFileItemFactory();
      ServletFileUpload fileUpload = new ServletFileUpload(factory);
      List<File> uploadedFiles = new ArrayList<>();

      for (FileItem item : fileUpload.parseRequest(request)) {
         if (!item.isFormField()) {
            String filename = FilenameUtils.getName(item.getName());
            InputStream filecontent = item.getInputStream();
            TpvFileUtils.checkDiskSpaceFull(item.getSize());
            String tempName = TpvFileUtils.isValidFileName(filename) && keepOrigName
               ? filename
               : UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(item.getName());
            File storeFile = new File(CommonConstants.USER_ZIP_TEMP_LOCATION + tempName);
            storeFile.getParentFile().mkdirs();

            try (FileOutputStream fos = new FileOutputStream(storeFile)) {
               IOUtils.copy(filecontent, fos);
            }

            uploadedFiles.add(storeFile);
         }
      }

      return uploadedFiles;
   }

   protected void responseText(String text, HttpServletResponse response) {
      if (text == null) {
         text = "";
      }

      try (PrintWriter pw = response.getWriter()) {
         pw.write(text);
         pw.flush();
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }
   }

   protected void responseJSON(String json, HttpServletResponse response) {
      if (json == null) {
         json = "";
      }

      response.setContentType("text/json;charset=UTF-8");

      try (PrintWriter writer = response.getWriter()) {
         writer.write(json);
         writer.flush();
      } catch (IOException e) {
         LOG.error(e.getMessage(), e);
      }
   }

   protected String successStatus(Object data) {
      JSONObject resObj = new JSONObject("{\"status\":\"success\"}");
      if (data != null) {
         if (data instanceof JSONObject || data instanceof JSONArray || data instanceof String) {
            resObj.put("data", data);
         } else if (data instanceof List) {
            resObj.put("data", new JSONArray(new Gson().toJson(data)));
         } else {
            resObj.put("data", new JSONObject(new Gson().toJson(data)));
         }
      }

      return resObj.toString();
   }

   protected String successStatus(Object data1, Object data2) {
      JSONObject resObj = new JSONObject("{\"status\":\"success\"}");
      if (data1 != null) {
         if (data1 instanceof JSONObject || data1 instanceof JSONArray || data1 instanceof String) {
            resObj.put("data1", data1);
         } else if (data1 instanceof List) {
            resObj.put("data1", new JSONArray(new Gson().toJson(data1)));
         } else {
            resObj.put("data1", new JSONObject(new Gson().toJson(data1)));
         }
      }

      if (data2 != null) {
         if (data2 instanceof JSONObject || data2 instanceof JSONArray || data2 instanceof String) {
            resObj.put("data2", data1);
         } else if (data2 instanceof List) {
            resObj.put("data2", new JSONArray(new Gson().toJson(data2)));
         } else {
            resObj.put("data2", new JSONObject(new Gson().toJson(data2)));
         }
      }

      return resObj.toString();
   }

   private String getOrderFromRequest(HttpServletRequest request) {
      List<String> orderList = new ArrayList<>();
      Map<String, String[]> reqParamsMap = request.getParameterMap();

      for (String param : reqParamsMap.keySet()) {
         if (param.contains("sort[")) {
            String fieldName = param.replace("sort[", "").replace("]", "");
            if (!TpvStringUtils.isValidMysqlFieldName(fieldName)) {
               LOG.warn("sort field : {}  is unvalid : ", fieldName);
            } else {
               String sortDirection = TpvStringUtils.getSortDirectionValueFromRequest(request, param);
               if (StringUtils.isNotBlank(sortDirection)) {
                  orderList.add(fieldName + "&" + sortDirection);
               }
            }
         }
      }

      return String.join(",", orderList);
   }

   protected SearchParam buildWhereClause(HttpServletRequest request, String filterFields) {
      String searchPhrase = request.getParameter("searchPhrase");
      int current = TpvStringUtils.tryParseInt(request.getParameter("current"), 0);
      int rowCount = TpvStringUtils.tryParseInt(request.getParameter("rowCount"), 0);
      String orderParam = this.getOrderFromRequest(request);
      LOG.debug("searchPhrase={}, current={},rowCount={},orderParam={}", searchPhrase, current, rowCount, orderParam);
      SearchParam sp = new SearchParam();
      sp.setCurrentPage(current);
      sp.setRowCount(rowCount);
      sp.setSearchPhrase(searchPhrase);
      sp.setSort(orderParam);
      sp.setFilterFields(filterFields);
      return sp;
   }

   public String optParameter(HttpServletRequest request, String paramName, String defaultValue) {
      return request.getParameter(paramName) != null ? request.getParameter(paramName) : defaultValue;
   }

   public String requestContent(HttpServletRequest request) throws IOException {
      StringBuilder sb = new StringBuilder();

      String line;
      try (BufferedReader reader = request.getReader()) {
         while ((line = reader.readLine()) != null) {
            sb.append(line);
         }
      }

      return sb.toString();
   }

   public static class MessageException extends IOException {
      private static final long serialVersionUID = 1L;
      private String message;

      public MessageException(String message) {
         this.message = message;
      }

      @Override
      public String getMessage() {
         return this.message;
      }
   }
}
