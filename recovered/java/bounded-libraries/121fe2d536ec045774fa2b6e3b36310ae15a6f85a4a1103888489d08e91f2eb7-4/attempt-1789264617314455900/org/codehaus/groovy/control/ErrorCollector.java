/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.control;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.Janitor;
import org.codehaus.groovy.control.MultipleCompilationErrorsException;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.control.messages.ExceptionMessage;
import org.codehaus.groovy.control.messages.LocatedMessage;
import org.codehaus.groovy.control.messages.Message;
import org.codehaus.groovy.control.messages.SyntaxErrorMessage;
import org.codehaus.groovy.control.messages.WarningMessage;
import org.codehaus.groovy.syntax.CSTNode;
import org.codehaus.groovy.syntax.SyntaxException;

public class ErrorCollector
implements Serializable {
    private static final long serialVersionUID = 2844774170905056755L;
    protected LinkedList<Message> errors;
    protected LinkedList<WarningMessage> warnings;
    protected final CompilerConfiguration configuration;

    public ErrorCollector(CompilerConfiguration configuration) {
        this.configuration = configuration;
    }

    public void addCollectorContents(ErrorCollector that) {
        if (that.errors != null) {
            if (this.errors == null) {
                this.errors = that.errors;
            } else {
                this.errors.addAll(that.errors);
            }
        }
        if (that.warnings != null) {
            if (this.warnings == null) {
                this.warnings = that.warnings;
            } else {
                this.warnings.addAll(that.warnings);
            }
        }
    }

    public void addErrorAndContinue(String error, ASTNode node, SourceUnit source) {
        this.addErrorAndContinue(Message.create(new SyntaxException(error, node), source));
    }

    public void addErrorAndContinue(SyntaxException error, SourceUnit source) {
        this.addErrorAndContinue(Message.create(error, source));
    }

    public void addErrorAndContinue(Message message) {
        if (this.errors == null) {
            this.errors = new LinkedList();
        }
        this.errors.add(message);
    }

    public void addError(Message message) throws CompilationFailedException {
        this.addErrorAndContinue(message);
        if (this.errors != null && this.errors.size() >= this.configuration.getTolerance()) {
            this.failIfErrors();
        }
    }

    public void addError(Message message, boolean fatal) throws CompilationFailedException {
        if (fatal) {
            this.addFatalError(message);
        } else {
            this.addError(message);
        }
    }

    public void addError(SyntaxException error, SourceUnit source) throws CompilationFailedException {
        this.addError(Message.create(error, source), error.isFatal());
    }

    public void addError(String error, CSTNode context, SourceUnit source) throws CompilationFailedException {
        this.addError(new LocatedMessage(error, context, source));
    }

    public void addException(Exception exception, SourceUnit source) throws CompilationFailedException {
        this.addError(new ExceptionMessage(exception, this.configuration.getDebug(), source));
        this.failIfErrors();
    }

    public void addFatalError(Message message) throws CompilationFailedException {
        this.addError(message);
        this.failIfErrors();
    }

    public void addWarning(WarningMessage message) {
        if (message.isRelevant(this.configuration.getWarningLevel())) {
            if (this.warnings == null) {
                this.warnings = new LinkedList();
            }
            this.warnings.add(message);
        }
    }

    public void addWarning(int importance, String text, CSTNode context, SourceUnit source) {
        if (WarningMessage.isRelevant(importance, this.configuration.getWarningLevel())) {
            this.addWarning(new WarningMessage(importance, text, context, source));
        }
    }

    public void addWarning(int importance, String text, Object data, CSTNode context, SourceUnit source) {
        if (WarningMessage.isRelevant(importance, this.configuration.getWarningLevel())) {
            this.addWarning(new WarningMessage(importance, text, data, context, source));
        }
    }

    public CompilerConfiguration getConfiguration() {
        return this.configuration;
    }

    public int getErrorCount() {
        return this.hasErrors() ? this.errors.size() : 0;
    }

    public Message getError(int index) {
        if (index < this.getErrorCount()) {
            return this.errors.get(index);
        }
        return null;
    }

    public List<? extends Message> getErrors() {
        return this.errors;
    }

    public boolean hasErrors() {
        return this.errors != null;
    }

    public int getWarningCount() {
        return this.hasWarnings() ? this.warnings.size() : 0;
    }

    public WarningMessage getWarning(int index) {
        if (index < this.getWarningCount()) {
            return this.warnings.get(index);
        }
        return null;
    }

    public List<WarningMessage> getWarnings() {
        return this.warnings;
    }

    public boolean hasWarnings() {
        return this.warnings != null;
    }

    public Message getLastError() {
        return this.errors.getLast();
    }

    public Exception getException(int index) {
        Exception exception = null;
        Message message = this.getError(index);
        if (message != null) {
            if (message instanceof ExceptionMessage) {
                exception = ((ExceptionMessage)message).getCause();
            } else if (message instanceof SyntaxErrorMessage) {
                exception = ((SyntaxErrorMessage)message).getCause();
            }
        }
        return exception;
    }

    public SyntaxException getSyntaxError(int index) {
        SyntaxException exception = null;
        Message message = this.getError(index);
        if (message instanceof SyntaxErrorMessage) {
            exception = ((SyntaxErrorMessage)message).getCause();
        }
        return exception;
    }

    protected void failIfErrors() throws CompilationFailedException {
        if (this.hasErrors()) {
            throw new MultipleCompilationErrorsException(this);
        }
    }

    private void write(PrintWriter writer, Janitor janitor, List<? extends Message> messages, String txt) {
        if (messages == null || messages.isEmpty()) {
            return;
        }
        for (Message message : messages) {
            message.write(writer, janitor);
            if (this.configuration.getDebug() && message instanceof SyntaxErrorMessage) {
                ((SyntaxErrorMessage)message).getCause().printStackTrace(writer);
            }
            writer.println();
        }
        writer.print(messages.size());
        writer.print(" " + txt);
        if (messages.size() > 1) {
            writer.print("s");
        }
        writer.println();
    }

    public void write(PrintWriter writer, Janitor janitor) {
        this.write(writer, janitor, this.warnings, "warning");
        this.write(writer, janitor, this.errors, "error");
    }
}

