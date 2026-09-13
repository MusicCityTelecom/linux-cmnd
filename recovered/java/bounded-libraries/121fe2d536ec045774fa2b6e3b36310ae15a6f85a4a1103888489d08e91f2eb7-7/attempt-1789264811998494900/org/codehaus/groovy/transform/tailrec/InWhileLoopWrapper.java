/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.tailrec;

import java.util.List;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.VariableScope;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ContinueStatement;
import org.codehaus.groovy.ast.stmt.EmptyStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.TryCatchStatement;
import org.codehaus.groovy.ast.stmt.WhileStatement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.transform.tailrec.GotoRecurHereException;

public class InWhileLoopWrapper {
    public static final String LOOP_LABEL = "_RECUR_HERE_";
    public static final GotoRecurHereException LOOP_EXCEPTION = new GotoRecurHereException();

    public void wrap(MethodNode method) {
        BlockStatement oldBody = (BlockStatement)method.getCode();
        TryCatchStatement tryCatchStatement = GeneralUtils.tryCatchS(oldBody, EmptyStatement.INSTANCE, GeneralUtils.catchS(GeneralUtils.param(ClassHelper.make(GotoRecurHereException.class), "ignore"), new ContinueStatement(LOOP_LABEL)));
        WhileStatement whileLoop = new WhileStatement(GeneralUtils.boolX(GeneralUtils.constX(true)), GeneralUtils.block(new VariableScope(method.getVariableScope()), tryCatchStatement));
        List<Statement> whileLoopStatements = ((BlockStatement)whileLoop.getLoopBlock()).getStatements();
        if (whileLoopStatements.size() > 0) {
            whileLoopStatements.get(0).setStatementLabel(LOOP_LABEL);
        }
        BlockStatement newBody = GeneralUtils.block(new VariableScope(method.getVariableScope()), new Statement[0]);
        newBody.addStatement(whileLoop);
        method.setCode(newBody);
    }
}

