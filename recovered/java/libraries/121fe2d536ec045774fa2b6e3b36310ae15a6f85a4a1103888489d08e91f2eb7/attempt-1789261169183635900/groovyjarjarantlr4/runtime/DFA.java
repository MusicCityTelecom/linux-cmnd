/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.runtime;

import groovyjarjarantlr4.runtime.BaseRecognizer;
import groovyjarjarantlr4.runtime.IntStream;
import groovyjarjarantlr4.runtime.NoViableAltException;
import groovyjarjarantlr4.runtime.RecognitionException;

public class DFA {
    protected short[] eot;
    protected short[] eof;
    protected char[] min;
    protected char[] max;
    protected short[] accept;
    protected short[] special;
    protected short[][] transition;
    protected int decisionNumber;
    protected BaseRecognizer recognizer;
    public static final boolean debug = false;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int predict(IntStream input) throws RecognitionException {
        int mark = input.mark();
        int s = 0;
        try {
            int n;
            char c;
            while (true) {
                short s2;
                short specialState;
                if ((specialState = this.special[s]) >= 0) {
                    s = this.specialStateTransition(specialState, input);
                    if (s == -1) {
                        this.noViableAlt(s, input);
                        s2 = 0;
                        return s2;
                    }
                    input.consume();
                    continue;
                }
                if (this.accept[s] >= 1) {
                    s2 = this.accept[s];
                    return s2;
                }
                c = (char)input.LA(1);
                if (c >= this.min[s] && c <= this.max[s]) {
                    int snext = this.transition[s][c - this.min[s]];
                    if (snext < 0) {
                        if (this.eot[s] >= 0) {
                            s = this.eot[s];
                            input.consume();
                            continue;
                        }
                        this.noViableAlt(s, input);
                        int n2 = 0;
                        return n2;
                    }
                    s = snext;
                    input.consume();
                    continue;
                }
                if (this.eot[s] < 0) break;
                s = this.eot[s];
                input.consume();
            }
            if (c == '\uffff' && this.eof[s] >= 0) {
                n = this.accept[this.eof[s]];
                return n;
            }
            this.noViableAlt(s, input);
            n = 0;
            return n;
        }
        finally {
            input.rewind(mark);
        }
    }

    protected void noViableAlt(int s, IntStream input) throws NoViableAltException {
        if (this.recognizer.state.backtracking > 0) {
            this.recognizer.state.failed = true;
            return;
        }
        NoViableAltException nvae = new NoViableAltException(this.getDescription(), this.decisionNumber, s, input);
        this.error(nvae);
        throw nvae;
    }

    protected void error(NoViableAltException nvae) {
    }

    public int specialStateTransition(int s, IntStream input) throws NoViableAltException {
        return -1;
    }

    public String getDescription() {
        return "n/a";
    }

    public static short[] unpackEncodedString(String encodedString) {
        int size = 0;
        for (int i = 0; i < encodedString.length(); i += 2) {
            size += encodedString.charAt(i);
        }
        short[] data = new short[size];
        int di = 0;
        for (int i = 0; i < encodedString.length(); i += 2) {
            int n = encodedString.charAt(i);
            char v = encodedString.charAt(i + 1);
            for (int j = 1; j <= n; ++j) {
                data[di++] = (short)v;
            }
        }
        return data;
    }

    public static char[] unpackEncodedStringToUnsignedChars(String encodedString) {
        int size = 0;
        for (int i = 0; i < encodedString.length(); i += 2) {
            size += encodedString.charAt(i);
        }
        char[] data = new char[size];
        int di = 0;
        for (int i = 0; i < encodedString.length(); i += 2) {
            int n = encodedString.charAt(i);
            char v = encodedString.charAt(i + 1);
            for (int j = 1; j <= n; ++j) {
                data[di++] = v;
            }
        }
        return data;
    }
}

