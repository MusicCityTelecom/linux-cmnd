/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.tree.CommonTree;
import groovyjarjarantlr4.runtime.tree.CommonTreeNodeStream;
import groovyjarjarantlr4.v4.codegen.OutputModelController;
import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.Action;
import groovyjarjarantlr4.v4.codegen.model.CodeBlockForOuterMostAlt;
import groovyjarjarantlr4.v4.codegen.model.ElementFrequenciesVisitor;
import groovyjarjarantlr4.v4.codegen.model.ExceptionClause;
import groovyjarjarantlr4.v4.codegen.model.ModelElement;
import groovyjarjarantlr4.v4.codegen.model.OutputModelObject;
import groovyjarjarantlr4.v4.codegen.model.SrcOp;
import groovyjarjarantlr4.v4.codegen.model.decl.AltLabelStructDecl;
import groovyjarjarantlr4.v4.codegen.model.decl.AttributeDecl;
import groovyjarjarantlr4.v4.codegen.model.decl.ContextRuleGetterDecl;
import groovyjarjarantlr4.v4.codegen.model.decl.ContextRuleListGetterDecl;
import groovyjarjarantlr4.v4.codegen.model.decl.ContextRuleListIndexedGetterDecl;
import groovyjarjarantlr4.v4.codegen.model.decl.ContextTokenGetterDecl;
import groovyjarjarantlr4.v4.codegen.model.decl.ContextTokenListGetterDecl;
import groovyjarjarantlr4.v4.codegen.model.decl.ContextTokenListIndexedGetterDecl;
import groovyjarjarantlr4.v4.codegen.model.decl.Decl;
import groovyjarjarantlr4.v4.codegen.model.decl.StructDecl;
import groovyjarjarantlr4.v4.misc.FrequencySet;
import groovyjarjarantlr4.v4.misc.Utils;
import groovyjarjarantlr4.v4.parse.GrammarASTAdaptor;
import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.runtime.misc.OrderedHashSet;
import groovyjarjarantlr4.v4.runtime.misc.Tuple;
import groovyjarjarantlr4.v4.runtime.misc.Tuple2;
import groovyjarjarantlr4.v4.tool.Attribute;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.AltAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.PredAST;
import groovyjarjarantlr4.v4.tool.ast.RuleAST;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RuleFunction
extends OutputModelObject {
    public String name;
    public List<String> modifiers;
    public String ctxType;
    public Collection<String> ruleLabels;
    public Collection<String> tokenLabels;
    public ATNState startState;
    public int index;
    public Rule rule;
    public boolean hasLookaheadBlock;
    public String variantOf;
    @ModelElement
    public List<SrcOp> code;
    @ModelElement
    public OrderedHashSet<Decl> locals;
    @ModelElement
    public Collection<AttributeDecl> args = null;
    @ModelElement
    public StructDecl ruleCtx;
    @ModelElement
    public Map<String, AltLabelStructDecl> altLabelCtxs;
    @ModelElement
    public Map<String, Action> namedActions;
    @ModelElement
    public Action finallyAction;
    @ModelElement
    public List<ExceptionClause> exceptions;
    @ModelElement
    public List<SrcOp> postamble;

    public RuleFunction(OutputModelFactory factory, Rule r) {
        super(factory);
        this.name = r.name;
        this.rule = r;
        if (r.modifiers != null && !r.modifiers.isEmpty()) {
            this.modifiers = new ArrayList<String>();
            for (GrammarAST t : r.modifiers) {
                this.modifiers.add(t.getText());
            }
        }
        this.modifiers = Utils.nodesToStrings(r.modifiers);
        this.index = r.index;
        int lfIndex = this.name.indexOf(36);
        if (lfIndex >= 0) {
            this.variantOf = this.name.substring(0, lfIndex);
        }
        if (r.name.equals(r.getBaseContext())) {
            Collection<Attribute> decls;
            this.ruleCtx = new StructDecl(factory, r);
            this.addContextGetters(factory, (Collection<RuleAST>)r.g.contextASTs.get(r.name));
            if (r.args != null && (decls = r.args.attributes.values()).size() > 0) {
                this.args = new ArrayList<AttributeDecl>();
                this.ruleCtx.addDecls(decls);
                for (Attribute a : decls) {
                    this.args.add(new AttributeDecl(factory, a));
                }
                this.ruleCtx.ctorAttrs = this.args;
            }
            if (r.retvals != null) {
                this.ruleCtx.addDecls(r.retvals.attributes.values());
            }
            if (r.locals != null) {
                this.ruleCtx.addDecls(r.locals.attributes.values());
            }
        } else if (r.args != null || r.retvals != null || r.locals != null) {
            throw new UnsupportedOperationException("customized fields are not yet supported for customized context objects");
        }
        this.ruleLabels = r.getElementLabelNames();
        this.tokenLabels = r.getTokenRefs();
        if (r.exceptions != null) {
            this.exceptions = new ArrayList<ExceptionClause>();
            for (GrammarAST e : r.exceptions) {
                ActionAST catchArg = (ActionAST)e.getChild(0);
                ActionAST catchAction = (ActionAST)e.getChild(1);
                this.exceptions.add(new ExceptionClause(factory, catchArg, catchAction));
            }
        }
        this.startState = factory.getGrammar().atn.ruleToStartState[r.index];
    }

    public StructDecl getEffectiveRuleContext(OutputModelController controller) {
        if (this.ruleCtx != null) {
            return this.ruleCtx;
        }
        RuleFunction effectiveRuleFunction = controller.rule(controller.getGrammar().getRule(this.rule.getBaseContext()));
        if (effectiveRuleFunction == this) {
            throw new IllegalStateException("Rule function does not have an effective context");
        }
        assert (effectiveRuleFunction.ruleCtx != null);
        return effectiveRuleFunction.ruleCtx;
    }

    public Map<String, AltLabelStructDecl> getEffectiveAltLabelContexts(OutputModelController controller) {
        if (this.altLabelCtxs != null) {
            return this.altLabelCtxs;
        }
        RuleFunction effectiveRuleFunction = controller.rule(controller.getGrammar().getRule(this.rule.getBaseContext()));
        if (effectiveRuleFunction == this) {
            throw new IllegalStateException("Rule function does not have an effective context");
        }
        assert (effectiveRuleFunction.altLabelCtxs != null);
        return effectiveRuleFunction.altLabelCtxs;
    }

    public void addContextGetters(OutputModelFactory factory, Collection<RuleAST> contextASTs) {
        ArrayList<AltAST> unlabeledAlternatives = new ArrayList<AltAST>();
        LinkedHashMap labeledAlternatives = new LinkedHashMap();
        for (RuleAST ruleAST : contextASTs) {
            try {
                unlabeledAlternatives.addAll(this.rule.g.getUnlabeledAlternatives(ruleAST));
                for (Map.Entry<String, List<Tuple2<Integer, AltAST>>> entry : this.rule.g.getLabeledAlternatives(ruleAST).entrySet()) {
                    ArrayList<AltAST> list = (ArrayList<AltAST>)labeledAlternatives.get(entry.getKey());
                    if (list == null) {
                        list = new ArrayList<AltAST>();
                        labeledAlternatives.put(entry.getKey(), list);
                    }
                    for (Tuple2<Integer, AltAST> tuple : entry.getValue()) {
                        list.add(tuple.getItem2());
                    }
                }
            }
            catch (RecognitionException ex) {
            }
        }
        if (!unlabeledAlternatives.isEmpty()) {
            Set<Decl> decls = this.getDeclsForAllElements(unlabeledAlternatives);
            for (Decl decl : decls) {
                this.ruleCtx.addDecl(decl);
            }
        }
        this.altLabelCtxs = new LinkedHashMap<String, AltLabelStructDecl>();
        if (!labeledAlternatives.isEmpty()) {
            for (Map.Entry entry : labeledAlternatives.entrySet()) {
                AltLabelStructDecl labelDecl = new AltLabelStructDecl(factory, this.rule, (String)entry.getKey());
                this.altLabelCtxs.put((String)entry.getKey(), labelDecl);
                Set<Decl> decls = this.getDeclsForAllElements((List)entry.getValue());
                for (Decl decl : decls) {
                    labelDecl.addDecl(decl);
                }
            }
        }
    }

    public void fillNamedActions(OutputModelFactory factory, Rule r) {
        if (r.finallyAction != null) {
            this.finallyAction = new Action(factory, r.finallyAction);
        }
        this.namedActions = new HashMap<String, Action>();
        for (String name : r.namedActions.keySet()) {
            ActionAST ast = r.namedActions.get(name);
            this.namedActions.put(name, new Action(factory, ast));
        }
    }

    public Set<Decl> getDeclsForAllElements(List<AltAST> altASTs) {
        HashSet<String> needsList = new HashSet<String>();
        HashSet<String> nonOptional = new HashSet<String>();
        HashSet<String> suppress = new HashSet<String>();
        ArrayList<GrammarAST> allRefs = new ArrayList<GrammarAST>();
        boolean firstAlt = true;
        IntervalSet reftypes = new IntervalSet(57, 66, 62);
        for (AltAST ast : altASTs) {
            List<GrammarAST> refs = this.getRuleTokens(ast.getNodesWithType(reftypes));
            allRefs.addAll(refs);
            Tuple2<FrequencySet<String>, FrequencySet<String>> minAndAltFreq = this.getElementFrequenciesForAlt(ast);
            FrequencySet<String> minFreq = minAndAltFreq.getItem1();
            FrequencySet<String> altFreq = minAndAltFreq.getItem2();
            for (GrammarAST t : refs) {
                String refLabelName = RuleFunction.getLabelName(this.rule.g, t);
                if (refLabelName == null) continue;
                if (altFreq.count(refLabelName) == 0) {
                    suppress.add(refLabelName);
                }
                if (altFreq.count(refLabelName) > 1) {
                    needsList.add(refLabelName);
                }
                if (!firstAlt || minFreq.count(refLabelName) == 0) continue;
                nonOptional.add(refLabelName);
            }
            for (String ref : nonOptional.toArray(new String[nonOptional.size()])) {
                if (minFreq.count(ref) != 0) continue;
                nonOptional.remove(ref);
            }
            firstAlt = false;
        }
        LinkedHashSet<Decl> decls = new LinkedHashSet<Decl>();
        for (GrammarAST t : allRefs) {
            String refLabelName = RuleFunction.getLabelName(this.rule.g, t);
            if (refLabelName == null || suppress.contains(refLabelName)) continue;
            List<Decl> d = this.getDeclForAltElement(t, refLabelName, needsList.contains(refLabelName), !nonOptional.contains(refLabelName));
            decls.addAll(d);
        }
        return decls;
    }

    private List<GrammarAST> getRuleTokens(List<GrammarAST> refs) {
        ArrayList<GrammarAST> result = new ArrayList<GrammarAST>(refs.size());
        Iterator<GrammarAST> i$ = refs.iterator();
        while (i$.hasNext()) {
            GrammarAST ref;
            CommonTree r = ref = i$.next();
            boolean ignore = false;
            while (r != null) {
                if (r instanceof PredAST) {
                    ignore = true;
                    break;
                }
                r = r.parent;
            }
            if (ignore) continue;
            result.add(ref);
        }
        return result;
    }

    public static String getLabelName(Grammar g, GrammarAST t) {
        String tokenName;
        String tokenText = t.getText();
        String string = tokenName = t.getType() != 62 ? tokenText : g.getTokenName(tokenText);
        if (tokenName == null || tokenName.startsWith("T__")) {
            return null;
        }
        String labelName = tokenName;
        Rule referencedRule = (Rule)g.rules.get(labelName);
        if (referencedRule != null) {
            labelName = referencedRule.getBaseContext();
        }
        return labelName;
    }

    protected Tuple2<FrequencySet<String>, FrequencySet<String>> getElementFrequenciesForAlt(AltAST ast) {
        try {
            ElementFrequenciesVisitor visitor = new ElementFrequenciesVisitor(this.rule.g, new CommonTreeNodeStream(new GrammarASTAdaptor(), ast));
            visitor.outerAlternative();
            if (visitor.frequencies.size() != 1) {
                this.factory.getGrammar().tool.errMgr.toolError(ErrorType.INTERNAL_ERROR, new Object[0]);
                return Tuple.create(new FrequencySet(), new FrequencySet());
            }
            return Tuple.create(visitor.getMinFrequencies(), visitor.frequencies.peek());
        }
        catch (RecognitionException ex) {
            this.factory.getGrammar().tool.errMgr.toolError(ErrorType.INTERNAL_ERROR, ex, new Object[0]);
            return Tuple.create(new FrequencySet(), new FrequencySet());
        }
    }

    public List<Decl> getDeclForAltElement(GrammarAST t, String refLabelName, boolean needList, boolean optional) {
        int lfIndex = refLabelName.indexOf(36);
        if (lfIndex >= 0) {
            refLabelName = refLabelName.substring(0, lfIndex);
        }
        ArrayList<Decl> decls = new ArrayList<Decl>();
        if (t.getType() == 57) {
            Rule rref = this.factory.getGrammar().getRule(t.getText());
            String ctxName = this.factory.getTarget().getRuleFunctionContextStructName(rref);
            if (needList) {
                if (this.factory.getTarget().supportsOverloadedMethods()) {
                    decls.add(new ContextRuleListGetterDecl(this.factory, refLabelName, ctxName));
                }
                decls.add(new ContextRuleListIndexedGetterDecl(this.factory, refLabelName, ctxName));
            } else {
                decls.add(new ContextRuleGetterDecl(this.factory, refLabelName, ctxName, optional));
            }
        } else if (needList) {
            if (this.factory.getTarget().supportsOverloadedMethods()) {
                decls.add(new ContextTokenListGetterDecl(this.factory, refLabelName));
            }
            decls.add(new ContextTokenListIndexedGetterDecl(this.factory, refLabelName));
        } else {
            decls.add(new ContextTokenGetterDecl(this.factory, refLabelName, optional));
        }
        return decls;
    }

    public void addLocalDecl(Decl d) {
        if (this.locals == null) {
            this.locals = new OrderedHashSet();
        }
        this.locals.add(d);
        d.isLocal = true;
    }

    public void addContextDecl(String altLabel, Decl d) {
        AltLabelStructDecl altCtx;
        CodeBlockForOuterMostAlt alt = d.getOuterMostAltCodeBlock();
        if (alt != null && (altCtx = this.getEffectiveAltLabelContexts(this.factory.getController()).get(altLabel)) != null) {
            altCtx.addDecl(d);
            return;
        }
        this.getEffectiveRuleContext(this.factory.getController()).addDecl(d);
    }
}

