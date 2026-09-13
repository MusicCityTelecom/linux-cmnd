/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.el.ELContext
 *  javax.el.ELException
 *  javax.el.FunctionMapper
 *  javax.el.PropertyNotFoundException
 *  javax.el.PropertyNotWritableException
 *  javax.el.ValueExpression
 *  javax.el.ValueReference
 *  javax.el.VariableMapper
 */
package com.sun.el;

import com.sun.el.lang.ELSupport;
import com.sun.el.lang.EvaluationContext;
import com.sun.el.lang.ExpressionBuilder;
import com.sun.el.parser.AstLiteralExpression;
import com.sun.el.parser.Node;
import com.sun.el.util.ReflectionUtil;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.FunctionMapper;
import javax.el.PropertyNotFoundException;
import javax.el.PropertyNotWritableException;
import javax.el.ValueExpression;
import javax.el.ValueReference;
import javax.el.VariableMapper;

public final class ValueExpressionImpl
extends ValueExpression
implements Externalizable {
    private Class expectedType;
    private String expr;
    private FunctionMapper fnMapper;
    private VariableMapper varMapper;
    private transient Node node;

    public ValueExpressionImpl() {
    }

    public ValueExpressionImpl(String expr, Node node, FunctionMapper fnMapper, VariableMapper varMapper, Class expectedType) {
        this.expr = expr;
        this.node = node;
        this.fnMapper = fnMapper;
        this.varMapper = varMapper;
        this.expectedType = expectedType;
    }

    public boolean equals(Object obj) {
        if (obj instanceof ValueExpressionImpl) {
            ValueExpressionImpl v = (ValueExpressionImpl)obj;
            return ((Object)this.getNode()).equals(v.getNode());
        }
        return false;
    }

    public Class getExpectedType() {
        return this.expectedType;
    }

    public String getExpressionString() {
        return this.expr;
    }

    private Node getNode() throws ELException {
        if (this.node == null) {
            this.node = ExpressionBuilder.createNode(this.expr);
        }
        return this.node;
    }

    public Class getType(ELContext context) throws PropertyNotFoundException, ELException {
        EvaluationContext ctx = new EvaluationContext(context, this.fnMapper, this.varMapper);
        return this.getNode().getType(ctx);
    }

    public ValueReference getValueReference(ELContext context) throws PropertyNotFoundException, ELException {
        EvaluationContext ctx = new EvaluationContext(context, this.fnMapper, this.varMapper);
        return this.getNode().getValueReference(ctx);
    }

    public Object getValue(ELContext context) throws PropertyNotFoundException, ELException {
        EvaluationContext ctx = new EvaluationContext(context, this.fnMapper, this.varMapper);
        Object value = this.getNode().getValue(ctx);
        if (this.expectedType != null) {
            try {
                value = ELSupport.coerceToType(value, this.expectedType);
            }
            catch (IllegalArgumentException ex) {
                throw new ELException((Throwable)ex);
            }
        }
        return value;
    }

    public int hashCode() {
        return ((Object)this.getNode()).hashCode();
    }

    public boolean isLiteralText() {
        try {
            return this.getNode() instanceof AstLiteralExpression;
        }
        catch (ELException ele) {
            return false;
        }
    }

    public boolean isReadOnly(ELContext context) throws PropertyNotFoundException, ELException {
        EvaluationContext ctx = new EvaluationContext(context, this.fnMapper, this.varMapper);
        return this.getNode().isReadOnly(ctx);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.expr = in.readUTF();
        String type = in.readUTF();
        if (!"".equals(type)) {
            this.expectedType = ReflectionUtil.forName(type);
        }
        this.fnMapper = (FunctionMapper)in.readObject();
        this.varMapper = (VariableMapper)in.readObject();
    }

    public void setValue(ELContext context, Object value) throws PropertyNotFoundException, PropertyNotWritableException, ELException {
        EvaluationContext ctx = new EvaluationContext(context, this.fnMapper, this.varMapper);
        this.getNode().setValue(ctx, value);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(this.expr);
        out.writeUTF(this.expectedType != null ? this.expectedType.getName() : "");
        out.writeObject(this.fnMapper);
        out.writeObject(this.varMapper);
    }

    public String toString() {
        return "ValueExpression[" + this.expr + "]";
    }
}

