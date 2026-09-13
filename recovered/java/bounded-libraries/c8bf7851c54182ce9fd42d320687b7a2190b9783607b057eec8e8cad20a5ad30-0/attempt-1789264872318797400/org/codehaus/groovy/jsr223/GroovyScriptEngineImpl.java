/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Binding
 *  groovy.lang.Closure
 *  groovy.lang.DelegatingMetaClass
 *  groovy.lang.GroovyClassLoader
 *  groovy.lang.MetaClass
 *  groovy.lang.MissingMethodException
 *  groovy.lang.MissingPropertyException
 *  groovy.lang.Script
 *  groovy.lang.Tuple
 *  org.codehaus.groovy.control.CompilationFailedException
 *  org.codehaus.groovy.control.CompilerConfiguration
 *  org.codehaus.groovy.runtime.InvokerHelper
 *  org.codehaus.groovy.runtime.MetaClassHelper
 *  org.codehaus.groovy.runtime.MethodClosure
 *  org.codehaus.groovy.util.ManagedConcurrentValueMap
 *  org.codehaus.groovy.util.ReferenceBundle
 */
package org.codehaus.groovy.jsr223;

import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.DelegatingMetaClass;
import groovy.lang.GroovyClassLoader;
import groovy.lang.MetaClass;
import groovy.lang.MissingMethodException;
import groovy.lang.MissingPropertyException;
import groovy.lang.Script;
import groovy.lang.Tuple;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.script.AbstractScriptEngine;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.jsr223.GroovyCompiledScript;
import org.codehaus.groovy.jsr223.GroovyScriptEngineFactory;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.runtime.MetaClassHelper;
import org.codehaus.groovy.runtime.MethodClosure;
import org.codehaus.groovy.util.ManagedConcurrentValueMap;
import org.codehaus.groovy.util.ReferenceBundle;

