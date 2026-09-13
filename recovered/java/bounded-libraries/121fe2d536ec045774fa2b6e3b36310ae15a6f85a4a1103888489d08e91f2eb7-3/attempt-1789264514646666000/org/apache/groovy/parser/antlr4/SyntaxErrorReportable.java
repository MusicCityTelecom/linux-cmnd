/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.parser.antlr4;

import groovy.lang.Tuple;
import groovy.lang.Tuple2;
import org.apache.groovy.parser.antlr4.GroovySyntaxError;
import org.apache.groovy.parser.antlr4.PositionInfo;

public interface SyntaxErrorReportable {
    public static final Tuple2<Integer, Integer> NO_OFFSET = Tuple.tuple(0, 0);

    default public void require(boolean condition, String msg, int offset, boolean toAttachPositionInfo) {
        this.require(condition, msg, Tuple.tuple(0, offset), toAttachPositionInfo);
    }

    default public void require(boolean condition, String msg, Tuple2<Integer, Integer> offset, boolean toAttachPositionInfo) {
        if (condition) {
            return;
        }
        this.throwSyntaxError(msg, offset, toAttachPositionInfo);
    }

    default public void require(boolean condition, String msg, boolean toAttachPositionInfo) {
        this.require(condition, msg, NO_OFFSET, toAttachPositionInfo);
    }

    default public void require(boolean condition, String msg, int offset) {
        this.require(condition, msg, Tuple.tuple(0, offset));
    }

    default public void require(boolean condition, String msg, Tuple2<Integer, Integer> offset) {
        this.require(condition, msg, offset, false);
    }

    default public void require(boolean condition, String msg) {
        this.require(condition, msg, false);
    }

    default public void throwSyntaxError(String msg, int offset, boolean toAttachPositionInfo) {
        this.throwSyntaxError(msg, Tuple.tuple(0, offset), toAttachPositionInfo);
    }

    default public void throwSyntaxError(String msg, Tuple2<Integer, Integer> offset, boolean toAttachPositionInfo) {
        PositionInfo positionInfo = this.genPositionInfo(offset);
        throw new GroovySyntaxError(msg + (toAttachPositionInfo ? positionInfo.toString() : ""), this.getSyntaxErrorSource(), positionInfo.getLine(), positionInfo.getColumn());
    }

    public int getSyntaxErrorSource();

    default public PositionInfo genPositionInfo(int offset) {
        return this.genPositionInfo(Tuple.tuple(0, offset));
    }

    default public PositionInfo genPositionInfo(Tuple2<Integer, Integer> offset) {
        return new PositionInfo(this.getErrorLine() + offset.getV1(), this.getErrorColumn() + offset.getV2());
    }

    public int getErrorLine();

    public int getErrorColumn();
}

