/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.ModelElement;
import groovyjarjarantlr4.v4.codegen.model.ParserFile;
import groovyjarjarantlr4.v4.codegen.model.Recognizer;
import groovyjarjarantlr4.v4.codegen.model.RuleFunction;
import java.util.ArrayList;
import java.util.List;

public class Parser
extends Recognizer {
    public ParserFile file;
    @ModelElement
    public List<RuleFunction> funcs = new ArrayList<RuleFunction>();

    public Parser(OutputModelFactory factory, ParserFile file) {
        super(factory);
        this.file = file;
    }
}

