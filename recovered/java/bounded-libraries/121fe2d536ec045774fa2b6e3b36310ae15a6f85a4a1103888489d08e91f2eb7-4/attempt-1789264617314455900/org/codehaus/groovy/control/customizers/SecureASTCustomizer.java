/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.control.customizers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.ImportNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ArrayExpression;
import org.codehaus.groovy.ast.expr.AttributeExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.BitwiseNegationExpression;
import org.codehaus.groovy.ast.expr.BooleanExpression;
import org.codehaus.groovy.ast.expr.CastExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.ClosureListExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.ElvisOperatorExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.FieldExpression;
import org.codehaus.groovy.ast.expr.GStringExpression;
import org.codehaus.groovy.ast.expr.LambdaExpression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.MapEntryExpression;
import org.codehaus.groovy.ast.expr.MapExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.MethodPointerExpression;
import org.codehaus.groovy.ast.expr.MethodReferenceExpression;
import org.codehaus.groovy.ast.expr.NotExpression;
import org.codehaus.groovy.ast.expr.PostfixExpression;
import org.codehaus.groovy.ast.expr.PrefixExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.RangeExpression;
import org.codehaus.groovy.ast.expr.SpreadExpression;
import org.codehaus.groovy.ast.expr.SpreadMapExpression;
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
import org.codehaus.groovy.ast.expr.TernaryExpression;
import org.codehaus.groovy.ast.expr.TupleExpression;
import org.codehaus.groovy.ast.expr.UnaryMinusExpression;
import org.codehaus.groovy.ast.expr.UnaryPlusExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.AssertStatement;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.BreakStatement;
import org.codehaus.groovy.ast.stmt.CaseStatement;
import org.codehaus.groovy.ast.stmt.CatchStatement;
import org.codehaus.groovy.ast.stmt.ContinueStatement;
import org.codehaus.groovy.ast.stmt.DoWhileStatement;
import org.codehaus.groovy.ast.stmt.EmptyStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.ForStatement;
import org.codehaus.groovy.ast.stmt.IfStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.SwitchStatement;
import org.codehaus.groovy.ast.stmt.SynchronizedStatement;
import org.codehaus.groovy.ast.stmt.ThrowStatement;
import org.codehaus.groovy.ast.stmt.TryCatchStatement;
import org.codehaus.groovy.ast.stmt.WhileStatement;
import org.codehaus.groovy.classgen.BytecodeExpression;
import org.codehaus.groovy.classgen.GeneratorContext;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.control.customizers.CompilationCustomizer;
import org.codehaus.groovy.syntax.Token;