public class GroovyScriptEngineImpl
extends AbstractScriptEngine
implements Compilable,
Invocable {
    private static boolean debug = false;
    private final ManagedConcurrentValueMap<String, Class<?>> classMap = new ManagedConcurrentValueMap(ReferenceBundle.getSoftBundle());
    private final ManagedConcurrentValueMap<String, Closure<?>> globalClosures = new ManagedConcurrentValueMap(ReferenceBundle.getHardBundle());
    private GroovyClassLoader loader;
    private volatile GroovyScriptEngineFactory factory;
    private static int counter = 0;

    public GroovyScriptEngineImpl() {
        this(GroovyScriptEngineImpl.createClassLoader());
    }

    private static GroovyClassLoader createClassLoader() {
        return AccessController.doPrivileged(new PrivilegedAction<GroovyClassLoader>(){

            @Override
            public GroovyClassLoader run() {
                return new GroovyClassLoader(GroovyScriptEngineImpl.getParentLoader(), new CompilerConfiguration(CompilerConfiguration.DEFAULT));
            }
        });
    }

    public GroovyScriptEngineImpl(GroovyClassLoader classLoader) {
        if (classLoader == null) {
            throw new IllegalArgumentException("GroovyClassLoader is null");
        }
        this.loader = classLoader;
    }

    GroovyScriptEngineImpl(GroovyScriptEngineFactory factory) {
        this();
        this.factory = factory;
    }

    @Override
    public Object eval(Reader reader, ScriptContext ctx) throws ScriptException {
        return this.eval(GroovyScriptEngineImpl.readFully(reader), ctx);
    }

    @Override
    public Object eval(String script, ScriptContext ctx) throws ScriptException {
        try {
            String val = (String)ctx.getAttribute("#jsr223.groovy.engine.keep.globals", 100);
            ReferenceBundle bundle = ReferenceBundle.getHardBundle();
            if (val != null && val.length() > 0) {
                if (val.equalsIgnoreCase("soft")) {
                    bundle = ReferenceBundle.getSoftBundle();
                } else if (val.equalsIgnoreCase("weak")) {
                    bundle = ReferenceBundle.getWeakBundle();
                } else if (val.equalsIgnoreCase("phantom")) {
                    bundle = ReferenceBundle.getPhantomBundle();
                }
            }
            this.globalClosures.setBundle(bundle);
        }
        catch (ClassCastException val) {
            // empty catch block
        }
        try {
            Class<?> clazz = this.getScriptClass(script, ctx);
            if (clazz == null) {
                throw new ScriptException("Script class is null");
            }
            return this.eval(clazz, ctx);
        }
        catch (Exception e) {
            if (debug) {
                e.printStackTrace();
            }
            throw new ScriptException(e);
        }
    }

    @Override
    public Bindings createBindings() {
        return new SimpleBindings();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ScriptEngineFactory getFactory() {
        if (this.factory == null) {
            GroovyScriptEngineImpl groovyScriptEngineImpl = this;
            synchronized (groovyScriptEngineImpl) {
                if (this.factory == null) {
                    this.factory = new GroovyScriptEngineFactory();
                }
            }
        }
        return this.factory;
    }

    @Override
    public CompiledScript compile(String scriptSource) throws ScriptException {
        try {
            return new GroovyCompiledScript(this, this.getScriptClass(scriptSource, this.context));
        }
        catch (CompilationFailedException ee) {
            throw new ScriptException((Exception)((Object)ee));
        }
    }

    @Override
    public CompiledScript compile(Reader reader) throws ScriptException {
        return this.compile(GroovyScriptEngineImpl.readFully(reader));
    }

    @Override
    public Object invokeFunction(String name, Object ... args) throws ScriptException, NoSuchMethodException {
        return this.invokeImpl(null, name, args);
    }

    @Override
    public Object invokeMethod(Object thiz, String name, Object ... args) throws ScriptException, NoSuchMethodException {
        if (thiz == null) {
            throw new IllegalArgumentException("script object is null");
        }
        return this.invokeImpl(thiz, name, args);
    }

    @Override
    public <T> T getInterface(Class<T> clazz) {
        return this.makeInterface(null, clazz);
    }

    @Override
    public <T> T getInterface(Object thiz, Class<T> clazz) {
        if (thiz == null) {
            throw new IllegalArgumentException("script object is null");
        }
        return this.makeInterface(thiz, clazz);
    }

    Object eval(Class<?> scriptClass, final ScriptContext ctx) throws ScriptException {
        Binding binding = new Binding(ctx.getBindings(100)){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            public Object getVariable(String name) {
                ScriptContext scriptContext = ctx;
                synchronized (scriptContext) {
                    Writer writer;
                    int scope = ctx.getAttributesScope(name);
                    if (scope != -1) {
                        return ctx.getAttribute(name, scope);
                    }
                    if ("out".equals(name) && (writer = ctx.getWriter()) != null) {
                        return writer instanceof PrintWriter ? (PrintWriter)writer : new PrintWriter(writer, true);
                    }
                    if ("context".equals(name)) {
                        return ctx;
                    }
                }
                throw new MissingPropertyException(name, ((Object)((Object)this)).getClass());
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            public void setVariable(String name, Object value) {
                ScriptContext scriptContext = ctx;
                synchronized (scriptContext) {
                    int scope = ctx.getAttributesScope(name);
                    if (scope == -1) {
                        scope = 100;
                    }
                    ctx.setAttribute(name, value, scope);
                }
            }
        };
        try {
            Method[] methods;
            if (!Script.class.isAssignableFrom(scriptClass)) {
                return scriptClass;
            }
            Script scriptObject = InvokerHelper.createScript(scriptClass, (Binding)binding);
            for (Method m : methods = scriptClass.getMethods()) {
                String name = m.getName();
                this.globalClosures.put((Object)name, (Object)new MethodClosure((Object)scriptObject, name));
            }
            MetaClass oldMetaClass = scriptObject.getMetaClass();
            scriptObject.setMetaClass((MetaClass)new DelegatingMetaClass(oldMetaClass){

                public Object invokeMethod(Object object, String name, Object args) {
                    if (args == null) {
                        return this.invokeMethod(object, name, MetaClassHelper.EMPTY_ARRAY);
                    }
                    if (args instanceof Tuple) {
                        return this.invokeMethod(object, name, ((Tuple)args).toArray());
                    }
                    if (args instanceof Object[]) {
                        return this.invokeMethod(object, name, (Object[])args);
                    }
                    return this.invokeMethod(object, name, new Object[]{args});
                }

                public Object invokeMethod(Object object, String name, Object[] args) {
                    try {
                        return super.invokeMethod(object, name, args);
                    }
                    catch (MissingMethodException mme) {
                        return GroovyScriptEngineImpl.this.callGlobal(name, args, ctx);
                    }
                }

                public Object invokeStaticMethod(Object object, String name, Object[] args) {
                    try {
                        return super.invokeStaticMethod(object, name, args);
                    }
                    catch (MissingMethodException mme) {
                        return GroovyScriptEngineImpl.this.callGlobal(name, args, ctx);
                    }
                }
            });
            return scriptObject.run();
        }
        catch (Exception e) {
            throw new ScriptException(e);
        }
    }

    Class<?> getScriptClass(String script) throws CompilationFailedException {
        return this.getScriptClass(script, null);
    }

    Class<?> getScriptClass(String script, ScriptContext context) throws CompilationFailedException {
        Class clazz = (Class)this.classMap.get((Object)script);
        if (clazz != null) {
            return clazz;
        }
        clazz = this.loader.parseClass(script, GroovyScriptEngineImpl.generateScriptName(context));
        this.classMap.put((Object)script, (Object)clazz);
        return clazz;
    }

    public void setClassLoader(GroovyClassLoader classLoader) {
        this.loader = classLoader;
    }

    public GroovyClassLoader getClassLoader() {
        return this.loader;
    }

    private Object invokeImpl(Object thiz, String name, Object ... args) throws ScriptException, NoSuchMethodException {
        if (name == null) {
            throw new NullPointerException("method name is null");
        }
        try {
            if (thiz != null) {
                return InvokerHelper.invokeMethod((Object)thiz, (String)name, (Object)args);
            }
            return this.callGlobal(name, args);
        }
        catch (MissingMethodException mme) {
            throw new NoSuchMethodException(mme.getMessage());
        }
        catch (Exception e) {
            throw new ScriptException(e);
        }
    }

    private Object invokeImplSafe(Object thiz, String name, Object ... args) {
        if (name == null) {
            throw new NullPointerException("method name is null");
        }
        try {
            if (thiz != null) {
                return InvokerHelper.invokeMethod((Object)thiz, (String)name, (Object)args);
            }
            return this.callGlobal(name, args);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object callGlobal(String name, Object[] args) {
        return this.callGlobal(name, args, this.context);
    }

    private Object callGlobal(String name, Object[] args, ScriptContext ctx) {
        Closure closure = (Closure)this.globalClosures.get((Object)name);
        if (closure != null) {
            return closure.call(args);
        }
        Object value = ctx.getAttribute(name);
        if (value instanceof Closure) {
            return ((Closure)value).call(args);
        }
        throw new MissingMethodException(name, this.getClass(), args);
    }

    private static synchronized String generateScriptName(ScriptContext context) {
        Object filename;
        if (context != null && (filename = context.getAttribute("javax.script.filename")) != null) {
            return filename.toString();
        }
        return "Script" + ++counter + ".groovy";
    }

    private <T> T makeInterface(Object obj, Class<T> clazz) {
        Object thiz = obj;
        if (clazz == null || !clazz.isInterface()) {
            throw new IllegalArgumentException("interface Class expected");
        }
        return (T)Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, (proxy, m, args) -> this.invokeImplSafe(thiz, m.getName(), args));
    }

    private static ClassLoader getParentLoader() {
        ClassLoader ctxtLoader = Thread.currentThread().getContextClassLoader();
        try {
            Class<?> c = ctxtLoader.loadClass(Script.class.getName());
            if (c == Script.class) {
                return ctxtLoader;
            }
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        return Script.class.getClassLoader();
    }

    private static String readFully(Reader reader) throws ScriptException {
        char[] arr = new char[8192];
        StringBuilder buf = new StringBuilder();
        try {
            int numChars;
            while ((numChars = reader.read(arr, 0, arr.length)) > 0) {
                buf.append(arr, 0, numChars);
            }
        }
        catch (IOException exp) {
            throw new ScriptException(exp);
        }
        return buf.toString();
    }
}

