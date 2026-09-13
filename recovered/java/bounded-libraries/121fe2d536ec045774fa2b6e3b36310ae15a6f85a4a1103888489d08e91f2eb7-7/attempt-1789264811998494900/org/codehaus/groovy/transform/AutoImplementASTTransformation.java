/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.transform.AutoImplement;
import groovy.transform.Undefined;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.apache.groovy.ast.tools.MethodNodeUtils;
import org.apache.groovy.util.BeanUtils;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.stmt.EmptyStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.tools.GenericsUtils;
import org.codehaus.groovy.ast.tools.ParameterUtils;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

@GroovyASTTransformation(phase=CompilePhase.CANONICALIZATION)
public class AutoImplementASTTransformation
extends AbstractASTTransformation {
    private static final Class<?> MY_CLASS = AutoImplement.class;
    private static final ClassNode MY_TYPE = ClassHelper.make(MY_CLASS);
    private static final String MY_TYPE_NAME = "@" + MY_TYPE.getNameWithoutPackage();

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        this.init(nodes, source);
        AnnotatedNode parent = (AnnotatedNode)nodes[1];
        AnnotationNode anno = (AnnotationNode)nodes[0];
        if (!MY_TYPE.equals(anno.getClassNode())) {
            return;
        }
        if (parent instanceof ClassNode) {
            Expression code;
            ClassNode cNode = (ClassNode)parent;
            if (!this.checkNotInterface(cNode, MY_TYPE_NAME)) {
                return;
            }
            String message = AutoImplementASTTransformation.getMemberStringValue(anno, "message");
            ClassNode exception = this.getMemberClassValue(anno, "exception");
            if (exception != null && Undefined.isUndefinedException(exception)) {
                exception = null;
            }
            if ((code = anno.getMember("code")) != null && !(code instanceof ClosureExpression)) {
                this.addError("Expected closure value for annotation parameter 'code'. Found " + code, cNode);
            } else {
                this.createMethods(cNode, exception, message, (ClosureExpression)code);
                if (code != null) {
                    anno.setMember("code", new ClosureExpression(Parameter.EMPTY_ARRAY, EmptyStatement.INSTANCE));
                }
            }
        }
    }

    private void createMethods(ClassNode cNode, ClassNode exception, String message, ClosureExpression code) {
        for (MethodNode candidate : AutoImplementASTTransformation.getAllCorrectedMethodsMap(cNode).values()) {
            if (!candidate.isAbstract()) continue;
            MethodNode mNode = ClassNodeUtils.addGeneratedMethod(cNode, candidate.getName(), candidate.getModifiers() & 7, candidate.getReturnType(), candidate.getParameters(), candidate.getExceptions(), AutoImplementASTTransformation.createMethodBody(cNode, candidate, exception, message, code));
            mNode.addAnnotation(ClassHelper.OVERRIDE_TYPE);
            mNode.setGenericsTypes(candidate.getGenericsTypes());
        }
    }

    private static Statement createMethodBody(ClassNode cNode, MethodNode mNode, ClassNode exception, String message, ClosureExpression code) {
        String propertyName;
        if (mNode.getParameters().length == 0 && (propertyName = MethodNodeUtils.getPropertyName(mNode)) != null) {
            String accessorName;
            String string = accessorName = mNode.getName().startsWith("is") ? GeneralUtils.getGetterName(propertyName) : "is" + BeanUtils.capitalize(propertyName);
            if (cNode.hasMethod(accessorName, Parameter.EMPTY_ARRAY)) {
                return GeneralUtils.returnS(GeneralUtils.callX(GeneralUtils.varX("this"), accessorName));
            }
        }
        if (code != null) {
            return code.getCode();
        }
        if (exception != null) {
            if (message == null) {
                return GeneralUtils.throwS(GeneralUtils.ctorX(exception));
            }
            return GeneralUtils.throwS(GeneralUtils.ctorX(exception, GeneralUtils.constX(message)));
        }
        return GeneralUtils.returnS(GeneralUtils.defaultValueX(mNode.getReturnType()));
    }

    private static Map<String, MethodNode> getAllCorrectedMethodsMap(ClassNode cNode) {
        HashMap<String, MethodNode> result = new HashMap<String, MethodNode>();
        for (MethodNode mn : cNode.getMethods()) {
            result.put(MethodNodeUtils.methodDescriptorWithoutReturnType(mn), mn);
        }
        ClassNode next = cNode;
        while (true) {
            Map<String, ClassNode> genericsSpec = GenericsUtils.createGenericsSpec(next);
            if (next != cNode) {
                for (MethodNode mn : next.getMethods()) {
                    String td;
                    ClassNode correctedClass;
                    MethodNode correctedMethod = GenericsUtils.correctToGenericsSpec(genericsSpec, mn);
                    MethodNode found = AutoImplementASTTransformation.getDeclaredMethodCorrected(genericsSpec, correctedMethod, correctedClass = GenericsUtils.correctToGenericsSpecRecurse(genericsSpec, next));
                    if (found == null || result.containsKey(td = MethodNodeUtils.methodDescriptorWithoutReturnType(found)) && AutoImplementASTTransformation.isWeakerCandidate((MethodNode)result.get(td), found)) continue;
                    result.put(td, found);
                }
            }
            ArrayList interfaces = new ArrayList();
            Collections.addAll(interfaces, next.getInterfaces());
            Map<String, ClassNode> updatedGenericsSpec = new HashMap<String, ClassNode>(genericsSpec);
            while (!interfaces.isEmpty()) {
                ClassNode origInterface = (ClassNode)interfaces.remove(0);
                if (ClassHelper.isObjectType(origInterface) || ClassHelper.isGroovyObjectType(origInterface)) continue;
                updatedGenericsSpec = GenericsUtils.createGenericsSpec(origInterface, updatedGenericsSpec);
                ClassNode correctedInterface = GenericsUtils.correctToGenericsSpecRecurse(updatedGenericsSpec, origInterface);
                for (MethodNode nextMethod : correctedInterface.getMethods()) {
                    String td;
                    MethodNode correctedMethod = GenericsUtils.correctToGenericsSpec(updatedGenericsSpec, nextMethod);
                    MethodNode found = AutoImplementASTTransformation.getDeclaredMethodCorrected(updatedGenericsSpec, correctedMethod, correctedInterface);
                    if (found == null || result.containsKey(td = MethodNodeUtils.methodDescriptorWithoutReturnType(found)) && AutoImplementASTTransformation.isWeakerCandidate((MethodNode)result.get(td), found)) continue;
                    result.put(td, found);
                }
                Collections.addAll(interfaces, correctedInterface.getInterfaces());
            }
            ClassNode superClass = next.getUnresolvedSuperClass();
            if (superClass == null) break;
            next = GenericsUtils.correctToGenericsSpecRecurse(updatedGenericsSpec, superClass);
        }
        for (ClassNode cn = cNode; cn != null && !ClassHelper.isObjectType(cn); cn = cn.getSuperClass()) {
            for (PropertyNode pn : cn.getProperties()) {
                if (!pn.getField().isFinal()) {
                    result.remove(pn.getSetterNameOrDefault() + ":" + pn.getType().getText() + ",");
                }
                if (!ClassHelper.isPrimitiveBoolean(pn.getType())) {
                    result.remove(pn.getGetterNameOrDefault() + ":");
                    continue;
                }
                if (pn.getGetterName() != null) {
                    result.remove(pn.getGetterName() + ":");
                    continue;
                }
                String isserName = "is" + BeanUtils.capitalize(pn.getName());
                String getterName = GeneralUtils.getGetterName(pn.getName());
                if (!cNode.hasMethod(isserName, Parameter.EMPTY_ARRAY)) {
                    result.remove(getterName + ":");
                }
                if (cNode.hasMethod(getterName, Parameter.EMPTY_ARRAY)) continue;
                result.remove(isserName + ":");
            }
        }
        return result;
    }

    private static boolean isWeakerCandidate(MethodNode existing, MethodNode found) {
        return (!existing.isAbstract() || found.isAbstract()) && ClassNodeUtils.isSubtype(found.getReturnType(), existing.getReturnType());
    }

    private static MethodNode getDeclaredMethodCorrected(Map<String, ClassNode> genericsSpec, MethodNode origMethod, ClassNode correctedClass) {
        for (MethodNode nameMatch : correctedClass.getDeclaredMethods(origMethod.getName())) {
            MethodNode correctedMethod = GenericsUtils.correctToGenericsSpec(genericsSpec, nameMatch);
            if (!ParameterUtils.parametersEqual(correctedMethod.getParameters(), origMethod.getParameters())) continue;
            return correctedMethod;
        }
        return null;
    }
}

