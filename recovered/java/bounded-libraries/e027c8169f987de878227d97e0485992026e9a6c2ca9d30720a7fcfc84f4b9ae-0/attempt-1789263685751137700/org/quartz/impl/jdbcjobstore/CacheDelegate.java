/*
 * Decompiled with CFR 0.152.
 */
package org.quartz.impl.jdbcjobstore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.quartz.impl.jdbcjobstore.StdJDBCDelegate;

public class CacheDelegate
extends StdJDBCDelegate {
    @Override
    protected void setBytes(PreparedStatement ps, int index, ByteArrayOutputStream baos) throws SQLException {
        ps.setObject(index, (Object)(baos == null ? null : baos.toByteArray()), 2004);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected Object getObjectFromBlob(ResultSet rs, String colName) throws ClassNotFoundException, IOException, SQLException {
        Blob blob = rs.getBlob(colName);
        if (blob == null) {
            return null;
        }
        try {
            Object object;
            if (blob.length() == 0L) {
                Object var4_4 = null;
                return var4_4;
            }
            InputStream binaryInput = blob.getBinaryStream();
            if (binaryInput == null) {
                Object var5_6 = null;
                return var5_6;
            }
            if (binaryInput instanceof ByteArrayInputStream && ((ByteArrayInputStream)binaryInput).available() == 0) {
                Object var5_7 = null;
                return var5_7;
            }
            ObjectInputStream in = new ObjectInputStream(binaryInput);
            try {
                object = in.readObject();
            }
            catch (Throwable throwable) {
                in.close();
                throw throwable;
            }
            in.close();
            return object;
        }
        finally {
            blob.free();
        }
    }

    @Override
    protected Object getJobDataFromBlob(ResultSet rs, String colName) throws ClassNotFoundException, IOException, SQLException {
        if (this.canUseProperties()) {
            Blob blob = rs.getBlob(colName);
            if (blob == null) {
                return null;
            }
            return new BlobFreeingStream(blob, blob.getBinaryStream());
        }
        return this.getObjectFromBlob(rs, colName);
    }

    private static class BlobFreeingStream
    extends InputStream {
        private final Blob source;
        private final InputStream delegate;

        private BlobFreeingStream(Blob blob, InputStream stream) {
            this.source = blob;
            this.delegate = stream;
        }

        @Override
        public int read() throws IOException {
            return this.delegate.read();
        }

        @Override
        public int read(byte[] b) throws IOException {
            return this.delegate.read(b);
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            return this.delegate.read(b, off, len);
        }

        @Override
        public long skip(long n) throws IOException {
            return this.delegate.skip(n);
        }

        @Override
        public int available() throws IOException {
            return this.delegate.available();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void close() throws IOException {
            try {
                this.delegate.close();
            }
            finally {
                try {
                    this.source.free();
                }
                catch (SQLException ex) {
                    throw new IOException(ex);
                }
            }
        }

        @Override
        public synchronized void mark(int readlimit) {
            this.delegate.mark(readlimit);
        }

        @Override
        public synchronized void reset() throws IOException {
            this.delegate.reset();
        }

        @Override
        public boolean markSupported() {
            return this.delegate.markSupported();
        }
    }
}

