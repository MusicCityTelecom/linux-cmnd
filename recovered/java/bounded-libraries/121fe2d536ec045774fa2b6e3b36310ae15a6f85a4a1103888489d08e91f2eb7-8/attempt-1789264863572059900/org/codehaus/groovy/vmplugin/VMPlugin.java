/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.vmplugin;

import groovy.lang.MetaClass;
import groovy.lang.MetaMethod;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.CompileUnit;

public interface VMPlugin {
    public void setAdditionalClassInformation(ClassNode var1);

    public Class[] getPluginDefaultGroovyMethods();

    public Class[] getPluginStaticGroovyMethods();

    public void configureAnnotationNodeFromDefinition(AnnotationNode var1, AnnotationNode var2);

    public void configureAnnotation(AnnotationNode var1);

    public void configureClassNode(CompileUnit var1, ClassNode var2);

    public void invalidateCallSites();

    public Object getInvokeSpecialHandle(Method var1, Object var2);

    public Object invokeHandle(Object var1, Object[] var2) throws Throwable;

    public int getVersion();

    public static String getJavaVersion() {
        try {
            return System.getProperty("java.specification.version");
        }
        catch (SecurityException se) {
            Class<?> versionClass;
            try {
                versionClass = Class.forName("java.lang.Runtime$Version");
            }
            catch (ClassNotFoundException e) {
                return "1.8";
            }
            try {
                MethodHandles.Lookup lookup = MethodHandles.lookup();
                MethodHandle versionMethodHandle = lookup.unreflect(Runtime.class.getMethod("version", new Class[0]));
                Object version = versionMethodHandle.invoke();
                MethodHandle majorMethodHandle = lookup.unreflect(versionClass.getMethod("major", new Class[0]));
                return String.valueOf(majorMethodHandle.invoke(version));
            }
            catch (Throwable t) {
                throw new GroovyBugError(t.getMessage());
            }
        }
    }

    public boolean checkCanSetAccessible(AccessibleObject var1, Class<?> var2);

    public boolean checkAccessible(Class<?> var1, Class<?> var2, int var3, boolean var4);

    public boolean trySetAccessible(AccessibleObject var1);

    public MetaMethod transformMetaMethod(MetaClass var1, MetaMethod var2, Class<?> var3);

    @Deprecated
    public <T> T doPrivileged(PrivilegedAction<T> var1);

    @Deprecated
    public <T> T doPrivileged(PrivilegedExceptionAction<T> var1) throws PrivilegedActionException;

    public MetaMethod transformMetaMethod(MetaClass var1, MetaMethod var2);

    default public Map<String, Set<String>> getDefaultImportClasses(String[] packageNames) {
        return Collections.emptyMap();
    }
}

