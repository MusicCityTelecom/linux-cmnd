/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.roomcheck;

import com.tpvision.smartinstall.dao.core.PlayoutInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomCheckResult {
    private String conflictResult;
    private List<PlayoutInfo> currentAllPlayoutInfoList = new ArrayList<PlayoutInfo>();
    private Map<PlayoutInfo, String> partialOverlapUpdateRequiredPlayoutInfoMap = new HashMap<PlayoutInfo, String>();
    private List<PlayoutInfo> fullOverlapRemoveRequiredPlayoutInfoList = new ArrayList<PlayoutInfo>();
    private int willAddCount = 0;

    public String getConflictResult() {
        return this.conflictResult;
    }

    public void setConflictResult(String conflictResult) {
        this.conflictResult = conflictResult;
    }

    public List<PlayoutInfo> getCurrentAllPlayoutInfoList() {
        return this.currentAllPlayoutInfoList;
    }

    public void setCurrentAllPlayoutInfoList(List<PlayoutInfo> currentAllPlayoutInfoList) {
        this.currentAllPlayoutInfoList = currentAllPlayoutInfoList;
    }

    public Map<PlayoutInfo, String> getPartialOverlapUpdateRequiredPlayoutInfoMap() {
        return this.partialOverlapUpdateRequiredPlayoutInfoMap;
    }

    public List<PlayoutInfo> getFullOverlapRemoveRequiredPlayoutInfoList() {
        return this.fullOverlapRemoveRequiredPlayoutInfoList;
    }

    public int getWillAddCount() {
        return this.willAddCount;
    }

    public void setWillAddCount(int willAddCount) {
        this.willAddCount = willAddCount;
    }
}

