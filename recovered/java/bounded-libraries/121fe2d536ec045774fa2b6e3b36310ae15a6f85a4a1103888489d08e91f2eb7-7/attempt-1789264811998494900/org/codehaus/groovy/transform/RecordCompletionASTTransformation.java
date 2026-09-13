/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.transform.RecordBase;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.codehaus.groovy.transform.stc.StaticTypesMarker;

@GroovyASTTransformation(phase=CompilePhase.INSTRUCTION_SELECTION)
public class RecordCompletionASTTransformation
extends AbstractASTTransformation {
    private static final Class<? extends Annotation> MY_CLASS = RecordBase.class;
    public static final ClassNode MY_TYPE = ClassHelper.makeWithoutCaching(MY_CLASS, false);
    private static final String MY_TYPE_NAME = MY_TYPE.getNameWithoutPackage();

    @Override
    public String getAnnotationName() {
        return MY_TYPE_NAME;
    }

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        this.init(nodes, source);
        AnnotatedNode parent = (AnnotatedNode)nodes[1];
        AnnotationNode anno = (AnnotationNode)nodes[0];
        if (!MY_TYPE.equals(anno.getClassNode())) {
            return;
        }
        if (parent instanceof ClassNode) {
            ClassNode cNode = (ClassNode)parent;
            MethodNode copyWith = cNode.getMethod("copyWith", GeneralUtils.params(GeneralUtils.param(ClassHelper.MAP_TYPE, "namedArgs")));
            if (copyWith != null) {
                this.adjustCopyWith(cNode, copyWith);
            }
        }
    }

    private void adjustCopyWith(ClassNode cNode, MethodNode copyWith) {
        ReturnStatement rs;
        Expression expr;
        Statement code;
        ArrayList<Parameter> params = new ArrayList<Parameter>();
        List<PropertyNode> pList = GeneralUtils.getInstanceProperties(cNode);
        for (int i = 0; i < pList.size(); ++i) {
            PropertyNode pNode = pList.get(i);
            params.add(GeneralUtils.param(pNode.getType(), "arg" + i));
        }
        ConstructorNode consNode = cNode.getDeclaredConstructor(params.toArray(Parameter.EMPTY_ARRAY));
        if (consNode != null && (code = copyWith.getCode()) instanceof ReturnStatement && (expr = (rs = (ReturnStatement)code).getExpression()) instanceof ConstructorCallExpression) {
            expr.putNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET, consNode);
        }
    }
}

