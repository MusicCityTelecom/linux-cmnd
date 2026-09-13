/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.tailrec;

import groovy.lang.Closure;
import java.util.Map;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.transform.tailrec.AstHelper;
import org.codehaus.groovy.transform.tailrec.VariableExpressionReplacer;
import org.codehaus.groovy.transform.tailrec.VariableReplacedListener;

public class VariableAccessReplacer {
    private Map<String, Map> nameAndTypeMapping;
    private VariableReplacedListener listener = VariableReplacedListener.NULL;

    public VariableAccessReplacer(Map<String, Map> nameAndTypeMapping) {
        this.nameAndTypeMapping = nameAndTypeMapping;
    }

    public VariableAccessReplacer(Map<String, Map> nameAndTypeMapping, VariableReplacedListener listener) {
        this.nameAndTypeMapping = nameAndTypeMapping;
        this.listener = listener;
    }

    public void replaceIn(ASTNode root) {
        Closure<Boolean> whenParam = new Closure<Boolean>((Object)this, (Object)this){

            public Boolean doCall(VariableExpression expr) {
                return VariableAccessReplacer.this.nameAndTypeMapping.containsKey(expr.getName());
            }
        };
        Closure<VariableExpression> replaceWithLocalVariable = new Closure<VariableExpression>((Object)this, (Object)this){

            public VariableExpression doCall(VariableExpression expr) {
                VariableExpression newVar = AstHelper.createVariableReference((Map)VariableAccessReplacer.this.nameAndTypeMapping.get(expr.getName()));
                VariableAccessReplacer.this.getListener().variableReplaced(expr, newVar);
                return newVar;
            }
        };
        new VariableExpressionReplacer(whenParam, replaceWithLocalVariable).replaceIn(root);
    }

    public void setNameAndTypeMapping(Map<String, Map> nameAndTypeMapping) {
        this.nameAndTypeMapping = nameAndTypeMapping;
    }

    public VariableReplacedListener getListener() {
        return this.listener;
    }

    public void setListener(VariableReplacedListener listener) {
        this.listener = listener;
    }
}

