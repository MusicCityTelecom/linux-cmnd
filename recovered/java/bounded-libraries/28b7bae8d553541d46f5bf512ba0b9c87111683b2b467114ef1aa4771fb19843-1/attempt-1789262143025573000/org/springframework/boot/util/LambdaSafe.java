/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.ResolvableType
 *  org.springframework.util.Assert
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ReflectionUtils
 */
package org.springframework.boot.util;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.ResolvableType;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

public final class LambdaSafe {
    private static final Method CLASS_GET_MODULE = ReflectionUtils.findMethod(Class.class, (String)"getModule");
    private static final Method MODULE_GET_NAME = CLASS_GET_MODULE != null ? ReflectionUtils.findMethod(CLASS_GET_MODULE.getReturnType(), (String)"getName") : null;

    private LambdaSafe() {
    }

    public static <C, A> Callback<C, A> callback(Class<C> callbackType, C callbackInstance, A argument, Object ... additionalArguments) {
        Assert.notNull(callbackType, (String)"CallbackType must not be null");
        Assert.notNull(callbackInstance, (String)"CallbackInstance must not be null");
        return new Callback(callbackType, callbackInstance, argument, additionalArguments);
    }

    public static <C, A> Callbacks<C, A> callbacks(Class<C> callbackType, Collection<? extends C> callbackInstances, A argument, Object ... additionalArguments) {
        Assert.notNull(callbackType, (String)"CallbackType must not be null");
        Assert.notNull(callbackInstances, (String)"CallbackInstances must not be null");
        return new Callbacks(callbackType, callbackInstances, argument, additionalArguments);
    }

    public static final class InvocationResult<R> {
        private static final InvocationResult<?> NONE = new InvocationResult<Object>(null);
        private final R value;

        private InvocationResult(R value) {
            this.value = value;
        }

        public boolean hasResult() {
            return this != NONE;
        }

        public R get() {
            return this.value;
        }

        public R get(R fallback) {
            return this != NONE ? this.value : fallback;
        }

        public static <R> InvocationResult<R> of(R value) {
            return new InvocationResult<R>(value);
        }

        public static <R> InvocationResult<R> noResult() {
            return NONE;
        }
    }

    private static class GenericTypeFilter<C, A>
    implements Filter<C, A> {
        private GenericTypeFilter() {
        }

        @Override
        public boolean match(Class<C> callbackType, C callbackInstance, A argument, Object[] additionalArguments) {
            ResolvableType type = ResolvableType.forClass(callbackType, callbackInstance.getClass());
            if (type.getGenerics().length == 1 && type.resolveGeneric(new int[0]) != null) {
                return type.resolveGeneric(new int[0]).isInstance(argument);
            }
            return true;
        }
    }

    @FunctionalInterface
    static interface Filter<C, A> {
        public boolean match(Class<C> var1, C var2, A var3, Object[] var4);

        public static <C, A> Filter<C, A> allowAll() {
            return (callbackType, callbackInstance, argument, additionalArguments) -> true;
        }
    }

    public static final class Callbacks<C, A>
    extends LambdaSafeCallback<C, A, Callbacks<C, A>> {
        private final Collection<? extends C> callbackInstances;

        private Callbacks(Class<C> callbackType, Collection<? extends C> callbackInstances, A argument, Object[] additionalArguments) {
            super(callbackType, argument, additionalArguments);
            this.callbackInstances = callbackInstances;
        }

        public void invoke(Consumer<C> invoker) {
            this.callbackInstances.forEach(callbackInstance -> this.invoke(callbackInstance, () -> {
                invoker.accept(callbackInstance);
                return null;
            }));
        }

        public <R> Stream<R> invokeAnd(Function<C, R> invoker) {
            Function<Object, InvocationResult> mapper = callbackInstance -> this.invoke(callbackInstance, () -> invoker.apply(callbackInstance));
            return this.callbackInstances.stream().map(mapper).filter(InvocationResult::hasResult).map(InvocationResult::get);
        }
    }

