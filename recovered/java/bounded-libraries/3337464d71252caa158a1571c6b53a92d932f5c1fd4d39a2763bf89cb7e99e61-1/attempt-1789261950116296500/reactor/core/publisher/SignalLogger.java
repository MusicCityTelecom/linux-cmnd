/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.reactivestreams.Subscription
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.LongConsumer;
import java.util.logging.Level;
import org.reactivestreams.Subscription;
import reactor.core.CorePublisher;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.publisher.SignalPeek;
import reactor.core.publisher.SignalType;
import reactor.core.publisher.Traces;
import reactor.util.Logger;
import reactor.util.Loggers;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class SignalLogger<IN>
implements SignalPeek<IN> {
    static final int CONTEXT_PARENT = 256;
    static final int SUBSCRIBE = 128;
    static final int ON_SUBSCRIBE = 64;
    static final int ON_NEXT = 32;
    static final int ON_ERROR = 16;
    static final int ON_COMPLETE = 8;
    static final int REQUEST = 4;
    static final int CANCEL = 2;
    static final int AFTER_TERMINATE = 1;
    static final int ALL = 510;
    static final AtomicLong IDS = new AtomicLong(1L);
    final CorePublisher<IN> source;
    final Logger log;
    final boolean fuseable;
    final int options;
    final Level level;
    final String operatorLine;
    final long id;
    static final String LOG_TEMPLATE = "{}({})";
    static final String LOG_TEMPLATE_FUSEABLE = "| {}({})";

    SignalLogger(CorePublisher<IN> source, @Nullable String category, Level level, boolean correlateStack, SignalType ... options) {
        this(source, category, level, correlateStack, Loggers::getLogger, options);
    }

    SignalLogger(CorePublisher<IN> source, @Nullable String category, Level level, boolean correlateStack, Function<String, Logger> loggerSupplier, SignalType ... options) {
        this.source = Objects.requireNonNull(source, "source");
        this.id = IDS.getAndIncrement();
        this.fuseable = source instanceof Fuseable;
        this.operatorLine = correlateStack ? Traces.extractOperatorAssemblyInformation(Traces.callSiteSupplierFactory.get().get()) : null;
        boolean generated = category == null || category.isEmpty() || category.endsWith(".");
        String string = category = generated && category == null ? "reactor." : category;
        if (generated) {
            category = source instanceof Mono ? category + "Mono." + source.getClass().getSimpleName().replace("Mono", "") : (source instanceof ParallelFlux ? category + "Parallel." + source.getClass().getSimpleName().replace("Parallel", "") : category + "Flux." + source.getClass().getSimpleName().replace("Flux", ""));
            category = category + "." + this.id;
        }
        this.log = loggerSupplier.apply(category);
        this.level = level;
        if (options == null || options.length == 0) {
            this.options = 510;
        } else {
            int opts = 0;
            for (SignalType option : options) {
                if (option == SignalType.CANCEL) {
                    opts |= 2;
                    continue;
                }
                if (option == SignalType.CURRENT_CONTEXT) {
                    opts |= 0x100;
                    continue;
                }
                if (option == SignalType.ON_SUBSCRIBE) {
                    opts |= 0x40;
                    continue;
                }
                if (option == SignalType.REQUEST) {
                    opts |= 4;
                    continue;
                }
                if (option == SignalType.ON_NEXT) {
                    opts |= 0x20;
                    continue;
                }
                if (option == SignalType.ON_ERROR) {
                    opts |= 0x10;
                    continue;
                }
                if (option == SignalType.ON_COMPLETE) {
                    opts |= 8;
                    continue;
                }
                if (option == SignalType.SUBSCRIBE) {
                    opts |= 0x80;
                    continue;
                }
                if (option != SignalType.AFTER_TERMINATE) continue;
                opts |= 1;
            }
            this.options = opts;
        }
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PARENT) {
            return this.source;
        }
        return null;
    }

    void log(SignalType signalType, Object signalValue) {
        String line;
        String string = line = this.fuseable ? LOG_TEMPLATE_FUSEABLE : LOG_TEMPLATE;
        if (this.operatorLine != null) {
            line = line + " " + this.operatorLine;
        }
        if (this.level == Level.FINEST) {
            this.log.trace(line, new Object[]{signalType, signalValue});
        } else if (this.level == Level.FINE) {
            this.log.debug(line, new Object[]{signalType, signalValue});
        } else if (this.level == Level.INFO) {
            this.log.info(line, new Object[]{signalType, signalValue});
        } else if (this.level == Level.WARNING) {
            this.log.warn(line, new Object[]{signalType, signalValue});
        } else if (this.level == Level.SEVERE) {
            this.log.error(line, new Object[]{signalType, signalValue});
        }
    }

    void safeLog(SignalType signalType, Object signalValue) {
        block4: {
            if (signalValue instanceof Fuseable.QueueSubscription) {
                signalValue = String.valueOf(signalValue);
                if (this.log.isDebugEnabled()) {
                    this.log.debug("A Fuseable Subscription has been passed to the logging framework, this is generally a sign of a misplaced log(), eg. 'window(2).log()' instead of 'window(2).flatMap(w -> w.log())'");
                }
            }
            try {
                this.log(signalType, signalValue);
            }
            catch (UnsupportedOperationException uoe) {
                this.log(signalType, String.valueOf(signalValue));
                if (!this.log.isDebugEnabled()) break block4;
                this.log.debug("UnsupportedOperationException has been raised by the logging framework, does your log() placement make sense? eg. 'window(2).log()' instead of 'window(2).flatMap(w -> w.log())'", uoe);
            }
        }
    }

    static String subscriptionAsString(@Nullable Subscription s) {
        if (s == null) {
            return "null subscription";
        }
        StringBuilder asString = new StringBuilder();
        if (s instanceof Fuseable.SynchronousSubscription) {
            asString.append("[Synchronous Fuseable] ");
        } else if (s instanceof Fuseable.QueueSubscription) {
            asString.append("[Fuseable] ");
        }
        Class<?> clazz = s.getClass();
        String name = clazz.getCanonicalName();
        if (name == null) {
            name = clazz.getName();
        }
        name = name.replaceFirst(clazz.getPackage().getName() + ".", "");
        asString.append(name);
        return asString.toString();
    }

    @Override
    @Nullable
    public Consumer<? super Subscription> onSubscribeCall() {
        if ((this.options & 0x40) == 64 && (this.level != Level.INFO || this.log.isInfoEnabled())) {
            return s -> this.log(SignalType.ON_SUBSCRIBE, SignalLogger.subscriptionAsString(s));
        }
        return null;
    }

    @Override
    @Nullable
    public Consumer<? super Context> onCurrentContextCall() {
        if ((this.options & 0x100) == 256 && (this.level == Level.FINE && this.log.isDebugEnabled() || this.level == Level.FINEST && this.log.isTraceEnabled())) {
            return c -> this.log(SignalType.CURRENT_CONTEXT, c);
        }
        return null;
    }

    @Override
    @Nullable
    public Consumer<? super IN> onNextCall() {
        if ((this.options & 0x20) == 32 && (this.level != Level.INFO || this.log.isInfoEnabled())) {
            return d -> this.safeLog(SignalType.ON_NEXT, d);
        }
        return null;
    }

    @Override
    @Nullable
    public Consumer<? super Throwable> onErrorCall() {
        boolean shouldLogAsError;
        boolean shouldLogAsDebug = this.level == Level.FINE && this.log.isDebugEnabled();
        boolean shouldLogAsTrace = this.level == Level.FINEST && this.log.isTraceEnabled();
        boolean bl = shouldLogAsError = this.level != Level.FINE && this.level != Level.FINEST && this.log.isErrorEnabled();
        if ((this.options & 0x10) == 16 && (shouldLogAsError || shouldLogAsDebug || shouldLogAsTrace)) {
            String line;
            String string = line = this.fuseable ? LOG_TEMPLATE_FUSEABLE : LOG_TEMPLATE;
            if (this.operatorLine != null) {
                line = line + " " + this.operatorLine;
            }
            String s = line;
            if (shouldLogAsTrace) {
                return e -> {
                    this.log.trace(s, new Object[]{SignalType.ON_ERROR, e, this.source});
                    this.log.trace("", (Throwable)e);
                };
            }
            if (shouldLogAsDebug) {
                return e -> {
                    this.log.debug(s, new Object[]{SignalType.ON_ERROR, e, this.source});
                    this.log.debug("", (Throwable)e);
                };
            }
            return e -> {
                this.log.error(s, new Object[]{SignalType.ON_ERROR, e, this.source});
                this.log.error("", (Throwable)e);
            };
        }
        return null;
    }

    @Override
    @Nullable
    public Runnable onCompleteCall() {
        if ((this.options & 8) == 8 && (this.level != Level.INFO || this.log.isInfoEnabled())) {
            return () -> this.log(SignalType.ON_COMPLETE, "");
        }
        return null;
    }

    @Override
    @Nullable
    public Runnable onAfterTerminateCall() {
        if ((this.options & 1) == 1 && (this.level != Level.INFO || this.log.isInfoEnabled())) {
            return () -> this.log(SignalType.AFTER_TERMINATE, "");
        }
        return null;
    }

    @Override
    @Nullable
    public LongConsumer onRequestCall() {
        if ((this.options & 4) == 4 && (this.level != Level.INFO || this.log.isInfoEnabled())) {
            return n -> this.log(SignalType.REQUEST, Long.MAX_VALUE == n ? "unbounded" : Long.valueOf(n));
        }
        return null;
    }

    @Override
    @Nullable
    public Runnable onCancelCall() {
        if ((this.options & 2) == 2 && (this.level != Level.INFO || this.log.isInfoEnabled())) {
            return () -> this.log(SignalType.CANCEL, "");
        }
        return null;
    }

    public String toString() {
        return "/loggers/" + this.log.getName() + "/" + this.id;
    }
}

