/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.AdbHelper;
import com.android.ddmlib.DdmPreferences;
import com.android.ddmlib.Device;
import com.android.ddmlib.FileListingService;
import com.android.ddmlib.Log;
import com.android.ddmlib.SyncException;
import com.android.ddmlib.TimeoutException;
import com.android.ddmlib.utils.ArrayHelper;
import com.android.ddmlib.utils.FilePermissionUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Date;

public class SyncService {
    private static final byte[] ID_OKAY = new byte[]{79, 75, 65, 89};
    private static final byte[] ID_FAIL = new byte[]{70, 65, 73, 76};
    private static final byte[] ID_STAT = new byte[]{83, 84, 65, 84};
    private static final byte[] ID_RECV = new byte[]{82, 69, 67, 86};
    private static final byte[] ID_DATA = new byte[]{68, 65, 84, 65};
    private static final byte[] ID_DONE = new byte[]{68, 79, 78, 69};
    private static final byte[] ID_SEND = new byte[]{83, 69, 78, 68};
    private static final NullSyncProgressMonitor sNullSyncProgressMonitor = new NullSyncProgressMonitor();
    private static final int S_ISOCK = 49152;
    private static final int S_IFLNK = 40960;
    private static final int S_IFREG = 32768;
    private static final int S_IFBLK = 24576;
    private static final int S_IFDIR = 16384;
    private static final int S_IFCHR = 8192;
    private static final int S_IFIFO = 4096;
    private static final int SYNC_DATA_MAX = 65536;
    private static final int REMOTE_PATH_MAX_LENGTH = 1024;
    private InetSocketAddress mAddress;
    private Device mDevice;
    private SocketChannel mChannel;
    private byte[] mBuffer;

    SyncService(InetSocketAddress address, Device device) {
        this.mAddress = address;
        this.mDevice = device;
    }

