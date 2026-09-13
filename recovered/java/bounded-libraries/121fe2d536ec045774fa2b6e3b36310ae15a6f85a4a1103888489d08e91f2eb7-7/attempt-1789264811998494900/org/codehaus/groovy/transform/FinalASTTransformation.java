/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.transform.Final;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
public class FinalASTTransformation
extends AbstractASTTransformation {
    private static final ClassNode MY_TYPE = ClassHelper.make(Final.class);

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        this.init(nodes, source);
        AnnotatedNode candidate = (AnnotatedNode)nodes[1];
        AnnotationNode node = (AnnotationNode)nodes[0];
        if (!MY_TYPE.equals(node.getClassNode())) {
            return;
        }
        if (this.memberHasValue(node, "enabled", false)) {
            return;
        }
        if (candidate instanceof ClassNode) {
            ClassNode cNode = (ClassNode)candidate;
            FinalASTTransformation.checkModifiers(this, cNode.getModifiers(), cNode, "type " + cNode.getName());
            cNode.setModifiers(cNode.getModifiers() | 0x10);
        } else if (candidate instanceof MethodNode) {
            MethodNode mNode = (MethodNode)candidate;
            FinalASTTransformation.checkModifiers(this, mNode.getModifiers(), mNode, "method or constructor " + mNode.getName());
            mNode.setModifiers(mNode.getModifiers() | 0x10);
        } else if (candidate instanceof FieldNode) {
            FieldNode fNode = (FieldNode)candidate;
            FinalASTTransformation.checkModifiers(this, fNode.getModifiers(), fNode, "field " + fNode.getName());
            fNode.setModifiers(fNode.getModifiers() | 0x10);
        }
    }

    static void checkModifiers(AbstractASTTransformation xform, int modifiers, AnnotatedNode node, String place) {
        if ((modifiers & 0x10) == 0 && (modifiers & 0x1400) == 5120) {
            xform.addError("Error during " + MY_TYPE.getNameWithoutPackage() + " processing: annotation found on " + place + " with innapropriate modifiers", node);
        }
    }
}

