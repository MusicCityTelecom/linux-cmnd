/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime.tree;

import groovyjarjarantlr4.runtime.CommonToken;
import groovyjarjarantlr4.runtime.tree.TreeAdaptor;
import groovyjarjarantlr4.runtime.tree.TreePatternLexer;
import groovyjarjarantlr4.runtime.tree.TreeWizard;

public class TreePatternParser {
    protected TreePatternLexer tokenizer;
    protected int ttype;
    protected TreeWizard wizard;
    protected TreeAdaptor adaptor;

    public TreePatternParser(TreePatternLexer tokenizer, TreeWizard wizard, TreeAdaptor adaptor) {
        this.tokenizer = tokenizer;
        this.wizard = wizard;
        this.adaptor = adaptor;
        this.ttype = tokenizer.nextToken();
    }

    public Object pattern() {
        if (this.ttype == 1) {
            return this.parseTree();
        }
        if (this.ttype == 3) {
            Object node = this.parseNode();
            if (this.ttype == -1) {
                return node;
            }
            return null;
        }
        return null;
    }

    public Object parseTree() {
        if (this.ttype != 1) {
            throw new RuntimeException("no BEGIN");
        }
        this.ttype = this.tokenizer.nextToken();
        Object root = this.parseNode();
        if (root == null) {
            return null;
        }
        while (this.ttype == 1 || this.ttype == 3 || this.ttype == 5 || this.ttype == 7) {
            if (this.ttype == 1) {
                Object subtree = this.parseTree();
                this.adaptor.addChild(root, subtree);
                continue;
            }
            Object child = this.parseNode();
            if (child == null) {
                return null;
            }
            this.adaptor.addChild(root, child);
        }
        if (this.ttype != 2) {
            throw new RuntimeException("no END");
        }
        this.ttype = this.tokenizer.nextToken();
        return root;
    }

    public Object parseNode() {
        int treeNodeType;
        String label = null;
        if (this.ttype == 5) {
            this.ttype = this.tokenizer.nextToken();
            if (this.ttype != 3) {
                return null;
            }
            label = this.tokenizer.sval.toString();
            this.ttype = this.tokenizer.nextToken();
            if (this.ttype != 6) {
                return null;
            }
            this.ttype = this.tokenizer.nextToken();
        }
        if (this.ttype == 7) {
            this.ttype = this.tokenizer.nextToken();
            CommonToken wildcardPayload = new CommonToken(0, ".");
            TreeWizard.WildcardTreePattern node = new TreeWizard.WildcardTreePattern(wildcardPayload);
            if (label != null) {
                node.label = label;
            }
            return node;
        }
        if (this.ttype != 3) {
            return null;
        }
        String tokenName = this.tokenizer.sval.toString();
        this.ttype = this.tokenizer.nextToken();
        if (tokenName.equals("nil")) {
            return this.adaptor.nil();
        }
        String text = tokenName;
        String arg = null;
        if (this.ttype == 4) {
            text = arg = this.tokenizer.sval.toString();
            this.ttype = this.tokenizer.nextToken();
        }
        if ((treeNodeType = this.wizard.getTokenType(tokenName)) == 0) {
            return null;
        }
        Object node = this.adaptor.create(treeNodeType, text);
        if (label != null && node.getClass() == TreeWizard.TreePattern.class) {
            ((TreeWizard.TreePattern)node).label = label;
        }
        if (arg != null && node.getClass() == TreeWizard.TreePattern.class) {
            ((TreeWizard.TreePattern)node).hasTextArg = true;
        }
        return node;
    }
}

