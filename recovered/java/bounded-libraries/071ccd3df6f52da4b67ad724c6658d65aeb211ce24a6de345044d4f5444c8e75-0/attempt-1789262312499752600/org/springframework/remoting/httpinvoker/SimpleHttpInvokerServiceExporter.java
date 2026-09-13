/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.UsesSunHttpServer
 *  org.springframework.remoting.rmi.RemoteInvocationSerializingExporter
 *  org.springframework.remoting.support.RemoteInvocation
 *  org.springframework.remoting.support.RemoteInvocationResult
 */
package org.springframework.remoting.httpinvoker;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import org.springframework.lang.UsesSunHttpServer;
import org.springframework.remoting.rmi.RemoteInvocationSerializingExporter;
import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationResult;

@Deprecated
@UsesSunHttpServer
public class SimpleHttpInvokerServiceExporter
extends RemoteInvocationSerializingExporter
implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            RemoteInvocation invocation = this.readRemoteInvocation(exchange);
            RemoteInvocationResult result = this.invokeAndCreateResult(invocation, this.getProxy());
            this.writeRemoteInvocationResult(exchange, result);
            exchange.close();
        }
        catch (ClassNotFoundException ex) {
            exchange.sendResponseHeaders(500, -1L);
            this.logger.error((Object)"Class not found during deserialization", (Throwable)ex);
        }
    }

    protected RemoteInvocation readRemoteInvocation(HttpExchange exchange) throws IOException, ClassNotFoundException {
        return this.readRemoteInvocation(exchange, exchange.getRequestBody());
    }

    protected RemoteInvocation readRemoteInvocation(HttpExchange exchange, InputStream is) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = this.createObjectInputStream(this.decorateInputStream(exchange, is));
        return this.doReadRemoteInvocation(ois);
    }

    protected InputStream decorateInputStream(HttpExchange exchange, InputStream is) throws IOException {
        return is;
    }

    protected void writeRemoteInvocationResult(HttpExchange exchange, RemoteInvocationResult result) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", this.getContentType());
        exchange.sendResponseHeaders(200, 0L);
        this.writeRemoteInvocationResult(exchange, result, exchange.getResponseBody());
    }

    protected void writeRemoteInvocationResult(HttpExchange exchange, RemoteInvocationResult result, OutputStream os) throws IOException {
        ObjectOutputStream oos = this.createObjectOutputStream(this.decorateOutputStream(exchange, os));
        this.doWriteRemoteInvocationResult(result, oos);
        oos.flush();
    }

    protected OutputStream decorateOutputStream(HttpExchange exchange, OutputStream os) throws IOException {
        return os;
    }
}

