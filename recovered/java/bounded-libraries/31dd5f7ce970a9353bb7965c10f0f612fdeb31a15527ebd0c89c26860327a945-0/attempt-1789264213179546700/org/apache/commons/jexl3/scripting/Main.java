/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3.scripting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import org.apache.commons.jexl3.scripting.JexlScriptEngineFactory;

public class Main {
    static BufferedReader read(Charset charset, String fileName) throws Exception {
        return new BufferedReader(new InputStreamReader(fileName == null ? System.in : new FileInputStream(new File(fileName)), charset == null ? Charset.defaultCharset() : charset));
    }

    public static void main(String[] args) throws Exception {
        JexlScriptEngineFactory fac = new JexlScriptEngineFactory();
        ScriptEngine engine = fac.getScriptEngine();
        engine.put("args", args);
        if (args.length == 1) {
            Object value = engine.eval(Main.read(null, args[0]));
            System.out.println("Return value: " + value);
        } else {
            String line;
            BufferedReader console = Main.read(null, null);
            System.out.print("> ");
            while (null != (line = console.readLine())) {
                try {
                    Object value = engine.eval(line);
                    System.out.println("Return value: " + value);
                }
                catch (ScriptException e) {
                    System.out.println(e.getLocalizedMessage());
                }
                System.out.print("> ");
            }
        }
    }
}

