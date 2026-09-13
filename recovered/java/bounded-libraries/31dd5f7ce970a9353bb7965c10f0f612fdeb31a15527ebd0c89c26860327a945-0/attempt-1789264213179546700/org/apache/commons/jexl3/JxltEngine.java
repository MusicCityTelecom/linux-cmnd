/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.jexl3;

import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.JexlInfo;

public abstract class JxltEngine {
    public Expression createExpression(String expression) {
        return this.createExpression(null, expression);
    }

    public abstract Expression createExpression(JexlInfo var1, String var2);

    public abstract Template createTemplate(JexlInfo var1, String var2, Reader var3, String ... var4);

    public Template createTemplate(JexlInfo info, String source, String ... parms) {
        return this.createTemplate(info, "$$", new StringReader(source), parms);
    }

    public Template createTemplate(JexlInfo info, String source) {
        return this.createTemplate(info, "$$", new StringReader(source), (String[])null);
    }

    public Template createTemplate(String prefix, Reader source, String ... parms) {
        return this.createTemplate(null, prefix, source, parms);
    }

    public Template createTemplate(String source, String ... parms) {
        return this.createTemplate((JexlInfo)null, source, parms);
    }

    public Template createTemplate(String source) {
        return this.createTemplate((JexlInfo)null, source);
    }

    public abstract JexlEngine getEngine();

    public abstract void clearCache();

    public static interface Template {
        public String asString();

        public void evaluate(JexlContext var1, Writer var2);

        public void evaluate(JexlContext var1, Writer var2, Object ... var3);

        public Template prepare(JexlContext var1);

        public Set<List<String>> getVariables();

        public String[] getParameters();

        public Map<String, Object> getPragmas();
    }

    public static interface Expression {
        public String asString();

        public StringBuilder asString(StringBuilder var1);

        public Object evaluate(JexlContext var1);

        public Expression getSource();

        public Set<List<String>> getVariables();

        public boolean isDeferred();

        public boolean isImmediate();

        public Expression prepare(JexlContext var1);

        public String toString();
    }

    public static class Exception
    extends JexlException {
        private static final long serialVersionUID = 201112030113L;

        public Exception(JexlInfo info, String msg, Throwable cause) {
            super(info, msg, cause);
        }
    }
}

