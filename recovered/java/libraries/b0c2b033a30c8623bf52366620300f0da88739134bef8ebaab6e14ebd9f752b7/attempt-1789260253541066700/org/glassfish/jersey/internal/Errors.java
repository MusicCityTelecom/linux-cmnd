/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Logger;
import org.glassfish.jersey.Severity;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.util.Producer;

public class Errors {
    private static final Logger LOGGER = Logger.getLogger(Errors.class.getName());
    private static final ThreadLocal<Errors> errors = new ThreadLocal();
    private final ArrayList<ErrorMessage> issues = new ArrayList(0);
    private Deque<Integer> mark = new ArrayDeque<Integer>(4);
    private int stack = 0;

    public static void error(String message, Severity severity) {
        Errors.error(null, message, severity);
    }

    public static void error(Object source, String message, Severity severity) {
        Errors.getInstance().issues.add(new ErrorMessage(source, message, severity));
    }

    public static void fatal(Object source, String message) {
        Errors.error(source, message, Severity.FATAL);
    }

    public static void warning(Object source, String message) {
        Errors.error(source, message, Severity.WARNING);
    }

    public static void hint(Object source, String message) {
        Errors.getInstance().issues.add(new ErrorMessage(source, message, Severity.HINT));
    }

    private static void processErrors(boolean throwException) {
        ArrayList<ErrorMessage> errors = new ArrayList<ErrorMessage>(Errors.errors.get().issues);
        boolean isFatal = Errors.logErrors(errors);
        if (throwException && isFatal) {
            throw new ErrorMessagesException(errors);
        }
    }

    public static boolean logErrors(boolean afterMark) {
        return Errors.logErrors(Errors.getInstance()._getErrorMessages(afterMark));
    }

    private static boolean logErrors(Collection<ErrorMessage> errors) {
        boolean isFatal = false;
        if (!errors.isEmpty()) {
            StringBuilder fatals = new StringBuilder("\n");
            StringBuilder warnings = new StringBuilder();
            StringBuilder hints = new StringBuilder();
            for (ErrorMessage error : errors) {
                switch (error.getSeverity()) {
                    case FATAL: {
                        isFatal = true;
                        fatals.append(LocalizationMessages.ERROR_MSG(error.getMessage())).append('\n');
                        break;
                    }
                    case WARNING: {
                        warnings.append(LocalizationMessages.WARNING_MSG(error.getMessage())).append('\n');
                        break;
                    }
                    case HINT: {
                        hints.append(LocalizationMessages.HINT_MSG(error.getMessage())).append('\n');
                    }
                }
            }
            if (isFatal) {
                LOGGER.severe(LocalizationMessages.ERRORS_AND_WARNINGS_DETECTED(fatals.append((CharSequence)warnings).append((CharSequence)hints).toString()));
            } else {
                if (warnings.length() > 0) {
                    LOGGER.warning(LocalizationMessages.WARNINGS_DETECTED(warnings.toString()));
                }
                if (hints.length() > 0) {
                    LOGGER.config(LocalizationMessages.HINTS_DETECTED(hints.toString()));
                }
            }
        }
        return isFatal;
    }

    public static boolean fatalIssuesFound() {
        for (ErrorMessage message : Errors.getInstance().issues) {
            if (message.getSeverity() != Severity.FATAL) continue;
            return true;
        }
        return false;
    }

    public static <T> T process(Producer<T> producer) {
        return Errors.process(producer, false);
    }

    public static <T> T process(Callable<T> task) throws Exception {
        return Errors.process(task, true);
    }

    public static <T> T processWithException(Producer<T> producer) {
        return Errors.process(producer, true);
    }

    public static void process(final Runnable task) {
        Errors.process(new Producer<Void>(){

            @Override
            public Void call() {
                task.run();
                return null;
            }
        }, false);
    }

    public static void processWithException(final Runnable task) {
        Errors.process(new Producer<Void>(){

            @Override
            public Void call() {
                task.run();
                return null;
            }
        }, true);
    }

