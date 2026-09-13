/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.tools;

import groovy.lang.GroovySystem;
import groovy.ui.GroovyMain;
import groovyjarjarpicocli.CommandLine;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.ConfigurationException;
import org.codehaus.groovy.runtime.DefaultGroovyStaticMethods;
import org.codehaus.groovy.runtime.StringGroovyMethods;
import org.codehaus.groovy.tools.ErrorReporter;
import org.codehaus.groovy.tools.javac.JavaAwareCompilationUnit;

public class FileSystemCompiler {
    private static boolean displayStackTraceOnError = false;
    private final CompilationUnit unit;

    public FileSystemCompiler(CompilerConfiguration configuration) throws ConfigurationException {
        this(configuration, null);
    }

    public FileSystemCompiler(CompilerConfiguration configuration, CompilationUnit cu) throws ConfigurationException {
        this.unit = cu != null ? cu : (configuration.getJointCompilationOptions() != null ? new JavaAwareCompilationUnit(configuration) : new CompilationUnit(configuration));
    }

    public static void displayHelp() {
        FileSystemCompiler.displayHelp(new PrintWriter(System.err, true));
    }

    public static void displayHelp(PrintWriter writer) {
        FileSystemCompiler.configureParser(new CompilationOptions()).usage(writer);
    }

    public static void displayVersion() {
        FileSystemCompiler.displayVersion(new PrintWriter(System.err, true));
    }

    public static void displayVersion(PrintWriter writer) {
        for (String line : new VersionProvider().getVersion()) {
            writer.println(line);
        }
    }

    public static int checkFiles(String[] filenames) {
        int errors = 0;
        for (String filename : filenames) {
            File file = new File(filename);
            if (!file.exists()) {
                System.err.println("error: file not found: " + file);
                ++errors;
                continue;
            }
            if (file.canRead()) continue;
            System.err.println("error: file not readable: " + file);
            ++errors;
        }
        return errors;
    }

    public static boolean validateFiles(String[] filenames) {
        return FileSystemCompiler.checkFiles(filenames) == 0;
    }

    public static void commandLineCompile(String[] args) throws Exception {
        FileSystemCompiler.commandLineCompile(args, true);
    }

    public static void commandLineCompile(String[] args, boolean lookupUnnamedFiles) throws Exception {
        boolean fileNameErrors;
        CompilationOptions options = new CompilationOptions();
        CommandLine parser = FileSystemCompiler.configureParser(options);
        CommandLine.ParseResult parseResult = parser.parseArgs(args);
        if (CommandLine.printHelpIfRequested(parseResult)) {
            return;
        }
        displayStackTraceOnError = options.printStack;
        CompilerConfiguration configuration = options.toCompilerConfiguration();
        String[] filenames = options.generateFileNames();
        boolean bl = fileNameErrors = filenames == null;
        if (!fileNameErrors && filenames.length == 0) {
            parser.usage(System.err);
            return;
        }
        boolean bl2 = fileNameErrors = fileNameErrors && !FileSystemCompiler.validateFiles(filenames);
        if (!fileNameErrors) {
            FileSystemCompiler.doCompilation(configuration, null, filenames, lookupUnnamedFiles);
        }
    }

    public static CommandLine configureParser(CompilationOptions options) {
        CommandLine parser = new CommandLine(options);
        parser.getCommandSpec().parser().unmatchedArgumentsAllowed(true).unmatchedOptionsArePositionalParams(true).expandAtFiles(false).toggleBooleanFlags(false);
        return parser;
    }

    public static void main(String[] args) {
        FileSystemCompiler.commandLineCompileWithErrorHandling(args, true);
    }

    public static void commandLineCompileWithErrorHandling(String[] args, boolean lookupUnnamedFiles) {
        try {
            FileSystemCompiler.commandLineCompile(args, lookupUnnamedFiles);
        }
        catch (Throwable e) {
            new ErrorReporter(e, displayStackTraceOnError).write(System.err);
            System.exit(1);
        }
    }

