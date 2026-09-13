/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast;

import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.GString;
import groovy.lang.GroovyInterceptable;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyObjectSupport;
import groovy.lang.MetaClass;
import groovy.lang.Range;
import groovy.lang.Reference;
import groovy.lang.Script;
import groovy.lang.Tuple;
import groovy.lang.Tuple0;
import groovy.lang.Tuple1;
import groovy.lang.Tuple10;
import groovy.lang.Tuple11;
import groovy.lang.Tuple12;
import groovy.lang.Tuple13;
import groovy.lang.Tuple14;
import groovy.lang.Tuple15;
import groovy.lang.Tuple16;
import groovy.lang.Tuple2;
import groovy.lang.Tuple3;
import groovy.lang.Tuple4;
import groovy.lang.Tuple5;
import groovy.lang.Tuple6;
import groovy.lang.Tuple7;
import groovy.lang.Tuple8;
import groovy.lang.Tuple9;
import groovy.transform.Sealed;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.invoke.SerializedLambda;
import java.lang.ref.SoftReference;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.apache.groovy.util.Maps;
import org.apache.groovy.util.concurrent.ManagedIdentityConcurrentMap;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.classgen.asm.util.TypeUtil;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.GeneratedLambda;
import org.codehaus.groovy.transform.stc.StaticTypeCheckingSupport;
import org.codehaus.groovy.transform.trait.Traits;
import org.codehaus.groovy.vmplugin.VMPluginFactory;

