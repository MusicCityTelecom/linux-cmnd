/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.ArrayUtils
 *  org.jose4j.jwa.AlgorithmConstraints
 *  org.jose4j.jwa.AlgorithmConstraints$ConstraintType
 *  org.jose4j.jwe.JsonWebEncryption
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.util.jwt;

import java.io.Serializable;
import java.security.Key;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.Generated;
import org.apache.commons.lang3.ArrayUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwe.JsonWebEncryption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonWebTokenEncryptor {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonWebTokenEncryptor.class);
    public static final List<String> ALGORITHM_ALL_EXCEPT_NONE = List.of("*");
    private final String algorithm;
    private final Map<String, Object> headers;
    private final Key key;
    private final Set<String> allowedAlgorithms;
    private final Set<String> allowedContentEncryptionAlgorithms;
    private final String keyId;
    private String encryptionMethod;

    public String encrypt(Serializable payload) {
        try {
            JsonWebEncryption jwe = new JsonWebEncryption();
            jwe.setPayload(payload.toString());
            jwe.enableDefaultCompression();
            jwe.setAlgorithmHeaderValue(this.algorithm);
            jwe.setEncryptionMethodHeaderParameter(this.encryptionMethod);
            jwe.setKey(this.key);
            jwe.setAlgorithmConstraints(this.getAlgorithmConstraints());
            jwe.setContentEncryptionAlgorithmConstraints(this.getContentEncryptionAlgorithmConstraints());
            jwe.setContentTypeHeaderValue("JWT");
            jwe.setHeader("typ", "JWT");
            FunctionUtils.doIfNotNull(this.keyId, arg_0 -> ((JsonWebEncryption)jwe).setKeyIdHeaderValue(arg_0));
            this.headers.forEach((k, v) -> jwe.setHeader(k, v.toString()));
            LOGGER.trace("Encrypting via [{}]", (Object)this.encryptionMethod);
            return jwe.getCompactSerialization();
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    private AlgorithmConstraints getAlgorithmConstraints() {
        return this.allowedAlgorithms.isEmpty() || this.allowedAlgorithms.contains("*") ? AlgorithmConstraints.DISALLOW_NONE : new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT, this.allowedAlgorithms.toArray(ArrayUtils.EMPTY_STRING_ARRAY));
    }

    private AlgorithmConstraints getContentEncryptionAlgorithmConstraints() {
        return this.allowedContentEncryptionAlgorithms.isEmpty() || this.allowedContentEncryptionAlgorithms.contains("*") ? AlgorithmConstraints.DISALLOW_NONE : new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT, this.allowedContentEncryptionAlgorithms.toArray(ArrayUtils.EMPTY_STRING_ARRAY));
    }

    @Generated
    private static Map<String, Object> $default$headers() {
        return new LinkedHashMap<String, Object>();
    }

    @Generated
    private static Set<String> $default$allowedAlgorithms() {
        return new LinkedHashSet<String>();
    }

    @Generated
    private static Set<String> $default$allowedContentEncryptionAlgorithms() {
        return new LinkedHashSet<String>();
    }

    @Generated
    private static String $default$keyId() {
        return UUID.randomUUID().toString();
    }

    @Generated
    protected JsonWebTokenEncryptor(JsonWebTokenEncryptorBuilder<?, ?> b) {
        this.algorithm = b.algorithm;
        this.headers = b.headers$set ? b.headers$value : JsonWebTokenEncryptor.$default$headers();
        this.key = b.key;
        this.allowedAlgorithms = b.allowedAlgorithms$set ? b.allowedAlgorithms$value : JsonWebTokenEncryptor.$default$allowedAlgorithms();
        this.allowedContentEncryptionAlgorithms = b.allowedContentEncryptionAlgorithms$set ? b.allowedContentEncryptionAlgorithms$value : JsonWebTokenEncryptor.$default$allowedContentEncryptionAlgorithms();
        this.keyId = b.keyId$set ? b.keyId$value : JsonWebTokenEncryptor.$default$keyId();
        this.encryptionMethod = b.encryptionMethod;
    }

    @Generated
    public static JsonWebTokenEncryptorBuilder<?, ?> builder() {
        return new JsonWebTokenEncryptorBuilderImpl();
    }

    @Generated
    private static final class JsonWebTokenEncryptorBuilderImpl
    extends JsonWebTokenEncryptorBuilder<JsonWebTokenEncryptor, JsonWebTokenEncryptorBuilderImpl> {
        @Generated
        private JsonWebTokenEncryptorBuilderImpl() {
        }

        @Override
        @Generated
        protected JsonWebTokenEncryptorBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public JsonWebTokenEncryptor build() {
            return new JsonWebTokenEncryptor(this);
        }
    }

    @Generated
    public static abstract class JsonWebTokenEncryptorBuilder<C extends JsonWebTokenEncryptor, B extends JsonWebTokenEncryptorBuilder<C, B>> {
        @Generated
        private String algorithm;
        @Generated
        private boolean headers$set;
        @Generated
        private Map<String, Object> headers$value;
        @Generated
        private Key key;
        @Generated
        private boolean allowedAlgorithms$set;
        @Generated
        private Set<String> allowedAlgorithms$value;
        @Generated
        private boolean allowedContentEncryptionAlgorithms$set;
        @Generated
        private Set<String> allowedContentEncryptionAlgorithms$value;
        @Generated
        private boolean keyId$set;
        @Generated
        private String keyId$value;
        @Generated
        private String encryptionMethod;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B algorithm(String algorithm) {
            this.algorithm = algorithm;
            return this.self();
        }

        @Generated
        public B headers(Map<String, Object> headers) {
            this.headers$value = headers;
            this.headers$set = true;
            return this.self();
        }

        @Generated
        public B key(Key key) {
            this.key = key;
            return this.self();
        }

        @Generated
        public B allowedAlgorithms(Set<String> allowedAlgorithms) {
            this.allowedAlgorithms$value = allowedAlgorithms;
            this.allowedAlgorithms$set = true;
            return this.self();
        }

        @Generated
        public B allowedContentEncryptionAlgorithms(Set<String> allowedContentEncryptionAlgorithms) {
            this.allowedContentEncryptionAlgorithms$value = allowedContentEncryptionAlgorithms;
            this.allowedContentEncryptionAlgorithms$set = true;
            return this.self();
        }

        @Generated
        public B keyId(String keyId) {
            this.keyId$value = keyId;
            this.keyId$set = true;
            return this.self();
        }

        @Generated
        public B encryptionMethod(String encryptionMethod) {
            this.encryptionMethod = encryptionMethod;
            return this.self();
        }

        @Generated
        public String toString() {
            return "JsonWebTokenEncryptor.JsonWebTokenEncryptorBuilder(algorithm=" + this.algorithm + ", headers$value=" + this.headers$value + ", key=" + this.key + ", allowedAlgorithms$value=" + this.allowedAlgorithms$value + ", allowedContentEncryptionAlgorithms$value=" + this.allowedContentEncryptionAlgorithms$value + ", keyId$value=" + this.keyId$value + ", encryptionMethod=" + this.encryptionMethod + ")";
        }
    }
}

