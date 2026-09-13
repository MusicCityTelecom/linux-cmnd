/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.tailrec;

import groovy.lang.Closure;
import groovy.transform.Memoized;
import groovy.transform.TailRecursive;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
import org.codehaus.groovy.ast.expr.TernaryExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.classgen.ReturnAdder;
import org.codehaus.groovy.classgen.VariableScopeVisitor;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.codehaus.groovy.transform.tailrec.AstHelper;
import org.codehaus.groovy.transform.tailrec.CollectRecursiveCalls;
import org.codehaus.groovy.transform.tailrec.HasRecursiveCalls;
import org.codehaus.groovy.transform.tailrec.InWhileLoopWrapper;
import org.codehaus.groovy.transform.tailrec.RecursivenessTester;
import org.codehaus.groovy.transform.tailrec.ReturnAdderForClosures;
import org.codehaus.groovy.transform.tailrec.ReturnStatementToIterationConverter;
import org.codehaus.groovy.transform.tailrec.StatementReplacer;
import org.codehaus.groovy.transform.tailrec.TernaryToIfStatementConverter;
import org.codehaus.groovy.transform.tailrec.VariableAccessReplacer;

@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
public class TailRecursiveASTTransformation
extends AbstractASTTransformation {
    private static final Class MY_CLASS = TailRecursive.class;
    private static final ClassNode MY_TYPE = new ClassNode(MY_CLASS);
    private static final String MY_TYPE_NAME = "@" + MY_TYPE.getNameWithoutPackage();
    private final HasRecursiveCalls hasRecursiveCalls = new HasRecursiveCalls();
    private final TernaryToIfStatementConverter ternaryToIfStatement = new TernaryToIfStatementConverter();

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        this.init(nodes, source);
        MethodNode method = (MethodNode)nodes[1];
        if (method.isAbstract()) {
            this.addError("Annotation " + TailRecursiveASTTransformation.getMY_TYPE_NAME() + " cannot be used for abstract methods.", method);
            return;
        }
        if (this.hasAnnotation(method, ClassHelper.make(Memoized.class))) {
            ClassNode memoizedClassNode = ClassHelper.make(Memoized.class);
            for (AnnotationNode annotationNode : method.getAnnotations()) {
                if (annotationNode.getClassNode().equals(MY_TYPE)) break;
                if (!annotationNode.getClassNode().equals(memoizedClassNode)) continue;
                this.addError("Annotation " + TailRecursiveASTTransformation.getMY_TYPE_NAME() + " must be placed before annotation @Memoized.", annotationNode);
                return;
            }
        }
        if (!this.hasRecursiveMethodCalls(method)) {
            AnnotationNode annotationNode = method.getAnnotations(ClassHelper.make(TailRecursive.class)).get(0);
            this.addError("No recursive calls detected. You must remove annotation " + TailRecursiveASTTransformation.getMY_TYPE_NAME() + ".", annotationNode);
            return;
        }
        this.transformToIteration(method, source);
        this.ensureAllRecursiveCallsHaveBeenTransformed(method);
    }

    private boolean hasAnnotation(MethodNode methodNode, ClassNode annotation) {
        List<AnnotationNode> annots = methodNode.getAnnotations(annotation);
        return annots != null && annots.size() > 0;
    }

    private void transformToIteration(MethodNode method, SourceUnit source) {
        if (method.isVoidMethod()) {
            this.transformVoidMethodToIteration(method);
        } else {
            this.transformNonVoidMethodToIteration(method, source);
        }
    }

    private void transformVoidMethodToIteration(MethodNode method) {
        this.addError("Void methods are not supported by @TailRecursive yet.", method);
    }

    private void transformNonVoidMethodToIteration(MethodNode method, SourceUnit source) {
        this.addMissingDefaultReturnStatement(method);
        this.replaceReturnsWithTernariesToIfStatements(method);
        this.wrapMethodBodyWithWhileLoop(method);
        Map<String, Map> nameAndTypeMapping = this.name2VariableMappingFor(method);
        this.replaceAllAccessToParams(method, nameAndTypeMapping);
        this.addLocalVariablesForAllParameters(method, nameAndTypeMapping);
        Map<Integer, Map> positionMapping = this.position2VariableMappingFor(method);
        this.replaceAllRecursiveReturnsWithIteration(method, positionMapping);
        this.repairVariableScopes(source, method);
    }

    private void repairVariableScopes(SourceUnit source, MethodNode method) {
        new VariableScopeVisitor(source).visitClass(method.getDeclaringClass());
    }

    private void replaceReturnsWithTernariesToIfStatements(MethodNode method) {
        Closure<Boolean> whenReturnWithTernary = new Closure<Boolean>((Object)this, (Object)this){

            public Boolean doCall(ASTNode node) {
                if (!(node instanceof ReturnStatement)) {
                    return false;
                }
                return ((ReturnStatement)node).getExpression() instanceof TernaryExpression;
            }
        };
        Closure<Statement> replaceWithIfStatement = new Closure<Statement>((Object)this, (Object)this){

            public Statement doCall(ReturnStatement statement) {
                return TailRecursiveASTTransformation.this.ternaryToIfStatement.convert(statement);
            }
        };
        StatementReplacer replacer = new StatementReplacer(whenReturnWithTernary, replaceWithIfStatement);
        replacer.replaceIn(method.getCode());
    }

    private void addLocalVariablesForAllParameters(MethodNode method, Map<String, Map> nameAndTypeMapping) {
        BlockStatement code = (BlockStatement)method.getCode();
        nameAndTypeMapping.forEach((paramName, localNameAndType) -> code.getStatements().add(0, AstHelper.createVariableDefinition((String)localNameAndType.get("name"), (ClassNode)localNameAndType.get("type"), new VariableExpression((String)paramName, (ClassNode)localNameAndType.get("type")))));
    }

    private void replaceAllAccessToParams(MethodNode method, Map<String, Map> nameAndTypeMapping) {
        new VariableAccessReplacer(nameAndTypeMapping).replaceIn(method.getCode());
    }

    public Map<String, Map> name2VariableMappingFor(MethodNode method) {
        LinkedHashMap<String, Map> nameAndTypeMapping = new LinkedHashMap<String, Map>();
        Arrays.stream(method.getParameters()).forEach(param -> {
            String paramName = param.getName();
            ClassNode paramType = param.getType();
            String iterationVariableName = this.iterationVariableName(paramName);
            LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>(2);
            map.put("name", iterationVariableName);
            map.put("type", paramType);
            TailRecursiveASTTransformation.putAt0(nameAndTypeMapping, paramName, map);
        });
        return nameAndTypeMapping;
    }

    public Map<Integer, Map> position2VariableMappingFor(MethodNode method) {
        LinkedHashMap<Integer, Map> positionMapping = new LinkedHashMap<Integer, Map>();
        Parameter[] parameters = method.getParameters();
        int n = parameters.length;
        for (int i = 0; i < n; ++i) {
            Parameter param = parameters[i];
            String paramName = param.getName();
            ClassNode paramType = param.getType();
            String iterationVariableName = this.iterationVariableName(paramName);
            LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>(2);
            map.put("name", iterationVariableName);
            map.put("type", paramType);
            TailRecursiveASTTransformation.putAt0(positionMapping, i, map);
        }
        return positionMapping;
    }

    private String iterationVariableName(String paramName) {
        return "_" + paramName + "_";
    }

    private void replaceAllRecursiveReturnsWithIteration(MethodNode method, Map positionMapping) {
        this.replaceRecursiveReturnsOutsideClosures(method, positionMapping);
        this.replaceRecursiveReturnsInsideClosures(method, positionMapping);
    }

    private void replaceRecursiveReturnsOutsideClosures(final MethodNode method, final Map<Integer, Map<String, Object>> positionMapping) {
        Closure<Boolean> whenRecursiveReturn = new Closure<Boolean>((Object)this, (Object)this){

            public Boolean doCall(Statement statement, boolean inClosure) {
                if (inClosure) {
                    return false;
                }
                if (!(statement instanceof ReturnStatement)) {
                    return false;
                }
                Expression inner = ((ReturnStatement)statement).getExpression();
                if (!(inner instanceof MethodCallExpression) && !(inner instanceof StaticMethodCallExpression)) {
                    return false;
                }
                return TailRecursiveASTTransformation.this.isRecursiveIn(inner, method);
            }
        };
        Closure<Statement> replaceWithContinueBlock = new Closure<Statement>((Object)this, (Object)this){

            public Statement doCall(ReturnStatement statement) {
                return new ReturnStatementToIterationConverter().convert(statement, positionMapping);
            }
        };
        StatementReplacer replacer = new StatementReplacer(whenRecursiveReturn, replaceWithContinueBlock);
        replacer.replaceIn(method.getCode());
    }

    private void replaceRecursiveReturnsInsideClosures(final MethodNode method, final Map<Integer, Map<String, Object>> positionMapping) {
        Closure<Boolean> whenRecursiveReturn = new Closure<Boolean>((Object)this, (Object)this){

            public Boolean doCall(Statement statement, boolean inClosure) {
                if (!inClosure) {
                    return false;
                }
                if (!(statement instanceof ReturnStatement)) {
                    return false;
                }
                Expression inner = ((ReturnStatement)statement).getExpression();
                if (!(inner instanceof MethodCallExpression) && !(inner instanceof StaticMethodCallExpression)) {
                    return false;
                }
                return TailRecursiveASTTransformation.this.isRecursiveIn(inner, method);
            }
        };
        Closure<Statement> replaceWithThrowLoopException = new Closure<Statement>((Object)this, (Object)this){

            public Statement doCall(ReturnStatement statement) {
                return new ReturnStatementToIterationConverter(AstHelper.recurByThrowStatement()).convert(statement, positionMapping);
            }
        };
        StatementReplacer replacer = new StatementReplacer(whenRecursiveReturn, replaceWithThrowLoopException);
        replacer.replaceIn(method.getCode());
    }

    private void wrapMethodBodyWithWhileLoop(MethodNode method) {
        new InWhileLoopWrapper().wrap(method);
    }

    private void addMissingDefaultReturnStatement(MethodNode method) {
        new ReturnAdder().visitMethod(method);
        new ReturnAdderForClosures().visitMethod(method);
    }

    private void ensureAllRecursiveCallsHaveBeenTransformed(MethodNode method) {
        List<Expression> remainingRecursiveCalls = new CollectRecursiveCalls().collect(method);
        for (Expression expression : remainingRecursiveCalls) {
            this.addError("Recursive call could not be transformed by @TailRecursive. Maybe it's not a tail call.", expression);
        }
    }

    private boolean hasRecursiveMethodCalls(MethodNode method) {
        return this.hasRecursiveCalls.test(method);
    }

    private boolean isRecursiveIn(Expression methodCall, MethodNode method) {
        if (methodCall instanceof MethodCallExpression) {
            return new RecursivenessTester().isRecursive(method, (MethodCallExpression)methodCall);
        }
        if (methodCall instanceof StaticMethodCallExpression) {
            return new RecursivenessTester().isRecursive(method, (StaticMethodCallExpression)methodCall);
        }
        return false;
    }

    public static String getMY_TYPE_NAME() {
        return MY_TYPE_NAME;
    }

    private static <K, V, Value extends V> Value putAt0(Map<K, V> propOwner, K key, Value value) {
        propOwner.put(key, value);
        return value;
    }
}

