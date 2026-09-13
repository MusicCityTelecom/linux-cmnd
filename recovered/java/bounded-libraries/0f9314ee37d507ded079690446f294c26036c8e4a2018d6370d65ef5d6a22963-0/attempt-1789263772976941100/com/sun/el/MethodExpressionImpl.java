/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.el.ELContext
 *  javax.el.ELException
 *  javax.el.FunctionMapper
 *  javax.el.MethodExpression
 *  javax.el.MethodInfo
 *  javax.el.MethodNotFoundException
 *  javax.el.PropertyNotFoundException
 *  javax.el.VariableMapper
 */
package com.sun.el;

import com.sun.el.lang.EvaluationContext;
import com.sun.el.lang.ExpressionBuilder;
import com.sun.el.parser.Node;
import com.sun.el.util.ReflectionUtil;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.FunctionMapper;
import javax.el.MethodExpression;
import javax.el.MethodInfo;
import javax.el.MethodNotFoundException;
import javax.el.PropertyNotFoundException;
import javax.el.VariableMapper;

public final class MethodExpressionImpl
extends MethodExpression
implements Externalizable {
    private Class expectedType;
    private String expr;
    private FunctionMapper fnMapper;
    private VariableMapper varMapper;
    private transient Node node;
    private Class[] paramTypes;

    public MethodExpressionImpl() {
    }

    public MethodExpressionImpl(String expr, Node node, FunctionMapper fnMapper, VariableMapper varMapper, Class expectedType, Class[] paramTypes) {
        this.expr = expr;
        this.node = node;
        this.fnMapper = fnMapper;
        this.varMapper = varMapper;
        this.expectedType = expectedType;
        this.paramTypes = paramTypes;
    }

    public boolean equals(Object obj) {
        if (obj instanceof MethodExpressionImpl) {
            MethodExpressionImpl me = (MethodExpressionImpl)obj;
            return ((Object)this.getNode()).equals(me.getNode());
        }
        return false;
    }

    public String getExpressionString() {
        return this.expr;
    }

    public MethodInfo getMethodInfo(ELContext context) throws PropertyNotFoundException, MethodNotFoundException, ELException {
        Node n = this.getNode();
        EvaluationContext ctx = new EvaluationContext(context, this.fnMapper, this.varMapper);
        return n.getMethodInfo(ctx, this.paramTypes);
    }

    private Node getNode() throws ELException {
        if (this.node == null) {
            this.node = ExpressionBuilder.createNode(this.expr);
        }
        return this.node;
    }

    public int hashCode() {
        return ((Object)this.getNode()).hashCode();
    }

    public Object invoke(ELContext context, Object[] params) throws PropertyNotFoundException, MethodNotFoundException, ELException {
        EvaluationContext ctx = new EvaluationContext(context, this.fnMapper, this.varMapper);
        return this.getNode().invoke(ctx, this.paramTypes, params);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.expr = in.readUTF();
        String type = in.readUTF();
        if (!"".equals(type)) {
            this.expectedType = ReflectionUtil.forName(type);
        }
        this.paramTypes = ReflectionUtil.toTypeArray((String[])in.readObject());
        this.fnMapper = (FunctionMapper)in.readObject();
        this.varMapper = (VariableMapper)in.readObject();
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(this.expr);
        out.writeUTF(this.expectedType != null ? this.expectedType.getName() : "");
        out.writeObject(ReflectionUtil.toTypeNameArray(this.paramTypes));
        out.writeObject(this.fnMapper);
        out.writeObject(this.varMapper);
    }

    public boolean isLiteralText() {
        return false;
    }
}

