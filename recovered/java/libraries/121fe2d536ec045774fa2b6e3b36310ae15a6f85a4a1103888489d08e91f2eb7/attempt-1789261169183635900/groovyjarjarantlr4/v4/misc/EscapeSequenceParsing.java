/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.misc;

import groovyjarjarantlr4.v4.misc.CharSupport;
import groovyjarjarantlr4.v4.runtime.misc.IntervalSet;
import groovyjarjarantlr4.v4.runtime.misc.MurmurHash;
import groovyjarjarantlr4.v4.runtime.misc.Utils;
import groovyjarjarantlr4.v4.unicode.UnicodeData;

public abstract class EscapeSequenceParsing {
    public static Result parseEscape(String s, int startOff) {
        int offset = startOff;
        if (offset + 2 > s.length() || s.codePointAt(offset) != 92) {
            return EscapeSequenceParsing.invalid(startOff, s.length() - 1);
        }
        int escaped = s.codePointAt(++offset);
        offset += Character.charCount(escaped);
        if (escaped == 117) {
            int hexEndOffset;
            int hexStartOffset;
            if (offset + 3 > s.length()) {
                return EscapeSequenceParsing.invalid(startOff, s.length() - 1);
            }
            if (s.codePointAt(offset) == 123) {
                hexStartOffset = offset + 1;
                hexEndOffset = s.indexOf(125, hexStartOffset);
                if (hexEndOffset == -1) {
                    return EscapeSequenceParsing.invalid(startOff, s.length() - 1);
                }
                offset = hexEndOffset + 1;
            } else {
                if (offset + 4 > s.length()) {
                    return EscapeSequenceParsing.invalid(startOff, s.length() - 1);
                }
                hexStartOffset = offset;
                offset = hexEndOffset = offset + 4;
            }
            int codePointValue = CharSupport.parseHexValue(s, hexStartOffset, hexEndOffset);
            if (codePointValue == -1 || codePointValue > 0x10FFFF) {
                return EscapeSequenceParsing.invalid(startOff, startOff + 6 - 1);
            }
            return new Result(Result.Type.CODE_POINT, codePointValue, IntervalSet.EMPTY_SET, startOff, offset - startOff);
        }
        if (escaped == 112 || escaped == 80) {
            if (offset + 3 > s.length()) {
                return EscapeSequenceParsing.invalid(startOff, s.length() - 1);
            }
            if (s.codePointAt(offset) != 123) {
                return EscapeSequenceParsing.invalid(startOff, offset);
            }
            int openBraceOffset = offset;
            int closeBraceOffset = s.indexOf(125, openBraceOffset);
            if (closeBraceOffset == -1) {
                return EscapeSequenceParsing.invalid(startOff, s.length() - 1);
            }
            String propertyName = s.substring(openBraceOffset + 1, closeBraceOffset);
            IntervalSet propertyIntervalSet = UnicodeData.getPropertyCodePoints(propertyName);
            if (propertyIntervalSet == null) {
                return EscapeSequenceParsing.invalid(startOff, closeBraceOffset);
            }
            offset = closeBraceOffset + 1;
            if (escaped == 80) {
                propertyIntervalSet = propertyIntervalSet.complement(IntervalSet.COMPLETE_CHAR_SET);
            }
            return new Result(Result.Type.PROPERTY, -1, propertyIntervalSet, startOff, offset - startOff);
        }
        if (escaped < CharSupport.ANTLRLiteralEscapedCharValue.length) {
            int codePoint = CharSupport.ANTLRLiteralEscapedCharValue[escaped];
            if (codePoint == 0) {
                if (escaped != 93 && escaped != 45) {
                    return EscapeSequenceParsing.invalid(startOff, startOff + 1);
                }
                codePoint = escaped;
            }
            return new Result(Result.Type.CODE_POINT, codePoint, IntervalSet.EMPTY_SET, startOff, offset - startOff);
        }
        return EscapeSequenceParsing.invalid(startOff, s.length() - 1);
    }

    private static Result invalid(int start, int stop) {
        return new Result(Result.Type.INVALID, 0, IntervalSet.EMPTY_SET, start, stop - start + 1);
    }

    public static class Result {
        public final Type type;
        public final int codePoint;
        public final IntervalSet propertyIntervalSet;
        public final int startOffset;
        public final int parseLength;

        public Result(Type type, int codePoint, IntervalSet propertyIntervalSet, int startOffset, int parseLength) {
            this.type = type;
            this.codePoint = codePoint;
            this.propertyIntervalSet = propertyIntervalSet;
            this.startOffset = startOffset;
            this.parseLength = parseLength;
        }

        public String toString() {
            return String.format("%s type=%s codePoint=%d propertyIntervalSet=%s parseLength=%d", new Object[]{super.toString(), this.type, this.codePoint, this.propertyIntervalSet, this.parseLength});
        }

        public boolean equals(Object other) {
            if (!(other instanceof Result)) {
                return false;
            }
            Result that = (Result)other;
            if (this == that) {
                return true;
            }
            return Utils.equals((Object)this.type, (Object)that.type) && Utils.equals(this.codePoint, that.codePoint) && Utils.equals(this.propertyIntervalSet, that.propertyIntervalSet) && Utils.equals(this.parseLength, that.parseLength);
        }

        public int hashCode() {
            int hash = MurmurHash.initialize();
            hash = MurmurHash.update(hash, (Object)this.type);
            hash = MurmurHash.update(hash, this.codePoint);
            hash = MurmurHash.update(hash, this.propertyIntervalSet);
            hash = MurmurHash.update(hash, this.parseLength);
            return MurmurHash.finish(hash, 4);
        }

        public static enum Type {
            INVALID,
            CODE_POINT,
            PROPERTY;

        }
    }
}

