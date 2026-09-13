/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.Service
 *  org.apereo.cas.authentication.principal.ServiceFactory
 */
package org.apereo.cas.authentication.principal;

import java.util.Map;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import lombok.Generated;
import org.apereo.cas.authentication.principal.Service;
import org.apereo.cas.authentication.principal.ServiceFactory;

public abstract class AbstractServiceFactory<T extends Service>
implements ServiceFactory<T> {
    private int order = Integer.MAX_VALUE;

    protected static String cleanupUrl(String url) {
        if (url == null) {
            return null;
        }
        int jsessionPosition = url.indexOf(";jsession");
        if (jsessionPosition == -1) {
            return url;
        }
        int questionMarkPosition = url.indexOf(63);
        if (questionMarkPosition < jsessionPosition) {
            return url.substring(0, url.indexOf(";jsession"));
        }
        return url.substring(0, jsessionPosition) + url.substring(questionMarkPosition);
    }

    protected static String getSourceParameter(HttpServletRequest request, String ... paramNames) {
        if (request != null) {
            Map parameterMap = request.getParameterMap();
            return Stream.of(paramNames).filter(p -> parameterMap.containsKey(p) || request.getAttribute(p) != null).findFirst().orElse(null);
        }
        return null;
    }

    public <T extends Service> T createService(String id, Class<T> clazz) {
        Service service = this.createService(id);
        if (!clazz.isAssignableFrom(service.getClass())) {
            throw new ClassCastException("Service [" + service.getId() + " is of type " + service.getClass() + " when we were expecting " + clazz);
        }
        return (T)service;
    }

    public <T extends Service> T createService(HttpServletRequest request, Class<T> clazz) {
        Service service = this.createService(request);
        if (!clazz.isAssignableFrom(service.getClass())) {
            throw new ClassCastException("Service [" + service.getId() + " is of type " + service.getClass() + " when we were expecting " + clazz);
        }
        return (T)service;
    }

    @Generated
    public String toString() {
        return "AbstractServiceFactory(order=" + this.order + ")";
    }

    @Generated
    public int getOrder() {
        return this.order;
    }

    @Generated
    public void setOrder(int order) {
        this.order = order;
    }
}

