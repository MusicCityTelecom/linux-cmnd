/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.transform.InheritConstructors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.tools.GenericsUtils;
import org.codehaus.groovy.ast.tools.ParameterUtils;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

@GroovyASTTransformation(phase=CompilePhase.CANONICALIZATION)
public class InheritConstructorsASTTransformation
extends AbstractASTTransformation {
    private static final ClassNode INHERIT_CONSTRUCTORS_TYPE = ClassHelper.make(InheritConstructors.class);
    private static final String ANNOTATION = "@" + INHERIT_CONSTRUCTORS_TYPE.getNameWithoutPackage();

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        this.init(nodes, source);
        AnnotationNode anno = (AnnotationNode)nodes[0];
        AnnotatedNode target = (AnnotatedNode)nodes[1];
        if (INHERIT_CONSTRUCTORS_TYPE.equals(anno.getClassNode()) && target instanceof ClassNode) {
            this.processClass((ClassNode)target, anno);
        }
    }

    private void processClass(ClassNode cNode, AnnotationNode node) {
        if (cNode.isInterface()) {
            this.addError("Error processing interface '" + cNode.getName() + "'. " + ANNOTATION + " only allowed for classes.", cNode);
            return;
        }
        boolean copyConstructorAnnotations = this.memberHasValue(node, "constructorAnnotations", true);
        boolean copyParameterAnnotations = this.memberHasValue(node, "parameterAnnotations", true);
        ClassNode sNode = cNode.getSuperClass();
        List<AnnotationNode> superAnnotations = sNode.getAnnotations(INHERIT_CONSTRUCTORS_TYPE);
        if (superAnnotations.size() == 1) {
            this.processClass(sNode, node);
        }
        for (ConstructorNode cn : sNode.getDeclaredConstructors()) {
            this.addConstructorUnlessAlreadyExisting(cNode, cn, copyConstructorAnnotations, copyParameterAnnotations);
        }
    }

    private void addConstructorUnlessAlreadyExisting(ClassNode classNode, ConstructorNode ctorNode, boolean copyConstructorAnnotations, boolean copyParameterAnnotations) {
        if (ctorNode.isPrivate()) {
            return;
        }
        Parameter[] oldParams = ctorNode.getParameters();
        Parameter[] newParams = new Parameter[oldParams.length];
        Map<String, ClassNode> genericsSpec = GenericsUtils.createGenericsSpec(classNode);
        genericsSpec = GenericsUtils.createGenericsSpec(classNode.getUnresolvedSuperClass());
        List<Expression> theArgs = this.buildParams(oldParams, newParams, genericsSpec, copyParameterAnnotations);
        if (!InheritConstructorsASTTransformation.isExisting(classNode, newParams)) {
            ConstructorNode added = ClassNodeUtils.addGeneratedConstructor(classNode, ctorNode.getModifiers(), newParams, ctorNode.getExceptions(), GeneralUtils.block(GeneralUtils.ctorSuperS(GeneralUtils.args(theArgs))));
            if (copyConstructorAnnotations) {
                added.addAnnotations(this.copyAnnotatedNodeAnnotations(ctorNode, ANNOTATION, false));
            }
        }
    }

    private List<Expression> buildParams(Parameter[] origParams, Parameter[] params, Map<String, ClassNode> genericsSpec, boolean copyParameterAnnotations) {
        ArrayList<Expression> theArgs = new ArrayList<Expression>();
        int n = origParams.length;
        for (int i = 0; i < n; ++i) {
            Parameter p = origParams[i];
            ClassNode newType = GenericsUtils.correctToGenericsSpecRecurse(genericsSpec, p.getType());
            Parameter parameter = params[i] = p.hasInitialExpression() ? GeneralUtils.param(newType, p.getName(), p.getInitialExpression()) : GeneralUtils.param(newType, p.getName());
            if (copyParameterAnnotations) {
                params[i].addAnnotations(this.copyAnnotatedNodeAnnotations(origParams[i], ANNOTATION));
            }
            theArgs.add(GeneralUtils.castX(p.getType(), GeneralUtils.varX(p.getName(), newType)));
        }
        return theArgs;
    }

    private static boolean isExisting(ClassNode classNode, Parameter[] params) {
        return classNode.getDeclaredConstructors().stream().anyMatch(ctor -> ParameterUtils.parametersEqual(params, ctor.getParameters()));
    }
}

