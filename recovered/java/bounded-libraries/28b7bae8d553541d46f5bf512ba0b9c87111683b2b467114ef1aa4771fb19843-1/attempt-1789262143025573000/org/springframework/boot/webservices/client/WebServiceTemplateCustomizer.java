/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.ws.client.core.WebServiceTemplate
 */
package org.springframework.boot.webservices.client;

import org.springframework.ws.client.core.WebServiceTemplate;

@FunctionalInterface
public interface WebServiceTemplateCustomizer {
    public void customize(WebServiceTemplate var1);
}

