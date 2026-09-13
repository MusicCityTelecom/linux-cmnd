/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.ivy.Ivy
 *  org.apache.ivy.core.IvyContext
 *  org.apache.ivy.core.cache.ResolutionCacheManager
 *  org.apache.ivy.core.event.IvyListener
 *  org.apache.ivy.core.event.download.PrepareDownloadEvent
 *  org.apache.ivy.core.event.resolve.StartResolveEvent
 *  org.apache.ivy.core.module.descriptor.Configuration
 *  org.apache.ivy.core.module.descriptor.DefaultDependencyArtifactDescriptor
 *  org.apache.ivy.core.module.descriptor.DefaultDependencyDescriptor
 *  org.apache.ivy.core.module.descriptor.DefaultExcludeRule
 *  org.apache.ivy.core.module.descriptor.DefaultModuleDescriptor
 *  org.apache.ivy.core.module.descriptor.DependencyArtifactDescriptor
 *  org.apache.ivy.core.module.descriptor.DependencyDescriptor
 *  org.apache.ivy.core.module.descriptor.ExcludeRule
 *  org.apache.ivy.core.module.descriptor.ModuleDescriptor
 *  org.apache.ivy.core.module.id.ArtifactId
 *  org.apache.ivy.core.module.id.ModuleId
 *  org.apache.ivy.core.module.id.ModuleRevisionId
 *  org.apache.ivy.core.report.ArtifactDownloadReport
 *  org.apache.ivy.core.report.ResolveReport
 *  org.apache.ivy.core.resolve.IvyNode
 *  org.apache.ivy.core.resolve.ResolveOptions
 *  org.apache.ivy.core.settings.IvySettings
 *  org.apache.ivy.plugins.matcher.ExactPatternMatcher
 *  org.apache.ivy.plugins.matcher.PatternMatcher
 *  org.apache.ivy.plugins.resolver.ChainResolver
 *  org.apache.ivy.plugins.resolver.IBiblioResolver
 *  org.apache.ivy.plugins.resolver.ResolverSettings
 *  org.apache.ivy.util.DefaultMessageLogger
 *  org.apache.ivy.util.Message
 *  org.apache.ivy.util.MessageLogger
 *  org.apache.ivy.util.extendable.ExtendableItem
 */
package groovy.grape;

