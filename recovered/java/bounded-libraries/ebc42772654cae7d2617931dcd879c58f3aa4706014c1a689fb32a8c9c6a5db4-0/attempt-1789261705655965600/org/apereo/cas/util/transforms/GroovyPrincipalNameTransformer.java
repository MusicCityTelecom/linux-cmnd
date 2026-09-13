/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.handler.PrincipalNameTransformer
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.util.transforms;

import lombok.Generated;
import org.apereo.cas.authentication.handler.PrincipalNameTransformer;
import org.apereo.cas.util.scripting.WatchableGroovyScriptResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

public class GroovyPrincipalNameTransformer
implements PrincipalNameTransformer {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyPrincipalNameTransformer.class);
    private final transient WatchableGroovyScriptResource watchableScript;

    public GroovyPrincipalNameTransformer(Resource groovyResource) {
        this.watchableScript = new WatchableGroovyScriptResource(groovyResource);
    }

    public String transform(String formUserId) {
        return this.watchableScript.execute(new Object[]{formUserId, LOGGER}, String.class, true);
    }

    @Generated
    public GroovyPrincipalNameTransformer(WatchableGroovyScriptResource watchableScript) {
        this.watchableScript = watchableScript;
    }
}

