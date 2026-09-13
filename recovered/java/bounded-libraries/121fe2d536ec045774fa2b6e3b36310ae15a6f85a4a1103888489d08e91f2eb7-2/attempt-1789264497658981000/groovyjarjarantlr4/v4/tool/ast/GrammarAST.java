/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.tool.ast;

import groovyjarjarantlr4.runtime.BitSet;
import groovyjarjarantlr4.runtime.CharStream;
import groovyjarjarantlr4.runtime.CommonToken;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.runtime.tree.CommonTree;
import groovyjarjarantlr4.runtime.tree.CommonTreeNodeStream;
import groovyjarjarantlr4.runtime.tree.Tree;
import groovyjarjarantlr4.v4.parse.ANTLRParser;
import groovyjarjarantlr4.v4.parse.GrammarASTAdaptor;
import groovyjarjarantlr4.v4.runtime.atn.ATNState;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.ast.AltAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTVisitor;
import groovyjarjarantlr4.v4.tool.ast.RuleAST;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GrammarAST
extends CommonTree {
    public Grammar g;
    public ATNState atnState;
    public String textOverride;

    public GrammarAST() {
    }

    public GrammarAST(Token t) {
        super(t);
    }

    public GrammarAST(GrammarAST node) {
        super(node);
        this.g = node.g;
        this.atnState = node.atnState;
        this.textOverride = node.textOverride;
    }

    public GrammarAST(int type) {
        super(new CommonToken(type, ANTLRParser.tokenNames[type]));
    }

    public GrammarAST(int type, Token t) {
        this(new CommonToken(t));
        this.token.setType(type);
    }

    public GrammarAST(int type, Token t, String text) {
        this(new CommonToken(t));
        this.token.setType(type);
        this.token.setText(text);
    }

    public GrammarAST[] getChildrenAsArray() {
        return this.children.toArray(new GrammarAST[this.children.size()]);
    }

    public List<GrammarAST> getNodesWithType(int ttype) {
        return this.getNodesWithType(IntervalSet.of(ttype));
    }

    public List<GrammarAST> getAllChildrenWithType(int type) {
        ArrayList<GrammarAST> nodes = new ArrayList<GrammarAST>();
        for (int i = 0; this.children != null && i < this.children.size(); ++i) {
            Tree t = (Tree)this.children.get(i);
            if (t.getType() != type) continue;
            nodes.add((GrammarAST)t);
        }
        return nodes;
    }

    public List<GrammarAST> getNodesWithType(IntervalSet types) {
        ArrayList<GrammarAST> nodes = new ArrayList<GrammarAST>();
        LinkedList<GrammarAST> work = new LinkedList<GrammarAST>();
        work.add(this);
        while (!work.isEmpty()) {
            GrammarAST t = (GrammarAST)work.remove(0);
            if (types == null || types.contains(t.getType())) {
                nodes.add(t);
            }
            if (t.children == null) continue;
            work.addAll(Arrays.asList(t.getChildrenAsArray()));
        }
        return nodes;
    }

    public List<GrammarAST> getNodesWithTypePreorderDFS(IntervalSet types) {
        ArrayList<GrammarAST> nodes = new ArrayList<GrammarAST>();
        this.getNodesWithTypePreorderDFS_(nodes, types);
        return nodes;
    }

    public void getNodesWithTypePreorderDFS_(List<GrammarAST> nodes, IntervalSet types) {
        if (types.contains(this.getType())) {
            nodes.add(this);
        }
        for (int i = 0; i < this.getChildCount(); ++i) {
            GrammarAST child = (GrammarAST)this.getChild(i);
            child.getNodesWithTypePreorderDFS_(nodes, types);
        }
    }

    public GrammarAST getNodeWithTokenIndex(int index) {
        if (this.getToken() != null && this.getToken().getTokenIndex() == index) {
            return this;
        }
        for (int i = 0; i < this.getChildCount(); ++i) {
            GrammarAST child = (GrammarAST)this.getChild(i);
            GrammarAST result = child.getNodeWithTokenIndex(index);
            if (result == null) continue;
            return result;
        }
        return null;
    }

    public AltAST getOutermostAltNode() {
        if (this instanceof AltAST && this.parent.parent instanceof RuleAST) {
            return (AltAST)this;
        }
        if (this.parent != null) {
            return ((GrammarAST)this.parent).getOutermostAltNode();
        }
        return null;
    }

    public String getAltLabel() {
        List<? extends Tree> ancestors = this.getAncestors();
        if (ancestors == null) {
            return null;
        }
        for (int i = ancestors.size() - 1; i >= 0; --i) {
            GrammarAST p = (GrammarAST)ancestors.get(i);
            if (p.getType() != 74) continue;
            AltAST a = (AltAST)p;
            if (a.altLabel != null) {
                return a.altLabel.getText();
            }
            if (a.leftRecursiveAltInfo == null) continue;
            return a.leftRecursiveAltInfo.altLabel;
        }
        return null;
    }

    public boolean deleteChild(Tree t) {
        for (int i = 0; i < this.children.size(); ++i) {
            Object c = this.children.get(i);
            if (c != t) continue;
            this.deleteChild(t.getChildIndex());
            return true;
        }
        return false;
    }

    public CommonTree getFirstDescendantWithType(int type) {
        if (this.getType() == type) {
            return this;
        }
        if (this.children == null) {
            return null;
        }
        for (Object c : this.children) {
            GrammarAST t = (GrammarAST)c;
            if (t.getType() == type) {
                return t;
            }
            CommonTree d = t.getFirstDescendantWithType(type);
            if (d == null) continue;
            return d;
        }
        return null;
    }

    public CommonTree getFirstDescendantWithType(BitSet types) {
        if (types.member(this.getType())) {
            return this;
        }
        if (this.children == null) {
            return null;
        }
        for (Object c : this.children) {
            GrammarAST t = (GrammarAST)c;
            if (types.member(t.getType())) {
                return t;
            }
            CommonTree d = t.getFirstDescendantWithType(types);
            if (d == null) continue;
            return d;
        }
        return null;
    }

    public void setType(int type) {
        this.token.setType(type);
    }

    public void setText(String text) {
        this.token.setText(text);
    }

    @Override
    public GrammarAST dupNode() {
        return new GrammarAST(this);
    }

    public GrammarAST dupTree() {
        GrammarAST t = this;
        CharStream input = this.token.getInputStream();
        GrammarASTAdaptor adaptor = new GrammarASTAdaptor(input);
        return (GrammarAST)adaptor.dupTree(t);
    }

    public String toTokenString() {
        CharStream input = this.token.getInputStream();
        GrammarASTAdaptor adaptor = new GrammarASTAdaptor(input);
        CommonTreeNodeStream nodes = new CommonTreeNodeStream(adaptor, this);
        StringBuilder buf = new StringBuilder();
        GrammarAST o = (GrammarAST)nodes.LT(1);
        int type = adaptor.getType(o);
        while (type != -1) {
            buf.append(" ");
            buf.append(o.getText());
            nodes.consume();
            o = (GrammarAST)nodes.LT(1);
            type = adaptor.getType(o);
        }
        return buf.toString();
    }

    public Object visit(GrammarASTVisitor v) {
        return v.visit(this);
    }
}

