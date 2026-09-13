/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.intercept.MethodInterceptor
 *  org.aopalliance.intercept.MethodInvocation
 *  org.springframework.aop.IntroductionInterceptor
 *  org.springframework.beans.BeansException
 *  org.springframework.beans.factory.BeanFactory
 *  org.springframework.beans.factory.BeanFactoryAware
 *  org.springframework.beans.factory.config.ConfigurableBeanFactory
 *  org.springframework.context.expression.BeanFactoryResolver
 *  org.springframework.core.annotation.AnnotatedElementUtils
 *  org.springframework.core.annotation.AnnotationAwareOrderComparator
 *  org.springframework.core.annotation.AnnotationUtils
 *  org.springframework.expression.BeanResolver
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.ParserContext
 *  org.springframework.expression.common.TemplateParserContext
 *  org.springframework.expression.spel.standard.SpelExpressionParser
 *  org.springframework.expression.spel.support.StandardEvaluationContext
 *  org.springframework.util.ConcurrentReferenceHashMap
 *  org.springframework.util.ReflectionUtils
 *  org.springframework.util.ReflectionUtils$MethodCallback
 *  org.springframework.util.StringUtils
 */
package org.springframework.retry.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.naming.OperationNotSupportedException;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.IntroductionInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.RecoverAnnotationRecoveryHandler;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.BackOffPolicyBuilder;
import org.springframework.retry.backoff.NoBackOffPolicy;
import org.springframework.retry.backoff.Sleeper;
import org.springframework.retry.interceptor.FixedKeyGenerator;
import org.springframework.retry.interceptor.MethodArgumentsKeyGenerator;
import org.springframework.retry.interceptor.MethodInvocationRecoverer;
import org.springframework.retry.interceptor.NewMethodArgumentsIdentifier;
import org.springframework.retry.interceptor.RetryInterceptorBuilder;
import org.springframework.retry.policy.CircuitBreakerRetryPolicy;
import org.springframework.retry.policy.ExpressionRetryPolicy;
import org.springframework.retry.policy.MapRetryContextCache;
import org.springframework.retry.policy.RetryContextCache;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

