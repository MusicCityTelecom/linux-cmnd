/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.codegen.model;

import groovyjarjarantlr4.v4.codegen.OutputModelFactory;
import groovyjarjarantlr4.v4.codegen.model.OutputModelObject;
import groovyjarjarantlr4.v4.runtime.atn.ATN;
import groovyjarjarantlr4.v4.runtime.atn.ATNSerializer;
import groovyjarjarantlr4.v4.runtime.misc.IntegerList;
import java.util.ArrayList;
import java.util.List;

public class SerializedATN
extends OutputModelObject {
    public List<String> serialized;

    public SerializedATN(OutputModelFactory factory, ATN atn, List<String> ruleNames) {
        super(factory);
        IntegerList data = ATNSerializer.getSerialized(atn, ruleNames);
        this.serialized = new ArrayList<String>(data.size());
        for (int c : data.toArray()) {
            String encoded = factory.getTarget().encodeIntAsCharEscape(c == -1 ? 65535 : c);
            this.serialized.add(encoded);
        }
    }

    public String[][] getSegments() {
        ArrayList<String[]> segments = new ArrayList<String[]>();
        int segmentLimit = this.factory.getTarget().getSerializedATNSegmentLimit();
        for (int i = 0; i < this.serialized.size(); i += segmentLimit) {
            List<String> currentSegment = this.serialized.subList(i, Math.min(i + segmentLimit, this.serialized.size()));
            segments.add(currentSegment.toArray(new String[currentSegment.size()]));
        }
        return (String[][])segments.toArray((T[])new String[segments.size()][]);
    }
}

