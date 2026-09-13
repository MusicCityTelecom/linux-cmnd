/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime.tree.pattern;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.tree.ParseTree;
import groovyjarjarantlr4.v4.runtime.tree.pattern.ParseTreeMatch;
import groovyjarjarantlr4.v4.runtime.tree.pattern.ParseTreePatternMatcher;
import groovyjarjarantlr4.v4.runtime.tree.xpath.XPath;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ParseTreePattern {
    private final int patternRuleIndex;
    @NotNull
    private final String pattern;
    @NotNull
    private final ParseTree patternTree;
    @NotNull
    private final ParseTreePatternMatcher matcher;

    public ParseTreePattern(@NotNull ParseTreePatternMatcher matcher, @NotNull String pattern, int patternRuleIndex, @NotNull ParseTree patternTree) {
        this.matcher = matcher;
        this.patternRuleIndex = patternRuleIndex;
        this.pattern = pattern;
        this.patternTree = patternTree;
    }

    @NotNull
    public ParseTreeMatch match(@NotNull ParseTree tree) {
        return this.matcher.match(tree, this);
    }

    public boolean matches(@NotNull ParseTree tree) {
        return this.matcher.match(tree, this).succeeded();
    }

    @NotNull
    public List<ParseTreeMatch> findAll(@NotNull ParseTree tree, @NotNull String xpath) {
        Collection<ParseTree> subtrees = XPath.findAll(tree, xpath, this.matcher.getParser());
        ArrayList<ParseTreeMatch> matches = new ArrayList<ParseTreeMatch>();
        for (ParseTree t : subtrees) {
            ParseTreeMatch match = this.match(t);
            if (!match.succeeded()) continue;
            matches.add(match);
        }
        return matches;
    }

    @NotNull
    public ParseTreePatternMatcher getMatcher() {
        return this.matcher;
    }

    @NotNull
    public String getPattern() {
        return this.pattern;
    }

    public int getPatternRuleIndex() {
        return this.patternRuleIndex;
    }

    @NotNull
    public ParseTree getPatternTree() {
        return this.patternTree;
    }
}

