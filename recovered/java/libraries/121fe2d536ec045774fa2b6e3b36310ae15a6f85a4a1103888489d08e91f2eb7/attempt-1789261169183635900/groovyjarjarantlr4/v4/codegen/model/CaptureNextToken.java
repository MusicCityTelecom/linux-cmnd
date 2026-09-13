/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.SrcOp;

public class CaptureNextToken
extends SrcOp {
    public String varName;

    public CaptureNextToken(OutputModelFactory factory, String varName) {
        super(factory);
        this.varName = varName;
    }
}

