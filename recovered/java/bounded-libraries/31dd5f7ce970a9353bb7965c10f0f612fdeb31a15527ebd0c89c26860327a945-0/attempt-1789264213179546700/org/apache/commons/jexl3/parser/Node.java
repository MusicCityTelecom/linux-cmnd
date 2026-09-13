/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import java.io.Serializable;
import org.apache.commons.jexl3.parser.ParserVisitor;

public interface Node
extends Serializable {
    public void jjtOpen();

    public void jjtClose();

    public void jjtSetParent(Node var1);

    public Node jjtGetParent();

    public void jjtAddChild(Node var1, int var2);

    public Node jjtGetChild(int var1);

    public int jjtGetNumChildren();

    public int getId();

    public Object jjtAccept(ParserVisitor var1, Object var2);
}

