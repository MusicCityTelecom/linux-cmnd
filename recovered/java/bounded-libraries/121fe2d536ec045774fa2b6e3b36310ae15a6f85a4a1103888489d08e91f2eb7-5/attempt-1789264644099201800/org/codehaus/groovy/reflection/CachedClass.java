/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.reflection;

import groovy.lang.Closure;
import groovy.lang.ExpandoMetaClass;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.MetaClass;
import groovy.lang.MetaClassImpl;
import groovy.lang.MetaMethod;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.codehaus.groovy.classgen.asm.BytecodeHelper;
import org.codehaus.groovy.reflection.CachedConstructor;
import org.codehaus.groovy.reflection.CachedField;
import org.codehaus.groovy.reflection.CachedMethod;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.reflection.ReflectionCache;
import org.codehaus.groovy.reflection.ReflectionUtils;
import org.codehaus.groovy.runtime.callsite.CallSiteClassLoader;
import org.codehaus.groovy.runtime.metaclass.ClosureMetaClass;
import org.codehaus.groovy.util.FastArray;
import org.codehaus.groovy.util.LazyReference;
import org.codehaus.groovy.util.ReferenceBundle;

public class CachedClass {
    public static final CachedClass[] EMPTY_ARRAY = new CachedClass[0];
    private static ReferenceBundle softBundle = ReferenceBundle.getSoftBundle();
    private final LazyReference<CachedField[]> fields = new LazyReference<CachedField[]>(softBundle){
        private static final long serialVersionUID = 5450437842165410025L;

        @Override
        public CachedField[] initValue() {
            PrivilegedAction<CachedField[]> action = () -> (CachedField[])Arrays.stream(CachedClass.this.getTheClass().getDeclaredFields()).filter(f -> ReflectionUtils.checkCanSetAccessible(f, CachedClass.class)).map(CachedField::new).toArray(CachedField[]::new);
            return (CachedField[])CachedClass.doPrivileged(action);
        }
    };
    private LazyReference<CachedConstructor[]> constructors = new LazyReference<CachedConstructor[]>(softBundle){
        private static final long serialVersionUID = -5834446523983631635L;

        @Override
        public CachedConstructor[] initValue() {
            PrivilegedAction<CachedConstructor[]> action = () -> (CachedConstructor[])Arrays.stream(CachedClass.this.getTheClass().getDeclaredConstructors()).filter(c -> !c.isSynthetic()).filter(c -> ReflectionUtils.checkCanSetAccessible(c, CachedClass.class)).map(c -> new CachedConstructor(CachedClass.this, (Constructor)c)).toArray(CachedConstructor[]::new);
            return (CachedConstructor[])CachedClass.doPrivileged(action);
        }
    };
    private final LazyReference<CachedMethod[]> methods = new LazyReference<CachedMethod[]>(softBundle){
        private static final long serialVersionUID = 6347586066597418308L;

        @Override
        public CachedMethod[] initValue() {
            PrivilegedAction<CachedMethod[]> action = () -> {
                try {
                    return (CachedMethod[])Arrays.stream(CachedClass.this.getTheClass().getDeclaredMethods()).filter(m -> m.getName().indexOf(43) < 0).filter(m -> ReflectionUtils.checkCanSetAccessible(m, CachedClass.class)).map(m -> new CachedMethod(CachedClass.this, (Method)m)).toArray(CachedMethod[]::new);
                }
                catch (LinkageError e) {
                    return CachedMethod.EMPTY_ARRAY;
                }
            };
            CachedMethod[] declaredMethods = (CachedMethod[])CachedClass.doPrivileged(action);
            ArrayList<CachedMethod> methods = new ArrayList<CachedMethod>(declaredMethods.length);
            ArrayList<CachedMethod> mopMethods = new ArrayList<CachedMethod>(declaredMethods.length);
            for (CachedMethod method : declaredMethods) {
                String name = method.getName();
                if (name.startsWith("this$") || name.startsWith("super$")) {
                    mopMethods.add(method);
                    continue;
                }
                methods.add(method);
            }
            Collections.sort(methods);
            CachedClass superClass = CachedClass.this.getCachedSuperClass();
            if (superClass != null) {
                superClass.getMethods();
                Collections.addAll(mopMethods, superClass.mopMethods);
            }
            if (mopMethods.size() > 1) {
                mopMethods.sort(CachedMethodComparatorByName.INSTANCE);
            }
            CachedClass.this.mopMethods = mopMethods.toArray(CachedMethod.EMPTY_ARRAY);
            return methods.toArray(CachedMethod.EMPTY_ARRAY);
        }
    };
    private LazyReference<CachedClass> cachedSuperClass = new LazyReference<CachedClass>(softBundle){
        private static final long serialVersionUID = -4663740963306806058L;

        @Override
        public CachedClass initValue() {
            if (!CachedClass.this.isArray) {
                return ReflectionCache.getCachedClass(CachedClass.this.getTheClass().getSuperclass());
            }
            if (CachedClass.this.cachedClass.getComponentType().isPrimitive() || CachedClass.this.cachedClass.getComponentType() == Object.class) {
                return ReflectionCache.OBJECT_CLASS;
            }
            return ReflectionCache.OBJECT_ARRAY_CLASS;
        }
    };
    private final LazyReference<CallSiteClassLoader> callSiteClassLoader = new LazyReference<CallSiteClassLoader>(softBundle){
        private static final long serialVersionUID = 4410385968428074090L;

        @Override
        public CallSiteClassLoader initValue() {
            return (CallSiteClassLoader)CachedClass.doPrivileged(() -> new CallSiteClassLoader(CachedClass.this.cachedClass));
        }
    };
    private final LazyReference<Collection<ClassInfo>> hierarchy = new LazyReference<Collection<ClassInfo>>(softBundle){
        private static final long serialVersionUID = 7166687623678851596L;

        @Override
        public Collection<ClassInfo> initValue() {
            LinkedHashSet<ClassInfo> res = new LinkedHashSet<ClassInfo>();
            res.add(CachedClass.this.classInfo);
            for (CachedClass iface : CachedClass.this.getDeclaredInterfaces()) {
                res.addAll(iface.getHierarchy());
            }
            CachedClass superClass = CachedClass.this.getCachedSuperClass();
            if (superClass != null) {
                res.addAll(superClass.getHierarchy());
            }
            if (CachedClass.this.isInterface) {
                res.add(ReflectionCache.OBJECT_CLASS.classInfo);
            }
            return res;
        }
    };
    private final LazyReference<Set<CachedClass>> declaredInterfaces = new LazyReference<Set<CachedClass>>(softBundle){
        private static final long serialVersionUID = 2139190436931329873L;

        @Override
        public Set<CachedClass> initValue() {
            Class<?>[] classes;
            HashSet<CachedClass> res = new HashSet<CachedClass>(0);
            for (Class<?> cls : classes = CachedClass.this.getTheClass().getInterfaces()) {
                res.add(ReflectionCache.getCachedClass(cls));
            }
            return res;
        }
    };
    private final LazyReference<Set<CachedClass>> interfaces = new LazyReference<Set<CachedClass>>(softBundle){
        private static final long serialVersionUID = 4060471819464086940L;

        @Override
        public Set<CachedClass> initValue() {
            Class<?>[] classes;
            HashSet<CachedClass> res = new HashSet<CachedClass>(0);
            if (CachedClass.this.getTheClass().isInterface()) {
                res.add(CachedClass.this);
            }
            for (Class<?> cls : classes = CachedClass.this.getTheClass().getInterfaces()) {
                CachedClass aClass = ReflectionCache.getCachedClass(cls);
                if (res.contains(aClass)) continue;
                res.addAll(aClass.getInterfaces());
            }
            CachedClass superClass = CachedClass.this.getCachedSuperClass();
            if (superClass != null) {
                res.addAll(superClass.getInterfaces());
            }
            return res;
        }
    };
    private final Class<?> cachedClass;
    public ClassInfo classInfo;
    public final boolean isArray;
    public final boolean isPrimitive;
    public final int modifiers;
    public final boolean isInterface;
    public final boolean isNumber;
    public CachedMethod[] mopMethods;
    int distance = -1;
    int hashCode;

