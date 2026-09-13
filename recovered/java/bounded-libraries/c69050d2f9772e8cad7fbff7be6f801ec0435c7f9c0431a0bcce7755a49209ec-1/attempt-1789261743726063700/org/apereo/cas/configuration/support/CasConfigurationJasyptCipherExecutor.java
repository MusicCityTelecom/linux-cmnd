/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.math.NumberUtils
 *  org.apereo.cas.util.crypto.CipherExecutor
 *  org.bouncycastle.jce.provider.BouncyCastleProvider
 *  org.jasypt.encryption.pbe.StandardPBEStringEncryptor
 *  org.jasypt.iv.IvGenerator
 *  org.jasypt.iv.NoIvGenerator
 *  org.jasypt.iv.RandomIvGenerator
 *  org.springframework.core.env.Environment
 */
package org.apereo.cas.configuration.support;

import java.security.Provider;
import java.security.Security;
import java.util.function.Function;
import java.util.regex.Pattern;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apereo.cas.util.crypto.CipherExecutor;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.IvGenerator;
import org.jasypt.iv.NoIvGenerator;
import org.jasypt.iv.RandomIvGenerator;
import org.springframework.core.env.Environment;

public class CasConfigurationJasyptCipherExecutor
implements CipherExecutor<String, String> {
    public static final String ENCRYPTED_VALUE_PREFIX = "{cas-cipher}";
    private static final Pattern ALGS_THAT_REQUIRE_IV_PATTERN = Pattern.compile("PBEWITHHMACSHA\\d+ANDAES_.*(?<!-BC)$");
    private final StandardPBEStringEncryptor jasyptInstance;

    public CasConfigurationJasyptCipherExecutor(String algorithm, String password) {
        Security.addProvider((Provider)new BouncyCastleProvider());
        this.jasyptInstance = new StandardPBEStringEncryptor();
        this.setIvGenerator((IvGenerator)new RandomIvGenerator());
        this.setAlgorithm(algorithm);
        this.setPassword(password);
    }

    public CasConfigurationJasyptCipherExecutor(Environment environment) {
        this(CasConfigurationJasyptCipherExecutor.getJasyptParamFromEnv(environment, JasyptEncryptionParameters.ALGORITHM), CasConfigurationJasyptCipherExecutor.getJasyptParamFromEnv(environment, JasyptEncryptionParameters.PASSWORD));
        String pName = CasConfigurationJasyptCipherExecutor.getJasyptParamFromEnv(environment, JasyptEncryptionParameters.PROVIDER);
        this.setProviderName(pName);
        String iter = CasConfigurationJasyptCipherExecutor.getJasyptParamFromEnv(environment, JasyptEncryptionParameters.ITERATIONS);
        this.setKeyObtentionIterations(iter);
        String initialize = CasConfigurationJasyptCipherExecutor.getJasyptParamFromEnv(environment, JasyptEncryptionParameters.INITIALIZATION_VECTOR);
        if (StringUtils.isNotBlank((CharSequence)initialize)) {
            boolean required = Boolean.parseBoolean(initialize);
            this.setIvGenerator((IvGenerator)(required ? new RandomIvGenerator() : new NoIvGenerator()));
        }
    }

    private static String getJasyptParamFromEnv(Environment environment, JasyptEncryptionParameters param) {
        return environment.getProperty(param.getPropertyName(), param.getDefaultValue());
    }

    public void setAlgorithm(String alg) {
        if (StringUtils.isNotBlank((CharSequence)alg)) {
            LOGGER.debug("Configured Jasypt algorithm [{}]", (Object)alg);
            this.jasyptInstance.setAlgorithm(alg);
            boolean required = CasConfigurationJasyptCipherExecutor.isVectorInitializationRequiredFor(alg);
            this.setIvGenerator((IvGenerator)(required ? new RandomIvGenerator() : new NoIvGenerator()));
        }
    }

    public void setIvGenerator(IvGenerator iv) {
        this.jasyptInstance.setIvGenerator(iv);
    }

    private static boolean isVectorInitializationRequiredFor(String algorithm) {
        return StringUtils.isNotBlank((CharSequence)algorithm) && ALGS_THAT_REQUIRE_IV_PATTERN.matcher(algorithm).matches();
    }

    public void setPassword(String psw) {
        if (StringUtils.isNotBlank((CharSequence)psw)) {
            LOGGER.debug("Configured Jasypt password");
            this.jasyptInstance.setPassword(psw);
        }
    }

    public void setKeyObtentionIterations(String iter) {
        if (StringUtils.isNotBlank((CharSequence)iter) && NumberUtils.isCreatable((String)iter)) {
            LOGGER.debug("Configured Jasypt iterations");
            this.jasyptInstance.setKeyObtentionIterations(Integer.parseInt(iter));
        }
    }

    public void setProviderName(String pName) {
        if (StringUtils.isNotBlank((CharSequence)pName)) {
            LOGGER.debug("Configured Jasypt provider");
            this.jasyptInstance.setProviderName(pName);
        }
    }

    public String encode(String value, Object[] parameters) {
        return this.encryptValue(value);
    }

    public String decode(String value, Object[] parameters) {
        return this.decryptValue(value);
    }

    public String getName() {
        return "CAS Configuration Jasypt Encryption";
    }

    public String encryptValue(String value, Function<Exception, String> handler) {
        try {
            return this.encryptValueAndThrow(value);
        }
        catch (Exception e) {
            return handler.apply(e);
        }
    }

    public String encryptValue(String value) {
        return this.encryptValue(value, e -> {
            LOGGER.warn("Could not encrypt value [{}]", (Object)value, e);
            return null;
        });
    }

    private String encryptValueAndThrow(String value) {
        this.initializeJasyptInstanceIfNecessary();
        return ENCRYPTED_VALUE_PREFIX + this.jasyptInstance.encrypt(value);
    }

    public String decryptValue(String value) {
        try {
            return this.decryptValueAndThrow(value);
        }
        catch (Exception e) {
            LOGGER.warn("Could not decrypt value [{}]", (Object)value, (Object)e);
            return null;
        }
    }

    private String decryptValueDirect(String value) {
        this.initializeJasyptInstanceIfNecessary();
        LOGGER.trace("Decrypting value [{}]...", (Object)value);
        String result = this.jasyptInstance.decrypt(value);
        if (StringUtils.isNotBlank((CharSequence)result)) {
            LOGGER.debug("Decrypted value [{}] successfully.", (Object)value);
            return result;
        }
        LOGGER.warn("Encrypted value [{}] has no values.", (Object)value);
        return value;
    }

    public static boolean isValueEncrypted(String value) {
        return StringUtils.isNotBlank((CharSequence)value) && value.startsWith(ENCRYPTED_VALUE_PREFIX);
    }

    public static String extractEncryptedValue(String value) {
        return CasConfigurationJasyptCipherExecutor.isValueEncrypted(value) ? value.substring(ENCRYPTED_VALUE_PREFIX.length()) : value;
    }

    private String decryptValueAndThrow(String value) {
        if (CasConfigurationJasyptCipherExecutor.isValueEncrypted(value)) {
            String encValue = CasConfigurationJasyptCipherExecutor.extractEncryptedValue(value);
            return this.decryptValueDirect(encValue);
        }
        return value;
    }

    private void initializeJasyptInstanceIfNecessary() {
        if (!this.jasyptInstance.isInitialized()) {
            LOGGER.trace("Initializing Jasypt...");
            this.jasyptInstance.initialize();
        }
    }

    public static enum JasyptEncryptionParameters {
        ALGORITHM("cas.standalone.configuration-security.alg", "PBEWithMD5AndTripleDES"),
        PROVIDER("cas.standalone.configuration-security.provider", null),
        ITERATIONS("cas.standalone.configuration-security.iterations", null),
        PASSWORD("cas.standalone.configuration-security.psw", null),
        INITIALIZATION_VECTOR("cas.standalone.configuration-security.initialization-vector", null);

        private final String propertyName;
        private final String defaultValue;

        private JasyptEncryptionParameters(String name, String defaultValue) {
            this.propertyName = name;
            this.defaultValue = defaultValue;
        }

        @Generated
        public String getPropertyName() {
            return this.propertyName;
        }

        @Generated
        public String getDefaultValue() {
            return this.defaultValue;
        }
    }
}

