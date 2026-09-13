/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Publisher
 */
package reactor.core.publisher;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import org.reactivestreams.Publisher;
import reactor.core.Exceptions;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.ConnectableFluxOnAssembly;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxCallableOnAssembly;
import reactor.core.publisher.FluxOnAssembly;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoCallableOnAssembly;
import reactor.core.publisher.MonoOnAssembly;
import reactor.core.publisher.OnNextFailureStrategy;
import reactor.core.publisher.ParallelFlux;
import reactor.core.publisher.ParallelFluxOnAssembly;
import reactor.util.Logger;
import reactor.util.Loggers;
import reactor.util.annotation.Nullable;

public abstract class Hooks {
    static Function<Publisher, Publisher> onEachOperatorHook;
    static volatile Function<Publisher, Publisher> onLastOperatorHook;
    static volatile BiFunction<? super Throwable, Object, ? extends Throwable> onOperatorErrorHook;
    static volatile Consumer<? super Throwable> onErrorDroppedHook;
    static volatile Consumer<Object> onNextDroppedHook;
    static volatile OnNextFailureStrategy onNextErrorHook;
    private static final LinkedHashMap<String, Function<? super Publisher<Object>, ? extends Publisher<Object>>> onEachOperatorHooks;
    private static final LinkedHashMap<String, Function<? super Publisher<Object>, ? extends Publisher<Object>>> onLastOperatorHooks;
    private static final LinkedHashMap<String, BiFunction<? super Throwable, Object, ? extends Throwable>> onOperatorErrorHooks;
    private static final LinkedHashMap<String, Function<Queue<?>, Queue<?>>> QUEUE_WRAPPERS;
    private static Function<Queue<?>, Queue<?>> QUEUE_WRAPPER;
    static final Logger log;
    static final String KEY_ON_ERROR_DROPPED = "reactor.onErrorDropped.local";
    static final String KEY_ON_NEXT_DROPPED = "reactor.onNextDropped.local";
    static final String KEY_ON_OPERATOR_ERROR = "reactor.onOperatorError.local";
    static final String KEY_ON_DISCARD = "reactor.onDiscard.local";
    static final String KEY_ON_REJECTED_EXECUTION = "reactor.onRejectedExecution.local";
    static boolean GLOBAL_TRACE;
    static boolean DETECT_CONTEXT_LOSS;

    public static <T> Flux<T> convertToFluxBypassingHooks(Publisher<T> publisher) {
        return Flux.wrap(publisher);
    }

    public static <T> Mono<T> convertToMonoBypassingHooks(Publisher<T> publisher, boolean enforceMonoContract) {
        return Mono.wrap(publisher, enforceMonoContract);
    }

