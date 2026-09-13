/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.tailrec;

import java.util.LinkedHashSet;
import java.util.Set;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.transform.tailrec.VariableReplacedListener;

public class UsedVariableTracker
implements VariableReplacedListener {
    private final Set<String> usedVariableNames = new LinkedHashSet<String>();

    @Override
    public void variableReplaced(VariableExpression oldVar, VariableExpression newVar) {
        this.usedVariableNames.add(newVar.getName());
    }

    public Set<String> getUsedVariableNames() {
        return this.usedVariableNames;
    }
}

