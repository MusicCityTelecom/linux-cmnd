/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  groovy.lang.Binding
 *  groovy.lang.GroovyClassLoader
 *  groovy.lang.GroovyObject
 *  groovy.lang.GroovyShell
 *  groovy.lang.Script
 *  lombok.Generated
 *  org.apache.commons.io.IOUtils
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.codehaus.groovy.control.CompilerConfiguration
 *  org.codehaus.groovy.runtime.InvokerInvocationException
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.io.AbstractResource
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.util.scripting;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;
import lombok.Generated;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.util.RegexUtils;
import org.apereo.cas.util.ResourceUtils;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.runtime.InvokerInvocationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;

public final class ScriptingUtils {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptingUtils.class);
    private static final String INLINE_PATTERN = "%s\\s*\\{\\s*(.+)\\s*\\}";
    private static final String FILE_PATTERN = "(file|classpath):(.+\\.%s)";
    private static final Pattern INLINE_GROOVY_PATTERN = RegexUtils.createPattern(String.format("%s\\s*\\{\\s*(.+)\\s*\\}", "groovy"), 40);
    private static final Pattern FILE_GROOVY_PATTERN = RegexUtils.createPattern(String.format("(file|classpath):(.+\\.%s)", "groovy"));

    public static boolean isInlineGroovyScript(String script) {
        return ScriptingUtils.getMatcherForInlineGroovyScript(script).find();
    }

    public static boolean isExternalGroovyScript(String script) {
        return ScriptingUtils.getMatcherForExternalGroovyScript(script).find();
    }

    public static Matcher getMatcherForInlineGroovyScript(String script) {
        return INLINE_GROOVY_PATTERN.matcher(script);
    }

    public static Matcher getMatcherForExternalGroovyScript(String script) {
        return FILE_GROOVY_PATTERN.matcher(script);
    }

    public static <T> T executeGroovyShellScript(Script script, Class<T> clazz) {
        return ScriptingUtils.executeGroovyShellScript(script, new HashMap<String, Object>(0), clazz);
    }

    public static <T> T executeGroovyShellScript(Script script, Map<String, Object> variables, Class<T> clazz) {
        try {
            Binding binding = script.getBinding();
            if (!binding.hasVariable("logger")) {
                binding.setVariable("logger", (Object)LOGGER);
            }
            if (variables != null && !variables.isEmpty()) {
                variables.forEach((arg_0, arg_1) -> ((Binding)binding).setVariable(arg_0, arg_1));
            }
            script.setBinding(binding);
            LOGGER.debug("Executing groovy script [{}] with variables [{}]", (Object)script, (Object)binding.getVariables());
            Object result = script.run();
            return ScriptingUtils.getGroovyScriptExecutionResultOrThrow(clazz, result);
        }
        catch (Exception e) {
            LoggingUtils.error(LOGGER, e);
            return null;
        }
    }

    public static <T> T executeGroovyScript(Resource groovyScript, Object[] args, Class<T> clazz, boolean failOnError) {
        return ScriptingUtils.executeGroovyScript(groovyScript, "run", args, clazz, failOnError);
    }

    public static <T> T executeGroovyScript(GroovyObject groovyObject, Object[] args, Class<T> clazz, boolean failOnError) {
        return ScriptingUtils.executeGroovyScript(groovyObject, "run", args, clazz, failOnError);
    }

    public static <T> T executeGroovyScript(Resource groovyScript, String methodName, Class<T> clazz, Object ... args) {
        return ScriptingUtils.executeGroovyScript(groovyScript, methodName, args, clazz, false);
    }

    public static <T> T executeGroovyScript(Resource groovyScript, String methodName, Class<T> clazz) {
        return ScriptingUtils.executeGroovyScript(groovyScript, methodName, ArrayUtils.EMPTY_OBJECT_ARRAY, clazz, false);
    }

    public static <T> T executeGroovyScript(Resource groovyScript, String methodName, Object[] args, Class<T> clazz, boolean failOnError) {
        if (groovyScript == null || StringUtils.isBlank((CharSequence)methodName)) {
            return null;
        }
        try {
            return ScriptingUtils.getGroovyResult(groovyScript, methodName, args, clazz, failOnError);
        }
        catch (Exception e) {
            if (failOnError) {
                throw e;
            }
            LoggingUtils.error(LOGGER, e);
            return null;
        }
    }

    public static <T> T executeGroovyScript(GroovyObject groovyObject, String methodName, Object[] args, Class<T> clazz, boolean failOnError) {
        try {
            LOGGER.trace("Executing groovy script's [{}] method, with parameters [{}]", (Object)methodName, (Object)args);
            Object result = groovyObject.invokeMethod(methodName, (Object)args);
            LOGGER.trace("Results returned by the groovy script are [{}]", result);
            if (!clazz.equals(Void.class)) {
                return ScriptingUtils.getGroovyScriptExecutionResultOrThrow(clazz, result);
            }
        }
        catch (Exception e) {
            Throwable cause;
            Throwable throwable = cause = e instanceof InvokerInvocationException ? e.getCause() : e;
            if (failOnError) {
                throw cause;
            }
            LOGGER.error(cause.getMessage(), cause);
        }
        return null;
    }

    public static Script parseGroovyShellScript(String script) {
        try {
            GroovyShell shell = new GroovyShell();
            LOGGER.debug("Parsing groovy script [{}]", (Object)script);
            return shell.parse(script);
        }
        catch (Exception e) {
            LoggingUtils.error(LOGGER, e);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static GroovyObject parseGroovyScript(Resource groovyScript, boolean failOnError) {
        ClassLoader parent = ScriptingUtils.class.getClassLoader();
        try (GroovyClassLoader loader = new GroovyClassLoader(parent);){
            Class groovyClass = ScriptingUtils.loadGroovyClass(groovyScript, loader);
            if (groovyClass != null) {
                LOGGER.trace("Creating groovy object instance from class [{}]", (Object)groovyScript.getURI().getPath());
                GroovyObject groovyObject = (GroovyObject)groovyClass.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                return groovyObject;
            }
            LOGGER.warn("Groovy script at [{}] does not exist", (Object)groovyScript.getURI().getPath());
            return null;
        }
        catch (Exception e) {
            if (failOnError) {
                throw new RuntimeException(e);
            }
            LoggingUtils.error(LOGGER, e);
        }
        return null;
    }

    private static Class loadGroovyClass(Resource groovyScript, GroovyClassLoader loader) throws IOException {
        if (ResourceUtils.isJarResource(groovyScript)) {
            try (BufferedReader groovyReader = new BufferedReader(new InputStreamReader(groovyScript.getInputStream(), StandardCharsets.UTF_8));){
                Class clazz = loader.parseClass((Reader)groovyReader, groovyScript.getFilename());
                return clazz;
            }
        }
        File groovyFile = groovyScript.getFile();
        if (groovyFile.exists()) {
            return loader.parseClass(groovyFile);
        }
        return null;
    }

    private static <T> T getGroovyResult(Resource groovyScript, String methodName, Object[] args, Class<T> clazz, boolean failOnError) {
        try {
            GroovyObject groovyObject = ScriptingUtils.parseGroovyScript(groovyScript, failOnError);
            if (groovyObject == null) {
                LOGGER.error("Could not parse the Groovy script at [{}]", (Object)groovyScript);
                return null;
            }
            return ScriptingUtils.executeGroovyScript(groovyObject, methodName, args, clazz, failOnError);
        }
        catch (Exception e) {
            if (failOnError) {
                throw e;
            }
            LoggingUtils.error(LOGGER, e);
            return null;
        }
    }

    private static <T> T getGroovyScriptExecutionResultOrThrow(Class<T> clazz, Object result) {
        if (result != null && !clazz.isAssignableFrom(result.getClass())) {
            throw new ClassCastException("Result [" + result + " is of type " + result.getClass() + " when we were expecting " + clazz);
        }
        return (T)result;
    }

    public static <T> T executeScriptEngine(String scriptFile, Object[] args, Class<T> clazz) {
        try {
            String engineName = ScriptingUtils.getScriptEngineName(scriptFile);
            if (StringUtils.isBlank((CharSequence)engineName)) {
                LOGGER.warn("Script engine name can not be determined for [{}]", (Object)engineName);
                return null;
            }
            ScriptEngine engine = new ScriptEngineManager().getEngineByName(engineName);
            AbstractResource resourceFrom = ResourceUtils.getResourceFrom(scriptFile);
            File theScriptFile = resourceFrom.getFile();
            if (theScriptFile.exists()) {
                LOGGER.debug("Created object instance from class [{}]", (Object)theScriptFile.getCanonicalPath());
                try (BufferedReader reader = Files.newBufferedReader(theScriptFile.toPath(), StandardCharsets.UTF_8);){
                    engine.eval(reader);
                }
                Invocable invocable = (Invocable)((Object)engine);
                LOGGER.debug("Executing script's run method, with parameters [{}]", args);
                Object result = invocable.invokeFunction("run", args);
                LOGGER.debug("Groovy script result is [{}]", result);
                return ScriptingUtils.getGroovyScriptExecutionResultOrThrow(clazz, result);
            }
            LOGGER.warn("[{}] script [{}] does not exist, or cannot be loaded", (Object)StringUtils.capitalize((String)engineName), (Object)scriptFile);
        }
        catch (Exception e) {
            LoggingUtils.error(LOGGER, e);
        }
        return null;
    }

    public static <T> T executeGroovyScriptEngine(String script, Map<String, Object> variables, Class<T> clazz) {
        try {
            ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");
            SimpleBindings binding = new SimpleBindings();
            if (variables != null && !variables.isEmpty()) {
                binding.putAll((Map<? extends String, ? extends Object>)variables);
            }
            if (!binding.containsKey("logger")) {
                binding.put("logger", (Object)LOGGER);
            }
            Object result = engine.eval(script, (Bindings)binding);
            return ScriptingUtils.getGroovyScriptExecutionResultOrThrow(clazz, result);
        }
        catch (Exception e) {
            LoggingUtils.error(LOGGER, e);
            return null;
        }
    }

    public static <T> T getObjectInstanceFromGroovyResource(Resource resource, Class<T> expectedType) {
        return ScriptingUtils.getObjectInstanceFromGroovyResource(resource, ArrayUtils.EMPTY_CLASS_ARRAY, ArrayUtils.EMPTY_OBJECT_ARRAY, expectedType);
    }

    public static <T> T getObjectInstanceFromGroovyResource(Resource resource, Class[] constructorArgs, Object[] args, Class<T> expectedType) {
        try {
            if (resource == null) {
                LOGGER.debug("No groovy script is defined");
                return null;
            }
            String script = IOUtils.toString((InputStream)resource.getInputStream(), (Charset)StandardCharsets.UTF_8);
            GroovyClassLoader classLoader = new GroovyClassLoader(ScriptingUtils.class.getClassLoader(), new CompilerConfiguration(), true);
            Class clazz = classLoader.parseClass(script);
            LOGGER.trace("Preparing constructor arguments [{}] for resource [{}]", (Object)args, (Object)resource);
            Constructor ctor = clazz.getDeclaredConstructor(constructorArgs);
            Object result = ctor.newInstance(args);
            if (!expectedType.isAssignableFrom(result.getClass())) {
                throw new ClassCastException("Result [" + result + " is of type " + result.getClass() + " when we were expecting " + expectedType);
            }
            return result;
        }
        catch (Exception e) {
            LoggingUtils.error(LOGGER, e);
            return null;
        }
    }

    public static String getScriptEngineName(String scriptFile) {
        if (scriptFile.endsWith(".py")) {
            return "python";
        }
        if (scriptFile.endsWith(".js")) {
            return "js";
        }
        if (scriptFile.endsWith(".groovy")) {
            return "groovy";
        }
        return null;
    }

    @Generated
    private ScriptingUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}

