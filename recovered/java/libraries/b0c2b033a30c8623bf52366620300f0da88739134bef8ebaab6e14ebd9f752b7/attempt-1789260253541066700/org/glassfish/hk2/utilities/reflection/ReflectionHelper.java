/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities.reflection;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Named;
import javax.inject.Qualifier;
import javax.inject.Scope;
import org.glassfish.hk2.utilities.general.GeneralUtilities;
import org.glassfish.hk2.utilities.reflection.Constants;
import org.glassfish.hk2.utilities.reflection.GenericArrayTypeImpl;
import org.glassfish.hk2.utilities.reflection.Logger;
import org.glassfish.hk2.utilities.reflection.MethodWrapper;
import org.glassfish.hk2.utilities.reflection.ParameterizedTypeImpl;
import org.glassfish.hk2.utilities.reflection.Pretty;
import org.glassfish.hk2.utilities.reflection.internal.MethodWrapperImpl;

public final class ReflectionHelper {
    private static final HashSet<Character> ESCAPE_CHARACTERS = new HashSet();
    private static final char[] ILLEGAL_CHARACTERS = new char[]{'{', '}', '[', ']', ':', ';', '=', ',', '\\'};
    private static final HashMap<Character, Character> REPLACE_CHARACTERS = new HashMap();
    private static final String EQUALS_STRING = "=";
    private static final String COMMA_STRING = ",";
    private static final String QUOTE_STRING = "\"";

