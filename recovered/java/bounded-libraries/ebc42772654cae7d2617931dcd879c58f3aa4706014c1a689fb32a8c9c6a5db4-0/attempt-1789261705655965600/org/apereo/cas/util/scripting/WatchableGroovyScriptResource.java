/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.GroovyObject
 *  lombok.Generated
 *  org.jooq.lambda.Unchecked
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.util.scripting;

import groovy.lang.GroovyObject;
import lombok.Generated;
import org.apereo.cas.util.ResourceUtils;
import org.apereo.cas.util.function.FunctionUtils;
import org.apereo.cas.util.io.FileWatcherService;
import org.apereo.cas.util.scripting.ExecutableCompiledGroovyScript;
import org.apereo.cas.util.scripting.ScriptingUtils;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

public class WatchableGroovyScriptResource
implements ExecutableCompiledGroovyScript {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(WatchableGroovyScriptResource.class);
    private final transient Resource resource;
    private transient FileWatcherService watcherService;
    private transient GroovyObject groovyScript;

    public WatchableGroovyScriptResource(Resource script, boolean enableWatcher) {
        this.resource = script;
        if (ResourceUtils.doesResourceExist(script)) {
            if (ResourceUtils.isFile(script) && enableWatcher) {
                this.watcherService = (FileWatcherService)FunctionUtils.doUnchecked(() -> new FileWatcherService(script.getFile(), Unchecked.consumer(file -> {
                    LOGGER.info("Reloading script at [{}]", file);
                    this.compileScriptResource(script);
                })));
                this.watcherService.start(script.getFilename());
            }
            this.compileScriptResource(script);
        }
    }

    public WatchableGroovyScriptResource(Resource script) {
        this(script, true);
    }

    @Override
    public <T> T execute(Object[] args, Class<T> clazz) {
        return this.execute(args, clazz, true);
    }

    @Override
    public void execute(Object[] args) {
        this.execute(args, Void.class, true);
    }

    @Override
    public <T> T execute(Object[] args, Class<T> clazz, boolean failOnError) {
        return this.groovyScript != null ? (T)ScriptingUtils.executeGroovyScript(this.groovyScript, args, clazz, failOnError) : null;
    }

    @Override
    public <T> T execute(String methodName, Class<T> clazz, Object ... args) {
        return this.execute(methodName, clazz, true, args);
    }

    public <T> T execute(String methodName, Class<T> clazz, boolean failOnError, Object ... args) {
        return this.groovyScript != null ? (T)ScriptingUtils.executeGroovyScript(this.groovyScript, methodName, args, clazz, failOnError) : null;
    }

    @Override
    public void close() {
        if (this.watcherService != null) {
            LOGGER.trace("Shutting down watcher service for [{}]", (Object)this.resource);
            this.watcherService.close();
        }
    }

    private void compileScriptResource(Resource script) {
        this.groovyScript = ScriptingUtils.parseGroovyScript(script, true);
    }

    @Generated
    public Resource getResource() {
        return this.resource;
    }

    @Generated
    public FileWatcherService getWatcherService() {
        return this.watcherService;
    }

    @Generated
    public GroovyObject getGroovyScript() {
        return this.groovyScript;
    }

    @Generated
    public String toString() {
        return "WatchableGroovyScriptResource(resource=" + this.resource + ")";
    }
}

