/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import javax.inject.Singleton;
import javax.ws.rs.core.Cookie;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.message.internal.HttpHeaderReader;
import org.glassfish.jersey.message.internal.StringBuilderUtils;
import org.glassfish.jersey.message.internal.Utils;
import org.glassfish.jersey.spi.HeaderDelegateProvider;

@Singleton
public class CookieProvider
implements HeaderDelegateProvider<Cookie> {
    @Override
    public boolean supports(Class<?> type) {
        return type == Cookie.class;
    }

    @Override
    public String toString(Cookie cookie) {
        Utils.throwIllegalArgumentExceptionIfNull(cookie, LocalizationMessages.COOKIE_IS_NULL());
        StringBuilder b = new StringBuilder();
        b.append("$Version=").append(cookie.getVersion()).append(';');
        b.append(cookie.getName()).append('=');
        StringBuilderUtils.appendQuotedIfWhitespace(b, cookie.getValue());
        if (cookie.getDomain() != null) {
            b.append(";$Domain=");
            StringBuilderUtils.appendQuotedIfWhitespace(b, cookie.getDomain());
        }
        if (cookie.getPath() != null) {
            b.append(";$Path=");
            StringBuilderUtils.appendQuotedIfWhitespace(b, cookie.getPath());
        }
        return b.toString();
    }

    @Override
    public Cookie fromString(String header) {
        Utils.throwIllegalArgumentExceptionIfNull(header, LocalizationMessages.COOKIE_IS_NULL());
        return HttpHeaderReader.readCookie(header);
    }
}