public class ClassHelper {
    private static final Class[] classes = new Class[]{Object.class, Boolean.TYPE, Character.TYPE, Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Double.TYPE, Float.TYPE, Void.TYPE, Closure.class, GString.class, List.class, Map.class, Range.class, Pattern.class, Script.class, String.class, Boolean.class, Character.class, Byte.class, Short.class, Integer.class, Long.class, Double.class, Float.class, BigDecimal.class, BigInteger.class, Number.class, Void.class, Reference.class, Class.class, MetaClass.class, Iterator.class, GeneratedClosure.class, GeneratedLambda.class, GroovyObjectSupport.class};
    public static final Class[] TUPLE_CLASSES = new Class[]{Tuple0.class, Tuple1.class, Tuple2.class, Tuple3.class, Tuple4.class, Tuple5.class, Tuple6.class, Tuple7.class, Tuple8.class, Tuple9.class, Tuple10.class, Tuple11.class, Tuple12.class, Tuple13.class, Tuple14.class, Tuple15.class, Tuple16.class};
    private static final String[] primitiveClassNames = new String[]{"", "boolean", "char", "byte", "short", "int", "long", "double", "float", "void"};
    public static final ClassNode OBJECT_TYPE = ClassHelper.makeCached(Object.class);
    public static final ClassNode CLOSURE_TYPE = ClassHelper.makeCached(Closure.class);
    public static final ClassNode GSTRING_TYPE = ClassHelper.makeCached(GString.class);
    public static final ClassNode RANGE_TYPE = ClassHelper.makeCached(Range.class);
    public static final ClassNode PATTERN_TYPE = ClassHelper.makeCached(Pattern.class);
    public static final ClassNode STRING_TYPE = ClassHelper.makeCached(String.class);
    public static final ClassNode SCRIPT_TYPE = ClassHelper.makeCached(Script.class);
    public static final ClassNode BINDING_TYPE = ClassHelper.makeCached(Binding.class);
    public static final ClassNode THROWABLE_TYPE = ClassHelper.makeCached(Throwable.class);
    public static final ClassNode boolean_TYPE = ClassHelper.makeCached(Boolean.TYPE);
    public static final ClassNode char_TYPE = ClassHelper.makeCached(Character.TYPE);
    public static final ClassNode byte_TYPE = ClassHelper.makeCached(Byte.TYPE);
    public static final ClassNode int_TYPE = ClassHelper.makeCached(Integer.TYPE);
    public static final ClassNode long_TYPE = ClassHelper.makeCached(Long.TYPE);
    public static final ClassNode short_TYPE = ClassHelper.makeCached(Short.TYPE);
    public static final ClassNode double_TYPE = ClassHelper.makeCached(Double.TYPE);
    public static final ClassNode float_TYPE = ClassHelper.makeCached(Float.TYPE);
    public static final ClassNode Byte_TYPE = ClassHelper.makeCached(Byte.class);
    public static final ClassNode Short_TYPE = ClassHelper.makeCached(Short.class);
    public static final ClassNode Integer_TYPE = ClassHelper.makeCached(Integer.class);
    public static final ClassNode Long_TYPE = ClassHelper.makeCached(Long.class);
    public static final ClassNode Character_TYPE = ClassHelper.makeCached(Character.class);
    public static final ClassNode Float_TYPE = ClassHelper.makeCached(Float.class);
    public static final ClassNode Double_TYPE = ClassHelper.makeCached(Double.class);
    public static final ClassNode Boolean_TYPE = ClassHelper.makeCached(Boolean.class);
    public static final ClassNode BigInteger_TYPE = ClassHelper.makeCached(BigInteger.class);
    public static final ClassNode BigDecimal_TYPE = ClassHelper.makeCached(BigDecimal.class);
    public static final ClassNode Number_TYPE = ClassHelper.makeCached(Number.class);
    public static final ClassNode VOID_TYPE = ClassHelper.makeCached(Void.TYPE);
    public static final ClassNode void_WRAPPER_TYPE = ClassHelper.makeCached(Void.class);
    public static final ClassNode METACLASS_TYPE = ClassHelper.makeCached(MetaClass.class);
    public static final ClassNode Iterator_TYPE = ClassHelper.makeCached(Iterator.class);
    public static final ClassNode Annotation_TYPE = ClassHelper.makeCached(Annotation.class);
    public static final ClassNode ELEMENT_TYPE_TYPE = ClassHelper.makeCached(ElementType.class);
    public static final ClassNode AUTOCLOSEABLE_TYPE = ClassHelper.makeCached(AutoCloseable.class);
    public static final ClassNode SERIALIZABLE_TYPE = ClassHelper.makeCached(Serializable.class);
    public static final ClassNode SERIALIZEDLAMBDA_TYPE = ClassHelper.makeCached(SerializedLambda.class);
    public static final ClassNode SEALED_TYPE = ClassHelper.makeCached(Sealed.class);
    public static final ClassNode OVERRIDE_TYPE = ClassHelper.makeCached(Override.class);
    public static final ClassNode DEPRECATED_TYPE = ClassHelper.makeCached(Deprecated.class);
    public static final ClassNode MAP_TYPE = ClassHelper.makeWithoutCaching(Map.class);
    public static final ClassNode SET_TYPE = ClassHelper.makeWithoutCaching(Set.class);
    public static final ClassNode LIST_TYPE = ClassHelper.makeWithoutCaching(List.class);
    public static final ClassNode Enum_Type = ClassHelper.makeWithoutCaching(Enum.class);
    public static final ClassNode CLASS_Type = ClassHelper.makeWithoutCaching(Class.class);
    public static final ClassNode TUPLE_TYPE = ClassHelper.makeWithoutCaching(Tuple.class);
    public static final ClassNode STREAM_TYPE = ClassHelper.makeWithoutCaching(Stream.class);
    public static final ClassNode ITERABLE_TYPE = ClassHelper.makeWithoutCaching(Iterable.class);
    public static final ClassNode REFERENCE_TYPE = ClassHelper.makeWithoutCaching(Reference.class);
    public static final ClassNode COLLECTION_TYPE = ClassHelper.makeWithoutCaching(Collection.class);
    public static final ClassNode COMPARABLE_TYPE = ClassHelper.makeWithoutCaching(Comparable.class);
    public static final ClassNode GROOVY_OBJECT_TYPE = ClassHelper.makeWithoutCaching(GroovyObject.class);
    public static final ClassNode GENERATED_LAMBDA_TYPE = ClassHelper.makeWithoutCaching(GeneratedLambda.class);
    public static final ClassNode GENERATED_CLOSURE_Type = ClassHelper.makeWithoutCaching(GeneratedClosure.class);
    public static final ClassNode GROOVY_INTERCEPTABLE_TYPE = ClassHelper.makeWithoutCaching(GroovyInterceptable.class);
    public static final ClassNode GROOVY_OBJECT_SUPPORT_TYPE = ClassHelper.makeWithoutCaching(GroovyObjectSupport.class);
    @Deprecated
    public static final ClassNode DYNAMIC_TYPE = OBJECT_TYPE;
    private static final ClassNode[] types = new ClassNode[]{OBJECT_TYPE, boolean_TYPE, char_TYPE, byte_TYPE, short_TYPE, int_TYPE, long_TYPE, double_TYPE, float_TYPE, VOID_TYPE, CLOSURE_TYPE, GSTRING_TYPE, LIST_TYPE, MAP_TYPE, RANGE_TYPE, PATTERN_TYPE, SCRIPT_TYPE, STRING_TYPE, Boolean_TYPE, Character_TYPE, Byte_TYPE, Short_TYPE, Integer_TYPE, Long_TYPE, Double_TYPE, Float_TYPE, BigDecimal_TYPE, BigInteger_TYPE, Number_TYPE, void_WRAPPER_TYPE, REFERENCE_TYPE, CLASS_Type, METACLASS_TYPE, Iterator_TYPE, GENERATED_CLOSURE_Type, GENERATED_LAMBDA_TYPE, GROOVY_OBJECT_SUPPORT_TYPE, GROOVY_OBJECT_TYPE, GROOVY_INTERCEPTABLE_TYPE, Enum_Type, Annotation_TYPE};
    private static final String DYNAMIC_TYPE_METADATA = "_DYNAMIC_TYPE_METADATA_";
    protected static final ClassNode[] EMPTY_TYPE_ARRAY = new ClassNode[0];
    public static final String OBJECT = "java.lang.Object";
    private static final Map<ClassNode, ClassNode> PRIMITIVE_TYPE_TO_WRAPPER_TYPE_MAP = Maps.of(boolean_TYPE, Boolean_TYPE, byte_TYPE, Byte_TYPE, char_TYPE, Character_TYPE, short_TYPE, Short_TYPE, int_TYPE, Integer_TYPE, long_TYPE, Long_TYPE, float_TYPE, Float_TYPE, double_TYPE, Double_TYPE, VOID_TYPE, void_WRAPPER_TYPE);
    private static final Map<ClassNode, ClassNode> WRAPPER_TYPE_TO_PRIMITIVE_TYPE_MAP = Maps.inverse(PRIMITIVE_TYPE_TO_WRAPPER_TYPE_MAP);

