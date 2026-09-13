/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.CodeBlockForAlt;
import groovyjarjarantlr4.v4.tool.Alternative;

public class CodeBlockForOuterMostAlt
extends CodeBlockForAlt {
    public String altLabel;
    public Alternative alt;

    public CodeBlockForOuterMostAlt(OutputModelFactory factory, Alternative alt) {
        super(factory);
        this.alt = alt;
        this.altLabel = alt.ast.altLabel != null ? alt.ast.altLabel.getText() : null;
    }
}

