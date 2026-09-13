/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Binding
 *  groovy.lang.Script
 *  lombok.Generated
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.util.scripting;

import groovy.lang.Binding;
import groovy.lang.Script;
import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apereo.cas.util.scripting.ExecutableCompiledGroovyScript;
import org.apereo.cas.util.scripting.ScriptingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GroovyShellScript
implements ExecutableCompiledGroovyScript {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyShellScript.class);
    private static final ThreadLocal<Map<String, Object>> BINDING_THREAD_LOCAL = new ThreadLocal();
    private final Script groovyScript;
    private final String script;

    public GroovyShellScript(String script) {
        this.script = script;
        this.groovyScript = ScriptingUtils.parseGroovyShellScript(script);
    }

    @Override
    public <T> T execute(Object[] args, Class<T> clazz) {
        return this.execute(args, clazz, true);
    }

    @Override
    public void execute(Object[] args) {
        this.execute(args, Void.class, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public synchronized <T> T execute(Object[] args, Class<T> clazz, boolean failOnError) {
        try {
            LOGGER.trace("Beginning to execute script [{}]", (Object)this);
            Map<String, Object> binding = BINDING_THREAD_LOCAL.get();
            if (this.groovyScript != null) {
                if (binding != null && !binding.isEmpty()) {
                    LOGGER.trace("Setting binding [{}]", binding);
                    this.groovyScript.setBinding(new Binding(binding));
                }
                LOGGER.trace("Current binding [{}]", (Object)this.groovyScript.getBinding());
                T result = ScriptingUtils.executeGroovyShellScript(this.groovyScript, clazz);
                LOGGER.debug("Groovy script [{}] returns result [{}]", (Object)this, result);
                T t = result;
                return t;
            }
            T t = null;
            return t;
        }
        finally {
            BINDING_THREAD_LOCAL.remove();
            if (this.groovyScript != null) {
                this.groovyScript.setBinding(new Binding(Map.of()));
            }
            LOGGER.trace("Completed script execution [{}]", (Object)this);
        }
    }

    @Override
    public <T> T execute(String methodName, Class<T> clazz, Object ... args) {
        return this.execute(args, clazz);
    }

    @Override
    public void setBinding(Map<String, Object> args) {
        BINDING_THREAD_LOCAL.set(new HashMap<String, Object>(args));
    }

    public String toString() {
        return new ToStringBuilder((Object)this).append("script", (Object)this.script).toString();
    }

    @Generated
    public Script getGroovyScript() {
        return this.groovyScript;
    }

    @Generated
    public String getScript() {
        return this.script;
    }
}