    public static ClassNode dynamicType() {
        ClassNode node = OBJECT_TYPE.getPlainNodeReference();
        node.putNodeMetaData(DYNAMIC_TYPE_METADATA, Boolean.TRUE);
        return node;
    }

    public static ClassNode makeCached(Class c) {
        ClassNode classNode;
        SoftReference classNodeSoftReference = (SoftReference)ClassHelperCache.classCache.get(c);
        if (classNodeSoftReference == null || (classNode = (ClassNode)classNodeSoftReference.get()) == null) {
            classNode = new ClassNode(c);
            ClassHelperCache.classCache.put(c, new SoftReference<ClassNode>(classNode));
            VMPluginFactory.getPlugin().setAdditionalClassInformation(classNode);
        }
        return classNode;
    }

    public static ClassNode[] make(Class[] classes) {
        ClassNode[] cns = new ClassNode[classes.length];
        for (int i = 0; i < cns.length; ++i) {
            cns[i] = ClassHelper.make(classes[i]);
        }
        return cns;
    }

    public static ClassNode make(Class c) {
        return ClassHelper.make(c, true);
    }

    public static ClassNode make(Class c, boolean includeGenerics) {
        for (int i = 0; i < classes.length; ++i) {
            if (c != classes[i]) continue;
            return types[i];
        }
        if (c.isArray()) {
            ClassNode cn = ClassHelper.make(c.getComponentType(), includeGenerics);
            return cn.makeArray();
        }
        return ClassHelper.makeWithoutCaching(c, includeGenerics);
    }

    public static ClassNode makeWithoutCaching(Class c) {
        return ClassHelper.makeWithoutCaching(c, true);
    }

    public static ClassNode makeWithoutCaching(Class c, boolean includeGenerics) {
        if (c.isArray()) {
            ClassNode cn = ClassHelper.makeWithoutCaching(c.getComponentType(), includeGenerics);
            return cn.makeArray();
        }
        ClassNode cached = ClassHelper.makeCached(c);
        if (includeGenerics) {
            return cached;
        }
        ClassNode t = ClassHelper.makeWithoutCaching(c.getName());
        t.setRedirect(cached);
        return t;
    }

    public static ClassNode makeWithoutCaching(String name) {
        ClassNode cn = new ClassNode(name, 1, OBJECT_TYPE);
        cn.isPrimaryNode = false;
        return cn;
    }

