/*
 * Decompiled with CFR 0.152.
 */
package groovy.ui;

import groovy.lang.Binding;
import groovy.lang.GroovyCodeSource;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.GroovyShell;
import groovy.lang.GroovySystem;
import groovy.lang.MissingMethodException;
import groovy.lang.Script;
import groovy.ui.GroovySocketServer;
import groovyjarjarpicocli.CommandLine;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.runtime.InvokerInvocationException;
import org.codehaus.groovy.runtime.ResourceGroovyMethods;
import org.codehaus.groovy.runtime.StackTraceUtils;
import org.codehaus.groovy.runtime.StringGroovyMethods;

public class GroovyMain {
    private List<String> args;
    private boolean isScriptFile;
    private String script;
    private boolean processFiles;
    private boolean editFiles;
    private boolean autoOutput;
    private boolean autoSplit;
    private String splitPattern = " ";
    private boolean processSockets;
    private int port;
    private String backupExtension;
    private boolean debug = false;
    private CompilerConfiguration conf = new CompilerConfiguration(System.getProperties());
    private static final Pattern URI_PATTERN = Pattern.compile("\\p{Alpha}[-+.\\p{Alnum}]*:[^\\\\]*");

    public static void main(String[] args) {
        GroovyMain.processArgs(args, System.out, System.err);
    }

    @Deprecated
    static void processArgs(String[] args, PrintStream out) {
        GroovyMain.processArgs(args, out, out);
    }

    static void processArgs(String[] args, PrintStream out, PrintStream err) {
        GroovyCommand groovyCommand = new GroovyCommand();
        CommandLine parser = new CommandLine(groovyCommand).setOut(new PrintWriter(out)).setErr(new PrintWriter(err)).setUnmatchedArgumentsAllowed(true).setStopAtUnmatched(true);
        try {
            CommandLine.ParseResult result = parser.parseArgs(args);
            if (CommandLine.printHelpIfRequested(result)) {
                return;
            }
            if (!groovyCommand.process(parser)) {
                System.exit(1);
            }
        }
        catch (CommandLine.ParameterException ex) {
            err.println(ex.getMessage());
            ex.getCommandLine().usage(err);
        }
        catch (IOException ioe) {
            err.println("error: " + ioe.getMessage());
        }
    }

    public static void processConfigScripts(List<String> scripts, CompilerConfiguration conf) throws IOException {
        if (scripts.isEmpty()) {
            return;
        }
        GroovyShell shell = GroovyMain.createConfigScriptsShell(conf);
        for (String script : scripts) {
            shell.evaluate(new File(script));
        }
    }

    public static void processConfigScriptText(String scriptText, CompilerConfiguration conf) {
        if (scriptText.trim().isEmpty()) {
            return;
        }
        GroovyShell shell = GroovyMain.createConfigScriptsShell(conf);
        shell.evaluate(scriptText);
    }

    public static String buildConfigScriptText(List<String> transforms) {
        StringBuilder script = new StringBuilder();
        script.append("withConfig(configuration) {").append("\n");
        for (String t : transforms) {
            script.append(t).append(";\n");
        }
        script.append("}");
        return script.toString();
    }

    private static GroovyShell createConfigScriptsShell(CompilerConfiguration conf) {
        Binding binding = new Binding();
        binding.setVariable("configuration", conf);
        CompilerConfiguration configuratorConfig = new CompilerConfiguration();
        ImportCustomizer customizer = new ImportCustomizer();
        customizer.addStaticStars("org.codehaus.groovy.control.customizers.builder.CompilerCustomizationBuilder");
        configuratorConfig.addCompilationCustomizers(customizer);
        return new GroovyShell(binding, configuratorConfig);
    }

    private boolean run() {
        try {
            if (this.processSockets) {
                this.processSockets();
            } else if (this.processFiles) {
                this.processFiles();
            } else {
                this.processOnce();
            }
            return true;
        }
        catch (CompilationFailedException e) {
            System.err.println(e);
            return false;
        }
        catch (Throwable e) {
            if (e instanceof InvokerInvocationException) {
                InvokerInvocationException iie = (InvokerInvocationException)e;
                e = iie.getCause();
            }
            System.err.println("Caught: " + e);
            if (!this.debug) {
                StackTraceUtils.deepSanitize(e);
            }
            e.printStackTrace();
            return false;
        }
    }

