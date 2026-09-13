/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.internal;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.internal.LocalizationMessages;
import org.glassfish.jersey.internal.util.PropertiesHelper;

public final class ReaderWriter {
    private static final Logger LOGGER = Logger.getLogger(ReaderWriter.class.getName());
    public static final Charset UTF8 = Charset.forName("UTF-8");
    public static final int BUFFER_SIZE = ReaderWriter.getBufferSize();

    private static int getBufferSize() {
        String value = AccessController.doPrivileged(PropertiesHelper.getSystemProperty("jersey.config.io.bufferSize"));
        if (value != null) {
            try {
                int i = Integer.parseInt(value);
                if (i <= 0) {
                    throw new NumberFormatException("Value not positive.");
                }
                return i;
            }
            catch (NumberFormatException e) {
                LOGGER.log(Level.CONFIG, "Value of jersey.config.io.bufferSize property is not a valid positive integer [" + value + "]. Reverting to default [" + 8192 + "].", e);
            }
        }
        return 8192;
    }

    public static void writeTo(InputStream in, OutputStream out) throws IOException {
        int read;
        byte[] data = new byte[BUFFER_SIZE];
        while ((read = in.read(data)) != -1) {
            out.write(data, 0, read);
        }
    }

    public static void writeTo(Reader in, Writer out) throws IOException {
        int read;
        char[] data = new char[BUFFER_SIZE];
        while ((read = in.read(data)) != -1) {
            out.write(data, 0, read);
        }
    }

    public static Charset getCharset(MediaType m) {
        String name = m == null ? null : m.getParameters().get("charset");
        return name == null ? UTF8 : Charset.forName(name);
    }

    public static String readFromAsString(InputStream in, MediaType type) throws IOException {
        return ReaderWriter.readFromAsString(new InputStreamReader(in, ReaderWriter.getCharset(type)));
    }

    public static String readFromAsString(Reader reader) throws IOException {
        int l;
        StringBuilder sb = new StringBuilder();
        char[] c = new char[BUFFER_SIZE];
        while ((l = reader.read(c)) != -1) {
            sb.append(c, 0, l);
        }
        return sb.toString();
    }

    public static void writeToAsString(String s, OutputStream out, MediaType type) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(out, ReaderWriter.getCharset(type));
        ((Writer)osw).write(s, 0, s.length());
        ((Writer)osw).flush();
    }

    public static void safelyClose(Closeable closeable) {
        try {
            closeable.close();
        }
        catch (IOException ioe) {
            LOGGER.log(Level.FINE, LocalizationMessages.MESSAGE_CONTENT_INPUT_STREAM_CLOSE_FAILED(), ioe);
        }
        catch (ProcessingException pe) {
            LOGGER.log(Level.FINE, LocalizationMessages.MESSAGE_CONTENT_INPUT_STREAM_CLOSE_FAILED(), pe);
        }
    }

    private ReaderWriter() {
    }
}

