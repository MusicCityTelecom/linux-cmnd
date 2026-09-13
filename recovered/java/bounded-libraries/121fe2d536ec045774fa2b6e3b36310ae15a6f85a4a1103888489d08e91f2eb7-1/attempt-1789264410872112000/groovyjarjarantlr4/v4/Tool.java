/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.stringtemplate.v4.STGroup
 */
package groovyjarjarantlr4.v4;

import groovyjarjarantlr4.runtime.ANTLRFileStream;
import groovyjarjarantlr4.runtime.ANTLRStringStream;
import groovyjarjarantlr4.runtime.CharStream;
import groovyjarjarantlr4.runtime.CommonTokenStream;
import groovyjarjarantlr4.runtime.ParserRuleReturnScope;
import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.TokenStream;
import groovyjarjarantlr4.v4.analysis.AnalysisPipeline;
import groovyjarjarantlr4.v4.automata.LexerATNFactory;
import groovyjarjarantlr4.v4.automata.ParserATNFactory;
import groovyjarjarantlr4.v4.codegen.CodeGenPipeline;
import groovyjarjarantlr4.v4.misc.Graph;
import groovyjarjarantlr4.v4.parse.ANTLRParser;
import groovyjarjarantlr4.v4.parse.GrammarASTAdaptor;
import groovyjarjarantlr4.v4.parse.GrammarTreeVisitor;
import groovyjarjarantlr4.v4.parse.ToolANTLRLexer;
import groovyjarjarantlr4.v4.parse.ToolANTLRParser;
import groovyjarjarantlr4.v4.parse.v3TreeGrammarException;
import groovyjarjarantlr4.v4.runtime.atn.ATNSerializer;
import groovyjarjarantlr4.v4.runtime.misc.IntegerList;
import groovyjarjarantlr4.v4.runtime.misc.LogManager;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import groovyjarjarantlr4.v4.semantics.SemanticPipeline;
import groovyjarjarantlr4.v4.tool.ANTLRMessage;
import groovyjarjarantlr4.v4.tool.ANTLRToolListener;
import groovyjarjarantlr4.v4.tool.BuildDependencyGenerator;
import groovyjarjarantlr4.v4.tool.DOTGenerator;
import groovyjarjarantlr4.v4.tool.DefaultToolListener;
import groovyjarjarantlr4.v4.tool.ErrorManager;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.Grammar;
import groovyjarjarantlr4.v4.tool.GrammarTransformPipeline;
import groovyjarjarantlr4.v4.tool.LexerGrammar;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ast.ActionAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarAST;
import groovyjarjarantlr4.v4.tool.ast.GrammarASTErrorNode;
import groovyjarjarantlr4.v4.tool.ast.GrammarRootAST;
import groovyjarjarantlr4.v4.tool.ast.RuleAST;
import groovyjarjarantlr4.v4.tool.ast.TerminalAST;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import org.stringtemplate.v4.STGroup;

public class Tool {
    public static final String VERSION;
    public static final String GRAMMAR_EXTENSION = ".g4";
    public static final String LEGACY_GRAMMAR_EXTENSION = ".g";
    public static final List<String> ALL_GRAMMAR_EXTENSIONS;
    public File inputDirectory;
    public String outputDirectory;
    public String libDirectory;
    public boolean generate_ATN_dot = false;
    public String grammarEncoding = null;
    public String msgFormat = "antlr";
    public boolean launch_ST_inspector = false;
    public boolean ST_inspector_wait_for_close = false;
    public boolean force_atn = false;
    public boolean log = false;
    public boolean gen_listener = true;
    public boolean gen_visitor = false;
    public boolean gen_dependencies = false;
    public String genPackage = null;
    public Map<String, String> grammarOptions = null;
    public boolean warnings_are_errors = false;
    public boolean longMessages = false;
    public boolean exact_output_dir = false;
    public static Option[] optionDefs;
    protected boolean haveOutputDir = false;
    protected boolean return_dont_exit = false;
    public static boolean internalOption_PrintGrammarTree;
    public static boolean internalOption_ShowATNConfigsInDFA;
    public final String[] args;
    protected List<String> grammarFiles = new ArrayList<String>();
    public ErrorManager errMgr;
    public LogManager logMgr = new LogManager();
    List<ANTLRToolListener> listeners = new CopyOnWriteArrayList<ANTLRToolListener>();
    DefaultToolListener defaultListener = new DefaultToolListener(this);
    private final Map<String, Grammar> importedGrammars = new HashMap<String, Grammar>();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void main(String[] args) {
        Tool antlr = new Tool(args);
        if (args.length == 0) {
            antlr.help();
            antlr.exit(0);
        }
        try {
            antlr.processGrammarsOnCommandLine();
        }
        finally {
            if (antlr.log) {
                try {
                    String logname = antlr.logMgr.save();
                    System.out.println("wrote " + logname);
                }
                catch (IOException ioe) {
                    antlr.errMgr.toolError(ErrorType.INTERNAL_ERROR, ioe, new Object[0]);
                }
            }
        }
        if (antlr.return_dont_exit) {
            return;
        }
        if (antlr.errMgr.getNumErrors() > 0) {
            antlr.exit(1);
        }
        antlr.exit(0);
    }

