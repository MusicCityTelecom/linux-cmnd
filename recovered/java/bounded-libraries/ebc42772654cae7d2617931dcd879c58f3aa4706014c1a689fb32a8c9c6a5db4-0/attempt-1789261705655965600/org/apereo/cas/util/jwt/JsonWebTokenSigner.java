/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.ArrayUtils
 *  org.jooq.lambda.Unchecked
 *  org.jose4j.jwa.AlgorithmConstraints
 *  org.jose4j.jwa.AlgorithmConstraints$ConstraintType
 *  org.jose4j.jws.JsonWebSignature
 *  org.jose4j.jwt.JwtClaims
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.util.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.Generated;
import org.apache.commons.lang3.ArrayUtils;
import org.apereo.cas.util.EncodingUtils;
import org.jooq.lambda.Unchecked;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonWebTokenSigner {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonWebTokenSigner.class);
    public static final Set<String> ALGORITHM_ALL_EXCEPT_NONE = Set.of("*");
    private final String keyId;
    private final String algorithm;
    private final Map<String, Object> headers;
    private final Key key;
    private final Set<String> allowedAlgorithms;

    public byte[] sign(byte[] value) {
        return (byte[])Unchecked.supplier(() -> {
            String base64 = EncodingUtils.encodeUrlSafeBase64(value);
            return this.sign(base64, true).getBytes(StandardCharsets.UTF_8);
        }).get();
    }

    public String sign(JwtClaims claims) {
        return (String)Unchecked.supplier(() -> {
            String jsonClaims = claims.toJson();
            return this.sign(jsonClaims, false);
        }).get();
    }

    private String sign(String payload, boolean encoded) throws Exception {
        JsonWebSignature jws = new JsonWebSignature();
        if (encoded) {
            jws.setEncodedPayload(payload);
        } else {
            jws.setPayload(payload);
        }
        jws.setAlgorithmHeaderValue(this.algorithm);
        jws.setAlgorithmConstraints(this.getAlgorithmConstraints());
        jws.setHeader("typ", "JWT");
        jws.setKey(this.key);
        jws.setKeyIdHeaderValue(this.keyId);
        this.headers.forEach((k, v) -> jws.setHeader(k, v.toString()));
        LOGGER.trace("Signing id token with key id header value [{}] and algorithm header value [{}]", (Object)jws.getKeyIdHeaderValue(), (Object)jws.getAlgorithmHeaderValue());
        return jws.getCompactSerialization();
    }

    private AlgorithmConstraints getAlgorithmConstraints() {
        return this.allowedAlgorithms.isEmpty() || this.allowedAlgorithms.contains("*") ? AlgorithmConstraints.DISALLOW_NONE : new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT, this.allowedAlgorithms.toArray(ArrayUtils.EMPTY_STRING_ARRAY));
    }

    @Generated
    private static String $default$keyId() {
        return UUID.randomUUID().toString();
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
    protected JsonWebTokenSigner(JsonWebTokenSignerBuilder<?, ?> b) {
        this.keyId = b.keyId$set ? b.keyId$value : JsonWebTokenSigner.$default$keyId();
        this.algorithm = b.algorithm;
        this.headers = b.headers$set ? b.headers$value : JsonWebTokenSigner.$default$headers();
        this.key = b.key;
        this.allowedAlgorithms = b.allowedAlgorithms$set ? b.allowedAlgorithms$value : JsonWebTokenSigner.$default$allowedAlgorithms();
    }

    @Generated
    public static JsonWebTokenSignerBuilder<?, ?> builder() {
        return new JsonWebTokenSignerBuilderImpl();
    }

    @Generated
    private static final class JsonWebTokenSignerBuilderImpl
    extends JsonWebTokenSignerBuilder<JsonWebTokenSigner, JsonWebTokenSignerBuilderImpl> {
        @Generated
        private JsonWebTokenSignerBuilderImpl() {
        }

        @Override
        @Generated
        protected JsonWebTokenSignerBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public JsonWebTokenSigner build() {
            return new JsonWebTokenSigner(this);
        }
    }

    @Generated
    public static abstract class JsonWebTokenSignerBuilder<C extends JsonWebTokenSigner, B extends JsonWebTokenSignerBuilder<C, B>> {
        @Generated
        private boolean keyId$set;
        @Generated
        private String keyId$value;
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
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B keyId(String keyId) {
            this.keyId$value = keyId;
            this.keyId$set = true;
            return this.self();
        }

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
        public String toString() {
            return "JsonWebTokenSigner.JsonWebTokenSignerBuilder(keyId$value=" + this.keyId$value + ", algorithm=" + this.algorithm + ", headers$value=" + this.headers$value + ", key=" + this.key + ", allowedAlgorithms$value=" + this.allowedAlgorithms$value + ")";
        }
    }
}

