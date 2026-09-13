/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.context.ResourceLoaderAware
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.io.support.ResourcePatternResolver
 *  org.springframework.core.io.support.ResourcePatternUtils
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.boot.sql.init;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.sql.init.DatabaseInitializationMode;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.util.CollectionUtils;

public abstract class AbstractScriptDatabaseInitializer
implements ResourceLoaderAware,
InitializingBean {
    private static final String OPTIONAL_LOCATION_PREFIX = "optional:";
    private final DatabaseInitializationSettings settings;
    private volatile ResourceLoader resourceLoader;

    protected AbstractScriptDatabaseInitializer(DatabaseInitializationSettings settings) {
        this.settings = settings;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void afterPropertiesSet() throws Exception {
        this.initializeDatabase();
    }

    public boolean initializeDatabase() {
        ScriptLocationResolver locationResolver = new ScriptLocationResolver(this.resourceLoader);
        boolean initialized = this.applySchemaScripts(locationResolver);
        return this.applyDataScripts(locationResolver) || initialized;
    }

    private boolean isEnabled() {
        if (this.settings.getMode() == DatabaseInitializationMode.NEVER) {
            return false;
        }
        return this.settings.getMode() == DatabaseInitializationMode.ALWAYS || this.isEmbeddedDatabase();
    }

    protected boolean isEmbeddedDatabase() {
        throw new IllegalStateException("Database initialization mode is '" + (Object)((Object)this.settings.getMode()) + "' and database type is unknown");
    }

    private boolean applySchemaScripts(ScriptLocationResolver locationResolver) {
        return this.applyScripts(this.settings.getSchemaLocations(), "schema", locationResolver);
    }

    private boolean applyDataScripts(ScriptLocationResolver locationResolver) {
        return this.applyScripts(this.settings.getDataLocations(), "data", locationResolver);
    }

    private boolean applyScripts(List<String> locations, String type, ScriptLocationResolver locationResolver) {
        List<Resource> scripts = this.getScripts(locations, type, locationResolver);
        if (!scripts.isEmpty() && this.isEnabled()) {
            this.runScripts(scripts);
            return true;
        }
        return false;
    }

    private List<Resource> getScripts(List<String> locations, String type, ScriptLocationResolver locationResolver) {
        if (CollectionUtils.isEmpty(locations)) {
            return Collections.emptyList();
        }
        ArrayList<Resource> resources = new ArrayList<Resource>();
        for (String location : locations) {
            boolean optional = location.startsWith(OPTIONAL_LOCATION_PREFIX);
            if (optional) {
                location = location.substring(OPTIONAL_LOCATION_PREFIX.length());
            }
            for (Resource resource : this.doGetResources(location, locationResolver)) {
                if (resource.exists()) {
                    resources.add(resource);
                    continue;
                }
                if (optional) continue;
                throw new IllegalStateException("No " + type + " scripts found at location '" + location + "'");
            }
        }
        return resources;
    }

    private List<Resource> doGetResources(String location, ScriptLocationResolver locationResolver) {
        try {
            return locationResolver.resolve(location);
        }
        catch (Exception ex) {
            throw new IllegalStateException("Unable to load resources from " + location, ex);
        }
    }

    private void runScripts(List<Resource> resources) {
        this.runScripts(resources, this.settings.isContinueOnError(), this.settings.getSeparator(), this.settings.getEncoding());
    }

    protected abstract void runScripts(List<Resource> var1, boolean var2, String var3, Charset var4);

    private static class ScriptLocationResolver {
        private final ResourcePatternResolver resourcePatternResolver;

        ScriptLocationResolver(ResourceLoader resourceLoader) {
            this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver((ResourceLoader)resourceLoader);
        }

        private List<Resource> resolve(String location) throws IOException {
            ArrayList<Resource> resources = new ArrayList<Resource>(Arrays.asList(this.resourcePatternResolver.getResources(location)));
            resources.sort((r1, r2) -> {
                try {
                    return r1.getURL().toString().compareTo(r2.getURL().toString());
                }
                catch (IOException ex) {
                    return 0;
                }
            });
            return resources;
        }
    }
}