    public static Class<?> getRawClass(Type type) {
        Type rawType;
        if (type == null) {
            return null;
        }
        if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType)type).getGenericComponentType();
            if (!(componentType instanceof ParameterizedType) && !(componentType instanceof Class)) {
                return null;
            }
            Class<?> rawComponentClass = ReflectionHelper.getRawClass(componentType);
            String forNameName = "[L" + rawComponentClass.getName() + ";";
            try {
                return Class.forName(forNameName);
            }
            catch (Throwable th) {
                return null;
            }
        }
        if (type instanceof Class) {
            return (Class)type;
        }
        if (type instanceof ParameterizedType && (rawType = ((ParameterizedType)type).getRawType()) instanceof Class) {
            return (Class)rawType;
        }
        return null;
    }

    public static Type resolveField(Class<?> topclass, Field field) {
        return ReflectionHelper.resolveMember(topclass, field.getGenericType(), field.getDeclaringClass());
    }

    public static Type resolveMember(Class<?> topclass, Type lookingForType, Class<?> declaringClass) {
        Map<String, Type> typeArguments = ReflectionHelper.typesFromSubClassToDeclaringClass(topclass, declaringClass);
        if (typeArguments == null) {
            return lookingForType;
        }
        if (lookingForType instanceof ParameterizedType) {
            return ReflectionHelper.fixTypeVariables((ParameterizedType)lookingForType, typeArguments);
        }
        if (lookingForType instanceof GenericArrayType) {
            return ReflectionHelper.fixGenericArrayTypeVariables((GenericArrayType)lookingForType, typeArguments);
        }
        if (!(lookingForType instanceof TypeVariable)) {
            return lookingForType;
        }
        TypeVariable tv = (TypeVariable)lookingForType;
        String typeVariableName = tv.getName();
        Type retVal = typeArguments.get(typeVariableName);
        if (retVal == null) {
            return lookingForType;
        }
        if (retVal instanceof Class) {
            return retVal;
        }
        if (retVal instanceof ParameterizedType) {
            return ReflectionHelper.fixTypeVariables((ParameterizedType)retVal, typeArguments);
        }
        if (retVal instanceof GenericArrayType) {
            return ReflectionHelper.fixGenericArrayTypeVariables((GenericArrayType)retVal, typeArguments);
        }
        return retVal;
    }

    public static Type resolveKnownType(TypeVariable<?> userType, ParameterizedType knownType, Class<?> knownDeclaringClass) {
        TypeVariable<Class<?>>[] knownTypeVariables = knownDeclaringClass.getTypeParameters();
        for (int lcv = 0; lcv < knownTypeVariables.length; ++lcv) {
            TypeVariable<Class<?>> knownTypeVariable = knownTypeVariables[lcv];
            if (!GeneralUtilities.safeEquals(knownTypeVariable.getName(), userType.getName())) continue;
            return knownType.getActualTypeArguments()[lcv];
        }
        return null;
    }

    private static Map<String, Type> typesFromSubClassToDeclaringClass(Class<?> topClass, Class<?> declaringClass) {
        if (topClass.equals(declaringClass)) {
            return null;
        }
        Type superType = topClass.getGenericSuperclass();
        Class<?> superClass = ReflectionHelper.getRawClass(superType);
        while (superType != null && superClass != null) {
            if (!(superType instanceof ParameterizedType)) {
                if (superClass.equals(declaringClass)) {
                    return null;
                }
                superType = superClass.getGenericSuperclass();
                superClass = ReflectionHelper.getRawClass(superType);
                continue;
            }
            ParameterizedType superPT = (ParameterizedType)superType;
            Map<String, Type> typeArguments = ReflectionHelper.getTypeArguments(superClass, superPT);
            if (superClass.equals(declaringClass)) {
                return typeArguments;
            }
            superType = superClass.getGenericSuperclass();
            superClass = ReflectionHelper.getRawClass(superType);
            if (!(superType instanceof ParameterizedType)) continue;
            superType = ReflectionHelper.fixTypeVariables((ParameterizedType)superType, typeArguments);
        }
        throw new AssertionError((Object)(topClass.getName() + " is not the same as or a subclass of " + declaringClass.getName()));
    }

    public static Type getFirstTypeArgument(Type type) {
        if (type instanceof Class) {
            return Object.class;
        }
        if (!(type instanceof ParameterizedType)) {
            return Object.class;
        }
        ParameterizedType pt = (ParameterizedType)type;
        Type[] arguments = pt.getActualTypeArguments();
        if (arguments.length <= 0) {
            return Object.class;
        }
        return arguments[0];
    }

    private static String getNamedName(Named named, Class<?> implClass) {
        String name = named.value();
        if (name != null && !name.equals("")) {
            return name;
        }
        String cn = implClass.getName();
        int index = cn.lastIndexOf(".");
        if (index < 0) {
            return cn;
        }
        return cn.substring(index + 1);
    }

    public static String getName(Class<?> implClass) {
        String namedName;
        Named named = implClass.getAnnotation(Named.class);
        String string = namedName = named != null ? ReflectionHelper.getNamedName(named, implClass) : null;
        if (namedName != null) {
            return namedName;
        }
        return null;
    }

    private static void addAllGenericInterfaces(Class<?> rawClass, Type type, Set<Type> closures) {
        Map<String, Type> typeArgumentsMap = null;
        for (Type currentType : rawClass.getGenericInterfaces()) {
            if (type instanceof ParameterizedType && currentType instanceof ParameterizedType) {
                if (typeArgumentsMap == null) {
                    typeArgumentsMap = ReflectionHelper.getTypeArguments(rawClass, (ParameterizedType)type);
                }
                currentType = ReflectionHelper.fixTypeVariables((ParameterizedType)currentType, typeArgumentsMap);
            }
            closures.add(currentType);
            rawClass = ReflectionHelper.getRawClass(currentType);
            if (rawClass == null) continue;
            ReflectionHelper.addAllGenericInterfaces(rawClass, currentType, closures);
        }
    }

    private static Type fixTypeVariables(ParameterizedType type, Map<String, Type> typeArgumentsMap) {
        Type[] newTypeArguments = ReflectionHelper.getNewTypeArguments(type, typeArgumentsMap);
        if (newTypeArguments != null) {
            return new ParameterizedTypeImpl(type.getRawType(), newTypeArguments);
        }
        return type;
    }

    private static Type fixGenericArrayTypeVariables(GenericArrayType type, Map<String, Type> typeArgumentsMap) {
        Type newTypeArgument = ReflectionHelper.getNewTypeArrayArguments(type, typeArgumentsMap);
        if (newTypeArgument != null) {
            ParameterizedType pt;
            if (newTypeArgument instanceof Class) {
                return ReflectionHelper.getArrayOfType((Class)newTypeArgument);
            }
            if (newTypeArgument instanceof ParameterizedType && (pt = (ParameterizedType)newTypeArgument).getRawType() instanceof Class) {
                return ReflectionHelper.getArrayOfType((Class)pt.getRawType());
            }
            return new GenericArrayTypeImpl(newTypeArgument);
        }
        return type;
    }

    private static Class<?> getArrayOfType(Class<?> type) {
        return Array.newInstance(type, 0).getClass();
    }

    private static Type[] getNewTypeArguments(ParameterizedType type, Map<String, Type> typeArgumentsMap) {
        Type[] typeArguments = type.getActualTypeArguments();
        Type[] newTypeArguments = new Type[typeArguments.length];
        boolean newArgsNeeded = false;
        int i = 0;
        for (Type argType : typeArguments) {
            if (argType instanceof TypeVariable) {
                newTypeArguments[i] = typeArgumentsMap.get(((TypeVariable)argType).getName());
                newArgsNeeded = true;
            } else if (argType instanceof ParameterizedType) {
                ParameterizedType original = (ParameterizedType)argType;
                Type[] internalTypeArgs = ReflectionHelper.getNewTypeArguments(original, typeArgumentsMap);
                if (internalTypeArgs != null) {
                    newTypeArguments[i] = new ParameterizedTypeImpl(original.getRawType(), internalTypeArgs);
                    newArgsNeeded = true;
                } else {
                    newTypeArguments[i] = argType;
                }
            } else if (argType instanceof GenericArrayType) {
                GenericArrayType gat = (GenericArrayType)argType;
                Type internalTypeArg = ReflectionHelper.getNewTypeArrayArguments(gat, typeArgumentsMap);
                if (internalTypeArg != null) {
                    if (internalTypeArg instanceof Class) {
                        newTypeArguments[i] = ReflectionHelper.getArrayOfType((Class)internalTypeArg);
                        newArgsNeeded = true;
                    } else if (internalTypeArg instanceof ParameterizedType && ((ParameterizedType)internalTypeArg).getRawType() instanceof Class) {
                        ParameterizedType pt = (ParameterizedType)internalTypeArg;
                        newTypeArguments[i] = ReflectionHelper.getArrayOfType((Class)pt.getRawType());
                        newArgsNeeded = true;
                    } else {
                        newTypeArguments[i] = new GenericArrayTypeImpl(internalTypeArg);
                        newArgsNeeded = true;
                    }
                } else {
                    newTypeArguments[i] = argType;
                }
            } else {
                newTypeArguments[i] = argType;
            }
            ++i;
        }
        return newArgsNeeded ? newTypeArguments : null;
    }

    private static Type getNewTypeArrayArguments(GenericArrayType gat, Map<String, Type> typeArgumentsMap) {
        Type typeArgument = gat.getGenericComponentType();
        if (typeArgument instanceof TypeVariable) {
            return typeArgumentsMap.get(((TypeVariable)typeArgument).getName());
        }
        if (typeArgument instanceof ParameterizedType) {
            ParameterizedType original = (ParameterizedType)typeArgument;
            Type[] internalTypeArgs = ReflectionHelper.getNewTypeArguments(original, typeArgumentsMap);
            if (internalTypeArgs != null) {
                return new ParameterizedTypeImpl(original.getRawType(), internalTypeArgs);
            }
            return original;
        }
        if (typeArgument instanceof GenericArrayType) {
            GenericArrayType original = (GenericArrayType)typeArgument;
            Type internalTypeArg = ReflectionHelper.getNewTypeArrayArguments(original, typeArgumentsMap);
            if (internalTypeArg != null) {
                ParameterizedType pt;
                if (internalTypeArg instanceof Class) {
                    return ReflectionHelper.getArrayOfType((Class)internalTypeArg);
                }
                if (internalTypeArg instanceof ParameterizedType && (pt = (ParameterizedType)internalTypeArg).getRawType() instanceof Class) {
                    return ReflectionHelper.getArrayOfType((Class)pt.getRawType());
                }
                return new GenericArrayTypeImpl(internalTypeArg);
            }
            return null;
        }
        return null;
    }

    private static Map<String, Type> getTypeArguments(Class<?> rawClass, ParameterizedType type) {
        HashMap<String, Type> typeMap = new HashMap<String, Type>();
        Type[] typeArguments = type.getActualTypeArguments();
        int i = 0;
        for (TypeVariable<Class<?>> typeVariable : rawClass.getTypeParameters()) {
            typeMap.put(typeVariable.getName(), typeArguments[i++]);
        }
        return typeMap;
    }

    private static Set<Type> getTypeClosure(Type ofType) {
        HashSet<Type> retVal = new HashSet<Type>();
        Class<?> rawClass = ReflectionHelper.getRawClass(ofType);
        if (rawClass != null) {
            Map<String, Type> typeArgumentsMap = null;
            Type currentType = ofType;
            while (currentType != null && rawClass != null) {
                retVal.add(currentType);
                ReflectionHelper.addAllGenericInterfaces(rawClass, currentType, retVal);
                if (typeArgumentsMap == null && currentType instanceof ParameterizedType) {
                    typeArgumentsMap = ReflectionHelper.getTypeArguments(rawClass, (ParameterizedType)currentType);
                }
                if ((currentType = rawClass.getGenericSuperclass()) == null) continue;
                rawClass = ReflectionHelper.getRawClass(currentType);
                if (typeArgumentsMap == null || !(currentType instanceof ParameterizedType)) continue;
                currentType = ReflectionHelper.fixTypeVariables((ParameterizedType)currentType, typeArgumentsMap);
            }
        }
        return retVal;
    }

    public static Set<Type> getTypeClosure(Type ofType, Set<String> contracts) {
        Set<Type> closure = ReflectionHelper.getTypeClosure(ofType);
        HashSet<Type> retVal = new HashSet<Type>();
        for (Type t : closure) {
            Class<?> rawClass = ReflectionHelper.getRawClass(t);
            if (rawClass == null || !contracts.contains(rawClass.getName())) continue;
            retVal.add(t);
        }
        return retVal;
    }

    public static Set<Type> getAdvertisedTypesFromClass(Type type, Class<? extends Annotation> markerAnnotation) {
        Class<?> rawClass;
        LinkedHashSet<Type> retVal = new LinkedHashSet<Type>();
        if (type == null) {
            return retVal;
        }
        retVal.add(type);
        Class<?> originalRawClass = ReflectionHelper.getRawClass(type);
        if (originalRawClass == null) {
            return retVal;
        }
        Type genericSuperclass = originalRawClass.getGenericSuperclass();
        while (genericSuperclass != null && (rawClass = ReflectionHelper.getRawClass(genericSuperclass)) != null) {
            if (rawClass.isAnnotationPresent(markerAnnotation)) {
                retVal.add(genericSuperclass);
            }
            genericSuperclass = rawClass.getGenericSuperclass();
        }
        HashSet alreadyHandled = new HashSet();
        while (originalRawClass != null) {
            ReflectionHelper.getAllContractsFromInterfaces(originalRawClass, markerAnnotation, retVal, alreadyHandled);
            originalRawClass = originalRawClass.getSuperclass();
        }
        return retVal;
    }

    private static void getAllContractsFromInterfaces(Class<?> clazzOrInterface, Class<? extends Annotation> markerAnnotation, Set<Type> addToMe, Set<Class<?>> alreadyHandled) {
        Type[] interfacesAsType;
        for (Type interfaceAsType : interfacesAsType = clazzOrInterface.getGenericInterfaces()) {
            Class<?> interfaceAsClass = ReflectionHelper.getRawClass(interfaceAsType);
            if (interfaceAsClass == null || alreadyHandled.contains(interfaceAsClass)) continue;
            alreadyHandled.add(interfaceAsClass);
            if (interfaceAsClass.isAnnotationPresent(markerAnnotation)) {
                addToMe.add(interfaceAsType);
            }
            ReflectionHelper.getAllContractsFromInterfaces(interfaceAsClass, markerAnnotation, addToMe, alreadyHandled);
        }
    }

    public static Set<Type> getAdvertisedTypesFromObject(Object t, Class<? extends Annotation> markerAnnotation) {
        if (t == null) {
            return Collections.emptySet();
        }
        return ReflectionHelper.getAdvertisedTypesFromClass(t.getClass(), markerAnnotation);
    }

    public static Set<String> getContractsFromClass(Class<?> clazz, Class<? extends Annotation> markerAnnotation) {
        LinkedHashSet<String> contractSet = new LinkedHashSet<String>();
        if (clazz == null) {
            return contractSet;
        }
        contractSet.add(clazz.getName());
        for (Class<?> extendsClasses = clazz; extendsClasses != null; extendsClasses = extendsClasses.getSuperclass()) {
            if (extendsClasses.isAnnotationPresent(markerAnnotation)) {
                contractSet.add(extendsClasses.getName());
            }
            ReflectionHelper.recursiveAddContractsFromInterfacesToMap(extendsClasses.getInterfaces(), contractSet, markerAnnotation);
        }
        return contractSet;
    }

    private static void recursiveAddContractsFromInterfacesToMap(Class[] interfaces, Set<String> contractSet, Class<? extends Annotation> markerAnnotation) {
        for (Class anInterface : interfaces) {
            if (anInterface.isAnnotationPresent(markerAnnotation)) {
                contractSet.add(anInterface.getName());
            }
            ReflectionHelper.recursiveAddContractsFromInterfacesToMap(anInterface.getInterfaces(), contractSet, markerAnnotation);
        }
    }

    public static Annotation getScopeAnnotationFromObject(Object t) {
        if (t == null) {
            throw new IllegalArgumentException();
        }
        return ReflectionHelper.getScopeAnnotationFromClass(t.getClass());
    }

    public static Annotation getScopeAnnotationFromClass(Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException();
        }
        for (Annotation annotation : clazz.getAnnotations()) {
            Class<? extends Annotation> annoClass = annotation.annotationType();
            if (!annoClass.isAnnotationPresent(Scope.class)) continue;
            return annotation;
        }
        return null;
    }

    public static Annotation getScopeFromObject(Object t, Annotation annoDefault) {
        if (t == null) {
            return annoDefault;
        }
        return ReflectionHelper.getScopeFromClass(t.getClass(), annoDefault);
    }

    public static Annotation getScopeFromClass(Class<?> clazz, Annotation annoDefault) {
        if (clazz == null) {
            return annoDefault;
        }
        for (Annotation annotation : clazz.getAnnotations()) {
            Class<? extends Annotation> annoClass = annotation.annotationType();
            if (!annoClass.isAnnotationPresent(Scope.class)) continue;
            return annotation;
        }
        return annoDefault;
    }

    public static boolean isAnnotationAQualifier(Annotation anno) {
        Class<? extends Annotation> annoType = anno.annotationType();
        return annoType.isAnnotationPresent(Qualifier.class);
    }

    public static Set<Annotation> getQualifiersFromObject(Object t) {
        if (t == null) {
            return Collections.emptySet();
        }
        return ReflectionHelper.getQualifierAnnotations(t.getClass());
    }

    public static Set<String> getQualifiersFromClass(Class<?> clazz) {
        LinkedHashSet<String> retVal = new LinkedHashSet<String>();
        if (clazz == null) {
            return retVal;
        }
        for (Annotation annotation : clazz.getAnnotations()) {
            if (!ReflectionHelper.isAnnotationAQualifier(annotation)) continue;
            retVal.add(annotation.annotationType().getName());
        }
        while (clazz != null) {
            for (Class<?> iFace : clazz.getInterfaces()) {
                for (Annotation annotation : iFace.getAnnotations()) {
                    if (!ReflectionHelper.isAnnotationAQualifier(annotation)) continue;
                    retVal.add(annotation.annotationType().getName());
                }
            }
            clazz = clazz.getSuperclass();
        }
        return retVal;
    }

    private static Set<Annotation> internalGetQualifierAnnotations(AnnotatedElement annotatedGuy) {
        LinkedHashSet<Annotation> retVal = new LinkedHashSet<Annotation>();
        if (annotatedGuy == null) {
            return retVal;
        }
        for (Annotation annotation : annotatedGuy.getAnnotations()) {
            Named n;
            if (!ReflectionHelper.isAnnotationAQualifier(annotation) || annotatedGuy instanceof Field && Named.class.equals(annotation.annotationType()) && ((n = (Named)annotation).value() == null || "".equals(n.value()))) continue;
            retVal.add(annotation);
        }
        if (!(annotatedGuy instanceof Class)) {
            return retVal;
        }
        for (Class clazz = (Class)annotatedGuy; clazz != null; clazz = clazz.getSuperclass()) {
            for (Class<?> iFace : clazz.getInterfaces()) {
                for (Annotation annotation : iFace.getAnnotations()) {
                    if (!ReflectionHelper.isAnnotationAQualifier(annotation)) continue;
                    retVal.add(annotation);
                }
            }
        }
        return retVal;
    }

    public static Set<Annotation> getQualifierAnnotations(final AnnotatedElement annotatedGuy) {
        Set<Annotation> retVal = AccessController.doPrivileged(new PrivilegedAction<Set<Annotation>>(){

            @Override
            public Set<Annotation> run() {
                return ReflectionHelper.internalGetQualifierAnnotations(annotatedGuy);
            }
        });
        return retVal;
    }

    public static String writeSet(Set<?> set) {
        return ReflectionHelper.writeSet(set, null);
    }

    public static String writeSet(Set<?> set, Object excludeMe) {
        if (set == null) {
            return "{}";
        }
        StringBuffer sb = new StringBuffer("{");
        boolean first = true;
        for (Object writeMe : set) {
            if (excludeMe != null && excludeMe.equals(writeMe)) continue;
            if (first) {
                first = false;
                sb.append(ReflectionHelper.escapeString(writeMe.toString()));
                continue;
            }
            sb.append(COMMA_STRING + ReflectionHelper.escapeString(writeMe.toString()));
        }
        sb.append("}");
        return sb.toString();
    }

    public static void readSet(String line, Collection<String> addToMe) throws IOException {
        char[] asChars = new char[line.length()];
        line.getChars(0, line.length(), asChars, 0);
        ReflectionHelper.internalReadSet(asChars, 0, addToMe);
    }

    private static int internalReadSet(char[] asChars, int startIndex, Collection<String> addToMe) throws IOException {
        int dot;
        int startOfSet = -1;
        for (dot = startIndex; dot < asChars.length; ++dot) {
            if (asChars[dot] != '{') continue;
            startOfSet = dot++;
            break;
        }
        if (startOfSet == -1) {
            throw new IOException("Unknown set format, no initial { character : " + new String(asChars));
        }
        StringBuffer elementBuffer = new StringBuffer();
        int endOfSet = -1;
        while (dot < asChars.length) {
            char dotChar = asChars[dot];
            if (dotChar == '}') {
                addToMe.add(elementBuffer.toString());
                endOfSet = dot;
                break;
            }
            if (dotChar == ',') {
                addToMe.add(elementBuffer.toString());
                elementBuffer = new StringBuffer();
            } else if (dotChar != '\\') {
                elementBuffer.append(dotChar);
            } else {
                if (dot + 1 >= asChars.length) break;
                if ((dotChar = asChars[++dot]) == 'n') {
                    elementBuffer.append('\n');
                } else if (dotChar == 'r') {
                    elementBuffer.append('\r');
                } else {
                    elementBuffer.append(dotChar);
                }
            }
            ++dot;
        }
        if (endOfSet == -1) {
            throw new IOException("Unknown set format, no ending } character : " + new String(asChars));
        }
        return dot - startIndex;
    }

    private static int readKeyStringListLine(char[] asChars, int startIndex, Map<String, List<String>> addToMe) throws IOException {
        char skipComma;
        int dot;
        int equalsIndex = -1;
        for (dot = startIndex; dot < asChars.length; ++dot) {
            char dotChar = asChars[dot];
            if (dotChar != '=') continue;
            equalsIndex = dot;
            break;
        }
        if (equalsIndex < 0) {
            throw new IOException("Unknown key-string list format, no equals: " + new String(asChars));
        }
        String key = new String(asChars, startIndex, equalsIndex - startIndex);
        if (++dot >= asChars.length) {
            throw new IOException("Found a key with no value, " + key + " in line " + new String(asChars));
        }
        LinkedList<String> listValues = new LinkedList<String>();
        int addOn = ReflectionHelper.internalReadSet(asChars, dot, listValues);
        if (!listValues.isEmpty()) {
            addToMe.put(key, listValues);
        }
        if ((dot += addOn + 1) < asChars.length && (skipComma = asChars[dot]) == ',') {
            ++dot;
        }
        return dot - startIndex;
    }

    public static void readMetadataMap(String line, Map<String, List<String>> addToMe) throws IOException {
        int addMe;
        char[] asChars = new char[line.length()];
        line.getChars(0, line.length(), asChars, 0);
        for (int dot = 0; dot < asChars.length; dot += addMe) {
            addMe = ReflectionHelper.readKeyStringListLine(asChars, dot, addToMe);
        }
    }

    private static String escapeString(String escapeMe) {
        char[] asChars = new char[escapeMe.length()];
        escapeMe.getChars(0, escapeMe.length(), asChars, 0);
        StringBuffer sb = new StringBuffer();
        for (int lcv = 0; lcv < asChars.length; ++lcv) {
            char candidateChar = asChars[lcv];
            if (ESCAPE_CHARACTERS.contains(Character.valueOf(candidateChar))) {
                sb.append('\\');
                sb.append(candidateChar);
                continue;
            }
            if (REPLACE_CHARACTERS.containsKey(Character.valueOf(candidateChar))) {
                char replaceWithMe = REPLACE_CHARACTERS.get(Character.valueOf(candidateChar)).charValue();
                sb.append('\\');
                sb.append(replaceWithMe);
                continue;
            }
            sb.append(candidateChar);
        }
        return sb.toString();
    }

    private static String writeList(List<String> list) {
        StringBuffer sb = new StringBuffer("{");
        boolean first = true;
        for (String writeMe : list) {
            if (first) {
                first = false;
                sb.append(ReflectionHelper.escapeString(writeMe));
                continue;
            }
            sb.append(COMMA_STRING + ReflectionHelper.escapeString(writeMe));
        }
        sb.append("}");
        return sb.toString();
    }

    public static String writeMetadata(Map<String, List<String>> metadata) {
        StringBuffer sb = new StringBuffer();
        boolean first = true;
        for (Map.Entry<String, List<String>> entry : metadata.entrySet()) {
            if (first) {
                first = false;
                sb.append(entry.getKey() + '=');
            } else {
                sb.append(COMMA_STRING + entry.getKey() + '=');
            }
            sb.append(ReflectionHelper.writeList(entry.getValue()));
        }
        return sb.toString();
    }

    public static void addMetadata(Map<String, List<String>> metadatas, String key, String value) {
        if (key == null || value == null) {
            return;
        }
        if (key.indexOf(61) >= 0) {
            throw new IllegalArgumentException("The key field may not have an = in it:" + key);
        }
        List<String> inner = metadatas.get(key);
        if (inner == null) {
            inner = new LinkedList<String>();
            metadatas.put(key, inner);
        }
        inner.add(value);
    }

    public static boolean removeMetadata(Map<String, List<String>> metadatas, String key, String value) {
        if (key == null || value == null) {
            return false;
        }
        List<String> inner = metadatas.get(key);
        if (inner == null) {
            return false;
        }
        boolean retVal = inner.remove(value);
        if (inner.size() <= 0) {
            metadatas.remove(key);
        }
        return retVal;
    }

    public static boolean removeAllMetadata(Map<String, List<String>> metadatas, String key) {
        List<String> values = metadatas.remove(key);
        return values != null && values.size() > 0;
    }

    public static Map<String, List<String>> deepCopyMetadata(Map<String, List<String>> copyMe) {
        if (copyMe == null) {
            return null;
        }
        LinkedHashMap<String, List<String>> retVal = new LinkedHashMap<String, List<String>>();
        for (Map.Entry<String, List<String>> entry : copyMe.entrySet()) {
            String key = entry.getKey();
            if (key.indexOf(61) >= 0) {
                throw new IllegalArgumentException("The key field may not have an = in it:" + key);
            }
            List<String> values = entry.getValue();
            LinkedList<String> valuesCopy = new LinkedList<String>();
            for (String value : values) {
                valuesCopy.add(value);
            }
            retVal.put(key, valuesCopy);
        }
        return retVal;
    }

    public static void setField(Field field, Object instance, Object value) throws Throwable {
        ReflectionHelper.setAccessible(field);
        try {
            field.set(instance, value);
        }
        catch (IllegalArgumentException e) {
            Logger.getLogger().debug(field.getDeclaringClass().getName(), field.getName(), e);
            throw e;
        }
        catch (IllegalAccessException e) {
            Logger.getLogger().debug(field.getDeclaringClass().getName(), field.getName(), e);
            throw e;
        }
    }

    public static Object invoke(Object o, Method m, Object[] args, boolean neutralCCL) throws Throwable {
        if (ReflectionHelper.isStatic(m)) {
            o = null;
        }
        ReflectionHelper.setAccessible(m);
        ClassLoader currentCCL = null;
        if (neutralCCL) {
            currentCCL = ReflectionHelper.getCurrentContextClassLoader();
        }
        try {
            Object object = m.invoke(o, args);
            return object;
        }
        catch (InvocationTargetException ite) {
            Throwable targetException = ite.getTargetException();
            Logger.getLogger().debug(m.getDeclaringClass().getName(), m.getName(), targetException);
            throw targetException;
        }
        catch (Throwable th) {
            Logger.getLogger().debug(m.getDeclaringClass().getName(), m.getName(), th);
            throw th;
        }
        finally {
            if (neutralCCL) {
                ReflectionHelper.setContextClassLoader(Thread.currentThread(), currentCCL);
            }
        }
    }

    public static boolean isStatic(Member member) {
        int modifiers = member.getModifiers();
        return (modifiers & 8) != 0;
    }

    private static void setContextClassLoader(final Thread t, final ClassLoader l) {
        AccessController.doPrivileged(new PrivilegedAction<Object>(){

            @Override
            public Object run() {
                t.setContextClassLoader(l);
                return null;
            }
        });
    }

    private static void setAccessible(final AccessibleObject ao) {
        if (ao.isAccessible()) {
            return;
        }
        AccessController.doPrivileged(new PrivilegedAction<Object>(){

            @Override
            public Object run() {
                ao.setAccessible(true);
                return null;
            }
        });
    }

    public static Object makeMe(Constructor<?> c, Object[] args, boolean neutralCCL) throws Throwable {
        ClassLoader currentCCL = null;
        if (neutralCCL) {
            currentCCL = ReflectionHelper.getCurrentContextClassLoader();
        }
        try {
            Object obj = c.newInstance(args);
            return obj;
        }
        catch (InvocationTargetException ite) {
            Throwable targetException = ite.getTargetException();
            Logger.getLogger().debug(c.getDeclaringClass().getName(), c.getName(), targetException);
            throw targetException;
        }
        catch (Throwable th) {
            Logger.getLogger().debug(c.getDeclaringClass().getName(), c.getName(), th);
            throw th;
        }
        finally {
            if (neutralCCL) {
                ReflectionHelper.setContextClassLoader(Thread.currentThread(), currentCCL);
            }
        }
    }

    public static void parseServiceMetadataString(String metadataField, Map<String, List<String>> metadata) {
        StringBuffer sb = new StringBuffer(metadataField);
        int dot = 0;
        int nextEquals = sb.indexOf(EQUALS_STRING, dot);
        while (nextEquals > 0) {
            int commaPlace;
            String key = sb.substring(dot, nextEquals);
            dot = nextEquals + 1;
            String value = null;
            if (sb.charAt(dot) == '\"') {
                int nextQuote;
                if ((nextQuote = sb.indexOf(QUOTE_STRING, ++dot)) < 0) {
                    throw new IllegalStateException("Badly formed metadata \"" + metadataField + "\" for key " + key + " has a leading quote but no trailing quote");
                }
                value = sb.substring(dot, nextQuote);
                dot = nextQuote + 1;
                commaPlace = sb.indexOf(COMMA_STRING, dot);
            } else {
                commaPlace = sb.indexOf(COMMA_STRING, dot);
                value = commaPlace < 0 ? sb.substring(dot) : sb.substring(dot, commaPlace);
            }
            List<String> addToMe = metadata.get(key);
            if (addToMe == null) {
                addToMe = new LinkedList<String>();
                metadata.put(key, addToMe);
            }
            addToMe.add(value);
            if (commaPlace >= 0) {
                dot = commaPlace + 1;
                nextEquals = sb.indexOf(EQUALS_STRING, dot);
                continue;
            }
            nextEquals = -1;
        }
    }

    public static String getNameFromAllQualifiers(Set<Annotation> qualifiers, AnnotatedElement parent) throws IllegalStateException {
        for (Annotation qualifier : qualifiers) {
            if (!Named.class.equals(qualifier.annotationType())) continue;
            Named named = (Named)qualifier;
            if (named.value() == null || named.value().equals("")) {
                if (parent != null) {
                    if (parent instanceof Class) {
                        return Pretty.clazz((Class)parent);
                    }
                    if (parent instanceof Field) {
                        return ((Field)parent).getName();
                    }
                }
                throw new IllegalStateException("@Named must have a value for " + parent);
            }
            return named.value();
        }
        return null;
    }

    private static ClassLoader getCurrentContextClassLoader() {
        return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

            @Override
            public ClassLoader run() {
                return Thread.currentThread().getContextClassLoader();
            }
        });
    }

    public static boolean annotationContainsAll(final Set<Annotation> candidateAnnotations, final Set<Annotation> requiredAnnotations) {
        return AccessController.doPrivileged(new PrivilegedAction<Boolean>(){

            @Override
            public Boolean run() {
                return candidateAnnotations.containsAll(requiredAnnotations);
            }
        });
    }

    public static Class<?> translatePrimitiveType(Class<?> type) {
        Class<?> translation = Constants.PRIMITIVE_MAP.get(type);
        if (translation == null) {
            return type;
        }
        return translation;
    }

    public static boolean isPrivate(Member member) {
        int modifiers = member.getModifiers();
        return (modifiers & 2) != 0;
    }

    public static Set<Type> getAllTypes(Type t) {
        Type[] rawSuperclass;
        LinkedHashSet<Type> retVal = new LinkedHashSet<Type>();
        retVal.add(t);
        Class<?> rawClass = ReflectionHelper.getRawClass(t);
        if (rawClass == null) {
            return retVal;
        }
        Type genericSuperclass = rawClass.getGenericSuperclass();
        while (genericSuperclass != null && (rawSuperclass = ReflectionHelper.getRawClass(genericSuperclass)) != null) {
            retVal.add(genericSuperclass);
            genericSuperclass = rawSuperclass.getGenericSuperclass();
        }
        while (rawClass != null) {
            for (Type iface : rawClass.getGenericInterfaces()) {
                ReflectionHelper.addAllInterfaceContracts(iface, retVal);
            }
            rawClass = rawClass.getSuperclass();
        }
        LinkedHashSet<Type> altRetVal = new LinkedHashSet<Type>();
        HashMap class2TypeMap = new HashMap();
        for (Type foundType : retVal) {
            if (!(foundType instanceof ParameterizedType)) {
                altRetVal.add(foundType);
                continue;
            }
            ParameterizedType originalPt = (ParameterizedType)foundType;
            Class<?> rawType = ReflectionHelper.getRawClass(foundType);
            class2TypeMap.put(rawType, originalPt);
            if (ReflectionHelper.isFilledIn(originalPt)) {
                altRetVal.add(foundType);
                continue;
            }
            ParameterizedType pti = ReflectionHelper.fillInPT(originalPt, class2TypeMap);
            altRetVal.add(pti);
            class2TypeMap.put(rawType, pti);
        }
        return altRetVal;
    }

    private static ParameterizedType fillInPT(ParameterizedType pt, HashMap<Class<?>, ParameterizedType> class2TypeMap) {
        if (ReflectionHelper.isFilledIn(pt)) {
            return pt;
        }
        Type[] newActualArguments = new Type[pt.getActualTypeArguments().length];
        for (int outerIndex = 0; outerIndex < newActualArguments.length; ++outerIndex) {
            ParameterizedType parentPType;
            Type fillMeIn;
            newActualArguments[outerIndex] = fillMeIn = pt.getActualTypeArguments()[outerIndex];
            if (fillMeIn instanceof ParameterizedType) {
                newActualArguments[outerIndex] = ReflectionHelper.fillInPT((ParameterizedType)fillMeIn, class2TypeMap);
                continue;
            }
            if (!(fillMeIn instanceof TypeVariable)) continue;
            TypeVariable tv = (TypeVariable)fillMeIn;
            Class genericDeclaration = (Class)tv.getGenericDeclaration();
            boolean found = false;
            int count = -1;
            for (TypeVariable parentVariable : genericDeclaration.getTypeParameters()) {
                ++count;
                if (!parentVariable.equals(tv)) continue;
                found = true;
                break;
            }
            if (!found || (parentPType = class2TypeMap.get(genericDeclaration)) == null) continue;
            newActualArguments[outerIndex] = parentPType.getActualTypeArguments()[count];
        }
        ParameterizedTypeImpl pti = new ParameterizedTypeImpl(ReflectionHelper.getRawClass(pt), newActualArguments);
        return pti;
    }

    private static boolean isFilledIn(ParameterizedType pt, HashSet<ParameterizedType> recursionKiller) {
        if (recursionKiller.contains(pt)) {
            return false;
        }
        recursionKiller.add(pt);
        for (Type t : pt.getActualTypeArguments()) {
            if (t instanceof TypeVariable) {
                return false;
            }
            if (t instanceof WildcardType) {
                return false;
            }
            if (!(t instanceof ParameterizedType)) continue;
            return ReflectionHelper.isFilledIn((ParameterizedType)t, recursionKiller);
        }
        return true;
    }

    private static boolean isFilledIn(ParameterizedType pt) {
        return ReflectionHelper.isFilledIn(pt, new HashSet<ParameterizedType>());
    }

    private static void addAllInterfaceContracts(Type interfaceType, LinkedHashSet<Type> addToMe) {
        Class<?> interfaceClass = ReflectionHelper.getRawClass(interfaceType);
        if (interfaceClass == null) {
            return;
        }
        if (addToMe.contains(interfaceType)) {
            return;
        }
        addToMe.add(interfaceType);
        for (Type extendedInterfaces : interfaceClass.getGenericInterfaces()) {
            ReflectionHelper.addAllInterfaceContracts(extendedInterfaces, addToMe);
        }
    }

    public static MethodWrapper createMethodWrapper(Method wrapMe) {
        return new MethodWrapperImpl(wrapMe);
    }

    public static <T> T cast(Object o) {
        return (T)o;
    }

    static {
        for (char illegal : ILLEGAL_CHARACTERS) {
            ESCAPE_CHARACTERS.add(Character.valueOf(illegal));
        }
        REPLACE_CHARACTERS.put(Character.valueOf('\n'), Character.valueOf('n'));
        REPLACE_CHARACTERS.put(Character.valueOf('\r'), Character.valueOf('r'));
    }
}

