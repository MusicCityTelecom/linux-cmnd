/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.stc;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.SwitchStatement;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.ErrorCollector;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.stc.DelegationMetadata;
import org.codehaus.groovy.transform.stc.SecondPassExpression;
import org.codehaus.groovy.transform.stc.StaticTypeCheckingSupport;
import org.codehaus.groovy.transform.stc.StaticTypeCheckingVisitor;

public class TypeCheckingContext {
    protected final StaticTypeCheckingVisitor visitor;
    protected SourceUnit source;
    protected CompilationUnit compilationUnit;
    protected final LinkedList<ErrorCollector> errorCollectors = new LinkedList();
    protected Stack<Map<Object, List<ClassNode>>> temporaryIfBranchTypeInformation = new Stack();
    protected DelegationMetadata delegationMetadata;
    protected boolean isInStaticContext;
    protected ClassNode lastImplicitItType;
    protected Set<MethodNode> methodsToBeVisited = Collections.emptySet();
    protected Map<VariableExpression, List<ClassNode>> ifElseForWhileAssignmentTracker;
    protected final IdentityHashMap<BlockStatement, Map<VariableExpression, List<ClassNode>>> blockStatements2Types = new IdentityHashMap();
    protected Set<MethodNode> alreadyVisitedMethods = new HashSet<MethodNode>();
    protected final LinkedHashSet<SecondPassExpression> secondPassExpressions = new LinkedHashSet();
    protected final Map<VariableExpression, List<ClassNode>> closureSharedVariablesAssignmentTypes = new HashMap<VariableExpression, List<ClassNode>>();
    protected Map<Parameter, ClassNode> controlStructureVariables = new HashMap<Parameter, ClassNode>();
    protected final Set<Long> reportedErrors = new TreeSet<Long>();
    protected final LinkedList<ClassNode> enclosingClassNodes = new LinkedList();
    protected final LinkedList<MethodNode> enclosingMethods = new LinkedList();
    protected final LinkedList<Expression> enclosingMethodCalls = new LinkedList();
    protected final LinkedList<BlockStatement> enclosingBlocks = new LinkedList();
    protected final LinkedList<SwitchStatement> switchStatements = new LinkedList();
    protected final LinkedList<EnclosingClosure> enclosingClosures = new LinkedList();
    protected final LinkedList<BinaryExpression> enclosingBinaryExpressions = new LinkedList();

    public TypeCheckingContext(StaticTypeCheckingVisitor visitor) {
        this.visitor = visitor;
    }

    public SourceUnit getSource() {
        return this.source;
    }

    public CompilationUnit getCompilationUnit() {
        return this.compilationUnit;
    }

    public void setCompilationUnit(CompilationUnit compilationUnit) {
        this.compilationUnit = compilationUnit;
    }

    public ErrorCollector pushErrorCollector() {
        CompilerConfiguration config = Optional.ofNullable(this.getErrorCollector()).map(ErrorCollector::getConfiguration).orElseGet(() -> this.getSource().getConfiguration());
        ErrorCollector collector = new ErrorCollector(config);
        this.pushErrorCollector(collector);
        return collector;
    }

    public void pushErrorCollector(ErrorCollector collector) {
        this.errorCollectors.addFirst(collector);
    }

    public ErrorCollector popErrorCollector() {
        return this.errorCollectors.removeFirst();
    }

    public ErrorCollector getErrorCollector() {
        if (this.errorCollectors.isEmpty()) {
            return null;
        }
        return this.errorCollectors.getFirst();
    }

    public List<ErrorCollector> getErrorCollectors() {
        return Collections.unmodifiableList(this.errorCollectors);
    }

    public void pushTemporaryTypeInfo() {
        HashMap potentialTypes = new HashMap();
        this.temporaryIfBranchTypeInformation.push(potentialTypes);
    }

    public void popTemporaryTypeInfo() {
        this.temporaryIfBranchTypeInformation.pop();
    }

    public void pushEnclosingBinaryExpression(BinaryExpression binaryExpression) {
        this.enclosingBinaryExpressions.addFirst(binaryExpression);
    }

    public BinaryExpression popEnclosingBinaryExpression() {
        return this.enclosingBinaryExpressions.removeFirst();
    }

