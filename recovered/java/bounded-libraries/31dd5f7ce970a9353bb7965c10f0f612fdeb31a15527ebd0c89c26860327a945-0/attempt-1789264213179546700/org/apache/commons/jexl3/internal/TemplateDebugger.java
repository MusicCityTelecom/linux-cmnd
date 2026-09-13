/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal;

import org.apache.commons.jexl3.JxltEngine;
import org.apache.commons.jexl3.internal.Debugger;
import org.apache.commons.jexl3.internal.TemplateEngine;
import org.apache.commons.jexl3.internal.TemplateScript;
import org.apache.commons.jexl3.parser.ASTBlock;
import org.apache.commons.jexl3.parser.ASTFunctionNode;
import org.apache.commons.jexl3.parser.ASTIdentifier;
import org.apache.commons.jexl3.parser.ASTJexlScript;
import org.apache.commons.jexl3.parser.ASTNumberLiteral;
import org.apache.commons.jexl3.parser.JexlNode;

public class TemplateDebugger
extends Debugger {
    private ASTJexlScript script;
    private TemplateEngine.TemplateExpression[] exprs;

    @Override
    public void reset() {
        super.reset();
        this.exprs = null;
        this.script = null;
    }

    public boolean debug(JxltEngine.Expression je) {
        if (je instanceof TemplateEngine.TemplateExpression) {
            TemplateEngine.TemplateExpression te = (TemplateEngine.TemplateExpression)je;
            return this.visit(te, (Object)this) != null;
        }
        return false;
    }

    public boolean debug(JxltEngine.Template jt) {
        if (!(jt instanceof TemplateScript)) {
            return false;
        }
        TemplateScript ts = (TemplateScript)jt;
        this.exprs = ts.getExpressions() == null ? new TemplateEngine.TemplateExpression[]{} : ts.getExpressions();
        this.script = ts.getScript();
        this.start = 0;
        this.end = 0;
        this.indentLevel = 0;
        this.builder.setLength(0);
        this.cause = this.script;
        int num = this.script.jjtGetNumChildren();
        for (int i = 0; i < num; ++i) {
            JexlNode child = this.script.jjtGetChild(i);
            this.acceptStatement(child, null);
        }
        if (this.builder.length() > 0 && this.builder.charAt(this.builder.length() - 1) != '\n') {
            this.builder.append('\n');
        }
        this.end = this.builder.length();
        return this.end > 0;
    }

    @Override
    protected Object visit(ASTBlock node, Object data) {
        int i;
        if (this.exprs == null) {
            return super.visit(node, data);
        }
        this.builder.append('{');
        if (this.indent > 0) {
            ++this.indentLevel;
            this.builder.append('\n');
        } else {
            this.builder.append(' ');
        }
        int num = node.jjtGetNumChildren();
        for (i = 0; i < num; ++i) {
            JexlNode child = node.jjtGetChild(i);
            this.acceptStatement(child, data);
        }
        this.newJexlLine();
        if (this.indent > 0) {
            --this.indentLevel;
            for (i = 0; i < this.indentLevel; ++i) {
                for (int s = 0; s < this.indent; ++s) {
                    this.builder.append(' ');
                }
            }
        }
        this.builder.append('}');
        return data;
    }

    @Override
    protected Object acceptStatement(JexlNode child, Object data) {
        if (this.exprs == null) {
            return super.acceptStatement(child, data);
        }
        TemplateEngine.TemplateExpression te = this.getPrintStatement(child);
        if (te != null) {
            this.newJxltLine();
            return this.visit(te, data);
        }
        this.newJexlLine();
        return super.acceptStatement(child, data);
    }

    private TemplateEngine.TemplateExpression getPrintStatement(JexlNode child) {
        if (this.exprs != null && child instanceof ASTFunctionNode) {
            ASTNumberLiteral exprn;
            int n;
            ASTFunctionNode node = (ASTFunctionNode)child;
            ASTIdentifier ns = (ASTIdentifier)node.jjtGetChild(0);
            JexlNode args = node.jjtGetChild(1);
            if ("jexl".equals(ns.getNamespace()) && "print".equals(ns.getName()) && args.jjtGetNumChildren() == 1 && args.jjtGetChild(0) instanceof ASTNumberLiteral && (n = (exprn = (ASTNumberLiteral)args.jjtGetChild(0)).getLiteral().intValue()) >= 0 && n < this.exprs.length) {
                return this.exprs[n];
            }
        }
        return null;
    }

    private void newJexlLine() {
        int length = this.builder.length();
        if (length == 0) {
            this.builder.append("$$ ");
        } else {
            for (int i = length - 1; i >= 0; --i) {
                char c = this.builder.charAt(i);
                switch (c) {
                    case '\n': {
                        this.builder.append("$$ ");
                        return;
                    }
                    case '}': {
                        this.builder.append("\n$$ ");
                        return;
                    }
                    case ' ': 
                    case ';': {
                        return;
                    }
                }
            }
        }
    }

    private void newJxltLine() {
        int length = this.builder.length();
        for (int i = length - 1; i >= 0; --i) {
            char c = this.builder.charAt(i);
            switch (c) {
                case '\n': 
                case ';': {
                    return;
                }
                case '}': {
                    this.builder.append('\n');
                    return;
                }
            }
        }
    }

    private Object visit(TemplateEngine.TemplateExpression expr, Object data) {
        Object r;
        switch (expr.getType()) {
            case CONSTANT: {
                r = this.visit((TemplateEngine.ConstantExpression)expr, data);
                break;
            }
            case IMMEDIATE: {
                r = this.visit((TemplateEngine.ImmediateExpression)expr, data);
                break;
            }
            case DEFERRED: {
                r = this.visit((TemplateEngine.DeferredExpression)expr, data);
                break;
            }
            case NESTED: {
                r = this.visit((TemplateEngine.NestedExpression)expr, data);
                break;
            }
            case COMPOSITE: {
                r = this.visit((TemplateEngine.CompositeExpression)expr, data);
                break;
            }
            default: {
                r = null;
            }
        }
        return r;
    }

    private Object visit(TemplateEngine.ConstantExpression expr, Object data) {
        expr.asString(this.builder);
        return data;
    }

    private Object visit(TemplateEngine.ImmediateExpression expr, Object data) {
        this.builder.append(expr.isImmediate() ? (char)'$' : '#');
        this.builder.append('{');
        super.accept(expr.node, data);
        this.builder.append('}');
        return data;
    }

    private Object visit(TemplateEngine.DeferredExpression expr, Object data) {
        this.builder.append(expr.isImmediate() ? (char)'$' : '#');
        this.builder.append('{');
        super.accept(expr.node, data);
        this.builder.append('}');
        return data;
    }

    private Object visit(TemplateEngine.NestedExpression expr, Object data) {
        super.accept(expr.node, data);
        return data;
    }

    private Object visit(TemplateEngine.CompositeExpression expr, Object data) {
        for (TemplateEngine.TemplateExpression ce : expr.exprs) {
            this.visit(ce, data);
        }
        return data;
    }
}

