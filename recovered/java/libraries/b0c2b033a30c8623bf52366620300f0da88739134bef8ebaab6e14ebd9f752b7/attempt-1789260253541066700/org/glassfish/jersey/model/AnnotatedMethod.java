/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Encoded;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import org.glassfish.jersey.internal.util.ReflectionHelper;

public class AnnotatedMethod
implements AnnotatedElement {
    private static final Set<Class<? extends Annotation>> METHOD_META_ANNOTATIONS = AnnotatedMethod.getSet(HttpMethod.class);
    private static final Set<Class<? extends Annotation>> METHOD_ANNOTATIONS = AnnotatedMethod.getSet(Path.class, Produces.class, Consumes.class);
    private static final Set<Class<? extends Annotation>> PARAMETER_ANNOTATIONS = AnnotatedMethod.getSet(Context.class, Encoded.class, DefaultValue.class, MatrixParam.class, QueryParam.class, CookieParam.class, HeaderParam.class, PathParam.class, FormParam.class);
    private final Method m;
    private final Method am;
    private final Annotation[] methodAnnotations;
    private final Annotation[][] parameterAnnotations;

    @SafeVarargs
    private static Set<Class<? extends Annotation>> getSet(Class<? extends Annotation> ... cs) {
        HashSet<Class<? extends Annotation>> s = new HashSet<Class<? extends Annotation>>();
        s.addAll(Arrays.asList(cs));
        return s;
    }

    public AnnotatedMethod(Method method) {
        this.m = method;
        this.am = AnnotatedMethod.findAnnotatedMethod(method);
        if (method.equals(this.am)) {
            this.methodAnnotations = method.getAnnotations();
            this.parameterAnnotations = method.getParameterAnnotations();
        } else {
            this.methodAnnotations = AnnotatedMethod.mergeMethodAnnotations(method, this.am);
            this.parameterAnnotations = AnnotatedMethod.mergeParameterAnnotations(method, this.am);
        }
    }

    public Method getMethod() {
        return this.am;
    }

    public Method getDeclaredMethod() {
        return this.m;
    }

    public Annotation[][] getParameterAnnotations() {
        return (Annotation[][])this.parameterAnnotations.clone();
    }

    public Class<?>[] getParameterTypes() {
        return this.am.getParameterTypes();
    }

    public TypeVariable<Method>[] getTypeParameters() {
        return this.am.getTypeParameters();
    }

    public Type[] getGenericParameterTypes() {
        return this.am.getGenericParameterTypes();
    }

    public <T extends Annotation> List<T> getMetaMethodAnnotations(Class<T> annotation) {
        ArrayList<T> ma = new ArrayList<T>();
        for (Annotation a : this.methodAnnotations) {
            T metaAnnotation = a.annotationType().getAnnotation(annotation);
            if (metaAnnotation == null) continue;
            ma.add(metaAnnotation);
        }
        return ma;
    }

    public String toString() {
        return this.m.toString();
    }

    @Override
    public boolean isAnnotationPresent(Class<? extends Annotation> annotationType) {
        for (Annotation ma : this.methodAnnotations) {
            if (ma.annotationType() != annotationType) continue;
            return true;
        }
        return false;
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
        for (Annotation ma : this.methodAnnotations) {
            if (ma.annotationType() != annotationType) continue;
            return (T)((Annotation)annotationType.cast(ma));
        }
        return this.am.getAnnotation(annotationType);
    }

    @Override
    public Annotation[] getAnnotations() {
        return (Annotation[])this.methodAnnotations.clone();
    }

    @Override
    public Annotation[] getDeclaredAnnotations() {
        return this.getAnnotations();
    }

    private static Annotation[] mergeMethodAnnotations(Method m, Method am) {
        List<Annotation> al = AnnotatedMethod.asList(m.getAnnotations());
        for (Annotation a : am.getAnnotations()) {
            if (m.isAnnotationPresent(a.getClass())) continue;
            al.add(a);
        }
        return al.toArray(new Annotation[al.size()]);
    }

    private static Annotation[][] mergeParameterAnnotations(Method m, Method am) {
        Annotation[][] methodParamAnnotations = m.getParameterAnnotations();
        Annotation[][] annotatedMethodParamAnnotations = am.getParameterAnnotations();
        ArrayList<List<Annotation>> methodParamAnnotationsList = new ArrayList<List<Annotation>>();
        for (int i = 0; i < methodParamAnnotations.length; ++i) {
            List<Annotation> al = AnnotatedMethod.asList(methodParamAnnotations[i]);
            for (Annotation a : annotatedMethodParamAnnotations[i]) {
                if (!AnnotatedMethod.annotationNotInList(a.getClass(), al)) continue;
                al.add(a);
            }
            methodParamAnnotationsList.add(al);
        }
        Annotation[][] mergedAnnotations = new Annotation[methodParamAnnotations.length][];
        for (int i = 0; i < methodParamAnnotations.length; ++i) {
            List paramAnnotations = (List)methodParamAnnotationsList.get(i);
            mergedAnnotations[i] = paramAnnotations.toArray(new Annotation[paramAnnotations.size()]);
        }
        return mergedAnnotations;
    }

    private static boolean annotationNotInList(Class<? extends Annotation> ca, List<Annotation> la) {
        for (Annotation a : la) {
            if (ca != a.getClass()) continue;
            return false;
        }
        return true;
    }

    private static Method findAnnotatedMethod(Method m) {
        Method am = AnnotatedMethod.findAnnotatedMethod(m.getDeclaringClass(), m);
        return am != null ? am : m;
    }

    private static Method findAnnotatedMethod(Class<?> c, Method m) {
        Method sm;
        if (c == Object.class) {
            return null;
        }
        if ((m = AccessController.doPrivileged(ReflectionHelper.findMethodOnClassPA(c, m))) == null) {
            return null;
        }
        if (AnnotatedMethod.hasAnnotations(m)) {
            return m;
        }
        Class<?> sc = c.getSuperclass();
        if (sc != null && sc != Object.class && (sm = AnnotatedMethod.findAnnotatedMethod(sc, m)) != null) {
            return sm;
        }
        for (Class<?> ic : c.getInterfaces()) {
            Method im = AnnotatedMethod.findAnnotatedMethod(ic, m);
            if (im == null) continue;
            return im;
        }
        return null;
    }

    private static boolean hasAnnotations(Method m) {
        return AnnotatedMethod.hasMetaMethodAnnotations(m) || AnnotatedMethod.hasMethodAnnotations(m) || AnnotatedMethod.hasParameterAnnotations(m);
    }

    private static boolean hasMetaMethodAnnotations(Method m) {
        for (Class<? extends Annotation> ac : METHOD_META_ANNOTATIONS) {
            for (Annotation a : m.getAnnotations()) {
                if (a.annotationType().getAnnotation(ac) == null) continue;
                return true;
            }
        }
        return false;
    }

    private static boolean hasMethodAnnotations(Method m) {
        for (Class<? extends Annotation> ac : METHOD_ANNOTATIONS) {
            if (!m.isAnnotationPresent(ac)) continue;
            return true;
        }
        return false;
    }

    private static boolean hasParameterAnnotations(Method m) {
        Annotation[][] annotationArray = m.getParameterAnnotations();
        int n = annotationArray.length;
        for (int i = 0; i < n; ++i) {
            Annotation[] as;
            for (Annotation a : as = annotationArray[i]) {
                if (!PARAMETER_ANNOTATIONS.contains(a.annotationType())) continue;
                return true;
            }
        }
        return false;
    }

    @SafeVarargs
    private static <T> List<T> asList(T ... ts) {
        ArrayList<T> l = new ArrayList<T>();
        l.addAll(Arrays.asList(ts));
        return l;
    }
}