    public Tool() {
        this(null);
    }

    public Tool(String[] args) {
        this.args = args;
        this.errMgr = new ErrorManager(this);
        this.errMgr.setFormat("antlr");
        this.handleArgs();
        this.errMgr.setFormat(this.msgFormat);
    }

    protected void handleArgs() {
        File outDir;
        int i = 0;
        while (this.args != null && i < this.args.length) {
            String arg = this.args[i];
            ++i;
            if (arg.startsWith("-D")) {
                this.handleOptionSetArg(arg);
                continue;
            }
            if (arg.charAt(0) != '-') {
                if (this.grammarFiles.contains(arg)) continue;
                this.grammarFiles.add(arg);
                continue;
            }
            boolean found = false;
            for (Option o : optionDefs) {
                if (!arg.equals(o.name)) continue;
                found = true;
                String argValue = null;
                if (o.argType == OptionArgType.STRING) {
                    argValue = this.args[i];
                    ++i;
                }
                Class<?> c = this.getClass();
                try {
                    Field f = c.getField(o.fieldName);
                    if (argValue == null) {
                        if (arg.startsWith("-no-")) {
                            f.setBoolean(this, false);
                            continue;
                        }
                        f.setBoolean(this, true);
                        continue;
                    }
                    f.set(this, argValue);
                }
                catch (Exception e) {
                    this.errMgr.toolError(ErrorType.INTERNAL_ERROR, "can't access field " + o.fieldName);
                }
            }
            if (found) continue;
            this.errMgr.toolError(ErrorType.INVALID_CMDLINE_ARG, arg);
        }
        if (this.outputDirectory != null) {
            if (this.outputDirectory.endsWith("/") || this.outputDirectory.endsWith("\\")) {
                this.outputDirectory = this.outputDirectory.substring(0, this.outputDirectory.length() - 1);
            }
            outDir = new File(this.outputDirectory);
            this.haveOutputDir = true;
            if (outDir.exists() && !outDir.isDirectory()) {
                this.errMgr.toolError(ErrorType.OUTPUT_DIR_IS_FILE, this.outputDirectory);
                this.outputDirectory = ".";
            }
        } else {
            this.outputDirectory = ".";
        }
        if (this.libDirectory != null) {
            if (this.libDirectory.endsWith("/") || this.libDirectory.endsWith("\\")) {
                this.libDirectory = this.libDirectory.substring(0, this.libDirectory.length() - 1);
            }
            if (!(outDir = new File(this.libDirectory)).exists()) {
                this.errMgr.toolError(ErrorType.DIR_NOT_FOUND, this.libDirectory);
                this.libDirectory = ".";
            }
        } else {
            this.libDirectory = ".";
        }
        if (this.launch_ST_inspector) {
            STGroup.trackCreationEvents = true;
            this.return_dont_exit = true;
        }
    }

