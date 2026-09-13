/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.vmplugin.v8;

import groovy.lang.GroovySystem;
import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.lang.invoke.SwitchPoint;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.groovy.util.SystemUtil;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.runtime.NullObject;
import org.codehaus.groovy.vmplugin.v8.CacheableCallSite;
import org.codehaus.groovy.vmplugin.v8.IndyArrayAccess;
import org.codehaus.groovy.vmplugin.v8.MethodHandleWrapper;
import org.codehaus.groovy.vmplugin.v8.Selector;

public class IndyInterface {
    private static final long INDY_OPTIMIZE_THRESHOLD;
    private static final long INDY_FALLBACK_THRESHOLD;
    public static final int SAFE_NAVIGATION = 1;
    public static final int THIS_CALL = 2;
    public static final int GROOVY_OBJECT = 4;
    public static final int IMPLICIT_THIS = 8;
    public static final int SPREAD_CALL = 16;
    public static final int UNCACHED_CALL = 32;
    private static final MethodHandleWrapper NULL_METHOD_HANDLE_WRAPPER;
    protected static final Logger LOG;
    protected static final boolean LOG_ENABLED;
    public static final MethodHandles.Lookup LOOKUP;
    private static final MethodHandle FROM_CACHE_METHOD;
    private static final MethodHandle SELECT_METHOD;
    protected static SwitchPoint switchPoint;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected static void invalidateSwitchPoints() {
        if (LOG_ENABLED) {
            LOG.info("invalidating switch point");
        }
        Class<IndyInterface> clazz = IndyInterface.class;
        synchronized (IndyInterface.class) {
            SwitchPoint old = switchPoint;
            switchPoint = new SwitchPoint();
            SwitchPoint.invalidateAll(new SwitchPoint[]{old});
            // ** MonitorExit[var0] (shouldn't be in output)
            return;
        }
    }

    public static CallSite bootstrap(MethodHandles.Lookup caller, String callType, MethodType type, String name, int flags) {
        CallType ct = CallType.fromCallSiteName(callType);
        if (null == ct) {
            throw new GroovyBugError("Unknown call type: " + callType);
        }
        int callID = ct.ordinal();
        boolean safe = (flags & 1) != 0;
        boolean thisCall = (flags & 2) != 0;
        boolean spreadCall = (flags & 0x10) != 0;
        return IndyInterface.realBootstrap(caller, name, callID, type, safe, thisCall, spreadCall);
    }

    private static CallSite realBootstrap(MethodHandles.Lookup caller, String name, int callID, MethodType type, boolean safe, boolean thisCall, boolean spreadCall) {
        CacheableCallSite mc = new CacheableCallSite(type);
        Class<?> sender = caller.lookupClass();
        MethodHandle mh = IndyInterface.makeAdapter(mc, sender, name, callID, type, safe, thisCall, spreadCall);
        mc.setTarget(mh);
        mc.setDefaultTarget(mh);
        mc.setFallbackTarget(IndyInterface.makeFallBack(mc, sender, name, callID, type, safe, thisCall, spreadCall));
        return mc;
    }

    protected static MethodHandle makeFallBack(MutableCallSite mc, Class<?> sender, String name, int callID, MethodType type, boolean safeNavigation, boolean thisCall, boolean spreadCall) {
        return IndyInterface.make(mc, sender, name, callID, type, safeNavigation, thisCall, spreadCall, SELECT_METHOD);
    }

    private static MethodHandle makeAdapter(MutableCallSite mc, Class<?> sender, String name, int callID, MethodType type, boolean safeNavigation, boolean thisCall, boolean spreadCall) {
        return IndyInterface.make(mc, sender, name, callID, type, safeNavigation, thisCall, spreadCall, FROM_CACHE_METHOD);
    }

    private static MethodHandle make(MutableCallSite mc, Class<?> sender, String name, int callID, MethodType type, boolean safeNavigation, boolean thisCall, boolean spreadCall, MethodHandle originalMH) {
        MethodHandle mh = MethodHandles.insertArguments(originalMH, 0, mc, sender, name, callID, safeNavigation, thisCall, spreadCall, 1);
        return mh.asCollector(Object[].class, type.parameterCount()).asType(type);
    }

