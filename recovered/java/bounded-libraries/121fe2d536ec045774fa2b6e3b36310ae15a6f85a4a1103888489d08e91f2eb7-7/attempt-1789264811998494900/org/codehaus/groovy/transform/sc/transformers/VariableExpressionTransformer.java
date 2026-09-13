/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.sc.transformers;

import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.transform.sc.StaticCompilationMetadataKeys;
import org.codehaus.groovy.transform.stc.StaticTypesMarker;

class VariableExpressionTransformer {
    VariableExpressionTransformer() {
    }

    Expression transformVariableExpression(VariableExpression ve) {
        Expression xe = VariableExpressionTransformer.tryTransformImplicitReceiver(ve);
        if (xe == null) {
            xe = VariableExpressionTransformer.tryTransformPrivateFieldAccess(ve);
        }
        if (xe == null) {
            xe = VariableExpressionTransformer.tryTransformDirectMethodTarget(ve);
        }
        if (xe != null) {
            return xe;
        }
        return ve;
    }

    private static Expression tryTransformImplicitReceiver(VariableExpression ve) {
        Object val = ve.getNodeMetaData((Object)StaticTypesMarker.IMPLICIT_RECEIVER);
        if (val == null || val.equals(ve.getName())) {
            return null;
        }
        VariableExpression receiver = new VariableExpression("owner".equals(val) ? (String)val : ("delegate".equals(val) ? (String)val : "this"));
        receiver.setColumnNumber(ve.getColumnNumber());
        receiver.setLineNumber(ve.getLineNumber());
        PropertyExpression pe = GeneralUtils.propX((Expression)receiver, ve.getName());
        pe.getProperty().setSourcePosition(ve);
        pe.setImplicitThis(true);
        pe.copyNodeMetaData(ve);
        ClassNode owner = (ClassNode)ve.getNodeMetaData((Object)StaticCompilationMetadataKeys.PROPERTY_OWNER);
        if (owner != null) {
            receiver.putNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE, owner);
            receiver.putNodeMetaData((Object)StaticTypesMarker.IMPLICIT_RECEIVER, val);
        }
        pe.removeNodeMetaData((Object)StaticTypesMarker.IMPLICIT_RECEIVER);
        return pe;
    }

    private static Expression tryTransformPrivateFieldAccess(VariableExpression ve) {
        FieldNode field = (FieldNode)ve.getNodeMetaData((Object)StaticTypesMarker.PV_FIELDS_ACCESS);
        if (field == null) {
            field = (FieldNode)ve.getNodeMetaData((Object)StaticTypesMarker.PV_FIELDS_MUTATION);
        }
        if (field == null) {
            return null;
        }
        PropertyExpression pe = !field.isStatic() ? GeneralUtils.thisPropX(true, ve.getName()) : GeneralUtils.propX((Expression)GeneralUtils.classX(field.getDeclaringClass()), ve.getName());
        pe.getObjectExpression().putNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE, field.getDeclaringClass());
        pe.putNodeMetaData((Object)StaticTypesMarker.DECLARATION_INFERRED_TYPE, field.getOriginType());
        pe.getProperty().setSourcePosition(ve);
        return pe;
    }

    private static Expression tryTransformDirectMethodTarget(VariableExpression ve) {
        MethodNode dmct = (MethodNode)ve.getNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET);
        if (dmct == null || dmct.getParameters().length != 0) {
            return null;
        }
        MethodCallExpression mce = GeneralUtils.callThisX(dmct.getName());
        mce.getMethod().setSourcePosition(ve);
        mce.setMethodTarget(dmct);
        mce.putNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE, ve.getNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE));
        return mce;
    }
}