    private void processSockets() throws CompilationFailedException, IOException, URISyntaxException {
        GroovyShell groovy = new GroovyShell(this.conf);
        new GroovySocketServer(groovy, this.getScriptSource(this.isScriptFile, this.script), this.autoOutput, this.port);
    }

    @Deprecated
    public String getText(String uriOrFilename) throws IOException {
        if (URI_PATTERN.matcher(uriOrFilename).matches()) {
            try {
                return ResourceGroovyMethods.getText(new URL(uriOrFilename));
            }
            catch (Exception e) {
                throw new GroovyRuntimeException("Unable to get script from URL: ", e);
            }
        }
        return ResourceGroovyMethods.getText(this.huntForTheScriptFile(uriOrFilename));
    }

    protected GroovyCodeSource getScriptSource(boolean isScriptFile, String script) throws IOException, URISyntaxException {
        if (isScriptFile) {
            File scriptFile = this.huntForTheScriptFile(script);
            if (!scriptFile.exists() && URI_PATTERN.matcher(script).matches()) {
                return new GroovyCodeSource(new URI(script));
            }
            return new GroovyCodeSource(scriptFile);
        }
        return new GroovyCodeSource(script, "script_from_command_line", "/groovy/shell");
    }

    public static File searchForGroovyScriptFile(String input) {
        String scriptFileName = input.trim();
        File scriptFile = new File(scriptFileName);
        String[] standardExtensions = new String[]{".groovy", ".gvy", ".gy", ".gsh"};
        for (int i = 0; i < standardExtensions.length && !scriptFile.exists(); ++i) {
            scriptFile = new File(scriptFileName + standardExtensions[i]);
        }
        if (!scriptFile.exists()) {
            scriptFile = new File(scriptFileName);
        }
        return scriptFile;
    }

    public File huntForTheScriptFile(String input) {
        return GroovyMain.searchForGroovyScriptFile(input);
    }

    private static void setupContextClassLoader(GroovyShell shell) {
        Thread current = Thread.currentThread();
        class DoSetContext
        implements PrivilegedAction<Object> {
            ClassLoader classLoader;
            final /* synthetic */ Thread val$current;

            public DoSetContext(ClassLoader classLoader) {
                this.val$current = classLoader;
                this.classLoader = loader;
            }

            @Override
            public Object run() {
                this.val$current.setContextClassLoader(this.classLoader);
                return null;
            }
        }
        GroovyMain.doPrivileged(new DoSetContext((ClassLoader)shell.getClassLoader(), current));
    }

    private static <T> T doPrivileged(PrivilegedAction<T> action) {
        return AccessController.doPrivileged(action);
    }

