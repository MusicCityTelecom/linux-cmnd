/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.support.events.CasEventRepositoryFilter
 *  org.apereo.cas.support.events.dao.CasEvent
 *  org.apereo.cas.util.scripting.WatchableGroovyScriptResource
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.beans.factory.DisposableBean
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.support.events.dao.filter;

import lombok.Generated;
import org.apereo.cas.support.events.CasEventRepositoryFilter;
import org.apereo.cas.support.events.dao.CasEvent;
import org.apereo.cas.util.scripting.WatchableGroovyScriptResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.core.io.Resource;

public class GroovyCasEventRepositoryFilter
implements CasEventRepositoryFilter,
DisposableBean {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyCasEventRepositoryFilter.class);
    private final WatchableGroovyScriptResource watchableScript;

    public GroovyCasEventRepositoryFilter(Resource groovyResource) {
        this.watchableScript = new WatchableGroovyScriptResource(groovyResource);
    }

    public boolean shouldSaveEvent(CasEvent event) {
        Object[] args = new Object[]{event, LOGGER};
        return (Boolean)this.watchableScript.execute("shouldSaveEvent", Boolean.class, args);
    }

    public void destroy() {
        this.watchableScript.close();
    }
}

