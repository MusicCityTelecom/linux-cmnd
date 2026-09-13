/*
 * Decompiled with CFR 0.152.
 */
package com.sun.el.parser;

import com.sun.el.parser.ELParserConstants;
import com.sun.el.parser.SimpleCharStream;
import com.sun.el.parser.Token;
import com.sun.el.parser.TokenMgrError;
import java.io.IOException;
import java.io.PrintStream;

public class ELParserTokenManager
implements ELParserConstants {
    public PrintStream debugStream = System.out;
    static final long[] jjbitVec0 = new long[]{-2L, -1L, -1L, -1L};
    static final long[] jjbitVec2 = new long[]{0L, 0L, -1L, -1L};
    static final long[] jjbitVec3 = new long[]{2301339413881290750L, -16384L, 0xFFFFFFFFL, 0x600000000000000L};
    static final long[] jjbitVec4 = new long[]{0L, 0L, 0L, -36028797027352577L};
    static final long[] jjbitVec5 = new long[]{0L, -1L, -1L, -1L};
    static final long[] jjbitVec6 = new long[]{-1L, -1L, 65535L, 0L};
    static final long[] jjbitVec7 = new long[]{-1L, -1L, 0L, 0L};
    static final long[] jjbitVec8 = new long[]{0x3FFFFFFFFFFFL, 0L, 0L, 0L};
    static final int[] jjnextStates = new int[]{0, 1, 3, 5, 8, 9, 10, 15, 16, 28, 29, 31, 32, 33, 20, 21, 23, 24, 25, 20, 21, 23, 28, 29, 31, 3, 4, 13, 14, 17, 18, 24, 25, 32, 33};
    public static final String[] jjstrLiteralImages = new String[]{"", null, "${", "#{", null, null, null, null, null, null, null, null, null, null, "true", "false", "null", "}", ".", "(", ")", "[", "]", ":", ",", ">", "gt", "<", "lt", ">=", "ge", "<=", "le", "==", "eq", "!=", "ne", "!", "not", "&&", "and", "||", "or", "empty", "instanceof", "*", "+", "-", "?", "/", "div", "%", "mod", null, null, null, null, null};
    public static final String[] lexStateNames = new String[]{"DEFAULT", "IN_EXPRESSION"};
    public static final int[] jjnewLexState = new int[]{-1, -1, 1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    static final long[] jjtoToken = new long[]{162129586585335311L};
    static final long[] jjtoSkip = new long[]{496L};
    protected SimpleCharStream input_stream;
    private final int[] jjrounds = new int[35];
    private final int[] jjstateSet = new int[70];
    protected char curChar;
    int curLexState = 0;
    int defaultLexState = 0;
    int jjnewStateCnt;
    int jjround;
    int jjmatchedPos;
    int jjmatchedKind;

    public void setDebugStream(PrintStream ds) {
        this.debugStream = ds;
    }

    private final int jjStopStringLiteralDfa_0(int pos, long active0) {
        switch (pos) {
            case 0: {
                if ((active0 & 0x10L) != 0L) {
                    return 2;
                }
                if ((active0 & 4L) != 0L) {
                    this.jjmatchedKind = 1;
                    return 4;
                }
                if ((active0 & 8L) != 0L) {
                    this.jjmatchedKind = 1;
                    return 6;
                }
                return -1;
            }
        }
        return -1;
    }

    private final int jjStartNfa_0(int pos, long active0) {
        return this.jjMoveNfa_0(this.jjStopStringLiteralDfa_0(pos, active0), pos + 1);
    }

    private int jjStopAtPos(int pos, int kind) {
        this.jjmatchedKind = kind;
        this.jjmatchedPos = pos;
        return pos + 1;
    }

    private int jjMoveStringLiteralDfa0_0() {
        switch (this.curChar) {
            case '#': {
                return this.jjMoveStringLiteralDfa1_0(8L);
            }
            case '$': {
                return this.jjMoveStringLiteralDfa1_0(4L);
            }
            case '\\': {
                return this.jjStartNfaWithStates_0(0, 4, 2);
            }
        }
        return this.jjMoveNfa_0(7, 0);
    }

    private int jjMoveStringLiteralDfa1_0(long active0) {
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_0(0, active0);
            return 1;
        }
        switch (this.curChar) {
            case '{': {
                if ((active0 & 4L) != 0L) {
                    return this.jjStopAtPos(1, 2);
                }
                if ((active0 & 8L) == 0L) break;
                return this.jjStopAtPos(1, 3);
            }
        }
        return this.jjStartNfa_0(0, active0);
    }

    private int jjStartNfaWithStates_0(int pos, int kind, int state) {
        this.jjmatchedKind = kind;
        this.jjmatchedPos = pos;
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            return pos + 1;
        }
        return this.jjMoveNfa_0(state, pos + 1);
    }

    private int jjMoveNfa_0(int startState, int curPos) {
        int startsAt = 0;
        this.jjnewStateCnt = 8;
        int i = 1;
        this.jjstateSet[0] = startState;
        int kind = Integer.MAX_VALUE;
        while (true) {
            if (++this.jjround == Integer.MAX_VALUE) {
                this.ReInitRounds();
            }
            if (this.curChar < '@') {
                long l = 1L << this.curChar;
                block22: do {
                    switch (this.jjstateSet[--i]) {
                        case 7: {
                            if ((0xFFFFFFE7FFFFFFFFL & l) != 0L) {
                                if (kind > 1) {
                                    kind = 1;
                                }
                                this.jjCheckNAddStates(0, 3);
                            } else if ((0x1800000000L & l) != 0L && kind > 1) {
                                kind = 1;
                            }
                            if (this.curChar == '#') {
                                this.jjstateSet[this.jjnewStateCnt++] = 6;
                                break;
                            }
                            if (this.curChar != '$') break;
                            this.jjstateSet[this.jjnewStateCnt++] = 4;
                            break;
                        }
                        case 0: {
                            if ((0xFFFFFFE7FFFFFFFFL & l) == 0L) continue block22;
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjCheckNAddStates(0, 3);
                            break;
                        }
                        case 2: {
                            if ((0x1800000000L & l) == 0L) continue block22;
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjCheckNAddStates(0, 3);
                            break;
                        }
                        case 3: {
                            if (this.curChar != '$') break;
                            this.jjstateSet[this.jjnewStateCnt++] = 4;
                            break;
                        }
                        case 4: {
                            if ((0xFFFFFFEFFFFFFFFFL & l) == 0L) continue block22;
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjCheckNAddStates(0, 3);
                            break;
                        }
                        case 5: {
                            if (this.curChar != '#') break;
                            this.jjstateSet[this.jjnewStateCnt++] = 6;
                            break;
                        }
                        case 6: {
                            if ((0xFFFFFFF7FFFFFFFFL & l) == 0L) continue block22;
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjCheckNAddStates(0, 3);
                            break;
                        }
                    }
                } while (i != startsAt);
            } else if (this.curChar < '\u0080') {
                long l = 1L << (this.curChar & 0x3F);
                block23: do {
                    switch (this.jjstateSet[--i]) {
                        case 7: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) != 0L) {
                                if (kind > 1) {
                                    kind = 1;
                                }
                                this.jjCheckNAddStates(0, 3);
                                break;
                            }
                            if (this.curChar != '\\') break;
                            this.jjstateSet[this.jjnewStateCnt++] = 2;
                            break;
                        }
                        case 0: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) == 0L) continue block23;
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjCheckNAddStates(0, 3);
                            break;
                        }
                        case 1: {
                            if (this.curChar != '\\') break;
                            this.jjstateSet[this.jjnewStateCnt++] = 2;
                            break;
                        }
                        case 2: {
                            if (this.curChar != '\\') continue block23;
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjCheckNAddStates(0, 3);
                            break;
                        }
                        case 4: 
                        case 6: {
                            if ((0xF7FFFFFFFFFFFFFFL & l) == 0L) continue block23;
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjCheckNAddStates(0, 3);
                            break;
                        }
                    }
                } while (i != startsAt);
            } else {
                int hiByte = this.curChar >> 8;
                int i1 = hiByte >> 6;
                long l1 = 1L << (hiByte & 0x3F);
                int i2 = (this.curChar & 0xFF) >> 6;
                long l2 = 1L << (this.curChar & 0x3F);
                block24: do {
                    switch (this.jjstateSet[--i]) {
                        case 0: 
                        case 4: 
                        case 6: 
                        case 7: {
                            if (!ELParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) continue block24;
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjCheckNAddStates(0, 3);
                            break;
                        }
                    }
                } while (i != startsAt);
            }
            if (kind != Integer.MAX_VALUE) {
                this.jjmatchedKind = kind;
                this.jjmatchedPos = curPos;
                kind = Integer.MAX_VALUE;
            }
            ++curPos;
            i = this.jjnewStateCnt;
            this.jjnewStateCnt = startsAt;
            if (i == (startsAt = 8 - this.jjnewStateCnt)) {
                return curPos;
            }
            try {
                this.curChar = this.input_stream.readChar();
            }
            catch (IOException e) {
                return curPos;
            }
        }
    }

    private final int jjStopStringLiteralDfa_1(int pos, long active0) {
        switch (pos) {
            case 0: {
                if ((active0 & 0x40000L) != 0L) {
                    return 1;
                }
                if ((active0 & 0x141D555401C000L) != 0L) {
                    this.jjmatchedKind = 53;
                    return 6;
                }
                return -1;
            }
            case 1: {
                if ((active0 & 0x41554000000L) != 0L) {
                    return 6;
                }
                if ((active0 & 0x1419400001C000L) != 0L) {
                    this.jjmatchedKind = 53;
                    this.jjmatchedPos = 1;
                    return 6;
                }
                return -1;
            }
            case 2: {
                if ((active0 & 0x14014000000000L) != 0L) {
                    return 6;
                }
                if ((active0 & 0x18000001C000L) != 0L) {
                    this.jjmatchedKind = 53;
                    this.jjmatchedPos = 2;
                    return 6;
                }
                return -1;
            }
            case 3: {
                if ((active0 & 0x14000L) != 0L) {
                    return 6;
                }
                if ((active0 & 0x180000008000L) != 0L) {
                    this.jjmatchedKind = 53;
                    this.jjmatchedPos = 3;
                    return 6;
                }
                return -1;
            }
            case 4: {
                if ((active0 & 0x80000008000L) != 0L) {
                    return 6;
                }
                if ((active0 & 0x100000000000L) != 0L) {
                    this.jjmatchedKind = 53;
                    this.jjmatchedPos = 4;
                    return 6;
                }
                return -1;
            }
            case 5: {
                if ((active0 & 0x100000000000L) != 0L) {
                    this.jjmatchedKind = 53;
                    this.jjmatchedPos = 5;
                    return 6;
                }
                return -1;
            }
            case 6: {
                if ((active0 & 0x100000000000L) != 0L) {
                    this.jjmatchedKind = 53;
                    this.jjmatchedPos = 6;
                    return 6;
                }
                return -1;
            }
            case 7: {
                if ((active0 & 0x100000000000L) != 0L) {
                    this.jjmatchedKind = 53;
                    this.jjmatchedPos = 7;
                    return 6;
                }
                return -1;
            }
            case 8: {
                if ((active0 & 0x100000000000L) != 0L) {
                    this.jjmatchedKind = 53;
                    this.jjmatchedPos = 8;
                    return 6;
                }
                return -1;
            }
        }
        return -1;
    }

    private final int jjStartNfa_1(int pos, long active0) {
        return this.jjMoveNfa_1(this.jjStopStringLiteralDfa_1(pos, active0), pos + 1);
    }

    private int jjMoveStringLiteralDfa0_1() {
        switch (this.curChar) {
            case '!': {
                this.jjmatchedKind = 37;
                return this.jjMoveStringLiteralDfa1_1(0x800000000L);
            }
            case '%': {
                return this.jjStopAtPos(0, 51);
            }
            case '&': {
                return this.jjMoveStringLiteralDfa1_1(0x8000000000L);
            }
            case '(': {
                return this.jjStopAtPos(0, 19);
            }
            case ')': {
                return this.jjStopAtPos(0, 20);
            }
            case '*': {
                return this.jjStopAtPos(0, 45);
            }
            case '+': {
                return this.jjStopAtPos(0, 46);
            }
            case ',': {
                return this.jjStopAtPos(0, 24);
            }
            case '-': {
                return this.jjStopAtPos(0, 47);
            }
            case '.': {
                return this.jjStartNfaWithStates_1(0, 18, 1);
            }
            case '/': {
                return this.jjStopAtPos(0, 49);
            }
            case ':': {
                return this.jjStopAtPos(0, 23);
            }
            case '<': {
                this.jjmatchedKind = 27;
                return this.jjMoveStringLiteralDfa1_1(0x80000000L);
            }
            case '=': {
                return this.jjMoveStringLiteralDfa1_1(0x200000000L);
            }
            case '>': {
                this.jjmatchedKind = 25;
                return this.jjMoveStringLiteralDfa1_1(0x20000000L);
            }
            case '?': {
                return this.jjStopAtPos(0, 48);
            }
            case '[': {
                return this.jjStopAtPos(0, 21);
            }
            case ']': {
                return this.jjStopAtPos(0, 22);
            }
            case 'a': {
                return this.jjMoveStringLiteralDfa1_1(0x10000000000L);
            }
            case 'd': {
                return this.jjMoveStringLiteralDfa1_1(0x4000000000000L);
            }
            case 'e': {
                return this.jjMoveStringLiteralDfa1_1(0x80400000000L);
            }
            case 'f': {
                return this.jjMoveStringLiteralDfa1_1(32768L);
            }
            case 'g': {
                return this.jjMoveStringLiteralDfa1_1(0x44000000L);
            }
            case 'i': {
                return this.jjMoveStringLiteralDfa1_1(0x100000000000L);
            }
            case 'l': {
                return this.jjMoveStringLiteralDfa1_1(0x110000000L);
            }
            case 'm': {
                return this.jjMoveStringLiteralDfa1_1(0x10000000000000L);
            }
            case 'n': {
                return this.jjMoveStringLiteralDfa1_1(0x5000010000L);
            }
            case 'o': {
                return this.jjMoveStringLiteralDfa1_1(0x40000000000L);
            }
            case 't': {
                return this.jjMoveStringLiteralDfa1_1(16384L);
            }
            case '|': {
                return this.jjMoveStringLiteralDfa1_1(0x20000000000L);
            }
            case '}': {
                return this.jjStopAtPos(0, 17);
            }
        }
        return this.jjMoveNfa_1(0, 0);
    }

    private int jjMoveStringLiteralDfa1_1(long active0) {
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_1(0, active0);
            return 1;
        }
        switch (this.curChar) {
            case '&': {
                if ((active0 & 0x8000000000L) == 0L) break;
                return this.jjStopAtPos(1, 39);
            }
            case '=': {
                if ((active0 & 0x20000000L) != 0L) {
                    return this.jjStopAtPos(1, 29);
                }
                if ((active0 & 0x80000000L) != 0L) {
                    return this.jjStopAtPos(1, 31);
                }
                if ((active0 & 0x200000000L) != 0L) {
                    return this.jjStopAtPos(1, 33);
                }
                if ((active0 & 0x800000000L) == 0L) break;
                return this.jjStopAtPos(1, 35);
            }
            case 'a': {
                return this.jjMoveStringLiteralDfa2_1(active0, 32768L);
            }
            case 'e': {
                if ((active0 & 0x40000000L) != 0L) {
                    return this.jjStartNfaWithStates_1(1, 30, 6);
                }
                if ((active0 & 0x100000000L) != 0L) {
                    return this.jjStartNfaWithStates_1(1, 32, 6);
                }
                if ((active0 & 0x1000000000L) == 0L) break;
                return this.jjStartNfaWithStates_1(1, 36, 6);
            }
            case 'i': {
                return this.jjMoveStringLiteralDfa2_1(active0, 0x4000000000000L);
            }
            case 'm': {
                return this.jjMoveStringLiteralDfa2_1(active0, 0x80000000000L);
            }
            case 'n': {
                return this.jjMoveStringLiteralDfa2_1(active0, 0x110000000000L);
            }
            case 'o': {
                return this.jjMoveStringLiteralDfa2_1(active0, 0x10004000000000L);
            }
            case 'q': {
                if ((active0 & 0x400000000L) == 0L) break;
                return this.jjStartNfaWithStates_1(1, 34, 6);
            }
            case 'r': {
                if ((active0 & 0x40000000000L) != 0L) {
                    return this.jjStartNfaWithStates_1(1, 42, 6);
                }
                return this.jjMoveStringLiteralDfa2_1(active0, 16384L);
            }
            case 't': {
                if ((active0 & 0x4000000L) != 0L) {
                    return this.jjStartNfaWithStates_1(1, 26, 6);
                }
                if ((active0 & 0x10000000L) == 0L) break;
                return this.jjStartNfaWithStates_1(1, 28, 6);
            }
            case 'u': {
                return this.jjMoveStringLiteralDfa2_1(active0, 65536L);
            }
            case '|': {
                if ((active0 & 0x20000000000L) == 0L) break;
                return this.jjStopAtPos(1, 41);
            }
        }
        return this.jjStartNfa_1(0, active0);
    }

    private int jjMoveStringLiteralDfa2_1(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_1(0, old0);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_1(1, active0);
            return 2;
        }
        switch (this.curChar) {
            case 'd': {
                if ((active0 & 0x10000000000L) != 0L) {
                    return this.jjStartNfaWithStates_1(2, 40, 6);
                }
                if ((active0 & 0x10000000000000L) == 0L) break;
                return this.jjStartNfaWithStates_1(2, 52, 6);
            }
            case 'l': {
                return this.jjMoveStringLiteralDfa3_1(active0, 98304L);
            }
            case 'p': {
                return this.jjMoveStringLiteralDfa3_1(active0, 0x80000000000L);
            }
            case 's': {
                return this.jjMoveStringLiteralDfa3_1(active0, 0x100000000000L);
            }
            case 't': {
                if ((active0 & 0x4000000000L) == 0L) break;
                return this.jjStartNfaWithStates_1(2, 38, 6);
            }
            case 'u': {
                return this.jjMoveStringLiteralDfa3_1(active0, 16384L);
            }
            case 'v': {
                if ((active0 & 0x4000000000000L) == 0L) break;
                return this.jjStartNfaWithStates_1(2, 50, 6);
            }
        }
        return this.jjStartNfa_1(1, active0);
    }

    private int jjMoveStringLiteralDfa3_1(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_1(1, old0);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_1(2, active0);
            return 3;
        }
        switch (this.curChar) {
            case 'e': {
                if ((active0 & 0x4000L) == 0L) break;
                return this.jjStartNfaWithStates_1(3, 14, 6);
            }
            case 'l': {
                if ((active0 & 0x10000L) == 0L) break;
                return this.jjStartNfaWithStates_1(3, 16, 6);
            }
            case 's': {
                return this.jjMoveStringLiteralDfa4_1(active0, 32768L);
            }
            case 't': {
                return this.jjMoveStringLiteralDfa4_1(active0, 0x180000000000L);
            }
        }
        return this.jjStartNfa_1(2, active0);
    }

    private int jjMoveStringLiteralDfa4_1(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_1(2, old0);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_1(3, active0);
            return 4;
        }
        switch (this.curChar) {
            case 'a': {
                return this.jjMoveStringLiteralDfa5_1(active0, 0x100000000000L);
            }
            case 'e': {
                if ((active0 & 0x8000L) == 0L) break;
                return this.jjStartNfaWithStates_1(4, 15, 6);
            }
            case 'y': {
                if ((active0 & 0x80000000000L) == 0L) break;
                return this.jjStartNfaWithStates_1(4, 43, 6);
            }
        }
        return this.jjStartNfa_1(3, active0);
    }

    private int jjMoveStringLiteralDfa5_1(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_1(3, old0);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_1(4, active0);
            return 5;
        }
        switch (this.curChar) {
            case 'n': {
                return this.jjMoveStringLiteralDfa6_1(active0, 0x100000000000L);
            }
        }
        return this.jjStartNfa_1(4, active0);
    }

    private int jjMoveStringLiteralDfa6_1(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_1(4, old0);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_1(5, active0);
            return 6;
        }
        switch (this.curChar) {
            case 'c': {
                return this.jjMoveStringLiteralDfa7_1(active0, 0x100000000000L);
            }
        }
        return this.jjStartNfa_1(5, active0);
    }

    private int jjMoveStringLiteralDfa7_1(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_1(5, old0);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_1(6, active0);
            return 7;
        }
        switch (this.curChar) {
            case 'e': {
                return this.jjMoveStringLiteralDfa8_1(active0, 0x100000000000L);
            }
        }
        return this.jjStartNfa_1(6, active0);
    }

    private int jjMoveStringLiteralDfa8_1(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_1(6, old0);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_1(7, active0);
            return 8;
        }
        switch (this.curChar) {
            case 'o': {
                return this.jjMoveStringLiteralDfa9_1(active0, 0x100000000000L);
            }
        }
        return this.jjStartNfa_1(7, active0);
    }

    private int jjMoveStringLiteralDfa9_1(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_1(7, old0);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_1(8, active0);
            return 9;
        }
        switch (this.curChar) {
            case 'f': {
                if ((active0 & 0x100000000000L) == 0L) break;
                return this.jjStartNfaWithStates_1(9, 44, 6);
            }
        }
        return this.jjStartNfa_1(8, active0);
    }

    private int jjStartNfaWithStates_1(int pos, int kind, int state) {
        this.jjmatchedKind = kind;
        this.jjmatchedPos = pos;
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            return pos + 1;
        }
        return this.jjMoveNfa_1(state, pos + 1);
    }

    private int jjMoveNfa_1(int startState, int curPos) {
        int startsAt = 0;
        this.jjnewStateCnt = 35;
        int i = 1;
        this.jjstateSet[0] = startState;
        int kind = Integer.MAX_VALUE;
        while (true) {
            if (++this.jjround == Integer.MAX_VALUE) {
                this.ReInitRounds();
            }
            if (this.curChar < '@') {
                long l = 1L << this.curChar;
                block58: do {
                    switch (this.jjstateSet[--i]) {
                        case 0: {
                            if ((0x3FF000000000000L & l) != 0L) {
                                if (kind > 9) {
                                    kind = 9;
                                }
                                this.jjCheckNAddStates(4, 8);
                                break;
                            }
                            if ((0x1800000000L & l) != 0L) {
                                if (kind > 53) {
                                    kind = 53;
                                }
                                this.jjCheckNAdd(6);
                                break;
                            }
                            if (this.curChar == '\'') {
                                this.jjCheckNAddStates(9, 13);
                                break;
                            }
                            if (this.curChar == '\"') {
                                this.jjCheckNAddStates(14, 18);
                                break;
                            }
                            if (this.curChar != '.') break;
                            this.jjCheckNAdd(1);
                            break;
                        }
                        case 1: {
                            if ((0x3FF000000000000L & l) == 0L) continue block58;
                            if (kind > 10) {
                                kind = 10;
                            }
                            this.jjCheckNAddTwoStates(1, 2);
                            break;
                        }
                        case 3: {
                            if ((0x280000000000L & l) == 0L) break;
                            this.jjCheckNAdd(4);
                            break;
                        }
                        case 4: {
                            if ((0x3FF000000000000L & l) == 0L) continue block58;
                            if (kind > 10) {
                                kind = 10;
                            }
                            this.jjCheckNAdd(4);
                            break;
                        }
                        case 5: {
                            if ((0x1800000000L & l) == 0L) continue block58;
                            if (kind > 53) {
                                kind = 53;
                            }
                            this.jjCheckNAdd(6);
                            break;
                        }
                        case 6: {
                            if ((0x3FF001000000000L & l) == 0L) continue block58;
                            if (kind > 53) {
                                kind = 53;
                            }
                            this.jjCheckNAdd(6);
                            break;
                        }
                        case 7: {
                            if ((0x3FF000000000000L & l) == 0L) continue block58;
                            if (kind > 9) {
                                kind = 9;
                            }
                            this.jjCheckNAddStates(4, 8);
                            break;
                        }
                        case 8: {
                            if ((0x3FF000000000000L & l) == 0L) continue block58;
                            if (kind > 9) {
                                kind = 9;
                            }
                            this.jjCheckNAdd(8);
                            break;
                        }
                        case 9: {
                            if ((0x3FF000000000000L & l) == 0L) break;
                            this.jjCheckNAddTwoStates(9, 10);
                            break;
                        }
                        case 10: {
                            if (this.curChar != '.') continue block58;
                            if (kind > 10) {
                                kind = 10;
                            }
                            this.jjCheckNAddTwoStates(11, 12);
                            break;
                        }
                        case 11: {
                            if ((0x3FF000000000000L & l) == 0L) continue block58;
                            if (kind > 10) {
                                kind = 10;
                            }
                            this.jjCheckNAddTwoStates(11, 12);
                            break;
                        }
                        case 13: {
                            if ((0x280000000000L & l) == 0L) break;
                            this.jjCheckNAdd(14);
                            break;
                        }
                        case 14: {
                            if ((0x3FF000000000000L & l) == 0L) continue block58;
                            if (kind > 10) {
                                kind = 10;
                            }
                            this.jjCheckNAdd(14);
                            break;
                        }
                        case 15: {
                            if ((0x3FF000000000000L & l) == 0L) break;
                            this.jjCheckNAddTwoStates(15, 16);
                            break;
                        }
                        case 17: {
                            if ((0x280000000000L & l) == 0L) break;
                            this.jjCheckNAdd(18);
                            break;
                        }
                        case 18: {
                            if ((0x3FF000000000000L & l) == 0L) continue block58;
                            if (kind > 10) {
                                kind = 10;
                            }
                            this.jjCheckNAdd(18);
                            break;
                        }
                        case 19: {
                            if (this.curChar != '\"') break;
                            this.jjCheckNAddStates(14, 18);
                            break;
                        }
                        case 20: {
                            if ((0xFFFFFFFBFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddStates(19, 21);
                            break;
                        }
                        case 22: {
                            if (this.curChar != '\"') break;
                            this.jjCheckNAddStates(19, 21);
                            break;
                        }
                        case 23: {
                            if (this.curChar != '\"' || kind <= 12) continue block58;
                            kind = 12;
                            break;
                        }
                        case 24: {
                            if ((0xFFFFFFFBFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(24, 25);
                            break;
                        }
                        case 26: {
                            if ((0xFFFFFFFBFFFFFFFFL & l) == 0L || kind <= 13) continue block58;
                            kind = 13;
                            break;
                        }
                        case 27: {
                            if (this.curChar != '\'') break;
                            this.jjCheckNAddStates(9, 13);
                            break;
                        }
                        case 28: {
                            if ((0xFFFFFF7FFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddStates(22, 24);
                            break;
                        }
                        case 30: {
                            if (this.curChar != '\'') break;
                            this.jjCheckNAddStates(22, 24);
                            break;
                        }
                        case 31: {
                            if (this.curChar != '\'' || kind <= 12) continue block58;
                            kind = 12;
                            break;
                        }
                        case 32: {
                            if ((0xFFFFFF7FFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(32, 33);
                            break;
                        }
                        case 34: {
                            if ((0xFFFFFF7FFFFFFFFFL & l) == 0L || kind <= 13) continue block58;
                            kind = 13;
                            break;
                        }
                    }
                } while (i != startsAt);
            } else if (this.curChar < '\u0080') {
                long l = 1L << (this.curChar & 0x3F);
                block59: do {
                    switch (this.jjstateSet[--i]) {
                        case 0: 
                        case 6: {
                            if ((0x7FFFFFE87FFFFFEL & l) == 0L) continue block59;
                            if (kind > 53) {
                                kind = 53;
                            }
                            this.jjCheckNAdd(6);
                            break;
                        }
                        case 2: {
                            if ((0x2000000020L & l) == 0L) break;
                            this.jjAddStates(25, 26);
                            break;
                        }
                        case 12: {
                            if ((0x2000000020L & l) == 0L) break;
                            this.jjAddStates(27, 28);
                            break;
                        }
                        case 16: {
                            if ((0x2000000020L & l) == 0L) break;
                            this.jjAddStates(29, 30);
                            break;
                        }
                        case 20: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddStates(19, 21);
                            break;
                        }
                        case 21: {
                            if (this.curChar != '\\') break;
                            this.jjstateSet[this.jjnewStateCnt++] = 22;
                            break;
                        }
                        case 22: {
                            if (this.curChar != '\\') break;
                            this.jjCheckNAddStates(19, 21);
                            break;
                        }
                        case 24: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) == 0L) break;
                            this.jjAddStates(31, 32);
                            break;
                        }
                        case 25: {
                            if (this.curChar != '\\') break;
                            this.jjstateSet[this.jjnewStateCnt++] = 26;
                            break;
                        }
                        case 26: 
                        case 34: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) == 0L || kind <= 13) continue block59;
                            kind = 13;
                            break;
                        }
                        case 28: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddStates(22, 24);
                            break;
                        }
                        case 29: {
                            if (this.curChar != '\\') break;
                            this.jjstateSet[this.jjnewStateCnt++] = 30;
                            break;
                        }
                        case 30: {
                            if (this.curChar != '\\') break;
                            this.jjCheckNAddStates(22, 24);
                            break;
                        }
                        case 32: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) == 0L) break;
                            this.jjAddStates(33, 34);
                            break;
                        }
                        case 33: {
                            if (this.curChar != '\\') break;
                            this.jjstateSet[this.jjnewStateCnt++] = 34;
                            break;
                        }
                    }
                } while (i != startsAt);
            } else {
                int hiByte = this.curChar >> 8;
                int i1 = hiByte >> 6;
                long l1 = 1L << (hiByte & 0x3F);
                int i2 = (this.curChar & 0xFF) >> 6;
                long l2 = 1L << (this.curChar & 0x3F);
                block60: do {
                    switch (this.jjstateSet[--i]) {
                        case 0: 
                        case 6: {
                            if (!ELParserTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block60;
                            if (kind > 53) {
                                kind = 53;
                            }
                            this.jjCheckNAdd(6);
                            break;
                        }
                        case 20: {
                            if (!ELParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) break;
                            this.jjAddStates(19, 21);
                            break;
                        }
                        case 24: {
                            if (!ELParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) break;
                            this.jjAddStates(31, 32);
                            break;
                        }
                        case 26: 
                        case 34: {
                            if (!ELParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2) || kind <= 13) continue block60;
                            kind = 13;
                            break;
                        }
                        case 28: {
                            if (!ELParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) break;
                            this.jjAddStates(22, 24);
                            break;
                        }
                        case 32: {
                            if (!ELParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) break;
                            this.jjAddStates(33, 34);
                            break;
                        }
                    }
                } while (i != startsAt);
            }
            if (kind != Integer.MAX_VALUE) {
                this.jjmatchedKind = kind;
                this.jjmatchedPos = curPos;
                kind = Integer.MAX_VALUE;
            }
            ++curPos;
            i = this.jjnewStateCnt;
            this.jjnewStateCnt = startsAt;
            if (i == (startsAt = 35 - this.jjnewStateCnt)) {
                return curPos;
            }
            try {
                this.curChar = this.input_stream.readChar();
            }
            catch (IOException e) {
                return curPos;
            }
        }
    }

    private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2) {
        switch (hiByte) {
            case 0: {
                return (jjbitVec2[i2] & l2) != 0L;
            }
        }
        return (jjbitVec0[i1] & l1) != 0L;
    }

    private static final boolean jjCanMove_1(int hiByte, int i1, int i2, long l1, long l2) {
        switch (hiByte) {
            case 0: {
                return (jjbitVec4[i2] & l2) != 0L;
            }
            case 48: {
                return (jjbitVec5[i2] & l2) != 0L;
            }
            case 49: {
                return (jjbitVec6[i2] & l2) != 0L;
            }
            case 51: {
                return (jjbitVec7[i2] & l2) != 0L;
            }
            case 61: {
                return (jjbitVec8[i2] & l2) != 0L;
            }
        }
        return (jjbitVec3[i1] & l1) != 0L;
    }

    public ELParserTokenManager(SimpleCharStream stream) {
        this.input_stream = stream;
    }

    public ELParserTokenManager(SimpleCharStream stream, int lexState) {
        this(stream);
        this.SwitchTo(lexState);
    }

    public void ReInit(SimpleCharStream stream) {
        this.jjnewStateCnt = 0;
        this.jjmatchedPos = 0;
        this.curLexState = this.defaultLexState;
        this.input_stream = stream;
        this.ReInitRounds();
    }

    private void ReInitRounds() {
        this.jjround = -2147483647;
        int i = 35;
        while (i-- > 0) {
            this.jjrounds[i] = Integer.MIN_VALUE;
        }
    }

    public void ReInit(SimpleCharStream stream, int lexState) {
        this.ReInit(stream);
        this.SwitchTo(lexState);
    }

    public void SwitchTo(int lexState) {
        if (lexState >= 2 || lexState < 0) {
            throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", 2);
        }
        this.curLexState = lexState;
    }

    protected Token jjFillToken() {
        String im = jjstrLiteralImages[this.jjmatchedKind];
        String curTokenImage = im == null ? this.input_stream.GetImage() : im;
        int beginLine = this.input_stream.getBeginLine();
        int beginColumn = this.input_stream.getBeginColumn();
        int endLine = this.input_stream.getEndLine();
        int endColumn = this.input_stream.getEndColumn();
        Token t = Token.newToken(this.jjmatchedKind);
        t.kind = this.jjmatchedKind;
        t.image = curTokenImage;
        t.beginLine = beginLine;
        t.endLine = endLine;
        t.beginColumn = beginColumn;
        t.endColumn = endColumn;
        return t;
    }

    public Token getNextToken() {
        int curPos = 0;
        block10: while (true) {
            try {
                this.curChar = this.input_stream.BeginToken();
            }
            catch (IOException e) {
                this.jjmatchedKind = 0;
                Token matchedToken = this.jjFillToken();
                return matchedToken;
            }
            switch (this.curLexState) {
                case 0: {
                    this.jjmatchedKind = Integer.MAX_VALUE;
                    this.jjmatchedPos = 0;
                    curPos = this.jjMoveStringLiteralDfa0_0();
                    break;
                }
                case 1: {
                    try {
                        this.input_stream.backup(0);
                        while (this.curChar <= ' ' && (0x100002600L & 1L << this.curChar) != 0L) {
                            this.curChar = this.input_stream.BeginToken();
                        }
                    }
                    catch (IOException e1) {
                        continue block10;
                    }
                    this.jjmatchedKind = Integer.MAX_VALUE;
                    this.jjmatchedPos = 0;
                    curPos = this.jjMoveStringLiteralDfa0_1();
                    if (this.jjmatchedPos != 0 || this.jjmatchedKind <= 57) break;
                    this.jjmatchedKind = 57;
                }
            }
            if (this.jjmatchedKind == Integer.MAX_VALUE) break;
            if (this.jjmatchedPos + 1 < curPos) {
                this.input_stream.backup(curPos - this.jjmatchedPos - 1);
            }
            if ((jjtoToken[this.jjmatchedKind >> 6] & 1L << (this.jjmatchedKind & 0x3F)) != 0L) {
                Token matchedToken = this.jjFillToken();
                if (jjnewLexState[this.jjmatchedKind] != -1) {
                    this.curLexState = jjnewLexState[this.jjmatchedKind];
                }
                return matchedToken;
            }
            if (jjnewLexState[this.jjmatchedKind] == -1) continue;
            this.curLexState = jjnewLexState[this.jjmatchedKind];
        }
        int error_line = this.input_stream.getEndLine();
        int error_column = this.input_stream.getEndColumn();
        String error_after = null;
        boolean EOFSeen = false;
        try {
            this.input_stream.readChar();
            this.input_stream.backup(1);
        }
        catch (IOException e1) {
            EOFSeen = true;
            String string = error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
            if (this.curChar == '\n' || this.curChar == '\r') {
                ++error_line;
                error_column = 0;
            }
            ++error_column;
        }
        if (!EOFSeen) {
            this.input_stream.backup(1);
            error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
        }
        throw new TokenMgrError(EOFSeen, this.curLexState, error_line, error_column, error_after, this.curChar, 0);
    }

    private void jjCheckNAdd(int state) {
        if (this.jjrounds[state] != this.jjround) {
            this.jjstateSet[this.jjnewStateCnt++] = state;
            this.jjrounds[state] = this.jjround;
        }
    }

    private void jjAddStates(int start, int end) {
        do {
            this.jjstateSet[this.jjnewStateCnt++] = jjnextStates[start];
        } while (start++ != end);
    }

    private void jjCheckNAddTwoStates(int state1, int state2) {
        this.jjCheckNAdd(state1);
        this.jjCheckNAdd(state2);
    }

    private void jjCheckNAddStates(int start, int end) {
        do {
            this.jjCheckNAdd(jjnextStates[start]);
        } while (start++ != end);
    }
}