    protected void handleOptionSetArg(String arg) {
        int eq = arg.indexOf(61);
        if (eq > 0 && arg.length() > 3) {
            String option = arg.substring("-D".length(), eq);
            String value = arg.substring(eq + 1);
            if (value.length() == 0) {
                this.errMgr.toolError(ErrorType.BAD_OPTION_SET_SYNTAX, arg);
                return;
            }
            if (Grammar.parserOptions.contains(option) || Grammar.lexerOptions.contains(option)) {
                if (this.grammarOptions == null) {
                    this.grammarOptions = new HashMap<String, String>();
                }
                this.grammarOptions.put(option, value);
            } else {
                this.errMgr.grammarError(ErrorType.ILLEGAL_OPTION, null, null, option);
            }
        } else {
            this.errMgr.toolError(ErrorType.BAD_OPTION_SET_SYNTAX, arg);
        }
    }

    public void processGrammarsOnCommandLine() {
        List<GrammarRootAST> sortedGrammars = this.sortGrammarByTokenVocab(this.grammarFiles);
        for (GrammarRootAST t : sortedGrammars) {
            Grammar g = this.createGrammar(t);
            g.fileName = t.fileName;
            if (this.gen_dependencies) {
                BuildDependencyGenerator dep = new BuildDependencyGenerator(this, g);
                System.out.println(dep.getDependencies().render());
                continue;
            }
            if (this.errMgr.getNumErrors() != 0) continue;
            this.process(g, true);
        }
    }

    public void process(Grammar g, boolean gencode) {
        GrammarRootAST lexerAST;
        g.loadImportedGrammars();
        GrammarTransformPipeline transform = new GrammarTransformPipeline(g, this);
        transform.process();
        if (g.ast != null && g.ast.grammarType == 81 && !g.ast.hasErrors && (lexerAST = transform.extractImplicitLexer(g)) != null) {
            if (this.grammarOptions != null) {
                lexerAST.cmdLineOptions = this.grammarOptions;
            }
            LexerGrammar lexerg = new LexerGrammar(this, lexerAST);
            lexerg.fileName = g.fileName;
            lexerg.originalGrammar = g;
            g.implicitLexer = lexerg;
            lexerg.implicitLexerOwner = g;
            int prevErrors = this.errMgr.getNumErrors();
            this.processNonCombinedGrammar(lexerg, gencode);
            if (this.errMgr.getNumErrors() > prevErrors) {
                return;
            }
        }
        if (g.implicitLexer != null) {
            g.importVocab(g.implicitLexer);
        }
        this.processNonCombinedGrammar(g, gencode);
    }

    public void processNonCombinedGrammar(Grammar g, boolean gencode) {
        boolean ruleFail;
        if (g.ast == null || g.ast.hasErrors) {
            return;
        }
        if (internalOption_PrintGrammarTree) {
            System.out.println(g.ast.toStringTree());
        }
        if (ruleFail = this.checkForRuleIssues(g)) {
            return;
        }
        int prevErrors = this.errMgr.getNumErrors();
        SemanticPipeline sem = new SemanticPipeline(g);
        sem.process();
        if (this.errMgr.getNumErrors() > prevErrors) {
            return;
        }
        ParserATNFactory factory = g.isLexer() ? new LexerATNFactory((LexerGrammar)g) : new ParserATNFactory(g);
        g.atn = factory.createATN();
        if (this.generate_ATN_dot) {
            this.generateATNs(g);
        }
        if (gencode && g.tool.getNumErrors() == 0) {
            this.generateInterpreterData(g);
        }
        AnalysisPipeline anal = new AnalysisPipeline(g);
        anal.process();
        if (g.tool.getNumErrors() > prevErrors) {
            return;
        }
        if (gencode) {
            CodeGenPipeline gen = new CodeGenPipeline(g);
            gen.process();
        }
    }

