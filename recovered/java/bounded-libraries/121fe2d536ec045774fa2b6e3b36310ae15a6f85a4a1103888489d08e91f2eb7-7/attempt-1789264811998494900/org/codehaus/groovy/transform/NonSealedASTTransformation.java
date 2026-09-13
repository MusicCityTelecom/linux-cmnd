/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.transform.NonSealed;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
public class NonSealedASTTransformation
extends AbstractASTTransformation {
    private static final Class<?> NON_SEALED_CLASS = NonSealed.class;
    private static final ClassNode NON_SEALED_TYPE = ClassHelper.make(NON_SEALED_CLASS);

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        this.init(nodes, source);
        AnnotatedNode parent = (AnnotatedNode)nodes[1];
        AnnotationNode anno = (AnnotationNode)nodes[0];
        if (!NON_SEALED_TYPE.equals(anno.getClassNode())) {
            return;
        }
        if (parent instanceof ClassNode) {
            ClassNode cNode = (ClassNode)parent;
            if (cNode.isEnum()) {
                this.addError("@" + NON_SEALED_CLASS.getSimpleName() + " not allowed for enum", cNode);
                return;
            }
            if (cNode.isAnnotationDefinition()) {
                this.addError("@" + NON_SEALED_CLASS.getSimpleName() + " not allowed for annotation definition", cNode);
                return;
            }
            cNode.putNodeMetaData(NON_SEALED_CLASS, Boolean.TRUE);
        }
    }
}

