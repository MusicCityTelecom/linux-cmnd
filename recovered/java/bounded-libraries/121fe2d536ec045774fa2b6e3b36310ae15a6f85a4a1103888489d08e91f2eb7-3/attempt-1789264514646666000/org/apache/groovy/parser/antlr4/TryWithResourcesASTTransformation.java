/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.parser.antlr4;

import java.util.Collections;
import java.util.List;
import org.apache.groovy.parser.antlr4.AstBuilder;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.BooleanExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.CatchStatement;
import org.codehaus.groovy.ast.stmt.EmptyStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.IfStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.ThrowStatement;
import org.codehaus.groovy.ast.stmt.TryCatchStatement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.syntax.Token;

public class TryWithResourcesASTTransformation {
    private AstBuilder astBuilder;
    private int primaryExcCnt = 0;
    private int tExcCnt = 0;
    private int suppressedExcCnt = 0;
    private int resourceCnt = 0;

    public TryWithResourcesASTTransformation(AstBuilder astBuilder) {
        this.astBuilder = astBuilder;
    }

    public Statement transform(TryCatchStatement tryCatchStatement) {
        if (!DefaultGroovyMethods.asBoolean(tryCatchStatement.getResourceStatements())) {
            return tryCatchStatement;
        }
        if (this.isBasicTryWithResourcesStatement(tryCatchStatement)) {
            return this.transformBasicTryWithResourcesStatement(tryCatchStatement);
        }
        return this.transformExtendedTryWithResourcesStatement(tryCatchStatement);
    }

    private boolean isBasicTryWithResourcesStatement(TryCatchStatement tryCatchStatement) {
        return tryCatchStatement.getFinallyStatement() instanceof EmptyStatement && !DefaultGroovyMethods.asBoolean(tryCatchStatement.getCatchStatements());
    }

    private ExpressionStatement makeVariableDeclarationFinal(ExpressionStatement variableDeclaration) {
        if (!DefaultGroovyMethods.asBoolean(variableDeclaration)) {
            return variableDeclaration;
        }
        if (!(variableDeclaration.getExpression() instanceof DeclarationExpression)) {
            throw new IllegalArgumentException("variableDeclaration is not a declaration statement");
        }
        DeclarationExpression declarationExpression = (DeclarationExpression)variableDeclaration.getExpression();
        if (!(declarationExpression.getLeftExpression() instanceof VariableExpression)) {
            throw this.astBuilder.createParsingFailedException("The expression statement is not a variable delcaration statement", variableDeclaration);
        }
        VariableExpression variableExpression = (VariableExpression)declarationExpression.getLeftExpression();
        variableExpression.setModifiers(variableExpression.getModifiers() | 0x10);
        return variableDeclaration;
    }

    private String genPrimaryExcName() {
        return "__$$primaryExc" + this.primaryExcCnt++;
    }

    private Statement transformExtendedTryWithResourcesStatement(TryCatchStatement tryCatchStatement) {
        TryCatchStatement newTryWithResourcesStatement = GeneralUtils.tryCatchS(tryCatchStatement.getTryStatement());
        tryCatchStatement.getResourceStatements().forEach(newTryWithResourcesStatement::addResource);
        TryCatchStatement newTryCatchStatement = GeneralUtils.tryCatchS(this.astBuilder.createBlockStatement(this.transform(newTryWithResourcesStatement)), tryCatchStatement.getFinallyStatement());
        tryCatchStatement.getCatchStatements().forEach(newTryCatchStatement::addCatch);
        return newTryCatchStatement;
    }

    private Statement transformBasicTryWithResourcesStatement(TryCatchStatement tryCatchStatement) {
        BlockStatement blockStatement = new BlockStatement();
        ExpressionStatement firstResourceStatement = this.makeVariableDeclarationFinal(tryCatchStatement.getResourceStatement(0));
        this.astBuilder.appendStatementsToBlockStatement(blockStatement, firstResourceStatement);
        String primaryExcName = this.genPrimaryExcName();
        VariableExpression primaryExcX = GeneralUtils.localVarX(primaryExcName, ClassHelper.THROWABLE_TYPE.getPlainNodeReference());
        ExpressionStatement primaryExcDeclarationStatement = new ExpressionStatement(new DeclarationExpression(primaryExcX, Token.newSymbol(100, -1, -1), (Expression)GeneralUtils.nullX()));
        this.astBuilder.appendStatementsToBlockStatement(blockStatement, primaryExcDeclarationStatement);
        String firstResourceIdentifierName = ((DeclarationExpression)tryCatchStatement.getResourceStatement(0).getExpression()).getLeftExpression().getText();
        TryCatchStatement newTryCatchStatement = GeneralUtils.tryCatchS(tryCatchStatement.getTryStatement(), this.createFinallyBlockForNewTryCatchStatement(primaryExcName, firstResourceIdentifierName));
        List<ExpressionStatement> resourceStatements = tryCatchStatement.getResourceStatements();
        List<ExpressionStatement> tailResourceStatements = resourceStatements.subList(1, resourceStatements.size());
        tailResourceStatements.stream().forEach(newTryCatchStatement::addResource);
        newTryCatchStatement.addCatch(this.createCatchBlockForOuterNewTryCatchStatement(primaryExcName));
        this.astBuilder.appendStatementsToBlockStatement(blockStatement, this.transform(newTryCatchStatement));
        return blockStatement;
    }

