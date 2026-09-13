/*
 * Decompiled with CFR 0.152.
 */
package org.springframework.boot.web.server;

import java.net.BindException;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import org.springframework.boot.web.server.WebServerException;

public class PortInUseException
extends WebServerException {
    private final int port;

    public PortInUseException(int port) {
        this(port, null);
    }

    public PortInUseException(int port, Throwable cause) {
        super("Port " + port + " is already in use", cause);
        this.port = port;
    }

    public int getPort() {
        return this.port;
    }

    public static void throwIfPortBindingException(Exception ex, IntSupplier port) {
        PortInUseException.ifPortBindingException(ex, bindException -> {
            throw new PortInUseException(port.getAsInt(), (Throwable)ex);
        });
    }

    public static void ifPortBindingException(Exception ex, Consumer<BindException> action) {
        PortInUseException.ifCausedBy(ex, BindException.class, bindException -> {
            if (bindException.getMessage().toLowerCase().contains("in use")) {
                action.accept((BindException)bindException);
            }
        });
    }

    public static <E extends Exception> void ifCausedBy(Exception ex, Class<E> causedBy, Consumer<E> action) {
        for (Throwable candidate = ex; candidate != null; candidate = candidate.getCause()) {
            if (!causedBy.isInstance(candidate)) continue;
            action.accept(candidate);
            return;
        }
    }
}

