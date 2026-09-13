package com.tpvision.smartinstall.servlet;

import com.tpvision.smartinstall.dao.core.PasswordReset;
import com.tpvision.smartinstall.dao.core.Profile;
import com.tpvision.smartinstall.dao.core.SIConfig;
import com.tpvision.smartinstall.dao.mgr.PasswordResetManager;
import com.tpvision.smartinstall.dao.mgr.ProfileManager;
import com.tpvision.smartinstall.dao.mgr.SIConfigManager;
import com.tpvision.smartinstall.util.DESHelper;
import com.tpvision.smartinstall.util.EmailUtils;
import com.tpvision.smartinstall.util.HttpUtils;
import com.tpvision.smartinstall.util.NetworkUtils;
import com.tpvision.smartinstall.util.TpvStringUtils;
import com.tpvision.smartinstall.util.UserEmailMonitorHelper;
import com.tpvision.smartinstall.util.UserManagerUtils;
import com.tpvision.smartinstall.util.Utils;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/password")
public class PasswordServlet {
   private static final Logger LOG = LoggerFactory.getLogger(PasswordServlet.class);
   private static final String SIGN_RESET_TOKEN_KEY = "^^#$#*$#))FJDFLDS***##)))FSF__";
   private static final String WEAK_RESET_KEY = "#)(UFKDFLSDFSDFSDFSDFDFF****#JFDNF";
   private static final String ERRORMSG_ATTR_NAME = "errorMsg";
   @Autowired
   private ProfileManager profileManager;
   @Autowired
   private PasswordResetManager passwordResetManager;
   @Autowired
   private SIConfigManager sIConfigManager;

   @GetMapping("/changeWeakPage")
   public String changeWeakPage(HttpServletRequest request, String info, Model model) {
      String referer = request.getHeader("referer");
      if (!StringUtils.isEmpty(referer) && referer.indexOf("cas/login") != -1) {
         JSONObject infoJson = null;

         try {
            String paramJson = new String(Base64.getDecoder().decode(info.getBytes()), StandardCharsets.UTF_8);
            infoJson = new JSONObject(paramJson);
         } catch (Exception e) {
            model.addAttribute("errorMsg", "param info error");
            return "password/reset_error";
         }

         String username = infoJson.optString("username", "");
         String checkKey = infoJson.optString("checkKey", "");
         String sourceIp = infoJson.optString("sourceIp", "");
         long time = infoJson.optLong("time", 0L);
         if (!StringUtils.equalsIgnoreCase(checkKey, DigestUtils.md5Hex(username + sourceIp + time + "#)(UFKDFLSDFSDFSDFSDFDFF****#JFDNF"))) {
            model.addAttribute("errorMsg", "invliad info string");
            return "password/reset_error";
         } else if (!StringUtils.equalsIgnoreCase(sourceIp, request.getRemoteAddr())) {
            model.addAttribute("errorMsg", "invliad source ip address");
            return "password/reset_error";
         } else if (System.currentTimeMillis() - time >= 300000L) {
            model.addAttribute("errorMsg", "reset link expired");
            return "password/reset_error";
         } else {
            Profile profile = this.profileManager.loadByKey(username);
            if (profile == null) {
               model.addAttribute("errorMsg", "invliad weak password user");
               return "password/reset_error";
            } else {
               PasswordReset passwordReset = this.createPasswordSet(username, info, (short)2, request);
               model.addAttribute("username", username);
               model.addAttribute("resetToken", this.getPasswordResetTokenById(passwordReset.getResetId()));
               model.addAttribute("initTip", "first login, you need update your password");
               model.addAttribute("email", profile.getEmail());
               return "password/change_password";
            }
         }
      } else {
         model.addAttribute("errorMsg", "link source invalid");
         return "password/reset_error";
      }
   }

   @GetMapping("/forgotPasswordPage")
   public String forgotPasswordPage(Model model) {
      model.addAttribute("isNetworkAvaliable", NetworkUtils.isUrlAvailable("https://www.google.com/", 2000));
      model.addAttribute("cmndVersion", Utils.getCMNDMajorVersion());
      return "password/forgot_password";
   }