    private CatchStatement createCatchBlockForOuterNewTryCatchStatement(String primaryExcName) {
        BlockStatement blockStatement = new BlockStatement();
        String tExcName = this.genTExcName();
        ExpressionStatement primaryExcAssignStatement = new ExpressionStatement(new BinaryExpression(new VariableExpression(primaryExcName), Token.newSymbol(100, -1, -1), new VariableExpression(tExcName)));
        this.astBuilder.appendStatementsToBlockStatement(blockStatement, primaryExcAssignStatement);
        ThrowStatement throwTExcStatement = new ThrowStatement(new VariableExpression(tExcName));
        this.astBuilder.appendStatementsToBlockStatement(blockStatement, throwTExcStatement);
        Parameter tExcParameter = new Parameter(ClassHelper.THROWABLE_TYPE.getPlainNodeReference(), tExcName);
        return new CatchStatement(tExcParameter, blockStatement);
    }

    private String genTExcName() {
        return "__$$t" + this.tExcCnt++;
    }

    private BlockStatement createFinallyBlockForNewTryCatchStatement(String primaryExcName, String firstResourceIdentifierName) {
        BlockStatement finallyBlock = new BlockStatement();
        BooleanExpression conditionExpression = new BooleanExpression(new BinaryExpression(new VariableExpression(primaryExcName), Token.newSymbol(120, -1, -1), new ConstantExpression(null)));
        TryCatchStatement newTryCatchStatement = GeneralUtils.tryCatchS(this.astBuilder.createBlockStatement(this.createCloseResourceStatement(firstResourceIdentifierName)));
        String suppressedExcName = this.genSuppressedExcName();
        newTryCatchStatement.addCatch(new CatchStatement(new Parameter(ClassHelper.THROWABLE_TYPE.getPlainNodeReference(), suppressedExcName), this.astBuilder.createBlockStatement(this.createAddSuppressedStatement(primaryExcName, suppressedExcName))));
        IfStatement ifStatement = new IfStatement(conditionExpression, newTryCatchStatement, this.createCloseResourceStatement(firstResourceIdentifierName));
        this.astBuilder.appendStatementsToBlockStatement(finallyBlock, ifStatement);
        return this.astBuilder.createBlockStatement(finallyBlock);
    }

    private String genSuppressedExcName() {
        return "__$$suppressedExc" + this.suppressedExcCnt++;
    }

    private ExpressionStatement createCloseResourceStatement(String firstResourceIdentifierName) {
        MethodCallExpression closeMethodCallExpression = new MethodCallExpression((Expression)new VariableExpression(firstResourceIdentifierName), "close", (Expression)new ArgumentListExpression());
        closeMethodCallExpression.setImplicitThis(false);
        closeMethodCallExpression.setSafe(true);
        return new ExpressionStatement(closeMethodCallExpression);
    }

    private ExpressionStatement createAddSuppressedStatement(String primaryExcName, String suppressedExcName) {
        MethodCallExpression addSuppressedMethodCallExpression = new MethodCallExpression((Expression)new VariableExpression(primaryExcName), "addSuppressed", (Expression)new ArgumentListExpression(Collections.singletonList(new VariableExpression(suppressedExcName))));
        addSuppressedMethodCallExpression.setImplicitThis(false);
        addSuppressedMethodCallExpression.setSafe(true);
        return new ExpressionStatement(addSuppressedMethodCallExpression);
    }

    public BinaryExpression transformResourceAccess(Expression variableAccessExpression) {
        return new BinaryExpression(new VariableExpression(this.genResourceName()), Token.newSymbol(100, -1, -1), variableAccessExpression);
    }

    private String genResourceName() {
        return "__$$resource" + this.resourceCnt++;
    }
}