public class SecureASTCustomizer
extends CompilationCustomizer {
    private boolean isPackageAllowed = true;
    private boolean isClosuresAllowed = true;
    private boolean isMethodDefinitionAllowed = true;
    private List<String> allowedImports;
    private List<String> disallowedImports;
    private List<String> allowedStaticImports;
    private List<String> disallowedStaticImports;
    private List<String> allowedStarImports;
    private List<String> disallowedStarImports;
    private List<String> allowedStaticStarImports;
    private List<String> disallowedStaticStarImports;
    private boolean isIndirectImportCheckEnabled;
    private List<Class<? extends Statement>> allowedStatements;
    private List<Class<? extends Statement>> disallowedStatements;
    private final List<StatementChecker> statementCheckers = new LinkedList<StatementChecker>();
    private List<Class<? extends Expression>> allowedExpressions;
    private List<Class<? extends Expression>> disallowedExpressions;
    private final List<ExpressionChecker> expressionCheckers = new LinkedList<ExpressionChecker>();
    private List<Integer> allowedTokens;
    private List<Integer> disallowedTokens;
    private List<String> allowedConstantTypes;
    private List<String> disallowedConstantTypes;
    private List<String> allowedReceivers;
    private List<String> disallowedReceivers;

    public SecureASTCustomizer() {
        super(CompilePhase.CANONICALIZATION);
    }

    public boolean isMethodDefinitionAllowed() {
        return this.isMethodDefinitionAllowed;
    }

    public void setMethodDefinitionAllowed(boolean methodDefinitionAllowed) {
        this.isMethodDefinitionAllowed = methodDefinitionAllowed;
    }

    public boolean isPackageAllowed() {
        return this.isPackageAllowed;
    }

    public boolean isClosuresAllowed() {
        return this.isClosuresAllowed;
    }

    public void setClosuresAllowed(boolean closuresAllowed) {
        this.isClosuresAllowed = closuresAllowed;
    }

    public void setPackageAllowed(boolean packageAllowed) {
        this.isPackageAllowed = packageAllowed;
    }

    public List<String> getDisallowedImports() {
        return this.disallowedImports;
    }

    @Deprecated
    public List<String> getImportsBlacklist() {
        return this.getDisallowedImports();
    }

    public void setDisallowedImports(List<String> disallowedImports) {
        if (this.allowedImports != null || this.allowedStarImports != null) {
            throw new IllegalArgumentException("You are not allowed to set both an allowed list and a disallowed list");
        }
        this.disallowedImports = disallowedImports;
    }

    @Deprecated
    public void setImportsBlacklist(List<String> disallowedImports) {
        this.setDisallowedImports(disallowedImports);
    }

    public List<String> getAllowedImports() {
        return this.allowedImports;
    }

    @Deprecated
    public List<String> getImportsWhitelist() {
        return this.getAllowedImports();
    }

    public void setAllowedImports(List<String> allowedImports) {
        if (this.disallowedImports != null || this.disallowedStarImports != null) {
            throw new IllegalArgumentException("You are not allowed to set both an allowed list and a disallowed list");
        }
        this.allowedImports = allowedImports;
    }

    @Deprecated
    public void setImportsWhitelist(List<String> allowedImports) {
        this.setAllowedImports(allowedImports);
    }

    public List<String> getDisallowedStarImports() {
        return this.disallowedStarImports;
    }

    @Deprecated
    public List<String> getStarImportsBlacklist() {
        return this.getDisallowedStarImports();
    }

    public void setDisallowedStarImports(List<String> disallowedStarImports) {
        if (this.allowedImports != null || this.allowedStarImports != null) {
            throw new IllegalArgumentException("You are not allowed to set both an allowed list and a disallowed list");
        }
        this.disallowedStarImports = SecureASTCustomizer.normalizeStarImports(disallowedStarImports);
        if (this.disallowedImports == null) {
            this.disallowedImports = Collections.emptyList();
        }
    }

    @Deprecated
    public void setStarImportsBlacklist(List<String> disallowedStarImports) {
        this.setDisallowedStarImports(disallowedStarImports);
    }

    public List<String> getAllowedStarImports() {
        return this.allowedStarImports;
    }

    @Deprecated
    public List<String> getStarImportsWhitelist() {
        return this.getAllowedStarImports();
    }

    public void setAllowedStarImports(List<String> allowedStarImports) {
        if (this.disallowedImports != null || this.disallowedStarImports != null) {
            throw new IllegalArgumentException("You are not allowed to set both an allowed list and a disallowed list");
        }
        this.allowedStarImports = SecureASTCustomizer.normalizeStarImports(allowedStarImports);
        if (this.allowedImports == null) {
            this.allowedImports = Collections.emptyList();
        }
    }

    @Deprecated
    public void setStarImportsWhitelist(List<String> allowedStarImports) {
        this.setAllowedStarImports(allowedStarImports);
    }

    private static List<String> normalizeStarImports(List<String> starImports) {
        ArrayList<String> result = new ArrayList<String>(starImports.size());
        for (String starImport : starImports) {
            if (starImport.endsWith(".*")) {
                result.add(starImport);
                continue;
            }
            if (starImport.endsWith("**")) {
                result.add(starImport.replaceFirst("\\*+$", ""));
                continue;
            }
            if (starImport.endsWith(".")) {
                result.add(starImport + "*");
                continue;
            }
            result.add(starImport + ".*");
        }
        return Collections.unmodifiableList(result);
    }

    public List<String> getDisallowedStaticImports() {
        return this.disallowedStaticImports;
    }

    @Deprecated
    public List<String> getStaticImportsBlacklist() {
        return this.getDisallowedStaticImports();
    }

    public void setDisallowedStaticImports(List<String> disallowedStaticImports) {
        if (this.allowedStaticImports != null || this.allowedStaticStarImports != null) {
            throw new IllegalArgumentException("You are not allowed to set both an allowed list and a disallowed list");
        }
        this.disallowedStaticImports = disallowedStaticImports;
    }

    @Deprecated
    public void setStaticImportsBlacklist(List<String> disallowedStaticImports) {
        this.setDisallowedStaticImports(disallowedStaticImports);
    }

    public List<String> getAllowedStaticImports() {
        return this.allowedStaticImports;
    }

    @Deprecated
    public List<String> getStaticImportsWhitelist() {
        return this.getAllowedStaticImports();
    }

    public void setAllowedStaticImports(List<String> allowedStaticImports) {
        if (this.disallowedStaticImports != null || this.disallowedStaticStarImports != null) {
            throw new IllegalArgumentException("You are not allowed to set both an allowed list and a disallowed list");
        }
        this.allowedStaticImports = allowedStaticImports;
    }

    @Deprecated
    public void setStaticImportsWhitelist(List<String> allowedStaticImports) {
        this.setAllowedStaticImports(allowedStaticImports);
    }

    public List<String> getDisallowedStaticStarImports() {
        return this.disallowedStaticStarImports;
    }

    @Deprecated
    public List<String> getStaticStarImportsBlacklist() {
        return this.getDisallowedStaticStarImports();
    }

    public void setDisallowedStaticStarImports(List<String> disallowedStaticStarImports) {
        if (this.allowedStaticImports != null || this.allowedStaticStarImports != null) {
            throw new IllegalArgumentException("You are not allowed to set both an allowed list and a disallowed list");
        }
        this.disallowedStaticStarImports = SecureASTCustomizer.normalizeStarImports(disallowedStaticStarImports);
        if (this.disallowedStaticImports == null) {
            this.disallowedStaticImports = Collections.emptyList();
        }
    }

    @Deprecated
    public void setStaticStarImportsBlacklist(List<String> disallowedStaticStarImports) {
        this.setDisallowedStaticStarImports(disallowedStaticStarImports);
    }

    public List<String> getAllowedStaticStarImports() {
        return this.allowedStaticStarImports;
    }

    @Deprecated
    public List<String> getStaticStarImportsWhitelist() {
        return this.getAllowedStaticStarImports();
    }

    public void setAllowedStaticStarImports(List<String> allowedStaticStarImports) {
        if (this.disallowedStaticImports != null || this.disallowedStaticStarImports != null) {
            throw new IllegalArgumentException("You are not allowed to set both an allowed list and a disallowed list");
        }
        this.allowedStaticStarImports = SecureASTCustomizer.normalizeStarImports(allowedStaticStarImports);
        if (this.allowedStaticImports == null) {
            this.allowedStaticImports = Collections.emptyList();
        }
    }

    @Deprecated
    public void setStaticStarImportsWhitelist(List<String> allowedStaticStarImports) {
        this.setAllowedStaticStarImports(allowedStaticStarImports);
    }

    public List<Class<? extends Expression>> getDisallowedExpressions() {
        return this.disallowedExpressions;
    }

    @Deprecated
    public List<Class<? extends Expression>> getExpressionsBlacklist() {
        return this.getDisallowedExpressions();
    }

    public void setDisallowedExpressions(List<Class<? extends Expression>> disallowedExpressions) {
        if (this.allowedExpressions != null) {
            throw new IllegalArgumentException("You are not allowed to set both an allowed list and a disallowed list");
        }
        this.disallowedExpressions = disallowedExpressions;
    }

    @Deprecated
    public void setExpressionsBlacklist(List<Class<? extends Expression>> disallowedExpressions) {
        this.setDisallowedExpressions(disallowedExpressions);
    }

    public List<Class<? extends Expression>> getAllowedExpressions() {
        return this.allowedExpressions;
    }

    @Deprecated
    public List<Class<? extends Expression>> getExpressionsWhitelist() {
        return this.getAllowedExpressions();
    }

    public void setAllowedExpressions(List<Class<? extends Expression>> allowedExpressions) {
        if (this.disallowedExpressions != null) {
            throw new IllegalArgumentException("You are not allowed to set both an allowed list and a disallowed list");
        }
        this.allowedExpressions = allowedExpressions;
    }

    @Deprecated
    public void setExpressionsWhitelist(List<Class<? extends Expression>> allowedExpressions) {
        this.setAllowedExpressions(allowedExpressions);
    }

    public List<Class<? extends Statement>> getDisallowedStatements() {
        return this.disallowedStatements;
    }

    @Deprecated
    public List<Class<? extends Statement>> getStatementsBlacklist() {
        return this.getDisallowedStatements();
    }

    public void setDisallowedStatements(List<Class<? extends Statement>> disallowedStatements) {
        if (this.allowedStatements != null) {
            throw new IllegalArgumentException("You are not allowed to set both an allowed list and a disallowed list");
        }
        this.disallowedStatements = disallowedStatements;
    }

    @Deprecated
    public void setStatementsBlacklist(List<Class<? extends Statement>> disallowedStatements) {
        this.setDisallowedStatements(disallowedStatements);
    }

    public List<Class<? extends Statement>> getAllowedStatements() {
        return this.allowedStatements;
    }

    @Deprecated
    public List<Class<? extends Statement>> getStatementsWhitelist() {
        return this.getAllowedStatements();
    }

    public void setAllowedStatements(List<Class<? extends Statement>> allowedStatements) {
        if (this.disallowedStatements != null) {
            throw new IllegalArgumentException("You are not allowed to set both an allowed list and a disallowed list");
        }
        this.allowedStatements = allowedStatements;
    }

    @Deprecated
    public void setStatementsWhitelist(List<Class<? extends Statement>> allowedStatements) {
        this.setAllowedStatements(allowedStatements);
    }

    public boolean isIndirectImportCheckEnabled() {
        return this.isIndirectImportCheckEnabled;
    }

    public void setIndirectImportCheckEnabled(boolean indirectImportCheckEnabled) {
        this.isIndirectImportCheckEnabled = indirectImportCheckEnabled;
    }

    public List<Integer> getDisallowedTokens() {
        return this.disallowedTokens;
    }

    @Deprecated
    public List<Integer> getTokensBlacklist() {
        return this.getDisallowedTokens();
    }

    public void setDisallowedTokens(List<Integer> disallowedTokens) {
        if (this.allowedTokens != null) {
            throw new IllegalArgumentException("You are not allowed to set both an allowed list and a disallowed list");
        }
        this.disallowedTokens = disallowedTokens;
    }

    @Deprecated
    public void setTokensBlacklist(List<Integer> disallowedTokens) {
        this.setDisallowedTokens(disallowedTokens);
    }

    public List<Integer> getAllowedTokens() {
        return this.allowedTokens;
    }

    @Deprecated
    public List<Integer> getTokensWhitelist() {
        return this.getAllowedTokens();
    }

    public void setAllowedTokens(List<Integer> allowedTokens) {
        if (this.disallowedTokens != null) {
            throw new IllegalArgumentException("You are not allowed to set both an allowed list and a disallowed list");
        }
        this.allowedTokens = allowedTokens;
    }

    @Deprecated
    public void setTokensWhitelist(List<Integer> allowedTokens) {
        this.setAllowedTokens(allowedTokens);
    }

    public void addStatementCheckers(StatementChecker ... checkers) {
        this.statementCheckers.addAll(Arrays.asList(checkers));
    }

    public void addExpressionCheckers(ExpressionChecker ... checkers) {
        this.expressionCheckers.addAll(Arrays.asList(checkers));
    }

    public List<String> getDisallowedConstantTypes() {
        return this.disallowedConstantTypes;
    }

    @Deprecated
    public List<String> getConstantTypesBlackList() {
        return this.getDisallowedConstantTypes();
    }

    public void setConstantTypesBlackList(List<String> constantTypesBlackList) {
        if (this.allowedConstantTypes != null) {
            throw new IllegalArgumentException("You are not allowed to set both an allowed list and a disallowed list");
        }
        this.disallowedConstantTypes = constantTypesBlackList;
    }

    public List<String> getAllowedConstantTypes() {
        return this.allowedConstantTypes;
    }

    @Deprecated
    public List<String> getConstantTypesWhiteList() {
        return this.getAllowedConstantTypes();
    }

    public void setAllowedConstantTypes(List<String> allowedConstantTypes) {
        if (this.disallowedConstantTypes != null) {
            throw new IllegalArgumentException("You are not allowed to set both an allowed list and a disallowed list");
        }
        this.allowedConstantTypes = allowedConstantTypes;
    }

    @Deprecated
    public void setConstantTypesWhiteList(List<String> allowedConstantTypes) {
        this.setAllowedConstantTypes(allowedConstantTypes);
    }

    public void setAllowedConstantTypesClasses(List<Class> allowedConstantTypes) {
        LinkedList<String> values = new LinkedList<String>();
        for (Class aClass : allowedConstantTypes) {
            values.add(aClass.getName());
        }
        this.setConstantTypesWhiteList(values);
    }

    @Deprecated
    public void setConstantTypesClassesWhiteList(List<Class> allowedConstantTypes) {
        this.setAllowedConstantTypesClasses(allowedConstantTypes);
    }

    public void setDisallowedConstantTypesClasses(List<Class> disallowedConstantTypes) {
        LinkedList<String> values = new LinkedList<String>();
        for (Class aClass : disallowedConstantTypes) {
            values.add(aClass.getName());
        }
        this.setConstantTypesBlackList(values);
    }

    @Deprecated
    public void setConstantTypesClassesBlackList(List<Class> disallowedConstantTypes) {
        this.setDisallowedConstantTypesClasses(disallowedConstantTypes);
    }

    public List<String> getDisallowedReceivers() {
        return this.disallowedReceivers;
    }

    @Deprecated
    public List<String> getReceiversBlackList() {
        return this.getDisallowedReceivers();
    }

    public void setDisallowedReceivers(List<String> disallowedReceivers) {
        if (this.allowedReceivers != null) {
            throw new IllegalArgumentException("You are not allowed to set both an allowed list and a disallowed list");
        }
        this.disallowedReceivers = disallowedReceivers;
    }

    @Deprecated
    public void setReceiversBlackList(List<String> disallowedReceivers) {
        this.setDisallowedReceivers(disallowedReceivers);
    }

    public void setDisallowedReceiversClasses(List<Class> disallowedReceivers) {
        LinkedList<String> values = new LinkedList<String>();
        for (Class aClass : disallowedReceivers) {
            values.add(aClass.getName());
        }
        this.setReceiversBlackList(values);
    }

    @Deprecated
    public void setReceiversClassesBlackList(List<Class> disallowedReceivers) {
        this.setDisallowedReceiversClasses(disallowedReceivers);
    }

    public List<String> getAllowedReceivers() {
        return this.allowedReceivers;
    }

    @Deprecated
    public List<String> getReceiversWhiteList() {
        return this.getAllowedReceivers();
    }

    public void setAllowedReceivers(List<String> allowedReceivers) {
        if (this.disallowedReceivers != null) {
            throw new IllegalArgumentException("You are not allowed to set both an allowed list and a disallowed list");
        }
        this.allowedReceivers = allowedReceivers;
    }

    @Deprecated
    public void setReceiversWhiteList(List<String> allowedReceivers) {
        if (this.disallowedReceivers != null) {
            throw new IllegalArgumentException("You are not allowed to set both an allowed list and a disallowed list");
        }
        this.allowedReceivers = allowedReceivers;
    }

    public void setAllowedReceiversClasses(List<Class> allowedReceivers) {
        LinkedList<String> values = new LinkedList<String>();
        for (Class aClass : allowedReceivers) {
            values.add(aClass.getName());
        }
        this.setReceiversWhiteList(values);
    }

    @Deprecated
    public void setReceiversClassesWhiteList(List<Class> allowedReceivers) {
        this.setAllowedReceiversClasses(allowedReceivers);
    }

    @Override
    public void call(SourceUnit source, GeneratorContext context, ClassNode classNode) throws CompilationFailedException {
        ModuleNode ast = source.getAST();
        if (!this.isPackageAllowed && ast.getPackage() != null) {
            throw new SecurityException("Package definitions are not allowed");
        }
        this.checkMethodDefinitionAllowed(classNode);
        if (this.disallowedImports != null || this.allowedImports != null || this.disallowedStarImports != null || this.allowedStarImports != null) {
            for (ImportNode importNode : ast.getImports()) {
                this.assertImportIsAllowed(importNode.getClassName());
            }
            for (ImportNode importNode : ast.getStarImports()) {
                this.assertStarImportIsAllowed(importNode.getPackageName() + "*");
            }
        }
        if (this.disallowedStaticImports != null || this.allowedStaticImports != null || this.disallowedStaticStarImports != null || this.allowedStaticStarImports != null) {
            String className;
            for (Map.Entry entry : ast.getStaticImports().entrySet()) {
                className = ((ImportNode)entry.getValue()).getClassName();
                this.assertStaticImportIsAllowed((String)entry.getKey(), className);
            }
            for (Map.Entry entry : ast.getStaticStarImports().entrySet()) {
                className = ((ImportNode)entry.getValue()).getClassName();
                this.assertStaticImportIsAllowed((String)entry.getKey(), className);
            }
        }
        GroovyCodeVisitor visitor = this.createGroovyCodeVisitor();
        ast.getStatementBlock().visit(visitor);
        for (ClassNode clNode : ast.getClasses()) {
            if (clNode == classNode) continue;
            this.checkMethodDefinitionAllowed(clNode);
            for (MethodNode methodNode : clNode.getMethods()) {
                if (methodNode.isSynthetic() || methodNode.getCode() == null) continue;
                methodNode.getCode().visit(visitor);
            }
        }
        List<MethodNode> list = SecureASTCustomizer.filterMethods(classNode);
        if (this.isMethodDefinitionAllowed) {
            for (MethodNode method : list) {
                if (method.getDeclaringClass() != classNode || method.getCode() == null) continue;
                method.getCode().visit(visitor);
            }
        }
    }

    protected GroovyCodeVisitor createGroovyCodeVisitor() {
        return new SecuringCodeVisitor();
    }

    protected void checkMethodDefinitionAllowed(ClassNode owner) {
        if (this.isMethodDefinitionAllowed) {
            return;
        }
        List<MethodNode> methods = SecureASTCustomizer.filterMethods(owner);
        if (!methods.isEmpty()) {
            throw new SecurityException("Method definitions are not allowed");
        }
    }

    protected static List<MethodNode> filterMethods(ClassNode owner) {
        LinkedList<MethodNode> result = new LinkedList<MethodNode>();
        List<MethodNode> methods = owner.getMethods();
        for (MethodNode method : methods) {
            if (method.getDeclaringClass() != owner || method.isSynthetic() || "main".equals(method.getName()) || "run".equals(method.getName()) && owner.isScriptBody()) continue;
            result.add(method);
        }
        return result;
    }

    protected void assertStarImportIsAllowed(String packageName) {
        block5: {
            block6: {
                if (this.allowedStarImports != null && !this.allowedStarImports.contains(packageName)) {
                    if (!this.allowedStarImports.stream().filter(it -> it.endsWith(".")).anyMatch(packageName::startsWith)) {
                        throw new SecurityException("Importing [" + packageName + "] is not allowed");
                    }
                }
                if (this.disallowedStarImports == null) break block5;
                if (this.disallowedStarImports.contains(packageName)) break block6;
                if (!this.disallowedStarImports.stream().filter(it -> it.endsWith(".")).anyMatch(packageName::startsWith)) break block5;
            }
            throw new SecurityException("Importing [" + packageName + "] is not allowed");
        }
    }

    protected void assertImportIsAllowed(String className) {
        block11: {
            block12: {
                block8: {
                    block9: {
                        block10: {
                            if (this.allowedImports == null && this.allowedStarImports == null) break block8;
                            if (this.allowedImports != null && this.allowedImports.contains(className)) {
                                return;
                            }
                            if (this.allowedStarImports == null) break block9;
                            String packageName = this.getWildCardImport(className);
                            if (this.allowedStarImports.contains(packageName)) break block10;
                            if (!this.allowedStarImports.stream().filter(it -> it.endsWith(".")).anyMatch(packageName::startsWith)) break block9;
                        }
                        return;
                    }
                    throw new SecurityException("Importing [" + className + "] is not allowed");
                }
                if (this.disallowedImports != null && this.disallowedImports.contains(className)) {
                    throw new SecurityException("Importing [" + className + "] is not allowed");
                }
                if (this.disallowedStarImports == null) break block11;
                String packageName = this.getWildCardImport(className);
                if (this.disallowedStarImports.contains(packageName)) break block12;
                if (!this.disallowedStarImports.stream().filter(it -> it.endsWith(".")).anyMatch(packageName::startsWith)) break block11;
            }
            throw new SecurityException("Importing [" + className + "] is not allowed");
        }
    }

    private String getWildCardImport(String className) {
        return className.substring(0, className.lastIndexOf(46) + 1) + "*";
    }

    protected void assertStaticImportIsAllowed(String member, String className) {
        block10: {
            String fqn;
            block11: {
                String packageName;
                String string = fqn = className.equals(member) ? className : className + "." + member;
                if (this.allowedStaticImports != null && !this.allowedStaticImports.contains(fqn)) {
                    if (this.allowedStaticStarImports != null) {
                        packageName = this.getWildCardImport(className);
                        if (!this.allowedStaticStarImports.contains(className + ".*")) {
                            if (this.allowedStaticStarImports.stream().filter(it -> it.endsWith(".")).noneMatch(packageName::startsWith)) {
                                throw new SecurityException("Importing [" + fqn + "] is not allowed");
                            }
                        }
                    } else {
                        throw new SecurityException("Importing [" + fqn + "] is not allowed");
                    }
                }
                if (this.disallowedStaticImports != null && this.disallowedStaticImports.contains(fqn)) {
                    throw new SecurityException("Importing [" + fqn + "] is not allowed");
                }
                if (this.disallowedStaticStarImports == null) break block10;
                packageName = this.getWildCardImport(className);
                if (this.disallowedStaticStarImports.contains(className + ".*")) break block11;
                if (!this.disallowedStaticStarImports.stream().filter(it -> it.endsWith(".")).anyMatch(packageName::startsWith)) break block10;
            }
            throw new SecurityException("Importing [" + fqn + "] is not allowed");
        }
    }

    protected class SecuringCodeVisitor
    implements GroovyCodeVisitor {
        protected SecuringCodeVisitor() {
        }

        protected void assertStatementAuthorized(Statement statement) throws SecurityException {
            Class<?> clazz = statement.getClass();
            if (SecureASTCustomizer.this.disallowedStatements != null && SecureASTCustomizer.this.disallowedStatements.contains(clazz)) {
                throw new SecurityException(clazz.getSimpleName() + "s are not allowed");
            }
            if (SecureASTCustomizer.this.allowedStatements != null && !SecureASTCustomizer.this.allowedStatements.contains(clazz)) {
                throw new SecurityException(clazz.getSimpleName() + "s are not allowed");
            }
            for (StatementChecker statementChecker : SecureASTCustomizer.this.statementCheckers) {
                if (statementChecker.isAuthorized(statement)) continue;
                throw new SecurityException("Statement [" + clazz.getSimpleName() + "] is not allowed");
            }
        }

        protected void assertExpressionAuthorized(Expression expression) throws SecurityException {
            Class<?> clazz = expression.getClass();
            if (SecureASTCustomizer.this.disallowedExpressions != null && SecureASTCustomizer.this.disallowedExpressions.contains(clazz)) {
                throw new SecurityException(clazz.getSimpleName() + "s are not allowed: " + expression.getText());
            }
            if (SecureASTCustomizer.this.allowedExpressions != null && !SecureASTCustomizer.this.allowedExpressions.contains(clazz)) {
                throw new SecurityException(clazz.getSimpleName() + "s are not allowed: " + expression.getText());
            }
            for (ExpressionChecker expressionChecker : SecureASTCustomizer.this.expressionCheckers) {
                if (expressionChecker.isAuthorized(expression)) continue;
                throw new SecurityException("Expression [" + clazz.getSimpleName() + "] is not allowed: " + expression.getText());
            }
            if (SecureASTCustomizer.this.isIndirectImportCheckEnabled) {
                try {
                    String typename;
                    Expression expr;
                    if (expression instanceof ConstructorCallExpression) {
                        SecureASTCustomizer.this.assertImportIsAllowed(expression.getType().getName());
                    } else if (expression instanceof MethodCallExpression) {
                        expr = (MethodCallExpression)expression;
                        ClassNode objectExpressionType = ((MethodCallExpression)expr).getObjectExpression().getType();
                        String typename2 = this.getExpressionType(objectExpressionType).getName();
                        SecureASTCustomizer.this.assertImportIsAllowed(typename2);
                        SecureASTCustomizer.this.assertStaticImportIsAllowed(((MethodCallExpression)expr).getMethodAsString(), typename2);
                    } else if (expression instanceof StaticMethodCallExpression) {
                        expr = (StaticMethodCallExpression)expression;
                        typename = ((StaticMethodCallExpression)expr).getOwnerType().getName();
                        SecureASTCustomizer.this.assertImportIsAllowed(typename);
                        SecureASTCustomizer.this.assertStaticImportIsAllowed(((StaticMethodCallExpression)expr).getMethod(), typename);
                    } else if (expression instanceof MethodPointerExpression) {
                        expr = (MethodPointerExpression)expression;
                        typename = expr.getType().getName();
                        SecureASTCustomizer.this.assertImportIsAllowed(typename);
                        SecureASTCustomizer.this.assertStaticImportIsAllowed(((MethodPointerExpression)expr).getText(), typename);
                    }
                }
                catch (SecurityException e) {
                    throw new SecurityException("Indirect import checks prevents usage of expression", e);
                }
            }
        }

        protected ClassNode getExpressionType(ClassNode objectExpressionType) {
            return objectExpressionType.isArray() ? this.getExpressionType(objectExpressionType.getComponentType()) : objectExpressionType;
        }

        protected void assertTokenAuthorized(Token token) throws SecurityException {
            int value = token.getType();
            if (SecureASTCustomizer.this.disallowedTokens != null && SecureASTCustomizer.this.disallowedTokens.contains(value)) {
                throw new SecurityException("Token " + token + " is not allowed");
            }
            if (SecureASTCustomizer.this.allowedTokens != null && !SecureASTCustomizer.this.allowedTokens.contains(value)) {
                throw new SecurityException("Token " + token + " is not allowed");
            }
        }

        @Override
        public void visitBlockStatement(BlockStatement block) {
            this.assertStatementAuthorized(block);
            for (Statement statement : block.getStatements()) {
                statement.visit(this);
            }
        }

        @Override
        public void visitForLoop(ForStatement forLoop) {
            this.assertStatementAuthorized(forLoop);
            forLoop.getCollectionExpression().visit(this);
            forLoop.getLoopBlock().visit(this);
        }

        @Override
        public void visitWhileLoop(WhileStatement loop) {
            this.assertStatementAuthorized(loop);
            loop.getBooleanExpression().visit(this);
            loop.getLoopBlock().visit(this);
        }

        @Override
        public void visitDoWhileLoop(DoWhileStatement loop) {
            this.assertStatementAuthorized(loop);
            loop.getBooleanExpression().visit(this);
            loop.getLoopBlock().visit(this);
        }

        @Override
        public void visitIfElse(IfStatement ifElse) {
            this.assertStatementAuthorized(ifElse);
            ifElse.getBooleanExpression().visit(this);
            ifElse.getIfBlock().visit(this);
            ifElse.getElseBlock().visit(this);
        }

        @Override
        public void visitExpressionStatement(ExpressionStatement statement) {
            this.assertStatementAuthorized(statement);
            statement.getExpression().visit(this);
        }

        @Override
        public void visitReturnStatement(ReturnStatement statement) {
            this.assertStatementAuthorized(statement);
            statement.getExpression().visit(this);
        }

        @Override
        public void visitAssertStatement(AssertStatement statement) {
            this.assertStatementAuthorized(statement);
            statement.getBooleanExpression().visit(this);
            statement.getMessageExpression().visit(this);
        }

        @Override
        public void visitTryCatchFinally(TryCatchStatement statement) {
            this.assertStatementAuthorized(statement);
            statement.getTryStatement().visit(this);
            for (CatchStatement catchStatement : statement.getCatchStatements()) {
                catchStatement.visit(this);
            }
            statement.getFinallyStatement().visit(this);
        }

        @Override
        public void visitEmptyStatement(EmptyStatement statement) {
        }

        @Override
        public void visitSwitch(SwitchStatement statement) {
            this.assertStatementAuthorized(statement);
            statement.getExpression().visit(this);
            for (CaseStatement caseStatement : statement.getCaseStatements()) {
                caseStatement.visit(this);
            }
            statement.getDefaultStatement().visit(this);
        }

        @Override
        public void visitCaseStatement(CaseStatement statement) {
            this.assertStatementAuthorized(statement);
            statement.getExpression().visit(this);
            statement.getCode().visit(this);
        }

        @Override
        public void visitBreakStatement(BreakStatement statement) {
            this.assertStatementAuthorized(statement);
        }

        @Override
        public void visitContinueStatement(ContinueStatement statement) {
            this.assertStatementAuthorized(statement);
        }

        @Override
        public void visitThrowStatement(ThrowStatement statement) {
            this.assertStatementAuthorized(statement);
            statement.getExpression().visit(this);
        }

        @Override
        public void visitSynchronizedStatement(SynchronizedStatement statement) {
            this.assertStatementAuthorized(statement);
            statement.getExpression().visit(this);
            statement.getCode().visit(this);
        }

        @Override
        public void visitCatchStatement(CatchStatement statement) {
            this.assertStatementAuthorized(statement);
            statement.getCode().visit(this);
        }

        @Override
        public void visitMethodCallExpression(MethodCallExpression call) {
            this.assertExpressionAuthorized(call);
            Expression receiver = call.getObjectExpression();
            String typeName = receiver.getType().getName();
            if (SecureASTCustomizer.this.allowedReceivers != null && !SecureASTCustomizer.this.allowedReceivers.contains(typeName)) {
                throw new SecurityException("Method calls not allowed on [" + typeName + "]");
            }
            if (SecureASTCustomizer.this.disallowedReceivers != null && SecureASTCustomizer.this.disallowedReceivers.contains(typeName)) {
                throw new SecurityException("Method calls not allowed on [" + typeName + "]");
            }
            receiver.visit(this);
            Expression method = call.getMethod();
            this.checkConstantTypeIfNotMethodNameOrProperty(method);
            call.getArguments().visit(this);
        }

        @Override
        public void visitStaticMethodCallExpression(StaticMethodCallExpression call) {
            this.assertExpressionAuthorized(call);
            String typeName = call.getOwnerType().getName();
            if (SecureASTCustomizer.this.allowedReceivers != null && !SecureASTCustomizer.this.allowedReceivers.contains(typeName)) {
                throw new SecurityException("Method calls not allowed on [" + typeName + "]");
            }
            if (SecureASTCustomizer.this.disallowedReceivers != null && SecureASTCustomizer.this.disallowedReceivers.contains(typeName)) {
                throw new SecurityException("Method calls not allowed on [" + typeName + "]");
            }
            call.getArguments().visit(this);
        }

        @Override
        public void visitConstructorCallExpression(ConstructorCallExpression call) {
            this.assertExpressionAuthorized(call);
            call.getArguments().visit(this);
        }

        @Override
        public void visitTernaryExpression(TernaryExpression expression) {
            this.assertExpressionAuthorized(expression);
            expression.getBooleanExpression().visit(this);
            expression.getTrueExpression().visit(this);
            expression.getFalseExpression().visit(this);
        }

        @Override
        public void visitShortTernaryExpression(ElvisOperatorExpression expression) {
            this.assertExpressionAuthorized(expression);
            this.visitTernaryExpression(expression);
        }

        @Override
        public void visitBinaryExpression(BinaryExpression expression) {
            this.assertExpressionAuthorized(expression);
            this.assertTokenAuthorized(expression.getOperation());
            expression.getLeftExpression().visit(this);
            expression.getRightExpression().visit(this);
        }

        @Override
        public void visitPrefixExpression(PrefixExpression expression) {
            this.assertExpressionAuthorized(expression);
            this.assertTokenAuthorized(expression.getOperation());
            expression.getExpression().visit(this);
        }

        @Override
        public void visitPostfixExpression(PostfixExpression expression) {
            this.assertExpressionAuthorized(expression);
            this.assertTokenAuthorized(expression.getOperation());
            expression.getExpression().visit(this);
        }

        @Override
        public void visitBooleanExpression(BooleanExpression expression) {
            this.assertExpressionAuthorized(expression);
            expression.getExpression().visit(this);
        }

        @Override
        public void visitClosureExpression(ClosureExpression expression) {
            this.assertExpressionAuthorized(expression);
            if (!SecureASTCustomizer.this.isClosuresAllowed) {
                throw new SecurityException("Closures are not allowed");
            }
            expression.getCode().visit(this);
        }

        @Override
        public void visitLambdaExpression(LambdaExpression expression) {
            this.visitClosureExpression(expression);
        }

        @Override
        public void visitTupleExpression(TupleExpression expression) {
            this.assertExpressionAuthorized(expression);
            this.visitListOfExpressions(expression.getExpressions());
        }

        @Override
        public void visitMapExpression(MapExpression expression) {
            this.assertExpressionAuthorized(expression);
            this.visitListOfExpressions(expression.getMapEntryExpressions());
        }

        @Override
        public void visitMapEntryExpression(MapEntryExpression expression) {
            this.assertExpressionAuthorized(expression);
            expression.getKeyExpression().visit(this);
            expression.getValueExpression().visit(this);
        }

        @Override
        public void visitListExpression(ListExpression expression) {
            this.assertExpressionAuthorized(expression);
            this.visitListOfExpressions(expression.getExpressions());
        }

        @Override
        public void visitRangeExpression(RangeExpression expression) {
            this.assertExpressionAuthorized(expression);
            expression.getFrom().visit(this);
            expression.getTo().visit(this);
        }

        @Override
        public void visitPropertyExpression(PropertyExpression expression) {
            this.assertExpressionAuthorized(expression);
            Expression receiver = expression.getObjectExpression();
            String typeName = receiver.getType().getName();
            if (SecureASTCustomizer.this.allowedReceivers != null && !SecureASTCustomizer.this.allowedReceivers.contains(typeName)) {
                throw new SecurityException("Property access not allowed on [" + typeName + "]");
            }
            if (SecureASTCustomizer.this.disallowedReceivers != null && SecureASTCustomizer.this.disallowedReceivers.contains(typeName)) {
                throw new SecurityException("Property access not allowed on [" + typeName + "]");
            }
            receiver.visit(this);
            Expression property = expression.getProperty();
            this.checkConstantTypeIfNotMethodNameOrProperty(property);
        }

        private void checkConstantTypeIfNotMethodNameOrProperty(Expression expr) {
            if (expr instanceof ConstantExpression) {
                if (!"java.lang.String".equals(expr.getType().getName())) {
                    expr.visit(this);
                }
            } else {
                expr.visit(this);
            }
        }

        @Override
        public void visitAttributeExpression(AttributeExpression expression) {
            this.assertExpressionAuthorized(expression);
            Expression receiver = expression.getObjectExpression();
            String typeName = receiver.getType().getName();
            if (SecureASTCustomizer.this.allowedReceivers != null && !SecureASTCustomizer.this.allowedReceivers.contains(typeName)) {
                throw new SecurityException("Attribute access not allowed on [" + typeName + "]");
            }
            if (SecureASTCustomizer.this.disallowedReceivers != null && SecureASTCustomizer.this.disallowedReceivers.contains(typeName)) {
                throw new SecurityException("Attribute access not allowed on [" + typeName + "]");
            }
            receiver.visit(this);
            Expression property = expression.getProperty();
            this.checkConstantTypeIfNotMethodNameOrProperty(property);
        }

        @Override
        public void visitFieldExpression(FieldExpression expression) {
            this.assertExpressionAuthorized(expression);
        }

        @Override
        public void visitMethodPointerExpression(MethodPointerExpression expression) {
            this.assertExpressionAuthorized(expression);
            expression.getExpression().visit(this);
            expression.getMethodName().visit(this);
        }

        @Override
        public void visitMethodReferenceExpression(MethodReferenceExpression expression) {
            this.visitMethodPointerExpression(expression);
        }

        @Override
        public void visitConstantExpression(ConstantExpression expression) {
            this.assertExpressionAuthorized(expression);
            String type = expression.getType().getName();
            if (SecureASTCustomizer.this.allowedConstantTypes != null && !SecureASTCustomizer.this.allowedConstantTypes.contains(type)) {
                throw new SecurityException("Constant expression type [" + type + "] is not allowed");
            }
            if (SecureASTCustomizer.this.disallowedConstantTypes != null && SecureASTCustomizer.this.disallowedConstantTypes.contains(type)) {
                throw new SecurityException("Constant expression type [" + type + "] is not allowed");
            }
        }

        @Override
        public void visitClassExpression(ClassExpression expression) {
            this.assertExpressionAuthorized(expression);
        }

        @Override
        public void visitVariableExpression(VariableExpression expression) {
            this.assertExpressionAuthorized(expression);
            String type = expression.getType().getName();
            if (SecureASTCustomizer.this.allowedConstantTypes != null && !SecureASTCustomizer.this.allowedConstantTypes.contains(type)) {
                throw new SecurityException("Usage of variables of type [" + type + "] is not allowed");
            }
            if (SecureASTCustomizer.this.disallowedConstantTypes != null && SecureASTCustomizer.this.disallowedConstantTypes.contains(type)) {
                throw new SecurityException("Usage of variables of type [" + type + "] is not allowed");
            }
        }

        @Override
        public void visitDeclarationExpression(DeclarationExpression expression) {
            this.assertExpressionAuthorized(expression);
            this.visitBinaryExpression(expression);
        }

        @Override
        public void visitGStringExpression(GStringExpression expression) {
            this.assertExpressionAuthorized(expression);
            this.visitListOfExpressions(expression.getStrings());
            this.visitListOfExpressions(expression.getValues());
        }

        @Override
        public void visitArrayExpression(ArrayExpression expression) {
            this.assertExpressionAuthorized(expression);
            this.visitListOfExpressions(expression.getExpressions());
            this.visitListOfExpressions(expression.getSizeExpression());
        }

        @Override
        public void visitSpreadExpression(SpreadExpression expression) {
            this.assertExpressionAuthorized(expression);
            expression.getExpression().visit(this);
        }

        @Override
        public void visitSpreadMapExpression(SpreadMapExpression expression) {
            this.assertExpressionAuthorized(expression);
            expression.getExpression().visit(this);
        }

        @Override
        public void visitNotExpression(NotExpression expression) {
            this.assertExpressionAuthorized(expression);
            expression.getExpression().visit(this);
        }

        @Override
        public void visitUnaryMinusExpression(UnaryMinusExpression expression) {
            this.assertExpressionAuthorized(expression);
            expression.getExpression().visit(this);
        }

        @Override
        public void visitUnaryPlusExpression(UnaryPlusExpression expression) {
            this.assertExpressionAuthorized(expression);
            expression.getExpression().visit(this);
        }

        @Override
        public void visitBitwiseNegationExpression(BitwiseNegationExpression expression) {
            this.assertExpressionAuthorized(expression);
            expression.getExpression().visit(this);
        }

        @Override
        public void visitCastExpression(CastExpression expression) {
            this.assertExpressionAuthorized(expression);
            expression.getExpression().visit(this);
        }

        @Override
        public void visitArgumentlistExpression(ArgumentListExpression expression) {
            this.assertExpressionAuthorized(expression);
            this.visitTupleExpression(expression);
        }

        @Override
        public void visitClosureListExpression(ClosureListExpression closureListExpression) {
            this.assertExpressionAuthorized(closureListExpression);
            if (!SecureASTCustomizer.this.isClosuresAllowed) {
                throw new SecurityException("Closures are not allowed");
            }
            this.visitListOfExpressions(closureListExpression.getExpressions());
        }

        @Override
        public void visitBytecodeExpression(BytecodeExpression expression) {
            this.assertExpressionAuthorized(expression);
        }
    }

    @FunctionalInterface
    public static interface StatementChecker {
        public boolean isAuthorized(Statement var1);
    }

    @FunctionalInterface
    public static interface ExpressionChecker {
        public boolean isAuthorized(Expression var1);
    }
}

