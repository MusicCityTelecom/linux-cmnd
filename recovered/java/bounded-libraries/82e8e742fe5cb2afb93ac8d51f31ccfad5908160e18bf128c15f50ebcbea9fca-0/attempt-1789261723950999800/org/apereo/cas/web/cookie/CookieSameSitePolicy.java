/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.commons.lang3.ClassUtils
 *  org.jooq.lambda.Unchecked
 */
package org.apereo.cas.web.cookie;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ClassUtils;
import org.apereo.cas.web.cookie.CookieGenerationContext;
import org.jooq.lambda.Unchecked;

@FunctionalInterface
public interface CookieSameSitePolicy {
    public static CookieSameSitePolicy of(CookieGenerationContext context) {
        String option = context.getSameSitePolicy();
        if (option.contains(".")) {
            return (CookieSameSitePolicy)Unchecked.supplier(() -> {
                Class clazz = ClassUtils.getClass((ClassLoader)CookieSameSitePolicy.class.getClassLoader(), (String)option);
                return (CookieSameSitePolicy)clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            }).get();
        }
        switch (option.toLowerCase().trim()) {
            case "strict": {
                return CookieSameSitePolicy.strict();
            }
            case "lax": {
                return CookieSameSitePolicy.lax();
            }
            case "off": {
                return CookieSameSitePolicy.off();
            }
        }
        return CookieSameSitePolicy.none();
    }

    public static CookieSameSitePolicy none() {
        return (request, response) -> Optional.of("SameSite=None;");
    }

    public static CookieSameSitePolicy lax() {
        return (request, response) -> Optional.of("SameSite=Lax;");
    }

    public static CookieSameSitePolicy strict() {
        return (request, response) -> Optional.of("SameSite=Strict;");
    }

    public static CookieSameSitePolicy off() {
        return (request, response) -> Optional.empty();
    }

    public Optional<String> build(HttpServletRequest var1, HttpServletResponse var2);
}

