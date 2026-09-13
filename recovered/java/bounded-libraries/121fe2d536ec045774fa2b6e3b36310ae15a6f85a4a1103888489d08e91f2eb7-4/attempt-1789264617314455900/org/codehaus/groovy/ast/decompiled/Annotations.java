/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.decompiled;

import groovyjarjarasm.asm.Type;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.decompiled.AnnotatedStub;
import org.codehaus.groovy.ast.decompiled.AnnotatedTypeStub;
import org.codehaus.groovy.ast.decompiled.AnnotationStub;
import org.codehaus.groovy.ast.decompiled.AsmReferenceResolver;
import org.codehaus.groovy.ast.decompiled.EnumConstantWrapper;
import org.codehaus.groovy.ast.decompiled.TypeAnnotationStub;
import org.codehaus.groovy.ast.decompiled.TypeWrapper;
import org.codehaus.groovy.ast.expr.AnnotationConstantExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.vmplugin.VMPluginFactory;

class Annotations {
    Annotations() {
    }

    static AnnotationNode createAnnotationNode(AnnotationStub annotation, AsmReferenceResolver resolver) {
        ClassNode classNode = resolver.resolveClassNullable(Type.getType(annotation.className).getClassName());
        if (classNode == null) {
            return null;
        }
        DecompiledAnnotationNode node = new DecompiledAnnotationNode(classNode);
        for (Map.Entry<String, Object> entry : annotation.members.entrySet()) {
            Annotations.addMemberIfFound(resolver, node, entry);
        }
        return node;
    }

    private static void addMemberIfFound(AsmReferenceResolver resolver, AnnotationNode node, Map.Entry<String, Object> entry) {
        Expression value = Annotations.annotationValueToExpression(entry.getValue(), resolver);
        if (value != null) {
            node.addMember(entry.getKey(), value);
        }
    }

    private static Expression annotationValueToExpression(Object value, AsmReferenceResolver resolver) {
        if (value instanceof TypeWrapper) {
            ClassNode type = resolver.resolveClassNullable(Type.getType(((TypeWrapper)value).desc).getClassName());
            return type != null ? new ClassExpression(type) : null;
        }
        if (value instanceof EnumConstantWrapper) {
            EnumConstantWrapper wrapper = (EnumConstantWrapper)value;
            return new PropertyExpression((Expression)new ClassExpression(resolver.resolveType(Type.getType(wrapper.enumDesc))), wrapper.constant);
        }
        if (value instanceof AnnotationStub) {
            AnnotationNode annotationNode = Annotations.createAnnotationNode((AnnotationStub)value, resolver);
            return annotationNode != null ? new AnnotationConstantExpression(annotationNode) : GeneralUtils.nullX();
        }
        if (value != null && value.getClass().isArray()) {
            ListExpression elementExprs = new ListExpression();
            int len = Array.getLength(value);
            for (int i = 0; i != len; ++i) {
                elementExprs.addExpression(Annotations.annotationValueToExpression(Array.get(value, i), resolver));
            }
            return elementExprs;
        }
        if (value instanceof List) {
            ListExpression elementExprs = new ListExpression();
            for (Object o : (List)value) {
                elementExprs.addExpression(Annotations.annotationValueToExpression(o, resolver));
            }
            return elementExprs;
        }
        return new ConstantExpression(value);
    }

    static <T extends AnnotatedNode> T addAnnotations(AnnotatedStub stub, T node, AsmReferenceResolver resolver) {
        List<AnnotationStub> annotations = stub.getAnnotations();
        if (annotations != null) {
            for (AnnotationStub annotation : annotations) {
                AnnotationNode annotationNode = Annotations.createAnnotationNode(annotation, resolver);
                if (annotationNode == null) continue;
                node.addAnnotation(annotationNode);
            }
        }
        return node;
    }

    static <T extends ClassNode> T addTypeAnnotations(AnnotatedTypeStub stub, T node, AsmReferenceResolver resolver) {
        List<TypeAnnotationStub> annotations = stub.getTypeAnnotations();
        if (annotations != null) {
            for (TypeAnnotationStub annotation : annotations) {
                AnnotationNode annotationNode = Annotations.createAnnotationNode(annotation, resolver);
                if (annotationNode == null) continue;
                node.addTypeAnnotation(annotationNode);
            }
        }
        return node;
    }

    private static class DecompiledAnnotationNode
    extends AnnotationNode {
        private final Object initLock = new Object();
        private volatile boolean lazyInitDone;

        public DecompiledAnnotationNode(ClassNode type) {
            super(type);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void lazyInit() {
            if (this.lazyInitDone) {
                return;
            }
            Object object = this.initLock;
            synchronized (object) {
                if (!this.lazyInitDone) {
                    for (AnnotationNode annotation : this.getClassNode().getAnnotations()) {
                        VMPluginFactory.getPlugin().configureAnnotationNodeFromDefinition(annotation, this);
                    }
                    this.lazyInitDone = true;
                }
            }
        }

        @Override
        public boolean isTargetAllowed(int target) {
            this.lazyInit();
            return super.isTargetAllowed(target);
        }

        @Override
        public boolean hasRuntimeRetention() {
            this.lazyInit();
            return super.hasRuntimeRetention();
        }

        @Override
        public boolean hasSourceRetention() {
            this.lazyInit();
            return super.hasSourceRetention();
        }

        @Override
        public boolean hasClassRetention() {
            this.lazyInit();
            return super.hasClassRetention();
        }
    }
}

