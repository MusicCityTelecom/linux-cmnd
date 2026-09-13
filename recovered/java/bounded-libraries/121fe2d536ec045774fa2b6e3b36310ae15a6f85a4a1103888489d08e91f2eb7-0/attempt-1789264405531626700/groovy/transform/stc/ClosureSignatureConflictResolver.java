/*
 * Decompiled with CFR 0.152.
 */
package groovy.transform.stc;

import java.util.List;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.SourceUnit;

public class ClosureSignatureConflictResolver {
    public List<ClassNode[]> resolve(List<ClassNode[]> candidates, ClassNode receiver, Expression arguments, ClosureExpression closure, MethodNode methodNode, SourceUnit sourceUnit, CompilationUnit compilationUnit, String[] options) {
        return candidates;
    }
}

