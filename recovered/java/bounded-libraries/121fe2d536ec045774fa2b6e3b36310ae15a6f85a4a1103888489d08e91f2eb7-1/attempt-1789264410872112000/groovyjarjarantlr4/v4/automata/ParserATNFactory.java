/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.automata;

import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.tree.CommonTreeNodeStream;
import groovyjarjarantlr4.runtime.tree.Tree;
import groovyjarjarantlr4.runtime.tree.TreeNodeStream;
import groovyjarjarantlr4.v4.automata.ATNFactory;
import groovyjarjarantlr4.v4.automata.ATNOptimizer;
import groovyjarjarantlr4.v4.automata.TailEpsilonRemover;
import groovyjarjarantlr4.v4.misc.CharSupport;
import groovyjarjarantlr4.v4.parse.ATNBuilder;
import groovyjarjarantlr4.v4.parse.GrammarASTAdaptor;
import groovyjarjarantlr4.v4.runtime.atn.ATN;
import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.atn.ATNType;
import groovyjarjarantlr4.v4.runtime.atn.AbstractPredicateTransition;
import groovyjarjarantlr4.v4.runtime.atn.ActionTransition;
import groovyjarjarantlr4.v4.runtime.atn.AtomTransition;
import groovyjarjarantlr4.v4.runtime.atn.BasicBlockStartState;
import groovyjarjarantlr4.v4.runtime.atn.BasicState;
import groovyjarjarantlr4.v4.runtime.atn.BlockEndState;
import groovyjarjarantlr4.v4.runtime.atn.BlockStartState;
import groovyjarjarantlr4.v4.runtime.atn.EpsilonTransition;
import groovyjarjarantlr4.v4.runtime.atn.LL1Analyzer;
import groovyjarjarantlr4.v4.runtime.atn.LoopEndState;
import groovyjarjarantlr4.v4.runtime.atn.NotSetTransition;
import groovyjarjarantlr4.v4.runtime.atn.PlusBlockStartState;
import groovyjarjarantlr4.v4.runtime.atn.PlusLoopbackState;
import groovyjarjarantlr4.v4.runtime.atn.PrecedencePredicateTransition;
import groovyjarjarantlr4.v4.runtime.atn.PredicateTransition;
import groovyjarjarantlr4.v4.runtime.atn.PredictionContext;
import groovyjarjarantlr4.v4.runtime.atn.RuleStartState;
import groovyjarjarantlr4.v4.runtime.atn.RuleStopState;
import groovyjarjarantlr4.v4.runtime.atn.RuleTransition;
import groovyjarjarantlr4.v4.runtime.atn.SetTransition;
import groovyjarjarantlr4.v4.runtime.atn.StarBlockStartState;
import groovyjarjarantlr4.v4.runtime.atn.StarLoopEntryState;
import groovyjarjarantlr4.v4.runtime.atn.StarLoopbackState;
import groovyjarjarantlr4.v4.runtime.atn.Transition;
import groovyjarjarantlr4.v4.runtime.atn.WildcardTransition;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.runtime.misc.Tuple;
import groovyjarjarantlr4.v4.runtime.misc.Tuple3;
import groovyjarjarantlr4.v4.semantics.UseDefAnalyzer;
import groovyjarjarantlr4.v4.tool.ErrorManager;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.LeftRecursiveRule;
import groovyjarjarantlr4.v4.tool.LexerGrammar;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.AltAST;
import groovyjarjarantlr4.v4.tool.ast.BlockAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTWithOptions;
import groovyjarjarantlr4.v4.tool.ast.PredAST;
import groovyjarjarantlr4.v4.tool.ast.QuantifierAST;
import groovyjarjarantlr4.v4.tool.ast.TerminalAST;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ParserATNFactory
implements ATNFactory {
    @NotNull
    public final Grammar g;
    @NotNull
    public final ATN atn;
    public Rule currentRule;
    public int currentOuterAlt;
    @NotNull
    protected final List<Tuple3<? extends Rule, ? extends ATNState, ? extends ATNState>> preventEpsilonClosureBlocks = new ArrayList<Tuple3<? extends Rule, ? extends ATNState, ? extends ATNState>>();
    @NotNull
    protected final List<Tuple3<? extends Rule, ? extends ATNState, ? extends ATNState>> preventEpsilonOptionalBlocks = new ArrayList<Tuple3<? extends Rule, ? extends ATNState, ? extends ATNState>>();

    public ParserATNFactory(@NotNull Grammar g) {
        if (g == null) {
            throw new NullPointerException("g");
        }
        this.g = g;
        ATNType atnType = g instanceof LexerGrammar ? ATNType.LEXER : ATNType.PARSER;
        int maxTokenType = g.getMaxTokenType();
        this.atn = new ATN(atnType, maxTokenType);
    }

    @Override
    @NotNull
    public ATN createATN() {
        this._createATN(this.g.rules.values());
        assert (this.atn.maxTokenType == this.g.getMaxTokenType());
        this.addRuleFollowLinks();
        this.addEOFTransitionToStartRules();
        ATNOptimizer.optimize(this.g, this.atn);
        Iterator<Tuple3<? extends Rule, ? extends ATNState, ? extends ATNState>> i$ = this.preventEpsilonClosureBlocks.iterator();
        while (i$.hasNext()) {
            ATNState blkStop;
            LL1Analyzer analyzer = new LL1Analyzer(this.atn);
            Tuple3<? extends Rule, ? extends ATNState, ? extends ATNState> pair = i$.next();
            ATNState blkStart = pair.getItem2();
            IntervalSet lookahead = analyzer.LOOK(blkStart, blkStop = pair.getItem3(), PredictionContext.EMPTY_LOCAL);
            if (!lookahead.contains(-2)) continue;
            ErrorType errorType = pair.getItem1() instanceof LeftRecursiveRule ? ErrorType.EPSILON_LR_FOLLOW : ErrorType.EPSILON_CLOSURE;
            this.g.tool.errMgr.grammarError(errorType, this.g.fileName, ((GrammarAST)pair.getItem1().ast.getChild(0)).getToken(), pair.getItem1().name);
        }
        block1: for (Tuple3<? extends Rule, ? extends ATNState, ? extends ATNState> pair : this.preventEpsilonOptionalBlocks) {
            int bypassCount = 0;
            for (int i = 0; i < pair.getItem2().getNumberOfTransitions(); ++i) {
                ATNState startState = pair.getItem2().transition((int)i).target;
                if (startState == pair.getItem3()) {
                    ++bypassCount;
                    continue;
                }
                LL1Analyzer analyzer = new LL1Analyzer(this.atn);
                if (!analyzer.LOOK(startState, pair.getItem3(), PredictionContext.EMPTY_LOCAL).contains(-2)) continue;
                this.g.tool.errMgr.grammarError(ErrorType.EPSILON_OPTIONAL, this.g.fileName, ((GrammarAST)pair.getItem1().ast.getChild(0)).getToken(), pair.getItem1().name);
                continue block1;
            }
            if (bypassCount == true) continue;
            throw new UnsupportedOperationException("Expected optional block with exactly 1 bypass alternative.");
        }
        return this.atn;
    }

    protected void _createATN(@NotNull Collection<Rule> rules) {
        this.createRuleStartAndStopATNStates();
        GrammarASTAdaptor adaptor = new GrammarASTAdaptor();
        for (Rule r : rules) {
            GrammarAST blk = (GrammarAST)r.ast.getFirstChildWithType(78);
            CommonTreeNodeStream nodes = new CommonTreeNodeStream(adaptor, blk);
            ATNBuilder b = new ATNBuilder((TreeNodeStream)nodes, this);
            try {
                this.setCurrentRuleName(r.name);
                ATNFactory.Handle h = b.ruleBlock(null);
                this.rule(r.ast, r.name, h);
            }
            catch (RecognitionException re) {
                ErrorManager.fatalInternalError("bad grammar AST structure", re);
            }
        }
    }

    @Override
    public void setCurrentRuleName(@NotNull String name) {
        this.currentRule = this.g.getRule(name);
    }

    @Override
    public void setCurrentOuterAlt(int alt) {
        this.currentOuterAlt = alt;
    }

    @Override
    @NotNull
    public ATNFactory.Handle rule(@NotNull GrammarAST ruleAST, @NotNull String name, @NotNull ATNFactory.Handle blk) {
        Rule r = this.g.getRule(name);
        RuleStartState start = this.atn.ruleToStartState[r.index];
        this.epsilon(start, blk.left);
        RuleStopState stop = this.atn.ruleToStopState[r.index];
        this.epsilon(blk.right, stop);
        ATNFactory.Handle h = new ATNFactory.Handle(start, stop);
        ruleAST.atnState = start;
        return h;
    }

    @Override
    @NotNull
    public ATNFactory.Handle tokenRef(@NotNull TerminalAST node) {
        ATNState left = this.newState(node);
        ATNState right = this.newState(node);
        int ttype = this.g.getTokenType(node.getText());
        left.addTransition(new AtomTransition(right, ttype));
        node.atnState = left;
        return new ATNFactory.Handle(left, right);
    }

    @Override
    @NotNull
    public ATNFactory.Handle set(@NotNull GrammarAST associatedAST, @NotNull List<GrammarAST> terminals, boolean invert) {
        ATNState left = this.newState(associatedAST);
        ATNState right = this.newState(associatedAST);
        IntervalSet set = new IntervalSet(new int[0]);
        for (GrammarAST t : terminals) {
            int ttype = this.g.getTokenType(t.getText());
            set.add(ttype);
        }
        if (invert) {
            left.addTransition(new NotSetTransition(right, set));
        } else {
            left.addTransition(new SetTransition(right, set));
        }
        associatedAST.atnState = left;
        return new ATNFactory.Handle(left, right);
    }

    @Override
    @NotNull
    public ATNFactory.Handle range(@NotNull GrammarAST a, @NotNull GrammarAST b) {
        this.g.tool.errMgr.grammarError(ErrorType.TOKEN_RANGE_IN_PARSER, this.g.fileName, a.getToken(), a.getToken().getText(), b.getToken().getText());
        return this.tokenRef((TerminalAST)a);
    }

    protected int getTokenType(@NotNull GrammarAST atom) {
        int ttype = this.g.isLexer() ? CharSupport.getCharValueFromGrammarCharLiteral(atom.getText()) : this.g.getTokenType(atom.getText());
        return ttype;
    }

    @Override
    @NotNull
    public ATNFactory.Handle stringLiteral(@NotNull TerminalAST stringLiteralAST) {
        return this.tokenRef(stringLiteralAST);
    }

    @Override
    @NotNull
    public ATNFactory.Handle charSetLiteral(@NotNull GrammarAST charSetAST) {
        return null;
    }

    @Override
    @NotNull
    public ATNFactory.Handle ruleRef(@NotNull GrammarAST node) {
        ATNFactory.Handle h = this._ruleRef(node);
        return h;
    }

    @NotNull
    public ATNFactory.Handle _ruleRef(@NotNull GrammarAST node) {
        Rule r = this.g.getRule(node.getText());
        if (r == null) {
            this.g.tool.errMgr.grammarError(ErrorType.INTERNAL_ERROR, this.g.fileName, node.getToken(), "Rule " + node.getText() + " undefined");
            return null;
        }
        RuleStartState start = this.atn.ruleToStartState[r.index];
        ATNState left = this.newState(node);
        ATNState right = this.newState(node);
        int precedence = 0;
        if (((GrammarASTWithOptions)node).getOptionString("p") != null) {
            precedence = Integer.parseInt(((GrammarASTWithOptions)node).getOptionString("p"));
        }
        RuleTransition call = new RuleTransition(start, r.index, precedence, right);
        left.addTransition(call);
        node.atnState = left;
        return new ATNFactory.Handle(left, right);
    }

    public void addFollowLink(int ruleIndex, ATNState right) {
        RuleStopState stop = this.atn.ruleToStopState[ruleIndex];
        this.epsilon(stop, right);
    }

    @Override
    @NotNull
    public ATNFactory.Handle epsilon(@NotNull GrammarAST node) {
        ATNState left = this.newState(node);
        ATNState right = this.newState(node);
        this.epsilon(left, right);
        node.atnState = left;
        return new ATNFactory.Handle(left, right);
    }

    @Override
    @NotNull
    public ATNFactory.Handle sempred(@NotNull PredAST pred) {
        AbstractPredicateTransition p;
        ATNState left = this.newState(pred);
        ATNState right = this.newState(pred);
        if (pred.getOptionString("p") != null) {
            int precedence = Integer.parseInt(pred.getOptionString("p"));
            p = new PrecedencePredicateTransition(right, precedence);
        } else {
            boolean isCtxDependent = UseDefAnalyzer.actionIsContextDependent(pred);
            p = new PredicateTransition(right, this.currentRule.index, this.g.sempreds.get(pred), isCtxDependent);
        }
        left.addTransition(p);
        pred.atnState = left;
        return new ATNFactory.Handle(left, right);
    }

    @Override
    @NotNull
    public ATNFactory.Handle action(@NotNull ActionAST action) {
        ATNState left = this.newState(action);
        ATNState right = this.newState(action);
        ActionTransition a = new ActionTransition(right, this.currentRule.index);
        left.addTransition(a);
        action.atnState = left;
        return new ATNFactory.Handle(left, right);
    }

    @Override
    @NotNull
    public ATNFactory.Handle action(@NotNull String action) {
        throw new UnsupportedOperationException("This element is not valid in parsers.");
    }

    @Override
    @NotNull
    public ATNFactory.Handle block(@NotNull BlockAST blkAST, @NotNull GrammarAST ebnfRoot, @NotNull List<ATNFactory.Handle> alts) {
        if (ebnfRoot == null) {
            if (alts.size() == 1) {
                ATNFactory.Handle h = alts.get(0);
                blkAST.atnState = h.left;
                return h;
            }
            BlockStartState start = this.newState(BasicBlockStartState.class, blkAST);
            if (alts.size() > 1) {
                this.atn.defineDecisionState(start);
            }
            return this.makeBlock(start, blkAST, alts);
        }
        switch (ebnfRoot.getType()) {
            case 89: {
                BlockStartState start = this.newState(BasicBlockStartState.class, blkAST);
                this.atn.defineDecisionState(start);
                ATNFactory.Handle h = this.makeBlock(start, blkAST, alts);
                return this.optional(ebnfRoot, h);
            }
            case 80: {
                BlockStartState star = this.newState(StarBlockStartState.class, ebnfRoot);
                if (alts.size() > 1) {
                    this.atn.defineDecisionState(star);
                }
                ATNFactory.Handle h = this.makeBlock(star, blkAST, alts);
                return this.star(ebnfRoot, h);
            }
            case 90: {
                PlusBlockStartState plus = this.newState(PlusBlockStartState.class, ebnfRoot);
                if (alts.size() > 1) {
                    this.atn.defineDecisionState(plus);
                }
                ATNFactory.Handle h = this.makeBlock(plus, blkAST, alts);
                return this.plus(ebnfRoot, h);
            }
        }
        return null;
    }

    @NotNull
    protected ATNFactory.Handle makeBlock(@NotNull BlockStartState start, @NotNull BlockAST blkAST, @NotNull List<ATNFactory.Handle> alts) {
        BlockEndState end;
        start.sll = this.isSLLDecision(blkAST);
        start.endState = end = this.newState(BlockEndState.class, blkAST);
        for (ATNFactory.Handle alt : alts) {
            this.epsilon(start, alt.left);
            this.epsilon(alt.right, end);
            TailEpsilonRemover opt = new TailEpsilonRemover(this.atn);
            opt.visit(alt.left);
        }
        ATNFactory.Handle h = new ATNFactory.Handle(start, end);
        blkAST.atnState = start;
        return h;
    }

    @Override
    @NotNull
    public ATNFactory.Handle alt(@NotNull List<ATNFactory.Handle> els) {
        return this.elemList(els);
    }

    @NotNull
    public ATNFactory.Handle elemList(@NotNull List<ATNFactory.Handle> els) {
        int n = els.size();
        for (int i = 0; i < n - 1; ++i) {
            ATNFactory.Handle el = els.get(i);
            Transition tr = null;
            if (el.left.getNumberOfTransitions() == 1) {
                tr = el.left.transition(0);
            }
            boolean isRuleTrans = tr instanceof RuleTransition;
            if (el.left.getStateType() == 1 && el.right.getStateType() == 1 && tr != null && (isRuleTrans && ((RuleTransition)tr).followState == el.right || tr.target == el.right)) {
                if (isRuleTrans) {
                    ((RuleTransition)tr).followState = els.get((int)(i + 1)).left;
                } else {
                    tr.target = els.get((int)(i + 1)).left;
                }
                this.atn.removeState(el.right);
                continue;
            }
            this.epsilon(el.right, els.get((int)(i + 1)).left);
        }
        ATNFactory.Handle first = els.get(0);
        ATNFactory.Handle last = els.get(n - 1);
        if (first == null || last == null) {
            this.g.tool.errMgr.toolError(ErrorType.INTERNAL_ERROR, "element list has first|last == null");
        }
        return new ATNFactory.Handle(first.left, last.right);
    }

    @Override
    @NotNull
    public ATNFactory.Handle optional(@NotNull GrammarAST optAST, @NotNull ATNFactory.Handle blk) {
        BlockStartState blkStart = (BlockStartState)blk.left;
        ATNState blkEnd = blk.right;
        this.preventEpsilonOptionalBlocks.add(Tuple.create(this.currentRule, blkStart, blkEnd));
        boolean greedy = ((QuantifierAST)((Object)optAST)).isGreedy();
        blkStart.sll = false;
        blkStart.nonGreedy = !greedy;
        this.epsilon(blkStart, blk.right, !greedy);
        optAST.atnState = blk.left;
        return blk;
    }

    @Override
    @NotNull
    public ATNFactory.Handle plus(@NotNull GrammarAST plusAST, @NotNull ATNFactory.Handle blk) {
        PlusBlockStartState blkStart = (PlusBlockStartState)blk.left;
        BlockEndState blkEnd = (BlockEndState)blk.right;
        this.preventEpsilonClosureBlocks.add(Tuple.create(this.currentRule, blkStart, blkEnd));
        PlusLoopbackState loop = this.newState(PlusLoopbackState.class, plusAST);
        loop.nonGreedy = !((QuantifierAST)((Object)plusAST)).isGreedy();
        loop.sll = false;
        this.atn.defineDecisionState(loop);
        LoopEndState end = this.newState(LoopEndState.class, plusAST);
        blkStart.loopBackState = loop;
        end.loopBackState = loop;
        plusAST.atnState = loop;
        this.epsilon(blkEnd, loop);
        BlockAST blkAST = (BlockAST)plusAST.getChild(0);
        if (((QuantifierAST)((Object)plusAST)).isGreedy()) {
            if (this.expectNonGreedy(blkAST)) {
                this.g.tool.errMgr.grammarError(ErrorType.EXPECTED_NON_GREEDY_WILDCARD_BLOCK, this.g.fileName, plusAST.getToken(), plusAST.getToken().getText());
            }
            this.epsilon(loop, blkStart);
            this.epsilon(loop, end);
        } else {
            this.epsilon(loop, end);
            this.epsilon(loop, blkStart);
        }
        return new ATNFactory.Handle(blkStart, end);
    }

    @Override
    @NotNull
    public ATNFactory.Handle star(@NotNull GrammarAST starAST, @NotNull ATNFactory.Handle elem) {
        StarLoopbackState loop;
        StarBlockStartState blkStart = (StarBlockStartState)elem.left;
        BlockEndState blkEnd = (BlockEndState)elem.right;
        this.preventEpsilonClosureBlocks.add(Tuple.create(this.currentRule, blkStart, blkEnd));
        StarLoopEntryState entry = this.newState(StarLoopEntryState.class, starAST);
        entry.nonGreedy = !((QuantifierAST)((Object)starAST)).isGreedy();
        entry.sll = false;
        this.atn.defineDecisionState(entry);
        LoopEndState end = this.newState(LoopEndState.class, starAST);
        entry.loopBackState = loop = this.newState(StarLoopbackState.class, starAST);
        end.loopBackState = loop;
        BlockAST blkAST = (BlockAST)starAST.getChild(0);
        if (((QuantifierAST)((Object)starAST)).isGreedy()) {
            if (this.expectNonGreedy(blkAST)) {
                this.g.tool.errMgr.grammarError(ErrorType.EXPECTED_NON_GREEDY_WILDCARD_BLOCK, this.g.fileName, starAST.getToken(), starAST.getToken().getText());
            }
            this.epsilon(entry, blkStart);
            this.epsilon(entry, end);
        } else {
            this.epsilon(entry, end);
            this.epsilon(entry, blkStart);
        }
        this.epsilon(blkEnd, loop);
        this.epsilon(loop, entry);
        starAST.atnState = entry;
        return new ATNFactory.Handle(entry, end);
    }

    @Override
    @NotNull
    public ATNFactory.Handle wildcard(@NotNull GrammarAST node) {
        ATNState left = this.newState(node);
        ATNState right = this.newState(node);
        left.addTransition(new WildcardTransition(right));
        node.atnState = left;
        return new ATNFactory.Handle(left, right);
    }

    protected void epsilon(ATNState a, @NotNull ATNState b) {
        this.epsilon(a, b, false);
    }

    protected void epsilon(ATNState a, @NotNull ATNState b, boolean prepend) {
        for (Transition t : a.getTransitions()) {
            if (t.getSerializationType() != 1 || t.target != b || ((EpsilonTransition)t).outermostPrecedenceReturn() != -1) continue;
            return;
        }
        if (a != null) {
            int index = prepend ? 0 : a.getNumberOfTransitions();
            a.addTransition(index, new EpsilonTransition(b));
        }
    }

    void createRuleStartAndStopATNStates() {
        this.atn.ruleToStartState = new RuleStartState[this.g.rules.size()];
        this.atn.ruleToStopState = new RuleStopState[this.g.rules.size()];
        for (Rule r : this.g.rules.values()) {
            RuleStopState stop;
            RuleStartState start = this.newState(RuleStartState.class, r.ast);
            start.stopState = stop = this.newState(RuleStopState.class, r.ast);
            start.isPrecedenceRule = r instanceof LeftRecursiveRule;
            start.setRuleIndex(r.index);
            stop.setRuleIndex(r.index);
            this.atn.ruleToStartState[r.index] = start;
            this.atn.ruleToStopState[r.index] = stop;
        }
    }

    public void addRuleFollowLinks() {
        for (ATNState p : this.atn.states) {
            if (p == null || p.getStateType() != 1 || p.getNumberOfTransitions() != 1 || !(p.transition(0) instanceof RuleTransition)) continue;
            RuleTransition rt = (RuleTransition)p.transition(0);
            this.addFollowLink(rt.ruleIndex, rt.followState);
        }
    }

    public int addEOFTransitionToStartRules() {
        int n = 0;
        ATNState eofTarget = this.newState(null);
        for (Rule r : this.g.rules.values()) {
            RuleStopState stop = this.atn.ruleToStopState[r.index];
            if (stop.getNumberOfTransitions() > 0) continue;
            ++n;
            AtomTransition t = new AtomTransition(eofTarget, -1);
            stop.addTransition(t);
        }
        return n;
    }

    @Override
    @NotNull
    public ATNFactory.Handle label(@NotNull ATNFactory.Handle t) {
        return t;
    }

    @Override
    @NotNull
    public ATNFactory.Handle listLabel(@NotNull ATNFactory.Handle t) {
        return t;
    }

    @NotNull
    public <T extends ATNState> T newState(@NotNull Class<T> nodeType, GrammarAST node) {
        Exception cause;
        try {
            Constructor<T> ctor = nodeType.getConstructor(new Class[0]);
            ATNState s = (ATNState)ctor.newInstance(new Object[0]);
            if (this.currentRule == null) {
                s.setRuleIndex(-1);
            } else {
                s.setRuleIndex(this.currentRule.index);
            }
            this.atn.addState(s);
            return (T)s;
        }
        catch (InstantiationException ex) {
            cause = ex;
        }
        catch (IllegalAccessException ex) {
            cause = ex;
        }
        catch (IllegalArgumentException ex) {
            cause = ex;
        }
        catch (InvocationTargetException ex) {
            cause = ex;
        }
        catch (NoSuchMethodException ex) {
            cause = ex;
        }
        catch (SecurityException ex) {
            cause = ex;
        }
        String message = String.format("Could not create %s of type %s.", ATNState.class.getName(), nodeType.getName());
        throw new UnsupportedOperationException(message, cause);
    }

    @NotNull
    public ATNState newState(@Nullable GrammarAST node) {
        BasicState n = new BasicState();
        n.setRuleIndex(this.currentRule.index);
        this.atn.addState(n);
        return n;
    }

    @Override
    @NotNull
    public ATNState newState() {
        return this.newState(null);
    }

    public boolean expectNonGreedy(@NotNull BlockAST blkAST) {
        return ParserATNFactory.blockHasWildcardAlt(blkAST);
    }

    public boolean isSLLDecision(@NotNull BlockAST blkAST) {
        return Boolean.toString(true).equalsIgnoreCase(blkAST.getOptionString("sll"));
    }

    public static boolean blockHasWildcardAlt(@NotNull GrammarAST block) {
        for (Object object : block.getChildren()) {
            Tree e;
            AltAST altAST;
            if (!(object instanceof AltAST) || (altAST = (AltAST)object).getChildCount() != 1 && (altAST.getChildCount() != 2 || altAST.getChild(0).getType() != 82) || (e = altAST.getChild(altAST.getChildCount() - 1)).getType() != 100) continue;
            return true;
        }
        return false;
    }

    @Override
    @NotNull
    public ATNFactory.Handle lexerAltCommands(@NotNull ATNFactory.Handle alt, @NotNull ATNFactory.Handle cmds) {
        throw new UnsupportedOperationException("This element is not allowed in parsers.");
    }

    @Override
    @NotNull
    public ATNFactory.Handle lexerCallCommand(@NotNull GrammarAST ID, @NotNull GrammarAST arg) {
        throw new UnsupportedOperationException("This element is not allowed in parsers.");
    }

    @Override
    @NotNull
    public ATNFactory.Handle lexerCommand(@NotNull GrammarAST ID) {
        throw new UnsupportedOperationException("This element is not allowed in parsers.");
    }
}

