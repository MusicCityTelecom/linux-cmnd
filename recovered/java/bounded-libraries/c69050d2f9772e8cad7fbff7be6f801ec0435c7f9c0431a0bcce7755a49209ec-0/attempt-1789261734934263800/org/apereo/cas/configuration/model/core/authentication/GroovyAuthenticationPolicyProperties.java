/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.core.authentication;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-authentication", automated=true)
public class GroovyAuthenticationPolicyProperties
implements Serializable {
    private static final long serialVersionUID = 8713917167124116270L;
    private String script;

    @Generated
    public String getScript() {
        return this.script;
    }

    @Generated
    public GroovyAuthenticationPolicyProperties setScript(String script) {
        this.script = script;
        return this;
    }
}

