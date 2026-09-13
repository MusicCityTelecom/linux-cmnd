package com.tpvision.smartinstall.util;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.GuestInfo;
import com.tpvision.smartinstall.dao.core.Message;
import com.tpvision.smartinstall.dao.core.PmsStatus;
import com.tpvision.smartinstall.dao.mgr.DevicesManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.MessageManager;
import com.tpvision.smartinstall.pms.PmsUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.HtmlUtils;

public class PmsMessageUtils {
   private static final Logger LOG = LoggerFactory.getLogger(PmsMessageUtils.class);
   private static final int MAX_ICON_COUNT = 10;
   private static final int MAX_NAME_LENGTH = 4;
   public static final int MAX_TITLE_LENGTH = 200;
   private static final String MESSAGE_SEND_PAGE_SUBMIT_TIME_FORMAT = "dd/MM/yyyy HH:mm";

   private PmsMessageUtils() {
   }

   public static JSONArray loadPmsIconArray() {
      JSONArray jsonArray = new JSONArray();
      String iconPath = getMessageIconPath();
      File file = new File(iconPath);
      File[] files = file.listFiles();
      if (files != null) {
         for (File icon : files) {
            jsonArray.put(icon.getName());
         }
      }

      return jsonArray;
   }

   public static void renderIcon(String name, HttpServletResponse response) {
      String iconPath = getMessageIconPath();
      String path = iconPath + name;
      File logoFile = new File(iconPath);
      if (!logoFile.exists() || null == iconPath || iconPath.isEmpty()) {
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

   private static String getMessageIconPath() {
      return String.format(Locale.ENGLISH, CommonConstants.MESSAGE_ICON_PATH_FORMAT);
   }

   public static void deleteMessageIcon(String iconName) {
      String iconPath = getMessageIconPath() + iconName;
      LOG.info("delete icon:", iconName);
      TpvFileUtils.deleteFile(iconPath);
   }

   public static String doMessageIconUpload(HttpServletRequest request) throws Exception {
      String logoPath = getMessageIconPath();
      FileItemFactory factory = new DiskFileItemFactory();
      ServletFileUpload fileUpload = new ServletFileUpload(factory);
      List<FileItem> items = fileUpload.parseRequest(request);
      String filename = "";

      for (FileItem item : items) {
         if (!item.isFormField()) {
            String var12 = FilenameUtils.getBaseName(item.getName());
            String ext = FilenameUtils.getExtension(item.getName());
            String shortName = var12.substring(0, var12.length() > 4 ? 4 : var12.length());
            filename = String.format(Locale.ENGLISH, "%s-%d.%s", shortName, System.currentTimeMillis(), ext);
            String logoFileName = logoPath + File.separator + filename;
            File logoFile = new File(logoFileName);
            if (!logoFile.getParentFile().exists()) {
               logoFile.getParentFile().mkdirs();
            }

            item.write(logoFile);
         }
      }

      tidyMessageIconFiles();
      return filename;
   }

   public static String doDefaultMessageIconUpload(HttpServletRequest request) throws Exception {
      FileItemFactory factory = new DiskFileItemFactory();
      ServletFileUpload fileUpload = new ServletFileUpload(factory);
      List<FileItem> items = fileUpload.parseRequest(request);
      String filename = "";

      for (FileItem item : items) {
         if (!item.isFormField()) {
            filename = saveIconFileToServer("message", item);
         }
      }

      return filename;
   }

   public static String doDefaultBillIconUpload(HttpServletRequest request) throws Exception {
      FileItemFactory factory = new DiskFileItemFactory();
      ServletFileUpload fileUpload = new ServletFileUpload(factory);
      List<FileItem> items = fileUpload.parseRequest(request);
      String filename = "";

      for (FileItem item : items) {
         if (!item.isFormField()) {
            filename = saveIconFileToServer("bill", item);
         }
      }

      return filename;
   }

   public static String saveIconFileToServer(String iconType, FileItem fileItem) throws Exception {
      String ext = FilenameUtils.getExtension(fileItem.getName()).toLowerCase();
      String imageName = iconType + "_" + System.currentTimeMillis() + "." + ext;
      File destIcon = new File(CommonConstants.RECEPTION_LOGO_IMG_LOCATION + imageName);
      if (destIcon.exists()) {
         FileUtils.deleteQuietly(destIcon);
      }

      fileItem.write(destIcon);
      String oldIconName = null;
      PmsStatus pmsStatus = JpaManager.getPmsStatusManager().loadByKey(1);
      if (StringUtils.equals("message", iconType)) {
         oldIconName = pmsStatus.getMessageIcon();
         pmsStatus.setMessageIcon(imageName);
      } else if (StringUtils.equals("bill", iconType)) {
         oldIconName = pmsStatus.getBillIcon();
         pmsStatus.setBillIcon(imageName);
      }

      JpaManager.getPmsStatusManager().save(pmsStatus);
      if (StringUtils.isNoneBlank(oldIconName) && !StringUtils.equalsIgnoreCase(imageName, oldIconName)) {
         File oldFile = new File(CommonConstants.RECEPTION_LOGO_IMG_LOCATION + oldIconName);
         if (oldFile.isFile()) {
            FileUtils.deleteQuietly(oldFile);
         }
      }

      return imageName;
   }

   public static List<Message> createMessage(String roomIds, String title, String content, String icon, String sendTime) {
      String[] roomIdArray = roomIds.split(",");
      SimpleDateFormat dbFormatter = TpvDateUtils.getMessageTimeFormat();
      List<GuestInfo> gis = JpaManager.getGuestInfoManager().findGuestInfosByRoomids(roomIdArray);
      MessageManager msgmgr = JpaManager.getMessageManager();
      DevicesManager devicesManager = JpaManager.getDevicesManager();
      List<Message> addedMessages = new ArrayList<>();

      for (int i = 0; i < gis.size(); i++) {
         GuestInfo guest = gis.get(i);
         if ("N".equalsIgnoreCase(guest.getCheckin())) {
            LOG.warn("guest <{}> not checked in skip to send message", guest.getGuestId());
         } else {
            List<Devices> tvs = devicesManager.findDevicesByRoomId(guest.getRoomid());
            boolean isSupportMessage = false;

            for (Devices tv : tvs) {
               if (PlatformUtils.isSupportMessage(tv.getType())) {
                  isSupportMessage = true;
                  break;
               }
            }

            if (!isSupportMessage) {
               LOG.warn("guest <{}> in room <{}> related device not support message", guest.getGuestId(), guest.getRoomid());
            } else {
               Message message = msgmgr.createMessage();
               message.setContent(content);
               message.setStatus("New");
               message.setGuestIds(String.valueOf(guest.getRoomid()));
               message.setIsSent("N");
               if (title.length() > 200) {
                  title = title.substring(0, 200);
               }

               message.setTitle(title);
               message.setIcon(icon);
               if ("Now".equals(sendTime)) {
                  message.setTimeSend(dbFormatter.format(new Date()));
                  msgmgr.save(message);
                  if (PmsUtils.sendMsg2TV(message.getGuestIds())) {
                     LOG.info("send new message to TV:{}", message.getGuestIds());
                  }
               } else {
                  try {
                     SimpleDateFormat formatter = TpvDateUtils.getSimpleDateFormatWithEnglishLocale("dd/MM/yyyy HH:mm");
                     message.setTimeSend(dbFormatter.format(formatter.parse(sendTime)));
                     msgmgr.save(message);
                  } catch (Exception e) {
                     LOG.error(e.getMessage(), e);
                  }
               }

               addedMessages.add(message);
               PmsUtils.setMessageUpdated();
            }
         }
      }

      return addedMessages;
   }

   private static void tidyMessageIconFiles() throws IOException {
      String logoPath = getMessageIconPath();
      File file = new File(logoPath);
      File[] files = file.listFiles();
      Arrays.sort(files, (f1, f2) -> {
         long diff = f1.lastModified() - f2.lastModified();
         if (diff > 0L) {
            return 1;
         } else {
            return diff == 0L ? 0 : -1;
         }
      });
      if (files.length > 10) {
         for (int i = 0; i < files.length - 10; i++) {
            LOG.info("remove old icon files:{}", files[i].getName());
            if (!files[i].delete()) {
               throw new IOException("delete file " + files[i].getName() + " failed");
            }
         }
      }

      syncMessageIconCached();
   }

   public static void syncMessageIconCached() {
      String logoPath = getMessageIconPath();
      File file = new File(logoPath);
      File[] files = file.listFiles();
      if (null == files) {
         LOG.error("no messageicons need to sync");
      } else {
         File targetPath = new File(CommonConstants.servletContextPath + "/static/images/messageicons/");
         targetPath.mkdirs();

         for (File iconFile : files) {
            File newFile = new File(targetPath.getAbsolutePath() + "/" + iconFile.getName());
            if (!newFile.exists()) {
               try {
                  FileUtils.copyFile(iconFile, newFile);
               } catch (IOException e) {
                  LOG.error(e.getMessage(), e);
               }
            }
         }
      }
   }

   public static JSONArray getGuestMessagesArray(Devices tv, List<Message> messages) {
      JSONArray array = new JSONArray();
      if (tv == null) {
         return array;
      }

      SimpleDateFormat dateFormat1 = TpvDateUtils.getMessageTimeFormat();
      SimpleDateFormat dateFormat2 = TpvDateUtils.getSimpleDateFormatWithEnglishLocale("dd/MM/yyyy");
      SimpleDateFormat dateFormat3 = TpvDateUtils.getSimpleDateFormatWithEnglishLocale("HH:mm");
      Date date = new Date();

      for (Message m : messages) {
         if (!"Delete".equalsIgnoreCase(m.getStatus())) {
            try {
               date = dateFormat1.parse(m.getTimeSend());
            } catch (ParseException e) {
               LOG.error("parse date failed:id={},timesend={}", m.getId(), m.getTimeSend());
            }

            JSONObject jsObj = new JSONObject();
            jsObj.put("ID", m.getMsgId());
            jsObj.put("From", "Reception");
            jsObj.put("MessageDate", dateFormat2.format(date));
            jsObj.put("MessageTime", dateFormat3.format(date));
            jsObj.put("Status", m.getStatus());
            String content = HtmlUtils.htmlUnescape(m.getContent());
            if (PlatformUtils.isPmsMessageUnicode(tv.getType())) {
               String title = HtmlUtils.htmlUnescape(m.getTitle());
               jsObj.put("MessageTitle", TpvStringUtils.string2Unicode(title));
               String icon = m.getIcon();
               if (null != icon && !icon.isEmpty()) {
                  String serverPath = JAPITUtils.getServerRequestPath(tv);
                  jsObj.put("MessageIcon", serverPath + "/static/images/messageicons/" + icon);
               } else {
                  String defaultMessageIcon = JpaManager.getPmsStatusManager().loadByKey(1).getMessageIcon();
                  if (StringUtils.isNoneBlank(defaultMessageIcon)) {
                     String serverPath = JAPITUtils.getServerRequestPath(tv);
                     jsObj.put("MessageIcon", serverPath + "files/image/pms/" + defaultMessageIcon);
                  }
               }

               jsObj.put("Message", TpvStringUtils.string2Unicode(content));
            } else {
               jsObj.put("Message", content);
            }

            array.put(jsObj);
         }
      }

      return array;
   }
}
