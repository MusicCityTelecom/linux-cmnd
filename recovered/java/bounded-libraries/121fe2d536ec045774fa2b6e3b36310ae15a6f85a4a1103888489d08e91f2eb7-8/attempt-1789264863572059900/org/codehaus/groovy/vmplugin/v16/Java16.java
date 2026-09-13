/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.vmplugin.v16;

import groovy.lang.GroovyRuntimeException;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.CompileUnit;
import org.codehaus.groovy.ast.RecordComponentNode;
import org.codehaus.groovy.vmplugin.v10.Java10;
import org.codehaus.groovy.vmplugin.v16.ProxyDefaultMethodHandle;

public class Java16
extends Java10 {
    @Override
    public int getVersion() {
        return 16;
    }

    @Override
    public Object getInvokeSpecialHandle(Method method, Object receiver) {
        try {
            Class<?> receiverType = receiver.getClass();
            if (method.isDefault() && Proxy.isProxyClass(receiverType)) {
                return new ProxyDefaultMethodHandle((Proxy)receiver, method);
            }
            MethodHandles.Lookup lookup = this.getLookup(receiver);
            if (0 != (2 & lookup.lookupModes())) {
                return lookup.unreflectSpecial(method, receiverType).bindTo(receiver);
            }
            return lookup.unreflect(method).bindTo(receiver);
        }
        catch (ReflectiveOperationException e) {
            return new GroovyRuntimeException(e);
        }
    }

    @Override
    public Object invokeHandle(Object handle, Object[] args) throws Throwable {
        if (handle instanceof ProxyDefaultMethodHandle) {
            return ((ProxyDefaultMethodHandle)handle).invokeWithArguments(args);
        }
        if (handle instanceof Throwable) {
            throw (Throwable)handle;
        }
        MethodHandle mh = (MethodHandle)handle;
        return mh.invokeWithArguments(args);
    }

    @Override
    protected void makeRecordComponents(CompileUnit cu, ClassNode classNode, Class<?> clazz) {
        if (!clazz.isRecord()) {
            return;
        }
        classNode.setRecordComponents(Arrays.stream(clazz.getRecordComponents()).map(rc -> {
            ClassNode type = this.makeClassNode(cu, rc.getGenericType(), rc.getType());
            type.addTypeAnnotations(Arrays.stream(rc.getAnnotatedType().getAnnotations()).map(annotation -> {
                AnnotationNode node = new AnnotationNode(ClassHelper.make(annotation.annotationType()));
                this.configureAnnotation(node, (Annotation)annotation);
                return node;
            }).collect(Collectors.toList()));
            return new RecordComponentNode(classNode, rc.getName(), type, Arrays.stream(rc.getAnnotations()).map(annotation -> {
                AnnotationNode node = new AnnotationNode(ClassHelper.make(annotation.annotationType()));
                this.configureAnnotation(node, (Annotation)annotation);
                return node;
            }).collect(Collectors.toList()));
        }).collect(Collectors.toList()));
    }

    @Override
    protected MethodHandles.Lookup newLookup(Class<?> declaringClass) {
        try {
            Method privateLookup = Java16.getPrivateLookup();
            if (privateLookup != null) {
                String pn;
                MethodHandles.Lookup caller = MethodHandles.lookup();
                Class<?> callerClass = caller.lookupClass();
                Module callerModule = callerClass.getModule();
                Module targetModule = declaringClass.getModule();
                if (targetModule != callerModule && targetModule.isNamed() && !targetModule.isOpen(pn = declaringClass.getPackageName(), callerModule)) {
                    return MethodHandles.lookup().in(declaringClass);
                }
                return (MethodHandles.Lookup)privateLookup.invoke(null, declaringClass, caller);
            }
            return Java16.getLookupConstructor().newInstance(declaringClass, 2).in(declaringClass);
        }
        catch (IllegalAccessException | InstantiationException e) {
            throw new IllegalArgumentException(e);
        }
        catch (InvocationTargetException e) {
            throw new GroovyRuntimeException(e);
        }
    }
}

