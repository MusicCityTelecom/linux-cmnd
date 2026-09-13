/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.security.core.context.SecurityContext
 *  org.springframework.util.function.SingletonSupplier
 */
package org.springframework.security.web.context;

import java.util.function.Supplier;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.util.function.SingletonSupplier;

public interface SecurityContextRepository {
    @Deprecated
    public SecurityContext loadContext(HttpRequestResponseHolder var1);

    default public Supplier<SecurityContext> loadContext(HttpServletRequest request) {
        return SingletonSupplier.of(() -> this.loadContext(new HttpRequestResponseHolder(request, null)));
    }

    public void saveContext(SecurityContext var1, HttpServletRequest var2, HttpServletResponse var3);

    public boolean containsContext(HttpServletRequest var1);
}

