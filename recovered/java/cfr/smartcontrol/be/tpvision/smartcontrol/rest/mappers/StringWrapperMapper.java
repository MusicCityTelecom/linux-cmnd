/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.mappers;

import be.tpvision.smartcontrol.domain.device_settings.StringWrapper;

public class StringWrapperMapper {
    private StringWrapperMapper() {
    }

    public static String toString(StringWrapper stringWrapper) {
        return stringWrapper != null ? stringWrapper.getValue() : null;
    }

    public static StringWrapper toStringWrapper(String string) {
        return string != null ? new StringWrapper(string) : null;
    }
}

