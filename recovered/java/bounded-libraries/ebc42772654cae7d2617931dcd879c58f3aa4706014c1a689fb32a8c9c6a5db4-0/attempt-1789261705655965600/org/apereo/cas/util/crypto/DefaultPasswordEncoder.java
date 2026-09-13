/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.codec.binary.Hex
 *  org.apache.commons.codec.digest.DigestUtils
 *  org.apache.commons.lang3.BooleanUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.security.crypto.password.PasswordEncoder
 */
package org.apereo.cas.util.crypto;

import java.nio.charset.Charset;
import lombok.Generated;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.util.LoggingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DefaultPasswordEncoder
implements PasswordEncoder {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPasswordEncoder.class);
    private final String encodingAlgorithm;
    private final String characterEncoding;

    public String encode(CharSequence password) {
        if (password == null) {
            return null;
        }
        if (StringUtils.isBlank((CharSequence)this.encodingAlgorithm)) {
            LOGGER.warn("No encoding algorithm is defined. Password cannot be encoded; Returning null");
            return null;
        }
        String encodingCharToUse = StringUtils.isNotBlank((CharSequence)this.characterEncoding) ? this.characterEncoding : Charset.defaultCharset().name();
        LOGGER.debug("Using [{}] as the character encoding algorithm to update the digest", (Object)encodingCharToUse);
        try {
            byte[] pswBytes = password.toString().getBytes(encodingCharToUse);
            String encoded = Hex.encodeHexString((byte[])DigestUtils.getDigest((String)this.encodingAlgorithm).digest(pswBytes));
            LOGGER.debug("Encoded password via algorithm [{}] and character-encoding [{}] is [{}]", new Object[]{this.encodingAlgorithm, encodingCharToUse, encoded});
            return encoded;
        }
        catch (Exception e) {
            LoggingUtils.error(LOGGER, e);
            return null;
        }
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String encodedRawPassword = StringUtils.isNotBlank((CharSequence)rawPassword) ? this.encode(rawPassword.toString()) : null;
        boolean matched = StringUtils.equals((CharSequence)encodedRawPassword, (CharSequence)encodedPassword);
        String msg = String.format("Provided password does%smatch the encoded password", BooleanUtils.toString((boolean)matched, (String)"", (String)" not "));
        LOGGER.debug(msg);
        return matched;
    }

    @Generated
    public DefaultPasswordEncoder(String encodingAlgorithm, String characterEncoding) {
        this.encodingAlgorithm = encodingAlgorithm;
        this.characterEncoding = characterEncoding;
    }
}

