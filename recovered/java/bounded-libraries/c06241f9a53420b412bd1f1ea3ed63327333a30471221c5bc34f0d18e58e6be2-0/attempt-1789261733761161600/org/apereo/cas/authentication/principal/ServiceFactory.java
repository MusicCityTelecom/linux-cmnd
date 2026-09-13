/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.core.Ordered
 */
package org.apereo.cas.authentication.principal;

import javax.servlet.http.HttpServletRequest;
import org.apereo.cas.authentication.principal.Service;
import org.springframework.core.Ordered;

public interface ServiceFactory<T extends Service>
extends Ordered {
    public T createService(HttpServletRequest var1);

    public T createService(String var1);

    public <T extends Service> T createService(String var1, Class<T> var2);

    public <T extends Service> T createService(HttpServletRequest var1, Class<T> var2);
}

