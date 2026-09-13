package com.tpvision.smartinstall.servlet;

import com.google.gson.Gson;
import com.tpvision.smartinstall.core.SettingChannelBean;
import com.tpvision.smartinstall.core.SettingCreator;
import com.tpvision.smartinstall.dao.core.ChannelPackage;
import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.Setting;
import com.tpvision.smartinstall.dao.core.SettingPackage;
import com.tpvision.smartinstall.dao.core.SmartcmsSetting;
import com.tpvision.smartinstall.dao.core.SmartinfoSetting;
import com.tpvision.smartinstall.dao.mgr.AppPackageManager;
import com.tpvision.smartinstall.dao.mgr.ChannelPackageManager;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.ScheduleManager;
import com.tpvision.smartinstall.dao.mgr.SettingManager;
import com.tpvision.smartinstall.dao.mgr.SettingPackageManager;
import com.tpvision.smartinstall.dao.mgr.SmartcmsSettingManager;
import com.tpvision.smartinstall.dao.mgr.SmartinfoSettingManager;
import com.tpvision.smartinstall.dao.mgr.UiCustomizationsManager;
import com.tpvision.smartinstall.dao.mgr.WelcomeManager;
import com.tpvision.smartinstall.util.CloneItemUtils;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.PlatformUtils;
import com.tpvision.smartinstall.util.SettingState;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.Utils;
import com.tpvision.smartinstall.util.CommonConstants.CloneItemType;
import com.tpvision.smartinstall.util.SettingState.ParamConverter;
import com.tpvision.smartinstall.weather.WeatherServiceImpl;
import com.tpvision.smartinstall.xml.setting.v2k16.roomspecific.Item;
import com.tpvision.smartinstall.xml.setting.v2k16.roomspecific.RoomSpecificSettings;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = "/setting/*")
public class SettingServlet extends BaseHttpServlet {
   private static final long serialVersionUID = 1L;
   private static final Logger LOG = LoggerFactory.getLogger(SettingServlet.class);
   private static String snameUpload = "";
   private static Random randomGenerator = new Random();

   @Override
   protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String mode = request.getParameter("mode");
      String sname = request.getParameter("sname");
      String umode = request.getAttribute("umode") != null ? (String)request.getAttribute("umode") : null;
      if (null != umode) {
         mode = "SET";
         sname = (String)request.getAttribute("configName");
         snameUpload = sname;
      }

      String overwrite = (String)request.getAttribute("overwrite");
      if (null != overwrite && overwrite.equalsIgnoreCase("yes")) {
         request.setAttribute("overwrite", "true");
      }

