/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.util.crypto;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FunctionalInterface
public interface DecodableCipher<I, O> {
    public static final Logger LOGGER = LoggerFactory.getLogger(DecodableCipher.class);

    public O decode(I var1, Object[] var2);

    default public O decode(I value) {
        return this.decode(value, ArrayUtils.EMPTY_OBJECT_ARRAY);
    }

    default public Map<String, Object> decode(Map<String, Object> properties, Object[] parameters) {
        HashMap<String, Object> decrypted = new HashMap<String, Object>();
        properties.forEach((key, value) -> {
            try {
                LOGGER.trace("Attempting to decode key [{}]", key);
                O result = this.decode(value, parameters);
                if (result != null) {
                    LOGGER.trace("Decrypted key [{}] successfully", key);
                    decrypted.put((String)key, result);
                }
            }
            catch (ClassCastException e) {
                LOGGER.debug("Value of key [{}], is not the correct type, not decrypting, but using value as-is.", key);
                decrypted.put((String)key, value);
            }
        });
        return decrypted;
    }
}

