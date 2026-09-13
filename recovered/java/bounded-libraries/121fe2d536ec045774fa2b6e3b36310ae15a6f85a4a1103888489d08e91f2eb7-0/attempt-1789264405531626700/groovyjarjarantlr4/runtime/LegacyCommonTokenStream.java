/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime;

import groovyjarjarantlr4.runtime.BitSet;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.TokenSource;
import groovyjarjarantlr4.runtime.TokenStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class LegacyCommonTokenStream
implements TokenStream {
    protected TokenSource tokenSource;
    protected List<Token> tokens = new ArrayList<Token>(500);
    protected Map<Integer, Integer> channelOverrideMap;
    protected Set<Integer> discardSet;
    protected int channel = 0;
    protected boolean discardOffChannelTokens = false;
    protected int lastMarker;
    protected int range = -1;
    protected int p = -1;

    public LegacyCommonTokenStream() {
    }

    public LegacyCommonTokenStream(TokenSource tokenSource) {
        this();
        this.tokenSource = tokenSource;
    }

    public LegacyCommonTokenStream(TokenSource tokenSource, int channel) {
        this(tokenSource);
        this.channel = channel;
    }

    public void setTokenSource(TokenSource tokenSource) {
        this.tokenSource = tokenSource;
        this.tokens.clear();
        this.p = -1;
        this.channel = 0;
    }

    protected void fillBuffer() {
        int index = 0;
        Token t = this.tokenSource.nextToken();
        while (t != null && t.getType() != -1) {
            Integer channelI;
            boolean discard = false;
            if (this.channelOverrideMap != null && (channelI = this.channelOverrideMap.get(t.getType())) != null) {
                t.setChannel(channelI);
            }
            if (this.discardSet != null && this.discardSet.contains(new Integer(t.getType()))) {
                discard = true;
            } else if (this.discardOffChannelTokens && t.getChannel() != this.channel) {
                discard = true;
            }
            if (!discard) {
                t.setTokenIndex(index);
                this.tokens.add(t);
                ++index;
            }
            t = this.tokenSource.nextToken();
        }
        this.p = 0;
        this.p = this.skipOffTokenChannels(this.p);
    }

    @Override
    public void consume() {
        if (this.p < this.tokens.size()) {
            ++this.p;
            this.p = this.skipOffTokenChannels(this.p);
        }
    }

    protected int skipOffTokenChannels(int i) {
        int n = this.tokens.size();
        while (i < n && this.tokens.get(i).getChannel() != this.channel) {
            ++i;
        }
        return i;
    }

    protected int skipOffTokenChannelsReverse(int i) {
        while (i >= 0 && this.tokens.get(i).getChannel() != this.channel) {
            --i;
        }
        return i;
    }

    public void setTokenTypeChannel(int ttype, int channel) {
        if (this.channelOverrideMap == null) {
            this.channelOverrideMap = new HashMap<Integer, Integer>();
        }
        this.channelOverrideMap.put(ttype, channel);
    }

    public void discardTokenType(int ttype) {
        if (this.discardSet == null) {
            this.discardSet = new HashSet<Integer>();
        }
        this.discardSet.add(ttype);
    }

    public void discardOffChannelTokens(boolean discardOffChannelTokens) {
        this.discardOffChannelTokens = discardOffChannelTokens;
    }

    public List<? extends Token> getTokens() {
        if (this.p == -1) {
            this.fillBuffer();
        }
        return this.tokens;
    }

    public List<? extends Token> getTokens(int start, int stop) {
        return this.getTokens(start, stop, (BitSet)null);
    }

    public List<? extends Token> getTokens(int start, int stop, BitSet types) {
        if (this.p == -1) {
            this.fillBuffer();
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
    public Token LT(int k) {
        if (this.p == -1) {
            this.fillBuffer();
        }
        if (k == 0) {
            return null;
        }
        if (k < 0) {
            return this.LB(-k);
        }
        if (this.p + k - 1 >= this.tokens.size()) {
            return this.tokens.get(this.tokens.size() - 1);
        }
        int i = this.p;
        for (int n = 1; n < k; ++n) {
            i = this.skipOffTokenChannels(i + 1);
        }
        if (i >= this.tokens.size()) {
            return this.tokens.get(this.tokens.size() - 1);
        }
        if (i > this.range) {
            this.range = i;
        }
        return this.tokens.get(i);
    }

    protected Token LB(int k) {
        if (this.p == -1) {
            this.fillBuffer();
        }
        if (k == 0) {
            return null;
        }
        if (this.p - k < 0) {
            return null;
        }
        int i = this.p;
        for (int n = 1; n <= k; ++n) {
            i = this.skipOffTokenChannelsReverse(i - 1);
        }
        if (i < 0) {
            return null;
        }
        return this.tokens.get(i);
    }

    @Override
    public Token get(int i) {
        return this.tokens.get(i);
    }

    public List<? extends Token> get(int start, int stop) {
        if (this.p == -1) {
            this.fillBuffer();
        }
        if (start < 0 || stop < 0) {
            return null;
        }
        return this.tokens.subList(start, stop);
    }

    @Override
    public int LA(int i) {
        return this.LT(i).getType();
    }

    @Override
    public int mark() {
        if (this.p == -1) {
            this.fillBuffer();
        }
        this.lastMarker = this.index();
        return this.lastMarker;
    }

    @Override
    public void release(int marker) {
    }

    @Override
    public int size() {
        return this.tokens.size();
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
    public TokenSource getTokenSource() {
        return this.tokenSource;
    }

    @Override
    public String getSourceName() {
        return this.getTokenSource().getSourceName();
    }

    public String toString() {
        if (this.p == -1) {
            this.fillBuffer();
        }
        return this.toString(0, this.tokens.size() - 1);
    }

    @Override
    public String toString(int start, int stop) {
        if (start < 0 || stop < 0) {
            return null;
        }
        if (this.p == -1) {
            this.fillBuffer();
        }
        if (stop >= this.tokens.size()) {
            stop = this.tokens.size() - 1;
        }
        StringBuilder buf = new StringBuilder();
        for (int i = start; i <= stop; ++i) {
            Token t = this.tokens.get(i);
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
}

