/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.lang.UsesSunHttpServer
 *  org.springframework.util.FileCopyUtils
 */
package org.springframework.remoting.caucho;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.springframework.lang.UsesSunHttpServer;
import org.springframework.remoting.caucho.HessianExporter;
import org.springframework.util.FileCopyUtils;

@Deprecated
@UsesSunHttpServer
public class SimpleHessianServiceExporter
extends HessianExporter
implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!"POST".equals(exchange.getRequestMethod())) {
            exchange.getResponseHeaders().set("Allow", "POST");
            exchange.sendResponseHeaders(405, -1L);
            return;
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream(1024);
        try {
            this.invoke(exchange.getRequestBody(), output);
        }
        catch (Throwable ex) {
            exchange.sendResponseHeaders(500, -1L);
            this.logger.error((Object)"Hessian skeleton invocation failed", ex);
            return;
        }
        exchange.getResponseHeaders().set("Content-Type", "application/x-hessian");
        exchange.sendResponseHeaders(200, output.size());
        FileCopyUtils.copy((byte[])output.toByteArray(), (OutputStream)exchange.getResponseBody());
    }
}