    public boolean checkForRuleIssues(final Grammar g) {
        GrammarAST RULES = (GrammarAST)g.ast.getFirstChildWithType(97);
        ArrayList<GrammarAST> rules = new ArrayList<GrammarAST>(RULES.getAllChildrenWithType(94));
        for (GrammarAST mode : g.ast.getAllChildrenWithType(36)) {
            rules.addAll(mode.getAllChildrenWithType(94));
        }
        boolean redefinition = false;
        final HashMap<String, RuleAST> ruleToAST = new HashMap<String, RuleAST>();
        for (GrammarAST r : rules) {
            RuleAST ruleAST = (RuleAST)r;
            GrammarAST ID = (GrammarAST)ruleAST.getChild(0);
            String ruleName = ID.getText();
            RuleAST prev = (RuleAST)ruleToAST.get(ruleName);
            if (prev != null) {
                GrammarAST prevChild = (GrammarAST)prev.getChild(0);
                g.tool.errMgr.grammarError(ErrorType.RULE_REDEFINITION, g.fileName, ID.getToken(), ruleName, prevChild.getToken().getLine());
                redefinition = true;
                continue;
            }
            ruleToAST.put(ruleName, ruleAST);
        }
        class UndefChecker
        extends GrammarTreeVisitor {
            public boolean badref = false;

            UndefChecker() {
            }

            @Override
            public void tokenRef(TerminalAST ref) {
                if ("EOF".equals(ref.getText())) {
                    return;
                }
                if (g.isLexer()) {
                    this.ruleRef(ref, null);
                }
            }

            @Override
            public void ruleRef(GrammarAST ref, ActionAST arg) {
                RuleAST ruleAST = (RuleAST)ruleToAST.get(ref.getText());
                String fileName = ref.getToken().getInputStream().getSourceName();
                if (Character.isUpperCase(this.currentRuleName.charAt(0)) && Character.isLowerCase(ref.getText().charAt(0))) {
                    this.badref = true;
                    Tool.this.errMgr.grammarError(ErrorType.PARSER_RULE_REF_IN_LEXER_RULE, fileName, ref.getToken(), ref.getText(), this.currentRuleName);
                } else if (ruleAST == null) {
                    this.badref = true;
                    Tool.this.errMgr.grammarError(ErrorType.UNDEFINED_RULE_REF, fileName, ref.token, ref.getText());
                }
            }

            @Override
            public ErrorManager getErrorManager() {
                return Tool.this.errMgr;
            }
        }
        UndefChecker chk = new UndefChecker();
        chk.visitGrammar(g.ast);
        return redefinition || chk.badref;
    }

    public List<GrammarRootAST> sortGrammarByTokenVocab(List<String> fileNames) {
        Graph<String> g = new Graph<String>();
        ArrayList<GrammarRootAST> roots = new ArrayList<GrammarRootAST>();
        for (String fileName : fileNames) {
            GrammarRootAST t = this.parseGrammar(fileName);
            if (t == null || t instanceof GrammarASTErrorNode || t.hasErrors) continue;
            GrammarRootAST root = t;
            roots.add(root);
            root.fileName = fileName;
            String grammarName = root.getChild(0).getText();
            GrammarAST tokenVocabNode = Tool.findOptionValueAST(root, "tokenVocab");
            if (tokenVocabNode != null) {
                int lastSlash;
                String vocabName = tokenVocabNode.getText();
                int len = vocabName.length();
                char firstChar = vocabName.charAt(0);
                char lastChar = vocabName.charAt(len - 1);
                if (len >= 2 && firstChar == '\'' && lastChar == '\'') {
                    vocabName = vocabName.substring(1, len - 1);
                }
                if ((lastSlash = vocabName.lastIndexOf(47)) >= 0) {
                    vocabName = vocabName.substring(lastSlash + 1);
                }
                g.addEdge(grammarName, vocabName);
            }
            g.addEdge(grammarName, grammarName);
        }
        List sortedGrammarNames = g.sort();
        ArrayList<GrammarRootAST> sortedRoots = new ArrayList<GrammarRootAST>();
        block1: for (String grammarName : sortedGrammarNames) {
            for (GrammarRootAST root : roots) {
                if (!root.getGrammarName().equals(grammarName)) continue;
                sortedRoots.add(root);
                continue block1;
            }
        }
        return sortedRoots;
    }

    public static GrammarAST findOptionValueAST(GrammarRootAST root, String option) {
        GrammarAST options = (GrammarAST)root.getFirstChildWithType(42);
        if (options != null && options.getChildCount() > 0) {
            for (Object object : options.getChildren()) {
                GrammarAST c = (GrammarAST)object;
                if (c.getType() != 10 || !c.getChild(0).getText().equals(option)) continue;
                return (GrammarAST)c.getChild(1);
            }
        }
        return null;
    }

    public Grammar createGrammar(GrammarRootAST ast) {
        Grammar g = ast.grammarType == 31 ? new LexerGrammar(this, ast) : new Grammar(this, ast);
        GrammarTransformPipeline.setGrammarPtr(g, ast);
        return g;
    }

