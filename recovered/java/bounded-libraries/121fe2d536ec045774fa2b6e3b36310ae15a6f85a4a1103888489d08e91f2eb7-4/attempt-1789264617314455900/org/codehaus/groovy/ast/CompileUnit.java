/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast;

import groovy.lang.GroovyClassLoader;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.ast.NodeMetaDataHandler;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.syntax.SyntaxException;

public class CompileUnit
implements NodeMetaDataHandler {
    private final CompilerConfiguration config;
    private final GroovyClassLoader loader;
    private final CodeSource codeSource;
    private Map<?, ?> metaDataMap;
    private final List<ModuleNode> modules = new ArrayList<ModuleNode>();
    private final Map<String, ClassNode> classes = new LinkedHashMap<String, ClassNode>();
    private final Map<String, ClassNode> classesToCompile = new LinkedHashMap<String, ClassNode>();
    private final Map<String, SourceUnit> classNameToSource = new LinkedHashMap<String, SourceUnit>();
    private final Map<String, InnerClassNode> generatedInnerClasses = new LinkedHashMap<String, InnerClassNode>();

    public CompileUnit(GroovyClassLoader classLoader, CompilerConfiguration config) {
        this(classLoader, null, config);
    }

    public CompileUnit(GroovyClassLoader classLoader, CodeSource codeSource, CompilerConfiguration config) {
        this.loader = classLoader;
        this.codeSource = codeSource;
        this.config = config;
    }

    public CompilerConfiguration getConfig() {
        return this.config;
    }

    public GroovyClassLoader getClassLoader() {
        return this.loader;
    }

    public CodeSource getCodeSource() {
        return this.codeSource;
    }

    @Override
    public Map<?, ?> getMetaDataMap() {
        return this.metaDataMap;
    }

    @Override
    public void setMetaDataMap(Map<?, ?> metaDataMap) {
        this.metaDataMap = metaDataMap;
    }

    public List<ModuleNode> getModules() {
        return this.modules;
    }

    public List<ClassNode> getClasses() {
        ArrayList<ClassNode> answer = new ArrayList<ClassNode>();
        for (ModuleNode module : this.modules) {
            answer.addAll(module.getClasses());
        }
        return answer;
    }

    public ClassNode getClass(String name) {
        ClassNode cn = this.classes.get(name);
        if (cn == null) {
            cn = this.classesToCompile.get(name);
        }
        return cn;
    }

    public Map<String, ClassNode> getClassesToCompile() {
        return this.classesToCompile;
    }

    public SourceUnit getScriptSourceLocation(String className) {
        return this.classNameToSource.get(className);
    }

    public Map<String, InnerClassNode> getGeneratedInnerClasses() {
        return Collections.unmodifiableMap(this.generatedInnerClasses);
    }

    public InnerClassNode getGeneratedInnerClass(String name) {
        return this.generatedInnerClasses.get(name);
    }

    @Deprecated
    public boolean hasClassNodeToCompile() {
        return !this.classesToCompile.isEmpty();
    }

    @Deprecated
    public Iterator<String> iterateClassNodeToCompile() {
        return this.classesToCompile.keySet().iterator();
    }

    public void addModule(ModuleNode node) {
        if (node != null) {
            this.modules.add(node);
            node.setUnit(this);
            this.addClasses(node.getClasses());
        }
    }

    public void addClasses(List<ClassNode> list) {
        for (ClassNode node : list) {
            this.addClass(node);
        }
    }

    public void addClass(ClassNode node) {
        String name = (node = node.redirect()).getName();
        ClassNode stored = this.classes.get(name);
        if (stored != null && stored != node) {
            SourceUnit nodeSource = node.getModule().getContext();
            SourceUnit storedSource = stored.getModule().getContext();
            String txt = "Invalid duplicate class definition of class " + node.getName() + " : ";
            if (nodeSource == storedSource) {
                txt = txt + "The source " + nodeSource.getName() + " contains at least two definitions of the class " + node.getName() + ".\n";
                if (node.isScriptBody() || stored.isScriptBody()) {
                    txt = txt + "One of the classes is an explicit generated class using the class statement, the other is a class generated from the script body based on the file name. Solutions are to change the file name or to change the class name.\n";
                }
            } else {
                txt = txt + "The sources " + nodeSource.getName() + " and " + storedSource.getName() + " each contain a class with the name " + node.getName() + ".\n";
            }
            nodeSource.addErrorAndContinue(new SyntaxException(txt, node));
        }
        this.classes.put(name, node);
        ClassNode cn = this.classesToCompile.remove(name);
        if (cn != null) {
            cn.setRedirect(node);
        }
    }

    public void addClassNodeToCompile(ClassNode node, SourceUnit location) {
        String nodeName = node.getName();
        this.classesToCompile.put(nodeName, node);
        this.classNameToSource.put(nodeName, location);
    }

    public void addGeneratedInnerClass(InnerClassNode icn) {
        this.generatedInnerClasses.put(icn.getName(), icn);
    }
}

