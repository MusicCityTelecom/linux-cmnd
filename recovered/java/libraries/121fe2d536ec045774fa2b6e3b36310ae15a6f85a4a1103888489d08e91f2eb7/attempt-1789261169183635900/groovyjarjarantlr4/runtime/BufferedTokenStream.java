/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime;

import groovyjarjarantlr4.runtime.BitSet;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.TokenSource;
import groovyjarjarantlr4.runtime.TokenStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class BufferedTokenStream
implements TokenStream {
    protected TokenSource tokenSource;
    protected List<Token> tokens = new ArrayList<Token>(100);
    protected int lastMarker;
    protected int p = -1;
    protected int range = -1;

    public BufferedTokenStream() {
    }

    public BufferedTokenStream(TokenSource tokenSource) {
        this.tokenSource = tokenSource;
    }

    @Override
    public TokenSource getTokenSource() {
        return this.tokenSource;
    }

    @Override
    public int index() {
        return this.p;
    }

    @Override
    public int range() {
        return this.range;
    }

    @Override
    public int mark() {
        if (this.p == -1) {
            this.setup();
        }
        this.lastMarker = this.index();
        return this.lastMarker;
    }

    @Override
    public void release(int marker) {
    }

    @Override
    public void rewind(int marker) {
        this.seek(marker);
    }

    @Override
    public void rewind() {
        this.seek(this.lastMarker);
    }

    public void reset() {
        this.p = 0;
        this.lastMarker = 0;
    }

    @Override
    public void seek(int index) {
        this.p = index;
    }

    @Override
    public int size() {
        return this.tokens.size();
    }

    @Override
    public void consume() {
        if (this.p == -1) {
            this.setup();
        }
        ++this.p;
        this.sync(this.p);
    }

    protected void sync(int i) {
        int n = i - this.tokens.size() + 1;
        if (n > 0) {
            this.fetch(n);
        }
    }

    protected void fetch(int n) {
        for (int i = 1; i <= n; ++i) {
            Token t = this.tokenSource.nextToken();
            t.setTokenIndex(this.tokens.size());
            this.tokens.add(t);
            if (t.getType() == -1) break;
        }
    }

    @Override
    public Token get(int i) {
        if (i < 0 || i >= this.tokens.size()) {
            throw new NoSuchElementException("token index " + i + " out of range 0.." + (this.tokens.size() - 1));
        }
        return this.tokens.get(i);
    }

    public List<? extends Token> get(int start, int stop) {
        Token t;
        if (start < 0 || stop < 0) {
            return null;
        }
        if (this.p == -1) {
            this.setup();
        }
        ArrayList<Token> subset = new ArrayList<Token>();
        if (stop >= this.tokens.size()) {
            stop = this.tokens.size() - 1;
        }
        for (int i = start; i <= stop && (t = this.tokens.get(i)).getType() != -1; ++i) {
            subset.add(t);
        }
        return subset;
    }

    @Override
    public int LA(int i) {
        return this.LT(i).getType();
    }

    protected Token LB(int k) {
        if (this.p - k < 0) {
            return null;
        }
        return this.tokens.get(this.p - k);
    }

    @Override
    public Token LT(int k) {
        if (this.p == -1) {
            this.setup();
        }
        if (k == 0) {
            return null;
        }
        if (k < 0) {
            return this.LB(-k);
        }
        int i = this.p + k - 1;
        this.sync(i);
        if (i >= this.tokens.size()) {
            return this.tokens.get(this.tokens.size() - 1);
        }
        if (i > this.range) {
            this.range = i;
        }
        return this.tokens.get(i);
    }

    protected void setup() {
        this.sync(0);
        this.p = 0;
    }

    public void setTokenSource(TokenSource tokenSource) {
        this.tokenSource = tokenSource;
        this.tokens.clear();
        this.p = -1;
    }

    public List<? extends Token> getTokens() {
        return this.tokens;
    }

    public List<? extends Token> getTokens(int start, int stop) {
        return this.getTokens(start, stop, (BitSet)null);
    }

    public List<? extends Token> getTokens(int start, int stop, BitSet types) {
        if (this.p == -1) {
            this.setup();
        }
        if (stop >= this.tokens.size()) {
            stop = this.tokens.size() - 1;
        }
        if (start < 0) {
            start = 0;
        }
        if (start > stop) {
            return null;
        }
        ArrayList<Token> filteredTokens = new ArrayList<Token>();
        for (int i = start; i <= stop; ++i) {
            Token t = this.tokens.get(i);
            if (types != null && !types.member(t.getType())) continue;
            filteredTokens.add(t);
        }
        if (filteredTokens.isEmpty()) {
            filteredTokens = null;
        }
        return filteredTokens;
    }

    public List<? extends Token> getTokens(int start, int stop, List<Integer> types) {
        return this.getTokens(start, stop, new BitSet(types));
    }

    public List<? extends Token> getTokens(int start, int stop, int ttype) {
        return this.getTokens(start, stop, BitSet.of(ttype));
    }

    @Override
    public String getSourceName() {
        return this.tokenSource.getSourceName();
    }

    public String toString() {
        if (this.p == -1) {
            this.setup();
        }
        this.fill();
        return this.toString(0, this.tokens.size() - 1);
    }

    @Override
    public String toString(int start, int stop) {
        Token t;
        if (start < 0 || stop < 0) {
            return null;
        }
        if (this.p == -1) {
            this.setup();
        }
        if (stop >= this.tokens.size()) {
            stop = this.tokens.size() - 1;
        }
        StringBuilder buf = new StringBuilder();
        for (int i = start; i <= stop && (t = this.tokens.get(i)).getType() != -1; ++i) {
            buf.append(t.getText());
        }
        return buf.toString();
    }

    @Override
    public String toString(Token start, Token stop) {
        if (start != null && stop != null) {
            return this.toString(start.getTokenIndex(), stop.getTokenIndex());
        }
        return null;
    }

    public void fill() {
        if (this.p == -1) {
            this.setup();
        }
        if (this.tokens.get(this.p).getType() == -1) {
            return;
        }
        int i = this.p + 1;
        this.sync(i);
        while (this.tokens.get(i).getType() != -1) {
            this.sync(++i);
        }
    }
}

