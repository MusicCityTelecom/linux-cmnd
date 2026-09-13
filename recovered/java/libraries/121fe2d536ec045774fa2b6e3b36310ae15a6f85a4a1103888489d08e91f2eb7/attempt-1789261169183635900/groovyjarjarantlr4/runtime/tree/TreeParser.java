/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.tree;

import groovyjarjarantlr4.runtime.BaseRecognizer;
import groovyjarjarantlr4.runtime.BitSet;
import groovyjarjarantlr4.runtime.CommonToken;
import groovyjarjarantlr4.runtime.IntStream;
import groovyjarjarantlr4.runtime.MismatchedTreeNodeException;
import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.RecognizerSharedState;
import groovyjarjarantlr4.runtime.tree.TreeAdaptor;
import groovyjarjarantlr4.runtime.tree.TreeNodeStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TreeParser
extends BaseRecognizer {
    public static final int DOWN = 2;
    public static final int UP = 3;
    static String dotdot = ".*[^.]\\.\\.[^.].*";
    static String doubleEtc = ".*\\.\\.\\.\\s+\\.\\.\\..*";
    static Pattern dotdotPattern = Pattern.compile(dotdot);
    static Pattern doubleEtcPattern = Pattern.compile(doubleEtc);
    protected TreeNodeStream input;

    public TreeParser(TreeNodeStream input) {
        this.setTreeNodeStream(input);
    }

    public TreeParser(TreeNodeStream input, RecognizerSharedState state) {
        super(state);
        this.setTreeNodeStream(input);
    }

    public void reset() {
        super.reset();
        if (this.input != null) {
            this.input.seek(0);
        }
    }

    public void setTreeNodeStream(TreeNodeStream input) {
        this.input = input;
    }

    public TreeNodeStream getTreeNodeStream() {
        return this.input;
    }

    public String getSourceName() {
        return this.input.getSourceName();
    }

    protected Object getCurrentInputSymbol(IntStream input) {
        return ((TreeNodeStream)input).LT(1);
    }

    protected Object getMissingSymbol(IntStream input, RecognitionException e, int expectedTokenType, BitSet follow) {
        String tokenText = "<missing " + this.getTokenNames()[expectedTokenType] + ">";
        TreeAdaptor adaptor = ((TreeNodeStream)e.input).getTreeAdaptor();
        return adaptor.create(new CommonToken(expectedTokenType, tokenText));
    }

    public void matchAny(IntStream ignore) {
        this.state.errorRecovery = false;
        this.state.failed = false;
        Object look = this.input.LT(1);
        if (this.input.getTreeAdaptor().getChildCount(look) == 0) {
            this.input.consume();
            return;
        }
        int level = 0;
        int tokenType = this.input.getTreeAdaptor().getType(look);
        while (tokenType != -1 && (tokenType != 3 || level != 0)) {
            this.input.consume();
            look = this.input.LT(1);
            tokenType = this.input.getTreeAdaptor().getType(look);
            if (tokenType == 2) {
                ++level;
                continue;
            }
            if (tokenType != 3) continue;
            --level;
        }
        this.input.consume();
    }

    protected Object recoverFromMismatchedToken(IntStream input, int ttype, BitSet follow) throws RecognitionException {
        throw new MismatchedTreeNodeException(ttype, (TreeNodeStream)input);
    }

    public String getErrorHeader(RecognitionException e) {
        return this.getGrammarFileName() + ": node from " + (e.approximateLineInfo ? "after " : "") + "line " + e.line + ":" + e.charPositionInLine;
    }

    public String getErrorMessage(RecognitionException e, String[] tokenNames) {
        if (this instanceof TreeParser) {
            TreeAdaptor adaptor = ((TreeNodeStream)e.input).getTreeAdaptor();
            e.token = adaptor.getToken(e.node);
            if (e.token == null) {
                e.token = new CommonToken(adaptor.getType(e.node), adaptor.getText(e.node));
            }
        }
        return super.getErrorMessage(e, tokenNames);
    }

    public boolean inContext(String context) {
        return TreeParser.inContext(this.input.getTreeAdaptor(), this.getTokenNames(), this.input.LT(1), context);
    }

    public static boolean inContext(TreeAdaptor adaptor, String[] tokenNames, Object t, String context) {
        int ni;
        Matcher dotdotMatcher = dotdotPattern.matcher(context);
        Matcher doubleEtcMatcher = doubleEtcPattern.matcher(context);
        if (dotdotMatcher.find()) {
            throw new IllegalArgumentException("invalid syntax: ..");
        }
        if (doubleEtcMatcher.find()) {
            throw new IllegalArgumentException("invalid syntax: ... ...");
        }
        context = context.replaceAll("\\.\\.\\.", " ... ");
        context = context.trim();
        String[] nodes = context.split("\\s+");
        t = adaptor.getParent(t);
        for (ni = nodes.length - 1; ni >= 0 && t != null; --ni) {
            String name;
            if (nodes[ni].equals("...")) {
                if (ni == 0) {
                    return true;
                }
                String goal = nodes[ni - 1];
                Object ancestor = TreeParser.getAncestor(adaptor, tokenNames, t, goal);
                if (ancestor == null) {
                    return false;
                }
                t = ancestor;
                --ni;
            }
            if (!(name = tokenNames[adaptor.getType(t)]).equals(nodes[ni])) {
                return false;
            }
            t = adaptor.getParent(t);
        }
        return t != null || ni < 0;
    }

    protected static Object getAncestor(TreeAdaptor adaptor, String[] tokenNames, Object t, String goal) {
        while (t != null) {
            String name = tokenNames[adaptor.getType(t)];
            if (name.equals(goal)) {
                return t;
            }
            t = adaptor.getParent(t);
        }
        return null;
    }

    public void traceIn(String ruleName, int ruleIndex) {
        super.traceIn(ruleName, ruleIndex, this.input.LT(1));
    }

    public void traceOut(String ruleName, int ruleIndex) {
        super.traceOut(ruleName, ruleIndex, this.input.LT(1));
    }
}

