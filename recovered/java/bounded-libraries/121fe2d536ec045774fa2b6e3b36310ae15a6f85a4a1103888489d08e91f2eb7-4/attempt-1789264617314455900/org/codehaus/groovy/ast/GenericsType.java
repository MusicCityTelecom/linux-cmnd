/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.tools.GenericsUtils;
import org.codehaus.groovy.ast.tools.WideningCategories;
import org.codehaus.groovy.transform.stc.StaticTypeCheckingSupport;

public class GenericsType
extends ASTNode {
    public static final GenericsType[] EMPTY_ARRAY = new GenericsType[0];
    private String name;
    private ClassNode type;
    private final ClassNode lowerBound;
    private final ClassNode[] upperBounds;
    private boolean placeholder;
    private boolean resolved;
    private boolean wildcard;

    public GenericsType(ClassNode type, ClassNode[] upperBounds, ClassNode lowerBound) {
        this.setType(type);
        this.lowerBound = lowerBound;
        this.upperBounds = upperBounds;
        this.placeholder = type.isGenericsPlaceHolder();
        this.setName(this.placeholder ? type.getUnresolvedName() : type.getName());
    }

    public GenericsType(ClassNode basicType) {
        this(basicType, null, null);
    }

    public ClassNode getType() {
        return this.type;
    }

    public void setType(ClassNode type) {
        this.type = Objects.requireNonNull(type);
    }

    public String toString() {
        return GenericsType.toString(this, new HashSet<String>());
    }

    private static String toString(GenericsType gt, Set<String> visited) {
        String name = gt.getName();
        ClassNode type = gt.getType();
        boolean wildcard = gt.isWildcard();
        boolean placeholder = gt.isPlaceholder();
        ClassNode lowerBound = gt.getLowerBound();
        ClassNode[] upperBounds = gt.getUpperBounds();
        if (placeholder) {
            visited.add(name);
        }
        StringBuilder ret = new StringBuilder(wildcard || placeholder ? name : GenericsType.genericsBounds(type, visited));
        if (lowerBound != null) {
            ret.append(" super ").append(GenericsType.genericsBounds(lowerBound, visited));
        } else if (!(upperBounds == null || placeholder && upperBounds.length == 1 && !upperBounds[0].isGenericsPlaceHolder() && upperBounds[0].getName().equals("java.lang.Object"))) {
            ret.append(" extends ");
            int n = upperBounds.length;
            for (int i = 0; i < n; ++i) {
                if (i != 0) {
                    ret.append(" & ");
                }
                ret.append(GenericsType.genericsBounds(upperBounds[i], visited));
            }
        }
        return ret.toString();
    }

    private static String genericsBounds(ClassNode theType, Set<String> visited) {
        StringBuilder ret = GenericsType.appendName(theType, new StringBuilder());
        GenericsType[] genericsTypes = theType.getGenericsTypes();
        if (genericsTypes != null && genericsTypes.length > 0 && !theType.isGenericsPlaceHolder()) {
            ret.append('<');
            int n = genericsTypes.length;
            for (int i = 0; i < n; ++i) {
                GenericsType type;
                if (i != 0) {
                    ret.append(", ");
                }
                if ((type = genericsTypes[i]).isPlaceholder() && visited.contains(type.getName())) {
                    ret.append(type.getName());
                    continue;
                }
                ret.append(GenericsType.toString(type, visited));
            }
            ret.append('>');
        }
        return ret.toString();
    }

    private static StringBuilder appendName(ClassNode theType, StringBuilder sb) {
        if (theType.isArray()) {
            GenericsType.appendName(theType.getComponentType(), sb).append("[]");
        } else if (theType.isGenericsPlaceHolder()) {
            sb.append(theType.getUnresolvedName());
        } else if (theType.getOuterClass() != null) {
            String parentClassNodeName = theType.getOuterClass().getName();
            if (Modifier.isStatic(theType.getModifiers()) || theType.isInterface()) {
                sb.append(parentClassNodeName);
            } else {
                sb.append(GenericsType.genericsBounds(theType.getOuterClass(), new HashSet<String>()));
            }
            sb.append('.');
            sb.append(theType.getName(), parentClassNodeName.length() + 1, theType.getName().length());
        } else {
            sb.append(theType.getName());
        }
        return sb;
    }

    public String getName() {
        return this.isWildcard() ? "?" : this.name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public boolean isResolved() {
        return this.resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public boolean isPlaceholder() {
        return this.placeholder;
    }

    public void setPlaceholder(boolean placeholder) {
        this.placeholder = placeholder;
        this.resolved = this.resolved || placeholder;
        this.wildcard = this.wildcard && !placeholder;
        this.getType().setGenericsPlaceHolder(placeholder);
    }

    public boolean isWildcard() {
        return this.wildcard;
    }

    public void setWildcard(boolean wildcard) {
        this.wildcard = wildcard;
        this.placeholder = this.placeholder && !wildcard;
    }

    public ClassNode getLowerBound() {
        return this.lowerBound;
    }

    public ClassNode[] getUpperBounds() {
        return this.upperBounds;
    }

    public boolean isCompatibleWith(ClassNode classNode) {
        GenericsType[] genericsTypes = classNode.getGenericsTypes();
        if (genericsTypes != null && genericsTypes.length == 0) {
            return true;
        }
        if (classNode.isGenericsPlaceHolder()) {
            if (genericsTypes == null) {
                return true;
            }
            String name = genericsTypes[0].getName();
            if (!this.isWildcard()) {
                return name.equals(this.getName());
            }
            if (this.getLowerBound() != null) {
                ClassNode lowerBound = this.getLowerBound();
                if (name.equals(lowerBound.getUnresolvedName())) {
                    return true;
                }
            } else if (this.getUpperBounds() != null) {
                for (ClassNode upperBound : this.getUpperBounds()) {
                    if (!name.equals(upperBound.getUnresolvedName())) continue;
                    return true;
                }
            }
            return this.checkGenerics(classNode);
        }
        if (this.isWildcard() || this.isPlaceholder()) {
            ClassNode lowerBound = this.getLowerBound();
            if (lowerBound != null) {
                if (!StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(lowerBound, classNode)) {
                    return false;
                }
                return this.checkGenerics(classNode);
            }
            ClassNode[] upperBounds = this.getUpperBounds();
            if (upperBounds != null) {
                for (ClassNode upperBound : upperBounds) {
                    if (StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(classNode, upperBound)) continue;
                    return false;
                }
                return this.checkGenerics(classNode);
            }
            return true;
        }
        return classNode.equals(this.getType()) && GenericsType.compareGenericsWithBound(classNode, this.getType());
    }

    private boolean checkGenerics(ClassNode classNode) {
        ClassNode lowerBound = this.getLowerBound();
        if (lowerBound != null) {
            return GenericsType.compareGenericsWithBound(classNode, lowerBound);
        }
        ClassNode[] upperBounds = this.getUpperBounds();
        if (upperBounds != null) {
            for (ClassNode upperBound : upperBounds) {
                if (GenericsType.compareGenericsWithBound(classNode, upperBound)) continue;
                return false;
            }
        }
        return true;
    }

    private static boolean compareGenericsWithBound(ClassNode classNode, ClassNode bound) {
        if (classNode == null) {
            return false;
        }
        if (bound.getGenericsTypes() == null || classNode.isGenericsPlaceHolder() || classNode.getGenericsTypes() == null && classNode.redirect().getGenericsTypes() != null) {
            return true;
        }
        if (!classNode.equals(bound)) {
            if (bound.isInterface()) {
                for (ClassNode face2 : classNode.getAllInterfaces()) {
                    if (!face2.equals(bound)) continue;
                    if (face2.getGenericsTypes() != null) {
                        face2 = GenericsUtils.parameterizeType(classNode, face2);
                    }
                    return GenericsType.compareGenericsWithBound(face2, bound);
                }
            }
            if (bound instanceof WideningCategories.LowestUpperBoundClassNode && GenericsType.compareGenericsWithBound(classNode, bound.getSuperClass()) && Arrays.stream(bound.getInterfaces()).allMatch(face -> GenericsType.compareGenericsWithBound(classNode, face))) {
                return true;
            }
            if (ClassHelper.isObjectType(classNode)) {
                return false;
            }
            ClassNode superClass = classNode.getUnresolvedSuperClass();
            if (superClass == null) {
                superClass = ClassHelper.OBJECT_TYPE;
            } else if (superClass.getGenericsTypes() != null) {
                superClass = GenericsUtils.parameterizeType(classNode, superClass);
            }
            return GenericsType.compareGenericsWithBound(superClass, bound);
        }
        GenericsType[] cnTypes = classNode.getGenericsTypes();
        if (cnTypes == null) {
            cnTypes = classNode.redirect().getGenericsTypes();
        }
        if (cnTypes == null) {
            return true;
        }
        GenericsType[] redirectBoundGenericTypes = bound.redirect().getGenericsTypes();
        Map<GenericsTypeName, GenericsType> boundPlaceHolders = GenericsUtils.extractPlaceholders(bound);
        Map<GenericsTypeName, GenericsType> classNodePlaceholders = GenericsUtils.extractPlaceholders(classNode);
        boolean match = true;
        block1: for (int i = 0; redirectBoundGenericTypes != null && i < redirectBoundGenericTypes.length && match; ++i) {
            GenericsTypeName name;
            GenericsType redirectBoundType = redirectBoundGenericTypes[i];
            GenericsType classNodeType = cnTypes[i];
            if (classNodeType.isPlaceholder()) {
                name = new GenericsTypeName(classNodeType.getName());
                if (redirectBoundType.isPlaceholder()) {
                    GenericsType boundGenericsType;
                    GenericsTypeName gtn = new GenericsTypeName(redirectBoundType.getName());
                    boolean bl = match = name.equals(gtn) || name.equals(new GenericsTypeName("#" + redirectBoundType.getName()));
                    if (match || (boundGenericsType = boundPlaceHolders.get(gtn)) == null) continue;
                    if (boundGenericsType.isPlaceholder()) {
                        match = true;
                        continue;
                    }
                    if (!boundGenericsType.isWildcard()) continue;
                    if (boundGenericsType.getUpperBounds() != null) {
                        match = classNodeType.isCompatibleWith(boundGenericsType.getUpperBounds()[0]);
                        continue;
                    }
                    if (boundGenericsType.getLowerBound() != null) {
                        match = classNodeType.isCompatibleWith(boundGenericsType.getLowerBound());
                        continue;
                    }
                    match = true;
                    continue;
                }
                match = classNodePlaceholders.getOrDefault(name, classNodeType).isCompatibleWith(redirectBoundType.getType());
                continue;
            }
            if (redirectBoundType.isPlaceholder()) {
                if (classNodeType.isPlaceholder()) {
                    match = classNodeType.getName().equals(redirectBoundType.getName());
                    continue;
                }
                name = new GenericsTypeName(redirectBoundType.getName());
                if (boundPlaceHolders.containsKey(name)) {
                    redirectBoundType = boundPlaceHolders.get(name);
                    if (redirectBoundType.isPlaceholder()) {
                        redirectBoundType = classNodePlaceholders.getOrDefault(name, redirectBoundType);
                    } else if (redirectBoundType.isWildcard()) {
                        if (redirectBoundType.getLowerBound() != null) {
                            GenericsType gt = new GenericsType(redirectBoundType.getLowerBound());
                            if (gt.isPlaceholder()) {
                                gt = classNodePlaceholders.getOrDefault(new GenericsTypeName(gt.getName()), gt);
                            }
                            if (classNodeType.isWildcard()) {
                                if (classNodeType.getLowerBound() != null || classNodeType.getUpperBounds() != null) {
                                    match = classNodeType.checkGenerics(gt.getType());
                                    continue;
                                }
                                match = false;
                                continue;
                            }
                            match = StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(gt.getType(), classNodeType.getType());
                            continue;
                        }
                        if (redirectBoundType.getUpperBounds() == null) continue;
                        for (ClassNode upperBound : redirectBoundType.getUpperBounds()) {
                            GenericsType gt = new GenericsType(upperBound);
                            if (gt.isPlaceholder()) {
                                gt = classNodePlaceholders.getOrDefault(new GenericsTypeName(gt.getName()), gt);
                            }
                            match = classNodeType.isWildcard() ? (classNodeType.getLowerBound() != null ? gt.checkGenerics(classNodeType.getLowerBound()) : (classNodeType.getUpperBounds() != null ? gt.checkGenerics(classNodeType.getUpperBounds()[0]) : !gt.isPlaceholder() && !gt.isWildcard() && ClassHelper.isObjectType(gt.getType()))) : StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(classNodeType.getType(), gt.getType());
                            if (!match) continue block1;
                        }
                        continue;
                    }
                }
                match = redirectBoundType.isCompatibleWith(classNodeType.getType());
                continue;
            }
            match = redirectBoundType.isWildcard() || classNodeType.isCompatibleWith(redirectBoundType.getType());
        }
        return match;
    }

    public static class GenericsTypeName {
        private final String name;

        public GenericsTypeName(String name) {
            this.name = Objects.requireNonNull(name);
        }

        public String getName() {
            return this.name;
        }

        public boolean equals(Object that) {
            if (this == that) {
                return true;
            }
            if (!(that instanceof GenericsTypeName)) {
                return false;
            }
            return this.getName().equals(((GenericsTypeName)that).getName());
        }

        public int hashCode() {
            return this.getName().hashCode();
        }

        public String toString() {
            return this.getName();
        }
    }
}

