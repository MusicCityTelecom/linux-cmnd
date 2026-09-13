/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.AuthenticationPasswordPolicyHandlingStrategy
 *  org.apereo.cas.authentication.MessageDescriptor
 *  org.apereo.cas.util.scripting.WatchableGroovyScriptResource
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ApplicationContext
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.authentication.support.password;

import java.util.List;
import lombok.Generated;
import org.apereo.cas.authentication.AuthenticationPasswordPolicyHandlingStrategy;
import org.apereo.cas.authentication.MessageDescriptor;
import org.apereo.cas.authentication.support.password.PasswordPolicyContext;
import org.apereo.cas.util.scripting.WatchableGroovyScriptResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

public class GroovyPasswordPolicyHandlingStrategy<AuthenticationResponse>
implements AuthenticationPasswordPolicyHandlingStrategy<AuthenticationResponse, PasswordPolicyContext> {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyPasswordPolicyHandlingStrategy.class);
    private final transient WatchableGroovyScriptResource watchableScript;
    private final transient ApplicationContext applicationContext;

    public GroovyPasswordPolicyHandlingStrategy(Resource groovyScript, ApplicationContext applicationContext) {
        this.watchableScript = new WatchableGroovyScriptResource(groovyScript);
        this.applicationContext = applicationContext;
    }

    public List<MessageDescriptor> handle(AuthenticationResponse response, PasswordPolicyContext configuration) {
        Object[] args = new Object[]{response, configuration, LOGGER, this.applicationContext};
        return (List)this.watchableScript.execute(args, List.class);
    }

    @Generated
    public GroovyPasswordPolicyHandlingStrategy(WatchableGroovyScriptResource watchableScript, ApplicationContext applicationContext) {
        this.watchableScript = watchableScript;
        this.applicationContext = applicationContext;
    }
}

