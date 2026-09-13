/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.ticket.InvalidTicketException
 *  org.jooq.lambda.Unchecked
 *  org.jooq.lambda.fi.util.function.CheckedConsumer
 *  org.jooq.lambda.fi.util.function.CheckedFunction
 *  org.jooq.lambda.fi.util.function.CheckedSupplier
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.retry.RetryCallback
 *  org.springframework.retry.RetryPolicy
 *  org.springframework.retry.backoff.BackOffPolicy
 *  org.springframework.retry.backoff.FixedBackOffPolicy
 *  org.springframework.retry.policy.SimpleRetryPolicy
 *  org.springframework.retry.support.RetryTemplate
 */
package org.apereo.cas.util.function;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import lombok.Generated;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.ticket.InvalidTicketException;
import org.apereo.cas.util.LoggingUtils;
import org.jooq.lambda.Unchecked;
import org.jooq.lambda.fi.util.function.CheckedConsumer;
import org.jooq.lambda.fi.util.function.CheckedFunction;
import org.jooq.lambda.fi.util.function.CheckedSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

public final class FunctionUtils {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(FunctionUtils.class);

    public static <T, R> Function<T, R> doIf(Predicate<Object> condition, Supplier<R> trueFunction, Supplier<R> falseFunction) {
        return t -> {
            try {
                if (condition.test(t)) {
                    return trueFunction.get();
                }
                return falseFunction.get();
            }
            catch (Throwable e) {
                LoggingUtils.warn(LOGGER, e);
                return falseFunction.get();
            }
        };
    }

    public static <T> Consumer<T> doIf(boolean condition, Consumer<T> trueFunction, Consumer<T> falseFunction) {
        return account -> {
            if (condition) {
                trueFunction.accept(account);
            } else {
                falseFunction.accept(account);
            }
        };
    }

    public static <T> Consumer<T> doIf(boolean condition, Consumer<T> trueFunction) {
        return FunctionUtils.doIf(condition, trueFunction, (T t) -> {});
    }

    public static <R> Supplier<R> doIf(boolean condition, Supplier<R> trueFunction, Supplier<R> falseFunction) {
        return () -> {
            try {
                if (condition) {
                    return trueFunction.get();
                }
                return falseFunction.get();
            }
            catch (Throwable e) {
                LoggingUtils.warn(LOGGER, e);
                return falseFunction.get();
            }
        };
    }

    public static <T, R> Function<T, R> doIf(Predicate<T> condition, CheckedFunction<T, R> trueFunction, CheckedFunction<T, R> falseFunction) {
        return t -> {
            try {
                if (condition.test(t)) {
                    return trueFunction.apply(t);
                }
                return falseFunction.apply(t);
            }
            catch (Throwable e) {
                LoggingUtils.warn(LOGGER, e);
                try {
                    return falseFunction.apply(t);
                }
                catch (Throwable ex) {
                    throw new IllegalArgumentException(ex.getMessage());
                }
            }
        };
    }

    public static <R> Supplier<R> doIfNotNull(Object input, Supplier<R> trueFunction, Supplier<R> falseFunction) {
        return () -> {
            try {
                if (input != null) {
                    return trueFunction.get();
                }
                return falseFunction.get();
            }
            catch (Throwable e) {
                LoggingUtils.warn(LOGGER, e);
                return falseFunction.get();
            }
        };
    }

    public static <T> void doIfNotNull(T input, CheckedConsumer<T> trueFunction) {
        try {
            if (input != null) {
                trueFunction.accept(input);
            }
        }
        catch (Throwable e) {
            LoggingUtils.warn(LOGGER, e);
        }
    }

    public static <T> void doIfNotNull(T input, CheckedConsumer<T> trueFunction, CheckedConsumer<T> elseFunction) {
        try {
            if (input != null) {
                trueFunction.accept(input);
            } else {
                elseFunction.accept(null);
            }
        }
        catch (Throwable e) {
            LoggingUtils.warn(LOGGER, e);
        }
    }

    public static <R> Supplier<R> doIfNull(Object input, Supplier<R> trueFunction, Supplier<R> falseFunction) {
        return () -> {
            try {
                if (input == null) {
                    return trueFunction.get();
                }
                return falseFunction.get();
            }
            catch (Throwable e) {
                LoggingUtils.warn(LOGGER, e);
                return falseFunction.get();
            }
        };
    }