    public static Object fromCache(MutableCallSite callSite, Class<?> sender, String methodName, int callID, Boolean safeNavigation, Boolean thisCall, Boolean spreadCall, Object dummyReceiver, Object[] arguments) throws Throwable {
        FallbackSupplier fallbackSupplier = new FallbackSupplier(callSite, sender, methodName, callID, safeNavigation, thisCall, spreadCall, dummyReceiver, arguments);
        MethodHandleWrapper mhw = IndyInterface.doWithCallSite(callSite, arguments, (cs, receiver) -> cs.getAndPut(receiver.getClass().getName(), c -> {
            MethodHandleWrapper fbMhw = fallbackSupplier.get();
            return fbMhw.isCanSetTarget() ? fbMhw : NULL_METHOD_HANDLE_WRAPPER;
        }));
        if (NULL_METHOD_HANDLE_WRAPPER == mhw) {
            mhw = fallbackSupplier.get();
        }
        if (mhw.isCanSetTarget() && callSite.getTarget() != mhw.getTargetMethodHandle() && mhw.getLatestHitCount() > INDY_OPTIMIZE_THRESHOLD) {
            callSite.setTarget(mhw.getTargetMethodHandle());
            if (LOG_ENABLED) {
                LOG.info("call site target set, preparing outside invocation");
            }
            mhw.resetLatestHitCount();
        }
        return mhw.getCachedMethodHandle().invokeExact(arguments);
    }

    public static Object selectMethod(MutableCallSite callSite, Class<?> sender, String methodName, int callID, Boolean safeNavigation, Boolean thisCall, Boolean spreadCall, Object dummyReceiver, Object[] arguments) throws Throwable {
        MethodHandleWrapper mhw = IndyInterface.fallback(callSite, sender, methodName, callID, safeNavigation, thisCall, spreadCall, dummyReceiver, arguments);
        if (callSite instanceof CacheableCallSite) {
            CacheableCallSite cacheableCallSite = (CacheableCallSite)callSite;
            MethodHandle defaultTarget = cacheableCallSite.getDefaultTarget();
            long fallbackCount = cacheableCallSite.incrementFallbackCount();
            if (fallbackCount > INDY_FALLBACK_THRESHOLD && cacheableCallSite.getTarget() != defaultTarget) {
                cacheableCallSite.setTarget(defaultTarget);
                if (LOG_ENABLED) {
                    LOG.info("call site target reset to default, preparing outside invocation");
                }
                cacheableCallSite.resetFallbackCount();
            }
            if (defaultTarget == cacheableCallSite.getTarget()) {
                IndyInterface.doWithCallSite(callSite, arguments, (cs, receiver) -> cs.put(receiver.getClass().getName(), mhw));
            }
        }
        return mhw.getCachedMethodHandle().invokeExact(arguments);
    }

    private static MethodHandleWrapper fallback(MutableCallSite callSite, Class<?> sender, String methodName, int callID, Boolean safeNavigation, Boolean thisCall, Boolean spreadCall, Object dummyReceiver, Object[] arguments) {
        Selector selector = Selector.getSelector(callSite, sender, methodName, callID, safeNavigation, thisCall, spreadCall, arguments);
        selector.setCallSiteTarget();
        return new MethodHandleWrapper(selector.handle.asSpreader(Object[].class, arguments.length).asType(MethodType.methodType(Object.class, Object[].class)), selector.handle, selector.cache);
    }

    private static <T> T doWithCallSite(MutableCallSite callSite, Object[] arguments, BiFunction<? super CacheableCallSite, ? super Object, ? extends T> f) {
        if (callSite instanceof CacheableCallSite) {
            CacheableCallSite cacheableCallSite = (CacheableCallSite)callSite;
            Object receiver = arguments[0];
            if (null == receiver) {
                receiver = NullObject.getNullObject();
            }
            return f.apply(cacheableCallSite, receiver);
        }
        throw new GroovyBugError("CacheableCallSite is expected, but the actual callsite is: " + callSite);
    }

