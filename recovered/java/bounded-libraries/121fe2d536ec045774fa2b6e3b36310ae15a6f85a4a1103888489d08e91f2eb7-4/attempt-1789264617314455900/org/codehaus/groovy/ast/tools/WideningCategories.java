/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.IntStream;
import org.apache.groovy.util.Maps;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.tools.GenericsUtils;

public class WideningCategories {
    private static final Map<ClassNode, Integer> NUMBER_TYPES_PRECEDENCE = Maps.of(ClassHelper.double_TYPE, 0, ClassHelper.float_TYPE, 1, ClassHelper.long_TYPE, 2, ClassHelper.int_TYPE, 3, ClassHelper.short_TYPE, 4, ClassHelper.byte_TYPE, 5);

    public static boolean isInt(ClassNode type) {
        return ClassHelper.isPrimitiveInt(type);
    }

    public static boolean isDouble(ClassNode type) {
        return ClassHelper.isPrimitiveDouble(type);
    }

    public static boolean isFloat(ClassNode type) {
        return ClassHelper.isPrimitiveFloat(type);
    }

    public static boolean isIntCategory(ClassNode type) {
        return ClassHelper.isPrimitiveByte(type) || ClassHelper.isPrimitiveChar(type) || ClassHelper.isPrimitiveInt(type) || ClassHelper.isPrimitiveShort(type);
    }

    public static boolean isLongCategory(ClassNode type) {
        return ClassHelper.isPrimitiveLong(type) || WideningCategories.isIntCategory(type);
    }

    public static boolean isBigIntCategory(ClassNode type) {
        return ClassHelper.isBigIntegerType(type) || WideningCategories.isLongCategory(type);
    }

    public static boolean isBigDecCategory(ClassNode type) {
        return ClassHelper.isBigDecimalType(type) || WideningCategories.isBigIntCategory(type);
    }

    public static boolean isDoubleCategory(ClassNode type) {
        return ClassHelper.isPrimitiveFloat(type) || ClassHelper.isPrimitiveDouble(type) || WideningCategories.isBigDecCategory(type);
    }

    public static boolean isFloatingCategory(ClassNode type) {
        return ClassHelper.isPrimitiveFloat(type) || ClassHelper.isPrimitiveDouble(type);
    }

    public static boolean isNumberCategory(ClassNode type) {
        return WideningCategories.isBigDecCategory(type) || type.isDerivedFrom(ClassHelper.Number_TYPE);
    }

    public static ClassNode lowestUpperBound(List<ClassNode> nodes) {
        if (nodes.size() == 1) {
            return nodes.get(0);
        }
        return WideningCategories.lowestUpperBound(nodes.get(0), WideningCategories.lowestUpperBound(nodes.subList(1, nodes.size())));
    }

    public static ClassNode lowestUpperBound(ClassNode a, ClassNode b) {
        ClassNode lub = WideningCategories.lowestUpperBound(a, b, null, null);
        if (lub == null || !lub.isUsingGenerics() || lub.isGenericsPlaceHolder()) {
            return lub;
        }
        if (lub instanceof LowestUpperBoundClassNode) {
            ClassNode superClass = lub.getSuperClass();
            if (superClass.redirect().getGenericsTypes() != null) {
                superClass = WideningCategories.parameterizeLowestUpperBound(superClass, a, b, lub);
            }
            ClassNode[] interfaces = (ClassNode[])lub.getInterfaces().clone();
            int n = interfaces.length;
            for (int i = 0; i < n; ++i) {
                ClassNode icn = interfaces[i];
                if (icn.redirect().getGenericsTypes() == null) continue;
                interfaces[i] = WideningCategories.parameterizeLowestUpperBound(icn, a, b, lub);
            }
            return new LowestUpperBoundClassNode(((LowestUpperBoundClassNode)lub).name, superClass, interfaces);
        }
        return WideningCategories.parameterizeLowestUpperBound(lub, a, b, lub);
    }

