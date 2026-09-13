/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.stringtemplate.v4.ST
 *  org.stringtemplate.v4.STGroup
 *  org.stringtemplate.v4.STGroupFile
 *  org.stringtemplate.v4.misc.ErrorBuffer
 */
package groovyjarjarantlr4.v4.tool;

import groovyjarjarantlr4.runtime.RecognitionException;
import groovyjarjarantlr4.runtime.Token;
import groovyjarjarantlr4.v4.Tool;
import groovyjarjarantlr4.v4.tool.ANTLRMessage;
import groovyjarjarantlr4.v4.tool.ErrorSeverity;
import groovyjarjarantlr4.v4.tool.ErrorType;
import groovyjarjarantlr4.v4.tool.GrammarSemanticsMessage;
import groovyjarjarantlr4.v4.tool.GrammarSyntaxMessage;
import groovyjarjarantlr4.v4.tool.LeftRecursionCyclesMessage;
import groovyjarjarantlr4.v4.tool.Rule;
import groovyjarjarantlr4.v4.tool.ToolMessage;
import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.misc.ErrorBuffer;

public class ErrorManager {
    public static final String FORMATS_DIR = "groovyjarjarantlr4/v4/tool/templates/messages/formats/";
    public Tool tool;
    public int errors;
    public int warnings;
    public Set<ErrorType> errorTypes = EnumSet.noneOf(ErrorType.class);
    STGroup format;
    String formatName;
    ErrorBuffer initSTListener = new ErrorBuffer();

    public ErrorManager(Tool tool) {
        this.tool = tool;
    }

    public void resetErrorState() {
        this.errors = 0;
        this.warnings = 0;
    }

    public ST getMessageTemplate(ANTLRMessage msg) {
        ST messageST = msg.getMessageTemplate(this.tool.longMessages);
        ST locationST = this.getLocationFormat();
        ST reportST = this.getReportFormat(msg.getErrorType().severity);
        ST messageFormatST = this.getMessageFormat();
        boolean locationValid = false;
        if (msg.line != -1) {
            locationST.add("line", (Object)msg.line);
            locationValid = true;
        }
        if (msg.charPosition != -1) {
            locationST.add("column", (Object)msg.charPosition);
            locationValid = true;
        }
        if (msg.fileName != null) {
            File f;
            String displayFileName = msg.fileName;
            if (this.format.equals("antlr") && (f = new File(msg.fileName)).exists()) {
                displayFileName = f.getName();
            }
            locationST.add("file", (Object)displayFileName);
            locationValid = true;
        }
        messageFormatST.add("id", (Object)msg.getErrorType().code);
        messageFormatST.add("text", (Object)messageST);
        if (locationValid) {
            reportST.add("location", (Object)locationST);
        }
        reportST.add("message", (Object)messageFormatST);
        return reportST;
    }

    public ST getLocationFormat() {
        return this.format.getInstanceOf("location");
    }

    public ST getReportFormat(ErrorSeverity severity) {
        ST st = this.format.getInstanceOf("report");
        st.add("type", (Object)severity.getText());
        return st;
    }

    public ST getMessageFormat() {
        return this.format.getInstanceOf("message");
    }

    public boolean formatWantsSingleLineMessage() {
        return this.format.getInstanceOf("wantsSingleLineMessage").render().equals("true");
    }

    public void info(String msg) {
        this.tool.info(msg);
    }

    public void syntaxError(ErrorType etype, String fileName, Token token, RecognitionException antlrException, Object ... args) {
        GrammarSyntaxMessage msg = new GrammarSyntaxMessage(etype, fileName, token, antlrException, args);
        this.emit(etype, msg);
    }

    public static void fatalInternalError(String error, Throwable e) {
        ErrorManager.internalError(error, e);
        throw new RuntimeException(error, e);
    }

    public static void internalError(String error, Throwable e) {
        StackTraceElement location = ErrorManager.getLastNonErrorManagerCodeLocation(e);
        ErrorManager.internalError("Exception " + e + "@" + location + ": " + error);
    }

    public static void internalError(String error) {
        StackTraceElement location = ErrorManager.getLastNonErrorManagerCodeLocation(new Exception());
        String msg = location + ": " + error;
        System.err.println("internal error: " + msg);
    }

    public void toolError(ErrorType errorType, Object ... args) {
        this.toolError(errorType, null, args);
    }

