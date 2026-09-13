/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.util.scripting.WatchableGroovyScriptResource
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.context.ApplicationContext
 *  org.springframework.core.io.Resource
 *  org.springframework.security.crypto.password.AbstractPasswordEncoder
 */
package org.apereo.cas.authentication.support.password;

import lombok.Generated;
import org.apereo.cas.util.scripting.WatchableGroovyScriptResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.AbstractPasswordEncoder;

public class GroovyPasswordEncoder
extends AbstractPasswordEncoder
implements DisposableBean {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyPasswordEncoder.class);
    private final WatchableGroovyScriptResource watchableScript;
    private final ApplicationContext applicationContext;

    public GroovyPasswordEncoder(Resource groovyScript, ApplicationContext applicationContext) {
        this.watchableScript = new WatchableGroovyScriptResource(groovyScript);
        this.applicationContext = applicationContext;
    }

    protected byte[] encode(CharSequence rawPassword, byte[] salt) {
        Object[] args = new Object[]{rawPassword, salt, LOGGER, this.applicationContext};
        return (byte[])this.watchableScript.execute(args, byte[].class);
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        Object[] args = new Object[]{rawPassword, encodedPassword, LOGGER, this.applicationContext};
        return (Boolean)this.watchableScript.execute("matches", Boolean.class, args);
    }

    public void destroy() {
        this.watchableScript.close();
    }

    @Generated
    public GroovyPasswordEncoder(WatchableGroovyScriptResource watchableScript, ApplicationContext applicationContext) {
        this.watchableScript = watchableScript;
        this.applicationContext = applicationContext;
    }
}

