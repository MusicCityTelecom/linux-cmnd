/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.services.RegisteredService
 *  org.apereo.cas.services.RegisteredServiceCipherExecutor
 *  org.apereo.cas.services.RegisteredServicePublicKey
 *  org.apereo.cas.util.EncodingUtils
 *  org.apereo.cas.util.LoggingUtils
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.bouncycastle.jce.provider.BouncyCastleProvider
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.services;

import java.nio.charset.StandardCharsets;
import java.security.Provider;
import java.security.Security;
import java.util.Optional;
import javax.crypto.Cipher;
import lombok.Generated;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.services.RegisteredServiceCipherExecutor;
import org.apereo.cas.services.RegisteredServicePublicKey;
import org.apereo.cas.util.EncodingUtils;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegisteredServicePublicKeyCipherExecutor
implements RegisteredServiceCipherExecutor {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(RegisteredServicePublicKeyCipherExecutor.class);

    public String encode(String data, Optional<RegisteredService> service) {
        try {
            RegisteredService registeredService;
            byte[] result;
            if (service.isPresent() && (result = RegisteredServicePublicKeyCipherExecutor.encodeInternal(data, registeredService = service.get())) != null) {
                return EncodingUtils.encodeBase64((byte[])result);
            }
        }
        catch (Exception e) {
            LoggingUtils.warn((Logger)LOGGER, (Throwable)e);
        }
        return null;
    }

    public String decode(String data, Optional<RegisteredService> service) {
        LOGGER.warn("Operation is not supported by this cipher");
        return null;
    }

    protected static byte[] encodeInternal(String data, RegisteredService registeredService) {
        RegisteredServicePublicKey publicKey = registeredService.getPublicKey();
        if (publicKey == null) {
            LOGGER.error("No public key is defined for service [{}]. No attributes will be released", (Object)registeredService);
            return null;
        }
        LOGGER.debug("Using service [{}] public key [{}] to initialize the cipher", (Object)registeredService.getServiceId(), (Object)publicKey);
        Cipher cipher = publicKey.toCipher();
        if (cipher != null) {
            LOGGER.trace("Initialized cipher successfully. Proceeding to finalize...");
            return (byte[])FunctionUtils.doUnchecked(() -> cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        }
        return null;
    }

    static {
        Security.addProvider((Provider)new BouncyCastleProvider());
    }
}

