/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.handler;

import java.util.Collection;

public interface HeaderPropagationAware {
    public void setNotPropagatedHeaders(String ... var1);

    public Collection<String> getNotPropagatedHeaders();

    public void addNotPropagatedHeaders(String ... var1);
}

