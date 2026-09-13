/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.control;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import org.apache.groovy.util.Maps;
import org.apache.groovy.util.SystemUtil;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.control.BytecodeProcessor;
import org.codehaus.groovy.control.ConfigurationException;
import org.codehaus.groovy.control.ParserPluginFactory;
import org.codehaus.groovy.control.SourceExtensionHandler;
import org.codehaus.groovy.control.customizers.CompilationCustomizer;
import org.codehaus.groovy.control.io.NullWriter;
import org.codehaus.groovy.runtime.StringGroovyMethods;
import org.codehaus.groovy.vmplugin.VMPlugin;

public class CompilerConfiguration {
    public static final String INVOKEDYNAMIC = "indy";
    public static final String GROOVYDOC = "groovydoc";
    public static final String RUNTIME_GROOVYDOC = "runtimeGroovydoc";
    public static final String PARALLEL_PARSE = "parallelParse";
    public static final String MEM_STUB = "memStub";
    public static final String JDK4 = "1.4";
    public static final String JDK5 = "1.5";
    public static final String JDK6 = "1.6";
    public static final String JDK7 = "1.7";
    public static final String JDK8 = "1.8";
    public static final String JDK9 = "9";
    public static final String JDK10 = "10";
    public static final String JDK11 = "11";
    public static final String JDK12 = "12";
    public static final String JDK13 = "13";
    public static final String JDK14 = "14";
    public static final String JDK15 = "15";
    public static final String JDK16 = "16";
    public static final String JDK17 = "17";
    public static final String JDK18 = "18";
    public static final String JDK19 = "19";
    @Deprecated
    public static final String POST_JDK5 = "1.5";
    @Deprecated
    public static final String PRE_JDK5 = "1.4";
    public static final Map<String, Integer> JDK_TO_BYTECODE_VERSION_MAP = Maps.of("1.4", 48, "1.5", 49, "1.6", 50, "1.7", 51, "1.8", 52, "9", 53, "10", 54, "11", 55, "12", 56, "13", 57, "14", 58, "15", 59, "16", 60, "17", 61, "18", 62, "19", 63);
    public static final String DEFAULT_TARGET_BYTECODE = CompilerConfiguration.defaultTargetBytecode();
    public static final String[] ALLOWED_JDKS = JDK_TO_BYTECODE_VERSION_MAP.keySet().toArray(new String[JDK_TO_BYTECODE_VERSION_MAP.size()]);
    public static final int ASM_API_VERSION = 589824;
    public static final String DEFAULT_SOURCE_ENCODING = "UTF-8";
    public static final CompilerConfiguration DEFAULT = new CompilerConfiguration(){

        @Override
        public List<String> getClasspath() {
            return Collections.unmodifiableList(super.getClasspath());
        }

        @Override
        public List<CompilationCustomizer> getCompilationCustomizers() {
            return Collections.unmodifiableList(super.getCompilationCustomizers());
        }

        @Override
        public Set<String> getDisabledGlobalASTTransformations() {
            return Optional.ofNullable(super.getDisabledGlobalASTTransformations()).map(Collections::unmodifiableSet).orElse(null);
        }

        @Override
        public Map<String, Object> getJointCompilationOptions() {
            return Optional.ofNullable(super.getJointCompilationOptions()).map(Collections::unmodifiableMap).orElse(null);
        }

        @Override
        public Map<String, Boolean> getOptimizationOptions() {
            return Collections.unmodifiableMap(super.getOptimizationOptions());
        }

        @Override
        public Set<String> getScriptExtensions() {
            return Collections.unmodifiableSet(super.getScriptExtensions());
        }

        @Override
        public void setBytecodePostprocessor(BytecodeProcessor bytecodePostprocessor) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setClasspath(String classpath) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setClasspathList(List<String> parts) {
            throw new UnsupportedOperationException();
        }

        @Override
        public CompilerConfiguration addCompilationCustomizers(CompilationCustomizer ... customizers) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setDebug(boolean debug) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setDefaultScriptExtension(String defaultScriptExtension) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setDisabledGlobalASTTransformations(Set<String> disabledGlobalASTTransformations) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setJointCompilationOptions(Map<String, Object> options) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setMinimumRecompilationInterval(int time) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setOptimizationOptions(Map<String, Boolean> options) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setOutput(PrintWriter output) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setParameters(boolean parameters) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setPluginFactory(ParserPluginFactory pluginFactory) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setPreviewFeatures(boolean previewFeatures) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setRecompileGroovySource(boolean recompile) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setScriptBaseClass(String scriptBaseClass) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setScriptExtensions(Set<String> scriptExtensions) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setSourceEncoding(String encoding) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setTargetBytecode(String version) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setTargetDirectory(File directory) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setTargetDirectory(String directory) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setTolerance(int tolerance) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setVerbose(boolean verbose) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setWarningLevel(int level) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setLogClassgen(boolean logClassgen) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setLogClassgenStackTraceMaxDepth(int logClassgenStackTraceMaxDepth) {
            throw new UnsupportedOperationException();
        }
    };
    private int warningLevel;
    private String sourceEncoding;
    private PrintWriter output;
    private File targetDirectory;
    private List<String> classpath;
    private boolean verbose;
    private boolean debug;
    private boolean parameters;
    private int tolerance;
    private String scriptBaseClass;
    private ParserPluginFactory pluginFactory;
    private String defaultScriptExtension;
    private Set<String> scriptExtensions = new LinkedHashSet<String>();
    private boolean recompileGroovySource;
    private int minimumRecompilationInterval;
    private String targetBytecode;
    private boolean previewFeatures;
    private boolean logClassgen;
    private int logClassgenStackTraceMaxDepth;
    private Map<String, Object> jointCompilationOptions;
    private Map<String, Boolean> optimizationOptions;
    private final List<CompilationCustomizer> compilationCustomizers = new LinkedList<CompilationCustomizer>();
    private Set<String> disabledGlobalASTTransformations;
    private BytecodeProcessor bytecodePostprocessor;

