/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.el.ELException
 *  javax.el.MethodInfo
 *  javax.el.ValueReference
 */
package com.sun.el.parser;

import com.sun.el.lang.EvaluationContext;
import com.sun.el.parser.NodeVisitor;
import javax.el.ELException;
import javax.el.MethodInfo;
import javax.el.ValueReference;

public interface Node {
    public void jjtOpen();

    public void jjtClose();

    public void jjtSetParent(Node var1);

    public Node jjtGetParent();

    public void jjtAddChild(Node var1, int var2);

    public Node jjtGetChild(int var1);

    public int jjtGetNumChildren();

    public String getImage();

    public Object getValue(EvaluationContext var1) throws ELException;

    public void setValue(EvaluationContext var1, Object var2) throws ELException;

    public Class getType(EvaluationContext var1) throws ELException;

    public ValueReference getValueReference(EvaluationContext var1) throws ELException;

    public boolean isReadOnly(EvaluationContext var1) throws ELException;

    public void accept(NodeVisitor var1) throws ELException;

    public MethodInfo getMethodInfo(EvaluationContext var1, Class[] var2) throws ELException;

    public Object invoke(EvaluationContext var1, Class[] var2, Object[] var3) throws ELException;

    public boolean equals(Object var1);

    public int hashCode();

    public boolean isParametersProvided();
}

