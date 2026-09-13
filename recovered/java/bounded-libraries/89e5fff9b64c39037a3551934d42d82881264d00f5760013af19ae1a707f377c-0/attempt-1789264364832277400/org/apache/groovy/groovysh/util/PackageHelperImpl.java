/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Closure
 *  groovy.lang.GroovyObject
 *  groovy.lang.GroovyShell
 *  groovy.lang.IntRange
 *  groovy.lang.MetaClass
 *  groovy.lang.Reference
 *  groovy.transform.Generated
 *  groovy.transform.Internal
 *  org.codehaus.groovy.reflection.ClassInfo
 *  org.codehaus.groovy.runtime.BytecodeInterface8
 *  org.codehaus.groovy.runtime.DefaultGroovyMethods
 *  org.codehaus.groovy.runtime.GStringImpl
 *  org.codehaus.groovy.runtime.GeneratedClosure
 *  org.codehaus.groovy.runtime.ScriptBytecodeAdapter
 *  org.codehaus.groovy.runtime.StringGroovyMethods
 *  org.codehaus.groovy.runtime.callsite.CallSite
 *  org.codehaus.groovy.runtime.callsite.CallSiteArray
 *  org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation
 *  org.codehaus.groovy.runtime.typehandling.ShortTypeHandling
 *  org.codehaus.groovy.tools.shell.util.Logger
 *  org.codehaus.groovy.tools.shell.util.Preferences
 */
package org.apache.groovy.groovysh.util;

import groovy.lang.Closure;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import groovy.lang.IntRange;
import groovy.lang.MetaClass;
import groovy.lang.Reference;
import groovy.transform.Generated;
import groovy.transform.Internal;
import java.beans.Transient;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.ref.SoftReference;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import org.apache.groovy.groovysh.util.CachedPackage;
import org.apache.groovy.groovysh.util.PackageHelper;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.runtime.BytecodeInterface8;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.runtime.GStringImpl;
import org.codehaus.groovy.runtime.GeneratedClosure;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.runtime.StringGroovyMethods;
import org.codehaus.groovy.runtime.callsite.CallSite;
import org.codehaus.groovy.runtime.callsite.CallSiteArray;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;
import org.codehaus.groovy.runtime.typehandling.ShortTypeHandling;
import org.codehaus.groovy.tools.shell.util.Logger;
import org.codehaus.groovy.tools.shell.util.Preferences;