    public CompilerConfiguration() {
        this.classpath = new LinkedList<String>();
        this.tolerance = 10;
        this.minimumRecompilationInterval = 100;
        this.warningLevel = 1;
        this.parameters = SystemUtil.getBooleanSafe("groovy.parameters");
        this.previewFeatures = SystemUtil.getBooleanSafe("groovy.preview.features");
        this.logClassgen = SystemUtil.getBooleanSafe("groovy.log.classgen");
        this.logClassgenStackTraceMaxDepth = SystemUtil.getIntegerSafe("groovy.log.classgen.stacktrace.max.depth", 0);
        this.sourceEncoding = SystemUtil.getSystemPropertySafe("groovy.source.encoding", SystemUtil.getSystemPropertySafe("file.encoding", DEFAULT_SOURCE_ENCODING));
        this.setTargetDirectorySafe(SystemUtil.getSystemPropertySafe("groovy.target.directory"));
        this.setTargetBytecodeIfValid(SystemUtil.getSystemPropertySafe("groovy.target.bytecode", DEFAULT_TARGET_BYTECODE));
        this.defaultScriptExtension = SystemUtil.getSystemPropertySafe("groovy.default.scriptExtension", ".groovy");
        this.optimizationOptions = new HashMap<String, Boolean>(4);
        this.handleOptimizationOption(INVOKEDYNAMIC, SystemUtil.getSystemPropertySafe("groovy.target.indy", "true"));
        this.handleOptimizationOption(GROOVYDOC, SystemUtil.getSystemPropertySafe("groovy.attach.groovydoc"));
        this.handleOptimizationOption(RUNTIME_GROOVYDOC, SystemUtil.getSystemPropertySafe("groovy.attach.runtime.groovydoc"));
        this.handleOptimizationOption(PARALLEL_PARSE, SystemUtil.getSystemPropertySafe("groovy.parallel.parse", "true"));
        boolean memStubEnabled = Boolean.parseBoolean(SystemUtil.getSystemPropertySafe("groovy.mem.stub", "false"));
        if (memStubEnabled) {
            this.jointCompilationOptions = new HashMap<String, Object>(2);
            this.jointCompilationOptions.put(MEM_STUB, memStubEnabled);
        }
    }