    private static <T> T doPrivileged(PrivilegedAction<T> action) {
        return AccessController.doPrivileged(action);
    }

    public CachedClass(Class<?> klazz, ClassInfo classInfo) {
        this.cachedClass = klazz;
        this.classInfo = classInfo;
        this.isArray = klazz.isArray();
        this.isPrimitive = klazz.isPrimitive();
        this.modifiers = klazz.getModifiers();
        this.isInterface = klazz.isInterface();
        this.isNumber = Number.class.isAssignableFrom(klazz);
        for (CachedClass inf : this.getInterfaces()) {
            ReflectionCache.isAssignableFrom(klazz, inf.cachedClass);
        }
        for (CachedClass cur = this; cur != null; cur = cur.getCachedSuperClass()) {
            ReflectionCache.setAssignableFrom(cur.cachedClass, klazz);
        }
    }

    public CachedClass getCachedSuperClass() {
        return this.cachedSuperClass.get();
    }

    public Set<CachedClass> getInterfaces() {
        return this.interfaces.get();
    }

    public Set<CachedClass> getDeclaredInterfaces() {
        return this.declaredInterfaces.get();
    }

    public CachedMethod[] getMethods() {
        return this.methods.get();
    }

    public CachedField[] getFields() {
        return this.fields.get();
    }