    public GrammarRootAST parseGrammar(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.isAbsolute()) {
                file = new File(this.inputDirectory, fileName);
            }
            ANTLRFileStream in = new ANTLRFileStream(file.getAbsolutePath(), this.grammarEncoding);
            GrammarRootAST t = this.parse(fileName, in);
            return t;
        }
        catch (IOException ioe) {
            this.errMgr.toolError(ErrorType.CANNOT_OPEN_FILE, ioe, fileName);
            return null;
        }
    }

    public Grammar loadGrammar(String fileName) {
        GrammarRootAST grammarRootAST = this.parseGrammar(fileName);
        Grammar g = this.createGrammar(grammarRootAST);
        g.fileName = fileName;
        this.process(g, false);
        return g;
    }

    public Grammar loadImportedGrammar(Grammar g, GrammarAST nameNode) throws IOException {
        String name = nameNode.getText();
        Grammar imported = this.importedGrammars.get(name);
        if (imported == null) {
            g.tool.log("grammar", "load " + name + " from " + g.fileName);
            File importedFile = null;
            for (String extension : ALL_GRAMMAR_EXTENSIONS) {
                importedFile = this.getImportedGrammarFile(g, name + extension);
                if (importedFile == null) continue;
                break;
            }
            if (importedFile == null) {
                this.errMgr.grammarError(ErrorType.CANNOT_FIND_IMPORTED_GRAMMAR, g.fileName, nameNode.getToken(), name);
                return null;
            }
            String absolutePath = importedFile.getAbsolutePath();
            ANTLRFileStream in = new ANTLRFileStream(absolutePath, this.grammarEncoding);
            GrammarRootAST root = this.parse(g.fileName, in);
            if (root == null) {
                return null;
            }
            imported = this.createGrammar(root);
            imported.fileName = absolutePath;
            this.importedGrammars.put(root.getGrammarName(), imported);
        }
        return imported;
    }

    public GrammarRootAST parseGrammarFromString(String grammar) {
        return this.parse("<string>", new ANTLRStringStream(grammar));
    }

    public GrammarRootAST parse(String fileName, CharStream in) {
        try {
            CommonTokenStream tokens;
            GrammarASTAdaptor adaptor = new GrammarASTAdaptor(in);
            ToolANTLRLexer lexer = new ToolANTLRLexer(in, this);
            lexer.tokens = tokens = new CommonTokenStream(lexer);
            ToolANTLRParser p = new ToolANTLRParser((TokenStream)tokens, this);
            p.setTreeAdaptor(adaptor);
            try {
                ANTLRParser.grammarSpec_return r = p.grammarSpec();
                GrammarAST root = (GrammarAST)((ParserRuleReturnScope)r).getTree();
                if (root instanceof GrammarRootAST) {
                    boolean bl = ((GrammarRootAST)root).hasErrors = lexer.getNumberOfSyntaxErrors() > 0 || p.getNumberOfSyntaxErrors() > 0;
                    assert (((GrammarRootAST)root).tokenStream == tokens);
                    if (this.grammarOptions != null) {
                        ((GrammarRootAST)root).cmdLineOptions = this.grammarOptions;
                    }
                    return (GrammarRootAST)root;
                }
            }
            catch (v3TreeGrammarException e) {
                this.errMgr.grammarError(ErrorType.V3_TREE_GRAMMAR, fileName, e.location, new Object[0]);
            }
            return null;
        }
        catch (RecognitionException re) {
            ErrorManager.internalError("can't generate this message at moment; antlr recovers");
            return null;
        }
    }

    public void generateATNs(Grammar g) {
        DOTGenerator dotGenerator = new DOTGenerator(g);
        ArrayList<Grammar> grammars = new ArrayList<Grammar>();
        grammars.add(g);
        List<Grammar> imported = g.getAllImportedGrammars();
        if (imported != null) {
            grammars.addAll(imported);
        }
        for (Grammar ig : grammars) {
            for (Rule r : ig.rules.values()) {
                try {
                    String dot = dotGenerator.getDOT(g.atn.ruleToStartState[r.index], g.isLexer());
                    if (dot == null) continue;
                    this.writeDOTFile(g, r, dot);
                }
                catch (IOException ioe) {
                    this.errMgr.toolError(ErrorType.CANNOT_WRITE_FILE, ioe, new Object[0]);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void generateInterpreterData(Grammar g) {
        String[] names;
        StringBuilder content = new StringBuilder();
        content.append("token literal names:\n");
        for (String name : names = g.getTokenLiteralNames()) {
            content.append(name).append("\n");
        }
        content.append("\n");
        content.append("token symbolic names:\n");
        for (String name : names = g.getTokenSymbolicNames()) {
            content.append(name).append("\n");
        }
        content.append("\n");
        content.append("rule names:\n");
        for (String name : names = g.getRuleNames()) {
            content.append(name).append("\n");
        }
        content.append("\n");
        if (g.isLexer()) {
            content.append("channel names:\n");
            content.append("DEFAULT_TOKEN_CHANNEL\n");
            content.append("HIDDEN\n");
            for (String channel : g.channelValueToNameList) {
                content.append(channel).append("\n");
            }
            content.append("\n");
            content.append("mode names:\n");
            for (String mode : ((LexerGrammar)g).modes.keySet()) {
                content.append(mode).append("\n");
            }
        }
        content.append("\n");
        IntegerList serializedATN = ATNSerializer.getSerialized(g.atn, Arrays.asList(g.getRuleNames()));
        content.append("atn:\n");
        content.append(serializedATN.toString());
        try {
            Writer fw = this.getOutputFileWriter(g, g.name + ".interp");
            try {
                fw.write(content.toString());
            }
            finally {
                fw.close();
            }
        }
        catch (IOException ioe) {
            this.errMgr.toolError(ErrorType.CANNOT_WRITE_FILE, ioe, new Object[0]);
        }
    }

    public Writer getOutputFileWriter(Grammar g, String fileName) throws IOException {
        if (this.outputDirectory == null) {
            return new StringWriter();
        }
        File outputDir = this.getOutputDirectory(g.fileName);
        File outputFile = new File(outputDir, fileName);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(outputFile);
        OutputStreamWriter osw = this.grammarEncoding != null ? new OutputStreamWriter((OutputStream)fos, this.grammarEncoding) : new OutputStreamWriter(fos);
        return new BufferedWriter(osw);
    }

    public File getImportedGrammarFile(Grammar g, String fileName) {
        File gfile;
        String parentDir;
        File importedFile = new File(this.inputDirectory, fileName);
        if (!(importedFile.exists() || (importedFile = new File(parentDir = (gfile = new File(g.fileName)).getParent(), fileName)).exists() || (importedFile = new File(this.libDirectory, fileName)).exists())) {
            return null;
        }
        return importedFile;
    }

    public File getOutputDirectory(String fileNameWithPath) {
        String fileDirectory = fileNameWithPath == null || fileNameWithPath.lastIndexOf(File.separatorChar) == -1 ? "." : fileNameWithPath.substring(0, fileNameWithPath.lastIndexOf(File.separatorChar));
        File outputDir = this.haveOutputDir ? (this.exact_output_dir ? new File(this.outputDirectory) : (fileDirectory != null && (new File(fileDirectory).isAbsolute() || fileDirectory.startsWith("~")) ? new File(this.outputDirectory) : (fileDirectory != null ? new File(this.outputDirectory, fileDirectory) : new File(this.outputDirectory)))) : new File(fileDirectory);
        return outputDir;
    }

    protected void writeDOTFile(Grammar g, Rule r, String dot) throws IOException {
        this.writeDOTFile(g, r.g.name + "." + r.name, dot);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void writeDOTFile(Grammar g, String name, String dot) throws IOException {
        Writer fw = this.getOutputFileWriter(g, name + ".dot");
        try {
            fw.write(dot);
        }
        finally {
            fw.close();
        }
    }

    public void help() {
        this.info("ANTLR Parser Generator  Version " + VERSION);
        for (Option o : optionDefs) {
            String name = o.name + (o.argType != OptionArgType.NONE ? " ___" : "");
            String s = String.format(" %-19s %s", name, o.description);
            this.info(s);
        }
    }

    public void log(@Nullable String component, String msg) {
        this.logMgr.log(component, msg);
    }

    public void log(String msg) {
        this.log(null, msg);
    }

    public int getNumErrors() {
        return this.errMgr.getNumErrors();
    }

    public void addListener(ANTLRToolListener tl) {
        if (tl != null) {
            this.listeners.add(tl);
        }
    }

    public void removeListener(ANTLRToolListener tl) {
        this.listeners.remove(tl);
    }

    public void removeListeners() {
        this.listeners.clear();
    }

    public List<ANTLRToolListener> getListeners() {
        return this.listeners;
    }

    public void info(String msg) {
        if (this.listeners.isEmpty()) {
            this.defaultListener.info(msg);
            return;
        }
        for (ANTLRToolListener l : this.listeners) {
            l.info(msg);
        }
    }

    public void error(ANTLRMessage msg) {
        if (this.listeners.isEmpty()) {
            this.defaultListener.error(msg);
            return;
        }
        for (ANTLRToolListener l : this.listeners) {
            l.error(msg);
        }
    }

    public void warning(ANTLRMessage msg) {
        if (this.listeners.isEmpty()) {
            this.defaultListener.warning(msg);
        } else {
            for (ANTLRToolListener l : this.listeners) {
                l.warning(msg);
            }
        }
        if (this.warnings_are_errors) {
            this.errMgr.emit(ErrorType.WARNING_TREATED_AS_ERROR, new ANTLRMessage(ErrorType.WARNING_TREATED_AS_ERROR));
        }
    }

    public void version() {
        this.info("ANTLR Parser Generator  Version " + VERSION);
    }

    public void exit(int e) {
        System.exit(e);
    }

    public void panic() {
        throw new Error("ANTLR panic");
    }

    static {
        String version = Tool.class.getPackage().getImplementationVersion();
        VERSION = version != null ? version : "4.x";
        ALL_GRAMMAR_EXTENSIONS = Collections.unmodifiableList(Arrays.asList(GRAMMAR_EXTENSION, LEGACY_GRAMMAR_EXTENSION));
        optionDefs = new Option[]{new Option("outputDirectory", "-o", OptionArgType.STRING, "specify output directory where all output is generated"), new Option("libDirectory", "-lib", OptionArgType.STRING, "specify location of grammars, tokens files"), new Option("generate_ATN_dot", "-atn", "generate rule augmented transition network diagrams"), new Option("grammarEncoding", "-encoding", OptionArgType.STRING, "specify grammar file encoding; e.g., euc-jp"), new Option("msgFormat", "-message-format", OptionArgType.STRING, "specify output style for messages in antlr, gnu, vs2005"), new Option("longMessages", "-long-messages", "show exception details when available for errors and warnings"), new Option("gen_listener", "-listener", "generate parse tree listener (default)"), new Option("gen_listener", "-no-listener", "don't generate parse tree listener"), new Option("gen_visitor", "-visitor", "generate parse tree visitor"), new Option("gen_visitor", "-no-visitor", "don't generate parse tree visitor (default)"), new Option("genPackage", "-package", OptionArgType.STRING, "specify a package/namespace for the generated code"), new Option("gen_dependencies", "-depend", "generate file dependencies"), new Option("", "-D<option>=value", "set/override a grammar-level option"), new Option("warnings_are_errors", "-Werror", "treat warnings as errors"), new Option("launch_ST_inspector", "-XdbgST", "launch StringTemplate visualizer on generated code"), new Option("ST_inspector_wait_for_close", "-XdbgSTWait", "wait for STViz to close before continuing"), new Option("force_atn", "-Xforce-atn", "use the ATN simulator for all predictions"), new Option("log", "-Xlog", "dump lots of logging info to antlr-timestamp.log"), new Option("exact_output_dir", "-Xexact-output-dir", "all output goes into -o dir regardless of paths/package")};
        internalOption_PrintGrammarTree = false;
        internalOption_ShowATNConfigsInDFA = false;
    }

    public static class Option {
        String fieldName;
        String name;
        OptionArgType argType;
        String description;

        public Option(String fieldName, String name, String description) {
            this(fieldName, name, OptionArgType.NONE, description);
        }

        public Option(String fieldName, String name, OptionArgType argType, String description) {
            this.fieldName = fieldName;
            this.name = name;
            this.argType = argType;
            this.description = description;
        }
    }

    public static enum OptionArgType {
        NONE,
        STRING;

    }
}

