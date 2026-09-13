/*
 * Decompiled with CFR 0.152.
 */
package jersey.repackaged.org.objectweb.asm;

import jersey.repackaged.org.objectweb.asm.Frame;
import jersey.repackaged.org.objectweb.asm.Label;
import jersey.repackaged.org.objectweb.asm.Symbol;
import jersey.repackaged.org.objectweb.asm.SymbolTable;

final class CurrentFrame
extends Frame {
    CurrentFrame(Label owner) {
        super(owner);
    }

    @Override
    void execute(int opcode, int arg, Symbol symbolArg, SymbolTable symbolTable) {
        super.execute(opcode, arg, symbolArg, symbolTable);
        Frame successor = new Frame(null);
        this.merge(symbolTable, successor, 0);
        this.copyFrom(successor);
    }
}

