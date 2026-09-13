/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.stringtemplate.v4.ST
 */
package groovyjarjarantlr4.v4.tool;

import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.Grammar;
import java.util.Arrays;
import org.stringtemplate.v4.ST;

public class ANTLRMessage {
    private static final Object[] EMPTY_ARGS = new Object[0];
    @NotNull
    private final ErrorType errorType;
    @Nullable
    private final Object[] args;
    @Nullable
    private final Throwable e;
    public String fileName;
    public int line = -1;
    public int charPosition = -1;
    public Grammar g;
    public Token offendingToken;

    public ANTLRMessage(@NotNull ErrorType errorType) {
        this(errorType, (Throwable)null, Token.INVALID_TOKEN, new Object[0]);
    }

    public ANTLRMessage(@NotNull ErrorType errorType, Token offendingToken, Object ... args) {
        this(errorType, null, offendingToken, args);
    }

    public ANTLRMessage(@NotNull ErrorType errorType, @Nullable Throwable e, Token offendingToken, Object ... args) {
        this.errorType = errorType;
        this.e = e;
        this.args = args;
        this.offendingToken = offendingToken;
    }

    @NotNull
    public ErrorType getErrorType() {
        return this.errorType;
    }

    @NotNull
    public Object[] getArgs() {
        if (this.args == null) {
            return EMPTY_ARGS;
        }
        return this.args;
    }

    public ST getMessageTemplate(boolean verbose) {
        Throwable cause;
        ST messageST = new ST(this.getErrorType().msg);
        messageST.impl.name = this.errorType.name();
        messageST.add("verbose", (Object)verbose);
        Object[] args = this.getArgs();
        for (int i = 0; i < args.length; ++i) {
            String attr = "arg";
            if (i > 0) {
                attr = attr + (i + 1);
            }
            messageST.add(attr, args[i]);
        }
        if (args.length < 2) {
            messageST.add("arg2", null);
        }
        if ((cause = this.getCause()) != null) {
            messageST.add("exception", (Object)cause);
            messageST.add("stackTrace", (Object)cause.getStackTrace());
        } else {
            messageST.add("exception", null);
            messageST.add("stackTrace", null);
        }
        return messageST;
    }

    @Nullable
    public Throwable getCause() {
        return this.e;
    }

    public String toString() {
        return "Message{errorType=" + (Object)((Object)this.getErrorType()) + ", args=" + Arrays.asList(this.getArgs()) + ", e=" + this.getCause() + ", fileName='" + this.fileName + '\'' + ", line=" + this.line + ", charPosition=" + this.charPosition + '}';
    }
}

