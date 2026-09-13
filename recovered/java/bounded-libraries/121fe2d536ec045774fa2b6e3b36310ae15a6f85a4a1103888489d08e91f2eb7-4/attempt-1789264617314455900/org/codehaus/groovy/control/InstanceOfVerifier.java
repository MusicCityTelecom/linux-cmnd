/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.control;

import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.Expression;

public abstract class InstanceOfVerifier
extends ClassCodeVisitorSupport {
    @Override
    public void visitBinaryExpression(BinaryExpression expression) {
        if (expression.getOperation().isA(1108) && expression.getRightExpression() instanceof ClassExpression) {
            ClassNode referenceType = expression.getRightExpression().getType();
            if (ClassHelper.isPrimitiveType(referenceType)) {
                this.addTypeError(expression.getRightExpression(), "primitive type " + referenceType.getName());
            } else {
                while (referenceType.isArray()) {
                    referenceType = referenceType.getComponentType();
                }
                if (referenceType.isGenericsPlaceHolder()) {
                    this.addTypeError(expression.getRightExpression(), "type parameter " + referenceType.getUnresolvedName() + ". Use its erasure " + referenceType.getNameWithoutPackage() + " instead since further generic type information will be erased at runtime");
                } else if (referenceType.getGenericsTypes() != null) {
                    // empty if block
                }
            }
        }
        super.visitBinaryExpression(expression);
    }

    private void addTypeError(Expression referenceExpr, String referenceType) {
        this.addError("Cannot perform instanceof check against " + referenceType, referenceExpr);
    }
}

