/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.transform.NullCheck;
import java.util.List;
import org.apache.groovy.ast.tools.AnnotatedNodeUtils;
import org.apache.groovy.ast.tools.ConstructorNodeUtils;
import org.apache.groovy.ast.tools.MethodNodeUtils;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ThrowStatement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.classgen.BytecodeSequence;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.codehaus.groovy.transform.stc.StaticTypesMarker;

@GroovyASTTransformation(phase=CompilePhase.INSTRUCTION_SELECTION)
public class NullCheckASTTransformation
extends AbstractASTTransformation {
    public static final ClassNode NULL_CHECK_TYPE = ClassHelper.make(NullCheck.class);
    private static final String NULL_CHECK_NAME = "@" + NULL_CHECK_TYPE.getNameWithoutPackage();
    private static final ClassNode EXCEPTION = ClassHelper.make(IllegalArgumentException.class);
    private static final ConstructorNode EXCEPTION_STRING_CTOR = EXCEPTION.getDeclaredConstructor(GeneralUtils.params(GeneralUtils.param(ClassHelper.STRING_TYPE, "s")));
    private static final String NULL_CHECK_IS_PROCESSED = "NullCheck.isProcessed";

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        this.init(nodes, source);
        AnnotatedNode parent = (AnnotatedNode)nodes[1];
        AnnotationNode anno = (AnnotationNode)nodes[0];
        if (!NULL_CHECK_TYPE.equals(anno.getClassNode())) {
            return;
        }
        boolean includeGenerated = this.isIncludeGenerated(anno);
        if (parent instanceof ClassNode) {
            ClassNode cNode = (ClassNode)parent;
            if (!this.checkNotInterface(cNode, NULL_CHECK_NAME)) {
                return;
            }
            for (ConstructorNode cn : cNode.getDeclaredConstructors()) {
                this.adjustMethod(cn, includeGenerated);
            }
            for (MethodNode mn : cNode.getAllDeclaredMethods()) {
                this.adjustMethod(mn, includeGenerated);
            }
        } else if (parent instanceof MethodNode) {
            this.adjustMethod((MethodNode)parent, false);
        }
    }

    private boolean isIncludeGenerated(AnnotationNode anno) {
        return this.memberHasValue(anno, "includeGenerated", true);
    }

    public static boolean hasIncludeGenerated(ClassNode cNode) {
        List<AnnotationNode> annotations = cNode.getAnnotations(NULL_CHECK_TYPE);
        if (annotations.isEmpty()) {
            return false;
        }
        return NullCheckASTTransformation.hasIncludeGenerated(annotations.get(0));
    }

    private static boolean hasIncludeGenerated(AnnotationNode node) {
        Expression member = node.getMember("includeGenerated");
        return member instanceof ConstantExpression && ((ConstantExpression)member).getValue().equals(true);
    }

    private void adjustMethod(MethodNode mn, boolean includeGenerated) {
        BlockStatement newCode = MethodNodeUtils.getCodeAsBlock(mn);
        if (mn.getParameters().length == 0) {
            return;
        }
        boolean generated = AnnotatedNodeUtils.isGenerated(mn);
        int startingIndex = 0;
        if (!includeGenerated && generated) {
            return;
        }
        if (NullCheckASTTransformation.isMarkedAsProcessed(mn)) {
            return;
        }
        if (mn instanceof ConstructorNode) {
            if (mn.getFirstStatement() instanceof BytecodeSequence) {
                return;
            }
            ConstructorCallExpression cce = ConstructorNodeUtils.getFirstIfSpecialConstructorCall(mn.getCode());
            if (cce != null) {
                if (generated) {
                    return;
                }
                startingIndex = 1;
            }
        }
        for (Parameter p : mn.getParameters()) {
            if (ClassHelper.isPrimitiveType(p.getType())) continue;
            newCode.getStatements().add(startingIndex, GeneralUtils.ifS((Expression)GeneralUtils.isNullX(GeneralUtils.varX(p)), NullCheckASTTransformation.makeThrowStmt(p.getName())));
        }
        mn.setCode(newCode);
    }

    public static ThrowStatement makeThrowStmt(String variableName) {
        ConstructorCallExpression newException = GeneralUtils.ctorX(EXCEPTION, GeneralUtils.constX(variableName + " cannot be null"));
        newException.putNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET, EXCEPTION_STRING_CTOR);
        return GeneralUtils.throwS(newException);
    }

    public static void markAsProcessed(MethodNode mn) {
        mn.setNodeMetaData(NULL_CHECK_IS_PROCESSED, Boolean.TRUE);
    }

    private static boolean isMarkedAsProcessed(MethodNode mn) {
        Boolean r = (Boolean)mn.getNodeMetaData(NULL_CHECK_IS_PROCESSED);
        return null != r && r != false;
    }
}

