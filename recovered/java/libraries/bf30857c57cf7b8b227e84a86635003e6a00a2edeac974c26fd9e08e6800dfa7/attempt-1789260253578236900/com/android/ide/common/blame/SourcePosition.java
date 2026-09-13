/*
 * Decompiled with CFR 0.152.
 */
package com.android.ide.common.blame;

import com.google.common.base.Objects;
import java.io.Serializable;

public final class SourcePosition
implements Serializable {
    public static final SourcePosition UNKNOWN = new SourcePosition();
    private final int mStartLine;
    private final int mStartColumn;
    private final int mStartOffset;
    private final int mEndLine;
    private final int mEndColumn;
    private final int mEndOffset;

    public SourcePosition(int startLine, int startColumn, int startOffset, int endLine, int endColumn, int endOffset) {
        this.mStartLine = startLine;
        this.mStartColumn = startColumn;
        this.mStartOffset = startOffset;
        this.mEndLine = endLine;
        this.mEndColumn = endColumn;
        this.mEndOffset = endOffset;
    }

    public SourcePosition(int lineNumber, int column, int offset) {
        this.mStartLine = this.mEndLine = lineNumber;
        this.mStartColumn = this.mEndColumn = column;
        this.mStartOffset = this.mEndOffset = offset;
    }

    private SourcePosition() {
        this.mEndOffset = -1;
        this.mEndColumn = -1;
        this.mEndLine = -1;
        this.mStartOffset = -1;
        this.mStartColumn = -1;
        this.mStartLine = -1;
    }

    protected SourcePosition(SourcePosition copy) {
        this.mStartLine = copy.getStartLine();
        this.mStartColumn = copy.getStartColumn();
        this.mStartOffset = copy.getStartOffset();
        this.mEndLine = copy.getEndLine();
        this.mEndColumn = copy.getEndColumn();
        this.mEndOffset = copy.getEndOffset();
    }

    public String toString() {
        if (this.mStartLine == -1) {
            return "?";
        }
        StringBuilder sB = new StringBuilder(15);
        sB.append(this.mStartLine + 1);
        if (this.mStartColumn != -1) {
            sB.append(':');
            sB.append(this.mStartColumn + 1);
        }
        if (this.mEndLine != -1) {
            if (this.mEndLine == this.mStartLine) {
                if (this.mEndColumn != -1 && this.mEndColumn != this.mStartColumn) {
                    sB.append('-');
                    sB.append(this.mEndColumn + 1);
                }
            } else {
                sB.append('-');
                sB.append(this.mEndLine + 1);
                if (this.mEndColumn != -1) {
                    sB.append(':');
                    sB.append(this.mEndColumn + 1);
                }
            }
        }
        return sB.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SourcePosition)) {
            return false;
        }
        SourcePosition other = (SourcePosition)obj;
        return other.mStartLine == this.mStartLine && other.mStartColumn == this.mStartColumn && other.mStartOffset == this.mStartOffset && other.mEndLine == this.mEndLine && other.mEndColumn == this.mEndColumn && other.mEndOffset == this.mEndOffset;
    }

    public int hashCode() {
        return Objects.hashCode(this.mStartLine, this.mStartColumn, this.mStartOffset, this.mEndLine, this.mEndColumn, this.mEndOffset);
    }

    public int getStartLine() {
        return this.mStartLine;
    }

    public int getStartColumn() {
        return this.mStartColumn;
    }

    public int getStartOffset() {
        return this.mStartOffset;
    }

    public int getEndLine() {
        return this.mEndLine;
    }

    public int getEndColumn() {
        return this.mEndColumn;
    }

    public int getEndOffset() {
        return this.mEndOffset;
    }

    public int compareStart(SourcePosition other) {
        if (this.mStartOffset != -1 && other.mStartOffset != -1) {
            return this.mStartOffset - other.mStartOffset;
        }
        if (this.mStartLine == other.mStartLine) {
            return this.mStartColumn - other.mStartColumn;
        }
        return this.mStartLine - other.mStartLine;
    }

    public int compareEnd(SourcePosition other) {
        if (this.mEndOffset != -1 && other.mEndOffset != -1) {
            return this.mEndOffset - other.mEndOffset;
        }
        if (this.mEndLine == other.mEndLine) {
            return this.mEndColumn - other.mEndColumn;
        }
        return this.mEndLine - other.mEndLine;
    }
}

