/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.beans.factory.SmartInitializingSingleton
 *  org.springframework.cache.CacheManager
 *  org.springframework.cache.interceptor.CacheAspectSupport
 *  org.springframework.cache.interceptor.CacheErrorHandler
 *  org.springframework.cache.interceptor.CacheEvictOperation
 *  org.springframework.cache.interceptor.CacheEvictOperation$Builder
 *  org.springframework.cache.interceptor.CacheOperation
 *  org.springframework.cache.interceptor.CacheOperationInvoker
 *  org.springframework.cache.interceptor.CacheOperationSource
 *  org.springframework.cache.interceptor.CachePutOperation
 *  org.springframework.cache.interceptor.CachePutOperation$Builder
 *  org.springframework.cache.interceptor.CacheResolver
 *  org.springframework.cache.interceptor.CacheableOperation
 *  org.springframework.cache.interceptor.CacheableOperation$Builder
 *  org.springframework.expression.EvaluationContext
 *  org.springframework.expression.Expression
 *  org.springframework.expression.spel.support.StandardEvaluationContext
 *  org.springframework.lang.Nullable
 *  org.springframework.messaging.Message
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ObjectUtils
 *  org.springframework.util.ReflectionUtils
 */
package org.springframework.integration.handler.advice;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheAspectSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheEvictOperation;
import org.springframework.cache.interceptor.CacheOperation;
import org.springframework.cache.interceptor.CacheOperationInvoker;
import org.springframework.cache.interceptor.CacheOperationSource;
import org.springframework.cache.interceptor.CachePutOperation;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.CacheableOperation;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.integration.expression.ExpressionUtils;
import org.springframework.integration.expression.FunctionExpression;
import org.springframework.integration.handler.advice.AbstractRequestHandlerAdvice;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