    private void handleOptimizationOption(String key, String val) {
        if (val != null) {
            this.optimizationOptions.put(key, Boolean.valueOf(val));
        }
    }

    public CompilerConfiguration(CompilerConfiguration configuration) {
        this.setWarningLevel(configuration.getWarningLevel());
        this.setTargetDirectory(configuration.getTargetDirectory());
        this.setClasspathList(new LinkedList<String>(configuration.getClasspath()));
        this.setVerbose(configuration.getVerbose());
        this.setDebug(configuration.getDebug());
        this.setParameters(configuration.getParameters());
        this.setTolerance(configuration.getTolerance());
        this.setScriptBaseClass(configuration.getScriptBaseClass());
        this.setRecompileGroovySource(configuration.getRecompileGroovySource());
        this.setMinimumRecompilationInterval(configuration.getMinimumRecompilationInterval());
        this.setTargetBytecode(configuration.getTargetBytecode());
        this.setPreviewFeatures(configuration.isPreviewFeatures());
        this.setLogClassgen(configuration.isLogClassgen());
        this.setLogClassgenStackTraceMaxDepth(configuration.getLogClassgenStackTraceMaxDepth());
        this.setDefaultScriptExtension(configuration.getDefaultScriptExtension());
        this.setSourceEncoding(configuration.getSourceEncoding());
        this.setPluginFactory(configuration.getPluginFactory());
        this.setDisabledGlobalASTTransformations(configuration.getDisabledGlobalASTTransformations());
        this.setScriptExtensions(new LinkedHashSet<String>(configuration.getScriptExtensions()));
        this.setOptimizationOptions(new HashMap<String, Boolean>(configuration.getOptimizationOptions()));
        this.setBytecodePostprocessor(configuration.getBytecodePostprocessor());
        HashMap jointCompilationOptions = configuration.getJointCompilationOptions();
        this.setJointCompilationOptions(null != jointCompilationOptions ? new HashMap(jointCompilationOptions) : jointCompilationOptions);
    }

    public CompilerConfiguration(Properties configuration) throws ConfigurationException {
        this();
        this.configure(configuration);
    }

    public static boolean isPostJDK5(String bytecodeVersion) {
        return StringGroovyMethods.isAtLeast(bytecodeVersion, "1.5");
    }

    public static boolean isPostJDK7(String bytecodeVersion) {
        return StringGroovyMethods.isAtLeast(bytecodeVersion, JDK7);
    }

    public static boolean isPostJDK8(String bytecodeVersion) {
        return StringGroovyMethods.isAtLeast(bytecodeVersion, JDK8);
    }

    public static boolean isPostJDK9(String bytecodeVersion) {
        return StringGroovyMethods.isAtLeast(bytecodeVersion, JDK9);
    }

    public static boolean isPostJDK10(String bytecodeVersion) {
        return StringGroovyMethods.isAtLeast(bytecodeVersion, JDK10);
    }

    public static boolean isPostJDK11(String bytecodeVersion) {
        return StringGroovyMethods.isAtLeast(bytecodeVersion, JDK11);
    }

    public static boolean isPostJDK12(String bytecodeVersion) {
        return StringGroovyMethods.isAtLeast(bytecodeVersion, JDK12);
    }

    public static boolean isPostJDK13(String bytecodeVersion) {
        return StringGroovyMethods.isAtLeast(bytecodeVersion, JDK13);
    }

    public static boolean isPostJDK14(String bytecodeVersion) {
        return StringGroovyMethods.isAtLeast(bytecodeVersion, JDK14);
    }

    public static boolean isPostJDK15(String bytecodeVersion) {
        return StringGroovyMethods.isAtLeast(bytecodeVersion, JDK15);
    }

    public static boolean isPostJDK16(String bytecodeVersion) {
        return StringGroovyMethods.isAtLeast(bytecodeVersion, JDK16);
    }

