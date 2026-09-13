/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlInfo;
import org.apache.commons.jexl3.JexlOptions;
import org.apache.commons.jexl3.JxltEngine;
import org.apache.commons.jexl3.internal.Closure;
import org.apache.commons.jexl3.internal.Engine;
import org.apache.commons.jexl3.internal.Frame;
import org.apache.commons.jexl3.internal.Interpreter;
import org.apache.commons.jexl3.internal.LexicalFrame;
import org.apache.commons.jexl3.internal.TemplateEngine;
import org.apache.commons.jexl3.internal.TemplateScript;
import org.apache.commons.jexl3.introspection.JexlMethod;
import org.apache.commons.jexl3.introspection.JexlUberspect;
import org.apache.commons.jexl3.parser.ASTArguments;
import org.apache.commons.jexl3.parser.ASTFunctionNode;
import org.apache.commons.jexl3.parser.ASTIdentifier;
import org.apache.commons.jexl3.parser.ASTJexlLambda;
import org.apache.commons.jexl3.parser.ASTJexlScript;
import org.apache.commons.jexl3.parser.JexlNode;

public class TemplateInterpreter
extends Interpreter {
    private final TemplateEngine.TemplateExpression[] exprs;
    private final Writer writer;

    TemplateInterpreter(Arguments args) {
        super(args.jexl, args.options, args.jcontext, args.jframe);
        this.exprs = args.expressions;
        this.writer = args.out;
        this.block = new LexicalFrame(this.frame, null);
    }

    public void include(JxltEngine.Template script, Object ... args) {
        script.evaluate(this.context, this.writer, args);
    }

    public void print(int e) {
        if (e < 0 || e >= this.exprs.length) {
            return;
        }
        TemplateEngine.TemplateExpression expr = this.exprs[e];
        if (expr.isDeferred()) {
            expr = expr.prepare(this.frame, this.context);
        }
        if (expr instanceof TemplateEngine.CompositeExpression) {
            this.printComposite((TemplateEngine.CompositeExpression)expr);
        } else {
            this.doPrint(expr.getInfo(), expr.evaluate(this));
        }
    }

    private void printComposite(TemplateEngine.CompositeExpression composite) {
        TemplateEngine.TemplateExpression[] cexprs;
        for (TemplateEngine.TemplateExpression cexpr : cexprs = composite.exprs) {
            Object value = cexpr.evaluate(this);
            this.doPrint(cexpr.getInfo(), value);
        }
    }

    private void doPrint(JexlInfo info, Object arg) {
        try {
            if (this.writer != null) {
                if (arg instanceof CharSequence) {
                    this.writer.write(arg.toString());
                } else if (arg != null) {
                    Object[] value = new Object[]{arg};
                    JexlUberspect uber = this.jexl.getUberspect();
                    JexlMethod method = uber.getMethod(this.writer, "print", value);
                    if (method != null) {
                        method.invoke(this.writer, value);
                    } else {
                        this.writer.write(arg.toString());
                    }
                }
            }
        }
        catch (IOException xio) {
            throw TemplateEngine.createException(info, "call print", null, xio);
        }
        catch (Exception xany) {
            throw TemplateEngine.createException(info, "invoke print", null, xany);
        }
    }

    @Override
    protected Object resolveNamespace(String prefix, JexlNode node) {
        return "jexl".equals(prefix) ? this : super.resolveNamespace(prefix, node);
    }

    @Override
    protected Object visit(ASTIdentifier node, Object data) {
        String name = node.getName();
        if ("$jexl".equals(name)) {
            return this.writer;
        }
        return super.visit(node, data);
    }

    @Override
    protected Object visit(ASTFunctionNode node, Object data) {
        ASTIdentifier functionNode;
        int argc = node.jjtGetNumChildren();
        if (argc == 2 && "jexl".equals((functionNode = (ASTIdentifier)node.jjtGetChild(0)).getNamespace())) {
            Object argv;
            String functionName = functionNode.getName();
            ASTArguments argNode = (ASTArguments)node.jjtGetChild(1);
            if ("print".equals(functionName) && (argv = this.visit(argNode, null)) != null && ((Object[])argv).length > 0 && argv[0] instanceof Number) {
                this.print(((Number)argv[0]).intValue());
                return null;
            }
            if ("include".equals(functionName) && (argv = this.visit(argNode, null)) != null && ((Object[])argv).length > 0 && argv[0] instanceof TemplateScript) {
                TemplateScript script = (TemplateScript)argv[0];
                argv = ((Object[])argv).length > 1 ? Arrays.copyOfRange(argv, 1, ((Object[])argv).length) : null;
                this.include(script, argv);
                return null;
            }
            throw new JxltEngine.Exception(node.jexlInfo(), "no callable template function " + functionName, null);
        }
        return super.visit(node, data);
    }

    @Override
    protected Object visit(ASTJexlScript script, Object data) {
        if (script instanceof ASTJexlLambda && !((ASTJexlLambda)script).isTopLevel()) {
            return new Closure(this, (ASTJexlLambda)script){

                @Override
                protected Interpreter createInterpreter(JexlContext context, Frame local) {
                    JexlOptions opts = this.jexl.options(this.script, context);
                    Arguments targs = new Arguments(this.jexl).context(context).options(opts).frame(local).expressions(TemplateInterpreter.this.exprs).writer(TemplateInterpreter.this.writer);
                    return new TemplateInterpreter(targs);
                }
            };
        }
        int numChildren = script.jjtGetNumChildren();
        Object result = null;
        for (int i = 0; i < numChildren; ++i) {
            JexlNode child = script.jjtGetChild(i);
            result = child.jjtAccept(this, data);
            this.cancelCheck(child);
        }
        return result;
    }

    static class Arguments {
        Engine jexl;
        JexlOptions options;
        JexlContext jcontext;
        Frame jframe;
        TemplateEngine.TemplateExpression[] expressions;
        Writer out;

        Arguments(Engine e) {
            this.jexl = e;
        }

        Arguments options(JexlOptions o) {
            this.options = o;
            return this;
        }

        Arguments context(JexlContext j) {
            this.jcontext = j;
            return this;
        }

        Arguments frame(Frame f) {
            this.jframe = f;
            return this;
        }

        Arguments expressions(TemplateEngine.TemplateExpression[] e) {
            this.expressions = e;
            return this;
        }

        Arguments writer(Writer o) {
            this.out = o;
            return this;
        }
    }
}

