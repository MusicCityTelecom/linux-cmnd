package com.tpvision.smartinstall.japit;

import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.util.CertUtils;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.DESHelper;
import com.tpvision.smartinstall.util.EncryptionDecryptionUtility;
import com.tpvision.smartinstall.util.JAPITUtils;
import com.tpvision.smartinstall.util.NetworkUtils;
import com.tpvision.smartinstall.util.TpvRunableTask;
import com.tpvision.smartinstall.util.Utils;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecuredCmdControlManager {
   private static final Logger logger = LoggerFactory.getLogger(SecuredCmdControlManager.class);
   public static final String SECURIT_KEY = getSecrectKeyFromDB();
   private static boolean isSupportHttps = StringUtils.equalsIgnoreCase("yes", Utils.getProductPropties().getProperty("japit.https.enabled"));

   private SecuredCmdControlManager() {
   }

   private static String getSecrectKeyFromDB() {
      return JpaManager.getSIConfigManager().getSecrectKey();
   }

   public static String parseReceiveSecuredCmdControl(boolean bWebServices, JSONObject commandDetails, Devices tv) {
      String transitCommunicationStatus = commandDetails.optString("TransitCommunicationStatus");
      if ("Transiting".equalsIgnoreCase(transitCommunicationStatus)) {
         if (StringUtils.equalsIgnoreCase("Transiting", tv.getSecureCmdSupport())) {
            logger.warn("tv <{}> already in the transiting status, skip handle", tv.getTvipaddress());
            return "";
         } else {
            String transitCommunicationTo = commandDetails.optString("TransitCommunicationTo");
            if ("NonSecured".equalsIgnoreCase(transitCommunicationTo) && !StringUtils.equalsIgnoreCase("true", tv.getSecureCmdSupport())) {
               logger.warn("tv <{}> already in the unsecure status,still aim to nosecure status, skip handle transiting status", tv.getTvipaddress());
               return "";
            } else {
               logger.info("update tv <{}> to transitiong status and start the status check thread", tv.getTvipaddress());
               tv.setSecureCmdSupport("Transiting");
               startCheckSecureCmdSupportThread(tv.getTvuniqueid());
               JpaManager.getDevicesManager().save(tv);
               return "";
            }
         }
      } else {
         String secureCmdSupport = commandDetails.optString("SecureCmdSupport");
         String certAvailableInTV = commandDetails.optString("CertAvailableInTV");
         String secureKeySupport = commandDetails.optString("SecureKeySupport");
         String currentTVCertVersion = commandDetails.optString("CurrentCACertVersion");
         String currentCmndCertVersion = CertUtils.getCaCertVersion();
         logger.info("current cmnd CA cert version ==> {}", currentCmndCertVersion);
         boolean isNeedUpdateDevice = false;
         if ("true".equalsIgnoreCase(secureCmdSupport)
            && "Present".equalsIgnoreCase(certAvailableInTV)
            && "Secured".equalsIgnoreCase(transitCommunicationStatus)
            && currentCmndCertVersion.equalsIgnoreCase(currentTVCertVersion)) {
            logger.info("update tv <{}> secure cmd status to https mode", tv.getTvipaddress());
            isNeedUpdateDevice = true;
            tv.setSecureCmdSupport("true");
         } else if ("false".equalsIgnoreCase(secureCmdSupport) || "NonSecured".equalsIgnoreCase(transitCommunicationStatus)) {
            logger.info("update tv <{}> secure cmd status to http mode", tv.getTvipaddress());
            isNeedUpdateDevice = true;
            tv.setSecureCmdSupport("false");
         }

         if (isSupportHttps) {
            logger.info("CMND current support https feature");
         } else {
            logger.warn("CMND current not support https feature");
         }

         String caResponse = "";
         if (isSupportHttps
            && "true".equalsIgnoreCase(secureCmdSupport)
            && currentCmndCertVersion.length() > 0
            && isRequireCaCertificate(certAvailableInTV, tv, secureKeySupport, currentCmndCertVersion, currentTVCertVersion)) {
            logger.info("parepare to create new CA certificate info to tv <{}> ", tv.getTvipaddress());
            caResponse = getRootCAResponse(bWebServices, currentCmndCertVersion, tv, secureKeySupport);
            if (!StringUtils.isEmpty(caResponse)) {
               logger.info("update tv <{}>'s secureKeySupport to <{}>", tv.getTvipaddress(), secureKeySupport);
               tv.setSecureKeySupport(secureKeySupport);
               isNeedUpdateDevice = true;
            }
         }

         if (isNeedUpdateDevice) {
            JpaManager.getDevicesManager().save(tv);
         }

         return caResponse;
      }
   }

   private static boolean isRequireCaCertificate(
      String certAvailableInTV, Devices tv, String targetSecureKeySupport, String currentCmndCertVersion, String currentTVCertVersion
   ) {
      if ("NotPresent".equalsIgnoreCase(certAvailableInTV)) {
         logger.info("there's no ca cert in TV <{}> ,send CA", tv.getTvipaddress());
         return true;
      } else if (!StringUtils.equalsIgnoreCase(currentCmndCertVersion, currentTVCertVersion)) {
         logger.info(
            "tv<{}>'s current ca version <{}> not equal cmnd's latest ca version<{}>, resend CA",
            tv.getTvipaddress(),
            currentTVCertVersion,
            currentCmndCertVersion
         );
         return true;
      } else if (!StringUtils.equalsIgnoreCase(tv.getSecureKeySupport(), targetSecureKeySupport)) {
         logger.info(
            "tv <{}>'s db current secure key support value <{}> not equal japit target support <{}>, resend CA",
            tv.getTvipaddress(),
            tv.getSecureKeySupport(),
            targetSecureKeySupport
         );
         return true;
      } else {
         return false;
      }
   }

   public static void requestSecuredCmd(Devices tv) {
      SecuredCmdControlCmd securedCmd = new SecuredCmdControlCmd(JapitCommand.CommandType.Request, JapitCommand.CommandSvc.WebListeningServices, null);
      JAPITUtils.sendAsyncCommand(tv, securedCmd.generateCommand(), (bResult, resultJson) -> {
         if (bResult) {
            parseReceiveSecuredCmdControl(false, resultJson, tv);
         }
      });
   }

   public static void sendSecuredCmdToHttp(Devices tv) {
      JSONObject detail = SecuredCmdControlCmd.getCommandDetail("NonSecured", "", "", "", "", "");
      SecuredCmdControlCmd securedCmd = new SecuredCmdControlCmd(JapitCommand.CommandType.Change, JapitCommand.CommandSvc.WebListeningServices, detail);
      JAPITUtils.sendAsyncCommand(tv, securedCmd.generateCommand());
   }

   public static void sendSecuredCmdToHttps(String tvip) {
      List<Devices> tvList = JpaManager.getDevicesManager().findDevicesByTvipaddress(tvip);
      String currentCmndCertVersion = CertUtils.getCaCertVersion();
      if (currentCmndCertVersion.length() > 0 && !tvList.isEmpty()) {
         logger.info("return CA URL.");
         getRootCAResponse(false, currentCmndCertVersion, tvList.get(0), "");
      }
   }

   private static String getRootCAResponse(boolean bWebServices, String currentCmndCertVersion, Devices tv, String secureKeySupport) {
      String serverip = NetworkUtils.getServerIpFromSameRoute(tv.getTvipaddress());
      String httpsBaseUrl = "https://" + serverip + ":" + CommonConstants.CMND_HTTPS_PORT + "/SmartInstall";
      String serverUrl = httpsBaseUrl + "/webservices.jsp";
      String httpBaseUrl = "http://" + serverip + ":" + CommonConstants.CMND_HTTP_PORT + "/SmartInstall";
      String caUrl = httpBaseUrl + "/Cert/ca.p12";
      String caNamePassword = getCANamePassword(tv.getId(), secureKeySupport);
      if (StringUtils.isEmpty(caNamePassword)) {
         logger.error("Cant generate Ca name password, return empty to TV.");
         return "";
      } else {
         JSONObject detail = SecuredCmdControlCmd.getCommandDetail("Secured", serverUrl, caUrl, currentCmndCertVersion, caNamePassword, secureKeySupport);
         if (bWebServices) {
            SecuredCmdControlCmd securedCmd = new SecuredCmdControlCmd(JapitCommand.CommandType.Change, JapitCommand.CommandSvc.WebService, detail);
            return securedCmd.generateCommand();
         } else {
            SecuredCmdControlCmd securedCmd = new SecuredCmdControlCmd(JapitCommand.CommandType.Change, JapitCommand.CommandSvc.WebListeningServices, detail);
            JAPITUtils.sendAsyncCommand(tv, securedCmd.generateCommand());
            return securedCmd.generateCommand();
         }
      }
   }

   private static String getCANamePassword(String tvId, String secureKeySupport) {
      if ("true".equalsIgnoreCase(secureKeySupport)) {
         String srcData = CertUtils.CA_NAME + "&" + CertUtils.CA_PASSWORD + "&" + SECURIT_KEY;
         return EncryptionDecryptionUtility.encrypt(srcData, tvId);
      } else {
         String srcData = CertUtils.CA_NAME + "&" + CertUtils.CA_PASSWORD;
         return DESHelper.encode(srcData, tvId);
      }
   }

   public static String generateRequestTokenFromPostData(String data) {
      return EncryptionDecryptionUtility.encrypt(EncryptionDecryptionUtility.getSHA256Hash(data.trim()), SECURIT_KEY);
   }

   private static void startCheckSecureCmdSupportThread(String tvUniqueId) {
      new Thread(
            (new TpvRunableTask() {
                  private String tvUniqueId;

                  public Runnable init(String tvUniqueId) {
                     this.tvUniqueId = tvUniqueId;
                     return this;
                  }

                  @Override
                  public void execute() {
                     try {
                        Thread.sleep(120000L);
                        Devices devices = JpaManager.getDevicesManager().loadByKey(this.tvUniqueId);
                        if (devices == null) {
                           SecuredCmdControlManager.logger.warn("tv <{}> not existed any more", this.tvUniqueId);
                           return;
                        }

                        if ("Transiting".equalsIgnoreCase(devices.getSecureCmdSupport())) {
                           SecuredCmdControlManager.logger
                              .warn("tv <{}>'s secure support status is still in transition,start to reset to false", devices.getTvipaddress());
                           devices.setSecureCmdSupport("false");
                           JpaManager.getDevicesManager().save(devices);
                        } else {
                           SecuredCmdControlManager.logger
                              .info(
                                 "tv<{}>'s secure support status is <{}> ,not transition status, ignore",
                                 devices.getTvipaddress(),
                                 devices.getSecureCmdSupport()
                              );
                        }
                     } catch (Exception e) {
                        SecuredCmdControlManager.logger.error(e.getMessage(), e);
                     }
                  }
               })
               .init(tvUniqueId)
         )
         .start();
   }
}