    private void processFiles() throws CompilationFailedException, IOException, URISyntaxException {
        GroovyShell groovy = new GroovyShell(Thread.currentThread().getContextClassLoader(), this.conf);
        GroovyMain.setupContextClassLoader(groovy);
        Script s = groovy.parse(this.getScriptSource(this.isScriptFile, this.script));
        if (this.args.isEmpty()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));){
                PrintWriter writer = new PrintWriter(System.out);
                this.processReader(s, reader, writer);
                writer.flush();
            }
        } else {
            for (String filename : this.args) {
                File file = this.huntForTheScriptFile(filename);
                this.processFile(s, file);
            }
        }
    }

    private void processFile(Script s, File file) throws IOException {
        File backup;
        if (!file.exists()) {
            throw new FileNotFoundException(file.getName());
        }
        if (!this.editFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file));){
                PrintWriter writer = new PrintWriter(System.out);
                this.processReader(s, reader, writer);
                writer.flush();
            }
        }
        if (this.backupExtension == null) {
            backup = File.createTempFile("groovy_", ".tmp");
            backup.deleteOnExit();
        } else {
            backup = new File(file.getPath() + this.backupExtension);
        }
        backup.delete();
        if (!file.renameTo(backup)) {
            throw new IOException("unable to rename " + file + " to " + backup);
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(backup));
             PrintWriter writer = new PrintWriter(new FileWriter(file));){
            this.processReader(s, reader, writer);
        }
    }

    private void processReader(Script s, BufferedReader reader, PrintWriter pw) throws IOException {
        String line;
        String lineCountName = "count";
        s.setProperty(lineCountName, BigInteger.ZERO);
        String autoSplitName = "split";
        s.setProperty("out", pw);
        try {
            InvokerHelper.invokeMethod(s, "begin", null);
        }
        catch (MissingMethodException missingMethodException) {
            // empty catch block
        }
        while ((line = reader.readLine()) != null) {
            s.setProperty("line", line);
            s.setProperty(lineCountName, ((BigInteger)s.getProperty(lineCountName)).add(BigInteger.ONE));
            if (this.autoSplit) {
                s.setProperty(autoSplitName, line.split(this.splitPattern));
            }
            Object o = s.run();
            if (!this.autoOutput || o == null) continue;
            pw.println(o);
        }
        try {
            InvokerHelper.invokeMethod(s, "end", null);
        }
        catch (MissingMethodException missingMethodException) {
            // empty catch block
        }
    }

    private void processOnce() throws CompilationFailedException, IOException, URISyntaxException {
        GroovyShell groovy = new GroovyShell(Thread.currentThread().getContextClassLoader(), this.conf);
        GroovyMain.setupContextClassLoader(groovy);
        groovy.run(this.getScriptSource(this.isScriptFile, this.script), this.args);
    }

    @CommandLine.Command(name="groovy", customSynopsis={"groovy [options] [filename] [args]"}, description={"The Groovy command line processor."}, sortOptions=false, versionProvider=VersionProvider.class)
    private static class GroovyCommand {
        @CommandLine.Option(names={"-cp", "-classpath", "--classpath"}, paramLabel="<path>", description={"Specify where to find the class files - must be first argument"})
        private String classpath;
        @CommandLine.Option(names={"-D", "--define"}, paramLabel="<property=value>", description={"Define a system property"})
        private Map<String, String> systemProperties = new LinkedHashMap<String, String>();
        @CommandLine.Option(names={"--disableopt"}, paramLabel="optlist", split=",", description={"Disables one or all optimization elements; optlist can be a comma separated list with the elements: ", "all (disables all optimizations), ", "int (disable any int based optimizations)"})
        private List<String> disableopt = new ArrayList<String>();
        @CommandLine.Option(names={"-d", "--debug"}, description={"Debug mode will print out full stack traces"})
        private boolean debug;
        @CommandLine.Option(names={"-c", "--encoding"}, paramLabel="<charset>", description={"Specify the encoding of the files"})
        private String encoding;
        @CommandLine.Option(names={"-e"}, paramLabel="<script>", description={"Specify a command line script"})
        private String script;
        @CommandLine.Option(names={"-i"}, arity="0..1", paramLabel="<extension>", description={"Modify files in place; create backup if extension is given (e.g. '.bak')"})
        private String extension;
        @CommandLine.Option(names={"-n"}, description={"Process files line by line using implicit 'line' variable"})
        private boolean lineByLine;
        @CommandLine.Option(names={"-p"}, description={"Process files line by line and print result (see also -n)"})
        private boolean lineByLinePrint;
        @CommandLine.Option(names={"-pa", "--parameters"}, description={"Generate metadata for reflection on method parameter names (jdk8+ only)"})
        private boolean parameterMetadata;
        @CommandLine.Option(names={"-pr", "--enable-preview"}, description={"Enable preview Java features (jdk12+ only)"})
        private boolean previewFeatures;
        @CommandLine.Option(names={"-l"}, arity="0..1", paramLabel="<port>", description={"Listen on a port and process inbound lines (default: 1960)"})
        private String port;
        @CommandLine.Option(names={"-a", "--autosplit"}, arity="0..1", paramLabel="<splitPattern>", description={"Split lines using splitPattern (default '\\s') using implicit 'split' variable"})
        private String splitPattern;
        @CommandLine.Option(names={"--configscript"}, paramLabel="<script>", description={"A script for tweaking the configuration options"})
        private String configscript;
        @CommandLine.Option(names={"-b", "--basescript"}, paramLabel="<class>", description={"Base class name for scripts (must derive from Script)"})
        private String scriptBaseClass;
        @CommandLine.Option(names={"-h", "--help"}, usageHelp=true, description={"Show this help message and exit"})
        private boolean helpRequested;
        @CommandLine.Option(names={"-v", "--version"}, versionHelp=true, description={"Print version information and exit"})
        private boolean versionRequested;
        @CommandLine.Option(names={"--compile-static"}, description={"Use CompileStatic"})
        private boolean compileStatic;
        @CommandLine.Option(names={"--type-checked"}, description={"Use TypeChecked"})
        private boolean typeChecked;
        @CommandLine.Unmatched
        List<String> arguments = new ArrayList<String>();

        private GroovyCommand() {
        }

        boolean process(CommandLine parser) throws CommandLine.ParameterException, IOException {
            for (Map.Entry<String, String> entry : this.systemProperties.entrySet()) {
                System.setProperty(entry.getKey(), entry.getValue());
            }
            GroovyMain main = new GroovyMain();
            if (this.encoding != null) {
                main.conf.setSourceEncoding(this.encoding);
            }
            main.debug = this.debug;
            main.conf.setDebug(main.debug);
            main.conf.setParameters(this.parameterMetadata);
            main.conf.setPreviewFeatures(this.previewFeatures);
            main.processFiles = this.lineByLine || this.lineByLinePrint;
            main.autoOutput = this.lineByLinePrint;
            main.editFiles = this.extension != null;
            if (main.editFiles) {
                main.backupExtension = this.extension;
            }
            main.autoSplit = this.splitPattern != null;
            if (main.autoSplit) {
                main.splitPattern = this.splitPattern;
            }
            main.isScriptFile = this.script == null;
            if (main.isScriptFile) {
                if (this.arguments.isEmpty()) {
                    throw new CommandLine.ParameterException(parser, "error: neither -e or filename provided");
                }
                main.script = this.arguments.remove(0);
                if (main.script.endsWith(".java")) {
                    throw new CommandLine.ParameterException(parser, "error: cannot compile file with .java extension: " + main.script);
                }
            } else {
                main.script = this.script;
            }
            main.processSockets = this.port != null;
            if (main.processSockets) {
                String p = this.port.trim().length() > 0 ? this.port : "1960";
                main.port = Integer.parseInt(p);
            }
            for (String optimization : this.disableopt) {
                main.conf.getOptimizationOptions().put(optimization, false);
            }
            if (this.scriptBaseClass != null) {
                main.conf.setScriptBaseClass(this.scriptBaseClass);
            }
            ArrayList<String> transformations = new ArrayList<String>();
            if (this.compileStatic) {
                transformations.add("ast(groovy.transform.CompileStatic)");
            }
            if (this.typeChecked) {
                transformations.add("ast(groovy.transform.TypeChecked)");
            }
            if (!transformations.isEmpty()) {
                GroovyMain.processConfigScriptText(GroovyMain.buildConfigScriptText(transformations), main.conf);
            }
            GroovyMain.processConfigScripts(this.getConfigScripts(), main.conf);
            main.args = this.arguments;
            return main.run();
        }

        private List<String> getConfigScripts() {
            String configScripts;
            ArrayList<String> scripts = new ArrayList<String>();
            if (this.configscript != null) {
                scripts.add(this.configscript);
            }
            if ((configScripts = System.getProperty("groovy.starter.configscripts", null)) != null && !configScripts.isEmpty()) {
                scripts.addAll(StringGroovyMethods.tokenize((CharSequence)configScripts, Character.valueOf(',')));
            }
            return scripts;
        }
    }

    public static class VersionProvider
    implements CommandLine.IVersionProvider {
        @Override
        public String[] getVersion() {
            return new String[]{"Groovy Version: " + GroovySystem.getVersion() + " JVM: " + System.getProperty("java.version") + " Vendor: " + System.getProperty("java.vm.vendor") + " OS: " + System.getProperty("os.name")};
        }
    }
}

