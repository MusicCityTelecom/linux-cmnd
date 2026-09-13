/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.catalina.Context
 *  org.apache.catalina.core.StandardContext
 */
package org.springframework.boot.web.embedded.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.core.StandardContext;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;

class DisableReferenceClearingContextCustomizer
implements TomcatContextCustomizer {
    DisableReferenceClearingContextCustomizer() {
    }

    @Override
    public void customize(Context context) {
        if (!(context instanceof StandardContext)) {
            return;
        }
        StandardContext standardContext = (StandardContext)context;
        try {
            standardContext.setClearReferencesObjectStreamClassCaches(false);
            standardContext.setClearReferencesRmiTargets(false);
            standardContext.setClearReferencesThreadLocals(false);
        }
        catch (NoSuchMethodError noSuchMethodError) {
            // empty catch block
        }
    }
}

