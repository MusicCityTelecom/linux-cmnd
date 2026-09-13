/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.support.interrupt;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.apereo.cas.configuration.model.SpringResourceProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-support-interrupt-webflow")
@JsonFilter(value="GroovyInterruptProperties")
public class GroovyInterruptProperties
extends SpringResourceProperties {
    private static final long serialVersionUID = 8079027843747126082L;
}

