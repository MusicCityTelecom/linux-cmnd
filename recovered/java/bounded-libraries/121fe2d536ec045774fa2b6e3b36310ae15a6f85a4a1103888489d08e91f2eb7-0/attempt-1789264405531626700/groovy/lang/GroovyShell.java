/*
 * Decompiled with CFR 0.152.
 */
package groovy.lang;

import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.DelegatesTo;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyCodeSource;
import groovy.lang.GroovyObjectSupport;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.ReadOnlyPropertyException;
import groovy.lang.Script;
import groovy.security.GroovyCodeSourcePermission;
import groovy.ui.GroovyMain;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.groovy.plugin.GroovyRunner;
import org.apache.groovy.plugin.GroovyRunnerRegistry;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.ResolveVisitor;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.runtime.InvokerInvocationException;

public class GroovyShell
extends GroovyObjectSupport {
    public static final String DEFAULT_CODE_BASE = "/groovy/shell";
    private static final String CONFIGURATION_CUSTOMIZER = "org.codehaus.groovy.control.customizers.builder.CompilerCustomizationBuilder";
    private final Binding context;
    private final AtomicInteger counter = new AtomicInteger(0);
    private final CompilerConfiguration config;
    private final GroovyClassLoader loader;

    public static void main(String[] args) {
        GroovyMain.main(args);
    }

    public static GroovyShell withConfig(@DelegatesTo(type="org.codehaus.groovy.control.customizers.builder.CompilerCustomizationBuilder") Closure<Void> spec) {
        CompilerConfiguration config = new CompilerConfiguration();
        try {
            Class.forName(CONFIGURATION_CUSTOMIZER).getDeclaredMethod("withConfig", CompilerConfiguration.class, Closure.class).invoke(null, config, spec);
        }
        catch (ReflectiveOperationException e) {
            throw new GroovyRuntimeException(e);
        }
        return new GroovyShell(config);
    }

    public GroovyShell() {
        this(null, new Binding());
    }

    public GroovyShell(Binding binding) {
        this(null, binding);
    }

    public GroovyShell(ClassLoader parent, CompilerConfiguration config) {
        this(parent, new Binding(), config);
    }

    public GroovyShell(CompilerConfiguration config) {
        this(new Binding(), config);
    }

    public GroovyShell(Binding binding, CompilerConfiguration config) {
        this(null, binding, config);
    }

    public GroovyShell(ClassLoader parent, Binding binding) {
        this(parent, binding, CompilerConfiguration.DEFAULT);
    }

    public GroovyShell(ClassLoader parent) {
        this(parent, new Binding(), CompilerConfiguration.DEFAULT);
    }

    public GroovyShell(ClassLoader parent, Binding binding, CompilerConfiguration config) {
        if (binding == null) {
            throw new IllegalArgumentException("Binding must not be null.");
        }
        if (config == null) {
            throw new IllegalArgumentException("Compiler configuration must not be null.");
        }
        ClassLoader parentLoader = parent != null ? parent : GroovyShell.class.getClassLoader();
        this.loader = parentLoader instanceof GroovyClassLoader && ((GroovyClassLoader)parentLoader).hasCompatibleConfiguration(config) ? (GroovyClassLoader)parentLoader : this.doPrivileged(() -> new GroovyClassLoader(parentLoader, config));
        this.context = binding;
        this.config = config;
    }

    private <T> T doPrivileged(PrivilegedAction<T> action) {
        return AccessController.doPrivileged(action);
    }

    private <T> T doPrivileged(PrivilegedExceptionAction<T> action) throws PrivilegedActionException {
        return AccessController.doPrivileged(action);
    }

    public void resetLoadedClasses() {
        this.loader.clearCache();
    }

    public GroovyShell(GroovyShell shell) {
        this((ClassLoader)shell.loader, shell.context);
    }

    public Binding getContext() {
        return this.context;
    }

    public GroovyClassLoader getClassLoader() {
        return this.loader;
    }

    @Override
    public Object getProperty(String property) {
        Object answer = this.getVariable(property);
        if (answer == null) {
            answer = super.getProperty(property);
        }
        return answer;
    }

    @Override
    public void setProperty(String property, Object newValue) {
        this.setVariable(property, newValue);
        try {
            super.setProperty(property, newValue);
        }
        catch (ReadOnlyPropertyException e) {
            throw e;
        }
        catch (GroovyRuntimeException groovyRuntimeException) {
            // empty catch block
        }
    }

    public Object run(File scriptFile, List<String> list) throws CompilationFailedException, IOException {
        return this.run(scriptFile, list.toArray(ResolveVisitor.EMPTY_STRING_ARRAY));
    }

    public Object run(String scriptText, String fileName, List<String> list) throws CompilationFailedException {
        return this.run(scriptText, fileName, list.toArray(ResolveVisitor.EMPTY_STRING_ARRAY));
    }

    public Object run(File scriptFile, String[] args) throws CompilationFailedException, IOException {
        Class scriptClass;
        String scriptName = scriptFile.getName();
        int p = scriptName.lastIndexOf(46);
        if (p++ >= 0 && scriptName.substring(p).equals("java")) {
            throw new CompilationFailedException(0, null);
        }
        final Thread thread = Thread.currentThread();
        class DoSetContext
        implements PrivilegedAction {
            final ClassLoader classLoader;

            public DoSetContext(ClassLoader loader) {
                this.classLoader = loader;
            }

            public Object run() {
                thread.setContextClassLoader(this.classLoader);
                return null;
            }
        }
        this.doPrivileged(new DoSetContext(this.loader));
        try {
            scriptClass = this.doPrivileged(() -> this.loader.parseClass(scriptFile));
        }
        catch (PrivilegedActionException pae) {
            Exception e = pae.getException();
            if (e instanceof CompilationFailedException) {
                throw (CompilationFailedException)e;
            }
            if (e instanceof IOException) {
                throw (IOException)e;
            }
            throw (RuntimeException)pae.getException();
        }
        return this.runScriptOrMainOrTestOrRunnable(scriptClass, args);
    }

    private Object runScriptOrMainOrTestOrRunnable(Class scriptClass, String[] args) {
        this.context.setProperty("args", args);
        if (scriptClass == null) {
            return null;
        }
        if (Script.class.isAssignableFrom(scriptClass)) {
            try {
                Script script = InvokerHelper.newScript(scriptClass, this.context);
                return script.run();
            }
            catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException script) {
                // empty catch block
            }
        }
        try {
            scriptClass.getMethod("main", String[].class);
            return InvokerHelper.invokeMethod(scriptClass, "main", new Object[]{args});
        }
        catch (NoSuchMethodException e) {
            if (Runnable.class.isAssignableFrom(scriptClass)) {
                return GroovyShell.runRunnable(scriptClass, args);
            }
            GroovyRunnerRegistry runnerRegistry = GroovyRunnerRegistry.getInstance();
            for (GroovyRunner runner : runnerRegistry) {
                if (!runner.canRun(scriptClass, this.loader)) continue;
                return runner.run(scriptClass, this.loader);
            }
            StringBuilder message = new StringBuilder("This script or class could not be run.\nIt should either:\n- have a main method,\n- be a JUnit test or extend GroovyTestCase,\n- implement the Runnable interface,\n- or be compatible with a registered script runner. Known runners:\n");
            if (runnerRegistry.isEmpty()) {
                message.append("  * <none>");
            } else {
                for (String key : runnerRegistry.keySet()) {
                    message.append("  * ").append(key).append("\n");
                }
            }
            throw new GroovyRuntimeException(message.toString());
        }
    }

    private static Object runRunnable(Class scriptClass, String[] args) {
        Constructor constructor = null;
        Runnable runnable = null;
        Throwable reason = null;
        try {
            constructor = scriptClass.getConstructor(String[].class);
            try {
                runnable = (Runnable)constructor.newInstance(new Object[]{args});
            }
            catch (Throwable t) {
                reason = t;
            }
        }
        catch (NoSuchMethodException e1) {
            try {
                constructor = scriptClass.getConstructor(new Class[0]);
                try {
                    runnable = (Runnable)constructor.newInstance(new Object[0]);
                }
                catch (InvocationTargetException ite) {
                    throw new InvokerInvocationException(ite.getTargetException());
                }
                catch (Throwable t) {
                    reason = t;
                }
            }
            catch (NoSuchMethodException nsme) {
                reason = nsme;
            }
        }
        if (constructor == null || runnable == null) {
            throw new GroovyRuntimeException("This script or class was runnable but could not be run. ", reason);
        }
        runnable.run();
        return null;
    }

    public Object run(String scriptText, String fileName, String[] args) throws CompilationFailedException {
        GroovyCodeSource gcs = this.doPrivileged(() -> new GroovyCodeSource(scriptText, fileName, DEFAULT_CODE_BASE));
        return this.run(gcs, args);
    }

    public Object run(GroovyCodeSource source, List<String> args) throws CompilationFailedException {
        return this.run(source, args.toArray(ResolveVisitor.EMPTY_STRING_ARRAY));
    }

    public Object run(GroovyCodeSource source, String[] args) throws CompilationFailedException {
        Class scriptClass = this.parseClass(source);
        return this.runScriptOrMainOrTestOrRunnable(scriptClass, args);
    }

    public Object run(URI source, List<String> args) throws CompilationFailedException, IOException {
        return this.run(new GroovyCodeSource(source), args.toArray(ResolveVisitor.EMPTY_STRING_ARRAY));
    }

    public Object run(URI source, String[] args) throws CompilationFailedException, IOException {
        return this.run(new GroovyCodeSource(source), args);
    }

    public Object run(Reader in, String fileName, List<String> list) throws CompilationFailedException {
        return this.run(in, fileName, list.toArray(ResolveVisitor.EMPTY_STRING_ARRAY));
    }

    public Object run(Reader in, String fileName, String[] args) throws CompilationFailedException {
        GroovyCodeSource gcs = this.doPrivileged(() -> new GroovyCodeSource(in, fileName, DEFAULT_CODE_BASE));
        Class scriptClass = this.parseClass(gcs);
        return this.runScriptOrMainOrTestOrRunnable(scriptClass, args);
    }

    public Object getVariable(String name) {
        return this.context.getVariables().get(name);
    }

    public void setVariable(String name, Object value) {
        this.context.setVariable(name, value);
    }

    public void removeVariable(String name) {
        this.context.removeVariable(name);
    }

    public Object evaluate(GroovyCodeSource codeSource) throws CompilationFailedException {
        Script script = this.parse(codeSource);
        return script.run();
    }

    public Object evaluate(String scriptText) throws CompilationFailedException {
        return this.evaluate(scriptText, this.generateScriptName(), DEFAULT_CODE_BASE);
    }

    public Object evaluate(String scriptText, String fileName) throws CompilationFailedException {
        return this.evaluate(scriptText, fileName, DEFAULT_CODE_BASE);
    }

    public Object evaluate(String scriptText, String fileName, String codeBase) throws CompilationFailedException {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new GroovyCodeSourcePermission(codeBase));
        }
        GroovyCodeSource gcs = this.doPrivileged(() -> new GroovyCodeSource(scriptText, fileName, codeBase));
        return this.evaluate(gcs);
    }

    public Object evaluate(File file) throws CompilationFailedException, IOException {
        return this.evaluate(new GroovyCodeSource(file, this.config.getSourceEncoding()));
    }

    public Object evaluate(URI uri) throws CompilationFailedException, IOException {
        return this.evaluate(new GroovyCodeSource(uri));
    }

    public Object evaluate(Reader in) throws CompilationFailedException {
        return this.evaluate(in, this.generateScriptName());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object evaluate(Reader in, String fileName) throws CompilationFailedException {
        Script script = null;
        try {
            script = this.parse(in, fileName);
            Object object = script.run();
            return object;
        }
        finally {
            if (script != null) {
                InvokerHelper.removeClass(script.getClass());
            }
        }
    }

    public Script parse(Reader reader, String fileName) throws CompilationFailedException {
        return this.parse(reader, fileName, this.context);
    }

    public Script parse(Reader reader, String fileName, Binding binding) throws CompilationFailedException {
        return this.parse(new GroovyCodeSource(reader, fileName, DEFAULT_CODE_BASE), binding);
    }

    private Class parseClass(GroovyCodeSource codeSource) throws CompilationFailedException {
        return this.loader.parseClass(codeSource, false);
    }

    public Script parse(GroovyCodeSource codeSource, Binding binding) throws CompilationFailedException {
        return InvokerHelper.createScript(this.parseClass(codeSource), binding);
    }

    public Script parse(GroovyCodeSource codeSource) throws CompilationFailedException {
        return this.parse(codeSource, this.context);
    }

    public Script parse(File file) throws CompilationFailedException, IOException {
        return this.parse(new GroovyCodeSource(file, this.config.getSourceEncoding()));
    }

    public Script parse(URI uri) throws CompilationFailedException, IOException {
        return this.parse(new GroovyCodeSource(uri));
    }

    public Script parse(String scriptText, Binding binding) throws CompilationFailedException {
        return this.parse(scriptText, this.generateScriptName(), binding);
    }

    public Script parse(String scriptText) throws CompilationFailedException {
        return this.parse(scriptText, this.context);
    }

    public Script parse(String scriptText, String fileName, Binding binding) throws CompilationFailedException {
        GroovyCodeSource gcs = this.doPrivileged(() -> new GroovyCodeSource(scriptText, fileName, DEFAULT_CODE_BASE));
        return this.parse(gcs, binding);
    }

    public Script parse(String scriptText, String fileName) throws CompilationFailedException {
        return this.parse(scriptText, fileName, this.context);
    }

    public Script parse(Reader in) throws CompilationFailedException {
        return this.parse(in, this.generateScriptName(), this.context);
    }

    public Script parse(Reader in, Binding binding) throws CompilationFailedException {
        return this.parse(in, this.generateScriptName(), binding);
    }

    protected String generateScriptName() {
        return "Script" + this.counter.incrementAndGet() + ".groovy";
    }
}

