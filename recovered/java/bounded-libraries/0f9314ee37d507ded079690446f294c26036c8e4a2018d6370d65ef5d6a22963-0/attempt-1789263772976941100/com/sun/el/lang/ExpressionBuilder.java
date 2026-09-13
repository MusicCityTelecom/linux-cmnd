/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.el.ELContext
 *  javax.el.ELException
 *  javax.el.FunctionMapper
 *  javax.el.MethodExpression
 *  javax.el.ValueExpression
 *  javax.el.VariableMapper
 */
package com.sun.el.lang;

import com.sun.el.MethodExpressionImpl;
import com.sun.el.MethodExpressionLiteral;
import com.sun.el.ValueExpressionImpl;
import com.sun.el.lang.FunctionMapperFactory;
import com.sun.el.lang.VariableMapperFactory;
import com.sun.el.parser.AstCompositeExpression;
import com.sun.el.parser.AstDeferredExpression;
import com.sun.el.parser.AstDynamicExpression;
import com.sun.el.parser.AstFunction;
import com.sun.el.parser.AstIdentifier;
import com.sun.el.parser.AstLiteralExpression;
import com.sun.el.parser.AstValue;
import com.sun.el.parser.ELParser;
import com.sun.el.parser.Node;
import com.sun.el.parser.NodeVisitor;
import com.sun.el.parser.ParseException;
import com.sun.el.util.MessageFactory;
import java.io.StringReader;
import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.FunctionMapper;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.el.VariableMapper;

public final class ExpressionBuilder
implements NodeVisitor {
    private static final int CACHE_INIT_SIZE = 256;
    private static final ConcurrentHashMap cache = new ConcurrentHashMap(256){

        public Object put(Object key, Object value) {
            SoftReference<Object> ref = new SoftReference<Object>(value);
            SoftReference<Object> prev = super.put(key, ref);
            return prev == null ? null : prev.get();
        }

        public Object putIfAbsent(Object key, Object value) {
            SoftReference<Object> ref = new SoftReference<Object>(value);
            SoftReference<Object> prev = super.putIfAbsent(key, ref);
            return prev == null ? null : prev.get();
        }

        public Object get(Object key) {
            SoftReference ref = (SoftReference)super.get(key);
            if (ref != null && ref.get() == null) {
                this.remove(key);
            }
            return ref != null ? ref.get() : null;
        }
    };
    private FunctionMapper fnMapper;
    private VariableMapper varMapper;
    private String expression;

    public ExpressionBuilder(String expression, ELContext ctx) throws ELException {
        this.expression = expression;
        FunctionMapper ctxFn = ctx.getFunctionMapper();
        VariableMapper ctxVar = ctx.getVariableMapper();
        if (ctxFn != null) {
            this.fnMapper = new FunctionMapperFactory(ctxFn);
        }
        if (ctxVar != null) {
            this.varMapper = new VariableMapperFactory(ctxVar);
        }
    }

    public static final Node createNode(String expr) throws ELException {
        Node n = ExpressionBuilder.createNodeInternal(expr);
        return n;
    }

    private static final Node createNodeInternal(String expr) throws ELException {
        if (expr == null) {
            throw new ELException(MessageFactory.get("error.null"));
        }
        Node n = (Node)cache.get(expr);
        if (n == null) {
            try {
                n = new ELParser(new StringReader(expr)).CompositeExpression();
                if (n instanceof AstCompositeExpression) {
                    int numChildren = n.jjtGetNumChildren();
                    if (numChildren == 1) {
                        n = n.jjtGetChild(0);
                    } else {
                        Class<?> type = null;
                        Node child = null;
                        for (int i = 0; i < numChildren; ++i) {
                            child = n.jjtGetChild(i);
                            if (child instanceof AstLiteralExpression) continue;
                            if (type == null) {
                                type = child.getClass();
                                continue;
                            }
                            if (type.equals(child.getClass())) continue;
                            throw new ELException(MessageFactory.get("error.mixed", expr));
                        }
                    }
                }
                if (n instanceof AstDeferredExpression || n instanceof AstDynamicExpression) {
                    n = n.jjtGetChild(0);
                }
                cache.putIfAbsent(expr, n);
            }
            catch (ParseException pe) {
                throw new ELException("Error Parsing: " + expr, (Throwable)pe);
            }
        }
        return n;
    }

    private void prepare(Node node) throws ELException {
        node.accept(this);
        if (this.fnMapper instanceof FunctionMapperFactory) {
            this.fnMapper = ((FunctionMapperFactory)this.fnMapper).create();
        }
        if (this.varMapper instanceof VariableMapperFactory) {
            this.varMapper = ((VariableMapperFactory)this.varMapper).create();
        }
    }

    private Node build() throws ELException {
        Node n = ExpressionBuilder.createNodeInternal(this.expression);
        this.prepare(n);
        if (n instanceof AstDeferredExpression || n instanceof AstDynamicExpression) {
            n = n.jjtGetChild(0);
        }
        return n;
    }

    public void visit(Node node) throws ELException {
        if (node instanceof AstFunction) {
            AstFunction funcNode = (AstFunction)node;
            if (this.fnMapper == null) {
                throw new ELException(MessageFactory.get("error.fnMapper.null"));
            }
            Method m = this.fnMapper.resolveFunction(funcNode.getPrefix(), funcNode.getLocalName());
            if (m == null) {
                throw new ELException(MessageFactory.get("error.fnMapper.method", funcNode.getOutputName()));
            }
            int pcnt = m.getParameterTypes().length;
            if (node.jjtGetNumChildren() != pcnt) {
                throw new ELException(MessageFactory.get("error.fnMapper.paramcount", funcNode.getOutputName(), "" + pcnt, "" + node.jjtGetNumChildren()));
            }
        } else if (node instanceof AstIdentifier && this.varMapper != null) {
            String variable = ((AstIdentifier)node).getImage();
            this.varMapper.resolveVariable(variable);
        }
    }

    public ValueExpression createValueExpression(Class expectedType) throws ELException {
        Node n = this.build();
        return new ValueExpressionImpl(this.expression, n, this.fnMapper, this.varMapper, expectedType);
    }

    public MethodExpression createMethodExpression(Class expectedReturnType, Class[] expectedParamTypes) throws ELException {
        Node n = this.build();
        if (n instanceof AstValue || n instanceof AstIdentifier) {
            return new MethodExpressionImpl(this.expression, n, this.fnMapper, this.varMapper, expectedReturnType, expectedParamTypes);
        }
        if (n instanceof AstLiteralExpression) {
            return new MethodExpressionLiteral(this.expression, expectedReturnType, expectedParamTypes);
        }
        throw new ELException("Not a Valid Method Expression: " + this.expression);
    }
}