    public static boolean isPostJDK17(String bytecodeVersion) {
        return StringGroovyMethods.isAtLeast(bytecodeVersion, JDK17);
    }

    public static boolean isPostJDK18(String bytecodeVersion) {
        return StringGroovyMethods.isAtLeast(bytecodeVersion, JDK18);
    }

    public void configure(Properties configuration) throws ConfigurationException {
        int numeric = this.getWarningLevel();
        String text = configuration.getProperty("groovy.warnings", "likely errors");
        try {
            numeric = Integer.parseInt(text);
        }
        catch (NumberFormatException e) {
            text = text.toLowerCase();
            if (text.equals("none")) {
                numeric = 0;
            }
            if (text.startsWith("likely")) {
                numeric = 1;
            }
            if (text.startsWith("possible")) {
                numeric = 2;
            }
            if (text.startsWith("paranoia")) {
                numeric = 3;
            }
            throw new ConfigurationException("unrecognized groovy.warnings: " + text);
        }
        this.setWarningLevel(numeric);
        text = configuration.getProperty("groovy.source.encoding");
        if (text == null) {
            text = configuration.getProperty("file.encoding", DEFAULT_SOURCE_ENCODING);
        }
        this.setSourceEncoding(text);
        text = configuration.getProperty("groovy.target.directory");
        if (text != null) {
            this.setTargetDirectory(text);
        }
        if ((text = configuration.getProperty("groovy.target.bytecode")) != null) {
            this.setTargetBytecode(text);
        }
        if ((text = configuration.getProperty("groovy.parameters")) != null) {
            this.setParameters(text.equalsIgnoreCase("true"));
        }
        if ((text = configuration.getProperty("groovy.preview.features")) != null) {
            this.setPreviewFeatures(text.equalsIgnoreCase("true"));
        }
        if ((text = configuration.getProperty("groovy.log.classgen")) != null) {
            this.setLogClassgen(text.equalsIgnoreCase("true"));
        }
        if ((text = configuration.getProperty("groovy.log.classgen.stacktrace.max.depth")) != null) {
            int logClassgenStackTraceMaxDepth = 0;
            try {
                logClassgenStackTraceMaxDepth = Integer.parseInt(text);
            }
            catch (Exception exception) {
                // empty catch block
            }
            this.setLogClassgenStackTraceMaxDepth(Math.max(logClassgenStackTraceMaxDepth, 0));
        }
        if ((text = configuration.getProperty("groovy.classpath")) != null) {
            this.setClasspath(text);
        }
        if ((text = configuration.getProperty("groovy.output.verbose")) != null) {
            this.setVerbose(text.equalsIgnoreCase("true"));
        }
        if ((text = configuration.getProperty("groovy.output.debug")) != null) {
            this.setDebug(text.equalsIgnoreCase("true"));
        }
        numeric = 10;
        text = configuration.getProperty("groovy.errors.tolerance", JDK10);
        try {
            numeric = Integer.parseInt(text);
        }
        catch (NumberFormatException e) {
            throw new ConfigurationException(e);
        }
        this.setTolerance(numeric);
        text = configuration.getProperty("groovy.default.scriptExtension");
        if (text != null) {
            this.setDefaultScriptExtension(text);
        }
        if ((text = configuration.getProperty("groovy.script.base")) != null) {
            this.setScriptBaseClass(text);
        }
        if ((text = configuration.getProperty("groovy.recompile")) != null) {
            this.setRecompileGroovySource(text.equalsIgnoreCase("true"));
        }
        numeric = 100;
        text = configuration.getProperty("groovy.recompile.minimumIntervall");
        try {
            if (text == null) {
                text = configuration.getProperty("groovy.recompile.minimumInterval");
            }
            if (text != null) {
                numeric = Integer.parseInt(text);
            }
        }
        catch (NumberFormatException e) {
            throw new ConfigurationException(e);
        }
        this.setMinimumRecompilationInterval(numeric);
        text = configuration.getProperty("groovy.disabled.global.ast.transformations");
        if (text != null) {
            String[] classNames = text.split(",\\s*}");
            HashSet<String> disabledTransforms = new HashSet<String>(Arrays.asList(classNames));
            this.setDisabledGlobalASTTransformations(disabledTransforms);
        }
    }

