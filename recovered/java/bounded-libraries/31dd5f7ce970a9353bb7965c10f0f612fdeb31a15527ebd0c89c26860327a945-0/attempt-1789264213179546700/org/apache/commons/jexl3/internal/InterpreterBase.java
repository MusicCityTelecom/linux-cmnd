/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 */
package org.apache.commons.jexl3.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.jexl3.JexlArithmetic;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.JexlOperator;
import org.apache.commons.jexl3.JexlOptions;
import org.apache.commons.jexl3.internal.Debugger;
import org.apache.commons.jexl3.internal.Engine;
import org.apache.commons.jexl3.internal.Frame;
import org.apache.commons.jexl3.internal.LexicalFrame;
import org.apache.commons.jexl3.internal.LexicalScope;
import org.apache.commons.jexl3.internal.Operators;
import org.apache.commons.jexl3.internal.Scope;
import org.apache.commons.jexl3.introspection.JexlMethod;
import org.apache.commons.jexl3.introspection.JexlPropertyGet;
import org.apache.commons.jexl3.introspection.JexlPropertySet;
import org.apache.commons.jexl3.introspection.JexlUberspect;
import org.apache.commons.jexl3.parser.ASTArrayAccess;
import org.apache.commons.jexl3.parser.ASTAssignment;
import org.apache.commons.jexl3.parser.ASTFunctionNode;
import org.apache.commons.jexl3.parser.ASTIdentifier;
import org.apache.commons.jexl3.parser.ASTIdentifierAccess;
import org.apache.commons.jexl3.parser.ASTMethodNode;
import org.apache.commons.jexl3.parser.ASTReference;
import org.apache.commons.jexl3.parser.ASTVar;
import org.apache.commons.jexl3.parser.JexlNode;
import org.apache.commons.jexl3.parser.ParserVisitor;
import org.apache.commons.logging.Log;