    private static <T> T process(Producer<T> task, boolean throwException) {
        try {
            return Errors.process(task, throwException);
        }
        catch (RuntimeException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static <T> T process(Callable<T> task, boolean throwException) throws Exception {
        T t;
        Errors instance = errors.get();
        if (instance == null) {
            instance = new Errors();
            errors.set(instance);
        }
        instance.preProcess();
        Exception caught = null;
        try {
            t = task.call();
            instance.postProcess(throwException && caught == null);
        }
        catch (Exception re) {
            try {
                caught = re;
                instance.postProcess(throwException && caught == null);
            }
            catch (Throwable throwable) {
                instance.postProcess(throwException && caught == null);
                throw throwable;
            }
        }
        return t;
        throw caught;
    }

    private static Errors getInstance() {
        Errors instance = errors.get();
        if (instance == null) {
            throw new IllegalStateException(LocalizationMessages.NO_ERROR_PROCESSING_IN_SCOPE());
        }
        if (instance.stack == 0) {
            errors.remove();
            throw new IllegalStateException(LocalizationMessages.NO_ERROR_PROCESSING_IN_SCOPE());
        }
        return instance;
    }

    public static List<ErrorMessage> getErrorMessages() {
        return Errors.getErrorMessages(false);
    }

    public static List<ErrorMessage> getErrorMessages(boolean afterMark) {
        return Errors.getInstance()._getErrorMessages(afterMark);
    }

    public static void mark() {
        Errors.getInstance()._mark();
    }

    public static void unmark() {
        Errors.getInstance()._unmark();
    }

    public static void reset() {
        Errors.getInstance()._reset();
    }

    private Errors() {
    }

    private void _mark() {
        this.mark.addLast(this.issues.size());
    }

    private void _unmark() {
        this.mark.pollLast();
    }

    private void _reset() {
        int markedPos;
        Integer _pos = this.mark.pollLast();
        int n = markedPos = _pos == null ? -1 : _pos;
        if (markedPos >= 0 && markedPos < this.issues.size()) {
            this.issues.subList(markedPos, this.issues.size()).clear();
        }
    }

    private void preProcess() {
        ++this.stack;
    }

    private void postProcess(boolean throwException) {
        --this.stack;
        if (this.stack == 0) {
            try {
                if (!this.issues.isEmpty()) {
                    Errors.processErrors(throwException);
                }
            }
            finally {
                errors.remove();
            }
        }
    }

    private List<ErrorMessage> _getErrorMessages(boolean afterMark) {
        if (afterMark) {
            int markedPos;
            Integer _pos = this.mark.peekLast();
            int n = markedPos = _pos == null ? -1 : _pos;
            if (markedPos >= 0 && markedPos < this.issues.size()) {
                return Collections.unmodifiableList(new ArrayList<ErrorMessage>(this.issues.subList(markedPos, this.issues.size())));
            }
        }
        return Collections.unmodifiableList(new ArrayList<ErrorMessage>(this.issues));
    }

    public static class ErrorMessage {
        private final Object source;
        private final String message;
        private final Severity severity;

        private ErrorMessage(Object source, String message, Severity severity) {
            this.source = source;
            this.message = message;
            this.severity = severity;
        }

        public Severity getSeverity() {
            return this.severity;
        }

        public String getMessage() {
            return this.message;
        }

        public Object getSource() {
            return this.source;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            ErrorMessage that = (ErrorMessage)o;
            if (this.message != null ? !this.message.equals(that.message) : that.message != null) {
                return false;
            }
            if (this.severity != that.severity) {
                return false;
            }
            return !(this.source != null ? !this.source.equals(that.source) : that.source != null);
        }

        public int hashCode() {
            int result = this.source != null ? this.source.hashCode() : 0;
            result = 31 * result + (this.message != null ? this.message.hashCode() : 0);
            result = 31 * result + (this.severity != null ? this.severity.hashCode() : 0);
            return result;
        }
    }

    public static class ErrorMessagesException
    extends RuntimeException {
        private final List<ErrorMessage> messages;

        private ErrorMessagesException(List<ErrorMessage> messages) {
            this.messages = messages;
        }

        public List<ErrorMessage> getMessages() {
            return this.messages;
        }
    }
}

