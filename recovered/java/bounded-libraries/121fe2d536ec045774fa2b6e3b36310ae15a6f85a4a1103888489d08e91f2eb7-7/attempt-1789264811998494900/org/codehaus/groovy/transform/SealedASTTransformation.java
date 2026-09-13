/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.transform.Sealed;
import groovy.transform.SealedMode;
import groovy.transform.SealedOptions;
import java.util.List;
import org.apache.groovy.lang.annotation.Incubating;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
public class SealedASTTransformation
extends AbstractASTTransformation {
    private static final Class<?> SEALED_CLASS = Sealed.class;
    private static final String SEALED_NAME = "@" + SEALED_CLASS.getSimpleName();
    private static final ClassNode SEALED_TYPE = ClassHelper.make(SEALED_CLASS);
    private static final ClassNode SEALED_OPTIONS_TYPE = ClassHelper.make(SealedOptions.class);
    private static final String SEALED_ALWAYS_ANNOTATE_KEY = "groovy.transform.SealedOptions.alwaysAnnotate";
    @Deprecated
    public static final String SEALED_ALWAYS_ANNOTATE = "groovy.transform.SealedOptions.alwaysAnnotate";

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        this.init(nodes, source);
        AnnotatedNode parent = (AnnotatedNode)nodes[1];
        AnnotationNode anno = (AnnotationNode)nodes[0];
        if (!SEALED_TYPE.equals(anno.getClassNode())) {
            return;
        }
        if (parent instanceof ClassNode) {
            boolean isNative;
            List<AnnotationNode> annotations;
            ClassNode cNode = (ClassNode)parent;
            if (cNode.isEnum()) {
                this.addError(SEALED_NAME + " not allowed for enum", cNode);
                return;
            }
            if (cNode.isAnnotationDefinition()) {
                this.addError(SEALED_NAME + " not allowed for annotation definition", cNode);
                return;
            }
            cNode.putNodeMetaData(SEALED_CLASS, Boolean.TRUE);
            boolean isPostJDK17 = false;
            String message = "Expecting JDK17+ but unable to determine target bytecode";
            if (this.sourceUnit != null) {
                CompilerConfiguration config = this.sourceUnit.getConfiguration();
                String targetBytecode = config.getTargetBytecode();
                isPostJDK17 = CompilerConfiguration.isPostJDK17(targetBytecode);
                message = "Expecting JDK17+ but found " + targetBytecode;
            }
            AnnotationNode options = (annotations = cNode.getAnnotations(SEALED_OPTIONS_TYPE)).isEmpty() ? null : annotations.get(0);
            SealedMode mode = SealedASTTransformation.getMode(options, "mode");
            boolean doNotAnnotate = options != null && this.memberHasValue(options, "alwaysAnnotate", Boolean.FALSE);
            boolean bl = isNative = isPostJDK17 && mode != SealedMode.EMULATE;
            if (doNotAnnotate) {
                cNode.putNodeMetaData("groovy.transform.SealedOptions.alwaysAnnotate", Boolean.FALSE);
            }
            if (isNative) {
                cNode.putNodeMetaData(SealedMode.class, (Object)SealedMode.NATIVE);
            } else if (mode == SealedMode.NATIVE) {
                this.addError(message + " when attempting to create a native sealed class", cNode);
            }
            List<ClassNode> newSubclasses = this.getMemberClassList(anno, "permittedSubclasses");
            if (newSubclasses != null) {
                newSubclasses.forEach(subnode -> {
                    if (subnode.equals(cNode)) {
                        this.addError("Illegal self-reference: a sealed class cannot have itself as a permitted subclass", anno);
                    }
                });
                cNode.getPermittedSubclasses().addAll(newSubclasses);
            }
        }
    }

    @Incubating
    public static boolean sealedNative(AnnotatedNode node) {
        return node.getNodeMetaData(SealedMode.class) == SealedMode.NATIVE;
    }

    @Incubating
    public static boolean sealedSkipAnnotation(AnnotatedNode node) {
        return Boolean.FALSE.equals(node.getNodeMetaData("groovy.transform.SealedOptions.alwaysAnnotate"));
    }

    private static SealedMode getMode(AnnotationNode node, String name) {
        ClassExpression ce;
        PropertyExpression prop;
        Expression oe;
        Expression member;
        if (node != null && (member = node.getMember(name)) instanceof PropertyExpression && (oe = (prop = (PropertyExpression)member).getObjectExpression()) instanceof ClassExpression && (ce = (ClassExpression)oe).getType().getName().equals("groovy.transform.SealedMode")) {
            return SealedMode.valueOf(prop.getPropertyAsString());
        }
        return null;
    }
}

