/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import org.apache.groovy.io.StringBuilderWriter;
import org.apache.groovy.lang.annotation.Incubating;
import org.apache.groovy.util.JavaShellCompilationException;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.tools.javac.MemJavaFileObject;

@Incubating
public class JavaShell {
    private static final String MAIN_METHOD_NAME = "main";
    private static final URL[] EMPTY_URL_ARRAY = new URL[0];
    private final JavaShellClassLoader jscl;
    private final Locale locale;
    private final Charset charset;

    public JavaShell() {
        this(null);
    }

    public JavaShell(ClassLoader parentClassLoader) {
        this.jscl = new JavaShellClassLoader(EMPTY_URL_ARRAY, null == parentClassLoader ? JavaShell.class.getClassLoader() : parentClassLoader);
        this.locale = Locale.ENGLISH;
        this.charset = Charset.forName(CompilerConfiguration.DEFAULT.getSourceEncoding());
    }

    public void run(String className, Iterable<String> options, String src, String ... args) throws Throwable {
        Class<?> c = this.compile(className, options, src);
        Method mainMethod = c.getMethod(MAIN_METHOD_NAME, String[].class);
        mainMethod.invoke(null, new Object[]{args});
    }

    public void run(String className, String src, String ... args) throws Throwable {
        this.run(className, Collections.emptyList(), src, args);
    }

    public Class<?> compile(String className, Iterable<String> options, String src) throws IOException, ClassNotFoundException {
        this.doCompile(className, src, options);
        return this.jscl.findClass(className);
    }

    public Class<?> compile(String className, String src) throws IOException, ClassNotFoundException {
        return this.compile(className, Collections.emptyList(), src);
    }

    public Map<String, Class<?>> compileAll(String className, Iterable<String> options, String src) throws IOException, ClassNotFoundException {
        this.doCompile(className, src, options);
        LinkedHashMap classes = new LinkedHashMap();
        for (String cn : this.jscl.getClassMap().keySet()) {
            Class<?> c = this.jscl.findClass(cn);
            classes.put(cn, c);
        }
        return classes;
    }

    public Map<String, Class<?>> compileAll(String className, String src) throws IOException, ClassNotFoundException {
        return this.compileAll(className, Collections.emptyList(), src);
    }

    private void doCompile(String className, String src, Iterable<String> options) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        try (BytesJavaFileManager bjfm = new BytesJavaFileManager(compiler.getStandardFileManager(null, this.locale, this.charset));){
            StringBuilderWriter out = new StringBuilderWriter();
            JavaCompiler.CompilationTask task = compiler.getTask(out, bjfm, null, options, Collections.emptyList(), Collections.singletonList(new MemJavaFileObject(className, src)));
            task.call();
            if (bjfm.isEmpty()) {
                throw new JavaShellCompilationException(out.toString());
            }
            Map<String, byte[]> classMap = bjfm.getClassMap();
            this.jscl.setClassMap(classMap);
        }
    }

    public JavaShellClassLoader getClassLoader() {
        return this.jscl;
    }

    private static final class JavaShellClassLoader
    extends URLClassLoader {
        private Map<String, byte[]> classMap = Collections.emptyMap();
        private final Map<String, Class<?>> classCache = new ConcurrentHashMap();

        public JavaShellClassLoader(URL[] urls, ClassLoader parent) {
            super(urls, parent);
        }

        @Override
        public Class<?> findClass(String name) throws ClassNotFoundException {
            byte[] bytes = this.classMap.get(name);
            if (null != bytes) {
                return this.classCache.computeIfAbsent(name, n -> this.defineClass((String)n, bytes, 0, bytes.length));
            }
            return super.findClass(name);
        }

        public Map<String, byte[]> getClassMap() {
            return this.classMap;
        }

        public void setClassMap(Map<String, byte[]> classMap) {
            this.classMap = classMap;
        }
    }

    private static final class BytesJavaFileManager
    extends ForwardingJavaFileManager<StandardJavaFileManager> {
        private final Map<String, BytesJavaFileObject> fileObjectMap = new HashMap<String, BytesJavaFileObject>();
        private Map<String, byte[]> classMap;

        public BytesJavaFileManager(StandardJavaFileManager sjfm) {
            super(sjfm);
        }

        public boolean isEmpty() {
            return this.fileObjectMap.isEmpty();
        }

        @Override
        public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location, String className, JavaFileObject.Kind kind, FileObject sibling) {
            BytesJavaFileObject bjfo = new BytesJavaFileObject(className, kind);
            this.fileObjectMap.put(className, bjfo);
            return bjfo;
        }

        public Map<String, byte[]> getClassMap() {
            if (this.classMap != null) {
                return this.classMap;
            }
            this.classMap = new LinkedHashMap<String, byte[]>();
            this.fileObjectMap.forEach((key, value) -> this.classMap.put((String)key, value.getBytes()));
            return Collections.unmodifiableMap(this.classMap);
        }
    }

    private static class BytesJavaFileObject
    extends SimpleJavaFileObject {
        private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

        public BytesJavaFileObject(String name, JavaFileObject.Kind kind) {
            super(URI.create("string:///" + name.replace('.', '/') + kind.extension), kind);
        }

        @Override
        public OutputStream openOutputStream() {
            return this.baos;
        }

        public byte[] getBytes() {
            return this.baos.toByteArray();
        }
    }
}

