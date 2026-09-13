/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Objects;
import org.apache.commons.jexl3.JexlArithmetic;
import org.apache.commons.jexl3.JexlFeatures;
import org.apache.commons.jexl3.JexlInfo;
import org.apache.commons.jexl3.internal.Debugger;
import org.apache.commons.jexl3.parser.JavaccError;
import org.apache.commons.jexl3.parser.JexlNode;
import org.apache.commons.jexl3.parser.ParseException;
import org.apache.commons.jexl3.parser.TokenMgrException;

public class JexlException
extends RuntimeException {
    private static final long serialVersionUID = 20210606123900L;
    private final transient JexlNode mark;
    private final transient JexlInfo info;
    private static final int MAX_EXCHARLOC = 42;
    private static final String VARQUOTE = "variable '";

    public JexlException(JexlNode node, String msg) {
        this(node, msg, null);
    }

    public JexlException(JexlNode node, String msg, Throwable cause) {
        this(node, msg != null ? msg : "", JexlException.unwrap(cause), true);
    }

    protected JexlException(JexlNode node, String msg, Throwable cause, boolean trace) {
        super(msg != null ? msg : "", JexlException.unwrap(cause), !trace, trace);
        if (node != null) {
            this.mark = node;
            this.info = node.jexlInfo();
        } else {
            this.mark = null;
            this.info = null;
        }
    }

    public JexlException(JexlInfo jinfo, String msg, Throwable cause) {
        super(msg != null ? msg : "", JexlException.unwrap(cause));
        this.mark = null;
        this.info = jinfo;
    }

    public JexlInfo getInfo() {
        return JexlException.detailedInfo(this.mark, this.info);
    }

    private static StringBuilder errorAt(JexlNode node) {
        JexlInfo info = node != null ? JexlException.detailedInfo(node, node.jexlInfo()) : null;
        StringBuilder msg = new StringBuilder();
        if (info != null) {
            msg.append(info.toString());
        } else {
            msg.append("?:");
        }
        msg.append(' ');
        return msg;
    }

    @Deprecated
    public static JexlInfo getInfo(JexlNode node, JexlInfo info) {
        return JexlException.detailedInfo(node, info);
    }

    private static JexlInfo detailedInfo(JexlNode node, JexlInfo info) {
        Debugger dbg;
        if (info != null && node != null && (dbg = new Debugger()).debug(node)) {
            return new JexlInfo(info){

                @Override
                public JexlInfo.Detail getDetail() {
                    return dbg;
                }
            };
        }
        return info;
    }

    public JexlException clean() {
        return JexlException.clean(this);
    }

    private static <X extends Throwable> X clean(X xthrow) {
        if (xthrow != null) {
            ArrayList<StackTraceElement> stackJexl = new ArrayList<StackTraceElement>();
            for (StackTraceElement se : xthrow.getStackTrace()) {
                String className = se.getClassName();
                if (className.startsWith("org.apache.commons.jexl3.internal") || className.startsWith("org.apache.commons.jexl3.parser")) continue;
                stackJexl.add(se);
            }
            xthrow.setStackTrace(stackJexl.toArray(new StackTraceElement[stackJexl.size()]));
        }
        return xthrow;
    }

    private static Throwable unwrap(Throwable xthrow) {
        if (xthrow instanceof TryFailed || xthrow instanceof InvocationTargetException || xthrow instanceof UndeclaredThrowableException) {
            return xthrow.getCause();
        }
        return xthrow;
    }

    private static JexlInfo merge(JexlInfo info, JavaccError cause) {
        if (cause == null || cause.getLine() < 0) {
            return info;
        }
        if (info == null) {
            return new JexlInfo("", cause.getLine(), cause.getColumn());
        }
        return new JexlInfo(info.getName(), cause.getLine(), cause.getColumn());
    }

    protected String detailedMessage() {
        Class<?> clazz = this.getClass();
        String name = clazz == JexlException.class ? "JEXL" : clazz.getSimpleName().toLowerCase();
        return name + " error : " + this.getDetail();
    }

    public final String getDetail() {
        return super.getMessage();
    }

    protected String parserError(String prefix, String expr) {
        int length = expr.length();
        if (length < 42) {
            return prefix + " error in '" + expr + "'";
        }
        int me = 21;
        int begin = this.info.getColumn() - 21;
        if (begin < 0 || length < 21) {
            begin = 0;
        } else if (begin > length) {
            begin = 21;
        }
        int end = begin + 42;
        if (end > length) {
            end = length;
        }
        return prefix + " error near '... " + expr.substring(begin, end) + " ...'";
    }

    protected JexlInfo info() {
        return this.info;
    }

    public static String sliceSource(String src, int froml, int fromc, int tol, int toc) {
        BufferedReader reader = new BufferedReader(new StringReader(src));
        StringBuilder buffer = new StringBuilder();
        int cl = 1;
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                if (cl < froml || cl > tol) {
                    buffer.append(line).append('\n');
                } else {
                    if (cl == froml) {
                        buffer.append(line, 0, fromc - 1);
                    }
                    if (cl == tol) {
                        buffer.append(line.substring(toc + 1));
                    }
                }
                ++cl;
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return buffer.toString();
    }

    @Deprecated
    public static String variableError(JexlNode node, String variable, boolean undef) {
        return JexlException.variableError(node, variable, undef ? VariableIssue.UNDEFINED : VariableIssue.NULLVALUE);
    }

    public static String variableError(JexlNode node, String variable, VariableIssue issue) {
        StringBuilder msg = JexlException.errorAt(node);
        msg.append(issue.message(variable));
        return msg.toString();
    }

    public static String propertyError(JexlNode node, String pty, boolean undef) {
        StringBuilder msg = JexlException.errorAt(node);
        if (undef) {
            msg.append("unsolvable");
        } else {
            msg.append("null value");
        }
        msg.append(" property '");
        msg.append(pty);
        msg.append('\'');
        return msg.toString();
    }

    @Deprecated
    public static String propertyError(JexlNode node, String var) {
        return JexlException.propertyError(node, var, true);
    }

    private static String methodSignature(String name, Object[] args) {
        if (args != null && args.length > 0) {
            StringBuilder strb = new StringBuilder(name);
            strb.append('(');
            for (int a = 0; a < args.length; ++a) {
                if (a > 0) {
                    strb.append(", ");
                }
                Class<Object> clazz = args[a] == null ? Object.class : args[a].getClass();
                strb.append(clazz.getSimpleName());
            }
            strb.append(')');
            return strb.toString();
        }
        return name;
    }

    @Deprecated
    public static String methodError(JexlNode node, String method) {
        return JexlException.methodError(node, method, null);
    }

    public static String methodError(JexlNode node, String method, Object[] args) {
        StringBuilder msg = JexlException.errorAt(node);
        msg.append("unsolvable function/method '");
        msg.append(JexlException.methodSignature(method, args));
        msg.append('\'');
        return msg.toString();
    }

    public static String operatorError(JexlNode node, String symbol) {
        StringBuilder msg = JexlException.errorAt(node);
        msg.append("error calling operator '");
        msg.append(symbol);
        msg.append('\'');
        return msg.toString();
    }

    public static String annotationError(JexlNode node, String annotation) {
        StringBuilder msg = JexlException.errorAt(node);
        msg.append("error processing annotation '");
        msg.append(annotation);
        msg.append('\'');
        return msg.toString();
    }

    public static JexlException tryFailed(InvocationTargetException xinvoke) {
        Throwable cause = xinvoke.getCause();
        return cause instanceof JexlException ? (JexlException)cause : new TryFailed(xinvoke);
    }

    @Override
    public String getMessage() {
        StringBuilder msg = new StringBuilder();
        if (this.info != null) {
            msg.append(this.info.toString());
        } else {
            msg.append("?:");
        }
        msg.append(' ');
        msg.append(this.detailedMessage());
        Throwable cause = this.getCause();
        if (cause instanceof JexlArithmetic.NullOperand) {
            msg.append(" caused by null operand");
        }
        return msg.toString();
    }

    public static class TryFailed
    extends JexlException {
        private static final long serialVersionUID = 20210606124105L;

        private TryFailed(InvocationTargetException xany) {
            super((JexlInfo)null, "tryFailed", xany.getCause());
        }
    }

    public static class Continue
    extends JexlException {
        private static final long serialVersionUID = 20210606124104L;

        public Continue(JexlNode node) {
            super(node, "continue loop", null, false);
        }
    }

    public static class Break
    extends JexlException {
        private static final long serialVersionUID = 20210606124103L;

        public Break(JexlNode node) {
            super(node, "break loop", null, false);
        }
    }

    public static class Cancel
    extends JexlException {
        public Cancel(JexlNode node) {
            super(node, "execution cancelled", null);
        }
    }

    public static class Return
    extends JexlException {
        private static final long serialVersionUID = 20210606124102L;
        private final transient Object result;

        public Return(JexlNode node, String msg, Object value) {
            super(node, msg, null, false);
            this.result = value;
        }

        public Object getValue() {
            return this.result;
        }
    }

    public static class Annotation
    extends JexlException {
        private static final long serialVersionUID = 20210606124101L;

        public Annotation(JexlNode node, String name, Throwable cause) {
            super(node, name, cause);
        }

        public String getAnnotation() {
            return this.getDetail();
        }

        @Override
        protected String detailedMessage() {
            return "error processing annotation '" + this.getAnnotation() + "'";
        }
    }

    public static class Operator
    extends JexlException {
        private static final long serialVersionUID = 20210606124100L;

        public Operator(JexlNode node, String symbol, Throwable cause) {
            super(node, symbol, cause);
        }

        public String getSymbol() {
            return this.getDetail();
        }

        @Override
        protected String detailedMessage() {
            return "error calling operator '" + this.getSymbol() + "'";
        }
    }

    public static class Method
    extends JexlException {
        private static final long serialVersionUID = 20210606123909L;

        @Deprecated
        public Method(JexlNode node, String name) {
            this(node, name, (Object[])null);
        }

        @Deprecated
        public Method(JexlInfo info, String name, Throwable cause) {
            this(info, name, null, cause);
        }

        public Method(JexlNode node, String name, Object[] args) {
            super(node, JexlException.methodSignature(name, args));
        }

        public Method(JexlInfo info, String name, Object[] args) {
            this(info, name, args, null);
        }

        public Method(JexlInfo info, String name, Object[] args, Throwable cause) {
            super(info, JexlException.methodSignature(name, args), cause);
        }

        public String getMethod() {
            String signature = this.getMethodSignature();
            int lparen = signature.indexOf(40);
            return lparen > 0 ? signature.substring(0, lparen) : signature;
        }

        public String getMethodSignature() {
            return this.getDetail();
        }

        @Override
        protected String detailedMessage() {
            return "unsolvable function/method '" + this.getMethodSignature() + "'";
        }
    }

    public static class Property
    extends JexlException {
        private static final long serialVersionUID = 20210606123908L;
        private final boolean undefined;

        @Deprecated
        public Property(JexlNode node, String pty) {
            this(node, pty, true, null);
        }

        @Deprecated
        public Property(JexlNode node, String pty, Throwable cause) {
            this(node, pty, true, cause);
        }

        public Property(JexlNode node, String pty, boolean undef, Throwable cause) {
            super(node, pty, cause);
            this.undefined = undef;
        }

        public boolean isUndefined() {
            return this.undefined;
        }

        public String getProperty() {
            return this.getDetail();
        }

        @Override
        protected String detailedMessage() {
            return (this.undefined ? "undefined" : "null value") + " property '" + this.getProperty() + "'";
        }
    }

    public static class Variable
    extends JexlException {
        private static final long serialVersionUID = 20210606123907L;
        private final VariableIssue issue;

        public Variable(JexlNode node, String var, VariableIssue vi) {
            super(node, var, null);
            this.issue = vi;
        }

        public Variable(JexlNode node, String var, boolean undef) {
            this(node, var, undef ? VariableIssue.UNDEFINED : VariableIssue.NULLVALUE);
        }

        public boolean isUndefined() {
            return this.issue == VariableIssue.UNDEFINED;
        }

        public String getVariable() {
            return this.getDetail();
        }

        @Override
        protected String detailedMessage() {
            return this.issue.message(this.getVariable());
        }
    }

    public static enum VariableIssue {
        UNDEFINED,
        REDEFINED,
        NULLVALUE;


        public String message(String var) {
            switch (this) {
                case NULLVALUE: {
                    return JexlException.VARQUOTE + var + "' is null";
                }
                case REDEFINED: {
                    return JexlException.VARQUOTE + var + "' is already defined";
                }
            }
            return JexlException.VARQUOTE + var + "' is undefined";
        }
    }

    public static class Feature
    extends Parsing {
        private static final long serialVersionUID = 20210606123906L;
        private final int code;

        public Feature(JexlInfo info, int feature, String expr) {
            super(info, expr);
            this.code = feature;
        }

        @Override
        protected String detailedMessage() {
            return this.parserError(JexlFeatures.stringify(this.code), this.getDetail());
        }
    }

    public static class Assignment
    extends Parsing {
        private static final long serialVersionUID = 20210606123905L;

        public Assignment(JexlInfo info, String expr) {
            super(info, expr);
        }

        @Override
        protected String detailedMessage() {
            return this.parserError("assignment", this.getDetail());
        }
    }

    public static class StackOverflow
    extends JexlException {
        private static final long serialVersionUID = 20210606123904L;

        public StackOverflow(JexlInfo info, String name, Throwable cause) {
            super(info, name, cause);
        }

        @Override
        protected String detailedMessage() {
            return "stack overflow " + this.getDetail();
        }
    }

    public static class Ambiguous
    extends Parsing {
        private static final long serialVersionUID = 20210606123903L;
        private final transient JexlInfo recover;

        public Ambiguous(JexlInfo info, String expr) {
            this(info, null, expr);
        }

        public Ambiguous(JexlInfo begin, JexlInfo end, String expr) {
            super(begin, expr);
            this.recover = end;
        }

        @Override
        protected String detailedMessage() {
            return this.parserError("ambiguous statement", this.getDetail());
        }

        public String tryCleanSource(String src) {
            JexlInfo ji = this.info();
            return ji == null || this.recover == null ? src : Ambiguous.sliceSource(src, ji.getLine(), ji.getColumn(), this.recover.getLine(), this.recover.getColumn());
        }
    }

    public static class Parsing
    extends JexlException {
        private static final long serialVersionUID = 20210606123902L;

        public Parsing(JexlInfo info, ParseException cause) {
            super(JexlException.merge(info, cause), Objects.requireNonNull(cause).getAfter(), null);
        }

        public Parsing(JexlInfo info, String msg) {
            super(info, msg, null);
        }

        @Override
        protected String detailedMessage() {
            return this.parserError("parsing", this.getDetail());
        }
    }

    public static class Tokenization
    extends JexlException {
        private static final long serialVersionUID = 20210606123901L;

        public Tokenization(JexlInfo info, TokenMgrException cause) {
            super(JexlException.merge(info, cause), Objects.requireNonNull(cause).getAfter(), null);
        }

        @Override
        protected String detailedMessage() {
            return this.parserError("tokenization", this.getDetail());
        }
    }
}

