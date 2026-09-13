/*
 * Decompiled with CFR 0.152.
 */
package org.apereo.cas.util.feature;

import java.util.List;
import org.apereo.cas.util.feature.CasRuntimeModule;

@FunctionalInterface
public interface CasRuntimeModuleLoader {
    public List<CasRuntimeModule> load() throws Exception;
}

