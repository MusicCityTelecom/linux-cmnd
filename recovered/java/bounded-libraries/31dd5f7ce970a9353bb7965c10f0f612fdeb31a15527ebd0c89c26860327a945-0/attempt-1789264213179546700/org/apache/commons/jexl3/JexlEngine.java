/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.MathContext;
import java.net.URL;
import java.nio.charset.Charset;
import org.apache.commons.jexl3.JexlArithmetic;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.JexlFeatures;
import org.apache.commons.jexl3.JexlInfo;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.JxltEngine;
import org.apache.commons.jexl3.introspection.JexlUberspect;

public abstract class JexlEngine {
    public static final Object TRY_FAILED = new FailObject();
    protected static final ThreadLocal<JexlContext.ThreadLocal> CONTEXT = new ThreadLocal();
    protected static final ThreadLocal<JexlEngine> ENGINE = new ThreadLocal();
    public static final JexlFeatures DEFAULT_FEATURES = new JexlFeatures();
    public static final JexlContext EMPTY_CONTEXT = new EmptyContext();
    public static final JexlContext.NamespaceResolver EMPTY_NS = new EmptyNamespaceResolver();
    private static final int JXLT_CACHE_SIZE = 256;

    public static JexlContext.ThreadLocal getThreadContext() {
        return CONTEXT.get();
    }

    public static JexlEngine getThreadEngine() {
        return ENGINE.get();
    }

    public static void setThreadContext(JexlContext.ThreadLocal tls) {
        CONTEXT.set(tls);
    }

    public abstract Charset getCharset();

    public abstract JexlUberspect getUberspect();

    public abstract JexlArithmetic getArithmetic();

    public abstract boolean isDebug();

    public abstract boolean isSilent();

    public abstract boolean isStrict();

    public abstract boolean isCancellable();

    public abstract void setClassLoader(ClassLoader var1);

    public JxltEngine createJxltEngine() {
        return this.createJxltEngine(true);
    }

    public JxltEngine createJxltEngine(boolean noScript) {
        return this.createJxltEngine(noScript, 256, '$', '#');
    }

    public abstract JxltEngine createJxltEngine(boolean var1, int var2, char var3, char var4);

    public abstract void clearCache();

    public abstract JexlExpression createExpression(JexlInfo var1, String var2);

    public final JexlExpression createExpression(String expression) {
        return this.createExpression(null, expression);
    }

    public abstract JexlScript createScript(JexlFeatures var1, JexlInfo var2, String var3, String ... var4);

    public final JexlScript createScript(JexlInfo info, String source, String ... names) {
        return this.createScript(null, info, source, names);
    }

    public final JexlScript createScript(String scriptText) {
        return this.createScript((JexlFeatures)null, (JexlInfo)null, scriptText, (String[])null);
    }

    public final JexlScript createScript(String source, String ... names) {
        return this.createScript((JexlFeatures)null, (JexlInfo)null, source, names);
    }

    public final JexlScript createScript(File scriptFile) {
        return this.createScript((JexlFeatures)null, (JexlInfo)null, this.readSource(scriptFile), (String[])null);
    }

    public final JexlScript createScript(File scriptFile, String ... names) {
        return this.createScript((JexlFeatures)null, (JexlInfo)null, this.readSource(scriptFile), names);
    }

    public final JexlScript createScript(JexlInfo info, File scriptFile, String ... names) {
        return this.createScript(null, info, this.readSource(scriptFile), names);
    }

    public final JexlScript createScript(URL scriptUrl) {
        return this.createScript((JexlInfo)null, this.readSource(scriptUrl), (String[])null);
    }

    public final JexlScript createScript(URL scriptUrl, String ... names) {
        return this.createScript((JexlFeatures)null, (JexlInfo)null, this.readSource(scriptUrl), names);
    }

    public final JexlScript createScript(JexlInfo info, URL scriptUrl, String ... names) {
        return this.createScript(null, info, this.readSource(scriptUrl), names);
    }

    public abstract Object getProperty(Object var1, String var2);

    public abstract Object getProperty(JexlContext var1, Object var2, String var3);

    public abstract void setProperty(Object var1, String var2, Object var3);

    public abstract void setProperty(JexlContext var1, Object var2, String var3, Object var4);

    public abstract Object invokeMethod(Object var1, String var2, Object ... var3);

    public abstract <T> T newInstance(Class<? extends T> var1, Object ... var2);

    public abstract Object newInstance(String var1, Object ... var2);

    public JexlInfo createInfo(String fn, int l, int c) {
        return new JexlInfo(fn, l, c);
    }

    public JexlInfo createInfo() {
        return new JexlInfo();
    }

    protected static String toString(BufferedReader reader) throws IOException {
        String line;
        StringBuilder buffer = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            buffer.append(line).append('\n');
        }
        return buffer.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected String readSource(File file) {
        if (file == null) {
            throw new NullPointerException("source file is null");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream)new FileInputStream(file), this.getCharset()));){
            String string = JexlEngine.toString(reader);
            return string;
        }
        catch (IOException xio) {
            throw new JexlException(this.createInfo(file.toString(), 1, 1), "could not read source File", (Throwable)xio);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected String readSource(URL url) {
        if (url == null) {
            throw new NullPointerException("source URL is null");
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), this.getCharset()));){
            String string = JexlEngine.toString(reader);
            return string;
        }
        catch (IOException xio) {
            throw new JexlException(this.createInfo(url.toString(), 1, 1), "could not read source URL", (Throwable)xio);
        }
    }

    public static final class EmptyNamespaceResolver
    implements JexlContext.NamespaceResolver {
        private EmptyNamespaceResolver() {
        }

        @Override
        public Object resolveNamespace(String name) {
            return null;
        }
    }

    public static final class EmptyContext
    implements JexlContext {
        private EmptyContext() {
        }

        @Override
        public Object get(String name) {
            return null;
        }

        @Override
        public boolean has(String name) {
            return false;
        }

        @Override
        public void set(String name, Object value) {
            throw new UnsupportedOperationException("Not supported in void context.");
        }
    }

    @Deprecated
    public static interface Options {
        public Charset getCharset();

        public Boolean isSilent();

        public Boolean isStrict();

        public Boolean isStrictArithmetic();

        public Boolean isCancellable();

        public MathContext getArithmeticMathContext();

        public int getArithmeticMathScale();
    }

    private static final class FailObject {
        private FailObject() {
        }

        public String toString() {
            return "tryExecute failed";
        }
    }
}