    public static CallSite staticArrayAccess(MethodHandles.Lookup lookup, String name, MethodType type) {
        if (type.parameterCount() == 2) {
            return new ConstantCallSite(IndyArrayAccess.arrayGet(type));
        }
        return new ConstantCallSite(IndyArrayAccess.arraySet(type));
    }

    static {
        MethodType mt;
        INDY_OPTIMIZE_THRESHOLD = SystemUtil.getLongSafe("groovy.indy.optimize.threshold", 10000L);
        INDY_FALLBACK_THRESHOLD = SystemUtil.getLongSafe("groovy.indy.fallback.threshold", 10000L);
        NULL_METHOD_HANDLE_WRAPPER = MethodHandleWrapper.getNullMethodHandleWrapper();
        boolean enableLogger = false;
        LOG = Logger.getLogger(IndyInterface.class.getName());
        try {
            if (System.getProperty("groovy.indy.logging") != null) {
                LOG.setLevel(Level.ALL);
                enableLogger = true;
            }
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
        LOG_ENABLED = enableLogger;
        LOOKUP = MethodHandles.lookup();
        try {
            mt = MethodType.methodType(Object.class, MutableCallSite.class, Class.class, String.class, Integer.TYPE, Boolean.class, Boolean.class, Boolean.class, Object.class, Object[].class);
            FROM_CACHE_METHOD = LOOKUP.findStatic(IndyInterface.class, "fromCache", mt);
        }
        catch (Exception e) {
            throw new GroovyBugError(e);
        }
        try {
            mt = MethodType.methodType(Object.class, MutableCallSite.class, Class.class, String.class, Integer.TYPE, Boolean.class, Boolean.class, Boolean.class, Object.class, Object[].class);
            SELECT_METHOD = LOOKUP.findStatic(IndyInterface.class, "selectMethod", mt);
        }
        catch (Exception e) {
            throw new GroovyBugError(e);
        }
        switchPoint = new SwitchPoint();
        GroovySystem.getMetaClassRegistry().addMetaClassRegistryChangeEventListener(cmcu -> IndyInterface.invalidateSwitchPoints());
    }

    public static enum CallType {
        METHOD("invoke"),
        INIT("init"),
        GET("getProperty"),
        SET("setProperty"),
        CAST("cast");

        private static final Map<String, CallType> NAME_CALLTYPE_MAP;
        private final String name;

        private CallType(String callSiteName) {
            this.name = callSiteName;
        }

        public String getCallSiteName() {
            return this.name;
        }

        public static CallType fromCallSiteName(String callSiteName) {
            return NAME_CALLTYPE_MAP.get(callSiteName);
        }

        static {
            NAME_CALLTYPE_MAP = Stream.of(CallType.values()).collect(Collectors.toMap(CallType::getCallSiteName, Function.identity()));
        }
    }

    private static class FallbackSupplier {
        private final MutableCallSite callSite;
        private final Class<?> sender;
        private final String methodName;
        private final int callID;
        private final Boolean safeNavigation;
        private final Boolean thisCall;
        private final Boolean spreadCall;
        private final Object dummyReceiver;
        private final Object[] arguments;
        private MethodHandleWrapper result;

        FallbackSupplier(MutableCallSite callSite, Class<?> sender, String methodName, int callID, Boolean safeNavigation, Boolean thisCall, Boolean spreadCall, Object dummyReceiver, Object[] arguments) {
            this.callSite = callSite;
            this.sender = sender;
            this.methodName = methodName;
            this.callID = callID;
            this.safeNavigation = safeNavigation;
            this.thisCall = thisCall;
            this.spreadCall = spreadCall;
            this.dummyReceiver = dummyReceiver;
            this.arguments = arguments;
        }

        MethodHandleWrapper get() {
            if (null == this.result) {
                this.result = IndyInterface.fallback(this.callSite, this.sender, this.methodName, this.callID, this.safeNavigation, this.thisCall, this.spreadCall, this.dummyReceiver, this.arguments);
            }
            return this.result;
        }
    }
}