    public int getWarningLevel() {
        return this.warningLevel;
    }

    public void setWarningLevel(int level) {
        this.warningLevel = level < 0 || level > 3 ? 1 : level;
    }

    public String getSourceEncoding() {
        return this.sourceEncoding;
    }

    public void setSourceEncoding(String encoding) {
        this.sourceEncoding = Optional.ofNullable(encoding).orElse(DEFAULT_SOURCE_ENCODING);
    }

    @Deprecated
    public PrintWriter getOutput() {
        return this.output;
    }

    @Deprecated
    public void setOutput(PrintWriter output) {
        this.output = output == null ? new PrintWriter(NullWriter.DEFAULT) : output;
    }

    public File getTargetDirectory() {
        return this.targetDirectory;
    }

    public void setTargetDirectory(String directory) {
        this.setTargetDirectorySafe(directory);
    }

    private void setTargetDirectorySafe(String directory) {
        this.targetDirectory = directory != null && directory.length() > 0 ? new File(directory) : null;
    }

    public void setTargetDirectory(File directory) {
        this.targetDirectory = directory;
    }

    public List<String> getClasspath() {
        return this.classpath;
    }

    public void setClasspath(String classpath) {
        this.classpath = new LinkedList<String>();
        StringTokenizer tokenizer = new StringTokenizer(classpath, File.pathSeparator);
        while (tokenizer.hasMoreTokens()) {
            this.classpath.add(tokenizer.nextToken());
        }
    }

    public void setClasspathList(List<String> parts) {
        this.classpath = new LinkedList<String>(parts);
    }

