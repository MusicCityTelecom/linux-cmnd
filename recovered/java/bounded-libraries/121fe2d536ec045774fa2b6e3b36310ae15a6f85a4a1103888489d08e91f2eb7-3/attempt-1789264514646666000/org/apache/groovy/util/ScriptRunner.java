/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.util;

import groovy.lang.GroovyRuntimeException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import org.codehaus.groovy.tools.GroovyStarter;

public class ScriptRunner {
    public static void runScript(File path) {
        try {
            GroovyStarter.main(new String[]{"--main", "groovy.ui.GroovyMain", path.getCanonicalPath()});
        }
        catch (IOException e) {
            throw new GroovyRuntimeException("Failed to run script: " + path, e);
        }
    }

    public static void runScript(String cp) {
        try {
            ScriptRunner.runScript(new File(ScriptRunner.class.getResource(cp).toURI()));
        }
        catch (URISyntaxException e) {
            throw new GroovyRuntimeException("Failed to run script: " + cp, e);
        }
    }

    private ScriptRunner() {
    }
}

