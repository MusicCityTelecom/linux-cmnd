/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen.asm.util;

import groovy.lang.Tuple2;
import groovyjarjarasm.asm.Type;
import java.util.Map;
import org.apache.groovy.util.Maps;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;

public abstract class TypeUtil {
    private static final String REF_DESCRIPTION = "L";
    private static final Map<ClassNode, String> PRIMITIVE_TYPE_TO_DESCRIPTION_MAP = Maps.of(ClassHelper.int_TYPE, "I", ClassHelper.VOID_TYPE, "V", ClassHelper.boolean_TYPE, "Z", ClassHelper.byte_TYPE, "B", ClassHelper.char_TYPE, "C", ClassHelper.short_TYPE, "S", ClassHelper.double_TYPE, "D", ClassHelper.float_TYPE, "F", ClassHelper.long_TYPE, "J");
    private static final Map<String, ClassNode> NAME_TO_PRIMITIVE_TYPE_MAP = Maps.of("int", ClassHelper.int_TYPE, "void", ClassHelper.VOID_TYPE, "boolean", ClassHelper.boolean_TYPE, "byte", ClassHelper.byte_TYPE, "char", ClassHelper.char_TYPE, "short", ClassHelper.short_TYPE, "double", ClassHelper.double_TYPE, "float", ClassHelper.float_TYPE, "long", ClassHelper.long_TYPE);
    private static final Map<Type, Integer> PRIMITIVE_TYPE_TO_LOAD_INSN_MAP = Maps.of(Type.BOOLEAN_TYPE, 21, Type.BYTE_TYPE, 21, Type.CHAR_TYPE, 21, Type.DOUBLE_TYPE, 24, Type.FLOAT_TYPE, 23, Type.INT_TYPE, 21, Type.LONG_TYPE, 22, Type.SHORT_TYPE, 21);
    private static final Map<Type, Integer> PRIMITIVE_TYPE_TO_RETURN_INSN_MAP = Maps.of(Type.BOOLEAN_TYPE, 172, Type.BYTE_TYPE, 172, Type.CHAR_TYPE, 172, Type.DOUBLE_TYPE, 175, Type.FLOAT_TYPE, 174, Type.INT_TYPE, 172, Type.LONG_TYPE, 173, Type.SHORT_TYPE, 172);
    private static final Map<Type, String> PRIMITIVE_TYPE_TO_WRAPPED_CLASS_DESCRIPTOR_MAP = Maps.of(Type.BOOLEAN_TYPE, "java/lang/Boolean", Type.BYTE_TYPE, "java/lang/Byte", Type.CHAR_TYPE, "java/lang/Character", Type.DOUBLE_TYPE, "java/lang/Double", Type.FLOAT_TYPE, "java/lang/Float", Type.INT_TYPE, "java/lang/Integer", Type.LONG_TYPE, "java/lang/Long", Type.SHORT_TYPE, "java/lang/Short");
    private static final Map<Class, Class> PRIMITIVE_TYPE_TO_WRAPPED_CLASS_MAP = Maps.of(Byte.TYPE, Byte.class, Boolean.TYPE, Boolean.class, Character.TYPE, Character.class, Double.TYPE, Double.class, Float.TYPE, Float.class, Integer.TYPE, Integer.class, Long.TYPE, Long.class, Short.TYPE, Short.class);

    public static Class autoboxType(Class type) {
        Class res = PRIMITIVE_TYPE_TO_WRAPPED_CLASS_MAP.get(type);
        return res == null ? type : res;
    }

    public static int getLoadInsnByType(Type type) {
        Integer insn = PRIMITIVE_TYPE_TO_LOAD_INSN_MAP.get(type);
        if (null != insn) {
            return insn;
        }
        return 25;
    }

    public static int getReturnInsnByType(Type type) {
        Integer insn = PRIMITIVE_TYPE_TO_RETURN_INSN_MAP.get(type);
        if (null != insn) {
            return insn;
        }
        return 176;
    }

    public static String getWrappedClassDescriptor(Type type) {
        String desc = PRIMITIVE_TYPE_TO_WRAPPED_CLASS_DESCRIPTOR_MAP.get(type);
        if (null != desc) {
            return desc;
        }
        throw new IllegalArgumentException("Unexpected type class [" + type + "]");
    }

    public static boolean isPrimitiveType(Type type) {
        return PRIMITIVE_TYPE_TO_LOAD_INSN_MAP.containsKey(type);
    }

    public static boolean isPrimitiveType(String name) {
        return NAME_TO_PRIMITIVE_TYPE_MAP.containsKey(name);
    }

    public static boolean isPrimitiveType(ClassNode type) {
        return type != null && PRIMITIVE_TYPE_TO_DESCRIPTION_MAP.containsKey(type.redirect());
    }

    public static String getDescriptionByType(ClassNode type) {
        String desc = PRIMITIVE_TYPE_TO_DESCRIPTION_MAP.get(type);
        if (null == desc) {
            if (!type.isArray()) {
                return TypeUtil.makeRefDescription(type.getName());
            }
            StringBuilder arrayDescription = new StringBuilder(32);
            Tuple2<ClassNode, Integer> arrayInfo = TypeUtil.extractArrayInfo(type);
            int dimension = arrayInfo.getV2();
            for (int i = 0; i < dimension; ++i) {
                arrayDescription.append("[");
            }
            ClassNode componentType = arrayInfo.getV1();
            return arrayDescription.append(TypeUtil.getDescriptionByType(componentType)).toString();
        }
        return desc;
    }

    public static String getDescriptionByName(String name) {
        ClassNode type = NAME_TO_PRIMITIVE_TYPE_MAP.get(name);
        if (null == type) {
            return TypeUtil.makeRefDescription(name);
        }
        return TypeUtil.getDescriptionByType(type);
    }

    private static String makeRefDescription(String name) {
        return REF_DESCRIPTION + name.replace('.', '/') + ";";
    }

    private static Tuple2<ClassNode, Integer> extractArrayInfo(ClassNode type) {
        int dimension = 0;
        do {
            ++dimension;
        } while ((type = type.getComponentType()).isArray());
        return new Tuple2<ClassNode, Integer>(type, dimension);
    }
}

