/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.ansi;

import org.springframework.boot.ansi.AnsiElement;

public enum AnsiStyle implements AnsiElement
{
    NORMAL("0"),
    BOLD("1"),
    FAINT("2"),
    ITALIC("3"),
    UNDERLINE("4");

    private final String code;

    private AnsiStyle(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code;
    }
}

