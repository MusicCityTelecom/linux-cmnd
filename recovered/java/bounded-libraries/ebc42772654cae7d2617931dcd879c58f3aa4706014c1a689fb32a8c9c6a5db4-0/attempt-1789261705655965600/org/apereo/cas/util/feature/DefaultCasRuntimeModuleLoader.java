/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.jooq.lambda.Unchecked
 *  org.springframework.core.io.support.PathMatchingResourcePatternResolver
 *  org.springframework.core.io.support.PropertiesLoaderUtils
 */
package org.apereo.cas.util.feature;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Generated;
import org.apereo.cas.util.feature.CasRuntimeModule;
import org.apereo.cas.util.feature.CasRuntimeModuleLoader;
import org.jooq.lambda.Unchecked;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class DefaultCasRuntimeModuleLoader
implements CasRuntimeModuleLoader {
    @Override
    public List<CasRuntimeModule> load() throws Exception {
        PathMatchingResourcePatternResolver loader = new PathMatchingResourcePatternResolver(this.getClass().getClassLoader());
        return Arrays.stream(loader.getResources("classpath*:/git.properties")).map(Unchecked.function(PropertiesLoaderUtils::loadProperties)).filter(props -> props.containsKey("project.name")).map(props -> ((CasRuntimeModule.CasRuntimeModuleBuilder)((CasRuntimeModule.CasRuntimeModuleBuilder)((CasRuntimeModule.CasRuntimeModuleBuilder)CasRuntimeModule.builder().name(props.get("project.name").toString())).version(props.get("project.version").toString())).description(props.get("project.description").toString())).build()).sorted(Comparator.comparing(CasRuntimeModule::getName)).collect(Collectors.toList());
    }

    @Generated
    public DefaultCasRuntimeModuleLoader() {
    }
}

