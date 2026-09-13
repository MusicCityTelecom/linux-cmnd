/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal;

import java.util.regex.Pattern;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.JexlInfo;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.internal.Script;
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
import org.apache.commons.jexl3.parser.ParserVisitor;
import org.apache.commons.jexl3.parser.StringParser;

public class Debugger
extends ParserVisitor
implements JexlInfo.Detail {
    protected final StringBuilder builder = new StringBuilder();
    protected JexlNode cause = null;
    protected int start = 0;
    protected int end = 0;
    protected int indentLevel = 0;
    protected int indent = 2;
    protected int depth = Integer.MAX_VALUE;
    protected static final Pattern QUOTED_IDENTIFIER = Pattern.compile("[\\s]|[\\p{Punct}&&[^@#\\$_]]");

    public void reset() {
        this.builder.setLength(0);
        this.cause = null;
        this.start = 0;
        this.end = 0;
        this.indentLevel = 0;
        this.indent = 2;
        this.depth = Integer.MAX_VALUE;
    }

    public boolean debug(JexlExpression jscript) {
        if (jscript instanceof Script) {
            return this.debug(((Script)jscript).script);
        }
        return false;
    }

    public boolean debug(JexlScript jscript) {
        if (jscript instanceof Script) {
            return this.debug(((Script)jscript).script);
        }
        return false;
    }

    public boolean debug(JexlNode node) {
        return this.debug(node, true);
    }

    public boolean debug(JexlNode node, boolean r) {
        this.start = 0;
        this.end = 0;
        this.indentLevel = 0;
        if (node != null) {
            this.builder.setLength(0);
            this.cause = node;
            JexlNode walk = node;
            if (r) {
                while (walk.jjtGetParent() != null) {
                    walk = walk.jjtGetParent();
                }
            }
            this.accept(walk, null);
        }
        return this.end > 0;
    }

    @Override
    public String toString() {
        return this.builder.toString();
    }

    public String data(JexlNode node) {
        this.start = 0;
        this.end = 0;
        this.indentLevel = 0;
        if (node != null) {
            this.builder.setLength(0);
            this.cause = node;
            this.accept(node, null);
        }
        return this.builder.toString();
    }

    @Override
    public int start() {
        return this.start;
    }

    @Override
    public int end() {
        return this.end;
    }

    public void setIndentation(int level) {
        this.indentation(level);
    }

    public Debugger indentation(int level) {
        this.indent = Math.max(level, 0);
        this.indentLevel = 0;
        return this;
    }

    public Debugger depth(int rdepth) {
        this.depth = rdepth;
        return this;
    }

    protected Object accept(JexlNode node, Object data) {
        if (this.depth <= 0) {
            this.builder.append("...");
            return data;
        }
        if (node == this.cause) {
            this.start = this.builder.length();
        }
        --this.depth;
        Object value = node.jjtAccept(this, data);
        ++this.depth;
        if (node == this.cause) {
            this.end = this.builder.length();
        }
        return value;
    }

    protected Object acceptStatement(JexlNode child, Object data) {
        JexlNode parent = child.jjtGetParent();
        if (this.indent > 0 && (parent instanceof ASTBlock || parent instanceof ASTJexlScript)) {
            for (int i = 0; i < this.indentLevel; ++i) {
                for (int s = 0; s < this.indent; ++s) {
                    this.builder.append(' ');
                }
            }
        }
        --this.depth;
        Object value = this.accept(child, data);
        ++this.depth;
        if (!(child instanceof ASTJexlScript || child instanceof ASTBlock || child instanceof ASTIfStatement || child instanceof ASTForeachStatement || child instanceof ASTWhileStatement || child instanceof ASTDoWhileStatement || child instanceof ASTAnnotation)) {
            this.builder.append(';');
            if (this.indent > 0) {
                this.builder.append('\n');
            } else {
                this.builder.append(' ');
            }
        }
        return value;
    }

    protected Object check(JexlNode node, String image, Object data) {
        if (node == this.cause) {
            this.start = this.builder.length();
        }
        if (image != null) {
            this.builder.append(image);
        } else {
            this.builder.append(node.toString());
        }
        if (node == this.cause) {
            this.end = this.builder.length();
        }
        return data;
    }

    protected Object infixChildren(JexlNode node, String infix, boolean paren, Object data) {
        int num = node.jjtGetNumChildren();
        if (paren) {
            this.builder.append('(');
        }
        for (int i = 0; i < num; ++i) {
            if (i > 0) {
                this.builder.append(infix);
            }
            this.accept(node.jjtGetChild(i), data);
        }
        if (paren) {
            this.builder.append(')');
        }
        return data;
    }

    protected Object prefixChild(JexlNode node, String prefix, Object data) {
        boolean paren = node.jjtGetChild(0).jjtGetNumChildren() > 1;
        this.builder.append(prefix);
        if (paren) {
            this.builder.append('(');
        }
        this.accept(node.jjtGetChild(0), data);
        if (paren) {
            this.builder.append(')');
        }
        return data;
    }

    @Override
    protected Object visit(ASTAddNode node, Object data) {
        return this.additiveNode(node, " + ", data);
    }

    @Override
    protected Object visit(ASTSubNode node, Object data) {
        return this.additiveNode(node, " - ", data);
    }

    protected Object additiveNode(JexlNode node, String op, Object data) {
        boolean paren = node.jjtGetParent() instanceof ASTMulNode || node.jjtGetParent() instanceof ASTDivNode || node.jjtGetParent() instanceof ASTModNode;
        int num = node.jjtGetNumChildren();
        if (paren) {
            this.builder.append('(');
        }
        this.accept(node.jjtGetChild(0), data);
        for (int i = 1; i < num; ++i) {
            this.builder.append(op);
            this.accept(node.jjtGetChild(i), data);
        }
        if (paren) {
            this.builder.append(')');
        }
        return data;
    }

    @Override
    protected Object visit(ASTAndNode node, Object data) {
        return this.infixChildren(node, " && ", false, data);
    }

    @Override
    protected Object visit(ASTArrayAccess node, Object data) {
        int num = node.jjtGetNumChildren();
        for (int i = 0; i < num; ++i) {
            this.builder.append('[');
            this.accept(node.jjtGetChild(i), data);
            this.builder.append(']');
        }
        return data;
    }

    @Override
    protected Object visit(ASTExtendedLiteral node, Object data) {
        this.builder.append("...");
        return data;
    }

    @Override
    protected Object visit(ASTArrayLiteral node, Object data) {
        int num = node.jjtGetNumChildren();
        this.builder.append("[ ");
        if (num > 0) {
            this.accept(node.jjtGetChild(0), data);
            for (int i = 1; i < num; ++i) {
                this.builder.append(", ");
                this.accept(node.jjtGetChild(i), data);
            }
        }
        this.builder.append(" ]");
        return data;
    }

    @Override
    protected Object visit(ASTRangeNode node, Object data) {
        return this.infixChildren(node, " .. ", false, data);
    }

    @Override
    protected Object visit(ASTAssignment node, Object data) {
        return this.infixChildren(node, " = ", false, data);
    }

    @Override
    protected Object visit(ASTBitwiseAndNode node, Object data) {
        return this.infixChildren(node, " & ", false, data);
    }

    @Override
    protected Object visit(ASTBitwiseComplNode node, Object data) {
        return this.prefixChild(node, "~", data);
    }

    @Override
    protected Object visit(ASTBitwiseOrNode node, Object data) {
        boolean paren = node.jjtGetParent() instanceof ASTBitwiseAndNode;
        return this.infixChildren(node, " | ", paren, data);
    }

    @Override
    protected Object visit(ASTBitwiseXorNode node, Object data) {
        boolean paren = node.jjtGetParent() instanceof ASTBitwiseAndNode;
        return this.infixChildren(node, " ^ ", paren, data);
    }

    @Override
    protected Object visit(ASTBlock node, Object data) {
        int i;
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
    protected Object visit(ASTDivNode node, Object data) {
        return this.infixChildren(node, " / ", false, data);
    }

    @Override
    protected Object visit(ASTEmptyFunction node, Object data) {
        this.builder.append("empty ");
        this.accept(node.jjtGetChild(0), data);
        return data;
    }

    @Override
    protected Object visit(ASTEQNode node, Object data) {
        return this.infixChildren(node, " == ", false, data);
    }

    @Override
    protected Object visit(ASTERNode node, Object data) {
        return this.infixChildren(node, " =~ ", false, data);
    }

    @Override
    protected Object visit(ASTSWNode node, Object data) {
        return this.infixChildren(node, " =^ ", false, data);
    }

    @Override
    protected Object visit(ASTEWNode node, Object data) {
        return this.infixChildren(node, " =$ ", false, data);
    }

    @Override
    protected Object visit(ASTNSWNode node, Object data) {
        return this.infixChildren(node, " !^ ", false, data);
    }

    @Override
    protected Object visit(ASTNEWNode node, Object data) {
        return this.infixChildren(node, " !$ ", false, data);
    }

    @Override
    protected Object visit(ASTFalseNode node, Object data) {
        return this.check(node, "false", data);
    }

    @Override
    protected Object visit(ASTContinue node, Object data) {
        return this.check(node, "continue", data);
    }

    @Override
    protected Object visit(ASTBreak node, Object data) {
        return this.check(node, "break", data);
    }

    @Override
    protected Object visit(ASTForeachStatement node, Object data) {
        this.builder.append("for(");
        this.accept(node.jjtGetChild(0), data);
        this.builder.append(" : ");
        this.accept(node.jjtGetChild(1), data);
        this.builder.append(") ");
        if (node.jjtGetNumChildren() > 2) {
            this.acceptStatement(node.jjtGetChild(2), data);
        } else {
            this.builder.append(';');
        }
        return data;
    }

    @Override
    protected Object visit(ASTGENode node, Object data) {
        return this.infixChildren(node, " >= ", false, data);
    }

    @Override
    protected Object visit(ASTGTNode node, Object data) {
        return this.infixChildren(node, " > ", false, data);
    }

    protected boolean needQuotes(String str) {
        return QUOTED_IDENTIFIER.matcher(str).find() || "size".equals(str) || "empty".equals(str);
    }

    @Override
    protected Object visit(ASTIdentifier node, Object data) {
        String ns = node.getNamespace();
        String image = StringParser.escapeIdentifier(node.getName());
        if (ns == null) {
            return this.check(node, image, data);
        }
        String nsid = StringParser.escapeIdentifier(ns) + ":" + image;
        return this.check(node, nsid, data);
    }

    @Override
    protected Object visit(ASTIdentifierAccess node, Object data) {
        this.builder.append(node.isSafe() ? "?." : ".");
        String image = node.getName();
        if (node.isExpression()) {
            this.builder.append('`');
            this.builder.append(image.replace("`", "\\`"));
            this.builder.append('`');
        } else if (this.needQuotes(image)) {
            this.builder.append('\'');
            this.builder.append(image.replace("'", "\\'"));
            this.builder.append('\'');
        } else {
            this.builder.append(image);
        }
        return data;
    }

    @Override
    protected Object visit(ASTIfStatement node, Object data) {
        int numChildren = node.jjtGetNumChildren();
        this.builder.append("if (");
        this.accept(node.jjtGetChild(0), data);
        this.builder.append(") ");
        this.acceptStatement(node.jjtGetChild(1), data);
        for (int c = 2; c < numChildren - 1; c += 2) {
            this.builder.append(" else if (");
            this.accept(node.jjtGetChild(c), data);
            this.builder.append(") ");
            this.acceptStatement(node.jjtGetChild(c + 1), data);
        }
        if ((numChildren & 1) == 1) {
            this.builder.append(" else ");
            this.acceptStatement(node.jjtGetChild(numChildren - 1), data);
        }
        return data;
    }

    @Override
    protected Object visit(ASTNumberLiteral node, Object data) {
        return this.check(node, node.toString(), data);
    }

    protected String visitParameter(String p, Object data) {
        return p;
    }

    @Override
    protected Object visit(ASTJexlScript node, Object data) {
        int num;
        if (node instanceof ASTJexlLambda) {
            JexlNode parent = node.jjtGetParent();
            boolean named = parent instanceof ASTAssignment;
            if (named) {
                this.builder.append("function");
            }
            this.builder.append('(');
            String[] params = node.getParameters();
            if (params != null && params.length > 0) {
                this.builder.append(this.visitParameter(params[0], data));
                for (int p = 1; p < params.length; ++p) {
                    this.builder.append(", ");
                    this.builder.append(this.visitParameter(params[p], data));
                }
            }
            this.builder.append(')');
            if (named) {
                this.builder.append(' ');
            } else {
                this.builder.append("->");
            }
        }
        if ((num = node.jjtGetNumChildren()) == 1 && !(node instanceof ASTJexlLambda)) {
            data = this.accept(node.jjtGetChild(0), data);
        } else {
            for (int i = 0; i < num; ++i) {
                JexlNode child = node.jjtGetChild(i);
                this.acceptStatement(child, data);
            }
        }
        return data;
    }

    @Override
    protected Object visit(ASTLENode node, Object data) {
        return this.infixChildren(node, " <= ", false, data);
    }

    @Override
    protected Object visit(ASTLTNode node, Object data) {
        return this.infixChildren(node, " < ", false, data);
    }

    @Override
    protected Object visit(ASTMapEntry node, Object data) {
        this.accept(node.jjtGetChild(0), data);
        this.builder.append(" : ");
        this.accept(node.jjtGetChild(1), data);
        return data;
    }

    @Override
    protected Object visit(ASTSetLiteral node, Object data) {
        int num = node.jjtGetNumChildren();
        this.builder.append("{ ");
        if (num > 0) {
            this.accept(node.jjtGetChild(0), data);
            for (int i = 1; i < num; ++i) {
                this.builder.append(",");
                this.accept(node.jjtGetChild(i), data);
            }
        }
        this.builder.append(" }");
        return data;
    }

    @Override
    protected Object visit(ASTMapLiteral node, Object data) {
        int num = node.jjtGetNumChildren();
        this.builder.append("{ ");
        if (num > 0) {
            this.accept(node.jjtGetChild(0), data);
            for (int i = 1; i < num; ++i) {
                this.builder.append(",");
                this.accept(node.jjtGetChild(i), data);
            }
        } else {
            this.builder.append(':');
        }
        this.builder.append(" }");
        return data;
    }

    @Override
    protected Object visit(ASTConstructorNode node, Object data) {
        int num = node.jjtGetNumChildren();
        this.builder.append("new(");
        if (num > 0) {
            this.accept(node.jjtGetChild(0), data);
            for (int i = 1; i < num; ++i) {
                this.builder.append(", ");
                this.accept(node.jjtGetChild(i), data);
            }
        }
        this.builder.append(")");
        return data;
    }

    @Override
    protected Object visit(ASTFunctionNode node, Object data) {
        int num = node.jjtGetNumChildren();
        if (num == 3) {
            this.accept(node.jjtGetChild(0), data);
            this.builder.append(":");
            this.accept(node.jjtGetChild(1), data);
            this.accept(node.jjtGetChild(2), data);
        } else if (num == 2) {
            this.accept(node.jjtGetChild(0), data);
            this.accept(node.jjtGetChild(1), data);
        }
        return data;
    }

    @Override
    protected Object visit(ASTMethodNode node, Object data) {
        int num = node.jjtGetNumChildren();
        if (num == 2) {
            this.accept(node.jjtGetChild(0), data);
            this.accept(node.jjtGetChild(1), data);
        }
        return data;
    }

    @Override
    protected Object visit(ASTArguments node, Object data) {
        int num = node.jjtGetNumChildren();
        this.builder.append("(");
        if (num > 0) {
            this.accept(node.jjtGetChild(0), data);
            for (int i = 1; i < num; ++i) {
                this.builder.append(", ");
                this.accept(node.jjtGetChild(i), data);
            }
        }
        this.builder.append(")");
        return data;
    }

    @Override
    protected Object visit(ASTModNode node, Object data) {
        return this.infixChildren(node, " % ", false, data);
    }

    @Override
    protected Object visit(ASTMulNode node, Object data) {
        return this.infixChildren(node, " * ", false, data);
    }

    @Override
    protected Object visit(ASTNENode node, Object data) {
        return this.infixChildren(node, " != ", false, data);
    }

    @Override
    protected Object visit(ASTNRNode node, Object data) {
        return this.infixChildren(node, " !~ ", false, data);
    }

    @Override
    protected Object visit(ASTNotNode node, Object data) {
        this.builder.append("!");
        this.accept(node.jjtGetChild(0), data);
        return data;
    }

    @Override
    protected Object visit(ASTNullLiteral node, Object data) {
        this.check(node, "null", data);
        return data;
    }

    @Override
    protected Object visit(ASTOrNode node, Object data) {
        boolean paren = node.jjtGetParent() instanceof ASTAndNode;
        return this.infixChildren(node, " || ", paren, data);
    }

    @Override
    protected Object visit(ASTReference node, Object data) {
        int num = node.jjtGetNumChildren();
        for (int i = 0; i < num; ++i) {
            this.accept(node.jjtGetChild(i), data);
        }
        return data;
    }

    @Override
    protected Object visit(ASTReferenceExpression node, Object data) {
        JexlNode first = node.jjtGetChild(0);
        this.builder.append('(');
        this.accept(first, data);
        this.builder.append(')');
        int num = node.jjtGetNumChildren();
        for (int i = 1; i < num; ++i) {
            this.builder.append("[");
            this.accept(node.jjtGetChild(i), data);
            this.builder.append("]");
        }
        return data;
    }

    @Override
    protected Object visit(ASTReturnStatement node, Object data) {
        this.builder.append("return ");
        this.accept(node.jjtGetChild(0), data);
        return data;
    }

    @Override
    protected Object visit(ASTSizeFunction node, Object data) {
        this.builder.append("size ");
        this.accept(node.jjtGetChild(0), data);
        return data;
    }

    @Override
    protected Object visit(ASTStringLiteral node, Object data) {
        String img = node.getLiteral().replace("'", "\\'");
        return this.check(node, "'" + img + "'", data);
    }

    @Override
    protected Object visit(ASTRegexLiteral node, Object data) {
        String img = node.toString().replace("/", "\\/");
        return this.check(node, "~/" + img + "/", data);
    }

    @Override
    protected Object visit(ASTTernaryNode node, Object data) {
        this.accept(node.jjtGetChild(0), data);
        if (node.jjtGetNumChildren() > 2) {
            this.builder.append("? ");
            this.accept(node.jjtGetChild(1), data);
            this.builder.append(" : ");
            this.accept(node.jjtGetChild(2), data);
        } else {
            this.builder.append("?: ");
            this.accept(node.jjtGetChild(1), data);
        }
        return data;
    }

    @Override
    protected Object visit(ASTNullpNode node, Object data) {
        this.accept(node.jjtGetChild(0), data);
        this.builder.append("??");
        this.accept(node.jjtGetChild(1), data);
        return data;
    }

    @Override
    protected Object visit(ASTTrueNode node, Object data) {
        this.check(node, "true", data);
        return data;
    }

    @Override
    protected Object visit(ASTUnaryMinusNode node, Object data) {
        return this.prefixChild(node, "-", data);
    }

    @Override
    protected Object visit(ASTUnaryPlusNode node, Object data) {
        return this.prefixChild(node, "+", data);
    }

    @Override
    protected Object visit(ASTVar node, Object data) {
        this.builder.append("var ");
        this.check(node, node.getName(), data);
        return data;
    }

    @Override
    protected Object visit(ASTWhileStatement node, Object data) {
        this.builder.append("while (");
        this.accept(node.jjtGetChild(0), data);
        this.builder.append(") ");
        if (node.jjtGetNumChildren() > 1) {
            this.acceptStatement(node.jjtGetChild(1), data);
        } else {
            this.builder.append(';');
        }
        return data;
    }

    @Override
    protected Object visit(ASTDoWhileStatement node, Object data) {
        this.builder.append("do ");
        int nc = node.jjtGetNumChildren();
        if (nc > 1) {
            this.acceptStatement(node.jjtGetChild(0), data);
        } else {
            this.builder.append(";");
        }
        this.builder.append(" while (");
        this.accept(node.jjtGetChild(nc - 1), data);
        this.builder.append(")");
        return data;
    }

    @Override
    protected Object visit(ASTSetAddNode node, Object data) {
        return this.infixChildren(node, " += ", false, data);
    }

    @Override
    protected Object visit(ASTSetSubNode node, Object data) {
        return this.infixChildren(node, " -= ", false, data);
    }

    @Override
    protected Object visit(ASTSetMultNode node, Object data) {
        return this.infixChildren(node, " *= ", false, data);
    }

    @Override
    protected Object visit(ASTSetDivNode node, Object data) {
        return this.infixChildren(node, " /= ", false, data);
    }

    @Override
    protected Object visit(ASTSetModNode node, Object data) {
        return this.infixChildren(node, " %= ", false, data);
    }

    @Override
    protected Object visit(ASTSetAndNode node, Object data) {
        return this.infixChildren(node, " &= ", false, data);
    }

    @Override
    protected Object visit(ASTSetOrNode node, Object data) {
        return this.infixChildren(node, " |= ", false, data);
    }

    @Override
    protected Object visit(ASTSetXorNode node, Object data) {
        return this.infixChildren(node, " ^= ", false, data);
    }

    @Override
    protected Object visit(ASTJxltLiteral node, Object data) {
        String img = node.getLiteral().replace("`", "\\`");
        return this.check(node, "`" + img + "`", data);
    }

    @Override
    protected Object visit(ASTAnnotation node, Object data) {
        int num = node.jjtGetNumChildren();
        this.builder.append('@');
        this.builder.append(node.getName());
        if (num > 0) {
            this.accept(node.jjtGetChild(0), data);
        }
        return null;
    }

    @Override
    protected Object visit(ASTAnnotatedStatement node, Object data) {
        int num = node.jjtGetNumChildren();
        for (int i = 0; i < num; ++i) {
            if (i > 0) {
                this.builder.append(' ');
            }
            JexlNode child = node.jjtGetChild(i);
            this.acceptStatement(child, data);
        }
        return data;
    }
}

