/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.tools;

import groovy.transform.Internal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.groovy.ast.tools.AnnotatedNodeUtils;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.stmt.Statement;

public class BeanUtils {
    static final String GET_PREFIX = "get";
    static final String SET_PREFIX = "set";
    static final String IS_PREFIX = "is";
    private static final ClassNode INTERNAL_TYPE = ClassHelper.make(Internal.class);

    public static List<PropertyNode> getAllProperties(ClassNode type, boolean includeSuperProperties, boolean includeStatic, boolean includePseudoGetters) {
        return BeanUtils.getAllProperties(type, includeSuperProperties, includeStatic, includePseudoGetters, false, false);
    }

    public static List<PropertyNode> getAllProperties(ClassNode type, boolean includeSuperProperties, boolean includeStatic, boolean includePseudoGetters, boolean includePseudoSetters, boolean superFirst) {
        return BeanUtils.getAllProperties(type, type, new HashSet<String>(), includeSuperProperties, includeStatic, includePseudoGetters, includePseudoSetters, superFirst);
    }

    private static List<PropertyNode> getAllProperties(ClassNode origType, ClassNode type, Set<String> names, boolean includeSuperProperties, boolean includeStatic, boolean includePseudoGetters, boolean includePseudoSetters, boolean superFirst) {
        if (type == null) {
            return new ArrayList<PropertyNode>();
        }
        ArrayList<PropertyNode> result = new ArrayList<PropertyNode>();
        if (superFirst && includeSuperProperties) {
            result.addAll(BeanUtils.getAllProperties(origType, type.getSuperClass(), names, includeSuperProperties, includeStatic, includePseudoGetters, includePseudoSetters, superFirst));
        }
        BeanUtils.addExplicitProperties(type, result, names, includeStatic);
        BeanUtils.addPseudoProperties(origType, type, result, names, includeStatic, includePseudoGetters, includePseudoSetters);
        if (!superFirst && includeSuperProperties) {
            result.addAll(BeanUtils.getAllProperties(origType, type.getSuperClass(), names, includeSuperProperties, includeStatic, includePseudoGetters, includePseudoSetters, superFirst));
        }
        return result;
    }

    private static void addExplicitProperties(ClassNode cNode, List<PropertyNode> result, Set<String> names, boolean includeStatic) {
        for (PropertyNode pNode : cNode.getProperties()) {
            if (!includeStatic && pNode.isStatic() || names.contains(pNode.getName())) continue;
            result.add(pNode);
            names.add(pNode.getName());
        }
    }

    public static void addPseudoProperties(ClassNode origType, ClassNode cNode, List<PropertyNode> result, Set<String> names, boolean includeStatic, boolean includePseudoGetters, boolean includePseudoSetters) {
        if (!includePseudoGetters && !includePseudoSetters) {
            return;
        }
        List<MethodNode> methods = cNode.getAllDeclaredMethods();
        for (MethodNode mNode : methods) {
            String name;
            if (!includeStatic && mNode.isStatic() || AnnotatedNodeUtils.hasAnnotation(mNode, INTERNAL_TYPE) || (name = mNode.getName()).length() <= 3 && !name.startsWith(IS_PREFIX) || name.equals("getClass") || name.equals("getMetaClass") || name.equals("getDeclaringClass") || mNode.getDeclaringClass() != origType && mNode.isPrivate()) continue;
            int paramCount = mNode.getParameters().length;
            ClassNode paramType = mNode.getReturnType();
            String propName = null;
            Statement getter = null;
            Statement setter = null;
            if (paramCount == 0) {
                if (includePseudoGetters && name.startsWith(GET_PREFIX)) {
                    propName = org.apache.groovy.util.BeanUtils.decapitalize(name.substring(3));
                    getter = mNode.getCode();
                } else if (includePseudoGetters && name.startsWith(IS_PREFIX) && ClassHelper.isPrimitiveBoolean(paramType)) {
                    propName = org.apache.groovy.util.BeanUtils.decapitalize(name.substring(2));
                    getter = mNode.getCode();
                }
            } else if (paramCount == 1 && includePseudoSetters && name.startsWith(SET_PREFIX)) {
                propName = org.apache.groovy.util.BeanUtils.decapitalize(name.substring(3));
                setter = mNode.getCode();
                paramType = mNode.getParameters()[0].getType();
            }
            if (propName == null) continue;
            BeanUtils.addIfMissing(cNode, result, names, mNode, paramType, propName, getter, setter);
        }
    }

    private static void addIfMissing(ClassNode cNode, List<PropertyNode> result, Set<String> names, MethodNode mNode, ClassNode returnType, String propName, Statement getter, Statement setter) {
        if (cNode.getProperty(propName) != null) {
            return;
        }
        if (names.contains(propName)) {
            for (PropertyNode pn : result) {
                if (pn.getName().equals(propName) && getter != null && pn.getGetterBlock() == null) {
                    pn.setGetterBlock(getter);
                }
                if (!pn.getName().equals(propName) || setter == null || pn.getSetterBlock() != null) continue;
                pn.setSetterBlock(setter);
            }
        } else {
            result.add(new PropertyNode(propName, mNode.getModifiers(), returnType, cNode, null, getter, setter));
            names.add(propName);
        }
    }
}

