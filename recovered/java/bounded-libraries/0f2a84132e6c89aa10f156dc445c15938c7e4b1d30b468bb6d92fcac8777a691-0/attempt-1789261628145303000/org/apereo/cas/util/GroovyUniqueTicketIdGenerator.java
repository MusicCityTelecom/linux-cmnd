/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.ticket.UniqueTicketIdGenerator
 *  org.apereo.cas.util.scripting.WatchableGroovyScriptResource
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.util;

import lombok.Generated;
import org.apereo.cas.ticket.UniqueTicketIdGenerator;
import org.apereo.cas.util.scripting.WatchableGroovyScriptResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

public class GroovyUniqueTicketIdGenerator
implements UniqueTicketIdGenerator {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyUniqueTicketIdGenerator.class);
    private final WatchableGroovyScriptResource watchableScript;

    public GroovyUniqueTicketIdGenerator(Resource groovyResource) {
        this.watchableScript = new WatchableGroovyScriptResource(groovyResource);
    }

    public String getNewTicketId(String prefix) {
        Object[] args = new Object[]{prefix, LOGGER};
        return (String)this.watchableScript.execute(args, String.class);
    }

    @Generated
    public GroovyUniqueTicketIdGenerator(WatchableGroovyScriptResource watchableScript) {
        this.watchableScript = watchableScript;
    }
}

