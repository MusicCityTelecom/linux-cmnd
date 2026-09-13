/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import org.apache.commons.jexl3.JexlInfo;
import org.apache.commons.jexl3.introspection.JexlMethod;
import org.apache.commons.jexl3.introspection.JexlPropertyGet;
import org.apache.commons.jexl3.introspection.JexlPropertySet;
import org.apache.commons.jexl3.parser.ASTArrayAccess;
import org.apache.commons.jexl3.parser.ASTFunctionNode;
import org.apache.commons.jexl3.parser.ASTIdentifier;
import org.apache.commons.jexl3.parser.ASTIdentifierAccess;
import org.apache.commons.jexl3.parser.ASTMapEntry;
import org.apache.commons.jexl3.parser.ASTMethodNode;
import org.apache.commons.jexl3.parser.ASTNullpNode;
import org.apache.commons.jexl3.parser.ASTReference;
import org.apache.commons.jexl3.parser.ASTTernaryNode;
import org.apache.commons.jexl3.parser.ASTVar;
import org.apache.commons.jexl3.parser.OperatorController;
import org.apache.commons.jexl3.parser.Parser;
import org.apache.commons.jexl3.parser.SimpleNode;
import org.apache.commons.jexl3.parser.Token;

public abstract class JexlNode
extends SimpleNode {
    private int lc = -1;

    public JexlNode(int id) {
        super(id);
    }

    public JexlNode(Parser p, int id) {
        super(p, id);
    }

    public void jjtSetFirstToken(Token t) {
        this.lc = t.beginLine << 12 | 0xFFF & t.beginColumn;
    }

    public void jjtSetLastToken(Token t) {
    }

    public int getLine() {
        return this.lc >>> 12;
    }

    public int getColumn() {
        return this.lc & 0xFFF;
    }

    public JexlInfo jexlInfo() {
        JexlInfo info = null;
        for (JexlNode node = this; node != null; node = node.jjtGetParent()) {
            if (!(node.jjtGetValue() instanceof JexlInfo)) continue;
            info = (JexlInfo)node.jjtGetValue();
            break;
        }
        if (this.lc >= 0) {
            int c = this.lc & 0xFFF;
            int l = this.lc >> 12;
            return info != null ? info.at(info.getLine() + l - 1, c) : new JexlInfo(null, l, c);
        }
        return info;
    }

    public void clearCache() {
        Object value = this.jjtGetValue();
        if (value instanceof JexlPropertyGet || value instanceof JexlPropertySet || value instanceof JexlMethod || value instanceof Funcall) {
            this.jjtSetValue(null);
        }
        for (int n = 0; n < this.jjtGetNumChildren(); ++n) {
            this.jjtGetChild(n).clearCache();
        }
    }

    public boolean isOperator() {
        return OperatorController.INSTANCE.control(this, Boolean.TRUE);
    }

    public boolean isStrictOperator() {
        return OperatorController.INSTANCE.control(this, Boolean.FALSE);
    }

    public boolean isConstant() {
        return this.isConstant(this instanceof Constant);
    }

    protected boolean isConstant(boolean literal) {
        if (literal) {
            for (int n = 0; n < this.jjtGetNumChildren(); ++n) {
                boolean is;
                JexlNode child = this.jjtGetChild(n);
                if (!(child instanceof ASTReference ? !(is = child.isConstant(true)) : (child instanceof ASTMapEntry ? !(is = child.isConstant(true)) : !child.isConstant()))) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean isLeftValue() {
        int nc;
        JexlNode walk = this;
        do {
            if (walk instanceof ASTIdentifier || walk instanceof ASTIdentifierAccess || walk instanceof ASTArrayAccess) {
                return true;
            }
            nc = walk.jjtGetNumChildren() - 1;
            if (nc >= 0) continue;
            return walk.jjtGetParent() instanceof ASTReference;
        } while ((walk = walk.jjtGetChild(nc)) != null);
        return false;
    }

    public boolean isGlobalVar() {
        if (this instanceof ASTVar) {
            return false;
        }
        if (this instanceof ASTIdentifier) {
            return ((ASTIdentifier)this).getSymbol() < 0;
        }
        int nc = this.jjtGetNumChildren() - 1;
        if (nc >= 0) {
            JexlNode first = this.jjtGetChild(0);
            return first.isGlobalVar();
        }
        return this.jjtGetParent() instanceof ASTReference;
    }

    public boolean isLocalVar() {
        return this instanceof ASTIdentifier && ((ASTIdentifier)this).getSymbol() >= 0;
    }

    public boolean isSafeLhs(boolean safe) {
        if (this instanceof ASTReference) {
            return this.jjtGetChild(0).isSafeLhs(safe);
        }
        if (this instanceof ASTMethodNode && this.jjtGetNumChildren() > 1 && this.jjtGetChild(0) instanceof ASTIdentifierAccess && (((ASTIdentifierAccess)this.jjtGetChild(0)).isSafe() || safe)) {
            return true;
        }
        JexlNode parent = this.jjtGetParent();
        if (parent == null) {
            return false;
        }
        int nsiblings = parent.jjtGetNumChildren();
        int rhs = -1;
        for (int s = 0; s < nsiblings; ++s) {
            JexlNode sibling = parent.jjtGetChild(s);
            if (sibling != this) continue;
            rhs = s + 1;
            break;
        }
        if (rhs >= 0 && rhs < nsiblings) {
            JexlNode rsibling = parent.jjtGetChild(rhs);
            if (rsibling instanceof ASTMethodNode || rsibling instanceof ASTFunctionNode) {
                rsibling = rsibling.jjtGetChild(0);
            }
            if (rsibling instanceof ASTIdentifierAccess && (((ASTIdentifierAccess)rsibling).isSafe() || safe)) {
                return true;
            }
            if (rsibling instanceof ASTArrayAccess) {
                return safe;
            }
        }
        return false;
    }

    public boolean isTernaryProtected() {
        JexlNode node = this;
        for (JexlNode walk = node.jjtGetParent(); walk != null; walk = walk.jjtGetParent()) {
            if (walk instanceof ASTTernaryNode || walk instanceof ASTNullpNode) {
                return node == walk.jjtGetChild(0);
            }
            if (!(walk instanceof ASTReference) && !(walk instanceof ASTArrayAccess)) break;
            node = walk;
        }
        return false;
    }

    public static class Info
    extends JexlInfo {
        JexlNode node = null;

        public Info(JexlNode jnode) {
            this(jnode, jnode.jexlInfo());
        }

        public Info(JexlNode jnode, JexlInfo info) {
            this(jnode, info.getName(), info.getLine(), info.getColumn());
        }

        private Info(JexlNode jnode, String name, int l, int c) {
            super(name, l, c);
            this.node = jnode;
        }

        public JexlNode getNode() {
            return this.node;
        }

        @Override
        public JexlInfo at(int l, int c) {
            return new Info(this.node, this.getName(), l, c);
        }

        @Override
        public JexlInfo detach() {
            this.node = null;
            return this;
        }
    }

    public static interface Funcall {
    }

    public static interface Constant<T> {
        public T getLiteral();
    }
}

