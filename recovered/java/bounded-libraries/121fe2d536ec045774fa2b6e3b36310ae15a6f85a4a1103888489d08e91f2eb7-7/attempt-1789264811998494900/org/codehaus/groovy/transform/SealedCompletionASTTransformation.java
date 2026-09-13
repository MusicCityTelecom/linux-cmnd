/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.transform.Sealed;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

@GroovyASTTransformation(phase=CompilePhase.CANONICALIZATION)
public class SealedCompletionASTTransformation
extends AbstractASTTransformation {
    private static final Class<Sealed> SEALED_CLASS = Sealed.class;
    private static final ClassNode SEALED_TYPE = ClassHelper.make(SEALED_CLASS);

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        this.init(nodes, source);
        AnnotatedNode parent = (AnnotatedNode)nodes[1];
        AnnotationNode anno = (AnnotationNode)nodes[0];
        if (!SEALED_TYPE.equals(anno.getClassNode())) {
            return;
        }
        if (parent instanceof ClassNode) {
            this.addDetectedSealedClasses((ClassNode)parent);
        }
    }

    private void addDetectedSealedClasses(ClassNode node) {
        boolean sealed = Boolean.TRUE.equals(node.getNodeMetaData(SEALED_CLASS));
        List<ClassNode> permitted = node.getPermittedSubclasses();
        if (!sealed || !permitted.isEmpty() || node.getModule() == null) {
            return;
        }
        for (ClassNode classNode : node.getModule().getClasses()) {
            if (classNode.getSuperClass().equals(node)) {
                permitted.add(classNode);
            }
            for (ClassNode iface : classNode.getInterfaces()) {
                if (!iface.equals(node)) continue;
                permitted.add(classNode);
            }
        }
        ArrayList<Expression> names = new ArrayList<Expression>();
        for (ClassNode next : permitted) {
            names.add(GeneralUtils.classX(ClassHelper.make(next.getName())));
        }
        AnnotationNode annotationNode = node.getAnnotations(SEALED_TYPE).get(0);
        annotationNode.addMember("permittedSubclasses", new ListExpression(names));
    }
}

