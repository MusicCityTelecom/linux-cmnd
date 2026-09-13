/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.aup;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-aup-core")
@JsonFilter(value="InMemoryAcceptableUsagePolicyProperties")
public class InMemoryAcceptableUsagePolicyProperties
implements Serializable {
    private static final long serialVersionUID = 8164227843747126083L;
    private Scope scope = Scope.GLOBAL;

    @Generated
    public Scope getScope() {
        return this.scope;
    }

    @Generated
    public InMemoryAcceptableUsagePolicyProperties setScope(Scope scope) {
        this.scope = scope;
        return this;
    }

    public static enum Scope {
        GLOBAL,
        AUTHENTICATION;

    }
}

