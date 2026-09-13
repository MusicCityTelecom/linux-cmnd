/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.cas.util.scripting.WatchableGroovyScriptResource
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.authentication.principal;

import java.util.List;
import java.util.Map;
import lombok.Generated;
import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.cas.util.scripting.WatchableGroovyScriptResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

public class GroovyPrincipalFactory
extends DefaultPrincipalFactory {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyPrincipalFactory.class);
    private static final long serialVersionUID = -3999695695604948495L;
    private final transient WatchableGroovyScriptResource watchableScript;

    public GroovyPrincipalFactory(Resource groovyResource) {
        this.watchableScript = new WatchableGroovyScriptResource(groovyResource);
    }

    @Override
    public Principal createPrincipal(String id, Map<String, List<Object>> attributes) {
        return (Principal)this.watchableScript.execute(new Object[]{id, attributes, LOGGER}, Principal.class);
    }

    @Override
    @Generated
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GroovyPrincipalFactory)) {
            return false;
        }
        GroovyPrincipalFactory other = (GroovyPrincipalFactory)o;
        if (!other.canEqual(this)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    @Generated
    protected boolean canEqual(Object other) {
        return other instanceof GroovyPrincipalFactory;
    }

    @Override
    @Generated
    public int hashCode() {
        int result = super.hashCode();
        return result;
    }
}

