/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import org.apache.commons.jexl3.parser.JexlNode;
import org.apache.commons.jexl3.parser.Node;
import org.apache.commons.jexl3.parser.Parser;
import org.apache.commons.jexl3.parser.ParserTreeConstants;
import org.apache.commons.jexl3.parser.ParserVisitor;

public class SimpleNode
implements Node {
    private JexlNode parent;
    private JexlNode[] children;
    protected final int id;
    private volatile Object value;

    public SimpleNode(int i) {
        this.id = i;
    }

    public SimpleNode(Parser p, int i) {
        this(i);
    }

    @Override
    public void jjtOpen() {
    }

    @Override
    public void jjtClose() {
    }

    @Override
    public void jjtSetParent(Node n) {
        this.parent = (JexlNode)n;
    }

    @Override
    public JexlNode jjtGetParent() {
        return this.parent;
    }

    @Override
    public void jjtAddChild(Node n, int i) {
        if (this.children == null) {
            this.children = new JexlNode[i + 1];
        } else if (i >= this.children.length) {
            JexlNode[] c = new JexlNode[i + 1];
            System.arraycopy(this.children, 0, c, 0, this.children.length);
            this.children = c;
        }
        this.children[i] = (JexlNode)n;
    }

    void jjtSetChildren(JexlNode[] jexlNodes) {
        this.children = jexlNodes;
    }

    @Override
    public JexlNode jjtGetChild(int i) {
        return this.children[i];
    }

    @Override
    public int jjtGetNumChildren() {
        return this.children == null ? 0 : this.children.length;
    }

    public void jjtSetValue(Object value) {
        this.value = value;
    }

    public Object jjtGetValue() {
        return this.value;
    }

    @Override
    public Object jjtAccept(ParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

    public Object childrenAccept(ParserVisitor visitor, Object data) {
        if (this.children != null) {
            for (JexlNode child : this.children) {
                child.jjtAccept(visitor, data);
            }
        }
        return data;
    }

    public String toString() {
        return ParserTreeConstants.jjtNodeName[this.id];
    }

    public String toString(String prefix) {
        return prefix + this.toString();
    }

    public void dump(String prefix) {
        System.out.println(this.toString(prefix));
        if (this.children != null) {
            for (JexlNode n : this.children) {
                if (n == null) continue;
                n.dump(prefix + " ");
            }
        }
    }

    @Override
    public int getId() {
        return this.id;
    }
}

