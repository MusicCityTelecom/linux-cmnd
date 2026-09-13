/*
 * Decompiled with CFR 0.152.
 */
package com.android.ddmlib;

import com.android.ddmlib.AllocationInfo;
import com.android.ddmlib.AllocationsParser;
import com.android.ddmlib.Client;
import com.android.ddmlib.HeapSegment;
import com.android.ddmlib.NativeAllocationInfo;
import com.android.ddmlib.NativeLibraryMapInfo;
import com.android.ddmlib.ThreadInfo;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class ClientData {
    private static final String PRE_INITIALIZED = "<pre-initialized>";
    public static final String FEATURE_PROFILING = "method-trace-profiling";
    public static final String FEATURE_PROFILING_STREAMING = "method-trace-profiling-streaming";
    public static final String FEATURE_SAMPLING_PROFILER = "method-sample-profiling";
    public static final String FEATURE_OPENGL_TRACING = "opengl-tracing";
    public static final String FEATURE_VIEW_HIERARCHY = "view-hierarchy";
    public static final String FEATURE_HPROF = "hprof-heap-dump";
    public static final String FEATURE_HPROF_STREAMING = "hprof-heap-dump-streaming";
    @Deprecated
    private static IHprofDumpHandler sHprofDumpHandler;
    private static IMethodProfilingHandler sMethodProfilingHandler;
    private static IAllocationTrackingHandler sAllocationTrackingHandler;
    private boolean mIsDdmAware;
    private final int mPid;
    private String mVmIdentifier;
    private String mClientDescription;
    private int mUserId;
    private boolean mValidUserId;
    private String mAbi;
    private String mJvmFlags;
    private boolean mNativeDebuggable = false;
    private DebuggerStatus mDebuggerInterest;
    private final HashSet<String> mFeatures = new HashSet();
    private TreeMap<Integer, ThreadInfo> mThreadMap;
    private final HeapData mHeapData = new HeapData();
    private final HeapData mNativeHeapData = new HeapData();
    private HprofData mHprofData = null;
    private HashMap<Integer, HeapInfo> mHeapInfoMap = new HashMap();
    private ArrayList<NativeLibraryMapInfo> mNativeLibMapInfo = new ArrayList();
    private ArrayList<NativeAllocationInfo> mNativeAllocationList = new ArrayList();
    private int mNativeTotalMemory;
    private byte[] mAllocationsData;
    private AllocationInfo[] mAllocations;
    private AllocationTrackingStatus mAllocationStatus = AllocationTrackingStatus.UNKNOWN;
    @Deprecated
    private String mPendingHprofDump;
    private MethodProfilingStatus mProfilingStatus = MethodProfilingStatus.UNKNOWN;
    private String mPendingMethodProfiling;

    public void setHprofData(byte[] data) {
        this.mHprofData = new HprofData(data);
    }

    public void setHprofData(String filename) {
        this.mHprofData = new HprofData(filename);
    }

    public void clearHprofData() {
        this.mHprofData = null;
    }

    public HprofData getHprofData() {
        return this.mHprofData;
    }

    @Deprecated
    public static void setHprofDumpHandler(IHprofDumpHandler handler) {
        sHprofDumpHandler = handler;
    }

    @Deprecated
    static IHprofDumpHandler getHprofDumpHandler() {
        return sHprofDumpHandler;
    }

    public static void setMethodProfilingHandler(IMethodProfilingHandler handler) {
        sMethodProfilingHandler = handler;
    }

    static IMethodProfilingHandler getMethodProfilingHandler() {
        return sMethodProfilingHandler;
    }

    @Deprecated
    public static void setAllocationTrackingHandler(IAllocationTrackingHandler handler) {
        sAllocationTrackingHandler = handler;
    }

    @Deprecated
    static IAllocationTrackingHandler getAllocationTrackingHandler() {
        return sAllocationTrackingHandler;
    }

    ClientData(int pid) {
        this.mPid = pid;
        this.mDebuggerInterest = DebuggerStatus.DEFAULT;
        this.mThreadMap = new TreeMap();
    }

    public boolean isDdmAware() {
        return this.mIsDdmAware;
    }

    void isDdmAware(boolean aware) {
        this.mIsDdmAware = aware;
    }

    public int getPid() {
        return this.mPid;
    }

    public String getVmIdentifier() {
        return this.mVmIdentifier;
    }

    void setVmIdentifier(String ident) {
        this.mVmIdentifier = ident;
    }

    public String getClientDescription() {
        return this.mClientDescription;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public boolean isValidUserId() {
        return this.mValidUserId;
    }

    public String getAbi() {
        return this.mAbi;
    }

    public String getJvmFlags() {
        return this.mJvmFlags;
    }

    void setClientDescription(String description) {
        if (!description.isEmpty() && !PRE_INITIALIZED.equals(description)) {
            this.mClientDescription = description;
        }
    }

    void setUserId(int id) {
        this.mUserId = id;
        this.mValidUserId = true;
    }

    void setAbi(String abi) {
        this.mAbi = abi;
    }

    void setJvmFlags(String jvmFlags) {
        this.mJvmFlags = jvmFlags;
    }

    public boolean isNativeDebuggable() {
        return this.mNativeDebuggable;
    }

    public void setNativeDebuggable(boolean nativeDebuggable) {
        this.mNativeDebuggable = nativeDebuggable;
    }

    public DebuggerStatus getDebuggerConnectionStatus() {
        return this.mDebuggerInterest;
    }

    void setDebuggerConnectionStatus(DebuggerStatus status) {
        this.mDebuggerInterest = status;
    }

    synchronized void setHeapInfo(int heapId, long maxSizeInBytes, long sizeInBytes, long bytesAllocated, long objectsAllocated, long timeStamp, byte reason) {
        this.mHeapInfoMap.put(heapId, new HeapInfo(maxSizeInBytes, sizeInBytes, bytesAllocated, objectsAllocated, timeStamp, reason));
    }

    public HeapData getVmHeapData() {
        return this.mHeapData;
    }

    HeapData getNativeHeapData() {
        return this.mNativeHeapData;
    }

    public synchronized Iterator<Integer> getVmHeapIds() {
        return this.mHeapInfoMap.keySet().iterator();
    }

    public synchronized HeapInfo getVmHeapInfo(int heapId) {
        return this.mHeapInfoMap.get(heapId);
    }

    synchronized void addThread(int threadId, String threadName) {
        ThreadInfo attr = new ThreadInfo(threadId, threadName);
        this.mThreadMap.put(threadId, attr);
    }

    synchronized void removeThread(int threadId) {
        this.mThreadMap.remove(threadId);
    }

    public synchronized ThreadInfo[] getThreads() {
        Collection<ThreadInfo> threads = this.mThreadMap.values();
        return threads.toArray(new ThreadInfo[threads.size()]);
    }

    synchronized ThreadInfo getThread(int threadId) {
        return this.mThreadMap.get(threadId);
    }

    synchronized void clearThreads() {
        this.mThreadMap.clear();
    }

    public synchronized List<NativeAllocationInfo> getNativeAllocationList() {
        return Collections.unmodifiableList(this.mNativeAllocationList);
    }

    synchronized void addNativeAllocation(NativeAllocationInfo allocInfo) {
        this.mNativeAllocationList.add(allocInfo);
    }

    synchronized void clearNativeAllocationInfo() {
        this.mNativeAllocationList.clear();
    }

    public synchronized int getTotalNativeMemory() {
        return this.mNativeTotalMemory;
    }

    synchronized void setTotalNativeMemory(int totalMemory) {
        this.mNativeTotalMemory = totalMemory;
    }

    synchronized void addNativeLibraryMapInfo(long startAddr, long endAddr, String library) {
        this.mNativeLibMapInfo.add(new NativeLibraryMapInfo(startAddr, endAddr, library));
    }

    public synchronized List<NativeLibraryMapInfo> getMappedNativeLibraries() {
        return Collections.unmodifiableList(this.mNativeLibMapInfo);
    }

    synchronized void setAllocationStatus(AllocationTrackingStatus status) {
        this.mAllocationStatus = status;
    }

    public synchronized AllocationTrackingStatus getAllocationStatus() {
        return this.mAllocationStatus;
    }

    synchronized void setAllocationsData(byte[] data) {
        this.mAllocationsData = data;
    }

    public synchronized byte[] getAllocationsData() {
        return this.mAllocationsData;
    }

    @Deprecated
    synchronized void setAllocations(AllocationInfo[] allocs) {
        this.mAllocations = allocs;
    }

    public synchronized AllocationInfo[] getAllocations() {
        if (this.mAllocationsData != null) {
            return AllocationsParser.parse(ByteBuffer.wrap(this.mAllocationsData));
        }
        return null;
    }

    void addFeature(String feature) {
        this.mFeatures.add(feature);
    }

    public boolean hasFeature(String feature) {
        return this.mFeatures.contains(feature);
    }

    @Deprecated
    void setPendingHprofDump(String pendingHprofDump) {
        this.mPendingHprofDump = pendingHprofDump;
    }

    @Deprecated
    String getPendingHprofDump() {
        return this.mPendingHprofDump;
    }

    @Deprecated
    public boolean hasPendingHprofDump() {
        return this.mPendingHprofDump != null;
    }

    synchronized void setMethodProfilingStatus(MethodProfilingStatus status) {
        this.mProfilingStatus = status;
    }

    public synchronized MethodProfilingStatus getMethodProfilingStatus() {
        return this.mProfilingStatus;
    }

    void setPendingMethodProfiling(String pendingMethodProfiling) {
        this.mPendingMethodProfiling = pendingMethodProfiling;
    }

    String getPendingMethodProfiling() {
        return this.mPendingMethodProfiling;
    }

    public String getPackageName() {
        if (this.mClientDescription == null) {
            return null;
        }
        int colonPos = this.mClientDescription.indexOf(58);
        return colonPos == -1 ? this.mClientDescription : this.mClientDescription.substring(0, colonPos);
    }

    public String getDataDir() {
        String packageName = this.getPackageName();
        if (this.isValidUserId() && this.getUserId() > 0) {
            return String.format("/data/user/%d/%s", this.getUserId(), packageName);
        }
        return "/data/data/" + packageName;
    }

    public static interface IAllocationTrackingHandler {
        public void onSuccess(byte[] var1, Client var2);
    }

    public static interface IMethodProfilingHandler {
        public void onSuccess(String var1, Client var2);

        public void onSuccess(byte[] var1, Client var2);

        public void onStartFailure(Client var1, String var2);

        public void onEndFailure(Client var1, String var2);
    }

    @Deprecated
    public static interface IHprofDumpHandler {
        public void onSuccess(String var1, Client var2);

        public void onSuccess(byte[] var1, Client var2);

        public void onEndFailure(Client var1, String var2);
    }

    public static class HprofData {
        public final Type type;
        public final String filename;
        public final byte[] data;

        public HprofData(String filename) {
            this.type = Type.FILE;
            this.filename = filename;
            this.data = null;
        }

        public HprofData(byte[] data) {
            this.type = Type.DATA;
            this.data = data;
            this.filename = null;
        }

        public static enum Type {
            FILE,
            DATA;

        }
    }

    public static class HeapInfo {
        public long maxSizeInBytes;
        public long sizeInBytes;
        public long bytesAllocated;
        public long objectsAllocated;
        public long timeStamp;
        public byte reason;

        public HeapInfo(long maxSizeInBytes, long sizeInBytes, long bytesAllocated, long objectsAllocated, long timeStamp, byte reason) {
            this.maxSizeInBytes = maxSizeInBytes;
            this.sizeInBytes = sizeInBytes;
            this.bytesAllocated = bytesAllocated;
            this.objectsAllocated = objectsAllocated;
            this.timeStamp = timeStamp;
            this.reason = reason;
        }
    }

    public static class HeapData {
        private TreeSet<HeapSegment> mHeapSegments = new TreeSet();
        private boolean mHeapDataComplete = false;
        private byte[] mProcessedHeapData;
        private Map<Integer, ArrayList<HeapSegment.HeapSegmentElement>> mProcessedHeapMap;

        public synchronized void clearHeapData() {
            this.mHeapSegments = new TreeSet();
            this.mHeapDataComplete = false;
        }

        synchronized void addHeapData(ByteBuffer data) {
            HeapSegment hs;
            if (this.mHeapDataComplete) {
                this.clearHeapData();
            }
            try {
                hs = new HeapSegment(data);
            }
            catch (BufferUnderflowException e2) {
                System.err.println("Discarding short HPSG data (length " + data.limit() + ")");
                return;
            }
            this.mHeapSegments.add(hs);
        }

        synchronized void sealHeapData() {
            this.mHeapDataComplete = true;
        }

        public boolean isHeapDataComplete() {
            return this.mHeapDataComplete;
        }

        public Collection<HeapSegment> getHeapSegments() {
            if (this.isHeapDataComplete()) {
                return this.mHeapSegments;
            }
            return null;
        }

        public void setProcessedHeapData(byte[] heapData) {
            this.mProcessedHeapData = heapData;
        }

        public byte[] getProcessedHeapData() {
            return this.mProcessedHeapData;
        }

        public void setProcessedHeapMap(Map<Integer, ArrayList<HeapSegment.HeapSegmentElement>> heapMap) {
            this.mProcessedHeapMap = heapMap;
        }

        public Map<Integer, ArrayList<HeapSegment.HeapSegmentElement>> getProcessedHeapMap() {
            return this.mProcessedHeapMap;
        }
    }

    public static enum MethodProfilingStatus {
        UNKNOWN,
        OFF,
        TRACER_ON,
        SAMPLER_ON;

    }

    public static enum AllocationTrackingStatus {
        UNKNOWN,
        OFF,
        ON;

    }

    public static enum DebuggerStatus {
        DEFAULT,
        WAITING,
        ATTACHED,
        ERROR;

    }
}

