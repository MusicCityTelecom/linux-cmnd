/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.analysis;

import groovyjarjarantlr4.v4.tool.ast.AltAST;

public class LeftRecursiveRuleAltInfo {
    public int altNum;
    public String leftRecursiveRuleRefLabel;
    public String altLabel;
    public final boolean isListLabel;
    public String altText;
    public AltAST altAST;
    public AltAST originalAltAST;
    public int nextPrec;

    public LeftRecursiveRuleAltInfo(int altNum, String altText) {
        this(altNum, altText, null, null, false, null);
    }

    public LeftRecursiveRuleAltInfo(int altNum, String altText, String leftRecursiveRuleRefLabel, String altLabel, boolean isListLabel, AltAST originalAltAST) {
        this.altNum = altNum;
        this.altText = altText;
        this.leftRecursiveRuleRefLabel = leftRecursiveRuleRefLabel;
        this.altLabel = altLabel;
        this.isListLabel = isListLabel;
        this.originalAltAST = originalAltAST;
    }
}

