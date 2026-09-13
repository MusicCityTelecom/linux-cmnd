/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.codec.digest.Crypt
 *  org.apache.commons.lang3.BooleanUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.security.crypto.password.PasswordEncoder
 */
package org.apereo.cas.util.crypto;

import lombok.Generated;
import org.apache.commons.codec.digest.Crypt;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.util.gen.HexRandomStringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

public class GlibcCryptPasswordEncoder
implements PasswordEncoder {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(GlibcCryptPasswordEncoder.class);
    private static final int SALT_LENGTH = 8;
    private final String encodingAlgorithm;
    private final int strength;
    private String secret;

    public String encode(CharSequence password) {
        if (password == null) {
            return null;
        }
        if (StringUtils.isBlank((CharSequence)this.encodingAlgorithm)) {
            LOGGER.warn("No encoding algorithm is defined. Password cannot be encoded; Returning null");
            return null;
        }
        return Crypt.crypt((String)password.toString(), (String)this.generateCryptSalt());
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (StringUtils.isBlank((CharSequence)encodedPassword)) {
            LOGGER.warn("The encoded password provided for matching is null. Returning false");
            return false;
        }
        String providedSalt = "";
        int lastDollarIndex = encodedPassword.lastIndexOf(36);
        if (lastDollarIndex == -1) {
            providedSalt = encodedPassword.substring(0, 2);
            LOGGER.debug("Assuming DES UnixCrypt as no delimiter could be found in the encoded password provided");
        } else {
            providedSalt = encodedPassword.substring(0, lastDollarIndex);
            LOGGER.debug("Encoded password uses algorithm [{}]", (Object)Character.valueOf(providedSalt.charAt(1)));
        }
        String encodedRawPassword = Crypt.crypt((String)rawPassword.toString(), (String)providedSalt);
        boolean matched = StringUtils.equals((CharSequence)encodedRawPassword, (CharSequence)encodedPassword);
        String msg = String.format("Provided password does %smatch the encoded password", BooleanUtils.toString((boolean)matched, (String)"", (String)"not "));
        LOGGER.debug(msg);
        return matched;
    }

    private String generateCryptSalt() {
        StringBuilder cryptSalt = new StringBuilder();
        if ("1".equals(this.encodingAlgorithm) || "MD5".equalsIgnoreCase(this.encodingAlgorithm)) {
            cryptSalt.append("$1$");
            LOGGER.debug("Encoding with MD5 algorithm");
        } else if ("5".equals(this.encodingAlgorithm) || "SHA-256".equalsIgnoreCase(this.encodingAlgorithm)) {
            cryptSalt.append("$5$rounds=").append(this.strength).append('$');
            LOGGER.debug("Encoding with SHA-256 algorithm and [{}] rounds", (Object)this.strength);
        } else if ("6".equals(this.encodingAlgorithm) || "SHA-512".equalsIgnoreCase(this.encodingAlgorithm)) {
            cryptSalt.append("$6$rounds=").append(this.strength).append('$');
            LOGGER.debug("Encoding with SHA-512 algorithm and [{}] rounds", (Object)this.strength);
        } else {
            cryptSalt.append(this.encodingAlgorithm);
            LOGGER.debug("Encoding with DES UnixCrypt algorithm as no indicator for another algorithm was found.");
        }
        if (StringUtils.isBlank((CharSequence)this.secret)) {
            LOGGER.debug("No secret was found. Generating a salt with length [{}]", (Object)8);
            HexRandomStringGenerator keygen = new HexRandomStringGenerator(8);
            this.secret = keygen.getNewString();
        } else {
            LOGGER.trace("The provided secret is used as a salt");
        }
        cryptSalt.append(this.secret);
        return cryptSalt.toString();
    }

    @Generated
    public GlibcCryptPasswordEncoder(String encodingAlgorithm, int strength, String secret) {
        this.encodingAlgorithm = encodingAlgorithm;
        this.strength = strength;
        this.secret = secret;
    }
}

