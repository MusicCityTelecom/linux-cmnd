/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.SrcOp;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import java.util.ArrayList;
import java.util.List;

public class TestSetInline
extends SrcOp {
    public int bitsetWordSize;
    public String varName;
    public Bitset[] bitsets;

    public TestSetInline(OutputModelFactory factory, GrammarAST ast, IntervalSet set, int wordSize) {
        super(factory, ast);
        this.bitsetWordSize = wordSize;
        Bitset[] withZeroOffset = TestSetInline.createBitsets(factory, set, wordSize, true);
        Bitset[] withoutZeroOffset = TestSetInline.createBitsets(factory, set, wordSize, false);
        this.bitsets = withZeroOffset.length <= withoutZeroOffset.length ? withZeroOffset : withoutZeroOffset;
        this.varName = "_la";
    }

    private static Bitset[] createBitsets(OutputModelFactory factory, IntervalSet set, int wordSize, boolean useZeroOffset) {
        ArrayList<Bitset> bitsetList = new ArrayList<Bitset>();
        for (int ttype : set.toArray()) {
            Bitset current;
            Bitset bitset = current = !bitsetList.isEmpty() ? (Bitset)bitsetList.get(bitsetList.size() - 1) : null;
            if (current == null || ttype > current.shift + wordSize - 1) {
                current = new Bitset();
                current.shift = useZeroOffset && ttype >= 0 && ttype < wordSize - 1 ? 0 : ttype;
                bitsetList.add(current);
            }
            current.ttypes.add(factory.getTarget().getTokenTypeAsTargetLabel(factory.getGrammar(), ttype));
        }
        return bitsetList.toArray(new Bitset[bitsetList.size()]);
    }

    public static final class Bitset {
        public int shift;
        public final List<String> ttypes = new ArrayList<String>();
    }
}