public class AnnotationAwareRetryOperationsInterceptor
implements IntroductionInterceptor,
BeanFactoryAware {
    private static final TemplateParserContext PARSER_CONTEXT = new TemplateParserContext();
    private static final SpelExpressionParser PARSER = new SpelExpressionParser();
    private static final MethodInterceptor NULL_INTERCEPTOR = new MethodInterceptor(){

        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            throw new OperationNotSupportedException("Not supported");
        }
    };
    private final StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
    private final ConcurrentReferenceHashMap<Object, ConcurrentMap<Method, MethodInterceptor>> delegates = new ConcurrentReferenceHashMap();
    private RetryContextCache retryContextCache = new MapRetryContextCache();
    private MethodArgumentsKeyGenerator methodArgumentsKeyGenerator;
    private NewMethodArgumentsIdentifier newMethodArgumentsIdentifier;
    private Sleeper sleeper;
    private BeanFactory beanFactory;
    private RetryListener[] globalListeners;

    public void setSleeper(Sleeper sleeper) {
        this.sleeper = sleeper;
    }

    public void setRetryContextCache(RetryContextCache retryContextCache) {
        this.retryContextCache = retryContextCache;
    }

    public void setKeyGenerator(MethodArgumentsKeyGenerator methodArgumentsKeyGenerator) {
        this.methodArgumentsKeyGenerator = methodArgumentsKeyGenerator;
    }

    public void setNewItemIdentifier(NewMethodArgumentsIdentifier newMethodArgumentsIdentifier) {
        this.newMethodArgumentsIdentifier = newMethodArgumentsIdentifier;
    }

    public void setListeners(Collection<RetryListener> globalListeners) {
        ArrayList<RetryListener> retryListeners = new ArrayList<RetryListener>(globalListeners);
        AnnotationAwareOrderComparator.sort(retryListeners);
        this.globalListeners = retryListeners.toArray(new RetryListener[0]);
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
        this.evaluationContext.setBeanResolver((BeanResolver)new BeanFactoryResolver(beanFactory));
    }

    public boolean implementsInterface(Class<?> intf) {
        return org.springframework.retry.interceptor.Retryable.class.isAssignableFrom(intf);
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        MethodInterceptor delegate = this.getDelegate(invocation.getThis(), invocation.getMethod());
        if (delegate != null) {
            return delegate.invoke(invocation);
        }
        return invocation.proceed();
    }

    private MethodInterceptor getDelegate(Object target, Method method) {
        MethodInterceptor delegate;
        ConcurrentHashMap<Method, MethodInterceptor> cachedMethods = (ConcurrentHashMap<Method, MethodInterceptor>)this.delegates.get(target);
        if (cachedMethods == null) {
            cachedMethods = new ConcurrentHashMap<Method, MethodInterceptor>();
        }
        if ((delegate = (MethodInterceptor)cachedMethods.get(method)) == null) {
            MethodInterceptor interceptor = NULL_INTERCEPTOR;
            Retryable retryable = (Retryable)AnnotatedElementUtils.findMergedAnnotation((AnnotatedElement)method, Retryable.class);
            if (retryable == null) {
                retryable = (Retryable)AnnotatedElementUtils.findMergedAnnotation(method.getDeclaringClass(), Retryable.class);
            }
            if (retryable == null) {
                retryable = this.findAnnotationOnTarget(target, method, Retryable.class);
            }
            if (retryable != null) {
                interceptor = StringUtils.hasText((String)retryable.interceptor()) ? (MethodInterceptor)this.beanFactory.getBean(retryable.interceptor(), MethodInterceptor.class) : (retryable.stateful() ? this.getStatefulInterceptor(target, method, retryable) : this.getStatelessInterceptor(target, method, retryable));
            }
            cachedMethods.putIfAbsent(method, interceptor);
            delegate = (MethodInterceptor)cachedMethods.get(method);
        }
        this.delegates.putIfAbsent(target, cachedMethods);
        return delegate == NULL_INTERCEPTOR ? null : delegate;
    }

    private <A extends Annotation> A findAnnotationOnTarget(Object target, Method method, Class<A> annotation) {
        try {
            Method targetMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());
            Annotation retryable = AnnotatedElementUtils.findMergedAnnotation((AnnotatedElement)targetMethod, annotation);
            if (retryable == null) {
                retryable = AnnotatedElementUtils.findMergedAnnotation(targetMethod.getDeclaringClass(), annotation);
            }
            return (A)retryable;
        }
        catch (Exception e) {
            return null;
        }
    }

    private MethodInterceptor getStatelessInterceptor(Object target, Method method, Retryable retryable) {
        RetryTemplate template = this.createTemplate(retryable.listeners());
        template.setRetryPolicy(this.getRetryPolicy(retryable));
        template.setBackOffPolicy(this.getBackoffPolicy(retryable.backoff()));
        return RetryInterceptorBuilder.stateless().retryOperations(template).label(retryable.label()).recoverer(this.getRecoverer(target, method)).build();
    }

    private MethodInterceptor getStatefulInterceptor(Object target, Method method, Retryable retryable) {
        RetryTemplate template = this.createTemplate(retryable.listeners());
        template.setRetryContextCache(this.retryContextCache);
        CircuitBreaker circuit = (CircuitBreaker)AnnotatedElementUtils.findMergedAnnotation((AnnotatedElement)method, CircuitBreaker.class);
        if (circuit == null) {
            circuit = this.findAnnotationOnTarget(target, method, CircuitBreaker.class);
        }
        if (circuit != null) {
            RetryPolicy policy = this.getRetryPolicy(circuit);
            CircuitBreakerRetryPolicy breaker = new CircuitBreakerRetryPolicy(policy);
            breaker.setOpenTimeout(this.getOpenTimeout(circuit));
            breaker.setResetTimeout(this.getResetTimeout(circuit));
            template.setRetryPolicy(breaker);
            template.setBackOffPolicy(new NoBackOffPolicy());
            String label = circuit.label();
            if (!StringUtils.hasText((String)label)) {
                label = method.toGenericString();
            }
            return RetryInterceptorBuilder.circuitBreaker().keyGenerator(new FixedKeyGenerator("circuit")).retryOperations(template).recoverer((MethodInvocationRecoverer)this.getRecoverer(target, method)).label(label).build();
        }
        RetryPolicy policy = this.getRetryPolicy(retryable);
        template.setRetryPolicy(policy);
        template.setBackOffPolicy(this.getBackoffPolicy(retryable.backoff()));
        String label = retryable.label();
        return RetryInterceptorBuilder.stateful().keyGenerator(this.methodArgumentsKeyGenerator).newMethodArgumentsIdentifier(this.newMethodArgumentsIdentifier).retryOperations(template).label(label).recoverer(this.getRecoverer(target, method)).build();
    }

    private long getOpenTimeout(CircuitBreaker circuit) {
        Long value;
        if (StringUtils.hasText((String)circuit.openTimeoutExpression()) && (value = (Long)PARSER.parseExpression(this.resolve(circuit.openTimeoutExpression()), (ParserContext)PARSER_CONTEXT).getValue(Long.class)) != null) {
            return value;
        }
        return circuit.openTimeout();
    }

    private long getResetTimeout(CircuitBreaker circuit) {
        Long value;
        if (StringUtils.hasText((String)circuit.resetTimeoutExpression()) && (value = (Long)PARSER.parseExpression(this.resolve(circuit.resetTimeoutExpression()), (ParserContext)PARSER_CONTEXT).getValue(Long.class)) != null) {
            return value;
        }
        return circuit.resetTimeout();
    }

    private RetryTemplate createTemplate(String[] listenersBeanNames) {
        RetryTemplate template = new RetryTemplate();
        if (listenersBeanNames.length > 0) {
            template.setListeners(this.getListenersBeans(listenersBeanNames));
        } else if (this.globalListeners != null) {
            template.setListeners(this.globalListeners);
        }
        return template;
    }

    private RetryListener[] getListenersBeans(String[] listenersBeanNames) {
        RetryListener[] listeners = new RetryListener[listenersBeanNames.length];
        for (int i = 0; i < listeners.length; ++i) {
            listeners[i] = (RetryListener)this.beanFactory.getBean(listenersBeanNames[i], RetryListener.class);
        }
        return listeners;
    }

    private MethodInvocationRecoverer<?> getRecoverer(Object target, Method method) {
        if (target instanceof MethodInvocationRecoverer) {
            return (MethodInvocationRecoverer)target;
        }
        final AtomicBoolean foundRecoverable = new AtomicBoolean(false);
        ReflectionUtils.doWithMethods(target.getClass(), (ReflectionUtils.MethodCallback)new ReflectionUtils.MethodCallback(){

            public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
                if (AnnotatedElementUtils.findMergedAnnotation((AnnotatedElement)method, Recover.class) != null) {
                    foundRecoverable.set(true);
                }
            }
        });
        if (!foundRecoverable.get()) {
            return null;
        }
        return new RecoverAnnotationRecoveryHandler(target, method);
    }

    private RetryPolicy getRetryPolicy(Annotation retryable) {
        boolean retryNotExcluded;
        Map attrs = AnnotationUtils.getAnnotationAttributes((Annotation)retryable);
        Class[] includes = (Class[])attrs.get("value");
        String exceptionExpression = (String)attrs.get("exceptionExpression");
        boolean hasExpression = StringUtils.hasText((String)exceptionExpression);
        if (includes.length == 0) {
            Class[] value;
            includes = value = (Class[])attrs.get("include");
        }
        Class[] excludes = (Class[])attrs.get("exclude");
        Integer maxAttempts = (Integer)attrs.get("maxAttempts");
        String maxAttemptsExpression = (String)attrs.get("maxAttemptsExpression");
        if (StringUtils.hasText((String)maxAttemptsExpression)) {
            maxAttempts = ExpressionRetryPolicy.isTemplate(maxAttemptsExpression) ? (Integer)PARSER.parseExpression(this.resolve(maxAttemptsExpression), (ParserContext)PARSER_CONTEXT).getValue((EvaluationContext)this.evaluationContext, Integer.class) : (Integer)PARSER.parseExpression(this.resolve(maxAttemptsExpression)).getValue((EvaluationContext)this.evaluationContext, Integer.class);
        }
        if (includes.length == 0 && excludes.length == 0) {
            SimpleRetryPolicy simple = hasExpression ? new ExpressionRetryPolicy(this.resolve(exceptionExpression)).withBeanFactory(this.beanFactory) : new SimpleRetryPolicy();
            simple.setMaxAttempts(maxAttempts);
            return simple;
        }
        HashMap<Class<? extends Throwable>, Boolean> policyMap = new HashMap<Class<? extends Throwable>, Boolean>();
        for (Class type : includes) {
            policyMap.put(type, true);
        }
        for (Class type : excludes) {
            policyMap.put(type, false);
        }
        boolean bl = retryNotExcluded = includes.length == 0;
        if (hasExpression) {
            return new ExpressionRetryPolicy(maxAttempts, policyMap, true, exceptionExpression, retryNotExcluded).withBeanFactory(this.beanFactory);
        }
        return new SimpleRetryPolicy(maxAttempts, policyMap, true, retryNotExcluded);
    }

    private BackOffPolicy getBackoffPolicy(Backoff backoff) {
        Map attrs = AnnotationUtils.getAnnotationAttributes((Annotation)backoff);
        long min = backoff.delay() == 0L ? backoff.value() : backoff.delay();
        String delayExpression = (String)attrs.get("delayExpression");
        if (StringUtils.hasText((String)delayExpression)) {
            min = ExpressionRetryPolicy.isTemplate(delayExpression) ? ((Long)PARSER.parseExpression(this.resolve(delayExpression), (ParserContext)PARSER_CONTEXT).getValue((EvaluationContext)this.evaluationContext, Long.class)).longValue() : ((Long)PARSER.parseExpression(this.resolve(delayExpression)).getValue((EvaluationContext)this.evaluationContext, Long.class)).longValue();
        }
        long max = backoff.maxDelay();
        String maxDelayExpression = (String)attrs.get("maxDelayExpression");
        if (StringUtils.hasText((String)maxDelayExpression)) {
            max = ExpressionRetryPolicy.isTemplate(maxDelayExpression) ? ((Long)PARSER.parseExpression(this.resolve(maxDelayExpression), (ParserContext)PARSER_CONTEXT).getValue((EvaluationContext)this.evaluationContext, Long.class)).longValue() : ((Long)PARSER.parseExpression(this.resolve(maxDelayExpression)).getValue((EvaluationContext)this.evaluationContext, Long.class)).longValue();
        }
        double multiplier = backoff.multiplier();
        String multiplierExpression = (String)attrs.get("multiplierExpression");
        if (StringUtils.hasText((String)multiplierExpression)) {
            multiplier = ExpressionRetryPolicy.isTemplate(multiplierExpression) ? ((Double)PARSER.parseExpression(this.resolve(multiplierExpression), (ParserContext)PARSER_CONTEXT).getValue((EvaluationContext)this.evaluationContext, Double.class)).doubleValue() : ((Double)PARSER.parseExpression(this.resolve(multiplierExpression)).getValue((EvaluationContext)this.evaluationContext, Double.class)).doubleValue();
        }
        boolean isRandom = false;
        if (multiplier > 0.0) {
            isRandom = backoff.random();
            String randomExpression = (String)attrs.get("randomExpression");
            if (StringUtils.hasText((String)randomExpression)) {
                isRandom = ExpressionRetryPolicy.isTemplate(randomExpression) ? ((Boolean)PARSER.parseExpression(this.resolve(randomExpression), (ParserContext)PARSER_CONTEXT).getValue((EvaluationContext)this.evaluationContext, Boolean.class)).booleanValue() : ((Boolean)PARSER.parseExpression(this.resolve(randomExpression)).getValue((EvaluationContext)this.evaluationContext, Boolean.class)).booleanValue();
            }
        }
        return BackOffPolicyBuilder.newBuilder().delay(min).maxDelay(max).multiplier(multiplier).random(isRandom).sleeper(this.sleeper).build();
    }

    private String resolve(String value) {
        if (this.beanFactory != null && this.beanFactory instanceof ConfigurableBeanFactory) {
            return ((ConfigurableBeanFactory)this.beanFactory).resolveEmbeddedValue(value);
        }
        return value;
    }
}

