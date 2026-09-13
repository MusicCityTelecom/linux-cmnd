/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.AdbCommandRejectedException;
import com.android.ddmlib.Device;
import com.android.ddmlib.IDevice;
import com.android.ddmlib.MultiLineReceiver;
import com.android.ddmlib.ShellCommandUnresponsiveException;
import com.android.ddmlib.TimeoutException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class FileListingService {
    private static final Pattern sApkPattern = Pattern.compile(".*\\.apk", 2);
    private static final String PM_FULL_LISTING = "pm list packages -f";
    private static final Pattern sPmPattern = Pattern.compile("^package:(.+?)=(.+)$");
    public static final String DIRECTORY_DATA = "data";
    public static final String DIRECTORY_SDCARD = "sdcard";
    public static final String DIRECTORY_MNT = "mnt";
    public static final String DIRECTORY_SYSTEM = "system";
    public static final String DIRECTORY_TEMP = "tmp";
    public static final String DIRECTORY_APP = "app";
    public static final long REFRESH_RATE = 5000L;
    static final long REFRESH_TEST = 4000L;
    public static final int TYPE_FILE = 0;
    public static final int TYPE_DIRECTORY = 1;
    public static final int TYPE_DIRECTORY_LINK = 2;
    public static final int TYPE_BLOCK = 3;
    public static final int TYPE_CHARACTER = 4;
    public static final int TYPE_LINK = 5;
    public static final int TYPE_SOCKET = 6;
    public static final int TYPE_FIFO = 7;
    public static final int TYPE_OTHER = 8;
    public static final String FILE_SEPARATOR = "/";
    private static final String FILE_ROOT = "/";
    public static final Pattern LS_L_PATTERN = Pattern.compile("^([bcdlsp-][-r][-w][-xsS][-r][-w][-xsS][-r][-w][-xstST])\\s+(?:\\d+\\s+)?(\\S+)\\s+(\\S+)\\s+([\\d\\s,]*)\\s+(\\d{4}-\\d\\d-\\d\\d)\\s+(\\d\\d:\\d\\d)\\s+(.*)$");
    public static final Pattern LS_LD_PATTERN = Pattern.compile("d[rwx-]{9}\\s+(\\d+\\s+)?\\S+\\s+\\S+\\s+(\\d+\\s+)?[0-9-]{10}\\s+\\d{2}:\\d{2}.*$");
    private Device mDevice;
    private FileEntry mRoot;
    private final ArrayList<Thread> mThreadList = new ArrayList();

    FileListingService(Device device) {
        this.mDevice = device;
    }

    public FileEntry getRoot() {
        if (this.mDevice != null) {
            if (this.mRoot == null) {
                this.mRoot = new FileEntry(null, "", 1, true);
            }
            return this.mRoot;
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public FileEntry[] getChildren(final FileEntry entry, boolean useCache, final IListingReceiver receiver) {
        if (useCache && !entry.needFetch()) {
            return entry.getCachedChildren();
        }
        if (receiver == null) {
            this.doLs(entry);
            return entry.getCachedChildren();
        }
        Thread t3 = new Thread("ls " + entry.getFullPath()){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void run() {
                FileListingService.this.doLs(entry);
                receiver.setChildren(entry, entry.getCachedChildren());
                FileEntry[] children = entry.getCachedChildren();
                if (children.length > 0 && children[0].isApplicationPackage()) {
                    final HashMap<String, FileEntry> map = new HashMap<String, FileEntry>();
                    for (FileEntry child : children) {
                        String path = child.getFullPath();
                        map.put(path, child);
                    }
                    String command = FileListingService.PM_FULL_LISTING;
                    try {
                        FileListingService.this.mDevice.executeShellCommand(command, new MultiLineReceiver(){

                            @Override
                            public void processNewLines(String[] lines) {
                                for (String line : lines) {
                                    FileEntry entry;
                                    Matcher m3;
                                    if (line.isEmpty() || !(m3 = sPmPattern.matcher(line)).matches() || (entry = (FileEntry)map.get(m3.group(1))) == null) continue;
                                    entry.info = m3.group(2);
                                    receiver.refreshEntry(entry);
                                }
                            }

                            @Override
                            public boolean isCancelled() {
                                return false;
                            }
                        });
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                ArrayList arrayList = FileListingService.this.mThreadList;
                synchronized (arrayList) {
                    FileListingService.this.mThreadList.remove(this);
                    if (!FileListingService.this.mThreadList.isEmpty()) {
                        Thread t3 = (Thread)FileListingService.this.mThreadList.get(0);
                        t3.start();
                    }
                }
            }
        };
        ArrayList<Thread> arrayList = this.mThreadList;
        synchronized (arrayList) {
            this.mThreadList.add(t3);
            if (this.mThreadList.size() == 1) {
                t3.start();
            }
        }
        return null;
    }

    public FileEntry[] getChildrenSync(FileEntry entry) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException {
        this.doLsAndThrow(entry);
        return entry.getCachedChildren();
    }

    private void doLs(FileEntry entry) {
        try {
            this.doLsAndThrow(entry);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void doLsAndThrow(FileEntry entry) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException {
        ArrayList<FileEntry> entryList = new ArrayList<FileEntry>();
        ArrayList<String> linkList = new ArrayList<String>();
        try {
            String command = "ls -l " + entry.getFullEscapedPath();
            if (entry.isDirectory()) {
                command = command + "/";
            }
            LsReceiver receiver = new LsReceiver(entry, entryList, linkList);
            this.mDevice.executeShellCommand(command, receiver);
            receiver.finishLinks(this.mDevice, entryList);
        }
        finally {
            entry.fetchTime = System.currentTimeMillis();
            Collections.sort(entryList, FileEntry.sEntryComparator);
            entry.setChildren(entryList);
        }
    }

    public static interface IListingReceiver {
        public void setChildren(FileEntry var1, FileEntry[] var2);

        public void refreshEntry(FileEntry var1);
    }

    private static class LsReceiver
    extends MultiLineReceiver {
        private ArrayList<FileEntry> mEntryList;
        private ArrayList<String> mLinkList;
        private FileEntry[] mCurrentChildren;
        private FileEntry mParentEntry;

        public LsReceiver(FileEntry parentEntry, ArrayList<FileEntry> entryList, ArrayList<String> linkList) {
            this.mParentEntry = parentEntry;
            this.mCurrentChildren = parentEntry.getCachedChildren();
            this.mEntryList = entryList;
            this.mLinkList = linkList;
        }

        @Override
        public void processNewLines(String[] lines) {
            for (String line : lines) {
                FileEntry entry;
                Matcher m3;
                if (line.isEmpty() || !(m3 = LS_L_PATTERN.matcher(line)).matches()) continue;
                String name = m3.group(7);
                String permissions = m3.group(1);
                String owner = m3.group(2);
                String group = m3.group(3);
                String size = m3.group(4);
                String date = m3.group(5);
                String time = m3.group(6);
                String info = null;
                int objectType = 8;
                switch (permissions.charAt(0)) {
                    case '-': {
                        objectType = 0;
                        break;
                    }
                    case 'b': {
                        objectType = 3;
                        break;
                    }
                    case 'c': {
                        objectType = 4;
                        break;
                    }
                    case 'd': {
                        objectType = 1;
                        break;
                    }
                    case 'l': {
                        objectType = 5;
                        break;
                    }
                    case 's': {
                        objectType = 6;
                        break;
                    }
                    case 'p': {
                        objectType = 7;
                    }
                }
                if (objectType == 5) {
                    String[] segments = name.split("\\s->\\s");
                    if (segments.length == 2) {
                        name = segments[0];
                        info = segments[1];
                        String[] pathSegments = info.split("/");
                        if (pathSegments.length == 1 && "..".equals(pathSegments[0])) {
                            objectType = 2;
                        }
                    }
                    info = "-> " + info;
                }
                if ((entry = this.getExistingEntry(name)) == null) {
                    entry = new FileEntry(this.mParentEntry, name, objectType, false);
                }
                entry.permissions = permissions;
                entry.size = size;
                entry.date = date;
                entry.time = time;
                entry.owner = owner;
                entry.group = group;
                if (objectType == 5) {
                    entry.info = info;
                }
                this.mEntryList.add(entry);
            }
        }

        private FileEntry getExistingEntry(String name) {
            for (int i2 = 0; i2 < this.mCurrentChildren.length; ++i2) {
                FileEntry e2 = this.mCurrentChildren[i2];
                if (e2 == null || !name.equals(e2.name)) continue;
                this.mCurrentChildren[i2] = null;
                return e2;
            }
            return null;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        public void finishLinks(IDevice device, ArrayList<FileEntry> entries) throws TimeoutException, AdbCommandRejectedException, ShellCommandUnresponsiveException, IOException {
            final int[] nLines = new int[]{0};
            MultiLineReceiver receiver = new MultiLineReceiver(){

                @Override
                public void processNewLines(String[] lines) {
                    for (String line : lines) {
                        Matcher m3 = LS_LD_PATTERN.matcher(line);
                        if (!m3.matches()) continue;
                        nLines[0] = nLines[0] + 1;
                    }
                }

                @Override
                public boolean isCancelled() {
                    return false;
                }
            };
            for (FileEntry entry : entries) {
                if (entry.getType() != 5) continue;
                nLines[0] = 0;
                String command = String.format("ls -l -d %s%s", entry.getFullEscapedPath(), "/");
                device.executeShellCommand(command, receiver);
                if (nLines[0] <= 0) continue;
                entry.setType(2);
            }
        }
    }

    public static final class FileEntry {
        private static final Pattern sEscapePattern = Pattern.compile("([\\\\()*+?\"'&#/\\s])");
        private static Comparator<FileEntry> sEntryComparator = new Comparator<FileEntry>(){

            @Override
            public int compare(FileEntry o12, FileEntry o22) {
                if (o12 instanceof FileEntry && o22 instanceof FileEntry) {
                    FileEntry fe1 = o12;
                    FileEntry fe2 = o22;
                    return fe1.name.compareTo(fe2.name);
                }
                return 0;
            }
        };
        FileEntry parent;
        String name;
        String info;
        String permissions;
        String size;
        String date;
        String time;
        String owner;
        String group;
        int type;
        boolean isAppPackage;
        boolean isRoot;
        long fetchTime = 0L;
        final ArrayList<FileEntry> mChildren = new ArrayList();

        public FileEntry(FileEntry parent, String name, int type, boolean isRoot) {
            this.parent = parent;
            this.name = name;
            this.type = type;
            this.isRoot = isRoot;
            this.checkAppPackageStatus();
        }

        public String getName() {
            return this.name;
        }

        public String getSize() {
            return this.size;
        }

        public int getSizeValue() {
            return Integer.parseInt(this.size);
        }

        public String getDate() {
            return this.date;
        }

        public String getTime() {
            return this.time;
        }

        public String getPermissions() {
            return this.permissions;
        }

        public String getOwner() {
            return this.owner;
        }

        public String getGroup() {
            return this.group;
        }

        public String getInfo() {
            return this.info;
        }

        public String getFullPath() {
            if (this.isRoot) {
                return "/";
            }
            StringBuilder pathBuilder = new StringBuilder();
            this.fillPathBuilder(pathBuilder, false);
            return pathBuilder.toString();
        }

        public String getFullEscapedPath() {
            StringBuilder pathBuilder = new StringBuilder();
            this.fillPathBuilder(pathBuilder, true);
            return pathBuilder.toString();
        }

        public String[] getPathSegments() {
            ArrayList<String> list = new ArrayList<String>();
            this.fillPathSegments(list);
            return list.toArray(new String[list.size()]);
        }

        public int getType() {
            return this.type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public boolean isDirectory() {
            return this.type == 1 || this.type == 2;
        }

        public FileEntry getParent() {
            return this.parent;
        }

        public FileEntry[] getCachedChildren() {
            return this.mChildren.toArray(new FileEntry[this.mChildren.size()]);
        }

        public FileEntry findChild(String name) {
            for (FileEntry entry : this.mChildren) {
                if (!entry.name.equals(name)) continue;
                return entry;
            }
            return null;
        }

        public boolean isRoot() {
            return this.isRoot;
        }

        void addChild(FileEntry child) {
            this.mChildren.add(child);
        }

        void setChildren(ArrayList<FileEntry> newChildren) {
            this.mChildren.clear();
            this.mChildren.addAll(newChildren);
        }

        boolean needFetch() {
            if (this.fetchTime == 0L) {
                return true;
            }
            long current = System.currentTimeMillis();
            return current - this.fetchTime > 4000L;
        }

        public boolean isApplicationPackage() {
            return this.isAppPackage;
        }

        public boolean isAppFileName() {
            Matcher m3 = sApkPattern.matcher(this.name);
            return m3.matches();
        }

        protected void fillPathBuilder(StringBuilder pathBuilder, boolean escapePath) {
            if (this.isRoot) {
                return;
            }
            if (this.parent != null) {
                this.parent.fillPathBuilder(pathBuilder, escapePath);
            }
            pathBuilder.append("/");
            pathBuilder.append(escapePath ? FileEntry.escape(this.name) : this.name);
        }

        protected void fillPathSegments(ArrayList<String> list) {
            if (this.isRoot) {
                return;
            }
            if (this.parent != null) {
                this.parent.fillPathSegments(list);
            }
            list.add(this.name);
        }

        private void checkAppPackageStatus() {
            this.isAppPackage = false;
            String[] segments = this.getPathSegments();
            if (this.type == 0 && segments.length == 3 && this.isAppFileName()) {
                this.isAppPackage = FileListingService.DIRECTORY_APP.equals(segments[1]) && (FileListingService.DIRECTORY_SYSTEM.equals(segments[0]) || FileListingService.DIRECTORY_DATA.equals(segments[0]));
            }
        }

        public static String escape(String entryName) {
            return sEscapePattern.matcher(entryName).replaceAll("\\\\$1");
        }
    }
}

