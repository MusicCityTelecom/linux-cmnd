/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.model.utils;

import com.android.bundle.Commands;
import com.android.tools.build.bundletool.model.AndroidManifest;
import com.android.tools.build.bundletool.model.AppBundle;
import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.BundleModuleName;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import java.util.Set;
import java.util.stream.Collectors;

public final class ModuleDependenciesUtils {
    public static ImmutableSet<BundleModule> getModulesIncludingDependencies(AppBundle appBundle, ImmutableList<BundleModule> modules) {
        Multimap<String, String> adjacencyMap = ModuleDependenciesUtils.buildAdjacencyMap(modules);
        Set<String> dependencyModules = modules.stream().map(module -> module.getName().getName()).collect(Collectors.toSet());
        for (BundleModule requestedModule : modules) {
            ModuleDependenciesUtils.addModuleDependencies(requestedModule.getName().getName(), adjacencyMap, dependencyModules);
        }
        return dependencyModules.stream().map(module -> appBundle.getModule(BundleModuleName.create(module))).collect(ImmutableSet.toImmutableSet());
    }

    public static ImmutableMultimap<String, String> buildAdjacencyMap(Commands.Variant variant) {
        ImmutableMultimap.Builder moduleDependenciesMap = ImmutableMultimap.builder();
        variant.getApkSetList().stream().map(Commands.ApkSet::getModuleMetadata).forEach(moduleMetadata -> {
            moduleDependenciesMap.putAll(moduleMetadata.getName(), moduleMetadata.getDependenciesList());
            moduleDependenciesMap.put(moduleMetadata.getName(), "base");
        });
        return moduleDependenciesMap.build();
    }

    public static Multimap<String, String> buildAdjacencyMap(ImmutableList<BundleModule> modules) {
        ArrayListMultimap<String, String> moduleDependenciesMap = ArrayListMultimap.create();
        for (BundleModule module : modules) {
            String moduleName = module.getName().getName();
            AndroidManifest manifest = module.getAndroidManifest();
            Preconditions.checkArgument(!moduleDependenciesMap.containsKey(moduleName), "Module named '%s' was passed in multiple times.", (Object)moduleName);
            moduleDependenciesMap.putAll(moduleName, manifest.getUsesSplits());
            if (moduleDependenciesMap.containsEntry(moduleName, BundleModuleName.BASE_MODULE_NAME.getName())) {
                throw ValidationException.builder().withMessage("Module '%s' declares dependency on the '%s' module, which is implicit.", moduleName, BundleModuleName.BASE_MODULE_NAME).build();
            }
            moduleDependenciesMap.put(moduleName, BundleModuleName.BASE_MODULE_NAME.getName());
        }
        return Multimaps.unmodifiableMultimap(moduleDependenciesMap);
    }

    public static void addModuleDependencies(String moduleName, Multimap<String, String> moduleDependenciesMap, Set<String> dependencyModules) {
        if (!moduleDependenciesMap.containsKey(moduleName)) {
            return;
        }
        for (String moduleDependency : moduleDependenciesMap.get(moduleName)) {
            if (!dependencyModules.add(moduleDependency)) continue;
            ModuleDependenciesUtils.addModuleDependencies(moduleDependency, moduleDependenciesMap, dependencyModules);
        }
    }
}