public abstract class InterpreterBase
extends ParserVisitor {
    protected final Engine jexl;
    protected final Log logger;
    protected final JexlUberspect uberspect;
    protected final JexlArithmetic arithmetic;
    protected final JexlContext context;
    protected final JexlOptions options;
    protected final boolean cache;
    protected final AtomicBoolean cancelled;
    protected static final Object[] EMPTY_PARAMS = new Object[0];
    protected final JexlContext.NamespaceResolver ns;
    protected final Operators operators;
    protected final Map<String, Object> functions;
    protected Map<String, Object> functors;

    protected InterpreterBase(Engine engine, JexlOptions opts, JexlContext aContext) {
        this.jexl = engine;
        this.logger = this.jexl.logger;
        this.uberspect = this.jexl.uberspect;
        this.context = aContext != null ? aContext : Engine.EMPTY_CONTEXT;
        this.cache = engine.cache != null;
        JexlArithmetic jexla = this.jexl.arithmetic;
        this.options = opts == null ? engine.options(aContext) : opts;
        this.arithmetic = jexla.options(this.options);
        if (this.arithmetic != jexla && !this.arithmetic.getClass().equals(jexla.getClass())) {
            this.logger.warn((Object)("expected arithmetic to be " + jexla.getClass().getSimpleName() + ", got " + this.arithmetic.getClass().getSimpleName()));
        }
        this.ns = this.context instanceof JexlContext.NamespaceResolver ? (JexlContext.NamespaceResolver)((Object)this.context) : Engine.EMPTY_NS;
        AtomicBoolean acancel = null;
        if (this.context instanceof JexlContext.CancellationHandle) {
            acancel = ((JexlContext.CancellationHandle)((Object)this.context)).getCancellation();
        }
        this.cancelled = acancel != null ? acancel : new AtomicBoolean(false);
        Map<String, Object> ons = this.options.getNamespaces();
        this.functions = ons.isEmpty() ? this.jexl.functions : ons;
        this.functors = null;
        this.operators = new Operators(this);
    }

    protected InterpreterBase(InterpreterBase ii, JexlArithmetic jexla) {
        this.jexl = ii.jexl;
        this.logger = ii.logger;
        this.uberspect = ii.uberspect;
        this.arithmetic = jexla;
        this.context = ii.context;
        this.options = ii.options.copy();
        this.cache = ii.cache;
        this.ns = ii.ns;
        this.operators = ii.operators;
        this.cancelled = ii.cancelled;
        this.functions = ii.functions;
        this.functors = ii.functors;
    }

    protected void closeIfSupported(Object closeable) {
        JexlMethod mclose;
        if (closeable != null && (mclose = this.uberspect.getMethod(closeable, "close", EMPTY_PARAMS)) != null) {
            try {
                mclose.invoke(closeable, EMPTY_PARAMS);
            }
            catch (Exception xignore) {
                this.logger.warn((Object)xignore);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected Object resolveNamespace(String prefix, JexlNode node) {
        boolean cacheable;
        Object cached;
        Class<?> namespace;
        InterpreterBase interpreterBase = this;
        synchronized (interpreterBase) {
            if (this.functors != null && (namespace = this.functors.get(prefix)) != null) {
                return namespace;
            }
        }
        namespace = this.ns.resolveNamespace(prefix);
        if (namespace == null) {
            namespace = this.functions.get(prefix);
            if (prefix != null && namespace == null) {
                throw new JexlException(node, "no such function namespace " + prefix, null);
            }
        }
        Object object = cached = (cacheable = this.cache) ? node.jjtGetValue() : null;
        if (cached != JexlContext.NamespaceFunctor.class) {
            Object functor = null;
            if (namespace instanceof JexlContext.NamespaceFunctor) {
                functor = ((JexlContext.NamespaceFunctor)((Object)namespace)).createFunctor(this.context);
            } else if (namespace instanceof Class || namespace instanceof String) {
                if (cached instanceof JexlMethod) {
                    try {
                        Object eval = ((JexlMethod)cached).tryInvoke(null, this.context, new Object[0]);
                        if (JexlEngine.TRY_FAILED != eval) {
                            functor = eval;
                        }
                    }
                    catch (JexlException.TryFailed xtry) {
                        throw new JexlException(node, "unable to instantiate namespace " + prefix, xtry.getCause());
                    }
                }
                if (functor == null) {
                    JexlMethod ctor = this.uberspect.getConstructor(namespace, this.context);
                    if (ctor != null) {
                        try {
                            functor = ctor.invoke(namespace, this.context);
                            if (cacheable && ctor.isCacheable()) {
                                node.jjtSetValue(ctor);
                            }
                        }
                        catch (Exception xinst) {
                            throw new JexlException(node, "unable to instantiate namespace " + prefix, (Throwable)xinst);
                        }
                    }
                    if (functor == null) {
                        ctor = this.uberspect.getConstructor(namespace, new Object[0]);
                        if (ctor != null) {
                            try {
                                functor = ctor.invoke(namespace, new Object[0]);
                            }
                            catch (Exception xinst) {
                                throw new JexlException(node, "unable to instantiate namespace " + prefix, (Throwable)xinst);
                            }
                        }
                        if (functor == null && namespace instanceof String) {
                            try {
                                namespace = this.uberspect.getClassLoader().loadClass((String)((Object)namespace));
                            }
                            catch (ClassNotFoundException xignore) {
                                namespace = null;
                            }
                        }
                    }
                }
            }
            if (functor != null) {
                InterpreterBase interpreterBase2 = this;
                synchronized (interpreterBase2) {
                    if (this.functors == null) {
                        this.functors = new HashMap<String, Object>();
                    }
                    this.functors.put(prefix, functor);
                }
                return functor;
            }
            node.jjtSetValue(JexlContext.NamespaceFunctor.class);
        }
        return namespace;
    }

    protected boolean defineVariable(ASTVar var, LexicalFrame frame) {
        int symbol = var.getSymbol();
        if (symbol < 0) {
            return false;
        }
        if (var.isRedefined()) {
            return false;
        }
        return frame.defineSymbol(symbol, var.isCaptured());
    }

    protected boolean isVariableDefined(Frame frame, LexicalScope block, String name) {
        if (frame != null && block != null) {
            int symbol;
            Integer ref = frame.getScope().getSymbol(name);
            int n = symbol = ref != null ? ref : -1;
            if (symbol >= 0 && block.hasSymbol(symbol)) {
                Object value = frame.get(symbol);
                return value != Scope.UNDEFINED && value != Scope.UNDECLARED;
            }
        }
        return this.context.has(name);
    }

    protected Object getVariable(Frame frame, LexicalScope block, ASTIdentifier identifier) {
        Object value;
        int symbol = identifier.getSymbol();
        String name = identifier.getName();
        if (this.options.isLexicalShade() && identifier.isShaded()) {
            return this.undefinedVariable(identifier, name);
        }
        if (symbol >= 0 && frame.has(symbol) && (value = frame.get(symbol)) != Scope.UNDEFINED) {
            if (value == null && this.arithmetic.isStrict() && identifier.jjtGetParent().isStrictOperator()) {
                return this.unsolvableVariable(identifier, name, false);
            }
            return value;
        }
        value = this.context.get(name);
        if (value == null) {
            if (!this.context.has(name)) {
                boolean ignore;
                boolean bl = ignore = this.isSafe() && (symbol >= 0 || identifier.jjtGetParent() instanceof ASTAssignment) || identifier.jjtGetParent() instanceof ASTReference;
                if (!ignore) {
                    return this.undefinedVariable(identifier, name);
                }
            } else if (this.arithmetic.isStrict() && identifier.jjtGetParent().isStrictOperator()) {
                return this.unsolvableVariable(identifier, name, false);
            }
        }
        return value;
    }

    protected void setContextVariable(JexlNode node, String name, Object value) {
        if (this.options.isLexicalShade() && !this.context.has(name)) {
            throw new JexlException.Variable(node, name, true);
        }
        try {
            this.context.set(name, value);
        }
        catch (UnsupportedOperationException xsupport) {
            throw new JexlException(node, "context is readonly", (Throwable)xsupport);
        }
    }

    protected boolean isStrictEngine() {
        return this.options.isStrict();
    }

    protected boolean isSafe() {
        return this.options.isSafe();
    }

    protected boolean isSilent() {
        return this.options.isSilent();
    }

    protected boolean isCancellable() {
        return this.options.isCancellable();
    }

    protected JexlNode findNullOperand(RuntimeException xrt, JexlNode node, Object left, Object right) {
        if (xrt instanceof JexlArithmetic.NullOperand) {
            if (left == null) {
                return node.jjtGetChild(0);
            }
            if (right == null) {
                return node.jjtGetChild(1);
            }
        }
        return node;
    }

    protected Object unsolvableVariable(JexlNode node, String var, boolean undef) {
        return this.variableError(node, var, undef ? JexlException.VariableIssue.UNDEFINED : JexlException.VariableIssue.NULLVALUE);
    }

    protected Object undefinedVariable(JexlNode node, String var) {
        return this.variableError(node, var, JexlException.VariableIssue.UNDEFINED);
    }

    protected Object redefinedVariable(JexlNode node, String var) {
        return this.variableError(node, var, JexlException.VariableIssue.REDEFINED);
    }

    protected Object variableError(JexlNode node, String var, JexlException.VariableIssue issue) {
        if (this.isStrictEngine() && !node.isTernaryProtected()) {
            throw new JexlException.Variable(node, var, issue);
        }
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)JexlException.variableError(node, var, issue));
        }
        return null;
    }

    protected Object unsolvableMethod(JexlNode node, String method) {
        return this.unsolvableMethod(node, method, null);
    }

    protected Object unsolvableMethod(JexlNode node, String method, Object[] args) {
        if (this.isStrictEngine()) {
            throw new JexlException.Method(node, method, args);
        }
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)JexlException.methodError(node, method, args));
        }
        return null;
    }

    protected Object unsolvableProperty(JexlNode node, String property, boolean undef, Throwable cause) {
        if (this.isStrictEngine() && !node.isTernaryProtected()) {
            throw new JexlException.Property(node, property, undef, cause);
        }
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)JexlException.propertyError(node, property, undef));
        }
        return null;
    }

    protected boolean isLocalVariable(ASTReference node, int which) {
        return node.jjtGetNumChildren() > which && node.jjtGetChild(which) instanceof ASTIdentifier && ((ASTIdentifier)node.jjtGetChild(which)).getSymbol() >= 0;
    }

    protected boolean isFunctionCall(ASTReference node) {
        return node.jjtGetNumChildren() > 0 && node.jjtGetChild(0) instanceof ASTFunctionNode;
    }

    protected String stringifyProperty(JexlNode node) {
        if (node instanceof ASTArrayAccess) {
            return "[" + InterpreterBase.stringifyPropertyValue(node.jjtGetChild(0)) + "]";
        }
        if (node instanceof ASTMethodNode) {
            return InterpreterBase.stringifyPropertyValue(node.jjtGetChild(0));
        }
        if (node instanceof ASTFunctionNode) {
            return InterpreterBase.stringifyPropertyValue(node.jjtGetChild(0));
        }
        if (node instanceof ASTIdentifier) {
            return ((ASTIdentifier)node).getName();
        }
        if (node instanceof ASTReference) {
            return this.stringifyProperty(node.jjtGetChild(0));
        }
        return InterpreterBase.stringifyPropertyValue(node);
    }

    protected static String stringifyPropertyValue(JexlNode node) {
        return node != null ? new Debugger().depth(1).data(node) : "???";
    }

    protected Object operatorError(JexlNode node, JexlOperator operator, Throwable cause) {
        if (this.isStrictEngine()) {
            throw new JexlException.Operator(node, operator.getOperatorSymbol(), cause);
        }
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)JexlException.operatorError(node, operator.getOperatorSymbol()), cause);
        }
        return null;
    }

    protected Object annotationError(JexlNode node, String annotation, Throwable cause) {
        if (this.isStrictEngine()) {
            throw new JexlException.Annotation(node, annotation, cause);
        }
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)JexlException.annotationError(node, annotation), cause);
        }
        return null;
    }

    protected JexlException invocationException(JexlNode node, String methodName, Throwable xany) {
        Throwable cause = xany.getCause();
        if (cause instanceof JexlException) {
            return (JexlException)cause;
        }
        if (cause instanceof InterruptedException) {
            return new JexlException.Cancel(node);
        }
        return new JexlException(node, methodName, xany);
    }

    protected boolean cancel() {
        return this.cancelled.compareAndSet(false, true);
    }

    protected boolean isCancelled() {
        return this.cancelled.get() | Thread.currentThread().isInterrupted();
    }

    protected void cancelCheck(JexlNode node) {
        if (this.isCancelled()) {
            throw new JexlException.Cancel(node);
        }
    }

    protected Object[] functionArguments(Object target, boolean narrow, Object[] args) {
        if (target == null || target == this.context) {
            if (narrow) {
                this.arithmetic.narrowArguments(args);
            }
            return args;
        }
        Object[] nargv = new Object[args.length + 1];
        if (narrow) {
            nargv[0] = this.functionArgument(true, target);
            for (int a = 1; a <= args.length; ++a) {
                nargv[a] = this.functionArgument(true, args[a - 1]);
            }
        } else {
            nargv[0] = target;
            System.arraycopy(args, 0, nargv, 1, args.length);
        }
        return nargv;
    }

    protected Object[] callArguments(Object target, boolean narrow, Object[] args) {
        Object[] nargv = new Object[args.length + 1];
        if (narrow) {
            nargv[0] = this.functionArgument(true, target);
            for (int a = 1; a <= args.length; ++a) {
                nargv[a] = this.functionArgument(true, args[a - 1]);
            }
        } else {
            nargv[0] = target;
            System.arraycopy(args, 0, nargv, 1, args.length);
        }
        return nargv;
    }

    protected Object functionArgument(boolean narrow, Object arg) {
        return narrow && arg instanceof Number ? this.arithmetic.narrow((Number)arg) : arg;
    }

    protected Object getAttribute(Object object, Object attribute, JexlNode node) {
        boolean safe;
        if (object == null) {
            throw new JexlException(node, "object is null");
        }
        this.cancelCheck(node);
        JexlOperator operator = node != null && node.jjtGetParent() instanceof ASTArrayAccess ? JexlOperator.ARRAY_GET : JexlOperator.PROPERTY_GET;
        Object result = this.operators.tryOverload(node, operator, object, attribute);
        if (result != JexlEngine.TRY_FAILED) {
            return result;
        }
        Exception xcause = null;
        try {
            Object value;
            JexlPropertyGet vg;
            Object cached;
            if (node != null && this.cache && (cached = node.jjtGetValue()) instanceof JexlPropertyGet && !(vg = (JexlPropertyGet)cached).tryFailed(value = vg.tryInvoke(object, attribute))) {
                return value;
            }
            List<JexlUberspect.PropertyResolver> resolvers = this.uberspect.getResolvers(operator, object);
            vg = this.uberspect.getPropertyGet(resolvers, object, attribute);
            if (vg != null) {
                value = vg.invoke(object);
                if (node != null && this.cache && vg.isCacheable()) {
                    node.jjtSetValue(vg);
                }
                return value;
            }
        }
        catch (Exception xany) {
            xcause = xany;
        }
        if (node == null) {
            String error = "unable to get object property, class: " + object.getClass().getName() + ", property: " + attribute;
            throw new UnsupportedOperationException(error, xcause);
        }
        boolean bl = safe = node instanceof ASTIdentifierAccess && ((ASTIdentifierAccess)node).isSafe();
        if (safe) {
            return null;
        }
        String attrStr = attribute != null ? attribute.toString() : null;
        return this.unsolvableProperty(node, attrStr, true, xcause);
    }

    protected void setAttribute(Object object, Object attribute, Object value, JexlNode node) {
        this.cancelCheck(node);
        JexlOperator operator = node != null && node.jjtGetParent() instanceof ASTArrayAccess ? JexlOperator.ARRAY_SET : JexlOperator.PROPERTY_SET;
        Object result = this.operators.tryOverload(node, operator, object, attribute, value);
        if (result != JexlEngine.TRY_FAILED) {
            return;
        }
        Exception xcause = null;
        try {
            Object[] narrow;
            Object eval;
            JexlPropertySet setter;
            Object cached;
            if (node != null && this.cache && (cached = node.jjtGetValue()) instanceof JexlPropertySet && !(setter = (JexlPropertySet)cached).tryFailed(eval = setter.tryInvoke(object, attribute, value))) {
                return;
            }
            List<JexlUberspect.PropertyResolver> resolvers = this.uberspect.getResolvers(operator, object);
            JexlPropertySet vs = this.uberspect.getPropertySet(resolvers, object, attribute, value);
            if (vs == null && this.arithmetic.narrowArguments(narrow = new Object[]{value})) {
                vs = this.uberspect.getPropertySet(resolvers, object, attribute, narrow[0]);
            }
            if (vs != null) {
                vs.invoke(object, value);
                if (node != null && this.cache && vs.isCacheable()) {
                    node.jjtSetValue(vs);
                }
                return;
            }
        }
        catch (Exception xany) {
            xcause = xany;
        }
        if (node == null) {
            String error = "unable to set object property, class: " + object.getClass().getName() + ", property: " + attribute + ", argument: " + value.getClass().getSimpleName();
            throw new UnsupportedOperationException(error, xcause);
        }
        String attrStr = attribute != null ? attribute.toString() : null;
        this.unsolvableProperty(node, attrStr, true, xcause);
    }

    protected class CallDispatcher {
        final JexlNode node;
        boolean cacheable = true;
        boolean narrow = false;
        JexlMethod vm = null;
        Object target = null;
        Object[] argv = null;
        Funcall funcall = null;

        CallDispatcher(JexlNode anode, boolean acacheable) {
            this.node = anode;
            this.cacheable = acacheable;
        }

        protected boolean isTargetMethod(Object ntarget, String mname, Object[] arguments) {
            this.vm = InterpreterBase.this.uberspect.getMethod(ntarget, mname, arguments);
            if (this.vm != null) {
                this.argv = arguments;
                this.target = ntarget;
                if (this.cacheable && this.vm.isCacheable()) {
                    this.funcall = new Funcall(this.vm, this.narrow);
                }
                return true;
            }
            return false;
        }

        protected boolean isContextMethod(String mname, Object[] arguments) {
            this.vm = InterpreterBase.this.uberspect.getMethod(InterpreterBase.this.context, mname, arguments);
            if (this.vm != null) {
                this.argv = arguments;
                this.target = InterpreterBase.this.context;
                if (this.cacheable && this.vm.isCacheable()) {
                    this.funcall = new ContextFuncall(this.vm, this.narrow);
                }
                return true;
            }
            return false;
        }

        protected boolean isArithmeticMethod(String mname, Object[] arguments) {
            this.vm = InterpreterBase.this.uberspect.getMethod(InterpreterBase.this.arithmetic, mname, arguments);
            if (this.vm != null) {
                this.argv = arguments;
                this.target = InterpreterBase.this.arithmetic;
                if (this.cacheable && this.vm.isCacheable()) {
                    this.funcall = new ArithmeticFuncall(this.vm, this.narrow);
                }
                return true;
            }
            return false;
        }

        protected Object tryEval(Object ntarget, String mname, Object[] arguments) {
            Object cached;
            if (mname != null && this.cacheable && ntarget != null && (cached = this.node.jjtGetValue()) instanceof Funcall) {
                return ((Funcall)cached).tryInvoke(InterpreterBase.this, mname, ntarget, arguments);
            }
            return JexlEngine.TRY_FAILED;
        }

        protected Object eval(String mname) throws Exception {
            if (this.vm != null) {
                Object eval = this.vm.invoke(this.target, this.argv);
                if (this.funcall != null) {
                    this.node.jjtSetValue(this.funcall);
                }
                return eval;
            }
            return InterpreterBase.this.unsolvableMethod(this.node, mname, this.argv);
        }
    }

    protected static class ContextualCtor
    extends Funcall {
        protected ContextualCtor(JexlMethod jme, boolean flag) {
            super(jme, flag);
        }

        @Override
        protected Object tryInvoke(InterpreterBase ii, String name, Object target, Object[] args) {
            return this.me.tryInvoke(name, target, ii.callArguments(ii.context, this.narrow, args));
        }
    }

    protected static class ContextFuncall
    extends Funcall {
        protected ContextFuncall(JexlMethod jme, boolean flag) {
            super(jme, flag);
        }

        @Override
        protected Object tryInvoke(InterpreterBase ii, String name, Object target, Object[] args) {
            return this.me.tryInvoke(name, ii.context, ii.functionArguments(target, this.narrow, args));
        }
    }

    protected static class ArithmeticFuncall
    extends Funcall {
        protected ArithmeticFuncall(JexlMethod jme, boolean flag) {
            super(jme, flag);
        }

        @Override
        protected Object tryInvoke(InterpreterBase ii, String name, Object target, Object[] args) {
            return this.me.tryInvoke(name, ii.arithmetic, ii.functionArguments(target, this.narrow, args));
        }
    }

    protected static class Funcall
    implements JexlNode.Funcall {
        protected final boolean narrow;
        protected final JexlMethod me;

        protected Funcall(JexlMethod jme, boolean flag) {
            this.me = jme;
            this.narrow = flag;
        }

        protected Object tryInvoke(InterpreterBase ii, String name, Object target, Object[] args) {
            return this.me.tryInvoke(name, target, ii.functionArguments(null, this.narrow, args));
        }
    }
}