   @PostMapping("/createTicketToken")
   @ResponseBody
   public String createTicketToken(String username, HttpServletRequest request) {
      Profile profile = this.profileManager.loadByKey(username);
      if (profile == null) {
         return "fail";
      }

      PasswordReset passwordReset = this.createPasswordSet(username, "", (short)1, request);
      String token = this.getPasswordResetTokenById(passwordReset.getResetId());
      String expireTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.getPasswordResetExpireTime(passwordReset));
      JSONObject result = new JSONObject();
      result.put("token", token);
      result.put("expireTime", expireTime);
      return result.toString();
   }

   @PostMapping(value = "/sendResetLink", produces = "application/json; charset=utf-8")
   @ResponseBody
   public String sendResetLink(String username, String email, HttpServletRequest request) {
      JSONObject errorResult = new JSONObject("{\"status\":\"fail\"}");
      Profile profile = this.profileManager.findProfileByIdAndEmail(username, email);
      if (profile == null) {
         profile = this.profileManager.loadByKey(username);
         if (profile != null && StringUtils.isEmpty(profile.getEmail())) {
            LOG.warn("profile exists while email is empty: username=<{}>", username);
            return errorResult.put("status", "warning")
               .put("msg", "Please use ‘Reset by Ticket’ instead to perform the reset as the account has no email address configured.")
               .toString();
         } else {
            LOG.warn("cant find profile data: username=<{}>, email=<{}>", username, email);
            return errorResult.put("msg", "Email failed to send, please check your username and email address.").toString();
         }
      } else {
         PasswordReset passwordReset = this.createPasswordSet(username, email, (short)0, request);
         String token = this.getPasswordResetTokenById(passwordReset.getResetId());
         String resetLink = HttpUtils.getServerUrlByRequest(request) + request.getContextPath() + "/password/reset?token=" + token;
         String subject = "Reset your CMND Account password";
         String text = "<!DOCTYPE html>\r\n<html>\r\n  <body>\r\n    Hello!\r\n    <p>We have received a request from you to reset your CMND password. Please open the link below while being connected to the CMND server network to finish resetting the password.</p>\r\n    <p><a href=\""
            + resetLink
            + "\">Reset password</a></p>\r\n    <p>If you did not request a password reset for your CMND account, please ignore this message.</p>\r\n    <p>\r\n      Kind regards,\r\n      <br/>\r\n      CMND Team\r\n      <br/>\r\n    </p>\r\n  </body>\r\n</html>";
         boolean sendResult = EmailUtils.sendEmailByAmazonService(subject, text, email);
         LOG.info("send reset link to email <{}>, resetLink <{}>, result:<{}> ", email, resetLink, sendResult);
         return sendResult
            ? "{\"status\":\"success\"}"
            : errorResult.put("msg", "Reset email link failed to send. Please check your username or use ticket to find your password").toString();
      }
   }

   @PostMapping("/resetByTicket")
   public String resetByTicket(String resetToken, String resetTicket, Model model) {
      if (!this.checkTokenTicket(resetToken, resetTicket)) {
         LOG.error("failed to check token and ticket: token <{}> not compare ticket <{}>", resetToken, resetTicket);
         model.addAttribute("errorMsg", "reset ticket is invalid!");
         return "password/reset_error";
      } else {
         PasswordReset passwordReset = this.getPasswordResetByToken(resetToken, model);
         if (passwordReset == null) {
            return "password/reset_error";
         } else {
            Profile profile = this.profileManager.loadByKey(passwordReset.getUsername());
            if (profile == null) {
               model.addAttribute("errorMsg", "invliad modify user");
               return "password/reset_error";
            } else {
               model.addAttribute("username", passwordReset.getUsername());
               model.addAttribute("resetToken", resetToken);
               model.addAttribute("resetTicket", resetTicket);
               model.addAttribute("email", profile.getEmail());
               return "password/change_password";
            }
         }
      }
   }

   @GetMapping("/reset")
   public String resetByEmail(String token, Model model) {
      PasswordReset passwordReset = this.getPasswordResetByToken(token, model);
      if (passwordReset == null) {
         return "password/reset_error";
      } else {
         Profile profile = this.profileManager.loadByKey(passwordReset.getUsername());
         if (profile == null) {
            model.addAttribute("errorMsg", "invliad reset user");
            return "password/reset_error";
         } else {
            model.addAttribute("username", passwordReset.getUsername());
            model.addAttribute("resetToken", token);
            model.addAttribute("email", profile.getEmail());
            return "password/change_password";
         }
      }
   }

   @PostMapping(value = "/updatePassword", produces = "application/json; charset=utf-8")
   @ResponseBody
   public String updatePassword(String resetToken, String resetTicket, String password, String email, Model model, HttpServletRequest request) {
      PasswordReset passwordReset = this.getPasswordResetByToken(resetToken, model);
      if (passwordReset == null) {
         return "{\"status\":\"fail\"}";
      } else if (passwordReset.getType() == 1 && !this.checkTokenTicket(resetToken, resetTicket)) {
         return "{\"status\":\"fail\"}";
      } else if (passwordReset.getType() == 2 && !StringUtils.equalsIgnoreCase(passwordReset.getStartIp(), request.getRemoteAddr())) {
         return "{\"status\":\"fail\"}";
      } else {
         String modifyResult = UserManagerUtils.modifyUserPasswordWithoutLogin(passwordReset.getUsername(), password);
         if ("success".equals(modifyResult)) {
            passwordReset.setEndTime(new Date());
            passwordReset.setStatus((short)1);
            this.passwordResetManager.savePasswordReset(passwordReset);
            Profile profile = this.profileManager.loadByKey(passwordReset.getUsername());
            profile.setEmail(email);
            this.profileManager.save(profile);
            return "{\"status\":\"success\"}";
         } else {
            return "{\"status\":\"fail\"}";
         }
      }
   }

   @PostMapping("/loginFailure")
   public void loginFailure(HttpServletRequest request, String info, HttpServletResponse response) {
      JSONObject infoJson = null;

      try {
         String paramJson = new String(Base64.getDecoder().decode(info.getBytes()), StandardCharsets.UTF_8);
         infoJson = new JSONObject(paramJson);
      } catch (Exception e) {
         Utils.renderErrorJsonMsg("param info error", response);
         return;
      }

      String username = infoJson.optString("username", "");
      String checkKey = infoJson.optString("checkKey", "");
      long time = infoJson.optLong("time", 0L);
      if (!StringUtils.equalsIgnoreCase(checkKey, DigestUtils.md5Hex(username + time + "#)(UFKDFLSDFSDFSDFSDFDFF****#JFDNF"))) {
         Utils.renderErrorJsonMsg("invliad info string", response);
      } else {
         if (System.currentTimeMillis() - time >= 300000L) {
            Utils.renderErrorJsonMsg("request time expired", response);
         }

         UserEmailMonitorHelper.sendInvalidCredentialLoginAttemptNotice(username);
         Utils.renderSuccessJsonData(response);
      }
   }

   private boolean checkTokenTicket(String token, String ticket) {
      return StringUtils.equalsIgnoreCase(DigestUtils.md5Hex(token + "#" + "^^#$#*$#))FJDFLDS***##)))FSF__"), ticket);
   }

   private String getPasswordResetTokenById(int resetId) {
      return Base64.getEncoder().encodeToString(DESHelper.encode(String.valueOf(resetId), this.getCmndPasswordResetKey()).getBytes());
   }

   private int getResetIdFromToken(String token) {
      int resetId = 0;

      try {
         resetId = Integer.parseInt(DESHelper.decode(new String(Base64.getDecoder().decode(token), StandardCharsets.UTF_8), this.getCmndPasswordResetKey()));
      } catch (Exception e) {
         LOG.error("cant decode token:" + token, e);
      }

      return resetId;
   }

   private PasswordReset getPasswordResetByToken(String token, Model model) {
      int resetId = this.getResetIdFromToken(token);
      if (resetId == 0) {
         model.addAttribute("errorMsg", "token is invalid !");
         return null;
      } else {
         PasswordReset passwordReset = this.passwordResetManager.getPasswordResetById(resetId);
         if (passwordReset == null) {
            LOG.error("cant find reset id:<{}>", resetId);
            model.addAttribute("errorMsg", "cant find related reset password request!");
            return null;
         } else if (passwordReset.getStatus() != 0) {
            LOG.error("reset id:<{}> status value is <{}>, should be process status", resetId, passwordReset.getStatus());
            model.addAttribute("errorMsg", "reset request has been finished !");
            return null;
         } else if (System.currentTimeMillis() >= this.getPasswordResetExpireTime(passwordReset)) {
            LOG.error("reset id:<{}> token expired, createTime is <{}> ", resetId, passwordReset.getStartTime());
            model.addAttribute("errorMsg", "reset token has expire! ");
            return null;
         } else {
            return passwordReset;
         }
      }
   }

   private long getPasswordResetExpireTime(PasswordReset passwordReset) {
      int expireMinites = 0;
      if (2 == passwordReset.getType()) {
         expireMinites = 30;
      } else if (0 == passwordReset.getType()) {
         expireMinites = 1440;
      } else if (1 == passwordReset.getType()) {
         expireMinites = 10080;
      }

      return passwordReset.getStartTime().getTime() + expireMinites * 60 * 1000L;
   }

   private String getCmndPasswordResetKey() {
      SIConfig siConfig = this.sIConfigManager.getSIConfig();
      String resetKey = siConfig.getResetKey();
      if (StringUtils.isEmpty(resetKey)) {
         resetKey = TpvStringUtils.getRandomString(8);
         siConfig.setResetKey(resetKey);
         this.sIConfigManager.saveSIConfig(siConfig);
      }

      return resetKey;
   }

   private PasswordReset createPasswordSet(String username, String info, short type, HttpServletRequest request) {
      PasswordReset passwordReset = new PasswordReset();
      passwordReset.setUsername(username);
      passwordReset.setType(type);
      passwordReset.setInfo(info);
      passwordReset.setStartIp(request.getRemoteAddr());
      passwordReset.setStartTime(new Date());
      passwordReset.setStatus((short)0);
      this.passwordResetManager.savePasswordReset(passwordReset);
      return passwordReset;
   }
}
