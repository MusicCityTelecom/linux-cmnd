/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import javax.inject.Singleton;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.message.internal.Utils;
import org.glassfish.jersey.spi.HeaderDelegateProvider;

@Singleton
public class StringHeaderProvider
implements HeaderDelegateProvider<String> {
    @Override
    public boolean supports(Class<?> type) {
        return type == String.class;
    }

    @Override
    public String toString(String header) {
        Utils.throwIllegalArgumentExceptionIfNull(header, LocalizationMessages.STRING_IS_NULL());
        return header;
    }

    @Override
    public String fromString(String header) {
        Utils.throwIllegalArgumentExceptionIfNull(header, LocalizationMessages.STRING_IS_NULL());
        return header;
    }
}

