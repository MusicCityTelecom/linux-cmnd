/*
 * Decompiled with CFR 0.152.
 */
package groovy.util.logging;

import groovy.lang.GroovyClassLoader;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.transform.GroovyASTTransformationClass;
import org.codehaus.groovy.transform.LogASTTransformation;

@Documented
@Retention(value=RetentionPolicy.SOURCE)
@Target(value={ElementType.TYPE})
@GroovyASTTransformationClass(value={"org.codehaus.groovy.transform.LogASTTransformation"})
public @interface PlatformLog {
    public String value() default "log";

    public String category() default "##default-category-name##";

    public String visibilityId() default "<DummyUndefinedMarkerString-DoNotUse>";

    public Class<? extends LogASTTransformation.LoggingStrategy> loggingStrategy() default JavaUtilLoggingStrategy.class;

    public static class JavaUtilLoggingStrategy
    extends LogASTTransformation.AbstractLoggingStrategyV2 {
        private static final ClassNode LOGGER_CLASSNODE = ClassHelper.make(System.Logger.class);
        private static final ClassNode LOGGER_FINDER_CLASSNODE = ClassHelper.make(System.LoggerFinder.class);

        protected JavaUtilLoggingStrategy(GroovyClassLoader loader) {
            super(loader);
        }

        @Override
        public FieldNode addLoggerFieldToClass(ClassNode classNode, String logFieldName, String categoryName, int fieldModifiers) {
            MethodCallExpression module = GeneralUtils.callX(GeneralUtils.classX(classNode), "getModule");
            MethodCallExpression loggerFinder = GeneralUtils.callX(GeneralUtils.classX(LOGGER_FINDER_CLASSNODE), "getLoggerFinder");
            MethodCallExpression initialValue = GeneralUtils.callX((Expression)loggerFinder, "getLogger", (Expression)GeneralUtils.args(GeneralUtils.constX(this.getCategoryName(classNode, categoryName)), module));
            return classNode.addField(logFieldName, fieldModifiers, LOGGER_CLASSNODE, initialValue);
        }

        @Override
        public boolean isLoggingMethod(String methodName) {
            return false;
        }

        @Override
        public Expression wrapLoggingMethodCall(Expression logVariable, String methodName, Expression originalExpression) {
            return originalExpression;
        }
    }
}

