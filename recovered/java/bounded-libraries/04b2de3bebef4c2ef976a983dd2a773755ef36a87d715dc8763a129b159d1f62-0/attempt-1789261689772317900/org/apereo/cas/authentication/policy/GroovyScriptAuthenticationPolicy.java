/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  javax.persistence.Transient
 *  lombok.Generated
 *  org.apereo.cas.authentication.Authentication
 *  org.apereo.cas.authentication.AuthenticationHandler
 *  org.apereo.cas.authentication.AuthenticationPolicyExecutionResult
 *  org.apereo.cas.util.CollectionUtils
 *  org.apereo.cas.util.ResourceUtils
 *  org.apereo.cas.util.scripting.ScriptingUtils
 *  org.apereo.cas.util.scripting.WatchableGroovyScriptResource
 *  org.jooq.lambda.Unchecked
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.context.ConfigurableApplicationContext
 *  org.springframework.core.io.AbstractResource
 *  org.springframework.core.io.Resource
 *  org.springframework.data.annotation.Transient
 */
package org.apereo.cas.authentication.policy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import javax.persistence.Transient;
import lombok.Generated;
import org.apereo.cas.authentication.Authentication;
import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.AuthenticationPolicyExecutionResult;
import org.apereo.cas.authentication.policy.BaseAuthenticationPolicy;
import org.apereo.cas.util.CollectionUtils;
import org.apereo.cas.util.ResourceUtils;
import org.apereo.cas.util.scripting.ScriptingUtils;
import org.apereo.cas.util.scripting.WatchableGroovyScriptResource;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
public class GroovyScriptAuthenticationPolicy
extends BaseAuthenticationPolicy {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyScriptAuthenticationPolicy.class);
    private static final long serialVersionUID = 6948477763790549040L;
    private String script;
    @JsonIgnore
    @Transient
    @org.springframework.data.annotation.Transient
    private transient WatchableGroovyScriptResource executableScript;

    public GroovyScriptAuthenticationPolicy(String script) {
        this.script = script;
    }

    public AuthenticationPolicyExecutionResult isSatisfiedBy(Authentication auth, Set<AuthenticationHandler> authenticationHandlers, ConfigurableApplicationContext applicationContext, Optional<Serializable> assertion) throws Exception {
        this.initializeWatchableScriptIfNeeded();
        Optional<Exception> ex = this.getScriptExecutionResult(auth);
        if (ex != null && ex.isPresent()) {
            throw new GeneralSecurityException(ex.get());
        }
        return AuthenticationPolicyExecutionResult.success();
    }

    public boolean shouldResumeOnFailure(Throwable failure) {
        return (Boolean)Unchecked.supplier(() -> {
            this.initializeWatchableScriptIfNeeded();
            Map args = CollectionUtils.wrap((String)"failure", (Object)failure, (String)"logger", (Object)LOGGER);
            this.executableScript.setBinding(args);
            return (Boolean)this.executableScript.execute("shouldResumeOnFailure", Boolean.class, args.values().toArray());
        }).get();
    }

    private void initializeWatchableScriptIfNeeded() throws Exception {
        if (this.executableScript == null) {
            Matcher matcherFile = ScriptingUtils.getMatcherForExternalGroovyScript((String)this.script);
            if (!matcherFile.find()) {
                throw new IllegalArgumentException("Unable to locate groovy script file at " + this.script);
            }
            AbstractResource resource = ResourceUtils.getRawResourceFrom((String)this.script);
            this.executableScript = new WatchableGroovyScriptResource((Resource)resource);
        }
    }

    private Optional<Exception> getScriptExecutionResult(Authentication auth) {
        Map args = CollectionUtils.wrap((String)"principal", (Object)auth.getPrincipal(), (String)"logger", (Object)LOGGER);
        this.executableScript.setBinding(args);
        return (Optional)this.executableScript.execute(args.values().toArray(), Optional.class);
    }

    @Generated
    public GroovyScriptAuthenticationPolicy() {
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GroovyScriptAuthenticationPolicy)) {
            return false;
        }
        GroovyScriptAuthenticationPolicy other = (GroovyScriptAuthenticationPolicy)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        String this$script = this.script;
        String other$script = other.script;
        return !(this$script == null ? other$script != null : !this$script.equals(other$script));
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof GroovyScriptAuthenticationPolicy;
    }

    @Override
    @Generated
    public int hashCode() {
        int PRIME = 59;
        int result = super.hashCode();
        String $script = this.script;
        result = result * 59 + ($script == null ? 43 : $script.hashCode());
        return result;
    }

    @Generated
    public void setScript(String script) {
        this.script = script;
    }

    @JsonIgnore
    @Generated
    public void setExecutableScript(WatchableGroovyScriptResource executableScript) {
        this.executableScript = executableScript;
    }

    @Generated
    public String getScript() {
        return this.script;
    }

    @Generated
    public WatchableGroovyScriptResource getExecutableScript() {
        return this.executableScript;
    }

    @Generated
    public GroovyScriptAuthenticationPolicy(String script, WatchableGroovyScriptResource executableScript) {
        this.script = script;
        this.executableScript = executableScript;
    }
}

