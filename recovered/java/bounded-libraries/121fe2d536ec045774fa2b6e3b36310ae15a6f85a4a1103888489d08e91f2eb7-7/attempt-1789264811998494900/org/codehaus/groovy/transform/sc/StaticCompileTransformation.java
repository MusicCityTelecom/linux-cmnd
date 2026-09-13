/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.sc;

import java.util.Collections;
import java.util.Map;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.classgen.asm.WriterControllerFactory;
import org.codehaus.groovy.classgen.asm.sc.StaticTypesWriterControllerFactoryImpl;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.syntax.SyntaxException;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.codehaus.groovy.transform.StaticTypesTransformation;
import org.codehaus.groovy.transform.sc.StaticCompilationMetadataKeys;
import org.codehaus.groovy.transform.sc.StaticCompilationVisitor;
import org.codehaus.groovy.transform.sc.transformers.StaticCompilationTransformer;
import org.codehaus.groovy.transform.stc.StaticTypeCheckingVisitor;

@GroovyASTTransformation(phase=CompilePhase.INSTRUCTION_SELECTION)
public class StaticCompileTransformation
extends StaticTypesTransformation {
    private final StaticTypesWriterControllerFactoryImpl factory = new StaticTypesWriterControllerFactoryImpl();

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        AnnotatedNode target = (AnnotatedNode)nodes[1];
        if (Boolean.TRUE.equals(target.getNodeMetaData(StaticTypeCheckingVisitor.class))) {
            return;
        }
        Map<String, Expression> members = ((AnnotationNode)nodes[0]).getMembers();
        Expression extensions = members.get("extensions");
        StaticTypeCheckingVisitor visitor = null;
        if (target instanceof ClassNode) {
            ClassNode classNode = (ClassNode)target;
            visitor = this.newVisitor(source, classNode);
            visitor.setCompilationUnit(this.compilationUnit);
            this.addTypeCheckingExtensions(visitor, extensions);
            classNode.putNodeMetaData(WriterControllerFactory.class, this.factory);
            target.putNodeMetaData((Object)StaticCompilationMetadataKeys.STATIC_COMPILE_NODE, !visitor.isSkipMode(target));
            visitor.initialize();
            visitor.visitClass(classNode);
        } else if (target instanceof MethodNode) {
            MethodNode methodNode = (MethodNode)target;
            ClassNode declaringClass = methodNode.getDeclaringClass();
            visitor = this.newVisitor(source, declaringClass);
            visitor.setCompilationUnit(this.compilationUnit);
            this.addTypeCheckingExtensions(visitor, extensions);
            methodNode.putNodeMetaData((Object)StaticCompilationMetadataKeys.STATIC_COMPILE_NODE, !visitor.isSkipMode(target));
            if (declaringClass.getNodeMetaData(WriterControllerFactory.class) == null) {
                declaringClass.putNodeMetaData(WriterControllerFactory.class, this.factory);
            }
            visitor.setMethodsToBeVisited(Collections.singleton(methodNode));
            visitor.initialize();
            visitor.visitMethod(methodNode);
        } else if (!(target instanceof PropertyNode)) {
            source.addError(new SyntaxException("[Static type checking] - Unimplemented node type", target));
        }
        if (visitor != null) {
            visitor.performSecondPass();
        }
        StaticCompilationTransformer transformer = new StaticCompilationTransformer(source, visitor);
        if (target instanceof ClassNode) {
            transformer.visitClass((ClassNode)target);
        } else if (target instanceof MethodNode) {
            transformer.visitMethod((MethodNode)target);
        }
    }

    @Override
    protected StaticTypeCheckingVisitor newVisitor(SourceUnit unit, ClassNode node) {
        return new StaticCompilationVisitor(unit, node);
    }
}

