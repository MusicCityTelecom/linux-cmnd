/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.uma;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.support.jpa.AbstractJpaProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-oauth-uma")
@JsonFilter(value="UmaResourceSetJpaProperties")
public class UmaResourceSetJpaProperties
extends AbstractJpaProperties {
    private static final long serialVersionUID = 210435146313504995L;

    public UmaResourceSetJpaProperties() {
        super.setUrl("");
    }
}

