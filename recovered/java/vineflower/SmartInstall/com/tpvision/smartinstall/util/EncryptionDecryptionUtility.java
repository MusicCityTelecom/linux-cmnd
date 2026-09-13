package com.tpvision.smartinstall.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptionDecryptionUtility {
   private static final Logger LOG = LoggerFactory.getLogger(EncryptionDecryptionUtility.class);
   private static final String AES_EMAIL_KEY = "^%&(*(YFD*^*(*))(R%%)*&^^**((^$$";
   private static final String INIT_VECTOR = "japitCMNDsaltVec";

   private EncryptionDecryptionUtility() {
   }

   private static SecretKeySpec getKey(String input) {
      MessageDigest sha = null;

      try {
         byte[] key = input.getBytes(StandardCharsets.UTF_8);
         sha = MessageDigest.getInstance("SHA-256");
         key = sha.digest(key);
         return new SecretKeySpec(key, "AES");
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         return null;
      }
   }

   public static String getSHA256Hash(String input) {
      MessageDigest sha = null;

      try {
         byte[] key = input.getBytes(StandardCharsets.UTF_8);
         sha = MessageDigest.getInstance("SHA-256");
         key = sha.digest(key);
         return Base64.getEncoder().encodeToString(key);
      } catch (Exception e) {
         LOG.error(e.getMessage(), e);
         return null;
      }
   }

   public static String encrypt(String plainText, String key) {
      try {
         IvParameterSpec iv = new IvParameterSpec("japitCMNDsaltVec".getBytes(StandardCharsets.UTF_8));
         SecretKeySpec skeySpec = getKey(key);
         Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
         cipher.init(1, skeySpec, iv);
         byte[] encrypted = cipher.doFinal(plainText.getBytes());
         return Base64.getEncoder().encodeToString(encrypted);
      } catch (Exception ex) {
         LOG.error(ex.getMessage(), ex);
         return null;
      }
   }

   public static String decrypt(String encryptedText, String key) {
      try {
         IvParameterSpec iv = new IvParameterSpec("japitCMNDsaltVec".getBytes(StandardCharsets.UTF_8));
         SecretKeySpec skeySpec = getKey(key);
         Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
         cipher.init(2, skeySpec, iv);
         byte[] encryptedTextBytes = Base64.getDecoder().decode(encryptedText);
         byte[] original = cipher.doFinal(encryptedTextBytes);
         return new String(original);
      } catch (Exception ex) {
         LOG.error(ex.getMessage(), ex);
         return null;
      }
   }

   public static String decryptEmailParam(String text) {
      return decrypt(text, "^%&(*(YFD*^*(*))(R%%)*&^^**((^$$");
   }
}