    public static ClassNode make(String name) {
        int i;
        if (name == null || name.length() == 0) {
            return ClassHelper.dynamicType();
        }
        for (i = 0; i < primitiveClassNames.length; ++i) {
            if (!primitiveClassNames[i].equals(name)) continue;
            return types[i];
        }
        for (i = 0; i < classes.length; ++i) {
            String cname = classes[i].getName();
            if (!name.equals(cname)) continue;
            return types[i];
        }
        return ClassHelper.makeWithoutCaching(name);
    }

    public static ClassNode getWrapper(ClassNode cn) {
        if (!ClassHelper.isPrimitiveType(cn = cn.redirect())) {
            return cn;
        }
        ClassNode result = PRIMITIVE_TYPE_TO_WRAPPER_TYPE_MAP.get(cn);
        if (result == null) {
            result = PRIMITIVE_TYPE_TO_WRAPPER_TYPE_MAP.get(cn.redirect());
        }
        if (null != result) {
            return result;
        }
        return cn;
    }

    public static ClassNode getUnwrapper(ClassNode cn) {
        if (ClassHelper.isPrimitiveType(cn = cn.redirect())) {
            return cn;
        }
        ClassNode result = WRAPPER_TYPE_TO_PRIMITIVE_TYPE_MAP.get(cn);
        if (null != result) {
            return result;
        }
        return cn;
    }

    public static boolean isPrimitiveType(ClassNode cn) {
        return TypeUtil.isPrimitiveType(cn);
    }

    public static boolean isStaticConstantInitializerType(ClassNode cn) {
        return ClassHelper.isPrimitiveInt(cn) || ClassHelper.isPrimitiveFloat(cn) || ClassHelper.isPrimitiveLong(cn) || ClassHelper.isPrimitiveDouble(cn) || ClassHelper.isStringType(cn) || ClassHelper.isPrimitiveByte(cn) || ClassHelper.isPrimitiveChar(cn) || ClassHelper.isPrimitiveShort(cn);
    }

    public static boolean isNumberType(ClassNode cn) {
        return ClassHelper.isWrapperByte(cn) || ClassHelper.isWrapperShort(cn) || ClassHelper.isWrapperInteger(cn) || ClassHelper.isWrapperLong(cn) || ClassHelper.isWrapperFloat(cn) || ClassHelper.isWrapperDouble(cn) || ClassHelper.isPrimitiveByte(cn) || ClassHelper.isPrimitiveShort(cn) || ClassHelper.isPrimitiveInt(cn) || ClassHelper.isPrimitiveLong(cn) || ClassHelper.isPrimitiveFloat(cn) || ClassHelper.isPrimitiveDouble(cn);
    }

    public static ClassNode makeReference() {
        return REFERENCE_TYPE.getPlainNodeReference();
    }

    public static boolean isCachedType(ClassNode type) {
        for (ClassNode cachedType : types) {
            if (cachedType != type) continue;
            return true;
        }
        return false;
    }

    public static boolean isDynamicTyped(ClassNode type) {
        return type != null && Boolean.TRUE.equals(type.getNodeMetaData(DYNAMIC_TYPE_METADATA));
    }

    public static boolean isPrimitiveBoolean(ClassNode type) {
        return type.redirect() == boolean_TYPE;
    }

    public static boolean isPrimitiveChar(ClassNode type) {
        return type.redirect() == char_TYPE;
    }

    public static boolean isPrimitiveByte(ClassNode type) {
        return type.redirect() == byte_TYPE;
    }

    public static boolean isPrimitiveInt(ClassNode type) {
        return type.redirect() == int_TYPE;
    }

    public static boolean isPrimitiveLong(ClassNode type) {
        return type.redirect() == long_TYPE;
    }

    public static boolean isPrimitiveShort(ClassNode type) {
        return type.redirect() == short_TYPE;
    }

    public static boolean isPrimitiveDouble(ClassNode type) {
        return type.redirect() == double_TYPE;
    }

    public static boolean isPrimitiveFloat(ClassNode type) {
        return type.redirect() == float_TYPE;
    }

    public static boolean isPrimitiveVoid(ClassNode type) {
        return type.redirect() == VOID_TYPE;
    }

    public static boolean isWrapperBoolean(ClassNode type) {
        return type != null && type.redirect() == Boolean_TYPE;
    }

    public static boolean isWrapperCharacter(ClassNode type) {
        return type != null && type.redirect() == Character_TYPE;
    }

    public static boolean isWrapperByte(ClassNode type) {
        return type != null && type.redirect() == Byte_TYPE;
    }

