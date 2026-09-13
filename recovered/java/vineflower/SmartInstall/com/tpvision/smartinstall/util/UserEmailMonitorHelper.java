package com.tpvision.smartinstall.util;

import com.tpvision.smartinstall.VersionChecker;
import com.tpvision.smartinstall.api.ApiLicenseChecker;
import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.Profile;
import com.tpvision.smartinstall.dao.core.ReceptionClient;
import com.tpvision.smartinstall.dao.core.Role;
import com.tpvision.smartinstall.dao.core.VersionNotice;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.ProfileRoleManager;
import com.tpvision.smartinstall.dao.mgr.VersionNoticeManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserEmailMonitorHelper {
   private static final Logger LOG = LoggerFactory.getLogger(UserEmailMonitorHelper.class);
   private static final Map<String, Date> tvIsOfflineNoticeEmailSendMap = new HashMap<>();
   private static final Map<String, Date> remoteControlLowBatteryNoticeEmailSendMap = new HashMap<>();
   private static ThreadPoolExecutor executorService = null;

   private UserEmailMonitorHelper() {
   }

   public static void sendPmsServerIsOfflineNotice(String lastStatus) {
      LOG.info("connect failed:{}, send mail", lastStatus);
      List<Profile> profileList = getNoticeProfileList(UserEmailMonitorHelper.MonitorEvents.PMS_SERVER_IS_OFFLINE);
      List<String> sentEmailList = new ArrayList<>();
      String locationText = getHotelLocationDetailInfo();

      for (Profile profile : profileList) {
         String email = profile.getEmail().trim();
         if (sentEmailList.contains(email)) {
            LOG.warn("<{}> already send the pms failure email", email);
         } else {
            sentEmailList.add(email);
            String subject = "PMS link failed on CMND";
            String text = "<p>We have detected a communication failure between the PMS and CMND server. Please check the network infrastructure, PMS and CMND server.</p>\r\n<p>If you did not own a CMND account, please ignore this message.</p>\r\n"
               + locationText;
            boolean sendResult = EmailUtils.sendEmailByAmazonService(subject, wrapEmailBody(profile.getId(), text), email);
            LOG.info("send pms connection failed to email <{}>, reason:<{}>,result: {}", email, lastStatus, sendResult);
         }
      }
   }

   public static void sendTVIsOfflineNotice(final String roomId) {
      List<Profile> profileList = getNoticeProfileList(UserEmailMonitorHelper.MonitorEvents.TV_IS_OFFLINE);
      final String hotelName = getHotelNameFromLocation();

      for (final Profile profile : profileList) {
         final String email = profile.getEmail().trim();
         String key = roomId + email.trim();
         if (tvIsOfflineNoticeEmailSendMap.containsKey(key)) {
            Date lastSendDate = tvIsOfflineNoticeEmailSendMap.get(key);
            if (System.currentTimeMillis() - lastSendDate.getTime() < 86400000L) {
               LOG.info("skip send to email<{}> notice for room <{}>", email, roomId);
               return;
            }
         }

         tvIsOfflineNoticeEmailSendMap.put(key, new Date());
         LOG.info("submit send offline notice task to email <{}> for room <{}>", email, roomId);
         getThreadPoolExecutor()
            .submit(
               new TpvRunableTask() {
                  @Override
                  public void execute() {
                     String subject = "CMND TV offline notice";
                     String text = "<p>The TV in room <b>"
                        + roomId
                        + "</b> at <b>"
                        + hotelName
                        + "</b> is detected to be offline. Please have someone take a look at it to get it back on line.</p>\r\n";
                     boolean sendResult = EmailUtils.sendEmailByAmazonService(subject, UserEmailMonitorHelper.wrapEmailBody(profile.getId(), text), email);
                     UserEmailMonitorHelper.LOG.info("send offline notice to email <{}>, result:<{}> ", email, sendResult);
                  }
               }
            );
      }
   }

   public static void sendRemoteControlLowBatteryNotice(final Devices device) {
      String tvUniqueId = device.getTvuniqueid();
      List<Profile> profileList = getNoticeProfileList(UserEmailMonitorHelper.MonitorEvents.LOW_BATTERY_REMOTE_CONTROL);
      final String hotelName = getHotelNameFromLocation();
      final String locationDetail = getHotelLocationDetailInfo();

      for (final Profile profile : profileList) {
         final String email = profile.getEmail().trim();
         String key = tvUniqueId + email.trim();
         if (remoteControlLowBatteryNoticeEmailSendMap.containsKey(key)) {
            Date lastSendDate = remoteControlLowBatteryNoticeEmailSendMap.get(key);
            if (System.currentTimeMillis() - lastSendDate.getTime() < 86400000L) {
               LOG.info("skip send low battery notice to email<{}> notice for tv <{}>", email, tvUniqueId);
               return;
            }
         }

         remoteControlLowBatteryNoticeEmailSendMap.put(key, new Date());
         LOG.info("submit low battery remote control notice task to email <{}> for tv <{}>", email, tvUniqueId);
         getThreadPoolExecutor()
            .submit(
               new TpvRunableTask() {
                  @Override
                  public void execute() {
                     String subject = "Remote control low battery notice";
                     String text = "<p>The battery of the TV remote control in room <b>"
                        + device.getTvroomid()
                        + "<b> at <b>"
                        + hotelName
                        + "</b> is low and will not be able to control the TV in the near future. Please replace the battery in order to avoid guest complaints.</p>\r\n"
                        + locationDetail;
                     boolean sendResult = EmailUtils.sendEmailByAmazonService(subject, UserEmailMonitorHelper.wrapEmailBody(profile.getId(), text), email);
                     UserEmailMonitorHelper.LOG.info("send low battery remote control notice to email <{}>, result:<{}> ", email, sendResult);
                  }
               }
            );
      }
   }

   public static void sendInvalidCredentialLoginAttemptNotice(final String userName) {
      int adminRoleId = -1;
      Role adminRole = JpaManager.getRoleManager().getRoleByName("ROLE_ADMIN");
      if (adminRole != null) {
         adminRoleId = adminRole.getIdrole();
      }

      List<Profile> profileList = getNoticeProfileList(UserEmailMonitorHelper.MonitorEvents.INVALID_CREDENTIAL_LOGIN_ATTEMPT);
      ProfileRoleManager profileRoleManager = JpaManager.getProfileRoleManager();
      final String hotelName = getHotelNameFromLocation();
      final boolean isUserNameExist = JpaManager.getProfileManager().loadByKey(userName) != null;
      Set<String> sendEmailSet = new HashSet<>();

      for (final Profile profile : profileList) {
         int roleId = profileRoleManager.loadByProfileId(profile.getId()).get(0).getRoleIdrole();
         if (roleId != adminRoleId) {
            LOG.warn("<{}> is not a admin role account, only admin role account will send login attemp notice", profile.getId());
         } else {
            final String email = profile.getEmail().trim();
            if (sendEmailSet.contains(email)) {
               LOG.warn("already send invalid login email to <{}>", email);
            } else {
               sendEmailSet.add(email);
               LOG.info("submit invalid credential login attempt task to email <{}> for account <{}>", email, userName);
               getThreadPoolExecutor()
                  .submit(
                     new TpvRunableTask() {
                        @Override
                        public void execute() {
                           String subject = "CMND Invalid credential login attempt notice";
                           String text = null;
                           if (isUserNameExist) {
                              text = "<p>A login attempt was made using an invalid password for username: <b>"
                                 + userName
                                 + "</b> on the server located at <b>"
                                 + hotelName
                                 + "</b>.</p>\r\n";
                           } else {
                              text = "<p>A login attempt was made using a none-existing username on the server located at <b>"
                                 + hotelName
                                 + "</b>. Entered username was: <b>"
                                 + userName
                                 + "</b>.</p>\r\n";
                           }

                           boolean sendResult = EmailUtils.sendEmailByAmazonService(subject, UserEmailMonitorHelper.wrapEmailBody(profile.getId(), text), email);
                           UserEmailMonitorHelper.LOG.info("send offline notice to email <{}>, result:<{}> ", email, sendResult);
                        }
                     }
                  );
            }
         }
      }
   }

   public static void sendCmndOrReceptionVersionOutOfDateNotice() {
      LOG.info("check CMND and CMND Reception version and send notice to the required emails");
      List<Profile> profileList = getNoticeProfileList(UserEmailMonitorHelper.MonitorEvents.CMND_AND_RECEPTION_VERSION_OUT_OF_DATE);
      if (profileList.isEmpty()) {
         LOG.warn("No users need to be notices, stop check");
      } else {
         String curerntCmndVersion = Utils.getCMNDMajorVersion();
         VersionChecker.VersionDetail newCmndVersion = VersionChecker.getCmndNewVersion(curerntCmndVersion);
         List<ReceptionClient> oldVeresionReceptionList = JpaManager.getReceptionClientManager()
            .findReceptionClientListByStatus(1)
            .stream()
            .filter(e -> VersionChecker.getReceptionNewVersion(e.getCurrentVersion()) != null)
            .collect(Collectors.toList());
         if (newCmndVersion == null && oldVeresionReceptionList.isEmpty()) {
            LOG.warn("No latest version for CMND and CMND Reception, return");
         } else {
            String locationText = getHotelLocationDetailInfo();
            VersionNoticeManager versionNoticeManager = JpaManager.getVersionNoticeManager();
            if (newCmndVersion != null) {
               String cmndSubject = "CMND is out-of-date";
               String cmndNoticeContent = "<p>The CMND server is running on out-of-date software. Please upgrade to apply the latest patches and features.</p>\r\n<p>Current software version: "
                  + curerntCmndVersion
                  + "</p>\r\n<p>Latest software version: <a target='_blank' href='"
                  + newCmndVersion.getUrl()
                  + "'>"
                  + newCmndVersion.getVersion()
                  + "</a></p>\r\n"
                  + locationText;

               for (Profile profile : profileList) {
                  String account = profile.getId();
                  String newVersion = newCmndVersion.getVersion();
                  if (!versionNoticeManager.findEmailCmndVersionNoticeList(account, newVersion).isEmpty()) {
                     LOG.warn("already send CMND new version notice for version: <{}> to account:{}  ", newVersion, account);
                  } else {
                     boolean sendResult = EmailUtils.sendEmailByAmazonService(cmndSubject, wrapEmailBody(account, cmndNoticeContent), profile.getEmail());
                     LOG.info("send cmnd version out of date notice to email <{}>, result: {}", profile.getEmail(), sendResult);
                     if (sendResult) {
                        VersionNotice versionNotice = new VersionNotice();
                        versionNotice.setNoticeType(1);
                        versionNotice.setNoticeAdmin(account);
                        versionNotice.setEntityType(0);
                        versionNotice.setEntityId("CMND");
                        versionNotice.setNoticeVersion(newVersion);
                        versionNotice.setNoticeTime(new Date());
                        versionNoticeManager.save(versionNotice);
                     }
                  }
               }
            }

            if (!oldVeresionReceptionList.isEmpty()) {
               VersionChecker.VersionDetail latestReceptionVersion = VersionChecker.getLatestReceptionVersionDirectly();

               for (Profile profile : profileList) {
                  String account = profile.getId();
                  String newVersion = latestReceptionVersion.getVersion();
                  List<ReceptionClient> noticeRequiredReceptions = oldVeresionReceptionList.stream()
                     .filter(e -> versionNoticeManager.findEmailReceptionVersionNoticeList(account, newVersion, e.getClientId()).isEmpty())
                     .collect(Collectors.toList());
                  if (noticeRequiredReceptions.isEmpty()) {
                     LOG.warn("already notice all reception out of date for version: <{}> to account:{}  ", newVersion, account);
                  } else {
                     String subject = "CMND Reception is out-of-date";
                     String tableHtml = "<table border=\"1\" cellspacing=\"0\" cellpadding=\"10\"><tr><th>PC UID</th><th>Current software</th><th>Latest software</th></tr>";

                     for (ReceptionClient e : oldVeresionReceptionList) {
                        tableHtml = tableHtml
                           + "<tr><td>"
                           + e.getClientId()
                           + "</td><td>"
                           + e.getCurrentVersion()
                           + "</td><td><a target='_blank' href='"
                           + latestReceptionVersion.getUrl()
                           + "'>"
                           + newVersion
                           + "</a></td></tr>";
                     }

                     tableHtml = tableHtml + "</table>";
                     String text = "<p>Following PCs are running on an out-of-date CMND Reception. Please upgrade the software to apply the latest patches and features.</p>\r\n"
                        + tableHtml
                        + "<br/>\r\n"
                        + locationText;
                     boolean sendResult = EmailUtils.sendEmailByAmazonService(subject, wrapEmailBody(account, text), profile.getEmail());
                     LOG.info("send reception out of date notice to email <{}>, result: {}", profile.getEmail(), sendResult);
                     if (sendResult) {
                        oldVeresionReceptionList.forEach(e -> {
                           VersionNotice versionNotice = new VersionNotice();
                           versionNotice.setNoticeType(1);
                           versionNotice.setNoticeAdmin(account);
                           versionNotice.setEntityType(1);
                           versionNotice.setEntityId(e.getClientId());
                           versionNotice.setNoticeVersion(newVersion);
                           versionNotice.setNoticeTime(new Date());
                           versionNoticeManager.save(versionNotice);
                        });
                     }
                  }
               }
            }
         }
      }
   }

   public static void sendTvFirmwareOutOfDateNotice() {
      LOG.info("check firmware version and send notice to the required emails");
      List<Profile> profileList = getNoticeProfileList(UserEmailMonitorHelper.MonitorEvents.TV_FIRMWARE_IS_OUT_OF_DATE);
      if (profileList.isEmpty()) {
         LOG.warn("No users need to be notices, stop check");
      } else {
         Map<Devices, VersionChecker.VersionDetail> requireUpgradeDevices = new HashMap<>();

         for (Devices devices : JpaManager.getDevicesManager().loadAll()) {
            if (!StringUtils.isEmpty(devices.getTvFirmwareIdentifier())) {
               VersionChecker.VersionDetail newVersionDetail = VersionChecker.getFirmwareNewVersion(
                  devices.getType(), devices.getTvmodelnumber(), devices.getTvFirmwareIdentifier()
               );
               if (newVersionDetail != null) {
                  requireUpgradeDevices.put(devices, newVersionDetail);
               }
            }
         }

         if (requireUpgradeDevices.isEmpty()) {
            LOG.warn("No latest firmware versions for devices, return");
         } else {
            VersionNoticeManager versionNoticeManager = JpaManager.getVersionNoticeManager();
            String locationText = getHotelLocationDetailInfo();

            for (Profile profile : profileList) {
               String account = profile.getId();
               Map<Devices, VersionChecker.VersionDetail> newRequiredDevices = new HashMap<>();

               for (Entry<Devices, VersionChecker.VersionDetail> entry : requireUpgradeDevices.entrySet()) {
                  Devices device = entry.getKey();
                  VersionChecker.VersionDetail detail = entry.getValue();
                  if (versionNoticeManager.findEmailFirmwareVersionNoticeList(account, detail.getVersion(), device.getTvuniqueid()).isEmpty()) {
                     newRequiredDevices.put(device, detail);
                  }
               }

               if (newRequiredDevices.isEmpty()) {
                  LOG.warn("already notice all firmware out of date to this account:{}  ", account);
               } else {
                  String subject = requireUpgradeDevices.size() == 1 ? "TV firmware is out-of-date" : "TV firmwares is out-of-date";
                  String tableHtml = "<table border=\"1\" cellspacing=\"0\" cellpadding=\"10\"><tr><th>Room</th><th>Model</th><th>IP</th><th>Current firmware</th><th>Latest firmware</th></tr>";

                  for (Entry<Devices, VersionChecker.VersionDetail> e : requireUpgradeDevices.entrySet()) {
                     Devices device = e.getKey();
                     VersionChecker.VersionDetail detail = e.getValue();
                     tableHtml = tableHtml
                        + "<tr><td>"
                        + device.getTvroomid()
                        + "</td><td>"
                        + device.getTvmodelnumber()
                        + "</td><td>"
                        + device.getTvipaddress()
                        + "</td><td>"
                        + device.getTvFirmwareIdentifier()
                        + "</td><td><a target='_blank' href='"
                        + detail.getUrl()
                        + "'>"
                        + detail.getVersion()
                        + "</a></td></tr>";
                  }

                  tableHtml = tableHtml + "</table>";
                  String text = "<p>Following TVs on the CMND server are running on out-of-date firmware. Please upgrade the firmware to apply the latest firmware patches and features.</p>\r\n"
                     + tableHtml
                     + "<br/>\r\n"
                     + locationText;
                  boolean sendResult = EmailUtils.sendEmailByAmazonService(subject, wrapEmailBody(profile.getId(), text), profile.getEmail());
                  LOG.info("send firmware out of date notice to email <{}>, result: {}", profile.getEmail(), sendResult);
                  if (sendResult) {
                     requireUpgradeDevices.entrySet().forEach(e -> {
                        VersionNotice versionNotice = new VersionNotice();
                        versionNotice.setNoticeType(1);
                        versionNotice.setNoticeAdmin(account);
                        versionNotice.setEntityType(2);
                        versionNotice.setEntityId(e.getKey().getTvuniqueid());
                        versionNotice.setNoticeVersion(e.getValue().getVersion());
                        versionNotice.setNoticeTime(new Date());
                        versionNoticeManager.save(versionNotice);
                     });
                  }
               }
            }
         }
      }
   }

   private static String wrapEmailBody(String account, String htmlContent) {
      return "<!DOCTYPE html>\r\n<html>\r\n  <body>\r\n    Dear <b>"
         + account
         + "</b>\r\n"
         + htmlContent
         + "   <p>\r\n     Kind regards,\r\n     <br/>\r\n  CMND Team\r\n     <br/><b>Ref:"
         + ApiLicenseChecker.getInstance().getSerialNumber()
         + "</b>\r\n     <br/>\r\n   </p>\r\n  </body>\r\n</html>";
   }

   private static List<Profile> getNoticeProfileList(UserEmailMonitorHelper.MonitorEvents monitorEvent) {
      List<Profile> noticeList = new ArrayList<>();

      for (Profile profile : JpaManager.getProfileManager().loadAll()) {
         if (isSupportNotice(profile) && resolveProfileMonitorConfig(profile).contains(monitorEvent.toString())) {
            noticeList.add(profile);
         }
      }

      return noticeList;
   }

   private static String getHotelLocationDetailInfo() {
      String locationText = "";

      try {
         Location location = LocationManager.getLocationFromFile();
         if (location == null || location.isEmpty()) {
            return "";
         }

         locationText = String.format(
            Locale.ENGLISH,
            "Location details:<br/>\r\n<b>%s<br/>\r\n%s<br/>\r\n%s<br/>\r\n%s<br/>\r\n%s<br/>\r\n</b>",
            location.getHotelName(),
            location.getAddressLin1(),
            location.getAddressLin2(),
            location.getCity(),
            location.getCountry()
         );
      } catch (IOException e) {
         LOG.error("location file not found");
      }

      return locationText;
   }

   private static String getHotelNameFromLocation() {
      String hotelName = "";

      try {
         Location location = LocationManager.getLocationFromFile();
         if (location != null) {
            hotelName = location.getHotelName();
         }
      } catch (Exception e) {
         LOG.error("location extraction error", e);
      }

      return hotelName;
   }

   public static boolean isSupportNotice(Profile profile) {
      return profile != null && StringUtils.equalsIgnoreCase(profile.getExtn(), Boolean.TRUE.toString()) && StringUtils.isNoneBlank(profile.getEmail());
   }

   public static String resolveProfileMonitorConfig(Profile profile) {
      return profile != null && profile.getMonitorConfig() != null
         ? profile.getMonitorConfig()
         : String.join(
            UserEmailMonitorHelper.MonitorEvents.PMS_SERVER_IS_OFFLINE.name(),
            UserEmailMonitorHelper.MonitorEvents.CMND_AND_RECEPTION_VERSION_OUT_OF_DATE.name(),
            UserEmailMonitorHelper.MonitorEvents.TV_FIRMWARE_IS_OUT_OF_DATE.name()
         );
   }

   private static synchronized ThreadPoolExecutor getThreadPoolExecutor() {
      if (executorService == null) {
         executorService = new ThreadPoolExecutor(2, 10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), new TpvNamedThreadFactory("user-email-notice"));
      }

      return executorService;
   }

   public enum MonitorEvents {
      PMS_SERVER_IS_OFFLINE,
      TV_IS_OFFLINE,
      LOW_BATTERY_REMOTE_CONTROL,
      INVALID_CREDENTIAL_LOGIN_ATTEMPT,
      CMND_AND_RECEPTION_VERSION_OUT_OF_DATE,
      TV_FIRMWARE_IS_OUT_OF_DATE;
   }
}
