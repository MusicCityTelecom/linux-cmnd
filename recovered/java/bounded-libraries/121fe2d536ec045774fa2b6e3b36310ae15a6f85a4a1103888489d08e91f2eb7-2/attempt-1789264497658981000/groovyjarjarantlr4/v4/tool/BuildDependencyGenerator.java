/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.stringtemplate.v4.ST
 *  org.stringtemplate.v4.STGroup
 *  org.stringtemplate.v4.STGroupFile
 */
package groovyjarjarantlr4.v4.tool;

import groovyjarjarantlr4.v4.Tool;
import groovyjarjarantlr4.v4.codegen.CodeGenerator;
import groovyjarjarantlr4.v4.codegen.Target;
import groovyjarjarantlr4.v4.tool.Grammar;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public class BuildDependencyGenerator {
    protected Tool tool;
    protected Grammar g;
    protected CodeGenerator generator;
    protected STGroup templates;

    public BuildDependencyGenerator(Tool tool, Grammar g) {
        this.tool = tool;
        this.g = g;
        String language = g.getOptionString("language");
        this.generator = new CodeGenerator(tool, g, language);
    }

    public List<File> getGeneratedFileList() {
        List<Grammar> imports;
        String suffix;
        Target target = this.generator.getTarget();
        if (target == null) {
            return new ArrayList<File>();
        }
        ArrayList<File> files = new ArrayList<File>();
        if (this.generator.getTarget().needsHeader()) {
            files.add(this.getOutputFile(this.generator.getRecognizerFileName(true)));
        }
        files.add(this.getOutputFile(this.generator.getRecognizerFileName(false)));
        files.add(this.getOutputFile(this.generator.getVocabFileName()));
        ST headerExtST = null;
        ST extST = target.getTemplates().getInstanceOf("codeFileExtension");
        if (target.getTemplates().isDefined("headerFile")) {
            headerExtST = target.getTemplates().getInstanceOf("headerFileExtension");
            suffix = Grammar.getGrammarTypeToFileNameSuffix(this.g.getType());
            String fileName = this.g.name + suffix + headerExtST.render();
            files.add(this.getOutputFile(fileName));
        }
        if (this.g.isCombined()) {
            suffix = Grammar.getGrammarTypeToFileNameSuffix(31);
            String lexer = this.g.name + suffix + extST.render();
            files.add(this.getOutputFile(lexer));
            String lexerTokens = this.g.name + suffix + ".tokens";
            files.add(this.getOutputFile(lexerTokens));
            if (headerExtST != null) {
                String header = this.g.name + suffix + headerExtST.render();
                files.add(this.getOutputFile(header));
            }
        }
        if (this.g.tool.gen_listener) {
            if (this.generator.getTarget().needsHeader()) {
                files.add(this.getOutputFile(this.generator.getListenerFileName(true)));
            }
            files.add(this.getOutputFile(this.generator.getListenerFileName(false)));
            if (this.generator.getTarget().needsHeader()) {
                files.add(this.getOutputFile(this.generator.getBaseListenerFileName(true)));
            }
            files.add(this.getOutputFile(this.generator.getBaseListenerFileName(false)));
        }
        if (this.g.tool.gen_visitor) {
            if (this.generator.getTarget().needsHeader()) {
                files.add(this.getOutputFile(this.generator.getVisitorFileName(true)));
            }
            files.add(this.getOutputFile(this.generator.getVisitorFileName(false)));
            if (this.generator.getTarget().needsHeader()) {
                files.add(this.getOutputFile(this.generator.getBaseVisitorFileName(true)));
            }
            files.add(this.getOutputFile(this.generator.getBaseVisitorFileName(false)));
        }
        if ((imports = this.g.getAllImportedGrammars()) != null) {
            for (Grammar g : imports) {
                files.add(this.getOutputFile(g.fileName));
            }
        }
        if (files.isEmpty()) {
            return null;
        }
        return files;
    }

    public File getOutputFile(String fileName) {
        File outputDir = this.tool.getOutputDirectory(this.g.fileName);
        if (outputDir.toString().equals(".")) {
            outputDir = this.tool.getOutputDirectory(fileName);
        }
        if (outputDir.toString().equals(".")) {
            return new File(fileName);
        }
        if (outputDir.getName().equals(".")) {
            String fname = outputDir.toString();
            int dot = fname.lastIndexOf(46);
            outputDir = new File(outputDir.toString().substring(0, dot));
        }
        if (outputDir.getName().indexOf(32) >= 0) {
            String escSpaces = outputDir.toString().replace(" ", "\\ ");
            outputDir = new File(escSpaces);
        }
        return new File(outputDir, fileName);
    }

    public List<File> getDependenciesFileList() {
        List<File> files = this.getNonImportDependenciesFileList();
        List<Grammar> imports = this.g.getAllImportedGrammars();
        if (imports != null) {
            for (Grammar g : imports) {
                String libdir = this.tool.libDirectory;
                String fileName = this.groomQualifiedFileName(libdir, g.fileName);
                files.add(new File(fileName));
            }
        }
        if (files.isEmpty()) {
            return null;
        }
        return files;
    }

    public List<File> getNonImportDependenciesFileList() {
        ArrayList<File> files = new ArrayList<File>();
        String tokenVocab = this.g.getOptionString("tokenVocab");
        if (tokenVocab != null) {
            String fileName = tokenVocab + ".tokens";
            File vocabFile = this.tool.libDirectory.equals(".") ? new File(fileName) : new File(this.tool.libDirectory, fileName);
            files.add(vocabFile);
        }
        return files;
    }

    public ST getDependencies() {
        this.loadDependencyTemplates();
        ST dependenciesST = this.templates.getInstanceOf("dependencies");
        dependenciesST.add("in", this.getDependenciesFileList());
        dependenciesST.add("out", this.getGeneratedFileList());
        dependenciesST.add("grammarFileName", (Object)this.g.fileName);
        return dependenciesST;
    }

    public void loadDependencyTemplates() {
        if (this.templates != null) {
            return;
        }
        String fileName = "groovyjarjarantlr4/v4/tool/templates/depend.stg";
        this.templates = new STGroupFile(fileName, "UTF-8");
    }

    public CodeGenerator getGenerator() {
        return this.generator;
    }

    public String groomQualifiedFileName(String outputDir, String fileName) {
        if (outputDir.equals(".")) {
            return fileName;
        }
        if (outputDir.indexOf(32) >= 0) {
            String escSpaces = outputDir.replace(" ", "\\ ");
            return escSpaces + File.separator + fileName;
        }
        return outputDir + File.separator + fileName;
    }
}

