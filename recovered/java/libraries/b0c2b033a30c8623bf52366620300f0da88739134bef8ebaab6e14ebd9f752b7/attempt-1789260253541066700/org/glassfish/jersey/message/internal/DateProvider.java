/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.text.ParseException;
import java.util.Date;
import javax.inject.Singleton;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.message.internal.HttpDateFormat;
import org.glassfish.jersey.message.internal.HttpHeaderReader;
import org.glassfish.jersey.message.internal.Utils;
import org.glassfish.jersey.spi.HeaderDelegateProvider;

@Singleton
public class DateProvider
implements HeaderDelegateProvider<Date> {
    @Override
    public boolean supports(Class<?> type) {
        return Date.class.isAssignableFrom(type);
    }

    @Override
    public String toString(Date header) {
        Utils.throwIllegalArgumentExceptionIfNull(header, LocalizationMessages.DATE_IS_NULL());
        return HttpDateFormat.getPreferredDateFormat().format(header);
    }

    @Override
    public Date fromString(String header) {
        Utils.throwIllegalArgumentExceptionIfNull(header, LocalizationMessages.DATE_IS_NULL());
        try {
            return HttpHeaderReader.readDate(header);
        }
        catch (ParseException ex) {
            throw new IllegalArgumentException("Error parsing date '" + header + "'", ex);
        }
    }
}

