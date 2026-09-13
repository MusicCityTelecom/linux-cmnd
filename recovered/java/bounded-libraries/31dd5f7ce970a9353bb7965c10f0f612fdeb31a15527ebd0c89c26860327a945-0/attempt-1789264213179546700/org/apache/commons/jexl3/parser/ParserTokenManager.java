/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import java.io.IOException;
import org.apache.commons.jexl3.parser.ParserConstants;
import org.apache.commons.jexl3.parser.SimpleCharStream;
import org.apache.commons.jexl3.parser.StringParser;
import org.apache.commons.jexl3.parser.Token;
import org.apache.commons.jexl3.parser.TokenMgrException;

public class ParserTokenManager
implements ParserConstants {
    int dotLexState = 0;
    static final long[] jjbitVec0 = new long[]{-4294967298L, -1L, -1L, -1L};
    static final long[] jjbitVec2 = new long[]{0L, 0L, -1L, -1L};
    static final long[] jjbitVec3 = new long[]{-3298534883329L, -1L, -1L, -1L};
    static final long[] jjbitVec4 = new long[]{-2L, -1L, -1L, -1L};
    public static final String[] jjstrLiteralImages = new String[]{"", null, null, null, null, null, null, null, null, "if", "else", "for", "while", "do", "new", "var", "empty", "size", "null", "true", "false", "return", "function", "->", "break", "continue", "#pragma", "(", ")", "{", "}", "[", "]", ";", ":", ",", ".", "?.", "...", "?", "?:", "??", "&&", "and", "||", "or", "==", "eq", "!=", "ne", ">", "gt", ">=", "ge", "<", "lt", "<=", "le", "=~", "!~", "=^", "=$", "!^", "!$", "+=", "-=", "*=", "/=", "%=", "&=", "|=", "^=", "=", "+", "-", "*", "/", "div", "%", "mod", "!", "not", "&", "|", "^", "~", "..", "NaN", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null};
    static final int[] jjnextStates = new int[]{68, 69, 71, 36, 37, 42, 43, 44, 14, 61, 67, 52, 59, 21, 22, 24, 16, 17, 19, 48, 50, 8, 53, 54, 56, 10, 11, 14, 26, 27, 29, 32, 33, 34, 38, 39, 14, 42, 43, 44, 14, 63, 64, 66, 12, 13, 40, 41, 45, 46, 30, 36, 15, 16, 18, 10, 11, 13, 37, 38, 40, 1, 2, 4, 20, 21, 23, 26, 27, 28, 32, 33, 35, 38, 39, 44, 45, 46, 16, 63, 69, 54, 61, 23, 24, 26, 18, 19, 21, 50, 52, 10, 70, 71, 73, 55, 56, 58, 12, 13, 16, 28, 29, 31, 34, 35, 36, 40, 41, 16, 44, 45, 46, 16, 65, 66, 68, 14, 15, 42, 43, 47, 48};
    int curLexState = 0;
    int defaultLexState = 0;
    int jjnewStateCnt;
    int jjround;
    int jjmatchedPos;
    int jjmatchedKind;
    public static final String[] lexStateNames = new String[]{"DEFAULT", "DOT_ID", "REGISTERS"};
    public static final int[] jjnewLexState = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    static final long[] jjtoToken = new long[]{-511L, 7768656314367L};
    static final long[] jjtoSkip = new long[]{510L, 0L};
    static final long[] jjtoSpecial = new long[]{0L, 0L};
    static final long[] jjtoMore = new long[]{0L, 0L};
    protected SimpleCharStream input_stream;
    private final int[] jjrounds = new int[74];
    private final int[] jjstateSet = new int[148];
    private final StringBuilder jjimage;
    private StringBuilder image = this.jjimage = new StringBuilder();
    private int jjimageLen;
    private int lengthOfMatch;
    protected int curChar;

    public void pushDot() {
        this.dotLexState = this.curLexState;
        this.curLexState = 1;
    }

    public void popDot() {
        if (this.curLexState == 1) {
            this.curLexState = this.dotLexState;
            this.dotLexState = this.defaultLexState;
        }
    }

    private final int jjStopStringLiteralDfa_0(int pos, long active0, long active1) {
        switch (pos) {
            case 0: {
                if ((active0 & 0x2AAA800037FFE00L) != 0L || (active1 & 0x82A000L) != 0L) {
                    this.jjmatchedKind = 90;
                    return 72;
                }
                if ((active0 & 0x5000000000L) != 0L || (active1 & 0x400000L) != 0L) {
                    return 10;
                }
                if ((active1 & 0x1008L) != 0L) {
                    return 61;
                }
                if ((active1 & 0x200000L) != 0L) {
                    return 31;
                }
                if ((active0 & 0x4000000L) != 0L) {
                    return 52;
                }
                return -1;
            }
            case 1: {
                if ((active0 & 0x2AAA00000006200L) != 0L) {
                    return 72;
                }
                if ((active0 & 0x800037F9C00L) != 0L || (active1 & 0x82A000L) != 0L) {
                    if (this.jjmatchedPos != 1) {
                        this.jjmatchedKind = 90;
                        this.jjmatchedPos = 1;
                    }
                    return 72;
                }
                return -1;
            }
            case 2: {
                if ((active0 & 0x8000000C800L) != 0L || (active1 & 0x82A000L) != 0L) {
                    return 72;
                }
                if ((active0 & 0x37F1400L) != 0L) {
                    this.jjmatchedKind = 90;
                    this.jjmatchedPos = 2;
                    return 72;
                }
                return -1;
            }
            case 3: {
                if ((active0 & 0x3711000L) != 0L) {
                    this.jjmatchedKind = 90;
                    this.jjmatchedPos = 3;
                    return 72;
                }
                if ((active0 & 0xE0400L) != 0L) {
                    return 72;
                }
                return -1;
            }
            case 4: {
                if ((active0 & 0x2600000L) != 0L) {
                    this.jjmatchedKind = 90;
                    this.jjmatchedPos = 4;
                    return 72;
                }
                if ((active0 & 0x1111000L) != 0L) {
                    return 72;
                }
                return -1;
            }
            case 5: {
                if ((active0 & 0x200000L) != 0L) {
                    return 72;
                }
                if ((active0 & 0x2400000L) != 0L) {
                    this.jjmatchedKind = 90;
                    this.jjmatchedPos = 5;
                    return 72;
                }
                return -1;
            }
            case 6: {
                if ((active0 & 0x2400000L) != 0L) {
                    this.jjmatchedKind = 90;
                    this.jjmatchedPos = 6;
                    return 72;
                }
                return -1;
            }
        }
        return -1;
    }

    private final int jjStartNfa_0(int pos, long active0, long active1) {
        return this.jjMoveNfa_0(this.jjStopStringLiteralDfa_0(pos, active0, active1), pos + 1);
    }

    private int jjStopAtPos(int pos, int kind) {
        this.jjmatchedKind = kind;
        this.jjmatchedPos = pos;
        return pos + 1;
    }

    private int jjMoveStringLiteralDfa0_0() {
        switch (this.curChar) {
            case 33: {
                this.jjmatchedKind = 80;
                return this.jjMoveStringLiteralDfa1_0(-4034943791147253760L, 0L);
            }
            case 35: {
                return this.jjMoveStringLiteralDfa1_0(0x4000000L, 0L);
            }
            case 37: {
                this.jjmatchedKind = 78;
                return this.jjMoveStringLiteralDfa1_0(0L, 16L);
            }
            case 38: {
                this.jjmatchedKind = 82;
                return this.jjMoveStringLiteralDfa1_0(0x40000000000L, 32L);
            }
            case 40: {
                return this.jjStopAtPos(0, 27);
            }
            case 41: {
                return this.jjStopAtPos(0, 28);
            }
            case 42: {
                this.jjmatchedKind = 75;
                return this.jjMoveStringLiteralDfa1_0(0L, 4L);
            }
            case 43: {
                this.jjmatchedKind = 73;
                return this.jjMoveStringLiteralDfa1_0(0L, 1L);
            }
            case 44: {
                return this.jjStopAtPos(0, 35);
            }
            case 45: {
                this.jjmatchedKind = 74;
                return this.jjMoveStringLiteralDfa1_0(0x800000L, 2L);
            }
            case 46: {
                this.jjmatchedKind = 36;
                return this.jjMoveStringLiteralDfa1_0(0x4000000000L, 0x400000L);
            }
            case 47: {
                this.jjmatchedKind = 76;
                return this.jjMoveStringLiteralDfa1_0(0L, 8L);
            }
            case 58: {
                return this.jjStopAtPos(0, 34);
            }
            case 59: {
                return this.jjStopAtPos(0, 33);
            }
            case 60: {
                this.jjmatchedKind = 54;
                return this.jjMoveStringLiteralDfa1_0(0x100000000000000L, 0L);
            }
            case 61: {
                this.jjmatchedKind = 72;
                return this.jjMoveStringLiteralDfa1_0(0x3400400000000000L, 0L);
            }
            case 62: {
                this.jjmatchedKind = 50;
                return this.jjMoveStringLiteralDfa1_0(0x10000000000000L, 0L);
            }
            case 63: {
                this.jjmatchedKind = 39;
                return this.jjMoveStringLiteralDfa1_0(0x32000000000L, 0L);
            }
            case 78: {
                return this.jjMoveStringLiteralDfa1_0(0L, 0x800000L);
            }
            case 91: {
                return this.jjStopAtPos(0, 31);
            }
            case 93: {
                return this.jjStopAtPos(0, 32);
            }
            case 94: {
                this.jjmatchedKind = 84;
                return this.jjMoveStringLiteralDfa1_0(0L, 128L);
            }
            case 97: {
                return this.jjMoveStringLiteralDfa1_0(0x80000000000L, 0L);
            }
            case 98: {
                return this.jjMoveStringLiteralDfa1_0(0x1000000L, 0L);
            }
            case 99: {
                return this.jjMoveStringLiteralDfa1_0(0x2000000L, 0L);
            }
            case 100: {
                return this.jjMoveStringLiteralDfa1_0(8192L, 8192L);
            }
            case 101: {
                return this.jjMoveStringLiteralDfa1_0(140737488421888L, 0L);
            }
            case 102: {
                return this.jjMoveStringLiteralDfa1_0(0x500800L, 0L);
            }
            case 103: {
                return this.jjMoveStringLiteralDfa1_0(0x28000000000000L, 0L);
            }
            case 105: {
                return this.jjMoveStringLiteralDfa1_0(512L, 0L);
            }
            case 108: {
                return this.jjMoveStringLiteralDfa1_0(0x280000000000000L, 0L);
            }
            case 109: {
                return this.jjMoveStringLiteralDfa1_0(0L, 32768L);
            }
            case 110: {
                return this.jjMoveStringLiteralDfa1_0(0x2000000044000L, 131072L);
            }
            case 111: {
                return this.jjMoveStringLiteralDfa1_0(0x200000000000L, 0L);
            }
            case 114: {
                return this.jjMoveStringLiteralDfa1_0(0x200000L, 0L);
            }
            case 115: {
                return this.jjMoveStringLiteralDfa1_0(131072L, 0L);
            }
            case 116: {
                return this.jjMoveStringLiteralDfa1_0(524288L, 0L);
            }
            case 118: {
                return this.jjMoveStringLiteralDfa1_0(32768L, 0L);
            }
            case 119: {
                return this.jjMoveStringLiteralDfa1_0(4096L, 0L);
            }
            case 123: {
                return this.jjStopAtPos(0, 29);
            }
            case 124: {
                this.jjmatchedKind = 83;
                return this.jjMoveStringLiteralDfa1_0(0x100000000000L, 64L);
            }
            case 125: {
                return this.jjStopAtPos(0, 30);
            }
            case 126: {
                return this.jjStartNfaWithStates_0(0, 85, 31);
            }
        }
        return this.jjMoveNfa_0(0, 0);
    }

    private int jjMoveStringLiteralDfa1_0(long active0, long active1) {
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_0(0, active0, active1);
            return 1;
        }
        switch (this.curChar) {
            case 36: {
                if ((active0 & 0x2000000000000000L) != 0L) {
                    return this.jjStopAtPos(1, 61);
                }
                if ((active0 & Long.MIN_VALUE) == 0L) break;
                return this.jjStopAtPos(1, 63);
            }
            case 38: {
                if ((active0 & 0x40000000000L) == 0L) break;
                return this.jjStopAtPos(1, 42);
            }
            case 46: {
                if ((active0 & 0x2000000000L) != 0L) {
                    return this.jjStopAtPos(1, 37);
                }
                if ((active1 & 0x400000L) != 0L) {
                    this.jjmatchedKind = 86;
                    this.jjmatchedPos = 1;
                }
                return this.jjMoveStringLiteralDfa2_0(active0, 0x4000000000L, active1, 0L);
            }
            case 58: {
                if ((active0 & 0x10000000000L) == 0L) break;
                return this.jjStopAtPos(1, 40);
            }
            case 61: {
                if ((active0 & 0x400000000000L) != 0L) {
                    return this.jjStopAtPos(1, 46);
                }
                if ((active0 & 0x1000000000000L) != 0L) {
                    return this.jjStopAtPos(1, 48);
                }
                if ((active0 & 0x10000000000000L) != 0L) {
                    return this.jjStopAtPos(1, 52);
                }
                if ((active0 & 0x100000000000000L) != 0L) {
                    return this.jjStopAtPos(1, 56);
                }
                if ((active1 & 1L) != 0L) {
                    return this.jjStopAtPos(1, 64);
                }
                if ((active1 & 2L) != 0L) {
                    return this.jjStopAtPos(1, 65);
                }
                if ((active1 & 4L) != 0L) {
                    return this.jjStopAtPos(1, 66);
                }
                if ((active1 & 8L) != 0L) {
                    return this.jjStopAtPos(1, 67);
                }
                if ((active1 & 0x10L) != 0L) {
                    return this.jjStopAtPos(1, 68);
                }
                if ((active1 & 0x20L) != 0L) {
                    return this.jjStopAtPos(1, 69);
                }
                if ((active1 & 0x40L) != 0L) {
                    return this.jjStopAtPos(1, 70);
                }
                if ((active1 & 0x80L) == 0L) break;
                return this.jjStopAtPos(1, 71);
            }
            case 62: {
                if ((active0 & 0x800000L) == 0L) break;
                return this.jjStopAtPos(1, 23);
            }
            case 63: {
                if ((active0 & 0x20000000000L) == 0L) break;
                return this.jjStopAtPos(1, 41);
            }
            case 94: {
                if ((active0 & 0x1000000000000000L) != 0L) {
                    return this.jjStopAtPos(1, 60);
                }
                if ((active0 & 0x4000000000000000L) == 0L) break;
                return this.jjStopAtPos(1, 62);
            }
            case 97: {
                return this.jjMoveStringLiteralDfa2_0(active0, 0x108000L, active1, 0x800000L);
            }
            case 101: {
                if ((active0 & 0x2000000000000L) != 0L) {
                    this.jjmatchedKind = 49;
                    this.jjmatchedPos = 1;
                } else {
                    if ((active0 & 0x20000000000000L) != 0L) {
                        return this.jjStartNfaWithStates_0(1, 53, 72);
                    }
                    if ((active0 & 0x200000000000000L) != 0L) {
                        return this.jjStartNfaWithStates_0(1, 57, 72);
                    }
                }
                return this.jjMoveStringLiteralDfa2_0(active0, 0x204000L, active1, 0L);
            }
            case 102: {
                if ((active0 & 0x200L) == 0L) break;
                return this.jjStartNfaWithStates_0(1, 9, 72);
            }
            case 104: {
                return this.jjMoveStringLiteralDfa2_0(active0, 4096L, active1, 0L);
            }
            case 105: {
                return this.jjMoveStringLiteralDfa2_0(active0, 131072L, active1, 8192L);
            }
            case 108: {
                return this.jjMoveStringLiteralDfa2_0(active0, 1024L, active1, 0L);
            }
            case 109: {
                return this.jjMoveStringLiteralDfa2_0(active0, 65536L, active1, 0L);
            }
            case 110: {
                return this.jjMoveStringLiteralDfa2_0(active0, 0x80000000000L, active1, 0L);
            }
            case 111: {
                if ((active0 & 0x2000L) != 0L) {
                    return this.jjStartNfaWithStates_0(1, 13, 72);
                }
                return this.jjMoveStringLiteralDfa2_0(active0, 0x2000800L, active1, 163840L);
            }
            case 112: {
                return this.jjMoveStringLiteralDfa2_0(active0, 0x4000000L, active1, 0L);
            }
            case 113: {
                if ((active0 & 0x800000000000L) == 0L) break;
                return this.jjStartNfaWithStates_0(1, 47, 72);
            }
            case 114: {
                if ((active0 & 0x200000000000L) != 0L) {
                    return this.jjStartNfaWithStates_0(1, 45, 72);
                }
                return this.jjMoveStringLiteralDfa2_0(active0, 0x1080000L, active1, 0L);
            }
            case 116: {
                if ((active0 & 0x8000000000000L) != 0L) {
                    return this.jjStartNfaWithStates_0(1, 51, 72);
                }
                if ((active0 & 0x80000000000000L) == 0L) break;
                return this.jjStartNfaWithStates_0(1, 55, 72);
            }
            case 117: {
                return this.jjMoveStringLiteralDfa2_0(active0, 0x440000L, active1, 0L);
            }
            case 124: {
                if ((active0 & 0x100000000000L) == 0L) break;
                return this.jjStopAtPos(1, 44);
            }
            case 126: {
                if ((active0 & 0x400000000000000L) != 0L) {
                    return this.jjStopAtPos(1, 58);
                }
                if ((active0 & 0x800000000000000L) == 0L) break;
                return this.jjStopAtPos(1, 59);
            }
        }
        return this.jjStartNfa_0(0, active0, active1);
    }

    private int jjMoveStringLiteralDfa2_0(long old0, long active0, long old1, long active1) {
        if (((active0 &= old0) | (active1 &= old1)) == 0L) {
            return this.jjStartNfa_0(0, old0, old1);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_0(1, active0, active1);
            return 2;
        }
        switch (this.curChar) {
            case 46: {
                if ((active0 & 0x4000000000L) == 0L) break;
                return this.jjStopAtPos(2, 38);
            }
            case 78: {
                if ((active1 & 0x800000L) == 0L) break;
                return this.jjStartNfaWithStates_0(2, 87, 72);
            }
            case 100: {
                if ((active0 & 0x80000000000L) != 0L) {
                    return this.jjStartNfaWithStates_0(2, 43, 72);
                }
                if ((active1 & 0x8000L) == 0L) break;
                return this.jjStartNfaWithStates_0(2, 79, 72);
            }
            case 101: {
                return this.jjMoveStringLiteralDfa3_0(active0, 0x1000000L, active1, 0L);
            }
            case 105: {
                return this.jjMoveStringLiteralDfa3_0(active0, 4096L, active1, 0L);
            }
            case 108: {
                return this.jjMoveStringLiteralDfa3_0(active0, 0x140000L, active1, 0L);
            }
            case 110: {
                return this.jjMoveStringLiteralDfa3_0(active0, 0x2400000L, active1, 0L);
            }
            case 112: {
                return this.jjMoveStringLiteralDfa3_0(active0, 65536L, active1, 0L);
            }
            case 114: {
                if ((active0 & 0x800L) != 0L) {
                    return this.jjStartNfaWithStates_0(2, 11, 72);
                }
                if ((active0 & 0x8000L) != 0L) {
                    return this.jjStartNfaWithStates_0(2, 15, 72);
                }
                return this.jjMoveStringLiteralDfa3_0(active0, 0x4000000L, active1, 0L);
            }
            case 115: {
                return this.jjMoveStringLiteralDfa3_0(active0, 1024L, active1, 0L);
            }
            case 116: {
                if ((active1 & 0x20000L) != 0L) {
                    return this.jjStartNfaWithStates_0(2, 81, 72);
                }
                return this.jjMoveStringLiteralDfa3_0(active0, 0x200000L, active1, 0L);
            }
            case 117: {
                return this.jjMoveStringLiteralDfa3_0(active0, 524288L, active1, 0L);
            }
            case 118: {
                if ((active1 & 0x2000L) == 0L) break;
                return this.jjStartNfaWithStates_0(2, 77, 72);
            }
            case 119: {
                if ((active0 & 0x4000L) == 0L) break;
                return this.jjStartNfaWithStates_0(2, 14, 72);
            }
            case 122: {
                return this.jjMoveStringLiteralDfa3_0(active0, 131072L, active1, 0L);
            }
        }
        return this.jjStartNfa_0(1, active0, active1);
    }

    private int jjMoveStringLiteralDfa3_0(long old0, long active0, long old1, long active1) {
        if (((active0 &= old0) | (active1 &= old1)) == 0L) {
            return this.jjStartNfa_0(1, old0, old1);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_0(2, active0, 0L);
            return 3;
        }
        switch (this.curChar) {
            case 97: {
                return this.jjMoveStringLiteralDfa4_0(active0, 0x5000000L);
            }
            case 99: {
                return this.jjMoveStringLiteralDfa4_0(active0, 0x400000L);
            }
            case 101: {
                if ((active0 & 0x400L) != 0L) {
                    return this.jjStartNfaWithStates_0(3, 10, 72);
                }
                if ((active0 & 0x20000L) != 0L) {
                    return this.jjStartNfaWithStates_0(3, 17, 72);
                }
                if ((active0 & 0x80000L) == 0L) break;
                return this.jjStartNfaWithStates_0(3, 19, 72);
            }
            case 108: {
                if ((active0 & 0x40000L) != 0L) {
                    return this.jjStartNfaWithStates_0(3, 18, 72);
                }
                return this.jjMoveStringLiteralDfa4_0(active0, 4096L);
            }
            case 115: {
                return this.jjMoveStringLiteralDfa4_0(active0, 0x100000L);
            }
            case 116: {
                return this.jjMoveStringLiteralDfa4_0(active0, 0x2010000L);
            }
            case 117: {
                return this.jjMoveStringLiteralDfa4_0(active0, 0x200000L);
            }
        }
        return this.jjStartNfa_0(2, active0, 0L);
    }

    private int jjMoveStringLiteralDfa4_0(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_0(2, old0, 0L);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_0(3, active0, 0L);
            return 4;
        }
        switch (this.curChar) {
            case 101: {
                if ((active0 & 0x1000L) != 0L) {
                    return this.jjStartNfaWithStates_0(4, 12, 72);
                }
                if ((active0 & 0x100000L) == 0L) break;
                return this.jjStartNfaWithStates_0(4, 20, 72);
            }
            case 103: {
                return this.jjMoveStringLiteralDfa5_0(active0, 0x4000000L);
            }
            case 105: {
                return this.jjMoveStringLiteralDfa5_0(active0, 0x2000000L);
            }
            case 107: {
                if ((active0 & 0x1000000L) == 0L) break;
                return this.jjStartNfaWithStates_0(4, 24, 72);
            }
            case 114: {
                return this.jjMoveStringLiteralDfa5_0(active0, 0x200000L);
            }
            case 116: {
                return this.jjMoveStringLiteralDfa5_0(active0, 0x400000L);
            }
            case 121: {
                if ((active0 & 0x10000L) == 0L) break;
                return this.jjStartNfaWithStates_0(4, 16, 72);
            }
        }
        return this.jjStartNfa_0(3, active0, 0L);
    }

    private int jjMoveStringLiteralDfa5_0(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_0(3, old0, 0L);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_0(4, active0, 0L);
            return 5;
        }
        switch (this.curChar) {
            case 105: {
                return this.jjMoveStringLiteralDfa6_0(active0, 0x400000L);
            }
            case 109: {
                return this.jjMoveStringLiteralDfa6_0(active0, 0x4000000L);
            }
            case 110: {
                if ((active0 & 0x200000L) != 0L) {
                    return this.jjStartNfaWithStates_0(5, 21, 72);
                }
                return this.jjMoveStringLiteralDfa6_0(active0, 0x2000000L);
            }
        }
        return this.jjStartNfa_0(4, active0, 0L);
    }

    private int jjMoveStringLiteralDfa6_0(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_0(4, old0, 0L);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_0(5, active0, 0L);
            return 6;
        }
        switch (this.curChar) {
            case 97: {
                if ((active0 & 0x4000000L) == 0L) break;
                return this.jjStopAtPos(6, 26);
            }
            case 111: {
                return this.jjMoveStringLiteralDfa7_0(active0, 0x400000L);
            }
            case 117: {
                return this.jjMoveStringLiteralDfa7_0(active0, 0x2000000L);
            }
        }
        return this.jjStartNfa_0(5, active0, 0L);
    }

    private int jjMoveStringLiteralDfa7_0(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_0(5, old0, 0L);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_0(6, active0, 0L);
            return 7;
        }
        switch (this.curChar) {
            case 101: {
                if ((active0 & 0x2000000L) == 0L) break;
                return this.jjStartNfaWithStates_0(7, 25, 72);
            }
            case 110: {
                if ((active0 & 0x400000L) == 0L) break;
                return this.jjStartNfaWithStates_0(7, 22, 72);
            }
        }
        return this.jjStartNfa_0(6, active0, 0L);
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
        this.jjnewStateCnt = 72;
        int i = 1;
        this.jjstateSet[0] = startState;
        int kind = Integer.MAX_VALUE;
        while (true) {
            if (++this.jjround == Integer.MAX_VALUE) {
                this.ReInitRounds();
            }
            if (this.curChar < 64) {
                long l = 1L << this.curChar;
                block107: do {
                    switch (this.jjstateSet[--i]) {
                        case 61: {
                            if (this.curChar == 47) {
                                if (kind > 3) {
                                    kind = 3;
                                }
                                this.jjCheckNAddStates(0, 2);
                                break;
                            }
                            if (this.curChar != 42) break;
                            this.jjCheckNAddTwoStates(62, 63);
                            break;
                        }
                        case 0: {
                            if ((0x3FF000000000000L & l) != 0L) {
                                this.jjCheckNAddStates(3, 8);
                            } else if (this.curChar == 47) {
                                this.jjAddStates(9, 10);
                            } else if (this.curChar == 35) {
                                this.jjAddStates(11, 12);
                            } else if (this.curChar == 39) {
                                this.jjCheckNAddStates(13, 15);
                            } else if (this.curChar == 34) {
                                this.jjCheckNAddStates(16, 18);
                            } else if (this.curChar == 46) {
                                this.jjCheckNAdd(10);
                            } else if (this.curChar == 36) {
                                if (kind > 90) {
                                    kind = 90;
                                }
                                this.jjCheckNAddTwoStates(3, 4);
                            }
                            if ((0x3FE000000000000L & l) != 0L) {
                                if (kind > 95) {
                                    kind = 95;
                                }
                                this.jjCheckNAddTwoStates(7, 8);
                                break;
                            }
                            if (this.curChar != 48) break;
                            if (kind > 95) {
                                kind = 95;
                            }
                            this.jjCheckNAddStates(19, 21);
                            break;
                        }
                        case 3: 
                        case 72: {
                            if ((0x3FF001000000000L & l) == 0L) continue block107;
                            if (kind > 90) {
                                kind = 90;
                            }
                            this.jjCheckNAddTwoStates(3, 4);
                            break;
                        }
                        case 52: {
                            if (this.curChar != 35) continue block107;
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjCheckNAddStates(22, 24);
                            break;
                        }
                        case 1: {
                            if ((0x3FF001000000000L & l) == 0L) continue block107;
                            if (kind > 88) {
                                kind = 88;
                            }
                            this.jjstateSet[this.jjnewStateCnt++] = 1;
                            break;
                        }
                        case 2: {
                            if (this.curChar != 36) continue block107;
                            if (kind > 90) {
                                kind = 90;
                            }
                            this.jjCheckNAddTwoStates(3, 4);
                            break;
                        }
                        case 5: {
                            if ((0x8500000000L & l) == 0L) continue block107;
                            if (kind > 90) {
                                kind = 90;
                            }
                            this.jjCheckNAddTwoStates(3, 4);
                            break;
                        }
                        case 6: {
                            if ((0x3FE000000000000L & l) == 0L) continue block107;
                            if (kind > 95) {
                                kind = 95;
                            }
                            this.jjCheckNAddTwoStates(7, 8);
                            break;
                        }
                        case 7: {
                            if ((0x3FF000000000000L & l) == 0L) continue block107;
                            if (kind > 95) {
                                kind = 95;
                            }
                            this.jjCheckNAddTwoStates(7, 8);
                            break;
                        }
                        case 9: {
                            if (this.curChar != 46) break;
                            this.jjCheckNAdd(10);
                            break;
                        }
                        case 10: {
                            if ((0x3FF000000000000L & l) == 0L) break;
                            this.jjCheckNAddStates(25, 27);
                            break;
                        }
                        case 12: {
                            if ((0x280000000000L & l) == 0L) break;
                            this.jjCheckNAdd(13);
                            break;
                        }
                        case 13: {
                            if ((0x3FF000000000000L & l) == 0L) continue block107;
                            if (kind > 100) {
                                kind = 100;
                            }
                            this.jjCheckNAddTwoStates(13, 14);
                            break;
                        }
                        case 15: {
                            if (this.curChar != 34) break;
                            this.jjCheckNAddStates(16, 18);
                            break;
                        }
                        case 16: {
                            if ((0xFFFFFFFBFFFFDBFFL & l) == 0L) break;
                            this.jjCheckNAddStates(16, 18);
                            break;
                        }
                        case 18: {
                            if ((0xFFFFFFFFFFFFDBFFL & l) == 0L) break;
                            this.jjCheckNAddStates(16, 18);
                            break;
                        }
                        case 19: {
                            if (this.curChar != 34 || kind <= 104) continue block107;
                            kind = 104;
                            break;
                        }
                        case 20: {
                            if (this.curChar != 39) break;
                            this.jjCheckNAddStates(13, 15);
                            break;
                        }
                        case 21: {
                            if ((0xFFFFFF7FFFFFDBFFL & l) == 0L) break;
                            this.jjCheckNAddStates(13, 15);
                            break;
                        }
                        case 23: {
                            if ((0xFFFFFFFFFFFFDBFFL & l) == 0L) break;
                            this.jjCheckNAddStates(13, 15);
                            break;
                        }
                        case 24: {
                            if (this.curChar != 39 || kind <= 104) continue block107;
                            kind = 104;
                            break;
                        }
                        case 26: {
                            this.jjCheckNAddStates(28, 30);
                            break;
                        }
                        case 28: {
                            if ((0xFFFFFFFFFFFFFFFEL & l) == 0L) break;
                            this.jjCheckNAddStates(28, 30);
                            break;
                        }
                        case 31: {
                            if (this.curChar != 47) break;
                            this.jjCheckNAddStates(31, 33);
                            break;
                        }
                        case 32: {
                            if ((0xFFFF7FFFFFFFDBFFL & l) == 0L) break;
                            this.jjCheckNAddStates(31, 33);
                            break;
                        }
                        case 34: {
                            if (this.curChar != 47 || kind <= 106) continue block107;
                            kind = 106;
                            break;
                        }
                        case 35: {
                            if ((0x3FF000000000000L & l) == 0L) break;
                            this.jjCheckNAddStates(3, 8);
                            break;
                        }
                        case 36: {
                            if ((0x3FF000000000000L & l) == 0L) break;
                            this.jjCheckNAddTwoStates(36, 37);
                            break;
                        }
                        case 37: {
                            if (this.curChar != 46) break;
                            this.jjCheckNAdd(38);
                            break;
                        }
                        case 38: {
                            if ((0x3FF000000000000L & l) == 0L) continue block107;
                            if (kind > 100) {
                                kind = 100;
                            }
                            this.jjCheckNAddStates(34, 36);
                            break;
                        }
                        case 40: {
                            if ((0x280000000000L & l) == 0L) break;
                            this.jjCheckNAdd(41);
                            break;
                        }
                        case 41: {
                            if ((0x3FF000000000000L & l) == 0L) continue block107;
                            if (kind > 100) {
                                kind = 100;
                            }
                            this.jjCheckNAddTwoStates(41, 14);
                            break;
                        }
                        case 42: {
                            if ((0x3FF000000000000L & l) == 0L) break;
                            this.jjCheckNAddStates(37, 40);
                            break;
                        }
                        case 43: {
                            if (this.curChar != 46) break;
                            this.jjCheckNAddTwoStates(44, 14);
                            break;
                        }
                        case 45: {
                            if ((0x280000000000L & l) == 0L) break;
                            this.jjCheckNAdd(46);
                            break;
                        }
                        case 46: {
                            if ((0x3FF000000000000L & l) == 0L) continue block107;
                            if (kind > 100) {
                                kind = 100;
                            }
                            this.jjCheckNAddTwoStates(46, 14);
                            break;
                        }
                        case 47: {
                            if (this.curChar != 48) continue block107;
                            if (kind > 95) {
                                kind = 95;
                            }
                            this.jjCheckNAddStates(19, 21);
                            break;
                        }
                        case 49: {
                            if ((0x3FF000000000000L & l) == 0L) continue block107;
                            if (kind > 95) {
                                kind = 95;
                            }
                            this.jjCheckNAddTwoStates(49, 8);
                            break;
                        }
                        case 50: {
                            if ((0xFF000000000000L & l) == 0L) continue block107;
                            if (kind > 95) {
                                kind = 95;
                            }
                            this.jjCheckNAddTwoStates(50, 8);
                            break;
                        }
                        case 51: {
                            if (this.curChar != 35) break;
                            this.jjAddStates(11, 12);
                            break;
                        }
                        case 53: {
                            if ((0xFFFFFFFFFFFFDBFFL & l) == 0L) continue block107;
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjCheckNAddStates(22, 24);
                            break;
                        }
                        case 54: {
                            if ((0x2400L & l) == 0L || kind <= 1) continue block107;
                            kind = 1;
                            break;
                        }
                        case 55: {
                            if (this.curChar != 10 || kind <= 1) continue block107;
                            kind = 1;
                            break;
                        }
                        case 56: {
                            if (this.curChar != 13) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 55;
                            break;
                        }
                        case 60: {
                            if (this.curChar != 47) break;
                            this.jjAddStates(9, 10);
                            break;
                        }
                        case 62: {
                            if ((0xFFFFFBFFFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(62, 63);
                            break;
                        }
                        case 63: {
                            if (this.curChar != 42) break;
                            this.jjCheckNAddStates(41, 43);
                            break;
                        }
                        case 64: {
                            if ((0xFFFF7BFFFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(65, 63);
                            break;
                        }
                        case 65: {
                            if ((0xFFFFFBFFFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(65, 63);
                            break;
                        }
                        case 66: {
                            if (this.curChar != 47 || kind <= 2) continue block107;
                            kind = 2;
                            break;
                        }
                        case 67: {
                            if (this.curChar != 47) continue block107;
                            if (kind > 3) {
                                kind = 3;
                            }
                            this.jjCheckNAddStates(0, 2);
                            break;
                        }
                        case 68: {
                            if ((0xFFFFFFFFFFFFDBFFL & l) == 0L) continue block107;
                            if (kind > 3) {
                                kind = 3;
                            }
                            this.jjCheckNAddStates(0, 2);
                            break;
                        }
                        case 69: {
                            if ((0x2400L & l) == 0L || kind <= 3) continue block107;
                            kind = 3;
                            break;
                        }
                        case 70: {
                            if (this.curChar != 10 || kind <= 3) continue block107;
                            kind = 3;
                            break;
                        }
                        case 71: {
                            if (this.curChar != 13) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 70;
                            break;
                        }
                    }
                } while (i != startsAt);
            } else if (this.curChar < 128) {
                long l = 1L << (this.curChar & 0x3F);
                block108: do {
                    switch (this.jjstateSet[--i]) {
                        case 0: {
                            if ((0x7FFFFFE87FFFFFFL & l) != 0L) {
                                if (kind > 90) {
                                    kind = 90;
                                }
                                this.jjCheckNAddTwoStates(3, 4);
                            } else if (this.curChar == 126) {
                                this.jjCheckNAdd(31);
                            } else if (this.curChar == 96) {
                                this.jjCheckNAddStates(28, 30);
                            }
                            if (this.curChar != 64) break;
                            this.jjCheckNAdd(1);
                            break;
                        }
                        case 72: {
                            if ((0x7FFFFFE87FFFFFFL & l) != 0L) {
                                if (kind > 90) {
                                    kind = 90;
                                }
                                this.jjCheckNAddTwoStates(3, 4);
                                break;
                            }
                            if (this.curChar != 92) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 5;
                            break;
                        }
                        case 52: {
                            if (this.curChar != 78) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 58;
                            break;
                        }
                        case 1: {
                            if ((0x7FFFFFE87FFFFFEL & l) == 0L) continue block108;
                            if (kind > 88) {
                                kind = 88;
                            }
                            this.jjCheckNAdd(1);
                            break;
                        }
                        case 2: {
                            if ((0x7FFFFFE87FFFFFFL & l) == 0L) continue block108;
                            if (kind > 90) {
                                kind = 90;
                            }
                            this.jjCheckNAddTwoStates(3, 4);
                            break;
                        }
                        case 3: {
                            if ((0x7FFFFFE87FFFFFFL & l) == 0L) continue block108;
                            if (kind > 90) {
                                kind = 90;
                            }
                            this.jjCheckNAddTwoStates(3, 4);
                            break;
                        }
                        case 4: {
                            if (this.curChar != 92) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 5;
                            break;
                        }
                        case 5: {
                            if (this.curChar != 92) continue block108;
                            if (kind > 90) {
                                kind = 90;
                            }
                            this.jjCheckNAddTwoStates(3, 4);
                            break;
                        }
                        case 8: {
                            if ((0x110000001100L & l) == 0L || kind <= 95) continue block108;
                            kind = 95;
                            break;
                        }
                        case 11: {
                            if ((0x2000000020L & l) == 0L) break;
                            this.jjAddStates(44, 45);
                            break;
                        }
                        case 14: {
                            if ((0x5400000054L & l) == 0L || kind <= 100) continue block108;
                            kind = 100;
                            break;
                        }
                        case 16: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddStates(16, 18);
                            break;
                        }
                        case 17: {
                            if (this.curChar != 92) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 18;
                            break;
                        }
                        case 18: {
                            this.jjCheckNAddStates(16, 18);
                            break;
                        }
                        case 21: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddStates(13, 15);
                            break;
                        }
                        case 22: {
                            if (this.curChar != 92) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 23;
                            break;
                        }
                        case 23: {
                            this.jjCheckNAddStates(13, 15);
                            break;
                        }
                        case 25: {
                            if (this.curChar != 96) break;
                            this.jjCheckNAddStates(28, 30);
                            break;
                        }
                        case 26: {
                            if ((0xFFFFFFFEEFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddStates(28, 30);
                            break;
                        }
                        case 27: {
                            if (this.curChar != 92) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 28;
                            break;
                        }
                        case 28: {
                            this.jjCheckNAddStates(28, 30);
                            break;
                        }
                        case 29: {
                            if (this.curChar != 96 || kind <= 105) continue block108;
                            kind = 105;
                            break;
                        }
                        case 30: {
                            if (this.curChar != 126) break;
                            this.jjCheckNAdd(31);
                            break;
                        }
                        case 32: {
                            this.jjAddStates(31, 33);
                            break;
                        }
                        case 33: {
                            if (this.curChar != 92) break;
                            this.jjCheckNAdd(31);
                            break;
                        }
                        case 39: {
                            if ((0x2000000020L & l) == 0L) break;
                            this.jjAddStates(46, 47);
                            break;
                        }
                        case 44: {
                            if ((0x2000000020L & l) == 0L) break;
                            this.jjAddStates(48, 49);
                            break;
                        }
                        case 48: {
                            if ((0x100000001000000L & l) == 0L) break;
                            this.jjCheckNAdd(49);
                            break;
                        }
                        case 49: {
                            if ((0x7E0000007EL & l) == 0L) continue block108;
                            if (kind > 95) {
                                kind = 95;
                            }
                            this.jjCheckNAddTwoStates(49, 8);
                            break;
                        }
                        case 53: {
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjAddStates(22, 24);
                            break;
                        }
                        case 57: {
                            if (this.curChar != 78 || kind <= 100) continue block108;
                            kind = 100;
                            break;
                        }
                        case 58: {
                            if (this.curChar != 97) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 57;
                            break;
                        }
                        case 62: {
                            this.jjCheckNAddTwoStates(62, 63);
                            break;
                        }
                        case 64: 
                        case 65: {
                            this.jjCheckNAddTwoStates(65, 63);
                            break;
                        }
                        case 68: {
                            if (kind > 3) {
                                kind = 3;
                            }
                            this.jjAddStates(0, 2);
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
                block109: do {
                    switch (this.jjstateSet[--i]) {
                        case 16: 
                        case 18: {
                            if (!ParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) continue block109;
                            this.jjCheckNAddStates(16, 18);
                            break;
                        }
                        case 21: 
                        case 23: {
                            if (!ParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) continue block109;
                            this.jjCheckNAddStates(13, 15);
                            break;
                        }
                        case 26: 
                        case 28: {
                            if (!ParserTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block109;
                            this.jjCheckNAddStates(28, 30);
                            break;
                        }
                        case 32: {
                            if (!ParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) continue block109;
                            this.jjAddStates(31, 33);
                            break;
                        }
                        case 53: {
                            if (!ParserTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block109;
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjAddStates(22, 24);
                            break;
                        }
                        case 62: {
                            if (!ParserTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block109;
                            this.jjCheckNAddTwoStates(62, 63);
                            break;
                        }
                        case 64: 
                        case 65: {
                            if (!ParserTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block109;
                            this.jjCheckNAddTwoStates(65, 63);
                            break;
                        }
                        case 68: {
                            if (!ParserTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block109;
                            if (kind > 3) {
                                kind = 3;
                            }
                            this.jjAddStates(0, 2);
                            break;
                        }
                        default: {
                            if (i1 != 0 && l1 != 0L && i2 != 0 && l2 != 0L) continue block109;
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
            startsAt = 72 - this.jjnewStateCnt;
            if (i == startsAt) {
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

    private final int jjStopStringLiteralDfa_1(int pos, long active0, long active1) {
        switch (pos) {
            case 0: {
                if ((active1 & 0x1008L) != 0L) {
                    return 30;
                }
                if ((active1 & 0x200000L) != 0L) {
                    return 25;
                }
                if ((active0 & 0x2AAA800037FFE00L) != 0L || (active1 & 0x82A000L) != 0L) {
                    this.jjmatchedKind = 89;
                    return 8;
                }
                if ((active0 & 0x4000000L) != 0L) {
                    return 0;
                }
                return -1;
            }
            case 1: {
                if ((active0 & 0x2AAA00000006200L) != 0L) {
                    return 8;
                }
                if ((active0 & 0x800037F9C00L) != 0L || (active1 & 0x82A000L) != 0L) {
                    if (this.jjmatchedPos != 1) {
                        this.jjmatchedKind = 89;
                        this.jjmatchedPos = 1;
                    }
                    return 8;
                }
                return -1;
            }
            case 2: {
                if ((active0 & 0x8000000C800L) != 0L || (active1 & 0x82A000L) != 0L) {
                    return 8;
                }
                if ((active0 & 0x37F1400L) != 0L) {
                    this.jjmatchedKind = 89;
                    this.jjmatchedPos = 2;
                    return 8;
                }
                return -1;
            }
            case 3: {
                if ((active0 & 0xE0400L) != 0L) {
                    return 8;
                }
                if ((active0 & 0x3711000L) != 0L) {
                    this.jjmatchedKind = 89;
                    this.jjmatchedPos = 3;
                    return 8;
                }
                return -1;
            }
            case 4: {
                if ((active0 & 0x2600000L) != 0L) {
                    this.jjmatchedKind = 89;
                    this.jjmatchedPos = 4;
                    return 8;
                }
                if ((active0 & 0x1111000L) != 0L) {
                    return 8;
                }
                return -1;
            }
            case 5: {
                if ((active0 & 0x200000L) != 0L) {
                    return 8;
                }
                if ((active0 & 0x2400000L) != 0L) {
                    this.jjmatchedKind = 89;
                    this.jjmatchedPos = 5;
                    return 8;
                }
                return -1;
            }
            case 6: {
                if ((active0 & 0x2400000L) != 0L) {
                    this.jjmatchedKind = 89;
                    this.jjmatchedPos = 6;
                    return 8;
                }
                return -1;
            }
        }
        return -1;
    }

    private final int jjStartNfa_1(int pos, long active0, long active1) {
        return this.jjMoveNfa_1(this.jjStopStringLiteralDfa_1(pos, active0, active1), pos + 1);
    }

    private int jjMoveStringLiteralDfa0_1() {
        switch (this.curChar) {
            case 33: {
                this.jjmatchedKind = 80;
                return this.jjMoveStringLiteralDfa1_1(-4034943791147253760L, 0L);
            }
            case 35: {
                return this.jjMoveStringLiteralDfa1_1(0x4000000L, 0L);
            }
            case 37: {
                this.jjmatchedKind = 78;
                return this.jjMoveStringLiteralDfa1_1(0L, 16L);
            }
            case 38: {
                this.jjmatchedKind = 82;
                return this.jjMoveStringLiteralDfa1_1(0x40000000000L, 32L);
            }
            case 40: {
                return this.jjStopAtPos(0, 27);
            }
            case 41: {
                return this.jjStopAtPos(0, 28);
            }
            case 42: {
                this.jjmatchedKind = 75;
                return this.jjMoveStringLiteralDfa1_1(0L, 4L);
            }
            case 43: {
                this.jjmatchedKind = 73;
                return this.jjMoveStringLiteralDfa1_1(0L, 1L);
            }
            case 44: {
                return this.jjStopAtPos(0, 35);
            }
            case 45: {
                this.jjmatchedKind = 74;
                return this.jjMoveStringLiteralDfa1_1(0x800000L, 2L);
            }
            case 46: {
                this.jjmatchedKind = 36;
                return this.jjMoveStringLiteralDfa1_1(0x4000000000L, 0x400000L);
            }
            case 47: {
                this.jjmatchedKind = 76;
                return this.jjMoveStringLiteralDfa1_1(0L, 8L);
            }
            case 58: {
                return this.jjStopAtPos(0, 34);
            }
            case 59: {
                return this.jjStopAtPos(0, 33);
            }
            case 60: {
                this.jjmatchedKind = 54;
                return this.jjMoveStringLiteralDfa1_1(0x100000000000000L, 0L);
            }
            case 61: {
                this.jjmatchedKind = 72;
                return this.jjMoveStringLiteralDfa1_1(0x3400400000000000L, 0L);
            }
            case 62: {
                this.jjmatchedKind = 50;
                return this.jjMoveStringLiteralDfa1_1(0x10000000000000L, 0L);
            }
            case 63: {
                this.jjmatchedKind = 39;
                return this.jjMoveStringLiteralDfa1_1(0x32000000000L, 0L);
            }
            case 78: {
                return this.jjMoveStringLiteralDfa1_1(0L, 0x800000L);
            }
            case 91: {
                return this.jjStopAtPos(0, 31);
            }
            case 93: {
                return this.jjStopAtPos(0, 32);
            }
            case 94: {
                this.jjmatchedKind = 84;
                return this.jjMoveStringLiteralDfa1_1(0L, 128L);
            }
            case 97: {
                return this.jjMoveStringLiteralDfa1_1(0x80000000000L, 0L);
            }
            case 98: {
                return this.jjMoveStringLiteralDfa1_1(0x1000000L, 0L);
            }
            case 99: {
                return this.jjMoveStringLiteralDfa1_1(0x2000000L, 0L);
            }
            case 100: {
                return this.jjMoveStringLiteralDfa1_1(8192L, 8192L);
            }
            case 101: {
                return this.jjMoveStringLiteralDfa1_1(140737488421888L, 0L);
            }
            case 102: {
                return this.jjMoveStringLiteralDfa1_1(0x500800L, 0L);
            }
            case 103: {
                return this.jjMoveStringLiteralDfa1_1(0x28000000000000L, 0L);
            }
            case 105: {
                return this.jjMoveStringLiteralDfa1_1(512L, 0L);
            }
            case 108: {
                return this.jjMoveStringLiteralDfa1_1(0x280000000000000L, 0L);
            }
            case 109: {
                return this.jjMoveStringLiteralDfa1_1(0L, 32768L);
            }
            case 110: {
                return this.jjMoveStringLiteralDfa1_1(0x2000000044000L, 131072L);
            }
            case 111: {
                return this.jjMoveStringLiteralDfa1_1(0x200000000000L, 0L);
            }
            case 114: {
                return this.jjMoveStringLiteralDfa1_1(0x200000L, 0L);
            }
            case 115: {
                return this.jjMoveStringLiteralDfa1_1(131072L, 0L);
            }
            case 116: {
                return this.jjMoveStringLiteralDfa1_1(524288L, 0L);
            }
            case 118: {
                return this.jjMoveStringLiteralDfa1_1(32768L, 0L);
            }
            case 119: {
                return this.jjMoveStringLiteralDfa1_1(4096L, 0L);
            }
            case 123: {
                return this.jjStopAtPos(0, 29);
            }
            case 124: {
                this.jjmatchedKind = 83;
                return this.jjMoveStringLiteralDfa1_1(0x100000000000L, 64L);
            }
            case 125: {
                return this.jjStopAtPos(0, 30);
            }
            case 126: {
                return this.jjStartNfaWithStates_1(0, 85, 25);
            }
        }
        return this.jjMoveNfa_1(5, 0);
    }

    private int jjMoveStringLiteralDfa1_1(long active0, long active1) {
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_1(0, active0, active1);
            return 1;
        }
        switch (this.curChar) {
            case 36: {
                if ((active0 & 0x2000000000000000L) != 0L) {
                    return this.jjStopAtPos(1, 61);
                }
                if ((active0 & Long.MIN_VALUE) == 0L) break;
                return this.jjStopAtPos(1, 63);
            }
            case 38: {
                if ((active0 & 0x40000000000L) == 0L) break;
                return this.jjStopAtPos(1, 42);
            }
            case 46: {
                if ((active0 & 0x2000000000L) != 0L) {
                    return this.jjStopAtPos(1, 37);
                }
                if ((active1 & 0x400000L) != 0L) {
                    this.jjmatchedKind = 86;
                    this.jjmatchedPos = 1;
                }
                return this.jjMoveStringLiteralDfa2_1(active0, 0x4000000000L, active1, 0L);
            }
            case 58: {
                if ((active0 & 0x10000000000L) == 0L) break;
                return this.jjStopAtPos(1, 40);
            }
            case 61: {
                if ((active0 & 0x400000000000L) != 0L) {
                    return this.jjStopAtPos(1, 46);
                }
                if ((active0 & 0x1000000000000L) != 0L) {
                    return this.jjStopAtPos(1, 48);
                }
                if ((active0 & 0x10000000000000L) != 0L) {
                    return this.jjStopAtPos(1, 52);
                }
                if ((active0 & 0x100000000000000L) != 0L) {
                    return this.jjStopAtPos(1, 56);
                }
                if ((active1 & 1L) != 0L) {
                    return this.jjStopAtPos(1, 64);
                }
                if ((active1 & 2L) != 0L) {
                    return this.jjStopAtPos(1, 65);
                }
                if ((active1 & 4L) != 0L) {
                    return this.jjStopAtPos(1, 66);
                }
                if ((active1 & 8L) != 0L) {
                    return this.jjStopAtPos(1, 67);
                }
                if ((active1 & 0x10L) != 0L) {
                    return this.jjStopAtPos(1, 68);
                }
                if ((active1 & 0x20L) != 0L) {
                    return this.jjStopAtPos(1, 69);
                }
                if ((active1 & 0x40L) != 0L) {
                    return this.jjStopAtPos(1, 70);
                }
                if ((active1 & 0x80L) == 0L) break;
                return this.jjStopAtPos(1, 71);
            }
            case 62: {
                if ((active0 & 0x800000L) == 0L) break;
                return this.jjStopAtPos(1, 23);
            }
            case 63: {
                if ((active0 & 0x20000000000L) == 0L) break;
                return this.jjStopAtPos(1, 41);
            }
            case 94: {
                if ((active0 & 0x1000000000000000L) != 0L) {
                    return this.jjStopAtPos(1, 60);
                }
                if ((active0 & 0x4000000000000000L) == 0L) break;
                return this.jjStopAtPos(1, 62);
            }
            case 97: {
                return this.jjMoveStringLiteralDfa2_1(active0, 0x108000L, active1, 0x800000L);
            }
            case 101: {
                if ((active0 & 0x2000000000000L) != 0L) {
                    this.jjmatchedKind = 49;
                    this.jjmatchedPos = 1;
                } else {
                    if ((active0 & 0x20000000000000L) != 0L) {
                        return this.jjStartNfaWithStates_1(1, 53, 8);
                    }
                    if ((active0 & 0x200000000000000L) != 0L) {
                        return this.jjStartNfaWithStates_1(1, 57, 8);
                    }
                }
                return this.jjMoveStringLiteralDfa2_1(active0, 0x204000L, active1, 0L);
            }
            case 102: {
                if ((active0 & 0x200L) == 0L) break;
                return this.jjStartNfaWithStates_1(1, 9, 8);
            }
            case 104: {
                return this.jjMoveStringLiteralDfa2_1(active0, 4096L, active1, 0L);
            }
            case 105: {
                return this.jjMoveStringLiteralDfa2_1(active0, 131072L, active1, 8192L);
            }
            case 108: {
                return this.jjMoveStringLiteralDfa2_1(active0, 1024L, active1, 0L);
            }
            case 109: {
                return this.jjMoveStringLiteralDfa2_1(active0, 65536L, active1, 0L);
            }
            case 110: {
                return this.jjMoveStringLiteralDfa2_1(active0, 0x80000000000L, active1, 0L);
            }
            case 111: {
                if ((active0 & 0x2000L) != 0L) {
                    return this.jjStartNfaWithStates_1(1, 13, 8);
                }
                return this.jjMoveStringLiteralDfa2_1(active0, 0x2000800L, active1, 163840L);
            }
            case 112: {
                return this.jjMoveStringLiteralDfa2_1(active0, 0x4000000L, active1, 0L);
            }
            case 113: {
                if ((active0 & 0x800000000000L) == 0L) break;
                return this.jjStartNfaWithStates_1(1, 47, 8);
            }
            case 114: {
                if ((active0 & 0x200000000000L) != 0L) {
                    return this.jjStartNfaWithStates_1(1, 45, 8);
                }
                return this.jjMoveStringLiteralDfa2_1(active0, 0x1080000L, active1, 0L);
            }
            case 116: {
                if ((active0 & 0x8000000000000L) != 0L) {
                    return this.jjStartNfaWithStates_1(1, 51, 8);
                }
                if ((active0 & 0x80000000000000L) == 0L) break;
                return this.jjStartNfaWithStates_1(1, 55, 8);
            }
            case 117: {
                return this.jjMoveStringLiteralDfa2_1(active0, 0x440000L, active1, 0L);
            }
            case 124: {
                if ((active0 & 0x100000000000L) == 0L) break;
                return this.jjStopAtPos(1, 44);
            }
            case 126: {
                if ((active0 & 0x400000000000000L) != 0L) {
                    return this.jjStopAtPos(1, 58);
                }
                if ((active0 & 0x800000000000000L) == 0L) break;
                return this.jjStopAtPos(1, 59);
            }
        }
        return this.jjStartNfa_1(0, active0, active1);
    }

    private int jjMoveStringLiteralDfa2_1(long old0, long active0, long old1, long active1) {
        if (((active0 &= old0) | (active1 &= old1)) == 0L) {
            return this.jjStartNfa_1(0, old0, old1);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_1(1, active0, active1);
            return 2;
        }
        switch (this.curChar) {
            case 46: {
                if ((active0 & 0x4000000000L) == 0L) break;
                return this.jjStopAtPos(2, 38);
            }
            case 78: {
                if ((active1 & 0x800000L) == 0L) break;
                return this.jjStartNfaWithStates_1(2, 87, 8);
            }
            case 100: {
                if ((active0 & 0x80000000000L) != 0L) {
                    return this.jjStartNfaWithStates_1(2, 43, 8);
                }
                if ((active1 & 0x8000L) == 0L) break;
                return this.jjStartNfaWithStates_1(2, 79, 8);
            }
            case 101: {
                return this.jjMoveStringLiteralDfa3_1(active0, 0x1000000L, active1, 0L);
            }
            case 105: {
                return this.jjMoveStringLiteralDfa3_1(active0, 4096L, active1, 0L);
            }
            case 108: {
                return this.jjMoveStringLiteralDfa3_1(active0, 0x140000L, active1, 0L);
            }
            case 110: {
                return this.jjMoveStringLiteralDfa3_1(active0, 0x2400000L, active1, 0L);
            }
            case 112: {
                return this.jjMoveStringLiteralDfa3_1(active0, 65536L, active1, 0L);
            }
            case 114: {
                if ((active0 & 0x800L) != 0L) {
                    return this.jjStartNfaWithStates_1(2, 11, 8);
                }
                if ((active0 & 0x8000L) != 0L) {
                    return this.jjStartNfaWithStates_1(2, 15, 8);
                }
                return this.jjMoveStringLiteralDfa3_1(active0, 0x4000000L, active1, 0L);
            }
            case 115: {
                return this.jjMoveStringLiteralDfa3_1(active0, 1024L, active1, 0L);
            }
            case 116: {
                if ((active1 & 0x20000L) != 0L) {
                    return this.jjStartNfaWithStates_1(2, 81, 8);
                }
                return this.jjMoveStringLiteralDfa3_1(active0, 0x200000L, active1, 0L);
            }
            case 117: {
                return this.jjMoveStringLiteralDfa3_1(active0, 524288L, active1, 0L);
            }
            case 118: {
                if ((active1 & 0x2000L) == 0L) break;
                return this.jjStartNfaWithStates_1(2, 77, 8);
            }
            case 119: {
                if ((active0 & 0x4000L) == 0L) break;
                return this.jjStartNfaWithStates_1(2, 14, 8);
            }
            case 122: {
                return this.jjMoveStringLiteralDfa3_1(active0, 131072L, active1, 0L);
            }
        }
        return this.jjStartNfa_1(1, active0, active1);
    }

    private int jjMoveStringLiteralDfa3_1(long old0, long active0, long old1, long active1) {
        if (((active0 &= old0) | (active1 &= old1)) == 0L) {
            return this.jjStartNfa_1(1, old0, old1);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_1(2, active0, 0L);
            return 3;
        }
        switch (this.curChar) {
            case 97: {
                return this.jjMoveStringLiteralDfa4_1(active0, 0x5000000L);
            }
            case 99: {
                return this.jjMoveStringLiteralDfa4_1(active0, 0x400000L);
            }
            case 101: {
                if ((active0 & 0x400L) != 0L) {
                    return this.jjStartNfaWithStates_1(3, 10, 8);
                }
                if ((active0 & 0x20000L) != 0L) {
                    return this.jjStartNfaWithStates_1(3, 17, 8);
                }
                if ((active0 & 0x80000L) == 0L) break;
                return this.jjStartNfaWithStates_1(3, 19, 8);
            }
            case 108: {
                if ((active0 & 0x40000L) != 0L) {
                    return this.jjStartNfaWithStates_1(3, 18, 8);
                }
                return this.jjMoveStringLiteralDfa4_1(active0, 4096L);
            }
            case 115: {
                return this.jjMoveStringLiteralDfa4_1(active0, 0x100000L);
            }
            case 116: {
                return this.jjMoveStringLiteralDfa4_1(active0, 0x2010000L);
            }
            case 117: {
                return this.jjMoveStringLiteralDfa4_1(active0, 0x200000L);
            }
        }
        return this.jjStartNfa_1(2, active0, 0L);
    }

    private int jjMoveStringLiteralDfa4_1(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_1(2, old0, 0L);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_1(3, active0, 0L);
            return 4;
        }
        switch (this.curChar) {
            case 101: {
                if ((active0 & 0x1000L) != 0L) {
                    return this.jjStartNfaWithStates_1(4, 12, 8);
                }
                if ((active0 & 0x100000L) == 0L) break;
                return this.jjStartNfaWithStates_1(4, 20, 8);
            }
            case 103: {
                return this.jjMoveStringLiteralDfa5_1(active0, 0x4000000L);
            }
            case 105: {
                return this.jjMoveStringLiteralDfa5_1(active0, 0x2000000L);
            }
            case 107: {
                if ((active0 & 0x1000000L) == 0L) break;
                return this.jjStartNfaWithStates_1(4, 24, 8);
            }
            case 114: {
                return this.jjMoveStringLiteralDfa5_1(active0, 0x200000L);
            }
            case 116: {
                return this.jjMoveStringLiteralDfa5_1(active0, 0x400000L);
            }
            case 121: {
                if ((active0 & 0x10000L) == 0L) break;
                return this.jjStartNfaWithStates_1(4, 16, 8);
            }
        }
        return this.jjStartNfa_1(3, active0, 0L);
    }

    private int jjMoveStringLiteralDfa5_1(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_1(3, old0, 0L);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_1(4, active0, 0L);
            return 5;
        }
        switch (this.curChar) {
            case 105: {
                return this.jjMoveStringLiteralDfa6_1(active0, 0x400000L);
            }
            case 109: {
                return this.jjMoveStringLiteralDfa6_1(active0, 0x4000000L);
            }
            case 110: {
                if ((active0 & 0x200000L) != 0L) {
                    return this.jjStartNfaWithStates_1(5, 21, 8);
                }
                return this.jjMoveStringLiteralDfa6_1(active0, 0x2000000L);
            }
        }
        return this.jjStartNfa_1(4, active0, 0L);
    }

    private int jjMoveStringLiteralDfa6_1(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_1(4, old0, 0L);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_1(5, active0, 0L);
            return 6;
        }
        switch (this.curChar) {
            case 97: {
                if ((active0 & 0x4000000L) == 0L) break;
                return this.jjStopAtPos(6, 26);
            }
            case 111: {
                return this.jjMoveStringLiteralDfa7_1(active0, 0x400000L);
            }
            case 117: {
                return this.jjMoveStringLiteralDfa7_1(active0, 0x2000000L);
            }
        }
        return this.jjStartNfa_1(5, active0, 0L);
    }

    private int jjMoveStringLiteralDfa7_1(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_1(5, old0, 0L);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_1(6, active0, 0L);
            return 7;
        }
        switch (this.curChar) {
            case 101: {
                if ((active0 & 0x2000000L) == 0L) break;
                return this.jjStartNfaWithStates_1(7, 25, 8);
            }
            case 110: {
                if ((active0 & 0x400000L) == 0L) break;
                return this.jjStartNfaWithStates_1(7, 22, 8);
            }
        }
        return this.jjStartNfa_1(6, active0, 0L);
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
        this.jjnewStateCnt = 41;
        int i = 1;
        this.jjstateSet[0] = startState;
        int kind = Integer.MAX_VALUE;
        while (true) {
            if (++this.jjround == Integer.MAX_VALUE) {
                this.ReInitRounds();
            }
            if (this.curChar < 64) {
                long l = 1L << this.curChar;
                block72: do {
                    switch (this.jjstateSet[--i]) {
                        case 5: {
                            if ((0x3FF001000000000L & l) != 0L) {
                                if (kind > 89) {
                                    kind = 89;
                                }
                                this.jjCheckNAdd(8);
                                break;
                            }
                            if (this.curChar == 47) {
                                this.jjAddStates(50, 51);
                                break;
                            }
                            if (this.curChar == 39) {
                                this.jjCheckNAddStates(52, 54);
                                break;
                            }
                            if (this.curChar == 34) {
                                this.jjCheckNAddStates(55, 57);
                                break;
                            }
                            if (this.curChar != 35) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 0;
                            break;
                        }
                        case 30: {
                            if (this.curChar == 47) {
                                if (kind > 3) {
                                    kind = 3;
                                }
                                this.jjCheckNAddStates(58, 60);
                                break;
                            }
                            if (this.curChar != 42) break;
                            this.jjCheckNAddTwoStates(31, 32);
                            break;
                        }
                        case 0: {
                            if (this.curChar != 35) continue block72;
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjCheckNAddStates(61, 63);
                            break;
                        }
                        case 1: {
                            if ((0xFFFFFFFFFFFFDBFFL & l) == 0L) continue block72;
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjCheckNAddStates(61, 63);
                            break;
                        }
                        case 2: {
                            if ((0x2400L & l) == 0L || kind <= 1) continue block72;
                            kind = 1;
                            break;
                        }
                        case 3: {
                            if (this.curChar != 10 || kind <= 1) continue block72;
                            kind = 1;
                            break;
                        }
                        case 4: {
                            if (this.curChar != 13) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 3;
                            break;
                        }
                        case 7: {
                            if ((0x3FF001000000000L & l) == 0L) continue block72;
                            if (kind > 88) {
                                kind = 88;
                            }
                            this.jjstateSet[this.jjnewStateCnt++] = 7;
                            break;
                        }
                        case 8: {
                            if ((0x3FF001000000000L & l) == 0L) continue block72;
                            if (kind > 89) {
                                kind = 89;
                            }
                            this.jjCheckNAdd(8);
                            break;
                        }
                        case 9: {
                            if (this.curChar != 34) break;
                            this.jjCheckNAddStates(55, 57);
                            break;
                        }
                        case 10: {
                            if ((0xFFFFFFFBFFFFDBFFL & l) == 0L) break;
                            this.jjCheckNAddStates(55, 57);
                            break;
                        }
                        case 12: {
                            if ((0xFFFFFFFFFFFFDBFFL & l) == 0L) break;
                            this.jjCheckNAddStates(55, 57);
                            break;
                        }
                        case 13: {
                            if (this.curChar != 34 || kind <= 104) continue block72;
                            kind = 104;
                            break;
                        }
                        case 14: {
                            if (this.curChar != 39) break;
                            this.jjCheckNAddStates(52, 54);
                            break;
                        }
                        case 15: {
                            if ((0xFFFFFF7FFFFFDBFFL & l) == 0L) break;
                            this.jjCheckNAddStates(52, 54);
                            break;
                        }
                        case 17: {
                            if ((0xFFFFFFFFFFFFDBFFL & l) == 0L) break;
                            this.jjCheckNAddStates(52, 54);
                            break;
                        }
                        case 18: {
                            if (this.curChar != 39 || kind <= 104) continue block72;
                            kind = 104;
                            break;
                        }
                        case 20: {
                            this.jjCheckNAddStates(64, 66);
                            break;
                        }
                        case 22: {
                            if ((0xFFFFFFFFFFFFFFFEL & l) == 0L) break;
                            this.jjCheckNAddStates(64, 66);
                            break;
                        }
                        case 25: {
                            if (this.curChar != 47) break;
                            this.jjCheckNAddStates(67, 69);
                            break;
                        }
                        case 26: {
                            if ((0xFFFF7FFFFFFFDBFFL & l) == 0L) break;
                            this.jjCheckNAddStates(67, 69);
                            break;
                        }
                        case 28: {
                            if (this.curChar != 47 || kind <= 106) continue block72;
                            kind = 106;
                            break;
                        }
                        case 29: {
                            if (this.curChar != 47) break;
                            this.jjAddStates(50, 51);
                            break;
                        }
                        case 31: {
                            if ((0xFFFFFBFFFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(31, 32);
                            break;
                        }
                        case 32: {
                            if (this.curChar != 42) break;
                            this.jjCheckNAddStates(70, 72);
                            break;
                        }
                        case 33: {
                            if ((0xFFFF7BFFFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(34, 32);
                            break;
                        }
                        case 34: {
                            if ((0xFFFFFBFFFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(34, 32);
                            break;
                        }
                        case 35: {
                            if (this.curChar != 47 || kind <= 2) continue block72;
                            kind = 2;
                            break;
                        }
                        case 36: {
                            if (this.curChar != 47) continue block72;
                            if (kind > 3) {
                                kind = 3;
                            }
                            this.jjCheckNAddStates(58, 60);
                            break;
                        }
                        case 37: {
                            if ((0xFFFFFFFFFFFFDBFFL & l) == 0L) continue block72;
                            if (kind > 3) {
                                kind = 3;
                            }
                            this.jjCheckNAddStates(58, 60);
                            break;
                        }
                        case 38: {
                            if ((0x2400L & l) == 0L || kind <= 3) continue block72;
                            kind = 3;
                            break;
                        }
                        case 39: {
                            if (this.curChar != 10 || kind <= 3) continue block72;
                            kind = 3;
                            break;
                        }
                        case 40: {
                            if (this.curChar != 13) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 39;
                            break;
                        }
                    }
                } while (i != startsAt);
            } else if (this.curChar < 128) {
                long l = 1L << (this.curChar & 0x3F);
                block73: do {
                    switch (this.jjstateSet[--i]) {
                        case 5: {
                            if ((0x7FFFFFE87FFFFFFL & l) != 0L) {
                                if (kind > 89) {
                                    kind = 89;
                                }
                                this.jjCheckNAdd(8);
                            } else if (this.curChar == 126) {
                                this.jjCheckNAdd(25);
                            } else if (this.curChar == 96) {
                                this.jjCheckNAddStates(64, 66);
                            }
                            if (this.curChar != 64) break;
                            this.jjCheckNAdd(7);
                            break;
                        }
                        case 1: {
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjAddStates(61, 63);
                            break;
                        }
                        case 6: {
                            if (this.curChar != 64) break;
                            this.jjCheckNAdd(7);
                            break;
                        }
                        case 7: {
                            if ((0x7FFFFFE87FFFFFEL & l) == 0L) continue block73;
                            if (kind > 88) {
                                kind = 88;
                            }
                            this.jjCheckNAdd(7);
                            break;
                        }
                        case 8: {
                            if ((0x7FFFFFE87FFFFFFL & l) == 0L) continue block73;
                            if (kind > 89) {
                                kind = 89;
                            }
                            this.jjCheckNAdd(8);
                            break;
                        }
                        case 10: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddStates(55, 57);
                            break;
                        }
                        case 11: {
                            if (this.curChar != 92) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 12;
                            break;
                        }
                        case 12: {
                            this.jjCheckNAddStates(55, 57);
                            break;
                        }
                        case 15: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddStates(52, 54);
                            break;
                        }
                        case 16: {
                            if (this.curChar != 92) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 17;
                            break;
                        }
                        case 17: {
                            this.jjCheckNAddStates(52, 54);
                            break;
                        }
                        case 19: {
                            if (this.curChar != 96) break;
                            this.jjCheckNAddStates(64, 66);
                            break;
                        }
                        case 20: {
                            if ((0xFFFFFFFEEFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddStates(64, 66);
                            break;
                        }
                        case 21: {
                            if (this.curChar != 92) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 22;
                            break;
                        }
                        case 22: {
                            this.jjCheckNAddStates(64, 66);
                            break;
                        }
                        case 23: {
                            if (this.curChar != 96 || kind <= 105) continue block73;
                            kind = 105;
                            break;
                        }
                        case 24: {
                            if (this.curChar != 126) break;
                            this.jjCheckNAdd(25);
                            break;
                        }
                        case 26: {
                            this.jjAddStates(67, 69);
                            break;
                        }
                        case 27: {
                            if (this.curChar != 92) break;
                            this.jjCheckNAdd(25);
                            break;
                        }
                        case 31: {
                            this.jjCheckNAddTwoStates(31, 32);
                            break;
                        }
                        case 33: 
                        case 34: {
                            this.jjCheckNAddTwoStates(34, 32);
                            break;
                        }
                        case 37: {
                            if (kind > 3) {
                                kind = 3;
                            }
                            this.jjAddStates(58, 60);
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
                block74: do {
                    switch (this.jjstateSet[--i]) {
                        case 1: {
                            if (!ParserTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block74;
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjAddStates(61, 63);
                            break;
                        }
                        case 10: 
                        case 12: {
                            if (!ParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) continue block74;
                            this.jjCheckNAddStates(55, 57);
                            break;
                        }
                        case 15: 
                        case 17: {
                            if (!ParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) continue block74;
                            this.jjCheckNAddStates(52, 54);
                            break;
                        }
                        case 20: 
                        case 22: {
                            if (!ParserTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block74;
                            this.jjCheckNAddStates(64, 66);
                            break;
                        }
                        case 26: {
                            if (!ParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) continue block74;
                            this.jjAddStates(67, 69);
                            break;
                        }
                        case 31: {
                            if (!ParserTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block74;
                            this.jjCheckNAddTwoStates(31, 32);
                            break;
                        }
                        case 33: 
                        case 34: {
                            if (!ParserTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block74;
                            this.jjCheckNAddTwoStates(34, 32);
                            break;
                        }
                        case 37: {
                            if (!ParserTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block74;
                            if (kind > 3) {
                                kind = 3;
                            }
                            this.jjAddStates(58, 60);
                            break;
                        }
                        default: {
                            if (i1 != 0 && l1 != 0L && i2 != 0 && l2 != 0L) continue block74;
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
            startsAt = 41 - this.jjnewStateCnt;
            if (i == startsAt) {
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

    private final int jjStopStringLiteralDfa_2(int pos, long active0, long active1) {
        switch (pos) {
            case 0: {
                if ((active0 & 0x2AAA800037FFE00L) != 0L || (active1 & 0x82A000L) != 0L) {
                    this.jjmatchedKind = 90;
                    return 74;
                }
                if ((active0 & 0x4000000L) != 0L) {
                    return 54;
                }
                if ((active0 & 0x5000000000L) != 0L || (active1 & 0x400000L) != 0L) {
                    return 12;
                }
                if ((active1 & 0x200000L) != 0L) {
                    return 33;
                }
                if ((active1 & 0x1008L) != 0L) {
                    return 63;
                }
                return -1;
            }
            case 1: {
                if ((active0 & 0x2AAA00000006200L) != 0L) {
                    return 74;
                }
                if ((active0 & 0x800037F9C00L) != 0L || (active1 & 0x82A000L) != 0L) {
                    if (this.jjmatchedPos != 1) {
                        this.jjmatchedKind = 90;
                        this.jjmatchedPos = 1;
                    }
                    return 74;
                }
                return -1;
            }
            case 2: {
                if ((active0 & 0x8000000C800L) != 0L || (active1 & 0x82A000L) != 0L) {
                    return 74;
                }
                if ((active0 & 0x37F1400L) != 0L) {
                    this.jjmatchedKind = 90;
                    this.jjmatchedPos = 2;
                    return 74;
                }
                return -1;
            }
            case 3: {
                if ((active0 & 0x3711000L) != 0L) {
                    this.jjmatchedKind = 90;
                    this.jjmatchedPos = 3;
                    return 74;
                }
                if ((active0 & 0xE0400L) != 0L) {
                    return 74;
                }
                return -1;
            }
            case 4: {
                if ((active0 & 0x2600000L) != 0L) {
                    this.jjmatchedKind = 90;
                    this.jjmatchedPos = 4;
                    return 74;
                }
                if ((active0 & 0x1111000L) != 0L) {
                    return 74;
                }
                return -1;
            }
            case 5: {
                if ((active0 & 0x200000L) != 0L) {
                    return 74;
                }
                if ((active0 & 0x2400000L) != 0L) {
                    this.jjmatchedKind = 90;
                    this.jjmatchedPos = 5;
                    return 74;
                }
                return -1;
            }
            case 6: {
                if ((active0 & 0x2400000L) != 0L) {
                    this.jjmatchedKind = 90;
                    this.jjmatchedPos = 6;
                    return 74;
                }
                return -1;
            }
        }
        return -1;
    }

    private final int jjStartNfa_2(int pos, long active0, long active1) {
        return this.jjMoveNfa_2(this.jjStopStringLiteralDfa_2(pos, active0, active1), pos + 1);
    }

    private int jjMoveStringLiteralDfa0_2() {
        switch (this.curChar) {
            case 33: {
                this.jjmatchedKind = 80;
                return this.jjMoveStringLiteralDfa1_2(-4034943791147253760L, 0L);
            }
            case 35: {
                return this.jjMoveStringLiteralDfa1_2(0x4000000L, 0L);
            }
            case 37: {
                this.jjmatchedKind = 78;
                return this.jjMoveStringLiteralDfa1_2(0L, 16L);
            }
            case 38: {
                this.jjmatchedKind = 82;
                return this.jjMoveStringLiteralDfa1_2(0x40000000000L, 32L);
            }
            case 40: {
                return this.jjStopAtPos(0, 27);
            }
            case 41: {
                return this.jjStopAtPos(0, 28);
            }
            case 42: {
                this.jjmatchedKind = 75;
                return this.jjMoveStringLiteralDfa1_2(0L, 4L);
            }
            case 43: {
                this.jjmatchedKind = 73;
                return this.jjMoveStringLiteralDfa1_2(0L, 1L);
            }
            case 44: {
                return this.jjStopAtPos(0, 35);
            }
            case 45: {
                this.jjmatchedKind = 74;
                return this.jjMoveStringLiteralDfa1_2(0x800000L, 2L);
            }
            case 46: {
                this.jjmatchedKind = 36;
                return this.jjMoveStringLiteralDfa1_2(0x4000000000L, 0x400000L);
            }
            case 47: {
                this.jjmatchedKind = 76;
                return this.jjMoveStringLiteralDfa1_2(0L, 8L);
            }
            case 58: {
                return this.jjStopAtPos(0, 34);
            }
            case 59: {
                return this.jjStopAtPos(0, 33);
            }
            case 60: {
                this.jjmatchedKind = 54;
                return this.jjMoveStringLiteralDfa1_2(0x100000000000000L, 0L);
            }
            case 61: {
                this.jjmatchedKind = 72;
                return this.jjMoveStringLiteralDfa1_2(0x3400400000000000L, 0L);
            }
            case 62: {
                this.jjmatchedKind = 50;
                return this.jjMoveStringLiteralDfa1_2(0x10000000000000L, 0L);
            }
            case 63: {
                this.jjmatchedKind = 39;
                return this.jjMoveStringLiteralDfa1_2(0x32000000000L, 0L);
            }
            case 78: {
                return this.jjMoveStringLiteralDfa1_2(0L, 0x800000L);
            }
            case 91: {
                return this.jjStopAtPos(0, 31);
            }
            case 93: {
                return this.jjStopAtPos(0, 32);
            }
            case 94: {
                this.jjmatchedKind = 84;
                return this.jjMoveStringLiteralDfa1_2(0L, 128L);
            }
            case 97: {
                return this.jjMoveStringLiteralDfa1_2(0x80000000000L, 0L);
            }
            case 98: {
                return this.jjMoveStringLiteralDfa1_2(0x1000000L, 0L);
            }
            case 99: {
                return this.jjMoveStringLiteralDfa1_2(0x2000000L, 0L);
            }
            case 100: {
                return this.jjMoveStringLiteralDfa1_2(8192L, 8192L);
            }
            case 101: {
                return this.jjMoveStringLiteralDfa1_2(140737488421888L, 0L);
            }
            case 102: {
                return this.jjMoveStringLiteralDfa1_2(0x500800L, 0L);
            }
            case 103: {
                return this.jjMoveStringLiteralDfa1_2(0x28000000000000L, 0L);
            }
            case 105: {
                return this.jjMoveStringLiteralDfa1_2(512L, 0L);
            }
            case 108: {
                return this.jjMoveStringLiteralDfa1_2(0x280000000000000L, 0L);
            }
            case 109: {
                return this.jjMoveStringLiteralDfa1_2(0L, 32768L);
            }
            case 110: {
                return this.jjMoveStringLiteralDfa1_2(0x2000000044000L, 131072L);
            }
            case 111: {
                return this.jjMoveStringLiteralDfa1_2(0x200000000000L, 0L);
            }
            case 114: {
                return this.jjMoveStringLiteralDfa1_2(0x200000L, 0L);
            }
            case 115: {
                return this.jjMoveStringLiteralDfa1_2(131072L, 0L);
            }
            case 116: {
                return this.jjMoveStringLiteralDfa1_2(524288L, 0L);
            }
            case 118: {
                return this.jjMoveStringLiteralDfa1_2(32768L, 0L);
            }
            case 119: {
                return this.jjMoveStringLiteralDfa1_2(4096L, 0L);
            }
            case 123: {
                return this.jjStopAtPos(0, 29);
            }
            case 124: {
                this.jjmatchedKind = 83;
                return this.jjMoveStringLiteralDfa1_2(0x100000000000L, 64L);
            }
            case 125: {
                return this.jjStopAtPos(0, 30);
            }
            case 126: {
                return this.jjStartNfaWithStates_2(0, 85, 33);
            }
        }
        return this.jjMoveNfa_2(0, 0);
    }

    private int jjMoveStringLiteralDfa1_2(long active0, long active1) {
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_2(0, active0, active1);
            return 1;
        }
        switch (this.curChar) {
            case 36: {
                if ((active0 & 0x2000000000000000L) != 0L) {
                    return this.jjStopAtPos(1, 61);
                }
                if ((active0 & Long.MIN_VALUE) == 0L) break;
                return this.jjStopAtPos(1, 63);
            }
            case 38: {
                if ((active0 & 0x40000000000L) == 0L) break;
                return this.jjStopAtPos(1, 42);
            }
            case 46: {
                if ((active0 & 0x2000000000L) != 0L) {
                    return this.jjStopAtPos(1, 37);
                }
                if ((active1 & 0x400000L) != 0L) {
                    this.jjmatchedKind = 86;
                    this.jjmatchedPos = 1;
                }
                return this.jjMoveStringLiteralDfa2_2(active0, 0x4000000000L, active1, 0L);
            }
            case 58: {
                if ((active0 & 0x10000000000L) == 0L) break;
                return this.jjStopAtPos(1, 40);
            }
            case 61: {
                if ((active0 & 0x400000000000L) != 0L) {
                    return this.jjStopAtPos(1, 46);
                }
                if ((active0 & 0x1000000000000L) != 0L) {
                    return this.jjStopAtPos(1, 48);
                }
                if ((active0 & 0x10000000000000L) != 0L) {
                    return this.jjStopAtPos(1, 52);
                }
                if ((active0 & 0x100000000000000L) != 0L) {
                    return this.jjStopAtPos(1, 56);
                }
                if ((active1 & 1L) != 0L) {
                    return this.jjStopAtPos(1, 64);
                }
                if ((active1 & 2L) != 0L) {
                    return this.jjStopAtPos(1, 65);
                }
                if ((active1 & 4L) != 0L) {
                    return this.jjStopAtPos(1, 66);
                }
                if ((active1 & 8L) != 0L) {
                    return this.jjStopAtPos(1, 67);
                }
                if ((active1 & 0x10L) != 0L) {
                    return this.jjStopAtPos(1, 68);
                }
                if ((active1 & 0x20L) != 0L) {
                    return this.jjStopAtPos(1, 69);
                }
                if ((active1 & 0x40L) != 0L) {
                    return this.jjStopAtPos(1, 70);
                }
                if ((active1 & 0x80L) == 0L) break;
                return this.jjStopAtPos(1, 71);
            }
            case 62: {
                if ((active0 & 0x800000L) == 0L) break;
                return this.jjStopAtPos(1, 23);
            }
            case 63: {
                if ((active0 & 0x20000000000L) == 0L) break;
                return this.jjStopAtPos(1, 41);
            }
            case 94: {
                if ((active0 & 0x1000000000000000L) != 0L) {
                    return this.jjStopAtPos(1, 60);
                }
                if ((active0 & 0x4000000000000000L) == 0L) break;
                return this.jjStopAtPos(1, 62);
            }
            case 97: {
                return this.jjMoveStringLiteralDfa2_2(active0, 0x108000L, active1, 0x800000L);
            }
            case 101: {
                if ((active0 & 0x2000000000000L) != 0L) {
                    this.jjmatchedKind = 49;
                    this.jjmatchedPos = 1;
                } else {
                    if ((active0 & 0x20000000000000L) != 0L) {
                        return this.jjStartNfaWithStates_2(1, 53, 74);
                    }
                    if ((active0 & 0x200000000000000L) != 0L) {
                        return this.jjStartNfaWithStates_2(1, 57, 74);
                    }
                }
                return this.jjMoveStringLiteralDfa2_2(active0, 0x204000L, active1, 0L);
            }
            case 102: {
                if ((active0 & 0x200L) == 0L) break;
                return this.jjStartNfaWithStates_2(1, 9, 74);
            }
            case 104: {
                return this.jjMoveStringLiteralDfa2_2(active0, 4096L, active1, 0L);
            }
            case 105: {
                return this.jjMoveStringLiteralDfa2_2(active0, 131072L, active1, 8192L);
            }
            case 108: {
                return this.jjMoveStringLiteralDfa2_2(active0, 1024L, active1, 0L);
            }
            case 109: {
                return this.jjMoveStringLiteralDfa2_2(active0, 65536L, active1, 0L);
            }
            case 110: {
                return this.jjMoveStringLiteralDfa2_2(active0, 0x80000000000L, active1, 0L);
            }
            case 111: {
                if ((active0 & 0x2000L) != 0L) {
                    return this.jjStartNfaWithStates_2(1, 13, 74);
                }
                return this.jjMoveStringLiteralDfa2_2(active0, 0x2000800L, active1, 163840L);
            }
            case 112: {
                return this.jjMoveStringLiteralDfa2_2(active0, 0x4000000L, active1, 0L);
            }
            case 113: {
                if ((active0 & 0x800000000000L) == 0L) break;
                return this.jjStartNfaWithStates_2(1, 47, 74);
            }
            case 114: {
                if ((active0 & 0x200000000000L) != 0L) {
                    return this.jjStartNfaWithStates_2(1, 45, 74);
                }
                return this.jjMoveStringLiteralDfa2_2(active0, 0x1080000L, active1, 0L);
            }
            case 116: {
                if ((active0 & 0x8000000000000L) != 0L) {
                    return this.jjStartNfaWithStates_2(1, 51, 74);
                }
                if ((active0 & 0x80000000000000L) == 0L) break;
                return this.jjStartNfaWithStates_2(1, 55, 74);
            }
            case 117: {
                return this.jjMoveStringLiteralDfa2_2(active0, 0x440000L, active1, 0L);
            }
            case 124: {
                if ((active0 & 0x100000000000L) == 0L) break;
                return this.jjStopAtPos(1, 44);
            }
            case 126: {
                if ((active0 & 0x400000000000000L) != 0L) {
                    return this.jjStopAtPos(1, 58);
                }
                if ((active0 & 0x800000000000000L) == 0L) break;
                return this.jjStopAtPos(1, 59);
            }
        }
        return this.jjStartNfa_2(0, active0, active1);
    }

    private int jjMoveStringLiteralDfa2_2(long old0, long active0, long old1, long active1) {
        if (((active0 &= old0) | (active1 &= old1)) == 0L) {
            return this.jjStartNfa_2(0, old0, old1);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_2(1, active0, active1);
            return 2;
        }
        switch (this.curChar) {
            case 46: {
                if ((active0 & 0x4000000000L) == 0L) break;
                return this.jjStopAtPos(2, 38);
            }
            case 78: {
                if ((active1 & 0x800000L) == 0L) break;
                return this.jjStartNfaWithStates_2(2, 87, 74);
            }
            case 100: {
                if ((active0 & 0x80000000000L) != 0L) {
                    return this.jjStartNfaWithStates_2(2, 43, 74);
                }
                if ((active1 & 0x8000L) == 0L) break;
                return this.jjStartNfaWithStates_2(2, 79, 74);
            }
            case 101: {
                return this.jjMoveStringLiteralDfa3_2(active0, 0x1000000L, active1, 0L);
            }
            case 105: {
                return this.jjMoveStringLiteralDfa3_2(active0, 4096L, active1, 0L);
            }
            case 108: {
                return this.jjMoveStringLiteralDfa3_2(active0, 0x140000L, active1, 0L);
            }
            case 110: {
                return this.jjMoveStringLiteralDfa3_2(active0, 0x2400000L, active1, 0L);
            }
            case 112: {
                return this.jjMoveStringLiteralDfa3_2(active0, 65536L, active1, 0L);
            }
            case 114: {
                if ((active0 & 0x800L) != 0L) {
                    return this.jjStartNfaWithStates_2(2, 11, 74);
                }
                if ((active0 & 0x8000L) != 0L) {
                    return this.jjStartNfaWithStates_2(2, 15, 74);
                }
                return this.jjMoveStringLiteralDfa3_2(active0, 0x4000000L, active1, 0L);
            }
            case 115: {
                return this.jjMoveStringLiteralDfa3_2(active0, 1024L, active1, 0L);
            }
            case 116: {
                if ((active1 & 0x20000L) != 0L) {
                    return this.jjStartNfaWithStates_2(2, 81, 74);
                }
                return this.jjMoveStringLiteralDfa3_2(active0, 0x200000L, active1, 0L);
            }
            case 117: {
                return this.jjMoveStringLiteralDfa3_2(active0, 524288L, active1, 0L);
            }
            case 118: {
                if ((active1 & 0x2000L) == 0L) break;
                return this.jjStartNfaWithStates_2(2, 77, 74);
            }
            case 119: {
                if ((active0 & 0x4000L) == 0L) break;
                return this.jjStartNfaWithStates_2(2, 14, 74);
            }
            case 122: {
                return this.jjMoveStringLiteralDfa3_2(active0, 131072L, active1, 0L);
            }
        }
        return this.jjStartNfa_2(1, active0, active1);
    }

    private int jjMoveStringLiteralDfa3_2(long old0, long active0, long old1, long active1) {
        if (((active0 &= old0) | (active1 &= old1)) == 0L) {
            return this.jjStartNfa_2(1, old0, old1);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_2(2, active0, 0L);
            return 3;
        }
        switch (this.curChar) {
            case 97: {
                return this.jjMoveStringLiteralDfa4_2(active0, 0x5000000L);
            }
            case 99: {
                return this.jjMoveStringLiteralDfa4_2(active0, 0x400000L);
            }
            case 101: {
                if ((active0 & 0x400L) != 0L) {
                    return this.jjStartNfaWithStates_2(3, 10, 74);
                }
                if ((active0 & 0x20000L) != 0L) {
                    return this.jjStartNfaWithStates_2(3, 17, 74);
                }
                if ((active0 & 0x80000L) == 0L) break;
                return this.jjStartNfaWithStates_2(3, 19, 74);
            }
            case 108: {
                if ((active0 & 0x40000L) != 0L) {
                    return this.jjStartNfaWithStates_2(3, 18, 74);
                }
                return this.jjMoveStringLiteralDfa4_2(active0, 4096L);
            }
            case 115: {
                return this.jjMoveStringLiteralDfa4_2(active0, 0x100000L);
            }
            case 116: {
                return this.jjMoveStringLiteralDfa4_2(active0, 0x2010000L);
            }
            case 117: {
                return this.jjMoveStringLiteralDfa4_2(active0, 0x200000L);
            }
        }
        return this.jjStartNfa_2(2, active0, 0L);
    }

    private int jjMoveStringLiteralDfa4_2(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_2(2, old0, 0L);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_2(3, active0, 0L);
            return 4;
        }
        switch (this.curChar) {
            case 101: {
                if ((active0 & 0x1000L) != 0L) {
                    return this.jjStartNfaWithStates_2(4, 12, 74);
                }
                if ((active0 & 0x100000L) == 0L) break;
                return this.jjStartNfaWithStates_2(4, 20, 74);
            }
            case 103: {
                return this.jjMoveStringLiteralDfa5_2(active0, 0x4000000L);
            }
            case 105: {
                return this.jjMoveStringLiteralDfa5_2(active0, 0x2000000L);
            }
            case 107: {
                if ((active0 & 0x1000000L) == 0L) break;
                return this.jjStartNfaWithStates_2(4, 24, 74);
            }
            case 114: {
                return this.jjMoveStringLiteralDfa5_2(active0, 0x200000L);
            }
            case 116: {
                return this.jjMoveStringLiteralDfa5_2(active0, 0x400000L);
            }
            case 121: {
                if ((active0 & 0x10000L) == 0L) break;
                return this.jjStartNfaWithStates_2(4, 16, 74);
            }
        }
        return this.jjStartNfa_2(3, active0, 0L);
    }

    private int jjMoveStringLiteralDfa5_2(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_2(3, old0, 0L);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_2(4, active0, 0L);
            return 5;
        }
        switch (this.curChar) {
            case 105: {
                return this.jjMoveStringLiteralDfa6_2(active0, 0x400000L);
            }
            case 109: {
                return this.jjMoveStringLiteralDfa6_2(active0, 0x4000000L);
            }
            case 110: {
                if ((active0 & 0x200000L) != 0L) {
                    return this.jjStartNfaWithStates_2(5, 21, 74);
                }
                return this.jjMoveStringLiteralDfa6_2(active0, 0x2000000L);
            }
        }
        return this.jjStartNfa_2(4, active0, 0L);
    }

    private int jjMoveStringLiteralDfa6_2(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_2(4, old0, 0L);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_2(5, active0, 0L);
            return 6;
        }
        switch (this.curChar) {
            case 97: {
                if ((active0 & 0x4000000L) == 0L) break;
                return this.jjStopAtPos(6, 26);
            }
            case 111: {
                return this.jjMoveStringLiteralDfa7_2(active0, 0x400000L);
            }
            case 117: {
                return this.jjMoveStringLiteralDfa7_2(active0, 0x2000000L);
            }
        }
        return this.jjStartNfa_2(5, active0, 0L);
    }

    private int jjMoveStringLiteralDfa7_2(long old0, long active0) {
        if ((active0 &= old0) == 0L) {
            return this.jjStartNfa_2(5, old0, 0L);
        }
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            this.jjStopStringLiteralDfa_2(6, active0, 0L);
            return 7;
        }
        switch (this.curChar) {
            case 101: {
                if ((active0 & 0x2000000L) == 0L) break;
                return this.jjStartNfaWithStates_2(7, 25, 74);
            }
            case 110: {
                if ((active0 & 0x400000L) == 0L) break;
                return this.jjStartNfaWithStates_2(7, 22, 74);
            }
        }
        return this.jjStartNfa_2(6, active0, 0L);
    }

    private int jjStartNfaWithStates_2(int pos, int kind, int state) {
        this.jjmatchedKind = kind;
        this.jjmatchedPos = pos;
        try {
            this.curChar = this.input_stream.readChar();
        }
        catch (IOException e) {
            return pos + 1;
        }
        return this.jjMoveNfa_2(state, pos + 1);
    }

    private int jjMoveNfa_2(int startState, int curPos) {
        int startsAt = 0;
        this.jjnewStateCnt = 74;
        int i = 1;
        this.jjstateSet[0] = startState;
        int kind = Integer.MAX_VALUE;
        while (true) {
            if (++this.jjround == Integer.MAX_VALUE) {
                this.ReInitRounds();
            }
            if (this.curChar < 64) {
                long l = 1L << this.curChar;
                block109: do {
                    switch (this.jjstateSet[--i]) {
                        case 0: {
                            if ((0x3FF000000000000L & l) != 0L) {
                                this.jjCheckNAddStates(73, 78);
                            } else if (this.curChar == 47) {
                                this.jjAddStates(79, 80);
                            } else if (this.curChar == 35) {
                                this.jjAddStates(81, 82);
                            } else if (this.curChar == 39) {
                                this.jjCheckNAddStates(83, 85);
                            } else if (this.curChar == 34) {
                                this.jjCheckNAddStates(86, 88);
                            } else if (this.curChar == 46) {
                                this.jjCheckNAdd(12);
                            } else if (this.curChar == 36) {
                                if (kind > 90) {
                                    kind = 90;
                                }
                                this.jjCheckNAddTwoStates(3, 4);
                            }
                            if ((0x3FE000000000000L & l) != 0L) {
                                if (kind > 95) {
                                    kind = 95;
                                }
                                this.jjCheckNAddTwoStates(9, 10);
                                break;
                            }
                            if (this.curChar == 48) {
                                if (kind > 95) {
                                    kind = 95;
                                }
                                this.jjCheckNAddStates(89, 91);
                                break;
                            }
                            if (this.curChar != 35) break;
                            this.jjCheckNAdd(7);
                            break;
                        }
                        case 63: {
                            if (this.curChar == 47) {
                                if (kind > 3) {
                                    kind = 3;
                                }
                                this.jjCheckNAddStates(92, 94);
                                break;
                            }
                            if (this.curChar != 42) break;
                            this.jjCheckNAddTwoStates(64, 65);
                            break;
                        }
                        case 54: {
                            if ((0x3FF000000000000L & l) != 0L) {
                                if (kind > 94) {
                                    kind = 94;
                                }
                                this.jjCheckNAdd(7);
                                break;
                            }
                            if (this.curChar != 35) break;
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjCheckNAddStates(95, 97);
                            break;
                        }
                        case 3: 
                        case 74: {
                            if ((0x3FF001000000000L & l) == 0L) continue block109;
                            if (kind > 90) {
                                kind = 90;
                            }
                            this.jjCheckNAddTwoStates(3, 4);
                            break;
                        }
                        case 1: {
                            if ((0x3FF001000000000L & l) == 0L) continue block109;
                            if (kind > 88) {
                                kind = 88;
                            }
                            this.jjstateSet[this.jjnewStateCnt++] = 1;
                            break;
                        }
                        case 2: {
                            if (this.curChar != 36) continue block109;
                            if (kind > 90) {
                                kind = 90;
                            }
                            this.jjCheckNAddTwoStates(3, 4);
                            break;
                        }
                        case 5: {
                            if ((0x8500000000L & l) == 0L) continue block109;
                            if (kind > 90) {
                                kind = 90;
                            }
                            this.jjCheckNAddTwoStates(3, 4);
                            break;
                        }
                        case 6: {
                            if (this.curChar != 35) break;
                            this.jjCheckNAdd(7);
                            break;
                        }
                        case 7: {
                            if ((0x3FF000000000000L & l) == 0L) continue block109;
                            if (kind > 94) {
                                kind = 94;
                            }
                            this.jjCheckNAdd(7);
                            break;
                        }
                        case 8: {
                            if ((0x3FE000000000000L & l) == 0L) continue block109;
                            if (kind > 95) {
                                kind = 95;
                            }
                            this.jjCheckNAddTwoStates(9, 10);
                            break;
                        }
                        case 9: {
                            if ((0x3FF000000000000L & l) == 0L) continue block109;
                            if (kind > 95) {
                                kind = 95;
                            }
                            this.jjCheckNAddTwoStates(9, 10);
                            break;
                        }
                        case 11: {
                            if (this.curChar != 46) break;
                            this.jjCheckNAdd(12);
                            break;
                        }
                        case 12: {
                            if ((0x3FF000000000000L & l) == 0L) break;
                            this.jjCheckNAddStates(98, 100);
                            break;
                        }
                        case 14: {
                            if ((0x280000000000L & l) == 0L) break;
                            this.jjCheckNAdd(15);
                            break;
                        }
                        case 15: {
                            if ((0x3FF000000000000L & l) == 0L) continue block109;
                            if (kind > 100) {
                                kind = 100;
                            }
                            this.jjCheckNAddTwoStates(15, 16);
                            break;
                        }
                        case 17: {
                            if (this.curChar != 34) break;
                            this.jjCheckNAddStates(86, 88);
                            break;
                        }
                        case 18: {
                            if ((0xFFFFFFFBFFFFDBFFL & l) == 0L) break;
                            this.jjCheckNAddStates(86, 88);
                            break;
                        }
                        case 20: {
                            if ((0xFFFFFFFFFFFFDBFFL & l) == 0L) break;
                            this.jjCheckNAddStates(86, 88);
                            break;
                        }
                        case 21: {
                            if (this.curChar != 34 || kind <= 104) continue block109;
                            kind = 104;
                            break;
                        }
                        case 22: {
                            if (this.curChar != 39) break;
                            this.jjCheckNAddStates(83, 85);
                            break;
                        }
                        case 23: {
                            if ((0xFFFFFF7FFFFFDBFFL & l) == 0L) break;
                            this.jjCheckNAddStates(83, 85);
                            break;
                        }
                        case 25: {
                            if ((0xFFFFFFFFFFFFDBFFL & l) == 0L) break;
                            this.jjCheckNAddStates(83, 85);
                            break;
                        }
                        case 26: {
                            if (this.curChar != 39 || kind <= 104) continue block109;
                            kind = 104;
                            break;
                        }
                        case 28: {
                            this.jjCheckNAddStates(101, 103);
                            break;
                        }
                        case 30: {
                            if ((0xFFFFFFFFFFFFFFFEL & l) == 0L) break;
                            this.jjCheckNAddStates(101, 103);
                            break;
                        }
                        case 33: {
                            if (this.curChar != 47) break;
                            this.jjCheckNAddStates(104, 106);
                            break;
                        }
                        case 34: {
                            if ((0xFFFF7FFFFFFFDBFFL & l) == 0L) break;
                            this.jjCheckNAddStates(104, 106);
                            break;
                        }
                        case 36: {
                            if (this.curChar != 47 || kind <= 106) continue block109;
                            kind = 106;
                            break;
                        }
                        case 37: {
                            if ((0x3FF000000000000L & l) == 0L) break;
                            this.jjCheckNAddStates(73, 78);
                            break;
                        }
                        case 38: {
                            if ((0x3FF000000000000L & l) == 0L) break;
                            this.jjCheckNAddTwoStates(38, 39);
                            break;
                        }
                        case 39: {
                            if (this.curChar != 46) break;
                            this.jjCheckNAdd(40);
                            break;
                        }
                        case 40: {
                            if ((0x3FF000000000000L & l) == 0L) continue block109;
                            if (kind > 100) {
                                kind = 100;
                            }
                            this.jjCheckNAddStates(107, 109);
                            break;
                        }
                        case 42: {
                            if ((0x280000000000L & l) == 0L) break;
                            this.jjCheckNAdd(43);
                            break;
                        }
                        case 43: {
                            if ((0x3FF000000000000L & l) == 0L) continue block109;
                            if (kind > 100) {
                                kind = 100;
                            }
                            this.jjCheckNAddTwoStates(43, 16);
                            break;
                        }
                        case 44: {
                            if ((0x3FF000000000000L & l) == 0L) break;
                            this.jjCheckNAddStates(110, 113);
                            break;
                        }
                        case 45: {
                            if (this.curChar != 46) break;
                            this.jjCheckNAddTwoStates(46, 16);
                            break;
                        }
                        case 47: {
                            if ((0x280000000000L & l) == 0L) break;
                            this.jjCheckNAdd(48);
                            break;
                        }
                        case 48: {
                            if ((0x3FF000000000000L & l) == 0L) continue block109;
                            if (kind > 100) {
                                kind = 100;
                            }
                            this.jjCheckNAddTwoStates(48, 16);
                            break;
                        }
                        case 49: {
                            if (this.curChar != 48) continue block109;
                            if (kind > 95) {
                                kind = 95;
                            }
                            this.jjCheckNAddStates(89, 91);
                            break;
                        }
                        case 51: {
                            if ((0x3FF000000000000L & l) == 0L) continue block109;
                            if (kind > 95) {
                                kind = 95;
                            }
                            this.jjCheckNAddTwoStates(51, 10);
                            break;
                        }
                        case 52: {
                            if ((0xFF000000000000L & l) == 0L) continue block109;
                            if (kind > 95) {
                                kind = 95;
                            }
                            this.jjCheckNAddTwoStates(52, 10);
                            break;
                        }
                        case 53: {
                            if (this.curChar != 35) break;
                            this.jjAddStates(81, 82);
                            break;
                        }
                        case 55: {
                            if ((0xFFFFFFFFFFFFDBFFL & l) == 0L) continue block109;
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjCheckNAddStates(95, 97);
                            break;
                        }
                        case 56: {
                            if ((0x2400L & l) == 0L || kind <= 1) continue block109;
                            kind = 1;
                            break;
                        }
                        case 57: {
                            if (this.curChar != 10 || kind <= 1) continue block109;
                            kind = 1;
                            break;
                        }
                        case 58: {
                            if (this.curChar != 13) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 57;
                            break;
                        }
                        case 62: {
                            if (this.curChar != 47) break;
                            this.jjAddStates(79, 80);
                            break;
                        }
                        case 64: {
                            if ((0xFFFFFBFFFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(64, 65);
                            break;
                        }
                        case 65: {
                            if (this.curChar != 42) break;
                            this.jjCheckNAddStates(114, 116);
                            break;
                        }
                        case 66: {
                            if ((0xFFFF7BFFFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(67, 65);
                            break;
                        }
                        case 67: {
                            if ((0xFFFFFBFFFFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddTwoStates(67, 65);
                            break;
                        }
                        case 68: {
                            if (this.curChar != 47 || kind <= 2) continue block109;
                            kind = 2;
                            break;
                        }
                        case 69: {
                            if (this.curChar != 47) continue block109;
                            if (kind > 3) {
                                kind = 3;
                            }
                            this.jjCheckNAddStates(92, 94);
                            break;
                        }
                        case 70: {
                            if ((0xFFFFFFFFFFFFDBFFL & l) == 0L) continue block109;
                            if (kind > 3) {
                                kind = 3;
                            }
                            this.jjCheckNAddStates(92, 94);
                            break;
                        }
                        case 71: {
                            if ((0x2400L & l) == 0L || kind <= 3) continue block109;
                            kind = 3;
                            break;
                        }
                        case 72: {
                            if (this.curChar != 10 || kind <= 3) continue block109;
                            kind = 3;
                            break;
                        }
                        case 73: {
                            if (this.curChar != 13) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 72;
                            break;
                        }
                    }
                } while (i != startsAt);
            } else if (this.curChar < 128) {
                long l = 1L << (this.curChar & 0x3F);
                block110: do {
                    switch (this.jjstateSet[--i]) {
                        case 0: {
                            if ((0x7FFFFFE87FFFFFFL & l) != 0L) {
                                if (kind > 90) {
                                    kind = 90;
                                }
                                this.jjCheckNAddTwoStates(3, 4);
                            } else if (this.curChar == 126) {
                                this.jjCheckNAdd(33);
                            } else if (this.curChar == 96) {
                                this.jjCheckNAddStates(101, 103);
                            }
                            if (this.curChar != 64) break;
                            this.jjCheckNAdd(1);
                            break;
                        }
                        case 54: {
                            if (this.curChar != 78) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 60;
                            break;
                        }
                        case 74: {
                            if ((0x7FFFFFE87FFFFFFL & l) != 0L) {
                                if (kind > 90) {
                                    kind = 90;
                                }
                                this.jjCheckNAddTwoStates(3, 4);
                                break;
                            }
                            if (this.curChar != 92) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 5;
                            break;
                        }
                        case 1: {
                            if ((0x7FFFFFE87FFFFFEL & l) == 0L) continue block110;
                            if (kind > 88) {
                                kind = 88;
                            }
                            this.jjCheckNAdd(1);
                            break;
                        }
                        case 2: {
                            if ((0x7FFFFFE87FFFFFFL & l) == 0L) continue block110;
                            if (kind > 90) {
                                kind = 90;
                            }
                            this.jjCheckNAddTwoStates(3, 4);
                            break;
                        }
                        case 3: {
                            if ((0x7FFFFFE87FFFFFFL & l) == 0L) continue block110;
                            if (kind > 90) {
                                kind = 90;
                            }
                            this.jjCheckNAddTwoStates(3, 4);
                            break;
                        }
                        case 4: {
                            if (this.curChar != 92) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 5;
                            break;
                        }
                        case 5: {
                            if (this.curChar != 92) continue block110;
                            if (kind > 90) {
                                kind = 90;
                            }
                            this.jjCheckNAddTwoStates(3, 4);
                            break;
                        }
                        case 10: {
                            if ((0x110000001100L & l) == 0L || kind <= 95) continue block110;
                            kind = 95;
                            break;
                        }
                        case 13: {
                            if ((0x2000000020L & l) == 0L) break;
                            this.jjAddStates(117, 118);
                            break;
                        }
                        case 16: {
                            if ((0x5400000054L & l) == 0L || kind <= 100) continue block110;
                            kind = 100;
                            break;
                        }
                        case 18: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddStates(86, 88);
                            break;
                        }
                        case 19: {
                            if (this.curChar != 92) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 20;
                            break;
                        }
                        case 20: {
                            this.jjCheckNAddStates(86, 88);
                            break;
                        }
                        case 23: {
                            if ((0xFFFFFFFFEFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddStates(83, 85);
                            break;
                        }
                        case 24: {
                            if (this.curChar != 92) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 25;
                            break;
                        }
                        case 25: {
                            this.jjCheckNAddStates(83, 85);
                            break;
                        }
                        case 27: {
                            if (this.curChar != 96) break;
                            this.jjCheckNAddStates(101, 103);
                            break;
                        }
                        case 28: {
                            if ((0xFFFFFFFEEFFFFFFFL & l) == 0L) break;
                            this.jjCheckNAddStates(101, 103);
                            break;
                        }
                        case 29: {
                            if (this.curChar != 92) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 30;
                            break;
                        }
                        case 30: {
                            this.jjCheckNAddStates(101, 103);
                            break;
                        }
                        case 31: {
                            if (this.curChar != 96 || kind <= 105) continue block110;
                            kind = 105;
                            break;
                        }
                        case 32: {
                            if (this.curChar != 126) break;
                            this.jjCheckNAdd(33);
                            break;
                        }
                        case 34: {
                            this.jjAddStates(104, 106);
                            break;
                        }
                        case 35: {
                            if (this.curChar != 92) break;
                            this.jjCheckNAdd(33);
                            break;
                        }
                        case 41: {
                            if ((0x2000000020L & l) == 0L) break;
                            this.jjAddStates(119, 120);
                            break;
                        }
                        case 46: {
                            if ((0x2000000020L & l) == 0L) break;
                            this.jjAddStates(121, 122);
                            break;
                        }
                        case 50: {
                            if ((0x100000001000000L & l) == 0L) break;
                            this.jjCheckNAdd(51);
                            break;
                        }
                        case 51: {
                            if ((0x7E0000007EL & l) == 0L) continue block110;
                            if (kind > 95) {
                                kind = 95;
                            }
                            this.jjCheckNAddTwoStates(51, 10);
                            break;
                        }
                        case 55: {
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjAddStates(95, 97);
                            break;
                        }
                        case 59: {
                            if (this.curChar != 78 || kind <= 100) continue block110;
                            kind = 100;
                            break;
                        }
                        case 60: {
                            if (this.curChar != 97) break;
                            this.jjstateSet[this.jjnewStateCnt++] = 59;
                            break;
                        }
                        case 64: {
                            this.jjCheckNAddTwoStates(64, 65);
                            break;
                        }
                        case 66: 
                        case 67: {
                            this.jjCheckNAddTwoStates(67, 65);
                            break;
                        }
                        case 70: {
                            if (kind > 3) {
                                kind = 3;
                            }
                            this.jjAddStates(92, 94);
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
                block111: do {
                    switch (this.jjstateSet[--i]) {
                        case 18: 
                        case 20: {
                            if (!ParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) continue block111;
                            this.jjCheckNAddStates(86, 88);
                            break;
                        }
                        case 23: 
                        case 25: {
                            if (!ParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) continue block111;
                            this.jjCheckNAddStates(83, 85);
                            break;
                        }
                        case 28: 
                        case 30: {
                            if (!ParserTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block111;
                            this.jjCheckNAddStates(101, 103);
                            break;
                        }
                        case 34: {
                            if (!ParserTokenManager.jjCanMove_0(hiByte, i1, i2, l1, l2)) continue block111;
                            this.jjAddStates(104, 106);
                            break;
                        }
                        case 55: {
                            if (!ParserTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block111;
                            if (kind > 1) {
                                kind = 1;
                            }
                            this.jjAddStates(95, 97);
                            break;
                        }
                        case 64: {
                            if (!ParserTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block111;
                            this.jjCheckNAddTwoStates(64, 65);
                            break;
                        }
                        case 66: 
                        case 67: {
                            if (!ParserTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block111;
                            this.jjCheckNAddTwoStates(67, 65);
                            break;
                        }
                        case 70: {
                            if (!ParserTokenManager.jjCanMove_1(hiByte, i1, i2, l1, l2)) continue block111;
                            if (kind > 3) {
                                kind = 3;
                            }
                            this.jjAddStates(92, 94);
                            break;
                        }
                        default: {
                            if (i1 != 0 && l1 != 0L && i2 != 0 && l2 != 0L) continue block111;
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
            startsAt = 74 - this.jjnewStateCnt;
            if (i == startsAt) {
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

    protected Token jjFillToken() {
        String im = jjstrLiteralImages[this.jjmatchedKind];
        String curTokenImage = im == null ? this.input_stream.getImage() : im;
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

    private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2) {
        switch (hiByte) {
            case 0: {
                return (jjbitVec2[i2] & l2) != 0L;
            }
            case 32: {
                return (jjbitVec3[i2] & l2) != 0L;
            }
        }
        return (jjbitVec0[i1] & l1) != 0L;
    }

    private static final boolean jjCanMove_1(int hiByte, int i1, int i2, long l1, long l2) {
        switch (hiByte) {
            case 0: {
                return (jjbitVec2[i2] & l2) != 0L;
            }
        }
        return (jjbitVec4[i1] & l1) != 0L;
    }

    public Token getNextToken() {
        int curPos = 0;
        block15: while (true) {
            try {
                this.curChar = this.input_stream.beginToken();
            }
            catch (Exception e) {
                this.jjmatchedKind = 0;
                this.jjmatchedPos = -1;
                Token matchedToken = this.jjFillToken();
                return matchedToken;
            }
            this.image = this.jjimage;
            this.image.setLength(0);
            this.jjimageLen = 0;
            switch (this.curLexState) {
                case 0: {
                    try {
                        this.input_stream.backup(0);
                        while (this.curChar <= 32 && (0x100003600L & 1L << this.curChar) != 0L) {
                            this.curChar = this.input_stream.beginToken();
                        }
                    }
                    catch (IOException e1) {
                        continue block15;
                    }
                    this.jjmatchedKind = Integer.MAX_VALUE;
                    this.jjmatchedPos = 0;
                    curPos = this.jjMoveStringLiteralDfa0_0();
                    break;
                }
                case 1: {
                    try {
                        this.input_stream.backup(0);
                        while (this.curChar <= 32 && (0x100003600L & 1L << this.curChar) != 0L) {
                            this.curChar = this.input_stream.beginToken();
                        }
                    }
                    catch (IOException e1) {
                        continue block15;
                    }
                    this.jjmatchedKind = Integer.MAX_VALUE;
                    this.jjmatchedPos = 0;
                    curPos = this.jjMoveStringLiteralDfa0_1();
                    break;
                }
                case 2: {
                    try {
                        this.input_stream.backup(0);
                        while (this.curChar <= 32 && (0x100003600L & 1L << this.curChar) != 0L) {
                            this.curChar = this.input_stream.beginToken();
                        }
                    }
                    catch (IOException e1) {
                        continue block15;
                    }
                    this.jjmatchedKind = Integer.MAX_VALUE;
                    this.jjmatchedPos = 0;
                    curPos = this.jjMoveStringLiteralDfa0_2();
                }
            }
            if (this.jjmatchedKind == Integer.MAX_VALUE) break;
            if (this.jjmatchedPos + 1 < curPos) {
                this.input_stream.backup(curPos - this.jjmatchedPos - 1);
            }
            if ((jjtoToken[this.jjmatchedKind >> 6] & 1L << (this.jjmatchedKind & 0x3F)) != 0L) {
                Token matchedToken = this.jjFillToken();
                this.TokenLexicalActions(matchedToken);
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
            String string = error_after = curPos <= 1 ? "" : this.input_stream.getImage();
            if (this.curChar == 10 || this.curChar == 13) {
                ++error_line;
                error_column = 0;
            }
            ++error_column;
        }
        if (!EOFSeen) {
            this.input_stream.backup(1);
            error_after = curPos <= 1 ? "" : this.input_stream.getImage();
        }
        throw new TokenMgrException(EOFSeen, this.curLexState, error_line, error_column, error_after, this.curChar, 0);
    }

    void SkipLexicalActions(Token matchedToken) {
        switch (this.jjmatchedKind) {
            default: 
        }
    }

    void MoreLexicalActions() {
        this.lengthOfMatch = this.jjmatchedPos + 1;
        this.jjimageLen += this.lengthOfMatch;
        switch (this.jjmatchedKind) {
            default: 
        }
    }

    void TokenLexicalActions(Token matchedToken) {
        switch (this.jjmatchedKind) {
            case 9: {
                this.image.append(jjstrLiteralImages[9]);
                this.lengthOfMatch = jjstrLiteralImages[9].length();
                this.popDot();
                break;
            }
            case 10: {
                this.image.append(jjstrLiteralImages[10]);
                this.lengthOfMatch = jjstrLiteralImages[10].length();
                this.popDot();
                break;
            }
            case 11: {
                this.image.append(jjstrLiteralImages[11]);
                this.lengthOfMatch = jjstrLiteralImages[11].length();
                this.popDot();
                break;
            }
            case 12: {
                this.image.append(jjstrLiteralImages[12]);
                this.lengthOfMatch = jjstrLiteralImages[12].length();
                this.popDot();
                break;
            }
            case 13: {
                this.image.append(jjstrLiteralImages[13]);
                this.lengthOfMatch = jjstrLiteralImages[13].length();
                this.popDot();
                break;
            }
            case 14: {
                this.image.append(jjstrLiteralImages[14]);
                this.lengthOfMatch = jjstrLiteralImages[14].length();
                this.popDot();
                break;
            }
            case 15: {
                this.image.append(jjstrLiteralImages[15]);
                this.lengthOfMatch = jjstrLiteralImages[15].length();
                this.popDot();
                break;
            }
            case 16: {
                this.image.append(jjstrLiteralImages[16]);
                this.lengthOfMatch = jjstrLiteralImages[16].length();
                this.popDot();
                break;
            }
            case 17: {
                this.image.append(jjstrLiteralImages[17]);
                this.lengthOfMatch = jjstrLiteralImages[17].length();
                this.popDot();
                break;
            }
            case 18: {
                this.image.append(jjstrLiteralImages[18]);
                this.lengthOfMatch = jjstrLiteralImages[18].length();
                this.popDot();
                break;
            }
            case 19: {
                this.image.append(jjstrLiteralImages[19]);
                this.lengthOfMatch = jjstrLiteralImages[19].length();
                this.popDot();
                break;
            }
            case 20: {
                this.image.append(jjstrLiteralImages[20]);
                this.lengthOfMatch = jjstrLiteralImages[20].length();
                this.popDot();
                break;
            }
            case 21: {
                this.image.append(jjstrLiteralImages[21]);
                this.lengthOfMatch = jjstrLiteralImages[21].length();
                this.popDot();
                break;
            }
            case 22: {
                this.image.append(jjstrLiteralImages[22]);
                this.lengthOfMatch = jjstrLiteralImages[22].length();
                this.popDot();
                break;
            }
            case 24: {
                this.image.append(jjstrLiteralImages[24]);
                this.lengthOfMatch = jjstrLiteralImages[24].length();
                this.popDot();
                break;
            }
            case 25: {
                this.image.append(jjstrLiteralImages[25]);
                this.lengthOfMatch = jjstrLiteralImages[25].length();
                this.popDot();
                break;
            }
            case 26: {
                this.image.append(jjstrLiteralImages[26]);
                this.lengthOfMatch = jjstrLiteralImages[26].length();
                this.popDot();
                break;
            }
            case 36: {
                this.image.append(jjstrLiteralImages[36]);
                this.lengthOfMatch = jjstrLiteralImages[36].length();
                this.pushDot();
                break;
            }
            case 37: {
                this.image.append(jjstrLiteralImages[37]);
                this.lengthOfMatch = jjstrLiteralImages[37].length();
                this.pushDot();
                break;
            }
            case 43: {
                this.image.append(jjstrLiteralImages[43]);
                this.lengthOfMatch = jjstrLiteralImages[43].length();
                this.popDot();
                break;
            }
            case 45: {
                this.image.append(jjstrLiteralImages[45]);
                this.lengthOfMatch = jjstrLiteralImages[45].length();
                this.popDot();
                break;
            }
            case 47: {
                this.image.append(jjstrLiteralImages[47]);
                this.lengthOfMatch = jjstrLiteralImages[47].length();
                this.popDot();
                break;
            }
            case 49: {
                this.image.append(jjstrLiteralImages[49]);
                this.lengthOfMatch = jjstrLiteralImages[49].length();
                this.popDot();
                break;
            }
            case 51: {
                this.image.append(jjstrLiteralImages[51]);
                this.lengthOfMatch = jjstrLiteralImages[51].length();
                this.popDot();
                break;
            }
            case 53: {
                this.image.append(jjstrLiteralImages[53]);
                this.lengthOfMatch = jjstrLiteralImages[53].length();
                this.popDot();
                break;
            }
            case 55: {
                this.image.append(jjstrLiteralImages[55]);
                this.lengthOfMatch = jjstrLiteralImages[55].length();
                this.popDot();
                break;
            }
            case 57: {
                this.image.append(jjstrLiteralImages[57]);
                this.lengthOfMatch = jjstrLiteralImages[57].length();
                this.popDot();
                break;
            }
            case 77: {
                this.image.append(jjstrLiteralImages[77]);
                this.lengthOfMatch = jjstrLiteralImages[77].length();
                this.popDot();
                break;
            }
            case 79: {
                this.image.append(jjstrLiteralImages[79]);
                this.lengthOfMatch = jjstrLiteralImages[79].length();
                this.popDot();
                break;
            }
            case 81: {
                this.image.append(jjstrLiteralImages[81]);
                this.lengthOfMatch = jjstrLiteralImages[81].length();
                this.popDot();
                break;
            }
            case 89: {
                this.lengthOfMatch = this.jjmatchedPos + 1;
                this.image.append(this.input_stream.getSuffix(this.jjimageLen + this.lengthOfMatch));
                this.popDot();
                break;
            }
            case 90: {
                this.lengthOfMatch = this.jjmatchedPos + 1;
                this.image.append(this.input_stream.getSuffix(this.jjimageLen + this.lengthOfMatch));
                matchedToken.image = StringParser.unescapeIdentifier(matchedToken.image);
                break;
            }
            case 104: {
                this.lengthOfMatch = this.jjmatchedPos + 1;
                this.image.append(this.input_stream.getSuffix(this.jjimageLen + this.lengthOfMatch));
                this.popDot();
                break;
            }
            case 105: {
                this.lengthOfMatch = this.jjmatchedPos + 1;
                this.image.append(this.input_stream.getSuffix(this.jjimageLen + this.lengthOfMatch));
                this.popDot();
                break;
            }
            case 106: {
                this.lengthOfMatch = this.jjmatchedPos + 1;
                this.image.append(this.input_stream.getSuffix(this.jjimageLen + this.lengthOfMatch));
                this.popDot();
                break;
            }
        }
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

    public ParserTokenManager(SimpleCharStream stream) {
        this.input_stream = stream;
    }

    public ParserTokenManager(SimpleCharStream stream, int lexState) {
        this.ReInit(stream);
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
        int i = 74;
        while (i-- > 0) {
            this.jjrounds[i] = Integer.MIN_VALUE;
        }
    }

    public void ReInit(SimpleCharStream stream, int lexState) {
        this.ReInit(stream);
        this.SwitchTo(lexState);
    }

    public void SwitchTo(int lexState) {
        if (lexState >= 3 || lexState < 0) {
            throw new TokenMgrException("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", 2);
        }
        this.curLexState = lexState;
    }
}

