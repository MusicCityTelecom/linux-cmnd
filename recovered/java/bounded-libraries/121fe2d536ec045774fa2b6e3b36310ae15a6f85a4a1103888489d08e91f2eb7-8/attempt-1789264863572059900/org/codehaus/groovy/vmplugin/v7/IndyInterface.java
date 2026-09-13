/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.vmplugin.v7;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.codehaus.groovy.vmplugin.VMPluginFactory;

@Deprecated
public class IndyInterface {
    public static final int SAFE_NAVIGATION = 1;
    public static final int THIS_CALL = 2;
    public static final int GROOVY_OBJECT = 4;
    public static final int IMPLICIT_THIS = 8;
    public static final int SPREAD_CALL = 16;
    public static final int UNCACHED_CALL = 32;
    public static final MethodHandles.Lookup LOOKUP = org.codehaus.groovy.vmplugin.v8.IndyInterface.LOOKUP;

    protected static void invalidateSwitchPoints() {
        VMPluginFactory.getPlugin().invalidateCallSites();
    }

    public static CallSite bootstrap(MethodHandles.Lookup caller, String callType, MethodType type, String name, int flags) {
        return org.codehaus.groovy.vmplugin.v8.IndyInterface.bootstrap(caller, callType, type, name, flags);
    }

    public static Object fromCache(MutableCallSite callSite, Class<?> sender, String methodName, int callID, Boolean safeNavigation, Boolean thisCall, Boolean spreadCall, Object dummyReceiver, Object[] arguments) throws Throwable {
        return org.codehaus.groovy.vmplugin.v8.IndyInterface.fromCache(callSite, sender, methodName, callID, safeNavigation, thisCall, spreadCall, dummyReceiver, arguments);
    }

    public static Object selectMethod(MutableCallSite callSite, Class<?> sender, String methodName, int callID, Boolean safeNavigation, Boolean thisCall, Boolean spreadCall, Object dummyReceiver, Object[] arguments) throws Throwable {
        return org.codehaus.groovy.vmplugin.v8.IndyInterface.selectMethod(callSite, sender, methodName, callID, safeNavigation, thisCall, spreadCall, dummyReceiver, arguments);
    }

    public static CallSite staticArrayAccess(MethodHandles.Lookup lookup, String name, MethodType type) {
        return org.codehaus.groovy.vmplugin.v8.IndyInterface.staticArrayAccess(lookup, name, type);
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
}

