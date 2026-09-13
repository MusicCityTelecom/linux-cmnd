/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.ast.tools;

import groovy.transform.VisibilityOptions;
import groovy.transform.options.Visibility;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.transform.AbstractASTTransformation;

public class VisibilityUtils {
    private static final ClassNode VISIBILITY_OPTIONS_TYPE = ClassHelper.makeWithoutCaching(VisibilityOptions.class, false);

    private VisibilityUtils() {
    }

    public static int getVisibility(AnnotationNode anno, AnnotatedNode node, Class<? extends AnnotatedNode> clazz, int originalModifiers) {
        List<AnnotationNode> annotations = node.getAnnotations(VISIBILITY_OPTIONS_TYPE);
        if (annotations.isEmpty() || anno == null) {
            return originalModifiers;
        }
        String visId = AbstractASTTransformation.getMemberStringValue(anno, "visibilityId", null);
        Visibility vis = null;
        if (visId == null) {
            vis = VisibilityUtils.getVisForAnnotation(clazz, annotations.get(0), null);
        } else {
            AnnotationNode visAnno;
            Iterator<AnnotationNode> iterator = annotations.iterator();
            while (iterator.hasNext() && (vis = VisibilityUtils.getVisForAnnotation(clazz, visAnno = iterator.next(), visId)) == Visibility.UNDEFINED) {
            }
        }
        if (vis == null || vis == Visibility.UNDEFINED) {
            return originalModifiers;
        }
        int result = originalModifiers & 0xFFFFFFF8;
        return result | vis.getModifier();
    }

    private static Visibility getVisForAnnotation(Class<? extends AnnotatedNode> clazz, AnnotationNode visAnno, String visId) {
        Map<String, Expression> visMembers = visAnno.getMembers();
        if (visMembers == null) {
            return Visibility.UNDEFINED;
        }
        String id = AbstractASTTransformation.getMemberStringValue(visAnno, "id", null);
        if (id == null && visId != null || id != null && !id.equals(visId)) {
            return Visibility.UNDEFINED;
        }
        Visibility vis = null;
        if (clazz.equals(ConstructorNode.class)) {
            vis = VisibilityUtils.getVisibility(visMembers.get("constructor"));
        } else if (clazz.equals(MethodNode.class)) {
            vis = VisibilityUtils.getVisibility(visMembers.get("method"));
        } else if (clazz.equals(ClassNode.class)) {
            vis = VisibilityUtils.getVisibility(visMembers.get("type"));
        }
        if (vis == null || vis == Visibility.UNDEFINED) {
            vis = VisibilityUtils.getVisibility(visMembers.get("value"));
        }
        return vis;
    }

    private static Visibility getVisibility(Expression e) {
        PropertyExpression pe;
        if (e instanceof PropertyExpression && (pe = (PropertyExpression)e).getObjectExpression() instanceof ClassExpression && pe.getObjectExpression().getText().equals("groovy.transform.options.Visibility")) {
            return Visibility.valueOf(pe.getPropertyAsString());
        }
        return Visibility.UNDEFINED;
    }
}

