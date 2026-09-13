/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.JexlInfo;
import org.apache.commons.jexl3.JexlOptions;
import org.apache.commons.jexl3.JxltEngine;
import org.apache.commons.jexl3.internal.Engine;
import org.apache.commons.jexl3.internal.Frame;
import org.apache.commons.jexl3.internal.Interpreter;
import org.apache.commons.jexl3.internal.Scope;
import org.apache.commons.jexl3.internal.SoftCache;
import org.apache.commons.jexl3.internal.TemplateInterpreter;
import org.apache.commons.jexl3.internal.TemplateScript;
import org.apache.commons.jexl3.parser.ASTJexlScript;
import org.apache.commons.jexl3.parser.JexlNode;
import org.apache.commons.jexl3.parser.StringParser;

public final class TemplateEngine
extends JxltEngine {
    private final SoftCache<String, TemplateExpression> cache;
    private final Engine jexl;
    private final char immediateChar;
    private final char deferredChar;
    private boolean noscript = true;

    public TemplateEngine(Engine aJexl, boolean noScript, int cacheSize, char immediate, char deferred) {
        this.jexl = aJexl;
        this.cache = new SoftCache(cacheSize);
        this.immediateChar = immediate;
        this.deferredChar = deferred;
        this.noscript = noScript;
    }

    char getImmediateChar() {
        return this.immediateChar;
    }

    char getDeferredChar() {
        return this.deferredChar;
    }

    @Override
    public Engine getEngine() {
        return this.jexl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void clearCache() {
        SoftCache<String, TemplateExpression> softCache = this.cache;
        synchronized (softCache) {
            this.cache.clear();
        }
    }

    @Override
    public JxltEngine.Expression createExpression(JexlInfo info, String expression) {
        if (info == null) {
            info = this.jexl.createInfo();
        }
        JxltEngine.Exception xuel = null;
        TemplateExpression stmt = null;
        try {
            stmt = this.cache.get(expression);
            if (stmt == null) {
                stmt = this.parseExpression(info, expression, null);
                this.cache.put(expression, stmt);
            }
        }
        catch (JexlException xjexl) {
            xuel = new JxltEngine.Exception(xjexl.getInfo(), "failed to parse '" + expression + "'", (Throwable)xjexl);
        }
        if (xuel != null) {
            if (!this.jexl.isSilent()) {
                throw xuel;
            }
            this.jexl.logger.warn((Object)xuel.getMessage(), xuel.getCause());
            stmt = null;
        }
        return stmt;
    }

    static JxltEngine.Exception createException(JexlInfo info, String action, TemplateExpression expr, Exception xany) {
        String causeMsg;
        Throwable cause;
        StringBuilder strb = new StringBuilder("failed to ");
        strb.append(action);
        if (expr != null) {
            strb.append(" '");
            strb.append(expr.toString());
            strb.append("'");
        }
        if ((cause = xany.getCause()) != null && (causeMsg = cause.getMessage()) != null) {
            strb.append(", ");
            strb.append(causeMsg);
        }
        return new JxltEngine.Exception(info, strb.toString(), (Throwable)xany);
    }

    private static int append(StringBuilder strb, CharSequence expr, int position, char c) {
        int index;
        strb.append(c);
        if (c != '\"' && c != '\'') {
            return position;
        }
        int end = expr.length();
        boolean escape = false;
        for (index = position + 1; index < end; ++index) {
            char ec = expr.charAt(index);
            strb.append(ec);
            if (ec == '\\') {
                escape = !escape;
                continue;
            }
            if (escape) {
                escape = false;
                continue;
            }
            if (ec == c) break;
        }
        return index;
    }

    TemplateExpression parseExpression(JexlInfo info, String expr, Scope scope) {
        int size = expr.length();
        ExpressionBuilder builder = new ExpressionBuilder(0);
        StringBuilder strb = new StringBuilder(size);
        ParseState state = ParseState.CONST;
        int immediate1 = 0;
        int deferred1 = 0;
        int inner1 = 0;
        boolean nested = false;
        int inested = -1;
        int lineno = info.getLine();
        block13: for (int column = 0; column < size; ++column) {
            char c = expr.charAt(column);
            switch (state) {
                default: {
                    throw new UnsupportedOperationException("unexpected unified expression type");
                }
                case CONST: {
                    if (c == this.immediateChar) {
                        state = ParseState.IMMEDIATE0;
                        break;
                    }
                    if (c == this.deferredChar) {
                        inested = column;
                        state = ParseState.DEFERRED0;
                        break;
                    }
                    if (c == '\\') {
                        state = ParseState.ESCAPE;
                        break;
                    }
                    strb.append(c);
                    break;
                }
                case IMMEDIATE0: {
                    ConstantExpression cexpr;
                    if (c == '{') {
                        state = ParseState.IMMEDIATE1;
                        if (strb.length() <= 0) break;
                        cexpr = new ConstantExpression(strb.toString(), null);
                        builder.add(cexpr);
                        strb.delete(0, Integer.MAX_VALUE);
                        break;
                    }
                    strb.append(this.immediateChar);
                    strb.append(c);
                    state = ParseState.CONST;
                    break;
                }
                case DEFERRED0: {
                    ConstantExpression cexpr;
                    if (c == '{') {
                        state = ParseState.DEFERRED1;
                        if (strb.length() <= 0) break;
                        cexpr = new ConstantExpression(strb.toString(), null);
                        builder.add(cexpr);
                        strb.delete(0, Integer.MAX_VALUE);
                        break;
                    }
                    strb.append(this.deferredChar);
                    strb.append(c);
                    state = ParseState.CONST;
                    break;
                }
                case IMMEDIATE1: {
                    String src;
                    if (c == '}') {
                        if (immediate1 > 0) {
                            --immediate1;
                            strb.append(c);
                            break;
                        }
                        src = strb.toString();
                        ImmediateExpression iexpr = new ImmediateExpression(src, this.jexl.parse(info.at(lineno, column), this.noscript, src, scope), null);
                        builder.add(iexpr);
                        strb.delete(0, Integer.MAX_VALUE);
                        state = ParseState.CONST;
                        break;
                    }
                    if (c == '{') {
                        ++immediate1;
                    }
                    column = TemplateEngine.append(strb, expr, column, c);
                    break;
                }
                case DEFERRED1: {
                    String src;
                    if (c == '\"' || c == '\'') {
                        strb.append(c);
                        column = StringParser.readString(strb, expr, column + 1, c);
                        continue block13;
                    }
                    if (c == '{') {
                        if (expr.charAt(column - 1) == this.immediateChar) {
                            ++inner1;
                            strb.deleteCharAt(strb.length() - 1);
                            nested = true;
                            continue block13;
                        }
                        ++deferred1;
                        strb.append(c);
                        continue block13;
                    }
                    if (c == '}') {
                        if (deferred1 > 0) {
                            --deferred1;
                            strb.append(c);
                            break;
                        }
                        if (inner1 > 0) {
                            --inner1;
                            break;
                        }
                        src = strb.toString();
                        JexlBasedExpression dexpr = nested ? new NestedExpression(expr.substring(inested, column + 1), this.jexl.parse(info.at(lineno, column), this.noscript, src, scope), null) : new DeferredExpression(strb.toString(), this.jexl.parse(info.at(lineno, column), this.noscript, src, scope), null);
                        builder.add(dexpr);
                        strb.delete(0, Integer.MAX_VALUE);
                        nested = false;
                        state = ParseState.CONST;
                        break;
                    }
                    column = TemplateEngine.append(strb, expr, column, c);
                    break;
                }
                case ESCAPE: {
                    if (c == this.deferredChar) {
                        strb.append(this.deferredChar);
                    } else if (c == this.immediateChar) {
                        strb.append(this.immediateChar);
                    } else {
                        strb.append('\\');
                        strb.append(c);
                    }
                    state = ParseState.CONST;
                }
            }
            if (c != '\n') continue;
            ++lineno;
        }
        if (state != ParseState.CONST) {
            switch (state) {
                case ESCAPE: {
                    strb.append('\\');
                    strb.append('\\');
                    break;
                }
                case DEFERRED0: {
                    strb.append(this.deferredChar);
                    break;
                }
                case IMMEDIATE0: {
                    strb.append(this.immediateChar);
                    break;
                }
                default: {
                    throw new JxltEngine.Exception(info.at(lineno, 0), "malformed expression: " + expr, null);
                }
            }
        }
        if (strb.length() > 0) {
            ConstantExpression cexpr = new ConstantExpression(strb.toString(), null);
            builder.add(cexpr);
        }
        return builder.build(this, null);
    }

    protected int startsWith(CharSequence sequence, CharSequence pattern) {
        int s;
        int length = sequence.length();
        for (s = 0; s < length && Character.isSpaceChar(sequence.charAt(s)); ++s) {
        }
        if (s < length && pattern.length() <= length - s && (sequence = sequence.subSequence(s, length)).subSequence(0, pattern.length()).equals(pattern)) {
            return s + pattern.length();
        }
        return -1;
    }

    protected static Iterator<CharSequence> readLines(final Reader reader) {
        if (!reader.markSupported()) {
            throw new IllegalArgumentException("mark support in reader required");
        }
        return new Iterator<CharSequence>(){
            private CharSequence next = this.doNext();

            private CharSequence doNext() {
                StringBuffer strb = new StringBuffer(64);
                boolean eol = false;
                try {
                    int c;
                    while ((c = reader.read()) >= 0) {
                        if (eol) {
                            reader.reset();
                            break;
                        }
                        if (c == 10) {
                            eol = true;
                        }
                        strb.append((char)c);
                        reader.mark(1);
                    }
                }
                catch (IOException xio) {
                    return null;
                }
                return strb.length() > 0 ? strb : null;
            }

            @Override
            public boolean hasNext() {
                return this.next != null;
            }

            @Override
            public CharSequence next() {
                CharSequence current = this.next;
                if (current != null) {
                    this.next = this.doNext();
                }
                return current;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported.");
            }
        };
    }

    protected List<Block> readTemplate(String prefix, Reader source) {
        CharSequence line;
        ArrayList<Block> blocks = new ArrayList<Block>();
        BufferedReader reader = source instanceof BufferedReader ? (BufferedReader)source : new BufferedReader(source);
        StringBuilder strb = new StringBuilder();
        BlockType type = null;
        Iterator<CharSequence> lines = TemplateEngine.readLines(reader);
        int lineno = 1;
        int start = 0;
        while (lines.hasNext() && (line = lines.next()) != null) {
            int prefixLen;
            if (type == null) {
                prefixLen = this.startsWith(line, prefix);
                if (prefixLen >= 0) {
                    type = BlockType.DIRECTIVE;
                    strb.append(line.subSequence(prefixLen, line.length()));
                } else {
                    type = BlockType.VERBATIM;
                    strb.append(line.subSequence(0, line.length()));
                }
                start = lineno;
            } else if (type == BlockType.DIRECTIVE) {
                prefixLen = this.startsWith(line, prefix);
                if (prefixLen < 0) {
                    Block directive = new Block(BlockType.DIRECTIVE, start, strb.toString());
                    strb.delete(0, Integer.MAX_VALUE);
                    blocks.add(directive);
                    type = BlockType.VERBATIM;
                    strb.append(line.subSequence(0, line.length()));
                    start = lineno;
                } else {
                    strb.append(line.subSequence(prefixLen, line.length()));
                }
            } else if (type == BlockType.VERBATIM) {
                prefixLen = this.startsWith(line, prefix);
                if (prefixLen >= 0) {
                    Block verbatim = new Block(BlockType.VERBATIM, start, strb.toString());
                    strb.delete(0, Integer.MAX_VALUE);
                    blocks.add(verbatim);
                    type = BlockType.DIRECTIVE;
                    strb.append(line.subSequence(prefixLen, line.length()));
                    start = lineno;
                } else {
                    strb.append(line.subSequence(0, line.length()));
                }
            }
            ++lineno;
        }
        if (type != null && strb.length() > 0) {
            Block block = new Block(type, start, strb.toString());
            blocks.add(block);
        }
        blocks.trimToSize();
        return blocks;
    }

    @Override
    public TemplateScript createTemplate(JexlInfo info, String prefix, Reader source, String ... parms) {
        return new TemplateScript(this, info, prefix, source, parms);
    }

    static final class Block {
        private final BlockType type;
        private final int line;
        private final String body;

        Block(BlockType theType, int theLine, String theBlock) {
            this.type = theType;
            this.line = theLine;
            this.body = theBlock;
        }

        BlockType getType() {
            return this.type;
        }

        int getLine() {
            return this.line;
        }

        String getBody() {
            return this.body;
        }

        public String toString() {
            if (BlockType.VERBATIM.equals((Object)this.type)) {
                return this.body;
            }
            StringBuilder strb = new StringBuilder(64);
            Iterator<CharSequence> lines = TemplateEngine.readLines(new StringReader(this.body));
            while (lines.hasNext()) {
                strb.append("$$").append(lines.next());
            }
            return strb.toString();
        }

        protected void toString(StringBuilder strb, String prefix) {
            if (BlockType.VERBATIM.equals((Object)this.type)) {
                strb.append(this.body);
            } else {
                Iterator<CharSequence> lines = TemplateEngine.readLines(new StringReader(this.body));
                while (lines.hasNext()) {
                    strb.append(prefix).append(lines.next());
                }
            }
        }
    }

    static enum BlockType {
        VERBATIM,
        DIRECTIVE;

    }

    private static enum ParseState {
        CONST,
        IMMEDIATE0,
        DEFERRED0,
        IMMEDIATE1,
        DEFERRED1,
        ESCAPE;

    }

    class CompositeExpression
    extends TemplateExpression {
        private final int meta;
        protected final TemplateExpression[] exprs;

        CompositeExpression(int[] counters, ArrayList<TemplateExpression> list, TemplateExpression src) {
            super(src);
            this.exprs = list.toArray(new TemplateExpression[list.size()]);
            this.meta = (counters[ExpressionType.DEFERRED.index] > 0 ? 2 : 0) | (counters[ExpressionType.IMMEDIATE.index] > 0 ? 1 : 0);
        }

        @Override
        public boolean isImmediate() {
            return (this.meta & 2) == 0;
        }

        @Override
        ExpressionType getType() {
            return ExpressionType.COMPOSITE;
        }

        @Override
        public StringBuilder asString(StringBuilder strb) {
            for (TemplateExpression e : this.exprs) {
                e.asString(strb);
            }
            return strb;
        }

        @Override
        public Set<List<String>> getVariables() {
            Engine.VarCollector collector = TemplateEngine.this.jexl.varCollector();
            for (TemplateExpression expr : this.exprs) {
                expr.getVariables(collector);
            }
            return collector.collected();
        }

        @Override
        protected void getVariables(Engine.VarCollector collector) {
            for (TemplateExpression expr : this.exprs) {
                expr.getVariables(collector);
            }
        }

        @Override
        protected TemplateExpression prepare(Interpreter interpreter) {
            if (this.source != this) {
                return this;
            }
            int size = this.exprs.length;
            ExpressionBuilder builder = new ExpressionBuilder(size);
            boolean eq = true;
            for (TemplateExpression expr : this.exprs) {
                TemplateExpression prepared = expr.prepare(interpreter);
                if (prepared != null) {
                    builder.add(prepared);
                }
                eq &= expr == prepared;
            }
            return eq ? this : builder.build(TemplateEngine.this, this);
        }

        @Override
        protected Object evaluate(Interpreter interpreter) {
            Object value;
            StringBuilder strb = new StringBuilder();
            for (TemplateExpression expr : this.exprs) {
                value = expr.evaluate(interpreter);
                if (value == null) continue;
                strb.append(value.toString());
            }
            value = strb.toString();
            return value;
        }
    }

    class NestedExpression
    extends JexlBasedExpression {
        NestedExpression(CharSequence expr, JexlNode node, TemplateExpression source) {
            super(expr, node, source);
            if (this.source != this) {
                throw new IllegalArgumentException("Nested TemplateExpression can not have a source");
            }
        }

        @Override
        public StringBuilder asString(StringBuilder strb) {
            strb.append(this.expr);
            return strb;
        }

        @Override
        public boolean isImmediate() {
            return false;
        }

        @Override
        ExpressionType getType() {
            return ExpressionType.NESTED;
        }

        @Override
        protected TemplateExpression prepare(Interpreter interpreter) {
            String value = interpreter.interpret(this.node).toString();
            ASTJexlScript dnode = TemplateEngine.this.jexl.parse(this.node.jexlInfo(), TemplateEngine.this.noscript, value, null);
            return new ImmediateExpression(value, dnode, this);
        }

        @Override
        protected Object evaluate(Interpreter interpreter) {
            return this.prepare(interpreter).evaluate(interpreter);
        }
    }

    class DeferredExpression
    extends JexlBasedExpression {
        DeferredExpression(CharSequence expr, JexlNode node, TemplateExpression source) {
            super(expr, node, source);
        }

        @Override
        public boolean isImmediate() {
            return false;
        }

        @Override
        ExpressionType getType() {
            return ExpressionType.DEFERRED;
        }

        @Override
        protected TemplateExpression prepare(Interpreter interpreter) {
            return new ImmediateExpression(this.expr, this.node, this.source);
        }

        @Override
        protected void getVariables(Engine.VarCollector collector) {
        }
    }

    class ImmediateExpression
    extends JexlBasedExpression {
        ImmediateExpression(CharSequence expr, JexlNode node, TemplateExpression source) {
            super(expr, node, source);
        }

        @Override
        ExpressionType getType() {
            return ExpressionType.IMMEDIATE;
        }

        @Override
        protected TemplateExpression prepare(Interpreter interpreter) {
            Object value = this.evaluate(interpreter);
            return value != null ? new ConstantExpression(value, this.source) : null;
        }
    }

    abstract class JexlBasedExpression
    extends TemplateExpression {
        protected final CharSequence expr;
        protected final JexlNode node;

        protected JexlBasedExpression(CharSequence theExpr, JexlNode theNode, TemplateExpression theSource) {
            super(theSource);
            this.expr = theExpr;
            this.node = theNode;
        }

        @Override
        public StringBuilder asString(StringBuilder strb) {
            strb.append(this.isImmediate() ? TemplateEngine.this.immediateChar : TemplateEngine.this.deferredChar);
            strb.append("{");
            strb.append(this.expr);
            strb.append("}");
            return strb;
        }

        @Override
        protected JexlOptions options(JexlContext context) {
            return TemplateEngine.this.jexl.options(this.node instanceof ASTJexlScript ? (ASTJexlScript)this.node : null, context);
        }

        @Override
        protected Object evaluate(Interpreter interpreter) {
            return interpreter.interpret(this.node);
        }

        @Override
        public Set<List<String>> getVariables() {
            Engine.VarCollector collector = TemplateEngine.this.jexl.varCollector();
            this.getVariables(collector);
            return collector.collected();
        }

        @Override
        protected void getVariables(Engine.VarCollector collector) {
            TemplateEngine.this.jexl.getVariables(this.node instanceof ASTJexlScript ? (ASTJexlScript)this.node : null, this.node, collector);
        }

        @Override
        JexlInfo getInfo() {
            return this.node.jexlInfo();
        }
    }

    class ConstantExpression
    extends TemplateExpression {
        private final Object value;

        ConstantExpression(Object val, TemplateExpression source) {
            super(source);
            if (val == null) {
                throw new NullPointerException("constant can not be null");
            }
            if (val instanceof String) {
                val = StringParser.buildTemplate((String)val, false);
            }
            this.value = val;
        }

        @Override
        ExpressionType getType() {
            return ExpressionType.CONSTANT;
        }

        @Override
        public StringBuilder asString(StringBuilder strb) {
            if (this.value != null) {
                strb.append(this.value.toString());
            }
            return strb;
        }

        @Override
        protected Object evaluate(Interpreter interpreter) {
            return this.value;
        }
    }

    abstract class TemplateExpression
    implements JxltEngine.Expression {
        protected final TemplateExpression source;

        TemplateExpression(TemplateExpression src) {
            this.source = src != null ? src : this;
        }

        @Override
        public boolean isImmediate() {
            return true;
        }

        @Override
        public final boolean isDeferred() {
            return !this.isImmediate();
        }

        abstract ExpressionType getType();

        JexlInfo getInfo() {
            return null;
        }

        @Override
        public final String toString() {
            StringBuilder strb = new StringBuilder();
            this.asString(strb);
            if (this.source != this) {
                strb.append(" /*= ");
                strb.append(this.source.toString());
                strb.append(" */");
            }
            return strb.toString();
        }

        @Override
        public String asString() {
            StringBuilder strb = new StringBuilder();
            this.asString(strb);
            return strb.toString();
        }

        @Override
        public Set<List<String>> getVariables() {
            return Collections.emptySet();
        }

        @Override
        public final TemplateExpression getSource() {
            return this.source;
        }

        protected void getVariables(Engine.VarCollector collector) {
        }

        @Override
        public final TemplateExpression prepare(JexlContext context) {
            return this.prepare(null, context);
        }

        protected final TemplateExpression prepare(Frame frame, JexlContext context) {
            try {
                Interpreter interpreter = TemplateEngine.this.jexl.createInterpreter(context, frame, TemplateEngine.this.jexl.options(context));
                return this.prepare(interpreter);
            }
            catch (JexlException xjexl) {
                JxltEngine.Exception xuel = TemplateEngine.createException(xjexl.getInfo(), "prepare", this, xjexl);
                if (TemplateEngine.this.jexl.isSilent()) {
                    ((TemplateEngine)TemplateEngine.this).jexl.logger.warn((Object)xuel.getMessage(), xuel.getCause());
                    return null;
                }
                throw xuel;
            }
        }

        protected TemplateExpression prepare(Interpreter interpreter) {
            return this;
        }

        @Override
        public final Object evaluate(JexlContext context) {
            return this.evaluate(null, context);
        }

        protected JexlOptions options(JexlContext context) {
            return TemplateEngine.this.jexl.options(null, context);
        }

        protected final Object evaluate(Frame frame, JexlContext context) {
            try {
                JexlOptions options = this.options(context);
                TemplateInterpreter.Arguments args = new TemplateInterpreter.Arguments(TemplateEngine.this.jexl).context(context).options(options).frame(frame);
                TemplateInterpreter interpreter = new TemplateInterpreter(args);
                return this.evaluate(interpreter);
            }
            catch (JexlException xjexl) {
                JxltEngine.Exception xuel = TemplateEngine.createException(xjexl.getInfo(), "evaluate", this, xjexl);
                if (TemplateEngine.this.jexl.isSilent()) {
                    ((TemplateEngine)TemplateEngine.this).jexl.logger.warn((Object)xuel.getMessage(), xuel.getCause());
                    return null;
                }
                throw xuel;
            }
        }

        protected abstract Object evaluate(Interpreter var1);
    }

    static final class ExpressionBuilder {
        private final int[] counts = new int[]{0, 0, 0};
        private final ArrayList<TemplateExpression> expressions;

        private ExpressionBuilder(int size) {
            this.expressions = new ArrayList(size <= 0 ? 3 : size);
        }

        private void add(TemplateExpression expr) {
            int n = expr.getType().index;
            this.counts[n] = this.counts[n] + 1;
            this.expressions.add(expr);
        }

        public String toString() {
            return this.toString(new StringBuilder()).toString();
        }

        private StringBuilder toString(StringBuilder error) {
            error.append("exprs{");
            error.append(this.expressions.size());
            error.append(", constant:");
            error.append(this.counts[ExpressionType.CONSTANT.index]);
            error.append(", immediate:");
            error.append(this.counts[ExpressionType.IMMEDIATE.index]);
            error.append(", deferred:");
            error.append(this.counts[ExpressionType.DEFERRED.index]);
            error.append("}");
            return error;
        }

        private TemplateExpression build(TemplateEngine el, TemplateExpression source) {
            int sum = 0;
            for (int count : this.counts) {
                sum += count;
            }
            if (this.expressions.size() != sum) {
                StringBuilder error = new StringBuilder("parsing algorithm error: ");
                throw new IllegalStateException(this.toString(error).toString());
            }
            if (this.expressions.size() == 1) {
                return this.expressions.get(0);
            }
            TemplateEngine templateEngine = el;
            templateEngine.getClass();
            return templateEngine.new CompositeExpression(this.counts, this.expressions, source);
        }
    }

    static enum ExpressionType {
        CONSTANT(0),
        IMMEDIATE(1),
        DEFERRED(2),
        NESTED(2),
        COMPOSITE(-1);

        private final int index;

        private ExpressionType(int idx) {
            this.index = idx;
        }
    }
}