    public BinaryExpression getEnclosingBinaryExpression() {
        if (this.enclosingBinaryExpressions.isEmpty()) {
            return null;
        }
        return this.enclosingBinaryExpressions.getFirst();
    }

    public List<BinaryExpression> getEnclosingBinaryExpressionStack() {
        return Collections.unmodifiableList(this.enclosingBinaryExpressions);
    }

    public boolean isTargetOfEnclosingAssignment(Expression expression) {
        return Optional.ofNullable(this.getEnclosingBinaryExpression()).filter(be -> be.getLeftExpression() == expression && StaticTypeCheckingSupport.isAssignment(be.getOperation().getType())).isPresent();
    }

    public void pushEnclosingClosureExpression(ClosureExpression closureExpression) {
        this.enclosingClosures.addFirst(new EnclosingClosure(closureExpression));
    }

    public EnclosingClosure popEnclosingClosure() {
        return this.enclosingClosures.removeFirst();
    }

    public EnclosingClosure getEnclosingClosure() {
        if (this.enclosingClosures.isEmpty()) {
            return null;
        }
        return this.enclosingClosures.getFirst();
    }

    public List<EnclosingClosure> getEnclosingClosureStack() {
        return Collections.unmodifiableList(this.enclosingClosures);
    }

    public void pushEnclosingClassNode(ClassNode classNode) {
        this.enclosingClassNodes.addFirst(classNode);
    }

    public ClassNode popEnclosingClassNode() {
        return this.enclosingClassNodes.removeFirst();
    }

    public ClassNode getEnclosingClassNode() {
        if (this.enclosingClassNodes.isEmpty()) {
            return null;
        }
        return this.enclosingClassNodes.getFirst();
    }

    public List<ClassNode> getEnclosingClassNodes() {
        return Collections.unmodifiableList(this.enclosingClassNodes);
    }

    public void pushEnclosingMethod(MethodNode methodNode) {
        this.enclosingMethods.addFirst(methodNode);
    }

    public MethodNode popEnclosingMethod() {
        return this.enclosingMethods.removeFirst();
    }

    public MethodNode getEnclosingMethod() {
        if (this.enclosingMethods.isEmpty()) {
            return null;
        }
        return this.enclosingMethods.getFirst();
    }

    public List<MethodNode> getEnclosingMethods() {
        return Collections.unmodifiableList(this.enclosingMethods);
    }

    public void pushEnclosingMethodCall(Expression call) {
        if (!(call instanceof MethodCallExpression) && !(call instanceof StaticMethodCallExpression)) {
            throw new IllegalArgumentException("Expression must be a method call or a static method call");
        }
        this.enclosingMethodCalls.addFirst(call);
    }

    public Expression popEnclosingMethodCall() {
        return this.enclosingMethodCalls.removeFirst();
    }

    public Expression getEnclosingMethodCall() {
        if (this.enclosingMethodCalls.isEmpty()) {
            return null;
        }
        return this.enclosingMethodCalls.getFirst();
    }

    public List<Expression> getEnclosingMethodCalls() {
        return Collections.unmodifiableList(this.enclosingMethodCalls);
    }

    public void pushEnclosingSwitchStatement(SwitchStatement switchStatement) {
        this.switchStatements.addFirst(switchStatement);
    }

    public SwitchStatement popEnclosingSwitchStatement() {
        return this.switchStatements.removeFirst();
    }

    public SwitchStatement getEnclosingSwitchStatement() {
        if (this.switchStatements.isEmpty()) {
            return null;
        }
        return this.switchStatements.getFirst();
    }

    public List<SwitchStatement> getEnclosingSwitchStatements() {
        return Collections.unmodifiableList(this.switchStatements);
    }

    public static class EnclosingClosure {
        private final ClosureExpression closureExpression;
        private final List<ClassNode> returnTypes = new LinkedList<ClassNode>();

        public EnclosingClosure(ClosureExpression closureExpression) {
            this.closureExpression = closureExpression;
        }

        public ClosureExpression getClosureExpression() {
            return this.closureExpression;
        }

        public List<ClassNode> getReturnTypes() {
            return this.returnTypes;
        }

        public void addReturnType(ClassNode type) {
            this.returnTypes.add(type);
        }

        public String toString() {
            return "EnclosingClosure{closureExpression=" + this.closureExpression.getText() + ", returnTypes=" + this.returnTypes + "}";
        }
    }
}

