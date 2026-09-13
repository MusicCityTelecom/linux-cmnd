/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.sc.transformers;

import org.apache.groovy.ast.tools.ExpressionUtils;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.transform.sc.transformers.StaticCompilationTransformer;
import org.codehaus.groovy.transform.stc.StaticTypesMarker;

class PropertyExpressionTransformer {
    private final StaticCompilationTransformer scTransformer;

    PropertyExpressionTransformer(StaticCompilationTransformer scTransformer) {
        this.scTransformer = scTransformer;
    }

    Expression transformPropertyExpression(PropertyExpression pe) {
        MethodNode dmct;
        if (ExpressionUtils.isThisOrSuper(pe.getObjectExpression()) && (dmct = (MethodNode)pe.getNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET)) != null && dmct.getParameters().length == 0) {
            MethodCallExpression mce = GeneralUtils.callX(this.scTransformer.transform(pe.getObjectExpression()), dmct.getName());
            mce.setImplicitThis(pe.isImplicitThis());
            mce.setSpreadSafe(pe.isSpreadSafe());
            mce.setMethodTarget(dmct);
            mce.setSourcePosition(pe);
            mce.copyNodeMetaData(pe);
            mce.setSafe(pe.isSafe());
            return mce;
        }
        return this.scTransformer.superTransform(pe);
    }
}

