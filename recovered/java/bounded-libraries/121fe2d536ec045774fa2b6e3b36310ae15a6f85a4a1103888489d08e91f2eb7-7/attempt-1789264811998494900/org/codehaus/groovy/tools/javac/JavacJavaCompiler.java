/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.tools.javac;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.security.AccessController;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import org.apache.groovy.io.StringBuilderWriter;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.messages.ExceptionMessage;
import org.codehaus.groovy.control.messages.SimpleMessage;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.tools.javac.JavaCompiler;

public class JavacJavaCompiler
implements JavaCompiler {
    private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;
    private final CompilerConfiguration config;
    private final Charset charset;

    public JavacJavaCompiler(CompilerConfiguration config) {
        this.config = config;
        this.charset = Charset.forName(config.getSourceEncoding());
    }

    @Override
    public void compile(List<String> files, CompilationUnit cu) {
        List<String> javacParameters = this.makeParameters(cu.getClassLoader());
        StringBuilderWriter javacOutput = new StringBuilderWriter();
        int javacReturnValue = 0;
        try {
            try {
                boolean successful = this.doCompileWithSystemJavaCompiler(cu, files, javacParameters, javacOutput);
                if (!successful) {
                    javacReturnValue = 1;
                }
            }
            catch (IllegalArgumentException e) {
                javacReturnValue = 2;
                cu.getErrorCollector().addFatalError(new ExceptionMessage(e, true, cu));
            }
            catch (IOException e) {
                javacReturnValue = 1;
                cu.getErrorCollector().addFatalError(new ExceptionMessage(e, true, cu));
            }
        }
        catch (Exception e) {
            cu.getErrorCollector().addFatalError(new ExceptionMessage(e, true, cu));
        }
        if (javacReturnValue != 0) {
            switch (javacReturnValue) {
                case 1: {
                    JavacJavaCompiler.addJavacError("Compile error during compilation with javac.", cu, javacOutput);
                    break;
                }
                case 2: {
                    JavacJavaCompiler.addJavacError("Invalid commandline usage for javac.", cu, javacOutput);
                    break;
                }
                default: {
                    JavacJavaCompiler.addJavacError("unexpected return value by javac.", cu, javacOutput);
                    break;
                }
            }
        } else {
            System.out.print(javacOutput);
        }
    }

    private boolean doCompileWithSystemJavaCompiler(CompilationUnit cu, List<String> files, List<String> javacParameters, StringBuilderWriter javacOutput) throws IOException {
        javax.tools.JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, DEFAULT_LOCALE, this.charset);){
            Set<JavaFileObject> compilationUnitSet = cu.getJavaCompilationUnitSet();
            Map<String, Object> options = this.config.getJointCompilationOptions();
            if (!Boolean.TRUE.equals(options.get("memStub"))) {
                compilationUnitSet = new HashSet<JavaFileObject>();
                javacParameters.add("-sourcepath");
                File stubDir = (File)options.get("stubDir");
                if (null == stubDir) {
                    throw new GroovyBugError("stubDir is not specified");
                }
                javacParameters.add(stubDir.getAbsolutePath());
            }
            fileManager.getJavaFileObjectsFromFiles(files.stream().map(File::new).collect(Collectors.toList())).forEach(compilationUnitSet::add);
            JavaCompiler.CompilationTask compilationTask = compiler.getTask(javacOutput, fileManager, null, javacParameters, Collections.emptyList(), compilationUnitSet);
            compilationTask.setLocale(DEFAULT_LOCALE);
            boolean bl = compilationTask.call();
            return bl;
        }
    }

    private static void addJavacError(String header, CompilationUnit cu, StringBuilderWriter msg) {
        header = msg != null ? header + "\n" + msg.getBuilder().toString() : header + "\nThis javac version does not support compile(String[],PrintWriter), so no further details of the error are available. The message error text should be found on System.err.\n";
        cu.getErrorCollector().addFatalError(new SimpleMessage(header, cu));
    }

    private List<String> makeParameters(GroovyClassLoader parentClassLoader) {
        Map<String, Object> options = this.config.getJointCompilationOptions();
        ArrayList<String> params = new ArrayList<String>();
        File target = this.config.getTargetDirectory();
        if (target == null) {
            target = new File(".");
        }
        params.add("-d");
        params.add(target.getAbsolutePath());
        String[] flags = (String[])options.get("flags");
        if (flags != null) {
            for (String flag : flags) {
                params.add("-" + flag);
            }
        }
        boolean hadClasspath = false;
        String[] namedValues = (String[])options.get("namedValues");
        if (namedValues != null) {
            int n = namedValues.length;
            for (int i = 0; i < n; i += 2) {
                URL[] name = namedValues[i];
                if (name.equals("classpath")) {
                    hadClasspath = true;
                }
                params.add("-" + (String)name);
                params.add(namedValues[i + 1]);
            }
        }
        if (!hadClasspath) {
            ArrayList<String> paths = new ArrayList<String>(this.config.getClasspath());
            for (ClassLoader loader = parentClassLoader; loader != null; loader = loader.getParent()) {
                if (!(loader instanceof URLClassLoader)) continue;
                for (URL u : ((URLClassLoader)loader).getURLs()) {
                    try {
                        paths.add(new File(u.toURI()).getPath());
                    }
                    catch (URISyntaxException uRISyntaxException) {
                        // empty catch block
                    }
                }
            }
            try {
                CodeSource codeSource = this.getCodeSource();
                if (codeSource != null) {
                    paths.add(new File(codeSource.getLocation().toURI()).getPath());
                }
            }
            catch (URISyntaxException uRISyntaxException) {
                // empty catch block
            }
            params.add("-classpath");
            params.add(DefaultGroovyMethods.join(paths, File.pathSeparator));
        }
        return params;
    }

    private CodeSource getCodeSource() {
        return AccessController.doPrivileged(() -> GroovyObject.class.getProtectionDomain().getCodeSource());
    }
}

