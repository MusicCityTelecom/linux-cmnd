/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.tree.pattern;

import groovyjarjarantlr4.v4.runtime.misc.MultiMap;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.runtime.tree.ParseTree;
import groovyjarjarantlr4.v4.runtime.tree.pattern.ParseTreePattern;
import java.util.Collections;
import java.util.List;

public class ParseTreeMatch {
    private final ParseTree tree;
    private final ParseTreePattern pattern;
    private final MultiMap<String, ParseTree> labels;
    private final ParseTree mismatchedNode;

    public ParseTreeMatch(@NotNull ParseTree tree, @NotNull ParseTreePattern pattern, @NotNull MultiMap<String, ParseTree> labels, @Nullable ParseTree mismatchedNode) {
        if (tree == null) {
            throw new IllegalArgumentException("tree cannot be null");
        }
        if (pattern == null) {
            throw new IllegalArgumentException("pattern cannot be null");
        }
        if (labels == null) {
            throw new IllegalArgumentException("labels cannot be null");
        }
        this.tree = tree;
        this.pattern = pattern;
        this.labels = labels;
        this.mismatchedNode = mismatchedNode;
    }

    @Nullable
    public ParseTree get(String label) {
        List parseTrees = (List)this.labels.get(label);
        if (parseTrees == null || parseTrees.size() == 0) {
            return null;
        }
        return (ParseTree)parseTrees.get(parseTrees.size() - 1);
    }

    @NotNull
    public List<ParseTree> getAll(@NotNull String label) {
        List nodes = (List)this.labels.get(label);
        if (nodes == null) {
            return Collections.emptyList();
        }
        return nodes;
    }

    @NotNull
    public MultiMap<String, ParseTree> getLabels() {
        return this.labels;
    }

    @Nullable
    public ParseTree getMismatchedNode() {
        return this.mismatchedNode;
    }

    public boolean succeeded() {
        return this.mismatchedNode == null;
    }

    @NotNull
    public ParseTreePattern getPattern() {
        return this.pattern;
    }

    @NotNull
    public ParseTree getTree() {
        return this.tree;
    }

    public String toString() {
        return String.format("Match %s; found %d labels", this.succeeded() ? "succeeded" : "failed", this.getLabels().size());
    }
}

