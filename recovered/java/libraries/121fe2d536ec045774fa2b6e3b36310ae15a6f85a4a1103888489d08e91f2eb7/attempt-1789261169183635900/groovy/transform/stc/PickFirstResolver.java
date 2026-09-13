/*
 * Decompiled with CFR 0.152.
 */
package groovy.transform.stc;

import groovy.transform.stc.ClosureSignatureConflictResolver;
import java.util.Collections;
import java.util.List;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.SourceUnit;

public class PickFirstResolver
extends ClosureSignatureConflictResolver {
    @Override
    public List<ClassNode[]> resolve(List<ClassNode[]> candidates, ClassNode receiver, Expression arguments, ClosureExpression closure, MethodNode methodNode, SourceUnit sourceUnit, CompilationUnit compilationUnit, String[] options) {
        return Collections.singletonList(candidates.get(0));
    }
}

