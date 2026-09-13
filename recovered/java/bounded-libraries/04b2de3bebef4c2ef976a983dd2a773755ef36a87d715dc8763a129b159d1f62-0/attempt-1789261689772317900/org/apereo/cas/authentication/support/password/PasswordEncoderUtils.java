/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.BooleanUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.configuration.model.core.authentication.PasswordEncoderProperties
 *  org.apereo.cas.configuration.model.core.authentication.PasswordEncoderProperties$PasswordEncoderTypes
 *  org.apereo.cas.util.LoggingUtils
 *  org.apereo.cas.util.RandomUtils
 *  org.apereo.cas.util.crypto.DefaultPasswordEncoder
 *  org.apereo.cas.util.crypto.GlibcCryptPasswordEncoder
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.core.io.Resource
 *  org.springframework.security.crypto.argon2.Argon2PasswordEncoder
 *  org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
 *  org.springframework.security.crypto.password.LdapShaPasswordEncoder
 *  org.springframework.security.crypto.password.NoOpPasswordEncoder
 *  org.springframework.security.crypto.password.PasswordEncoder
 *  org.springframework.security.crypto.password.Pbkdf2PasswordEncoder
 *  org.springframework.security.crypto.password.StandardPasswordEncoder
 *  org.springframework.security.crypto.scrypt.SCryptPasswordEncoder
 */
package org.apereo.cas.authentication.support.password;

import lombok.Generated;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.support.password.GroovyPasswordEncoder;
import org.apereo.cas.configuration.model.core.authentication.PasswordEncoderProperties;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.util.RandomUtils;
import org.apereo.cas.util.crypto.DefaultPasswordEncoder;
import org.apereo.cas.util.crypto.GlibcCryptPasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

public final class PasswordEncoderUtils {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordEncoderUtils.class);
    private static final int HASH_WIDTH = 256;
    private static final int ARGON2_DEFAULT_MEMORY = 4096;
    private static final int ARGON2_DEFAULT_ITERATIONS = 3;

    public static PasswordEncoder newPasswordEncoder(PasswordEncoderProperties properties, ApplicationContext applicationContext) {
        String type = properties.getType();
        if (StringUtils.isBlank((CharSequence)type)) {
            LOGGER.trace("No password encoder type is defined, and so none shall be created");
            return NoOpPasswordEncoder.getInstance();
        }
        if (type.endsWith(".groovy")) {
            LOGGER.trace("Creating Groovy-based password encoder at [{}]", (Object)type);
            Resource resource = applicationContext.getResource(type);
            return new GroovyPasswordEncoder(resource, applicationContext);
        }
        if (type.contains(".")) {
            try {
                LOGGER.debug("Configuration indicates use of a custom password encoder [{}]", (Object)type);
                Class<?> clazz = Class.forName(type);
                return (PasswordEncoder)clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            }
            catch (Exception e) {
                String msg = "Falling back to a no-op password encoder as CAS has failed to create an instance of the custom password encoder class " + type;
                LoggingUtils.error((Logger)LOGGER, (String)msg, (Throwable)e);
                return NoOpPasswordEncoder.getInstance();
            }
        }
        PasswordEncoderProperties.PasswordEncoderTypes encoderType = PasswordEncoderProperties.PasswordEncoderTypes.valueOf((String)type);
        switch (encoderType) {
            case DEFAULT: {
                LOGGER.debug("Creating default password encoder with encoding alg [{}] and character encoding [{}]", (Object)properties.getEncodingAlgorithm(), (Object)properties.getCharacterEncoding());
                return new DefaultPasswordEncoder(properties.getEncodingAlgorithm(), properties.getCharacterEncoding());
            }
            case STANDARD: {
                LOGGER.debug("Creating standard password encoder with the secret defined in the configuration");
                return new StandardPasswordEncoder((CharSequence)properties.getSecret());
            }
            case ARGON2: {
                return new Argon2PasswordEncoder(properties.getStrength(), properties.getHashLength(), 1, 4096, 3);
            }
            case BCRYPT: {
                LOGGER.debug("Creating BCRYPT password encoder given the strength [{}] and secret in the configuration", (Object)properties.getStrength());
                if (StringUtils.isBlank((CharSequence)properties.getSecret())) {
                    LOGGER.debug("Creating BCRYPT encoder without secret");
                    return new BCryptPasswordEncoder(properties.getStrength());
                }
                LOGGER.debug("Creating BCRYPT encoder with secret");
                return new BCryptPasswordEncoder(properties.getStrength(), RandomUtils.getNativeInstance());
            }
            case SCRYPT: {
                LOGGER.debug("Creating SCRYPT encoder");
                return new SCryptPasswordEncoder();
            }
            case SSHA: {
                LOGGER.warn("Creating SSHA encoder; digest based password encoding is not considered secure. This strategy is here to support legacy implementations and using it is considered insecure.");
                return new LdapShaPasswordEncoder();
            }
            case PBKDF2: {
                if (StringUtils.isBlank((CharSequence)properties.getSecret())) {
                    LOGGER.trace("Creating PBKDF2 encoder without secret");
                    return new Pbkdf2PasswordEncoder();
                }
                return new Pbkdf2PasswordEncoder((CharSequence)properties.getSecret(), properties.getStrength(), 256);
            }
            case GLIBC_CRYPT: {
                boolean hasSecret = StringUtils.isNotBlank((CharSequence)properties.getSecret());
                String msg = String.format("Creating glibc CRYPT encoder with encoding alg [%s], strength [%s] and %ssecret", properties.getEncodingAlgorithm(), properties.getStrength(), BooleanUtils.toString((boolean)hasSecret, (String)"", (String)"without "));
                LOGGER.debug(msg);
                return new GlibcCryptPasswordEncoder(properties.getEncodingAlgorithm(), properties.getStrength(), properties.getSecret());
            }
        }
        LOGGER.trace("No password encoder shall be created given the requested encoder type [{}]", (Object)type);
        return NoOpPasswordEncoder.getInstance();
    }

    @Generated
    private PasswordEncoderUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

