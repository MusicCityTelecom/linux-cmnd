/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.parser;

import org.apache.commons.jexl3.parser.ASTIdentifierAccess;
import org.apache.commons.jexl3.parser.Parser;

public class ASTIdentifierAccessSafe
extends ASTIdentifierAccess {
    ASTIdentifierAccessSafe(int id) {
        super(id);
    }

    ASTIdentifierAccessSafe(Parser p, int id) {
        super(p, id);
    }

    @Override
    public boolean isSafe() {
        return true;
    }
}

