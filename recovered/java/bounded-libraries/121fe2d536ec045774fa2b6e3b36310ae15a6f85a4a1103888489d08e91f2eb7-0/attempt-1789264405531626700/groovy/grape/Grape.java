/*
 * Decompiled with CFR 0.152.
 */
package groovy.grape;

import groovy.grape.GrapeEngine;
import java.net.URI;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Grape {
    public static final String AUTO_DOWNLOAD_SETTING = "autoDownload";
    public static final String DISABLE_CHECKSUMS_SETTING = "disableChecksums";
    public static final String SYSTEM_PROPERTIES_SETTING = "systemProperties";
    private static final URI[] EMPTY_URI_ARRAY = new URI[0];
    private static final Map[] EMPTY_MAP_ARRAY = new Map[0];
    private static boolean enableGrapes = Boolean.parseBoolean(System.getProperty("groovy.grape.enable", "true"));
    private static boolean enableAutoDownload = Boolean.parseBoolean(System.getProperty("groovy.grape.autoDownload", "true"));
    private static boolean disableChecksums = Boolean.parseBoolean(System.getProperty("groovy.grape.disableChecksums", "false"));
    protected static GrapeEngine instance;

    public static boolean getEnableGrapes() {
        return enableGrapes;
    }

    public static void setEnableGrapes(boolean enableGrapes) {
        Grape.enableGrapes = enableGrapes;
    }

    public static boolean getEnableAutoDownload() {
        return enableAutoDownload;
    }

    public static void setEnableAutoDownload(boolean enableAutoDownload) {
        Grape.enableAutoDownload = enableAutoDownload;
    }

    public static boolean getDisableChecksums() {
        return disableChecksums;
    }

    public static void setDisableChecksums(boolean disableChecksums) {
        Grape.disableChecksums = disableChecksums;
    }

    public static synchronized GrapeEngine getInstance() {
        if (instance == null) {
            try {
                instance = (GrapeEngine)Class.forName("groovy.grape.GrapeIvy").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            }
            catch (ReflectiveOperationException reflectiveOperationException) {
                // empty catch block
            }
        }
        return instance;
    }

    public static void grab(String endorsed) {
        GrapeEngine instance;
        if (enableGrapes && (instance = Grape.getInstance()) != null) {
            instance.grab(endorsed);
        }
    }

    public static void grab(Map<String, Object> dependency) {
        GrapeEngine instance;
        if (enableGrapes && (instance = Grape.getInstance()) != null) {
            if (!dependency.containsKey(AUTO_DOWNLOAD_SETTING)) {
                dependency.put(AUTO_DOWNLOAD_SETTING, enableAutoDownload);
            }
            if (!dependency.containsKey(DISABLE_CHECKSUMS_SETTING)) {
                dependency.put(DISABLE_CHECKSUMS_SETTING, disableChecksums);
            }
            instance.grab(dependency);
        }
    }

    public static void grab(final Map<String, Object> args, final Map ... dependencies) {
        if (enableGrapes) {
            AccessController.doPrivileged(new PrivilegedAction<Void>(){

                @Override
                public Void run() {
                    GrapeEngine instance1 = Grape.getInstance();
                    if (instance1 != null) {
                        if (!args.containsKey(Grape.AUTO_DOWNLOAD_SETTING)) {
                            args.put(Grape.AUTO_DOWNLOAD_SETTING, enableAutoDownload);
                        }
                        if (!args.containsKey(Grape.DISABLE_CHECKSUMS_SETTING)) {
                            args.put(Grape.DISABLE_CHECKSUMS_SETTING, disableChecksums);
                        }
                        if (!args.containsKey("calleeDepth")) {
                            args.put("calleeDepth", 5);
                        }
                        instance1.grab(args, dependencies);
                    }
                    return null;
                }
            });
        }
    }

    public static Map<String, Map<String, List<String>>> enumerateGrapes() {
        GrapeEngine instance;
        Map<String, Map<String, List<String>>> grapes = null;
        if (enableGrapes && (instance = Grape.getInstance()) != null) {
            grapes = instance.enumerateGrapes();
        }
        if (grapes == null) {
            return Collections.emptyMap();
        }
        return grapes;
    }

    public static URI[] resolve(Map<String, Object> args, Map ... dependencies) {
        return Grape.resolve(args, null, dependencies);
    }

    public static URI[] resolve(Map<String, Object> args, List depsInfo, Map ... dependencies) {
        GrapeEngine instance;
        URI[] uris = null;
        if (enableGrapes && (instance = Grape.getInstance()) != null) {
            if (!args.containsKey(AUTO_DOWNLOAD_SETTING)) {
                args.put(AUTO_DOWNLOAD_SETTING, enableAutoDownload);
            }
            if (!args.containsKey(DISABLE_CHECKSUMS_SETTING)) {
                args.put(DISABLE_CHECKSUMS_SETTING, disableChecksums);
            }
            uris = instance.resolve(args, depsInfo, dependencies);
        }
        if (uris == null) {
            return EMPTY_URI_ARRAY;
        }
        return uris;
    }

    public static Map[] listDependencies(ClassLoader cl) {
        GrapeEngine instance;
        Map[] maps = null;
        if (enableGrapes && (instance = Grape.getInstance()) != null) {
            maps = instance.listDependencies(cl);
        }
        if (maps == null) {
            return EMPTY_MAP_ARRAY;
        }
        return maps;
    }

    public static void addResolver(Map<String, Object> args) {
        GrapeEngine instance;
        if (enableGrapes && (instance = Grape.getInstance()) != null) {
            instance.addResolver(args);
        }
    }
}