    boolean openSync() throws TimeoutException, AdbCommandRejectedException, IOException {
        try {
            this.mChannel = SocketChannel.open(this.mAddress);
            this.mChannel.configureBlocking(false);
            AdbHelper.setDevice(this.mChannel, this.mDevice);
            byte[] request = AdbHelper.formAdbRequest("sync:");
            AdbHelper.write(this.mChannel, request, -1, DdmPreferences.getTimeOut());
            AdbHelper.AdbResponse resp = AdbHelper.readAdbResponse(this.mChannel, false);
            if (!resp.okay) {
                Log.w("ddms", "Got unhappy response from ADB sync req: " + resp.message);
                this.mChannel.close();
                this.mChannel = null;
                return false;
            }
        }
        catch (TimeoutException e2) {
            if (this.mChannel != null) {
                try {
                    this.mChannel.close();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
                this.mChannel = null;
            }
            throw e2;
        }
        catch (IOException e3) {
            if (this.mChannel != null) {
                try {
                    this.mChannel.close();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
                this.mChannel = null;
            }
            throw e3;
        }
        return true;
    }

    public void close() {
        if (this.mChannel != null) {
            try {
                this.mChannel.close();
            }
            catch (IOException iOException) {
                // empty catch block
            }
            this.mChannel = null;
        }
    }

    public static ISyncProgressMonitor getNullProgressMonitor() {
        return sNullSyncProgressMonitor;
    }

    public void pull(FileListingService.FileEntry[] entries, String localPath, ISyncProgressMonitor monitor) throws SyncException, IOException, TimeoutException {
        File f2 = new File(localPath);
        if (!f2.exists()) {
            throw new SyncException(SyncException.SyncError.NO_DIR_TARGET);
        }
        if (!f2.isDirectory()) {
            throw new SyncException(SyncException.SyncError.TARGET_IS_FILE);
        }
        FileListingService fls = new FileListingService(this.mDevice);
        int total = this.getTotalRemoteFileSize(entries, fls);
        monitor.start(total);
        this.doPull(entries, localPath, fls, monitor);
        monitor.stop();
    }

    public void pullFile(FileListingService.FileEntry remote, String localFilename, ISyncProgressMonitor monitor) throws IOException, SyncException, TimeoutException {
        int total = remote.getSizeValue();
        monitor.start(total);
        this.doPullFile(remote.getFullPath(), localFilename, monitor);
        monitor.stop();
    }

    public void pullFile(String remoteFilepath, String localFilename, ISyncProgressMonitor monitor) throws TimeoutException, IOException, SyncException {
        FileStat fileStat = this.statFile(remoteFilepath);
        if (fileStat != null && fileStat.getMode() == 0) {
            throw new SyncException(SyncException.SyncError.NO_REMOTE_OBJECT);
        }
        monitor.start(0);
        this.doPullFile(remoteFilepath, localFilename, monitor);
        monitor.stop();
    }

    public void push(String[] local, FileListingService.FileEntry remote, ISyncProgressMonitor monitor) throws SyncException, IOException, TimeoutException {
        if (!remote.isDirectory()) {
            throw new SyncException(SyncException.SyncError.REMOTE_IS_FILE);
        }
        ArrayList<File> files = new ArrayList<File>();
        for (String path : local) {
            files.add(new File(path));
        }
        File[] fileArray = files.toArray(new File[files.size()]);
        int total = this.getTotalLocalFileSize(fileArray);
        monitor.start(total);
        this.doPush(fileArray, remote.getFullPath(), monitor);
        monitor.stop();
    }

    public void pushFile(String local, String remote, ISyncProgressMonitor monitor) throws SyncException, IOException, TimeoutException {
        File f2 = new File(local);
        if (!f2.exists()) {
            throw new SyncException(SyncException.SyncError.NO_LOCAL_FILE);
        }
        if (f2.isDirectory()) {
            throw new SyncException(SyncException.SyncError.LOCAL_IS_DIRECTORY);
        }
        monitor.start((int)f2.length());
        this.doPushFile(local, remote, monitor);
        monitor.stop();
    }

    private int getTotalRemoteFileSize(FileListingService.FileEntry[] entries, FileListingService fls) {
        int count = 0;
        for (FileListingService.FileEntry e2 : entries) {
            int type = e2.getType();
            if (type == 1) {
                FileListingService.FileEntry[] children = fls.getChildren(e2, false, null);
                count += this.getTotalRemoteFileSize(children, fls) + 1;
                continue;
            }
            if (type != 0) continue;
            count += e2.getSizeValue();
        }
        return count;
    }

    private int getTotalLocalFileSize(File[] files) {
        int count = 0;
        for (File f2 : files) {
            if (!f2.exists()) continue;
            if (f2.isDirectory()) {
                return this.getTotalLocalFileSize(f2.listFiles()) + 1;
            }
            if (!f2.isFile()) continue;
            count = (int)((long)count + f2.length());
        }
        return count;
    }

    private void doPull(FileListingService.FileEntry[] entries, String localPath, FileListingService fileListingService, ISyncProgressMonitor monitor) throws SyncException, IOException, TimeoutException {
        for (FileListingService.FileEntry e2 : entries) {
            String dest;
            if (monitor.isCanceled()) {
                throw new SyncException(SyncException.SyncError.CANCELED);
            }
            int type = e2.getType();
            if (type == 1) {
                monitor.startSubTask(e2.getFullPath());
                dest = localPath + File.separator + e2.getName();
                File d2 = new File(dest);
                d2.mkdir();
                FileListingService.FileEntry[] children = fileListingService.getChildren(e2, true, null);
                this.doPull(children, dest, fileListingService, monitor);
                monitor.advance(1);
                continue;
            }
            if (type != 0) continue;
            monitor.startSubTask(e2.getFullPath());
            dest = localPath + File.separator + e2.getName();
            this.doPullFile(e2.getFullPath(), dest, monitor);
        }
    }

    private void doPullFile(String remotePath, String localPath, ISyncProgressMonitor monitor) throws IOException, SyncException, TimeoutException {
        byte[] msg = null;
        byte[] pullResult = new byte[8];
        int timeOut = DdmPreferences.getTimeOut();
        byte[] remotePathContent = remotePath.getBytes(AdbHelper.DEFAULT_CHARSET);
        if (remotePathContent.length > 1024) {
            throw new SyncException(SyncException.SyncError.REMOTE_PATH_LENGTH);
        }
        msg = SyncService.createFileReq(ID_RECV, remotePathContent);
        AdbHelper.write(this.mChannel, msg, -1, timeOut);
        AdbHelper.read(this.mChannel, pullResult, -1, timeOut);
        if (!SyncService.checkResult(pullResult, ID_DATA) && !SyncService.checkResult(pullResult, ID_DONE)) {
            throw new SyncException(SyncException.SyncError.TRANSFER_PROTOCOL_ERROR, this.readErrorMessage(pullResult, timeOut));
        }
        File f2 = new File(localPath);
        try (FileOutputStream fos = null;){
            fos = new FileOutputStream(f2);
            byte[] data = new byte[65536];
            while (true) {
                if (monitor.isCanceled()) {
                    throw new SyncException(SyncException.SyncError.CANCELED);
                }
                if (SyncService.checkResult(pullResult, ID_DONE)) break;
                if (!SyncService.checkResult(pullResult, ID_DATA)) {
                    throw new SyncException(SyncException.SyncError.TRANSFER_PROTOCOL_ERROR, this.readErrorMessage(pullResult, timeOut));
                }
                int length = ArrayHelper.swap32bitFromArray(pullResult, 4);
                if (length > 65536) {
                    throw new SyncException(SyncException.SyncError.BUFFER_OVERRUN);
                }
                AdbHelper.read(this.mChannel, data, length, timeOut);
                AdbHelper.read(this.mChannel, pullResult, -1, timeOut);
                fos.write(data, 0, length);
                monitor.advance(length);
            }
            fos.flush();
        }
    }

    private void doPush(File[] fileArray, String remotePath, ISyncProgressMonitor monitor) throws SyncException, IOException, TimeoutException {
        for (File f2 : fileArray) {
            if (monitor.isCanceled()) {
                throw new SyncException(SyncException.SyncError.CANCELED);
            }
            if (!f2.exists()) continue;
            if (f2.isDirectory()) {
                String dest = remotePath + "/" + f2.getName();
                monitor.startSubTask(dest);
                this.doPush(f2.listFiles(), dest, monitor);
                monitor.advance(1);
                continue;
            }
            if (!f2.isFile()) continue;
            String remoteFile = remotePath + "/" + f2.getName();
            monitor.startSubTask(remoteFile);
            this.doPushFile(f2.getAbsolutePath(), remoteFile, monitor);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void doPushFile(String localPath, String remotePath, ISyncProgressMonitor monitor) throws SyncException, IOException, TimeoutException {
        byte[] msg;
        FileInputStream fis = null;
        int timeOut = DdmPreferences.getTimeOut();
        File f2 = new File(localPath);
        try {
            byte[] remotePathContent = remotePath.getBytes(AdbHelper.DEFAULT_CHARSET);
            if (remotePathContent.length > 1024) {
                throw new SyncException(SyncException.SyncError.REMOTE_PATH_LENGTH);
            }
            fis = new FileInputStream(f2);
            int permissions = FilePermissionUtil.getFilePosixPermission(f2);
            msg = SyncService.createSendFileReq(ID_SEND, remotePathContent, permissions);
            AdbHelper.write(this.mChannel, msg, -1, timeOut);
            System.arraycopy(ID_DATA, 0, this.getBuffer(), 0, ID_DATA.length);
            while (true) {
                if (monitor.isCanceled()) {
                    throw new SyncException(SyncException.SyncError.CANCELED);
                }
                int readCount = fis.read(this.getBuffer(), 8, 65536);
                if (readCount == -1) {
                    break;
                }
                ArrayHelper.swap32bitsToArray(readCount, this.getBuffer(), 4);
                AdbHelper.write(this.mChannel, this.getBuffer(), readCount + 8, timeOut);
                monitor.advance(readCount);
            }
        }
        finally {
            if (fis != null) {
                fis.close();
            }
        }
        long time = f2.lastModified() / 1000L;
        msg = SyncService.createReq(ID_DONE, (int)time);
        AdbHelper.write(this.mChannel, msg, -1, timeOut);
        byte[] result = new byte[8];
        AdbHelper.read(this.mChannel, result, -1, timeOut);
        if (!SyncService.checkResult(result, ID_OKAY)) {
            throw new SyncException(SyncException.SyncError.TRANSFER_PROTOCOL_ERROR, this.readErrorMessage(result, timeOut));
        }
    }

    private String readErrorMessage(byte[] result, int timeOut) throws TimeoutException, IOException {
        int len;
        if (SyncService.checkResult(result, ID_FAIL) && (len = ArrayHelper.swap32bitFromArray(result, 4)) > 0) {
            AdbHelper.read(this.mChannel, this.getBuffer(), len, timeOut);
            String message = new String(this.getBuffer(), 0, len);
            Log.e("ddms", "transfer error: " + message);
            return message;
        }
        return null;
    }

    public FileStat statFile(String path) throws TimeoutException, IOException {
        byte[] msg = SyncService.createFileReq(ID_STAT, path);
        AdbHelper.write(this.mChannel, msg, -1, DdmPreferences.getTimeOut());
        byte[] statResult = new byte[16];
        AdbHelper.read(this.mChannel, statResult, -1, DdmPreferences.getTimeOut());
        if (!SyncService.checkResult(statResult, ID_STAT)) {
            return null;
        }
        int mode = ArrayHelper.swap32bitFromArray(statResult, 4);
        int size = ArrayHelper.swap32bitFromArray(statResult, 8);
        int lastModifiedSecs = ArrayHelper.swap32bitFromArray(statResult, 12);
        return new FileStat(mode, size, lastModifiedSecs);
    }

    private static byte[] createReq(byte[] command, int value) {
        byte[] array = new byte[8];
        System.arraycopy(command, 0, array, 0, 4);
        ArrayHelper.swap32bitsToArray(value, array, 4);
        return array;
    }

    private static byte[] createFileReq(byte[] command, String path) {
        return SyncService.createFileReq(command, path.getBytes(AdbHelper.DEFAULT_CHARSET));
    }

    private static byte[] createFileReq(byte[] command, byte[] path) {
        byte[] array = new byte[8 + path.length];
        System.arraycopy(command, 0, array, 0, 4);
        ArrayHelper.swap32bitsToArray(path.length, array, 4);
        System.arraycopy(path, 0, array, 8, path.length);
        return array;
    }

    private static byte[] createSendFileReq(byte[] command, byte[] path, int mode) {
        String modeStr = "," + (mode & 0x1FF);
        byte[] modeContent = modeStr.getBytes(AdbHelper.DEFAULT_CHARSET);
        byte[] array = new byte[8 + path.length + modeContent.length];
        System.arraycopy(command, 0, array, 0, 4);
        ArrayHelper.swap32bitsToArray(path.length + modeContent.length, array, 4);
        System.arraycopy(path, 0, array, 8, path.length);
        System.arraycopy(modeContent, 0, array, 8 + path.length, modeContent.length);
        return array;
    }

    private static boolean checkResult(byte[] result, byte[] code) {
        return result[0] == code[0] && result[1] == code[1] && result[2] == code[2] && result[3] == code[3];
    }

    private static int getFileType(int mode) {
        if ((mode & 0xC000) == 49152) {
            return 6;
        }
        if ((mode & 0xA000) == 40960) {
            return 5;
        }
        if ((mode & 0x8000) == 32768) {
            return 0;
        }
        if ((mode & 0x6000) == 24576) {
            return 3;
        }
        if ((mode & 0x4000) == 16384) {
            return 1;
        }
        if ((mode & 0x2000) == 8192) {
            return 4;
        }
        if ((mode & 0x1000) == 4096) {
            return 7;
        }
        return 8;
    }

    private byte[] getBuffer() {
        if (this.mBuffer == null) {
            this.mBuffer = new byte[65544];
        }
        return this.mBuffer;
    }

    private static class NullSyncProgressMonitor
    implements ISyncProgressMonitor {
        private NullSyncProgressMonitor() {
        }

        @Override
        public void advance(int work) {
        }

        @Override
        public boolean isCanceled() {
            return false;
        }

        @Override
        public void start(int totalWork) {
        }

        @Override
        public void startSubTask(String name) {
        }

        @Override
        public void stop() {
        }
    }

    public static class FileStat {
        private final int myMode;
        private final int mySize;
        private final Date myLastModified;

        public FileStat(int mode, int size, int lastModifiedSecs) {
            this.myMode = mode;
            this.mySize = size;
            this.myLastModified = new Date((long)lastModifiedSecs * 1000L);
        }

        public int getMode() {
            return this.myMode;
        }

        public int getSize() {
            return this.mySize;
        }

        public Date getLastModified() {
            return this.myLastModified;
        }
    }

    public static interface ISyncProgressMonitor {
        public void start(int var1);

        public void stop();

        public boolean isCanceled();

        public void startSubTask(String var1);

        public void advance(int var1);
    }
}

