/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.tree.pattern;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.runtime.tree.pattern.Chunk;

class TagChunk
extends Chunk {
    private final String tag;
    private final String label;

    public TagChunk(String tag) {
        this(null, tag);
    }

    public TagChunk(String label, String tag) {
        if (tag == null || tag.isEmpty()) {
            throw new IllegalArgumentException("tag cannot be null or empty");
        }
        this.label = label;
        this.tag = tag;
    }

    @NotNull
    public final String getTag() {
        return this.tag;
    }

    @Nullable
    public final String getLabel() {
        return this.label;
    }

    public String toString() {
        if (this.label != null) {
            return this.label + ":" + this.tag;
        }
        return this.tag;
    }
}

