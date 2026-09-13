/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationHandler
 *  org.apereo.cas.authentication.AuthenticationHandlerResolver
 *  org.apereo.cas.authentication.AuthenticationTransaction
 *  org.apereo.cas.services.ServicesManager
 *  org.apereo.cas.util.scripting.WatchableGroovyScriptResource
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.authentication.handler;

import java.util.Set;
import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationHandlerResolver;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.services.ServicesManager;
import org.apereo.cas.util.scripting.WatchableGroovyScriptResource;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.core.io.Resource;

public class GroovyAuthenticationHandlerResolver
implements AuthenticationHandlerResolver,
DisposableBean {
    private final WatchableGroovyScriptResource watchableScript;
    private final ServicesManager servicesManager;
    private int order;

    public GroovyAuthenticationHandlerResolver(Resource groovyResource, ServicesManager servicesManager) {
        this(groovyResource, servicesManager, Integer.MAX_VALUE);
    }

    public GroovyAuthenticationHandlerResolver(Resource groovyResource, ServicesManager servicesManager, int order) {
        this.order = order;
        this.servicesManager = servicesManager;
        this.watchableScript = new WatchableGroovyScriptResource(groovyResource);
    }

    public Set<AuthenticationHandler> resolve(Set<AuthenticationHandler> candidateHandlers, AuthenticationTransaction transaction) {
        Object[] args = new Object[]{candidateHandlers, transaction, this.servicesManager, LOGGER};
        return (Set)this.watchableScript.execute(args, Set.class);
    }

    public boolean supports(Set<AuthenticationHandler> handlers, AuthenticationTransaction transaction) {
        Object[] args = new Object[]{handlers, transaction, this.servicesManager, LOGGER};
        return (Boolean)this.watchableScript.execute("supports", Boolean.class, args);
    }

    public void destroy() {
        this.watchableScript.close();
    }

    @Generated
    public WatchableGroovyScriptResource getWatchableScript() {
        return this.watchableScript;
    }

    @Generated
    public ServicesManager getServicesManager() {
        return this.servicesManager;
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