    private static ClassNode parameterizeLowestUpperBound(ClassNode lub, ClassNode a, ClassNode b, ClassNode fallback) {
        GenericsType[] bgt;
        if (a.toString(false).equals(b.toString(false))) {
            return lub;
        }
        ClassNode holderForA = WideningCategories.findGenericsTypeHolderForClass(a, lub);
        ClassNode holderForB = WideningCategories.findGenericsTypeHolderForClass(b, lub);
        GenericsType[] agt = holderForA == null ? null : holderForA.getGenericsTypes();
        GenericsType[] genericsTypeArray = bgt = holderForB == null ? null : holderForB.getGenericsTypes();
        if (agt == null || bgt == null || agt.length != bgt.length) {
            return lub;
        }
        int n = agt.length;
        GenericsType[] lubGTs = new GenericsType[n];
        for (int i = 0; i < n; ++i) {
            ClassNode t1 = WideningCategories.upperBound(agt[i]);
            ClassNode t2 = WideningCategories.upperBound(bgt[i]);
            ClassNode basicType = WideningCategories.areEqualWithGenerics(t1, ClassHelper.isPrimitiveType(a) ? ClassHelper.getWrapper(a) : a) && WideningCategories.areEqualWithGenerics(t2, ClassHelper.isPrimitiveType(b) ? ClassHelper.getWrapper(b) : b) ? fallback : WideningCategories.lowestUpperBound(t1, t2);
            lubGTs[i] = agt[i].isWildcard() || bgt[i].isWildcard() || !t1.equals(t2) ? GenericsUtils.buildWildcardType(basicType) : basicType.asGenericsType();
        }
        return GenericsUtils.makeClassSafe0(lub, lubGTs);
    }

    private static ClassNode findGenericsTypeHolderForClass(ClassNode source, ClassNode target) {
        if (ClassHelper.isPrimitiveType(source)) {
            source = ClassHelper.getWrapper(source);
        }
        if (source.equals(target)) {
            return source;
        }
        if (target.isInterface() ? source.implementsInterface(target) : source.isDerivedFrom(target)) {
            ClassNode sc;
            do {
                if (!GenericsUtils.hasUnresolvedGenerics(sc = ClassHelper.getNextSuperClass(source, target))) continue;
                sc = GenericsUtils.correctToGenericsSpecRecurse(GenericsUtils.createGenericsSpec(source), sc);
            } while (!(source = sc).equals(target));
            return sc;
        }
        return null;
    }

    private static ClassNode upperBound(GenericsType gt) {
        ClassNode[] ub;
        if (gt.isWildcard() && (ub = gt.getUpperBounds()) != null) {
            return ub[0];
        }
        return gt.getType();
    }

