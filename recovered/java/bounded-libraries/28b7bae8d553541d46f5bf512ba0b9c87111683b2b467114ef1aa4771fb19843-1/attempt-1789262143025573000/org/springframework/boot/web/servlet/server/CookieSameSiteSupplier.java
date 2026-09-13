/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.Cookie
 *  org.springframework.util.Assert
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.boot.web.servlet.server;

import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import javax.servlet.http.Cookie;
import org.springframework.boot.web.server.Cookie;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

@FunctionalInterface
public interface CookieSameSiteSupplier {
    public Cookie.SameSite getSameSite(Cookie var1);

    default public CookieSameSiteSupplier whenHasName(String name) {
        Assert.hasText((String)name, (String)"Name must not be empty");
        return this.when(cookie -> ObjectUtils.nullSafeEquals((Object)cookie.getName(), (Object)name));
    }

    default public CookieSameSiteSupplier whenHasName(Supplier<String> nameSupplier) {
        Assert.notNull(nameSupplier, (String)"NameSupplier must not be empty");
        return this.when(cookie -> ObjectUtils.nullSafeEquals((Object)cookie.getName(), nameSupplier.get()));
    }

    default public CookieSameSiteSupplier whenHasNameMatching(String regex) {
        Assert.hasText((String)regex, (String)"Regex must not be empty");
        return this.whenHasNameMatching(Pattern.compile(regex));
    }

    default public CookieSameSiteSupplier whenHasNameMatching(Pattern pattern) {
        Assert.notNull((Object)pattern, (String)"Pattern must not be null");
        return this.when(cookie -> pattern.matcher(cookie.getName()).matches());
    }

    default public CookieSameSiteSupplier when(Predicate<Cookie> predicate) {
        Assert.notNull(predicate, (String)"Predicate must not be null");
        return cookie -> predicate.test(cookie) ? this.getSameSite(cookie) : null;
    }

    public static CookieSameSiteSupplier ofNone() {
        return CookieSameSiteSupplier.of(Cookie.SameSite.NONE);
    }

    public static CookieSameSiteSupplier ofLax() {
        return CookieSameSiteSupplier.of(Cookie.SameSite.LAX);
    }

    public static CookieSameSiteSupplier ofStrict() {
        return CookieSameSiteSupplier.of(Cookie.SameSite.STRICT);
    }

    public static CookieSameSiteSupplier of(Cookie.SameSite sameSite) {
        Assert.notNull((Object)((Object)sameSite), (String)"SameSite must not be null");
        return cookie -> sameSite;
    }
}

