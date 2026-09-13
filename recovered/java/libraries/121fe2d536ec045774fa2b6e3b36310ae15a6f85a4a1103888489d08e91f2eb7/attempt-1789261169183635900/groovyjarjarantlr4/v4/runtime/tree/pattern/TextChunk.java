/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.tree.pattern;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.tree.pattern.Chunk;

class TextChunk
extends Chunk {
    @NotNull
    private final String text;

    public TextChunk(@NotNull String text) {
        if (text == null) {
            throw new IllegalArgumentException("text cannot be null");
        }
        this.text = text;
    }

    @NotNull
    public final String getText() {
        return this.text;
    }

    public String toString() {
        return "'" + this.text + "'";
    }
}

