/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFilter
 */
package org.apereo.cas.configuration.model.core.web;

import com.fasterxml.jackson.annotation.JsonFilter;
import java.io.Serializable;
import org.apereo.cas.configuration.model.support.cookie.PinnableCookieProperties;
import org.apereo.cas.configuration.support.RequiresModule;

@RequiresModule(name="cas-server-core-web", automated=true)
@JsonFilter(value="LocaleCookieProperties")
public class LocaleCookieProperties
extends PinnableCookieProperties
implements Serializable {
    private static final long serialVersionUID = 158577966798914031L;
}

