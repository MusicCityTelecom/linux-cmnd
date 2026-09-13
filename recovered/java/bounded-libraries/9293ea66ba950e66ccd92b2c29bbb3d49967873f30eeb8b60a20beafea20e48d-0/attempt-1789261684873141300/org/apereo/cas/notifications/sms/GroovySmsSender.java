/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.util.scripting.WatchableGroovyScriptResource
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.notifications.sms;

import lombok.Generated;
import org.apereo.cas.notifications.sms.SmsSender;
import org.apereo.cas.util.scripting.WatchableGroovyScriptResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.core.io.Resource;

public class GroovySmsSender
implements SmsSender,
DisposableBean {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(GroovySmsSender.class);
    private final transient WatchableGroovyScriptResource watchableScript;

    public GroovySmsSender(Resource groovyResource) {
        this.watchableScript = new WatchableGroovyScriptResource(groovyResource);
    }

    @Override
    public boolean send(String from, String to, String message) {
        return (Boolean)this.watchableScript.execute(new Object[]{from, to, message, LOGGER}, Boolean.class);
    }

    public void destroy() {
        this.watchableScript.close();
    }
}

