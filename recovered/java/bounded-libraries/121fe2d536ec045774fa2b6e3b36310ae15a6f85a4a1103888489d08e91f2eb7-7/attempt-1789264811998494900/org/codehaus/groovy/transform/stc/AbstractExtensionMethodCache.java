/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.stc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import org.apache.groovy.util.SystemUtil;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.runtime.m12n.ExtensionModule;
import org.codehaus.groovy.runtime.m12n.ExtensionModuleScanner;
import org.codehaus.groovy.runtime.m12n.MetaInfExtensionModule;
import org.codehaus.groovy.runtime.memoize.EvictableCache;
import org.codehaus.groovy.runtime.memoize.StampedCommonCache;
import org.codehaus.groovy.transform.stc.ExtensionMethodNode;

public abstract class AbstractExtensionMethodCache {
    final EvictableCache<ClassLoader, Map<String, List<MethodNode>>> cache = new StampedCommonCache<ClassLoader, Map<String, List<MethodNode>>>(new WeakHashMap());
    private final String disabledString = SystemUtil.getSystemPropertySafe(this.getDisablePropertyName());
    private final boolean disabling = this.disabledString != null;
    private final Set<String> disabledNames = this.disabling ? new HashSet<String>(Arrays.asList(this.disabledString.split(","))) : null;

    public Map<String, List<MethodNode>> get(ClassLoader loader) {
        return this.cache.getAndPut(loader, this::getMethodsFromClassLoader);
    }

    private Map<String, List<MethodNode>> getMethodsFromClassLoader(ClassLoader classLoader) {
        LinkedList<ExtensionModule> modules = new LinkedList<ExtensionModule>();
        ExtensionModuleScanner scanner = new ExtensionModuleScanner(module -> {
            if (!(module instanceof MetaInfExtensionModule)) {
                return;
            }
            boolean skip = false;
            for (ExtensionModule extensionModule : modules) {
                if (!extensionModule.getName().equals(module.getName())) continue;
                skip = true;
                break;
            }
            if (!skip) {
                modules.add(module);
            }
        }, classLoader);
        scanner.scanClasspathModules();
        return this.makeMethodsUnmodifiable(this.getMethods(modules));
    }

    private Map<String, List<MethodNode>> getMethods(List<ExtensionModule> modules) {
        LinkedHashSet<Class> instanceExtClasses = new LinkedHashSet<Class>();
        LinkedHashSet<Class> staticExtClasses = new LinkedHashSet<Class>();
        for (ExtensionModule module : modules) {
            MetaInfExtensionModule extensionModule = (MetaInfExtensionModule)module;
            instanceExtClasses.addAll(extensionModule.getInstanceMethodsExtensionClasses());
            staticExtClasses.addAll(extensionModule.getStaticMethodsExtensionClasses());
        }
        HashMap<String, List<MethodNode>> methods = new HashMap<String, List<MethodNode>>();
        this.addAdditionalClassesToScan(instanceExtClasses, staticExtClasses);
        this.scan(methods, staticExtClasses, true);
        this.scan(methods, instanceExtClasses, false);
        return methods;
    }

    private Map<String, List<MethodNode>> makeMethodsUnmodifiable(Map<String, List<MethodNode>> methods) {
        methods.replaceAll((k, v) -> Collections.unmodifiableList(v));
        return Collections.unmodifiableMap(methods);
    }

    protected abstract void addAdditionalClassesToScan(Set<Class> var1, Set<Class> var2);

    private void scan(Map<String, List<MethodNode>> accumulator, Iterable<Class> allClasses, boolean isStatic) {
        Predicate<MethodNode> methodFilter = this.getMethodFilter();
        Function<MethodNode, String> methodMapper = this.getMethodMapper();
        for (Class dgmLikeClass : allClasses) {
            ClassNode cn = ClassHelper.makeWithoutCaching(dgmLikeClass, true);
            for (MethodNode methodNode : cn.getMethods()) {
                if (!methodNode.isStatic() || !methodNode.isPublic() || methodNode.getParameters().length == 0 || methodFilter.test(methodNode) || this.disabling && this.disabledNames.contains(methodNode.getName())) continue;
                this.accumulate(accumulator, isStatic, methodNode, methodMapper);
            }
        }
    }

    protected abstract String getDisablePropertyName();

    protected abstract Predicate<MethodNode> getMethodFilter();

    protected abstract Function<MethodNode, String> getMethodMapper();

    private void accumulate(Map<String, List<MethodNode>> accumulator, boolean isStatic, MethodNode metaMethod, Function<MethodNode, String> mapperFunction) {
        Parameter[] types = metaMethod.getParameters();
        Parameter[] parameters = new Parameter[types.length - 1];
        System.arraycopy(types, 1, parameters, 0, parameters.length);
        ExtensionMethodNode node = new ExtensionMethodNode(metaMethod, metaMethod.getName(), metaMethod.getModifiers(), metaMethod.getReturnType(), parameters, ClassNode.EMPTY_ARRAY, null, isStatic);
        node.setGenericsTypes(metaMethod.getGenericsTypes());
        ClassNode declaringClass = types[0].getType();
        node.setDeclaringClass(declaringClass);
        String key = mapperFunction.apply(metaMethod);
        List nodes = accumulator.computeIfAbsent(key, k -> new ArrayList());
        nodes.add(node);
    }
}

