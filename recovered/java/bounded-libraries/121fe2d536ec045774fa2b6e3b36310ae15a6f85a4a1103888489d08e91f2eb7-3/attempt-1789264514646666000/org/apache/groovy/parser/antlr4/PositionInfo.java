/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.parser.antlr4;

import java.util.Objects;

public class PositionInfo {
    private int line;
    private int column;

    public PositionInfo(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public int getLine() {
        return this.line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return this.column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        PositionInfo that = (PositionInfo)o;
        return this.line == that.line && this.column == that.column;
    }

    public int hashCode() {
        return Objects.hash(this.line, this.column);
    }

    public String toString() {
        if (-1 == this.line || -1 == this.column) {
            return "";
        }
        return " @ line " + this.line + ", column " + this.column;
    }
}

