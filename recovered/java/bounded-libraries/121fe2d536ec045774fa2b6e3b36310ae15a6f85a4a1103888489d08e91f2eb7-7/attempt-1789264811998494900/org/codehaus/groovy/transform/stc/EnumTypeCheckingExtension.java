/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.stc;

import java.lang.reflect.Modifier;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.SwitchStatement;
import org.codehaus.groovy.transform.stc.StaticTypeCheckingVisitor;
import org.codehaus.groovy.transform.stc.StaticTypesMarker;
import org.codehaus.groovy.transform.stc.TypeCheckingExtension;

public class EnumTypeCheckingExtension
extends TypeCheckingExtension {
    public EnumTypeCheckingExtension(StaticTypeCheckingVisitor staticTypeCheckingVisitor) {
        super(staticTypeCheckingVisitor);
    }

    @Override
    public boolean handleUnresolvedVariableExpression(VariableExpression vexp) {
        int modifiers;
        FieldNode fieldNode;
        SwitchStatement switchStatement = this.typeCheckingVisitor.typeCheckingContext.getEnclosingSwitchStatement();
        if (null == switchStatement) {
            return false;
        }
        ClassNode type = (ClassNode)switchStatement.getExpression().getNodeMetaData((Object)StaticTypesMarker.TYPE);
        if (null == type) {
            return false;
        }
        if (type.isEnum() && null != (fieldNode = type.redirect().getField(vexp.getName())) && Modifier.isPublic(modifiers = fieldNode.getModifiers()) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers) && type.equals(fieldNode.getType())) {
            vexp.putNodeMetaData((Object)StaticTypesMarker.SWITCH_CONDITION_EXPRESSION_TYPE, type);
            return true;
        }
        return false;
    }
}