    public static <T, R> Function<T, R> doAndHandle(CheckedFunction<T, R> function, CheckedFunction<Throwable, R> errorHandler) {
        return t -> {
            try {
                return function.apply(t);
            }
            catch (Throwable e) {
                try {
                    LoggingUtils.warn(LOGGER, e);
                    return errorHandler.apply((Object)e);
                }
                catch (Throwable ex) {
                    throw new IllegalArgumentException(ex.getMessage());
                }
            }
        };
    }

    public static <R> Consumer<R> doAndHandle(CheckedConsumer<R> function, CheckedFunction<Throwable, R> errorHandler) {
        return value -> {
            try {
                function.accept(value);
            }
            catch (Throwable e) {
                try {
                    LoggingUtils.warn(LOGGER, e);
                    errorHandler.apply((Object)e);
                }
                catch (Throwable ex) {
                    throw new IllegalArgumentException(ex);
                }
            }
        };
    }

    public static <R> R doAndHandle(CheckedSupplier<R> function) {
        try {
            return (R)function.get();
        }
        catch (InvalidTicketException e) {
            LOGGER.debug(e.getMessage(), (Throwable)e);
        }
        catch (Throwable e) {
            LoggingUtils.warn(LOGGER, e);
        }
        return null;
    }

    public static <R> void doAndHandle(CheckedConsumer<R> function) {
        try {
            function.accept(null);
        }
        catch (Throwable e) {
            LoggingUtils.warn(LOGGER, e);
        }
    }

    public static <R> Supplier<R> doAndHandle(CheckedSupplier<R> function, CheckedFunction<Throwable, R> errorHandler) {
        return () -> {
            try {
                return function.get();
            }
            catch (Throwable e) {
                try {
                    LoggingUtils.warn(LOGGER, e);
                    return errorHandler.apply((Object)e);
                }
                catch (Throwable ex) {
                    if (ex instanceof RuntimeException) {
                        throw (RuntimeException)ex;
                    }
                    throw new IllegalArgumentException(ex);
                }
            }
        };
    }

    public static boolean doWithoutThrows(Consumer<Object> func, Object ... params) {
        try {
            func.accept(params);
            return true;
        }
        catch (Throwable e) {
            LoggingUtils.warn(LOGGER, e);
            return false;
        }
    }

    public static <T> T doUnchecked(CheckedSupplier<T> consumer) {
        return Unchecked.supplier(consumer).get();
    }

    public static void doUnchecked(CheckedConsumer<Object> consumer, Object ... params) {
        Unchecked.consumer(s -> consumer.accept((Object)params)).accept(null);
    }

    public static <T> T doAndRetry(RetryCallback<T, Exception> callback) {
        return FunctionUtils.doAndRetry(List.of(), callback);
    }

    public static <T> T doAndRetry(List<Class<? extends Throwable>> clazzes, RetryCallback<T, Exception> callback) {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setBackOffPolicy((BackOffPolicy)new FixedBackOffPolicy());
        HashMap<Class, Boolean> classified = new HashMap<Class, Boolean>();
        classified.put(Error.class, Boolean.TRUE);
        classified.put(Throwable.class, Boolean.TRUE);
        clazzes.forEach(clz -> classified.put((Class)clz, Boolean.TRUE));
        retryTemplate.setRetryPolicy((RetryPolicy)new SimpleRetryPolicy(3, classified, true));
        retryTemplate.setThrowLastExceptionOnExhausted(true);
        return Unchecked.supplier(() -> retryTemplate.execute(callback)).get();
    }

    public static String throwIfBlank(String value) {
        FunctionUtils.throwIf(StringUtils.isBlank((CharSequence)value), () -> new IllegalArgumentException("Value cannot be empty or blank"));
        return value;
    }

    public static void throwIf(boolean condition, Supplier<? extends RuntimeException> throwable) {
        if (condition) {
            throw throwable.get();
        }
    }

    public static <T> T doAndReturn(boolean condition, Supplier<T> trueTask, Supplier<T> falseTask) {
        return condition ? trueTask.get() : falseTask.get();
    }

    @Generated
    private FunctionUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

