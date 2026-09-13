/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.control.messages;

import java.io.PrintWriter;
import java.util.Objects;
import org.codehaus.groovy.control.Janitor;
import org.codehaus.groovy.control.ProcessingUnit;
import org.codehaus.groovy.control.messages.Message;

public class ExceptionMessage
extends Message {
    private final Exception cause;
    protected final boolean debug;
    protected final ProcessingUnit owner;

    public ExceptionMessage(Exception cause, boolean debug, ProcessingUnit owner) {
        this.cause = Objects.requireNonNull(cause);
        this.debug = debug;
        this.owner = owner;
    }

    public Exception getCause() {
        return this.cause;
    }

    @Override
    public void write(PrintWriter output, Janitor janitor) {
        String description = "General error during " + this.owner.getPhaseDescription() + ": ";
        String message = this.cause.getMessage();
        if (message != null) {
            output.println(description + message);
        } else {
            output.println(description + this.cause);
        }
        output.println();
        this.cause.printStackTrace(output);
    }
}

