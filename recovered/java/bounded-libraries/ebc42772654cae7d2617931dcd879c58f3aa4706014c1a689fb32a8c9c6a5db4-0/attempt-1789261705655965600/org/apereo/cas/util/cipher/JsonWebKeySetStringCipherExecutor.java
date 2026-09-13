/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.io.FileUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.jooq.lambda.Unchecked
 *  org.jooq.lambda.fi.util.function.CheckedConsumer
 *  org.jose4j.jwk.HttpsJwks
 *  org.jose4j.jwk.JsonWebKey
 *  org.jose4j.jwk.JsonWebKeySet
 *  org.jose4j.jwk.RsaJsonWebKey
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.DisposableBean
 */
package org.apereo.cas.util.cipher;

import java.io.File;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.Generated;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.util.cipher.BaseStringCipherExecutor;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.io.FileWatcherService;
import org.jooq.lambda.Unchecked;
import org.jooq.lambda.fi.util.function.CheckedConsumer;
import org.jose4j.jwk.HttpsJwks;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jwk.RsaJsonWebKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

public class JsonWebKeySetStringCipherExecutor
extends BaseStringCipherExecutor
implements AutoCloseable,
DisposableBean {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonWebKeySetStringCipherExecutor.class);
    private final FileWatcherService keystorePatchWatcherService;
    private final Optional<String> keyIdToUse;
    private final Optional<HttpsJwks> httpsJkws;
    private JsonWebKeySet webKeySet;

    public JsonWebKeySetStringCipherExecutor(File jwksKeystore) {
        this(jwksKeystore, Optional.empty());
    }

    public JsonWebKeySetStringCipherExecutor(File jwksKeystore, String httpsJwksEndpointUrl) {
        this(jwksKeystore, Optional.empty(), httpsJwksEndpointUrl);
    }

    public JsonWebKeySetStringCipherExecutor(File jwksKeystore, Optional<String> keyId) {
        this(jwksKeystore, keyId, null);
    }

    public JsonWebKeySetStringCipherExecutor(File jwksKeystore, Optional<String> keyId, String httpsJwksEndpointUrl) {
        String json = (String)FunctionUtils.doUnchecked(() -> FileUtils.readFileToString((File)jwksKeystore, (Charset)StandardCharsets.UTF_8));
        this.keystorePatchWatcherService = new FileWatcherService(jwksKeystore, Unchecked.consumer(file -> {
            String reloadedJson = FileUtils.readFileToString((File)jwksKeystore, (Charset)StandardCharsets.UTF_8);
            this.webKeySet = new JsonWebKeySet(reloadedJson);
        }));
        this.webKeySet = (JsonWebKeySet)FunctionUtils.doUnchecked(() -> new JsonWebKeySet(json));
        this.keyIdToUse = keyId;
        this.httpsJkws = StringUtils.isNotBlank((CharSequence)httpsJwksEndpointUrl) ? Optional.of(new HttpsJwks(httpsJwksEndpointUrl)) : Optional.empty();
        this.keystorePatchWatcherService.start(this.getClass().getSimpleName());
        LOGGER.debug("Started JWKS watcher thread");
    }

    private static Optional<RsaJsonWebKey> findRsaJsonWebKey(List<JsonWebKey> keys, Predicate<JsonWebKey> filter) {
        return keys.stream().filter(key -> key instanceof RsaJsonWebKey && filter.test((JsonWebKey)key)).map(RsaJsonWebKey.class::cast).findFirst();
    }

    @Override
    public void close() {
        if (this.keystorePatchWatcherService != null) {
            this.keystorePatchWatcherService.close();
        }
    }

    public void destroy() {
        this.close();
    }

    @Override
    public String encode(Serializable value, Object[] parameters) {
        this.configureSigningParametersForEncoding();
        this.configureEncryptionParametersForEncoding();
        return super.encode(value, parameters);
    }

    @Override
    public String decode(Serializable value, Object[] parameters) {
        this.configureSigningParametersForDecoding();
        this.configureEncryptionParametersForDecoding();
        return super.decode(value, parameters);
    }

    private void configureSigningParametersForDecoding() {
        Optional<RsaJsonWebKey> result = this.findRsaJsonWebKeyByProvidedKeyId(this.webKeySet.getJsonWebKeys());
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Could not locate RSA JSON web key from keystore");
        }
        RsaJsonWebKey key = result.get();
        if (key.getPublicKey() == null) {
            throw new IllegalArgumentException("Public key located from keystore for key id " + key.getKeyId() + " is undefined");
        }
        this.setSigningKey(key.getPublicKey());
    }

    private void configureEncryptionParametersForDecoding() {
        FunctionUtils.doUnchecked((CheckedConsumer<Object>)((CheckedConsumer)param -> {
            if (this.httpsJkws.isEmpty()) {
                LOGGER.debug("No JWKS endpoint is defined. Configuration of encryption parameters and keys are skipped");
            } else {
                List keys = this.httpsJkws.get().getJsonWebKeys();
                Optional<RsaJsonWebKey> encKeyResult = JsonWebKeySetStringCipherExecutor.findRsaJsonWebKey(keys, jsonWebKey -> true);
                if (encKeyResult.isEmpty()) {
                    throw new IllegalArgumentException("Could not locate RSA JSON web key from endpoint");
                }
                RsaJsonWebKey encKey = encKeyResult.get();
                if (encKey.getPrivateKey() == null) {
                    throw new IllegalArgumentException("Private key located from endpoint for key id " + encKey.getKeyId() + " is undefined");
                }
                this.setEncryptionKey(encKey.getPrivateKey());
                this.setContentEncryptionAlgorithmIdentifier("A128CBC-HS256");
                this.setEncryptionAlgorithm("RSA-OAEP-256");
            }
        }), new Object[0]);
    }

    private void configureEncryptionParametersForEncoding() {
        FunctionUtils.doUnchecked((CheckedConsumer<Object>)((CheckedConsumer)param -> {
            if (this.httpsJkws.isEmpty()) {
                LOGGER.debug("No JWKS endpoint is defined. Configuration of encryption parameters and keys are skipped");
            } else {
                List keys = this.httpsJkws.get().getJsonWebKeys();
                Optional<RsaJsonWebKey> encKeyResult = JsonWebKeySetStringCipherExecutor.findRsaJsonWebKey(keys, jsonWebKey -> true);
                if (encKeyResult.isEmpty()) {
                    throw new IllegalArgumentException("Could not locate RSA JSON web key from endpoint");
                }
                RsaJsonWebKey encKey = encKeyResult.get();
                if (encKey.getPublicKey() == null) {
                    throw new IllegalArgumentException("Public key from endpoint for key id " + encKey.getKeyId() + " is undefined");
                }
                this.setEncryptionKey(encKey.getPublicKey());
                this.setContentEncryptionAlgorithmIdentifier("A128CBC-HS256");
                this.setEncryptionAlgorithm("RSA-OAEP-256");
            }
        }), new Object[0]);
    }

    private void configureSigningParametersForEncoding() {
        Optional<RsaJsonWebKey> result = this.findRsaJsonWebKeyByProvidedKeyId(this.webKeySet.getJsonWebKeys());
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Could not locate RSA JSON web key from keystore");
        }
        RsaJsonWebKey key = result.get();
        if (key.getPrivateKey() == null) {
            throw new IllegalArgumentException("Private key located from keystore for key id " + key.getKeyId() + " is undefined");
        }
        this.setSigningKey(key.getPrivateKey());
    }

    private Optional<RsaJsonWebKey> findRsaJsonWebKeyByProvidedKeyId(List<JsonWebKey> keys) {
        Predicate predicate = this.keyIdToUse.map(s -> jsonWebKey -> jsonWebKey.getKeyId().equalsIgnoreCase((String)s)).orElseGet(() -> jsonWebKey -> true);
        return JsonWebKeySetStringCipherExecutor.findRsaJsonWebKey(keys, predicate);
    }

    @Generated
    public void setWebKeySet(JsonWebKeySet webKeySet) {
        this.webKeySet = webKeySet;
    }
}

