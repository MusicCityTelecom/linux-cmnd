/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal;

import java.util.Iterator;
import java.util.concurrent.Callable;
import org.apache.commons.jexl3.JexlArithmetic;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.JexlInfo;
import org.apache.commons.jexl3.JexlOperator;
import org.apache.commons.jexl3.JexlOptions;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.JxltEngine;
import org.apache.commons.jexl3.internal.Closure;
import org.apache.commons.jexl3.internal.Engine;
import org.apache.commons.jexl3.internal.Frame;
import org.apache.commons.jexl3.internal.InterpreterBase;
import org.apache.commons.jexl3.internal.LexicalFrame;
import org.apache.commons.jexl3.internal.TemplateEngine;
import org.apache.commons.jexl3.introspection.JexlMethod;
import org.apache.commons.jexl3.introspection.JexlPropertyGet;
import org.apache.commons.jexl3.parser.ASTAddNode;
import org.apache.commons.jexl3.parser.ASTAndNode;
import org.apache.commons.jexl3.parser.ASTAnnotatedStatement;
import org.apache.commons.jexl3.parser.ASTAnnotation;
import org.apache.commons.jexl3.parser.ASTArguments;
import org.apache.commons.jexl3.parser.ASTArrayAccess;
import org.apache.commons.jexl3.parser.ASTArrayLiteral;
import org.apache.commons.jexl3.parser.ASTAssignment;
import org.apache.commons.jexl3.parser.ASTBitwiseAndNode;
import org.apache.commons.jexl3.parser.ASTBitwiseComplNode;
import org.apache.commons.jexl3.parser.ASTBitwiseOrNode;
import org.apache.commons.jexl3.parser.ASTBitwiseXorNode;
import org.apache.commons.jexl3.parser.ASTBlock;
import org.apache.commons.jexl3.parser.ASTBreak;
import org.apache.commons.jexl3.parser.ASTConstructorNode;
import org.apache.commons.jexl3.parser.ASTContinue;
import org.apache.commons.jexl3.parser.ASTDivNode;
import org.apache.commons.jexl3.parser.ASTDoWhileStatement;
import org.apache.commons.jexl3.parser.ASTEQNode;
import org.apache.commons.jexl3.parser.ASTERNode;
import org.apache.commons.jexl3.parser.ASTEWNode;
import org.apache.commons.jexl3.parser.ASTEmptyFunction;
import org.apache.commons.jexl3.parser.ASTExtendedLiteral;
import org.apache.commons.jexl3.parser.ASTFalseNode;
import org.apache.commons.jexl3.parser.ASTForeachStatement;
import org.apache.commons.jexl3.parser.ASTFunctionNode;
import org.apache.commons.jexl3.parser.ASTGENode;
import org.apache.commons.jexl3.parser.ASTGTNode;
import org.apache.commons.jexl3.parser.ASTIdentifier;
import org.apache.commons.jexl3.parser.ASTIdentifierAccess;
import org.apache.commons.jexl3.parser.ASTIdentifierAccessJxlt;
import org.apache.commons.jexl3.parser.ASTIfStatement;
import org.apache.commons.jexl3.parser.ASTJexlLambda;
import org.apache.commons.jexl3.parser.ASTJexlScript;
import org.apache.commons.jexl3.parser.ASTJxltLiteral;
import org.apache.commons.jexl3.parser.ASTLENode;
import org.apache.commons.jexl3.parser.ASTLTNode;
import org.apache.commons.jexl3.parser.ASTMapEntry;
import org.apache.commons.jexl3.parser.ASTMapLiteral;
import org.apache.commons.jexl3.parser.ASTMethodNode;
import org.apache.commons.jexl3.parser.ASTModNode;
import org.apache.commons.jexl3.parser.ASTMulNode;
import org.apache.commons.jexl3.parser.ASTNENode;
import org.apache.commons.jexl3.parser.ASTNEWNode;
import org.apache.commons.jexl3.parser.ASTNRNode;
import org.apache.commons.jexl3.parser.ASTNSWNode;
import org.apache.commons.jexl3.parser.ASTNotNode;
import org.apache.commons.jexl3.parser.ASTNullLiteral;
import org.apache.commons.jexl3.parser.ASTNullpNode;
import org.apache.commons.jexl3.parser.ASTNumberLiteral;
import org.apache.commons.jexl3.parser.ASTOrNode;
import org.apache.commons.jexl3.parser.ASTRangeNode;
import org.apache.commons.jexl3.parser.ASTReference;
import org.apache.commons.jexl3.parser.ASTReferenceExpression;
import org.apache.commons.jexl3.parser.ASTRegexLiteral;
import org.apache.commons.jexl3.parser.ASTReturnStatement;
import org.apache.commons.jexl3.parser.ASTSWNode;
import org.apache.commons.jexl3.parser.ASTSetAddNode;
import org.apache.commons.jexl3.parser.ASTSetAndNode;
import org.apache.commons.jexl3.parser.ASTSetDivNode;
import org.apache.commons.jexl3.parser.ASTSetLiteral;
import org.apache.commons.jexl3.parser.ASTSetModNode;
import org.apache.commons.jexl3.parser.ASTSetMultNode;
import org.apache.commons.jexl3.parser.ASTSetOrNode;
import org.apache.commons.jexl3.parser.ASTSetSubNode;
import org.apache.commons.jexl3.parser.ASTSetXorNode;
import org.apache.commons.jexl3.parser.ASTSizeFunction;
import org.apache.commons.jexl3.parser.ASTStringLiteral;
import org.apache.commons.jexl3.parser.ASTSubNode;
import org.apache.commons.jexl3.parser.ASTTernaryNode;
import org.apache.commons.jexl3.parser.ASTTrueNode;
import org.apache.commons.jexl3.parser.ASTUnaryMinusNode;
import org.apache.commons.jexl3.parser.ASTUnaryPlusNode;
import org.apache.commons.jexl3.parser.ASTVar;
import org.apache.commons.jexl3.parser.ASTWhileStatement;
import org.apache.commons.jexl3.parser.JexlNode;

