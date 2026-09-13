/*
 * Decompiled with CFR 0.152.
 */
package groovy.transform.stc;

import groovy.transform.stc.SingleSignatureClosureHint;
import java.util.Arrays;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.SourceUnit;

public class SimpleType
extends SingleSignatureClosureHint {
    @Override
    public ClassNode[] getParameterTypes(MethodNode node, String[] options, SourceUnit sourceUnit, CompilationUnit compilationUnit, ASTNode usage) {
        return (ClassNode[])Arrays.stream(options).map(option -> this.findClassNode(sourceUnit, compilationUnit, (String)option)).toArray(ClassNode[]::new);
    }
}

