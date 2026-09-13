/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen;

import groovy.transform.NonSealed;
import groovy.transform.Sealed;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import org.apache.groovy.ast.tools.AnnotatedNodeUtils;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.Variable;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.GStringExpression;
import org.codehaus.groovy.ast.expr.MapEntryExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.TupleExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.CatchStatement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.tools.ParameterUtils;
import org.codehaus.groovy.classgen.Verifier;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.syntax.Types;
import org.codehaus.groovy.transform.trait.Traits;

public class ClassCompletionVerifier
extends ClassCodeVisitorSupport {
    private static final String[] INVALID_NAME_CHARS = new String[]{".", ":", "/", ";", "[", "<", ">"};
    private final boolean strictNames = Boolean.getBoolean("groovy.compiler.strictNames");
    private boolean inConstructor;
    private boolean inStaticConstructor;
    private final SourceUnit source;
    private ClassNode currentClass;

    public ClassCompletionVerifier(SourceUnit source) {
        this.source = source;
    }

    @Override
    protected SourceUnit getSourceUnit() {
        return this.source;
    }

    public ClassNode getClassNode() {
        return this.currentClass;
    }

    @Override
    public void visitClass(ClassNode node) {
        ClassNode previousClass = this.currentClass;
        this.currentClass = node;
        try {
            this.checkImplementsAndExtends(node);
            if (this.source != null && !this.source.getErrorCollector().hasErrors()) {
                this.checkClassForIncorrectModifiers(node);
                this.checkInterfaceMethodVisibility(node);
                this.checkAbstractMethodVisibility(node);
                this.checkClassForExtendingFinalOrSealed(node);
                this.checkMethodsForIncorrectModifiers(node);
                this.checkMethodsForIncorrectName(node);
                this.checkMethodsForWeakerAccess(node);
                this.checkMethodsForOverridingFinal(node);
                this.checkNoAbstractMethodsNonAbstractClass(node);
                this.checkClassExtendsAllSelfTypes(node);
                this.checkNoStaticMethodWithSameSignatureAsNonStatic(node);
                this.checkGenericsUsage((ASTNode)node, node.getUnresolvedInterfaces());
                this.checkGenericsUsage((ASTNode)node, node.getUnresolvedSuperClass());
            }
            super.visitClass(node);
        }
        finally {
            this.currentClass = previousClass;
        }
    }

    private void checkNoStaticMethodWithSameSignatureAsNonStatic(ClassNode node) {
        ClassNode parent = node.getSuperClass();
        Map<String, MethodNode> result = parent != null ? parent.getDeclaredMethodsMap() : new HashMap<String, MethodNode>();
        ClassNodeUtils.addDeclaredMethodsFromInterfaces(node, result);
        for (MethodNode methodNode : node.getMethods()) {
            MethodNode mn = result.get(methodNode.getTypeDescriptor());
            if (mn != null && mn.isStatic() ^ methodNode.isStatic() && !methodNode.isStaticConstructor()) {
                if (!mn.isAbstract()) continue;
                ClassNode declaringClass = mn.getDeclaringClass();
                ClassNode cn = declaringClass.getOuterClass();
                if (cn == null && declaringClass.isResolved()) {
                    Class<?> typeClass = declaringClass.getTypeClass();
                    if ((typeClass = typeClass.getEnclosingClass()) != null) {
                        cn = ClassHelper.make(typeClass);
                    }
                }
                if (!Traits.isTrait(cn)) {
                    AnnotatedNode errorNode = methodNode;
                    String name = mn.getName();
                    if (errorNode.getLineNumber() == -1) {
                        for (PropertyNode propertyNode : node.getProperties()) {
                            String shortName;
                            String propName;
                            if (!name.startsWith("set") && !name.startsWith("get") && !name.startsWith("is") || !(propName = Verifier.capitalize(propertyNode.getField().getName())).equals(shortName = name.substring(name.startsWith("is") ? 2 : 3))) continue;
                            errorNode = propertyNode;
                            break;
                        }
                    }
                    this.addError("The " + ClassCompletionVerifier.getDescription(methodNode) + " is already defined in " + ClassCompletionVerifier.getDescription(node) + ". You cannot have both a static and an instance method with the same signature", errorNode);
                }
            }
            result.put(methodNode.getTypeDescriptor(), methodNode);
        }
    }

    private void checkInterfaceMethodVisibility(ClassNode node) {
        if (!node.isInterface()) {
            return;
        }
        for (MethodNode method : node.getMethods()) {
            if (method.isPrivate()) {
                this.addError("Method '" + method.getName() + "' is private but should be public in " + ClassCompletionVerifier.getDescription(this.currentClass) + ".", method);
                continue;
            }
            if (!method.isProtected()) continue;
            this.addError("Method '" + method.getName() + "' is protected but should be public in " + ClassCompletionVerifier.getDescription(this.currentClass) + ".", method);
        }
    }

    private void checkAbstractMethodVisibility(ClassNode node) {
        if (!node.isAbstract() || node.isInterface()) {
            return;
        }
        for (MethodNode method : node.getAbstractMethods()) {
            if (!method.isPrivate()) continue;
            this.addError("Method '" + method.getName() + "' from " + ClassCompletionVerifier.getDescription(node) + " must not be private as it is declared as an abstract method.", method);
        }
    }

    private void checkNoAbstractMethodsNonAbstractClass(ClassNode node) {
        if (node.isAbstract()) {
            return;
        }
        for (MethodNode method : node.getAbstractMethods()) {
            String what;
            MethodNode sameArgsMethod = node.getMethod(method.getName(), method.getParameters());
            if (sameArgsMethod == null && (sameArgsMethod = ClassHelper.GROOVY_OBJECT_TYPE.getMethod(method.getName(), method.getParameters())) != null && !sameArgsMethod.isAbstract() && sameArgsMethod.getReturnType().equals(method.getReturnType())) continue;
            AnnotatedNode where = node;
            if (sameArgsMethod == null || sameArgsMethod.getReturnType().equals(method.getReturnType())) {
                what = "Can't have an abstract method in a non-abstract class. The " + ClassCompletionVerifier.getDescription(node) + " must be declared abstract or the " + ClassCompletionVerifier.getDescription(method) + " must be implemented.";
            } else {
                what = "Abstract " + ClassCompletionVerifier.getDescription(method) + " is not implemented but a method of the same name but different return type is defined: " + (sameArgsMethod.isStatic() ? "static " : "") + ClassCompletionVerifier.getDescription(sameArgsMethod);
                where = method;
            }
            this.addError(what, where);
        }
    }

    private void checkClassExtendsAllSelfTypes(ClassNode node) {
        if (node.isInterface()) {
            return;
        }
        for (ClassNode anInterface : GeneralUtils.getInterfacesAndSuperInterfaces(node)) {
            if (!Traits.isTrait(anInterface)) continue;
            LinkedHashSet<ClassNode> selfTypes = new LinkedHashSet<ClassNode>();
            for (ClassNode type : Traits.collectSelfTypes(anInterface, selfTypes, true, false)) {
                if (type.isInterface() && !node.implementsInterface(type)) {
                    this.addError(ClassCompletionVerifier.getDescription(node) + " implements " + ClassCompletionVerifier.getDescription(anInterface) + " but does not implement self type " + ClassCompletionVerifier.getDescription(type), anInterface);
                    continue;
                }
                if (type.isInterface() || node.isDerivedFrom(type)) continue;
                this.addError(ClassCompletionVerifier.getDescription(node) + " implements " + ClassCompletionVerifier.getDescription(anInterface) + " but does not extend self type " + ClassCompletionVerifier.getDescription(type), anInterface);
            }
        }
    }

    private void checkClassForIncorrectModifiers(ClassNode node) {
        this.checkClassForAbstractAndFinal(node);
        this.checkClassForOtherModifiers(node);
    }

    private void checkClassForAbstractAndFinal(ClassNode node) {
        if (!node.isAbstract() || !Modifier.isFinal(node.getModifiers())) {
            return;
        }
        if (node.isInterface()) {
            this.addError("The " + ClassCompletionVerifier.getDescription(node) + " must not be final. It is by definition abstract.", node);
        } else {
            this.addError("The " + ClassCompletionVerifier.getDescription(node) + " must not be both final and abstract.", node);
        }
    }

    private void checkClassForOtherModifiers(ClassNode node) {
        this.checkClassForModifier(node, Modifier.isTransient(node.getModifiers()), "transient");
        this.checkClassForModifier(node, Modifier.isVolatile(node.getModifiers()), "volatile");
        this.checkClassForModifier(node, Modifier.isNative(node.getModifiers()), "native");
        if (!(node instanceof InnerClassNode)) {
            this.checkClassForModifier(node, Modifier.isStatic(node.getModifiers()), "static");
            this.checkClassForModifier(node, Modifier.isPrivate(node.getModifiers()), "private");
        }
    }

    private void checkMethodForModifier(MethodNode node, boolean condition, String modifierName) {
        if (!condition) {
            return;
        }
        this.addError("The " + ClassCompletionVerifier.getDescription(node) + " has an incorrect modifier " + modifierName + ".", node);
    }

    private void checkClassForModifier(ClassNode node, boolean condition, String modifierName) {
        if (!condition) {
            return;
        }
        this.addError("The " + ClassCompletionVerifier.getDescription(node) + " has an incorrect modifier " + modifierName + ".", node);
    }

    private static String getDescription(ClassNode node) {
        return (node.isInterface() ? (Traits.isTrait(node) ? "trait" : "interface") : "class") + " '" + node.getName() + "'";
    }

    private static String getDescription(MethodNode node) {
        return "method '" + node.getTypeDescriptor() + "'";
    }

    private static String getDescription(FieldNode node) {
        return "field '" + node.getName() + "'";
    }

    private static String getDescription(PropertyNode node) {
        return "property '" + node.getName() + "'";
    }

    private static String getDescription(Parameter node) {
        return "parameter '" + node.getName() + "'";
    }

    private void checkAbstractDeclaration(MethodNode methodNode) {
        if (!methodNode.isAbstract() || this.currentClass.isAbstract()) {
            return;
        }
        this.addError("Can't have an abstract method in a non-abstract class. The " + ClassCompletionVerifier.getDescription(this.currentClass) + " must be declared abstract or the method '" + methodNode.getTypeDescriptor() + "' must not be abstract.", methodNode);
    }

    private void checkClassForExtendingFinalOrSealed(ClassNode cn) {
        boolean sealed = Boolean.TRUE.equals(cn.getNodeMetaData(Sealed.class));
        if (sealed && cn.getPermittedSubclasses().isEmpty()) {
            this.addError("Sealed " + ClassCompletionVerifier.getDescription(cn) + " has no explicit or implicit permitted subclasses.", cn);
            return;
        }
        boolean isFinal = Modifier.isFinal(cn.getModifiers());
        if (sealed && isFinal) {
            this.addError("The " + ClassCompletionVerifier.getDescription(cn) + " cannot be both final and sealed.", cn);
            return;
        }
        boolean explicitNonSealed = this.nonSealed(cn);
        ClassNode superCN = cn.getSuperClass();
        boolean sealedSuper = superCN != null && superCN.isSealed();
        boolean sealedInterface = Arrays.stream(cn.getInterfaces()).anyMatch(ClassNode::isSealed);
        boolean nonSealedSuper = superCN != null && this.nonSealed(superCN);
        boolean nonSealedInterface = Arrays.stream(cn.getInterfaces()).anyMatch(this::nonSealed);
        if (!(!explicitNonSealed || sealedSuper || sealedInterface || nonSealedSuper || nonSealedInterface)) {
            this.addError("The " + ClassCompletionVerifier.getDescription(cn) + " cannot be non-sealed as it has no sealed parent.", cn);
            return;
        }
        if (sealedSuper || sealedInterface) {
            if (sealed && explicitNonSealed) {
                this.addError("The " + ClassCompletionVerifier.getDescription(cn) + " cannot be both sealed and non-sealed.", cn);
                return;
            }
            if (isFinal && explicitNonSealed) {
                this.addError("The " + ClassCompletionVerifier.getDescription(cn) + " cannot be both final and non-sealed.", cn);
                return;
            }
            if (sealedSuper) {
                this.checkSealedParent(cn, superCN);
            }
            if (sealedInterface) {
                for (ClassNode candidate : cn.getInterfaces()) {
                    if (!candidate.isSealed()) continue;
                    this.checkSealedParent(cn, candidate);
                }
            }
        }
        if (superCN == null || !Modifier.isFinal(superCN.getModifiers())) {
            return;
        }
        this.addError("You are not allowed to extend the final " + ClassCompletionVerifier.getDescription(superCN) + ".", cn);
    }

    private boolean nonSealed(ClassNode node) {
        return Boolean.TRUE.equals(node.getNodeMetaData(NonSealed.class));
    }

    private void checkSealedParent(ClassNode cn, ClassNode parent) {
        boolean found = false;
        for (ClassNode permitted : parent.getPermittedSubclasses()) {
            if (!permitted.equals(cn)) continue;
            found = true;
            break;
        }
        if (!found) {
            this.addError("The " + ClassCompletionVerifier.getDescription(cn) + " is not a permitted subclass of the sealed " + ClassCompletionVerifier.getDescription(parent) + ".", cn);
        }
    }

    private void checkImplementsAndExtends(ClassNode node) {
        ClassNode sn = node.getSuperClass();
        if (sn != null && sn.isInterface() && !node.isInterface()) {
            this.addError("You are not allowed to extend the " + ClassCompletionVerifier.getDescription(sn) + ", use implements instead.", node);
        }
        for (ClassNode anInterface : node.getInterfaces()) {
            if (!anInterface.isInterface()) {
                this.addError("You are not allowed to implement the " + ClassCompletionVerifier.getDescription(anInterface) + ", use extends instead.", node);
                continue;
            }
            if (!anInterface.isSealed()) continue;
            this.checkSealedParent(node, anInterface);
        }
    }

    private void checkMethodsForIncorrectName(ClassNode cn) {
        if (!this.strictNames) {
            return;
        }
        List<MethodNode> methods = cn.getAllDeclaredMethods();
        for (MethodNode mNode : methods) {
            if (mNode.isConstructor() || mNode.isStaticConstructor()) continue;
            String name = mNode.getName();
            for (String ch : INVALID_NAME_CHARS) {
                if (!name.contains(ch)) continue;
                this.addError("You are not allowed to have '" + ch + "' in a method name", mNode);
            }
        }
    }

    private void checkMethodsForIncorrectModifiers(ClassNode cn) {
        if (!cn.isInterface()) {
            return;
        }
        for (MethodNode method : cn.getMethods()) {
            if (method.isFinal()) {
                this.addError("The " + ClassCompletionVerifier.getDescription(method) + " from " + ClassCompletionVerifier.getDescription(cn) + " must not be final. It is by definition abstract.", method);
            }
            if (!method.isStatic() || method.isStaticConstructor()) continue;
            this.addError("The " + ClassCompletionVerifier.getDescription(method) + " from " + ClassCompletionVerifier.getDescription(cn) + " must not be static. Only fields may be static in an interface.", method);
        }
    }

    private void checkMethodsForWeakerAccess(ClassNode cn) {
        for (MethodNode method : cn.getMethods()) {
            this.checkMethodForWeakerAccessPrivileges(method, cn);
        }
    }

    private void checkMethodsForOverridingFinal(ClassNode cn) {
        block0: for (MethodNode method : cn.getMethods()) {
            Parameter[] params = method.getParameters();
            for (MethodNode superMethod : cn.getSuperClass().getMethods(method.getName())) {
                Parameter[] superParams = superMethod.getParameters();
                if (!ParameterUtils.parametersEqual(params, superParams)) continue;
                if (!superMethod.isFinal()) continue block0;
                this.addInvalidUseOfFinalError(method, params, superMethod.getDeclaringClass());
                return;
            }
        }
    }

    private void addInvalidUseOfFinalError(MethodNode method, Parameter[] parameters, ClassNode superCN) {
        StringBuilder msg = new StringBuilder();
        msg.append("You are not allowed to override the final method ").append(method.getName());
        this.appendParamsDescription(parameters, msg);
        msg.append(" from ").append(ClassCompletionVerifier.getDescription(superCN));
        msg.append(".");
        this.addError(msg.toString(), method);
    }

    private void appendParamsDescription(Parameter[] parameters, StringBuilder msg) {
        msg.append('(');
        boolean needsComma = false;
        for (Parameter parameter : parameters) {
            if (needsComma) {
                msg.append(',');
            } else {
                needsComma = true;
            }
            msg.append(parameter.getType());
        }
        msg.append(')');
    }

    private void addWeakerAccessError(ClassNode cn, MethodNode method, Parameter[] parameters, MethodNode superMethod) {
        StringBuilder msg = new StringBuilder();
        msg.append(method.getName());
        this.appendParamsDescription(parameters, msg);
        msg.append(" in ");
        msg.append(cn.getName());
        msg.append(" cannot override ");
        msg.append(superMethod.getName());
        msg.append(" in ");
        msg.append(superMethod.getDeclaringClass().getName());
        msg.append("; attempting to assign weaker access privileges; was ");
        msg.append(superMethod.isPublic() ? "public" : (superMethod.isProtected() ? "protected" : "package-private"));
        this.addError(msg.toString(), method);
    }

    @Override
    public void visitMethod(MethodNode node) {
        this.inConstructor = false;
        this.inStaticConstructor = node.isStaticConstructor();
        this.checkAbstractDeclaration(node);
        this.checkRepetitiveMethod(node);
        this.checkOverloadingPrivateAndPublic(node);
        this.checkMethodModifiers(node);
        this.checkGenericsUsage((ASTNode)node, node.getParameters());
        this.checkGenericsUsage((ASTNode)node, node.getReturnType());
        for (Parameter param : node.getParameters()) {
            if (!ClassHelper.isPrimitiveVoid(param.getType())) continue;
            this.addError("The " + ClassCompletionVerifier.getDescription(param) + " in " + ClassCompletionVerifier.getDescription(node) + " has invalid type void", param);
        }
        super.visitMethod(node);
    }

    private void checkMethodModifiers(MethodNode node) {
        if ((this.currentClass.getModifiers() & 0x200) != 0) {
            this.checkMethodForModifier(node, Modifier.isStrict(node.getModifiers()), "strictfp");
            this.checkMethodForModifier(node, Modifier.isSynchronized(node.getModifiers()), "synchronized");
            this.checkMethodForModifier(node, Modifier.isNative(node.getModifiers()), "native");
        }
        if (!AnnotatedNodeUtils.isGenerated(node)) {
            this.checkMethodForModifier(node, Modifier.isTransient(node.getModifiers()), "transient");
        }
    }

    private void checkMethodForWeakerAccessPrivileges(MethodNode mn, ClassNode cn) {
        if (mn.isPublic()) {
            return;
        }
        Parameter[] params = mn.getParameters();
        for (MethodNode superMethod : cn.getSuperClass().getMethods(mn.getName())) {
            Parameter[] superParams = superMethod.getParameters();
            if (!ParameterUtils.parametersEqual(params, superParams) || !(mn.isPrivate() && !superMethod.isPrivate() || mn.isProtected() && !superMethod.isProtected() && !superMethod.isPackageScope() && !superMethod.isPrivate()) && (mn.isPrivate() || mn.isProtected() || mn.isPublic() || !superMethod.isPublic() && !superMethod.isProtected())) continue;
            this.addWeakerAccessError(cn, mn, params, superMethod);
            return;
        }
    }

    private void checkOverloadingPrivateAndPublic(MethodNode node) {
        if (node.isStaticConstructor()) {
            return;
        }
        boolean hasPrivate = node.isPrivate();
        boolean hasPublic = node.isPublic();
        for (MethodNode method : this.currentClass.getMethods(node.getName())) {
            if (method == node || !method.getDeclaringClass().equals(node.getDeclaringClass())) continue;
            if (method.isPublic() || method.isProtected()) {
                hasPublic = true;
            } else {
                hasPrivate = true;
            }
            if (!hasPrivate || !hasPublic) continue;
            break;
        }
        if (hasPrivate && hasPublic) {
            this.addError("Mixing private and public/protected methods of the same name causes multimethods to be disabled and is forbidden to avoid surprising behaviour. Renaming the private methods will solve the problem.", node);
        }
    }

    private void checkRepetitiveMethod(MethodNode node) {
        if (node.isStaticConstructor()) {
            return;
        }
        for (MethodNode method : this.currentClass.getMethods(node.getName())) {
            Parameter[] p2;
            Parameter[] p1;
            if (method == node || !method.getDeclaringClass().equals(node.getDeclaringClass()) || (p1 = node.getParameters()).length != (p2 = method.getParameters()).length) continue;
            this.addErrorIfParamsAndReturnTypeEqual(p2, p1, node, method);
        }
    }

    private void addErrorIfParamsAndReturnTypeEqual(Parameter[] p2, Parameter[] p1, MethodNode node, MethodNode element) {
        boolean isEqual = true;
        for (int i = 0; i < p2.length && (isEqual &= p1[i].getType().equals(p2[i].getType())); ++i) {
        }
        if (isEqual &= node.getReturnType().equals(element.getReturnType())) {
            this.addError("Repetitive method name/signature for " + ClassCompletionVerifier.getDescription(node) + " in " + ClassCompletionVerifier.getDescription(this.currentClass) + ".", node);
        }
    }

    @Override
    public void visitField(FieldNode node) {
        if (this.currentClass.getDeclaredField(node.getName()) != node) {
            this.addError("The " + ClassCompletionVerifier.getDescription(node) + " is declared multiple times.", node);
        }
        this.checkInterfaceFieldModifiers(node);
        this.checkInvalidFieldModifiers(node);
        this.checkGenericsUsage((ASTNode)node, node.getType());
        if (ClassHelper.isPrimitiveVoid(node.getType())) {
            this.addError("The " + ClassCompletionVerifier.getDescription(node) + " has invalid type void", node);
        }
        super.visitField(node);
    }

    @Override
    public void visitProperty(PropertyNode node) {
        if (this.currentClass.getProperty(node.getName()) != node) {
            this.addError("The " + ClassCompletionVerifier.getDescription(node) + " is declared multiple times.", node);
        }
        this.checkDuplicateProperties(node);
        this.checkGenericsUsage((ASTNode)node, node.getType());
        super.visitProperty(node);
    }

    private void checkDuplicateProperties(PropertyNode node) {
        ClassNode cn = node.getDeclaringClass();
        String name = node.getName();
        String getterName = node.getGetterNameOrDefault();
        if (Character.isUpperCase(name.charAt(0))) {
            for (PropertyNode otherNode : cn.getProperties()) {
                String otherName = otherNode.getName();
                if (node == otherNode || !getterName.equals(otherNode.getGetterNameOrDefault())) continue;
                String msg = "The field " + name + " and " + otherName + " on the class " + cn.getName() + " will result in duplicate JavaBean properties, which is not allowed";
                this.addError(msg, node);
            }
        }
    }

    private void checkInterfaceFieldModifiers(FieldNode node) {
        if (!this.currentClass.isInterface()) {
            return;
        }
        if ((node.getModifiers() & 0x19) == 0 || (node.getModifiers() & 6) != 0) {
            this.addError("The " + ClassCompletionVerifier.getDescription(node) + " is not 'public static final' but is defined in " + ClassCompletionVerifier.getDescription(this.currentClass) + ".", node);
        }
    }

    private void checkInvalidFieldModifiers(FieldNode node) {
        if ((node.getModifiers() & 0x50) == 80) {
            this.addError("Illegal combination of modifiers, final and volatile, for field '" + node.getName() + "'", node);
        }
    }

    @Override
    public void visitBinaryExpression(BinaryExpression expression) {
        if (expression.getOperation().getType() == 30 && expression.getRightExpression() instanceof MapEntryExpression) {
            this.addError("You tried to use a map entry for an index operation, this is not allowed. Maybe something should be set in parentheses or a comma is missing?", expression.getRightExpression());
        }
        super.visitBinaryExpression(expression);
        if (Types.isAssignment(expression.getOperation().getType())) {
            this.checkFinalFieldAccess(expression.getLeftExpression());
            this.checkSuperOrThisOnLHS(expression.getLeftExpression());
        }
    }

    private void checkSuperOrThisOnLHS(Expression expression) {
        if (!(expression instanceof VariableExpression)) {
            return;
        }
        VariableExpression ve = (VariableExpression)expression;
        if (ve.isThisExpression()) {
            this.addError("cannot have 'this' as LHS of an assignment", expression);
        } else if (ve.isSuperExpression()) {
            this.addError("cannot have 'super' as LHS of an assignment", expression);
        }
    }

    private void checkFinalFieldAccess(Expression expression) {
        if (!(expression instanceof VariableExpression) && !(expression instanceof PropertyExpression)) {
            return;
        }
        Variable v = null;
        if (expression instanceof VariableExpression) {
            VariableExpression ve = (VariableExpression)expression;
            v = ve.getAccessedVariable();
        } else {
            VariableExpression varExp;
            PropertyExpression propExp = (PropertyExpression)expression;
            Expression objectExpression = propExp.getObjectExpression();
            if (objectExpression instanceof VariableExpression && (varExp = (VariableExpression)objectExpression).isThisExpression()) {
                v = this.currentClass.getDeclaredField(propExp.getPropertyAsString());
            }
        }
        if (v instanceof FieldNode) {
            boolean error;
            FieldNode fn = (FieldNode)v;
            boolean isFinal = fn.isFinal();
            boolean isStatic = fn.isStatic();
            boolean bl = error = isFinal && (isStatic && !this.inStaticConstructor || !isStatic && !this.inConstructor);
            if (error) {
                this.addError("cannot modify" + (isStatic ? " static" : "") + " final field '" + fn.getName() + "' outside of " + (isStatic ? "static initialization block." : "constructor."), expression);
            }
        }
    }

    @Override
    public void visitConstructor(ConstructorNode node) {
        this.inConstructor = true;
        this.inStaticConstructor = node.isStaticConstructor();
        this.checkGenericsUsage((ASTNode)node, node.getParameters());
        super.visitConstructor(node);
    }

    @Override
    public void visitCatchStatement(CatchStatement cs) {
        if (!cs.getExceptionType().isDerivedFrom(ClassHelper.THROWABLE_TYPE)) {
            this.addError("Catch statement parameter type is not a subclass of Throwable.", cs);
        }
        super.visitCatchStatement(cs);
    }

    @Override
    public void visitMethodCallExpression(MethodCallExpression mce) {
        super.visitMethodCallExpression(mce);
        Expression aexp = mce.getArguments();
        if (aexp instanceof TupleExpression) {
            TupleExpression arguments = (TupleExpression)aexp;
            for (Expression e : arguments.getExpressions()) {
                this.checkForInvalidDeclaration(e);
            }
        } else {
            this.checkForInvalidDeclaration(aexp);
        }
    }

    @Override
    public void visitDeclarationExpression(DeclarationExpression expression) {
        super.visitDeclarationExpression(expression);
        if (expression.isMultipleAssignmentDeclaration()) {
            return;
        }
        this.checkInvalidDeclarationModifier(expression, 1024, "abstract");
        this.checkInvalidDeclarationModifier(expression, 256, "native");
        this.checkInvalidDeclarationModifier(expression, 2, "private");
        this.checkInvalidDeclarationModifier(expression, 4, "protected");
        this.checkInvalidDeclarationModifier(expression, 1, "public");
        this.checkInvalidDeclarationModifier(expression, 8, "static");
        this.checkInvalidDeclarationModifier(expression, 2048, "strictfp");
        this.checkInvalidDeclarationModifier(expression, 32, "synchronized");
        this.checkInvalidDeclarationModifier(expression, 128, "transient");
        this.checkInvalidDeclarationModifier(expression, 64, "volatile");
        if (ClassHelper.isPrimitiveVoid(expression.getVariableExpression().getOriginType())) {
            this.addError("The variable '" + expression.getVariableExpression().getName() + "' has invalid type void", expression);
        }
    }

    private void checkInvalidDeclarationModifier(DeclarationExpression expression, int modifier, String modName) {
        if ((expression.getVariableExpression().getModifiers() & modifier) != 0) {
            this.addError("Modifier '" + modName + "' not allowed here.", expression);
        }
    }

    private void checkForInvalidDeclaration(Expression exp) {
        if (!(exp instanceof DeclarationExpression)) {
            return;
        }
        this.addError("Invalid use of declaration inside method call.", exp);
    }

    @Override
    public void visitConstantExpression(ConstantExpression expression) {
        super.visitConstantExpression(expression);
        this.checkStringExceedingMaximumLength(expression);
    }

    @Override
    public void visitGStringExpression(GStringExpression expression) {
        super.visitGStringExpression(expression);
        for (ConstantExpression ce : expression.getStrings()) {
            this.checkStringExceedingMaximumLength(ce);
        }
    }

    private void checkStringExceedingMaximumLength(ConstantExpression expression) {
        String s;
        Object value = expression.getValue();
        if (value instanceof String && (s = (String)value).length() > 65535) {
            this.addError("String too long. The given string is " + s.length() + " Unicode code units long, but only a maximum of 65535 is allowed.", expression);
        }
    }

    private void checkGenericsUsage(ASTNode ref, ClassNode[] nodes) {
        for (ClassNode node : nodes) {
            this.checkGenericsUsage(ref, node);
        }
    }

    private void checkGenericsUsage(ASTNode ref, Parameter[] params) {
        for (Parameter p : params) {
            this.checkGenericsUsage(ref, p.getType());
        }
    }

    private void checkGenericsUsage(ASTNode ref, ClassNode node) {
        if (node.isArray()) {
            this.checkGenericsUsage(ref, node.getComponentType());
        } else if (!node.isRedirectNode() && node.isUsingGenerics()) {
            this.addError("A transform used a generics containing ClassNode " + node + " for " + ClassCompletionVerifier.getRefDescriptor(ref) + "directly. You are not supposed to do this. Please create a new ClassNode referring to the old ClassNode and use the new ClassNode instead of the old one. Otherwise the compiler will create wrong descriptors and a potential NullPointerException in TypeResolver in the OpenJDK. If this is not your own doing, please report this bug to the writer of the transform.", ref);
        }
    }

    private static String getRefDescriptor(ASTNode ref) {
        if (ref instanceof FieldNode) {
            FieldNode f = (FieldNode)ref;
            return "the field " + f.getName() + " ";
        }
        if (ref instanceof PropertyNode) {
            PropertyNode p = (PropertyNode)ref;
            return "the property " + p.getName() + " ";
        }
        if (ref instanceof ConstructorNode) {
            return "the constructor " + ref.getText() + " ";
        }
        if (ref instanceof MethodNode) {
            return "the method " + ref.getText() + " ";
        }
        if (ref instanceof ClassNode) {
            return "the super class " + ref + " ";
        }
        return "<unknown with class " + ref.getClass() + "> ";
    }
}

