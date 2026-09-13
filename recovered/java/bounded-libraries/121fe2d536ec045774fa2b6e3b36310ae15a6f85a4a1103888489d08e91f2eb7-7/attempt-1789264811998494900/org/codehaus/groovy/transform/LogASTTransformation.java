/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyRuntimeException;
import groovy.transform.CompilationUnitAware;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.apache.groovy.ast.tools.VisibilityUtils;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassCodeExpressionTransformer;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.DynamicVariable;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.TupleExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.classgen.VariableScopeVisitor;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
public class LogASTTransformation
extends AbstractASTTransformation
implements CompilationUnitAware {
    public static final String DEFAULT_CATEGORY_NAME = "##default-category-name##";
    public static final String DEFAULT_ACCESS_MODIFIER = "private";
    private CompilationUnit compilationUnit;

    @Override
    public void setCompilationUnit(CompilationUnit compilationUnit) {
        this.compilationUnit = compilationUnit;
    }

    @Override
    public void visit(ASTNode[] nodes, final SourceUnit sourceUnit) {
        this.init(nodes, sourceUnit);
        AnnotatedNode targetClass = (AnnotatedNode)nodes[1];
        AnnotationNode logAnnotation = (AnnotationNode)nodes[0];
        final LoggingStrategy loggingStrategy = LogASTTransformation.createLoggingStrategy(logAnnotation, sourceUnit.getClassLoader(), this.compilationUnit.getTransformLoader());
        if (loggingStrategy == null) {
            return;
        }
        final String logFieldName = LogASTTransformation.lookupLogFieldName(logAnnotation);
        final String categoryName = LogASTTransformation.lookupCategoryName(logAnnotation);
        final int logFieldModifiers = LogASTTransformation.lookupLogFieldModifiers(targetClass, logAnnotation);
        if (!(targetClass instanceof ClassNode)) {
            throw new GroovyBugError("Class annotation " + logAnnotation.getClassNode().getName() + " annotated no Class, this must not happen.");
        }
        ClassNode classNode = (ClassNode)targetClass;
        ClassCodeExpressionTransformer transformer = new ClassCodeExpressionTransformer(){
            private FieldNode logNode;

            @Override
            protected SourceUnit getSourceUnit() {
                return sourceUnit;
            }

            @Override
            public Expression transform(Expression exp) {
                if (exp == null) {
                    return null;
                }
                if (exp instanceof MethodCallExpression) {
                    return this.transformMethodCallExpression(exp);
                }
                if (exp instanceof ClosureExpression) {
                    return this.transformClosureExpression((ClosureExpression)exp);
                }
                return super.transform(exp);
            }

            @Override
            public void visitClass(ClassNode node) {
                FieldNode logField = node.getField(logFieldName);
                if (logField != null && logField.getOwner().equals(node)) {
                    this.addError("Class annotated with Log annotation cannot have log field declared", logField);
                } else if (logField != null && !Modifier.isPrivate(logField.getModifiers())) {
                    this.addError("Class annotated with Log annotation cannot have log field declared because the field exists in the parent class: " + logField.getOwner().getName(), logField);
                } else if (loggingStrategy instanceof LoggingStrategyV2) {
                    LoggingStrategyV2 loggingStrategyV2 = (LoggingStrategyV2)loggingStrategy;
                    this.logNode = loggingStrategyV2.addLoggerFieldToClass(node, logFieldName, categoryName, logFieldModifiers);
                } else {
                    this.logNode = loggingStrategy.addLoggerFieldToClass(node, logFieldName, categoryName);
                }
                super.visitClass(node);
            }

            private Expression transformClosureExpression(ClosureExpression exp) {
                if (exp.getCode() instanceof BlockStatement) {
                    BlockStatement code = (BlockStatement)exp.getCode();
                    super.visitBlockStatement(code);
                }
                return exp;
            }

            private Expression transformMethodCallExpression(Expression exp) {
                Expression modifiedCall = this.addGuard((MethodCallExpression)exp);
                return modifiedCall == null ? super.transform(exp) : modifiedCall;
            }

            private Expression addGuard(MethodCallExpression mce) {
                if (!(mce.getObjectExpression() instanceof VariableExpression)) {
                    return null;
                }
                VariableExpression variableExpression = (VariableExpression)mce.getObjectExpression();
                if (!variableExpression.getName().equals(logFieldName) || !(variableExpression.getAccessedVariable() instanceof DynamicVariable)) {
                    return null;
                }
                String methodName = mce.getMethodAsString();
                if (methodName == null) {
                    return null;
                }
                if (!loggingStrategy.isLoggingMethod(methodName)) {
                    return null;
                }
                if (this.usesSimpleMethodArgumentsOnly(mce)) {
                    return null;
                }
                variableExpression.setAccessedVariable(this.logNode);
                return loggingStrategy.wrapLoggingMethodCall(variableExpression, methodName, mce);
            }

            private boolean usesSimpleMethodArgumentsOnly(MethodCallExpression mce) {
                Expression arguments = mce.getArguments();
                if (arguments instanceof TupleExpression) {
                    TupleExpression tuple = (TupleExpression)arguments;
                    for (Expression exp : tuple.getExpressions()) {
                        if (this.isSimpleExpression(exp)) continue;
                        return false;
                    }
                    return true;
                }
                return !this.isSimpleExpression(arguments);
            }

            private boolean isSimpleExpression(Expression exp) {
                if (exp instanceof ConstantExpression) {
                    return true;
                }
                return exp instanceof VariableExpression;
            }
        };
        transformer.visitClass(classNode);
        new VariableScopeVisitor(sourceUnit, true).visitClass(classNode);
    }

    private static String lookupLogFieldName(AnnotationNode logAnnotation) {
        Expression member = logAnnotation.getMember("value");
        if (member != null && member.getText() != null) {
            return member.getText();
        }
        return "log";
    }

    private static String lookupCategoryName(AnnotationNode logAnnotation) {
        Expression member = logAnnotation.getMember("category");
        if (member != null && member.getText() != null) {
            return member.getText();
        }
        return DEFAULT_CATEGORY_NAME;
    }

    private static int lookupLogFieldModifiers(AnnotatedNode targetClass, AnnotationNode logAnnotation) {
        int modifiers = VisibilityUtils.getVisibility(logAnnotation, targetClass, ClassNode.class, 2);
        return 0x98 | modifiers;
    }

    private static LoggingStrategy createLoggingStrategy(AnnotationNode logAnnotation, ClassLoader classLoader, ClassLoader xformLoader) {
        Object defaultValue;
        Method annotationMethod;
        Class<?> annotationClass;
        String annotationName = logAnnotation.getClassNode().getName();
        try {
            annotationClass = Class.forName(annotationName, false, xformLoader);
        }
        catch (Throwable t) {
            throw new RuntimeException("Could not resolve class named " + annotationName);
        }
        try {
            annotationMethod = annotationClass.getDeclaredMethod("loggingStrategy", null);
        }
        catch (Throwable t) {
            throw new RuntimeException("Could not find method named loggingStrategy on class named " + annotationName);
        }
        try {
            defaultValue = annotationMethod.getDefaultValue();
        }
        catch (Throwable t) {
            throw new RuntimeException("Could not find default value of method named loggingStrategy on class named " + annotationName);
        }
        if (!LoggingStrategy.class.isAssignableFrom((Class)defaultValue)) {
            throw new RuntimeException("Default loggingStrategy value on class named " + annotationName + " is not a LoggingStrategy");
        }
        try {
            Class strategyClass = (Class)defaultValue;
            if (AbstractLoggingStrategy.class.isAssignableFrom(strategyClass)) {
                return (LoggingStrategy)DefaultGroovyMethods.newInstance(strategyClass, new Object[]{classLoader});
            }
            return (LoggingStrategy)strategyClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception strategyClass) {
            try {
                Class strategyClass2 = (Class)defaultValue;
                if (AbstractLoggingStrategy.class.isAssignableFrom(strategyClass2)) {
                    return (LoggingStrategy)DefaultGroovyMethods.newInstance(strategyClass2, new Object[]{classLoader});
                }
                return (LoggingStrategy)strategyClass2.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            }
            catch (Exception exception) {
                return null;
            }
        }
    }

    public static interface LoggingStrategy {
        public FieldNode addLoggerFieldToClass(ClassNode var1, String var2, String var3);

        public boolean isLoggingMethod(String var1);

        default public String getCategoryName(ClassNode classNode, String categoryName) {
            return categoryName.equals(LogASTTransformation.DEFAULT_CATEGORY_NAME) ? classNode.getName() : categoryName;
        }

        public Expression wrapLoggingMethodCall(Expression var1, String var2, Expression var3);
    }

    public static abstract class AbstractLoggingStrategy
    implements LoggingStrategy {
        protected final GroovyClassLoader loader;

        protected AbstractLoggingStrategy(GroovyClassLoader loader) {
            this.loader = loader;
        }

        protected AbstractLoggingStrategy() {
            this(null);
        }

        protected ClassNode classNode(String name) {
            ClassLoader cl = this.loader != null ? this.loader : this.getClass().getClassLoader();
            try {
                Class<?> c = Class.forName(name, false, cl);
                return ClassHelper.make(c);
            }
            catch (ClassNotFoundException e) {
                throw new GroovyRuntimeException("Unable to load class: " + name, e);
            }
        }
    }

    public static abstract class AbstractLoggingStrategyV2
    extends AbstractLoggingStrategy
    implements LoggingStrategyV2 {
        protected AbstractLoggingStrategyV2(GroovyClassLoader loader) {
            super(loader);
        }

        protected AbstractLoggingStrategyV2() {
            this(null);
        }

        @Override
        public FieldNode addLoggerFieldToClass(ClassNode classNode, String fieldName, String categoryName) {
            throw new UnsupportedOperationException("This logger requires a later version of Groovy");
        }
    }

    public static interface LoggingStrategyV2
    extends LoggingStrategy {
        public FieldNode addLoggerFieldToClass(ClassNode var1, String var2, String var3, int var4);
    }
}

