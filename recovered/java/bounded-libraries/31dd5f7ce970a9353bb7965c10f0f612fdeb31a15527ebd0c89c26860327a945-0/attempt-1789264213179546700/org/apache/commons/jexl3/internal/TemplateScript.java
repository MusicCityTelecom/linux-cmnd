/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.internal;

import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.JexlInfo;
import org.apache.commons.jexl3.JexlOptions;
import org.apache.commons.jexl3.JxltEngine;
import org.apache.commons.jexl3.internal.Engine;
import org.apache.commons.jexl3.internal.Frame;
import org.apache.commons.jexl3.internal.Scope;
import org.apache.commons.jexl3.internal.TemplateEngine;
import org.apache.commons.jexl3.internal.TemplateInterpreter;
import org.apache.commons.jexl3.parser.ASTArguments;
import org.apache.commons.jexl3.parser.ASTFunctionNode;
import org.apache.commons.jexl3.parser.ASTIdentifier;
import org.apache.commons.jexl3.parser.ASTJexlScript;
import org.apache.commons.jexl3.parser.ASTNumberLiteral;
import org.apache.commons.jexl3.parser.JexlNode;

public final class TemplateScript
implements JxltEngine.Template {
    private final String prefix;
    private final TemplateEngine.Block[] source;
    private final ASTJexlScript script;
    private final TemplateEngine.TemplateExpression[] exprs;
    private final TemplateEngine jxlt;

    public TemplateScript(TemplateEngine engine, JexlInfo info, String directive, Reader reader, String ... parms) {
        if (directive == null) {
            throw new NullPointerException("null prefix");
        }
        String engineImmediateCharString = Character.toString(engine.getImmediateChar());
        String engineDeferredCharString = Character.toString(engine.getDeferredChar());
        if (engineImmediateCharString.equals(directive) || engineDeferredCharString.equals(directive) || (engineImmediateCharString + "{").equals(directive) || (engineDeferredCharString + "{").equals(directive)) {
            throw new IllegalArgumentException(directive + ": is not a valid directive pattern");
        }
        if (reader == null) {
            throw new NullPointerException("null input");
        }
        this.jxlt = engine;
        this.prefix = directive;
        List<TemplateEngine.Block> blocks = this.jxlt.readTemplate(this.prefix, reader);
        ArrayList<TemplateEngine.TemplateExpression> uexprs = new ArrayList<TemplateEngine.TemplateExpression>();
        StringBuilder strb = new StringBuilder();
        int nuexpr = 0;
        int codeStart = -1;
        int line = 1;
        for (int b = 0; b < blocks.size(); ++b) {
            TemplateEngine.Block block = blocks.get(b);
            int bl = block.getLine();
            while (line < bl) {
                strb.append("//\n");
                ++line;
            }
            if (block.getType() == TemplateEngine.BlockType.VERBATIM) {
                strb.append("jexl:print(");
                strb.append(nuexpr++);
                strb.append(");\n");
                ++line;
                continue;
            }
            if (codeStart < 0) {
                codeStart = b;
            }
            String body = block.getBody();
            strb.append(body);
            for (int c = 0; c < body.length(); ++c) {
                if (body.charAt(c) != '\n') continue;
                ++line;
            }
        }
        if (info == null) {
            info = this.jxlt.getEngine().createInfo();
        }
        Scope scope = parms == null ? null : new Scope(null, parms);
        this.script = this.jxlt.getEngine().parse(info.at(1, 1), false, strb.toString(), scope).script();
        TreeMap<Integer, JexlNode.Info> minfo = new TreeMap<Integer, JexlNode.Info>();
        TemplateScript.collectPrintScope(this.script.script(), minfo);
        int jpe = 0;
        for (TemplateEngine.Block block : blocks) {
            TemplateEngine.TemplateExpression te;
            if (block.getType() != TemplateEngine.BlockType.VERBATIM) continue;
            JexlNode.Info ji = (JexlNode.Info)minfo.get(jpe);
            if (ji != null) {
                te = this.jxlt.parseExpression(ji, block.getBody(), TemplateScript.scopeOf(ji));
            } else {
                TemplateEngine templateEngine = this.jxlt;
                templateEngine.getClass();
                te = templateEngine.new TemplateEngine.ConstantExpression(block.getBody(), null);
            }
            uexprs.add(te);
            ++jpe;
        }
        this.source = blocks.toArray(new TemplateEngine.Block[blocks.size()]);
        this.exprs = uexprs.toArray(new TemplateEngine.TemplateExpression[uexprs.size()]);
    }

    TemplateScript(TemplateEngine engine, String thePrefix, TemplateEngine.Block[] theSource, ASTJexlScript theScript, TemplateEngine.TemplateExpression[] theExprs) {
        this.jxlt = engine;
        this.prefix = thePrefix;
        this.source = theSource;
        this.script = theScript;
        this.exprs = theExprs;
    }

    private static Scope scopeOf(JexlNode.Info info) {
        for (JexlNode walk = info.getNode(); walk != null; walk = walk.jjtGetParent()) {
            if (!(walk instanceof ASTJexlScript)) continue;
            return ((ASTJexlScript)walk).getScope();
        }
        return null;
    }

    private static void collectPrintScope(JexlNode node, Map<Integer, JexlNode.Info> minfo) {
        JexlNode arg0;
        ASTArguments argNode;
        ASTIdentifier nameNode;
        int nc = node.jjtGetNumChildren();
        if (node instanceof ASTFunctionNode && nc == 2 && "print".equals((nameNode = (ASTIdentifier)node.jjtGetChild(0)).getName()) && "jexl".equals(nameNode.getNamespace()) && (argNode = (ASTArguments)node.jjtGetChild(1)).jjtGetNumChildren() == 1 && (arg0 = argNode.jjtGetChild(0)) instanceof ASTNumberLiteral) {
            int exprNumber = ((ASTNumberLiteral)arg0).getLiteral().intValue();
            minfo.put(exprNumber, new JexlNode.Info(nameNode));
            return;
        }
        for (int c = 0; c < nc; ++c) {
            TemplateScript.collectPrintScope(node.jjtGetChild(c), minfo);
        }
    }

    ASTJexlScript getScript() {
        return this.script;
    }

    TemplateEngine.TemplateExpression[] getExpressions() {
        return this.exprs;
    }

    public String toString() {
        StringBuilder strb = new StringBuilder();
        for (TemplateEngine.Block block : this.source) {
            block.toString(strb, this.prefix);
        }
        return strb.toString();
    }

    @Override
    public String asString() {
        StringBuilder strb = new StringBuilder();
        int e = 0;
        for (TemplateEngine.Block block : this.source) {
            if (block.getType() == TemplateEngine.BlockType.DIRECTIVE) {
                strb.append(this.prefix);
                strb.append(block.getBody());
                continue;
            }
            this.exprs[e++].asString(strb);
        }
        return strb.toString();
    }

    @Override
    public TemplateScript prepare(JexlContext context) {
        Engine jexl = this.jxlt.getEngine();
        JexlOptions options = jexl.options(this.script, context);
        Frame frame = this.script.createFrame((Object[])null);
        TemplateInterpreter.Arguments targs = new TemplateInterpreter.Arguments(this.jxlt.getEngine()).context(context).options(options).frame(frame);
        TemplateInterpreter interpreter = new TemplateInterpreter(targs);
        TemplateEngine.TemplateExpression[] immediates = new TemplateEngine.TemplateExpression[this.exprs.length];
        for (int e = 0; e < this.exprs.length; ++e) {
            try {
                immediates[e] = this.exprs[e].prepare(interpreter);
                continue;
            }
            catch (JexlException xjexl) {
                JxltEngine.Exception xuel = TemplateEngine.createException(xjexl.getInfo(), "prepare", this.exprs[e], xjexl);
                if (jexl.isSilent()) {
                    jexl.logger.warn((Object)xuel.getMessage(), xuel.getCause());
                    return null;
                }
                throw xuel;
            }
        }
        return new TemplateScript(this.jxlt, this.prefix, this.source, this.script, immediates);
    }

    @Override
    public void evaluate(JexlContext context, Writer writer) {
        this.evaluate(context, writer, null);
    }

    @Override
    public void evaluate(JexlContext context, Writer writer, Object ... args) {
        Engine jexl = this.jxlt.getEngine();
        JexlOptions options = jexl.options(this.script, context);
        Frame frame = this.script.createFrame(args);
        TemplateInterpreter.Arguments targs = new TemplateInterpreter.Arguments(jexl).context(context).options(options).frame(frame).expressions(this.exprs).writer(writer);
        TemplateInterpreter interpreter = new TemplateInterpreter(targs);
        interpreter.interpret(this.script);
    }

    @Override
    public Set<List<String>> getVariables() {
        Engine.VarCollector collector = this.jxlt.getEngine().varCollector();
        for (TemplateEngine.TemplateExpression expr : this.exprs) {
            expr.getVariables(collector);
        }
        return collector.collected();
    }

    @Override
    public String[] getParameters() {
        return this.script.getParameters();
    }

    @Override
    public Map<String, Object> getPragmas() {
        return this.script.getPragmas();
    }
}

