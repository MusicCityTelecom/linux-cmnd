/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.ast.tools;

import groovy.transform.Generated;
import java.util.List;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;

public class AnnotatedNodeUtils {
    private static final ClassNode GENERATED_TYPE = ClassHelper.make(Generated.class);

    private AnnotatedNodeUtils() {
    }

    public static <T extends AnnotatedNode> T markAsGenerated(ClassNode containingClass, T nodeToMark) {
        return AnnotatedNodeUtils.markAsGenerated(containingClass, nodeToMark, false);
    }

    public static <T extends AnnotatedNode> T markAsGenerated(ClassNode containingClass, T nodeToMark, boolean skipChecks) {
        boolean shouldAnnotate;
        boolean bl = shouldAnnotate = skipChecks || containingClass.getModule() != null && containingClass.getModule().getContext() != null;
        if (shouldAnnotate && !AnnotatedNodeUtils.isGenerated(nodeToMark)) {
            nodeToMark.addAnnotation(new AnnotationNode(GENERATED_TYPE));
        }
        return nodeToMark;
    }

    public static boolean hasAnnotation(AnnotatedNode node, ClassNode annotation) {
        List<AnnotationNode> annots = node.getAnnotations(annotation);
        return annots != null && !annots.isEmpty();
    }

    public static boolean isGenerated(AnnotatedNode node) {
        List<AnnotationNode> annots = node.getAnnotations(GENERATED_TYPE);
        return annots != null && !annots.isEmpty();
    }
}

