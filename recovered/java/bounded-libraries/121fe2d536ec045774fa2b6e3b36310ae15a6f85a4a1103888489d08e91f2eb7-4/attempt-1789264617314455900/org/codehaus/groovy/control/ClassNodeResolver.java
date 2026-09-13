/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.control;

import groovy.lang.GroovyClassLoader;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.decompiled.AsmDecompiler;
import org.codehaus.groovy.ast.decompiled.AsmReferenceResolver;
import org.codehaus.groovy.ast.decompiled.DecompiledClassNode;
import org.codehaus.groovy.classgen.Verifier;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.SourceUnit;

public class ClassNodeResolver {
    private final Map<String, ClassNode> cachedClasses = new HashMap<String, ClassNode>();
    protected static final ClassNode NO_CLASS = new ClassNode("NO_CLASS", 1, ClassHelper.OBJECT_TYPE){

        @Override
        public void setRedirect(ClassNode cn) {
            throw new GroovyBugError("This is a dummy class node only! Never use it for real classes.");
        }
    };

    public LookupResult resolveName(String name, CompilationUnit compilationUnit) {
        ClassNode type = this.getFromClassCache(name);
        if (type != null) {
            if (type == NO_CLASS) {
                return null;
            }
            return new LookupResult(null, type);
        }
        LookupResult result = this.findClassNode(name, compilationUnit);
        if (result != null) {
            if (result.isClassNode()) {
                this.cacheClass(name, result.getClassNode());
            }
            return result;
        }
        this.cacheClass(name, NO_CLASS);
        return null;
    }

    public void cacheClass(String name, ClassNode res) {
        this.cachedClasses.put(name, res);
    }

    public ClassNode getFromClassCache(String name) {
        ClassNode cached = this.cachedClasses.get(name);
        return cached;
    }

    public LookupResult findClassNode(String name, CompilationUnit compilationUnit) {
        return compilationUnit == null ? null : this.tryAsLoaderClassOrScript(name, compilationUnit);
    }

    private LookupResult tryAsLoaderClassOrScript(String name, CompilationUnit compilationUnit) {
        LookupResult result;
        GroovyClassLoader loader = compilationUnit.getClassLoader();
        Map<String, Boolean> options = compilationUnit.configuration.getOptimizationOptions();
        if (!Boolean.FALSE.equals(options.get("asmResolving")) && (result = this.findDecompiled(name, compilationUnit, loader)) != null) {
            return result;
        }
        if (!Boolean.FALSE.equals(options.get("classLoaderResolving"))) {
            return ClassNodeResolver.findByClassLoading(name, compilationUnit, loader);
        }
        return ClassNodeResolver.tryAsScript(name, compilationUnit, null);
    }

    private static LookupResult findByClassLoading(String name, CompilationUnit compilationUnit, GroovyClassLoader loader) {
        Class cls;
        try {
            cls = loader.loadClass(name, false, true);
        }
        catch (ClassNotFoundException cnfe) {
            return ClassNodeResolver.tryAsScript(name, compilationUnit, null);
        }
        catch (CompilationFailedException cfe) {
            throw new GroovyBugError("The lookup for " + name + " caused a failed compilation. There should not have been any compilation from this call.", cfe);
        }
        if (cls == null) {
            return null;
        }
        ClassNode cn = ClassHelper.make(cls);
        if (cls.getClassLoader() != loader) {
            return ClassNodeResolver.tryAsScript(name, compilationUnit, cn);
        }
        return new LookupResult(null, cn);
    }

    private LookupResult findDecompiled(String name, CompilationUnit compilationUnit, GroovyClassLoader loader) {
        ClassNode node = ClassHelper.make(name);
        if (node.isResolved()) {
            return new LookupResult(null, node);
        }
        DecompiledClassNode asmClass = null;
        String fileName = name.replace('.', '/') + ".class";
        URL resource = loader.getResource(fileName);
        if (resource != null) {
            try {
                asmClass = new DecompiledClassNode(AsmDecompiler.parseClass(resource), new AsmReferenceResolver(this, compilationUnit));
                if (!asmClass.getName().equals(name)) {
                    asmClass = null;
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        if (asmClass != null) {
            if (ClassNodeResolver.isFromAnotherClassLoader(loader, fileName)) {
                return ClassNodeResolver.tryAsScript(name, compilationUnit, asmClass);
            }
            return new LookupResult(null, asmClass);
        }
        return null;
    }

    private static boolean isFromAnotherClassLoader(GroovyClassLoader loader, String fileName) {
        ClassLoader parent = loader.getParent();
        return parent != null && parent.getResource(fileName) != null;
    }

    private static LookupResult tryAsScript(String name, CompilationUnit compilationUnit, ClassNode oldClass) {
        LookupResult lr = null;
        if (oldClass != null) {
            lr = new LookupResult(null, oldClass);
        }
        if (name.startsWith("java.")) {
            return lr;
        }
        int i = name.indexOf(36);
        if (i != -1) {
            return lr;
        }
        GroovyClassLoader gcl = compilationUnit.getClassLoader();
        URL url = null;
        try {
            url = gcl.getResourceLoader().loadGroovySource(name);
        }
        catch (MalformedURLException malformedURLException) {
            // empty catch block
        }
        if (url != null && (oldClass == null || ClassNodeResolver.isSourceNewer(url, oldClass))) {
            SourceUnit sourceUnit = compilationUnit.addSource(url);
            lr = new LookupResult(sourceUnit, null);
        }
        return lr;
    }

    private static long getTimeStamp(ClassNode cls) {
        if (!(cls instanceof DecompiledClassNode)) {
            return Verifier.getTimestamp(cls.getTypeClass());
        }
        return ((DecompiledClassNode)cls).getCompilationTimeStamp();
    }

    private static boolean isSourceNewer(URL source, ClassNode cls) {
        try {
            long lastMod;
            if (source.getProtocol().equals("file")) {
                String path = source.getPath().replace('/', File.separatorChar).replace('|', ':');
                File file = new File(path);
                lastMod = file.lastModified();
            } else {
                URLConnection conn = source.openConnection();
                lastMod = conn.getLastModified();
                conn.getInputStream().close();
            }
            return lastMod > ClassNodeResolver.getTimeStamp(cls);
        }
        catch (IOException e) {
            return false;
        }
    }

    public static class LookupResult {
        private final SourceUnit su;
        private final ClassNode cn;

        public LookupResult(SourceUnit su, ClassNode cn) {
            this.su = su;
            this.cn = cn;
            if (su == null && cn == null) {
                throw new IllegalArgumentException("Either the SourceUnit or the ClassNode must not be null.");
            }
            if (su != null && cn != null) {
                throw new IllegalArgumentException("SourceUnit and ClassNode cannot be set at the same time.");
            }
        }

        public boolean isClassNode() {
            return this.cn != null;
        }

        public boolean isSourceUnit() {
            return this.su != null;
        }

        public SourceUnit getSourceUnit() {
            return this.su;
        }

        public ClassNode getClassNode() {
            return this.cn;
        }
    }
}