public class CacheRequestHandlerAdvice
extends AbstractRequestHandlerAdvice
implements SmartInitializingSingleton {
    private static final Method HANDLE_REQUEST_METHOD;
    private final IntegrationCacheAspect delegate = new IntegrationCacheAspect();
    private final String[] cacheNames;
    private final List<CacheOperation> cacheOperations = new ArrayList<CacheOperation>();
    private Expression keyExpression = new FunctionExpression<Message>(Message::getPayload);

    public CacheRequestHandlerAdvice(String ... cacheNamesArg) {
        this.cacheNames = cacheNamesArg != null ? Arrays.copyOf(cacheNamesArg, cacheNamesArg.length) : null;
        CacheableOperation.Builder builder = new CacheableOperation.Builder();
        builder.setName(this.toString());
        this.cacheOperations.add((CacheOperation)builder.build());
    }

    public void setCacheOperations(CacheOperation ... cacheOperations) {
        Assert.notEmpty((Object[])cacheOperations, (String)"'cacheOperations' must not be empty");
        Assert.notNull((Object)cacheOperations, (String)"'cacheOperations' must not be null");
        this.cacheOperations.clear();
        this.cacheOperations.addAll(Arrays.asList(cacheOperations));
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.delegate.setCacheManager(cacheManager);
    }

    public void setCacheResolver(CacheResolver cacheResolver) {
        this.delegate.setCacheResolver(cacheResolver);
    }

    public void setErrorHandler(CacheErrorHandler errorHandler) {
        Assert.notNull((Object)errorHandler, (String)"'errorHandler' must not be null");
        this.delegate.setErrorHandler(errorHandler);
    }

    public void setKeyExpressionString(String keyExpression) {
        Assert.hasText((String)keyExpression, (String)"'keyExpression' must not be empty");
        this.setKeyExpression(EXPRESSION_PARSER.parseExpression(keyExpression));
    }

    public void setKeyFunction(Function<Message<?>, ?> keyFunction) {
        Assert.notNull(keyFunction, (String)"'keyFunction' must not be null");
        this.setKeyExpression(new FunctionExpression(keyFunction));
    }

    public void setKeyExpression(Expression keyExpression) {
        Assert.notNull((Object)keyExpression, (String)"'keyExpression' must not be null");
        this.keyExpression = keyExpression;
    }

    public void afterSingletonsInstantiated() {
        this.delegate.afterSingletonsInstantiated();
    }

    @Override
    protected void onInit() {
        List<Object> cacheOperationsToUse = !ObjectUtils.isEmpty((Object[])this.cacheNames) ? this.cacheOperations.stream().filter(operation -> ObjectUtils.isEmpty((Object)operation.getCacheNames())).map(operation -> {
            CachePutOperation.Builder builder;
            if (operation instanceof CacheableOperation) {
                CacheableOperation cacheableOperation = (CacheableOperation)operation;
                CacheableOperation.Builder cacheableBuilder = new CacheableOperation.Builder();
                cacheableBuilder.setSync(cacheableOperation.isSync());
                String unless = cacheableOperation.getUnless();
                if (unless != null) {
                    cacheableBuilder.setUnless(unless);
                }
                builder = cacheableBuilder;
            } else if (operation instanceof CacheEvictOperation) {
                CacheEvictOperation.Builder cacheEvictBuilder = new CacheEvictOperation.Builder();
                CacheEvictOperation cacheEvictOperation = (CacheEvictOperation)operation;
                cacheEvictBuilder.setBeforeInvocation(cacheEvictOperation.isBeforeInvocation());
                cacheEvictBuilder.setCacheWide(cacheEvictOperation.isCacheWide());
                builder = cacheEvictBuilder;
            } else {
                CachePutOperation cachePutOperation = (CachePutOperation)operation;
                CachePutOperation.Builder cachePutBuilder = new CachePutOperation.Builder();
                String unless = cachePutOperation.getUnless();
                if (unless != null) {
                    cachePutBuilder.setUnless(unless);
                }
                builder = cachePutBuilder;
            }
            builder.setName(operation.getName());
            builder.setCacheManager(operation.getCacheManager());
            builder.setCacheNames(this.cacheNames);
            builder.setCacheResolver(operation.getCacheResolver());
            builder.setCondition(operation.getCondition());
            builder.setKey(operation.getKey());
            builder.setKeyGenerator(operation.getKeyGenerator());
            return builder.build();
        }).collect(Collectors.toList()) : this.cacheOperations;
        this.delegate.setBeanFactory(this.getBeanFactory());
        StandardEvaluationContext evaluationContext = ExpressionUtils.createStandardEvaluationContext(this.getBeanFactory());
        this.delegate.setKeyGenerator((arg_0, arg_1, arg_2) -> this.lambda$onInit$2((EvaluationContext)evaluationContext, arg_0, arg_1, arg_2));
        this.delegate.setCacheOperationSources(new CacheOperationSource[]{(method, targetClass) -> cacheOperationsToUse});
        this.delegate.afterPropertiesSet();
    }

    @Override
    @Nullable
    protected Object doInvoke(AbstractRequestHandlerAdvice.ExecutionCallback callback, Object target, Message<?> message) {
        CacheOperationInvoker operationInvoker = () -> {
            Object result = callback.execute();
            if (result instanceof AbstractIntegrationMessageBuilder) {
                return ((AbstractIntegrationMessageBuilder)result).build();
            }
            return result;
        };
        return this.delegate.invoke(operationInvoker, target, message);
    }

    private /* synthetic */ Object lambda$onInit$2(EvaluationContext evaluationContext, Object target, Method method, Object[] params) {
        return this.keyExpression.getValue(evaluationContext, params[0]);
    }

    static {
        block5: {
            block4: {
                Class requestHandlerClass = null;
                try {
                    requestHandlerClass = ClassUtils.forName((String)"org.springframework.integration.handler.AbstractReplyProducingMessageHandler.RequestHandler", null);
                    if (requestHandlerClass == null) break block4;
                }
                catch (ClassNotFoundException ex) {
                    try {
                        throw new IllegalStateException(ex);
                    }
                    catch (Throwable throwable) {
                        HANDLE_REQUEST_METHOD = requestHandlerClass != null ? ReflectionUtils.findMethod((Class)requestHandlerClass, (String)"handleRequestMessage", (Class[])new Class[]{Message.class}) : null;
                        throw throwable;
                    }
                }
                HANDLE_REQUEST_METHOD = ReflectionUtils.findMethod((Class)requestHandlerClass, (String)"handleRequestMessage", (Class[])new Class[]{Message.class});
                break block5;
            }
            HANDLE_REQUEST_METHOD = null;
        }
    }

    private static class IntegrationCacheAspect
    extends CacheAspectSupport {
        IntegrationCacheAspect() {
        }

        @Nullable
        Object invoke(CacheOperationInvoker invoker, Object target, Message<?> message) {
            return super.execute(invoker, target, HANDLE_REQUEST_METHOD, new Object[]{message});
        }
    }
}

