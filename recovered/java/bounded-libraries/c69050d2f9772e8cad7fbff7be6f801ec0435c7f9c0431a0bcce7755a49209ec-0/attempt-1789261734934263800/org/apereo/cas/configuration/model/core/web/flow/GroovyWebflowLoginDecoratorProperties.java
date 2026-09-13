/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.core.web.flow;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-webflow", automated=true)
@JsonFilter(value="GroovyWebflowLoginDecoratorProperties")
public class GroovyWebflowLoginDecoratorProperties
extends SpringResourceProperties {
    private static final long serialVersionUID = 8079027843747126083L;
}

