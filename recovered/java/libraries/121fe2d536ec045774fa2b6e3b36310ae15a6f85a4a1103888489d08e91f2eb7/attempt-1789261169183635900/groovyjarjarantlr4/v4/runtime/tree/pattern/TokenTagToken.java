/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.tree.pattern;

import groovyjarjarantlr4.v4.runtime.CommonToken;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;

public class TokenTagToken
extends CommonToken {
    @NotNull
    private final String tokenName;
    @Nullable
    private final String label;

    public TokenTagToken(@NotNull String tokenName, int type) {
        this(tokenName, type, null);
    }

    public TokenTagToken(@NotNull String tokenName, int type, @Nullable String label) {
        super(type);
        this.tokenName = tokenName;
        this.label = label;
    }

    @NotNull
    public final String getTokenName() {
        return this.tokenName;
    }

    @Nullable
    public final String getLabel() {
        return this.label;
    }

    @Override
    public String getText() {
        if (this.label != null) {
            return "<" + this.label + ":" + this.tokenName + ">";
        }
        return "<" + this.tokenName + ">";
    }

    @Override
    public String toString() {
        return this.tokenName + ":" + this.type;
    }
}