public class PackageHelperImpl
implements PreferenceChangeListener,
PackageHelper,
GroovyObject {
    protected static final Logger LOG;
    public static final Pattern NAME_PATTERN;
    private static final String CLASS_SUFFIX = ".class";
    private Map<String, CachedPackage> rootPackages;
    private final ClassLoader groovyClassLoader;
    private static /* synthetic */ ClassInfo $staticClassInfo;
    public static transient /* synthetic */ boolean __$stMC;
    private transient /* synthetic */ MetaClass metaClass;
    private static /* synthetic */ SoftReference $callSiteArray;

    public PackageHelperImpl(ClassLoader groovyClassLoader) {
        ClassLoader classLoader;
        MetaClass metaClass;
        this.metaClass = metaClass = this.$getStaticMetaClass();
        this.groovyClassLoader = classLoader = groovyClassLoader;
        this.initializePackages();
        Preferences.addChangeListener((PreferenceChangeListener)this);
    }

    @Generated
    public PackageHelperImpl() {
        CallSite[] callSiteArray = PackageHelperImpl.$getCallSiteArray();
        this(null);
    }

    @Override
    public void reset() {
        this.initializePackages();
    }

    private void initializePackages() {
        Boolean bl = Boolean.valueOf(Preferences.get((String)IMPORT_COMPLETION_PREFERENCE_KEY));
        if (!(bl == null ? false : bl)) {
            Map<String, CachedPackage> map = PackageHelperImpl.getPackages(this.groovyClassLoader);
            this.rootPackages = map;
        }
    }

    @Override
    public void preferenceChange(PreferenceChangeEvent evt) {
        if (ScriptBytecodeAdapter.compareEqual((Object)evt.getKey(), (Object)IMPORT_COMPLETION_PREFERENCE_KEY)) {
            Boolean bl = Boolean.valueOf(evt.getNewValue());
            if (bl == null ? false : bl) {
                Object var2_2 = null;
                this.rootPackages = (Map)ScriptBytecodeAdapter.castToType(var2_2, Map.class);
            } else if (this.rootPackages == null) {
                this.initializePackages();
            }
        }
    }

    private static Map<String, CachedPackage> getPackages(ClassLoader groovyClassLoader) throws IOException {
        HashMap<String, CachedPackage> rootPackages = new HashMap<String, CachedPackage>();
        Reference urls = new Reference(new HashSet());
        ClassLoader loader = groovyClassLoader;
        while (loader != null) {
            ClassLoader classLoader;
            if (!(loader instanceof URLClassLoader)) {
                LOG.debug((Object)StringGroovyMethods.plus((CharSequence)"Ignoring classloader for completion: ", (Object)loader));
            } else {
                DefaultGroovyMethods.addAll((Collection)((HashSet)urls.get()), (Object[])((URLClassLoader)ScriptBytecodeAdapter.castToType((Object)loader, URLClassLoader.class)).getURLs());
            }
            loader = classLoader = loader.getParent();
        }
        Object[] systemClasses = new Class[]{String.class, JFrame.class, GroovyObject.class};
        Reference jigsaw = new Reference((Object)false);
        public final class _getPackages_closure1
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference urls;
            private /* synthetic */ Reference jigsaw;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;

            public _getPackages_closure1(Object _outerInstance, Object _thisObject, Reference urls, Reference jigsaw) {
                super(_outerInstance, _thisObject);
                Reference reference;
                Reference reference2;
                this.urls = reference2 = urls;
                this.jigsaw = reference = jigsaw;
            }

            public Boolean doCall(Class systemClass) {
                String classfileName = StringGroovyMethods.plus((String)systemClass.getName().replace(".", "/"), (CharSequence)PackageHelperImpl.pfaccess$0(null));
                URL classURL = systemClass.getResource(classfileName);
                if (classURL == null) {
                    URL uRL;
                    classURL = uRL = Thread.currentThread().getContextClassLoader().getResource(classfileName);
                }
                if (classURL != null) {
                    URLConnection uc = classURL.openConnection();
                    if (uc instanceof JarURLConnection) {
                        return ((HashSet)this.urls.get()).add(((JarURLConnection)ScriptBytecodeAdapter.castToType((Object)uc, JarURLConnection.class)).getJarFileURL());
                    }
                    if (uc.getClass().getSimpleName().equals("JavaRuntimeURLConnection")) {
                        boolean bl = true;
                        this.jigsaw.set((Object)bl);
                        return bl;
                    }
                    String filepath = classURL.toExternalForm();
                    String rootFolder = filepath.substring(0, filepath.length() - classfileName.length() - 1);
                    return ((HashSet)this.urls.get()).add(new URL(rootFolder));
                }
                return null;
            }

            @Generated
            public Boolean call(Class systemClass) {
                return this.doCall(systemClass);
            }

            @Generated
            public Set getUrls() {
                return (Set)ScriptBytecodeAdapter.castToType((Object)this.urls.get(), Set.class);
            }

            @Generated
            public Boolean getJigsaw() {
                return (Boolean)ScriptBytecodeAdapter.castToType((Object)this.jigsaw.get(), Boolean.class);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _getPackages_closure1.class) {
                    return ScriptBytecodeAdapter.initMetaClass((Object)((Object)this));
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(((Object)((Object)this)).getClass());
                }
                return classInfo.getMetaClass();
            }

            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                return MethodHandles.lookup();
            }
        }
        DefaultGroovyMethods.each((Object[])systemClasses, (Closure)new _getPackages_closure1(PackageHelperImpl.class, PackageHelperImpl.class, urls, jigsaw));
        HashSet hashSet = (HashSet)urls.get();
        URL url = null;
        Iterator iterator = hashSet != null ? hashSet.iterator() : null;
        if (iterator != null) {
            while (iterator.hasNext()) {
                url = (URL)ScriptBytecodeAdapter.castToType(iterator.next(), URL.class);
                Collection<String> packageNames = PackageHelperImpl.getPackageNames(url);
                Collection<String> collection = packageNames;
                if (!(collection == null ? false : DefaultTypeTransformation.booleanUnbox(collection))) continue;
                PackageHelperImpl.mergeNewPackages(packageNames, url, rootPackages);
            }
        }
        if (DefaultTypeTransformation.booleanUnbox((Object)((Boolean)jigsaw.get())) || PackageHelperImpl.isModularRuntime()) {
            URL jigsawURL = URI.create("jrt:/").toURL();
            public final class _getPackages_closure2
            extends Closure
            implements GeneratedClosure {
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;

                public _getPackages_closure2(Object _outerInstance, Object _thisObject) {
                    super(_outerInstance, _thisObject);
                }

                public Boolean doCall(Object isPackage, Object name) {
                    return DefaultTypeTransformation.booleanUnbox((Object)isPackage) && DefaultTypeTransformation.booleanUnbox((Object)name);
                }

                @Generated
                public Boolean call(Object isPackage, Object name) {
                    return this.doCall(isPackage, name);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _getPackages_closure2.class) {
                        return ScriptBytecodeAdapter.initMetaClass((Object)((Object)this));
                    }
                    ClassInfo classInfo = $staticClassInfo;
                    if (classInfo == null) {
                        $staticClassInfo = classInfo = ClassInfo.getClassInfo(((Object)((Object)this)).getClass());
                    }
                    return classInfo.getMetaClass();
                }

                public /* synthetic */ MethodHandles.Lookup $getLookup() {
                    return MethodHandles.lookup();
                }
            }
            Set<String> jigsawPackages = PackageHelperImpl.getPackagesAndClassesFromJigsaw(jigsawURL, new _getPackages_closure2(PackageHelperImpl.class, PackageHelperImpl.class));
            PackageHelperImpl.mergeNewPackages(jigsawPackages, jigsawURL, rootPackages);
        }
        return rootPackages;
    }

    private static boolean isModularRuntime() {
        CallSite[] callSiteArray = PackageHelperImpl.$getCallSiteArray();
        boolean bl = ScriptBytecodeAdapter.compareNotEqual((Object)callSiteArray[0].call(callSiteArray[1].callGetProperty(PackageHelperImpl.class), (Object)"java.lang.reflect.Module", (Object)false), null);
        try {
            return bl;
        }
        catch (Exception e) {
            boolean bl2 = false;
            return bl2;
        }
    }

    private static Set<String> getPackagesAndClassesFromJigsaw(URL jigsawURL, Closure<Boolean> predicate) {
        GroovyShell shell = new GroovyShell();
        shell.setProperty("predicate", predicate);
        String jigsawURLString = jigsawURL.toString();
        shell.setProperty("jigsawURLString", (Object)jigsawURLString);
        shell.evaluate("import java.nio.file.*\n\ndef fs = FileSystems.newFileSystem(URI.create(jigsawURLString), [:])\n\nresult = [] as Set\n\ndef filterPackageName(Path path) {\n    def elems = \"$path\".split('/')\n\n    if (elems && elems.length > 2) {\n        // remove e.g. 'modules/java.base/\n        elems = elems[2..<elems.length]\n\n        def name = elems.join('.')\n        if (predicate(true, name)) {\n            result << name\n        }\n    }\n}\n\ndef filterClassName(Path path) {\n    def elems = \"$path\".split('/')\n\n    if (elems && elems.length > 2) {\n        // remove e.g. 'modules/java.base/\n        elems = elems[2..<elems.length]\n\n        def name = elems.join('.')\n        if (name.endsWith('.class')) {\n            name = name.substring(0, name.lastIndexOf('.'))\n            if (predicate(false, name)) {\n                result << name\n            }\n        }\n    }\n}\n\nclass GroovyFileVisitor extends SimpleFileVisitor {}\n\n// walk each file and directory, possibly storing directories as packages, and files as classes\nFiles.walkFileTree(fs.getPath('modules'),\n        [preVisitDirectory: { dir, attrs -> filterPackageName(dir); FileVisitResult.CONTINUE },\n         visitFile: { file, attrs -> filterClassName(file); FileVisitResult.CONTINUE}\n        ]\n            as GroovyFileVisitor)\n");
        Set jigsawPackages = (Set)ScriptBytecodeAdapter.castToType((Object)shell.getProperty("result"), Set.class);
        return jigsawPackages;
    }

    /*
     * WARNING - void declaration
     */
    public static Object mergeNewPackages(Collection<String> packageNames, URL url, Map<String, CachedPackage> rootPackages) {
        void var2_2;
        Reference url2 = new Reference((Object)url);
        Reference rootPackages2 = new Reference((Object)var2_2);
        Reference tokenizer = new Reference(null);
        StringTokenizer cfr_ignored_0 = (StringTokenizer)tokenizer.get();
        public final class _mergeNewPackages_closure3
        extends Closure
        implements GeneratedClosure {
            private /* synthetic */ Reference tokenizer;
            private /* synthetic */ Reference rootPackages;
            private /* synthetic */ Reference url;
            private static /* synthetic */ ClassInfo $staticClassInfo;
            public static transient /* synthetic */ boolean __$stMC;

            public _mergeNewPackages_closure3(Object _outerInstance, Object _thisObject, Reference tokenizer, Reference rootPackages, Reference url) {
                super(_outerInstance, _thisObject);
                Reference reference;
                Reference reference2;
                Reference reference3;
                this.tokenizer = reference3 = tokenizer;
                this.rootPackages = reference2 = rootPackages;
                this.url = reference = url;
            }

            public Object doCall(String packname) {
                CachedPackage cachedPackage;
                StringTokenizer stringTokenizer = new StringTokenizer(packname, ".");
                this.tokenizer.set((Object)stringTokenizer);
                if (!((StringTokenizer)this.tokenizer.get()).hasMoreTokens()) {
                    return null;
                }
                String rootname = ((StringTokenizer)this.tokenizer.get()).nextToken();
                CachedPackage cp = null;
                CachedPackage childp = null;
                cp = cachedPackage = (CachedPackage)DefaultGroovyMethods.get((Map)((Map)ScriptBytecodeAdapter.castToType((Object)this.rootPackages.get(), Map.class)), (Object)rootname, null);
                if (cp == null) {
                    CachedPackage cachedPackage2;
                    cp = cachedPackage2 = new CachedPackage(rootname, (Set)ScriptBytecodeAdapter.asType((Object)ScriptBytecodeAdapter.createList((Object[])new Object[]{this.url.get()}), Set.class));
                    ((Map)this.rootPackages.get()).put(rootname, cp);
                }
                while (((StringTokenizer)this.tokenizer.get()).hasMoreTokens()) {
                    CachedPackage cachedPackage3;
                    CachedPackage cachedPackage4;
                    String packbasename = ((StringTokenizer)this.tokenizer.get()).nextToken();
                    if (cp.getChildPackages() == null) {
                        HashMap<String, CachedPackage> hashMap;
                        new HashMap<String, CachedPackage>(1);
                        cp.setChildPackages(hashMap);
                    }
                    childp = cachedPackage4 = (CachedPackage)DefaultGroovyMethods.get(cp.getChildPackages(), (Object)packbasename, null);
                    if (childp == null) {
                        CachedPackage cachedPackage5;
                        HashSet<URL> urllist = new HashSet<URL>(1);
                        urllist.add((URL)this.url.get());
                        childp = cachedPackage5 = new CachedPackage(packbasename, urllist);
                        cp.getChildPackages().put(packbasename, childp);
                    } else {
                        childp.getSources().add((URL)this.url.get());
                    }
                    cp = cachedPackage3 = childp;
                }
                return null;
            }

            @Generated
            public Object call(String packname) {
                return this.doCall(packname);
            }

            @Generated
            public StringTokenizer getTokenizer() {
                return (StringTokenizer)ScriptBytecodeAdapter.castToType((Object)this.tokenizer.get(), StringTokenizer.class);
            }

            @Generated
            public Map getRootPackages() {
                return (Map)ScriptBytecodeAdapter.castToType((Object)this.rootPackages.get(), Map.class);
            }

            @Generated
            public URL getUrl() {
                return (URL)ScriptBytecodeAdapter.castToType((Object)this.url.get(), URL.class);
            }

            protected /* synthetic */ MetaClass $getStaticMetaClass() {
                if (((Object)((Object)this)).getClass() != _mergeNewPackages_closure3.class) {
                    return ScriptBytecodeAdapter.initMetaClass((Object)((Object)this));
                }
                ClassInfo classInfo = $staticClassInfo;
                if (classInfo == null) {
                    $staticClassInfo = classInfo = ClassInfo.getClassInfo(((Object)((Object)this)).getClass());
                }
                return classInfo.getMetaClass();
            }

            public /* synthetic */ MethodHandles.Lookup $getLookup() {
                return MethodHandles.lookup();
            }
        }
        return DefaultGroovyMethods.each(packageNames, (Closure)new _mergeNewPackages_closure3(PackageHelperImpl.class, PackageHelperImpl.class, tokenizer, rootPackages2, url2));
    }

    public static Collection<String> getPackageNames(URL url) {
        Reference urlFile = new Reference((Object)Paths.get(url.toURI()).toFile());
        if (((File)urlFile.get()).isDirectory()) {
            public final class _getPackageNames_closure4
            extends Closure
            implements GeneratedClosure {
                private /* synthetic */ Reference urlFile;
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;

                public _getPackageNames_closure4(Object _outerInstance, Object _thisObject, Reference urlFile) {
                    super(_outerInstance, _thisObject);
                    Reference reference;
                    this.urlFile = reference = urlFile;
                }

                public Collection<String> doCall(Object it) {
                    return PackageHelperImpl.collectPackageNamesFromFolderRecursive((File)ScriptBytecodeAdapter.castToType((Object)this.urlFile.get(), File.class), "", (Set)ScriptBytecodeAdapter.castToType((Object)it, Set.class));
                }

                @Generated
                public File getUrlFile() {
                    return (File)ScriptBytecodeAdapter.castToType((Object)this.urlFile.get(), File.class);
                }

                @Generated
                public Object call(Object args) {
                    return this.doCall(args);
                }

                @Generated
                public Object call() {
                    return this.doCall(null);
                }

                @Generated
                public Collection<String> doCall() {
                    return this.doCall(null);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _getPackageNames_closure4.class) {
                        return ScriptBytecodeAdapter.initMetaClass((Object)((Object)this));
                    }
                    ClassInfo classInfo = $staticClassInfo;
                    if (classInfo == null) {
                        $staticClassInfo = classInfo = ClassInfo.getClassInfo(((Object)((Object)this)).getClass());
                    }
                    return classInfo.getMetaClass();
                }

                public /* synthetic */ MethodHandles.Lookup $getLookup() {
                    return MethodHandles.lookup();
                }
            }
            return (Collection)ScriptBytecodeAdapter.castToType((Object)DefaultGroovyMethods.tap(new HashSet(), (Closure)new _getPackageNames_closure4(PackageHelperImpl.class, PackageHelperImpl.class, urlFile)), Collection.class);
        }
        if (((File)urlFile.get()).getPath().endsWith(".jar")) {
            JarFile jarFile = new JarFile((File)urlFile.get());
            Collection<String> collection = PackageHelperImpl.getPackageNamesFromJar(jarFile);
            try {
                return collection;
            }
            catch (IOException e) {
                if (LOG.isDebugEnabled()) {
                    e.printStackTrace();
                }
                LOG.warn((Object)new GStringImpl(new Object[]{url.getFile(), e.toString()}, new String[]{"Error opening jar file : '", "' : ", ""}));
            }
            catch (InvalidPathException e) {
                if (LOG.isDebugEnabled()) {
                    e.printStackTrace();
                }
                LOG.warn((Object)new GStringImpl(new Object[]{url.getFile(), e.toString()}, new String[]{"Error opening jar file : '", "' : ", ""}));
            }
        }
        return ScriptBytecodeAdapter.createList((Object[])new Object[0]);
    }

    public static Collection<String> collectPackageNamesFromFolderRecursive(File directory, String prefix, Set<String> packnames) {
        Object[] files = directory.listFiles();
        boolean packageAdded = false;
        int i = 0;
        while (files != null && i < files.length) {
            if (((File)BytecodeInterface8.objectArrayGet((Object[])files, (int)i)).isDirectory()) {
                if (((File)BytecodeInterface8.objectArrayGet((Object[])files, (int)i)).getName().startsWith(".")) {
                    return (Collection)ScriptBytecodeAdapter.castToType(null, Collection.class);
                }
                String string = prefix;
                String optionalDot = (string == null ? false : DefaultTypeTransformation.booleanUnbox((Object)string)) ? "." : "";
                PackageHelperImpl.collectPackageNamesFromFolderRecursive((File)ScriptBytecodeAdapter.castToType((Object)BytecodeInterface8.objectArrayGet((Object[])files, (int)i), File.class), StringGroovyMethods.plus((String)StringGroovyMethods.plus((String)prefix, (CharSequence)optionalDot), (CharSequence)((File)BytecodeInterface8.objectArrayGet((Object[])files, (int)i)).getName()), packnames);
            } else if (!packageAdded && ((File)BytecodeInterface8.objectArrayGet((Object[])files, (int)i)).getName().endsWith(CLASS_SUFFIX)) {
                boolean bl;
                packageAdded = bl = true;
                String string = prefix;
                if (string == null ? false : DefaultTypeTransformation.booleanUnbox((Object)string)) {
                    packnames.add(prefix);
                }
            }
            int n = i;
            int cfr_ignored_0 = n + 1;
        }
        return (Collection)ScriptBytecodeAdapter.castToType(null, Collection.class);
    }

    public static Collection<String> getPackageNamesFromJar(JarFile jf) {
        HashSet<String> packnames = new HashSet<String>();
        Enumeration<JarEntry> e = jf.entries();
        while (e.hasMoreElements()) {
            JarEntry entry = e.nextElement();
            if (entry == null) continue;
            String name = entry.getName();
            if (!name.endsWith(CLASS_SUFFIX)) continue;
            String fullname = name.replace("/", ".").substring(0, name.length() - CLASS_SUFFIX.length());
            if (!(fullname.lastIndexOf(".") > -1)) continue;
            packnames.add(fullname.substring(0, fullname.lastIndexOf(".")));
        }
        return packnames;
    }

    @Override
    public Set<String> getContents(String packagename) {
        String string;
        String string2;
        Map<String, CachedPackage> map = this.rootPackages;
        if (!(map == null ? false : DefaultTypeTransformation.booleanUnbox(map))) {
            return (Set)ScriptBytecodeAdapter.asType((Object)ScriptBytecodeAdapter.createList((Object[])new Object[0]), Set.class);
        }
        String string3 = packagename;
        if (!(string3 == null ? false : DefaultTypeTransformation.booleanUnbox((Object)string3))) {
            public final class _getContents_closure5
            extends Closure
            implements GeneratedClosure {
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;

                public _getContents_closure5(Object _outerInstance, Object _thisObject) {
                    super(_outerInstance, _thisObject);
                }

                public String doCall(String key, CachedPackage v) {
                    return key;
                }

                @Generated
                public String call(String key, CachedPackage v) {
                    return this.doCall(key, v);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _getContents_closure5.class) {
                        return ScriptBytecodeAdapter.initMetaClass((Object)((Object)this));
                    }
                    ClassInfo classInfo = $staticClassInfo;
                    if (classInfo == null) {
                        $staticClassInfo = classInfo = ClassInfo.getClassInfo(((Object)((Object)this)).getClass());
                    }
                    return classInfo.getMetaClass();
                }

                public /* synthetic */ MethodHandles.Lookup $getLookup() {
                    return MethodHandles.lookup();
                }
            }
            return (Set)ScriptBytecodeAdapter.asType((Object)DefaultGroovyMethods.collect(this.rootPackages, (Closure)new _getContents_closure5(this, this)), Set.class);
        }
        String sanitizedPackageName = null;
        sanitizedPackageName = packagename.endsWith(".*") ? (string2 = StringGroovyMethods.getAt((String)packagename, (IntRange)new IntRange(true, true, 0, -3))) : (string = packagename);
        StringTokenizer tokenizer = new StringTokenizer(sanitizedPackageName, ".");
        CachedPackage cp = (CachedPackage)ScriptBytecodeAdapter.castToType((Object)this.rootPackages.get(tokenizer.nextToken()), CachedPackage.class);
        while (cp != null && tokenizer.hasMoreTokens()) {
            CachedPackage cachedPackage;
            String token = tokenizer.nextToken();
            if (cp.getChildPackages() == null) {
                return (Set)ScriptBytecodeAdapter.asType((Object)ScriptBytecodeAdapter.createList((Object[])new Object[0]), Set.class);
            }
            cp = cachedPackage = cp.getChildPackages().get(token);
        }
        if (cp == null) {
            return (Set)ScriptBytecodeAdapter.asType((Object)ScriptBytecodeAdapter.createList((Object[])new Object[0]), Set.class);
        }
        TreeSet<String> children = new TreeSet<String>();
        Map<String, CachedPackage> map2 = cp.getChildPackages();
        if (map2 == null ? false : DefaultTypeTransformation.booleanUnbox(map2)) {
            public final class _getContents_closure6
            extends Closure
            implements GeneratedClosure {
                private static /* synthetic */ ClassInfo $staticClassInfo;
                public static transient /* synthetic */ boolean __$stMC;

                public _getContents_closure6(Object _outerInstance, Object _thisObject) {
                    super(_outerInstance, _thisObject);
                }

                public String doCall(String key, CachedPackage v) {
                    return key;
                }

                @Generated
                public String call(String key, CachedPackage v) {
                    return this.doCall(key, v);
                }

                protected /* synthetic */ MetaClass $getStaticMetaClass() {
                    if (((Object)((Object)this)).getClass() != _getContents_closure6.class) {
                        return ScriptBytecodeAdapter.initMetaClass((Object)((Object)this));
                    }
                    ClassInfo classInfo = $staticClassInfo;
                    if (classInfo == null) {
                        $staticClassInfo = classInfo = ClassInfo.getClassInfo(((Object)((Object)this)).getClass());
                    }
                    return classInfo.getMetaClass();
                }

                public /* synthetic */ MethodHandles.Lookup $getLookup() {
                    return MethodHandles.lookup();
                }
            }
            children.addAll(DefaultGroovyMethods.collect(cp.getChildPackages(), (Closure)new _getContents_closure6(this, this)));
        }
        if (cp.isChecked() && !cp.isContainsClasses()) {
            return children;
        }
        Set<String> classnames = PackageHelperImpl.getClassnames(cp.getSources(), sanitizedPackageName);
        boolean bl = true;
        cp.setChecked(bl);
        Set<String> set = classnames;
        if (set == null ? false : DefaultTypeTransformation.booleanUnbox(set)) {
            boolean bl2 = true;
            cp.setContainsClasses(bl2);
            children.addAll(classnames);
        }
        return children;
    }

    public static Set<String> getClassnames(Set<URL> urls, String packagename) {
        Reference packagename2 = new Reference((Object)packagename);
        TreeSet<String> classes = new TreeSet<String>();
        String pathname = ((String)packagename2.get()).replace(".", "/");
        Iterator<URL> it = urls.iterator();
        while (it.hasNext()) {
            URL url = (URL)ScriptBytecodeAdapter.castToType((Object)it.next(), URL.class);
            if (ScriptBytecodeAdapter.compareEqual((Object)url.getProtocol(), (Object)"jrt")) {
                public final class _getClassnames_closure7
                extends Closure
                implements GeneratedClosure {
                    private /* synthetic */ Reference packagename;
                    private static /* synthetic */ ClassInfo $staticClassInfo;
                    public static transient /* synthetic */ boolean __$stMC;

                    public _getClassnames_closure7(Object _outerInstance, Object _thisObject, Reference packagename) {
                        super(_outerInstance, _thisObject);
                        Reference reference;
                        this.packagename = reference = packagename;
                    }

                    public Boolean doCall(boolean isPackage, String name) {
                        return !isPackage && name.startsWith(ShortTypeHandling.castToString((Object)this.packagename.get()));
                    }

                    @Generated
                    public Boolean call(boolean isPackage, String name) {
                        return this.doCall(isPackage, name);
                    }

                    @Generated
                    public String getPackagename() {
                        return ShortTypeHandling.castToString((Object)this.packagename.get());
                    }

                    protected /* synthetic */ MetaClass $getStaticMetaClass() {
                        if (((Object)((Object)this)).getClass() != _getClassnames_closure7.class) {
                            return ScriptBytecodeAdapter.initMetaClass((Object)((Object)this));
                        }
                        ClassInfo classInfo = $staticClassInfo;
                        if (classInfo == null) {
                            $staticClassInfo = classInfo = ClassInfo.getClassInfo(((Object)((Object)this)).getClass());
                        }
                        return classInfo.getMetaClass();
                    }

                    public /* synthetic */ MethodHandles.Lookup $getLookup() {
                        return MethodHandles.lookup();
                    }
                }
                public final class _getClassnames_closure8
                extends Closure
                implements GeneratedClosure {
                    private /* synthetic */ Reference packagename;
                    private static /* synthetic */ ClassInfo $staticClassInfo;
                    public static transient /* synthetic */ boolean __$stMC;

                    public _getClassnames_closure8(Object _outerInstance, Object _thisObject, Reference packagename) {
                        super(_outerInstance, _thisObject);
                        Reference reference;
                        this.packagename = reference = packagename;
                    }

                    public String doCall(Object it) {
                        return StringGroovyMethods.minus((CharSequence)((CharSequence)ScriptBytecodeAdapter.castToType((Object)it, CharSequence.class)), (Object)new GStringImpl(new Object[]{this.packagename.get()}, new String[]{"", "."}));
                    }

                    @Generated
                    public String getPackagename() {
                        return ShortTypeHandling.castToString((Object)this.packagename.get());
                    }

                    @Generated
                    public Object call(Object args) {
                        return this.doCall(args);
                    }

                    @Generated
                    public Object call() {
                        return this.doCall(null);
                    }

                    @Generated
                    public String doCall() {
                        return this.doCall(null);
                    }

                    protected /* synthetic */ MetaClass $getStaticMetaClass() {
                        if (((Object)((Object)this)).getClass() != _getClassnames_closure8.class) {
                            return ScriptBytecodeAdapter.initMetaClass((Object)((Object)this));
                        }
                        ClassInfo classInfo = $staticClassInfo;
                        if (classInfo == null) {
                            $staticClassInfo = classInfo = ClassInfo.getClassInfo(((Object)((Object)this)).getClass());
                        }
                        return classInfo.getMetaClass();
                    }

                    public /* synthetic */ MethodHandles.Lookup $getLookup() {
                        return MethodHandles.lookup();
                    }
                }
                DefaultGroovyMethods.collect(PackageHelperImpl.getPackagesAndClassesFromJigsaw(url, new _getClassnames_closure7(PackageHelperImpl.class, PackageHelperImpl.class, packagename2)), classes, (Closure)new _getClassnames_closure8(PackageHelperImpl.class, PackageHelperImpl.class, packagename2));
                continue;
            }
            File file = new File(URLDecoder.decode(url.getFile(), "UTF-8"));
            if (file == null) continue;
            if (file.isDirectory()) {
                File packFolder = new File(file, pathname);
                if (!packFolder.isDirectory()) continue;
                Object[] files = packFolder.listFiles();
                int i = 0;
                while (files != null && i < files.length) {
                    if (((File)BytecodeInterface8.objectArrayGet((Object[])files, (int)i)).isFile()) {
                        String filename = ((File)BytecodeInterface8.objectArrayGet((Object[])files, (int)i)).getName();
                        if (filename.endsWith(CLASS_SUFFIX)) {
                            String name = filename.substring(0, filename.length() - CLASS_SUFFIX.length());
                            if (!(!StringGroovyMethods.matches((CharSequence)name, (Pattern)NAME_PATTERN))) {
                                classes.add(name);
                            }
                        }
                    }
                    int n = i;
                    int cfr_ignored_0 = n + 1;
                }
                continue;
            }
            if (!file.toString().endsWith(".jar")) continue;
            try (JarFile jf = new JarFile(file);){
                Enumeration<JarEntry> e = jf.entries();
                while (e.hasMoreElements()) {
                    String string;
                    JarEntry entry = e.nextElement();
                    if (entry == null) continue;
                    String name = entry.getName();
                    if (!name.endsWith(CLASS_SUFFIX)) continue;
                    int lastslash = name.lastIndexOf("/");
                    if (lastslash == -1 || ScriptBytecodeAdapter.compareNotEqual((Object)name.substring(0, lastslash), (Object)pathname)) continue;
                    name = string = name.substring(lastslash + 1, name.length() - CLASS_SUFFIX.length());
                    if (!StringGroovyMethods.matches((CharSequence)name, (Pattern)NAME_PATTERN)) continue;
                    classes.add(name);
                }
            }
        }
        return classes;
    }

    public static /* synthetic */ String pfaccess$0(PackageHelperImpl $that) {
        return ShortTypeHandling.castToString((Object)ScriptBytecodeAdapter.getField(PackageHelperImpl.class, PackageHelperImpl.class, (String)"CLASS_SUFFIX"));
    }

    protected /* synthetic */ MetaClass $getStaticMetaClass() {
        if (this.getClass() != PackageHelperImpl.class) {
            return ScriptBytecodeAdapter.initMetaClass((Object)this);
        }
        ClassInfo classInfo = $staticClassInfo;
        if (classInfo == null) {
            $staticClassInfo = classInfo = ClassInfo.getClassInfo(this.getClass());
        }
        return classInfo.getMetaClass();
    }

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

    @Generated
    @Internal
    public void setMetaClass(MetaClass metaClass) {
        this.metaClass = metaClass;
    }

    public static /* synthetic */ MethodHandles.Lookup $getLookup() {
        return MethodHandles.lookup();
    }

    static {
        Logger logger;
        LOG = logger = Logger.create(PackageHelperImpl.class);
        Object object = ScriptBytecodeAdapter.bitwiseNegate((Object)"^[A-Z][^.$_]+$");
        NAME_PATTERN = (Pattern)ScriptBytecodeAdapter.castToType((Object)object, Pattern.class);
    }

    @Generated
    public Map<String, CachedPackage> getRootPackages() {
        return this.rootPackages;
    }

    @Generated
    public void setRootPackages(Map<String, CachedPackage> map) {
        this.rootPackages = map;
    }

    @Generated
    public final ClassLoader getGroovyClassLoader() {
        return this.groovyClassLoader;
    }

    private static /* synthetic */ void $createCallSiteArray_1(String[] stringArray) {
        stringArray[0] = "loadClass";
        stringArray[1] = "classLoader";
    }

    private static /* synthetic */ CallSiteArray $createCallSiteArray() {
        String[] stringArray = new String[2];
        PackageHelperImpl.$createCallSiteArray_1(stringArray);
        return new CallSiteArray(PackageHelperImpl.class, stringArray);
    }

    private static /* synthetic */ CallSite[] $getCallSiteArray() {
        CallSiteArray callSiteArray;
        if ($callSiteArray == null || (callSiteArray = (CallSiteArray)$callSiteArray.get()) == null) {
            callSiteArray = PackageHelperImpl.$createCallSiteArray();
            $callSiteArray = new SoftReference<CallSiteArray>(callSiteArray);
        }
        return callSiteArray.array;
    }
}

