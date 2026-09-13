/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.vmplugin.v8;

import groovy.lang.GroovyObject;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.MetaClass;
import groovy.lang.MetaMethod;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.ReflectPermission;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.security.AccessController;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.groovy.lang.GroovyObjectHelper;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.CompileUnit;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.PackageNode;
import org.codehaus.groovy.ast.expr.AnnotationConstantExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.reflection.ReflectionUtils;
import org.codehaus.groovy.runtime.MetaClassHelper;
import org.codehaus.groovy.vmplugin.VMPlugin;
import org.codehaus.groovy.vmplugin.VMPluginFactory;
import org.codehaus.groovy.vmplugin.v8.IndyInterface;
import org.codehaus.groovy.vmplugin.v8.PluginDefaultGroovyMethods;

public class Java8
implements VMPlugin {
    private static final Method[] EMPTY_METHOD_ARRAY = new Method[0];
    private static final Annotation[] EMPTY_ANNOTATION_ARRAY = new Annotation[0];
    private static final Permission ACCESS_PERMISSION = new ReflectPermission("suppressAccessChecks");

    public static GenericsType configureTypeVariableDefinition(ClassNode base, ClassNode[] cBounds) {
        GenericsType gt;
        ClassNode redirect = base.redirect();
        base.setRedirect(null);
        if (cBounds == null || cBounds.length == 0) {
            gt = new GenericsType(base);
        } else {
            gt = new GenericsType(base, cBounds, null);
            gt.setName(base.getName());
            gt.setPlaceholder(true);
        }
        base.setRedirect(redirect);
        return gt;
    }

    private static ClassNode configureClass(Class<?> c) {
        if (c.isPrimitive()) {
            return ClassHelper.make(c);
        }
        return ClassHelper.makeWithoutCaching(c, false);
    }

    public static ClassNode configureTypeVariableReference(String name) {
        ClassNode cn = ClassHelper.makeWithoutCaching(name);
        cn.setGenericsPlaceHolder(true);
        ClassNode cn2 = ClassHelper.makeWithoutCaching(name);
        cn2.setGenericsPlaceHolder(true);
        GenericsType[] gts = new GenericsType[]{new GenericsType(cn2)};
        cn.setGenericsTypes(gts);
        cn.setRedirect(ClassHelper.OBJECT_TYPE);
        return cn;
    }

    private static void setRetentionPolicy(RetentionPolicy value, AnnotationNode node) {
        switch (value) {
            case RUNTIME: {
                node.setRuntimeRetention(true);
                break;
            }
            case SOURCE: {
                node.setSourceRetention(true);
                break;
            }
            case CLASS: {
                node.setClassRetention(true);
                break;
            }
            default: {
                throw new GroovyBugError("unsupported Retention " + (Object)((Object)value));
            }
        }
    }

    private static void setMethodDefaultValue(MethodNode mn, Method m) {
        ConstantExpression cExp = new ConstantExpression(m.getDefaultValue());
        mn.setCode(new ReturnStatement(cExp));
        mn.setAnnotationDefault(true);
    }

    public Class<?>[] getPluginDefaultGroovyMethods() {
        return new Class[]{PluginDefaultGroovyMethods.class};
    }

    public Class<?>[] getPluginStaticGroovyMethods() {
        return MetaClassHelper.EMPTY_TYPE_ARRAY;
    }

    @Override
    public int getVersion() {
        return 8;
    }

    protected int getElementCode(ElementType value) {
        switch (value) {
            case TYPE: {
                return 65;
            }
            case CONSTRUCTOR: {
                return 2;
            }
            case METHOD: {
                return 4;
            }
            case FIELD: {
                return 8;
            }
            case PARAMETER: {
                return 16;
            }
            case LOCAL_VARIABLE: {
                return 32;
            }
            case ANNOTATION_TYPE: {
                return 64;
            }
            case PACKAGE: {
                return 128;
            }
            case TYPE_PARAMETER: {
                return 256;
            }
            case TYPE_USE: {
                return 512;
            }
        }
        String name = value.name();
        if ("MODULE".equals(name)) {
            return 65;
        }
        if ("RECORD_COMPONENT".equals(name)) {
            return 1024;
        }
        throw new GroovyBugError("unsupported Target " + (Object)((Object)value));
    }

    @Override
    public void setAdditionalClassInformation(ClassNode cn) {
        this.setGenericsTypes(cn);
    }

    private void setGenericsTypes(ClassNode cn) {
        TypeVariable[] tvs = cn.getTypeClass().getTypeParameters();
        GenericsType[] gts = this.configureTypeVariable(tvs);
        cn.setGenericsTypes(gts);
    }

    private GenericsType[] configureTypeVariable(TypeVariable[] tvs) {
        int n = tvs.length;
        if (n == 0) {
            return null;
        }
        GenericsType[] gts = new GenericsType[n];
        for (int i = 0; i < n; ++i) {
            gts[i] = this.configureTypeVariableDefinition(tvs[i]);
        }
        return gts;
    }

    private GenericsType configureTypeVariableDefinition(TypeVariable tv) {
        return Java8.configureTypeVariableDefinition(Java8.configureTypeVariableReference(tv.getName()), this.configureTypes(tv.getBounds()));
    }

    private ClassNode[] configureTypes(Type[] types) {
        int n = types.length;
        if (n == 0) {
            return null;
        }
        ClassNode[] nodes = new ClassNode[n];
        for (int i = 0; i < n; ++i) {
            nodes[i] = this.configureType(types[i]);
        }
        return nodes;
    }

    private ClassNode configureType(Type type) {
        if (type instanceof WildcardType) {
            return this.configureWildcardType((WildcardType)type);
        }
        if (type instanceof ParameterizedType) {
            return this.configureParameterizedType((ParameterizedType)type);
        }
        if (type instanceof GenericArrayType) {
            return this.configureGenericArray((GenericArrayType)type);
        }
        if (type instanceof TypeVariable) {
            return Java8.configureTypeVariableReference(((TypeVariable)type).getName());
        }
        if (type instanceof Class) {
            return Java8.configureClass((Class)type);
        }
        if (type == null) {
            throw new GroovyBugError("Type is null. Most probably you let a transform reuse existing ClassNodes with generics information, that is now used in a wrong context.");
        }
        throw new GroovyBugError("unknown type: " + type + " := " + type.getClass());
    }

    private ClassNode configureGenericArray(GenericArrayType genericArrayType) {
        Type component = genericArrayType.getGenericComponentType();
        ClassNode node = this.configureType(component);
        return node.makeArray();
    }

    private ClassNode configureWildcardType(WildcardType wildcardType) {
        ClassNode base = ClassHelper.makeWithoutCaching("?");
        base.setRedirect(ClassHelper.OBJECT_TYPE);
        ClassNode[] lowers = this.configureTypes(wildcardType.getLowerBounds());
        ClassNode[] uppers = this.configureTypes(wildcardType.getUpperBounds());
        if (lowers != null || wildcardType.getTypeName().equals("?")) {
            uppers = null;
        }
        GenericsType gt = new GenericsType(base, uppers, lowers != null ? lowers[0] : null);
        gt.setWildcard(true);
        ClassNode wt = ClassHelper.makeWithoutCaching(Object.class, false);
        wt.setGenericsTypes(new GenericsType[]{gt});
        return wt;
    }

    private ClassNode configureParameterizedType(ParameterizedType parameterizedType) {
        ClassNode base = this.configureType(parameterizedType.getRawType());
        GenericsType[] gts = this.configureTypeArguments(parameterizedType.getActualTypeArguments());
        base.setGenericsTypes(gts);
        return base;
    }

    private GenericsType[] configureTypeArguments(Type[] ta) {
        int n = ta.length;
        if (n == 0) {
            return null;
        }
        GenericsType[] gts = new GenericsType[n];
        for (int i = 0; i < n; ++i) {
            ClassNode t = this.configureType(ta[i]);
            if (ta[i] instanceof WildcardType) {
                GenericsType[] gen = t.getGenericsTypes();
                gts[i] = gen[0];
                continue;
            }
            gts[i] = new GenericsType(t);
        }
        return gts;
    }

    @Override
    public void configureAnnotation(AnnotationNode node) {
        ClassNode type = node.getClassNode();
        VMPlugin plugin = VMPluginFactory.getPlugin();
        List<AnnotationNode> annotations = type.getAnnotations();
        for (AnnotationNode an : annotations) {
            plugin.configureAnnotationNodeFromDefinition(an, node);
        }
        if (!node.getClassNode().getName().equals("java.lang.annotation.Retention")) {
            plugin.configureAnnotationNodeFromDefinition(node, node);
        }
    }

    protected void configureAnnotation(AnnotationNode node, Annotation annotation) {
        Class<? extends Annotation> type = annotation.annotationType();
        if (type == Retention.class) {
            Retention r = (Retention)annotation;
            RetentionPolicy value = r.value();
            Java8.setRetentionPolicy(value, node);
            node.setMember("value", new PropertyExpression((Expression)new ClassExpression(ClassHelper.makeWithoutCaching(RetentionPolicy.class, false)), value.toString()));
        } else if (type == Target.class) {
            Target t = (Target)annotation;
            ElementType[] elements = t.value();
            ListExpression elementExprs = new ListExpression();
            for (ElementType element : elements) {
                elementExprs.addExpression(new PropertyExpression((Expression)new ClassExpression(ClassHelper.ELEMENT_TYPE_TYPE), element.name()));
            }
            node.setMember("value", elementExprs);
        } else {
            Method[] declaredMethods;
            try {
                declaredMethods = type.getDeclaredMethods();
            }
            catch (SecurityException se) {
                declaredMethods = EMPTY_METHOD_ARRAY;
            }
            for (Method declaredMethod : declaredMethods) {
                try {
                    Object value = declaredMethod.invoke(annotation, new Object[0]);
                    Expression valueExpression = this.toAnnotationValueExpression(value);
                    if (valueExpression == null) continue;
                    node.setMember(declaredMethod.getName(), valueExpression);
                }
                catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
                    // empty catch block
                }
            }
        }
    }

    private void setAnnotationMetaData(Annotation[] annotations, AnnotatedNode target) {
        for (Annotation annotation : annotations) {
            target.addAnnotation(this.toAnnotationNode(annotation));
        }
    }

    private AnnotationNode toAnnotationNode(Annotation annotation) {
        ClassNode type = ClassHelper.make(annotation.annotationType());
        AnnotationNode node = new AnnotationNode(type);
        this.configureAnnotation(node, annotation);
        return node;
    }

    private Expression toAnnotationValueExpression(Object value) {
        if (value == null || value instanceof String || value instanceof Number || value instanceof Character || value instanceof Boolean) {
            return new ConstantExpression(value);
        }
        if (value instanceof Class) {
            return new ClassExpression(ClassHelper.makeWithoutCaching((Class)value));
        }
        if (value instanceof Annotation) {
            return new AnnotationConstantExpression(this.toAnnotationNode((Annotation)value));
        }
        if (value instanceof Enum) {
            return new PropertyExpression((Expression)new ClassExpression(ClassHelper.makeWithoutCaching(value.getClass())), value.toString());
        }
        if (value.getClass().isArray()) {
            ListExpression list = new ListExpression();
            int n = Array.getLength(value);
            for (int i = 0; i < n; ++i) {
                list.addExpression(this.toAnnotationValueExpression(Array.get(value, i)));
            }
            return list;
        }
        return null;
    }

    @Override
    public void configureAnnotationNodeFromDefinition(AnnotationNode definition, AnnotationNode root) {
        ClassNode type = definition.getClassNode();
        String typeName = type.getName();
        if ("java.lang.annotation.Retention".equals(typeName)) {
            Expression exp = definition.getMember("value");
            if (!(exp instanceof PropertyExpression)) {
                return;
            }
            PropertyExpression pe = (PropertyExpression)exp;
            String name = pe.getPropertyAsString();
            RetentionPolicy policy = RetentionPolicy.valueOf(name);
            Java8.setRetentionPolicy(policy, root);
        } else if ("java.lang.annotation.Target".equals(typeName)) {
            Expression exp = definition.getMember("value");
            if (!(exp instanceof ListExpression)) {
                return;
            }
            ListExpression le = (ListExpression)exp;
            int bitmap = 0;
            for (Expression e : le.getExpressions()) {
                if (!(e instanceof PropertyExpression)) {
                    return;
                }
                PropertyExpression element = (PropertyExpression)e;
                String name = element.getPropertyAsString();
                ElementType value = ElementType.valueOf(name);
                bitmap |= this.getElementCode(value);
            }
            root.setAllowedTargets(bitmap);
        }
    }

    @Override
    public void configureClassNode(CompileUnit compileUnit, ClassNode classNode) {
        try {
            Constructor<?>[] constructors;
            ClassNode[] exceptions;
            org.codehaus.groovy.ast.Parameter[] params;
            Method[] methods;
            Field[] fields;
            Class clazz = classNode.getTypeClass();
            for (Field f : fields = clazz.getDeclaredFields()) {
                ClassNode ret = this.makeClassNode(compileUnit, f.getGenericType(), f.getType());
                FieldNode fn = new FieldNode(f.getName(), f.getModifiers(), ret, classNode, null);
                this.setAnnotationMetaData(f.getAnnotations(), fn);
                classNode.addField(fn);
            }
            for (Method m : methods = clazz.getDeclaredMethods()) {
                ClassNode ret = this.makeClassNode(compileUnit, m.getGenericReturnType(), m.getReturnType());
                params = this.makeParameters(compileUnit, m.getGenericParameterTypes(), m.getParameterTypes(), m.getParameterAnnotations(), m);
                exceptions = this.makeClassNodes(compileUnit, m.getGenericExceptionTypes(), m.getExceptionTypes());
                MethodNode mn = new MethodNode(m.getName(), m.getModifiers(), ret, params, exceptions, null);
                mn.setSynthetic(m.isSynthetic());
                Java8.setMethodDefaultValue(mn, m);
                this.setAnnotationMetaData(m.getAnnotations(), mn);
                mn.setGenericsTypes(this.configureTypeVariable(m.getTypeParameters()));
                classNode.addMethod(mn);
            }
            for (Constructor<?> ctor : constructors = clazz.getDeclaredConstructors()) {
                params = this.makeParameters(compileUnit, ctor.getGenericParameterTypes(), ctor.getParameterTypes(), this.getConstructorParameterAnnotations(ctor), ctor);
                exceptions = this.makeClassNodes(compileUnit, ctor.getGenericExceptionTypes(), ctor.getExceptionTypes());
                ConstructorNode cn = classNode.addConstructor(ctor.getModifiers(), params, exceptions, null);
                this.setAnnotationMetaData(ctor.getAnnotations(), cn);
            }
            Class sc = clazz.getSuperclass();
            if (sc != null) {
                classNode.setUnresolvedSuperClass(this.makeClassNode(compileUnit, clazz.getGenericSuperclass(), sc));
            }
            this.makeInterfaceTypes(compileUnit, classNode, clazz);
            this.makePermittedSubclasses(compileUnit, classNode, clazz);
            this.makeRecordComponents(compileUnit, classNode, clazz);
            this.setAnnotationMetaData(clazz.getAnnotations(), classNode);
            PackageNode packageNode = classNode.getPackage();
            if (packageNode != null) {
                this.setAnnotationMetaData(clazz.getPackage().getAnnotations(), packageNode);
            }
        }
        catch (NoClassDefFoundError e) {
            throw new NoClassDefFoundError("Unable to load class " + classNode.toString(false) + " due to missing dependency " + e.getMessage());
        }
        catch (MalformedParameterizedTypeException e) {
            throw new RuntimeException("Unable to configure class node for class " + classNode.toString(false) + " due to malformed parameterized types", e);
        }
    }

    private Annotation[][] getConstructorParameterAnnotations(Constructor<?> constructor) {
        Annotation[][] annotations;
        int parameterCount = constructor.getParameterTypes().length;
        int diff = parameterCount - (annotations = constructor.getParameterAnnotations()).length;
        if (diff > 0) {
            if (!constructor.getDeclaringClass().isEnum() && diff > 1 || diff > 2) {
                throw new GroovyBugError("Constructor parameter annotations length [" + annotations.length + "] does not match the parameter length: " + constructor);
            }
            Annotation[][] adjusted = new Annotation[parameterCount][];
            for (int i = 0; i < diff; ++i) {
                adjusted[i] = EMPTY_ANNOTATION_ARRAY;
            }
            System.arraycopy(annotations, 0, adjusted, diff, annotations.length);
            return adjusted;
        }
        return annotations;
    }

    private void makePermittedSubclasses(CompileUnit cu, ClassNode classNode, Class<?> clazz) {
        if (!ReflectionUtils.isSealed(clazz)) {
            return;
        }
        List<ClassNode> permittedSubclasses = Arrays.stream(ReflectionUtils.getPermittedSubclasses(clazz)).map(c -> this.makeClassNode(cu, (Type)c, (Class<?>)c)).collect(Collectors.toList());
        classNode.setPermittedSubclasses(permittedSubclasses);
    }

    protected void makeRecordComponents(CompileUnit cu, ClassNode classNode, Class<?> clazz) {
    }

    private void makeInterfaceTypes(CompileUnit cu, ClassNode classNode, Class<?> clazz) {
        Type[] interfaceTypes = clazz.getGenericInterfaces();
        int n = interfaceTypes.length;
        if (n == 0) {
            classNode.setInterfaces(ClassNode.EMPTY_ARRAY);
        } else {
            ClassNode[] ret = new ClassNode[n];
            for (int i = 0; i < n; ++i) {
                Type type = interfaceTypes[i];
                while (!(type instanceof Class)) {
                    ParameterizedType pt = (ParameterizedType)type;
                    Type t2 = pt.getRawType();
                    if (t2 == type) {
                        throw new GroovyBugError("Cannot transform generic signature of " + clazz + " with generic interface " + interfaceTypes[i] + " to a class.");
                    }
                    type = t2;
                }
                ret[i] = this.makeClassNode(cu, interfaceTypes[i], (Class)type);
            }
            classNode.setInterfaces(ret);
        }
    }

    private ClassNode[] makeClassNodes(CompileUnit cu, Type[] types, Class<?>[] cls) {
        int n = types.length;
        ClassNode[] nodes = new ClassNode[n];
        for (int i = 0; i < n; ++i) {
            nodes[i] = this.makeClassNode(cu, types[i], cls[i]);
        }
        return nodes;
    }

    protected ClassNode makeClassNode(CompileUnit cu, Type t, Class<?> c) {
        ClassNode back = null;
        if (cu != null) {
            back = cu.getClass(c.getName());
        }
        if (back == null) {
            back = ClassHelper.make(c);
        }
        if (!(t instanceof Class)) {
            ClassNode front = this.configureType(t);
            front.setRedirect(back);
            return front;
        }
        return back.getPlainNodeReference();
    }

    private org.codehaus.groovy.ast.Parameter[] makeParameters(CompileUnit cu, Type[] types, Class<?>[] cls, Annotation[][] parameterAnnotations, Member member) {
        org.codehaus.groovy.ast.Parameter[] params = org.codehaus.groovy.ast.Parameter.EMPTY_ARRAY;
        int n = types.length;
        if (n > 0) {
            params = new org.codehaus.groovy.ast.Parameter[n];
            String[] names = new String[n];
            this.fillParameterNames(names, member);
            for (int i = 0; i < n; ++i) {
                params[i] = new org.codehaus.groovy.ast.Parameter(this.makeClassNode(cu, types[i], cls[i]), names[i]);
                this.setAnnotationMetaData(parameterAnnotations[i], params[i]);
            }
        }
        return params;
    }

    protected void fillParameterNames(String[] names, Member member) {
        try {
            Parameter[] parameters = ((Executable)member).getParameters();
            int n = names.length;
            for (int i = 0; i < n; ++i) {
                names[i] = parameters[i].getName();
            }
        }
        catch (RuntimeException e) {
            throw new GroovyBugError(e);
        }
    }

    @Override
    public boolean checkCanSetAccessible(AccessibleObject accessibleObject, Class<?> callerClass) {
        Constructor c;
        SecurityManager sm = System.getSecurityManager();
        try {
            if (sm != null) {
                sm.checkPermission(ACCESS_PERMISSION);
            }
        }
        catch (SecurityException e) {
            return false;
        }
        return !(accessibleObject instanceof Constructor) || (c = (Constructor)accessibleObject).getDeclaringClass() != Class.class;
    }

    @Override
    public boolean checkAccessible(Class<?> callerClass, Class<?> declaringClass, int memberModifiers, boolean allowIllegalAccess) {
        return true;
    }

    @Override
    public boolean trySetAccessible(AccessibleObject ao) {
        try {
            ao.setAccessible(true);
            return true;
        }
        catch (SecurityException e) {
            throw e;
        }
        catch (Throwable t) {
            return false;
        }
    }

    @Override
    public MetaMethod transformMetaMethod(MetaClass metaClass, MetaMethod metaMethod, Class<?> caller) {
        return metaMethod;
    }

    @Override
    @Deprecated
    public <T> T doPrivileged(PrivilegedAction<T> action) {
        throw new UnsupportedOperationException("doPrivileged is no longer supported");
    }

    @Override
    @Deprecated
    public <T> T doPrivileged(PrivilegedExceptionAction<T> action) throws PrivilegedActionException {
        throw new UnsupportedOperationException("doPrivileged is no longer supported");
    }

    @Override
    public MetaMethod transformMetaMethod(MetaClass metaClass, MetaMethod metaMethod) {
        return this.transformMetaMethod(metaClass, metaMethod, null);
    }

    @Override
    public void invalidateCallSites() {
        IndyInterface.invalidateSwitchPoints();
    }

    protected MethodHandles.Lookup getLookup(Object receiver) {
        Optional<MethodHandles.Lookup> lookup = Optional.empty();
        if (receiver instanceof GroovyObject) {
            lookup = GroovyObjectHelper.lookup((GroovyObject)receiver);
        }
        return lookup.orElseGet(() -> this.newLookup(receiver.getClass()));
    }

    @Override
    public Object getInvokeSpecialHandle(Method method, Object receiver) {
        try {
            return this.getLookup(receiver).unreflectSpecial(method, receiver.getClass()).bindTo(receiver);
        }
        catch (ReflectiveOperationException e) {
            return this.getInvokeSpecialHandleFallback(method, receiver);
        }
    }

    private Object getInvokeSpecialHandleFallback(Method method, Object receiver) {
        if (!method.isAccessible()) {
            Java8.doPrivilegedInternal(() -> {
                ReflectionUtils.trySetAccessible(method);
                return null;
            });
        }
        Class<?> declaringClass = method.getDeclaringClass();
        try {
            return this.newLookup(declaringClass).unreflectSpecial(method, declaringClass).bindTo(receiver);
        }
        catch (ReflectiveOperationException e) {
            throw new GroovyBugError(e);
        }
    }

    private static <T> T doPrivilegedInternal(PrivilegedAction<T> action) {
        return AccessController.doPrivileged(action);
    }

    @Override
    public Object invokeHandle(Object handle, Object[] args) throws Throwable {
        return ((MethodHandle)handle).invokeWithArguments(args);
    }

    protected MethodHandles.Lookup newLookup(Class<?> declaringClass) {
        return Java8.of(declaringClass);
    }

    public static MethodHandles.Lookup of(Class<?> declaringClass) {
        try {
            return ((MethodHandles.Lookup)LookupHolder.LOOKUP_Constructor.newInstance(declaringClass, 2)).in(declaringClass);
        }
        catch (IllegalAccessException | InstantiationException e) {
            throw new IllegalArgumentException(e);
        }
        catch (InvocationTargetException e) {
            throw new GroovyRuntimeException(e);
        }
    }

    private static class LookupHolder {
        private static final Constructor<MethodHandles.Lookup> LOOKUP_Constructor;

        private LookupHolder() {
        }

        static {
            Constructor lookup;
            try {
                lookup = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, Integer.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new IllegalStateException("Incompatible JVM", e);
            }
            try {
                if (!lookup.isAccessible()) {
                    Constructor finalReference = lookup;
                    Java8.doPrivilegedInternal(() -> {
                        ReflectionUtils.trySetAccessible(finalReference);
                        return null;
                    });
                }
            }
            catch (SecurityException ignore) {
                lookup = null;
            }
            catch (RuntimeException e) {
                throw e;
            }
            LOOKUP_Constructor = lookup;
        }
    }
}

