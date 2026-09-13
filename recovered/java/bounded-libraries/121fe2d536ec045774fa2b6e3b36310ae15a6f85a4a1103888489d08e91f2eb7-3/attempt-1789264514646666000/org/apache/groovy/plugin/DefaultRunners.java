/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.plugin;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyRuntimeException;
import java.lang.reflect.Method;
import java.util.List;
import org.apache.groovy.plugin.GroovyRunner;
import org.codehaus.groovy.runtime.InvokerHelper;

final class DefaultRunners {
    private static final GroovyRunner JUNIT3_TEST = new Junit3TestRunner();
    private static final GroovyRunner JUNIT3_SUITE = new Junit3SuiteRunner();
    private static final GroovyRunner JUNIT4_TEST = new Junit4TestRunner();

    private DefaultRunners() {
    }

    static GroovyRunner junit3TestRunner() {
        return JUNIT3_TEST;
    }

    static GroovyRunner junit3SuiteRunner() {
        return JUNIT3_SUITE;
    }

    static GroovyRunner junit4TestRunner() {
        return JUNIT4_TEST;
    }

    private static class Junit3TestRunner
    implements GroovyRunner {
        private Junit3TestRunner() {
        }

        @Override
        public boolean canRun(Class<?> scriptClass, GroovyClassLoader loader) {
            try {
                Class<?> testCaseClass = loader.loadClass("junit.framework.TestCase");
                return testCaseClass.isAssignableFrom(scriptClass);
            }
            catch (Throwable e) {
                return false;
            }
        }

        @Override
        public Object run(Class<?> scriptClass, GroovyClassLoader loader) {
            try {
                Object testSuite = InvokerHelper.invokeConstructorOf("junit.framework.TestSuite", (Object)new Object[]{scriptClass});
                return InvokerHelper.invokeStaticMethod("junit.textui.TestRunner", "run", (Object)new Object[]{testSuite});
            }
            catch (ClassNotFoundException e) {
                throw new GroovyRuntimeException("Failed to run the unit test. JUnit is not on the Classpath.", e);
            }
        }
    }

    private static class Junit3SuiteRunner
    implements GroovyRunner {
        private Junit3SuiteRunner() {
        }

        @Override
        public boolean canRun(Class<?> scriptClass, GroovyClassLoader loader) {
            try {
                Class<?> testSuiteClass = loader.loadClass("junit.framework.TestSuite");
                return testSuiteClass.isAssignableFrom(scriptClass);
            }
            catch (Throwable e) {
                return false;
            }
        }

        @Override
        public Object run(Class<?> scriptClass, GroovyClassLoader loader) {
            try {
                Object testSuite = InvokerHelper.invokeStaticMethod(scriptClass, "suite", (Object)new Object[0]);
                return InvokerHelper.invokeStaticMethod("junit.textui.TestRunner", "run", (Object)new Object[]{testSuite});
            }
            catch (ClassNotFoundException e) {
                throw new GroovyRuntimeException("Failed to run the unit test. JUnit is not on the Classpath.", e);
            }
        }
    }

    private static class Junit4TestRunner
    implements GroovyRunner {
        private Junit4TestRunner() {
        }

        @Override
        public boolean canRun(Class<?> scriptClass, GroovyClassLoader loader) {
            return Junit4TestRunner.hasRunWithAnnotation(scriptClass, loader) || Junit4TestRunner.hasTestAnnotatedMethod(scriptClass, loader);
        }

        @Override
        public Object run(Class<?> scriptClass, GroovyClassLoader loader) {
            try {
                Class<?> junitCoreClass = loader.loadClass("org.junit.runner.JUnitCore");
                Object result = InvokerHelper.invokeStaticMethod(junitCoreClass, "runClasses", (Object)new Object[]{scriptClass});
                System.out.print("JUnit 4 Runner, Tests: " + InvokerHelper.getProperty(result, "runCount"));
                System.out.print(", Failures: " + InvokerHelper.getProperty(result, "failureCount"));
                System.out.println(", Time: " + InvokerHelper.getProperty(result, "runTime"));
                List failures = (List)InvokerHelper.getProperty(result, "failures");
                for (Object f : failures) {
                    System.out.println("Test Failure: " + InvokerHelper.getProperty(f, "description"));
                    System.out.println(InvokerHelper.getProperty(f, "trace"));
                }
                return result;
            }
            catch (ClassNotFoundException e) {
                throw new GroovyRuntimeException("Error running JUnit 4 test.", e);
            }
        }

        private static boolean hasRunWithAnnotation(Class<?> scriptClass, ClassLoader loader) {
            try {
                Class<?> runWithAnnotationClass = loader.loadClass("org.junit.runner.RunWith");
                return scriptClass.isAnnotationPresent(runWithAnnotationClass);
            }
            catch (Throwable e) {
                return false;
            }
        }

        private static boolean hasTestAnnotatedMethod(Class<?> scriptClass, ClassLoader loader) {
            try {
                Method[] methods;
                Class<?> testAnnotationClass = loader.loadClass("org.junit.Test");
                for (Method method : methods = scriptClass.getMethods()) {
                    if (!method.isAnnotationPresent(testAnnotationClass)) continue;
                    return true;
                }
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            return false;
        }
    }
}

