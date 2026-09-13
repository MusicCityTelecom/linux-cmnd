/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.util;

import be.tpvision.smartcontrol.messages.util.security_utilities.DecryptMessages;
import be.tpvision.smartcontrol.messages.util.security_utilities.EncryptMessages;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class SecurityUtilities {
    private static final Logger logger = LoggerFactory.getLogger(SecurityUtilities.class);
    private static final String SECRET_KEY = "MySecretKey";
    private static final String ENCRYPTION = "AES";
    private static SecretKey secretKey;

    private SecurityUtilities() {
    }

    public static String encrypt(String input) {
        Assert.notNull((Object)input, EncryptMessages.INPUT_CAN_NOT_BE_NULL);
        try {
            Cipher cipher = Cipher.getInstance(ENCRYPTION);
            cipher.init(1, secretKey);
            byte[] inputBytes = input.getBytes();
            byte[] encryptedInputBytes = cipher.doFinal(inputBytes);
            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(encryptedInputBytes);
        }
        catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            String message = e.getMessage();
            logger.error(message, e);
            return null;
        }
    }

    public static String decrypt(String input) {
        Assert.notNull((Object)input, DecryptMessages.INPUT_CAN_NOT_BE_NULL);
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encryptedInputBytes = decoder.decode(input);
        try {
            Cipher cipher = Cipher.getInstance(ENCRYPTION);
            cipher.init(2, secretKey);
            byte[] decryptedInputBytes = cipher.doFinal(encryptedInputBytes);
            return new String(decryptedInputBytes);
        }
        catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            String message = e.getMessage();
            logger.error(message, e);
            return null;
        }
    }

    static {
        byte[] secretKeyBytes = SECRET_KEY.getBytes();
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            secretKeyBytes = sha.digest(secretKeyBytes);
            secretKeyBytes = Arrays.copyOf(secretKeyBytes, 16);
            secretKey = new SecretKeySpec(secretKeyBytes, ENCRYPTION);
        }
        catch (NoSuchAlgorithmException e) {
            String message = e.getMessage();
            logger.error(message, e);
        }
    }
}

