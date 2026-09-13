/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.apache.commons.jexl3.scripting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import javax.script.AbstractScriptEngine;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.scripting.JexlScriptEngineFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JexlScriptEngine
extends AbstractScriptEngine
implements Compilable {
    private static final Log LOG = LogFactory.getLog(JexlScriptEngine.class);
    private static final int CACHE_SIZE = 512;
    public static final String CONTEXT_KEY = "context";
    public static final String JEXL_OBJECT_KEY = "JEXL";
    private final JexlScriptObject jexlObject;
    private final ScriptEngineFactory parentFactory;
    private final JexlEngine jexlEngine;

    public JexlScriptEngine() {
        this(FactorySingletonHolder.DEFAULT_FACTORY);
    }

    public JexlScriptEngine(ScriptEngineFactory factory) {
        if (factory == null) {
            throw new NullPointerException("ScriptEngineFactory must not be null");
        }
        this.parentFactory = factory;
        this.jexlEngine = EngineSingletonHolder.DEFAULT_ENGINE;
        this.jexlObject = new JexlScriptObject();
    }

    @Override
    public Bindings createBindings() {
        return new SimpleBindings();
    }

    @Override
    public Object eval(Reader reader, ScriptContext context) throws ScriptException {
        if (reader == null || context == null) {
            throw new NullPointerException("script and context must be non-null");
        }
        return this.eval(JexlScriptEngine.readerToString(reader), context);
    }

    @Override
    public Object eval(String script, ScriptContext context) throws ScriptException {
        if (script == null || context == null) {
            throw new NullPointerException("script and context must be non-null");
        }
        context.setAttribute(CONTEXT_KEY, context, 100);
        try {
            JexlScript jexlScript = this.jexlEngine.createScript(script);
            JexlContextWrapper ctxt = new JexlContextWrapper(context);
            return jexlScript.execute(ctxt);
        }
        catch (Exception e) {
            throw new ScriptException(e.toString());
        }
    }

    @Override
    public ScriptEngineFactory getFactory() {
        return this.parentFactory;
    }

    @Override
    public CompiledScript compile(String script) throws ScriptException {
        if (script == null) {
            throw new NullPointerException("script must be non-null");
        }
        try {
            JexlScript jexlScript = this.jexlEngine.createScript(script);
            return new JexlCompiledScript(jexlScript);
        }
        catch (Exception e) {
            throw new ScriptException(e.toString());
        }
    }

    @Override
    public CompiledScript compile(Reader script) throws ScriptException {
        if (script == null) {
            throw new NullPointerException("script must be non-null");
        }
        return this.compile(JexlScriptEngine.readerToString(script));
    }

    private static String readerToString(Reader scriptReader) throws ScriptException {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = scriptReader instanceof BufferedReader ? (BufferedReader)scriptReader : new BufferedReader(scriptReader);
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append('\n');
            }
            return buffer.toString();
        }
        catch (IOException e) {
            throw new ScriptException(e);
        }
    }

    private final class JexlCompiledScript
    extends CompiledScript {
        private final JexlScript script;

        private JexlCompiledScript(JexlScript theScript) {
            this.script = theScript;
        }

        public String toString() {
            return this.script.getSourceText();
        }

        @Override
        public Object eval(ScriptContext context) throws ScriptException {
            context.setAttribute(JexlScriptEngine.CONTEXT_KEY, context, 100);
            try {
                JexlContextWrapper ctxt = new JexlContextWrapper(context);
                return this.script.execute(ctxt);
            }
            catch (Exception e) {
                throw new ScriptException(e.toString());
            }
        }

        @Override
        public ScriptEngine getEngine() {
            return JexlScriptEngine.this;
        }
    }

    private final class JexlContextWrapper
    implements JexlContext {
        private final ScriptContext scriptContext;

        private JexlContextWrapper(ScriptContext theContext) {
            this.scriptContext = theContext;
        }

        @Override
        public Object get(String name) {
            Object o = this.scriptContext.getAttribute(name);
            if (JexlScriptEngine.JEXL_OBJECT_KEY.equals(name)) {
                if (o != null) {
                    LOG.warn((Object)"JEXL is a reserved variable name, user defined value is ignored");
                }
                return JexlScriptEngine.this.jexlObject;
            }
            return o;
        }

        @Override
        public void set(String name, Object value) {
            int scope = this.scriptContext.getAttributesScope(name);
            if (scope == -1) {
                scope = 100;
            }
            this.scriptContext.getBindings(scope).put(name, value);
        }

        @Override
        public boolean has(String name) {
            Bindings bnd = this.scriptContext.getBindings(100);
            return bnd.containsKey(name);
        }
    }

    private static class EngineSingletonHolder {
        private static final JexlEngine DEFAULT_ENGINE = new JexlBuilder().logger(JexlScriptEngine.access$500()).cache(512).create();

        private EngineSingletonHolder() {
        }
    }

    private static class FactorySingletonHolder {
        private static final JexlScriptEngineFactory DEFAULT_FACTORY = new JexlScriptEngineFactory();

        private FactorySingletonHolder() {
        }
    }

    public class JexlScriptObject {
        public JexlEngine getEngine() {
            return JexlScriptEngine.this.jexlEngine;
        }

        public PrintWriter getOut() {
            Writer out = JexlScriptEngine.this.context.getWriter();
            if (out instanceof PrintWriter) {
                return (PrintWriter)out;
            }
            if (out != null) {
                return new PrintWriter(out, true);
            }
            return null;
        }

        public PrintWriter getErr() {
            Writer error = JexlScriptEngine.this.context.getErrorWriter();
            if (error instanceof PrintWriter) {
                return (PrintWriter)error;
            }
            if (error != null) {
                return new PrintWriter(error, true);
            }
            return null;
        }

        public Reader getIn() {
            return JexlScriptEngine.this.context.getReader();
        }

        public Class<System> getSystem() {
            return System.class;
        }

        public Log getLogger() {
            return LOG;
        }
    }
}