    private static ClassNode lowestUpperBound(ClassNode a, ClassNode b, Set<ClassNode> interfacesImplementedByA, Set<ClassNode> interfacesImplementedByB) {
        if (a == null || b == null) {
            return null;
        }
        if (a.isArray() && b.isArray()) {
            return WideningCategories.lowestUpperBound(a.getComponentType(), b.getComponentType(), interfacesImplementedByA, interfacesImplementedByB).makeArray();
        }
        if (ClassHelper.isObjectType(a) || ClassHelper.isObjectType(b)) {
            GenericsType[] gta = a.getGenericsTypes();
            GenericsType[] gtb = b.getGenericsTypes();
            if (gta != null && gtb != null && gta.length == 1 && gtb.length == 1 && gta[0].getName().equals(gtb[0].getName())) {
                return a;
            }
            return ClassHelper.OBJECT_TYPE;
        }
        if (ClassHelper.isPrimitiveVoid(a) || ClassHelper.isPrimitiveVoid(b)) {
            if (!b.equals(a)) {
                return ClassHelper.OBJECT_TYPE;
            }
            return ClassHelper.VOID_TYPE;
        }
        boolean isPrimitiveA = ClassHelper.isPrimitiveType(a);
        boolean isPrimitiveB = ClassHelper.isPrimitiveType(b);
        if (isPrimitiveA && !isPrimitiveB) {
            return WideningCategories.lowestUpperBound(ClassHelper.getWrapper(a), b, null, null);
        }
        if (isPrimitiveB && !isPrimitiveA) {
            return WideningCategories.lowestUpperBound(a, ClassHelper.getWrapper(b), null, null);
        }
        if (isPrimitiveA && isPrimitiveB) {
            Integer pa = NUMBER_TYPES_PRECEDENCE.get(a);
            Integer pb = NUMBER_TYPES_PRECEDENCE.get(b);
            if (pa != null && pb != null) {
                if (pa <= pb) {
                    return a;
                }
                return b;
            }
            return a.equals(b) ? a : WideningCategories.lowestUpperBound(ClassHelper.getWrapper(a), ClassHelper.getWrapper(b), null, null);
        }
        if (ClassHelper.isNumberType(a.redirect()) && ClassHelper.isNumberType(b.redirect())) {
            ClassNode ua = ClassHelper.getUnwrapper(a);
            ClassNode ub = ClassHelper.getUnwrapper(b);
            Integer pa = NUMBER_TYPES_PRECEDENCE.get(ua);
            Integer pb = NUMBER_TYPES_PRECEDENCE.get(ub);
            if (pa != null && pb != null) {
                return pa <= pb ? a : b;
            }
        }
        boolean isInterfaceA = a.isInterface();
        boolean isInterfaceB = b.isInterface();
        if (isInterfaceA && isInterfaceB) {
            if (a.equals(b)) {
                return a;
            }
            if (b.implementsInterface(a)) {
                return a;
            }
            if (a.implementsInterface(b)) {
                return b;
            }
            ClassNode[] interfacesFromA = a.getInterfaces();
            ClassNode[] interfacesFromB = b.getInterfaces();
            HashSet<ClassNode> common = new HashSet<ClassNode>();
            Collections.addAll(common, interfacesFromA);
            HashSet fromB = new HashSet();
            Collections.addAll(fromB, interfacesFromB);
            common.retainAll(fromB);
            if (common.size() == 1) {
                return (ClassNode)common.iterator().next();
            }
            if (common.size() > 1) {
                return WideningCategories.buildTypeWithInterfaces(a, b, common);
            }
            return ClassHelper.OBJECT_TYPE;
        }
        if (isInterfaceB) {
            return WideningCategories.lowestUpperBound(b, a, null, null);
        }
        if (isInterfaceA) {
            LinkedList<ClassNode> matchingInterfaces = new LinkedList<ClassNode>();
            WideningCategories.extractMostSpecificImplementedInterfaces(b, a, matchingInterfaces);
            if (matchingInterfaces.isEmpty()) {
                return ClassHelper.OBJECT_TYPE;
            }
            if (matchingInterfaces.size() == 1) {
                return (ClassNode)matchingInterfaces.get(0);
            }
            return WideningCategories.buildTypeWithInterfaces(a, b, matchingInterfaces);
        }
        if (a.equals(b)) {
            return WideningCategories.buildTypeWithInterfaces(a, b, WideningCategories.keepLowestCommonInterfaces(interfacesImplementedByA, interfacesImplementedByB));
        }
        if (a.isDerivedFrom(b) || b.isDerivedFrom(a)) {
            return WideningCategories.buildTypeWithInterfaces(a, b, WideningCategories.keepLowestCommonInterfaces(interfacesImplementedByA, interfacesImplementedByB));
        }
        ClassNode sa = a.getUnresolvedSuperClass();
        ClassNode sb = b.getUnresolvedSuperClass();
        if (interfacesImplementedByA == null) {
            interfacesImplementedByA = GeneralUtils.getInterfacesAndSuperInterfaces(a);
        }
        if (interfacesImplementedByB == null) {
            interfacesImplementedByB = GeneralUtils.getInterfacesAndSuperInterfaces(b);
        }
        if (sa == null || sb == null) {
            return WideningCategories.buildTypeWithInterfaces(ClassHelper.OBJECT_TYPE, ClassHelper.OBJECT_TYPE, WideningCategories.keepLowestCommonInterfaces(interfacesImplementedByA, interfacesImplementedByB));
        }
        if (sa.isDerivedFrom(sb) || sb.isDerivedFrom(sa)) {
            return WideningCategories.buildTypeWithInterfaces(sa, sb, WideningCategories.keepLowestCommonInterfaces(interfacesImplementedByA, interfacesImplementedByB));
        }
        return WideningCategories.lowestUpperBound(sa, sb, interfacesImplementedByA, interfacesImplementedByB);
    }

    private static List<ClassNode> keepLowestCommonInterfaces(Set<ClassNode> fromA, Set<ClassNode> fromB) {
        if (fromA == null || fromB == null) {
            return Collections.emptyList();
        }
        HashSet<ClassNode> common = new HashSet<ClassNode>(fromA);
        common.retainAll(fromB);
        ArrayList<ClassNode> result = new ArrayList<ClassNode>(common.size());
        for (ClassNode classNode : common) {
            WideningCategories.addMostSpecificInterface(classNode, result);
        }
        return result;
    }