    public static final class Callback<C, A>
    extends LambdaSafeCallback<C, A, Callback<C, A>> {
        private final C callbackInstance;

        private Callback(Class<C> callbackType, C callbackInstance, A argument, Object[] additionalArguments) {
            super(callbackType, argument, additionalArguments);
            this.callbackInstance = callbackInstance;
        }

        public void invoke(Consumer<C> invoker) {
            this.invoke(this.callbackInstance, () -> {
                invoker.accept(this.callbackInstance);
                return null;
            });
        }

        public <R> InvocationResult<R> invokeAnd(Function<C, R> invoker) {
            return this.invoke(this.callbackInstance, () -> invoker.apply(this.callbackInstance));
        }
    }

    protected static abstract class LambdaSafeCallback<C, A, SELF extends LambdaSafeCallback<C, A, SELF>> {
        private final Class<C> callbackType;
        private final A argument;
        private final Object[] additionalArguments;
        private Log logger;
        private Filter<C, A> filter = new GenericTypeFilter();

        LambdaSafeCallback(Class<C> callbackType, A argument, Object[] additionalArguments) {
            this.callbackType = callbackType;
            this.argument = argument;
            this.additionalArguments = additionalArguments;
            this.logger = LogFactory.getLog(callbackType);
        }

        public SELF withLogger(Class<?> loggerSource) {
            return this.withLogger(LogFactory.getLog(loggerSource));
        }

        public SELF withLogger(Log logger) {
            Assert.notNull((Object)logger, (String)"Logger must not be null");
            this.logger = logger;
            return this.self();
        }

        SELF withFilter(Filter<C, A> filter) {
            Assert.notNull(filter, (String)"Filter must not be null");
            this.filter = filter;
            return this.self();
        }

        protected final <R> InvocationResult<R> invoke(C callbackInstance, Supplier<R> supplier) {
            if (this.filter.match(this.callbackType, callbackInstance, this.argument, this.additionalArguments)) {
                try {
                    return InvocationResult.of(supplier.get());
                }
                catch (ClassCastException ex) {
                    if (!this.isLambdaGenericProblem(ex)) {
                        throw ex;
                    }
                    this.logNonMatchingType(callbackInstance, ex);
                }
            }
            return InvocationResult.noResult();
        }

        private boolean isLambdaGenericProblem(ClassCastException ex) {
            return ex.getMessage() == null || this.startsWithArgumentClassName(ex.getMessage());
        }

        private boolean startsWithArgumentClassName(String message) {
            Predicate<Object> startsWith = argument -> this.startsWithArgumentClassName(message, argument);
            return startsWith.test(this.argument) || Stream.of(this.additionalArguments).anyMatch(startsWith);
        }

        private boolean startsWithArgumentClassName(String message, Object argument) {
            if (argument == null) {
                return false;
            }
            Class<?> argumentType = argument.getClass();
            if (message.startsWith(argumentType.getName())) {
                return true;
            }
            if (message.startsWith(argumentType.toString())) {
                return true;
            }
            int moduleSeparatorIndex = message.indexOf(47);
            if (moduleSeparatorIndex != -1 && message.startsWith(argumentType.getName(), moduleSeparatorIndex + 1)) {
                return true;
            }
            if (CLASS_GET_MODULE != null) {
                Object module = ReflectionUtils.invokeMethod((Method)CLASS_GET_MODULE, argumentType);
                Object moduleName = ReflectionUtils.invokeMethod((Method)MODULE_GET_NAME, (Object)module);
                return message.startsWith(moduleName + "/" + argumentType.getName());
            }
            return false;
        }

        private void logNonMatchingType(C callback, ClassCastException ex) {
            if (this.logger.isDebugEnabled()) {
                Class expectedType = ResolvableType.forClass(this.callbackType).resolveGeneric(new int[0]);
                String expectedTypeName = expectedType != null ? ClassUtils.getShortName((Class)expectedType) + " type" : "type";
                String message = "Non-matching " + expectedTypeName + " for callback " + ClassUtils.getShortName(this.callbackType) + ": " + callback;
                this.logger.debug((Object)message, (Throwable)ex);
            }
        }

        private SELF self() {
            return (SELF)this;
        }
    }
}