    public CachedConstructor[] getConstructors() {
        return this.constructors.get();
    }

    public CachedMethod searchMethods(String name, CachedClass[] parameterTypes) {
        CachedMethod[] methods = this.getMethods();
        CachedMethod res = null;
        for (CachedMethod m : methods) {
            if (!m.getName().equals(name) || !ReflectionCache.arrayContentsEq(parameterTypes, m.getParameterTypes()) || res != null && !res.getReturnType().isAssignableFrom(m.getReturnType())) continue;
            res = m;
        }
        return res;
    }

    public int getModifiers() {
        return this.modifiers;
    }

    public Object coerceArgument(Object argument) {
        return argument;
    }

    public int getSuperClassDistance() {
        if (this.distance >= 0) {
            return this.distance;
        }
        int distance = 0;
        for (Class klazz = this.getTheClass(); klazz != null; klazz = klazz.getSuperclass()) {
            ++distance;
        }
        this.distance = distance;
        return distance;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = super.hashCode();
            if (this.hashCode == 0) {
                this.hashCode = -889274690;
            }
        }
        return this.hashCode;
    }

    public boolean isPrimitive() {
        return this.isPrimitive;
    }

    public boolean isVoid() {
        return this.getTheClass() == Void.TYPE;
    }

    public boolean isInterface() {
        return this.isInterface;
    }

    public String getName() {
        return this.getTheClass().getName();
    }

    public String getTypeDescription() {
        return BytecodeHelper.getTypeDescription(this.getTheClass());
    }

    public final Class getTheClass() {
        return this.cachedClass;
    }

    public MetaMethod[] getNewMetaMethods() {
        ArrayList<MetaMethod> arr = new ArrayList<MetaMethod>(Arrays.asList(this.classInfo.newMetaMethods));
        MetaClass metaClass = this.classInfo.getStrongMetaClass();
        if (metaClass instanceof ExpandoMetaClass) {
            arr.addAll(((ExpandoMetaClass)metaClass).getExpandoMethods());
        }
        if (this.isInterface) {
            MetaClass mc = ReflectionCache.OBJECT_CLASS.classInfo.getStrongMetaClass();
            this.addSubclassExpandos(arr, mc);
        } else {
            for (CachedClass cls = this; cls != null; cls = cls.getCachedSuperClass()) {
                MetaClass mc = cls.classInfo.getStrongMetaClass();
                this.addSubclassExpandos(arr, mc);
            }
        }
        for (CachedClass inf : this.getInterfaces()) {
            MetaClass mc = inf.classInfo.getStrongMetaClass();
            this.addSubclassExpandos(arr, mc);
        }
        return arr.toArray(MetaMethod.EMPTY_ARRAY);
    }

    private void addSubclassExpandos(List<MetaMethod> arr, MetaClass mc) {
        if (mc instanceof ExpandoMetaClass) {
            ExpandoMetaClass emc = (ExpandoMetaClass)mc;
            for (Object mm : emc.getExpandoSubclassMethods()) {
                if (mm instanceof MetaMethod) {
                    MetaMethod method = (MetaMethod)mm;
                    if (method.getDeclaringClass() != this) continue;
                    arr.add(method);
                    continue;
                }
                FastArray farr = (FastArray)mm;
                for (int i = 0; i != farr.size; ++i) {
                    MetaMethod method = (MetaMethod)farr.get(i);
                    if (method.getDeclaringClass() != this) continue;
                    arr.add(method);
                }
            }
        }
    }

    public void setNewMopMethods(List<MetaMethod> arr) {
        MetaClass metaClass = this.classInfo.getStrongMetaClass();
        if (metaClass != null) {
            if (metaClass.getClass() == MetaClassImpl.class) {
                this.classInfo.setStrongMetaClass(null);
                this.updateSetNewMopMethods(arr);
                MetaClassImpl mci = new MetaClassImpl(metaClass.getTheClass());
                mci.initialize();
                this.classInfo.setStrongMetaClass(mci);
                return;
            }
            if (metaClass.getClass() == ExpandoMetaClass.class) {
                this.classInfo.setStrongMetaClass(null);
                this.updateSetNewMopMethods(arr);
                ExpandoMetaClass newEmc = new ExpandoMetaClass(metaClass.getTheClass());
                newEmc.initialize();
                this.classInfo.setStrongMetaClass(newEmc);
                return;
            }
            throw new GroovyRuntimeException("Can't add methods to class " + this.getTheClass().getName() + ". Strong custom meta class already set.");
        }
        this.classInfo.setWeakMetaClass(null);
        this.updateSetNewMopMethods(arr);
    }

    private void updateSetNewMopMethods(List<MetaMethod> arr) {
        if (arr != null) {
            MetaMethod[] metaMethods = arr.toArray(MetaMethod.EMPTY_ARRAY);
            this.classInfo.dgmMetaMethods = metaMethods;
            this.classInfo.newMetaMethods = metaMethods;
        } else {
            this.classInfo.newMetaMethods = this.classInfo.dgmMetaMethods;
        }
    }

    public void addNewMopMethods(List<MetaMethod> arr) {
        MetaClass metaClass = this.classInfo.getStrongMetaClass();
        if (metaClass != null) {
            if (metaClass.getClass() == MetaClassImpl.class) {
                this.classInfo.setStrongMetaClass(null);
                ArrayList<MetaMethod> res = new ArrayList<MetaMethod>();
                Collections.addAll(res, this.classInfo.newMetaMethods);
                res.addAll(arr);
                this.updateSetNewMopMethods(res);
                MetaClassImpl answer = new MetaClassImpl(((MetaClassImpl)metaClass).getRegistry(), metaClass.getTheClass());
                answer.initialize();
                this.classInfo.setStrongMetaClass(answer);
                return;
            }
            if (metaClass.getClass() == ExpandoMetaClass.class) {
                ExpandoMetaClass emc = (ExpandoMetaClass)metaClass;
                this.classInfo.setStrongMetaClass(null);
                this.updateAddNewMopMethods(arr);
                ExpandoMetaClass newEmc = new ExpandoMetaClass(metaClass.getTheClass());
                for (MetaMethod mm : emc.getExpandoMethods()) {
                    newEmc.registerInstanceMethod(mm);
                }
                newEmc.initialize();
                this.classInfo.setStrongMetaClass(newEmc);
                return;
            }
            throw new GroovyRuntimeException("Can't add methods to class " + this.getTheClass().getName() + ". Strong custom meta class already set.");
        }
        this.classInfo.setWeakMetaClass(null);
        this.updateAddNewMopMethods(arr);
    }

    private void updateAddNewMopMethods(List<MetaMethod> arr) {
        ArrayList<MetaMethod> res = new ArrayList<MetaMethod>();
        res.addAll(Arrays.asList(this.classInfo.newMetaMethods));
        res.addAll(arr);
        this.classInfo.newMetaMethods = res.toArray(MetaMethod.EMPTY_ARRAY);
        Class theClass = this.classInfo.getCachedClass().getTheClass();
        if (theClass == Closure.class || theClass == Class.class) {
            ClosureMetaClass.resetCachedMetaClasses();
        }
    }

    public boolean isAssignableFrom(Class argument) {
        return argument == null || ReflectionCache.isAssignableFrom(this.getTheClass(), argument);
    }

    public boolean isDirectlyAssignable(Object argument) {
        return ReflectionCache.isAssignableFrom(this.getTheClass(), argument.getClass());
    }

    public CallSiteClassLoader getCallSiteLoader() {
        return this.callSiteClassLoader.get();
    }

    public Collection<ClassInfo> getHierarchy() {
        return this.hierarchy.get();
    }

    public String toString() {
        return this.cachedClass.toString();
    }

    public CachedClass getCachedClass() {
        return this;
    }

    public static class CachedMethodComparatorWithString
    implements Comparator {
        public static final Comparator INSTANCE = new CachedMethodComparatorWithString();

        public int compare(Object o1, Object o2) {
            if (o1 instanceof CachedMethod) {
                return ((CachedMethod)o1).getName().compareTo((String)o2);
            }
            return ((String)o1).compareTo(((CachedMethod)o2).getName());
        }
    }

    public static class CachedMethodComparatorByName
    implements Comparator<CachedMethod> {
        public static final Comparator INSTANCE = new CachedMethodComparatorByName();

        @Override
        public int compare(CachedMethod o1, CachedMethod o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
}

