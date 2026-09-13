/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.undertow.servlet.api.DeploymentInfo
 */
package org.springframework.boot.web.embedded.undertow;

import io.undertow.servlet.api.DeploymentInfo;

@FunctionalInterface
public interface UndertowDeploymentInfoCustomizer {
    public void customize(DeploymentInfo var1);
}

