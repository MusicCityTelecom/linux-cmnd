/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationException
 *  org.apereo.cas.authentication.AuthenticationPreProcessor
 *  org.apereo.cas.authentication.AuthenticationTransaction
 *  org.apereo.cas.authentication.Credential
 *  org.apereo.cas.util.scripting.WatchableGroovyScriptResource
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.authentication;

import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationException;
import org.apereo.cas.authentication.AuthenticationPreProcessor;
import org.apereo.cas.authentication.AuthenticationTransaction;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.util.scripting.WatchableGroovyScriptResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.core.io.Resource;

public class GroovyAuthenticationPreProcessor
implements AuthenticationPreProcessor,
DisposableBean {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyAuthenticationPreProcessor.class);
    private final transient WatchableGroovyScriptResource watchableScript;
    private int order;

    public GroovyAuthenticationPreProcessor(Resource groovyResource) {
        this.watchableScript = new WatchableGroovyScriptResource(groovyResource);
    }

    public boolean process(AuthenticationTransaction transaction) throws AuthenticationException {
        Object[] args = new Object[]{transaction, LOGGER};
        return (Boolean)this.watchableScript.execute(args, Boolean.class);
    }

    public boolean supports(Credential credential) {
        Object[] args = new Object[]{credential, LOGGER};
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
    public int getOrder() {
        return this.order;
    }

    @Generated
    public void setOrder(int order) {
        this.order = order;
    }
}

