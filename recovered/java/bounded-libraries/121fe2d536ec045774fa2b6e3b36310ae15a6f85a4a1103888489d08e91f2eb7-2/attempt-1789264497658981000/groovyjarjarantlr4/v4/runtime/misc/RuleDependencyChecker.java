/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.misc;

import groovyjarjarantlr4.v4.runtime.Recognizer;
import groovyjarjarantlr4.v4.runtime.RuleDependencies;
import groovyjarjarantlr4.v4.runtime.RuleDependency;
import groovyjarjarantlr4.v4.runtime.RuleVersion;
import groovyjarjarantlr4.v4.runtime.misc.Tuple;
import groovyjarjarantlr4.v4.runtime.misc.Tuple2;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RuleDependencyChecker {
    private static final Logger LOGGER = Logger.getLogger(RuleDependencyChecker.class.getName());
    private static final Set<Class<?>> checkedTypes = new HashSet();

    public static void checkDependencies(Class<?> dependentClass) {
        if (RuleDependencyChecker.isChecked(dependentClass)) {
            return;
        }
        ArrayList typesToCheck = new ArrayList();
        typesToCheck.add(dependentClass);
        Collections.addAll(typesToCheck, dependentClass.getDeclaredClasses());
        for (Class clazz : typesToCheck) {
            List<Tuple2<RuleDependency, AnnotatedElement>> dependencies;
            if (RuleDependencyChecker.isChecked(clazz) || (dependencies = RuleDependencyChecker.getDependencies(clazz)).isEmpty()) continue;
            RuleDependencyChecker.checkDependencies(dependencies, dependencies.get(0).getItem1().recognizer());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static boolean isChecked(Class<?> clazz) {
        Set<Class<?>> set = checkedTypes;
        synchronized (set) {
            return checkedTypes.contains(clazz);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void markChecked(Class<?> clazz) {
        Set<Class<?>> set = checkedTypes;
        synchronized (set) {
            checkedTypes.add(clazz);
        }
    }

    private static void checkDependencies(List<Tuple2<RuleDependency, AnnotatedElement>> dependencies, Class<? extends Recognizer<?, ?>> recognizerClass) {
        String[] ruleNames = RuleDependencyChecker.getRuleNames(recognizerClass);
        int[] ruleVersions = RuleDependencyChecker.getRuleVersions(recognizerClass, ruleNames);
        StringBuilder incompatible = new StringBuilder();
        for (Tuple2<RuleDependency, AnnotatedElement> dependency : dependencies) {
            if (!recognizerClass.isAssignableFrom(dependency.getItem1().recognizer())) continue;
            if (dependency.getItem1().rule() < 0 || dependency.getItem1().rule() >= ruleVersions.length) {
                incompatible.append(String.format("Element %s dependent on unknown rule %d@%d in %s\n", dependency.getItem2().toString(), dependency.getItem1().rule(), dependency.getItem1().version(), dependency.getItem1().recognizer().getSimpleName()));
                continue;
            }
            if (ruleVersions[dependency.getItem1().rule()] == dependency.getItem1().version()) continue;
            incompatible.append(String.format("Element %s dependent on rule %s@%d (found @%d) in %s\n", dependency.getItem2().toString(), ruleNames[dependency.getItem1().rule()], dependency.getItem1().version(), ruleVersions[dependency.getItem1().rule()], dependency.getItem1().recognizer().getSimpleName()));
        }
        if (incompatible.length() != 0) {
            throw new IllegalStateException(incompatible.toString());
        }
        RuleDependencyChecker.markChecked(recognizerClass);
    }

    private static int[] getRuleVersions(Class<? extends Recognizer<?, ?>> recognizerClass, String[] ruleNames) {
        Field[] fields;
        int[] versions = new int[ruleNames.length];
        for (Field field : fields = recognizerClass.getFields()) {
            boolean isInteger;
            boolean isStatic = (field.getModifiers() & 8) != 0;
            boolean bl = isInteger = field.getType() == Integer.TYPE;
            if (!isStatic || !isInteger || !field.getName().startsWith("RULE_")) continue;
            try {
                int version;
                String name = field.getName().substring("RULE_".length());
                if (name.isEmpty() || !Character.isLowerCase(name.charAt(0))) continue;
                int index = field.getInt(null);
                if (index < 0 || index >= versions.length) {
                    Object[] params = new Object[]{index, field.getName(), recognizerClass.getSimpleName()};
                    LOGGER.log(Level.WARNING, "Rule index {0} for rule ''{1}'' out of bounds for recognizer {2}.", params);
                    continue;
                }
                Method ruleMethod = RuleDependencyChecker.getRuleMethod(recognizerClass, name);
                if (ruleMethod == null) {
                    Object[] params = new Object[]{name, recognizerClass.getSimpleName()};
                    LOGGER.log(Level.WARNING, "Could not find rule method for rule ''{0}'' in recognizer {1}.", params);
                    continue;
                }
                RuleVersion ruleVersion = ruleMethod.getAnnotation(RuleVersion.class);
                versions[index] = version = ruleVersion != null ? ruleVersion.value() : 0;
            }
            catch (IllegalArgumentException ex) {
                LOGGER.log(Level.WARNING, null, ex);
            }
            catch (IllegalAccessException ex) {
                LOGGER.log(Level.WARNING, null, ex);
            }
        }
        return versions;
    }

    private static Method getRuleMethod(Class<? extends Recognizer<?, ?>> recognizerClass, String name) {
        Method[] declaredMethods;
        for (Method method : declaredMethods = recognizerClass.getMethods()) {
            if (!method.getName().equals(name) || !method.isAnnotationPresent(RuleVersion.class)) continue;
            return method;
        }
        return null;
    }

    private static String[] getRuleNames(Class<? extends Recognizer<?, ?>> recognizerClass) {
        try {
            Field ruleNames = recognizerClass.getField("ruleNames");
            return (String[])ruleNames.get(null);
        }
        catch (NoSuchFieldException ex) {
            LOGGER.log(Level.WARNING, null, ex);
        }
        catch (SecurityException ex) {
            LOGGER.log(Level.WARNING, null, ex);
        }
        catch (IllegalArgumentException ex) {
            LOGGER.log(Level.WARNING, null, ex);
        }
        catch (IllegalAccessException ex) {
            LOGGER.log(Level.WARNING, null, ex);
        }
        return new String[0];
    }

    public static List<Tuple2<RuleDependency, AnnotatedElement>> getDependencies(Class<?> clazz) {
        ArrayList<Tuple2<RuleDependency, AnnotatedElement>> result = new ArrayList<Tuple2<RuleDependency, AnnotatedElement>>();
        List<ElementType> supportedTarget = Arrays.asList(RuleDependency.class.getAnnotation(Target.class).value());
        block10: for (ElementType target : supportedTarget) {
            switch (target) {
                case TYPE: {
                    if (clazz.isAnnotation()) break;
                    RuleDependencyChecker.getElementDependencies(clazz, result);
                    break;
                }
                case ANNOTATION_TYPE: {
                    if (clazz.isAnnotation()) break;
                    RuleDependencyChecker.getElementDependencies(clazz, result);
                    break;
                }
                case CONSTRUCTOR: {
                    for (AccessibleObject accessibleObject : clazz.getDeclaredConstructors()) {
                        RuleDependencyChecker.getElementDependencies(accessibleObject, result);
                    }
                    continue block10;
                }
                case FIELD: {
                    for (AccessibleObject accessibleObject : clazz.getDeclaredFields()) {
                        RuleDependencyChecker.getElementDependencies(accessibleObject, result);
                    }
                    continue block10;
                }
                case LOCAL_VARIABLE: {
                    System.err.println("Runtime rule dependency checking is not supported for local variables.");
                    break;
                }
                case METHOD: {
                    for (AccessibleObject accessibleObject : clazz.getDeclaredMethods()) {
                        RuleDependencyChecker.getElementDependencies(accessibleObject, result);
                    }
                    continue block10;
                }
                case PACKAGE: {
                    break;
                }
                case PARAMETER: {
                    System.err.println("Runtime rule dependency checking is not supported for parameters.");
                }
            }
        }
        return result;
    }

    private static void getElementDependencies(AnnotatedElement annotatedElement, List<Tuple2<RuleDependency, AnnotatedElement>> result) {
        RuleDependencies dependencies;
        RuleDependency dependency = annotatedElement.getAnnotation(RuleDependency.class);
        if (dependency != null) {
            result.add(Tuple.create(dependency, annotatedElement));
        }
        if ((dependencies = annotatedElement.getAnnotation(RuleDependencies.class)) != null) {
            for (RuleDependency d : dependencies.value()) {
                if (d == null) continue;
                result.add(Tuple.create(d, annotatedElement));
            }
        }
    }

    private RuleDependencyChecker() {
    }
}

