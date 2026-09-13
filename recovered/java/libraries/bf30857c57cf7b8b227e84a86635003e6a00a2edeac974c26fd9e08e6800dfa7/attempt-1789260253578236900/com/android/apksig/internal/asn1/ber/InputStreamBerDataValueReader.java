/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.asn1.ber;

import com.android.apksig.internal.asn1.ber.BerDataValue;
import com.android.apksig.internal.asn1.ber.BerDataValueFormatException;
import com.android.apksig.internal.asn1.ber.BerDataValueReader;
import com.android.apksig.internal.asn1.ber.BerEncoding;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class InputStreamBerDataValueReader
implements BerDataValueReader {
    private final InputStream mIn;

    public InputStreamBerDataValueReader(InputStream in) {
        if (in == null) {
            throw new NullPointerException("in == null");
        }
        this.mIn = in;
    }

    @Override
    public BerDataValue readDataValue() throws BerDataValueFormatException {
        return InputStreamBerDataValueReader.readDataValue(this.mIn);
    }

    private static BerDataValue readDataValue(InputStream input) throws BerDataValueFormatException {
        RecordingInputStream in = new RecordingInputStream(input);
        try {
            int contentsOffsetInDataValue;
            int contentsLength;
            int firstIdentifierByte = in.read();
            if (firstIdentifierByte == -1) {
                return null;
            }
            int tagNumber = InputStreamBerDataValueReader.readTagNumber(in, firstIdentifierByte);
            int firstLengthByte = in.read();
            if (firstLengthByte == -1) {
                throw new BerDataValueFormatException("Missing length");
            }
            boolean constructed = BerEncoding.isConstructed((byte)firstIdentifierByte);
            if ((firstLengthByte & 0x80) == 0) {
                contentsLength = InputStreamBerDataValueReader.readShortFormLength(firstLengthByte);
                contentsOffsetInDataValue = in.getReadByteCount();
                InputStreamBerDataValueReader.skipDefiniteLengthContents(in, contentsLength);
            } else if ((firstLengthByte & 0xFF) != 128) {
                contentsLength = InputStreamBerDataValueReader.readLongFormLength(in, firstLengthByte);
                contentsOffsetInDataValue = in.getReadByteCount();
                InputStreamBerDataValueReader.skipDefiniteLengthContents(in, contentsLength);
            } else {
                contentsOffsetInDataValue = in.getReadByteCount();
                contentsLength = constructed ? InputStreamBerDataValueReader.skipConstructedIndefiniteLengthContents(in) : InputStreamBerDataValueReader.skipPrimitiveIndefiniteLengthContents(in);
            }
            byte[] encoded = in.getReadBytes();
            ByteBuffer encodedContents = ByteBuffer.wrap(encoded, contentsOffsetInDataValue, contentsLength);
            return new BerDataValue(ByteBuffer.wrap(encoded), encodedContents, BerEncoding.getTagClass((byte)firstIdentifierByte), constructed, tagNumber);
        }
        catch (IOException e2) {
            throw new BerDataValueFormatException("Failed to read data value", e2);
        }
    }

    private static int readTagNumber(InputStream in, int firstIdentifierByte) throws IOException, BerDataValueFormatException {
        int tagNumber = BerEncoding.getTagNumber((byte)firstIdentifierByte);
        if (tagNumber == 31) {
            return InputStreamBerDataValueReader.readHighTagNumber(in);
        }
        return tagNumber;
    }

    private static int readHighTagNumber(InputStream in) throws IOException, BerDataValueFormatException {
        int b2;
        int result = 0;
        do {
            if ((b2 = in.read()) == -1) {
                throw new BerDataValueFormatException("Truncated tag number");
            }
            if (result > 0xFFFFFF) {
                throw new BerDataValueFormatException("Tag number too large");
            }
            result <<= 7;
            result |= b2 & 0x7F;
        } while ((b2 & 0x80) != 0);
        return result;
    }

    private static int readShortFormLength(int firstLengthByte) {
        return firstLengthByte & 0x7F;
    }

    private static int readLongFormLength(InputStream in, int firstLengthByte) throws IOException, BerDataValueFormatException {
        int byteCount = firstLengthByte & 0x7F;
        if (byteCount > 4) {
            throw new BerDataValueFormatException("Length too large: " + byteCount + " bytes");
        }
        int result = 0;
        for (int i2 = 0; i2 < byteCount; ++i2) {
            int b2 = in.read();
            if (b2 == -1) {
                throw new BerDataValueFormatException("Truncated length");
            }
            if (result > 0x7FFFFF) {
                throw new BerDataValueFormatException("Length too large");
            }
            result <<= 8;
            result |= b2 & 0xFF;
        }
        return result;
    }

    private static void skipDefiniteLengthContents(InputStream in, int len) throws IOException, BerDataValueFormatException {
        long bytesRead = 0L;
        while (len > 0) {
            int skipped = (int)in.skip(len);
            if (skipped <= 0) {
                throw new BerDataValueFormatException("Truncated definite-length contents: " + bytesRead + " bytes read, " + len + " missing");
            }
            len -= skipped;
            bytesRead += (long)skipped;
        }
    }

    private static int skipPrimitiveIndefiniteLengthContents(InputStream in) throws IOException, BerDataValueFormatException {
        boolean prevZeroByte = false;
        int bytesRead = 0;
        while (true) {
            int b2;
            if ((b2 = in.read()) == -1) {
                throw new BerDataValueFormatException("Truncated indefinite-length contents: " + bytesRead + " bytes read");
            }
            if (++bytesRead < 0) {
                throw new BerDataValueFormatException("Indefinite-length contents too long");
            }
            if (b2 == 0) {
                if (prevZeroByte) {
                    return bytesRead - 2;
                }
                prevZeroByte = true;
                continue;
            }
            prevZeroByte = false;
        }
    }

    private static int skipConstructedIndefiniteLengthContents(RecordingInputStream in) throws BerDataValueFormatException {
        BerDataValue dataValue;
        ByteBuffer encoded;
        int readByteCountBefore = in.getReadByteCount();
        do {
            if ((dataValue = InputStreamBerDataValueReader.readDataValue(in)) == null) {
                throw new BerDataValueFormatException("Truncated indefinite-length contents: " + (in.getReadByteCount() - readByteCountBefore) + " bytes read");
            }
            if (in.getReadByteCount() > 0) continue;
            throw new BerDataValueFormatException("Indefinite-length contents too long");
        } while ((encoded = dataValue.getEncoded()).remaining() != 2 || encoded.get(0) != 0 || encoded.get(1) != 0);
        return in.getReadByteCount() - readByteCountBefore - 2;
    }

    private static class RecordingInputStream
    extends InputStream {
        private final InputStream mIn;
        private final ByteArrayOutputStream mBuf;

        private RecordingInputStream(InputStream in) {
            this.mIn = in;
            this.mBuf = new ByteArrayOutputStream();
        }

        public byte[] getReadBytes() {
            return this.mBuf.toByteArray();
        }

        public int getReadByteCount() {
            return this.mBuf.size();
        }

        @Override
        public int read() throws IOException {
            int b2 = this.mIn.read();
            if (b2 != -1) {
                this.mBuf.write(b2);
            }
            return b2;
        }

        @Override
        public int read(byte[] b2) throws IOException {
            int len = this.mIn.read(b2);
            if (len > 0) {
                this.mBuf.write(b2, 0, len);
            }
            return len;
        }

        @Override
        public int read(byte[] b2, int off, int len) throws IOException {
            if ((len = this.mIn.read(b2, off, len)) > 0) {
                this.mBuf.write(b2, off, len);
            }
            return len;
        }

        @Override
        public long skip(long n2) throws IOException {
            if (n2 <= 0L) {
                return this.mIn.skip(n2);
            }
            byte[] buf = new byte[4096];
            int len = this.mIn.read(buf, 0, (int)Math.min((long)buf.length, n2));
            if (len > 0) {
                this.mBuf.write(buf, 0, len);
            }
            return len < 0 ? 0L : (long)len;
        }

        @Override
        public int available() throws IOException {
            return super.available();
        }

        @Override
        public void close() throws IOException {
            super.close();
        }

        @Override
        public synchronized void mark(int readlimit) {
        }

        @Override
        public synchronized void reset() throws IOException {
            throw new IOException("mark/reset not supported");
        }

        @Override
        public boolean markSupported() {
            return false;
        }
    }
}

