/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.configuration.model.support.fortress;

import java.io.Serializable;
import lombok.Generated;
import org.apereo.cas.configuration.support.RequiredProperty;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-fortress")
@Deprecated(since="6.6")
public class FortressAuthenticationProperties
implements Serializable {
    private static final long serialVersionUID = 9068259944327425315L;
    @RequiredProperty
    private String rbaccontext = "HOME";

    @Generated
    public String getRbaccontext() {
        return this.rbaccontext;
    }

    @Generated
    public FortressAuthenticationProperties setRbaccontext(String rbaccontext) {
        this.rbaccontext = rbaccontext;
        return this;
    }
}

