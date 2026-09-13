/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.stringtemplate.v4.ST
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.runtime.CommonToken;
import groovyjarjarantlr4.v4.codegen.ActionTranslator;
import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.ModelElement;
import groovyjarjarantlr4.v4.codegen.model.RuleElement;
import groovyjarjarantlr4.v4.codegen.model.RuleFunction;
import groovyjarjarantlr4.v4.codegen.model.chunk.ActionChunk;
import groovyjarjarantlr4.v4.codegen.model.chunk.ActionTemplate;
import groovyjarjarantlr4.v4.codegen.model.chunk.ActionText;
import groovyjarjarantlr4.v4.codegen.model.decl.StructDecl;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import java.util.ArrayList;
import java.util.List;
import org.stringtemplate.v4.ST;

public class Action
extends RuleElement {
    @ModelElement
    public List<ActionChunk> chunks;

    public Action(OutputModelFactory factory, ActionAST ast) {
        super(factory, ast);
        RuleFunction rf = factory.getCurrentRuleFunction();
        this.chunks = ast != null ? ActionTranslator.translateAction(factory, rf, ast.token, ast) : new ArrayList<ActionChunk>();
    }

    public Action(OutputModelFactory factory, StructDecl ctx, String action) {
        super(factory, null);
        ActionAST ast = new ActionAST(new CommonToken(4, action));
        RuleFunction rf = factory.getCurrentRuleFunction();
        if (rf != null) {
            ast.resolver = rf.rule;
            this.chunks = ActionTranslator.translateActionChunk(factory, rf, action, ast);
        } else {
            this.chunks = new ArrayList<ActionChunk>();
            this.chunks.add(new ActionText(ctx, action));
        }
    }

    public Action(OutputModelFactory factory, StructDecl ctx, ST actionST) {
        super(factory, null);
        this.chunks = new ArrayList<ActionChunk>();
        this.chunks.add(new ActionTemplate(ctx, actionST));
    }
}

