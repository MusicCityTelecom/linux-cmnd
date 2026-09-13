/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.Action;
import groovyjarjarantlr4.v4.codegen.model.ModelElement;
import groovyjarjarantlr4.v4.codegen.model.OutputModelObject;
import groovyjarjarantlr4.v4.tool.Rule;
import java.util.LinkedHashMap;

public class RuleActionFunction
extends OutputModelObject {
    public String name;
    public String ctxType;
    public int ruleIndex;
    @ModelElement
    public LinkedHashMap<Integer, Action> actions = new LinkedHashMap();

    public RuleActionFunction(OutputModelFactory factory, Rule r, String ctxType) {
        super(factory);
        this.name = r.name;
        this.ruleIndex = r.index;
        this.ctxType = ctxType;
    }
}