    public static boolean isWrapperInteger(ClassNode type) {
        return type != null && type.redirect() == Integer_TYPE;
    }

    public static boolean isWrapperLong(ClassNode type) {
        return type != null && type.redirect() == Long_TYPE;
    }

    public static boolean isWrapperShort(ClassNode type) {
        return type != null && type.redirect() == Short_TYPE;
    }

    public static boolean isWrapperDouble(ClassNode type) {
        return type != null && type.redirect() == Double_TYPE;
    }

    public static boolean isWrapperFloat(ClassNode type) {
        return type != null && type.redirect() == Float_TYPE;
    }

    public static boolean isWrapperVoid(ClassNode type) {
        return type != null && type.redirect() == void_WRAPPER_TYPE;
    }

    public static boolean isBigIntegerType(ClassNode type) {
        return BigInteger_TYPE.equals(type);
    }

    public static boolean isBigDecimalType(ClassNode type) {
        return BigDecimal_TYPE.equals(type);
    }

    public static boolean isStringType(ClassNode type) {
        return STRING_TYPE.equals(type);
    }

    public static boolean isGStringType(ClassNode type) {
        return GSTRING_TYPE.equals(type);
    }

    public static boolean isObjectType(ClassNode type) {
        return OBJECT_TYPE.equals(type);
    }

    public static boolean isGroovyObjectType(ClassNode type) {
        return GROOVY_OBJECT_TYPE.equals(type);
    }

    public static boolean isClassType(ClassNode type) {
        return CLASS_Type.equals(type);
    }

    public static boolean isSAMType(ClassNode type) {
        return ClassHelper.findSAM(type) != null;
    }

    public static boolean isFunctionalInterface(ClassNode type) {
        return type != null && type.isInterface() && ClassHelper.isSAMType(type);
    }

    public static boolean isGeneratedFunction(ClassNode type) {
        return type.implementsAnyInterfaces(GENERATED_CLOSURE_Type, GENERATED_LAMBDA_TYPE);
    }

    public static MethodNode findSAM(ClassNode type) {
        if (type.isInterface()) {
            MethodNode sam = null;
            for (MethodNode mn : type.getAbstractMethods()) {
                if (Traits.hasDefaultImplementation(mn) || OBJECT_TYPE.getDeclaredMethod(mn.getName(), mn.getParameters()) != null) continue;
                if (sam != null) {
                    return null;
                }
                sam = mn;
            }
            return sam;
        }
        if (type.isAbstract()) {
            MethodNode sam = null;
            for (MethodNode mn : type.getAbstractMethods()) {
                if (ClassHelper.hasUsableImplementation(type, mn)) continue;
                if (sam != null) {
                    return null;
                }
                sam = mn;
            }
            return sam;
        }
        return null;
    }

    private static boolean hasUsableImplementation(ClassNode c, MethodNode m) {
        ClassNode declaringClass = m.getDeclaringClass();
        if (c.equals(declaringClass)) {
            return false;
        }
        if (ClassHelper.isGroovyObjectType(declaringClass) && c.getCompileUnit() != null) {
            return true;
        }
        MethodNode found = c.getDeclaredMethod(m.getName(), m.getParameters());
        if (found == null) {
            return false;
        }
        int modifiers = found.getModifiers() & 0x40F;
        if (modifiers == 1 || modifiers == 4) {
            return true;
        }
        return !ClassHelper.isObjectType(c) && ClassHelper.hasUsableImplementation(c.getSuperClass(), m);
    }

    public static ClassNode getNextSuperClass(ClassNode source, ClassNode target) {
        if (source.isArray()) {
            if (!target.isArray()) {
                return null;
            }
            ClassNode cn = ClassHelper.getNextSuperClass(source.getComponentType(), target.getComponentType());
            if (cn != null) {
                cn = cn.makeArray();
            }
            return cn;
        }
        if (target.isInterface()) {
            for (ClassNode face : source.getUnresolvedInterfaces()) {
                if (!StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(face, target)) continue;
                return face;
            }
        } else if (source.isInterface()) {
            return OBJECT_TYPE;
        }
        return source.getUnresolvedSuperClass();
    }

    static class ClassHelperCache {
        static ManagedIdentityConcurrentMap<Class, SoftReference<ClassNode>> classCache = new ManagedIdentityConcurrentMap(128);

        ClassHelperCache() {
        }
    }
}

