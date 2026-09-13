/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.scripting;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import org.apache.commons.jexl3.parser.StringParser;
import org.apache.commons.jexl3.scripting.JexlScriptEngine;

public class JexlScriptEngineFactory
implements ScriptEngineFactory {
    @Override
    public String getEngineName() {
        return "JEXL Engine";
    }

    @Override
    public String getEngineVersion() {
        return "3.2";
    }

    @Override
    public String getLanguageName() {
        return "JEXL";
    }

    @Override
    public String getLanguageVersion() {
        return "3.2";
    }

    @Override
    public String getMethodCallSyntax(String obj, String m, String ... args) {
        StringBuilder sb = new StringBuilder();
        sb.append(obj);
        sb.append('.');
        sb.append(m);
        sb.append('(');
        boolean needComma = false;
        for (String arg : args) {
            if (needComma) {
                sb.append(',');
            }
            sb.append(arg);
            needComma = true;
        }
        sb.append(')');
        return sb.toString();
    }

    @Override
    public List<String> getExtensions() {
        return Collections.unmodifiableList(Arrays.asList("jexl", "jexl2", "jexl3"));
    }

    @Override
    public List<String> getMimeTypes() {
        return Collections.unmodifiableList(Arrays.asList("application/x-jexl", "application/x-jexl2", "application/x-jexl3"));
    }

    @Override
    public List<String> getNames() {
        return Collections.unmodifiableList(Arrays.asList("JEXL", "Jexl", "jexl", "JEXL2", "Jexl2", "jexl2", "JEXL3", "Jexl3", "jexl3"));
    }

    @Override
    public String getOutputStatement(String toDisplay) {
        if (toDisplay == null) {
            return "JEXL.out.print(null)";
        }
        return "JEXL.out.print(" + StringParser.escapeString(toDisplay, '\'') + ")";
    }

    @Override
    public Object getParameter(String key) {
        switch (key) {
            case "javax.script.engine": {
                return this.getEngineName();
            }
            case "javax.script.engine_version": {
                return this.getEngineVersion();
            }
            case "javax.script.name": {
                return this.getNames();
            }
            case "javax.script.language": {
                return this.getLanguageName();
            }
            case "javax.script.language_version": {
                return this.getLanguageVersion();
            }
        }
        return null;
    }

    @Override
    public String getProgram(String ... statements) {
        StringBuilder sb = new StringBuilder();
        for (String statement : statements) {
            sb.append(statement.trim());
            if (statement.endsWith(";")) continue;
            sb.append(';');
        }
        return sb.toString();
    }

    @Override
    public ScriptEngine getScriptEngine() {
        return new JexlScriptEngine(this);
    }
}