public class Interpreter
extends InterpreterBase {
    protected int fp = 0;
    protected final Frame frame;
    protected LexicalFrame block = null;
    protected static final ThreadLocal<Interpreter> INTER = new ThreadLocal();

    protected Interpreter(Engine engine, JexlOptions opts, JexlContext aContext, Frame eFrame) {
        super(engine, opts, aContext);
        this.frame = eFrame;
    }

    protected Interpreter(Interpreter ii, JexlArithmetic jexla) {
        super(ii, jexla);
        this.frame = ii.frame;
        this.block = ii.block != null ? new LexicalFrame(ii.block) : null;
    }

    protected Interpreter putThreadInterpreter(Interpreter inter) {
        Interpreter pinter = INTER.get();
        INTER.set(inter);
        return pinter;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Object interpret(JexlNode node) {
        JexlContext.ThreadLocal tcontext = null;
        JexlEngine tjexl = null;
        Interpreter tinter = null;
        try {
            tinter = this.putThreadInterpreter(this);
            if (tinter != null) {
                this.fp = tinter.fp + 1;
            }
            if (this.context instanceof JexlContext.ThreadLocal) {
                tcontext = this.jexl.putThreadLocal((JexlContext.ThreadLocal)this.context);
            }
            tjexl = this.jexl.putThreadEngine(this.jexl);
            if (this.fp > this.jexl.stackOverflow) {
                throw new JexlException.StackOverflow(node.jexlInfo(), "jexl (" + this.jexl.stackOverflow + ")", null);
            }
            this.cancelCheck(node);
            Object object = node.jjtAccept(this, null);
            return object;
        }
        catch (StackOverflowError xstack) {
            JexlException.StackOverflow xjexl = new JexlException.StackOverflow(node.jexlInfo(), "jvm", (Throwable)xstack);
            if (!this.isSilent()) {
                throw xjexl.clean();
            }
            if (this.logger.isWarnEnabled()) {
                this.logger.warn((Object)xjexl.getMessage(), xjexl.getCause());
            }
        }
        catch (JexlException.Return xreturn) {
            Object object = xreturn.getValue();
            return object;
        }
        catch (JexlException.Cancel xcancel) {
            this.cancelled.weakCompareAndSet(false, Thread.interrupted());
            if (this.isCancellable()) {
                throw xcancel.clean();
            }
        }
        catch (JexlException xjexl) {
            if (!this.isSilent()) {
                throw xjexl.clean();
            }
            if (this.logger.isWarnEnabled()) {
                this.logger.warn((Object)xjexl.getMessage(), xjexl.getCause());
            }
        }
        finally {
            Interpreter xstack = this;
            synchronized (xstack) {
                if (this.functors != null) {
                    for (Object functor : this.functors.values()) {
                        this.closeIfSupported(functor);
                    }
                    this.functors.clear();
                    this.functors = null;
                }
            }
            this.jexl.putThreadEngine(tjexl);
            if (this.context instanceof JexlContext.ThreadLocal) {
                this.jexl.putThreadLocal(tcontext);
            }
            if (tinter != null) {
                this.fp = tinter.fp - 1;
            }
            this.putThreadInterpreter(tinter);
        }
        return null;
    }

    public Object getAttribute(Object object, Object attribute) {
        return this.getAttribute(object, attribute, null);
    }

    public void setAttribute(Object object, Object attribute, Object value) {
        this.setAttribute(object, attribute, value, null);
    }

    @Override
    protected Object visit(ASTAddNode node, Object data) {
        Object left = node.jjtGetChild(0).jjtAccept(this, data);
        Object right = node.jjtGetChild(1).jjtAccept(this, data);
        try {
            Object result = this.operators.tryOverload(node, JexlOperator.ADD, left, right);
            return result != JexlEngine.TRY_FAILED ? result : this.arithmetic.add(left, right);
        }
        catch (ArithmeticException xrt) {
            throw new JexlException(node, "+ error", (Throwable)xrt);
        }
    }

    @Override
    protected Object visit(ASTSubNode node, Object data) {
        Object left = node.jjtGetChild(0).jjtAccept(this, data);
        Object right = node.jjtGetChild(1).jjtAccept(this, data);
        try {
            Object result = this.operators.tryOverload(node, JexlOperator.SUBTRACT, left, right);
            return result != JexlEngine.TRY_FAILED ? result : this.arithmetic.subtract(left, right);
        }
        catch (ArithmeticException xrt) {
            throw new JexlException(node, "- error", (Throwable)xrt);
        }
    }

    @Override
    protected Object visit(ASTMulNode node, Object data) {
        Object left = node.jjtGetChild(0).jjtAccept(this, data);
        Object right = node.jjtGetChild(1).jjtAccept(this, data);
        try {
            Object result = this.operators.tryOverload(node, JexlOperator.MULTIPLY, left, right);
            return result != JexlEngine.TRY_FAILED ? result : this.arithmetic.multiply(left, right);
        }
        catch (ArithmeticException xrt) {
            JexlNode xnode = this.findNullOperand(xrt, node, left, right);
            throw new JexlException(xnode, "* error", (Throwable)xrt);
        }
    }

    @Override
    protected Object visit(ASTDivNode node, Object data) {
        Object left = node.jjtGetChild(0).jjtAccept(this, data);
        Object right = node.jjtGetChild(1).jjtAccept(this, data);
        try {
            Object result = this.operators.tryOverload(node, JexlOperator.DIVIDE, left, right);
            return result != JexlEngine.TRY_FAILED ? result : this.arithmetic.divide(left, right);
        }
        catch (ArithmeticException xrt) {
            if (!this.arithmetic.isStrict()) {
                return 0.0;
            }
            JexlNode xnode = this.findNullOperand(xrt, node, left, right);
            throw new JexlException(xnode, "/ error", (Throwable)xrt);
        }
    }

    @Override
    protected Object visit(ASTModNode node, Object data) {
        Object left = node.jjtGetChild(0).jjtAccept(this, data);
        Object right = node.jjtGetChild(1).jjtAccept(this, data);
        try {
            Object result = this.operators.tryOverload(node, JexlOperator.MOD, left, right);
            return result != JexlEngine.TRY_FAILED ? result : this.arithmetic.mod(left, right);
        }
        catch (ArithmeticException xrt) {
            if (!this.arithmetic.isStrict()) {
                return 0.0;
            }
            JexlNode xnode = this.findNullOperand(xrt, node, left, right);
            throw new JexlException(xnode, "% error", (Throwable)xrt);
        }
    }

    @Override
    protected Object visit(ASTBitwiseAndNode node, Object data) {
        Object left = node.jjtGetChild(0).jjtAccept(this, data);
        Object right = node.jjtGetChild(1).jjtAccept(this, data);
        try {
            Object result = this.operators.tryOverload(node, JexlOperator.AND, left, right);
            return result != JexlEngine.TRY_FAILED ? result : this.arithmetic.and(left, right);
        }
        catch (ArithmeticException xrt) {
            throw new JexlException(node, "& error", (Throwable)xrt);
        }
    }

    @Override
    protected Object visit(ASTBitwiseOrNode node, Object data) {
        Object left = node.jjtGetChild(0).jjtAccept(this, data);
        Object right = node.jjtGetChild(1).jjtAccept(this, data);
        try {
            Object result = this.operators.tryOverload(node, JexlOperator.OR, left, right);
            return result != JexlEngine.TRY_FAILED ? result : this.arithmetic.or(left, right);
        }
        catch (ArithmeticException xrt) {
            throw new JexlException(node, "| error", (Throwable)xrt);
        }
    }

    @Override
    protected Object visit(ASTBitwiseXorNode node, Object data) {
        Object left = node.jjtGetChild(0).jjtAccept(this, data);
        Object right = node.jjtGetChild(1).jjtAccept(this, data);
        try {
            Object result = this.operators.tryOverload(node, JexlOperator.XOR, left, right);
            return result != JexlEngine.TRY_FAILED ? result : this.arithmetic.xor(left, right);
        }
        catch (ArithmeticException xrt) {
            throw new JexlException(node, "^ error", (Throwable)xrt);
        }
    }

    @Override
    protected Object visit(ASTEQNode node, Object data) {
        Object left = node.jjtGetChild(0).jjtAccept(this, data);
        Object right = node.jjtGetChild(1).jjtAccept(this, data);
        try {
            Object result = this.operators.tryOverload(node, JexlOperator.EQ, left, right);
            return result != JexlEngine.TRY_FAILED ? result : Boolean.valueOf(this.arithmetic.equals(left, right));
        }
        catch (ArithmeticException xrt) {
            throw new JexlException(node, "== error", (Throwable)xrt);
        }
    }

    @Override
    protected Object visit(ASTNENode node, Object data) {
        Object left = node.jjtGetChild(0).jjtAccept(this, data);
        Object right = node.jjtGetChild(1).jjtAccept(this, data);
        try {
            Object result = this.operators.tryOverload(node, JexlOperator.EQ, left, right);
            return result != JexlEngine.TRY_FAILED ? !this.arithmetic.toBoolean(result) : !this.arithmetic.equals(left, right);
        }
        catch (ArithmeticException xrt) {
            JexlNode xnode = this.findNullOperand(xrt, node, left, right);
            throw new JexlException(xnode, "!= error", (Throwable)xrt);
        }
    }

    @Override
    protected Object visit(ASTGENode node, Object data) {
        Object left = node.jjtGetChild(0).jjtAccept(this, data);
        Object right = node.jjtGetChild(1).jjtAccept(this, data);
        try {
            Object result = this.operators.tryOverload(node, JexlOperator.GTE, left, right);
            return result != JexlEngine.TRY_FAILED ? result : Boolean.valueOf(this.arithmetic.greaterThanOrEqual(left, right));
        }
        catch (ArithmeticException xrt) {
            throw new JexlException(node, ">= error", (Throwable)xrt);
        }
    }

    @Override
    protected Object visit(ASTGTNode node, Object data) {
        Object left = node.jjtGetChild(0).jjtAccept(this, data);
        Object right = node.jjtGetChild(1).jjtAccept(this, data);
        try {
            Object result = this.operators.tryOverload(node, JexlOperator.GT, left, right);
            return result != JexlEngine.TRY_FAILED ? result : Boolean.valueOf(this.arithmetic.greaterThan(left, right));
        }
        catch (ArithmeticException xrt) {
            throw new JexlException(node, "> error", (Throwable)xrt);
        }
    }

    @Override
    protected Object visit(ASTLENode node, Object data) {
        Object left = node.jjtGetChild(0).jjtAccept(this, data);
        Object right = node.jjtGetChild(1).jjtAccept(this, data);
        try {
            Object result = this.operators.tryOverload(node, JexlOperator.LTE, left, right);
            return result != JexlEngine.TRY_FAILED ? result : Boolean.valueOf(this.arithmetic.lessThanOrEqual(left, right));
        }
        catch (ArithmeticException xrt) {
            throw new JexlException(node, "<= error", (Throwable)xrt);
        }
    }

    @Override
    protected Object visit(ASTLTNode node, Object data) {
        Object left = node.jjtGetChild(0).jjtAccept(this, data);
        Object right = node.jjtGetChild(1).jjtAccept(this, data);
        try {
            Object result = this.operators.tryOverload(node, JexlOperator.LT, left, right);
            return result != JexlEngine.TRY_FAILED ? result : Boolean.valueOf(this.arithmetic.lessThan(left, right));
        }
        catch (ArithmeticException xrt) {
            throw new JexlException(node, "< error", (Throwable)xrt);
        }
    }

    @Override
    protected Object visit(ASTSWNode node, Object data) {
        Object left = node.jjtGetChild(0).jjtAccept(this, data);
        Object right = node.jjtGetChild(1).jjtAccept(this, data);
        return this.operators.startsWith(node, "^=", left, right);
    }

    @Override
    protected Object visit(ASTNSWNode node, Object data) {
        Object right;
        Object left = node.jjtGetChild(0).jjtAccept(this, data);
        return !this.operators.startsWith(node, "^!", left, right = node.jjtGetChild(1).jjtAccept(this, data));
    }

    @Override
    protected Object visit(ASTEWNode node, Object data) {
        Object left = node.jjtGetChild(0).jjtAccept(this, data);
        Object right = node.jjtGetChild(1).jjtAccept(this, data);
        return this.operators.endsWith(node, "$=", left, right);
    }

    @Override
    protected Object visit(ASTNEWNode node, Object data) {
        Object right;
        Object left = node.jjtGetChild(0).jjtAccept(this, data);
        return !this.operators.endsWith(node, "$!", left, right = node.jjtGetChild(1).jjtAccept(this, data));
    }

    @Override
    protected Object visit(ASTERNode node, Object data) {
        Object left = node.jjtGetChild(0).jjtAccept(this, data);
        Object right = node.jjtGetChild(1).jjtAccept(this, data);
        return this.operators.contains(node, "=~", right, left);
    }

    @Override
    protected Object visit(ASTNRNode node, Object data) {
        Object left = node.jjtGetChild(0).jjtAccept(this, data);
        Object right = node.jjtGetChild(1).jjtAccept(this, data);
        return !this.operators.contains(node, "!~", right, left);
    }

    @Override
    protected Object visit(ASTRangeNode node, Object data) {
        Object left = node.jjtGetChild(0).jjtAccept(this, data);
        Object right = node.jjtGetChild(1).jjtAccept(this, data);
        try {
            return this.arithmetic.createRange(left, right);
        }
        catch (ArithmeticException xrt) {
            JexlNode xnode = this.findNullOperand(xrt, node, left, right);
            throw new JexlException(xnode, ".. error", (Throwable)xrt);
        }
    }

    @Override
    protected Object visit(ASTUnaryMinusNode node, Object data) {
        Object value = node.jjtGetValue();
        if (value != null && !(value instanceof JexlMethod)) {
            return value;
        }
        JexlNode valNode = node.jjtGetChild(0);
        Object val = valNode.jjtAccept(this, data);
        try {
            Object result = this.operators.tryOverload(node, JexlOperator.NEGATE, val);
            if (result != JexlEngine.TRY_FAILED) {
                return result;
            }
            Object number = this.arithmetic.negate(val);
            if (number instanceof Number && valNode instanceof ASTNumberLiteral) {
                number = this.arithmetic.narrowNumber((Number)number, ((ASTNumberLiteral)valNode).getLiteralClass());
                if (this.arithmetic.isNegateStable()) {
                    node.jjtSetValue(number);
                }
            }
            return number;
        }
        catch (ArithmeticException xrt) {
            throw new JexlException(valNode, "- error", (Throwable)xrt);
        }
    }

    @Override
    protected Object visit(ASTUnaryPlusNode node, Object data) {
        Object value = node.jjtGetValue();
        if (value != null && !(value instanceof JexlMethod)) {
            return value;
        }
        JexlNode valNode = node.jjtGetChild(0);
        Object val = valNode.jjtAccept(this, data);
        try {
            Object result = this.operators.tryOverload(node, JexlOperator.POSITIVIZE, val);
            if (result != JexlEngine.TRY_FAILED) {
                return result;
            }
            Object number = this.arithmetic.positivize(val);
            if (valNode instanceof ASTNumberLiteral && number instanceof Number && this.arithmetic.isPositivizeStable()) {
                node.jjtSetValue(number);
            }
            return number;
        }
        catch (ArithmeticException xrt) {
            throw new JexlException(valNode, "- error", (Throwable)xrt);
        }
    }

    @Override
    protected Object visit(ASTBitwiseComplNode node, Object data) {
        Object arg = node.jjtGetChild(0).jjtAccept(this, data);
        try {
            Object result = this.operators.tryOverload(node, JexlOperator.COMPLEMENT, arg);
            return result != JexlEngine.TRY_FAILED ? result : this.arithmetic.complement(arg);
        }
        catch (ArithmeticException xrt) {
            throw new JexlException(node, "~ error", (Throwable)xrt);
        }
    }

    @Override
    protected Object visit(ASTNotNode node, Object data) {
        Object val = node.jjtGetChild(0).jjtAccept(this, data);
        try {
            Object result = this.operators.tryOverload(node, JexlOperator.NOT, val);
            return result != JexlEngine.TRY_FAILED ? result : this.arithmetic.not(val);
        }
        catch (ArithmeticException xrt) {
            throw new JexlException(node, "! error", (Throwable)xrt);
        }
    }

    @Override
    protected Object visit(ASTIfStatement node, Object data) {
        boolean n = false;
        int numChildren = node.jjtGetNumChildren();
        try {
            Object result = null;
            for (int ifElse = 0; ifElse < numChildren - 1; ifElse += 2) {
                Object condition = node.jjtGetChild(ifElse).jjtAccept(this, null);
                if (!this.arithmetic.toBoolean(condition)) continue;
                return node.jjtGetChild(ifElse + 1).jjtAccept(this, null);
            }
            if ((numChildren & 1) == 1) {
                result = node.jjtGetChild(numChildren - 1).jjtAccept(this, null);
            }
            return result;
        }
        catch (ArithmeticException xrt) {
            throw new JexlException(node.jjtGetChild(0), "if error", (Throwable)xrt);
        }
    }

    @Override
    protected Object visit(ASTVar node, Object data) {
        int symbol = node.getSymbol();
        if (!this.options.isLexical()) {
            if (this.frame.has(symbol)) {
                return this.frame.get(symbol);
            }
        } else if (!this.defineVariable(node, this.block)) {
            return this.redefinedVariable(node, node.getName());
        }
        this.frame.set(symbol, null);
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected Object visit(ASTBlock node, Object data) {
        int cnt = node.getSymbolCount();
        if (!this.options.isLexical() || cnt <= 0) {
            return this.visitBlock(node, data);
        }
        try {
            this.block = new LexicalFrame(this.frame, this.block);
            Object object = this.visitBlock(node, data);
            return object;
        }
        finally {
            this.block = this.block.pop();
        }
    }

    private Object visitBlock(ASTBlock node, Object data) {
        int numChildren = node.jjtGetNumChildren();
        Object result = null;
        for (int i = 0; i < numChildren; ++i) {
            this.cancelCheck(node);
            result = node.jjtGetChild(i).jjtAccept(this, data);
        }
        return result;
    }

    @Override
    protected Object visit(ASTReturnStatement node, Object data) {
        Object val = node.jjtGetChild(0).jjtAccept(this, data);
        this.cancelCheck(node);
        throw new JexlException.Return((JexlNode)node, null, val);
    }

    @Override
    protected Object visit(ASTContinue node, Object data) {
        throw new JexlException.Continue(node);
    }

    @Override
    protected Object visit(ASTBreak node, Object data) {
        throw new JexlException.Break(node);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected Object visit(ASTForeachStatement node, Object data) {
        Object forEach;
        boolean lexical;
        Object result;
        block14: {
            Object object;
            Iterator<?> itemsIterator;
            JexlNode statement;
            boolean loopSymbol;
            result = null;
            ASTReference loopReference = (ASTReference)node.jjtGetChild(0);
            ASTIdentifier loopVariable = (ASTIdentifier)loopReference.jjtGetChild(0);
            int symbol = loopVariable.getSymbol();
            lexical = this.options.isLexical();
            LexicalFrame locals = lexical ? new LexicalFrame(this.frame, this.block) : null;
            boolean bl = loopSymbol = symbol >= 0 && loopVariable instanceof ASTVar;
            if (lexical) {
                if (loopSymbol && !this.defineVariable((ASTVar)loopVariable, locals)) {
                    return this.redefinedVariable(node, loopVariable.getName());
                }
                this.block = locals;
            }
            forEach = null;
            try {
                Object iterableValue = node.jjtGetChild(1).jjtAccept(this, data);
                if (iterableValue == null) break block14;
                statement = node.jjtGetNumChildren() >= 3 ? node.jjtGetChild(2) : null;
                forEach = this.operators.tryOverload(node, JexlOperator.FOR_EACH, iterableValue);
                Iterator<?> iterator = itemsIterator = forEach instanceof Iterator ? (Iterator<?>)forEach : this.uberspect.getIterator(iterableValue);
                if (itemsIterator == null) break block14;
                int cnt = 0;
                while (itemsIterator.hasNext()) {
                    this.cancelCheck(node);
                    if (!lexical || cnt++ <= 0) break block15;
                    this.block.pop();
                    if (!loopSymbol || this.defineVariable((ASTVar)loopVariable, locals)) break block15;
                    object = this.redefinedVariable(node, loopVariable.getName());
                }
            }
            catch (Throwable throwable) {
                this.closeIfSupported(forEach);
                if (lexical) {
                    this.block = this.block.pop();
                }
                throw throwable;
            }
            {
                block15: {
                    this.closeIfSupported(forEach);
                    if (lexical) {
                        this.block = this.block.pop();
                    }
                    return object;
                }
                Object value = itemsIterator.next();
                if (symbol < 0) {
                    this.setContextVariable(node, loopVariable.getName(), value);
                } else {
                    this.frame.set(symbol, value);
                }
                if (statement == null) continue;
                try {
                    result = statement.jjtAccept(this, data);
                    continue;
                }
                catch (JexlException.Break stmtBreak) {
                    break;
                }
                catch (JexlException.Continue continue_) {
                    continue;
                }
            }
        }
        this.closeIfSupported(forEach);
        if (lexical) {
            this.block = this.block.pop();
        }
        return result;
    }

    @Override
    protected Object visit(ASTWhileStatement node, Object data) {
        Object result = null;
        JexlNode condition = node.jjtGetChild(0);
        while (this.arithmetic.toBoolean(condition.jjtAccept(this, data))) {
            this.cancelCheck(node);
            if (node.jjtGetNumChildren() <= 1) continue;
            try {
                result = node.jjtGetChild(1).jjtAccept(this, data);
            }
            catch (JexlException.Break stmtBreak) {
                break;
            }
            catch (JexlException.Continue continue_) {
            }
        }
        return result;
    }

    @Override
    protected Object visit(ASTDoWhileStatement node, Object data) {
        Object result = null;
        int nc = node.jjtGetNumChildren();
        JexlNode condition = node.jjtGetChild(nc - 1);
        do {
            this.cancelCheck(node);
            if (nc <= 1) continue;
            try {
                result = node.jjtGetChild(0).jjtAccept(this, data);
            }
            catch (JexlException.Break stmtBreak) {
                break;
            }
            catch (JexlException.Continue continue_) {
                // empty catch block
            }
        } while (this.arithmetic.toBoolean(condition.jjtAccept(this, data)));
        return result;
    }

    @Override
    protected Object visit(ASTAndNode node, Object data) {
        Object left = node.jjtGetChild(0).jjtAccept(this, data);
        try {
            boolean leftValue = this.arithmetic.toBoolean(left);
            if (!leftValue) {
                return Boolean.FALSE;
            }
        }
        catch (ArithmeticException xrt) {
            throw new JexlException(node.jjtGetChild(0), "boolean coercion error", (Throwable)xrt);
        }
        Object right = node.jjtGetChild(1).jjtAccept(this, data);
        try {
            boolean rightValue = this.arithmetic.toBoolean(right);
            if (!rightValue) {
                return Boolean.FALSE;
            }
        }
        catch (ArithmeticException xrt) {
            throw new JexlException(node.jjtGetChild(1), "boolean coercion error", (Throwable)xrt);
        }
        return Boolean.TRUE;
    }

    @Override
    protected Object visit(ASTOrNode node, Object data) {
        Object left = node.jjtGetChild(0).jjtAccept(this, data);
        try {
            boolean leftValue = this.arithmetic.toBoolean(left);
            if (leftValue) {
                return Boolean.TRUE;
            }
        }
        catch (ArithmeticException xrt) {
            throw new JexlException(node.jjtGetChild(0), "boolean coercion error", (Throwable)xrt);
        }
        Object right = node.jjtGetChild(1).jjtAccept(this, data);
        try {
            boolean rightValue = this.arithmetic.toBoolean(right);
            if (rightValue) {
                return Boolean.TRUE;
            }
        }
        catch (ArithmeticException xrt) {
            throw new JexlException(node.jjtGetChild(1), "boolean coercion error", (Throwable)xrt);
        }
        return Boolean.FALSE;
    }

    @Override
    protected Object visit(ASTNullLiteral node, Object data) {
        return null;
    }

    @Override
    protected Object visit(ASTTrueNode node, Object data) {
        return Boolean.TRUE;
    }

    @Override
    protected Object visit(ASTFalseNode node, Object data) {
        return Boolean.FALSE;
    }

    @Override
    protected Object visit(ASTNumberLiteral node, Object data) {
        if (data != null && node.isInteger()) {
            return this.getAttribute(data, node.getLiteral(), node);
        }
        return node.getLiteral();
    }

    @Override
    protected Object visit(ASTStringLiteral node, Object data) {
        if (data != null) {
            return this.getAttribute(data, node.getLiteral(), node);
        }
        return node.getLiteral();
    }

    @Override
    protected Object visit(ASTRegexLiteral node, Object data) {
        return node.getLiteral();
    }

    @Override
    protected Object visit(ASTArrayLiteral node, Object data) {
        int childCount = node.jjtGetNumChildren();
        JexlArithmetic.ArrayBuilder ab = this.arithmetic.arrayBuilder(childCount);
        boolean extended = false;
        for (int i = 0; i < childCount; ++i) {
            this.cancelCheck(node);
            JexlNode child = node.jjtGetChild(i);
            if (child instanceof ASTExtendedLiteral) {
                extended = true;
                continue;
            }
            Object entry = node.jjtGetChild(i).jjtAccept(this, data);
            ab.add(entry);
        }
        return ab.create(extended);
    }

    @Override
    protected Object visit(ASTExtendedLiteral node, Object data) {
        return node;
    }

    @Override
    protected Object visit(ASTSetLiteral node, Object data) {
        int childCount = node.jjtGetNumChildren();
        JexlArithmetic.SetBuilder mb = this.arithmetic.setBuilder(childCount);
        for (int i = 0; i < childCount; ++i) {
            this.cancelCheck(node);
            Object entry = node.jjtGetChild(i).jjtAccept(this, data);
            mb.add(entry);
        }
        return mb.create();
    }

    @Override
    protected Object visit(ASTMapLiteral node, Object data) {
        int childCount = node.jjtGetNumChildren();
        JexlArithmetic.MapBuilder mb = this.arithmetic.mapBuilder(childCount);
        for (int i = 0; i < childCount; ++i) {
            this.cancelCheck(node);
            Object[] entry = (Object[])node.jjtGetChild(i).jjtAccept(this, data);
            mb.put(entry[0], entry[1]);
        }
        return mb.create();
    }

    @Override
    protected Object visit(ASTMapEntry node, Object data) {
        Object key = node.jjtGetChild(0).jjtAccept(this, data);
        Object value = node.jjtGetChild(1).jjtAccept(this, data);
        return new Object[]{key, value};
    }

    @Override
    protected Object visit(ASTTernaryNode node, Object data) {
        Object condition;
        try {
            condition = node.jjtGetChild(0).jjtAccept(this, data);
        }
        catch (JexlException xany) {
            if (!(xany.getCause() instanceof JexlArithmetic.NullOperand)) {
                throw xany;
            }
            condition = null;
        }
        if (node.jjtGetNumChildren() == 3) {
            if (condition != null && this.arithmetic.toBoolean(condition)) {
                return node.jjtGetChild(1).jjtAccept(this, data);
            }
            return node.jjtGetChild(2).jjtAccept(this, data);
        }
        if (condition != null && this.arithmetic.toBoolean(condition)) {
            return condition;
        }
        return node.jjtGetChild(1).jjtAccept(this, data);
    }

    @Override
    protected Object visit(ASTNullpNode node, Object data) {
        Object lhs;
        try {
            lhs = node.jjtGetChild(0).jjtAccept(this, data);
        }
        catch (JexlException xany) {
            if (!(xany.getCause() instanceof JexlArithmetic.NullOperand)) {
                throw xany;
            }
            lhs = null;
        }
        return lhs != null ? lhs : node.jjtGetChild(1).jjtAccept(this, data);
    }

    @Override
    protected Object visit(ASTSizeFunction node, Object data) {
        try {
            Object val = node.jjtGetChild(0).jjtAccept(this, data);
            return this.operators.size(node, val);
        }
        catch (JexlException xany) {
            return 0;
        }
    }

    @Override
    protected Object visit(ASTEmptyFunction node, Object data) {
        try {
            Object value = node.jjtGetChild(0).jjtAccept(this, data);
            return this.operators.empty(node, value);
        }
        catch (JexlException xany) {
            return true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected Object visitLexicalNode(JexlNode node, Object data) {
        this.block = new LexicalFrame(this.frame, null);
        try {
            Object object = node.jjtAccept(this, data);
            return object;
        }
        finally {
            this.block = this.block.pop();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected Object runClosure(Closure closure, Object data) {
        ASTJexlScript script = closure.getScript();
        this.block = new LexicalFrame(this.frame, this.block).defineArgs();
        try {
            JexlNode body = script.jjtGetChild(script.jjtGetNumChildren() - 1);
            Object object = this.interpret(body);
            return object;
        }
        finally {
            this.block = this.block.pop();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected Object visit(ASTJexlScript script, Object data) {
        if (script instanceof ASTJexlLambda && !((ASTJexlLambda)script).isTopLevel()) {
            return new Closure(this, (ASTJexlLambda)script);
        }
        this.block = new LexicalFrame(this.frame, this.block).defineArgs();
        try {
            int numChildren = script.jjtGetNumChildren();
            Object result = null;
            for (int i = 0; i < numChildren; ++i) {
                JexlNode child = script.jjtGetChild(i);
                result = child.jjtAccept(this, data);
                this.cancelCheck(child);
            }
            Object object = result;
            return object;
        }
        finally {
            this.block = this.block.pop();
        }
    }

    @Override
    protected Object visit(ASTReferenceExpression node, Object data) {
        return node.jjtGetChild(0).jjtAccept(this, data);
    }

    @Override
    protected Object visit(ASTIdentifier identifier, Object data) {
        this.cancelCheck(identifier);
        return data != null ? this.getAttribute(data, identifier.getName(), identifier) : this.getVariable(this.frame, this.block, identifier);
    }

    @Override
    protected Object visit(ASTArrayAccess node, Object data) {
        Object object = data;
        int numChildren = node.jjtGetNumChildren();
        for (int i = 0; i < numChildren; ++i) {
            JexlNode nindex = node.jjtGetChild(i);
            if (object == null) {
                return this.unsolvableProperty(nindex, this.stringifyProperty(nindex), false, null);
            }
            Object index = nindex.jjtAccept(this, null);
            this.cancelCheck(node);
            object = this.getAttribute(object, index, nindex);
        }
        return object;
    }

    private Object evalIdentifier(ASTIdentifierAccess node) {
        if (!(node instanceof ASTIdentifierAccessJxlt)) {
            return node.getIdentifier();
        }
        ASTIdentifierAccessJxlt accessJxlt = (ASTIdentifierAccessJxlt)node;
        String src = node.getName();
        JxltEngine.Exception cause = null;
        TemplateEngine.TemplateExpression expr = (TemplateEngine.TemplateExpression)accessJxlt.getExpression();
        try {
            Object name;
            if (expr == null) {
                TemplateEngine jxlt = this.jexl.jxlt();
                expr = jxlt.parseExpression(node.jexlInfo(), src, this.frame != null ? this.frame.getScope() : null);
                accessJxlt.setExpression(expr);
            }
            if (expr != null && (name = expr.evaluate(this.frame, this.context)) != null) {
                Integer id = ASTIdentifierAccess.parseIdentifier(name.toString());
                return id != null ? id : name;
            }
        }
        catch (JxltEngine.Exception xjxlt) {
            cause = xjxlt;
        }
        return node.isSafe() ? null : this.unsolvableProperty(node, src, true, cause);
    }

    @Override
    protected Object visit(ASTIdentifierAccess node, Object data) {
        if (data == null) {
            return null;
        }
        Object id = this.evalIdentifier(node);
        return this.getAttribute(data, id, node);
    }

    @Override
    protected Object visit(ASTReference node, Object data) {
        this.cancelCheck(node);
        int numChildren = node.jjtGetNumChildren();
        JexlNode parent = node.jjtGetParent();
        Object object = null;
        JexlNode objectNode = null;
        JexlNode ptyNode = null;
        StringBuilder ant = null;
        boolean antish = !(parent instanceof ASTReference);
        int v = 1;
        block0: for (int c = 0; c < numChildren; ++c) {
            JexlNode child;
            objectNode = node.jjtGetChild(c);
            if (objectNode instanceof ASTMethodNode) {
                antish = false;
                if (object == null) {
                    if (ant == null || !((child = objectNode.jjtGetChild(0)) instanceof ASTIdentifierAccess)) break;
                    int alen = ant.length();
                    ant.append('.');
                    ant.append(((ASTIdentifierAccess)child).getName());
                    object = this.context.get(ant.toString());
                    if (object != null) {
                        object = this.visit((ASTMethodNode)objectNode, object, this.context);
                        continue;
                    }
                    ant.delete(alen, ant.length());
                    ptyNode = objectNode;
                    break;
                }
            } else if (objectNode instanceof ASTArrayAccess) {
                antish = false;
                if (object == null) {
                    ptyNode = objectNode;
                    break;
                }
            }
            object = objectNode.jjtAccept(this, object);
            this.cancelCheck(node);
            if (object != null) {
                antish = false;
                continue;
            }
            if (antish) {
                if (ant == null) {
                    JexlNode first = node.jjtGetChild(0);
                    if (!(first instanceof ASTIdentifier)) {
                        ptyNode = objectNode;
                        break;
                    }
                    ASTIdentifier afirst = (ASTIdentifier)first;
                    ant = new StringBuilder(afirst.getName());
                    if (!this.options.isAntish()) {
                        antish = false;
                        continue;
                    }
                    if (c == 0) continue;
                }
                while (v <= c) {
                    child = node.jjtGetChild(v);
                    if (!(child instanceof ASTIdentifierAccess)) {
                        ptyNode = objectNode;
                        break block0;
                    }
                    ASTIdentifierAccess achild = (ASTIdentifierAccess)child;
                    if (achild.isSafe() || achild.isExpression()) break block0;
                    ant.append('.');
                    ant.append(achild.getName());
                    ++v;
                }
                object = this.context.get(ant.toString());
                continue;
            }
            if (c == numChildren - 1) continue;
            ptyNode = objectNode;
            break;
        }
        if (object == null) {
            if (ptyNode != null) {
                if (ptyNode.isSafeLhs(this.isSafe())) {
                    return null;
                }
                if (ant != null) {
                    String aname;
                    boolean defined = this.isVariableDefined(this.frame, this.block, aname = ant.toString());
                    return this.unsolvableVariable(node, aname, !defined);
                }
                return this.unsolvableProperty(node, this.stringifyProperty(ptyNode), ptyNode == objectNode, null);
            }
            if (antish) {
                if (node.isSafeLhs(this.isSafe())) {
                    return null;
                }
                String aname = ant != null ? ant.toString() : "?";
                boolean defined = this.isVariableDefined(this.frame, this.block, aname);
                if (!(!defined || this.arithmetic.isStrict() && node.jjtGetParent().isStrictOperator())) {
                    return null;
                }
                return this.unsolvableVariable(node, aname, !defined);
            }
        }
        return object;
    }

    @Override
    protected Object visit(ASTAssignment node, Object data) {
        return this.executeAssign(node, null, data);
    }

    @Override
    protected Object visit(ASTSetAddNode node, Object data) {
        return this.executeAssign(node, JexlOperator.SELF_ADD, data);
    }

    @Override
    protected Object visit(ASTSetSubNode node, Object data) {
        return this.executeAssign(node, JexlOperator.SELF_SUBTRACT, data);
    }

    @Override
    protected Object visit(ASTSetMultNode node, Object data) {
        return this.executeAssign(node, JexlOperator.SELF_MULTIPLY, data);
    }

    @Override
    protected Object visit(ASTSetDivNode node, Object data) {
        return this.executeAssign(node, JexlOperator.SELF_DIVIDE, data);
    }

    @Override
    protected Object visit(ASTSetModNode node, Object data) {
        return this.executeAssign(node, JexlOperator.SELF_MOD, data);
    }

    @Override
    protected Object visit(ASTSetAndNode node, Object data) {
        return this.executeAssign(node, JexlOperator.SELF_AND, data);
    }

    @Override
    protected Object visit(ASTSetOrNode node, Object data) {
        return this.executeAssign(node, JexlOperator.SELF_OR, data);
    }

    @Override
    protected Object visit(ASTSetXorNode node, Object data) {
        return this.executeAssign(node, JexlOperator.SELF_XOR, data);
    }

    protected Object executeAssign(JexlNode node, JexlOperator assignop, Object data) {
        Object self;
        ASTIdentifierAccess propertyId;
        int c;
        this.cancelCheck(node);
        JexlNode left = node.jjtGetChild(0);
        ASTIdentifier var = null;
        Object object = null;
        int symbol = -1;
        if (left instanceof ASTIdentifier && (symbol = (var = (ASTIdentifier)left).getSymbol()) >= 0 && this.options.isLexical()) {
            if (var instanceof ASTVar) {
                if (!this.defineVariable((ASTVar)var, this.block)) {
                    return this.redefinedVariable(var, var.getName());
                }
            } else if (this.options.isLexicalShade() && var.isShaded()) {
                return this.undefinedVariable(var, var.getName());
            }
        }
        boolean antish = this.options.isAntish();
        int last = left.jjtGetNumChildren() - 1;
        Object right = node.jjtGetChild(1).jjtAccept(this, data);
        if (var != null) {
            if (symbol >= 0) {
                if (last < 0) {
                    Object self2;
                    if (assignop != null && (right = this.operators.tryAssignOverload(node, assignop, self2 = this.getVariable(this.frame, this.block, var), right)) == JexlOperator.ASSIGN) {
                        return self2;
                    }
                    this.frame.set(symbol, right);
                    if (right instanceof Closure) {
                        ((Closure)right).setCaptured(symbol, right);
                    }
                    return right;
                }
                object = this.getVariable(this.frame, this.block, var);
                antish = false;
            } else {
                if (last < 0) {
                    Object self3;
                    if (assignop != null && (right = this.operators.tryAssignOverload(node, assignop, self3 = this.context.get(var.getName()), right)) == JexlOperator.ASSIGN) {
                        return self3;
                    }
                    this.setContextVariable(node, var.getName(), right);
                    return right;
                }
                object = this.context.get(var.getName());
                if (object != null) {
                    antish = false;
                }
            }
        } else if (!(left instanceof ASTReference)) {
            throw new JexlException(left, "illegal assignment form 0");
        }
        JexlNode objectNode = null;
        StringBuilder ant = null;
        int v = 1;
        int n = c = symbol >= 0 ? 1 : 0;
        block0: while (c < last) {
            objectNode = left.jjtGetChild(c);
            object = objectNode.jjtAccept(this, object);
            if (object != null) {
                antish = false;
            } else if (antish) {
                if (ant == null) {
                    ASTIdentifier firstId;
                    JexlNode first = left.jjtGetChild(0);
                    ASTIdentifier aSTIdentifier = firstId = first instanceof ASTIdentifier ? (ASTIdentifier)first : null;
                    if (firstId == null || firstId.getSymbol() >= 0) {
                        antish = false;
                        break;
                    }
                    ant = new StringBuilder(firstId.getName());
                }
                while (v <= c) {
                    ASTIdentifierAccess aid;
                    JexlNode child = left.jjtGetChild(v);
                    ASTIdentifierAccess aSTIdentifierAccess = aid = child instanceof ASTIdentifierAccess ? (ASTIdentifierAccess)child : null;
                    if (aid == null || aid.isSafe() || aid.isExpression()) {
                        antish = false;
                        break block0;
                    }
                    ant.append('.');
                    ant.append(aid.getName());
                    ++v;
                }
                object = this.context.get(ant.toString());
            } else {
                throw new JexlException(objectNode, "illegal assignment form");
            }
            ++c;
        }
        Object property = null;
        JexlNode propertyNode = left.jjtGetChild(last);
        ASTIdentifierAccess aSTIdentifierAccess = propertyId = propertyNode instanceof ASTIdentifierAccess ? (ASTIdentifierAccess)propertyNode : null;
        if (propertyId != null) {
            if (antish && ant != null && object == null && !propertyId.isSafe() && !propertyId.isExpression()) {
                Object self4;
                if (last > 0) {
                    ant.append('.');
                }
                ant.append(propertyId.getName());
                if (assignop != null && (right = this.operators.tryAssignOverload(node, assignop, self4 = this.context.get(ant.toString()), right)) == JexlOperator.ASSIGN) {
                    return self4;
                }
                this.setContextVariable(propertyNode, ant.toString(), right);
                return right;
            }
            property = this.evalIdentifier(propertyId);
        } else if (propertyNode instanceof ASTArrayAccess) {
            int numChildren = propertyNode.jjtGetNumChildren() - 1;
            for (int i = 0; i < numChildren; ++i) {
                JexlNode nindex = propertyNode.jjtGetChild(i);
                Object index = nindex.jjtAccept(this, null);
                object = this.getAttribute(object, index, nindex);
            }
            propertyNode = propertyNode.jjtGetChild(numChildren);
            property = propertyNode.jjtAccept(this, null);
        } else {
            throw new JexlException(objectNode, "illegal assignment form");
        }
        if (object == null) {
            return this.unsolvableProperty(objectNode, "<null>.<?>", true, null);
        }
        if (assignop != null && (right = this.operators.tryAssignOverload(node, assignop, self = this.getAttribute(object, property, propertyNode), right)) == JexlOperator.ASSIGN) {
            return self;
        }
        this.setAttribute(object, property, right, propertyNode);
        return right;
    }

    protected Object[] visit(ASTArguments node, Object data) {
        int argc = node.jjtGetNumChildren();
        Object[] argv = new Object[argc];
        for (int i = 0; i < argc; ++i) {
            argv[i] = node.jjtGetChild(i).jjtAccept(this, data);
        }
        return argv;
    }

    @Override
    protected Object visit(ASTMethodNode node, Object data) {
        return this.visit(node, null, data);
    }

    private Object visit(ASTMethodNode node, Object object, Object data) {
        Object method;
        JexlNode methodNode = node.jjtGetChild(0);
        if (methodNode instanceof ASTIdentifierAccess) {
            method = methodNode;
            if (object == null) {
                object = data;
                if (object == null) {
                    return node.isSafeLhs(this.isSafe()) ? null : this.unsolvableMethod(methodNode, "<null>.<?>(...)");
                }
            } else {
                method = object;
            }
        } else {
            method = methodNode.jjtAccept(this, data);
        }
        Object result = method;
        for (int a = 1; a < node.jjtGetNumChildren(); ++a) {
            if (result == null) {
                return node.isSafeLhs(this.isSafe()) ? null : this.unsolvableMethod(methodNode, "<?>.<null>(...)");
            }
            ASTArguments argNode = (ASTArguments)node.jjtGetChild(a);
            object = result = this.call(node, object, result, argNode);
        }
        return result;
    }

    @Override
    protected Object visit(ASTFunctionNode node, Object data) {
        ASTIdentifier functionNode = (ASTIdentifier)node.jjtGetChild(0);
        String nsid = functionNode.getNamespace();
        JexlContext namespace = nsid != null ? this.resolveNamespace(nsid, node) : this.context;
        ASTArguments argNode = (ASTArguments)node.jjtGetChild(1);
        return this.call(node, namespace, functionNode, argNode);
    }

    protected Object call(JexlNode node, Object target, Object functor, ASTArguments argNode) {
        String methodName;
        int symbol;
        this.cancelCheck(node);
        Object[] argv = this.visit(argNode, null);
        boolean cacheable = this.cache;
        boolean isavar = false;
        if (functor instanceof ASTIdentifier) {
            ASTIdentifier methodIdentifier = (ASTIdentifier)functor;
            symbol = methodIdentifier.getSymbol();
            methodName = methodIdentifier.getName();
            functor = null;
            if (target == this.context) {
                if (this.frame != null && this.frame.has(symbol)) {
                    functor = this.frame.get(symbol);
                    isavar = functor != null;
                } else if (this.context.has(methodName)) {
                    functor = this.context.get(methodName);
                    isavar = functor != null;
                }
                cacheable &= !isavar;
            }
        } else if (functor instanceof ASTIdentifierAccess) {
            methodName = ((ASTIdentifierAccess)functor).getName();
            symbol = -1;
            functor = null;
            cacheable = true;
        } else if (functor != null) {
            symbol = -2;
            methodName = null;
            cacheable = false;
        } else {
            if (!node.isSafeLhs(this.isSafe())) {
                return this.unsolvableMethod(node, "?(...)");
            }
            return null;
        }
        InterpreterBase.CallDispatcher call = new InterpreterBase.CallDispatcher(this, node, cacheable);
        try {
            Object eval = call.tryEval(target, methodName, argv);
            if (JexlEngine.TRY_FAILED != eval) {
                return eval;
            }
            boolean functorp = false;
            boolean narrow = false;
            while (true) {
                call.narrow = narrow;
                if (functor == null || functorp) {
                    if (call.isTargetMethod(target, methodName, argv)) {
                        return call.eval(methodName);
                    }
                    if (target == this.context) {
                        Object namespace = this.resolveNamespace(null, node);
                        if (namespace != null && namespace != this.context && call.isTargetMethod(namespace, methodName, argv)) {
                            return call.eval(methodName);
                        }
                        if (call.isArithmeticMethod(methodName, argv)) {
                            return call.eval(methodName);
                        }
                    } else {
                        JexlPropertyGet get;
                        Object[] pargv = this.functionArguments(target, narrow, argv);
                        if (call.isContextMethod(methodName, pargv)) {
                            return call.eval(methodName);
                        }
                        if (call.isArithmeticMethod(methodName, pargv)) {
                            return call.eval(methodName);
                        }
                        if (!narrow && (get = this.uberspect.getPropertyGet(target, methodName)) != null) {
                            functor = get.tryInvoke(target, methodName);
                            boolean bl = functorp = functor != null;
                        }
                    }
                }
                if (functor != null) {
                    Object[] pargv;
                    if (functor instanceof JexlScript) {
                        return ((JexlScript)functor).execute(this.context, argv);
                    }
                    if (functor instanceof JexlMethod) {
                        return ((JexlMethod)functor).invoke(target, argv);
                    }
                    String mCALL = "call";
                    if (call.isTargetMethod(functor, "call", argv)) {
                        return call.eval("call");
                    }
                    if (isavar && target == this.context) {
                        if (call.isContextMethod(methodName, argv)) {
                            return call.eval(methodName);
                        }
                        if (call.isArithmeticMethod(methodName, argv)) {
                            return call.eval(methodName);
                        }
                    }
                    if (call.isContextMethod("call", pargv = this.functionArguments(functor, narrow, argv))) {
                        return call.eval("call");
                    }
                    if (call.isArithmeticMethod("call", pargv)) {
                        return call.eval("call");
                    }
                }
                if (!narrow && this.arithmetic.narrowArguments(argv)) {
                    narrow = true;
                    continue;
                }
                break;
            }
        }
        catch (JexlException.Method eval) {
        }
        catch (JexlException.TryFailed xany) {
            throw this.invocationException(node, methodName, xany);
        }
        catch (JexlException xthru) {
            throw xthru;
        }
        catch (Exception xany) {
            throw this.invocationException(node, methodName, xany);
        }
        return node.isSafeLhs(this.isSafe()) ? null : this.unsolvableMethod(node, methodName, argv);
    }

    @Override
    protected Object visit(ASTConstructorNode node, Object data) {
        if (this.isCancelled()) {
            throw new JexlException.Cancel(node);
        }
        Object target = node.jjtGetChild(0).jjtAccept(this, data);
        int argc = node.jjtGetNumChildren() - 1;
        Object[] argv = new Object[argc];
        for (int i = 0; i < argc; ++i) {
            argv[i] = node.jjtGetChild(i + 1).jjtAccept(this, data);
        }
        try {
            Object eval;
            Object cached;
            boolean cacheable = this.cache;
            if (cacheable && (cached = node.jjtGetValue()) instanceof InterpreterBase.Funcall && JexlEngine.TRY_FAILED != (eval = ((InterpreterBase.Funcall)cached).tryInvoke(this, null, target, argv))) {
                return eval;
            }
            boolean narrow = false;
            JexlMethod ctor = null;
            InterpreterBase.Funcall funcall = null;
            while (true) {
                if ((ctor = this.uberspect.getConstructor(target, argv)) != null) {
                    if (!cacheable || !ctor.isCacheable()) break;
                    funcall = new InterpreterBase.Funcall(ctor, narrow);
                    break;
                }
                Object[] nargv = this.callArguments(this.context, narrow, argv);
                ctor = this.uberspect.getConstructor(target, nargv);
                if (ctor != null) {
                    if (cacheable && ctor.isCacheable()) {
                        funcall = new InterpreterBase.ContextualCtor(ctor, narrow);
                    }
                    argv = nargv;
                    break;
                }
                if (narrow || !this.arithmetic.narrowArguments(argv)) break;
                narrow = true;
            }
            if (ctor != null) {
                Object eval2 = ctor.invoke(target, argv);
                if (funcall != null) {
                    node.jjtSetValue(funcall);
                }
                return eval2;
            }
            String tstr = target != null ? target.toString() : "?";
            return this.unsolvableMethod(node, tstr, argv);
        }
        catch (JexlException.Method xmethod) {
            throw xmethod;
        }
        catch (Exception xany) {
            String tstr = target != null ? target.toString() : "?";
            throw this.invocationException(node, tstr, xany);
        }
    }

    @Override
    protected Object visit(ASTJxltLiteral node, Object data) {
        TemplateEngine.TemplateExpression tp = (TemplateEngine.TemplateExpression)node.jjtGetValue();
        if (tp == null) {
            TemplateEngine jxlt = this.jexl.jxlt();
            JexlInfo info = node.jexlInfo();
            if (this.block != null) {
                info = new JexlNode.Info(node, info);
            }
            tp = jxlt.parseExpression(info, node.getLiteral(), this.frame != null ? this.frame.getScope() : null);
            node.jjtSetValue(tp);
        }
        if (tp != null) {
            return tp.evaluate(this.frame, this.context);
        }
        return null;
    }

    @Override
    protected Object visit(ASTAnnotation node, Object data) {
        throw new UnsupportedOperationException(ASTAnnotation.class.getName() + ": Not supported.");
    }

    @Override
    protected Object visit(ASTAnnotatedStatement node, Object data) {
        return this.processAnnotation(node, 0, data);
    }

    protected Object processAnnotation(ASTAnnotatedStatement stmt, int index, Object data) {
        Object result;
        int last = stmt.jjtGetNumChildren() - 1;
        if (index == last) {
            JexlNode cblock = stmt.jjtGetChild(last);
            JexlArithmetic jexla = this.arithmetic.options(this.context);
            if (jexla == this.arithmetic) {
                return cblock.jjtAccept(this, data);
            }
            if (!this.arithmetic.getClass().equals(jexla.getClass())) {
                this.logger.warn((Object)("expected arithmetic to be " + this.arithmetic.getClass().getSimpleName() + ", got " + jexla.getClass().getSimpleName()));
            }
            Interpreter ii = new Interpreter(this, jexla);
            Object r = cblock.jjtAccept(ii, data);
            if (ii.isCancelled()) {
                this.cancel();
            }
            return r;
        }
        AnnotatedCall jstmt = new AnnotatedCall(stmt, index + 1, data);
        ASTAnnotation anode = (ASTAnnotation)stmt.jjtGetChild(index);
        String aname = anode.getName();
        Object[] argv = anode.jjtGetNumChildren() > 0 ? this.visit((ASTArguments)anode.jjtGetChild(0), null) : null;
        try {
            result = this.processAnnotation(aname, argv, jstmt);
            if (!jstmt.isProcessed()) {
                return this.annotationError(anode, aname, null);
            }
        }
        catch (JexlException xany) {
            throw xany;
        }
        catch (Exception xany) {
            return this.annotationError(anode, aname, xany);
        }
        if (result instanceof JexlException) {
            throw (JexlException)result;
        }
        return result;
    }

    protected Object processAnnotation(String annotation, Object[] args, Callable<Object> stmt) throws Exception {
        return this.context instanceof JexlContext.AnnotationProcessor ? ((JexlContext.AnnotationProcessor)((Object)this.context)).processAnnotation(annotation, args, stmt) : stmt.call();
    }

    public class AnnotatedCall
    implements Callable<Object> {
        private final ASTAnnotatedStatement stmt;
        private final int index;
        private final Object data;
        private boolean processed = false;

        AnnotatedCall(ASTAnnotatedStatement astmt, int aindex, Object adata) {
            this.stmt = astmt;
            this.index = aindex;
            this.data = adata;
        }

        @Override
        public Object call() throws Exception {
            this.processed = true;
            try {
                return Interpreter.this.processAnnotation(this.stmt, this.index, this.data);
            }
            catch (JexlException.Break | JexlException.Continue | JexlException.Return xreturn) {
                return xreturn;
            }
        }

        public boolean isProcessed() {
            return this.processed;
        }

        public Object getStatement() {
            return this.stmt;
        }
    }
}

