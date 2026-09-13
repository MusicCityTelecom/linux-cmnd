/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.springframework.core.io.FileSystemResource
 *  org.springframework.core.io.Resource
 *  org.springframework.lang.Nullable
 *  org.springframework.util.ClassUtils
 *  org.springframework.util.ReflectionUtils
 */
package org.springframework.boot.actuate.management;

import java.io.Closeable;
import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

@WebEndpoint(id="heapdump")
public class HeapDumpWebEndpoint {
    private final long timeout;
    private final Lock lock = new ReentrantLock();
    private HeapDumper heapDumper;

    public HeapDumpWebEndpoint() {
        this(TimeUnit.SECONDS.toMillis(10L));
    }

    protected HeapDumpWebEndpoint(long timeout) {
        this.timeout = timeout;
    }

    @ReadOperation
    public WebEndpointResponse<Resource> heapDump(@Nullable Boolean live) {
        block7: {
            if (!this.lock.tryLock(this.timeout, TimeUnit.MILLISECONDS)) break block7;
            try {
                WebEndpointResponse<Resource> webEndpointResponse = new WebEndpointResponse<Resource>(this.dumpHeap(live != null ? live : true));
                this.lock.unlock();
                return webEndpointResponse;
            }
            catch (Throwable throwable) {
                try {
                    this.lock.unlock();
                    throw throwable;
                }
                catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                catch (IOException ex) {
                    return new WebEndpointResponse<int>(500);
                }
                catch (HeapDumperUnavailableException ex) {
                    return new WebEndpointResponse<int>(503);
                }
            }
        }
        return new WebEndpointResponse<int>(429);
    }

    private Resource dumpHeap(boolean live) throws IOException, InterruptedException {
        if (this.heapDumper == null) {
            this.heapDumper = this.createHeapDumper();
        }
        File file = this.createTempFile();
        this.heapDumper.dumpHeap(file, live);
        return new TemporaryFileSystemResource(file);
    }

    private File createTempFile() throws IOException {
        String date = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm").format(LocalDateTime.now());
        File file = File.createTempFile("heap-" + date, "." + this.determineDumpSuffix());
        file.delete();
        return file;
    }

    private String determineDumpSuffix() {
        if (this.heapDumper instanceof OpenJ9DiagnosticsMXBeanHeapDumper) {
            return "phd";
        }
        return "hprof";
    }

    protected HeapDumper createHeapDumper() throws HeapDumperUnavailableException {
        try {
            return new HotSpotDiagnosticMXBeanHeapDumper();
        }
        catch (HeapDumperUnavailableException ex) {
            return new OpenJ9DiagnosticsMXBeanHeapDumper();
        }
    }

    private static final class TemporaryFileSystemResource
    extends FileSystemResource {
        private final Log logger = LogFactory.getLog(((Object)((Object)this)).getClass());

        private TemporaryFileSystemResource(File file) {
            super(file);
        }

        public ReadableByteChannel readableChannel() throws IOException {
            final ReadableByteChannel readableChannel = super.readableChannel();
            return new ReadableByteChannel(){

                @Override
                public boolean isOpen() {
                    return readableChannel.isOpen();
                }

                @Override
                public void close() throws IOException {
                    this.closeThenDeleteFile(readableChannel);
                }

                @Override
                public int read(ByteBuffer dst) throws IOException {
                    return readableChannel.read(dst);
                }
            };
        }

        public InputStream getInputStream() throws IOException {
            return new FilterInputStream(super.getInputStream()){

                @Override
                public void close() throws IOException {
                    this.closeThenDeleteFile(this.in);
                }
            };
        }

        private void closeThenDeleteFile(Closeable closeable) throws IOException {
            try {
                closeable.close();
            }
            finally {
                this.deleteFile();
            }
        }

        private void deleteFile() {
            try {
                Files.delete(this.getFile().toPath());
            }
            catch (IOException ex) {
                this.logger.warn((Object)("Failed to delete temporary heap dump file '" + this.getFile() + "'"), (Throwable)ex);
            }
        }

        public boolean isFile() {
            return false;
        }
    }

    protected static class HeapDumperUnavailableException
    extends RuntimeException {
        public HeapDumperUnavailableException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private static final class OpenJ9DiagnosticsMXBeanHeapDumper
    implements HeapDumper {
        private Object diagnosticMXBean;
        private Method dumpHeapMethod;

        private OpenJ9DiagnosticsMXBeanHeapDumper() {
            try {
                Class mxBeanClass = ClassUtils.resolveClassName((String)"openj9.lang.management.OpenJ9DiagnosticsMXBean", null);
                this.diagnosticMXBean = ManagementFactory.getPlatformMXBean(mxBeanClass);
                this.dumpHeapMethod = ReflectionUtils.findMethod((Class)mxBeanClass, (String)"triggerDumpToFile", (Class[])new Class[]{String.class, String.class});
            }
            catch (Throwable ex) {
                throw new HeapDumperUnavailableException("Unable to locate OpenJ9DiagnosticsMXBean", ex);
            }
        }

        @Override
        public void dumpHeap(File file, boolean live) throws IOException, InterruptedException {
            ReflectionUtils.invokeMethod((Method)this.dumpHeapMethod, (Object)this.diagnosticMXBean, (Object[])new Object[]{"heap", file.getAbsolutePath()});
        }
    }

    protected static class HotSpotDiagnosticMXBeanHeapDumper
    implements HeapDumper {
        private Object diagnosticMXBean;
        private Method dumpHeapMethod;

        protected HotSpotDiagnosticMXBeanHeapDumper() {
            try {
                Class diagnosticMXBeanClass = ClassUtils.resolveClassName((String)"com.sun.management.HotSpotDiagnosticMXBean", null);
                this.diagnosticMXBean = ManagementFactory.getPlatformMXBean(diagnosticMXBeanClass);
                this.dumpHeapMethod = ReflectionUtils.findMethod((Class)diagnosticMXBeanClass, (String)"dumpHeap", (Class[])new Class[]{String.class, Boolean.TYPE});
            }
            catch (Throwable ex) {
                throw new HeapDumperUnavailableException("Unable to locate HotSpotDiagnosticMXBean", ex);
            }
        }

        @Override
        public void dumpHeap(File file, boolean live) {
            ReflectionUtils.invokeMethod((Method)this.dumpHeapMethod, (Object)this.diagnosticMXBean, (Object[])new Object[]{file.getAbsolutePath(), live});
        }
    }

    @FunctionalInterface
    protected static interface HeapDumper {
        public void dumpHeap(File var1, boolean var2) throws IOException, InterruptedException;
    }
}

