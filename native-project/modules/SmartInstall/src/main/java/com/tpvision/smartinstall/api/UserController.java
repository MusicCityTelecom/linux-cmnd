package com.tpvision.smartinstall.api;

import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.japit.SecuredCmdControlManager;
import com.tpvision.smartinstall.util.CertUtils;
import com.tpvision.smartinstall.util.TpvStringUtils;
import com.tpvision.smartinstall.util.UserManagerUtils;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.crypto.Cipher;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {
   private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
   public static final String API_USER_TOKEN_SUBJECT = "cmnd rest api";
   public static final String EXAPI_USER_TOKEN_SUBJECT = "cmnd rest exapi";
   public static final int JWT_EXPIRE_IN_HOUR = 24;
   public static final int PASSWORD_ENC_TYPE_RAW = 0;
   public static final int PASSWORD_ENC_TYPE_RSA = 1;

   @GetMapping("/api/user/pubkey")
   public JSONObject getRSAPublicKey() {
      JSONObject result = new JSONObject();
      result.put("key", CertUtils.getRSAPublicKey());
      return result;
   }

   @PostMapping("/api/user/validate")
   public ApiErrorCode validate(String username, String password, @RequestParam(value = "encType", defaultValue = "0") int encType) {
      if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
         if (encType == 1) {
            username = this.decryptByPrivateKey(username);
            if (StringUtils.isEmpty(username)) {
               return ApiErrorCode.USER_USERINFO_ENC_USERNAME_ERROR;
            }

            password = this.decryptByPrivateKey(password);
            if (StringUtils.isEmpty(password)) {
               return ApiErrorCode.USER_USERINFO_ENC_PASSWORD_ERROR;
            }
         }

         String checkResult = UserManagerUtils.checkUserPasswordWithoutLogin(username, password);
         if (TpvStringUtils.isJSONString(checkResult)) {
            JSONObject jsonObject = new JSONObject(checkResult);
            if (jsonObject.getInt("code") == 0) {
               return ApiErrorCode.SUCCESS_OK;
            }
         }

         return ApiErrorCode.USER_VALIDATE_CHECK_FAILURE;
      } else {
         return ApiErrorCode.USER_USERNAME_PASSWORD_EMPTY;
      }
   }

   @PostMapping("/api/user/login")
   public Object login(String username, String password, @RequestParam(value = "encType", defaultValue = "0") int encType) {
      ApiErrorCode validateResult = this.validate(username, password, encType);
      if (validateResult == ApiErrorCode.SUCCESS_OK) {
         String subject = "cmnd rest api";
         String jwt = this.createJWT(24, username, subject);
         Map<String, Object> resultData = new HashMap<>();
         resultData.put("token", jwt);
         resultData.put("expire_hour", 24);
         return resultData;
      } else {
         return validateResult == ApiErrorCode.USER_VALIDATE_CHECK_FAILURE ? ApiErrorCode.USER_LOGIN_FAILURE : validateResult;
      }
   }

   @PostMapping("/exapi/login")
   public Object exApiLogin(String username, String password) {
      if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
         String checkResult = UserManagerUtils.checkUserPasswordWithoutLogin(username, password);
         if (TpvStringUtils.isJSONString(checkResult)) {
            JSONObject jsonObject = new JSONObject(checkResult);
            if (jsonObject.getInt("code") == 0) {
               String role = JpaManager.getProfileManager().findRoleNameByUsername(username);
               if (!StringUtils.equalsIgnoreCase("ROLE_ADMIN", role)) {
                  return ApiErrorCode.USER_ROLE_PERMISSION_NOT_ENOUGH;
               }

               String subject = "cmnd rest exapi";
               String jwt = this.createJWT(24, username, subject);
               Map<String, Object> resultData = new HashMap<>();
               resultData.put("token", jwt);
               resultData.put("expire_hour", 24);
               return resultData;
            }
         }

         return ApiErrorCode.USER_LOGIN_FAILURE;
      } else {
         return ApiErrorCode.USER_USERNAME_PASSWORD_EMPTY;
      }
   }

   private String createJWT(int expireSeconds, String username, String subject) {
      SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
      Date now = new Date();
      Map<String, Object> claims = new HashMap<>();
      claims.put("username", username);
      String key = SecuredCmdControlManager.SECURIT_KEY;
      JwtBuilder builder = Jwts.builder()
         .setClaims(claims)
         .setId(UUID.randomUUID().toString())
         .setIssuedAt(now)
         .setSubject(subject)
         .signWith(signatureAlgorithm, key);
      long expMillis = System.currentTimeMillis() + expireSeconds * 3600 * 1000;
      Date exp = new Date(expMillis);
      builder.setExpiration(exp);
      return builder.compact();
   }

   private String decryptByPrivateKey(String text) {
      try {
         String privateKeyText = CertUtils.getRSAPrivateKey();
         PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyText));
         KeyFactory keyFactory = KeyFactory.getInstance("RSA");
         PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec5);
         Cipher cipher = Cipher.getInstance("RSA");
         cipher.init(2, privateKey);
         byte[] result = cipher.doFinal(Base64.decodeBase64(text));
         return new String(result);
      } catch (Exception e) {
         LOG.error("decrypt password failure", e);
         return null;
      }
   }
}
