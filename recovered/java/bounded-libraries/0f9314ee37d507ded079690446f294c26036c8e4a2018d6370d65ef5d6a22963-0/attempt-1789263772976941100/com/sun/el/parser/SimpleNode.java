/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.el.ELException
 *  javax.el.MethodInfo
 *  javax.el.PropertyNotWritableException
 *  javax.el.ValueReference
 */
package com.sun.el.parser;

import com.sun.el.lang.ELSupport;
import com.sun.el.lang.EvaluationContext;
import com.sun.el.parser.ELParserTreeConstants;
import com.sun.el.parser.Node;
import com.sun.el.parser.NodeVisitor;
import com.sun.el.util.MessageFactory;
import javax.el.ELException;
import javax.el.MethodInfo;
import javax.el.PropertyNotWritableException;
import javax.el.ValueReference;

public abstract class SimpleNode
extends ELSupport
implements Node {
    protected Node parent;
    protected Node[] children;
    protected int id;
    protected String image;

    public SimpleNode(int i) {
        this.id = i;
    }

    public void jjtOpen() {
    }

    public void jjtClose() {
    }

    public void jjtSetParent(Node n) {
        this.parent = n;
    }

    public Node jjtGetParent() {
        return this.parent;
    }

    public void jjtAddChild(Node n, int i) {
        if (this.children == null) {
            this.children = new Node[i + 1];
        } else if (i >= this.children.length) {
            Node[] c = new Node[i + 1];
            System.arraycopy(this.children, 0, c, 0, this.children.length);
            this.children = c;
        }
        this.children[i] = n;
    }

    public Node jjtGetChild(int i) {
        return this.children[i];
    }

    public int jjtGetNumChildren() {
        return this.children == null ? 0 : this.children.length;
    }

    public String toString() {
        if (this.image != null) {
            return ELParserTreeConstants.jjtNodeName[this.id] + "[" + this.image + "]";
        }
        return ELParserTreeConstants.jjtNodeName[this.id];
    }

    public String toString(String prefix) {
        return prefix + this.toString();
    }

    public void dump(String prefix) {
        System.out.println(this.toString(prefix));
        if (this.children != null) {
            for (int i = 0; i < this.children.length; ++i) {
                SimpleNode n = (SimpleNode)this.children[i];
                if (n == null) continue;
                n.dump(prefix + " ");
            }
        }
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Class getType(EvaluationContext ctx) throws ELException {
        throw new UnsupportedOperationException();
    }

    public Object getValue(EvaluationContext ctx) throws ELException {
        throw new UnsupportedOperationException();
    }

    public ValueReference getValueReference(EvaluationContext ctx) throws ELException {
        return null;
    }

    public boolean isReadOnly(EvaluationContext ctx) throws ELException {
        return true;
    }

    public void setValue(EvaluationContext ctx, Object value) throws ELException {
        throw new PropertyNotWritableException(MessageFactory.get("error.syntax.set"));
    }

    public void accept(NodeVisitor visitor) throws ELException {
        visitor.visit(this);
        if (this.children != null && this.children.length > 0) {
            for (int i = 0; i < this.children.length; ++i) {
                this.children[i].accept(visitor);
            }
        }
    }

    public Object invoke(EvaluationContext ctx, Class[] paramTypes, Object[] paramValues) throws ELException {
        throw new UnsupportedOperationException();
    }

    public MethodInfo getMethodInfo(EvaluationContext ctx, Class[] paramTypes) throws ELException {
        throw new UnsupportedOperationException();
    }

    public boolean equals(Object node) {
        if (!(node instanceof SimpleNode)) {
            return false;
        }
        SimpleNode n = (SimpleNode)node;
        if (this.id != n.id) {
            return false;
        }
        if (this.children == null && n.children == null) {
            if (this.image == null) {
                return n.image == null;
            }
            return this.image.equals(n.image);
        }
        if (this.children == null || n.children == null) {
            return false;
        }
        if (this.children.length != n.children.length) {
            return false;
        }
        if (this.children.length == 0) {
            if (this.image == null) {
                return n.image == null;
            }
            return this.image.equals(n.image);
        }
        for (int i = 0; i < this.children.length; ++i) {
            if (((Object)this.children[i]).equals(n.children[i])) continue;
            return false;
        }
        return true;
    }

    public boolean isParametersProvided() {
        return false;
    }

    public int hashCode() {
        if (this.children == null || this.children.length == 0) {
            if (this.image != null) {
                return this.image.hashCode();
            }
            return this.id;
        }
        int h = 0;
        for (int i = this.children.length - 1; i >= 0; --i) {
            h = h + h + h + ((Object)this.children[i]).hashCode();
        }
        h = h + h + h + this.id;
        return h;
    }
}