    public static void onEachOperator(Function<? super Publisher<Object>, ? extends Publisher<Object>> onEachOperator) {
        Hooks.onEachOperator(onEachOperator.toString(), onEachOperator);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void onEachOperator(String key, Function<? super Publisher<Object>, ? extends Publisher<Object>> onEachOperator) {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(onEachOperator, "onEachOperator");
        log.debug("Hooking onEachOperator: {}", key);
        Logger logger = log;
        synchronized (logger) {
            onEachOperatorHooks.put(key, onEachOperator);
            onEachOperatorHook = Hooks.createOrUpdateOpHook(onEachOperatorHooks.values());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void resetOnEachOperator(String key) {
        Objects.requireNonNull(key, "key");
        log.debug("Reset onEachOperator: {}", key);
        Logger logger = log;
        synchronized (logger) {
            onEachOperatorHooks.remove(key);
            onEachOperatorHook = Hooks.createOrUpdateOpHook(onEachOperatorHooks.values());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void resetOnEachOperator() {
        log.debug("Reset to factory defaults : onEachOperator");
        Logger logger = log;
        synchronized (logger) {
            onEachOperatorHooks.clear();
            onEachOperatorHook = null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void onErrorDropped(Consumer<? super Throwable> c) {
        Objects.requireNonNull(c, "onErrorDroppedHook");
        log.debug("Hooking new default : onErrorDropped");
        Logger logger = log;
        synchronized (logger) {
            if (onErrorDroppedHook != null) {
                Consumer<? super Throwable> _c = onErrorDroppedHook.andThen(c);
                onErrorDroppedHook = _c;
            } else {
                onErrorDroppedHook = c;
            }
        }
    }

    public static void onLastOperator(Function<? super Publisher<Object>, ? extends Publisher<Object>> onLastOperator) {
        Hooks.onLastOperator(onLastOperator.toString(), onLastOperator);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void onLastOperator(String key, Function<? super Publisher<Object>, ? extends Publisher<Object>> onLastOperator) {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(onLastOperator, "onLastOperator");
        log.debug("Hooking onLastOperator: {}", key);
        Logger logger = log;
        synchronized (logger) {
            onLastOperatorHooks.put(key, onLastOperator);
            onLastOperatorHook = Hooks.createOrUpdateOpHook(onLastOperatorHooks.values());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void resetOnLastOperator(String key) {
        Objects.requireNonNull(key, "key");
        log.debug("Reset onLastOperator: {}", key);
        Logger logger = log;
        synchronized (logger) {
            onLastOperatorHooks.remove(key);
            onLastOperatorHook = Hooks.createOrUpdateOpHook(onLastOperatorHooks.values());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void resetOnLastOperator() {
        log.debug("Reset to factory defaults : onLastOperator");
        Logger logger = log;
        synchronized (logger) {
            onLastOperatorHooks.clear();
            onLastOperatorHook = null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void onNextDropped(Consumer<Object> c) {
        Objects.requireNonNull(c, "onNextDroppedHook");
        log.debug("Hooking new default : onNextDropped");
        Logger logger = log;
        synchronized (logger) {
            onNextDroppedHook = onNextDroppedHook != null ? onNextDroppedHook.andThen(c) : c;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void onNextDroppedFail() {
        log.debug("Enabling failure mode for onNextDropped");
        Logger logger = log;
        synchronized (logger) {
            onNextDroppedHook = n -> {
                throw Exceptions.failWithCancel();
            };
        }
    }

    public static void onOperatorDebug() {
        log.debug("Enabling stacktrace debugging via onOperatorDebug");
        GLOBAL_TRACE = true;
    }

    public static void resetOnOperatorDebug() {
        GLOBAL_TRACE = false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void onNextError(BiFunction<? super Throwable, Object, ? extends Throwable> onNextError) {
        Objects.requireNonNull(onNextError, "onNextError");
        log.debug("Hooking new default : onNextError");
        if (onNextError instanceof OnNextFailureStrategy) {
            Logger logger = log;
            synchronized (logger) {
                onNextErrorHook = (OnNextFailureStrategy)onNextError;
            }
        }
        Logger logger = log;
        synchronized (logger) {
            onNextErrorHook = new OnNextFailureStrategy.LambdaOnNextErrorStrategy(onNextError);
        }
    }

    public static void onOperatorError(BiFunction<? super Throwable, Object, ? extends Throwable> onOperatorError) {
        Hooks.onOperatorError(onOperatorError.toString(), onOperatorError);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void onOperatorError(String key, BiFunction<? super Throwable, Object, ? extends Throwable> onOperatorError) {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(onOperatorError, "onOperatorError");
        log.debug("Hooking onOperatorError: {}", key);
        Logger logger = log;
        synchronized (logger) {
            onOperatorErrorHooks.put(key, onOperatorError);
            onOperatorErrorHook = Hooks.createOrUpdateOpErrorHook(onOperatorErrorHooks.values());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void resetOnOperatorError(String key) {
        Objects.requireNonNull(key, "key");
        log.debug("Reset onOperatorError: {}", key);
        Logger logger = log;
        synchronized (logger) {
            onOperatorErrorHooks.remove(key);
            onOperatorErrorHook = Hooks.createOrUpdateOpErrorHook(onOperatorErrorHooks.values());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void resetOnOperatorError() {
        log.debug("Reset to factory defaults : onOperatorError");
        Logger logger = log;
        synchronized (logger) {
            onOperatorErrorHooks.clear();
            onOperatorErrorHook = null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void resetOnErrorDropped() {
        log.debug("Reset to factory defaults : onErrorDropped");
        Logger logger = log;
        synchronized (logger) {
            onErrorDroppedHook = null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void resetOnNextDropped() {
        log.debug("Reset to factory defaults : onNextDropped");
        Logger logger = log;
        synchronized (logger) {
            onNextDroppedHook = null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void resetOnNextError() {
        log.debug("Reset to factory defaults : onNextError");
        Logger logger = log;
        synchronized (logger) {
            onNextErrorHook = null;
        }
    }

    public static void enableContextLossTracking() {
        DETECT_CONTEXT_LOSS = true;
    }

    public static void disableContextLossTracking() {
        DETECT_CONTEXT_LOSS = false;
    }

    @Nullable
    static Function<Publisher, Publisher> createOrUpdateOpHook(Collection<Function<? super Publisher<Object>, ? extends Publisher<Object>>> hooks) {
        Function<? super Publisher<Object>, ? extends Publisher<Object>> composite = null;
        Iterator<Function<? super Publisher<Object>, ? extends Publisher<Object>>> iterator = hooks.iterator();
        while (iterator.hasNext()) {
            Function<? super Publisher<Object>, ? extends Publisher<Object>> function;
            Function<? super Publisher<Object>, ? extends Publisher<Object>> op = function = iterator.next();
            if (composite != null) {
                composite = composite.andThen(op);
                continue;
            }
            composite = op;
        }
        return composite;
    }

    @Nullable
    static BiFunction<? super Throwable, Object, ? extends Throwable> createOrUpdateOpErrorHook(Collection<BiFunction<? super Throwable, Object, ? extends Throwable>> hooks) {
        BiFunction<? super Throwable, Object, ? extends Throwable> composite = null;
        for (BiFunction<? super Throwable, Object, ? extends Throwable> function : hooks) {
            if (composite != null) {
                BiFunction<? super Throwable, Object, ? extends Throwable> ff = composite;
                composite = (e, data) -> (Throwable)function.apply((Throwable)ff.apply((Throwable)e, data), data);
                continue;
            }
            composite = function;
        }
        return composite;
    }

    static final Map<String, Function<? super Publisher<Object>, ? extends Publisher<Object>>> getOnEachOperatorHooks() {
        return Collections.unmodifiableMap(onEachOperatorHooks);
    }

    static final Map<String, Function<? super Publisher<Object>, ? extends Publisher<Object>>> getOnLastOperatorHooks() {
        return Collections.unmodifiableMap(onLastOperatorHooks);
    }

    static final Map<String, BiFunction<? super Throwable, Object, ? extends Throwable>> getOnOperatorErrorHooks() {
        return Collections.unmodifiableMap(onOperatorErrorHooks);
    }

    static boolean initStaticGlobalTrace() {
        return Boolean.parseBoolean(System.getProperty("reactor.trace.operatorStacktrace", "false"));
    }

    Hooks() {
    }

    @Nullable
    @Deprecated
    public static <T, P extends Publisher<T>> Publisher<T> addReturnInfo(@Nullable P publisher, String method) {
        if (publisher == null) {
            return null;
        }
        return Hooks.addAssemblyInfo(publisher, new FluxOnAssembly.MethodReturnSnapshot(method));
    }

    @Nullable
    @Deprecated
    public static <T, P extends Publisher<T>> Publisher<T> addCallSiteInfo(@Nullable P publisher, String callSite) {
        if (publisher == null) {
            return null;
        }
        return Hooks.addAssemblyInfo(publisher, new FluxOnAssembly.AssemblySnapshot(callSite));
    }

    static <T, P extends Publisher<T>> Publisher<T> addAssemblyInfo(P publisher, FluxOnAssembly.AssemblySnapshot stacktrace) {
        if (publisher instanceof Callable) {
            if (publisher instanceof Mono) {
                return new MonoCallableOnAssembly((Mono)publisher, stacktrace);
            }
            return new FluxCallableOnAssembly((Flux)publisher, stacktrace);
        }
        if (publisher instanceof Mono) {
            return new MonoOnAssembly((Mono)publisher, stacktrace);
        }
        if (publisher instanceof ParallelFlux) {
            return new ParallelFluxOnAssembly((ParallelFlux)publisher, stacktrace);
        }
        if (publisher instanceof ConnectableFlux) {
            return new ConnectableFluxOnAssembly((ConnectableFlux)publisher, stacktrace);
        }
        return new FluxOnAssembly((Flux)publisher, stacktrace);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void addQueueWrapper(String key, Function<Queue<?>, Queue<?>> decorator) {
        LinkedHashMap<String, Function<Queue<?>, Queue<?>>> linkedHashMap = QUEUE_WRAPPERS;
        synchronized (linkedHashMap) {
            QUEUE_WRAPPERS.put(key, decorator);
            Function<Queue<?>, Queue<?>> newHook = null;
            for (Function<Queue<?>, Queue<?>> function : QUEUE_WRAPPERS.values()) {
                if (newHook == null) {
                    newHook = function;
                    continue;
                }
                newHook = newHook.andThen(function);
            }
            QUEUE_WRAPPER = newHook;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void removeQueueWrapper(String key) {
        LinkedHashMap<String, Function<Queue<?>, Queue<?>>> linkedHashMap = QUEUE_WRAPPERS;
        synchronized (linkedHashMap) {
            QUEUE_WRAPPERS.remove(key);
            if (QUEUE_WRAPPERS.isEmpty()) {
                QUEUE_WRAPPER = Function.identity();
            } else {
                Function<Queue<?>, Queue<?>> newHook = null;
                for (Function<Queue<?>, Queue<?>> function : QUEUE_WRAPPERS.values()) {
                    if (newHook == null) {
                        newHook = function;
                        continue;
                    }
                    newHook = newHook.andThen(function);
                }
                QUEUE_WRAPPER = newHook;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void removeQueueWrappers() {
        LinkedHashMap<String, Function<Queue<?>, Queue<?>>> linkedHashMap = QUEUE_WRAPPERS;
        synchronized (linkedHashMap) {
            QUEUE_WRAPPERS.clear();
            QUEUE_WRAPPER = Function.identity();
        }
    }

    public static <T> Queue<T> wrapQueue(Queue<T> queue) {
        return QUEUE_WRAPPER.apply(queue);
    }

    static {
        QUEUE_WRAPPERS = new LinkedHashMap(1);
        QUEUE_WRAPPER = Function.identity();
        log = Loggers.getLogger(Hooks.class);
        GLOBAL_TRACE = Hooks.initStaticGlobalTrace();
        DETECT_CONTEXT_LOSS = false;
        onEachOperatorHooks = new LinkedHashMap(1);
        onLastOperatorHooks = new LinkedHashMap(1);
        onOperatorErrorHooks = new LinkedHashMap(1);
    }
}

