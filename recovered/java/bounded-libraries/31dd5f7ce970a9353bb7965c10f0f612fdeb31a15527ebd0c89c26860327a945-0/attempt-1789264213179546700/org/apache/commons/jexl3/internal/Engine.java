/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package org.apache.commons.jexl3.internal;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import org.apache.commons.jexl3.JexlArithmetic;
import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.JexlFeatures;
import org.apache.commons.jexl3.JexlInfo;
import org.apache.commons.jexl3.JexlOptions;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.internal.Frame;
import org.apache.commons.jexl3.internal.Interpreter;
import org.apache.commons.jexl3.internal.Scope;
import org.apache.commons.jexl3.internal.Script;
import org.apache.commons.jexl3.internal.SoftCache;
import org.apache.commons.jexl3.internal.Source;
import org.apache.commons.jexl3.internal.TemplateEngine;
import org.apache.commons.jexl3.internal.introspection.SandboxUberspect;
import org.apache.commons.jexl3.internal.introspection.Uberspect;
import org.apache.commons.jexl3.introspection.JexlMethod;
import org.apache.commons.jexl3.introspection.JexlSandbox;
import org.apache.commons.jexl3.introspection.JexlUberspect;
import org.apache.commons.jexl3.parser.ASTArrayAccess;
import org.apache.commons.jexl3.parser.ASTFunctionNode;
import org.apache.commons.jexl3.parser.ASTIdentifier;
import org.apache.commons.jexl3.parser.ASTIdentifierAccess;
import org.apache.commons.jexl3.parser.ASTJexlScript;
import org.apache.commons.jexl3.parser.ASTMethodNode;
import org.apache.commons.jexl3.parser.ASTNumberLiteral;
import org.apache.commons.jexl3.parser.ASTStringLiteral;
import org.apache.commons.jexl3.parser.JexlNode;
import org.apache.commons.jexl3.parser.Parser;
import org.apache.commons.jexl3.parser.StringProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Engine
extends JexlEngine {
    protected static final String PRAGMA_OPTIONS = "jexl.options";
    protected static final String PRAGMA_JEXLNS = "jexl.namespace.";
    protected final Log logger;
    protected final JexlUberspect uberspect;
    protected final JexlArithmetic arithmetic;
    protected final Map<String, Object> functions;
    protected final int stackOverflow;
    protected final boolean strict;
    protected final boolean safe;
    protected final boolean silent;
    protected final boolean cancellable;
    protected final boolean debug;
    protected final JexlFeatures scriptFeatures;
    protected final JexlFeatures expressionFeatures;
    protected final Charset charset;
    protected final AtomicBoolean parsing = new AtomicBoolean(false);
    protected final Parser parser = new Parser(new StringProvider(";"));
    protected final int cacheThreshold;
    protected final SoftCache<Source, ASTJexlScript> cache;
    protected volatile TemplateEngine jxlt = null;
    protected final int collectMode;
    protected final JexlOptions options;
    protected static final JexlFeatures PROPERTY_FEATURES = new JexlFeatures().localVar(false).loops(false).lambda(false).script(false).arrayReferenceExpr(false).methodCall(false).register(true);

    public Engine() {
        this(new JexlBuilder());
    }

    public Engine(JexlBuilder conf) {
        JexlSandbox sandbox;
        this.options = conf.options().copy();
        this.strict = this.options.isStrict();
        this.safe = this.options.isSafe();
        this.silent = this.options.isSilent();
        this.cancellable = Engine.option(conf.cancellable(), !this.silent && this.strict);
        this.options.setCancellable(this.cancellable);
        this.debug = Engine.option(conf.debug(), true);
        this.collectMode = conf.collectMode();
        this.stackOverflow = conf.stackOverflow() > 0 ? conf.stackOverflow() : Integer.MAX_VALUE;
        JexlUberspect uber = conf.uberspect() == null ? Engine.getUberspect(conf.logger(), conf.strategy()) : conf.uberspect();
        ClassLoader loader = conf.loader();
        if (loader != null) {
            uber.setClassLoader(loader);
        }
        this.uberspect = (sandbox = conf.sandbox()) == null ? uber : new SandboxUberspect(uber, sandbox);
        this.logger = conf.logger() == null ? LogFactory.getLog(JexlEngine.class) : conf.logger();
        this.arithmetic = conf.arithmetic() == null ? new JexlArithmetic(this.strict) : conf.arithmetic();
        this.options.setMathContext(this.arithmetic.getMathContext());
        this.options.setMathScale(this.arithmetic.getMathScale());
        this.options.setStrictArithmetic(this.arithmetic.isStrict());
        this.functions = conf.namespaces() == null ? Collections.emptyMap() : conf.namespaces();
        JexlFeatures features = conf.features() == null ? DEFAULT_FEATURES : conf.features();
        Predicate<String> nsTest = features.namespaceTest();
        Set<String> nsNames = this.functions.keySet();
        if (!nsNames.isEmpty()) {
            nsTest = nsTest == JexlFeatures.TEST_STR_FALSE ? nsNames::contains : nsTest.or(nsNames::contains);
        }
        this.expressionFeatures = new JexlFeatures(features).script(false).namespaceTest(nsTest);
        this.scriptFeatures = new JexlFeatures(features).script(true).namespaceTest(nsTest);
        this.charset = conf.charset();
        this.cache = conf.cache() <= 0 ? null : new SoftCache(conf.cache());
        this.cacheThreshold = conf.cacheThreshold();
        if (this.uberspect == null) {
            throw new IllegalArgumentException("uberspect can not be null");
        }
    }

    public static Uberspect getUberspect(Log logger, JexlUberspect.ResolverStrategy strategy) {
        if (!(logger != null && !logger.equals(LogFactory.getLog(JexlEngine.class)) || strategy != null && strategy != JexlUberspect.JEXL_STRATEGY)) {
            return UberspectHolder.UBERSPECT;
        }
        return new Uberspect(logger, strategy);
    }

    @Override
    public JexlUberspect getUberspect() {
        return this.uberspect;
    }

    @Override
    public JexlArithmetic getArithmetic() {
        return this.arithmetic;
    }

    @Override
    public boolean isDebug() {
        return this.debug;
    }

    @Override
    public boolean isSilent() {
        return this.silent;
    }

    @Override
    public boolean isStrict() {
        return this.strict;
    }

    @Override
    public boolean isCancellable() {
        return this.cancellable;
    }

    @Override
    public void setClassLoader(ClassLoader loader) {
        this.jxlt = null;
        this.uberspect.setClassLoader(loader);
        if (this.functions != null) {
            ArrayList<String> names = new ArrayList<String>(this.functions.keySet());
            for (String name : names) {
                Object functor = this.functions.get(name);
                if (!(functor instanceof Class)) continue;
                Class fclass = (Class)functor;
                try {
                    Class<?> nclass = loader.loadClass(fclass.getName());
                    if (nclass == fclass) continue;
                    this.functions.put(name, nclass);
                }
                catch (ClassNotFoundException xany) {
                    this.functions.put(name, fclass.getName());
                }
            }
        }
        if (this.cache != null) {
            this.cache.clear();
        }
    }

    @Override
    public Charset getCharset() {
        return this.charset;
    }

    private static <T> T option(T conf, T def) {
        return conf == null ? def : conf;
    }

    protected JexlOptions options(JexlContext context) {
        if (context instanceof JexlContext.OptionsHandle) {
            JexlOptions jexlo = ((JexlContext.OptionsHandle)((Object)context)).getEngineOptions();
            if (jexlo != null) {
                return jexlo.isSharedInstance() ? jexlo : jexlo.copy();
            }
        } else if (context instanceof JexlEngine.Options) {
            JexlOptions jexlo = this.options.copy();
            Engine jexl = this;
            JexlEngine.Options opts = (JexlEngine.Options)((Object)context);
            jexlo.setCancellable(Engine.option(opts.isCancellable(), ((JexlEngine)jexl).isCancellable()));
            jexlo.setSilent(Engine.option(opts.isSilent(), ((JexlEngine)jexl).isSilent()));
            jexlo.setStrict(Engine.option(opts.isStrict(), ((JexlEngine)jexl).isStrict()));
            JexlArithmetic jexla = ((JexlEngine)jexl).getArithmetic();
            jexlo.setStrictArithmetic(Engine.option(opts.isStrictArithmetic(), jexla.isStrict()));
            jexlo.setMathContext(opts.getArithmeticMathContext());
            jexlo.setMathScale(opts.getArithmeticMathScale());
            return jexlo;
        }
        return this.options;
    }

    protected JexlOptions options(ASTJexlScript script, JexlContext context) {
        JexlOptions opts = this.options(context);
        if (opts != this.options) {
            if (this.scriptFeatures.isLexical()) {
                opts.setLexical(true);
            }
            if (this.scriptFeatures.isLexicalShade()) {
                opts.setLexicalShade(true);
            }
        }
        if (script != null) {
            this.processPragmas(script, context, opts);
        }
        return opts;
    }

    protected void processPragmas(ASTJexlScript script, JexlContext context, JexlOptions opts) {
        Map<String, Object> pragmas = script.getPragmas();
        if (pragmas != null && !pragmas.isEmpty()) {
            JexlContext.PragmaProcessor processor = context instanceof JexlContext.PragmaProcessor ? (JexlContext.PragmaProcessor)((Object)context) : null;
            HashMap<String, Object> ns = null;
            for (Map.Entry<String, Object> pragma : pragmas.entrySet()) {
                String key = pragma.getKey();
                Object value = pragma.getValue();
                if (value instanceof String) {
                    String nsname;
                    if (PRAGMA_OPTIONS.equals(key)) {
                        String[] vs = value.toString().split(" ");
                        opts.setFlags(vs);
                    } else if (key.startsWith(PRAGMA_JEXLNS) && (nsname = key.substring(PRAGMA_JEXLNS.length())) != null && !nsname.isEmpty()) {
                        if (ns == null) {
                            ns = new HashMap<String, Object>(this.functions);
                        }
                        String nsclass = value.toString();
                        try {
                            ns.put(nsname, this.uberspect.getClassLoader().loadClass(nsclass));
                        }
                        catch (ClassNotFoundException e) {
                            ns.put(nsname, nsclass);
                        }
                    }
                }
                if (processor == null) continue;
                processor.processPragma(key, value);
            }
            if (ns != null) {
                opts.setNamespaces(ns);
            }
        }
    }

    public JexlOptions optionsSet(JexlOptions opts) {
        if (opts != null) {
            opts.set(this.options);
        }
        return opts;
    }

    @Override
    public TemplateEngine createJxltEngine(boolean noScript, int cacheSize, char immediate, char deferred) {
        return new TemplateEngine(this, noScript, cacheSize, immediate, deferred);
    }

    @Override
    public void clearCache() {
        if (this.cache != null) {
            this.cache.clear();
        }
    }

    protected Interpreter createInterpreter(JexlContext context, Frame frame, JexlOptions opts) {
        return new Interpreter(this, opts, context, frame);
    }

    @Override
    public Script createExpression(JexlInfo info, String expression) {
        return this.createScript(this.expressionFeatures, info, expression, null);
    }

    @Override
    public Script createScript(JexlFeatures features, JexlInfo info, String scriptText, String ... names) {
        if (scriptText == null) {
            throw new NullPointerException("source is null");
        }
        String source = this.trimSource(scriptText);
        Scope scope = names == null || names.length == 0 ? null : new Scope(null, names);
        JexlFeatures ftrs = features == null ? this.scriptFeatures : features;
        ASTJexlScript tree = this.parse(info, ftrs, source, scope);
        return new Script(this, source, tree);
    }

    @Override
    public Object getProperty(Object bean, String expr) {
        return this.getProperty(null, bean, expr);
    }

    @Override
    public Object getProperty(JexlContext context, Object bean, String expr) {
        if (context == null) {
            context = EMPTY_CONTEXT;
        }
        String src = this.trimSource(expr);
        src = "#0" + (src.charAt(0) == '[' ? "" : ".") + src;
        try {
            Scope scope = new Scope(null, "#0");
            ASTJexlScript script = this.parse(null, PROPERTY_FEATURES, src, scope);
            JexlNode node = script.jjtGetChild(0);
            Frame frame = script.createFrame(bean);
            Interpreter interpreter = this.createInterpreter(context, frame, this.options);
            return interpreter.visitLexicalNode(node, null);
        }
        catch (JexlException xjexl) {
            if (this.silent) {
                this.logger.warn((Object)xjexl.getMessage(), xjexl.getCause());
                return null;
            }
            throw xjexl.clean();
        }
    }

    @Override
    public void setProperty(Object bean, String expr, Object value) {
        this.setProperty(null, bean, expr, value);
    }

    @Override
    public void setProperty(JexlContext context, Object bean, String expr, Object value) {
        if (context == null) {
            context = EMPTY_CONTEXT;
        }
        String src = this.trimSource(expr);
        src = "#0" + (src.charAt(0) == '[' ? "" : ".") + src + "=#1";
        try {
            Scope scope = new Scope(null, "#0", "#1");
            ASTJexlScript script = this.parse(null, PROPERTY_FEATURES, src, scope);
            JexlNode node = script.jjtGetChild(0);
            Frame frame = script.createFrame(bean, value);
            Interpreter interpreter = this.createInterpreter(context, frame, this.options);
            interpreter.visitLexicalNode(node, null);
        }
        catch (JexlException xjexl) {
            if (this.silent) {
                this.logger.warn((Object)xjexl.getMessage(), xjexl.getCause());
                return;
            }
            throw xjexl.clean();
        }
    }

    @Override
    public Object invokeMethod(Object obj, String meth, Object ... args) {
        JexlException xjexl = null;
        Object result = null;
        JexlInfo info = this.debug ? this.createInfo() : null;
        try {
            JexlMethod method = this.uberspect.getMethod(obj, meth, args);
            if (method == null && this.arithmetic.narrowArguments(args)) {
                method = this.uberspect.getMethod(obj, meth, args);
            }
            if (method != null) {
                result = method.invoke(obj, args);
            } else {
                xjexl = new JexlException.Method(info, meth, args);
            }
        }
        catch (JexlException xany) {
            xjexl = xany;
        }
        catch (Exception xany) {
            xjexl = new JexlException.Method(info, meth, args, xany);
        }
        if (xjexl != null) {
            if (!this.silent) {
                throw xjexl.clean();
            }
            this.logger.warn((Object)xjexl.getMessage(), xjexl.getCause());
            result = null;
        }
        return result;
    }

    @Override
    public <T> T newInstance(Class<? extends T> clazz, Object ... args) {
        return clazz.cast(this.doCreateInstance(clazz, args));
    }

    @Override
    public Object newInstance(String clazz, Object ... args) {
        return this.doCreateInstance(clazz, args);
    }

    protected Object doCreateInstance(Object clazz, Object ... args) {
        JexlException xjexl = null;
        Object result = null;
        JexlInfo info = this.debug ? this.createInfo() : null;
        try {
            JexlMethod ctor = this.uberspect.getConstructor(clazz, args);
            if (ctor == null && this.arithmetic.narrowArguments(args)) {
                ctor = this.uberspect.getConstructor(clazz, args);
            }
            if (ctor != null) {
                result = ctor.invoke(clazz, args);
            } else {
                xjexl = new JexlException.Method(info, clazz.toString(), args);
            }
        }
        catch (JexlException xany) {
            xjexl = xany;
        }
        catch (Exception xany) {
            xjexl = new JexlException.Method(info, clazz.toString(), args, xany);
        }
        if (xjexl != null) {
            if (this.silent) {
                this.logger.warn((Object)xjexl.getMessage(), xjexl.getCause());
                return null;
            }
            throw xjexl.clean();
        }
        return result;
    }

    protected JexlContext.ThreadLocal putThreadLocal(JexlContext.ThreadLocal tls) {
        JexlContext.ThreadLocal local = (JexlContext.ThreadLocal)CONTEXT.get();
        CONTEXT.set(tls);
        return local;
    }

    protected JexlEngine putThreadEngine(JexlEngine jexl) {
        JexlEngine pjexl = (JexlEngine)ENGINE.get();
        ENGINE.set(jexl);
        return pjexl;
    }

    protected Set<List<String>> getVariables(ASTJexlScript script) {
        VarCollector collector = this.varCollector();
        this.getVariables(script, script, collector);
        return collector.collected();
    }

    protected VarCollector varCollector() {
        return new VarCollector(this.collectMode);
    }

    protected void getVariables(ASTJexlScript script, JexlNode node, VarCollector collector) {
        if (node instanceof ASTIdentifier) {
            JexlNode parent = node.jjtGetParent();
            if (parent instanceof ASTMethodNode || parent instanceof ASTFunctionNode) {
                collector.collect(null);
                return;
            }
            ASTIdentifier identifier = (ASTIdentifier)node;
            int symbol = identifier.getSymbol();
            if (symbol >= 0 && script != null && !script.isCapturedSymbol(symbol)) {
                collector.collect(null);
            } else {
                collector.collect(identifier);
                collector.add(identifier.getName());
            }
        } else if (node instanceof ASTIdentifierAccess) {
            JexlNode parent = node.jjtGetParent();
            if (parent instanceof ASTMethodNode || parent instanceof ASTFunctionNode) {
                collector.collect(null);
                return;
            }
            if (collector.isCollecting()) {
                collector.add(((ASTIdentifierAccess)node).getName());
            }
        } else if (node instanceof ASTArrayAccess && collector.mode > 0) {
            int num = node.jjtGetNumChildren();
            boolean collecting = collector.isCollecting();
            for (int i = 0; i < num; ++i) {
                JexlNode child = node.jjtGetChild(i);
                if (collecting && child.isConstant()) {
                    boolean collect;
                    boolean bl = collect = collector.mode > 1 || child instanceof ASTStringLiteral || child instanceof ASTNumberLiteral;
                    if (!collect) continue;
                    String image = child.toString();
                    collector.add(image);
                    continue;
                }
                collecting = false;
                collector.collect(null);
                this.getVariables(script, child, collector);
                collector.collect(null);
            }
        } else {
            int num = node.jjtGetNumChildren();
            for (int i = 0; i < num; ++i) {
                this.getVariables(script, node.jjtGetChild(i), collector);
            }
            collector.collect(null);
        }
    }

    protected String[] getParameters(JexlScript script) {
        return script.getParameters();
    }

    protected String[] getLocalVariables(JexlScript script) {
        return script.getLocalVariables();
    }

    protected ASTJexlScript parse(JexlInfo info, boolean expr, String src, Scope scope) {
        return this.parse(info, expr ? this.expressionFeatures : this.scriptFeatures, src, scope);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected ASTJexlScript parse(JexlInfo info, JexlFeatures parsingf, String src, Scope scope) {
        JexlInfo ninfo;
        Scope f;
        boolean cached = src.length() < this.cacheThreshold && this.cache != null;
        JexlFeatures features = parsingf != null ? parsingf : DEFAULT_FEATURES;
        Source source = cached ? new Source(features, src) : null;
        ASTJexlScript script = null;
        if (source != null && (script = this.cache.get(source)) != null && ((f = script.getScope()) == null && scope == null || f != null && f.equals(scope))) {
            return script;
        }
        JexlInfo jexlInfo = ninfo = info == null && this.debug ? this.createInfo() : info;
        if (this.parsing.compareAndSet(false, true)) {
            try {
                script = this.parser.parse(ninfo, features, src, scope);
            }
            finally {
                this.parsing.set(false);
            }
        } else {
            Parser lparser = new Parser(new StringProvider(";"));
            script = lparser.parse(ninfo, features, src, scope);
        }
        if (source != null) {
            this.cache.put(source, script);
        }
        return script;
    }

    protected String trimSource(CharSequence str) {
        if (str != null) {
            int end = str.length();
            if (end > 0) {
                int start;
                for (start = 0; start < end && Character.isSpaceChar(str.charAt(start)); ++start) {
                }
                while (end > start && Character.isSpaceChar(str.charAt(end - 1))) {
                    --end;
                }
                return str.subSequence(start, end).toString();
            }
            return "";
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected TemplateEngine jxlt() {
        TemplateEngine e = this.jxlt;
        if (e == null) {
            Engine engine = this;
            synchronized (engine) {
                e = this.jxlt;
                if (e == null) {
                    this.jxlt = e = new TemplateEngine(this, true, 0, '$', '#');
                }
            }
        }
        return e;
    }

    protected static class VarCollector {
        private final Set<List<String>> refs = new LinkedHashSet<List<String>>();
        private List<String> ref = new ArrayList<String>();
        private JexlNode root = null;
        private int mode = 1;

        protected VarCollector(int constaa) {
            this.mode = constaa;
        }

        public void collect(JexlNode node) {
            if (!this.ref.isEmpty()) {
                this.refs.add(this.ref);
                this.ref = new ArrayList<String>();
            }
            this.root = node;
        }

        public boolean isCollecting() {
            return this.root instanceof ASTIdentifier;
        }

        public void add(String name) {
            this.ref.add(name);
        }

        public Set<List<String>> collected() {
            return this.refs;
        }
    }

    private static final class UberspectHolder {
        private static final Uberspect UBERSPECT = new Uberspect(LogFactory.getLog(JexlEngine.class), JexlUberspect.JEXL_STRATEGY);

        private UberspectHolder() {
        }
    }
}