    private static void addMostSpecificInterface(ClassNode interfaceNode, List<ClassNode> nodes) {
        if (nodes.isEmpty()) {
            nodes.add(interfaceNode);
        }
        int n = nodes.size();
        for (int i = 0; i < n; ++i) {
            ClassNode node = nodes.get(i);
            if (node.equals(interfaceNode) || node.implementsInterface(interfaceNode)) {
                return;
            }
            if (!interfaceNode.implementsInterface(node)) continue;
            nodes.set(i, interfaceNode);
            return;
        }
        nodes.add(interfaceNode);
    }

    private static void extractMostSpecificImplementedInterfaces(ClassNode type, ClassNode inode, List<ClassNode> result) {
        if (type.implementsInterface(inode)) {
            result.add(inode);
        } else {
            ClassNode[] interfaces;
            for (ClassNode interfaceNode : interfaces = inode.getInterfaces()) {
                if (!type.implementsInterface(interfaceNode)) continue;
                result.add(interfaceNode);
            }
            if (result.isEmpty() && interfaces.length > 0) {
                for (ClassNode interfaceNode : interfaces) {
                    WideningCategories.extractMostSpecificImplementedInterfaces(type, interfaceNode, result);
                }
            }
        }
    }

    private static ClassNode buildTypeWithInterfaces(ClassNode baseType1, ClassNode baseType2, Collection<ClassNode> interfaces) {
        ClassNode superClass;
        String name;
        if (interfaces.isEmpty()) {
            if (baseType2.isDerivedFrom(baseType1)) {
                return baseType1;
            }
            if (baseType1.isDerivedFrom(baseType2)) {
                return baseType2;
            }
        }
        if (baseType1.equals(baseType2)) {
            name = "Virtual$" + baseType1.getName();
            superClass = baseType1;
        } else {
            name = "CommonAssignOf$" + baseType1.getName() + "$" + baseType2.getName();
            superClass = baseType1.isDerivedFrom(baseType2) ? baseType2 : (baseType2.isDerivedFrom(baseType1) ? baseType1 : ClassHelper.OBJECT_TYPE);
        }
        interfaces.removeIf(i -> superClass.equals(i) || superClass.implementsInterface((ClassNode)i));
        int nInterfaces = interfaces.size();
        if (nInterfaces == 0) {
            return superClass;
        }
        if (nInterfaces == 1 && ClassHelper.isObjectType(superClass)) {
            return interfaces.iterator().next();
        }
        return new LowestUpperBoundClassNode(name, superClass, interfaces.toArray(ClassNode.EMPTY_ARRAY));
    }

    private static boolean areEqualWithGenerics(ClassNode a, ClassNode b) {
        if (a == null) {
            return b == null;
        }
        if (!a.equals(b)) {
            return false;
        }
        if (a.isUsingGenerics() && !b.isUsingGenerics()) {
            return false;
        }
        GenericsType[] gta = a.getGenericsTypes();
        GenericsType[] gtb = b.getGenericsTypes();
        if (gta == null && gtb != null) {
            return false;
        }
        if (gtb == null && gta != null) {
            return false;
        }
        if (gta != null && gtb != null) {
            if (gta.length != gtb.length) {
                return false;
            }
            int n = gta.length;
            for (int i = 0; i < n; ++i) {
                GenericsType gta_i = gta[i];
                GenericsType gtb_i = gtb[i];
                ClassNode[] upperA = gta_i.getUpperBounds();
                ClassNode[] upperB = gtb_i.getUpperBounds();
                if (gta_i.isPlaceholder() == gtb_i.isPlaceholder() && gta_i.isWildcard() == gtb_i.isWildcard() && gta_i.getName().equals(gtb_i.getName()) && WideningCategories.areEqualWithGenerics(gta_i.getType(), gtb_i.getType()) && WideningCategories.areEqualWithGenerics(gta_i.getLowerBound(), gtb_i.getLowerBound()) && !(upperA == null ? upperB != null : upperB.length != upperA.length || IntStream.range(0, upperA.length).anyMatch(j -> !WideningCategories.areEqualWithGenerics(upperA[j], upperB[j])))) continue;
                return false;
            }
        }
        return true;
    }

