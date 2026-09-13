/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.configuration.model.core.util.EncryptionRandomizedSigningJwtCryptographyProperties
 *  org.apereo.cas.util.cipher.DefaultTicketCipherExecutor
 *  org.apereo.cas.util.crypto.CipherExecutor
 *  org.apereo.cas.util.function.FunctionUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.util;

import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.configuration.model.core.util.EncryptionRandomizedSigningJwtCryptographyProperties;
import org.apereo.cas.util.cipher.DefaultTicketCipherExecutor;
import org.apereo.cas.util.crypto.CipherExecutor;
import org.apereo.cas.util.function.FunctionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CoreTicketUtils {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(CoreTicketUtils.class);

    public static CipherExecutor newTicketRegistryCipherExecutor(EncryptionRandomizedSigningJwtCryptographyProperties registry, String registryName) {
        return CoreTicketUtils.newTicketRegistryCipherExecutor(registry, false, registryName);
    }

    public static CipherExecutor newTicketRegistryCipherExecutor(EncryptionRandomizedSigningJwtCryptographyProperties registry, boolean forceIfBlankKeys, String registryName) {
        boolean bl = !registry.isEnabled() && StringUtils.isNotBlank((CharSequence)registry.getEncryption().getKey()) && StringUtils.isNotBlank((CharSequence)registry.getSigning().getKey());
        Boolean enabled = (Boolean)FunctionUtils.doIf((boolean)bl, () -> {
            LOGGER.warn("Ticket registry encryption/signing for [{}] is not enabled explicitly in the configuration, yet signing/encryption keys are defined for ticket operations. CAS will proceed to enable the ticket registry encryption/signing functionality. If you intend to turn off this behavior, consider removing/disabling the signing/encryption keys defined in settings", (Object)registryName);
            LOGGER.debug("Defined signing key is [{}], and defined encryption key is [{}]", (Object)registry.getSigning().getKey(), (Object)registry.getEncryption().getKey());
            return Boolean.TRUE;
        }, () -> ((EncryptionRandomizedSigningJwtCryptographyProperties)registry).isEnabled()).get();
        if (enabled.booleanValue() || forceIfBlankKeys) {
            LOGGER.debug("Ticket registry encryption/signing is enabled for [{}]", (Object)registryName);
            return new DefaultTicketCipherExecutor(registry.getEncryption().getKey(), registry.getSigning().getKey(), registry.getAlg(), registry.getSigning().getKeySize(), registry.getEncryption().getKeySize(), registryName);
        }
        LOGGER.info("Ticket registry encryption/signing is turned off. This MAY NOT be safe in a clustered production environment. Consider using other choices to handle encryption, signing and verification of ticket registry tickets, and verify the chosen ticket registry does support this behavior.");
        return CipherExecutor.noOp();
    }

    @Generated
    private CoreTicketUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