    public void toolError(ErrorType errorType, Throwable e, Object ... args) {
        ToolMessage msg = new ToolMessage(errorType, e, args);
        this.emit(errorType, msg);
    }

    public void grammarError(ErrorType etype, String fileName, Token token, Object ... args) {
        GrammarSemanticsMessage msg = new GrammarSemanticsMessage(etype, fileName, token, args);
        this.emit(etype, msg);
    }

    public void leftRecursionCycles(String fileName, Collection<? extends Collection<Rule>> cycles) {
        ++this.errors;
        LeftRecursionCyclesMessage msg = new LeftRecursionCyclesMessage(fileName, cycles);
        this.tool.error(msg);
    }

    public int getNumErrors() {
        return this.errors;
    }

    private static StackTraceElement getLastNonErrorManagerCodeLocation(Throwable e) {
        StackTraceElement t;
        int i;
        StackTraceElement[] stack = e.getStackTrace();
        for (i = 0; i < stack.length && (t = stack[i]).toString().contains("ErrorManager"); ++i) {
        }
        StackTraceElement location = stack[i];
        return location;
    }

    public void emit(ErrorType etype, ANTLRMessage msg) {
        switch (etype.severity) {
            case WARNING_ONE_OFF: {
                if (this.errorTypes.contains((Object)etype)) break;
            }
            case WARNING: {
                ++this.warnings;
                this.tool.warning(msg);
                break;
            }
            case ERROR_ONE_OFF: {
                if (this.errorTypes.contains((Object)etype)) break;
            }
            case ERROR: {
                ++this.errors;
                this.tool.error(msg);
            }
        }
        this.errorTypes.add(etype);
    }

    public void setFormat(String formatName) {
        boolean formatOK;
        this.formatName = formatName;
        String fileName = FORMATS_DIR + formatName + STGroup.GROUP_FILE_EXTENSION;
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        URL url = cl.getResource(fileName);
        if (url == null) {
            cl = ErrorManager.class.getClassLoader();
            url = cl.getResource(fileName);
        }
        if (url == null && formatName.equals("antlr")) {
            ErrorManager.rawError("ANTLR installation corrupted; cannot find ANTLR messages format file " + fileName);
            ErrorManager.panic();
        } else if (url == null) {
            ErrorManager.rawError("no such message format file " + fileName + " retrying with default ANTLR format");
            this.setFormat("antlr");
            return;
        }
        this.format = new STGroupFile(url, "UTF-8", '<', '>');
        this.format.load();
        if (!this.initSTListener.errors.isEmpty()) {
            ErrorManager.rawError("ANTLR installation corrupted; can't load messages format file:\n" + this.initSTListener.toString());
            ErrorManager.panic();
        }
        if (!(formatOK = this.verifyFormat()) && formatName.equals("antlr")) {
            ErrorManager.rawError("ANTLR installation corrupted; ANTLR messages format file " + formatName + ".stg incomplete");
            ErrorManager.panic();
        } else if (!formatOK) {
            this.setFormat("antlr");
        }
    }

    protected boolean verifyFormat() {
        boolean ok = true;
        if (!this.format.isDefined("location")) {
            System.err.println("Format template 'location' not found in " + this.formatName);
            ok = false;
        }
        if (!this.format.isDefined("message")) {
            System.err.println("Format template 'message' not found in " + this.formatName);
            ok = false;
        }
        if (!this.format.isDefined("report")) {
            System.err.println("Format template 'report' not found in " + this.formatName);
            ok = false;
        }
        return ok;
    }

    static void rawError(String msg) {
        System.err.println(msg);
    }

    static void rawError(String msg, Throwable e) {
        ErrorManager.rawError(msg);
        e.printStackTrace(System.err);
    }

    public void panic(ErrorType errorType, Object ... args) {
        ToolMessage msg = new ToolMessage(errorType, args);
        ST msgST = this.getMessageTemplate(msg);
        String outputMsg = msgST.render();
        if (this.formatWantsSingleLineMessage()) {
            outputMsg = outputMsg.replace('\n', ' ');
        }
        ErrorManager.panic(outputMsg);
    }

    public static void panic(String msg) {
        ErrorManager.rawError(msg);
        ErrorManager.panic();
    }

    public static void panic() {
        throw new Error("ANTLR ErrorManager panic");
    }
}