    public static boolean implementsInterfaceOrSubclassOf(ClassNode source, ClassNode targetType) {
        if (source.isDerivedFrom(targetType) || source.implementsInterface(targetType)) {
            return true;
        }
        if (targetType instanceof LowestUpperBoundClassNode) {
            LowestUpperBoundClassNode lub = (LowestUpperBoundClassNode)targetType;
            if (WideningCategories.implementsInterfaceOrSubclassOf(source, lub.getSuperClass())) {
                return true;
            }
            for (ClassNode classNode : lub.getInterfaces()) {
                if (!source.implementsInterface(classNode)) continue;
                return true;
            }
        }
        return false;
    }

    public static class LowestUpperBoundClassNode
    extends ClassNode {
        private final String name;
        private final String text;
        private final ClassNode upper;
        private final ClassNode[] interfaces;
        private final ClassNode compileTimeClassNode;

        public LowestUpperBoundClassNode(String name, ClassNode upper, ClassNode ... interfaces) {
            super(name, 17, upper, interfaces, null);
            this.name = name;
            this.upper = upper;
            this.interfaces = interfaces;
            Arrays.sort(interfaces, (cn1, cn2) -> {
                String n1 = cn1 instanceof LowestUpperBoundClassNode ? ((LowestUpperBoundClassNode)cn1).name : cn1.getName();
                String n2 = cn2 instanceof LowestUpperBoundClassNode ? ((LowestUpperBoundClassNode)cn2).name : cn2.getName();
                return n1.compareTo(n2);
            });
            this.compileTimeClassNode = ClassHelper.isObjectType(upper) && interfaces.length > 0 ? interfaces[0] : upper;
            StringJoiner sj = new StringJoiner(" or ", "(", ")");
            if (!ClassHelper.isObjectType(upper)) {
                sj.add(upper.getText());
            }
            for (ClassNode i : interfaces) {
                sj.add(i.getText());
            }
            this.text = sj.toString();
            boolean usesGenerics = upper.isUsingGenerics();
            ArrayList<GenericsType[]> genericsTypesList = new ArrayList<GenericsType[]>();
            genericsTypesList.add(upper.getGenericsTypes());
            for (ClassNode anInterface : interfaces) {
                usesGenerics |= anInterface.isUsingGenerics();
                genericsTypesList.add(anInterface.getGenericsTypes());
                for (MethodNode methodNode : anInterface.getMethods()) {
                    MethodNode method = this.addMethod(methodNode.getName(), methodNode.getModifiers(), methodNode.getReturnType(), methodNode.getParameters(), methodNode.getExceptions(), methodNode.getCode());
                    method.setDeclaringClass(anInterface);
                }
            }
            this.setUsingGenerics(usesGenerics);
            if (usesGenerics) {
                ArrayList flatList = new ArrayList();
                for (GenericsType[] gts : genericsTypesList) {
                    if (gts == null) continue;
                    Collections.addAll(flatList, gts);
                }
                this.setGenericsTypes(flatList.toArray(GenericsType.EMPTY_ARRAY));
            }
        }

        public String getLubName() {
            return this.name;
        }

        @Override
        public String getText() {
            return this.text;
        }

        @Override
        public String getName() {
            return this.compileTimeClassNode.getName();
        }

        @Override
        public Class getTypeClass() {
            return this.compileTimeClassNode.getTypeClass();
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (this.name != null ? this.name.hashCode() : 0);
            return result;
        }

        @Override
        public String toString(boolean x) {
            return this.text;
        }

        @Override
        public GenericsType asGenericsType() {
            ClassNode[] ubs;
            if (ClassHelper.isObjectType(this.upper)) {
                ubs = this.interfaces;
            } else {
                ubs = new ClassNode[this.interfaces.length + 1];
                ubs[0] = this.upper;
                System.arraycopy(this.interfaces, 0, ubs, 1, this.interfaces.length);
            }
            GenericsType gt = new GenericsType(ClassHelper.makeWithoutCaching("?"), ubs, null);
            gt.setWildcard(true);
            return gt;
        }

        @Override
        public ClassNode getPlainNodeReference() {
            ClassNode[] faces = (ClassNode[])this.interfaces.clone();
            for (int i = 0; i < this.interfaces.length; ++i) {
                faces[i] = this.interfaces[i].getPlainNodeReference();
            }
            return new LowestUpperBoundClassNode(this.name, this.upper.getPlainNodeReference(), faces);
        }
    }
}