    public static void doCompilation(CompilerConfiguration configuration, CompilationUnit unit, String[] filenames) throws Exception {
        FileSystemCompiler.doCompilation(configuration, unit, filenames, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void doCompilation(CompilerConfiguration configuration, CompilationUnit unit, String[] filenames, boolean lookupUnnamedFiles) throws Exception {
        File tmpDir = null;
        try {
            if (configuration.getJointCompilationOptions() != null && !configuration.getJointCompilationOptions().containsKey("stubDir")) {
                tmpDir = DefaultGroovyStaticMethods.createTempDir(null, "groovy-generated-", "-java-source");
                configuration.getJointCompilationOptions().put("stubDir", tmpDir);
            }
            FileSystemCompiler compiler = new FileSystemCompiler(configuration, unit);
            if (lookupUnnamedFiles) {
                for (String filename2 : filenames) {
                    File file = new File(filename2);
                    if (!file.isFile()) continue;
                    URL url = file.getAbsoluteFile().getParentFile().toURI().toURL();
                    compiler.unit.getClassLoader().addURL(url);
                }
            } else {
                compiler.unit.getClassLoader().setResourceLoader(filename -> null);
            }
            compiler.compile(filenames);
        }
        catch (Throwable throwable) {
            try {
                if (tmpDir != null) {
                    FileSystemCompiler.deleteRecursive(tmpDir);
                }
            }
            catch (Throwable t) {
                System.err.println("error: could not delete temp files - " + tmpDir.getPath());
            }
            throw throwable;
        }
        try {
            if (tmpDir != null) {
                FileSystemCompiler.deleteRecursive(tmpDir);
            }
        }
        catch (Throwable t) {
            System.err.println("error: could not delete temp files - " + tmpDir.getPath());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static String[] generateFileNamesFromOptions(List<String> filenames) {
        if (filenames == null) {
            return new String[0];
        }
        ArrayList<String> fileList = new ArrayList<String>(filenames.size());
        boolean errors = false;
        for (String filename : filenames) {
            if (filename.startsWith("@")) {
                String fn = filename.substring(1);
                BufferedReader br = null;
                try {
                    String file;
                    br = new BufferedReader(new FileReader(fn));
                    while ((file = br.readLine()) != null) {
                        fileList.add(file);
                    }
                    continue;
                }
                catch (IOException ioe) {
                    System.err.println("error: file not readable: " + fn);
                    errors = true;
                    continue;
                }
                finally {
                    if (null == br) continue;
                    try {
                        br.close();
                    }
                    catch (IOException e) {
                        System.err.println("error: failed to close buffered reader: " + fn);
                        errors = true;
                    }
                    continue;
                }
            }
            fileList.add(filename);
        }
        if (errors) {
            return null;
        }
        return fileList.toArray(new String[0]);
    }

    public static void deleteRecursive(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            File[] files;
            for (File value : files = file.listFiles()) {
                FileSystemCompiler.deleteRecursive(value);
            }
            file.delete();
        }
    }

    public void compile(String[] paths) throws Exception {
        this.unit.addSources(paths);
        this.unit.compile();
    }

    public void compile(File[] files) throws Exception {
        this.unit.addSources(files);
        this.unit.compile();
    }

    @CommandLine.Command(name="groovyc", customSynopsis={"groovyc [options] <source-files>"}, sortOptions=false, versionProvider=VersionProvider.class)
    public static class CompilationOptions {
        @CommandLine.Option(names={"-cp", "-classpath", "--classpath"}, paramLabel="<path>", description={"Specify where to find the class files - must be first argument"})
        private String classpath;
        @CommandLine.Option(names={"-sourcepath", "--sourcepath"}, paramLabel="<path>", description={"Specify where to find the source files"})
        private File sourcepath;
        @CommandLine.Option(names={"--temp"}, paramLabel="<temp>", description={"Specify temporary directory"})
        private File temp;
        @CommandLine.Option(names={"--encoding"}, description={"Specify the encoding of the user class files"})
        private String encoding;
        @CommandLine.Option(names={"-d"}, paramLabel="<dir>", description={"Specify where to place generated class files"})
        private File targetDir;
        @CommandLine.Option(names={"-e", "--exception"}, description={"Print stack trace on error"})
        private boolean printStack;
        @CommandLine.Option(names={"-pa", "--parameters"}, description={"Generate metadata for reflection on method parameter names (jdk8+ only)"})
        private boolean parameterMetadata;
        @CommandLine.Option(names={"-pr", "--enable-preview"}, description={"Enable preview Java features (jdk12+ only) - must be after classpath but before other arguments"})
        private boolean previewFeatures;
        @CommandLine.Option(names={"-j", "--jointCompilation"}, description={"Attach javac compiler to compile .java files"})
        private boolean jointCompilation;
        @CommandLine.Option(names={"-b", "--basescript"}, paramLabel="<class>", description={"Base class name for scripts (must derive from Script)"})
        private String scriptBaseClass;
        @CommandLine.Option(names={"-J"}, paramLabel="<property=value>", description={"Name-value pairs to pass to javac"})
        private Map<String, String> javacOptionsMap;
        @CommandLine.Option(names={"-F"}, paramLabel="<flag>", description={"Passed to javac for joint compilation"})
        private List<String> flags;
        @CommandLine.Option(names={"--configscript"}, paramLabel="<script>", description={"A script for tweaking the configuration options"})
        private String configScript;
        @CommandLine.Option(names={"-h", "--help"}, usageHelp=true, description={"Show this help message and exit"})
        private boolean helpRequested;
        @CommandLine.Option(names={"-v", "--version"}, versionHelp=true, description={"Print version information and exit"})
        private boolean versionRequested;
        @CommandLine.Parameters(description={"The groovy source files to compile, or @-files containing a list of source files to compile"}, paramLabel="<source-files>")
        private List<String> files;
        @CommandLine.Option(names={"--compile-static"}, description={"Use CompileStatic"})
        private boolean compileStatic;
        @CommandLine.Option(names={"--type-checked"}, description={"Use TypeChecked"})
        private boolean typeChecked;

        public CompilerConfiguration toCompilerConfiguration() throws IOException {
            CompilerConfiguration configuration = new CompilerConfiguration();
            if (this.classpath != null) {
                configuration.setClasspath(this.classpath);
            }
            if (this.targetDir != null && this.targetDir.getName().length() > 0) {
                configuration.setTargetDirectory(this.targetDir);
            }
            configuration.setParameters(this.parameterMetadata);
            configuration.setPreviewFeatures(this.previewFeatures);
            configuration.setSourceEncoding(this.encoding);
            configuration.setScriptBaseClass(this.scriptBaseClass);
            if (this.jointCompilation) {
                HashMap<String, Object> compilerOptions = new HashMap<String, Object>();
                compilerOptions.put("flags", this.javacFlags());
                compilerOptions.put("namedValues", this.javacNamedValues());
                configuration.setJointCompilationOptions(compilerOptions);
            }
            ArrayList<String> transformations = new ArrayList<String>();
            if (this.compileStatic) {
                transformations.add("ast(groovy.transform.CompileStatic)");
            }
            if (this.typeChecked) {
                transformations.add("ast(groovy.transform.TypeChecked)");
            }
            if (!transformations.isEmpty()) {
                GroovyMain.processConfigScriptText(GroovyMain.buildConfigScriptText(transformations), configuration);
            }
            String configScripts = System.getProperty("groovy.starter.configscripts", null);
            if (this.configScript != null || configScripts != null && !configScripts.isEmpty()) {
                ArrayList<String> scripts = new ArrayList<String>();
                if (this.configScript != null) {
                    scripts.add(this.configScript);
                }
                if (configScripts != null) {
                    scripts.addAll(StringGroovyMethods.tokenize((CharSequence)configScripts, Character.valueOf(',')));
                }
                GroovyMain.processConfigScripts(scripts, configuration);
            }
            return configuration;
        }

        public String[] generateFileNames() {
            return FileSystemCompiler.generateFileNamesFromOptions(this.files);
        }

        private String[] javacNamedValues() {
            ArrayList<String> result = new ArrayList<String>();
            if (this.javacOptionsMap != null) {
                for (Map.Entry<String, String> entry : this.javacOptionsMap.entrySet()) {
                    result.add(entry.getKey());
                    result.add(entry.getValue());
                }
            }
            return result.isEmpty() ? null : result.toArray(new String[0]);
        }

        private String[] javacFlags() {
            ArrayList<String> result = new ArrayList<String>();
            if (this.flags != null) {
                result.addAll(this.flags);
            }
            if (this.parameterMetadata) {
                result.add("parameters");
            }
            if (this.previewFeatures) {
                result.add("-enable-preview");
            }
            return result.isEmpty() ? null : result.toArray(new String[0]);
        }
    }

    public static class VersionProvider
    implements CommandLine.IVersionProvider {
        @Override
        public String[] getVersion() {
            return new String[]{"Groovy compiler version " + GroovySystem.getVersion(), "Copyright 2003-2022 The Apache Software Foundation. https://groovy-lang.org/", ""};
        }
    }
}

