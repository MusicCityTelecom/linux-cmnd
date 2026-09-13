/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.core.convert.TypeDescriptor
 *  org.springframework.expression.AccessException
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.EvaluationException
 *  org.springframework.expression.Expression
 *  org.springframework.expression.MethodExecutor
 *  org.springframework.expression.MethodFilter
 *  org.springframework.expression.spel.support.ReflectiveMethodResolver
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.util.CollectionUtils
 */
package org.springframework.integration.handler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.AccessException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.MethodExecutor;
import org.springframework.expression.MethodFilter;
import org.springframework.expression.spel.support.ReflectiveMethodResolver;
import org.springframework.integration.IntegrationPattern;
import org.springframework.integration.IntegrationPatternType;
import org.springframework.integration.handler.AbstractMessageProcessor;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.util.CollectionUtils;

public class ExpressionCommandMessageProcessor
extends AbstractMessageProcessor<Object>
implements IntegrationPattern {
    @Nullable
    private final MethodFilter methodFilter;

    public ExpressionCommandMessageProcessor() {
        this.methodFilter = null;
    }

    public ExpressionCommandMessageProcessor(@Nullable MethodFilter methodFilter) {
        this(methodFilter, null);
    }

    public ExpressionCommandMessageProcessor(@Nullable MethodFilter methodFilter, @Nullable BeanFactory beanFactory) {
        this.methodFilter = methodFilter;
        if (beanFactory != null) {
            this.setBeanFactory(beanFactory);
        }
    }

    @Override
    public final void setBeanFactory(BeanFactory beanFactory) {
        super.setBeanFactory(beanFactory);
        if (this.methodFilter != null) {
            ExpressionCommandMethodResolver methodResolver = new ExpressionCommandMethodResolver(this.methodFilter);
            this.getEvaluationContext().setMethodResolvers(Collections.singletonList(methodResolver));
        }
    }

    @Override
    public IntegrationPatternType getIntegrationPatternType() {
        return IntegrationPatternType.control_bus;
    }

    @Override
    @Nullable
    public Object processMessage(Message<?> message) {
        Object expression = message.getPayload();
        if (expression instanceof Expression) {
            return this.evaluateExpression((Expression)expression, message);
        }
        if (expression instanceof String) {
            return this.evaluateExpression((String)expression, message);
        }
        throw new IllegalArgumentException("Message payload must be an Expression instance or an expression String.");
    }

    private static final class ExpressionCommandMethodResolver
    extends ReflectiveMethodResolver {
        private final MethodFilter methodFilter;

        ExpressionCommandMethodResolver(MethodFilter methodFilter) {
            this.methodFilter = methodFilter;
        }

        public MethodExecutor resolve(EvaluationContext context, Object targetObject, String name, List<TypeDescriptor> argumentTypes) throws AccessException {
            this.validateMethod(targetObject, name, !CollectionUtils.isEmpty(argumentTypes) ? argumentTypes.size() : 0);
            return super.resolve(context, targetObject, name, argumentTypes);
        }

        private void validateMethod(Object targetObject, String name, int argumentCount) {
            Class<?> type = targetObject instanceof Class ? (Class<?>)targetObject : targetObject.getClass();
            Method[] methods = type.getMethods();
            ArrayList<Method> candidates = new ArrayList<Method>();
            for (Method method : methods) {
                if (!method.getName().equals(name) || method.getParameterTypes().length != argumentCount) continue;
                candidates.add(method);
            }
            List supportedMethods = this.methodFilter.filter(candidates);
            if (supportedMethods.size() == 0) {
                String methodDescription = candidates.size() > 0 ? ((Method)candidates.get(0)).toString() : name;
                throw new EvaluationException("The method '" + methodDescription + "' is not supported by this command processor. If using the Control Bus, consider adding @ManagedOperation or @ManagedAttribute.");
            }
        }
    }
}

