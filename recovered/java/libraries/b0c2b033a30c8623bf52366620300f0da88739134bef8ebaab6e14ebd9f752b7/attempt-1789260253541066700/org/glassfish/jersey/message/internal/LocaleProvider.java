/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.text.ParseException;
import java.util.Locale;
import javax.inject.Singleton;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.message.internal.LanguageTag;
import org.glassfish.jersey.message.internal.Utils;
import org.glassfish.jersey.spi.HeaderDelegateProvider;

@Singleton
public class LocaleProvider
implements HeaderDelegateProvider<Locale> {
    @Override
    public boolean supports(Class<?> type) {
        return Locale.class.isAssignableFrom(type);
    }

    @Override
    public String toString(Locale header) {
        Utils.throwIllegalArgumentExceptionIfNull(header, LocalizationMessages.LOCALE_IS_NULL());
        if (header.getCountry().length() == 0) {
            return header.getLanguage();
        }
        return header.getLanguage() + '-' + header.getCountry();
    }

    @Override
    public Locale fromString(String header) {
        Utils.throwIllegalArgumentExceptionIfNull(header, LocalizationMessages.LOCALE_IS_NULL());
        try {
            LanguageTag lt = new LanguageTag(header);
            return lt.getAsLocale();
        }
        catch (ParseException ex) {
            throw new IllegalArgumentException("Error parsing date '" + header + "'", ex);
        }
    }
}