      if ((!"SET".equalsIgnoreCase(mode) || !"".equals(sname.trim())) && (!"SET".equalsIgnoreCase(mode) || !"null".equals(sname.trim()))) {
         if ("CHANGESETTINGNAME".equalsIgnoreCase(mode)) {
            this.modeChangeSettingName(request, response);
         } else if ("SET".equalsIgnoreCase(mode)) {
            this.modeSet(request, response);
         } else if ("LIST_SETTING_PROFILE".equals(mode)) {
            this.modeListSettingProfile(request, response);
         } else if ("FIND_SETTING_PROFILE".equals(mode)) {
            this.modeFindSettingProfile(request, response);
         } else if ("updateHotelInfo".equals(mode)) {
            this.modeUpdateHotelInfo(request, response);
         } else if ("DELETE_HOTEL".equals(mode)) {
            this.modeDeleteHotel(request, response);
         } else if ("DELETE_CLONE".equals(mode)) {
            this.modeDeleteClone(request, response);
         } else if ("COPY_CLONE".equals(mode)) {
            this.modeCopyClone(request, response);
         } else if ("CHECK_ONLINE".equals(mode)) {
            HttpSession session = request.getSession();
            long nextInterval = -1L;
            if (session.getMaxInactiveInterval() > 0) {
               nextInterval = (session.getMaxInactiveInterval() + 5) * 1000;
            }

            JSONObject result = new JSONObject();
            result.put("nextCheckInterval", nextInterval).put("id", session.getId());
            Utils.renderSuccessJsonData(result, response);
         } else if ("Clone_Is_Old".equals(mode)) {
            this.modeCloneIsOld(request, response);
         } else if ("ASSIGN_CHANNEL_PACKAGE".equals(mode)) {
            this.modeAssignChannelPackage(request, response);
         }
      } else {
         response.sendRedirect(request.getContextPath() + "/getFile?mode=index&config_error=config_error");
      }
   }

   protected void modeChangeSettingName(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String status = "{\"status\":\"fail\",\"errorCode\":\"-1\"}";
      boolean namefound = false;
      String newname = request.getParameter("name");
      String id = request.getParameter("id");
      SettingManager sm = JpaManager.getSettingManager();
      List<Setting> settings = sm.loadAll();
      List<String> names = new ArrayList<>();

      for (int j = 0; settings.size() != j; j++) {
         names.add(settings.get(j).getClonerename());
         if (settings.get(j).getClonerename().equalsIgnoreCase(newname)) {
            status = "{\"status\":\"fail\",\"errorCode\":\"0\"}";
            namefound = true;
         }
      }

      if (!namefound) {
         Setting setting = sm.loadByKey(Integer.parseInt(id));
         if (null != setting) {
            setting.setClonerename(newname);
            sm.save(setting);
            status = "{\"status\":\"success\",\"errorCode\":\"1\"}";
         }
      }

      response.setContentType("text/json");
      IOUtils.write(status.getBytes(), response.getOutputStream());
   }

   protected void modeAssignChannelPackage(HttpServletRequest request, HttpServletResponse response) {
      String sid = request.getParameter("sid");
      String[] setGroup = sid.split(",");
      String[] fullcpid = request.getParameter("cpid").split("_");
      int cpid = Integer.parseInt(fullcpid[1]);
      if (cpid > 0) {
         ChannelPackageManager cpm = JpaManager.getChannelPackageManager();
         ChannelPackage cp = cpm.loadByKey(cpid);
         if (cp == null) {
            LOG.error("selected ChannelPackage:{} not exists", cpid);
            Utils.renderErrorJsonMsg("{\"status\":\"fail\"}", response);
            return;
         }
      }

      SettingManager sm = JpaManager.getSettingManager();

      for (int i = 0; i < setGroup.length; i++) {
         Setting set = sm.loadByKey(Integer.parseInt(setGroup[i]));
         if (null != set) {
            set.setChannelPackageId(cpid);
            set.setLastUpdatedDate(new Date());
            sm.save(set);
         }
      }

      Utils.renderSuccessJsonData(response);
   }

   private SettingChannelBean getMergedSettingChannelBeanBySetting(Setting setting) {
      SettingChannelBean scb = new Gson().fromJson(setting.getValue(), SettingChannelBean.class);
      if (setting.getSettingPackageId() > 0) {
         SettingPackage settingPackage = JpaManager.getSettingPackageManager().loadByKey(setting.getSettingPackageId());
         if (settingPackage != null) {
            SettingChannelBean settingPackageBean = new Gson().fromJson(settingPackage.getValue(), SettingChannelBean.class);
            scb.mergeWithSettingPackageBean(settingPackageBean);
         }
      }

      if (setting.getChannelPackageId() > 0) {
         ChannelPackage channelPackage = JpaManager.getChannelPackageManager().loadByKey(setting.getChannelPackageId());
         if (channelPackage != null) {
            SettingChannelBean channelPackageBean = new Gson().fromJson(channelPackage.getValue(), SettingChannelBean.class);
            scb.mergeWithChanelPackageBean(channelPackageBean);
         }
      }

      return scb;
   }

   protected void modeSet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String sname = request.getParameter("sname");
      if (null != sname) {
         sname = sname.replace(":scrpt:", "script");
      }

      String umode = request.getAttribute("umode") != null ? (String)request.getAttribute("umode") : null;
      boolean scbmode = request.getParameter("scbmode") != null;
      String multiRemoteControlChecked = request.getParameter("MultiRemoteControlChecked");
      if (null != umode) {
         scbmode = true;
         sname = (String)request.getAttribute("configName");
      }

      SettingManager smgr = JpaManager.getSettingManager();
      boolean formSubmition = request.getParameter("formSubmit") != null;
      String cloneloading = request.getParameter("cloneloading") == null ? "false" : request.getParameter("cloneloading");
      List<Setting> settings = smgr.findSettingsByName(sname);
      String fname = null;
      if (settings.isEmpty()) {
         response.sendRedirect("/SmartInstall/getFile?mode=index");
      } else {
         Setting setting = settings.get(0);
         SettingChannelBean scb = this.getMergedSettingChannelBeanBySetting(setting);
         if (setting.getPlatform().equalsIgnoreCase("")) {
            File folderName = new File(CommonConstants.CLONE_PROCESS_LOCATION + setting.getName() + File.separator);
            String[] names = folderName.list();

            for (String name : names) {
               fname = name;
            }

            setting.setPlatform(fname);
         }

         String uid = request.getParameter("uid");
         HttpSession session = request.getSession();
         session.setAttribute("cloneName", sname);
         session.setAttribute("uid", uid);
         session.setAttribute("platform", setting.getPlatform());
         Object hotelImageFileName = request.getAttribute("HOTEL_IMAGE_FILE_NAME");
         Object welcomelogoImageFileName = request.getAttribute("WELCOMELOGO_IMAGE_FILE_NAME");
         if (null != hotelImageFileName) {
            scb.setHotelImageName((String)hotelImageFileName);
         }

         if (null != welcomelogoImageFileName) {
            scb.setWelcomelogoImageName((String)welcomelogoImageFileName);
         }

         this.setWelcomeMsg(scb, request);
         this.setSwitchOnSource(scb, request);
         this.setClockChannel(scb, request);
         this.setDiagnosticAnalytic(scb, request);
         if (null != setting) {
            for (com.tpvision.smartinstall.xml.Setting s : scb.getSetttings().getSetting()) {
               if (s.getRefFile() == null
                  || s.getRefFile() != null
                     && (
                        "ES2K12".equalsIgnoreCase(s.getXaddr())
                           || "ES2K13".equalsIgnoreCase(s.getXaddr())
                           || "MS2K14".equalsIgnoreCase(s.getXaddr())
                           || "MS2K16".equalsIgnoreCase(s.getXaddr())
                           || "MS2K15".equalsIgnoreCase(s.getXaddr())
                           || "ES2K16".equalsIgnoreCase(s.getXaddr())
                     )) {
                  request.setAttribute("ESMODE", "true");
                  break;
               }
            }

            SettingState ss = null;
            if (!formSubmition) {
               ss = new SettingState(scb);
               request.setAttribute("roomSettings", this.getRoomSepcificSettings(scb.getRoomSpecificSettings()));
            } else {
               ss = new SettingState(request);
               if (scb.getRoomSpecificSettings() != null) {
                  this.setRoomSepcificSettings(scb.getRoomSpecificSettings(), request);
               }
            }

            Map<String, String> map = ss.getSettingToValuesMap();
            String geonameID = null;

            for (Entry<String, String> entry : map.entrySet()) {
               if (entry.getValue() != null) {
                  for (com.tpvision.smartinstall.xml.Setting s : scb.getSetttings().getSetting()) {
                     if (setting.getPlatform().startsWith("TPN14")) {
                        if ("Oncredit".equalsIgnoreCase(s.getLastValue())) {
                           s.setLastValue("On(credit)");
                        }
                     } else if (setting.getPlatform().startsWith("TPM153")) {
                        if ("Stereo uncompressed".equalsIgnoreCase(s.getLastValue())) {
                           s.setLastValue("Stereo (uncompressed)");
                        } else if ("Deive".equalsIgnoreCase(s.getLastValue())) {
                           s.setLastValue("Descriptive");
                        } else if ("Chinese Cantonese".equalsIgnoreCase(s.getLastValue())) {
                           s.setLastValue("Chinese (Cantonese)");
                        } else if ("Chinese Mandarin".equalsIgnoreCase(s.getLastValue())) {
                           s.setLastValue("Chinese (Mandarin)");
                        } else if ("Scottish Gaelic Gàidhlig".equalsIgnoreCase(s.getLastValue())) {
                           s.setLastValue("Scottish Gaelic (Gàidhlig)");
                        } else if ("Irish Gaelic Gaeilge".equalsIgnoreCase(s.getLastValue())) {
                           s.setLastValue("Irish Gaelic (Gaeilge)");
                        }
                     } else if (setting.getPlatform().equals("TPN161HE_CloneData") && "Deive".equalsIgnoreCase(s.getLastValue())) {
                        s.setLastValue("Descriptive");
                        break;
                     }

                     if (s.getRefFile() == null || !s.getRefFile().startsWith("BdsLastStatus")) {
                        if (s.getRefFile() != null
                           || s.getXaddr() == null
                           || !s.getXaddr().equalsIgnoreCase("ES2K12") && !s.getXaddr().equalsIgnoreCase("ES2K13") && !s.getXaddr().equalsIgnoreCase("MS2K14")) {
                           if (s.getRefFile() != null
                              && !s.getRefFile().startsWith("LastStatus")
                              && !s.getRefFile().equalsIgnoreCase("ES2K12")
                              && s.getXaddr() != null
                              && !s.getXaddr().equalsIgnoreCase("ES2K13")
                              && !s.getXaddr().equalsIgnoreCase("MS2K14")
                              && entry.getValue() != null
                              && s.getItem().equalsIgnoreCase(entry.getKey())) {
                              if (entry.getValue().equalsIgnoreCase("chfalse")) {
                                 s.setLastValue("0");
                              } else {
                                 s.setLastValue(entry.getValue());
                              }
                           } else if (s.getXaddr() != null
                              && (
                                 s.getXaddr().equalsIgnoreCase("ES2K12")
                                    || s.getXaddr().equalsIgnoreCase("ES2K13")
                                    || s.getXaddr().equalsIgnoreCase("MS2K14")
                                    || s.getXaddr().equalsIgnoreCase("MS2K16")
                              )
                              && s.getRefFile() != null
                              && (
                                 s.getRefFile().equalsIgnoreCase("ES2K12")
                                    || s.getXaddr().equalsIgnoreCase("ES2K13")
                                    || s.getXaddr().equalsIgnoreCase("MS2K14")
                                    || s.getXaddr().equalsIgnoreCase("MS2K16")
                                    || s.getXaddr().equalsIgnoreCase("ES2K16")
                                    || s.getRefFile().equalsIgnoreCase("")
                              )
                              && map.get(s.getItem()) != null
                              && s.getItem().equalsIgnoreCase(entry.getKey())) {
                              if (!entry.getValue().equalsIgnoreCase("chfalse")) {
                                 s.setLastValue(entry.getValue());
                              } else if (null == umode && !scbmode) {
                                 s.setLastValue("0");
                              } else {
                                 s.setLastValue(s.getLastValue());
                              }
                           }
                        } else {
                           if (map.get(s.getItem()) != null && !map.get(s.getItem()).equalsIgnoreCase("chfalse")) {
                              s.setLastValue(entry.getValue());
                           }

                           if (s.getXaddr() != null && s.getXaddr().equalsIgnoreCase("ES2K13")) {
                              s.setRefFile("ES2K13");
                           } else if (s.getXaddr() != null && s.getXaddr().equalsIgnoreCase("MS2K14")) {
                              s.setRefFile("MS2K14");
                           } else if (s.getXaddr() != null && s.getXaddr().equalsIgnoreCase("MS2K16")) {
                              s.setRefFile("MS2K16");
                           } else if (s.getXaddr() != null && s.getXaddr().equalsIgnoreCase("ES2K16")) {
                              s.setRefFile("ES2K16");
                           } else {
                              s.setRefFile("ES2K12");
                           }
                        }

                        if ("Advanced.Identification Settings.Premises Geoname LocationID".equals(s.getItem())) {
                           geonameID = map.get(s.getItem());
                           setting.setGeonameId(geonameID);
                        }
                     } else if (entry.getValue() != null && s.getItem1().equalsIgnoreCase(entry.getKey())) {
                        if (entry.getValue().equalsIgnoreCase("chfalse")) {
                           s.setLastValue("0");
                        } else {
                           s.setLastValue1(entry.getValue());
                        }
                     }

                     if (setting.getPlatform().startsWith("TPM153")) {
                        if ("Features.Multi Remote Control".equalsIgnoreCase(s.getItem())) {
                           if (null != multiRemoteControlChecked && !"null".equalsIgnoreCase(multiRemoteControlChecked)) {
                              if ("Yes".equalsIgnoreCase(multiRemoteControlChecked)) {
                                 s.setCloneIn("Yes");
                              } else {
                                 s.setCloneIn("No");
                              }
                           }

                           multiRemoteControlChecked = s.getCloneIn();
                        }
                     } else if (setting.getPlatform().startsWith("TPM161") && "Feature Settings.Multi Remote Control".equalsIgnoreCase(s.getItem())) {
                        if (null != multiRemoteControlChecked && !"null".equalsIgnoreCase(multiRemoteControlChecked)) {
                           if ("Yes".equalsIgnoreCase(multiRemoteControlChecked)) {
                              s.setCloneIn("Yes");
                           } else {
                              s.setCloneIn("No");
                           }
                        }

                        multiRemoteControlChecked = s.getCloneIn();
                     }
                  }
               }
            }

            setting.setValue(scb.exportSettingSaveJson());
            if ("true".equalsIgnoreCase(cloneloading)) {
               setting.setLastUpdatedBy(Utils.getAuthenticationName());
            }

            if (formSubmition) {
               setting.setLastUpdatedDate(new Date());
               this.updateSettingIdentifier(setting.getName(), new Date(), setting.getPlatform());
            }

            smgr.save(setting);
            int spid = setting.getSettingPackageId();
            SettingPackage settingPackage = JpaManager.getSettingPackageManager().loadByKey(spid);
            if (null != settingPackage) {
               settingPackage.setValue(scb.exportSettingPackageSaveJson());
               JpaManager.getSettingPackageManager().save(settingPackage);
            }

            WeatherServiceImpl wsi = new WeatherServiceImpl();
            wsi.refreshCurrentWeather(geonameID, null);
            this.setWelcomeMsg(scb, request);
            this.setSwitchOnSource(scb, request);
            this.setClockChannel(scb, request);
            request.setAttribute("scb", scb);
            request.setAttribute("configName", sname);
            request.setAttribute("MSG", "successfully uploaded");
            int appPackageId = setting.getAppPackageId();
            request.getSession().setAttribute("appPackageId", appPackageId);
            String platform = "TPN161HE_CloneData";
            String name = sname;
            String flag = "old";
            String userName = Utils.getAuthenticationName();
            String path = CommonConstants.CLONE_PROCESS_LOCATION + userName + File.separator + name + File.separator + platform + File.separator + "DataDump";
            File[] file = new File(path).listFiles();
            if (null != file && file.length > 0) {
               for (File f : file) {
                  if (f.isFile() && f.getName().contains("CSM")) {
                     String str;
                     try (
                        FileReader fw = new FileReader(f);
                        BufferedReader bf = new BufferedReader(fw);
                     ) {
                        while ((str = bf.readLine()) != null) {
                           if (str.contains("Current Main Software") && str.compareTo("2.1 Current Main Software: TPN161HE_2.190.") > 0) {
                              flag = "new";
                           }
                        }
                        break;
                     } catch (Exception e) {
                        LOG.error(e.getMessage(), e);
                     }
                  }
               }
            }

            request.setAttribute("161version", flag);
            request.setAttribute("MultiRemoteControlChecked", multiRemoteControlChecked);
            request.setAttribute("Platform", PlatformUtils.getPlatformName(settingPackage.getPlatform()));
            request.setAttribute("DisplayName", settingPackage.getName());
            request.setAttribute("channelPackageId", setting.getChannelPackageId());
            request.setAttribute("appPackageId", appPackageId);
            String channelPackageId = request.getParameter("channelPackageId");
            request.getSession().setAttribute("channelPackageId", channelPackageId);
            String platformId = setting.getPlatform();
            String jspUrl = PlatformUtils.getConfigJSPUrl(platformId);
            session.setAttribute("platformName", PlatformUtils.getPlatformName(platformId));
            request.setAttribute("platformName", platformId);
            request.getRequestDispatcher(jspUrl).forward(request, response);
            session.setAttribute("channelPackageId", setting.getChannelPackageId());
         }

         ArrayList<String> details = new ArrayList<>();
         details.add(session.getAttribute("cloneName").toString().trim());
         details.add(session.getAttribute("platform").toString().trim());
      }
   }

   protected void modeUpdateHotelInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      List<FileItem> items = null;
      JSONObject msg = new JSONObject();
      msg.put("result", "fail");

      try {
         items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
      } catch (FileUploadException e1) {
         LOG.error(e1.getMessage(), e1);
      }

      InputStream filecontent = null;
      String file_name = null;
      String id = "null";

      for (FileItem item : items) {
         filecontent = item.getInputStream();
         file_name = item.getName();
      }

      id = Integer.toString(randomGenerator.nextInt(10000));
      byte[] content = IOUtils.toByteArray(filecontent);
      if (null != content
         && content.length > 0
         && null != file_name
         && (file_name.endsWith("jpg") || file_name.endsWith("JPG") || file_name.endsWith("jpeg") || file_name.endsWith("JPEG"))) {
         String imageName = DigestUtils.md5Hex(id);
         File f = new File(CommonConstants.HOTEL_INFO_IMG_LOCATION + imageName);
         File file = new File(CommonConstants.HOTEL_INFO_THUMB_IMG_LOCATION + imageName);
         FileUtils.writeByteArrayToFile(f, content);
         FileUtils.writeByteArrayToFile(file, content);

         try {
            BufferedImage image = ImageIO.read(file);
            if (null != image) {
               int height = image.getHeight();
               int width = image.getWidth();
               if (width == 1920 && height == 1080) {
                  BufferedImage thumbnail = Scalr.resize(image, 160, 90);
                  ImageIO.write(thumbnail, "jpg", file);
                  image.flush();
                  thumbnail.flush();
                  msg.put("result", "success");
               } else {
                  FileUtils.deleteQuietly(f);
                  FileUtils.deleteQuietly(file);
                  msg.put("msg", "Only jpeg images of 1920*1080 resolution are allowed");
               }
            }
         } catch (IOException e) {
            LOG.error(e.getMessage(), e);
         }
      } else {
         msg.put("msg", "Only jpeg images are allowed");
      }

      response.setContentType("text/json");

      try (PrintWriter writer = response.getWriter()) {
         writer.print(msg.toString());
         writer.flush();
      } catch (IOException ex) {
         LOG.error(ex.getMessage(), ex);
      }
   }

   protected void modeCopyClone(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String status = "{\"status\":\"fail\"}";
      String idStr = request.getParameter("id");
      if (null != idStr) {
         int id = Integer.parseInt(idStr);
         Setting settingSrc = null;
         SettingManager setmgr = JpaManager.getSettingManager();
         settingSrc = setmgr.loadByKey(id);
         if (null != settingSrc) {
            Setting settingDest = new Setting();
            settingDest.setValue(settingSrc.getValue());
            settingDest.setPlatform(settingSrc.getPlatform());
            List<String> currentSettingNames = setmgr.loadAll().stream().map(Setting::getClonerename).map(String::trim).collect(Collectors.toList());
            String rename = CloneItemUtils.getUniqueCloneName(currentSettingNames, "Copy of " + settingSrc.getClonerename());
            settingDest.setClonerename(rename);
            settingDest.setName(rename);
            settingDest.setIsdelete(settingSrc.getIsdelete());
            settingDest.setType(settingSrc.getType());
            settingDest.setCreatedBy(settingSrc.getCreatedBy());
            settingDest.setCreatedDate(settingSrc.getCreatedDate());
            settingDest.setLastUpdatedBy(settingSrc.getLastUpdatedBy());
            settingDest.setLastUpdatedDate(settingSrc.getLastUpdatedDate());
            settingDest.setGeonameId(settingSrc.getGeonameId());
            settingDest.setLanguage(settingSrc.getLanguage());
            settingDest.setAndroidApps(settingSrc.getAndroidApps());
            settingDest.setContent(settingSrc.getContent());
            if (settingSrc.getChannelPackageId() > 0) {
               settingDest.setChannelPackageId(SettingCreator.copyCloneItem(CloneItemType.ChannelList.name(), settingSrc.getChannelPackageId()));
            }

            if (settingSrc.getAppPackageId() > 0) {
               settingDest.setAppPackageId(SettingCreator.copyCloneItem(CloneItemType.AndroidApps.name(), settingSrc.getAppPackageId()));
            }

            if (settingSrc.getSettingPackageId() > 0) {
               settingDest.setSettingPackageId(SettingCreator.copyCloneItem(CloneItemType.TVSettings.name(), settingSrc.getSettingPackageId()));
            }

            if (settingSrc.getUiCustomizationsId() > 0) {
               settingDest.setUiCustomizationsId(SettingCreator.copyCloneItem(CloneItemType.UiCustomizations.name(), settingSrc.getUiCustomizationsId()));
            }

            if (settingSrc.getBannersId() > 0) {
               settingDest.setBannersId(SettingCreator.copyCloneItem(CloneItemType.Banner.name(), settingSrc.getBannersId()));
            }

            if (settingSrc.getScheduleId() > 0) {
               settingDest.setScheduleId(SettingCreator.copyCloneItem(CloneItemType.Schedules.name(), settingSrc.getScheduleId()));
            }

            if (settingSrc.getWelcomeId() > 0) {
               settingDest.setWelcomeId(SettingCreator.copyCloneItem(CloneItemType.WelcomeLogo.name(), settingSrc.getWelcomeId()));
            }

            settingDest.setCloneItemStatus(settingSrc.getCloneItemStatus());
            setmgr.save(settingDest);
            status = "{\"status\":\"success\"}";
            File srcPath = new File(CommonConstants.CLONE_PROCESS_LOCATION + settingSrc.getName());
            File dirPath = new File(CommonConstants.CLONE_PROCESS_LOCATION + rename);
            FileUtils.copyDirectory(srcPath, dirPath);
         }

         Utils.writeToResponse(status, "text/html;charset=UTF-8", response);
      }
   }

   protected void modeDeleteClone(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String status = "{\"status\":\"fail\"}";
      int id = Integer.parseInt(request.getParameter("id"));
      SmartcmsSettingManager sismgr = JpaManager.getSmartcmsSettingManager();
      List<SmartcmsSetting> cmssettings = sismgr.findSmartcmsSettingBySettingId(id);

      for (int i = 0; i < cmssettings.size(); i++) {
         sismgr.deleteByKey(cmssettings.get(i).getId());
      }

      List<Devices> relatedDevices = JpaManager.getDevicesManager().findDevicesByCloneId(id, "clone");
      relatedDevices.forEach(device -> {
         device.setCloneid(0);
         device.setCloneColor("black");
         JpaManager.getDevicesManager().save(device);
      });
      SmartinfoSettingManager suimgr = JpaManager.getSmartinfoSettingManager();
      List<SmartinfoSetting> smartinfoSettings = suimgr.findSmartinfoSettingBySettingId(id);

      for (int i = 0; i < smartinfoSettings.size(); i++) {
         suimgr.deleteByKey(smartinfoSettings.get(i).getId());
      }

      SettingManager smgr = JpaManager.getSettingManager();

      try {
         Setting setting = smgr.loadByKey(id);
         if (null != setting) {
            this.deletePartialClone(setting);
            smgr.deleteByKey(id);
            File f = new File(CommonConstants.CLONE_PROCESS_LOCATION + setting.getName());
            if (f.exists()) {
               FileUtils.forceDelete(f);
            }

            SettingCreator.cleanCachedCloneData(setting.getId());
         }

         status = "{\"status\":\"success\"}";
         HttpSession session = request.getSession();
         String clonedataNamme = (String)session.getAttribute("cloneName");
         String settingNamme = (String)session.getAttribute("sname");
         if (null != setting && (setting.getName().equalsIgnoreCase(clonedataNamme) || setting.getName().equalsIgnoreCase(settingNamme))) {
            session.setAttribute("cloneName", null);
            session.setAttribute("sname", null);
         }
      } catch (NumberFormatException e) {
         LOG.error(e.getMessage(), e);
      }

      response.setContentType("text/json");
      IOUtils.write(status.getBytes(), response.getOutputStream());
   }

   protected void deletePartialClone(Setting setting) {
      SettingManager smgr = JpaManager.getSettingManager();
      int settingPackegeId = -1;
      int channelPackageId = -1;
      int appPackageId = -1;
      int welcomePackageId = -1;
      int uiCustomizationsPackageId = -1;
      int schedulePackageId = -1;

      try {
         if (null != setting) {
            settingPackegeId = setting.getSettingPackageId();
            if (settingPackegeId > 0) {
               List<Setting> settingPackage = smgr.findSettingListBySettingPackageId(settingPackegeId);
               if (null != settingPackage && settingPackage.size() == 1) {
                  SettingPackageManager settMag = JpaManager.getSettingPackageManager();
                  settMag.deleteByKey(settingPackegeId);
               }
            }

            appPackageId = setting.getAppPackageId();
            if (appPackageId > 0) {
               List<Setting> settingAppPackage = smgr.findSettingListByAppPackageId(appPackageId);
               if (null != settingAppPackage && settingAppPackage.size() == 1) {
                  String deleteDirectoryStr = CommonConstants.CLONE_PROCESS_LOCATION + "AppPackages" + File.separator + appPackageId;
                  File appPackage = new File(deleteDirectoryStr);
                  if (null != appPackage && appPackage.exists()) {
                     FileUtils.forceDelete(appPackage);
                  }

                  AppPackageManager appMag = JpaManager.getAppPackageManager();
                  appMag.deleteByKey(appPackageId);
               }
            }

            channelPackageId = setting.getChannelPackageId();
            if (channelPackageId > 0) {
               List<Setting> settingChannelPackage = smgr.findSettingListByChannelPackageId(channelPackageId);
               if (null != settingChannelPackage && settingChannelPackage.size() == 1) {
                  String deleteDirectoryStr = CommonConstants.CLONE_PROCESS_LOCATION + "ChannelPackages" + File.separator + channelPackageId;
                  File channelPackage = new File(deleteDirectoryStr);
                  if (null != channelPackage && channelPackage.exists()) {
                     FileUtils.forceDelete(channelPackage);
                  }

                  ChannelPackageManager channMag = JpaManager.getChannelPackageManager();
                  channMag.deleteByKey(channelPackageId);
               }
            }

            welcomePackageId = setting.getWelcomeId();
            if (welcomePackageId > 0) {
               List<Setting> settingWelcomePackage = smgr.findSettingListByWelcomeId(welcomePackageId);
               if (null != settingWelcomePackage && settingWelcomePackage.size() == 1) {
                  String deleteDirectoryStr = CommonConstants.CLONE_PROCESS_LOCATION + "Welcome" + File.separator + welcomePackageId;
                  File welcomePackageFile = new File(deleteDirectoryStr);
                  if (null != welcomePackageFile && welcomePackageFile.exists()) {
                     FileUtils.forceDelete(welcomePackageFile);
                  }

                  WelcomeManager welcomeMag = JpaManager.getWelcomeManager();
                  welcomeMag.deleteByKey(welcomePackageId);
               }
            }

            uiCustomizationsPackageId = setting.getUiCustomizationsId();
            if (uiCustomizationsPackageId > 0) {
               List<Setting> settingUiCustomizationsPackage = smgr.findSettingListByUiCustomizationsId(uiCustomizationsPackageId);
               if (null != settingUiCustomizationsPackage && settingUiCustomizationsPackage.size() == 1) {
                  String deleteDirectoryStr = CommonConstants.CLONE_PROCESS_LOCATION + "uiCustomizations" + File.separator + uiCustomizationsPackageId;
                  File uiCustomizationsFile = new File(deleteDirectoryStr);
                  if (null != uiCustomizationsFile && uiCustomizationsFile.exists()) {
                     FileUtils.forceDelete(uiCustomizationsFile);
                  }

                  UiCustomizationsManager uiCustomizationsMag = JpaManager.getUiCustomizationsManager();
                  uiCustomizationsMag.deleteByKey(uiCustomizationsPackageId);
               }
            }

            schedulePackageId = setting.getScheduleId();
            if (schedulePackageId > 0) {
               List<Setting> settingSchedulePackage = smgr.findSettingListByScheduleId(schedulePackageId);
               if (null != settingSchedulePackage && settingSchedulePackage.size() == 1) {
                  ScheduleManager scheduleMag = JpaManager.getScheduleManager();
                  scheduleMag.deleteByKey(schedulePackageId);
               }
            }
         }
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }
   }

   protected void modeDeleteHotel(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String id = request.getParameter("id");
      String status = "{\"status\":\"fail\"}";

      try {
         File workFolder = new File(CommonConstants.HOTEL_INFO_IMG_LOCATION);

         for (File f : workFolder.listFiles()) {
            if (f.getName().equalsIgnoreCase(id)) {
               FileUtils.deleteQuietly(f);
            }
         }

         File workFolderThumb = new File(CommonConstants.HOTEL_INFO_THUMB_IMG_LOCATION);
         if (workFolderThumb.length() > 0L) {
            for (File f : workFolderThumb.listFiles()) {
               if (f.getName().equalsIgnoreCase(id)) {
                  FileUtils.deleteQuietly(f);
                  status = "{\"status\":\"success\"}";
               }
            }
         }
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
      }

      response.setContentType("text/json");
      IOUtils.write(status.getBytes(), response.getOutputStream());
   }

   protected void modeListSettingProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
      SettingManager smgr = JpaManager.getSettingManager();
      List<Setting> settings = smgr.loadAll();
      StringBuilder sbuild = new StringBuilder();
      sbuild.append("{setting:[");

      for (Setting s : settings) {
         sbuild.append("\"").append(s.getName()).append("\"").append(",");
      }

      sbuild.append("\"").append("\"");
      sbuild.append("]}");
      response.setContentType("text/json");
      IOUtils.write(sbuild.toString().getBytes(), response.getOutputStream());
   }

   protected void modeFindSettingProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      SettingManager smgr = JpaManager.getSettingManager();
      String settingName = request.getParameter("sname");
      String status = "{\"status\":\"success\"}";
      List<Setting> settings = smgr.findSettingsByName(settingName);
      if (null == settings || settings.isEmpty()) {
         status = "{\"status\":\"fail\"}";
      }

      response.setContentType("text/json");
      IOUtils.write(status.getBytes(), response.getOutputStream());
   }

   private void setWelcomeMsg(SettingChannelBean scb, HttpServletRequest request) {
      List<String> lines = new ArrayList<>();
      boolean isEasySuite = false;
      if (null != scb) {
         if (scb.getSetttings() != null) {
            if (scb.getSetttings().getSetting() != null) {
               if (scb.getSetttings().getSetting().size() >= 1) {
                  for (com.tpvision.smartinstall.xml.Setting s : scb.getSetttings().getSetting()) {
                     if ((s.getRefFile() == null || s.getRefFile() != null && s.getRefFile().equalsIgnoreCase("ES2K12"))
                        && s.getXaddr() != null
                        && s.getXaddr().equalsIgnoreCase("ES2K12")) {
                        isEasySuite = true;
                        break;
                     }
                  }

                  Map<String, String> settingItemToLastValueMap = new HashMap<>();

                  for (com.tpvision.smartinstall.xml.Setting s : scb.getSetttings().getSetting()) {
                     settingItemToLastValueMap.put(s.getItem(), s.getLastValue());
                  }

                  if (!isEasySuite) {
                     char ascii = '\u0000';

                     for (int i = 1; i < 3; i++) {
                        StringBuilder sbuild = new StringBuilder();

                        for (int j = 0; j < 20; j++) {
                           String key = "HMWelcomeMessageLine" + i + "Char" + j;
                           String value = settingItemToLastValueMap.get(key);
                           if (null != value && (null == value || !value.equals("0")) && null != value && !value.trim().equals("")) {
                              ascii = (char)Integer.parseInt(value);
                              sbuild.append(Character.toString(ascii));
                           }
                        }

                        lines.add(sbuild.toString());
                     }

                     request.setAttribute("WELCOME_MSG_LINE_1", lines.get(0));
                     request.setAttribute("WELCOME_MSG_LINE_2", lines.get(1));
                  } else {
                     request.setAttribute("WELCOME_MSG_LINE_1", settingItemToLastValueMap.get("WelcomeMsgLine1"));
                     request.setAttribute("WELCOME_MSG_LINE_2", settingItemToLastValueMap.get("WelcomeMsgLine2"));
                  }
               }
            }
         }
      }
   }

   private void setClockChannel(SettingChannelBean scb, HttpServletRequest request) {
      String toClockChannel = request.getParameter("cs_dnprogram");
      if (null != toClockChannel && !toClockChannel.trim().equals("")) {
         for (com.tpvision.smartinstall.xml.Setting s : scb.getSetttings().getSetting()) {
            if (null != s && s.getItem() != null && s.getItem().equalsIgnoreCase("HMClockChannelOnePartNo")) {
               s.setLastValue(toClockChannel);
            }

            if (null != s && s.getItem() != null && s.getItem().equalsIgnoreCase("HMClockChannelAnalogNo")) {
               s.setLastValue(toClockChannel);
            }

            if (null != s && s.getItem() != null && s.getItem().equalsIgnoreCase("HMClockChannelDigit")) {
               s.setLastValue(toClockChannel);
            }

            if (null != s && s.getItem() != null && s.getItem().equalsIgnoreCase("HMClockChannelTwoPartMajorNo")) {
               s.setLastValue(toClockChannel);
            }
         }
      }
   }

   private void setSwitchOnSource(SettingChannelBean scb, HttpServletRequest request) {
      SettingState ss = new SettingState(scb);
      Map<String, String> settingToValueMap = ss.getSettingToValuesMap();
      String toSetHMOnChannelSrc = settingToValueMap.get(ParamConverter.instance().getSettingName("switchon_source"));
      String toSetChannelNumber = settingToValueMap.get(ParamConverter.instance().getSettingName("switchon_source_no"));
      boolean shouldSetChannelNumber = true;
      if (null == toSetChannelNumber || null == toSetHMOnChannelSrc || null != toSetHMOnChannelSrc && !toSetHMOnChannelSrc.equalsIgnoreCase("1")) {
         shouldSetChannelNumber = false;
      }

      for (com.tpvision.smartinstall.xml.Setting s : scb.getSetttings().getSetting()) {
         if (null != s && s.getItem() != null && s.getItem().equalsIgnoreCase("HMOnchannelSrc")) {
            s.setLastValue(toSetHMOnChannelSrc);
         }

         if (shouldSetChannelNumber) {
            if (null != s && s.getItem() != null && s.getItem().equalsIgnoreCase("HMOnChannelAnalogNo")) {
               s.setLastValue(toSetChannelNumber);
            }

            if (null != s && s.getItem() != null && s.getItem().equalsIgnoreCase("HMOnChannelDigit")) {
               s.setLastValue(toSetChannelNumber);
            }

            if (null != s && s.getItem() != null && s.getItem().equalsIgnoreCase("HMOnChannelTwoPartMajorNo")) {
               s.setLastValue(toSetChannelNumber);
            }

            if (null != s && s.getItem() != null && s.getItem().equalsIgnoreCase("HMOnChannelOnePartNo")) {
               s.setLastValue(toSetChannelNumber);
            }
         }
      }
   }

   private void setDiagnosticAnalytic(SettingChannelBean scb, HttpServletRequest request) {
   }

   private void modeCloneIsOld(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      int UPLOAD_CLONE_IS_OLD = 1;
      int EDIT_CLONE_IS_WRONG = 2;
      int error_type = 0;
      String msg = "{\"error_type\":\"0\"}";
      String flag_error = request.getParameter("flag_error");
      String sname = request.getParameter("sname");
      if (flag_error.equalsIgnoreCase("1")) {
         error_type = this.checkCloneIsOld(snameUpload, 1);
      } else if (flag_error.equalsIgnoreCase("2")) {
         error_type = this.checkCloneIsOld(sname, 2);
      }

      if (1 == error_type) {
         msg = "{\"error_type\":\"1\"}";
      } else if (2 == error_type) {
         msg = "{\"error_type\":\"2\"}";
      } else {
         msg = "{\"error_type\":\"0\"}";
      }

      request.setAttribute("flag_error", "0");
      response.setContentType("text/json");
      IOUtils.write(msg.getBytes(), response.getOutputStream());
   }

   private int checkCloneIsOld(String sname, int index) {
      if (index == 1) {
         SettingManager smgr = JpaManager.getSettingManager();
         List<Setting> settings = smgr.findSettingsByName(sname);
         if (!settings.isEmpty()) {
            smgr.deleteByKey(settings.get(0).getId());
            return 1;
         } else {
            return 0;
         }
      } else {
         return 2;
      }
   }

   private String getRoomSepcificSettings(RoomSpecificSettings rss) {
      if (null != rss && rss.getTV() != null) {
         JSONObject jsonObject = new JSONObject();
         jsonObject.put("serialNumber", rss.getTV().getSerialNumber());

         for (Item item : rss.getTV().getItem()) {
            String name = "";
            if ("Advanced.Identification Settings.RoomID".equalsIgnoreCase(item.getName())) {
               name = "RoomID";
            } else if ("Features.MultiRemoteControl".equalsIgnoreCase(item.getName())) {
               name = "MultiRemoteControl";
            }

            jsonObject.put(name, item.getValue());
         }

         return jsonObject.toString();
      } else {
         return null;
      }
   }

   private void setRoomSepcificSettings(RoomSpecificSettings rss, HttpServletRequest request) {
      String roomId = request.getParameter("RoomID");
      String multiRemoteControl = request.getParameter("multiRemoteControl");
      if (null != rss && rss.getTV() != null) {
         for (Item item : rss.getTV().getItem()) {
            String value = "";
            if ("Advanced.Identification Settings.RoomID".equalsIgnoreCase(item.getName())) {
               value = roomId;
            } else if ("Features.MultiRemoteControl".equalsIgnoreCase(item.getName()) && null != multiRemoteControl) {
               value = multiRemoteControl;
            }

            item.setValue(value);
         }
      }
   }

   private void updateSettingIdentifier(String cloneName, Date updatedDate, String platform) {
      String srcPath = CommonConstants.CLONE_PROCESS_LOCATION + cloneName + "/" + platform + "/";
      File rootFile = new File(srcPath);
      if (rootFile.exists()) {
         if (platform.indexOf("TPN16") > -1 || platform.indexOf("TPM153") > -1 || platform.indexOf("TPM181") > -1) {
            srcPath = srcPath + "MasterCloneData/TVSettings/TVSettings_Identifier.txt";
         } else if (platform.indexOf("TPN14") <= -1 && platform.indexOf("T911") <= -1 && platform.indexOf("TPM1012") <= -1) {
            if (platform.indexOf("Q55") != -1) {
               return;
            }
         } else {
            platform = platform.substring(0, platform.indexOf(95));
            srcPath = srcPath + platform + "_SSB_Identifier.txt";
         }

         File settingIdentifierFile = new File(srcPath);

         try {
            FileUtils.writeStringToFile(settingIdentifierFile, TpvDateUtils.getCurrentIndentifierFormatTime(), StandardCharsets.UTF_8);
         } catch (IOException e) {
            LOG.error(e.getMessage(), e);
         }
      }
   }
}