import groovy.grape.GrapeEngine;
import groovy.grape.IvyGrabRecord;
import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.GroovySystem;
import groovy.lang.IntRange;
import groovy.lang.MetaClass;
import groovy.lang.MetaClassRegistry;
import groovy.lang.MetaMethod;
import groovy.lang.Reference;
import groovy.transform.Generated;
import groovy.transform.Internal;
import groovy.transform.NamedParam;
import groovy.transform.NamedParams;
import java.beans.Transient;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.groovy.plugin.GroovyRunner;
import org.apache.groovy.plugin.GroovyRunnerRegistry;
import org.apache.ivy.Ivy;
import org.apache.ivy.core.IvyContext;
import org.apache.ivy.core.cache.ResolutionCacheManager;
import org.apache.ivy.core.event.IvyListener;
import org.apache.ivy.core.event.download.PrepareDownloadEvent;
import org.apache.ivy.core.event.resolve.StartResolveEvent;
import org.apache.ivy.core.module.descriptor.Configuration;
import org.apache.ivy.core.module.descriptor.DefaultDependencyArtifactDescriptor;
import org.apache.ivy.core.module.descriptor.DefaultDependencyDescriptor;
import org.apache.ivy.core.module.descriptor.DefaultExcludeRule;
import org.apache.ivy.core.module.descriptor.DefaultModuleDescriptor;
import org.apache.ivy.core.module.descriptor.DependencyArtifactDescriptor;
import org.apache.ivy.core.module.descriptor.DependencyDescriptor;
import org.apache.ivy.core.module.descriptor.ExcludeRule;
import org.apache.ivy.core.module.descriptor.ModuleDescriptor;
import org.apache.ivy.core.module.id.ArtifactId;
import org.apache.ivy.core.module.id.ModuleId;
import org.apache.ivy.core.module.id.ModuleRevisionId;
import org.apache.ivy.core.report.ArtifactDownloadReport;
import org.apache.ivy.core.report.ResolveReport;
import org.apache.ivy.core.resolve.IvyNode;
import org.apache.ivy.core.resolve.ResolveOptions;
import org.apache.ivy.core.settings.IvySettings;
import org.apache.ivy.plugins.matcher.ExactPatternMatcher;
import org.apache.ivy.plugins.matcher.PatternMatcher;
import org.apache.ivy.plugins.resolver.ChainResolver;
import org.apache.ivy.plugins.resolver.IBiblioResolver;
import org.apache.ivy.plugins.resolver.ResolverSettings;
import org.apache.ivy.util.DefaultMessageLogger;
import org.apache.ivy.util.Message;
import org.apache.ivy.util.MessageLogger;
import org.apache.ivy.util.extendable.ExtendableItem;
import org.codehaus.groovy.reflection.CachedClass;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.reflection.ReflectionUtils;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.runtime.DefaultGroovyStaticMethods;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.GeneratedLambda;
import org.codehaus.groovy.runtime.IOGroovyMethods;
import org.codehaus.groovy.runtime.ResourceGroovyMethods;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.StringGroovyMethods;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.m12n.ExtensionModuleScanner;
import org.codehaus.groovy.runtime.metaclass.MetaClassRegistryImpl;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GrapeIvy
implements GrapeEngine,
GroovyObject {
    private static final String METAINF_PREFIX = "META-INF/services/";
    private static final String RUNNER_PROVIDER_CONFIG;
    private static final List<String> DEFAULT_CONF;
    private static final Map<String, Set<String>> MUTUALLY_EXCLUSIVE_KEYS;
    private boolean enableGrapes;
    private Ivy ivyInstance;
    private IvySettings settings;
    private Set<String> downloadedArtifacts;
    private Set<String> resolvedDependencies;
    private final Map<ClassLoader, Set<IvyGrabRecord>> loadedDeps;
    private final Set<IvyGrabRecord> grabRecordsForCurrDependencies;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    public GrapeIvy() {
        Ivy ivy;
        IvySettings ivySettings;
        MetaClass metaClass;
        LinkedHashSet linkedHashSet;
        WeakHashMap weakHashMap;
        boolean bl;
        this.enableGrapes = bl = true;
        List list = ScriptBytecodeAdapter.createList(new Object[0]);
        this.downloadedArtifacts = (Set)ScriptBytecodeAdapter.castToType(list, Set.class);
        List list2 = ScriptBytecodeAdapter.createList(new Object[0]);
        this.resolvedDependencies = (Set)ScriptBytecodeAdapter.castToType(list2, Set.class);
        this.loadedDeps = weakHashMap = (WeakHashMap)ScriptBytecodeAdapter.asType(ScriptBytecodeAdapter.createList(new Object[0]), WeakHashMap.class);
        this.grabRecordsForCurrDependencies = linkedHashSet = (LinkedHashSet)ScriptBytecodeAdapter.asType(ScriptBytecodeAdapter.createList(new Object[0]), LinkedHashSet.class);
        this.metaClass = metaClass = this.$getStaticMetaClass();
        Message.setDefaultLogger((MessageLogger)new DefaultMessageLogger(DefaultTypeTransformation.intUnbox(ScriptBytecodeAdapter.asType(System.getProperty("ivy.message.logger.level", "-1"), Integer.TYPE))));
        this.settings = ivySettings = new IvySettings();
        this.settings.setVariable("user.home.url", (String)ScriptBytecodeAdapter.asType(new File(System.getProperty("user.home")).toURI().toURL(), String.class));
        File grapeConfig = this.getLocalGrapeConfig();
        if (grapeConfig.exists()) {
            try {
                this.settings.load(grapeConfig);
            }
            catch (ParseException e) {
                DefaultGroovyMethods.println(System.err, (Object)new GStringImpl(new Object[]{grapeConfig.getCanonicalPath(), e.getMessage()}, new String[]{"Local Ivy config file '", "' appears corrupt - ignoring it and using default config instead\nError was: ", ""}));
                this.settings.load(GrapeIvy.class.getResource("defaultGrapeConfig.xml"));
            }
        } else {
            this.settings.load(GrapeIvy.class.getResource("defaultGrapeConfig.xml"));
        }
        this.settings.setDefaultCache(this.getGrapeCacheDir());
        this.settings.setVariable("ivy.default.configuration.m2compatible", "true");
        this.ivyInstance = ivy = Ivy.newInstance((IvySettings)this.settings);
        IvyContext.getContext().setIvy(this.ivyInstance);
    }

    private static Map<String, Set<String>> processGrabArgs(List<List<String>> grabArgs) {
        CallSite[] callSiteArray = GrapeIvy.$getCallSiteArray();
        public final class _processGrabArgs_closure1
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;
            private static /* synthetic */ SoftReference $callSiteArray;

            public _processGrabArgs_closure1(Object _outerInstance, Object _thisObject) {
                CallSite[] callSiteArray = _processGrabArgs_closure1.$getCallSiteArray();
                super(_outerInstance, _thisObject);
            }

            /*
             * WARNING - void declaration
             */
            public Object doCall(Map m, List g) {
                void var2_2;
                Reference<Map> m2 = new Reference<Map>(m);
                Reference<void> g2 = new Reference<void>(var2_2);
                CallSite[] callSiteArray = _processGrabArgs_closure1.$getCallSiteArray();
                public final class _closure17
                extends Closure
                implements GeneratedClosure {
                    private /* synthetic */ Reference m;
                    private /* synthetic */ Reference g;
                    private static /* synthetic */ ClassInfo $staticClassInfo;
                    public static transient /* synthetic */ boolean __$stMC;
                    private static /* synthetic */ SoftReference $callSiteArray;

                    public _closure17(Object _outerInstance, Object _thisObject, Reference m, Reference g) {
                        Reference reference;
                        Reference reference2;
                        CallSite[] callSiteArray = _closure17.$getCallSiteArray();
                        super(_outerInstance, _thisObject);
                        this.m = reference2 = m;
                        this.g = reference = g;
                    }

                    public Object doCall(Object a) {
                        CallSite[] callSiteArray = _closure17.$getCallSiteArray();
                        Set set = (Set)ScriptBytecodeAdapter.asType(callSiteArray[0].call(this.g.get(), a), Set.class);
                        callSiteArray[1].call(this.m.get(), a, set);
                        return set;
                    }

                    @Generated
                    public Map getM() {
                        CallSite[] callSiteArray = _closure17.$getCallSiteArray();
                        return (Map)ScriptBytecodeAdapter.castToType(this.m.get(), Map.class);
                    }

                    @Generated
                    public List getG() {
                        CallSite[] callSiteArray = _closure17.$getCallSiteArray();
                        return (List)ScriptBytecodeAdapter.castToType(this.g.get(), List.class);
                    }

                    protected /* synthetic */ MetaClass $getStaticMetaClass() {
                        if (this.getClass() != _closure17.class) {
                            return ScriptBytecodeAdapter.initMetaClass(this);
                        }
                        ClassInfo classInfo = $staticClassInfo;
                        if (classInfo == null) {
                            $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                        }
                        return classInfo.getMetaClass();
                    }

                    public /* synthetic */ MethodHandles.Lookup $getLookup() {
                        return MethodHandles.lookup();
                    }

                    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
                        stringArray[0] = "minus";
                        stringArray[1] = "putAt";
                    }

                    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                        String[] stringArray = new String[2];
                        _closure17.$createCallSiteArray_1(stringArray);
                        return new CallSiteArray(_closure17.class, stringArray);
                    }

                    private static /* synthetic */ CallSite[] $getCallSiteArray() {
                        CallSiteArray callSiteArray;
                        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                            callSiteArray = _closure17.$createCallSiteArray();
                            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                        }
                        return callSiteArray.array;
                    }
                }
                callSiteArray[0].call((Object)((List)g2.get()), new _closure17(this, this.getThisObject(), m2, g2));
                return m2.get();
            }

            /*
             * WARNING - void declaration
             */
            @Generated
            public Object call(Map m, List g) {
                void var2_2;
                Reference<Map> m2 = new Reference<Map>(m);
                Reference<void> g2 = new Reference<void>(var2_2);
                CallSite[] callSiteArray = _processGrabArgs_closure1.$getCallSiteArray();
                return callSiteArray[1].callCurrent(this, m2.get(), (List)g2.get());
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _processGrabArgs_closure1.class) {
                    return ScriptBytecodeAdapter.initMetaClass(this);
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                }
                return classInfo.getMetaClass();
            }

            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                return MethodHandles.lookup();
            }

            private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
                stringArray[0] = "each";
                stringArray[1] = "doCall";
            }

            private static /* synthetic */ CallSiteArray $createCallSiteArray() {
                String[] stringArray = new String[2];
                _processGrabArgs_closure1.$createCallSiteArray_1(stringArray);
                return new CallSiteArray(_processGrabArgs_closure1.class, stringArray);
            }

            private static /* synthetic */ CallSite[] $getCallSiteArray() {
                CallSiteArray callSiteArray;
                if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
                    callSiteArray = _processGrabArgs_closure1.$createCallSiteArray();
                    $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
                }
                return callSiteArray.array;
            }
        }
        return (Map)ScriptBytecodeAdapter.castToType(callSiteArray[0].call(grabArgs, ScriptBytecodeAdapter.createMap(new Object[0]), new _processGrabArgs_closure1(GrapeIvy.class, GrapeIvy.class)), Map.class);
    }

    public File getGroovyRoot() {
        File file;
        File file2;
        String root = System.getProperty("groovy.root");
        File groovyRoot = null;
        groovyRoot = root == null ? (file2 = new File(System.getProperty("user.home"), ".groovy")) : (file = new File(root));
        try {
            File file3;
            groovyRoot = file3 = groovyRoot.getCanonicalFile();
        }
        catch (IOException ignore) {
        }
        return groovyRoot;
    }

    public File getGrapeDir() {
        String root = System.getProperty("grape.root");
        if (root == null) {
            return this.getGroovyRoot();
        }
        File grapeRoot = new File(root);
        try {
            File file;
            grapeRoot = file = grapeRoot.getCanonicalFile();
        }
        catch (IOException ignore) {
        }
        return grapeRoot;
    }

    public File getGrapeCacheDir() {
        File cache = new File(this.getGrapeDir(), "grapes");
        if (!cache.exists()) {
            cache.mkdirs();
        } else if (!cache.isDirectory()) {
            throw (Throwable)new RuntimeException(ShortTypeHandling.castToString(new GStringImpl(new Object[]{cache}, new String[]{"The grape cache dir ", " is not a directory"})));
        }
        return cache;
    }

    public File getLocalGrapeConfig() {
        String grapeConfig = System.getProperty("grape.config");
        String string = grapeConfig;
        if (string == null ? false : DefaultTypeTransformation.booleanUnbox(string)) {
            return new File(grapeConfig);
        }
        return new File(this.getGrapeDir(), "grapeConfig.xml");
    }

    public ClassLoader chooseClassLoader(Map args) {
        ClassLoader loader = (ClassLoader)ScriptBytecodeAdapter.castToType(args.get("classLoader"), ClassLoader.class);
        if (!this.isValidTargetClassLoader(loader)) {
            ClassLoader classLoader;
            int n;
            Object v = args.get("refObject");
            Class clazz = v != null ? v.getClass() : null;
            Class caller = DefaultTypeTransformation.booleanUnbox(clazz) ? clazz : ReflectionUtils.getCallingClass((n = DefaultTypeTransformation.intUnbox(args.get("calleeDepth"))) != 0 ? n : 1);
            Class clazz2 = caller;
            loader = classLoader = clazz2 != null ? clazz2.getClassLoader() : null;
            while (DefaultTypeTransformation.booleanUnbox(loader) && !this.isValidTargetClassLoader(loader)) {
                ClassLoader classLoader2;
                loader = classLoader2 = loader.getParent();
            }
            if (!this.isValidTargetClassLoader(loader)) {
                throw (Throwable)new RuntimeException("No suitable ClassLoader found for grab");
            }
        }
        return loader;
    }

    private boolean isValidTargetClassLoader(ClassLoader loader) {
        ClassLoader classLoader = loader;
        return this.isValidTargetClassLoaderClass(classLoader != null ? classLoader.getClass() : null);
    }

    private boolean isValidTargetClassLoaderClass(Class loaderClass) {
        return loaderClass != null && (ScriptBytecodeAdapter.compareEqual(loaderClass.getName(), "groovy.lang.GroovyClassLoader") || ScriptBytecodeAdapter.compareEqual(loaderClass.getName(), "org.codehaus.groovy.tools.RootLoader") || this.isValidTargetClassLoaderClass(loaderClass.getSuperclass()));
    }

    public IvyGrabRecord createGrabRecord(Map dep) {
        Object v;
        Object v2;
        Object v3;
        Object v4;
        Object v5;
        Object v6;
        Object v7;
        Object v8;
        Object v9;
        Object v10 = dep.get("module");
        String module = ShortTypeHandling.castToString(DefaultTypeTransformation.booleanUnbox(v10) ? v10 : (DefaultTypeTransformation.booleanUnbox(v9 = dep.get("artifactId")) ? v9 : dep.get("artifact")));
        String string = module;
        if (!(string == null ? false : DefaultTypeTransformation.booleanUnbox(string))) {
            throw (Throwable)new RuntimeException("grab requires at least a module: or artifactId: or artifact: argument");
        }
        Object v11 = dep.get("group");
        String groupId = ShortTypeHandling.castToString(DefaultTypeTransformation.booleanUnbox(v11) ? v11 : (DefaultTypeTransformation.booleanUnbox(v8 = dep.get("groupId")) ? v8 : (DefaultTypeTransformation.booleanUnbox(v7 = dep.get("organisation")) ? v7 : (DefaultTypeTransformation.booleanUnbox(v6 = dep.get("organization")) ? v6 : (DefaultTypeTransformation.booleanUnbox(v5 = dep.get("org")) ? v5 : "")))));
        Object v12 = dep.get("version");
        String version = ShortTypeHandling.castToString(DefaultTypeTransformation.booleanUnbox(v12) ? v12 : (DefaultTypeTransformation.booleanUnbox(v4 = dep.get("revision")) ? v4 : (DefaultTypeTransformation.booleanUnbox(v3 = dep.get("rev")) ? v3 : "*")));
        if (ScriptBytecodeAdapter.compareEqual(version, Character.valueOf('*'))) {
            String string2;
            version = string2 = "latest.default";
        }
        String classifier = ShortTypeHandling.castToString(DefaultTypeTransformation.booleanUnbox(v2 = dep.get("classifier")) ? v2 : null);
        Object v13 = dep.get("ext");
        String ext = ShortTypeHandling.castToString(DefaultTypeTransformation.booleanUnbox(v13) ? v13 : (DefaultTypeTransformation.booleanUnbox(v = dep.get("type")) ? v : ""));
        Object v14 = dep.get("type");
        String type = ShortTypeHandling.castToString(DefaultTypeTransformation.booleanUnbox(v14) ? v14 : "");
        ModuleRevisionId mrid = ModuleRevisionId.newInstance((String)groupId, (String)module, (String)version);
        boolean force = DefaultTypeTransformation.booleanUnbox(dep.containsKey("force") ? dep.get("force") : Boolean.valueOf(true));
        boolean changing = DefaultTypeTransformation.booleanUnbox(dep.containsKey("changing") ? dep.get("changing") : Boolean.valueOf(false));
        boolean transitive = DefaultTypeTransformation.booleanUnbox(dep.containsKey("transitive") ? dep.get("transitive") : Boolean.valueOf(true));
        IvyGrabRecord ivyGrabRecord = new IvyGrabRecord();
        ModuleRevisionId moduleRevisionId = mrid;
        ivyGrabRecord.setMrid(moduleRevisionId);
        List<String> list = this.getConfList(dep);
        ivyGrabRecord.setConf(list);
        boolean bl = force;
        ivyGrabRecord.setForce(bl);
        boolean bl2 = changing;
        ivyGrabRecord.setChanging(bl2);
        boolean bl3 = transitive;
        ivyGrabRecord.setTransitive(bl3);
        String string3 = ext;
        ivyGrabRecord.setExt(string3);
        String string4 = type;
        ivyGrabRecord.setType(string4);
        String string5 = classifier;
        ivyGrabRecord.setClassifier(string5);
        return ivyGrabRecord;
    }

    private List<String> getConfList(Map dep) {
        Object object;
        Object object2;
        CallSite[] callSiteArray = GrapeIvy.$getCallSiteArray();
        List<String> list = callSiteArray[1].callGetProperty(dep);
        Object conf = DefaultTypeTransformation.booleanUnbox(list) ? list : (DefaultTypeTransformation.booleanUnbox(object2 = callSiteArray[2].callGetProperty(dep)) ? object2 : (DefaultTypeTransformation.booleanUnbox(object = callSiteArray[3].callGetProperty(dep)) ? object : DEFAULT_CONF));
        if (conf instanceof String) {
            Object object3;
            if (DefaultTypeTransformation.booleanUnbox(callSiteArray[4].call(conf, "[")) && DefaultTypeTransformation.booleanUnbox(callSiteArray[5].call(conf, "]"))) {
                Object object4;
                conf = object4 = callSiteArray[6].call(conf, ScriptBytecodeAdapter.createRange(1, -2, false, false));
            }
            conf = object3 = callSiteArray[7].call(callSiteArray[8].call(conf, ","));
        }
        return (List)ScriptBytecodeAdapter.castToType(conf, List.class);
    }

    @Override
    public Object grab(String endorsedModule) {
        return this.grab(ScriptBytecodeAdapter.createMap(new Object[]{"group", "groovy.endorsed", "module", endorsedModule, "version", GroovySystem.getVersion()}));
    }

    @Override
    public Object grab(Map args) {
        Object v;
        Object v2 = args.get("calleeDepth");
        Object v3 = v = DefaultTypeTransformation.booleanUnbox(v2) ? v2 : Integer.valueOf(DEFAULT_CALLEE_DEPTH + 1);
        args.put("calleeDepth", v3);
        return this.grab(args, args);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Object grab(Map args, Map ... dependencies) {
        ClassLoader loader = null;
        this.grabRecordsForCurrDependencies.clear();
        try {
            ClassLoader classLoader;
            Object[] objectArray = new Object[6];
            objectArray[0] = "refObject";
            objectArray[1] = args.remove("refObject");
            objectArray[2] = "classLoader";
            objectArray[3] = args.remove("classLoader");
            objectArray[4] = "calleeDepth";
            Object v = args.get("calleeDepth");
            objectArray[5] = DefaultTypeTransformation.booleanUnbox(v) ? v : Integer.valueOf(DEFAULT_CALLEE_DEPTH);
            loader = classLoader = this.chooseClassLoader(ScriptBytecodeAdapter.createMap(objectArray));
            ClassLoader classLoader2 = loader;
            if (classLoader2 == null) {
                return null;
            }
            boolean bl = DefaultTypeTransformation.booleanUnbox(classLoader2);
            if (!bl) {
                return null;
            }
            boolean bl2 = false;
            if (bl2) {
                return null;
            }
        }
        catch (Exception e) {
            Set<IvyGrabRecord> grabRecordsForCurrLoader = this.getLoadedDepsForLoader(loader);
            grabRecordsForCurrLoader.removeAll(this.grabRecordsForCurrDependencies);
            this.grabRecordsForCurrDependencies.clear();
            Object v = args.get("noExceptions");
            if (!(v == null ? false : DefaultTypeTransformation.booleanUnbox(v))) throw (Throwable)e;
            Exception exception = e;
            return exception;
        }
        {
            URI[] uris = this.resolve(loader, args, dependencies);
            URI uri2 = null;
            URI[] uRIArray = uris;
            if (uris != null) {
                for (URI uri2 : uRIArray) {
                    this.addURL(loader, uri2);
                }
            }
            boolean runnerServicesFound = false;
            URI uri32 = null;
            URI[] uRIArray2 = uris;
            if (uris != null) {
                for (URI uri32 : uRIArray2) {
                    boolean bl;
                    File file = new File(uri32);
                    this.processCategoryMethods(loader, file);
                    Collection<String> services = this.processMetaInfServices(loader, file);
                    if (!(!runnerServicesFound)) continue;
                    runnerServicesFound = bl = services.contains(RUNNER_PROVIDER_CONFIG);
                }
            }
            if (!runnerServicesFound) return null;
            GroovyRunnerRegistry.getInstance().load(loader);
            return null;
        }
    }

    private void addURL(ClassLoader loader, URI uri) {
        CallSite[] callSiteArray = GrapeIvy.$getCallSiteArray();
        callSiteArray[9].call((Object)loader, callSiteArray[10].call(uri));
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Object processCategoryMethods(ClassLoader loader, File file) {
        Throwable __$$primaryExc1;
        JarFile jar;
        block56: {
            Object v13;
            Throwable __$$primaryExc0;
            InputStream is;
            Properties props;
            if (!file.getName().toLowerCase().endsWith(".jar")) return null;
            MetaClassRegistry mcRegistry = GroovySystem.getMetaClassRegistry();
            if (!(mcRegistry instanceof MetaClassRegistryImpl)) return null;
            try {
                jar = new JarFile(file);
                __$$primaryExc1 = null;
                try {
                    ZipEntry entry = jar.getEntry(ExtensionModuleScanner.MODULE_META_INF_FILE);
                    ZipEntry zipEntry = entry;
                    if (!(zipEntry == null ? false : DefaultTypeTransformation.booleanUnbox(zipEntry))) {
                        ZipEntry zipEntry2;
                        entry = zipEntry2 = jar.getEntry(ExtensionModuleScanner.LEGACY_MODULE_META_INF_FILE);
                    }
                    ZipEntry zipEntry3 = entry;
                    if (!(zipEntry3 == null ? false : DefaultTypeTransformation.booleanUnbox(zipEntry3))) break block56;
                    props = new Properties();
                    is = jar.getInputStream(entry);
                    __$$primaryExc0 = null;
                    try {
                        try {
                            props.load(is);
                        }
                        catch (Throwable __$$t0) {
                            Throwable throwable;
                            __$$primaryExc0 = throwable = __$$t0;
                            throw __$$t0;
                        }
                    }
                    catch (Throwable throwable) {
                        Object v4;
                        if (__$$primaryExc0 != null) {
                            try {
                                Object v2;
                                InputStream inputStream = is;
                                if (inputStream != null) {
                                    inputStream.close();
                                    v2 = null;
                                    throw throwable;
                                }
                                v2 = null;
                                throw throwable;
                            }
                            catch (Throwable __$$suppressedExc0) {
                                Object v3;
                                Throwable throwable2 = __$$primaryExc0;
                                if (throwable2 != null) {
                                    throwable2.addSuppressed(__$$suppressedExc0);
                                    v3 = null;
                                    throw throwable;
                                }
                                v3 = null;
                                throw throwable;
                            }
                        }
                        InputStream inputStream = is;
                        if (inputStream != null) {
                            inputStream.close();
                            v4 = null;
                            throw throwable;
                        }
                        v4 = null;
                        throw throwable;
                    }
                }
                catch (Throwable __$$t1) {
                    Throwable throwable;
                    __$$primaryExc1 = throwable = __$$t1;
                    throw __$$t1;
                }
                catch (Throwable throwable) {
                    Object v7;
                    if (__$$primaryExc1 != null) {
                        try {
                            Object v5;
                            JarFile jarFile = jar;
                            if (jarFile != null) {
                                jarFile.close();
                                v5 = null;
                                throw throwable;
                            }
                            v5 = null;
                            throw throwable;
                        }
                        catch (Throwable __$$suppressedExc1) {
                            Object v6;
                            Throwable throwable3 = __$$primaryExc1;
                            if (throwable3 != null) {
                                throwable3.addSuppressed(__$$suppressedExc1);
                                v6 = null;
                                throw throwable;
                            }
                            v6 = null;
                            throw throwable;
                        }
                    }
                    JarFile jarFile = jar;
                    if (jarFile != null) {
                        jarFile.close();
                        v7 = null;
                        throw throwable;
                    }
                    v7 = null;
                    throw throwable;
                }
            }
            catch (ZipException e) {
                throw (Throwable)new RuntimeException(ShortTypeHandling.castToString(new GStringImpl(new Object[]{file}, new String[]{"Grape could not load jar '", "'"})), e);
            }
            catch (Throwable throwable) {
                throw throwable;
            }
            if (__$$primaryExc0 != null) {
                try {
                    Object v8;
                    InputStream inputStream = is;
                    if (inputStream != null) {
                        inputStream.close();
                        v8 = null;
                    }
                    v8 = null;
                }
                catch (Throwable __$$suppressedExc0) {
                    Object v9;
                    Throwable throwable = __$$primaryExc0;
                    if (throwable != null) {
                        throwable.addSuppressed(__$$suppressedExc0);
                        v9 = null;
                    }
                    v9 = null;
                }
            } else {
                Object v10;
                InputStream inputStream = is;
                if (inputStream != null) {
                    inputStream.close();
                    v10 = null;
                } else {
                    v10 = null;
                }
            }
            LinkedHashMap metaMethods = (LinkedHashMap)ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.createMap(new Object[0]), LinkedHashMap.class);
            ((MetaClassRegistryImpl)ScriptBytecodeAdapter.castToType(mcRegistry, MetaClassRegistryImpl.class)).registerExtensionModuleFromProperties(props, loader, metaMethods);
            public final class _processCategoryMethods_closure2
            extends Closure
            implements GeneratedClosure {
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;

                public _processCategoryMethods_closure2(Object _outerInstance, Object _thisObject) {
                    super(_outerInstance, _thisObject);
                }

                public List<Void> doCall(CachedClass c, List<MetaMethod> methods) {
                    Reference<CachedClass> c2 = new Reference<CachedClass>(c);
                    Reference classesToBeUpdated = new Reference(DefaultGroovyMethods.toSet(ScriptBytecodeAdapter.createList(new Object[]{c2.get()})));
                    public final class _closure18
                    extends Closure
                    implements GeneratedClosure {
                        private /* synthetic */ Reference c;
                        private /* synthetic */ Reference classesToBeUpdated;
                        private static /* synthetic */ ClassInfo $staticClassInfo;
                        public static transient /* synthetic */ boolean __$stMC;

                        public _closure18(Object _outerInstance, Object _thisObject, Reference c, Reference classesToBeUpdated) {
                            super(_outerInstance, _thisObject);
                            Reference reference;
                            Reference reference2;
                            this.c = reference2 = c;
                            this.classesToBeUpdated = reference = classesToBeUpdated;
                        }

                        public Set<CachedClass> doCall(ClassInfo info) {
                            if (((CachedClass)this.c.get()).getTheClass().isAssignableFrom(info.getCachedClass().getTheClass())) {
                                return DefaultGroovyMethods.leftShift((Set)ScriptBytecodeAdapter.castToType(this.classesToBeUpdated.get(), Set.class), info.getCachedClass());
                            }
                            return null;
                        }

                        @Generated
                        public Set<CachedClass> call(ClassInfo info) {
                            return this.doCall(info);
                        }

                        @Generated
                        public CachedClass getC() {
                            return (CachedClass)ScriptBytecodeAdapter.castToType(this.c.get(), CachedClass.class);
                        }

                        @Generated
                        public Set getClassesToBeUpdated() {
                            return (Set)ScriptBytecodeAdapter.castToType(this.classesToBeUpdated.get(), Set.class);
                        }

                        protected /* synthetic */ MetaClass $getStaticMetaClass() {
                            if (this.getClass() != _closure18.class) {
                                return ScriptBytecodeAdapter.initMetaClass(this);
                            }
                            ClassInfo classInfo = $staticClassInfo;
                            if (classInfo == null) {
                                $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                            }
                            return classInfo.getMetaClass();
                        }

                        public /* synthetic */ MethodHandles.Lookup $getLookup() {
                            return MethodHandles.lookup();
                        }
                    }
                    ClassInfo.onAllClassInfo((ClassInfo.ClassInfoAction)ScriptBytecodeAdapter.castToType(new _closure18(this, this.getThisObject(), c2, classesToBeUpdated), ClassInfo.ClassInfoAction.class));
                    ArrayList<Void> arrayList = new ArrayList<Void>();
                    if (classesToBeUpdated.get() != null) {
                        Set set = classesToBeUpdated.get();
                        Iterator iterator = set != null ? set.iterator() : null;
                        CachedClass for$it$1 = null;
                        Iterator iterator2 = iterator;
                        if (iterator2 != null) {
                            while (iterator2.hasNext()) {
                                Object v1;
                                for$it$1 = (CachedClass)ScriptBytecodeAdapter.castToType(iterator2.next(), CachedClass.class);
                                CachedClass cachedClass = for$it$1;
                                if (cachedClass != null) {
                                    cachedClass.addNewMopMethods(methods);
                                    v1 = null;
                                } else {
                                    v1 = null;
                                }
                                arrayList.add(v1);
                            }
                        }
                    }
                    return arrayList;
                }

                @Generated
                public List<Void> call(CachedClass c, List<MetaMethod> methods) {
                    Reference<CachedClass> c2 = new Reference<CachedClass>(c);
                    return this.doCall(c2.get(), methods);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (this.getClass() != _processCategoryMethods_closure2.class) {
                        return ScriptBytecodeAdapter.initMetaClass(this);
                    }
                    ClassInfo classInfo = $staticClassInfo;
                    if (classInfo == null) {
                        $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                    }
                    return classInfo.getMetaClass();
                }

                public /* synthetic */ MethodHandles.Lookup $getLookup() {
                    return MethodHandles.lookup();
                }
            }
            Map map = DefaultGroovyMethods.each(metaMethods, new _processCategoryMethods_closure2(this, this));
            if (__$$primaryExc1 != null) {
                try {
                    Object v11;
                    JarFile jarFile = jar;
                    if (jarFile != null) {
                        jarFile.close();
                        v11 = null;
                        return map;
                    }
                    v11 = null;
                    return map;
                }
                catch (Throwable __$$suppressedExc1) {
                    Object v12;
                    Throwable throwable = __$$primaryExc1;
                    if (throwable != null) {
                        throwable.addSuppressed(__$$suppressedExc1);
                        v12 = null;
                        return map;
                    }
                    v12 = null;
                }
                return map;
            }
            JarFile jarFile = jar;
            if (jarFile != null) {
                jarFile.close();
                v13 = null;
                return map;
            }
            v13 = null;
            return map;
        }
        if (__$$primaryExc1 != null) {
            try {
                JarFile jarFile = jar;
                if (jarFile == null) return null;
                jarFile.close();
                return null;
            }
            catch (Throwable __$$suppressedExc1) {
                Throwable throwable = __$$primaryExc1;
                if (throwable == null) return null;
                throwable.addSuppressed(__$$suppressedExc1);
                return null;
            }
        }
        JarFile jarFile = jar;
        if (jarFile == null) return null;
        jarFile.close();
        return null;
    }

    public void processOtherServices(ClassLoader loader, File f) {
        this.processMetaInfServices(loader, f);
    }

    /*
     * Exception decompiling
     */
    private Collection<String> processMetaInfServices(ClassLoader loader, File f) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public void processSerializedCategoryMethods(InputStream is) {
        public final class _processSerializedCategoryMethods_closure3
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;

            public _processSerializedCategoryMethods_closure3(Object _outerInstance, Object _thisObject) {
                super(_outerInstance, _thisObject);
            }

            public Object doCall(Object it) {
                System.err.println(((String)it).trim());
                return null;
            }

            @Generated
            public Object call(Object args) {
                return this.doCall(args);
            }

            @Override
            @Generated
            public Object call() {
                return this.doCall(null);
            }

            @Generated
            public Object doCall() {
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _processSerializedCategoryMethods_closure3.class) {
                    return ScriptBytecodeAdapter.initMetaClass(this);
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                }
                return classInfo.getMetaClass();
            }

            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                return MethodHandles.lookup();
            }
        }
        DefaultGroovyMethods.each(StringGroovyMethods.readLines(IOGroovyMethods.getText(is)), (Closure)new _processSerializedCategoryMethods_closure3(this, this));
    }

    /*
     * WARNING - void declaration
     */
    public void processRunners(InputStream is, String name, ClassLoader loader) {
        void var3_3;
        Reference<String> name2 = new Reference<String>(name);
        Reference<void> loader2 = new Reference<void>(var3_3);
        Reference<GroovyRunnerRegistry> registry = new Reference<GroovyRunnerRegistry>(GroovyRunnerRegistry.getInstance());
        ArrayList<String> arrayList = new ArrayList<String>();
        List<String> list = StringGroovyMethods.readLines(IOGroovyMethods.getText(is));
        if (list != null) {
            List<String> list2 = list;
            Iterator<String> iterator = list2 != null ? list2.iterator() : null;
            String for$it$37 = null;
            Iterator<String> iterator2 = iterator;
            if (iterator2 != null) {
                while (iterator2.hasNext()) {
                    for$it$37 = ShortTypeHandling.castToString(iterator2.next());
                    String string = for$it$37;
                    arrayList.add(string != null ? string.trim() : null);
                }
            }
        }
        public final class _processRunners_closure4
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference registry;
            private /* synthetic */ Reference name;
            private /* synthetic */ Reference loader;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;

            public _processRunners_closure4(Object _outerInstance, Object _thisObject, Reference registry, Reference name, Reference loader) {
                super(_outerInstance, _thisObject);
                Reference reference;
                Reference reference2;
                Reference reference3;
                this.registry = reference3 = registry;
                this.name = reference2 = name;
                this.loader = reference = loader;
            }

            public GroovyRunner doCall(String line) {
                if (!line.isEmpty() && ScriptBytecodeAdapter.compareNotEqual(StringGroovyMethods.getAt(line, 0), Character.valueOf('#'))) {
                    GroovyRunner groovyRunner = (GroovyRunner)ScriptBytecodeAdapter.castToType(DefaultGroovyMethods.newInstance(((ClassLoader)this.loader.get()).loadClass(line)), GroovyRunner.class);
                    ScriptBytecodeAdapter.invokeMethodN(_processRunners_closure4.class, this.registry.get(), "putAt", new Object[]{this.name.get(), groovyRunner});
                    GroovyRunner groovyRunner2 = groovyRunner;
                    try {
                        return groovyRunner2;
                    }
                    catch (Exception e) {
                        throw (Throwable)new IllegalStateException(ShortTypeHandling.castToString(new GStringImpl(new Object[]{line}, new String[]{"Error registering runner class '", "'"})), e);
                    }
                }
                return null;
            }

            @Generated
            public GroovyRunner call(String line) {
                return this.doCall(line);
            }

            @Generated
            public GroovyRunnerRegistry getRegistry() {
                return (GroovyRunnerRegistry)ScriptBytecodeAdapter.castToType(this.registry.get(), GroovyRunnerRegistry.class);
            }

            @Generated
            public String getName() {
                return ShortTypeHandling.castToString(this.name.get());
            }

            @Generated
            public ClassLoader getLoader() {
                return (ClassLoader)ScriptBytecodeAdapter.castToType(this.loader.get(), ClassLoader.class);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _processRunners_closure4.class) {
                    return ScriptBytecodeAdapter.initMetaClass(this);
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                }
                return classInfo.getMetaClass();
            }

            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                return MethodHandles.lookup();
            }
        }
        DefaultGroovyMethods.each(arrayList, (Closure)new _processRunners_closure4(this, this, registry, name2, loader2));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public ResolveReport getDependencies(Map args, IvyGrabRecord ... grabRecords) {
        ResolutionCacheManager cacheManager = this.ivyInstance.getResolutionCacheManager();
        long millis = System.currentTimeMillis();
        DefaultModuleDescriptor md = new DefaultModuleDescriptor(ModuleRevisionId.newInstance((String)"caller", (String)"all-caller", (String)StringGroovyMethods.plus("working", (CharSequence)StringGroovyMethods.getAt(Long.valueOf(millis).toString(), new IntRange(true, true, -2, -1)))), "integration", null, true);
        ((DefaultModuleDescriptor)ScriptBytecodeAdapter.castToType(md, DefaultModuleDescriptor.class)).addConfiguration(new Configuration("default"));
        ((DefaultModuleDescriptor)ScriptBytecodeAdapter.castToType(md, DefaultModuleDescriptor.class)).setLastModified(millis);
        this.addExcludesIfNeeded(args, (DefaultModuleDescriptor)ScriptBytecodeAdapter.castToType(md, DefaultModuleDescriptor.class));
        Reference<Object> grabRecord = new Reference<Object>(null);
        IvyGrabRecord[] ivyGrabRecordArray = grabRecords;
        if (grabRecords != null) {
            int n = ivyGrabRecordArray.length;
            int n2 = 0;
            while (n2 < n) {
                String string;
                grabRecord.set(ivyGrabRecordArray[n2]);
                ++n2;
                List list = ((IvyGrabRecord)grabRecord.get()).getConf();
                List confs = DefaultTypeTransformation.booleanUnbox(list) ? list : ScriptBytecodeAdapter.createList(new Object[]{"*"});
                public final class _getDependencies_closure5
                extends Closure
                implements GeneratedClosure {
                    private /* synthetic */ Reference grabRecord;
                    private static /* synthetic */ ClassInfo $staticClassInfo;
                    public static transient /* synthetic */ boolean __$stMC;

                    public _getDependencies_closure5(Object _outerInstance, Object _thisObject, Reference grabRecord) {
                        super(_outerInstance, _thisObject);
                        Reference reference;
                        this.grabRecord = reference = grabRecord;
                    }

                    public Boolean doCall(Object it) {
                        return ScriptBytecodeAdapter.compareEqual(((DependencyDescriptor)it).getDependencyRevisionId(), ((IvyGrabRecord)this.grabRecord.get()).getMrid());
                    }

                    @Generated
                    public IvyGrabRecord getGrabRecord() {
                        return (IvyGrabRecord)ScriptBytecodeAdapter.castToType(this.grabRecord.get(), IvyGrabRecord.class);
                    }

                    @Generated
                    public Object call(Object args) {
                        return this.doCall(args);
                    }

                    @Override
                    @Generated
                    public Object call() {
                        return this.doCall(null);
                    }

                    @Generated
                    public Boolean doCall() {
                        return this.doCall(null);
                    }

                    protected /* synthetic */ MetaClass $getStaticMetaClass() {
                        if (this.getClass() != _getDependencies_closure5.class) {
                            return ScriptBytecodeAdapter.initMetaClass(this);
                        }
                        ClassInfo classInfo = $staticClassInfo;
                        if (classInfo == null) {
                            $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                        }
                        return classInfo.getMetaClass();
                    }

                    public /* synthetic */ MethodHandles.Lookup $getLookup() {
                        return MethodHandles.lookup();
                    }
                }
                Reference<DefaultDependencyDescriptor> dd = new Reference<DefaultDependencyDescriptor>((DefaultDependencyDescriptor)ScriptBytecodeAdapter.castToType(DefaultGroovyMethods.find(((DefaultModuleDescriptor)ScriptBytecodeAdapter.castToType(md, DefaultModuleDescriptor.class)).getDependencies(), (Closure)new _getDependencies_closure5(this, this, grabRecord)), DefaultDependencyDescriptor.class));
                DefaultDependencyDescriptor defaultDependencyDescriptor = dd.get();
                if (!(defaultDependencyDescriptor == null ? false : DefaultTypeTransformation.booleanUnbox(defaultDependencyDescriptor))) {
                    DefaultDependencyDescriptor defaultDependencyDescriptor2 = new DefaultDependencyDescriptor((ModuleDescriptor)md, ((IvyGrabRecord)grabRecord.get()).getMrid(), ((IvyGrabRecord)grabRecord.get()).isForce(), ((IvyGrabRecord)grabRecord.get()).isChanging(), ((IvyGrabRecord)grabRecord.get()).isTransitive());
                    dd.set(defaultDependencyDescriptor2);
                    public final class _getDependencies_closure6
                    extends Closure
                    implements GeneratedClosure {
                        private /* synthetic */ Reference dd;
                        private static /* synthetic */ ClassInfo $staticClassInfo;
                        public static transient /* synthetic */ boolean __$stMC;

                        public _getDependencies_closure6(Object _outerInstance, Object _thisObject, Reference dd) {
                            super(_outerInstance, _thisObject);
                            Reference reference;
                            this.dd = reference = dd;
                        }

                        public Object doCall(Object conf) {
                            ((DefaultDependencyDescriptor)this.dd.get()).addDependencyConfiguration("default", ShortTypeHandling.castToString(conf));
                            return null;
                        }

                        @Generated
                        public DefaultDependencyDescriptor getDd() {
                            return (DefaultDependencyDescriptor)ScriptBytecodeAdapter.castToType(this.dd.get(), DefaultDependencyDescriptor.class);
                        }

                        @Generated
                        public Object call(Object args) {
                            return this.doCall(args);
                        }

                        @Override
                        @Generated
                        public Object call() {
                            return this.doCall(null);
                        }

                        protected /* synthetic */ MetaClass $getStaticMetaClass() {
                            if (this.getClass() != _getDependencies_closure6.class) {
                                return ScriptBytecodeAdapter.initMetaClass(this);
                            }
                            ClassInfo classInfo = $staticClassInfo;
                            if (classInfo == null) {
                                $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                            }
                            return classInfo.getMetaClass();
                        }

                        public /* synthetic */ MethodHandles.Lookup $getLookup() {
                            return MethodHandles.lookup();
                        }
                    }
                    DefaultGroovyMethods.each(confs, (Closure)new _getDependencies_closure6(this, this, dd));
                    ((DefaultModuleDescriptor)ScriptBytecodeAdapter.castToType(md, DefaultModuleDescriptor.class)).addDependency((DependencyDescriptor)dd.get());
                }
                if (!(((IvyGrabRecord)grabRecord.get()).getClassifier() != null || ((IvyGrabRecord)grabRecord.get()).getExt() != null && ScriptBytecodeAdapter.compareNotEqual(((IvyGrabRecord)grabRecord.get()).getExt(), "jar") || ((IvyGrabRecord)grabRecord.get()).getType() != null && ScriptBytecodeAdapter.compareNotEqual(((IvyGrabRecord)grabRecord.get()).getType(), "jar"))) continue;
                String string2 = ((IvyGrabRecord)grabRecord.get()).getExt();
                String string3 = ((IvyGrabRecord)grabRecord.get()).getClassifier();
                Reference<DefaultDependencyArtifactDescriptor> dad = new Reference<DefaultDependencyArtifactDescriptor>(new DefaultDependencyArtifactDescriptor((DependencyDescriptor)dd.get(), ((IvyGrabRecord)grabRecord.get()).getMrid().getName(), DefaultTypeTransformation.booleanUnbox(string = ((IvyGrabRecord)grabRecord.get()).getType()) ? string : "jar", DefaultTypeTransformation.booleanUnbox(string2) ? string2 : "jar", null, (Map)ScriptBytecodeAdapter.castToType((string3 == null ? false : DefaultTypeTransformation.booleanUnbox(string3)) ? ScriptBytecodeAdapter.createMap(new Object[]{"classifier", ((IvyGrabRecord)grabRecord.get()).getClassifier()}) : null, Map.class)));
                public final class _getDependencies_closure7
                extends Closure
                implements GeneratedClosure {
                    private /* synthetic */ Reference dd;
                    private /* synthetic */ Reference dad;
                    private static /* synthetic */ ClassInfo $staticClassInfo;
                    public static transient /* synthetic */ boolean __$stMC;

                    public _getDependencies_closure7(Object _outerInstance, Object _thisObject, Reference dd, Reference dad) {
                        super(_outerInstance, _thisObject);
                        Reference reference;
                        Reference reference2;
                        this.dd = reference2 = dd;
                        this.dad = reference = dad;
                    }

                    public Object doCall(Object conf) {
                        ((DefaultDependencyDescriptor)this.dd.get()).addDependencyArtifact(ShortTypeHandling.castToString(conf), (DependencyArtifactDescriptor)ScriptBytecodeAdapter.castToType(this.dad.get(), DependencyArtifactDescriptor.class));
                        return null;
                    }

                    @Generated
                    public DefaultDependencyDescriptor getDd() {
                        return (DefaultDependencyDescriptor)ScriptBytecodeAdapter.castToType(this.dd.get(), DefaultDependencyDescriptor.class);
                    }

                    @Generated
                    public Object getDad() {
                        return this.dad.get();
                    }

                    @Generated
                    public Object call(Object args) {
                        return this.doCall(args);
                    }

                    @Override
                    @Generated
                    public Object call() {
                        return this.doCall(null);
                    }

                    protected /* synthetic */ MetaClass $getStaticMetaClass() {
                        if (this.getClass() != _getDependencies_closure7.class) {
                            return ScriptBytecodeAdapter.initMetaClass(this);
                        }
                        ClassInfo classInfo = $staticClassInfo;
                        if (classInfo == null) {
                            $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                        }
                        return classInfo.getMetaClass();
                    }

                    public /* synthetic */ MethodHandles.Lookup $getLookup() {
                        return MethodHandles.lookup();
                    }
                }
                DefaultGroovyMethods.each(confs, (Closure)new _getDependencies_closure7(this, this, dd, dad));
            }
        }
        ResolveOptions resolveOptions = new ResolveOptions();
        String[] stringArray = (String[])ScriptBytecodeAdapter.asType(DEFAULT_CONF, String[].class);
        resolveOptions.setConfs(stringArray);
        boolean bl = false;
        resolveOptions.setOutputReport(bl);
        boolean bl2 = args.containsKey("validate") ? DefaultTypeTransformation.booleanUnbox(args.get("validate")) : false;
        resolveOptions.setValidate(bl2);
        ResolveOptions resolveOptions2 = resolveOptions;
        Object v = args.get("autoDownload");
        this.ivyInstance.getSettings().setDefaultResolver((v == null ? false : DefaultTypeTransformation.booleanUnbox(v)) ? "downloadGrapes" : "cachedGrapes");
        Object v2 = args.get("disableChecksums");
        if (v2 == null ? false : DefaultTypeTransformation.booleanUnbox(v2)) {
            this.ivyInstance.getSettings().setVariable("ivy.checksums", "");
        }
        boolean reportDownloads = Boolean.getBoolean("groovy.grape.report.downloads");
        if (reportDownloads) {
            this.addIvyListener();
        }
        ResolveReport report = null;
        int attempt = 8;
        while (true) {
            ResolveReport resolveReport;
            report = resolveReport = this.ivyInstance.resolve((ModuleDescriptor)md, resolveOptions2);
            try {
            }
            catch (IOException e) {
                int n = attempt;
                int cfr_ignored_0 = n - 1;
                if (!(n != 0)) throw (Throwable)new RuntimeException(ShortTypeHandling.castToString(new GStringImpl(new Object[]{e.getMessage()}, new String[]{"Error grabbing grapes -- ", ""})));
                if (reportDownloads) {
                    System.err.println("Grab Error: retrying...");
                }
                DefaultGroovyStaticMethods.sleep(null, attempt > 4 ? 350 : 1000);
                continue;
            }
            break;
        }
        if (report.hasError()) {
            throw (Throwable)new RuntimeException(ShortTypeHandling.castToString(new GStringImpl(new Object[]{report.getAllProblemMessages()}, new String[]{"Error grabbing Grapes -- ", ""})));
        }
        if (DefaultTypeTransformation.booleanUnbox(report.getDownloadSize()) && reportDownloads) {
            Object[] objectArray = new Object[3];
            objectArray[0] = report.getDownloadSize() >> 10;
            objectArray[1] = report.getDownloadTime();
            ArrayList<String> arrayList = new ArrayList<String>();
            ArtifactDownloadReport[] artifactDownloadReportArray = report.getAllArtifactsReports();
            if (artifactDownloadReportArray != null) {
                ArtifactDownloadReport for$it$402 = null;
                ArtifactDownloadReport[] artifactDownloadReportArray2 = artifactDownloadReportArray;
                if (artifactDownloadReportArray != null) {
                    for (ArtifactDownloadReport for$it$402 : artifactDownloadReportArray2) {
                        ArtifactDownloadReport artifactDownloadReport = for$it$402;
                        arrayList.add(artifactDownloadReport != null ? artifactDownloadReport.toString() : null);
                    }
                }
            }
            objectArray[2] = DefaultGroovyMethods.join(arrayList, "\n  ");
            DefaultGroovyMethods.println(System.err, (Object)new GStringImpl(objectArray, new String[]{"Downloaded ", " Kbytes in ", "ms:\n  ", ""}));
        }
        ModuleDescriptor moduleDescriptor = report.getModuleDescriptor();
        md = moduleDescriptor;
        Object v3 = args.get("preserveFiles");
        if (!(!(v3 == null ? false : DefaultTypeTransformation.booleanUnbox(v3)))) return report;
        cacheManager.getResolvedIvyFileInCache(md.getModuleRevisionId()).delete();
        cacheManager.getResolvedIvyPropertiesInCache(md.getModuleRevisionId()).delete();
        return report;
    }

    private Object addIvyListener() {
        public final class _addIvyListener_closure8
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;

            public _addIvyListener_closure8(Object _outerInstance, Object _thisObject) {
                super(_outerInstance, _thisObject);
            }

            public ExtendableItem[] doCall(Object ivyEvent) {
                Object object = ivyEvent;
                if (ScriptBytecodeAdapter.isCase(object, StartResolveEvent.class)) {
                    public final class _closure19
                    extends Closure
                    implements GeneratedClosure {
                        private static /* synthetic */ ClassInfo $staticClassInfo;
                        public static transient /* synthetic */ boolean __$stMC;

                        public _closure19(Object _outerInstance, Object _thisObject) {
                            super(_outerInstance, _thisObject);
                        }

                        public Object doCall(Object it) {
                            String name = it.toString();
                            if (((GrapeIvy)this.getThisObject()).getResolvedDependencies().add(name)) {
                                DefaultGroovyMethods.println(System.err, (Object)new GStringImpl(new Object[]{name}, new String[]{"Resolving ", ""}));
                                return null;
                            }
                            return null;
                        }

                        @Generated
                        public Object call(Object args) {
                            return this.doCall(args);
                        }

                        @Override
                        @Generated
                        public Object call() {
                            return this.doCall(null);
                        }

                        @Generated
                        public Object doCall() {
                            return this.doCall(null);
                        }

                        protected /* synthetic */ MetaClass $getStaticMetaClass() {
                            if (this.getClass() != _closure19.class) {
                                return ScriptBytecodeAdapter.initMetaClass(this);
                            }
                            ClassInfo classInfo = $staticClassInfo;
                            if (classInfo == null) {
                                $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                            }
                            return classInfo.getMetaClass();
                        }

                        public /* synthetic */ MethodHandles.Lookup $getLookup() {
                            return MethodHandles.lookup();
                        }
                    }
                    return (ExtendableItem[])ScriptBytecodeAdapter.castToType(DefaultGroovyMethods.each(((StartResolveEvent)ScriptBytecodeAdapter.castToType(ivyEvent, StartResolveEvent.class)).getModuleDescriptor().getDependencies(), (Closure)new _closure19(this, this.getThisObject())), ExtendableItem[].class);
                }
                if (ScriptBytecodeAdapter.isCase(object, PrepareDownloadEvent.class)) {
                    public final class _closure20
                    extends Closure
                    implements GeneratedClosure {
                        private static /* synthetic */ ClassInfo $staticClassInfo;
                        public static transient /* synthetic */ boolean __$stMC;

                        public _closure20(Object _outerInstance, Object _thisObject) {
                            super(_outerInstance, _thisObject);
                        }

                        public Object doCall(Object it) {
                            String name = it.toString();
                            if (((GrapeIvy)this.getThisObject()).getDownloadedArtifacts().add(name)) {
                                DefaultGroovyMethods.println(System.err, (Object)new GStringImpl(new Object[]{name}, new String[]{"Preparing to download artifact ", ""}));
                                return null;
                            }
                            return null;
                        }

                        @Generated
                        public Object call(Object args) {
                            return this.doCall(args);
                        }

                        @Override
                        @Generated
                        public Object call() {
                            return this.doCall(null);
                        }

                        @Generated
                        public Object doCall() {
                            return this.doCall(null);
                        }

                        protected /* synthetic */ MetaClass $getStaticMetaClass() {
                            if (this.getClass() != _closure20.class) {
                                return ScriptBytecodeAdapter.initMetaClass(this);
                            }
                            ClassInfo classInfo = $staticClassInfo;
                            if (classInfo == null) {
                                $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                            }
                            return classInfo.getMetaClass();
                        }

                        public /* synthetic */ MethodHandles.Lookup $getLookup() {
                            return MethodHandles.lookup();
                        }
                    }
                    return (ExtendableItem[])ScriptBytecodeAdapter.castToType(DefaultGroovyMethods.each(((PrepareDownloadEvent)ScriptBytecodeAdapter.castToType(ivyEvent, PrepareDownloadEvent.class)).getArtifacts(), (Closure)new _closure20(this, this.getThisObject())), ExtendableItem[].class);
                }
                return null;
            }

            @Generated
            public Object call(Object args) {
                return this.doCall(args);
            }

            @Override
            @Generated
            public Object call() {
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _addIvyListener_closure8.class) {
                    return ScriptBytecodeAdapter.initMetaClass(this);
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                }
                return classInfo.getMetaClass();
            }

            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                return MethodHandles.lookup();
            }
        }
        this.ivyInstance.getEventManager().addIvyListener((IvyListener)ScriptBytecodeAdapter.castToType(new _addIvyListener_closure8(this, this), IvyListener.class));
        return null;
    }

    /*
     * WARNING - void declaration
     */
    public void uninstallArtifact(String group, String module, String rev) {
        void var3_3;
        void var2_2;
        Reference<String> group2 = new Reference<String>(group);
        Reference<void> module2 = new Reference<void>(var2_2);
        Reference<void> rev2 = new Reference<void>(var3_3);
        Reference<Pattern> ivyFilePattern = new Reference<Pattern>((Pattern)ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.bitwiseNegate("ivy-(.*)\\.xml"), Pattern.class));
        public final class _uninstallArtifact_closure9
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference group;
            private /* synthetic */ Reference module;
            private /* synthetic */ Reference ivyFilePattern;
            private /* synthetic */ Reference rev;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;

            public _uninstallArtifact_closure9(Object _outerInstance, Object _thisObject, Reference group, Reference module, Reference ivyFilePattern, Reference rev) {
                super(_outerInstance, _thisObject);
                Reference reference;
                Reference reference2;
                Reference reference3;
                Reference reference4;
                this.group = reference4 = group;
                this.module = reference3 = module;
                this.ivyFilePattern = reference2 = ivyFilePattern;
                this.rev = reference = rev;
            }

            public Object doCall(File groupDir) {
                if (ScriptBytecodeAdapter.compareEqual(groupDir.getName(), this.group.get())) {
                    public final class _closure21
                    extends Closure
                    implements GeneratedClosure {
                        private /* synthetic */ Reference module;
                        private /* synthetic */ Reference ivyFilePattern;
                        private /* synthetic */ Reference rev;
                        private static /* synthetic */ ClassInfo $staticClassInfo;
                        public static transient /* synthetic */ boolean __$stMC;

                        public _closure21(Object _outerInstance, Object _thisObject, Reference module, Reference ivyFilePattern, Reference rev) {
                            super(_outerInstance, _thisObject);
                            Reference reference;
                            Reference reference2;
                            Reference reference3;
                            this.module = reference3 = module;
                            this.ivyFilePattern = reference2 = ivyFilePattern;
                            this.rev = reference = rev;
                        }

                        public Object doCall(File moduleDir) {
                            Reference<File> moduleDir2 = new Reference<File>(moduleDir);
                            if (ScriptBytecodeAdapter.compareEqual(moduleDir2.get().getName(), this.module.get())) {
                                public final class _closure22
                                extends Closure
                                implements GeneratedClosure {
                                    private /* synthetic */ Reference ivyFilePattern;
                                    private /* synthetic */ Reference rev;
                                    private /* synthetic */ Reference moduleDir;
                                    private static /* synthetic */ ClassInfo $staticClassInfo;
                                    public static transient /* synthetic */ boolean __$stMC;

                                    public _closure22(Object _outerInstance, Object _thisObject, Reference ivyFilePattern, Reference rev, Reference moduleDir) {
                                        super(_outerInstance, _thisObject);
                                        Reference reference;
                                        Reference reference2;
                                        Reference reference3;
                                        this.ivyFilePattern = reference3 = ivyFilePattern;
                                        this.rev = reference2 = rev;
                                        this.moduleDir = reference = moduleDir;
                                    }

                                    public Boolean doCall(File ivyFile) {
                                        Matcher m = ((Pattern)this.ivyFilePattern.get()).matcher(ivyFile.getName());
                                        if (m.matches() && ScriptBytecodeAdapter.compareEqual(m.group(1), this.rev.get())) {
                                            File jardir = new File((File)ScriptBytecodeAdapter.castToType(this.moduleDir.get(), File.class), "jars");
                                            if (!jardir.exists()) {
                                                return (Boolean)ScriptBytecodeAdapter.castToType(null, Boolean.class);
                                            }
                                            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                                            Element root = db.parse(ivyFile).getDocumentElement();
                                            NodeList publis = root.getElementsByTagName("publications");
                                            int i = 0;
                                            while (i < publis.getLength()) {
                                                NodeList artifacts = ((Element)ScriptBytecodeAdapter.castToType(publis.item(i), Element.class)).getElementsByTagName("artifact");
                                                ((GrapeIvy)ScriptBytecodeAdapter.castToType(this.getThisObject(), GrapeIvy.class)).processArtifacts(artifacts, ShortTypeHandling.castToString(this.rev.get()), jardir);
                                                int cfr_ignored_0 = i + 1;
                                            }
                                            return ivyFile.delete();
                                        }
                                        return null;
                                    }

                                    @Generated
                                    public Boolean call(File ivyFile) {
                                        return this.doCall(ivyFile);
                                    }

                                    @Generated
                                    public Pattern getIvyFilePattern() {
                                        return (Pattern)ScriptBytecodeAdapter.castToType(this.ivyFilePattern.get(), Pattern.class);
                                    }

                                    @Generated
                                    public String getRev() {
                                        return ShortTypeHandling.castToString(this.rev.get());
                                    }

                                    @Generated
                                    public File getModuleDir() {
                                        return (File)ScriptBytecodeAdapter.castToType(this.moduleDir.get(), File.class);
                                    }

                                    protected /* synthetic */ MetaClass $getStaticMetaClass() {
                                        if (this.getClass() != _closure22.class) {
                                            return ScriptBytecodeAdapter.initMetaClass(this);
                                        }
                                        ClassInfo classInfo = $staticClassInfo;
                                        if (classInfo == null) {
                                            $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                                        }
                                        return classInfo.getMetaClass();
                                    }

                                    public /* synthetic */ MethodHandles.Lookup $getLookup() {
                                        return MethodHandles.lookup();
                                    }
                                }
                                ResourceGroovyMethods.eachFileMatch(moduleDir2.get(), this.ivyFilePattern.get(), new _closure22(this, this.getThisObject(), this.ivyFilePattern, this.rev, moduleDir2));
                                return null;
                            }
                            return null;
                        }

                        @Generated
                        public Object call(File moduleDir) {
                            Reference<File> moduleDir2 = new Reference<File>(moduleDir);
                            return this.doCall(moduleDir2.get());
                        }

                        @Generated
                        public String getModule() {
                            return ShortTypeHandling.castToString(this.module.get());
                        }

                        @Generated
                        public Pattern getIvyFilePattern() {
                            return (Pattern)ScriptBytecodeAdapter.castToType(this.ivyFilePattern.get(), Pattern.class);
                        }

                        @Generated
                        public String getRev() {
                            return ShortTypeHandling.castToString(this.rev.get());
                        }

                        protected /* synthetic */ MetaClass $getStaticMetaClass() {
                            if (this.getClass() != _closure21.class) {
                                return ScriptBytecodeAdapter.initMetaClass(this);
                            }
                            ClassInfo classInfo = $staticClassInfo;
                            if (classInfo == null) {
                                $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                            }
                            return classInfo.getMetaClass();
                        }

                        public /* synthetic */ MethodHandles.Lookup $getLookup() {
                            return MethodHandles.lookup();
                        }
                    }
                    ResourceGroovyMethods.eachDir(groupDir, new _closure21(this, this.getThisObject(), this.module, this.ivyFilePattern, this.rev));
                    return null;
                }
                return null;
            }

            @Generated
            public Object call(File groupDir) {
                return this.doCall(groupDir);
            }

            @Generated
            public String getGroup() {
                return ShortTypeHandling.castToString(this.group.get());
            }

            @Generated
            public String getModule() {
                return ShortTypeHandling.castToString(this.module.get());
            }

            @Generated
            public Pattern getIvyFilePattern() {
                return (Pattern)ScriptBytecodeAdapter.castToType(this.ivyFilePattern.get(), Pattern.class);
            }

            @Generated
            public String getRev() {
                return ShortTypeHandling.castToString(this.rev.get());
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _uninstallArtifact_closure9.class) {
                    return ScriptBytecodeAdapter.initMetaClass(this);
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                }
                return classInfo.getMetaClass();
            }

            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                return MethodHandles.lookup();
            }
        }
        ResourceGroovyMethods.eachDir(this.getGrapeCacheDir(), new _uninstallArtifact_closure9(this, this, group2, module2, ivyFilePattern, rev2));
    }

    private void processArtifacts(NodeList artifacts, String rev, File jardir) {
        int i = 0;
        int n = artifacts.getLength();
        while (i < n) {
            String string;
            Node artifact = artifacts.item(i);
            NamedNodeMap attrs = artifact.getAttributes();
            String name = StringGroovyMethods.plus(attrs.getNamedItem("name").getTextContent(), new GStringImpl(new Object[]{rev}, new String[]{"-", ""}));
            Node node = attrs.getNamedItemNS("m", "classifier");
            String classifier = node != null ? node.getTextContent() : null;
            String string2 = classifier;
            if (string2 == null ? false : DefaultTypeTransformation.booleanUnbox(string2)) {
                String string3;
                name = string3 = StringGroovyMethods.plus(name, new GStringImpl(new Object[]{classifier}, new String[]{"-", ""}));
            }
            name = string = StringGroovyMethods.plus(name, new GStringImpl(new Object[]{attrs.getNamedItem("ext").getTextContent()}, new String[]{".", ""}));
            File jarfile = new File(jardir, name);
            if (jarfile.exists()) {
                DefaultGroovyMethods.println(System.err, (Object)new GStringImpl(new Object[]{jarfile.getName()}, new String[]{"Deleting ", ""}));
                jarfile.delete();
            }
            int cfr_ignored_0 = i + 1;
        }
    }

    private Object addExcludesIfNeeded(Map args, DefaultModuleDescriptor md) {
        Reference<DefaultModuleDescriptor> md2 = new Reference<DefaultModuleDescriptor>(md);
        Object v = args.get("excludes");
        public final class _addExcludesIfNeeded_closure10
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference md;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;

            public _addExcludesIfNeeded_closure10(Object _outerInstance, Object _thisObject, Reference md) {
                super(_outerInstance, _thisObject);
                Reference reference;
                this.md = reference = md;
            }

            public Object doCall(Map<String, String> map) {
                DefaultExcludeRule excludeRule = new DefaultExcludeRule(new ArtifactId(new ModuleId(ShortTypeHandling.castToString(map.get("group")), ShortTypeHandling.castToString(map.get("module"))), PatternMatcher.ANY_EXPRESSION, PatternMatcher.ANY_EXPRESSION, PatternMatcher.ANY_EXPRESSION), (PatternMatcher)ExactPatternMatcher.INSTANCE, null);
                excludeRule.addConfiguration("default");
                ((DefaultModuleDescriptor)this.md.get()).addExcludeRule((ExcludeRule)excludeRule);
                return null;
            }

            @Generated
            public Object call(Map<String, String> map) {
                return this.doCall(map);
            }

            @Generated
            public DefaultModuleDescriptor getMd() {
                return (DefaultModuleDescriptor)ScriptBytecodeAdapter.castToType(this.md.get(), DefaultModuleDescriptor.class);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _addExcludesIfNeeded_closure10.class) {
                    return ScriptBytecodeAdapter.initMetaClass(this);
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                }
                return classInfo.getMetaClass();
            }

            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                return MethodHandles.lookup();
            }
        }
        return v != null ? DefaultGroovyMethods.each(v, (Closure)new _addExcludesIfNeeded_closure10(this, this, md2)) : null;
    }

    @Override
    public Map<String, Map<String, List<String>>> enumerateGrapes() {
        Reference<LinkedHashMap> bunches = new Reference<LinkedHashMap>((LinkedHashMap)ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.createMap(new Object[0]), LinkedHashMap.class));
        Reference<Pattern> ivyFilePattern = new Reference<Pattern>((Pattern)ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.bitwiseNegate("ivy-(.*)\\.xml"), Pattern.class));
        public final class _enumerateGrapes_closure11
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference bunches;
            private /* synthetic */ Reference ivyFilePattern;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;

            public _enumerateGrapes_closure11(Object _outerInstance, Object _thisObject, Reference bunches, Reference ivyFilePattern) {
                super(_outerInstance, _thisObject);
                Reference reference;
                Reference reference2;
                this.bunches = reference2 = bunches;
                this.ivyFilePattern = reference = ivyFilePattern;
            }

            public Object doCall(File groupDir) {
                Reference<LinkedHashMap> grapes = new Reference<LinkedHashMap>((LinkedHashMap)ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.createMap(new Object[0]), LinkedHashMap.class));
                LinkedHashMap linkedHashMap = grapes.get();
                ScriptBytecodeAdapter.invokeMethodN(_enumerateGrapes_closure11.class, this.bunches.get(), "putAt", new Object[]{groupDir.getName(), linkedHashMap});
                public final class _closure23
                extends Closure
                implements GeneratedClosure {
                    private /* synthetic */ Reference ivyFilePattern;
                    private /* synthetic */ Reference grapes;
                    private static /* synthetic */ ClassInfo $staticClassInfo;
                    public static transient /* synthetic */ boolean __$stMC;

                    public _closure23(Object _outerInstance, Object _thisObject, Reference ivyFilePattern, Reference grapes) {
                        super(_outerInstance, _thisObject);
                        Reference reference;
                        Reference reference2;
                        this.ivyFilePattern = reference2 = ivyFilePattern;
                        this.grapes = reference = grapes;
                    }

                    public ArrayList<String> doCall(File moduleDir) {
                        Reference<ArrayList> versions = new Reference<ArrayList>((ArrayList)ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.createList(new Object[0]), ArrayList.class));
                        public final class _closure24
                        extends Closure
                        implements GeneratedClosure {
                            private /* synthetic */ Reference ivyFilePattern;
                            private /* synthetic */ Reference versions;
                            private static /* synthetic */ ClassInfo $staticClassInfo;
                            public static transient /* synthetic */ boolean __$stMC;

                            public _closure24(Object _outerInstance, Object _thisObject, Reference ivyFilePattern, Reference versions) {
                                super(_outerInstance, _thisObject);
                                Reference reference;
                                Reference reference2;
                                this.ivyFilePattern = reference2 = ivyFilePattern;
                                this.versions = reference = versions;
                            }

                            public ArrayList<String> doCall(File ivyFile) {
                                Matcher m = ((Pattern)this.ivyFilePattern.get()).matcher(ivyFile.getName());
                                if (m.matches()) {
                                    List<String> list = DefaultGroovyMethods.plus((List)ScriptBytecodeAdapter.castToType(this.versions.get(), List.class), m.group(1));
                                    this.versions.set(list);
                                    return (ArrayList)ScriptBytecodeAdapter.castToType(list, ArrayList.class);
                                }
                                return null;
                            }

                            @Generated
                            public ArrayList<String> call(File ivyFile) {
                                return this.doCall(ivyFile);
                            }

                            @Generated
                            public Pattern getIvyFilePattern() {
                                return (Pattern)ScriptBytecodeAdapter.castToType(this.ivyFilePattern.get(), Pattern.class);
                            }

                            @Generated
                            public List getVersions() {
                                return (List)ScriptBytecodeAdapter.castToType(this.versions.get(), List.class);
                            }

                            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                                if (this.getClass() != _closure24.class) {
                                    return ScriptBytecodeAdapter.initMetaClass(this);
                                }
                                ClassInfo classInfo = $staticClassInfo;
                                if (classInfo == null) {
                                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                                }
                                return classInfo.getMetaClass();
                            }

                            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                                return MethodHandles.lookup();
                            }
                        }
                        ResourceGroovyMethods.eachFileMatch(moduleDir, this.ivyFilePattern.get(), new _closure24(this, this.getThisObject(), this.ivyFilePattern, versions));
                        ArrayList arrayList = versions.get();
                        ScriptBytecodeAdapter.invokeMethodN(_closure23.class, this.grapes.get(), "putAt", new Object[]{moduleDir.getName(), arrayList});
                        return arrayList;
                    }

                    @Generated
                    public ArrayList<String> call(File moduleDir) {
                        return this.doCall(moduleDir);
                    }

                    @Generated
                    public Pattern getIvyFilePattern() {
                        return (Pattern)ScriptBytecodeAdapter.castToType(this.ivyFilePattern.get(), Pattern.class);
                    }

                    @Generated
                    public Map getGrapes() {
                        return (Map)ScriptBytecodeAdapter.castToType(this.grapes.get(), Map.class);
                    }

                    protected /* synthetic */ MetaClass $getStaticMetaClass() {
                        if (this.getClass() != _closure23.class) {
                            return ScriptBytecodeAdapter.initMetaClass(this);
                        }
                        ClassInfo classInfo = $staticClassInfo;
                        if (classInfo == null) {
                            $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                        }
                        return classInfo.getMetaClass();
                    }

                    public /* synthetic */ MethodHandles.Lookup $getLookup() {
                        return MethodHandles.lookup();
                    }
                }
                ResourceGroovyMethods.eachDir(groupDir, new _closure23(this, this.getThisObject(), this.ivyFilePattern, grapes));
                return null;
            }

            @Generated
            public Object call(File groupDir) {
                return this.doCall(groupDir);
            }

            @Generated
            public Map getBunches() {
                return (Map)ScriptBytecodeAdapter.castToType(this.bunches.get(), Map.class);
            }

            @Generated
            public Pattern getIvyFilePattern() {
                return (Pattern)ScriptBytecodeAdapter.castToType(this.ivyFilePattern.get(), Pattern.class);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _enumerateGrapes_closure11.class) {
                    return ScriptBytecodeAdapter.initMetaClass(this);
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                }
                return classInfo.getMetaClass();
            }

            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                return MethodHandles.lookup();
            }
        }
        ResourceGroovyMethods.eachDir(this.getGrapeCacheDir(), new _enumerateGrapes_closure11(this, this, bunches, ivyFilePattern));
        return bunches.get();
    }

    @Override
    public URI[] resolve(Map args, Map ... dependencies) {
        return this.resolve(args, (List)null, dependencies);
    }

    @Override
    public URI[] resolve(Map args, List depsInfo, Map ... dependencies) {
        Object v;
        ClassLoader loader = this.chooseClassLoader(ScriptBytecodeAdapter.createMap(new Object[]{"refObject", args.remove("refObject"), "classLoader", args.remove("classLoader"), "calleeDepth", DefaultTypeTransformation.booleanUnbox(v = args.get("calleeDepth")) ? v : Integer.valueOf(DEFAULT_CALLEE_DEPTH)}));
        ClassLoader classLoader = loader;
        if (!(classLoader == null ? false : DefaultTypeTransformation.booleanUnbox(classLoader))) {
            return new URI[0];
        }
        return this.resolve(loader, args, depsInfo, dependencies);
    }

    public URI[] resolve(ClassLoader loader, Map args, Map ... dependencies) {
        return this.resolve(loader, args, (List)null, dependencies);
    }

    public URI[] resolve(ClassLoader loader, Map args, List depsInfo, Map ... dependencies) {
        Reference<List> depsInfo2 = new Reference<List>(depsInfo);
        Reference keys = new Reference(args.keySet());
        public final class _resolve_closure12
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference keys;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;

            public _resolve_closure12(Object _outerInstance, Object _thisObject, Reference keys) {
                super(_outerInstance, _thisObject);
                Reference reference;
                this.keys = reference = keys;
            }

            public Object doCall(Object key) {
                Set badArgs = (Set)ScriptBytecodeAdapter.castToType(DefaultGroovyMethods.getAt(GrapeIvy.pfaccess$1(null), key), Set.class);
                if (DefaultTypeTransformation.booleanUnbox(badArgs) && !DefaultGroovyMethods.disjoint(badArgs, (Iterable)ScriptBytecodeAdapter.castToType(this.keys.get(), Iterable.class))) {
                    throw (Throwable)new RuntimeException(ShortTypeHandling.castToString(new GStringImpl(new Object[]{DefaultGroovyMethods.plus(DefaultGroovyMethods.intersect((Set)ScriptBytecodeAdapter.castToType(this.keys.get(), Set.class), badArgs), key)}, new String[]{"Mutually exclusive arguments passed into grab: ", ""})));
                }
                return null;
            }

            @Generated
            public Set getKeys() {
                return (Set)ScriptBytecodeAdapter.castToType(this.keys.get(), Set.class);
            }

            @Generated
            public Object call(Object args) {
                return this.doCall(args);
            }

            @Override
            @Generated
            public Object call() {
                return this.doCall(null);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _resolve_closure12.class) {
                    return ScriptBytecodeAdapter.initMetaClass(this);
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                }
                return classInfo.getMetaClass();
            }

            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                return MethodHandles.lookup();
            }
        }
        DefaultGroovyMethods.each(keys.get(), (Closure)new _resolve_closure12(this, this, keys));
        if (!this.enableGrapes) {
            return new URI[0];
        }
        boolean populateDepsInfo = depsInfo2.get() != null;
        Reference<Set<IvyGrabRecord>> localDeps = new Reference<Set<IvyGrabRecord>>(this.getLoadedDepsForLoader(loader));
        public final class _resolve_closure13
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference localDeps;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;

            public _resolve_closure13(Object _outerInstance, Object _thisObject, Reference localDeps) {
                super(_outerInstance, _thisObject);
                Reference reference;
                this.localDeps = reference = localDeps;
            }

            public Boolean doCall(Map dep) {
                IvyGrabRecord igr = ((GrapeIvy)this.getThisObject()).createGrabRecord(dep);
                ((GrapeIvy)this.getThisObject()).getGrabRecordsForCurrDependencies().add(igr);
                return ((Set)this.localDeps.get()).add(igr);
            }

            @Generated
            public Boolean call(Map dep) {
                return this.doCall(dep);
            }

            @Generated
            public Set getLocalDeps() {
                return (Set)ScriptBytecodeAdapter.castToType(this.localDeps.get(), Set.class);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _resolve_closure13.class) {
                    return ScriptBytecodeAdapter.initMetaClass(this);
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                }
                return classInfo.getMetaClass();
            }

            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                return MethodHandles.lookup();
            }
        }
        DefaultGroovyMethods.each(dependencies, (Closure)new _resolve_closure13(this, this, localDeps));
        ResolveReport report = null;
        try {
            ResolveReport resolveReport;
            report = resolveReport = this.getDependencies(args, (IvyGrabRecord[])ScriptBytecodeAdapter.castToType(DefaultGroovyMethods.reverse((IvyGrabRecord[])ScriptBytecodeAdapter.asType(localDeps.get(), IvyGrabRecord[].class), true), IvyGrabRecord[].class));
        }
        catch (Exception e) {
            localDeps.get().removeAll(this.grabRecordsForCurrDependencies);
            this.grabRecordsForCurrDependencies.clear();
            throw (Throwable)e;
        }
        ArrayList results = (ArrayList)ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.createList(new Object[0]), ArrayList.class);
        ArtifactDownloadReport adl2 = null;
        ArtifactDownloadReport[] artifactDownloadReportArray = report.getAllArtifactsReports();
        if (artifactDownloadReportArray != null) {
            for (ArtifactDownloadReport adl2 : artifactDownloadReportArray) {
                File file = adl2.getLocalFile();
                if (!(file == null ? false : DefaultTypeTransformation.booleanUnbox(file))) continue;
                List<URI> list = DefaultGroovyMethods.plus(results, adl2.getLocalFile().toURI());
                results = (ArrayList)ScriptBytecodeAdapter.castToType(list, ArrayList.class);
            }
        }
        if (populateDepsInfo) {
            public final class _resolve_closure14
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference depsInfo;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;

                public _resolve_closure14(Object _outerInstance, Object _thisObject, Reference depsInfo) {
                    super(_outerInstance, _thisObject);
                    Reference reference;
                    this.depsInfo = reference = depsInfo;
                }

                public List<LinkedHashMap<String, String>> doCall(Object ivyNode) {
                    ModuleRevisionId id = ((IvyNode)ivyNode).getId();
                    return DefaultGroovyMethods.leftShift((List)ScriptBytecodeAdapter.castToType(this.depsInfo.get(), List.class), ScriptBytecodeAdapter.createMap(new Object[]{"group", id.getOrganisation(), "module", id.getName(), "revision", id.getRevision()}));
                }

                @Generated
                public List getDepsInfo() {
                    return (List)ScriptBytecodeAdapter.castToType(this.depsInfo.get(), List.class);
                }

                @Generated
                public Object call(Object args) {
                    return this.doCall(args);
                }

                @Override
                @Generated
                public Object call() {
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (this.getClass() != _resolve_closure14.class) {
                        return ScriptBytecodeAdapter.initMetaClass(this);
                    }
                    ClassInfo classInfo = $staticClassInfo;
                    if (classInfo == null) {
                        $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                    }
                    return classInfo.getMetaClass();
                }

                public /* synthetic */ MethodHandles.Lookup $getLookup() {
                    return MethodHandles.lookup();
                }
            }
            DefaultGroovyMethods.each(report.getDependencies(), (Closure)new _resolve_closure14(this, this, depsInfo2));
        }
        return (URI[])ScriptBytecodeAdapter.asType(results, URI[].class);
    }

    private Set<IvyGrabRecord> getLoadedDepsForLoader(ClassLoader loader) {
        public final class _getLoadedDepsForLoader_lambda15
        extends Closure
        implements GeneratedLambda {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;

            public _getLoadedDepsForLoader_lambda15(Object _outerInstance, Object _thisObject) {
                super(_outerInstance, _thisObject);
            }

            public R doCall(Object k) {
                return (LinkedHashSet)ScriptBytecodeAdapter.asType(ScriptBytecodeAdapter.createList(new Object[0]), LinkedHashSet.class);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _getLoadedDepsForLoader_lambda15.class) {
                    return ScriptBytecodeAdapter.initMetaClass(this);
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                }
                return classInfo.getMetaClass();
            }

            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                return MethodHandles.lookup();
            }
        }
        return (Set)ScriptBytecodeAdapter.castToType(this.loadedDeps.computeIfAbsent(loader, new _getLoadedDepsForLoader_lambda15(GrapeIvy.class, GrapeIvy.class)::doCall), Set.class);
    }

    @Override
    public Map[] listDependencies(ClassLoader classLoader) {
        Set<IvyGrabRecord> set = DefaultGroovyMethods.getAt(this.loadedDeps, classLoader);
        public final class _listDependencies_closure16
        extends Closure
        implements GeneratedClosure {
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;

            public _listDependencies_closure16(Object _outerInstance, Object _thisObject) {
                super(_outerInstance, _thisObject);
            }

            public LinkedHashMap<String, String> doCall(IvyGrabRecord grabbed) {
                LinkedHashMap dep = (LinkedHashMap)ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.createMap(new Object[]{"group", grabbed.getMrid().getOrganisation(), "module", grabbed.getMrid().getName(), "version", grabbed.getMrid().getRevision()}), LinkedHashMap.class);
                if (ScriptBytecodeAdapter.compareNotEqual(grabbed.getConf(), GrapeIvy.pfaccess$0(null))) {
                    List<String> list;
                    List<String> list2 = list = grabbed.getConf();
                    dep.put("conf", list2);
                }
                if (grabbed.isChanging()) {
                    boolean bl;
                    boolean bl2 = bl = grabbed.isChanging();
                    dep.put("changing", bl2);
                }
                if (!grabbed.isTransitive()) {
                    boolean bl;
                    boolean bl3 = bl = grabbed.isTransitive();
                    dep.put("transitive", bl3);
                }
                if (!grabbed.isForce()) {
                    boolean bl;
                    boolean bl4 = bl = grabbed.isForce();
                    dep.put("force", bl4);
                }
                String string = grabbed.getClassifier();
                if (string == null ? false : DefaultTypeTransformation.booleanUnbox(string)) {
                    String string2;
                    String string3 = string2 = grabbed.getClassifier();
                    dep.put("classifier", string3);
                }
                String string4 = grabbed.getExt();
                if (string4 == null ? false : DefaultTypeTransformation.booleanUnbox(string4)) {
                    String string5;
                    String string6 = string5 = grabbed.getExt();
                    dep.put("ext", string6);
                }
                String string7 = grabbed.getType();
                if (string7 == null ? false : DefaultTypeTransformation.booleanUnbox(string7)) {
                    String string8;
                    String string9 = string8 = grabbed.getType();
                    dep.put("type", string9);
                }
                return dep;
            }

            @Generated
            public LinkedHashMap<String, String> call(IvyGrabRecord grabbed) {
                return this.doCall(grabbed);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (this.getClass() != _listDependencies_closure16.class) {
                    return ScriptBytecodeAdapter.initMetaClass(this);
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
                }
                return classInfo.getMetaClass();
            }

            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                return MethodHandles.lookup();
            }
        }
        List results = set != null ? DefaultGroovyMethods.collect((Iterable)ScriptBytecodeAdapter.castToType(set, Iterable.class), new _listDependencies_closure16(this, this)) : null;
        return (Map[])ScriptBytecodeAdapter.asType(results, Map[].class);
    }

    @Override
    public void addResolver(@NamedParams(value={@NamedParam(type=String.class, value="name", required=true), @NamedParam(type=String.class, value="root", required=true), @NamedParam(type=Boolean.class, value="m2Compatible", required=false)}) Map<String, Object> args) {
        Ivy ivy;
        IBiblioResolver iBiblioResolver = new IBiblioResolver();
        String string = ShortTypeHandling.castToString(args.get("name"));
        iBiblioResolver.setName(string);
        String string2 = ShortTypeHandling.castToString(args.get("root"));
        iBiblioResolver.setRoot(string2);
        ResolverSettings resolverSettings = (ResolverSettings)this.settings;
        iBiblioResolver.setSettings(resolverSettings);
        boolean bl = DefaultTypeTransformation.booleanUnbox(args.getOrDefault("m2Compatible", Boolean.TRUE));
        iBiblioResolver.setM2compatible(bl);
        IBiblioResolver resolver = iBiblioResolver;
        ChainResolver chainResolver = (ChainResolver)ScriptBytecodeAdapter.castToType(this.settings.getResolver("downloadGrapes"), ChainResolver.class);
        chainResolver.getResolvers().add(0, resolver);
        this.ivyInstance = ivy = Ivy.newInstance((IvySettings)this.settings);
        List list = ScriptBytecodeAdapter.createList(new Object[0]);
        this.resolvedDependencies = (Set)ScriptBytecodeAdapter.castToType(list, Set.class);
        List list2 = ScriptBytecodeAdapter.createList(new Object[0]);
        this.downloadedArtifacts = (Set)ScriptBytecodeAdapter.castToType(list2, Set.class);
    }

    public static /* synthetic */ List<String> pfaccess$0(GrapeIvy $that) {
        return (List)ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.getField(GrapeIvy.class, GrapeIvy.class, "DEFAULT_CONF"), List.class);
    }

    public static /* synthetic */ Map<String, Set<String>> pfaccess$1(GrapeIvy $that) {
        return (Map)ScriptBytecodeAdapter.castToType(ScriptBytecodeAdapter.getField(GrapeIvy.class, GrapeIvy.class, "MUTUALLY_EXCLUSIVE_KEYS"), Map.class);
    }

    public static /* synthetic */ Set<String> pfaccess$2(GrapeIvy $that) {
        return $that.downloadedArtifacts;
    }

    public static /* synthetic */ Set<String> pfaccess$3(GrapeIvy $that) {
        return $that.resolvedDependencies;
    }

    public static /* synthetic */ Set<IvyGrabRecord> pfaccess$4(GrapeIvy $that) {
        return $that.grabRecordsForCurrDependencies;
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != GrapeIvy.class) {
            return ScriptBytecodeAdapter.initMetaClass(this);
        }
        ClassInfo classInfo = $staticClassInfo;
        if (classInfo == null) {
            $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
        }
        return classInfo.getMetaClass();
    }

    @Override
    @Generated
    @Internal
    @Transient
    public MetaClass getMetaClass() {
        MetaClass metaClass = this.metaClass;
        if (metaClass != null) {
            return metaClass;
        }
        this.metaClass = this.$getStaticMetaClass();
        return this.metaClass;
    }

    @Override
    @Generated
    @Internal
    public void setMetaClass(MetaClass metaClass) {
        this.metaClass = metaClass;
    }

    public static /* synthetic */ MethodHandles.Lookup $getLookup() {
        return MethodHandles.lookup();
    }

    static {
        String string;
        RUNNER_PROVIDER_CONFIG = string = GroovyRunner.class.getName();
        List<String> list = Collections.singletonList("default");
        DEFAULT_CONF = list;
        Map<String, Set<String>> map = GrapeIvy.processGrabArgs(ScriptBytecodeAdapter.createList(new Object[]{ScriptBytecodeAdapter.createList(new Object[]{"group", "groupId", "organisation", "organization", "org"}), ScriptBytecodeAdapter.createList(new Object[]{"module", "artifactId", "artifact"}), ScriptBytecodeAdapter.createList(new Object[]{"version", "revision", "rev"}), ScriptBytecodeAdapter.createList(new Object[]{"conf", "scope", "configuration"})}));
        MUTUALLY_EXCLUSIVE_KEYS = map;
    }

    @Generated
    public boolean getEnableGrapes() {
        return this.enableGrapes;
    }

    @Generated
    public boolean isEnableGrapes() {
        return this.enableGrapes;
    }

    @Generated
    public void setEnableGrapes(boolean bl) {
        this.enableGrapes = bl;
    }

    @Generated
    public Ivy getIvyInstance() {
        return this.ivyInstance;
    }

    @Generated
    public void setIvyInstance(Ivy ivy) {
        this.ivyInstance = ivy;
    }

    @Generated
    public IvySettings getSettings() {
        return this.settings;
    }

    @Generated
    public void setSettings(IvySettings ivySettings) {
        this.settings = ivySettings;
    }

    @Generated
    public Set<String> getDownloadedArtifacts() {
        return this.downloadedArtifacts;
    }

    @Generated
    public void setDownloadedArtifacts(Set<String> set) {
        this.downloadedArtifacts = set;
    }

    @Generated
    public Set<String> getResolvedDependencies() {
        return this.resolvedDependencies;
    }

    @Generated
    public void setResolvedDependencies(Set<String> set) {
        this.resolvedDependencies = set;
    }

    @Generated
    public final Map<ClassLoader, Set<IvyGrabRecord>> getLoadedDeps() {
        return this.loadedDeps;
    }

    @Generated
    public final Set<IvyGrabRecord> getGrabRecordsForCurrDependencies() {
        return this.grabRecordsForCurrDependencies;
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "inject";
        stringArray[1] = "conf";
        stringArray[2] = "scope";
        stringArray[3] = "configuration";
        stringArray[4] = "startsWith";
        stringArray[5] = "endsWith";
        stringArray[6] = "getAt";
        stringArray[7] = "toList";
        stringArray[8] = "split";
        stringArray[9] = "addURL";
        stringArray[10] = "toURL";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[11];
        GrapeIvy.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(GrapeIvy.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = GrapeIvy.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

