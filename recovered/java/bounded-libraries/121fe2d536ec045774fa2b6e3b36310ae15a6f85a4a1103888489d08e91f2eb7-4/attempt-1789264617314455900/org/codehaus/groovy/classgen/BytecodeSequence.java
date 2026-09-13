/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.classgen.BytecodeInstruction;
import org.codehaus.groovy.classgen.ClassGenerator;

public class BytecodeSequence
extends Statement {
    private final List<?> instructions;

    public BytecodeSequence(BytecodeInstruction instruction) {
        this.instructions = Collections.singletonList(instruction);
    }

    public BytecodeSequence(List<?> instructions) {
        this.instructions = Objects.requireNonNull(instructions);
    }

    public List<?> getInstructions() {
        return Collections.unmodifiableList(this.instructions);
    }

    public BytecodeInstruction getBytecodeInstruction() {
        Object instruction;
        if (this.instructions.size() == 1 && (instruction = this.instructions.get(0)) instanceof BytecodeInstruction) {
            return (BytecodeInstruction)instruction;
        }
        return null;
    }

    @Override
    public void visit(GroovyCodeVisitor visitor) {
        if (visitor instanceof ClassGenerator) {
            ((ClassGenerator)visitor).visitBytecodeSequence(this);
        } else {
            for (Object instruction : this.instructions) {
                if (!(instruction instanceof ASTNode)) continue;
                ((ASTNode)instruction).visit(visitor);
            }
        }
    }
}

