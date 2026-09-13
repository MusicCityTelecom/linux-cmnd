/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.builder;

import groovy.lang.GroovyCodeSource;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.runtime.DefaultGroovyMethodsSupport;

public class AstStringCompiler {
    public List<ASTNode> compile(String script) {
        return this.compile(script, CompilePhase.CONVERSION, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<ASTNode> compile(String script, CompilePhase compilePhase, boolean statementsOnly) {
        String scriptClassName = AstStringCompiler.makeScriptClassName();
        GroovyCodeSource codeSource = new GroovyCodeSource(script, scriptClassName + ".groovy", "/groovy/script");
        CompilationUnit cu = new CompilationUnit(CompilerConfiguration.DEFAULT, codeSource.getCodeSource(), null);
        try {
            cu.addSource(codeSource.getName(), script);
            cu.compile(compilePhase.getPhaseNumber());
        }
        finally {
            DefaultGroovyMethodsSupport.closeQuietly(cu.getClassLoader());
        }
        ArrayList<ASTNode> nodes = new ArrayList<ASTNode>();
        for (ModuleNode mn : cu.getAST().getModules()) {
            BlockStatement statementBlock = mn.getStatementBlock();
            if (statementBlock != null) {
                nodes.add(statementBlock);
            }
            for (ClassNode cn : mn.getClasses()) {
                if (statementsOnly && scriptClassName.equals(cn.getName())) continue;
                nodes.add(cn);
            }
        }
        return nodes;
    }

    private static String makeScriptClassName() {
        return "Script" + System.nanoTime();
    }
}

