/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.ANTLRErrorListener;
import groovyjarjarantlr4.v4.runtime.RecognitionException;
import groovyjarjarantlr4.v4.runtime.Recognizer;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import java.util.Collection;

public class ProxyErrorListener<Symbol>
implements ANTLRErrorListener<Symbol> {
    private final Collection<? extends ANTLRErrorListener<? super Symbol>> delegates;

    public ProxyErrorListener(Collection<? extends ANTLRErrorListener<? super Symbol>> delegates) {
        if (delegates == null) {
            throw new NullPointerException("delegates");
        }
        this.delegates = delegates;
    }

    protected Collection<? extends ANTLRErrorListener<? super Symbol>> getDelegates() {
        return this.delegates;
    }

    @Override
    public <T extends Symbol> void syntaxError(@NotNull Recognizer<T, ?> recognizer, @Nullable T offendingSymbol, int line, int charPositionInLine, @NotNull String msg, @Nullable RecognitionException e) {
        for (ANTLRErrorListener<Symbol> listener : this.delegates) {
            listener.syntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e);
        }
    }
}

