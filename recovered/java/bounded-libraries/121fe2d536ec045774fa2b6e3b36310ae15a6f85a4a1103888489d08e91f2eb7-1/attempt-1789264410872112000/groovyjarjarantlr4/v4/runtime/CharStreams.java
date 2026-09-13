/*
 * Decompiled with CFR 0.152.
 */
package groovyjarjarantlr4.v4.runtime;

import groovyjarjarantlr4.v4.runtime.CharStream;
import groovyjarjarantlr4.v4.runtime.CodePointBuffer;
import groovyjarjarantlr4.v4.runtime.CodePointCharStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

public enum CharStreams {

    private static final int DEFAULT_BUFFER_SIZE = 4096;

    public static CharStream fromFile(File file) throws IOException {
        return CharStreams.fromFile(file, Charset.forName("UTF-8"));
    }

    public static CharStream fromFile(File file, Charset charset) throws IOException {
        long size = file.length();
        return CharStreams.fromStream(new FileInputStream(file), charset, file.toString(), size);
    }

    public static CharStream fromFileName(String fileName) throws IOException {
        return CharStreams.fromFileName(fileName, Charset.forName("UTF-8"));
    }

    public static CharStream fromFileName(String fileName, Charset charset) throws IOException {
        return CharStreams.fromFile(new File(fileName), charset);
    }

    public static CharStream fromStream(InputStream is) throws IOException {
        return CharStreams.fromStream(is, Charset.forName("UTF-8"));
    }

    public static CharStream fromStream(InputStream is, Charset charset) throws IOException {
        return CharStreams.fromStream(is, charset, "<unknown>", -1L);
    }

    public static CharStream fromStream(InputStream is, Charset charset, String sourceName, long inputSize) throws IOException {
        return CharStreams.fromChannel(Channels.newChannel(is), charset, 4096, CodingErrorAction.REPLACE, sourceName, inputSize);
    }

    public static CharStream fromChannel(ReadableByteChannel channel) throws IOException {
        return CharStreams.fromChannel(channel, Charset.forName("UTF-8"));
    }

    public static CharStream fromChannel(ReadableByteChannel channel, Charset charset) throws IOException {
        return CharStreams.fromChannel(channel, 4096, CodingErrorAction.REPLACE, "<unknown>");
    }

    public static CodePointCharStream fromReader(Reader r) throws IOException {
        return CharStreams.fromReader(r, "<unknown>");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static CodePointCharStream fromReader(Reader r, String sourceName) throws IOException {
        try {
            CodePointBuffer.Builder codePointBufferBuilder = CodePointBuffer.builder(4096);
            CharBuffer charBuffer = CharBuffer.allocate(4096);
            while (r.read(charBuffer) != -1) {
                charBuffer.flip();
                codePointBufferBuilder.append(charBuffer);
                charBuffer.compact();
            }
            CodePointCharStream codePointCharStream = CodePointCharStream.fromBuffer(codePointBufferBuilder.build(), sourceName);
            return codePointCharStream;
        }
        finally {
            r.close();
        }
    }

    public static CodePointCharStream fromString(String s) {
        return CharStreams.fromString(s, "<unknown>");
    }

    public static CodePointCharStream fromString(String s, String sourceName) {
        CodePointBuffer.Builder codePointBufferBuilder = CodePointBuffer.builder(s.length());
        CharBuffer cb = CharBuffer.allocate(s.length());
        cb.put(s);
        cb.flip();
        codePointBufferBuilder.append(cb);
        return CodePointCharStream.fromBuffer(codePointBufferBuilder.build(), sourceName);
    }

    public static CodePointCharStream fromChannel(ReadableByteChannel channel, int bufferSize, CodingErrorAction decodingErrorAction, String sourceName) throws IOException {
        return CharStreams.fromChannel(channel, Charset.forName("UTF-8"), bufferSize, decodingErrorAction, sourceName, -1L);
    }

    public static CodePointCharStream fromChannel(ReadableByteChannel channel, Charset charset, int bufferSize, CodingErrorAction decodingErrorAction, String sourceName, long inputSize) throws IOException {
        CodePointBuffer codePointBuffer = CharStreams.bufferFromChannel(channel, charset, bufferSize, decodingErrorAction, inputSize);
        return CodePointCharStream.fromBuffer(codePointBuffer, sourceName);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static CodePointBuffer bufferFromChannel(ReadableByteChannel channel, Charset charset, int bufferSize, CodingErrorAction decodingErrorAction, long inputSize) throws IOException {
        try {
            ByteBuffer utf8BytesIn = ByteBuffer.allocate(bufferSize);
            CharBuffer utf16CodeUnitsOut = CharBuffer.allocate(bufferSize);
            if (inputSize == -1L) {
                inputSize = bufferSize;
            } else if (inputSize > Integer.MAX_VALUE) {
                throw new IOException(String.format("inputSize %d larger than max %d", inputSize, Integer.MAX_VALUE));
            }
            CodePointBuffer.Builder codePointBufferBuilder = CodePointBuffer.builder((int)inputSize);
            CharsetDecoder decoder = charset.newDecoder().onMalformedInput(decodingErrorAction).onUnmappableCharacter(decodingErrorAction);
            boolean endOfInput = false;
            while (!endOfInput) {
                int bytesRead = channel.read(utf8BytesIn);
                endOfInput = bytesRead == -1;
                utf8BytesIn.flip();
                CoderResult result = decoder.decode(utf8BytesIn, utf16CodeUnitsOut, endOfInput);
                if (result.isError() && decodingErrorAction.equals(CodingErrorAction.REPORT)) {
                    result.throwException();
                }
                utf16CodeUnitsOut.flip();
                codePointBufferBuilder.append(utf16CodeUnitsOut);
                utf8BytesIn.compact();
                utf16CodeUnitsOut.compact();
            }
            CoderResult flushResult = decoder.flush(utf16CodeUnitsOut);
            if (flushResult.isError() && decodingErrorAction.equals(CodingErrorAction.REPORT)) {
                flushResult.throwException();
            }
            utf16CodeUnitsOut.flip();
            codePointBufferBuilder.append(utf16CodeUnitsOut);
            CodePointBuffer codePointBuffer = codePointBufferBuilder.build();
            return codePointBuffer;
        }
        finally {
            channel.close();
        }
    }
}