    public boolean getVerbose() {
        return this.verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public boolean getDebug() {
        return this.debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean getParameters() {
        return this.parameters;
    }

    public void setParameters(boolean parameters) {
        this.parameters = parameters;
    }

    public int getTolerance() {
        return this.tolerance;
    }

    public void setTolerance(int tolerance) {
        this.tolerance = tolerance;
    }

    public String getScriptBaseClass() {
        return this.scriptBaseClass;
    }

    public void setScriptBaseClass(String scriptBaseClass) {
        this.scriptBaseClass = scriptBaseClass;
    }

    public ParserPluginFactory getPluginFactory() {
        if (this.pluginFactory == null) {
            this.pluginFactory = ParserPluginFactory.antlr4();
        }
        return this.pluginFactory;
    }

    public void setPluginFactory(ParserPluginFactory pluginFactory) {
        this.pluginFactory = pluginFactory;
    }

    public void setScriptExtensions(Set<String> scriptExtensions) {
        this.scriptExtensions = Optional.ofNullable(scriptExtensions).orElseGet(LinkedHashSet::new);
    }

    public Set<String> getScriptExtensions() {
        if (this.scriptExtensions == null || this.scriptExtensions.isEmpty()) {
            this.scriptExtensions = SourceExtensionHandler.getRegisteredExtensions(this.getClass().getClassLoader());
        }
        return this.scriptExtensions;
    }

    public String getDefaultScriptExtension() {
        return this.defaultScriptExtension;
    }

    public void setDefaultScriptExtension(String defaultScriptExtension) {
        this.defaultScriptExtension = defaultScriptExtension;
    }

    public boolean getRecompileGroovySource() {
        return this.recompileGroovySource;
    }

    public void setRecompileGroovySource(boolean recompile) {
        this.recompileGroovySource = recompile;
    }

    public int getMinimumRecompilationInterval() {
        return this.minimumRecompilationInterval;
    }

    public void setMinimumRecompilationInterval(int time) {
        this.minimumRecompilationInterval = Math.max(0, time);
    }

    public void setTargetBytecode(String version) {
        this.setTargetBytecodeIfValid(version);
    }

    private void setTargetBytecodeIfValid(String version) {
        if (JDK_TO_BYTECODE_VERSION_MAP.containsKey(version)) {
            this.targetBytecode = version;
        }
    }

    public String getTargetBytecode() {
        return this.targetBytecode;
    }

    public int getBytecodeVersion() {
        Integer bytecodeVersion = JDK_TO_BYTECODE_VERSION_MAP.get(this.targetBytecode);
        if (bytecodeVersion == null) {
            throw new GroovyBugError("Bytecode version [" + this.targetBytecode + "] is not supported by the compiler");
        }
        if (bytecodeVersion <= 52) {
            return 52;
        }
        if (this.previewFeatures) {
            return bytecodeVersion | 0xFFFF0000;
        }
        return bytecodeVersion;
    }

    private static String defaultTargetBytecode() {
        String javaVersion = VMPlugin.getJavaVersion();
        if (JDK_TO_BYTECODE_VERSION_MAP.containsKey(javaVersion)) {
            return javaVersion;
        }
        return JDK8;
    }

    public boolean isPreviewFeatures() {
        return this.previewFeatures;
    }

    public void setPreviewFeatures(boolean previewFeatures) {
        this.previewFeatures = previewFeatures;
    }

    public boolean isLogClassgen() {
        return this.logClassgen;
    }

    public void setLogClassgen(boolean logClassgen) {
        this.logClassgen = logClassgen;
    }

    public int getLogClassgenStackTraceMaxDepth() {
        return this.logClassgenStackTraceMaxDepth;
    }

    public void setLogClassgenStackTraceMaxDepth(int logClassgenStackTraceMaxDepth) {
        this.logClassgenStackTraceMaxDepth = logClassgenStackTraceMaxDepth;
    }

    public Map<String, Object> getJointCompilationOptions() {
        return this.jointCompilationOptions;
    }

    public void setJointCompilationOptions(Map<String, Object> options) {
        this.jointCompilationOptions = options;
    }

    public Map<String, Boolean> getOptimizationOptions() {
        return this.optimizationOptions;
    }

    public void setOptimizationOptions(Map<String, Boolean> options) {
        if (options == null) {
            throw new IllegalArgumentException("provided option map must not be null");
        }
        this.optimizationOptions = options;
    }

    public CompilerConfiguration addCompilationCustomizers(CompilationCustomizer ... customizers) {
        if (customizers == null) {
            throw new IllegalArgumentException("provided customizers list must not be null");
        }
        Collections.addAll(this.compilationCustomizers, customizers);
        return this;
    }

    public List<CompilationCustomizer> getCompilationCustomizers() {
        return this.compilationCustomizers;
    }

    public Set<String> getDisabledGlobalASTTransformations() {
        return this.disabledGlobalASTTransformations;
    }

    public void setDisabledGlobalASTTransformations(Set<String> disabledGlobalASTTransformations) {
        this.disabledGlobalASTTransformations = disabledGlobalASTTransformations;
    }

    public BytecodeProcessor getBytecodePostprocessor() {
        return this.bytecodePostprocessor;
    }

    public void setBytecodePostprocessor(BytecodeProcessor bytecodePostprocessor) {
        this.bytecodePostprocessor = bytecodePostprocessor;
    }

    public boolean isIndyEnabled() {
        Boolean indyEnabled = this.getOptimizationOptions().get(INVOKEDYNAMIC);
        return Optional.ofNullable(indyEnabled).orElse(Boolean.TRUE);
    }

    public boolean isGroovydocEnabled() {
        Boolean groovydocEnabled = this.getOptimizationOptions().get(GROOVYDOC);
        return Optional.ofNullable(groovydocEnabled).orElse(Boolean.FALSE);
    }

    public boolean isRuntimeGroovydocEnabled() {
        Boolean runtimeGroovydocEnabled = this.getOptimizationOptions().get(RUNTIME_GROOVYDOC);
        return Optional.ofNullable(runtimeGroovydocEnabled).orElse(Boolean.FALSE);
    }
}

