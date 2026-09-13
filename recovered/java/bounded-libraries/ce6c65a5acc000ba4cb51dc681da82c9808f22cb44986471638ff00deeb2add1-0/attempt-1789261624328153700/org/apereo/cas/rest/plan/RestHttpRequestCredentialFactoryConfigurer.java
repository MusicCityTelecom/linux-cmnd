/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.rest.plan;

import org.apereo.cas.rest.factory.ChainingRestHttpRequestCredentialFactory;

@FunctionalInterface
public interface RestHttpRequestCredentialFactoryConfigurer {
    public void configureCredentialFactory(ChainingRestHttpRequestCredentialFactory var1);
}

