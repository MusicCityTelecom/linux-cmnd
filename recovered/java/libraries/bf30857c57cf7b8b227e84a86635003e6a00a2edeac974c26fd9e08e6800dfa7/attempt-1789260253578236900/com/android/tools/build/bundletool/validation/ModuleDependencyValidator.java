/*
 * Decompiled with CFR 0.152.
 */
package com.android.tools.build.bundletool.validation;

import com.android.tools.build.bundletool.model.BundleModule;
import com.android.tools.build.bundletool.model.BundleModuleName;
import com.android.tools.build.bundletool.model.exceptions.ValidationException;
import com.android.tools.build.bundletool.model.utils.ModuleDependenciesUtils;
import com.android.tools.build.bundletool.validation.SubValidator;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class ModuleDependencyValidator
extends SubValidator {
    @Override
    public void validateAllModules(ImmutableList<BundleModule> modules) {
        ModuleDependencyValidator.checkHasBaseModule(modules);
        Multimap<String, String> moduleDependenciesMap = ModuleDependenciesUtils.buildAdjacencyMap(modules);
        ImmutableMap<String, BundleModule> modulesByName = Maps.uniqueIndex(modules, module -> module.getName().getName());
        ModuleDependencyValidator.checkNoReflexiveDependencies(moduleDependenciesMap);
        ModuleDependencyValidator.checkModulesHaveUniqueDependencies(moduleDependenciesMap);
        ModuleDependencyValidator.checkReferencedModulesExist(moduleDependenciesMap);
        ModuleDependencyValidator.checkNoCycles(moduleDependenciesMap);
        ModuleDependencyValidator.checkInstantModuleDependencies(moduleDependenciesMap, modulesByName);
        ModuleDependencyValidator.checkValidModuleDeliveryTypeDependencies(moduleDependenciesMap, modulesByName);
        ModuleDependencyValidator.checkMinSdkIsCompatibleWithDependencies(moduleDependenciesMap, modulesByName);
        ModuleDependencyValidator.checkAssetModulesHaveNoDependencies(moduleDependenciesMap, modulesByName);
    }

    private static void checkHasBaseModule(ImmutableList<BundleModule> modules) {
        if (!modules.stream().anyMatch(BundleModule::isBaseModule)) {
            throw ValidationException.builder().withMessage("Mandatory '%s' module is missing.", BundleModuleName.BASE_MODULE_NAME).build();
        }
    }

    private static void checkReferencedModulesExist(Multimap<String, String> moduleDependenciesMap) {
        for (String referencedModule : moduleDependenciesMap.values()) {
            if (moduleDependenciesMap.containsKey(referencedModule)) continue;
            throw ValidationException.builder().withMessage("Module '%s' is referenced by <uses-split> but does not exist.", referencedModule).build();
        }
    }

    private static void checkNoReflexiveDependencies(Multimap<String, String> moduleDependenciesMap) {
        for (String moduleName : moduleDependenciesMap.keySet()) {
            if (moduleName.equals(BundleModuleName.BASE_MODULE_NAME.getName()) || !moduleDependenciesMap.containsEntry(moduleName, moduleName)) continue;
            throw ValidationException.builder().withMessage("Module '%s' depends on itself via <uses-split>.", moduleName).build();
        }
    }

    private static void checkModulesHaveUniqueDependencies(Multimap<String, String> moduleDependenciesMap) {
        for (Map.Entry<String, Collection<String>> entry : moduleDependenciesMap.asMap().entrySet()) {
            String moduleName = entry.getKey();
            Collection<String> moduleDeps = entry.getValue();
            HashSet<String> alreadyReferencedModules = new HashSet<String>();
            for (String moduleDep : moduleDeps) {
                if (alreadyReferencedModules.add(moduleDep)) continue;
                throw ValidationException.builder().withMessage("Module '%s' declares dependency on module '%s' multiple times.", moduleName, moduleDep).build();
            }
        }
    }

    private static void checkNoCycles(Multimap<String, String> moduleDependenciesMap) {
        HashSet<String> safe = new HashSet<String>();
        for (String moduleName : moduleDependenciesMap.keySet()) {
            HashSet<String> visited = new HashSet<String>();
            ModuleDependencyValidator.checkNoCycles(moduleName, moduleDependenciesMap, visited, safe, new LinkedHashSet<String>());
            safe.addAll(visited);
        }
    }

    private static void checkNoCycles(String moduleName, Multimap<String, String> moduleDependenciesMap, Set<String> visited, Set<String> safe, Set<String> processing) {
        if (safe.contains(moduleName)) {
            return;
        }
        if (processing.contains(moduleName)) {
            throw ValidationException.builder().withMessage("Found cyclic dependency between modules: %s", processing).build();
        }
        visited.add(moduleName);
        processing.add(moduleName);
        for (String referencedModule : moduleDependenciesMap.get(moduleName)) {
            if (moduleName.equals(referencedModule)) continue;
            ModuleDependencyValidator.checkNoCycles(referencedModule, moduleDependenciesMap, visited, safe, processing);
        }
        processing.remove(moduleName);
    }

    private static void checkValidModuleDeliveryTypeDependencies(Multimap<String, String> moduleDependenciesMap, ImmutableMap<String, BundleModule> modulesByName) {
        for (Map.Entry<String, String> dependencyEntry : moduleDependenciesMap.entries()) {
            String moduleName = dependencyEntry.getKey();
            String moduleDep = dependencyEntry.getValue();
            BundleModule.ModuleDeliveryType moduleDeliveryType = modulesByName.get(moduleName).getDeliveryType();
            BundleModule.ModuleDeliveryType depDeliveryType = modulesByName.get(moduleDep).getDeliveryType();
            if (moduleDeliveryType.equals((Object)BundleModule.ModuleDeliveryType.CONDITIONAL_INITIAL_INSTALL) && !moduleDep.equals(BundleModuleName.BASE_MODULE_NAME.getName())) {
                throw ValidationException.builder().withMessage("Conditional module '%s' cannot have dependencies but uses module '%s'.", moduleName, moduleDep).build();
            }
            if (moduleDeliveryType.equals((Object)BundleModule.ModuleDeliveryType.NO_INITIAL_INSTALL) && depDeliveryType.equals((Object)BundleModule.ModuleDeliveryType.CONDITIONAL_INITIAL_INSTALL)) {
                throw ValidationException.builder().withMessage("An on-demand module '%s' cannot depend on a conditional module '%s'.", moduleName, moduleDep).build();
            }
            if (!moduleDeliveryType.equals((Object)BundleModule.ModuleDeliveryType.ALWAYS_INITIAL_INSTALL) || depDeliveryType.equals((Object)BundleModule.ModuleDeliveryType.ALWAYS_INITIAL_INSTALL)) continue;
            throw ValidationException.builder().withMessage("Install-time module '%s' cannot depend on a module '%s' that is not install-time.", moduleName, moduleDep).build();
        }
    }

    private static void checkMinSdkIsCompatibleWithDependencies(Multimap<String, String> moduleDependenciesMap, ImmutableMap<String, BundleModule> modulesByName) {
        for (Map.Entry<String, String> dependencyEntry : moduleDependenciesMap.entries()) {
            String moduleName = dependencyEntry.getKey();
            String moduleDepName = dependencyEntry.getValue();
            BundleModule module = modulesByName.get(moduleName);
            if (module.getModuleType().equals((Object)BundleModule.ModuleType.ASSET_MODULE)) continue;
            BundleModule moduleDep = modulesByName.get(moduleDepName);
            int minSdk = module.getAndroidManifest().getEffectiveMinSdkVersion();
            int minSdkDep = moduleDep.getAndroidManifest().getEffectiveMinSdkVersion();
            if (module.getDeliveryType().equals((Object)BundleModule.ModuleDeliveryType.ALWAYS_INITIAL_INSTALL) && minSdk != minSdkDep) {
                throw ValidationException.builder().withMessage("Install-time module '%s' has a minSdkVersion(%d) different than the minSdkVersion(%d) of its dependency '%s'.", moduleName, minSdk, minSdkDep, moduleDepName).build();
            }
            if (minSdk >= minSdkDep || moduleDep.isBaseModule()) continue;
            throw ValidationException.builder().withMessage("Conditional or on-demand module '%s' has a minSdkVersion(%d), which is smaller than the minSdkVersion(%d) of its dependency '%s'.", moduleName, minSdk, minSdkDep, moduleDepName).build();
        }
    }

    private static void checkAssetModulesHaveNoDependencies(Multimap<String, String> moduleDependenciesMap, ImmutableMap<String, BundleModule> modulesByName) {
        for (Map.Entry<String, String> dependencyEntry : moduleDependenciesMap.entries()) {
            String moduleName = dependencyEntry.getKey();
            String moduleDepName = dependencyEntry.getValue();
            BundleModule.ModuleType moduleType = modulesByName.get(moduleName).getModuleType();
            BundleModule.ModuleType moduleDepType = modulesByName.get(moduleDepName).getModuleType();
            if (moduleDepName.equals(BundleModuleName.BASE_MODULE_NAME.getName()) || !moduleType.equals((Object)BundleModule.ModuleType.ASSET_MODULE) && !moduleDepType.equals((Object)BundleModule.ModuleType.ASSET_MODULE)) continue;
            throw ValidationException.builder().withMessage("Module '%s' cannot depend on module '%s' because one of them is an asset pack.", moduleName, moduleDepName).build();
        }
    }

    private static void checkInstantModuleDependencies(Multimap<String, String> moduleDependenciesMap, ImmutableMap<String, BundleModule> modulesByName) {
        for (Map.Entry<String, String> dependencyEntry : moduleDependenciesMap.entries()) {
            String moduleName = dependencyEntry.getKey();
            String moduleDepName = dependencyEntry.getValue();
            boolean isInstantModule = modulesByName.get(moduleName).isInstantModule();
            boolean isDepInstantModule = modulesByName.get(moduleDepName).isInstantModule();
            if (!isInstantModule || isDepInstantModule) continue;
            throw ValidationException.builder().withMessage("Instant module '%s' cannot depend on a module '%s' that is not instant.", moduleName, moduleDepName).build();
        }
    }
}

