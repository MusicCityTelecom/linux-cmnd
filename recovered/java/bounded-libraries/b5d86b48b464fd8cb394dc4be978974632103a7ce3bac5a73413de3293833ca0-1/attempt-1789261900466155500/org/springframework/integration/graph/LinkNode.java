/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.integration.graph;

public class LinkNode {
    private final int from;
    private final int to;
    private final Type type;

    public LinkNode(int from, int to, Type type) {
        this.from = from;
        this.to = to;
        this.type = type;
    }

    public int getFrom() {
        return this.from;
    }

    public int getTo() {
        return this.to;
    }

    public Type getType() {
        return this.type;
    }

    public static enum Type {
        input,
        output,
        error,
        discard,
        route;

    }
}

