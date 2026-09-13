/*
 * Decompiled with CFR 0.152.
 */
package groovy.lang;

import groovy.lang.MetaClassRegistry;
import java.util.Map;
import org.apache.groovy.plugin.GroovyRunner;
import org.apache.groovy.plugin.GroovyRunnerRegistry;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.runtime.metaclass.MetaClassRegistryImpl;
import org.codehaus.groovy.util.ReferenceBundle;
import org.codehaus.groovy.util.ReleaseInfo;

public final class GroovySystem {
    @Deprecated
    private static final boolean USE_REFLECTION = true;
    private static final MetaClassRegistry META_CLASS_REGISTRY = new MetaClassRegistryImpl();
    @Deprecated
    public static final Map<String, GroovyRunner> RUNNER_REGISTRY = GroovyRunnerRegistry.getInstance();
    private static boolean keepJavaMetaClasses = false;

    private GroovySystem() {
    }

    @Deprecated
    public static boolean isUseReflection() {
        return USE_REFLECTION;
    }

    public static MetaClassRegistry getMetaClassRegistry() {
        return META_CLASS_REGISTRY;
    }

    public static void setKeepJavaMetaClasses(boolean keepJavaMetaClasses) {
        GroovySystem.keepJavaMetaClasses = keepJavaMetaClasses;
    }

    public static boolean isKeepJavaMetaClasses() {
        return keepJavaMetaClasses;
    }

    public static void stopThreadedReferenceManager() {
        ReferenceBundle.getSoftBundle().getManager().stopThread();
        ReferenceBundle.getWeakBundle().getManager().stopThread();
    }

    public static String getVersion() {
        return ReleaseInfo.getVersion();
    }

    public static String getShortVersion() {
        int firstDot;
        String full = GroovySystem.getVersion();
        int secondDot = full.indexOf(46, (firstDot = full.indexOf(46)) + 1);
        if (secondDot < 0) {
            throw new GroovyBugError("Unexpected version found: " + full);
        }
        return full.substring(0, secondDot);
    }
}

