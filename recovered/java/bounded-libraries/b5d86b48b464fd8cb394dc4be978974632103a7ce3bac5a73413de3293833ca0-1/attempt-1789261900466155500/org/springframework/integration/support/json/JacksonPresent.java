/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.util.ClassUtils
 */
package org.springframework.integration.support.json;

import org.springframework.util.ClassUtils;

public final class JacksonPresent {
    private static final boolean JACKSON_2_PRESENT = ClassUtils.isPresent((String)"com.fasterxml.jackson.databind.ObjectMapper", null) && ClassUtils.isPresent((String)"com.fasterxml.jackson.core.JsonGenerator", null);

    public static boolean isJackson2Present() {
        return JACKSON_2_PRESENT;
    }

    private JacksonPresent() {
    }
}

