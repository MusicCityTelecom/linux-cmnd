/*
 * Decompiled with CFR 0.152.
 */
package groovy.lang;

import groovy.lang.GroovyCodeSource;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyResourceLoader;
import groovy.lang.GroovyRuntimeException;
import groovy.util.CharsetToolkit;
import groovyjarjarasm.asm.ClassVisitor;
import groovyjarjarasm.asm.ClassWriter;
import groovyjarjarasm.asm.Opcodes;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.NoSuchAlgorithmException;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.classgen.GeneratorContext;
import org.codehaus.groovy.classgen.Verifier;
import org.codehaus.groovy.control.BytecodeProcessor;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.runtime.EncodingGroovyMethods;
import org.codehaus.groovy.runtime.IOGroovyMethods;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.runtime.memoize.EvictableCache;
import org.codehaus.groovy.runtime.memoize.StampedCommonCache;
import org.codehaus.groovy.runtime.memoize.UnlimitedConcurrentCache;
import org.codehaus.groovy.util.URLStreams;

public class GroovyClassLoader
extends URLClassLoader {
    private static final URL[] EMPTY_URL_ARRAY = new URL[0];
    private static final Class[] EMPTY_CLASS_ARRAY = new Class[0];
    protected final EvictableCache<String, Class> classCache = new UnlimitedConcurrentCache<String, Class>();
    protected final StampedCommonCache<String, Class> sourceCache = new StampedCommonCache();
    private final CompilerConfiguration config;
    private String sourceEncoding;
    private Boolean recompile;
    private static int scriptNameCounter = 1000000;
    private GroovyResourceLoader resourceLoader = new GroovyResourceLoader(){

        @Override
        public URL loadGroovySource(String filename) throws MalformedURLException {
            return AccessController.doPrivileged(() -> {
                for (String extension : GroovyClassLoader.this.config.getScriptExtensions()) {
                    try {
                        URL ret = GroovyClassLoader.this.getSourceFile(filename, extension);
                        if (ret == null) continue;
                        return ret;
                    }
                    catch (Throwable throwable) {
                    }
                }
                return null;
            });
        }
    };

    public GroovyClassLoader() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public GroovyClassLoader(ClassLoader loader) {
        this(loader, null);
    }

    public GroovyClassLoader(GroovyClassLoader parent) {
        this(parent, parent.config, false);
    }

    public GroovyClassLoader(ClassLoader parent, CompilerConfiguration config, boolean useConfigurationClasspath) {
        super(EMPTY_URL_ARRAY, parent);
        if (config == null) {
            config = CompilerConfiguration.DEFAULT;
        }
        this.config = config;
        if (useConfigurationClasspath) {
            for (String path : config.getClasspath()) {
                this.addClasspath(path);
            }
        }
        this.initSourceEncoding(config);
    }

    private void initSourceEncoding(CompilerConfiguration config) {
        this.sourceEncoding = config.getSourceEncoding();
        if (null == this.sourceEncoding) {
            this.sourceEncoding = CharsetToolkit.getDefaultSystemCharset().name();
        }
    }

    public GroovyClassLoader(ClassLoader loader, CompilerConfiguration config) {
        this(loader, config, true);
    }

    public void setResourceLoader(GroovyResourceLoader resourceLoader) {
        if (resourceLoader == null) {
            throw new IllegalArgumentException("Resource loader must not be null!");
        }
        this.resourceLoader = resourceLoader;
    }

    public GroovyResourceLoader getResourceLoader() {
        return this.resourceLoader;
    }

    public Class defineClass(ClassNode classNode, String file, String newCodeBase) {
        CodeSource codeSource = null;
        try {
            codeSource = new CodeSource(new URL("file", "", newCodeBase), (Certificate[])null);
        }
        catch (MalformedURLException malformedURLException) {
            // empty catch block
        }
        CompilationUnit unit = this.createCompilationUnit(this.config, codeSource);
        ClassCollector collector = this.createCollector(unit, classNode.getModule().getContext());
        try {
            unit.addClassNode(classNode);
            unit.setClassgenCallback(collector);
            unit.compile(7);
            this.definePackageInternal(collector.generatedClass.getName());
            return collector.generatedClass;
        }
        catch (CompilationFailedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasCompatibleConfiguration(CompilerConfiguration config) {
        return this.config == config;
    }

    public Class parseClass(File file) throws CompilationFailedException, IOException {
        return this.parseClass(new GroovyCodeSource(file, this.config.getSourceEncoding()));
    }

    public Class parseClass(String text, String fileName) throws CompilationFailedException {
        GroovyCodeSource gcs = this.createCodeSource(() -> new GroovyCodeSource(text, fileName, "/groovy/script"));
        gcs.setCachable(false);
        return this.parseClass(gcs);
    }

    private GroovyCodeSource createCodeSource(PrivilegedAction<GroovyCodeSource> action) {
        return AccessController.doPrivileged(action);
    }

    public Class parseClass(String text) throws CompilationFailedException {
        try {
            return this.parseClass(text, "Script_" + EncodingGroovyMethods.md5(text) + ".groovy");
        }
        catch (NoSuchAlgorithmException e) {
            throw new GroovyBugError("Failed to generate md5", e);
        }
    }

    public synchronized String generateScriptName() {
        return "script" + ++scriptNameCounter + ".groovy";
    }

    public Class parseClass(Reader reader, String fileName) throws CompilationFailedException {
        GroovyCodeSource gcs = this.createCodeSource(() -> {
            try {
                String scriptText = IOGroovyMethods.getText(reader);
                return new GroovyCodeSource(scriptText, fileName, "/groovy/script");
            }
            catch (IOException e) {
                throw new RuntimeException("Impossible to read the content of the reader for file named: " + fileName, e);
            }
        });
        return this.parseClass(gcs);
    }

    public Class parseClass(GroovyCodeSource codeSource) throws CompilationFailedException {
        return this.parseClass(codeSource, codeSource.isCachable());
    }

    public Class parseClass(GroovyCodeSource codeSource, boolean shouldCacheSource) throws CompilationFailedException {
        String cacheKey = this.genSourceCacheKey(codeSource);
        return this.sourceCache.getAndPut(cacheKey, key -> this.doParseClass(codeSource), shouldCacheSource);
    }

    private String genSourceCacheKey(GroovyCodeSource codeSource) {
        StringBuilder strToDigest;
        String scriptText = codeSource.getScriptText();
        if (null != scriptText) {
            strToDigest = new StringBuilder((int)((double)scriptText.length() * 1.2));
            strToDigest.append("scriptText:").append(scriptText);
            CodeSource cs = codeSource.getCodeSource();
            if (null != cs) {
                strToDigest.append("/codeSource:").append(cs);
            }
        } else {
            strToDigest = new StringBuilder(32);
            strToDigest.append("name:").append(codeSource.getName());
        }
        try {
            return EncodingGroovyMethods.md5(strToDigest);
        }
        catch (NoSuchAlgorithmException e) {
            throw new GroovyRuntimeException(e);
        }
    }

    private Class doParseClass(GroovyCodeSource codeSource) {
        URL url;
        GroovyClassLoader.validate(codeSource);
        CompilationUnit unit = this.createCompilationUnit(this.config, codeSource.getCodeSource());
        if (this.recompile != null && this.recompile.booleanValue() || this.recompile == null && this.config.getRecompileGroovySource()) {
            unit.addFirstPhaseOperation(TimestampAdder.INSTANCE, CompilePhase.CLASS_GENERATION.getPhaseNumber());
        }
        SourceUnit su = null;
        File file = codeSource.getFile();
        su = file != null ? unit.addSource(file) : ((url = codeSource.getURL()) != null ? unit.addSource(url) : unit.addSource(codeSource.getName(), codeSource.getScriptText()));
        ClassCollector collector = this.createCollector(unit, su);
        unit.setClassgenCallback(collector);
        int goalPhase = 7;
        if (this.config != null && this.config.getTargetDirectory() != null) {
            goalPhase = 8;
        }
        unit.compile(goalPhase);
        Class answer = collector.generatedClass;
        String mainClass = su.getAST().getMainClassName();
        for (Object o : collector.getLoadedClasses()) {
            Class clazz = (Class)o;
            String clazzName = clazz.getName();
            this.definePackageInternal(clazzName);
            this.setClassCacheEntry(clazz);
            if (!clazzName.equals(mainClass)) continue;
            answer = clazz;
        }
        return answer;
    }

    private static void validate(GroovyCodeSource codeSource) {
        if (codeSource.getFile() == null && codeSource.getScriptText() == null) {
            throw new IllegalArgumentException("Script text to compile cannot be null!");
        }
    }

    private void definePackageInternal(String className) {
        String pkgName;
        Package pkg;
        int i = className.lastIndexOf(46);
        if (i != -1 && (pkg = this.getPackage(pkgName = className.substring(0, i))) == null) {
            this.definePackage(pkgName, null, null, null, null, null, null, null);
        }
    }

    protected String[] getClassPath() {
        URL[] urls = this.getURLs();
        String[] ret = new String[urls.length];
        for (int i = 0; i < ret.length; ++i) {
            ret[i] = urls[i].getFile();
        }
        return ret;
    }

    @Override
    protected PermissionCollection getPermissions(CodeSource codeSource) {
        PermissionCollection perms;
        try {
            try {
                perms = super.getPermissions(codeSource);
            }
            catch (SecurityException e) {
                perms = new Permissions();
            }
            ProtectionDomain myDomain = this.getProtectionDomain();
            PermissionCollection myPerms = myDomain.getPermissions();
            if (myPerms != null) {
                Enumeration<Permission> elements = myPerms.elements();
                while (elements.hasMoreElements()) {
                    perms.add(elements.nextElement());
                }
            }
        }
        catch (Throwable e) {
            perms = new Permissions();
        }
        perms.setReadOnly();
        return perms;
    }

    private ProtectionDomain getProtectionDomain() {
        return AccessController.doPrivileged(new PrivilegedAction<ProtectionDomain>(){

            @Override
            public ProtectionDomain run() {
                return this.getClass().getProtectionDomain();
            }
        });
    }

    protected CompilationUnit createCompilationUnit(CompilerConfiguration config, CodeSource source) {
        return new CompilationUnit(config, source, this);
    }

    protected ClassCollector createCollector(CompilationUnit unit, SourceUnit su) {
        return new ClassCollector(this.createLoader(), unit, su);
    }

    private InnerLoader createLoader() {
        return AccessController.doPrivileged(() -> new InnerLoader(this));
    }

    public Class defineClass(String name, byte[] bytes) throws ClassFormatError {
        return super.defineClass(name, bytes, 0, bytes.length);
    }

    public Class loadClass(String name, boolean lookupScriptFiles, boolean preferClassOverScript) throws ClassNotFoundException, CompilationFailedException {
        return this.loadClass(name, lookupScriptFiles, preferClassOverScript, false);
    }

    protected Class getClassCacheEntry(String name) {
        return (Class)this.classCache.get(name);
    }

    protected void setClassCacheEntry(Class cls) {
        this.classCache.put(cls.getName(), cls);
    }

    protected void removeClassCacheEntry(String name) {
        this.classCache.remove(name);
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }

    protected boolean isRecompilable(Class cls) {
        if (cls == null) {
            return true;
        }
        if (cls.getClassLoader() == this) {
            return false;
        }
        if (this.recompile == null && !this.config.getRecompileGroovySource()) {
            return false;
        }
        if (this.recompile != null && !this.recompile.booleanValue()) {
            return false;
        }
        if (!GroovyObject.class.isAssignableFrom(cls)) {
            return false;
        }
        long timestamp = this.getTimeStamp(cls);
        return timestamp != Long.MAX_VALUE;
    }

    public void setShouldRecompile(Boolean mode) {
        this.recompile = mode;
    }

    public Boolean isShouldRecompile() {
        return this.recompile;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Class loadClass(String name, boolean lookupScriptFiles, boolean preferClassOverScript, boolean resolve) throws ClassNotFoundException, CompilationFailedException {
        String className;
        int i2;
        Class cls = this.getClassCacheEntry(name);
        boolean recompile = this.isRecompilable(cls);
        if (!recompile) {
            return cls;
        }
        ClassNotFoundException last = null;
        try {
            Class<?> parentClassLoaderClass = super.loadClass(name, resolve);
            if (cls != parentClassLoaderClass) {
                return parentClassLoaderClass;
            }
        }
        catch (ClassNotFoundException cnfe) {
            last = cnfe;
        }
        catch (NoClassDefFoundError ncdfe) {
            if (ncdfe.getMessage().indexOf("wrong name") > 0) {
                last = new ClassNotFoundException(name);
            }
            throw ncdfe;
        }
        SecurityManager sm = System.getSecurityManager();
        if (sm != null && (i2 = (className = name.replace('/', '.')).lastIndexOf(46)) != -1 && !className.startsWith("sun.reflect.")) {
            sm.checkPackageAccess(className.substring(0, i2));
        }
        if (cls != null && preferClassOverScript) {
            return cls;
        }
        if (lookupScriptFiles) {
            try {
                Class classCacheEntry = this.getClassCacheEntry(name);
                if (classCacheEntry != cls) {
                    Class i2 = classCacheEntry;
                    return i2;
                }
                URL source = this.resourceLoader.loadGroovySource(name);
                Class oldClass = cls;
                cls = null;
                cls = this.recompile(source, name, oldClass);
            }
            catch (IOException ioe) {
                last = new ClassNotFoundException("IOException while opening groovy source: " + name, ioe);
            }
            finally {
                if (cls == null) {
                    this.removeClassCacheEntry(name);
                } else {
                    this.setClassCacheEntry(cls);
                }
            }
        }
        if (cls == null) {
            if (last == null) {
                throw new AssertionError(true);
            }
            throw last;
        }
        return cls;
    }

    protected Class recompile(URL source, String className, Class oldClass) throws CompilationFailedException, IOException {
        if (source != null && (oldClass == null || this.isSourceNewer(source, oldClass))) {
            String name = source.toExternalForm();
            this.sourceCache.remove(name);
            if (GroovyClassLoader.isFile(source)) {
                try {
                    return this.parseClass(new GroovyCodeSource(new File(source.toURI()), this.sourceEncoding));
                }
                catch (URISyntaxException uRISyntaxException) {
                    // empty catch block
                }
            }
            return this.parseClass(new InputStreamReader(URLStreams.openUncachedStream(source), this.sourceEncoding), name);
        }
        return oldClass;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return this.loadClass(name, false);
    }

    protected Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
        return this.loadClass(name, true, true, resolve);
    }

    protected long getTimeStamp(Class cls) {
        return Verifier.getTimestamp(cls);
    }

    private static String decodeFileName(String fileName) {
        String decodedFile = fileName;
        try {
            decodedFile = URLDecoder.decode(fileName, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            System.err.println("Encountered an invalid encoding scheme when trying to use URLDecoder.decode() inside of the GroovyClassLoader.decodeFileName() method.  Returning the unencoded URL.");
            System.err.println("Please note that if you encounter this error and you have spaces in your directory you will run into issues.  Refer to GROOVY-1787 for description of this bug.");
        }
        return decodedFile;
    }

    private static boolean isFile(URL ret) {
        return ret != null && ret.getProtocol().equals("file");
    }

    private static File getFileForUrl(URL ret, String filename) {
        String fileWithoutPackage = filename;
        if (fileWithoutPackage.indexOf(47) != -1) {
            int index = fileWithoutPackage.lastIndexOf(47);
            fileWithoutPackage = fileWithoutPackage.substring(index + 1);
        }
        return GroovyClassLoader.fileReallyExists(ret, fileWithoutPackage);
    }

    private static File fileReallyExists(URL ret, String fileWithoutPackage) {
        File path;
        try {
            path = new File(ret.toURI());
        }
        catch (URISyntaxException e) {
            path = new File(GroovyClassLoader.decodeFileName(ret.getFile()));
        }
        path = path.getParentFile();
        File file = new File(path, fileWithoutPackage);
        if (file.exists()) {
            try {
                String caseSensitiveName = file.getCanonicalPath();
                int index = caseSensitiveName.lastIndexOf(File.separator);
                if (index != -1) {
                    caseSensitiveName = caseSensitiveName.substring(index + 1);
                }
                if (fileWithoutPackage.equals(caseSensitiveName)) {
                    return file;
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return null;
    }

    private URL getSourceFile(String name, String extension) {
        String filename = name.replace('.', '/') + "." + extension;
        URL ret = this.getResource(filename);
        if (GroovyClassLoader.isFile(ret) && GroovyClassLoader.getFileForUrl(ret, filename) == null) {
            return null;
        }
        return ret;
    }

    protected boolean isSourceNewer(URL source, Class cls) throws IOException {
        long lastMod;
        if (GroovyClassLoader.isFile(source)) {
            String path = source.getPath().replace('/', File.separatorChar).replace('|', ':');
            File file = new File(path);
            lastMod = file.lastModified();
        } else {
            URLConnection conn = source.openConnection();
            lastMod = conn.getLastModified();
            conn.getInputStream().close();
        }
        long classTime = this.getTimeStamp(cls);
        return classTime + (long)this.config.getMinimumRecompilationInterval() < lastMod;
    }

    public void addClasspath(String path) {
        AccessController.doPrivileged(() -> {
            URL[] urls;
            URI newURI;
            try {
                newURI = new URI(path);
                newURI.toURL();
            }
            catch (IllegalArgumentException | MalformedURLException | URISyntaxException e) {
                newURI = new File(path).toURI();
            }
            for (URL url : urls = this.getURLs()) {
                try {
                    if (!newURI.equals(url.toURI())) continue;
                    return null;
                }
                catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                this.addURL(newURI.toURL());
            }
            catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            return null;
        });
    }

    public Class[] getLoadedClasses() {
        return this.classCache.values().toArray(EMPTY_CLASS_ARRAY);
    }

    public void clearCache() {
        Map<String, Class> clearedClasses = this.classCache.clearAll();
        this.sourceCache.clear();
        for (Map.Entry<String, Class> entry : clearedClasses.entrySet()) {
            InvokerHelper.removeClass(entry.getValue());
        }
    }

    @Override
    public void close() throws IOException {
        super.close();
        this.clearCache();
    }

    public static class ClassCollector
    implements CompilationUnit.ClassgenCallback {
        private Class generatedClass;
        private final GroovyClassLoader cl;
        private final SourceUnit su;
        private final CompilationUnit unit;
        private final Collection<Class> loadedClasses;

        protected ClassCollector(InnerLoader cl, CompilationUnit unit, SourceUnit su) {
            this.cl = cl;
            this.unit = unit;
            this.loadedClasses = new ArrayList<Class>();
            this.su = su;
        }

        public GroovyClassLoader getDefiningClassLoader() {
            return this.cl;
        }

        protected Class createClass(byte[] code, ClassNode classNode) {
            BytecodeProcessor bytecodePostprocessor = this.unit.getConfiguration().getBytecodePostprocessor();
            byte[] fcode = code;
            if (bytecodePostprocessor != null) {
                fcode = bytecodePostprocessor.processBytecode(classNode.getName(), fcode);
            }
            GroovyClassLoader cl = this.getDefiningClassLoader();
            Class theClass = cl.defineClass(classNode.getName(), fcode, 0, fcode.length, this.unit.getAST().getCodeSource());
            this.loadedClasses.add(theClass);
            if (this.generatedClass == null) {
                ModuleNode mn = classNode.getModule();
                SourceUnit msu = null;
                if (mn != null) {
                    msu = mn.getContext();
                }
                ClassNode main = null;
                if (mn != null) {
                    main = mn.getClasses().get(0);
                }
                if (msu == this.su && main == classNode) {
                    this.generatedClass = theClass;
                }
            }
            return theClass;
        }

        protected Class onClassNode(ClassWriter classWriter, ClassNode classNode) {
            byte[] code = classWriter.toByteArray();
            return this.createClass(code, classNode);
        }

        @Override
        public void call(ClassVisitor classWriter, ClassNode classNode) {
            this.onClassNode((ClassWriter)classWriter, classNode);
        }

        public Collection getLoadedClasses() {
            return this.loadedClasses;
        }
    }

    private static class TimestampAdder
    implements CompilationUnit.IPrimaryClassNodeOperation,
    Opcodes {
        private static final TimestampAdder INSTANCE = new TimestampAdder();

        private TimestampAdder() {
        }

        protected void addTimeStamp(ClassNode node) {
            if (node.getDeclaredField("__timeStamp") == null) {
                FieldNode timeTagField = new FieldNode("__timeStamp", 4105, ClassHelper.long_TYPE, node, new ConstantExpression(System.currentTimeMillis()));
                timeTagField.setSynthetic(true);
                node.addField(timeTagField);
                timeTagField = new FieldNode("__timeStamp__239_neverHappen" + System.currentTimeMillis(), 4105, ClassHelper.long_TYPE, node, new ConstantExpression(0L));
                timeTagField.setSynthetic(true);
                node.addField(timeTagField);
            }
        }

        @Override
        public void call(SourceUnit source, GeneratorContext context, ClassNode classNode) throws CompilationFailedException {
            if ((classNode.getModifiers() & 0x200) > 0) {
                return;
            }
            if (!(classNode instanceof InnerClassNode)) {
                this.addTimeStamp(classNode);
            }
        }
    }

    public static class InnerLoader
    extends GroovyClassLoader {
        private final GroovyClassLoader delegate;
        private final long timeStamp;

        public InnerLoader(GroovyClassLoader delegate) {
            super(delegate);
            this.delegate = delegate;
            this.timeStamp = System.currentTimeMillis();
        }

        @Override
        public void addClasspath(String path) {
            this.delegate.addClasspath(path);
        }

        @Override
        public void clearCache() {
            this.delegate.clearCache();
        }

        @Override
        public URL findResource(String name) {
            return this.delegate.findResource(name);
        }

        @Override
        public Enumeration<URL> findResources(String name) throws IOException {
            return this.delegate.findResources(name);
        }

        @Override
        public Class[] getLoadedClasses() {
            return this.delegate.getLoadedClasses();
        }

        @Override
        public URL getResource(String name) {
            return this.delegate.getResource(name);
        }

        @Override
        public InputStream getResourceAsStream(String name) {
            return this.delegate.getResourceAsStream(name);
        }

        @Override
        public GroovyResourceLoader getResourceLoader() {
            return this.delegate.getResourceLoader();
        }

        @Override
        public URL[] getURLs() {
            return this.delegate.getURLs();
        }

        @Override
        public Class loadClass(String name, boolean lookupScriptFiles, boolean preferClassOverScript, boolean resolve) throws ClassNotFoundException, CompilationFailedException {
            Class<?> c = this.findLoadedClass(name);
            if (c != null) {
                return c;
            }
            return this.delegate.loadClass(name, lookupScriptFiles, preferClassOverScript, resolve);
        }

        @Override
        public Class parseClass(GroovyCodeSource codeSource, boolean shouldCache) throws CompilationFailedException {
            return this.delegate.parseClass(codeSource, shouldCache);
        }

        @Override
        public void setResourceLoader(GroovyResourceLoader resourceLoader) {
            this.delegate.setResourceLoader(resourceLoader);
        }

        @Override
        public void addURL(URL url) {
            this.delegate.addURL(url);
        }

        @Override
        public Class defineClass(ClassNode classNode, String file, String newCodeBase) {
            return this.delegate.defineClass(classNode, file, newCodeBase);
        }

        @Override
        public Class parseClass(File file) throws CompilationFailedException, IOException {
            return this.delegate.parseClass(file);
        }

        @Override
        public Class parseClass(String text, String fileName) throws CompilationFailedException {
            return this.delegate.parseClass(text, fileName);
        }

        @Override
        public Class parseClass(String text) throws CompilationFailedException {
            return this.delegate.parseClass(text);
        }

        @Override
        public String generateScriptName() {
            return this.delegate.generateScriptName();
        }

        @Override
        public Class parseClass(Reader reader, String fileName) throws CompilationFailedException {
            return this.delegate.parseClass(reader, fileName);
        }

        @Override
        public Class parseClass(GroovyCodeSource codeSource) throws CompilationFailedException {
            return this.delegate.parseClass(codeSource);
        }

        @Override
        public Class defineClass(String name, byte[] b) {
            return this.delegate.defineClass(name, b);
        }

        @Override
        public Class loadClass(String name, boolean lookupScriptFiles, boolean preferClassOverScript) throws ClassNotFoundException, CompilationFailedException {
            return this.delegate.loadClass(name, lookupScriptFiles, preferClassOverScript);
        }

        @Override
        public void setShouldRecompile(Boolean mode) {
            this.delegate.setShouldRecompile(mode);
        }

        @Override
        public Boolean isShouldRecompile() {
            return this.delegate.isShouldRecompile();
        }

        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            return this.delegate.loadClass(name);
        }

        @Override
        public Enumeration<URL> getResources(String name) throws IOException {
            return this.delegate.getResources(name);
        }

        @Override
        public void setDefaultAssertionStatus(boolean enabled) {
            this.delegate.setDefaultAssertionStatus(enabled);
        }

        @Override
        public void setPackageAssertionStatus(String packageName, boolean enabled) {
            this.delegate.setPackageAssertionStatus(packageName, enabled);
        }

        @Override
        public void setClassAssertionStatus(String className, boolean enabled) {
            this.delegate.setClassAssertionStatus(className, enabled);
        }

        @Override
        public void clearAssertionStatus() {
            this.delegate.clearAssertionStatus();
        }

        @Override
        public void close() throws IOException {
            try {
                super.close();
            }
            finally {
                this.delegate.close();
            }
        }

        public long getTimeStamp() {
            return this.timeStamp;
        }
    }
}

