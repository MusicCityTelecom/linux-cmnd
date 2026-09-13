/*
 * Decompiled with CFR 0.152.
 */
package groovy.transform.stc;

import groovy.transform.stc.ClosureSignatureHint;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.trait.Traits;

public class FromAbstractTypeMethods
extends ClosureSignatureHint {
    @Override
    public List<ClassNode[]> getClosureSignatures(MethodNode node, SourceUnit sourceUnit, CompilationUnit compilationUnit, String[] options, ASTNode usage) {
        String className = options[0];
        ClassNode classNode = this.findClassNode(sourceUnit, compilationUnit, className);
        ArrayList<ClassNode[]> signatures = new ArrayList<ClassNode[]>();
        for (MethodNode method : classNode.getAbstractMethods()) {
            if (method.isSynthetic() || Traits.hasDefaultImplementation(method) || ClassHelper.isGroovyObjectType(method.getDeclaringClass())) continue;
            signatures.add((ClassNode[])Arrays.stream(method.getParameters()).map(Parameter::getOriginType).toArray(ClassNode[]::new));
        }
        return signatures;
    }
}

